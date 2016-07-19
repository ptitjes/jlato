package org.jlato.tree.stmt;

import org.jlato.tree.TreeCombinators;
import org.jlato.tree.name.Name;
import org.jlato.util.Mutation;

/**
 * A labeled statement.
 */
public interface LabeledStmt extends Stmt, TreeCombinators<LabeledStmt> {

	/**
	 * Returns the label of this labeled statement.
	 *
	 * @return the label of this labeled statement.
	 */
	Name label();

	/**
	 * Replaces the label of this labeled statement.
	 *
	 * @param label the replacement for the label of this labeled statement.
	 * @return the resulting mutated labeled statement.
	 */
	LabeledStmt withLabel(Name label);

	/**
	 * Mutates the label of this labeled statement.
	 *
	 * @param mutation the mutation to apply to the label of this labeled statement.
	 * @return the resulting mutated labeled statement.
	 */
	LabeledStmt withLabel(Mutation<Name> mutation);

	/**
	 * Replaces the label of this labeled statement.
	 *
	 * @param label the replacement for the label of this labeled statement.
	 * @return the resulting mutated labeled statement.
	 */
	LabeledStmt withLabel(String label);

	/**
	 * Returns the statement of this labeled statement.
	 *
	 * @return the statement of this labeled statement.
	 */
	Stmt stmt();

	/**
	 * Replaces the statement of this labeled statement.
	 *
	 * @param stmt the replacement for the statement of this labeled statement.
	 * @return the resulting mutated labeled statement.
	 */
	LabeledStmt withStmt(Stmt stmt);

	/**
	 * Mutates the statement of this labeled statement.
	 *
	 * @param mutation the mutation to apply to the statement of this labeled statement.
	 * @return the resulting mutated labeled statement.
	 */
	LabeledStmt withStmt(Mutation<Stmt> mutation);
}
