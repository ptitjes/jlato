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

import java.util.Collections;

/**
 * @author Didier Villevalois
 */
public class SNodeEitherState implements STreeState<SNodeEitherState> {

	public final STree<?> element;
	public final EitherSide side;

	public SNodeEitherState(STree<?> element, EitherSide side) {
		this.element = element;
		this.side = side;
	}

	public static STraversal<SNodeEitherState> elementTraversal() {
		return new ElementTraversal();
	}

	public static STraversal<SNodeEitherState> leftTraversal() {
		return new ElementTraversal(EitherSide.Left);
	}

	public static STraversal<SNodeEitherState> rightTraversal() {
		return new ElementTraversal(EitherSide.Right);
	}

	@Override
	public Iterable<SProperty<SNodeEitherState>> allProperties() {
		return Collections.emptyList();
	}

	@Override
	public STraversal<SNodeEitherState> firstChild() {
		return elementTraversal();
	}

	@Override
	public STraversal<SNodeEitherState> lastChild() {
		return elementTraversal();
	}

	public STree<?> element() {
		return element;
	}

	public SNodeEitherState withSide(EitherSide side) {
		return new SNodeEitherState(element, side);
	}

	public SNodeEitherState withElement(STree<?> element) {
		return new SNodeEitherState(element, side);
	}

	public SNodeEitherState withLeft(STree<?> element) {
		return new SNodeEitherState(element, EitherSide.Left);
	}

	public SNodeEitherState withRight(STree<?> element) {
		return new SNodeEitherState(element, EitherSide.Right);
	}

	public void validate(STree tree) {
		if (element != null) element.validate();
	}

	public static class ElementTraversal extends STraversal<SNodeEitherState> {

		private final EitherSide side;

		public ElementTraversal() {
			this(null);
		}

		public ElementTraversal(EitherSide side) {
			this.side = side;
		}

		@Override
		public STree<?> traverse(SNodeEitherState state) {
			return side == null || side == state.side ? state.element : null;
		}

		@Override
		public SNodeEitherState rebuildParentState(SNodeEitherState state, STree<?> child) {
			return state.withElement(child).withSide(side != null ? side : state.side);
		}

		@Override
		public STraversal<SNodeEitherState> leftSibling(SNodeEitherState state) {
			return null;
		}

		@Override
		public STraversal<SNodeEitherState> rightSibling(SNodeEitherState state) {
			return null;
		}
	}

	public enum EitherSide {
		Left, Right
	}
}
