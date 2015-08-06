package org.jlato.tree.type;

import org.jlato.tree.NodeList;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.util.Mutation;

/**
 * A primitive type.
 */
public interface PrimitiveType extends Type, TreeCombinators<PrimitiveType> {

	/**
	 * Returns the annotations of this primitive type.
	 *
	 * @return the annotations of this primitive type.
	 */
	NodeList<AnnotationExpr> annotations();

	/**
	 * Replaces the annotations of this primitive type.
	 *
	 * @param annotations the replacement for the annotations of this primitive type.
	 * @return the resulting mutated primitive type.
	 */
	PrimitiveType withAnnotations(NodeList<AnnotationExpr> annotations);

	/**
	 * Mutates the annotations of this primitive type.
	 *
	 * @param mutation the mutation to apply to the annotations of this primitive type.
	 * @return the resulting mutated primitive type.
	 */
	PrimitiveType withAnnotations(Mutation<NodeList<AnnotationExpr>> mutation);

	/**
	 * Returns the primitive of this primitive type.
	 *
	 * @return the primitive of this primitive type.
	 */
	Primitive primitive();

	/**
	 * Replaces the primitive of this primitive type.
	 *
	 * @param primitive the replacement for the primitive of this primitive type.
	 * @return the resulting mutated primitive type.
	 */
	PrimitiveType withPrimitive(Primitive primitive);

	/**
	 * Mutates the primitive of this primitive type.
	 *
	 * @param mutation the mutation to apply to the primitive of this primitive type.
	 * @return the resulting mutated primitive type.
	 */
	PrimitiveType withPrimitive(Mutation<Primitive> mutation);
}
