package org.jlato.tree.expr;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.Expr;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;

public class ThisExpr extends Expr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public ThisExpr instantiate(SLocation location) {
			return new ThisExpr(location);
		}

		public LexicalShape shape() {
			return null;
		}
	};

	private ThisExpr(SLocation location) {
		super(location);
	}

	public ThisExpr(Expr classExpr) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(classExpr)))));
	}

	public Expr classExpr() {
		return location.nodeChild(CLASS_EXPR);
	}

	public ThisExpr withClassExpr(Expr classExpr) {
		return location.nodeWithChild(CLASS_EXPR, classExpr);
	}

	private static final int CLASS_EXPR = 0;
}
