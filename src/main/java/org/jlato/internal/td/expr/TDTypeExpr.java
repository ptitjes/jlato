package org.jlato.internal.td.expr;

import org.jlato.internal.bu.expr.STypeExpr;
import org.jlato.internal.bu.type.SType;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.expr.TypeExpr;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

/**
 * A type expression.
 */
public class TDTypeExpr extends TDTree<STypeExpr, Expr, TypeExpr> implements TypeExpr {

	/**
	 * Returns the kind of this type expression.
	 *
	 * @return the kind of this type expression.
	 */
	public Kind kind() {
		return Kind.TypeExpr;
	}

	/**
	 * Creates a type expression for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDTypeExpr(TDLocation<STypeExpr> location) {
		super(location);
	}

	/**
	 * Creates a type expression with the specified child trees.
	 *
	 * @param type the type child tree.
	 */
	public TDTypeExpr(Type type) {
		super(new TDLocation<STypeExpr>(STypeExpr.make(TDTree.<SType>treeOf(type))));
	}

	/**
	 * Returns the type of this type expression.
	 *
	 * @return the type of this type expression.
	 */
	public Type type() {
		return location.safeTraversal(STypeExpr.TYPE);
	}

	/**
	 * Replaces the type of this type expression.
	 *
	 * @param type the replacement for the type of this type expression.
	 * @return the resulting mutated type expression.
	 */
	public TypeExpr withType(Type type) {
		return location.safeTraversalReplace(STypeExpr.TYPE, type);
	}

	/**
	 * Mutates the type of this type expression.
	 *
	 * @param mutation the mutation to apply to the type of this type expression.
	 * @return the resulting mutated type expression.
	 */
	public TypeExpr withType(Mutation<Type> mutation) {
		return location.safeTraversalMutate(STypeExpr.TYPE, mutation);
	}
}
