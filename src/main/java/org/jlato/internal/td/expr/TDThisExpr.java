package org.jlato.internal.td.expr;

import org.jlato.internal.bu.SNodeOption;
import org.jlato.internal.bu.expr.SThisExpr;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeOption;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.expr.ThisExpr;
import org.jlato.util.Mutation;

public class TDThisExpr extends TDTree<SThisExpr, Expr, ThisExpr> implements ThisExpr {

	public Kind kind() {
		return Kind.ThisExpr;
	}

	public TDThisExpr(TDLocation<SThisExpr> location) {
		super(location);
	}

	public TDThisExpr(NodeOption<Expr> classExpr) {
		super(new TDLocation<SThisExpr>(SThisExpr.make(TDTree.<SNodeOption>treeOf(classExpr))));
	}

	public NodeOption<Expr> classExpr() {
		return location.safeTraversal(SThisExpr.CLASS_EXPR);
	}

	public ThisExpr withClassExpr(NodeOption<Expr> classExpr) {
		return location.safeTraversalReplace(SThisExpr.CLASS_EXPR, classExpr);
	}

	public ThisExpr withClassExpr(Mutation<NodeOption<Expr>> mutation) {
		return location.safeTraversalMutate(SThisExpr.CLASS_EXPR, mutation);
	}
}
