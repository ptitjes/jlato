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

public class UnionType extends TreeBase<UnionType.State, Type, UnionType> implements Type {

	public Kind kind() {
		return Kind.UnionType;
	}

	private UnionType(SLocation<UnionType.State> location) {
		super(location);
	}

	public static STree<UnionType.State> make(STree<SNodeListState> types) {
		return new STree<UnionType.State>(new UnionType.State(types));
	}

	public UnionType(NodeList<Type> types) {
		super(new SLocation<UnionType.State>(make(TreeBase.<SNodeListState>treeOf(types))));
	}

	public NodeList<Type> types() {
		return location.safeTraversal(TYPES);
	}

	public UnionType withTypes(NodeList<Type> types) {
		return location.safeTraversalReplace(TYPES, types);
	}

	public UnionType withTypes(Mutation<NodeList<Type>> mutation) {
		return location.safeTraversalMutate(TYPES, mutation);
	}

	public static class State extends SNodeState<State> implements Type.State {

		public final STree<SNodeListState> types;

		State(STree<SNodeListState> types) {
			this.types = types;
		}

		public UnionType.State withTypes(STree<SNodeListState> types) {
			return new UnionType.State(types);
		}

		@Override
		public Kind kind() {
			return Kind.UnionType;
		}

		@Override
		protected Tree doInstantiate(SLocation<UnionType.State> location) {
			return new UnionType(location);
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
			UnionType.State state = (UnionType.State) o;
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

	private static STypeSafeTraversal<UnionType.State, SNodeListState, NodeList<Type>> TYPES = new STypeSafeTraversal<UnionType.State, SNodeListState, NodeList<Type>>() {

		@Override
		protected STree<?> doTraverse(UnionType.State state) {
			return state.types;
		}

		@Override
		protected UnionType.State doRebuildParentState(UnionType.State state, STree<SNodeListState> child) {
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
			child(TYPES, Type.unionShape)
	);
}
