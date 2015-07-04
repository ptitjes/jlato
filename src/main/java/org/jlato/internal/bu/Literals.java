package org.jlato.internal.bu;

import org.jlato.parser.ASTParserConstants;

/**
 * @author Didier Villevalois
 */
public class Literals {
	// TODO rework parsing and rendering

	public static <T> String from(Class<T> literalClass, T literalValue) {
		if (Void.class.isAssignableFrom(literalClass)) {
			if (literalValue != null) {
				throw new IllegalArgumentException();
			}
			return LToken.Null.string;
		} else if (Boolean.class.isAssignableFrom(literalClass)) {
			if (Boolean.TRUE.equals(literalValue))
				return LToken.True.string;
			else if (Boolean.FALSE.equals(literalValue))
				return LToken.False.string;
			else throw new IllegalStateException();
		} else if (Character.class.isAssignableFrom(literalClass)) {
			return "'" + literalValue.toString().charAt(0) + "'";
		} else if (Integer.class.isAssignableFrom(literalClass)) {
			return literalValue.toString();
		} else if (Long.class.isAssignableFrom(literalClass)) {
			return literalValue.toString() + "L";
		} else if (Float.class.isAssignableFrom(literalClass)) {
			return literalValue.toString() + "F";
		} else if (Double.class.isAssignableFrom(literalClass)) {
			return literalValue.toString() + "D";
		} else if (String.class.isAssignableFrom(literalClass)) {
			return "\"" + literalValue.toString() + "\"";
		} else {
			throw new IllegalArgumentException();
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> Class<T> literalClassFor(int tokenKind) {
		switch (tokenKind) {
			case ASTParserConstants.NULL:
				return (Class<T>) Void.class;
			case ASTParserConstants.TRUE:
			case ASTParserConstants.FALSE:
				return (Class<T>) Boolean.class;
			case ASTParserConstants.CHARACTER_LITERAL:
				return (Class<T>) Character.class;
			case ASTParserConstants.INTEGER_LITERAL:
				return (Class<T>) Integer.class;
			case ASTParserConstants.LONG_LITERAL:
				return (Class<T>) Long.class;
			case ASTParserConstants.FLOAT_LITERAL:
				return (Class<T>) Float.class;
			case ASTParserConstants.DOUBLE_LITERAL:
				return (Class<T>) Double.class;
			case ASTParserConstants.STRING_LITERAL:
				return (Class<T>) String.class;
			default:
				throw new IllegalArgumentException();
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T valueFor(Class<T> literalClass, String literalString) {
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
}
