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

package org.jlato.rewrite;

import com.github.andrewoma.dexx.collection.ArrayList;
import org.jlato.internal.bu.SLeafState;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Tree; import org.jlato.internal.td.TreeBase; import org.jlato.internal.bu.SNodeState;

/**
 * @author Didier Villevalois
 */
class LeafPattern<T extends Tree> extends TreePattern<T> {

	public LeafPattern(TreeBase.Kind kind, ArrayList<Pattern<?>> data) {
		super(kind, data);
	}

	@Override
	protected STreeState buildState(Substitution substitution) {
		return new SLeafState(buildData(substitution));
	}
}
