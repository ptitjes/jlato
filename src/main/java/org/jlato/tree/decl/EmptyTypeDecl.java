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
import org.jlato.internal.bu.SLeafState;
import org.jlato.internal.bu.STree; import org.jlato.internal.td.TreeBase; import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation; import org.jlato.internal.td.TreeBase; import org.jlato.internal.bu.SNodeState;
import org.jlato.tree.Tree; import org.jlato.internal.td.TreeBase; import org.jlato.internal.bu.SNodeState;

import static org.jlato.internal.shapes.LexicalShape.token;

public class EmptyTypeDecl extends TreeBase<SLeafState> implements TypeDecl {

	public final static TreeBase.Kind kind = new TreeBase.Kind() {
		public EmptyTypeDecl instantiate(SLocation location) {
			return new EmptyTypeDecl(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private EmptyTypeDecl(SLocation<SLeafState> location) {
		super(location);
	}

	public EmptyTypeDecl() {
		super(new SLocation<SLeafState>(new STree<SLeafState>(kind, new SLeafState(dataOf()))));
	}

	@Override
	public MemberKind memberKind() {
		return MemberKind.Type;
	}

	@Override
	public TypeKind typeKind() {
		return TypeKind.Empty;
	}

	public final static LexicalShape shape = token(LToken.SemiColon);
}
