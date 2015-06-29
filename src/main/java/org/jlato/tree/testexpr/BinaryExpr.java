package org.jlato.tree.testexpr;

import org.jlato.internal.bu.SContext;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.STree;
import org.jlato.tree.Expr;

/**
 * @author Didier Villevalois
 */
public class BinaryExpr extends Expr<BinaryExpr> {

	private BinaryExpr(SContext context, STree<BinaryExpr> content) {
		super(context, content);
	}

	public BinaryExpr(Expr left, BinaryOp operator, Expr right) {
		super(null, new SNode<BinaryExpr>(type, null, treesOf(left, operator, right), null));
	}

	public Expr left() {
		return child(LEFT);
	}

	public BinaryExpr withLeft(Expr left) {
		return with(LEFT, left);
	}

	public BinaryOp operator() {
		return child(OPERATOR);
	}

	public BinaryExpr withOperator(BinaryOp operator) {
		return with(OPERATOR, operator);
	}

	public Expr right() {
		return child(RIGHT);
	}

	public BinaryExpr withRight(Expr right) {
		return with(RIGHT, right);
	}

	private static final int LEFT = 0;
	private static final int OPERATOR = 1;
	private static final int RIGHT = 2;

	public final static Expr.Type<BinaryExpr> type = new Expr.Type<BinaryExpr>() {
		protected BinaryExpr instantiateNode(SContext context, STree<BinaryExpr> content) {
			return new BinaryExpr(context, content);
		}
	};
}
