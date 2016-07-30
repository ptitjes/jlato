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
 * A 'this' expression.
 */
public interface ThisExpr extends Expr, TreeCombinators<ThisExpr> {

	/**
	 * Returns the 'class' expression of this 'this' expression.
	 *
	 * @return the 'class' expression of this 'this' expression.
	 */
	NodeOption<Expr> classExpr();

	/**
	 * Replaces the 'class' expression of this 'this' expression.
	 *
	 * @param classExpr the replacement for the 'class' expression of this 'this' expression.
	 * @return the resulting mutated 'this' expression.
	 */
	ThisExpr withClassExpr(NodeOption<Expr> classExpr);

	/**
	 * Mutates the 'class' expression of this 'this' expression.
	 *
	 * @param mutation the mutation to apply to the 'class' expression of this 'this' expression.
	 * @return the resulting mutated 'this' expression.
	 */
	ThisExpr withClassExpr(Mutation<NodeOption<Expr>> mutation);

	/**
	 * Replaces the 'class' expression of this 'this' expression.
	 *
	 * @param classExpr the replacement for the 'class' expression of this 'this' expression.
	 * @return the resulting mutated 'this' expression.
	 */
	ThisExpr withClassExpr(Expr classExpr);

	/**
	 * Replaces the 'class' expression of this 'this' expression.
	 *
	 * @return the resulting mutated 'this' expression.
	 */
	ThisExpr withNoClassExpr();
}
