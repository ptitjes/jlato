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

import org.jlato.internal.bu.expr.SMarkerAnnotationExpr;
import org.jlato.internal.bu.name.SQualifiedName;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.expr.MarkerAnnotationExpr;
import org.jlato.tree.name.QualifiedName;
import org.jlato.util.Mutation;

/**
 * A marker annotation expression.
 */
public class TDMarkerAnnotationExpr extends TDTree<SMarkerAnnotationExpr, AnnotationExpr, MarkerAnnotationExpr> implements MarkerAnnotationExpr {

	/**
	 * Returns the kind of this marker annotation expression.
	 *
	 * @return the kind of this marker annotation expression.
	 */
	public Kind kind() {
		return Kind.MarkerAnnotationExpr;
	}

	/**
	 * Creates a marker annotation expression for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDMarkerAnnotationExpr(TDLocation<SMarkerAnnotationExpr> location) {
		super(location);
	}

	/**
	 * Creates a marker annotation expression with the specified child trees.
	 *
	 * @param name the name child tree.
	 */
	public TDMarkerAnnotationExpr(QualifiedName name) {
		super(new TDLocation<SMarkerAnnotationExpr>(SMarkerAnnotationExpr.make(TDTree.<SQualifiedName>treeOf(name))));
	}

	/**
	 * Returns the name of this marker annotation expression.
	 *
	 * @return the name of this marker annotation expression.
	 */
	public QualifiedName name() {
		return location.safeTraversal(SMarkerAnnotationExpr.NAME);
	}

	/**
	 * Replaces the name of this marker annotation expression.
	 *
	 * @param name the replacement for the name of this marker annotation expression.
	 * @return the resulting mutated marker annotation expression.
	 */
	public MarkerAnnotationExpr withName(QualifiedName name) {
		return location.safeTraversalReplace(SMarkerAnnotationExpr.NAME, name);
	}

	/**
	 * Mutates the name of this marker annotation expression.
	 *
	 * @param mutation the mutation to apply to the name of this marker annotation expression.
	 * @return the resulting mutated marker annotation expression.
	 */
	public MarkerAnnotationExpr withName(Mutation<QualifiedName> mutation) {
		return location.safeTraversalMutate(SMarkerAnnotationExpr.NAME, mutation);
	}
}
