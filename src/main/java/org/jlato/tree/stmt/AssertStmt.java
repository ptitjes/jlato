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
import org.jlato.tree.Kind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeOption;
import org.jlato.tree.expr.Expr;

import static org.jlato.internal.shapes.LSCondition.childIs;
import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;
import org.jlato.internal.bu.*;
import org.jlato.tree.Tree;

public class AssertStmt extends TreeBase<AssertStmt.State, Stmt, AssertStmt> implements Stmt {

	public Kind kind() {
		return Kind.AssertStmt;
	}

	private AssertStmt(SLocation<AssertStmt.State> location) {
		super(location);
	}

	public static STree<AssertStmt.State> make(Expr check, NodeOption<Expr> msg) {
		return new STree<AssertStmt.State>(new AssertStmt.State(TreeBase.<Expr.State>nodeOf(check), TreeBase.<SNodeOptionState>nodeOf(msg)));
	}

	public AssertStmt(Expr check, NodeOption<Expr> msg) {
		super(new SLocation<AssertStmt.State>(make(check, msg)));
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

	private static final STraversal<AssertStmt.State> CHECK = new STraversal<AssertStmt.State>() {

		public STree<?> traverse(AssertStmt.State state) {
			return state.check;
		}

		public AssertStmt.State rebuildParentState(AssertStmt.State state, STree<?> child) {
			return state.withCheck((STree) child);
		}

		public STraversal<AssertStmt.State> leftSibling(AssertStmt.State state) {
			return null;
		}

		public STraversal<AssertStmt.State> rightSibling(AssertStmt.State state) {
			return MSG;
		}
	};
	private static final STraversal<AssertStmt.State> MSG = new STraversal<AssertStmt.State>() {

		public STree<?> traverse(AssertStmt.State state) {
			return state.msg;
		}

		public AssertStmt.State rebuildParentState(AssertStmt.State state, STree<?> child) {
			return state.withMsg((STree) child);
		}

		public STraversal<AssertStmt.State> leftSibling(AssertStmt.State state) {
			return CHECK;
		}

		public STraversal<AssertStmt.State> rightSibling(AssertStmt.State state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			token(LToken.Assert),
			child(CHECK),
			when(childIs(MSG, some()), composite(
					token(LToken.Colon).withSpacing(space(), space()),
					child(MSG, element())
			)),
			token(LToken.SemiColon)
	);

	public static class State extends SNodeState<State> {

		public final STree<Expr.State> check;

		public final STree<SNodeOptionState> msg;

		State(STree<Expr.State> check, STree<SNodeOptionState> msg) {
			this.check = check;
			this.msg = msg;
		}

		public AssertStmt.State withCheck(STree<Expr.State> check) {
			return new AssertStmt.State(check, msg);
		}

		public AssertStmt.State withMsg(STree<SNodeOptionState> msg) {
			return new AssertStmt.State(check, msg);
		}

		public STraversal<AssertStmt.State> firstChild() {
			return CHECK;
		}

		public STraversal<AssertStmt.State> lastChild() {
			return MSG;
		}

		public Tree instantiate(SLocation<AssertStmt.State> location) {
			return new AssertStmt(location);
		}

		public LexicalShape shape() {
			return shape;
		}

		public Kind kind() {
			return Kind.AssertStmt;
		}
	}
}
