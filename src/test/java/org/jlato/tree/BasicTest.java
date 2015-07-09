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

import org.jlato.parser.ParseContext;
import org.jlato.parser.ParseException;
import org.jlato.parser.Parser;
import org.jlato.parser.ParserConfiguration;
import org.jlato.printer.FormattingSettings;
import org.jlato.printer.Printer;
import org.jlato.tree.decl.CompilationUnit;
import org.jlato.tree.decl.ImportDecl;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.*;

/**
 * @author Didier Villevalois
 */
@RunWith(JUnit4.class)
public class BasicTest {

	@Test
	public void testRun() throws FileNotFoundException, ParseException {
		final Parser parser = new Parser(ParserConfiguration.Default.preserveWhitespaces(true));
		final String content =
				"import /*before*/ org/*a*/./*b*/jlato/*c*/./*d*/tree/*e*/./*f*/Tree/*after*/;";
		final ImportDecl importDecl1 = parser.parse(ParseContext.ImportDecl, content);
		System.out.println(Printer.printToString(importDecl1, true));
		final ImportDecl importDecl2 = importDecl1.setStatic(true);
		System.out.println(Printer.printToString(importDecl2));
		final ImportDecl importDecl3 = importDecl2.setOnDemand(true);
		System.out.println(Printer.printToString(importDecl3));
		final ImportDecl importDecl4 = importDecl3.setStatic(false);
		System.out.println(Printer.printToString(importDecl4));
	}

	@Ignore
	@Test
	public void testPrinter() throws FileNotFoundException, ParseException {
		File rootDirectory = new File("src/main/java/org/jlato/tree/");

		final CompilationUnit cu = new Parser().parse(new File(rootDirectory, "Tree.java"), "UTF-8");

		System.out.print(Printer.printToString(cu));
	}

	@Ignore
	@Test
	public void javaConcepts() throws IOException, ParseException {
		final InputStream inputStream = ClassLoader.getSystemResourceAsStream("org/jlato/samples/JavaConcepts.java");
		final String original = new String(readFully(inputStream), "UTF-8");
		final Parser parser = new Parser(ParserConfiguration.Default.preserveWhitespaces(true));
		final CompilationUnit cu = parser.parse(ParseContext.CompilationUnit, original);
		Assert.assertEquals(original, Printer.printToString(cu, FormattingSettings.JavaParser));
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
