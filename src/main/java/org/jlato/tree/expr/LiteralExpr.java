package org.jlato.tree.expr;

import org.jlato.internal.bu.LLiteral;
import org.jlato.internal.bu.SLeaf;
import org.jlato.tree.Expr;
import org.jlato.tree.Tree;

public class LiteralExpr<T> extends Expr {

	public final static Kind<Tree> kind = new Kind<Tree>();

	@SuppressWarnings("unchecked")
	public static <T> Kind<T> kind() {
		return (Kind<T>) kind;
	}

	public static class Kind<T> implements Tree.Kind {
		public Tree instantiate(SLocation location) {
			return new LiteralExpr<T>(location);
		}
	}

	protected LiteralExpr(SLocation location) {
		super(location);
	}

	public LiteralExpr(LLiteral<T> literal) {
		super(new SLocation(new SLeaf(kind, literal)));
	}

	public LiteralExpr(Class<T> literalClass, T literalValue) {
		super(new SLocation(new SLeaf(kind, LLiteral.from(literalClass, literalValue))));
	}

	@SuppressWarnings("unchecked")
	public T value() {
		return ((LLiteral<T>) location.leafToken()).value();
	}

	@SuppressWarnings("unchecked")
	public LiteralExpr withValue(T value) {
		final Class<T> literalClass = ((LLiteral<T>) location.leafToken()).literalClass();
		return location.leafWithToken(LLiteral.from(literalClass, value));
	}
}
