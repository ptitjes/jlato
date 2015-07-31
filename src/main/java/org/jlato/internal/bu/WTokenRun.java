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
import com.github.andrewoma.dexx.collection.Vector;
import org.jlato.parser.ParserImplConstants;

/**
 * @author Didier Villevalois
 */
public class WTokenRun extends WRun {

	public static final WTokenRun EMPTY = new WTokenRun(Vector.<WToken>empty());
	public static final WTokenRun NULL = null;

	public final IndexedList<WToken> elements;

	public WTokenRun(IndexedList<WToken> elements) {
		this.elements = elements;
	}

	public WTokenRun append(WToken element) {
		return new WTokenRun(elements.append(element));
	}

	public WTokenRun appendAll(WTokenRun tokens) {
		WTokenRun concatenation = this;
		for (WToken token : tokens.elements) {
			concatenation = concatenation.append(token);
		}
		return concatenation;
	}

	public TwoWaySplit splitTrailingComment() {
		int splitIndex = splitIndexOfTrailingComment();
		return new TwoWaySplit(
				splitIndex == 0 ? NULL : new WTokenRun(elements.take(splitIndex)),
				splitIndex == elements.size() ? EMPTY : new WTokenRun(elements.drop(splitIndex))
		);
	}

	public TwoWaySplit splitLeadingComments() {
		int splitIndex = splitIndexOfLeadingComments();
		return new TwoWaySplit(
				splitIndex == 0 ? EMPTY : new WTokenRun(elements.take(splitIndex)),
				splitIndex == elements.size() ? NULL : new WTokenRun(elements.drop(splitIndex))
		);
	}

	public ThreeWaySplit splitTrailingAndLeadingComments() {
		if (containsOneCommentNoNewLine()) {
			final TwoWaySplit leadingSplit = splitLeadingComments();

			return new ThreeWaySplit(
					null,
					leadingSplit.left,
					leadingSplit.right
			);
		} else {
			final TwoWaySplit trailingSplit = splitTrailingComment();
			final TwoWaySplit leadingSplit = trailingSplit.right.splitLeadingComments();

			return new ThreeWaySplit(
					trailingSplit.left,
					leadingSplit.left,
					leadingSplit.right
			);
		}
	}

	public static class TwoWaySplit {
		public final WTokenRun left;
		public final WTokenRun right;

		private TwoWaySplit(WTokenRun left, WTokenRun right) {
			this.left = left;
			this.right = right;
		}
	}

	public static class ThreeWaySplit {
		public final WTokenRun left;
		public final WTokenRun middle;
		public final WTokenRun right;

		private ThreeWaySplit(WTokenRun left, WTokenRun middle, WTokenRun right) {
			this.left = left;
			this.middle = middle;
			this.right = right;
		}
	}

	private int splitIndexOfTrailingComment() {
		// We expect to find one comment at beginning with no new line before
		int splitIndex;
		for (splitIndex = 0; splitIndex < elements.size(); splitIndex++) {
			final WToken token = elements.get(splitIndex);
			switch (token.kind) {
				case ParserImplConstants.SINGLE_LINE_COMMENT:
				case ParserImplConstants.MULTI_LINE_COMMENT:
				case ParserImplConstants.JAVA_DOC_COMMENT:
					return splitIndex + 1;
				case ParserImplConstants.NEWLINE:
					return splitIndex;
				case ParserImplConstants.WHITESPACE:
					break;
				default:
					// Checked at WToken instantiation
					throw new IllegalStateException();
			}
		}
		return splitIndex;
	}

	private int splitIndexOfLeadingComments() {
		// We expect to find some comments at end with no empty line between or after
		boolean emptyLine = false;
		int splitIndex = elements.size();
		for (int i = elements.size() - 1; i >= 0; i--) {
			final WToken token = elements.get(i);
			switch (token.kind) {
				case ParserImplConstants.SINGLE_LINE_COMMENT:
				case ParserImplConstants.MULTI_LINE_COMMENT:
				case ParserImplConstants.JAVA_DOC_COMMENT:
					emptyLine = false;
					splitIndex = i;
					break;
				case ParserImplConstants.NEWLINE:
					if (emptyLine) return splitIndex;
					emptyLine = true;
					break;
				case ParserImplConstants.WHITESPACE:
					break;
				default:
					// Checked at WToken instantiation
					throw new IllegalStateException();
			}
		}
		return splitIndex;
	}

	public boolean containsOneCommentNoNewLine() {
		if (elements.size() > 3) return false;

		int count = 0;
		for (WToken token : elements) {
			switch (token.kind) {
				case ParserImplConstants.SINGLE_LINE_COMMENT:
				case ParserImplConstants.MULTI_LINE_COMMENT:
				case ParserImplConstants.JAVA_DOC_COMMENT:
					count++;
					break;
				case ParserImplConstants.NEWLINE:
					return false;
				case ParserImplConstants.WHITESPACE:
					break;
				default:
					// Checked at WToken instantiation
					throw new IllegalStateException();
			}
		}
		return count == 1;
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

	public static class Builder {
		private final com.github.andrewoma.dexx.collection.Builder<WToken, Vector<WToken>> elements = Vector.<WToken>factory().newBuilder();

		public void add(WToken token) {
			elements.add(token);
		}

		public WTokenRun build() {
			return new WTokenRun(elements.build());
		}
	}
}
