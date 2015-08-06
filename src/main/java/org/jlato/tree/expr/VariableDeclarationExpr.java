package org.jlato.tree.expr;

import org.jlato.tree.TreeCombinators;
import org.jlato.tree.decl.LocalVariableDecl;
import org.jlato.util.Mutation;

/**
 * A variable declaration expression.
 */
public interface VariableDeclarationExpr extends Expr, TreeCombinators<VariableDeclarationExpr> {

	/**
	 * Returns the declaration of this variable declaration expression.
	 *
	 * @return the declaration of this variable declaration expression.
	 */
	LocalVariableDecl declaration();

	/**
	 * Replaces the declaration of this variable declaration expression.
	 *
	 * @param declaration the replacement for the declaration of this variable declaration expression.
	 * @return the resulting mutated variable declaration expression.
	 */
	VariableDeclarationExpr withDeclaration(LocalVariableDecl declaration);

	/**
	 * Mutates the declaration of this variable declaration expression.
	 *
	 * @param mutation the mutation to apply to the declaration of this variable declaration expression.
	 * @return the resulting mutated variable declaration expression.
	 */
	VariableDeclarationExpr withDeclaration(Mutation<LocalVariableDecl> mutation);
}
