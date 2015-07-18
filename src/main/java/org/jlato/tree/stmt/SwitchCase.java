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
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;

import static org.jlato.internal.shapes.LSCondition.childIs;
import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.BLOCK;
import static org.jlato.printer.IndentationConstraint.indent;
import static org.jlato.printer.IndentationConstraint.unIndent;
import static org.jlato.printer.SpacingConstraint.newLine;
import org.jlato.internal.bu.*;

public class SwitchCase extends TreeBase<SwitchCase.State, Tree, SwitchCase> implements Tree {

	public final static SKind<SwitchCase.State> kind = new SKind<SwitchCase.State>() {

	};

	private SwitchCase(SLocation<SwitchCase.State> location) {
		super(location);
	}

	public static STree<SwitchCase.State> make(NodeOption<Expr> label, NodeList<Stmt> stmts) {
		return new STree<SwitchCase.State>(kind, new SwitchCase.State(TreeBase.<SNodeOptionState>nodeOf(label), TreeBase.<SNodeListState>nodeOf(stmts)));
	}

	public SwitchCase(NodeOption<Expr> label, NodeList<Stmt> stmts) {
		super(new SLocation<SwitchCase.State>(make(label, stmts)));
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

	private static final STraversal<SwitchCase.State> LABEL = new STraversal<SwitchCase.State>() {

		public STree<?> traverse(SwitchCase.State state) {
			return state.label;
		}

		public SwitchCase.State rebuildParentState(SwitchCase.State state, STree<?> child) {
			return state.withLabel((STree) child);
		}

		public STraversal<SwitchCase.State> leftSibling(SwitchCase.State state) {
			return null;
		}

		public STraversal<SwitchCase.State> rightSibling(SwitchCase.State state) {
			return STMTS;
		}
	};
	private static final STraversal<SwitchCase.State> STMTS = new STraversal<SwitchCase.State>() {

		public STree<?> traverse(SwitchCase.State state) {
			return state.stmts;
		}

		public SwitchCase.State rebuildParentState(SwitchCase.State state, STree<?> child) {
			return state.withStmts((STree) child);
		}

		public STraversal<SwitchCase.State> leftSibling(SwitchCase.State state) {
			return LABEL;
		}

		public STraversal<SwitchCase.State> rightSibling(SwitchCase.State state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			alternative(childIs(LABEL, some()),
					composite(token(LToken.Case), child(LABEL, element())),
					token(LToken.Default)
			),
			token(LToken.Colon).withSpacingAfter(newLine()),
			none().withIndentationAfter(indent(BLOCK)),
			child(STMTS, Stmt.listShape),
			none().withIndentationBefore(unIndent(BLOCK))
	);

	public static final LexicalShape listShape = list(none().withSpacingAfter(newLine()));

	public static class State extends SNodeState<State> {

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

		public STraversal<SwitchCase.State> firstChild() {
			return null;
		}

		public STraversal<SwitchCase.State> lastChild() {
			return null;
		}

		public Tree instantiate(SLocation<SwitchCase.State> location) {
			return new SwitchCase(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	}
}
