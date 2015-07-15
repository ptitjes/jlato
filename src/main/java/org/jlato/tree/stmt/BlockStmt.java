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
import org.jlato.tree.NodeList;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.BLOCK;
import static org.jlato.printer.IndentationConstraint.indent;
import static org.jlato.printer.IndentationConstraint.unIndent;
import static org.jlato.printer.SpacingConstraint.newLine;

public class BlockStmt extends TreeBase<SNodeState> implements Stmt {

	public final static SKind<SNodeState> kind = new SKind<SNodeState>() {
		public BlockStmt instantiate(SLocation<SNodeState> location) {
			return new BlockStmt(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private BlockStmt(SLocation<SNodeState> location) {
		super(location);
	}

	public BlockStmt(NodeList<Stmt> stmts) {
		super(new SLocation<SNodeState>(new STree<SNodeState>(kind, new SNodeState(treesOf(stmts)))));
	}

	public NodeList<Stmt> stmts() {
		return location.safeTraversal(STMTS);
	}

	public BlockStmt withStmts(NodeList<Stmt> stmts) {
		return location.safeTraversalReplace(STMTS, stmts);
	}

	public BlockStmt withStmts(Mutation<NodeList<Stmt>> mutation) {
		return location.safeTraversalMutate(STMTS, mutation);
	}

	private static final STraversal<SNodeState> STMTS = SNodeState.childTraversal(0);

	public final static LexicalShape shape = composite(
			nonEmptyChildren(STMTS,
					composite(
							token(LToken.BraceLeft)
									.withSpacingAfter(newLine())
									.withIndentationAfter(indent(BLOCK)),
							child(STMTS, listShape),
							token(LToken.BraceRight)
									.withIndentationBefore(unIndent(BLOCK))
									.withSpacingBefore(newLine())
					),
					composite(
							token(LToken.BraceLeft)
									.withSpacingAfter(newLine())
									.withIndentationAfter(indent(BLOCK)),
							token(LToken.BraceRight)
									.withIndentationBefore(unIndent(BLOCK))
					)
			)
	);
}
