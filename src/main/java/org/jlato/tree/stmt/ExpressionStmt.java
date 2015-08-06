package org.jlato.tree.stmt;

import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.Expr;
import org.jlato.util.Mutation;

/**
 * An expression statement.
 */
public interface ExpressionStmt extends Stmt, TreeCombinators<ExpressionStmt> {

	/**
	 * Returns the expression of this expression statement.
	 *
	 * @return the expression of this expression statement.
	 */
	Expr expr();

	/**
	 * Replaces the expression of this expression statement.
	 *
	 * @param expr the replacement for the expression of this expression statement.
	 * @return the resulting mutated expression statement.
	 */
	ExpressionStmt withExpr(Expr expr);

	/**
	 * Mutates the expression of this expression statement.
	 *
	 * @param mutation the mutation to apply to the expression of this expression statement.
	 * @return the resulting mutated expression statement.
	 */
	ExpressionStmt withExpr(Mutation<Expr> mutation);
}
