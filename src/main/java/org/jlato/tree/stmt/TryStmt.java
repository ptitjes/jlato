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
import org.jlato.tree.NodeOption;
import org.jlato.tree.expr.VariableDeclarationExpr;

import static org.jlato.internal.shapes.LSCondition.childIs;
import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;
import org.jlato.internal.bu.*;
import org.jlato.tree.Tree;
import org.jlato.internal.bu.*;
import org.jlato.internal.td.*;

public class TryStmt extends TreeBase<TryStmt.State, Stmt, TryStmt> implements Stmt {

	public Kind kind() {
		return Kind.TryStmt;
	}

	private TryStmt(SLocation<TryStmt.State> location) {
		super(location);
	}

	public static STree<TryStmt.State> make(STree<SNodeListState> resources, STree<BlockStmt.State> tryBlock, STree<SNodeListState> catchs, STree<SNodeOptionState> finallyBlock) {
		return new STree<TryStmt.State>(new TryStmt.State(resources, tryBlock, catchs, finallyBlock));
	}

	public TryStmt(NodeList<VariableDeclarationExpr> resources, BlockStmt tryBlock, NodeList<CatchClause> catchs, NodeOption<BlockStmt> finallyBlock) {
		super(new SLocation<TryStmt.State>(make(TreeBase.<SNodeListState>nodeOf(resources), TreeBase.<BlockStmt.State>nodeOf(tryBlock), TreeBase.<SNodeListState>nodeOf(catchs), TreeBase.<SNodeOptionState>nodeOf(finallyBlock))));
	}

	public NodeList<VariableDeclarationExpr> resources() {
		return location.safeTraversal(RESOURCES);
	}

	public TryStmt withResources(NodeList<VariableDeclarationExpr> resources) {
		return location.safeTraversalReplace(RESOURCES, resources);
	}

	public TryStmt withResources(Mutation<NodeList<VariableDeclarationExpr>> mutation) {
		return location.safeTraversalMutate(RESOURCES, mutation);
	}

	public BlockStmt tryBlock() {
		return location.safeTraversal(TRY_BLOCK);
	}

	public TryStmt withTryBlock(BlockStmt tryBlock) {
		return location.safeTraversalReplace(TRY_BLOCK, tryBlock);
	}

	public TryStmt withTryBlock(Mutation<BlockStmt> mutation) {
		return location.safeTraversalMutate(TRY_BLOCK, mutation);
	}

	public NodeList<CatchClause> catchs() {
		return location.safeTraversal(CATCHS);
	}

	public TryStmt withCatchs(NodeList<CatchClause> catchs) {
		return location.safeTraversalReplace(CATCHS, catchs);
	}

	public TryStmt withCatchs(Mutation<NodeList<CatchClause>> mutation) {
		return location.safeTraversalMutate(CATCHS, mutation);
	}

	public NodeOption<BlockStmt> finallyBlock() {
		return location.safeTraversal(FINALLY_BLOCK);
	}

	public TryStmt withFinallyBlock(NodeOption<BlockStmt> finallyBlock) {
		return location.safeTraversalReplace(FINALLY_BLOCK, finallyBlock);
	}

	public TryStmt withFinallyBlock(Mutation<NodeOption<BlockStmt>> mutation) {
		return location.safeTraversalMutate(FINALLY_BLOCK, mutation);
	}

	public final static LexicalShape shape = composite(
			token(LToken.Try).withSpacingAfter(space()),
			child(RESOURCES, VariableDeclarationExpr.resourcesShape),
			child(TRY_BLOCK),
			child(CATCHS, CatchClause.listShape),
			when(childIs(FINALLY_BLOCK, some()), composite(
					token(LToken.Finally).withSpacing(space(), space()),
					child(FINALLY_BLOCK, element())
			))
	);
}
