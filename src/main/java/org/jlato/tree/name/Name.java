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

import org.jlato.internal.bu.*;
import org.jlato.internal.shapes.LSToken;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.parser.ParserImplConstants;
import org.jlato.tree.Kind;
import org.jlato.tree.Mutation;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;

import static org.jlato.internal.shapes.LexicalShape.token;

public class Name extends TreeBase<Name.State, Expr, Name> implements Expr {

	public Kind kind() {
		return Kind.Name;
	}

	private Name(SLocation<Name.State> location) {
		super(location);
	}

	public static STree<Name.State> make(String identifier) {
		return new STree<Name.State>(new Name.State(identifier));
	}

	public Name(String identifier) {
		super(new SLocation<Name.State>(make(identifier)));
	}

	public String name() {
		return location.safeProperty(IDENTIFIER).toString();
	}

	public Name withName(String name) {
		return location.safePropertyReplace(IDENTIFIER, name);
	}

	public Name withName(Mutation<String> mutation) {
		return location.safePropertyMutate(IDENTIFIER, mutation);
	}

	@Override
	public String toString() {
		return name();
	}

	public static class State extends SNodeState<State> implements Expr.State {

		public final String identifier;

		State(String identifier) {
			this.identifier = identifier;
		}

		public Name.State withIdentifier(String identifier) {
			return new Name.State(identifier);
		}

		@Override
		public Kind kind() {
			return Kind.Name;
		}

		@Override
		protected Tree doInstantiate(SLocation<Name.State> location) {
			return new Name(location);
		}

		@Override
		public LexicalShape shape() {
			return shape;
		}

		@Override
		public STraversal firstChild() {
			return null;
		}

		@Override
		public STraversal lastChild() {
			return null;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			Name.State state = (Name.State) o;
			if (!identifier.equals(state.identifier))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			result = 37 * result + identifier.hashCode();
			return result;
		}
	}

	private static STypeSafeProperty<Name.State, String> IDENTIFIER = new STypeSafeProperty<Name.State, String>() {

		@Override
		protected String doRetrieve(Name.State state) {
			return state.identifier;
		}

		@Override
		protected Name.State doRebuildParentState(Name.State state, String value) {
			return state.withIdentifier(value);
		}
	};

	public final static LexicalShape shape = token(new LSToken.Provider() {
		public LToken tokenFor(STree tree) {
			return new LToken(ParserImplConstants.IDENTIFIER, ((State) tree.state).identifier);
		}
	});
}
