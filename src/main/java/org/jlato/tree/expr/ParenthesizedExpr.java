package org.jlato.tree.expr;

import org.jlato.tree.TreeCombinators;
import org.jlato.util.Mutation;

public interface ParenthesizedExpr extends Expr, TreeCombinators<ParenthesizedExpr> {

	Expr inner();

	ParenthesizedExpr withInner(Expr inner);

	ParenthesizedExpr withInner(Mutation<Expr> mutation);
}
