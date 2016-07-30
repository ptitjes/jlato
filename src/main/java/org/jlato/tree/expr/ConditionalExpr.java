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

import org.jlato.tree.TreeCombinators;
import org.jlato.util.Mutation;

/**
 * A conditional expression.
 */
public interface ConditionalExpr extends Expr, TreeCombinators<ConditionalExpr> {

	/**
	 * Returns the condition of this conditional expression.
	 *
	 * @return the condition of this conditional expression.
	 */
	Expr condition();

	/**
	 * Replaces the condition of this conditional expression.
	 *
	 * @param condition the replacement for the condition of this conditional expression.
	 * @return the resulting mutated conditional expression.
	 */
	ConditionalExpr withCondition(Expr condition);

	/**
	 * Mutates the condition of this conditional expression.
	 *
	 * @param mutation the mutation to apply to the condition of this conditional expression.
	 * @return the resulting mutated conditional expression.
	 */
	ConditionalExpr withCondition(Mutation<Expr> mutation);

	/**
	 * Returns the then expression of this conditional expression.
	 *
	 * @return the then expression of this conditional expression.
	 */
	Expr thenExpr();

	/**
	 * Replaces the then expression of this conditional expression.
	 *
	 * @param thenExpr the replacement for the then expression of this conditional expression.
	 * @return the resulting mutated conditional expression.
	 */
	ConditionalExpr withThenExpr(Expr thenExpr);

	/**
	 * Mutates the then expression of this conditional expression.
	 *
	 * @param mutation the mutation to apply to the then expression of this conditional expression.
	 * @return the resulting mutated conditional expression.
	 */
	ConditionalExpr withThenExpr(Mutation<Expr> mutation);

	/**
	 * Returns the else expression of this conditional expression.
	 *
	 * @return the else expression of this conditional expression.
	 */
	Expr elseExpr();

	/**
	 * Replaces the else expression of this conditional expression.
	 *
	 * @param elseExpr the replacement for the else expression of this conditional expression.
	 * @return the resulting mutated conditional expression.
	 */
	ConditionalExpr withElseExpr(Expr elseExpr);

	/**
	 * Mutates the else expression of this conditional expression.
	 *
	 * @param mutation the mutation to apply to the else expression of this conditional expression.
	 * @return the resulting mutated conditional expression.
	 */
	ConditionalExpr withElseExpr(Mutation<Expr> mutation);
}
