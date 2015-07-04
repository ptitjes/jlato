package org.jlato.tree.expr;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeData;
import org.jlato.tree.Expr;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;

public class ConditionalExpr extends Expr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public ConditionalExpr instantiate(SLocation location) {
			return new ConditionalExpr(location);
		}
	};

	private ConditionalExpr(SLocation location) {
		super(location);
	}

	public ConditionalExpr(Expr condition, Expr thenExpr, Expr elseExpr) {
		super(new SLocation(new SNode(kind, new SNodeData(treesOf(condition, thenExpr, elseExpr)))));
	}

	public Expr condition() {
		return location.nodeChild(CONDITION);
	}

	public ConditionalExpr withCondition(Expr condition) {
		return location.nodeWithChild(CONDITION, condition);
	}

	public Expr thenExpr() {
		return location.nodeChild(THEN_EXPR);
	}

	public ConditionalExpr withThenExpr(Expr thenExpr) {
		return location.nodeWithChild(THEN_EXPR, thenExpr);
	}

	public Expr elseExpr() {
		return location.nodeChild(ELSE_EXPR);
	}

	public ConditionalExpr withElseExpr(Expr elseExpr) {
		return location.nodeWithChild(ELSE_EXPR, elseExpr);
	}

	private static final int CONDITION = 0;
	private static final int THEN_EXPR = 1;
	private static final int ELSE_EXPR = 2;
}
