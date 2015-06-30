package org.jlato.tree.expr;

import org.jlato.internal.bu.SNode;
import org.jlato.tree.Expr;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.type.Type;

public class CallExpr extends Expr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public CallExpr instantiate(SLocation location) {
			return new CallExpr(location);
		}
	};

	private CallExpr(SLocation location) {
		super(location);
	}

	public CallExpr(Expr scope, NodeList<Type> typeArgs, NameExpr name, NodeList<Expr> args) {
		super(new SLocation(new SNode(kind, runOf(scope, typeArgs, name, args))));
	}

	public Expr scope() {
		return location.nodeChild(SCOPE);
	}

	public CallExpr withScope(Expr scope) {
		return location.nodeWithChild(SCOPE, scope);
	}

	public NodeList<Type> typeArgs() {
		return location.nodeChild(TYPE_ARGS);
	}

	public CallExpr withTypeArgs(NodeList<Type> typeArgs) {
		return location.nodeWithChild(TYPE_ARGS, typeArgs);
	}

	public NameExpr name() {
		return location.nodeChild(NAME);
	}

	public CallExpr withName(NameExpr name) {
		return location.nodeWithChild(NAME, name);
	}

	public NodeList<Expr> args() {
		return location.nodeChild(ARGS);
	}

	public CallExpr withArgs(NodeList<Expr> args) {
		return location.nodeWithChild(ARGS, args);
	}

	private static final int SCOPE = 0;
	private static final int TYPE_ARGS = 1;
	private static final int NAME = 2;
	private static final int ARGS = 3;
}
