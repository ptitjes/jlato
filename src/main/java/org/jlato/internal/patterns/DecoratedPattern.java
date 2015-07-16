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

package org.jlato.internal.patterns;

import org.jlato.rewrite.Pattern;
import org.jlato.rewrite.Substitution;

/**
 * @author Didier Villevalois
 */
public class DecoratedPattern<T> extends Pattern<T> {

	private final Pattern<T> pattern;

	public DecoratedPattern(Pattern<T> pattern) {
		this.pattern = pattern;
	}

	@Override
	public Substitution match(Object object, Substitution substitution) {
		return pattern.match(object, substitution);
	}

	@Override
	public T build(Substitution substitution) {
		return pattern.build(substitution);
	}
}
