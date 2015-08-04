package org.jlato.internal.bu.stmt;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.stmt.TDAssertStmt;
import org.jlato.tree.*;
import org.jlato.tree.expr.*;

import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class SAssertStmt extends SNodeState<SAssertStmt> implements SStmt {

	public static STree<SAssertStmt> make(STree<? extends SExpr> check, STree<SNodeOptionState> msg) {
		return new STree<SAssertStmt>(new SAssertStmt(check, msg));
	}

	public final STree<? extends SExpr> check;

	public final STree<SNodeOptionState> msg;

	public SAssertStmt(STree<? extends SExpr> check, STree<SNodeOptionState> msg) {
		this.check = check;
		this.msg = msg;
	}

	@Override
	public Kind kind() {
		return Kind.AssertStmt;
	}

	public STree<? extends SExpr> check() {
		return check;
	}

	public SAssertStmt withCheck(STree<? extends SExpr> check) {
		return new SAssertStmt(check, msg);
	}

	public STree<SNodeOptionState> msg() {
		return msg;
	}

	public SAssertStmt withMsg(STree<SNodeOptionState> msg) {
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
		public STree<?> doTraverse(SAssertStmt state) {
			return state.check;
		}

		@Override
		public SAssertStmt doRebuildParentState(SAssertStmt state, STree<SExpr> child) {
			return state.withCheck(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return MSG;
		}
	};

	public static STypeSafeTraversal<SAssertStmt, SNodeOptionState, NodeOption<Expr>> MSG = new STypeSafeTraversal<SAssertStmt, SNodeOptionState, NodeOption<Expr>>() {

		@Override
		public STree<?> doTraverse(SAssertStmt state) {
			return state.msg;
		}

		@Override
		public SAssertStmt doRebuildParentState(SAssertStmt state, STree<SNodeOptionState> child) {
			return state.withMsg(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return CHECK;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
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
