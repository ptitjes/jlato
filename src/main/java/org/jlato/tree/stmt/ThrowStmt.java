package org.jlato.tree.stmt;

import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.Expr;
import org.jlato.util.Mutation;

/**
 * A 'throw' statement.
 */
public interface ThrowStmt extends Stmt, TreeCombinators<ThrowStmt> {

	/**
	 * Returns the expression of this 'throw' statement.
	 *
	 * @return the expression of this 'throw' statement.
	 */
	Expr expr();

	/**
	 * Replaces the expression of this 'throw' statement.
	 *
	 * @param expr the replacement for the expression of this 'throw' statement.
	 * @return the resulting mutated 'throw' statement.
	 */
	ThrowStmt withExpr(Expr expr);

	/**
	 * Mutates the expression of this 'throw' statement.
	 *
	 * @param mutation the mutation to apply to the expression of this 'throw' statement.
	 * @return the resulting mutated 'throw' statement.
	 */
	ThrowStmt withExpr(Mutation<Expr> mutation);
}
