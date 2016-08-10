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

import org.jlato.parser.ParseException;
import org.jlato.printer.FormattingSettings;
import org.jlato.pattern.MatchVisitor;
import org.jlato.pattern.Matcher;
import org.jlato.pattern.Substitution;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.CompilationUnit;
import org.jlato.tree.name.Name;
import org.jlato.unit.util.BaseTestFromFiles;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

import static org.jlato.tree.Trees.listOf;
import static org.jlato.tree.Trees.name;

/**
 * @author Didier Villevalois
 */
@RunWith(JUnit4.class)
public class TraversalTest extends BaseTestFromFiles {

	@Test
	public void javaConceptLeftRightTraversal() throws IOException, ParseException {
		final String original = resourceAsString("org/jlato/samples/JavaConcepts.java");
		final CompilationUnit cu = parse(original, true);

		final CompilationUnit rewrote = cu.leftForAll(ANY_NODE, new MatchVisitor<Tree>() {
			@Override
			public Tree visit(Tree t, Substitution s) {
				return t;
			}
		});

		Assert.assertEquals(original, print(rewrote, false, FormattingSettings.Default));
	}

	@Test
	public void javaConceptRightLeftTraversal() throws IOException, ParseException {
		final String original = resourceAsString("org/jlato/samples/JavaConcepts.java");
		final CompilationUnit cu = parse(original, true);

		final CompilationUnit rewrote = cu.rightForAll(ANY_NODE, new MatchVisitor<Tree>() {
			@Override
			public Tree visit(Tree t, Substitution s) {
				return t;
			}
		});

		Assert.assertEquals(original, print(rewrote, false, FormattingSettings.Default));
	}

	@Test
	public void javaConceptLeftRightFindAll() throws IOException, ParseException {
		final String original = resourceAsString("org/jlato/samples/JavaConcepts.java");
		final CompilationUnit cu = parse(original, true);

		final int nodeCount = countIterable(cu.findAll(ANY_NODE));

		Assert.assertEquals(3619, nodeCount);
	}

	@Test
	public void javaConceptRightLeftFindAll() throws IOException, ParseException {
		final String original = resourceAsString("org/jlato/samples/JavaConcepts.java");
		final CompilationUnit cu = parse(original, true);

		final int nodeCount = countIterable(cu.rightFindAll(ANY_NODE));

		Assert.assertEquals(3619, nodeCount);
	}

	@Test
	public void findAllOrder() throws IOException, ParseException {
		NodeList<Name> names = listOf(name("1"), name("2"), name("3"), name("4"), name("5"));

		Matcher<Name> oddNameMatcher = new Matcher<Name>() {
			@Override
			public Substitution match(Object object, Substitution substitution) {
				return object instanceof Name && Integer.parseInt(((Name) object).id()) % 2 == 1 ? substitution : null;
			}
		};

		Assert.assertEquals(3, countIterable(names.leftFindAll(oddNameMatcher)));
		Assert.assertEquals(3, countIterable(names.rightFindAll(oddNameMatcher)));
		Assert.assertArrayEquals(new String[]{"1", "3", "5"}, nameIterableAsArray(names.leftFindAll(oddNameMatcher)));
		Assert.assertArrayEquals(new String[]{"5", "3", "1"}, nameIterableAsArray(names.rightFindAll(oddNameMatcher)));
	}

	public static final Matcher<Tree> ANY_NODE = new Matcher<Tree>() {
		@Override
		public Substitution match(Object object, Substitution substitution) {
			return substitution;
		}
	};

	private int countIterable(Iterable<? extends Tree> trees) {
		int count = 0;
		for (Tree tree : trees) {
			count++;
		}
		return count;
	}

	private String[] nameIterableAsArray(Iterable<Name> names) {
		java.util.ArrayList<String> nameStrings = new java.util.ArrayList<String>();
		for (Name name : names) {
			nameStrings.add(name.id());
		}
		return nameStrings.toArray(new String[nameStrings.size()]);
	}
}
