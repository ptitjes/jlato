/*
 * Copyright (C) 2015-2016 Didier Villevalois.
 *
 * This file is part of JLaTo.
 *
 * JLaTo is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * JLaTo is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JLaTo.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.jlato.unit.rewrite;

import org.jlato.parser.ParseContext;
import org.jlato.parser.ParseException;
import org.jlato.parser.Parser;
import org.jlato.pattern.Pattern;
import org.jlato.pattern.Quotes;
import org.jlato.pattern.Substitution;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;
import org.jlato.unit.util.BaseTestFromFiles;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileNotFoundException;

import static org.jlato.parser.ParseContext.Expression;
import static org.jlato.tree.Trees.*;

/**
 * @author Didier Villevalois
 */
@RunWith(JUnit4.class)
public class MatchTest extends BaseTestFromFiles {

	private final Parser parser = new Parser();

	@Test
	public void methodCalls() throws FileNotFoundException, ParseException {
		final Pattern<Expr> pattern = Quotes.expr("$p.hashCode()");

		Assert.assertFalse(pattern.matches(parse(Expression, "o.otherMethod()")));
		Assert.assertTrue(pattern.matches(parse(Expression, "o.hashCode()")));

		Assert.assertNull(pattern.match(parse(Expression, "o.otherMethod()")));
		Assert.assertNotNull(pattern.match(parse(Expression, "o.hashCode()")));

		Assert.assertFalse(parse(Expression, "o.otherMethod()").matches(pattern));
		Assert.assertTrue(parse(Expression, "o.hashCode()").matches(pattern));

		Assert.assertNull(parse(Expression, "o.otherMethod()").match(pattern));
		Assert.assertNotNull(parse(Expression, "o.hashCode()").match(pattern));

		Assert.assertTrue(parse(Expression, "o.hashCode()").match(pattern).binds("p"));
		Assert.assertEquals(name("o"), parse(Expression, "o.hashCode()").match(pattern).get("p"));

		Assert.assertNull(pattern.match(parse(Expression, "o.otherMethod()"), Substitution.empty().bind("p", name("o"))));
		Assert.assertNull(pattern.match(parse(Expression, "o.hashCode()"), Substitution.empty().bind("p", name("other"))));
		Assert.assertNotNull(pattern.match(parse(Expression, "o.hashCode()"), Substitution.empty().bind("p", name("o"))));
		Assert.assertTrue(pattern.match(parse(Expression, "o.hashCode()"), Substitution.empty().bind("p", name("o"))).binds("p"));
		Assert.assertEquals(name("o"), pattern.match(parse(Expression, "o.hashCode()"), Substitution.empty().bind("p", name("o"))).get("p"));
	}

	private <T extends Tree> T parse(ParseContext<T> context, String content) throws ParseException {
		return parser.parse(context, content);
	}
}
