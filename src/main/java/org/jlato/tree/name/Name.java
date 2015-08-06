package org.jlato.tree.name;

import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.Expr;
import org.jlato.util.Mutation;

/**
 * A name.
 */
public interface Name extends Expr, TreeCombinators<Name> {

	/**
	 * Returns the identifier of this name.
	 *
	 * @return the identifier of this name.
	 */
	String id();

	/**
	 * Replaces the identifier of this name.
	 *
	 * @param id the replacement for the identifier of this name.
	 * @return the resulting mutated name.
	 */
	Name withId(String id);

	/**
	 * Mutates the identifier of this name.
	 *
	 * @param mutation the mutation to apply to the identifier of this name.
	 * @return the resulting mutated name.
	 */
	Name withId(Mutation<String> mutation);
}
