package org.jlato.internal.td.expr;

import org.jlato.internal.bu.expr.SConditionalExpr;
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.expr.ConditionalExpr;
import org.jlato.tree.expr.Expr;
import org.jlato.util.Mutation;

public class TDConditionalExpr extends TDTree<SConditionalExpr, Expr, ConditionalExpr> implements ConditionalExpr {

	public Kind kind() {
		return Kind.ConditionalExpr;
	}

	public TDConditionalExpr(SLocation<SConditionalExpr> location) {
		super(location);
	}

	public TDConditionalExpr(Expr condition, Expr thenExpr, Expr elseExpr) {
		super(new SLocation<SConditionalExpr>(SConditionalExpr.make(TDTree.<SExpr>treeOf(condition), TDTree.<SExpr>treeOf(thenExpr), TDTree.<SExpr>treeOf(elseExpr))));
	}

	public Expr condition() {
		return location.safeTraversal(SConditionalExpr.CONDITION);
	}

	public ConditionalExpr withCondition(Expr condition) {
		return location.safeTraversalReplace(SConditionalExpr.CONDITION, condition);
	}

	public ConditionalExpr withCondition(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SConditionalExpr.CONDITION, mutation);
	}

	public Expr thenExpr() {
		return location.safeTraversal(SConditionalExpr.THEN_EXPR);
	}

	public ConditionalExpr withThenExpr(Expr thenExpr) {
		return location.safeTraversalReplace(SConditionalExpr.THEN_EXPR, thenExpr);
	}

	public ConditionalExpr withThenExpr(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SConditionalExpr.THEN_EXPR, mutation);
	}

	public Expr elseExpr() {
		return location.safeTraversal(SConditionalExpr.ELSE_EXPR);
	}

	public ConditionalExpr withElseExpr(Expr elseExpr) {
		return location.safeTraversalReplace(SConditionalExpr.ELSE_EXPR, elseExpr);
	}

	public ConditionalExpr withElseExpr(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SConditionalExpr.ELSE_EXPR, mutation);
	}
}
