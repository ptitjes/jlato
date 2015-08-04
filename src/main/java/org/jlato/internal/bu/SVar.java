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
import org.jlato.internal.td.TDLocation;
import org.jlato.tree.*;

import java.util.Collections;

/**
 * @author Didier Villevalois
 */
public class SVar implements STree {

	public final String name;

	public SVar(String name) {
		this.name = name;
	}

	@Override
	public Tree instantiate(TDLocation<?> location) {
		throw new UnsupportedOperationException();
	}

	@Override
	public LexicalShape shape() {
		return null;
	}

	@Override
	public Iterable<SProperty> allProperties() {
		return Collections.emptyList();
	}

	@Override
	public STraversal firstChild() {
		return null;
	}

	@Override
	public STraversal lastChild() {
		return null;
	}

	@Override
	public void validate(BUTree<?> tree) {
	}
}