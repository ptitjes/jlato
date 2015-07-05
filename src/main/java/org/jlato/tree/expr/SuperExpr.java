package org.jlato.tree.expr;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.Expr;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;

import static org.jlato.internal.shapes.LexicalShape.Factory.*;

public class SuperExpr extends Expr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public SuperExpr instantiate(SLocation location) {
			return new SuperExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private SuperExpr(SLocation location) {
		super(location);
	}

	public SuperExpr(Expr classExpr) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(classExpr)))));
	}

	public Expr classExpr() {
		return location.nodeChild(CLASS_EXPR);
	}

	public SuperExpr withClassExpr(Expr classExpr) {
		return location.nodeWithChild(CLASS_EXPR, classExpr);
	}

	private static final int CLASS_EXPR = 0;

	public final static LexicalShape shape = composite(
			nonNullChild(CLASS_EXPR, composite(child(CLASS_EXPR), token(LToken.Dot))),
			token(LToken.Super)
	);
}
