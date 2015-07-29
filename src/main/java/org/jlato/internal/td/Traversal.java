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
import org.jlato.rewrite.Substitution;
import org.jlato.rewrite.TypeSafeMatcher;
import org.jlato.tree.Tree;

/**
 * @author Didier Villevalois
 */
public abstract class Traversal<T extends Tree> {

	public static <R extends Tree, T extends Tree> R leftForAll(R from, TypeSafeMatcher<T> matcher, MatchVisitor<T> visitor) {
		return new LeftRightDepthFirst<T>().traverse(from, matcher, visitor);
	}

	public static <R extends Tree, T extends Tree> R rightForAll(R from, TypeSafeMatcher<T> matcher, MatchVisitor<T> visitor) {
		return new RightLeftDepthFirst<T>().traverse(from, matcher, visitor);
	}

	@SuppressWarnings("unchecked")
	public <R extends Tree> R traverse(R tree, TypeSafeMatcher<T> matcher, MatchVisitor<T> visitor) {
		SLocation location = TreeBase.locationOf(tree);
		return (R) doTraverse(location, matcher, visitor).facade;
	}

	// TODO Trampoline recursion

	protected abstract SLocation doTraverse(SLocation location, TypeSafeMatcher<T> matcher, MatchVisitor<T> visitor);

	public static abstract class DepthFirst<T extends Tree> extends Traversal<T> {

		@Override
		protected SLocation doTraverse(SLocation location, TypeSafeMatcher<T> matcher, MatchVisitor<T> visitor) {
			// Visit this location
			SLocation afterVisit = doVisit(location, matcher, visitor);

			// Visit children
			SLocation afterVisitChildren = doTraverseChildren(afterVisit, matcher, visitor);

			return afterVisitChildren;
		}

		private SLocation doVisit(SLocation location, TypeSafeMatcher<T> matcher, MatchVisitor<T> visitor) {
			Tree facade = location.facade;
			Substitution match = matcher.match(facade);
			if (match != null) {
				SLocation rewrote = TreeBase.locationOf(visitor.visit((T) facade, match));
				return location.withTree(rewrote.tree);
			} else {
				return location;
			}
		}

		private SLocation doTraverseChildren(SLocation location, TypeSafeMatcher<T> matcher, MatchVisitor<T> visitor) {
			SLocation child = child(location);
			if (child == null) return location;

			SLocation nextChild = child;
			while (nextChild != null) {
				child = doTraverse(nextChild, matcher, visitor);
				nextChild = sibling(child);
			}

			return child.parent();
		}

		protected abstract SLocation child(SLocation location);

		protected abstract SLocation sibling(SLocation location);
	}

	public static class LeftRightDepthFirst<T extends Tree> extends DepthFirst<T> {

		protected SLocation child(SLocation location) {
			return location.firstChild();
		}

		protected SLocation sibling(SLocation location) {
			return location.rightSibling();
		}
	}

	public static class RightLeftDepthFirst<T extends Tree> extends DepthFirst<T> {

		protected SLocation child(SLocation location) {
			return location.lastChild();
		}

		protected SLocation sibling(SLocation location) {
			return location.leftSibling();
		}
	}
}
