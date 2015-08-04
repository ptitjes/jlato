package org.jlato.internal.td.decl;

import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.bu.decl.SAnnotationMemberDecl;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.bu.type.SType;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.decl.AnnotationMemberDecl;
import org.jlato.tree.decl.ArrayDim;
import org.jlato.tree.decl.ExtendedModifier;
import org.jlato.tree.decl.MemberDecl;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

public class TDAnnotationMemberDecl extends TDTree<SAnnotationMemberDecl, MemberDecl, AnnotationMemberDecl> implements AnnotationMemberDecl {

	public Kind kind() {
		return Kind.AnnotationMemberDecl;
	}

	public TDAnnotationMemberDecl(TDLocation<SAnnotationMemberDecl> location) {
		super(location);
	}

	public TDAnnotationMemberDecl(NodeList<ExtendedModifier> modifiers, Type type, Name name, NodeList<ArrayDim> dims, NodeOption<Expr> defaultValue) {
		super(new TDLocation<SAnnotationMemberDecl>(SAnnotationMemberDecl.make(TDTree.<SNodeList>treeOf(modifiers), TDTree.<SType>treeOf(type), TDTree.<SName>treeOf(name), TDTree.<SNodeList>treeOf(dims), TDTree.<SNodeOption>treeOf(defaultValue))));
	}

	public NodeList<ExtendedModifier> modifiers() {
		return location.safeTraversal(SAnnotationMemberDecl.MODIFIERS);
	}

	public AnnotationMemberDecl withModifiers(NodeList<ExtendedModifier> modifiers) {
		return location.safeTraversalReplace(SAnnotationMemberDecl.MODIFIERS, modifiers);
	}

	public AnnotationMemberDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation) {
		return location.safeTraversalMutate(SAnnotationMemberDecl.MODIFIERS, mutation);
	}

	public Type type() {
		return location.safeTraversal(SAnnotationMemberDecl.TYPE);
	}

	public AnnotationMemberDecl withType(Type type) {
		return location.safeTraversalReplace(SAnnotationMemberDecl.TYPE, type);
	}

	public AnnotationMemberDecl withType(Mutation<Type> mutation) {
		return location.safeTraversalMutate(SAnnotationMemberDecl.TYPE, mutation);
	}

	public Name name() {
		return location.safeTraversal(SAnnotationMemberDecl.NAME);
	}

	public AnnotationMemberDecl withName(Name name) {
		return location.safeTraversalReplace(SAnnotationMemberDecl.NAME, name);
	}

	public AnnotationMemberDecl withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(SAnnotationMemberDecl.NAME, mutation);
	}

	public NodeList<ArrayDim> dims() {
		return location.safeTraversal(SAnnotationMemberDecl.DIMS);
	}

	public AnnotationMemberDecl withDims(NodeList<ArrayDim> dims) {
		return location.safeTraversalReplace(SAnnotationMemberDecl.DIMS, dims);
	}

	public AnnotationMemberDecl withDims(Mutation<NodeList<ArrayDim>> mutation) {
		return location.safeTraversalMutate(SAnnotationMemberDecl.DIMS, mutation);
	}

	public NodeOption<Expr> defaultValue() {
		return location.safeTraversal(SAnnotationMemberDecl.DEFAULT_VALUE);
	}

	public AnnotationMemberDecl withDefaultValue(NodeOption<Expr> defaultValue) {
		return location.safeTraversalReplace(SAnnotationMemberDecl.DEFAULT_VALUE, defaultValue);
	}

	public AnnotationMemberDecl withDefaultValue(Mutation<NodeOption<Expr>> mutation) {
		return location.safeTraversalMutate(SAnnotationMemberDecl.DEFAULT_VALUE, mutation);
	}
}
