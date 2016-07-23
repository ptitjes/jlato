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

package org.jlato.rewrite;

import org.jlato.internal.patterns.DecoratedPattern;
import org.jlato.tree.Tree;
import org.jlato.util.Function1;

/**
 * @author Didier Villevalois
 */
public abstract class Pattern<T> implements TypeSafeMatcher<T>, TypeSafeBuilder<T> {

	public final Substitution match(Object object) {
		return match(object, Substitution.empty());
	}

	public final T build() {
		return build(Substitution.empty());
	}

	public Pattern<T> or(final Pattern<T> other) {
		return new DecoratedPattern<T>(this) {
			@Override
			public Substitution match(Object object, Substitution substitution) {
				Substitution match = super.match(object, substitution);
				return match != null ? match : other.match(object, substitution);
			}
		};
	}

	public Pattern<T> suchThat(final Function1<? super T, Boolean> predicate) {
		return new DecoratedPattern<T>(this) {
			@Override
			@SuppressWarnings("unchecked")
			public Substitution match(Object object, Substitution substitution) {
				Substitution match = super.match(object, substitution);
				return match != null && predicate.apply((T) object) ? match : null;
			}
		};
	}

	public <U> Pattern<T> suchThat(final String var, final Function1<U, Boolean> predicate) {
		return new DecoratedPattern<T>(this) {
			@Override
			@SuppressWarnings("unchecked")
			public Substitution match(Object object, Substitution substitution) {
				Substitution match = super.match(object, substitution);
				return match != null && predicate.apply(match.<U>get(var)) ? match : null;
			}
		};
	}
}
