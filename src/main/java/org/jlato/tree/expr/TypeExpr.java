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

import static org.jlato.internal.shapes.LexicalShape.child;

public class TypeExpr extends TreeBase<TypeExpr.State, Expr, TypeExpr> implements Expr {

	public Kind kind() {
		return Kind.TypeExpr;
	}

	private TypeExpr(SLocation<TypeExpr.State> location) {
		super(location);
	}

	public static STree<TypeExpr.State> make(STree<? extends Type.State> type) {
		return new STree<TypeExpr.State>(new TypeExpr.State(type));
	}

	public TypeExpr(Type type) {
		super(new SLocation<TypeExpr.State>(make(TreeBase.<Type.State>treeOf(type))));
	}

	public Type type() {
		return location.safeTraversal(TYPE);
	}

	public TypeExpr withType(Type type) {
		return location.safeTraversalReplace(TYPE, type);
	}

	public TypeExpr withType(Mutation<Type> mutation) {
		return location.safeTraversalMutate(TYPE, mutation);
	}

	public static class State extends SNodeState<State> implements Expr.State {

		public final STree<? extends Type.State> type;

		State(STree<? extends Type.State> type) {
			this.type = type;
		}

		public TypeExpr.State withType(STree<? extends Type.State> type) {
			return new TypeExpr.State(type);
		}

		@Override
		public Kind kind() {
			return Kind.TypeExpr;
		}

		@Override
		protected Tree doInstantiate(SLocation<TypeExpr.State> location) {
			return new TypeExpr(location);
		}

		@Override
		public LexicalShape shape() {
			return shape;
		}

		@Override
		public STraversal firstChild() {
			return TYPE;
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
			TypeExpr.State state = (TypeExpr.State) o;
			if (type == null ? state.type != null : !type.equals(state.type))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			if (type != null) result = 37 * result + type.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<TypeExpr.State, Type.State, Type> TYPE = new STypeSafeTraversal<TypeExpr.State, Type.State, Type>() {

		@Override
		public STree<?> doTraverse(State state) {
			return state.type;
		}

		@Override
		public TypeExpr.State doRebuildParentState(State state, STree<Type.State> child) {
			return state.withType(child);
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

	public final static LexicalShape shape = child(TYPE);
}
