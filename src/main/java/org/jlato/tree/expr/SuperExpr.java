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
import org.jlato.tree.Kind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeOption;

import static org.jlato.internal.shapes.LSCondition.childIs;
import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;
import org.jlato.internal.bu.*;
import org.jlato.tree.Tree;

public class SuperExpr extends TreeBase<SuperExpr.State, Expr, SuperExpr> implements Expr {

	public Kind kind() {
		return Kind.SuperExpr;
	}

	private SuperExpr(SLocation<SuperExpr.State> location) {
		super(location);
	}

	public static STree<SuperExpr.State> make(NodeOption<Expr> classExpr) {
		return new STree<SuperExpr.State>(new SuperExpr.State(TreeBase.<SNodeOptionState>nodeOf(classExpr)));
	}

	public SuperExpr(NodeOption<Expr> classExpr) {
		super(new SLocation<SuperExpr.State>(make(classExpr)));
	}

	public NodeOption<Expr> classExpr() {
		return location.safeTraversal(CLASS_EXPR);
	}

	public SuperExpr withClassExpr(NodeOption<Expr> classExpr) {
		return location.safeTraversalReplace(CLASS_EXPR, classExpr);
	}

	public SuperExpr withClassExpr(Mutation<NodeOption<Expr>> mutation) {
		return location.safeTraversalMutate(CLASS_EXPR, mutation);
	}

	private static final STraversal<SuperExpr.State> CLASS_EXPR = new STraversal<SuperExpr.State>() {

		public STree<?> traverse(SuperExpr.State state) {
			return state.classExpr;
		}

		public SuperExpr.State rebuildParentState(SuperExpr.State state, STree<?> child) {
			return state.withClassExpr((STree) child);
		}

		public STraversal<SuperExpr.State> leftSibling(SuperExpr.State state) {
			return null;
		}

		public STraversal<SuperExpr.State> rightSibling(SuperExpr.State state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			when(childIs(CLASS_EXPR, some()), composite(child(CLASS_EXPR, element()), token(LToken.Dot))),
			token(LToken.Super)
	);

	public static class State extends SNodeState<State> {

		public final STree<SNodeOptionState> classExpr;

		State(STree<SNodeOptionState> classExpr) {
			this.classExpr = classExpr;
		}

		public SuperExpr.State withClassExpr(STree<SNodeOptionState> classExpr) {
			return new SuperExpr.State(classExpr);
		}

		public STraversal<SuperExpr.State> firstChild() {
			return CLASS_EXPR;
		}

		public STraversal<SuperExpr.State> lastChild() {
			return CLASS_EXPR;
		}

		public Tree instantiate(SLocation<SuperExpr.State> location) {
			return new SuperExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}

		public Kind kind() {
			return Kind.SuperExpr;
		}
	}
}
