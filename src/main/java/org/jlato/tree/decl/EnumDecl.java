package org.jlato.tree.decl;

import org.jlato.tree.NodeList;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.QualifiedType;
import org.jlato.util.Mutation;

public interface EnumDecl extends TypeDecl, TreeCombinators<EnumDecl> {

	NodeList<ExtendedModifier> modifiers();

	EnumDecl withModifiers(NodeList<ExtendedModifier> modifiers);

	EnumDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation);

	Name name();

	EnumDecl withName(Name name);

	EnumDecl withName(Mutation<Name> mutation);

	NodeList<QualifiedType> implementsClause();

	EnumDecl withImplementsClause(NodeList<QualifiedType> implementsClause);

	EnumDecl withImplementsClause(Mutation<NodeList<QualifiedType>> mutation);

	NodeList<EnumConstantDecl> enumConstants();

	EnumDecl withEnumConstants(NodeList<EnumConstantDecl> enumConstants);

	EnumDecl withEnumConstants(Mutation<NodeList<EnumConstantDecl>> mutation);

	boolean trailingComma();

	EnumDecl withTrailingComma(boolean trailingComma);

	EnumDecl withTrailingComma(Mutation<Boolean> mutation);

	NodeList<MemberDecl> members();

	EnumDecl withMembers(NodeList<MemberDecl> members);

	EnumDecl withMembers(Mutation<NodeList<MemberDecl>> mutation);
}
