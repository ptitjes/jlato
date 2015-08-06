package org.jlato.tree.stmt;

import org.jlato.tree.NodeList;
import org.jlato.tree.TreeCombinators;
import org.jlato.util.Mutation;

/**
 * A block statement.
 */
public interface BlockStmt extends Stmt, TreeCombinators<BlockStmt> {

	/**
	 * Returns the statements of this block statement.
	 *
	 * @return the statements of this block statement.
	 */
	NodeList<Stmt> stmts();

	/**
	 * Replaces the statements of this block statement.
	 *
	 * @param stmts the replacement for the statements of this block statement.
	 * @return the resulting mutated block statement.
	 */
	BlockStmt withStmts(NodeList<Stmt> stmts);

	/**
	 * Mutates the statements of this block statement.
	 *
	 * @param mutation the mutation to apply to the statements of this block statement.
	 * @return the resulting mutated block statement.
	 */
	BlockStmt withStmts(Mutation<NodeList<Stmt>> mutation);
}
