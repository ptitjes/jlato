package org.jlato.tree.stmt;

import org.jlato.tree.NodeOption;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.name.Name;
import org.jlato.util.Mutation;

/**
 * A 'continue' statement.
 */
public interface ContinueStmt extends Stmt, TreeCombinators<ContinueStmt> {

	/**
	 * Returns the identifier of this 'continue' statement.
	 *
	 * @return the identifier of this 'continue' statement.
	 */
	NodeOption<Name> id();

	/**
	 * Replaces the identifier of this 'continue' statement.
	 *
	 * @param id the replacement for the identifier of this 'continue' statement.
	 * @return the resulting mutated 'continue' statement.
	 */
	ContinueStmt withId(NodeOption<Name> id);

	/**
	 * Mutates the identifier of this 'continue' statement.
	 *
	 * @param mutation the mutation to apply to the identifier of this 'continue' statement.
	 * @return the resulting mutated 'continue' statement.
	 */
	ContinueStmt withId(Mutation<NodeOption<Name>> mutation);

	/**
	 * Replaces the identifier of this 'continue' statement.
	 *
	 * @param id the replacement for the identifier of this 'continue' statement.
	 * @return the resulting mutated 'continue' statement.
	 */
	ContinueStmt withId(Name id);

	/**
	 * Replaces the identifier of this 'continue' statement.
	 *
	 * @return the resulting mutated 'continue' statement.
	 */
	ContinueStmt withNoId();
}
