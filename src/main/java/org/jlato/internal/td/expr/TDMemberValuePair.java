package org.jlato.internal.td.expr;

import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.bu.expr.SMemberValuePair;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.Node;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.expr.MemberValuePair;
import org.jlato.tree.name.Name;
import org.jlato.util.Mutation;

/**
 * An annotation member value pair.
 */
public class TDMemberValuePair extends TDTree<SMemberValuePair, Node, MemberValuePair> implements MemberValuePair {

	/**
	 * Returns the kind of this annotation member value pair.
	 *
	 * @return the kind of this annotation member value pair.
	 */
	public Kind kind() {
		return Kind.MemberValuePair;
	}

	/**
	 * Creates an annotation member value pair for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDMemberValuePair(TDLocation<SMemberValuePair> location) {
		super(location);
	}

	/**
	 * Creates an annotation member value pair with the specified child trees.
	 *
	 * @param name  the name child tree.
	 * @param value the value child tree.
	 */
	public TDMemberValuePair(Name name, Expr value) {
		super(new TDLocation<SMemberValuePair>(SMemberValuePair.make(TDTree.<SName>treeOf(name), TDTree.<SExpr>treeOf(value))));
	}

	/**
	 * Returns the name of this annotation member value pair.
	 *
	 * @return the name of this annotation member value pair.
	 */
	public Name name() {
		return location.safeTraversal(SMemberValuePair.NAME);
	}

	/**
	 * Replaces the name of this annotation member value pair.
	 *
	 * @param name the replacement for the name of this annotation member value pair.
	 * @return the resulting mutated annotation member value pair.
	 */
	public MemberValuePair withName(Name name) {
		return location.safeTraversalReplace(SMemberValuePair.NAME, name);
	}

	/**
	 * Mutates the name of this annotation member value pair.
	 *
	 * @param mutation the mutation to apply to the name of this annotation member value pair.
	 * @return the resulting mutated annotation member value pair.
	 */
	public MemberValuePair withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(SMemberValuePair.NAME, mutation);
	}

	/**
	 * Returns the value of this annotation member value pair.
	 *
	 * @return the value of this annotation member value pair.
	 */
	public Expr value() {
		return location.safeTraversal(SMemberValuePair.VALUE);
	}

	/**
	 * Replaces the value of this annotation member value pair.
	 *
	 * @param value the replacement for the value of this annotation member value pair.
	 * @return the resulting mutated annotation member value pair.
	 */
	public MemberValuePair withValue(Expr value) {
		return location.safeTraversalReplace(SMemberValuePair.VALUE, value);
	}

	/**
	 * Mutates the value of this annotation member value pair.
	 *
	 * @param mutation the mutation to apply to the value of this annotation member value pair.
	 * @return the resulting mutated annotation member value pair.
	 */
	public MemberValuePair withValue(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SMemberValuePair.VALUE, mutation);
	}
}
