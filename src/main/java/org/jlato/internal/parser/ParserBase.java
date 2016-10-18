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

import java.io.*;
import java.util.*;

import static org.jlato.internal.parser.TokenType.*;

/**
 * @author Didier Villevalois
 */
public abstract class ParserBase {

	private final Lexer lexer;
	protected ParserConfiguration configuration;
	protected boolean quotesMode = false;
	private boolean preserveWhitespaces;

	public ParserBase() {
		lexer = new Lexer();
		lexer.setParser(this);
	}

	protected final void configure(ParserConfiguration configuration, boolean quotesMode) {
		this.configuration = configuration;
		this.quotesMode = quotesMode;
		this.preserveWhitespaces = configuration.preserveWhitespaces;
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
		lookaheadCells.clear();
		matchLookahead = 0;

		if (preserveWhitespaces) {
			lastProcessedToken = -1;
			runStack.clear();
			runStack.push(Vector.<WTokenRun>empty());
		}

		resetStats();
	}

	// Parser interface

	protected abstract BUTree<SCompilationUnit> parseCompilationUnitEntry() throws ParseException;

	protected abstract BUTree<SPackageDecl> parsePackageDeclEntry() throws ParseException;

	protected abstract BUTree<SImportDecl> parseImportDeclEntry() throws ParseException;

	protected abstract BUTree<? extends STypeDecl> parseTypeDeclEntry() throws ParseException;

	protected abstract BUTree<? extends SMemberDecl> parseMemberDeclEntry(TypeKind kind) throws ParseException;

	protected abstract BUTree<? extends SMemberDecl> parseAnnotationMemberDeclEntry() throws ParseException;

	protected abstract BUTree<SNodeList> parseModifiersEntry() throws ParseException;

	protected abstract BUTree<SNodeList> parseAnnotationsEntry() throws ParseException;

	protected abstract BUTree<SMethodDecl> parseMethodDeclEntry() throws ParseException;

	protected abstract BUTree<SFieldDecl> parseFieldDeclEntry() throws ParseException;

	protected abstract BUTree<SAnnotationMemberDecl> parseAnnotationElementDeclEntry() throws ParseException;

	protected abstract BUTree<SEnumConstantDecl> parseEnumConstantDeclEntry() throws ParseException;

	protected abstract BUTree<SFormalParameter> parseFormalParameterEntry() throws ParseException;

	protected abstract BUTree<STypeParameter> parseTypeParameterEntry() throws ParseException;

	protected abstract BUTree<SNodeList> parseStatementsEntry() throws ParseException;

	protected abstract BUTree<? extends SStmt> parseBlockStatementEntry() throws ParseException;

	protected abstract BUTree<? extends SExpr> parseExpressionEntry() throws ParseException;

	protected abstract BUTree<? extends SType> parseTypeEntry() throws ParseException;

	protected abstract BUTree<SQualifiedName> parseQualifiedNameEntry() throws ParseException;

	protected abstract BUTree<SName> parseNameEntry() throws ParseException;

	// Lexical preservation mechanism

	private Stack<IndexedList<WTokenRun>> runStack = new Stack<IndexedList<WTokenRun>>();
	private int lastProcessedToken;

	protected void run() {
		if (!preserveWhitespaces) return;

		pushWhitespace(0);
		runStack.push(Vector.<WTokenRun>empty());
	}

	protected void lateRun() {
		if (!preserveWhitespaces) return;

		runStack.push(Vector.<WTokenRun>empty());
		pushWhitespace(0);
	}

