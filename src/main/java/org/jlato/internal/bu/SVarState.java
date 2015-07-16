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

package org.jlato.internal.bu;

import com.github.andrewoma.dexx.collection.ArrayList;

/**
 * @author Didier Villevalois
 */
public class SVarState extends STreeState<SVarState> {

	public final String name;

	public SVarState(String name) {
		this(ArrayList.empty(), name);
	}

	public SVarState(ArrayList<Object> data, String name) {
		super(data);
		this.name = name;
	}

	public SVarState withData(int index, Object value) {
		return new SVarState(data.set(index, value), name);
	}

	@Override
	public STraversal<SVarState> firstChild() {
		return null;
	}

	@Override
	public STraversal<SVarState> lastChild() {
		return null;
	}
}