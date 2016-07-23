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
import org.jlato.tree.decl.MethodDecl;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.stmt.Stmt;
import org.jlato.unit.util.BaseTestFromFiles;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileNotFoundException;

import static org.jlato.rewrite.Quotes.*;
import static org.jlato.tree.Trees.*;

/**
 * @author Didier Villevalois
 */
@RunWith(JUnit4.class)
public class MatchTest extends BaseTestFromFiles {

	@Test
	public void methodCalls() throws FileNotFoundException, ParseException {
		final Pattern<Expr> expr = Quotes.expr("$p.hashCode()");

		Assert.assertFalse(parseExpr("o.otherMethod()").matches(expr));
		Assert.assertTrue(parseExpr("o.hashCode()").matches(expr));

		Assert.assertNull(parseExpr("o.otherMethod()").match(expr));
		Assert.assertNotNull(parseExpr("o.hashCode()").match(expr));
		Assert.assertTrue(parseExpr("o.hashCode()").match(expr).binds("p"));
		Assert.assertEquals(name("o"), parseExpr("o.hashCode()").match(expr).get("p"));

		Assert.assertNull(expr.match(parseExpr("o.otherMethod()"), Substitution.empty().bind("p", name("o"))));
		Assert.assertNull(expr.match(parseExpr("o.hashCode()"), Substitution.empty().bind("p", name("other"))));
		Assert.assertNotNull(expr.match(parseExpr("o.hashCode()"), Substitution.empty().bind("p", name("o"))));
		Assert.assertTrue(expr.match(parseExpr("o.hashCode()"), Substitution.empty().bind("p", name("o"))).binds("p"));
		Assert.assertEquals(name("o"), expr.match(parseExpr("o.hashCode()"), Substitution.empty().bind("p", name("o"))).get("p"));
	}

	private Expr parseExpr(String content) throws ParseException {
		return new Parser().parse(ParseContext.Expression, content);
	}
}
