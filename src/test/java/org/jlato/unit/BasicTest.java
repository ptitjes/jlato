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

import org.jlato.parser.ParseContext;
import org.jlato.parser.ParseException;
import org.jlato.parser.Parser;
import org.jlato.parser.ParserConfiguration;
import org.jlato.printer.Printer;
import org.jlato.tree.decl.ImportDecl;
import org.jlato.tree.expr.Expr;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author Didier Villevalois
 */
@RunWith(JUnit4.class)
public class BasicTest {

	@Test
	public void testRunImportDecl() throws FileNotFoundException, ParseException {
		final Parser parser = new Parser(ParserConfiguration.Default.preserveWhitespaces(true));
		final String content = "/*prolog*/ import /*before*/ org/*a*/./*b*/jlato/*c*/./*d*/tree/*e*/./*f*/Tree/*after*/; /*epilog*/";

		final ImportDecl importDecl1 = parser.parse(ParseContext.ImportDecl, content);
		Assert.assertEquals(
				"/*prolog*/ import /*before*/ org/*a*/./*b*/jlato/*c*/./*d*/tree/*e*/./*f*/Tree/*after*/; /*epilog*/",
				Printer.printToString(importDecl1, true)
		);

		final ImportDecl importDecl2 = importDecl1.setStatic(true);
		Assert.assertEquals(
				"/*prolog*/ import static /*before*/ org/*a*/./*b*/jlato/*c*/./*d*/tree/*e*/./*f*/Tree/*after*/; /*epilog*/",
				Printer.printToString(importDecl2, true)
		);

		final ImportDecl importDecl3 = importDecl2.setOnDemand(true);
		Assert.assertEquals(
				"/*prolog*/ import static /*before*/ org/*a*/./*b*/jlato/*c*/./*d*/tree/*e*/./*f*/Tree/*after*/.*; /*epilog*/",
				Printer.printToString(importDecl3, true)
		);

		final ImportDecl importDecl4 = importDecl3.setStatic(false);
		Assert.assertEquals(
				"/*prolog*/ import /*before*/ org/*a*/./*b*/jlato/*c*/./*d*/tree/*e*/./*f*/Tree/*after*/.*; /*epilog*/",
				Printer.printToString(importDecl4, true)
		);
	}

	@Test
	public void testRunMethodInvocationExpr() throws FileNotFoundException, ParseException {
		final Parser parser = new Parser(ParserConfiguration.Default.preserveWhitespaces(true));
		final String content = "scope/*a*/./*a*/method /*c*/ (/*1*/arg1/*2*/, /*3*/arg2/*4*/)";

		final Expr expr1 = parser.parse(ParseContext.Expression, content);
		Assert.assertEquals(
				"scope/*a*/./*a*/method /*c*/ (/*1*/arg1/*2*/, /*3*/arg2/*4*/)",
				Printer.printToString(expr1, false)
		);
	}

	@Test
	public void nameTest() throws IOException, ParseException {
		final String original = "$name";
		final Parser parser = new Parser(ParserConfiguration.Default.preserveWhitespaces(true));
		final Expr e = parser.parse(ParseContext.Expression, original);
	}
}
