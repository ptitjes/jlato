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
import org.jlato.internal.bu.expr.SNormalAnnotationExpr;
import org.jlato.internal.bu.name.SQualifiedName;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.expr.MemberValuePair;
import org.jlato.tree.expr.NormalAnnotationExpr;
import org.jlato.tree.name.QualifiedName;
import org.jlato.util.Mutation;

/**
 * A normal annotation expression.
 */
public class TDNormalAnnotationExpr extends TDTree<SNormalAnnotationExpr, AnnotationExpr, NormalAnnotationExpr> implements NormalAnnotationExpr {

	/**
	 * Returns the kind of this normal annotation expression.
	 *
	 * @return the kind of this normal annotation expression.
	 */
	public Kind kind() {
		return Kind.NormalAnnotationExpr;
	}

	/**
	 * Creates a normal annotation expression for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDNormalAnnotationExpr(TDLocation<SNormalAnnotationExpr> location) {
		super(location);
	}

	/**
	 * Creates a normal annotation expression with the specified child trees.
	 *
	 * @param name  the name child tree.
	 * @param pairs the pairs child tree.
	 */
	public TDNormalAnnotationExpr(QualifiedName name, NodeList<MemberValuePair> pairs) {
		super(new TDLocation<SNormalAnnotationExpr>(SNormalAnnotationExpr.make(TDTree.<SQualifiedName>treeOf(name), TDTree.<SNodeList>treeOf(pairs))));
	}

	/**
	 * Returns the name of this normal annotation expression.
	 *
	 * @return the name of this normal annotation expression.
	 */
	public QualifiedName name() {
		return location.safeTraversal(SNormalAnnotationExpr.NAME);
	}

	/**
	 * Replaces the name of this normal annotation expression.
	 *
	 * @param name the replacement for the name of this normal annotation expression.
	 * @return the resulting mutated normal annotation expression.
	 */
	public NormalAnnotationExpr withName(QualifiedName name) {
		return location.safeTraversalReplace(SNormalAnnotationExpr.NAME, name);
	}

	/**
	 * Mutates the name of this normal annotation expression.
	 *
	 * @param mutation the mutation to apply to the name of this normal annotation expression.
	 * @return the resulting mutated normal annotation expression.
	 */
	public NormalAnnotationExpr withName(Mutation<QualifiedName> mutation) {
		return location.safeTraversalMutate(SNormalAnnotationExpr.NAME, mutation);
	}

	/**
	 * Returns the pairs of this normal annotation expression.
	 *
	 * @return the pairs of this normal annotation expression.
	 */
	public NodeList<MemberValuePair> pairs() {
		return location.safeTraversal(SNormalAnnotationExpr.PAIRS);
	}

	/**
	 * Replaces the pairs of this normal annotation expression.
	 *
	 * @param pairs the replacement for the pairs of this normal annotation expression.
	 * @return the resulting mutated normal annotation expression.
	 */
	public NormalAnnotationExpr withPairs(NodeList<MemberValuePair> pairs) {
		return location.safeTraversalReplace(SNormalAnnotationExpr.PAIRS, pairs);
	}

	/**
	 * Mutates the pairs of this normal annotation expression.
	 *
	 * @param mutation the mutation to apply to the pairs of this normal annotation expression.
	 * @return the resulting mutated normal annotation expression.
	 */
	public NormalAnnotationExpr withPairs(Mutation<NodeList<MemberValuePair>> mutation) {
		return location.safeTraversalMutate(SNormalAnnotationExpr.PAIRS, mutation);
	}
}
