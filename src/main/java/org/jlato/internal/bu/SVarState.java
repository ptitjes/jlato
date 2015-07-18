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

import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.tree.Tree;

import java.util.Collections;

/**
 * @author Didier Villevalois
 */
public class SVarState implements STreeState<SVarState> {

	public final String name;

	public SVarState(String name) {
		this.name = name;
	}

	@Override
	public Tree instantiate(SLocation<SVarState> location) {
		return null;
	}

	@Override
	public LexicalShape shape() {
		return null;
	}

	@Override
	public Iterable<SProperty<SVarState>> allProperties() {
		return Collections.emptyList();
	}

	@Override
	public STraversal<SVarState> firstChild() {
		return null;
	}

	@Override
	public STraversal<SVarState> lastChild() {
		return null;
	}

	@Override
	public void validate(STree<SVarState> tree) {
	}
}
