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
public final class LSAlternative extends LexicalShape {

	private final LSCondition condition;
	private final LexicalShape shape, alternative;

	public LSAlternative(LSCondition condition, LexicalShape shape, LexicalShape alternative) {
		this.condition = condition;
		this.shape = shape;
		this.alternative = alternative;
	}

	@Override
	public boolean isDefined(BUTree tree) {
		return condition.test(tree) ?
				shape != null && shape.isDefined(tree) :
				alternative != null && alternative.isDefined(tree);
	}

	@Override
	public void dress(DressingBuilder<?> builder, BUTree<?> discriminator) {
		if (condition.test(discriminator)) {
			if (shape != null) shape.dress(builder, discriminator);
		} else {
			if (alternative != null) alternative.dress(builder, discriminator);
		}
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
	public void render(BUTree tree, WRunRun run, Print print) {
		if (condition.test(tree)) {
			if (shape != null) shape.render(tree, run, print);
		} else {
			if (alternative != null) alternative.render(tree, run, print);
		}
	}
}
