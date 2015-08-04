package org.jlato.tree.expr;

import org.jlato.tree.NodeOption;
import org.jlato.tree.TreeCombinators;
import org.jlato.util.Mutation;

public interface SuperExpr extends Expr, TreeCombinators<SuperExpr> {

	NodeOption<Expr> classExpr();

	SuperExpr withClassExpr(NodeOption<Expr> classExpr);

	SuperExpr withClassExpr(Mutation<NodeOption<Expr>> mutation);
}
