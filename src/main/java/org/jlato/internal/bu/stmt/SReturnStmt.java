package org.jlato.internal.bu.stmt;

import org.jlato.internal.bu.*;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.stmt.TDReturnStmt;
import org.jlato.tree.*;
import org.jlato.tree.expr.*;

import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class SReturnStmt extends SNode<SReturnStmt> implements SStmt {

	public static BUTree<SReturnStmt> make(BUTree<SNodeOption> expr) {
		return new BUTree<SReturnStmt>(new SReturnStmt(expr));
	}

	public final BUTree<SNodeOption> expr;

	public SReturnStmt(BUTree<SNodeOption> expr) {
		this.expr = expr;
	}

	@Override
	public Kind kind() {
		return Kind.ReturnStmt;
	}

	public BUTree<SNodeOption> expr() {
		return expr;
	}

	public SReturnStmt withExpr(BUTree<SNodeOption> expr) {
		return new SReturnStmt(expr);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SReturnStmt> location) {
		return new TDReturnStmt(location);
	}

	@Override
	public LexicalShape shape() {
		return shape;
	}

	@Override
	public STraversal firstChild() {
		return EXPR;
	}

	@Override
	public STraversal lastChild() {
		return EXPR;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SReturnStmt state = (SReturnStmt) o;
		if (!expr.equals(state.expr))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + expr.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SReturnStmt, SNodeOption, NodeOption<Expr>> EXPR = new STypeSafeTraversal<SReturnStmt, SNodeOption, NodeOption<Expr>>() {

		@Override
		public BUTree<?> doTraverse(SReturnStmt state) {
			return state.expr;
		}

		@Override
		public SReturnStmt doRebuildParentState(SReturnStmt state, BUTree<SNodeOption> child) {
			return state.withExpr(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			token(LToken.Return),
			child(EXPR, when(some(), element().withSpacingBefore(space()))),
			token(LToken.SemiColon)
	);
}
