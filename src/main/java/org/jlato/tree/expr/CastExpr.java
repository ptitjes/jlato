package org.jlato.tree.expr;

import org.jlato.tree.TreeCombinators;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

public interface CastExpr extends Expr, TreeCombinators<CastExpr> {

	Type type();

	CastExpr withType(Type type);

	CastExpr withType(Mutation<Type> mutation);

	Expr expr();

	CastExpr withExpr(Expr expr);

	CastExpr withExpr(Mutation<Expr> mutation);
}
