package org.jlato.tree.expr;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SLeaf;
import org.jlato.internal.bu.SNode;
import org.jlato.tree.Expr;
import org.jlato.tree.Tree;

public class BinaryExpr extends Expr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public BinaryExpr instantiate(SLocation location) {
			return new BinaryExpr(location);
		}
	};

	private BinaryExpr(SLocation location) {
		super(location);
	}

	public BinaryExpr(Expr left, Expr right, BinaryOp operator) {
		super(new SLocation(new SNode(kind, runOf(left, right, operator))));
	}

	public Expr left() {
		return location.nodeChild(LEFT);
	}

	public BinaryExpr withLeft(Expr left) {
		return location.nodeWithChild(LEFT, left);
	}

	public Expr right() {
		return location.nodeChild(RIGHT);
	}

	public BinaryExpr withRight(Expr right) {
		return location.nodeWithChild(RIGHT, right);
	}

	public BinaryOp operator() {
		return location.nodeChild(OPERATOR);
	}

	public BinaryExpr withOperator(BinaryOp operator) {
		return location.nodeWithChild(OPERATOR, operator);
	}

	private static final int LEFT = 0;
	private static final int RIGHT = 1;
	private static final int OPERATOR = 2;

	public static class BinaryOp extends Tree {

		public final static Tree.Kind kind = new Tree.Kind() {
			public BinaryOp instantiate(SLocation location) {
				return new BinaryOp(location);
			}
		};

		public static final BinaryOp or = new BinaryOp(LToken.or);
		public static final BinaryOp and = new BinaryOp(LToken.and);
		public static final BinaryOp binOr = new BinaryOp(LToken.binOr);
		public static final BinaryOp binAnd = new BinaryOp(LToken.binAnd);
		public static final BinaryOp xor = new BinaryOp(LToken.xor);
		public static final BinaryOp equals = new BinaryOp(LToken.equals);
		public static final BinaryOp notEquals = new BinaryOp(LToken.notEquals);
		public static final BinaryOp less = new BinaryOp(LToken.less);
		public static final BinaryOp greater = new BinaryOp(LToken.greater);
		public static final BinaryOp lessEquals = new BinaryOp(LToken.lessEquals);
		public static final BinaryOp greaterEquals = new BinaryOp(LToken.greaterEquals);
		public static final BinaryOp lShift = new BinaryOp(LToken.lShift);
		public static final BinaryOp rSignedShift = new BinaryOp(LToken.rSignedShift);
		public static final BinaryOp rUnsignedShift = new BinaryOp(LToken.rUnsignedShift);
		public static final BinaryOp plus = new BinaryOp(LToken.plus);
		public static final BinaryOp minus = new BinaryOp(LToken.minus);
		public static final BinaryOp times = new BinaryOp(LToken.times);
		public static final BinaryOp divide = new BinaryOp(LToken.divide);
		public static final BinaryOp remainder = new BinaryOp(LToken.remainder);

		private BinaryOp(SLocation location) {
			super(location);
		}

		private BinaryOp(LToken token) {super(new SLocation(new SLeaf(kind, token)));}

		public String toString() {
			return location.leafToken().toString();
		}

	}
}
