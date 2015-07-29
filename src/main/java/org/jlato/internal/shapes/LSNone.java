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
import org.jlato.internal.bu.WRunRun;
import org.jlato.internal.bu.WTokenRun;
import org.jlato.printer.Printer;

/**
 * @author Didier Villevalois
 */
public final class LSNone extends LexicalShape {

	public LSNone() {
	}

	@Override
	public boolean isDefined(STree tree) {
		return false;
	}

	@Override
	public void dress(DressingBuilder<?> builder, STree<?> discriminator) {
	}

	@Override
	public boolean acceptsTrailingWhitespace() {
		return false;
	}

	@Override
	public boolean acceptsLeadingWhitespace() {
		return false;
	}

	@Override
	public void dressTrailing(WTokenRun tokens, DressingBuilder<?> builder) {
	}

	@Override
	public void dressLeading(WTokenRun tokens, DressingBuilder<?> builder) {
	}

	@Override
	public void render(STree tree, WRunRun run, Printer printer) {
	}
}
