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
