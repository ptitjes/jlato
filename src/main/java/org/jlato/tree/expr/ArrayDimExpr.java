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
import org.jlato.tree.Tree;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;
import org.jlato.internal.bu.*;

public class ArrayDimExpr extends TreeBase<ArrayDimExpr.State, Tree, ArrayDimExpr> implements Tree {

	public final static SKind<ArrayDimExpr.State> kind = new SKind<ArrayDimExpr.State>() {
		public ArrayDimExpr instantiate(SLocation<ArrayDimExpr.State> location) {
			return new ArrayDimExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private ArrayDimExpr(SLocation<ArrayDimExpr.State> location) {
		super(location);
	}

	public static STree<ArrayDimExpr.State> make(NodeList<AnnotationExpr> annotations, Expr expr) {
		return new STree<ArrayDimExpr.State>(kind, new ArrayDimExpr.State(TreeBase.<SNodeListState>nodeOf(annotations), TreeBase.<Expr.State>nodeOf(expr)));
	}

	public ArrayDimExpr(NodeList<AnnotationExpr> annotations, Expr expr) {
		super(new SLocation<ArrayDimExpr.State>(make(annotations, expr)));
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

	private static final STraversal<ArrayDimExpr.State> ANNOTATIONS = new STraversal<ArrayDimExpr.State>() {

		public STree<?> traverse(ArrayDimExpr.State state) {
			return state.annotations;
		}

		public ArrayDimExpr.State rebuildParentState(ArrayDimExpr.State state, STree<?> child) {
			return state.withAnnotations((STree) child);
		}

		public STraversal<ArrayDimExpr.State> leftSibling(ArrayDimExpr.State state) {
			return null;
		}

		public STraversal<ArrayDimExpr.State> rightSibling(ArrayDimExpr.State state) {
			return EXPR;
		}
	};
	private static final STraversal<ArrayDimExpr.State> EXPR = new STraversal<ArrayDimExpr.State>() {

		public STree<?> traverse(ArrayDimExpr.State state) {
			return state.expr;
		}

		public ArrayDimExpr.State rebuildParentState(ArrayDimExpr.State state, STree<?> child) {
			return state.withExpr((STree) child);
		}

		public STraversal<ArrayDimExpr.State> leftSibling(ArrayDimExpr.State state) {
			return ANNOTATIONS;
		}

		public STraversal<ArrayDimExpr.State> rightSibling(ArrayDimExpr.State state) {
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

	public static class State extends SNodeState<State> {

		public final STree<SNodeListState> annotations;

		public final STree<Expr.State> expr;

		State(STree<SNodeListState> annotations, STree<Expr.State> expr) {
			this.annotations = annotations;
			this.expr = expr;
		}

		public ArrayDimExpr.State withAnnotations(STree<SNodeListState> annotations) {
			return new ArrayDimExpr.State(annotations, expr);
		}

		public ArrayDimExpr.State withExpr(STree<Expr.State> expr) {
			return new ArrayDimExpr.State(annotations, expr);
		}

		public STraversal<ArrayDimExpr.State> firstChild() {
			return null;
		}

		public STraversal<ArrayDimExpr.State> lastChild() {
			return null;
		}
	}
}
