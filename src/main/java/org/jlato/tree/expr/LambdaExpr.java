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

package org.jlato.tree.expr;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SKind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeEither;
import org.jlato.tree.NodeList;
import org.jlato.tree.decl.FormalParameter;
import org.jlato.tree.stmt.BlockStmt;

import static org.jlato.internal.shapes.LSCondition.data;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;
import org.jlato.internal.bu.*;

public class LambdaExpr extends TreeBase<LambdaExpr.State, Expr, LambdaExpr> implements Expr {

	public final static SKind<LambdaExpr.State> kind = new SKind<LambdaExpr.State>() {
		public LambdaExpr instantiate(SLocation<LambdaExpr.State> location) {
			return new LambdaExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private LambdaExpr(SLocation<LambdaExpr.State> location) {
		super(location);
	}

	public static STree<LambdaExpr.State> make(NodeList<FormalParameter> params, boolean hasParens, NodeEither<Expr, BlockStmt> body) {
		return new STree<LambdaExpr.State>(kind, new LambdaExpr.State(TreeBase.<SNodeListState>nodeOf(params), hasParens, TreeBase.<SNodeEitherState>nodeOf(body)));
	}

	public LambdaExpr(NodeList<FormalParameter> params, boolean hasParens, NodeEither<Expr, BlockStmt> body) {
		super(new SLocation<LambdaExpr.State>(make(params, hasParens, body)));
	}

	public LambdaExpr(NodeList<FormalParameter> params, boolean hasParens, Expr expr) {
		this(params, hasParens, NodeEither.<Expr, BlockStmt>left(expr));
	}

	public LambdaExpr(NodeList<FormalParameter> params, boolean hasParens, BlockStmt block) {
		this(params, hasParens, NodeEither.<Expr, BlockStmt>right(block));
	}

	public NodeList<FormalParameter> params() {
		return location.safeTraversal(PARAMS);
	}

	public LambdaExpr withParams(NodeList<FormalParameter> params) {
		return location.safeTraversalReplace(PARAMS, params);
	}

	public LambdaExpr withParams(Mutation<NodeList<FormalParameter>> mutation) {
		return location.safeTraversalMutate(PARAMS, mutation);
	}

	public boolean hasParens() {
		return location.<Boolean>data(PARENS);
	}

	public FormalParameter setParens(boolean hasParens) {
		return location.withData(PARENS, hasParens);
	}

	public NodeEither<Expr, BlockStmt> body() {
		return location.safeTraversal(BODY);
	}

	public LambdaExpr withBody(NodeEither<Expr, BlockStmt> body) {
		return location.safeTraversalReplace(BODY, body);
	}

	public LambdaExpr withBody(Mutation<NodeEither<Expr, BlockStmt>> mutation) {
		return location.safeTraversalMutate(BODY, mutation);
	}

	private static final STraversal<LambdaExpr.State> PARAMS = new STraversal<LambdaExpr.State>() {

		public STree<?> traverse(LambdaExpr.State state) {
			return state.params;
		}

		public LambdaExpr.State rebuildParentState(LambdaExpr.State state, STree<?> child) {
			return state.withParams((STree) child);
		}

		public STraversal<LambdaExpr.State> leftSibling(LambdaExpr.State state) {
			return null;
		}

		public STraversal<LambdaExpr.State> rightSibling(LambdaExpr.State state) {
			return BODY;
		}
	};
	private static final STraversal<LambdaExpr.State> BODY = new STraversal<LambdaExpr.State>() {

		public STree<?> traverse(LambdaExpr.State state) {
			return state.body;
		}

		public LambdaExpr.State rebuildParentState(LambdaExpr.State state, STree<?> child) {
			return state.withBody((STree) child);
		}

		public STraversal<LambdaExpr.State> leftSibling(LambdaExpr.State state) {
			return PARAMS;
		}

		public STraversal<LambdaExpr.State> rightSibling(LambdaExpr.State state) {
			return null;
		}
	};

	private static final int PARENS = 0;

	public final static LexicalShape shape = composite(
			when(data(PARENS), token(LToken.ParenthesisLeft)),
			child(PARAMS, Expr.listShape),
			when(data(PARENS), token(LToken.ParenthesisRight)),
			token(LToken.Arrow).withSpacing(space(), space()),
			child(BODY, leftOrRight())
	);

	public static class State extends SNodeState<State> {

		public final STree<SNodeListState> params;

		public final boolean hasParens;

		public final STree<SNodeEitherState> body;

		State(STree<SNodeListState> params, boolean hasParens, STree<SNodeEitherState> body) {
			this.params = params;
			this.hasParens = hasParens;
			this.body = body;
		}

		public LambdaExpr.State withParams(STree<SNodeListState> params) {
			return new LambdaExpr.State(params, hasParens, body);
		}

		public LambdaExpr.State withHasParens(boolean hasParens) {
			return new LambdaExpr.State(params, hasParens, body);
		}

		public LambdaExpr.State withBody(STree<SNodeEitherState> body) {
			return new LambdaExpr.State(params, hasParens, body);
		}

		public STraversal<LambdaExpr.State> firstChild() {
			return null;
		}

		public STraversal<LambdaExpr.State> lastChild() {
			return null;
		}
	}
}
