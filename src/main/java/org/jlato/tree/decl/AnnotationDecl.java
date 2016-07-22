package org.jlato.tree.decl;

import org.jlato.tree.NodeList;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.name.Name;
import org.jlato.util.Mutation;

/**
 * An annotation type declaration.
 */
public interface AnnotationDecl extends TypeDecl, Documentable<AnnotationDecl>, TreeCombinators<AnnotationDecl> {

	/**
	 * Returns the modifiers of this annotation type declaration.
	 *
	 * @return the modifiers of this annotation type declaration.
	 */
	NodeList<ExtendedModifier> modifiers();

	/**
	 * Replaces the modifiers of this annotation type declaration.
	 *
	 * @param modifiers the replacement for the modifiers of this annotation type declaration.
	 * @return the resulting mutated annotation type declaration.
	 */
	AnnotationDecl withModifiers(NodeList<ExtendedModifier> modifiers);

	/**
	 * Mutates the modifiers of this annotation type declaration.
	 *
	 * @param mutation the mutation to apply to the modifiers of this annotation type declaration.
	 * @return the resulting mutated annotation type declaration.
	 */
	AnnotationDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation);

	/**
	 * Returns the name of this annotation type declaration.
	 *
	 * @return the name of this annotation type declaration.
	 */
	Name name();

	/**
	 * Replaces the name of this annotation type declaration.
	 *
	 * @param name the replacement for the name of this annotation type declaration.
	 * @return the resulting mutated annotation type declaration.
	 */
	AnnotationDecl withName(Name name);

	/**
	 * Mutates the name of this annotation type declaration.
	 *
	 * @param mutation the mutation to apply to the name of this annotation type declaration.
	 * @return the resulting mutated annotation type declaration.
	 */
	AnnotationDecl withName(Mutation<Name> mutation);

	/**
	 * Replaces the name of this annotation type declaration.
	 *
	 * @param name the replacement for the name of this annotation type declaration.
	 * @return the resulting mutated annotation type declaration.
	 */
	AnnotationDecl withName(String name);

	/**
	 * Returns the members of this annotation type declaration.
	 *
	 * @return the members of this annotation type declaration.
	 */
	NodeList<MemberDecl> members();

	/**
	 * Replaces the members of this annotation type declaration.
	 *
	 * @param members the replacement for the members of this annotation type declaration.
	 * @return the resulting mutated annotation type declaration.
	 */
	AnnotationDecl withMembers(NodeList<MemberDecl> members);

	/**
	 * Mutates the members of this annotation type declaration.
	 *
	 * @param mutation the mutation to apply to the members of this annotation type declaration.
	 * @return the resulting mutated annotation type declaration.
	 */
	AnnotationDecl withMembers(Mutation<NodeList<MemberDecl>> mutation);
}
