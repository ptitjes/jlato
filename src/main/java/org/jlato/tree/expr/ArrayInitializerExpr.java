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
import org.jlato.tree.TreeCombinators;
import org.jlato.util.Mutation;

/**
 * An array initializer expression.
 */
public interface ArrayInitializerExpr extends Expr, TreeCombinators<ArrayInitializerExpr> {

	/**
	 * Returns the values of this array initializer expression.
	 *
	 * @return the values of this array initializer expression.
	 */
	NodeList<Expr> values();

	/**
	 * Replaces the values of this array initializer expression.
	 *
	 * @param values the replacement for the values of this array initializer expression.
	 * @return the resulting mutated array initializer expression.
	 */
	ArrayInitializerExpr withValues(NodeList<Expr> values);

	/**
	 * Mutates the values of this array initializer expression.
	 *
	 * @param mutation the mutation to apply to the values of this array initializer expression.
	 * @return the resulting mutated array initializer expression.
	 */
	ArrayInitializerExpr withValues(Mutation<NodeList<Expr>> mutation);

	/**
	 * Tests whether this array initializer expression has a trailing comma.
	 *
	 * @return <code>true</code> if this array initializer expression has a trailing comma, <code>false</code> otherwise.
	 */
	boolean trailingComma();

	/**
	 * Sets whether this array initializer expression has a trailing comma.
	 *
	 * @param trailingComma <code>true</code> if this array initializer expression has a trailing comma, <code>false</code> otherwise.
	 * @return the resulting mutated array initializer expression.
	 */
	ArrayInitializerExpr withTrailingComma(boolean trailingComma);

	/**
	 * Mutates whether this array initializer expression has a trailing comma.
	 *
	 * @param mutation the mutation to apply to whether this array initializer expression has a trailing comma.
	 * @return the resulting mutated array initializer expression.
	 */
	ArrayInitializerExpr withTrailingComma(Mutation<Boolean> mutation);
}
