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
import org.jlato.tree.expr.VariableDeclarationExpr;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class ForeachStmt extends TreeBase<ForeachStmt.State, Stmt, ForeachStmt> implements Stmt {

	public Kind kind() {
		return Kind.ForeachStmt;
	}

	private ForeachStmt(SLocation<ForeachStmt.State> location) {
		super(location);
	}

	public static STree<ForeachStmt.State> make(STree<VariableDeclarationExpr.State> var, STree<? extends Expr.State> iterable, STree<? extends Stmt.State> body) {
		return new STree<ForeachStmt.State>(new ForeachStmt.State(var, iterable, body));
	}

	public ForeachStmt(VariableDeclarationExpr var, Expr iterable, Stmt body) {
		super(new SLocation<ForeachStmt.State>(make(TreeBase.<VariableDeclarationExpr.State>treeOf(var), TreeBase.<Expr.State>treeOf(iterable), TreeBase.<Stmt.State>treeOf(body))));
	}

	public VariableDeclarationExpr var() {
		return location.safeTraversal(VAR);
	}

	public ForeachStmt withVar(VariableDeclarationExpr var) {
		return location.safeTraversalReplace(VAR, var);
	}

	public ForeachStmt withVar(Mutation<VariableDeclarationExpr> mutation) {
		return location.safeTraversalMutate(VAR, mutation);
	}

	public Expr iterable() {
		return location.safeTraversal(ITERABLE);
	}

	public ForeachStmt withIterable(Expr iterable) {
		return location.safeTraversalReplace(ITERABLE, iterable);
	}

	public ForeachStmt withIterable(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(ITERABLE, mutation);
	}

	public Stmt body() {
		return location.safeTraversal(BODY);
	}

	public ForeachStmt withBody(Stmt body) {
		return location.safeTraversalReplace(BODY, body);
	}

	public ForeachStmt withBody(Mutation<Stmt> mutation) {
		return location.safeTraversalMutate(BODY, mutation);
	}

	public static class State extends SNodeState<State> implements Stmt.State {

		public final STree<VariableDeclarationExpr.State> var;

		public final STree<? extends Expr.State> iterable;

		public final STree<? extends Stmt.State> body;

		State(STree<VariableDeclarationExpr.State> var, STree<? extends Expr.State> iterable, STree<? extends Stmt.State> body) {
			this.var = var;
			this.iterable = iterable;
			this.body = body;
		}

		public ForeachStmt.State withVar(STree<VariableDeclarationExpr.State> var) {
			return new ForeachStmt.State(var, iterable, body);
		}

		public ForeachStmt.State withIterable(STree<? extends Expr.State> iterable) {
			return new ForeachStmt.State(var, iterable, body);
		}

		public ForeachStmt.State withBody(STree<? extends Stmt.State> body) {
			return new ForeachStmt.State(var, iterable, body);
		}

		@Override
		public Kind kind() {
			return Kind.ForeachStmt;
		}

		@Override
		protected Tree doInstantiate(SLocation<ForeachStmt.State> location) {
			return new ForeachStmt(location);
		}

		@Override
		public LexicalShape shape() {
			return shape;
		}

		@Override
		public STraversal firstChild() {
			return VAR;
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
			State state = (State) o;
			if (var == null ? state.var != null : !var.equals(state.var))
				return false;
			if (iterable == null ? state.iterable != null : !iterable.equals(state.iterable))
				return false;
			if (body == null ? state.body != null : !body.equals(state.body))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			if (var != null) result = 37 * result + var.hashCode();
			if (iterable != null) result = 37 * result + iterable.hashCode();
			if (body != null) result = 37 * result + body.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<ForeachStmt.State, VariableDeclarationExpr.State, VariableDeclarationExpr> VAR = new STypeSafeTraversal<ForeachStmt.State, VariableDeclarationExpr.State, VariableDeclarationExpr>() {

		@Override
		public STree<?> doTraverse(ForeachStmt.State state) {
			return state.var;
		}

		@Override
		public ForeachStmt.State doRebuildParentState(ForeachStmt.State state, STree<VariableDeclarationExpr.State> child) {
			return state.withVar(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return ITERABLE;
		}
	};

	private static STypeSafeTraversal<ForeachStmt.State, Expr.State, Expr> ITERABLE = new STypeSafeTraversal<ForeachStmt.State, Expr.State, Expr>() {

		@Override
		public STree<?> doTraverse(ForeachStmt.State state) {
			return state.iterable;
		}

		@Override
		public ForeachStmt.State doRebuildParentState(ForeachStmt.State state, STree<Expr.State> child) {
			return state.withIterable(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return VAR;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return BODY;
		}
	};

	private static STypeSafeTraversal<ForeachStmt.State, Stmt.State, Stmt> BODY = new STypeSafeTraversal<ForeachStmt.State, Stmt.State, Stmt>() {

		@Override
		public STree<?> doTraverse(ForeachStmt.State state) {
			return state.body;
		}

		@Override
		public ForeachStmt.State doRebuildParentState(ForeachStmt.State state, STree<Stmt.State> child) {
			return state.withBody(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return ITERABLE;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			keyword(LToken.For), token(LToken.ParenthesisLeft).withSpacingBefore(space()),
			child(VAR),
			token(LToken.Colon).withSpacing(space(), space()),
			child(ITERABLE),
			token(LToken.ParenthesisRight).withSpacingAfter(space()),
			child(BODY)
	);
}
