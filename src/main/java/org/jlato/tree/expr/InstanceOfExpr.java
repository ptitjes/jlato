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
 * An 'instanceof' expression.
 */
public interface InstanceOfExpr extends Expr, TreeCombinators<InstanceOfExpr> {

	/**
	 * Returns the expression of this 'instanceof' expression.
	 *
	 * @return the expression of this 'instanceof' expression.
	 */
	Expr expr();

	/**
	 * Replaces the expression of this 'instanceof' expression.
	 *
	 * @param expr the replacement for the expression of this 'instanceof' expression.
	 * @return the resulting mutated 'instanceof' expression.
	 */
	InstanceOfExpr withExpr(Expr expr);

	/**
	 * Mutates the expression of this 'instanceof' expression.
	 *
	 * @param mutation the mutation to apply to the expression of this 'instanceof' expression.
	 * @return the resulting mutated 'instanceof' expression.
	 */
	InstanceOfExpr withExpr(Mutation<Expr> mutation);

	/**
	 * Returns the type of this 'instanceof' expression.
	 *
	 * @return the type of this 'instanceof' expression.
	 */
	Type type();

	/**
	 * Replaces the type of this 'instanceof' expression.
	 *
	 * @param type the replacement for the type of this 'instanceof' expression.
	 * @return the resulting mutated 'instanceof' expression.
	 */
	InstanceOfExpr withType(Type type);

	/**
	 * Mutates the type of this 'instanceof' expression.
	 *
	 * @param mutation the mutation to apply to the type of this 'instanceof' expression.
	 * @return the resulting mutated 'instanceof' expression.
	 */
	InstanceOfExpr withType(Mutation<Type> mutation);
}
