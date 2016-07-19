package org.jlato.tree.type;

import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.name.Name;
import org.jlato.util.Mutation;

/**
 * A qualified type.
 */
public interface QualifiedType extends ReferenceType, TreeCombinators<QualifiedType> {

	/**
	 * Returns the annotations of this qualified type.
	 *
	 * @return the annotations of this qualified type.
	 */
	NodeList<AnnotationExpr> annotations();

	/**
	 * Replaces the annotations of this qualified type.
	 *
	 * @param annotations the replacement for the annotations of this qualified type.
	 * @return the resulting mutated qualified type.
	 */
	QualifiedType withAnnotations(NodeList<AnnotationExpr> annotations);

	/**
	 * Mutates the annotations of this qualified type.
	 *
	 * @param mutation the mutation to apply to the annotations of this qualified type.
	 * @return the resulting mutated qualified type.
	 */
	QualifiedType withAnnotations(Mutation<NodeList<AnnotationExpr>> mutation);

	/**
	 * Returns the scope of this qualified type.
	 *
	 * @return the scope of this qualified type.
	 */
	NodeOption<QualifiedType> scope();

	/**
	 * Replaces the scope of this qualified type.
	 *
	 * @param scope the replacement for the scope of this qualified type.
	 * @return the resulting mutated qualified type.
	 */
	QualifiedType withScope(NodeOption<QualifiedType> scope);

	/**
	 * Mutates the scope of this qualified type.
	 *
	 * @param mutation the mutation to apply to the scope of this qualified type.
	 * @return the resulting mutated qualified type.
	 */
	QualifiedType withScope(Mutation<NodeOption<QualifiedType>> mutation);

	/**
	 * Returns the name of this qualified type.
	 *
	 * @return the name of this qualified type.
	 */
	Name name();

	/**
	 * Replaces the name of this qualified type.
	 *
	 * @param name the replacement for the name of this qualified type.
	 * @return the resulting mutated qualified type.
	 */
	QualifiedType withName(Name name);

	/**
	 * Mutates the name of this qualified type.
	 *
	 * @param mutation the mutation to apply to the name of this qualified type.
	 * @return the resulting mutated qualified type.
	 */
	QualifiedType withName(Mutation<Name> mutation);

	/**
	 * Replaces the name of this qualified type.
	 *
	 * @param name the replacement for the name of this qualified type.
	 * @return the resulting mutated qualified type.
	 */
	QualifiedType withName(String name);

	/**
	 * Returns the type args of this qualified type.
	 *
	 * @return the type args of this qualified type.
	 */
	NodeOption<NodeList<Type>> typeArgs();

	/**
	 * Replaces the type args of this qualified type.
	 *
	 * @param typeArgs the replacement for the type args of this qualified type.
	 * @return the resulting mutated qualified type.
	 */
	QualifiedType withTypeArgs(NodeOption<NodeList<Type>> typeArgs);

	/**
	 * Mutates the type args of this qualified type.
	 *
	 * @param mutation the mutation to apply to the type args of this qualified type.
	 * @return the resulting mutated qualified type.
	 */
	QualifiedType withTypeArgs(Mutation<NodeOption<NodeList<Type>>> mutation);
}
