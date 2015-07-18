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

public class IfStmt extends TreeBase<IfStmt.State, Stmt, IfStmt> implements Stmt {

	public Kind kind() {
		return Kind.IfStmt;
	}

	private IfStmt(SLocation<IfStmt.State> location) {
		super(location);
	}

	public static STree<IfStmt.State> make(Expr condition, Stmt thenStmt, NodeOption<Stmt> elseStmt) {
		return new STree<IfStmt.State>(new IfStmt.State(TreeBase.<Expr.State>nodeOf(condition), TreeBase.<Stmt.State>nodeOf(thenStmt), TreeBase.<SNodeOptionState>nodeOf(elseStmt)));
	}

	public IfStmt(Expr condition, Stmt thenStmt, NodeOption<Stmt> elseStmt) {
		super(new SLocation<IfStmt.State>(make(condition, thenStmt, elseStmt)));
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

	private static final STraversal<IfStmt.State> CONDITION = new STraversal<IfStmt.State>() {

		public STree<?> traverse(IfStmt.State state) {
			return state.condition;
		}

		public IfStmt.State rebuildParentState(IfStmt.State state, STree<?> child) {
			return state.withCondition((STree) child);
		}

		public STraversal<IfStmt.State> leftSibling(IfStmt.State state) {
			return null;
		}

		public STraversal<IfStmt.State> rightSibling(IfStmt.State state) {
			return THEN_STMT;
		}
	};
	private static final STraversal<IfStmt.State> THEN_STMT = new STraversal<IfStmt.State>() {

		public STree<?> traverse(IfStmt.State state) {
			return state.thenStmt;
		}

		public IfStmt.State rebuildParentState(IfStmt.State state, STree<?> child) {
			return state.withThenStmt((STree) child);
		}

		public STraversal<IfStmt.State> leftSibling(IfStmt.State state) {
			return CONDITION;
		}

		public STraversal<IfStmt.State> rightSibling(IfStmt.State state) {
			return ELSE_STMT;
		}
	};
	private static final STraversal<IfStmt.State> ELSE_STMT = new STraversal<IfStmt.State>() {

		public STree<?> traverse(IfStmt.State state) {
			return state.elseStmt;
		}

		public IfStmt.State rebuildParentState(IfStmt.State state, STree<?> child) {
			return state.withElseStmt((STree) child);
		}

		public STraversal<IfStmt.State> leftSibling(IfStmt.State state) {
			return THEN_STMT;
		}

		public STraversal<IfStmt.State> rightSibling(IfStmt.State state) {
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

	public static class State extends SNodeState<State> {

		public final STree<Expr.State> condition;

		public final STree<Stmt.State> thenStmt;

		public final STree<SNodeOptionState> elseStmt;

		State(STree<Expr.State> condition, STree<Stmt.State> thenStmt, STree<SNodeOptionState> elseStmt) {
			this.condition = condition;
			this.thenStmt = thenStmt;
			this.elseStmt = elseStmt;
		}

		public IfStmt.State withCondition(STree<Expr.State> condition) {
			return new IfStmt.State(condition, thenStmt, elseStmt);
		}

		public IfStmt.State withThenStmt(STree<Stmt.State> thenStmt) {
			return new IfStmt.State(condition, thenStmt, elseStmt);
		}

		public IfStmt.State withElseStmt(STree<SNodeOptionState> elseStmt) {
			return new IfStmt.State(condition, thenStmt, elseStmt);
		}

		public STraversal<IfStmt.State> firstChild() {
			return null;
		}

		public STraversal<IfStmt.State> lastChild() {
			return null;
		}

		public Tree instantiate(SLocation<IfStmt.State> location) {
			return new IfStmt(location);
		}

		public LexicalShape shape() {
			return shape;
		}

		public Kind kind() {
			return Kind.IfStmt;
		}
	}
}
