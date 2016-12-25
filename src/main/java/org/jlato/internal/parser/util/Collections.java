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

import gnu.trove.map.hash.THashMap;
import gnu.trove.set.hash.THashSet;

import java.util.Map;
import java.util.Set;

/**
 * @author Didier Villevalois
 */
public class Collections {

	public static <V> Set<V> hashSet() {
		return new THashSet<V>();
	}

	public static IntSet intSet() {
		return new IntSet();
	}

	public static <K, V> Map<K, V> hashMap() {
		return new THashMap<K, V>();
	}

	public static <V> IntObjectMap<V> intObjectMap() {
		return new IntObjectMap<V>();
	}

	public static <V> IntPairObjectMap<V> intPairObjectMap() {
		return new IntPairObjectMap<V>();
	}
}
