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
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.util.Mutation;

import static org.jlato.internal.shapes.LexicalShape.*;

public class ArrayDim extends TreeBase<ArrayDim.State, Tree, ArrayDim> implements Tree {

	public Kind kind() {
		return Kind.ArrayDim;
	}

	private ArrayDim(SLocation<ArrayDim.State> location) {
		super(location);
	}

	public static STree<ArrayDim.State> make(STree<SNodeListState> annotations) {
		return new STree<ArrayDim.State>(new ArrayDim.State(annotations));
	}

	public ArrayDim(NodeList<AnnotationExpr> annotations) {
		super(new SLocation<ArrayDim.State>(make(TreeBase.<SNodeListState>treeOf(annotations))));
	}

	public NodeList<AnnotationExpr> annotations() {
		return location.safeTraversal(ANNOTATIONS);
	}

	public ArrayDim withAnnotations(NodeList<AnnotationExpr> annotations) {
		return location.safeTraversalReplace(ANNOTATIONS, annotations);
	}

	public ArrayDim withAnnotations(Mutation<NodeList<AnnotationExpr>> mutation) {
		return location.safeTraversalMutate(ANNOTATIONS, mutation);
	}

	public static class State extends SNodeState<State> implements STreeState {

		public final STree<SNodeListState> annotations;

		State(STree<SNodeListState> annotations) {
			this.annotations = annotations;
		}

		public ArrayDim.State withAnnotations(STree<SNodeListState> annotations) {
			return new ArrayDim.State(annotations);
		}

		@Override
		public Kind kind() {
			return Kind.ArrayDim;
		}

		@Override
		protected Tree doInstantiate(SLocation<ArrayDim.State> location) {
			return new ArrayDim(location);
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
			return ANNOTATIONS;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			State state = (State) o;
			if (!annotations.equals(state.annotations))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			result = 37 * result + annotations.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<ArrayDim.State, SNodeListState, NodeList<AnnotationExpr>> ANNOTATIONS = new STypeSafeTraversal<ArrayDim.State, SNodeListState, NodeList<AnnotationExpr>>() {

		@Override
		public STree<?> doTraverse(ArrayDim.State state) {
			return state.annotations;
		}

		@Override
		public ArrayDim.State doRebuildParentState(ArrayDim.State state, STree<SNodeListState> child) {
			return state.withAnnotations(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(ANNOTATIONS, AnnotationExpr.singleLineAnnotationsShapeWithSpaceBefore),
			token(LToken.BracketLeft), token(LToken.BracketRight)
	);

	public static final LexicalShape listShape = list();
}
