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
 * An array access expression.
 */
public interface ArrayAccessExpr extends Expr, TreeCombinators<ArrayAccessExpr> {

	/**
	 * Returns the name of this array access expression.
	 *
	 * @return the name of this array access expression.
	 */
	Expr name();

	/**
	 * Replaces the name of this array access expression.
	 *
	 * @param name the replacement for the name of this array access expression.
	 * @return the resulting mutated array access expression.
	 */
	ArrayAccessExpr withName(Expr name);

	/**
	 * Mutates the name of this array access expression.
	 *
	 * @param mutation the mutation to apply to the name of this array access expression.
	 * @return the resulting mutated array access expression.
	 */
	ArrayAccessExpr withName(Mutation<Expr> mutation);

	/**
	 * Returns the index of this array access expression.
	 *
	 * @return the index of this array access expression.
	 */
	Expr index();

	/**
	 * Replaces the index of this array access expression.
	 *
	 * @param index the replacement for the index of this array access expression.
	 * @return the resulting mutated array access expression.
	 */
	ArrayAccessExpr withIndex(Expr index);

	/**
	 * Mutates the index of this array access expression.
	 *
	 * @param mutation the mutation to apply to the index of this array access expression.
	 * @return the resulting mutated array access expression.
	 */
	ArrayAccessExpr withIndex(Mutation<Expr> mutation);
}
