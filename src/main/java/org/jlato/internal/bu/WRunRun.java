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

	public WRunRun insert(int index, int count) {
		if (index < 1 || index >= elements.size())
			throw new IllegalArgumentException();
		if (count < 1)
			throw new IllegalArgumentException();

		IndexedList<WRun> left = elements.take(index);
		IndexedList<WRun> right = elements.drop(index);

		IndexedList<WRun> newElements = left;

		for (int i = 0; i < count; i++) {
			// Append a whitespaces run
			newElements = newElements.append(NULL);
		}

		for (WRun element : right) {
			newElements = newElements.append(element);
		}
		return new WRunRun(newElements);
	}

	public WRunRun delete(int index, int count) {
		if (index < 1 || index >= elements.size())
			throw new IllegalArgumentException();
		if (count < 1)
			throw new IllegalArgumentException();

		IndexedList<WRun> left = elements.take(index);
		IndexedList<WRun> right = elements.drop(index + count);

		IndexedList<WRun> newElements = left;

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
