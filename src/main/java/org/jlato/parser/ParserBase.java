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
import com.github.andrewoma.dexx.collection.internal.base.AbstractIndexedList;
import org.jlato.internal.bu.*;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.printer.Printer;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;

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

	protected <T extends Tree> T enRun(T facade) {
		if (!configuration.preserveWhitespaces) return facade;

		try {
			final IndexedList<WTokenRun> tokens = popTokens();

			if (facade == null) return null;

			final STree tree = TreeBase.treeOf(facade);
			final LexicalShape shape = tree.kind.shape();
			return doEnRun(tree, shape, tokens);

		} catch (EmptyStackException e) {
			debugFailedPopTokens();
			throw e;
		}
	}

	protected <T extends Tree> T enRun(T facade, LexicalShape shape) {
		if (!configuration.preserveWhitespaces) return facade;

		try {
			final IndexedList<WTokenRun> tokens = popTokens();

			if (facade == null) return null;

			final STree tree = TreeBase.treeOf(facade);
			return doEnRun(tree, shape, tokens);

		} catch (EmptyStackException e) {
			debugFailedPopTokens();
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	private <T extends Tree> T doEnRun(STree tree, LexicalShape shape,
	                                   IndexedList<WTokenRun> tokens) {
		try {
			final Iterator<WTokenRun> tokenIterator = tokens.iterator();
			final WRunRun run = shape.enRun(tree, tokenIterator);

			if (tokenIterator.hasNext()) {
				// Flow up the remaining whitespace run for consumption by parent tree
				final WTokenRun deferred = tokenIterator.next();
				pushWhitespace(deferred);

				// Only one whitespace run at most should flow up
				if (tokenIterator.hasNext()) {
					throw new IllegalStateException();
				}
			}

			final STree newTree = tree.withRun(run);
			return (T) newTree.asTree();

		} catch (NoSuchElementException e) {
			debugFailedEnRun(tree, shape, tokens);
			throw e;
		}
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

	static class TokenBase {

		int realKind = ParserImplConstants.GT;
		WTokenRun whitespace;
	}

	// Convenience methods for lists

	protected <T extends Tree> NodeList<T> append(NodeList<T> list, T element) {
		return list == null ? NodeList.of(element) : list.append(element);
	}

	protected <T extends Tree> NodeList<T> ensureNotNull(NodeList<T> list) {
		return list == null ? NodeList.<T>empty() : list;
	}

	// Debug methods

	private void debugFailedPopTokens() {
		System.out.println("Error at location: " + getToken(0).beginLine + ", " + getToken(0).beginColumn);
		System.out.println("Failed to pop tokens !");
	}

	private void debugFailedEnRun(STree tree, LexicalShape shape, IndexedList<WTokenRun> tokens) {
		System.out.println("Error at location: " + getToken(0).beginLine + ", " + getToken(0).beginColumn);

		System.out.print("Failed to enRun tokens: ");
		System.out.println(tokens);

		System.out.println("For tree of kind: " + tree.kind);

		final STreeState state = tree.state;

		AbstractIndexedList<? extends STree> children =
				state instanceof SNodeListState ? ((SNodeListState) state).children :
						state instanceof SNodeState ? ((SNodeState) state).children :
								Vector.<STree>empty();
		if (!children.isEmpty()) {
			System.out.println("With children: ");
			for (int i = 0; i < children.size(); i++) {
				STree child = children.get(i);
				System.out.print("  " + i + " - ");
				if (child == null) {
					System.out.println("null");
				} else {
					System.out.print(child.kind + ": ");
					System.out.println(Printer.printToString(child.asTree()));
				}
			}
		}

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
