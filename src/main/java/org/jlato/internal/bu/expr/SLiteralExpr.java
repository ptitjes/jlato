package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.*;
import org.jlato.internal.shapes.LSToken;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDLiteralExpr;
import org.jlato.tree.*;

import java.util.Arrays;

import static org.jlato.internal.shapes.LexicalShape.token;

public class SLiteralExpr extends SNodeState<SLiteralExpr> implements SExpr {

	public static STree<SLiteralExpr> make(Class<?> literalClass, String literalString) {
		return new STree<SLiteralExpr>(new SLiteralExpr(literalClass, literalString));
	}

	public final Class<?> literalClass;

	public final String literalString;

	public SLiteralExpr(Class<?> literalClass, String literalString) {
		this.literalClass = literalClass;
		this.literalString = literalString;
	}

	@Override
	public Kind kind() {
		return Kind.LiteralExpr;
	}

	public Class<?> literalClass() {
		return literalClass;
	}

	public SLiteralExpr withLiteralClass(Class<?> literalClass) {
		return new SLiteralExpr(literalClass, literalString);
	}

	public String literalString() {
		return literalString;
	}

	public SLiteralExpr withLiteralString(String literalString) {
		return new SLiteralExpr(literalClass, literalString);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SLiteralExpr> location) {
		return new TDLiteralExpr(location);
	}

	@Override
	public LexicalShape shape() {
		return shape;
	}

	@Override
	public Iterable<SProperty> allProperties() {
		return Arrays.<SProperty>asList(LITERAL_CLASS, LITERAL_STRING);
	}

	@Override
	public STraversal firstChild() {
		return null;
	}

	@Override
	public STraversal lastChild() {
		return null;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SLiteralExpr state = (SLiteralExpr) o;
		if (literalClass == null ? state.literalClass != null : !literalClass.equals(state.literalClass))
			return false;
		if (literalString == null ? state.literalString != null : !literalString.equals(state.literalString))
			return false;
		return true;
	}

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
		public LToken tokenFor(STree tree) {
			final String literalString = ((SLiteralExpr) tree.state).literalString;
			return new LToken(0, literalString); // TODO Fix
		}
	});
}
