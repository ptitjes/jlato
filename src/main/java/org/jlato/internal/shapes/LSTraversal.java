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

/**
 * @author Didier Villevalois
 */
public class LSTraversal extends LSDecorated {

	private final STraversal traversal;

	public LSTraversal(STraversal traversal, LexicalShape shape) {
		super(shape);
		this.traversal = traversal;
	}

	protected BUTree traverse(BUTree tree) {
		return tree.traverse(traversal);
	}

	@Override
	public boolean isDefined(BUTree tree) {
		final BUTree child = traverse(tree);
		return child != null && (child.dressing != null || super.isDefined(child));
	}

	@Override
	public void dress(DressingBuilder<?> builder, BUTree<?> discriminator) {
		builder.openChild(traversal);
		super.dress(builder, discriminator.traverse(traversal));
		builder.closeChild();
	}

	@Override
	public void dressTrailing(WTokenRun tokens, DressingBuilder<?> builder) {
		builder.openChild(traversal);
		super.dressTrailing(tokens, builder);
		builder.closeChild();
	}

	@Override
	public void dressLeading(WTokenRun tokens, DressingBuilder<?> builder) {
		builder.openChild(traversal);
		super.dressLeading(tokens, builder);
		builder.closeChild();
	}

	@Override
	public void render(BUTree tree, WRunRun run, Print print) {
		final BUTree child = traverse(tree);
		if (child == null) return;

		if (child.dressing != null) shape.render(child, print);
		else super.render(child, run, print);
	}
}
