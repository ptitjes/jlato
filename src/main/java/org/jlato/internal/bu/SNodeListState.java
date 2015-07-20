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
import org.jlato.internal.td.SLocation;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;

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
	public Tree instantiate(SLocation<?> location) {
		return new NodeList<Tree>((SLocation<SNodeListState>) location);
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

	@Override
	public Iterable<SProperty> allProperties() {
		return Collections.emptyList();
	}

	@Override
	public STraversal firstChild() {
		if (children.isEmpty()) return null;
		return elementTraversal(-1).rightSibling(this);
	}

	@Override
	public STraversal lastChild() {
		if (children.isEmpty()) return null;
		return elementTraversal(children.size()).leftSibling(this);
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
		protected STree<?> doTraverse(SNodeListState state) {
			return state.child(index);
		}

		@Override
		protected SNodeListState doRebuildParentState(SNodeListState state, STree<STreeState> child) {
			return state.withChild(index, child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			int previousIndex = index - 1;
			while (previousIndex >= 0) {
				if (((SNodeListState) state).child(previousIndex) != null)
					return new ElementTraversal(previousIndex);
				previousIndex--;
			}
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			int nextIndex = index + 1;
			while (nextIndex < ((SNodeListState) state).children.size()) {
				if (((SNodeListState) state).child(nextIndex) != null)
					return new ElementTraversal(nextIndex);
				nextIndex++;
			}
			return null;
		}
	}
}
