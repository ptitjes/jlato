package org.jlato.internal.td.stmt;

import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.bu.stmt.SBreakStmt;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeOption;
import org.jlato.tree.name.Name;
import org.jlato.tree.stmt.BreakStmt;
import org.jlato.tree.stmt.Stmt;
import org.jlato.util.Mutation;

/**
 * A 'break' statement.
 */
public class TDBreakStmt extends TDTree<SBreakStmt, Stmt, BreakStmt> implements BreakStmt {

	/**
	 * Returns the kind of this 'break' statement.
	 *
	 * @return the kind of this 'break' statement.
	 */
	public Kind kind() {
		return Kind.BreakStmt;
	}

	/**
	 * Creates a 'break' statement for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDBreakStmt(TDLocation<SBreakStmt> location) {
		super(location);
	}

	/**
	 * Creates a 'break' statement with the specified child trees.
	 *
	 * @param id the identifier child tree.
	 */
	public TDBreakStmt(NodeOption<Name> id) {
		super(new TDLocation<SBreakStmt>(SBreakStmt.make(TDTree.<SNodeOption>treeOf(id))));
	}

	/**
	 * Returns the identifier of this 'break' statement.
	 *
	 * @return the identifier of this 'break' statement.
	 */
	public NodeOption<Name> id() {
		return location.safeTraversal(SBreakStmt.ID);
	}

	/**
	 * Replaces the identifier of this 'break' statement.
	 *
	 * @param id the replacement for the identifier of this 'break' statement.
	 * @return the resulting mutated 'break' statement.
	 */
	public BreakStmt withId(NodeOption<Name> id) {
		return location.safeTraversalReplace(SBreakStmt.ID, id);
	}

	/**
	 * Mutates the identifier of this 'break' statement.
	 *
	 * @param mutation the mutation to apply to the identifier of this 'break' statement.
	 * @return the resulting mutated 'break' statement.
	 */
	public BreakStmt withId(Mutation<NodeOption<Name>> mutation) {
		return location.safeTraversalMutate(SBreakStmt.ID, mutation);
	}
}
