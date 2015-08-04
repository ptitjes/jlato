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

import org.jlato.tree.*;

/**
 * @author Didier Villevalois
 */
public abstract class STypeSafeTraversal<P extends STree, S extends STree, T extends Tree> extends STraversal {

	@Override
	@SuppressWarnings("unchecked")
	public final BUTree<?> traverse(STree state) {
		return doTraverse((P) state);
	}

	public abstract BUTree<?> doTraverse(P state);

	@Override
	@SuppressWarnings("unchecked")
	public final STree rebuildParentState(STree state, BUTree<?> child) {
		return doRebuildParentState((P) state, (BUTree<S>) child);
	}

	public abstract P doRebuildParentState(P state, BUTree<S> child);
}
