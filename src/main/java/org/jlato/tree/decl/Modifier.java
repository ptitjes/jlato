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

package org.jlato.tree.decl;

import org.jlato.internal.bu.*;
import org.jlato.internal.shapes.LSToken;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.SpacingConstraint;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;

import static org.jlato.internal.shapes.LexicalShape.keyword;
import static org.jlato.internal.shapes.LexicalShape.token;
import static org.jlato.printer.SpacingConstraint.space;

public class Modifier extends TreeBase<Modifier.State, ExtendedModifier, Modifier> implements ExtendedModifier {

	public Kind kind() {
		return Kind.Modifier;
	}

	public static STree<Modifier.State> make(LToken keyword) {
		switch (keyword.kind) {
			case ParserImplConstants.PUBLIC:
				return State.Public;
			case ParserImplConstants.PROTECTED:
				return State.Protected;
			case ParserImplConstants.PRIVATE:
				return State.Private;
			case ParserImplConstants.ABSTRACT:
				return State.Abstract;
			case ParserImplConstants.DEFAULT:
				return State.Default;
			case ParserImplConstants.STATIC:
				return State.Static;
			case ParserImplConstants.FINAL:
				return State.Final;
			case ParserImplConstants.TRANSIENT:
				return State.Transient;
			case ParserImplConstants.VOLATILE:
				return State.Volatile;
			case ParserImplConstants.SYNCHRONIZED:
				return State.Synchronized;
			case ParserImplConstants.NATIVE:
				return State.Native;
			case ParserImplConstants.STRICTFP:
				return State.StrictFP;
		}
		throw new IllegalStateException();
	}

	public static final Modifier Public = new Modifier(LToken.Public);

	public static final Modifier Protected = new Modifier(LToken.Protected);

	public static final Modifier Private = new Modifier(LToken.Private);

	public static final Modifier Abstract = new Modifier(LToken.Abstract);

	public static final Modifier Default = new Modifier(LToken.Default);

	public static final Modifier Static = new Modifier(LToken.Static);

	public static final Modifier Final = new Modifier(LToken.Final);

	public static final Modifier Transient = new Modifier(LToken.Transient);

	public static final Modifier Volatile = new Modifier(LToken.Volatile);

	public static final Modifier Synchronized = new Modifier(LToken.Synchronized);

	public static final Modifier Native = new Modifier(LToken.Native);

	public static final Modifier StrictFP = new Modifier(LToken.StrictFP);

	private static final Modifier[] MODIFIERS =
			new Modifier[]{Public, Protected, Private, Abstract, Default, Static, Final, Transient, Volatile, Synchronized, Native, StrictFP};

	protected Modifier(SLocation<Modifier.State> location) {
		super(location);
	}

	private Modifier(LToken keyword) {
		super(new SLocation<Modifier.State>(make(keyword)));
	}

	public String toString() {
		return location.safeProperty(KEYWORD).toString();
	}

	public static Modifier[] values() {
		return MODIFIERS;
	}

	public static class State extends SNodeState<State> implements ExtendedModifier.State {

		public final LToken keyword;

		State(LToken keyword) {
			this.keyword = keyword;
		}

		public static final STree<State> Public = new STree<State>(new State(LToken.Public));

		public static final STree<State> Protected = new STree<State>(new State(LToken.Protected));

		public static final STree<State> Private = new STree<State>(new State(LToken.Private));

		public static final STree<State> Abstract = new STree<State>(new State(LToken.Abstract));

		public static final STree<State> Default = new STree<State>(new State(LToken.Default));

		public static final STree<State> Static = new STree<State>(new State(LToken.Static));

		public static final STree<State> Final = new STree<State>(new State(LToken.Final));

		public static final STree<State> Transient = new STree<State>(new State(LToken.Transient));

		public static final STree<State> Volatile = new STree<State>(new State(LToken.Volatile));

		public static final STree<State> Synchronized = new STree<State>(new State(LToken.Synchronized));

		public static final STree<State> Native = new STree<State>(new State(LToken.Native));

		public static final STree<State> StrictFP = new STree<State>(new State(LToken.StrictFP));

		@Override
		public Kind kind() {
			return Kind.Modifier;
		}

		@Override
		protected Tree doInstantiate(SLocation<Modifier.State> location) {
			return new Modifier(location);
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
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			State state = (State) o;

			return keyword.kind == state.keyword.kind;
		}

		@Override
		public int hashCode() {
			return keyword != null ? ((Integer) keyword.kind).hashCode() : 0;
		}
	}

	private static STypeSafeProperty<Modifier.State, LToken> KEYWORD = new STypeSafeProperty<Modifier.State, LToken>() {

		@Override
		public LToken doRetrieve(State state) {
			return state.keyword;
		}

		@Override
		public Modifier.State doRebuildParentState(State state, LToken value) {
			throw new UnsupportedOperationException();
		}
	};

	public final static LexicalShape shape = token(new LSToken.Provider() {
		public LToken tokenFor(STree tree) {
			return ((State) tree.state).keyword;
		}
	});
}
