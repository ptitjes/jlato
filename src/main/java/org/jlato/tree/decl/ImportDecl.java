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
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.name.QualifiedName;
import org.jlato.util.Mutation;

/**
 * An import declaration.
 */
public interface ImportDecl extends Node, TreeCombinators<ImportDecl> {

	/**
	 * Returns the name of this import declaration.
	 *
	 * @return the name of this import declaration.
	 */
	QualifiedName name();

	/**
	 * Replaces the name of this import declaration.
	 *
	 * @param name the replacement for the name of this import declaration.
	 * @return the resulting mutated import declaration.
	 */
	ImportDecl withName(QualifiedName name);

	/**
	 * Mutates the name of this import declaration.
	 *
	 * @param mutation the mutation to apply to the name of this import declaration.
	 * @return the resulting mutated import declaration.
	 */
	ImportDecl withName(Mutation<QualifiedName> mutation);

	/**
	 * Tests whether this import declaration is static.
	 *
	 * @return <code>true</code> if this import declaration is static, <code>false</code> otherwise.
	 */
	boolean isStatic();

	/**
	 * Sets whether this import declaration is static.
	 *
	 * @param isStatic <code>true</code> if this import declaration is static, <code>false</code> otherwise.
	 * @return the resulting mutated import declaration.
	 */
	ImportDecl setStatic(boolean isStatic);

	/**
	 * Mutates whether this import declaration is static.
	 *
	 * @param mutation the mutation to apply to whether this import declaration is static.
	 * @return the resulting mutated import declaration.
	 */
	ImportDecl setStatic(Mutation<Boolean> mutation);

	/**
	 * Tests whether this import declaration is on-demand.
	 *
	 * @return <code>true</code> if this import declaration is on-demand, <code>false</code> otherwise.
	 */
	boolean isOnDemand();

	/**
	 * Sets whether this import declaration is on-demand.
	 *
	 * @param isOnDemand <code>true</code> if this import declaration is on-demand, <code>false</code> otherwise.
	 * @return the resulting mutated import declaration.
	 */
	ImportDecl setOnDemand(boolean isOnDemand);

	/**
	 * Mutates whether this import declaration is on-demand.
	 *
	 * @param mutation the mutation to apply to whether this import declaration is on-demand.
	 * @return the resulting mutated import declaration.
	 */
	ImportDecl setOnDemand(Mutation<Boolean> mutation);
}
