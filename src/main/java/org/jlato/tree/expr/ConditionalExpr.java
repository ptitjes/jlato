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
import org.jlato.tree.Tree;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class ConditionalExpr extends TreeBase<ConditionalExpr.State, Expr, ConditionalExpr> implements Expr {

	public Kind kind() {
		return Kind.ConditionalExpr;
	}

	private ConditionalExpr(SLocation<ConditionalExpr.State> location) {
		super(location);
	}

	public static STree<ConditionalExpr.State> make(STree<? extends Expr.State> condition, STree<? extends Expr.State> thenExpr, STree<? extends Expr.State> elseExpr) {
		return new STree<ConditionalExpr.State>(new ConditionalExpr.State(condition, thenExpr, elseExpr));
	}

	public ConditionalExpr(Expr condition, Expr thenExpr, Expr elseExpr) {
		super(new SLocation<ConditionalExpr.State>(make(TreeBase.<Expr.State>treeOf(condition), TreeBase.<Expr.State>treeOf(thenExpr), TreeBase.<Expr.State>treeOf(elseExpr))));
	}

	public Expr condition() {
		return location.safeTraversal(CONDITION);
	}

	public ConditionalExpr withCondition(Expr condition) {
		return location.safeTraversalReplace(CONDITION, condition);
	}

	public ConditionalExpr withCondition(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(CONDITION, mutation);
	}

	public Expr thenExpr() {
		return location.safeTraversal(THEN_EXPR);
	}

	public ConditionalExpr withThenExpr(Expr thenExpr) {
		return location.safeTraversalReplace(THEN_EXPR, thenExpr);
	}

	public ConditionalExpr withThenExpr(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(THEN_EXPR, mutation);
	}

	public Expr elseExpr() {
		return location.safeTraversal(ELSE_EXPR);
	}

	public ConditionalExpr withElseExpr(Expr elseExpr) {
		return location.safeTraversalReplace(ELSE_EXPR, elseExpr);
	}

	public ConditionalExpr withElseExpr(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(ELSE_EXPR, mutation);
	}

	public static class State extends SNodeState<State> implements Expr.State {

		public final STree<? extends Expr.State> condition;

		public final STree<? extends Expr.State> thenExpr;

		public final STree<? extends Expr.State> elseExpr;

		State(STree<? extends Expr.State> condition, STree<? extends Expr.State> thenExpr, STree<? extends Expr.State> elseExpr) {
			this.condition = condition;
			this.thenExpr = thenExpr;
			this.elseExpr = elseExpr;
		}

		public ConditionalExpr.State withCondition(STree<? extends Expr.State> condition) {
			return new ConditionalExpr.State(condition, thenExpr, elseExpr);
		}

		public ConditionalExpr.State withThenExpr(STree<? extends Expr.State> thenExpr) {
			return new ConditionalExpr.State(condition, thenExpr, elseExpr);
		}

		public ConditionalExpr.State withElseExpr(STree<? extends Expr.State> elseExpr) {
			return new ConditionalExpr.State(condition, thenExpr, elseExpr);
		}

		@Override
		public Kind kind() {
			return Kind.ConditionalExpr;
		}

		@Override
		protected Tree doInstantiate(SLocation<ConditionalExpr.State> location) {
			return new ConditionalExpr(location);
		}

		@Override
		public LexicalShape shape() {
			return shape;
		}

		@Override
		public STraversal firstChild() {
			return CONDITION;
		}

		@Override
		public STraversal lastChild() {
			return ELSE_EXPR;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			ConditionalExpr.State state = (ConditionalExpr.State) o;
			if (condition == null ? state.condition != null : !condition.equals(state.condition))
				return false;
			if (thenExpr == null ? state.thenExpr != null : !thenExpr.equals(state.thenExpr))
				return false;
			if (elseExpr == null ? state.elseExpr != null : !elseExpr.equals(state.elseExpr))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			if (condition != null) result = 37 * result + condition.hashCode();
			if (thenExpr != null) result = 37 * result + thenExpr.hashCode();
			if (elseExpr != null) result = 37 * result + elseExpr.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<ConditionalExpr.State, Expr.State, Expr> CONDITION = new STypeSafeTraversal<ConditionalExpr.State, Expr.State, Expr>() {

		@Override
		public STree<?> doTraverse(State state) {
			return state.condition;
		}

		@Override
		public ConditionalExpr.State doRebuildParentState(State state, STree<Expr.State> child) {
			return state.withCondition(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return THEN_EXPR;
		}
	};

	private static STypeSafeTraversal<ConditionalExpr.State, Expr.State, Expr> THEN_EXPR = new STypeSafeTraversal<ConditionalExpr.State, Expr.State, Expr>() {

		@Override
		public STree<?> doTraverse(State state) {
			return state.thenExpr;
		}

		@Override
		public ConditionalExpr.State doRebuildParentState(State state, STree<Expr.State> child) {
			return state.withThenExpr(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return CONDITION;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return ELSE_EXPR;
		}
	};

	private static STypeSafeTraversal<ConditionalExpr.State, Expr.State, Expr> ELSE_EXPR = new STypeSafeTraversal<ConditionalExpr.State, Expr.State, Expr>() {

		@Override
		public STree<?> doTraverse(State state) {
			return state.elseExpr;
		}

		@Override
		public ConditionalExpr.State doRebuildParentState(State state, STree<Expr.State> child) {
			return state.withElseExpr(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return THEN_EXPR;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			child(CONDITION),
			token(LToken.QuestionMark).withSpacing(space(), space()),
			child(THEN_EXPR),
			token(LToken.Colon).withSpacing(space(), space()),
			child(ELSE_EXPR)
	);
}
