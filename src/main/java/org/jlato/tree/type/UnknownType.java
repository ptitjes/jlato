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

package org.jlato.tree.type;

import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;

import static org.jlato.internal.shapes.LexicalShape.none;

public class UnknownType extends TreeBase<SNodeState> implements Type {

	public final static TreeBase.Kind kind = new TreeBase.Kind() {
		public UnknownType instantiate(SLocation location) {
			return new UnknownType(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private UnknownType(SLocation<SNodeState> location) {
		super(location);
	}

	public UnknownType() {
		super(new SLocation<SNodeState>(new STree<SNodeState>(kind, new SNodeState(treesOf()))));
	}

	public final static LexicalShape shape = none();
}
