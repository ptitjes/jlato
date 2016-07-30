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
import org.jlato.internal.bu.expr.SArrayCreationExpr;
import org.jlato.internal.bu.type.SType;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Trees;
import org.jlato.tree.decl.ArrayDim;
import org.jlato.tree.expr.ArrayCreationExpr;
import org.jlato.tree.expr.ArrayDimExpr;
import org.jlato.tree.expr.ArrayInitializerExpr;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

/**
 * An array creation expression.
 */
public class TDArrayCreationExpr extends TDTree<SArrayCreationExpr, Expr, ArrayCreationExpr> implements ArrayCreationExpr {

	/**
	 * Returns the kind of this array creation expression.
	 *
	 * @return the kind of this array creation expression.
	 */
	public Kind kind() {
		return Kind.ArrayCreationExpr;
	}

	/**
	 * Creates an array creation expression for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDArrayCreationExpr(TDLocation<SArrayCreationExpr> location) {
		super(location);
	}

	/**
	 * Creates an array creation expression with the specified child trees.
	 *
	 * @param type     the type child tree.
	 * @param dimExprs the dimension expressions child tree.
	 * @param dims     the dimensions child tree.
	 * @param init     the init child tree.
	 */
	public TDArrayCreationExpr(Type type, NodeList<ArrayDimExpr> dimExprs, NodeList<ArrayDim> dims, NodeOption<ArrayInitializerExpr> init) {
		super(new TDLocation<SArrayCreationExpr>(SArrayCreationExpr.make(TDTree.<SType>treeOf(type), TDTree.<SNodeList>treeOf(dimExprs), TDTree.<SNodeList>treeOf(dims), TDTree.<SNodeOption>treeOf(init))));
	}

	/**
	 * Returns the type of this array creation expression.
	 *
	 * @return the type of this array creation expression.
	 */
	public Type type() {
		return location.safeTraversal(SArrayCreationExpr.TYPE);
	}

	/**
	 * Replaces the type of this array creation expression.
	 *
	 * @param type the replacement for the type of this array creation expression.
	 * @return the resulting mutated array creation expression.
	 */
	public ArrayCreationExpr withType(Type type) {
		return location.safeTraversalReplace(SArrayCreationExpr.TYPE, type);
	}

	/**
	 * Mutates the type of this array creation expression.
	 *
	 * @param mutation the mutation to apply to the type of this array creation expression.
	 * @return the resulting mutated array creation expression.
	 */
	public ArrayCreationExpr withType(Mutation<Type> mutation) {
		return location.safeTraversalMutate(SArrayCreationExpr.TYPE, mutation);
	}

	/**
	 * Returns the dimension expressions of this array creation expression.
	 *
	 * @return the dimension expressions of this array creation expression.
	 */
	public NodeList<ArrayDimExpr> dimExprs() {
		return location.safeTraversal(SArrayCreationExpr.DIM_EXPRS);
	}

	/**
	 * Replaces the dimension expressions of this array creation expression.
	 *
	 * @param dimExprs the replacement for the dimension expressions of this array creation expression.
	 * @return the resulting mutated array creation expression.
	 */
	public ArrayCreationExpr withDimExprs(NodeList<ArrayDimExpr> dimExprs) {
		return location.safeTraversalReplace(SArrayCreationExpr.DIM_EXPRS, dimExprs);
	}

	/**
	 * Mutates the dimension expressions of this array creation expression.
	 *
	 * @param mutation the mutation to apply to the dimension expressions of this array creation expression.
	 * @return the resulting mutated array creation expression.
	 */
	public ArrayCreationExpr withDimExprs(Mutation<NodeList<ArrayDimExpr>> mutation) {
		return location.safeTraversalMutate(SArrayCreationExpr.DIM_EXPRS, mutation);
	}

	/**
	 * Returns the dimensions of this array creation expression.
	 *
	 * @return the dimensions of this array creation expression.
	 */
	public NodeList<ArrayDim> dims() {
		return location.safeTraversal(SArrayCreationExpr.DIMS);
	}

	/**
	 * Replaces the dimensions of this array creation expression.
	 *
	 * @param dims the replacement for the dimensions of this array creation expression.
	 * @return the resulting mutated array creation expression.
	 */
	public ArrayCreationExpr withDims(NodeList<ArrayDim> dims) {
		return location.safeTraversalReplace(SArrayCreationExpr.DIMS, dims);
	}

	/**
	 * Mutates the dimensions of this array creation expression.
	 *
	 * @param mutation the mutation to apply to the dimensions of this array creation expression.
	 * @return the resulting mutated array creation expression.
	 */
	public ArrayCreationExpr withDims(Mutation<NodeList<ArrayDim>> mutation) {
		return location.safeTraversalMutate(SArrayCreationExpr.DIMS, mutation);
	}

	/**
	 * Returns the init of this array creation expression.
	 *
	 * @return the init of this array creation expression.
	 */
	public NodeOption<ArrayInitializerExpr> init() {
		return location.safeTraversal(SArrayCreationExpr.INIT);
	}

	/**
	 * Replaces the init of this array creation expression.
	 *
	 * @param init the replacement for the init of this array creation expression.
	 * @return the resulting mutated array creation expression.
	 */
	public ArrayCreationExpr withInit(NodeOption<ArrayInitializerExpr> init) {
		return location.safeTraversalReplace(SArrayCreationExpr.INIT, init);
	}

	/**
	 * Mutates the init of this array creation expression.
	 *
	 * @param mutation the mutation to apply to the init of this array creation expression.
	 * @return the resulting mutated array creation expression.
	 */
	public ArrayCreationExpr withInit(Mutation<NodeOption<ArrayInitializerExpr>> mutation) {
		return location.safeTraversalMutate(SArrayCreationExpr.INIT, mutation);
	}

	/**
	 * Replaces the init of this array creation expression.
	 *
	 * @param init the replacement for the init of this array creation expression.
	 * @return the resulting mutated array creation expression.
	 */
	public ArrayCreationExpr withInit(ArrayInitializerExpr init) {
		return location.safeTraversalReplace(SArrayCreationExpr.INIT, Trees.some(init));
	}

	/**
	 * Replaces the init of this array creation expression.
	 *
	 * @return the resulting mutated array creation expression.
	 */
	public ArrayCreationExpr withNoInit() {
		return location.safeTraversalReplace(SArrayCreationExpr.INIT, Trees.<ArrayInitializerExpr>none());
	}
}
