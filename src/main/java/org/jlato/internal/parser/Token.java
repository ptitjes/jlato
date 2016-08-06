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

package org.jlato.internal.parser;

/**
 * An input Token.
 */

public class Token {

	/**
	 * An integer that describes the kind of this token.  This numbering
	 * system is determined by JavaCCParser, and a table of these numbers is
	 * stored in the file ...Constants.java.
	 */
	public final int kind;

	/**
	 * The line number of the first character of this Token.
	 */
	public final int beginLine;

	/**
	 * The column number of the first character of this Token.
	 */
	public final int beginColumn;

	/**
	 * The line number of the last character of this Token.
	 */
	public int endLine;

	/**
	 * The column number of the last character of this Token.
	 */
	public int endColumn;

	/**
	 * The string image of the token.
	 */
	public String image;

	/**
	 * Constructs a new token for the specified Image and Kind.
	 */
	public Token(int kind, String image, int beginLine, int beginColumn) {
		this.kind = kind;
		this.image = image;
		this.beginLine = beginLine;
		this.beginColumn = beginColumn;
	}

	/**
	 * Returns the image.
	 */
	public String toString() {
		return image;
	}
}
