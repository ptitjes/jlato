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

package org.jlato.unit.printer;

import org.jlato.parser.ParseContext;
import org.jlato.parser.ParseException;
import org.jlato.parser.Parser;
import org.jlato.parser.ParserConfiguration;
import org.jlato.printer.Printer;
import org.jlato.tree.decl.FieldDecl;
import org.jlato.tree.expr.MethodInvocationExpr;
import org.jlato.tree.name.Name;
import org.jlato.tree.stmt.ReturnStmt;
import org.jlato.tree.stmt.Stmt;
import org.jlato.tree.type.Primitive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileNotFoundException;

import static org.jlato.tree.Trees.*;

/**
 * @author Didier Villevalois
 */
@RunWith(JUnit4.class)
public class CommentsTest {

	private final ReturnStmt stmt;
	private final Name expr;

	public CommentsTest() throws ParseException {
		final Parser parser = new Parser(ParserConfiguration.Default.preserveWhitespaces(true));

		stmt = (ReturnStmt) parser.parse(ParseContext.Statement, "return foo;");
		expr = (Name) parser.parse(ParseContext.Expression, "foo");
	}

	@Test
	public void commentsOnStatements() throws ParseException {
		ReturnStmt stmt1 = stmt.insertLeadingComment("leading");
		Assert.assertEquals(
				"// leading\nreturn foo;",
				Printer.printToString(stmt1, true));
		Assert.assertArrayEquals(new String[]{"leading"}, stmt1.leadingComments());
		Assert.assertArrayEquals(new String[]{}, stmt1.trailingComments());

		ReturnStmt stmt2 = stmt1.insertTrailingComment("trailing");
		Assert.assertEquals(
				"// leading\nreturn foo; // trailing",
				Printer.printToString(stmt2, true));
		Assert.assertArrayEquals(new String[]{"leading"}, stmt2.leadingComments());
		Assert.assertArrayEquals(new String[]{"trailing"}, stmt2.trailingComments());

		ReturnStmt stmt3 = stmt
				.insertLeadingComment("leading1").insertLeadingComment("leading2")
				.insertTrailingComment("trailing1").insertTrailingComment("trailing2");
		Assert.assertEquals(
				"// leading1\n// leading2\nreturn foo; /* trailing2 */ // trailing1",
				Printer.printToString(stmt3, true));
		Assert.assertArrayEquals(new String[]{"leading1", "leading2"}, stmt3.leadingComments());
		Assert.assertArrayEquals(new String[]{"trailing2", "trailing1"}, stmt3.trailingComments());
	}

	@Test
	public void commentsOnStatementsForceMultiLine() throws ParseException {
		ReturnStmt stmt1 = stmt.insertLeadingComment("leading", true);
		Assert.assertEquals(
				"/* leading */\nreturn foo;",
				Printer.printToString(stmt1, true));
		Assert.assertArrayEquals(new String[]{"leading"}, stmt1.leadingComments());
		Assert.assertArrayEquals(new String[]{}, stmt1.trailingComments());

		ReturnStmt stmt2 = stmt1.insertTrailingComment("trailing", true);
		Assert.assertEquals(
				"/* leading */\nreturn foo; /* trailing */",
				Printer.printToString(stmt2, true));
		Assert.assertArrayEquals(new String[]{"leading"}, stmt2.leadingComments());
		Assert.assertArrayEquals(new String[]{"trailing"}, stmt2.trailingComments());

		ReturnStmt stmt3 = stmt
				.insertLeadingComment("leading1", true).insertLeadingComment("leading2", true)
				.insertTrailingComment("trailing1", true).insertTrailingComment("trailing2", true);
		Assert.assertEquals(
				"/* leading1 */\n/* leading2 */\nreturn foo; /* trailing2 */ /* trailing1 */",
				Printer.printToString(stmt3, true));
		Assert.assertArrayEquals(new String[]{"leading1", "leading2"}, stmt3.leadingComments());
		Assert.assertArrayEquals(new String[]{"trailing2", "trailing1"}, stmt3.trailingComments());
	}

	@Test
	public void commentsOnExpressions() throws ParseException {
		Name expr1 = expr.insertLeadingComment("leading");
		Assert.assertEquals(
				"/* leading */ foo",
				Printer.printToString(expr1, true));
		Assert.assertArrayEquals(new String[]{"leading"}, expr1.leadingComments());
		Assert.assertArrayEquals(new String[]{}, expr1.trailingComments());

		Name expr2 = expr1.insertTrailingComment("trailing");
		Assert.assertEquals(
				"/* leading */ foo /* trailing */",
				Printer.printToString(expr2, true));
		Assert.assertArrayEquals(new String[]{"leading"}, expr2.leadingComments());
		Assert.assertArrayEquals(new String[]{"trailing"}, expr2.trailingComments());

		Name expr3 = expr
				.insertLeadingComment("leading1").insertLeadingComment("leading2")
				.insertTrailingComment("trailing1").insertTrailingComment("trailing2");
		Assert.assertEquals(
				"/* leading1 */ /* leading2 */ foo /* trailing2 */ /* trailing1 */",
				Printer.printToString(expr3, true));
		Assert.assertArrayEquals(new String[]{"leading1", "leading2"}, expr3.leadingComments());
		Assert.assertArrayEquals(new String[]{"trailing2", "trailing1"}, expr3.trailingComments());
	}
}
