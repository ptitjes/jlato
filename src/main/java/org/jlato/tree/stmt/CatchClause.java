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
import org.jlato.tree.decl.FormalParameter;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class CatchClause extends Tree {

	public final static Tree.Kind kind = new Tree.Kind() {
		public CatchClause instantiate(SLocation location) {
			return new CatchClause(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private CatchClause(SLocation location) {
		super(location);
	}

	public CatchClause(FormalParameter except, BlockStmt catchBlock) {
		super(new SLocation(new STree(kind, new SNodeState(treesOf(except, catchBlock)))));
	}

	public FormalParameter except() {
		return location.nodeChild(EXCEPT);
	}

	public CatchClause withExcept(FormalParameter except) {
		return location.nodeWithChild(EXCEPT, except);
	}

	public CatchClause withExcept(Mutation<FormalParameter> mutation) {
		return location.nodeMutateChild(EXCEPT, mutation);
	}

	public BlockStmt catchBlock() {
		return location.nodeChild(CATCH_BLOCK);
	}

	public CatchClause withCatchBlock(BlockStmt catchBlock) {
		return location.nodeWithChild(CATCH_BLOCK, catchBlock);
	}

	public CatchClause withCatchBlock(Mutation<BlockStmt> mutation) {
		return location.nodeMutateChild(CATCH_BLOCK, mutation);
	}

	private static final int EXCEPT = 0;
	private static final int CATCH_BLOCK = 1;

	public final static LexicalShape shape = composite(
			token(LToken.Catch).withSpacingBefore(space()),
			token(LToken.ParenthesisLeft).withSpacingBefore(space()),
			child(EXCEPT),
			token(LToken.ParenthesisRight).withSpacingAfter(space()),
			child(CATCH_BLOCK)
	);

	public static final LexicalShape listShape = list();
}
