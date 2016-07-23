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

import org.jlato.rewrite.MatchVisitor;
import org.jlato.rewrite.Matcher;
import org.jlato.rewrite.Substitution;
import org.jlato.tree.Tree;

/**
 * @author Didier Villevalois
 */
public abstract class Traversal<T extends Tree> {

	public static <R extends Tree, T extends Tree> R leftForAll(R from, Matcher<? extends T> matcher, MatchVisitor<T> visitor) {
		return new LeftRightDepthFirst<T>().traverse(from, matcher, visitor);
	}

	public static <R extends Tree, T extends Tree> R rightForAll(R from, Matcher<? extends T> matcher, MatchVisitor<T> visitor) {
		return new RightLeftDepthFirst<T>().traverse(from, matcher, visitor);
	}

	@SuppressWarnings("unchecked")
	public <R extends Tree> R traverse(R tree, Matcher<? extends T> matcher, MatchVisitor<T> visitor) {
		TDLocation location = TDTree.locationOf(tree);
		return (R) doTraverse(location, matcher, visitor).facade;
	}

	// TODO Trampoline recursion

	protected abstract TDLocation doTraverse(TDLocation location, Matcher<? extends T> matcher, MatchVisitor<T> visitor);

	public static abstract class DepthFirst<T extends Tree> extends Traversal<T> {

		@Override
		protected TDLocation doTraverse(TDLocation location, Matcher<? extends T> matcher, MatchVisitor<T> visitor) {
			// Visit this location
			TDLocation afterVisit = doVisit(location, matcher, visitor);

			// Visit children
			TDLocation afterVisitChildren = doTraverseChildren(afterVisit, matcher, visitor);

			return afterVisitChildren;
		}

		private TDLocation doVisit(TDLocation location, Matcher<? extends T> matcher, MatchVisitor<T> visitor) {
			Tree facade = location.facade;
			Substitution match = matcher.match(facade, Substitution.empty());
			if (match != null) {
				TDLocation rewrote = TDTree.locationOf(visitor.visit((T) facade, match));
				return location.withTree(rewrote.tree);
			} else {
				return location;
			}
		}

		private TDLocation doTraverseChildren(TDLocation location, Matcher<? extends T> matcher, MatchVisitor<T> visitor) {
			TDLocation child = child(location);
			if (child == null) return location;

			TDLocation nextChild = child;
			while (nextChild != null) {
				child = doTraverse(nextChild, matcher, visitor);
				nextChild = sibling(child);
			}

			return child.parent();
		}

		protected abstract TDLocation child(TDLocation location);

		protected abstract TDLocation sibling(TDLocation location);
	}

	public static class LeftRightDepthFirst<T extends Tree> extends DepthFirst<T> {

		protected TDLocation child(TDLocation location) {
			return location.firstChild();
		}

		protected TDLocation sibling(TDLocation location) {
			return location.rightSibling();
		}
	}

	public static class RightLeftDepthFirst<T extends Tree> extends DepthFirst<T> {

		protected TDLocation child(TDLocation location) {
			return location.lastChild();
		}

		protected TDLocation sibling(TDLocation location) {
			return location.leftSibling();
		}
	}
}
