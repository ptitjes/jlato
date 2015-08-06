package org.jlato.tree.decl;

import org.jlato.tree.Node;
import org.jlato.tree.NodeList;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

/**
 * A formal parameter.
 */
public interface FormalParameter extends Node, TreeCombinators<FormalParameter> {

	/**
	 * Returns the modifiers of this formal parameter.
	 *
	 * @return the modifiers of this formal parameter.
	 */
	NodeList<ExtendedModifier> modifiers();

	/**
	 * Replaces the modifiers of this formal parameter.
	 *
	 * @param modifiers the replacement for the modifiers of this formal parameter.
	 * @return the resulting mutated formal parameter.
	 */
	FormalParameter withModifiers(NodeList<ExtendedModifier> modifiers);

	/**
	 * Mutates the modifiers of this formal parameter.
	 *
	 * @param mutation the mutation to apply to the modifiers of this formal parameter.
	 * @return the resulting mutated formal parameter.
	 */
	FormalParameter withModifiers(Mutation<NodeList<ExtendedModifier>> mutation);

	/**
	 * Returns the type of this formal parameter.
	 *
	 * @return the type of this formal parameter.
	 */
	Type type();

	/**
	 * Replaces the type of this formal parameter.
	 *
	 * @param type the replacement for the type of this formal parameter.
	 * @return the resulting mutated formal parameter.
	 */
	FormalParameter withType(Type type);

	/**
	 * Mutates the type of this formal parameter.
	 *
	 * @param mutation the mutation to apply to the type of this formal parameter.
	 * @return the resulting mutated formal parameter.
	 */
	FormalParameter withType(Mutation<Type> mutation);

	/**
	 * Tests whether this formal parameter is a variadic parameter.
	 *
	 * @return <code>true</code> if this formal parameter is a variadic parameter, <code>false</code> otherwise.
	 */
	boolean isVarArgs();

	/**
	 * Sets whether this formal parameter is a variadic parameter.
	 *
	 * @param isVarArgs <code>true</code> if this formal parameter is a variadic parameter, <code>false</code> otherwise.
	 * @return the resulting mutated formal parameter.
	 */
	FormalParameter setVarArgs(boolean isVarArgs);

	/**
	 * Mutates whether this formal parameter is a variadic parameter.
	 *
	 * @param mutation the mutation to apply to whether this formal parameter is a variadic parameter.
	 * @return the resulting mutated formal parameter.
	 */
	FormalParameter setVarArgs(Mutation<Boolean> mutation);

	/**
	 * Returns the identifier of this formal parameter.
	 *
	 * @return the identifier of this formal parameter.
	 */
	VariableDeclaratorId id();

	/**
	 * Replaces the identifier of this formal parameter.
	 *
	 * @param id the replacement for the identifier of this formal parameter.
	 * @return the resulting mutated formal parameter.
	 */
	FormalParameter withId(VariableDeclaratorId id);

	/**
	 * Mutates the identifier of this formal parameter.
	 *
	 * @param mutation the mutation to apply to the identifier of this formal parameter.
	 * @return the resulting mutated formal parameter.
	 */
	FormalParameter withId(Mutation<VariableDeclaratorId> mutation);
}
