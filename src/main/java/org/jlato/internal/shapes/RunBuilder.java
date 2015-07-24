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
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.WRun;
import org.jlato.internal.bu.WRunRun;
import org.jlato.internal.bu.WTokenRun;

import java.util.Iterator;

/**
 * @author Didier Villevalois
 */
public class RunBuilder implements ShapeHandler {

	private final Iterator<WTokenRun> tokenIterator;

	private Builder<WRun, ArrayList<WRun>> elements = ArrayList.<WRun>factory().newBuilder();
	private boolean firstShape = true;
	private boolean firstDefinedShape = true;
	private int shapeCount = 0;

	public RunBuilder(Iterator<WTokenRun> tokenIterator) {
		this.tokenIterator = tokenIterator;
	}

	public void handleNext(LexicalShape shape, STree tree) {
		shapeCount++;

		final boolean defined = shape != null && shape.isDefined(tree);

		if (!defined) {
			if (firstShape) firstShape = false;
			else elements.add(null);

			elements.add(null);
		} else {
			if (firstShape) {
				firstShape = false;
				if (firstDefinedShape) firstDefinedShape = false;
			} else if (firstDefinedShape) {
				firstDefinedShape = false;
				elements.add(null);
			} else elements.add(tokenIterator.next());

			elements.add(shape.enRun(tree, tokenIterator));
		}
	}

	public WRunRun build() {
		final WRunRun run = new WRunRun(elements.build());
		if (run.elements.size() != shapeCount * 2 - 1) {
			throw new IllegalStateException();
		}
		return run;
	}
}
