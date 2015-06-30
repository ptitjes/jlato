package org.jlato.tree.type;

import org.jlato.internal.bu.SNode;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.NameExpr;

public class ClassOrInterfaceType extends ReferenceType {

	public final static Tree.Kind kind = new Tree.Kind() {
		public ClassOrInterfaceType instantiate(SLocation location) {
			return new ClassOrInterfaceType(location);
		}
	};

	private ClassOrInterfaceType(SLocation location) {
		super(location);
	}

	public ClassOrInterfaceType(ClassOrInterfaceType scope, NameExpr name, NodeList<Type> typeArgs) {
		super(new SLocation(new SNode(kind, runOf(scope, name, typeArgs))));
	}

	public ClassOrInterfaceType scope() {
		return location.nodeChild(SCOPE);
	}

	public ClassOrInterfaceType withScope(ClassOrInterfaceType scope) {
		return location.nodeWithChild(SCOPE, scope);
	}

	public NameExpr name() {
		return location.nodeChild(NAME);
	}

	public ClassOrInterfaceType withName(NameExpr name) {
		return location.nodeWithChild(NAME, name);
	}

	public NodeList<Type> typeArgs() {
		return location.nodeChild(TYPE_ARGS);
	}

	public ClassOrInterfaceType withTypeArgs(NodeList<Type> typeArgs) {
		return location.nodeWithChild(TYPE_ARGS, typeArgs);
	}

	private static final int SCOPE = 1;
	private static final int NAME = 2;
	private static final int TYPE_ARGS = 3;
}
