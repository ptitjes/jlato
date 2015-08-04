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

public class TDMemberValuePair extends TDTree<SMemberValuePair, Node, MemberValuePair> implements MemberValuePair {

	public Kind kind() {
		return Kind.MemberValuePair;
	}

	public TDMemberValuePair(TDLocation<SMemberValuePair> location) {
		super(location);
	}

	public TDMemberValuePair(Name name, Expr value) {
		super(new TDLocation<SMemberValuePair>(SMemberValuePair.make(TDTree.<SName>treeOf(name), TDTree.<SExpr>treeOf(value))));
	}

	public Name name() {
		return location.safeTraversal(SMemberValuePair.NAME);
	}

	public MemberValuePair withName(Name name) {
		return location.safeTraversalReplace(SMemberValuePair.NAME, name);
	}

	public MemberValuePair withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(SMemberValuePair.NAME, mutation);
	}

	public Expr value() {
		return location.safeTraversal(SMemberValuePair.VALUE);
	}

	public MemberValuePair withValue(Expr value) {
		return location.safeTraversalReplace(SMemberValuePair.VALUE, value);
	}

	public MemberValuePair withValue(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SMemberValuePair.VALUE, mutation);
	}
}
