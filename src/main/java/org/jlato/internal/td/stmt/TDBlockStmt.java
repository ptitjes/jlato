package org.jlato.internal.td.stmt;

import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.stmt.SBlockStmt;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.stmt.BlockStmt;
import org.jlato.tree.stmt.Stmt;
import org.jlato.util.Mutation;

/**
 * A block statement.
 */
public class TDBlockStmt extends TDTree<SBlockStmt, Stmt, BlockStmt> implements BlockStmt {

	/**
	 * Returns the kind of this block statement.
	 *
	 * @return the kind of this block statement.
	 */
	public Kind kind() {
		return Kind.BlockStmt;
	}

	/**
	 * Creates a block statement for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDBlockStmt(TDLocation<SBlockStmt> location) {
		super(location);
	}

	/**
	 * Creates a block statement with the specified child trees.
	 *
	 * @param stmts the statements child tree.
	 */
	public TDBlockStmt(NodeList<Stmt> stmts) {
		super(new TDLocation<SBlockStmt>(SBlockStmt.make(TDTree.<SNodeList>treeOf(stmts))));
	}

	/**
	 * Returns the statements of this block statement.
	 *
	 * @return the statements of this block statement.
	 */
	public NodeList<Stmt> stmts() {
		return location.safeTraversal(SBlockStmt.STMTS);
	}

	/**
	 * Replaces the statements of this block statement.
	 *
	 * @param stmts the replacement for the statements of this block statement.
	 * @return the resulting mutated block statement.
	 */
	public BlockStmt withStmts(NodeList<Stmt> stmts) {
		return location.safeTraversalReplace(SBlockStmt.STMTS, stmts);
	}

	/**
	 * Mutates the statements of this block statement.
	 *
	 * @param mutation the mutation to apply to the statements of this block statement.
	 * @return the resulting mutated block statement.
	 */
	public BlockStmt withStmts(Mutation<NodeList<Stmt>> mutation) {
		return location.safeTraversalMutate(SBlockStmt.STMTS, mutation);
	}
}
