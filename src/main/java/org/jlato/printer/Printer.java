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

import org.jlato.internal.shapes.Print;
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
		Printer print = new Printer();
		print.print(tree, writer);
	}

	/**
	 * Prints the specified tree to the specified writer with the default formatting settings.
	 *
	 * @param tree   the tree to print
	 * @param writer the writer to print to
	 * @param format whether to format printed trees
	 */
	public static void printTo(Tree tree, PrintWriter writer, boolean format) {
		Printer print = new Printer(format);
		print.print(tree, writer);
	}

	/**
	 * Prints the specified tree to the specified writer with the specified formatting settings.
	 *
	 * @param tree               the tree to print
	 * @param writer             the writer to print to
	 * @param formattingSettings the settings to apply to format printed trees
	 */
	public static void printTo(Tree tree, PrintWriter writer, FormattingSettings formattingSettings) {
		Printer print = new Printer(formattingSettings);
		print.print(tree, writer);
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
		Printer print = new Printer(format, formattingSettings);
		print.print(tree, writer);
	}

	/**
	 * Creates a Printer that prints with no formatting and the default formatting settings.
	 */
	public Printer() {
		this(false, FormattingSettings.Default);
	}

	/**
	 * Creates a Printer that prints with the default formatting settings.
	 *
	 * @param format whether to format printed trees
	 */
	public Printer(boolean format) {
		this(format, FormattingSettings.Default);
	}

	/**
	 * Creates a Printer that prints with the specified formatting settings.
	 *
	 * @param formattingSettings the settings to apply to format printed trees
	 */
	public Printer(FormattingSettings formattingSettings) {
		this(true, formattingSettings);
	}

	/**
	 * Creates a Printer that prints with the specified formatting settings.
	 *
	 * @param format             whether to format printed trees
	 * @param formattingSettings the settings to apply to format printed trees
	 */
	public Printer(boolean format, FormattingSettings formattingSettings) {
		this.format = format;
		this.formattingSettings = formattingSettings;
	}

	private final boolean format;
	private final FormattingSettings formattingSettings;

	/**
	 * Prints the specified tree.
	 *
	 * @param tree   the tree to print
	 * @param writer the writer to print to
	 */
	public void print(Tree tree, PrintWriter writer) {
		new Print(writer, format, formattingSettings).print(tree);
	}
}
