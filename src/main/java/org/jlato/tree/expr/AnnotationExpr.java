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

package org.jlato.tree.expr;

import org.jlato.tree.decl.ExtendedModifier;
import org.jlato.tree.name.QualifiedName;
import org.jlato.util.Mutation;

/**
 * An annotation expression.
 */
public interface AnnotationExpr extends Expr, ExtendedModifier {

	/**
	 * Returns the name of this annotation expression.
	 *
	 * @return the name of this annotation expression.
	 */
	QualifiedName name();

	/**
	 * Replaces the name of this annotation expression.
	 *
	 * @param name the replacement for the name of this annotation expression.
	 * @return the resulting mutated annotation expression.
	 */
	AnnotationExpr withName(QualifiedName name);

	/**
	 * Mutates the name of this annotation expression.
	 *
	 * @param mutation the mutation to apply to the name of this annotation expression.
	 * @return the resulting mutated annotation expression.
	 */
	AnnotationExpr withName(Mutation<QualifiedName> mutation);
}
