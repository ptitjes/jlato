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

package org.jlato.internal.shapes;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.WToken;
import org.jlato.internal.bu.WTokenRun;
import org.jlato.internal.td.TDTree;
import org.jlato.internal.parser.TokenType;
import org.jlato.printer.FormattingSettings;
import org.jlato.printer.Spacing;
import org.jlato.tree.Tree;

import java.io.PrintWriter;

/**
 * @author Didier Villevalois
 */
public class Print {

	/**
	 * Creates a Print that prints to the specified writer with the specified formatting settings.
	 *
	 * @param writer             the writer to print to
	 * @param format             whether to format printed trees
	 * @param formattingSettings the settings to apply to format printed trees
	 */
	public Print(PrintWriter writer, boolean format, FormattingSettings formattingSettings) {
		this.writer = writer;
		this.format = format;
		this.formattingSettings = formattingSettings;
	}

	private final PrintWriter writer;
	private final boolean format;
	private final FormattingSettings formattingSettings;

	/**
	 * Prints the specified tree.
	 *
	 * @param tree the tree to print
	 */
	public void print(Tree tree) {
		reset();
		final BUTree buTree = TDTree.treeOf(tree);
		final LexicalShape shape = LexicalShape.defaultShape();
		shape.render(buTree, this);
		flush();
	}

	private boolean start;
	private int indentationLevel;
	private boolean needsIndentation;
	private boolean afterAlpha;

	private Spacing spacing;
	private WTokenRun existingWhitespace;
	private WTokenRun leadingWhitespace;
	private WTokenRun trailingWhitespace;

	private void reset() {
		start = true;
		indentationLevel = 0;
		needsIndentation = true;
		afterAlpha = false;
		spacing = Spacing.noSpace;
		existingWhitespace = null;
	}

	public void encounteredSpacing(SpacingConstraint constraint) {
		final Spacing otherSpacing = constraint.resolve(formattingSettings);
		spacing = otherSpacing != null ? spacing.max(otherSpacing) : spacing;
	}

	public void encounteredWhitespace(WTokenRun whitespace) {
		if (whitespace == null) return;

		assert existingWhitespace == null;
		existingWhitespace = whitespace;
	}

	public void encounteredLeading(WTokenRun whitespace) {
		if (whitespace == null) return;

		assert leadingWhitespace == null;
		leadingWhitespace = whitespace;
	}

	public void encounteredTrailing(WTokenRun whitespace) {
		if (whitespace == null) return;

		assert trailingWhitespace == null;
		trailingWhitespace = whitespace;
	}

	public void encounteredIndentation(IndentationConstraint constraint) {
		indentationLevel += constraint.resolve(formattingSettings);
	}

	public void append(LToken token) {
		boolean requiresFormatting = existingWhitespace == null;
		if (!start) {
			renderTrailing();
			renderSpacing();
		} else start = false;
		renderLeading();

		boolean isAlpha = token.isKeyword() || token.isIdentifier();
		if ((format || requiresFormatting) && needsIndentation) doPrintIndent();
		if (afterAlpha && isAlpha) writer.append(" ");

		writer.append(token.string);
		afterAlpha = isAlpha;
	}

	private void flush() {
		if (trailingWhitespace != null) {
			renderTrailing();
		} else {
			renderSpacing();
		}
	}

	private void renderSpacing() {
		switch (spacing.unit) {
			case Space:
				if (existingWhitespace != null && (!format || existingWhitespace.containsComments()))
					dump(existingWhitespace);
				else {
					for (int i = 0; i < spacing.count; i++) {
						appendWhiteSpace(" ");
					}
				}
				break;
			case Line:
				if (existingWhitespace != null) {
					if (format) {
						final int actualEmptyLines = existingWhitespace.emptyLineCount();
						appendNewLine(spacing.count - actualEmptyLines);
					}
					dump(existingWhitespace);
				} else {
					appendNewLine(spacing.count);
				}
				break;
		}

		spacing = Spacing.noSpace;
		existingWhitespace = null;
	}

