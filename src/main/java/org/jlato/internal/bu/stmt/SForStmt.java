package org.jlato.internal.bu.stmt;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.stmt.TDForStmt;
import org.jlato.tree.*;
import org.jlato.tree.expr.*;
import org.jlato.tree.stmt.*;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class SForStmt extends SNode<SForStmt> implements SStmt {

	public static BUTree<SForStmt> make(BUTree<SNodeList> init, BUTree<? extends SExpr> compare, BUTree<SNodeList> update, BUTree<? extends SStmt> body) {
		return new BUTree<SForStmt>(new SForStmt(init, compare, update, body));
	}

	public final BUTree<SNodeList> init;

	public final BUTree<? extends SExpr> compare;

	public final BUTree<SNodeList> update;

	public final BUTree<? extends SStmt> body;

	public SForStmt(BUTree<SNodeList> init, BUTree<? extends SExpr> compare, BUTree<SNodeList> update, BUTree<? extends SStmt> body) {
		this.init = init;
		this.compare = compare;
		this.update = update;
		this.body = body;
	}

	@Override
	public Kind kind() {
		return Kind.ForStmt;
	}

	public BUTree<SNodeList> init() {
		return init;
	}

	public SForStmt withInit(BUTree<SNodeList> init) {
		return new SForStmt(init, compare, update, body);
	}

	public BUTree<? extends SExpr> compare() {
		return compare;
	}

	public SForStmt withCompare(BUTree<? extends SExpr> compare) {
		return new SForStmt(init, compare, update, body);
	}

	public BUTree<SNodeList> update() {
		return update;
	}

	public SForStmt withUpdate(BUTree<SNodeList> update) {
		return new SForStmt(init, compare, update, body);
	}

	public BUTree<? extends SStmt> body() {
		return body;
	}

	public SForStmt withBody(BUTree<? extends SStmt> body) {
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

	public static STypeSafeTraversal<SForStmt, SNodeList, NodeList<Expr>> INIT = new STypeSafeTraversal<SForStmt, SNodeList, NodeList<Expr>>() {

		@Override
		public BUTree<?> doTraverse(SForStmt state) {
			return state.init;
		}

		@Override
		public SForStmt doRebuildParentState(SForStmt state, BUTree<SNodeList> child) {
			return state.withInit(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return COMPARE;
		}
	};

	public static STypeSafeTraversal<SForStmt, SExpr, Expr> COMPARE = new STypeSafeTraversal<SForStmt, SExpr, Expr>() {

		@Override
		public BUTree<?> doTraverse(SForStmt state) {
			return state.compare;
		}

		@Override
		public SForStmt doRebuildParentState(SForStmt state, BUTree<SExpr> child) {
			return state.withCompare(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return INIT;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return UPDATE;
		}
	};

	public static STypeSafeTraversal<SForStmt, SNodeList, NodeList<Expr>> UPDATE = new STypeSafeTraversal<SForStmt, SNodeList, NodeList<Expr>>() {

		@Override
		public BUTree<?> doTraverse(SForStmt state) {
			return state.update;
		}

		@Override
		public SForStmt doRebuildParentState(SForStmt state, BUTree<SNodeList> child) {
			return state.withUpdate(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return COMPARE;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return BODY;
		}
	};

	public static STypeSafeTraversal<SForStmt, SStmt, Stmt> BODY = new STypeSafeTraversal<SForStmt, SStmt, Stmt>() {

		@Override
		public BUTree<?> doTraverse(SForStmt state) {
			return state.body;
		}

		@Override
		public SForStmt doRebuildParentState(SForStmt state, BUTree<SStmt> child) {
			return state.withBody(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return UPDATE;
		}

		@Override
		public STraversal rightSibling(STree state) {
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
