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
import org.jlato.tree.Tree;
import org.jlato.tree.expr.AnnotationExpr;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;
import org.jlato.internal.bu.*;

public class ArrayDim extends TreeBase<ArrayDim.State, Tree, ArrayDim> implements Tree {

	public Kind kind() {
		return Kind.ArrayDim;
	}

	private ArrayDim(SLocation<ArrayDim.State> location) {
		super(location);
	}

	public static STree<ArrayDim.State> make(NodeList<AnnotationExpr> annotations) {
		return new STree<ArrayDim.State>(new ArrayDim.State(TreeBase.<SNodeListState>nodeOf(annotations)));
	}

	public ArrayDim(NodeList<AnnotationExpr> annotations) {
		super(new SLocation<ArrayDim.State>(make(annotations)));
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

	private static final STraversal<ArrayDim.State> ANNOTATIONS = new STraversal<ArrayDim.State>() {

		public STree<?> traverse(ArrayDim.State state) {
			return state.annotations;
		}

		public ArrayDim.State rebuildParentState(ArrayDim.State state, STree<?> child) {
			return state.withAnnotations((STree) child);
		}

		public STraversal<ArrayDim.State> leftSibling(ArrayDim.State state) {
			return null;
		}

		public STraversal<ArrayDim.State> rightSibling(ArrayDim.State state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			child(ANNOTATIONS, list(
					none().withSpacingBefore(space()),
					none().withSpacingBefore(space()),
					none().withSpacingBefore(space())
			)),
			token(LToken.BracketLeft), token(LToken.BracketRight)
	);

	public static final LexicalShape listShape = list();

	public static class State extends SNodeState<State> {

		public final STree<SNodeListState> annotations;

		State(STree<SNodeListState> annotations) {
			this.annotations = annotations;
		}

		public ArrayDim.State withAnnotations(STree<SNodeListState> annotations) {
			return new ArrayDim.State(annotations);
		}

		public STraversal<ArrayDim.State> firstChild() {
			return null;
		}

		public STraversal<ArrayDim.State> lastChild() {
			return null;
		}

		public Tree instantiate(SLocation<ArrayDim.State> location) {
			return new ArrayDim(location);
		}

		public LexicalShape shape() {
			return shape;
		}

		public Kind kind() {
			return Kind.ArrayDim;
		}
	}
}
