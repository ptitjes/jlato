package org.jlato.internal.bu;

/**
 * @author Didier Villevalois
 */
public class LLiteral<T> extends LToken {
	// TODO rework parsing and rendering

	public static <T> LLiteral<T> from(Class<T> literalClass, T literalValue) {
		if (Void.class.isAssignableFrom(literalClass)) {
			if (literalValue != null) {
				throw new IllegalArgumentException();
			}
			return new LLiteral<T>(literalClass, "null");
		} else if (Boolean.class.isAssignableFrom(literalClass)) {
			if (Boolean.TRUE.equals(literalValue))
				return new LLiteral<T>(literalClass, "true");
			else if (Boolean.FALSE.equals(literalValue))
				return new LLiteral<T>(literalClass, "false");
			else throw new IllegalStateException();
		} else if (Character.class.isAssignableFrom(literalClass)) {
			return new LLiteral<T>(literalClass, "'" + literalValue.toString() + "'");
		} else if (Integer.class.isAssignableFrom(literalClass)) {
			return new LLiteral<T>(literalClass, literalValue.toString());
		} else if (Long.class.isAssignableFrom(literalClass)) {
			return new LLiteral<T>(literalClass, literalValue.toString() + "L");
		} else if (Float.class.isAssignableFrom(literalClass)) {
			return new LLiteral<T>(literalClass, literalValue.toString() + "F");
		} else if (Double.class.isAssignableFrom(literalClass)) {
			return new LLiteral<T>(literalClass, literalValue.toString());
		} else if (String.class.isAssignableFrom(literalClass)) {
			return new LLiteral<T>(literalClass, "\"" + literalValue.toString() + "\"");
		} else {
			throw new IllegalStateException();
		}
	}

	private final Class<T> literalClass;
	private final String literalString;

	public LLiteral(Class<T> literalClass, String literalString) {
		this.literalClass = literalClass;
		this.literalString = literalString;
	}

	@Override
	public int width() {
		return literalString.length();
	}

	public Class<T> literalClass() {
		return literalClass;
	}

	@SuppressWarnings("unchecked")
	public T value() {
		if (Void.class.isAssignableFrom(literalClass)) {
			return null;
		} else if (Boolean.class.isAssignableFrom(literalClass)) {
			if (literalString.equals("true"))
				return (T) Boolean.TRUE;
			else if (literalString.equals("true"))
				return (T) Boolean.FALSE;
			else throw new IllegalStateException();
		} else if (Character.class.isAssignableFrom(literalClass)) {
			return (T) (Character) literalString.charAt(1);
		} else if (Integer.class.isAssignableFrom(literalClass)) {
			return (T) (Integer) Integer.parseInt(literalString);
		} else if (Long.class.isAssignableFrom(literalClass)) {
			return (T) (Long) Long.parseLong(literalString);
		} else if (Float.class.isAssignableFrom(literalClass)) {
			return (T) (Float) Float.parseFloat(literalString);
		} else if (Double.class.isAssignableFrom(literalClass)) {
			return (T) (Double) Double.parseDouble(literalString);
		} else if (String.class.isAssignableFrom(literalClass)) {
			return (T) literalString.substring(1, literalString.length() - 1);
		} else {
			throw new IllegalStateException();
		}
	}

	public LLiteral<T> withValue(T value) {
		return from(literalClass, value);
	}

	@Override
	public String toString() {
		return literalString;
	}
}
