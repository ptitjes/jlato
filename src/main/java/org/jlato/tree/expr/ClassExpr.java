package org.jlato.tree.expr;

import org.jlato.tree.TreeCombinators;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

/**
 * A 'class' expression.
 */
public interface ClassExpr extends Expr, TreeCombinators<ClassExpr> {

	/**
	 * Returns the type of this 'class' expression.
	 *
	 * @return the type of this 'class' expression.
	 */
	Type type();

	/**
	 * Replaces the type of this 'class' expression.
	 *
	 * @param type the replacement for the type of this 'class' expression.
	 * @return the resulting mutated 'class' expression.
	 */
	ClassExpr withType(Type type);

	/**
	 * Mutates the type of this 'class' expression.
	 *
	 * @param mutation the mutation to apply to the type of this 'class' expression.
	 * @return the resulting mutated 'class' expression.
	 */
	ClassExpr withType(Mutation<Type> mutation);
}
