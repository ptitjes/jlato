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
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.name.QualifiedName;
import org.jlato.util.Mutation;

/**
 * A package declaration.
 */
public interface PackageDecl extends Node, Documentable<PackageDecl>, TreeCombinators<PackageDecl> {

	/**
	 * Returns the annotations of this package declaration.
	 *
	 * @return the annotations of this package declaration.
	 */
	NodeList<AnnotationExpr> annotations();

	/**
	 * Replaces the annotations of this package declaration.
	 *
	 * @param annotations the replacement for the annotations of this package declaration.
	 * @return the resulting mutated package declaration.
	 */
	PackageDecl withAnnotations(NodeList<AnnotationExpr> annotations);

	/**
	 * Mutates the annotations of this package declaration.
	 *
	 * @param mutation the mutation to apply to the annotations of this package declaration.
	 * @return the resulting mutated package declaration.
	 */
	PackageDecl withAnnotations(Mutation<NodeList<AnnotationExpr>> mutation);

	/**
	 * Returns the name of this package declaration.
	 *
	 * @return the name of this package declaration.
	 */
	QualifiedName name();

	/**
	 * Replaces the name of this package declaration.
	 *
	 * @param name the replacement for the name of this package declaration.
	 * @return the resulting mutated package declaration.
	 */
	PackageDecl withName(QualifiedName name);

	/**
	 * Mutates the name of this package declaration.
	 *
	 * @param mutation the mutation to apply to the name of this package declaration.
	 * @return the resulting mutated package declaration.
	 */
	PackageDecl withName(Mutation<QualifiedName> mutation);
}
