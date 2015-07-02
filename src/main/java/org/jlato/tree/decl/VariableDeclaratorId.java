package org.jlato.tree.decl;

import org.jlato.internal.bu.SNode;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.NameExpr;

public class VariableDeclaratorId extends Tree {

	public final static Tree.Kind kind = new Tree.Kind() {
		public VariableDeclaratorId instantiate(SLocation location) {
			return new VariableDeclaratorId(location);
		}
	};

	private VariableDeclaratorId(SLocation location) {
		super(location);
	}

	public VariableDeclaratorId(NameExpr name, NodeList<ArrayDim> dimensions) {
		super(new SLocation(new SNode(kind, runOf(name, dimensions))));
	}

	public NameExpr name() {
		return location.nodeChild(NAME);
	}

	public VariableDeclaratorId withName(NameExpr name) {
		return location.nodeWithChild(NAME, name);
	}

	public NodeList<ArrayDim> dimensions() {
		return location.nodeChild(DIMENSIONS);
	}

	public VariableDeclaratorId withDimensions(NodeList<ArrayDim> dimensions) {
		return location.nodeWithChild(DIMENSIONS, dimensions);
	}

	private static final int NAME = 0;
	private static final int DIMENSIONS = 1;
}
