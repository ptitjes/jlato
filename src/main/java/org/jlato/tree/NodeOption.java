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

package org.jlato.tree;

import org.jlato.util.Mutation;

import java.util.Iterator;

/**
 * @author Didier Villevalois
 */
public interface NodeOption<T extends Tree> extends Tree, Iterable<T> {

	boolean isDefined();

	boolean isNone();

	boolean isSome();

	boolean contains(T element);

	T get();

	NodeOption<T> set(T element);

	NodeOption<T> set(Mutation<T> mutation);

	NodeOption<T> setNone();

	Iterator<T> iterator();

	String mkString(String start, String sep, String end);
}
