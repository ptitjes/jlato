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
