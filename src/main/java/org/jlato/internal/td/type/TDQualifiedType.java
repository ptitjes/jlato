package org.jlato.internal.td.type;

import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.SNodeOptionState;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.bu.type.SQualifiedType;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.QualifiedType;
import org.jlato.tree.type.ReferenceType;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

public class TDQualifiedType extends TDTree<SQualifiedType, ReferenceType, QualifiedType> implements QualifiedType {

	public Kind kind() {
		return Kind.QualifiedType;
	}

	public TDQualifiedType(SLocation<SQualifiedType> location) {
		super(location);
	}

	public TDQualifiedType(NodeList<AnnotationExpr> annotations, NodeOption<QualifiedType> scope, Name name, NodeOption<NodeList<Type>> typeArgs) {
		super(new SLocation<SQualifiedType>(SQualifiedType.make(TDTree.<SNodeListState>treeOf(annotations), TDTree.<SNodeOptionState>treeOf(scope), TDTree.<SName>treeOf(name), TDTree.<SNodeOptionState>treeOf(typeArgs))));
	}

	public NodeList<AnnotationExpr> annotations() {
		return location.safeTraversal(SQualifiedType.ANNOTATIONS);
	}

	public QualifiedType withAnnotations(NodeList<AnnotationExpr> annotations) {
		return location.safeTraversalReplace(SQualifiedType.ANNOTATIONS, annotations);
	}

	public QualifiedType withAnnotations(Mutation<NodeList<AnnotationExpr>> mutation) {
		return location.safeTraversalMutate(SQualifiedType.ANNOTATIONS, mutation);
	}

	public NodeOption<QualifiedType> scope() {
		return location.safeTraversal(SQualifiedType.SCOPE);
	}

	public QualifiedType withScope(NodeOption<QualifiedType> scope) {
		return location.safeTraversalReplace(SQualifiedType.SCOPE, scope);
	}

	public QualifiedType withScope(Mutation<NodeOption<QualifiedType>> mutation) {
		return location.safeTraversalMutate(SQualifiedType.SCOPE, mutation);
	}

	public Name name() {
		return location.safeTraversal(SQualifiedType.NAME);
	}

	public QualifiedType withName(Name name) {
		return location.safeTraversalReplace(SQualifiedType.NAME, name);
	}

	public QualifiedType withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(SQualifiedType.NAME, mutation);
	}

	public NodeOption<NodeList<Type>> typeArgs() {
		return location.safeTraversal(SQualifiedType.TYPE_ARGS);
	}

	public QualifiedType withTypeArgs(NodeOption<NodeList<Type>> typeArgs) {
		return location.safeTraversalReplace(SQualifiedType.TYPE_ARGS, typeArgs);
	}

	public QualifiedType withTypeArgs(Mutation<NodeOption<NodeList<Type>>> mutation) {
		return location.safeTraversalMutate(SQualifiedType.TYPE_ARGS, mutation);
	}
}
