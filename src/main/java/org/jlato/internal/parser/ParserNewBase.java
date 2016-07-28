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

package org.jlato.internal.parser;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.coll.SNodeEither;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.bu.decl.*;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.bu.type.SType;
import org.jlato.internal.bu.type.SUnknownType;
import org.jlato.parser.*;

import java.io.*;
import java.util.LinkedList;

import static org.jlato.parser.ParserImplConstants.ARROW;
import static org.jlato.parser.ParserImplConstants.LPAREN;
import static org.jlato.parser.ParserImplConstants.RPAREN;

/**
 * @author Didier Villevalois
 */
public abstract class ParserNewBase extends ParserInterface {

	public static class ParserNewFactory implements ParserInterface.Factory {
		@Override
		public ParserInterface newInstance(InputStream in, String encoding) {
			ParserImplementation parser = new ParserImplementation();
			parser.reset(in, encoding);
			return parser;
		}

		@Override
		public ParserInterface newInstance(Reader in) {
			ParserImplementation parser = new ParserImplementation();
			parser.reset(in);
			return parser;
		}
	}

	private final Lexer lexer;

	public ParserNewBase() {
		lexer = new Lexer();
		lexer.setParser(this);
	}

	protected void reset(InputStream inputStream, String encoding) {
		try {
			lexer.yyreset(new InputStreamReader(inputStream, encoding));
		} catch (UnsupportedEncodingException e) {
			// TODO Fix error management
			throw new IllegalArgumentException(e);
		}
	}

	protected void reset(Reader reader) {
		lexer.yyreset(reader);
		lookaheadTokens.clear();
		matchLookahead = 0;
	}

	// Parser interface

	@Override
	protected BUTree<? extends SMemberDecl> parseMemberDecl(TypeKind kind) throws ParseException {
		return parseClassOrInterfaceBodyDecl(kind);
	}

	@Override
	protected BUTree<? extends SMemberDecl> parseAnnotationMemberDecl() throws ParseException {
		return parseAnnotationTypeBodyDecl();
	}

	@Override
	protected BUTree<SMethodDecl> parseMethodDecl() throws ParseException {
		run();
		return parseMethodDecl(parseModifiers());
	}

	@Override
	protected BUTree<SFieldDecl> parseFieldDecl() throws ParseException {
		run();
		return parseFieldDecl(parseModifiers());
	}

	@Override
	protected BUTree<SAnnotationMemberDecl> parseAnnotationElementDecl() throws ParseException {
		run();
		return parseAnnotationTypeMemberDecl(parseModifiers());
	}

	@Override
	protected BUTree<? extends SType> parseType() throws ParseException {
		run();
		return parseType(parseAnnotations());
	}

	protected abstract BUTree<? extends SMemberDecl> parseClassOrInterfaceBodyDecl(TypeKind kind) throws ParseException;

	protected abstract BUTree<? extends SMemberDecl> parseAnnotationTypeBodyDecl() throws ParseException;

	protected abstract BUTree<SAnnotationMemberDecl> parseAnnotationTypeMemberDecl(BUTree<SNodeList> modifiers) throws ParseException;

	protected abstract BUTree<SMethodDecl> parseMethodDecl(BUTree<SNodeList> modifiers) throws ParseException;

	protected abstract BUTree<SFieldDecl> parseFieldDecl(BUTree<SNodeList> modifiers) throws ParseException;

	protected abstract BUTree<? extends SType> parseType(BUTree<SNodeList> annotations) throws ParseException;

	// Temporarily fake methods


	protected void run() {
	}

	protected void lateRun() {
	}

	protected void popNewWhitespaces() {
	}

	protected <S extends STree> BUTree<S> dress(BUTree<S> tree) {
		return doCollectProblems(tree);
	}

	protected <S extends STree> BUTree<S> dressWithPrologAndEpilog(BUTree<S> tree) {
		return tree;
	}

	// TODO This should be handled through BUTree.validate() ?
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

	// Base parse methods

	private LinkedList<Token> lookaheadTokens = new LinkedList<Token>();
	protected int matchLookahead;

	private void advance(int index) {
		try {
			for (int i = lookaheadTokens.size(); i <= index; i++) {
				Token token = getNextToken();
				lookaheadTokens.add(token);
			}
		} catch (IOException e) {
			// TODO Fix error management
			throw new IllegalArgumentException(e);
		}
	}

	private Token getNextToken() throws IOException {
		Token token;
		if (!configuration.preserveWhitespaces) {
			do {
				token = lexer.yylex();
				switch (token.kind) {
					case ParserImplConstants.MULTI_LINE_COMMENT:
					case ParserImplConstants.SINGLE_LINE_COMMENT:
					case ParserImplConstants.JAVA_DOC_COMMENT:
					case ParserImplConstants.WHITESPACE:
					case ParserImplConstants.NEWLINE:
						break;
					default:
						return token;
				}
			} while (true);
		}
		throw new IllegalStateException();
	}

	protected Token getToken(int index) {
		advance(index);
		return lookaheadTokens.get(index);
	}

	protected boolean backupLookahead(boolean match) {
		try {
			return match;
		} finally {
			matchLookahead = 0;
		}
	}

	protected Token parse(int tokenType) throws ParseException {
		Token token = getToken(0);
		if (token.kind != tokenType) {
			String found = token.kind == ParserImplConstants.EOF ? "<EOF>" : token.image;
			String expected = ParserImplConstants.tokenImage[tokenType];
			throw new ParseException("Found " + found + " – Expected " + expected +
					" (" + (token.beginLine + 1) + ":" + (token.beginColumn + 1) + ")");
		}
		lookaheadTokens.remove(0);
		return token;
	}

	protected int match(int lookahead, int tokenType) {
		return getToken(lookahead).kind == tokenType ? lookahead + 1 : -1;
	}

	protected int match(int lookahead, int... tokenTypes) {
		for (int tokenType : tokenTypes) {
			if (getToken(lookahead).kind == tokenType) return lookahead + 1;
		}
		return -1;
	}

	BUTree<SFormalParameter> makeFormalParameter(BUTree<SName> name) {
		return SFormalParameter.make(emptyList(), SUnknownType.make(), false,
				SVariableDeclaratorId.make(name, emptyList())
		);
	}

	protected <S extends STree> BUTreeVar<S> makeVar() {
		String image = getToken(0).image;
		boolean nodeListVar = image.startsWith("..$");
		String name = nodeListVar ? image.substring(3) : image.substring(1);
		return BUTreeVar.var(name);
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
}
