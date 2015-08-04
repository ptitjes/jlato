package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.expr.TDArrayAccessExpr;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;

import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;
import static org.jlato.printer.IndentationConstraint.*;
import static org.jlato.printer.SpacingConstraint.*;

public class SArrayAccessExpr extends SNodeState<SArrayAccessExpr> implements SExpr {

	public static STree<SArrayAccessExpr> make(STree<? extends SExpr> name, STree<? extends SExpr> index) {
		return new STree<SArrayAccessExpr>(new SArrayAccessExpr(name, index));
	}

	public final STree<? extends SExpr> name;

	public final STree<? extends SExpr> index;

	public SArrayAccessExpr(STree<? extends SExpr> name, STree<? extends SExpr> index) {
		this.name = name;
		this.index = index;
	}

	@Override
	public Kind kind() {
		return Kind.ArrayAccessExpr;
	}

	public STree<? extends SExpr> name() {
		return name;
	}

	public SArrayAccessExpr withName(STree<? extends SExpr> name) {
		return new SArrayAccessExpr(name, index);
	}

	public STree<? extends SExpr> index() {
		return index;
	}

	public SArrayAccessExpr withIndex(STree<? extends SExpr> index) {
		return new SArrayAccessExpr(name, index);
	}

	@Override
	protected Tree doInstantiate(SLocation<SArrayAccessExpr> location) {
		return new TDArrayAccessExpr(location);
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
		return INDEX;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SArrayAccessExpr state = (SArrayAccessExpr) o;
		if (name == null ? state.name != null : !name.equals(state.name))
			return false;
		if (index == null ? state.index != null : !index.equals(state.index))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		if (name != null) result = 37 * result + name.hashCode();
		if (index != null) result = 37 * result + index.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SArrayAccessExpr, SExpr, Expr> NAME = new STypeSafeTraversal<SArrayAccessExpr, SExpr, Expr>() {

		@Override
		public STree<?> doTraverse(SArrayAccessExpr state) {
			return state.name;
		}

		@Override
		public SArrayAccessExpr doRebuildParentState(SArrayAccessExpr state, STree<SExpr> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return INDEX;
		}
	};

	public static STypeSafeTraversal<SArrayAccessExpr, SExpr, Expr> INDEX = new STypeSafeTraversal<SArrayAccessExpr, SExpr, Expr>() {

		@Override
		public STree<?> doTraverse(SArrayAccessExpr state) {
			return state.index;
		}

		@Override
		public SArrayAccessExpr doRebuildParentState(SArrayAccessExpr state, STree<SExpr> child) {
			return state.withIndex(child);
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
			token(LToken.BracketLeft), child(INDEX), token(LToken.BracketRight)
	);
}
