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
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SKind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;

import static org.jlato.internal.shapes.LexicalShape.token;
import org.jlato.internal.bu.*;
import org.jlato.internal.td.*;

public class EmptyMemberDecl extends TreeBase<EmptyMemberDecl.State, MemberDecl, EmptyMemberDecl> implements MemberDecl {

	public final static SKind<EmptyMemberDecl.State> kind = new SKind<EmptyMemberDecl.State>() {
		public EmptyMemberDecl instantiate(SLocation<EmptyMemberDecl.State> location) {
			return new EmptyMemberDecl(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private EmptyMemberDecl(SLocation<EmptyMemberDecl.State> location) {
		super(location);
	}

	public static STree<EmptyMemberDecl.State> make() {
		return new STree<EmptyMemberDecl.State>(kind, new EmptyMemberDecl.State());
	}

	public EmptyMemberDecl() {
		super(new SLocation<EmptyMemberDecl.State>(make()));
	}

	@Override
	public MemberKind memberKind() {
		return MemberKind.Empty;
	}

	public final static LexicalShape shape = token(LToken.SemiColon);

	public static class State extends SNodeState<State> {

		State() {
		}

		public STraversal<EmptyMemberDecl.State> firstChild() {
			return null;
		}

		public STraversal<EmptyMemberDecl.State> lastChild() {
			return null;
		}
	}
}
