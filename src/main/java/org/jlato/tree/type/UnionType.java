package org.jlato.tree.type;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.NodeList;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.Type;

public class UnionType extends Type {

	public final static Tree.Kind kind = new Tree.Kind() {
		public UnionType instantiate(SLocation location) {
			return new UnionType(location);
		}

		public LexicalShape shape() {
			return null;
		}
	};

	private UnionType(SLocation location) {
		super(location);
	}

	public UnionType(NodeList<Type> types) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(types)))));
	}

	public NodeList<Type> types() {
		return location.nodeChild(TYPES);
	}

	public UnionType withTypes(NodeList<Type> types) {
		return location.nodeWithChild(TYPES, types);
	}

	private static final int TYPES = 0;
}
