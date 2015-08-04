package org.jlato.tree.expr;

import org.jlato.tree.TreeCombinators;
import org.jlato.util.Mutation;

public interface AssignExpr extends Expr, TreeCombinators<AssignExpr> {

	Expr target();

	AssignExpr withTarget(Expr target);

	AssignExpr withTarget(Mutation<Expr> mutation);

	AssignOp op();

	AssignExpr withOp(AssignOp op);

	AssignExpr withOp(Mutation<AssignOp> mutation);

	Expr value();

	AssignExpr withValue(Expr value);

	AssignExpr withValue(Mutation<Expr> mutation);
}
