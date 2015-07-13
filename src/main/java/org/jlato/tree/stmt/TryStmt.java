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
import org.jlato.tree.NodeList;
import org.jlato.tree.Rewrite;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.VariableDeclarationExpr;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class TryStmt extends Stmt {

	public final static Tree.Kind kind = new Tree.Kind() {
		public TryStmt instantiate(SLocation location) {
			return new TryStmt(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private TryStmt(SLocation location) {
		super(location);
	}

	public TryStmt(NodeList<VariableDeclarationExpr> resources, BlockStmt tryBlock, NodeList<CatchClause> catchs, BlockStmt finallyBlock) {
		super(new SLocation(new STree(kind, new SNodeState(treesOf(resources, tryBlock, catchs, finallyBlock)))));
	}

	public NodeList<VariableDeclarationExpr> resources() {
		return location.nodeChild(RESOURCES);
	}

	public TryStmt withResources(NodeList<VariableDeclarationExpr> resources) {
		return location.nodeWithChild(RESOURCES, resources);
	}

	public TryStmt withResources(Rewrite<NodeList<VariableDeclarationExpr>> resources) {
		return location.nodeWithChild(RESOURCES, resources);
	}

	public BlockStmt tryBlock() {
		return location.nodeChild(TRY_BLOCK);
	}

	public TryStmt withTryBlock(BlockStmt tryBlock) {
		return location.nodeWithChild(TRY_BLOCK, tryBlock);
	}

	public TryStmt withTryBlock(Rewrite<BlockStmt> tryBlock) {
		return location.nodeWithChild(TRY_BLOCK, tryBlock);
	}

	public NodeList<CatchClause> catchs() {
		return location.nodeChild(CATCHS);
	}

	public TryStmt withCatchs(NodeList<CatchClause> catchs) {
		return location.nodeWithChild(CATCHS, catchs);
	}

	public TryStmt withCatchs(Rewrite<NodeList<CatchClause>> catchs) {
		return location.nodeWithChild(CATCHS, catchs);
	}

	public BlockStmt finallyBlock() {
		return location.nodeChild(FINALLY_BLOCK);
	}

	public TryStmt withFinallyBlock(BlockStmt finallyBlock) {
		return location.nodeWithChild(FINALLY_BLOCK, finallyBlock);
	}

	public TryStmt withFinallyBlock(Rewrite<BlockStmt> finallyBlock) {
		return location.nodeWithChild(FINALLY_BLOCK, finallyBlock);
	}

	private static final int RESOURCES = 0;
	private static final int TRY_BLOCK = 1;
	private static final int CATCHS = 2;
	private static final int FINALLY_BLOCK = 3;

	public final static LexicalShape shape = composite(
			token(LToken.Try).withSpacingAfter(space()),
			child(RESOURCES, VariableDeclarationExpr.resourcesShape),
			child(TRY_BLOCK),
			child(CATCHS, CatchClause.listShape),
			nonNullChild(FINALLY_BLOCK,
					composite(
							token(LToken.Finally).withSpacing(space(), space()),
							child(FINALLY_BLOCK)
					)
			)
	);
}
