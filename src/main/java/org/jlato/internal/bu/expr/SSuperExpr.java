package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.*;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDSuperExpr;
import org.jlato.tree.*;
import org.jlato.tree.expr.*;

import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;

public class SSuperExpr extends SNodeState<SSuperExpr> implements SExpr {

	public static BUTree<SSuperExpr> make(BUTree<SNodeOptionState> classExpr) {
		return new BUTree<SSuperExpr>(new SSuperExpr(classExpr));
	}

	public final BUTree<SNodeOptionState> classExpr;

	public SSuperExpr(BUTree<SNodeOptionState> classExpr) {
		this.classExpr = classExpr;
	}

	@Override
	public Kind kind() {
		return Kind.SuperExpr;
	}

	public BUTree<SNodeOptionState> classExpr() {
		return classExpr;
	}

	public SSuperExpr withClassExpr(BUTree<SNodeOptionState> classExpr) {
		return new SSuperExpr(classExpr);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SSuperExpr> location) {
		return new TDSuperExpr(location);
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
		SSuperExpr state = (SSuperExpr) o;
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

	public static STypeSafeTraversal<SSuperExpr, SNodeOptionState, NodeOption<Expr>> CLASS_EXPR = new STypeSafeTraversal<SSuperExpr, SNodeOptionState, NodeOption<Expr>>() {

		@Override
		public BUTree<?> doTraverse(SSuperExpr state) {
			return state.classExpr;
		}

		@Override
		public SSuperExpr doRebuildParentState(SSuperExpr state, BUTree<SNodeOptionState> child) {
			return state.withClassExpr(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(CLASS_EXPR, when(some(), composite(element(), token(LToken.Dot)))),
			token(LToken.Super)
	);
}
