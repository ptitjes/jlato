package org.jlato.tree.expr;

import org.jlato.tree.TreeCombinators;
import org.jlato.tree.name.QualifiedName;
import org.jlato.util.Mutation;

/**
 * A single member annotation expression.
 */
public interface SingleMemberAnnotationExpr extends AnnotationExpr, TreeCombinators<SingleMemberAnnotationExpr> {

	/**
	 * Returns the name of this single member annotation expression.
	 *
	 * @return the name of this single member annotation expression.
	 */
	QualifiedName name();

	/**
	 * Replaces the name of this single member annotation expression.
	 *
	 * @param name the replacement for the name of this single member annotation expression.
	 * @return the resulting mutated single member annotation expression.
	 */
	SingleMemberAnnotationExpr withName(QualifiedName name);

	/**
	 * Mutates the name of this single member annotation expression.
	 *
	 * @param mutation the mutation to apply to the name of this single member annotation expression.
	 * @return the resulting mutated single member annotation expression.
	 */
	SingleMemberAnnotationExpr withName(Mutation<QualifiedName> mutation);

	/**
	 * Returns the member value of this single member annotation expression.
	 *
	 * @return the member value of this single member annotation expression.
	 */
	Expr memberValue();

	/**
	 * Replaces the member value of this single member annotation expression.
	 *
	 * @param memberValue the replacement for the member value of this single member annotation expression.
	 * @return the resulting mutated single member annotation expression.
	 */
	SingleMemberAnnotationExpr withMemberValue(Expr memberValue);

	/**
	 * Mutates the member value of this single member annotation expression.
	 *
	 * @param mutation the mutation to apply to the member value of this single member annotation expression.
	 * @return the resulting mutated single member annotation expression.
	 */
	SingleMemberAnnotationExpr withMemberValue(Mutation<Expr> mutation);
}
