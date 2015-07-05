package org.jlato.tree.expr;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.tree.Expr;
import org.jlato.tree.NodeList;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.ArrayDim;
import org.jlato.tree.Type;

public class ArrayCreationExpr extends Expr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public ArrayCreationExpr instantiate(SLocation location) {
			return new ArrayCreationExpr(location);
		}
	};

	private ArrayCreationExpr(SLocation location) {
		super(location);
	}

	public ArrayCreationExpr(Type type, NodeList<ArrayDimExpr> dimensionExpressions, NodeList<ArrayDim> dimensions, ArrayInitializerExpr initializer) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(type, dimensionExpressions, dimensions, initializer)))));
	}

	public Type type() {
		return location.nodeChild(TYPE);
	}

	public ArrayCreationExpr withType(Type type) {
		return location.nodeWithChild(TYPE, type);
	}

	public NodeList<ArrayDimExpr> dimensionExpressions() {
		return location.nodeChild(DIMENSION_EXPRESSIONS);
	}

	public ArrayCreationExpr withDimensionExpressions(NodeList<ArrayDimExpr> dimensionExpressions) {
		return location.nodeWithChild(DIMENSION_EXPRESSIONS, dimensionExpressions);
	}

	public NodeList<ArrayDim> dimensions() {
		return location.nodeChild(DIMENSIONS);
	}

	public ArrayCreationExpr withDimensions(NodeList<ArrayDim> dimensions) {
		return location.nodeWithChild(DIMENSIONS, dimensions);
	}

	public ArrayInitializerExpr initializer() {
		return location.nodeChild(INITIALIZER);
	}

	public ArrayCreationExpr withInitializer(ArrayInitializerExpr initializer) {
		return location.nodeWithChild(INITIALIZER, initializer);
	}

	private static final int TYPE = 0;
	private static final int DIMENSION_EXPRESSIONS = 1;
	private static final int DIMENSIONS = 2;
	private static final int INITIALIZER = 3;
}
