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

import com.github.andrewoma.dexx.collection.IndexedList;

import static org.jlato.internal.bu.WTokenRun.NULL;

/**
 * @author Didier Villevalois
 */
public class WRunRun extends WRun {

	public final IndexedList<WRun> elements;

	public WRunRun(IndexedList<WRun> elements) {
		this.elements = elements;
	}

	public WRunRun insert(int index, int count, boolean before) {
		if (index < 1 || index >= elements.size() / 2)
			throw new IllegalArgumentException();
		if (count < 1)
			throw new IllegalArgumentException();

		int cutIndex = index * 2 + (before ? 0 : +1);

		IndexedList<WRun> left = elements.take(cutIndex);
		IndexedList<WRun> right = elements.drop(cutIndex);

		IndexedList<WRun> newElements = left;

		if (before) {
			for (int i = 0; i < count; i++) {
				// Append a sub-shape run and a whitespaces run
				newElements = newElements.append(null).append(NULL);
			}
		} else {
			for (int i = 0; i < count; i++) {
				// Append a whitespaces run and a sub-shape run
				newElements = newElements.append(NULL).append(null);
			}
		}

		for (WRun element : right) {
			newElements = newElements.append(element);
		}
		return new WRunRun(newElements);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[");

		boolean first = true;
		for (WRun element : elements) {
			if (!first) builder.append(",");
			else first = false;

			builder.append(element == null ? null : element.toString());
		}

		builder.append("]");
		return builder.toString();
	}
}
