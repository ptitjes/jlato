package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.coll.SNodeEither;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDLambdaExpr;
import org.jlato.tree.*;
import org.jlato.tree.decl.*;
import org.jlato.tree.expr.*;
import org.jlato.tree.stmt.*;

import java.util.Collections;

import static org.jlato.internal.shapes.LSCondition.data;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.space;

public class SLambdaExpr extends SNode<SLambdaExpr> implements SExpr {

	public static BUTree<SLambdaExpr> make(BUTree<SNodeList> params, boolean hasParens, BUTree<SNodeEither> body) {
		return new BUTree<SLambdaExpr>(new SLambdaExpr(params, hasParens, body));
	}

	public final BUTree<SNodeList> params;

	public final boolean hasParens;

	public final BUTree<SNodeEither> body;

	public SLambdaExpr(BUTree<SNodeList> params, boolean hasParens, BUTree<SNodeEither> body) {
		this.params = params;
		this.hasParens = hasParens;
		this.body = body;
	}

	@Override
	public Kind kind() {
		return Kind.LambdaExpr;
	}

	public BUTree<SNodeList> params() {
		return params;
	}

	public SLambdaExpr withParams(BUTree<SNodeList> params) {
		return new SLambdaExpr(params, hasParens, body);
	}

	public boolean hasParens() {
		return hasParens;
	}

	public SLambdaExpr setParens(boolean hasParens) {
		return new SLambdaExpr(params, hasParens, body);
	}

	public BUTree<SNodeEither> body() {
		return body;
	}

	public SLambdaExpr withBody(BUTree<SNodeEither> body) {
		return new SLambdaExpr(params, hasParens, body);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SLambdaExpr> location) {
		return new TDLambdaExpr(location);
	}

	@Override
	public LexicalShape shape() {
		return shape;
	}

	@Override
	public Iterable<SProperty> allProperties() {
		return Collections.<SProperty>singleton(PARENS);
	}

	@Override
	public STraversal firstChild() {
		return PARAMS;
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
		SLambdaExpr state = (SLambdaExpr) o;
		if (!params.equals(state.params))
			return false;
		if (hasParens != state.hasParens)
			return false;
		if (!body.equals(state.body))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + params.hashCode();
		result = 37 * result + (hasParens ? 1 : 0);
		result = 37 * result + body.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SLambdaExpr, SNodeList, NodeList<FormalParameter>> PARAMS = new STypeSafeTraversal<SLambdaExpr, SNodeList, NodeList<FormalParameter>>() {

		@Override
		public BUTree<?> doTraverse(SLambdaExpr state) {
			return state.params;
		}

		@Override
		public SLambdaExpr doRebuildParentState(SLambdaExpr state, BUTree<SNodeList> child) {
			return state.withParams(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return BODY;
		}
	};

	public static STypeSafeTraversal<SLambdaExpr, SNodeEither, NodeEither<Expr, BlockStmt>> BODY = new STypeSafeTraversal<SLambdaExpr, SNodeEither, NodeEither<Expr, BlockStmt>>() {

		@Override
		public BUTree<?> doTraverse(SLambdaExpr state) {
			return state.body;
		}

		@Override
		public SLambdaExpr doRebuildParentState(SLambdaExpr state, BUTree<SNodeEither> child) {
			return state.withBody(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return PARAMS;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static STypeSafeProperty<SLambdaExpr, Boolean> PARENS = new STypeSafeProperty<SLambdaExpr, Boolean>() {

		@Override
		public Boolean doRetrieve(SLambdaExpr state) {
			return state.hasParens;
		}

		@Override
		public SLambdaExpr doRebuildParentState(SLambdaExpr state, Boolean value) {
			return state.setParens(value);
		}
	};

	public static final LexicalShape shape = composite(
			when(data(PARENS), token(LToken.ParenthesisLeft)),
			child(PARAMS, SExpr.listShape),
			when(data(PARENS), token(LToken.ParenthesisRight)),
			token(LToken.Arrow).withSpacing(space(), space()),
			child(BODY, leftOrRight())
	);
}
