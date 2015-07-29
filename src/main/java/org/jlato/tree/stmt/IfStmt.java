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
import org.jlato.internal.shapes.LSCondition;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;

import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.BLOCK;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;
import static org.jlato.printer.IndentationConstraint.indent;
import static org.jlato.printer.IndentationConstraint.unIndent;
import static org.jlato.printer.SpacingConstraint.*;

public class IfStmt extends TreeBase<IfStmt.State, Stmt, IfStmt> implements Stmt {

	public Kind kind() {
		return Kind.IfStmt;
	}

	private IfStmt(SLocation<IfStmt.State> location) {
		super(location);
	}

	public static STree<IfStmt.State> make(STree<? extends Expr.State> condition, STree<? extends Stmt.State> thenStmt, STree<SNodeOptionState> elseStmt) {
		return new STree<IfStmt.State>(new IfStmt.State(condition, thenStmt, elseStmt));
	}

	public IfStmt(Expr condition, Stmt thenStmt, NodeOption<Stmt> elseStmt) {
		super(new SLocation<IfStmt.State>(make(TreeBase.<Expr.State>treeOf(condition), TreeBase.<Stmt.State>treeOf(thenStmt), TreeBase.<SNodeOptionState>treeOf(elseStmt))));
	}

	public Expr condition() {
		return location.safeTraversal(CONDITION);
	}

	public IfStmt withCondition(Expr condition) {
		return location.safeTraversalReplace(CONDITION, condition);
	}

	public IfStmt withCondition(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(CONDITION, mutation);
	}

	public Stmt thenStmt() {
		return location.safeTraversal(THEN_STMT);
	}

	public IfStmt withThenStmt(Stmt thenStmt) {
		return location.safeTraversalReplace(THEN_STMT, thenStmt);
	}

	public IfStmt withThenStmt(Mutation<Stmt> mutation) {
		return location.safeTraversalMutate(THEN_STMT, mutation);
	}

	public NodeOption<Stmt> elseStmt() {
		return location.safeTraversal(ELSE_STMT);
	}

	public IfStmt withElseStmt(NodeOption<Stmt> elseStmt) {
		return location.safeTraversalReplace(ELSE_STMT, elseStmt);
	}

	public IfStmt withElseStmt(Mutation<NodeOption<Stmt>> mutation) {
		return location.safeTraversalMutate(ELSE_STMT, mutation);
	}

	public static class State extends SNodeState<State> implements Stmt.State {

		public final STree<? extends Expr.State> condition;

		public final STree<? extends Stmt.State> thenStmt;

		public final STree<SNodeOptionState> elseStmt;

		State(STree<? extends Expr.State> condition, STree<? extends Stmt.State> thenStmt, STree<SNodeOptionState> elseStmt) {
			this.condition = condition;
			this.thenStmt = thenStmt;
			this.elseStmt = elseStmt;
		}

		public IfStmt.State withCondition(STree<? extends Expr.State> condition) {
			return new IfStmt.State(condition, thenStmt, elseStmt);
		}

		public IfStmt.State withThenStmt(STree<? extends Stmt.State> thenStmt) {
			return new IfStmt.State(condition, thenStmt, elseStmt);
		}

		public IfStmt.State withElseStmt(STree<SNodeOptionState> elseStmt) {
			return new IfStmt.State(condition, thenStmt, elseStmt);
		}

		@Override
		public Kind kind() {
			return Kind.IfStmt;
		}

		@Override
		protected Tree doInstantiate(SLocation<IfStmt.State> location) {
			return new IfStmt(location);
		}

		@Override
		public LexicalShape shape() {
			return shape;
		}

		@Override
		public STraversal firstChild() {
			return CONDITION;
		}

		@Override
		public STraversal lastChild() {
			return ELSE_STMT;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			State state = (State) o;
			if (condition == null ? state.condition != null : !condition.equals(state.condition))
				return false;
			if (thenStmt == null ? state.thenStmt != null : !thenStmt.equals(state.thenStmt))
				return false;
			if (!elseStmt.equals(state.elseStmt))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			if (condition != null) result = 37 * result + condition.hashCode();
			if (thenStmt != null) result = 37 * result + thenStmt.hashCode();
			result = 37 * result + elseStmt.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<IfStmt.State, Expr.State, Expr> CONDITION = new STypeSafeTraversal<IfStmt.State, Expr.State, Expr>() {

		@Override
		public STree<?> doTraverse(State state) {
			return state.condition;
		}

		@Override
		public IfStmt.State doRebuildParentState(State state, STree<Expr.State> child) {
			return state.withCondition(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return THEN_STMT;
		}
	};

	private static STypeSafeTraversal<IfStmt.State, Stmt.State, Stmt> THEN_STMT = new STypeSafeTraversal<IfStmt.State, Stmt.State, Stmt>() {

		@Override
		public STree<?> doTraverse(State state) {
			return state.thenStmt;
		}

		@Override
		public IfStmt.State doRebuildParentState(State state, STree<Stmt.State> child) {
			return state.withThenStmt(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return CONDITION;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return ELSE_STMT;
		}
	};

	private static STypeSafeTraversal<IfStmt.State, SNodeOptionState, NodeOption<Stmt>> ELSE_STMT = new STypeSafeTraversal<IfStmt.State, SNodeOptionState, NodeOption<Stmt>>() {

		@Override
		public STree<?> doTraverse(State state) {
			return state.elseStmt;
		}

		@Override
		public IfStmt.State doRebuildParentState(State state, STree<SNodeOptionState> child) {
			return state.withElseStmt(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return THEN_STMT;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			token(LToken.If).withSpacingAfter(space()),
			token(LToken.ParenthesisLeft),
			child(CONDITION),
			token(LToken.ParenthesisRight),
			child(THEN_STMT,
					alternative(LSCondition.kind(Kind.BlockStmt),
							defaultShape().withSpacingBefore(space()),
							alternative(LSCondition.kind(Kind.ExpressionStmt),
									defaultShape()
											.withSpacingBefore(spacing(IfStmt_ThenExpressionStmt))
											.withIndentationBefore(indent(BLOCK))
											.withIndentationAfter(unIndent(BLOCK))
											.withSpacingAfter(newLine()),
									defaultShape()
											.withSpacingBefore(spacing(IfStmt_ThenOtherStmt))
											.withIndentationBefore(indent(BLOCK))
											.withIndentationAfter(unIndent(BLOCK))
											.withSpacingAfter(newLine())
							)
					)
			),
			child(ELSE_STMT, when(some(), composite(
					token(LToken.Else).withSpacingBefore(space()),
					element(alternative(LSCondition.kind(Kind.BlockStmt),
							defaultShape().withSpacingBefore(space()),
							alternative(LSCondition.kind(Kind.IfStmt),
									defaultShape().withSpacingBefore(spacing(IfStmt_ElseIfStmt)),
									alternative(LSCondition.kind(Kind.ExpressionStmt),
											defaultShape()
													.withSpacingBefore(spacing(IfStmt_ElseExpressionStmt))
													.withIndentationBefore(indent(BLOCK))
													.withIndentationAfter(unIndent(BLOCK)),
											defaultShape()
													.withSpacingBefore(spacing(IfStmt_ElseOtherStmt))
													.withIndentationBefore(indent(BLOCK))
													.withIndentationAfter(unIndent(BLOCK))
													.withSpacingAfter(newLine())
									)
							)
					))
			)))
	);
}
