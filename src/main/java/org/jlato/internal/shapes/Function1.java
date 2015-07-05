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
