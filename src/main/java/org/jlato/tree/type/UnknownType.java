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

package org.jlato.tree.type;

import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.Kind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;

import static org.jlato.internal.shapes.LexicalShape.none;
import org.jlato.internal.bu.*;
import org.jlato.tree.Tree;
import org.jlato.internal.bu.*;
import org.jlato.internal.td.*;
import org.jlato.internal.bu.*;
import org.jlato.internal.td.*;

public class UnknownType extends TreeBase<UnknownType.State, Type, UnknownType> implements Type {

	public Kind kind() {
		return Kind.UnknownType;
	}

	private UnknownType(SLocation<UnknownType.State> location) {
		super(location);
	}

	public static STree<UnknownType.State> make() {
		return new STree<UnknownType.State>(new UnknownType.State());
	}

	public UnknownType() {
		super(new SLocation<UnknownType.State>(make()));
	}

	public static class State extends SNodeState<State>implements Type.State {

		State() {
		}

		@Override
		public Kind kind() {
			return Kind.UnknownType;
		}

		@Override
		protected Tree doInstantiate(SLocation<UnknownType.State> location) {
			return new UnknownType(location);
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

	public final static LexicalShape shape = none();
}
