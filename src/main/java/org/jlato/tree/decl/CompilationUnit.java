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
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.NodeList;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;

import static org.jlato.internal.shapes.LexicalShape.Factory.*;
import static org.jlato.internal.shapes.SpacingConstraint.Factory.newLine;
import static org.jlato.internal.shapes.SpacingConstraint.Factory.spacing;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

public class CompilationUnit extends Tree {

	public final static Kind kind = new Kind() {
		public CompilationUnit instantiate(SLocation location) {
			return new CompilationUnit(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private CompilationUnit(SLocation location) {
		super(location);
	}

	public CompilationUnit(PackageDecl packageDecl, NodeList<ImportDecl> imports, NodeList<TypeDecl> types) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(packageDecl, imports, types)))));
	}

	public PackageDecl packageDecl() {
		return location.nodeChild(PACKAGE_DECL);
	}

	public CompilationUnit withPackageDecl(PackageDecl packageDecl) {
		return location.nodeWithChild(PACKAGE_DECL, packageDecl);
	}

	public NodeList<ImportDecl> imports() {
		return location.nodeChild(IMPORTS);
	}

	public CompilationUnit withImports(NodeList<ImportDecl> imports) {
		return location.nodeWithChild(IMPORTS, imports);
	}

	public NodeList<TypeDecl> types() {
		return location.nodeChild(TYPES);
	}

	public CompilationUnit withTypes(NodeList<TypeDecl> types) {
		return location.nodeWithChild(TYPES, types);
	}

	private static final int PACKAGE_DECL = 0;
	private static final int IMPORTS = 1;
	private static final int TYPES = 2;

	public final static LexicalShape shape = composite(
			child(PACKAGE_DECL),
			none().withSpacing(spacing(CompilationUnit_AfterPackageDecl)),
			children(IMPORTS,
					none(),
					none().withSpacing(newLine()),
					none().withSpacing(spacing(CompilationUnit_AfterImports))
			),
			children(TYPES, none().withSpacing(spacing(CompilationUnit_BetweenTopLevelDecl))),
			token(LToken.EOF).withSpacingBefore(newLine())
	);
}
