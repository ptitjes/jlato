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

package org.jlato.internal.bu;

import com.github.andrewoma.dexx.collection.Vector;

/**
 * @author Didier Villevalois
 */
public class BUTreeVar<S extends STree> extends BUTree<S> {

	public static <S extends STree> BUTreeVar<S> var(String name) {
		return new BUTreeVar<S>(null, null, false, null, name);
	}

	public static <S extends STree> BUTreeVar<S> var(String name, BUTree<S> base) {
		return new BUTreeVar<S>(base.state, base.dressing, base.hasProblems(), base.problems(), name);
	}

	public final String name;

	protected BUTreeVar(S state, WDressing dressing, boolean hasProblems, Vector<BUProblem> problems, String name) {
		super(state, dressing, hasProblems, problems);
		this.name = name;
	}

	protected BUTree<S> copy(S state, WDressing dressing, boolean hasProblems, Vector<BUProblem> problems) {
		return new BUTreeVar<S>(state, dressing, hasProblems, problems, name);
	}
}
