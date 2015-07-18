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
import org.jlato.internal.bu.*;
import org.jlato.tree.Tree;

public class ThisExpr extends TreeBase<ThisExpr.State, Expr, ThisExpr> implements Expr {

	public final static SKind<ThisExpr.State> kind = new SKind<ThisExpr.State>() {

	};

	private ThisExpr(SLocation<ThisExpr.State> location) {
		super(location);
	}

	public static STree<ThisExpr.State> make(NodeOption<Expr> classExpr) {
		return new STree<ThisExpr.State>(kind, new ThisExpr.State(TreeBase.<SNodeOptionState>nodeOf(classExpr)));
	}

	public ThisExpr(NodeOption<Expr> classExpr) {
		super(new SLocation<ThisExpr.State>(make(classExpr)));
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

	private static final STraversal<ThisExpr.State> CLASS_EXPR = new STraversal<ThisExpr.State>() {

		public STree<?> traverse(ThisExpr.State state) {
			return state.classExpr;
		}

		public ThisExpr.State rebuildParentState(ThisExpr.State state, STree<?> child) {
			return state.withClassExpr((STree) child);
		}

		public STraversal<ThisExpr.State> leftSibling(ThisExpr.State state) {
			return null;
		}

		public STraversal<ThisExpr.State> rightSibling(ThisExpr.State state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			when(childIs(CLASS_EXPR, some()), composite(child(CLASS_EXPR, element()), token(LToken.Dot))),
			token(LToken.This)
	);

	public static class State extends SNodeState<State> {

		public final STree<SNodeOptionState> classExpr;

		State(STree<SNodeOptionState> classExpr) {
			this.classExpr = classExpr;
		}

		public ThisExpr.State withClassExpr(STree<SNodeOptionState> classExpr) {
			return new ThisExpr.State(classExpr);
		}

		public STraversal<ThisExpr.State> firstChild() {
			return null;
		}

		public STraversal<ThisExpr.State> lastChild() {
			return null;
		}

		public Tree instantiate(SLocation<ThisExpr.State> location) {
			return new ThisExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	}
}
