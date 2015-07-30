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
import org.jlato.tree.NodeEither;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.ImportDecl;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.expr.MethodInvocationExpr;
import org.jlato.tree.name.Name;
import org.jlato.tree.stmt.BlockStmt;
import org.jlato.tree.stmt.Stmt;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileNotFoundException;
import java.io.IOException;

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

		Assert.assertEquals(
				"scope.method(newArg, arg2)",
				Printer.printToString(expr.withArgs(
						expr.args().set(0, name("newArg"))
				), true)
		);
		Assert.assertEquals(
				"scope.method(arg1, newArg)",
				Printer.printToString(expr.withArgs(
						expr.args().set(1, name("newArg"))
				), true)
		);
		Assert.assertEquals(
				"scope.method(arg1, arg2, newArg)",
				Printer.printToString(expr.withArgs(
						expr.args().append(name("newArg"))
				), true)
		);
		Assert.assertEquals(
				"scope.method(arg1, arg2, newArg1, newArg2)",
				Printer.printToString(expr.withArgs(
						expr.args().appendAll(NodeList.of(name("newArg1"), name("newArg2")))
				), true)
		);
		Assert.assertEquals(
				"scope.method(newArg, arg1, arg2)",
				Printer.printToString(expr.withArgs(
						expr.args().prepend(name("newArg"))
				), true)
		);
		Assert.assertEquals(
				"scope.method(newArg1, newArg2, arg1, arg2)",
				Printer.printToString(expr.withArgs(
						expr.args().prependAll(NodeList.of(name("newArg1"), name("newArg2")))
				), true)
		);
		Assert.assertEquals(
				"scope.method(arg1, newArg, arg2)",
				Printer.printToString(expr.withArgs(
						expr.args().insert(1, name("newArg"))
				), true)
		);
		Assert.assertEquals(
				"scope.method(arg1, newArg1, newArg2, arg2)",
				Printer.printToString(expr.withArgs(
						expr.args().insertAll(1, NodeList.of(name("newArg1"), name("newArg2")))
				), true)
		);
	}

	@Test
	public void nodeListStaticCreationHelpers() {
		assertNodeListContent(NodeList.<Name>empty(), 0);
		assertNodeListContent(NodeList.of(name(0)), 1);
		assertNodeListContent(NodeList.of(name(0), name(1)), 2);
		assertNodeListContent(NodeList.of(name(0), name(1), name(2)), 3);
		assertNodeListContent(NodeList.of(name(0), name(1), name(2), name(3)), 4);
		assertNodeListContent(NodeList.of(name(0), name(1), name(2), name(3), name(4)), 5);
		assertNodeListContent(NodeList.of(name(0), name(1), name(2), name(3), name(4), name(5)), 6);
		assertNodeListContent(NodeList.of(name(0), name(1), name(2), name(3), name(4), name(5), name(6)), 7);
		assertNodeListContent(NodeList.of(name(0), name(1), name(2), name(3), name(4), name(5), name(6), name(7)), 8);
		assertNodeListContent(NodeList.of(name(0), name(1), name(2), name(3), name(4), name(5), name(6), name(7), name(8)), 9);
		assertNodeListContent(NodeList.of(name(0), name(1), name(2), name(3), name(4), name(5), name(6), name(7), name(8), name(9)), 10);
		assertNodeListContent(NodeList.of(name(0), name(1), name(2), name(3), name(4), name(5), name(6), name(7), name(8), name(9), name(10)), 11);
		assertNodeListContent(NodeList.of(name(0), name(1), name(2), name(3), name(4), name(5), name(6), name(7), name(8), name(9), name(10), name(11)), 12);
		assertNodeListContent(NodeList.of(name(0), name(1), name(2), name(3), name(4), name(5), name(6), name(7), name(8), name(9), name(10), name(11), name(12)), 13);
		assertNodeListContent(NodeList.of(name(0), name(1), name(2), name(3), name(4), name(5), name(6), name(7), name(8), name(9), name(10), name(11), name(12), name(13)), 14);
		assertNodeListContent(NodeList.of(name(0), name(1), name(2), name(3), name(4), name(5), name(6), name(7), name(8), name(9), name(10), name(11), name(12), name(13), name(14)), 15);
		assertNodeListContent(NodeList.of(name(0), name(1), name(2), name(3), name(4), name(5), name(6), name(7), name(8), name(9), name(10), name(11), name(12), name(13), name(14), name(15)), 16);
		assertNodeListContent(NodeList.of(name(0), name(1), name(2), name(3), name(4), name(5), name(6), name(7), name(8), name(9), name(10), name(11), name(12), name(13), name(14), name(15), name(16)), 17);
		assertNodeListContent(NodeList.of(name(0), name(1), name(2), name(3), name(4), name(5), name(6), name(7), name(8), name(9), name(10), name(11), name(12), name(13), name(14), name(15), name(16), name(17)), 18);
		assertNodeListContent(NodeList.of(name(0), name(1), name(2), name(3), name(4), name(5), name(6), name(7), name(8), name(9), name(10), name(11), name(12), name(13), name(14), name(15), name(16), name(17), name(18)), 19);
		assertNodeListContent(NodeList.of(name(0), name(1), name(2), name(3), name(4), name(5), name(6), name(7), name(8), name(9), name(10), name(11), name(12), name(13), name(14), name(15), name(16), name(17), name(18), name(19)), 20);
		assertNodeListContent(NodeList.of(name(0), name(1), name(2), name(3), name(4), name(5), name(6), name(7), name(8), name(9), name(10), name(11), name(12), name(13), name(14), name(15), name(16), name(17), name(18), name(19), name(20)), 21);
		assertNodeListContent(NodeList.of(name(0), name(1), name(2), name(3), name(4), name(5), name(6), name(7), name(8), name(9), name(10), name(11), name(12), name(13), name(14), name(15), name(16), name(17), name(18), name(19), name(20), name(21)), 22);
		assertNodeListContent(NodeList.of(name(0), name(1), name(2), name(3), name(4), name(5), name(6), name(7), name(8), name(9), name(10), name(11), name(12), name(13), name(14), name(15), name(16), name(17), name(18), name(19), name(20), name(21), name(22)), 23);
	}

	private void assertNodeListContent(NodeList<? extends Expr> list, int expectedSize) {
		Assert.assertEquals(expectedSize, list.size());
		for (int i = 0; i < expectedSize; i++) {
			Assert.assertEquals(name(i), list.get(i));
		}
	}

	@Test
	public void nodeOptionManipulations() throws FileNotFoundException, ParseException {
		NodeOption<Expr> option1 = NodeOption.none();
		Assert.assertTrue(!option1.isDefined());
		Assert.assertTrue(option1.isNone());
		Assert.assertEquals(null, option1.get());

		NodeOption<Expr> option2 = option1.set(name("name"));
		Assert.assertTrue(option2.isDefined());
		Assert.assertTrue(option2.isSome());
		Assert.assertEquals(name("name"), option2.get());

		NodeOption<Expr> option3 = option2.setNone();
		Assert.assertTrue(!option3.isDefined());
		Assert.assertTrue(option3.isNone());
		Assert.assertEquals(null, option3.get());
	}

	@Test
	public void nodeEitherManipulations() throws FileNotFoundException, ParseException {
		NodeEither<Expr, BlockStmt> either1 = NodeEither.<Expr, BlockStmt>left(name("name"));
		Assert.assertTrue(either1.isLeft());
		Assert.assertTrue(!either1.isRight());
		Assert.assertEquals(name("name"), either1.left());
		Assert.assertEquals(null, either1.right());

		NodeEither<Expr, BlockStmt> either2 = either1.setRight(emptyBlock());
		Assert.assertTrue(!either2.isLeft());
		Assert.assertTrue(either2.isRight());
		Assert.assertEquals(null, either2.left());
		Assert.assertEquals(emptyBlock(), either2.right());

		NodeEither<Expr, BlockStmt> either3 = either2.setLeft(name("name2"));
		Assert.assertTrue(either3.isLeft());
		Assert.assertTrue(!either3.isRight());
		Assert.assertEquals(name("name2"), either3.left());
		Assert.assertEquals(null, either3.right());
	}

	public Name name(String string) {
		return new Name(string);
	}

	public Name name(int index) {
		return new Name("name" + index);
	}

	public BlockStmt emptyBlock() {
		return new BlockStmt(NodeList.<Stmt>empty());
	}
}
