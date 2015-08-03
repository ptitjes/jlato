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
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.util.Mutation;

import static org.jlato.internal.shapes.LSCondition.childIs;
import static org.jlato.internal.shapes.LSCondition.empty;
import static org.jlato.internal.shapes.LSCondition.not;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.BLOCK;
import static org.jlato.printer.IndentationConstraint.indent;
import static org.jlato.printer.IndentationConstraint.unIndent;
import static org.jlato.printer.SpacingConstraint.newLine;

public class BlockStmt extends TreeBase<BlockStmt.State, Stmt, BlockStmt> implements Stmt {

	public Kind kind() {
		return Kind.BlockStmt;
	}

	private BlockStmt(SLocation<BlockStmt.State> location) {
		super(location);
	}

	public static STree<BlockStmt.State> make(STree<SNodeListState> stmts) {
		return new STree<BlockStmt.State>(new BlockStmt.State(stmts));
	}

	public BlockStmt(NodeList<Stmt> stmts) {
		super(new SLocation<BlockStmt.State>(make(TreeBase.<SNodeListState>treeOf(stmts))));
	}

	public NodeList<Stmt> stmts() {
		return location.safeTraversal(STMTS);
	}

	public BlockStmt withStmts(NodeList<Stmt> stmts) {
		return location.safeTraversalReplace(STMTS, stmts);
	}

	public BlockStmt withStmts(Mutation<NodeList<Stmt>> mutation) {
		return location.safeTraversalMutate(STMTS, mutation);
	}

	public static class State extends SNodeState<State> implements Stmt.State {

		public final STree<SNodeListState> stmts;

		State(STree<SNodeListState> stmts) {
			this.stmts = stmts;
		}

		public BlockStmt.State withStmts(STree<SNodeListState> stmts) {
			return new BlockStmt.State(stmts);
		}

		@Override
		public Kind kind() {
			return Kind.BlockStmt;
		}

		@Override
		protected Tree doInstantiate(SLocation<BlockStmt.State> location) {
			return new BlockStmt(location);
		}

		@Override
		public LexicalShape shape() {
			return shape;
		}

		@Override
		public STraversal firstChild() {
			return STMTS;
		}

		@Override
		public STraversal lastChild() {
			return STMTS;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			State state = (State) o;
			if (!stmts.equals(state.stmts))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			result = 37 * result + stmts.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<BlockStmt.State, SNodeListState, NodeList<Stmt>> STMTS = new STypeSafeTraversal<BlockStmt.State, SNodeListState, NodeList<Stmt>>() {

		@Override
		public STree<?> doTraverse(BlockStmt.State state) {
			return state.stmts;
		}

		@Override
		public BlockStmt.State doRebuildParentState(BlockStmt.State state, STree<SNodeListState> child) {
			return state.withStmts(child);
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

	public static final LexicalShape shape = alternative(childIs(STMTS, not(empty())),
			composite(
					token(LToken.BraceLeft)
							.withSpacingAfter(newLine())
							.withIndentationAfter(indent(BLOCK)),
					child(STMTS, listShape),
					token(LToken.BraceRight)
							.withIndentationBefore(unIndent(BLOCK))
							.withSpacingBefore(newLine())
			),
			composite(
					token(LToken.BraceLeft)
							.withSpacingAfter(newLine())
							.withIndentationAfter(indent(BLOCK)),
					token(LToken.BraceRight)
							.withIndentationBefore(unIndent(BLOCK))
			)
	);
}
