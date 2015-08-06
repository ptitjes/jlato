package org.jlato.tree.stmt;

import org.jlato.tree.Node;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.Expr;
import org.jlato.util.Mutation;

/**
 * A 'switch' case.
 */
public interface SwitchCase extends Node, TreeCombinators<SwitchCase> {

	/**
	 * Returns the label of this 'switch' case.
	 *
	 * @return the label of this 'switch' case.
	 */
	NodeOption<Expr> label();

	/**
	 * Replaces the label of this 'switch' case.
	 *
	 * @param label the replacement for the label of this 'switch' case.
	 * @return the resulting mutated 'switch' case.
	 */
	SwitchCase withLabel(NodeOption<Expr> label);

	/**
	 * Mutates the label of this 'switch' case.
	 *
	 * @param mutation the mutation to apply to the label of this 'switch' case.
	 * @return the resulting mutated 'switch' case.
	 */
	SwitchCase withLabel(Mutation<NodeOption<Expr>> mutation);

	/**
	 * Returns the statements of this 'switch' case.
	 *
	 * @return the statements of this 'switch' case.
	 */
	NodeList<Stmt> stmts();

	/**
	 * Replaces the statements of this 'switch' case.
	 *
	 * @param stmts the replacement for the statements of this 'switch' case.
	 * @return the resulting mutated 'switch' case.
	 */
	SwitchCase withStmts(NodeList<Stmt> stmts);

	/**
	 * Mutates the statements of this 'switch' case.
	 *
	 * @param mutation the mutation to apply to the statements of this 'switch' case.
	 * @return the resulting mutated 'switch' case.
	 */
	SwitchCase withStmts(Mutation<NodeList<Stmt>> mutation);
}
