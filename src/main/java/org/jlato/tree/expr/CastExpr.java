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
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

/**
 * A cast expression.
 */
public interface CastExpr extends Expr, TreeCombinators<CastExpr> {

	/**
	 * Returns the type of this cast expression.
	 *
	 * @return the type of this cast expression.
	 */
	Type type();

	/**
	 * Replaces the type of this cast expression.
	 *
	 * @param type the replacement for the type of this cast expression.
	 * @return the resulting mutated cast expression.
	 */
	CastExpr withType(Type type);

	/**
	 * Mutates the type of this cast expression.
	 *
	 * @param mutation the mutation to apply to the type of this cast expression.
	 * @return the resulting mutated cast expression.
	 */
	CastExpr withType(Mutation<Type> mutation);

	/**
	 * Returns the expression of this cast expression.
	 *
	 * @return the expression of this cast expression.
	 */
	Expr expr();

	/**
	 * Replaces the expression of this cast expression.
	 *
	 * @param expr the replacement for the expression of this cast expression.
	 * @return the resulting mutated cast expression.
	 */
	CastExpr withExpr(Expr expr);

	/**
	 * Mutates the expression of this cast expression.
	 *
	 * @param mutation the mutation to apply to the expression of this cast expression.
	 * @return the resulting mutated cast expression.
	 */
	CastExpr withExpr(Mutation<Expr> mutation);
}
