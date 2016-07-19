package org.jlato.tree.name;

import org.jlato.tree.Node;
import org.jlato.tree.NodeOption;
import org.jlato.tree.TreeCombinators;
import org.jlato.util.Mutation;

/**
 * A qualified name.
 */
public interface QualifiedName extends Node, TreeCombinators<QualifiedName> {

	/**
	 * Returns the qualifier of this qualified name.
	 *
	 * @return the qualifier of this qualified name.
	 */
	NodeOption<QualifiedName> qualifier();

	/**
	 * Replaces the qualifier of this qualified name.
	 *
	 * @param qualifier the replacement for the qualifier of this qualified name.
	 * @return the resulting mutated qualified name.
	 */
	QualifiedName withQualifier(NodeOption<QualifiedName> qualifier);

	/**
	 * Mutates the qualifier of this qualified name.
	 *
	 * @param mutation the mutation to apply to the qualifier of this qualified name.
	 * @return the resulting mutated qualified name.
	 */
	QualifiedName withQualifier(Mutation<NodeOption<QualifiedName>> mutation);

	/**
	 * Returns the name of this qualified name.
	 *
	 * @return the name of this qualified name.
	 */
	Name name();

	/**
	 * Replaces the name of this qualified name.
	 *
	 * @param name the replacement for the name of this qualified name.
	 * @return the resulting mutated qualified name.
	 */
	QualifiedName withName(Name name);

	/**
	 * Mutates the name of this qualified name.
	 *
	 * @param mutation the mutation to apply to the name of this qualified name.
	 * @return the resulting mutated qualified name.
	 */
	QualifiedName withName(Mutation<Name> mutation);

	/**
	 * Replaces the name of this qualified name.
	 *
	 * @param name the replacement for the name of this qualified name.
	 * @return the resulting mutated qualified name.
	 */
	QualifiedName withName(String name);
}
