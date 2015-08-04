package org.jlato.tree.decl;

import org.jlato.tree.NodeList;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.QualifiedType;
import org.jlato.util.Mutation;

public interface InterfaceDecl extends TypeDecl, TreeCombinators<InterfaceDecl> {

	NodeList<ExtendedModifier> modifiers();

	InterfaceDecl withModifiers(NodeList<ExtendedModifier> modifiers);

	InterfaceDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation);

	Name name();

	InterfaceDecl withName(Name name);

	InterfaceDecl withName(Mutation<Name> mutation);

	NodeList<TypeParameter> typeParams();

	InterfaceDecl withTypeParams(NodeList<TypeParameter> typeParams);

	InterfaceDecl withTypeParams(Mutation<NodeList<TypeParameter>> mutation);

	NodeList<QualifiedType> extendsClause();

	InterfaceDecl withExtendsClause(NodeList<QualifiedType> extendsClause);

	InterfaceDecl withExtendsClause(Mutation<NodeList<QualifiedType>> mutation);

	NodeList<MemberDecl> members();

	InterfaceDecl withMembers(NodeList<MemberDecl> members);

	InterfaceDecl withMembers(Mutation<NodeList<MemberDecl>> mutation);
}
