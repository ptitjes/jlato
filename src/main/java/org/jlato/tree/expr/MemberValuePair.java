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
import org.jlato.internal.bu.STree; import org.jlato.internal.td.TreeBase; import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation; import org.jlato.internal.td.TreeBase; import org.jlato.internal.bu.SNodeState;
import org.jlato.tree.Mutation;
import org.jlato.tree.Tree; import org.jlato.internal.td.TreeBase; import org.jlato.internal.bu.SNodeState;
import org.jlato.tree.name.Name;

import static org.jlato.internal.shapes.LexicalShape.*;
import org.jlato.internal.bu.STraversal;

public class MemberValuePair extends TreeBase<SNodeState> implements Tree {

	public final static TreeBase.Kind kind = new TreeBase.Kind() {
		public MemberValuePair instantiate(SLocation location) {
			return new MemberValuePair(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private MemberValuePair(SLocation<SNodeState> location) {
		super(location);
	}

	public MemberValuePair(Name name, Expr value) {
		super(new SLocation<SNodeState>(new STree<SNodeState>(kind, new SNodeState(treesOf(name, value)))));
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

	private static final STraversal<SNodeState> NAME = SNodeState.childTraversal(0);
	private static final STraversal<SNodeState> VALUE = SNodeState.childTraversal(1);

	public final static LexicalShape shape = composite(
			child(NAME), token(LToken.Assign), child(VALUE)
	);
}
