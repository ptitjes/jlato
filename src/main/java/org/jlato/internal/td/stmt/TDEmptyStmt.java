package org.jlato.internal.td.stmt;

import org.jlato.internal.bu.stmt.SEmptyStmt;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.stmt.EmptyStmt;
import org.jlato.tree.stmt.Stmt;

/**
 * An empty statement.
 */
public class TDEmptyStmt extends TDTree<SEmptyStmt, Stmt, EmptyStmt> implements EmptyStmt {

	/**
	 * Returns the kind of this empty statement.
	 *
	 * @return the kind of this empty statement.
	 */
	public Kind kind() {
		return Kind.EmptyStmt;
	}

	/**
	 * Creates an empty statement for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDEmptyStmt(TDLocation<SEmptyStmt> location) {
		super(location);
	}

	/**
	 * Creates an empty statement with the specified child trees.
	 */
	public TDEmptyStmt() {
		super(new TDLocation<SEmptyStmt>(SEmptyStmt.make()));
	}
}
