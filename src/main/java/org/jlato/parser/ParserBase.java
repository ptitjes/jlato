/*
 * Copyright (C) 2015 Didier Villevalois.
 *
 * This file is part of JLaTo.
 *
 * JLaTo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JLaTo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JLaTo.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.jlato.parser;

import com.github.andrewoma.dexx.collection.IndexedList;
import com.github.andrewoma.dexx.collection.Vector;
import org.jlato.internal.bu.*;
import org.jlato.internal.bu.coll.SNodeEither;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.bu.decl.*;
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.bu.name.SQualifiedName;
import org.jlato.internal.bu.stmt.SStmt;
import org.jlato.internal.bu.type.SType;
import org.jlato.internal.shapes.DressingBuilder;
import org.jlato.internal.shapes.LexicalShape;

import java.io.InputStream;
import java.io.Reader;
import java.util.Iterator;
import java.util.Stack;

import static org.jlato.parser.ParserImplConstants.*;

/**
 * @author Didier Villevalois
 */
public abstract class ParserBase extends ParserInterface {

	static class JavaCCParserFactory implements Factory {
		@Override
		public ParserInterface newInstance(InputStream in, String encoding) {
			ParserImpl parser = new ParserImpl(in, encoding);
			parser.reset();
			return parser;
		}

		@Override
		public ParserInterface newInstance(Reader in) {
			ParserImpl parser = new ParserImpl(in);
			parser.reset();
			return parser;
		}
	}

	private Stack<IndexedList<WTokenRun>> runStack = new Stack<IndexedList<WTokenRun>>();
	private Token lastProcessedToken;

	public ParserBase() {
		reset();
	}

	// Interface with ParserImpl
	protected abstract Token getToken(int index);

	protected void reset() {
		lastProcessedToken = null;
		runStack.clear();
		runStack.push(Vector.<WTokenRun>empty());

		if (lastProcessedToken == null) {
			lastProcessedToken = getToken(0);
		}
		pushWhitespace(getToken(0));
	}

	protected void run() {
		if (!configuration.preserveWhitespaces) return;

		pushWhitespace(getToken(1));
		runStack.push(Vector.<WTokenRun>empty());
	}

	protected void lateRun() {
		if (!configuration.preserveWhitespaces) return;

		runStack.push(Vector.<WTokenRun>empty());
		pushWhitespace(getToken(1));
	}

	protected void popNewWhitespaces() {
		if (!configuration.preserveWhitespaces) return;

		lastProcessedToken = getToken(0);
	}

	private void pushWhitespace(Token upToToken) {
		if (lastProcessedToken != upToToken &&
				(upToToken.next == null || lastProcessedToken != upToToken.next)) {
			do {
				lastProcessedToken = lastProcessedToken.next;
				pushWhitespace(lastProcessedToken.whitespace);
			} while (lastProcessedToken != upToToken);
		}
	}

	private void pushWhitespace(WTokenRun whitespace) {
		if (whitespace == null) return;

		runStack.push(runStack.pop().append(whitespace));
	}

	private IndexedList<WTokenRun> popTokens() {
		pushWhitespace(getToken(0));
		return runStack.pop();
	}

	protected <S extends STree> BUTree<S> dress(BUTree<S> tree) {
		return doCollectProblems(doDress(tree));
	}

	protected <S extends STree> BUTree<S> doDress(BUTree<S> tree) {
		if (!configuration.preserveWhitespaces) return tree;

		final IndexedList<WTokenRun> tokens = popTokens();

		if (tree == null) return null;

		final LexicalShape shape = tree.state.shape();
		return doDress(tree, shape, tokens);
	}

	private <S extends STree> BUTree<S> doDress(BUTree<S> tree, LexicalShape shape,
	                                            IndexedList<WTokenRun> tokens) {

		final Iterator<WTokenRun> tokenIterator = tokens.iterator();
		final BUTree<S> newTree;
		if (shape != null) {
			final DressingBuilder<S> builder = new DressingBuilder<S>(tree, tokenIterator);
			shape.dress(builder, tree);
			newTree = builder.build();
		} else newTree = tree;

		if (tokenIterator.hasNext()) {
			// Flow up the remaining whitespace run for consumption by parent tree
			final WTokenRun deferred = tokenIterator.next();
			pushWhitespace(deferred);

			// Only one whitespace run at most should flow up
			if (tokenIterator.hasNext()) {
				throw new IllegalStateException();
			}
		}

		return newTree;
	}

