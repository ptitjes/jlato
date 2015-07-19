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

import org.jlato.internal.bu.*;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.*;
import org.jlato.tree.decl.FormalParameter;
import org.jlato.tree.stmt.BlockStmt;

import static org.jlato.internal.shapes.LSCondition.data;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class LambdaExpr extends TreeBase<LambdaExpr.State, Expr, LambdaExpr> implements Expr {

	public Kind kind() {
		return Kind.LambdaExpr;
	}

	private LambdaExpr(SLocation<LambdaExpr.State> location) {
		super(location);
	}

	public static STree<LambdaExpr.State> make(STree<SNodeListState> params, boolean hasParens, STree<SNodeEitherState> body) {
		return new STree<LambdaExpr.State>(new LambdaExpr.State(params, hasParens, body));
	}

	public LambdaExpr(NodeList<FormalParameter> params, boolean hasParens, NodeEither<Expr, BlockStmt> body) {
		super(new SLocation<LambdaExpr.State>(make(TreeBase.<SNodeListState>nodeOf(params), hasParens, TreeBase.<SNodeEitherState>nodeOf(body))));
	}

	public LambdaExpr(NodeList<FormalParameter> params, boolean hasParens, Expr body) {
		super(new SLocation<LambdaExpr.State>(make(TreeBase.<SNodeListState>nodeOf(params), hasParens, TreeBase.<SNodeEitherState>nodeOf(body))));
	}

	public LambdaExpr(NodeList<FormalParameter> params, boolean hasParens, BlockStmt body) {
		super(new SLocation<LambdaExpr.State>(make(TreeBase.<SNodeListState>nodeOf(params), hasParens, TreeBase.<SNodeEitherState>nodeOf(body))));
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
		return location.safeProperty(PARENS);
	}

	public FormalParameter setParens(boolean hasParens) {
		return location.safePropertyReplace(PARENS, (Boolean) hasParens);
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

	public static class State extends SNodeState<State> implements Expr.State {

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

		public LambdaExpr.State setParens(boolean hasParens) {
			return new LambdaExpr.State(params, hasParens, body);
		}

		public LambdaExpr.State withBody(STree<SNodeEitherState> body) {
			return new LambdaExpr.State(params, hasParens, body);
		}

		@Override
		public Kind kind() {
			return Kind.LambdaExpr;
		}

		@Override
		protected Tree doInstantiate(SLocation<LambdaExpr.State> location) {
			return new LambdaExpr(location);
		}

		@Override
		public LexicalShape shape() {
			return shape;
		}

		@Override
		public STraversal firstChild() {
			return PARAMS;
		}

		@Override
		public STraversal lastChild() {
			return BODY;
		}
	}

	private static STypeSafeTraversal<LambdaExpr.State, SNodeListState, NodeList<FormalParameter>> PARAMS = new STypeSafeTraversal<LambdaExpr.State, SNodeListState, NodeList<FormalParameter>>() {

		@Override
		protected STree<?> doTraverse(LambdaExpr.State state) {
			return state.params;
		}

		@Override
		protected LambdaExpr.State doRebuildParentState(LambdaExpr.State state, STree<SNodeListState> child) {
			return state.withParams(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return BODY;
		}
	};

	private static STypeSafeTraversal<LambdaExpr.State, SNodeEitherState, NodeEither<Expr, BlockStmt>> BODY = new STypeSafeTraversal<LambdaExpr.State, SNodeEitherState, NodeEither<Expr, BlockStmt>>() {

		@Override
		protected STree<?> doTraverse(LambdaExpr.State state) {
			return state.body;
		}

		@Override
		protected LambdaExpr.State doRebuildParentState(LambdaExpr.State state, STree<SNodeEitherState> child) {
			return state.withBody(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return PARAMS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	private static STypeSafeProperty<LambdaExpr.State, Boolean> PARENS = new STypeSafeProperty<LambdaExpr.State, Boolean>() {

		@Override
		protected Boolean doRetrieve(LambdaExpr.State state) {
			return state.hasParens;
		}

		@Override
		protected LambdaExpr.State doRebuildParentState(LambdaExpr.State state, Boolean value) {
			return state.setParens(value);
		}
	};

	public final static LexicalShape shape = composite(
			when(data(PARENS), token(LToken.ParenthesisLeft)),
			child(PARAMS, Expr.listShape),
			when(data(PARENS), token(LToken.ParenthesisRight)),
			token(LToken.Arrow).withSpacing(space(), space()),
			child(BODY, leftOrRight())
	);
}
