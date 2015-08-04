package org.jlato.tree.expr;

import org.jlato.tree.NodeList;
import org.jlato.tree.TreeCombinators;
import org.jlato.util.Mutation;

public interface ArrayInitializerExpr extends Expr, TreeCombinators<ArrayInitializerExpr> {

	NodeList<Expr> values();

	ArrayInitializerExpr withValues(NodeList<Expr> values);

	ArrayInitializerExpr withValues(Mutation<NodeList<Expr>> mutation);

	boolean trailingComma();

	ArrayInitializerExpr withTrailingComma(boolean trailingComma);

	ArrayInitializerExpr withTrailingComma(Mutation<Boolean> mutation);
}
