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
import org.jlato.internal.bu.LToken;

import java.io.InputStream;
import java.io.Reader;
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

	protected void run() {
		runStack.push(Vector.<IndexedList<LToken>>empty());
	}

	protected void postProcessToken(Token token) {
		if (configuration.preserveWhitespaces) {
			if (token.specialToken != null) {
				runStack.push(runStack.pop().append(buildWhitespaceRunPart(token.specialToken)));
			}
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

}
