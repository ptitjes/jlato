package org.jlato.tree.decl;

import org.jlato.tree.Node;
import org.jlato.tree.NodeList;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.name.Name;
import org.jlato.util.Mutation;

/**
 * A variable declarator identifier.
 */
public interface VariableDeclaratorId extends Node, TreeCombinators<VariableDeclaratorId> {

	/**
	 * Returns the name of this variable declarator identifier.
	 *
	 * @return the name of this variable declarator identifier.
	 */
	Name name();

	/**
	 * Replaces the name of this variable declarator identifier.
	 *
	 * @param name the replacement for the name of this variable declarator identifier.
	 * @return the resulting mutated variable declarator identifier.
	 */
	VariableDeclaratorId withName(Name name);

	/**
	 * Mutates the name of this variable declarator identifier.
	 *
	 * @param mutation the mutation to apply to the name of this variable declarator identifier.
	 * @return the resulting mutated variable declarator identifier.
	 */
	VariableDeclaratorId withName(Mutation<Name> mutation);

	/**
	 * Returns the dimensions of this variable declarator identifier.
	 *
	 * @return the dimensions of this variable declarator identifier.
	 */
	NodeList<ArrayDim> dims();

	/**
	 * Replaces the dimensions of this variable declarator identifier.
	 *
	 * @param dims the replacement for the dimensions of this variable declarator identifier.
	 * @return the resulting mutated variable declarator identifier.
	 */
	VariableDeclaratorId withDims(NodeList<ArrayDim> dims);

	/**
	 * Mutates the dimensions of this variable declarator identifier.
	 *
	 * @param mutation the mutation to apply to the dimensions of this variable declarator identifier.
	 * @return the resulting mutated variable declarator identifier.
	 */
	VariableDeclaratorId withDims(Mutation<NodeList<ArrayDim>> mutation);
}
