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

/**
 * @author Didier Villevalois
 */
public class WRun extends WElement {

	public final IndexedList<WElement> elements;

	public WRun(IndexedList<WElement> elements) {
		this.elements = elements;
	}

	public WRun append(WElement element) {
		return new WRun(elements.append(element));
	}

	public WRun insertBefore(int index, int count) {
		if (index < 1 || index > (elements.size() + 1) / 2)
			throw new IllegalArgumentException();
		if (count < 1)
			throw new IllegalArgumentException();

		if (index == 0) {
			IndexedList<WElement> newElements = elements;
			for (int i = 0; i < count; i++) {
				// Prepend a sub-shape run and a whitespaces run
				newElements = newElements.prepend(null).prepend(null);
			}
			return new WRun(newElements);
		} else if (index == (elements.size() + 1) / 2) {
			IndexedList<WElement> newElements = elements;
			for (int i = 0; i < count; i++) {
				// Append a whitespaces run and a sub-shape run
				newElements = newElements.append(null).append(null);
			}
			return new WRun(newElements);
		} else {
			int cutIndex = index * 2 - 1;

			WRun whitespace = (WRun) elements.get(cutIndex);
			IndexedList<WElement> left = elements.take(cutIndex);
			IndexedList<WElement> right = elements.drop(cutIndex);

			// Split the whitespace run
			// TODO Implement splitting the whitespaces with user involvement ?
			WRun leftSplit = null;
			WRun rightSplit = whitespace;

			// Append the left split whitespaces run and a sub-shape run
			IndexedList<WElement> newElements = left.append(leftSplit).append(null);

			for (int i = 0; i < count - 1; i++) {
				// Append a whitespaces run and a sub-shape run
				newElements = newElements.append(null).append(null);
			}
			// Append the right split whitespaces run
			newElements = newElements.append(rightSplit);

			for (WElement element : right) {
				newElements = newElements.append(element);
			}
			return new WRun(newElements);
		}
	}

	public interface WRunSplitter {

	}
}
