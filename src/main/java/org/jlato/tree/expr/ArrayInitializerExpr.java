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

public class ArrayInitializerExpr extends TreeBase<ArrayInitializerExpr.State, Expr, ArrayInitializerExpr> implements Expr {

	public Kind kind() {
		return Kind.ArrayInitializerExpr;
	}

	private ArrayInitializerExpr(SLocation<ArrayInitializerExpr.State> location) {
		super(location);
	}

	public static STree<ArrayInitializerExpr.State> make(STree<SNodeListState> values) {
		return new STree<ArrayInitializerExpr.State>(new ArrayInitializerExpr.State(values));
	}

	public ArrayInitializerExpr(NodeList<Expr> values) {
		super(new SLocation<ArrayInitializerExpr.State>(make(TreeBase.<SNodeListState>treeOf(values))));
	}

	public NodeList<Expr> values() {
		return location.safeTraversal(VALUES);
	}

	public ArrayInitializerExpr withValues(NodeList<Expr> values) {
		return location.safeTraversalReplace(VALUES, values);
	}

	public ArrayInitializerExpr withValues(Mutation<NodeList<Expr>> mutation) {
		return location.safeTraversalMutate(VALUES, mutation);
	}

	public static class State extends SNodeState<State> implements Expr.State {

		public final STree<SNodeListState> values;

		State(STree<SNodeListState> values) {
			this.values = values;
		}

		public ArrayInitializerExpr.State withValues(STree<SNodeListState> values) {
			return new ArrayInitializerExpr.State(values);
		}

		@Override
		public Kind kind() {
			return Kind.ArrayInitializerExpr;
		}

		@Override
		protected Tree doInstantiate(SLocation<ArrayInitializerExpr.State> location) {
			return new ArrayInitializerExpr(location);
		}

		@Override
		public LexicalShape shape() {
			return shape;
		}

		@Override
		public STraversal firstChild() {
			return VALUES;
		}

		@Override
		public STraversal lastChild() {
			return VALUES;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			ArrayInitializerExpr.State state = (ArrayInitializerExpr.State) o;
			if (!values.equals(state.values))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			result = 37 * result + values.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<ArrayInitializerExpr.State, SNodeListState, NodeList<Expr>> VALUES = new STypeSafeTraversal<ArrayInitializerExpr.State, SNodeListState, NodeList<Expr>>() {

		@Override
		protected STree<?> doTraverse(ArrayInitializerExpr.State state) {
			return state.values;
		}

		@Override
		protected ArrayInitializerExpr.State doRebuildParentState(ArrayInitializerExpr.State state, STree<SNodeListState> child) {
			return state.withValues(child);
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

	public final static LexicalShape shape = composite(
			nonEmptyChildren(VALUES,
					composite(
							token(LToken.BraceLeft).withSpacingAfter(space()),
							child(VALUES, Expr.listShape),
							token(LToken.BraceRight).withSpacingBefore(space())
					),
					composite(token(LToken.BraceLeft), token(LToken.BraceRight))
			)
	);
}
