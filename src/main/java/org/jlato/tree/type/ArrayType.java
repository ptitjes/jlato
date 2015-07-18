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
import org.jlato.tree.Kind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeList;
import org.jlato.tree.decl.ArrayDim;
import org.jlato.tree.decl.VariableDeclaratorId;

import static org.jlato.internal.shapes.LexicalShape.*;
import org.jlato.internal.bu.*;
import org.jlato.tree.Tree;

public class ArrayType extends TreeBase<ArrayType.State, ReferenceType, ArrayType> implements ReferenceType {

	public final static Kind kind = new Kind() {

	};

	private ArrayType(SLocation<ArrayType.State> location) {
		super(location);
	}

	public static STree<ArrayType.State> make(Type componentType, NodeList<ArrayDim> dims) {
		return new STree<ArrayType.State>(kind, new ArrayType.State(TreeBase.<Type.State>nodeOf(componentType), TreeBase.<SNodeListState>nodeOf(dims)));
	}

	public ArrayType(Type componentType, NodeList<ArrayDim> dims) {
		super(new SLocation<ArrayType.State>(make(componentType, dims)));
	}

	public Type componentType() {
		return location.safeTraversal(COMPONENT_TYPE);
	}

	public ArrayType withComponentType(Type componentType) {
		return location.safeTraversalReplace(COMPONENT_TYPE, componentType);
	}

	public ArrayType withComponentType(Mutation<Type> mutation) {
		return location.safeTraversalMutate(COMPONENT_TYPE, mutation);
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

	private static final STraversal<ArrayType.State> COMPONENT_TYPE = new STraversal<ArrayType.State>() {

		public STree<?> traverse(ArrayType.State state) {
			return state.componentType;
		}

		public ArrayType.State rebuildParentState(ArrayType.State state, STree<?> child) {
			return state.withComponentType((STree) child);
		}

		public STraversal<ArrayType.State> leftSibling(ArrayType.State state) {
			return null;
		}

		public STraversal<ArrayType.State> rightSibling(ArrayType.State state) {
			return DIMS;
		}
	};
	private static final STraversal<ArrayType.State> DIMS = new STraversal<ArrayType.State>() {

		public STree<?> traverse(ArrayType.State state) {
			return state.dims;
		}

		public ArrayType.State rebuildParentState(ArrayType.State state, STree<?> child) {
			return state.withDims((STree) child);
		}

		public STraversal<ArrayType.State> leftSibling(ArrayType.State state) {
			return COMPONENT_TYPE;
		}

		public STraversal<ArrayType.State> rightSibling(ArrayType.State state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			child(COMPONENT_TYPE),
			child(DIMS, list())
	);

	public static class State extends SNodeState<State> {

		public final STree<Type.State> componentType;

		public final STree<SNodeListState> dims;

		State(STree<Type.State> componentType, STree<SNodeListState> dims) {
			this.componentType = componentType;
			this.dims = dims;
		}

		public ArrayType.State withComponentType(STree<Type.State> componentType) {
			return new ArrayType.State(componentType, dims);
		}

		public ArrayType.State withDims(STree<SNodeListState> dims) {
			return new ArrayType.State(componentType, dims);
		}

		public STraversal<ArrayType.State> firstChild() {
			return null;
		}

		public STraversal<ArrayType.State> lastChild() {
			return null;
		}

		public Tree instantiate(SLocation<ArrayType.State> location) {
			return new ArrayType(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	}
}
