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
import org.jlato.internal.td.SLocation;

import static org.jlato.internal.shapes.LexicalShape.list;
import static org.jlato.internal.shapes.LexicalShape.none;
import static org.jlato.internal.shapes.SpacingConstraint.Factory.spacing;
import static org.jlato.printer.FormattingSettings.SpacingLocation.CompilationUnit_BetweenTopLevelDecl;

public abstract class TypeDecl extends MemberDecl {

	protected TypeDecl(SLocation location) {
		super(location);
	}

	public abstract TypeKind typeKind();

	public static final LexicalShape listShape = list(
			none().withSpacingAfter(spacing(CompilationUnit_BetweenTopLevelDecl))
	);

	public enum TypeKind {
		Empty,
		Class,
		Interface,
		Enum,
		AnnotationType,
		// Keep last comma
	}
}
