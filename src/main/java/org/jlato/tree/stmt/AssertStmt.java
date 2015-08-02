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
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;

import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class AssertStmt extends TreeBase<AssertStmt.State, Stmt, AssertStmt> implements Stmt {

	public Kind kind() {
		return Kind.AssertStmt;
	}

	private AssertStmt(SLocation<AssertStmt.State> location) {
		super(location);
	}

	public static STree<AssertStmt.State> make(STree<? extends Expr.State> check, STree<SNodeOptionState> msg) {
		return new STree<AssertStmt.State>(new AssertStmt.State(check, msg));
	}

	public AssertStmt(Expr check, NodeOption<Expr> msg) {
		super(new SLocation<AssertStmt.State>(make(TreeBase.<Expr.State>treeOf(check), TreeBase.<SNodeOptionState>treeOf(msg))));
	}

	public Expr check() {
		return location.safeTraversal(CHECK);
	}

	public AssertStmt withCheck(Expr check) {
		return location.safeTraversalReplace(CHECK, check);
	}

	public AssertStmt withCheck(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(CHECK, mutation);
	}

	public NodeOption<Expr> msg() {
		return location.safeTraversal(MSG);
	}

	public AssertStmt withMsg(NodeOption<Expr> msg) {
		return location.safeTraversalReplace(MSG, msg);
	}

	public AssertStmt withMsg(Mutation<NodeOption<Expr>> mutation) {
		return location.safeTraversalMutate(MSG, mutation);
	}

	public static class State extends SNodeState<State> implements Stmt.State {

		public final STree<? extends Expr.State> check;

		public final STree<SNodeOptionState> msg;

		State(STree<? extends Expr.State> check, STree<SNodeOptionState> msg) {
			this.check = check;
			this.msg = msg;
		}

		public AssertStmt.State withCheck(STree<? extends Expr.State> check) {
			return new AssertStmt.State(check, msg);
		}

		public AssertStmt.State withMsg(STree<SNodeOptionState> msg) {
			return new AssertStmt.State(check, msg);
		}

		@Override
		public Kind kind() {
			return Kind.AssertStmt;
		}

		@Override
		protected Tree doInstantiate(SLocation<AssertStmt.State> location) {
			return new AssertStmt(location);
		}

		@Override
		public LexicalShape shape() {
			return shape;
		}

		@Override
		public STraversal firstChild() {
			return CHECK;
		}

		@Override
		public STraversal lastChild() {
			return MSG;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			State state = (State) o;
			if (check == null ? state.check != null : !check.equals(state.check))
				return false;
			if (!msg.equals(state.msg))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			if (check != null) result = 37 * result + check.hashCode();
			result = 37 * result + msg.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<AssertStmt.State, Expr.State, Expr> CHECK = new STypeSafeTraversal<AssertStmt.State, Expr.State, Expr>() {

		@Override
		public STree<?> doTraverse(AssertStmt.State state) {
			return state.check;
		}

		@Override
		public AssertStmt.State doRebuildParentState(AssertStmt.State state, STree<Expr.State> child) {
			return state.withCheck(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return MSG;
		}
	};

	private static STypeSafeTraversal<AssertStmt.State, SNodeOptionState, NodeOption<Expr>> MSG = new STypeSafeTraversal<AssertStmt.State, SNodeOptionState, NodeOption<Expr>>() {

		@Override
		public STree<?> doTraverse(AssertStmt.State state) {
			return state.msg;
		}

		@Override
		public AssertStmt.State doRebuildParentState(AssertStmt.State state, STree<SNodeOptionState> child) {
			return state.withMsg(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return CHECK;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			keyword(LToken.Assert),
			child(CHECK),
			child(MSG, when(some(),
					composite(token(LToken.Colon).withSpacing(space(), space()), element())
			)),
			token(LToken.SemiColon)
	);
}
