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
public class SNodeState extends STreeState<SNodeState> {

	private final ArrayList<STree<?>> children;

	public SNodeState(ArrayList<STree<? extends STreeState<?>>> children) {
		this(ArrayList.empty(), children);
	}

	public SNodeState(ArrayList<Object> data, ArrayList<STree<? extends STreeState<?>>> children) {
		super(data);
		this.children = children;
	}

	public static STraversal<SNodeState> childTraversal(int index) {
		return new ChildTraversal(index);
	}

	@Override
	public STraversal<SNodeState> firstChild() {
		if (children.isEmpty()) return null;
		return childTraversal(-1).rightSibling(this);
	}

	@Override
	public STraversal<SNodeState> lastChild() {
		if (children.isEmpty()) return null;
		return childTraversal(children.size()).leftSibling(this);
	}

	public STree<?> child(int index) {
		return children.get(index);
	}

	public SNodeState withChild(int index, STree<?> value) {
		return new SNodeState(data, children.set(index, value));
	}

	public SNodeState withData(int index, Object value) {
		return new SNodeState(data.set(index, value), children);
	}

	public void validate(STree tree) {
		super.validate(tree);

		for (STree child : children) {
			if (child == null) // TODO Add better error message
				throw new IllegalStateException();
			child.state.validate(child);
		}
	}

	public Iterable<STree<? extends STreeState<?>>> children() {
		return children;
	}

	public static class ChildTraversal extends STraversal<SNodeState> {

		private final int index;

		public ChildTraversal(int index) {
			this.index = index;
		}

		@Override
		public STree<?> traverse(SNodeState state) {
			return state.child(index);
		}

		@Override
		public SNodeState rebuildParentState(SNodeState state, STree<?> child) {
			return state.withChild(index, child);
		}

		@Override
		public STraversal<SNodeState> leftSibling(SNodeState state) {
			int previousIndex = index - 1;
			while (previousIndex >= 0) {
				if (state.child(previousIndex) != null) return new ChildTraversal(previousIndex);
				previousIndex--;
			}
			return null;
		}

		@Override
		public STraversal<SNodeState> rightSibling(SNodeState state) {
			int nextIndex = index + 1;
			while (nextIndex < state.children.size()) {
				if (state.child(nextIndex) != null) return new ChildTraversal(nextIndex);
				nextIndex++;
			}
			return null;
		}
	}

}
