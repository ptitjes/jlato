package org.jlato.internal.td.decl;

import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.decl.STypeParameter;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.Node;
import org.jlato.tree.NodeList;
import org.jlato.tree.decl.TypeParameter;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

/**
 * A type parameter.
 */
public class TDTypeParameter extends TDTree<STypeParameter, Node, TypeParameter> implements TypeParameter {

	/**
	 * Returns the kind of this type parameter.
	 *
	 * @return the kind of this type parameter.
	 */
	public Kind kind() {
		return Kind.TypeParameter;
	}

	/**
	 * Creates a type parameter for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDTypeParameter(TDLocation<STypeParameter> location) {
		super(location);
	}

	/**
	 * Creates a type parameter with the specified child trees.
	 *
	 * @param annotations the annotations child tree.
	 * @param name        the name child tree.
	 * @param bounds      the bounds child tree.
	 */
	public TDTypeParameter(NodeList<AnnotationExpr> annotations, Name name, NodeList<Type> bounds) {
		super(new TDLocation<STypeParameter>(STypeParameter.make(TDTree.<SNodeList>treeOf(annotations), TDTree.<SName>treeOf(name), TDTree.<SNodeList>treeOf(bounds))));
	}

	/**
	 * Returns the annotations of this type parameter.
	 *
	 * @return the annotations of this type parameter.
	 */
	public NodeList<AnnotationExpr> annotations() {
		return location.safeTraversal(STypeParameter.ANNOTATIONS);
	}

	/**
	 * Replaces the annotations of this type parameter.
	 *
	 * @param annotations the replacement for the annotations of this type parameter.
	 * @return the resulting mutated type parameter.
	 */
	public TypeParameter withAnnotations(NodeList<AnnotationExpr> annotations) {
		return location.safeTraversalReplace(STypeParameter.ANNOTATIONS, annotations);
	}

	/**
	 * Mutates the annotations of this type parameter.
	 *
	 * @param mutation the mutation to apply to the annotations of this type parameter.
	 * @return the resulting mutated type parameter.
	 */
	public TypeParameter withAnnotations(Mutation<NodeList<AnnotationExpr>> mutation) {
		return location.safeTraversalMutate(STypeParameter.ANNOTATIONS, mutation);
	}

	/**
	 * Returns the name of this type parameter.
	 *
	 * @return the name of this type parameter.
	 */
	public Name name() {
		return location.safeTraversal(STypeParameter.NAME);
	}

	/**
	 * Replaces the name of this type parameter.
	 *
	 * @param name the replacement for the name of this type parameter.
	 * @return the resulting mutated type parameter.
	 */
	public TypeParameter withName(Name name) {
		return location.safeTraversalReplace(STypeParameter.NAME, name);
	}

	/**
	 * Mutates the name of this type parameter.
	 *
	 * @param mutation the mutation to apply to the name of this type parameter.
	 * @return the resulting mutated type parameter.
	 */
	public TypeParameter withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(STypeParameter.NAME, mutation);
	}

	/**
	 * Returns the bounds of this type parameter.
	 *
	 * @return the bounds of this type parameter.
	 */
	public NodeList<Type> bounds() {
		return location.safeTraversal(STypeParameter.BOUNDS);
	}

	/**
	 * Replaces the bounds of this type parameter.
	 *
	 * @param bounds the replacement for the bounds of this type parameter.
	 * @return the resulting mutated type parameter.
	 */
	public TypeParameter withBounds(NodeList<Type> bounds) {
		return location.safeTraversalReplace(STypeParameter.BOUNDS, bounds);
	}

	/**
	 * Mutates the bounds of this type parameter.
	 *
	 * @param mutation the mutation to apply to the bounds of this type parameter.
	 * @return the resulting mutated type parameter.
	 */
	public TypeParameter withBounds(Mutation<NodeList<Type>> mutation) {
		return location.safeTraversalMutate(STypeParameter.BOUNDS, mutation);
	}
}
