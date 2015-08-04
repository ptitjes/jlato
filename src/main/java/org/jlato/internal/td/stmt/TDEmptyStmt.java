package org.jlato.internal.td.stmt;

import org.jlato.internal.bu.stmt.SEmptyStmt;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.stmt.EmptyStmt;
import org.jlato.tree.stmt.Stmt;

public class TDEmptyStmt extends TDTree<SEmptyStmt, Stmt, EmptyStmt> implements EmptyStmt {

	public Kind kind() {
		return Kind.EmptyStmt;
	}

	public TDEmptyStmt(SLocation<SEmptyStmt> location) {
		super(location);
	}

	public TDEmptyStmt() {
		super(new SLocation<SEmptyStmt>(SEmptyStmt.make()));
	}
}
