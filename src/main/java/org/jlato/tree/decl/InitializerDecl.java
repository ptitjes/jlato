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

import org.jlato.tree.NodeList;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.stmt.BlockStmt;
import org.jlato.util.Mutation;

/**
 * An initializer declaration.
 */
public interface InitializerDecl extends MemberDecl, TreeCombinators<InitializerDecl> {

	/**
	 * Returns the modifiers of this initializer declaration.
	 *
	 * @return the modifiers of this initializer declaration.
	 */
	NodeList<ExtendedModifier> modifiers();

	/**
	 * Replaces the modifiers of this initializer declaration.
	 *
	 * @param modifiers the replacement for the modifiers of this initializer declaration.
	 * @return the resulting mutated initializer declaration.
	 */
	InitializerDecl withModifiers(NodeList<ExtendedModifier> modifiers);

	/**
	 * Mutates the modifiers of this initializer declaration.
	 *
	 * @param mutation the mutation to apply to the modifiers of this initializer declaration.
	 * @return the resulting mutated initializer declaration.
	 */
	InitializerDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation);

	/**
	 * Returns the body of this initializer declaration.
	 *
	 * @return the body of this initializer declaration.
	 */
	BlockStmt body();

	/**
	 * Replaces the body of this initializer declaration.
	 *
	 * @param body the replacement for the body of this initializer declaration.
	 * @return the resulting mutated initializer declaration.
	 */
	InitializerDecl withBody(BlockStmt body);

	/**
	 * Mutates the body of this initializer declaration.
	 *
	 * @param mutation the mutation to apply to the body of this initializer declaration.
	 * @return the resulting mutated initializer declaration.
	 */
	InitializerDecl withBody(Mutation<BlockStmt> mutation);
}
