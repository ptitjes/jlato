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

	public BinaryExpr(Expr left, BinaryOp operator, Expr right) {
		super(new SLocation(new SNode(kind, runOf(left, operator, right))));
	}

	public Expr left() {
		return location.nodeChild(LEFT);
	}

	public BinaryExpr withLeft(Expr left) {
		return location.nodeWithChild(LEFT, left);
	}

	public BinaryOp op() {
		return location.nodeChild(OPERATOR);
	}

	public BinaryExpr withOp(BinaryOp operator) {
		return location.nodeWithChild(OPERATOR, operator);
	}

	public Expr right() {
		return location.nodeChild(RIGHT);
	}

	public BinaryExpr withRight(Expr right) {
		return location.nodeWithChild(RIGHT, right);
	}

	private static final int LEFT = 0;
	private static final int OPERATOR = 1;
	private static final int RIGHT = 2;

	public static class BinaryOp extends Tree {

		public final static Tree.Kind kind = new Tree.Kind() {
			public BinaryOp instantiate(SLocation location) {
				return new BinaryOp(location);
			}
		};

		public static final BinaryOp Or = new BinaryOp(LToken.Or);
		public static final BinaryOp And = new BinaryOp(LToken.And);
		public static final BinaryOp BinOr = new BinaryOp(LToken.BinOr);
		public static final BinaryOp BinAnd = new BinaryOp(LToken.BinAnd);
		public static final BinaryOp XOr = new BinaryOp(LToken.XOr);
		public static final BinaryOp Equal = new BinaryOp(LToken.Equal);
		public static final BinaryOp NotEqual = new BinaryOp(LToken.NotEqual);
		public static final BinaryOp Less = new BinaryOp(LToken.Less);
		public static final BinaryOp Greater = new BinaryOp(LToken.Greater);
		public static final BinaryOp LessOrEqual = new BinaryOp(LToken.LessOrEqual);
		public static final BinaryOp GreaterOrEqual = new BinaryOp(LToken.GreaterOrEqual);
		public static final BinaryOp LShift = new BinaryOp(LToken.LShift);
		public static final BinaryOp RSignedShift = new BinaryOp(LToken.RSignedShift);
		public static final BinaryOp RUnsignedShift = new BinaryOp(LToken.RUnsignedShift);
		public static final BinaryOp Plus = new BinaryOp(LToken.Plus);
		public static final BinaryOp Minus = new BinaryOp(LToken.Minus);
		public static final BinaryOp Times = new BinaryOp(LToken.Times);
		public static final BinaryOp Divide = new BinaryOp(LToken.Divide);
		public static final BinaryOp Remainder = new BinaryOp(LToken.Remainder);

		private BinaryOp(SLocation location) {
			super(location);
		}

		private BinaryOp(LToken token) {super(new SLocation(new SLeaf(kind, token)));}

		public String toString() {
			return location.leafToken().toString();
		}

	}
}
