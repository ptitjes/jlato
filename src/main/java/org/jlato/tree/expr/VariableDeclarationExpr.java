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
import org.jlato.tree.decl.LocalVariableDecl;
import org.jlato.util.Mutation;

/**
 * A variable declaration expression.
 */
public interface VariableDeclarationExpr extends Expr, TreeCombinators<VariableDeclarationExpr> {

	/**
	 * Returns the declaration of this variable declaration expression.
	 *
	 * @return the declaration of this variable declaration expression.
	 */
	LocalVariableDecl declaration();

	/**
	 * Replaces the declaration of this variable declaration expression.
	 *
	 * @param declaration the replacement for the declaration of this variable declaration expression.
	 * @return the resulting mutated variable declaration expression.
	 */
	VariableDeclarationExpr withDeclaration(LocalVariableDecl declaration);

	/**
	 * Mutates the declaration of this variable declaration expression.
	 *
	 * @param mutation the mutation to apply to the declaration of this variable declaration expression.
	 * @return the resulting mutated variable declaration expression.
	 */
	VariableDeclarationExpr withDeclaration(Mutation<LocalVariableDecl> mutation);
}
