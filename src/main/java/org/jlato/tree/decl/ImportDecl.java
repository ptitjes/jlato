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
import org.jlato.internal.td.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.name.QualifiedName;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.Factory.newLine;
import static org.jlato.internal.shapes.SpacingConstraint.Factory.spacing;
import static org.jlato.printer.FormattingSettings.SpacingLocation.CompilationUnit_AfterImports;

public class ImportDecl extends Tree {

	public final static Kind kind = new Kind() {
		public ImportDecl instantiate(SLocation location) {
			return new ImportDecl(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private ImportDecl(SLocation location) {
		super(location);
	}

	public ImportDecl(QualifiedName name, boolean isStatic, boolean isOnDemand) {
		super(new SLocation(new STree(kind, new SNodeState(treesOf(name), dataOf(isStatic, isOnDemand)))));
	}

	public QualifiedName name() {
		return location.nodeChild(NAME);
	}

	public ImportDecl withName(QualifiedName name) {
		return location.nodeWithChild(NAME, name);
	}

	public boolean isStatic() {
		return location.nodeData(STATIC);
	}

	public ImportDecl setStatic(boolean isStatic) {
		return location.nodeWithData(STATIC, isStatic);
	}

	public boolean isOnDemand() {
		return location.nodeData(ON_DEMAND);
	}

	public ImportDecl setOnDemand(boolean isOnDemand) {
		return location.nodeWithData(ON_DEMAND, isOnDemand);
	}

	private static final int NAME = 0;

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
