package org.jlato.tree.decl;

import org.jlato.tree.Node;
import org.jlato.tree.NodeList;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.util.Mutation;

public interface ArrayDim extends Node, TreeCombinators<ArrayDim> {

	NodeList<AnnotationExpr> annotations();

	ArrayDim withAnnotations(NodeList<AnnotationExpr> annotations);

	ArrayDim withAnnotations(Mutation<NodeList<AnnotationExpr>> mutation);
}
