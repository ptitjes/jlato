package org.jlato.tree.stmt;

import org.jlato.internal.bu.SNode;
import org.jlato.tree.*;
import org.jlato.tree.type.Type;

public class ExplicitConstructorInvocationStmt extends Stmt {

	public final static Tree.Kind kind = new Tree.Kind() {
		public ExplicitConstructorInvocationStmt instantiate(SLocation location) {
			return new ExplicitConstructorInvocationStmt(location);
		}
	};

	private ExplicitConstructorInvocationStmt(SLocation location) {
		super(location);
	}

	public ExplicitConstructorInvocationStmt(NodeList<Type> typeArgs, boolean isThis, Expr expr, NodeList<Expr> args) {
		super(new SLocation(new SNode(kind, runOf(typeArgs, isThis, expr, args))));
	}

	public NodeList<Type> typeArgs() {
		return location.nodeChild(TYPE_ARGS);
	}

	public ExplicitConstructorInvocationStmt withTypeArgs(NodeList<Type> typeArgs) {
		return location.nodeWithChild(TYPE_ARGS, typeArgs);
	}

	public boolean isThis() {
		return location.nodeChild(IS_THIS);
	}

	public ExplicitConstructorInvocationStmt withIsThis(boolean isThis) {
		return location.nodeWithChild(IS_THIS, isThis);
	}

	public Expr expr() {
		return location.nodeChild(EXPR);
	}

	public ExplicitConstructorInvocationStmt withExpr(Expr expr) {
		return location.nodeWithChild(EXPR, expr);
	}

	public NodeList<Expr> args() {
		return location.nodeChild(ARGS);
	}

	public ExplicitConstructorInvocationStmt withArgs(NodeList<Expr> args) {
		return location.nodeWithChild(ARGS, args);
	}

	private static final int TYPE_ARGS = 0;
	private static final int IS_THIS = 1;
	private static final int EXPR = 2;
	private static final int ARGS = 3;
}
