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

package org.jlato.internal.td.type;

import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.type.SIntersectionType;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.type.IntersectionType;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

/**
 * An intersection type.
 */
public class TDIntersectionType extends TDTree<SIntersectionType, Type, IntersectionType> implements IntersectionType {

	/**
	 * Returns the kind of this intersection type.
	 *
	 * @return the kind of this intersection type.
	 */
	public Kind kind() {
		return Kind.IntersectionType;
	}

	/**
	 * Creates an intersection type for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDIntersectionType(TDLocation<SIntersectionType> location) {
		super(location);
	}

	/**
	 * Creates an intersection type with the specified child trees.
	 *
	 * @param types the types child tree.
	 */
	public TDIntersectionType(NodeList<Type> types) {
		super(new TDLocation<SIntersectionType>(SIntersectionType.make(TDTree.<SNodeList>treeOf(types))));
	}

	/**
	 * Returns the types of this intersection type.
	 *
	 * @return the types of this intersection type.
	 */
	public NodeList<Type> types() {
		return location.safeTraversal(SIntersectionType.TYPES);
	}

	/**
	 * Replaces the types of this intersection type.
	 *
	 * @param types the replacement for the types of this intersection type.
	 * @return the resulting mutated intersection type.
	 */
	public IntersectionType withTypes(NodeList<Type> types) {
		return location.safeTraversalReplace(SIntersectionType.TYPES, types);
	}

	/**
	 * Mutates the types of this intersection type.
	 *
	 * @param mutation the mutation to apply to the types of this intersection type.
	 * @return the resulting mutated intersection type.
	 */
	public IntersectionType withTypes(Mutation<NodeList<Type>> mutation) {
		return location.safeTraversalMutate(SIntersectionType.TYPES, mutation);
	}
}
