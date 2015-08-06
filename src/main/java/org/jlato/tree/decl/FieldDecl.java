package org.jlato.tree.decl;

import org.jlato.tree.NodeList;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

/**
 * A field declaration.
 */
public interface FieldDecl extends MemberDecl, TreeCombinators<FieldDecl> {

	/**
	 * Returns the modifiers of this field declaration.
	 *
	 * @return the modifiers of this field declaration.
	 */
	NodeList<ExtendedModifier> modifiers();

	/**
	 * Replaces the modifiers of this field declaration.
	 *
	 * @param modifiers the replacement for the modifiers of this field declaration.
	 * @return the resulting mutated field declaration.
	 */
	FieldDecl withModifiers(NodeList<ExtendedModifier> modifiers);

	/**
	 * Mutates the modifiers of this field declaration.
	 *
	 * @param mutation the mutation to apply to the modifiers of this field declaration.
	 * @return the resulting mutated field declaration.
	 */
	FieldDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation);

	/**
	 * Returns the type of this field declaration.
	 *
	 * @return the type of this field declaration.
	 */
	Type type();

	/**
	 * Replaces the type of this field declaration.
	 *
	 * @param type the replacement for the type of this field declaration.
	 * @return the resulting mutated field declaration.
	 */
	FieldDecl withType(Type type);

	/**
	 * Mutates the type of this field declaration.
	 *
	 * @param mutation the mutation to apply to the type of this field declaration.
	 * @return the resulting mutated field declaration.
	 */
	FieldDecl withType(Mutation<Type> mutation);

	/**
	 * Returns the variables of this field declaration.
	 *
	 * @return the variables of this field declaration.
	 */
	NodeList<VariableDeclarator> variables();

	/**
	 * Replaces the variables of this field declaration.
	 *
	 * @param variables the replacement for the variables of this field declaration.
	 * @return the resulting mutated field declaration.
	 */
	FieldDecl withVariables(NodeList<VariableDeclarator> variables);

	/**
	 * Mutates the variables of this field declaration.
	 *
	 * @param mutation the mutation to apply to the variables of this field declaration.
	 * @return the resulting mutated field declaration.
	 */
	FieldDecl withVariables(Mutation<NodeList<VariableDeclarator>> mutation);
}
