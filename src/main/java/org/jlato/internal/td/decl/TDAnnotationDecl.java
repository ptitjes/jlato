package org.jlato.internal.td.decl;

import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.decl.SAnnotationDecl;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.decl.AnnotationDecl;
import org.jlato.tree.decl.ExtendedModifier;
import org.jlato.tree.decl.MemberDecl;
import org.jlato.tree.decl.TypeDecl;
import org.jlato.tree.name.Name;
import org.jlato.util.Mutation;

public class TDAnnotationDecl extends TreeBase<SAnnotationDecl, TypeDecl, AnnotationDecl> implements AnnotationDecl {

	public Kind kind() {
		return Kind.AnnotationDecl;
	}

	public TDAnnotationDecl(SLocation<SAnnotationDecl> location) {
		super(location);
	}

	public TDAnnotationDecl(NodeList<ExtendedModifier> modifiers, Name name, NodeList<MemberDecl> members) {
		super(new SLocation<SAnnotationDecl>(SAnnotationDecl.make(TreeBase.<SNodeListState>treeOf(modifiers), TreeBase.<SName>treeOf(name), TreeBase.<SNodeListState>treeOf(members))));
	}

	public NodeList<ExtendedModifier> modifiers() {
		return location.safeTraversal(SAnnotationDecl.MODIFIERS);
	}

	public AnnotationDecl withModifiers(NodeList<ExtendedModifier> modifiers) {
		return location.safeTraversalReplace(SAnnotationDecl.MODIFIERS, modifiers);
	}

	public AnnotationDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation) {
		return location.safeTraversalMutate(SAnnotationDecl.MODIFIERS, mutation);
	}

	public Name name() {
		return location.safeTraversal(SAnnotationDecl.NAME);
	}

	public AnnotationDecl withName(Name name) {
		return location.safeTraversalReplace(SAnnotationDecl.NAME, name);
	}

	public AnnotationDecl withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(SAnnotationDecl.NAME, mutation);
	}

	public NodeList<MemberDecl> members() {
		return location.safeTraversal(SAnnotationDecl.MEMBERS);
	}

	public AnnotationDecl withMembers(NodeList<MemberDecl> members) {
		return location.safeTraversalReplace(SAnnotationDecl.MEMBERS, members);
	}

	public AnnotationDecl withMembers(Mutation<NodeList<MemberDecl>> mutation) {
		return location.safeTraversalMutate(SAnnotationDecl.MEMBERS, mutation);
	}
}
