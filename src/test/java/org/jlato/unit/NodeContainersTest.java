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
import org.jlato.tree.*;
import org.jlato.tree.expr.*;
import org.jlato.tree.name.*;
import org.jlato.tree.stmt.*;
import org.jlato.util.Function1;
import org.jlato.util.Mutation;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.jlato.tree.Trees.*;

/**
 * @author Didier Villevalois
 */
@RunWith(JUnit4.class)
public class NodeContainersTest {

	@Test
	public void nodeListManipulations() throws FileNotFoundException, ParseException {
		final Parser parser = new Parser(ParserConfiguration.Default.preserveWhitespaces(true));
		final String content = "scope.method(arg1, arg2)";
		final MethodInvocationExpr expr = (MethodInvocationExpr) parser.parse(ParseContext.Expression, content);

		Assert.assertTrue(expr.args().contains(name("arg1")));
		Assert.assertTrue(expr.args().contains(name("arg2")));

		Assert.assertEquals(
				"scope.method(newArg, arg2)",
				Printer.printToString(expr.withArgs(
						expr.args().set(0, name("newArg"))
				), false)
		);
		Assert.assertEquals(
				"scope.method(arg1, newArg)",
				Printer.printToString(expr.withArgs(
						expr.args().set(1, name("newArg"))
				), false)
		);
		Assert.assertEquals(
				"scope.method(arg1, arg2, newArg)",
				Printer.printToString(expr.withArgs(
						expr.args().append(name("newArg"))
				), false)
		);
		Assert.assertEquals(
				"scope.method(arg1, arg2, newArg1, newArg2)",
				Printer.printToString(expr.withArgs(
						expr.args().appendAll(listOf(name("newArg1"), name("newArg2")))
				), false)
		);
		Assert.assertEquals(
				"scope.method(newArg, arg1, arg2)",
				Printer.printToString(expr.withArgs(
						expr.args().prepend(name("newArg"))
				), false)
		);
		Assert.assertEquals(
				"scope.method(newArg1, newArg2, arg1, arg2)",
				Printer.printToString(expr.withArgs(
						expr.args().prependAll(listOf(name("newArg1"), name("newArg2")))
				), false)
		);
		Assert.assertEquals(
				"scope.method(arg1, newArg, arg2)",
				Printer.printToString(expr.withArgs(
						expr.args().insert(1, name("newArg"))
				), false)
		);
		Assert.assertEquals(
				"scope.method(arg1, newArg1, newArg2, arg2)",
				Printer.printToString(expr.withArgs(
						expr.args().insertAll(1, listOf(name("newArg1"), name("newArg2")))
				), false)
		);
	}

