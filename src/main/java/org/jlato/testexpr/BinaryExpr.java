package org.jlato.testexpr;

import org.jlato.internal.bu.SNode;
import org.jlato.tree.Expr;
import org.jlato.tree.Tree;

/**
 * @author Didier Villevalois
 */
public class BinaryExpr extends Expr {

	public final static Kind kind = new Kind() {
		public Tree instantiate(SLocation location) {
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

	public BinaryOp operator() {
		return location.nodeChild(OPERATOR);
	}

	public BinaryExpr withOperator(BinaryOp operator) {
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

	@Override
	public String toString() {
		return left() + " " + operator() + " " + right();
	}
}
