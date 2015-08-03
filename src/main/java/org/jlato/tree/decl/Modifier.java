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
import org.jlato.tree.*;
import org.jlato.util.Mutation;

import java.util.Collections;

import static org.jlato.internal.shapes.LexicalShape.token;

public class Modifier extends TreeBase<Modifier.State, ExtendedModifier, Modifier> implements ExtendedModifier {

	public static final Modifier Public = new Modifier(ModifierKeyword.Public);

	public static final Modifier Protected = new Modifier(ModifierKeyword.Protected);

	public static final Modifier Private = new Modifier(ModifierKeyword.Private);

	public static final Modifier Abstract = new Modifier(ModifierKeyword.Abstract);

	public static final Modifier Default = new Modifier(ModifierKeyword.Default);

	public static final Modifier Static = new Modifier(ModifierKeyword.Static);

	public static final Modifier Final = new Modifier(ModifierKeyword.Final);

	public static final Modifier Transient = new Modifier(ModifierKeyword.Transient);

	public static final Modifier Volatile = new Modifier(ModifierKeyword.Volatile);

	public static final Modifier Synchronized = new Modifier(ModifierKeyword.Synchronized);

	public static final Modifier Native = new Modifier(ModifierKeyword.Native);

	public static final Modifier StrictFP = new Modifier(ModifierKeyword.StrictFP);

	public Kind kind() {
		return Kind.Modifier;
	}

	private Modifier(SLocation<Modifier.State> location) {
		super(location);
	}

	public static STree<Modifier.State> make(ModifierKeyword keyword) {
		return new STree<Modifier.State>(new Modifier.State(keyword));
	}

	public Modifier(ModifierKeyword keyword) {
		super(new SLocation<Modifier.State>(make(keyword)));
	}

	public ModifierKeyword keyword() {
		return location.safeProperty(KEYWORD);
	}

	public Modifier withKeyword(ModifierKeyword keyword) {
		return location.safePropertyReplace(KEYWORD, keyword);
	}

	public Modifier withKeyword(Mutation<ModifierKeyword> mutation) {
		return location.safePropertyMutate(KEYWORD, mutation);
	}

	public static class State extends SNodeState<State> implements ExtendedModifier.State {

		public final ModifierKeyword keyword;

		State(ModifierKeyword keyword) {
			this.keyword = keyword;
		}

		public Modifier.State withKeyword(ModifierKeyword keyword) {
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
		public Iterable<SProperty> allProperties() {
			return Collections.<SProperty>singleton(KEYWORD);
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
			State state = (State) o;
			if (keyword == null ? state.keyword != null : !keyword.equals(state.keyword))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			if (keyword != null) result = 37 * result + keyword.hashCode();
			return result;
		}
	}

	private static STypeSafeProperty<Modifier.State, ModifierKeyword> KEYWORD = new STypeSafeProperty<Modifier.State, ModifierKeyword>() {

		@Override
		public ModifierKeyword doRetrieve(Modifier.State state) {
			return state.keyword;
		}

		@Override
		public Modifier.State doRebuildParentState(Modifier.State state, ModifierKeyword value) {
			return state.withKeyword(value);
		}
	};

	public static final LexicalShape shape = token(new LSToken.Provider() {
		public LToken tokenFor(STree tree) {
			final ModifierKeyword keyword = ((State) tree.state).keyword;
			switch (keyword) {
				case Public:
					return LToken.Public;
				case Protected:
					return LToken.Protected;
				case Private:
					return LToken.Private;
				case Abstract:
					return LToken.Abstract;
				case Default:
					return LToken.Default;
				case Static:
					return LToken.Static;
				case Final:
					return LToken.Final;
				case Transient:
					return LToken.Transient;
				case Volatile:
					return LToken.Volatile;
				case Synchronized:
					return LToken.Synchronized;
				case Native:
					return LToken.Native;
				case StrictFP:
					return LToken.StrictFP;
				default:
					// Can't happen by definition of enum
					throw new IllegalStateException();
			}
		}
	});
}
