package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDArrayInitializerExpr;
import org.jlato.tree.*;
import org.jlato.tree.expr.*;

import java.util.Collections;

import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class SArrayInitializerExpr extends SNode<SArrayInitializerExpr> implements SExpr {

	public static BUTree<SArrayInitializerExpr> make(BUTree<SNodeList> values, boolean trailingComma) {
		return new BUTree<SArrayInitializerExpr>(new SArrayInitializerExpr(values, trailingComma));
	}

	public final BUTree<SNodeList> values;

	public final boolean trailingComma;

	public SArrayInitializerExpr(BUTree<SNodeList> values, boolean trailingComma) {
		this.values = values;
		this.trailingComma = trailingComma;
	}

	@Override
	public Kind kind() {
		return Kind.ArrayInitializerExpr;
	}

	public BUTree<SNodeList> values() {
		return values;
	}

	public SArrayInitializerExpr withValues(BUTree<SNodeList> values) {
		return new SArrayInitializerExpr(values, trailingComma);
	}

	public boolean trailingComma() {
		return trailingComma;
	}

	public SArrayInitializerExpr withTrailingComma(boolean trailingComma) {
		return new SArrayInitializerExpr(values, trailingComma);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SArrayInitializerExpr> location) {
		return new TDArrayInitializerExpr(location);
	}

	@Override
	public LexicalShape shape() {
		return shape;
	}

	@Override
	public Iterable<SProperty> allProperties() {
		return Collections.<SProperty>singleton(TRAILING_COMMA);
	}

	@Override
	public STraversal firstChild() {
		return VALUES;
	}

	@Override
	public STraversal lastChild() {
		return VALUES;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SArrayInitializerExpr state = (SArrayInitializerExpr) o;
		if (!values.equals(state.values))
			return false;
		if (trailingComma != state.trailingComma)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + values.hashCode();
		result = 37 * result + (trailingComma ? 1 : 0);
		return result;
	}

	public static STypeSafeTraversal<SArrayInitializerExpr, SNodeList, NodeList<Expr>> VALUES = new STypeSafeTraversal<SArrayInitializerExpr, SNodeList, NodeList<Expr>>() {

		@Override
		public BUTree<?> doTraverse(SArrayInitializerExpr state) {
			return state.values;
		}

		@Override
		public SArrayInitializerExpr doRebuildParentState(SArrayInitializerExpr state, BUTree<SNodeList> child) {
			return state.withValues(child);
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

	public static STypeSafeProperty<SArrayInitializerExpr, Boolean> TRAILING_COMMA = new STypeSafeProperty<SArrayInitializerExpr, Boolean>() {

		@Override
		public Boolean doRetrieve(SArrayInitializerExpr state) {
			return state.trailingComma;
		}

		@Override
		public SArrayInitializerExpr doRebuildParentState(SArrayInitializerExpr state, Boolean value) {
			return state.withTrailingComma(value);
		}
	};

	public static final LexicalShape shape = composite(
			alternative(childIs(VALUES, not(empty())), composite(
					token(LToken.BraceLeft).withSpacingAfter(space()),
					child(VALUES, SExpr.listShape),
					when(data(TRAILING_COMMA), token(LToken.Comma)),
					token(LToken.BraceRight).withSpacingBefore(space())
			), composite(
					token(LToken.BraceLeft),
					when(data(TRAILING_COMMA), token(LToken.Comma)),
					token(LToken.BraceRight)
			))
	);
}
