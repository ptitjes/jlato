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
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LexicalShape.*;

import org.jlato.tree.Tree;

public class InstanceOfExpr extends TreeBase<InstanceOfExpr.State, Expr, InstanceOfExpr> implements Expr {

	public Kind kind() {
		return Kind.InstanceOfExpr;
	}

	private InstanceOfExpr(SLocation<InstanceOfExpr.State> location) {
		super(location);
	}

	public static STree<InstanceOfExpr.State> make(Expr expr, Type type) {
		return new STree<InstanceOfExpr.State>(new InstanceOfExpr.State(TreeBase.<Expr.State>nodeOf(expr), TreeBase.<Type.State>nodeOf(type)));
	}

	public InstanceOfExpr(Expr expr, Type type) {
		super(new SLocation<InstanceOfExpr.State>(make(expr, type)));
	}

	public Expr expr() {
		return location.safeTraversal(EXPR);
	}

	public InstanceOfExpr withExpr(Expr expr) {
		return location.safeTraversalReplace(EXPR, expr);
	}

	public InstanceOfExpr withExpr(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(EXPR, mutation);
	}

	public Type type() {
		return location.safeTraversal(TYPE);
	}

	public InstanceOfExpr withType(Type type) {
		return location.safeTraversalReplace(TYPE, type);
	}

	public InstanceOfExpr withType(Mutation<Type> mutation) {
		return location.safeTraversalMutate(TYPE, mutation);
	}

	private static final STraversal<InstanceOfExpr.State> EXPR = new STraversal<InstanceOfExpr.State>() {

		public STree<?> traverse(InstanceOfExpr.State state) {
			return state.expr;
		}

		public InstanceOfExpr.State rebuildParentState(InstanceOfExpr.State state, STree<?> child) {
			return state.withExpr((STree) child);
		}

		public STraversal<InstanceOfExpr.State> leftSibling(InstanceOfExpr.State state) {
			return null;
		}

		public STraversal<InstanceOfExpr.State> rightSibling(InstanceOfExpr.State state) {
			return TYPE;
		}
	};
	private static final STraversal<InstanceOfExpr.State> TYPE = new STraversal<InstanceOfExpr.State>() {

		public STree<?> traverse(InstanceOfExpr.State state) {
			return state.type;
		}

		public InstanceOfExpr.State rebuildParentState(InstanceOfExpr.State state, STree<?> child) {
			return state.withType((STree) child);
		}

		public STraversal<InstanceOfExpr.State> leftSibling(InstanceOfExpr.State state) {
			return EXPR;
		}

		public STraversal<InstanceOfExpr.State> rightSibling(InstanceOfExpr.State state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			child(EXPR),
			token(LToken.InstanceOf),
			child(TYPE)
	);

	public static class State extends SNodeState<State> {

		public final STree<Expr.State> expr;

		public final STree<Type.State> type;

		State(STree<Expr.State> expr, STree<Type.State> type) {
			this.expr = expr;
			this.type = type;
		}

		public InstanceOfExpr.State withExpr(STree<Expr.State> expr) {
			return new InstanceOfExpr.State(expr, type);
		}

		public InstanceOfExpr.State withType(STree<Type.State> type) {
			return new InstanceOfExpr.State(expr, type);
		}

		public STraversal<InstanceOfExpr.State> firstChild() {
			return null;
		}

		public STraversal<InstanceOfExpr.State> lastChild() {
			return null;
		}

		public Tree instantiate(SLocation<InstanceOfExpr.State> location) {
			return new InstanceOfExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}

		public Kind kind() {
			return Kind.InstanceOfExpr;
		}
	}
}
