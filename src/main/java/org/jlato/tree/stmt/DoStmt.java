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
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;
import org.jlato.util.Mutation;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class DoStmt extends TreeBase<DoStmt.State, Stmt, DoStmt> implements Stmt {

	public Kind kind() {
		return Kind.DoStmt;
	}

	private DoStmt(SLocation<DoStmt.State> location) {
		super(location);
	}

	public static STree<DoStmt.State> make(STree<? extends Stmt.State> body, STree<? extends Expr.State> condition) {
		return new STree<DoStmt.State>(new DoStmt.State(body, condition));
	}

	public DoStmt(Stmt body, Expr condition) {
		super(new SLocation<DoStmt.State>(make(TreeBase.<Stmt.State>treeOf(body), TreeBase.<Expr.State>treeOf(condition))));
	}

	public Stmt body() {
		return location.safeTraversal(BODY);
	}

	public DoStmt withBody(Stmt body) {
		return location.safeTraversalReplace(BODY, body);
	}

	public DoStmt withBody(Mutation<Stmt> mutation) {
		return location.safeTraversalMutate(BODY, mutation);
	}

	public Expr condition() {
		return location.safeTraversal(CONDITION);
	}

	public DoStmt withCondition(Expr condition) {
		return location.safeTraversalReplace(CONDITION, condition);
	}

	public DoStmt withCondition(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(CONDITION, mutation);
	}

	public static class State extends SNodeState<State> implements Stmt.State {

		public final STree<? extends Stmt.State> body;

		public final STree<? extends Expr.State> condition;

		State(STree<? extends Stmt.State> body, STree<? extends Expr.State> condition) {
			this.body = body;
			this.condition = condition;
		}

		public DoStmt.State withBody(STree<? extends Stmt.State> body) {
			return new DoStmt.State(body, condition);
		}

		public DoStmt.State withCondition(STree<? extends Expr.State> condition) {
			return new DoStmt.State(body, condition);
		}

		@Override
		public Kind kind() {
			return Kind.DoStmt;
		}

		@Override
		protected Tree doInstantiate(SLocation<DoStmt.State> location) {
			return new DoStmt(location);
		}

		@Override
		public LexicalShape shape() {
			return shape;
		}

		@Override
		public STraversal firstChild() {
			return BODY;
		}

		@Override
		public STraversal lastChild() {
			return CONDITION;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			State state = (State) o;
			if (body == null ? state.body != null : !body.equals(state.body))
				return false;
			if (condition == null ? state.condition != null : !condition.equals(state.condition))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			if (body != null) result = 37 * result + body.hashCode();
			if (condition != null) result = 37 * result + condition.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<DoStmt.State, Stmt.State, Stmt> BODY = new STypeSafeTraversal<DoStmt.State, Stmt.State, Stmt>() {

		@Override
		public STree<?> doTraverse(DoStmt.State state) {
			return state.body;
		}

		@Override
		public DoStmt.State doRebuildParentState(DoStmt.State state, STree<Stmt.State> child) {
			return state.withBody(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return CONDITION;
		}
	};

	private static STypeSafeTraversal<DoStmt.State, Expr.State, Expr> CONDITION = new STypeSafeTraversal<DoStmt.State, Expr.State, Expr>() {

		@Override
		public STree<?> doTraverse(DoStmt.State state) {
			return state.condition;
		}

		@Override
		public DoStmt.State doRebuildParentState(DoStmt.State state, STree<Expr.State> child) {
			return state.withCondition(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return BODY;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			keyword(LToken.Do),
			child(BODY),
			keyword(LToken.While),
			token(LToken.ParenthesisLeft).withSpacingBefore(space()),
			child(CONDITION),
			token(LToken.ParenthesisRight),
			token(LToken.SemiColon)
	);
}
