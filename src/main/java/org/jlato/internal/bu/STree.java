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

import org.jlato.tree.Kind;
import org.jlato.internal.td.SContext;
import org.jlato.internal.td.SLocation;
import org.jlato.tree.Tree;

/**
 * @author Didier Villevalois
 */
public class STree<S extends STreeState<S>> {

	public final S state;
	public final WRunRun run;

	public STree(S state) {
		this(state, null);
	}

	public STree(S state, WRunRun run) {
		this.state = state;
		this.run = run;
	}

	public int width() {
		return 0;
	}

	public STree<S> withState(S state) {
		return new STree<S>(state, run);
	}

	public STree<S> withRun(WRunRun run) {
		return new STree<S>(state, run);
	}

	public STree<?> traverse(STraversal<S> traversal) {
		return traversal.traverse(state);
	}

	public STree<S> traverseReplace(STraversal<S> traversal, STree<?> child) {
		final S newState = traversal.rebuildParentState(state, child);
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
}
