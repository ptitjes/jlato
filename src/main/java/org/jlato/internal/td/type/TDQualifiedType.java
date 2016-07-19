package org.jlato.internal.td.type;

import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.bu.type.SQualifiedType;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Trees;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.QualifiedType;
import org.jlato.tree.type.ReferenceType;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

/**
 * A qualified type.
 */
public class TDQualifiedType extends TDTree<SQualifiedType, ReferenceType, QualifiedType> implements QualifiedType {

	/**
	 * Returns the kind of this qualified type.
	 *
	 * @return the kind of this qualified type.
	 */
	public Kind kind() {
		return Kind.QualifiedType;
	}

	/**
	 * Creates a qualified type for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDQualifiedType(TDLocation<SQualifiedType> location) {
		super(location);
	}

	/**
	 * Creates a qualified type with the specified child trees.
	 *
	 * @param annotations the annotations child tree.
	 * @param scope       the scope child tree.
	 * @param name        the name child tree.
	 * @param typeArgs    the type args child tree.
	 */
	public TDQualifiedType(NodeList<AnnotationExpr> annotations, NodeOption<QualifiedType> scope, Name name, NodeOption<NodeList<Type>> typeArgs) {
		super(new TDLocation<SQualifiedType>(SQualifiedType.make(TDTree.<SNodeList>treeOf(annotations), TDTree.<SNodeOption>treeOf(scope), TDTree.<SName>treeOf(name), TDTree.<SNodeOption>treeOf(typeArgs))));
	}

	/**
	 * Returns the annotations of this qualified type.
	 *
	 * @return the annotations of this qualified type.
	 */
	public NodeList<AnnotationExpr> annotations() {
		return location.safeTraversal(SQualifiedType.ANNOTATIONS);
	}

	/**
	 * Replaces the annotations of this qualified type.
	 *
	 * @param annotations the replacement for the annotations of this qualified type.
	 * @return the resulting mutated qualified type.
	 */
	public QualifiedType withAnnotations(NodeList<AnnotationExpr> annotations) {
		return location.safeTraversalReplace(SQualifiedType.ANNOTATIONS, annotations);
	}

	/**
	 * Mutates the annotations of this qualified type.
	 *
	 * @param mutation the mutation to apply to the annotations of this qualified type.
	 * @return the resulting mutated qualified type.
	 */
	public QualifiedType withAnnotations(Mutation<NodeList<AnnotationExpr>> mutation) {
		return location.safeTraversalMutate(SQualifiedType.ANNOTATIONS, mutation);
	}

	/**
	 * Returns the scope of this qualified type.
	 *
	 * @return the scope of this qualified type.
	 */
	public NodeOption<QualifiedType> scope() {
		return location.safeTraversal(SQualifiedType.SCOPE);
	}

	/**
	 * Replaces the scope of this qualified type.
	 *
	 * @param scope the replacement for the scope of this qualified type.
	 * @return the resulting mutated qualified type.
	 */
	public QualifiedType withScope(NodeOption<QualifiedType> scope) {
		return location.safeTraversalReplace(SQualifiedType.SCOPE, scope);
	}

	/**
	 * Mutates the scope of this qualified type.
	 *
	 * @param mutation the mutation to apply to the scope of this qualified type.
	 * @return the resulting mutated qualified type.
	 */
	public QualifiedType withScope(Mutation<NodeOption<QualifiedType>> mutation) {
		return location.safeTraversalMutate(SQualifiedType.SCOPE, mutation);
	}

	/**
	 * Returns the name of this qualified type.
	 *
	 * @return the name of this qualified type.
	 */
	public Name name() {
		return location.safeTraversal(SQualifiedType.NAME);
	}

	/**
	 * Replaces the name of this qualified type.
	 *
	 * @param name the replacement for the name of this qualified type.
	 * @return the resulting mutated qualified type.
	 */
	public QualifiedType withName(Name name) {
		return location.safeTraversalReplace(SQualifiedType.NAME, name);
	}

	/**
	 * Mutates the name of this qualified type.
	 *
	 * @param mutation the mutation to apply to the name of this qualified type.
	 * @return the resulting mutated qualified type.
	 */
	public QualifiedType withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(SQualifiedType.NAME, mutation);
	}

	/**
	 * Replaces the name of this qualified type.
	 *
	 * @param name the replacement for the name of this qualified type.
	 * @return the resulting mutated qualified type.
	 */
	public QualifiedType withName(String name) {
		return location.safeTraversalReplace(SQualifiedType.NAME, Trees.name(name));
	}

	/**
	 * Returns the type args of this qualified type.
	 *
	 * @return the type args of this qualified type.
	 */
	public NodeOption<NodeList<Type>> typeArgs() {
		return location.safeTraversal(SQualifiedType.TYPE_ARGS);
	}

	/**
	 * Replaces the type args of this qualified type.
	 *
	 * @param typeArgs the replacement for the type args of this qualified type.
	 * @return the resulting mutated qualified type.
	 */
	public QualifiedType withTypeArgs(NodeOption<NodeList<Type>> typeArgs) {
		return location.safeTraversalReplace(SQualifiedType.TYPE_ARGS, typeArgs);
	}

	/**
	 * Mutates the type args of this qualified type.
	 *
	 * @param mutation the mutation to apply to the type args of this qualified type.
	 * @return the resulting mutated qualified type.
	 */
	public QualifiedType withTypeArgs(Mutation<NodeOption<NodeList<Type>>> mutation) {
		return location.safeTraversalMutate(SQualifiedType.TYPE_ARGS, mutation);
	}
}
