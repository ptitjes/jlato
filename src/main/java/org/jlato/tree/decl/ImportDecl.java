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
import org.jlato.tree.Kind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;
import org.jlato.tree.Tree;
import org.jlato.tree.name.QualifiedName;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.CompilationUnit_AfterImports;
import static org.jlato.printer.SpacingConstraint.newLine;
import static org.jlato.printer.SpacingConstraint.spacing;
import org.jlato.internal.bu.*;
import org.jlato.internal.bu.*;
import org.jlato.internal.td.*;

public class ImportDecl extends TreeBase<ImportDecl.State, Tree, ImportDecl> implements Tree {

	public Kind kind() {
		return Kind.ImportDecl;
	}

	private ImportDecl(SLocation<ImportDecl.State> location) {
		super(location);
	}

	public static STree<ImportDecl.State> make(STree<QualifiedName.State> name, boolean isStatic, boolean isOnDemand) {
		return new STree<ImportDecl.State>(new ImportDecl.State(name, isStatic, isOnDemand));
	}

	public ImportDecl(QualifiedName name, boolean isStatic, boolean isOnDemand) {
		super(new SLocation<ImportDecl.State>(make(TreeBase.<QualifiedName.State>nodeOf(name), isStatic, isOnDemand)));
	}

	public QualifiedName name() {
		return location.safeTraversal(NAME);
	}

	public ImportDecl withName(QualifiedName name) {
		return location.safeTraversalReplace(NAME, name);
	}

	public ImportDecl withName(Mutation<QualifiedName> mutation) {
		return location.safeTraversalMutate(NAME, mutation);
	}

	public boolean isStatic() {
		return location.safeProperty(STATIC);
	}

	public ImportDecl setStatic(boolean isStatic) {
		return location.safePropertyReplace(STATIC, (Boolean) isStatic);
	}

	public boolean isOnDemand() {
		return location.safeProperty(ON_DEMAND);
	}

	public ImportDecl setOnDemand(boolean isOnDemand) {
		return location.safePropertyReplace(ON_DEMAND, (Boolean) isOnDemand);
	}

	public final static LexicalShape shape = composite(
			token(LToken.Import),
			dataOption(STATIC, token(LToken.Static)),
			child(NAME),
			dataOption(ON_DEMAND, composite(token(LToken.Dot), token(LToken.Times))),
			token(LToken.SemiColon)
	);

	public static final LexicalShape listShape = list(
			none(),
			none().withSpacingAfter(newLine()),
			none().withSpacingAfter(spacing(CompilationUnit_AfterImports))
	);
}
