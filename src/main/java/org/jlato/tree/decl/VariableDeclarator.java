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

package org.jlato.tree.decl;

import org.jlato.tree.Node;
import org.jlato.tree.NodeOption;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.Expr;
import org.jlato.util.Mutation;

/**
 * A variable declarator.
 */
public interface VariableDeclarator extends Node, TreeCombinators<VariableDeclarator> {

	/**
	 * Returns the identifier of this variable declarator.
	 *
	 * @return the identifier of this variable declarator.
	 */
	VariableDeclaratorId id();

	/**
	 * Replaces the identifier of this variable declarator.
	 *
	 * @param id the replacement for the identifier of this variable declarator.
	 * @return the resulting mutated variable declarator.
	 */
	VariableDeclarator withId(VariableDeclaratorId id);

	/**
	 * Mutates the identifier of this variable declarator.
	 *
	 * @param mutation the mutation to apply to the identifier of this variable declarator.
	 * @return the resulting mutated variable declarator.
	 */
	VariableDeclarator withId(Mutation<VariableDeclaratorId> mutation);

	/**
	 * Returns the init of this variable declarator.
	 *
	 * @return the init of this variable declarator.
	 */
	NodeOption<Expr> init();

	/**
	 * Replaces the init of this variable declarator.
	 *
	 * @param init the replacement for the init of this variable declarator.
	 * @return the resulting mutated variable declarator.
	 */
	VariableDeclarator withInit(NodeOption<Expr> init);

	/**
	 * Mutates the init of this variable declarator.
	 *
	 * @param mutation the mutation to apply to the init of this variable declarator.
	 * @return the resulting mutated variable declarator.
	 */
	VariableDeclarator withInit(Mutation<NodeOption<Expr>> mutation);

	/**
	 * Replaces the init of this variable declarator.
	 *
	 * @param init the replacement for the init of this variable declarator.
	 * @return the resulting mutated variable declarator.
	 */
	VariableDeclarator withInit(Expr init);

	/**
	 * Replaces the init of this variable declarator.
	 *
	 * @return the resulting mutated variable declarator.
	 */
	VariableDeclarator withNoInit();
}
