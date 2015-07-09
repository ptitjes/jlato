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
import com.github.andrewoma.dexx.collection.IndexedList;

import java.util.Iterator;

/**
 * @author Didier Villevalois
 */
public class LRun implements Iterable<IndexedList<LToken>> {

	public final ArrayList<LRun> subRuns;
	public final IndexedList<IndexedList<LToken>> whitespaces;

	public LRun(ArrayList<LRun> subRuns, IndexedList<IndexedList<LToken>> whitespaces) {
		this.subRuns = subRuns;
		this.whitespaces = whitespaces;
	}

	public Iterator<IndexedList<LToken>> iterator() {
		return whitespaces.iterator();
	}
}
