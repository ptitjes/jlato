package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDThisExpr;
import org.jlato.tree.*;
import org.jlato.tree.expr.*;

import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;

public class SThisExpr extends SNode<SThisExpr> implements SExpr {

	public static BUTree<SThisExpr> make(BUTree<SNodeOption> classExpr) {
		return new BUTree<SThisExpr>(new SThisExpr(classExpr));
	}

	public final BUTree<SNodeOption> classExpr;

	public SThisExpr(BUTree<SNodeOption> classExpr) {
		this.classExpr = classExpr;
	}

	@Override
	public Kind kind() {
		return Kind.ThisExpr;
	}

	public BUTree<SNodeOption> classExpr() {
		return classExpr;
	}

	public SThisExpr withClassExpr(BUTree<SNodeOption> classExpr) {
		return new SThisExpr(classExpr);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SThisExpr> location) {
		return new TDThisExpr(location);
	}

	@Override
	public LexicalShape shape() {
		return shape;
	}

	@Override
	public STraversal firstChild() {
		return CLASS_EXPR;
	}

	@Override
	public STraversal lastChild() {
		return CLASS_EXPR;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SThisExpr state = (SThisExpr) o;
		if (!classExpr.equals(state.classExpr))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + classExpr.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SThisExpr, SNodeOption, NodeOption<Expr>> CLASS_EXPR = new STypeSafeTraversal<SThisExpr, SNodeOption, NodeOption<Expr>>() {

		@Override
		public BUTree<?> doTraverse(SThisExpr state) {
			return state.classExpr;
		}

		@Override
		public SThisExpr doRebuildParentState(SThisExpr state, BUTree<SNodeOption> child) {
			return state.withClassExpr(child);
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
			child(CLASS_EXPR, when(some(), composite(element(), token(LToken.Dot)))),
			token(LToken.This)
	);
}
