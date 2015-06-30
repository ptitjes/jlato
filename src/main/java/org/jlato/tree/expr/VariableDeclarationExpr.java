package org.jlato.tree.expr;

import org.jlato.internal.bu.SNode;
import org.jlato.tree.Expr;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.VariableDeclarator;
import org.jlato.tree.type.Type;

public class VariableDeclarationExpr extends Expr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public VariableDeclarationExpr instantiate(SLocation location) {
			return new VariableDeclarationExpr(location);
		}
	};

	private VariableDeclarationExpr(SLocation location) {
		super(location);
	}

	public VariableDeclarationExpr(int modifiers, NodeList<AnnotationExpr> annotations, Type type, NodeList<VariableDeclarator> vars) {
		super(new SLocation(new SNode(kind, runOf(modifiers, annotations, type, vars))));
	}

	public int modifiers() {
		return location.nodeChild(MODIFIERS);
	}

	public VariableDeclarationExpr withModifiers(int modifiers) {
		return location.nodeWithChild(MODIFIERS, modifiers);
	}

	public NodeList<AnnotationExpr> annotations() {
		return location.nodeChild(ANNOTATIONS);
	}

	public VariableDeclarationExpr withAnnotations(NodeList<AnnotationExpr> annotations) {
		return location.nodeWithChild(ANNOTATIONS, annotations);
	}

	public Type type() {
		return location.nodeChild(TYPE);
	}

	public VariableDeclarationExpr withType(Type type) {
		return location.nodeWithChild(TYPE, type);
	}

	public NodeList<VariableDeclarator> vars() {
		return location.nodeChild(VARS);
	}

	public VariableDeclarationExpr withVars(NodeList<VariableDeclarator> vars) {
		return location.nodeWithChild(VARS, vars);
	}

	private static final int MODIFIERS = 0;
	private static final int ANNOTATIONS = 1;
	private static final int TYPE = 2;
	private static final int VARS = 3;
}
