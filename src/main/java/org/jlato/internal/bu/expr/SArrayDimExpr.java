package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDArrayDimExpr;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.expr.Expr;

import static org.jlato.internal.shapes.LexicalShape.*;

public class SArrayDimExpr extends SNodeState<SArrayDimExpr> implements STreeState {

	public static STree<SArrayDimExpr> make(STree<SNodeListState> annotations, STree<? extends SExpr> expr) {
		return new STree<SArrayDimExpr>(new SArrayDimExpr(annotations, expr));
	}

	public final STree<SNodeListState> annotations;

	public final STree<? extends SExpr> expr;

	public SArrayDimExpr(STree<SNodeListState> annotations, STree<? extends SExpr> expr) {
		this.annotations = annotations;
		this.expr = expr;
	}

	@Override
	public Kind kind() {
		return Kind.ArrayDimExpr;
	}

	public STree<SNodeListState> annotations() {
		return annotations;
	}

	public SArrayDimExpr withAnnotations(STree<SNodeListState> annotations) {
		return new SArrayDimExpr(annotations, expr);
	}

	public STree<? extends SExpr> expr() {
		return expr;
	}

	public SArrayDimExpr withExpr(STree<? extends SExpr> expr) {
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

	public static STypeSafeTraversal<SArrayDimExpr, SNodeListState, NodeList<AnnotationExpr>> ANNOTATIONS = new STypeSafeTraversal<SArrayDimExpr, SNodeListState, NodeList<AnnotationExpr>>() {

		@Override
		public STree<?> doTraverse(SArrayDimExpr state) {
			return state.annotations;
		}

		@Override
		public SArrayDimExpr doRebuildParentState(SArrayDimExpr state, STree<SNodeListState> child) {
			return state.withAnnotations(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return EXPR;
		}
	};

	public static STypeSafeTraversal<SArrayDimExpr, SExpr, Expr> EXPR = new STypeSafeTraversal<SArrayDimExpr, SExpr, Expr>() {

		@Override
		public STree<?> doTraverse(SArrayDimExpr state) {
			return state.expr;
		}

		@Override
		public SArrayDimExpr doRebuildParentState(SArrayDimExpr state, STree<SExpr> child) {
			return state.withExpr(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return ANNOTATIONS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(ANNOTATIONS, org.jlato.internal.bu.expr.SAnnotationExpr.singleLineAnnotationsShapeWithSpaceBefore),
			token(LToken.BracketLeft), child(EXPR), token(LToken.BracketRight)
	);

	public static final LexicalShape listShape = list();
}
