package org.jlato.tree.type;

import org.jlato.tree.NodeList;
import org.jlato.tree.TreeCombinators;
import org.jlato.util.Mutation;

/**
 * An union type.
 */
public interface UnionType extends Type, TreeCombinators<UnionType> {

	/**
	 * Returns the types of this union type.
	 *
	 * @return the types of this union type.
	 */
	NodeList<Type> types();

	/**
	 * Replaces the types of this union type.
	 *
	 * @param types the replacement for the types of this union type.
	 * @return the resulting mutated union type.
	 */
	UnionType withTypes(NodeList<Type> types);

	/**
	 * Mutates the types of this union type.
	 *
	 * @param mutation the mutation to apply to the types of this union type.
	 * @return the resulting mutated union type.
	 */
	UnionType withTypes(Mutation<NodeList<Type>> mutation);
}
