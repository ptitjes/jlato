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
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.tree.Rewrite;
import org.jlato.tree.Tree;
import org.jlato.tree.name.Name;

import static org.jlato.internal.shapes.LexicalShape.*;

public class BreakStmt extends Stmt {

	public final static Tree.Kind kind = new Tree.Kind() {
		public BreakStmt instantiate(SLocation location) {
			return new BreakStmt(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private BreakStmt(SLocation location) {
		super(location);
	}

	public BreakStmt(Name id) {
		super(new SLocation(new STree(kind, new SNodeState(treesOf(id)))));
	}

	public Name id() {
		return location.nodeChild(ID);
	}

	public BreakStmt withId(Name id) {
		return location.nodeWithChild(ID, id);
	}

	public BreakStmt withId(Rewrite<Name> id) {
		return location.nodeWithChild(ID, id);
	}

	private static final int ID = 0;

	public final static LexicalShape shape = composite(
			token(LToken.Break),
			child(ID),
			token(LToken.SemiColon)
	);
}
