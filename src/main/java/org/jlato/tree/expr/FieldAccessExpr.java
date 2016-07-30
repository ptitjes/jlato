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
import org.jlato.tree.name.Name;
import org.jlato.util.Mutation;

/**
 * A field access expression.
 */
public interface FieldAccessExpr extends Expr, TreeCombinators<FieldAccessExpr> {

	/**
	 * Returns the scope of this field access expression.
	 *
	 * @return the scope of this field access expression.
	 */
	NodeOption<Expr> scope();

	/**
	 * Replaces the scope of this field access expression.
	 *
	 * @param scope the replacement for the scope of this field access expression.
	 * @return the resulting mutated field access expression.
	 */
	FieldAccessExpr withScope(NodeOption<Expr> scope);

	/**
	 * Mutates the scope of this field access expression.
	 *
	 * @param mutation the mutation to apply to the scope of this field access expression.
	 * @return the resulting mutated field access expression.
	 */
	FieldAccessExpr withScope(Mutation<NodeOption<Expr>> mutation);

	/**
	 * Replaces the scope of this field access expression.
	 *
	 * @param scope the replacement for the scope of this field access expression.
	 * @return the resulting mutated field access expression.
	 */
	FieldAccessExpr withScope(Expr scope);

	/**
	 * Replaces the scope of this field access expression.
	 *
	 * @return the resulting mutated field access expression.
	 */
	FieldAccessExpr withNoScope();

	/**
	 * Returns the name of this field access expression.
	 *
	 * @return the name of this field access expression.
	 */
	Name name();

	/**
	 * Replaces the name of this field access expression.
	 *
	 * @param name the replacement for the name of this field access expression.
	 * @return the resulting mutated field access expression.
	 */
	FieldAccessExpr withName(Name name);

	/**
	 * Mutates the name of this field access expression.
	 *
	 * @param mutation the mutation to apply to the name of this field access expression.
	 * @return the resulting mutated field access expression.
	 */
	FieldAccessExpr withName(Mutation<Name> mutation);

	/**
	 * Replaces the name of this field access expression.
	 *
	 * @param name the replacement for the name of this field access expression.
	 * @return the resulting mutated field access expression.
	 */
	FieldAccessExpr withName(String name);
}
