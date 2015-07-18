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

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.Kind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.decl.ArrayDim;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LSCondition.childIs;
import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;
import org.jlato.internal.bu.*;
import org.jlato.tree.Tree;

public class ArrayCreationExpr extends TreeBase<ArrayCreationExpr.State, Expr, ArrayCreationExpr> implements Expr {

	public final static Kind kind = new Kind() {

	};

	private ArrayCreationExpr(SLocation<ArrayCreationExpr.State> location) {
		super(location);
	}

	public static STree<ArrayCreationExpr.State> make(Type type, NodeList<ArrayDimExpr> dimExprs, NodeList<ArrayDim> dims, NodeOption<ArrayInitializerExpr> init) {
		return new STree<ArrayCreationExpr.State>(kind, new ArrayCreationExpr.State(TreeBase.<Type.State>nodeOf(type), TreeBase.<SNodeListState>nodeOf(dimExprs), TreeBase.<SNodeListState>nodeOf(dims), TreeBase.<SNodeOptionState>nodeOf(init)));
	}

	public ArrayCreationExpr(Type type, NodeList<ArrayDimExpr> dimExprs, NodeList<ArrayDim> dims, NodeOption<ArrayInitializerExpr> init) {
		super(new SLocation<ArrayCreationExpr.State>(make(type, dimExprs, dims, init)));
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

	public ArrayCreationExpr withInit(NodeOption<ArrayInitializerExpr> initializer) {
		return location.safeTraversalReplace(INIT, initializer);
	}

	public ArrayCreationExpr withInit(Mutation<NodeOption<ArrayInitializerExpr>> mutation) {
		return location.safeTraversalMutate(INIT, mutation);
	}

	private static final STraversal<ArrayCreationExpr.State> TYPE = new STraversal<ArrayCreationExpr.State>() {

		public STree<?> traverse(ArrayCreationExpr.State state) {
			return state.type;
		}

		public ArrayCreationExpr.State rebuildParentState(ArrayCreationExpr.State state, STree<?> child) {
			return state.withType((STree) child);
		}

		public STraversal<ArrayCreationExpr.State> leftSibling(ArrayCreationExpr.State state) {
			return null;
		}

		public STraversal<ArrayCreationExpr.State> rightSibling(ArrayCreationExpr.State state) {
			return DIM_EXPRS;
		}
	};
	private static final STraversal<ArrayCreationExpr.State> DIM_EXPRS = new STraversal<ArrayCreationExpr.State>() {

		public STree<?> traverse(ArrayCreationExpr.State state) {
			return state.dimExprs;
		}

		public ArrayCreationExpr.State rebuildParentState(ArrayCreationExpr.State state, STree<?> child) {
			return state.withDimExprs((STree) child);
		}

		public STraversal<ArrayCreationExpr.State> leftSibling(ArrayCreationExpr.State state) {
			return TYPE;
		}

		public STraversal<ArrayCreationExpr.State> rightSibling(ArrayCreationExpr.State state) {
			return DIMS;
		}
	};
	private static final STraversal<ArrayCreationExpr.State> DIMS = new STraversal<ArrayCreationExpr.State>() {

		public STree<?> traverse(ArrayCreationExpr.State state) {
			return state.dims;
		}

		public ArrayCreationExpr.State rebuildParentState(ArrayCreationExpr.State state, STree<?> child) {
			return state.withDims((STree) child);
		}

		public STraversal<ArrayCreationExpr.State> leftSibling(ArrayCreationExpr.State state) {
			return DIM_EXPRS;
		}

		public STraversal<ArrayCreationExpr.State> rightSibling(ArrayCreationExpr.State state) {
			return INIT;
		}
	};
	private static final STraversal<ArrayCreationExpr.State> INIT = new STraversal<ArrayCreationExpr.State>() {

		public STree<?> traverse(ArrayCreationExpr.State state) {
			return state.init;
		}

		public ArrayCreationExpr.State rebuildParentState(ArrayCreationExpr.State state, STree<?> child) {
			return state.withInit((STree) child);
		}

		public STraversal<ArrayCreationExpr.State> leftSibling(ArrayCreationExpr.State state) {
			return DIMS;
		}

		public STraversal<ArrayCreationExpr.State> rightSibling(ArrayCreationExpr.State state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			token(LToken.New),
			child(TYPE),
			child(DIM_EXPRS, list()),
			child(DIMS, list()),
			when(childIs(INIT, some()),
					composite(
							none().withSpacingAfter(space()),
							child(INIT, element())
					)
			)
	);

	public static class State extends SNodeState<State> {

		public final STree<Type.State> type;

		public final STree<SNodeListState> dimExprs;

		public final STree<SNodeListState> dims;

		public final STree<SNodeOptionState> init;

		State(STree<Type.State> type, STree<SNodeListState> dimExprs, STree<SNodeListState> dims, STree<SNodeOptionState> init) {
			this.type = type;
			this.dimExprs = dimExprs;
			this.dims = dims;
			this.init = init;
		}

		public ArrayCreationExpr.State withType(STree<Type.State> type) {
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

		public STraversal<ArrayCreationExpr.State> firstChild() {
			return null;
		}

		public STraversal<ArrayCreationExpr.State> lastChild() {
			return null;
		}

		public Tree instantiate(SLocation<ArrayCreationExpr.State> location) {
			return new ArrayCreationExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	}
}
