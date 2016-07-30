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
 * A binary expression.
 */
public interface BinaryExpr extends Expr, TreeCombinators<BinaryExpr> {

	/**
	 * Returns the left of this binary expression.
	 *
	 * @return the left of this binary expression.
	 */
	Expr left();

	/**
	 * Replaces the left of this binary expression.
	 *
	 * @param left the replacement for the left of this binary expression.
	 * @return the resulting mutated binary expression.
	 */
	BinaryExpr withLeft(Expr left);

	/**
	 * Mutates the left of this binary expression.
	 *
	 * @param mutation the mutation to apply to the left of this binary expression.
	 * @return the resulting mutated binary expression.
	 */
	BinaryExpr withLeft(Mutation<Expr> mutation);

	/**
	 * Returns the op of this binary expression.
	 *
	 * @return the op of this binary expression.
	 */
	BinaryOp op();

	/**
	 * Replaces the op of this binary expression.
	 *
	 * @param op the replacement for the op of this binary expression.
	 * @return the resulting mutated binary expression.
	 */
	BinaryExpr withOp(BinaryOp op);

	/**
	 * Mutates the op of this binary expression.
	 *
	 * @param mutation the mutation to apply to the op of this binary expression.
	 * @return the resulting mutated binary expression.
	 */
	BinaryExpr withOp(Mutation<BinaryOp> mutation);

	/**
	 * Returns the right of this binary expression.
	 *
	 * @return the right of this binary expression.
	 */
	Expr right();

	/**
	 * Replaces the right of this binary expression.
	 *
	 * @param right the replacement for the right of this binary expression.
	 * @return the resulting mutated binary expression.
	 */
	BinaryExpr withRight(Expr right);

	/**
	 * Mutates the right of this binary expression.
	 *
	 * @param mutation the mutation to apply to the right of this binary expression.
	 * @return the resulting mutated binary expression.
	 */
	BinaryExpr withRight(Mutation<Expr> mutation);
}
