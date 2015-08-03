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
import org.jlato.internal.shapes.LSToken;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;

import static org.jlato.internal.shapes.LexicalShape.token;

public class LiteralExpr<T> extends TreeBase<LiteralExpr.State, Expr, LiteralExpr> implements Expr {

	public Kind kind() {
		return Kind.LiteralExpr;
	}

	public static STree<LiteralExpr.State> make(Class<?> literalClass, String literalString) {
		return new STree<LiteralExpr.State>(new LiteralExpr.State(literalClass, literalString));
	}

	protected LiteralExpr(SLocation<LiteralExpr.State> location) {
		super(location);
	}

	public LiteralExpr(Class<T> literalClass, String literalString) {
		super(new SLocation<LiteralExpr.State>(make(literalClass, literalString)));
	}

	@SuppressWarnings("unchecked")
	public Class<T> literalClass() {
		return (Class<T>) location.tree.state.literalClass;
	}

	public String literalString() {
		return location.tree.state.literalString;
	}

	@SuppressWarnings("unchecked")
	public T value() {
		final Class<?> literalClass = location.tree.state.literalClass;
		final String literalString = location.tree.state.literalString;
		return (T) Literals.valueFor(literalClass, literalString);
	}

	@SuppressWarnings("unchecked")
	public LiteralExpr<T> withValue(T value) {
		final Class<?> literalClass = location.tree.state.literalClass;
		return (LiteralExpr<T>) location.safePropertyReplace(LITERAL_STRING, Literals.from((Class<T>) literalClass, value));
	}

	public static class State extends SNodeState<State> implements Expr.State {

		public final Class<?> literalClass;

		public final String literalString;

		State(Class<?> literalClass, String literalString) {
			this.literalClass = literalClass;
			this.literalString = literalString;
		}

		public LiteralExpr.State withLiteralClass(Class<?> literalClass) {
			return new LiteralExpr.State(literalClass, literalString);
		}

		public LiteralExpr.State withLiteralString(String literalString) {
			return new LiteralExpr.State(literalClass, literalString);
		}

		@Override
		public Kind kind() {
			return Kind.LiteralExpr;
		}

		@Override
		protected Tree doInstantiate(SLocation<LiteralExpr.State> location) {
			return new LiteralExpr(location);
		}

		@Override
		public LexicalShape shape() {
			return shape;
		}

		@Override
		public STraversal firstChild() {
			return null;
		}

		@Override
		public STraversal lastChild() {
			return null;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			State state = (State) o;
			if (literalClass == null ? state.literalClass != null : !literalClass.equals(state.literalClass))
				return false;
			if (literalString == null ? state.literalString != null : !literalString.equals(state.literalString))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			if (literalClass != null) result = 37 * result + literalClass.hashCode();
			if (literalString != null) result = 37 * result + literalString.hashCode();
			return result;
		}
	}

	private static STypeSafeProperty<LiteralExpr.State, Class<?>> LITERAL_CLASS = new STypeSafeProperty<LiteralExpr.State, Class<?>>() {

		@Override
		public Class<?> doRetrieve(State state) {
			return state.literalClass;
		}

		@Override
		public LiteralExpr.State doRebuildParentState(State state, Class<?> value) {
			return state.withLiteralClass(value);
		}
	};

	private static STypeSafeProperty<LiteralExpr.State, String> LITERAL_STRING = new STypeSafeProperty<LiteralExpr.State, String>() {

		@Override
		public String doRetrieve(State state) {
			return state.literalString;
		}

		@Override
		public LiteralExpr.State doRebuildParentState(State state, String value) {
			return state.withLiteralString(value);
		}
	};

	public final static LexicalShape shape = token(new LSToken.Provider() {
		public LToken tokenFor(STree tree) {
			final Class<?> literalClass = ((State) tree.state).literalClass;
			final String literalString = ((State) tree.state).literalString;
			return new LToken(0, literalString); // TODO Fix
		}
	});
}
