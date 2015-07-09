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

import org.jlato.tree.Tree;

/**
 * @author Didier Villevalois
 */
public class SNodeList extends STree {

	public SNodeList(Tree.Kind kind, SNodeListState state) {
		this(kind, state, null);
	}

	public SNodeList(Tree.Kind kind, SNodeListState state, LRun run) {
		super(kind, state, run);
	}

	public SNodeListState state() {
		return (SNodeListState) state;
	}

	@Override
	public int width() {
		return 0;/*run.width();*/
	}

	public SNodeList withState(SNodeListState state) {
		return new SNodeList(kind, state, run);
	}

	public SNodeList withRun(LRun run) {
		return new SNodeList(kind, state(), run);
	}
}
