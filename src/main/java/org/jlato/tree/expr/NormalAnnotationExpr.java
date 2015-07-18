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
import org.jlato.tree.name.QualifiedName;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;
import org.jlato.internal.bu.*;
import org.jlato.tree.Tree;

public class NormalAnnotationExpr extends TreeBase<NormalAnnotationExpr.State, AnnotationExpr, NormalAnnotationExpr> implements AnnotationExpr {

	public final static Kind kind = new Kind() {

	};

	private NormalAnnotationExpr(SLocation<NormalAnnotationExpr.State> location) {
		super(location);
	}

	public static STree<NormalAnnotationExpr.State> make(QualifiedName name, NodeList<MemberValuePair> pairs) {
		return new STree<NormalAnnotationExpr.State>(kind, new NormalAnnotationExpr.State(TreeBase.<QualifiedName.State>nodeOf(name), TreeBase.<SNodeListState>nodeOf(pairs)));
	}

	public NormalAnnotationExpr(QualifiedName name, NodeList<MemberValuePair> pairs) {
		super(new SLocation<NormalAnnotationExpr.State>(make(name, pairs)));
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

	private static final STraversal<NormalAnnotationExpr.State> NAME = new STraversal<NormalAnnotationExpr.State>() {

		public STree<?> traverse(NormalAnnotationExpr.State state) {
			return state.name;
		}

		public NormalAnnotationExpr.State rebuildParentState(NormalAnnotationExpr.State state, STree<?> child) {
			return state.withName((STree) child);
		}

		public STraversal<NormalAnnotationExpr.State> leftSibling(NormalAnnotationExpr.State state) {
			return null;
		}

		public STraversal<NormalAnnotationExpr.State> rightSibling(NormalAnnotationExpr.State state) {
			return PAIRS;
		}
	};
	private static final STraversal<NormalAnnotationExpr.State> PAIRS = new STraversal<NormalAnnotationExpr.State>() {

		public STree<?> traverse(NormalAnnotationExpr.State state) {
			return state.pairs;
		}

		public NormalAnnotationExpr.State rebuildParentState(NormalAnnotationExpr.State state, STree<?> child) {
			return state.withPairs((STree) child);
		}

		public STraversal<NormalAnnotationExpr.State> leftSibling(NormalAnnotationExpr.State state) {
			return NAME;
		}

		public STraversal<NormalAnnotationExpr.State> rightSibling(NormalAnnotationExpr.State state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			token(LToken.At), child(NAME),
			token(LToken.ParenthesisLeft),
			child(PAIRS, list(token(LToken.Comma).withSpacingAfter(space()))),
			token(LToken.ParenthesisRight)
	);

	public static class State extends SNodeState<State> {

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

		public STraversal<NormalAnnotationExpr.State> firstChild() {
			return null;
		}

		public STraversal<NormalAnnotationExpr.State> lastChild() {
			return null;
		}

		public Tree instantiate(SLocation<NormalAnnotationExpr.State> location) {
			return new NormalAnnotationExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	}
}
