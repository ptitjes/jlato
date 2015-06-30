package org.jlato.tree.expr;

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

	public UnaryExpr(Expr expr, UnaryOp operator) {
		super(new SLocation(new SNode(kind, runOf(expr, operator))));
	}

	public Expr expr() {
		return location.nodeChild(EXPR);
	}

	public UnaryExpr withExpr(Expr expr) {
		return location.nodeWithChild(EXPR, expr);
	}

	public UnaryOp operator() {
		return location.nodeChild(OPERATOR);
	}

	public UnaryExpr withOperator(UnaryOp operator) {
		return location.nodeWithChild(OPERATOR, operator);
	}

	private static final int EXPR = 0;
	private static final int OPERATOR = 1;

	public static class UnaryOp extends Tree {

		public final static Tree.Kind kind = new Tree.Kind() {
			public UnaryOp instantiate(SLocation location) {
				return new UnaryOp(location);
			}
		};

		public static final UnaryOp positive = new UnaryOp(LToken.positive);
		public static final UnaryOp negative = new UnaryOp(LToken.negative);
		public static final UnaryOp preIncrement = new UnaryOp(LToken.preIncrement);
		public static final UnaryOp preDecrement = new UnaryOp(LToken.preDecrement);
		public static final UnaryOp not = new UnaryOp(LToken.not);
		public static final UnaryOp inverse = new UnaryOp(LToken.inverse);
		public static final UnaryOp posIncrement = new UnaryOp(LToken.posIncrement);
		public static final UnaryOp posDecrement = new UnaryOp(LToken.posDecrement);

		private UnaryOp(SLocation location) {
			super(location);
		}

		private UnaryOp(LToken token) {super(new SLocation(new SLeaf(kind, token)));}

		public String toString() {
			return location.leafToken().toString();
		}

	}
}
