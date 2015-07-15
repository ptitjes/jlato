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

package org.jlato.tree.decl;

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
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;

import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class VariableDeclarator extends TreeBase<SNodeState> implements Tree {

	public final static SKind<SNodeState> kind = new SKind<SNodeState>() {
		public VariableDeclarator instantiate(SLocation<SNodeState> location) {
			return new VariableDeclarator(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private VariableDeclarator(SLocation<SNodeState> location) {
		super(location);
	}

	public VariableDeclarator(VariableDeclaratorId id, NodeOption<Expr> init) {
		super(new SLocation<SNodeState>(new STree<SNodeState>(kind, new SNodeState(treesOf(id, init)))));
	}

	public VariableDeclaratorId id() {
		return location.safeTraversal(ID);
	}

	public VariableDeclarator withId(VariableDeclaratorId id) {
		return location.safeTraversalReplace(ID, id);
	}

	public VariableDeclarator withId(Mutation<VariableDeclaratorId> mutation) {
		return location.safeTraversalMutate(ID, mutation);
	}

	public NodeOption<Expr> init() {
		return location.safeTraversal(INIT);
	}

	public VariableDeclarator withInit(NodeOption<Expr> init) {
		return location.safeTraversalReplace(INIT, init);
	}

	public VariableDeclarator withInit(Mutation<NodeOption<Expr>> mutation) {
		return location.safeTraversalMutate(INIT, mutation);
	}

	private static final STraversal<SNodeState> ID = SNodeState.childTraversal(0);
	private static final STraversal<SNodeState> INIT = SNodeState.childTraversal(1);

	public static final LexicalShape initializerShape = composite(
			token(LToken.Assign).withSpacing(space(), space()),
			element()
	);

	public final static LexicalShape shape = composite(
			child(ID),
			child(INIT, when(some(), initializerShape))
	);

	public final static LexicalShape listShape = list(none(), token(LToken.Comma).withSpacingAfter(space()), none());
}
