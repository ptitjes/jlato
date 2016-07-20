package org.jlato.internal.td.expr;

import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.bu.expr.SFieldAccessExpr;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Trees;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.expr.FieldAccessExpr;
import org.jlato.tree.name.Name;
import org.jlato.util.Mutation;

/**
 * A field access expression.
 */
public class TDFieldAccessExpr extends TDTree<SFieldAccessExpr, Expr, FieldAccessExpr> implements FieldAccessExpr {

	/**
	 * Returns the kind of this field access expression.
	 *
	 * @return the kind of this field access expression.
	 */
	public Kind kind() {
		return Kind.FieldAccessExpr;
	}

	/**
	 * Creates a field access expression for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDFieldAccessExpr(TDLocation<SFieldAccessExpr> location) {
		super(location);
	}

	/**
	 * Creates a field access expression with the specified child trees.
	 *
	 * @param scope the scope child tree.
	 * @param name  the name child tree.
	 */
	public TDFieldAccessExpr(NodeOption<Expr> scope, Name name) {
		super(new TDLocation<SFieldAccessExpr>(SFieldAccessExpr.make(TDTree.<SNodeOption>treeOf(scope), TDTree.<SName>treeOf(name))));
	}

	/**
	 * Returns the scope of this field access expression.
	 *
	 * @return the scope of this field access expression.
	 */
	public NodeOption<Expr> scope() {
		return location.safeTraversal(SFieldAccessExpr.SCOPE);
	}

	/**
	 * Replaces the scope of this field access expression.
	 *
	 * @param scope the replacement for the scope of this field access expression.
	 * @return the resulting mutated field access expression.
	 */
	public FieldAccessExpr withScope(NodeOption<Expr> scope) {
		return location.safeTraversalReplace(SFieldAccessExpr.SCOPE, scope);
	}

	/**
	 * Mutates the scope of this field access expression.
	 *
	 * @param mutation the mutation to apply to the scope of this field access expression.
	 * @return the resulting mutated field access expression.
	 */
	public FieldAccessExpr withScope(Mutation<NodeOption<Expr>> mutation) {
		return location.safeTraversalMutate(SFieldAccessExpr.SCOPE, mutation);
	}

	/**
	 * Replaces the scope of this field access expression.
	 *
	 * @param scope the replacement for the scope of this field access expression.
	 * @return the resulting mutated field access expression.
	 */
	public FieldAccessExpr withScope(Expr scope) {
		return location.safeTraversalReplace(SFieldAccessExpr.SCOPE, Trees.some(scope));
	}

	/**
	 * Replaces the scope of this field access expression.
	 *
	 * @return the resulting mutated field access expression.
	 */
	public FieldAccessExpr withNoScope() {
		return location.safeTraversalReplace(SFieldAccessExpr.SCOPE, Trees.<Expr>none());
	}

	/**
	 * Returns the name of this field access expression.
	 *
	 * @return the name of this field access expression.
	 */
	public Name name() {
		return location.safeTraversal(SFieldAccessExpr.NAME);
	}

	/**
	 * Replaces the name of this field access expression.
	 *
	 * @param name the replacement for the name of this field access expression.
	 * @return the resulting mutated field access expression.
	 */
	public FieldAccessExpr withName(Name name) {
		return location.safeTraversalReplace(SFieldAccessExpr.NAME, name);
	}

	/**
	 * Mutates the name of this field access expression.
	 *
	 * @param mutation the mutation to apply to the name of this field access expression.
	 * @return the resulting mutated field access expression.
	 */
	public FieldAccessExpr withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(SFieldAccessExpr.NAME, mutation);
	}

	/**
	 * Replaces the name of this field access expression.
	 *
	 * @param name the replacement for the name of this field access expression.
	 * @return the resulting mutated field access expression.
	 */
	public FieldAccessExpr withName(String name) {
		return location.safeTraversalReplace(SFieldAccessExpr.NAME, Trees.name(name));
	}
}
