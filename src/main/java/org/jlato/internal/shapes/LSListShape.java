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

import com.github.andrewoma.dexx.collection.IndexedList;
import com.github.andrewoma.dexx.collection.Vector;
import org.jlato.internal.bu.*;
import org.jlato.printer.Printer;

import java.util.Iterator;

/**
 * @author Didier Villevalois
 */
public final class LSListShape extends LexicalShape {

	private final LexicalShape shape, before, separator, after;

	public LSListShape(LexicalShape shape, LexicalShape before, LexicalShape separator, LexicalShape after) {
		this.shape = shape;
		this.before = before;
		this.separator = separator;
		this.after = after;
	}

	@Override
	public boolean isDefined(STree tree) {
		final SNodeList nodeList = (SNodeList) tree;
		final Vector<STree> children = nodeList.state().children;
		return !children.isEmpty();
	}

	@Override
	public boolean isWhitespaceOnly() {
		return false;
	}

	@Override
	public LRun enRun(STree tree, Iterator<IndexedList<LToken>> tokenIterator) {
		return null;
	}

	public void render(STree tree, LRun run, Printer printer) {
		final SNodeList nodeList = (SNodeList) tree;
		final Vector<STree> children = nodeList.state().children;
		if (children.isEmpty()) return;

		final RunRenderer renderer = new RunRenderer(run);

		boolean firstElement = true;
		for (STree child : children) {
			if (firstElement) {
				if (before != null) {
					renderer.renderNext(before, tree, printer);
				}
				firstElement = false;
			} else if (separator != null) {
				renderer.renderNext(separator, tree, printer);
			}

			renderer.renderNext(shape, child, printer);
		}

		if (!firstElement) {
			if (after != null) {
				renderer.renderNext(after, tree, printer);
			}
		}
	}

	@Override
	public SpacingConstraint spacingBefore(STree tree) {
		final SNodeList nodeList = (SNodeList) tree;
		final Vector<STree> children = nodeList.state().children;
		if (children.isEmpty()) return null;

		if (before != null) return before.spacingBefore(tree);
		final STree child = children.first();
		return shape.spacingBefore(child);
	}

	@Override
	public SpacingConstraint spacingAfter(STree tree) {
		final SNodeList nodeList = (SNodeList) tree;
		final Vector<STree> children = nodeList.state().children;
		if (children.isEmpty()) return null;

		if (after != null) return after.spacingAfter(tree);
		final STree child = children.last();
		return shape.spacingAfter(child);
	}
}
