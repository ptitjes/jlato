package org.jlato.tree.expr;

import org.jlato.tree.NodeList;
import org.jlato.tree.TreeCombinators;
import org.jlato.util.Mutation;

/**
 * An array initializer expression.
 */
public interface ArrayInitializerExpr extends Expr, TreeCombinators<ArrayInitializerExpr> {

	/**
	 * Returns the values of this array initializer expression.
	 *
	 * @return the values of this array initializer expression.
	 */
	NodeList<Expr> values();

	/**
	 * Replaces the values of this array initializer expression.
	 *
	 * @param values the replacement for the values of this array initializer expression.
	 * @return the resulting mutated array initializer expression.
	 */
	ArrayInitializerExpr withValues(NodeList<Expr> values);

	/**
	 * Mutates the values of this array initializer expression.
	 *
	 * @param mutation the mutation to apply to the values of this array initializer expression.
	 * @return the resulting mutated array initializer expression.
	 */
	ArrayInitializerExpr withValues(Mutation<NodeList<Expr>> mutation);

	/**
	 * Tests whether this array initializer expression has a trailing comma.
	 *
	 * @return <code>true</code> if this array initializer expression has a trailing comma, <code>false</code> otherwise.
	 */
	boolean trailingComma();

	/**
	 * Sets whether this array initializer expression has a trailing comma.
	 *
	 * @param trailingComma <code>true</code> if this array initializer expression has a trailing comma, <code>false</code> otherwise.
	 * @return the resulting mutated array initializer expression.
	 */
	ArrayInitializerExpr withTrailingComma(boolean trailingComma);

	/**
	 * Mutates whether this array initializer expression has a trailing comma.
	 *
	 * @param mutation the mutation to apply to whether this array initializer expression has a trailing comma.
	 * @return the resulting mutated array initializer expression.
	 */
	ArrayInitializerExpr withTrailingComma(Mutation<Boolean> mutation);
}
