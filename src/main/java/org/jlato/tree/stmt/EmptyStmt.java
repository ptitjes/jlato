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

package org.jlato.tree.stmt;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SLeaf;
import org.jlato.internal.bu.SLeafState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.tree.Tree;

import static org.jlato.internal.shapes.LexicalShape.Factory.token;

public class EmptyStmt extends Stmt {

	public final static Tree.Kind kind = new Tree.Kind() {
		public EmptyStmt instantiate(SLocation location) {
			return new EmptyStmt(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private EmptyStmt(SLocation location) {
		super(location);
	}

	public EmptyStmt() {
		super(new SLocation(new SLeaf(kind, new SLeafState(dataOf()))));
	}

	public final static LexicalShape shape = token(LToken.SemiColon);
}
