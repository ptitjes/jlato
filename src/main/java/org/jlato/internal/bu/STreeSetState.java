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

import com.github.andrewoma.dexx.collection.TreeMap;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.TreeSet;

import java.util.Collections;
import java.util.Comparator;

/**
 * @author Didier Villevalois
 */
public class STreeSetState implements STreeState {

	public final String rootPath;
	public final TreeMap<String, STree<?>> trees;

	public STreeSetState(String rootPath) {
		this(rootPath, new TreeMap<String, STree<?>>(STRING_COMPARATOR, null));
	}

	public STreeSetState(String rootPath, TreeMap<String, STree<?>> trees) {
		this.rootPath = rootPath;
		this.trees = trees;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Tree instantiate(SLocation<?> location) {
		return new TreeSet<Tree>((SLocation<STreeSetState>) location);
	}

	@Override
	public LexicalShape shape() {
		throw new UnsupportedOperationException();
	}

	public static TreeTraversal treeTraversal(String path) {
		return new TreeTraversal(path);
	}

	@Override
	public Iterable<SProperty> allProperties() {
		return Collections.emptyList();
	}

	@Override
	public STraversal firstChild() {
		// TODO
		return null;
	}

	@Override
	public STraversal lastChild() {
		// TODO
		return null;
	}

	public STree tree(String path) {
		return trees.get(path);
	}

	public STreeSetState withTree(String path, STree<?> value) {
		return new STreeSetState(rootPath, trees.put(path, value));
	}

	public STreeState withTrees(TreeMap<String, STree<?>> trees) {
		return new STreeSetState(rootPath, trees);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		STreeSetState that = (STreeSetState) o;

		if (!rootPath.equals(that.rootPath)) return false;
		return trees.equals(that.trees);

	}

	@Override
	public int hashCode() {
		int result = rootPath.hashCode();
		result = 31 * result + trees.hashCode();
		return result;
	}

	@Override
	public void validate(STree<?> tree) {
		// TODO
	}

	private static final Comparator<String> STRING_COMPARATOR = new Comparator<String>() {
		@Override
		public int compare(String s1, String s2) {
			return s1.compareTo(s2);
		}
	};

	public static class TreeTraversal extends STypeSafeTraversal<STreeSetState, STreeState, Tree> {

		private final String path;

		public TreeTraversal(String path) {
			this.path = path;
		}

		@Override
		protected STree<?> doTraverse(STreeSetState state) {
			return state.tree(path);
		}

		@Override
		protected STreeSetState doRebuildParentState(STreeSetState state, STree<STreeState> child) {
			return state.withTree(path, child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			// TODO
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			// TODO
			return null;
		}
	}
}
