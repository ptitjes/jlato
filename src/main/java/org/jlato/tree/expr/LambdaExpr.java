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
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.FormalParameter;
import org.jlato.tree.stmt.BlockStmt;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class LambdaExpr extends Expr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public LambdaExpr instantiate(SLocation location) {
			return new LambdaExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private LambdaExpr(SLocation location) {
		super(location);
	}

	public LambdaExpr(NodeList<FormalParameter> params, Expr expr, BlockStmt body) {
		super(new SLocation(new STree(kind, new SNodeState(treesOf(params, expr, body)))));
	}

	public LambdaExpr(NodeList<FormalParameter> params, Expr expr) {
		this(params, expr, null);
	}

	public LambdaExpr(NodeList<FormalParameter> params, BlockStmt body) {
		this(params, null, body);
	}

	public NodeList<FormalParameter> params() {
		return location.nodeChild(PARAMETERS);
	}

	public LambdaExpr withParams(NodeList<FormalParameter> params) {
		return location.nodeWithChild(PARAMETERS, params);
	}

	public LambdaExpr withParams(Mutation<NodeList<FormalParameter>> params) {
		return location.nodeWithChild(PARAMETERS, params);
	}

	public Expr expr() {
		return location.nodeChild(EXPR);
	}

	public LambdaExpr withExpr(Expr expr) {
		return location.nodeWithChild(EXPR, expr);
	}

	public LambdaExpr withExpr(Mutation<Expr> expr) {
		return location.nodeWithChild(EXPR, expr);
	}

	public BlockStmt block() {
		return location.nodeChild(BLOCK);
	}

	public LambdaExpr withBlock(BlockStmt block) {
		return location.nodeWithChild(BLOCK, block);
	}

	public LambdaExpr withBlock(Mutation<BlockStmt> block) {
		return location.nodeWithChild(BLOCK, block);
	}

	private static final int PARAMETERS = 0;
	private static final int EXPR = 1;
	private static final int BLOCK = 2;

	public final static LexicalShape shape = composite(
			token(LToken.ParenthesisLeft),
			nonNullChild(PARAMETERS, composite(child(PARAMETERS, list(token(LToken.Comma).withSpacingAfter(space()))))),
			token(LToken.ParenthesisRight),
			token(LToken.Arrow).withSpacing(space(), space()),
			nonNullChild(EXPR, child(EXPR), child(BLOCK))
	);
}
