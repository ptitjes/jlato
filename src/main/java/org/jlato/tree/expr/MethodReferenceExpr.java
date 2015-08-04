package org.jlato.tree.expr;

import org.jlato.tree.NodeList;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

public interface MethodReferenceExpr extends Expr, TreeCombinators<MethodReferenceExpr> {

	Expr scope();

	MethodReferenceExpr withScope(Expr scope);

	MethodReferenceExpr withScope(Mutation<Expr> mutation);

	NodeList<Type> typeArgs();

	MethodReferenceExpr withTypeArgs(NodeList<Type> typeArgs);

	MethodReferenceExpr withTypeArgs(Mutation<NodeList<Type>> mutation);

	Name name();

	MethodReferenceExpr withName(Name name);

	MethodReferenceExpr withName(Mutation<Name> mutation);
}
