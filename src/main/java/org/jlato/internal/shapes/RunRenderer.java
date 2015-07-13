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
import org.jlato.internal.bu.WElement;
import org.jlato.internal.bu.WRun;
import org.jlato.printer.Printer;

import java.util.Iterator;

/**
 * @author Didier Villevalois
 */
public class RunRenderer {

	private final Iterator<WElement> elements;

	private boolean firstShape = true;

	public RunRenderer(WRun run) {
		elements = run == null ? null : run.elements.iterator();
	}

	public void renderNext(LexicalShape shape, STree tree, Printer printer) {
		if (firstShape) firstShape = false;
		else {
			final WRun tokens = (WRun) safeNext(elements);
			printer.addWhitespace(tokens);
		}

		final WRun subRun = (WRun) safeNext(elements);
		if (shape != null) {
			shape.render(tree, subRun, printer);
		}
	}

	private <E> E safeNext(Iterator<E> iterator) {
		return iterator == null ? null : iterator.next();
	}
}
