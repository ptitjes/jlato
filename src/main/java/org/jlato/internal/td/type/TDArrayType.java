package org.jlato.internal.td.type;

import org.jlato.internal.bu.SNodeListState;
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

public class TDArrayType extends TDTree<SArrayType, ReferenceType, ArrayType> implements ArrayType {

	public Kind kind() {
		return Kind.ArrayType;
	}

	public TDArrayType(TDLocation<SArrayType> location) {
		super(location);
	}

	public TDArrayType(Type componentType, NodeList<ArrayDim> dims) {
		super(new TDLocation<SArrayType>(SArrayType.make(TDTree.<SType>treeOf(componentType), TDTree.<SNodeListState>treeOf(dims))));
	}

	public Type componentType() {
		return location.safeTraversal(SArrayType.COMPONENT_TYPE);
	}

	public ArrayType withComponentType(Type componentType) {
		return location.safeTraversalReplace(SArrayType.COMPONENT_TYPE, componentType);
	}

	public ArrayType withComponentType(Mutation<Type> mutation) {
		return location.safeTraversalMutate(SArrayType.COMPONENT_TYPE, mutation);
	}

	public NodeList<ArrayDim> dims() {
		return location.safeTraversal(SArrayType.DIMS);
	}

	public ArrayType withDims(NodeList<ArrayDim> dims) {
		return location.safeTraversalReplace(SArrayType.DIMS, dims);
	}

	public ArrayType withDims(Mutation<NodeList<ArrayDim>> mutation) {
		return location.safeTraversalMutate(SArrayType.DIMS, mutation);
	}
}
