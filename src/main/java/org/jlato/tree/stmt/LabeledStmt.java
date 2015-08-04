package org.jlato.tree.stmt;

import org.jlato.tree.TreeCombinators;
import org.jlato.tree.name.Name;
import org.jlato.util.Mutation;

public interface LabeledStmt extends Stmt, TreeCombinators<LabeledStmt> {

	Name label();

	LabeledStmt withLabel(Name label);

	LabeledStmt withLabel(Mutation<Name> mutation);

	Stmt stmt();

	LabeledStmt withStmt(Stmt stmt);

	LabeledStmt withStmt(Mutation<Stmt> mutation);
}
