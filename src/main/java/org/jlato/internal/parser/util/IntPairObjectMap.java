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

package org.jlato.internal.parser.util;

import gnu.trove.iterator.TLongObjectIterator;
import gnu.trove.map.hash.TLongObjectHashMap;

/**
 * @author Didier Villevalois
 */
public class IntPairObjectMap<V> extends TLongObjectHashMap<V> {

	public void put(int key1, int key2, V value) {
		long key = (long) key1 << 32 | (long) key2 & 0xffffffffL;
		put(key, value);
	}

	public V get(int key1, int key2) {
		long key = (long) key1 << 32 | (long) key2 & 0xffffffffL;
		return get(key);
	}

	public IntPairObjectIterator<V> iterator() {
		return new IntPairObjectIterator<V>(super.iterator());
	}

	public static class IntPairObjectIterator<V> implements TLongObjectIterator<V> {

		private final TLongObjectIterator<V> iterator;

		public IntPairObjectIterator(TLongObjectIterator<V> iterator) {
			this.iterator = iterator;
		}

		@Override
		public long key() {
			return iterator.key();
		}

		public int getKey1() {
			long key = iterator.key();
			return (int) (key >>> 32);
		}

		public int getKey2() {
			long key = iterator.key();
			return (int) (key & 0xffffffffL);
		}

		@Override
		public V value() {
			return iterator.value();
		}

		@Override
		public V setValue(V v) {
			return iterator.setValue(v);
		}

		@Override
		public void advance() {
			iterator.advance();
		}

		@Override
		public boolean hasNext() {
			return iterator.hasNext();
		}

		@Override
		public void remove() {
			iterator.remove();
		}
	}
}
