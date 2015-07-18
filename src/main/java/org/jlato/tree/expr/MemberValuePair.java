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

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SKind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;
import org.jlato.tree.Tree;
import org.jlato.tree.name.Name;

import static org.jlato.internal.shapes.LexicalShape.*;

public class MemberValuePair extends TreeBase<MemberValuePair.State, Tree, MemberValuePair> implements Tree {

	public final static SKind<MemberValuePair.State> kind = new SKind<MemberValuePair.State>() {

	};

	private MemberValuePair(SLocation<MemberValuePair.State> location) {
		super(location);
	}

	public static STree<MemberValuePair.State> make(Name name, Expr value) {
		return new STree<MemberValuePair.State>(kind, new MemberValuePair.State(TreeBase.<Name.State>nodeOf(name), TreeBase.<Expr.State>nodeOf(value)));
	}

	public MemberValuePair(Name name, Expr value) {
		super(new SLocation<MemberValuePair.State>(make(name, value)));
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

	public Name value() {
		return location.safeTraversal(VALUE);
	}

	public MemberValuePair withValue(Expr value) {
		return location.safeTraversalReplace(VALUE, value);
	}

	public MemberValuePair withValue(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(VALUE, mutation);
	}

	private static final STraversal<MemberValuePair.State> NAME = new STraversal<MemberValuePair.State>() {

		public STree<?> traverse(MemberValuePair.State state) {
			return state.name;
		}

		public MemberValuePair.State rebuildParentState(MemberValuePair.State state, STree<?> child) {
			return state.withName((STree) child);
		}

		public STraversal<MemberValuePair.State> leftSibling(MemberValuePair.State state) {
			return null;
		}

		public STraversal<MemberValuePair.State> rightSibling(MemberValuePair.State state) {
			return VALUE;
		}
	};
	private static final STraversal<MemberValuePair.State> VALUE = new STraversal<MemberValuePair.State>() {

		public STree<?> traverse(MemberValuePair.State state) {
			return state.value;
		}

		public MemberValuePair.State rebuildParentState(MemberValuePair.State state, STree<?> child) {
			return state.withValue((STree) child);
		}

		public STraversal<MemberValuePair.State> leftSibling(MemberValuePair.State state) {
			return NAME;
		}

		public STraversal<MemberValuePair.State> rightSibling(MemberValuePair.State state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			child(NAME), token(LToken.Assign), child(VALUE)
	);

	public static class State extends SNodeState<State> {

		public final STree<Name.State> name;

		public final STree<Expr.State> value;

		State(STree<Name.State> name, STree<Expr.State> value) {
			this.name = name;
			this.value = value;
		}

		public MemberValuePair.State withName(STree<Name.State> name) {
			return new MemberValuePair.State(name, value);
		}

		public MemberValuePair.State withValue(STree<Expr.State> value) {
			return new MemberValuePair.State(name, value);
		}

		public STraversal<MemberValuePair.State> firstChild() {
			return null;
		}

		public STraversal<MemberValuePair.State> lastChild() {
			return null;
		}

		public Tree instantiate(SLocation<MemberValuePair.State> location) {
			return new MemberValuePair(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	}
}
