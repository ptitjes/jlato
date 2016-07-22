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
import org.jlato.internal.bu.SVar;
import org.jlato.internal.bu.WRunRun;
import org.jlato.internal.bu.WTokenRun;

/**
 * @author Didier Villevalois
 */
public final class LSDefault extends LexicalShape {

	public LSDefault() {
	}

	private LexicalShape shapeFor(BUTree tree) {
		return tree.state.shape();
	}

	@Override
	public boolean isDefined(BUTree tree) {
		if (tree.state instanceof SVar) return true;

		final LexicalShape shape = shapeFor(tree);
		return shape != null && shape.isDefined(tree);
	}

	@Override
	public void dress(DressingBuilder<?> builder, BUTree<?> discriminator) {
	}

	@Override
	public boolean acceptsTrailingWhitespace() {
		return true;
	}

	@Override
	public boolean acceptsLeadingWhitespace() {
		return true;
	}

	@Override
	public void dressTrailing(WTokenRun tokens, DressingBuilder<?> builder) {
		builder.setTrailing(tokens);
	}

	@Override
	public void dressLeading(WTokenRun tokens, DressingBuilder<?> builder) {
		builder.setLeading(tokens);
	}

	@Override
	public void render(BUTree tree, WRunRun run, Print print) {
		final LexicalShape shape = shapeFor(tree);

		if (tree.dressing != null) print.encounteredLeading(tree.dressing.leading);
		shape.render(tree, print);
		if (tree.dressing != null) print.encounteredTrailing(tree.dressing.trailing);
	}
}
