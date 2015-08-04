package org.jlato.tree.expr;

import org.jlato.tree.TreeCombinators;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

public interface InstanceOfExpr extends Expr, TreeCombinators<InstanceOfExpr> {

	Expr expr();

	InstanceOfExpr withExpr(Expr expr);

	InstanceOfExpr withExpr(Mutation<Expr> mutation);

	Type type();

	InstanceOfExpr withType(Type type);

	InstanceOfExpr withType(Mutation<Type> mutation);
}
