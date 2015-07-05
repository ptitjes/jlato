package org.jlato.tree.stmt;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SLeaf;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.SLocation;
import org.jlato.tree.*;
import org.jlato.tree.Type;

public class ExplicitConstructorInvocationStmt extends Stmt {

	public final static Tree.Kind kind = new Tree.Kind() {
		public ExplicitConstructorInvocationStmt instantiate(SLocation location) {
			return new ExplicitConstructorInvocationStmt(location);
		}

		public LexicalShape shape() {
			return null;
		}
	};

	private ExplicitConstructorInvocationStmt(SLocation location) {
		super(location);
	}

	public ExplicitConstructorInvocationStmt(NodeList<Type> typeArgs, boolean isThis, Expr expr, NodeList<Expr> args) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(typeArgs, expr, args), dataOf(constructorKind(isThis))))));
	}

	public NodeList<Type> typeArgs() {
		return location.nodeChild(TYPE_ARGS);
	}

	public ExplicitConstructorInvocationStmt withTypeArgs(NodeList<Type> typeArgs) {
		return location.nodeWithChild(TYPE_ARGS, typeArgs);
	}

	public boolean isThis() {
		return location.nodeData(CONSTRUCTOR_KIND) == ConstructorKind.This;
	}

	public ExplicitConstructorInvocationStmt setThis(boolean isThis) {
		return location.nodeWithData(CONSTRUCTOR_KIND, constructorKind(isThis));
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
	private static final int EXPR = 1;
	private static final int ARGS = 2;

	private static final int CONSTRUCTOR_KIND = 0;

	private static ConstructorKind constructorKind(boolean isThis) {
		return isThis ? ConstructorKind.This : ConstructorKind.Super;
	}

	public enum ConstructorKind {
		This(LToken.This),
		Super(LToken.Super),
		// Keep last comma
		;

		protected final LToken token;

		ConstructorKind(LToken token) {
			this.token = token;
		}

		public String toString() {
			return token.toString();
		}
	}
}
