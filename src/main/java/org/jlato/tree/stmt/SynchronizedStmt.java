package org.jlato.tree.stmt;

import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.Expr;
import org.jlato.util.Mutation;

/**
 * A 'synchronized' statement.
 */
public interface SynchronizedStmt extends Stmt, TreeCombinators<SynchronizedStmt> {

	/**
	 * Returns the expression of this 'synchronized' statement.
	 *
	 * @return the expression of this 'synchronized' statement.
	 */
	Expr expr();

	/**
	 * Replaces the expression of this 'synchronized' statement.
	 *
	 * @param expr the replacement for the expression of this 'synchronized' statement.
	 * @return the resulting mutated 'synchronized' statement.
	 */
	SynchronizedStmt withExpr(Expr expr);

	/**
	 * Mutates the expression of this 'synchronized' statement.
	 *
	 * @param mutation the mutation to apply to the expression of this 'synchronized' statement.
	 * @return the resulting mutated 'synchronized' statement.
	 */
	SynchronizedStmt withExpr(Mutation<Expr> mutation);

	/**
	 * Returns the block of this 'synchronized' statement.
	 *
	 * @return the block of this 'synchronized' statement.
	 */
	BlockStmt block();

	/**
	 * Replaces the block of this 'synchronized' statement.
	 *
	 * @param block the replacement for the block of this 'synchronized' statement.
	 * @return the resulting mutated 'synchronized' statement.
	 */
	SynchronizedStmt withBlock(BlockStmt block);

	/**
	 * Mutates the block of this 'synchronized' statement.
	 *
	 * @param mutation the mutation to apply to the block of this 'synchronized' statement.
	 * @return the resulting mutated 'synchronized' statement.
	 */
	SynchronizedStmt withBlock(Mutation<BlockStmt> mutation);
}
