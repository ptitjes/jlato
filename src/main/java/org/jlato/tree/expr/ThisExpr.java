package org.jlato.tree.expr;

import org.jlato.tree.NodeOption;
import org.jlato.tree.TreeCombinators;
import org.jlato.util.Mutation;

public interface ThisExpr extends Expr, TreeCombinators<ThisExpr> {

	NodeOption<Expr> classExpr();

	ThisExpr withClassExpr(NodeOption<Expr> classExpr);

	ThisExpr withClassExpr(Mutation<NodeOption<Expr>> mutation);
}
