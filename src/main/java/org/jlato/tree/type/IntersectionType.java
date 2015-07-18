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
import org.jlato.internal.td.*;

public class IntersectionType extends TreeBase<IntersectionType.State, Type, IntersectionType> implements Type {

	public final static SKind<IntersectionType.State> kind = new SKind<IntersectionType.State>() {
		public IntersectionType instantiate(SLocation<IntersectionType.State> location) {
			return new IntersectionType(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private IntersectionType(SLocation<IntersectionType.State> location) {
		super(location);
	}

	public static STree<IntersectionType.State> make(NodeList<Type> types) {
		return new STree<IntersectionType.State>(kind, new IntersectionType.State(TreeBase.<SNodeListState>nodeOf(types)));
	}

	public IntersectionType(NodeList<Type> types) {
		super(new SLocation<IntersectionType.State>(make(types)));
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

	private static final STraversal<IntersectionType.State> TYPES = new STraversal<IntersectionType.State>() {

		public STree<?> traverse(IntersectionType.State state) {
			return state.types;
		}

		public IntersectionType.State rebuildParentState(IntersectionType.State state, STree<?> child) {
			return state.withTypes((STree) child);
		}

		public STraversal<IntersectionType.State> leftSibling(IntersectionType.State state) {
			return null;
		}

		public STraversal<IntersectionType.State> rightSibling(IntersectionType.State state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			child(TYPES, Type.intersectionShape)
	);

	public static class State extends SNodeState<State> {

		public final STree<SNodeListState> types;

		State(STree<SNodeListState> types) {
			this.types = types;
		}

		public IntersectionType.State withTypes(STree<SNodeListState> types) {
			return new IntersectionType.State(types);
		}

		public STraversal<IntersectionType.State> firstChild() {
			return null;
		}

		public STraversal<IntersectionType.State> lastChild() {
			return null;
		}
	}
}
