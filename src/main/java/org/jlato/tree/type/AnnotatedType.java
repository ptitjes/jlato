package org.jlato.tree.type;

import org.jlato.tree.NodeList;
import org.jlato.tree.SLocation;
import org.jlato.tree.Type;
import org.jlato.tree.expr.AnnotationExpr;

/**
 * @author Didier Villevalois
 */
public abstract class AnnotatedType extends Type {

	protected AnnotatedType(SLocation location) {
		super(location);
	}

	public NodeList<AnnotationExpr> annotations() {
		return location.nodeChild(ANNOTATIONS);
	}

	public Type withAnnotations(NodeList<AnnotationExpr> annotations) {
		return location.nodeWithChild(ANNOTATIONS, annotations);
	}

	private static final int ANNOTATIONS = 0;
}
