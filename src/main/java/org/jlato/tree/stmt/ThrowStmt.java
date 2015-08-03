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

public class ThrowStmt extends TreeBase<ThrowStmt.State, Stmt, ThrowStmt> implements Stmt {

	public Kind kind() {
		return Kind.ThrowStmt;
	}

	private ThrowStmt(SLocation<ThrowStmt.State> location) {
		super(location);
	}

	public static STree<ThrowStmt.State> make(STree<? extends Expr.State> expr) {
		return new STree<ThrowStmt.State>(new ThrowStmt.State(expr));
	}

	public ThrowStmt(Expr expr) {
		super(new SLocation<ThrowStmt.State>(make(TreeBase.<Expr.State>treeOf(expr))));
	}

	public Expr expr() {
		return location.safeTraversal(EXPR);
	}

	public ThrowStmt withExpr(Expr expr) {
		return location.safeTraversalReplace(EXPR, expr);
	}

	public ThrowStmt withExpr(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(EXPR, mutation);
	}

	public static class State extends SNodeState<State> implements Stmt.State {

		public final STree<? extends Expr.State> expr;

		State(STree<? extends Expr.State> expr) {
			this.expr = expr;
		}

		public ThrowStmt.State withExpr(STree<? extends Expr.State> expr) {
			return new ThrowStmt.State(expr);
		}

		@Override
		public Kind kind() {
			return Kind.ThrowStmt;
		}

		@Override
		protected Tree doInstantiate(SLocation<ThrowStmt.State> location) {
			return new ThrowStmt(location);
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
			State state = (State) o;
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

	private static STypeSafeTraversal<ThrowStmt.State, Expr.State, Expr> EXPR = new STypeSafeTraversal<ThrowStmt.State, Expr.State, Expr>() {

		@Override
		public STree<?> doTraverse(ThrowStmt.State state) {
			return state.expr;
		}

		@Override
		public ThrowStmt.State doRebuildParentState(ThrowStmt.State state, STree<Expr.State> child) {
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

	public static final LexicalShape shape = composite(
			keyword(LToken.Throw), child(EXPR), token(LToken.SemiColon)
	);
}
