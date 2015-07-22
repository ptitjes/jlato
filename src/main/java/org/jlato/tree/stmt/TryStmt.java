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
import org.jlato.tree.*;
import org.jlato.tree.expr.VariableDeclarationExpr;

import static org.jlato.internal.shapes.LSCondition.childIs;
import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class TryStmt extends TreeBase<TryStmt.State, Stmt, TryStmt> implements Stmt {

	public Kind kind() {
		return Kind.TryStmt;
	}

	private TryStmt(SLocation<TryStmt.State> location) {
		super(location);
	}

	public static STree<TryStmt.State> make(STree<SNodeListState> resources, STree<BlockStmt.State> tryBlock, STree<SNodeListState> catchs, STree<SNodeOptionState> finallyBlock) {
		return new STree<TryStmt.State>(new TryStmt.State(resources, tryBlock, catchs, finallyBlock));
	}

	public TryStmt(NodeList<VariableDeclarationExpr> resources, BlockStmt tryBlock, NodeList<CatchClause> catchs, NodeOption<BlockStmt> finallyBlock) {
		super(new SLocation<TryStmt.State>(make(TreeBase.<SNodeListState>treeOf(resources), TreeBase.<BlockStmt.State>treeOf(tryBlock), TreeBase.<SNodeListState>treeOf(catchs), TreeBase.<SNodeOptionState>treeOf(finallyBlock))));
	}

	public NodeList<VariableDeclarationExpr> resources() {
		return location.safeTraversal(RESOURCES);
	}

	public TryStmt withResources(NodeList<VariableDeclarationExpr> resources) {
		return location.safeTraversalReplace(RESOURCES, resources);
	}

	public TryStmt withResources(Mutation<NodeList<VariableDeclarationExpr>> mutation) {
		return location.safeTraversalMutate(RESOURCES, mutation);
	}

	public BlockStmt tryBlock() {
		return location.safeTraversal(TRY_BLOCK);
	}

	public TryStmt withTryBlock(BlockStmt tryBlock) {
		return location.safeTraversalReplace(TRY_BLOCK, tryBlock);
	}

	public TryStmt withTryBlock(Mutation<BlockStmt> mutation) {
		return location.safeTraversalMutate(TRY_BLOCK, mutation);
	}

	public NodeList<CatchClause> catchs() {
		return location.safeTraversal(CATCHS);
	}

	public TryStmt withCatchs(NodeList<CatchClause> catchs) {
		return location.safeTraversalReplace(CATCHS, catchs);
	}

	public TryStmt withCatchs(Mutation<NodeList<CatchClause>> mutation) {
		return location.safeTraversalMutate(CATCHS, mutation);
	}

	public NodeOption<BlockStmt> finallyBlock() {
		return location.safeTraversal(FINALLY_BLOCK);
	}

	public TryStmt withFinallyBlock(NodeOption<BlockStmt> finallyBlock) {
		return location.safeTraversalReplace(FINALLY_BLOCK, finallyBlock);
	}

	public TryStmt withFinallyBlock(Mutation<NodeOption<BlockStmt>> mutation) {
		return location.safeTraversalMutate(FINALLY_BLOCK, mutation);
	}

	public static class State extends SNodeState<State> implements Stmt.State {

		public final STree<SNodeListState> resources;

		public final STree<BlockStmt.State> tryBlock;

		public final STree<SNodeListState> catchs;

		public final STree<SNodeOptionState> finallyBlock;

		State(STree<SNodeListState> resources, STree<BlockStmt.State> tryBlock, STree<SNodeListState> catchs, STree<SNodeOptionState> finallyBlock) {
			this.resources = resources;
			this.tryBlock = tryBlock;
			this.catchs = catchs;
			this.finallyBlock = finallyBlock;
		}

		public TryStmt.State withResources(STree<SNodeListState> resources) {
			return new TryStmt.State(resources, tryBlock, catchs, finallyBlock);
		}

		public TryStmt.State withTryBlock(STree<BlockStmt.State> tryBlock) {
			return new TryStmt.State(resources, tryBlock, catchs, finallyBlock);
		}

		public TryStmt.State withCatchs(STree<SNodeListState> catchs) {
			return new TryStmt.State(resources, tryBlock, catchs, finallyBlock);
		}

		public TryStmt.State withFinallyBlock(STree<SNodeOptionState> finallyBlock) {
			return new TryStmt.State(resources, tryBlock, catchs, finallyBlock);
		}

		@Override
		public Kind kind() {
			return Kind.TryStmt;
		}

		@Override
		protected Tree doInstantiate(SLocation<TryStmt.State> location) {
			return new TryStmt(location);
		}

		@Override
		public LexicalShape shape() {
			return shape;
		}

		@Override
		public STraversal firstChild() {
			return RESOURCES;
		}

		@Override
		public STraversal lastChild() {
			return FINALLY_BLOCK;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			TryStmt.State state = (TryStmt.State) o;
			if (!resources.equals(state.resources))
				return false;
			if (tryBlock == null ? state.tryBlock != null : !tryBlock.equals(state.tryBlock))
				return false;
			if (!catchs.equals(state.catchs))
				return false;
			if (!finallyBlock.equals(state.finallyBlock))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			result = 37 * result + resources.hashCode();
			if (tryBlock != null) result = 37 * result + tryBlock.hashCode();
			result = 37 * result + catchs.hashCode();
			result = 37 * result + finallyBlock.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<TryStmt.State, SNodeListState, NodeList<VariableDeclarationExpr>> RESOURCES = new STypeSafeTraversal<TryStmt.State, SNodeListState, NodeList<VariableDeclarationExpr>>() {

		@Override
		protected STree<?> doTraverse(TryStmt.State state) {
			return state.resources;
		}

		@Override
		protected TryStmt.State doRebuildParentState(TryStmt.State state, STree<SNodeListState> child) {
			return state.withResources(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return TRY_BLOCK;
		}
	};

	private static STypeSafeTraversal<TryStmt.State, BlockStmt.State, BlockStmt> TRY_BLOCK = new STypeSafeTraversal<TryStmt.State, BlockStmt.State, BlockStmt>() {

		@Override
		protected STree<?> doTraverse(TryStmt.State state) {
			return state.tryBlock;
		}

		@Override
		protected TryStmt.State doRebuildParentState(TryStmt.State state, STree<BlockStmt.State> child) {
			return state.withTryBlock(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return RESOURCES;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return CATCHS;
		}
	};

	private static STypeSafeTraversal<TryStmt.State, SNodeListState, NodeList<CatchClause>> CATCHS = new STypeSafeTraversal<TryStmt.State, SNodeListState, NodeList<CatchClause>>() {

		@Override
		protected STree<?> doTraverse(TryStmt.State state) {
			return state.catchs;
		}

		@Override
		protected TryStmt.State doRebuildParentState(TryStmt.State state, STree<SNodeListState> child) {
			return state.withCatchs(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return TRY_BLOCK;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return FINALLY_BLOCK;
		}
	};

	private static STypeSafeTraversal<TryStmt.State, SNodeOptionState, NodeOption<BlockStmt>> FINALLY_BLOCK = new STypeSafeTraversal<TryStmt.State, SNodeOptionState, NodeOption<BlockStmt>>() {

		@Override
		protected STree<?> doTraverse(TryStmt.State state) {
			return state.finallyBlock;
		}

		@Override
		protected TryStmt.State doRebuildParentState(TryStmt.State state, STree<SNodeOptionState> child) {
			return state.withFinallyBlock(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return CATCHS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			token(LToken.Try).withSpacingAfter(space()),
			child(RESOURCES, VariableDeclarationExpr.resourcesShape),
			child(TRY_BLOCK),
			child(CATCHS, CatchClause.listShape),
			when(childIs(FINALLY_BLOCK, some()), composite(
					token(LToken.Finally).withSpacing(space(), space()),
					child(FINALLY_BLOCK, element())
			))
	);
}
