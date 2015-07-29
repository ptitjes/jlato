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

import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class ArrayInitializerExpr extends TreeBase<ArrayInitializerExpr.State, Expr, ArrayInitializerExpr> implements Expr {

	public Kind kind() {
		return Kind.ArrayInitializerExpr;
	}

	private ArrayInitializerExpr(SLocation<ArrayInitializerExpr.State> location) {
		super(location);
	}

	public static STree<ArrayInitializerExpr.State> make(STree<SNodeListState> values, boolean trailingComma) {
		return new STree<ArrayInitializerExpr.State>(new ArrayInitializerExpr.State(values, trailingComma));
	}

	public ArrayInitializerExpr(NodeList<Expr> values, boolean trailingComma) {
		super(new SLocation<ArrayInitializerExpr.State>(make(TreeBase.<SNodeListState>treeOf(values), trailingComma)));
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

	public boolean trailingComma() {
		return location.safeProperty(TRAILING_COMMA);
	}

	public ArrayInitializerExpr withTrailingComma(boolean trailingComma) {
		return location.safePropertyReplace(TRAILING_COMMA, trailingComma);
	}

	public ArrayInitializerExpr withTrailingComma(Mutation<Boolean> mutation) {
		return location.safePropertyMutate(TRAILING_COMMA, mutation);
	}

	public static class State extends SNodeState<State> implements Expr.State {

		public final STree<SNodeListState> values;
		public final boolean trailingComma;

		State(STree<SNodeListState> values, boolean trailingComma) {
			this.values = values;
			this.trailingComma = trailingComma;
		}

		public ArrayInitializerExpr.State withValues(STree<SNodeListState> values) {
			return new ArrayInitializerExpr.State(values, trailingComma);
		}

		public ArrayInitializerExpr.State withTrailingComma(boolean trailingComma) {
			return new ArrayInitializerExpr.State(values, trailingComma);
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
			State state = (State) o;
			if (!values.equals(state.values))
				return false;
			if (trailingComma != state.trailingComma)
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			result = 37 * result + values.hashCode();
			result = 37 * result + (trailingComma ? 1 : 0);
			return result;
		}
	}

	private static STypeSafeTraversal<ArrayInitializerExpr.State, SNodeListState, NodeList<Expr>> VALUES = new STypeSafeTraversal<ArrayInitializerExpr.State, SNodeListState, NodeList<Expr>>() {

		@Override
		public STree<?> doTraverse(State state) {
			return state.values;
		}

		@Override
		public ArrayInitializerExpr.State doRebuildParentState(State state, STree<SNodeListState> child) {
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

	private static STypeSafeProperty<ArrayInitializerExpr.State, Boolean> TRAILING_COMMA = new STypeSafeProperty<ArrayInitializerExpr.State, Boolean>() {

		@Override
		public Boolean doRetrieve(State state) {
			return state.trailingComma;
		}

		@Override
		public ArrayInitializerExpr.State doRebuildParentState(State state, Boolean value) {
			return state.withTrailingComma(value);
		}
	};

	public final static LexicalShape shape = composite(
			alternative(childIs(VALUES, not(empty())), composite(
					token(LToken.BraceLeft).withSpacingAfter(space()),
					child(VALUES, Expr.listShape),
					when(data(TRAILING_COMMA), token(LToken.Comma)),
					token(LToken.BraceRight).withSpacingBefore(space())
			), composite(
					token(LToken.BraceLeft),
					when(data(TRAILING_COMMA), token(LToken.Comma)),
					token(LToken.BraceRight)
			))
	);
}
