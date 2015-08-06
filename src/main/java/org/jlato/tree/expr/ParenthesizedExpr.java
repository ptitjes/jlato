package org.jlato.tree.expr;

import org.jlato.tree.TreeCombinators;
import org.jlato.util.Mutation;

/**
 * A parenthesized expression.
 */
public interface ParenthesizedExpr extends Expr, TreeCombinators<ParenthesizedExpr> {

	/**
	 * Returns the inner of this parenthesized expression.
	 *
	 * @return the inner of this parenthesized expression.
	 */
	Expr inner();

	/**
	 * Replaces the inner of this parenthesized expression.
	 *
	 * @param inner the replacement for the inner of this parenthesized expression.
	 * @return the resulting mutated parenthesized expression.
	 */
	ParenthesizedExpr withInner(Expr inner);

	/**
	 * Mutates the inner of this parenthesized expression.
	 *
	 * @param mutation the mutation to apply to the inner of this parenthesized expression.
	 * @return the resulting mutated parenthesized expression.
	 */
	ParenthesizedExpr withInner(Mutation<Expr> mutation);
}
