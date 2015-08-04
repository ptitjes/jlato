package org.jlato.tree.decl;

import org.jlato.tree.NodeList;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.name.Name;
import org.jlato.tree.stmt.BlockStmt;
import org.jlato.tree.type.QualifiedType;
import org.jlato.util.Mutation;

public interface ConstructorDecl extends MemberDecl, TreeCombinators<ConstructorDecl> {

	NodeList<ExtendedModifier> modifiers();

	ConstructorDecl withModifiers(NodeList<ExtendedModifier> modifiers);

	ConstructorDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation);

	NodeList<TypeParameter> typeParams();

	ConstructorDecl withTypeParams(NodeList<TypeParameter> typeParams);

	ConstructorDecl withTypeParams(Mutation<NodeList<TypeParameter>> mutation);

	Name name();

	ConstructorDecl withName(Name name);

	ConstructorDecl withName(Mutation<Name> mutation);

	NodeList<FormalParameter> params();

	ConstructorDecl withParams(NodeList<FormalParameter> params);

	ConstructorDecl withParams(Mutation<NodeList<FormalParameter>> mutation);

	NodeList<QualifiedType> throwsClause();

	ConstructorDecl withThrowsClause(NodeList<QualifiedType> throwsClause);

	ConstructorDecl withThrowsClause(Mutation<NodeList<QualifiedType>> mutation);

	BlockStmt body();

	ConstructorDecl withBody(BlockStmt body);

	ConstructorDecl withBody(Mutation<BlockStmt> mutation);
}
