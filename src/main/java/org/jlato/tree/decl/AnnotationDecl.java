package org.jlato.tree.decl;

import org.jlato.tree.NodeList;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.name.Name;
import org.jlato.util.Mutation;

public interface AnnotationDecl extends TypeDecl, TreeCombinators<AnnotationDecl> {

	NodeList<ExtendedModifier> modifiers();

	AnnotationDecl withModifiers(NodeList<ExtendedModifier> modifiers);

	AnnotationDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation);

	Name name();

	AnnotationDecl withName(Name name);

	AnnotationDecl withName(Mutation<Name> mutation);

	NodeList<MemberDecl> members();

	AnnotationDecl withMembers(NodeList<MemberDecl> members);

	AnnotationDecl withMembers(Mutation<NodeList<MemberDecl>> mutation);
}
