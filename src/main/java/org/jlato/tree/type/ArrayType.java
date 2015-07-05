package org.jlato.tree.type;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.NodeList;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.Type;
import org.jlato.tree.expr.AnnotationExpr;

public class ArrayType extends ReferenceType {

	public final static Tree.Kind kind = new Tree.Kind() {
		public ArrayType instantiate(SLocation location) {
			return new ArrayType(location);
		}

		public LexicalShape shape() {
			return null;
		}
	};

	private ArrayType(SLocation location) {
		super(location);
	}

	public ArrayType(NodeList<AnnotationExpr> annotations, Type componentType) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(annotations, componentType)))));
	}

	public Type componentType() {
		return location.nodeChild(COMPONENT_TYPE);
	}

	public ArrayType withComponentType(Type componentType) {
		return location.nodeWithChild(COMPONENT_TYPE, componentType);
	}

	private static final int COMPONENT_TYPE = 1;
}
