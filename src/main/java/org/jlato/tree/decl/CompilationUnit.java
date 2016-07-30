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

package org.jlato.tree.decl;

import org.jlato.tree.Node;
import org.jlato.tree.NodeList;
import org.jlato.tree.TreeCombinators;
import org.jlato.util.Mutation;

/**
 * A compilation unit.
 */
public interface CompilationUnit extends Node, TreeCombinators<CompilationUnit> {

	/**
	 * Returns the package declaration of this compilation unit.
	 *
	 * @return the package declaration of this compilation unit.
	 */
	PackageDecl packageDecl();

	/**
	 * Replaces the package declaration of this compilation unit.
	 *
	 * @param packageDecl the replacement for the package declaration of this compilation unit.
	 * @return the resulting mutated compilation unit.
	 */
	CompilationUnit withPackageDecl(PackageDecl packageDecl);

	/**
	 * Mutates the package declaration of this compilation unit.
	 *
	 * @param mutation the mutation to apply to the package declaration of this compilation unit.
	 * @return the resulting mutated compilation unit.
	 */
	CompilationUnit withPackageDecl(Mutation<PackageDecl> mutation);

	/**
	 * Returns the import declarations of this compilation unit.
	 *
	 * @return the import declarations of this compilation unit.
	 */
	NodeList<ImportDecl> imports();

	/**
	 * Replaces the import declarations of this compilation unit.
	 *
	 * @param imports the replacement for the import declarations of this compilation unit.
	 * @return the resulting mutated compilation unit.
	 */
	CompilationUnit withImports(NodeList<ImportDecl> imports);

	/**
	 * Mutates the import declarations of this compilation unit.
	 *
	 * @param mutation the mutation to apply to the import declarations of this compilation unit.
	 * @return the resulting mutated compilation unit.
	 */
	CompilationUnit withImports(Mutation<NodeList<ImportDecl>> mutation);

	/**
	 * Returns the types of this compilation unit.
	 *
	 * @return the types of this compilation unit.
	 */
	NodeList<TypeDecl> types();

	/**
	 * Replaces the types of this compilation unit.
	 *
	 * @param types the replacement for the types of this compilation unit.
	 * @return the resulting mutated compilation unit.
	 */
	CompilationUnit withTypes(NodeList<TypeDecl> types);

	/**
	 * Mutates the types of this compilation unit.
	 *
	 * @param mutation the mutation to apply to the types of this compilation unit.
	 * @return the resulting mutated compilation unit.
	 */
	CompilationUnit withTypes(Mutation<NodeList<TypeDecl>> mutation);
}
