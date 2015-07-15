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
import org.jlato.internal.bu.STree; import org.jlato.internal.td.TreeBase; import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation; import org.jlato.internal.td.TreeBase; import org.jlato.internal.bu.SNodeState;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree; import org.jlato.internal.td.TreeBase; import org.jlato.internal.bu.SNodeState;
import org.jlato.tree.decl.FormalParameter;
import org.jlato.tree.stmt.BlockStmt;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;
import org.jlato.internal.bu.STraversal;

public class LambdaExpr extends TreeBase<SNodeState> implements Expr {

	public final static TreeBase.Kind kind = new TreeBase.Kind() {
		public LambdaExpr instantiate(SLocation location) {
			return new LambdaExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private LambdaExpr(SLocation<SNodeState> location) {
		super(location);
	}

	public LambdaExpr(NodeList<FormalParameter> params, Expr expr, BlockStmt body) {
		super(new SLocation<SNodeState>(new STree<SNodeState>(kind, new SNodeState(treesOf(params, expr, body)))));
	}

	public LambdaExpr(NodeList<FormalParameter> params, Expr expr) {
		this(params, expr, null);
	}

	public LambdaExpr(NodeList<FormalParameter> params, BlockStmt body) {
		this(params, null, body);
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

	public Expr expr() {
		return location.safeTraversal(EXPR);
	}

	public LambdaExpr withExpr(Expr expr) {
		return location.safeTraversalReplace(EXPR, expr);
	}

	public LambdaExpr withExpr(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(EXPR, mutation);
	}

	public BlockStmt block() {
		return location.safeTraversal(BLOCK);
	}

	public LambdaExpr withBlock(BlockStmt block) {
		return location.safeTraversalReplace(BLOCK, block);
	}

	public LambdaExpr withBlock(Mutation<BlockStmt> mutation) {
		return location.safeTraversalMutate(BLOCK, mutation);
	}

	private static final STraversal<SNodeState> PARAMETERS = SNodeState.childTraversal(0);
	private static final STraversal<SNodeState> EXPR = SNodeState.childTraversal(1);
	private static final STraversal<SNodeState> BLOCK = SNodeState.childTraversal(2);

	public final static LexicalShape shape = composite(
			token(LToken.ParenthesisLeft),
			child(PARAMETERS, list(token(LToken.Comma).withSpacingAfter(space()))),
			token(LToken.ParenthesisRight),
			token(LToken.Arrow).withSpacing(space(), space()),
			nonNullChild(EXPR, child(EXPR), child(BLOCK))
	);
}
