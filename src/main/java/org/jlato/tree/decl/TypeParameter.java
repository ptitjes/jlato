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
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class TypeParameter extends TreeBase<TypeParameter.State, Tree, TypeParameter> implements Tree {

	public Kind kind() {
		return Kind.TypeParameter;
	}

	private TypeParameter(SLocation<TypeParameter.State> location) {
		super(location);
	}

	public static STree<TypeParameter.State> make(STree<SNodeListState> annotations, STree<Name.State> name, STree<SNodeListState> bounds) {
		return new STree<TypeParameter.State>(new TypeParameter.State(annotations, name, bounds));
	}

	public TypeParameter(NodeList<AnnotationExpr> annotations, Name name, NodeList<Type> bounds) {
		super(new SLocation<TypeParameter.State>(make(TreeBase.<SNodeListState>treeOf(annotations), TreeBase.<Name.State>treeOf(name), TreeBase.<SNodeListState>treeOf(bounds))));
	}

	public NodeList<AnnotationExpr> annotations() {
		return location.safeTraversal(ANNOTATIONS);
	}

	public TypeParameter withAnnotations(NodeList<AnnotationExpr> annotations) {
		return location.safeTraversalReplace(ANNOTATIONS, annotations);
	}

	public TypeParameter withAnnotations(Mutation<NodeList<AnnotationExpr>> mutation) {
		return location.safeTraversalMutate(ANNOTATIONS, mutation);
	}

	public Name name() {
		return location.safeTraversal(NAME);
	}

	public TypeParameter withName(Name name) {
		return location.safeTraversalReplace(NAME, name);
	}

	public TypeParameter withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(NAME, mutation);
	}

	public NodeList<Type> bounds() {
		return location.safeTraversal(BOUNDS);
	}

	public TypeParameter withBounds(NodeList<Type> bounds) {
		return location.safeTraversalReplace(BOUNDS, bounds);
	}

	public TypeParameter withBounds(Mutation<NodeList<Type>> mutation) {
		return location.safeTraversalMutate(BOUNDS, mutation);
	}

	public static class State extends SNodeState<State> implements STreeState {

		public final STree<SNodeListState> annotations;

		public final STree<Name.State> name;

		public final STree<SNodeListState> bounds;

		State(STree<SNodeListState> annotations, STree<Name.State> name, STree<SNodeListState> bounds) {
			this.annotations = annotations;
			this.name = name;
			this.bounds = bounds;
		}

		public TypeParameter.State withAnnotations(STree<SNodeListState> annotations) {
			return new TypeParameter.State(annotations, name, bounds);
		}

		public TypeParameter.State withName(STree<Name.State> name) {
			return new TypeParameter.State(annotations, name, bounds);
		}

		public TypeParameter.State withBounds(STree<SNodeListState> bounds) {
			return new TypeParameter.State(annotations, name, bounds);
		}

		@Override
		public Kind kind() {
			return Kind.TypeParameter;
		}

		@Override
		protected Tree doInstantiate(SLocation<TypeParameter.State> location) {
			return new TypeParameter(location);
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
			return BOUNDS;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			TypeParameter.State state = (TypeParameter.State) o;
			if (!annotations.equals(state.annotations))
				return false;
			if (!name.equals(state.name))
				return false;
			if (!bounds.equals(state.bounds))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			result = 37 * result + annotations.hashCode();
			result = 37 * result + name.hashCode();
			result = 37 * result + bounds.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<TypeParameter.State, SNodeListState, NodeList<AnnotationExpr>> ANNOTATIONS = new STypeSafeTraversal<TypeParameter.State, SNodeListState, NodeList<AnnotationExpr>>() {

		@Override
		protected STree<?> doTraverse(TypeParameter.State state) {
			return state.annotations;
		}

		@Override
		protected TypeParameter.State doRebuildParentState(TypeParameter.State state, STree<SNodeListState> child) {
			return state.withAnnotations(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return NAME;
		}
	};

	private static STypeSafeTraversal<TypeParameter.State, Name.State, Name> NAME = new STypeSafeTraversal<TypeParameter.State, Name.State, Name>() {

		@Override
		protected STree<?> doTraverse(TypeParameter.State state) {
			return state.name;
		}

		@Override
		protected TypeParameter.State doRebuildParentState(TypeParameter.State state, STree<Name.State> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return ANNOTATIONS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return BOUNDS;
		}
	};

	private static STypeSafeTraversal<TypeParameter.State, SNodeListState, NodeList<Type>> BOUNDS = new STypeSafeTraversal<TypeParameter.State, SNodeListState, NodeList<Type>>() {

		@Override
		protected STree<?> doTraverse(TypeParameter.State state) {
			return state.bounds;
		}

		@Override
		protected TypeParameter.State doRebuildParentState(TypeParameter.State state, STree<SNodeListState> child) {
			return state.withBounds(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return NAME;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public static final LexicalShape boundsShape = list(
			token(LToken.Extends).withSpacingBefore(space()),
			token(LToken.BinAnd).withSpacing(space(), space()),
			none()
	);

	public final static LexicalShape shape = composite(
			child(ANNOTATIONS, list()),
			child(NAME),
			child(BOUNDS, boundsShape)
	);

	public static final LexicalShape listShape = list(
			token(LToken.Less),
			token(LToken.Comma).withSpacingAfter(space()),
			token(LToken.Greater).withSpacingAfter(space())
	);
}
