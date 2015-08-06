package org.jlato.internal.td.type;

import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.type.SArrayType;
import org.jlato.internal.bu.type.SType;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.decl.ArrayDim;
import org.jlato.tree.type.ArrayType;
import org.jlato.tree.type.ReferenceType;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

/**
 * An array type.
 */
public class TDArrayType extends TDTree<SArrayType, ReferenceType, ArrayType> implements ArrayType {

	/**
	 * Returns the kind of this array type.
	 *
	 * @return the kind of this array type.
	 */
	public Kind kind() {
		return Kind.ArrayType;
	}

	/**
	 * Creates an array type for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDArrayType(TDLocation<SArrayType> location) {
		super(location);
	}

	/**
	 * Creates an array type with the specified child trees.
	 *
	 * @param componentType the component type child tree.
	 * @param dims          the dimensions child tree.
	 */
	public TDArrayType(Type componentType, NodeList<ArrayDim> dims) {
		super(new TDLocation<SArrayType>(SArrayType.make(TDTree.<SType>treeOf(componentType), TDTree.<SNodeList>treeOf(dims))));
	}

	/**
	 * Returns the component type of this array type.
	 *
	 * @return the component type of this array type.
	 */
	public Type componentType() {
		return location.safeTraversal(SArrayType.COMPONENT_TYPE);
	}

	/**
	 * Replaces the component type of this array type.
	 *
	 * @param componentType the replacement for the component type of this array type.
	 * @return the resulting mutated array type.
	 */
	public ArrayType withComponentType(Type componentType) {
		return location.safeTraversalReplace(SArrayType.COMPONENT_TYPE, componentType);
	}

	/**
	 * Mutates the component type of this array type.
	 *
	 * @param mutation the mutation to apply to the component type of this array type.
	 * @return the resulting mutated array type.
	 */
	public ArrayType withComponentType(Mutation<Type> mutation) {
		return location.safeTraversalMutate(SArrayType.COMPONENT_TYPE, mutation);
	}

	/**
	 * Returns the dimensions of this array type.
	 *
	 * @return the dimensions of this array type.
	 */
	public NodeList<ArrayDim> dims() {
		return location.safeTraversal(SArrayType.DIMS);
	}

	/**
	 * Replaces the dimensions of this array type.
	 *
	 * @param dims the replacement for the dimensions of this array type.
	 * @return the resulting mutated array type.
	 */
	public ArrayType withDims(NodeList<ArrayDim> dims) {
		return location.safeTraversalReplace(SArrayType.DIMS, dims);
	}

	/**
	 * Mutates the dimensions of this array type.
	 *
	 * @param mutation the mutation to apply to the dimensions of this array type.
	 * @return the resulting mutated array type.
	 */
	public ArrayType withDims(Mutation<NodeList<ArrayDim>> mutation) {
		return location.safeTraversalMutate(SArrayType.DIMS, mutation);
	}
}
