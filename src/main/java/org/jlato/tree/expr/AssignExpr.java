package org.jlato.tree.expr;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.Expr;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;

public class AssignExpr extends Expr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public AssignExpr instantiate(SLocation location) {
			return new AssignExpr(location);
		}

		public LexicalShape shape() {
			return null;
		}
	};

	private AssignExpr(SLocation location) {
		super(location);
	}

	public AssignExpr(Expr target, AssignOp operator, Expr value) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(target, value), dataOf(operator)))));
	}

	public Expr target() {
		return location.nodeChild(TARGET);
	}

	public AssignExpr withTarget(Expr target) {
		return location.nodeWithChild(TARGET, target);
	}

	public AssignOp op() {
		return location.nodeData(OPERATOR);
	}

	public AssignExpr withOp(AssignOp operator) {
		return location.nodeWithData(OPERATOR, operator);
	}

	public Expr value() {
		return location.nodeChild(VALUE);
	}

	public AssignExpr withValue(Expr value) {
		return location.nodeWithChild(VALUE, value);
	}

	private static final int TARGET = 0;
	private static final int VALUE = 1;

	private static final int OPERATOR = 0;

	public enum AssignOp {
		Normal(LToken.Assign),
		Plus(LToken.AssignPlus),
		Minus(LToken.AssignMinus),
		Times(LToken.AssignTimes),
		Divide(LToken.AssignDivide),
		And(LToken.AssignAnd),
		Or(LToken.AssignOr),
		XOr(LToken.AssignXOr),
		Remainder(LToken.AssignRemainder),
		LeftShift(LToken.AssignLShift),
		RightSignedShift(LToken.AssignRSignedShift),
		RightUnsignedShift(LToken.AssignRUnsignedShift),
		// Keep last comma
		;

		protected final LToken token;

		AssignOp(LToken token) {
			this.token = token;
		}
	}
}
