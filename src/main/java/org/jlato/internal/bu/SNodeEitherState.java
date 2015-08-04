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
import org.jlato.internal.td.TDLocation;
import org.jlato.tree.*;

import java.util.Collections;

/**
 * @author Didier Villevalois
 */
public class SNodeEitherState implements STreeState {

	public final STree<?> element;
	public final EitherSide side;

	public SNodeEitherState(STree<?> element, EitherSide side) {
		this.element = element;
		this.side = side;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Tree instantiate(TDLocation<?> location) {
		return new NodeEither<Tree, Tree>((TDLocation<SNodeEitherState>) location);
	}

	@Override
	public LexicalShape shape() {
		throw new UnsupportedOperationException();
	}

	public static ElementTraversal elementTraversal() {
		return new ElementTraversal();
	}

	public static ElementTraversal leftTraversal() {
		return new ElementTraversal(EitherSide.Left);
	}

	public static ElementTraversal rightTraversal() {
		return new ElementTraversal(EitherSide.Right);
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		SNodeEitherState that = (SNodeEitherState) o;

		if (!element.equals(that.element)) return false;
		return side == that.side;

	}

	@Override
	public int hashCode() {
		int result = element.hashCode();
		result = 31 * result + side.hashCode();
		return result;
	}

	@Override
	public void validate(STree<?> tree) {
		if (element != null) element.validate();
	}

	public static class ElementTraversal extends STypeSafeTraversal<SNodeEitherState, STreeState, Tree> {

		private final EitherSide side;

		public ElementTraversal() {
			this(null);
		}

		public ElementTraversal(EitherSide side) {
			this.side = side;
		}

		@Override
		public STree<?> doTraverse(SNodeEitherState state) {
			return side == null || side == state.side ? state.element : null;
		}

		@Override
		public SNodeEitherState doRebuildParentState(SNodeEitherState state, STree<STreeState> child) {
			return state.withElement(child).withSide(side != null ? side : state.side);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	}

	public enum EitherSide {
		Left, Right
	}
}
