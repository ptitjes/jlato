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

	public static STree<Name.State> make(String id) {
		return new STree<Name.State>(new Name.State(id));
	}

	public Name(String id) {
		super(new SLocation<Name.State>(make(id)));
	}

	public String id() {
		return location.safeProperty(ID).toString();
	}

	public Name withId(String id) {
		return location.safePropertyReplace(ID, id);
	}

	public Name withId(Mutation<String> mutation) {
		return location.safePropertyMutate(ID, mutation);
	}

	@Override
	public String toString() {
		return id();
	}

	public static class State extends SNodeState<State> implements Expr.State {

		public final String id;

		State(String id) {
			this.id = id;
		}

		public Name.State withId(String id) {
			return new Name.State(id);
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
			if (id == null ? state.id != null : !id.equals(state.id))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			if (id != null) result = 37 * result + id.hashCode();
			return result;
		}
	}

	private static STypeSafeProperty<Name.State, String> ID = new STypeSafeProperty<Name.State, String>() {

		@Override
		public String doRetrieve(State state) {
			return state.id;
		}

		@Override
		public Name.State doRebuildParentState(State state, String value) {
			return state.withId(value);
		}
	};

	public final static LexicalShape shape = token(new LSToken.Provider() {
		public LToken tokenFor(STree tree) {
			return new LToken(ParserImplConstants.IDENTIFIER, ((State) tree.state).id);
		}
	});
}
