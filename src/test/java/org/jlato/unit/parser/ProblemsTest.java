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

package org.jlato.unit.parser;

import org.jlato.parser.ParseContext;
import org.jlato.parser.ParseException;
import org.jlato.parser.Parser;
import org.jlato.parser.ParserConfiguration;
import org.jlato.tree.Problem;
import org.jlato.tree.decl.CompilationUnit;
import org.jlato.tree.decl.InterfaceDecl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Iterator;

/**
 * @author Didier Villevalois
 */
@RunWith(JUnit4.class)
public class ProblemsTest {

	@Test
	public void testProblems() throws ParseException {
		final Parser parser = new Parser();
		doTestProblems(parser);
	}

	@Test
	public void testProblemsWithWhitespaces() throws ParseException {
		final Parser parser = new Parser(ParserConfiguration.Default.preserveWhitespaces(true));
		doTestProblems(parser);
	}

	private void doTestProblems(Parser parser) throws ParseException {
		final String content = "package test;" +
				"interface Test {\n" +
				"\t\t\tdefault void test();\n" +
				"\t\t}";

		final CompilationUnit cu = parser.parse(ParseContext.CompilationUnit, content);
		Assert.assertTrue(cu.hasProblems());
		Iterator<Problem> problemIterator = cu.problems().iterator();

		Assert.assertTrue(problemIterator.hasNext());

		Problem problem = problemIterator.next();
		Assert.assertEquals(Problem.Severity.ERROR, problem.severity());
		Assert.assertEquals("Default methods must have a body", problem.code());
		Assert.assertEquals(((InterfaceDecl) cu.types().first()).members().first(), problem.tree());

		Assert.assertFalse(problemIterator.hasNext());
	}
}
