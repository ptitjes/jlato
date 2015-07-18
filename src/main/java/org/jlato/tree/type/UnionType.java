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

import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SKind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeList;

import static org.jlato.internal.shapes.LexicalShape.child;
import static org.jlato.internal.shapes.LexicalShape.composite;
import org.jlato.internal.bu.*;
import org.jlato.tree.Tree;

public class UnionType extends TreeBase<UnionType.State, Type, UnionType> implements Type {

	public final static SKind<UnionType.State> kind = new SKind<UnionType.State>() {

	};

	private UnionType(SLocation<UnionType.State> location) {
		super(location);
	}

	public static STree<UnionType.State> make(NodeList<Type> types) {
		return new STree<UnionType.State>(kind, new UnionType.State(TreeBase.<SNodeListState>nodeOf(types)));
	}

	public UnionType(NodeList<Type> types) {
		super(new SLocation<UnionType.State>(make(types)));
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

	private static final STraversal<UnionType.State> TYPES = new STraversal<UnionType.State>() {

		public STree<?> traverse(UnionType.State state) {
			return state.types;
		}

		public UnionType.State rebuildParentState(UnionType.State state, STree<?> child) {
			return state.withTypes((STree) child);
		}

		public STraversal<UnionType.State> leftSibling(UnionType.State state) {
			return null;
		}

		public STraversal<UnionType.State> rightSibling(UnionType.State state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			child(TYPES, Type.unionShape)
	);

	public static class State extends SNodeState<State> {

		public final STree<SNodeListState> types;

		State(STree<SNodeListState> types) {
			this.types = types;
		}

		public UnionType.State withTypes(STree<SNodeListState> types) {
			return new UnionType.State(types);
		}

		public STraversal<UnionType.State> firstChild() {
			return null;
		}

		public STraversal<UnionType.State> lastChild() {
			return null;
		}

		public Tree instantiate(SLocation<UnionType.State> location) {
			return new UnionType(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	}
}
