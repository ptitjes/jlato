package org.jlato.internal.td.expr;

import org.jlato.internal.bu.expr.SClassExpr;
import org.jlato.internal.bu.type.SType;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.expr.ClassExpr;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

public class TDClassExpr extends TDTree<SClassExpr, Expr, ClassExpr> implements ClassExpr {

	public Kind kind() {
		return Kind.ClassExpr;
	}

	public TDClassExpr(SLocation<SClassExpr> location) {
		super(location);
	}

	public TDClassExpr(Type type) {
		super(new SLocation<SClassExpr>(SClassExpr.make(TDTree.<SType>treeOf(type))));
	}

	public Type type() {
		return location.safeTraversal(SClassExpr.TYPE);
	}

	public ClassExpr withType(Type type) {
		return location.safeTraversalReplace(SClassExpr.TYPE, type);
	}

	public ClassExpr withType(Mutation<Type> mutation) {
		return location.safeTraversalMutate(SClassExpr.TYPE, mutation);
	}
}
