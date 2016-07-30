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
 * An unary expression.
 */
public interface UnaryExpr extends Expr, TreeCombinators<UnaryExpr> {

	/**
	 * Returns the op of this unary expression.
	 *
	 * @return the op of this unary expression.
	 */
	UnaryOp op();

	/**
	 * Replaces the op of this unary expression.
	 *
	 * @param op the replacement for the op of this unary expression.
	 * @return the resulting mutated unary expression.
	 */
	UnaryExpr withOp(UnaryOp op);

	/**
	 * Mutates the op of this unary expression.
	 *
	 * @param mutation the mutation to apply to the op of this unary expression.
	 * @return the resulting mutated unary expression.
	 */
	UnaryExpr withOp(Mutation<UnaryOp> mutation);

	/**
	 * Returns the expression of this unary expression.
	 *
	 * @return the expression of this unary expression.
	 */
	Expr expr();

	/**
	 * Replaces the expression of this unary expression.
	 *
	 * @param expr the replacement for the expression of this unary expression.
	 * @return the resulting mutated unary expression.
	 */
	UnaryExpr withExpr(Expr expr);

	/**
	 * Mutates the expression of this unary expression.
	 *
	 * @param mutation the mutation to apply to the expression of this unary expression.
	 * @return the resulting mutated unary expression.
	 */
	UnaryExpr withExpr(Mutation<Expr> mutation);
}
