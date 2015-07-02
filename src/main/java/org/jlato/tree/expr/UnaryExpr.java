package org.jlato.tree.expr;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SLeaf;
import org.jlato.internal.bu.SNode;
import org.jlato.tree.Expr;
import org.jlato.tree.Tree;

public class UnaryExpr extends Expr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public UnaryExpr instantiate(SLocation location) {
			return new UnaryExpr(location);
		}
	};

	private UnaryExpr(SLocation location) {
		super(location);
	}

	public UnaryExpr(UnaryOp operator, Expr expr) {
		super(new SLocation(new SNode(kind, runOf(operator, expr))));
	}

	public UnaryOp op() {
		return location.nodeChild(OPERATOR);
	}

	public UnaryExpr withOp(UnaryOp operator) {
		return location.nodeWithChild(OPERATOR, operator);
	}

	public Expr expr() {
		return location.nodeChild(EXPR);
	}

	public UnaryExpr withExpr(Expr expr) {
		return location.nodeWithChild(EXPR, expr);
	}

	private static final int OPERATOR = 0;
	private static final int EXPR = 1;

	public static class UnaryOp extends Tree {

		public final static Tree.Kind kind = new Tree.Kind() {
			public UnaryOp instantiate(SLocation location) {
				return new UnaryOp(location);
			}
		};

		public static final UnaryOp Positive = new UnaryOp(LToken.Plus);
		public static final UnaryOp Negative = new UnaryOp(LToken.Minus);
		public static final UnaryOp PreIncrement = new UnaryOp(LToken.Increment);
		public static final UnaryOp PreDecrement = new UnaryOp(LToken.Decrement);
		public static final UnaryOp Not = new UnaryOp(LToken.Not);
		public static final UnaryOp Inverse = new UnaryOp(LToken.Inverse);
		public static final UnaryOp PostIncrement = new UnaryOp(LToken.Increment);
		public static final UnaryOp PostDecrement = new UnaryOp(LToken.Decrement);

		private UnaryOp(SLocation location) {
			super(location);
		}

		private UnaryOp(LToken token) {
			super(new SLocation(new SLeaf(kind, token)));
		}

		public String toString() {
			return location.leafToken().toString();
		}

	}
}
