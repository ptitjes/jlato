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
import org.jlato.printer.FormattingSettings;
import org.jlato.rewrite.MatchVisitor;
import org.jlato.rewrite.Pattern;
import org.jlato.rewrite.Substitution;
import org.jlato.util.Mutation;
import org.jlato.tree.decl.CompilationUnit;
import org.jlato.tree.decl.FormalParameter;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.QualifiedType;
import org.jlato.tree.type.Type;
import org.jlato.unit.util.BaseTestFromFiles;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

import static org.jlato.rewrite.Quotes.param;
import static org.jlato.rewrite.Quotes.type;

/**
 * @author Didier Villevalois
 */
@RunWith(JUnit4.class)
public class ForAllTest extends BaseTestFromFiles {

	@Test
	public void renamedParameters() throws IOException, ParseException {
		final String original = resourceAsString("org/jlato/samples/TestClass.java");
		CompilationUnit cu = parse(original, true);

		final Pattern<FormalParameter> paramPattern = param("$t $n").or(param("$t... $n"));

		final CompilationUnit rewrote = cu.forAll(paramPattern, new MatchVisitor<FormalParameter>() {
			@Override
			public FormalParameter visit(FormalParameter p, Substitution s) {
				return p.withId(p.id().withName(new Name(p.id().name().id() + "42")));
			}
		});

		final String expected = resourceAsString("org/jlato/samples/rewrote/TestClass-renamed-parameters");
		Assert.assertEquals(expected, print(rewrote, false, FormattingSettings.Default));
	}

	@Test
	public void renamedTypes() throws IOException, ParseException {
		final String original = resourceAsString("org/jlato/samples/TestClass.java");
		CompilationUnit cu = parse(original, true);

		final Pattern<Type> typePattern = type("$t").or(type("$t<..$_>"));

		final CompilationUnit rewrote = cu.forAll(typePattern, new MatchVisitor<Type>() {
			@Override
			public Type visit(Type type, Substitution s) {
				if (type instanceof QualifiedType) {
					return ((QualifiedType) type).withName(new Mutation<Name>() {
						@Override
						public Name mutate(Name tree) {
							return tree.withId(tree.id() + "42");
						}
					});
				}
				return type;
			}
		});

		final String expected = resourceAsString("org/jlato/samples/rewrote/TestClass-renamed-types");
		Assert.assertEquals(expected, print(rewrote, false, FormattingSettings.Default));
	}
}
