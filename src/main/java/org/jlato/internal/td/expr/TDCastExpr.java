package org.jlato.internal.td.expr;

import org.jlato.internal.bu.expr.SCastExpr;
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.bu.type.SType;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.expr.CastExpr;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

public class TDCastExpr extends TreeBase<SCastExpr, Expr, CastExpr> implements CastExpr {

	public Kind kind() {
		return Kind.CastExpr;
	}

	public TDCastExpr(SLocation<SCastExpr> location) {
		super(location);
	}

	public TDCastExpr(Type type, Expr expr) {
		super(new SLocation<SCastExpr>(SCastExpr.make(TreeBase.<SType>treeOf(type), TreeBase.<SExpr>treeOf(expr))));
	}

	public Type type() {
		return location.safeTraversal(SCastExpr.TYPE);
	}

	public CastExpr withType(Type type) {
		return location.safeTraversalReplace(SCastExpr.TYPE, type);
	}

	public CastExpr withType(Mutation<Type> mutation) {
		return location.safeTraversalMutate(SCastExpr.TYPE, mutation);
	}

	public Expr expr() {
		return location.safeTraversal(SCastExpr.EXPR);
	}

	public CastExpr withExpr(Expr expr) {
		return location.safeTraversalReplace(SCastExpr.EXPR, expr);
	}

	public CastExpr withExpr(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SCastExpr.EXPR, mutation);
	}
}
