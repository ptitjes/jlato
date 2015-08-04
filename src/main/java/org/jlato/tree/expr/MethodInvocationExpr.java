package org.jlato.tree.expr;

import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

public interface MethodInvocationExpr extends Expr, TreeCombinators<MethodInvocationExpr> {

	NodeOption<Expr> scope();

	MethodInvocationExpr withScope(NodeOption<Expr> scope);

	MethodInvocationExpr withScope(Mutation<NodeOption<Expr>> mutation);

	NodeList<Type> typeArgs();

	MethodInvocationExpr withTypeArgs(NodeList<Type> typeArgs);

	MethodInvocationExpr withTypeArgs(Mutation<NodeList<Type>> mutation);

	Name name();

	MethodInvocationExpr withName(Name name);

	MethodInvocationExpr withName(Mutation<Name> mutation);

	NodeList<Expr> args();

	MethodInvocationExpr withArgs(NodeList<Expr> args);

	MethodInvocationExpr withArgs(Mutation<NodeList<Expr>> mutation);
}