	private void renderLeading() {
		if (leadingWhitespace == null) return;

		dump(leadingWhitespace);

		leadingWhitespace = null;
	}

	private void renderTrailing() {
		if (trailingWhitespace == null) return;

		dump(trailingWhitespace);

		trailingWhitespace = null;
	}

	private void dump(WTokenRun tokens) {
		for (WToken token : tokens.elements) {
			switch (token.kind) {
				case TokenType.JAVA_DOC_COMMENT:
					appendJavaDocComment(token.string);
					break;
				case TokenType.SINGLE_LINE_COMMENT:
					appendSingleLineComment(token.string);
					break;
				case TokenType.MULTI_LINE_COMMENT:
					appendMultiLineComment(token.string);
					break;
				case TokenType.NEWLINE:
					appendNewLine(token.string);
					break;
				case TokenType.WHITESPACE:
					appendWhiteSpace(token.string);
					break;
			}
		}
	}

	private void appendFormattedComment(String image,
	                                    String startMarker, String emptyLineMarker,
	                                    String lineMarker, String stopMarker,
	                                    boolean clearMarkers) {
		appendCommentLine(startMarker, clearMarkers);

		// Remove /** and */
		image = image.trim();
		if (image.trim().startsWith(startMarker.trim()))
			image = image.substring(startMarker.length()).trim();
		if (image.trim().endsWith(stopMarker.trim()))
			image = image.substring(0, image.length() - stopMarker.length()).trim();

		String[] lines = image.split("\n|\r\n|\r|\f");
		for (int i = 0; i < lines.length; i++) {
			String line = lines[i];
			if (!emptyLineMarker.equals("") && line.trim().equals(emptyLineMarker.trim()))
				line = "";
			else if (!lineMarker.equals("") && line.trim().startsWith(lineMarker.trim()))
				line = line.substring(lineMarker.trim().length()).trim();
			lines[i] = line;
		}

		int first = 0;
		int last = lines.length - 1;
		for (int i = 0; i < lines.length; i++) {
			if (!lines[i].isEmpty()) {
				first = i;
				break;
			}
		}
		for (int i = lines.length - 1; i >= first; i--) {
			if (!lines[i].isEmpty()) {
				last = i;
				break;
			}
		}

		for (int i = first; i <= last; i++) {
			String line = lines[i];
			if (line.isEmpty()) appendCommentLine(emptyLineMarker, clearMarkers || i != last);
			else appendCommentLine(lineMarker + line, clearMarkers || i != last);
		}
		appendCommentLine(stopMarker, false);
	}

	private void appendCommentLine(String image, boolean newLine) {
		if (format && needsIndentation) doPrintIndent();
		writer.append(image);
		if (newLine) appendNewLine();
	}

	private void appendComment(String image) {
		if (format && needsIndentation) doPrintIndent();
		writer.append(image);
		afterAlpha = false;
	}

	private void appendJavaDocComment(String image) {
		if (formattingSettings.docCommentFormatting()) appendFormattedComment(image, "/**", " *", " * ", " */", true);
		else appendComment(image);
	}

	private void appendMultiLineComment(String image) {
		if (formattingSettings.commentFormatting()) appendFormattedComment(image, "/* ", "", "", " */", false);
		else appendComment(image);
	}

	private void appendSingleLineComment(String image) {
		appendComment(image);
	}

	private void appendWhiteSpace(String string) {
		if (!(format && needsIndentation)) {
			writer.append(string);
			needsIndentation = false;
		}
		afterAlpha = false;
	}

	private void appendNewLine(int count) {
		for (int i = 0; i < count; i++) {
			appendNewLine();
		}
	}

	private void appendNewLine() {
		appendNewLine(formattingSettings.newLine());
	}

	private void appendNewLine(String image) {
		doPrintNewLine(image);
		afterAlpha = false;
	}

	private void doPrintNewLine(String image) {
		writer.append(image);
		needsIndentation = true;
	}

	private void doPrintIndent() {
		for (int i = 0; i < indentationLevel; i++) {
			writer.append(formattingSettings.indentation());
		}
		needsIndentation = false;
	}
}
