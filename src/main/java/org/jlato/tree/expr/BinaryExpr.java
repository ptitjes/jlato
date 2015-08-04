package org.jlato.tree.expr;

import org.jlato.tree.TreeCombinators;
import org.jlato.util.Mutation;

public interface BinaryExpr extends Expr, TreeCombinators<BinaryExpr> {

	Expr left();

	BinaryExpr withLeft(Expr left);

	BinaryExpr withLeft(Mutation<Expr> mutation);

	BinaryOp op();

	BinaryExpr withOp(BinaryOp op);

	BinaryExpr withOp(Mutation<BinaryOp> mutation);

	Expr right();

	BinaryExpr withRight(Expr right);

	BinaryExpr withRight(Mutation<Expr> mutation);
}
