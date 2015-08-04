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
import org.jlato.internal.bu.decl.*;
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.bu.stmt.SStmt;
import org.jlato.internal.bu.type.SType;
import org.jlato.internal.shapes.DressingBuilder;
import org.jlato.internal.shapes.LexicalShape;

import java.io.InputStream;
import java.io.Reader;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

import static org.jlato.parser.ParserImplConstants.*;

/**
 * @author Didier Villevalois
 */
abstract class ParserBase {

	public static ParserImpl newInstance(InputStream in, String encoding, ParserConfiguration configuration) {
		ParserImpl parser = new ParserImpl(in, encoding);
		parser.configure(configuration);
		parser.reset();
		return parser;
	}

	public static ParserImpl newInstance(Reader in, ParserConfiguration configuration) {
		ParserImpl parser = new ParserImpl(in);
		parser.configure(configuration);
		parser.reset();
		return parser;
	}

	protected void configure(ParserConfiguration configuration) {
		this.configuration = configuration;
	}

	protected ParserConfiguration configuration;

	protected boolean quotesMode = false;
	private Stack<IndexedList<WTokenRun>> runStack = new Stack<IndexedList<WTokenRun>>();
	private Token lastProcessedToken;

	public ParserBase() {
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

	protected <S extends STreeState> BUTree<S> dress(BUTree<S> tree) {
		if (!configuration.preserveWhitespaces) return tree;

		try {
			final IndexedList<WTokenRun> tokens = popTokens();

			if (tree == null) return null;

			final LexicalShape shape = tree.state.shape();
			return doDress(tree, shape, tokens);

		} catch (EmptyStackException e) {
			debugFailedPopTokens();
			throw e;
		} catch (IllegalStateException e) {
			debugFailedPopTokens();
			throw e;
		}
	}

	private <S extends STreeState> BUTree<S> doDress(BUTree<S> tree, LexicalShape shape,
	                                                IndexedList<WTokenRun> tokens) {
		try {
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

		} catch (NoSuchElementException e) {
			debugFailedEnRun(tree, shape, tokens);
			throw e;
		}
	}

	protected <S extends STreeState> BUTree<S> dressWithPrologAndEpilog(BUTree<S> tree) {
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

	// TODO This is really dirty and temporary until the parser parses STrees directly
	protected <S extends STreeState> BUTree<S> makeVar() {
		Token token = getToken(0);
		pushWhitespace(token);

		String image = token.image;
		boolean nodeListVar = image.startsWith("..$");
		String name = nodeListVar ? image.substring(3) : image.substring(1);
		return (BUTree<S>) new BUTree<SVarState>(new SVarState(name));
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

	abstract BUTree<SNodeListState> Modifiers() throws ParseException;

	abstract BUTree<SMethodDecl> MethodDecl(BUTree<SNodeListState> modifiers) throws ParseException;

	abstract BUTree<SFieldDecl> FieldDecl(BUTree<SNodeListState> modifiers) throws ParseException;

	abstract BUTree<SAnnotationMemberDecl> AnnotationTypeMemberDecl(BUTree<SNodeListState> modifiers) throws ParseException;

	abstract BUTree<SEnumConstantDecl> EnumConstantDecl() throws ParseException;

	abstract BUTree<SFormalParameter> FormalParameter() throws ParseException;

	abstract BUTree<STypeParameter> TypeParameter() throws ParseException;

	abstract BUTree<? extends SStmt> BlockStatement() throws ParseException;

	abstract BUTree<? extends SExpr> Expression() throws ParseException;

	abstract BUTree<SNodeListState> Annotations() throws ParseException;

	abstract BUTree<? extends SType> Type(BUTree<SNodeListState> annotations) throws ParseException;

	public enum TypeKind {
		Empty,
		Class,
		Interface,
		Enum,
		AnnotationType,
		// Keep last comma
	}

	static class TokenBase {

		int realKind = ParserImplConstants.GT;
		WTokenRun whitespace;
	}

	// Convenience methods for lists

	protected BUTree<SNodeListState> emptyList() {
		return new BUTree<SNodeListState>(new SNodeListState());
	}

	protected BUTree<SNodeListState> singletonList(BUTree<?> element) {
		return new BUTree<SNodeListState>(new SNodeListState(element));
	}

	protected BUTree<SNodeListState> append(BUTree<SNodeListState> list, BUTree<?> element) {
		return list == null ? singletonList(element) :
				list.withState(list.state.withChildren(list.state.children.append(element)));
	}

	protected boolean contains(BUTree<SNodeListState> list, BUTree<?> element) {
		return list.state.children.indexOf(element) != -1;
	}

	protected BUTree<SNodeListState> ensureNotNull(BUTree<SNodeListState> list) {
		return list == null ? emptyList() : list;
	}

	protected BUTree<SNodeOptionState> optionOf(BUTree<?> element) {
		return new BUTree<SNodeOptionState>(new SNodeOptionState(element));
	}

	protected BUTree<SNodeOptionState> none() {
		return new BUTree<SNodeOptionState>(new SNodeOptionState(null));
	}

	protected BUTree<SNodeEitherState> left(BUTree<?> element) {
		return new BUTree<SNodeEitherState>(new SNodeEitherState(element, SNodeEitherState.EitherSide.Left));
	}

	protected BUTree<SNodeEitherState> right(BUTree<?> element) {
		return new BUTree<SNodeEitherState>(new SNodeEitherState(element, SNodeEitherState.EitherSide.Right));
	}

	// Convenience class to get more data from a called production

	static class ByRef<T> {
		public T value;

		public ByRef() {
		}

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

	boolean isLambdaOrExpr(int lookaheadStart) {
		for (int lookahead = lookaheadStart; ; lookahead++) {
			int kind = getToken(lookahead).kind;
			switch (kind) {
				case LPAREN:
					// ( => Expr
					return true;
				case RPAREN:
					return getToken(lookahead + 1).kind == ARROW;
			}
		}
	}

	// Debug methods

	private void debugFailedPopTokens() {
		System.out.println("Error at location: " + getToken(0).beginLine + ", " + getToken(0).beginColumn);
		System.out.println("Failed to pop tokens !");
	}

	private void debugFailedEnRun(BUTree tree, LexicalShape shape, IndexedList<WTokenRun> tokens) {
		final Token token = getToken(0);
		System.out.println("Error at location: " + token.beginLine + ", " + token.beginColumn + "; Token: " + token.image);

		System.out.print("Failed to enRun tokens: ");
		System.out.println(tokens);

		System.out.println("For tree of kind: " + ((SNodeState) tree.state).kind());

		final STreeState state = tree.state;

/*
		Iterable<? extends STree> children =
				state instanceof SNodeListState ? ((SNodeListState) state).children :
						state instanceof SNodeState ? ((SNodeState) state).children :
								Vector.<STree>empty();
		System.out.println("With children: ");
		int index = 0;
		for (STree child : children) {
			System.out.print("  " + index + " - ");
			if (child == null) {
				System.out.println("null");
			} else {
				System.out.print(child.kind + ": ");
				System.out.println(Printer.printToString(child.asTree()));
			}
			index++;
		}
*/

		System.out.println("For shape: " + shape);
		dumpRunStack();
	}

	private void dumpRunStack() {
		System.out.println("RunStack dump:");
		for (int i = runStack.size() - 1; i >= 0; i--) {
			System.out.println("  " + runStack.get(i));
		}
	}

	public static boolean lContains(IndexedList<LToken> tokens, String str) {
		for (LToken token : tokens) {
			if (token.string.equals("/*" + str + "*/")) return true;
		}
		return false;
	}

	public static boolean llContains(IndexedList<IndexedList<LToken>> tokens, String str) {
		for (IndexedList<LToken> tokenList : tokens) {
			for (LToken token : tokenList) {
				if (token.string.contains("/*" + str + "*/")) return true;
			}
		}
		return false;
	}
}
