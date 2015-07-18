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
import org.jlato.internal.bu.*;
import org.jlato.tree.Tree;

public class ReturnStmt extends TreeBase<ReturnStmt.State, Stmt, ReturnStmt> implements Stmt {

	public Kind kind() {
		return Kind.ReturnStmt;
	}

	private ReturnStmt(SLocation<ReturnStmt.State> location) {
		super(location);
	}

	public static STree<ReturnStmt.State> make(NodeOption<Expr> expr) {
		return new STree<ReturnStmt.State>(new ReturnStmt.State(TreeBase.<SNodeOptionState>nodeOf(expr)));
	}

	public ReturnStmt(NodeOption<Expr> expr) {
		super(new SLocation<ReturnStmt.State>(make(expr)));
	}

	public NodeOption<Expr> expr() {
		return location.safeTraversal(EXPR);
	}

	public ReturnStmt withExpr(NodeOption<Expr> expr) {
		return location.safeTraversalReplace(EXPR, expr);
	}

	public ReturnStmt withExpr(Mutation<NodeOption<Expr>> mutation) {
		return location.safeTraversalMutate(EXPR, mutation);
	}

	private static final STraversal<ReturnStmt.State> EXPR = new STraversal<ReturnStmt.State>() {

		public STree<?> traverse(ReturnStmt.State state) {
			return state.expr;
		}

		public ReturnStmt.State rebuildParentState(ReturnStmt.State state, STree<?> child) {
			return state.withExpr((STree) child);
		}

		public STraversal<ReturnStmt.State> leftSibling(ReturnStmt.State state) {
			return null;
		}

		public STraversal<ReturnStmt.State> rightSibling(ReturnStmt.State state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			token(LToken.Return),
			when(childIs(EXPR, some()), child(EXPR, element())),
			token(LToken.SemiColon)
	);

	public static class State extends SNodeState<State> {

		public final STree<SNodeOptionState> expr;

		State(STree<SNodeOptionState> expr) {
			this.expr = expr;
		}

		public ReturnStmt.State withExpr(STree<SNodeOptionState> expr) {
			return new ReturnStmt.State(expr);
		}

		public STraversal<ReturnStmt.State> firstChild() {
			return null;
		}

		public STraversal<ReturnStmt.State> lastChild() {
			return null;
		}

		public Tree instantiate(SLocation<ReturnStmt.State> location) {
			return new ReturnStmt(location);
		}

		public LexicalShape shape() {
			return shape;
		}

		public Kind kind() {
			return Kind.ReturnStmt;
		}
	}
}
