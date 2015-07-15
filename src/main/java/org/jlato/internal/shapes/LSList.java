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

import com.github.andrewoma.dexx.collection.Vector;
import org.jlato.internal.bu.*;
import org.jlato.printer.Printer;

import java.util.Iterator;

/**
 * @author Didier Villevalois
 */
public final class LSList extends LexicalShape {

	private final LexicalShape shape, before, separator, after;
	private final boolean renderIfEmpty;

	public LSList(LexicalShape shape, LexicalShape before, LexicalShape separator, LexicalShape after, boolean renderIfEmpty) {
		this.shape = shape;
		this.before = before;
		this.separator = separator;
		this.after = after;
		this.renderIfEmpty = renderIfEmpty;
	}

	@Override
	public boolean isDefined(STree tree) {
		final SNodeListState state = (SNodeListState) tree.state;
		final Vector<STree<?>> children = state.children;
		return !children.isEmpty() || (renderIfEmpty &&
				((before != null && before.isDefined(tree)) ||
						(after != null && after.isDefined(tree))));
	}

	@Override
	public WRunRun enRun(STree tree, Iterator<WTokenRun> tokenIterator) {
		final RunBuilder builder = new RunBuilder(tokenIterator);

		final SNodeListState state = (SNodeListState) tree.state;
		final Vector<STree<?>> children = state.children;
		final boolean isEmpty = children.isEmpty();

		builder.handleNext(isEmpty && !renderIfEmpty ? none() : before, tree);

		boolean firstElement = true;
		STree previous = null;
		for (STree child : children) {
			if (firstElement) {
				firstElement = false;
			} else {
				builder.handleNext(separator, previous);
			}

			builder.handleNext(shape, child);

			previous = child;
		}

		builder.handleNext(isEmpty && !renderIfEmpty ? none() : after, tree);

		return builder.build();
	}

	public void render(STree tree, WRunRun run, Printer printer) {
		final SNodeListState state = (SNodeListState) tree.state;
		final Vector<STree<?>> children = state.children;
		final boolean isEmpty = children.isEmpty();

		final RunRenderer renderer = new RunRenderer(run);

		renderer.renderNext(isEmpty && !renderIfEmpty ? none() : before, tree, printer);

		boolean firstElement = true;
		STree previous = null;
		for (STree child : children) {
			if (firstElement) {
				firstElement = false;
			} else {
				renderer.renderNext(separator, previous, printer);
			}

			renderer.renderNext(shape, child, printer);

			previous = child;
		}

		renderer.renderNext(isEmpty && !renderIfEmpty ? none() : after, tree, printer);
	}

}
