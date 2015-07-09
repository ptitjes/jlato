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

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;
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
		final STree sTree = Tree.treeOf(tree);
		final LexicalShape shape = sTree.kind.shape();
		shape.render(sTree, sTree.run, this);
	}

	private int indentLevel;
	private int delayedIndentation;
	private boolean needsIndentation;
	private boolean afterAlpha;

	private void reset() {
		indentLevel = 0;
		needsIndentation = true;
		afterAlpha = false;
	}

	public void append(LToken token, boolean requiresFormatting) {
		boolean isAlpha = token.isKeyword() || token.isIdentifier();
		if ((format || requiresFormatting) && needsIndentation) doPrintIndent();
		if (afterAlpha && isAlpha) writer.append(" ");
		writer.append(token.string);
		afterAlpha = isAlpha;
	}

	public void appendWhiteSpace(String string) {
		if (!(format && needsIndentation)) writer.append(string);
		afterAlpha = false;
	}

	public void appendNewLine(int count) {
		for (int i = 0; i < count; i++) {
			appendNewLine();
		}
	}

	public void appendNewLine() {
		appendNewLine(formattingSettings.newLine());
	}

	public void appendNewLine(String image) {
		doPrintNewLine(image);
		afterAlpha = false;
	}

	public void indent(int indentation) {
		indentLevel += indentation;
	}

	public void unIndent(int indentation) {
		indentLevel -= indentation;
	}

	private void doPrintNewLine(String image) {
		writer.append(image);
		needsIndentation = true;
	}

	private void doPrintIndent() {
		for (int i = 0; i < indentLevel; i++) {
			writer.append(formattingSettings.indentation());
		}
		needsIndentation = false;
	}
}
