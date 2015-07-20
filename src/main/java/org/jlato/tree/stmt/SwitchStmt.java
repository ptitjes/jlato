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
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.BLOCK;
import static org.jlato.printer.FormattingSettings.SpacingLocation.SwitchStmt_AfterSwitchKeyword;
import static org.jlato.printer.IndentationConstraint.indent;
import static org.jlato.printer.IndentationConstraint.unIndent;
import static org.jlato.printer.SpacingConstraint.*;

public class SwitchStmt extends TreeBase<SwitchStmt.State, Stmt, SwitchStmt> implements Stmt {

	public Kind kind() {
		return Kind.SwitchStmt;
	}

	private SwitchStmt(SLocation<SwitchStmt.State> location) {
		super(location);
	}

	public static STree<SwitchStmt.State> make(STree<? extends Expr.State> selector, STree<SNodeListState> cases) {
		return new STree<SwitchStmt.State>(new SwitchStmt.State(selector, cases));
	}

	public SwitchStmt(Expr selector, NodeList<SwitchCase> cases) {
		super(new SLocation<SwitchStmt.State>(make(TreeBase.<Expr.State>nodeOf(selector), TreeBase.<SNodeListState>nodeOf(cases))));
	}

	public Expr selector() {
		return location.safeTraversal(SELECTOR);
	}

	public SwitchStmt withSelector(Expr selector) {
		return location.safeTraversalReplace(SELECTOR, selector);
	}

	public SwitchStmt withSelector(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SELECTOR, mutation);
	}

	public NodeList<SwitchCase> cases() {
		return location.safeTraversal(CASES);
	}

	public SwitchStmt withCases(NodeList<SwitchCase> cases) {
		return location.safeTraversalReplace(CASES, cases);
	}

	public SwitchStmt withCases(Mutation<NodeList<SwitchCase>> mutation) {
		return location.safeTraversalMutate(CASES, mutation);
	}

	public static class State extends SNodeState<State> implements Stmt.State {

		public final STree<? extends Expr.State> selector;

		public final STree<SNodeListState> cases;

		State(STree<? extends Expr.State> selector, STree<SNodeListState> cases) {
			this.selector = selector;
			this.cases = cases;
		}

		public SwitchStmt.State withSelector(STree<? extends Expr.State> selector) {
			return new SwitchStmt.State(selector, cases);
		}

		public SwitchStmt.State withCases(STree<SNodeListState> cases) {
			return new SwitchStmt.State(selector, cases);
		}

		@Override
		public Kind kind() {
			return Kind.SwitchStmt;
		}

		@Override
		protected Tree doInstantiate(SLocation<SwitchStmt.State> location) {
			return new SwitchStmt(location);
		}

		@Override
		public LexicalShape shape() {
			return shape;
		}

		@Override
		public STraversal firstChild() {
			return SELECTOR;
		}

		@Override
		public STraversal lastChild() {
			return CASES;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			SwitchStmt.State state = (SwitchStmt.State) o;
			if (!selector.equals(state.selector))
				return false;
			if (!cases.equals(state.cases))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			result = 37 * result + selector.hashCode();
			result = 37 * result + cases.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<SwitchStmt.State, Expr.State, Expr> SELECTOR = new STypeSafeTraversal<SwitchStmt.State, Expr.State, Expr>() {

		@Override
		protected STree<?> doTraverse(SwitchStmt.State state) {
			return state.selector;
		}

		@Override
		protected SwitchStmt.State doRebuildParentState(SwitchStmt.State state, STree<Expr.State> child) {
			return state.withSelector(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return CASES;
		}
	};

	private static STypeSafeTraversal<SwitchStmt.State, SNodeListState, NodeList<SwitchCase>> CASES = new STypeSafeTraversal<SwitchStmt.State, SNodeListState, NodeList<SwitchCase>>() {

		@Override
		protected STree<?> doTraverse(SwitchStmt.State state) {
			return state.cases;
		}

		@Override
		protected SwitchStmt.State doRebuildParentState(SwitchStmt.State state, STree<SNodeListState> child) {
			return state.withCases(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return SELECTOR;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			token(LToken.Switch),
			token(LToken.ParenthesisLeft).withSpacingBefore(spacing(SwitchStmt_AfterSwitchKeyword)),
			child(SELECTOR),
			token(LToken.ParenthesisRight).withSpacingAfter(space()),
			nonEmptyChildren(CASES,
					composite(
							token(LToken.BraceLeft)
									.withSpacingAfter(newLine())
									.withIndentationAfter(indent(BLOCK)),
							child(CASES, SwitchCase.listShape),
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
			)
	);
}
