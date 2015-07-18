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
import org.jlato.internal.td.SKind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;

import static org.jlato.internal.shapes.LexicalShape.token;

public class EmptyStmt extends TreeBase<SNodeState, Stmt, EmptyStmt> implements Stmt {

	public final static SKind<SNodeState> kind = new SKind<SNodeState>() {
		public EmptyStmt instantiate(SLocation<SNodeState> location) {
			return new EmptyStmt(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private EmptyStmt(SLocation<SNodeState> location) {
		super(location);
	}

	public EmptyStmt() {
		super(new SLocation<SNodeState>(new STree<SNodeState>(kind, new SNodeState(dataOf(), treesOf()))));
	}

	public final static LexicalShape shape = token(LToken.SemiColon);
}
