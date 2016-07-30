/*
 * Copyright (C) 2015-2016 Didier Villevalois.
 *
 * This file is part of JLaTo.
 *
 * JLaTo is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * JLaTo is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JLaTo.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDCompilationUnit;
import org.jlato.internal.parser.TokenType;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.ImportDecl;
import org.jlato.tree.decl.PackageDecl;
import org.jlato.tree.decl.TypeDecl;

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for a compilation unit.
 */
public class SCompilationUnit extends SNode<SCompilationUnit> implements STree {

	/**
	 * Creates a <code>BUTree</code> with a new compilation unit.
	 *
	 * @param packageDecl the package declaration child <code>BUTree</code>.
	 * @param imports     the import declarations child <code>BUTree</code>.
	 * @param types       the types child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with a compilation unit.
	 */
	public static BUTree<SCompilationUnit> make(BUTree<SPackageDecl> packageDecl, BUTree<SNodeList> imports, BUTree<SNodeList> types) {
		return new BUTree<SCompilationUnit>(new SCompilationUnit(packageDecl, imports, types));
	}

	/**
	 * The package declaration of this compilation unit state.
	 */
	public final BUTree<SPackageDecl> packageDecl;

	/**
	 * The import declarations of this compilation unit state.
	 */
	public final BUTree<SNodeList> imports;

	/**
	 * The types of this compilation unit state.
	 */
	public final BUTree<SNodeList> types;

	/**
	 * Constructs a compilation unit state.
	 *
	 * @param packageDecl the package declaration child <code>BUTree</code>.
	 * @param imports     the import declarations child <code>BUTree</code>.
	 * @param types       the types child <code>BUTree</code>.
	 */
	public SCompilationUnit(BUTree<SPackageDecl> packageDecl, BUTree<SNodeList> imports, BUTree<SNodeList> types) {
		this.packageDecl = packageDecl;
		this.imports = imports;
		this.types = types;
	}

	/**
	 * Returns the kind of this compilation unit.
	 *
	 * @return the kind of this compilation unit.
	 */
	@Override
	public Kind kind() {
		return Kind.CompilationUnit;
	}

	/**
	 * Replaces the package declaration of this compilation unit state.
	 *
	 * @param packageDecl the replacement for the package declaration of this compilation unit state.
	 * @return the resulting mutated compilation unit state.
	 */
	public SCompilationUnit withPackageDecl(BUTree<SPackageDecl> packageDecl) {
		return new SCompilationUnit(packageDecl, imports, types);
	}

	/**
	 * Replaces the import declarations of this compilation unit state.
	 *
	 * @param imports the replacement for the import declarations of this compilation unit state.
	 * @return the resulting mutated compilation unit state.
	 */
	public SCompilationUnit withImports(BUTree<SNodeList> imports) {
		return new SCompilationUnit(packageDecl, imports, types);
	}

	/**
	 * Replaces the types of this compilation unit state.
	 *
	 * @param types the replacement for the types of this compilation unit state.
	 * @return the resulting mutated compilation unit state.
	 */
	public SCompilationUnit withTypes(BUTree<SNodeList> types) {
		return new SCompilationUnit(packageDecl, imports, types);
	}

	/**
	 * Builds a compilation unit facade for the specified compilation unit <code>TDLocation</code>.
	 *
	 * @param location the compilation unit <code>TDLocation</code>.
	 * @return a compilation unit facade for the specified compilation unit <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SCompilationUnit> location) {
		return new TDCompilationUnit(location);
	}

	/**
	 * Returns the shape for this compilation unit state.
	 *
	 * @return the shape for this compilation unit state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this compilation unit state.
	 *
	 * @return the first child traversal for this compilation unit state.
	 */
	@Override
	public STraversal firstChild() {
		return PACKAGE_DECL;
	}

	/**
	 * Returns the last child traversal for this compilation unit state.
	 *
	 * @return the last child traversal for this compilation unit state.
	 */
	@Override
	public STraversal lastChild() {
		return TYPES;
	}

	/**
	 * Compares this state object to the specified object.
	 *
	 * @param o the object to compare this state with.
	 * @return <code>true</code> if the specified object is equal to this state, <code>false</code> otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SCompilationUnit state = (SCompilationUnit) o;
		if (packageDecl == null ? state.packageDecl != null : !packageDecl.equals(state.packageDecl))
			return false;
		if (!imports.equals(state.imports))
			return false;
		if (!types.equals(state.types))
			return false;
		return true;
	}

	/**
	 * Returns a hash code for this state object.
	 *
	 * @return a hash code value for this object.
	 */
	@Override
	public int hashCode() {
		int result = 17;
		if (packageDecl != null) result = 37 * result + packageDecl.hashCode();
		result = 37 * result + imports.hashCode();
		result = 37 * result + types.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SCompilationUnit, SPackageDecl, PackageDecl> PACKAGE_DECL = new STypeSafeTraversal<SCompilationUnit, SPackageDecl, PackageDecl>() {

		@Override
		public BUTree<?> doTraverse(SCompilationUnit state) {
			return state.packageDecl;
		}

		@Override
		public SCompilationUnit doRebuildParentState(SCompilationUnit state, BUTree<SPackageDecl> child) {
			return state.withPackageDecl(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return IMPORTS;
		}
	};

	public static STypeSafeTraversal<SCompilationUnit, SNodeList, NodeList<ImportDecl>> IMPORTS = new STypeSafeTraversal<SCompilationUnit, SNodeList, NodeList<ImportDecl>>() {

		@Override
		public BUTree<?> doTraverse(SCompilationUnit state) {
			return state.imports;
		}

		@Override
		public SCompilationUnit doRebuildParentState(SCompilationUnit state, BUTree<SNodeList> child) {
			return state.withImports(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return PACKAGE_DECL;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return TYPES;
		}
	};

	public static STypeSafeTraversal<SCompilationUnit, SNodeList, NodeList<TypeDecl>> TYPES = new STypeSafeTraversal<SCompilationUnit, SNodeList, NodeList<TypeDecl>>() {

		@Override
		public BUTree<?> doTraverse(SCompilationUnit state) {
			return state.types;
		}

		@Override
		public SCompilationUnit doRebuildParentState(SCompilationUnit state, BUTree<SNodeList> child) {
			return state.withTypes(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return IMPORTS;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(PACKAGE_DECL).withSpacingAfter(spacing(CompilationUnit_AfterPackageDecl)),
			child(IMPORTS, SImportDecl.listShape),
			child(TYPES, STypeDecl.listShape),
			none().withSpacingAfter(newLine())
	);
}
