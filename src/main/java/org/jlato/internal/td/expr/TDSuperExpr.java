package org.jlato.internal.td.expr;

import org.jlato.internal.bu.SNodeOption;
import org.jlato.internal.bu.expr.SSuperExpr;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeOption;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.expr.SuperExpr;
import org.jlato.util.Mutation;

public class TDSuperExpr extends TDTree<SSuperExpr, Expr, SuperExpr> implements SuperExpr {

	public Kind kind() {
		return Kind.SuperExpr;
	}

	public TDSuperExpr(TDLocation<SSuperExpr> location) {
		super(location);
	}

	public TDSuperExpr(NodeOption<Expr> classExpr) {
		super(new TDLocation<SSuperExpr>(SSuperExpr.make(TDTree.<SNodeOption>treeOf(classExpr))));
	}

	public NodeOption<Expr> classExpr() {
		return location.safeTraversal(SSuperExpr.CLASS_EXPR);
	}

	public SuperExpr withClassExpr(NodeOption<Expr> classExpr) {
		return location.safeTraversalReplace(SSuperExpr.CLASS_EXPR, classExpr);
	}

	public SuperExpr withClassExpr(Mutation<NodeOption<Expr>> mutation) {
		return location.safeTraversalMutate(SSuperExpr.CLASS_EXPR, mutation);
	}
}
