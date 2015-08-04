package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeEitherState;
import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.SProperty;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.bu.STypeSafeProperty;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDLambdaExpr;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeEither;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.FormalParameter;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.stmt.BlockStmt;

import java.util.Collections;

import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.*;

public class SLambdaExpr extends SNodeState<SLambdaExpr> implements SExpr {

	public static STree<SLambdaExpr> make(STree<SNodeListState> params, boolean hasParens, STree<SNodeEitherState> body) {
		return new STree<SLambdaExpr>(new SLambdaExpr(params, hasParens, body));
	}

	public final STree<SNodeListState> params;

	public final boolean hasParens;

	public final STree<SNodeEitherState> body;

	public SLambdaExpr(STree<SNodeListState> params, boolean hasParens, STree<SNodeEitherState> body) {
		this.params = params;
		this.hasParens = hasParens;
		this.body = body;
	}

	@Override
	public Kind kind() {
		return Kind.LambdaExpr;
	}

	public STree<SNodeListState> params() {
		return params;
	}

	public SLambdaExpr withParams(STree<SNodeListState> params) {
		return new SLambdaExpr(params, hasParens, body);
	}

	public boolean hasParens() {
		return hasParens;
	}

	public SLambdaExpr setParens(boolean hasParens) {
		return new SLambdaExpr(params, hasParens, body);
	}

	public STree<SNodeEitherState> body() {
		return body;
	}

	public SLambdaExpr withBody(STree<SNodeEitherState> body) {
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

	public static STypeSafeTraversal<SLambdaExpr, SNodeListState, NodeList<FormalParameter>> PARAMS = new STypeSafeTraversal<SLambdaExpr, SNodeListState, NodeList<FormalParameter>>() {

		@Override
		public STree<?> doTraverse(SLambdaExpr state) {
			return state.params;
		}

		@Override
		public SLambdaExpr doRebuildParentState(SLambdaExpr state, STree<SNodeListState> child) {
			return state.withParams(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return BODY;
		}
	};

	public static STypeSafeTraversal<SLambdaExpr, SNodeEitherState, NodeEither<Expr, BlockStmt>> BODY = new STypeSafeTraversal<SLambdaExpr, SNodeEitherState, NodeEither<Expr, BlockStmt>>() {

		@Override
		public STree<?> doTraverse(SLambdaExpr state) {
			return state.body;
		}

		@Override
		public SLambdaExpr doRebuildParentState(SLambdaExpr state, STree<SNodeEitherState> child) {
			return state.withBody(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return PARAMS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
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
