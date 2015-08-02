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
import org.jlato.tree.Tree;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LexicalShape.*;

public class InstanceOfExpr extends TreeBase<InstanceOfExpr.State, Expr, InstanceOfExpr> implements Expr {

	public Kind kind() {
		return Kind.InstanceOfExpr;
	}

	private InstanceOfExpr(SLocation<InstanceOfExpr.State> location) {
		super(location);
	}

	public static STree<InstanceOfExpr.State> make(STree<? extends Expr.State> expr, STree<? extends Type.State> type) {
		return new STree<InstanceOfExpr.State>(new InstanceOfExpr.State(expr, type));
	}

	public InstanceOfExpr(Expr expr, Type type) {
		super(new SLocation<InstanceOfExpr.State>(make(TreeBase.<Expr.State>treeOf(expr), TreeBase.<Type.State>treeOf(type))));
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

	public static class State extends SNodeState<State> implements Expr.State {

		public final STree<? extends Expr.State> expr;

		public final STree<? extends Type.State> type;

		State(STree<? extends Expr.State> expr, STree<? extends Type.State> type) {
			this.expr = expr;
			this.type = type;
		}

		public InstanceOfExpr.State withExpr(STree<? extends Expr.State> expr) {
			return new InstanceOfExpr.State(expr, type);
		}

		public InstanceOfExpr.State withType(STree<? extends Type.State> type) {
			return new InstanceOfExpr.State(expr, type);
		}

		@Override
		public Kind kind() {
			return Kind.InstanceOfExpr;
		}

		@Override
		protected Tree doInstantiate(SLocation<InstanceOfExpr.State> location) {
			return new InstanceOfExpr(location);
		}

		@Override
		public LexicalShape shape() {
			return shape;
		}

		@Override
		public STraversal firstChild() {
			return EXPR;
		}

		@Override
		public STraversal lastChild() {
			return TYPE;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			State state = (State) o;
			if (expr == null ? state.expr != null : !expr.equals(state.expr))
				return false;
			if (type == null ? state.type != null : !type.equals(state.type))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			if (expr != null) result = 37 * result + expr.hashCode();
			if (type != null) result = 37 * result + type.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<InstanceOfExpr.State, Expr.State, Expr> EXPR = new STypeSafeTraversal<InstanceOfExpr.State, Expr.State, Expr>() {

		@Override
		public STree<?> doTraverse(InstanceOfExpr.State state) {
			return state.expr;
		}

		@Override
		public InstanceOfExpr.State doRebuildParentState(InstanceOfExpr.State state, STree<Expr.State> child) {
			return state.withExpr(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return TYPE;
		}
	};

	private static STypeSafeTraversal<InstanceOfExpr.State, Type.State, Type> TYPE = new STypeSafeTraversal<InstanceOfExpr.State, Type.State, Type>() {

		@Override
		public STree<?> doTraverse(InstanceOfExpr.State state) {
			return state.type;
		}

		@Override
		public InstanceOfExpr.State doRebuildParentState(InstanceOfExpr.State state, STree<Type.State> child) {
			return state.withType(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return EXPR;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(EXPR),
			keyword(LToken.InstanceOf),
			child(TYPE)
	);
}
