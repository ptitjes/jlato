package org.jlato.tree.stmt;

import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.Expr;
import org.jlato.util.Mutation;

/**
 * A 'do-while' statement.
 */
public interface DoStmt extends Stmt, TreeCombinators<DoStmt> {

	/**
	 * Returns the body of this 'do-while' statement.
	 *
	 * @return the body of this 'do-while' statement.
	 */
	Stmt body();

	/**
	 * Replaces the body of this 'do-while' statement.
	 *
	 * @param body the replacement for the body of this 'do-while' statement.
	 * @return the resulting mutated 'do-while' statement.
	 */
	DoStmt withBody(Stmt body);

	/**
	 * Mutates the body of this 'do-while' statement.
	 *
	 * @param mutation the mutation to apply to the body of this 'do-while' statement.
	 * @return the resulting mutated 'do-while' statement.
	 */
	DoStmt withBody(Mutation<Stmt> mutation);

	/**
	 * Returns the condition of this 'do-while' statement.
	 *
	 * @return the condition of this 'do-while' statement.
	 */
	Expr condition();

	/**
	 * Replaces the condition of this 'do-while' statement.
	 *
	 * @param condition the replacement for the condition of this 'do-while' statement.
	 * @return the resulting mutated 'do-while' statement.
	 */
	DoStmt withCondition(Expr condition);

	/**
	 * Mutates the condition of this 'do-while' statement.
	 *
	 * @param mutation the mutation to apply to the condition of this 'do-while' statement.
	 * @return the resulting mutated 'do-while' statement.
	 */
	DoStmt withCondition(Mutation<Expr> mutation);
}
