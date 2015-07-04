package org.jlato.tree.expr;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeData;
import org.jlato.tree.Expr;
import org.jlato.tree.SLocation;
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
		super(new SLocation(new SNode(kind, new SNodeData(treesOf(left, right), attributesOf(operator)))));
	}

	public Expr left() {
		return location.nodeChild(LEFT);
	}

	public BinaryExpr withLeft(Expr left) {
		return location.nodeWithChild(LEFT, left);
	}

	public BinaryOp op() {
		return location.nodeAttribute(OPERATOR);
	}

	public BinaryExpr withOp(BinaryOp operator) {
		return location.nodeWithAttribute(OPERATOR, operator);
	}

	public Expr right() {
		return location.nodeChild(RIGHT);
	}

	public BinaryExpr withRight(Expr right) {
		return location.nodeWithChild(RIGHT, right);
	}

	private static final int LEFT = 0;
	private static final int RIGHT = 1;

	private static final int OPERATOR = 0;

	public enum BinaryOp {
		Or(LToken.Or),
		And(LToken.And),
		BinOr(LToken.BinOr),
		BinAnd(LToken.BinAnd),
		XOr(LToken.XOr),
		Equal(LToken.Equal),
		NotEqual(LToken.NotEqual),
		Less(LToken.Less),
		Greater(LToken.Greater),
		LessOrEqual(LToken.LessOrEqual),
		GreaterOrEqual(LToken.GreaterOrEqual),
		LeftShift(LToken.LShift),
		RightSignedShift(LToken.RSignedShift),
		RightUnsignedShift(LToken.RUnsignedShift),
		Plus(LToken.Plus),
		Minus(LToken.Minus),
		Times(LToken.Times),
		Divide(LToken.Divide),
		Remainder(LToken.Remainder),
		// Keep last comma
		;

		protected final LToken token;

		BinaryOp(LToken token) {
			this.token = token;
		}

		public String toString() {
			return token.toString();
		}
	}
}
