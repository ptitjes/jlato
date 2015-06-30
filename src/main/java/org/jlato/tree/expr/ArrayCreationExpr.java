package org.jlato.tree.expr;

import org.jlato.internal.bu.SNode;
import org.jlato.tree.Expr;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.type.Type;

public class ArrayCreationExpr extends Expr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public ArrayCreationExpr instantiate(SLocation location) {
			return new ArrayCreationExpr(location);
		}
	};

	private ArrayCreationExpr(SLocation location) {
		super(location);
	}

	public ArrayCreationExpr(Type type, int arrayCount, ArrayInitializerExpr initializer, NodeList<Expr> dimensions, NodeList<NodeList<AnnotationExpr>> arraysAnnotations) {
		super(new SLocation(new SNode(kind, runOf(type, arrayCount, initializer, dimensions, arraysAnnotations))));
	}

	public Type type() {
		return location.nodeChild(TYPE);
	}

	public ArrayCreationExpr withType(Type type) {
		return location.nodeWithChild(TYPE, type);
	}

	public int arrayCount() {
		return location.nodeChild(ARRAY_COUNT);
	}

	public ArrayCreationExpr withArrayCount(int arrayCount) {
		return location.nodeWithChild(ARRAY_COUNT, arrayCount);
	}

	public ArrayInitializerExpr initializer() {
		return location.nodeChild(INITIALIZER);
	}

	public ArrayCreationExpr withInitializer(ArrayInitializerExpr initializer) {
		return location.nodeWithChild(INITIALIZER, initializer);
	}

	public NodeList<Expr> dimensions() {
		return location.nodeChild(DIMENSIONS);
	}

	public ArrayCreationExpr withDimensions(NodeList<Expr> dimensions) {
		return location.nodeWithChild(DIMENSIONS, dimensions);
	}

	public NodeList<NodeList<AnnotationExpr>> arraysAnnotations() {
		return location.nodeChild(ARRAYS_ANNOTATIONS);
	}

	public ArrayCreationExpr withArraysAnnotations(NodeList<NodeList<AnnotationExpr>> arraysAnnotations) {
		return location.nodeWithChild(ARRAYS_ANNOTATIONS, arraysAnnotations);
	}

	private static final int TYPE = 0;
	private static final int ARRAY_COUNT = 1;
	private static final int INITIALIZER = 2;
	private static final int DIMENSIONS = 3;
	private static final int ARRAYS_ANNOTATIONS = 4;
}
