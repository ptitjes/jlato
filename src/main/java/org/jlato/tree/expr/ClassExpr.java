package org.jlato.tree.expr;

import org.jlato.tree.TreeCombinators;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

public interface ClassExpr extends Expr, TreeCombinators<ClassExpr> {

	Type type();

	ClassExpr withType(Type type);

	ClassExpr withType(Mutation<Type> mutation);
}
