package org.jlato.internal.td.stmt;

import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.bu.stmt.SBlockStmt;
import org.jlato.internal.bu.stmt.SSynchronizedStmt;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.stmt.BlockStmt;
import org.jlato.tree.stmt.Stmt;
import org.jlato.tree.stmt.SynchronizedStmt;
import org.jlato.util.Mutation;

/**
 * A 'synchronized' statement.
 */
public class TDSynchronizedStmt extends TDTree<SSynchronizedStmt, Stmt, SynchronizedStmt> implements SynchronizedStmt {

	/**
	 * Returns the kind of this 'synchronized' statement.
	 *
	 * @return the kind of this 'synchronized' statement.
	 */
	public Kind kind() {
		return Kind.SynchronizedStmt;
	}

	/**
	 * Creates a 'synchronized' statement for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDSynchronizedStmt(TDLocation<SSynchronizedStmt> location) {
		super(location);
	}

	/**
	 * Creates a 'synchronized' statement with the specified child trees.
	 *
	 * @param expr  the expression child tree.
	 * @param block the block child tree.
	 */
	public TDSynchronizedStmt(Expr expr, BlockStmt block) {
		super(new TDLocation<SSynchronizedStmt>(SSynchronizedStmt.make(TDTree.<SExpr>treeOf(expr), TDTree.<SBlockStmt>treeOf(block))));
	}

	/**
	 * Returns the expression of this 'synchronized' statement.
	 *
	 * @return the expression of this 'synchronized' statement.
	 */
	public Expr expr() {
		return location.safeTraversal(SSynchronizedStmt.EXPR);
	}

	/**
	 * Replaces the expression of this 'synchronized' statement.
	 *
	 * @param expr the replacement for the expression of this 'synchronized' statement.
	 * @return the resulting mutated 'synchronized' statement.
	 */
	public SynchronizedStmt withExpr(Expr expr) {
		return location.safeTraversalReplace(SSynchronizedStmt.EXPR, expr);
	}

	/**
	 * Mutates the expression of this 'synchronized' statement.
	 *
	 * @param mutation the mutation to apply to the expression of this 'synchronized' statement.
	 * @return the resulting mutated 'synchronized' statement.
	 */
	public SynchronizedStmt withExpr(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SSynchronizedStmt.EXPR, mutation);
	}

	/**
	 * Returns the block of this 'synchronized' statement.
	 *
	 * @return the block of this 'synchronized' statement.
	 */
	public BlockStmt block() {
		return location.safeTraversal(SSynchronizedStmt.BLOCK);
	}

	/**
	 * Replaces the block of this 'synchronized' statement.
	 *
	 * @param block the replacement for the block of this 'synchronized' statement.
	 * @return the resulting mutated 'synchronized' statement.
	 */
	public SynchronizedStmt withBlock(BlockStmt block) {
		return location.safeTraversalReplace(SSynchronizedStmt.BLOCK, block);
	}

	/**
	 * Mutates the block of this 'synchronized' statement.
	 *
	 * @param mutation the mutation to apply to the block of this 'synchronized' statement.
	 * @return the resulting mutated 'synchronized' statement.
	 */
	public SynchronizedStmt withBlock(Mutation<BlockStmt> mutation) {
		return location.safeTraversalMutate(SSynchronizedStmt.BLOCK, mutation);
	}
}
