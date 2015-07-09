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

import com.github.andrewoma.dexx.collection.ArrayList;
import com.github.andrewoma.dexx.collection.Builder;
import com.github.andrewoma.dexx.collection.IndexedList;
import com.github.andrewoma.dexx.collection.Vector;
import org.jlato.internal.bu.LRun;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.STree;
import org.jlato.printer.Printer;
import org.jlato.printer.Spacing;

import java.util.Iterator;

/**
 * @author Didier Villevalois
 */
public final class LSComposite extends LexicalShape {

	public final ArrayList<LexicalShape> shapes;

	public LSComposite(LexicalShape... shapes) {
		this.shapes = makeArray(shapes);
	}

	private static ArrayList<LexicalShape> makeArray(LexicalShape[] shapes) {
		final Builder<LexicalShape, ArrayList<LexicalShape>> builder = ArrayList.<LexicalShape>factory().newBuilder();
		for (LexicalShape shape : shapes) {
			builder.add(shape);
		}
		return builder.build();
	}

	@Override
	public boolean isDefined(STree tree) {
		return true;
	}

	@Override
	public boolean isWhitespaceOnly() {
		for (LexicalShape shape : shapes) {
			if (!shape.isWhitespaceOnly()) return false;
		}
		return true;
	}

	@Override
	public LRun enRun(STree tree, Iterator<IndexedList<LToken>> tokenIterator) {
		Builder<LRun, ArrayList<LRun>> subRuns = ArrayList.<LRun>factory().newBuilder();
		Builder<IndexedList<LToken>, ArrayList<IndexedList<LToken>>> tokens = ArrayList.<IndexedList<LToken>>factory().newBuilder();

		boolean firstDefinedShape = true;
		for (LexicalShape subShape : shapes) {
			if (subShape.isWhitespaceOnly()) continue;

			if (subShape.isDefined(tree)) {
				if (firstDefinedShape) firstDefinedShape = false;
				else tokens.add(tokenIterator.next());
			} else tokens.add(Vector.<LToken>empty());

			subRuns.add(subShape.enRun(tree, tokenIterator));
		}
		return new LRun(subRuns.build(), tokens.build());
	}

	public void render(STree tree, LRun run, Printer printer) {
		final RunRenderer renderer = new RunRenderer(run);
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
		return shapes.first().spacingBefore(tree);
	}

	@Override
	public SpacingConstraint spacingAfter(STree tree) {
		return shapes.last().spacingAfter(tree);
	}
}
