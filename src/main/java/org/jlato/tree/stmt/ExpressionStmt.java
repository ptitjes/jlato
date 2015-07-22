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

public class ExpressionStmt extends TreeBase<ExpressionStmt.State, Stmt, ExpressionStmt> implements Stmt {

	public Kind kind() {
		return Kind.ExpressionStmt;
	}

	private ExpressionStmt(SLocation<ExpressionStmt.State> location) {
		super(location);
	}

	public static STree<ExpressionStmt.State> make(STree<? extends Expr.State> expr) {
		return new STree<ExpressionStmt.State>(new ExpressionStmt.State(expr));
	}

	public ExpressionStmt(Expr expr) {
		super(new SLocation<ExpressionStmt.State>(make(TreeBase.<Expr.State>treeOf(expr))));
	}

	public Expr expr() {
		return location.safeTraversal(EXPR);
	}

	public ExpressionStmt withExpr(Expr expr) {
		return location.safeTraversalReplace(EXPR, expr);
	}

	public ExpressionStmt withExpr(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(EXPR, mutation);
	}

	public static class State extends SNodeState<State> implements Stmt.State {

		public final STree<? extends Expr.State> expr;

		State(STree<? extends Expr.State> expr) {
			this.expr = expr;
		}

		public ExpressionStmt.State withExpr(STree<? extends Expr.State> expr) {
			return new ExpressionStmt.State(expr);
		}

		@Override
		public Kind kind() {
			return Kind.ExpressionStmt;
		}

		@Override
		protected Tree doInstantiate(SLocation<ExpressionStmt.State> location) {
			return new ExpressionStmt(location);
		}

		@Override
		public LexicalShape shape() {
			return shape;
		}

		@Override
		public STraversal firstChild() {
			return EXPR;
		}

		@Override
		public STraversal lastChild() {
			return EXPR;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			ExpressionStmt.State state = (ExpressionStmt.State) o;
			if (expr == null ? state.expr != null : !expr.equals(state.expr))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			if (expr != null) result = 37 * result + expr.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<ExpressionStmt.State, Expr.State, Expr> EXPR = new STypeSafeTraversal<ExpressionStmt.State, Expr.State, Expr>() {

		@Override
		protected STree<?> doTraverse(ExpressionStmt.State state) {
			return state.expr;
		}

		@Override
		protected ExpressionStmt.State doRebuildParentState(ExpressionStmt.State state, STree<Expr.State> child) {
			return state.withExpr(child);
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
			child(EXPR), token(LToken.SemiColon)
	);
}
