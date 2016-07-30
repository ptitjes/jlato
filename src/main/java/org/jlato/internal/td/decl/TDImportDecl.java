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

package org.jlato.internal.td.decl;

import org.jlato.internal.bu.decl.SImportDecl;
import org.jlato.internal.bu.name.SQualifiedName;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.Node;
import org.jlato.tree.decl.ImportDecl;
import org.jlato.tree.name.QualifiedName;
import org.jlato.util.Mutation;

/**
 * An import declaration.
 */
public class TDImportDecl extends TDTree<SImportDecl, Node, ImportDecl> implements ImportDecl {

	/**
	 * Returns the kind of this import declaration.
	 *
	 * @return the kind of this import declaration.
	 */
	public Kind kind() {
		return Kind.ImportDecl;
	}

	/**
	 * Creates an import declaration for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDImportDecl(TDLocation<SImportDecl> location) {
		super(location);
	}

	/**
	 * Creates an import declaration with the specified child trees.
	 *
	 * @param name       the name child tree.
	 * @param isStatic   the is static child tree.
	 * @param isOnDemand the is on-demand child tree.
	 */
	public TDImportDecl(QualifiedName name, boolean isStatic, boolean isOnDemand) {
		super(new TDLocation<SImportDecl>(SImportDecl.make(TDTree.<SQualifiedName>treeOf(name), isStatic, isOnDemand)));
	}

	/**
	 * Returns the name of this import declaration.
	 *
	 * @return the name of this import declaration.
	 */
	public QualifiedName name() {
		return location.safeTraversal(SImportDecl.NAME);
	}

	/**
	 * Replaces the name of this import declaration.
	 *
	 * @param name the replacement for the name of this import declaration.
	 * @return the resulting mutated import declaration.
	 */
	public ImportDecl withName(QualifiedName name) {
		return location.safeTraversalReplace(SImportDecl.NAME, name);
	}

	/**
	 * Mutates the name of this import declaration.
	 *
	 * @param mutation the mutation to apply to the name of this import declaration.
	 * @return the resulting mutated import declaration.
	 */
	public ImportDecl withName(Mutation<QualifiedName> mutation) {
		return location.safeTraversalMutate(SImportDecl.NAME, mutation);
	}

	/**
	 * Tests whether this import declaration is static.
	 *
	 * @return <code>true</code> if this import declaration is static, <code>false</code> otherwise.
	 */
	public boolean isStatic() {
		return location.safeProperty(SImportDecl.STATIC);
	}

	/**
	 * Sets whether this import declaration is static.
	 *
	 * @param isStatic <code>true</code> if this import declaration is static, <code>false</code> otherwise.
	 * @return the resulting mutated import declaration.
	 */
	public ImportDecl setStatic(boolean isStatic) {
		return location.safePropertyReplace(SImportDecl.STATIC, isStatic);
	}

	/**
	 * Mutates whether this import declaration is static.
	 *
	 * @param mutation the mutation to apply to whether this import declaration is static.
	 * @return the resulting mutated import declaration.
	 */
	public ImportDecl setStatic(Mutation<Boolean> mutation) {
		return location.safePropertyMutate(SImportDecl.STATIC, mutation);
	}

	/**
	 * Tests whether this import declaration is on-demand.
	 *
	 * @return <code>true</code> if this import declaration is on-demand, <code>false</code> otherwise.
	 */
	public boolean isOnDemand() {
		return location.safeProperty(SImportDecl.ON_DEMAND);
	}

	/**
	 * Sets whether this import declaration is on-demand.
	 *
	 * @param isOnDemand <code>true</code> if this import declaration is on-demand, <code>false</code> otherwise.
	 * @return the resulting mutated import declaration.
	 */
	public ImportDecl setOnDemand(boolean isOnDemand) {
		return location.safePropertyReplace(SImportDecl.ON_DEMAND, isOnDemand);
	}

	/**
	 * Mutates whether this import declaration is on-demand.
	 *
	 * @param mutation the mutation to apply to whether this import declaration is on-demand.
	 * @return the resulting mutated import declaration.
	 */
	public ImportDecl setOnDemand(Mutation<Boolean> mutation) {
		return location.safePropertyMutate(SImportDecl.ON_DEMAND, mutation);
	}
}
