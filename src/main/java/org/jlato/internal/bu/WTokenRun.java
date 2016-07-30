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

import com.github.andrewoma.dexx.collection.ArrayList;
import com.github.andrewoma.dexx.collection.IndexedList;
import com.github.andrewoma.dexx.collection.Vector;
import org.jlato.internal.parser.TokenType;

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

	public WTokenRun prepend(WToken element) {
		return new WTokenRun(elements.prepend(element));
	}

	public WTokenRun append(WToken element) {
		return new WTokenRun(elements.append(element));
	}

	public String[] getComments() {
		java.util.ArrayList<String> comments = new java.util.ArrayList<String>();
		for (WToken token : elements) {
			switch (token.kind) {
				case TokenType.SINGLE_LINE_COMMENT:
				case TokenType.MULTI_LINE_COMMENT:
					comments.add(trimmedCommentString(token));
					break;
				case TokenType.JAVA_DOC_COMMENT:
				case TokenType.NEWLINE:
				case TokenType.WHITESPACE:
					break;
				default:
					// Checked at WToken instantiation
					throw new IllegalStateException();
			}
		}
		return comments.toArray(new String[comments.size()]);
	}

	private String trimmedCommentString(WToken comment) {
		String commentString = comment.string;
		return comment.kind == TokenType.SINGLE_LINE_COMMENT ?
				commentString.substring("//".length()).trim() :
				commentString.substring("/*".length(), commentString.length() - "*/".length()).trim();
	}

	public WTokenRun replaceOrAppendDocComment(WToken docComment) {
		int indexOfDocComment = indexOfDocComment();
		if (indexOfDocComment != -1) {
			return new WTokenRun(elements.set(indexOfDocComment, docComment));
		} else {
			return append(docComment).append(WToken.newLine());
		}
	}

	public WToken getDocComment() {
		int indexOfDocComment = indexOfDocComment();
		return indexOfDocComment != -1 ? elements.get(indexOfDocComment) : null;
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
		// We can also have many trailing comments before a new line
		boolean containsNewLines = containsNewLines();
		boolean containsComments = containsComments();

		if (!containsComments) return 0;

		for (int i = 0; i < elements.size(); i++) {
			final WToken token = elements.get(i);
			switch (token.kind) {
				case TokenType.SINGLE_LINE_COMMENT:
				case TokenType.MULTI_LINE_COMMENT:
				case TokenType.JAVA_DOC_COMMENT:
					if (!containsNewLines) return i + 1;
					else break;
				case TokenType.NEWLINE:
					return i;
				case TokenType.WHITESPACE:
					break;
				default:
					// Checked at WToken instantiation
					throw new IllegalStateException();
			}
		}
		return elements.size();
	}

	private int splitIndexOfLeadingComments() {
		// We expect to find some comments at end with no empty line between or after
		boolean emptyLine = false;
		int splitIndex = elements.size();
		for (int i = elements.size() - 1; i >= 0; i--) {
			final WToken token = elements.get(i);
			switch (token.kind) {
				case TokenType.SINGLE_LINE_COMMENT:
				case TokenType.MULTI_LINE_COMMENT:
				case TokenType.JAVA_DOC_COMMENT:
					emptyLine = false;
					splitIndex = i;
					break;
				case TokenType.NEWLINE:
					if (emptyLine) return splitIndex;
					emptyLine = true;
					break;
				case TokenType.WHITESPACE:
					break;
				default:
					// Checked at WToken instantiation
					throw new IllegalStateException();
			}
		}
		return splitIndex;
	}

	private int indexOfDocComment() {
		for (int i = 0; i < elements.size(); i++) {
			final WToken token = elements.get(i);
			switch (token.kind) {
				case TokenType.SINGLE_LINE_COMMENT:
				case TokenType.MULTI_LINE_COMMENT:
					break;
				case TokenType.JAVA_DOC_COMMENT:
					return i;
				case TokenType.NEWLINE:
				case TokenType.WHITESPACE:
					break;
				default:
					// Checked at WToken instantiation
					throw new IllegalStateException();
			}
		}
		return -1;
	}

	public boolean containsOneCommentNoNewLine() {
		if (elements.size() > 3) return false;

		int count = 0;
		for (WToken token : elements) {
			switch (token.kind) {
				case TokenType.SINGLE_LINE_COMMENT:
				case TokenType.MULTI_LINE_COMMENT:
				case TokenType.JAVA_DOC_COMMENT:
					count++;
					break;
				case TokenType.NEWLINE:
					return false;
				case TokenType.WHITESPACE:
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
				case TokenType.SINGLE_LINE_COMMENT:
				case TokenType.MULTI_LINE_COMMENT:
				case TokenType.JAVA_DOC_COMMENT:
					emptyLine = false;
					break;
				case TokenType.NEWLINE:
					if (emptyLine) count++;
					emptyLine = true;
					break;
				case TokenType.WHITESPACE:
					break;
				default:
					// Checked at WToken instantiation
					throw new IllegalStateException();
			}
		}
		return count;
	}

	public boolean containsNewLines() {
		for (WToken token : elements) {
			switch (token.kind) {
				case TokenType.SINGLE_LINE_COMMENT:
				case TokenType.MULTI_LINE_COMMENT:
				case TokenType.JAVA_DOC_COMMENT:
					break;
				case TokenType.NEWLINE:
					return true;
				case TokenType.WHITESPACE:
					break;
				default:
					// Checked at WToken instantiation
					throw new IllegalStateException();
			}
		}
		return false;
	}

	public boolean containsComments() {
		for (WToken token : elements) {
			switch (token.kind) {
				case TokenType.SINGLE_LINE_COMMENT:
				case TokenType.MULTI_LINE_COMMENT:
				case TokenType.JAVA_DOC_COMMENT:
					return true;
				case TokenType.NEWLINE:
				case TokenType.WHITESPACE:
					break;
				default:
					// Checked at WToken instantiation
					throw new IllegalStateException();
			}
		}
		return false;
	}

	public boolean containsSingleLineComment() {
		for (WToken token : elements) {
			switch (token.kind) {
				case TokenType.SINGLE_LINE_COMMENT:
					return true;
				case TokenType.MULTI_LINE_COMMENT:
				case TokenType.JAVA_DOC_COMMENT:
				case TokenType.NEWLINE:
				case TokenType.WHITESPACE:
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
