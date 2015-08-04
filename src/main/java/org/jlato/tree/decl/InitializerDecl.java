package org.jlato.tree.decl;

import org.jlato.tree.NodeList;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.stmt.BlockStmt;
import org.jlato.util.Mutation;

public interface InitializerDecl extends MemberDecl, TreeCombinators<InitializerDecl> {

	NodeList<ExtendedModifier> modifiers();

	InitializerDecl withModifiers(NodeList<ExtendedModifier> modifiers);

	InitializerDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation);

	BlockStmt body();

	InitializerDecl withBody(BlockStmt body);

	InitializerDecl withBody(Mutation<BlockStmt> mutation);
}
