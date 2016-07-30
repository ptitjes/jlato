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

import org.jlato.internal.parser.TokenType;

/**
 * @author Didier Villevalois
 */
public class WToken {

	public static WToken newLine() {
		return new WToken(TokenType.NEWLINE, "\n");
	}

	public static WToken singleLineComment(String image) {
		return new WToken(TokenType.SINGLE_LINE_COMMENT, image);
	}

	public static WToken multiLineComment(String image) {
		return new WToken(TokenType.MULTI_LINE_COMMENT, image);
	}

	public static WToken javaDocComment(String image) {
		return new WToken(TokenType.JAVA_DOC_COMMENT, image);
	}

	public static WToken whitespace(String image) {
		return new WToken(TokenType.WHITESPACE, image);
	}

	public final int kind;
	public final String string;

	public WToken(int kind, String string) {
		switch (kind) {
			case TokenType.SINGLE_LINE_COMMENT:
			case TokenType.MULTI_LINE_COMMENT:
			case TokenType.JAVA_DOC_COMMENT:
			case TokenType.NEWLINE:
			case TokenType.WHITESPACE:
				break;
			default:
				throw new IllegalArgumentException("Tokens are supposed to be meaningless");
		}

		this.kind = kind;
		this.string = string;
	}

	@Override
	public String toString() {
		return string.replace("\n", "\\n").replace("\t", "\\t");
	}
}
