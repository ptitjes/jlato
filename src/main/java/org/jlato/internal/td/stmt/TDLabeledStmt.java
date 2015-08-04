package org.jlato.internal.td.stmt;

import org.jlato.internal.bu.name.SName;
import org.jlato.internal.bu.stmt.SLabeledStmt;
import org.jlato.internal.bu.stmt.SStmt;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.name.Name;
import org.jlato.tree.stmt.LabeledStmt;
import org.jlato.tree.stmt.Stmt;
import org.jlato.util.Mutation;

public class TDLabeledStmt extends TreeBase<SLabeledStmt, Stmt, LabeledStmt> implements LabeledStmt {

	public Kind kind() {
		return Kind.LabeledStmt;
	}

	public TDLabeledStmt(SLocation<SLabeledStmt> location) {
		super(location);
	}

	public TDLabeledStmt(Name label, Stmt stmt) {
		super(new SLocation<SLabeledStmt>(SLabeledStmt.make(TreeBase.<SName>treeOf(label), TreeBase.<SStmt>treeOf(stmt))));
	}

	public Name label() {
		return location.safeTraversal(SLabeledStmt.LABEL);
	}

	public LabeledStmt withLabel(Name label) {
		return location.safeTraversalReplace(SLabeledStmt.LABEL, label);
	}

	public LabeledStmt withLabel(Mutation<Name> mutation) {
		return location.safeTraversalMutate(SLabeledStmt.LABEL, mutation);
	}

	public Stmt stmt() {
		return location.safeTraversal(SLabeledStmt.STMT);
	}

	public LabeledStmt withStmt(Stmt stmt) {
		return location.safeTraversalReplace(SLabeledStmt.STMT, stmt);
	}

	public LabeledStmt withStmt(Mutation<Stmt> mutation) {
		return location.safeTraversalMutate(SLabeledStmt.STMT, mutation);
	}
}
