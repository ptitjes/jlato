package org.jlato.tree.expr;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.Expr;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.name.Name;

public class MemberValuePair extends Tree {

	public final static Tree.Kind kind = new Tree.Kind() {
		public MemberValuePair instantiate(SLocation location) {
			return new MemberValuePair(location);
		}

		public LexicalShape shape() {
			return null;
		}
	};

	private MemberValuePair(SLocation location) {
		super(location);
	}

	public MemberValuePair(Name name, Expr value) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(name, value)))));
	}

	public Name name() {
		return location.nodeChild(NAME);
	}

	public MemberValuePair withName(Name name) {
		return location.nodeWithChild(NAME, name);
	}

	public Name value() {
		return location.nodeChild(VALUE);
	}

	public MemberValuePair withValue(Expr value) {
		return location.nodeWithChild(VALUE, value);
	}

	private static final int NAME = 0;
	private static final int VALUE = 1;
}
