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

package org.jlato.tree.name;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SLeaf;
import org.jlato.internal.bu.SLeafState;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LSToken;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.parser.ASTParserConstants;
import org.jlato.tree.Expr;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;

import static org.jlato.internal.shapes.LexicalShape.Factory.token;

public class Name extends Expr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public Name instantiate(SLocation location) {
			return new Name(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private Name(SLocation location) {
		super(location);
	}

	public Name(String identifier) {
		super(new SLocation(new SLeaf(kind, new SLeafState(dataOf(identifier)))));
	}

	public String name() {
		return location.leafData(IDENTIFIER).toString();
	}

	public Name withName(String name) {
		return location.leafWithData(IDENTIFIER, name);
	}

	@Override
	public String toString() {
		return name();
	}

	public static final int IDENTIFIER = 0;

	public final static LexicalShape shape = token(new LSToken.Provider() {
		public LToken tokenFor(STree tree) {
			return new LToken(ASTParserConstants.IDENTIFIER, (String) tree.state.data(IDENTIFIER));
		}
	});
}
