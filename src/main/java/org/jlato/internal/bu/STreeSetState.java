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
import com.github.andrewoma.dexx.collection.TreeMap;

import java.util.Comparator;

/**
 * @author Didier Villevalois
 */
public class STreeSetState extends STreeState<STreeSetState> {

	public final TreeMap<String, STree<?>> trees;

	public STreeSetState(ArrayList<Object> data) {
		this(new TreeMap<String, STree<?>>(STRING_COMPARATOR, null), data);
	}

	public STreeSetState(TreeMap<String, STree<?>> trees) {
		this(trees, ArrayList.empty());
	}

	public STreeSetState(TreeMap<String, STree<?>> trees, ArrayList<Object> data) {
		super(data);
		this.trees = trees;
	}

	public static STraversal<STreeSetState> treeTraversal(String path) {
		return new TreeTraversal(path);
	}

	@Override
	public STraversal<STreeSetState> firstChild() {
		// TODO
		return null;
	}

	@Override
	public STraversal<STreeSetState> lastChild() {
		// TODO
		return null;
	}

	public STree tree(String path) {
		return trees.get(path);
	}

	public STreeSetState withTree(String path, STree<?> value) {
		return new STreeSetState(trees.put(path, value), data);
	}

	public STreeSetState withData(int index, Object value) {
		return new STreeSetState(trees, data.set(index, value));
	}

	public STreeState withTrees(TreeMap<String, STree<?>> trees) {
		return new STreeSetState(trees, data);
	}

	private static final Comparator<String> STRING_COMPARATOR = new Comparator<String>() {
		@Override
		public int compare(String s1, String s2) {
			return s1.compareTo(s2);
		}
	};

	public static class TreeTraversal extends STraversal<STreeSetState> {

		private final String path;

		public TreeTraversal(String path) {
			this.path = path;
		}

		@Override
		public STree<?> traverse(STreeSetState state) {
			return state.tree(path);
		}

		@Override
		public STreeSetState rebuildParentState(STreeSetState state, STree<?> child) {
			return state.withTree(path, child);
		}

		@Override
		public STraversal<STreeSetState> leftSibling(STreeSetState state) {
			// TODO
			return null;
		}

		@Override
		public STraversal<STreeSetState> rightSibling(STreeSetState state) {
			// TODO
			return null;
		}
	}
}
