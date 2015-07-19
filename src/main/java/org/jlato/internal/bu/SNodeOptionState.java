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

import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;

import java.util.Collections;

/**
 * @author Didier Villevalois
 */
public class SNodeOptionState implements STreeState {

	public final STree<?> element;

	public SNodeOptionState(STree<?> element) {
		this.element = element;
	}

	@Override
	public Tree instantiate(SLocation<SNodeOptionState> location) {
		return new NodeOption<Tree>(location);
	}

	@Override
	public LexicalShape shape() {
		throw new UnsupportedOperationException();
	}

	public static STraversal elementTraversal() {
		return new ElementTraversal();
	}

	@Override
	public Iterable<SProperty> allProperties() {
		return Collections.emptyList();
	}

	@Override
	public STraversal firstChild() {
		return elementTraversal();
	}

	@Override
	public STraversal lastChild() {
		return elementTraversal();
	}

	public STree<?> element() {
		return element;
	}

	public SNodeOptionState withElement(STree<?> element) {
		return new SNodeOptionState(element);
	}

	public void validate(STree<SNodeOptionState> tree) {
		if (element != null) element.validate();
	}


	public static class ElementTraversal extends STraversal {

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
		public STraversal leftSibling(SNodeOptionState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(SNodeOptionState state) {
			return null;
		}
	}
}
