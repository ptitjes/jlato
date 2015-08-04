package org.jlato.tree.expr;

import org.jlato.tree.TreeCombinators;
import org.jlato.util.Mutation;

public interface ConditionalExpr extends Expr, TreeCombinators<ConditionalExpr> {

	Expr condition();

	ConditionalExpr withCondition(Expr condition);

	ConditionalExpr withCondition(Mutation<Expr> mutation);

	Expr thenExpr();

	ConditionalExpr withThenExpr(Expr thenExpr);

	ConditionalExpr withThenExpr(Mutation<Expr> mutation);

	Expr elseExpr();

	ConditionalExpr withElseExpr(Expr elseExpr);

	ConditionalExpr withElseExpr(Mutation<Expr> mutation);
}
