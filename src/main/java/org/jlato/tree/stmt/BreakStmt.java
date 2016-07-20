package org.jlato.tree.stmt;

import org.jlato.tree.NodeOption;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.name.Name;
import org.jlato.util.Mutation;

/**
 * A 'break' statement.
 */
public interface BreakStmt extends Stmt, TreeCombinators<BreakStmt> {

	/**
	 * Returns the identifier of this 'break' statement.
	 *
	 * @return the identifier of this 'break' statement.
	 */
	NodeOption<Name> id();

	/**
	 * Replaces the identifier of this 'break' statement.
	 *
	 * @param id the replacement for the identifier of this 'break' statement.
	 * @return the resulting mutated 'break' statement.
	 */
	BreakStmt withId(NodeOption<Name> id);

	/**
	 * Mutates the identifier of this 'break' statement.
	 *
	 * @param mutation the mutation to apply to the identifier of this 'break' statement.
	 * @return the resulting mutated 'break' statement.
	 */
	BreakStmt withId(Mutation<NodeOption<Name>> mutation);

	/**
	 * Replaces the identifier of this 'break' statement.
	 *
	 * @param id the replacement for the identifier of this 'break' statement.
	 * @return the resulting mutated 'break' statement.
	 */
	BreakStmt withId(Name id);

	/**
	 * Replaces the identifier of this 'break' statement.
	 *
	 * @return the resulting mutated 'break' statement.
	 */
	BreakStmt withNoId();
}
