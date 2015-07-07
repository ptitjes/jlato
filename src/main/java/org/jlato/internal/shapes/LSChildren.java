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
import org.jlato.internal.bu.SNodeList;
import org.jlato.internal.bu.STree;
import org.jlato.printer.Printer;

/**
 * @author Didier Villevalois
 */
public final class LSChildren extends LexicalShape {

	private final ChildrenSelector selector;
	private final ShapeProvider shapeProvider;
	private final LexicalShape before, separator, after;

	public LSChildren(ChildrenSelector selector, ShapeProvider shapeProvider,
	                  LexicalShape before, LexicalShape separator, LexicalShape after) {
		this.selector = selector;
		this.shapeProvider = shapeProvider;
		this.before = before;
		this.separator = separator;
		this.after = after;
	}

	public void render(STree tree, Printer printer) {
		final SNode node = (SNode) tree;
		final SNodeList nodeList = selector.select(node);

		if (nodeList == null || nodeList.run.treeCount() == 0) return;

		boolean firstElement = true;
		for (STree child : nodeList.run) {
			if (!selector.filter(child)) continue;

			if (firstElement) {
				if (before != null) {
					before.render(tree, printer);
				}
				firstElement = false;
			} else if (separator != null) {
				separator.render(child, printer);
			}

			final LexicalShape shape = shapeProvider.shapeFor(child);
			if (shape == null) throw new IllegalArgumentException();

			shape.render(child, printer);
		}

		if (!firstElement) {
			if (after != null) {
				after.render(tree, printer);
			}
		}
	}

	public interface ChildrenSelector {

		SNodeList select(SNode node);

		boolean filter(STree tree);
	}
}
