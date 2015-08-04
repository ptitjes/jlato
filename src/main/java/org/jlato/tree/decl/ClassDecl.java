package org.jlato.tree.decl;

import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.QualifiedType;
import org.jlato.util.Mutation;

public interface ClassDecl extends TypeDecl, TreeCombinators<ClassDecl> {

	NodeList<ExtendedModifier> modifiers();

	ClassDecl withModifiers(NodeList<ExtendedModifier> modifiers);

	ClassDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation);

	Name name();

	ClassDecl withName(Name name);

	ClassDecl withName(Mutation<Name> mutation);

	NodeList<TypeParameter> typeParams();

	ClassDecl withTypeParams(NodeList<TypeParameter> typeParams);

	ClassDecl withTypeParams(Mutation<NodeList<TypeParameter>> mutation);

	NodeOption<QualifiedType> extendsClause();

	ClassDecl withExtendsClause(NodeOption<QualifiedType> extendsClause);

	ClassDecl withExtendsClause(Mutation<NodeOption<QualifiedType>> mutation);

	NodeList<QualifiedType> implementsClause();

	ClassDecl withImplementsClause(NodeList<QualifiedType> implementsClause);

	ClassDecl withImplementsClause(Mutation<NodeList<QualifiedType>> mutation);

	NodeList<MemberDecl> members();

	ClassDecl withMembers(NodeList<MemberDecl> members);

	ClassDecl withMembers(Mutation<NodeList<MemberDecl>> mutation);
}
