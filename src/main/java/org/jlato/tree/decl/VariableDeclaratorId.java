package org.jlato.tree.decl;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.NodeList;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.name.Name;

import static org.jlato.internal.shapes.LexicalShape.Factory.child;
import static org.jlato.internal.shapes.LexicalShape.Factory.children;
import static org.jlato.internal.shapes.LexicalShape.Factory.composite;

public class VariableDeclaratorId extends Tree {

	public final static Tree.Kind kind = new Tree.Kind() {
		public VariableDeclaratorId instantiate(SLocation location) {
			return new VariableDeclaratorId(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private VariableDeclaratorId(SLocation location) {
		super(location);
	}

	public VariableDeclaratorId(Name name, NodeList<ArrayDim> dimensions) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(name, dimensions)))));
	}

	public Name name() {
		return location.nodeChild(NAME);
	}

	public VariableDeclaratorId withName(Name name) {
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

	public final static LexicalShape shape = composite(
			child(NAME), children(DIMENSIONS)
	);
}
