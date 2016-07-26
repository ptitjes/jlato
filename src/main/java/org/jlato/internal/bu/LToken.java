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
public class LToken {

	public static final LToken Ellipsis = new LToken(ParserImplConstants.ELLIPSIS, "...");

	public static final LToken Public = new LToken(ParserImplConstants.PUBLIC, "public");
	public static final LToken Protected = new LToken(ParserImplConstants.PROTECTED, "protected");
	public static final LToken Private = new LToken(ParserImplConstants.PRIVATE, "private");
	public static final LToken Abstract = new LToken(ParserImplConstants.ABSTRACT, "abstract");
	public static final LToken Default = new LToken(ParserImplConstants.DEFAULT, "default");
	public static final LToken Static = new LToken(ParserImplConstants.STATIC, "static");
	public static final LToken Final = new LToken(ParserImplConstants.FINAL, "final");
	public static final LToken Transient = new LToken(ParserImplConstants.TRANSIENT, "transient");
	public static final LToken Volatile = new LToken(ParserImplConstants.VOLATILE, "volatile");
	public static final LToken Synchronized = new LToken(ParserImplConstants.SYNCHRONIZED, "synchronized");
	public static final LToken Native = new LToken(ParserImplConstants.NATIVE, "native");
	public static final LToken StrictFP = new LToken(ParserImplConstants.STRICTFP, "strictfp");

	public static final LToken Package = new LToken(ParserImplConstants.PACKAGE, "package");
	public static final LToken Import = new LToken(ParserImplConstants.IMPORT, "import");

	public static final LToken Class = new LToken(ParserImplConstants.CLASS, "class");
	public static final LToken Interface = new LToken(ParserImplConstants.INTERFACE, "interface");
	public static final LToken Enum = new LToken(ParserImplConstants.ENUM, "enum");

	public static final LToken This = new LToken(ParserImplConstants.THIS, "this");
	public static final LToken Super = new LToken(ParserImplConstants.SUPER, "super");
	public static final LToken Extends = new LToken(ParserImplConstants.EXTENDS, "extends");
	public static final LToken Implements = new LToken(ParserImplConstants.IMPLEMENTS, "implements");
	public static final LToken Throws = new LToken(ParserImplConstants.THROWS, "throws");

	public static final LToken Try = new LToken(ParserImplConstants.TRY, "try");
	public static final LToken Catch = new LToken(ParserImplConstants.CATCH, "catch");
	public static final LToken Finally = new LToken(ParserImplConstants.FINALLY, "finally");
	public static final LToken Throw = new LToken(ParserImplConstants.THROW, "throw");
	public static final LToken If = new LToken(ParserImplConstants.IF, "if");
	public static final LToken Else = new LToken(ParserImplConstants.ELSE, "else");
	public static final LToken For = new LToken(ParserImplConstants.FOR, "for");
	public static final LToken Do = new LToken(ParserImplConstants.DO, "do");
	public static final LToken While = new LToken(ParserImplConstants.WHILE, "while");
	public static final LToken Continue = new LToken(ParserImplConstants.CONTINUE, "continue");
	public static final LToken Break = new LToken(ParserImplConstants.BREAK, "break");
	public static final LToken Return = new LToken(ParserImplConstants.RETURN, "return");
	public static final LToken Switch = new LToken(ParserImplConstants.SWITCH, "switch");
	public static final LToken Case = new LToken(ParserImplConstants.CASE, "case");
	public static final LToken Assert = new LToken(ParserImplConstants.ASSERT, "assert");

	public static final LToken New = new LToken(ParserImplConstants.NEW, "new");
	public static final LToken InstanceOf = new LToken(ParserImplConstants.INSTANCEOF, "instanceof");

	public static final LToken Void = new LToken(ParserImplConstants.VOID, "void");
	public static final LToken Boolean = new LToken(ParserImplConstants.BOOLEAN, "boolean");
	public static final LToken Char = new LToken(ParserImplConstants.CHAR, "char");
	public static final LToken Byte = new LToken(ParserImplConstants.BYTE, "byte");
	public static final LToken Short = new LToken(ParserImplConstants.SHORT, "short");
	public static final LToken Int = new LToken(ParserImplConstants.INT, "int");
	public static final LToken Long = new LToken(ParserImplConstants.LONG, "long");
	public static final LToken Float = new LToken(ParserImplConstants.FLOAT, "float");
	public static final LToken Double = new LToken(ParserImplConstants.DOUBLE, "double");