	private <S extends STree> BUTree<S> doCollectProblems(BUTree<S> tree) {
		S state = tree.state;
		boolean hasProblem = false;

		STraversal traversal = state.firstChild();
		while (traversal != null) {
			BUTree<?> child = traversal.traverse(state);

			if (child != null) {
				if (child.state instanceof SNodeList
						|| child.state instanceof SNodeOption
						|| child.state instanceof SNodeEither) {
					child = doCollectProblems(child);
					if (child.hasProblems()) {
						tree = tree.traverseReplace(traversal, child);
					}
				}
				if (child.hasProblems()) hasProblem = true;
			}

			traversal = traversal.rightSibling(state);
		}

		if (hasProblem) tree = tree.setProblems();
		return tree;
	}

	protected <S extends STree> BUTree<S> dressWithPrologAndEpilog(BUTree<S> tree) {
		if (!configuration.preserveWhitespaces) return tree;

		assert runStack.size() == 1;
		final IndexedList<WTokenRun> tokens = popTokens();
		// TODO This assertion does not hold with com/github/javaparser/ast/comments/CommentsParser
		// assert tokens.size() == 2;
		final WTokenRun prolog = tokens.get(0);
		final WTokenRun epilog = tokens.get(1);

		WDressing dressing = tree.dressing;
		if (dressing == null) dressing = new WDressing();

		if (!prolog.elements.isEmpty()) dressing = dressing.withLeading(prolog);
		if (!epilog.elements.isEmpty()) dressing = dressing.withTrailing(epilog);
		return tree.withDressing(dressing);
	}

	protected <S extends STree> BUTreeVar<S> makeVar() {
		Token token = getToken(0);
		pushWhitespace(token);

		String image = token.image;
		boolean nodeListVar = image.startsWith("..$");
		String name = nodeListVar ? image.substring(3) : image.substring(1);
		return BUTreeVar.var(name);
	}

	// Interface with ParserImpl
	protected void postProcessToken(Token token) {
		if (!configuration.preserveWhitespaces) return;

		token.whitespace = buildWhitespaceRunPart(token.specialToken);
	}

	private WTokenRun buildWhitespaceRunPart(Token token) {
		if (token != null)
			return buildWhitespaceRunPart(token.specialToken).append(new WToken(token.kind, token.image));
		else return new WTokenRun(Vector.<WToken>empty());
	}

	abstract void Epilog() throws ParseException;

	abstract BUTree<SCompilationUnit> CompilationUnit() throws ParseException;

	abstract BUTree<SPackageDecl> PackageDecl() throws ParseException;

	abstract BUTree<SImportDecl> ImportDecl() throws ParseException;

	abstract BUTree<? extends STypeDecl> TypeDecl() throws ParseException;

	abstract BUTree<? extends SMemberDecl> ClassOrInterfaceBodyDecl(TypeKind kind) throws ParseException;

	abstract BUTree<? extends SMemberDecl> AnnotationTypeBodyDecl() throws ParseException;

	abstract BUTree<SNodeList> Modifiers() throws ParseException;

	abstract BUTree<SMethodDecl> MethodDecl(BUTree<SNodeList> modifiers) throws ParseException;

	abstract BUTree<SFieldDecl> FieldDecl(BUTree<SNodeList> modifiers) throws ParseException;

	abstract BUTree<SAnnotationMemberDecl> AnnotationTypeMemberDecl(BUTree<SNodeList> modifiers) throws ParseException;

	abstract BUTree<SEnumConstantDecl> EnumConstantDecl() throws ParseException;

	abstract BUTree<SFormalParameter> FormalParameter() throws ParseException;

	abstract BUTree<STypeParameter> TypeParameter() throws ParseException;

	abstract BUTree<SNodeList> Statements() throws ParseException;

	abstract BUTree<? extends SStmt> BlockStatement() throws ParseException;

	abstract BUTree<? extends SExpr> Expression() throws ParseException;

	abstract BUTree<SNodeList> Annotations() throws ParseException;

	abstract BUTree<? extends SType> Type(BUTree<SNodeList> annotations) throws ParseException;

	abstract BUTree<SQualifiedName> QualifiedName() throws ParseException;

	abstract BUTree<SName> Name() throws ParseException;

	@Override
	protected BUTree<SCompilationUnit> parseCompilationUnit() throws ParseException {
		return CompilationUnit();
	}

	@Override
	protected BUTree<SPackageDecl> parsePackageDecl() throws ParseException {
		return wrapWithPrologAndEpilog(PackageDecl());
	}

