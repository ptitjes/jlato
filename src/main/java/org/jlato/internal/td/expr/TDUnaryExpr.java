package org.jlato.internal.td.expr;

import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.bu.expr.SUnaryExpr;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.expr.UnaryExpr;
import org.jlato.tree.expr.UnaryOp;
import org.jlato.util.Mutation;

public class TDUnaryExpr extends TreeBase<SUnaryExpr, Expr, UnaryExpr> implements UnaryExpr {

	public Kind kind() {
		return Kind.UnaryExpr;
	}

	public TDUnaryExpr(SLocation<SUnaryExpr> location) {
		super(location);
	}

	public TDUnaryExpr(UnaryOp op, Expr expr) {
		super(new SLocation<SUnaryExpr>(SUnaryExpr.make(op, TreeBase.<SExpr>treeOf(expr))));
	}

	public UnaryOp op() {
		return location.safeProperty(SUnaryExpr.OP);
	}

	public UnaryExpr withOp(UnaryOp op) {
		return location.safePropertyReplace(SUnaryExpr.OP, op);
	}

	public UnaryExpr withOp(Mutation<UnaryOp> mutation) {
		return location.safePropertyMutate(SUnaryExpr.OP, mutation);
	}

	public Expr expr() {
		return location.safeTraversal(SUnaryExpr.EXPR);
	}

	public UnaryExpr withExpr(Expr expr) {
		return location.safeTraversalReplace(SUnaryExpr.EXPR, expr);
	}

	public UnaryExpr withExpr(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SUnaryExpr.EXPR, mutation);
	}
}
