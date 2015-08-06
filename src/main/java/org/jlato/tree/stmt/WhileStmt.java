package org.jlato.tree.stmt;

import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.Expr;
import org.jlato.util.Mutation;

/**
 * A 'while' statement.
 */
public interface WhileStmt extends Stmt, TreeCombinators<WhileStmt> {

	/**
	 * Returns the condition of this 'while' statement.
	 *
	 * @return the condition of this 'while' statement.
	 */
	Expr condition();

	/**
	 * Replaces the condition of this 'while' statement.
	 *
	 * @param condition the replacement for the condition of this 'while' statement.
	 * @return the resulting mutated 'while' statement.
	 */
	WhileStmt withCondition(Expr condition);

	/**
	 * Mutates the condition of this 'while' statement.
	 *
	 * @param mutation the mutation to apply to the condition of this 'while' statement.
	 * @return the resulting mutated 'while' statement.
	 */
	WhileStmt withCondition(Mutation<Expr> mutation);

	/**
	 * Returns the body of this 'while' statement.
	 *
	 * @return the body of this 'while' statement.
	 */
	Stmt body();

	/**
	 * Replaces the body of this 'while' statement.
	 *
	 * @param body the replacement for the body of this 'while' statement.
	 * @return the resulting mutated 'while' statement.
	 */
	WhileStmt withBody(Stmt body);

	/**
	 * Mutates the body of this 'while' statement.
	 *
	 * @param mutation the mutation to apply to the body of this 'while' statement.
	 * @return the resulting mutated 'while' statement.
	 */
	WhileStmt withBody(Mutation<Stmt> mutation);
}
