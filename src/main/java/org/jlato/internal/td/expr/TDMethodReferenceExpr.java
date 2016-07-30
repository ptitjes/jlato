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
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.bu.expr.SMethodReferenceExpr;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.Trees;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.expr.MethodReferenceExpr;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

/**
 * A method reference expression.
 */
public class TDMethodReferenceExpr extends TDTree<SMethodReferenceExpr, Expr, MethodReferenceExpr> implements MethodReferenceExpr {

	/**
	 * Returns the kind of this method reference expression.
	 *
	 * @return the kind of this method reference expression.
	 */
	public Kind kind() {
		return Kind.MethodReferenceExpr;
	}

	/**
	 * Creates a method reference expression for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDMethodReferenceExpr(TDLocation<SMethodReferenceExpr> location) {
		super(location);
	}

	/**
	 * Creates a method reference expression with the specified child trees.
	 *
	 * @param scope    the scope child tree.
	 * @param typeArgs the type args child tree.
	 * @param name     the name child tree.
	 */
	public TDMethodReferenceExpr(Expr scope, NodeList<Type> typeArgs, Name name) {
		super(new TDLocation<SMethodReferenceExpr>(SMethodReferenceExpr.make(TDTree.<SExpr>treeOf(scope), TDTree.<SNodeList>treeOf(typeArgs), TDTree.<SName>treeOf(name))));
	}

	/**
	 * Returns the scope of this method reference expression.
	 *
	 * @return the scope of this method reference expression.
	 */
	public Expr scope() {
		return location.safeTraversal(SMethodReferenceExpr.SCOPE);
	}

	/**
	 * Replaces the scope of this method reference expression.
	 *
	 * @param scope the replacement for the scope of this method reference expression.
	 * @return the resulting mutated method reference expression.
	 */
	public MethodReferenceExpr withScope(Expr scope) {
		return location.safeTraversalReplace(SMethodReferenceExpr.SCOPE, scope);
	}

	/**
	 * Mutates the scope of this method reference expression.
	 *
	 * @param mutation the mutation to apply to the scope of this method reference expression.
	 * @return the resulting mutated method reference expression.
	 */
	public MethodReferenceExpr withScope(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SMethodReferenceExpr.SCOPE, mutation);
	}

	/**
	 * Returns the type args of this method reference expression.
	 *
	 * @return the type args of this method reference expression.
	 */
	public NodeList<Type> typeArgs() {
		return location.safeTraversal(SMethodReferenceExpr.TYPE_ARGS);
	}

	/**
	 * Replaces the type args of this method reference expression.
	 *
	 * @param typeArgs the replacement for the type args of this method reference expression.
	 * @return the resulting mutated method reference expression.
	 */
	public MethodReferenceExpr withTypeArgs(NodeList<Type> typeArgs) {
		return location.safeTraversalReplace(SMethodReferenceExpr.TYPE_ARGS, typeArgs);
	}

	/**
	 * Mutates the type args of this method reference expression.
	 *
	 * @param mutation the mutation to apply to the type args of this method reference expression.
	 * @return the resulting mutated method reference expression.
	 */
	public MethodReferenceExpr withTypeArgs(Mutation<NodeList<Type>> mutation) {
		return location.safeTraversalMutate(SMethodReferenceExpr.TYPE_ARGS, mutation);
	}

	/**
	 * Returns the name of this method reference expression.
	 *
	 * @return the name of this method reference expression.
	 */
	public Name name() {
		return location.safeTraversal(SMethodReferenceExpr.NAME);
	}

	/**
	 * Replaces the name of this method reference expression.
	 *
	 * @param name the replacement for the name of this method reference expression.
	 * @return the resulting mutated method reference expression.
	 */
	public MethodReferenceExpr withName(Name name) {
		return location.safeTraversalReplace(SMethodReferenceExpr.NAME, name);
	}

	/**
	 * Mutates the name of this method reference expression.
	 *
	 * @param mutation the mutation to apply to the name of this method reference expression.
	 * @return the resulting mutated method reference expression.
	 */
	public MethodReferenceExpr withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(SMethodReferenceExpr.NAME, mutation);
	}

	/**
	 * Replaces the name of this method reference expression.
	 *
	 * @param name the replacement for the name of this method reference expression.
	 * @return the resulting mutated method reference expression.
	 */
	public MethodReferenceExpr withName(String name) {
		return location.safeTraversalReplace(SMethodReferenceExpr.NAME, Trees.name(name));
	}
}
