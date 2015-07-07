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
import com.github.andrewoma.dexx.collection.Builder;
import com.github.andrewoma.dexx.collection.IndexedList;
import com.github.andrewoma.dexx.collection.Vector;

import java.util.Iterator;

/**
 * @author Didier Villevalois
 */
public class LRun implements Iterable<STree> {

	public final IndexedList<LToken> tokensBefore;
	public final IndexedList<TreeAndTokensAfter> treesAndTokensAfter;
	public final int width;

	public LRun(IndexedList<LToken> tokensBefore, IndexedList<TreeAndTokensAfter> treesAndTokensAfter) {
		this.tokensBefore = tokensBefore;
		this.treesAndTokensAfter = treesAndTokensAfter;
		this.width = computeWidth();
	}

	private int computeWidth() {
		int width = 0;
		for (LToken token : tokensBefore) {
			width += token.width();
		}
		for (TreeAndTokensAfter element : treesAndTokensAfter) {
			width += element.tree.width();
			for (LToken token : element.tokensAfter) {
				width += token.width();
			}
		}
		return width;
	}

	public int width() {
		return width;
	}

	public boolean hasOnlyOneToken() {
		return tokensBefore.size() == 1 && treesAndTokensAfter.isEmpty();
	}

	public int treeCount() {
		return treesAndTokensAfter.size();
	}

	public boolean contains(STree tree) {
		for (TreeAndTokensAfter treeAndTokensAfter : treesAndTokensAfter) {
			if (treeAndTokensAfter.tree == tree) return true;
		}
		return false;
	}

	public STree tree(int index) {
		if (index >= treesAndTokensAfter.size()) return null;
		return treesAndTokensAfter.get(index).tree;
	}

	public LElement element(int index) {
		for (LToken token : tokensBefore) {
			if (index == 0) return token;
			index--;
		}
		for (TreeAndTokensAfter element : treesAndTokensAfter) {
			if (index == 0) return element.tree;
			index--;
			for (LToken token : element.tokensAfter) {
				if (index == 0) return token;
				index--;
			}
		}
		return null;
	}

	public LRun set(int index, STree tree) {
		return new LRun(tokensBefore,
				treesAndTokensAfter.set(index, treesAndTokensAfter.get(index).withTree(tree)));
	}

	public LRun append(STree tree) {
		return new LRun(tokensBefore, treesAndTokensAfter.append(new TreeAndTokensAfter(tree)));
	}

	public LRun prepend(STree tree) {
		return new LRun(Vector.<LToken>empty(), treesAndTokensAfter.prepend(new TreeAndTokensAfter(tree, tokensBefore)));
	}

	public Iterator<LElement> elementIterator() {
		return new ElementIterator();
	}

	public Iterator<STree> iterator() {
		return new TreeIterator();
	}

	private class ElementIterator implements Iterator<LElement> {
		private Iterator<TreeAndTokensAfter> trees = treesAndTokensAfter.iterator();
		private STree currentTree = null;
		private Iterator<LToken> currentTokens = tokensBefore.iterator();

		public boolean hasNext() {
			while (!currentTokens.hasNext() && trees.hasNext()) {
				TreeAndTokensAfter treeAndTokensAfter = trees.next();
				currentTree = treeAndTokensAfter.tree;
				currentTokens = treeAndTokensAfter.tokensAfter.iterator();
			}
			return currentTree != null || currentTokens.hasNext();
		}

		public LElement next() {
			if (currentTree == null || !currentTokens.hasNext()) {
				while (!currentTokens.hasNext() && trees.hasNext()) {
					TreeAndTokensAfter treeAndTokensAfter = trees.next();
					currentTree = treeAndTokensAfter.tree;
					currentTokens = treeAndTokensAfter.tokensAfter.iterator();
				}
			}

			if (currentTree == null) {
				STree tree = currentTree;
				currentTree = null;
				return tree;
			} else {
				return currentTokens.next();
			}
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	private class TreeIterator implements Iterator<STree> {
		private Iterator<TreeAndTokensAfter> trees = treesAndTokensAfter.iterator();

		public boolean hasNext() {
			return trees.hasNext();
		}

		public STree next() {
			return trees.next().tree;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	private static class TreeAndTokensAfter {
		public final STree tree;
		public final IndexedList<LToken> tokensAfter;

		public TreeAndTokensAfter(STree tree) {
			this(tree, Vector.<LToken>empty());
		}

		public TreeAndTokensAfter(STree tree, IndexedList<LToken> tokensAfter) {
			this.tree = tree;
			this.tokensAfter = tokensAfter;
		}

		public TreeAndTokensAfter withTree(STree tree) {
			return new TreeAndTokensAfter(tree, tokensAfter);
		}

		public TreeAndTokensAfter withTokensAfter(IndexedList<LToken> tokensAfter) {
			return new TreeAndTokensAfter(tree, tokensAfter);
		}
	}

	public static class RunBuilder {

		public Builder<LToken, ? extends IndexedList<LToken>> tokensBefore;
		public Builder<TreeAndTokensAfter, ? extends IndexedList<TreeAndTokensAfter>> treesAndTokensAfter;
		public Builder<LToken, ? extends IndexedList<LToken>> lastTokens;
		public STree lastTree = null;

		public static RunBuilder withFixedArity() {
			return new RunBuilder(
					ArrayList.<TreeAndTokensAfter>factory().newBuilder()
			);
		}

		public static RunBuilder withVariableArity() {
			return new RunBuilder(
					Vector.<TreeAndTokensAfter>factory().newBuilder()
			);
		}

		public RunBuilder(Builder<TreeAndTokensAfter, ? extends IndexedList<TreeAndTokensAfter>> treesAndTokensAfter) {
			this.treesAndTokensAfter = treesAndTokensAfter;
			this.tokensBefore = lastTokens = Vector.<LToken>factory().newBuilder();
		}

		public void addTree(STree tree) {
			addLastTreeAndTokensAfter();
			lastTree = tree;
			lastTokens = Vector.<LToken>factory().newBuilder();
		}

		private void addLastTreeAndTokensAfter() {
			if (lastTree != null) {
				treesAndTokensAfter.add(new TreeAndTokensAfter(lastTree, lastTokens.build()));
			}
		}

		public void addToken(LToken token) {
			lastTokens.add(token);
		}

		public LRun build() {
			addLastTreeAndTokensAfter();
			return new LRun(tokensBefore.build(), treesAndTokensAfter.build());
		}
	}
}
