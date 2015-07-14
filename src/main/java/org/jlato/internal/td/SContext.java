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

package org.jlato.internal.td;

import org.jlato.internal.bu.*;

/**
 * @author Didier Villevalois
 */
public abstract class SContext {

	public abstract STree peruse(STree parent);

	public abstract STree rebuildParent(STree parent, STree child);

	public abstract SContext leftSibling(STree parent);

	public abstract SContext rightSibling(STree parent);

	public static class NodeChild extends SContext {

		private final int index;

		public NodeChild(int index) {
			this.index = index;
		}

		@Override
		public STree peruse(STree parent) {
			final SNodeState state = (SNodeState) parent.state;
			return state.child(index);
		}

		public STree rebuildParent(STree parent, STree child) {
			final SNodeState state = (SNodeState) parent.state;
			return parent.withState(state.withChild(index, child));
		}

		@Override
		public SContext leftSibling(STree parent) {
			final SNodeState state = (SNodeState) parent.state;
			int previousIndex = index - 1;
			while (previousIndex >= 0) {
				if (state.child(previousIndex) != null) return new NodeChild(previousIndex);
				previousIndex--;
			}
			return null;
		}

		@Override
		public SContext rightSibling(STree parent) {
			final SNodeState state = (SNodeState) parent.state;
			int nextIndex = index + 1;
			while (nextIndex < state.children.size()) {
				if (state.child(nextIndex) != null) return new NodeChild(nextIndex);
				nextIndex++;
			}
			return null;
		}
	}

	public static class NodeListChild extends SContext {

		private final int index;

		public NodeListChild(int index) {
			this.index = index;
		}

		@Override
		public STree peruse(STree parent) {
			final SNodeListState state = (SNodeListState) parent.state;
			return state.child(index);
		}

		@Override
		public STree rebuildParent(STree parent, STree child) {
			final SNodeListState state = (SNodeListState) parent.state;
			return parent.withState(state.withChild(index, child));
		}

		@Override
		public SContext leftSibling(STree parent) {
			final SNodeListState state = (SNodeListState) parent.state;
			int previousIndex = index - 1;
			while (previousIndex >= 0) {
				if (state.child(previousIndex) != null) return new NodeListChild(previousIndex);
				previousIndex--;
			}
			return null;
		}

		@Override
		public SContext rightSibling(STree parent) {
			final SNodeListState state = (SNodeListState) parent.state;
			int nextIndex = index + 1;
			while (nextIndex < state.children.size()) {
				if (state.child(nextIndex) != null) return new NodeListChild(nextIndex);
				nextIndex++;
			}
			return null;
		}
	}

	public static class NodeOptionElement extends SContext {

		public NodeOptionElement() {
		}

		@Override
		public STree peruse(STree parent) {
			final SNodeOptionState state = (SNodeOptionState) parent.state;
			return state.element;
		}

		@Override
		public STree rebuildParent(STree parent, STree child) {
			final SNodeOptionState state = (SNodeOptionState) parent.state;
			return parent.withState(state.withElement(child));
		}

		@Override
		public SContext leftSibling(STree parent) {
			return null;
		}

		@Override
		public SContext rightSibling(STree parent) {
			return null;
		}
	}

	public static class TreeSetTree extends SContext {

		private final String path;

		public TreeSetTree(String path) {
			this.path = path;
		}

		@Override
		public STree peruse(STree parent) {
			final STreeSetState state = (STreeSetState) parent.state;
			return state.tree(path);
		}

		@Override
		public STree rebuildParent(STree parent, STree child) {
			final STreeSetState state = (STreeSetState) parent.state;
			return parent.withState(state.withTree(path, child));
		}

		@Override
		public SContext leftSibling(STree parent) {
			return null;
		}

		@Override
		public SContext rightSibling(STree parent) {
			return null;
		}
	}
}
