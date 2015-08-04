package org.jlato.internal.td.expr;

import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.bu.expr.SInstanceOfExpr;
import org.jlato.internal.bu.type.SType;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.expr.InstanceOfExpr;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

public class TDInstanceOfExpr extends TreeBase<SInstanceOfExpr, Expr, InstanceOfExpr> implements InstanceOfExpr {

	public Kind kind() {
		return Kind.InstanceOfExpr;
	}

	public TDInstanceOfExpr(SLocation<SInstanceOfExpr> location) {
		super(location);
	}

	public TDInstanceOfExpr(Expr expr, Type type) {
		super(new SLocation<SInstanceOfExpr>(SInstanceOfExpr.make(TreeBase.<SExpr>treeOf(expr), TreeBase.<SType>treeOf(type))));
	}

	public Expr expr() {
		return location.safeTraversal(SInstanceOfExpr.EXPR);
	}

	public InstanceOfExpr withExpr(Expr expr) {
		return location.safeTraversalReplace(SInstanceOfExpr.EXPR, expr);
	}

	public InstanceOfExpr withExpr(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SInstanceOfExpr.EXPR, mutation);
	}

	public Type type() {
		return location.safeTraversal(SInstanceOfExpr.TYPE);
	}

	public InstanceOfExpr withType(Type type) {
		return location.safeTraversalReplace(SInstanceOfExpr.TYPE, type);
	}

	public InstanceOfExpr withType(Mutation<Type> mutation) {
		return location.safeTraversalMutate(SInstanceOfExpr.TYPE, mutation);
	}
}
