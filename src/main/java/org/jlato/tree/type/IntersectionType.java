package org.jlato.tree.type;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeData;
import org.jlato.tree.NodeList;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.Type;

public class IntersectionType extends Type {

	public final static Tree.Kind kind = new Tree.Kind() {
		public IntersectionType instantiate(SLocation location) {
			return new IntersectionType(location);
		}
	};

	private IntersectionType(SLocation location) {
		super(location);
	}

	public IntersectionType(NodeList<Type> types) {
		super(new SLocation(new SNode(kind, new SNodeData(treesOf(types)))));
	}

	public NodeList<Type> types() {
		return location.nodeChild(TYPES);
	}

	public IntersectionType withTypes(NodeList<Type> types) {
		return location.nodeWithChild(TYPES, types);
	}

	private static final int TYPES = 0;
}
