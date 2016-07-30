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
public class LToken {

	public static final LToken Ellipsis = new LToken(TokenType.ELLIPSIS, "...");

	public static final LToken Public = new LToken(TokenType.PUBLIC, "public");
	public static final LToken Protected = new LToken(TokenType.PROTECTED, "protected");
	public static final LToken Private = new LToken(TokenType.PRIVATE, "private");
	public static final LToken Abstract = new LToken(TokenType.ABSTRACT, "abstract");
	public static final LToken Default = new LToken(TokenType.DEFAULT, "default");
	public static final LToken Static = new LToken(TokenType.STATIC, "static");
	public static final LToken Final = new LToken(TokenType.FINAL, "final");
	public static final LToken Transient = new LToken(TokenType.TRANSIENT, "transient");
	public static final LToken Volatile = new LToken(TokenType.VOLATILE, "volatile");
	public static final LToken Synchronized = new LToken(TokenType.SYNCHRONIZED, "synchronized");
	public static final LToken Native = new LToken(TokenType.NATIVE, "native");
	public static final LToken StrictFP = new LToken(TokenType.STRICTFP, "strictfp");

	public static final LToken Package = new LToken(TokenType.PACKAGE, "package");
	public static final LToken Import = new LToken(TokenType.IMPORT, "import");

	public static final LToken Class = new LToken(TokenType.CLASS, "class");
	public static final LToken Interface = new LToken(TokenType.INTERFACE, "interface");
	public static final LToken Enum = new LToken(TokenType.ENUM, "enum");

	public static final LToken This = new LToken(TokenType.THIS, "this");
	public static final LToken Super = new LToken(TokenType.SUPER, "super");
	public static final LToken Extends = new LToken(TokenType.EXTENDS, "extends");
	public static final LToken Implements = new LToken(TokenType.IMPLEMENTS, "implements");
	public static final LToken Throws = new LToken(TokenType.THROWS, "throws");

	public static final LToken Try = new LToken(TokenType.TRY, "try");
	public static final LToken Catch = new LToken(TokenType.CATCH, "catch");
	public static final LToken Finally = new LToken(TokenType.FINALLY, "finally");
	public static final LToken Throw = new LToken(TokenType.THROW, "throw");
	public static final LToken If = new LToken(TokenType.IF, "if");
	public static final LToken Else = new LToken(TokenType.ELSE, "else");
	public static final LToken For = new LToken(TokenType.FOR, "for");
	public static final LToken Do = new LToken(TokenType.DO, "do");
	public static final LToken While = new LToken(TokenType.WHILE, "while");
	public static final LToken Continue = new LToken(TokenType.CONTINUE, "continue");
	public static final LToken Break = new LToken(TokenType.BREAK, "break");
	public static final LToken Return = new LToken(TokenType.RETURN, "return");
	public static final LToken Switch = new LToken(TokenType.SWITCH, "switch");
	public static final LToken Case = new LToken(TokenType.CASE, "case");
	public static final LToken Assert = new LToken(TokenType.ASSERT, "assert");

	public static final LToken New = new LToken(TokenType.NEW, "new");
	public static final LToken InstanceOf = new LToken(TokenType.INSTANCEOF, "instanceof");

	public static final LToken Void = new LToken(TokenType.VOID, "void");
	public static final LToken Boolean = new LToken(TokenType.BOOLEAN, "boolean");
	public static final LToken Char = new LToken(TokenType.CHAR, "char");
	public static final LToken Byte = new LToken(TokenType.BYTE, "byte");
	public static final LToken Short = new LToken(TokenType.SHORT, "short");
	public static final LToken Int = new LToken(TokenType.INT, "int");
	public static final LToken Long = new LToken(TokenType.LONG, "long");
	public static final LToken Float = new LToken(TokenType.FLOAT, "float");
	public static final LToken Double = new LToken(TokenType.DOUBLE, "double");

