package org.jlato.tree.expr;

import org.jlato.tree.TreeCombinators;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

/**
 * A cast expression.
 */
public interface CastExpr extends Expr, TreeCombinators<CastExpr> {

	/**
	 * Returns the type of this cast expression.
	 *
	 * @return the type of this cast expression.
	 */
	Type type();

	/**
	 * Replaces the type of this cast expression.
	 *
	 * @param type the replacement for the type of this cast expression.
	 * @return the resulting mutated cast expression.
	 */
	CastExpr withType(Type type);

	/**
	 * Mutates the type of this cast expression.
	 *
	 * @param mutation the mutation to apply to the type of this cast expression.
	 * @return the resulting mutated cast expression.
	 */
	CastExpr withType(Mutation<Type> mutation);

	/**
	 * Returns the expression of this cast expression.
	 *
	 * @return the expression of this cast expression.
	 */
	Expr expr();

	/**
	 * Replaces the expression of this cast expression.
	 *
	 * @param expr the replacement for the expression of this cast expression.
	 * @return the resulting mutated cast expression.
	 */
	CastExpr withExpr(Expr expr);

	/**
	 * Mutates the expression of this cast expression.
	 *
	 * @param mutation the mutation to apply to the expression of this cast expression.
	 * @return the resulting mutated cast expression.
	 */
	CastExpr withExpr(Mutation<Expr> mutation);
}
