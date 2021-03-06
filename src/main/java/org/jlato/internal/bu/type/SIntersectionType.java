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

package org.jlato.internal.bu.type;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.type.TDIntersectionType;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LexicalShape.child;
import static org.jlato.internal.shapes.LexicalShape.composite;

/**
 * A state object for an intersection type.
 */
public class SIntersectionType extends SNode<SIntersectionType> implements SType {

	/**
	 * Creates a <code>BUTree</code> with a new intersection type.
	 *
	 * @param types the types child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with an intersection type.
	 */
	public static BUTree<SIntersectionType> make(BUTree<SNodeList> types) {
		return new BUTree<SIntersectionType>(new SIntersectionType(types));
	}

	/**
	 * The types of this intersection type state.
	 */
	public final BUTree<SNodeList> types;

	/**
	 * Constructs an intersection type state.
	 *
	 * @param types the types child <code>BUTree</code>.
	 */
	public SIntersectionType(BUTree<SNodeList> types) {
		this.types = types;
	}

	/**
	 * Returns the kind of this intersection type.
	 *
	 * @return the kind of this intersection type.
	 */
	@Override
	public Kind kind() {
		return Kind.IntersectionType;
	}

	/**
	 * Replaces the types of this intersection type state.
	 *
	 * @param types the replacement for the types of this intersection type state.
	 * @return the resulting mutated intersection type state.
	 */
	public SIntersectionType withTypes(BUTree<SNodeList> types) {
		return new SIntersectionType(types);
	}

	/**
	 * Builds an intersection type facade for the specified intersection type <code>TDLocation</code>.
	 *
	 * @param location the intersection type <code>TDLocation</code>.
	 * @return an intersection type facade for the specified intersection type <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SIntersectionType> location) {
		return new TDIntersectionType(location);
	}

	/**
	 * Returns the shape for this intersection type state.
	 *
	 * @return the shape for this intersection type state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this intersection type state.
	 *
	 * @return the first child traversal for this intersection type state.
	 */
	@Override
	public STraversal firstChild() {
		return TYPES;
	}

	/**
	 * Returns the last child traversal for this intersection type state.
	 *
	 * @return the last child traversal for this intersection type state.
	 */
	@Override
	public STraversal lastChild() {
		return TYPES;
	}

	/**
	 * Compares this state object to the specified object.
	 *
	 * @param o the object to compare this state with.
	 * @return <code>true</code> if the specified object is equal to this state, <code>false</code> otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SIntersectionType state = (SIntersectionType) o;
		if (!types.equals(state.types))
			return false;
		return true;
	}

	/**
	 * Returns a hash code for this state object.
	 *
	 * @return a hash code value for this object.
	 */
	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + types.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SIntersectionType, SNodeList, NodeList<Type>> TYPES = new STypeSafeTraversal<SIntersectionType, SNodeList, NodeList<Type>>() {

		@Override
		public BUTree<?> doTraverse(SIntersectionType state) {
			return state.types;
		}

		@Override
		public SIntersectionType doRebuildParentState(SIntersectionType state, BUTree<SNodeList> child) {
			return state.withTypes(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(TYPES, org.jlato.internal.bu.type.SType.intersectionShape)
	);
}
