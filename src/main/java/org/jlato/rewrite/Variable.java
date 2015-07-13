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
public final class Variable<T> extends Pattern<T> {

	public final String name;
	public final Class<T> valueClass;

	public Variable(String name, Class<T> valueClass) {
		this.name = name;
		this.valueClass = valueClass;
	}

	@Override
	protected HashSet<Variable<?>> collectVariables(HashSet<Variable<?>> variables) {
		return variables.add(this);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected Substitution match(Object object, Substitution substitution) {
		if (substitution.binds(this)) return substitution.get(this) == object ? substitution : null;
		if (object != null && !valueClass.isInstance(object)) return null;
		return substitution.bind(this, (T) object);
	}

	@Override
	public T build(Substitution substitution) {
		return substitution.get(this);
	}
}
