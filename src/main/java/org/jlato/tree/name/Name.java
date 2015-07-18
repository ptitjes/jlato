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
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LSToken;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SKind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.parser.ParserImplConstants;
import org.jlato.tree.Mutation;
import org.jlato.tree.expr.Expr;

import static org.jlato.internal.shapes.LexicalShape.token;

public class Name extends TreeBase<SNodeState, Expr, Name> implements Expr {

	public final static SKind<SNodeState> kind = new SKind<SNodeState>() {
		public Name instantiate(SLocation<SNodeState> location) {
			return new Name(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private Name(SLocation<SNodeState> location) {
		super(location);
	}

	public Name(String identifier) {
		super(new SLocation<SNodeState>(new STree<SNodeState>(kind, new SNodeState(dataOf(identifier), treesOf()))));
	}

	public String name() {
		return location.data(IDENTIFIER).toString();
	}

	public Name withName(String name) {
		return location.withData(IDENTIFIER, name);
	}

	public Name withName(Mutation<String> mutation) {
		return location.mutateData(IDENTIFIER, mutation);
	}

	@Override
	public String toString() {
		return name();
	}

	public static final int IDENTIFIER = 0;

	public final static LexicalShape shape = token(new LSToken.Provider() {
		public LToken tokenFor(STree tree) {
			return new LToken(ParserImplConstants.IDENTIFIER, (String) tree.state.data(IDENTIFIER));
		}
	});
}
