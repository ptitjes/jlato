/*
 * Copyright (C) 2015-2016 Didier Villevalois.
 *
 * This file is part of JLaTo.
 *
 * JLaTo is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * JLaTo is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JLaTo.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.jlato.internal.bu;

/**
 * @author Didier Villevalois
 */
public final class Literals {

	private Literals() {
	}

	public static <T> String from(Class<T> literalClass, T literalValue) {
		if (Void.class.isAssignableFrom(literalClass)) {
			if (literalValue != null) {
				throw new IllegalArgumentException();
			}
			return LToken.Null.string;

		} else {
			if (!literalClass.isInstance(literalValue)) {
				throw new IllegalArgumentException();
			}

			if (Boolean.class.isAssignableFrom(literalClass)) {
				if (Boolean.TRUE.equals(literalValue))
					return LToken.True.string;
				else
					return LToken.False.string;

			} else if (Character.class.isAssignableFrom(literalClass)) {
				char c = (char) (Character) literalValue;
				return "'" + escapeChar(c) + "'";

			} else if (Integer.class.isAssignableFrom(literalClass)) {
				return literalValue.toString();

			} else if (Long.class.isAssignableFrom(literalClass)) {
				return literalValue.toString() + "L";

			} else if (Float.class.isAssignableFrom(literalClass)) {
				return literalValue.toString() + "F";

			} else if (Double.class.isAssignableFrom(literalClass)) {
				return literalValue.toString();

			} else if (String.class.isAssignableFrom(literalClass)) {
				return "\"" + escapeString((String) literalValue) + "\"";

			} else {
				throw new IllegalArgumentException();
			}
		}
	}

