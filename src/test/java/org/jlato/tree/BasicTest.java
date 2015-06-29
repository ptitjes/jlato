package org.jlato.tree;

import org.jlato.internal.TreeBuilder;
import org.jlato.internal.bu.LLiteral;
import org.jlato.tree.testexpr.BinaryExpr;
import org.jlato.tree.testexpr.BinaryOp;
import org.jlato.tree.testexpr.LiteralExpr;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author Didier Villevalois
 */
@RunWith(JUnit4.class)
public class BasicTest {

	@Test
	public void test1() {
		BinaryExpr expr1 = new BinaryExpr(
				new LiteralExpr("1"),
				BinaryOp.Plus,
				new LiteralExpr("2")
		);
		Assert.assertSame(expr1, expr1.left().parent());
		Assert.assertSame(expr1, expr1.right().parent());

		BinaryExpr expr2 = expr1.withLeft(new LiteralExpr("1"));
		Assert.assertNotSame(expr1.left().tree, expr2.left().tree);
		Assert.assertSame(expr1.right().tree, expr2.right().tree);

		BinaryExpr expr3 = expr2.withRight(new LiteralExpr("2"));
		Assert.assertNotSame(expr1.left().tree, expr3.left().tree);
		Assert.assertNotSame(expr1.right().tree, expr3.right().tree);
	}

	@Test
	public void test2() {
		BinaryExpr expr1 = new BinaryExpr(
				new LiteralExpr("1"),
				BinaryOp.Plus,
				new LiteralExpr("2")
		);

		BinaryExpr expr2 = expr1.withRight(expr1);
		Assert.assertTrue(expr2.right() instanceof BinaryExpr);
	}

	@Test
	public void test3() {
		TreeBuilder builder = new TreeBuilder();

		builder.start();

		builder.start();
		builder.addToken(new LLiteral("3"));
		builder.stopAs(LiteralExpr.TYPE);

		builder.addToken(new LLiteral("+"));
		builder.lastTokenAs(BinaryOp.TYPE);

		builder.start();

		builder.start();
		builder.addToken(new LLiteral("2"));
		builder.stopAs(LiteralExpr.TYPE);

		builder.start();
		builder.addToken(new LLiteral("+"));
		builder.stopAs(BinaryOp.TYPE);

		builder.start();
		builder.addToken(new LLiteral("4"));
		builder.stopAs(LiteralExpr.TYPE);

		builder.stopAs(BinaryExpr.type);

		builder.stopAs(BinaryExpr.type);

		BinaryExpr expr = builder.build(BinaryExpr.type);
	}

	@Test
	public void test4() {
		TreeBuilder builder = new TreeBuilder();

		builder.start();

		builder.addToken(new LLiteral("3"));
		builder.lastTokenAs(LiteralExpr.TYPE);

		builder.addToken(new LLiteral("+"));
		builder.lastTokenAs(BinaryOp.TYPE);

		builder.addToken(new LLiteral("2"));
		builder.lastTokenAs(LiteralExpr.TYPE);

		builder.stopAsAndWrap(BinaryExpr.type);

		builder.addToken(new LLiteral("+"));
		builder.lastTokenAs(BinaryOp.TYPE);

		builder.addToken(new LLiteral("4"));
		builder.lastTokenAs(LiteralExpr.TYPE);

		builder.stopAs(BinaryExpr.type);

		BinaryExpr expr = builder.build(BinaryExpr.type);
	}
}
