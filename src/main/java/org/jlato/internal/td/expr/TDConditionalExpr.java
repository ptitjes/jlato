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

import org.jlato.internal.bu.expr.SConditionalExpr;
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.expr.ConditionalExpr;
import org.jlato.tree.expr.Expr;
import org.jlato.util.Mutation;

/**
 * A conditional expression.
 */
public class TDConditionalExpr extends TDTree<SConditionalExpr, Expr, ConditionalExpr> implements ConditionalExpr {

	/**
	 * Returns the kind of this conditional expression.
	 *
	 * @return the kind of this conditional expression.
	 */
	public Kind kind() {
		return Kind.ConditionalExpr;
	}

	/**
	 * Creates a conditional expression for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDConditionalExpr(TDLocation<SConditionalExpr> location) {
		super(location);
	}

	/**
	 * Creates a conditional expression with the specified child trees.
	 *
	 * @param condition the condition child tree.
	 * @param thenExpr  the then expression child tree.
	 * @param elseExpr  the else expression child tree.
	 */
	public TDConditionalExpr(Expr condition, Expr thenExpr, Expr elseExpr) {
		super(new TDLocation<SConditionalExpr>(SConditionalExpr.make(TDTree.<SExpr>treeOf(condition), TDTree.<SExpr>treeOf(thenExpr), TDTree.<SExpr>treeOf(elseExpr))));
	}

	/**
	 * Returns the condition of this conditional expression.
	 *
	 * @return the condition of this conditional expression.
	 */
	public Expr condition() {
		return location.safeTraversal(SConditionalExpr.CONDITION);
	}

	/**
	 * Replaces the condition of this conditional expression.
	 *
	 * @param condition the replacement for the condition of this conditional expression.
	 * @return the resulting mutated conditional expression.
	 */
	public ConditionalExpr withCondition(Expr condition) {
		return location.safeTraversalReplace(SConditionalExpr.CONDITION, condition);
	}

	/**
	 * Mutates the condition of this conditional expression.
	 *
	 * @param mutation the mutation to apply to the condition of this conditional expression.
	 * @return the resulting mutated conditional expression.
	 */
	public ConditionalExpr withCondition(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SConditionalExpr.CONDITION, mutation);
	}

	/**
	 * Returns the then expression of this conditional expression.
	 *
	 * @return the then expression of this conditional expression.
	 */
	public Expr thenExpr() {
		return location.safeTraversal(SConditionalExpr.THEN_EXPR);
	}

	/**
	 * Replaces the then expression of this conditional expression.
	 *
	 * @param thenExpr the replacement for the then expression of this conditional expression.
	 * @return the resulting mutated conditional expression.
	 */
	public ConditionalExpr withThenExpr(Expr thenExpr) {
		return location.safeTraversalReplace(SConditionalExpr.THEN_EXPR, thenExpr);
	}

	/**
	 * Mutates the then expression of this conditional expression.
	 *
	 * @param mutation the mutation to apply to the then expression of this conditional expression.
	 * @return the resulting mutated conditional expression.
	 */
	public ConditionalExpr withThenExpr(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SConditionalExpr.THEN_EXPR, mutation);
	}

	/**
	 * Returns the else expression of this conditional expression.
	 *
	 * @return the else expression of this conditional expression.
	 */
	public Expr elseExpr() {
		return location.safeTraversal(SConditionalExpr.ELSE_EXPR);
	}

	/**
	 * Replaces the else expression of this conditional expression.
	 *
	 * @param elseExpr the replacement for the else expression of this conditional expression.
	 * @return the resulting mutated conditional expression.
	 */
	public ConditionalExpr withElseExpr(Expr elseExpr) {
		return location.safeTraversalReplace(SConditionalExpr.ELSE_EXPR, elseExpr);
	}

	/**
	 * Mutates the else expression of this conditional expression.
	 *
	 * @param mutation the mutation to apply to the else expression of this conditional expression.
	 * @return the resulting mutated conditional expression.
	 */
	public ConditionalExpr withElseExpr(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SConditionalExpr.ELSE_EXPR, mutation);
	}
}
