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
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;
import org.jlato.util.Mutation;

import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class ReturnStmt extends TreeBase<ReturnStmt.State, Stmt, ReturnStmt> implements Stmt {

	public Kind kind() {
		return Kind.ReturnStmt;
	}

	private ReturnStmt(SLocation<ReturnStmt.State> location) {
		super(location);
	}

	public static STree<ReturnStmt.State> make(STree<SNodeOptionState> expr) {
		return new STree<ReturnStmt.State>(new ReturnStmt.State(expr));
	}

	public ReturnStmt(NodeOption<Expr> expr) {
		super(new SLocation<ReturnStmt.State>(make(TreeBase.<SNodeOptionState>treeOf(expr))));
	}

	public NodeOption<Expr> expr() {
		return location.safeTraversal(EXPR);
	}

	public ReturnStmt withExpr(NodeOption<Expr> expr) {
		return location.safeTraversalReplace(EXPR, expr);
	}

	public ReturnStmt withExpr(Mutation<NodeOption<Expr>> mutation) {
		return location.safeTraversalMutate(EXPR, mutation);
	}

	public static class State extends SNodeState<State> implements Stmt.State {

		public final STree<SNodeOptionState> expr;

		State(STree<SNodeOptionState> expr) {
			this.expr = expr;
		}

		public ReturnStmt.State withExpr(STree<SNodeOptionState> expr) {
			return new ReturnStmt.State(expr);
		}

		@Override
		public Kind kind() {
			return Kind.ReturnStmt;
		}

		@Override
		protected Tree doInstantiate(SLocation<ReturnStmt.State> location) {
			return new ReturnStmt(location);
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
			if (!expr.equals(state.expr))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			result = 37 * result + expr.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<ReturnStmt.State, SNodeOptionState, NodeOption<Expr>> EXPR = new STypeSafeTraversal<ReturnStmt.State, SNodeOptionState, NodeOption<Expr>>() {

		@Override
		public STree<?> doTraverse(ReturnStmt.State state) {
			return state.expr;
		}

		@Override
		public ReturnStmt.State doRebuildParentState(ReturnStmt.State state, STree<SNodeOptionState> child) {
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
			token(LToken.Return),
			child(EXPR, when(some(), element().withSpacingBefore(space()))),
			token(LToken.SemiColon)
	);
}
