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
import org.jlato.tree.Kind;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class ArrayDimExpr extends TreeBase<ArrayDimExpr.State, Tree, ArrayDimExpr> implements Tree {

	public Kind kind() {
		return Kind.ArrayDimExpr;
	}

	private ArrayDimExpr(SLocation<ArrayDimExpr.State> location) {
		super(location);
	}

	public static STree<ArrayDimExpr.State> make(STree<SNodeListState> annotations, STree<? extends Expr.State> expr) {
		return new STree<ArrayDimExpr.State>(new ArrayDimExpr.State(annotations, expr));
	}

	public ArrayDimExpr(NodeList<AnnotationExpr> annotations, Expr expr) {
		super(new SLocation<ArrayDimExpr.State>(make(TreeBase.<SNodeListState>nodeOf(annotations), TreeBase.<Expr.State>nodeOf(expr))));
	}

	public NodeList<AnnotationExpr> annotations() {
		return location.safeTraversal(ANNOTATIONS);
	}

	public ArrayDimExpr withAnnotations(NodeList<AnnotationExpr> annotations) {
		return location.safeTraversalReplace(ANNOTATIONS, annotations);
	}

	public ArrayDimExpr withAnnotations(Mutation<NodeList<AnnotationExpr>> mutation) {
		return location.safeTraversalMutate(ANNOTATIONS, mutation);
	}

	public Expr expr() {
		return location.safeTraversal(EXPR);
	}

	public ArrayDimExpr withExpr(Expr expr) {
		return location.safeTraversalReplace(EXPR, expr);
	}

	public ArrayDimExpr withExpr(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(EXPR, mutation);
	}

	public static class State extends SNodeState<State> implements STreeState {

		public final STree<SNodeListState> annotations;

		public final STree<? extends Expr.State> expr;

		State(STree<SNodeListState> annotations, STree<? extends Expr.State> expr) {
			this.annotations = annotations;
			this.expr = expr;
		}

		public ArrayDimExpr.State withAnnotations(STree<SNodeListState> annotations) {
			return new ArrayDimExpr.State(annotations, expr);
		}

		public ArrayDimExpr.State withExpr(STree<? extends Expr.State> expr) {
			return new ArrayDimExpr.State(annotations, expr);
		}

		@Override
		public Kind kind() {
			return Kind.ArrayDimExpr;
		}

		@Override
		protected Tree doInstantiate(SLocation<ArrayDimExpr.State> location) {
			return new ArrayDimExpr(location);
		}

		@Override
		public LexicalShape shape() {
			return shape;
		}

		@Override
		public STraversal firstChild() {
			return ANNOTATIONS;
		}

		@Override
		public STraversal lastChild() {
			return EXPR;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			ArrayDimExpr.State state = (ArrayDimExpr.State) o;
			if (!annotations.equals(state.annotations))
				return false;
			if (!expr.equals(state.expr))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			result = 37 * result + annotations.hashCode();
			result = 37 * result + expr.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<ArrayDimExpr.State, SNodeListState, NodeList<AnnotationExpr>> ANNOTATIONS = new STypeSafeTraversal<ArrayDimExpr.State, SNodeListState, NodeList<AnnotationExpr>>() {

		@Override
		protected STree<?> doTraverse(ArrayDimExpr.State state) {
			return state.annotations;
		}

		@Override
		protected ArrayDimExpr.State doRebuildParentState(ArrayDimExpr.State state, STree<SNodeListState> child) {
			return state.withAnnotations(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return EXPR;
		}
	};

	private static STypeSafeTraversal<ArrayDimExpr.State, Expr.State, Expr> EXPR = new STypeSafeTraversal<ArrayDimExpr.State, Expr.State, Expr>() {

		@Override
		protected STree<?> doTraverse(ArrayDimExpr.State state) {
			return state.expr;
		}

		@Override
		protected ArrayDimExpr.State doRebuildParentState(ArrayDimExpr.State state, STree<Expr.State> child) {
			return state.withExpr(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return ANNOTATIONS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			child(ANNOTATIONS, list(
					none().withSpacingBefore(space()),
					none().withSpacingBefore(space()),
					none().withSpacingBefore(space())
			)),
			token(LToken.BracketLeft), child(EXPR), token(LToken.BracketRight)
	);

	public static final LexicalShape listShape = list();
}
