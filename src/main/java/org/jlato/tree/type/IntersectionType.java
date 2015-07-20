/*
 * Copyright (C) 2015 Didier Villevalois.
 *
 * This file is part of JLaTo.
 *
 * JLaTo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JLaTo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JLaTo.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.jlato.tree.type;

import org.jlato.internal.bu.*;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;

import static org.jlato.internal.shapes.LexicalShape.child;
import static org.jlato.internal.shapes.LexicalShape.composite;

public class IntersectionType extends TreeBase<IntersectionType.State, Type, IntersectionType> implements Type {

	public Kind kind() {
		return Kind.IntersectionType;
	}

	private IntersectionType(SLocation<IntersectionType.State> location) {
		super(location);
	}

	public static STree<IntersectionType.State> make(STree<SNodeListState> types) {
		return new STree<IntersectionType.State>(new IntersectionType.State(types));
	}

	public IntersectionType(NodeList<Type> types) {
		super(new SLocation<IntersectionType.State>(make(TreeBase.<SNodeListState>nodeOf(types))));
	}

	public NodeList<Type> types() {
		return location.safeTraversal(TYPES);
	}

	public IntersectionType withTypes(NodeList<Type> types) {
		return location.safeTraversalReplace(TYPES, types);
	}

	public IntersectionType withTypes(Mutation<NodeList<Type>> mutation) {
		return location.safeTraversalMutate(TYPES, mutation);
	}

	public static class State extends SNodeState<State> implements Type.State {

		public final STree<SNodeListState> types;

		State(STree<SNodeListState> types) {
			this.types = types;
		}

		public IntersectionType.State withTypes(STree<SNodeListState> types) {
			return new IntersectionType.State(types);
		}

		@Override
		public Kind kind() {
			return Kind.IntersectionType;
		}

		@Override
		protected Tree doInstantiate(SLocation<IntersectionType.State> location) {
			return new IntersectionType(location);
		}

		@Override
		public LexicalShape shape() {
			return shape;
		}

		@Override
		public STraversal firstChild() {
			return TYPES;
		}

		@Override
		public STraversal lastChild() {
			return TYPES;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			IntersectionType.State state = (IntersectionType.State) o;
			if (!types.equals(state.types))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			result = 37 * result + types.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<IntersectionType.State, SNodeListState, NodeList<Type>> TYPES = new STypeSafeTraversal<IntersectionType.State, SNodeListState, NodeList<Type>>() {

		@Override
		protected STree<?> doTraverse(IntersectionType.State state) {
			return state.types;
		}

		@Override
		protected IntersectionType.State doRebuildParentState(IntersectionType.State state, STree<SNodeListState> child) {
			return state.withTypes(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			child(TYPES, Type.intersectionShape)
	);
}
