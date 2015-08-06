package org.jlato.tree.type;

import org.jlato.tree.NodeList;
import org.jlato.tree.TreeCombinators;
import org.jlato.util.Mutation;

/**
 * An intersection type.
 */
public interface IntersectionType extends Type, TreeCombinators<IntersectionType> {

	/**
	 * Returns the types of this intersection type.
	 *
	 * @return the types of this intersection type.
	 */
	NodeList<Type> types();

	/**
	 * Replaces the types of this intersection type.
	 *
	 * @param types the replacement for the types of this intersection type.
	 * @return the resulting mutated intersection type.
	 */
	IntersectionType withTypes(NodeList<Type> types);

	/**
	 * Mutates the types of this intersection type.
	 *
	 * @param mutation the mutation to apply to the types of this intersection type.
	 * @return the resulting mutated intersection type.
	 */
	IntersectionType withTypes(Mutation<NodeList<Type>> mutation);
}
