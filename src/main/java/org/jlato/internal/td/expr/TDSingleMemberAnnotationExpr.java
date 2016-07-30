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

import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.bu.expr.SSingleMemberAnnotationExpr;
import org.jlato.internal.bu.name.SQualifiedName;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.expr.SingleMemberAnnotationExpr;
import org.jlato.tree.name.QualifiedName;
import org.jlato.util.Mutation;

/**
 * A single member annotation expression.
 */
public class TDSingleMemberAnnotationExpr extends TDTree<SSingleMemberAnnotationExpr, AnnotationExpr, SingleMemberAnnotationExpr> implements SingleMemberAnnotationExpr {

	/**
	 * Returns the kind of this single member annotation expression.
	 *
	 * @return the kind of this single member annotation expression.
	 */
	public Kind kind() {
		return Kind.SingleMemberAnnotationExpr;
	}

	/**
	 * Creates a single member annotation expression for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDSingleMemberAnnotationExpr(TDLocation<SSingleMemberAnnotationExpr> location) {
		super(location);
	}

	/**
	 * Creates a single member annotation expression with the specified child trees.
	 *
	 * @param name        the name child tree.
	 * @param memberValue the member value child tree.
	 */
	public TDSingleMemberAnnotationExpr(QualifiedName name, Expr memberValue) {
		super(new TDLocation<SSingleMemberAnnotationExpr>(SSingleMemberAnnotationExpr.make(TDTree.<SQualifiedName>treeOf(name), TDTree.<SExpr>treeOf(memberValue))));
	}

	/**
	 * Returns the name of this single member annotation expression.
	 *
	 * @return the name of this single member annotation expression.
	 */
	public QualifiedName name() {
		return location.safeTraversal(SSingleMemberAnnotationExpr.NAME);
	}

	/**
	 * Replaces the name of this single member annotation expression.
	 *
	 * @param name the replacement for the name of this single member annotation expression.
	 * @return the resulting mutated single member annotation expression.
	 */
	public SingleMemberAnnotationExpr withName(QualifiedName name) {
		return location.safeTraversalReplace(SSingleMemberAnnotationExpr.NAME, name);
	}

	/**
	 * Mutates the name of this single member annotation expression.
	 *
	 * @param mutation the mutation to apply to the name of this single member annotation expression.
	 * @return the resulting mutated single member annotation expression.
	 */
	public SingleMemberAnnotationExpr withName(Mutation<QualifiedName> mutation) {
		return location.safeTraversalMutate(SSingleMemberAnnotationExpr.NAME, mutation);
	}

	/**
	 * Returns the member value of this single member annotation expression.
	 *
	 * @return the member value of this single member annotation expression.
	 */
	public Expr memberValue() {
		return location.safeTraversal(SSingleMemberAnnotationExpr.MEMBER_VALUE);
	}

	/**
	 * Replaces the member value of this single member annotation expression.
	 *
	 * @param memberValue the replacement for the member value of this single member annotation expression.
	 * @return the resulting mutated single member annotation expression.
	 */
	public SingleMemberAnnotationExpr withMemberValue(Expr memberValue) {
		return location.safeTraversalReplace(SSingleMemberAnnotationExpr.MEMBER_VALUE, memberValue);
	}

	/**
	 * Mutates the member value of this single member annotation expression.
	 *
	 * @param mutation the mutation to apply to the member value of this single member annotation expression.
	 * @return the resulting mutated single member annotation expression.
	 */
	public SingleMemberAnnotationExpr withMemberValue(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SSingleMemberAnnotationExpr.MEMBER_VALUE, mutation);
	}
}
