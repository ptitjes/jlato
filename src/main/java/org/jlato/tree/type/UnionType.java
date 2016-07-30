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

package org.jlato.tree.type;

import org.jlato.tree.NodeList;
import org.jlato.tree.TreeCombinators;
import org.jlato.util.Mutation;

/**
 * An union type.
 */
public interface UnionType extends Type, TreeCombinators<UnionType> {

	/**
	 * Returns the types of this union type.
	 *
	 * @return the types of this union type.
	 */
	NodeList<Type> types();

	/**
	 * Replaces the types of this union type.
	 *
	 * @param types the replacement for the types of this union type.
	 * @return the resulting mutated union type.
	 */
	UnionType withTypes(NodeList<Type> types);

	/**
	 * Mutates the types of this union type.
	 *
	 * @param mutation the mutation to apply to the types of this union type.
	 * @return the resulting mutated union type.
	 */
	UnionType withTypes(Mutation<NodeList<Type>> mutation);
}