	public static final LToken Plus = new LToken(ParserImplConstants.PLUS, "+");
	public static final LToken Minus = new LToken(ParserImplConstants.MINUS, "-");
	public static final LToken Increment = new LToken(ParserImplConstants.INCR, "++");
	public static final LToken Decrement = new LToken(ParserImplConstants.DECR, "--");
	public static final LToken Not = new LToken(ParserImplConstants.BANG, "!");
	public static final LToken Inverse = new LToken(ParserImplConstants.TILDE, "~");
	public static final LToken Or = new LToken(ParserImplConstants.SC_OR, "||");
	public static final LToken And = new LToken(ParserImplConstants.SC_AND, "&&");
	public static final LToken BinOr = new LToken(ParserImplConstants.BIT_OR, "|");
	public static final LToken BinAnd = new LToken(ParserImplConstants.BIT_AND, "&");
	public static final LToken XOr = new LToken(ParserImplConstants.XOR, "^");
	public static final LToken Equal = new LToken(ParserImplConstants.EQ, "==");
	public static final LToken NotEqual = new LToken(ParserImplConstants.NE, "!=");
	public static final LToken Less = new LToken(ParserImplConstants.LT, "<");
	public static final LToken Greater = new LToken(ParserImplConstants.GT, ">");
	public static final LToken LessOrEqual = new LToken(ParserImplConstants.LE, "<=");
	public static final LToken GreaterOrEqual = new LToken(ParserImplConstants.GE, ">=");
	public static final LToken LShift = new LToken(ParserImplConstants.LSHIFT, "<<");
	public static final LToken RSignedShift = new LToken(ParserImplConstants.RSIGNEDSHIFT, ">>");
	public static final LToken RUnsignedShift = new LToken(ParserImplConstants.RUNSIGNEDSHIFT, ">>>");
	public static final LToken Times = new LToken(ParserImplConstants.STAR, "*");
	public static final LToken Divide = new LToken(ParserImplConstants.SLASH, "/");
	public static final LToken Remainder = new LToken(ParserImplConstants.REM, "%");
	public static final LToken QuestionMark = new LToken(ParserImplConstants.HOOK, "?");

	public static final LToken Assign = new LToken(ParserImplConstants.ASSIGN, "=");
	public static final LToken AssignPlus = new LToken(ParserImplConstants.PLUSASSIGN, "+=");
	public static final LToken AssignMinus = new LToken(ParserImplConstants.MINUSASSIGN, "-=");
	public static final LToken AssignTimes = new LToken(ParserImplConstants.STARASSIGN, "*=");
	public static final LToken AssignDivide = new LToken(ParserImplConstants.SLASHASSIGN, "/=");
	public static final LToken AssignAnd = new LToken(ParserImplConstants.ANDASSIGN, "&=");
	public static final LToken AssignOr = new LToken(ParserImplConstants.ORASSIGN, "|=");
	public static final LToken AssignXOr = new LToken(ParserImplConstants.XORASSIGN, "^=");
	public static final LToken AssignRemainder = new LToken(ParserImplConstants.REMASSIGN, "%=");
	public static final LToken AssignLShift = new LToken(ParserImplConstants.LSHIFTASSIGN, "<<=");
	public static final LToken AssignRSignedShift = new LToken(ParserImplConstants.RSIGNEDSHIFTASSIGN, ">>=");
	public static final LToken AssignRUnsignedShift = new LToken(ParserImplConstants.RUNSIGNEDSHIFTASSIGN, ">>>=");

	public static final LToken Null = new LToken(ParserImplConstants.NULL, "null");
	public static final LToken True = new LToken(ParserImplConstants.TRUE, "true");
	public static final LToken False = new LToken(ParserImplConstants.FALSE, "false");

	public static final LToken At = new LToken(ParserImplConstants.AT, "@");
	public static final LToken SemiColon = new LToken(ParserImplConstants.SEMICOLON, ";");
	public static final LToken Colon = new LToken(ParserImplConstants.COLON, ":");
	public static final LToken Comma = new LToken(ParserImplConstants.COMMA, ",");
	public static final LToken Dot = new LToken(ParserImplConstants.DOT, ".");

	public static final LToken DoubleColon = new LToken(ParserImplConstants.DOUBLECOLON, "::");
	public static final LToken Arrow = new LToken(ParserImplConstants.ARROW, "->");

	public static final LToken BracketLeft = new LToken(ParserImplConstants.LBRACKET, "[");
	public static final LToken BracketRight = new LToken(ParserImplConstants.RBRACKET, "]");
	public static final LToken BraceLeft = new LToken(ParserImplConstants.LBRACE, "{");
	public static final LToken BraceRight = new LToken(ParserImplConstants.RBRACE, "}");
	public static final LToken ParenthesisLeft = new LToken(ParserImplConstants.LPAREN, "(");
	public static final LToken ParenthesisRight = new LToken(ParserImplConstants.RPAREN, ")");

	public final int kind;
	public final String string;

	public LToken(int kind, String string) {
		this.kind = kind;
		this.string = string;
	}

	public boolean isIdentifier() {
		return kind == ParserImplConstants.IDENTIFIER;
	}

