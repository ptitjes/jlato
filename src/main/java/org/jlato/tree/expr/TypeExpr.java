package org.jlato.tree.expr;

import org.jlato.tree.TreeCombinators;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

public interface TypeExpr extends Expr, TreeCombinators<TypeExpr> {

	Type type();

	TypeExpr withType(Type type);

	TypeExpr withType(Mutation<Type> mutation);
}
