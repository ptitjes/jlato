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

import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;

/**
 * @author Didier Villevalois
 */
public final class SContext<P extends STreeState> {

	public final SLocation<P> parent;
	public final STraversal traversal;

	public SContext(SLocation<P> parent, STraversal traversal) {
		this.parent = parent;
		this.traversal = traversal;
	}

	public SContext<P> rebuilt(STree<?> child) {
		return new SContext<P>(rebuiltParent(child), traversal);
	}

	private SLocation<P> rebuiltParent(STree<?> child) {
		return parent.withTree(parent.tree.traverseReplace(traversal, child));
	}

	public STree<?> peruse() {
		return traversal.traverse(parent.tree.state);
	}

	public SLocation<?> newLocation() {
		STree<?> tree = peruse();
		return tree == null ? null : tree.locationIn(this);
	}

	public SContext<P> leftSibling() {
		STraversal leftSibling = traversal.leftSibling(parent.tree.state);
		return leftSibling == null ? null : new SContext<P>(parent, leftSibling);
	}

	public SContext<P> rightSibling() {
		STraversal rightSibling = traversal.rightSibling(parent.tree.state);
		return rightSibling == null ? null : new SContext<P>(parent, rightSibling);
	}
}
