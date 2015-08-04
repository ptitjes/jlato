package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.expr.TDMemberValuePair;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.name.Name;

import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;
import static org.jlato.printer.IndentationConstraint.*;
import static org.jlato.printer.SpacingConstraint.*;

public class SMemberValuePair extends SNodeState<SMemberValuePair> implements STreeState {

	public static STree<SMemberValuePair> make(STree<SName> name, STree<? extends SExpr> value) {
		return new STree<SMemberValuePair>(new SMemberValuePair(name, value));
	}

	public final STree<SName> name;

	public final STree<? extends SExpr> value;

	public SMemberValuePair(STree<SName> name, STree<? extends SExpr> value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public Kind kind() {
		return Kind.MemberValuePair;
	}

	public STree<SName> name() {
		return name;
	}

	public SMemberValuePair withName(STree<SName> name) {
		return new SMemberValuePair(name, value);
	}

	public STree<? extends SExpr> value() {
		return value;
	}

	public SMemberValuePair withValue(STree<? extends SExpr> value) {
		return new SMemberValuePair(name, value);
	}

	@Override
	protected Tree doInstantiate(SLocation<SMemberValuePair> location) {
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
		public STree<?> doTraverse(SMemberValuePair state) {
			return state.name;
		}

		@Override
		public SMemberValuePair doRebuildParentState(SMemberValuePair state, STree<SName> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return VALUE;
		}
	};

	public static STypeSafeTraversal<SMemberValuePair, SExpr, Expr> VALUE = new STypeSafeTraversal<SMemberValuePair, SExpr, Expr>() {

		@Override
		public STree<?> doTraverse(SMemberValuePair state) {
			return state.value;
		}

		@Override
		public SMemberValuePair doRebuildParentState(SMemberValuePair state, STree<SExpr> child) {
			return state.withValue(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return NAME;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(NAME),
			token(LToken.Assign).withSpacing(space(), space()),
			child(VALUE)
	);
}
