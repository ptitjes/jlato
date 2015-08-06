package org.jlato.tree.expr;

import org.jlato.tree.TreeCombinators;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

/**
 * An 'instanceof' expression.
 */
public interface InstanceOfExpr extends Expr, TreeCombinators<InstanceOfExpr> {

	/**
	 * Returns the expression of this 'instanceof' expression.
	 *
	 * @return the expression of this 'instanceof' expression.
	 */
	Expr expr();

	/**
	 * Replaces the expression of this 'instanceof' expression.
	 *
	 * @param expr the replacement for the expression of this 'instanceof' expression.
	 * @return the resulting mutated 'instanceof' expression.
	 */
	InstanceOfExpr withExpr(Expr expr);

	/**
	 * Mutates the expression of this 'instanceof' expression.
	 *
	 * @param mutation the mutation to apply to the expression of this 'instanceof' expression.
	 * @return the resulting mutated 'instanceof' expression.
	 */
	InstanceOfExpr withExpr(Mutation<Expr> mutation);

	/**
	 * Returns the type of this 'instanceof' expression.
	 *
	 * @return the type of this 'instanceof' expression.
	 */
	Type type();

	/**
	 * Replaces the type of this 'instanceof' expression.
	 *
	 * @param type the replacement for the type of this 'instanceof' expression.
	 * @return the resulting mutated 'instanceof' expression.
	 */
	InstanceOfExpr withType(Type type);

	/**
	 * Mutates the type of this 'instanceof' expression.
	 *
	 * @param mutation the mutation to apply to the type of this 'instanceof' expression.
	 * @return the resulting mutated 'instanceof' expression.
	 */
	InstanceOfExpr withType(Mutation<Type> mutation);
}
