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

import com.github.andrewoma.dexx.collection.Pair;
import com.github.andrewoma.dexx.collection.TreeMap;
import org.jlato.internal.bu.*;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.coll.TDNodeMap;
import org.jlato.tree.*;

import java.util.Collections;
import java.util.Comparator;

/**
 * @author Didier Villevalois
 */
public class SNodeMap implements STree {

	public final TreeMap<String, BUTree<?>> trees;

	public SNodeMap() {
		this(new TreeMap<String, BUTree<?>>(STRING_COMPARATOR, null));
	}

	public SNodeMap(TreeMap<String, BUTree<?>> trees) {
		this.trees = trees;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Tree instantiate(TDLocation<?> location) {
		return new TDNodeMap<Tree>((TDLocation<SNodeMap>) location);
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
		Pair<String, BUTree<?>> firstPair = trees.first();
		return firstPair == null ? null : new TreeTraversal(firstPair.component1());
	}

	@Override
	public STraversal lastChild() {
		Pair<String, BUTree<?>> lastPair = trees.last();
		return lastPair == null ? null : new TreeTraversal(lastPair.component1());
	}

	public BUTree tree(String path) {
		return trees.get(path);
	}

	public SNodeMap withTree(String path, BUTree<?> value) {
		return withTrees(trees.put(path, value));
	}

	public SNodeMap withTrees(TreeMap<String, BUTree<?>> trees) {
		return new SNodeMap(trees);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		SNodeMap that = (SNodeMap) o;
		return trees.equals(that.trees);
	}

	@Override
	public int hashCode() {
		int result = trees.hashCode();
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

	public static class TreeTraversal extends STypeSafeTraversal<SNodeMap, STree, Tree> {

		private final String path;

		public TreeTraversal(String path) {
			this.path = path;
		}

		@Override
		public BUTree<?> doTraverse(SNodeMap state) {
			return state.tree(path);
		}

		@Override
		public SNodeMap doRebuildParentState(SNodeMap state, BUTree<STree> child) {
			return state.withTree(path, child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			SNodeMap treeSetState = (SNodeMap) state;
			Pair<String, BUTree<?>> previousPair = treeSetState.trees.to(this.path, false).last();
			return previousPair == null ? null : new TreeTraversal(previousPair.component1());
		}

		@Override
		public STraversal rightSibling(STree state) {
			SNodeMap treeSetState = (SNodeMap) state;
			Pair<String, BUTree<?>> nextPair = treeSetState.trees.from(this.path, false).first();
			return nextPair == null ? null : new TreeTraversal(nextPair.component1());
		}
	}
}
