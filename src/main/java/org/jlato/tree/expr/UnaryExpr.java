package org.jlato.tree.expr;

import org.jlato.tree.TreeCombinators;
import org.jlato.util.Mutation;

public interface UnaryExpr extends Expr, TreeCombinators<UnaryExpr> {

	UnaryOp op();

	UnaryExpr withOp(UnaryOp op);

	UnaryExpr withOp(Mutation<UnaryOp> mutation);

	Expr expr();

	UnaryExpr withExpr(Expr expr);

	UnaryExpr withExpr(Mutation<Expr> mutation);
}
