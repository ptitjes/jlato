package org.jlato.tree.decl;

import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.name.Name;
import org.jlato.tree.stmt.BlockStmt;
import org.jlato.tree.type.QualifiedType;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

public interface MethodDecl extends MemberDecl, TreeCombinators<MethodDecl> {

	NodeList<ExtendedModifier> modifiers();

	MethodDecl withModifiers(NodeList<ExtendedModifier> modifiers);

	MethodDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation);

	NodeList<TypeParameter> typeParams();

	MethodDecl withTypeParams(NodeList<TypeParameter> typeParams);

	MethodDecl withTypeParams(Mutation<NodeList<TypeParameter>> mutation);

	Type type();

	MethodDecl withType(Type type);

	MethodDecl withType(Mutation<Type> mutation);

	Name name();

	MethodDecl withName(Name name);

	MethodDecl withName(Mutation<Name> mutation);

	NodeList<FormalParameter> params();

	MethodDecl withParams(NodeList<FormalParameter> params);

	MethodDecl withParams(Mutation<NodeList<FormalParameter>> mutation);

	NodeList<ArrayDim> dims();

	MethodDecl withDims(NodeList<ArrayDim> dims);

	MethodDecl withDims(Mutation<NodeList<ArrayDim>> mutation);

	NodeList<QualifiedType> throwsClause();

	MethodDecl withThrowsClause(NodeList<QualifiedType> throwsClause);

	MethodDecl withThrowsClause(Mutation<NodeList<QualifiedType>> mutation);

	NodeOption<BlockStmt> body();

	MethodDecl withBody(NodeOption<BlockStmt> body);

	MethodDecl withBody(Mutation<NodeOption<BlockStmt>> mutation);
}
