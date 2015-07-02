package org.jlato.tree.stmt;

import org.jlato.internal.bu.SNode;
import org.jlato.tree.Stmt;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.NameExpr;

public class LabeledStmt extends Stmt {

	public final static Tree.Kind kind = new Tree.Kind() {
		public LabeledStmt instantiate(SLocation location) {
			return new LabeledStmt(location);
		}
	};

	private LabeledStmt(SLocation location) {
		super(location);
	}

	public LabeledStmt(NameExpr label, Stmt stmt) {
		super(new SLocation(new SNode(kind, runOf(label, stmt))));
	}

	public NameExpr label() {
		return location.nodeChild(LABEL);
	}

	public LabeledStmt withLabel(NameExpr label) {
		return location.nodeWithChild(LABEL, label);
	}

	public Stmt stmt() {
		return location.nodeChild(STMT);
	}

	public LabeledStmt withStmt(Stmt stmt) {
		return location.nodeWithChild(STMT, stmt);
	}

	private static final int LABEL = 0;
	private static final int STMT = 1;
}
