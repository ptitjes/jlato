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

import java.util.Collections;

/**
 * @author Didier Villevalois
 */
public abstract class STreeState<S extends STreeState<S>> {

	public abstract Object data(int index);

	public abstract S withData(int index, Object value);

	public Iterable<SProperty<S>> allProperties() {
		return Collections.emptyList();
	}

	public abstract STraversal<S> firstChild();

	public abstract STraversal<S> lastChild();

	public void validate(STree tree) {
	}
}
