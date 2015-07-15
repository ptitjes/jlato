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
import org.jlato.internal.td.SKind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;
import org.jlato.tree.Tree;
import org.jlato.tree.name.QualifiedName;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.CompilationUnit_AfterImports;
import static org.jlato.printer.SpacingConstraint.newLine;
import static org.jlato.printer.SpacingConstraint.spacing;

public class ImportDecl extends TreeBase<SNodeState> implements Tree {

	public final static SKind<SNodeState> kind = new SKind<SNodeState>() {
		public ImportDecl instantiate(SLocation<SNodeState> location) {
			return new ImportDecl(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private ImportDecl(SLocation<SNodeState> location) {
		super(location);
	}

	public ImportDecl(QualifiedName name, boolean isStatic, boolean isOnDemand) {
		super(new SLocation<SNodeState>(new STree<SNodeState>(kind, new SNodeState(dataOf(isStatic, isOnDemand), treesOf(name)))));
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
		return location.<Boolean>data(STATIC);
	}

	public ImportDecl setStatic(boolean isStatic) {
		return location.withData(STATIC, isStatic);
	}

	public boolean isOnDemand() {
		return location.<Boolean>data(ON_DEMAND);
	}

	public ImportDecl setOnDemand(boolean isOnDemand) {
		return location.withData(ON_DEMAND, isOnDemand);
	}

	private static final STraversal<SNodeState> NAME = SNodeState.childTraversal(0);

	private static final int STATIC = 0;
	private static final int ON_DEMAND = 1;

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
