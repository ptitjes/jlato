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

package org.jlato.internal.bu;

import com.github.andrewoma.dexx.collection.IndexedList;
import org.jlato.parser.ParserImplConstants;
import org.jlato.tree.Tree;

/**
 * @author Didier Villevalois
 */
public class WTokenRun extends WRun {

	public final IndexedList<WToken> elements;

	public WTokenRun(IndexedList<WToken> elements) {
		this.elements = elements;
	}

	public WTokenRun append(WToken element) {
		return new WTokenRun(elements.append(element));
	}

	public int emptyLineCount() {
		int count = 0;
		boolean emptyLine = true;
		for (WToken token : elements) {
			switch (token.kind) {
				case ParserImplConstants.SINGLE_LINE_COMMENT:
				case ParserImplConstants.MULTI_LINE_COMMENT:
				case ParserImplConstants.JAVA_DOC_COMMENT:
					emptyLine = false;
					break;
				case ParserImplConstants.NEWLINE:
					if (emptyLine) count++;
					emptyLine = true;
					break;
				case ParserImplConstants.WHITESPACE:
					break;
				default:
					// Checked at WToken instantiation
					throw new IllegalStateException();
			}
		}
		return count;
	}

	public boolean containsComments() {
		for (WToken token : elements) {
			switch (token.kind) {
				case ParserImplConstants.SINGLE_LINE_COMMENT:
				case ParserImplConstants.MULTI_LINE_COMMENT:
				case ParserImplConstants.JAVA_DOC_COMMENT:
					return true;
				case ParserImplConstants.NEWLINE:
				case ParserImplConstants.WHITESPACE:
					break;
				default:
					// Checked at WToken instantiation
					throw new IllegalStateException();
			}
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[");

		boolean first = true;
		for (WToken token : elements) {
			if (!first) builder.append(",");
			else first = false;

			builder.append(token.toString());
		}

		builder.append("]");
		return builder.toString();
	}
}
