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

package org.jlato.internal.td.expr;

import org.jlato.internal.bu.decl.SLocalVariableDecl;
import org.jlato.internal.bu.expr.SVariableDeclarationExpr;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.decl.LocalVariableDecl;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.expr.VariableDeclarationExpr;
import org.jlato.util.Mutation;

/**
 * A variable declaration expression.
 */
public class TDVariableDeclarationExpr extends TDTree<SVariableDeclarationExpr, Expr, VariableDeclarationExpr> implements VariableDeclarationExpr {

	/**
	 * Returns the kind of this variable declaration expression.
	 *
	 * @return the kind of this variable declaration expression.
	 */
	public Kind kind() {
		return Kind.VariableDeclarationExpr;
	}

	/**
	 * Creates a variable declaration expression for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDVariableDeclarationExpr(TDLocation<SVariableDeclarationExpr> location) {
		super(location);
	}

	/**
	 * Creates a variable declaration expression with the specified child trees.
	 *
	 * @param declaration the declaration child tree.
	 */
	public TDVariableDeclarationExpr(LocalVariableDecl declaration) {
		super(new TDLocation<SVariableDeclarationExpr>(SVariableDeclarationExpr.make(TDTree.<SLocalVariableDecl>treeOf(declaration))));
	}

	/**
	 * Returns the declaration of this variable declaration expression.
	 *
	 * @return the declaration of this variable declaration expression.
	 */
	public LocalVariableDecl declaration() {
		return location.safeTraversal(SVariableDeclarationExpr.DECLARATION);
	}

	/**
	 * Replaces the declaration of this variable declaration expression.
	 *
	 * @param declaration the replacement for the declaration of this variable declaration expression.
	 * @return the resulting mutated variable declaration expression.
	 */
	public VariableDeclarationExpr withDeclaration(LocalVariableDecl declaration) {
		return location.safeTraversalReplace(SVariableDeclarationExpr.DECLARATION, declaration);
	}

	/**
	 * Mutates the declaration of this variable declaration expression.
	 *
	 * @param mutation the mutation to apply to the declaration of this variable declaration expression.
	 * @return the resulting mutated variable declaration expression.
	 */
	public VariableDeclarationExpr withDeclaration(Mutation<LocalVariableDecl> mutation) {
		return location.safeTraversalMutate(SVariableDeclarationExpr.DECLARATION, mutation);
	}
}
