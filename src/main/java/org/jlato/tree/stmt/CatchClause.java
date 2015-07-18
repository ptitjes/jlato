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
import org.jlato.tree.Tree;
import org.jlato.tree.decl.FormalParameter;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;
import org.jlato.internal.bu.*;
import org.jlato.internal.td.*;

public class CatchClause extends TreeBase<CatchClause.State, Tree, CatchClause> implements Tree {

	public final static SKind<CatchClause.State> kind = new SKind<CatchClause.State>() {
		public CatchClause instantiate(SLocation<CatchClause.State> location) {
			return new CatchClause(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private CatchClause(SLocation<CatchClause.State> location) {
		super(location);
	}

	public static STree<CatchClause.State> make(FormalParameter except, BlockStmt catchBlock) {
		return new STree<CatchClause.State>(kind, new CatchClause.State(TreeBase.<FormalParameter.State>nodeOf(except), TreeBase.<BlockStmt.State>nodeOf(catchBlock)));
	}

	public CatchClause(FormalParameter except, BlockStmt catchBlock) {
		super(new SLocation<CatchClause.State>(make(except, catchBlock)));
	}

	public FormalParameter except() {
		return location.safeTraversal(EXCEPT);
	}

	public CatchClause withExcept(FormalParameter except) {
		return location.safeTraversalReplace(EXCEPT, except);
	}

	public CatchClause withExcept(Mutation<FormalParameter> mutation) {
		return location.safeTraversalMutate(EXCEPT, mutation);
	}

	public BlockStmt catchBlock() {
		return location.safeTraversal(CATCH_BLOCK);
	}

	public CatchClause withCatchBlock(BlockStmt catchBlock) {
		return location.safeTraversalReplace(CATCH_BLOCK, catchBlock);
	}

	public CatchClause withCatchBlock(Mutation<BlockStmt> mutation) {
		return location.safeTraversalMutate(CATCH_BLOCK, mutation);
	}

	private static final STraversal<CatchClause.State> EXCEPT = SNodeState.childTraversal(0);
	private static final STraversal<CatchClause.State> CATCH_BLOCK = SNodeState.childTraversal(1);

	public final static LexicalShape shape = composite(
			token(LToken.Catch).withSpacingBefore(space()),
			token(LToken.ParenthesisLeft).withSpacingBefore(space()),
			child(EXCEPT),
			token(LToken.ParenthesisRight).withSpacingAfter(space()),
			child(CATCH_BLOCK)
	);

	public static final LexicalShape listShape = list();

	public static class State extends SNodeState<State> {

		public final STree<FormalParameter.State> except;

		public final STree<BlockStmt.State> catchBlock;

		State(STree<FormalParameter.State> except, STree<BlockStmt.State> catchBlock) {
			this.except = except;
			this.catchBlock = catchBlock;
		}

		public CatchClause.State withExcept(STree<FormalParameter.State> except) {
			return new CatchClause.State(except, catchBlock);
		}

		public CatchClause.State withCatchBlock(STree<BlockStmt.State> catchBlock) {
			return new CatchClause.State(except, catchBlock);
		}

		public STraversal<CatchClause.State> firstChild() {
			return null;
		}

		public STraversal<CatchClause.State> lastChild() {
			return null;
		}
	}
}
