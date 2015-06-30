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

	public AssignExpr(Expr target, Expr value, AssignOp operator) {
		super(new SLocation(new SNode(kind, runOf(target, value, operator))));
	}

	public Expr target() {
		return location.nodeChild(TARGET);
	}

	public AssignExpr withTarget(Expr target) {
		return location.nodeWithChild(TARGET, target);
	}

	public Expr value() {
		return location.nodeChild(VALUE);
	}

	public AssignExpr withValue(Expr value) {
		return location.nodeWithChild(VALUE, value);
	}

	public AssignOp operator() {
		return location.nodeChild(OPERATOR);
	}

	public AssignExpr withOperator(AssignOp operator) {
		return location.nodeWithChild(OPERATOR, operator);
	}

	private static final int TARGET = 0;
	private static final int VALUE = 1;
	private static final int OPERATOR = 2;

	public static class AssignOp extends Tree {

		public final static Tree.Kind kind = new Tree.Kind() {
			public AssignOp instantiate(SLocation location) {
				return new AssignOp(location);
			}
		};

		public static final AssignOp assign = new AssignOp(LToken.assign);
		public static final AssignOp plus = new AssignOp(LToken.plus);
		public static final AssignOp minus = new AssignOp(LToken.minus);
		public static final AssignOp star = new AssignOp(LToken.star);
		public static final AssignOp slash = new AssignOp(LToken.slash);
		public static final AssignOp and = new AssignOp(LToken.and);
		public static final AssignOp or = new AssignOp(LToken.or);
		public static final AssignOp xor = new AssignOp(LToken.xor);
		public static final AssignOp rem = new AssignOp(LToken.rem);
		public static final AssignOp lShift = new AssignOp(LToken.lShift);
		public static final AssignOp rSignedShift = new AssignOp(LToken.rSignedShift);
		public static final AssignOp rUnsignedShift = new AssignOp(LToken.rUnsignedShift);

		private AssignOp(SLocation location) {
			super(location);
		}

		private AssignOp(LToken token) {super(new SLocation(new SLeaf(kind, token)));}

		public String toString() {
			return location.leafToken().toString();
		}

	}
}
