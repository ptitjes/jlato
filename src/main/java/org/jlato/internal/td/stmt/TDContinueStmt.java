package org.jlato.internal.td.stmt;

import org.jlato.internal.bu.SNodeOption;
import org.jlato.internal.bu.stmt.SContinueStmt;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeOption;
import org.jlato.tree.name.Name;
import org.jlato.tree.stmt.ContinueStmt;
import org.jlato.tree.stmt.Stmt;
import org.jlato.util.Mutation;

public class TDContinueStmt extends TDTree<SContinueStmt, Stmt, ContinueStmt> implements ContinueStmt {

	public Kind kind() {
		return Kind.ContinueStmt;
	}

	public TDContinueStmt(TDLocation<SContinueStmt> location) {
		super(location);
	}

	public TDContinueStmt(NodeOption<Name> id) {
		super(new TDLocation<SContinueStmt>(SContinueStmt.make(TDTree.<SNodeOption>treeOf(id))));
	}

	public NodeOption<Name> id() {
		return location.safeTraversal(SContinueStmt.ID);
	}

	public ContinueStmt withId(NodeOption<Name> id) {
		return location.safeTraversalReplace(SContinueStmt.ID, id);
	}

	public ContinueStmt withId(Mutation<NodeOption<Name>> mutation) {
		return location.safeTraversalMutate(SContinueStmt.ID, mutation);
	}
}
