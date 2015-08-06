package org.jlato.tree.stmt;

import org.jlato.tree.NodeList;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.Expr;
import org.jlato.util.Mutation;

/**
 * A 'for' statement.
 */
public interface ForStmt extends Stmt, TreeCombinators<ForStmt> {

	/**
	 * Returns the init of this 'for' statement.
	 *
	 * @return the init of this 'for' statement.
	 */
	NodeList<Expr> init();

	/**
	 * Replaces the init of this 'for' statement.
	 *
	 * @param init the replacement for the init of this 'for' statement.
	 * @return the resulting mutated 'for' statement.
	 */
	ForStmt withInit(NodeList<Expr> init);

	/**
	 * Mutates the init of this 'for' statement.
	 *
	 * @param mutation the mutation to apply to the init of this 'for' statement.
	 * @return the resulting mutated 'for' statement.
	 */
	ForStmt withInit(Mutation<NodeList<Expr>> mutation);

	/**
	 * Returns the compare of this 'for' statement.
	 *
	 * @return the compare of this 'for' statement.
	 */
	Expr compare();

	/**
	 * Replaces the compare of this 'for' statement.
	 *
	 * @param compare the replacement for the compare of this 'for' statement.
	 * @return the resulting mutated 'for' statement.
	 */
	ForStmt withCompare(Expr compare);

	/**
	 * Mutates the compare of this 'for' statement.
	 *
	 * @param mutation the mutation to apply to the compare of this 'for' statement.
	 * @return the resulting mutated 'for' statement.
	 */
	ForStmt withCompare(Mutation<Expr> mutation);

	/**
	 * Returns the update of this 'for' statement.
	 *
	 * @return the update of this 'for' statement.
	 */
	NodeList<Expr> update();

	/**
	 * Replaces the update of this 'for' statement.
	 *
	 * @param update the replacement for the update of this 'for' statement.
	 * @return the resulting mutated 'for' statement.
	 */
	ForStmt withUpdate(NodeList<Expr> update);

	/**
	 * Mutates the update of this 'for' statement.
	 *
	 * @param mutation the mutation to apply to the update of this 'for' statement.
	 * @return the resulting mutated 'for' statement.
	 */
	ForStmt withUpdate(Mutation<NodeList<Expr>> mutation);

	/**
	 * Returns the body of this 'for' statement.
	 *
	 * @return the body of this 'for' statement.
	 */
	Stmt body();

	/**
	 * Replaces the body of this 'for' statement.
	 *
	 * @param body the replacement for the body of this 'for' statement.
	 * @return the resulting mutated 'for' statement.
	 */
	ForStmt withBody(Stmt body);

	/**
	 * Mutates the body of this 'for' statement.
	 *
	 * @param mutation the mutation to apply to the body of this 'for' statement.
	 * @return the resulting mutated 'for' statement.
	 */
	ForStmt withBody(Mutation<Stmt> mutation);
}
