package org.jlato.internal.td.decl;

import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.decl.STypeParameter;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.TypeParameter;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

public class TDTypeParameter extends TreeBase<STypeParameter, Tree, TypeParameter> implements TypeParameter {

	public Kind kind() {
		return Kind.TypeParameter;
	}

	public TDTypeParameter(SLocation<STypeParameter> location) {
		super(location);
	}

	public TDTypeParameter(NodeList<AnnotationExpr> annotations, Name name, NodeList<Type> bounds) {
		super(new SLocation<STypeParameter>(STypeParameter.make(TreeBase.<SNodeListState>treeOf(annotations), TreeBase.<SName>treeOf(name), TreeBase.<SNodeListState>treeOf(bounds))));
	}

	public NodeList<AnnotationExpr> annotations() {
		return location.safeTraversal(STypeParameter.ANNOTATIONS);
	}

	public TypeParameter withAnnotations(NodeList<AnnotationExpr> annotations) {
		return location.safeTraversalReplace(STypeParameter.ANNOTATIONS, annotations);
	}

	public TypeParameter withAnnotations(Mutation<NodeList<AnnotationExpr>> mutation) {
		return location.safeTraversalMutate(STypeParameter.ANNOTATIONS, mutation);
	}

	public Name name() {
		return location.safeTraversal(STypeParameter.NAME);
	}

	public TypeParameter withName(Name name) {
		return location.safeTraversalReplace(STypeParameter.NAME, name);
	}

	public TypeParameter withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(STypeParameter.NAME, mutation);
	}

	public NodeList<Type> bounds() {
		return location.safeTraversal(STypeParameter.BOUNDS);
	}

	public TypeParameter withBounds(NodeList<Type> bounds) {
		return location.safeTraversalReplace(STypeParameter.BOUNDS, bounds);
	}

	public TypeParameter withBounds(Mutation<NodeList<Type>> mutation) {
		return location.safeTraversalMutate(STypeParameter.BOUNDS, mutation);
	}
}
