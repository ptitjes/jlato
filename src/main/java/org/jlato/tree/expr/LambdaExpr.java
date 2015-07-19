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
import org.jlato.tree.Kind;
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
import org.jlato.tree.Tree;
import org.jlato.internal.bu.*;
import org.jlato.internal.td.*;

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

	public final static LexicalShape shape = composite(
			when(data(PARENS), token(LToken.ParenthesisLeft)),
			child(PARAMS, Expr.listShape),
			when(data(PARENS), token(LToken.ParenthesisRight)),
			token(LToken.Arrow).withSpacing(space(), space()),
			child(BODY, leftOrRight())
	);
}
