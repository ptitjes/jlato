package org.jlato.tree.decl;

import org.jlato.tree.Node;
import org.jlato.tree.NodeList;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

/**
 * A type parameter.
 */
public interface TypeParameter extends Node, TreeCombinators<TypeParameter> {

	/**
	 * Returns the annotations of this type parameter.
	 *
	 * @return the annotations of this type parameter.
	 */
	NodeList<AnnotationExpr> annotations();

	/**
	 * Replaces the annotations of this type parameter.
	 *
	 * @param annotations the replacement for the annotations of this type parameter.
	 * @return the resulting mutated type parameter.
	 */
	TypeParameter withAnnotations(NodeList<AnnotationExpr> annotations);

	/**
	 * Mutates the annotations of this type parameter.
	 *
	 * @param mutation the mutation to apply to the annotations of this type parameter.
	 * @return the resulting mutated type parameter.
	 */
	TypeParameter withAnnotations(Mutation<NodeList<AnnotationExpr>> mutation);

	/**
	 * Returns the name of this type parameter.
	 *
	 * @return the name of this type parameter.
	 */
	Name name();

	/**
	 * Replaces the name of this type parameter.
	 *
	 * @param name the replacement for the name of this type parameter.
	 * @return the resulting mutated type parameter.
	 */
	TypeParameter withName(Name name);

	/**
	 * Mutates the name of this type parameter.
	 *
	 * @param mutation the mutation to apply to the name of this type parameter.
	 * @return the resulting mutated type parameter.
	 */
	TypeParameter withName(Mutation<Name> mutation);

	/**
	 * Returns the bounds of this type parameter.
	 *
	 * @return the bounds of this type parameter.
	 */
	NodeList<Type> bounds();

	/**
	 * Replaces the bounds of this type parameter.
	 *
	 * @param bounds the replacement for the bounds of this type parameter.
	 * @return the resulting mutated type parameter.
	 */
	TypeParameter withBounds(NodeList<Type> bounds);

	/**
	 * Mutates the bounds of this type parameter.
	 *
	 * @param mutation the mutation to apply to the bounds of this type parameter.
	 * @return the resulting mutated type parameter.
	 */
	TypeParameter withBounds(Mutation<NodeList<Type>> mutation);
}
