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

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

import org.jlato.tree.Tree;

public class ConditionalExpr extends TreeBase<ConditionalExpr.State, Expr, ConditionalExpr> implements Expr {

	public final static SKind<ConditionalExpr.State> kind = new SKind<ConditionalExpr.State>() {

	};

	private ConditionalExpr(SLocation<ConditionalExpr.State> location) {
		super(location);
	}

	public static STree<ConditionalExpr.State> make(Expr condition, Expr thenExpr, Expr elseExpr) {
		return new STree<ConditionalExpr.State>(kind, new ConditionalExpr.State(TreeBase.<Expr.State>nodeOf(condition), TreeBase.<Expr.State>nodeOf(thenExpr), TreeBase.<Expr.State>nodeOf(elseExpr)));
	}

	public ConditionalExpr(Expr condition, Expr thenExpr, Expr elseExpr) {
		super(new SLocation<ConditionalExpr.State>(make(condition, thenExpr, elseExpr)));
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

	private static final STraversal<ConditionalExpr.State> CONDITION = new STraversal<ConditionalExpr.State>() {

		public STree<?> traverse(ConditionalExpr.State state) {
			return state.condition;
		}

		public ConditionalExpr.State rebuildParentState(ConditionalExpr.State state, STree<?> child) {
			return state.withCondition((STree) child);
		}

		public STraversal<ConditionalExpr.State> leftSibling(ConditionalExpr.State state) {
			return null;
		}

		public STraversal<ConditionalExpr.State> rightSibling(ConditionalExpr.State state) {
			return THEN_EXPR;
		}
	};
	private static final STraversal<ConditionalExpr.State> THEN_EXPR = new STraversal<ConditionalExpr.State>() {

		public STree<?> traverse(ConditionalExpr.State state) {
			return state.thenExpr;
		}

		public ConditionalExpr.State rebuildParentState(ConditionalExpr.State state, STree<?> child) {
			return state.withThenExpr((STree) child);
		}

		public STraversal<ConditionalExpr.State> leftSibling(ConditionalExpr.State state) {
			return CONDITION;
		}

		public STraversal<ConditionalExpr.State> rightSibling(ConditionalExpr.State state) {
			return ELSE_EXPR;
		}
	};
	private static final STraversal<ConditionalExpr.State> ELSE_EXPR = new STraversal<ConditionalExpr.State>() {

		public STree<?> traverse(ConditionalExpr.State state) {
			return state.elseExpr;
		}

		public ConditionalExpr.State rebuildParentState(ConditionalExpr.State state, STree<?> child) {
			return state.withElseExpr((STree) child);
		}

		public STraversal<ConditionalExpr.State> leftSibling(ConditionalExpr.State state) {
			return THEN_EXPR;
		}

		public STraversal<ConditionalExpr.State> rightSibling(ConditionalExpr.State state) {
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

	public static class State extends SNodeState<State> {

		public final STree<Expr.State> condition;

		public final STree<Expr.State> thenExpr;

		public final STree<Expr.State> elseExpr;

		State(STree<Expr.State> condition, STree<Expr.State> thenExpr, STree<Expr.State> elseExpr) {
			this.condition = condition;
			this.thenExpr = thenExpr;
			this.elseExpr = elseExpr;
		}

		public ConditionalExpr.State withCondition(STree<Expr.State> condition) {
			return new ConditionalExpr.State(condition, thenExpr, elseExpr);
		}

		public ConditionalExpr.State withThenExpr(STree<Expr.State> thenExpr) {
			return new ConditionalExpr.State(condition, thenExpr, elseExpr);
		}

		public ConditionalExpr.State withElseExpr(STree<Expr.State> elseExpr) {
			return new ConditionalExpr.State(condition, thenExpr, elseExpr);
		}

		public STraversal<ConditionalExpr.State> firstChild() {
			return null;
		}

		public STraversal<ConditionalExpr.State> lastChild() {
			return null;
		}

		public Tree instantiate(SLocation<ConditionalExpr.State> location) {
			return new ConditionalExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	}
}
