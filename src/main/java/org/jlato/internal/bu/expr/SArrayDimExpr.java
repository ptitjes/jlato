package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDArrayDimExpr;
import org.jlato.tree.*;
import org.jlato.tree.expr.*;

import static org.jlato.internal.shapes.LexicalShape.*;

public class SArrayDimExpr extends SNode<SArrayDimExpr> implements STree {

	public static BUTree<SArrayDimExpr> make(BUTree<SNodeList> annotations, BUTree<? extends SExpr> expr) {
		return new BUTree<SArrayDimExpr>(new SArrayDimExpr(annotations, expr));
	}

	public final BUTree<SNodeList> annotations;

	public final BUTree<? extends SExpr> expr;

	public SArrayDimExpr(BUTree<SNodeList> annotations, BUTree<? extends SExpr> expr) {
		this.annotations = annotations;
		this.expr = expr;
	}

	@Override
	public Kind kind() {
		return Kind.ArrayDimExpr;
	}

	public BUTree<SNodeList> annotations() {
		return annotations;
	}

	public SArrayDimExpr withAnnotations(BUTree<SNodeList> annotations) {
		return new SArrayDimExpr(annotations, expr);
	}

	public BUTree<? extends SExpr> expr() {
		return expr;
	}

	public SArrayDimExpr withExpr(BUTree<? extends SExpr> expr) {
		return new SArrayDimExpr(annotations, expr);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SArrayDimExpr> location) {
		return new TDArrayDimExpr(location);
	}

	@Override
	public LexicalShape shape() {
		return shape;
	}

	@Override
	public STraversal firstChild() {
		return ANNOTATIONS;
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
		SArrayDimExpr state = (SArrayDimExpr) o;
		if (!annotations.equals(state.annotations))
			return false;
		if (expr == null ? state.expr != null : !expr.equals(state.expr))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + annotations.hashCode();
		if (expr != null) result = 37 * result + expr.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SArrayDimExpr, SNodeList, NodeList<AnnotationExpr>> ANNOTATIONS = new STypeSafeTraversal<SArrayDimExpr, SNodeList, NodeList<AnnotationExpr>>() {

		@Override
		public BUTree<?> doTraverse(SArrayDimExpr state) {
			return state.annotations;
		}

		@Override
		public SArrayDimExpr doRebuildParentState(SArrayDimExpr state, BUTree<SNodeList> child) {
			return state.withAnnotations(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return EXPR;
		}
	};

	public static STypeSafeTraversal<SArrayDimExpr, SExpr, Expr> EXPR = new STypeSafeTraversal<SArrayDimExpr, SExpr, Expr>() {

		@Override
		public BUTree<?> doTraverse(SArrayDimExpr state) {
			return state.expr;
		}

		@Override
		public SArrayDimExpr doRebuildParentState(SArrayDimExpr state, BUTree<SExpr> child) {
			return state.withExpr(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return ANNOTATIONS;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(ANNOTATIONS, org.jlato.internal.bu.expr.SAnnotationExpr.singleLineAnnotationsShapeWithSpaceBefore),
			token(LToken.BracketLeft), child(EXPR), token(LToken.BracketRight)
	);

	public static final LexicalShape listShape = list();
}
