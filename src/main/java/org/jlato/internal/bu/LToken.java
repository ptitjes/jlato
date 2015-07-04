package org.jlato.internal.bu;

import com.github.javaparser.ASTParserConstants;

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

	public static final LToken Class = new LToken(ASTParserConstants.CLASS, "class");
	public static final LToken Interface = new LToken(ASTParserConstants.INTERFACE, "interface");
	public static final LToken Enum = new LToken(ASTParserConstants.ENUM, "enum");
	public static final LToken AnnotationType = new LToken(ASTParserConstants.INTERFACE, "@interface"); // FIX

	public static final LToken This = new LToken(ASTParserConstants.THIS, "this");
	public static final LToken Super = new LToken(ASTParserConstants.SUPER, "super");

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

	public static final LToken SemiColon = new LToken(ASTParserConstants.SEMICOLON, ";");


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

	@Override
	public final int width() {
		return string.length();
	}

	@Override
	public String toString() {
		return string;
	}
}
