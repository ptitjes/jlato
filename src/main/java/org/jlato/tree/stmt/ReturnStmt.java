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
import org.jlato.internal.td.SKind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeOption;
import org.jlato.tree.expr.Expr;

import static org.jlato.internal.shapes.LSCondition.childIs;
import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;

public class ReturnStmt extends TreeBase<SNodeState, Stmt, ReturnStmt> implements Stmt {

	public final static SKind<SNodeState> kind = new SKind<SNodeState>() {
		public ReturnStmt instantiate(SLocation<SNodeState> location) {
			return new ReturnStmt(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private ReturnStmt(SLocation<SNodeState> location) {
		super(location);
	}

	public ReturnStmt(NodeOption<Expr> expr) {
		super(new SLocation<SNodeState>(new STree<SNodeState>(kind, new SNodeState(treesOf(expr)))));
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

	private static final STraversal<SNodeState> EXPR = SNodeState.childTraversal(0);

	public final static LexicalShape shape = composite(
			token(LToken.Return),
			when(childIs(EXPR, some()), child(EXPR, element())),
			token(LToken.SemiColon)
	);
}
