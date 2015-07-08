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

import org.jlato.internal.bu.STree;
import org.jlato.printer.Printer;
import org.jlato.printer.Spacing;

/**
 * @author Didier Villevalois
 */
public final class LSComposite extends LexicalShape {

	private final LexicalShape[] shapes;

	public LSComposite(LexicalShape... shapes) {
		this.shapes = shapes;
	}

	@Override
	public boolean isDefined(STree tree) {
		return true;
	}

	@Override
	public boolean isWhitespaceOnly(STree tree) {
		for (LexicalShape shape : shapes) {
			if (!shape.isWhitespaceOnly(tree)) return false;
		}
		return true;
	}

	public void render(STree tree, Printer printer) {
		final RunRenderer renderer = new RunRenderer(tree.run);
		for (LexicalShape shape : shapes) {
			renderer.renderNext(shape, tree, printer);
		}
	}

	private Spacing maxSpacing(Spacing spacing, SpacingConstraint constraint, Printer printer) {
		final Spacing otherSpacing = constraint.resolve(printer);
		return spacing != null && otherSpacing != null ? spacing.max(otherSpacing) : otherSpacing;
	}

	@Override
	public SpacingConstraint spacingBefore(STree tree) {
		return shapes[0].spacingBefore(tree);
	}

	@Override
	public SpacingConstraint spacingAfter(STree tree) {
		return shapes[shapes.length - 1].spacingAfter(tree);
	}
}
