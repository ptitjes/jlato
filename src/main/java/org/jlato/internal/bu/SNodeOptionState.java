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

/**
 * @author Didier Villevalois
 */
public class SNodeOptionState extends STreeState<SNodeOptionState> {

	public final STree<?> element;

	public SNodeOptionState(STree<?> element) {
		this(element, ArrayList.empty());
	}

	public SNodeOptionState(STree<?> element, ArrayList<Object> data) {
		super(data);
		this.element = element;
	}

	public static STraversal<SNodeOptionState> elementTraversal() {
		return new ElementTraversal();
	}

	@Override
	public STraversal<SNodeOptionState> firstChild() {
		return elementTraversal();
	}

	@Override
	public STraversal<SNodeOptionState> lastChild() {
		return elementTraversal();
	}

	public STree<?> element() {
		return element;
	}

	public SNodeOptionState withElement(STree<?> element) {
		return new SNodeOptionState(element, data);
	}

	public SNodeOptionState withData(int index, Object value) {
		return new SNodeOptionState(element, data.set(index, value));
	}

	public void validate(STree tree) {
		super.validate(tree);

		if (element != null) element.state.validate(element);
	}


	public static class ElementTraversal extends STraversal<SNodeOptionState> {

		public ElementTraversal() {
		}

		@Override
		public STree<?> traverse(SNodeOptionState state) {
			return state.element;
		}

		@Override
		public SNodeOptionState rebuildParentState(SNodeOptionState state, STree<?> child) {
			return state.withElement(child);
		}

		@Override
		public STraversal<SNodeOptionState> leftSibling(SNodeOptionState state) {
			return null;
		}

		@Override
		public STraversal<SNodeOptionState> rightSibling(SNodeOptionState state) {
			return null;
		}
	}
}
