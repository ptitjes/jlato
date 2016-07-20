package org.jlato.internal.td.stmt;

import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.bu.stmt.SSwitchCase;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.Node;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Trees;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.stmt.Stmt;
import org.jlato.tree.stmt.SwitchCase;
import org.jlato.util.Mutation;

/**
 * A 'switch' case.
 */
public class TDSwitchCase extends TDTree<SSwitchCase, Node, SwitchCase> implements SwitchCase {

	/**
	 * Returns the kind of this 'switch' case.
	 *
	 * @return the kind of this 'switch' case.
	 */
	public Kind kind() {
		return Kind.SwitchCase;
	}

	/**
	 * Creates a 'switch' case for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDSwitchCase(TDLocation<SSwitchCase> location) {
		super(location);
	}

	/**
	 * Creates a 'switch' case with the specified child trees.
	 *
	 * @param label the label child tree.
	 * @param stmts the statements child tree.
	 */
	public TDSwitchCase(NodeOption<Expr> label, NodeList<Stmt> stmts) {
		super(new TDLocation<SSwitchCase>(SSwitchCase.make(TDTree.<SNodeOption>treeOf(label), TDTree.<SNodeList>treeOf(stmts))));
	}

	/**
	 * Returns the label of this 'switch' case.
	 *
	 * @return the label of this 'switch' case.
	 */
	public NodeOption<Expr> label() {
		return location.safeTraversal(SSwitchCase.LABEL);
	}

	/**
	 * Replaces the label of this 'switch' case.
	 *
	 * @param label the replacement for the label of this 'switch' case.
	 * @return the resulting mutated 'switch' case.
	 */
	public SwitchCase withLabel(NodeOption<Expr> label) {
		return location.safeTraversalReplace(SSwitchCase.LABEL, label);
	}

	/**
	 * Mutates the label of this 'switch' case.
	 *
	 * @param mutation the mutation to apply to the label of this 'switch' case.
	 * @return the resulting mutated 'switch' case.
	 */
	public SwitchCase withLabel(Mutation<NodeOption<Expr>> mutation) {
		return location.safeTraversalMutate(SSwitchCase.LABEL, mutation);
	}

	/**
	 * Replaces the label of this 'switch' case.
	 *
	 * @param label the replacement for the label of this 'switch' case.
	 * @return the resulting mutated 'switch' case.
	 */
	public SwitchCase withLabel(Expr label) {
		return location.safeTraversalReplace(SSwitchCase.LABEL, Trees.some(label));
	}

	/**
	 * Replaces the label of this 'switch' case.
	 *
	 * @return the resulting mutated 'switch' case.
	 */
	public SwitchCase withNoLabel() {
		return location.safeTraversalReplace(SSwitchCase.LABEL, Trees.<Expr>none());
	}

	/**
	 * Returns the statements of this 'switch' case.
	 *
	 * @return the statements of this 'switch' case.
	 */
	public NodeList<Stmt> stmts() {
		return location.safeTraversal(SSwitchCase.STMTS);
	}

	/**
	 * Replaces the statements of this 'switch' case.
	 *
	 * @param stmts the replacement for the statements of this 'switch' case.
	 * @return the resulting mutated 'switch' case.
	 */
	public SwitchCase withStmts(NodeList<Stmt> stmts) {
		return location.safeTraversalReplace(SSwitchCase.STMTS, stmts);
	}

	/**
	 * Mutates the statements of this 'switch' case.
	 *
	 * @param mutation the mutation to apply to the statements of this 'switch' case.
	 * @return the resulting mutated 'switch' case.
	 */
	public SwitchCase withStmts(Mutation<NodeList<Stmt>> mutation) {
		return location.safeTraversalMutate(SSwitchCase.STMTS, mutation);
	}
}
