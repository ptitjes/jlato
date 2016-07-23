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

package org.jlato.internal.bu.coll;

import org.jlato.internal.bu.*;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.coll.TDNodeEither;
import org.jlato.tree.*;

import java.util.Collections;

/**
 * @author Didier Villevalois
 */
public class SNodeEither implements STree {

	public final BUTree<?> element;
	public final EitherSide side;

	public SNodeEither(BUTree<?> element, EitherSide side) {
		this.element = element;
		this.side = side;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Tree instantiate(TDLocation<?> location) {
		return new TDNodeEither<Tree, Tree>((TDLocation<SNodeEither>) location);
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

	public SNodeEither withSide(EitherSide side) {
		return new SNodeEither(element, side);
	}

	public SNodeEither withElement(BUTree<?> element) {
		return new SNodeEither(element, side);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		SNodeEither that = (SNodeEither) o;

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
	public void validate(BUTree<?> tree) {
		if (element != null) element.validate();
	}

	public static class ElementTraversal extends STypeSafeTraversal<SNodeEither, STree, Tree> {

		private final EitherSide side;

		public ElementTraversal() {
			this(null);
		}

		public ElementTraversal(EitherSide side) {
			this.side = side;
		}

		@Override
		public BUTree<?> doTraverse(SNodeEither state) {
			return side == null || side == state.side ? state.element : null;
		}

		@Override
		public SNodeEither doRebuildParentState(SNodeEither state, BUTree<STree> child) {
			return state.withElement(child).withSide(side != null ? side : state.side);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	}

	public enum EitherSide {
		Left, Right
	}
}
