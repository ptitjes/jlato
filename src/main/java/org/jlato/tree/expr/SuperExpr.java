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

import org.jlato.tree.NodeOption;
import org.jlato.tree.TreeCombinators;
import org.jlato.util.Mutation;

/**
 * A 'super' expression.
 */
public interface SuperExpr extends Expr, TreeCombinators<SuperExpr> {

	/**
	 * Returns the 'class' expression of this 'super' expression.
	 *
	 * @return the 'class' expression of this 'super' expression.
	 */
	NodeOption<Expr> classExpr();

	/**
	 * Replaces the 'class' expression of this 'super' expression.
	 *
	 * @param classExpr the replacement for the 'class' expression of this 'super' expression.
	 * @return the resulting mutated 'super' expression.
	 */
	SuperExpr withClassExpr(NodeOption<Expr> classExpr);

	/**
	 * Mutates the 'class' expression of this 'super' expression.
	 *
	 * @param mutation the mutation to apply to the 'class' expression of this 'super' expression.
	 * @return the resulting mutated 'super' expression.
	 */
	SuperExpr withClassExpr(Mutation<NodeOption<Expr>> mutation);

	/**
	 * Replaces the 'class' expression of this 'super' expression.
	 *
	 * @param classExpr the replacement for the 'class' expression of this 'super' expression.
	 * @return the resulting mutated 'super' expression.
	 */
	SuperExpr withClassExpr(Expr classExpr);

	/**
	 * Replaces the 'class' expression of this 'super' expression.
	 *
	 * @return the resulting mutated 'super' expression.
	 */
	SuperExpr withNoClassExpr();
}
