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

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.STree;
import org.jlato.printer.Printer;

/**
 * @author Didier Villevalois
 */
public final class LSChild extends LexicalShape {

	private final ChildSelector selector;
	private final ShapeProvider shapeProvider;

	public LSChild(ChildSelector selector, ShapeProvider shapeProvider) {
		this.selector = selector;
		this.shapeProvider = shapeProvider;
	}

	public void render(STree tree, Printer printer) {
		final SNode node = (SNode) tree;
		final STree child = selector.select(node);
		if (child == null) return;

		final LexicalShape shape = shapeProvider.shapeFor(child);
		if (shape != null) shape.render(child, printer);
	}

	public interface ChildSelector {
		STree select(SNode node);
	}
}
