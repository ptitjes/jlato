package org.jlato.tree.expr;

import org.jlato.internal.bu.SNode;
import org.jlato.tree.Expr;
import org.jlato.tree.Tree;

public class MemberValuePair extends Tree {

	public final static Tree.Kind kind = new Tree.Kind() {
		public MemberValuePair instantiate(SLocation location) {
			return new MemberValuePair(location);
		}
	};

	private MemberValuePair(SLocation location) {
		super(location);
	}

	public MemberValuePair(NameExpr name, Expr value) {
		super(new SLocation(new SNode(kind, runOf(name, value))));
	}

	public NameExpr name() {
		return location.nodeChild(NAME);
	}

	public MemberValuePair withName(NameExpr name) {
		return location.nodeWithChild(NAME, name);
	}

	public NameExpr value() {
		return location.nodeChild(VALUE);
	}

	public MemberValuePair withValue(Expr value) {
		return location.nodeWithChild(VALUE, value);
	}

	private static final int NAME = 0;
	private static final int VALUE = 1;
}
