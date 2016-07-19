package org.jlato.tree.expr;

import org.jlato.tree.NodeOption;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.name.Name;
import org.jlato.util.Mutation;

/**
 * A field access expression.
 */
public interface FieldAccessExpr extends Expr, TreeCombinators<FieldAccessExpr> {

	/**
	 * Returns the scope of this field access expression.
	 *
	 * @return the scope of this field access expression.
	 */
	NodeOption<Expr> scope();

	/**
	 * Replaces the scope of this field access expression.
	 *
	 * @param scope the replacement for the scope of this field access expression.
	 * @return the resulting mutated field access expression.
	 */
	FieldAccessExpr withScope(NodeOption<Expr> scope);

	/**
	 * Mutates the scope of this field access expression.
	 *
	 * @param mutation the mutation to apply to the scope of this field access expression.
	 * @return the resulting mutated field access expression.
	 */
	FieldAccessExpr withScope(Mutation<NodeOption<Expr>> mutation);

	/**
	 * Returns the name of this field access expression.
	 *
	 * @return the name of this field access expression.
	 */
	Name name();

	/**
	 * Replaces the name of this field access expression.
	 *
	 * @param name the replacement for the name of this field access expression.
	 * @return the resulting mutated field access expression.
	 */
	FieldAccessExpr withName(Name name);

	/**
	 * Mutates the name of this field access expression.
	 *
	 * @param mutation the mutation to apply to the name of this field access expression.
	 * @return the resulting mutated field access expression.
	 */
	FieldAccessExpr withName(Mutation<Name> mutation);

	/**
	 * Replaces the name of this field access expression.
	 *
	 * @param name the replacement for the name of this field access expression.
	 * @return the resulting mutated field access expression.
	 */
	FieldAccessExpr withName(String name);
}
