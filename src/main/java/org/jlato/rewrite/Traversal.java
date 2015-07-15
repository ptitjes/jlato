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

package org.jlato.rewrite;

import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Tree;

/**
 * @author Didier Villevalois
 */
public abstract class Traversal<T extends Tree> {

	public static <R extends Tree, T extends Tree> R forAll(final Pattern<T> p, R from, Visitor<T> visitor) {
		return new DepthFirst<T>() {
			@Override
			protected boolean accept(Tree object) {
				return p.match(object) != null;
			}
		}.traverse(from, visitor);
	}

	@SuppressWarnings("unchecked")
	public <R extends Tree> R traverse(R tree, Visitor<T> visitor) {
		SLocation location = TreeBase.locationOf(tree);
		return (R) doTraverse(location, visitor).facade;
	}

	public interface Visitor<T extends Tree> {
		T visit(T t);
	}

	// TODO Trampoline recursion

	protected abstract boolean accept(Tree object);

	protected abstract SLocation doTraverse(SLocation location, Visitor<T> visitor);

	public static abstract class DepthFirst<T extends Tree> extends Traversal<T> {

		@Override
		protected SLocation doTraverse(SLocation location, Visitor<T> visitor) {
			// Visit this location
			SLocation afterVisit = doVisit(location, visitor);

			// Visit children
			SLocation afterVisitChildren = doTraverseChildren(afterVisit, visitor);

			return afterVisitChildren;
		}

		private SLocation doVisit(SLocation location, Visitor<T> visitor) {
			Tree facade = location.facade;
			if (!accept(facade)) return location;
			SLocation rewrote = TreeBase.locationOf(visitor.visit((T) facade));
			return location.withTree(rewrote.tree);
		}

		private SLocation doTraverseChildren(SLocation location, Visitor<T> visitor) {
			SLocation child = location.firstChild();
			if (child == null) return location;

			SLocation nextChild = child;
			while (nextChild != null) {
				child = doTraverse(nextChild, visitor);
				nextChild = child.rightSibling();
			}

			return child.parent();
		}
	}
}
