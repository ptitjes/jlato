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
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class ForStmt extends TreeBase<ForStmt.State, Stmt, ForStmt> implements Stmt {

	public Kind kind() {
		return Kind.ForStmt;
	}

	private ForStmt(SLocation<ForStmt.State> location) {
		super(location);
	}

	public static STree<ForStmt.State> make(STree<SNodeListState> init, STree<? extends Expr.State> compare, STree<SNodeListState> update, STree<? extends Stmt.State> body) {
		return new STree<ForStmt.State>(new ForStmt.State(init, compare, update, body));
	}

	public ForStmt(NodeList<Expr> init, Expr compare, NodeList<Expr> update, Stmt body) {
		super(new SLocation<ForStmt.State>(make(TreeBase.<SNodeListState>nodeOf(init), TreeBase.<Expr.State>nodeOf(compare), TreeBase.<SNodeListState>nodeOf(update), TreeBase.<Stmt.State>nodeOf(body))));
	}

	public NodeList<Expr> init() {
		return location.safeTraversal(INIT);
	}

	public ForStmt withInit(NodeList<Expr> init) {
		return location.safeTraversalReplace(INIT, init);
	}

	public ForStmt withInit(Mutation<NodeList<Expr>> mutation) {
		return location.safeTraversalMutate(INIT, mutation);
	}

	public Expr compare() {
		return location.safeTraversal(COMPARE);
	}

	public ForStmt withCompare(Expr compare) {
		return location.safeTraversalReplace(COMPARE, compare);
	}

	public ForStmt withCompare(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(COMPARE, mutation);
	}

	public NodeList<Expr> update() {
		return location.safeTraversal(UPDATE);
	}

	public ForStmt withUpdate(NodeList<Expr> update) {
		return location.safeTraversalReplace(UPDATE, update);
	}

	public ForStmt withUpdate(Mutation<NodeList<Expr>> mutation) {
		return location.safeTraversalMutate(UPDATE, mutation);
	}

	public Stmt body() {
		return location.safeTraversal(BODY);
	}

	public ForStmt withBody(Stmt body) {
		return location.safeTraversalReplace(BODY, body);
	}

	public ForStmt withBody(Mutation<Stmt> mutation) {
		return location.safeTraversalMutate(BODY, mutation);
	}

	public static class State extends SNodeState<State> implements Stmt.State {

		public final STree<SNodeListState> init;

		public final STree<? extends Expr.State> compare;

		public final STree<SNodeListState> update;

		public final STree<? extends Stmt.State> body;

		State(STree<SNodeListState> init, STree<? extends Expr.State> compare, STree<SNodeListState> update, STree<? extends Stmt.State> body) {
			this.init = init;
			this.compare = compare;
			this.update = update;
			this.body = body;
		}

		public ForStmt.State withInit(STree<SNodeListState> init) {
			return new ForStmt.State(init, compare, update, body);
		}

		public ForStmt.State withCompare(STree<? extends Expr.State> compare) {
			return new ForStmt.State(init, compare, update, body);
		}

		public ForStmt.State withUpdate(STree<SNodeListState> update) {
			return new ForStmt.State(init, compare, update, body);
		}

		public ForStmt.State withBody(STree<? extends Stmt.State> body) {
			return new ForStmt.State(init, compare, update, body);
		}

		@Override
		public Kind kind() {
			return Kind.ForStmt;
		}

		@Override
		protected Tree doInstantiate(SLocation<ForStmt.State> location) {
			return new ForStmt(location);
		}

		@Override
		public LexicalShape shape() {
			return shape;
		}

		@Override
		public STraversal firstChild() {
			return INIT;
		}

		@Override
		public STraversal lastChild() {
			return BODY;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			ForStmt.State state = (ForStmt.State) o;
			if (!init.equals(state.init))
				return false;
			if (!compare.equals(state.compare))
				return false;
			if (!update.equals(state.update))
				return false;
			if (!body.equals(state.body))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			result = 37 * result + init.hashCode();
			result = 37 * result + compare.hashCode();
			result = 37 * result + update.hashCode();
			result = 37 * result + body.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<ForStmt.State, SNodeListState, NodeList<Expr>> INIT = new STypeSafeTraversal<ForStmt.State, SNodeListState, NodeList<Expr>>() {

		@Override
		protected STree<?> doTraverse(ForStmt.State state) {
			return state.init;
		}

		@Override
		protected ForStmt.State doRebuildParentState(ForStmt.State state, STree<SNodeListState> child) {
			return state.withInit(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return COMPARE;
		}
	};

	private static STypeSafeTraversal<ForStmt.State, Expr.State, Expr> COMPARE = new STypeSafeTraversal<ForStmt.State, Expr.State, Expr>() {

		@Override
		protected STree<?> doTraverse(ForStmt.State state) {
			return state.compare;
		}

		@Override
		protected ForStmt.State doRebuildParentState(ForStmt.State state, STree<Expr.State> child) {
			return state.withCompare(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return INIT;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return UPDATE;
		}
	};

	private static STypeSafeTraversal<ForStmt.State, SNodeListState, NodeList<Expr>> UPDATE = new STypeSafeTraversal<ForStmt.State, SNodeListState, NodeList<Expr>>() {

		@Override
		protected STree<?> doTraverse(ForStmt.State state) {
			return state.update;
		}

		@Override
		protected ForStmt.State doRebuildParentState(ForStmt.State state, STree<SNodeListState> child) {
			return state.withUpdate(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return COMPARE;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return BODY;
		}
	};

	private static STypeSafeTraversal<ForStmt.State, Stmt.State, Stmt> BODY = new STypeSafeTraversal<ForStmt.State, Stmt.State, Stmt>() {

		@Override
		protected STree<?> doTraverse(ForStmt.State state) {
			return state.body;
		}

		@Override
		protected ForStmt.State doRebuildParentState(ForStmt.State state, STree<Stmt.State> child) {
			return state.withBody(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return UPDATE;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			token(LToken.For), token(LToken.ParenthesisLeft).withSpacingBefore(space()),
			child(INIT, list(token(LToken.Comma).withSpacingAfter(space()))),
			token(LToken.SemiColon).withSpacingAfter(space()),
			child(COMPARE),
			token(LToken.SemiColon).withSpacingAfter(space()),
			child(UPDATE, list(token(LToken.Comma).withSpacingAfter(space()))),
			token(LToken.ParenthesisRight).withSpacingAfter(space()),
			child(BODY)
	);
}