	public boolean isKeyword() {
		switch (kind) {
			case ParserImplConstants.PUBLIC:
			case ParserImplConstants.PROTECTED:
			case ParserImplConstants.PRIVATE:
			case ParserImplConstants.ABSTRACT:
			case ParserImplConstants.DEFAULT:
			case ParserImplConstants.STATIC:
			case ParserImplConstants.FINAL:
			case ParserImplConstants.TRANSIENT:
			case ParserImplConstants.VOLATILE:
			case ParserImplConstants.SYNCHRONIZED:
			case ParserImplConstants.NATIVE:
			case ParserImplConstants.STRICTFP:

			case ParserImplConstants.PACKAGE:
			case ParserImplConstants.IMPORT:

			case ParserImplConstants.CLASS:
			case ParserImplConstants.INTERFACE:
			case ParserImplConstants.ENUM:

			case ParserImplConstants.THIS:
			case ParserImplConstants.SUPER:
			case ParserImplConstants.EXTENDS:
			case ParserImplConstants.IMPLEMENTS:
			case ParserImplConstants.THROWS:

			case ParserImplConstants.TRY:
			case ParserImplConstants.CATCH:
			case ParserImplConstants.FINALLY:
			case ParserImplConstants.THROW:
			case ParserImplConstants.IF:
			case ParserImplConstants.ELSE:
			case ParserImplConstants.FOR:
			case ParserImplConstants.DO:
			case ParserImplConstants.WHILE:
			case ParserImplConstants.CONTINUE:
			case ParserImplConstants.BREAK:
			case ParserImplConstants.RETURN:
			case ParserImplConstants.SWITCH:
			case ParserImplConstants.CASE:
			case ParserImplConstants.ASSERT:

			case ParserImplConstants.NEW:
			case ParserImplConstants.INSTANCEOF:

			case ParserImplConstants.VOID:
			case ParserImplConstants.BOOLEAN:
			case ParserImplConstants.CHAR:
			case ParserImplConstants.BYTE:
			case ParserImplConstants.SHORT:
			case ParserImplConstants.INT:
			case ParserImplConstants.LONG:
			case ParserImplConstants.FLOAT:
			case ParserImplConstants.DOUBLE:

			case ParserImplConstants.NULL:
			case ParserImplConstants.TRUE:
			case ParserImplConstants.FALSE:
				return true;

			default:
				return false;
		}
	}

	// The following methods are not used
	// Keeping them in case they become needed when moving to new parser

/*
	public boolean isOperator() {
		switch (kind) {
			case ParserImplConstants.PLUS:
			case ParserImplConstants.MINUS:
			case ParserImplConstants.INCR:
			case ParserImplConstants.DECR:
			case ParserImplConstants.BANG:
			case ParserImplConstants.TILDE:
			case ParserImplConstants.SC_OR:
			case ParserImplConstants.SC_AND:
			case ParserImplConstants.BIT_OR:
			case ParserImplConstants.BIT_AND:
			case ParserImplConstants.XOR:
			case ParserImplConstants.EQ:
			case ParserImplConstants.NE:
			case ParserImplConstants.LT:
			case ParserImplConstants.GT:
			case ParserImplConstants.LE:
			case ParserImplConstants.GE:
			case ParserImplConstants.LSHIFT:
			case ParserImplConstants.RSIGNEDSHIFT:
			case ParserImplConstants.RUNSIGNEDSHIFT:
			case ParserImplConstants.STAR:
			case ParserImplConstants.SLASH:
			case ParserImplConstants.REM:
			case ParserImplConstants.HOOK:

			case ParserImplConstants.ASSIGN:
			case ParserImplConstants.PLUSASSIGN:
			case ParserImplConstants.MINUSASSIGN:
			case ParserImplConstants.STARASSIGN:
			case ParserImplConstants.SLASHASSIGN:
			case ParserImplConstants.ANDASSIGN:
			case ParserImplConstants.ORASSIGN:
			case ParserImplConstants.XORASSIGN:
			case ParserImplConstants.REMASSIGN:
			case ParserImplConstants.LSHIFTASSIGN:
			case ParserImplConstants.RSIGNEDSHIFTASSIGN:
			case ParserImplConstants.RUNSIGNEDSHIFTASSIGN:
				return true;
			default:
				return false;
		}
	}

	public boolean isSeparator() {
		switch (kind) {
			case ParserImplConstants.ELLIPSIS:

			case ParserImplConstants.AT:
			case ParserImplConstants.SEMICOLON:
			case ParserImplConstants.COLON:
			case ParserImplConstants.COMMA:
			case ParserImplConstants.DOT:

			case ParserImplConstants.DOUBLECOLON:
			case ParserImplConstants.ARROW:

			case ParserImplConstants.LBRACKET:
			case ParserImplConstants.RBRACKET:
			case ParserImplConstants.LBRACE:
			case ParserImplConstants.RBRACE:
			case ParserImplConstants.LPAREN:
			case ParserImplConstants.RPAREN:
				return true;

			default:
				return false;
		}
	}

	public final int width() {
		return string.length();
	}

	@Override
	public String toString() {
		return string;
	}
*/
}
