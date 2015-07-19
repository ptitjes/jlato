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
public abstract class STypeSafeTraversal<P extends STreeState, S extends STreeState, T extends Tree> extends STraversal {

	@Override
	@SuppressWarnings("unchecked")
	public final STree<?> traverse(STreeState state) {
		return doTraverse((P) state);
	}

	protected abstract STree<?> doTraverse(P state);

	@Override
	@SuppressWarnings("unchecked")
	public final STreeState rebuildParentState(STreeState state, STree<?> child) {
		return doRebuildParentState((P) state, (STree<S>) child);
	}

	protected abstract P doRebuildParentState(P state, STree<S> child);
}
