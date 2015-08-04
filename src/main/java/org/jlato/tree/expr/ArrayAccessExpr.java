package org.jlato.tree.expr;

import org.jlato.tree.TreeCombinators;
import org.jlato.util.Mutation;

public interface ArrayAccessExpr extends Expr, TreeCombinators<ArrayAccessExpr> {

	Expr name();

	ArrayAccessExpr withName(Expr name);

	ArrayAccessExpr withName(Mutation<Expr> mutation);

	Expr index();

	ArrayAccessExpr withIndex(Expr index);

	ArrayAccessExpr withIndex(Mutation<Expr> mutation);
}
