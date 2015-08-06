package org.jlato.tree.decl;

import org.jlato.tree.NodeList;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.QualifiedType;
import org.jlato.util.Mutation;

/**
 * An interface declaration.
 */
public interface InterfaceDecl extends TypeDecl, TreeCombinators<InterfaceDecl> {

	/**
	 * Returns the modifiers of this interface declaration.
	 *
	 * @return the modifiers of this interface declaration.
	 */
	NodeList<ExtendedModifier> modifiers();

	/**
	 * Replaces the modifiers of this interface declaration.
	 *
	 * @param modifiers the replacement for the modifiers of this interface declaration.
	 * @return the resulting mutated interface declaration.
	 */
	InterfaceDecl withModifiers(NodeList<ExtendedModifier> modifiers);

	/**
	 * Mutates the modifiers of this interface declaration.
	 *
	 * @param mutation the mutation to apply to the modifiers of this interface declaration.
	 * @return the resulting mutated interface declaration.
	 */
	InterfaceDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation);

	/**
	 * Returns the name of this interface declaration.
	 *
	 * @return the name of this interface declaration.
	 */
	Name name();

	/**
	 * Replaces the name of this interface declaration.
	 *
	 * @param name the replacement for the name of this interface declaration.
	 * @return the resulting mutated interface declaration.
	 */
	InterfaceDecl withName(Name name);

	/**
	 * Mutates the name of this interface declaration.
	 *
	 * @param mutation the mutation to apply to the name of this interface declaration.
	 * @return the resulting mutated interface declaration.
	 */
	InterfaceDecl withName(Mutation<Name> mutation);

	/**
	 * Returns the type parameters of this interface declaration.
	 *
	 * @return the type parameters of this interface declaration.
	 */
	NodeList<TypeParameter> typeParams();

	/**
	 * Replaces the type parameters of this interface declaration.
	 *
	 * @param typeParams the replacement for the type parameters of this interface declaration.
	 * @return the resulting mutated interface declaration.
	 */
	InterfaceDecl withTypeParams(NodeList<TypeParameter> typeParams);

	/**
	 * Mutates the type parameters of this interface declaration.
	 *
	 * @param mutation the mutation to apply to the type parameters of this interface declaration.
	 * @return the resulting mutated interface declaration.
	 */
	InterfaceDecl withTypeParams(Mutation<NodeList<TypeParameter>> mutation);

	/**
	 * Returns the 'extends' clause of this interface declaration.
	 *
	 * @return the 'extends' clause of this interface declaration.
	 */
	NodeList<QualifiedType> extendsClause();

	/**
	 * Replaces the 'extends' clause of this interface declaration.
	 *
	 * @param extendsClause the replacement for the 'extends' clause of this interface declaration.
	 * @return the resulting mutated interface declaration.
	 */
	InterfaceDecl withExtendsClause(NodeList<QualifiedType> extendsClause);

	/**
	 * Mutates the 'extends' clause of this interface declaration.
	 *
	 * @param mutation the mutation to apply to the 'extends' clause of this interface declaration.
	 * @return the resulting mutated interface declaration.
	 */
	InterfaceDecl withExtendsClause(Mutation<NodeList<QualifiedType>> mutation);

	/**
	 * Returns the members of this interface declaration.
	 *
	 * @return the members of this interface declaration.
	 */
	NodeList<MemberDecl> members();

	/**
	 * Replaces the members of this interface declaration.
	 *
	 * @param members the replacement for the members of this interface declaration.
	 * @return the resulting mutated interface declaration.
	 */
	InterfaceDecl withMembers(NodeList<MemberDecl> members);

	/**
	 * Mutates the members of this interface declaration.
	 *
	 * @param mutation the mutation to apply to the members of this interface declaration.
	 * @return the resulting mutated interface declaration.
	 */
	InterfaceDecl withMembers(Mutation<NodeList<MemberDecl>> mutation);
}
