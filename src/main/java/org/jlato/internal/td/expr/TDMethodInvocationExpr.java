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

package org.jlato.internal.td.expr;

import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.bu.expr.SMethodInvocationExpr;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Trees;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.expr.MethodInvocationExpr;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

/**
 * A method invocation expression.
 */
public class TDMethodInvocationExpr extends TDTree<SMethodInvocationExpr, Expr, MethodInvocationExpr> implements MethodInvocationExpr {

	/**
	 * Returns the kind of this method invocation expression.
	 *
	 * @return the kind of this method invocation expression.
	 */
	public Kind kind() {
		return Kind.MethodInvocationExpr;
	}

	/**
	 * Creates a method invocation expression for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDMethodInvocationExpr(TDLocation<SMethodInvocationExpr> location) {
		super(location);
	}

	/**
	 * Creates a method invocation expression with the specified child trees.
	 *
	 * @param scope    the scope child tree.
	 * @param typeArgs the type args child tree.
	 * @param name     the name child tree.
	 * @param args     the args child tree.
	 */
	public TDMethodInvocationExpr(NodeOption<Expr> scope, NodeList<Type> typeArgs, Name name, NodeList<Expr> args) {
		super(new TDLocation<SMethodInvocationExpr>(SMethodInvocationExpr.make(TDTree.<SNodeOption>treeOf(scope), TDTree.<SNodeList>treeOf(typeArgs), TDTree.<SName>treeOf(name), TDTree.<SNodeList>treeOf(args))));
	}

	/**
	 * Returns the scope of this method invocation expression.
	 *
	 * @return the scope of this method invocation expression.
	 */
	public NodeOption<Expr> scope() {
		return location.safeTraversal(SMethodInvocationExpr.SCOPE);
	}

	/**
	 * Replaces the scope of this method invocation expression.
	 *
	 * @param scope the replacement for the scope of this method invocation expression.
	 * @return the resulting mutated method invocation expression.
	 */
	public MethodInvocationExpr withScope(NodeOption<Expr> scope) {
		return location.safeTraversalReplace(SMethodInvocationExpr.SCOPE, scope);
	}

	/**
	 * Mutates the scope of this method invocation expression.
	 *
	 * @param mutation the mutation to apply to the scope of this method invocation expression.
	 * @return the resulting mutated method invocation expression.
	 */
	public MethodInvocationExpr withScope(Mutation<NodeOption<Expr>> mutation) {
		return location.safeTraversalMutate(SMethodInvocationExpr.SCOPE, mutation);
	}

	/**
	 * Replaces the scope of this method invocation expression.
	 *
	 * @param scope the replacement for the scope of this method invocation expression.
	 * @return the resulting mutated method invocation expression.
	 */
	public MethodInvocationExpr withScope(Expr scope) {
		return location.safeTraversalReplace(SMethodInvocationExpr.SCOPE, Trees.some(scope));
	}

	/**
	 * Replaces the scope of this method invocation expression.
	 *
	 * @return the resulting mutated method invocation expression.
	 */
	public MethodInvocationExpr withNoScope() {
		return location.safeTraversalReplace(SMethodInvocationExpr.SCOPE, Trees.<Expr>none());
	}

	/**
	 * Returns the type args of this method invocation expression.
	 *
	 * @return the type args of this method invocation expression.
	 */
	public NodeList<Type> typeArgs() {
		return location.safeTraversal(SMethodInvocationExpr.TYPE_ARGS);
	}

	/**
	 * Replaces the type args of this method invocation expression.
	 *
	 * @param typeArgs the replacement for the type args of this method invocation expression.
	 * @return the resulting mutated method invocation expression.
	 */
	public MethodInvocationExpr withTypeArgs(NodeList<Type> typeArgs) {
		return location.safeTraversalReplace(SMethodInvocationExpr.TYPE_ARGS, typeArgs);
	}

	/**
	 * Mutates the type args of this method invocation expression.
	 *
	 * @param mutation the mutation to apply to the type args of this method invocation expression.
	 * @return the resulting mutated method invocation expression.
	 */
	public MethodInvocationExpr withTypeArgs(Mutation<NodeList<Type>> mutation) {
		return location.safeTraversalMutate(SMethodInvocationExpr.TYPE_ARGS, mutation);
	}

	/**
	 * Returns the name of this method invocation expression.
	 *
	 * @return the name of this method invocation expression.
	 */
	public Name name() {
		return location.safeTraversal(SMethodInvocationExpr.NAME);
	}

	/**
	 * Replaces the name of this method invocation expression.
	 *
	 * @param name the replacement for the name of this method invocation expression.
	 * @return the resulting mutated method invocation expression.
	 */
	public MethodInvocationExpr withName(Name name) {
		return location.safeTraversalReplace(SMethodInvocationExpr.NAME, name);
	}

	/**
	 * Mutates the name of this method invocation expression.
	 *
	 * @param mutation the mutation to apply to the name of this method invocation expression.
	 * @return the resulting mutated method invocation expression.
	 */
	public MethodInvocationExpr withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(SMethodInvocationExpr.NAME, mutation);
	}

	/**
	 * Replaces the name of this method invocation expression.
	 *
	 * @param name the replacement for the name of this method invocation expression.
	 * @return the resulting mutated method invocation expression.
	 */
	public MethodInvocationExpr withName(String name) {
		return location.safeTraversalReplace(SMethodInvocationExpr.NAME, Trees.name(name));
	}

	/**
	 * Returns the args of this method invocation expression.
	 *
	 * @return the args of this method invocation expression.
	 */
	public NodeList<Expr> args() {
		return location.safeTraversal(SMethodInvocationExpr.ARGS);
	}

	/**
	 * Replaces the args of this method invocation expression.
	 *
	 * @param args the replacement for the args of this method invocation expression.
	 * @return the resulting mutated method invocation expression.
	 */
	public MethodInvocationExpr withArgs(NodeList<Expr> args) {
		return location.safeTraversalReplace(SMethodInvocationExpr.ARGS, args);
	}

	/**
	 * Mutates the args of this method invocation expression.
	 *
	 * @param mutation the mutation to apply to the args of this method invocation expression.
	 * @return the resulting mutated method invocation expression.
	 */
	public MethodInvocationExpr withArgs(Mutation<NodeList<Expr>> mutation) {
		return location.safeTraversalMutate(SMethodInvocationExpr.ARGS, mutation);
	}
}
