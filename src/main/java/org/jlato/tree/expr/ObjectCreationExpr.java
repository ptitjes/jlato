package org.jlato.tree.expr;

import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.decl.MemberDecl;
import org.jlato.tree.type.QualifiedType;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

public interface ObjectCreationExpr extends Expr, TreeCombinators<ObjectCreationExpr> {

	NodeOption<Expr> scope();

	ObjectCreationExpr withScope(NodeOption<Expr> scope);

	ObjectCreationExpr withScope(Mutation<NodeOption<Expr>> mutation);

	NodeList<Type> typeArgs();

	ObjectCreationExpr withTypeArgs(NodeList<Type> typeArgs);

	ObjectCreationExpr withTypeArgs(Mutation<NodeList<Type>> mutation);

	QualifiedType type();

	ObjectCreationExpr withType(QualifiedType type);

	ObjectCreationExpr withType(Mutation<QualifiedType> mutation);

	NodeList<Expr> args();

	ObjectCreationExpr withArgs(NodeList<Expr> args);

	ObjectCreationExpr withArgs(Mutation<NodeList<Expr>> mutation);

	NodeOption<NodeList<MemberDecl>> body();

	ObjectCreationExpr withBody(NodeOption<NodeList<MemberDecl>> body);

	ObjectCreationExpr withBody(Mutation<NodeOption<NodeList<MemberDecl>>> mutation);
}
