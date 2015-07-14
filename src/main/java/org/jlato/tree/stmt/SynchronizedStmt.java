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
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class SynchronizedStmt extends Stmt {

	public final static Tree.Kind kind = new Tree.Kind() {
		public SynchronizedStmt instantiate(SLocation location) {
			return new SynchronizedStmt(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private SynchronizedStmt(SLocation location) {
		super(location);
	}

	public SynchronizedStmt(Expr expr, BlockStmt block) {
		super(new SLocation(new STree(kind, new SNodeState(treesOf(expr, block)))));
	}

	public Expr expr() {
		return location.nodeChild(EXPR);
	}

	public SynchronizedStmt withExpr(Expr expr) {
		return location.nodeWithChild(EXPR, expr);
	}

	public SynchronizedStmt withExpr(Mutation<Expr> mutation) {
		return location.nodeMutateChild(EXPR, mutation);
	}

	public BlockStmt block() {
		return location.nodeChild(BLOCK);
	}

	public SynchronizedStmt withBlock(BlockStmt block) {
		return location.nodeWithChild(BLOCK, block);
	}

	public SynchronizedStmt withBlock(Mutation<BlockStmt> mutation) {
		return location.nodeMutateChild(BLOCK, mutation);
	}

	private static final int EXPR = 0;
	private static final int BLOCK = 1;

	public final static LexicalShape shape = composite(
			token(LToken.Synchronized),
			token(LToken.ParenthesisLeft).withSpacingBefore(space()),
			child(EXPR),
			token(LToken.ParenthesisRight).withSpacingAfter(space()),
			child(BLOCK)
	);
}