	protected void popNewWhitespaces(int count) {
		if (!preserveWhitespaces) return;

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
		if (!preserveWhitespaces) return tree;

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
		if (!preserveWhitespaces) return tree;

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

	protected int memoizedProductionCount() {
		return 0;
	}

	private final int memoizedProductionCount = memoizedProductionCount();
	private final short[] emptyMatches = new short[memoizedProductionCount()];

	{
		for (int i = 0; i < memoizedProductionCount; i++) {
			emptyMatches[i] = -2;
		}
	}

	static class LookaheadCell {
		public Token token;
		public WTokenRun whitespace;
		public short[] matches;
	}

	private CircularBuffer<LookaheadCell> lookaheadCells = new CircularBuffer<LookaheadCell>(20, 10) {
		@Override
		protected LookaheadCell createCell() {
			LookaheadCell cell = new LookaheadCell();
			cell.matches = new short[memoizedProductionCount];
			return cell;
		}

		@Override
		protected void clearCell(LookaheadCell cell) {
			cell.token = null;
			cell.whitespace = null;
			System.arraycopy(emptyMatches, 0, cell.matches, 0, memoizedProductionCount);
		}
	};
	protected int matchLookahead;

	private void advance(int index) {
		try {
			for (int i = lookaheadCells.size(); i <= index; i++) {
				pushNextToken();
			}
		} catch (IOException e) {
			// TODO Fix error management
			throw new IllegalArgumentException(e);
		}
	}

	private void pushNextToken() throws IOException {
		if (preserveWhitespaces) {
			Token token;
			WTokenRun.Builder builder = new WTokenRun.Builder();
			do {
				token = lexer.yylex();

				switch (token.kind) {
					case TokenType.MULTI_LINE_COMMENT:
					case TokenType.SINGLE_LINE_COMMENT:
					case TokenType.JAVA_DOC_COMMENT:
					case TokenType.WHITESPACE:
					case TokenType.NEWLINE:
						builder.add(new WToken(token.kind, token.image));
						break;
					default:
						LookaheadCell cell = lookaheadCells.add();
						cell.token = token;
						cell.whitespace = builder.build();
						return;
				}
			} while (true);
		} else {
			Token token;
			do {
				token = lexer.yylex();

				switch (token.kind) {
					case TokenType.MULTI_LINE_COMMENT:
					case TokenType.SINGLE_LINE_COMMENT:
					case TokenType.JAVA_DOC_COMMENT:
					case TokenType.WHITESPACE:
					case TokenType.NEWLINE:
						break;
					default:
						LookaheadCell cell = lookaheadCells.add();
						cell.token = token;
						return;
				}
			} while (true);
		}
	}

	protected Token getToken(int index) {
		advance(index);
		return lookaheadCells.get(index).token;
	}

	protected WTokenRun getWhitespace(int index) {
		advance(index);
		return lookaheadCells.get(index).whitespace;
	}

	protected Token parse(int tokenType) throws ParseException {
		if (preserveWhitespaces) {
			pushWhitespace(0);
			lastProcessedToken--;
		}

		if (STATISTICS) validateMatches();

		Token token = getToken(0);
		if (token.kind != tokenType) {
			String found = token.kind == TokenType.EOF ? "<EOF>" : token.image;
			String expected = TokenType.tokenImage[tokenType];
			throw new ParseException("Found " + found + " – Expected " + expected +
					" (" + token.beginLine + ":" + token.beginColumn + ")");
		}
		lookaheadCells.dropHead();

		return token;
	}

	protected int match(int lookahead, int tokenType) {
		if (STATISTICS) {
			historizeMatches(new int[]{tokenType});
			matchCount++;
		}

		return getToken(lookahead).kind == tokenType ? lookahead + 1 : -1;
	}

	protected int match(int lookahead, int... tokenTypes) {
		if (STATISTICS) historizeMatches(tokenTypes);

		for (int tokenType : tokenTypes) {
			if (STATISTICS) matchCount++;

			if (getToken(lookahead).kind == tokenType) return lookahead + 1;
		}
		return -1;
	}

	protected int memoizeMatch(int lookahead, int productionNumber, int match) {
		advance(lookahead);
		lookaheadCells.get(lookahead).matches[productionNumber] = match == -1 ? -1 : (short) (match - lookahead);
		return match;
	}

	protected int memoizedMatch(int lookahead, int productionNumber) {
		advance(lookahead);
		short match = lookaheadCells.get(lookahead).matches[productionNumber];

		if (STATISTICS) {
			if (match > -2) hit(productionNumber);
			else miss(productionNumber);
			call(productionNumber);
		}

		return match < 0 ? match : lookahead + match;
	}

	private final static boolean STATISTICS = false;

	private void resetStats() {
		matchCount = 0;
	}

	private int totalMatchCount = 0;
	private int matchCount = 0;
	private int maxMatchCount = -1;
	private int[] allMatchCounts = new int[0];

	private List<String> matchHistory = new LinkedList<String>();

	protected void historize(String matchMethod) {
		matchHistory.add(matchMethod);
	}

	protected void historizeMatches(int[] matches) {
		StringBuilder builder = new StringBuilder();
		builder.append("  >> ");
		for (int match : matches) {
			builder.append(TokenType.tokenImage[match] + " ");
		}
		historize(builder.toString());
	}

	protected void validateMatches() {
		// Stats
		if (matchCount == 505) {
			System.out.println();
			Token token = lookaheadCells.get(0).token;
			System.out.println("At " + token.beginLine + ":" + token.beginColumn);
			System.out.print(matchCount + ": ");
			dumpTokens();
			dumpMatchHistory();
		}

		matchHistory.clear();

		if (matchCount > maxMatchCount) {
			int[] newMatchCounts = new int[matchCount + 1];
			System.arraycopy(allMatchCounts, 0, newMatchCounts, 0, maxMatchCount + 1);
			allMatchCounts = newMatchCounts;
			maxMatchCount = matchCount;
		}
		allMatchCounts[matchCount]++;
		totalMatchCount += matchCount;
		matchCount = 0;
	}

	private void dumpMatchHistory() {
		int indent = 0;
		for (String matches : matchHistory) {
			if (matches.startsWith("Out")) indent--;
			System.out.println(indent(indent) + matches);
			if (matches.startsWith("In")) indent++;
		}
	}

	private String indent(int indent) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < indent; i++) {
			builder.append(" ");
		}
		return builder.toString();
	}

	private void dumpTokens() {
		int count = lookaheadCells.size();
		for (int i = 0; i < count; i++) {
			System.out.print(lookaheadCells.get(i).token.toString() + " ");
		}
		System.out.println();
	}

	private void hit(int productionNumber) {
		Integer hits = hitStats.get(productionNumber);
		hitStats.put(productionNumber, hits == null ? 1 : hits + 1);
	}

	private void miss(int productionNumber) {
		Integer misses = missStats.get(productionNumber);
		missStats.put(productionNumber, misses == null ? 1 : misses + 1);
	}

	private void call(int productionNumber) {
		Integer calls = callStats.get(productionNumber);
		callStats.put(productionNumber, calls == null ? 1 : calls + 1);
	}

	public void clearStats() {
	}

	public void printStats() {
		if (STATISTICS) {
			System.out.println("Total match count: " + totalMatchCount);
			for (int i = 0; i <= maxMatchCount; i++) {
				int count = allMatchCounts[i];
				if (count != 0) System.out.println("" + i + " matches: " + count + " times");
			}

			java.util.ArrayList<Map.Entry<Integer, Integer>> hits = new java.util.ArrayList<Map.Entry<Integer, Integer>>(hitStats.entrySet());
			java.util.ArrayList<Map.Entry<Integer, Integer>> misses = new java.util.ArrayList<Map.Entry<Integer, Integer>>(missStats.entrySet());
			Comparator<Map.Entry<Integer, Integer>> comparator = new Comparator<Map.Entry<Integer, Integer>>() {
				@Override
				public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
					return -o1.getValue().compareTo(o2.getValue());
				}
			};
			Collections.sort(hits, comparator);
			Collections.sort(misses, comparator);

			System.out.println("Productions covered: " + callStats.size());
			System.out.println("Hits: ");
			for (Map.Entry<Integer, Integer> hit : hits) {
				System.out.println("Production " + hit.getKey() +
						" - hits: " + hit.getValue() +
						" - misses: " + missStats.get(hit.getKey()) +
						" - calls: " + callStats.get(hit.getKey()));
			}
			System.out.println("Misses: ");
			for (Map.Entry<Integer, Integer> miss : misses) {
				System.out.println("Production " + miss.getKey() +
						" - misses: " + miss.getValue() +
						" - hits: " + hitStats.get(miss.getKey()) +
						" - calls: " + callStats.get(miss.getKey()));
			}
		}
	}

	public Map<Integer, Integer> hitStats = new java.util.HashMap<Integer, Integer>();
	public Map<Integer, Integer> missStats = new java.util.HashMap<Integer, Integer>();
	public Map<Integer, Integer> callStats = new java.util.HashMap<Integer, Integer>();

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
		return SFormalParameter.make(emptyList(), SUnknownType.make(), false, emptyList(),
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
				initialize(tail, newCapacity);
			}
		}
	}
}
