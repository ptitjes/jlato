package org.jlato.tree.expr;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.*;
import org.jlato.tree.name.Name;

import static org.jlato.internal.shapes.LexicalShape.Factory.*;

public class CallExpr extends Expr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public CallExpr instantiate(SLocation location) {
			return new CallExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private CallExpr(SLocation location) {
		super(location);
	}

	public CallExpr(Expr scope, NodeList<Type> typeArgs, Name name, NodeList<Expr> args) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(scope, typeArgs, name, args)))));
	}

	public Expr scope() {
		return location.nodeChild(SCOPE);
	}

	public CallExpr withScope(Expr scope) {
		return location.nodeWithChild(SCOPE, scope);
	}

	public NodeList<Type> typeArguments() {
		return location.nodeChild(TYPE_ARGUMENTS);
	}

	public CallExpr withTypeArguments(NodeList<Type> typeArguments) {
		return location.nodeWithChild(TYPE_ARGUMENTS, typeArguments);
	}

	public Name name() {
		return location.nodeChild(NAME);
	}

	public CallExpr withName(Name name) {
		return location.nodeWithChild(NAME, name);
	}

	public NodeList<Expr> arguments() {
		return location.nodeChild(ARGUMENTS);
	}

	public CallExpr withArguments(NodeList<Expr> arguments) {
		return location.nodeWithChild(ARGUMENTS, arguments);
	}

	private static final int SCOPE = 0;
	private static final int TYPE_ARGUMENTS = 1;
	private static final int NAME = 2;
	private static final int ARGUMENTS = 3;

	public final static LexicalShape shape = composite(
			nonNullChild(SCOPE, composite(child(SCOPE), token(LToken.Dot))),
			children(TYPE_ARGUMENTS, token(LToken.Less), token(LToken.Comma), token(LToken.Greater)),
			child(NAME),
			token(LToken.ParenthesisLeft),
			children(ARGUMENTS, token(LToken.Comma)),
			token(LToken.ParenthesisRight)
	);
}
