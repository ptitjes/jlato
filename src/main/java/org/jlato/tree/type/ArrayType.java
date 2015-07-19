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
import org.jlato.internal.bu.*;
import org.jlato.internal.td.*;
import org.jlato.internal.bu.*;
import org.jlato.internal.td.*;

public class ArrayType extends TreeBase<ArrayType.State, ReferenceType, ArrayType> implements ReferenceType {

	public Kind kind() {
		return Kind.ArrayType;
	}

	private ArrayType(SLocation<ArrayType.State> location) {
		super(location);
	}

	public static STree<ArrayType.State> make(STree<? extends Type.State> componentType, STree<SNodeListState> dims) {
		return new STree<ArrayType.State>(new ArrayType.State(componentType, dims));
	}

	public ArrayType(Type componentType, NodeList<ArrayDim> dims) {
		super(new SLocation<ArrayType.State>(make(TreeBase.<Type.State>nodeOf(componentType), TreeBase.<SNodeListState>nodeOf(dims))));
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

	public static class State extends SNodeState<State>implements ReferenceType.State {

		public final STree<? extends Type.State> componentType;

		public final STree<SNodeListState> dims;

		State(STree<? extends Type.State> componentType, STree<SNodeListState> dims) {
			this.componentType = componentType;
			this.dims = dims;
		}

		public ArrayType.State withComponentType(STree<? extends Type.State> componentType) {
			return new ArrayType.State(componentType, dims);
		}

		public ArrayType.State withDims(STree<SNodeListState> dims) {
			return new ArrayType.State(componentType, dims);
		}

		@Override
		public Kind kind() {
			return Kind.ArrayType;
		}

		@Override
		protected Tree doInstantiate(SLocation<ArrayType.State> location) {
			return new ArrayType(location);
		}

		@Override
		public LexicalShape shape() {
			return shape;
		}

		@Override
		public STraversal firstChild() {
			return COMPONENT_TYPE;
		}

		@Override
		public STraversal lastChild() {
			return DIMS;
		}
	}

	private static STypeSafeTraversal<ArrayType.State, Type.State, Type> COMPONENT_TYPE = new STypeSafeTraversal<ArrayType.State, Type.State, Type>() {

		@Override
		protected STree<?> doTraverse(ArrayType.State state) {
			return state.componentType;
		}

		@Override
		protected ArrayType.State doRebuildParentState(ArrayType.State state, STree<Type.State> child) {
			return state.withComponentType(child);
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

	private static STypeSafeTraversal<ArrayType.State, SNodeListState, NodeList<ArrayDim>> DIMS = new STypeSafeTraversal<ArrayType.State, SNodeListState, NodeList<ArrayDim>>() {

		@Override
		protected STree<?> doTraverse(ArrayType.State state) {
			return state.dims;
		}

		@Override
		protected ArrayType.State doRebuildParentState(ArrayType.State state, STree<SNodeListState> child) {
			return state.withDims(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return COMPONENT_TYPE;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			child(COMPONENT_TYPE),
			child(DIMS, list())
	);
}
