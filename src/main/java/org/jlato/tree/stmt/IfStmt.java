package org.jlato.tree.stmt;

import org.jlato.tree.NodeOption;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.Expr;
import org.jlato.util.Mutation;

/**
 * An 'if' statement.
 */
public interface IfStmt extends Stmt, TreeCombinators<IfStmt> {

	/**
	 * Returns the condition of this 'if' statement.
	 *
	 * @return the condition of this 'if' statement.
	 */
	Expr condition();

	/**
	 * Replaces the condition of this 'if' statement.
	 *
	 * @param condition the replacement for the condition of this 'if' statement.
	 * @return the resulting mutated 'if' statement.
	 */
	IfStmt withCondition(Expr condition);

	/**
	 * Mutates the condition of this 'if' statement.
	 *
	 * @param mutation the mutation to apply to the condition of this 'if' statement.
	 * @return the resulting mutated 'if' statement.
	 */
	IfStmt withCondition(Mutation<Expr> mutation);

	/**
	 * Returns the then statement of this 'if' statement.
	 *
	 * @return the then statement of this 'if' statement.
	 */
	Stmt thenStmt();

	/**
	 * Replaces the then statement of this 'if' statement.
	 *
	 * @param thenStmt the replacement for the then statement of this 'if' statement.
	 * @return the resulting mutated 'if' statement.
	 */
	IfStmt withThenStmt(Stmt thenStmt);

	/**
	 * Mutates the then statement of this 'if' statement.
	 *
	 * @param mutation the mutation to apply to the then statement of this 'if' statement.
	 * @return the resulting mutated 'if' statement.
	 */
	IfStmt withThenStmt(Mutation<Stmt> mutation);

	/**
	 * Returns the else statement of this 'if' statement.
	 *
	 * @return the else statement of this 'if' statement.
	 */
	NodeOption<Stmt> elseStmt();

	/**
	 * Replaces the else statement of this 'if' statement.
	 *
	 * @param elseStmt the replacement for the else statement of this 'if' statement.
	 * @return the resulting mutated 'if' statement.
	 */
	IfStmt withElseStmt(NodeOption<Stmt> elseStmt);

	/**
	 * Mutates the else statement of this 'if' statement.
	 *
	 * @param mutation the mutation to apply to the else statement of this 'if' statement.
	 * @return the resulting mutated 'if' statement.
	 */
	IfStmt withElseStmt(Mutation<NodeOption<Stmt>> mutation);
}
