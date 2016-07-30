/*
 * Copyright (C) 2015-2016 Didier Villevalois.
 *
 * This file is part of JLaTo.
 *
 * JLaTo is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * JLaTo is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JLaTo.  If not, see <http://www.gnu.org/licenses/>.
 */

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
