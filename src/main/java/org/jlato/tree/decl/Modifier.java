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
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;

import static org.jlato.internal.shapes.LexicalShape.token;

public class Modifier extends TreeBase<Modifier.State, ExtendedModifier, Modifier> implements ExtendedModifier {

	public Kind kind() {
		return Kind.Modifier;
	}

	public static final Modifier Public = new Modifier(LToken.Public);

	public static STree<Modifier.State> make(LToken keyword) {
		return new STree<Modifier.State>(new Modifier.State(keyword));
	}

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

	protected Modifier(SLocation<Modifier.State> location) {
		super(location);
	}

	public Modifier(LToken keyword) {
		super(new SLocation<Modifier.State>(make(keyword)));
	}

	public String toString() {
		return location.safeProperty(KEYWORD).toString();
	}

	public static class State extends SNodeState<State> implements ExtendedModifier.State {

		public final LToken keyword;

		State(LToken keyword) {
			this.keyword = keyword;
		}

		public Modifier.State withKeyword(LToken keyword) {
			return new Modifier.State(keyword);
		}

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
	}

	private static STypeSafeProperty<Modifier.State, LToken> KEYWORD = new STypeSafeProperty<Modifier.State, LToken>() {

		@Override
		protected LToken doRetrieve(Modifier.State state) {
			return state.keyword;
		}

		@Override
		protected Modifier.State doRebuildParentState(Modifier.State state, LToken value) {
			return state.withKeyword(value);
		}
	};

	public final static LexicalShape shape = token(new LSToken.Provider() {
		public LToken tokenFor(STree tree) {
			return ((State) tree.state).keyword;
		}
	});
}
