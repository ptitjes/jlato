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

/**
 * @author Didier Villevalois
 */
public class RunRenderer {

	private final Iterator<IndexedList<LToken>> spacesIterator;

	private SpacingConstraint constraint;
	private Spacing spacing = null;
	private boolean firstNonWhitespaceShape = true;

	public RunRenderer(LRun run) {
		spacesIterator = run == null ? null : run.iterator();
	}

	public void renderNext(LexicalShape shape, STree tree, Printer printer) {
		if (shape.isWhitespaceOnly(tree)) {
			constraint = shape.spacingBefore(tree);
			spacing = constraint == null ? spacing : maxSpacing(spacing, constraint, printer);

			shape.render(tree, printer);

			constraint = shape.spacingAfter(tree);
			spacing = constraint == null ? spacing : maxSpacing(spacing, constraint, printer);
		} else {
			if (firstNonWhitespaceShape) firstNonWhitespaceShape = false;
			else {
				constraint = shape.spacingBefore(tree);
				spacing = constraint == null ? spacing : maxSpacing(spacing, constraint, printer);

				if (!shape.isWhitespaceOnly(tree) && spacing != null) {
					SpacingConstraint.render(spacing, spacesIterator == null ? null : spacesIterator.next(), printer);
				}
			}

			shape.render(tree, printer);

			constraint = shape.spacingAfter(tree);
			spacing = constraint == null ? null : constraint.resolve(printer);
		}
	}

	private Spacing maxSpacing(Spacing spacing, SpacingConstraint constraint, Printer printer) {
		final Spacing otherSpacing = constraint.resolve(printer);
		return spacing != null && otherSpacing != null ? spacing.max(otherSpacing) : otherSpacing;
	}
}
