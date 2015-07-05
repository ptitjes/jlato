package org.jlato.printer;

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
	 * Prints the specified tree to a string.
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
	 * Prints the specified tree to the specified writer.
	 *
	 * @param tree   the tree to print
	 * @param writer the writer to print to
	 */
	public static void printTo(Tree tree, PrintWriter writer) {
		Printer printer = new Printer(writer);
		printer.print(tree);
	}

	private final PrintWriter writer;
	public final boolean format;
	public final FormattingSettings formattingSettings;

	/**
	 * Creates a Printer that prints to the specified writer with no formatting and the default formatter settings.
	 *
	 * @param writer the writer to print to
	 */
	public Printer(PrintWriter writer) {
		this(writer, false, FormattingSettings.DEFAULT);
	}

	/**
	 * Creates a Printer that prints to the specified writer with the default formatter settings.
	 *
	 * @param writer the writer to print to
	 * @param format whether to format printed trees
	 */
	public Printer(PrintWriter writer, boolean format) {
		this(writer, format, FormattingSettings.DEFAULT);
	}

	/**
	 * Creates a Printer that prints to the specified writer with the specified formatter settings.
	 *
	 * @param writer             the writer to print to
	 * @param formattingSettings the settings to apply to format printed trees
	 */
	public Printer(PrintWriter writer, FormattingSettings formattingSettings) {
		this(writer, true, formattingSettings);
	}

	/**
	 * Creates a Printer that prints to the specified writer with the specified formatter settings.
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
		final STree sTree = Tree.treeOf(tree);
		final LexicalShape shape = sTree.kind.shape();
		shape.render(sTree, this);
	}

	private int indentLevel = 0;

	public void append(String string) {
		writer.append(string);
	}

	public void newLine() {
		writer.append(formattingSettings.newLine());
	}
}
