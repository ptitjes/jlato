package org.jlato.tree.stmt;

import org.jlato.tree.NodeOption;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.name.Name;
import org.jlato.util.Mutation;

public interface BreakStmt extends Stmt, TreeCombinators<BreakStmt> {

	NodeOption<Name> id();

	BreakStmt withId(NodeOption<Name> id);

	BreakStmt withId(Mutation<NodeOption<Name>> mutation);
}
