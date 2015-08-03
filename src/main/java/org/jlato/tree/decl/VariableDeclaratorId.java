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

package org.jlato.tree.decl;

import org.jlato.internal.bu.*;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.name.Name;
import org.jlato.util.Mutation;

import static org.jlato.internal.shapes.LexicalShape.*;

public class VariableDeclaratorId extends TreeBase<VariableDeclaratorId.State, Tree, VariableDeclaratorId> implements Tree {

	public Kind kind() {
		return Kind.VariableDeclaratorId;
	}

	private VariableDeclaratorId(SLocation<VariableDeclaratorId.State> location) {
		super(location);
	}

	public static STree<VariableDeclaratorId.State> make(STree<Name.State> name, STree<SNodeListState> dims) {
		return new STree<VariableDeclaratorId.State>(new VariableDeclaratorId.State(name, dims));
	}

	public VariableDeclaratorId(Name name, NodeList<ArrayDim> dims) {
		super(new SLocation<VariableDeclaratorId.State>(make(TreeBase.<Name.State>treeOf(name), TreeBase.<SNodeListState>treeOf(dims))));
	}

	public Name name() {
		return location.safeTraversal(NAME);
	}

	public VariableDeclaratorId withName(Name name) {
		return location.safeTraversalReplace(NAME, name);
	}

	public VariableDeclaratorId withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(NAME, mutation);
	}

	public NodeList<ArrayDim> dims() {
		return location.safeTraversal(DIMS);
	}

	public VariableDeclaratorId withDims(NodeList<ArrayDim> dims) {
		return location.safeTraversalReplace(DIMS, dims);
	}

	public VariableDeclaratorId withDims(Mutation<NodeList<ArrayDim>> mutation) {
		return location.safeTraversalMutate(DIMS, mutation);
	}

	public static class State extends SNodeState<State> implements STreeState {

		public final STree<Name.State> name;

		public final STree<SNodeListState> dims;

		State(STree<Name.State> name, STree<SNodeListState> dims) {
			this.name = name;
			this.dims = dims;
		}

		public VariableDeclaratorId.State withName(STree<Name.State> name) {
			return new VariableDeclaratorId.State(name, dims);
		}

		public VariableDeclaratorId.State withDims(STree<SNodeListState> dims) {
			return new VariableDeclaratorId.State(name, dims);
		}

		@Override
		public Kind kind() {
			return Kind.VariableDeclaratorId;
		}

		@Override
		protected Tree doInstantiate(SLocation<VariableDeclaratorId.State> location) {
			return new VariableDeclaratorId(location);
		}

		@Override
		public LexicalShape shape() {
			return shape;
		}

		@Override
		public STraversal firstChild() {
			return NAME;
		}

		@Override
		public STraversal lastChild() {
			return DIMS;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			State state = (State) o;
			if (name == null ? state.name != null : !name.equals(state.name))
				return false;
			if (!dims.equals(state.dims))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			if (name != null) result = 37 * result + name.hashCode();
			result = 37 * result + dims.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<VariableDeclaratorId.State, Name.State, Name> NAME = new STypeSafeTraversal<VariableDeclaratorId.State, Name.State, Name>() {

		@Override
		public STree<?> doTraverse(VariableDeclaratorId.State state) {
			return state.name;
		}

		@Override
		public VariableDeclaratorId.State doRebuildParentState(VariableDeclaratorId.State state, STree<Name.State> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return DIMS;
		}
	};

	private static STypeSafeTraversal<VariableDeclaratorId.State, SNodeListState, NodeList<ArrayDim>> DIMS = new STypeSafeTraversal<VariableDeclaratorId.State, SNodeListState, NodeList<ArrayDim>>() {

		@Override
		public STree<?> doTraverse(VariableDeclaratorId.State state) {
			return state.dims;
		}

		@Override
		public VariableDeclaratorId.State doRebuildParentState(VariableDeclaratorId.State state, STree<SNodeListState> child) {
			return state.withDims(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return NAME;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(NAME),
			child(DIMS, list())
	);
}
