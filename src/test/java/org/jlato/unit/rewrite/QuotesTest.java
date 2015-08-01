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

package org.jlato.unit.rewrite;

import org.jlato.parser.ParseException;
import org.jlato.printer.Printer;
import org.jlato.rewrite.Pattern;
import org.jlato.rewrite.Substitution;
import org.jlato.tree.NodeList;
import org.jlato.tree.decl.FormalParameter;
import org.jlato.tree.decl.MemberDecl;
import org.jlato.tree.decl.MethodDecl;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.expr.LiteralExpr;
import org.jlato.tree.name.Name;
import org.jlato.tree.stmt.Stmt;
import org.jlato.tree.type.PrimitiveType;
import org.jlato.tree.type.Type;
import org.jlato.unit.util.BaseTestFromFiles;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileNotFoundException;

import static org.jlato.rewrite.Quotes.expr;
import static org.jlato.rewrite.Quotes.memberDecl;
import static org.jlato.rewrite.Quotes.stmt;
import static org.jlato.tree.NodeOption.some;
import static org.jlato.tree.TreeFactory.*;
import static org.jlato.tree.expr.BinaryExpr.BinaryOp.NotEqual;

/**
 * @author Didier Villevalois
 */
@RunWith(JUnit4.class)
public class QuotesTest extends BaseTestFromFiles {

	@Test
	public void methodCalls() throws FileNotFoundException, ParseException {
		final Pattern<Expr> expr = expr("$p.hashCode()");
	}

	@Test
	public void enclosedExpr() throws FileNotFoundException, ParseException {
		Assert.assertEquals("(var ? true : false)", Printer.printToString(
				expr("($e ? true : false)").build(Substitution.empty().bind("e", new Name("var")))
		));
		Assert.assertEquals("( ( var ) ? true : false)", Printer.printToString(
				expr("( ( $e ) ? true : false)").build(Substitution.empty().bind("e", new Name("var")))
		));
	}

	@Test
	public void genMethod() {
		NodeList<Stmt> stmts = NodeList.empty();
		stmts = stmts.append(stmt("int result = 17;").build());
		stmts = stmts.append(stmt("if (val1 != null) result = 37 * result + val1.hashCode();").build());
		stmts = stmts.append(stmt("if (val2 != null) result = 37 * result + val2.hashCode();").build());
		stmts = stmts.append(stmt("return result;").build());

		MethodDecl decl = (MethodDecl) memberDecl("@Override public int hashCode() { ..$_ }").build();
		decl = decl.withBody(some(blockStmt().withStmts(stmts)));
		Assert.assertEquals(
				"@Override public int hashCode() {\n" +
						"\tint result = 17;\n" +
						"\tif (val1 != null) result = 37 * result + val1.hashCode();\n" +
						"\tif (val2 != null) result = 37 * result + val2.hashCode();\n" +
						"\treturn result;\n" +
						"}",
				Printer.printToString(decl));
	}
}