	@Override
	protected BUTree<SImportDecl> parseImportDecl() throws ParseException {
		return wrapWithPrologAndEpilog(ImportDecl());
	}

	@Override
	protected BUTree<? extends STypeDecl> parseTypeDecl() throws ParseException {
		return wrapWithPrologAndEpilog(TypeDecl());
	}

	@Override
	protected BUTree<? extends SMemberDecl> parseMemberDecl(ParserInterface.TypeKind kind) throws ParseException {
		return wrapWithPrologAndEpilog(ClassOrInterfaceBodyDecl(kind));
	}

	@Override
	protected BUTree<? extends SMemberDecl> parseAnnotationMemberDecl() throws ParseException {
		return wrapWithPrologAndEpilog(AnnotationTypeBodyDecl());
	}

	@Override
	protected BUTree<SNodeList> parseModifiers() throws ParseException {
		return Modifiers();
	}

	@Override
	protected BUTree<SNodeList> parseAnnotations() throws ParseException {
		return Annotations();
	}

	@Override
	protected BUTree<SMethodDecl> parseMethodDecl() throws ParseException {
		run();
		BUTree<SNodeList> modifiers = Modifiers();
		return wrapWithPrologAndEpilog(MethodDecl(modifiers));
	}

	@Override
	protected BUTree<SFieldDecl> parseFieldDecl() throws ParseException {
		run();
		BUTree<SNodeList> modifiers = Modifiers();
		return wrapWithPrologAndEpilog(FieldDecl(modifiers));
	}

	@Override
	protected BUTree<SAnnotationMemberDecl> parseAnnotationElementDecl() throws ParseException {
		run();
		BUTree<SNodeList> modifiers = Modifiers();
		return wrapWithPrologAndEpilog(AnnotationTypeMemberDecl(modifiers));
	}

	@Override
	protected BUTree<SEnumConstantDecl> parseEnumConstantDecl() throws ParseException {
		return wrapWithPrologAndEpilog(EnumConstantDecl());
	}

	@Override
	protected BUTree<SFormalParameter> parseFormalParameter() throws ParseException {
		return wrapWithPrologAndEpilog(FormalParameter());
	}

	@Override
	protected BUTree<STypeParameter> parseTypeParameter() throws ParseException {
		return wrapWithPrologAndEpilog(TypeParameter());
	}

	@Override
	protected BUTree<SNodeList> parseStatements() throws ParseException {
		return Statements();
	}

	@Override
	protected BUTree<? extends SStmt> parseStatement() throws ParseException {
		return wrapWithPrologAndEpilog(BlockStatement());
	}

	@Override
	protected BUTree<? extends SExpr> parseExpression() throws ParseException {
		return wrapWithPrologAndEpilog(Expression());
	}

	@Override
	protected BUTree<? extends SType> parseType() throws ParseException {
		run();
		final BUTree<SNodeList> annotations = Annotations();
		return wrapWithPrologAndEpilog(Type(annotations));
	}

	@Override
	protected BUTree<SQualifiedName> parseQualifiedName() throws ParseException {
		return wrapWithPrologAndEpilog(QualifiedName());
	}

	@Override
	protected BUTree<SName> parseName() throws ParseException {
		return wrapWithPrologAndEpilog(Name());
	}

	private <S extends STree> BUTree<S> wrapWithPrologAndEpilog(BUTree<S> tree) throws ParseException {
		Epilog();
		return dressWithPrologAndEpilog(tree);
	}

	static class TokenBase {

		int realKind = ParserImplConstants.GT;
		WTokenRun whitespace;
	}

	// Convenience methods for lists

	protected BUTree<SNodeList> emptyList() {
		return new BUTree<SNodeList>(new SNodeList());
	}

	protected BUTree<SNodeList> singletonList(BUTree<?> element) {
		return new BUTree<SNodeList>(new SNodeList(element));
	}

	protected BUTree<SNodeList> append(BUTree<SNodeList> list, BUTree<?> element) {
		return list == null ? singletonList(element) :
				list.withState(list.state.withChildren(list.state.children.append(element)));
	}

	protected boolean contains(BUTree<SNodeList> list, BUTree<?> element) {
		return list.state.children.indexOf(element) != -1;
	}

	protected BUTree<SNodeList> ensureNotNull(BUTree<SNodeList> list) {
		return list == null ? emptyList() : list;
	}

