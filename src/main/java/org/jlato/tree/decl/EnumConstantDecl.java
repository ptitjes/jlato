package org.jlato.tree.decl;

import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.name.Name;
import org.jlato.util.Mutation;

public interface EnumConstantDecl extends MemberDecl, TreeCombinators<EnumConstantDecl> {

	NodeList<ExtendedModifier> modifiers();

	EnumConstantDecl withModifiers(NodeList<ExtendedModifier> modifiers);

	EnumConstantDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation);

	Name name();

	EnumConstantDecl withName(Name name);

	EnumConstantDecl withName(Mutation<Name> mutation);

	NodeOption<NodeList<Expr>> args();

	EnumConstantDecl withArgs(NodeOption<NodeList<Expr>> args);

	EnumConstantDecl withArgs(Mutation<NodeOption<NodeList<Expr>>> mutation);

	NodeOption<NodeList<MemberDecl>> classBody();

	EnumConstantDecl withClassBody(NodeOption<NodeList<MemberDecl>> classBody);

	EnumConstantDecl withClassBody(Mutation<NodeOption<NodeList<MemberDecl>>> mutation);
}
