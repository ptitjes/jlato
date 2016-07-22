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

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.WRunRun;
import org.jlato.internal.bu.WTokenRun;

/**
 * @author Didier Villevalois
 */
public class LSDecorated extends LexicalShape {

	protected final LexicalShape shape;

	public LSDecorated(LexicalShape shape) {
		this.shape = shape;
	}

	@Override
	public boolean isDefined(BUTree tree) {
		return true;
	}

	@Override
	public void dress(DressingBuilder<?> builder, BUTree<?> discriminator) {
		shape.dress(builder, discriminator);
	}

	@Override
	public boolean acceptsTrailingWhitespace() {
		return shape.acceptsTrailingWhitespace();
	}

	@Override
	public boolean acceptsLeadingWhitespace() {
		return shape.acceptsLeadingWhitespace();
	}

	@Override
	public void dressTrailing(WTokenRun tokens, DressingBuilder<?> builder) {
		shape.dressTrailing(tokens, builder);
	}

	@Override
	public void dressLeading(WTokenRun tokens, DressingBuilder<?> builder) {
		shape.dressLeading(tokens, builder);
	}

	@Override
	public void render(BUTree tree, WRunRun run, Print print) {
		shape.render(tree, run, print);
	}
}