	@Test
	public void nodeListManipulationsWithComments() throws FileNotFoundException, ParseException {
		final Parser parser = new Parser(ParserConfiguration.Default.preserveWhitespaces(true));
		final String content = "scope.method(/*1*/arg1/*2*/, /*3*/arg2/*4*/)";
		final MethodInvocationExpr expr = (MethodInvocationExpr) parser.parse(ParseContext.Expression, content);

		Assert.assertTrue(expr.args().contains(name("arg1")));
		Assert.assertTrue(expr.args().contains(name("arg2")));

		Assert.assertEquals(
				"scope.method(newArg, /*3*/arg2/*4*/)",
				Printer.printToString(expr.withArgs(
						expr.args().set(0, name("newArg"))
				), false)
		);
		Assert.assertEquals(
				"scope.method(/*1*/arg1/*2*/, newArg)",
				Printer.printToString(expr.withArgs(
						expr.args().set(1, name("newArg"))
				), false)
		);
		Assert.assertEquals(
				"scope.method(/*1*/arg1/*2*/, /*3*/arg2/*4*/, newArg)",
				Printer.printToString(expr.withArgs(
						expr.args().append(name("newArg"))
				), false)
		);
		Assert.assertEquals(
				"scope.method(/*1*/arg1/*2*/, /*3*/arg2/*4*/, newArg1, newArg2)",
				Printer.printToString(expr.withArgs(
						expr.args().appendAll(listOf(name("newArg1"), name("newArg2")))
				), false)
		);
		Assert.assertEquals(
				"scope.method(newArg, /*1*/arg1/*2*/, /*3*/arg2/*4*/)",
				Printer.printToString(expr.withArgs(
						expr.args().prepend(name("newArg"))
				), false)
		);
		Assert.assertEquals(
				"scope.method(newArg1, newArg2, /*1*/arg1/*2*/, /*3*/arg2/*4*/)",
				Printer.printToString(expr.withArgs(
						expr.args().prependAll(listOf(name("newArg1"), name("newArg2")))
				), false)
		);
		Assert.assertEquals(
				"scope.method(/*1*/arg1/*2*/, newArg, /*3*/arg2/*4*/)",
				Printer.printToString(expr.withArgs(
						expr.args().insert(1, name("newArg"))
				), false)
		);
		Assert.assertEquals(
				"scope.method(/*1*/arg1/*2*/, newArg1, newArg2, /*3*/arg2/*4*/)",
				Printer.printToString(expr.withArgs(
						expr.args().insertAll(1, listOf(name("newArg1"), name("newArg2")))
				), false)
		);
		Assert.assertEquals(
				"scope.method(/*1*/arg1/*2*/, newArg2, newArg1, /*3*/arg2/*4*/)",
				Printer.printToString(expr.withArgs(
						expr.args().insert(1, name("newArg1")).insert(name("newArg1"), name("newArg2"))
				), false)
		);
		Assert.assertEquals(
				"scope.method(/*1*/arg1/*2*/, newArg2, newArg3, newArg1, /*3*/arg2/*4*/)",
				Printer.printToString(expr.withArgs(
						expr.args().insert(1, name("newArg1")).insertAll(name("newArg1"), listOf(name("newArg2"), name("newArg3")))
				), false)
		);
		Assert.assertEquals(
				"scope.method(/*1*/arg1/*2*/, newArg2, /*3*/arg2/*4*/)",
				Printer.printToString(expr.withArgs(
						expr.args().insertAll(1, listOf(name("newArg1"), name("newArg2"))).delete(1)
				), false)
		);
		Assert.assertEquals(
				"scope.method(/*3*/arg2/*4*/)",
				Printer.printToString(expr.withArgs(
						expr.args().delete(0)
				), false)
		);
		Assert.assertEquals(
				"scope.method()",
				Printer.printToString(expr.withArgs(
						expr.args().delete(0).delete(0)
				), false)
		);
		Assert.assertEquals(
				"scope.method(/*1*/arg1/*2*/, newArg2, /*3*/arg2/*4*/)",
				Printer.printToString(expr.withArgs(
						expr.args().insertAll(1, listOf(name("newArg1"), name("newArg2"))).delete(name("newArg1"))
				), false)
		);
	}

