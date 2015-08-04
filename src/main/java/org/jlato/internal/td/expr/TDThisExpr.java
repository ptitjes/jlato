package org.jlato.internal.td.expr;

import org.jlato.internal.bu.SNodeOptionState;
import org.jlato.internal.bu.expr.SThisExpr;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeOption;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.expr.ThisExpr;
import org.jlato.util.Mutation;

public class TDThisExpr extends TreeBase<SThisExpr, Expr, ThisExpr> implements ThisExpr {

	public Kind kind() {
		return Kind.ThisExpr;
	}

	public TDThisExpr(SLocation<SThisExpr> location) {
		super(location);
	}

	public TDThisExpr(NodeOption<Expr> classExpr) {
		super(new SLocation<SThisExpr>(SThisExpr.make(TreeBase.<SNodeOptionState>treeOf(classExpr))));
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
