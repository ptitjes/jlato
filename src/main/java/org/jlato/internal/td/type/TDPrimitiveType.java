package org.jlato.internal.td.type;

import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.type.SPrimitiveType;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.type.Primitive;
import org.jlato.tree.type.PrimitiveType;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

/**
 * A primitive type.
 */
public class TDPrimitiveType extends TDTree<SPrimitiveType, Type, PrimitiveType> implements PrimitiveType {

	/**
	 * Returns the kind of this primitive type.
	 *
	 * @return the kind of this primitive type.
	 */
	public Kind kind() {
		return Kind.PrimitiveType;
	}

	/**
	 * Creates a primitive type for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDPrimitiveType(TDLocation<SPrimitiveType> location) {
		super(location);
	}

	/**
	 * Creates a primitive type with the specified child trees.
	 *
	 * @param annotations the annotations child tree.
	 * @param primitive   the primitive child tree.
	 */
	public TDPrimitiveType(NodeList<AnnotationExpr> annotations, Primitive primitive) {
		super(new TDLocation<SPrimitiveType>(SPrimitiveType.make(TDTree.<SNodeList>treeOf(annotations), primitive)));
	}

	/**
	 * Returns the annotations of this primitive type.
	 *
	 * @return the annotations of this primitive type.
	 */
	public NodeList<AnnotationExpr> annotations() {
		return location.safeTraversal(SPrimitiveType.ANNOTATIONS);
	}

	/**
	 * Replaces the annotations of this primitive type.
	 *
	 * @param annotations the replacement for the annotations of this primitive type.
	 * @return the resulting mutated primitive type.
	 */
	public PrimitiveType withAnnotations(NodeList<AnnotationExpr> annotations) {
		return location.safeTraversalReplace(SPrimitiveType.ANNOTATIONS, annotations);
	}

	/**
	 * Mutates the annotations of this primitive type.
	 *
	 * @param mutation the mutation to apply to the annotations of this primitive type.
	 * @return the resulting mutated primitive type.
	 */
	public PrimitiveType withAnnotations(Mutation<NodeList<AnnotationExpr>> mutation) {
		return location.safeTraversalMutate(SPrimitiveType.ANNOTATIONS, mutation);
	}

	/**
	 * Returns the primitive of this primitive type.
	 *
	 * @return the primitive of this primitive type.
	 */
	public Primitive primitive() {
		return location.safeProperty(SPrimitiveType.PRIMITIVE);
	}

	/**
	 * Replaces the primitive of this primitive type.
	 *
	 * @param primitive the replacement for the primitive of this primitive type.
	 * @return the resulting mutated primitive type.
	 */
	public PrimitiveType withPrimitive(Primitive primitive) {
		return location.safePropertyReplace(SPrimitiveType.PRIMITIVE, primitive);
	}

	/**
	 * Mutates the primitive of this primitive type.
	 *
	 * @param mutation the mutation to apply to the primitive of this primitive type.
	 * @return the resulting mutated primitive type.
	 */
	public PrimitiveType withPrimitive(Mutation<Primitive> mutation) {
		return location.safePropertyMutate(SPrimitiveType.PRIMITIVE, mutation);
	}
}
