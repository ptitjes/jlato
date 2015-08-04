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

import org.jlato.internal.td.TDContext;
import org.jlato.internal.td.TDLocation;
import org.jlato.tree.Tree;

/**
 * @author Didier Villevalois
 */
public class STree<S extends STreeState> {

	public final S state;
	public final WDressing dressing;

	public STree(S state) {
		this(state, null);
	}

	public STree(S state, WDressing dressing) {
		this.state = state;
		this.dressing = dressing;
	}

	public int width() {
		return 0;
	}

	public STree<S> withState(S state) {
		return new STree<S>(state, dressing);
	}

	public STree<S> withDressing(WDressing dressing) {
		return new STree<S>(state, dressing);
	}

	public STree<?> traverse(STraversal traversal) {
		return traversal.traverse(state);
	}

	@SuppressWarnings("unchecked")
	public STree<S> traverseReplace(STraversal traversal, STree<?> child) {
		final S newState = (S) traversal.rebuildParentState(state, child);
		return withState(newState);
	}

	public Tree asTree() {
		return location().facade;
	}

	public TDLocation<S> location() {
		return new TDLocation<S>(this);
	}

	public TDLocation<S> locationIn(TDContext<?> context) {
		return new TDLocation<S>(context, this);
	}

	public void validate() {
		state.validate(this);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		STree<?> sTree = (STree<?>) o;

		return state.equals(sTree.state);
	}

	@Override
	public int hashCode() {
		return state.hashCode();
	}
}
