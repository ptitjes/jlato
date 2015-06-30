package org.jlato.tree.stmt;

import org.jlato.internal.bu.SNode;
import org.jlato.tree.Stmt;
import org.jlato.tree.Tree;

public class EmptyStmt extends Stmt {

	public final static Tree.Kind kind = new Tree.Kind() {
		public EmptyStmt instantiate(SLocation location) {
			return new EmptyStmt(location);
		}
	};

	private EmptyStmt(SLocation location) {
		super(location);
	}

	public EmptyStmt() {
		super(new SLocation(new SNode(kind, runOf())));
	}

}
