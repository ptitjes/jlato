package org.jlato.tree.decl;

import org.jlato.internal.bu.SNode;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.expr.NameExpr;
import org.jlato.tree.type.Type;

public class TypeParameter extends Tree {

	public final static Kind kind = new Kind() {
		public TypeParameter instantiate(SLocation location) {
			return new TypeParameter(location);
		}
	};

	private TypeParameter(SLocation location) {
		super(location);
	}

	public TypeParameter(NodeList<AnnotationExpr> annotations, NameExpr name, NodeList<Type> bounds) {
		super(new SLocation(new SNode(kind, runOf(annotations, name, bounds))));
	}

	public NodeList<AnnotationExpr> annotations() {
		return location.nodeChild(ANNOTATIONS);
	}

	public TypeParameter withAnnotations(NodeList<AnnotationExpr> annotations) {
		return location.nodeWithChild(ANNOTATIONS, annotations);
	}

	public NameExpr name() {
		return location.nodeChild(NAME);
	}

	public TypeParameter withName(NameExpr name) {
		return location.nodeWithChild(NAME, name);
	}

	public NodeList<Type> bounds() {
		return location.nodeChild(BOUNDS);
	}

	public TypeParameter withBounds(NodeList<Type> bounds) {
		return location.nodeWithChild(BOUNDS, bounds);
	}

	private static final int ANNOTATIONS = 0;
	private static final int NAME = 1;
	private static final int BOUNDS = 2;
}
