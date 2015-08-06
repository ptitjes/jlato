package org.jlato.tree.decl;

import org.jlato.tree.Node;
import org.jlato.tree.NodeOption;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.Expr;
import org.jlato.util.Mutation;

/**
 * A variable declarator.
 */
public interface VariableDeclarator extends Node, TreeCombinators<VariableDeclarator> {

	/**
	 * Returns the identifier of this variable declarator.
	 *
	 * @return the identifier of this variable declarator.
	 */
	VariableDeclaratorId id();

	/**
	 * Replaces the identifier of this variable declarator.
	 *
	 * @param id the replacement for the identifier of this variable declarator.
	 * @return the resulting mutated variable declarator.
	 */
	VariableDeclarator withId(VariableDeclaratorId id);

	/**
	 * Mutates the identifier of this variable declarator.
	 *
	 * @param mutation the mutation to apply to the identifier of this variable declarator.
	 * @return the resulting mutated variable declarator.
	 */
	VariableDeclarator withId(Mutation<VariableDeclaratorId> mutation);

	/**
	 * Returns the init of this variable declarator.
	 *
	 * @return the init of this variable declarator.
	 */
	NodeOption<Expr> init();

	/**
	 * Replaces the init of this variable declarator.
	 *
	 * @param init the replacement for the init of this variable declarator.
	 * @return the resulting mutated variable declarator.
	 */
	VariableDeclarator withInit(NodeOption<Expr> init);

	/**
	 * Mutates the init of this variable declarator.
	 *
	 * @param mutation the mutation to apply to the init of this variable declarator.
	 * @return the resulting mutated variable declarator.
	 */
	VariableDeclarator withInit(Mutation<NodeOption<Expr>> mutation);
}
