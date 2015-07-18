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

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.Kind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;
import org.jlato.tree.expr.Expr;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

import org.jlato.tree.Tree;

public class WhileStmt extends TreeBase<WhileStmt.State, Stmt, WhileStmt> implements Stmt {

	public final static Kind kind = new Kind() {

	};

	private WhileStmt(SLocation<WhileStmt.State> location) {
		super(location);
	}

	public static STree<WhileStmt.State> make(Expr condition, Stmt body) {
		return new STree<WhileStmt.State>(kind, new WhileStmt.State(TreeBase.<Expr.State>nodeOf(condition), TreeBase.<Stmt.State>nodeOf(body)));
	}

	public WhileStmt(Expr condition, Stmt body) {
		super(new SLocation<WhileStmt.State>(make(condition, body)));
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

	private static final STraversal<WhileStmt.State> CONDITION = new STraversal<WhileStmt.State>() {

		public STree<?> traverse(WhileStmt.State state) {
			return state.condition;
		}

		public WhileStmt.State rebuildParentState(WhileStmt.State state, STree<?> child) {
			return state.withCondition((STree) child);
		}

		public STraversal<WhileStmt.State> leftSibling(WhileStmt.State state) {
			return null;
		}

		public STraversal<WhileStmt.State> rightSibling(WhileStmt.State state) {
			return BODY;
		}
	};
	private static final STraversal<WhileStmt.State> BODY = new STraversal<WhileStmt.State>() {

		public STree<?> traverse(WhileStmt.State state) {
			return state.body;
		}

		public WhileStmt.State rebuildParentState(WhileStmt.State state, STree<?> child) {
			return state.withBody((STree) child);
		}

		public STraversal<WhileStmt.State> leftSibling(WhileStmt.State state) {
			return CONDITION;
		}

		public STraversal<WhileStmt.State> rightSibling(WhileStmt.State state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			token(LToken.While), token(LToken.ParenthesisLeft).withSpacingBefore(space()),
			child(CONDITION),
			token(LToken.ParenthesisRight).withSpacingAfter(space()),
			child(BODY)
	);

	public static class State extends SNodeState<State> {

		public final STree<Expr.State> condition;

		public final STree<Stmt.State> body;

		State(STree<Expr.State> condition, STree<Stmt.State> body) {
			this.condition = condition;
			this.body = body;
		}

		public WhileStmt.State withCondition(STree<Expr.State> condition) {
			return new WhileStmt.State(condition, body);
		}

		public WhileStmt.State withBody(STree<Stmt.State> body) {
			return new WhileStmt.State(condition, body);
		}

		public STraversal<WhileStmt.State> firstChild() {
			return null;
		}

		public STraversal<WhileStmt.State> lastChild() {
			return null;
		}

		public Tree instantiate(SLocation<WhileStmt.State> location) {
			return new WhileStmt(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	}
}
