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

package org.jlato.tree.stmt;

import org.jlato.internal.bu.*;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.Mutation;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class WhileStmt extends TreeBase<WhileStmt.State, Stmt, WhileStmt> implements Stmt {

	public Kind kind() {
		return Kind.WhileStmt;
	}

	private WhileStmt(SLocation<WhileStmt.State> location) {
		super(location);
	}

	public static STree<WhileStmt.State> make(STree<? extends Expr.State> condition, STree<? extends Stmt.State> body) {
		return new STree<WhileStmt.State>(new WhileStmt.State(condition, body));
	}

	public WhileStmt(Expr condition, Stmt body) {
		super(new SLocation<WhileStmt.State>(make(TreeBase.<Expr.State>nodeOf(condition), TreeBase.<Stmt.State>nodeOf(body))));
	}

	public Expr condition() {
		return location.safeTraversal(CONDITION);
	}

	public WhileStmt withCondition(Expr condition) {
		return location.safeTraversalReplace(CONDITION, condition);
	}

	public WhileStmt withCondition(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(CONDITION, mutation);
	}

	public Stmt body() {
		return location.safeTraversal(BODY);
	}

	public WhileStmt withBody(Stmt body) {
		return location.safeTraversalReplace(BODY, body);
	}

	public WhileStmt withBody(Mutation<Stmt> mutation) {
		return location.safeTraversalMutate(BODY, mutation);
	}

	public static class State extends SNodeState<State> implements Stmt.State {

		public final STree<? extends Expr.State> condition;

		public final STree<? extends Stmt.State> body;

		State(STree<? extends Expr.State> condition, STree<? extends Stmt.State> body) {
			this.condition = condition;
			this.body = body;
		}

		public WhileStmt.State withCondition(STree<? extends Expr.State> condition) {
			return new WhileStmt.State(condition, body);
		}

		public WhileStmt.State withBody(STree<? extends Stmt.State> body) {
			return new WhileStmt.State(condition, body);
		}

		@Override
		public Kind kind() {
			return Kind.WhileStmt;
		}

		@Override
		protected Tree doInstantiate(SLocation<WhileStmt.State> location) {
			return new WhileStmt(location);
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
			return BODY;
		}
	}

	private static STypeSafeTraversal<WhileStmt.State, Expr.State, Expr> CONDITION = new STypeSafeTraversal<WhileStmt.State, Expr.State, Expr>() {

		@Override
		protected STree<?> doTraverse(WhileStmt.State state) {
			return state.condition;
		}

		@Override
		protected WhileStmt.State doRebuildParentState(WhileStmt.State state, STree<Expr.State> child) {
			return state.withCondition(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return BODY;
		}
	};

	private static STypeSafeTraversal<WhileStmt.State, Stmt.State, Stmt> BODY = new STypeSafeTraversal<WhileStmt.State, Stmt.State, Stmt>() {

		@Override
		protected STree<?> doTraverse(WhileStmt.State state) {
			return state.body;
		}

		@Override
		protected WhileStmt.State doRebuildParentState(WhileStmt.State state, STree<Stmt.State> child) {
			return state.withBody(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return CONDITION;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			token(LToken.While), token(LToken.ParenthesisLeft).withSpacingBefore(space()),
			child(CONDITION),
			token(LToken.ParenthesisRight).withSpacingAfter(space()),
			child(BODY)
	);
}
