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

import org.jlato.internal.bu.*;
import org.jlato.printer.Printer;

import java.util.Iterator;

/**
 * @author Didier Villevalois
 */
public class LSTraversal extends LSDecorated {

	private final STraversal traversal;

	public LSTraversal(STraversal traversal, LexicalShape shape) {
		super(shape);
		this.traversal = traversal;
	}

	protected STree traverse(STree tree) {
		return tree.traverse(traversal);
	}

	@Override
	public boolean isDefined(STree tree) {
		final STree child = traverse(tree);
		return child != null && (child.run != null || shape.isDefined(child));
	}

	@Override
	public WRunRun enRun(STree tree, Iterator<WTokenRun> tokenIterator) {
		final STree child = traverse(tree);
		if (child == null) return null;

		if (child.run != null) return null;
		return shape.enRun(child, tokenIterator);
	}

	@Override
	public void render(STree tree, WRunRun run, Printer printer) {
		final STree child = traverse(tree);
		if (child == null) return;

		if (child.run != null) shape.render(child, printer);
		else shape.render(child, run, printer);
	}
}
