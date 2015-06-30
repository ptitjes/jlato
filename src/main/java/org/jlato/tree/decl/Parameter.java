package org.jlato.tree.decl;

import org.jlato.internal.bu.SNode;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.type.Type;

public class Parameter extends Tree {

	public final static Tree.Kind kind = new Tree.Kind() {
		public Parameter instantiate(SLocation location) {
			return new Parameter(location);
		}
	};

	private Parameter(SLocation location) {
		super(location);
	}

	public Parameter(int modifiers, NodeList<AnnotationExpr> annotations, Type type, VariableDeclaratorId id, boolean isVarArgs) {
		super(new SLocation(new SNode(kind, runOf(modifiers, annotations, type, id, isVarArgs))));
	}

	public int modifiers() {
		return location.nodeChild(MODIFIERS);
	}

	public Parameter withModifiers(int modifiers) {
		return location.nodeWithChild(MODIFIERS, modifiers);
	}

	public NodeList<AnnotationExpr> annotations() {
		return location.nodeChild(ANNOTATIONS);
	}

	public Parameter withAnnotations(NodeList<AnnotationExpr> annotations) {
		return location.nodeWithChild(ANNOTATIONS, annotations);
	}

	public Type type() {
		return location.nodeChild(TYPE);
	}

	public Parameter withType(Type type) {
		return location.nodeWithChild(TYPE, type);
	}

	public VariableDeclaratorId id() {
		return location.nodeChild(ID);
	}

	public Parameter withId(VariableDeclaratorId id) {
		return location.nodeWithChild(ID, id);
	}

	public boolean isVarArgs() {
		return location.nodeChild(IS_VAR_ARGS);
	}

	public Parameter withIsVarArgs(boolean isVarArgs) {
		return location.nodeWithChild(IS_VAR_ARGS, isVarArgs);
	}

	private static final int MODIFIERS = 0;
	private static final int ANNOTATIONS = 1;
	private static final int TYPE = 2;
	private static final int ID = 3;
	private static final int IS_VAR_ARGS = 4;
}
