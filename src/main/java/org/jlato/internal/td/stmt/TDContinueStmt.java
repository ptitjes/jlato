package org.jlato.internal.td.stmt;

import org.jlato.internal.bu.SNodeOptionState;
import org.jlato.internal.bu.stmt.SContinueStmt;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeOption;
import org.jlato.tree.name.Name;
import org.jlato.tree.stmt.ContinueStmt;
import org.jlato.tree.stmt.Stmt;
import org.jlato.util.Mutation;

public class TDContinueStmt extends TreeBase<SContinueStmt, Stmt, ContinueStmt> implements ContinueStmt {

	public Kind kind() {
		return Kind.ContinueStmt;
	}

	public TDContinueStmt(SLocation<SContinueStmt> location) {
		super(location);
	}

	public TDContinueStmt(NodeOption<Name> id) {
		super(new SLocation<SContinueStmt>(SContinueStmt.make(TreeBase.<SNodeOptionState>treeOf(id))));
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
