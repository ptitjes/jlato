package org.jlato.internal.td.type;

import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.type.SUnionType;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.type.Type;
import org.jlato.tree.type.UnionType;
import org.jlato.util.Mutation;

/**
 * An union type.
 */
public class TDUnionType extends TDTree<SUnionType, Type, UnionType> implements UnionType {

	/**
	 * Returns the kind of this union type.
	 *
	 * @return the kind of this union type.
	 */
	public Kind kind() {
		return Kind.UnionType;
	}

	/**
	 * Creates an union type for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDUnionType(TDLocation<SUnionType> location) {
		super(location);
	}

	/**
	 * Creates an union type with the specified child trees.
	 *
	 * @param types the types child tree.
	 */
	public TDUnionType(NodeList<Type> types) {
		super(new TDLocation<SUnionType>(SUnionType.make(TDTree.<SNodeList>treeOf(types))));
	}

	/**
	 * Returns the types of this union type.
	 *
	 * @return the types of this union type.
	 */
	public NodeList<Type> types() {
		return location.safeTraversal(SUnionType.TYPES);
	}

	/**
	 * Replaces the types of this union type.
	 *
	 * @param types the replacement for the types of this union type.
	 * @return the resulting mutated union type.
	 */
	public UnionType withTypes(NodeList<Type> types) {
		return location.safeTraversalReplace(SUnionType.TYPES, types);
	}

	/**
	 * Mutates the types of this union type.
	 *
	 * @param mutation the mutation to apply to the types of this union type.
	 * @return the resulting mutated union type.
	 */
	public UnionType withTypes(Mutation<NodeList<Type>> mutation) {
		return location.safeTraversalMutate(SUnionType.TYPES, mutation);
	}
}
