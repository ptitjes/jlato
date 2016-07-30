package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.type.SType;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDTypeExpr;
import org.jlato.internal.parser.TokenType;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for a type expression.
 */
public class STypeExpr extends SNode<STypeExpr> implements SExpr {

	/**
	 * Creates a <code>BUTree</code> with a new type expression.
	 *
	 * @param type the type child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with a type expression.
	 */
	public static BUTree<STypeExpr> make(BUTree<? extends SType> type) {
		return new BUTree<STypeExpr>(new STypeExpr(type));
	}

	/**
	 * The type of this type expression state.
	 */
	public final BUTree<? extends SType> type;

	/**
	 * Constructs a type expression state.
	 *
	 * @param type the type child <code>BUTree</code>.
	 */
	public STypeExpr(BUTree<? extends SType> type) {
		this.type = type;
	}

	/**
	 * Returns the kind of this type expression.
	 *
	 * @return the kind of this type expression.
	 */
	@Override
	public Kind kind() {
		return Kind.TypeExpr;
	}

	/**
	 * Replaces the type of this type expression state.
	 *
	 * @param type the replacement for the type of this type expression state.
	 * @return the resulting mutated type expression state.
	 */
	public STypeExpr withType(BUTree<? extends SType> type) {
		return new STypeExpr(type);
	}

	/**
	 * Builds a type expression facade for the specified type expression <code>TDLocation</code>.
	 *
	 * @param location the type expression <code>TDLocation</code>.
	 * @return a type expression facade for the specified type expression <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<STypeExpr> location) {
		return new TDTypeExpr(location);
	}

	/**
	 * Returns the shape for this type expression state.
	 *
	 * @return the shape for this type expression state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this type expression state.
	 *
	 * @return the first child traversal for this type expression state.
	 */
	@Override
	public STraversal firstChild() {
		return TYPE;
	}

	/**
	 * Returns the last child traversal for this type expression state.
	 *
	 * @return the last child traversal for this type expression state.
	 */
	@Override
	public STraversal lastChild() {
		return TYPE;
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
		STypeExpr state = (STypeExpr) o;
		if (type == null ? state.type != null : !type.equals(state.type))
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
		if (type != null) result = 37 * result + type.hashCode();
		return result;
	}

	public static STypeSafeTraversal<STypeExpr, SType, Type> TYPE = new STypeSafeTraversal<STypeExpr, SType, Type>() {

		@Override
		public BUTree<?> doTraverse(STypeExpr state) {
			return state.type;
		}

		@Override
		public STypeExpr doRebuildParentState(STypeExpr state, BUTree<SType> child) {
			return state.withType(child);
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

	public static final LexicalShape shape = child(TYPE);
}
