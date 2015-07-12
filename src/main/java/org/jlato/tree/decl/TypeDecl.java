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

import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.SLocation;

import static org.jlato.internal.shapes.LexicalShape.Factory.list;
import static org.jlato.internal.shapes.LexicalShape.Factory.none;
import static org.jlato.internal.shapes.SpacingConstraint.Factory.spacing;
import static org.jlato.printer.FormattingSettings.SpacingLocation.CompilationUnit_BetweenTopLevelDecl;

public abstract class TypeDecl extends Decl implements TopLevel, Member {

	protected TypeDecl(SLocation location) {
		super(location);
	}

	public abstract TypeKind typeKind();

//	public abstract Modifiers modifiers();
//
//	public abstract TypeDecl withModifiers(Modifiers modifiers);
//
//	public abstract Name name();
//
//	public abstract TypeDecl withName(Name name);
//
//	public abstract <M extends Decl & Member> NodeList<M> members();
//
//	public abstract <M extends Decl & Member> TypeDecl withMembers(NodeList<M> members);

	public static final LexicalShape listShape = list(
			none().withSpacing(spacing(CompilationUnit_BetweenTopLevelDecl))
	);

	public enum TypeKind {
		Empty,
		Class,
		Interface,
		Enum,
		AnnotationType
		// Keep last comma
		;
	}
}
