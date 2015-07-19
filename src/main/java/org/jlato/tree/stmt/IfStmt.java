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
import org.jlato.internal.shapes.LSCondition;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.Kind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeOption;
import org.jlato.tree.expr.Expr;

import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.BLOCK;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;
import static org.jlato.printer.IndentationConstraint.indent;
import static org.jlato.printer.IndentationConstraint.unIndent;
import static org.jlato.printer.SpacingConstraint.*;
import org.jlato.internal.bu.*;
import org.jlato.tree.Tree;
import org.jlato.internal.bu.*;
import org.jlato.internal.td.*;
import org.jlato.internal.bu.*;
import org.jlato.internal.td.*;

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
		super(new SLocation<IfStmt.State>(make(TreeBase.<Expr.State>nodeOf(condition), TreeBase.<Stmt.State>nodeOf(thenStmt), TreeBase.<SNodeOptionState>nodeOf(elseStmt))));
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

	public static class State extends SNodeState<State>implements Stmt.State {

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
	}

	private static STypeSafeTraversal<IfStmt.State, Expr.State, Expr> CONDITION = new STypeSafeTraversal<IfStmt.State, Expr.State, Expr>() {

		@Override
		protected STree<?> doTraverse(IfStmt.State state) {
			return state.condition;
		}

		@Override
		protected IfStmt.State doRebuildParentState(IfStmt.State state, STree<Expr.State> child) {
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
		protected STree<?> doTraverse(IfStmt.State state) {
			return state.thenStmt;
		}

		@Override
		protected IfStmt.State doRebuildParentState(IfStmt.State state, STree<Stmt.State> child) {
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
		protected STree<?> doTraverse(IfStmt.State state) {
			return state.elseStmt;
		}

		@Override
		protected IfStmt.State doRebuildParentState(IfStmt.State state, STree<SNodeOptionState> child) {
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
			token(LToken.If), token(LToken.ParenthesisLeft).withSpacingBefore(space()),
			child(CONDITION),
			token(LToken.ParenthesisRight),
			alternative(childHas(THEN_STMT, LSCondition.kind(Kind.BlockStmt)),
					composite(none().withSpacingAfter(space()), child(THEN_STMT)),
					alternative(childHas(THEN_STMT, LSCondition.kind(Kind.ExpressionStmt)),
							composite(
									none().withSpacingAfter(spacing(IfStmt_ThenExpressionStmt)).withIndentationAfter(indent(BLOCK)),
									child(THEN_STMT),
									none().withIndentationBefore(unIndent(BLOCK)).withSpacingAfter(newLine())
							),
							composite(
									none().withSpacingAfter(spacing(IfStmt_ThenOtherStmt)).withIndentationAfter(indent(BLOCK)),
									child(THEN_STMT),
									none().withIndentationBefore(unIndent(BLOCK)).withSpacingAfter(newLine())
							)
					)
			),
			when(childIs(ELSE_STMT, some()), composite(
					token(LToken.Else).withSpacingBefore(space()),
					alternative(childHas(ELSE_STMT, elementHas(LSCondition.kind(Kind.BlockStmt))),
							composite(none().withSpacingAfter(space()), child(ELSE_STMT, element())),
							alternative(childHas(ELSE_STMT, elementHas(LSCondition.kind(Kind.IfStmt))),
									composite(
											none().withSpacingAfter(spacing(IfStmt_ElseIfStmt)),
											child(ELSE_STMT, element())
									),
									alternative(childHas(ELSE_STMT, elementHas(LSCondition.kind(Kind.ExpressionStmt))),
											composite(
													none().withSpacingAfter(spacing(IfStmt_ElseExpressionStmt)).withIndentationAfter(indent(BLOCK)),
													child(ELSE_STMT, element()),
													none().withIndentationBefore(unIndent(BLOCK))
											),
											composite(
													none().withSpacingAfter(spacing(IfStmt_ElseOtherStmt)).withIndentationAfter(indent(BLOCK)),
													child(ELSE_STMT, element()),
													none().withIndentationBefore(unIndent(BLOCK)).withSpacingAfter(newLine())
											)
									)
							)
					)
			))
	);
}
