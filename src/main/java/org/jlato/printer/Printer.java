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

package org.jlato.printer;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.WToken;
import org.jlato.internal.bu.WTokenRun;
import org.jlato.internal.shapes.IndentationConstraint;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.shapes.SpacingConstraint;
import org.jlato.internal.td.TDTree;
import org.jlato.parser.ParserImplConstants;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author Didier Villevalois
 */
public class Printer {

	/**
	 * Prints the specified tree to a string with no formatting and the default formatting settings.
	 *
	 * @param tree the tree to print
	 * @return the tree printed to a string
	 */
	public static String printToString(Tree tree) {
		StringWriter writer = new StringWriter();
		printTo(tree, new PrintWriter(writer));
		return writer.toString();
	}

	/**
	 * Prints the specified tree to a string with the default formatting settings.
	 *
	 * @param tree   the tree to print
	 * @param format whether to format printed trees
	 * @return the tree printed to a string
	 */
	public static String printToString(Tree tree, boolean format) {
		StringWriter writer = new StringWriter();
		printTo(tree, new PrintWriter(writer), format);
		return writer.toString();
	}

	/**
	 * Prints the specified tree to a string with the specified formatting settings.
	 *
	 * @param tree               the tree to print
	 * @param formattingSettings the settings to apply to format printed trees
	 * @return the tree printed to a string
	 */
	public static String printToString(Tree tree, FormattingSettings formattingSettings) {
		StringWriter writer = new StringWriter();
		printTo(tree, new PrintWriter(writer), formattingSettings);
		return writer.toString();
	}

	/**
	 * Prints the specified tree to a string with the specified formatting settings.
	 *
	 * @param tree               the tree to print
	 * @param format             whether to format printed trees
	 * @param formattingSettings the settings to apply to format printed trees
	 * @return the tree printed to a string
	 */
	public static String printToString(Tree tree, boolean format, FormattingSettings formattingSettings) {
		StringWriter writer = new StringWriter();
		printTo(tree, new PrintWriter(writer), format, formattingSettings);
		return writer.toString();
	}

	/**
	 * Prints the specified tree to the specified writer with no formatting and the default formatting settings.
	 *
	 * @param tree   the tree to print
	 * @param writer the writer to print to
	 */
	public static void printTo(Tree tree, PrintWriter writer) {
		Printer printer = new Printer(writer);
		printer.print(tree);
	}

	/**
	 * Prints the specified tree to the specified writer with the default formatting settings.
	 *
	 * @param tree   the tree to print
	 * @param writer the writer to print to
	 * @param format whether to format printed trees
	 */
	public static void printTo(Tree tree, PrintWriter writer, boolean format) {
		Printer printer = new Printer(writer, format);
		printer.print(tree);
	}

	/**
	 * Prints the specified tree to the specified writer with the specified formatting settings.
	 *
	 * @param tree               the tree to print
	 * @param writer             the writer to print to
	 * @param formattingSettings the settings to apply to format printed trees
	 */
	public static void printTo(Tree tree, PrintWriter writer, FormattingSettings formattingSettings) {
		Printer printer = new Printer(writer, formattingSettings);
		printer.print(tree);
	}

	/**
	 * Prints the specified tree to the specified writer with the specified formatting settings.
	 *
	 * @param tree               the tree to print
	 * @param writer             the writer to print to
	 * @param format             whether to format printed trees
	 * @param formattingSettings the settings to apply to format printed trees
	 */
	public static void printTo(Tree tree, PrintWriter writer, boolean format, FormattingSettings formattingSettings) {
		Printer printer = new Printer(writer, format, formattingSettings);
		printer.print(tree);
	}

	private final PrintWriter writer;
	public final boolean format;
	public final FormattingSettings formattingSettings;

	/**
	 * Creates a Printer that prints to the specified writer with no formatting and the default formatting settings.
	 *
	 * @param writer the writer to print to
	 */
	public Printer(PrintWriter writer) {
		this(writer, false, FormattingSettings.Default);
	}

	/**
	 * Creates a Printer that prints to the specified writer with the default formatting settings.
	 *
	 * @param writer the writer to print to
	 * @param format whether to format printed trees
	 */
	public Printer(PrintWriter writer, boolean format) {
		this(writer, format, FormattingSettings.Default);
	}

	/**
	 * Creates a Printer that prints to the specified writer with the specified formatting settings.
	 *
	 * @param writer             the writer to print to
	 * @param formattingSettings the settings to apply to format printed trees
	 */
	public Printer(PrintWriter writer, FormattingSettings formattingSettings) {
		this(writer, true, formattingSettings);
	}

	/**
	 * Creates a Printer that prints to the specified writer with the specified formatting settings.
	 *
	 * @param writer             the writer to print to
	 * @param format             whether to format printed trees
	 * @param formattingSettings the settings to apply to format printed trees
	 */
	public Printer(PrintWriter writer, boolean format, FormattingSettings formattingSettings) {
		this.writer = writer;
		this.format = format;
		this.formattingSettings = formattingSettings;
	}

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

	public void append(LToken token, boolean requiresFormatting) {
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
				case ParserImplConstants.JAVA_DOC_COMMENT:
					appendJavaDoc(token.string);
					break;
				case ParserImplConstants.SINGLE_LINE_COMMENT:
				case ParserImplConstants.MULTI_LINE_COMMENT:
					appendComment(token.string);
					break;
				case ParserImplConstants.NEWLINE:
					appendNewLine(token.string);
					break;
				case ParserImplConstants.WHITESPACE:
					appendWhiteSpace(token.string);
					break;
			}
		}
	}

	private void appendJavaDoc(String image) {
		appendJavaDocLine("/**", false);

		// Remove /** and */
		image = image.trim();
		image = image.substring(3, image.length() - 2);

		String[] lines = image.split("\n|\r\n|\r|\f");
		for (int i = 0; i < lines.length; i++) {
			String line = lines[i];
			line = line.trim();
			if (line.startsWith("*")) line = line.substring(1);
			lines[i] = line.trim();
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
			if (line.isEmpty()) appendJavaDocLine(" *", false);
			else appendJavaDocLine(" * " + line, false);
		}
		appendJavaDocLine(" */", true);
	}

	private void appendJavaDocLine(String image, boolean last) {
		if (needsIndentation) doPrintIndent();
		writer.append(image);
		if (!last) appendNewLine();
	}

	private void appendComment(String image) {
		if (needsIndentation) doPrintIndent();
		writer.append(image);
		afterAlpha = false;
		needsIndentation = false;
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
