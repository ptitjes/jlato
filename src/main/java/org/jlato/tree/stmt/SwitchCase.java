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
import org.jlato.tree.expr.Expr;

import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.BLOCK;
import static org.jlato.printer.IndentationConstraint.indent;
import static org.jlato.printer.IndentationConstraint.unIndent;
import static org.jlato.printer.SpacingConstraint.newLine;

public class SwitchCase extends TreeBase<SwitchCase.State, Tree, SwitchCase> implements Tree {

	public Kind kind() {
		return Kind.SwitchCase;
	}

	private SwitchCase(SLocation<SwitchCase.State> location) {
		super(location);
	}

	public static STree<SwitchCase.State> make(STree<SNodeOptionState> label, STree<SNodeListState> stmts) {
		return new STree<SwitchCase.State>(new SwitchCase.State(label, stmts));
	}

	public SwitchCase(NodeOption<Expr> label, NodeList<Stmt> stmts) {
		super(new SLocation<SwitchCase.State>(make(TreeBase.<SNodeOptionState>treeOf(label), TreeBase.<SNodeListState>treeOf(stmts))));
	}

	public NodeOption<Expr> label() {
		return location.safeTraversal(LABEL);
	}

	public SwitchCase withLabel(NodeOption<Expr> label) {
		return location.safeTraversalReplace(LABEL, label);
	}

	public SwitchCase withLabel(Mutation<NodeOption<Expr>> mutation) {
		return location.safeTraversalMutate(LABEL, mutation);
	}

	public NodeList<Stmt> stmts() {
		return location.safeTraversal(STMTS);
	}

	public SwitchCase withStmts(NodeList<Stmt> stmts) {
		return location.safeTraversalReplace(STMTS, stmts);
	}

	public SwitchCase withStmts(Mutation<NodeList<Stmt>> mutation) {
		return location.safeTraversalMutate(STMTS, mutation);
	}

	public static class State extends SNodeState<State> implements STreeState {

		public final STree<SNodeOptionState> label;

		public final STree<SNodeListState> stmts;

		State(STree<SNodeOptionState> label, STree<SNodeListState> stmts) {
			this.label = label;
			this.stmts = stmts;
		}

		public SwitchCase.State withLabel(STree<SNodeOptionState> label) {
			return new SwitchCase.State(label, stmts);
		}

		public SwitchCase.State withStmts(STree<SNodeListState> stmts) {
			return new SwitchCase.State(label, stmts);
		}

		@Override
		public Kind kind() {
			return Kind.SwitchCase;
		}

		@Override
		protected Tree doInstantiate(SLocation<SwitchCase.State> location) {
			return new SwitchCase(location);
		}

		@Override
		public LexicalShape shape() {
			return shape;
		}

		@Override
		public STraversal firstChild() {
			return LABEL;
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
			if (!label.equals(state.label))
				return false;
			if (!stmts.equals(state.stmts))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			result = 37 * result + label.hashCode();
			result = 37 * result + stmts.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<SwitchCase.State, SNodeOptionState, NodeOption<Expr>> LABEL = new STypeSafeTraversal<SwitchCase.State, SNodeOptionState, NodeOption<Expr>>() {

		@Override
		public STree<?> doTraverse(State state) {
			return state.label;
		}

		@Override
		public SwitchCase.State doRebuildParentState(State state, STree<SNodeOptionState> child) {
			return state.withLabel(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return STMTS;
		}
	};

	private static STypeSafeTraversal<SwitchCase.State, SNodeListState, NodeList<Stmt>> STMTS = new STypeSafeTraversal<SwitchCase.State, SNodeListState, NodeList<Stmt>>() {

		@Override
		public STree<?> doTraverse(State state) {
			return state.stmts;
		}

		@Override
		public SwitchCase.State doRebuildParentState(State state, STree<SNodeListState> child) {
			return state.withStmts(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return LABEL;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			child(LABEL, alternative(some(),
					composite(token(LToken.Case), element()),
					token(LToken.Default)
			)),
			token(LToken.Colon).withSpacingAfter(newLine()),
			none().withIndentationAfter(indent(BLOCK)),
			child(STMTS, Stmt.listShape),
			none().withIndentationBefore(unIndent(BLOCK))
	);

	public static final LexicalShape listShape = list(none().withSpacingAfter(newLine()));
}
