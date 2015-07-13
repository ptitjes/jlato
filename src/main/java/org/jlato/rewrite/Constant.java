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

import com.github.andrewoma.dexx.collection.HashSet;

/**
 * @author Didier Villevalois
 */
class Constant<T> extends Pattern<T> {

	public static <T> Constant<T> of(T value) {
		return new Constant<T>(value);
	}

	public final T value;

	private Constant(T value) {
		this.value = value;
	}

	@Override
	protected HashSet<Variable<?>> collectVariables(HashSet<Variable<?>> variables) {
		return variables;
	}

	@Override
	protected Substitution match(Object object, Substitution substitution) {
		return (value == null && object == null) || (value != null && value.equals(object)) ? substitution : null;
	}

	@Override
	public T build(Substitution substitution) {
		return value;
	}
}