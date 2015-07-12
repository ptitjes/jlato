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
public class LSDump extends LexicalShape {

	private final int dataIndex;

	public LSDump(int dataIndex) {
		this.dataIndex = dataIndex;
	}

	@Override
	public boolean isDefined(STree tree) {
		return false;
	}

	@Override
	public boolean isWhitespaceOnly(STree tree) {
		return true;
	}

	@Override
	public LRun enRun(STree tree, Iterator<IndexedList<LToken>> tokenIterator) {
		throw new UnsupportedOperationException();
	}

	public void render(STree tree, LRun run, Printer printer) {
		final IndexedList<IndexedList<LToken>> data = (IndexedList<IndexedList<LToken>>) tree.state.data(dataIndex);
		if (data != null) {
			for (IndexedList<LToken> tokens : data) {
				for (LToken token : tokens) {
					printer.appendWhiteSpace(token.string);
				}
			}
		}
	}

	@Override
	public SpacingConstraint spacingBefore(STree tree) {
		return null;
	}

	@Override
	public SpacingConstraint spacingAfter(STree tree) {
		return null;
	}
}