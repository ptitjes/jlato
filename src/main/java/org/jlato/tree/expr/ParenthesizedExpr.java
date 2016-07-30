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
 * A parenthesized expression.
 */
public interface ParenthesizedExpr extends Expr, TreeCombinators<ParenthesizedExpr> {

	/**
	 * Returns the inner of this parenthesized expression.
	 *
	 * @return the inner of this parenthesized expression.
	 */
	Expr inner();

	/**
	 * Replaces the inner of this parenthesized expression.
	 *
	 * @param inner the replacement for the inner of this parenthesized expression.
	 * @return the resulting mutated parenthesized expression.
	 */
	ParenthesizedExpr withInner(Expr inner);

	/**
	 * Mutates the inner of this parenthesized expression.
	 *
	 * @param mutation the mutation to apply to the inner of this parenthesized expression.
	 * @return the resulting mutated parenthesized expression.
	 */
	ParenthesizedExpr withInner(Mutation<Expr> mutation);
}
