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
import org.jlato.internal.bu.Literals;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LSToken;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Tree;

import static org.jlato.internal.shapes.LexicalShape.token;

public class LiteralExpr<T> extends TreeBase<SNodeState> implements Expr {

	public final static Kind<Tree> kind = new Kind<Tree>();

	@SuppressWarnings("unchecked")
	public static <T> Kind<T> kind() {
		return (Kind<T>) kind;
	}

	public static class Kind<T> implements TreeBase.Kind {
		public Tree instantiate(SLocation location) {
			return new LiteralExpr<T>(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	}

	public static LiteralExpr<Void> nullLiteral() {
		return of(Void.class, null);
	}

	public static LiteralExpr<Boolean> of(boolean value) {
		return of(Boolean.class, value);
	}

	public static LiteralExpr<Integer> of(int value) {
		return of(Integer.class, value);
	}

	public static LiteralExpr<Long> of(long value) {
		return of(Long.class, value);
	}

	public static LiteralExpr<Float> of(float value) {
		return of(Float.class, value);
	}

	public static LiteralExpr<Double> of(double value) {
		return of(Double.class, value);
	}

	public static LiteralExpr<Character> of(char value) {
		return of(Character.class, value);
	}

	public static LiteralExpr<String> of(String value) {
		return of(String.class, value);
	}

	public static <T> LiteralExpr<T> of(Class<T> literalClass, T literalValue) {
		return new LiteralExpr<T>(literalClass, Literals.from(literalClass, literalValue));
	}

	protected LiteralExpr(SLocation<SNodeState> location) {
		super(location);
	}

	public LiteralExpr(Class<T> literalClass, String literalString) {
		super(new SLocation<SNodeState>(new STree<SNodeState>(kind, new SNodeState(treesOf(), dataOf(literalClass, literalString)))));
	}

	@SuppressWarnings("unchecked")
	public T value() {
		final Class<T> literalClass = location.data(CLASS);
		final String literalString = location.data(STRING);
		return Literals.valueFor(literalClass, literalString);
	}

	@SuppressWarnings("unchecked")
	public LiteralExpr<T> withValue(T value) {
		final Class<T> literalClass = location.data(CLASS);
		return location.withData(STRING, Literals.from(literalClass, value));
	}

	private static final int CLASS = 0;
	private static final int STRING = 1;

	public final static LexicalShape shape = token(new LSToken.Provider() {
		public LToken tokenFor(STree tree) {
			final Class<?> literalClass = (Class<?>) tree.state.data(CLASS);
			final String literalString = (String) tree.state.data(STRING);
			return new LToken(0, literalString); // TODO Fix
		}
	});
}
