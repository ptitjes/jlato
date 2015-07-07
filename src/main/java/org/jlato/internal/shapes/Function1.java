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

package org.jlato.internal.shapes;

import org.jlato.tree.expr.ObjectCreationExpr;

/**
 * @author Didier Villevalois
 */
public interface Function1<A, R> {
	R apply(A arg);

	class Std {

		public static <A, R> Function1<A, R> constant(final R constant) {
			return new Function1<A, R>() {
				public R apply(A arg) {
					return constant;
				}
			};
		}

		@SuppressWarnings("unchecked")
		public static <A> Function1<A, Boolean> alwaysTrue() {
			return (Function1<A, Boolean>) ALWAYS_TRUE;
		}

		@SuppressWarnings("unchecked")
		public static <A> Function1<A, Boolean> alwaysFalse() {
			return (Function1<A, Boolean>) ALWAYS_FALSE;
		}

		private static final Function1<Object, Boolean> ALWAYS_TRUE = constant(true);
		private static final Function1<Object, Boolean> ALWAYS_FALSE = constant(false);
	}
}
