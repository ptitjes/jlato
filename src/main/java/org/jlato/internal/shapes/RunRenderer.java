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
import org.jlato.printer.Spacing;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Didier Villevalois
 */
public class RunRenderer {

	private final Iterator<LRun> subRunIterator;
	private final Iterator<IndexedList<LToken>> whitespaceIterator;

	private SpacingConstraint constraint;
	private Spacing spacing = null;
	private boolean firstNonWhitespaceShape = true;

	public RunRenderer(LRun run) {
		subRunIterator = run == null ? null : run.subRuns.iterator();
		whitespaceIterator = run == null ? null : run.whitespaces.iterator();
	}

	public void renderNext(LexicalShape shape, STree tree, Printer printer) {
		if (shape.isWhitespaceOnly(tree)) {
			constraint = shape.spacingBefore(tree);
			spacing = constraint == null ? spacing : maxSpacing(spacing, constraint, printer);

			shape.render(tree, null, printer);

			constraint = shape.spacingAfter(tree);
			spacing = constraint == null ? spacing : maxSpacing(spacing, constraint, printer);
		} else {
			if (firstNonWhitespaceShape) firstNonWhitespaceShape = false;
			else {
				constraint = shape.spacingBefore(tree);
				spacing = constraint == null ? spacing : maxSpacing(spacing, constraint, printer);

				final IndexedList<LToken> tokens = safeNext(whitespaceIterator);
				SpacingConstraint.render(
						spacing == null ? Spacing.noSpace : spacing,
						tokens, printer);
			}

			shape.render(tree, safeNext(subRunIterator), printer);

			constraint = shape.spacingAfter(tree);
			spacing = constraint == null ? null : constraint.resolve(printer);
		}
	}

	private <E> E safeNext(Iterator<E> iterator) {
		return iterator == null ? null : iterator.next();
	}

	private Spacing maxSpacing(Spacing spacing, SpacingConstraint constraint, Printer printer) {
		final Spacing otherSpacing = constraint.resolve(printer);
		return spacing != null && otherSpacing != null ? spacing.max(otherSpacing) : otherSpacing;
	}
}
