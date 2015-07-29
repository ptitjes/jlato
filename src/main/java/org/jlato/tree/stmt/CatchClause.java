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
import org.jlato.tree.decl.FormalParameter;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class CatchClause extends TreeBase<CatchClause.State, Tree, CatchClause> implements Tree {

	public Kind kind() {
		return Kind.CatchClause;
	}

	private CatchClause(SLocation<CatchClause.State> location) {
		super(location);
	}

	public static STree<CatchClause.State> make(STree<FormalParameter.State> except, STree<BlockStmt.State> catchBlock) {
		return new STree<CatchClause.State>(new CatchClause.State(except, catchBlock));
	}

	public CatchClause(FormalParameter except, BlockStmt catchBlock) {
		super(new SLocation<CatchClause.State>(make(TreeBase.<FormalParameter.State>treeOf(except), TreeBase.<BlockStmt.State>treeOf(catchBlock))));
	}

	public FormalParameter except() {
		return location.safeTraversal(EXCEPT);
	}

	public CatchClause withExcept(FormalParameter except) {
		return location.safeTraversalReplace(EXCEPT, except);
	}

	public CatchClause withExcept(Mutation<FormalParameter> mutation) {
		return location.safeTraversalMutate(EXCEPT, mutation);
	}

	public BlockStmt catchBlock() {
		return location.safeTraversal(CATCH_BLOCK);
	}

	public CatchClause withCatchBlock(BlockStmt catchBlock) {
		return location.safeTraversalReplace(CATCH_BLOCK, catchBlock);
	}

	public CatchClause withCatchBlock(Mutation<BlockStmt> mutation) {
		return location.safeTraversalMutate(CATCH_BLOCK, mutation);
	}

	public static class State extends SNodeState<State> implements STreeState {

		public final STree<FormalParameter.State> except;

		public final STree<BlockStmt.State> catchBlock;

		State(STree<FormalParameter.State> except, STree<BlockStmt.State> catchBlock) {
			this.except = except;
			this.catchBlock = catchBlock;
		}

		public CatchClause.State withExcept(STree<FormalParameter.State> except) {
			return new CatchClause.State(except, catchBlock);
		}

		public CatchClause.State withCatchBlock(STree<BlockStmt.State> catchBlock) {
			return new CatchClause.State(except, catchBlock);
		}

		@Override
		public Kind kind() {
			return Kind.CatchClause;
		}

		@Override
		protected Tree doInstantiate(SLocation<CatchClause.State> location) {
			return new CatchClause(location);
		}

		@Override
		public LexicalShape shape() {
			return shape;
		}

		@Override
		public STraversal firstChild() {
			return EXCEPT;
		}

		@Override
		public STraversal lastChild() {
			return CATCH_BLOCK;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			State state = (State) o;
			if (except == null ? state.except != null : !except.equals(state.except))
				return false;
			if (catchBlock == null ? state.catchBlock != null : !catchBlock.equals(state.catchBlock))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			if (except != null) result = 37 * result + except.hashCode();
			if (catchBlock != null) result = 37 * result + catchBlock.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<CatchClause.State, FormalParameter.State, FormalParameter> EXCEPT = new STypeSafeTraversal<CatchClause.State, FormalParameter.State, FormalParameter>() {

		@Override
		public STree<?> doTraverse(State state) {
			return state.except;
		}

		@Override
		public CatchClause.State doRebuildParentState(State state, STree<FormalParameter.State> child) {
			return state.withExcept(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return CATCH_BLOCK;
		}
	};

	private static STypeSafeTraversal<CatchClause.State, BlockStmt.State, BlockStmt> CATCH_BLOCK = new STypeSafeTraversal<CatchClause.State, BlockStmt.State, BlockStmt>() {

		@Override
		public STree<?> doTraverse(State state) {
			return state.catchBlock;
		}

		@Override
		public CatchClause.State doRebuildParentState(State state, STree<BlockStmt.State> child) {
			return state.withCatchBlock(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return EXCEPT;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			token(LToken.Catch).withSpacingBefore(space()),
			token(LToken.ParenthesisLeft).withSpacingBefore(space()),
			child(EXCEPT),
			token(LToken.ParenthesisRight).withSpacingAfter(space()),
			child(CATCH_BLOCK)
	);

	public static final LexicalShape listShape = list();
}
