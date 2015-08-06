package org.jlato.tree.expr;

import org.jlato.tree.TreeCombinators;
import org.jlato.util.Mutation;

/**
 * An assignment expression.
 */
public interface AssignExpr extends Expr, TreeCombinators<AssignExpr> {

	/**
	 * Returns the target of this assignment expression.
	 *
	 * @return the target of this assignment expression.
	 */
	Expr target();

	/**
	 * Replaces the target of this assignment expression.
	 *
	 * @param target the replacement for the target of this assignment expression.
	 * @return the resulting mutated assignment expression.
	 */
	AssignExpr withTarget(Expr target);

	/**
	 * Mutates the target of this assignment expression.
	 *
	 * @param mutation the mutation to apply to the target of this assignment expression.
	 * @return the resulting mutated assignment expression.
	 */
	AssignExpr withTarget(Mutation<Expr> mutation);

	/**
	 * Returns the op of this assignment expression.
	 *
	 * @return the op of this assignment expression.
	 */
	AssignOp op();

	/**
	 * Replaces the op of this assignment expression.
	 *
	 * @param op the replacement for the op of this assignment expression.
	 * @return the resulting mutated assignment expression.
	 */
	AssignExpr withOp(AssignOp op);

	/**
	 * Mutates the op of this assignment expression.
	 *
	 * @param mutation the mutation to apply to the op of this assignment expression.
	 * @return the resulting mutated assignment expression.
	 */
	AssignExpr withOp(Mutation<AssignOp> mutation);

	/**
	 * Returns the value of this assignment expression.
	 *
	 * @return the value of this assignment expression.
	 */
	Expr value();

	/**
	 * Replaces the value of this assignment expression.
	 *
	 * @param value the replacement for the value of this assignment expression.
	 * @return the resulting mutated assignment expression.
	 */
	AssignExpr withValue(Expr value);

	/**
	 * Mutates the value of this assignment expression.
	 *
	 * @param mutation the mutation to apply to the value of this assignment expression.
	 * @return the resulting mutated assignment expression.
	 */
	AssignExpr withValue(Mutation<Expr> mutation);
}
