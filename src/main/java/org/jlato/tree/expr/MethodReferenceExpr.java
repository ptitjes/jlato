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
import org.jlato.tree.name.Name;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

/**
 * A method reference expression.
 */
public interface MethodReferenceExpr extends Expr, TreeCombinators<MethodReferenceExpr> {

	/**
	 * Returns the scope of this method reference expression.
	 *
	 * @return the scope of this method reference expression.
	 */
	Expr scope();

	/**
	 * Replaces the scope of this method reference expression.
	 *
	 * @param scope the replacement for the scope of this method reference expression.
	 * @return the resulting mutated method reference expression.
	 */
	MethodReferenceExpr withScope(Expr scope);

	/**
	 * Mutates the scope of this method reference expression.
	 *
	 * @param mutation the mutation to apply to the scope of this method reference expression.
	 * @return the resulting mutated method reference expression.
	 */
	MethodReferenceExpr withScope(Mutation<Expr> mutation);

	/**
	 * Returns the type args of this method reference expression.
	 *
	 * @return the type args of this method reference expression.
	 */
	NodeList<Type> typeArgs();

	/**
	 * Replaces the type args of this method reference expression.
	 *
	 * @param typeArgs the replacement for the type args of this method reference expression.
	 * @return the resulting mutated method reference expression.
	 */
	MethodReferenceExpr withTypeArgs(NodeList<Type> typeArgs);

	/**
	 * Mutates the type args of this method reference expression.
	 *
	 * @param mutation the mutation to apply to the type args of this method reference expression.
	 * @return the resulting mutated method reference expression.
	 */
	MethodReferenceExpr withTypeArgs(Mutation<NodeList<Type>> mutation);

	/**
	 * Returns the name of this method reference expression.
	 *
	 * @return the name of this method reference expression.
	 */
	Name name();

	/**
	 * Replaces the name of this method reference expression.
	 *
	 * @param name the replacement for the name of this method reference expression.
	 * @return the resulting mutated method reference expression.
	 */
	MethodReferenceExpr withName(Name name);

	/**
	 * Mutates the name of this method reference expression.
	 *
	 * @param mutation the mutation to apply to the name of this method reference expression.
	 * @return the resulting mutated method reference expression.
	 */
	MethodReferenceExpr withName(Mutation<Name> mutation);

	/**
	 * Replaces the name of this method reference expression.
	 *
	 * @param name the replacement for the name of this method reference expression.
	 * @return the resulting mutated method reference expression.
	 */
	MethodReferenceExpr withName(String name);
}
