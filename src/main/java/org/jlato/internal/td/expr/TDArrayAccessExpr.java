package org.jlato.internal.td.expr;

import org.jlato.internal.bu.expr.SArrayAccessExpr;
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.expr.ArrayAccessExpr;
import org.jlato.tree.expr.Expr;
import org.jlato.util.Mutation;

public class TDArrayAccessExpr extends TDTree<SArrayAccessExpr, Expr, ArrayAccessExpr> implements ArrayAccessExpr {

	public Kind kind() {
		return Kind.ArrayAccessExpr;
	}

	public TDArrayAccessExpr(SLocation<SArrayAccessExpr> location) {
		super(location);
	}

	public TDArrayAccessExpr(Expr name, Expr index) {
		super(new SLocation<SArrayAccessExpr>(SArrayAccessExpr.make(TDTree.<SExpr>treeOf(name), TDTree.<SExpr>treeOf(index))));
	}

	public Expr name() {
		return location.safeTraversal(SArrayAccessExpr.NAME);
	}

	public ArrayAccessExpr withName(Expr name) {
		return location.safeTraversalReplace(SArrayAccessExpr.NAME, name);
	}

	public ArrayAccessExpr withName(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SArrayAccessExpr.NAME, mutation);
	}

	public Expr index() {
		return location.safeTraversal(SArrayAccessExpr.INDEX);
	}

	public ArrayAccessExpr withIndex(Expr index) {
		return location.safeTraversalReplace(SArrayAccessExpr.INDEX, index);
	}

	public ArrayAccessExpr withIndex(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SArrayAccessExpr.INDEX, mutation);
	}
}
