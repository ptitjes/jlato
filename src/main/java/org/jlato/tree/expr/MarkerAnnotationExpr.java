package org.jlato.tree.expr;

import org.jlato.tree.TreeCombinators;
import org.jlato.tree.name.QualifiedName;
import org.jlato.util.Mutation;

/**
 * A marker annotation expression.
 */
public interface MarkerAnnotationExpr extends AnnotationExpr, TreeCombinators<MarkerAnnotationExpr> {

	/**
	 * Returns the name of this marker annotation expression.
	 *
	 * @return the name of this marker annotation expression.
	 */
	QualifiedName name();

	/**
	 * Replaces the name of this marker annotation expression.
	 *
	 * @param name the replacement for the name of this marker annotation expression.
	 * @return the resulting mutated marker annotation expression.
	 */
	MarkerAnnotationExpr withName(QualifiedName name);

	/**
	 * Mutates the name of this marker annotation expression.
	 *
	 * @param mutation the mutation to apply to the name of this marker annotation expression.
	 * @return the resulting mutated marker annotation expression.
	 */
	MarkerAnnotationExpr withName(Mutation<QualifiedName> mutation);
}
