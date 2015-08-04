package org.jlato.internal.td.expr;

import org.jlato.internal.bu.expr.STypeExpr;
import org.jlato.internal.bu.type.SType;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.expr.TypeExpr;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

public class TDTypeExpr extends TreeBase<STypeExpr, Expr, TypeExpr> implements TypeExpr {

	public Kind kind() {
		return Kind.TypeExpr;
	}

	public TDTypeExpr(SLocation<STypeExpr> location) {
		super(location);
	}

	public TDTypeExpr(Type type) {
		super(new SLocation<STypeExpr>(STypeExpr.make(TreeBase.<SType>treeOf(type))));
	}

	public Type type() {
		return location.safeTraversal(STypeExpr.TYPE);
	}

	public TypeExpr withType(Type type) {
		return location.safeTraversalReplace(STypeExpr.TYPE, type);
	}

	public TypeExpr withType(Mutation<Type> mutation) {
		return location.safeTraversalMutate(STypeExpr.TYPE, mutation);
	}
}
