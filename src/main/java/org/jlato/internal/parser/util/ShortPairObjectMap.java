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

import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.iterator.TLongObjectIterator;
import gnu.trove.map.hash.TIntObjectHashMap;

/**
 * @author Didier Villevalois
 */
public class ShortPairObjectMap<V> extends TIntObjectHashMap<V> {

	public void put(int key1, int key2, V value) {
		int key = keyFor(key1, key2);
		put(key, value);
	}

	public V get(int key1, int key2) {
		int key = keyFor(key1, key2);
		return get(key);
	}

	private int keyFor(int key1, int key2) {
		return (int) (key1 << 16 | key2 & 0xffffL);
	}

	public ShortPairObjectIterator<V> iterator() {
		return new ShortPairObjectIterator<V>(super.iterator());
	}

	public static class ShortPairObjectIterator<V> implements TIntObjectIterator<V> {

		private final TIntObjectIterator<V> iterator;

		public ShortPairObjectIterator(TIntObjectIterator<V> iterator) {
			this.iterator = iterator;
		}

		@Override
		public int key() {
			return iterator.key();
		}

		public short getKey1() {
			long key = iterator.key();
			return (short) (key >>> 16);
		}

		public short getKey2() {
			long key = iterator.key();
			return (short) (key & 0xffffL);
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
