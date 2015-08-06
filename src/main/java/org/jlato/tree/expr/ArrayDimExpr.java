package org.jlato.tree.expr;

import org.jlato.tree.Node;
import org.jlato.tree.NodeList;
import org.jlato.tree.TreeCombinators;
import org.jlato.util.Mutation;

/**
 * An array dimension expression.
 */
public interface ArrayDimExpr extends Node, TreeCombinators<ArrayDimExpr> {

	/**
	 * Returns the annotations of this array dimension expression.
	 *
	 * @return the annotations of this array dimension expression.
	 */
	NodeList<AnnotationExpr> annotations();

	/**
	 * Replaces the annotations of this array dimension expression.
	 *
	 * @param annotations the replacement for the annotations of this array dimension expression.
	 * @return the resulting mutated array dimension expression.
	 */
	ArrayDimExpr withAnnotations(NodeList<AnnotationExpr> annotations);

	/**
	 * Mutates the annotations of this array dimension expression.
	 *
	 * @param mutation the mutation to apply to the annotations of this array dimension expression.
	 * @return the resulting mutated array dimension expression.
	 */
	ArrayDimExpr withAnnotations(Mutation<NodeList<AnnotationExpr>> mutation);

	/**
	 * Returns the expression of this array dimension expression.
	 *
	 * @return the expression of this array dimension expression.
	 */
	Expr expr();

	/**
	 * Replaces the expression of this array dimension expression.
	 *
	 * @param expr the replacement for the expression of this array dimension expression.
	 * @return the resulting mutated array dimension expression.
	 */
	ArrayDimExpr withExpr(Expr expr);

	/**
	 * Mutates the expression of this array dimension expression.
	 *
	 * @param mutation the mutation to apply to the expression of this array dimension expression.
	 * @return the resulting mutated array dimension expression.
	 */
	ArrayDimExpr withExpr(Mutation<Expr> mutation);
}