	@Test
	public void nodeListManipulationsWithCommentsAndNewComments() throws FileNotFoundException, ParseException {
		final Parser parser = new Parser(ParserConfiguration.Default.preserveWhitespaces(true));
		final String content = "scope.method(/*1*/arg1/*2*/, /*3*/arg2/*4*/)";
		final MethodInvocationExpr expr = (MethodInvocationExpr) parser.parse(ParseContext.Expression, content);

		Assert.assertTrue(expr.args().contains(name("arg1")));
		Assert.assertTrue(expr.args().contains(name("arg2")));

		Assert.assertEquals(
				"scope.method(/*leading*/ newArg /*trailing*/, /*3*/arg2/*4*/)",
				Printer.printToString(expr.withArgs(
						expr.args().set(0, newArgWithComments())
				), false)
		);
		Assert.assertEquals(
				"scope.method(/*1*/arg1/*2*/, /*leading*/ newArg /*trailing*/)",
				Printer.printToString(expr.withArgs(
						expr.args().set(1, newArgWithComments())
				), false)
		);
		Assert.assertEquals(
				"scope.method(/*1*/arg1/*2*/, /*3*/arg2/*4*/, /*leading*/ newArg /*trailing*/)",
				Printer.printToString(expr.withArgs(
						expr.args().append(newArgWithComments())
				), false)
		);
		Assert.assertEquals(
				"scope.method(/*1*/arg1/*2*/, /*3*/arg2/*4*/, /*leading1*/ newArg1 /*trailing1*/, /*leading2*/ newArg2 /*trailing2*/)",
				Printer.printToString(expr.withArgs(
						expr.args().appendAll(listOf(newArgWithComments(1), newArgWithComments(2)))
				), false)
		);
		Assert.assertEquals(
				"scope.method(/*leading*/ newArg /*trailing*/, /*1*/arg1/*2*/, /*3*/arg2/*4*/)",
				Printer.printToString(expr.withArgs(
						expr.args().prepend(newArgWithComments())
				), false)
		);
		Assert.assertEquals(
				"scope.method(/*leading1*/ newArg1 /*trailing1*/, /*leading2*/ newArg2 /*trailing2*/, /*1*/arg1/*2*/, /*3*/arg2/*4*/)",
				Printer.printToString(expr.withArgs(
						expr.args().prependAll(listOf(newArgWithComments(1), newArgWithComments(2)))
				), false)
		);
		Assert.assertEquals(
				"scope.method(/*1*/arg1/*2*/, /*leading*/ newArg /*trailing*/, /*3*/arg2/*4*/)",
				Printer.printToString(expr.withArgs(
						expr.args().insert(1, newArgWithComments())
				), false)
		);
		Assert.assertEquals(
				"scope.method(/*1*/arg1/*2*/, /*leading1*/ newArg1 /*trailing1*/, /*leading2*/ newArg2 /*trailing2*/, /*3*/arg2/*4*/)",
				Printer.printToString(expr.withArgs(
						expr.args().insertAll(1, listOf(newArgWithComments(1), newArgWithComments(2)))
				), false)
		);
	}

	private Expr newArgWithComments() {
		return name("newArg").insertLeadingComment("/*leading*/").insertTrailingComment("/*trailing*/");
	}

	private Expr newArgWithComments(int index) {
		return name("newArg" + index).insertLeadingComment("/*leading" + index + "*/").insertTrailingComment("/*trailing" + index + "*/");
	}

