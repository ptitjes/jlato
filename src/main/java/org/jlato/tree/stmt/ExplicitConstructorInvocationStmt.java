package org.jlato.tree.stmt;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LSToken;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.*;

import static org.jlato.internal.shapes.LexicalShape.Factory.*;

public class ExplicitConstructorInvocationStmt extends Stmt {

	public final static Tree.Kind kind = new Tree.Kind() {
		public ExplicitConstructorInvocationStmt instantiate(SLocation location) {
			return new ExplicitConstructorInvocationStmt(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private ExplicitConstructorInvocationStmt(SLocation location) {
		super(location);
	}

	public ExplicitConstructorInvocationStmt(NodeList<Type> typeArgs, boolean isThis, Expr expr, NodeList<Expr> args) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(typeArgs, expr, args), dataOf(constructorKind(isThis))))));
	}

	public NodeList<Type> typeArguments() {
		return location.nodeChild(TYPE_ARGUMENTS);
	}

	public ExplicitConstructorInvocationStmt withTypeArguments(NodeList<Type> typeArguments) {
		return location.nodeWithChild(TYPE_ARGUMENTS, typeArguments);
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

	public NodeList<Expr> arguments() {
		return location.nodeChild(ARGUMENTS);
	}

	public ExplicitConstructorInvocationStmt withArguments(NodeList<Expr> arguments) {
		return location.nodeWithChild(ARGUMENTS, arguments);
	}

	private static final int TYPE_ARGUMENTS = 0;
	private static final int EXPR = 1;
	private static final int ARGUMENTS = 2;

	private static final int CONSTRUCTOR_KIND = 0;

	public final static LexicalShape shape = composite(
			nonNullChild(EXPR, composite(child(EXPR), token(LToken.Dot))),
			children(TYPE_ARGUMENTS,
					token(LToken.Less),
					token(LToken.Comma),
					token(LToken.Greater)
			),
			token(new LSToken.Provider() {
				public LToken tokenFor(STree tree) {
					return ((ConstructorKind) tree.state.data(CONSTRUCTOR_KIND)).token;
				}
			}),
			token(LToken.ParenthesisLeft),
			children(ARGUMENTS, token(LToken.Comma)),
			token(LToken.ParenthesisRight),
			token(LToken.SemiColon)
	);

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
