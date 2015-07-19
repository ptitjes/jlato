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
import org.jlato.tree.Kind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeList;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.BLOCK;
import static org.jlato.printer.IndentationConstraint.indent;
import static org.jlato.printer.IndentationConstraint.unIndent;
import static org.jlato.printer.SpacingConstraint.newLine;
import org.jlato.internal.bu.*;
import org.jlato.tree.Tree;
import org.jlato.internal.bu.*;
import org.jlato.internal.td.*;

public class BlockStmt extends TreeBase<BlockStmt.State, Stmt, BlockStmt> implements Stmt {

	public Kind kind() {
		return Kind.BlockStmt;
	}

	private BlockStmt(SLocation<BlockStmt.State> location) {
		super(location);
	}

	public static STree<BlockStmt.State> make(STree<SNodeListState> stmts) {
		return new STree<BlockStmt.State>(new BlockStmt.State(stmts));
	}

	public BlockStmt(NodeList<Stmt> stmts) {
		super(new SLocation<BlockStmt.State>(make(TreeBase.<SNodeListState>nodeOf(stmts))));
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
