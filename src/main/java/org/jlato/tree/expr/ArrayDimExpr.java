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

import org.jlato.tree.Node;
import org.jlato.tree.NodeList;
import org.jlato.tree.TreeCombinators;
import org.jlato.util.Mutation;

/**
 * An array dimension expression.
 */
public interface ArrayDimExpr extends Node, TreeCombinators<ArrayDimExpr> {

	/**
	 * Returns the annotations of this array dimension expression.
	 *
	 * @return the annotations of this array dimension expression.
	 */
	NodeList<AnnotationExpr> annotations();

	/**
	 * Replaces the annotations of this array dimension expression.
	 *
	 * @param annotations the replacement for the annotations of this array dimension expression.
	 * @return the resulting mutated array dimension expression.
	 */
	ArrayDimExpr withAnnotations(NodeList<AnnotationExpr> annotations);

	/**
	 * Mutates the annotations of this array dimension expression.
	 *
	 * @param mutation the mutation to apply to the annotations of this array dimension expression.
	 * @return the resulting mutated array dimension expression.
	 */
	ArrayDimExpr withAnnotations(Mutation<NodeList<AnnotationExpr>> mutation);

	/**
	 * Returns the expression of this array dimension expression.
	 *
	 * @return the expression of this array dimension expression.
	 */
	Expr expr();

	/**
	 * Replaces the expression of this array dimension expression.
	 *
	 * @param expr the replacement for the expression of this array dimension expression.
	 * @return the resulting mutated array dimension expression.
	 */
	ArrayDimExpr withExpr(Expr expr);

	/**
	 * Mutates the expression of this array dimension expression.
	 *
	 * @param mutation the mutation to apply to the expression of this array dimension expression.
	 * @return the resulting mutated array dimension expression.
	 */
	ArrayDimExpr withExpr(Mutation<Expr> mutation);
}
