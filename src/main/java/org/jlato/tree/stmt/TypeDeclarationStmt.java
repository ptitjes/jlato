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

package org.jlato.tree.stmt;

import org.jlato.tree.TreeCombinators;
import org.jlato.tree.decl.TypeDecl;
import org.jlato.util.Mutation;

/**
 * A type declaration statement.
 */
public interface TypeDeclarationStmt extends Stmt, TreeCombinators<TypeDeclarationStmt> {

	/**
	 * Returns the type declaration of this type declaration statement.
	 *
	 * @return the type declaration of this type declaration statement.
	 */
	TypeDecl typeDecl();

	/**
	 * Replaces the type declaration of this type declaration statement.
	 *
	 * @param typeDecl the replacement for the type declaration of this type declaration statement.
	 * @return the resulting mutated type declaration statement.
	 */
	TypeDeclarationStmt withTypeDecl(TypeDecl typeDecl);

	/**
	 * Mutates the type declaration of this type declaration statement.
	 *
	 * @param mutation the mutation to apply to the type declaration of this type declaration statement.
	 * @return the resulting mutated type declaration statement.
	 */
	TypeDeclarationStmt withTypeDecl(Mutation<TypeDecl> mutation);
}
