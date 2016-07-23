package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SProperty;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STypeSafeProperty;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDArrayInitializerExpr;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;

import java.util.Collections;

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for an array initializer expression.
 */
public class SArrayInitializerExpr extends SNode<SArrayInitializerExpr> implements SExpr {

	/**
	 * Creates a <code>BUTree</code> with a new array initializer expression.
	 *
	 * @param values        the values child <code>BUTree</code>.
	 * @param trailingComma the has a trailing comma child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with an array initializer expression.
	 */
	public static BUTree<SArrayInitializerExpr> make(BUTree<SNodeList> values, boolean trailingComma) {
		return new BUTree<SArrayInitializerExpr>(new SArrayInitializerExpr(values, trailingComma));
	}

	/**
	 * The values of this array initializer expression state.
	 */
	public final BUTree<SNodeList> values;

	/**
	 * The has a trailing comma of this array initializer expression state.
	 */
	public final boolean trailingComma;

	/**
	 * Constructs an array initializer expression state.
	 *
	 * @param values        the values child <code>BUTree</code>.
	 * @param trailingComma the has a trailing comma child <code>BUTree</code>.
	 */
	public SArrayInitializerExpr(BUTree<SNodeList> values, boolean trailingComma) {
		this.values = values;
		this.trailingComma = trailingComma;
	}

	/**
	 * Returns the kind of this array initializer expression.
	 *
	 * @return the kind of this array initializer expression.
	 */
	@Override
	public Kind kind() {
		return Kind.ArrayInitializerExpr;
	}

	/**
	 * Replaces the values of this array initializer expression state.
	 *
	 * @param values the replacement for the values of this array initializer expression state.
	 * @return the resulting mutated array initializer expression state.
	 */
	public SArrayInitializerExpr withValues(BUTree<SNodeList> values) {
		return new SArrayInitializerExpr(values, trailingComma);
	}

	/**
	 * Replaces the has a trailing comma of this array initializer expression state.
	 *
	 * @param trailingComma the replacement for the has a trailing comma of this array initializer expression state.
	 * @return the resulting mutated array initializer expression state.
	 */
	public SArrayInitializerExpr withTrailingComma(boolean trailingComma) {
		return new SArrayInitializerExpr(values, trailingComma);
	}

	/**
	 * Builds an array initializer expression facade for the specified array initializer expression <code>TDLocation</code>.
	 *
	 * @param location the array initializer expression <code>TDLocation</code>.
	 * @return an array initializer expression facade for the specified array initializer expression <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SArrayInitializerExpr> location) {
		return new TDArrayInitializerExpr(location);
	}

	/**
	 * Returns the shape for this array initializer expression state.
	 *
	 * @return the shape for this array initializer expression state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the properties for this array initializer expression state.
	 *
	 * @return the properties for this array initializer expression state.
	 */
	@Override
	public Iterable<SProperty> allProperties() {
		return Collections.<SProperty>singleton(TRAILING_COMMA);
	}

	/**
	 * Returns the first child traversal for this array initializer expression state.
	 *
	 * @return the first child traversal for this array initializer expression state.
	 */
	@Override
	public STraversal firstChild() {
		return VALUES;
	}

	/**
	 * Returns the last child traversal for this array initializer expression state.
	 *
	 * @return the last child traversal for this array initializer expression state.
	 */
	@Override
	public STraversal lastChild() {
		return VALUES;
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
		SArrayInitializerExpr state = (SArrayInitializerExpr) o;
		if (!values.equals(state.values))
			return false;
		if (trailingComma != state.trailingComma)
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
			alternative(childIs(VALUES, not(empty())),
					token(LToken.BraceLeft).withSpacingAfter(space()),
					token(LToken.BraceLeft)
			),
			child(VALUES, SExpr.listShape),
			when(data(TRAILING_COMMA), token(LToken.Comma)),
			alternative(childIs(VALUES, not(empty())),
					token(LToken.BraceRight).withSpacingBefore(space()),
					token(LToken.BraceRight)
			)
	);
}
