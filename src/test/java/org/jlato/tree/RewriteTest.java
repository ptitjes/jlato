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

package org.jlato.tree;

import org.jlato.rewrite.*;
import org.jlato.parser.ParseContext;
import org.jlato.parser.ParseException;
import org.jlato.parser.Parser;
import org.jlato.parser.ParserConfiguration;
import org.jlato.printer.FormattingSettings;
import org.jlato.printer.Printer;
import org.jlato.tree.decl.CompilationUnit;
import org.jlato.tree.decl.FormalParameter;
import org.jlato.tree.type.Type;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.*;

import static org.jlato.rewrite.Quotes.expr;
import static org.jlato.rewrite.Quotes.param;
import static org.jlato.rewrite.Quotes.type;
import static org.jlato.rewrite.Traversal.forAll;

/**
 * @author Didier Villevalois
 */
@RunWith(JUnit4.class)
public class RewriteTest {

	@Test
	public void testTraverse() throws IOException, ParseException {
		final String original = resourceAsString("org/jlato/samples/TestClass.java");
		CompilationUnit cu = parse(original, true);

		forAll(param("$t $n").or(param("$t... $n")), cu, new Traversal.Visitor<FormalParameter>() {
			@Override
			public FormalParameter visit(FormalParameter formalParameter) {
				System.out.println(printToString(formalParameter, false, FormattingSettings.Default));
				return formalParameter;
			}
		});
	}

	@Ignore
	@Test
	public void testClass() throws IOException, ParseException {
		final String original = resourceAsString("org/jlato/samples/TestClass.java");

		Rewriter rewriter = expr("$t.<$e>factory().newBuilder()").rewriteTo(expr("Builder.<$t<$e>>of()"));

		String rewrote = parseRewriteAndPrint(original, true, rewriter, false, FormattingSettings.Default);
		System.out.println(rewrote);
	}

	private String parseRewriteAndPrint(String original, boolean preserveWhitespaces, Rewriter rewriter, boolean format, FormattingSettings formattingSettings) throws ParseException {
		final CompilationUnit cu = parse(original, preserveWhitespaces);
		CompilationUnit rewrote = RewriteStrategy.OutermostNeeded.rewrite(cu, rewriter);
		return printToString(rewrote, format, formattingSettings);
	}

	private String printToString(Tree tree, boolean format, FormattingSettings formattingSettings) {
		return Printer.printToString(tree, format, formattingSettings);
	}

	private CompilationUnit parse(String original, boolean preserveWhitespaces) throws ParseException {
		final Parser parser = new Parser(ParserConfiguration.Default.preserveWhitespaces(preserveWhitespaces));
		return parser.parse(ParseContext.CompilationUnit, original);
	}

	private String fileAsString(String name) throws IOException {
		final InputStream inputStream = new FileInputStream(name);
		return new String(readFully(inputStream), "UTF-8");
	}

	private String resourceAsString(String name) throws IOException {
		final InputStream inputStream = ClassLoader.getSystemResourceAsStream(name);
		return new String(readFully(inputStream), "UTF-8");
	}

	private byte[] readFully(InputStream inputStream)
			throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length = 0;
		while ((length = inputStream.read(buffer)) != -1) {
			baos.write(buffer, 0, length);
		}
		return baos.toByteArray();
	}
}
