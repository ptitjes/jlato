package org.jlato.tree.expr;

import org.jlato.tree.decl.ExtendedModifier;
import org.jlato.tree.name.QualifiedName;
import org.jlato.util.Mutation;

/**
 * An annotation expression.
 */
public interface AnnotationExpr extends Expr, ExtendedModifier {

	/**
	 * Returns the name of this annotation expression.
	 *
	 * @return the name of this annotation expression.
	 */
	QualifiedName name();

	/**
	 * Replaces the name of this annotation expression.
	 *
	 * @param name the replacement for the name of this annotation expression.
	 * @return the resulting mutated annotation expression.
	 */
	AnnotationExpr withName(QualifiedName name);

	/**
	 * Mutates the name of this annotation expression.
	 *
	 * @param mutation the mutation to apply to the name of this annotation expression.
	 * @return the resulting mutated annotation expression.
	 */
	AnnotationExpr withName(Mutation<QualifiedName> mutation);
}
