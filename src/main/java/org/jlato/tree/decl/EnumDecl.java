package org.jlato.tree.decl;

import org.jlato.tree.NodeList;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.QualifiedType;
import org.jlato.util.Mutation;

/**
 * An enum declaration.
 */
public interface EnumDecl extends TypeDecl, Documentable<EnumDecl>, TreeCombinators<EnumDecl> {

	/**
	 * Returns the modifiers of this enum declaration.
	 *
	 * @return the modifiers of this enum declaration.
	 */
	NodeList<ExtendedModifier> modifiers();

	/**
	 * Replaces the modifiers of this enum declaration.
	 *
	 * @param modifiers the replacement for the modifiers of this enum declaration.
	 * @return the resulting mutated enum declaration.
	 */
	EnumDecl withModifiers(NodeList<ExtendedModifier> modifiers);

	/**
	 * Mutates the modifiers of this enum declaration.
	 *
	 * @param mutation the mutation to apply to the modifiers of this enum declaration.
	 * @return the resulting mutated enum declaration.
	 */
	EnumDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation);

	/**
	 * Returns the name of this enum declaration.
	 *
	 * @return the name of this enum declaration.
	 */
	Name name();

	/**
	 * Replaces the name of this enum declaration.
	 *
	 * @param name the replacement for the name of this enum declaration.
	 * @return the resulting mutated enum declaration.
	 */
	EnumDecl withName(Name name);

	/**
	 * Mutates the name of this enum declaration.
	 *
	 * @param mutation the mutation to apply to the name of this enum declaration.
	 * @return the resulting mutated enum declaration.
	 */
	EnumDecl withName(Mutation<Name> mutation);

	/**
	 * Replaces the name of this enum declaration.
	 *
	 * @param name the replacement for the name of this enum declaration.
	 * @return the resulting mutated enum declaration.
	 */
	EnumDecl withName(String name);

	/**
	 * Returns the 'implements' clause of this enum declaration.
	 *
	 * @return the 'implements' clause of this enum declaration.
	 */
	NodeList<QualifiedType> implementsClause();

	/**
	 * Replaces the 'implements' clause of this enum declaration.
	 *
	 * @param implementsClause the replacement for the 'implements' clause of this enum declaration.
	 * @return the resulting mutated enum declaration.
	 */
	EnumDecl withImplementsClause(NodeList<QualifiedType> implementsClause);

	/**
	 * Mutates the 'implements' clause of this enum declaration.
	 *
	 * @param mutation the mutation to apply to the 'implements' clause of this enum declaration.
	 * @return the resulting mutated enum declaration.
	 */
	EnumDecl withImplementsClause(Mutation<NodeList<QualifiedType>> mutation);

	/**
	 * Returns the enum constants of this enum declaration.
	 *
	 * @return the enum constants of this enum declaration.
	 */
	NodeList<EnumConstantDecl> enumConstants();

	/**
	 * Replaces the enum constants of this enum declaration.
	 *
	 * @param enumConstants the replacement for the enum constants of this enum declaration.
	 * @return the resulting mutated enum declaration.
	 */
	EnumDecl withEnumConstants(NodeList<EnumConstantDecl> enumConstants);

	/**
	 * Mutates the enum constants of this enum declaration.
	 *
	 * @param mutation the mutation to apply to the enum constants of this enum declaration.
	 * @return the resulting mutated enum declaration.
	 */
	EnumDecl withEnumConstants(Mutation<NodeList<EnumConstantDecl>> mutation);

	/**
	 * Tests whether this enum declaration has a trailing comma.
	 *
	 * @return <code>true</code> if this enum declaration has a trailing comma, <code>false</code> otherwise.
	 */
	boolean trailingComma();

	/**
	 * Sets whether this enum declaration has a trailing comma.
	 *
	 * @param trailingComma <code>true</code> if this enum declaration has a trailing comma, <code>false</code> otherwise.
	 * @return the resulting mutated enum declaration.
	 */
	EnumDecl withTrailingComma(boolean trailingComma);

	/**
	 * Mutates whether this enum declaration has a trailing comma.
	 *
	 * @param mutation the mutation to apply to whether this enum declaration has a trailing comma.
	 * @return the resulting mutated enum declaration.
	 */
	EnumDecl withTrailingComma(Mutation<Boolean> mutation);

	/**
	 * Returns the members of this enum declaration.
	 *
	 * @return the members of this enum declaration.
	 */
	NodeList<MemberDecl> members();

	/**
	 * Replaces the members of this enum declaration.
	 *
	 * @param members the replacement for the members of this enum declaration.
	 * @return the resulting mutated enum declaration.
	 */
	EnumDecl withMembers(NodeList<MemberDecl> members);

	/**
	 * Mutates the members of this enum declaration.
	 *
	 * @param mutation the mutation to apply to the members of this enum declaration.
	 * @return the resulting mutated enum declaration.
	 */
	EnumDecl withMembers(Mutation<NodeList<MemberDecl>> mutation);
}
