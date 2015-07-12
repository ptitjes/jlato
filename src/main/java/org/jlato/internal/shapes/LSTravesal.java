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
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STree;
import org.jlato.printer.Printer;

import java.util.Iterator;

/**
 * @author Didier Villevalois
 */
public final class LSTravesal extends LexicalShape {

	private final int index;
	private final LexicalShape shape;

	public LSTravesal(int index, LexicalShape shape) {
		this.index = index;
		this.shape = shape;
	}

	private STree traverse(STree tree) {
		final SNodeState state = (SNodeState) tree.state;
		return state.child(index);
	}

	@Override
	public boolean isDefined(STree tree) {
		final STree child = traverse(tree);
		return child != null && shape.isDefined(child);
	}

	@Override
	public boolean isWhitespaceOnly(STree tree) {
		final STree child = traverse(tree);
		return child != null && shape.isWhitespaceOnly(child);
	}

	@Override
	public LRun enRun(STree tree, Iterator<IndexedList<LToken>> tokenIterator) {
		return null;
	}

	public void render(STree tree, LRun run, Printer printer) {
		final STree child = traverse(tree);
		if (child == null) return;

		shape.render(child, child.run, printer);
	}

	@Override
	public SpacingConstraint spacingBefore(STree tree) {
		final STree child = traverse(tree);
		if (child == null) return null;

		return shape.spacingBefore(child);
	}

	@Override
	public SpacingConstraint spacingAfter(STree tree) {
		final STree child = traverse(tree);
		if (child == null) return null;

		return shape.spacingAfter(child);
	}
}