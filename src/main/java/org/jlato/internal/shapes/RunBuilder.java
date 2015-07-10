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
import org.jlato.parser.ParserBase;

import java.util.Iterator;

/**
 * @author Didier Villevalois
 */
public class RunBuilder {

	private final Iterator<IndexedList<LToken>> tokenIterator;

	private Builder<LRun, ArrayList<LRun>> subRuns = ArrayList.<LRun>factory().newBuilder();
	private Builder<IndexedList<LToken>, ArrayList<IndexedList<LToken>>> tokens = ArrayList.<IndexedList<LToken>>factory().newBuilder();
	private boolean firstDefinedShape = true;
	private int shapeCount = 0;

	public RunBuilder(Iterator<IndexedList<LToken>> tokenIterator) {
		this.tokenIterator = tokenIterator;
	}

	public void handleNext(LexicalShape shape, STree tree) {
		if (shape.isWhitespaceOnly(tree)) return;

		if (shape.isDefined(tree)) {
			if (firstDefinedShape) firstDefinedShape = false;
			else {
				final IndexedList<LToken> incomming = tokenIterator.next();
				tokens.add(incomming);
			}
		} else tokens.add(Vector.<LToken>empty());

		shapeCount++;

		subRuns.add(shape.enRun(tree, tokenIterator));
	}

	public LRun build() {
		final LRun run = new LRun(subRuns.build(), tokens.build());
		if (run.subRuns.size() != shapeCount ||
				(shapeCount > 0 && run.whitespaces.size() != shapeCount - 1)) {
			throw new IllegalStateException();
		}
		return run;
	}
}
