package org.jlato.tree.expr;

import org.jlato.tree.TreeCombinators;
import org.jlato.tree.decl.LocalVariableDecl;
import org.jlato.util.Mutation;

public interface VariableDeclarationExpr extends Expr, TreeCombinators<VariableDeclarationExpr> {

	LocalVariableDecl declaration();

	VariableDeclarationExpr withDeclaration(LocalVariableDecl declaration);

	VariableDeclarationExpr withDeclaration(Mutation<LocalVariableDecl> mutation);
}
