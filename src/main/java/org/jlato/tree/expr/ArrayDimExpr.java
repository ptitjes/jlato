package org.jlato.tree.expr;

import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.TreeCombinators;
import org.jlato.util.Mutation;

public interface ArrayDimExpr extends Tree, TreeCombinators<ArrayDimExpr> {

	NodeList<AnnotationExpr> annotations();

	ArrayDimExpr withAnnotations(NodeList<AnnotationExpr> annotations);

	ArrayDimExpr withAnnotations(Mutation<NodeList<AnnotationExpr>> mutation);

	Expr expr();

	ArrayDimExpr withExpr(Expr expr);

	ArrayDimExpr withExpr(Mutation<Expr> mutation);
}
