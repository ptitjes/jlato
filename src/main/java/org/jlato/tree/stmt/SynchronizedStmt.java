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

public class SynchronizedStmt extends TreeBase<SynchronizedStmt.State, Stmt, SynchronizedStmt> implements Stmt {

	public Kind kind() {
		return Kind.SynchronizedStmt;
	}

	private SynchronizedStmt(SLocation<SynchronizedStmt.State> location) {
		super(location);
	}

	public static STree<SynchronizedStmt.State> make(STree<? extends Expr.State> expr, STree<BlockStmt.State> block) {
		return new STree<SynchronizedStmt.State>(new SynchronizedStmt.State(expr, block));
	}

	public SynchronizedStmt(Expr expr, BlockStmt block) {
		super(new SLocation<SynchronizedStmt.State>(make(TreeBase.<Expr.State>treeOf(expr), TreeBase.<BlockStmt.State>treeOf(block))));
	}

	public Expr expr() {
		return location.safeTraversal(EXPR);
	}

	public SynchronizedStmt withExpr(Expr expr) {
		return location.safeTraversalReplace(EXPR, expr);
	}

	public SynchronizedStmt withExpr(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(EXPR, mutation);
	}

	public BlockStmt block() {
		return location.safeTraversal(BLOCK);
	}

	public SynchronizedStmt withBlock(BlockStmt block) {
		return location.safeTraversalReplace(BLOCK, block);
	}

	public SynchronizedStmt withBlock(Mutation<BlockStmt> mutation) {
		return location.safeTraversalMutate(BLOCK, mutation);
	}

	public static class State extends SNodeState<State> implements Stmt.State {

		public final STree<? extends Expr.State> expr;

		public final STree<BlockStmt.State> block;

		State(STree<? extends Expr.State> expr, STree<BlockStmt.State> block) {
			this.expr = expr;
			this.block = block;
		}

		public SynchronizedStmt.State withExpr(STree<? extends Expr.State> expr) {
			return new SynchronizedStmt.State(expr, block);
		}

		public SynchronizedStmt.State withBlock(STree<BlockStmt.State> block) {
			return new SynchronizedStmt.State(expr, block);
		}

		@Override
		public Kind kind() {
			return Kind.SynchronizedStmt;
		}

		@Override
		protected Tree doInstantiate(SLocation<SynchronizedStmt.State> location) {
			return new SynchronizedStmt(location);
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
			return BLOCK;
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
			if (block == null ? state.block != null : !block.equals(state.block))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			if (expr != null) result = 37 * result + expr.hashCode();
			if (block != null) result = 37 * result + block.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<SynchronizedStmt.State, Expr.State, Expr> EXPR = new STypeSafeTraversal<SynchronizedStmt.State, Expr.State, Expr>() {

		@Override
		public STree<?> doTraverse(SynchronizedStmt.State state) {
			return state.expr;
		}

		@Override
		public SynchronizedStmt.State doRebuildParentState(SynchronizedStmt.State state, STree<Expr.State> child) {
			return state.withExpr(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return BLOCK;
		}
	};

	private static STypeSafeTraversal<SynchronizedStmt.State, BlockStmt.State, BlockStmt> BLOCK = new STypeSafeTraversal<SynchronizedStmt.State, BlockStmt.State, BlockStmt>() {

		@Override
		public STree<?> doTraverse(SynchronizedStmt.State state) {
			return state.block;
		}

		@Override
		public SynchronizedStmt.State doRebuildParentState(SynchronizedStmt.State state, STree<BlockStmt.State> child) {
			return state.withBlock(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return EXPR;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			token(LToken.Synchronized),
			token(LToken.ParenthesisLeft).withSpacingBefore(space()),
			child(EXPR),
			token(LToken.ParenthesisRight).withSpacingAfter(space()),
			child(BLOCK)
	);
}
