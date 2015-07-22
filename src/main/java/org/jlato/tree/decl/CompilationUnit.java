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

import com.github.andrewoma.dexx.collection.IndexedList;
import org.jlato.internal.bu.*;
import org.jlato.internal.shapes.LSDump;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.CompilationUnit_AfterPackageDecl;
import static org.jlato.printer.SpacingConstraint.newLine;
import static org.jlato.printer.SpacingConstraint.spacing;

public class CompilationUnit extends TreeBase<CompilationUnit.State, Tree, CompilationUnit> implements Tree {

	public Kind kind() {
		return Kind.CompilationUnit;
	}

	private CompilationUnit(SLocation<CompilationUnit.State> location) {
		super(location);
	}

	public static STree<CompilationUnit.State> make(IndexedList<WTokenRun> preamble, STree<PackageDecl.State> packageDecl, STree<SNodeListState> imports, STree<SNodeListState> types) {
		return new STree<CompilationUnit.State>(new CompilationUnit.State(preamble, packageDecl, imports, types));
	}

	public CompilationUnit(IndexedList<WTokenRun> preamble, PackageDecl packageDecl, NodeList<ImportDecl> imports, NodeList<TypeDecl> types) {
		super(new SLocation<CompilationUnit.State>(make(preamble, TreeBase.<PackageDecl.State>treeOf(packageDecl), TreeBase.<SNodeListState>treeOf(imports), TreeBase.<SNodeListState>treeOf(types))));
	}

	public PackageDecl packageDecl() {
		return location.safeTraversal(PACKAGE_DECL);
	}

	public CompilationUnit withPackageDecl(PackageDecl packageDecl) {
		return location.safeTraversalReplace(PACKAGE_DECL, packageDecl);
	}

	public CompilationUnit withPackageDecl(Mutation<PackageDecl> mutation) {
		return location.safeTraversalMutate(PACKAGE_DECL, mutation);
	}

	public NodeList<ImportDecl> imports() {
		return location.safeTraversal(IMPORTS);
	}

	public CompilationUnit withImports(NodeList<ImportDecl> imports) {
		return location.safeTraversalReplace(IMPORTS, imports);
	}

	public CompilationUnit withImports(Mutation<NodeList<ImportDecl>> mutation) {
		return location.safeTraversalMutate(IMPORTS, mutation);
	}

	public NodeList<TypeDecl> types() {
		return location.safeTraversal(TYPES);
	}

	public CompilationUnit withTypes(NodeList<TypeDecl> types) {
		return location.safeTraversalReplace(TYPES, types);
	}

	public CompilationUnit withTypes(Mutation<NodeList<TypeDecl>> mutation) {
		return location.safeTraversalMutate(TYPES, mutation);
	}

	public static class State extends SNodeState<State> implements STreeState {

		public final IndexedList<WTokenRun> preamble;

		public final STree<PackageDecl.State> packageDecl;

		public final STree<SNodeListState> imports;

		public final STree<SNodeListState> types;

		State(IndexedList<WTokenRun> preamble, STree<PackageDecl.State> packageDecl, STree<SNodeListState> imports, STree<SNodeListState> types) {
			this.preamble = preamble;
			this.packageDecl = packageDecl;
			this.imports = imports;
			this.types = types;
		}

		public CompilationUnit.State withPreamble(IndexedList<WTokenRun> preamble) {
			return new CompilationUnit.State(preamble, packageDecl, imports, types);
		}

		public CompilationUnit.State withPackageDecl(STree<PackageDecl.State> packageDecl) {
			return new CompilationUnit.State(preamble, packageDecl, imports, types);
		}

		public CompilationUnit.State withImports(STree<SNodeListState> imports) {
			return new CompilationUnit.State(preamble, packageDecl, imports, types);
		}

		public CompilationUnit.State withTypes(STree<SNodeListState> types) {
			return new CompilationUnit.State(preamble, packageDecl, imports, types);
		}

		@Override
		public Kind kind() {
			return Kind.CompilationUnit;
		}

		@Override
		protected Tree doInstantiate(SLocation<CompilationUnit.State> location) {
			return new CompilationUnit(location);
		}

		@Override
		public LexicalShape shape() {
			return shape;
		}

		@Override
		public STraversal firstChild() {
			return PACKAGE_DECL;
		}

		@Override
		public STraversal lastChild() {
			return TYPES;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			CompilationUnit.State state = (CompilationUnit.State) o;
			if (preamble == null ? state.preamble != null : !preamble.equals(state.preamble))
				return false;
			if (packageDecl == null ? state.packageDecl != null : !packageDecl.equals(state.packageDecl))
				return false;
			if (!imports.equals(state.imports))
				return false;
			if (!types.equals(state.types))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			if (preamble != null) result = 37 * result + preamble.hashCode();
			if (packageDecl != null) result = 37 * result + packageDecl.hashCode();
			result = 37 * result + imports.hashCode();
			result = 37 * result + types.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<CompilationUnit.State, PackageDecl.State, PackageDecl> PACKAGE_DECL = new STypeSafeTraversal<CompilationUnit.State, PackageDecl.State, PackageDecl>() {

		@Override
		protected STree<?> doTraverse(CompilationUnit.State state) {
			return state.packageDecl;
		}

		@Override
		protected CompilationUnit.State doRebuildParentState(CompilationUnit.State state, STree<PackageDecl.State> child) {
			return state.withPackageDecl(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return IMPORTS;
		}
	};

	private static STypeSafeTraversal<CompilationUnit.State, SNodeListState, NodeList<ImportDecl>> IMPORTS = new STypeSafeTraversal<CompilationUnit.State, SNodeListState, NodeList<ImportDecl>>() {

		@Override
		protected STree<?> doTraverse(CompilationUnit.State state) {
			return state.imports;
		}

		@Override
		protected CompilationUnit.State doRebuildParentState(CompilationUnit.State state, STree<SNodeListState> child) {
			return state.withImports(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return PACKAGE_DECL;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return TYPES;
		}
	};

	private static STypeSafeTraversal<CompilationUnit.State, SNodeListState, NodeList<TypeDecl>> TYPES = new STypeSafeTraversal<CompilationUnit.State, SNodeListState, NodeList<TypeDecl>>() {

		@Override
		protected STree<?> doTraverse(CompilationUnit.State state) {
			return state.types;
		}

		@Override
		protected CompilationUnit.State doRebuildParentState(CompilationUnit.State state, STree<SNodeListState> child) {
			return state.withTypes(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return IMPORTS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	private static STypeSafeProperty<CompilationUnit.State, IndexedList<WTokenRun>> PREAMBLE = new STypeSafeProperty<CompilationUnit.State, IndexedList<WTokenRun>>() {

		@Override
		protected IndexedList<WTokenRun> doRetrieve(CompilationUnit.State state) {
			return state.preamble;
		}

		@Override
		protected CompilationUnit.State doRebuildParentState(CompilationUnit.State state, IndexedList<WTokenRun> value) {
			return state.withPreamble(value);
		}
	};

	public final static LexicalShape shape = composite(
			new LSDump<CompilationUnit.State>(PREAMBLE),
			child(PACKAGE_DECL),
			none().withSpacingAfter(spacing(CompilationUnit_AfterPackageDecl)),
			child(IMPORTS, ImportDecl.listShape),
			child(TYPES, TypeDecl.listShape),
			token(LToken.EOF).withSpacingBefore(newLine())
	);
}
