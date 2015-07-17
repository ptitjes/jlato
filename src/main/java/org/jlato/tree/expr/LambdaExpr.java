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

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class LambdaExpr extends TreeBase<SNodeState, Expr, LambdaExpr> implements Expr {

	public final static SKind<SNodeState> kind = new SKind<SNodeState>() {
		public LambdaExpr instantiate(SLocation<SNodeState> location) {
			return new LambdaExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private LambdaExpr(SLocation<SNodeState> location) {
		super(location);
	}

	public LambdaExpr(NodeList<FormalParameter> params, NodeEither<Expr, BlockStmt> body) {
		super(new SLocation<SNodeState>(new STree<SNodeState>(kind, new SNodeState(treesOf(params, body)))));
	}

	public LambdaExpr(NodeList<FormalParameter> params, Expr expr) {
		this(params, NodeEither.<Expr, BlockStmt>left(expr));
	}

	public LambdaExpr(NodeList<FormalParameter> params, BlockStmt block) {
		this(params, NodeEither.<Expr, BlockStmt>right(block));
	}

	public NodeList<FormalParameter> params() {
		return location.safeTraversal(PARAMETERS);
	}

	public LambdaExpr withParams(NodeList<FormalParameter> params) {
		return location.safeTraversalReplace(PARAMETERS, params);
	}

	public LambdaExpr withParams(Mutation<NodeList<FormalParameter>> mutation) {
		return location.safeTraversalMutate(PARAMETERS, mutation);
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

	private static final STraversal<SNodeState> PARAMETERS = SNodeState.childTraversal(0);
	private static final STraversal<SNodeState> BODY = SNodeState.childTraversal(1);

	public final static LexicalShape shape = composite(
			token(LToken.ParenthesisLeft),
			child(PARAMETERS, Expr.listShape),
			token(LToken.ParenthesisRight),
			token(LToken.Arrow).withSpacing(space(), space()),
			child(BODY, leftOrRight())
	);
}
