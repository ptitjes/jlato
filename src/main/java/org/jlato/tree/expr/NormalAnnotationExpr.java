package org.jlato.tree.expr;

import org.jlato.tree.NodeList;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.name.QualifiedName;
import org.jlato.util.Mutation;

/**
 * A normal annotation expression.
 */
public interface NormalAnnotationExpr extends AnnotationExpr, TreeCombinators<NormalAnnotationExpr> {

	/**
	 * Returns the name of this normal annotation expression.
	 *
	 * @return the name of this normal annotation expression.
	 */
	QualifiedName name();

	/**
	 * Replaces the name of this normal annotation expression.
	 *
	 * @param name the replacement for the name of this normal annotation expression.
	 * @return the resulting mutated normal annotation expression.
	 */
	NormalAnnotationExpr withName(QualifiedName name);

	/**
	 * Mutates the name of this normal annotation expression.
	 *
	 * @param mutation the mutation to apply to the name of this normal annotation expression.
	 * @return the resulting mutated normal annotation expression.
	 */
	NormalAnnotationExpr withName(Mutation<QualifiedName> mutation);

	/**
	 * Returns the pairs of this normal annotation expression.
	 *
	 * @return the pairs of this normal annotation expression.
	 */
	NodeList<MemberValuePair> pairs();

	/**
	 * Replaces the pairs of this normal annotation expression.
	 *
	 * @param pairs the replacement for the pairs of this normal annotation expression.
	 * @return the resulting mutated normal annotation expression.
	 */
	NormalAnnotationExpr withPairs(NodeList<MemberValuePair> pairs);

	/**
	 * Mutates the pairs of this normal annotation expression.
	 *
	 * @param mutation the mutation to apply to the pairs of this normal annotation expression.
	 * @return the resulting mutated normal annotation expression.
	 */
	NormalAnnotationExpr withPairs(Mutation<NodeList<MemberValuePair>> mutation);
}
