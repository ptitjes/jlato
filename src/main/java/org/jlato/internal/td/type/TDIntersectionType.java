package org.jlato.internal.td.type;

import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.type.SIntersectionType;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.type.IntersectionType;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

public class TDIntersectionType extends TDTree<SIntersectionType, Type, IntersectionType> implements IntersectionType {

	public Kind kind() {
		return Kind.IntersectionType;
	}

	public TDIntersectionType(TDLocation<SIntersectionType> location) {
		super(location);
	}

	public TDIntersectionType(NodeList<Type> types) {
		super(new TDLocation<SIntersectionType>(SIntersectionType.make(TDTree.<SNodeListState>treeOf(types))));
	}

	public NodeList<Type> types() {
		return location.safeTraversal(SIntersectionType.TYPES);
	}

	public IntersectionType withTypes(NodeList<Type> types) {
		return location.safeTraversalReplace(SIntersectionType.TYPES, types);
	}

	public IntersectionType withTypes(Mutation<NodeList<Type>> mutation) {
		return location.safeTraversalMutate(SIntersectionType.TYPES, mutation);
	}
}
