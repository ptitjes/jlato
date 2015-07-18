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
import org.jlato.internal.td.SKind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;
import org.jlato.tree.expr.Expr;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

import org.jlato.tree.Tree;

public class SynchronizedStmt extends TreeBase<SynchronizedStmt.State, Stmt, SynchronizedStmt> implements Stmt {

	public final static SKind<SynchronizedStmt.State> kind = new SKind<SynchronizedStmt.State>() {

	};

	private SynchronizedStmt(SLocation<SynchronizedStmt.State> location) {
		super(location);
	}

	public static STree<SynchronizedStmt.State> make(Expr expr, BlockStmt block) {
		return new STree<SynchronizedStmt.State>(kind, new SynchronizedStmt.State(TreeBase.<Expr.State>nodeOf(expr), TreeBase.<BlockStmt.State>nodeOf(block)));
	}

	public SynchronizedStmt(Expr expr, BlockStmt block) {
		super(new SLocation<SynchronizedStmt.State>(make(expr, block)));
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

	private static final STraversal<SynchronizedStmt.State> EXPR = new STraversal<SynchronizedStmt.State>() {

		public STree<?> traverse(SynchronizedStmt.State state) {
			return state.expr;
		}

		public SynchronizedStmt.State rebuildParentState(SynchronizedStmt.State state, STree<?> child) {
			return state.withExpr((STree) child);
		}

		public STraversal<SynchronizedStmt.State> leftSibling(SynchronizedStmt.State state) {
			return null;
		}

		public STraversal<SynchronizedStmt.State> rightSibling(SynchronizedStmt.State state) {
			return BLOCK;
		}
	};
	private static final STraversal<SynchronizedStmt.State> BLOCK = new STraversal<SynchronizedStmt.State>() {

		public STree<?> traverse(SynchronizedStmt.State state) {
			return state.block;
		}

		public SynchronizedStmt.State rebuildParentState(SynchronizedStmt.State state, STree<?> child) {
			return state.withBlock((STree) child);
		}

		public STraversal<SynchronizedStmt.State> leftSibling(SynchronizedStmt.State state) {
			return EXPR;
		}

		public STraversal<SynchronizedStmt.State> rightSibling(SynchronizedStmt.State state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			token(LToken.Synchronized),
			token(LToken.ParenthesisLeft).withSpacingBefore(space()),
			child(EXPR),
			token(LToken.ParenthesisRight).withSpacingAfter(space()),
			child(BLOCK)
	);

	public static class State extends SNodeState<State> {

		public final STree<Expr.State> expr;

		public final STree<BlockStmt.State> block;

		State(STree<Expr.State> expr, STree<BlockStmt.State> block) {
			this.expr = expr;
			this.block = block;
		}

		public SynchronizedStmt.State withExpr(STree<Expr.State> expr) {
			return new SynchronizedStmt.State(expr, block);
		}

		public SynchronizedStmt.State withBlock(STree<BlockStmt.State> block) {
			return new SynchronizedStmt.State(expr, block);
		}

		public STraversal<SynchronizedStmt.State> firstChild() {
			return null;
		}

		public STraversal<SynchronizedStmt.State> lastChild() {
			return null;
		}

		public Tree instantiate(SLocation<SynchronizedStmt.State> location) {
			return new SynchronizedStmt(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	}
}
