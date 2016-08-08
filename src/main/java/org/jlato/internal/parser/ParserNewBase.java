/*
 * Copyright (C) 2015-2016 Didier Villevalois.
 *
 * This file is part of JLaTo.
 *
 * JLaTo is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * JLaTo is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JLaTo.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.jlato.internal.parser;

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
import org.jlato.internal.bu.type.SUnknownType;
import org.jlato.internal.shapes.DressingBuilder;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.parser.ParseException;
import org.jlato.parser.ParserConfiguration;
import org.jlato.tree.expr.BinaryOp;

import java.io.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

import static org.jlato.internal.parser.TokenType.*;

/**
 * @author Didier Villevalois
 */
public abstract class ParserNewBase {

	private final Lexer lexer;
	protected ParserConfiguration configuration;
	protected boolean quotesMode = false;

	public ParserNewBase() {
		lexer = new Lexer();
		lexer.setParser(this);
	}

	protected final void configure(ParserConfiguration configuration, boolean quotesMode) {
		this.configuration = configuration;
		this.quotesMode = quotesMode;
	}

	protected void reset(InputStream inputStream, String encoding) {
		try {
			reset(new InputStreamReader(inputStream, encoding));
		} catch (UnsupportedEncodingException e) {
			// TODO Fix error management
			throw new IllegalArgumentException(e);
		}
	}

	protected void reset(Reader reader) {
		lexer.yyreset(reader);
		lookahead.clear();
		matchLookahead = 0;

		if (configuration.preserveWhitespaces) {
			lastProcessedToken = -1;
			runStack.clear();
			runStack.push(Vector.<WTokenRun>empty());
		}
	}

	// Parser interface

	protected abstract BUTree<SCompilationUnit> parseCompilationUnit() throws ParseException;

	protected abstract BUTree<SPackageDecl> parsePackageDecl() throws ParseException;

	protected abstract BUTree<SImportDecl> parseImportDecl() throws ParseException;

	protected abstract BUTree<? extends STypeDecl> parseTypeDecl() throws ParseException;

	protected abstract BUTree<? extends SMemberDecl> parseClassOrInterfaceBodyDecl(TypeKind kind) throws ParseException;

	protected abstract BUTree<? extends SMemberDecl> parseAnnotationTypeBodyDecl() throws ParseException;

	protected abstract BUTree<SNodeList> parseModifiers() throws ParseException;

	protected abstract BUTree<SMethodDecl> parseMethodDecl(BUTree<SNodeList> modifiers) throws ParseException;

	protected abstract BUTree<SFieldDecl> parseFieldDecl(BUTree<SNodeList> modifiers) throws ParseException;

	protected abstract BUTree<SAnnotationMemberDecl> parseAnnotationTypeMemberDecl(BUTree<SNodeList> modifiers) throws ParseException;

	protected abstract BUTree<SEnumConstantDecl> parseEnumConstantDecl() throws ParseException;

	protected abstract BUTree<SFormalParameter> parseFormalParameter() throws ParseException;

	protected abstract BUTree<STypeParameter> parseTypeParameter() throws ParseException;

	protected abstract BUTree<SNodeList> parseStatements(boolean inConstructor) throws ParseException;

	protected abstract BUTree<? extends SStmt> parseBlockStatement() throws ParseException;

	protected abstract BUTree<? extends SExpr> parseExpression() throws ParseException;

	protected abstract BUTree<SNodeList> parseAnnotations() throws ParseException;

	protected abstract BUTree<? extends SType> parseType(BUTree<SNodeList> annotations) throws ParseException;

	protected abstract BUTree<SQualifiedName> parseQualifiedName() throws ParseException;

	protected abstract BUTree<SName> parseName() throws ParseException;

	protected abstract void parseEpilog() throws ParseException;

	// Lexical preservation mechanism

	private Stack<IndexedList<WTokenRun>> runStack = new Stack<IndexedList<WTokenRun>>();
	private int lastProcessedToken;

	protected void run() {
		if (!configuration.preserveWhitespaces) return;

		pushWhitespace(0);
		runStack.push(Vector.<WTokenRun>empty());
	}

	protected void lateRun() {
		if (!configuration.preserveWhitespaces) return;

		runStack.push(Vector.<WTokenRun>empty());
		pushWhitespace(0);
	}

	protected void popNewWhitespaces(int count) {
		if (!configuration.preserveWhitespaces) return;

		IndexedList<WTokenRun> tokenRuns = runStack.pop();
		runStack.push(tokenRuns.take(tokenRuns.size() - count));
	}

