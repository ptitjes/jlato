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
import com.github.andrewoma.dexx.collection.Vector;

/**
 * @author Didier Villevalois
 */
public class SNodeListState extends STreeState<SNodeListState> {

	public final Vector<STree<?>> children;

	public SNodeListState(Vector<STree<? extends STreeState<?>>> children) {
		this(children, ArrayList.empty());
	}

	public SNodeListState(Vector<STree<? extends STreeState<?>>> children, ArrayList<Object> data) {
		super(data);
		this.children = children;
	}

	public static STraversal<SNodeListState> elementTraversal(int index) {
		return new ElementTraversal(index);
	}

	@Override
	public STraversal<SNodeListState> firstChild() {
		if (children.isEmpty()) return null;
		return elementTraversal(-1).rightSibling(this);
	}

	@Override
	public STraversal<SNodeListState> lastChild() {
		if (children.isEmpty()) return null;
		return elementTraversal(children.size()).leftSibling(this);
	}

	public STree<?> child(int index) {
		return children.get(index);
	}

	public SNodeListState withChild(int index, STree<?> value) {
		return new SNodeListState(children.set(index, value), data);
	}

	public SNodeListState withChildren(Vector<STree<?>> children) {
		return new SNodeListState(children, data);
	}

	public SNodeListState withData(int index, Object value) {
		return new SNodeListState(children, data.set(index, value));
	}

	public void validate(STree tree) {
		super.validate(tree);

		for (STree child : children) {
			if (child == null) // TODO Add better error message
				throw new IllegalStateException();
			child.state.validate(child);
		}
	}

	public static class ElementTraversal extends STraversal<SNodeListState> {

		private final int index;

		public ElementTraversal(int index) {
			this.index = index;
		}

		@Override
		public STree<?> traverse(SNodeListState state) {
			return state.child(index);
		}

		@Override
		public SNodeListState rebuildParentState(SNodeListState state, STree<?> child) {
			return state.withChild(index, child);
		}

		@Override
		public STraversal<SNodeListState> leftSibling(SNodeListState state) {
			int previousIndex = index - 1;
			while (previousIndex >= 0) {
				if (state.child(previousIndex) != null) return new ElementTraversal(previousIndex);
				previousIndex--;
			}
			return null;
		}

		@Override
		public STraversal<SNodeListState> rightSibling(SNodeListState state) {
			int nextIndex = index + 1;
			while (nextIndex < state.children.size()) {
				if (state.child(nextIndex) != null) return new ElementTraversal(nextIndex);
				nextIndex++;
			}
			return null;
		}
	}
}
