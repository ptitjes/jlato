package org.jlato.tree.expr;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SLeaf;
import org.jlato.internal.bu.SNode;
import org.jlato.tree.Expr;
import org.jlato.tree.Tree;

public class AssignExpr extends Expr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public AssignExpr instantiate(SLocation location) {
			return new AssignExpr(location);
		}
	};

	private AssignExpr(SLocation location) {
		super(location);
	}

	public AssignExpr(Expr target, AssignOp operator, Expr value) {
		super(new SLocation(new SNode(kind, runOf(target, operator, value))));
	}

	public Expr target() {
		return location.nodeChild(TARGET);
	}

	public AssignExpr withTarget(Expr target) {
		return location.nodeWithChild(TARGET, target);
	}

	public AssignOp op() {
		return location.nodeChild(OPERATOR);
	}

	public AssignExpr withOp(AssignOp operator) {
		return location.nodeWithChild(OPERATOR, operator);
	}

	public Expr value() {
		return location.nodeChild(VALUE);
	}

	public AssignExpr withValue(Expr value) {
		return location.nodeWithChild(VALUE, value);
	}

	private static final int TARGET = 0;
	private static final int OPERATOR = 1;
	private static final int VALUE = 2;

	public static class AssignOp extends Tree {

		public final static Tree.Kind kind = new Tree.Kind() {
			public AssignOp instantiate(SLocation location) {
				return new AssignOp(location);
			}
		};

		public static final AssignOp Normal = new AssignOp(LToken.Assign);
		public static final AssignOp Plus = new AssignOp(LToken.AssignPlus);
		public static final AssignOp Minus = new AssignOp(LToken.AssignMinus);
		public static final AssignOp Times = new AssignOp(LToken.AssignTimes);
		public static final AssignOp Divide = new AssignOp(LToken.AssignDivide);
		public static final AssignOp And = new AssignOp(LToken.AssignAnd);
		public static final AssignOp Or = new AssignOp(LToken.AssignOr);
		public static final AssignOp XOr = new AssignOp(LToken.AssignXOr);
		public static final AssignOp Remainder = new AssignOp(LToken.AssignRemainder);
		public static final AssignOp LShift = new AssignOp(LToken.AssignLShift);
		public static final AssignOp RSignedShift = new AssignOp(LToken.AssignRSignedShift);
		public static final AssignOp RUnsignedShift = new AssignOp(LToken.AssignRUnsignedShift);

		private AssignOp(SLocation location) {
			super(location);
		}

		private AssignOp(LToken token) {
			super(new SLocation(new SLeaf(kind, token)));
		}

		public String toString() {
			return location.leafToken().toString();
		}

	}
}
