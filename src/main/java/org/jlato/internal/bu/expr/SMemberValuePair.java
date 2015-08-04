package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDMemberValuePair;
import org.jlato.tree.*;
import org.jlato.tree.expr.*;
import org.jlato.tree.name.*;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.space;

public class SMemberValuePair extends SNode<SMemberValuePair> implements STree {

	public static BUTree<SMemberValuePair> make(BUTree<SName> name, BUTree<? extends SExpr> value) {
		return new BUTree<SMemberValuePair>(new SMemberValuePair(name, value));
	}

	public final BUTree<SName> name;

	public final BUTree<? extends SExpr> value;

	public SMemberValuePair(BUTree<SName> name, BUTree<? extends SExpr> value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public Kind kind() {
		return Kind.MemberValuePair;
	}

	public BUTree<SName> name() {
		return name;
	}

	public SMemberValuePair withName(BUTree<SName> name) {
		return new SMemberValuePair(name, value);
	}

	public BUTree<? extends SExpr> value() {
		return value;
	}

	public SMemberValuePair withValue(BUTree<? extends SExpr> value) {
		return new SMemberValuePair(name, value);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SMemberValuePair> location) {
		return new TDMemberValuePair(location);
	}

	@Override
	public LexicalShape shape() {
		return shape;
	}

	@Override
	public STraversal firstChild() {
		return NAME;
	}

	@Override
	public STraversal lastChild() {
		return VALUE;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SMemberValuePair state = (SMemberValuePair) o;
		if (name == null ? state.name != null : !name.equals(state.name))
			return false;
		if (value == null ? state.value != null : !value.equals(state.value))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		if (name != null) result = 37 * result + name.hashCode();
		if (value != null) result = 37 * result + value.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SMemberValuePair, SName, Name> NAME = new STypeSafeTraversal<SMemberValuePair, SName, Name>() {

		@Override
		public BUTree<?> doTraverse(SMemberValuePair state) {
			return state.name;
		}

		@Override
		public SMemberValuePair doRebuildParentState(SMemberValuePair state, BUTree<SName> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return VALUE;
		}
	};

	public static STypeSafeTraversal<SMemberValuePair, SExpr, Expr> VALUE = new STypeSafeTraversal<SMemberValuePair, SExpr, Expr>() {

		@Override
		public BUTree<?> doTraverse(SMemberValuePair state) {
			return state.value;
		}

		@Override
		public SMemberValuePair doRebuildParentState(SMemberValuePair state, BUTree<SExpr> child) {
			return state.withValue(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return NAME;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(NAME),
			token(LToken.Assign).withSpacing(space(), space()),
			child(VALUE)
	);
}
