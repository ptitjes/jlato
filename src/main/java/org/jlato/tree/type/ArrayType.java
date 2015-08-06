package org.jlato.tree.type;

import org.jlato.tree.NodeList;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.decl.ArrayDim;
import org.jlato.util.Mutation;

/**
 * An array type.
 */
public interface ArrayType extends ReferenceType, TreeCombinators<ArrayType> {

	/**
	 * Returns the component type of this array type.
	 *
	 * @return the component type of this array type.
	 */
	Type componentType();

	/**
	 * Replaces the component type of this array type.
	 *
	 * @param componentType the replacement for the component type of this array type.
	 * @return the resulting mutated array type.
	 */
	ArrayType withComponentType(Type componentType);

	/**
	 * Mutates the component type of this array type.
	 *
	 * @param mutation the mutation to apply to the component type of this array type.
	 * @return the resulting mutated array type.
	 */
	ArrayType withComponentType(Mutation<Type> mutation);

	/**
	 * Returns the dimensions of this array type.
	 *
	 * @return the dimensions of this array type.
	 */
	NodeList<ArrayDim> dims();

	/**
	 * Replaces the dimensions of this array type.
	 *
	 * @param dims the replacement for the dimensions of this array type.
	 * @return the resulting mutated array type.
	 */
	ArrayType withDims(NodeList<ArrayDim> dims);

	/**
	 * Mutates the dimensions of this array type.
	 *
	 * @param mutation the mutation to apply to the dimensions of this array type.
	 * @return the resulting mutated array type.
	 */
	ArrayType withDims(Mutation<NodeList<ArrayDim>> mutation);
}
