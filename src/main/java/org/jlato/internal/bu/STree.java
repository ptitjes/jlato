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
public class STree extends LElement {

	public final Tree.Kind kind;
	public final STreeState state;
	public final WRun run;

	public STree(Tree.Kind kind, STreeState state) {
		this(kind, state, null);
	}

	public STree(Tree.Kind kind, STreeState state, WRun run) {
		this.kind = kind;
		this.state = state;
		this.run = run;
	}

	@Override
	public boolean isToken() {
		return false;
	}

	@Override
	public int width() {
		return 0;
	}

	public STree withState(STreeState state) {
		return new STree(kind, state, run);
	}

	public STree withRun(WRun run) {
		return new STree(kind, state, run);
	}

	public Tree asTree() {
		return kind.instantiate(new SLocation(new SContext.Root(), this));
	}
}
