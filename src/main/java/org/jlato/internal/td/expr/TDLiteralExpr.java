package org.jlato.internal.td.expr;

import org.jlato.internal.bu.Literals;
import org.jlato.internal.bu.expr.SLiteralExpr;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.expr.LiteralExpr;

public class TDLiteralExpr<T> extends TDTree<SLiteralExpr, Expr, LiteralExpr<T>> implements LiteralExpr<T> {

	public Kind kind() {
		return Kind.LiteralExpr;
	}

	public TDLiteralExpr(SLocation<SLiteralExpr> location) {
		super(location);
	}

	public TDLiteralExpr(Class<?> literalClass, String literalString) {
		super(new SLocation<SLiteralExpr>(SLiteralExpr.make(literalClass, literalString)));
	}

	@SuppressWarnings("unchecked")
	public Class<T> literalClass() {
		return (Class<T>) location.safeProperty(SLiteralExpr.LITERAL_CLASS);
	}

	public String literalString() {
		return location.safeProperty(SLiteralExpr.LITERAL_STRING);
	}

	public T value() {
		return (T) Literals.valueFor(literalClass(), literalString());
	}
}
