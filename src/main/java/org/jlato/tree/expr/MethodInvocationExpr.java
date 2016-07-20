package org.jlato.tree.expr;

import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

/**
 * A method invocation expression.
 */
public interface MethodInvocationExpr extends Expr, TreeCombinators<MethodInvocationExpr> {

	/**
	 * Returns the scope of this method invocation expression.
	 *
	 * @return the scope of this method invocation expression.
	 */
	NodeOption<Expr> scope();

	/**
	 * Replaces the scope of this method invocation expression.
	 *
	 * @param scope the replacement for the scope of this method invocation expression.
	 * @return the resulting mutated method invocation expression.
	 */
	MethodInvocationExpr withScope(NodeOption<Expr> scope);

	/**
	 * Mutates the scope of this method invocation expression.
	 *
	 * @param mutation the mutation to apply to the scope of this method invocation expression.
	 * @return the resulting mutated method invocation expression.
	 */
	MethodInvocationExpr withScope(Mutation<NodeOption<Expr>> mutation);

	/**
	 * Replaces the scope of this method invocation expression.
	 *
	 * @param scope the replacement for the scope of this method invocation expression.
	 * @return the resulting mutated method invocation expression.
	 */
	MethodInvocationExpr withScope(Expr scope);

	/**
	 * Replaces the scope of this method invocation expression.
	 *
	 * @return the resulting mutated method invocation expression.
	 */
	MethodInvocationExpr withNoScope();

	/**
	 * Returns the type args of this method invocation expression.
	 *
	 * @return the type args of this method invocation expression.
	 */
	NodeList<Type> typeArgs();

	/**
	 * Replaces the type args of this method invocation expression.
	 *
	 * @param typeArgs the replacement for the type args of this method invocation expression.
	 * @return the resulting mutated method invocation expression.
	 */
	MethodInvocationExpr withTypeArgs(NodeList<Type> typeArgs);

	/**
	 * Mutates the type args of this method invocation expression.
	 *
	 * @param mutation the mutation to apply to the type args of this method invocation expression.
	 * @return the resulting mutated method invocation expression.
	 */
	MethodInvocationExpr withTypeArgs(Mutation<NodeList<Type>> mutation);

	/**
	 * Returns the name of this method invocation expression.
	 *
	 * @return the name of this method invocation expression.
	 */
	Name name();

	/**
	 * Replaces the name of this method invocation expression.
	 *
	 * @param name the replacement for the name of this method invocation expression.
	 * @return the resulting mutated method invocation expression.
	 */
	MethodInvocationExpr withName(Name name);

	/**
	 * Mutates the name of this method invocation expression.
	 *
	 * @param mutation the mutation to apply to the name of this method invocation expression.
	 * @return the resulting mutated method invocation expression.
	 */
	MethodInvocationExpr withName(Mutation<Name> mutation);

	/**
	 * Replaces the name of this method invocation expression.
	 *
	 * @param name the replacement for the name of this method invocation expression.
	 * @return the resulting mutated method invocation expression.
	 */
	MethodInvocationExpr withName(String name);

	/**
	 * Returns the args of this method invocation expression.
	 *
	 * @return the args of this method invocation expression.
	 */
	NodeList<Expr> args();

	/**
	 * Replaces the args of this method invocation expression.
	 *
	 * @param args the replacement for the args of this method invocation expression.
	 * @return the resulting mutated method invocation expression.
	 */
	MethodInvocationExpr withArgs(NodeList<Expr> args);

	/**
	 * Mutates the args of this method invocation expression.
	 *
	 * @param mutation the mutation to apply to the args of this method invocation expression.
	 * @return the resulting mutated method invocation expression.
	 */
	MethodInvocationExpr withArgs(Mutation<NodeList<Expr>> mutation);
}
