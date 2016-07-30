/*
 * Copyright (C) 2015-2016 Didier Villevalois.
 *
 * This file is part of JLaTo.
 *
 * JLaTo is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * JLaTo is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JLaTo.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.jlato.internal.bu.coll;

import com.github.andrewoma.dexx.collection.Vector;
import org.jlato.internal.bu.*;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.coll.TDNodeList;
import org.jlato.tree.*;

import java.util.Collections;

/**
 * @author Didier Villevalois
 */
public class SNodeList implements STree {

	public final Vector<BUTree<?>> children;

	public SNodeList() {
		this(Vector.<BUTree<? extends STree>>empty());
	}

	public SNodeList(BUTree<?> element) {
		this(Vector.<BUTree<? extends STree>>empty().append(element));
	}

	public SNodeList(Vector<BUTree<? extends STree>> children) {
		this.children = children;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Tree instantiate(TDLocation<?> location) {
		return new TDNodeList<Tree>((TDLocation<SNodeList>) location);
	}

	@Override
	public void validate(BUTree<?> tree) {
		for (BUTree child : children) {
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

	public BUTree<?> child(int index) {
		return children.get(index);
	}

	public SNodeList withChild(int index, BUTree<?> value) {
		return new SNodeList(children.set(index, value));
	}

	public SNodeList withChildren(Vector<BUTree<?>> children) {
		return new SNodeList(children);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		SNodeList that = (SNodeList) o;

		return children.equals(that.children);

	}

	@Override
	public int hashCode() {
		return children.hashCode();
	}

	public static class ElementTraversal extends STypeSafeTraversal<SNodeList, STree, Tree> {

		private final int index;

		public ElementTraversal(int index) {
			this.index = index;
		}

		@Override
		public BUTree<?> doTraverse(SNodeList state) {
			return state.child(index(state));
		}

		private int index(SNodeList state) {
			return index >= 0 ? index : state.children.size() + index;
		}

		@Override
		public SNodeList doRebuildParentState(SNodeList state, BUTree<STree> child) {
			return state.withChild(index(state), child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			final SNodeList nodeListState = (SNodeList) state;
			int previousIndex = index(nodeListState) - 1;
			if (previousIndex >= 0) return new ElementTraversal(previousIndex);
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			final SNodeList nodeListState = (SNodeList) state;
			int nextIndex = index(nodeListState) + 1;
			if (nextIndex < nodeListState.children.size()) return new ElementTraversal(nextIndex);
			return null;
		}
	}
}
