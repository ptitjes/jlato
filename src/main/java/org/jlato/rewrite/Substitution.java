/*
 * Copyright (C) 2015-2016 Didier Villevalois.
 *
 * This file is part of JLaTo.
 *
 * JLaTo is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * JLaTo is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JLaTo.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.jlato.rewrite;

import com.github.andrewoma.dexx.collection.HashMap;

/**
 * @author Didier Villevalois
 */
public final class Substitution {

	private final HashMap<String, Object> bindings;

	public static Substitution empty() {
		return new Substitution();
	}

	private Substitution() {
		this(HashMap.<String, Object>empty());
	}

	private Substitution(HashMap<String, Object> bindings) {
		this.bindings = bindings;
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String name) {
		return (T) bindings.get(name);
	}

	public <T> Substitution bind(String name, T value) {
		return new Substitution(bindings.put(name, value));
	}

	public boolean binds(String name) {
		return bindings.containsKey(name);
	}
}
