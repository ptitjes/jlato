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
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeOption;
import org.jlato.tree.expr.Expr;

import static org.jlato.internal.shapes.LSCondition.childIs;
import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class AssertStmt extends TreeBase<SNodeState> implements Stmt {

	public final static TreeBase.Kind kind = new TreeBase.Kind() {
		public AssertStmt instantiate(SLocation location) {
			return new AssertStmt(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private AssertStmt(SLocation<SNodeState> location) {
		super(location);
	}

	public AssertStmt(Expr check, NodeOption<Expr> msg) {
		super(new SLocation<SNodeState>(new STree<SNodeState>(kind, new SNodeState(treesOf(check, msg)))));
	}

	public Expr check() {
		return location.safeTraversal(CHECK);
	}

	public AssertStmt withCheck(Expr check) {
		return location.safeTraversalReplace(CHECK, check);
	}

	public AssertStmt withCheck(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(CHECK, mutation);
	}

	public NodeOption<Expr> msg() {
		return location.safeTraversal(MSG);
	}

	public AssertStmt withMsg(NodeOption<Expr> msg) {
		return location.safeTraversalReplace(MSG, msg);
	}

	public AssertStmt withMsg(Mutation<NodeOption<Expr>> mutation) {
		return location.safeTraversalMutate(MSG, mutation);
	}

	private static final STraversal<SNodeState> CHECK = SNodeState.childTraversal(0);
	private static final STraversal<SNodeState> MSG = SNodeState.childTraversal(1);

	public final static LexicalShape shape = composite(
			token(LToken.Assert),
			child(CHECK),
			when(childIs(MSG, some()), composite(
					token(LToken.Colon).withSpacing(space(), space()),
					child(MSG, element())
			)),
			token(LToken.SemiColon)
	);
}
