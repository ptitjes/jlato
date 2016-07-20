package org.jlato.tree.stmt;

import org.jlato.tree.NodeOption;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.Expr;
import org.jlato.util.Mutation;

/**
 * An 'assert' statement.
 */
public interface AssertStmt extends Stmt, TreeCombinators<AssertStmt> {

	/**
	 * Returns the check of this 'assert' statement.
	 *
	 * @return the check of this 'assert' statement.
	 */
	Expr check();

	/**
	 * Replaces the check of this 'assert' statement.
	 *
	 * @param check the replacement for the check of this 'assert' statement.
	 * @return the resulting mutated 'assert' statement.
	 */
	AssertStmt withCheck(Expr check);

	/**
	 * Mutates the check of this 'assert' statement.
	 *
	 * @param mutation the mutation to apply to the check of this 'assert' statement.
	 * @return the resulting mutated 'assert' statement.
	 */
	AssertStmt withCheck(Mutation<Expr> mutation);

	/**
	 * Returns the msg of this 'assert' statement.
	 *
	 * @return the msg of this 'assert' statement.
	 */
	NodeOption<Expr> msg();

	/**
	 * Replaces the msg of this 'assert' statement.
	 *
	 * @param msg the replacement for the msg of this 'assert' statement.
	 * @return the resulting mutated 'assert' statement.
	 */
	AssertStmt withMsg(NodeOption<Expr> msg);

	/**
	 * Mutates the msg of this 'assert' statement.
	 *
	 * @param mutation the mutation to apply to the msg of this 'assert' statement.
	 * @return the resulting mutated 'assert' statement.
	 */
	AssertStmt withMsg(Mutation<NodeOption<Expr>> mutation);

	/**
	 * Replaces the msg of this 'assert' statement.
	 *
	 * @param msg the replacement for the msg of this 'assert' statement.
	 * @return the resulting mutated 'assert' statement.
	 */
	AssertStmt withMsg(Expr msg);

	/**
	 * Replaces the msg of this 'assert' statement.
	 *
	 * @return the resulting mutated 'assert' statement.
	 */
	AssertStmt withNoMsg();
}