	@Test
	public void nodeListStaticCreationHelpers() {
		assertNodeListContent(listOf(Arrays.asList(indexedName(0), indexedName(1), indexedName(2))), 3);

		assertNodeListContent(Trees.<Name>emptyList(), 0);
		assertNodeListContent(listOf(indexedName(0)), 1);
		assertNodeListContent(listOf(indexedName(0), indexedName(1)), 2);
		assertNodeListContent(listOf(indexedName(0), indexedName(1), indexedName(2)), 3);
		assertNodeListContent(listOf(indexedName(0), indexedName(1), indexedName(2), indexedName(3)), 4);
		assertNodeListContent(listOf(indexedName(0), indexedName(1), indexedName(2), indexedName(3), indexedName(4)), 5);
		assertNodeListContent(listOf(indexedName(0), indexedName(1), indexedName(2), indexedName(3), indexedName(4), indexedName(5)), 6);
		assertNodeListContent(listOf(indexedName(0), indexedName(1), indexedName(2), indexedName(3), indexedName(4), indexedName(5), indexedName(6)), 7);
		assertNodeListContent(listOf(indexedName(0), indexedName(1), indexedName(2), indexedName(3), indexedName(4), indexedName(5), indexedName(6), indexedName(7)), 8);
		assertNodeListContent(listOf(indexedName(0), indexedName(1), indexedName(2), indexedName(3), indexedName(4), indexedName(5), indexedName(6), indexedName(7), indexedName(8)), 9);
		assertNodeListContent(listOf(indexedName(0), indexedName(1), indexedName(2), indexedName(3), indexedName(4), indexedName(5), indexedName(6), indexedName(7), indexedName(8), indexedName(9)), 10);
		assertNodeListContent(listOf(indexedName(0), indexedName(1), indexedName(2), indexedName(3), indexedName(4), indexedName(5), indexedName(6), indexedName(7), indexedName(8), indexedName(9), indexedName(10)), 11);
		assertNodeListContent(listOf(indexedName(0), indexedName(1), indexedName(2), indexedName(3), indexedName(4), indexedName(5), indexedName(6), indexedName(7), indexedName(8), indexedName(9), indexedName(10), indexedName(11)), 12);
		assertNodeListContent(listOf(indexedName(0), indexedName(1), indexedName(2), indexedName(3), indexedName(4), indexedName(5), indexedName(6), indexedName(7), indexedName(8), indexedName(9), indexedName(10), indexedName(11), indexedName(12)), 13);
		assertNodeListContent(listOf(indexedName(0), indexedName(1), indexedName(2), indexedName(3), indexedName(4), indexedName(5), indexedName(6), indexedName(7), indexedName(8), indexedName(9), indexedName(10), indexedName(11), indexedName(12), indexedName(13)), 14);
		assertNodeListContent(listOf(indexedName(0), indexedName(1), indexedName(2), indexedName(3), indexedName(4), indexedName(5), indexedName(6), indexedName(7), indexedName(8), indexedName(9), indexedName(10), indexedName(11), indexedName(12), indexedName(13), indexedName(14)), 15);
		assertNodeListContent(listOf(indexedName(0), indexedName(1), indexedName(2), indexedName(3), indexedName(4), indexedName(5), indexedName(6), indexedName(7), indexedName(8), indexedName(9), indexedName(10), indexedName(11), indexedName(12), indexedName(13), indexedName(14), indexedName(15)), 16);
		assertNodeListContent(listOf(indexedName(0), indexedName(1), indexedName(2), indexedName(3), indexedName(4), indexedName(5), indexedName(6), indexedName(7), indexedName(8), indexedName(9), indexedName(10), indexedName(11), indexedName(12), indexedName(13), indexedName(14), indexedName(15), indexedName(16)), 17);
		assertNodeListContent(listOf(indexedName(0), indexedName(1), indexedName(2), indexedName(3), indexedName(4), indexedName(5), indexedName(6), indexedName(7), indexedName(8), indexedName(9), indexedName(10), indexedName(11), indexedName(12), indexedName(13), indexedName(14), indexedName(15), indexedName(16), indexedName(17)), 18);
		assertNodeListContent(listOf(indexedName(0), indexedName(1), indexedName(2), indexedName(3), indexedName(4), indexedName(5), indexedName(6), indexedName(7), indexedName(8), indexedName(9), indexedName(10), indexedName(11), indexedName(12), indexedName(13), indexedName(14), indexedName(15), indexedName(16), indexedName(17), indexedName(18)), 19);
		assertNodeListContent(listOf(indexedName(0), indexedName(1), indexedName(2), indexedName(3), indexedName(4), indexedName(5), indexedName(6), indexedName(7), indexedName(8), indexedName(9), indexedName(10), indexedName(11), indexedName(12), indexedName(13), indexedName(14), indexedName(15), indexedName(16), indexedName(17), indexedName(18), indexedName(19)), 20);
		assertNodeListContent(listOf(indexedName(0), indexedName(1), indexedName(2), indexedName(3), indexedName(4), indexedName(5), indexedName(6), indexedName(7), indexedName(8), indexedName(9), indexedName(10), indexedName(11), indexedName(12), indexedName(13), indexedName(14), indexedName(15), indexedName(16), indexedName(17), indexedName(18), indexedName(19), indexedName(20)), 21);
		assertNodeListContent(listOf(indexedName(0), indexedName(1), indexedName(2), indexedName(3), indexedName(4), indexedName(5), indexedName(6), indexedName(7), indexedName(8), indexedName(9), indexedName(10), indexedName(11), indexedName(12), indexedName(13), indexedName(14), indexedName(15), indexedName(16), indexedName(17), indexedName(18), indexedName(19), indexedName(20), indexedName(21)), 22);
		assertNodeListContent(listOf(indexedName(0), indexedName(1), indexedName(2), indexedName(3), indexedName(4), indexedName(5), indexedName(6), indexedName(7), indexedName(8), indexedName(9), indexedName(10), indexedName(11), indexedName(12), indexedName(13), indexedName(14), indexedName(15), indexedName(16), indexedName(17), indexedName(18), indexedName(19), indexedName(20), indexedName(21), indexedName(22)), 23);
	}

