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

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;

import static org.jlato.internal.shapes.LexicalShape.token;

public class EmptyMemberDecl extends TreeBase<EmptyMemberDecl.State, MemberDecl, EmptyMemberDecl> implements MemberDecl {

	public Kind kind() {
		return Kind.EmptyMemberDecl;
	}

	private EmptyMemberDecl(SLocation<EmptyMemberDecl.State> location) {
		super(location);
	}

	public static STree<EmptyMemberDecl.State> make() {
		return new STree<EmptyMemberDecl.State>(new EmptyMemberDecl.State());
	}

	public EmptyMemberDecl() {
		super(new SLocation<EmptyMemberDecl.State>(make()));
	}

	@Override
	public MemberKind memberKind() {
		return MemberKind.Empty;
	}

	public static class State extends SNodeState<State> implements MemberDecl.State {

		State() {
		}

		@Override
		public Kind kind() {
			return Kind.EmptyMemberDecl;
		}

		@Override
		protected Tree doInstantiate(SLocation<EmptyMemberDecl.State> location) {
			return new EmptyMemberDecl(location);
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
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			return result;
		}
	}

	public final static LexicalShape shape = token(LToken.SemiColon);
}
