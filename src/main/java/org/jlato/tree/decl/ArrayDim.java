package org.jlato.tree.decl;

import org.jlato.tree.Node;
import org.jlato.tree.NodeList;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.util.Mutation;

/**
 * An array dimension.
 */
public interface ArrayDim extends Node, TreeCombinators<ArrayDim> {

	/**
	 * Returns the annotations of this array dimension.
	 *
	 * @return the annotations of this array dimension.
	 */
	NodeList<AnnotationExpr> annotations();

	/**
	 * Replaces the annotations of this array dimension.
	 *
	 * @param annotations the replacement for the annotations of this array dimension.
	 * @return the resulting mutated array dimension.
	 */
	ArrayDim withAnnotations(NodeList<AnnotationExpr> annotations);

	/**
	 * Mutates the annotations of this array dimension.
	 *
	 * @param mutation the mutation to apply to the annotations of this array dimension.
	 * @return the resulting mutated array dimension.
	 */
	ArrayDim withAnnotations(Mutation<NodeList<AnnotationExpr>> mutation);
}
