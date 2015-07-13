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

import org.jlato.parser.ParserImplConstants;

/**
 * @author Didier Villevalois
 */
public class WToken {

	public final int kind;
	public final String string;

	public WToken(int kind, String string) {
		this.kind = kind;
		this.string = string;
	}

	public boolean isNewLine() {
		return kind == ParserImplConstants.NEWLINE;
	}

	public boolean isSpaces() {
		return kind == ParserImplConstants.WHITESPACE;
	}

	public boolean isComment() {
		switch (kind) {
			case ParserImplConstants.SINGLE_LINE_COMMENT:
			case ParserImplConstants.MULTI_LINE_COMMENT:
			case ParserImplConstants.JAVA_DOC_COMMENT:
				return true;
		}
		return false;
	}

	public boolean isSingleLineComment() {
		return kind == ParserImplConstants.SINGLE_LINE_COMMENT;
	}

	public boolean isMultiLineComment() {
		return kind == ParserImplConstants.MULTI_LINE_COMMENT;
	}

	public boolean isJavaDocComment() {
		return kind == ParserImplConstants.JAVA_DOC_COMMENT;
	}
}
