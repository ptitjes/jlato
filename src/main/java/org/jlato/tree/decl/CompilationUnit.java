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
import org.jlato.tree.Kind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
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
		super(new SLocation<CompilationUnit.State>(make(preamble, TreeBase.<PackageDecl.State>nodeOf(packageDecl), TreeBase.<SNodeListState>nodeOf(imports), TreeBase.<SNodeListState>nodeOf(types))));
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

	private static final STraversal PACKAGE_DECL = new STraversal() {

		public STree<?> traverse(CompilationUnit.State state) {
			return state.packageDecl;
		}

		public CompilationUnit.State rebuildParentState(CompilationUnit.State state, STree<?> child) {
			return state.withPackageDecl((STree) child);
		}

		public STraversal leftSibling(CompilationUnit.State state) {
			return null;
		}

		public STraversal rightSibling(CompilationUnit.State state) {
			return IMPORTS;
		}
	};
	private static final STraversal IMPORTS = new STraversal() {

		public STree<?> traverse(CompilationUnit.State state) {
			return state.imports;
		}

		public CompilationUnit.State rebuildParentState(CompilationUnit.State state, STree<?> child) {
			return state.withImports((STree) child);
		}

		public STraversal leftSibling(CompilationUnit.State state) {
			return PACKAGE_DECL;
		}

		public STraversal rightSibling(CompilationUnit.State state) {
			return TYPES;
		}
	};
	private static final STraversal TYPES = new STraversal() {

		public STree<?> traverse(CompilationUnit.State state) {
			return state.types;
		}

		public CompilationUnit.State rebuildParentState(CompilationUnit.State state, STree<?> child) {
			return state.withTypes((STree) child);
		}

		public STraversal leftSibling(CompilationUnit.State state) {
			return IMPORTS;
		}

		public STraversal rightSibling(CompilationUnit.State state) {
			return null;
		}
	};

	private static final SProperty PREAMBLE = new SProperty() {

		public Object retrieve(CompilationUnit.State state) {
			return state.preamble;
		}

		public CompilationUnit.State rebuildParentState(CompilationUnit.State state, Object value) {
			return state.withPreamble((IndexedList<WTokenRun>) value);
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

	public static class State extends SNodeState<State> {

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

		public STraversal firstChild() {
			return PACKAGE_DECL;
		}

		public STraversal lastChild() {
			return TYPES;
		}

		public Tree instantiate(SLocation<CompilationUnit.State> location) {
			return new CompilationUnit(location);
		}

		public LexicalShape shape() {
			return shape;
		}

		public Kind kind() {
			return Kind.CompilationUnit;
		}
	}
}
