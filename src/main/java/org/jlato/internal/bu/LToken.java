package org.jlato.internal.bu;

import org.jlato.parser.ASTParserConstants;

/**
 * @author Didier Villevalois
 */
public class LToken extends LElement {

	public static final LToken Ellipsis = new LToken(ASTParserConstants.ELLIPSIS, "...");

	public static final LToken Public = new LToken(ASTParserConstants.PUBLIC, "public");
	public static final LToken Protected = new LToken(ASTParserConstants.PROTECTED, "protected");
	public static final LToken Private = new LToken(ASTParserConstants.PRIVATE, "private");
	public static final LToken Abstract = new LToken(ASTParserConstants.ABSTRACT, "abstract");
	public static final LToken Default = new LToken(ASTParserConstants.DEFAULT, "default");
	public static final LToken Static = new LToken(ASTParserConstants.STATIC, "static");
	public static final LToken Final = new LToken(ASTParserConstants.FINAL, "final");
	public static final LToken Transient = new LToken(ASTParserConstants.TRANSIENT, "transient");
	public static final LToken Volatile = new LToken(ASTParserConstants.VOLATILE, "volatile");
	public static final LToken Synchronized = new LToken(ASTParserConstants.SYNCHRONIZED, "synchronized");
	public static final LToken Native = new LToken(ASTParserConstants.NATIVE, "native");
	public static final LToken StrictFP = new LToken(ASTParserConstants.STRICTFP, "strictfp");

	public static final LToken Package = new LToken(ASTParserConstants.PACKAGE, "package");
	public static final LToken Import = new LToken(ASTParserConstants.IMPORT, "import");

	public static final LToken Class = new LToken(ASTParserConstants.CLASS, "class");
	public static final LToken Interface = new LToken(ASTParserConstants.INTERFACE, "interface");
	public static final LToken Enum = new LToken(ASTParserConstants.ENUM, "enum");
	public static final LToken AnnotationType = new LToken(ASTParserConstants.INTERFACE, "@interface"); // FIX

	public static final LToken This = new LToken(ASTParserConstants.THIS, "this");
	public static final LToken Super = new LToken(ASTParserConstants.SUPER, "super");
	public static final LToken Extends = new LToken(ASTParserConstants.EXTENDS, "extends");
	public static final LToken Implements = new LToken(ASTParserConstants.IMPLEMENTS, "implements");
	public static final LToken Throws = new LToken(ASTParserConstants.THROWS, "throws");

	public static final LToken Try = new LToken(ASTParserConstants.TRY, "try");
	public static final LToken Catch = new LToken(ASTParserConstants.CATCH, "catch");
	public static final LToken Finally = new LToken(ASTParserConstants.FINALLY, "finally");
	public static final LToken Throw = new LToken(ASTParserConstants.THROW, "throw");
	public static final LToken If = new LToken(ASTParserConstants.IF, "if");
	public static final LToken Else = new LToken(ASTParserConstants.ELSE, "else");
	public static final LToken For = new LToken(ASTParserConstants.FOR, "for");
	public static final LToken Do = new LToken(ASTParserConstants.DO, "do");
	public static final LToken While = new LToken(ASTParserConstants.WHILE, "while");
	public static final LToken Continue = new LToken(ASTParserConstants.CONTINUE, "continue");
	public static final LToken Break = new LToken(ASTParserConstants.BREAK, "break");
	public static final LToken Return = new LToken(ASTParserConstants.RETURN, "return");
	public static final LToken Switch = new LToken(ASTParserConstants.SWITCH, "switch");
	public static final LToken Case = new LToken(ASTParserConstants.CASE, "case");
	public static final LToken Assert = new LToken(ASTParserConstants.ASSERT, "assert");

	public static final LToken New = new LToken(ASTParserConstants.NEW, "new");
	public static final LToken InstanceOf = new LToken(ASTParserConstants.INSTANCEOF, "instanceof");

	public static final LToken Void = new LToken(ASTParserConstants.VOID, "void");
	public static final LToken Boolean = new LToken(ASTParserConstants.BOOLEAN, "boolean");
	public static final LToken Char = new LToken(ASTParserConstants.CHAR, "char");
	public static final LToken Byte = new LToken(ASTParserConstants.BYTE, "byte");
	public static final LToken Short = new LToken(ASTParserConstants.SHORT, "short");
	public static final LToken Int = new LToken(ASTParserConstants.INT, "int");
	public static final LToken Long = new LToken(ASTParserConstants.LONG, "long");
	public static final LToken Float = new LToken(ASTParserConstants.FLOAT, "float");
	public static final LToken Double = new LToken(ASTParserConstants.DOUBLE, "double");