	public static final LToken Plus = new LToken(TokenType.PLUS, "+");
	public static final LToken Minus = new LToken(TokenType.MINUS, "-");
	public static final LToken Increment = new LToken(TokenType.INCR, "++");
	public static final LToken Decrement = new LToken(TokenType.DECR, "--");
	public static final LToken Not = new LToken(TokenType.BANG, "!");
	public static final LToken Inverse = new LToken(TokenType.TILDE, "~");
	public static final LToken Or = new LToken(TokenType.SC_OR, "||");
	public static final LToken And = new LToken(TokenType.SC_AND, "&&");
	public static final LToken BinOr = new LToken(TokenType.BIT_OR, "|");
	public static final LToken BinAnd = new LToken(TokenType.BIT_AND, "&");
	public static final LToken XOr = new LToken(TokenType.XOR, "^");
	public static final LToken Equal = new LToken(TokenType.EQ, "==");
	public static final LToken NotEqual = new LToken(TokenType.NE, "!=");
	public static final LToken Less = new LToken(TokenType.LT, "<");
	public static final LToken Greater = new LToken(TokenType.GT, ">");
	public static final LToken LessOrEqual = new LToken(TokenType.LE, "<=");
	public static final LToken GreaterOrEqual = new LToken(TokenType.GE, ">=");
	public static final LToken LShift = new LToken(TokenType.LSHIFT, "<<");
	public static final LToken RSignedShift = new LToken(TokenType.RSIGNEDSHIFT, ">>");
	public static final LToken RUnsignedShift = new LToken(TokenType.RUNSIGNEDSHIFT, ">>>");
	public static final LToken Times = new LToken(TokenType.STAR, "*");
	public static final LToken Divide = new LToken(TokenType.SLASH, "/");
	public static final LToken Remainder = new LToken(TokenType.REM, "%");
	public static final LToken QuestionMark = new LToken(TokenType.HOOK, "?");

	public static final LToken Assign = new LToken(TokenType.ASSIGN, "=");
	public static final LToken AssignPlus = new LToken(TokenType.PLUSASSIGN, "+=");
	public static final LToken AssignMinus = new LToken(TokenType.MINUSASSIGN, "-=");
	public static final LToken AssignTimes = new LToken(TokenType.STARASSIGN, "*=");
	public static final LToken AssignDivide = new LToken(TokenType.SLASHASSIGN, "/=");
	public static final LToken AssignAnd = new LToken(TokenType.ANDASSIGN, "&=");
	public static final LToken AssignOr = new LToken(TokenType.ORASSIGN, "|=");
	public static final LToken AssignXOr = new LToken(TokenType.XORASSIGN, "^=");
	public static final LToken AssignRemainder = new LToken(TokenType.REMASSIGN, "%=");
	public static final LToken AssignLShift = new LToken(TokenType.LSHIFTASSIGN, "<<=");
	public static final LToken AssignRSignedShift = new LToken(TokenType.RSIGNEDSHIFTASSIGN, ">>=");
	public static final LToken AssignRUnsignedShift = new LToken(TokenType.RUNSIGNEDSHIFTASSIGN, ">>>=");

	public static final LToken Null = new LToken(TokenType.NULL, "null");
	public static final LToken True = new LToken(TokenType.TRUE, "true");
	public static final LToken False = new LToken(TokenType.FALSE, "false");

	public static final LToken At = new LToken(TokenType.AT, "@");
	public static final LToken SemiColon = new LToken(TokenType.SEMICOLON, ";");
	public static final LToken Colon = new LToken(TokenType.COLON, ":");
	public static final LToken Comma = new LToken(TokenType.COMMA, ",");
	public static final LToken Dot = new LToken(TokenType.DOT, ".");

	public static final LToken DoubleColon = new LToken(TokenType.DOUBLECOLON, "::");
	public static final LToken Arrow = new LToken(TokenType.ARROW, "->");

	public static final LToken BracketLeft = new LToken(TokenType.LBRACKET, "[");
	public static final LToken BracketRight = new LToken(TokenType.RBRACKET, "]");
	public static final LToken BraceLeft = new LToken(TokenType.LBRACE, "{");
	public static final LToken BraceRight = new LToken(TokenType.RBRACE, "}");
	public static final LToken ParenthesisLeft = new LToken(TokenType.LPAREN, "(");
	public static final LToken ParenthesisRight = new LToken(TokenType.RPAREN, ")");

