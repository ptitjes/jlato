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

import com.github.andrewoma.dexx.collection.ArrayList;
import com.github.andrewoma.dexx.collection.Builder;
import com.github.andrewoma.dexx.collection.HashSet;
import com.github.andrewoma.dexx.collection.Set;
import org.jlato.tree.Predicate;
import org.jlato.tree.Tree;

/**
 * @author Didier Villevalois
 */
public abstract class Pattern<T> {

	public Set<String> variables() {
		HashSet<String> variables = HashSet.empty();
		variables = collectVariables(variables);
		return variables;
	}

	protected abstract HashSet<String> collectVariables(HashSet<String> variables);

	public Substitution match(Object object) {
		return match(object, Substitution.empty());
	}

	protected abstract Substitution match(Object object, Substitution substitution);

	public abstract T build(Substitution substitution);

	protected static ArrayList<Pattern<?>> termsOf(Pattern<?>... terms) {
		final Builder<Pattern<?>, ArrayList<Pattern<?>>> builder = ArrayList.<Pattern<?>>factory().newBuilder();
		for (Pattern<?> attribute : terms) {
			builder.add(attribute);
		}
		return builder.build();
	}

	public Pattern<T> or(final Pattern<T> other) {
		return new DecoratedPattern<T>(this) {
			@Override
			public Substitution match(Object object) {
				Substitution match = super.match(object);
				return match != null ? match : other.match(object);
			}
		};
	}

	public Pattern<T> suchThat(final Predicate<T> predicate) {
		return new DecoratedPattern<T>(this) {
			@Override
			@SuppressWarnings("unchecked")
			public Substitution match(Object object) {
				Substitution match = super.match(object);
				return match != null && predicate.test((T) object) ? match : null;
			}
		};
	}

	public <U> Pattern<T> suchThat(final String var, final Predicate<U> predicate) {
		return new DecoratedPattern<T>(this) {
			@Override
			@SuppressWarnings("unchecked")
			public Substitution match(Object object) {
				Substitution match = super.match(object);
				return match != null && predicate.test((U) match.get(var)) ? match : null;
			}
		};
	}

	public Rewriter rewriteTo(final Pattern<T> rewrote) {
		return new Rewriter() {
			@Override
			@SuppressWarnings("unchecked")
			public <T extends Tree> T rewrite(T t) {
				Substitution match = Pattern.this.match(t);
				return match == null ? t : (T) rewrote.build(match);
			}
		};
	}
}
