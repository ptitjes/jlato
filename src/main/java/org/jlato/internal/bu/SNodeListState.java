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

import com.github.andrewoma.dexx.collection.Vector;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.tree.*;

import java.util.Collections;

/**
 * @author Didier Villevalois
 */
public class SNodeListState implements STreeState {

	public final Vector<STree<?>> children;

	public SNodeListState() {
		this(Vector.<STree<? extends STreeState>>empty());
	}

	public SNodeListState(STree<?> element) {
		this(Vector.<STree<? extends STreeState>>empty().append(element));
	}

	public SNodeListState(Vector<STree<? extends STreeState>> children) {
		this.children = children;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Tree instantiate(TDLocation<?> location) {
		return new NodeList<Tree>((TDLocation<SNodeListState>) location);
	}

	@Override
	public void validate(STree<?> tree) {
		for (STree child : children) {
			if (child == null) // TODO Add better error message
				throw new IllegalStateException();
			child.validate();
		}
	}

	@Override
	public LexicalShape shape() {
		throw new UnsupportedOperationException();
	}

	public static ElementTraversal elementTraversal(int index) {
		return new ElementTraversal(index);
	}

	public static ElementTraversal firstTraversal() {
		return new ElementTraversal(0);
	}

	public static ElementTraversal lastTraversal() {
		return new ElementTraversal(-1);
	}

	@Override
	public Iterable<SProperty> allProperties() {
		return Collections.emptyList();
	}

	@Override
	public STraversal firstChild() {
		if (children.isEmpty()) return null;
		return elementTraversal(0);
	}

	@Override
	public STraversal lastChild() {
		if (children.isEmpty()) return null;
		return elementTraversal(children.size() - 1);
	}

	public STree<?> child(int index) {
		return children.get(index);
	}

	public SNodeListState withChild(int index, STree<?> value) {
		return new SNodeListState(children.set(index, value));
	}

	public SNodeListState withChildren(Vector<STree<?>> children) {
		return new SNodeListState(children);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		SNodeListState that = (SNodeListState) o;

		return children.equals(that.children);

	}

	@Override
	public int hashCode() {
		return children.hashCode();
	}

	public static class ElementTraversal extends STypeSafeTraversal<SNodeListState, STreeState, Tree> {

		private final int index;

		public ElementTraversal(int index) {
			this.index = index;
		}

		@Override
		public STree<?> doTraverse(SNodeListState state) {
			return state.child(index(state));
		}

		private int index(SNodeListState state) {
			return index >= 0 ? index : state.children.size() + index;
		}

		@Override
		public SNodeListState doRebuildParentState(SNodeListState state, STree<STreeState> child) {
			return state.withChild(index(state), child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			final SNodeListState nodeListState = (SNodeListState) state;
			int previousIndex = index(nodeListState) - 1;
			if (previousIndex >= 0) return new ElementTraversal(previousIndex);
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			final SNodeListState nodeListState = (SNodeListState) state;
			int nextIndex = index(nodeListState) + 1;
			if (nextIndex < nodeListState.children.size()) return new ElementTraversal(nextIndex);
			return null;
		}
	}
}
