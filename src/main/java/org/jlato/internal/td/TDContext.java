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

package org.jlato.internal.td;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;

/**
 * @author Didier Villevalois
 */
public final class TDContext<P extends STree> {

	public final TDLocation<P> parent;
	public final STraversal traversal;

	public TDContext(TDLocation<P> parent, STraversal traversal) {
		this.parent = parent;
		this.traversal = traversal;
	}

	public TDContext<P> rebuilt(BUTree<?> child) {
		return new TDContext<P>(rebuiltParent(child), traversal);
	}

	private TDLocation<P> rebuiltParent(BUTree<?> child) {
		return parent.withTree(parent.tree.traverseReplace(traversal, child));
	}

	public BUTree<?> peruse() {
		return traversal.traverse(parent.tree.state);
	}

	public TDLocation<?> newLocation() {
		BUTree<?> tree = peruse();
		return tree == null ? null : tree.locationIn(this);
	}

	public TDContext<P> leftSibling() {
		STraversal leftSibling = traversal.leftSibling(parent.tree.state);
		return leftSibling == null ? null : new TDContext<P>(parent, leftSibling);
	}

	public TDContext<P> rightSibling() {
		STraversal rightSibling = traversal.rightSibling(parent.tree.state);
		return rightSibling == null ? null : new TDContext<P>(parent, rightSibling);
	}
}
