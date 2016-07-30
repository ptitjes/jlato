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

package org.jlato.internal.td.stmt;

import org.jlato.internal.bu.decl.STypeDecl;
import org.jlato.internal.bu.stmt.STypeDeclarationStmt;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.decl.TypeDecl;
import org.jlato.tree.stmt.Stmt;
import org.jlato.tree.stmt.TypeDeclarationStmt;
import org.jlato.util.Mutation;

/**
 * A type declaration statement.
 */
public class TDTypeDeclarationStmt extends TDTree<STypeDeclarationStmt, Stmt, TypeDeclarationStmt> implements TypeDeclarationStmt {

	/**
	 * Returns the kind of this type declaration statement.
	 *
	 * @return the kind of this type declaration statement.
	 */
	public Kind kind() {
		return Kind.TypeDeclarationStmt;
	}

	/**
	 * Creates a type declaration statement for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDTypeDeclarationStmt(TDLocation<STypeDeclarationStmt> location) {
		super(location);
	}

	/**
	 * Creates a type declaration statement with the specified child trees.
	 *
	 * @param typeDecl the type declaration child tree.
	 */
	public TDTypeDeclarationStmt(TypeDecl typeDecl) {
		super(new TDLocation<STypeDeclarationStmt>(STypeDeclarationStmt.make(TDTree.<STypeDecl>treeOf(typeDecl))));
	}

	/**
	 * Returns the type declaration of this type declaration statement.
	 *
	 * @return the type declaration of this type declaration statement.
	 */
	public TypeDecl typeDecl() {
		return location.safeTraversal(STypeDeclarationStmt.TYPE_DECL);
	}

	/**
	 * Replaces the type declaration of this type declaration statement.
	 *
	 * @param typeDecl the replacement for the type declaration of this type declaration statement.
	 * @return the resulting mutated type declaration statement.
	 */
	public TypeDeclarationStmt withTypeDecl(TypeDecl typeDecl) {
		return location.safeTraversalReplace(STypeDeclarationStmt.TYPE_DECL, typeDecl);
	}

	/**
	 * Mutates the type declaration of this type declaration statement.
	 *
	 * @param mutation the mutation to apply to the type declaration of this type declaration statement.
	 * @return the resulting mutated type declaration statement.
	 */
	public TypeDeclarationStmt withTypeDecl(Mutation<TypeDecl> mutation) {
		return location.safeTraversalMutate(STypeDeclarationStmt.TYPE_DECL, mutation);
	}
}
