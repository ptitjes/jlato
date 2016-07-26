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
import org.jlato.tree.name.Name;
import org.jlato.tree.name.QualifiedName;
import org.jlato.tree.stmt.Stmt;
import org.jlato.unit.util.BaseTestFromFiles;
import org.jlato.util.Function1;
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
public class PatternTest extends BaseTestFromFiles {

	private final Parser parser = new Parser();

	@Test
	public void or() throws FileNotFoundException, ParseException {
		final Pattern<org.jlato.tree.name.QualifiedName> pattern =
				Quotes.qualifiedName("org.jlato.tree.$a").or(Quotes.qualifiedName("org.jlato.util.$a"));

		Assert.assertTrue(parse(QualifiedName, "org.jlato.tree.Tree").matches(pattern));
		Assert.assertTrue(parse(QualifiedName, "org.jlato.util.Function1").matches(pattern));
		Assert.assertFalse(parse(QualifiedName, "org.jlato.pattern.Pattern").matches(pattern));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void orCantBeBuilt() throws FileNotFoundException, ParseException {
		final Pattern<org.jlato.tree.name.QualifiedName> pattern =
				Quotes.qualifiedName("org.jlato.tree.$a").or(Quotes.qualifiedName("org.jlato.util.$a"));

		pattern.build(Substitution.empty());
	}

	@Test
	public void suchThat() throws FileNotFoundException, ParseException {
		final Pattern<org.jlato.tree.name.QualifiedName> pattern1 = Quotes.qualifiedName("org.jlato.$a.$b");
		final Pattern<QualifiedName> pattern2 = pattern1.suchThat(new Function1<QualifiedName, Boolean>() {
			@Override
			public Boolean apply(QualifiedName o) {
				return o.toString().equals("org.jlato.tree.Tree");
			}
		});

		Assert.assertTrue(parse(QualifiedName, "org.jlato.tree.Tree").matches(pattern1));
		Assert.assertTrue(parse(QualifiedName, "org.jlato.util.Function1").matches(pattern1));
		Assert.assertTrue(parse(QualifiedName, "org.jlato.tree.Tree").matches(pattern2));
		Assert.assertFalse(parse(QualifiedName, "org.jlato.util.Function1").matches(pattern2));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void suchThatCantBeBuilt() throws FileNotFoundException, ParseException {
		final Pattern<org.jlato.tree.name.QualifiedName> pattern1 = Quotes.qualifiedName("org.jlato.$a.$b");
		final Pattern<QualifiedName> pattern2 = pattern1.suchThat(new Function1<QualifiedName, Boolean>() {
			@Override
			public Boolean apply(QualifiedName o) {
				return o.toString().equals("org.jlato.tree.Tree");
			}
		});

		pattern2.build(Substitution.empty());
	}

	@Test
	public void suchThatVar() throws FileNotFoundException, ParseException {
		final Pattern<org.jlato.tree.name.QualifiedName> pattern1 = Quotes.qualifiedName("org.jlato.$a.$b");
		final Pattern<QualifiedName> pattern2 = pattern1.suchThat("a", new Function1<Name, Boolean>() {
			@Override
			public Boolean apply(Name o) {
				return o.id().startsWith("t");
			}
		});

		Assert.assertTrue(parse(QualifiedName, "org.jlato.tree.Tree").matches(pattern1));
		Assert.assertTrue(parse(QualifiedName, "org.jlato.util.Function1").matches(pattern1));
		Assert.assertTrue(parse(QualifiedName, "org.jlato.tree.Tree").matches(pattern2));
		Assert.assertFalse(parse(QualifiedName, "org.jlato.util.Function1").matches(pattern2));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void suchThatVarCantBeBuilt() throws FileNotFoundException, ParseException {
		final Pattern<org.jlato.tree.name.QualifiedName> pattern1 = Quotes.qualifiedName("org.jlato.$a.$b");
		final Pattern<QualifiedName> pattern2 = pattern1.suchThat("a", new Function1<Name, Boolean>() {
			@Override
			public Boolean apply(Name o) {
				return o.id().startsWith("t");
			}
		});

		pattern2.build(Substitution.empty());
	}

	private <T extends Tree> T parse(ParseContext<T> context, String content) throws ParseException {
		return parser.parse(context, content);
	}
}
