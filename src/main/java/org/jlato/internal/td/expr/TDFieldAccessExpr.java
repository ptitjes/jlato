package org.jlato.internal.td.expr;

import org.jlato.internal.bu.SNodeOptionState;
import org.jlato.internal.bu.expr.SFieldAccessExpr;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeOption;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.expr.FieldAccessExpr;
import org.jlato.tree.name.Name;
import org.jlato.util.Mutation;

public class TDFieldAccessExpr extends TreeBase<SFieldAccessExpr, Expr, FieldAccessExpr> implements FieldAccessExpr {

	public Kind kind() {
		return Kind.FieldAccessExpr;
	}

	public TDFieldAccessExpr(SLocation<SFieldAccessExpr> location) {
		super(location);
	}

	public TDFieldAccessExpr(NodeOption<Expr> scope, Name name) {
		super(new SLocation<SFieldAccessExpr>(SFieldAccessExpr.make(TreeBase.<SNodeOptionState>treeOf(scope), TreeBase.<SName>treeOf(name))));
	}

	public NodeOption<Expr> scope() {
		return location.safeTraversal(SFieldAccessExpr.SCOPE);
	}

	public FieldAccessExpr withScope(NodeOption<Expr> scope) {
		return location.safeTraversalReplace(SFieldAccessExpr.SCOPE, scope);
	}

	public FieldAccessExpr withScope(Mutation<NodeOption<Expr>> mutation) {
		return location.safeTraversalMutate(SFieldAccessExpr.SCOPE, mutation);
	}

	public Name name() {
		return location.safeTraversal(SFieldAccessExpr.NAME);
	}

	public FieldAccessExpr withName(Name name) {
		return location.safeTraversalReplace(SFieldAccessExpr.NAME, name);
	}

	public FieldAccessExpr withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(SFieldAccessExpr.NAME, mutation);
	}
}
