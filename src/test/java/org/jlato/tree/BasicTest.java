package org.jlato.tree;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author Didier Villevalois
 */
@RunWith(JUnit4.class)
public class BasicTest {
/*
	@Test
	public void test1() {
		BinaryExpr expr1 = new BinaryExpr(
				new LiteralExpr<Integer>(Integer.class, 2),
				BinaryOp.Plus,
				new LiteralExpr<Integer>(Integer.class, 3)
		);
		Assert.assertSame(expr1, expr1.left().parent());
		Assert.assertSame(expr1, expr1.right().parent());

		BinaryExpr expr2 = expr1.withLeft(new LiteralExpr<Integer>(Integer.class, 1));
//		Assert.assertNotSame(expr1.left().tree, expr2.left().tree);
//		Assert.assertSame(expr1.right().tree, expr2.right().tree);

		BinaryExpr expr3 = expr2.withRight(new LiteralExpr<Integer>(Integer.class, 2));
//		Assert.assertNotSame(expr1.left().tree, expr3.left().tree);
//		Assert.assertNotSame(expr1.right().tree, expr3.right().tree);
	}

	@Test
	public void test2() {
		BinaryExpr expr1 = new BinaryExpr(
				new LiteralExpr<Integer>(Integer.class, 1),
				BinaryOp.Plus,
				new LiteralExpr<Integer>(Integer.class, 2)
		);

		BinaryExpr expr2 = expr1.withRight(expr1);
		Assert.assertTrue(expr2.right() instanceof BinaryExpr);
	}

	@Test
	public void test3() {
		TreeBuilder builder = new TreeBuilder();

		builder.start();

		builder.start();
		builder.addToken(new LLiteral<Integer>(Integer.class, "3"));
		builder.stopAs(LiteralExpr.kind);

		builder.tokenAs(new LLiteral<Integer>(Integer.class, "+"), BinaryOp.kind);

		builder.start();

		builder.start();
		builder.addToken(new LLiteral<Integer>(Integer.class, "2"));
		builder.stopAs(LiteralExpr.kind);

		builder.start();
		builder.addToken(new LLiteral<Integer>(Integer.class, "+"));
		builder.stopAs(BinaryOp.kind);

		builder.start();
		builder.addToken(new LLiteral<Integer>(Integer.class, "4"));
		builder.stopAs(LiteralExpr.kind);

		builder.stopAs(BinaryExpr.kind);

		builder.stopAs(BinaryExpr.kind);

		BinaryExpr expr = builder.build(BinaryExpr.kind);
	}

	@Test
	public void test4() {
		TreeBuilder builder = new TreeBuilder();

		builder.start();

		builder.tokenAs(new LLiteral<Integer>(Integer.class, "3"), LiteralExpr.kind);

		builder.tokenAs(new LLiteral<Integer>(Integer.class, "+"), BinaryOp.kind);

		builder.tokenAs(new LLiteral<Integer>(Integer.class, "2"), LiteralExpr.kind);

		builder.stopAsAndWrap(BinaryExpr.kind);

		builder.tokenAs(new LLiteral<Integer>(Integer.class, "+"), BinaryOp.kind);

		builder.tokenAs(new LLiteral<Integer>(Integer.class, "4"), LiteralExpr.kind);

		builder.stopAs(BinaryExpr.kind);

		BinaryExpr expr = builder.build(BinaryExpr.kind);

		Expr expr2 = expr.right().replace(new LiteralExpr<Integer>(Integer.class, 8));
		Tree expr3 = expr2.root();

//		Operator expr4 = expr.operator().replace(UnaryOp.Minus);
//		Tree expr5 = expr4.root();
	}

	@Test
	public void test5() {
		TreeBuilder builder = new TreeBuilder();

		builder.start();

		builder.start();

		builder.tokenAs(new LLiteral<Integer>(Integer.class, "3"), LiteralExpr.kind);

		builder.tokenAs(new LLiteral<Integer>(Integer.class, "+"), BinaryOp.kind);

		builder.tokenAs(new LLiteral<Integer>(Integer.class, "2"), LiteralExpr.kind);

		builder.stopAs(BinaryExpr.kind);

		builder.tokenAs(new LLiteral<Integer>(Integer.class, "1"), LiteralExpr.kind);

		builder.tokenAs(new LLiteral<Integer>(Integer.class, "2"), LiteralExpr.kind);

		builder.tokenAs(new LLiteral<Integer>(Integer.class, "3"), LiteralExpr.kind);

		builder.stopAs(NodeList.<Expr>kind());

		NodeList<Expr> exprs = builder.build(NodeList.<Expr>kind());

		NodeList<Expr> newExprs = exprs.append(new LiteralExpr<Integer>(Integer.class, 42));
	}*/
}
