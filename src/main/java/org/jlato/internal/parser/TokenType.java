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

  /** End of File. */
  int EOF = 0;
  /** RegularExpression Id. */
  int WHITESPACE = 1;
  /** RegularExpression Id. */
  int NEWLINE = 2;
  /** RegularExpression Id. */
  int SINGLE_LINE_COMMENT = 3;
  /** RegularExpression Id. */
  int JAVA_DOC_COMMENT = 6;
  /** RegularExpression Id. */
  int MULTI_LINE_COMMENT = 7;
  /** RegularExpression Id. */
  int ABSTRACT = 9;
  /** RegularExpression Id. */
  int ASSERT = 10;
  /** RegularExpression Id. */
  int BOOLEAN = 11;
  /** RegularExpression Id. */
  int BREAK = 12;
  /** RegularExpression Id. */
  int BYTE = 13;
  /** RegularExpression Id. */
  int CASE = 14;
  /** RegularExpression Id. */
  int CATCH = 15;
  /** RegularExpression Id. */
  int CHAR = 16;
  /** RegularExpression Id. */
  int CLASS = 17;
  /** RegularExpression Id. */
  int CONST = 18;
  /** RegularExpression Id. */
  int CONTINUE = 19;
  /** RegularExpression Id. */
  int DEFAULT = 20;
  /** RegularExpression Id. */
  int DO = 21;
  /** RegularExpression Id. */
  int DOUBLE = 22;
  /** RegularExpression Id. */
  int ELSE = 23;
  /** RegularExpression Id. */
  int ENUM = 24;
  /** RegularExpression Id. */
  int EXTENDS = 25;
  /** RegularExpression Id. */
  int FALSE = 26;
  /** RegularExpression Id. */
  int FINAL = 27;
  /** RegularExpression Id. */
  int FINALLY = 28;
  /** RegularExpression Id. */
  int FLOAT = 29;
  /** RegularExpression Id. */
  int FOR = 30;
  /** RegularExpression Id. */
  int GOTO = 31;
  /** RegularExpression Id. */
  int IF = 32;
  /** RegularExpression Id. */
  int IMPLEMENTS = 33;
  /** RegularExpression Id. */
  int IMPORT = 34;
  /** RegularExpression Id. */
  int INSTANCEOF = 35;
  /** RegularExpression Id. */
  int INT = 36;
  /** RegularExpression Id. */
  int INTERFACE = 37;
  /** RegularExpression Id. */
  int LONG = 38;
  /** RegularExpression Id. */
  int NATIVE = 39;
  /** RegularExpression Id. */
  int NEW = 40;
  /** RegularExpression Id. */
  int NULL = 41;
  /** RegularExpression Id. */
  int PACKAGE = 42;
  /** RegularExpression Id. */
  int PRIVATE = 43;
  /** RegularExpression Id. */
  int PROTECTED = 44;
  /** RegularExpression Id. */
  int PUBLIC = 45;
  /** RegularExpression Id. */
  int RETURN = 46;
  /** RegularExpression Id. */
  int SHORT = 47;
  /** RegularExpression Id. */
  int STATIC = 48;
  /** RegularExpression Id. */
  int STRICTFP = 49;
  /** RegularExpression Id. */
  int SUPER = 50;
  /** RegularExpression Id. */
  int SWITCH = 51;
  /** RegularExpression Id. */
  int SYNCHRONIZED = 52;
  /** RegularExpression Id. */
  int THIS = 53;
  /** RegularExpression Id. */
  int THROW = 54;
  /** RegularExpression Id. */
  int THROWS = 55;
  /** RegularExpression Id. */
  int TRANSIENT = 56;
  /** RegularExpression Id. */
  int TRUE = 57;
  /** RegularExpression Id. */
  int TRY = 58;
  /** RegularExpression Id. */
  int VOID = 59;
  /** RegularExpression Id. */
  int VOLATILE = 60;
  /** RegularExpression Id. */
  int WHILE = 61;
  /** RegularExpression Id. */
  int LONG_LITERAL = 62;
  /** RegularExpression Id. */
  int INTEGER_LITERAL = 63;
  /** RegularExpression Id. */
  int FLOAT_LITERAL = 68;
  /** RegularExpression Id. */
  int DOUBLE_LITERAL = 69;
  /** RegularExpression Id. */
  int CHARACTER_LITERAL = 78;
  /** RegularExpression Id. */
  int STRING_LITERAL = 79;
  /** RegularExpression Id. */
  int LPAREN = 80;
  /** RegularExpression Id. */
  int RPAREN = 81;
  /** RegularExpression Id. */
  int LBRACE = 82;
  /** RegularExpression Id. */
  int RBRACE = 83;
  /** RegularExpression Id. */
  int LBRACKET = 84;
  /** RegularExpression Id. */
  int RBRACKET = 85;
  /** RegularExpression Id. */
  int SEMICOLON = 86;
  /** RegularExpression Id. */
  int COMMA = 87;
  /** RegularExpression Id. */
  int DOT = 88;
  /** RegularExpression Id. */
  int AT = 89;
  /** RegularExpression Id. */
  int ASSIGN = 90;
  /** RegularExpression Id. */
  int LT = 91;
  /** RegularExpression Id. */
  int BANG = 92;
  /** RegularExpression Id. */
  int TILDE = 93;
  /** RegularExpression Id. */
  int HOOK = 94;
  /** RegularExpression Id. */
  int COLON = 95;
  /** RegularExpression Id. */
  int EQ = 96;
  /** RegularExpression Id. */
  int LE = 97;
  /** RegularExpression Id. */
  int GE = 98;
  /** RegularExpression Id. */
  int NE = 99;
  /** RegularExpression Id. */
  int SC_OR = 100;
  /** RegularExpression Id. */
  int SC_AND = 101;
  /** RegularExpression Id. */
  int INCR = 102;
  /** RegularExpression Id. */
  int DECR = 103;
  /** RegularExpression Id. */
  int PLUS = 104;
  /** RegularExpression Id. */
  int MINUS = 105;
  /** RegularExpression Id. */
  int STAR = 106;
  /** RegularExpression Id. */
  int SLASH = 107;
  /** RegularExpression Id. */
  int BIT_AND = 108;
  /** RegularExpression Id. */
  int BIT_OR = 109;
  /** RegularExpression Id. */
  int XOR = 110;
  /** RegularExpression Id. */
  int REM = 111;
  /** RegularExpression Id. */
  int LSHIFT = 112;
  /** RegularExpression Id. */
  int PLUSASSIGN = 113;
  /** RegularExpression Id. */
  int MINUSASSIGN = 114;
  /** RegularExpression Id. */
  int STARASSIGN = 115;
  /** RegularExpression Id. */
  int SLASHASSIGN = 116;
  /** RegularExpression Id. */
  int ANDASSIGN = 117;
  /** RegularExpression Id. */
  int ORASSIGN = 118;
  /** RegularExpression Id. */
  int XORASSIGN = 119;
  /** RegularExpression Id. */
  int REMASSIGN = 120;
  /** RegularExpression Id. */
  int LSHIFTASSIGN = 121;
  /** RegularExpression Id. */
  int RSIGNEDSHIFTASSIGN = 122;
  /** RegularExpression Id. */
  int RUNSIGNEDSHIFTASSIGN = 123;
  /** RegularExpression Id. */
  int ELLIPSIS = 124;
  /** RegularExpression Id. */
  int ARROW = 125;
  /** RegularExpression Id. */
  int DOUBLECOLON = 126;
  /** RegularExpression Id. */
  int RUNSIGNEDSHIFT = 127;
  /** RegularExpression Id. */
  int RSIGNEDSHIFT = 128;
  /** RegularExpression Id. */
  int GT = 129;
  /** RegularExpression Id. */
  int NODE_VARIABLE = 130;
  /** RegularExpression Id. */
  int NODE_LIST_VARIABLE = 131;
  /** RegularExpression Id. */
  int IDENTIFIER = 132;

  /** Literal token values. */
  String[] tokenImage = {
    "<EOF>",
    "<WHITESPACE>",
    "<NEWLINE>",
    "<SINGLE_LINE_COMMENT>",
    "<token of kind 4>",
    "\"/*\"",
    "\"*/\"",
    "\"*/\"",
    "<token of kind 8>",
    "\"abstract\"",
    "\"assert\"",
    "\"boolean\"",
    "\"break\"",
    "\"byte\"",
    "\"case\"",
    "\"catch\"",
    "\"char\"",
    "\"class\"",
    "\"const\"",
    "\"continue\"",
    "\"default\"",
    "\"do\"",
    "\"double\"",
    "\"else\"",
    "\"enum\"",
    "\"extends\"",
    "\"false\"",
    "\"final\"",
    "\"finally\"",
    "\"float\"",
    "\"for\"",
    "\"goto\"",
    "\"if\"",
    "\"implements\"",
    "\"import\"",
    "\"instanceof\"",
    "\"int\"",
    "\"interface\"",
    "\"long\"",
    "\"native\"",
    "\"new\"",
    "\"null\"",
    "\"package\"",
    "\"private\"",
    "\"protected\"",
    "\"public\"",
    "\"return\"",
    "\"short\"",
    "\"static\"",
    "\"strictfp\"",
    "\"super\"",
    "\"switch\"",
    "\"synchronized\"",
    "\"this\"",
    "\"throw\"",
    "\"throws\"",
    "\"transient\"",
    "\"true\"",
    "\"try\"",
    "\"void\"",
    "\"volatile\"",
    "\"while\"",
    "<LONG_LITERAL>",
    "<INTEGER_LITERAL>",
    "<DECIMAL_LITERAL>",
    "<HEX_LITERAL>",
    "<OCTAL_LITERAL>",
    "<BINARY_LITERAL>",
    "<FLOAT_LITERAL>",
    "<DOUBLE_LITERAL>",
    "<FLOAT_SUFFIX>",
    "<DOUBLE_SUFFIX>",
    "<DECIMAL_FLOAT_LITERAL>",
    "<DECIMAL_DOUBLE_LITERAL>",
    "<DECIMAL_EXPONENT>",
    "<HEXADECIMAL_FLOAT_LITERAL>",
    "<HEXADECIMAL_DOUBLE_LITERAL>",
    "<HEXADECIMAL_EXPONENT>",
    "<CHARACTER_LITERAL>",
    "<STRING_LITERAL>",
    "\"(\"",
    "\")\"",
    "\"{\"",
    "\"}\"",
    "\"[\"",
    "\"]\"",
    "\";\"",
    "\",\"",
    "\".\"",
    "\"@\"",
    "\"=\"",
    "\"<\"",
    "\"!\"",
    "\"~\"",
    "\"?\"",
    "\":\"",
    "\"==\"",
    "\"<=\"",
    "\">=\"",
    "\"!=\"",
    "\"||\"",
    "\"&&\"",
    "\"++\"",
    "\"--\"",
    "\"+\"",
    "\"-\"",
    "\"*\"",
    "\"/\"",
    "\"&\"",
    "\"|\"",
    "\"^\"",
    "\"%\"",
    "\"<<\"",
    "\"+=\"",
    "\"-=\"",
    "\"*=\"",
    "\"/=\"",
    "\"&=\"",
    "\"|=\"",
    "\"^=\"",
    "\"%=\"",
    "\"<<=\"",
    "\">>=\"",
    "\">>>=\"",
    "\"...\"",
    "\"->\"",
    "\"::\"",
    "\">>>\"",
    "\">>\"",
    "\">\"",
    "<NODE_VARIABLE>",
    "<NODE_LIST_VARIABLE>",
    "<IDENTIFIER>",
  };
}
