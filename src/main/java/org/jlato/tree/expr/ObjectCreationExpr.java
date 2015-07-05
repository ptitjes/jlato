package org.jlato.tree.expr;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.Expr;
import org.jlato.tree.NodeList;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.Decl;
import org.jlato.tree.type.ClassOrInterfaceType;
import org.jlato.tree.Type;

public class ObjectCreationExpr extends Expr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public ObjectCreationExpr instantiate(SLocation location) {
			return new ObjectCreationExpr(location);
		}

		public LexicalShape shape() {
			return null;
		}
	};

	private ObjectCreationExpr(SLocation location) {
		super(location);
	}

	public ObjectCreationExpr(Expr scope, ClassOrInterfaceType type, NodeList<Type> typeArgs, NodeList<Expr> args, NodeList<Decl> anonymousClassBody) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(scope, type, typeArgs, args, anonymousClassBody)))));
	}

	public Expr scope() {
		return location.nodeChild(SCOPE);
	}

	public ObjectCreationExpr withScope(Expr scope) {
		return location.nodeWithChild(SCOPE, scope);
	}

	public ClassOrInterfaceType type() {
		return location.nodeChild(TYPE);
	}

	public ObjectCreationExpr withType(ClassOrInterfaceType type) {
		return location.nodeWithChild(TYPE, type);
	}

	public NodeList<Type> typeArgs() {
		return location.nodeChild(TYPE_ARGS);
	}

	public ObjectCreationExpr withTypeArgs(NodeList<Type> typeArgs) {
		return location.nodeWithChild(TYPE_ARGS, typeArgs);
	}

	public NodeList<Expr> args() {
		return location.nodeChild(ARGS);
	}

	public ObjectCreationExpr withArgs(NodeList<Expr> args) {
		return location.nodeWithChild(ARGS, args);
	}

	public NodeList<Decl> anonymousClassBody() {
		return location.nodeChild(ANONYMOUS_CLASS_BODY);
	}

	public ObjectCreationExpr withAnonymousClassBody(NodeList<Decl> anonymousClassBody) {
		return location.nodeWithChild(ANONYMOUS_CLASS_BODY, anonymousClassBody);
	}

	private static final int SCOPE = 0;
	private static final int TYPE = 1;
	private static final int TYPE_ARGS = 2;
	private static final int ARGS = 3;
	private static final int ANONYMOUS_CLASS_BODY = 4;
}