	protected BUTree<SNodeOption> optionOf(BUTree<?> element) {
		return new BUTree<SNodeOption>(new SNodeOption(element));
	}

	protected BUTree<SNodeOption> none() {
		return new BUTree<SNodeOption>(new SNodeOption(null));
	}

	protected BUTree<SNodeEither> left(BUTree<?> element) {
		return new BUTree<SNodeEither>(new SNodeEither(element, SNodeEither.EitherSide.Left));
	}

	protected BUTree<SNodeEither> right(BUTree<?> element) {
		return new BUTree<SNodeEither>(new SNodeEither(element, SNodeEither.EitherSide.Right));
	}

	// Convenience class to get more data from a called production

	protected static class ByRef<T> {
		public T value;

		public ByRef(T value) {
			this.value = value;
		}
	}

	boolean isLambda() {
		for (int lookahead = 1; ; lookahead++) {
			int kind = getToken(lookahead).kind;
			switch (kind) {
				case LPAREN:
					// ( after ( => Expr
					return false;
				case RPAREN:
					if (lookahead == 1) return true;
					else return getToken(lookahead + 1).kind == ARROW;
			}
		}
	}

	// An fruitless attempt to do better lookahead than LOOKAHEAD(CastExpression())
	/*boolean isCast(boolean readOpenParenthesis) {
		int lookahead = 1;
		if (!readOpenParenthesis) {
			if (getToken(1).kind != LPAREN) return false;
			lookahead++;
		}

		int ltCount = 0;
		int gtCount = 0;
		boolean hadPrimitiveType = false;
		for (; ; lookahead++) {
			int kind = getToken(lookahead).kind;
			switch (kind) {
				case LT:
					ltCount++;
					break;
				case GT:
					gtCount++;
					break;
				case COMMA:
					if (ltCount - gtCount == 0) return false;
					break;

				case HOOK: // May be wildcard
					break;

				case ASSIGN:
				case BANG:
				case TILDE:
				case COLON:
				case EQ:
				case LE:
				case GE:
				case NE:
				case SC_OR:
				case SC_AND:
				case INCR:
				case DECR:
				case PLUS:
				case MINUS:
				case STAR:
				case SLASH:
				case BIT_OR:
				case XOR:
				case REM:
				case LSHIFT:
				case PLUSASSIGN:
				case MINUSASSIGN:
				case STARASSIGN:
				case SLASHASSIGN:
				case ANDASSIGN:
				case ORASSIGN:
				case XORASSIGN:
				case REMASSIGN:
				case LSHIFTASSIGN:
				case RSIGNEDSHIFTASSIGN:
				case RUNSIGNEDSHIFTASSIGN:
				case ELLIPSIS:
				case ARROW:
				case DOUBLECOLON:
				case INSTANCEOF:
				case NULL:
				case TRUE:
				case FALSE:
				case CHARACTER_LITERAL:
				case INTEGER_LITERAL:
				case LONG_LITERAL:
				case FLOAT_LITERAL:
				case DOUBLE_LITERAL:
				case STRING_LITERAL:

				case CLASS:
				case THIS:
				case SUPER:
					return false;

				case BOOLEAN:
				case CHAR:
				case BYTE:
				case SHORT:
				case INT:
				case LONG:
				case FLOAT:
				case DOUBLE:
					if (ltCount == 0) hadPrimitiveType = true;
					break;
				case LPAREN:
					// ( after ( => Expr
					return false;
				case RPAREN:
					int nextKind = getToken(lookahead + 1).kind;
					if (hadPrimitiveType) return true;
					else switch (nextKind) {
						case ASSIGN:
						case BANG:
						case TILDE:
						case HOOK:
						case COLON:
						case EQ:
						case LE:
						case GE:
						case NE:
						case SC_OR:
						case SC_AND:
						case INCR:
						case DECR:
						case PLUS:
						case MINUS:
						case STAR:
						case SLASH:
						case BIT_OR:
						case XOR:
						case REM:
						case LSHIFT:
						case PLUSASSIGN:
						case MINUSASSIGN:
						case STARASSIGN:
						case SLASHASSIGN:
						case ANDASSIGN:
						case ORASSIGN:
						case XORASSIGN:
						case REMASSIGN:
						case LSHIFTASSIGN:
						case RSIGNEDSHIFTASSIGN:
						case RUNSIGNEDSHIFTASSIGN:
						case ELLIPSIS:
						case ARROW:
						case DOUBLECOLON:
						case DOT:
							return false;
						default:
							return true;
					}
			}
		}
	}*/
}
