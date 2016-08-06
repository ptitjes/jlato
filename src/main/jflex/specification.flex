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

import org.jlato.internal.parser.TokenType;

/**
 * A Java 1.8 lexer.
 *
 * @author Didier Villevalois
 */
%%

%{
    private ParserNewBase parser;

    public Lexer() {
        this((java.io.Reader)null);
    }

    public void setParser(ParserNewBase parser) {
        this.parser = parser;
    }

    private Token newToken(int type) {
        String image;
        switch (type) {
            case TokenType.WHITESPACE:
            case TokenType.NEWLINE:

            case TokenType.SINGLE_LINE_COMMENT:
            case TokenType.JAVA_DOC_COMMENT:
            case TokenType.MULTI_LINE_COMMENT:

            case TokenType.INTEGER_LITERAL:
            case TokenType.LONG_LITERAL:
            case TokenType.FLOAT_LITERAL:
            case TokenType.DOUBLE_LITERAL:
            case TokenType.CHARACTER_LITERAL:
            case TokenType.STRING_LITERAL:

            case TokenType.NODE_VARIABLE:
            case TokenType.NODE_LIST_VARIABLE:
            case TokenType.IDENTIFIER:
                image = yytext();
                break;

            default:
                image = TokenType.tokenImage[type];
        }

        return new Token(type, image, yyline + 1, yycolumn + 1);
    }
%}

%public
%class Lexer
%type Token
%unicode
%line
%column

LineTerminator = \r|\n|\r\n|\f
WhiteSpace = [\ \t]+

