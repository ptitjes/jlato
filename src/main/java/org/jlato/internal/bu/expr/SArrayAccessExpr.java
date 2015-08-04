package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.*;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDArrayAccessExpr;
import org.jlato.tree.*;
import org.jlato.tree.expr.*;

import static org.jlato.internal.shapes.LexicalShape.*;

public class SArrayAccessExpr extends SNodeState<SArrayAccessExpr> implements SExpr {

	public static BUTree<SArrayAccessExpr> make(BUTree<? extends SExpr> name, BUTree<? extends SExpr> index) {
		return new BUTree<SArrayAccessExpr>(new SArrayAccessExpr(name, index));
	}

	public final BUTree<? extends SExpr> name;

	public final BUTree<? extends SExpr> index;

	public SArrayAccessExpr(BUTree<? extends SExpr> name, BUTree<? extends SExpr> index) {
		this.name = name;
		this.index = index;
	}

	@Override
	public Kind kind() {
		return Kind.ArrayAccessExpr;
	}

	public BUTree<? extends SExpr> name() {
		return name;
	}

	public SArrayAccessExpr withName(BUTree<? extends SExpr> name) {
		return new SArrayAccessExpr(name, index);
	}

	public BUTree<? extends SExpr> index() {
		return index;
	}

	public SArrayAccessExpr withIndex(BUTree<? extends SExpr> index) {
		return new SArrayAccessExpr(name, index);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SArrayAccessExpr> location) {
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
		public BUTree<?> doTraverse(SArrayAccessExpr state) {
			return state.name;
		}

		@Override
		public SArrayAccessExpr doRebuildParentState(SArrayAccessExpr state, BUTree<SExpr> child) {
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
		public BUTree<?> doTraverse(SArrayAccessExpr state) {
			return state.index;
		}

		@Override
		public SArrayAccessExpr doRebuildParentState(SArrayAccessExpr state, BUTree<SExpr> child) {
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
