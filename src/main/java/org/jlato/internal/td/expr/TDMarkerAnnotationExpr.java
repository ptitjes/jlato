package org.jlato.internal.td.expr;

import org.jlato.internal.bu.expr.SMarkerAnnotationExpr;
import org.jlato.internal.bu.name.SQualifiedName;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.expr.MarkerAnnotationExpr;
import org.jlato.tree.name.QualifiedName;
import org.jlato.util.Mutation;

public class TDMarkerAnnotationExpr extends TDTree<SMarkerAnnotationExpr, AnnotationExpr, MarkerAnnotationExpr> implements MarkerAnnotationExpr {

	public Kind kind() {
		return Kind.MarkerAnnotationExpr;
	}

	public TDMarkerAnnotationExpr(SLocation<SMarkerAnnotationExpr> location) {
		super(location);
	}

	public TDMarkerAnnotationExpr(QualifiedName name) {
		super(new SLocation<SMarkerAnnotationExpr>(SMarkerAnnotationExpr.make(TDTree.<SQualifiedName>treeOf(name))));
	}

	public QualifiedName name() {
		return location.safeTraversal(SMarkerAnnotationExpr.NAME);
	}

	public MarkerAnnotationExpr withName(QualifiedName name) {
		return location.safeTraversalReplace(SMarkerAnnotationExpr.NAME, name);
	}

	public MarkerAnnotationExpr withName(Mutation<QualifiedName> mutation) {
		return location.safeTraversalMutate(SMarkerAnnotationExpr.NAME, mutation);
	}
}
