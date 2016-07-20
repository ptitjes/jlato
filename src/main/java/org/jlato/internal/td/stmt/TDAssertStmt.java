package org.jlato.internal.td.stmt;

import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.bu.stmt.SAssertStmt;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Trees;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.stmt.AssertStmt;
import org.jlato.tree.stmt.Stmt;
import org.jlato.util.Mutation;

/**
 * An 'assert' statement.
 */
public class TDAssertStmt extends TDTree<SAssertStmt, Stmt, AssertStmt> implements AssertStmt {

	/**
	 * Returns the kind of this 'assert' statement.
	 *
	 * @return the kind of this 'assert' statement.
	 */
	public Kind kind() {
		return Kind.AssertStmt;
	}

	/**
	 * Creates an 'assert' statement for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDAssertStmt(TDLocation<SAssertStmt> location) {
		super(location);
	}

	/**
	 * Creates an 'assert' statement with the specified child trees.
	 *
	 * @param check the check child tree.
	 * @param msg   the msg child tree.
	 */
	public TDAssertStmt(Expr check, NodeOption<Expr> msg) {
		super(new TDLocation<SAssertStmt>(SAssertStmt.make(TDTree.<SExpr>treeOf(check), TDTree.<SNodeOption>treeOf(msg))));
	}

	/**
	 * Returns the check of this 'assert' statement.
	 *
	 * @return the check of this 'assert' statement.
	 */
	public Expr check() {
		return location.safeTraversal(SAssertStmt.CHECK);
	}

	/**
	 * Replaces the check of this 'assert' statement.
	 *
	 * @param check the replacement for the check of this 'assert' statement.
	 * @return the resulting mutated 'assert' statement.
	 */
	public AssertStmt withCheck(Expr check) {
		return location.safeTraversalReplace(SAssertStmt.CHECK, check);
	}

	/**
	 * Mutates the check of this 'assert' statement.
	 *
	 * @param mutation the mutation to apply to the check of this 'assert' statement.
	 * @return the resulting mutated 'assert' statement.
	 */
	public AssertStmt withCheck(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SAssertStmt.CHECK, mutation);
	}

	/**
	 * Returns the msg of this 'assert' statement.
	 *
	 * @return the msg of this 'assert' statement.
	 */
	public NodeOption<Expr> msg() {
		return location.safeTraversal(SAssertStmt.MSG);
	}

	/**
	 * Replaces the msg of this 'assert' statement.
	 *
	 * @param msg the replacement for the msg of this 'assert' statement.
	 * @return the resulting mutated 'assert' statement.
	 */
	public AssertStmt withMsg(NodeOption<Expr> msg) {
		return location.safeTraversalReplace(SAssertStmt.MSG, msg);
	}

	/**
	 * Mutates the msg of this 'assert' statement.
	 *
	 * @param mutation the mutation to apply to the msg of this 'assert' statement.
	 * @return the resulting mutated 'assert' statement.
	 */
	public AssertStmt withMsg(Mutation<NodeOption<Expr>> mutation) {
		return location.safeTraversalMutate(SAssertStmt.MSG, mutation);
	}

	/**
	 * Replaces the msg of this 'assert' statement.
	 *
	 * @param msg the replacement for the msg of this 'assert' statement.
	 * @return the resulting mutated 'assert' statement.
	 */
	public AssertStmt withMsg(Expr msg) {
		return location.safeTraversalReplace(SAssertStmt.MSG, Trees.some(msg));
	}

	/**
	 * Replaces the msg of this 'assert' statement.
	 *
	 * @return the resulting mutated 'assert' statement.
	 */
	public AssertStmt withNoMsg() {
		return location.safeTraversalReplace(SAssertStmt.MSG, Trees.<Expr>none());
	}
}
