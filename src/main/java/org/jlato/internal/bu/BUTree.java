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
import org.jlato.tree.*;

/**
 * @author Didier Villevalois
 */
public class BUTree<S extends STree> {

	public final S state;
	public final WDressing dressing;

	public BUTree(S state) {
		this(state, null);
	}

	public BUTree(S state, WDressing dressing) {
		this.state = state;
		this.dressing = dressing;
	}

	public int width() {
		return 0;
	}

	public BUTree<S> withState(S state) {
		return new BUTree<S>(state, dressing);
	}

	public BUTree<S> withDressing(WDressing dressing) {
		return new BUTree<S>(state, dressing);
	}

	public BUTree<?> traverse(STraversal traversal) {
		return traversal.traverse(state);
	}

	@SuppressWarnings("unchecked")
	public BUTree<S> traverseReplace(STraversal traversal, BUTree<?> child) {
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

		BUTree<?> buTree = (BUTree<?>) o;

		return state.equals(buTree.state);
	}

	@Override
	public int hashCode() {
		return state.hashCode();
	}
}
