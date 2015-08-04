package org.jlato.internal.td.expr;

import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.bu.expr.SParenthesizedExpr;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.expr.ParenthesizedExpr;
import org.jlato.util.Mutation;

public class TDParenthesizedExpr extends TDTree<SParenthesizedExpr, Expr, ParenthesizedExpr> implements ParenthesizedExpr {

	public Kind kind() {
		return Kind.ParenthesizedExpr;
	}

	public TDParenthesizedExpr(TDLocation<SParenthesizedExpr> location) {
		super(location);
	}

	public TDParenthesizedExpr(Expr inner) {
		super(new TDLocation<SParenthesizedExpr>(SParenthesizedExpr.make(TDTree.<SExpr>treeOf(inner))));
	}

	public Expr inner() {
		return location.safeTraversal(SParenthesizedExpr.INNER);
	}

	public ParenthesizedExpr withInner(Expr inner) {
		return location.safeTraversalReplace(SParenthesizedExpr.INNER, inner);
	}

	public ParenthesizedExpr withInner(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SParenthesizedExpr.INNER, mutation);
	}
}
