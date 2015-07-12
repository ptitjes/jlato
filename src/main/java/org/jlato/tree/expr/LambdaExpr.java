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
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.NodeList;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.Parameter;
import org.jlato.tree.stmt.BlockStmt;

import static org.jlato.internal.shapes.LexicalShape.Factory.*;
import static org.jlato.internal.shapes.SpacingConstraint.Factory.space;

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

	public LambdaExpr(NodeList<Parameter> parameters, Expr expr) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(parameters, expr, null)))));
	}

	public LambdaExpr(NodeList<Parameter> parameters, BlockStmt body) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(parameters, null, body)))));
	}

	public NodeList<Parameter> parameters() {
		return location.nodeChild(PARAMETERS);
	}

	public LambdaExpr withParameters(NodeList<Parameter> parameters) {
		return location.nodeWithChild(PARAMETERS, parameters);
	}

	public Expr expr() {
		return location.nodeChild(EXPR);
	}

	public LambdaExpr withExpr(Expr expr) {
		return location.nodeWithChild(EXPR, expr);
	}

	public BlockStmt block() {
		return location.nodeChild(BLOCK);
	}

	public LambdaExpr withBlock(BlockStmt block) {
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
