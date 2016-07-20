package org.jlato.tree.expr;

import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.decl.ArrayDim;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

/**
 * An array creation expression.
 */
public interface ArrayCreationExpr extends Expr, TreeCombinators<ArrayCreationExpr> {

	/**
	 * Returns the type of this array creation expression.
	 *
	 * @return the type of this array creation expression.
	 */
	Type type();

	/**
	 * Replaces the type of this array creation expression.
	 *
	 * @param type the replacement for the type of this array creation expression.
	 * @return the resulting mutated array creation expression.
	 */
	ArrayCreationExpr withType(Type type);

	/**
	 * Mutates the type of this array creation expression.
	 *
	 * @param mutation the mutation to apply to the type of this array creation expression.
	 * @return the resulting mutated array creation expression.
	 */
	ArrayCreationExpr withType(Mutation<Type> mutation);

	/**
	 * Returns the dimension expressions of this array creation expression.
	 *
	 * @return the dimension expressions of this array creation expression.
	 */
	NodeList<ArrayDimExpr> dimExprs();

	/**
	 * Replaces the dimension expressions of this array creation expression.
	 *
	 * @param dimExprs the replacement for the dimension expressions of this array creation expression.
	 * @return the resulting mutated array creation expression.
	 */
	ArrayCreationExpr withDimExprs(NodeList<ArrayDimExpr> dimExprs);

	/**
	 * Mutates the dimension expressions of this array creation expression.
	 *
	 * @param mutation the mutation to apply to the dimension expressions of this array creation expression.
	 * @return the resulting mutated array creation expression.
	 */
	ArrayCreationExpr withDimExprs(Mutation<NodeList<ArrayDimExpr>> mutation);

	/**
	 * Returns the dimensions of this array creation expression.
	 *
	 * @return the dimensions of this array creation expression.
	 */
	NodeList<ArrayDim> dims();

	/**
	 * Replaces the dimensions of this array creation expression.
	 *
	 * @param dims the replacement for the dimensions of this array creation expression.
	 * @return the resulting mutated array creation expression.
	 */
	ArrayCreationExpr withDims(NodeList<ArrayDim> dims);

	/**
	 * Mutates the dimensions of this array creation expression.
	 *
	 * @param mutation the mutation to apply to the dimensions of this array creation expression.
	 * @return the resulting mutated array creation expression.
	 */
	ArrayCreationExpr withDims(Mutation<NodeList<ArrayDim>> mutation);

	/**
	 * Returns the init of this array creation expression.
	 *
	 * @return the init of this array creation expression.
	 */
	NodeOption<ArrayInitializerExpr> init();

	/**
	 * Replaces the init of this array creation expression.
	 *
	 * @param init the replacement for the init of this array creation expression.
	 * @return the resulting mutated array creation expression.
	 */
	ArrayCreationExpr withInit(NodeOption<ArrayInitializerExpr> init);

	/**
	 * Mutates the init of this array creation expression.
	 *
	 * @param mutation the mutation to apply to the init of this array creation expression.
	 * @return the resulting mutated array creation expression.
	 */
	ArrayCreationExpr withInit(Mutation<NodeOption<ArrayInitializerExpr>> mutation);

	/**
	 * Replaces the init of this array creation expression.
	 *
	 * @param init the replacement for the init of this array creation expression.
	 * @return the resulting mutated array creation expression.
	 */
	ArrayCreationExpr withInit(ArrayInitializerExpr init);

	/**
	 * Replaces the init of this array creation expression.
	 *
	 * @return the resulting mutated array creation expression.
	 */
	ArrayCreationExpr withNoInit();
}
