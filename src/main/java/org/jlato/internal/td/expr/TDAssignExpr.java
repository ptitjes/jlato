package org.jlato.internal.td.expr;

import org.jlato.internal.bu.expr.SAssignExpr;
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.expr.AssignExpr;
import org.jlato.tree.expr.AssignOp;
import org.jlato.tree.expr.Expr;
import org.jlato.util.Mutation;

public class TDAssignExpr extends TreeBase<SAssignExpr, Expr, AssignExpr> implements AssignExpr {

	public Kind kind() {
		return Kind.AssignExpr;
	}

	public TDAssignExpr(SLocation<SAssignExpr> location) {
		super(location);
	}

	public TDAssignExpr(Expr target, AssignOp op, Expr value) {
		super(new SLocation<SAssignExpr>(SAssignExpr.make(TreeBase.<SExpr>treeOf(target), op, TreeBase.<SExpr>treeOf(value))));
	}

	public Expr target() {
		return location.safeTraversal(SAssignExpr.TARGET);
	}

	public AssignExpr withTarget(Expr target) {
		return location.safeTraversalReplace(SAssignExpr.TARGET, target);
	}

	public AssignExpr withTarget(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SAssignExpr.TARGET, mutation);
	}

	public AssignOp op() {
		return location.safeProperty(SAssignExpr.OP);
	}

	public AssignExpr withOp(AssignOp op) {
		return location.safePropertyReplace(SAssignExpr.OP, op);
	}

	public AssignExpr withOp(Mutation<AssignOp> mutation) {
		return location.safePropertyMutate(SAssignExpr.OP, mutation);
	}

	public Expr value() {
		return location.safeTraversal(SAssignExpr.VALUE);
	}

	public AssignExpr withValue(Expr value) {
		return location.safeTraversalReplace(SAssignExpr.VALUE, value);
	}

	public AssignExpr withValue(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SAssignExpr.VALUE, mutation);
	}
}
