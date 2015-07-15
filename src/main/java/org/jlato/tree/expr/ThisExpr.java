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

package org.jlato.tree.expr;

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

import static org.jlato.internal.shapes.LSCondition.childIs;
import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;

public class ThisExpr extends TreeBase<SNodeState> implements Expr {

	public final static SKind<SNodeState> kind = new SKind<SNodeState>() {
		public ThisExpr instantiate(SLocation<SNodeState> location) {
			return new ThisExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private ThisExpr(SLocation<SNodeState> location) {
		super(location);
	}

	public ThisExpr(NodeOption<Expr> classExpr) {
		super(new SLocation<SNodeState>(new STree<SNodeState>(kind, new SNodeState(treesOf(classExpr)))));
	}

	public NodeOption<Expr> classExpr() {
		return location.safeTraversal(CLASS_EXPR);
	}

	public ThisExpr withClassExpr(NodeOption<Expr> classExpr) {
		return location.safeTraversalReplace(CLASS_EXPR, classExpr);
	}

	public ThisExpr withClassExpr(Mutation<NodeOption<Expr>> mutation) {
		return location.safeTraversalMutate(CLASS_EXPR, mutation);
	}

	private static final STraversal<SNodeState> CLASS_EXPR = SNodeState.childTraversal(0);

	public final static LexicalShape shape = composite(
			when(childIs(CLASS_EXPR, some()), composite(child(CLASS_EXPR, element()), token(LToken.Dot))),
			token(LToken.This)
	);
}
