package org.jlato.internal.td.expr;

import org.jlato.internal.bu.SNodeOptionState;
import org.jlato.internal.bu.expr.SSuperExpr;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeOption;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.expr.SuperExpr;
import org.jlato.util.Mutation;

public class TDSuperExpr extends TreeBase<SSuperExpr, Expr, SuperExpr> implements SuperExpr {

	public Kind kind() {
		return Kind.SuperExpr;
	}

	public TDSuperExpr(SLocation<SSuperExpr> location) {
		super(location);
	}

	public TDSuperExpr(NodeOption<Expr> classExpr) {
		super(new SLocation<SSuperExpr>(SSuperExpr.make(TreeBase.<SNodeOptionState>treeOf(classExpr))));
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
