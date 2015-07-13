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

/**
 * @author Didier Villevalois
 */
public class WRunRun extends WRun {

	public final IndexedList<WRun> elements;

	public WRunRun(IndexedList<WRun> elements) {
		this.elements = elements;
	}

	public WRunRun insertBefore(int index, int count) {
		if (index < 1 || index > (elements.size() + 1) / 2)
			throw new IllegalArgumentException();
		if (count < 1)
			throw new IllegalArgumentException();

		if (index == 0) {
			IndexedList<WRun> newElements = elements;
			for (int i = 0; i < count; i++) {
				// Prepend a sub-shape run and a whitespaces run
				newElements = newElements.prepend(null).prepend(null);
			}
			return new WRunRun(newElements);
		} else if (index == (elements.size() + 1) / 2) {
			IndexedList<WRun> newElements = elements;
			for (int i = 0; i < count; i++) {
				// Append a whitespaces run and a sub-shape run
				newElements = newElements.append(null).append(null);
			}
			return new WRunRun(newElements);
		} else {
			int cutIndex = index * 2 - 1;

			WRun whitespace = elements.get(cutIndex);
			IndexedList<WRun> left = elements.take(cutIndex);
			IndexedList<WRun> right = elements.drop(cutIndex);

			// Split the whitespace run
			// TODO Implement splitting the whitespaces with user involvement ?
			WRun leftSplit = null;
			WRun rightSplit = whitespace;

			// Append the left split whitespaces run and a sub-shape run
			IndexedList<WRun> newElements = left.append(leftSplit).append(null);

			for (int i = 0; i < count - 1; i++) {
				// Append a whitespaces run and a sub-shape run
				newElements = newElements.append(null).append(null);
			}
			// Append the right split whitespaces run
			newElements = newElements.append(rightSplit);

			for (WRun element : right) {
				newElements = newElements.append(element);
			}
			return new WRunRun(newElements);
		}
	}
}
