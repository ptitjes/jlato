package org.jlato.internal.td.stmt;

import org.jlato.internal.bu.SNodeOption;
import org.jlato.internal.bu.stmt.SBreakStmt;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeOption;
import org.jlato.tree.name.Name;
import org.jlato.tree.stmt.BreakStmt;
import org.jlato.tree.stmt.Stmt;
import org.jlato.util.Mutation;

public class TDBreakStmt extends TDTree<SBreakStmt, Stmt, BreakStmt> implements BreakStmt {

	public Kind kind() {
		return Kind.BreakStmt;
	}

	public TDBreakStmt(TDLocation<SBreakStmt> location) {
		super(location);
	}

	public TDBreakStmt(NodeOption<Name> id) {
		super(new TDLocation<SBreakStmt>(SBreakStmt.make(TDTree.<SNodeOption>treeOf(id))));
	}

	public NodeOption<Name> id() {
		return location.safeTraversal(SBreakStmt.ID);
	}

	public BreakStmt withId(NodeOption<Name> id) {
		return location.safeTraversalReplace(SBreakStmt.ID, id);
	}

	public BreakStmt withId(Mutation<NodeOption<Name>> mutation) {
		return location.safeTraversalMutate(SBreakStmt.ID, mutation);
	}
}
