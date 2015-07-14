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
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;

import static org.jlato.internal.shapes.LSCondition.childIs;
import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;

public class ReturnStmt extends Stmt {

	public final static Tree.Kind kind = new Tree.Kind() {
		public ReturnStmt instantiate(SLocation location) {
			return new ReturnStmt(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private ReturnStmt(SLocation location) {
		super(location);
	}

	public ReturnStmt(NodeOption<Expr> expr) {
		super(new SLocation(new STree(kind, new SNodeState(treesOf(expr)))));
	}

	public NodeOption<Expr> expr() {
		return location.nodeChild(EXPR);
	}

	public ReturnStmt withExpr(NodeOption<Expr> expr) {
		return location.nodeWithChild(EXPR, expr);
	}

	public ReturnStmt withExpr(Mutation<NodeOption<Expr>> mutation) {
		return location.nodeMutateChild(EXPR, mutation);
	}

	private static final int EXPR = 0;

	public final static LexicalShape shape = composite(
			token(LToken.Return),
			when(childIs(EXPR, some()), child(EXPR, element())),
			token(LToken.SemiColon)
	);
}
