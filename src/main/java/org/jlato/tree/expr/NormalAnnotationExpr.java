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
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.name.QualifiedName;
import org.jlato.util.Mutation;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class NormalAnnotationExpr extends TreeBase<NormalAnnotationExpr.State, AnnotationExpr, NormalAnnotationExpr> implements AnnotationExpr {

	public Kind kind() {
		return Kind.NormalAnnotationExpr;
	}

	private NormalAnnotationExpr(SLocation<NormalAnnotationExpr.State> location) {
		super(location);
	}

	public static STree<NormalAnnotationExpr.State> make(STree<QualifiedName.State> name, STree<SNodeListState> pairs) {
		return new STree<NormalAnnotationExpr.State>(new NormalAnnotationExpr.State(name, pairs));
	}

	public NormalAnnotationExpr(QualifiedName name, NodeList<MemberValuePair> pairs) {
		super(new SLocation<NormalAnnotationExpr.State>(make(TreeBase.<QualifiedName.State>treeOf(name), TreeBase.<SNodeListState>treeOf(pairs))));
	}

	public QualifiedName name() {
		return location.safeTraversal(NAME);
	}

	public NormalAnnotationExpr withName(QualifiedName name) {
		return location.safeTraversalReplace(NAME, name);
	}

	public NormalAnnotationExpr withName(Mutation<QualifiedName> mutation) {
		return location.safeTraversalMutate(NAME, mutation);
	}

	public NodeList<MemberValuePair> pairs() {
		return location.safeTraversal(PAIRS);
	}

	public NormalAnnotationExpr withPairs(NodeList<MemberValuePair> pairs) {
		return location.safeTraversalReplace(PAIRS, pairs);
	}

	public NormalAnnotationExpr withPairs(Mutation<NodeList<MemberValuePair>> mutation) {
		return location.safeTraversalMutate(PAIRS, mutation);
	}

	public static class State extends SNodeState<State> implements AnnotationExpr.State {

		public final STree<QualifiedName.State> name;

		public final STree<SNodeListState> pairs;

		State(STree<QualifiedName.State> name, STree<SNodeListState> pairs) {
			this.name = name;
			this.pairs = pairs;
		}

		public NormalAnnotationExpr.State withName(STree<QualifiedName.State> name) {
			return new NormalAnnotationExpr.State(name, pairs);
		}

		public NormalAnnotationExpr.State withPairs(STree<SNodeListState> pairs) {
			return new NormalAnnotationExpr.State(name, pairs);
		}

		@Override
		public Kind kind() {
			return Kind.NormalAnnotationExpr;
		}

		@Override
		protected Tree doInstantiate(SLocation<NormalAnnotationExpr.State> location) {
			return new NormalAnnotationExpr(location);
		}

		@Override
		public LexicalShape shape() {
			return shape;
		}

		@Override
		public STraversal firstChild() {
			return NAME;
		}

		@Override
		public STraversal lastChild() {
			return PAIRS;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			State state = (State) o;
			if (name == null ? state.name != null : !name.equals(state.name))
				return false;
			if (!pairs.equals(state.pairs))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			if (name != null) result = 37 * result + name.hashCode();
			result = 37 * result + pairs.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<NormalAnnotationExpr.State, QualifiedName.State, QualifiedName> NAME = new STypeSafeTraversal<NormalAnnotationExpr.State, QualifiedName.State, QualifiedName>() {

		@Override
		public STree<?> doTraverse(NormalAnnotationExpr.State state) {
			return state.name;
		}

		@Override
		public NormalAnnotationExpr.State doRebuildParentState(NormalAnnotationExpr.State state, STree<QualifiedName.State> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return PAIRS;
		}
	};

	private static STypeSafeTraversal<NormalAnnotationExpr.State, SNodeListState, NodeList<MemberValuePair>> PAIRS = new STypeSafeTraversal<NormalAnnotationExpr.State, SNodeListState, NodeList<MemberValuePair>>() {

		@Override
		public STree<?> doTraverse(NormalAnnotationExpr.State state) {
			return state.pairs;
		}

		@Override
		public NormalAnnotationExpr.State doRebuildParentState(NormalAnnotationExpr.State state, STree<SNodeListState> child) {
			return state.withPairs(child);
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

	public static final LexicalShape shape = composite(
			token(LToken.At), child(NAME),
			token(LToken.ParenthesisLeft),
			child(PAIRS, list(token(LToken.Comma).withSpacingAfter(space()))),
			token(LToken.ParenthesisRight)
	);
}
