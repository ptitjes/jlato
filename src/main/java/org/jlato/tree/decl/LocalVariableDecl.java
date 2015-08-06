package org.jlato.tree.decl;

import org.jlato.tree.NodeList;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

/**
 * A local variable declaration.
 */
public interface LocalVariableDecl extends Decl, TreeCombinators<LocalVariableDecl> {

	/**
	 * Returns the modifiers of this local variable declaration.
	 *
	 * @return the modifiers of this local variable declaration.
	 */
	NodeList<ExtendedModifier> modifiers();

	/**
	 * Replaces the modifiers of this local variable declaration.
	 *
	 * @param modifiers the replacement for the modifiers of this local variable declaration.
	 * @return the resulting mutated local variable declaration.
	 */
	LocalVariableDecl withModifiers(NodeList<ExtendedModifier> modifiers);

	/**
	 * Mutates the modifiers of this local variable declaration.
	 *
	 * @param mutation the mutation to apply to the modifiers of this local variable declaration.
	 * @return the resulting mutated local variable declaration.
	 */
	LocalVariableDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation);

	/**
	 * Returns the type of this local variable declaration.
	 *
	 * @return the type of this local variable declaration.
	 */
	Type type();

	/**
	 * Replaces the type of this local variable declaration.
	 *
	 * @param type the replacement for the type of this local variable declaration.
	 * @return the resulting mutated local variable declaration.
	 */
	LocalVariableDecl withType(Type type);

	/**
	 * Mutates the type of this local variable declaration.
	 *
	 * @param mutation the mutation to apply to the type of this local variable declaration.
	 * @return the resulting mutated local variable declaration.
	 */
	LocalVariableDecl withType(Mutation<Type> mutation);

	/**
	 * Returns the variables of this local variable declaration.
	 *
	 * @return the variables of this local variable declaration.
	 */
	NodeList<VariableDeclarator> variables();

	/**
	 * Replaces the variables of this local variable declaration.
	 *
	 * @param variables the replacement for the variables of this local variable declaration.
	 * @return the resulting mutated local variable declaration.
	 */
	LocalVariableDecl withVariables(NodeList<VariableDeclarator> variables);

	/**
	 * Mutates the variables of this local variable declaration.
	 *
	 * @param mutation the mutation to apply to the variables of this local variable declaration.
	 * @return the resulting mutated local variable declaration.
	 */
	LocalVariableDecl withVariables(Mutation<NodeList<VariableDeclarator>> mutation);
}
