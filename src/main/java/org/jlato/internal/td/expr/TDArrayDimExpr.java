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

import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.expr.SArrayDimExpr;
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.Node;
import org.jlato.tree.NodeList;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.expr.ArrayDimExpr;
import org.jlato.tree.expr.Expr;
import org.jlato.util.Mutation;

/**
 * An array dimension expression.
 */
public class TDArrayDimExpr extends TDTree<SArrayDimExpr, Node, ArrayDimExpr> implements ArrayDimExpr {

	/**
	 * Returns the kind of this array dimension expression.
	 *
	 * @return the kind of this array dimension expression.
	 */
	public Kind kind() {
		return Kind.ArrayDimExpr;
	}

	/**
	 * Creates an array dimension expression for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDArrayDimExpr(TDLocation<SArrayDimExpr> location) {
		super(location);
	}

	/**
	 * Creates an array dimension expression with the specified child trees.
	 *
	 * @param annotations the annotations child tree.
	 * @param expr        the expression child tree.
	 */
	public TDArrayDimExpr(NodeList<AnnotationExpr> annotations, Expr expr) {
		super(new TDLocation<SArrayDimExpr>(SArrayDimExpr.make(TDTree.<SNodeList>treeOf(annotations), TDTree.<SExpr>treeOf(expr))));
	}

	/**
	 * Returns the annotations of this array dimension expression.
	 *
	 * @return the annotations of this array dimension expression.
	 */
	public NodeList<AnnotationExpr> annotations() {
		return location.safeTraversal(SArrayDimExpr.ANNOTATIONS);
	}

	/**
	 * Replaces the annotations of this array dimension expression.
	 *
	 * @param annotations the replacement for the annotations of this array dimension expression.
	 * @return the resulting mutated array dimension expression.
	 */
	public ArrayDimExpr withAnnotations(NodeList<AnnotationExpr> annotations) {
		return location.safeTraversalReplace(SArrayDimExpr.ANNOTATIONS, annotations);
	}

	/**
	 * Mutates the annotations of this array dimension expression.
	 *
	 * @param mutation the mutation to apply to the annotations of this array dimension expression.
	 * @return the resulting mutated array dimension expression.
	 */
	public ArrayDimExpr withAnnotations(Mutation<NodeList<AnnotationExpr>> mutation) {
		return location.safeTraversalMutate(SArrayDimExpr.ANNOTATIONS, mutation);
	}

	/**
	 * Returns the expression of this array dimension expression.
	 *
	 * @return the expression of this array dimension expression.
	 */
	public Expr expr() {
		return location.safeTraversal(SArrayDimExpr.EXPR);
	}

	/**
	 * Replaces the expression of this array dimension expression.
	 *
	 * @param expr the replacement for the expression of this array dimension expression.
	 * @return the resulting mutated array dimension expression.
	 */
	public ArrayDimExpr withExpr(Expr expr) {
		return location.safeTraversalReplace(SArrayDimExpr.EXPR, expr);
	}

	/**
	 * Mutates the expression of this array dimension expression.
	 *
	 * @param mutation the mutation to apply to the expression of this array dimension expression.
	 * @return the resulting mutated array dimension expression.
	 */
	public ArrayDimExpr withExpr(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SArrayDimExpr.EXPR, mutation);
	}
}
