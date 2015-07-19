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
import org.jlato.tree.Kind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;
import org.jlato.tree.Tree;
import org.jlato.tree.name.Name;

import static org.jlato.internal.shapes.LexicalShape.*;
import org.jlato.internal.bu.*;
import org.jlato.internal.td.*;

public class MemberValuePair extends TreeBase<MemberValuePair.State, Tree, MemberValuePair> implements Tree {

	public Kind kind() {
		return Kind.MemberValuePair;
	}

	private MemberValuePair(SLocation<MemberValuePair.State> location) {
		super(location);
	}

	public static STree<MemberValuePair.State> make(STree<Name.State> name, STree<Expr.State> value) {
		return new STree<MemberValuePair.State>(new MemberValuePair.State(name, value));
	}

	public MemberValuePair(Name name, Expr value) {
		super(new SLocation<MemberValuePair.State>(make(TreeBase.<Name.State>nodeOf(name), TreeBase.<Expr.State>nodeOf(value))));
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

	public final static LexicalShape shape = composite(
			child(NAME), token(LToken.Assign), child(VALUE)
	);
}