	private void assertNodeListContent(NodeList<? extends Expr> list, int expectedSize) {
		Assert.assertEquals(expectedSize, list.size());
		for (int i = 0; i < expectedSize; i++) {
			Assert.assertEquals(indexedName(i), list.get(i));
		}
	}

	@Test
	public void nodeListFirstLast() {
		NodeList<Name> list = listOf(indexedName(0), indexedName(1), indexedName(2));
		Assert.assertEquals(indexedName(0), list.first());
		Assert.assertEquals(indexedName(2), list.last());
	}

	@Test
	public void nodeListMap() {
		NodeList<Name> list = listOf(indexedName(0), indexedName(1), indexedName(2)).map(new Function1<Name, Name>() {
			@Override
			public Name apply(Name name) {
				return name("prefix" + name.id());
			}
		});
		Assert.assertEquals(indexedName("prefix", 0), list.get(0));
		Assert.assertEquals(indexedName("prefix", 1), list.get(1));
		Assert.assertEquals(indexedName("prefix", 2), list.get(2));
	}

	@Test
	public void nodeListExists() {
		NodeList<Name> list = listOf(indexedName(0), indexedName(1), indexedName(2));
		Assert.assertTrue(list.exists(new Function1<Name, Boolean>() {
			@Override
			public Boolean apply(Name name) {
				return name.id().equals("name1");
			}
		}));
		Assert.assertFalse(list.exists(new Function1<Name, Boolean>() {
			@Override
			public Boolean apply(Name name) {
				return name.id().equals("name3");
			}
		}));
	}

	@Test
	public void nodeListFilter() {
		NodeList<Name> list = listOf(indexedName(0), indexedName(1), indexedName(2));
		Assert.assertEquals(1, list.filter(new Function1<Name, Boolean>() {
			@Override
			public Boolean apply(Name name) {
				return name.id().equals("name1");
			}
		}).size());
		Assert.assertEquals(2, list.filter(new Function1<Name, Boolean>() {
			@Override
			public Boolean apply(Name name) {
				return !name.id().equals("name1");
			}
		}).size());
	}

	@Test
	public void nodeListRewriteAll() {
		NodeList<Name> list = listOf(indexedName(0), indexedName(1), indexedName(2)).rewriteAll(new Mutation<Name>() {
			@Override
			public Name mutate(Name name) {
				return name("prefix" + name.id());
			}
		});
		Assert.assertEquals(indexedName("prefix", 0), list.get(0));
		Assert.assertEquals(indexedName("prefix", 1), list.get(1));
		Assert.assertEquals(indexedName("prefix", 2), list.get(2));
	}

	@Test
	public void nodeListMkString() {
		NodeList<Name> list = listOf(indexedName(0), indexedName(1), indexedName(2));
		Assert.assertEquals("[name0, name1, name2]", list.mkString("[", ", ", "]"));
		Assert.assertEquals("(name0|name1|name2)", list.mkString("(", "|", ")"));
	}

