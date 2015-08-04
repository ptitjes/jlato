package org.jlato.tree.decl;

import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.util.Mutation;

public interface ArrayDim extends Tree, TreeCombinators<ArrayDim> {

	NodeList<AnnotationExpr> annotations();

	ArrayDim withAnnotations(NodeList<AnnotationExpr> annotations);

	ArrayDim withAnnotations(Mutation<NodeList<AnnotationExpr>> mutation);
}
