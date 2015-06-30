package org.jlato.tree.decl;

import org.jlato.internal.bu.SNode;
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

	public VariableDeclaratorId(NameExpr name, int arrayCount) {
		super(new SLocation(new SNode(kind, runOf(name, arrayCount))));
	}

	public NameExpr name() {
		return location.nodeChild(NAME);
	}

	public VariableDeclaratorId withName(NameExpr name) {
		return location.nodeWithChild(NAME, name);
	}

	public int arrayCount() {
		return location.nodeChild(ARRAY_COUNT);
	}

	public VariableDeclaratorId withArrayCount(int arrayCount) {
		return location.nodeWithChild(ARRAY_COUNT, arrayCount);
	}

	private static final int NAME = 0;
	private static final int ARRAY_COUNT = 1;
}
