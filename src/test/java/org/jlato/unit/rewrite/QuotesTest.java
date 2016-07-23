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

import org.jlato.parser.ParseContext;
import org.jlato.parser.ParseException;
import org.jlato.parser.Parser;
import org.jlato.printer.Printer;
import org.jlato.rewrite.Pattern;
import org.jlato.rewrite.Quotes;
import org.jlato.rewrite.Substitution;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.ImportDecl;
import org.jlato.tree.decl.MethodDecl;
import org.jlato.tree.decl.PackageDecl;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.name.QualifiedName;
import org.jlato.tree.stmt.Stmt;
import org.jlato.unit.util.BaseTestFromFiles;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileNotFoundException;

import static org.jlato.parser.ParseContext.*;
import static org.jlato.rewrite.Quotes.*;
import static org.jlato.tree.Trees.*;

/**
 * @author Didier Villevalois
 */
@RunWith(JUnit4.class)
public class QuotesTest extends BaseTestFromFiles {

	private final Parser parser = new Parser();

	@Test(expected = IllegalArgumentException.class)
	public void invalidQuote() throws FileNotFoundException, ParseException {
		Quotes.packageDecl("erroneous package decl");
	}

	@Test
	public void packageDecls() throws FileNotFoundException, ParseException {
		final Pattern<PackageDecl> pattern = Quotes.packageDecl("package org.jlato.tree.$p;");

		Assert.assertTrue(parse(PackageDecl, "package org.jlato.tree.decl;").matches(pattern));
		Assert.assertFalse(parse(PackageDecl, "package org.jlato.util;").matches(pattern));

		Assert.assertNotNull(parse(PackageDecl, "package org.jlato.tree.decl;").match(pattern));
		Assert.assertEquals(name("decl"), parse(PackageDecl, "package org.jlato.tree.decl;").match(pattern).get("p"));
	}

	@Test
	public void importDecls() throws FileNotFoundException, ParseException {
		final Pattern<ImportDecl> pattern = Quotes.importDecl("import org.jlato.tree.$c;");

		Assert.assertTrue(parse(ImportDecl, "import org.jlato.tree.Tree;").matches(pattern));
		Assert.assertFalse(parse(ImportDecl, "import org.jlato.util.Function1;").matches(pattern));

		Assert.assertNotNull(parse(ImportDecl, "import org.jlato.tree.Tree;").match(pattern));
		Assert.assertEquals(name("Tree"), parse(ImportDecl, "import org.jlato.tree.Tree;").match(pattern).get("c"));
	}

	@Test
	public void qualifiedNames() throws FileNotFoundException, ParseException {
		final Pattern<QualifiedName> pattern = Quotes.qualifiedName("org.jlato.$a.$b");

		Assert.assertTrue(parse(QualifiedName, "org.jlato.tree.Tree").matches(pattern));
		Assert.assertFalse(parse(QualifiedName, "java.util.Function1").matches(pattern));
		Assert.assertFalse(parse(QualifiedName, "java.util.Function1").matches(pattern));

		Assert.assertNotNull(parse(QualifiedName, "org.jlato.tree.Tree").match(pattern));
		Assert.assertEquals(name("tree"), parse(QualifiedName, "org.jlato.tree.Tree").match(pattern).get("a"));
		Assert.assertEquals(name("Tree"), parse(QualifiedName, "org.jlato.tree.Tree").match(pattern).get("b"));
	}

	@Test
	public void methodCalls() throws FileNotFoundException, ParseException {
		final Pattern<Expr> pattern = Quotes.expr("$p.hashCode()");

		Assert.assertTrue(parse(Expression, "o.hashCode()").matches(pattern));
		Assert.assertFalse(parse(Expression, "o.otherMethod()").matches(pattern));

		Assert.assertNotNull(parse(Expression, "o.hashCode()").match(pattern));
		Assert.assertNull(parse(Expression, "o.otherMethod()").match(pattern));
		Assert.assertTrue(parse(Expression, "o.hashCode()").match(pattern).binds("p"));
		Assert.assertEquals(name("o"), parse(Expression, "o.hashCode()").match(pattern).get("p"));
	}

	@Test
	public void enclosedExpr() throws FileNotFoundException, ParseException {
		Assert.assertEquals("(var ? true : false)", Printer.printToString(
				expr("($e ? true : false)").build(Substitution.empty().bind("e", name("var")))
		));
		Assert.assertEquals("( ( var ) ? true : false)", Printer.printToString(
				expr("( ( $e ) ? true : false)").build(Substitution.empty().bind("e", name("var")))
		));
	}

	@Test
	public void genMethod() {
		NodeList<Stmt> stmts = emptyList();
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

	private <T extends Tree> T parse(ParseContext<T> context, String content) throws ParseException {
		return parser.parse(context, content);
	}
}
