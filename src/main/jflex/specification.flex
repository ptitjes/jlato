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
package org.jlato.parser;

import org.jlato.parser.*;

/**
 * A Java 1.8 lexer.
 *
 * @author Didier Villevalois
 */
%%

%{
    private ParserInterface parser;

    public Lexer() {
        this((java.io.Reader)null);
    }

    public void setParser(ParserInterface parser) {
        this.parser = parser;
    }

    private Token newToken(int type) {
        return newToken(type, yytext());
    }

    private Token newToken(int type, String value) {
        Token token = Token.newToken(type, value);
        token.beginLine = yyline + 1;
        token.beginColumn = yycolumn + 1;
        return token;
    }
%}

%public
%class Lexer
%type Token
%unicode
%line
%column

LineTerminator = \r|\n|\r\n|\f
WhiteSpace = [\ \t]

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

<<EOF>>  { return newToken(ParserImplConstants.EOF); }

<YYINITIAL> {

    {LineTerminator}        { return newToken(ParserImplConstants.NEWLINE); }
    {WhiteSpace}            { return newToken(ParserImplConstants.WHITESPACE); }

    {MultiLineComment}      { return newToken(ParserImplConstants.MULTI_LINE_COMMENT); }
    {EndOfLineComment}      { return newToken(ParserImplConstants.SINGLE_LINE_COMMENT); }
    {DocumentationComment}  { return newToken(ParserImplConstants.JAVA_DOC_COMMENT); }

    /* quote variables */
    {NodeVariable}          { return newToken(parser.quotesMode ? ParserImplConstants.NODE_VARIABLE : ParserImplConstants.IDENTIFIER); }
    {NodeListVariable}      { return newToken(ParserImplConstants.NODE_LIST_VARIABLE); }

    /* literals */
    {IntegerLiteral}        { return newToken(ParserImplConstants.INTEGER_LITERAL); }
    {LongLiteral}           { return newToken(ParserImplConstants.LONG_LITERAL); }
    {FloatLiteral}          { return newToken(ParserImplConstants.FLOAT_LITERAL); }
    {DoubleLiteral}         { return newToken(ParserImplConstants.DOUBLE_LITERAL); }
    {CharacterLiteral}      { return newToken(ParserImplConstants.CHARACTER_LITERAL); }
    {StringLiteral}         { return newToken(ParserImplConstants.STRING_LITERAL); }

    /* keywords */
    "abstract"		{ return newToken(ParserImplConstants.ABSTRACT); }
    "assert"		{ return newToken(ParserImplConstants.ASSERT); }
    "boolean"		{ return newToken(ParserImplConstants.BOOLEAN); }
    "break"	    	{ return newToken(ParserImplConstants.BREAK); }
    "byte"	    	{ return newToken(ParserImplConstants.BYTE); }
    "case"	    	{ return newToken(ParserImplConstants.CASE); }
    "catch"		    { return newToken(ParserImplConstants.CATCH); }
    "char"  		{ return newToken(ParserImplConstants.CHAR); }
    "class"	    	{ return newToken(ParserImplConstants.CLASS); }
    "const"	    	{ return newToken(ParserImplConstants.CONST); }
    "continue"		{ return newToken(ParserImplConstants.CONTINUE); }
    "default"		{ return newToken(ParserImplConstants._DEFAULT); }
    "do"	    	{ return newToken(ParserImplConstants.DO); }
    "double"		{ return newToken(ParserImplConstants.DOUBLE); }
    "else"	    	{ return newToken(ParserImplConstants.ELSE); }
    "enum"	    	{ return newToken(ParserImplConstants.ENUM); }
    "extends"		{ return newToken(ParserImplConstants.EXTENDS); }
    "false" 		{ return newToken(ParserImplConstants.FALSE); }
    "final"	    	{ return newToken(ParserImplConstants.FINAL); }
    "finally"		{ return newToken(ParserImplConstants.FINALLY); }
    "float"	    	{ return newToken(ParserImplConstants.FLOAT); }
    "for"	    	{ return newToken(ParserImplConstants.FOR); }
    "goto"	    	{ return newToken(ParserImplConstants.GOTO); }
    "if"	    	{ return newToken(ParserImplConstants.IF); }
    "implements"	{ return newToken(ParserImplConstants.IMPLEMENTS); }
    "import"		{ return newToken(ParserImplConstants.IMPORT); }
    "instanceof"	{ return newToken(ParserImplConstants.INSTANCEOF); }
    "int"	    	{ return newToken(ParserImplConstants.INT); }
    "interface"		{ return newToken(ParserImplConstants.INTERFACE); }
    "long"		    { return newToken(ParserImplConstants.LONG); }
    "native"		{ return newToken(ParserImplConstants.NATIVE); }
    "new"	    	{ return newToken(ParserImplConstants.NEW); }
    "null"		    { return newToken(ParserImplConstants.NULL); }
    "package"		{ return newToken(ParserImplConstants.PACKAGE); }
    "private"		{ return newToken(ParserImplConstants.PRIVATE); }
    "protected"		{ return newToken(ParserImplConstants.PROTECTED); }
    "public"		{ return newToken(ParserImplConstants.PUBLIC); }
    "return"		{ return newToken(ParserImplConstants.RETURN); }
    "short"	    	{ return newToken(ParserImplConstants.SHORT); }
    "static"		{ return newToken(ParserImplConstants.STATIC); }
    "strictfp"		{ return newToken(ParserImplConstants.STRICTFP); }
    "super"	    	{ return newToken(ParserImplConstants.SUPER); }
    "switch"		{ return newToken(ParserImplConstants.SWITCH); }
    "synchronized"	{ return newToken(ParserImplConstants.SYNCHRONIZED); }
    "this"	    	{ return newToken(ParserImplConstants.THIS); }
    "throw"	    	{ return newToken(ParserImplConstants.THROW); }
    "throws"		{ return newToken(ParserImplConstants.THROWS); }
    "transient"		{ return newToken(ParserImplConstants.TRANSIENT); }
    "true"		    { return newToken(ParserImplConstants.TRUE); }
    "try"	    	{ return newToken(ParserImplConstants.TRY); }
    "void"	    	{ return newToken(ParserImplConstants.VOID); }
    "volatile"		{ return newToken(ParserImplConstants.VOLATILE); }
    "while"	    	{ return newToken(ParserImplConstants.WHILE); }

    /* operators */
    "("	    	{ return newToken(ParserImplConstants.LPAREN); }
    ")"	    	{ return newToken(ParserImplConstants.RPAREN); }
    "{"	    	{ return newToken(ParserImplConstants.LBRACE); }
    "}"		    { return newToken(ParserImplConstants.RBRACE); }
    "["	    	{ return newToken(ParserImplConstants.LBRACKET); }
    "]"		    { return newToken(ParserImplConstants.RBRACKET); }
    ";"	    	{ return newToken(ParserImplConstants.SEMICOLON); }
    ","	    	{ return newToken(ParserImplConstants.COMMA); }
    "."	    	{ return newToken(ParserImplConstants.DOT); }
    "@"		    { return newToken(ParserImplConstants.AT); }

    "="	    	{ return newToken(ParserImplConstants.ASSIGN); }
    "<"	    	{ return newToken(ParserImplConstants.LT); }
    ">"	    	{ return newToken(ParserImplConstants.GT); }
    "!"	    	{ return newToken(ParserImplConstants.BANG); }
    "~"	    	{ return newToken(ParserImplConstants.TILDE); }
    "?"	    	{ return newToken(ParserImplConstants.HOOK); }
    ":"	    	{ return newToken(ParserImplConstants.COLON); }
    "=="		{ return newToken(ParserImplConstants.EQ); }
    "<="		{ return newToken(ParserImplConstants.LE); }
    ">="		{ return newToken(ParserImplConstants.GE); }
    "!="		{ return newToken(ParserImplConstants.NE); }
    "||"		{ return newToken(ParserImplConstants.SC_OR); }
    "&&"		{ return newToken(ParserImplConstants.SC_AND); }
    "++"		{ return newToken(ParserImplConstants.INCR); }
    "--"		{ return newToken(ParserImplConstants.DECR); }
    "+"	    	{ return newToken(ParserImplConstants.PLUS); }
    "-"	    	{ return newToken(ParserImplConstants.MINUS); }
    "*"	    	{ return newToken(ParserImplConstants.STAR); }
    "/" 		{ return newToken(ParserImplConstants.SLASH); }
    "&" 		{ return newToken(ParserImplConstants.BIT_AND); }
    "|"		    { return newToken(ParserImplConstants.BIT_OR); }
    "^"		    { return newToken(ParserImplConstants.XOR); }
    "%" 		{ return newToken(ParserImplConstants.REM); }
    "<<"		{ return newToken(ParserImplConstants.LSHIFT); }
    "+="		{ return newToken(ParserImplConstants.PLUSASSIGN); }
    "-="		{ return newToken(ParserImplConstants.MINUSASSIGN); }
    "*="		{ return newToken(ParserImplConstants.STARASSIGN); }
    "/="		{ return newToken(ParserImplConstants.SLASHASSIGN); }
    "&="		{ return newToken(ParserImplConstants.ANDASSIGN); }
    "|="		{ return newToken(ParserImplConstants.ORASSIGN); }
    "^="		{ return newToken(ParserImplConstants.XORASSIGN); }
    "%="		{ return newToken(ParserImplConstants.REMASSIGN); }
    "<<="		{ return newToken(ParserImplConstants.LSHIFTASSIGN); }
    ">>="		{ return newToken(ParserImplConstants.RSIGNEDSHIFTASSIGN); }
    ">>>="		{ return newToken(ParserImplConstants.RUNSIGNEDSHIFTASSIGN); }
    "..."		{ return newToken(ParserImplConstants.ELLIPSIS); }
    "->"		{ return newToken(ParserImplConstants.ARROW); }
    "::"		{ return newToken(ParserImplConstants.DOUBLECOLON); }

    /* identifiers */
    {Identifier}            { return newToken(ParserImplConstants.IDENTIFIER); }

}

[^]             { throw new Error("Illegal character <" + yytext() + ">"); }
// TODO Do not throw an exception for a bad character