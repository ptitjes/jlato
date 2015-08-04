package org.jlato.internal.td.expr;

import org.jlato.internal.bu.expr.SBinaryExpr;
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.expr.BinaryExpr;
import org.jlato.tree.expr.BinaryOp;
import org.jlato.tree.expr.Expr;
import org.jlato.util.Mutation;

public class TDBinaryExpr extends TreeBase<SBinaryExpr, Expr, BinaryExpr> implements BinaryExpr {

	public Kind kind() {
		return Kind.BinaryExpr;
	}

	public TDBinaryExpr(SLocation<SBinaryExpr> location) {
		super(location);
	}

	public TDBinaryExpr(Expr left, BinaryOp op, Expr right) {
		super(new SLocation<SBinaryExpr>(SBinaryExpr.make(TreeBase.<SExpr>treeOf(left), op, TreeBase.<SExpr>treeOf(right))));
	}

	public Expr left() {
		return location.safeTraversal(SBinaryExpr.LEFT);
	}

	public BinaryExpr withLeft(Expr left) {
		return location.safeTraversalReplace(SBinaryExpr.LEFT, left);
	}

	public BinaryExpr withLeft(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SBinaryExpr.LEFT, mutation);
	}

	public BinaryOp op() {
		return location.safeProperty(SBinaryExpr.OP);
	}

	public BinaryExpr withOp(BinaryOp op) {
		return location.safePropertyReplace(SBinaryExpr.OP, op);
	}

	public BinaryExpr withOp(Mutation<BinaryOp> mutation) {
		return location.safePropertyMutate(SBinaryExpr.OP, mutation);
	}

	public Expr right() {
		return location.safeTraversal(SBinaryExpr.RIGHT);
	}

	public BinaryExpr withRight(Expr right) {
		return location.safeTraversalReplace(SBinaryExpr.RIGHT, right);
	}

	public BinaryExpr withRight(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SBinaryExpr.RIGHT, mutation);
	}
}
