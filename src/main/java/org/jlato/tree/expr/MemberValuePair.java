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

package org.jlato.tree.expr;

import org.jlato.internal.bu.*;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.Mutation;
import org.jlato.tree.Tree;
import org.jlato.tree.name.Name;

import static org.jlato.internal.shapes.LexicalShape.*;

public class MemberValuePair extends TreeBase<MemberValuePair.State, Tree, MemberValuePair> implements Tree {

	public Kind kind() {
		return Kind.MemberValuePair;
	}

	private MemberValuePair(SLocation<MemberValuePair.State> location) {
		super(location);
	}

	public static STree<MemberValuePair.State> make(STree<Name.State> name, STree<? extends Expr.State> value) {
		return new STree<MemberValuePair.State>(new MemberValuePair.State(name, value));
	}

	public MemberValuePair(Name name, Expr value) {
		super(new SLocation<MemberValuePair.State>(make(TreeBase.<Name.State>treeOf(name), TreeBase.<Expr.State>treeOf(value))));
	}

	public Name name() {
		return location.safeTraversal(NAME);
	}

	public MemberValuePair withName(Name name) {
		return location.safeTraversalReplace(NAME, name);
	}

	public MemberValuePair withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(NAME, mutation);
	}

	public Expr value() {
		return location.safeTraversal(VALUE);
	}

	public MemberValuePair withValue(Expr value) {
		return location.safeTraversalReplace(VALUE, value);
	}

	public MemberValuePair withValue(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(VALUE, mutation);
	}

	public static class State extends SNodeState<State> implements STreeState {

		public final STree<Name.State> name;

		public final STree<? extends Expr.State> value;

		State(STree<Name.State> name, STree<? extends Expr.State> value) {
			this.name = name;
			this.value = value;
		}

		public MemberValuePair.State withName(STree<Name.State> name) {
			return new MemberValuePair.State(name, value);
		}

		public MemberValuePair.State withValue(STree<? extends Expr.State> value) {
			return new MemberValuePair.State(name, value);
		}

		@Override
		public Kind kind() {
			return Kind.MemberValuePair;
		}

		@Override
		protected Tree doInstantiate(SLocation<MemberValuePair.State> location) {
			return new MemberValuePair(location);
		}

		@Override
		public LexicalShape shape() {
			return shape;
		}

		@Override
		public STraversal firstChild() {
			return NAME;
		}

		@Override
		public STraversal lastChild() {
			return VALUE;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			State state = (State) o;
			if (name == null ? state.name != null : !name.equals(state.name))
				return false;
			if (value == null ? state.value != null : !value.equals(state.value))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			if (name != null) result = 37 * result + name.hashCode();
			if (value != null) result = 37 * result + value.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<MemberValuePair.State, Name.State, Name> NAME = new STypeSafeTraversal<MemberValuePair.State, Name.State, Name>() {

		@Override
		public STree<?> doTraverse(State state) {
			return state.name;
		}

		@Override
		public MemberValuePair.State doRebuildParentState(State state, STree<Name.State> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return VALUE;
		}
	};

	private static STypeSafeTraversal<MemberValuePair.State, Expr.State, Expr> VALUE = new STypeSafeTraversal<MemberValuePair.State, Expr.State, Expr>() {

		@Override
		public STree<?> doTraverse(State state) {
			return state.value;
		}

		@Override
		public MemberValuePair.State doRebuildParentState(State state, STree<Expr.State> child) {
			return state.withValue(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return NAME;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			child(NAME), token(LToken.Assign), child(VALUE)
	);
}
