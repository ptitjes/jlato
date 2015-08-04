package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.SProperty;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.bu.STypeSafeProperty;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.expr.TDArrayInitializerExpr;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;

import java.util.Collections;

import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;
import static org.jlato.printer.IndentationConstraint.*;
import static org.jlato.printer.SpacingConstraint.*;

public class SArrayInitializerExpr extends SNodeState<SArrayInitializerExpr> implements SExpr {

	public static STree<SArrayInitializerExpr> make(STree<SNodeListState> values, boolean trailingComma) {
		return new STree<SArrayInitializerExpr>(new SArrayInitializerExpr(values, trailingComma));
	}

	public final STree<SNodeListState> values;

	public final boolean trailingComma;

	public SArrayInitializerExpr(STree<SNodeListState> values, boolean trailingComma) {
		this.values = values;
		this.trailingComma = trailingComma;
	}

	@Override
	public Kind kind() {
		return Kind.ArrayInitializerExpr;
	}

	public STree<SNodeListState> values() {
		return values;
	}

	public SArrayInitializerExpr withValues(STree<SNodeListState> values) {
		return new SArrayInitializerExpr(values, trailingComma);
	}

	public boolean trailingComma() {
		return trailingComma;
	}

	public SArrayInitializerExpr withTrailingComma(boolean trailingComma) {
		return new SArrayInitializerExpr(values, trailingComma);
	}

	@Override
	protected Tree doInstantiate(SLocation<SArrayInitializerExpr> location) {
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

	public static STypeSafeTraversal<SArrayInitializerExpr, SNodeListState, NodeList<Expr>> VALUES = new STypeSafeTraversal<SArrayInitializerExpr, SNodeListState, NodeList<Expr>>() {

		@Override
		public STree<?> doTraverse(SArrayInitializerExpr state) {
			return state.values;
		}

		@Override
		public SArrayInitializerExpr doRebuildParentState(SArrayInitializerExpr state, STree<SNodeListState> child) {
			return state.withValues(child);
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
