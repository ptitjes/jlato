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

package org.jlato.unit;

import org.jlato.internal.bu.STree;
import org.jlato.internal.td.TreeBase;
import org.jlato.parser.ParseContext;
import org.jlato.parser.ParseException;
import org.jlato.parser.Parser;
import org.jlato.parser.ParserConfiguration;
import org.jlato.printer.FormattingSettings;
import org.jlato.printer.Printer;
import org.jlato.tree.decl.CompilationUnit;
import org.jlato.tree.decl.ImportDecl;
import org.jlato.tree.expr.Expr;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.*;

/**
 * @author Didier Villevalois
 */
@RunWith(JUnit4.class)
public class SamplesTest {

	@Test
	public void testClass() throws IOException, ParseException {
		final String original = resourceAsString("org/jlato/samples/TestClass.java");
		Assert.assertEquals(original, parseAndPrint(original, false, true, FormattingSettings.Default));
		Assert.assertEquals(original, parseAndPrint(original, false, false, FormattingSettings.Default));
		Assert.assertEquals(original, parseAndPrint(original, true, false, FormattingSettings.Default));
		Assert.assertEquals(original, parseAndPrint(original, true, true, FormattingSettings.Default));
	}

	@Test
	public void javaConcepts() throws IOException, ParseException {
		final String original = resourceAsString("org/jlato/samples/JavaConcepts.java");
		Assert.assertEquals(original, parseAndPrint(original, false, true, FormattingSettings.JavaParser));
		Assert.assertEquals(original, parseAndPrint(original, false, false, FormattingSettings.JavaParser));
		Assert.assertEquals(original, parseAndPrint(original, true, false, FormattingSettings.JavaParser));
		Assert.assertEquals(original, parseAndPrint(original, true, true, FormattingSettings.JavaParser));
	}

	@Test
	public void randoopTest0() throws IOException, ParseException {
		final String original = resourceAsString("org/jlato/samples/RandoopTest0.java");
		Assert.assertEquals(original, parseAndPrint(original, true, false, FormattingSettings.Default));
	}

	private String parseAndPrint(String original, boolean preserveWhitespaces, boolean format, FormattingSettings formattingSettings) throws ParseException {
		final Parser parser = new Parser(ParserConfiguration.Default.preserveWhitespaces(preserveWhitespaces));
		final CompilationUnit cu = parser.parse(ParseContext.CompilationUnit, original);
		STree tree = TreeBase.treeOf(cu);
		tree.validate();
		return Printer.printToString(cu, format, formattingSettings);
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
