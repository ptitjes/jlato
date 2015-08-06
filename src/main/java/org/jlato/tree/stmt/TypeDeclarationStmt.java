package org.jlato.tree.stmt;

import org.jlato.tree.TreeCombinators;
import org.jlato.tree.decl.TypeDecl;
import org.jlato.util.Mutation;

/**
 * A type declaration statement.
 */
public interface TypeDeclarationStmt extends Stmt, TreeCombinators<TypeDeclarationStmt> {

	/**
	 * Returns the type declaration of this type declaration statement.
	 *
	 * @return the type declaration of this type declaration statement.
	 */
	TypeDecl typeDecl();

	/**
	 * Replaces the type declaration of this type declaration statement.
	 *
	 * @param typeDecl the replacement for the type declaration of this type declaration statement.
	 * @return the resulting mutated type declaration statement.
	 */
	TypeDeclarationStmt withTypeDecl(TypeDecl typeDecl);

	/**
	 * Mutates the type declaration of this type declaration statement.
	 *
	 * @param mutation the mutation to apply to the type declaration of this type declaration statement.
	 * @return the resulting mutated type declaration statement.
	 */
	TypeDeclarationStmt withTypeDecl(Mutation<TypeDecl> mutation);
}
