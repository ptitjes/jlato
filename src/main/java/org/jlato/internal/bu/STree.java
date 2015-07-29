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

import org.jlato.internal.td.SContext;
import org.jlato.internal.td.SLocation;
import org.jlato.tree.Tree;

/**
 * @author Didier Villevalois
 */
public class STree<S extends STreeState> {

	public final S state;
	public final WTokenRun leading;
	public final WRunRun run;
	public final WTokenRun trailing;

	public STree(S state) {
		this(state, null, null, null);
	}

	public STree(S state, WTokenRun leading, WRunRun run, WTokenRun trailing) {
		this.state = state;
		this.leading = leading;
		this.run = run;
		this.trailing = trailing;
	}

	public int width() {
		return 0;
	}

	public STree<S> withState(S state) {
		return new STree<S>(state, leading, run, trailing);
	}

	public STree<S> withRun(WRunRun run) {
		return new STree<S>(state, leading, run, trailing);
	}

	public STree<S> withLeading(WTokenRun leading) {
		return new STree<S>(state, leading, run, trailing);
	}

	public STree<S> withTrailing(WTokenRun trailing) {
		return new STree<S>(state, leading, run, trailing);
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

	public SLocation<S> location() {
		return new SLocation<S>(this);
	}

	public SLocation<S> locationIn(SContext<?> context) {
		return new SLocation<S>(context, this);
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
