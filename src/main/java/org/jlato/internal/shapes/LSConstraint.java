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

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.WRunRun;
import org.jlato.printer.Printer;

/**
 * @author Didier Villevalois
 */
public final class LSConstraint extends LSDecorated {

	private final SpacingConstraint spacingBefore;
	private final SpacingConstraint spacingAfter;
	private final IndentationConstraint indentationBefore;
	private final IndentationConstraint indentationAfter;

	public LSConstraint(LexicalShape shape,
	                    SpacingConstraint spacingBefore,
	                    SpacingConstraint spacingAfter,
	                    IndentationConstraint indentationBefore,
	                    IndentationConstraint indentationAfter) {
		super(shape);
		this.spacingBefore = spacingBefore;
		this.spacingAfter = spacingAfter;
		this.indentationBefore = indentationBefore;
		this.indentationAfter = indentationAfter;
	}

	public LSConstraint withSpacing(SpacingConstraint before, SpacingConstraint after) {
		return new LSConstraint(shape, before, after, indentationBefore, indentationAfter);
	}

	public LSConstraint withSpacingBefore(SpacingConstraint spacingBefore) {
		return new LSConstraint(shape, spacingBefore, spacingAfter, indentationBefore, indentationAfter);
	}

	public LSConstraint withSpacingAfter(SpacingConstraint spacingAfter) {
		return new LSConstraint(shape, spacingBefore, spacingAfter, indentationBefore, indentationAfter);
	}

	public LSConstraint withIndentationBefore(IndentationConstraint indentationBefore) {
		return new LSConstraint(shape, spacingBefore, spacingAfter, indentationBefore, indentationAfter);
	}

	public LSConstraint withIndentationAfter(IndentationConstraint indentationAfter) {
		return new LSConstraint(shape, spacingBefore, spacingAfter, indentationBefore, indentationAfter);
	}

	@Override
	public boolean opensSubRun() {
		return shape.opensSubRun();
	}

	@Override
	public boolean isDefined(BUTree tree) {
		return shape.isDefined(tree);
	}

	@Override
	public void render(BUTree tree, WRunRun run, Printer printer) {
		if (spacingBefore != null) printer.encounteredSpacing(spacingBefore);
		if (indentationBefore != null) printer.encounteredIndentation(indentationBefore);

		super.render(tree, run, printer);

		if (indentationAfter != null) printer.encounteredIndentation(indentationAfter);
		if (spacingAfter != null) printer.encounteredSpacing(spacingAfter);
	}
}
