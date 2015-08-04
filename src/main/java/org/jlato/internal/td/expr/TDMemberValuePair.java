package org.jlato.internal.td.expr;

import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.bu.expr.SMemberValuePair;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.expr.MemberValuePair;
import org.jlato.tree.name.Name;
import org.jlato.util.Mutation;

public class TDMemberValuePair extends TreeBase<SMemberValuePair, Tree, MemberValuePair> implements MemberValuePair {

	public Kind kind() {
		return Kind.MemberValuePair;
	}

	public TDMemberValuePair(SLocation<SMemberValuePair> location) {
		super(location);
	}

	public TDMemberValuePair(Name name, Expr value) {
		super(new SLocation<SMemberValuePair>(SMemberValuePair.make(TreeBase.<SName>treeOf(name), TreeBase.<SExpr>treeOf(value))));
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
