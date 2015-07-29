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
import org.jlato.internal.shapes.DressingBuilder;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.decl.*;
import org.jlato.tree.decl.TypeDecl.TypeKind;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.stmt.Stmt;
import org.jlato.tree.type.Type;

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
		return parser;
	}

	public static ParserImpl newInstance(Reader in, ParserConfiguration configuration) {
		ParserImpl parser = new ParserImpl(in);
		parser.configure(configuration);
		return parser;
	}

	protected void configure(ParserConfiguration configuration) {
		this.configuration = configuration;
	}

	protected ParserConfiguration configuration;

	protected boolean quotesMode = false;
	protected IndexedList<WTokenRun> preamble;
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
		preamble = Vector.empty();
	}

	protected void run() {
		if (!configuration.preserveWhitespaces) return;

		if (lastProcessedToken == null) {
			lastProcessedToken = getToken(0);
		}
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

		// TODO Handle root whitespace before first token better than with LSDump
		if (!runStack.isEmpty()) {
			runStack.push(runStack.pop().append(whitespace));
		} else {
			preamble = preamble.append(whitespace);
		}
	}

	private IndexedList<WTokenRun> popTokens() {
		pushWhitespace(getToken(0));
		return runStack.pop();
	}

	protected <S extends STreeState> STree<S> enRun(STree<S> tree) {
		if (!configuration.preserveWhitespaces) return tree;

		try {
			final IndexedList<WTokenRun> tokens = popTokens();

			if (tree == null) return null;

			final LexicalShape shape = tree.state.shape();
			return doEnRun(tree, shape, tokens);

		} catch (EmptyStackException e) {
			debugFailedPopTokens();
			throw e;
		} catch (IllegalStateException e) {
			debugFailedPopTokens();
			throw e;
		}
	}

	private <S extends STreeState> STree<S> doEnRun(STree<S> tree, LexicalShape shape,
	                                                IndexedList<WTokenRun> tokens) {
		try {
			final Iterator<WTokenRun> tokenIterator = tokens.iterator();
			final DressingBuilder<S> builder = new DressingBuilder<S>(tree, tokenIterator);
			shape.dress(builder, tree);
			final STree<S> newTree = builder.build();

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

	// TODO This is really dirty and temporary until the parser parses STrees directly
	protected <S extends STreeState> STree<S> makeVar(Token token) {
		String image = token.image;
		boolean nodeListVar = image.startsWith("..$");
		String name = nodeListVar ? image.substring(3) : image.substring(1);
		return (STree<S>) new STree<SVarState>(new SVarState(name));
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

	public abstract STree<CompilationUnit.State> CompilationUnit() throws ParseException;

	public abstract STree<PackageDecl.State> PackageDecl() throws ParseException;

	public abstract STree<ImportDecl.State> ImportDecl() throws ParseException;

	public abstract STree<? extends TypeDecl.State> TypeDecl() throws ParseException;

	public abstract STree<? extends MemberDecl.State> ClassOrInterfaceBodyDecl(TypeKind kind) throws ParseException;

	public abstract STree<? extends MemberDecl.State> AnnotationTypeBodyDecl() throws ParseException;

	public abstract STree<SNodeListState> Modifiers() throws ParseException;

	public abstract STree<MethodDecl.State> MethodDecl(STree<SNodeListState> modifiers) throws ParseException;

	public abstract STree<FieldDecl.State> FieldDecl(STree<SNodeListState> modifiers) throws ParseException;

	public abstract STree<AnnotationMemberDecl.State> AnnotationTypeMemberDecl(STree<SNodeListState> modifiers) throws ParseException;

	public abstract STree<EnumConstantDecl.State> EnumConstantDecl() throws ParseException;

	public abstract STree<FormalParameter.State> FormalParameter() throws ParseException;

	public abstract STree<TypeParameter.State> TypeParameter() throws ParseException;

	public abstract STree<? extends Stmt.State> BlockStatement() throws ParseException;

	public abstract STree<? extends Expr.State> Expression() throws ParseException;

	public abstract STree<SNodeListState> Annotations() throws ParseException;

	public abstract STree<? extends Type.State> Type(STree<SNodeListState> annotations) throws ParseException;

	static class TokenBase {

		int realKind = ParserImplConstants.GT;
		WTokenRun whitespace;
	}

	// Convenience methods for lists

	protected STree<SNodeListState> emptyList() {
		return new STree<SNodeListState>(new SNodeListState());
	}

	protected STree<SNodeListState> singletonList(STree<?> element) {
		return new STree<SNodeListState>(new SNodeListState(element));
	}

	protected STree<SNodeListState> append(STree<SNodeListState> list, STree<?> element) {
		return list == null ? singletonList(element) :
				list.withState(list.state.withChildren(list.state.children.append(element)));
	}

	protected boolean contains(STree<SNodeListState> list, STree<?> element) {
		return list.state.children.indexOf(element) != -1;
	}

	protected STree<SNodeListState> ensureNotNull(STree<SNodeListState> list) {
		return list == null ? emptyList() : list;
	}

	protected STree<SNodeOptionState> optionOf(STree<?> element) {
		return new STree<SNodeOptionState>(new SNodeOptionState(element));
	}

	protected STree<SNodeOptionState> none() {
		return new STree<SNodeOptionState>(new SNodeOptionState(null));
	}

	protected STree<SNodeEitherState> left(STree<?> element) {
		return new STree<SNodeEitherState>(new SNodeEitherState(element, SNodeEitherState.EitherSide.Left));
	}

	protected STree<SNodeEitherState> right(STree<?> element) {
		return new STree<SNodeEitherState>(new SNodeEitherState(element, SNodeEitherState.EitherSide.Right));
	}

	// Convenience class to get more data from a called production

	static class ByRef<T> {
		public T value;
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

	private void debugFailedEnRun(STree tree, LexicalShape shape, IndexedList<WTokenRun> tokens) {
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
