package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SProperty;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STypeSafeProperty;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.coll.SNodeEither;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDLambdaExpr;
import org.jlato.internal.parser.TokenType;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeEither;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.FormalParameter;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.stmt.BlockStmt;

import java.util.Collections;

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for a lambda expression.
 */
public class SLambdaExpr extends SNode<SLambdaExpr> implements SExpr {

	/**
	 * Creates a <code>BUTree</code> with a new lambda expression.
	 *
	 * @param params    the parameters child <code>BUTree</code>.
	 * @param hasParens the has its arguments parenthesized child <code>BUTree</code>.
	 * @param body      the body child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with a lambda expression.
	 */
	public static BUTree<SLambdaExpr> make(BUTree<SNodeList> params, boolean hasParens, BUTree<SNodeEither> body) {
		return new BUTree<SLambdaExpr>(new SLambdaExpr(params, hasParens, body));
	}

	/**
	 * The parameters of this lambda expression state.
	 */
	public final BUTree<SNodeList> params;

	/**
	 * The has its arguments parenthesized of this lambda expression state.
	 */
	public final boolean hasParens;

	/**
	 * The body of this lambda expression state.
	 */
	public final BUTree<SNodeEither> body;

	/**
	 * Constructs a lambda expression state.
	 *
	 * @param params    the parameters child <code>BUTree</code>.
	 * @param hasParens the has its arguments parenthesized child <code>BUTree</code>.
	 * @param body      the body child <code>BUTree</code>.
	 */
	public SLambdaExpr(BUTree<SNodeList> params, boolean hasParens, BUTree<SNodeEither> body) {
		this.params = params;
		this.hasParens = hasParens;
		this.body = body;
	}

	/**
	 * Returns the kind of this lambda expression.
	 *
	 * @return the kind of this lambda expression.
	 */
	@Override
	public Kind kind() {
		return Kind.LambdaExpr;
	}

	/**
	 * Replaces the parameters of this lambda expression state.
	 *
	 * @param params the replacement for the parameters of this lambda expression state.
	 * @return the resulting mutated lambda expression state.
	 */
	public SLambdaExpr withParams(BUTree<SNodeList> params) {
		return new SLambdaExpr(params, hasParens, body);
	}

	/**
	 * Replaces the has its arguments parenthesized of this lambda expression state.
	 *
	 * @param hasParens the replacement for the has its arguments parenthesized of this lambda expression state.
	 * @return the resulting mutated lambda expression state.
	 */
	public SLambdaExpr setParens(boolean hasParens) {
		return new SLambdaExpr(params, hasParens, body);
	}

	/**
	 * Replaces the body of this lambda expression state.
	 *
	 * @param body the replacement for the body of this lambda expression state.
	 * @return the resulting mutated lambda expression state.
	 */
	public SLambdaExpr withBody(BUTree<SNodeEither> body) {
		return new SLambdaExpr(params, hasParens, body);
	}

	/**
	 * Builds a lambda expression facade for the specified lambda expression <code>TDLocation</code>.
	 *
	 * @param location the lambda expression <code>TDLocation</code>.
	 * @return a lambda expression facade for the specified lambda expression <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SLambdaExpr> location) {
		return new TDLambdaExpr(location);
	}

	/**
	 * Returns the shape for this lambda expression state.
	 *
	 * @return the shape for this lambda expression state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the properties for this lambda expression state.
	 *
	 * @return the properties for this lambda expression state.
	 */
	@Override
	public Iterable<SProperty> allProperties() {
		return Collections.<SProperty>singleton(PARENS);
	}

	/**
	 * Returns the first child traversal for this lambda expression state.
	 *
	 * @return the first child traversal for this lambda expression state.
	 */
	@Override
	public STraversal firstChild() {
		return PARAMS;
	}

	/**
	 * Returns the last child traversal for this lambda expression state.
	 *
	 * @return the last child traversal for this lambda expression state.
	 */
	@Override
	public STraversal lastChild() {
		return BODY;
	}

	/**
	 * Compares this state object to the specified object.
	 *
	 * @param o the object to compare this state with.
	 * @return <code>true</code> if the specified object is equal to this state, <code>false</code> otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SLambdaExpr state = (SLambdaExpr) o;
		if (!params.equals(state.params))
			return false;
		if (hasParens != state.hasParens)
			return false;
		if (!body.equals(state.body))
			return false;
		return true;
	}

	/**
	 * Returns a hash code for this state object.
	 *
	 * @return a hash code value for this object.
	 */
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
