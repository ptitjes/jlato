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
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SContext;
import org.jlato.internal.td.SLocation;
import org.jlato.printer.Printer;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.AssignExpr;

import java.io.InputStream;
import java.io.Reader;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * @author Didier Villevalois
 */
abstract class ParserBase {

	public static ParserImpl newInstance(InputStream in, String encoding, ParserConfiguration configuration) {
		ParserImpl parser = new ParserImpl(in, encoding);
		parser.configuration = configuration;
		return parser;
	}

	public static ParserImpl newInstance(Reader in, ParserConfiguration configuration) {
		ParserImpl parser = new ParserImpl(in);
		parser.configuration = configuration;
		return parser;
	}

	protected ParserConfiguration configuration;

	private Stack<IndexedList<IndexedList<LToken>>> runStack = new Stack<IndexedList<IndexedList<LToken>>>();
	private Token lastProcessedToken;

	protected void reset() {
		lastProcessedToken = null;
		runStack.clear();
	}

	protected void run() {
		if (lastProcessedToken == null) {
			lastProcessedToken = getToken(0);
		}
		pushWhitespaceForTokens(getToken(1));
		runStack.push(Vector.<IndexedList<LToken>>empty());
	}

	protected void lateRun() {
		runStack.push(Vector.<IndexedList<LToken>>empty());
		pushWhitespaceForTokens(getToken(1));
	}

	private void pushWhitespaceForTokens(Token upToToken) {
		if (lastProcessedToken != upToToken &&
				(upToToken.next == null || lastProcessedToken != upToToken.next)) {
			do {
				lastProcessedToken = lastProcessedToken.next;
				pushWhitespace(lastProcessedToken.whitespace);
			} while (lastProcessedToken != upToToken);
		}
	}

	private void pushWhitespace(IndexedList<LToken> whitespace) {
		// TODO Handle root whitespace before first token
		if (!runStack.isEmpty()) {
			runStack.push(runStack.pop().append(whitespace));
		}
	}

	protected void popNewWhitespaces() {
		lastProcessedToken = getToken(0);
	}

	protected abstract Token getToken(int index);

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

	protected <T extends Tree> T enRun(T facade) {
		if (!configuration.preserveWhitespaces) return facade;

		try {
			final IndexedList<IndexedList<LToken>> tokens = popTokens();

			if (facade == null) return null;

			final STree tree = Tree.treeOf(facade);
			final LexicalShape shape = tree.kind.shape();
			return doEnRun(tree, shape, tokens);
		} catch (EmptyStackException e) {
			System.out.println("Failed to pop tokens:");
			System.out.println("Parser location: " + getToken(0).beginLine + ", " + getToken(0).beginColumn);
			throw e;
		}
	}

	protected <T extends Tree> T enRun(T facade, LexicalShape shape) {
		if (!configuration.preserveWhitespaces) return facade;

		try {
			final IndexedList<IndexedList<LToken>> tokens = popTokens();

			if (facade == null) return null;

			final STree tree = Tree.treeOf(facade);
			return doEnRun(tree, shape, tokens);
		} catch (EmptyStackException e) {
			System.out.println("Failed to pop tokens:");
			System.out.println("Parser location: " + getToken(0).beginLine + ", " + getToken(0).beginColumn);
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	private <T extends Tree> T doEnRun(STree tree, LexicalShape shape,
	                                   IndexedList<IndexedList<LToken>> tokens) {
		try {
			final Iterator<IndexedList<LToken>> tokenIterator = tokens.iterator();
			final LRun run = shape.enRun(tree, tokenIterator);

			if (tokenIterator.hasNext()) {
				final IndexedList<LToken> defered = tokenIterator.next();
				pushWhitespace(defered);

				if (tokenIterator.hasNext()) {
					throw new IllegalStateException();
				}
				while (tokenIterator.hasNext()) {
					pushWhitespace(tokenIterator.next());
				}
			}

			final STree newTree = tree.withRun(run);

			final SLocation location = new SLocation(new SContext.Root(), newTree);
			return (T) newTree.kind.instantiate(location);

		} catch (NoSuchElementException e) {
			System.out.println("Failed to enRun tokens:");
			System.out.println(tokens);
			System.out.println("For tree of kind: " + tree.kind);

			final STreeState state = tree.state;
			if (state instanceof SNodeListState) {
				System.out.println("Elements: ");
				for (STree child : ((SNodeListState) state).children) {
					System.out.println(Printer.printToString(child.asTree()));
					System.out.println("  " + child.kind);
				}
			} else if (state instanceof SNodeState && tree.kind == AssignExpr.kind) {
				System.out.println("Children: ");
				final STree target = ((SNodeState) state).child(0);
				final STree value = ((SNodeState) state).child(1);
				System.out.println(Printer.printToString(target.asTree()));
				System.out.println(Printer.printToString(value.asTree()));
			}
			System.out.println("For shape: " + shape);
			System.out.println("Parser location: " + getToken(0).beginLine + ", " + getToken(0).beginColumn);
			dumpRunStack();
			throw e;
		}
	}

	private void dumpRunStack() {
		System.out.println("RunStack dump: ");
		for (int i = runStack.size() - 1; i >= 0; i--) {
			System.out.println("  " + runStack.get(i));
		}
	}

	protected void popRun() {
		popTokens();
	}

	private IndexedList<IndexedList<LToken>> popTokens() {
		pushWhitespaceForTokens(getToken(0));
		return runStack.pop();
	}

	protected void postProcessToken(Token token) {
		if (configuration.preserveWhitespaces) {
			token.whitespace = buildWhitespaceRunPart(token.specialToken);
		}
	}

	private IndexedList<LToken> buildWhitespaceRunPart(Token token) {
		if (token != null)
			return buildWhitespaceRunPart(token.specialToken).append(new LToken(token.kind, token.image));
		else return Vector.empty();
	}

	static class TokenBase {

		int realKind = ParserImplConstants.GT;
		IndexedList<LToken> whitespace;
	}

	// Utility methods

	protected <T extends Tree> NodeList<T> append(NodeList<T> list, T element) {
		return list == null ? new NodeList<T>(element) : list.append(element);
	}
}
