package org.jlato.tree.expr;

import org.jlato.tree.TreeCombinators;
import org.jlato.util.Mutation;

/**
 * An unary expression.
 */
public interface UnaryExpr extends Expr, TreeCombinators<UnaryExpr> {

	/**
	 * Returns the op of this unary expression.
	 *
	 * @return the op of this unary expression.
	 */
	UnaryOp op();

	/**
	 * Replaces the op of this unary expression.
	 *
	 * @param op the replacement for the op of this unary expression.
	 * @return the resulting mutated unary expression.
	 */
	UnaryExpr withOp(UnaryOp op);

	/**
	 * Mutates the op of this unary expression.
	 *
	 * @param mutation the mutation to apply to the op of this unary expression.
	 * @return the resulting mutated unary expression.
	 */
	UnaryExpr withOp(Mutation<UnaryOp> mutation);

	/**
	 * Returns the expression of this unary expression.
	 *
	 * @return the expression of this unary expression.
	 */
	Expr expr();

	/**
	 * Replaces the expression of this unary expression.
	 *
	 * @param expr the replacement for the expression of this unary expression.
	 * @return the resulting mutated unary expression.
	 */
	UnaryExpr withExpr(Expr expr);

	/**
	 * Mutates the expression of this unary expression.
	 *
	 * @param mutation the mutation to apply to the expression of this unary expression.
	 * @return the resulting mutated unary expression.
	 */
	UnaryExpr withExpr(Mutation<Expr> mutation);
}
