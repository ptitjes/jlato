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
import org.jlato.internal.td.SKind;
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
import org.jlato.internal.td.*;

public class ArrayCreationExpr extends TreeBase<ArrayCreationExpr.State, Expr, ArrayCreationExpr> implements Expr {

	public final static SKind<ArrayCreationExpr.State> kind = new SKind<ArrayCreationExpr.State>() {
		public ArrayCreationExpr instantiate(SLocation<ArrayCreationExpr.State> location) {
			return new ArrayCreationExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private ArrayCreationExpr(SLocation<ArrayCreationExpr.State> location) {
		super(location);
	}

	public static STree<ArrayCreationExpr.State> make(Type type, NodeList<ArrayDimExpr> dimExprs, NodeList<ArrayDim> dims, NodeOption<ArrayInitializerExpr> initializer) {
		return new STree<ArrayCreationExpr.State>(kind, new ArrayCreationExpr.State(TreeBase.<Type.State>nodeOf(type), TreeBase.<SNodeListState>nodeOf(dimExprs), TreeBase.<SNodeListState>nodeOf(dims), TreeBase.<SNodeOptionState>nodeOf(initializer)));
	}

	public ArrayCreationExpr(Type type, NodeList<ArrayDimExpr> dimExprs, NodeList<ArrayDim> dims, NodeOption<ArrayInitializerExpr> initializer) {
		super(new SLocation<ArrayCreationExpr.State>(make(type, dimExprs, dims, initializer)));
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
		return location.safeTraversal(DIMENSION_EXPRESSIONS);
	}

	public ArrayCreationExpr withDimExprs(NodeList<ArrayDimExpr> dimExprs) {
		return location.safeTraversalReplace(DIMENSION_EXPRESSIONS, dimExprs);
	}

	public ArrayCreationExpr withDimExprs(Mutation<NodeList<ArrayDimExpr>> mutation) {
		return location.safeTraversalMutate(DIMENSION_EXPRESSIONS, mutation);
	}

	public NodeList<ArrayDim> dims() {
		return location.safeTraversal(DIMENSIONS);
	}

	public ArrayCreationExpr withDims(NodeList<ArrayDim> dims) {
		return location.safeTraversalReplace(DIMENSIONS, dims);
	}

	public ArrayCreationExpr withDims(Mutation<NodeList<ArrayDim>> mutation) {
		return location.safeTraversalMutate(DIMENSIONS, mutation);
	}

	public NodeOption<ArrayInitializerExpr> init() {
		return location.safeTraversal(INITIALIZER);
	}

	public ArrayCreationExpr withInit(NodeOption<ArrayInitializerExpr> initializer) {
		return location.safeTraversalReplace(INITIALIZER, initializer);
	}

	public ArrayCreationExpr withInit(Mutation<NodeOption<ArrayInitializerExpr>> mutation) {
		return location.safeTraversalMutate(INITIALIZER, mutation);
	}

	private static final STraversal<ArrayCreationExpr.State> TYPE = SNodeState.childTraversal(0);
	private static final STraversal<ArrayCreationExpr.State> DIMENSION_EXPRESSIONS = SNodeState.childTraversal(1);
	private static final STraversal<ArrayCreationExpr.State> DIMENSIONS = SNodeState.childTraversal(2);
	private static final STraversal<ArrayCreationExpr.State> INITIALIZER = SNodeState.childTraversal(3);

	public final static LexicalShape shape = composite(
			token(LToken.New),
			child(TYPE),
			child(DIMENSION_EXPRESSIONS, list()),
			child(DIMENSIONS, list()),
			when(childIs(INITIALIZER, some()),
					composite(
							none().withSpacingAfter(space()),
							child(INITIALIZER, element())
					)
			)
	);

	public static class State extends SNodeState<State> {

		public final STree<Type.State> type;

		public final STree<SNodeListState> dimExprs;

		public final STree<SNodeListState> dims;

		public final STree<SNodeOptionState> initializer;

		State(STree<Type.State> type, STree<SNodeListState> dimExprs, STree<SNodeListState> dims, STree<SNodeOptionState> initializer) {
			this.type = type;
			this.dimExprs = dimExprs;
			this.dims = dims;
			this.initializer = initializer;
		}

		public ArrayCreationExpr.State withType(STree<Type.State> type) {
			return new ArrayCreationExpr.State(type, dimExprs, dims, initializer);
		}

		public ArrayCreationExpr.State withDimExprs(STree<SNodeListState> dimExprs) {
			return new ArrayCreationExpr.State(type, dimExprs, dims, initializer);
		}

		public ArrayCreationExpr.State withDims(STree<SNodeListState> dims) {
			return new ArrayCreationExpr.State(type, dimExprs, dims, initializer);
		}

		public ArrayCreationExpr.State withInitializer(STree<SNodeOptionState> initializer) {
			return new ArrayCreationExpr.State(type, dimExprs, dims, initializer);
		}

		public STraversal<ArrayCreationExpr.State> firstChild() {
			return null;
		}

		public STraversal<ArrayCreationExpr.State> lastChild() {
			return null;
		}
	}
}
