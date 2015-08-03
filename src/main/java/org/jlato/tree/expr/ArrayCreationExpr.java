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

package org.jlato.tree.expr;

import org.jlato.internal.bu.*;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.*;
import org.jlato.tree.decl.ArrayDim;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class ArrayCreationExpr extends TreeBase<ArrayCreationExpr.State, Expr, ArrayCreationExpr> implements Expr {

	public Kind kind() {
		return Kind.ArrayCreationExpr;
	}

	private ArrayCreationExpr(SLocation<ArrayCreationExpr.State> location) {
		super(location);
	}

	public static STree<ArrayCreationExpr.State> make(STree<? extends Type.State> type, STree<SNodeListState> dimExprs, STree<SNodeListState> dims, STree<SNodeOptionState> init) {
		return new STree<ArrayCreationExpr.State>(new ArrayCreationExpr.State(type, dimExprs, dims, init));
	}

	public ArrayCreationExpr(Type type, NodeList<ArrayDimExpr> dimExprs, NodeList<ArrayDim> dims, NodeOption<ArrayInitializerExpr> init) {
		super(new SLocation<ArrayCreationExpr.State>(make(TreeBase.<Type.State>treeOf(type), TreeBase.<SNodeListState>treeOf(dimExprs), TreeBase.<SNodeListState>treeOf(dims), TreeBase.<SNodeOptionState>treeOf(init))));
	}

	public Type type() {
		return location.safeTraversal(TYPE);
	}

	public ArrayCreationExpr withType(Type type) {
		return location.safeTraversalReplace(TYPE, type);
	}

	public ArrayCreationExpr withType(Mutation<Type> mutation) {
		return location.safeTraversalMutate(TYPE, mutation);
	}

	public NodeList<ArrayDimExpr> dimExprs() {
		return location.safeTraversal(DIM_EXPRS);
	}

	public ArrayCreationExpr withDimExprs(NodeList<ArrayDimExpr> dimExprs) {
		return location.safeTraversalReplace(DIM_EXPRS, dimExprs);
	}

	public ArrayCreationExpr withDimExprs(Mutation<NodeList<ArrayDimExpr>> mutation) {
		return location.safeTraversalMutate(DIM_EXPRS, mutation);
	}

	public NodeList<ArrayDim> dims() {
		return location.safeTraversal(DIMS);
	}

	public ArrayCreationExpr withDims(NodeList<ArrayDim> dims) {
		return location.safeTraversalReplace(DIMS, dims);
	}

	public ArrayCreationExpr withDims(Mutation<NodeList<ArrayDim>> mutation) {
		return location.safeTraversalMutate(DIMS, mutation);
	}

	public NodeOption<ArrayInitializerExpr> init() {
		return location.safeTraversal(INIT);
	}

	public ArrayCreationExpr withInit(NodeOption<ArrayInitializerExpr> init) {
		return location.safeTraversalReplace(INIT, init);
	}

	public ArrayCreationExpr withInit(Mutation<NodeOption<ArrayInitializerExpr>> mutation) {
		return location.safeTraversalMutate(INIT, mutation);
	}

	public static class State extends SNodeState<State> implements Expr.State {

		public final STree<? extends Type.State> type;

		public final STree<SNodeListState> dimExprs;

		public final STree<SNodeListState> dims;

		public final STree<SNodeOptionState> init;

		State(STree<? extends Type.State> type, STree<SNodeListState> dimExprs, STree<SNodeListState> dims, STree<SNodeOptionState> init) {
			this.type = type;
			this.dimExprs = dimExprs;
			this.dims = dims;
			this.init = init;
		}

		public ArrayCreationExpr.State withType(STree<? extends Type.State> type) {
			return new ArrayCreationExpr.State(type, dimExprs, dims, init);
		}

		public ArrayCreationExpr.State withDimExprs(STree<SNodeListState> dimExprs) {
			return new ArrayCreationExpr.State(type, dimExprs, dims, init);
		}

		public ArrayCreationExpr.State withDims(STree<SNodeListState> dims) {
			return new ArrayCreationExpr.State(type, dimExprs, dims, init);
		}

		public ArrayCreationExpr.State withInit(STree<SNodeOptionState> init) {
			return new ArrayCreationExpr.State(type, dimExprs, dims, init);
		}

		@Override
		public Kind kind() {
			return Kind.ArrayCreationExpr;
		}

		@Override
		protected Tree doInstantiate(SLocation<ArrayCreationExpr.State> location) {
			return new ArrayCreationExpr(location);
		}

		@Override
		public LexicalShape shape() {
			return shape;
		}

		@Override
		public STraversal firstChild() {
			return TYPE;
		}

		@Override
		public STraversal lastChild() {
			return INIT;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			State state = (State) o;
			if (type == null ? state.type != null : !type.equals(state.type))
				return false;
			if (!dimExprs.equals(state.dimExprs))
				return false;
			if (!dims.equals(state.dims))
				return false;
			if (!init.equals(state.init))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			if (type != null) result = 37 * result + type.hashCode();
			result = 37 * result + dimExprs.hashCode();
			result = 37 * result + dims.hashCode();
			result = 37 * result + init.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<ArrayCreationExpr.State, Type.State, Type> TYPE = new STypeSafeTraversal<ArrayCreationExpr.State, Type.State, Type>() {

		@Override
		public STree<?> doTraverse(ArrayCreationExpr.State state) {
			return state.type;
		}

		@Override
		public ArrayCreationExpr.State doRebuildParentState(ArrayCreationExpr.State state, STree<Type.State> child) {
			return state.withType(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return DIM_EXPRS;
		}
	};

	private static STypeSafeTraversal<ArrayCreationExpr.State, SNodeListState, NodeList<ArrayDimExpr>> DIM_EXPRS = new STypeSafeTraversal<ArrayCreationExpr.State, SNodeListState, NodeList<ArrayDimExpr>>() {

		@Override
		public STree<?> doTraverse(ArrayCreationExpr.State state) {
			return state.dimExprs;
		}

		@Override
		public ArrayCreationExpr.State doRebuildParentState(ArrayCreationExpr.State state, STree<SNodeListState> child) {
			return state.withDimExprs(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return TYPE;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return DIMS;
		}
	};

	private static STypeSafeTraversal<ArrayCreationExpr.State, SNodeListState, NodeList<ArrayDim>> DIMS = new STypeSafeTraversal<ArrayCreationExpr.State, SNodeListState, NodeList<ArrayDim>>() {

		@Override
		public STree<?> doTraverse(ArrayCreationExpr.State state) {
			return state.dims;
		}

		@Override
		public ArrayCreationExpr.State doRebuildParentState(ArrayCreationExpr.State state, STree<SNodeListState> child) {
			return state.withDims(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return DIM_EXPRS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return INIT;
		}
	};

	private static STypeSafeTraversal<ArrayCreationExpr.State, SNodeOptionState, NodeOption<ArrayInitializerExpr>> INIT = new STypeSafeTraversal<ArrayCreationExpr.State, SNodeOptionState, NodeOption<ArrayInitializerExpr>>() {

		@Override
		public STree<?> doTraverse(ArrayCreationExpr.State state) {
			return state.init;
		}

		@Override
		public ArrayCreationExpr.State doRebuildParentState(ArrayCreationExpr.State state, STree<SNodeOptionState> child) {
			return state.withInit(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return DIMS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			token(LToken.New),
			child(TYPE),
			child(DIM_EXPRS, list()),
			child(DIMS, list()),
			child(INIT, when(some(),
					element().withSpacingBefore(space())
			))
	);
}
