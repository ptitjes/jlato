package org.jlato.internal.td.type;

import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.SNodeOptionState;
import org.jlato.internal.bu.type.SWildcardType;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.type.ReferenceType;
import org.jlato.tree.type.Type;
import org.jlato.tree.type.WildcardType;
import org.jlato.util.Mutation;

public class TDWildcardType extends TreeBase<SWildcardType, Type, WildcardType> implements WildcardType {

	public Kind kind() {
		return Kind.WildcardType;
	}

	public TDWildcardType(SLocation<SWildcardType> location) {
		super(location);
	}

	public TDWildcardType(NodeList<AnnotationExpr> annotations, NodeOption<ReferenceType> ext, NodeOption<ReferenceType> sup) {
		super(new SLocation<SWildcardType>(SWildcardType.make(TreeBase.<SNodeListState>treeOf(annotations), TreeBase.<SNodeOptionState>treeOf(ext), TreeBase.<SNodeOptionState>treeOf(sup))));
	}

	public NodeList<AnnotationExpr> annotations() {
		return location.safeTraversal(SWildcardType.ANNOTATIONS);
	}

	public WildcardType withAnnotations(NodeList<AnnotationExpr> annotations) {
		return location.safeTraversalReplace(SWildcardType.ANNOTATIONS, annotations);
	}

	public WildcardType withAnnotations(Mutation<NodeList<AnnotationExpr>> mutation) {
		return location.safeTraversalMutate(SWildcardType.ANNOTATIONS, mutation);
	}

	public NodeOption<ReferenceType> ext() {
		return location.safeTraversal(SWildcardType.EXT);
	}

	public WildcardType withExt(NodeOption<ReferenceType> ext) {
		return location.safeTraversalReplace(SWildcardType.EXT, ext);
	}

	public WildcardType withExt(Mutation<NodeOption<ReferenceType>> mutation) {
		return location.safeTraversalMutate(SWildcardType.EXT, mutation);
	}

	public NodeOption<ReferenceType> sup() {
		return location.safeTraversal(SWildcardType.SUP);
	}

	public WildcardType withSup(NodeOption<ReferenceType> sup) {
		return location.safeTraversalReplace(SWildcardType.SUP, sup);
	}

	public WildcardType withSup(Mutation<NodeOption<ReferenceType>> mutation) {
		return location.safeTraversalMutate(SWildcardType.SUP, mutation);
	}
}
