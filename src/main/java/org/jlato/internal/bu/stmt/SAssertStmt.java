package org.jlato.internal.bu.stmt;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.stmt.TDAssertStmt;
import org.jlato.tree.*;
import org.jlato.tree.expr.*;

import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.space;

public class SAssertStmt extends SNode<SAssertStmt> implements SStmt {

	public static BUTree<SAssertStmt> make(BUTree<? extends SExpr> check, BUTree<SNodeOption> msg) {
		return new BUTree<SAssertStmt>(new SAssertStmt(check, msg));
	}

	public final BUTree<? extends SExpr> check;

	public final BUTree<SNodeOption> msg;

	public SAssertStmt(BUTree<? extends SExpr> check, BUTree<SNodeOption> msg) {
		this.check = check;
		this.msg = msg;
	}

	@Override
	public Kind kind() {
		return Kind.AssertStmt;
	}

	public SAssertStmt withCheck(BUTree<? extends SExpr> check) {
		return new SAssertStmt(check, msg);
	}

	public SAssertStmt withMsg(BUTree<SNodeOption> msg) {
		return new SAssertStmt(check, msg);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SAssertStmt> location) {
		return new TDAssertStmt(location);
	}

	@Override
	public LexicalShape shape() {
		return shape;
	}

	@Override
	public STraversal firstChild() {
		return CHECK;
	}

	@Override
	public STraversal lastChild() {
		return MSG;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SAssertStmt state = (SAssertStmt) o;
		if (check == null ? state.check != null : !check.equals(state.check))
			return false;
		if (!msg.equals(state.msg))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		if (check != null) result = 37 * result + check.hashCode();
		result = 37 * result + msg.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SAssertStmt, SExpr, Expr> CHECK = new STypeSafeTraversal<SAssertStmt, SExpr, Expr>() {

		@Override
		public BUTree<?> doTraverse(SAssertStmt state) {
			return state.check;
		}

		@Override
		public SAssertStmt doRebuildParentState(SAssertStmt state, BUTree<SExpr> child) {
			return state.withCheck(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return MSG;
		}
	};

	public static STypeSafeTraversal<SAssertStmt, SNodeOption, NodeOption<Expr>> MSG = new STypeSafeTraversal<SAssertStmt, SNodeOption, NodeOption<Expr>>() {

		@Override
		public BUTree<?> doTraverse(SAssertStmt state) {
			return state.msg;
		}

		@Override
		public SAssertStmt doRebuildParentState(SAssertStmt state, BUTree<SNodeOption> child) {
			return state.withMsg(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return CHECK;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			keyword(LToken.Assert),
			child(CHECK),
			child(MSG, when(some(),
					composite(token(LToken.Colon).withSpacing(space(), space()), element())
			)),
			token(LToken.SemiColon)
	);
}
