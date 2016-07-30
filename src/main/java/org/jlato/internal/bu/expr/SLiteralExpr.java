package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SProperty;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STypeSafeProperty;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDLiteralExpr;
import org.jlato.internal.parser.TokenType;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;

import java.util.Arrays;

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for a literal expression.
 */
public class SLiteralExpr extends SNode<SLiteralExpr> implements SExpr {

	/**
	 * Creates a <code>BUTree</code> with a new literal expression.
	 *
	 * @param literalClass  the literal class child <code>BUTree</code>.
	 * @param literalString the literal string child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with a literal expression.
	 */
	public static BUTree<SLiteralExpr> make(Class<?> literalClass, String literalString) {
		return new BUTree<SLiteralExpr>(new SLiteralExpr(literalClass, literalString));
	}

	/**
	 * The literal class of this literal expression state.
	 */
	public final Class<?> literalClass;

	/**
	 * The literal string of this literal expression state.
	 */
	public final String literalString;

	/**
	 * Constructs a literal expression state.
	 *
	 * @param literalClass  the literal class child <code>BUTree</code>.
	 * @param literalString the literal string child <code>BUTree</code>.
	 */
	public SLiteralExpr(Class<?> literalClass, String literalString) {
		this.literalClass = literalClass;
		this.literalString = literalString;
	}

	/**
	 * Returns the kind of this literal expression.
	 *
	 * @return the kind of this literal expression.
	 */
	@Override
	public Kind kind() {
		return Kind.LiteralExpr;
	}

	/**
	 * Replaces the literal class of this literal expression state.
	 *
	 * @param literalClass the replacement for the literal class of this literal expression state.
	 * @return the resulting mutated literal expression state.
	 */
	public SLiteralExpr withLiteralClass(Class<?> literalClass) {
		return new SLiteralExpr(literalClass, literalString);
	}

	/**
	 * Replaces the literal string of this literal expression state.
	 *
	 * @param literalString the replacement for the literal string of this literal expression state.
	 * @return the resulting mutated literal expression state.
	 */
	public SLiteralExpr withLiteralString(String literalString) {
		return new SLiteralExpr(literalClass, literalString);
	}

	/**
	 * Builds a literal expression facade for the specified literal expression <code>TDLocation</code>.
	 *
	 * @param location the literal expression <code>TDLocation</code>.
	 * @return a literal expression facade for the specified literal expression <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SLiteralExpr> location) {
		return new TDLiteralExpr(location);
	}

	/**
	 * Returns the shape for this literal expression state.
	 *
	 * @return the shape for this literal expression state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the properties for this literal expression state.
	 *
	 * @return the properties for this literal expression state.
	 */
	@Override
	public Iterable<SProperty> allProperties() {
		return Arrays.<SProperty>asList(LITERAL_CLASS, LITERAL_STRING);
	}

	/**
	 * Returns the first child traversal for this literal expression state.
	 *
	 * @return the first child traversal for this literal expression state.
	 */
	@Override
	public STraversal firstChild() {
		return null;
	}

	/**
	 * Returns the last child traversal for this literal expression state.
	 *
	 * @return the last child traversal for this literal expression state.
	 */
	@Override
	public STraversal lastChild() {
		return null;
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
		SLiteralExpr state = (SLiteralExpr) o;
		if (literalClass == null ? state.literalClass != null : !literalClass.equals(state.literalClass))
			return false;
		if (literalString == null ? state.literalString != null : !literalString.equals(state.literalString))
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
		if (literalClass != null) result = 37 * result + literalClass.hashCode();
		if (literalString != null) result = 37 * result + literalString.hashCode();
		return result;
	}

	public static STypeSafeProperty<SLiteralExpr, Class<?>> LITERAL_CLASS = new STypeSafeProperty<SLiteralExpr, Class<?>>() {

		@Override
		public Class<?> doRetrieve(SLiteralExpr state) {
			return state.literalClass;
		}

		@Override
		public SLiteralExpr doRebuildParentState(SLiteralExpr state, Class<?> value) {
			return state.withLiteralClass(value);
		}
	};

	public static STypeSafeProperty<SLiteralExpr, String> LITERAL_STRING = new STypeSafeProperty<SLiteralExpr, String>() {

		@Override
		public String doRetrieve(SLiteralExpr state) {
			return state.literalString;
		}

		@Override
		public SLiteralExpr doRebuildParentState(SLiteralExpr state, String value) {
			return state.withLiteralString(value);
		}
	};

	public static final LexicalShape shape = token(new LSToken.Provider() {
		public LToken tokenFor(BUTree tree) {
			final String literalString = ((SLiteralExpr) tree.state).literalString;
			return new LToken(0, literalString); // TODO Fix
		}
	});
}
