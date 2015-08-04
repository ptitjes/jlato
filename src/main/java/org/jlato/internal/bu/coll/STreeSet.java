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

import com.github.andrewoma.dexx.collection.TreeMap;
import org.jlato.internal.bu.*;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.tree.*;

import java.util.Collections;
import java.util.Comparator;

/**
 * @author Didier Villevalois
 */
public class STreeSet implements STree {

	public final String rootPath;
	public final TreeMap<String, BUTree<?>> trees;

	public STreeSet(String rootPath) {
		this(rootPath, new TreeMap<String, BUTree<?>>(STRING_COMPARATOR, null));
	}

	public STreeSet(String rootPath, TreeMap<String, BUTree<?>> trees) {
		this.rootPath = rootPath;
		this.trees = trees;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Tree instantiate(TDLocation<?> location) {
		return new TreeSet<Tree>((TDLocation<STreeSet>) location);
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

	public BUTree tree(String path) {
		return trees.get(path);
	}

	public STreeSet withTree(String path, BUTree<?> value) {
		return new STreeSet(rootPath, trees.put(path, value));
	}

	public STree withTrees(TreeMap<String, BUTree<?>> trees) {
		return new STreeSet(rootPath, trees);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		STreeSet that = (STreeSet) o;

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
	public void validate(BUTree<?> tree) {
		// TODO
	}

	private static final Comparator<String> STRING_COMPARATOR = new Comparator<String>() {
		@Override
		public int compare(String s1, String s2) {
			return s1.compareTo(s2);
		}
	};

	public static class TreeTraversal extends STypeSafeTraversal<STreeSet, STree, Tree> {

		private final String path;

		public TreeTraversal(String path) {
			this.path = path;
		}

		@Override
		public BUTree<?> doTraverse(STreeSet state) {
			return state.tree(path);
		}

		@Override
		public STreeSet doRebuildParentState(STreeSet state, BUTree<STree> child) {
			return state.withTree(path, child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			// TODO
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			// TODO
			return null;
		}
	}
}
