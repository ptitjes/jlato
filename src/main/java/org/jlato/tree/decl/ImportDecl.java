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

	private static final STraversal NAME = new STraversal() {

		public STree<?> traverse(ImportDecl.State state) {
			return state.name;
		}

		public ImportDecl.State rebuildParentState(ImportDecl.State state, STree<?> child) {
			return state.withName((STree) child);
		}

		public STraversal leftSibling(ImportDecl.State state) {
			return null;
		}

		public STraversal rightSibling(ImportDecl.State state) {
			return null;
		}
	};

	private static final SProperty STATIC = new SProperty() {

		public Object retrieve(ImportDecl.State state) {
			return state.isStatic;
		}

		public ImportDecl.State rebuildParentState(ImportDecl.State state, Object value) {
			return state.withStatic((Boolean) value);
		}
	};
	private static final SProperty ON_DEMAND = new SProperty() {

		public Object retrieve(ImportDecl.State state) {
			return state.isOnDemand;
		}

		public ImportDecl.State rebuildParentState(ImportDecl.State state, Object value) {
			return state.withOnDemand((Boolean) value);
		}
	};

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

	public static class State extends SNodeState<State> {

		public final STree<QualifiedName.State> name;

		public final boolean isStatic;

		public final boolean isOnDemand;

		State(STree<QualifiedName.State> name, boolean isStatic, boolean isOnDemand) {
			this.name = name;
			this.isStatic = isStatic;
			this.isOnDemand = isOnDemand;
		}

		public ImportDecl.State withName(STree<QualifiedName.State> name) {
			return new ImportDecl.State(name, isStatic, isOnDemand);
		}

		public ImportDecl.State withStatic(boolean isStatic) {
			return new ImportDecl.State(name, isStatic, isOnDemand);
		}

		public ImportDecl.State withOnDemand(boolean isOnDemand) {
			return new ImportDecl.State(name, isStatic, isOnDemand);
		}

		public STraversal firstChild() {
			return NAME;
		}

		public STraversal lastChild() {
			return NAME;
		}

		public Tree instantiate(SLocation<ImportDecl.State> location) {
			return new ImportDecl(location);
		}

		public LexicalShape shape() {
			return shape;
		}

		public Kind kind() {
			return Kind.ImportDecl;
		}
	}
}