	public static final LToken Plus = new LToken(ASTParserConstants.PLUS, "+");
	public static final LToken Minus = new LToken(ASTParserConstants.MINUS, "-");
	public static final LToken Increment = new LToken(ASTParserConstants.INCR, "++");
	public static final LToken Decrement = new LToken(ASTParserConstants.DECR, "--");
	public static final LToken Not = new LToken(ASTParserConstants.BANG, "!");
	public static final LToken Inverse = new LToken(ASTParserConstants.TILDE, "~");
	public static final LToken Or = new LToken(ASTParserConstants.SC_OR, "||");
	public static final LToken And = new LToken(ASTParserConstants.SC_AND, "&&");
	public static final LToken BinOr = new LToken(ASTParserConstants.BIT_OR, "|");
	public static final LToken BinAnd = new LToken(ASTParserConstants.BIT_AND, "&");
	public static final LToken XOr = new LToken(ASTParserConstants.XOR, "^");
	public static final LToken Equal = new LToken(ASTParserConstants.EQ, "==");
	public static final LToken NotEqual = new LToken(ASTParserConstants.NE, "!=");
	public static final LToken Less = new LToken(ASTParserConstants.LT, "<");
	public static final LToken Greater = new LToken(ASTParserConstants.GT, ">");
	public static final LToken LessOrEqual = new LToken(ASTParserConstants.LE, "<=");
	public static final LToken GreaterOrEqual = new LToken(ASTParserConstants.GE, ">=");
	public static final LToken LShift = new LToken(ASTParserConstants.LSHIFT, "<<");
	public static final LToken RSignedShift = new LToken(ASTParserConstants.RSIGNEDSHIFT, ">>");
	public static final LToken RUnsignedShift = new LToken(ASTParserConstants.RUNSIGNEDSHIFT, ">>>");
	public static final LToken Times = new LToken(ASTParserConstants.STAR, "*");
	public static final LToken Divide = new LToken(ASTParserConstants.SLASH, "/");
	public static final LToken Remainder = new LToken(ASTParserConstants.REM, "%");
	public static final LToken QuestionMark = new LToken(ASTParserConstants.HOOK, "?");

	public static final LToken Assign = new LToken(ASTParserConstants.ASSIGN, "=");
	public static final LToken AssignPlus = new LToken(ASTParserConstants.PLUSASSIGN, "+=");
	public static final LToken AssignMinus = new LToken(ASTParserConstants.MINUSASSIGN, "-=");
	public static final LToken AssignTimes = new LToken(ASTParserConstants.STARASSIGN, "*=");
	public static final LToken AssignDivide = new LToken(ASTParserConstants.SLASHASSIGN, "/=");
	public static final LToken AssignAnd = new LToken(ASTParserConstants.ANDASSIGN, "&=");
	public static final LToken AssignOr = new LToken(ASTParserConstants.ORASSIGN, "|=");
	public static final LToken AssignXOr = new LToken(ASTParserConstants.XORASSIGN, "^=");
	public static final LToken AssignRemainder = new LToken(ASTParserConstants.REMASSIGN, "%=");
	public static final LToken AssignLShift = new LToken(ASTParserConstants.LSHIFTASSIGN, "<<=");
	public static final LToken AssignRSignedShift = new LToken(ASTParserConstants.RSIGNEDSHIFTASSIGN, ">>=");
	public static final LToken AssignRUnsignedShift = new LToken(ASTParserConstants.RUNSIGNEDSHIFTASSIGN, ">>>=");

	public static final LToken Null = new LToken(ASTParserConstants.NULL, "null");
	public static final LToken True = new LToken(ASTParserConstants.TRUE, "true");
	public static final LToken False = new LToken(ASTParserConstants.FALSE, "false");

	public static final LToken At = new LToken(ASTParserConstants.AT, "@");
	public static final LToken SemiColon = new LToken(ASTParserConstants.SEMICOLON, ";");
	public static final LToken Colon = new LToken(ASTParserConstants.COLON, ":");
	public static final LToken Comma = new LToken(ASTParserConstants.COMMA, ",");
	public static final LToken Dot = new LToken(ASTParserConstants.DOT, ".");

	public static final LToken DoubleColon = new LToken(ASTParserConstants.DOUBLECOLON, "::");
	public static final LToken Arrow = new LToken(ASTParserConstants.ARROW, "->");

	public static final LToken BracketLeft = new LToken(ASTParserConstants.LBRACKET, "[");
	public static final LToken BracketRight = new LToken(ASTParserConstants.RBRACKET, "]");
	public static final LToken BraceLeft = new LToken(ASTParserConstants.LBRACE, "{");
	public static final LToken BraceRight = new LToken(ASTParserConstants.RBRACE, "}");
	public static final LToken ParenthesisLeft = new LToken(ASTParserConstants.LPAREN, "(");
	public static final LToken ParenthesisRight = new LToken(ASTParserConstants.RPAREN, ")");

	public final int kind;
	public final String string;

	public LToken(int kind, String string) {
		this.kind = kind;
		this.string = string;
	}

	@Override
	public final boolean isToken() {
		return true;
	}

	public boolean isIdentifier() {
		return kind == ASTParserConstants.IDENTIFIER;
	}

