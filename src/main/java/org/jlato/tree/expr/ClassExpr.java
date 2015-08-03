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
import org.jlato.tree.Tree;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

import static org.jlato.internal.shapes.LexicalShape.*;

public class ClassExpr extends TreeBase<ClassExpr.State, Expr, ClassExpr> implements Expr {

	public Kind kind() {
		return Kind.ClassExpr;
	}

	private ClassExpr(SLocation<ClassExpr.State> location) {
		super(location);
	}

	public static STree<ClassExpr.State> make(STree<? extends Type.State> type) {
		return new STree<ClassExpr.State>(new ClassExpr.State(type));
	}

	public ClassExpr(Type type) {
		super(new SLocation<ClassExpr.State>(make(TreeBase.<Type.State>treeOf(type))));
	}

	public Type type() {
		return location.safeTraversal(TYPE);
	}

	public ClassExpr withType(Type type) {
		return location.safeTraversalReplace(TYPE, type);
	}

	public ClassExpr withType(Mutation<Type> mutation) {
		return location.safeTraversalMutate(TYPE, mutation);
	}

	public static class State extends SNodeState<State> implements Expr.State {

		public final STree<? extends Type.State> type;

		State(STree<? extends Type.State> type) {
			this.type = type;
		}

		public ClassExpr.State withType(STree<? extends Type.State> type) {
			return new ClassExpr.State(type);
		}

		@Override
		public Kind kind() {
			return Kind.ClassExpr;
		}

		@Override
		protected Tree doInstantiate(SLocation<ClassExpr.State> location) {
			return new ClassExpr(location);
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
			State state = (State) o;
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

	private static STypeSafeTraversal<ClassExpr.State, Type.State, Type> TYPE = new STypeSafeTraversal<ClassExpr.State, Type.State, Type>() {

		@Override
		public STree<?> doTraverse(ClassExpr.State state) {
			return state.type;
		}

		@Override
		public ClassExpr.State doRebuildParentState(ClassExpr.State state, STree<Type.State> child) {
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

	public static final LexicalShape shape = composite(
			child(TYPE),
			token(LToken.Dot), token(LToken.Class)
	);
}