	public final int kind;
	public final String string;

	public LToken(int kind, String string) {
		this.kind = kind;
		this.string = string;
	}

	public boolean isIdentifier() {
		return kind == TokenType.IDENTIFIER;
	}

	public boolean isKeyword() {
		switch (kind) {
			case TokenType.PUBLIC:
			case TokenType.PROTECTED:
			case TokenType.PRIVATE:
			case TokenType.ABSTRACT:
			case TokenType.DEFAULT:
			case TokenType.STATIC:
			case TokenType.FINAL:
			case TokenType.TRANSIENT:
			case TokenType.VOLATILE:
			case TokenType.SYNCHRONIZED:
			case TokenType.NATIVE:
			case TokenType.STRICTFP:

			case TokenType.PACKAGE:
			case TokenType.IMPORT:

			case TokenType.CLASS:
			case TokenType.INTERFACE:
			case TokenType.ENUM:

			case TokenType.THIS:
			case TokenType.SUPER:
			case TokenType.EXTENDS:
			case TokenType.IMPLEMENTS:
			case TokenType.THROWS:

			case TokenType.TRY:
			case TokenType.CATCH:
			case TokenType.FINALLY:
			case TokenType.THROW:
			case TokenType.IF:
			case TokenType.ELSE:
			case TokenType.FOR:
			case TokenType.DO:
			case TokenType.WHILE:
			case TokenType.CONTINUE:
			case TokenType.BREAK:
			case TokenType.RETURN:
			case TokenType.SWITCH:
			case TokenType.CASE:
			case TokenType.ASSERT:

			case TokenType.NEW:
			case TokenType.INSTANCEOF:

			case TokenType.VOID:
			case TokenType.BOOLEAN:
			case TokenType.CHAR:
			case TokenType.BYTE:
			case TokenType.SHORT:
			case TokenType.INT:
			case TokenType.LONG:
			case TokenType.FLOAT:
			case TokenType.DOUBLE:

			case TokenType.NULL:
			case TokenType.TRUE:
			case TokenType.FALSE:
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
			case TokenType.PLUS:
			case TokenType.MINUS:
			case TokenType.INCR:
			case TokenType.DECR:
			case TokenType.BANG:
			case TokenType.TILDE:
			case TokenType.SC_OR:
			case TokenType.SC_AND:
			case TokenType.BIT_OR:
			case TokenType.BIT_AND:
			case TokenType.XOR:
			case TokenType.EQ:
			case TokenType.NE:
			case TokenType.LT:
			case TokenType.GT:
			case TokenType.LE:
			case TokenType.GE:
			case TokenType.LSHIFT:
			case TokenType.RSIGNEDSHIFT:
			case TokenType.RUNSIGNEDSHIFT:
			case TokenType.STAR:
			case TokenType.SLASH:
			case TokenType.REM:
			case TokenType.HOOK:

			case TokenType.ASSIGN:
			case TokenType.PLUSASSIGN:
			case TokenType.MINUSASSIGN:
			case TokenType.STARASSIGN:
			case TokenType.SLASHASSIGN:
			case TokenType.ANDASSIGN:
			case TokenType.ORASSIGN:
			case TokenType.XORASSIGN:
			case TokenType.REMASSIGN:
			case TokenType.LSHIFTASSIGN:
			case TokenType.RSIGNEDSHIFTASSIGN:
			case TokenType.RUNSIGNEDSHIFTASSIGN:
				return true;
			default:
				return false;
		}
	}

	public boolean isSeparator() {
		switch (kind) {
			case TokenType.ELLIPSIS:

			case TokenType.AT:
			case TokenType.SEMICOLON:
			case TokenType.COLON:
			case TokenType.COMMA:
			case TokenType.DOT:

			case TokenType.DOUBLECOLON:
			case TokenType.ARROW:

			case TokenType.LBRACKET:
			case TokenType.RBRACKET:
			case TokenType.LBRACE:
			case TokenType.RBRACE:
			case TokenType.LPAREN:
			case TokenType.RPAREN:
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
