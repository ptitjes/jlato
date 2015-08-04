package org.jlato.tree.stmt;

import org.jlato.tree.NodeOption;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.name.Name;
import org.jlato.util.Mutation;

public interface ContinueStmt extends Stmt, TreeCombinators<ContinueStmt> {

	NodeOption<Name> id();

	ContinueStmt withId(NodeOption<Name> id);

	ContinueStmt withId(Mutation<NodeOption<Name>> mutation);
}
