package org.jlato.tree.expr;

import org.jlato.internal.bu.SNode;
import org.jlato.tree.Expr;
import org.jlato.tree.Tree;

public class SuperExpr extends Expr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public SuperExpr instantiate(SLocation location) {
			return new SuperExpr(location);
		}
	};

	private SuperExpr(SLocation location) {
		super(location);
	}

	public SuperExpr(Expr classExpr) {
		super(new SLocation(new SNode(kind, runOf(classExpr))));
	}

	public Expr classExpr() {
		return location.nodeChild(CLASS_EXPR);
	}

	public SuperExpr withClassExpr(Expr classExpr) {
		return location.nodeWithChild(CLASS_EXPR, classExpr);
	}

	private static final int CLASS_EXPR = 0;
}
