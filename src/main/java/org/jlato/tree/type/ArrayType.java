package org.jlato.tree.type;

import org.jlato.internal.bu.SNode;
import org.jlato.tree.Tree;

public class ArrayType extends ReferenceType {

	public final static Tree.Kind kind = new Tree.Kind() {
		public ArrayType instantiate(SLocation location) {
			return new ArrayType(location);
		}
	};

	private ArrayType(SLocation location) {
		super(location);
	}

	public ArrayType(Type componentType) {
		super(new SLocation(new SNode(kind, runOf(componentType))));
	}

	public Type componentType() {
		return location.nodeChild(COMPONENT_TYPE);
	}

	public ArrayType withComponentType(Type componentType) {
		return location.nodeWithChild(COMPONENT_TYPE, componentType);
	}

	private static final int COMPONENT_TYPE = 1;
}
