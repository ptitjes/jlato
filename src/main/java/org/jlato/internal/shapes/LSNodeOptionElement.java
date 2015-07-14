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

package org.jlato.internal.shapes;

import org.jlato.internal.bu.SNodeOptionState;
import org.jlato.internal.bu.STree;

/**
 * @author Didier Villevalois
 */
public final class LSNodeOptionElement extends LSTraversal {

	public LSNodeOptionElement(LexicalShape shape) {
		super(shape);
	}

	protected STree traverse(STree tree) {
		final SNodeOptionState state = (SNodeOptionState) tree.state;
		return state.element;
	}
}
