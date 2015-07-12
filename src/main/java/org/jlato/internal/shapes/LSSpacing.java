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
public final class LSSpacing extends LSDecorated {

	private final SpacingConstraint spacingBefore;
	private final SpacingConstraint spacingAfter;
	private final IndentationConstraint indentationBefore;
	private final IndentationConstraint indentationAfter;

	public LSSpacing(LexicalShape shape,
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

	public LSSpacing withSpacing(SpacingConstraint before, SpacingConstraint after) {
		return new LSSpacing(shape, before, after, indentationBefore, indentationAfter);
	}

	public LSSpacing withSpacingBefore(SpacingConstraint spacingBefore) {
		return new LSSpacing(shape, spacingBefore, spacingAfter, indentationBefore, indentationAfter);
	}

	public LSSpacing withSpacingAfter(SpacingConstraint spacingAfter) {
		return new LSSpacing(shape, spacingBefore, spacingAfter, indentationBefore, indentationAfter);
	}

	public LSSpacing withIndentationBefore(IndentationConstraint indentationBefore) {
		return new LSSpacing(shape, spacingBefore, spacingAfter, indentationBefore, indentationAfter);
	}

	public LSSpacing withIndentationAfter(IndentationConstraint indentationAfter) {
		return new LSSpacing(shape, spacingBefore, spacingAfter, indentationBefore, indentationAfter);
	}

	@Override
	public boolean isDefined(STree tree) {
		return shape.isDefined(tree);
	}

	@Override
	public LRun enRun(STree tree, Iterator<IndexedList<LToken>> tokenIterator) {
		return super.enRun(tree, tokenIterator);
	}

	public void render(STree tree, LRun run, Printer printer) {
		if (spacingBefore != null) printer.addSpacingConstraint(spacingBefore);
		if (indentationBefore != null) printer.indent(indentationBefore.resolve(printer));

		super.render(tree, run, printer);

		if (indentationAfter != null) printer.indent(indentationAfter.resolve(printer));
		if (spacingAfter != null) printer.addSpacingConstraint(spacingAfter);
	}
}
