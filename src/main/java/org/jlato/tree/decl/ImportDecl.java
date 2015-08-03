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

import org.jlato.internal.bu.*;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;
import org.jlato.tree.name.QualifiedName;
import org.jlato.util.Mutation;

import java.util.Arrays;

import static org.jlato.internal.shapes.LSCondition.data;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.CompilationUnit_AfterImports;
import static org.jlato.printer.SpacingConstraint.newLine;
import static org.jlato.printer.SpacingConstraint.spacing;

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
		super(new SLocation<ImportDecl.State>(make(TreeBase.<QualifiedName.State>treeOf(name), isStatic, isOnDemand)));
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
		return location.safePropertyReplace(STATIC, isStatic);
	}

	public ImportDecl setStatic(Mutation<Boolean> mutation) {
		return location.safePropertyMutate(STATIC, mutation);
	}

	public boolean isOnDemand() {
		return location.safeProperty(ON_DEMAND);
	}

	public ImportDecl setOnDemand(boolean isOnDemand) {
		return location.safePropertyReplace(ON_DEMAND, isOnDemand);
	}

	public ImportDecl setOnDemand(Mutation<Boolean> mutation) {
		return location.safePropertyMutate(ON_DEMAND, mutation);
	}

	public static class State extends SNodeState<State> implements STreeState {

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

		public ImportDecl.State setStatic(boolean isStatic) {
			return new ImportDecl.State(name, isStatic, isOnDemand);
		}

		public ImportDecl.State setOnDemand(boolean isOnDemand) {
			return new ImportDecl.State(name, isStatic, isOnDemand);
		}

		@Override
		public Kind kind() {
			return Kind.ImportDecl;
		}

		@Override
		protected Tree doInstantiate(SLocation<ImportDecl.State> location) {
			return new ImportDecl(location);
		}

		@Override
		public LexicalShape shape() {
			return shape;
		}

		@Override
		public Iterable<SProperty> allProperties() {
			return Arrays.<SProperty>asList(STATIC, ON_DEMAND);
		}

		@Override
		public STraversal firstChild() {
			return NAME;
		}

		@Override
		public STraversal lastChild() {
			return NAME;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			State state = (State) o;
			if (name == null ? state.name != null : !name.equals(state.name))
				return false;
			if (isStatic != state.isStatic)
				return false;
			if (isOnDemand != state.isOnDemand)
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			if (name != null) result = 37 * result + name.hashCode();
			result = 37 * result + (isStatic ? 1 : 0);
			result = 37 * result + (isOnDemand ? 1 : 0);
			return result;
		}
	}

	private static STypeSafeTraversal<ImportDecl.State, QualifiedName.State, QualifiedName> NAME = new STypeSafeTraversal<ImportDecl.State, QualifiedName.State, QualifiedName>() {

		@Override
		public STree<?> doTraverse(ImportDecl.State state) {
			return state.name;
		}

		@Override
		public ImportDecl.State doRebuildParentState(ImportDecl.State state, STree<QualifiedName.State> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	private static STypeSafeProperty<ImportDecl.State, Boolean> STATIC = new STypeSafeProperty<ImportDecl.State, Boolean>() {

		@Override
		public Boolean doRetrieve(ImportDecl.State state) {
			return state.isStatic;
		}

		@Override
		public ImportDecl.State doRebuildParentState(ImportDecl.State state, Boolean value) {
			return state.setStatic(value);
		}
	};

	private static STypeSafeProperty<ImportDecl.State, Boolean> ON_DEMAND = new STypeSafeProperty<ImportDecl.State, Boolean>() {

		@Override
		public Boolean doRetrieve(ImportDecl.State state) {
			return state.isOnDemand;
		}

		@Override
		public ImportDecl.State doRebuildParentState(ImportDecl.State state, Boolean value) {
			return state.setOnDemand(value);
		}
	};

	public static final LexicalShape shape = composite(
			keyword(LToken.Import),
			when(data(STATIC), keyword(LToken.Static)),
			child(NAME),
			when(data(ON_DEMAND), composite(token(LToken.Dot), token(LToken.Times))),
			token(LToken.SemiColon)
	);

	public static final LexicalShape listShape = list(
			none(),
			none().withSpacingAfter(newLine()),
			none().withSpacingAfter(spacing(CompilationUnit_AfterImports))
	);
}
