/*
 * Copyright (C) 2015 Didier Villevalois.
 *
 * This file is part of JLaTo.
 *
 * JLaTo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JLaTo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JLaTo.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.jlato.internal.bu;

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

	private static String escapeString(String string) {
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

	private static String escapeChar(char c) {
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
			return null;

		} else if (Boolean.class.isAssignableFrom(literalClass)) {
			if (literalString.equals("true"))
				return (T) Boolean.TRUE;
			else if (literalString.equals("false"))
				return (T) Boolean.FALSE;
			else throw new IllegalStateException();

		} else if (Character.class.isAssignableFrom(literalClass)) {
			if (literalString.startsWith("'\\")) {
				char[] chars = literalString.toCharArray();
				return (T) Character.valueOf(unEscapeChar(chars, 1));
			} else return (T) Character.valueOf(literalString.charAt(1));

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
			throw new IllegalStateException();
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

	public static String unEscapeString(String string) {
		StringBuilder buffer = new StringBuilder();
		char[] chars = string.toCharArray();
		for (int i = 1; i < chars.length - 1; ) {
			buffer.append(unEscapeChar(chars, i));
			i += escapedCharLength(chars, i);
		}
		return buffer.toString();
	}

	public static char unEscapeChar(char[] chars, int index) {
		switch (chars[index]) {
			case '\\':
				if (chars.length == index + 1) {
					throw new IllegalArgumentException("Unknown char escape '" + chars[index] + "'");
				}
				switch (chars[index + 1]) {
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
						switch (chars[index + 1]) {
							case '0':
							case '1':
							case '2':
							case '3':
								firstZeroToThree = true;
							case '4':
							case '5':
							case '6':
							case '7':
								codePoint = Character.digit(chars[index + 1], 8);
								if (chars.length > index + 2) {
									switch (chars[index + 2]) {
										case '0':
										case '1':
										case '2':
										case '3':
										case '4':
										case '5':
										case '6':
										case '7':
											codePoint = codePoint * 8 + Character.digit(chars[index + 2], 8);
											if (chars.length > index + 3) {
												switch (chars[index + 3]) {
													case '0':
													case '1':
													case '2':
													case '3':
													case '4':
													case '5':
													case '6':
													case '7':
														if (!firstZeroToThree) {
															throw new IllegalArgumentException("Unknown char escape '" +
																	chars[index] + chars[index + 1] +
																	chars[index + 2] + chars[index + 3] + "'");
														}
														codePoint = codePoint * 8 + Character.digit(chars[index + 3], 8);
													default:
												}
											}
										default:
									}
								}
								return Character.toChars(codePoint)[0];
							default:
						}

						throw new IllegalArgumentException("Unknown char escape '" + chars[index] + chars[index + 1] + "'");
				}
			default:
				return chars[index];
		}
	}

	private static char escapedCharLength(char[] value, int index) {
		switch (value[index]) {
			case '\\':
				if (value.length < index + 1) {
					throw new IllegalArgumentException("Unknown char escape '" + value[index] + "'");
				}
				switch (value[index + 1]) {
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
						switch (value[index + 1]) {
							case '0':
							case '1':
							case '2':
							case '3':
								firstZeroToThree = true;
							case '4':
							case '5':
							case '6':
							case '7':
								if (value.length > index + 2) {
									switch (value[index + 2]) {
										case '0':
										case '1':
										case '2':
										case '3':
										case '4':
										case '5':
										case '6':
										case '7':
											if (value.length > index + 3) {
												switch (value[index + 3]) {
													case '0':
													case '1':
													case '2':
													case '3':
													case '4':
													case '5':
													case '6':
													case '7':
														if (!firstZeroToThree) {
															throw new IllegalArgumentException("Unknown char escape '" +
																	value[index] + value[index + 1] +
																	value[index + 2] + value[index + 3] + "'");
														}
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
						throw new IllegalArgumentException("Unknown char escape '" + value[index] + value[index + 1] + "'");
				}
			default:
				return 1;
		}
	}
}
