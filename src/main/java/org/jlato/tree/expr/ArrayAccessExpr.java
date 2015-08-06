package org.jlato.tree.expr;

import org.jlato.tree.TreeCombinators;
import org.jlato.util.Mutation;

/**
 * An array access expression.
 */
public interface ArrayAccessExpr extends Expr, TreeCombinators<ArrayAccessExpr> {

	/**
	 * Returns the name of this array access expression.
	 *
	 * @return the name of this array access expression.
	 */
	Expr name();

	/**
	 * Replaces the name of this array access expression.
	 *
	 * @param name the replacement for the name of this array access expression.
	 * @return the resulting mutated array access expression.
	 */
	ArrayAccessExpr withName(Expr name);

	/**
	 * Mutates the name of this array access expression.
	 *
	 * @param mutation the mutation to apply to the name of this array access expression.
	 * @return the resulting mutated array access expression.
	 */
	ArrayAccessExpr withName(Mutation<Expr> mutation);

	/**
	 * Returns the index of this array access expression.
	 *
	 * @return the index of this array access expression.
	 */
	Expr index();

	/**
	 * Replaces the index of this array access expression.
	 *
	 * @param index the replacement for the index of this array access expression.
	 * @return the resulting mutated array access expression.
	 */
	ArrayAccessExpr withIndex(Expr index);

	/**
	 * Mutates the index of this array access expression.
	 *
	 * @param mutation the mutation to apply to the index of this array access expression.
	 * @return the resulting mutated array access expression.
	 */
	ArrayAccessExpr withIndex(Mutation<Expr> mutation);
}