MultiLineComment   = "/*" [^*] ~"*/" | "/*" "*"+ "/"
EndOfLineComment     = "//" [^\r\n]*
DocumentationComment = "/**" {CommentContent} "*"+ "/"
CommentContent       = ( [^*] | \*+ [^/*] )*

NodeVariable = "$" [:jletterdigit:]*
NodeListVariable = "..$" [:jletterdigit:]*

Identifier = [:jletter:] [:jletterdigit:]*

NonZeroDigit = [1-9]
Digit = [0-9]
DigitOrUnderscore = [_0-9]
Digits = {Digit} | {Digit} {DigitOrUnderscore}* {Digit}
HexDigit = [0-9a-fA-F]
HexDigitOrUnderscore = [_0-9a-fA-F]
HexDigits = {HexDigit} | {HexDigit} {HexDigitOrUnderscore}* {HexDigit}
OctalDigit = [0-7]
OctalDigitOrUnderscore = [_0-7]

IntegerLiteral = {DecimalLiteral} | {HexLiteral} | {OctalLiteral} | {BinaryLiteral}
LongLiteral = {IntegerLiteral} [lL]

DecimalLiteral = {Digit} | {NonZeroDigit} {DigitOrUnderscore}* {Digit}
HexLiteral = "0" [xX] {HexDigits}
OctalLiteral = "0" ({OctalDigit} | {OctalDigit} {OctalDigitOrUnderscore}* {OctalDigit})
BinaryLiteral = "0" [bB] ([01] | [01] [_01]* [01])

DecFloat = {Digits} "." {Digits}? {DecExponent}? {FloatSuffix} | "." {Digits} {DecExponent}? {FloatSuffix} | {Digits} {DecExponent} {FloatSuffix} | {Digits} {DecExponent}? {FloatSuffix}
DecDouble = {Digits} "." {Digits}? {DecExponent}? {DoubleSuffix}? | "." {Digits} {DecExponent}? {DoubleSuffix}? | {Digits} {DecExponent} {DoubleSuffix}? | {Digits} {DecExponent}? {DoubleSuffix}
DecExponent = [eE] [+-]? {Digits}
HexFloat = "0" [xX] ({HexDigits} (".")? | {HexDigits}? "." {HexDigits}) {HexExponent}? {FloatSuffix}
HexDouble = "0" [xX] ({HexDigits} (".")? | {HexDigits}? "." {HexDigits}) {HexExponent}? {DoubleSuffix}?
HexExponent = [pP] [+-]? {Digit}+
FloatSuffix = [fF]
DoubleSuffix = [dD]

FloatLiteral = {DecFloat} | {HexFloat}
DoubleLiteral = {DecDouble} | {HexDouble}

EscapeSequence = \\[^\r\n]
CharacterLiteral = "'" ([^\\\'\r\n] | {EscapeSequence})* ("'"|\\)?
StringLiteral = \" ([^\\\"\r\n] | {EscapeSequence})* (\"|\\)?

%%

<<EOF>>  { return newToken(TokenType.EOF); }

<YYINITIAL> {

    {LineTerminator}        { return newToken(TokenType.NEWLINE); }
    {WhiteSpace}            { return newToken(TokenType.WHITESPACE); }

    {MultiLineComment}      { return newToken(TokenType.MULTI_LINE_COMMENT); }
    {EndOfLineComment}      { return newToken(TokenType.SINGLE_LINE_COMMENT); }
    {DocumentationComment}  { return newToken(TokenType.JAVA_DOC_COMMENT); }

    /* literals */
    {IntegerLiteral}        { return newToken(TokenType.INTEGER_LITERAL); }
    {LongLiteral}           { return newToken(TokenType.LONG_LITERAL); }
    {FloatLiteral}          { return newToken(TokenType.FLOAT_LITERAL); }
    {DoubleLiteral}         { return newToken(TokenType.DOUBLE_LITERAL); }
    {CharacterLiteral}      { return newToken(TokenType.CHARACTER_LITERAL); }
    {StringLiteral}         { return newToken(TokenType.STRING_LITERAL); }

    /* operators */
    "("	    	{ return newToken(TokenType.LPAREN); }
    ")"	    	{ return newToken(TokenType.RPAREN); }
    "{"	    	{ return newToken(TokenType.LBRACE); }
    "}"		    { return newToken(TokenType.RBRACE); }
    "["	    	{ return newToken(TokenType.LBRACKET); }
    "]"		    { return newToken(TokenType.RBRACKET); }
    ";"	    	{ return newToken(TokenType.SEMICOLON); }
    ","	    	{ return newToken(TokenType.COMMA); }
    "."	    	{ return newToken(TokenType.DOT); }
    "@"		    { return newToken(TokenType.AT); }

    "="	    	{ return newToken(TokenType.ASSIGN); }
    "<"	    	{ return newToken(TokenType.LT); }
    ">"	    	{ return newToken(TokenType.GT); }
    "!"	    	{ return newToken(TokenType.BANG); }
    "~"	    	{ return newToken(TokenType.TILDE); }
    "?"	    	{ return newToken(TokenType.HOOK); }
    ":"	    	{ return newToken(TokenType.COLON); }
    "=="		{ return newToken(TokenType.EQ); }
    "<="		{ return newToken(TokenType.LE); }
    ">="		{ return newToken(TokenType.GE); }
    "!="		{ return newToken(TokenType.NE); }
    "||"		{ return newToken(TokenType.SC_OR); }
    "&&"		{ return newToken(TokenType.SC_AND); }
    "++"		{ return newToken(TokenType.INCR); }
    "--"		{ return newToken(TokenType.DECR); }
    "+"	    	{ return newToken(TokenType.PLUS); }
    "-"	    	{ return newToken(TokenType.MINUS); }
    "*"	    	{ return newToken(TokenType.STAR); }
    "/" 		{ return newToken(TokenType.SLASH); }
    "&" 		{ return newToken(TokenType.BIT_AND); }
    "|"		    { return newToken(TokenType.BIT_OR); }
    "^"		    { return newToken(TokenType.XOR); }
    "%" 		{ return newToken(TokenType.REM); }
    "<<"		{ return newToken(TokenType.LSHIFT); }
    "+="		{ return newToken(TokenType.PLUSASSIGN); }
    "-="		{ return newToken(TokenType.MINUSASSIGN); }
    "*="		{ return newToken(TokenType.STARASSIGN); }
    "/="		{ return newToken(TokenType.SLASHASSIGN); }
    "&="		{ return newToken(TokenType.ANDASSIGN); }
    "|="		{ return newToken(TokenType.ORASSIGN); }
    "^="		{ return newToken(TokenType.XORASSIGN); }
    "%="		{ return newToken(TokenType.REMASSIGN); }
    "<<="		{ return newToken(TokenType.LSHIFTASSIGN); }
    ">>="		{ return newToken(TokenType.RSIGNEDSHIFTASSIGN); }
    ">>>="		{ return newToken(TokenType.RUNSIGNEDSHIFTASSIGN); }
    "..."		{ return newToken(TokenType.ELLIPSIS); }
    "->"		{ return newToken(TokenType.ARROW); }
    "::"		{ return newToken(TokenType.DOUBLECOLON); }

    /* quote variables */
    {NodeVariable}          { return newToken(parser.quotesMode ? TokenType.NODE_VARIABLE : TokenType.IDENTIFIER); }
    {NodeListVariable}      { return newToken(TokenType.NODE_LIST_VARIABLE); }

    /* keywords */
    "abstract"		{ return newToken(TokenType.ABSTRACT); }
    "assert"		{ return newToken(TokenType.ASSERT); }
    "boolean"		{ return newToken(TokenType.BOOLEAN); }
    "break"	    	{ return newToken(TokenType.BREAK); }
    "byte"	    	{ return newToken(TokenType.BYTE); }
    "case"	    	{ return newToken(TokenType.CASE); }
    "catch"		    { return newToken(TokenType.CATCH); }
    "char"  		{ return newToken(TokenType.CHAR); }
    "class"	    	{ return newToken(TokenType.CLASS); }
    "const"	    	{ return newToken(TokenType.CONST); }
    "continue"		{ return newToken(TokenType.CONTINUE); }
    "default"		{ return newToken(TokenType.DEFAULT); }
    "do"	    	{ return newToken(TokenType.DO); }
    "double"		{ return newToken(TokenType.DOUBLE); }
    "else"	    	{ return newToken(TokenType.ELSE); }
    "enum"	    	{ return newToken(TokenType.ENUM); }
    "extends"		{ return newToken(TokenType.EXTENDS); }
    "false" 		{ return newToken(TokenType.FALSE); }
    "final"	    	{ return newToken(TokenType.FINAL); }
    "finally"		{ return newToken(TokenType.FINALLY); }
    "float"	    	{ return newToken(TokenType.FLOAT); }
    "for"	    	{ return newToken(TokenType.FOR); }
    "goto"	    	{ return newToken(TokenType.GOTO); }
    "if"	    	{ return newToken(TokenType.IF); }
    "implements"	{ return newToken(TokenType.IMPLEMENTS); }
    "import"		{ return newToken(TokenType.IMPORT); }
    "instanceof"	{ return newToken(TokenType.INSTANCEOF); }
    "int"	    	{ return newToken(TokenType.INT); }
    "interface"		{ return newToken(TokenType.INTERFACE); }
    "long"		    { return newToken(TokenType.LONG); }
    "native"		{ return newToken(TokenType.NATIVE); }
    "new"	    	{ return newToken(TokenType.NEW); }
    "null"		    { return newToken(TokenType.NULL); }
    "package"		{ return newToken(TokenType.PACKAGE); }
    "private"		{ return newToken(TokenType.PRIVATE); }
    "protected"		{ return newToken(TokenType.PROTECTED); }
    "public"		{ return newToken(TokenType.PUBLIC); }
    "return"		{ return newToken(TokenType.RETURN); }
    "short"	    	{ return newToken(TokenType.SHORT); }
    "static"		{ return newToken(TokenType.STATIC); }
    "strictfp"		{ return newToken(TokenType.STRICTFP); }
    "super"	    	{ return newToken(TokenType.SUPER); }
    "switch"		{ return newToken(TokenType.SWITCH); }
    "synchronized"	{ return newToken(TokenType.SYNCHRONIZED); }
    "this"	    	{ return newToken(TokenType.THIS); }
    "throw"	    	{ return newToken(TokenType.THROW); }
    "throws"		{ return newToken(TokenType.THROWS); }
    "transient"		{ return newToken(TokenType.TRANSIENT); }
    "true"		    { return newToken(TokenType.TRUE); }
    "try"	    	{ return newToken(TokenType.TRY); }
    "void"	    	{ return newToken(TokenType.VOID); }
    "volatile"		{ return newToken(TokenType.VOLATILE); }
    "while"	    	{ return newToken(TokenType.WHILE); }

    /* identifiers */
    {Identifier}            { return newToken(TokenType.IDENTIFIER); }
}

[^]             { throw new Error("Illegal character <" + yytext() + ">"); }
// TODO Do not throw an exception for a bad character