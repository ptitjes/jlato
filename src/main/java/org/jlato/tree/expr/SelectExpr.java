package org.jlato.tree.expr;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.*;
import org.jlato.tree.name.Name;

import static org.jlato.internal.shapes.LexicalShape.Factory.*;

public class SelectExpr extends Expr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public SelectExpr instantiate(SLocation location) {
			return new SelectExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private SelectExpr(SLocation location) {
		super(location);
	}

	public SelectExpr(Expr scope, NodeList<Type> typeArgs, Name field) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(scope, typeArgs, field)))));
	}

	public Expr scope() {
		return location.nodeChild(SCOPE);
	}

	public SelectExpr withScope(Expr scope) {
		return location.nodeWithChild(SCOPE, scope);
	}

	public NodeList<Type> typeArgs() {
		return location.nodeChild(TYPE_ARGS);
	}

	public SelectExpr withTypeArgs(NodeList<Type> typeArgs) {
		return location.nodeWithChild(TYPE_ARGS, typeArgs);
	}

	public Name field() {
		return location.nodeChild(FIELD);
	}

	public SelectExpr withField(Name field) {
		return location.nodeWithChild(FIELD, field);
	}

	private static final int SCOPE = 0;
	private static final int TYPE_ARGS = 1;
	private static final int FIELD = 2;

	public final static LexicalShape shape = composite(
			nonNullChild(SCOPE, composite(child(SCOPE), token(LToken.Dot))),
			child(FIELD)
	);
}
