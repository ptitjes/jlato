package org.jlato.internal.bu.stmt;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.stmt.TDForStmt;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.stmt.Stmt;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.*;

public class SForStmt extends SNodeState<SForStmt> implements SStmt {

	public static STree<SForStmt> make(STree<SNodeListState> init, STree<? extends SExpr> compare, STree<SNodeListState> update, STree<? extends SStmt> body) {
		return new STree<SForStmt>(new SForStmt(init, compare, update, body));
	}

	public final STree<SNodeListState> init;

	public final STree<? extends SExpr> compare;

	public final STree<SNodeListState> update;

	public final STree<? extends SStmt> body;

	public SForStmt(STree<SNodeListState> init, STree<? extends SExpr> compare, STree<SNodeListState> update, STree<? extends SStmt> body) {
		this.init = init;
		this.compare = compare;
		this.update = update;
		this.body = body;
	}

	@Override
	public Kind kind() {
		return Kind.ForStmt;
	}

	public STree<SNodeListState> init() {
		return init;
	}

	public SForStmt withInit(STree<SNodeListState> init) {
		return new SForStmt(init, compare, update, body);
	}

	public STree<? extends SExpr> compare() {
		return compare;
	}

	public SForStmt withCompare(STree<? extends SExpr> compare) {
		return new SForStmt(init, compare, update, body);
	}

	public STree<SNodeListState> update() {
		return update;
	}

	public SForStmt withUpdate(STree<SNodeListState> update) {
		return new SForStmt(init, compare, update, body);
	}

	public STree<? extends SStmt> body() {
		return body;
	}

	public SForStmt withBody(STree<? extends SStmt> body) {
		return new SForStmt(init, compare, update, body);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SForStmt> location) {
		return new TDForStmt(location);
	}

	@Override
	public LexicalShape shape() {
		return shape;
	}

	@Override
	public STraversal firstChild() {
		return INIT;
	}

	@Override
	public STraversal lastChild() {
		return BODY;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SForStmt state = (SForStmt) o;
		if (!init.equals(state.init))
			return false;
		if (compare == null ? state.compare != null : !compare.equals(state.compare))
			return false;
		if (!update.equals(state.update))
			return false;
		if (body == null ? state.body != null : !body.equals(state.body))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + init.hashCode();
		if (compare != null) result = 37 * result + compare.hashCode();
		result = 37 * result + update.hashCode();
		if (body != null) result = 37 * result + body.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SForStmt, SNodeListState, NodeList<Expr>> INIT = new STypeSafeTraversal<SForStmt, SNodeListState, NodeList<Expr>>() {

		@Override
		public STree<?> doTraverse(SForStmt state) {
			return state.init;
		}

		@Override
		public SForStmt doRebuildParentState(SForStmt state, STree<SNodeListState> child) {
			return state.withInit(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return COMPARE;
		}
	};

	public static STypeSafeTraversal<SForStmt, SExpr, Expr> COMPARE = new STypeSafeTraversal<SForStmt, SExpr, Expr>() {

		@Override
		public STree<?> doTraverse(SForStmt state) {
			return state.compare;
		}

		@Override
		public SForStmt doRebuildParentState(SForStmt state, STree<SExpr> child) {
			return state.withCompare(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return INIT;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return UPDATE;
		}
	};

	public static STypeSafeTraversal<SForStmt, SNodeListState, NodeList<Expr>> UPDATE = new STypeSafeTraversal<SForStmt, SNodeListState, NodeList<Expr>>() {

		@Override
		public STree<?> doTraverse(SForStmt state) {
			return state.update;
		}

		@Override
		public SForStmt doRebuildParentState(SForStmt state, STree<SNodeListState> child) {
			return state.withUpdate(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return COMPARE;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return BODY;
		}
	};

	public static STypeSafeTraversal<SForStmt, SStmt, Stmt> BODY = new STypeSafeTraversal<SForStmt, SStmt, Stmt>() {

		@Override
		public STree<?> doTraverse(SForStmt state) {
			return state.body;
		}

		@Override
		public SForStmt doRebuildParentState(SForStmt state, STree<SStmt> child) {
			return state.withBody(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return UPDATE;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			keyword(LToken.For), token(LToken.ParenthesisLeft).withSpacingBefore(space()),
			child(INIT, list(token(LToken.Comma).withSpacingAfter(space()))),
			token(LToken.SemiColon).withSpacingAfter(space()),
			child(COMPARE),
			token(LToken.SemiColon).withSpacingAfter(space()),
			child(UPDATE, list(token(LToken.Comma).withSpacingAfter(space()))),
			token(LToken.ParenthesisRight).withSpacingAfter(space()),
			child(BODY)
	);
}
