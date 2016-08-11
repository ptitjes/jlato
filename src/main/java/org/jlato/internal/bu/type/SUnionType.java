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
import org.jlato.internal.td.type.TDUnionType;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LexicalShape.child;
import static org.jlato.internal.shapes.LexicalShape.composite;

/**
 * A state object for an union type.
 */
public class SUnionType extends SNode<SUnionType> implements SType {

	/**
	 * Creates a <code>BUTree</code> with a new union type.
	 *
	 * @param types the types child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with an union type.
	 */
	public static BUTree<SUnionType> make(BUTree<SNodeList> types) {
		return new BUTree<SUnionType>(new SUnionType(types));
	}

	/**
	 * The types of this union type state.
	 */
	public final BUTree<SNodeList> types;

	/**
	 * Constructs an union type state.
	 *
	 * @param types the types child <code>BUTree</code>.
	 */
	public SUnionType(BUTree<SNodeList> types) {
		this.types = types;
	}

	/**
	 * Returns the kind of this union type.
	 *
	 * @return the kind of this union type.
	 */
	@Override
	public Kind kind() {
		return Kind.UnionType;
	}

	/**
	 * Replaces the types of this union type state.
	 *
	 * @param types the replacement for the types of this union type state.
	 * @return the resulting mutated union type state.
	 */
	public SUnionType withTypes(BUTree<SNodeList> types) {
		return new SUnionType(types);
	}

	/**
	 * Builds an union type facade for the specified union type <code>TDLocation</code>.
	 *
	 * @param location the union type <code>TDLocation</code>.
	 * @return an union type facade for the specified union type <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SUnionType> location) {
		return new TDUnionType(location);
	}

	/**
	 * Returns the shape for this union type state.
	 *
	 * @return the shape for this union type state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this union type state.
	 *
	 * @return the first child traversal for this union type state.
	 */
	@Override
	public STraversal firstChild() {
		return TYPES;
	}

	/**
	 * Returns the last child traversal for this union type state.
	 *
	 * @return the last child traversal for this union type state.
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
		SUnionType state = (SUnionType) o;
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

	public static STypeSafeTraversal<SUnionType, SNodeList, NodeList<Type>> TYPES = new STypeSafeTraversal<SUnionType, SNodeList, NodeList<Type>>() {

		@Override
		public BUTree<?> doTraverse(SUnionType state) {
			return state.types;
		}

		@Override
		public SUnionType doRebuildParentState(SUnionType state, BUTree<SNodeList> child) {
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
			child(TYPES, org.jlato.internal.bu.type.SType.unionShape)
	);
}