	public static String escapeString(String string) {
		StringBuilder buffer = new StringBuilder();
		char[] chars = string.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			switch (c) {
				case '\t':
					buffer.append("\\t");
					break;
				case '\b':
					buffer.append("\\b");
					break;
				case '\n':
					buffer.append("\\n");
					break;
				case '\r':
					buffer.append("\\r");
					break;
				case '\f':
					buffer.append("\\f");
					break;
				case '\'':
					buffer.append("\\'");
					break;
				case '\"':
					buffer.append("\\\"");
					break;
				case '\\':
					buffer.append("\\\\");
					break;
				default:
					buffer.append(c);
			}
		}
		return buffer.toString();
	}

	public static String escapeChar(char c) {
		switch (c) {
			case '\t':
				return "\\t";
			case '\b':
				return "\\b";
			case '\n':
				return "\\n";
			case '\r':
				return "\\r";
			case '\f':
				return "\\f";
			case '\'':
				return "\\'";
			case '\"':
				return "\\\"";
			case '\\':
				return "\\\\";
			default:
				return "" + c;
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T valueFor(Class<T> literalClass, String literalString) {
		if (Void.class.isAssignableFrom(literalClass)) {
			if (!literalString.equals("null"))
				throw new IllegalArgumentException();

			return null;

		} else if (Boolean.class.isAssignableFrom(literalClass)) {
			if (literalString.equals("true"))
				return (T) Boolean.TRUE;
			else if (literalString.equals("false"))
				return (T) Boolean.FALSE;
			else throw new IllegalArgumentException();

		} else if (Character.class.isAssignableFrom(literalClass)) {
			return (T) unEscapeChar(literalString);

		} else if (Integer.class.isAssignableFrom(literalClass)) {
			literalString = removeUnderscores(literalString);

			if (literalString.startsWith("0x") || literalString.startsWith("0X"))
				return (T) Integer.valueOf(removePrefix(literalString, 2), 16);
			else if (literalString.startsWith("0b") || literalString.startsWith("0B"))
				return (T) Integer.valueOf(removePrefix(literalString, 2), 2);
			else if (literalString.startsWith("0") || literalString.startsWith("0"))
				return (T) Integer.valueOf(removePrefix(literalString, 1), 8);
			else return (T) Integer.valueOf(literalString);

		} else if (Long.class.isAssignableFrom(literalClass)) {
			literalString = removeUnderscores(literalString);
			literalString = removeSuffix(literalString, 1);

			if (literalString.startsWith("0x") || literalString.startsWith("0X"))
				return (T) Long.valueOf(removePrefix(literalString, 2), 16);
			else if (literalString.startsWith("0b") || literalString.startsWith("0B"))
				return (T) Long.valueOf(removePrefix(literalString, 2), 2);
			else if (literalString.startsWith("0") || literalString.startsWith("0"))
				return (T) Long.valueOf(removePrefix(literalString, 1), 8);
			return (T) Long.valueOf(literalString);

		} else if (Float.class.isAssignableFrom(literalClass)) {
			return (T) Float.valueOf(literalString);

		} else if (Double.class.isAssignableFrom(literalClass)) {
			return (T) Double.valueOf(literalString);

		} else if (String.class.isAssignableFrom(literalClass)) {
			return (T) unEscapeString(literalString);

		} else {
			throw new IllegalArgumentException("Unknown literal class");
		}
	}

	private static String removePrefix(String string, int length) {
		return string.substring(length);
	}

	private static String removeSuffix(String string, int length) {
		return string.substring(0, string.length() - length);
	}

	private static String removeUnderscores(String literalString) {
		literalString = literalString.replace("_", "");
		return literalString;
	}

	public static Character unEscapeChar(String literalString) {
		char[] chars = literalString.toCharArray();
		int length = chars.length - 1;

		if (length - 1 <= 0 || !(chars[0] == '\'' && chars[length] == '\''))
			throw new IllegalArgumentException("Illegal character literal");

		if (escapedCharLength(chars, 1, length) + 1 != length)
			throw new IllegalArgumentException("Illegal character literal");

		return unEscapeChar(chars, 1, length);
	}

	public static String unEscapeString(String literalString) {
		char[] chars = literalString.toCharArray();
		int length = chars.length - 1;

		if (!(chars[0] == '\"' && chars[length] == '\"'))
			throw new IllegalArgumentException("Illegal string literal");

		StringBuilder buffer = new StringBuilder();
		for (int i = 1; i < length; ) {
			buffer.append(unEscapeChar(chars, i, length));
			i += escapedCharLength(chars, i, length);
		}
		return buffer.toString();
	}

	private static char unEscapeChar(char[] chars, int offset, int length) {
		switch (chars[offset]) {
			case '\\':
				if (length == offset + 1) {
					throw new IllegalArgumentException("Unknown char escape '" + chars[offset] + "'");
				}
				switch (chars[offset + 1]) {
					case 't':
						return '\t';
					case 'b':
						return '\b';
					case 'n':
						return '\n';
					case 'r':
						return '\r';
					case 'f':
						return '\f';
					case '\'':
						return '\'';
					case '"':
						return '\"';
					case '\\':
						return '\\';
					default:
						int codePoint;
						boolean firstZeroToThree = false;
						switch (chars[offset + 1]) {
							case '0':
							case '1':
							case '2':
							case '3':
								firstZeroToThree = true;
							case '4':
							case '5':
							case '6':
							case '7':
								codePoint = Character.digit(chars[offset + 1], 8);
								if (length > offset + 2) {
									switch (chars[offset + 2]) {
										case '0':
										case '1':
										case '2':
										case '3':
										case '4':
										case '5':
										case '6':
										case '7':
											codePoint = codePoint * 8 + Character.digit(chars[offset + 2], 8);
											if (firstZeroToThree && length > offset + 3) {
												switch (chars[offset + 3]) {
													case '0':
													case '1':
													case '2':
													case '3':
													case '4':
													case '5':
													case '6':
													case '7':
														codePoint = codePoint * 8 + Character.digit(chars[offset + 3], 8);
													default:
												}
											}
										default:
									}
								}
								return Character.toChars(codePoint)[0];
							default:
						}

						throw new IllegalArgumentException("Unknown char escape '" + chars[offset] + chars[offset + 1] + "'");
				}
			default:
				return chars[offset];
		}
	}

	private static char escapedCharLength(char[] chars, int offset, int length) {
		switch (chars[offset]) {
			case '\\':
				if (length == offset + 1) {
					throw new IllegalArgumentException("Unknown char escape '" + chars[offset] + "'");
				}
				switch (chars[offset + 1]) {
					case 't':
						return 2;
					case 'b':
						return 2;
					case 'n':
						return 2;
					case 'r':
						return 2;
					case 'f':
						return 2;
					case '\'':
						return 2;
					case '"':
						return 2;
					case '\\':
						return 2;
					default:
						boolean firstZeroToThree = false;
						switch (chars[offset + 1]) {
							case '0':
							case '1':
							case '2':
							case '3':
								firstZeroToThree = true;
							case '4':
							case '5':
							case '6':
							case '7':
								if (length > offset + 2) {
									switch (chars[offset + 2]) {
										case '0':
										case '1':
										case '2':
										case '3':
										case '4':
										case '5':
										case '6':
										case '7':
											if (firstZeroToThree && length > offset + 3) {
												switch (chars[offset + 3]) {
													case '0':
													case '1':
													case '2':
													case '3':
													case '4':
													case '5':
													case '6':
													case '7':
														return 4;
													default:
												}
											}
											return 3;
										default:
									}
								}
								return 2;
							default:
						}
						throw new IllegalArgumentException("Unknown char escape '" + chars[offset] + chars[offset + 1] + "'");
				}
			default:
				return 1;
		}
	}
}
