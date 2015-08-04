package org.jlato.internal.td.type;

import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.type.SUnionType;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.type.Type;
import org.jlato.tree.type.UnionType;
import org.jlato.util.Mutation;

public class TDUnionType extends TDTree<SUnionType, Type, UnionType> implements UnionType {

	public Kind kind() {
		return Kind.UnionType;
	}

	public TDUnionType(TDLocation<SUnionType> location) {
		super(location);
	}

	public TDUnionType(NodeList<Type> types) {
		super(new TDLocation<SUnionType>(SUnionType.make(TDTree.<SNodeListState>treeOf(types))));
	}

	public NodeList<Type> types() {
		return location.safeTraversal(SUnionType.TYPES);
	}

	public UnionType withTypes(NodeList<Type> types) {
		return location.safeTraversalReplace(SUnionType.TYPES, types);
	}

	public UnionType withTypes(Mutation<NodeList<Type>> mutation) {
		return location.safeTraversalMutate(SUnionType.TYPES, mutation);
	}
}
