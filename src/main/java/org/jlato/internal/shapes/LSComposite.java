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
import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.WRunRun;
import org.jlato.internal.bu.WTokenRun;

/**
 * @author Didier Villevalois
 */
public final class LSComposite extends LexicalShape {

	public final ArrayList<LexicalShape> shapes;

	public LSComposite(LexicalShape... shapes) {
		this.shapes = makeArray(shapes);
	}

	private static ArrayList<LexicalShape> makeArray(LexicalShape[] shapes) {
		final Builder<LexicalShape, ArrayList<LexicalShape>> builder = ArrayList.<LexicalShape>factory().newBuilder();
		for (LexicalShape shape : shapes) {
			builder.add(shape);
		}
		return builder.build();
	}

	@Override
	public boolean isDefined(BUTree tree) {
		for (LexicalShape shape : shapes) {
			if (shape.isDefined(tree)) return true;
		}
		return false;
	}

	@Override
	public void dress(DressingBuilder<?> builder, BUTree<?> discriminator) {
		builder.openRun();
		for (LexicalShape shape : shapes) {
			builder.handleNext(shape, discriminator);
		}
		builder.closeRun();
	}

	@Override
	public void render(BUTree tree, WRunRun run, Print print) {
		final RunRenderer renderer = new RunRenderer(print, run);

		for (LexicalShape shape : shapes) {
			renderer.handleNext(shape, tree);
		}
	}
}
