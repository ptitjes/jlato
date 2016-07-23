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

import com.github.andrewoma.dexx.collection.Vector;
import org.jlato.internal.td.TDContext;
import org.jlato.internal.td.TDLocation;
import org.jlato.tree.*;

/**
 * @author Didier Villevalois
 */
public class BUTree<S extends STree> {

	public final S state;
	public final WDressing dressing;
	private final boolean hasProblems;
	private final Vector<BUProblem> problems;

	public BUTree(S state) {
		this(state, null, false, Vector.<BUProblem>empty());
	}

	protected BUTree(S state, WDressing dressing, boolean hasProblems, Vector<BUProblem> problems) {
		this.state = state;
		this.dressing = dressing;
		this.hasProblems = hasProblems;
		this.problems = problems;
	}

	protected BUTree<S> copy(S state, WDressing dressing, boolean hasProblems, Vector<BUProblem> problems) {
		return new BUTree<S>(state, dressing, hasProblems, problems);
	}

	public int width() {
		return 0;
	}

	public BUTree<S> withState(S state) {
		return copy(state, dressing, hasProblems, problems);
	}

	public BUTree<S> withDressing(WDressing dressing) {
		return copy(state, dressing, hasProblems, problems);
	}

	public boolean hasProblems() {
		return hasProblems;
	}

	public Vector<BUProblem> problems() {
		return problems;
	}

	public BUTree<S> setProblems() {
		return copy(state, dressing, true, problems);
	}

	public BUTree<S> withProblem(BUProblem problem) {
		return problem == null ? this : copy(state, dressing, true, problems.append(problem));
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
