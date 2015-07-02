package org.jlato.tree.stmt;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SLeaf;
import org.jlato.internal.bu.SNode;
import org.jlato.tree.Expr;
import org.jlato.tree.NodeList;
import org.jlato.tree.Stmt;
import org.jlato.tree.Tree;
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
		super(new SLocation(new SNode(kind, runOf(typeArgs, isThis ? ConstructorKind.This : ConstructorKind.Super, expr, args))));
	}

	public NodeList<Type> typeArgs() {
		return location.nodeChild(TYPE_ARGS);
	}

	public ExplicitConstructorInvocationStmt withTypeArgs(NodeList<Type> typeArgs) {
		return location.nodeWithChild(TYPE_ARGS, typeArgs);
	}

	public boolean isThis() {
		return location.nodeChild(CONSTRUCTOR_KIND) == ConstructorKind.This;
	}

	public ExplicitConstructorInvocationStmt setThis(boolean isThis) {
		return location.nodeWithChild(CONSTRUCTOR_KIND, isThis ? ConstructorKind.This : ConstructorKind.Super);
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
	private static final int CONSTRUCTOR_KIND = 1;
	private static final int EXPR = 2;
	private static final int ARGS = 3;

	private static class ConstructorKind extends Tree {

		public final static Kind kind = new Kind() {
			public Tree instantiate(SLocation location) {
				return new ConstructorKind(location);
			}
		};

		public static final ConstructorKind This = new ConstructorKind(LToken.This);
		public static final ConstructorKind Super = new ConstructorKind(LToken.Super);

		protected ConstructorKind(SLocation location) {
			super(location);
		}

		private ConstructorKind(LToken keyword) {
			super(new SLocation(new SLeaf(kind, keyword)));
		}

		public String toString() {
			return location.leafToken().toString();
		}
	}
}
