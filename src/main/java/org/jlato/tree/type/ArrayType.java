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
import org.jlato.tree.decl.ArrayDim;
import org.jlato.util.Mutation;

/**
 * An array type.
 */
public interface ArrayType extends ReferenceType, TreeCombinators<ArrayType> {

	/**
	 * Returns the component type of this array type.
	 *
	 * @return the component type of this array type.
	 */
	Type componentType();

	/**
	 * Replaces the component type of this array type.
	 *
	 * @param componentType the replacement for the component type of this array type.
	 * @return the resulting mutated array type.
	 */
	ArrayType withComponentType(Type componentType);

	/**
	 * Mutates the component type of this array type.
	 *
	 * @param mutation the mutation to apply to the component type of this array type.
	 * @return the resulting mutated array type.
	 */
	ArrayType withComponentType(Mutation<Type> mutation);

	/**
	 * Returns the dimensions of this array type.
	 *
	 * @return the dimensions of this array type.
	 */
	NodeList<ArrayDim> dims();

	/**
	 * Replaces the dimensions of this array type.
	 *
	 * @param dims the replacement for the dimensions of this array type.
	 * @return the resulting mutated array type.
	 */
	ArrayType withDims(NodeList<ArrayDim> dims);

	/**
	 * Mutates the dimensions of this array type.
	 *
	 * @param mutation the mutation to apply to the dimensions of this array type.
	 * @return the resulting mutated array type.
	 */
	ArrayType withDims(Mutation<NodeList<ArrayDim>> mutation);
}
