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

import org.jlato.internal.td.SLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;

import java.util.Collections;

/**
 * @author Didier Villevalois
 */
public abstract class SNodeState<S extends SNodeState<S>> implements STreeState {

	public SNodeState() {
	}

	public abstract Kind kind();

	@Override
	public Iterable<SProperty> allProperties() {
		return Collections.emptyList();
	}

	public void validate(STree<?> tree) {
//		for (STree<?> child : children) {
//			if (child == null) // TODO Add better error message
//				throw new IllegalStateException();
//			child.validate();
//		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public final Tree instantiate(SLocation<?> location) {
		return doInstantiate((SLocation<S>) location);
	}

	protected abstract Tree doInstantiate(SLocation<S> location);
}
