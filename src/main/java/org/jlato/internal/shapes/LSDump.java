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
import org.jlato.internal.bu.*;
import org.jlato.printer.Printer;

/**
 * @author Didier Villevalois
 */
public class LSDump<S extends STreeState> extends LexicalShape {

	private final SProperty property;

	public LSDump(SProperty property) {
		this.property = property;
	}

	@Override
	public boolean isDefined(STree tree) {
		return false;
	}

	@Override
	public void dress(DressingBuilder builder) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void render(STree tree, WRunRun run, Printer printer) {
		final IndexedList<WTokenRun> data = (IndexedList<WTokenRun>) property.retrieve((S) tree.state);
		if (data != null) {
			for (WTokenRun tokens : data) {
				printer.dump(tokens);
			}
		}
	}
}