	private void pushWhitespace(int upToToken) {
		for (int i = lastProcessedToken + 1; i <= upToToken; i++) {
			pushWhitespace(getWhitespace(i));
		}
		lastProcessedToken = upToToken;
	}

	private void pushWhitespace(WTokenRun whitespace) {
		if (whitespace == null) return;

		runStack.push(runStack.pop().append(whitespace));
	}

	private IndexedList<WTokenRun> popTokens() {
		pushWhitespace(-1);
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
			try {
				shape.dress(builder, tree);
			} catch (RuntimeException e) {
				System.out.println(getToken(0).beginLine + ":" + getToken(0).beginColumn);
				throw e;
			}
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

	protected <S extends STree> BUTree<S> wrapWithPrologAndEpilog(BUTree<S> tree) throws ParseException {
		parseEpilog();
		return dressWithPrologAndEpilog(tree);
	}

	// Problem propagation

	// TODO This should be handled through BUTree.validate() ?
	private <S extends STree> BUTree<S> doCollectProblems(BUTree<S> tree) {
		return tree;

//		S state = tree.state;
//		boolean hasProblem = false;
//
//		STraversal traversal = state.firstChild();
//		while (traversal != null) {
//			BUTree<?> child = traversal.traverse(state);
//
//			if (child != null && child.state != null) {
//				if (child.state.treeKind() != STree.STreeKind.Node) {
//					child = doCollectProblems(child);
//					if (child.hasProblems()) {
//						tree = tree.traverseReplace(traversal, child);
//					}
//				}
//				if (child.hasProblems()) hasProblem = true;
//			}
//
//			traversal = traversal.rightSibling(state);
//		}
//
//		if (hasProblem) tree = tree.setProblems();
//		return tree;
	}

	// Base parse methods

	static class LookaheadCell {
		public Token token;
		public WTokenRun whitespace;
	}

	private CircularBuffer<LookaheadCell> lookahead = new CircularBuffer<LookaheadCell>(20, 10) {
		@Override
		protected LookaheadCell createCell() {
			return new LookaheadCell();
		}

		@Override
		protected void clearCell(LookaheadCell cell) {
			cell.token = null;
			cell.whitespace = null;
		}
	};
	protected int matchLookahead;

	private void advance(int index) {
		try {
			for (int i = lookahead.size(); i <= index; i++) {
				pushNextToken();
			}
		} catch (IOException e) {
			// TODO Fix error management
			throw new IllegalArgumentException(e);
		}
	}

	private void pushNextToken() throws IOException {
		Token token;
		WTokenRun.Builder builder = configuration.preserveWhitespaces ? new WTokenRun.Builder() : null;
		do {
			token = lexer.yylex();

			switch (token.kind) {
				case TokenType.MULTI_LINE_COMMENT:
				case TokenType.SINGLE_LINE_COMMENT:
				case TokenType.JAVA_DOC_COMMENT:
				case TokenType.WHITESPACE:
				case TokenType.NEWLINE:
					if (configuration.preserveWhitespaces) builder.add(new WToken(token.kind, token.image));
					break;
				default:
					LookaheadCell cell = lookahead.add();
					cell.token = token;
					if (configuration.preserveWhitespaces) cell.whitespace = builder.build();
					return;
			}
		} while (true);
	}

	protected Token getToken(int index) {
		advance(index);
		return lookahead.get(index).token;
	}

	protected WTokenRun getWhitespace(int index) {
		advance(index);
		return lookahead.get(index).whitespace;
	}

	protected Token parse(int tokenType) throws ParseException {
		if (configuration.preserveWhitespaces) {
			pushWhitespace(0);
			lastProcessedToken--;
		}

		Token token = getToken(0);
		if (token.kind != tokenType) {
			String found = token.kind == TokenType.EOF ? "<EOF>" : token.image;
			String expected = TokenType.tokenImage[tokenType];
			throw new ParseException("Found " + found + " – Expected " + expected +
					" (" + token.beginLine + ":" + token.beginColumn + ")");
		}
		lookahead.dropHead();
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

	protected ParseException produceParseException(int... expectedTokenTypes) {
		String eol = System.getProperty("line.separator", "\n");
		StringBuffer expected = new StringBuffer();
		for (int i = 0; i < expectedTokenTypes.length; i++) {
			expected.append("\"").append(TokenType.tokenImage[expectedTokenTypes[i]]).append("\" ");
			expected.append("...");
			expected.append(eol).append("    ");
		}

		String retval = "Encountered \"";
		Token tok = getToken(0);

		if (tok.kind == 0) {
			retval += TokenType.tokenImage[0];
		} else {
			retval += " " + TokenType.tokenImage[tok.kind];
			retval += " \"";
			retval += add_escapes(tok.image);
			retval += " \"";
		}

		retval += "\" at line " + tok.beginLine + ", column " + tok.beginColumn;
		retval += "." + eol;
		if (expectedTokenTypes.length == 1) {
			retval += "Was expecting:" + eol + "    ";
		} else {
			retval += "Was expecting one of:" + eol + "    ";
		}

		retval += expected.toString();
		return new ParseException(retval);
	}

	BUTree<SFormalParameter> makeFormalParameter(BUTree<SName> name) {
		return SFormalParameter.make(emptyList(), SUnknownType.make(), false,
				optionOf(SVariableDeclaratorId.make(name, emptyList())), false, none()
		);
	}

	protected <S extends STree> BUTreeVar<S> makeVar(Token id) {
		String image = id.image;
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

	int precedenceForInstanceOf() {
		return 8;
	}

	int precedenceFor(BinaryOp op) {
		switch (op) {
			case Or:
				return 2;
			case And:
				return 3;
			case BinOr:
				return 4;
			case XOr:
				return 5;
			case BinAnd:
				return 6;
			case Equal:
			case NotEqual:
				return 7;

			// InstanceOf is 8

			case Less:
			case Greater:
			case LessOrEqual:
			case GreaterOrEqual:
				return 9;
			case LeftShift:
			case RightSignedShift:
			case RightUnsignedShift:
				return 10;
			case Plus:
			case Minus:
				return 11;
			case Times:
			case Divide:
			case Remainder:
				return 12;
			default:
				return -1;
		}
	}

	boolean leftAssociative(BinaryOp op) {
		return true;
	}

	boolean isLambda(int initialLookahead) {
		for (int lookahead = initialLookahead; ; lookahead++) {
			int kind = getToken(lookahead).kind;
			switch (kind) {
				case LPAREN:
					// ( after ( => Expr
					return false;
				case RPAREN:
					if (lookahead == initialLookahead) return true;
					else return getToken(lookahead + 1).kind == ARROW;
			}
		}
	}

	boolean isCast(int initialLookahead) {
		if (getToken(initialLookahead).kind != LPAREN) return false;

		int gtLtBalance = 0;
		for (int lookahead = initialLookahead + 1; ; lookahead++) {
			switch (getToken(lookahead).kind) {
				case LT:
					gtLtBalance++;
					break;
				case GT:
					gtLtBalance--;
					break;
				case LPAREN:
					// ( after ( => Expr
					return false;
				case RPAREN:
					if (gtLtBalance != 0) return false;
					else if (lookahead == initialLookahead + 1) return false; // Lambda
					else {
						switch (getToken(lookahead + 1).kind) {
							case LT:
							case GT:
							case ARROW:
							case HOOK:
							case EQ:
							case LE:
							case GE:
							case NE:
							case SC_OR:
							case SC_AND:
							case INCR:
							case DECR:
							case STAR:
							case SLASH:
							case BIT_AND:
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

							case SEMICOLON:
							case RPAREN:
								return false;

							case LPAREN:
							case IDENTIFIER:
							case THIS:
							case SUPER:
							case NEW:
							case NULL:

							case FALSE:
							case TRUE:
							case LONG_LITERAL:
							case INTEGER_LITERAL:
							case FLOAT_LITERAL:
							case DOUBLE_LITERAL:
							case CHARACTER_LITERAL:
							case STRING_LITERAL:
								return true;

							case BANG:
							case TILDE:
							case PLUS:
							case MINUS:
								if (lookahead == 2) {
									switch (getToken(initialLookahead + 1).kind) {
										case BOOLEAN:
										case BYTE:
										case CHAR:
										case DOUBLE:
										case FLOAT:
										case INT:
										case LONG:
										case SHORT:
											return true;
									}
								}
								break;
						}
					}
					break;

				case AT:
					return true;

				case ABSTRACT:
				case ASSERT:
				case BREAK:
				case CASE:
				case CATCH:
				case CLASS:
				case CONST:
				case CONTINUE:
				case DEFAULT:
				case DO:
				case ELSE:
				case ENUM:
				case FALSE:
				case FINAL:
				case FINALLY:
				case FOR:
				case GOTO:
				case IF:
				case IMPLEMENTS:
				case IMPORT:
				case INSTANCEOF:
				case INTERFACE:
				case NATIVE:
				case NEW:
				case NULL:
				case PACKAGE:
				case PRIVATE:
				case PROTECTED:
				case PUBLIC:
				case RETURN:
				case STATIC:
				case STRICTFP:
				case SWITCH:
				case SYNCHRONIZED:
				case THIS:
				case THROW:
				case THROWS:
				case TRANSIENT:
				case TRUE:
				case TRY:
				case VOID:
				case VOLATILE:
				case WHILE:
				case LONG_LITERAL:
				case INTEGER_LITERAL:
				case FLOAT_LITERAL:
				case DOUBLE_LITERAL:
				case CHARACTER_LITERAL:
				case STRING_LITERAL:
				case LBRACE:
				case RBRACE:
				case SEMICOLON:
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
					return false;

				case BOOLEAN:
				case BYTE:
				case CHAR:
				case DOUBLE:
				case FLOAT:
				case INT:
				case LONG:
				case SHORT:

				case LBRACKET:
				case RBRACKET:
				case HOOK:
				case EXTENDS:
				case SUPER:
				case BIT_AND:
					break;
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

	static String add_escapes(String str) {
		StringBuffer retval = new StringBuffer();
		char ch;
		for (int i = 0; i < str.length(); i++) {
			switch (str.charAt(i)) {
				case 0:
					continue;
				case '\b':
					retval.append("\\b");
					continue;
				case '\t':
					retval.append("\\t");
					continue;
				case '\n':
					retval.append("\\n");
					continue;
				case '\f':
					retval.append("\\f");
					continue;
				case '\r':
					retval.append("\\r");
					continue;
				case '\"':
					retval.append("\\\"");
					continue;
				case '\'':
					retval.append("\\\'");
					continue;
				case '\\':
					retval.append("\\\\");
					continue;
				default:
					if ((ch = str.charAt(i)) < 0x20 || ch > 0x7e) {
						String s = "0000" + Integer.toString(ch, 16);
						retval.append("\\u" + s.substring(s.length() - 4, s.length()));
					} else {
						retval.append(ch);
					}
					continue;
			}
		}
		return retval.toString();
	}

	static abstract class CircularBuffer<E> {
		private Object[] elementData;
		private int head;
		private int tail;
		private final int capacityIncrement;

		public CircularBuffer(int initialCapacity, int capacityIncrement) {
			elementData = new Object[initialCapacity];
			initialize(0, initialCapacity);
			head = 0;
			tail = 0;
			this.capacityIncrement = capacityIncrement;
		}

		private void initialize(int from, int to) {
			for (int i = from; i < to; i++) {
				elementData[i] = createCell();
			}
		}

		protected abstract E createCell();

		protected abstract void clearCell(E cell);

		public int size() {
			if (head <= tail) return tail - head;
			else return elementData.length - head + tail;
		}

		public void clear() {
			head = tail = 0;
		}

		@SuppressWarnings("unchecked")
		public void add(E value) {
			ensureCapacityForAppend();

			clearCell((E) elementData[tail]);
			elementData[tail] = value;

			tail++;
			if (tail == elementData.length) {
				tail = 0;
			}
		}

		@SuppressWarnings("unchecked")
		public E add() {
			ensureCapacityForAppend();

			E cell = (E) elementData[tail];
			clearCell(cell);

			tail++;
			if (tail == elementData.length) {
				tail = 0;
			}

			return cell;
		}

		public E dropHead() {
			if (head == tail) throw new NoSuchElementException();

			E element = elementData(head++);
			if (head == elementData.length) {
				head = 0;
			}
			return element;
		}

		public E get(int index) {
			int realIndex = head + index;
			if (head < tail) {
				if (realIndex < tail) {
					return elementData(realIndex);
				}
			} else {
				if (realIndex < elementData.length) {
					return elementData(realIndex);
				}
				realIndex = realIndex - elementData.length;
				if (realIndex < tail) {
					return elementData(realIndex);
				}
			}

			throw new NoSuchElementException();
		}

		@SuppressWarnings("unchecked")
		private E elementData(int index) {
			return (E) elementData[index];
		}

		private void ensureCapacityForAppend() {
			int currentCapacity = elementData.length;
			int newCapacity = currentCapacity + capacityIncrement;

			if (tail + 1 == head) {
				elementData = Arrays.copyOf(elementData, newCapacity);
				System.arraycopy(elementData, head, elementData, head + capacityIncrement, currentCapacity - head);
				head = head + capacityIncrement;
				initialize(tail, head);
			} else if (tail == currentCapacity - 1 && head == 0) {
				elementData = Arrays.copyOf(elementData, newCapacity);
				initialize(tail, elementData.length);
			}
		}
	}
}
