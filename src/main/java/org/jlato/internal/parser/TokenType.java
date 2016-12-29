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
 * Token literal values and constants.
 */
public interface TokenType {

	int EOF = 0;

	int WHITESPACE = 1;

	int NEWLINE = 2;

	int SINGLE_LINE_COMMENT = 3;

	int JAVA_DOC_COMMENT = 4;

	int MULTI_LINE_COMMENT = 5;

	int ABSTRACT = 6;

	int ASSERT = 7;

	int BOOLEAN = 8;

	int BREAK = 9;

	int BYTE = 10;

	int CASE = 11;

	int CATCH = 12;

	int CHAR = 13;

	int CLASS = 14;

	int CONST = 15;

	int CONTINUE = 16;

	int DEFAULT = 17;

	int DO = 18;

	int DOUBLE = 19;

	int ELSE = 20;

	int ENUM = 21;

	int EXTENDS = 22;

	int FALSE = 23;

	int FINAL = 24;

	int FINALLY = 25;

	int FLOAT = 26;

	int FOR = 27;

	int GOTO = 28;

	int IF = 29;

	int IMPLEMENTS = 30;

	int IMPORT = 31;

	int INSTANCEOF = 32;

	int INT = 33;

	int INTERFACE = 34;

	int LONG = 35;

	int NATIVE = 36;

	int NEW = 37;

	int NULL = 38;

	int PACKAGE = 39;

	int PRIVATE = 40;

	int PROTECTED = 41;

	int PUBLIC = 42;

	int RETURN = 43;

	int SHORT = 44;

	int STATIC = 45;

	int STRICTFP = 46;

	int SUPER = 47;

	int SWITCH = 48;

	int SYNCHRONIZED = 49;

	int THIS = 50;

	int THROW = 51;

	int THROWS = 52;

	int TRANSIENT = 53;

	int TRUE = 54;

	int TRY = 55;

	int VOID = 56;

	int VOLATILE = 57;

	int WHILE = 58;

	int LONG_LITERAL = 59;

	int INTEGER_LITERAL = 60;

	int FLOAT_LITERAL = 61;

	int DOUBLE_LITERAL = 62;

	int CHARACTER_LITERAL = 63;

	int STRING_LITERAL = 64;

	int LPAREN = 65;

	int RPAREN = 66;

	int LBRACE = 67;

	int RBRACE = 68;

	int LBRACKET = 69;

	int RBRACKET = 70;

	int SEMICOLON = 71;

	int COMMA = 72;

	int DOT = 73;

	int AT = 74;

	int ASSIGN = 75;

	int LT = 76;

	int BANG = 77;

	int TILDE = 78;

	int HOOK = 79;

	int COLON = 80;

	int EQ = 81;

	int LE = 82;

	int GE = 83;

	int NE = 84;

	int SC_OR = 85;

	int SC_AND = 86;

	int INCR = 87;

	int DECR = 88;

	int PLUS = 89;

	int MINUS = 90;

	int STAR = 91;

	int SLASH = 92;

	int BIT_AND = 93;

	int BIT_OR = 94;

	int XOR = 95;

	int REM = 96;

	int LSHIFT = 97;

	int PLUSASSIGN = 98;

	int MINUSASSIGN = 99;

	int STARASSIGN = 100;

	int SLASHASSIGN = 101;

	int ANDASSIGN = 102;

	int ORASSIGN = 103;

	int XORASSIGN = 104;

	int REMASSIGN = 105;

	int LSHIFTASSIGN = 106;

	int RSIGNEDSHIFTASSIGN = 107;

	int RUNSIGNEDSHIFTASSIGN = 108;

	int ELLIPSIS = 109;

	int ARROW = 110;

	int DOUBLECOLON = 111;

	int RUNSIGNEDSHIFT = 112;

	int RSIGNEDSHIFT = 113;

	int GT = 114;

	int NODE_VARIABLE = 115;

	int NODE_LIST_VARIABLE = 116;

	int IDENTIFIER = 117;

	String[] tokenImage = {
			"<EOF>",
			"<WHITESPACE>",
			"<NEWLINE>",
			"<SINGLE_LINE_COMMENT>",
			"<JAVA_DOC_COMMENT>",
			"<MULTI_LINE_COMMENT>",
			"abstract",
			"assert",
			"boolean",
			"break",
			"byte",
			"case",
			"catch",
			"char",
			"class",
			"const",
			"continue",
			"default",
			"do",
			"double",
			"else",
			"enum",
			"extends",
			"false",
			"final",
			"finally",
			"float",
			"for",
			"goto",
			"if",
			"implements",
			"import",
			"instanceof",
			"int",
			"interface",
			"long",
			"native",
			"new",
			"null",
			"package",
			"private",
			"protected",
			"public",
			"return",
			"short",
			"static",
			"strictfp",
			"super",
			"switch",
			"synchronized",
			"this",
			"throw",
			"throws",
			"transient",
			"true",
			"try",
			"void",
			"volatile",
			"while",
			"<LONG_LITERAL>",
			"<INTEGER_LITERAL>",
			"<FLOAT_LITERAL>",
			"<DOUBLE_LITERAL>",
			"<CHARACTER_LITERAL>",
			"<STRING_LITERAL>",
			"(",
			")",
			"{",
			"}",
			"[",
			"]",
			";",
			",",
			".",
			"@",
			"=",
			"<",
			"!",
			"~",
			"?",
			":",
			"==",
			"<=",
			">=",
			"!=",
			"||",
			"&&",
			"++",
			"--",
			"+",
			"-",
			"*",
			"/",
			"&",
			"|",
			"^",
			"%",
			"<<",
			"+=",
			"-=",
			"*=",
			"/=",
			"&=",
			"|=",
			"^=",
			"%=",
			"<<=",
			">>=",
			">>>=",
			"...",
			"->",
			"::",
			">>>",
			">>",
			">",
			"<NODE_VARIABLE>",
			"<NODE_LIST_VARIABLE>",
			"<IDENTIFIER>",
	};
}
