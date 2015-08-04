package org.jlato.internal.td.type;

import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.bu.type.SWildcardType;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.type.ReferenceType;
import org.jlato.tree.type.Type;
import org.jlato.tree.type.WildcardType;
import org.jlato.util.Mutation;

public class TDWildcardType extends TDTree<SWildcardType, Type, WildcardType> implements WildcardType {

	public Kind kind() {
		return Kind.WildcardType;
	}

	public TDWildcardType(TDLocation<SWildcardType> location) {
		super(location);
	}

	public TDWildcardType(NodeList<AnnotationExpr> annotations, NodeOption<ReferenceType> ext, NodeOption<ReferenceType> sup) {
		super(new TDLocation<SWildcardType>(SWildcardType.make(TDTree.<SNodeList>treeOf(annotations), TDTree.<SNodeOption>treeOf(ext), TDTree.<SNodeOption>treeOf(sup))));
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
