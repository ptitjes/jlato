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

import com.github.andrewoma.dexx.collection.ArrayList;
import com.github.andrewoma.dexx.collection.Builder;
import com.github.andrewoma.dexx.collection.IndexedList;
import com.github.andrewoma.dexx.collection.Vector;
import org.jlato.internal.bu.LRun;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LSComposite;
import org.jlato.internal.shapes.LSToken;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SContext;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;

import java.io.InputStream;
import java.io.Reader;
import java.util.Iterator;
import java.util.Stack;

/**
 * @author Didier Villevalois
 */
public abstract class ParserBase {

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
		if (lastProcessedToken != upToToken) {
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

	protected abstract Token getToken(int index);

	@SuppressWarnings("unchecked")
	protected <T extends Tree> T enRun(T facade) {
		if (!configuration.preserveWhitespaces) return facade;

		pushWhitespaceForTokens(getToken(0));

		final STree tree = Tree.treeOf(facade);

		final IndexedList<IndexedList<LToken>> tokens = runStack.pop();

		final Iterator<IndexedList<LToken>> tokenIterator = tokens.iterator();
		final LexicalShape shape = tree.kind.shape();
		final LRun run = shape.enRun(tree, tokenIterator);

		final STree newTree = tree.withRun(run);

		final SLocation location = new SLocation(new SContext.Root(), newTree);
		return (T) newTree.kind.instantiate(location);
	}

	protected void postProcessToken(Token token) {
		if (configuration.preserveWhitespaces) {
			token.whitespace = buildWhitespaceRunPart(token.specialToken);
		}
	}

	private IndexedList<LToken> buildWhitespaceRunPart(Token token) {
//		if (token.kind == ASTParserConstants.GT) {
//			if (lastProcessedLexeme.is(OperatorKind.RSIGNEDSHIFT, OperatorKind.RUNSIGNEDSHIFT)) {
//				return lastProcessedLexeme;
//			}
//		}

		if (token != null)
			return buildWhitespaceRunPart(token.specialToken).append(new LToken(token.kind, token.image));
		else return Vector.empty();
	}

	static class TokenBase {

		int realKind = ParserImplConstants.GT;
		IndexedList<LToken> whitespace;
	}
}
