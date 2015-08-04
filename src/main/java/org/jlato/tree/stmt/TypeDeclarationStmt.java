package org.jlato.tree.stmt;

import org.jlato.tree.TreeCombinators;
import org.jlato.tree.decl.TypeDecl;
import org.jlato.util.Mutation;

public interface TypeDeclarationStmt extends Stmt, TreeCombinators<TypeDeclarationStmt> {

	TypeDecl typeDecl();

	TypeDeclarationStmt withTypeDecl(TypeDecl typeDecl);

	TypeDeclarationStmt withTypeDecl(Mutation<TypeDecl> mutation);
}
