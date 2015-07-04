package org.jlato.tree.expr;

import org.jlato.internal.bu.Literals;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeData;
import org.jlato.tree.Expr;
import org.jlato.tree.SLocation;
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

	public static LiteralExpr<Void> nullLiteral() {
		return of(Void.class, null);
	}

	public static LiteralExpr<Boolean> of(boolean value) {
		return of(Boolean.class, value);
	}

	public static LiteralExpr<Integer> of(int value) {
		return of(Integer.class, value);
	}

	public static LiteralExpr<Long> of(long value) {
		return of(Long.class, value);
	}

	public static LiteralExpr<Float> of(float value) {
		return of(Float.class, value);
	}

	public static LiteralExpr<Double> of(double value) {
		return of(Double.class, value);
	}

	public static LiteralExpr<Character> of(char value) {
		return of(Character.class, value);
	}

	public static LiteralExpr<String> of(String value) {
		return of(String.class, value);
	}

	public static <T> LiteralExpr<T> of(Class<T> literalClass, T literalValue) {
		return new LiteralExpr<T>(literalClass, Literals.from(literalClass, literalValue));
	}

	protected LiteralExpr(SLocation location) {
		super(location);
	}

	public LiteralExpr(Class<T> literalClass, String literalString) {
		super(new SLocation(new SNode(kind, new SNodeData(treesOf(), attributesOf(literalClass, literalString)))));
	}

	@SuppressWarnings("unchecked")
	public T value() {
		final Class<T> literalClass = location.nodeAttribute(CLASS);
		final String literalString = location.nodeAttribute(STRING);
		return Literals.valueFor(literalClass, literalString);
	}

	@SuppressWarnings("unchecked")
	public LiteralExpr<T> withValue(T value) {
		final Class<T> literalClass = location.nodeAttribute(CLASS);
		return location.nodeWithAttribute(STRING, Literals.from(literalClass, value));
	}

	private static final int CLASS = 0;
	private static final int STRING = 1;
}
