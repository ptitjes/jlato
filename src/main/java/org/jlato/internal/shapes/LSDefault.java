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
import org.jlato.internal.bu.LRun;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.STree;
import org.jlato.printer.Printer;

import java.util.Iterator;

/**
 * @author Didier Villevalois
 */
public final class LSDefault extends LexicalShape {

	public LSDefault() {
	}

	private LexicalShape shapeFor(STree tree) {
		return tree.kind.shape();
	}

	@Override
	public boolean isDefined(STree tree) {
		final LexicalShape shape = shapeFor(tree);
		return shape != null && shape.isDefined(tree);
	}

	@Override
	public boolean isWhitespaceOnly() {
		return false;
	}

	@Override
	public LRun enRun(STree tree, Iterator<IndexedList<LToken>> tokenIterator) {
		throw new UnsupportedOperationException();
	}

	public void render(STree tree, LRun run, Printer printer) {
		final LexicalShape shape = shapeFor(tree);
		if (shape != null) shape.render(tree, run, printer);
	}

	@Override
	public SpacingConstraint spacingBefore(STree tree) {
		final LexicalShape shape = shapeFor(tree);
		return shape == null ? null : shape.spacingBefore(tree);
	}

	@Override
	public SpacingConstraint spacingAfter(STree tree) {
		final LexicalShape shape = shapeFor(tree);
		return shape == null ? null : shape.spacingAfter(tree);
	}
}
