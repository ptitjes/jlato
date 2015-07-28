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

import org.jlato.internal.bu.*;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;

import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;

public class ThisExpr extends TreeBase<ThisExpr.State, Expr, ThisExpr> implements Expr {

	public Kind kind() {
		return Kind.ThisExpr;
	}

	private ThisExpr(SLocation<ThisExpr.State> location) {
		super(location);
	}

	public static STree<ThisExpr.State> make(STree<SNodeOptionState> classExpr) {
		return new STree<ThisExpr.State>(new ThisExpr.State(classExpr));
	}

	public ThisExpr(NodeOption<Expr> classExpr) {
		super(new SLocation<ThisExpr.State>(make(TreeBase.<SNodeOptionState>treeOf(classExpr))));
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

	public static class State extends SNodeState<State> implements Expr.State {

		public final STree<SNodeOptionState> classExpr;

		State(STree<SNodeOptionState> classExpr) {
			this.classExpr = classExpr;
		}

		public ThisExpr.State withClassExpr(STree<SNodeOptionState> classExpr) {
			return new ThisExpr.State(classExpr);
		}

		@Override
		public Kind kind() {
			return Kind.ThisExpr;
		}

		@Override
		protected Tree doInstantiate(SLocation<ThisExpr.State> location) {
			return new ThisExpr(location);
		}

		@Override
		public LexicalShape shape() {
			return shape;
		}

		@Override
		public STraversal firstChild() {
			return CLASS_EXPR;
		}

		@Override
		public STraversal lastChild() {
			return CLASS_EXPR;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			ThisExpr.State state = (ThisExpr.State) o;
			if (!classExpr.equals(state.classExpr))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			result = 37 * result + classExpr.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<ThisExpr.State, SNodeOptionState, NodeOption<Expr>> CLASS_EXPR = new STypeSafeTraversal<ThisExpr.State, SNodeOptionState, NodeOption<Expr>>() {

		@Override
		public STree<?> doTraverse(State state) {
			return state.classExpr;
		}

		@Override
		public ThisExpr.State doRebuildParentState(State state, STree<SNodeOptionState> child) {
			return state.withClassExpr(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			child(CLASS_EXPR, when(some(), composite(element(), token(LToken.Dot)))),
			token(LToken.This)
	);
}
