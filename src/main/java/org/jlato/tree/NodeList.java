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

package org.jlato.tree;

import org.jlato.util.Function1;
import org.jlato.util.Function2;
import org.jlato.util.Mutation;

import java.util.Iterator;

/**
 * @author Didier Villevalois
 */
public interface NodeList<T extends Tree> extends Tree, Iterable<T> {

	boolean isEmpty();

	int size();

	boolean contains(T element);

	int indexOf(T element);

	T get(final int index);

	T first();

	T last();

	NodeList<T> set(int index, T element);

	NodeList<T> prepend(T element);

	NodeList<T> prependAll(NodeList<? extends T> elements);

	NodeList<T> append(T element);

	NodeList<T> appendAll(NodeList<? extends T> elements);

	NodeList<T> insert(int index, T element);

	NodeList<T> insertAll(int index, NodeList<? extends T> elements);

	NodeList<T> insert(T before, T element);

	NodeList<T> insertAll(T before, NodeList<? extends T> elements);

	NodeList<T> delete(int index);

	NodeList<T> delete(T element);

	NodeList<T> rewriteAll(Mutation<T> mutator);

	<U extends Tree> NodeList<U> map(Function1<T, U> f);

	<U extends Tree> U foldLeft(U z, Function2<U, T, U> f);

	<U extends Tree> U foldRight(U z, Function2<T, U, U> f);

	/**
	 * Tests whether a predicate holds for some of the elements of this node list.
	 *
	 * @param p the predicate used to test elements.
	 * @return <code>true</code> if the given predicate p holds for some of the elements of this sequence,
	 * <code>false</code> otherwise.
	 */
	boolean exists(Function1<T, Boolean> p);

	/**
	 * Selects all elements of this sequence which satisfy a predicate.
	 *
	 * @param p the predicate used to test elements.
	 * @return a new sequence consisting of all elements of this sequence that satisfy the given predicate p.
	 * The order of the elements is preserved.
	 */
	NodeList<T> filter(Function1<T, Boolean> p);

	Iterator<T> iterator();

	String mkString(String start, String sep, String end);
}