	@Test
	public void nodeOptionManipulations() throws FileNotFoundException, ParseException {
		NodeOption<Expr> option1 = none();
		Assert.assertTrue(!option1.isDefined());
		Assert.assertTrue(option1.isNone());
		Assert.assertEquals(null, option1.get());
		Assert.assertTrue(!option1.contains(name("name")));
		Assert.assertTrue(option1.contains(null));
		Assert.assertEquals("()", option1.mkString("(", "", ")"));
		Assert.assertFalse(option1.iterator().hasNext());
		{
			Throwable e = null;
			try {
				option1.iterator().next();
			} catch (Throwable ex) {
				e = ex;
			}
			Assert.assertTrue(e instanceof NoSuchElementException);
		}
		{
			Throwable e = null;
			try {
				option1.iterator().remove();
			} catch (Throwable ex) {
				e = ex;
			}
			Assert.assertTrue(e instanceof UnsupportedOperationException);
		}

		NodeOption<Expr> option2 = option1.set(name("name"));
		Assert.assertTrue(option2.isDefined());
		Assert.assertTrue(option2.isSome());
		Assert.assertEquals(name("name"), option2.get());
		Assert.assertTrue(option2.contains(name("name")));
		Assert.assertTrue(!option2.contains(null));
		Assert.assertEquals("(name)", option2.mkString("(", "", ")"));
		Assert.assertTrue(option2.iterator().hasNext());
		Assert.assertEquals(name("name"), option2.iterator().next());
		{
			Throwable e = null;
			try {
				option2.iterator().remove();
			} catch (Throwable ex) {
				e = ex;
			}
			Assert.assertTrue(e instanceof UnsupportedOperationException);
		}

		NodeOption<Expr> option3 = option2.setNone();
		Assert.assertTrue(!option3.isDefined());
		Assert.assertTrue(option3.isNone());
		Assert.assertEquals(null, option3.get());
		Assert.assertTrue(!option3.contains(name("name")));
		Assert.assertTrue(option3.contains(null));
		Assert.assertEquals("()", option3.mkString("(", "", ")"));
		Assert.assertFalse(option3.iterator().hasNext());
		{
			Throwable e = null;
			try {
				option3.iterator().next();
			} catch (Throwable ex) {
				e = ex;
			}
			Assert.assertTrue(e instanceof NoSuchElementException);
		}
		{
			Throwable e = null;
			try {
				option3.iterator().remove();
			} catch (Throwable ex) {
				e = ex;
			}
			Assert.assertTrue(e instanceof UnsupportedOperationException);
		}

		NodeOption<Expr> option4 = option3.set(this.<Expr>mutationBy(null, name("name2")));
		Assert.assertTrue(option4.isDefined());
		Assert.assertTrue(option4.isSome());
		Assert.assertEquals(name("name2"), option4.get());
		Assert.assertTrue(option4.contains(name("name2")));
		Assert.assertTrue(!option4.contains(null));
		Assert.assertEquals("(name2)", option4.mkString("(", "", ")"));
		Assert.assertTrue(option4.iterator().hasNext());
		Assert.assertEquals(name("name2"), option4.iterator().next());
		{
			Throwable e = null;
			try {
				option4.iterator().remove();
			} catch (Throwable ex) {
				e = ex;
			}
			Assert.assertTrue(e instanceof UnsupportedOperationException);
		}
	}

	@Test
	public void nodeOptionMkString() {
		Assert.assertEquals("[name0]", some(indexedName(0)).mkString("[", ", ", "]"));
		Assert.assertEquals("(name0)", some(indexedName(0)).mkString("(", "|", ")"));
		Assert.assertEquals("()", none().mkString("(", "|", ")"));
	}

	@Test
	public void nodeEitherManipulations() throws FileNotFoundException, ParseException {
		NodeEither<Expr, BlockStmt> either1 = Trees.<Expr, BlockStmt>left(name("name"));
		Assert.assertTrue(either1.isLeft());
		Assert.assertTrue(!either1.isRight());
		Assert.assertEquals(name("name"), either1.left());
		Assert.assertEquals(null, either1.right());
		Assert.assertEquals("(name)", either1.mkString("(", ", ", ")"));
		Assert.assertEquals("[name]", either1.mkString("[", "|", "]"));

		NodeEither<Expr, BlockStmt> either2 = either1.setRight(emptyBlock());
		Assert.assertTrue(!either2.isLeft());
		Assert.assertTrue(either2.isRight());
		Assert.assertEquals(null, either2.left());
		Assert.assertEquals(emptyBlock(), either2.right());
		Assert.assertEquals("({\n})", either2.mkString("(", ", ", ")"));

		NodeEither<Expr, BlockStmt> either3 = either2.setLeft(name("name2"));
		Assert.assertTrue(either3.isLeft());
		Assert.assertTrue(!either3.isRight());
		Assert.assertEquals(name("name2"), either3.left());
		Assert.assertEquals(null, either3.right());
		Assert.assertEquals("(name2)", either3.mkString("(", ", ", ")"));
	}

	public Name indexedName(int index) {
		return name("name" + index);
	}

	public Name indexedName(String prefix, int index) {
		return name(prefix + "name" + index);
	}

	public BlockStmt emptyBlock() {
		return blockStmt();
	}

	private <T> Mutation<T> mutationBy(final T before, final T after) {
		return new Mutation<T>() {

			public T mutate(final T t) {
				Assert.assertEquals(before, t);
				return after;
			}
		};
	}
}
