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
import org.jlato.tree.NodeList;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.name.Name;
import org.jlato.util.Mutation;

/**
 * A variable declarator identifier.
 */
public interface VariableDeclaratorId extends Node, TreeCombinators<VariableDeclaratorId> {

	/**
	 * Returns the name of this variable declarator identifier.
	 *
	 * @return the name of this variable declarator identifier.
	 */
	Name name();

	/**
	 * Replaces the name of this variable declarator identifier.
	 *
	 * @param name the replacement for the name of this variable declarator identifier.
	 * @return the resulting mutated variable declarator identifier.
	 */
	VariableDeclaratorId withName(Name name);

	/**
	 * Mutates the name of this variable declarator identifier.
	 *
	 * @param mutation the mutation to apply to the name of this variable declarator identifier.
	 * @return the resulting mutated variable declarator identifier.
	 */
	VariableDeclaratorId withName(Mutation<Name> mutation);

	/**
	 * Replaces the name of this variable declarator identifier.
	 *
	 * @param name the replacement for the name of this variable declarator identifier.
	 * @return the resulting mutated variable declarator identifier.
	 */
	VariableDeclaratorId withName(String name);

	/**
	 * Returns the dimensions of this variable declarator identifier.
	 *
	 * @return the dimensions of this variable declarator identifier.
	 */
	NodeList<ArrayDim> dims();

	/**
	 * Replaces the dimensions of this variable declarator identifier.
	 *
	 * @param dims the replacement for the dimensions of this variable declarator identifier.
	 * @return the resulting mutated variable declarator identifier.
	 */
	VariableDeclaratorId withDims(NodeList<ArrayDim> dims);

	/**
	 * Mutates the dimensions of this variable declarator identifier.
	 *
	 * @param mutation the mutation to apply to the dimensions of this variable declarator identifier.
	 * @return the resulting mutated variable declarator identifier.
	 */
	VariableDeclaratorId withDims(Mutation<NodeList<ArrayDim>> mutation);
}