	public boolean isKeyword() {
		switch (kind) {
			case ASTParserConstants.PUBLIC:
			case ASTParserConstants.PROTECTED:
			case ASTParserConstants.PRIVATE:
			case ASTParserConstants.ABSTRACT:
			case ASTParserConstants.DEFAULT:
			case ASTParserConstants.STATIC:
			case ASTParserConstants.FINAL:
			case ASTParserConstants.TRANSIENT:
			case ASTParserConstants.VOLATILE:
			case ASTParserConstants.SYNCHRONIZED:
			case ASTParserConstants.NATIVE:
			case ASTParserConstants.STRICTFP:

			case ASTParserConstants.PACKAGE:
			case ASTParserConstants.IMPORT:

			case ASTParserConstants.CLASS:
			case ASTParserConstants.INTERFACE:
			case ASTParserConstants.ENUM:

			case ASTParserConstants.THIS:
			case ASTParserConstants.SUPER:
			case ASTParserConstants.EXTENDS:
			case ASTParserConstants.IMPLEMENTS:
			case ASTParserConstants.THROWS:

			case ASTParserConstants.TRY:
			case ASTParserConstants.CATCH:
			case ASTParserConstants.FINALLY:
			case ASTParserConstants.THROW:
			case ASTParserConstants.IF:
			case ASTParserConstants.ELSE:
			case ASTParserConstants.FOR:
			case ASTParserConstants.DO:
			case ASTParserConstants.WHILE:
			case ASTParserConstants.CONTINUE:
			case ASTParserConstants.BREAK:
			case ASTParserConstants.RETURN:
			case ASTParserConstants.SWITCH:
			case ASTParserConstants.CASE:
			case ASTParserConstants.ASSERT:

			case ASTParserConstants.NEW:
			case ASTParserConstants.INSTANCEOF:

			case ASTParserConstants.VOID:
			case ASTParserConstants.BOOLEAN:
			case ASTParserConstants.CHAR:
			case ASTParserConstants.BYTE:
			case ASTParserConstants.SHORT:
			case ASTParserConstants.INT:
			case ASTParserConstants.LONG:
			case ASTParserConstants.FLOAT:
			case ASTParserConstants.DOUBLE:

			case ASTParserConstants.NULL:
			case ASTParserConstants.TRUE:
			case ASTParserConstants.FALSE:
				return true;

			default:
				return false;
		}
	}

	public boolean isOperator() {
		switch (kind) {
			case ASTParserConstants.PLUS:
			case ASTParserConstants.MINUS:
			case ASTParserConstants.INCR:
			case ASTParserConstants.DECR:
			case ASTParserConstants.BANG:
			case ASTParserConstants.TILDE:
			case ASTParserConstants.SC_OR:
			case ASTParserConstants.SC_AND:
			case ASTParserConstants.BIT_OR:
			case ASTParserConstants.BIT_AND:
			case ASTParserConstants.XOR:
			case ASTParserConstants.EQ:
			case ASTParserConstants.NE:
			case ASTParserConstants.LT:
			case ASTParserConstants.GT:
			case ASTParserConstants.LE:
			case ASTParserConstants.GE:
			case ASTParserConstants.LSHIFT:
			case ASTParserConstants.RSIGNEDSHIFT:
			case ASTParserConstants.RUNSIGNEDSHIFT:
			case ASTParserConstants.STAR:
			case ASTParserConstants.SLASH:
			case ASTParserConstants.REM:
			case ASTParserConstants.HOOK:

			case ASTParserConstants.ASSIGN:
			case ASTParserConstants.PLUSASSIGN:
			case ASTParserConstants.MINUSASSIGN:
			case ASTParserConstants.STARASSIGN:
			case ASTParserConstants.SLASHASSIGN:
			case ASTParserConstants.ANDASSIGN:
			case ASTParserConstants.ORASSIGN:
			case ASTParserConstants.XORASSIGN:
			case ASTParserConstants.REMASSIGN:
			case ASTParserConstants.LSHIFTASSIGN:
			case ASTParserConstants.RSIGNEDSHIFTASSIGN:
			case ASTParserConstants.RUNSIGNEDSHIFTASSIGN:
				return true;
			default:
				return false;
		}
	}

	public boolean isSeparator() {
		switch (kind) {
			case ASTParserConstants.ELLIPSIS:

			case ASTParserConstants.AT:
			case ASTParserConstants.SEMICOLON:
			case ASTParserConstants.COLON:
			case ASTParserConstants.COMMA:
			case ASTParserConstants.DOT:

			case ASTParserConstants.DOUBLECOLON:
			case ASTParserConstants.ARROW:

			case ASTParserConstants.LBRACKET:
			case ASTParserConstants.RBRACKET:
			case ASTParserConstants.LBRACE:
			case ASTParserConstants.RBRACE:
			case ASTParserConstants.LPAREN:
			case ASTParserConstants.RPAREN:
				return true;

			default:
				return false;
		}
	}

	@Override
	public final int width() {
		return string.length();
	}

	@Override
	public String toString() {
		return string;
	}
}
