package org.jlato.tree.expr;

import org.jlato.tree.TreeCombinators;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

/**
 * A type expression.
 */
public interface TypeExpr extends Expr, TreeCombinators<TypeExpr> {

	/**
	 * Returns the type of this type expression.
	 *
	 * @return the type of this type expression.
	 */
	Type type();

	/**
	 * Replaces the type of this type expression.
	 *
	 * @param type the replacement for the type of this type expression.
	 * @return the resulting mutated type expression.
	 */
	TypeExpr withType(Type type);

	/**
	 * Mutates the type of this type expression.
	 *
	 * @param mutation the mutation to apply to the type of this type expression.
	 * @return the resulting mutated type expression.
	 */
	TypeExpr withType(Mutation<Type> mutation);
}
