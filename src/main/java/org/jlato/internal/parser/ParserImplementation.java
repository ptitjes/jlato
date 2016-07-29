package org.jlato.internal.parser;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.coll.*;
import org.jlato.internal.bu.decl.*;
import org.jlato.internal.bu.expr.*;
import org.jlato.internal.bu.name.*;
import org.jlato.internal.bu.stmt.*;
import org.jlato.internal.bu.type.*;
import org.jlato.parser.ParseException;
import org.jlato.parser.ParserImplConstants;
import org.jlato.parser.ParserInterface.TypeKind;
import org.jlato.tree.Problem.Severity;
import org.jlato.tree.decl.ModifierKeyword;
import org.jlato.tree.expr.AssignOp;
import org.jlato.tree.expr.BinaryOp;
import org.jlato.tree.expr.UnaryOp;
import org.jlato.tree.type.Primitive;

import static org.jlato.parser.ParserImplConstants.GT;
import static org.jlato.parser.ParserImplConstants.RSIGNEDSHIFT;
import static org.jlato.parser.ParserImplConstants.RUNSIGNEDSHIFT;

public class ParserImplementation extends ParserNewBase {

	public BUTree<SNodeList> parseNodeListVar() throws ParseException {
		parse(ParserImplConstants.NODE_LIST_VARIABLE);
		return makeVar();
	}

	public BUTree<SName> parseNodeVar() throws ParseException {
		parse(ParserImplConstants.NODE_VARIABLE);
		return makeVar();
	}

	public BUTree<SCompilationUnit> parseCompilationUnit() throws ParseException {
		BUTree<SPackageDecl> packageDecl = null;
		BUTree<SNodeList> imports;
		BUTree<SNodeList> types;
		BUTree<SCompilationUnit> compilationUnit;
		run();
		if (matchCompilationUnit1(0) != -1) {
			packageDecl = parsePackageDecl();
		}
		imports = parseImportDecls();
		types = parseTypeDecls();
		compilationUnit = dress(SCompilationUnit.make(packageDecl, imports, types));
		parseEpilog();
		return dressWithPrologAndEpilog(compilationUnit);
	}

	/* Sequence(
			Action({
				run();
			})
			Terminal(id, "IDENTIFIER")
			Action({
				name = dress(SName.make(id.image));
			})
		) */
	private int matchName_1_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.IDENTIFIER);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("NODE_VARIABLE")
			Action({
				return makeVar();
			})
		) */
	private int matchNodeVar(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.NODE_VARIABLE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(			quotesMode
	)
			NonTerminal(name, NodeVar)
		) */
	private int matchName_1_2(int lookahead) {
		lookahead = quotesMode ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = matchNodeVar(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				Action({
					run();
				})
				Terminal(id, "IDENTIFIER")
				Action({
					name = dress(SName.make(id.image));
				})
			)
			Sequence(
				LookAhead(				quotesMode
	)
				NonTerminal(name, NodeVar)
			)
		) */
	private int matchName_1(int lookahead) {
		int newLookahead;
		newLookahead = matchName_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchName_1_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			Choice(
				Sequence(
					Action({
						run();
					})
					Terminal(id, "IDENTIFIER")
					Action({
						name = dress(SName.make(id.image));
					})
				)
				Sequence(
					LookAhead(					quotesMode
	)
					NonTerminal(name, NodeVar)
				)
			)
			Action({
				return name;
			})
		) */
	private int matchName(int lookahead) {
		lookahead = matchName_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(2)
			Action({
				lateRun();
			})
			Terminal("DOT")
			Action({
				qualifier = optionOf(ret);
			})
			NonTerminal(name, Name)
			Action({
				ret = dress(SQualifiedName.make(qualifier, name));
			})
		) */
	private int matchQualifiedName_4_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.DOT);
		if (lookahead == -1)
			return -1;
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrMore(
			LookAhead(2)
			Action({
				lateRun();
			})
			Terminal("DOT")
			Action({
				qualifier = optionOf(ret);
			})
			NonTerminal(name, Name)
			Action({
				ret = dress(SQualifiedName.make(qualifier, name));
			})
		) */
	private int matchQualifiedName_4(int lookahead) {
		int newLookahead;
		newLookahead = matchQualifiedName_4_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchQualifiedName_4_1(lookahead);
		}
		return lookahead;
	}

	/* Sequence(
			Action({
				run();
			})
			NonTerminal(name, Name)
			Action({
				ret = dress(SQualifiedName.make(qualifier, name));
			})
			ZeroOrMore(
				LookAhead(2)
				Action({
					lateRun();
				})
				Terminal("DOT")
				Action({
					qualifier = optionOf(ret);
				})
				NonTerminal(name, Name)
				Action({
					ret = dress(SQualifiedName.make(qualifier, name));
				})
			)
			Action({
				return ret;
			})
		) */
	private int matchQualifiedName(int lookahead) {
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchQualifiedName_4(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(2)
			Terminal("COMMA")
			NonTerminal(member, MemberValue)
			Action({
				ret = append(ret, member);
			})
		) */
	private int matchMemberValueArrayInitializer_3_1_3_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.COMMA);
		if (lookahead == -1)
			return -1;
		lookahead = matchMemberValue(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrMore(
			LookAhead(2)
			Terminal("COMMA")
			NonTerminal(member, MemberValue)
			Action({
				ret = append(ret, member);
			})
		) */
	private int matchMemberValueArrayInitializer_3_1_3(int lookahead) {
		int newLookahead;
		newLookahead = matchMemberValueArrayInitializer_3_1_3_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchMemberValueArrayInitializer_3_1_3_1(lookahead);
		}
		return lookahead;
	}

	/* Sequence(
			NonTerminal(member, MemberValue)
			Action({
				ret = append(ret, member);
			})
			ZeroOrMore(
				LookAhead(2)
				Terminal("COMMA")
				NonTerminal(member, MemberValue)
				Action({
					ret = append(ret, member);
				})
			)
		) */
	private int matchMemberValueArrayInitializer_3_1(int lookahead) {
		lookahead = matchMemberValue(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchMemberValueArrayInitializer_3_1_3(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			NonTerminal(member, MemberValue)
			Action({
				ret = append(ret, member);
			})
			ZeroOrMore(
				LookAhead(2)
				Terminal("COMMA")
				NonTerminal(member, MemberValue)
				Action({
					ret = append(ret, member);
				})
			)
		) */
	private int matchMemberValueArrayInitializer_3(int lookahead) {
		int newLookahead;
		newLookahead = matchMemberValueArrayInitializer_3_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			Terminal("COMMA")
			Action({
				trailingComma = true;
			})
		) */
	private int matchMemberValueArrayInitializer_4_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.COMMA);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			Terminal("COMMA")
			Action({
				trailingComma = true;
			})
		) */
	private int matchMemberValueArrayInitializer_4(int lookahead) {
		int newLookahead;
		newLookahead = matchMemberValueArrayInitializer_4_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			Action({
				run();
			})
			Terminal("LBRACE")
			ZeroOrOne(
				NonTerminal(member, MemberValue)
				Action({
					ret = append(ret, member);
				})
				ZeroOrMore(
					LookAhead(2)
					Terminal("COMMA")
					NonTerminal(member, MemberValue)
					Action({
						ret = append(ret, member);
					})
				)
			)
			ZeroOrOne(
				Terminal("COMMA")
				Action({
					trailingComma = true;
				})
			)
			Terminal("RBRACE")
			Action({
				return dress(SArrayInitializerExpr.make(ret, trailingComma));
			})
		) */
	private int matchMemberValueArrayInitializer(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.LBRACE);
		if (lookahead == -1)
			return -1;
		lookahead = matchMemberValueArrayInitializer_3(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchMemberValueArrayInitializer_4(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RBRACE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("INCR")
			Action({
				op = UnaryOp.PreIncrement;
			})
		) */
	private int matchPrefixExpression_2_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.INCR);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("DECR")
			Action({
				op = UnaryOp.PreDecrement;
			})
		) */
	private int matchPrefixExpression_2_2(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.DECR);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				Terminal("INCR")
				Action({
					op = UnaryOp.PreIncrement;
				})
			)
			Sequence(
				Terminal("DECR")
				Action({
					op = UnaryOp.PreDecrement;
				})
			)
		) */
	private int matchPrefixExpression_2(int lookahead) {
		int newLookahead;
		newLookahead = matchPrefixExpression_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchPrefixExpression_2_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			Action({
				run();
			})
			Choice(
				Sequence(
					Terminal("INCR")
					Action({
						op = UnaryOp.PreIncrement;
					})
				)
				Sequence(
					Terminal("DECR")
					Action({
						op = UnaryOp.PreDecrement;
					})
				)
			)
			NonTerminal(ret, UnaryExpression)
			Action({
				return dress(SUnaryExpr.make(op, ret));
			})
		) */
	private int matchPrefixExpression(int lookahead) {
		lookahead = matchPrefixExpression_2(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchUnaryExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("PLUS")
			Action({
				op = UnaryOp.Positive;
			})
		) */
	private int matchUnaryExpression_1_2_2_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.PLUS);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("MINUS")
			Action({
				op = UnaryOp.Negative;
			})
		) */
	private int matchUnaryExpression_1_2_2_2(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.MINUS);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				Terminal("PLUS")
				Action({
					op = UnaryOp.Positive;
				})
			)
			Sequence(
				Terminal("MINUS")
				Action({
					op = UnaryOp.Negative;
				})
			)
		) */
	private int matchUnaryExpression_1_2_2(int lookahead) {
		int newLookahead;
		newLookahead = matchUnaryExpression_1_2_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchUnaryExpression_1_2_2_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			Action({
				run();
			})
			Choice(
				Sequence(
					Terminal("PLUS")
					Action({
						op = UnaryOp.Positive;
					})
				)
				Sequence(
					Terminal("MINUS")
					Action({
						op = UnaryOp.Negative;
					})
				)
			)
			NonTerminal(ret, UnaryExpression)
			Action({
				ret = dress(SUnaryExpr.make(op, ret));
			})
		) */
	private int matchUnaryExpression_1_2(int lookahead) {
		lookahead = matchUnaryExpression_1_2_2(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchUnaryExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("TILDE")
			Action({
				op = UnaryOp.Inverse;
			})
		) */
	private int matchUnaryExpressionNotPlusMinus_1_1_2_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.TILDE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("BANG")
			Action({
				op = UnaryOp.Not;
			})
		) */
	private int matchUnaryExpressionNotPlusMinus_1_1_2_2(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.BANG);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				Terminal("TILDE")
				Action({
					op = UnaryOp.Inverse;
				})
			)
			Sequence(
				Terminal("BANG")
				Action({
					op = UnaryOp.Not;
				})
			)
		) */
	private int matchUnaryExpressionNotPlusMinus_1_1_2(int lookahead) {
		int newLookahead;
		newLookahead = matchUnaryExpressionNotPlusMinus_1_1_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchUnaryExpressionNotPlusMinus_1_1_2_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			Action({
				run();
			})
			Choice(
				Sequence(
					Terminal("TILDE")
					Action({
						op = UnaryOp.Inverse;
					})
				)
				Sequence(
					Terminal("BANG")
					Action({
						op = UnaryOp.Not;
					})
				)
			)
			NonTerminal(ret, UnaryExpression)
			Action({
				ret = dress(SUnaryExpr.make(op, ret));
			})
		) */
	private int matchUnaryExpressionNotPlusMinus_1_1(int lookahead) {
		lookahead = matchUnaryExpressionNotPlusMinus_1_1_2(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchUnaryExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("BOOLEAN")
			Action({
				primitive = Primitive.Boolean;
			})
		) */
	private int matchPrimitiveType_2_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.BOOLEAN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("CHAR")
			Action({
				primitive = Primitive.Char;
			})
		) */
	private int matchPrimitiveType_2_2(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.CHAR);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("BYTE")
			Action({
				primitive = Primitive.Byte;
			})
		) */
	private int matchPrimitiveType_2_3(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.BYTE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("SHORT")
			Action({
				primitive = Primitive.Short;
			})
		) */
	private int matchPrimitiveType_2_4(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.SHORT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("INT")
			Action({
				primitive = Primitive.Int;
			})
		) */
	private int matchPrimitiveType_2_5(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.INT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("LONG")
			Action({
				primitive = Primitive.Long;
			})
		) */
	private int matchPrimitiveType_2_6(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.LONG);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("FLOAT")
			Action({
				primitive = Primitive.Float;
			})
		) */
	private int matchPrimitiveType_2_7(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.FLOAT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("DOUBLE")
			Action({
				primitive = Primitive.Double;
			})
		) */
	private int matchPrimitiveType_2_8(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.DOUBLE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				Terminal("BOOLEAN")
				Action({
					primitive = Primitive.Boolean;
				})
			)
			Sequence(
				Terminal("CHAR")
				Action({
					primitive = Primitive.Char;
				})
			)
			Sequence(
				Terminal("BYTE")
				Action({
					primitive = Primitive.Byte;
				})
			)
			Sequence(
				Terminal("SHORT")
				Action({
					primitive = Primitive.Short;
				})
			)
			Sequence(
				Terminal("INT")
				Action({
					primitive = Primitive.Int;
				})
			)
			Sequence(
				Terminal("LONG")
				Action({
					primitive = Primitive.Long;
				})
			)
			Sequence(
				Terminal("FLOAT")
				Action({
					primitive = Primitive.Float;
				})
			)
			Sequence(
				Terminal("DOUBLE")
				Action({
					primitive = Primitive.Double;
				})
			)
		) */
	private int matchPrimitiveType_2(int lookahead) {
		int newLookahead;
		newLookahead = matchPrimitiveType_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchPrimitiveType_2_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchPrimitiveType_2_3(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchPrimitiveType_2_4(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchPrimitiveType_2_5(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchPrimitiveType_2_6(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchPrimitiveType_2_7(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchPrimitiveType_2_8(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			Action({
				if (annotations == null) {
		run();
		annotations = emptyList();
	}
			})
			Choice(
				Sequence(
					Terminal("BOOLEAN")
					Action({
						primitive = Primitive.Boolean;
					})
				)
				Sequence(
					Terminal("CHAR")
					Action({
						primitive = Primitive.Char;
					})
				)
				Sequence(
					Terminal("BYTE")
					Action({
						primitive = Primitive.Byte;
					})
				)
				Sequence(
					Terminal("SHORT")
					Action({
						primitive = Primitive.Short;
					})
				)
				Sequence(
					Terminal("INT")
					Action({
						primitive = Primitive.Int;
					})
				)
				Sequence(
					Terminal("LONG")
					Action({
						primitive = Primitive.Long;
					})
				)
				Sequence(
					Terminal("FLOAT")
					Action({
						primitive = Primitive.Float;
					})
				)
				Sequence(
					Terminal("DOUBLE")
					Action({
						primitive = Primitive.Double;
					})
				)
			)
			Action({
				return dress(SPrimitiveType.make(annotations, primitive));
			})
		) */
	private int matchPrimitiveType(int lookahead) {
		lookahead = matchPrimitiveType_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("RPAREN")
			NonTerminal(ret, UnaryExpression)
			Action({
				ret = dress(SCastExpr.make(primitiveType, ret));
			})
		) */
	private int matchCastExpression_5_1_2_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchUnaryExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(
				NonTerminal(Annotations)
				Terminal("LBRACKET")
				Terminal("RBRACKET")
			)
			Action({
				run();
			})
			NonTerminal(annotations, Annotations)
			Terminal("LBRACKET")
			Terminal("RBRACKET")
			Action({
				arrayDims = append(arrayDims, dress(SArrayDim.make(annotations)));
			})
		) */
	private int matchArrayDimsMandatory_1_1(int lookahead) {
		lookahead = matchAnnotations(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.LBRACKET);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RBRACKET);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* OneOrMore(
			LookAhead(
				NonTerminal(Annotations)
				Terminal("LBRACKET")
				Terminal("RBRACKET")
			)
			Action({
				run();
			})
			NonTerminal(annotations, Annotations)
			Terminal("LBRACKET")
			Terminal("RBRACKET")
			Action({
				arrayDims = append(arrayDims, dress(SArrayDim.make(annotations)));
			})
		) */
	private int matchArrayDimsMandatory_1(int lookahead) {
		int newLookahead;
		newLookahead = matchArrayDimsMandatory_1_1(lookahead);
		if (newLookahead == -1)
			return -1;
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchArrayDimsMandatory_1_1(lookahead);
		}
		return lookahead;
	}

	/* Sequence(
			OneOrMore(
				LookAhead(
					NonTerminal(Annotations)
					Terminal("LBRACKET")
					Terminal("RBRACKET")
				)
				Action({
					run();
				})
				NonTerminal(annotations, Annotations)
				Terminal("LBRACKET")
				Terminal("RBRACKET")
				Action({
					arrayDims = append(arrayDims, dress(SArrayDim.make(annotations)));
				})
			)
			Action({
				return arrayDims;
			})
		) */
	private int matchArrayDimsMandatory(int lookahead) {
		lookahead = matchArrayDimsMandatory_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(primitiveType, PrimitiveType)
			Action({
				lateRun();
			})
			NonTerminal(arrayDims, ArrayDimsMandatory)
			Action({
				type = dress(SArrayType.make(primitiveType, arrayDims));
			})
		) */
	private int matchReferenceType_1_1(int lookahead) {
		lookahead = matchPrimitiveType(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchArrayDimsMandatory(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("EXTENDS")
			Action({
				run();
			})
			NonTerminal(boundAnnotations, Annotations)
			NonTerminal(ext, ReferenceType)
		) */
	private int matchWildcard_3_1_1_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.EXTENDS);
		if (lookahead == -1)
			return -1;
		lookahead = matchAnnotations(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchReferenceType(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("SUPER")
			Action({
				run();
			})
			NonTerminal(boundAnnotations, Annotations)
			NonTerminal(sup, ReferenceType)
		) */
	private int matchWildcard_3_1_1_2(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.SUPER);
		if (lookahead == -1)
			return -1;
		lookahead = matchAnnotations(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchReferenceType(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				Terminal("EXTENDS")
				Action({
					run();
				})
				NonTerminal(boundAnnotations, Annotations)
				NonTerminal(ext, ReferenceType)
			)
			Sequence(
				Terminal("SUPER")
				Action({
					run();
				})
				NonTerminal(boundAnnotations, Annotations)
				NonTerminal(sup, ReferenceType)
			)
		) */
	private int matchWildcard_3_1_1(int lookahead) {
		int newLookahead;
		newLookahead = matchWildcard_3_1_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchWildcard_3_1_1_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			Choice(
				Sequence(
					Terminal("EXTENDS")
					Action({
						run();
					})
					NonTerminal(boundAnnotations, Annotations)
					NonTerminal(ext, ReferenceType)
				)
				Sequence(
					Terminal("SUPER")
					Action({
						run();
					})
					NonTerminal(boundAnnotations, Annotations)
					NonTerminal(sup, ReferenceType)
				)
			)
		) */
	private int matchWildcard_3_1(int lookahead) {
		lookahead = matchWildcard_3_1_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			Choice(
				Sequence(
					Terminal("EXTENDS")
					Action({
						run();
					})
					NonTerminal(boundAnnotations, Annotations)
					NonTerminal(ext, ReferenceType)
				)
				Sequence(
					Terminal("SUPER")
					Action({
						run();
					})
					NonTerminal(boundAnnotations, Annotations)
					NonTerminal(sup, ReferenceType)
				)
			)
		) */
	private int matchWildcard_3(int lookahead) {
		int newLookahead;
		newLookahead = matchWildcard_3_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			Action({
				if (annotations == null) {
		run();
		annotations = emptyList();
	}
			})
			Terminal("HOOK")
			ZeroOrOne(
				Choice(
					Sequence(
						Terminal("EXTENDS")
						Action({
							run();
						})
						NonTerminal(boundAnnotations, Annotations)
						NonTerminal(ext, ReferenceType)
					)
					Sequence(
						Terminal("SUPER")
						Action({
							run();
						})
						NonTerminal(boundAnnotations, Annotations)
						NonTerminal(sup, ReferenceType)
					)
				)
			)
			Action({
				return dress(SWildcardType.make(annotations, optionOf(ext), optionOf(sup)));
			})
		) */
	private int matchWildcard(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.HOOK);
		if (lookahead == -1)
			return -1;
		lookahead = matchWildcard_3(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			NonTerminal(ret, ReferenceType)
			NonTerminal(ret, Wildcard)
		) */
	private int matchTypeArgument_3(int lookahead) {
		int newLookahead;
		newLookahead = matchReferenceType(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchWildcard(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			Action({
				run();
			})
			NonTerminal(annotations, Annotations)
			Choice(
				NonTerminal(ret, ReferenceType)
				NonTerminal(ret, Wildcard)
			)
			Action({
				return ret;
			})
		) */
	private int matchTypeArgument(int lookahead) {
		lookahead = matchAnnotations(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchTypeArgument_3(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("COMMA")
			NonTerminal(type, TypeArgument)
			Action({
				ret = append(ret, type);
			})
		) */
	private int matchTypeArgumentList_1_3_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.COMMA);
		if (lookahead == -1)
			return -1;
		lookahead = matchTypeArgument(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrMore(
			Terminal("COMMA")
			NonTerminal(type, TypeArgument)
			Action({
				ret = append(ret, type);
			})
		) */
	private int matchTypeArgumentList_1_3(int lookahead) {
		int newLookahead;
		newLookahead = matchTypeArgumentList_1_3_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchTypeArgumentList_1_3_1(lookahead);
		}
		return lookahead;
	}

	/* Sequence(
			NonTerminal(type, TypeArgument)
			Action({
				ret = append(ret, type);
			})
			ZeroOrMore(
				Terminal("COMMA")
				NonTerminal(type, TypeArgument)
				Action({
					ret = append(ret, type);
				})
			)
			Action({
				return ret;
			})
		) */
	private int matchTypeArgumentList_1(int lookahead) {
		lookahead = matchTypeArgument(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchTypeArgumentList_1_3(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(			quotesMode
	)
			Terminal("NODE_LIST_VARIABLE")
			Action({
				return makeVar();
			})
		) */
	private int matchTypeArgumentList_2(int lookahead) {
		lookahead = quotesMode ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.NODE_LIST_VARIABLE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				NonTerminal(type, TypeArgument)
				Action({
					ret = append(ret, type);
				})
				ZeroOrMore(
					Terminal("COMMA")
					NonTerminal(type, TypeArgument)
					Action({
						ret = append(ret, type);
					})
				)
				Action({
					return ret;
				})
			)
			Sequence(
				LookAhead(				quotesMode
	)
				Terminal("NODE_LIST_VARIABLE")
				Action({
					return makeVar();
				})
			)
		) */
	private int matchTypeArgumentList(int lookahead) {
		int newLookahead;
		newLookahead = matchTypeArgumentList_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchTypeArgumentList_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			NonTerminal(ret, TypeArgumentList)
		) */
	private int matchTypeArgumentsOrDiamond_2_1(int lookahead) {
		lookahead = matchTypeArgumentList(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			NonTerminal(ret, TypeArgumentList)
		) */
	private int matchTypeArgumentsOrDiamond_2(int lookahead) {
		int newLookahead;
		newLookahead = matchTypeArgumentsOrDiamond_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			Terminal("LT")
			ZeroOrOne(
				NonTerminal(ret, TypeArgumentList)
			)
			Terminal("GT")
			Action({
				return ret;
			})
		) */
	private int matchTypeArgumentsOrDiamond(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.LT);
		if (lookahead == -1)
			return -1;
		lookahead = matchTypeArgumentsOrDiamond_2(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.GT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(2)
			NonTerminal(typeArgs, TypeArgumentsOrDiamond)
		) */
	private int matchQualifiedType_3_1(int lookahead) {
		lookahead = matchTypeArgumentsOrDiamond(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			LookAhead(2)
			NonTerminal(typeArgs, TypeArgumentsOrDiamond)
		) */
	private int matchQualifiedType_3(int lookahead) {
		int newLookahead;
		newLookahead = matchQualifiedType_3_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			LookAhead(2)
			NonTerminal(typeArgs, TypeArgumentsOrDiamond)
		) */
	private int matchQualifiedType_5_1_7_1(int lookahead) {
		lookahead = matchTypeArgumentsOrDiamond(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			LookAhead(2)
			NonTerminal(typeArgs, TypeArgumentsOrDiamond)
		) */
	private int matchQualifiedType_5_1_7(int lookahead) {
		int newLookahead;
		newLookahead = matchQualifiedType_5_1_7_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			LookAhead(2)
			Action({
				lateRun();
			})
			Terminal("DOT")
			Action({
				scope = optionOf(ret);
			})
			NonTerminal(annotations, Annotations)
			NonTerminal(name, Name)
			ZeroOrOne(
				LookAhead(2)
				NonTerminal(typeArgs, TypeArgumentsOrDiamond)
			)
			Action({
				ret = dress(SQualifiedType.make(annotations, scope, name, optionOf(typeArgs)));
			})
		) */
	private int matchQualifiedType_5_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.DOT);
		if (lookahead == -1)
			return -1;
		lookahead = matchAnnotations(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchQualifiedType_5_1_7(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrMore(
			LookAhead(2)
			Action({
				lateRun();
			})
			Terminal("DOT")
			Action({
				scope = optionOf(ret);
			})
			NonTerminal(annotations, Annotations)
			NonTerminal(name, Name)
			ZeroOrOne(
				LookAhead(2)
				NonTerminal(typeArgs, TypeArgumentsOrDiamond)
			)
			Action({
				ret = dress(SQualifiedType.make(annotations, scope, name, optionOf(typeArgs)));
			})
		) */
	private int matchQualifiedType_5(int lookahead) {
		int newLookahead;
		newLookahead = matchQualifiedType_5_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchQualifiedType_5_1(lookahead);
		}
		return lookahead;
	}

	/* Sequence(
			Action({
				if (annotations == null) {
		run();
		annotations = emptyList();
	}
			})
			NonTerminal(name, Name)
			ZeroOrOne(
				LookAhead(2)
				NonTerminal(typeArgs, TypeArgumentsOrDiamond)
			)
			Action({
				ret = dress(SQualifiedType.make(annotations, scope, name, optionOf(typeArgs)));
			})
			ZeroOrMore(
				LookAhead(2)
				Action({
					lateRun();
				})
				Terminal("DOT")
				Action({
					scope = optionOf(ret);
				})
				NonTerminal(annotations, Annotations)
				NonTerminal(name, Name)
				ZeroOrOne(
					LookAhead(2)
					NonTerminal(typeArgs, TypeArgumentsOrDiamond)
				)
				Action({
					ret = dress(SQualifiedType.make(annotations, scope, name, optionOf(typeArgs)));
				})
			)
			Action({
				return ret;
			})
		) */
	private int matchQualifiedType(int lookahead) {
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchQualifiedType_3(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchQualifiedType_5(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(
				NonTerminal(Annotations)
				Terminal("LBRACKET")
			)
			Action({
				lateRun();
			})
			NonTerminal(arrayDims, ArrayDimsMandatory)
			Action({
				type = dress(SArrayType.make(type, arrayDims));
			})
		) */
	private int matchReferenceType_1_2_2_1(int lookahead) {
		lookahead = matchArrayDimsMandatory(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			LookAhead(
				NonTerminal(Annotations)
				Terminal("LBRACKET")
			)
			Action({
				lateRun();
			})
			NonTerminal(arrayDims, ArrayDimsMandatory)
			Action({
				type = dress(SArrayType.make(type, arrayDims));
			})
		) */
	private int matchReferenceType_1_2_2(int lookahead) {
		int newLookahead;
		newLookahead = matchReferenceType_1_2_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(type, QualifiedType)
			ZeroOrOne(
				LookAhead(
					NonTerminal(Annotations)
					Terminal("LBRACKET")
				)
				Action({
					lateRun();
				})
				NonTerminal(arrayDims, ArrayDimsMandatory)
				Action({
					type = dress(SArrayType.make(type, arrayDims));
				})
			)
		) */
	private int matchReferenceType_1_2(int lookahead) {
		lookahead = matchQualifiedType(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchReferenceType_1_2_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				NonTerminal(primitiveType, PrimitiveType)
				Action({
					lateRun();
				})
				NonTerminal(arrayDims, ArrayDimsMandatory)
				Action({
					type = dress(SArrayType.make(primitiveType, arrayDims));
				})
			)
			Sequence(
				NonTerminal(type, QualifiedType)
				ZeroOrOne(
					LookAhead(
						NonTerminal(Annotations)
						Terminal("LBRACKET")
					)
					Action({
						lateRun();
					})
					NonTerminal(arrayDims, ArrayDimsMandatory)
					Action({
						type = dress(SArrayType.make(type, arrayDims));
					})
				)
			)
		) */
	private int matchReferenceType_1(int lookahead) {
		int newLookahead;
		newLookahead = matchReferenceType_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchReferenceType_1_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			Choice(
				Sequence(
					NonTerminal(primitiveType, PrimitiveType)
					Action({
						lateRun();
					})
					NonTerminal(arrayDims, ArrayDimsMandatory)
					Action({
						type = dress(SArrayType.make(primitiveType, arrayDims));
					})
				)
				Sequence(
					NonTerminal(type, QualifiedType)
					ZeroOrOne(
						LookAhead(
							NonTerminal(Annotations)
							Terminal("LBRACKET")
						)
						Action({
							lateRun();
						})
						NonTerminal(arrayDims, ArrayDimsMandatory)
						Action({
							type = dress(SArrayType.make(type, arrayDims));
						})
					)
				)
			)
			Action({
				return type;
			})
		) */
	private int matchReferenceType(int lookahead) {
		lookahead = matchReferenceType_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("BIT_AND")
			Action({
				run();
			})
			NonTerminal(annotations, Annotations)
			NonTerminal(type, ReferenceType)
			Action({
				types = append(types, type);
			})
		) */
	private int matchReferenceCastTypeRest_1_1_4_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.BIT_AND);
		if (lookahead == -1)
			return -1;
		lookahead = matchAnnotations(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchReferenceType(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* OneOrMore(
			Terminal("BIT_AND")
			Action({
				run();
			})
			NonTerminal(annotations, Annotations)
			NonTerminal(type, ReferenceType)
			Action({
				types = append(types, type);
			})
		) */
	private int matchReferenceCastTypeRest_1_1_4(int lookahead) {
		int newLookahead;
		newLookahead = matchReferenceCastTypeRest_1_1_4_1(lookahead);
		if (newLookahead == -1)
			return -1;
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchReferenceCastTypeRest_1_1_4_1(lookahead);
		}
		return lookahead;
	}

	/* Sequence(
			LookAhead(
				Terminal("BIT_AND")
			)
			Action({
				types = append(types, type);
			})
			Action({
				lateRun();
			})
			OneOrMore(
				Terminal("BIT_AND")
				Action({
					run();
				})
				NonTerminal(annotations, Annotations)
				NonTerminal(type, ReferenceType)
				Action({
					types = append(types, type);
				})
			)
			Action({
				type = dress(SIntersectionType.make(types));
			})
		) */
	private int matchReferenceCastTypeRest_1_1(int lookahead) {
		lookahead = matchReferenceCastTypeRest_1_1_4(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			LookAhead(
				Terminal("BIT_AND")
			)
			Action({
				types = append(types, type);
			})
			Action({
				lateRun();
			})
			OneOrMore(
				Terminal("BIT_AND")
				Action({
					run();
				})
				NonTerminal(annotations, Annotations)
				NonTerminal(type, ReferenceType)
				Action({
					types = append(types, type);
				})
			)
			Action({
				type = dress(SIntersectionType.make(types));
			})
		) */
	private int matchReferenceCastTypeRest_1(int lookahead) {
		int newLookahead;
		newLookahead = matchReferenceCastTypeRest_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			ZeroOrOne(
				LookAhead(
					Terminal("BIT_AND")
				)
				Action({
					types = append(types, type);
				})
				Action({
					lateRun();
				})
				OneOrMore(
					Terminal("BIT_AND")
					Action({
						run();
					})
					NonTerminal(annotations, Annotations)
					NonTerminal(type, ReferenceType)
					Action({
						types = append(types, type);
					})
				)
				Action({
					type = dress(SIntersectionType.make(types));
				})
			)
			Action({
				return type;
			})
		) */
	private int matchReferenceCastTypeRest(int lookahead) {
		lookahead = matchReferenceCastTypeRest_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Action({
				lateRun();
			})
			NonTerminal(arrayDims, ArrayDimsMandatory)
			Action({
				type = dress(SArrayType.make(primitiveType, arrayDims));
			})
			NonTerminal(type, ReferenceCastTypeRest)
			Terminal("RPAREN")
			NonTerminal(ret, UnaryExpressionNotPlusMinus)
			Action({
				ret = dress(SCastExpr.make(type, ret));
			})
		) */
	private int matchCastExpression_5_1_2_2(int lookahead) {
		lookahead = matchArrayDimsMandatory(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchReferenceCastTypeRest(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchUnaryExpressionNotPlusMinus(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				Terminal("RPAREN")
				NonTerminal(ret, UnaryExpression)
				Action({
					ret = dress(SCastExpr.make(primitiveType, ret));
				})
			)
			Sequence(
				Action({
					lateRun();
				})
				NonTerminal(arrayDims, ArrayDimsMandatory)
				Action({
					type = dress(SArrayType.make(primitiveType, arrayDims));
				})
				NonTerminal(type, ReferenceCastTypeRest)
				Terminal("RPAREN")
				NonTerminal(ret, UnaryExpressionNotPlusMinus)
				Action({
					ret = dress(SCastExpr.make(type, ret));
				})
			)
		) */
	private int matchCastExpression_5_1_2(int lookahead) {
		int newLookahead;
		newLookahead = matchCastExpression_5_1_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchCastExpression_5_1_2_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			NonTerminal(primitiveType, PrimitiveType)
			Choice(
				Sequence(
					Terminal("RPAREN")
					NonTerminal(ret, UnaryExpression)
					Action({
						ret = dress(SCastExpr.make(primitiveType, ret));
					})
				)
				Sequence(
					Action({
						lateRun();
					})
					NonTerminal(arrayDims, ArrayDimsMandatory)
					Action({
						type = dress(SArrayType.make(primitiveType, arrayDims));
					})
					NonTerminal(type, ReferenceCastTypeRest)
					Terminal("RPAREN")
					NonTerminal(ret, UnaryExpressionNotPlusMinus)
					Action({
						ret = dress(SCastExpr.make(type, ret));
					})
				)
			)
		) */
	private int matchCastExpression_5_1(int lookahead) {
		lookahead = matchPrimitiveType(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchCastExpression_5_1_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(
				NonTerminal(Annotations)
				Terminal("LBRACKET")
			)
			Action({
				lateRun();
			})
			NonTerminal(arrayDims, ArrayDimsMandatory)
			Action({
				type = dress(SArrayType.make(type, arrayDims));
			})
		) */
	private int matchCastExpression_5_2_2_1(int lookahead) {
		lookahead = matchArrayDimsMandatory(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			LookAhead(
				NonTerminal(Annotations)
				Terminal("LBRACKET")
			)
			Action({
				lateRun();
			})
			NonTerminal(arrayDims, ArrayDimsMandatory)
			Action({
				type = dress(SArrayType.make(type, arrayDims));
			})
		) */
	private int matchCastExpression_5_2_2(int lookahead) {
		int newLookahead;
		newLookahead = matchCastExpression_5_2_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(type, QualifiedType)
			ZeroOrOne(
				LookAhead(
					NonTerminal(Annotations)
					Terminal("LBRACKET")
				)
				Action({
					lateRun();
				})
				NonTerminal(arrayDims, ArrayDimsMandatory)
				Action({
					type = dress(SArrayType.make(type, arrayDims));
				})
			)
			NonTerminal(type, ReferenceCastTypeRest)
			Terminal("RPAREN")
			NonTerminal(ret, UnaryExpressionNotPlusMinus)
			Action({
				ret = dress(SCastExpr.make(type, ret));
			})
		) */
	private int matchCastExpression_5_2(int lookahead) {
		lookahead = matchQualifiedType(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchCastExpression_5_2_2(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchReferenceCastTypeRest(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchUnaryExpressionNotPlusMinus(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				NonTerminal(primitiveType, PrimitiveType)
				Choice(
					Sequence(
						Terminal("RPAREN")
						NonTerminal(ret, UnaryExpression)
						Action({
							ret = dress(SCastExpr.make(primitiveType, ret));
						})
					)
					Sequence(
						Action({
							lateRun();
						})
						NonTerminal(arrayDims, ArrayDimsMandatory)
						Action({
							type = dress(SArrayType.make(primitiveType, arrayDims));
						})
						NonTerminal(type, ReferenceCastTypeRest)
						Terminal("RPAREN")
						NonTerminal(ret, UnaryExpressionNotPlusMinus)
						Action({
							ret = dress(SCastExpr.make(type, ret));
						})
					)
				)
			)
			Sequence(
				NonTerminal(type, QualifiedType)
				ZeroOrOne(
					LookAhead(
						NonTerminal(Annotations)
						Terminal("LBRACKET")
					)
					Action({
						lateRun();
					})
					NonTerminal(arrayDims, ArrayDimsMandatory)
					Action({
						type = dress(SArrayType.make(type, arrayDims));
					})
				)
				NonTerminal(type, ReferenceCastTypeRest)
				Terminal("RPAREN")
				NonTerminal(ret, UnaryExpressionNotPlusMinus)
				Action({
					ret = dress(SCastExpr.make(type, ret));
				})
			)
		) */
	private int matchCastExpression_5(int lookahead) {
		int newLookahead;
		newLookahead = matchCastExpression_5_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchCastExpression_5_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			Action({
				run();
			})
			Terminal("LPAREN")
			Action({
				run();
			})
			NonTerminal(annotations, Annotations)
			Choice(
				Sequence(
					NonTerminal(primitiveType, PrimitiveType)
					Choice(
						Sequence(
							Terminal("RPAREN")
							NonTerminal(ret, UnaryExpression)
							Action({
								ret = dress(SCastExpr.make(primitiveType, ret));
							})
						)
						Sequence(
							Action({
								lateRun();
							})
							NonTerminal(arrayDims, ArrayDimsMandatory)
							Action({
								type = dress(SArrayType.make(primitiveType, arrayDims));
							})
							NonTerminal(type, ReferenceCastTypeRest)
							Terminal("RPAREN")
							NonTerminal(ret, UnaryExpressionNotPlusMinus)
							Action({
								ret = dress(SCastExpr.make(type, ret));
							})
						)
					)
				)
				Sequence(
					NonTerminal(type, QualifiedType)
					ZeroOrOne(
						LookAhead(
							NonTerminal(Annotations)
							Terminal("LBRACKET")
						)
						Action({
							lateRun();
						})
						NonTerminal(arrayDims, ArrayDimsMandatory)
						Action({
							type = dress(SArrayType.make(type, arrayDims));
						})
					)
					NonTerminal(type, ReferenceCastTypeRest)
					Terminal("RPAREN")
					NonTerminal(ret, UnaryExpressionNotPlusMinus)
					Action({
						ret = dress(SCastExpr.make(type, ret));
					})
				)
			)
			Action({
				return ret;
			})
		) */
	private int matchCastExpression(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchAnnotations(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchCastExpression_5(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(
				NonTerminal(CastExpression)
			)
			NonTerminal(ret, CastExpression)
		) */
	private int matchUnaryExpressionNotPlusMinus_1_2(int lookahead) {
		lookahead = matchCastExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal(literal, "INTEGER_LITERAL")
			Action({
				ret = SLiteralExpr.make(Integer.class, literal.image);
			})
		) */
	private int matchLiteral_2_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.INTEGER_LITERAL);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal(literal, "LONG_LITERAL")
			Action({
				ret = SLiteralExpr.make(Long.class, literal.image);
			})
		) */
	private int matchLiteral_2_2(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.LONG_LITERAL);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal(literal, "FLOAT_LITERAL")
			Action({
				ret = SLiteralExpr.make(Float.class, literal.image);
			})
		) */
	private int matchLiteral_2_3(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.FLOAT_LITERAL);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal(literal, "DOUBLE_LITERAL")
			Action({
				ret = SLiteralExpr.make(Double.class, literal.image);
			})
		) */
	private int matchLiteral_2_4(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.DOUBLE_LITERAL);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal(literal, "CHARACTER_LITERAL")
			Action({
				ret = SLiteralExpr.make(Character.class, literal.image);
			})
		) */
	private int matchLiteral_2_5(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.CHARACTER_LITERAL);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal(literal, "STRING_LITERAL")
			Action({
				ret = SLiteralExpr.make(String.class, literal.image);
			})
		) */
	private int matchLiteral_2_6(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.STRING_LITERAL);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal(literal, "TRUE")
			Action({
				ret = SLiteralExpr.make(Boolean.class, literal.image);
			})
		) */
	private int matchLiteral_2_7(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.TRUE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal(literal, "FALSE")
			Action({
				ret = SLiteralExpr.make(Boolean.class, literal.image);
			})
		) */
	private int matchLiteral_2_8(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.FALSE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal(literal, "NULL")
			Action({
				ret = SLiteralExpr.make(Void.class, literal.image);
			})
		) */
	private int matchLiteral_2_9(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.NULL);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				Terminal(literal, "INTEGER_LITERAL")
				Action({
					ret = SLiteralExpr.make(Integer.class, literal.image);
				})
			)
			Sequence(
				Terminal(literal, "LONG_LITERAL")
				Action({
					ret = SLiteralExpr.make(Long.class, literal.image);
				})
			)
			Sequence(
				Terminal(literal, "FLOAT_LITERAL")
				Action({
					ret = SLiteralExpr.make(Float.class, literal.image);
				})
			)
			Sequence(
				Terminal(literal, "DOUBLE_LITERAL")
				Action({
					ret = SLiteralExpr.make(Double.class, literal.image);
				})
			)
			Sequence(
				Terminal(literal, "CHARACTER_LITERAL")
				Action({
					ret = SLiteralExpr.make(Character.class, literal.image);
				})
			)
			Sequence(
				Terminal(literal, "STRING_LITERAL")
				Action({
					ret = SLiteralExpr.make(String.class, literal.image);
				})
			)
			Sequence(
				Terminal(literal, "TRUE")
				Action({
					ret = SLiteralExpr.make(Boolean.class, literal.image);
				})
			)
			Sequence(
				Terminal(literal, "FALSE")
				Action({
					ret = SLiteralExpr.make(Boolean.class, literal.image);
				})
			)
			Sequence(
				Terminal(literal, "NULL")
				Action({
					ret = SLiteralExpr.make(Void.class, literal.image);
				})
			)
		) */
	private int matchLiteral_2(int lookahead) {
		int newLookahead;
		newLookahead = matchLiteral_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchLiteral_2_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchLiteral_2_3(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchLiteral_2_4(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchLiteral_2_5(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchLiteral_2_6(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchLiteral_2_7(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchLiteral_2_8(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchLiteral_2_9(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			Action({
				run();
			})
			Choice(
				Sequence(
					Terminal(literal, "INTEGER_LITERAL")
					Action({
						ret = SLiteralExpr.make(Integer.class, literal.image);
					})
				)
				Sequence(
					Terminal(literal, "LONG_LITERAL")
					Action({
						ret = SLiteralExpr.make(Long.class, literal.image);
					})
				)
				Sequence(
					Terminal(literal, "FLOAT_LITERAL")
					Action({
						ret = SLiteralExpr.make(Float.class, literal.image);
					})
				)
				Sequence(
					Terminal(literal, "DOUBLE_LITERAL")
					Action({
						ret = SLiteralExpr.make(Double.class, literal.image);
					})
				)
				Sequence(
					Terminal(literal, "CHARACTER_LITERAL")
					Action({
						ret = SLiteralExpr.make(Character.class, literal.image);
					})
				)
				Sequence(
					Terminal(literal, "STRING_LITERAL")
					Action({
						ret = SLiteralExpr.make(String.class, literal.image);
					})
				)
				Sequence(
					Terminal(literal, "TRUE")
					Action({
						ret = SLiteralExpr.make(Boolean.class, literal.image);
					})
				)
				Sequence(
					Terminal(literal, "FALSE")
					Action({
						ret = SLiteralExpr.make(Boolean.class, literal.image);
					})
				)
				Sequence(
					Terminal(literal, "NULL")
					Action({
						ret = SLiteralExpr.make(Void.class, literal.image);
					})
				)
			)
			Action({
				return dress(ret);
			})
		) */
	private int matchLiteral(int lookahead) {
		lookahead = matchLiteral_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Action({
				run();
			})
			Terminal("THIS")
			Action({
				ret = dress(SThisExpr.make(none()));
			})
		) */
	private int matchPrimaryPrefix_1_2(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.THIS);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(ret, TypeArgumentList)
		) */
	private int matchTypeArguments_2_1(int lookahead) {
		lookahead = matchTypeArgumentList(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			NonTerminal(ret, TypeArgumentList)
		) */
	private int matchTypeArguments_2(int lookahead) {
		int newLookahead;
		newLookahead = matchTypeArguments_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			Terminal("LT")
			ZeroOrOne(
				NonTerminal(ret, TypeArgumentList)
			)
			Terminal("GT")
			Action({
				return ret;
			})
		) */
	private int matchTypeArguments(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.LT);
		if (lookahead == -1)
			return -1;
		lookahead = matchTypeArguments_2(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.GT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(typeArgs, TypeArguments)
		) */
	private int matchMethodInvocation_1_1(int lookahead) {
		lookahead = matchTypeArguments(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			NonTerminal(typeArgs, TypeArguments)
		) */
	private int matchMethodInvocation_1(int lookahead) {
		int newLookahead;
		newLookahead = matchMethodInvocation_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(expr, Expression)
			Action({
				ret = dress(SLambdaExpr.make(parameters, parenthesis, left(expr)));
			})
		) */
	private int matchLambdaBody_1_1(int lookahead) {
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("PUBLIC")
			Action({
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Public));
			})
		) */
	private int matchModifiersNoDefault_1_1_2_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.PUBLIC);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("PROTECTED")
			Action({
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Protected));
			})
		) */
	private int matchModifiersNoDefault_1_1_2_2(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.PROTECTED);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("PRIVATE")
			Action({
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Private));
			})
		) */
	private int matchModifiersNoDefault_1_1_2_3(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.PRIVATE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("ABSTRACT")
			Action({
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Abstract));
			})
		) */
	private int matchModifiersNoDefault_1_1_2_4(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.ABSTRACT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("STATIC")
			Action({
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Static));
			})
		) */
	private int matchModifiersNoDefault_1_1_2_5(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.STATIC);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("FINAL")
			Action({
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Final));
			})
		) */
	private int matchModifiersNoDefault_1_1_2_6(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.FINAL);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("TRANSIENT")
			Action({
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Transient));
			})
		) */
	private int matchModifiersNoDefault_1_1_2_7(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.TRANSIENT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("VOLATILE")
			Action({
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Volatile));
			})
		) */
	private int matchModifiersNoDefault_1_1_2_8(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.VOLATILE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("SYNCHRONIZED")
			Action({
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Synchronized));
			})
		) */
	private int matchModifiersNoDefault_1_1_2_9(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.SYNCHRONIZED);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("NATIVE")
			Action({
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Native));
			})
		) */
	private int matchModifiersNoDefault_1_1_2_10(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.NATIVE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("STRICTFP")
			Action({
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.StrictFP));
			})
		) */
	private int matchModifiersNoDefault_1_1_2_11(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.STRICTFP);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(ann, Annotation)
			Action({
				modifiers = append(modifiers, ann);
			})
		) */
	private int matchModifiersNoDefault_1_1_2_12(int lookahead) {
		lookahead = matchAnnotation(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				Terminal("PUBLIC")
				Action({
					modifiers = append(modifiers, SModifier.make(ModifierKeyword.Public));
				})
			)
			Sequence(
				Terminal("PROTECTED")
				Action({
					modifiers = append(modifiers, SModifier.make(ModifierKeyword.Protected));
				})
			)
			Sequence(
				Terminal("PRIVATE")
				Action({
					modifiers = append(modifiers, SModifier.make(ModifierKeyword.Private));
				})
			)
			Sequence(
				Terminal("ABSTRACT")
				Action({
					modifiers = append(modifiers, SModifier.make(ModifierKeyword.Abstract));
				})
			)
			Sequence(
				Terminal("STATIC")
				Action({
					modifiers = append(modifiers, SModifier.make(ModifierKeyword.Static));
				})
			)
			Sequence(
				Terminal("FINAL")
				Action({
					modifiers = append(modifiers, SModifier.make(ModifierKeyword.Final));
				})
			)
			Sequence(
				Terminal("TRANSIENT")
				Action({
					modifiers = append(modifiers, SModifier.make(ModifierKeyword.Transient));
				})
			)
			Sequence(
				Terminal("VOLATILE")
				Action({
					modifiers = append(modifiers, SModifier.make(ModifierKeyword.Volatile));
				})
			)
			Sequence(
				Terminal("SYNCHRONIZED")
				Action({
					modifiers = append(modifiers, SModifier.make(ModifierKeyword.Synchronized));
				})
			)
			Sequence(
				Terminal("NATIVE")
				Action({
					modifiers = append(modifiers, SModifier.make(ModifierKeyword.Native));
				})
			)
			Sequence(
				Terminal("STRICTFP")
				Action({
					modifiers = append(modifiers, SModifier.make(ModifierKeyword.StrictFP));
				})
			)
			Sequence(
				NonTerminal(ann, Annotation)
				Action({
					modifiers = append(modifiers, ann);
				})
			)
		) */
	private int matchModifiersNoDefault_1_1_2(int lookahead) {
		int newLookahead;
		newLookahead = matchModifiersNoDefault_1_1_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchModifiersNoDefault_1_1_2_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchModifiersNoDefault_1_1_2_3(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchModifiersNoDefault_1_1_2_4(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchModifiersNoDefault_1_1_2_5(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchModifiersNoDefault_1_1_2_6(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchModifiersNoDefault_1_1_2_7(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchModifiersNoDefault_1_1_2_8(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchModifiersNoDefault_1_1_2_9(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchModifiersNoDefault_1_1_2_10(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchModifiersNoDefault_1_1_2_11(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchModifiersNoDefault_1_1_2_12(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			LookAhead(2)
			Choice(
				Sequence(
					Terminal("PUBLIC")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Public));
					})
				)
				Sequence(
					Terminal("PROTECTED")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Protected));
					})
				)
				Sequence(
					Terminal("PRIVATE")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Private));
					})
				)
				Sequence(
					Terminal("ABSTRACT")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Abstract));
					})
				)
				Sequence(
					Terminal("STATIC")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Static));
					})
				)
				Sequence(
					Terminal("FINAL")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Final));
					})
				)
				Sequence(
					Terminal("TRANSIENT")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Transient));
					})
				)
				Sequence(
					Terminal("VOLATILE")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Volatile));
					})
				)
				Sequence(
					Terminal("SYNCHRONIZED")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Synchronized));
					})
				)
				Sequence(
					Terminal("NATIVE")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Native));
					})
				)
				Sequence(
					Terminal("STRICTFP")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.StrictFP));
					})
				)
				Sequence(
					NonTerminal(ann, Annotation)
					Action({
						modifiers = append(modifiers, ann);
					})
				)
			)
		) */
	private int matchModifiersNoDefault_1_1(int lookahead) {
		lookahead = matchModifiersNoDefault_1_1_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrMore(
			LookAhead(2)
			Choice(
				Sequence(
					Terminal("PUBLIC")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Public));
					})
				)
				Sequence(
					Terminal("PROTECTED")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Protected));
					})
				)
				Sequence(
					Terminal("PRIVATE")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Private));
					})
				)
				Sequence(
					Terminal("ABSTRACT")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Abstract));
					})
				)
				Sequence(
					Terminal("STATIC")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Static));
					})
				)
				Sequence(
					Terminal("FINAL")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Final));
					})
				)
				Sequence(
					Terminal("TRANSIENT")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Transient));
					})
				)
				Sequence(
					Terminal("VOLATILE")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Volatile));
					})
				)
				Sequence(
					Terminal("SYNCHRONIZED")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Synchronized));
					})
				)
				Sequence(
					Terminal("NATIVE")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Native));
					})
				)
				Sequence(
					Terminal("STRICTFP")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.StrictFP));
					})
				)
				Sequence(
					NonTerminal(ann, Annotation)
					Action({
						modifiers = append(modifiers, ann);
					})
				)
			)
		) */
	private int matchModifiersNoDefault_1(int lookahead) {
		int newLookahead;
		newLookahead = matchModifiersNoDefault_1_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchModifiersNoDefault_1_1(lookahead);
		}
		return lookahead;
	}

	/* Sequence(
			ZeroOrMore(
				LookAhead(2)
				Choice(
					Sequence(
						Terminal("PUBLIC")
						Action({
							modifiers = append(modifiers, SModifier.make(ModifierKeyword.Public));
						})
					)
					Sequence(
						Terminal("PROTECTED")
						Action({
							modifiers = append(modifiers, SModifier.make(ModifierKeyword.Protected));
						})
					)
					Sequence(
						Terminal("PRIVATE")
						Action({
							modifiers = append(modifiers, SModifier.make(ModifierKeyword.Private));
						})
					)
					Sequence(
						Terminal("ABSTRACT")
						Action({
							modifiers = append(modifiers, SModifier.make(ModifierKeyword.Abstract));
						})
					)
					Sequence(
						Terminal("STATIC")
						Action({
							modifiers = append(modifiers, SModifier.make(ModifierKeyword.Static));
						})
					)
					Sequence(
						Terminal("FINAL")
						Action({
							modifiers = append(modifiers, SModifier.make(ModifierKeyword.Final));
						})
					)
					Sequence(
						Terminal("TRANSIENT")
						Action({
							modifiers = append(modifiers, SModifier.make(ModifierKeyword.Transient));
						})
					)
					Sequence(
						Terminal("VOLATILE")
						Action({
							modifiers = append(modifiers, SModifier.make(ModifierKeyword.Volatile));
						})
					)
					Sequence(
						Terminal("SYNCHRONIZED")
						Action({
							modifiers = append(modifiers, SModifier.make(ModifierKeyword.Synchronized));
						})
					)
					Sequence(
						Terminal("NATIVE")
						Action({
							modifiers = append(modifiers, SModifier.make(ModifierKeyword.Native));
						})
					)
					Sequence(
						Terminal("STRICTFP")
						Action({
							modifiers = append(modifiers, SModifier.make(ModifierKeyword.StrictFP));
						})
					)
					Sequence(
						NonTerminal(ann, Annotation)
						Action({
							modifiers = append(modifiers, ann);
						})
					)
				)
			)
			Action({
				return modifiers;
			})
		) */
	private int matchModifiersNoDefault(int lookahead) {
		lookahead = matchModifiersNoDefault_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Action({
				run();
			})
			NonTerminal(annotations, Annotations)
			NonTerminal(ret, QualifiedType)
			Action({
				return ret;
			})
		) */
	private int matchAnnotatedQualifiedType(int lookahead) {
		lookahead = matchAnnotations(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchQualifiedType(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("BIT_AND")
			NonTerminal(cit, AnnotatedQualifiedType)
			Action({
				ret = append(ret, cit);
			})
		) */
	private int matchTypeBounds_2_1_3_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.BIT_AND);
		if (lookahead == -1)
			return -1;
		lookahead = matchAnnotatedQualifiedType(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrMore(
			Terminal("BIT_AND")
			NonTerminal(cit, AnnotatedQualifiedType)
			Action({
				ret = append(ret, cit);
			})
		) */
	private int matchTypeBounds_2_1_3(int lookahead) {
		int newLookahead;
		newLookahead = matchTypeBounds_2_1_3_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchTypeBounds_2_1_3_1(lookahead);
		}
		return lookahead;
	}

	/* Sequence(
			NonTerminal(cit, AnnotatedQualifiedType)
			Action({
				ret = append(ret, cit);
			})
			ZeroOrMore(
				Terminal("BIT_AND")
				NonTerminal(cit, AnnotatedQualifiedType)
				Action({
					ret = append(ret, cit);
				})
			)
		) */
	private int matchTypeBounds_2_1(int lookahead) {
		lookahead = matchAnnotatedQualifiedType(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchTypeBounds_2_1_3(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("NODE_LIST_VARIABLE")
			Action({
				return makeVar();
			})
		) */
	private int matchNodeListVar(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.NODE_LIST_VARIABLE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(			quotesMode
	)
			NonTerminal(ret, NodeListVar)
		) */
	private int matchTypeBounds_2_2(int lookahead) {
		lookahead = quotesMode ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = matchNodeListVar(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				NonTerminal(cit, AnnotatedQualifiedType)
				Action({
					ret = append(ret, cit);
				})
				ZeroOrMore(
					Terminal("BIT_AND")
					NonTerminal(cit, AnnotatedQualifiedType)
					Action({
						ret = append(ret, cit);
					})
				)
			)
			Sequence(
				LookAhead(				quotesMode
	)
				NonTerminal(ret, NodeListVar)
			)
		) */
	private int matchTypeBounds_2(int lookahead) {
		int newLookahead;
		newLookahead = matchTypeBounds_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchTypeBounds_2_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			Terminal("EXTENDS")
			Choice(
				Sequence(
					NonTerminal(cit, AnnotatedQualifiedType)
					Action({
						ret = append(ret, cit);
					})
					ZeroOrMore(
						Terminal("BIT_AND")
						NonTerminal(cit, AnnotatedQualifiedType)
						Action({
							ret = append(ret, cit);
						})
					)
				)
				Sequence(
					LookAhead(					quotesMode
	)
					NonTerminal(ret, NodeListVar)
				)
			)
			Action({
				return ret;
			})
		) */
	private int matchTypeBounds(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.EXTENDS);
		if (lookahead == -1)
			return -1;
		lookahead = matchTypeBounds_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(typeBounds, TypeBounds)
		) */
	private int matchTypeParameter_4_1(int lookahead) {
		lookahead = matchTypeBounds(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			NonTerminal(typeBounds, TypeBounds)
		) */
	private int matchTypeParameter_4(int lookahead) {
		int newLookahead;
		newLookahead = matchTypeParameter_4_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			Action({
				run();
			})
			NonTerminal(annotations, Annotations)
			NonTerminal(name, Name)
			ZeroOrOne(
				NonTerminal(typeBounds, TypeBounds)
			)
			Action({
				return dress(STypeParameter.make(annotations, name, ensureNotNull(typeBounds)));
			})
		) */
	private int matchTypeParameter(int lookahead) {
		lookahead = matchAnnotations(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchTypeParameter_4(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("COMMA")
			NonTerminal(tp, TypeParameter)
			Action({
				ret = append(ret, tp);
			})
		) */
	private int matchTypeParameters_2_1_3_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.COMMA);
		if (lookahead == -1)
			return -1;
		lookahead = matchTypeParameter(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrMore(
			Terminal("COMMA")
			NonTerminal(tp, TypeParameter)
			Action({
				ret = append(ret, tp);
			})
		) */
	private int matchTypeParameters_2_1_3(int lookahead) {
		int newLookahead;
		newLookahead = matchTypeParameters_2_1_3_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchTypeParameters_2_1_3_1(lookahead);
		}
		return lookahead;
	}

	/* Sequence(
			NonTerminal(tp, TypeParameter)
			Action({
				ret = append(ret, tp);
			})
			ZeroOrMore(
				Terminal("COMMA")
				NonTerminal(tp, TypeParameter)
				Action({
					ret = append(ret, tp);
				})
			)
		) */
	private int matchTypeParameters_2_1(int lookahead) {
		lookahead = matchTypeParameter(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchTypeParameters_2_1_3(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(			quotesMode
	)
			NonTerminal(ret, NodeListVar)
		) */
	private int matchTypeParameters_2_2(int lookahead) {
		lookahead = quotesMode ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = matchNodeListVar(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				NonTerminal(tp, TypeParameter)
				Action({
					ret = append(ret, tp);
				})
				ZeroOrMore(
					Terminal("COMMA")
					NonTerminal(tp, TypeParameter)
					Action({
						ret = append(ret, tp);
					})
				)
			)
			Sequence(
				LookAhead(				quotesMode
	)
				NonTerminal(ret, NodeListVar)
			)
		) */
	private int matchTypeParameters_2(int lookahead) {
		int newLookahead;
		newLookahead = matchTypeParameters_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchTypeParameters_2_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			Terminal("LT")
			Choice(
				Sequence(
					NonTerminal(tp, TypeParameter)
					Action({
						ret = append(ret, tp);
					})
					ZeroOrMore(
						Terminal("COMMA")
						NonTerminal(tp, TypeParameter)
						Action({
							ret = append(ret, tp);
						})
					)
				)
				Sequence(
					LookAhead(					quotesMode
	)
					NonTerminal(ret, NodeListVar)
				)
			)
			Terminal("GT")
			Action({
				return ret;
			})
		) */
	private int matchTypeParameters(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.LT);
		if (lookahead == -1)
			return -1;
		lookahead = matchTypeParameters_2(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.GT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(typeParams, TypeParameters)
		) */
	private int matchClassOrInterfaceDecl_1_1_4_1(int lookahead) {
		lookahead = matchTypeParameters(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			NonTerminal(typeParams, TypeParameters)
		) */
	private int matchClassOrInterfaceDecl_1_1_4(int lookahead) {
		int newLookahead;
		newLookahead = matchClassOrInterfaceDecl_1_1_4_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			Terminal("EXTENDS")
			NonTerminal(superClassType, AnnotatedQualifiedType)
		) */
	private int matchClassOrInterfaceDecl_1_1_5_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.EXTENDS);
		if (lookahead == -1)
			return -1;
		lookahead = matchAnnotatedQualifiedType(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			Terminal("EXTENDS")
			NonTerminal(superClassType, AnnotatedQualifiedType)
		) */
	private int matchClassOrInterfaceDecl_1_1_5(int lookahead) {
		int newLookahead;
		newLookahead = matchClassOrInterfaceDecl_1_1_5_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			Terminal("COMMA")
			NonTerminal(cit, AnnotatedQualifiedType)
			Action({
				ret = append(ret, cit);
			})
		) */
	private int matchImplementsList_2_1_3_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.COMMA);
		if (lookahead == -1)
			return -1;
		lookahead = matchAnnotatedQualifiedType(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrMore(
			Terminal("COMMA")
			NonTerminal(cit, AnnotatedQualifiedType)
			Action({
				ret = append(ret, cit);
			})
		) */
	private int matchImplementsList_2_1_3(int lookahead) {
		int newLookahead;
		newLookahead = matchImplementsList_2_1_3_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchImplementsList_2_1_3_1(lookahead);
		}
		return lookahead;
	}

	/* Sequence(
			NonTerminal(cit, AnnotatedQualifiedType)
			Action({
				ret = append(ret, cit);
			})
			ZeroOrMore(
				Terminal("COMMA")
				NonTerminal(cit, AnnotatedQualifiedType)
				Action({
					ret = append(ret, cit);
				})
			)
			Action({
				if (typeKind == TypeKind.Interface) problem.value = new BUProblem(Severity.ERROR, "An interface cannot implement other interfaces");

			})
		) */
	private int matchImplementsList_2_1(int lookahead) {
		lookahead = matchAnnotatedQualifiedType(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchImplementsList_2_1_3(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(			quotesMode
	)
			NonTerminal(ret, NodeListVar)
		) */
	private int matchImplementsList_2_2(int lookahead) {
		lookahead = quotesMode ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = matchNodeListVar(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				NonTerminal(cit, AnnotatedQualifiedType)
				Action({
					ret = append(ret, cit);
				})
				ZeroOrMore(
					Terminal("COMMA")
					NonTerminal(cit, AnnotatedQualifiedType)
					Action({
						ret = append(ret, cit);
					})
				)
				Action({
					if (typeKind == TypeKind.Interface) problem.value = new BUProblem(Severity.ERROR, "An interface cannot implement other interfaces");

				})
			)
			Sequence(
				LookAhead(				quotesMode
	)
				NonTerminal(ret, NodeListVar)
			)
		) */
	private int matchImplementsList_2(int lookahead) {
		int newLookahead;
		newLookahead = matchImplementsList_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchImplementsList_2_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			Terminal("IMPLEMENTS")
			Choice(
				Sequence(
					NonTerminal(cit, AnnotatedQualifiedType)
					Action({
						ret = append(ret, cit);
					})
					ZeroOrMore(
						Terminal("COMMA")
						NonTerminal(cit, AnnotatedQualifiedType)
						Action({
							ret = append(ret, cit);
						})
					)
					Action({
						if (typeKind == TypeKind.Interface) problem.value = new BUProblem(Severity.ERROR, "An interface cannot implement other interfaces");

					})
				)
				Sequence(
					LookAhead(					quotesMode
	)
					NonTerminal(ret, NodeListVar)
				)
			)
			Action({
				return ret;
			})
		) */
	private int matchImplementsList(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.IMPLEMENTS);
		if (lookahead == -1)
			return -1;
		lookahead = matchImplementsList_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(implementsClause, ImplementsList)
		) */
	private int matchClassOrInterfaceDecl_1_1_6_1(int lookahead) {
		lookahead = matchImplementsList(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			NonTerminal(implementsClause, ImplementsList)
		) */
	private int matchClassOrInterfaceDecl_1_1_6(int lookahead) {
		int newLookahead;
		newLookahead = matchClassOrInterfaceDecl_1_1_6_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			Terminal("CLASS")
			Action({
				typeKind = TypeKind.Class;
			})
			NonTerminal(name, Name)
			ZeroOrOne(
				NonTerminal(typeParams, TypeParameters)
			)
			ZeroOrOne(
				Terminal("EXTENDS")
				NonTerminal(superClassType, AnnotatedQualifiedType)
			)
			ZeroOrOne(
				NonTerminal(implementsClause, ImplementsList)
			)
		) */
	private int matchClassOrInterfaceDecl_1_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.CLASS);
		if (lookahead == -1)
			return -1;
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchClassOrInterfaceDecl_1_1_4(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchClassOrInterfaceDecl_1_1_5(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchClassOrInterfaceDecl_1_1_6(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(typeParams, TypeParameters)
		) */
	private int matchClassOrInterfaceDecl_1_2_4_1(int lookahead) {
		lookahead = matchTypeParameters(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			NonTerminal(typeParams, TypeParameters)
		) */
	private int matchClassOrInterfaceDecl_1_2_4(int lookahead) {
		int newLookahead;
		newLookahead = matchClassOrInterfaceDecl_1_2_4_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			Terminal("COMMA")
			NonTerminal(cit, AnnotatedQualifiedType)
			Action({
				ret = append(ret, cit);
			})
		) */
	private int matchExtendsList_2_1_3_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.COMMA);
		if (lookahead == -1)
			return -1;
		lookahead = matchAnnotatedQualifiedType(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrMore(
			Terminal("COMMA")
			NonTerminal(cit, AnnotatedQualifiedType)
			Action({
				ret = append(ret, cit);
			})
		) */
	private int matchExtendsList_2_1_3(int lookahead) {
		int newLookahead;
		newLookahead = matchExtendsList_2_1_3_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchExtendsList_2_1_3_1(lookahead);
		}
		return lookahead;
	}

	/* Sequence(
			NonTerminal(cit, AnnotatedQualifiedType)
			Action({
				ret = append(ret, cit);
			})
			ZeroOrMore(
				Terminal("COMMA")
				NonTerminal(cit, AnnotatedQualifiedType)
				Action({
					ret = append(ret, cit);
				})
			)
		) */
	private int matchExtendsList_2_1(int lookahead) {
		lookahead = matchAnnotatedQualifiedType(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchExtendsList_2_1_3(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(			quotesMode
	)
			NonTerminal(ret, NodeListVar)
		) */
	private int matchExtendsList_2_2(int lookahead) {
		lookahead = quotesMode ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = matchNodeListVar(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				NonTerminal(cit, AnnotatedQualifiedType)
				Action({
					ret = append(ret, cit);
				})
				ZeroOrMore(
					Terminal("COMMA")
					NonTerminal(cit, AnnotatedQualifiedType)
					Action({
						ret = append(ret, cit);
					})
				)
			)
			Sequence(
				LookAhead(				quotesMode
	)
				NonTerminal(ret, NodeListVar)
			)
		) */
	private int matchExtendsList_2(int lookahead) {
		int newLookahead;
		newLookahead = matchExtendsList_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchExtendsList_2_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			Terminal("EXTENDS")
			Choice(
				Sequence(
					NonTerminal(cit, AnnotatedQualifiedType)
					Action({
						ret = append(ret, cit);
					})
					ZeroOrMore(
						Terminal("COMMA")
						NonTerminal(cit, AnnotatedQualifiedType)
						Action({
							ret = append(ret, cit);
						})
					)
				)
				Sequence(
					LookAhead(					quotesMode
	)
					NonTerminal(ret, NodeListVar)
				)
			)
			Action({
				return ret;
			})
		) */
	private int matchExtendsList(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.EXTENDS);
		if (lookahead == -1)
			return -1;
		lookahead = matchExtendsList_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(extendsClause, ExtendsList)
		) */
	private int matchClassOrInterfaceDecl_1_2_5_1(int lookahead) {
		lookahead = matchExtendsList(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			NonTerminal(extendsClause, ExtendsList)
		) */
	private int matchClassOrInterfaceDecl_1_2_5(int lookahead) {
		int newLookahead;
		newLookahead = matchClassOrInterfaceDecl_1_2_5_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			Terminal("INTERFACE")
			Action({
				typeKind = TypeKind.Interface;
			})
			NonTerminal(name, Name)
			ZeroOrOne(
				NonTerminal(typeParams, TypeParameters)
			)
			ZeroOrOne(
				NonTerminal(extendsClause, ExtendsList)
			)
		) */
	private int matchClassOrInterfaceDecl_1_2(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.INTERFACE);
		if (lookahead == -1)
			return -1;
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchClassOrInterfaceDecl_1_2_4(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchClassOrInterfaceDecl_1_2_5(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				Terminal("CLASS")
				Action({
					typeKind = TypeKind.Class;
				})
				NonTerminal(name, Name)
				ZeroOrOne(
					NonTerminal(typeParams, TypeParameters)
				)
				ZeroOrOne(
					Terminal("EXTENDS")
					NonTerminal(superClassType, AnnotatedQualifiedType)
				)
				ZeroOrOne(
					NonTerminal(implementsClause, ImplementsList)
				)
			)
			Sequence(
				Terminal("INTERFACE")
				Action({
					typeKind = TypeKind.Interface;
				})
				NonTerminal(name, Name)
				ZeroOrOne(
					NonTerminal(typeParams, TypeParameters)
				)
				ZeroOrOne(
					NonTerminal(extendsClause, ExtendsList)
				)
			)
		) */
	private int matchClassOrInterfaceDecl_1(int lookahead) {
		int newLookahead;
		newLookahead = matchClassOrInterfaceDecl_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchClassOrInterfaceDecl_1_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			Terminal("SEMICOLON")
			Action({
				ret = dress(SEmptyMemberDecl.make());
			})
		) */
	private int matchClassOrInterfaceBodyDecl_2_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.SEMICOLON);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("PUBLIC")
			Action({
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Public));
			})
		) */
	private int matchModifiers_1_1_2_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.PUBLIC);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("PROTECTED")
			Action({
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Protected));
			})
		) */
	private int matchModifiers_1_1_2_2(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.PROTECTED);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("PRIVATE")
			Action({
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Private));
			})
		) */
	private int matchModifiers_1_1_2_3(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.PRIVATE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("ABSTRACT")
			Action({
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Abstract));
			})
		) */
	private int matchModifiers_1_1_2_4(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.ABSTRACT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("_DEFAULT")
			Action({
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Default));
			})
		) */
	private int matchModifiers_1_1_2_5(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants._DEFAULT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("STATIC")
			Action({
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Static));
			})
		) */
	private int matchModifiers_1_1_2_6(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.STATIC);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("FINAL")
			Action({
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Final));
			})
		) */
	private int matchModifiers_1_1_2_7(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.FINAL);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("TRANSIENT")
			Action({
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Transient));
			})
		) */
	private int matchModifiers_1_1_2_8(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.TRANSIENT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("VOLATILE")
			Action({
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Volatile));
			})
		) */
	private int matchModifiers_1_1_2_9(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.VOLATILE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("SYNCHRONIZED")
			Action({
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Synchronized));
			})
		) */
	private int matchModifiers_1_1_2_10(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.SYNCHRONIZED);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("NATIVE")
			Action({
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Native));
			})
		) */
	private int matchModifiers_1_1_2_11(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.NATIVE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("STRICTFP")
			Action({
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.StrictFP));
			})
		) */
	private int matchModifiers_1_1_2_12(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.STRICTFP);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(ann, Annotation)
			Action({
				modifiers = append(modifiers, ann);
			})
		) */
	private int matchModifiers_1_1_2_13(int lookahead) {
		lookahead = matchAnnotation(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				Terminal("PUBLIC")
				Action({
					modifiers = append(modifiers, SModifier.make(ModifierKeyword.Public));
				})
			)
			Sequence(
				Terminal("PROTECTED")
				Action({
					modifiers = append(modifiers, SModifier.make(ModifierKeyword.Protected));
				})
			)
			Sequence(
				Terminal("PRIVATE")
				Action({
					modifiers = append(modifiers, SModifier.make(ModifierKeyword.Private));
				})
			)
			Sequence(
				Terminal("ABSTRACT")
				Action({
					modifiers = append(modifiers, SModifier.make(ModifierKeyword.Abstract));
				})
			)
			Sequence(
				Terminal("_DEFAULT")
				Action({
					modifiers = append(modifiers, SModifier.make(ModifierKeyword.Default));
				})
			)
			Sequence(
				Terminal("STATIC")
				Action({
					modifiers = append(modifiers, SModifier.make(ModifierKeyword.Static));
				})
			)
			Sequence(
				Terminal("FINAL")
				Action({
					modifiers = append(modifiers, SModifier.make(ModifierKeyword.Final));
				})
			)
			Sequence(
				Terminal("TRANSIENT")
				Action({
					modifiers = append(modifiers, SModifier.make(ModifierKeyword.Transient));
				})
			)
			Sequence(
				Terminal("VOLATILE")
				Action({
					modifiers = append(modifiers, SModifier.make(ModifierKeyword.Volatile));
				})
			)
			Sequence(
				Terminal("SYNCHRONIZED")
				Action({
					modifiers = append(modifiers, SModifier.make(ModifierKeyword.Synchronized));
				})
			)
			Sequence(
				Terminal("NATIVE")
				Action({
					modifiers = append(modifiers, SModifier.make(ModifierKeyword.Native));
				})
			)
			Sequence(
				Terminal("STRICTFP")
				Action({
					modifiers = append(modifiers, SModifier.make(ModifierKeyword.StrictFP));
				})
			)
			Sequence(
				NonTerminal(ann, Annotation)
				Action({
					modifiers = append(modifiers, ann);
				})
			)
		) */
	private int matchModifiers_1_1_2(int lookahead) {
		int newLookahead;
		newLookahead = matchModifiers_1_1_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchModifiers_1_1_2_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchModifiers_1_1_2_3(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchModifiers_1_1_2_4(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchModifiers_1_1_2_5(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchModifiers_1_1_2_6(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchModifiers_1_1_2_7(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchModifiers_1_1_2_8(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchModifiers_1_1_2_9(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchModifiers_1_1_2_10(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchModifiers_1_1_2_11(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchModifiers_1_1_2_12(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchModifiers_1_1_2_13(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			LookAhead(2)
			Choice(
				Sequence(
					Terminal("PUBLIC")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Public));
					})
				)
				Sequence(
					Terminal("PROTECTED")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Protected));
					})
				)
				Sequence(
					Terminal("PRIVATE")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Private));
					})
				)
				Sequence(
					Terminal("ABSTRACT")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Abstract));
					})
				)
				Sequence(
					Terminal("_DEFAULT")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Default));
					})
				)
				Sequence(
					Terminal("STATIC")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Static));
					})
				)
				Sequence(
					Terminal("FINAL")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Final));
					})
				)
				Sequence(
					Terminal("TRANSIENT")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Transient));
					})
				)
				Sequence(
					Terminal("VOLATILE")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Volatile));
					})
				)
				Sequence(
					Terminal("SYNCHRONIZED")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Synchronized));
					})
				)
				Sequence(
					Terminal("NATIVE")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Native));
					})
				)
				Sequence(
					Terminal("STRICTFP")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.StrictFP));
					})
				)
				Sequence(
					NonTerminal(ann, Annotation)
					Action({
						modifiers = append(modifiers, ann);
					})
				)
			)
		) */
	private int matchModifiers_1_1(int lookahead) {
		lookahead = matchModifiers_1_1_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrMore(
			LookAhead(2)
			Choice(
				Sequence(
					Terminal("PUBLIC")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Public));
					})
				)
				Sequence(
					Terminal("PROTECTED")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Protected));
					})
				)
				Sequence(
					Terminal("PRIVATE")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Private));
					})
				)
				Sequence(
					Terminal("ABSTRACT")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Abstract));
					})
				)
				Sequence(
					Terminal("_DEFAULT")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Default));
					})
				)
				Sequence(
					Terminal("STATIC")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Static));
					})
				)
				Sequence(
					Terminal("FINAL")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Final));
					})
				)
				Sequence(
					Terminal("TRANSIENT")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Transient));
					})
				)
				Sequence(
					Terminal("VOLATILE")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Volatile));
					})
				)
				Sequence(
					Terminal("SYNCHRONIZED")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Synchronized));
					})
				)
				Sequence(
					Terminal("NATIVE")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Native));
					})
				)
				Sequence(
					Terminal("STRICTFP")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.StrictFP));
					})
				)
				Sequence(
					NonTerminal(ann, Annotation)
					Action({
						modifiers = append(modifiers, ann);
					})
				)
			)
		) */
	private int matchModifiers_1(int lookahead) {
		int newLookahead;
		newLookahead = matchModifiers_1_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchModifiers_1_1(lookahead);
		}
		return lookahead;
	}

	/* Sequence(
			ZeroOrMore(
				LookAhead(2)
				Choice(
					Sequence(
						Terminal("PUBLIC")
						Action({
							modifiers = append(modifiers, SModifier.make(ModifierKeyword.Public));
						})
					)
					Sequence(
						Terminal("PROTECTED")
						Action({
							modifiers = append(modifiers, SModifier.make(ModifierKeyword.Protected));
						})
					)
					Sequence(
						Terminal("PRIVATE")
						Action({
							modifiers = append(modifiers, SModifier.make(ModifierKeyword.Private));
						})
					)
					Sequence(
						Terminal("ABSTRACT")
						Action({
							modifiers = append(modifiers, SModifier.make(ModifierKeyword.Abstract));
						})
					)
					Sequence(
						Terminal("_DEFAULT")
						Action({
							modifiers = append(modifiers, SModifier.make(ModifierKeyword.Default));
						})
					)
					Sequence(
						Terminal("STATIC")
						Action({
							modifiers = append(modifiers, SModifier.make(ModifierKeyword.Static));
						})
					)
					Sequence(
						Terminal("FINAL")
						Action({
							modifiers = append(modifiers, SModifier.make(ModifierKeyword.Final));
						})
					)
					Sequence(
						Terminal("TRANSIENT")
						Action({
							modifiers = append(modifiers, SModifier.make(ModifierKeyword.Transient));
						})
					)
					Sequence(
						Terminal("VOLATILE")
						Action({
							modifiers = append(modifiers, SModifier.make(ModifierKeyword.Volatile));
						})
					)
					Sequence(
						Terminal("SYNCHRONIZED")
						Action({
							modifiers = append(modifiers, SModifier.make(ModifierKeyword.Synchronized));
						})
					)
					Sequence(
						Terminal("NATIVE")
						Action({
							modifiers = append(modifiers, SModifier.make(ModifierKeyword.Native));
						})
					)
					Sequence(
						Terminal("STRICTFP")
						Action({
							modifiers = append(modifiers, SModifier.make(ModifierKeyword.StrictFP));
						})
					)
					Sequence(
						NonTerminal(ann, Annotation)
						Action({
							modifiers = append(modifiers, ann);
						})
					)
				)
			)
			Action({
				return modifiers;
			})
		) */
	private int matchModifiers(int lookahead) {
		lookahead = matchModifiers_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(block, Block)
			Action({
				return dress(SInitializerDecl.make(modifiers, block));
			})
		) */
	private int matchInitializerDecl(int lookahead) {
		lookahead = matchBlock(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(ret, InitializerDecl)
			Action({
				if (typeKind == TypeKind.Interface) ret = ret.withProblem(new BUProblem(Severity.ERROR, "An interface cannot have initializers"));

			})
		) */
	private int matchClassOrInterfaceBodyDecl_2_2_3_1(int lookahead) {
		lookahead = matchInitializerDecl(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(implementsClause, ImplementsList)
		) */
	private int matchEnumDecl_3_1(int lookahead) {
		lookahead = matchImplementsList(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			NonTerminal(implementsClause, ImplementsList)
		) */
	private int matchEnumDecl_3(int lookahead) {
		int newLookahead;
		newLookahead = matchEnumDecl_3_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(args, Arguments)
		) */
	private int matchEnumConstantDecl_4_1(int lookahead) {
		lookahead = matchArguments(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			NonTerminal(args, Arguments)
		) */
	private int matchEnumConstantDecl_4(int lookahead) {
		int newLookahead;
		newLookahead = matchEnumConstantDecl_4_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(classBody, ClassOrInterfaceBody)
		) */
	private int matchEnumConstantDecl_5_1(int lookahead) {
		lookahead = matchClassOrInterfaceBody(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			NonTerminal(classBody, ClassOrInterfaceBody)
		) */
	private int matchEnumConstantDecl_5(int lookahead) {
		int newLookahead;
		newLookahead = matchEnumConstantDecl_5_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			Action({
				run();
			})
			NonTerminal(modifiers, Modifiers)
			NonTerminal(name, Name)
			ZeroOrOne(
				NonTerminal(args, Arguments)
			)
			ZeroOrOne(
				NonTerminal(classBody, ClassOrInterfaceBody)
			)
			Action({
				return dress(SEnumConstantDecl.make(modifiers, name, optionOf(args), optionOf(classBody)));
			})
		) */
	private int matchEnumConstantDecl(int lookahead) {
		lookahead = matchModifiers(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchEnumConstantDecl_4(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchEnumConstantDecl_5(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(2)
			Terminal("COMMA")
			NonTerminal(entry, EnumConstantDecl)
			Action({
				constants = append(constants, entry);
			})
		) */
	private int matchEnumDecl_5_1_1_1_3_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.COMMA);
		if (lookahead == -1)
			return -1;
		lookahead = matchEnumConstantDecl(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrMore(
			LookAhead(2)
			Terminal("COMMA")
			NonTerminal(entry, EnumConstantDecl)
			Action({
				constants = append(constants, entry);
			})
		) */
	private int matchEnumDecl_5_1_1_1_3(int lookahead) {
		int newLookahead;
		newLookahead = matchEnumDecl_5_1_1_1_3_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchEnumDecl_5_1_1_1_3_1(lookahead);
		}
		return lookahead;
	}

	/* Sequence(
			NonTerminal(entry, EnumConstantDecl)
			Action({
				constants = append(constants, entry);
			})
			ZeroOrMore(
				LookAhead(2)
				Terminal("COMMA")
				NonTerminal(entry, EnumConstantDecl)
				Action({
					constants = append(constants, entry);
				})
			)
		) */
	private int matchEnumDecl_5_1_1_1(int lookahead) {
		lookahead = matchEnumConstantDecl(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchEnumDecl_5_1_1_1_3(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(			quotesMode
	)
			NonTerminal(constants, NodeListVar)
		) */
	private int matchEnumDecl_5_1_1_2(int lookahead) {
		lookahead = quotesMode ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = matchNodeListVar(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				NonTerminal(entry, EnumConstantDecl)
				Action({
					constants = append(constants, entry);
				})
				ZeroOrMore(
					LookAhead(2)
					Terminal("COMMA")
					NonTerminal(entry, EnumConstantDecl)
					Action({
						constants = append(constants, entry);
					})
				)
			)
			Sequence(
				LookAhead(				quotesMode
	)
				NonTerminal(constants, NodeListVar)
			)
		) */
	private int matchEnumDecl_5_1_1(int lookahead) {
		int newLookahead;
		newLookahead = matchEnumDecl_5_1_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchEnumDecl_5_1_1_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			Choice(
				Sequence(
					NonTerminal(entry, EnumConstantDecl)
					Action({
						constants = append(constants, entry);
					})
					ZeroOrMore(
						LookAhead(2)
						Terminal("COMMA")
						NonTerminal(entry, EnumConstantDecl)
						Action({
							constants = append(constants, entry);
						})
					)
				)
				Sequence(
					LookAhead(					quotesMode
	)
					NonTerminal(constants, NodeListVar)
				)
			)
		) */
	private int matchEnumDecl_5_1(int lookahead) {
		lookahead = matchEnumDecl_5_1_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			Choice(
				Sequence(
					NonTerminal(entry, EnumConstantDecl)
					Action({
						constants = append(constants, entry);
					})
					ZeroOrMore(
						LookAhead(2)
						Terminal("COMMA")
						NonTerminal(entry, EnumConstantDecl)
						Action({
							constants = append(constants, entry);
						})
					)
				)
				Sequence(
					LookAhead(					quotesMode
	)
					NonTerminal(constants, NodeListVar)
				)
			)
		) */
	private int matchEnumDecl_5(int lookahead) {
		int newLookahead;
		newLookahead = matchEnumDecl_5_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			Terminal("COMMA")
			Action({
				trailingComma = true;
			})
		) */
	private int matchEnumDecl_6_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.COMMA);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			Terminal("COMMA")
			Action({
				trailingComma = true;
			})
		) */
	private int matchEnumDecl_6(int lookahead) {
		int newLookahead;
		newLookahead = matchEnumDecl_6_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			Terminal("SEMICOLON")
			NonTerminal(members, ClassOrInterfaceBodyDecls)
		) */
	private int matchEnumDecl_7_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.SEMICOLON);
		if (lookahead == -1)
			return -1;
		lookahead = matchClassOrInterfaceBodyDecls(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			Terminal("SEMICOLON")
			NonTerminal(members, ClassOrInterfaceBodyDecls)
		) */
	private int matchEnumDecl_7(int lookahead) {
		int newLookahead;
		newLookahead = matchEnumDecl_7_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			Terminal("ENUM")
			NonTerminal(name, Name)
			ZeroOrOne(
				NonTerminal(implementsClause, ImplementsList)
			)
			Terminal("LBRACE")
			ZeroOrOne(
				Choice(
					Sequence(
						NonTerminal(entry, EnumConstantDecl)
						Action({
							constants = append(constants, entry);
						})
						ZeroOrMore(
							LookAhead(2)
							Terminal("COMMA")
							NonTerminal(entry, EnumConstantDecl)
							Action({
								constants = append(constants, entry);
							})
						)
					)
					Sequence(
						LookAhead(						quotesMode
	)
						NonTerminal(constants, NodeListVar)
					)
				)
			)
			ZeroOrOne(
				Terminal("COMMA")
				Action({
					trailingComma = true;
				})
			)
			ZeroOrOne(
				Terminal("SEMICOLON")
				NonTerminal(members, ClassOrInterfaceBodyDecls)
			)
			Terminal("RBRACE")
			Action({
				return dress(SEnumDecl.make(modifiers, name, implementsClause, constants, trailingComma, ensureNotNull(members))).withProblem(problem.value);
			})
		) */
	private int matchEnumDecl(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.ENUM);
		if (lookahead == -1)
			return -1;
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchEnumDecl_3(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.LBRACE);
		if (lookahead == -1)
			return -1;
		lookahead = matchEnumDecl_5(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchEnumDecl_6(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchEnumDecl_7(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RBRACE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("SEMICOLON")
			Action({
				ret = dress(SEmptyTypeDecl.make());
			})
		) */
	private int matchAnnotationTypeBodyDecl_2_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.SEMICOLON);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(
				NonTerminal(Annotations)
				Terminal("LBRACKET")
			)
			Action({
				lateRun();
			})
			NonTerminal(arrayDims, ArrayDimsMandatory)
			Action({
				type = dress(SArrayType.make(primitiveType, arrayDims));
			})
		) */
	private int matchType_1_1_2_1(int lookahead) {
		lookahead = matchArrayDimsMandatory(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			LookAhead(
				NonTerminal(Annotations)
				Terminal("LBRACKET")
			)
			Action({
				lateRun();
			})
			NonTerminal(arrayDims, ArrayDimsMandatory)
			Action({
				type = dress(SArrayType.make(primitiveType, arrayDims));
			})
		) */
	private int matchType_1_1_2(int lookahead) {
		int newLookahead;
		newLookahead = matchType_1_1_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(primitiveType, PrimitiveType)
			ZeroOrOne(
				LookAhead(
					NonTerminal(Annotations)
					Terminal("LBRACKET")
				)
				Action({
					lateRun();
				})
				NonTerminal(arrayDims, ArrayDimsMandatory)
				Action({
					type = dress(SArrayType.make(primitiveType, arrayDims));
				})
			)
		) */
	private int matchType_1_1(int lookahead) {
		lookahead = matchPrimitiveType(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchType_1_1_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(
				NonTerminal(Annotations)
				Terminal("LBRACKET")
			)
			Action({
				lateRun();
			})
			NonTerminal(arrayDims, ArrayDimsMandatory)
			Action({
				type = dress(SArrayType.make(type, arrayDims));
			})
		) */
	private int matchType_1_2_2_1(int lookahead) {
		lookahead = matchArrayDimsMandatory(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			LookAhead(
				NonTerminal(Annotations)
				Terminal("LBRACKET")
			)
			Action({
				lateRun();
			})
			NonTerminal(arrayDims, ArrayDimsMandatory)
			Action({
				type = dress(SArrayType.make(type, arrayDims));
			})
		) */
	private int matchType_1_2_2(int lookahead) {
		int newLookahead;
		newLookahead = matchType_1_2_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(type, QualifiedType)
			ZeroOrOne(
				LookAhead(
					NonTerminal(Annotations)
					Terminal("LBRACKET")
				)
				Action({
					lateRun();
				})
				NonTerminal(arrayDims, ArrayDimsMandatory)
				Action({
					type = dress(SArrayType.make(type, arrayDims));
				})
			)
		) */
	private int matchType_1_2(int lookahead) {
		lookahead = matchQualifiedType(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchType_1_2_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				NonTerminal(primitiveType, PrimitiveType)
				ZeroOrOne(
					LookAhead(
						NonTerminal(Annotations)
						Terminal("LBRACKET")
					)
					Action({
						lateRun();
					})
					NonTerminal(arrayDims, ArrayDimsMandatory)
					Action({
						type = dress(SArrayType.make(primitiveType, arrayDims));
					})
				)
			)
			Sequence(
				NonTerminal(type, QualifiedType)
				ZeroOrOne(
					LookAhead(
						NonTerminal(Annotations)
						Terminal("LBRACKET")
					)
					Action({
						lateRun();
					})
					NonTerminal(arrayDims, ArrayDimsMandatory)
					Action({
						type = dress(SArrayType.make(type, arrayDims));
					})
				)
			)
		) */
	private int matchType_1(int lookahead) {
		int newLookahead;
		newLookahead = matchType_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchType_1_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			Choice(
				Sequence(
					NonTerminal(primitiveType, PrimitiveType)
					ZeroOrOne(
						LookAhead(
							NonTerminal(Annotations)
							Terminal("LBRACKET")
						)
						Action({
							lateRun();
						})
						NonTerminal(arrayDims, ArrayDimsMandatory)
						Action({
							type = dress(SArrayType.make(primitiveType, arrayDims));
						})
					)
				)
				Sequence(
					NonTerminal(type, QualifiedType)
					ZeroOrOne(
						LookAhead(
							NonTerminal(Annotations)
							Terminal("LBRACKET")
						)
						Action({
							lateRun();
						})
						NonTerminal(arrayDims, ArrayDimsMandatory)
						Action({
							type = dress(SArrayType.make(type, arrayDims));
						})
					)
				)
			)
			Action({
				return type == null ? primitiveType : type;
			})
		) */
	private int matchType(int lookahead) {
		lookahead = matchType_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(
				NonTerminal(Annotations)
				Terminal("LBRACKET")
				Terminal("RBRACKET")
			)
			Action({
				run();
			})
			NonTerminal(annotations, Annotations)
			Terminal("LBRACKET")
			Terminal("RBRACKET")
			Action({
				arrayDims = append(arrayDims, dress(SArrayDim.make(annotations)));
			})
		) */
	private int matchArrayDims_1_1(int lookahead) {
		lookahead = matchAnnotations(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.LBRACKET);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RBRACKET);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrMore(
			LookAhead(
				NonTerminal(Annotations)
				Terminal("LBRACKET")
				Terminal("RBRACKET")
			)
			Action({
				run();
			})
			NonTerminal(annotations, Annotations)
			Terminal("LBRACKET")
			Terminal("RBRACKET")
			Action({
				arrayDims = append(arrayDims, dress(SArrayDim.make(annotations)));
			})
		) */
	private int matchArrayDims_1(int lookahead) {
		int newLookahead;
		newLookahead = matchArrayDims_1_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchArrayDims_1_1(lookahead);
		}
		return lookahead;
	}

	/* Sequence(
			ZeroOrMore(
				LookAhead(
					NonTerminal(Annotations)
					Terminal("LBRACKET")
					Terminal("RBRACKET")
				)
				Action({
					run();
				})
				NonTerminal(annotations, Annotations)
				Terminal("LBRACKET")
				Terminal("RBRACKET")
				Action({
					arrayDims = append(arrayDims, dress(SArrayDim.make(annotations)));
				})
			)
			Action({
				return arrayDims;
			})
		) */
	private int matchArrayDims(int lookahead) {
		lookahead = matchArrayDims_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("_DEFAULT")
			NonTerminal(val, MemberValue)
			Action({
				defaultVal = optionOf(val);
			})
		) */
	private int matchAnnotationTypeMemberDecl_6_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants._DEFAULT);
		if (lookahead == -1)
			return -1;
		lookahead = matchMemberValue(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			Terminal("_DEFAULT")
			NonTerminal(val, MemberValue)
			Action({
				defaultVal = optionOf(val);
			})
		) */
	private int matchAnnotationTypeMemberDecl_6(int lookahead) {
		int newLookahead;
		newLookahead = matchAnnotationTypeMemberDecl_6_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(type, Type)
			NonTerminal(name, Name)
			Terminal("LPAREN")
			Terminal("RPAREN")
			NonTerminal(dims, ArrayDims)
			ZeroOrOne(
				Terminal("_DEFAULT")
				NonTerminal(val, MemberValue)
				Action({
					defaultVal = optionOf(val);
				})
			)
			Terminal("SEMICOLON")
			Action({
				return dress(SAnnotationMemberDecl.make(modifiers, type, name, dims, defaultVal));
			})
		) */
	private int matchAnnotationTypeMemberDecl(int lookahead) {
		lookahead = matchType(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchArrayDims(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchAnnotationTypeMemberDecl_6(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.SEMICOLON);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(
				NonTerminal(Type)
				NonTerminal(Name)
				Terminal("LPAREN")
			)
			NonTerminal(ret, AnnotationTypeMemberDecl)
		) */
	private int matchAnnotationTypeBodyDecl_2_2_2_1(int lookahead) {
		lookahead = matchAnnotationTypeMemberDecl(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Action({
				run();
			})
			NonTerminal(name, Name)
			NonTerminal(arrayDims, ArrayDims)
			Action({
				return dress(SVariableDeclaratorId.make(name, arrayDims));
			})
		) */
	private int matchVariableDeclaratorId(int lookahead) {
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchArrayDims(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(2)
			Terminal("COMMA")
			NonTerminal(val, VariableInitializer)
			Action({
				values = append(values, val);
			})
		) */
	private int matchArrayInitializer_3_1_3_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.COMMA);
		if (lookahead == -1)
			return -1;
		lookahead = matchVariableInitializer(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrMore(
			LookAhead(2)
			Terminal("COMMA")
			NonTerminal(val, VariableInitializer)
			Action({
				values = append(values, val);
			})
		) */
	private int matchArrayInitializer_3_1_3(int lookahead) {
		int newLookahead;
		newLookahead = matchArrayInitializer_3_1_3_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchArrayInitializer_3_1_3_1(lookahead);
		}
		return lookahead;
	}

	/* Sequence(
			NonTerminal(val, VariableInitializer)
			Action({
				values = append(values, val);
			})
			ZeroOrMore(
				LookAhead(2)
				Terminal("COMMA")
				NonTerminal(val, VariableInitializer)
				Action({
					values = append(values, val);
				})
			)
		) */
	private int matchArrayInitializer_3_1(int lookahead) {
		lookahead = matchVariableInitializer(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchArrayInitializer_3_1_3(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			NonTerminal(val, VariableInitializer)
			Action({
				values = append(values, val);
			})
			ZeroOrMore(
				LookAhead(2)
				Terminal("COMMA")
				NonTerminal(val, VariableInitializer)
				Action({
					values = append(values, val);
				})
			)
		) */
	private int matchArrayInitializer_3(int lookahead) {
		int newLookahead;
		newLookahead = matchArrayInitializer_3_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			Terminal("COMMA")
			Action({
				trailingComma = true;
			})
		) */
	private int matchArrayInitializer_4_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.COMMA);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			Terminal("COMMA")
			Action({
				trailingComma = true;
			})
		) */
	private int matchArrayInitializer_4(int lookahead) {
		int newLookahead;
		newLookahead = matchArrayInitializer_4_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			Action({
				run();
			})
			Terminal("LBRACE")
			ZeroOrOne(
				NonTerminal(val, VariableInitializer)
				Action({
					values = append(values, val);
				})
				ZeroOrMore(
					LookAhead(2)
					Terminal("COMMA")
					NonTerminal(val, VariableInitializer)
					Action({
						values = append(values, val);
					})
				)
			)
			ZeroOrOne(
				Terminal("COMMA")
				Action({
					trailingComma = true;
				})
			)
			Terminal("RBRACE")
			Action({
				return dress(SArrayInitializerExpr.make(values, trailingComma));
			})
		) */
	private int matchArrayInitializer(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.LBRACE);
		if (lookahead == -1)
			return -1;
		lookahead = matchArrayInitializer_3(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchArrayInitializer_4(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RBRACE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			NonTerminal(ret, ArrayInitializer)
			NonTerminal(ret, Expression)
		) */
	private int matchVariableInitializer_1(int lookahead) {
		int newLookahead;
		newLookahead = matchArrayInitializer(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchExpression(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			Choice(
				NonTerminal(ret, ArrayInitializer)
				NonTerminal(ret, Expression)
			)
			Action({
				return ret;
			})
		) */
	private int matchVariableInitializer(int lookahead) {
		lookahead = matchVariableInitializer_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("ASSIGN")
			NonTerminal(initExpr, VariableInitializer)
			Action({
				init = optionOf(initExpr);
			})
		) */
	private int matchVariableDeclarator_3_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.ASSIGN);
		if (lookahead == -1)
			return -1;
		lookahead = matchVariableInitializer(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			Terminal("ASSIGN")
			NonTerminal(initExpr, VariableInitializer)
			Action({
				init = optionOf(initExpr);
			})
		) */
	private int matchVariableDeclarator_3(int lookahead) {
		int newLookahead;
		newLookahead = matchVariableDeclarator_3_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			Action({
				run();
			})
			NonTerminal(id, VariableDeclaratorId)
			ZeroOrOne(
				Terminal("ASSIGN")
				NonTerminal(initExpr, VariableInitializer)
				Action({
					init = optionOf(initExpr);
				})
			)
			Action({
				return dress(SVariableDeclarator.make(id, init));
			})
		) */
	private int matchVariableDeclarator(int lookahead) {
		lookahead = matchVariableDeclaratorId(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchVariableDeclarator_3(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("COMMA")
			NonTerminal(val, VariableDeclarator)
			Action({
				variables = append(variables, val);
			})
		) */
	private int matchVariableDeclarators_3_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.COMMA);
		if (lookahead == -1)
			return -1;
		lookahead = matchVariableDeclarator(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrMore(
			Terminal("COMMA")
			NonTerminal(val, VariableDeclarator)
			Action({
				variables = append(variables, val);
			})
		) */
	private int matchVariableDeclarators_3(int lookahead) {
		int newLookahead;
		newLookahead = matchVariableDeclarators_3_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchVariableDeclarators_3_1(lookahead);
		}
		return lookahead;
	}

	/* Sequence(
			NonTerminal(val, VariableDeclarator)
			Action({
				variables = append(variables, val);
			})
			ZeroOrMore(
				Terminal("COMMA")
				NonTerminal(val, VariableDeclarator)
				Action({
					variables = append(variables, val);
				})
			)
			Action({
				return variables;
			})
		) */
	private int matchVariableDeclarators(int lookahead) {
		lookahead = matchVariableDeclarator(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchVariableDeclarators_3(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(type, Type)
			NonTerminal(variables, VariableDeclarators)
			Terminal("SEMICOLON")
			Action({
				return dress(SFieldDecl.make(modifiers, type, variables));
			})
		) */
	private int matchFieldDecl(int lookahead) {
		lookahead = matchType(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchVariableDeclarators(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.SEMICOLON);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				LookAhead(
					NonTerminal(Type)
					NonTerminal(Name)
					Terminal("LPAREN")
				)
				NonTerminal(ret, AnnotationTypeMemberDecl)
			)
			NonTerminal(ret, ClassOrInterfaceDecl)
			NonTerminal(ret, EnumDecl)
			NonTerminal(ret, AnnotationTypeDecl)
			NonTerminal(ret, FieldDecl)
		) */
	private int matchAnnotationTypeBodyDecl_2_2_2(int lookahead) {
		int newLookahead;
		newLookahead = matchAnnotationTypeBodyDecl_2_2_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchClassOrInterfaceDecl(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchEnumDecl(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchAnnotationTypeDecl(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchFieldDecl(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			NonTerminal(modifiers, Modifiers)
			Choice(
				Sequence(
					LookAhead(
						NonTerminal(Type)
						NonTerminal(Name)
						Terminal("LPAREN")
					)
					NonTerminal(ret, AnnotationTypeMemberDecl)
				)
				NonTerminal(ret, ClassOrInterfaceDecl)
				NonTerminal(ret, EnumDecl)
				NonTerminal(ret, AnnotationTypeDecl)
				NonTerminal(ret, FieldDecl)
			)
		) */
	private int matchAnnotationTypeBodyDecl_2_2(int lookahead) {
		lookahead = matchModifiers(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchAnnotationTypeBodyDecl_2_2_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				Terminal("SEMICOLON")
				Action({
					ret = dress(SEmptyTypeDecl.make());
				})
			)
			Sequence(
				NonTerminal(modifiers, Modifiers)
				Choice(
					Sequence(
						LookAhead(
							NonTerminal(Type)
							NonTerminal(Name)
							Terminal("LPAREN")
						)
						NonTerminal(ret, AnnotationTypeMemberDecl)
					)
					NonTerminal(ret, ClassOrInterfaceDecl)
					NonTerminal(ret, EnumDecl)
					NonTerminal(ret, AnnotationTypeDecl)
					NonTerminal(ret, FieldDecl)
				)
			)
		) */
	private int matchAnnotationTypeBodyDecl_2(int lookahead) {
		int newLookahead;
		newLookahead = matchAnnotationTypeBodyDecl_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchAnnotationTypeBodyDecl_2_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			Action({
				run();
			})
			Choice(
				Sequence(
					Terminal("SEMICOLON")
					Action({
						ret = dress(SEmptyTypeDecl.make());
					})
				)
				Sequence(
					NonTerminal(modifiers, Modifiers)
					Choice(
						Sequence(
							LookAhead(
								NonTerminal(Type)
								NonTerminal(Name)
								Terminal("LPAREN")
							)
							NonTerminal(ret, AnnotationTypeMemberDecl)
						)
						NonTerminal(ret, ClassOrInterfaceDecl)
						NonTerminal(ret, EnumDecl)
						NonTerminal(ret, AnnotationTypeDecl)
						NonTerminal(ret, FieldDecl)
					)
				)
			)
			Action({
				return ret;
			})
		) */
	private int matchAnnotationTypeBodyDecl(int lookahead) {
		lookahead = matchAnnotationTypeBodyDecl_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(member, AnnotationTypeBodyDecl)
			Action({
				ret = append(ret, member);
			})
		) */
	private int matchAnnotationTypeBody_2_1_1_1_1(int lookahead) {
		lookahead = matchAnnotationTypeBodyDecl(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* OneOrMore(
			NonTerminal(member, AnnotationTypeBodyDecl)
			Action({
				ret = append(ret, member);
			})
		) */
	private int matchAnnotationTypeBody_2_1_1_1(int lookahead) {
		int newLookahead;
		newLookahead = matchAnnotationTypeBody_2_1_1_1_1(lookahead);
		if (newLookahead == -1)
			return -1;
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchAnnotationTypeBody_2_1_1_1_1(lookahead);
		}
		return lookahead;
	}

	/* Sequence(
			LookAhead(			quotesMode
	)
			NonTerminal(ret, NodeListVar)
		) */
	private int matchAnnotationTypeBody_2_1_1_2(int lookahead) {
		lookahead = quotesMode ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = matchNodeListVar(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			OneOrMore(
				NonTerminal(member, AnnotationTypeBodyDecl)
				Action({
					ret = append(ret, member);
				})
			)
			Sequence(
				LookAhead(				quotesMode
	)
				NonTerminal(ret, NodeListVar)
			)
		) */
	private int matchAnnotationTypeBody_2_1_1(int lookahead) {
		int newLookahead;
		newLookahead = matchAnnotationTypeBody_2_1_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchAnnotationTypeBody_2_1_1_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			Choice(
				OneOrMore(
					NonTerminal(member, AnnotationTypeBodyDecl)
					Action({
						ret = append(ret, member);
					})
				)
				Sequence(
					LookAhead(					quotesMode
	)
					NonTerminal(ret, NodeListVar)
				)
			)
		) */
	private int matchAnnotationTypeBody_2_1(int lookahead) {
		lookahead = matchAnnotationTypeBody_2_1_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			Choice(
				OneOrMore(
					NonTerminal(member, AnnotationTypeBodyDecl)
					Action({
						ret = append(ret, member);
					})
				)
				Sequence(
					LookAhead(					quotesMode
	)
					NonTerminal(ret, NodeListVar)
				)
			)
		) */
	private int matchAnnotationTypeBody_2(int lookahead) {
		int newLookahead;
		newLookahead = matchAnnotationTypeBody_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			Terminal("LBRACE")
			ZeroOrOne(
				Choice(
					OneOrMore(
						NonTerminal(member, AnnotationTypeBodyDecl)
						Action({
							ret = append(ret, member);
						})
					)
					Sequence(
						LookAhead(						quotesMode
	)
						NonTerminal(ret, NodeListVar)
					)
				)
			)
			Terminal("RBRACE")
			Action({
				return ret;
			})
		) */
	private int matchAnnotationTypeBody(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.LBRACE);
		if (lookahead == -1)
			return -1;
		lookahead = matchAnnotationTypeBody_2(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RBRACE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("AT")
			Terminal("INTERFACE")
			NonTerminal(name, Name)
			NonTerminal(members, AnnotationTypeBody)
			Action({
				return dress(SAnnotationDecl.make(modifiers, name, members));
			})
		) */
	private int matchAnnotationTypeDecl(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.AT);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.INTERFACE);
		if (lookahead == -1)
			return -1;
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchAnnotationTypeBody(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(typeParameters, TypeParameters)
		) */
	private int matchConstructorDecl_1_1(int lookahead) {
		lookahead = matchTypeParameters(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			NonTerminal(typeParameters, TypeParameters)
		) */
	private int matchConstructorDecl_1(int lookahead) {
		int newLookahead;
		newLookahead = matchConstructorDecl_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			Terminal("ELLIPSIS")
			Action({
				isVarArg = true;
			})
		) */
	private int matchFormalParameter_4_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.ELLIPSIS);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			Terminal("ELLIPSIS")
			Action({
				isVarArg = true;
			})
		) */
	private int matchFormalParameter_4(int lookahead) {
		int newLookahead;
		newLookahead = matchFormalParameter_4_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			Action({
				run();
			})
			NonTerminal(modifiers, Modifiers)
			NonTerminal(type, Type)
			ZeroOrOne(
				Terminal("ELLIPSIS")
				Action({
					isVarArg = true;
				})
			)
			NonTerminal(id, VariableDeclaratorId)
			Action({
				return dress(SFormalParameter.make(modifiers, type, isVarArg, id));
			})
		) */
	private int matchFormalParameter(int lookahead) {
		lookahead = matchModifiers(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchType(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchFormalParameter_4(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchVariableDeclaratorId(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("COMMA")
			NonTerminal(par, FormalParameter)
			Action({
				ret = append(ret, par);
			})
		) */
	private int matchFormalParameterList_1_1_3_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.COMMA);
		if (lookahead == -1)
			return -1;
		lookahead = matchFormalParameter(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrMore(
			Terminal("COMMA")
			NonTerminal(par, FormalParameter)
			Action({
				ret = append(ret, par);
			})
		) */
	private int matchFormalParameterList_1_1_3(int lookahead) {
		int newLookahead;
		newLookahead = matchFormalParameterList_1_1_3_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchFormalParameterList_1_1_3_1(lookahead);
		}
		return lookahead;
	}

	/* Sequence(
			NonTerminal(par, FormalParameter)
			Action({
				ret = append(ret, par);
			})
			ZeroOrMore(
				Terminal("COMMA")
				NonTerminal(par, FormalParameter)
				Action({
					ret = append(ret, par);
				})
			)
		) */
	private int matchFormalParameterList_1_1(int lookahead) {
		lookahead = matchFormalParameter(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchFormalParameterList_1_1_3(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(			quotesMode
	)
			NonTerminal(ret, NodeListVar)
		) */
	private int matchFormalParameterList_1_2(int lookahead) {
		lookahead = quotesMode ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = matchNodeListVar(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				NonTerminal(par, FormalParameter)
				Action({
					ret = append(ret, par);
				})
				ZeroOrMore(
					Terminal("COMMA")
					NonTerminal(par, FormalParameter)
					Action({
						ret = append(ret, par);
					})
				)
			)
			Sequence(
				LookAhead(				quotesMode
	)
				NonTerminal(ret, NodeListVar)
			)
		) */
	private int matchFormalParameterList_1(int lookahead) {
		int newLookahead;
		newLookahead = matchFormalParameterList_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchFormalParameterList_1_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			Choice(
				Sequence(
					NonTerminal(par, FormalParameter)
					Action({
						ret = append(ret, par);
					})
					ZeroOrMore(
						Terminal("COMMA")
						NonTerminal(par, FormalParameter)
						Action({
							ret = append(ret, par);
						})
					)
				)
				Sequence(
					LookAhead(					quotesMode
	)
					NonTerminal(ret, NodeListVar)
				)
			)
			Action({
				return ret;
			})
		) */
	private int matchFormalParameterList(int lookahead) {
		lookahead = matchFormalParameterList_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(ret, FormalParameterList)
		) */
	private int matchFormalParameters_2_1(int lookahead) {
		lookahead = matchFormalParameterList(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			NonTerminal(ret, FormalParameterList)
		) */
	private int matchFormalParameters_2(int lookahead) {
		int newLookahead;
		newLookahead = matchFormalParameters_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			Terminal("LPAREN")
			ZeroOrOne(
				NonTerminal(ret, FormalParameterList)
			)
			Terminal("RPAREN")
			Action({
				return ensureNotNull(ret);
			})
		) */
	private int matchFormalParameters(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchFormalParameters_2(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("COMMA")
			NonTerminal(cit, AnnotatedQualifiedType)
			Action({
				ret = append(ret, cit);
			})
		) */
	private int matchThrowsClause_4_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.COMMA);
		if (lookahead == -1)
			return -1;
		lookahead = matchAnnotatedQualifiedType(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrMore(
			Terminal("COMMA")
			NonTerminal(cit, AnnotatedQualifiedType)
			Action({
				ret = append(ret, cit);
			})
		) */
	private int matchThrowsClause_4(int lookahead) {
		int newLookahead;
		newLookahead = matchThrowsClause_4_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchThrowsClause_4_1(lookahead);
		}
		return lookahead;
	}

	/* Sequence(
			Terminal("THROWS")
			NonTerminal(cit, AnnotatedQualifiedType)
			Action({
				ret = append(ret, cit);
			})
			ZeroOrMore(
				Terminal("COMMA")
				NonTerminal(cit, AnnotatedQualifiedType)
				Action({
					ret = append(ret, cit);
				})
			)
			Action({
				return ret;
			})
		) */
	private int matchThrowsClause(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.THROWS);
		if (lookahead == -1)
			return -1;
		lookahead = matchAnnotatedQualifiedType(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchThrowsClause_4(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(throwsClause, ThrowsClause)
		) */
	private int matchConstructorDecl_4_1(int lookahead) {
		lookahead = matchThrowsClause(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			NonTerminal(throwsClause, ThrowsClause)
		) */
	private int matchConstructorDecl_4(int lookahead) {
		int newLookahead;
		newLookahead = matchConstructorDecl_4_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(typeArgs, TypeArguments)
		) */
	private int matchExplicitConstructorInvocation_2_1_2_1(int lookahead) {
		lookahead = matchTypeArguments(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			NonTerminal(typeArgs, TypeArguments)
		) */
	private int matchExplicitConstructorInvocation_2_1_2(int lookahead) {
		int newLookahead;
		newLookahead = matchExplicitConstructorInvocation_2_1_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			LookAhead(
				ZeroOrOne(
					NonTerminal(TypeArguments)
				)
				Terminal("THIS")
				Terminal("LPAREN")
			)
			ZeroOrOne(
				NonTerminal(typeArgs, TypeArguments)
			)
			Terminal("THIS")
			Action({
				isThis = true;
			})
			NonTerminal(args, Arguments)
			Terminal("SEMICOLON")
		) */
	private int matchExplicitConstructorInvocation_2_1(int lookahead) {
		lookahead = matchExplicitConstructorInvocation_2_1_2(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.THIS);
		if (lookahead == -1)
			return -1;
		lookahead = matchArguments(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.SEMICOLON);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("THIS")
			Action({
				ret = dress(SThisExpr.make(optionOf(scope)));
			})
		) */
	private int matchPrimarySuffixWithoutSuper_1_1_2_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.THIS);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(typeArgs, TypeArguments)
		) */
	private int matchAllocationExpression_3_1(int lookahead) {
		lookahead = matchTypeArguments(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			NonTerminal(typeArgs, TypeArguments)
		) */
	private int matchAllocationExpression_3(int lookahead) {
		int newLookahead;
		newLookahead = matchAllocationExpression_3_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			LookAhead(
				NonTerminal(Annotations)
				Terminal("LBRACKET")
				NonTerminal(Expression)
				Terminal("RBRACKET")
			)
			Action({
				run();
			})
			NonTerminal(annotations, Annotations)
			Terminal("LBRACKET")
			NonTerminal(expr, Expression)
			Terminal("RBRACKET")
			Action({
				arrayDimExprs = append(arrayDimExprs, dress(SArrayDimExpr.make(annotations, expr)));
			})
		) */
	private int matchArrayDimExprsMandatory_1_1(int lookahead) {
		lookahead = matchAnnotations(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.LBRACKET);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RBRACKET);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* OneOrMore(
			LookAhead(
				NonTerminal(Annotations)
				Terminal("LBRACKET")
				NonTerminal(Expression)
				Terminal("RBRACKET")
			)
			Action({
				run();
			})
			NonTerminal(annotations, Annotations)
			Terminal("LBRACKET")
			NonTerminal(expr, Expression)
			Terminal("RBRACKET")
			Action({
				arrayDimExprs = append(arrayDimExprs, dress(SArrayDimExpr.make(annotations, expr)));
			})
		) */
	private int matchArrayDimExprsMandatory_1(int lookahead) {
		int newLookahead;
		newLookahead = matchArrayDimExprsMandatory_1_1(lookahead);
		if (newLookahead == -1)
			return -1;
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchArrayDimExprsMandatory_1_1(lookahead);
		}
		return lookahead;
	}

	/* Sequence(
			OneOrMore(
				LookAhead(
					NonTerminal(Annotations)
					Terminal("LBRACKET")
					NonTerminal(Expression)
					Terminal("RBRACKET")
				)
				Action({
					run();
				})
				NonTerminal(annotations, Annotations)
				Terminal("LBRACKET")
				NonTerminal(expr, Expression)
				Terminal("RBRACKET")
				Action({
					arrayDimExprs = append(arrayDimExprs, dress(SArrayDimExpr.make(annotations, expr)));
				})
			)
			Action({
				return arrayDimExprs;
			})
		) */
	private int matchArrayDimExprsMandatory(int lookahead) {
		lookahead = matchArrayDimExprsMandatory_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(
				NonTerminal(Annotations)
				Terminal("LBRACKET")
				NonTerminal(Expression)
				Terminal("RBRACKET")
			)
			NonTerminal(arrayDimExprs, ArrayDimExprsMandatory)
			NonTerminal(arrayDims, ArrayDims)
			Action({
				return dress(SArrayCreationExpr.make(componentType, arrayDimExprs, arrayDims, none()));
			})
		) */
	private int matchArrayCreationExpr_1(int lookahead) {
		lookahead = matchArrayDimExprsMandatory(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchArrayDims(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(arrayDims, ArrayDimsMandatory)
			NonTerminal(initializer, ArrayInitializer)
			Action({
				return dress(SArrayCreationExpr.make(componentType, arrayDimExprs, arrayDims, optionOf(initializer)));
			})
		) */
	private int matchArrayCreationExpr_2(int lookahead) {
		lookahead = matchArrayDimsMandatory(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchArrayInitializer(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				LookAhead(
					NonTerminal(Annotations)
					Terminal("LBRACKET")
					NonTerminal(Expression)
					Terminal("RBRACKET")
				)
				NonTerminal(arrayDimExprs, ArrayDimExprsMandatory)
				NonTerminal(arrayDims, ArrayDims)
				Action({
					return dress(SArrayCreationExpr.make(componentType, arrayDimExprs, arrayDims, none()));
				})
			)
			Sequence(
				NonTerminal(arrayDims, ArrayDimsMandatory)
				NonTerminal(initializer, ArrayInitializer)
				Action({
					return dress(SArrayCreationExpr.make(componentType, arrayDimExprs, arrayDims, optionOf(initializer)));
				})
			)
		) */
	private int matchArrayCreationExpr(int lookahead) {
		int newLookahead;
		newLookahead = matchArrayCreationExpr_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchArrayCreationExpr_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			NonTerminal(type, PrimitiveType)
			NonTerminal(ret, ArrayCreationExpr)
		) */
	private int matchAllocationExpression_6_1(int lookahead) {
		lookahead = matchPrimitiveType(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchArrayCreationExpr(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(
				Terminal("LBRACE")
			)
			NonTerminal(anonymousBody, ClassOrInterfaceBody)
		) */
	private int matchAllocationExpression_6_2_2_2_2_1(int lookahead) {
		lookahead = matchClassOrInterfaceBody(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			LookAhead(
				Terminal("LBRACE")
			)
			NonTerminal(anonymousBody, ClassOrInterfaceBody)
		) */
	private int matchAllocationExpression_6_2_2_2_2(int lookahead) {
		int newLookahead;
		newLookahead = matchAllocationExpression_6_2_2_2_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(args, Arguments)
			ZeroOrOne(
				LookAhead(
					Terminal("LBRACE")
				)
				NonTerminal(anonymousBody, ClassOrInterfaceBody)
			)
			Action({
				ret = dress(SObjectCreationExpr.make(optionOf(scope), ensureNotNull(typeArgs), (BUTree<SQualifiedType>) type, args, optionOf(anonymousBody)));
			})
		) */
	private int matchAllocationExpression_6_2_2_2(int lookahead) {
		lookahead = matchArguments(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchAllocationExpression_6_2_2_2_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			NonTerminal(ret, ArrayCreationExpr)
			Sequence(
				NonTerminal(args, Arguments)
				ZeroOrOne(
					LookAhead(
						Terminal("LBRACE")
					)
					NonTerminal(anonymousBody, ClassOrInterfaceBody)
				)
				Action({
					ret = dress(SObjectCreationExpr.make(optionOf(scope), ensureNotNull(typeArgs), (BUTree<SQualifiedType>) type, args, optionOf(anonymousBody)));
				})
			)
		) */
	private int matchAllocationExpression_6_2_2(int lookahead) {
		int newLookahead;
		newLookahead = matchArrayCreationExpr(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchAllocationExpression_6_2_2_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			NonTerminal(type, QualifiedType)
			Choice(
				NonTerminal(ret, ArrayCreationExpr)
				Sequence(
					NonTerminal(args, Arguments)
					ZeroOrOne(
						LookAhead(
							Terminal("LBRACE")
						)
						NonTerminal(anonymousBody, ClassOrInterfaceBody)
					)
					Action({
						ret = dress(SObjectCreationExpr.make(optionOf(scope), ensureNotNull(typeArgs), (BUTree<SQualifiedType>) type, args, optionOf(anonymousBody)));
					})
				)
			)
		) */
	private int matchAllocationExpression_6_2(int lookahead) {
		lookahead = matchQualifiedType(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchAllocationExpression_6_2_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				NonTerminal(type, PrimitiveType)
				NonTerminal(ret, ArrayCreationExpr)
			)
			Sequence(
				NonTerminal(type, QualifiedType)
				Choice(
					NonTerminal(ret, ArrayCreationExpr)
					Sequence(
						NonTerminal(args, Arguments)
						ZeroOrOne(
							LookAhead(
								Terminal("LBRACE")
							)
							NonTerminal(anonymousBody, ClassOrInterfaceBody)
						)
						Action({
							ret = dress(SObjectCreationExpr.make(optionOf(scope), ensureNotNull(typeArgs), (BUTree<SQualifiedType>) type, args, optionOf(anonymousBody)));
						})
					)
				)
			)
		) */
	private int matchAllocationExpression_6(int lookahead) {
		int newLookahead;
		newLookahead = matchAllocationExpression_6_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchAllocationExpression_6_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			Action({
				if (scope == null) run();

			})
			Terminal("NEW")
			ZeroOrOne(
				NonTerminal(typeArgs, TypeArguments)
			)
			Action({
				run();
			})
			NonTerminal(annotations, Annotations)
			Choice(
				Sequence(
					NonTerminal(type, PrimitiveType)
					NonTerminal(ret, ArrayCreationExpr)
				)
				Sequence(
					NonTerminal(type, QualifiedType)
					Choice(
						NonTerminal(ret, ArrayCreationExpr)
						Sequence(
							NonTerminal(args, Arguments)
							ZeroOrOne(
								LookAhead(
									Terminal("LBRACE")
								)
								NonTerminal(anonymousBody, ClassOrInterfaceBody)
							)
							Action({
								ret = dress(SObjectCreationExpr.make(optionOf(scope), ensureNotNull(typeArgs), (BUTree<SQualifiedType>) type, args, optionOf(anonymousBody)));
							})
						)
					)
				)
			)
			Action({
				return ret;
			})
		) */
	private int matchAllocationExpression(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.NEW);
		if (lookahead == -1)
			return -1;
		lookahead = matchAllocationExpression_3(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchAnnotations(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchAllocationExpression_6(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(
				ZeroOrOne(
					NonTerminal(TypeArguments)
				)
				NonTerminal(Name)
				Terminal("LPAREN")
			)
			NonTerminal(ret, MethodInvocation)
		) */
	private int matchPrimarySuffixWithoutSuper_1_1_2_3(int lookahead) {
		lookahead = matchMethodInvocation(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(name, Name)
			Action({
				return dress(SFieldAccessExpr.make(optionOf(scope), name));
			})
		) */
	private int matchFieldAccess(int lookahead) {
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				Terminal("THIS")
				Action({
					ret = dress(SThisExpr.make(optionOf(scope)));
				})
			)
			NonTerminal(ret, AllocationExpression)
			Sequence(
				LookAhead(
					ZeroOrOne(
						NonTerminal(TypeArguments)
					)
					NonTerminal(Name)
					Terminal("LPAREN")
				)
				NonTerminal(ret, MethodInvocation)
			)
			NonTerminal(ret, FieldAccess)
		) */
	private int matchPrimarySuffixWithoutSuper_1_1_2(int lookahead) {
		int newLookahead;
		newLookahead = matchPrimarySuffixWithoutSuper_1_1_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchAllocationExpression(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchPrimarySuffixWithoutSuper_1_1_2_3(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchFieldAccess(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			Terminal("DOT")
			Choice(
				Sequence(
					Terminal("THIS")
					Action({
						ret = dress(SThisExpr.make(optionOf(scope)));
					})
				)
				NonTerminal(ret, AllocationExpression)
				Sequence(
					LookAhead(
						ZeroOrOne(
							NonTerminal(TypeArguments)
						)
						NonTerminal(Name)
						Terminal("LPAREN")
					)
					NonTerminal(ret, MethodInvocation)
				)
				NonTerminal(ret, FieldAccess)
			)
		) */
	private int matchPrimarySuffixWithoutSuper_1_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.DOT);
		if (lookahead == -1)
			return -1;
		lookahead = matchPrimarySuffixWithoutSuper_1_1_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("LBRACKET")
			NonTerminal(ret, Expression)
			Terminal("RBRACKET")
			Action({
				ret = dress(SArrayAccessExpr.make(scope, ret));
			})
		) */
	private int matchPrimarySuffixWithoutSuper_1_2(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.LBRACKET);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RBRACKET);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				Terminal("DOT")
				Choice(
					Sequence(
						Terminal("THIS")
						Action({
							ret = dress(SThisExpr.make(optionOf(scope)));
						})
					)
					NonTerminal(ret, AllocationExpression)
					Sequence(
						LookAhead(
							ZeroOrOne(
								NonTerminal(TypeArguments)
							)
							NonTerminal(Name)
							Terminal("LPAREN")
						)
						NonTerminal(ret, MethodInvocation)
					)
					NonTerminal(ret, FieldAccess)
				)
			)
			Sequence(
				Terminal("LBRACKET")
				NonTerminal(ret, Expression)
				Terminal("RBRACKET")
				Action({
					ret = dress(SArrayAccessExpr.make(scope, ret));
				})
			)
		) */
	private int matchPrimarySuffixWithoutSuper_1(int lookahead) {
		int newLookahead;
		newLookahead = matchPrimarySuffixWithoutSuper_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchPrimarySuffixWithoutSuper_1_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			Choice(
				Sequence(
					Terminal("DOT")
					Choice(
						Sequence(
							Terminal("THIS")
							Action({
								ret = dress(SThisExpr.make(optionOf(scope)));
							})
						)
						NonTerminal(ret, AllocationExpression)
						Sequence(
							LookAhead(
								ZeroOrOne(
									NonTerminal(TypeArguments)
								)
								NonTerminal(Name)
								Terminal("LPAREN")
							)
							NonTerminal(ret, MethodInvocation)
						)
						NonTerminal(ret, FieldAccess)
					)
				)
				Sequence(
					Terminal("LBRACKET")
					NonTerminal(ret, Expression)
					Terminal("RBRACKET")
					Action({
						ret = dress(SArrayAccessExpr.make(scope, ret));
					})
				)
			)
			Action({
				return ret;
			})
		) */
	private int matchPrimarySuffixWithoutSuper(int lookahead) {
		lookahead = matchPrimarySuffixWithoutSuper_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(
				NonTerminal(PrimarySuffixWithoutSuper)
			)
			Action({
				lateRun();
			})
			NonTerminal(ret, PrimarySuffixWithoutSuper)
		) */
	private int matchPrimaryExpressionWithoutSuperSuffix_2_1(int lookahead) {
		lookahead = matchPrimarySuffixWithoutSuper(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrMore(
			LookAhead(
				NonTerminal(PrimarySuffixWithoutSuper)
			)
			Action({
				lateRun();
			})
			NonTerminal(ret, PrimarySuffixWithoutSuper)
		) */
	private int matchPrimaryExpressionWithoutSuperSuffix_2(int lookahead) {
		int newLookahead;
		newLookahead = matchPrimaryExpressionWithoutSuperSuffix_2_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchPrimaryExpressionWithoutSuperSuffix_2_1(lookahead);
		}
		return lookahead;
	}

	/* Sequence(
			NonTerminal(ret, PrimaryPrefix)
			ZeroOrMore(
				LookAhead(
					NonTerminal(PrimarySuffixWithoutSuper)
				)
				Action({
					lateRun();
				})
				NonTerminal(ret, PrimarySuffixWithoutSuper)
			)
			Action({
				return ret;
			})
		) */
	private int matchPrimaryExpressionWithoutSuperSuffix(int lookahead) {
		lookahead = matchPrimaryPrefix(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchPrimaryExpressionWithoutSuperSuffix_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(
				NonTerminal(PrimaryExpressionWithoutSuperSuffix)
				Terminal("DOT")
			)
			NonTerminal(expr, PrimaryExpressionWithoutSuperSuffix)
			Terminal("DOT")
		) */
	private int matchExplicitConstructorInvocation_2_2_1_1(int lookahead) {
		lookahead = matchPrimaryExpressionWithoutSuperSuffix(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.DOT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			LookAhead(
				NonTerminal(PrimaryExpressionWithoutSuperSuffix)
				Terminal("DOT")
			)
			NonTerminal(expr, PrimaryExpressionWithoutSuperSuffix)
			Terminal("DOT")
		) */
	private int matchExplicitConstructorInvocation_2_2_1(int lookahead) {
		int newLookahead;
		newLookahead = matchExplicitConstructorInvocation_2_2_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(typeArgs, TypeArguments)
		) */
	private int matchExplicitConstructorInvocation_2_2_2_1(int lookahead) {
		lookahead = matchTypeArguments(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			NonTerminal(typeArgs, TypeArguments)
		) */
	private int matchExplicitConstructorInvocation_2_2_2(int lookahead) {
		int newLookahead;
		newLookahead = matchExplicitConstructorInvocation_2_2_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			ZeroOrOne(
				LookAhead(
					NonTerminal(PrimaryExpressionWithoutSuperSuffix)
					Terminal("DOT")
				)
				NonTerminal(expr, PrimaryExpressionWithoutSuperSuffix)
				Terminal("DOT")
			)
			ZeroOrOne(
				NonTerminal(typeArgs, TypeArguments)
			)
			Terminal("SUPER")
			NonTerminal(args, Arguments)
			Terminal("SEMICOLON")
		) */
	private int matchExplicitConstructorInvocation_2_2(int lookahead) {
		lookahead = matchExplicitConstructorInvocation_2_2_1(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchExplicitConstructorInvocation_2_2_2(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.SUPER);
		if (lookahead == -1)
			return -1;
		lookahead = matchArguments(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.SEMICOLON);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				LookAhead(
					ZeroOrOne(
						NonTerminal(TypeArguments)
					)
					Terminal("THIS")
					Terminal("LPAREN")
				)
				ZeroOrOne(
					NonTerminal(typeArgs, TypeArguments)
				)
				Terminal("THIS")
				Action({
					isThis = true;
				})
				NonTerminal(args, Arguments)
				Terminal("SEMICOLON")
			)
			Sequence(
				ZeroOrOne(
					LookAhead(
						NonTerminal(PrimaryExpressionWithoutSuperSuffix)
						Terminal("DOT")
					)
					NonTerminal(expr, PrimaryExpressionWithoutSuperSuffix)
					Terminal("DOT")
				)
				ZeroOrOne(
					NonTerminal(typeArgs, TypeArguments)
				)
				Terminal("SUPER")
				NonTerminal(args, Arguments)
				Terminal("SEMICOLON")
			)
		) */
	private int matchExplicitConstructorInvocation_2(int lookahead) {
		int newLookahead;
		newLookahead = matchExplicitConstructorInvocation_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchExplicitConstructorInvocation_2_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			Action({
				run();
			})
			Choice(
				Sequence(
					LookAhead(
						ZeroOrOne(
							NonTerminal(TypeArguments)
						)
						Terminal("THIS")
						Terminal("LPAREN")
					)
					ZeroOrOne(
						NonTerminal(typeArgs, TypeArguments)
					)
					Terminal("THIS")
					Action({
						isThis = true;
					})
					NonTerminal(args, Arguments)
					Terminal("SEMICOLON")
				)
				Sequence(
					ZeroOrOne(
						LookAhead(
							NonTerminal(PrimaryExpressionWithoutSuperSuffix)
							Terminal("DOT")
						)
						NonTerminal(expr, PrimaryExpressionWithoutSuperSuffix)
						Terminal("DOT")
					)
					ZeroOrOne(
						NonTerminal(typeArgs, TypeArguments)
					)
					Terminal("SUPER")
					NonTerminal(args, Arguments)
					Terminal("SEMICOLON")
				)
			)
			Action({
				return dress(SExplicitConstructorInvocationStmt.make(ensureNotNull(typeArgs), isThis, optionOf(expr), args));
			})
		) */
	private int matchExplicitConstructorInvocation(int lookahead) {
		lookahead = matchExplicitConstructorInvocation_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(
				NonTerminal(ExplicitConstructorInvocation)
			)
			NonTerminal(stmt, ExplicitConstructorInvocation)
			Action({
				stmts = append(stmts, stmt);
			})
		) */
	private int matchConstructorDecl_7_1_1_1_2_1(int lookahead) {
		lookahead = matchExplicitConstructorInvocation(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(2)
			NonTerminal(stmt, BlockStatement)
			Action({
				stmts = append(stmts, stmt);
			})
		) */
	private int matchConstructorDecl_7_1_1_1_2_2(int lookahead) {
		lookahead = matchBlockStatement(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				LookAhead(
					NonTerminal(ExplicitConstructorInvocation)
				)
				NonTerminal(stmt, ExplicitConstructorInvocation)
				Action({
					stmts = append(stmts, stmt);
				})
			)
			Sequence(
				LookAhead(2)
				NonTerminal(stmt, BlockStatement)
				Action({
					stmts = append(stmts, stmt);
				})
			)
		) */
	private int matchConstructorDecl_7_1_1_1_2(int lookahead) {
		int newLookahead;
		newLookahead = matchConstructorDecl_7_1_1_1_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchConstructorDecl_7_1_1_1_2_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			LookAhead(2)
			NonTerminal(stmt, BlockStatement)
			Action({
				stmts = append(stmts, stmt);
			})
		) */
	private int matchConstructorDecl_7_1_1_1_3_1(int lookahead) {
		lookahead = matchBlockStatement(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrMore(
			LookAhead(2)
			NonTerminal(stmt, BlockStatement)
			Action({
				stmts = append(stmts, stmt);
			})
		) */
	private int matchConstructorDecl_7_1_1_1_3(int lookahead) {
		int newLookahead;
		newLookahead = matchConstructorDecl_7_1_1_1_3_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchConstructorDecl_7_1_1_1_3_1(lookahead);
		}
		return lookahead;
	}

	/* Sequence(
			LookAhead(2)
			Choice(
				Sequence(
					LookAhead(
						NonTerminal(ExplicitConstructorInvocation)
					)
					NonTerminal(stmt, ExplicitConstructorInvocation)
					Action({
						stmts = append(stmts, stmt);
					})
				)
				Sequence(
					LookAhead(2)
					NonTerminal(stmt, BlockStatement)
					Action({
						stmts = append(stmts, stmt);
					})
				)
			)
			ZeroOrMore(
				LookAhead(2)
				NonTerminal(stmt, BlockStatement)
				Action({
					stmts = append(stmts, stmt);
				})
			)
		) */
	private int matchConstructorDecl_7_1_1_1(int lookahead) {
		lookahead = matchConstructorDecl_7_1_1_1_2(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchConstructorDecl_7_1_1_1_3(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(			quotesMode
	)
			NonTerminal(stmts, NodeListVar)
		) */
	private int matchConstructorDecl_7_1_1_2(int lookahead) {
		lookahead = quotesMode ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = matchNodeListVar(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				LookAhead(2)
				Choice(
					Sequence(
						LookAhead(
							NonTerminal(ExplicitConstructorInvocation)
						)
						NonTerminal(stmt, ExplicitConstructorInvocation)
						Action({
							stmts = append(stmts, stmt);
						})
					)
					Sequence(
						LookAhead(2)
						NonTerminal(stmt, BlockStatement)
						Action({
							stmts = append(stmts, stmt);
						})
					)
				)
				ZeroOrMore(
					LookAhead(2)
					NonTerminal(stmt, BlockStatement)
					Action({
						stmts = append(stmts, stmt);
					})
				)
			)
			Sequence(
				LookAhead(				quotesMode
	)
				NonTerminal(stmts, NodeListVar)
			)
		) */
	private int matchConstructorDecl_7_1_1(int lookahead) {
		int newLookahead;
		newLookahead = matchConstructorDecl_7_1_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchConstructorDecl_7_1_1_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			Choice(
				Sequence(
					LookAhead(2)
					Choice(
						Sequence(
							LookAhead(
								NonTerminal(ExplicitConstructorInvocation)
							)
							NonTerminal(stmt, ExplicitConstructorInvocation)
							Action({
								stmts = append(stmts, stmt);
							})
						)
						Sequence(
							LookAhead(2)
							NonTerminal(stmt, BlockStatement)
							Action({
								stmts = append(stmts, stmt);
							})
						)
					)
					ZeroOrMore(
						LookAhead(2)
						NonTerminal(stmt, BlockStatement)
						Action({
							stmts = append(stmts, stmt);
						})
					)
				)
				Sequence(
					LookAhead(					quotesMode
	)
					NonTerminal(stmts, NodeListVar)
				)
			)
		) */
	private int matchConstructorDecl_7_1(int lookahead) {
		lookahead = matchConstructorDecl_7_1_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			Choice(
				Sequence(
					LookAhead(2)
					Choice(
						Sequence(
							LookAhead(
								NonTerminal(ExplicitConstructorInvocation)
							)
							NonTerminal(stmt, ExplicitConstructorInvocation)
							Action({
								stmts = append(stmts, stmt);
							})
						)
						Sequence(
							LookAhead(2)
							NonTerminal(stmt, BlockStatement)
							Action({
								stmts = append(stmts, stmt);
							})
						)
					)
					ZeroOrMore(
						LookAhead(2)
						NonTerminal(stmt, BlockStatement)
						Action({
							stmts = append(stmts, stmt);
						})
					)
				)
				Sequence(
					LookAhead(					quotesMode
	)
					NonTerminal(stmts, NodeListVar)
				)
			)
		) */
	private int matchConstructorDecl_7(int lookahead) {
		int newLookahead;
		newLookahead = matchConstructorDecl_7_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			ZeroOrOne(
				NonTerminal(typeParameters, TypeParameters)
			)
			NonTerminal(name, Name)
			NonTerminal(parameters, FormalParameters)
			ZeroOrOne(
				NonTerminal(throwsClause, ThrowsClause)
			)
			Action({
				run();
			})
			Terminal("LBRACE")
			ZeroOrOne(
				Choice(
					Sequence(
						LookAhead(2)
						Choice(
							Sequence(
								LookAhead(
									NonTerminal(ExplicitConstructorInvocation)
								)
								NonTerminal(stmt, ExplicitConstructorInvocation)
								Action({
									stmts = append(stmts, stmt);
								})
							)
							Sequence(
								LookAhead(2)
								NonTerminal(stmt, BlockStatement)
								Action({
									stmts = append(stmts, stmt);
								})
							)
						)
						ZeroOrMore(
							LookAhead(2)
							NonTerminal(stmt, BlockStatement)
							Action({
								stmts = append(stmts, stmt);
							})
						)
					)
					Sequence(
						LookAhead(						quotesMode
	)
						NonTerminal(stmts, NodeListVar)
					)
				)
			)
			Terminal("RBRACE")
			Action({
				block = dress(SBlockStmt.make(stmts));
			})
			Action({
				return dress(SConstructorDecl.make(modifiers, ensureNotNull(typeParameters), name, parameters, ensureNotNull(throwsClause), block));
			})
		) */
	private int matchConstructorDecl(int lookahead) {
		lookahead = matchConstructorDecl_1(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchFormalParameters(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchConstructorDecl_4(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.LBRACE);
		if (lookahead == -1)
			return -1;
		lookahead = matchConstructorDecl_7(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RBRACE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(
				ZeroOrOne(
					NonTerminal(TypeParameters)
				)
				NonTerminal(Name)
				Terminal("LPAREN")
			)
			NonTerminal(ret, ConstructorDecl)
			Action({
				if (typeKind == TypeKind.Interface) ret = ret.withProblem(new BUProblem(Severity.ERROR, "An interface cannot have constructors"));

			})
		) */
	private int matchClassOrInterfaceBodyDecl_2_2_3_5(int lookahead) {
		lookahead = matchConstructorDecl(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(
				NonTerminal(Type)
				NonTerminal(Name)
				ZeroOrMore(
					Terminal("LBRACKET")
					Terminal("RBRACKET")
				)
				Choice(
					Terminal("COMMA")
					Terminal("ASSIGN")
					Terminal("SEMICOLON")
				)
			)
			NonTerminal(ret, FieldDecl)
		) */
	private int matchClassOrInterfaceBodyDecl_2_2_3_6(int lookahead) {
		lookahead = matchFieldDecl(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(typeParameters, TypeParameters)
		) */
	private int matchMethodDecl_1_1(int lookahead) {
		lookahead = matchTypeParameters(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			NonTerminal(typeParameters, TypeParameters)
		) */
	private int matchMethodDecl_1(int lookahead) {
		int newLookahead;
		newLookahead = matchMethodDecl_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			Action({
				run();
			})
			Terminal("VOID")
			Action({
				ret = dress(SVoidType.make());
			})
		) */
	private int matchResultType_1_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.VOID);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				Action({
					run();
				})
				Terminal("VOID")
				Action({
					ret = dress(SVoidType.make());
				})
			)
			NonTerminal(ret, Type)
		) */
	private int matchResultType_1(int lookahead) {
		int newLookahead;
		newLookahead = matchResultType_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchType(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			Choice(
				Sequence(
					Action({
						run();
					})
					Terminal("VOID")
					Action({
						ret = dress(SVoidType.make());
					})
				)
				NonTerminal(ret, Type)
			)
			Action({
				return ret;
			})
		) */
	private int matchResultType(int lookahead) {
		lookahead = matchResultType_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(throwsClause, ThrowsClause)
		) */
	private int matchMethodDecl_6_1(int lookahead) {
		lookahead = matchThrowsClause(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			NonTerminal(throwsClause, ThrowsClause)
		) */
	private int matchMethodDecl_6(int lookahead) {
		int newLookahead;
		newLookahead = matchMethodDecl_6_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			Terminal("SEMICOLON")
			Action({
				if (modifiers != null && contains(modifiers, SModifier.make(ModifierKeyword.Default))) problem = new BUProblem(Severity.ERROR, "Default methods must have a body");

			})
		) */
	private int matchMethodDecl_7_2(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.SEMICOLON);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			NonTerminal(block, Block)
			Sequence(
				Terminal("SEMICOLON")
				Action({
					if (modifiers != null && contains(modifiers, SModifier.make(ModifierKeyword.Default))) problem = new BUProblem(Severity.ERROR, "Default methods must have a body");

				})
			)
		) */
	private int matchMethodDecl_7(int lookahead) {
		int newLookahead;
		newLookahead = matchBlock(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchMethodDecl_7_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			ZeroOrOne(
				NonTerminal(typeParameters, TypeParameters)
			)
			NonTerminal(type, ResultType)
			NonTerminal(name, Name)
			NonTerminal(parameters, FormalParameters)
			NonTerminal(arrayDims, ArrayDims)
			ZeroOrOne(
				NonTerminal(throwsClause, ThrowsClause)
			)
			Choice(
				NonTerminal(block, Block)
				Sequence(
					Terminal("SEMICOLON")
					Action({
						if (modifiers != null && contains(modifiers, SModifier.make(ModifierKeyword.Default))) problem = new BUProblem(Severity.ERROR, "Default methods must have a body");

					})
				)
			)
			Action({
				return dress(SMethodDecl.make(modifiers, ensureNotNull(typeParameters), type, name, parameters, arrayDims, ensureNotNull(throwsClause), optionOf(block))).withProblem(problem);
			})
		) */
	private int matchMethodDecl(int lookahead) {
		lookahead = matchMethodDecl_1(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchResultType(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchFormalParameters(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchArrayDims(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchMethodDecl_6(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchMethodDecl_7(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				NonTerminal(ret, InitializerDecl)
				Action({
					if (typeKind == TypeKind.Interface) ret = ret.withProblem(new BUProblem(Severity.ERROR, "An interface cannot have initializers"));

				})
			)
			NonTerminal(ret, ClassOrInterfaceDecl)
			NonTerminal(ret, EnumDecl)
			NonTerminal(ret, AnnotationTypeDecl)
			Sequence(
				LookAhead(
					ZeroOrOne(
						NonTerminal(TypeParameters)
					)
					NonTerminal(Name)
					Terminal("LPAREN")
				)
				NonTerminal(ret, ConstructorDecl)
				Action({
					if (typeKind == TypeKind.Interface) ret = ret.withProblem(new BUProblem(Severity.ERROR, "An interface cannot have constructors"));

				})
			)
			Sequence(
				LookAhead(
					NonTerminal(Type)
					NonTerminal(Name)
					ZeroOrMore(
						Terminal("LBRACKET")
						Terminal("RBRACKET")
					)
					Choice(
						Terminal("COMMA")
						Terminal("ASSIGN")
						Terminal("SEMICOLON")
					)
				)
				NonTerminal(ret, FieldDecl)
			)
			NonTerminal(ret, MethodDecl)
		) */
	private int matchClassOrInterfaceBodyDecl_2_2_3(int lookahead) {
		int newLookahead;
		newLookahead = matchClassOrInterfaceBodyDecl_2_2_3_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchClassOrInterfaceDecl(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchEnumDecl(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchAnnotationTypeDecl(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchClassOrInterfaceBodyDecl_2_2_3_5(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchClassOrInterfaceBodyDecl_2_2_3_6(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchMethodDecl(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			NonTerminal(modifiers, Modifiers)
			Action({
				if (modifiers != null && contains(modifiers, SModifier.make(ModifierKeyword.Default)) && typeKind != TypeKind.Interface) problem = new BUProblem(Severity.ERROR, "Only interfaces can have default members");

			})
			Choice(
				Sequence(
					NonTerminal(ret, InitializerDecl)
					Action({
						if (typeKind == TypeKind.Interface) ret = ret.withProblem(new BUProblem(Severity.ERROR, "An interface cannot have initializers"));

					})
				)
				NonTerminal(ret, ClassOrInterfaceDecl)
				NonTerminal(ret, EnumDecl)
				NonTerminal(ret, AnnotationTypeDecl)
				Sequence(
					LookAhead(
						ZeroOrOne(
							NonTerminal(TypeParameters)
						)
						NonTerminal(Name)
						Terminal("LPAREN")
					)
					NonTerminal(ret, ConstructorDecl)
					Action({
						if (typeKind == TypeKind.Interface) ret = ret.withProblem(new BUProblem(Severity.ERROR, "An interface cannot have constructors"));

					})
				)
				Sequence(
					LookAhead(
						NonTerminal(Type)
						NonTerminal(Name)
						ZeroOrMore(
							Terminal("LBRACKET")
							Terminal("RBRACKET")
						)
						Choice(
							Terminal("COMMA")
							Terminal("ASSIGN")
							Terminal("SEMICOLON")
						)
					)
					NonTerminal(ret, FieldDecl)
				)
				NonTerminal(ret, MethodDecl)
			)
		) */
	private int matchClassOrInterfaceBodyDecl_2_2(int lookahead) {
		lookahead = matchModifiers(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchClassOrInterfaceBodyDecl_2_2_3(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				Terminal("SEMICOLON")
				Action({
					ret = dress(SEmptyMemberDecl.make());
				})
			)
			Sequence(
				NonTerminal(modifiers, Modifiers)
				Action({
					if (modifiers != null && contains(modifiers, SModifier.make(ModifierKeyword.Default)) && typeKind != TypeKind.Interface) problem = new BUProblem(Severity.ERROR, "Only interfaces can have default members");

				})
				Choice(
					Sequence(
						NonTerminal(ret, InitializerDecl)
						Action({
							if (typeKind == TypeKind.Interface) ret = ret.withProblem(new BUProblem(Severity.ERROR, "An interface cannot have initializers"));

						})
					)
					NonTerminal(ret, ClassOrInterfaceDecl)
					NonTerminal(ret, EnumDecl)
					NonTerminal(ret, AnnotationTypeDecl)
					Sequence(
						LookAhead(
							ZeroOrOne(
								NonTerminal(TypeParameters)
							)
							NonTerminal(Name)
							Terminal("LPAREN")
						)
						NonTerminal(ret, ConstructorDecl)
						Action({
							if (typeKind == TypeKind.Interface) ret = ret.withProblem(new BUProblem(Severity.ERROR, "An interface cannot have constructors"));

						})
					)
					Sequence(
						LookAhead(
							NonTerminal(Type)
							NonTerminal(Name)
							ZeroOrMore(
								Terminal("LBRACKET")
								Terminal("RBRACKET")
							)
							Choice(
								Terminal("COMMA")
								Terminal("ASSIGN")
								Terminal("SEMICOLON")
							)
						)
						NonTerminal(ret, FieldDecl)
					)
					NonTerminal(ret, MethodDecl)
				)
			)
		) */
	private int matchClassOrInterfaceBodyDecl_2(int lookahead) {
		int newLookahead;
		newLookahead = matchClassOrInterfaceBodyDecl_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchClassOrInterfaceBodyDecl_2_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			Action({
				run();
			})
			Choice(
				Sequence(
					Terminal("SEMICOLON")
					Action({
						ret = dress(SEmptyMemberDecl.make());
					})
				)
				Sequence(
					NonTerminal(modifiers, Modifiers)
					Action({
						if (modifiers != null && contains(modifiers, SModifier.make(ModifierKeyword.Default)) && typeKind != TypeKind.Interface) problem = new BUProblem(Severity.ERROR, "Only interfaces can have default members");

					})
					Choice(
						Sequence(
							NonTerminal(ret, InitializerDecl)
							Action({
								if (typeKind == TypeKind.Interface) ret = ret.withProblem(new BUProblem(Severity.ERROR, "An interface cannot have initializers"));

							})
						)
						NonTerminal(ret, ClassOrInterfaceDecl)
						NonTerminal(ret, EnumDecl)
						NonTerminal(ret, AnnotationTypeDecl)
						Sequence(
							LookAhead(
								ZeroOrOne(
									NonTerminal(TypeParameters)
								)
								NonTerminal(Name)
								Terminal("LPAREN")
							)
							NonTerminal(ret, ConstructorDecl)
							Action({
								if (typeKind == TypeKind.Interface) ret = ret.withProblem(new BUProblem(Severity.ERROR, "An interface cannot have constructors"));

							})
						)
						Sequence(
							LookAhead(
								NonTerminal(Type)
								NonTerminal(Name)
								ZeroOrMore(
									Terminal("LBRACKET")
									Terminal("RBRACKET")
								)
								Choice(
									Terminal("COMMA")
									Terminal("ASSIGN")
									Terminal("SEMICOLON")
								)
							)
							NonTerminal(ret, FieldDecl)
						)
						NonTerminal(ret, MethodDecl)
					)
				)
			)
			Action({
				return ret.withProblem(problem);
			})
		) */
	private int matchClassOrInterfaceBodyDecl(int lookahead) {
		lookahead = matchClassOrInterfaceBodyDecl_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(member, ClassOrInterfaceBodyDecl)
			Action({
				ret = append(ret, member);
			})
		) */
	private int matchClassOrInterfaceBodyDecls_1_1_1_1_1(int lookahead) {
		lookahead = matchClassOrInterfaceBodyDecl(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* OneOrMore(
			NonTerminal(member, ClassOrInterfaceBodyDecl)
			Action({
				ret = append(ret, member);
			})
		) */
	private int matchClassOrInterfaceBodyDecls_1_1_1_1(int lookahead) {
		int newLookahead;
		newLookahead = matchClassOrInterfaceBodyDecls_1_1_1_1_1(lookahead);
		if (newLookahead == -1)
			return -1;
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchClassOrInterfaceBodyDecls_1_1_1_1_1(lookahead);
		}
		return lookahead;
	}

	/* Sequence(
			LookAhead(			quotesMode
	)
			NonTerminal(ret, NodeListVar)
		) */
	private int matchClassOrInterfaceBodyDecls_1_1_1_2(int lookahead) {
		lookahead = quotesMode ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = matchNodeListVar(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			OneOrMore(
				NonTerminal(member, ClassOrInterfaceBodyDecl)
				Action({
					ret = append(ret, member);
				})
			)
			Sequence(
				LookAhead(				quotesMode
	)
				NonTerminal(ret, NodeListVar)
			)
		) */
	private int matchClassOrInterfaceBodyDecls_1_1_1(int lookahead) {
		int newLookahead;
		newLookahead = matchClassOrInterfaceBodyDecls_1_1_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchClassOrInterfaceBodyDecls_1_1_1_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			Choice(
				OneOrMore(
					NonTerminal(member, ClassOrInterfaceBodyDecl)
					Action({
						ret = append(ret, member);
					})
				)
				Sequence(
					LookAhead(					quotesMode
	)
					NonTerminal(ret, NodeListVar)
				)
			)
		) */
	private int matchClassOrInterfaceBodyDecls_1_1(int lookahead) {
		lookahead = matchClassOrInterfaceBodyDecls_1_1_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			Choice(
				OneOrMore(
					NonTerminal(member, ClassOrInterfaceBodyDecl)
					Action({
						ret = append(ret, member);
					})
				)
				Sequence(
					LookAhead(					quotesMode
	)
					NonTerminal(ret, NodeListVar)
				)
			)
		) */
	private int matchClassOrInterfaceBodyDecls_1(int lookahead) {
		int newLookahead;
		newLookahead = matchClassOrInterfaceBodyDecls_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			ZeroOrOne(
				Choice(
					OneOrMore(
						NonTerminal(member, ClassOrInterfaceBodyDecl)
						Action({
							ret = append(ret, member);
						})
					)
					Sequence(
						LookAhead(						quotesMode
	)
						NonTerminal(ret, NodeListVar)
					)
				)
			)
			Action({
				return ret;
			})
		) */
	private int matchClassOrInterfaceBodyDecls(int lookahead) {
		lookahead = matchClassOrInterfaceBodyDecls_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("LBRACE")
			NonTerminal(ret, ClassOrInterfaceBodyDecls)
			Terminal("RBRACE")
			Action({
				return ret;
			})
		) */
	private int matchClassOrInterfaceBody(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.LBRACE);
		if (lookahead == -1)
			return -1;
		lookahead = matchClassOrInterfaceBodyDecls(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RBRACE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Choice(
				Sequence(
					Terminal("CLASS")
					Action({
						typeKind = TypeKind.Class;
					})
					NonTerminal(name, Name)
					ZeroOrOne(
						NonTerminal(typeParams, TypeParameters)
					)
					ZeroOrOne(
						Terminal("EXTENDS")
						NonTerminal(superClassType, AnnotatedQualifiedType)
					)
					ZeroOrOne(
						NonTerminal(implementsClause, ImplementsList)
					)
				)
				Sequence(
					Terminal("INTERFACE")
					Action({
						typeKind = TypeKind.Interface;
					})
					NonTerminal(name, Name)
					ZeroOrOne(
						NonTerminal(typeParams, TypeParameters)
					)
					ZeroOrOne(
						NonTerminal(extendsClause, ExtendsList)
					)
				)
			)
			NonTerminal(members, ClassOrInterfaceBody)
			Action({
				if (typeKind == TypeKind.Interface)
		return dress(SInterfaceDecl.make(modifiers, name, ensureNotNull(typeParams), ensureNotNull(extendsClause), members)).withProblem(problem.value);
	else {
		return dress(SClassDecl.make(modifiers, name, ensureNotNull(typeParams), optionOf(superClassType), ensureNotNull(implementsClause), members));
	}
			})
		) */
	private int matchClassOrInterfaceDecl(int lookahead) {
		lookahead = matchClassOrInterfaceDecl_1(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchClassOrInterfaceBody(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(
				NonTerminal(ModifiersNoDefault)
				Choice(
					Terminal("CLASS")
					Terminal("INTERFACE")
				)
			)
			Action({
				run();
			})
			Action({
				run();
			})
			NonTerminal(modifiers, ModifiersNoDefault)
			NonTerminal(typeDecl, ClassOrInterfaceDecl)
			Action({
				ret = dress(STypeDeclarationStmt.make(typeDecl));
			})
		) */
	private int matchBlockStatement_1_1(int lookahead) {
		lookahead = matchModifiersNoDefault(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchClassOrInterfaceDecl(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(type, Type)
			NonTerminal(variables, VariableDeclarators)
			Action({
				return dress(SLocalVariableDecl.make(modifiers, type, variables));
			})
		) */
	private int matchVariableDecl(int lookahead) {
		lookahead = matchType(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchVariableDeclarators(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Action({
				run();
			})
			Action({
				run();
			})
			NonTerminal(modifiers, ModifiersNoDefault)
			NonTerminal(variableDecl, VariableDecl)
			Action({
				return dress(SVariableDeclarationExpr.make(variableDecl));
			})
		) */
	private int matchVariableDeclExpression(int lookahead) {
		lookahead = matchModifiersNoDefault(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchVariableDecl(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(
				NonTerminal(VariableDeclExpression)
			)
			Action({
				run();
			})
			NonTerminal(expr, VariableDeclExpression)
			Terminal("SEMICOLON")
			Action({
				ret = dress(SExpressionStmt.make(expr));
			})
		) */
	private int matchBlockStatement_1_2(int lookahead) {
		lookahead = matchVariableDeclExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.SEMICOLON);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Action({
				run();
			})
			NonTerminal(label, Name)
			Terminal("COLON")
			NonTerminal(stmt, Statement)
			Action({
				return dress(SLabeledStmt.make(label, stmt));
			})
		) */
	private int matchLabeledStatement(int lookahead) {
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.COLON);
		if (lookahead == -1)
			return -1;
		lookahead = matchStatement(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(2)
			NonTerminal(ret, LabeledStatement)
		) */
	private int matchStatement_1_1(int lookahead) {
		lookahead = matchLabeledStatement(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("COLON")
			NonTerminal(msg, Expression)
		) */
	private int matchAssertStatement_4_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.COLON);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			Terminal("COLON")
			NonTerminal(msg, Expression)
		) */
	private int matchAssertStatement_4(int lookahead) {
		int newLookahead;
		newLookahead = matchAssertStatement_4_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			Action({
				run();
			})
			Terminal("ASSERT")
			NonTerminal(check, Expression)
			ZeroOrOne(
				Terminal("COLON")
				NonTerminal(msg, Expression)
			)
			Terminal("SEMICOLON")
			Action({
				return dress(SAssertStmt.make(check, optionOf(msg)));
			})
		) */
	private int matchAssertStatement(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.ASSERT);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchAssertStatement_4(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.SEMICOLON);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Action({
				run();
			})
			Terminal("SEMICOLON")
			Action({
				return dress(SEmptyStmt.make());
			})
		) */
	private int matchEmptyStatement(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.SEMICOLON);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Action({
				lateRun();
			})
			Terminal("INCR")
			Action({
				expr = dress(SUnaryExpr.make(UnaryOp.PostIncrement, expr));
			})
		) */
	private int matchStatementExpression_2_2_2_1_1_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.INCR);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Action({
				lateRun();
			})
			Terminal("DECR")
			Action({
				expr = dress(SUnaryExpr.make(UnaryOp.PostDecrement, expr));
			})
		) */
	private int matchStatementExpression_2_2_2_1_1_2(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.DECR);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("ASSIGN")
			Action({
				ret = AssignOp.Normal;
			})
		) */
	private int matchAssignmentOperator_1_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.ASSIGN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("STARASSIGN")
			Action({
				ret = AssignOp.Times;
			})
		) */
	private int matchAssignmentOperator_1_2(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.STARASSIGN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("SLASHASSIGN")
			Action({
				ret = AssignOp.Divide;
			})
		) */
	private int matchAssignmentOperator_1_3(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.SLASHASSIGN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("REMASSIGN")
			Action({
				ret = AssignOp.Remainder;
			})
		) */
	private int matchAssignmentOperator_1_4(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.REMASSIGN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("PLUSASSIGN")
			Action({
				ret = AssignOp.Plus;
			})
		) */
	private int matchAssignmentOperator_1_5(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.PLUSASSIGN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("MINUSASSIGN")
			Action({
				ret = AssignOp.Minus;
			})
		) */
	private int matchAssignmentOperator_1_6(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.MINUSASSIGN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("LSHIFTASSIGN")
			Action({
				ret = AssignOp.LeftShift;
			})
		) */
	private int matchAssignmentOperator_1_7(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.LSHIFTASSIGN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("RSIGNEDSHIFTASSIGN")
			Action({
				ret = AssignOp.RightSignedShift;
			})
		) */
	private int matchAssignmentOperator_1_8(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.RSIGNEDSHIFTASSIGN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("RUNSIGNEDSHIFTASSIGN")
			Action({
				ret = AssignOp.RightUnsignedShift;
			})
		) */
	private int matchAssignmentOperator_1_9(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.RUNSIGNEDSHIFTASSIGN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("ANDASSIGN")
			Action({
				ret = AssignOp.And;
			})
		) */
	private int matchAssignmentOperator_1_10(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.ANDASSIGN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("XORASSIGN")
			Action({
				ret = AssignOp.XOr;
			})
		) */
	private int matchAssignmentOperator_1_11(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.XORASSIGN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("ORASSIGN")
			Action({
				ret = AssignOp.Or;
			})
		) */
	private int matchAssignmentOperator_1_12(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.ORASSIGN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				Terminal("ASSIGN")
				Action({
					ret = AssignOp.Normal;
				})
			)
			Sequence(
				Terminal("STARASSIGN")
				Action({
					ret = AssignOp.Times;
				})
			)
			Sequence(
				Terminal("SLASHASSIGN")
				Action({
					ret = AssignOp.Divide;
				})
			)
			Sequence(
				Terminal("REMASSIGN")
				Action({
					ret = AssignOp.Remainder;
				})
			)
			Sequence(
				Terminal("PLUSASSIGN")
				Action({
					ret = AssignOp.Plus;
				})
			)
			Sequence(
				Terminal("MINUSASSIGN")
				Action({
					ret = AssignOp.Minus;
				})
			)
			Sequence(
				Terminal("LSHIFTASSIGN")
				Action({
					ret = AssignOp.LeftShift;
				})
			)
			Sequence(
				Terminal("RSIGNEDSHIFTASSIGN")
				Action({
					ret = AssignOp.RightSignedShift;
				})
			)
			Sequence(
				Terminal("RUNSIGNEDSHIFTASSIGN")
				Action({
					ret = AssignOp.RightUnsignedShift;
				})
			)
			Sequence(
				Terminal("ANDASSIGN")
				Action({
					ret = AssignOp.And;
				})
			)
			Sequence(
				Terminal("XORASSIGN")
				Action({
					ret = AssignOp.XOr;
				})
			)
			Sequence(
				Terminal("ORASSIGN")
				Action({
					ret = AssignOp.Or;
				})
			)
		) */
	private int matchAssignmentOperator_1(int lookahead) {
		int newLookahead;
		newLookahead = matchAssignmentOperator_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchAssignmentOperator_1_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchAssignmentOperator_1_3(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchAssignmentOperator_1_4(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchAssignmentOperator_1_5(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchAssignmentOperator_1_6(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchAssignmentOperator_1_7(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchAssignmentOperator_1_8(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchAssignmentOperator_1_9(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchAssignmentOperator_1_10(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchAssignmentOperator_1_11(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchAssignmentOperator_1_12(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			Choice(
				Sequence(
					Terminal("ASSIGN")
					Action({
						ret = AssignOp.Normal;
					})
				)
				Sequence(
					Terminal("STARASSIGN")
					Action({
						ret = AssignOp.Times;
					})
				)
				Sequence(
					Terminal("SLASHASSIGN")
					Action({
						ret = AssignOp.Divide;
					})
				)
				Sequence(
					Terminal("REMASSIGN")
					Action({
						ret = AssignOp.Remainder;
					})
				)
				Sequence(
					Terminal("PLUSASSIGN")
					Action({
						ret = AssignOp.Plus;
					})
				)
				Sequence(
					Terminal("MINUSASSIGN")
					Action({
						ret = AssignOp.Minus;
					})
				)
				Sequence(
					Terminal("LSHIFTASSIGN")
					Action({
						ret = AssignOp.LeftShift;
					})
				)
				Sequence(
					Terminal("RSIGNEDSHIFTASSIGN")
					Action({
						ret = AssignOp.RightSignedShift;
					})
				)
				Sequence(
					Terminal("RUNSIGNEDSHIFTASSIGN")
					Action({
						ret = AssignOp.RightUnsignedShift;
					})
				)
				Sequence(
					Terminal("ANDASSIGN")
					Action({
						ret = AssignOp.And;
					})
				)
				Sequence(
					Terminal("XORASSIGN")
					Action({
						ret = AssignOp.XOr;
					})
				)
				Sequence(
					Terminal("ORASSIGN")
					Action({
						ret = AssignOp.Or;
					})
				)
			)
			Action({
				return ret;
			})
		) */
	private int matchAssignmentOperator(int lookahead) {
		lookahead = matchAssignmentOperator_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Action({
				lateRun();
			})
			NonTerminal(op, AssignmentOperator)
			NonTerminal(value, Expression)
			Action({
				expr = dress(SAssignExpr.make(expr, op, value));
			})
		) */
	private int matchStatementExpression_2_2_2_1_1_3(int lookahead) {
		lookahead = matchAssignmentOperator(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				Action({
					lateRun();
				})
				Terminal("INCR")
				Action({
					expr = dress(SUnaryExpr.make(UnaryOp.PostIncrement, expr));
				})
			)
			Sequence(
				Action({
					lateRun();
				})
				Terminal("DECR")
				Action({
					expr = dress(SUnaryExpr.make(UnaryOp.PostDecrement, expr));
				})
			)
			Sequence(
				Action({
					lateRun();
				})
				NonTerminal(op, AssignmentOperator)
				NonTerminal(value, Expression)
				Action({
					expr = dress(SAssignExpr.make(expr, op, value));
				})
			)
		) */
	private int matchStatementExpression_2_2_2_1_1(int lookahead) {
		int newLookahead;
		newLookahead = matchStatementExpression_2_2_2_1_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchStatementExpression_2_2_2_1_1_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchStatementExpression_2_2_2_1_1_3(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			Choice(
				Sequence(
					Action({
						lateRun();
					})
					Terminal("INCR")
					Action({
						expr = dress(SUnaryExpr.make(UnaryOp.PostIncrement, expr));
					})
				)
				Sequence(
					Action({
						lateRun();
					})
					Terminal("DECR")
					Action({
						expr = dress(SUnaryExpr.make(UnaryOp.PostDecrement, expr));
					})
				)
				Sequence(
					Action({
						lateRun();
					})
					NonTerminal(op, AssignmentOperator)
					NonTerminal(value, Expression)
					Action({
						expr = dress(SAssignExpr.make(expr, op, value));
					})
				)
			)
		) */
	private int matchStatementExpression_2_2_2_1(int lookahead) {
		lookahead = matchStatementExpression_2_2_2_1_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			Choice(
				Sequence(
					Action({
						lateRun();
					})
					Terminal("INCR")
					Action({
						expr = dress(SUnaryExpr.make(UnaryOp.PostIncrement, expr));
					})
				)
				Sequence(
					Action({
						lateRun();
					})
					Terminal("DECR")
					Action({
						expr = dress(SUnaryExpr.make(UnaryOp.PostDecrement, expr));
					})
				)
				Sequence(
					Action({
						lateRun();
					})
					NonTerminal(op, AssignmentOperator)
					NonTerminal(value, Expression)
					Action({
						expr = dress(SAssignExpr.make(expr, op, value));
					})
				)
			)
		) */
	private int matchStatementExpression_2_2_2(int lookahead) {
		int newLookahead;
		newLookahead = matchStatementExpression_2_2_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(expr, PrimaryExpression)
			ZeroOrOne(
				Choice(
					Sequence(
						Action({
							lateRun();
						})
						Terminal("INCR")
						Action({
							expr = dress(SUnaryExpr.make(UnaryOp.PostIncrement, expr));
						})
					)
					Sequence(
						Action({
							lateRun();
						})
						Terminal("DECR")
						Action({
							expr = dress(SUnaryExpr.make(UnaryOp.PostDecrement, expr));
						})
					)
					Sequence(
						Action({
							lateRun();
						})
						NonTerminal(op, AssignmentOperator)
						NonTerminal(value, Expression)
						Action({
							expr = dress(SAssignExpr.make(expr, op, value));
						})
					)
				)
			)
		) */
	private int matchStatementExpression_2_2(int lookahead) {
		lookahead = matchPrimaryExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchStatementExpression_2_2_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			NonTerminal(expr, PrefixExpression)
			Sequence(
				NonTerminal(expr, PrimaryExpression)
				ZeroOrOne(
					Choice(
						Sequence(
							Action({
								lateRun();
							})
							Terminal("INCR")
							Action({
								expr = dress(SUnaryExpr.make(UnaryOp.PostIncrement, expr));
							})
						)
						Sequence(
							Action({
								lateRun();
							})
							Terminal("DECR")
							Action({
								expr = dress(SUnaryExpr.make(UnaryOp.PostDecrement, expr));
							})
						)
						Sequence(
							Action({
								lateRun();
							})
							NonTerminal(op, AssignmentOperator)
							NonTerminal(value, Expression)
							Action({
								expr = dress(SAssignExpr.make(expr, op, value));
							})
						)
					)
				)
			)
		) */
	private int matchStatementExpression_2(int lookahead) {
		int newLookahead;
		newLookahead = matchPrefixExpression(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchStatementExpression_2_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			Action({
				run();
			})
			Choice(
				NonTerminal(expr, PrefixExpression)
				Sequence(
					NonTerminal(expr, PrimaryExpression)
					ZeroOrOne(
						Choice(
							Sequence(
								Action({
									lateRun();
								})
								Terminal("INCR")
								Action({
									expr = dress(SUnaryExpr.make(UnaryOp.PostIncrement, expr));
								})
							)
							Sequence(
								Action({
									lateRun();
								})
								Terminal("DECR")
								Action({
									expr = dress(SUnaryExpr.make(UnaryOp.PostDecrement, expr));
								})
							)
							Sequence(
								Action({
									lateRun();
								})
								NonTerminal(op, AssignmentOperator)
								NonTerminal(value, Expression)
								Action({
									expr = dress(SAssignExpr.make(expr, op, value));
								})
							)
						)
					)
				)
			)
			Terminal("SEMICOLON")
			Action({
				return dress(SExpressionStmt.make(expr));
			})
		) */
	private int matchStatementExpression(int lookahead) {
		lookahead = matchStatementExpression_2(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.SEMICOLON);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("CASE")
			NonTerminal(label, Expression)
		) */
	private int matchSwitchEntry_2_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.CASE);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				Terminal("CASE")
				NonTerminal(label, Expression)
			)
			Terminal("_DEFAULT")
		) */
	private int matchSwitchEntry_2(int lookahead) {
		int newLookahead;
		newLookahead = matchSwitchEntry_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = match(lookahead, ParserImplConstants._DEFAULT);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			Action({
				run();
			})
			Choice(
				Sequence(
					Terminal("CASE")
					NonTerminal(label, Expression)
				)
				Terminal("_DEFAULT")
			)
			Terminal("COLON")
			NonTerminal(stmts, Statements)
			Action({
				return dress(SSwitchCase.make(optionOf(label), ensureNotNull(stmts)));
			})
		) */
	private int matchSwitchEntry(int lookahead) {
		lookahead = matchSwitchEntry_2(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.COLON);
		if (lookahead == -1)
			return -1;
		lookahead = matchStatements(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(entry, SwitchEntry)
			Action({
				entries = append(entries, entry);
			})
		) */
	private int matchSwitchStatement_7_1(int lookahead) {
		lookahead = matchSwitchEntry(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrMore(
			NonTerminal(entry, SwitchEntry)
			Action({
				entries = append(entries, entry);
			})
		) */
	private int matchSwitchStatement_7(int lookahead) {
		int newLookahead;
		newLookahead = matchSwitchStatement_7_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchSwitchStatement_7_1(lookahead);
		}
		return lookahead;
	}

	/* Sequence(
			Action({
				run();
			})
			Terminal("SWITCH")
			Terminal("LPAREN")
			NonTerminal(selector, Expression)
			Terminal("RPAREN")
			Terminal("LBRACE")
			ZeroOrMore(
				NonTerminal(entry, SwitchEntry)
				Action({
					entries = append(entries, entry);
				})
			)
			Terminal("RBRACE")
			Action({
				return dress(SSwitchStmt.make(selector, entries));
			})
		) */
	private int matchSwitchStatement(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.SWITCH);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.LBRACE);
		if (lookahead == -1)
			return -1;
		lookahead = matchSwitchStatement_7(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RBRACE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(1)
			Terminal("ELSE")
			NonTerminal(elseStmt, Statement)
		) */
	private int matchIfStatement_7_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.ELSE);
		if (lookahead == -1)
			return -1;
		lookahead = matchStatement(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			LookAhead(1)
			Terminal("ELSE")
			NonTerminal(elseStmt, Statement)
		) */
	private int matchIfStatement_7(int lookahead) {
		int newLookahead;
		newLookahead = matchIfStatement_7_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			Action({
				run();
			})
			Terminal("IF")
			Terminal("LPAREN")
			NonTerminal(condition, Expression)
			Terminal("RPAREN")
			NonTerminal(thenStmt, Statement)
			ZeroOrOne(
				LookAhead(1)
				Terminal("ELSE")
				NonTerminal(elseStmt, Statement)
			)
			Action({
				return dress(SIfStmt.make(condition, thenStmt, optionOf(elseStmt)));
			})
		) */
	private int matchIfStatement(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.IF);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchStatement(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchIfStatement_7(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Action({
				run();
			})
			Terminal("WHILE")
			Terminal("LPAREN")
			NonTerminal(condition, Expression)
			Terminal("RPAREN")
			NonTerminal(body, Statement)
			Action({
				return dress(SWhileStmt.make(condition, body));
			})
		) */
	private int matchWhileStatement(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.WHILE);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchStatement(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Action({
				run();
			})
			Terminal("DO")
			NonTerminal(body, Statement)
			Terminal("WHILE")
			Terminal("LPAREN")
			NonTerminal(condition, Expression)
			Terminal("RPAREN")
			Terminal("SEMICOLON")
			Action({
				return dress(SDoStmt.make(body, condition));
			})
		) */
	private int matchDoStatement(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.DO);
		if (lookahead == -1)
			return -1;
		lookahead = matchStatement(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.WHILE);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.SEMICOLON);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(
				NonTerminal(VariableDeclExpression)
				Terminal("COLON")
			)
			NonTerminal(varExpr, VariableDeclExpression)
			Terminal("COLON")
			NonTerminal(expr, Expression)
		) */
	private int matchForStatement_4_1(int lookahead) {
		lookahead = matchVariableDeclExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.COLON);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(
				NonTerminal(Modifiers)
				NonTerminal(Type)
				NonTerminal(Name)
			)
			NonTerminal(expr, VariableDeclExpression)
			Action({
				ret = emptyList();
				ret = append(ret, expr);
			})
		) */
	private int matchForInit_1_1(int lookahead) {
		lookahead = matchVariableDeclExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("COMMA")
			NonTerminal(expr, Expression)
			Action({
				ret = append(ret, expr);
			})
		) */
	private int matchExpressionList_3_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.COMMA);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrMore(
			Terminal("COMMA")
			NonTerminal(expr, Expression)
			Action({
				ret = append(ret, expr);
			})
		) */
	private int matchExpressionList_3(int lookahead) {
		int newLookahead;
		newLookahead = matchExpressionList_3_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchExpressionList_3_1(lookahead);
		}
		return lookahead;
	}

	/* Sequence(
			NonTerminal(expr, Expression)
			Action({
				ret = append(ret, expr);
			})
			ZeroOrMore(
				Terminal("COMMA")
				NonTerminal(expr, Expression)
				Action({
					ret = append(ret, expr);
				})
			)
			Action({
				return ret;
			})
		) */
	private int matchExpressionList(int lookahead) {
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpressionList_3(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				LookAhead(
					NonTerminal(Modifiers)
					NonTerminal(Type)
					NonTerminal(Name)
				)
				NonTerminal(expr, VariableDeclExpression)
				Action({
					ret = emptyList();
					ret = append(ret, expr);
				})
			)
			NonTerminal(ret, ExpressionList)
		) */
	private int matchForInit_1(int lookahead) {
		int newLookahead;
		newLookahead = matchForInit_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchExpressionList(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			Choice(
				Sequence(
					LookAhead(
						NonTerminal(Modifiers)
						NonTerminal(Type)
						NonTerminal(Name)
					)
					NonTerminal(expr, VariableDeclExpression)
					Action({
						ret = emptyList();
						ret = append(ret, expr);
					})
				)
				NonTerminal(ret, ExpressionList)
			)
			Action({
				return ret;
			})
		) */
	private int matchForInit(int lookahead) {
		lookahead = matchForInit_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(init, ForInit)
		) */
	private int matchForStatement_4_2_1_1(int lookahead) {
		lookahead = matchForInit(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			NonTerminal(init, ForInit)
		) */
	private int matchForStatement_4_2_1(int lookahead) {
		int newLookahead;
		newLookahead = matchForStatement_4_2_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(expr, Expression)
		) */
	private int matchForStatement_4_2_3_1(int lookahead) {
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			NonTerminal(expr, Expression)
		) */
	private int matchForStatement_4_2_3(int lookahead) {
		int newLookahead;
		newLookahead = matchForStatement_4_2_3_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(ret, ExpressionList)
			Action({
				return ret;
			})
		) */
	private int matchForUpdate(int lookahead) {
		lookahead = matchExpressionList(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(update, ForUpdate)
		) */
	private int matchForStatement_4_2_5_1(int lookahead) {
		lookahead = matchForUpdate(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			NonTerminal(update, ForUpdate)
		) */
	private int matchForStatement_4_2_5(int lookahead) {
		int newLookahead;
		newLookahead = matchForStatement_4_2_5_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			ZeroOrOne(
				NonTerminal(init, ForInit)
			)
			Terminal("SEMICOLON")
			ZeroOrOne(
				NonTerminal(expr, Expression)
			)
			Terminal("SEMICOLON")
			ZeroOrOne(
				NonTerminal(update, ForUpdate)
			)
		) */
	private int matchForStatement_4_2(int lookahead) {
		lookahead = matchForStatement_4_2_1(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.SEMICOLON);
		if (lookahead == -1)
			return -1;
		lookahead = matchForStatement_4_2_3(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.SEMICOLON);
		if (lookahead == -1)
			return -1;
		lookahead = matchForStatement_4_2_5(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				LookAhead(
					NonTerminal(VariableDeclExpression)
					Terminal("COLON")
				)
				NonTerminal(varExpr, VariableDeclExpression)
				Terminal("COLON")
				NonTerminal(expr, Expression)
			)
			Sequence(
				ZeroOrOne(
					NonTerminal(init, ForInit)
				)
				Terminal("SEMICOLON")
				ZeroOrOne(
					NonTerminal(expr, Expression)
				)
				Terminal("SEMICOLON")
				ZeroOrOne(
					NonTerminal(update, ForUpdate)
				)
			)
		) */
	private int matchForStatement_4(int lookahead) {
		int newLookahead;
		newLookahead = matchForStatement_4_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchForStatement_4_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			Action({
				run();
			})
			Terminal("FOR")
			Terminal("LPAREN")
			Choice(
				Sequence(
					LookAhead(
						NonTerminal(VariableDeclExpression)
						Terminal("COLON")
					)
					NonTerminal(varExpr, VariableDeclExpression)
					Terminal("COLON")
					NonTerminal(expr, Expression)
				)
				Sequence(
					ZeroOrOne(
						NonTerminal(init, ForInit)
					)
					Terminal("SEMICOLON")
					ZeroOrOne(
						NonTerminal(expr, Expression)
					)
					Terminal("SEMICOLON")
					ZeroOrOne(
						NonTerminal(update, ForUpdate)
					)
				)
			)
			Terminal("RPAREN")
			NonTerminal(body, Statement)
			Action({
				if (varExpr != null)
		return dress(SForeachStmt.make(varExpr, expr, body));
	else
		return dress(SForStmt.make(init, expr, update, body));

			})
		) */
	private int matchForStatement(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.FOR);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchForStatement_4(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchStatement(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(id, Name)
		) */
	private int matchBreakStatement_3_1(int lookahead) {
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			NonTerminal(id, Name)
		) */
	private int matchBreakStatement_3(int lookahead) {
		int newLookahead;
		newLookahead = matchBreakStatement_3_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			Action({
				run();
			})
			Terminal("BREAK")
			ZeroOrOne(
				NonTerminal(id, Name)
			)
			Terminal("SEMICOLON")
			Action({
				return dress(SBreakStmt.make(optionOf(id)));
			})
		) */
	private int matchBreakStatement(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.BREAK);
		if (lookahead == -1)
			return -1;
		lookahead = matchBreakStatement_3(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.SEMICOLON);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(id, Name)
		) */
	private int matchContinueStatement_3_1(int lookahead) {
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			NonTerminal(id, Name)
		) */
	private int matchContinueStatement_3(int lookahead) {
		int newLookahead;
		newLookahead = matchContinueStatement_3_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			Action({
				run();
			})
			Terminal("CONTINUE")
			ZeroOrOne(
				NonTerminal(id, Name)
			)
			Terminal("SEMICOLON")
			Action({
				return dress(SContinueStmt.make(optionOf(id)));
			})
		) */
	private int matchContinueStatement(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.CONTINUE);
		if (lookahead == -1)
			return -1;
		lookahead = matchContinueStatement_3(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.SEMICOLON);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(expr, Expression)
		) */
	private int matchReturnStatement_3_1(int lookahead) {
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			NonTerminal(expr, Expression)
		) */
	private int matchReturnStatement_3(int lookahead) {
		int newLookahead;
		newLookahead = matchReturnStatement_3_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			Action({
				run();
			})
			Terminal("RETURN")
			ZeroOrOne(
				NonTerminal(expr, Expression)
			)
			Terminal("SEMICOLON")
			Action({
				return dress(SReturnStmt.make(optionOf(expr)));
			})
		) */
	private int matchReturnStatement(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.RETURN);
		if (lookahead == -1)
			return -1;
		lookahead = matchReturnStatement_3(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.SEMICOLON);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Action({
				run();
			})
			Terminal("THROW")
			NonTerminal(expr, Expression)
			Terminal("SEMICOLON")
			Action({
				return dress(SThrowStmt.make(expr));
			})
		) */
	private int matchThrowStatement(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.THROW);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.SEMICOLON);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Action({
				run();
			})
			Terminal("SYNCHRONIZED")
			Terminal("LPAREN")
			NonTerminal(expr, Expression)
			Terminal("RPAREN")
			NonTerminal(block, Block)
			Action({
				return dress(SSynchronizedStmt.make(expr, block));
			})
		) */
	private int matchSynchronizedStatement(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.SYNCHRONIZED);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchBlock(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(2)
			Terminal("SEMICOLON")
			NonTerminal(var, VariableDeclExpression)
			Action({
				vars = append(vars, var);
			})
		) */
	private int matchResourceSpecification_4_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.SEMICOLON);
		if (lookahead == -1)
			return -1;
		lookahead = matchVariableDeclExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrMore(
			LookAhead(2)
			Terminal("SEMICOLON")
			NonTerminal(var, VariableDeclExpression)
			Action({
				vars = append(vars, var);
			})
		) */
	private int matchResourceSpecification_4(int lookahead) {
		int newLookahead;
		newLookahead = matchResourceSpecification_4_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchResourceSpecification_4_1(lookahead);
		}
		return lookahead;
	}

	/* Sequence(
			LookAhead(2)
			Terminal("SEMICOLON")
			Action({
				trailingSemiColon.value = true;
			})
		) */
	private int matchResourceSpecification_5_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.SEMICOLON);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			LookAhead(2)
			Terminal("SEMICOLON")
			Action({
				trailingSemiColon.value = true;
			})
		) */
	private int matchResourceSpecification_5(int lookahead) {
		int newLookahead;
		newLookahead = matchResourceSpecification_5_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			Terminal("LPAREN")
			NonTerminal(var, VariableDeclExpression)
			Action({
				vars = append(vars, var);
			})
			ZeroOrMore(
				LookAhead(2)
				Terminal("SEMICOLON")
				NonTerminal(var, VariableDeclExpression)
				Action({
					vars = append(vars, var);
				})
			)
			ZeroOrOne(
				LookAhead(2)
				Terminal("SEMICOLON")
				Action({
					trailingSemiColon.value = true;
				})
			)
			Terminal("RPAREN")
			Action({
				return vars;
			})
		) */
	private int matchResourceSpecification(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchVariableDeclExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchResourceSpecification_4(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchResourceSpecification_5(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("BIT_OR")
			NonTerminal(exceptType, AnnotatedQualifiedType)
			Action({
				exceptTypes = append(exceptTypes, exceptType);
			})
		) */
	private int matchCatchFormalParameter_5_1_3_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.BIT_OR);
		if (lookahead == -1)
			return -1;
		lookahead = matchAnnotatedQualifiedType(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* OneOrMore(
			Terminal("BIT_OR")
			NonTerminal(exceptType, AnnotatedQualifiedType)
			Action({
				exceptTypes = append(exceptTypes, exceptType);
			})
		) */
	private int matchCatchFormalParameter_5_1_3(int lookahead) {
		int newLookahead;
		newLookahead = matchCatchFormalParameter_5_1_3_1(lookahead);
		if (newLookahead == -1)
			return -1;
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchCatchFormalParameter_5_1_3_1(lookahead);
		}
		return lookahead;
	}

	/* Sequence(
			LookAhead(
				Terminal("BIT_OR")
			)
			Action({
				lateRun();
			})
			OneOrMore(
				Terminal("BIT_OR")
				NonTerminal(exceptType, AnnotatedQualifiedType)
				Action({
					exceptTypes = append(exceptTypes, exceptType);
				})
			)
			Action({
				exceptType = dress(SUnionType.make(exceptTypes));
			})
		) */
	private int matchCatchFormalParameter_5_1(int lookahead) {
		lookahead = matchCatchFormalParameter_5_1_3(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			LookAhead(
				Terminal("BIT_OR")
			)
			Action({
				lateRun();
			})
			OneOrMore(
				Terminal("BIT_OR")
				NonTerminal(exceptType, AnnotatedQualifiedType)
				Action({
					exceptTypes = append(exceptTypes, exceptType);
				})
			)
			Action({
				exceptType = dress(SUnionType.make(exceptTypes));
			})
		) */
	private int matchCatchFormalParameter_5(int lookahead) {
		int newLookahead;
		newLookahead = matchCatchFormalParameter_5_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			Action({
				run();
			})
			NonTerminal(modifiers, Modifiers)
			NonTerminal(exceptType, QualifiedType)
			Action({
				exceptTypes = append(exceptTypes, exceptType);
			})
			ZeroOrOne(
				LookAhead(
					Terminal("BIT_OR")
				)
				Action({
					lateRun();
				})
				OneOrMore(
					Terminal("BIT_OR")
					NonTerminal(exceptType, AnnotatedQualifiedType)
					Action({
						exceptTypes = append(exceptTypes, exceptType);
					})
				)
				Action({
					exceptType = dress(SUnionType.make(exceptTypes));
				})
			)
			NonTerminal(exceptId, VariableDeclaratorId)
			Action({
				return dress(SFormalParameter.make(modifiers, exceptType, false, exceptId));
			})
		) */
	private int matchCatchFormalParameter(int lookahead) {
		lookahead = matchModifiers(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchQualifiedType(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchCatchFormalParameter_5(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchVariableDeclaratorId(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Action({
				run();
			})
			Terminal("CATCH")
			Terminal("LPAREN")
			NonTerminal(param, CatchFormalParameter)
			Terminal("RPAREN")
			NonTerminal(catchBlock, Block)
			Action({
				return dress(SCatchClause.make(param, catchBlock));
			})
		) */
	private int matchCatchClause(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.CATCH);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchCatchFormalParameter(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchBlock(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(catchClause, CatchClause)
			Action({
				catchClauses = append(catchClauses, catchClause);
			})
		) */
	private int matchCatchClauses_1_1(int lookahead) {
		lookahead = matchCatchClause(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* OneOrMore(
			NonTerminal(catchClause, CatchClause)
			Action({
				catchClauses = append(catchClauses, catchClause);
			})
		) */
	private int matchCatchClauses_1(int lookahead) {
		int newLookahead;
		newLookahead = matchCatchClauses_1_1(lookahead);
		if (newLookahead == -1)
			return -1;
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchCatchClauses_1_1(lookahead);
		}
		return lookahead;
	}

	/* Sequence(
			OneOrMore(
				NonTerminal(catchClause, CatchClause)
				Action({
					catchClauses = append(catchClauses, catchClause);
				})
			)
			Action({
				return catchClauses;
			})
		) */
	private int matchCatchClauses(int lookahead) {
		lookahead = matchCatchClauses_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(catchClauses, CatchClauses)
		) */
	private int matchTryStatement_3_1_3_1(int lookahead) {
		lookahead = matchCatchClauses(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			NonTerminal(catchClauses, CatchClauses)
		) */
	private int matchTryStatement_3_1_3(int lookahead) {
		int newLookahead;
		newLookahead = matchTryStatement_3_1_3_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			Terminal("FINALLY")
			NonTerminal(finallyBlock, Block)
		) */
	private int matchTryStatement_3_1_4_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.FINALLY);
		if (lookahead == -1)
			return -1;
		lookahead = matchBlock(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			Terminal("FINALLY")
			NonTerminal(finallyBlock, Block)
		) */
	private int matchTryStatement_3_1_4(int lookahead) {
		int newLookahead;
		newLookahead = matchTryStatement_3_1_4_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(resources, ResourceSpecification)
			NonTerminal(tryBlock, Block)
			ZeroOrOne(
				NonTerminal(catchClauses, CatchClauses)
			)
			ZeroOrOne(
				Terminal("FINALLY")
				NonTerminal(finallyBlock, Block)
			)
		) */
	private int matchTryStatement_3_1(int lookahead) {
		lookahead = matchResourceSpecification(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchBlock(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchTryStatement_3_1_3(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchTryStatement_3_1_4(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("FINALLY")
			NonTerminal(finallyBlock, Block)
		) */
	private int matchTryStatement_3_2_2_1_2_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.FINALLY);
		if (lookahead == -1)
			return -1;
		lookahead = matchBlock(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			Terminal("FINALLY")
			NonTerminal(finallyBlock, Block)
		) */
	private int matchTryStatement_3_2_2_1_2(int lookahead) {
		int newLookahead;
		newLookahead = matchTryStatement_3_2_2_1_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(catchClauses, CatchClauses)
			ZeroOrOne(
				Terminal("FINALLY")
				NonTerminal(finallyBlock, Block)
			)
		) */
	private int matchTryStatement_3_2_2_1(int lookahead) {
		lookahead = matchCatchClauses(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchTryStatement_3_2_2_1_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("FINALLY")
			NonTerminal(finallyBlock, Block)
		) */
	private int matchTryStatement_3_2_2_2(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.FINALLY);
		if (lookahead == -1)
			return -1;
		lookahead = matchBlock(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				NonTerminal(catchClauses, CatchClauses)
				ZeroOrOne(
					Terminal("FINALLY")
					NonTerminal(finallyBlock, Block)
				)
			)
			Sequence(
				Terminal("FINALLY")
				NonTerminal(finallyBlock, Block)
			)
		) */
	private int matchTryStatement_3_2_2(int lookahead) {
		int newLookahead;
		newLookahead = matchTryStatement_3_2_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchTryStatement_3_2_2_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			NonTerminal(tryBlock, Block)
			Choice(
				Sequence(
					NonTerminal(catchClauses, CatchClauses)
					ZeroOrOne(
						Terminal("FINALLY")
						NonTerminal(finallyBlock, Block)
					)
				)
				Sequence(
					Terminal("FINALLY")
					NonTerminal(finallyBlock, Block)
				)
			)
		) */
	private int matchTryStatement_3_2(int lookahead) {
		lookahead = matchBlock(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchTryStatement_3_2_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				NonTerminal(resources, ResourceSpecification)
				NonTerminal(tryBlock, Block)
				ZeroOrOne(
					NonTerminal(catchClauses, CatchClauses)
				)
				ZeroOrOne(
					Terminal("FINALLY")
					NonTerminal(finallyBlock, Block)
				)
			)
			Sequence(
				NonTerminal(tryBlock, Block)
				Choice(
					Sequence(
						NonTerminal(catchClauses, CatchClauses)
						ZeroOrOne(
							Terminal("FINALLY")
							NonTerminal(finallyBlock, Block)
						)
					)
					Sequence(
						Terminal("FINALLY")
						NonTerminal(finallyBlock, Block)
					)
				)
			)
		) */
	private int matchTryStatement_3(int lookahead) {
		int newLookahead;
		newLookahead = matchTryStatement_3_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchTryStatement_3_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			Action({
				run();
			})
			Terminal("TRY")
			Choice(
				Sequence(
					NonTerminal(resources, ResourceSpecification)
					NonTerminal(tryBlock, Block)
					ZeroOrOne(
						NonTerminal(catchClauses, CatchClauses)
					)
					ZeroOrOne(
						Terminal("FINALLY")
						NonTerminal(finallyBlock, Block)
					)
				)
				Sequence(
					NonTerminal(tryBlock, Block)
					Choice(
						Sequence(
							NonTerminal(catchClauses, CatchClauses)
							ZeroOrOne(
								Terminal("FINALLY")
								NonTerminal(finallyBlock, Block)
							)
						)
						Sequence(
							Terminal("FINALLY")
							NonTerminal(finallyBlock, Block)
						)
					)
				)
			)
			Action({
				return dress(STryStmt.make(ensureNotNull(resources), trailingSemiColon.value, tryBlock, ensureNotNull(catchClauses), optionOf(finallyBlock)));
			})
		) */
	private int matchTryStatement(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.TRY);
		if (lookahead == -1)
			return -1;
		lookahead = matchTryStatement_3(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				LookAhead(2)
				NonTerminal(ret, LabeledStatement)
			)
			NonTerminal(ret, AssertStatement)
			NonTerminal(ret, Block)
			NonTerminal(ret, EmptyStatement)
			NonTerminal(ret, StatementExpression)
			NonTerminal(ret, SwitchStatement)
			NonTerminal(ret, IfStatement)
			NonTerminal(ret, WhileStatement)
			NonTerminal(ret, DoStatement)
			NonTerminal(ret, ForStatement)
			NonTerminal(ret, BreakStatement)
			NonTerminal(ret, ContinueStatement)
			NonTerminal(ret, ReturnStatement)
			NonTerminal(ret, ThrowStatement)
			NonTerminal(ret, SynchronizedStatement)
			NonTerminal(ret, TryStatement)
		) */
	private int matchStatement_1(int lookahead) {
		int newLookahead;
		newLookahead = matchStatement_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchAssertStatement(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchBlock(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchEmptyStatement(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchStatementExpression(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchSwitchStatement(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchIfStatement(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchWhileStatement(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchDoStatement(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchForStatement(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchBreakStatement(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchContinueStatement(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchReturnStatement(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchThrowStatement(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchSynchronizedStatement(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchTryStatement(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			Choice(
				Sequence(
					LookAhead(2)
					NonTerminal(ret, LabeledStatement)
				)
				NonTerminal(ret, AssertStatement)
				NonTerminal(ret, Block)
				NonTerminal(ret, EmptyStatement)
				NonTerminal(ret, StatementExpression)
				NonTerminal(ret, SwitchStatement)
				NonTerminal(ret, IfStatement)
				NonTerminal(ret, WhileStatement)
				NonTerminal(ret, DoStatement)
				NonTerminal(ret, ForStatement)
				NonTerminal(ret, BreakStatement)
				NonTerminal(ret, ContinueStatement)
				NonTerminal(ret, ReturnStatement)
				NonTerminal(ret, ThrowStatement)
				NonTerminal(ret, SynchronizedStatement)
				NonTerminal(ret, TryStatement)
			)
			Action({
				return ret;
			})
		) */
	private int matchStatement(int lookahead) {
		lookahead = matchStatement_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				LookAhead(
					NonTerminal(ModifiersNoDefault)
					Choice(
						Terminal("CLASS")
						Terminal("INTERFACE")
					)
				)
				Action({
					run();
				})
				Action({
					run();
				})
				NonTerminal(modifiers, ModifiersNoDefault)
				NonTerminal(typeDecl, ClassOrInterfaceDecl)
				Action({
					ret = dress(STypeDeclarationStmt.make(typeDecl));
				})
			)
			Sequence(
				LookAhead(
					NonTerminal(VariableDeclExpression)
				)
				Action({
					run();
				})
				NonTerminal(expr, VariableDeclExpression)
				Terminal("SEMICOLON")
				Action({
					ret = dress(SExpressionStmt.make(expr));
				})
			)
			NonTerminal(ret, Statement)
		) */
	private int matchBlockStatement_1(int lookahead) {
		int newLookahead;
		newLookahead = matchBlockStatement_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchBlockStatement_1_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchStatement(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			Choice(
				Sequence(
					LookAhead(
						NonTerminal(ModifiersNoDefault)
						Choice(
							Terminal("CLASS")
							Terminal("INTERFACE")
						)
					)
					Action({
						run();
					})
					Action({
						run();
					})
					NonTerminal(modifiers, ModifiersNoDefault)
					NonTerminal(typeDecl, ClassOrInterfaceDecl)
					Action({
						ret = dress(STypeDeclarationStmt.make(typeDecl));
					})
				)
				Sequence(
					LookAhead(
						NonTerminal(VariableDeclExpression)
					)
					Action({
						run();
					})
					NonTerminal(expr, VariableDeclExpression)
					Terminal("SEMICOLON")
					Action({
						ret = dress(SExpressionStmt.make(expr));
					})
				)
				NonTerminal(ret, Statement)
			)
			Action({
				return ret;
			})
		) */
	private int matchBlockStatement(int lookahead) {
		lookahead = matchBlockStatement_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(stmt, BlockStatement)
			Action({
				ret = append(ret, stmt);
			})
		) */
	private int matchStatements_1_1_2_1_1(int lookahead) {
		lookahead = matchBlockStatement(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* OneOrMore(
			NonTerminal(stmt, BlockStatement)
			Action({
				ret = append(ret, stmt);
			})
		) */
	private int matchStatements_1_1_2_1(int lookahead) {
		int newLookahead;
		newLookahead = matchStatements_1_1_2_1_1(lookahead);
		if (newLookahead == -1)
			return -1;
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchStatements_1_1_2_1_1(lookahead);
		}
		return lookahead;
	}

	/* Sequence(
			LookAhead(			quotesMode
	)
			NonTerminal(ret, NodeListVar)
		) */
	private int matchStatements_1_1_2_2(int lookahead) {
		lookahead = quotesMode ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = matchNodeListVar(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			OneOrMore(
				NonTerminal(stmt, BlockStatement)
				Action({
					ret = append(ret, stmt);
				})
			)
			Sequence(
				LookAhead(				quotesMode
	)
				NonTerminal(ret, NodeListVar)
			)
		) */
	private int matchStatements_1_1_2(int lookahead) {
		int newLookahead;
		newLookahead = matchStatements_1_1_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchStatements_1_1_2_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			LookAhead(2)
			Choice(
				OneOrMore(
					NonTerminal(stmt, BlockStatement)
					Action({
						ret = append(ret, stmt);
					})
				)
				Sequence(
					LookAhead(					quotesMode
	)
					NonTerminal(ret, NodeListVar)
				)
			)
		) */
	private int matchStatements_1_1(int lookahead) {
		lookahead = matchStatements_1_1_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			LookAhead(2)
			Choice(
				OneOrMore(
					NonTerminal(stmt, BlockStatement)
					Action({
						ret = append(ret, stmt);
					})
				)
				Sequence(
					LookAhead(					quotesMode
	)
					NonTerminal(ret, NodeListVar)
				)
			)
		) */
	private int matchStatements_1(int lookahead) {
		int newLookahead;
		newLookahead = matchStatements_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			ZeroOrOne(
				LookAhead(2)
				Choice(
					OneOrMore(
						NonTerminal(stmt, BlockStatement)
						Action({
							ret = append(ret, stmt);
						})
					)
					Sequence(
						LookAhead(						quotesMode
	)
						NonTerminal(ret, NodeListVar)
					)
				)
			)
			Action({
				return ensureNotNull(ret);
			})
		) */
	private int matchStatements(int lookahead) {
		lookahead = matchStatements_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Action({
				run();
			})
			Terminal("LBRACE")
			NonTerminal(stmts, Statements)
			Terminal("RBRACE")
			Action({
				return dress(SBlockStmt.make(ensureNotNull(stmts)));
			})
		) */
	private int matchBlock(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.LBRACE);
		if (lookahead == -1)
			return -1;
		lookahead = matchStatements(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RBRACE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(block, Block)
			Action({
				ret = dress(SLambdaExpr.make(parameters, parenthesis, right(block)));
			})
		) */
	private int matchLambdaBody_1_2(int lookahead) {
		lookahead = matchBlock(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				NonTerminal(expr, Expression)
				Action({
					ret = dress(SLambdaExpr.make(parameters, parenthesis, left(expr)));
				})
			)
			Sequence(
				NonTerminal(block, Block)
				Action({
					ret = dress(SLambdaExpr.make(parameters, parenthesis, right(block)));
				})
			)
		) */
	private int matchLambdaBody_1(int lookahead) {
		int newLookahead;
		newLookahead = matchLambdaBody_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchLambdaBody_1_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			Choice(
				Sequence(
					NonTerminal(expr, Expression)
					Action({
						ret = dress(SLambdaExpr.make(parameters, parenthesis, left(expr)));
					})
				)
				Sequence(
					NonTerminal(block, Block)
					Action({
						ret = dress(SLambdaExpr.make(parameters, parenthesis, right(block)));
					})
				)
			)
			Action({
				return ret;
			})
		) */
	private int matchLambdaBody(int lookahead) {
		lookahead = matchLambdaBody_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(
				NonTerminal(Name)
				Terminal("ARROW")
			)
			Action({
				run();
			})
			NonTerminal(ret, Name)
			Terminal("ARROW")
			NonTerminal(ret, LambdaBody)
		) */
	private int matchExpression_1_1(int lookahead) {
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.ARROW);
		if (lookahead == -1)
			return -1;
		lookahead = matchLambdaBody(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(
				Terminal("LPAREN")
				Terminal("RPAREN")
				Terminal("ARROW")
			)
			Action({
				run();
			})
			Terminal("LPAREN")
			Terminal("RPAREN")
			Terminal("ARROW")
			NonTerminal(ret, LambdaBody)
		) */
	private int matchExpression_1_2(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.ARROW);
		if (lookahead == -1)
			return -1;
		lookahead = matchLambdaBody(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(
				Terminal("LPAREN")
				NonTerminal(Name)
				Terminal("RPAREN")
				Terminal("ARROW")
			)
			Action({
				run();
			})
			Terminal("LPAREN")
			NonTerminal(ret, Name)
			Terminal("RPAREN")
			Terminal("ARROW")
			NonTerminal(ret, LambdaBody)
		) */
	private int matchExpression_1_3(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.ARROW);
		if (lookahead == -1)
			return -1;
		lookahead = matchLambdaBody(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(name, Name)
			Action({
				return makeFormalParameter(name);
			})
		) */
	private int matchInferredFormalParameter(int lookahead) {
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("COMMA")
			NonTerminal(param, InferredFormalParameter)
			Action({
				ret = append(ret, param);
			})
		) */
	private int matchInferredFormalParameterList_3_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.COMMA);
		if (lookahead == -1)
			return -1;
		lookahead = matchInferredFormalParameter(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrMore(
			Terminal("COMMA")
			NonTerminal(param, InferredFormalParameter)
			Action({
				ret = append(ret, param);
			})
		) */
	private int matchInferredFormalParameterList_3(int lookahead) {
		int newLookahead;
		newLookahead = matchInferredFormalParameterList_3_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchInferredFormalParameterList_3_1(lookahead);
		}
		return lookahead;
	}

	/* Sequence(
			NonTerminal(param, InferredFormalParameter)
			Action({
				ret = append(ret, param);
			})
			ZeroOrMore(
				Terminal("COMMA")
				NonTerminal(param, InferredFormalParameter)
				Action({
					ret = append(ret, param);
				})
			)
			Action({
				return ret;
			})
		) */
	private int matchInferredFormalParameterList(int lookahead) {
		lookahead = matchInferredFormalParameter(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchInferredFormalParameterList_3(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(
				Terminal("LPAREN")
				NonTerminal(Name)
				Terminal("COMMA")
			)
			Action({
				run();
			})
			Terminal("LPAREN")
			NonTerminal(params, InferredFormalParameterList)
			Terminal("RPAREN")
			Terminal("ARROW")
			NonTerminal(ret, LambdaBody)
		) */
	private int matchExpression_1_4(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchInferredFormalParameterList(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.ARROW);
		if (lookahead == -1)
			return -1;
		lookahead = matchLambdaBody(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(2)
			Action({
				lateRun();
			})
			NonTerminal(op, AssignmentOperator)
			NonTerminal(value, Expression)
			Action({
				ret = dress(SAssignExpr.make(ret, op, value));
			})
		) */
	private int matchExpression_1_5_2_1(int lookahead) {
		lookahead = matchAssignmentOperator(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			LookAhead(2)
			Action({
				lateRun();
			})
			NonTerminal(op, AssignmentOperator)
			NonTerminal(value, Expression)
			Action({
				ret = dress(SAssignExpr.make(ret, op, value));
			})
		) */
	private int matchExpression_1_5_2(int lookahead) {
		int newLookahead;
		newLookahead = matchExpression_1_5_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(ret, ConditionalExpression)
			ZeroOrOne(
				LookAhead(2)
				Action({
					lateRun();
				})
				NonTerminal(op, AssignmentOperator)
				NonTerminal(value, Expression)
				Action({
					ret = dress(SAssignExpr.make(ret, op, value));
				})
			)
		) */
	private int matchExpression_1_5(int lookahead) {
		lookahead = matchConditionalExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpression_1_5_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				LookAhead(
					NonTerminal(Name)
					Terminal("ARROW")
				)
				Action({
					run();
				})
				NonTerminal(ret, Name)
				Terminal("ARROW")
				NonTerminal(ret, LambdaBody)
			)
			Sequence(
				LookAhead(
					Terminal("LPAREN")
					Terminal("RPAREN")
					Terminal("ARROW")
				)
				Action({
					run();
				})
				Terminal("LPAREN")
				Terminal("RPAREN")
				Terminal("ARROW")
				NonTerminal(ret, LambdaBody)
			)
			Sequence(
				LookAhead(
					Terminal("LPAREN")
					NonTerminal(Name)
					Terminal("RPAREN")
					Terminal("ARROW")
				)
				Action({
					run();
				})
				Terminal("LPAREN")
				NonTerminal(ret, Name)
				Terminal("RPAREN")
				Terminal("ARROW")
				NonTerminal(ret, LambdaBody)
			)
			Sequence(
				LookAhead(
					Terminal("LPAREN")
					NonTerminal(Name)
					Terminal("COMMA")
				)
				Action({
					run();
				})
				Terminal("LPAREN")
				NonTerminal(params, InferredFormalParameterList)
				Terminal("RPAREN")
				Terminal("ARROW")
				NonTerminal(ret, LambdaBody)
			)
			Sequence(
				NonTerminal(ret, ConditionalExpression)
				ZeroOrOne(
					LookAhead(2)
					Action({
						lateRun();
					})
					NonTerminal(op, AssignmentOperator)
					NonTerminal(value, Expression)
					Action({
						ret = dress(SAssignExpr.make(ret, op, value));
					})
				)
			)
		) */
	private int matchExpression_1(int lookahead) {
		int newLookahead;
		newLookahead = matchExpression_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchExpression_1_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchExpression_1_3(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchExpression_1_4(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchExpression_1_5(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			Choice(
				Sequence(
					LookAhead(
						NonTerminal(Name)
						Terminal("ARROW")
					)
					Action({
						run();
					})
					NonTerminal(ret, Name)
					Terminal("ARROW")
					NonTerminal(ret, LambdaBody)
				)
				Sequence(
					LookAhead(
						Terminal("LPAREN")
						Terminal("RPAREN")
						Terminal("ARROW")
					)
					Action({
						run();
					})
					Terminal("LPAREN")
					Terminal("RPAREN")
					Terminal("ARROW")
					NonTerminal(ret, LambdaBody)
				)
				Sequence(
					LookAhead(
						Terminal("LPAREN")
						NonTerminal(Name)
						Terminal("RPAREN")
						Terminal("ARROW")
					)
					Action({
						run();
					})
					Terminal("LPAREN")
					NonTerminal(ret, Name)
					Terminal("RPAREN")
					Terminal("ARROW")
					NonTerminal(ret, LambdaBody)
				)
				Sequence(
					LookAhead(
						Terminal("LPAREN")
						NonTerminal(Name)
						Terminal("COMMA")
					)
					Action({
						run();
					})
					Terminal("LPAREN")
					NonTerminal(params, InferredFormalParameterList)
					Terminal("RPAREN")
					Terminal("ARROW")
					NonTerminal(ret, LambdaBody)
				)
				Sequence(
					NonTerminal(ret, ConditionalExpression)
					ZeroOrOne(
						LookAhead(2)
						Action({
							lateRun();
						})
						NonTerminal(op, AssignmentOperator)
						NonTerminal(value, Expression)
						Action({
							ret = dress(SAssignExpr.make(ret, op, value));
						})
					)
				)
			)
			Action({
				return ret;
			})
		) */
	private int matchExpression(int lookahead) {
		lookahead = matchExpression_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("COMMA")
			NonTerminal(expr, Expression)
			Action({
				ret = append(ret, expr);
			})
		) */
	private int matchArguments_2_1_1_1_4_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.COMMA);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrMore(
			Terminal("COMMA")
			NonTerminal(expr, Expression)
			Action({
				ret = append(ret, expr);
			})
		) */
	private int matchArguments_2_1_1_1_4(int lookahead) {
		int newLookahead;
		newLookahead = matchArguments_2_1_1_1_4_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchArguments_2_1_1_1_4_1(lookahead);
		}
		return lookahead;
	}

	/* Sequence(
			LookAhead(1)
			NonTerminal(expr, Expression)
			Action({
				ret = append(ret, expr);
			})
			ZeroOrMore(
				Terminal("COMMA")
				NonTerminal(expr, Expression)
				Action({
					ret = append(ret, expr);
				})
			)
		) */
	private int matchArguments_2_1_1_1(int lookahead) {
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchArguments_2_1_1_1_4(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(			quotesMode
	)
			NonTerminal(ret, NodeListVar)
		) */
	private int matchArguments_2_1_1_2(int lookahead) {
		lookahead = quotesMode ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = matchNodeListVar(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				LookAhead(1)
				NonTerminal(expr, Expression)
				Action({
					ret = append(ret, expr);
				})
				ZeroOrMore(
					Terminal("COMMA")
					NonTerminal(expr, Expression)
					Action({
						ret = append(ret, expr);
					})
				)
			)
			Sequence(
				LookAhead(				quotesMode
	)
				NonTerminal(ret, NodeListVar)
			)
		) */
	private int matchArguments_2_1_1(int lookahead) {
		int newLookahead;
		newLookahead = matchArguments_2_1_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchArguments_2_1_1_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			Choice(
				Sequence(
					LookAhead(1)
					NonTerminal(expr, Expression)
					Action({
						ret = append(ret, expr);
					})
					ZeroOrMore(
						Terminal("COMMA")
						NonTerminal(expr, Expression)
						Action({
							ret = append(ret, expr);
						})
					)
				)
				Sequence(
					LookAhead(					quotesMode
	)
					NonTerminal(ret, NodeListVar)
				)
			)
		) */
	private int matchArguments_2_1(int lookahead) {
		lookahead = matchArguments_2_1_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			Choice(
				Sequence(
					LookAhead(1)
					NonTerminal(expr, Expression)
					Action({
						ret = append(ret, expr);
					})
					ZeroOrMore(
						Terminal("COMMA")
						NonTerminal(expr, Expression)
						Action({
							ret = append(ret, expr);
						})
					)
				)
				Sequence(
					LookAhead(					quotesMode
	)
					NonTerminal(ret, NodeListVar)
				)
			)
		) */
	private int matchArguments_2(int lookahead) {
		int newLookahead;
		newLookahead = matchArguments_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			Terminal("LPAREN")
			ZeroOrOne(
				Choice(
					Sequence(
						LookAhead(1)
						NonTerminal(expr, Expression)
						Action({
							ret = append(ret, expr);
						})
						ZeroOrMore(
							Terminal("COMMA")
							NonTerminal(expr, Expression)
							Action({
								ret = append(ret, expr);
							})
						)
					)
					Sequence(
						LookAhead(						quotesMode
	)
						NonTerminal(ret, NodeListVar)
					)
				)
			)
			Terminal("RPAREN")
			Action({
				return ret;
			})
		) */
	private int matchArguments(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchArguments_2(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			ZeroOrOne(
				NonTerminal(typeArgs, TypeArguments)
			)
			NonTerminal(name, Name)
			NonTerminal(args, Arguments)
			Action({
				return dress(SMethodInvocationExpr.make(optionOf(scope), ensureNotNull(typeArgs), name, args));
			})
		) */
	private int matchMethodInvocation(int lookahead) {
		lookahead = matchMethodInvocation_1(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchArguments(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(
				ZeroOrOne(
					NonTerminal(TypeArguments)
				)
				NonTerminal(Name)
				Terminal("LPAREN")
			)
			NonTerminal(ret, MethodInvocation)
		) */
	private int matchPrimaryPrefix_1_3_4_1_3_1(int lookahead) {
		lookahead = matchMethodInvocation(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				LookAhead(
					ZeroOrOne(
						NonTerminal(TypeArguments)
					)
					NonTerminal(Name)
					Terminal("LPAREN")
				)
				NonTerminal(ret, MethodInvocation)
			)
			NonTerminal(ret, FieldAccess)
		) */
	private int matchPrimaryPrefix_1_3_4_1_3(int lookahead) {
		int newLookahead;
		newLookahead = matchPrimaryPrefix_1_3_4_1_3_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchFieldAccess(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			Action({
				lateRun();
			})
			Terminal("DOT")
			Choice(
				Sequence(
					LookAhead(
						ZeroOrOne(
							NonTerminal(TypeArguments)
						)
						NonTerminal(Name)
						Terminal("LPAREN")
					)
					NonTerminal(ret, MethodInvocation)
				)
				NonTerminal(ret, FieldAccess)
			)
		) */
	private int matchPrimaryPrefix_1_3_4_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.DOT);
		if (lookahead == -1)
			return -1;
		lookahead = matchPrimaryPrefix_1_3_4_1_3(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(typeArgs, TypeArguments)
		) */
	private int matchMethodReferenceSuffix_2_1(int lookahead) {
		lookahead = matchTypeArguments(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			NonTerminal(typeArgs, TypeArguments)
		) */
	private int matchMethodReferenceSuffix_2(int lookahead) {
		int newLookahead;
		newLookahead = matchMethodReferenceSuffix_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			Terminal("NEW")
			Action({
				name = SName.make("new");
			})
		) */
	private int matchMethodReferenceSuffix_3_2(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.NEW);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			NonTerminal(name, Name)
			Sequence(
				Terminal("NEW")
				Action({
					name = SName.make("new");
				})
			)
		) */
	private int matchMethodReferenceSuffix_3(int lookahead) {
		int newLookahead;
		newLookahead = matchName(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchMethodReferenceSuffix_3_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			Terminal("DOUBLECOLON")
			ZeroOrOne(
				NonTerminal(typeArgs, TypeArguments)
			)
			Choice(
				NonTerminal(name, Name)
				Sequence(
					Terminal("NEW")
					Action({
						name = SName.make("new");
					})
				)
			)
			Action({
				ret = dress(SMethodReferenceExpr.make(scope, ensureNotNull(typeArgs), name));
			})
			Action({
				return ret;
			})
		) */
	private int matchMethodReferenceSuffix(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.DOUBLECOLON);
		if (lookahead == -1)
			return -1;
		lookahead = matchMethodReferenceSuffix_2(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchMethodReferenceSuffix_3(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Action({
				lateRun();
			})
			NonTerminal(ret, MethodReferenceSuffix)
		) */
	private int matchPrimaryPrefix_1_3_4_2(int lookahead) {
		lookahead = matchMethodReferenceSuffix(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				Action({
					lateRun();
				})
				Terminal("DOT")
				Choice(
					Sequence(
						LookAhead(
							ZeroOrOne(
								NonTerminal(TypeArguments)
							)
							NonTerminal(Name)
							Terminal("LPAREN")
						)
						NonTerminal(ret, MethodInvocation)
					)
					NonTerminal(ret, FieldAccess)
				)
			)
			Sequence(
				Action({
					lateRun();
				})
				NonTerminal(ret, MethodReferenceSuffix)
			)
		) */
	private int matchPrimaryPrefix_1_3_4(int lookahead) {
		int newLookahead;
		newLookahead = matchPrimaryPrefix_1_3_4_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchPrimaryPrefix_1_3_4_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			Action({
				run();
			})
			Terminal("SUPER")
			Action({
				ret = dress(SSuperExpr.make(none()));
			})
			Choice(
				Sequence(
					Action({
						lateRun();
					})
					Terminal("DOT")
					Choice(
						Sequence(
							LookAhead(
								ZeroOrOne(
									NonTerminal(TypeArguments)
								)
								NonTerminal(Name)
								Terminal("LPAREN")
							)
							NonTerminal(ret, MethodInvocation)
						)
						NonTerminal(ret, FieldAccess)
					)
				)
				Sequence(
					Action({
						lateRun();
					})
					NonTerminal(ret, MethodReferenceSuffix)
				)
			)
		) */
	private int matchPrimaryPrefix_1_3(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.SUPER);
		if (lookahead == -1)
			return -1;
		lookahead = matchPrimaryPrefix_1_3_4(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(
				NonTerminal(ResultType)
				Terminal("DOT")
				Terminal("CLASS")
			)
			Action({
				run();
			})
			NonTerminal(type, ResultType)
			Terminal("DOT")
			Terminal("CLASS")
			Action({
				ret = dress(SClassExpr.make(type));
			})
		) */
	private int matchPrimaryPrefix_1_5(int lookahead) {
		lookahead = matchResultType(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.DOT);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.CLASS);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(
				NonTerminal(ResultType)
				Terminal("DOUBLECOLON")
			)
			Action({
				run();
			})
			NonTerminal(type, ResultType)
			Action({
				ret = STypeExpr.make(type);
			})
			NonTerminal(ret, MethodReferenceSuffix)
		) */
	private int matchPrimaryPrefix_1_6(int lookahead) {
		lookahead = matchResultType(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchMethodReferenceSuffix(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(
				ZeroOrOne(
					NonTerminal(TypeArguments)
				)
				NonTerminal(Name)
				Terminal("LPAREN")
			)
			Action({
				run();
			})
			NonTerminal(ret, MethodInvocation)
		) */
	private int matchPrimaryPrefix_1_7(int lookahead) {
		lookahead = matchMethodInvocation(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Action({
				lateRun();
			})
			Terminal("ARROW")
			NonTerminal(ret, LambdaBody)
		) */
	private int matchPrimaryPrefix_1_8_2_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.ARROW);
		if (lookahead == -1)
			return -1;
		lookahead = matchLambdaBody(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			Action({
				lateRun();
			})
			Terminal("ARROW")
			NonTerminal(ret, LambdaBody)
		) */
	private int matchPrimaryPrefix_1_8_2(int lookahead) {
		int newLookahead;
		newLookahead = matchPrimaryPrefix_1_8_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(ret, Name)
			ZeroOrOne(
				Action({
					lateRun();
				})
				Terminal("ARROW")
				NonTerminal(ret, LambdaBody)
			)
		) */
	private int matchPrimaryPrefix_1_8(int lookahead) {
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchPrimaryPrefix_1_8_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("RPAREN")
			Terminal("ARROW")
			NonTerminal(ret, LambdaBody)
		) */
	private int matchPrimaryPrefix_1_9_3_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.ARROW);
		if (lookahead == -1)
			return -1;
		lookahead = matchLambdaBody(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(
				NonTerminal(Name)
				Terminal("RPAREN")
				Terminal("ARROW")
			)
			NonTerminal(ret, Name)
			Terminal("RPAREN")
			Terminal("ARROW")
			NonTerminal(ret, LambdaBody)
		) */
	private int matchPrimaryPrefix_1_9_3_2(int lookahead) {
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.ARROW);
		if (lookahead == -1)
			return -1;
		lookahead = matchLambdaBody(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(
				NonTerminal(Name)
				Terminal("COMMA")
			)
			NonTerminal(params, InferredFormalParameterList)
			Terminal("RPAREN")
			Terminal("ARROW")
			NonTerminal(ret, LambdaBody)
		) */
	private int matchPrimaryPrefix_1_9_3_3(int lookahead) {
		lookahead = matchInferredFormalParameterList(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.ARROW);
		if (lookahead == -1)
			return -1;
		lookahead = matchLambdaBody(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(			isLambda()
	)
			NonTerminal(params, FormalParameterList)
			Terminal("RPAREN")
			Terminal("ARROW")
			NonTerminal(ret, LambdaBody)
		) */
	private int matchPrimaryPrefix_1_9_3_4(int lookahead) {
		lookahead = isLambda(lookahead) ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = matchFormalParameterList(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.ARROW);
		if (lookahead == -1)
			return -1;
		lookahead = matchLambdaBody(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(ret, Expression)
			Terminal("RPAREN")
			Action({
				ret = dress(SParenthesizedExpr.make(ret));
			})
		) */
	private int matchPrimaryPrefix_1_9_3_5(int lookahead) {
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				Terminal("RPAREN")
				Terminal("ARROW")
				NonTerminal(ret, LambdaBody)
			)
			Sequence(
				LookAhead(
					NonTerminal(Name)
					Terminal("RPAREN")
					Terminal("ARROW")
				)
				NonTerminal(ret, Name)
				Terminal("RPAREN")
				Terminal("ARROW")
				NonTerminal(ret, LambdaBody)
			)
			Sequence(
				LookAhead(
					NonTerminal(Name)
					Terminal("COMMA")
				)
				NonTerminal(params, InferredFormalParameterList)
				Terminal("RPAREN")
				Terminal("ARROW")
				NonTerminal(ret, LambdaBody)
			)
			Sequence(
				LookAhead(				isLambda()
	)
				NonTerminal(params, FormalParameterList)
				Terminal("RPAREN")
				Terminal("ARROW")
				NonTerminal(ret, LambdaBody)
			)
			Sequence(
				NonTerminal(ret, Expression)
				Terminal("RPAREN")
				Action({
					ret = dress(SParenthesizedExpr.make(ret));
				})
			)
		) */
	private int matchPrimaryPrefix_1_9_3(int lookahead) {
		int newLookahead;
		newLookahead = matchPrimaryPrefix_1_9_3_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchPrimaryPrefix_1_9_3_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchPrimaryPrefix_1_9_3_3(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchPrimaryPrefix_1_9_3_4(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchPrimaryPrefix_1_9_3_5(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			Action({
				run();
			})
			Terminal("LPAREN")
			Choice(
				Sequence(
					Terminal("RPAREN")
					Terminal("ARROW")
					NonTerminal(ret, LambdaBody)
				)
				Sequence(
					LookAhead(
						NonTerminal(Name)
						Terminal("RPAREN")
						Terminal("ARROW")
					)
					NonTerminal(ret, Name)
					Terminal("RPAREN")
					Terminal("ARROW")
					NonTerminal(ret, LambdaBody)
				)
				Sequence(
					LookAhead(
						NonTerminal(Name)
						Terminal("COMMA")
					)
					NonTerminal(params, InferredFormalParameterList)
					Terminal("RPAREN")
					Terminal("ARROW")
					NonTerminal(ret, LambdaBody)
				)
				Sequence(
					LookAhead(					isLambda()
	)
					NonTerminal(params, FormalParameterList)
					Terminal("RPAREN")
					Terminal("ARROW")
					NonTerminal(ret, LambdaBody)
				)
				Sequence(
					NonTerminal(ret, Expression)
					Terminal("RPAREN")
					Action({
						ret = dress(SParenthesizedExpr.make(ret));
					})
				)
			)
		) */
	private int matchPrimaryPrefix_1_9(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchPrimaryPrefix_1_9_3(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			NonTerminal(ret, Literal)
			Sequence(
				Action({
					run();
				})
				Terminal("THIS")
				Action({
					ret = dress(SThisExpr.make(none()));
				})
			)
			Sequence(
				Action({
					run();
				})
				Terminal("SUPER")
				Action({
					ret = dress(SSuperExpr.make(none()));
				})
				Choice(
					Sequence(
						Action({
							lateRun();
						})
						Terminal("DOT")
						Choice(
							Sequence(
								LookAhead(
									ZeroOrOne(
										NonTerminal(TypeArguments)
									)
									NonTerminal(Name)
									Terminal("LPAREN")
								)
								NonTerminal(ret, MethodInvocation)
							)
							NonTerminal(ret, FieldAccess)
						)
					)
					Sequence(
						Action({
							lateRun();
						})
						NonTerminal(ret, MethodReferenceSuffix)
					)
				)
			)
			NonTerminal(ret, AllocationExpression)
			Sequence(
				LookAhead(
					NonTerminal(ResultType)
					Terminal("DOT")
					Terminal("CLASS")
				)
				Action({
					run();
				})
				NonTerminal(type, ResultType)
				Terminal("DOT")
				Terminal("CLASS")
				Action({
					ret = dress(SClassExpr.make(type));
				})
			)
			Sequence(
				LookAhead(
					NonTerminal(ResultType)
					Terminal("DOUBLECOLON")
				)
				Action({
					run();
				})
				NonTerminal(type, ResultType)
				Action({
					ret = STypeExpr.make(type);
				})
				NonTerminal(ret, MethodReferenceSuffix)
			)
			Sequence(
				LookAhead(
					ZeroOrOne(
						NonTerminal(TypeArguments)
					)
					NonTerminal(Name)
					Terminal("LPAREN")
				)
				Action({
					run();
				})
				NonTerminal(ret, MethodInvocation)
			)
			Sequence(
				NonTerminal(ret, Name)
				ZeroOrOne(
					Action({
						lateRun();
					})
					Terminal("ARROW")
					NonTerminal(ret, LambdaBody)
				)
			)
			Sequence(
				Action({
					run();
				})
				Terminal("LPAREN")
				Choice(
					Sequence(
						Terminal("RPAREN")
						Terminal("ARROW")
						NonTerminal(ret, LambdaBody)
					)
					Sequence(
						LookAhead(
							NonTerminal(Name)
							Terminal("RPAREN")
							Terminal("ARROW")
						)
						NonTerminal(ret, Name)
						Terminal("RPAREN")
						Terminal("ARROW")
						NonTerminal(ret, LambdaBody)
					)
					Sequence(
						LookAhead(
							NonTerminal(Name)
							Terminal("COMMA")
						)
						NonTerminal(params, InferredFormalParameterList)
						Terminal("RPAREN")
						Terminal("ARROW")
						NonTerminal(ret, LambdaBody)
					)
					Sequence(
						LookAhead(						isLambda()
	)
						NonTerminal(params, FormalParameterList)
						Terminal("RPAREN")
						Terminal("ARROW")
						NonTerminal(ret, LambdaBody)
					)
					Sequence(
						NonTerminal(ret, Expression)
						Terminal("RPAREN")
						Action({
							ret = dress(SParenthesizedExpr.make(ret));
						})
					)
				)
			)
		) */
	private int matchPrimaryPrefix_1(int lookahead) {
		int newLookahead;
		newLookahead = matchLiteral(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchPrimaryPrefix_1_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchPrimaryPrefix_1_3(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchAllocationExpression(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchPrimaryPrefix_1_5(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchPrimaryPrefix_1_6(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchPrimaryPrefix_1_7(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchPrimaryPrefix_1_8(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchPrimaryPrefix_1_9(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			Choice(
				NonTerminal(ret, Literal)
				Sequence(
					Action({
						run();
					})
					Terminal("THIS")
					Action({
						ret = dress(SThisExpr.make(none()));
					})
				)
				Sequence(
					Action({
						run();
					})
					Terminal("SUPER")
					Action({
						ret = dress(SSuperExpr.make(none()));
					})
					Choice(
						Sequence(
							Action({
								lateRun();
							})
							Terminal("DOT")
							Choice(
								Sequence(
									LookAhead(
										ZeroOrOne(
											NonTerminal(TypeArguments)
										)
										NonTerminal(Name)
										Terminal("LPAREN")
									)
									NonTerminal(ret, MethodInvocation)
								)
								NonTerminal(ret, FieldAccess)
							)
						)
						Sequence(
							Action({
								lateRun();
							})
							NonTerminal(ret, MethodReferenceSuffix)
						)
					)
				)
				NonTerminal(ret, AllocationExpression)
				Sequence(
					LookAhead(
						NonTerminal(ResultType)
						Terminal("DOT")
						Terminal("CLASS")
					)
					Action({
						run();
					})
					NonTerminal(type, ResultType)
					Terminal("DOT")
					Terminal("CLASS")
					Action({
						ret = dress(SClassExpr.make(type));
					})
				)
				Sequence(
					LookAhead(
						NonTerminal(ResultType)
						Terminal("DOUBLECOLON")
					)
					Action({
						run();
					})
					NonTerminal(type, ResultType)
					Action({
						ret = STypeExpr.make(type);
					})
					NonTerminal(ret, MethodReferenceSuffix)
				)
				Sequence(
					LookAhead(
						ZeroOrOne(
							NonTerminal(TypeArguments)
						)
						NonTerminal(Name)
						Terminal("LPAREN")
					)
					Action({
						run();
					})
					NonTerminal(ret, MethodInvocation)
				)
				Sequence(
					NonTerminal(ret, Name)
					ZeroOrOne(
						Action({
							lateRun();
						})
						Terminal("ARROW")
						NonTerminal(ret, LambdaBody)
					)
				)
				Sequence(
					Action({
						run();
					})
					Terminal("LPAREN")
					Choice(
						Sequence(
							Terminal("RPAREN")
							Terminal("ARROW")
							NonTerminal(ret, LambdaBody)
						)
						Sequence(
							LookAhead(
								NonTerminal(Name)
								Terminal("RPAREN")
								Terminal("ARROW")
							)
							NonTerminal(ret, Name)
							Terminal("RPAREN")
							Terminal("ARROW")
							NonTerminal(ret, LambdaBody)
						)
						Sequence(
							LookAhead(
								NonTerminal(Name)
								Terminal("COMMA")
							)
							NonTerminal(params, InferredFormalParameterList)
							Terminal("RPAREN")
							Terminal("ARROW")
							NonTerminal(ret, LambdaBody)
						)
						Sequence(
							LookAhead(							isLambda()
	)
							NonTerminal(params, FormalParameterList)
							Terminal("RPAREN")
							Terminal("ARROW")
							NonTerminal(ret, LambdaBody)
						)
						Sequence(
							NonTerminal(ret, Expression)
							Terminal("RPAREN")
							Action({
								ret = dress(SParenthesizedExpr.make(ret));
							})
						)
					)
				)
			)
			Action({
				return ret;
			})
		) */
	private int matchPrimaryPrefix(int lookahead) {
		lookahead = matchPrimaryPrefix_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(2)
			NonTerminal(ret, PrimarySuffixWithoutSuper)
		) */
	private int matchPrimarySuffix_1_1(int lookahead) {
		lookahead = matchPrimarySuffixWithoutSuper(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("DOT")
			Terminal("SUPER")
			Action({
				ret = dress(SSuperExpr.make(optionOf(scope)));
			})
		) */
	private int matchPrimarySuffix_1_2(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.DOT);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.SUPER);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				LookAhead(2)
				NonTerminal(ret, PrimarySuffixWithoutSuper)
			)
			Sequence(
				Terminal("DOT")
				Terminal("SUPER")
				Action({
					ret = dress(SSuperExpr.make(optionOf(scope)));
				})
			)
			NonTerminal(ret, MethodReferenceSuffix)
		) */
	private int matchPrimarySuffix_1(int lookahead) {
		int newLookahead;
		newLookahead = matchPrimarySuffix_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchPrimarySuffix_1_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchMethodReferenceSuffix(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			Choice(
				Sequence(
					LookAhead(2)
					NonTerminal(ret, PrimarySuffixWithoutSuper)
				)
				Sequence(
					Terminal("DOT")
					Terminal("SUPER")
					Action({
						ret = dress(SSuperExpr.make(optionOf(scope)));
					})
				)
				NonTerminal(ret, MethodReferenceSuffix)
			)
			Action({
				return ret;
			})
		) */
	private int matchPrimarySuffix(int lookahead) {
		lookahead = matchPrimarySuffix_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(2)
			Action({
				lateRun();
			})
			NonTerminal(ret, PrimarySuffix)
		) */
	private int matchPrimaryExpression_2_1(int lookahead) {
		lookahead = matchPrimarySuffix(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrMore(
			LookAhead(2)
			Action({
				lateRun();
			})
			NonTerminal(ret, PrimarySuffix)
		) */
	private int matchPrimaryExpression_2(int lookahead) {
		int newLookahead;
		newLookahead = matchPrimaryExpression_2_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchPrimaryExpression_2_1(lookahead);
		}
		return lookahead;
	}

	/* Sequence(
			NonTerminal(ret, PrimaryPrefix)
			ZeroOrMore(
				LookAhead(2)
				Action({
					lateRun();
				})
				NonTerminal(ret, PrimarySuffix)
			)
			Action({
				return ret;
			})
		) */
	private int matchPrimaryExpression(int lookahead) {
		lookahead = matchPrimaryPrefix(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchPrimaryExpression_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("INCR")
			Action({
				op = UnaryOp.PostIncrement;
			})
		) */
	private int matchPostfixExpression_2_1_3_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.INCR);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("DECR")
			Action({
				op = UnaryOp.PostDecrement;
			})
		) */
	private int matchPostfixExpression_2_1_3_2(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.DECR);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				Terminal("INCR")
				Action({
					op = UnaryOp.PostIncrement;
				})
			)
			Sequence(
				Terminal("DECR")
				Action({
					op = UnaryOp.PostDecrement;
				})
			)
		) */
	private int matchPostfixExpression_2_1_3(int lookahead) {
		int newLookahead;
		newLookahead = matchPostfixExpression_2_1_3_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchPostfixExpression_2_1_3_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			LookAhead(2)
			Action({
				lateRun();
			})
			Choice(
				Sequence(
					Terminal("INCR")
					Action({
						op = UnaryOp.PostIncrement;
					})
				)
				Sequence(
					Terminal("DECR")
					Action({
						op = UnaryOp.PostDecrement;
					})
				)
			)
			Action({
				ret = dress(SUnaryExpr.make(op, ret));
			})
		) */
	private int matchPostfixExpression_2_1(int lookahead) {
		lookahead = matchPostfixExpression_2_1_3(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			LookAhead(2)
			Action({
				lateRun();
			})
			Choice(
				Sequence(
					Terminal("INCR")
					Action({
						op = UnaryOp.PostIncrement;
					})
				)
				Sequence(
					Terminal("DECR")
					Action({
						op = UnaryOp.PostDecrement;
					})
				)
			)
			Action({
				ret = dress(SUnaryExpr.make(op, ret));
			})
		) */
	private int matchPostfixExpression_2(int lookahead) {
		int newLookahead;
		newLookahead = matchPostfixExpression_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(ret, PrimaryExpression)
			ZeroOrOne(
				LookAhead(2)
				Action({
					lateRun();
				})
				Choice(
					Sequence(
						Terminal("INCR")
						Action({
							op = UnaryOp.PostIncrement;
						})
					)
					Sequence(
						Terminal("DECR")
						Action({
							op = UnaryOp.PostDecrement;
						})
					)
				)
				Action({
					ret = dress(SUnaryExpr.make(op, ret));
				})
			)
			Action({
				return ret;
			})
		) */
	private int matchPostfixExpression(int lookahead) {
		lookahead = matchPrimaryExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchPostfixExpression_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				Action({
					run();
				})
				Choice(
					Sequence(
						Terminal("TILDE")
						Action({
							op = UnaryOp.Inverse;
						})
					)
					Sequence(
						Terminal("BANG")
						Action({
							op = UnaryOp.Not;
						})
					)
				)
				NonTerminal(ret, UnaryExpression)
				Action({
					ret = dress(SUnaryExpr.make(op, ret));
				})
			)
			Sequence(
				LookAhead(
					NonTerminal(CastExpression)
				)
				NonTerminal(ret, CastExpression)
			)
			NonTerminal(ret, PostfixExpression)
		) */
	private int matchUnaryExpressionNotPlusMinus_1(int lookahead) {
		int newLookahead;
		newLookahead = matchUnaryExpressionNotPlusMinus_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchUnaryExpressionNotPlusMinus_1_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchPostfixExpression(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			Choice(
				Sequence(
					Action({
						run();
					})
					Choice(
						Sequence(
							Terminal("TILDE")
							Action({
								op = UnaryOp.Inverse;
							})
						)
						Sequence(
							Terminal("BANG")
							Action({
								op = UnaryOp.Not;
							})
						)
					)
					NonTerminal(ret, UnaryExpression)
					Action({
						ret = dress(SUnaryExpr.make(op, ret));
					})
				)
				Sequence(
					LookAhead(
						NonTerminal(CastExpression)
					)
					NonTerminal(ret, CastExpression)
				)
				NonTerminal(ret, PostfixExpression)
			)
			Action({
				return ret;
			})
		) */
	private int matchUnaryExpressionNotPlusMinus(int lookahead) {
		lookahead = matchUnaryExpressionNotPlusMinus_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			NonTerminal(ret, PrefixExpression)
			Sequence(
				Action({
					run();
				})
				Choice(
					Sequence(
						Terminal("PLUS")
						Action({
							op = UnaryOp.Positive;
						})
					)
					Sequence(
						Terminal("MINUS")
						Action({
							op = UnaryOp.Negative;
						})
					)
				)
				NonTerminal(ret, UnaryExpression)
				Action({
					ret = dress(SUnaryExpr.make(op, ret));
				})
			)
			NonTerminal(ret, UnaryExpressionNotPlusMinus)
		) */
	private int matchUnaryExpression_1(int lookahead) {
		int newLookahead;
		newLookahead = matchPrefixExpression(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchUnaryExpression_1_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchUnaryExpressionNotPlusMinus(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			Choice(
				NonTerminal(ret, PrefixExpression)
				Sequence(
					Action({
						run();
					})
					Choice(
						Sequence(
							Terminal("PLUS")
							Action({
								op = UnaryOp.Positive;
							})
						)
						Sequence(
							Terminal("MINUS")
							Action({
								op = UnaryOp.Negative;
							})
						)
					)
					NonTerminal(ret, UnaryExpression)
					Action({
						ret = dress(SUnaryExpr.make(op, ret));
					})
				)
				NonTerminal(ret, UnaryExpressionNotPlusMinus)
			)
			Action({
				return ret;
			})
		) */
	private int matchUnaryExpression(int lookahead) {
		lookahead = matchUnaryExpression_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("STAR")
			Action({
				op = BinaryOp.Times;
			})
		) */
	private int matchMultiplicativeExpression_2_1_3_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.STAR);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("SLASH")
			Action({
				op = BinaryOp.Divide;
			})
		) */
	private int matchMultiplicativeExpression_2_1_3_2(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.SLASH);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("REM")
			Action({
				op = BinaryOp.Remainder;
			})
		) */
	private int matchMultiplicativeExpression_2_1_3_3(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.REM);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				Terminal("STAR")
				Action({
					op = BinaryOp.Times;
				})
			)
			Sequence(
				Terminal("SLASH")
				Action({
					op = BinaryOp.Divide;
				})
			)
			Sequence(
				Terminal("REM")
				Action({
					op = BinaryOp.Remainder;
				})
			)
		) */
	private int matchMultiplicativeExpression_2_1_3(int lookahead) {
		int newLookahead;
		newLookahead = matchMultiplicativeExpression_2_1_3_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchMultiplicativeExpression_2_1_3_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchMultiplicativeExpression_2_1_3_3(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			LookAhead(2)
			Action({
				lateRun();
			})
			Choice(
				Sequence(
					Terminal("STAR")
					Action({
						op = BinaryOp.Times;
					})
				)
				Sequence(
					Terminal("SLASH")
					Action({
						op = BinaryOp.Divide;
					})
				)
				Sequence(
					Terminal("REM")
					Action({
						op = BinaryOp.Remainder;
					})
				)
			)
			NonTerminal(right, UnaryExpression)
			Action({
				ret = dress(SBinaryExpr.make(ret, op, right));
			})
		) */
	private int matchMultiplicativeExpression_2_1(int lookahead) {
		lookahead = matchMultiplicativeExpression_2_1_3(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchUnaryExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrMore(
			LookAhead(2)
			Action({
				lateRun();
			})
			Choice(
				Sequence(
					Terminal("STAR")
					Action({
						op = BinaryOp.Times;
					})
				)
				Sequence(
					Terminal("SLASH")
					Action({
						op = BinaryOp.Divide;
					})
				)
				Sequence(
					Terminal("REM")
					Action({
						op = BinaryOp.Remainder;
					})
				)
			)
			NonTerminal(right, UnaryExpression)
			Action({
				ret = dress(SBinaryExpr.make(ret, op, right));
			})
		) */
	private int matchMultiplicativeExpression_2(int lookahead) {
		int newLookahead;
		newLookahead = matchMultiplicativeExpression_2_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchMultiplicativeExpression_2_1(lookahead);
		}
		return lookahead;
	}

	/* Sequence(
			NonTerminal(ret, UnaryExpression)
			ZeroOrMore(
				LookAhead(2)
				Action({
					lateRun();
				})
				Choice(
					Sequence(
						Terminal("STAR")
						Action({
							op = BinaryOp.Times;
						})
					)
					Sequence(
						Terminal("SLASH")
						Action({
							op = BinaryOp.Divide;
						})
					)
					Sequence(
						Terminal("REM")
						Action({
							op = BinaryOp.Remainder;
						})
					)
				)
				NonTerminal(right, UnaryExpression)
				Action({
					ret = dress(SBinaryExpr.make(ret, op, right));
				})
			)
			Action({
				return ret;
			})
		) */
	private int matchMultiplicativeExpression(int lookahead) {
		lookahead = matchUnaryExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchMultiplicativeExpression_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("PLUS")
			Action({
				op = BinaryOp.Plus;
			})
		) */
	private int matchAdditiveExpression_2_1_3_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.PLUS);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("MINUS")
			Action({
				op = BinaryOp.Minus;
			})
		) */
	private int matchAdditiveExpression_2_1_3_2(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.MINUS);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				Terminal("PLUS")
				Action({
					op = BinaryOp.Plus;
				})
			)
			Sequence(
				Terminal("MINUS")
				Action({
					op = BinaryOp.Minus;
				})
			)
		) */
	private int matchAdditiveExpression_2_1_3(int lookahead) {
		int newLookahead;
		newLookahead = matchAdditiveExpression_2_1_3_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchAdditiveExpression_2_1_3_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			LookAhead(2)
			Action({
				lateRun();
			})
			Choice(
				Sequence(
					Terminal("PLUS")
					Action({
						op = BinaryOp.Plus;
					})
				)
				Sequence(
					Terminal("MINUS")
					Action({
						op = BinaryOp.Minus;
					})
				)
			)
			NonTerminal(right, MultiplicativeExpression)
			Action({
				ret = dress(SBinaryExpr.make(ret, op, right));
			})
		) */
	private int matchAdditiveExpression_2_1(int lookahead) {
		lookahead = matchAdditiveExpression_2_1_3(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchMultiplicativeExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrMore(
			LookAhead(2)
			Action({
				lateRun();
			})
			Choice(
				Sequence(
					Terminal("PLUS")
					Action({
						op = BinaryOp.Plus;
					})
				)
				Sequence(
					Terminal("MINUS")
					Action({
						op = BinaryOp.Minus;
					})
				)
			)
			NonTerminal(right, MultiplicativeExpression)
			Action({
				ret = dress(SBinaryExpr.make(ret, op, right));
			})
		) */
	private int matchAdditiveExpression_2(int lookahead) {
		int newLookahead;
		newLookahead = matchAdditiveExpression_2_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchAdditiveExpression_2_1(lookahead);
		}
		return lookahead;
	}

	/* Sequence(
			NonTerminal(ret, MultiplicativeExpression)
			ZeroOrMore(
				LookAhead(2)
				Action({
					lateRun();
				})
				Choice(
					Sequence(
						Terminal("PLUS")
						Action({
							op = BinaryOp.Plus;
						})
					)
					Sequence(
						Terminal("MINUS")
						Action({
							op = BinaryOp.Minus;
						})
					)
				)
				NonTerminal(right, MultiplicativeExpression)
				Action({
					ret = dress(SBinaryExpr.make(ret, op, right));
				})
			)
			Action({
				return ret;
			})
		) */
	private int matchAdditiveExpression(int lookahead) {
		lookahead = matchMultiplicativeExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchAdditiveExpression_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("LSHIFT")
			Action({
				op = BinaryOp.LeftShift;
			})
		) */
	private int matchShiftExpression_2_1_3_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.LSHIFT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(			getToken(1).kind == GT && getToken(1).realKind == RUNSIGNEDSHIFT
	)
			Terminal("GT")
			Terminal("GT")
			Terminal("GT")
			Action({
				popNewWhitespaces();
			})
		) */
	private int matchRUNSIGNEDSHIFT(int lookahead) {
		lookahead = getToken(1).kind == GT && getToken(1).realKind == RUNSIGNEDSHIFT ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.GT);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.GT);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.GT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(3)
			NonTerminal(RUNSIGNEDSHIFT)
			Action({
				op = BinaryOp.RightUnsignedShift;
			})
		) */
	private int matchShiftExpression_2_1_3_2(int lookahead) {
		lookahead = matchRUNSIGNEDSHIFT(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(			getToken(1).kind == GT && getToken(1).realKind == RSIGNEDSHIFT
	)
			Terminal("GT")
			Terminal("GT")
			Action({
				popNewWhitespaces();
			})
		) */
	private int matchRSIGNEDSHIFT(int lookahead) {
		lookahead = getToken(1).kind == GT && getToken(1).realKind == RSIGNEDSHIFT ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.GT);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.GT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(2)
			NonTerminal(RSIGNEDSHIFT)
			Action({
				op = BinaryOp.RightSignedShift;
			})
		) */
	private int matchShiftExpression_2_1_3_3(int lookahead) {
		lookahead = matchRSIGNEDSHIFT(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				Terminal("LSHIFT")
				Action({
					op = BinaryOp.LeftShift;
				})
			)
			Sequence(
				LookAhead(3)
				NonTerminal(RUNSIGNEDSHIFT)
				Action({
					op = BinaryOp.RightUnsignedShift;
				})
			)
			Sequence(
				LookAhead(2)
				NonTerminal(RSIGNEDSHIFT)
				Action({
					op = BinaryOp.RightSignedShift;
				})
			)
		) */
	private int matchShiftExpression_2_1_3(int lookahead) {
		int newLookahead;
		newLookahead = matchShiftExpression_2_1_3_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchShiftExpression_2_1_3_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchShiftExpression_2_1_3_3(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			LookAhead(2)
			Action({
				lateRun();
			})
			Choice(
				Sequence(
					Terminal("LSHIFT")
					Action({
						op = BinaryOp.LeftShift;
					})
				)
				Sequence(
					LookAhead(3)
					NonTerminal(RUNSIGNEDSHIFT)
					Action({
						op = BinaryOp.RightUnsignedShift;
					})
				)
				Sequence(
					LookAhead(2)
					NonTerminal(RSIGNEDSHIFT)
					Action({
						op = BinaryOp.RightSignedShift;
					})
				)
			)
			NonTerminal(right, AdditiveExpression)
			Action({
				ret = dress(SBinaryExpr.make(ret, op, right));
			})
		) */
	private int matchShiftExpression_2_1(int lookahead) {
		lookahead = matchShiftExpression_2_1_3(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchAdditiveExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrMore(
			LookAhead(2)
			Action({
				lateRun();
			})
			Choice(
				Sequence(
					Terminal("LSHIFT")
					Action({
						op = BinaryOp.LeftShift;
					})
				)
				Sequence(
					LookAhead(3)
					NonTerminal(RUNSIGNEDSHIFT)
					Action({
						op = BinaryOp.RightUnsignedShift;
					})
				)
				Sequence(
					LookAhead(2)
					NonTerminal(RSIGNEDSHIFT)
					Action({
						op = BinaryOp.RightSignedShift;
					})
				)
			)
			NonTerminal(right, AdditiveExpression)
			Action({
				ret = dress(SBinaryExpr.make(ret, op, right));
			})
		) */
	private int matchShiftExpression_2(int lookahead) {
		int newLookahead;
		newLookahead = matchShiftExpression_2_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchShiftExpression_2_1(lookahead);
		}
		return lookahead;
	}

	/* Sequence(
			NonTerminal(ret, AdditiveExpression)
			ZeroOrMore(
				LookAhead(2)
				Action({
					lateRun();
				})
				Choice(
					Sequence(
						Terminal("LSHIFT")
						Action({
							op = BinaryOp.LeftShift;
						})
					)
					Sequence(
						LookAhead(3)
						NonTerminal(RUNSIGNEDSHIFT)
						Action({
							op = BinaryOp.RightUnsignedShift;
						})
					)
					Sequence(
						LookAhead(2)
						NonTerminal(RSIGNEDSHIFT)
						Action({
							op = BinaryOp.RightSignedShift;
						})
					)
				)
				NonTerminal(right, AdditiveExpression)
				Action({
					ret = dress(SBinaryExpr.make(ret, op, right));
				})
			)
			Action({
				return ret;
			})
		) */
	private int matchShiftExpression(int lookahead) {
		lookahead = matchAdditiveExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchShiftExpression_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("LT")
			Action({
				op = BinaryOp.Less;
			})
		) */
	private int matchRelationalExpression_2_1_3_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.LT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("GT")
			Action({
				op = BinaryOp.Greater;
			})
		) */
	private int matchRelationalExpression_2_1_3_2(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.GT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("LE")
			Action({
				op = BinaryOp.LessOrEqual;
			})
		) */
	private int matchRelationalExpression_2_1_3_3(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.LE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("GE")
			Action({
				op = BinaryOp.GreaterOrEqual;
			})
		) */
	private int matchRelationalExpression_2_1_3_4(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.GE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				Terminal("LT")
				Action({
					op = BinaryOp.Less;
				})
			)
			Sequence(
				Terminal("GT")
				Action({
					op = BinaryOp.Greater;
				})
			)
			Sequence(
				Terminal("LE")
				Action({
					op = BinaryOp.LessOrEqual;
				})
			)
			Sequence(
				Terminal("GE")
				Action({
					op = BinaryOp.GreaterOrEqual;
				})
			)
		) */
	private int matchRelationalExpression_2_1_3(int lookahead) {
		int newLookahead;
		newLookahead = matchRelationalExpression_2_1_3_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchRelationalExpression_2_1_3_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchRelationalExpression_2_1_3_3(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchRelationalExpression_2_1_3_4(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			LookAhead(2)
			Action({
				lateRun();
			})
			Choice(
				Sequence(
					Terminal("LT")
					Action({
						op = BinaryOp.Less;
					})
				)
				Sequence(
					Terminal("GT")
					Action({
						op = BinaryOp.Greater;
					})
				)
				Sequence(
					Terminal("LE")
					Action({
						op = BinaryOp.LessOrEqual;
					})
				)
				Sequence(
					Terminal("GE")
					Action({
						op = BinaryOp.GreaterOrEqual;
					})
				)
			)
			NonTerminal(right, ShiftExpression)
			Action({
				ret = dress(SBinaryExpr.make(ret, op, right));
			})
		) */
	private int matchRelationalExpression_2_1(int lookahead) {
		lookahead = matchRelationalExpression_2_1_3(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchShiftExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrMore(
			LookAhead(2)
			Action({
				lateRun();
			})
			Choice(
				Sequence(
					Terminal("LT")
					Action({
						op = BinaryOp.Less;
					})
				)
				Sequence(
					Terminal("GT")
					Action({
						op = BinaryOp.Greater;
					})
				)
				Sequence(
					Terminal("LE")
					Action({
						op = BinaryOp.LessOrEqual;
					})
				)
				Sequence(
					Terminal("GE")
					Action({
						op = BinaryOp.GreaterOrEqual;
					})
				)
			)
			NonTerminal(right, ShiftExpression)
			Action({
				ret = dress(SBinaryExpr.make(ret, op, right));
			})
		) */
	private int matchRelationalExpression_2(int lookahead) {
		int newLookahead;
		newLookahead = matchRelationalExpression_2_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchRelationalExpression_2_1(lookahead);
		}
		return lookahead;
	}

	/* Sequence(
			NonTerminal(ret, ShiftExpression)
			ZeroOrMore(
				LookAhead(2)
				Action({
					lateRun();
				})
				Choice(
					Sequence(
						Terminal("LT")
						Action({
							op = BinaryOp.Less;
						})
					)
					Sequence(
						Terminal("GT")
						Action({
							op = BinaryOp.Greater;
						})
					)
					Sequence(
						Terminal("LE")
						Action({
							op = BinaryOp.LessOrEqual;
						})
					)
					Sequence(
						Terminal("GE")
						Action({
							op = BinaryOp.GreaterOrEqual;
						})
					)
				)
				NonTerminal(right, ShiftExpression)
				Action({
					ret = dress(SBinaryExpr.make(ret, op, right));
				})
			)
			Action({
				return ret;
			})
		) */
	private int matchRelationalExpression(int lookahead) {
		lookahead = matchShiftExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchRelationalExpression_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(2)
			Action({
				lateRun();
			})
			Terminal("INSTANCEOF")
			Action({
				run();
			})
			NonTerminal(annotations, Annotations)
			NonTerminal(type, Type)
			Action({
				ret = dress(SInstanceOfExpr.make(ret, type));
			})
		) */
	private int matchInstanceOfExpression_2_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.INSTANCEOF);
		if (lookahead == -1)
			return -1;
		lookahead = matchAnnotations(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchType(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			LookAhead(2)
			Action({
				lateRun();
			})
			Terminal("INSTANCEOF")
			Action({
				run();
			})
			NonTerminal(annotations, Annotations)
			NonTerminal(type, Type)
			Action({
				ret = dress(SInstanceOfExpr.make(ret, type));
			})
		) */
	private int matchInstanceOfExpression_2(int lookahead) {
		int newLookahead;
		newLookahead = matchInstanceOfExpression_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(ret, RelationalExpression)
			ZeroOrOne(
				LookAhead(2)
				Action({
					lateRun();
				})
				Terminal("INSTANCEOF")
				Action({
					run();
				})
				NonTerminal(annotations, Annotations)
				NonTerminal(type, Type)
				Action({
					ret = dress(SInstanceOfExpr.make(ret, type));
				})
			)
			Action({
				return ret;
			})
		) */
	private int matchInstanceOfExpression(int lookahead) {
		lookahead = matchRelationalExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchInstanceOfExpression_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("EQ")
			Action({
				op = BinaryOp.Equal;
			})
		) */
	private int matchEqualityExpression_2_1_3_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.EQ);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("NE")
			Action({
				op = BinaryOp.NotEqual;
			})
		) */
	private int matchEqualityExpression_2_1_3_2(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.NE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				Terminal("EQ")
				Action({
					op = BinaryOp.Equal;
				})
			)
			Sequence(
				Terminal("NE")
				Action({
					op = BinaryOp.NotEqual;
				})
			)
		) */
	private int matchEqualityExpression_2_1_3(int lookahead) {
		int newLookahead;
		newLookahead = matchEqualityExpression_2_1_3_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchEqualityExpression_2_1_3_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			LookAhead(2)
			Action({
				lateRun();
			})
			Choice(
				Sequence(
					Terminal("EQ")
					Action({
						op = BinaryOp.Equal;
					})
				)
				Sequence(
					Terminal("NE")
					Action({
						op = BinaryOp.NotEqual;
					})
				)
			)
			NonTerminal(right, InstanceOfExpression)
			Action({
				ret = dress(SBinaryExpr.make(ret, op, right));
			})
		) */
	private int matchEqualityExpression_2_1(int lookahead) {
		lookahead = matchEqualityExpression_2_1_3(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchInstanceOfExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrMore(
			LookAhead(2)
			Action({
				lateRun();
			})
			Choice(
				Sequence(
					Terminal("EQ")
					Action({
						op = BinaryOp.Equal;
					})
				)
				Sequence(
					Terminal("NE")
					Action({
						op = BinaryOp.NotEqual;
					})
				)
			)
			NonTerminal(right, InstanceOfExpression)
			Action({
				ret = dress(SBinaryExpr.make(ret, op, right));
			})
		) */
	private int matchEqualityExpression_2(int lookahead) {
		int newLookahead;
		newLookahead = matchEqualityExpression_2_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchEqualityExpression_2_1(lookahead);
		}
		return lookahead;
	}

	/* Sequence(
			NonTerminal(ret, InstanceOfExpression)
			ZeroOrMore(
				LookAhead(2)
				Action({
					lateRun();
				})
				Choice(
					Sequence(
						Terminal("EQ")
						Action({
							op = BinaryOp.Equal;
						})
					)
					Sequence(
						Terminal("NE")
						Action({
							op = BinaryOp.NotEqual;
						})
					)
				)
				NonTerminal(right, InstanceOfExpression)
				Action({
					ret = dress(SBinaryExpr.make(ret, op, right));
				})
			)
			Action({
				return ret;
			})
		) */
	private int matchEqualityExpression(int lookahead) {
		lookahead = matchInstanceOfExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchEqualityExpression_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(2)
			Action({
				lateRun();
			})
			Terminal("BIT_AND")
			NonTerminal(right, EqualityExpression)
			Action({
				ret = dress(SBinaryExpr.make(ret, BinaryOp.BinAnd, right));
			})
		) */
	private int matchAndExpression_2_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.BIT_AND);
		if (lookahead == -1)
			return -1;
		lookahead = matchEqualityExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrMore(
			LookAhead(2)
			Action({
				lateRun();
			})
			Terminal("BIT_AND")
			NonTerminal(right, EqualityExpression)
			Action({
				ret = dress(SBinaryExpr.make(ret, BinaryOp.BinAnd, right));
			})
		) */
	private int matchAndExpression_2(int lookahead) {
		int newLookahead;
		newLookahead = matchAndExpression_2_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchAndExpression_2_1(lookahead);
		}
		return lookahead;
	}

	/* Sequence(
			NonTerminal(ret, EqualityExpression)
			ZeroOrMore(
				LookAhead(2)
				Action({
					lateRun();
				})
				Terminal("BIT_AND")
				NonTerminal(right, EqualityExpression)
				Action({
					ret = dress(SBinaryExpr.make(ret, BinaryOp.BinAnd, right));
				})
			)
			Action({
				return ret;
			})
		) */
	private int matchAndExpression(int lookahead) {
		lookahead = matchEqualityExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchAndExpression_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(2)
			Action({
				lateRun();
			})
			Terminal("XOR")
			NonTerminal(right, AndExpression)
			Action({
				ret = dress(SBinaryExpr.make(ret, BinaryOp.XOr, right));
			})
		) */
	private int matchExclusiveOrExpression_2_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.XOR);
		if (lookahead == -1)
			return -1;
		lookahead = matchAndExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrMore(
			LookAhead(2)
			Action({
				lateRun();
			})
			Terminal("XOR")
			NonTerminal(right, AndExpression)
			Action({
				ret = dress(SBinaryExpr.make(ret, BinaryOp.XOr, right));
			})
		) */
	private int matchExclusiveOrExpression_2(int lookahead) {
		int newLookahead;
		newLookahead = matchExclusiveOrExpression_2_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchExclusiveOrExpression_2_1(lookahead);
		}
		return lookahead;
	}

	/* Sequence(
			NonTerminal(ret, AndExpression)
			ZeroOrMore(
				LookAhead(2)
				Action({
					lateRun();
				})
				Terminal("XOR")
				NonTerminal(right, AndExpression)
				Action({
					ret = dress(SBinaryExpr.make(ret, BinaryOp.XOr, right));
				})
			)
			Action({
				return ret;
			})
		) */
	private int matchExclusiveOrExpression(int lookahead) {
		lookahead = matchAndExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchExclusiveOrExpression_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(2)
			Action({
				lateRun();
			})
			Terminal("BIT_OR")
			NonTerminal(right, ExclusiveOrExpression)
			Action({
				ret = dress(SBinaryExpr.make(ret, BinaryOp.BinOr, right));
			})
		) */
	private int matchInclusiveOrExpression_2_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.BIT_OR);
		if (lookahead == -1)
			return -1;
		lookahead = matchExclusiveOrExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrMore(
			LookAhead(2)
			Action({
				lateRun();
			})
			Terminal("BIT_OR")
			NonTerminal(right, ExclusiveOrExpression)
			Action({
				ret = dress(SBinaryExpr.make(ret, BinaryOp.BinOr, right));
			})
		) */
	private int matchInclusiveOrExpression_2(int lookahead) {
		int newLookahead;
		newLookahead = matchInclusiveOrExpression_2_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchInclusiveOrExpression_2_1(lookahead);
		}
		return lookahead;
	}

	/* Sequence(
			NonTerminal(ret, ExclusiveOrExpression)
			ZeroOrMore(
				LookAhead(2)
				Action({
					lateRun();
				})
				Terminal("BIT_OR")
				NonTerminal(right, ExclusiveOrExpression)
				Action({
					ret = dress(SBinaryExpr.make(ret, BinaryOp.BinOr, right));
				})
			)
			Action({
				return ret;
			})
		) */
	private int matchInclusiveOrExpression(int lookahead) {
		lookahead = matchExclusiveOrExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchInclusiveOrExpression_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(2)
			Action({
				lateRun();
			})
			Terminal("SC_AND")
			NonTerminal(right, InclusiveOrExpression)
			Action({
				ret = dress(SBinaryExpr.make(ret, BinaryOp.And, right));
			})
		) */
	private int matchConditionalAndExpression_2_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.SC_AND);
		if (lookahead == -1)
			return -1;
		lookahead = matchInclusiveOrExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrMore(
			LookAhead(2)
			Action({
				lateRun();
			})
			Terminal("SC_AND")
			NonTerminal(right, InclusiveOrExpression)
			Action({
				ret = dress(SBinaryExpr.make(ret, BinaryOp.And, right));
			})
		) */
	private int matchConditionalAndExpression_2(int lookahead) {
		int newLookahead;
		newLookahead = matchConditionalAndExpression_2_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchConditionalAndExpression_2_1(lookahead);
		}
		return lookahead;
	}

	/* Sequence(
			NonTerminal(ret, InclusiveOrExpression)
			ZeroOrMore(
				LookAhead(2)
				Action({
					lateRun();
				})
				Terminal("SC_AND")
				NonTerminal(right, InclusiveOrExpression)
				Action({
					ret = dress(SBinaryExpr.make(ret, BinaryOp.And, right));
				})
			)
			Action({
				return ret;
			})
		) */
	private int matchConditionalAndExpression(int lookahead) {
		lookahead = matchInclusiveOrExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchConditionalAndExpression_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(2)
			Action({
				lateRun();
			})
			Terminal("SC_OR")
			NonTerminal(right, ConditionalAndExpression)
			Action({
				ret = dress(SBinaryExpr.make(ret, BinaryOp.Or, right));
			})
		) */
	private int matchConditionalOrExpression_2_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.SC_OR);
		if (lookahead == -1)
			return -1;
		lookahead = matchConditionalAndExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrMore(
			LookAhead(2)
			Action({
				lateRun();
			})
			Terminal("SC_OR")
			NonTerminal(right, ConditionalAndExpression)
			Action({
				ret = dress(SBinaryExpr.make(ret, BinaryOp.Or, right));
			})
		) */
	private int matchConditionalOrExpression_2(int lookahead) {
		int newLookahead;
		newLookahead = matchConditionalOrExpression_2_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchConditionalOrExpression_2_1(lookahead);
		}
		return lookahead;
	}

	/* Sequence(
			NonTerminal(ret, ConditionalAndExpression)
			ZeroOrMore(
				LookAhead(2)
				Action({
					lateRun();
				})
				Terminal("SC_OR")
				NonTerminal(right, ConditionalAndExpression)
				Action({
					ret = dress(SBinaryExpr.make(ret, BinaryOp.Or, right));
				})
			)
			Action({
				return ret;
			})
		) */
	private int matchConditionalOrExpression(int lookahead) {
		lookahead = matchConditionalAndExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchConditionalOrExpression_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(2)
			Action({
				lateRun();
			})
			Terminal("HOOK")
			NonTerminal(left, Expression)
			Terminal("COLON")
			NonTerminal(right, ConditionalExpression)
			Action({
				ret = dress(SConditionalExpr.make(ret, left, right));
			})
		) */
	private int matchConditionalExpression_2_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.HOOK);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.COLON);
		if (lookahead == -1)
			return -1;
		lookahead = matchConditionalExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			LookAhead(2)
			Action({
				lateRun();
			})
			Terminal("HOOK")
			NonTerminal(left, Expression)
			Terminal("COLON")
			NonTerminal(right, ConditionalExpression)
			Action({
				ret = dress(SConditionalExpr.make(ret, left, right));
			})
		) */
	private int matchConditionalExpression_2(int lookahead) {
		int newLookahead;
		newLookahead = matchConditionalExpression_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(ret, ConditionalOrExpression)
			ZeroOrOne(
				LookAhead(2)
				Action({
					lateRun();
				})
				Terminal("HOOK")
				NonTerminal(left, Expression)
				Terminal("COLON")
				NonTerminal(right, ConditionalExpression)
				Action({
					ret = dress(SConditionalExpr.make(ret, left, right));
				})
			)
			Action({
				return ret;
			})
		) */
	private int matchConditionalExpression(int lookahead) {
		lookahead = matchConditionalOrExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchConditionalExpression_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			NonTerminal(ret, Annotation)
			NonTerminal(ret, MemberValueArrayInitializer)
			NonTerminal(ret, ConditionalExpression)
		) */
	private int matchMemberValue_1(int lookahead) {
		int newLookahead;
		newLookahead = matchAnnotation(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchMemberValueArrayInitializer(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchConditionalExpression(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			Choice(
				NonTerminal(ret, Annotation)
				NonTerminal(ret, MemberValueArrayInitializer)
				NonTerminal(ret, ConditionalExpression)
			)
			Action({
				return ret;
			})
		) */
	private int matchMemberValue(int lookahead) {
		lookahead = matchMemberValue_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Action({
				run();
			})
			NonTerminal(name, Name)
			Terminal("ASSIGN")
			NonTerminal(value, MemberValue)
			Action({
				return dress(SMemberValuePair.make(name, value));
			})
		) */
	private int matchMemberValuePair(int lookahead) {
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.ASSIGN);
		if (lookahead == -1)
			return -1;
		lookahead = matchMemberValue(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("COMMA")
			NonTerminal(pair, MemberValuePair)
			Action({
				ret = append(ret, pair);
			})
		) */
	private int matchMemberValuePairs_3_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.COMMA);
		if (lookahead == -1)
			return -1;
		lookahead = matchMemberValuePair(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrMore(
			Terminal("COMMA")
			NonTerminal(pair, MemberValuePair)
			Action({
				ret = append(ret, pair);
			})
		) */
	private int matchMemberValuePairs_3(int lookahead) {
		int newLookahead;
		newLookahead = matchMemberValuePairs_3_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchMemberValuePairs_3_1(lookahead);
		}
		return lookahead;
	}

	/* Sequence(
			NonTerminal(pair, MemberValuePair)
			Action({
				ret = append(ret, pair);
			})
			ZeroOrMore(
				Terminal("COMMA")
				NonTerminal(pair, MemberValuePair)
				Action({
					ret = append(ret, pair);
				})
			)
			Action({
				return ret;
			})
		) */
	private int matchMemberValuePairs(int lookahead) {
		lookahead = matchMemberValuePair(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchMemberValuePairs_3(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(pairs, MemberValuePairs)
		) */
	private int matchNormalAnnotation_5_1(int lookahead) {
		lookahead = matchMemberValuePairs(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			NonTerminal(pairs, MemberValuePairs)
		) */
	private int matchNormalAnnotation_5(int lookahead) {
		int newLookahead;
		newLookahead = matchNormalAnnotation_5_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			Action({
				run();
			})
			Terminal("AT")
			NonTerminal(name, QualifiedName)
			Terminal("LPAREN")
			ZeroOrOne(
				NonTerminal(pairs, MemberValuePairs)
			)
			Terminal("RPAREN")
			Action({
				return dress(SNormalAnnotationExpr.make(name, ensureNotNull(pairs)));
			})
		) */
	private int matchNormalAnnotation(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.AT);
		if (lookahead == -1)
			return -1;
		lookahead = matchQualifiedName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchNormalAnnotation_5(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(
				Terminal("AT")
				NonTerminal(QualifiedName)
				Terminal("LPAREN")
				Choice(
					Sequence(
						NonTerminal(Name)
						Terminal("ASSIGN")
					)
					Terminal("RPAREN")
				)
			)
			NonTerminal(ret, NormalAnnotation)
		) */
	private int matchAnnotation_1_1(int lookahead) {
		lookahead = matchNormalAnnotation(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Action({
				run();
			})
			Terminal("AT")
			NonTerminal(name, QualifiedName)
			Terminal("LPAREN")
			NonTerminal(memberVal, MemberValue)
			Terminal("RPAREN")
			Action({
				return dress(SSingleMemberAnnotationExpr.make(name, memberVal));
			})
		) */
	private int matchSingleMemberAnnotation(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.AT);
		if (lookahead == -1)
			return -1;
		lookahead = matchQualifiedName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchMemberValue(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(
				Terminal("AT")
				NonTerminal(QualifiedName)
				Terminal("LPAREN")
			)
			NonTerminal(ret, SingleMemberAnnotation)
		) */
	private int matchAnnotation_1_2(int lookahead) {
		lookahead = matchSingleMemberAnnotation(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Action({
				run();
			})
			Terminal("AT")
			NonTerminal(name, QualifiedName)
			Action({
				return dress(SMarkerAnnotationExpr.make(name));
			})
		) */
	private int matchMarkerAnnotation(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.AT);
		if (lookahead == -1)
			return -1;
		lookahead = matchQualifiedName(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(
				Terminal("AT")
				NonTerminal(QualifiedName)
			)
			NonTerminal(ret, MarkerAnnotation)
		) */
	private int matchAnnotation_1_3(int lookahead) {
		lookahead = matchMarkerAnnotation(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				LookAhead(
					Terminal("AT")
					NonTerminal(QualifiedName)
					Terminal("LPAREN")
					Choice(
						Sequence(
							NonTerminal(Name)
							Terminal("ASSIGN")
						)
						Terminal("RPAREN")
					)
				)
				NonTerminal(ret, NormalAnnotation)
			)
			Sequence(
				LookAhead(
					Terminal("AT")
					NonTerminal(QualifiedName)
					Terminal("LPAREN")
				)
				NonTerminal(ret, SingleMemberAnnotation)
			)
			Sequence(
				LookAhead(
					Terminal("AT")
					NonTerminal(QualifiedName)
				)
				NonTerminal(ret, MarkerAnnotation)
			)
		) */
	private int matchAnnotation_1(int lookahead) {
		int newLookahead;
		newLookahead = matchAnnotation_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchAnnotation_1_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchAnnotation_1_3(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			Choice(
				Sequence(
					LookAhead(
						Terminal("AT")
						NonTerminal(QualifiedName)
						Terminal("LPAREN")
						Choice(
							Sequence(
								NonTerminal(Name)
								Terminal("ASSIGN")
							)
							Terminal("RPAREN")
						)
					)
					NonTerminal(ret, NormalAnnotation)
				)
				Sequence(
					LookAhead(
						Terminal("AT")
						NonTerminal(QualifiedName)
						Terminal("LPAREN")
					)
					NonTerminal(ret, SingleMemberAnnotation)
				)
				Sequence(
					LookAhead(
						Terminal("AT")
						NonTerminal(QualifiedName)
					)
					NonTerminal(ret, MarkerAnnotation)
				)
			)
			Action({
				return ret;
			})
		) */
	private int matchAnnotation(int lookahead) {
		lookahead = matchAnnotation_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(annotation, Annotation)
			Action({
				annotations = append(annotations, annotation);
			})
		) */
	private int matchAnnotations_1_1(int lookahead) {
		lookahead = matchAnnotation(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrMore(
			NonTerminal(annotation, Annotation)
			Action({
				annotations = append(annotations, annotation);
			})
		) */
	private int matchAnnotations_1(int lookahead) {
		int newLookahead;
		newLookahead = matchAnnotations_1_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchAnnotations_1_1(lookahead);
		}
		return lookahead;
	}

	/* Sequence(
			ZeroOrMore(
				NonTerminal(annotation, Annotation)
				Action({
					annotations = append(annotations, annotation);
				})
			)
			Action({
				return annotations;
			})
		) */
	private int matchAnnotations(int lookahead) {
		lookahead = matchAnnotations_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Action({
				run();
			})
			NonTerminal(annotations, Annotations)
			Terminal("PACKAGE")
			NonTerminal(name, QualifiedName)
			Terminal("SEMICOLON")
			Action({
				return dress(SPackageDecl.make(annotations, name));
			})
		) */
	private int matchPackageDecl(int lookahead) {
		lookahead = matchAnnotations(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.PACKAGE);
		if (lookahead == -1)
			return -1;
		lookahead = matchQualifiedName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.SEMICOLON);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(PackageDecl)
		) */
	private int matchCompilationUnit1(int lookahead) {
		lookahead = matchPackageDecl(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	public void parseEpilog() throws ParseException {
		if (match(0, ParserImplConstants.EOF) != -1) {
			parse(ParserImplConstants.EOF);
		} else if (match(0, ParserImplConstants.EOF) != -1) {
			parse(ParserImplConstants.EOF);
		} else {
			throw produceParseException(ParserImplConstants.EOF);
		}
	}

	public BUTree<SPackageDecl> parsePackageDecl() throws ParseException {
		BUTree<SNodeList> annotations = null;
		BUTree<SQualifiedName> name;
		run();
		annotations = parseAnnotations();
		parse(ParserImplConstants.PACKAGE);
		name = parseQualifiedName();
		parse(ParserImplConstants.SEMICOLON);
		return dress(SPackageDecl.make(annotations, name));
	}

	public BUTree<SNodeList> parseImportDecls() throws ParseException {
		BUTree<SNodeList> imports = emptyList();
		BUTree<SImportDecl> importDecl = null;
		while (match(0, ParserImplConstants.IMPORT) != -1) {
			importDecl = parseImportDecl();
			imports = append(imports, importDecl);
		}
		return imports;
	}

	public BUTree<SImportDecl> parseImportDecl() throws ParseException {
		BUTree<SQualifiedName> name;
		boolean isStatic = false;
		boolean isAsterisk = false;
		run();
		parse(ParserImplConstants.IMPORT);
		if (match(0, ParserImplConstants.STATIC) != -1) {
			parse(ParserImplConstants.STATIC);
			isStatic = true;
		}
		name = parseQualifiedName();
		if (match(0, ParserImplConstants.DOT) != -1) {
			parse(ParserImplConstants.DOT);
			parse(ParserImplConstants.STAR);
			isAsterisk = true;
		}
		parse(ParserImplConstants.SEMICOLON);
		return dress(SImportDecl.make(name, isStatic, isAsterisk));
	}

	public BUTree<SNodeList> parseTypeDecls() throws ParseException {
		BUTree<SNodeList> types = emptyList();
		BUTree<? extends STypeDecl> typeDecl = null;
		while (match(0, ParserImplConstants.PROTECTED, ParserImplConstants.PUBLIC, ParserImplConstants.ABSTRACT, ParserImplConstants.PRIVATE, ParserImplConstants.STATIC, ParserImplConstants._DEFAULT, ParserImplConstants.TRANSIENT, ParserImplConstants.FINAL, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.VOLATILE, ParserImplConstants.STRICTFP, ParserImplConstants.NATIVE, ParserImplConstants.AT, ParserImplConstants.ENUM, ParserImplConstants.CLASS, ParserImplConstants.INTERFACE, ParserImplConstants.SEMICOLON) != -1) {
			typeDecl = parseTypeDecl();
			types = append(types, typeDecl);
		}
		return types;
	}

	public BUTree<SNodeList> parseModifiers() throws ParseException {
		BUTree<SNodeList> modifiers = emptyList();
		BUTree<? extends SAnnotationExpr> ann;
		while (matchModifiers1(0) != -1) {
			if (match(0, ParserImplConstants.PUBLIC) != -1) {
				parse(ParserImplConstants.PUBLIC);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Public));
			} else if (match(0, ParserImplConstants.PROTECTED) != -1) {
				parse(ParserImplConstants.PROTECTED);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Protected));
			} else if (match(0, ParserImplConstants.PRIVATE) != -1) {
				parse(ParserImplConstants.PRIVATE);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Private));
			} else if (match(0, ParserImplConstants.ABSTRACT) != -1) {
				parse(ParserImplConstants.ABSTRACT);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Abstract));
			} else if (match(0, ParserImplConstants._DEFAULT) != -1) {
				parse(ParserImplConstants._DEFAULT);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Default));
			} else if (match(0, ParserImplConstants.STATIC) != -1) {
				parse(ParserImplConstants.STATIC);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Static));
			} else if (match(0, ParserImplConstants.FINAL) != -1) {
				parse(ParserImplConstants.FINAL);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Final));
			} else if (match(0, ParserImplConstants.TRANSIENT) != -1) {
				parse(ParserImplConstants.TRANSIENT);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Transient));
			} else if (match(0, ParserImplConstants.VOLATILE) != -1) {
				parse(ParserImplConstants.VOLATILE);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Volatile));
			} else if (match(0, ParserImplConstants.SYNCHRONIZED) != -1) {
				parse(ParserImplConstants.SYNCHRONIZED);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Synchronized));
			} else if (match(0, ParserImplConstants.NATIVE) != -1) {
				parse(ParserImplConstants.NATIVE);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Native));
			} else if (match(0, ParserImplConstants.STRICTFP) != -1) {
				parse(ParserImplConstants.STRICTFP);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.StrictFP));
			} else if (match(0, ParserImplConstants.AT) != -1) {
				ann = parseAnnotation();
				modifiers = append(modifiers, ann);
			} else {
				throw produceParseException(ParserImplConstants.AT, ParserImplConstants.STRICTFP, ParserImplConstants.NATIVE, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.VOLATILE, ParserImplConstants.TRANSIENT, ParserImplConstants.FINAL, ParserImplConstants.STATIC, ParserImplConstants._DEFAULT, ParserImplConstants.ABSTRACT, ParserImplConstants.PRIVATE, ParserImplConstants.PROTECTED, ParserImplConstants.PUBLIC);
			}
		}
		return modifiers;
	}

	/* ZeroOrMore(
			LookAhead(2)
			Choice(
				Sequence(
					Terminal("PUBLIC")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Public));
					})
				)
				Sequence(
					Terminal("PROTECTED")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Protected));
					})
				)
				Sequence(
					Terminal("PRIVATE")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Private));
					})
				)
				Sequence(
					Terminal("ABSTRACT")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Abstract));
					})
				)
				Sequence(
					Terminal("_DEFAULT")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Default));
					})
				)
				Sequence(
					Terminal("STATIC")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Static));
					})
				)
				Sequence(
					Terminal("FINAL")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Final));
					})
				)
				Sequence(
					Terminal("TRANSIENT")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Transient));
					})
				)
				Sequence(
					Terminal("VOLATILE")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Volatile));
					})
				)
				Sequence(
					Terminal("SYNCHRONIZED")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Synchronized));
					})
				)
				Sequence(
					Terminal("NATIVE")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Native));
					})
				)
				Sequence(
					Terminal("STRICTFP")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.StrictFP));
					})
				)
				Sequence(
					NonTerminal(ann, Annotation)
					Action({
						modifiers = append(modifiers, ann);
					})
				)
			)
		) */
	private int matchModifiers1(int lookahead) {
		if (match(0, ParserImplConstants.VOLATILE) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.SYNCHRONIZED) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.NATIVE) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.AT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.PUBLIC) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.FINAL) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.PROTECTED) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.ABSTRACT) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants._DEFAULT) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.TRANSIENT) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.STRICTFP) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.STATIC) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.PRIVATE) != -1) {
			return lookahead;
		}
		return -1;
	}

	public BUTree<SNodeList> parseModifiersNoDefault() throws ParseException {
		BUTree<SNodeList> modifiers = emptyList();
		BUTree<? extends SAnnotationExpr> ann;
		while (matchModifiersNoDefault1(0) != -1) {
			if (match(0, ParserImplConstants.PUBLIC) != -1) {
				parse(ParserImplConstants.PUBLIC);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Public));
			} else if (match(0, ParserImplConstants.PROTECTED) != -1) {
				parse(ParserImplConstants.PROTECTED);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Protected));
			} else if (match(0, ParserImplConstants.PRIVATE) != -1) {
				parse(ParserImplConstants.PRIVATE);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Private));
			} else if (match(0, ParserImplConstants.ABSTRACT) != -1) {
				parse(ParserImplConstants.ABSTRACT);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Abstract));
			} else if (match(0, ParserImplConstants.STATIC) != -1) {
				parse(ParserImplConstants.STATIC);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Static));
			} else if (match(0, ParserImplConstants.FINAL) != -1) {
				parse(ParserImplConstants.FINAL);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Final));
			} else if (match(0, ParserImplConstants.TRANSIENT) != -1) {
				parse(ParserImplConstants.TRANSIENT);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Transient));
			} else if (match(0, ParserImplConstants.VOLATILE) != -1) {
				parse(ParserImplConstants.VOLATILE);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Volatile));
			} else if (match(0, ParserImplConstants.SYNCHRONIZED) != -1) {
				parse(ParserImplConstants.SYNCHRONIZED);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Synchronized));
			} else if (match(0, ParserImplConstants.NATIVE) != -1) {
				parse(ParserImplConstants.NATIVE);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Native));
			} else if (match(0, ParserImplConstants.STRICTFP) != -1) {
				parse(ParserImplConstants.STRICTFP);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.StrictFP));
			} else if (match(0, ParserImplConstants.AT) != -1) {
				ann = parseAnnotation();
				modifiers = append(modifiers, ann);
			} else {
				throw produceParseException(ParserImplConstants.AT, ParserImplConstants.STRICTFP, ParserImplConstants.NATIVE, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.VOLATILE, ParserImplConstants.TRANSIENT, ParserImplConstants.FINAL, ParserImplConstants.STATIC, ParserImplConstants.ABSTRACT, ParserImplConstants.PRIVATE, ParserImplConstants.PROTECTED, ParserImplConstants.PUBLIC);
			}
		}
		return modifiers;
	}

	/* ZeroOrMore(
			LookAhead(2)
			Choice(
				Sequence(
					Terminal("PUBLIC")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Public));
					})
				)
				Sequence(
					Terminal("PROTECTED")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Protected));
					})
				)
				Sequence(
					Terminal("PRIVATE")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Private));
					})
				)
				Sequence(
					Terminal("ABSTRACT")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Abstract));
					})
				)
				Sequence(
					Terminal("STATIC")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Static));
					})
				)
				Sequence(
					Terminal("FINAL")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Final));
					})
				)
				Sequence(
					Terminal("TRANSIENT")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Transient));
					})
				)
				Sequence(
					Terminal("VOLATILE")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Volatile));
					})
				)
				Sequence(
					Terminal("SYNCHRONIZED")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Synchronized));
					})
				)
				Sequence(
					Terminal("NATIVE")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.Native));
					})
				)
				Sequence(
					Terminal("STRICTFP")
					Action({
						modifiers = append(modifiers, SModifier.make(ModifierKeyword.StrictFP));
					})
				)
				Sequence(
					NonTerminal(ann, Annotation)
					Action({
						modifiers = append(modifiers, ann);
					})
				)
			)
		) */
	private int matchModifiersNoDefault1(int lookahead) {
		if (match(0, ParserImplConstants.VOLATILE) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.SYNCHRONIZED) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.NATIVE) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.AT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.PUBLIC) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.FINAL) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.PROTECTED) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.ABSTRACT) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.TRANSIENT) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.STRICTFP) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.STATIC) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.PRIVATE) != -1) {
			return lookahead;
		}
		return -1;
	}

	public BUTree<? extends STypeDecl> parseTypeDecl() throws ParseException {
		BUTree<SNodeList> modifiers;
		BUTree<? extends STypeDecl> ret;
		run();
		if (match(0, ParserImplConstants.SEMICOLON) != -1) {
			parse(ParserImplConstants.SEMICOLON);
			ret = dress(SEmptyTypeDecl.make());
		} else if (match(0, ParserImplConstants.PROTECTED, ParserImplConstants.PUBLIC, ParserImplConstants.ABSTRACT, ParserImplConstants.PRIVATE, ParserImplConstants.STATIC, ParserImplConstants._DEFAULT, ParserImplConstants.TRANSIENT, ParserImplConstants.FINAL, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.VOLATILE, ParserImplConstants.STRICTFP, ParserImplConstants.NATIVE, ParserImplConstants.AT, ParserImplConstants.ENUM, ParserImplConstants.CLASS, ParserImplConstants.INTERFACE) != -1) {
			modifiers = parseModifiers();
			if (match(0, ParserImplConstants.CLASS, ParserImplConstants.INTERFACE) != -1) {
				ret = parseClassOrInterfaceDecl(modifiers);
			} else if (match(0, ParserImplConstants.ENUM) != -1) {
				ret = parseEnumDecl(modifiers);
			} else if (match(0, ParserImplConstants.AT) != -1) {
				ret = parseAnnotationTypeDecl(modifiers);
			} else {
				throw produceParseException(ParserImplConstants.AT, ParserImplConstants.ENUM, ParserImplConstants.INTERFACE, ParserImplConstants.CLASS);
			}
		} else {
			throw produceParseException(ParserImplConstants.VOLATILE, ParserImplConstants.TRANSIENT, ParserImplConstants.NATIVE, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.AT, ParserImplConstants.STRICTFP, ParserImplConstants.PUBLIC, ParserImplConstants.PRIVATE, ParserImplConstants.PROTECTED, ParserImplConstants._DEFAULT, ParserImplConstants.ABSTRACT, ParserImplConstants.FINAL, ParserImplConstants.STATIC, ParserImplConstants.ENUM, ParserImplConstants.CLASS, ParserImplConstants.INTERFACE, ParserImplConstants.SEMICOLON);
		}
		return ret;
	}

	public BUTree<? extends STypeDecl> parseClassOrInterfaceDecl(BUTree<SNodeList> modifiers) throws ParseException {
		TypeKind typeKind;
		BUTree<SName> name;
		BUTree<SNodeList> typeParams = null;
		BUTree<SQualifiedType> superClassType = null;
		BUTree<SNodeList> extendsClause = null;
		BUTree<SNodeList> implementsClause = null;
		BUTree<SNodeList> members;
		ByRef<BUProblem> problem = new ByRef<BUProblem>(null);
		if (match(0, ParserImplConstants.CLASS) != -1) {
			parse(ParserImplConstants.CLASS);
			typeKind = TypeKind.Class;
			name = parseName();
			if (match(0, ParserImplConstants.LT) != -1) {
				typeParams = parseTypeParameters();
			}
			if (match(0, ParserImplConstants.EXTENDS) != -1) {
				parse(ParserImplConstants.EXTENDS);
				superClassType = parseAnnotatedQualifiedType();
			}
			if (match(0, ParserImplConstants.IMPLEMENTS) != -1) {
				implementsClause = parseImplementsList(typeKind, problem);
			}
		} else if (match(0, ParserImplConstants.INTERFACE) != -1) {
			parse(ParserImplConstants.INTERFACE);
			typeKind = TypeKind.Interface;
			name = parseName();
			if (match(0, ParserImplConstants.LT) != -1) {
				typeParams = parseTypeParameters();
			}
			if (match(0, ParserImplConstants.EXTENDS) != -1) {
				extendsClause = parseExtendsList();
			}
		} else {
			throw produceParseException(ParserImplConstants.INTERFACE, ParserImplConstants.CLASS);
		}
		members = parseClassOrInterfaceBody(typeKind);
		if (typeKind == TypeKind.Interface)
			return dress(SInterfaceDecl.make(modifiers, name, ensureNotNull(typeParams), ensureNotNull(extendsClause), members)).withProblem(problem.value);
		else {
			return dress(SClassDecl.make(modifiers, name, ensureNotNull(typeParams), optionOf(superClassType), ensureNotNull(implementsClause), members));
		}
	}

	public BUTree<SNodeList> parseExtendsList() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<SQualifiedType> cit;
		BUTree<SNodeList> annotations = null;
		parse(ParserImplConstants.EXTENDS);
		if (match(0, ParserImplConstants.AT, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE) != -1) {
			cit = parseAnnotatedQualifiedType();
			ret = append(ret, cit);
			while (match(0, ParserImplConstants.COMMA) != -1) {
				parse(ParserImplConstants.COMMA);
				cit = parseAnnotatedQualifiedType();
				ret = append(ret, cit);
			}
		} else if (quotesMode) {
			ret = parseNodeListVar();
		} else {
			throw produceParseException(ParserImplConstants.NODE_LIST_VARIABLE, ParserImplConstants.AT, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE);
		}
		return ret;
	}

	public BUTree<SNodeList> parseImplementsList(TypeKind typeKind, ByRef<BUProblem> problem) throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<SQualifiedType> cit;
		BUTree<SNodeList> annotations = null;
		parse(ParserImplConstants.IMPLEMENTS);
		if (match(0, ParserImplConstants.AT, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE) != -1) {
			cit = parseAnnotatedQualifiedType();
			ret = append(ret, cit);
			while (match(0, ParserImplConstants.COMMA) != -1) {
				parse(ParserImplConstants.COMMA);
				cit = parseAnnotatedQualifiedType();
				ret = append(ret, cit);
			}
			if (typeKind == TypeKind.Interface)
				problem.value = new BUProblem(Severity.ERROR, "An interface cannot implement other interfaces");

		} else if (quotesMode) {
			ret = parseNodeListVar();
		} else {
			throw produceParseException(ParserImplConstants.NODE_LIST_VARIABLE, ParserImplConstants.AT, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE);
		}
		return ret;
	}

	public BUTree<? extends STypeDecl> parseEnumDecl(BUTree<SNodeList> modifiers) throws ParseException {
		BUTree<SName> name;
		BUTree<SNodeList> implementsClause = emptyList();
		BUTree<SEnumConstantDecl> entry;
		BUTree<SNodeList> constants = emptyList();
		boolean trailingComma = false;
		BUTree<SNodeList> members = null;
		ByRef<BUProblem> problem = new ByRef<BUProblem>(null);
		parse(ParserImplConstants.ENUM);
		name = parseName();
		if (match(0, ParserImplConstants.IMPLEMENTS) != -1) {
			implementsClause = parseImplementsList(TypeKind.Enum, problem);
		}
		parse(ParserImplConstants.LBRACE);
		if (match(0, ParserImplConstants.PUBLIC, ParserImplConstants.AT, ParserImplConstants.STRICTFP, ParserImplConstants.NATIVE, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.VOLATILE, ParserImplConstants.TRANSIENT, ParserImplConstants.FINAL, ParserImplConstants.STATIC, ParserImplConstants._DEFAULT, ParserImplConstants.ABSTRACT, ParserImplConstants.PRIVATE, ParserImplConstants.PROTECTED, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.NODE_LIST_VARIABLE) != -1) {
			if (match(0, ParserImplConstants.AT, ParserImplConstants.STRICTFP, ParserImplConstants.NATIVE, ParserImplConstants.SYNCHRONIZED, ParserImplConstants._DEFAULT, ParserImplConstants.ABSTRACT, ParserImplConstants.PRIVATE, ParserImplConstants.PROTECTED, ParserImplConstants.VOLATILE, ParserImplConstants.TRANSIENT, ParserImplConstants.FINAL, ParserImplConstants.STATIC, ParserImplConstants.PUBLIC, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE) != -1) {
				entry = parseEnumConstantDecl();
				constants = append(constants, entry);
				while (matchEnumDecl1(0) != -1) {
					parse(ParserImplConstants.COMMA);
					entry = parseEnumConstantDecl();
					constants = append(constants, entry);
				}
			} else if (quotesMode) {
				constants = parseNodeListVar();
			} else {
				throw produceParseException(ParserImplConstants.NODE_LIST_VARIABLE, ParserImplConstants.PROTECTED, ParserImplConstants.PRIVATE, ParserImplConstants.PUBLIC, ParserImplConstants.STRICTFP, ParserImplConstants.AT, ParserImplConstants.STATIC, ParserImplConstants.FINAL, ParserImplConstants.ABSTRACT, ParserImplConstants._DEFAULT, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.NATIVE, ParserImplConstants.TRANSIENT, ParserImplConstants.VOLATILE, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE);
			}
		}
		if (match(0, ParserImplConstants.COMMA) != -1) {
			parse(ParserImplConstants.COMMA);
			trailingComma = true;
		}
		if (match(0, ParserImplConstants.SEMICOLON) != -1) {
			parse(ParserImplConstants.SEMICOLON);
			members = parseClassOrInterfaceBodyDecls(TypeKind.Enum);
		}
		parse(ParserImplConstants.RBRACE);
		return dress(SEnumDecl.make(modifiers, name, implementsClause, constants, trailingComma, ensureNotNull(members))).withProblem(problem.value);
	}

	/* ZeroOrMore(
			LookAhead(2)
			Terminal("COMMA")
			NonTerminal(entry, EnumConstantDecl)
			Action({
				constants = append(constants, entry);
			})
		) */
	private int matchEnumDecl1(int lookahead) {
		if (match(0, ParserImplConstants.COMMA) != -1) {
			if (match(1, ParserImplConstants.VOLATILE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SYNCHRONIZED) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NATIVE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FINAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRICTFP) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ABSTRACT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants._DEFAULT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STATIC) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PRIVATE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PUBLIC) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PROTECTED) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRANSIENT) != -1) {
				return lookahead;
			}
		}
		return -1;
	}

	public BUTree<SEnumConstantDecl> parseEnumConstantDecl() throws ParseException {
		BUTree<SNodeList> modifiers = null;
		BUTree<SName> name;
		BUTree<SNodeList> args = null;
		BUTree<SNodeList> classBody = null;
		run();
		modifiers = parseModifiers();
		name = parseName();
		if (match(0, ParserImplConstants.LPAREN) != -1) {
			args = parseArguments();
		}
		if (match(0, ParserImplConstants.LBRACE) != -1) {
			classBody = parseClassOrInterfaceBody(TypeKind.Class);
		}
		return dress(SEnumConstantDecl.make(modifiers, name, optionOf(args), optionOf(classBody)));
	}

	public BUTree<SAnnotationDecl> parseAnnotationTypeDecl(BUTree<SNodeList> modifiers) throws ParseException {
		BUTree<SName> name;
		BUTree<SNodeList> members;
		parse(ParserImplConstants.AT);
		parse(ParserImplConstants.INTERFACE);
		name = parseName();
		members = parseAnnotationTypeBody();
		return dress(SAnnotationDecl.make(modifiers, name, members));
	}

	public BUTree<SNodeList> parseAnnotationTypeBody() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<? extends SMemberDecl> member;
		parse(ParserImplConstants.LBRACE);
		if (match(0, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.VOLATILE, ParserImplConstants.STRICTFP, ParserImplConstants.NATIVE, ParserImplConstants.AT, ParserImplConstants.PROTECTED, ParserImplConstants.PUBLIC, ParserImplConstants.ABSTRACT, ParserImplConstants.PRIVATE, ParserImplConstants.STATIC, ParserImplConstants._DEFAULT, ParserImplConstants.TRANSIENT, ParserImplConstants.FINAL, ParserImplConstants.DOUBLE, ParserImplConstants.LONG, ParserImplConstants.FLOAT, ParserImplConstants.BOOLEAN, ParserImplConstants.SHORT, ParserImplConstants.INT, ParserImplConstants.CHAR, ParserImplConstants.BYTE, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.ENUM, ParserImplConstants.CLASS, ParserImplConstants.INTERFACE, ParserImplConstants.SEMICOLON, ParserImplConstants.NODE_LIST_VARIABLE) != -1) {
			if (match(0, ParserImplConstants.PROTECTED, ParserImplConstants.PUBLIC, ParserImplConstants.ABSTRACT, ParserImplConstants.PRIVATE, ParserImplConstants.STATIC, ParserImplConstants._DEFAULT, ParserImplConstants.TRANSIENT, ParserImplConstants.FINAL, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.VOLATILE, ParserImplConstants.STRICTFP, ParserImplConstants.NATIVE, ParserImplConstants.AT, ParserImplConstants.ENUM, ParserImplConstants.INTERFACE, ParserImplConstants.CLASS, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.SEMICOLON) != -1) {
				do {
					member = parseAnnotationTypeBodyDecl();
					ret = append(ret, member);
				}
				while (match(0, ParserImplConstants.PROTECTED, ParserImplConstants.PUBLIC, ParserImplConstants.ABSTRACT, ParserImplConstants.PRIVATE, ParserImplConstants.STATIC, ParserImplConstants._DEFAULT, ParserImplConstants.TRANSIENT, ParserImplConstants.FINAL, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.VOLATILE, ParserImplConstants.STRICTFP, ParserImplConstants.NATIVE, ParserImplConstants.AT, ParserImplConstants.ENUM, ParserImplConstants.INTERFACE, ParserImplConstants.CLASS, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.SEMICOLON) != -1);
			} else if (quotesMode) {
				ret = parseNodeListVar();
			} else {
				throw produceParseException(ParserImplConstants.NODE_LIST_VARIABLE, ParserImplConstants.PUBLIC, ParserImplConstants.PROTECTED, ParserImplConstants.AT, ParserImplConstants.NATIVE, ParserImplConstants.STRICTFP, ParserImplConstants.VOLATILE, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.FINAL, ParserImplConstants.TRANSIENT, ParserImplConstants._DEFAULT, ParserImplConstants.STATIC, ParserImplConstants.PRIVATE, ParserImplConstants.ABSTRACT, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.CLASS, ParserImplConstants.INTERFACE, ParserImplConstants.ENUM, ParserImplConstants.SEMICOLON);
			}
		}
		parse(ParserImplConstants.RBRACE);
		return ret;
	}

	public BUTree<? extends SMemberDecl> parseAnnotationTypeBodyDecl() throws ParseException {
		BUTree<SNodeList> modifiers;
		BUTree<? extends SMemberDecl> ret;
		run();
		if (match(0, ParserImplConstants.SEMICOLON) != -1) {
			parse(ParserImplConstants.SEMICOLON);
			ret = dress(SEmptyTypeDecl.make());
		} else if (match(0, ParserImplConstants.PROTECTED, ParserImplConstants.PUBLIC, ParserImplConstants.ABSTRACT, ParserImplConstants.PRIVATE, ParserImplConstants.STATIC, ParserImplConstants._DEFAULT, ParserImplConstants.TRANSIENT, ParserImplConstants.FINAL, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.VOLATILE, ParserImplConstants.STRICTFP, ParserImplConstants.NATIVE, ParserImplConstants.AT, ParserImplConstants.INTERFACE, ParserImplConstants.CLASS, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.ENUM) != -1) {
			modifiers = parseModifiers();
			if (matchAnnotationTypeBodyDecl1(0) != -1) {
				ret = parseAnnotationTypeMemberDecl(modifiers);
			} else if (match(0, ParserImplConstants.CLASS, ParserImplConstants.INTERFACE) != -1) {
				ret = parseClassOrInterfaceDecl(modifiers);
			} else if (match(0, ParserImplConstants.ENUM) != -1) {
				ret = parseEnumDecl(modifiers);
			} else if (match(0, ParserImplConstants.AT) != -1) {
				ret = parseAnnotationTypeDecl(modifiers);
			} else if (match(0, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT) != -1) {
				ret = parseFieldDecl(modifiers);
			} else {
				throw produceParseException(ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.AT, ParserImplConstants.ENUM, ParserImplConstants.INTERFACE, ParserImplConstants.CLASS);
			}
		} else {
			throw produceParseException(ParserImplConstants.VOLATILE, ParserImplConstants.TRANSIENT, ParserImplConstants.NATIVE, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.AT, ParserImplConstants.STRICTFP, ParserImplConstants.PUBLIC, ParserImplConstants.PRIVATE, ParserImplConstants.PROTECTED, ParserImplConstants._DEFAULT, ParserImplConstants.ABSTRACT, ParserImplConstants.FINAL, ParserImplConstants.STATIC, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.ENUM, ParserImplConstants.INTERFACE, ParserImplConstants.CLASS, ParserImplConstants.SEMICOLON);
		}
		return ret;
	}

	/* Sequence(
			NonTerminal(Type)
			NonTerminal(Name)
			Terminal("LPAREN")
		) */
	private int matchAnnotationTypeBodyDecl1(int lookahead) {
		lookahead = matchType(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	public BUTree<SAnnotationMemberDecl> parseAnnotationTypeMemberDecl(BUTree<SNodeList> modifiers) throws ParseException {
		BUTree<? extends SType> type;
		BUTree<SName> name;
		BUTree<SNodeList> dims;
		BUTree<SNodeOption> defaultVal = none();
		BUTree<? extends SExpr> val = null;
		type = parseType(null);
		name = parseName();
		parse(ParserImplConstants.LPAREN);
		parse(ParserImplConstants.RPAREN);
		dims = parseArrayDims();
		if (match(0, ParserImplConstants._DEFAULT) != -1) {
			parse(ParserImplConstants._DEFAULT);
			val = parseMemberValue();
			defaultVal = optionOf(val);
		}
		parse(ParserImplConstants.SEMICOLON);
		return dress(SAnnotationMemberDecl.make(modifiers, type, name, dims, defaultVal));
	}

	public BUTree<SNodeList> parseTypeParameters() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<STypeParameter> tp;
		parse(ParserImplConstants.LT);
		if (match(0, ParserImplConstants.AT, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE) != -1) {
			tp = parseTypeParameter();
			ret = append(ret, tp);
			while (match(0, ParserImplConstants.COMMA) != -1) {
				parse(ParserImplConstants.COMMA);
				tp = parseTypeParameter();
				ret = append(ret, tp);
			}
		} else if (quotesMode) {
			ret = parseNodeListVar();
		} else {
			throw produceParseException(ParserImplConstants.NODE_LIST_VARIABLE, ParserImplConstants.AT, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE);
		}
		parse(ParserImplConstants.GT);
		return ret;
	}

	public BUTree<STypeParameter> parseTypeParameter() throws ParseException {
		BUTree<SNodeList> annotations = null;
		BUTree<SName> name;
		BUTree<SNodeList> typeBounds = null;
		run();
		annotations = parseAnnotations();
		name = parseName();
		if (match(0, ParserImplConstants.EXTENDS) != -1) {
			typeBounds = parseTypeBounds();
		}
		return dress(STypeParameter.make(annotations, name, ensureNotNull(typeBounds)));
	}

	public BUTree<SNodeList> parseTypeBounds() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<SQualifiedType> cit;
		BUTree<SNodeList> annotations = null;
		parse(ParserImplConstants.EXTENDS);
		if (match(0, ParserImplConstants.AT, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE) != -1) {
			cit = parseAnnotatedQualifiedType();
			ret = append(ret, cit);
			while (match(0, ParserImplConstants.BIT_AND) != -1) {
				parse(ParserImplConstants.BIT_AND);
				cit = parseAnnotatedQualifiedType();
				ret = append(ret, cit);
			}
		} else if (quotesMode) {
			ret = parseNodeListVar();
		} else {
			throw produceParseException(ParserImplConstants.NODE_LIST_VARIABLE, ParserImplConstants.AT, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE);
		}
		return ret;
	}

	public BUTree<SNodeList> parseClassOrInterfaceBody(TypeKind typeKind) throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<? extends SMemberDecl> member;
		parse(ParserImplConstants.LBRACE);
		ret = parseClassOrInterfaceBodyDecls(typeKind);
		parse(ParserImplConstants.RBRACE);
		return ret;
	}

	public BUTree<SNodeList> parseClassOrInterfaceBodyDecls(TypeKind typeKind) throws ParseException {
		BUTree<? extends SMemberDecl> member;
		BUTree<SNodeList> ret = emptyList();
		if (match(0, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.VOLATILE, ParserImplConstants.STRICTFP, ParserImplConstants.NATIVE, ParserImplConstants.AT, ParserImplConstants.PROTECTED, ParserImplConstants.PUBLIC, ParserImplConstants.ABSTRACT, ParserImplConstants.PRIVATE, ParserImplConstants.STATIC, ParserImplConstants._DEFAULT, ParserImplConstants.TRANSIENT, ParserImplConstants.FINAL, ParserImplConstants.LBRACE, ParserImplConstants.LT, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.VOID, ParserImplConstants.ENUM, ParserImplConstants.CLASS, ParserImplConstants.INTERFACE, ParserImplConstants.SEMICOLON, ParserImplConstants.NODE_LIST_VARIABLE) != -1) {
			if (match(0, ParserImplConstants.PROTECTED, ParserImplConstants.PUBLIC, ParserImplConstants.ABSTRACT, ParserImplConstants.PRIVATE, ParserImplConstants.STATIC, ParserImplConstants._DEFAULT, ParserImplConstants.TRANSIENT, ParserImplConstants.FINAL, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.VOLATILE, ParserImplConstants.STRICTFP, ParserImplConstants.NATIVE, ParserImplConstants.AT, ParserImplConstants.LBRACE, ParserImplConstants.LT, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.ENUM, ParserImplConstants.CLASS, ParserImplConstants.INTERFACE, ParserImplConstants.FLOAT, ParserImplConstants.DOUBLE, ParserImplConstants.BYTE, ParserImplConstants.SHORT, ParserImplConstants.INT, ParserImplConstants.LONG, ParserImplConstants.BOOLEAN, ParserImplConstants.CHAR, ParserImplConstants.VOID, ParserImplConstants.SEMICOLON) != -1) {
				do {
					member = parseClassOrInterfaceBodyDecl(typeKind);
					ret = append(ret, member);
				}
				while (match(0, ParserImplConstants.PROTECTED, ParserImplConstants.PUBLIC, ParserImplConstants.ABSTRACT, ParserImplConstants.PRIVATE, ParserImplConstants.STATIC, ParserImplConstants._DEFAULT, ParserImplConstants.TRANSIENT, ParserImplConstants.FINAL, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.VOLATILE, ParserImplConstants.STRICTFP, ParserImplConstants.NATIVE, ParserImplConstants.AT, ParserImplConstants.LBRACE, ParserImplConstants.LT, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.ENUM, ParserImplConstants.CLASS, ParserImplConstants.INTERFACE, ParserImplConstants.FLOAT, ParserImplConstants.DOUBLE, ParserImplConstants.BYTE, ParserImplConstants.SHORT, ParserImplConstants.INT, ParserImplConstants.LONG, ParserImplConstants.BOOLEAN, ParserImplConstants.CHAR, ParserImplConstants.VOID, ParserImplConstants.SEMICOLON) != -1);
			} else if (quotesMode) {
				ret = parseNodeListVar();
			} else {
				throw produceParseException(ParserImplConstants.NODE_LIST_VARIABLE, ParserImplConstants.PUBLIC, ParserImplConstants.PROTECTED, ParserImplConstants.AT, ParserImplConstants.NATIVE, ParserImplConstants.STRICTFP, ParserImplConstants.VOLATILE, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.FINAL, ParserImplConstants.TRANSIENT, ParserImplConstants._DEFAULT, ParserImplConstants.STATIC, ParserImplConstants.PRIVATE, ParserImplConstants.ABSTRACT, ParserImplConstants.INTERFACE, ParserImplConstants.CLASS, ParserImplConstants.ENUM, ParserImplConstants.LBRACE, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.LT, ParserImplConstants.VOID, ParserImplConstants.SEMICOLON);
			}
		}
		return ret;
	}

	public BUTree<? extends SMemberDecl> parseClassOrInterfaceBodyDecl(TypeKind typeKind) throws ParseException {
		BUTree<SNodeList> modifiers;
		BUTree<? extends SMemberDecl> ret;
		BUProblem problem = null;
		run();
		if (match(0, ParserImplConstants.SEMICOLON) != -1) {
			parse(ParserImplConstants.SEMICOLON);
			ret = dress(SEmptyMemberDecl.make());
		} else if (match(0, ParserImplConstants.PROTECTED, ParserImplConstants.PUBLIC, ParserImplConstants.ABSTRACT, ParserImplConstants.PRIVATE, ParserImplConstants.STATIC, ParserImplConstants._DEFAULT, ParserImplConstants.TRANSIENT, ParserImplConstants.FINAL, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.VOLATILE, ParserImplConstants.STRICTFP, ParserImplConstants.NATIVE, ParserImplConstants.AT, ParserImplConstants.ENUM, ParserImplConstants.CLASS, ParserImplConstants.INTERFACE, ParserImplConstants.LBRACE, ParserImplConstants.LT, ParserImplConstants.VOID, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.FLOAT, ParserImplConstants.DOUBLE, ParserImplConstants.INT, ParserImplConstants.LONG, ParserImplConstants.BYTE, ParserImplConstants.SHORT, ParserImplConstants.BOOLEAN, ParserImplConstants.CHAR) != -1) {
			modifiers = parseModifiers();
			if (modifiers != null && contains(modifiers, SModifier.make(ModifierKeyword.Default)) && typeKind != TypeKind.Interface)
				problem = new BUProblem(Severity.ERROR, "Only interfaces can have default members");

			if (match(0, ParserImplConstants.LBRACE) != -1) {
				ret = parseInitializerDecl(modifiers);
				if (typeKind == TypeKind.Interface)
					ret = ret.withProblem(new BUProblem(Severity.ERROR, "An interface cannot have initializers"));

			} else if (match(0, ParserImplConstants.CLASS, ParserImplConstants.INTERFACE) != -1) {
				ret = parseClassOrInterfaceDecl(modifiers);
			} else if (match(0, ParserImplConstants.ENUM) != -1) {
				ret = parseEnumDecl(modifiers);
			} else if (match(0, ParserImplConstants.AT) != -1) {
				ret = parseAnnotationTypeDecl(modifiers);
			} else if (matchClassOrInterfaceBodyDecl1(0) != -1) {
				ret = parseConstructorDecl(modifiers);
				if (typeKind == TypeKind.Interface)
					ret = ret.withProblem(new BUProblem(Severity.ERROR, "An interface cannot have constructors"));

			} else if (matchClassOrInterfaceBodyDecl4(0) != -1) {
				ret = parseFieldDecl(modifiers);
			} else if (match(0, ParserImplConstants.LT, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.VOID) != -1) {
				ret = parseMethodDecl(modifiers);
			} else {
				throw produceParseException(ParserImplConstants.LT, ParserImplConstants.VOID, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.AT, ParserImplConstants.ENUM, ParserImplConstants.INTERFACE, ParserImplConstants.CLASS, ParserImplConstants.LBRACE);
			}
		} else {
			throw produceParseException(ParserImplConstants.VOLATILE, ParserImplConstants.TRANSIENT, ParserImplConstants.NATIVE, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.AT, ParserImplConstants.STRICTFP, ParserImplConstants.PUBLIC, ParserImplConstants.PRIVATE, ParserImplConstants.PROTECTED, ParserImplConstants._DEFAULT, ParserImplConstants.ABSTRACT, ParserImplConstants.FINAL, ParserImplConstants.STATIC, ParserImplConstants.LT, ParserImplConstants.SHORT, ParserImplConstants.INT, ParserImplConstants.CHAR, ParserImplConstants.BYTE, ParserImplConstants.BOOLEAN, ParserImplConstants.DOUBLE, ParserImplConstants.LONG, ParserImplConstants.FLOAT, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.VOID, ParserImplConstants.ENUM, ParserImplConstants.INTERFACE, ParserImplConstants.CLASS, ParserImplConstants.LBRACE, ParserImplConstants.SEMICOLON);
		}
		return ret.withProblem(problem);
	}

	/* Sequence(
			NonTerminal(TypeParameters)
		) */
	private int matchClassOrInterfaceBodyDecl1_1_1(int lookahead) {
		lookahead = matchTypeParameters(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			NonTerminal(TypeParameters)
		) */
	private int matchClassOrInterfaceBodyDecl1_1(int lookahead) {
		int newLookahead;
		newLookahead = matchClassOrInterfaceBodyDecl1_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			ZeroOrOne(
				NonTerminal(TypeParameters)
			)
			NonTerminal(Name)
			Terminal("LPAREN")
		) */
	private int matchClassOrInterfaceBodyDecl1(int lookahead) {
		lookahead = matchClassOrInterfaceBodyDecl1_1(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("LBRACKET")
			Terminal("RBRACKET")
		) */
	private int matchClassOrInterfaceBodyDecl4_3_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.LBRACKET);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RBRACKET);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrMore(
			Terminal("LBRACKET")
			Terminal("RBRACKET")
		) */
	private int matchClassOrInterfaceBodyDecl4_3(int lookahead) {
		int newLookahead;
		newLookahead = matchClassOrInterfaceBodyDecl4_3_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchClassOrInterfaceBodyDecl4_3_1(lookahead);
		}
		return lookahead;
	}

	/* Choice(
			Terminal("COMMA")
			Terminal("ASSIGN")
			Terminal("SEMICOLON")
		) */
	private int matchClassOrInterfaceBodyDecl4_4(int lookahead) {
		int newLookahead;
		newLookahead = match(lookahead, ParserImplConstants.COMMA);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = match(lookahead, ParserImplConstants.ASSIGN);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = match(lookahead, ParserImplConstants.SEMICOLON);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			NonTerminal(Type)
			NonTerminal(Name)
			ZeroOrMore(
				Terminal("LBRACKET")
				Terminal("RBRACKET")
			)
			Choice(
				Terminal("COMMA")
				Terminal("ASSIGN")
				Terminal("SEMICOLON")
			)
		) */
	private int matchClassOrInterfaceBodyDecl4(int lookahead) {
		lookahead = matchType(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchClassOrInterfaceBodyDecl4_3(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchClassOrInterfaceBodyDecl4_4(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	public BUTree<SFieldDecl> parseFieldDecl(BUTree<SNodeList> modifiers) throws ParseException {
		BUTree<? extends SType> type;
		BUTree<SNodeList> variables = emptyList();
		BUTree<SVariableDeclarator> val;
		type = parseType(null);
		variables = parseVariableDeclarators();
		parse(ParserImplConstants.SEMICOLON);
		return dress(SFieldDecl.make(modifiers, type, variables));
	}

	public BUTree<SLocalVariableDecl> parseVariableDecl(BUTree<SNodeList> modifiers) throws ParseException {
		BUTree<? extends SType> type;
		BUTree<SNodeList> variables = emptyList();
		type = parseType(null);
		variables = parseVariableDeclarators();
		return dress(SLocalVariableDecl.make(modifiers, type, variables));
	}

	public BUTree<SNodeList> parseVariableDeclarators() throws ParseException {
		BUTree<SNodeList> variables = emptyList();
		BUTree<SVariableDeclarator> val;
		val = parseVariableDeclarator();
		variables = append(variables, val);
		while (match(0, ParserImplConstants.COMMA) != -1) {
			parse(ParserImplConstants.COMMA);
			val = parseVariableDeclarator();
			variables = append(variables, val);
		}
		return variables;
	}

	public BUTree<SVariableDeclarator> parseVariableDeclarator() throws ParseException {
		BUTree<SVariableDeclaratorId> id;
		BUTree<SNodeOption> init = none();
		BUTree<? extends SExpr> initExpr = null;
		run();
		id = parseVariableDeclaratorId();
		if (match(0, ParserImplConstants.ASSIGN) != -1) {
			parse(ParserImplConstants.ASSIGN);
			initExpr = parseVariableInitializer();
			init = optionOf(initExpr);
		}
		return dress(SVariableDeclarator.make(id, init));
	}

	public BUTree<SVariableDeclaratorId> parseVariableDeclaratorId() throws ParseException {
		BUTree<SName> name;
		BUTree<SNodeList> arrayDims;
		run();
		name = parseName();
		arrayDims = parseArrayDims();
		return dress(SVariableDeclaratorId.make(name, arrayDims));
	}

	public BUTree<SNodeList> parseArrayDims() throws ParseException {
		BUTree<SNodeList> arrayDims = emptyList();
		BUTree<SNodeList> annotations;
		while (matchArrayDims1(0) != -1) {
			run();
			annotations = parseAnnotations();
			parse(ParserImplConstants.LBRACKET);
			parse(ParserImplConstants.RBRACKET);
			arrayDims = append(arrayDims, dress(SArrayDim.make(annotations)));
		}
		return arrayDims;
	}

	/* Sequence(
			NonTerminal(Annotations)
			Terminal("LBRACKET")
			Terminal("RBRACKET")
		) */
	private int matchArrayDims1(int lookahead) {
		lookahead = matchAnnotations(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.LBRACKET);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RBRACKET);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	public BUTree<? extends SExpr> parseVariableInitializer() throws ParseException {
		BUTree<? extends SExpr> ret;
		if (match(0, ParserImplConstants.LBRACE) != -1) {
			ret = parseArrayInitializer();
		} else if (match(0, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.DECR, ParserImplConstants.INCR, ParserImplConstants.LPAREN, ParserImplConstants.FLOAT, ParserImplConstants.DOUBLE, ParserImplConstants.BYTE, ParserImplConstants.SHORT, ParserImplConstants.INT, ParserImplConstants.LONG, ParserImplConstants.BOOLEAN, ParserImplConstants.CHAR, ParserImplConstants.VOID, ParserImplConstants.LT, ParserImplConstants.NEW, ParserImplConstants.THIS, ParserImplConstants.SUPER, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.TRUE, ParserImplConstants.FALSE, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.STRING_LITERAL, ParserImplConstants.NULL, ParserImplConstants.BANG, ParserImplConstants.TILDE, ParserImplConstants.PLUS, ParserImplConstants.MINUS) != -1) {
			ret = parseExpression();
		} else {
			throw produceParseException(ParserImplConstants.BANG, ParserImplConstants.TILDE, ParserImplConstants.LPAREN, ParserImplConstants.SUPER, ParserImplConstants.NEW, ParserImplConstants.VOID, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.LT, ParserImplConstants.TRUE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.NULL, ParserImplConstants.FALSE, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.THIS, ParserImplConstants.INCR, ParserImplConstants.DECR, ParserImplConstants.PLUS, ParserImplConstants.MINUS, ParserImplConstants.LBRACE);
		}
		return ret;
	}

	public BUTree<SArrayInitializerExpr> parseArrayInitializer() throws ParseException {
		BUTree<SNodeList> values = emptyList();
		BUTree<? extends SExpr> val;
		boolean trailingComma = false;
		run();
		parse(ParserImplConstants.LBRACE);
		if (match(0, ParserImplConstants.LPAREN, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.DECR, ParserImplConstants.INCR, ParserImplConstants.MINUS, ParserImplConstants.PLUS, ParserImplConstants.SUPER, ParserImplConstants.NEW, ParserImplConstants.VOID, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.LT, ParserImplConstants.FALSE, ParserImplConstants.NULL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.STRING_LITERAL, ParserImplConstants.TRUE, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.THIS, ParserImplConstants.TILDE, ParserImplConstants.BANG, ParserImplConstants.LBRACE) != -1) {
			val = parseVariableInitializer();
			values = append(values, val);
			while (matchArrayInitializer1(0) != -1) {
				parse(ParserImplConstants.COMMA);
				val = parseVariableInitializer();
				values = append(values, val);
			}
		}
		if (match(0, ParserImplConstants.COMMA) != -1) {
			parse(ParserImplConstants.COMMA);
			trailingComma = true;
		}
		parse(ParserImplConstants.RBRACE);
		return dress(SArrayInitializerExpr.make(values, trailingComma));
	}

	/* ZeroOrMore(
			LookAhead(2)
			Terminal("COMMA")
			NonTerminal(val, VariableInitializer)
			Action({
				values = append(values, val);
			})
		) */
	private int matchArrayInitializer1(int lookahead) {
		if (match(0, ParserImplConstants.COMMA) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		return -1;
	}

	public BUTree<SMethodDecl> parseMethodDecl(BUTree<SNodeList> modifiers) throws ParseException {
		BUTree<SNodeList> typeParameters = null;
		BUTree<? extends SType> type;
		BUTree<SName> name;
		BUTree<SNodeList> parameters;
		BUTree<SNodeList> arrayDims;
		BUTree<SNodeList> throwsClause = null;
		BUTree<SBlockStmt> block = null;
		BUProblem problem = null;
		if (match(0, ParserImplConstants.LT) != -1) {
			typeParameters = parseTypeParameters();
		}
		type = parseResultType();
		name = parseName();
		parameters = parseFormalParameters();
		arrayDims = parseArrayDims();
		if (match(0, ParserImplConstants.THROWS) != -1) {
			throwsClause = parseThrowsClause();
		}
		if (match(0, ParserImplConstants.LBRACE) != -1) {
			block = parseBlock();
		} else if (match(0, ParserImplConstants.SEMICOLON) != -1) {
			parse(ParserImplConstants.SEMICOLON);
			if (modifiers != null && contains(modifiers, SModifier.make(ModifierKeyword.Default)))
				problem = new BUProblem(Severity.ERROR, "Default methods must have a body");

		} else {
			throw produceParseException(ParserImplConstants.SEMICOLON, ParserImplConstants.LBRACE);
		}
		return dress(SMethodDecl.make(modifiers, ensureNotNull(typeParameters), type, name, parameters, arrayDims, ensureNotNull(throwsClause), optionOf(block))).withProblem(problem);
	}

	public BUTree<SNodeList> parseFormalParameters() throws ParseException {
		BUTree<SNodeList> ret = null;
		BUTree<SFormalParameter> par;
		parse(ParserImplConstants.LPAREN);
		if (match(0, ParserImplConstants.NODE_LIST_VARIABLE, ParserImplConstants.PUBLIC, ParserImplConstants.PROTECTED, ParserImplConstants.PRIVATE, ParserImplConstants.STRICTFP, ParserImplConstants.AT, ParserImplConstants.TRANSIENT, ParserImplConstants.VOLATILE, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.NATIVE, ParserImplConstants.ABSTRACT, ParserImplConstants._DEFAULT, ParserImplConstants.STATIC, ParserImplConstants.FINAL, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.INT, ParserImplConstants.LONG, ParserImplConstants.FLOAT, ParserImplConstants.DOUBLE, ParserImplConstants.BOOLEAN, ParserImplConstants.CHAR, ParserImplConstants.BYTE, ParserImplConstants.SHORT) != -1) {
			ret = parseFormalParameterList();
		}
		parse(ParserImplConstants.RPAREN);
		return ensureNotNull(ret);
	}

	public BUTree<SNodeList> parseFormalParameterList() throws ParseException {
		BUTree<SNodeList> ret = null;
		BUTree<SFormalParameter> par;
		if (match(0, ParserImplConstants.AT, ParserImplConstants.STRICTFP, ParserImplConstants.NATIVE, ParserImplConstants.SYNCHRONIZED, ParserImplConstants._DEFAULT, ParserImplConstants.ABSTRACT, ParserImplConstants.PRIVATE, ParserImplConstants.PROTECTED, ParserImplConstants.VOLATILE, ParserImplConstants.TRANSIENT, ParserImplConstants.FINAL, ParserImplConstants.STATIC, ParserImplConstants.PUBLIC, ParserImplConstants.BOOLEAN, ParserImplConstants.CHAR, ParserImplConstants.BYTE, ParserImplConstants.SHORT, ParserImplConstants.INT, ParserImplConstants.LONG, ParserImplConstants.FLOAT, ParserImplConstants.DOUBLE, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER) != -1) {
			par = parseFormalParameter();
			ret = append(ret, par);
			while (match(0, ParserImplConstants.COMMA) != -1) {
				parse(ParserImplConstants.COMMA);
				par = parseFormalParameter();
				ret = append(ret, par);
			}
		} else if (quotesMode) {
			ret = parseNodeListVar();
		} else {
			throw produceParseException(ParserImplConstants.NODE_LIST_VARIABLE, ParserImplConstants.PROTECTED, ParserImplConstants.PRIVATE, ParserImplConstants.PUBLIC, ParserImplConstants.STRICTFP, ParserImplConstants.AT, ParserImplConstants.STATIC, ParserImplConstants.FINAL, ParserImplConstants.ABSTRACT, ParserImplConstants._DEFAULT, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.NATIVE, ParserImplConstants.TRANSIENT, ParserImplConstants.VOLATILE, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE);
		}
		return ret;
	}

	public BUTree<SFormalParameter> parseFormalParameter() throws ParseException {
		BUTree<SNodeList> modifiers;
		BUTree<? extends SType> type;
		boolean isVarArg = false;
		BUTree<SVariableDeclaratorId> id;
		run();
		modifiers = parseModifiers();
		type = parseType(null);
		if (match(0, ParserImplConstants.ELLIPSIS) != -1) {
			parse(ParserImplConstants.ELLIPSIS);
			isVarArg = true;
		}
		id = parseVariableDeclaratorId();
		return dress(SFormalParameter.make(modifiers, type, isVarArg, id));
	}

	public BUTree<SNodeList> parseThrowsClause() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<SQualifiedType> cit;
		parse(ParserImplConstants.THROWS);
		cit = parseAnnotatedQualifiedType();
		ret = append(ret, cit);
		while (match(0, ParserImplConstants.COMMA) != -1) {
			parse(ParserImplConstants.COMMA);
			cit = parseAnnotatedQualifiedType();
			ret = append(ret, cit);
		}
		return ret;
	}

	public BUTree<SConstructorDecl> parseConstructorDecl(BUTree<SNodeList> modifiers) throws ParseException {
		BUTree<SNodeList> typeParameters = null;
		BUTree<SName> name;
		BUTree<SNodeList> parameters;
		BUTree<SNodeList> throwsClause = null;
		BUTree<SExplicitConstructorInvocationStmt> exConsInv = null;
		BUTree<SBlockStmt> block;
		BUTree<SNodeList> stmts = emptyList();
		BUTree<? extends SStmt> stmt;
		if (match(0, ParserImplConstants.LT) != -1) {
			typeParameters = parseTypeParameters();
		}
		name = parseName();
		parameters = parseFormalParameters();
		if (match(0, ParserImplConstants.THROWS) != -1) {
			throwsClause = parseThrowsClause();
		}
		run();
		parse(ParserImplConstants.LBRACE);
		if (match(0, ParserImplConstants.TRY, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.THROW, ParserImplConstants.RETURN, ParserImplConstants.CONTINUE, ParserImplConstants.BREAK, ParserImplConstants.FOR, ParserImplConstants.DO, ParserImplConstants.WHILE, ParserImplConstants.IF, ParserImplConstants.SWITCH, ParserImplConstants.INCR, ParserImplConstants.DECR, ParserImplConstants.FLOAT, ParserImplConstants.DOUBLE, ParserImplConstants.INT, ParserImplConstants.LONG, ParserImplConstants.BYTE, ParserImplConstants.SHORT, ParserImplConstants.BOOLEAN, ParserImplConstants.CHAR, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.VOID, ParserImplConstants.LT, ParserImplConstants.STRING_LITERAL, ParserImplConstants.TRUE, ParserImplConstants.FALSE, ParserImplConstants.NULL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.THIS, ParserImplConstants.SUPER, ParserImplConstants.NEW, ParserImplConstants.LPAREN, ParserImplConstants.SEMICOLON, ParserImplConstants.LBRACE, ParserImplConstants.ASSERT, ParserImplConstants.PROTECTED, ParserImplConstants.PUBLIC, ParserImplConstants.ABSTRACT, ParserImplConstants.PRIVATE, ParserImplConstants.NATIVE, ParserImplConstants.AT, ParserImplConstants.STRICTFP, ParserImplConstants.FINAL, ParserImplConstants.STATIC, ParserImplConstants.VOLATILE, ParserImplConstants.TRANSIENT, ParserImplConstants.INTERFACE, ParserImplConstants.CLASS, ParserImplConstants.NODE_LIST_VARIABLE) != -1) {
			if (matchConstructorDecl1(0) != -1) {
				if (matchConstructorDecl2(0) != -1) {
					stmt = parseExplicitConstructorInvocation();
					stmts = append(stmts, stmt);
				} else if (matchConstructorDecl3(0) != -1) {
					stmt = parseBlockStatement();
					stmts = append(stmts, stmt);
				} else {
					throw produceParseException(ParserImplConstants.PUBLIC, ParserImplConstants.AT, ParserImplConstants.NATIVE, ParserImplConstants.STRICTFP, ParserImplConstants.VOLATILE, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.FINAL, ParserImplConstants.TRANSIENT, ParserImplConstants.ABSTRACT, ParserImplConstants.STATIC, ParserImplConstants.PROTECTED, ParserImplConstants.PRIVATE, ParserImplConstants.INT, ParserImplConstants.LONG, ParserImplConstants.FLOAT, ParserImplConstants.DOUBLE, ParserImplConstants.BOOLEAN, ParserImplConstants.CHAR, ParserImplConstants.BYTE, ParserImplConstants.SHORT, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.TRY, ParserImplConstants.CONTINUE, ParserImplConstants.RETURN, ParserImplConstants.THROW, ParserImplConstants.WHILE, ParserImplConstants.DO, ParserImplConstants.FOR, ParserImplConstants.BREAK, ParserImplConstants.SEMICOLON, ParserImplConstants.NEW, ParserImplConstants.VOID, ParserImplConstants.LT, ParserImplConstants.LPAREN, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.NULL, ParserImplConstants.FALSE, ParserImplConstants.TRUE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.THIS, ParserImplConstants.SUPER, ParserImplConstants.DECR, ParserImplConstants.INCR, ParserImplConstants.SWITCH, ParserImplConstants.IF, ParserImplConstants.ASSERT, ParserImplConstants.LBRACE, ParserImplConstants.CLASS, ParserImplConstants.INTERFACE);
				}
				while (matchConstructorDecl4(0) != -1) {
					stmt = parseBlockStatement();
					stmts = append(stmts, stmt);
				}
			} else if (quotesMode) {
				stmts = parseNodeListVar();
			} else {
				throw produceParseException(ParserImplConstants.NODE_LIST_VARIABLE, ParserImplConstants.PUBLIC, ParserImplConstants.PROTECTED, ParserImplConstants.PRIVATE, ParserImplConstants.ABSTRACT, ParserImplConstants.STATIC, ParserImplConstants.FINAL, ParserImplConstants.TRANSIENT, ParserImplConstants.VOLATILE, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.NATIVE, ParserImplConstants.STRICTFP, ParserImplConstants.AT, ParserImplConstants.CLASS, ParserImplConstants.INTERFACE, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.DOUBLE, ParserImplConstants.LBRACE, ParserImplConstants.SEMICOLON, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.FALSE, ParserImplConstants.NULL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.STRING_LITERAL, ParserImplConstants.TRUE, ParserImplConstants.THIS, ParserImplConstants.VOID, ParserImplConstants.SUPER, ParserImplConstants.NEW, ParserImplConstants.LPAREN, ParserImplConstants.LT, ParserImplConstants.INCR, ParserImplConstants.DECR, ParserImplConstants.SWITCH, ParserImplConstants.ASSERT, ParserImplConstants.BREAK, ParserImplConstants.CONTINUE, ParserImplConstants.RETURN, ParserImplConstants.THROW, ParserImplConstants.IF, ParserImplConstants.WHILE, ParserImplConstants.DO, ParserImplConstants.FOR, ParserImplConstants.TRY);
			}
		}
		parse(ParserImplConstants.RBRACE);
		block = dress(SBlockStmt.make(stmts));
		return dress(SConstructorDecl.make(modifiers, ensureNotNull(typeParameters), name, parameters, ensureNotNull(throwsClause), block));
	}

	/* Sequence(
			LookAhead(2)
			Choice(
				Sequence(
					LookAhead(
						NonTerminal(ExplicitConstructorInvocation)
					)
					NonTerminal(stmt, ExplicitConstructorInvocation)
					Action({
						stmts = append(stmts, stmt);
					})
				)
				Sequence(
					LookAhead(2)
					NonTerminal(stmt, BlockStatement)
					Action({
						stmts = append(stmts, stmt);
					})
				)
			)
			ZeroOrMore(
				LookAhead(2)
				NonTerminal(stmt, BlockStatement)
				Action({
					stmts = append(stmts, stmt);
				})
			)
		) */
	private int matchConstructorDecl1(int lookahead) {
		if (match(0, ParserImplConstants.VOLATILE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.NEW) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.THROW) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.LT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.HOOK) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_LIST_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.GT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.INTEGER_LITERAL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.DO) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THROW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DO) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RETURN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BREAK) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRY) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IF) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SYNCHRONIZED) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSERT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FOR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CONTINUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SWITCH) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.WHILE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACE) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.CLASS) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.STATIC) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.INTERFACE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.SEMICOLON) != -1) {
			if (match(1, ParserImplConstants.VOLATILE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THROW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DO) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STATIC) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BREAK) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRY) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IF) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SYNCHRONIZED) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FOR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FINAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PUBLIC) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SWITCH) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRANSIENT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NATIVE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ABSTRACT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRICTFP) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RETURN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PROTECTED) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSERT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PRIVATE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CONTINUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.WHILE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.BREAK) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.BYTE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.TRY) != -1) {
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACE) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.IF) != -1) {
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.SYNCHRONIZED) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.NULL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.CHARACTER_LITERAL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.FOR) != -1) {
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.LPAREN) != -1) {
			if (match(1, ParserImplConstants.VOLATILE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NATIVE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ABSTRACT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants._DEFAULT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRICTFP) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STATIC) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PROTECTED) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SYNCHRONIZED) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FINAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_LIST_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PRIVATE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PUBLIC) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRANSIENT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.TRUE) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.LONG_LITERAL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.FINAL) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.BOOLEAN) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.DECR) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.AT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.THIS) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.PUBLIC) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.SWITCH) != -1) {
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.VOID) != -1) {
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.TRANSIENT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.LBRACE) != -1) {
			if (match(1, ParserImplConstants.VOLATILE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THROW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DO) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STATIC) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BREAK) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRY) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IF) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SYNCHRONIZED) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FOR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FINAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RBRACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PUBLIC) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SWITCH) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRANSIENT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NATIVE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ABSTRACT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRICTFP) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RETURN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PROTECTED) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSERT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_LIST_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PRIVATE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CONTINUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.WHILE) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.NODE_VARIABLE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ARROW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.COLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.FLOAT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.NATIVE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.CHAR) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.STRICTFP) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.ABSTRACT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.INT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.RETURN) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.IDENTIFIER) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ARROW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.COLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.DOUBLE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.PROTECTED) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.LONG) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.SUPER) != -1) {
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.DOUBLE_LITERAL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.ASSERT) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.SHORT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.PRIVATE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.CONTINUE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.FLOAT_LITERAL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.INCR) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.WHILE) != -1) {
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.FALSE) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.STRING_LITERAL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		return -1;
	}

	/* Sequence(
			NonTerminal(ExplicitConstructorInvocation)
		) */
	private int matchConstructorDecl2(int lookahead) {
		lookahead = matchExplicitConstructorInvocation(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			LookAhead(2)
			NonTerminal(stmt, BlockStatement)
			Action({
				stmts = append(stmts, stmt);
			})
		) */
	private int matchConstructorDecl3(int lookahead) {
		if (match(0, ParserImplConstants.VOLATILE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.NEW) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.THROW) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.LT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.HOOK) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_LIST_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.GT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.INTEGER_LITERAL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.CLASS) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.DO) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THROW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DO) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RETURN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BREAK) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRY) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IF) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SYNCHRONIZED) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSERT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FOR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CONTINUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.WHILE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SWITCH) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACE) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.STATIC) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.INTERFACE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.SEMICOLON) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.BREAK) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.BYTE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.TRY) != -1) {
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACE) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.IF) != -1) {
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.SYNCHRONIZED) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.NULL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.CHARACTER_LITERAL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.FOR) != -1) {
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.LPAREN) != -1) {
			if (match(1, ParserImplConstants.VOLATILE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NATIVE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRICTFP) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants._DEFAULT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ABSTRACT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STATIC) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PROTECTED) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SYNCHRONIZED) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FINAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_LIST_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PRIVATE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PUBLIC) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRANSIENT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.TRUE) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.FINAL) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.LONG_LITERAL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.BOOLEAN) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.DECR) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.AT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.PUBLIC) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.THIS) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.SWITCH) != -1) {
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.VOID) != -1) {
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.TRANSIENT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.LBRACE) != -1) {
			if (match(1, ParserImplConstants.VOLATILE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THROW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DO) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STATIC) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BREAK) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRY) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IF) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SYNCHRONIZED) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FOR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FINAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RBRACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PUBLIC) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SWITCH) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRANSIENT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NATIVE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRICTFP) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ABSTRACT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RETURN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PROTECTED) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSERT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_LIST_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PRIVATE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CONTINUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.WHILE) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.NODE_VARIABLE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.COLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ARROW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.NATIVE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.FLOAT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.CHAR) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.STRICTFP) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.ABSTRACT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.INT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.RETURN) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.DOUBLE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.IDENTIFIER) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.COLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ARROW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.PROTECTED) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.LONG) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.SUPER) != -1) {
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.DOUBLE_LITERAL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.ASSERT) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.PRIVATE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.SHORT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.CONTINUE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.FLOAT_LITERAL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.INCR) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.WHILE) != -1) {
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.STRING_LITERAL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.FALSE) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		return -1;
	}

	/* ZeroOrMore(
			LookAhead(2)
			NonTerminal(stmt, BlockStatement)
			Action({
				stmts = append(stmts, stmt);
			})
		) */
	private int matchConstructorDecl4(int lookahead) {
		if (match(0, ParserImplConstants.VOLATILE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.NEW) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.THROW) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.LT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.HOOK) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_LIST_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.GT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.INTEGER_LITERAL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.CLASS) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.DO) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THROW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DO) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RETURN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BREAK) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRY) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IF) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SYNCHRONIZED) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSERT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FOR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CONTINUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.WHILE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SWITCH) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACE) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.STATIC) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.INTERFACE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.SEMICOLON) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.BREAK) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.BYTE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.TRY) != -1) {
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACE) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.IF) != -1) {
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.SYNCHRONIZED) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.NULL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.CHARACTER_LITERAL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.FOR) != -1) {
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.LPAREN) != -1) {
			if (match(1, ParserImplConstants.VOLATILE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NATIVE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRICTFP) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants._DEFAULT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ABSTRACT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STATIC) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PROTECTED) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SYNCHRONIZED) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FINAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_LIST_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PRIVATE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PUBLIC) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRANSIENT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.TRUE) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.FINAL) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.LONG_LITERAL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.BOOLEAN) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.DECR) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.AT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.PUBLIC) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.THIS) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.SWITCH) != -1) {
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.VOID) != -1) {
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.TRANSIENT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.LBRACE) != -1) {
			if (match(1, ParserImplConstants.VOLATILE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THROW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DO) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STATIC) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BREAK) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRY) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IF) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SYNCHRONIZED) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FOR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FINAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RBRACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PUBLIC) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SWITCH) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRANSIENT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NATIVE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRICTFP) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ABSTRACT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RETURN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PROTECTED) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSERT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_LIST_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PRIVATE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CONTINUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.WHILE) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.NODE_VARIABLE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.COLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ARROW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.NATIVE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.FLOAT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.CHAR) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.STRICTFP) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.ABSTRACT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.INT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.RETURN) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.DOUBLE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.IDENTIFIER) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.COLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ARROW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.PROTECTED) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.LONG) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.SUPER) != -1) {
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.DOUBLE_LITERAL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.ASSERT) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.PRIVATE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.SHORT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.CONTINUE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.FLOAT_LITERAL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.INCR) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.WHILE) != -1) {
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.STRING_LITERAL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.FALSE) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		return -1;
	}

	public BUTree<SExplicitConstructorInvocationStmt> parseExplicitConstructorInvocation() throws ParseException {
		boolean isThis = false;
		BUTree<SNodeList> args;
		BUTree<? extends SExpr> expr = null;
		BUTree<SNodeList> typeArgs = null;
		run();
		if (matchExplicitConstructorInvocation1(0) != -1) {
			if (match(0, ParserImplConstants.LT) != -1) {
				typeArgs = parseTypeArguments();
			}
			parse(ParserImplConstants.THIS);
			isThis = true;
			args = parseArguments();
			parse(ParserImplConstants.SEMICOLON);
		} else if (match(0, ParserImplConstants.LPAREN, ParserImplConstants.NEW, ParserImplConstants.SUPER, ParserImplConstants.THIS, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.NULL, ParserImplConstants.FALSE, ParserImplConstants.TRUE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.VOID, ParserImplConstants.BOOLEAN, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.DOUBLE) != -1) {
			if (matchExplicitConstructorInvocation4(0) != -1) {
				expr = parsePrimaryExpressionWithoutSuperSuffix();
				parse(ParserImplConstants.DOT);
			}
			if (match(0, ParserImplConstants.LT) != -1) {
				typeArgs = parseTypeArguments();
			}
			parse(ParserImplConstants.SUPER);
			args = parseArguments();
			parse(ParserImplConstants.SEMICOLON);
		} else {
			throw produceParseException(ParserImplConstants.SUPER, ParserImplConstants.THIS, ParserImplConstants.STRING_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.FALSE, ParserImplConstants.TRUE, ParserImplConstants.NULL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.LPAREN, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.VOID, ParserImplConstants.NEW);
		}
		return dress(SExplicitConstructorInvocationStmt.make(ensureNotNull(typeArgs), isThis, optionOf(expr), args));
	}

	/* Sequence(
			NonTerminal(TypeArguments)
		) */
	private int matchExplicitConstructorInvocation1_1_1(int lookahead) {
		lookahead = matchTypeArguments(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			NonTerminal(TypeArguments)
		) */
	private int matchExplicitConstructorInvocation1_1(int lookahead) {
		int newLookahead;
		newLookahead = matchExplicitConstructorInvocation1_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			ZeroOrOne(
				NonTerminal(TypeArguments)
			)
			Terminal("THIS")
			Terminal("LPAREN")
		) */
	private int matchExplicitConstructorInvocation1(int lookahead) {
		lookahead = matchExplicitConstructorInvocation1_1(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.THIS);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(PrimaryExpressionWithoutSuperSuffix)
			Terminal("DOT")
		) */
	private int matchExplicitConstructorInvocation4(int lookahead) {
		lookahead = matchPrimaryExpressionWithoutSuperSuffix(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.DOT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	public BUTree<SNodeList> parseStatements() throws ParseException {
		BUTree<SNodeList> ret = null;
		BUTree<? extends SStmt> stmt;
		if (matchStatements1(0) != -1) {
			if (match(0, ParserImplConstants.DO, ParserImplConstants.WHILE, ParserImplConstants.IF, ParserImplConstants.SWITCH, ParserImplConstants.RETURN, ParserImplConstants.CONTINUE, ParserImplConstants.BREAK, ParserImplConstants.FOR, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.TRUE, ParserImplConstants.FALSE, ParserImplConstants.NULL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.STRING_LITERAL, ParserImplConstants.VOID, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.DOUBLE, ParserImplConstants.BOOLEAN, ParserImplConstants.NEW, ParserImplConstants.SUPER, ParserImplConstants.THIS, ParserImplConstants.LPAREN, ParserImplConstants.LT, ParserImplConstants.DECR, ParserImplConstants.INCR, ParserImplConstants.SEMICOLON, ParserImplConstants.LBRACE, ParserImplConstants.ASSERT, ParserImplConstants.TRY, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.THROW, ParserImplConstants.PUBLIC, ParserImplConstants.PRIVATE, ParserImplConstants.PROTECTED, ParserImplConstants.AT, ParserImplConstants.VOLATILE, ParserImplConstants.STRICTFP, ParserImplConstants.NATIVE, ParserImplConstants.STATIC, ParserImplConstants.ABSTRACT, ParserImplConstants.TRANSIENT, ParserImplConstants.FINAL, ParserImplConstants.CLASS, ParserImplConstants.INTERFACE) != -1) {
				do {
					stmt = parseBlockStatement();
					ret = append(ret, stmt);
				}
				while (match(0, ParserImplConstants.DO, ParserImplConstants.WHILE, ParserImplConstants.IF, ParserImplConstants.SWITCH, ParserImplConstants.RETURN, ParserImplConstants.CONTINUE, ParserImplConstants.BREAK, ParserImplConstants.FOR, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.TRUE, ParserImplConstants.FALSE, ParserImplConstants.NULL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.STRING_LITERAL, ParserImplConstants.VOID, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.DOUBLE, ParserImplConstants.BOOLEAN, ParserImplConstants.NEW, ParserImplConstants.SUPER, ParserImplConstants.THIS, ParserImplConstants.LPAREN, ParserImplConstants.LT, ParserImplConstants.DECR, ParserImplConstants.INCR, ParserImplConstants.SEMICOLON, ParserImplConstants.LBRACE, ParserImplConstants.ASSERT, ParserImplConstants.TRY, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.THROW, ParserImplConstants.PUBLIC, ParserImplConstants.PRIVATE, ParserImplConstants.PROTECTED, ParserImplConstants.AT, ParserImplConstants.VOLATILE, ParserImplConstants.STRICTFP, ParserImplConstants.NATIVE, ParserImplConstants.STATIC, ParserImplConstants.ABSTRACT, ParserImplConstants.TRANSIENT, ParserImplConstants.FINAL, ParserImplConstants.CLASS, ParserImplConstants.INTERFACE) != -1);
			} else if (quotesMode) {
				ret = parseNodeListVar();
			} else {
				throw produceParseException(ParserImplConstants.NODE_LIST_VARIABLE, ParserImplConstants.TRY, ParserImplConstants.DO, ParserImplConstants.WHILE, ParserImplConstants.BREAK, ParserImplConstants.FOR, ParserImplConstants.RETURN, ParserImplConstants.CONTINUE, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.THROW, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LBRACE, ParserImplConstants.ASSERT, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.NULL, ParserImplConstants.FALSE, ParserImplConstants.TRUE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.SUPER, ParserImplConstants.THIS, ParserImplConstants.CHAR, ParserImplConstants.BYTE, ParserImplConstants.BOOLEAN, ParserImplConstants.LONG, ParserImplConstants.FLOAT, ParserImplConstants.SHORT, ParserImplConstants.INT, ParserImplConstants.DOUBLE, ParserImplConstants.VOID, ParserImplConstants.NEW, ParserImplConstants.LT, ParserImplConstants.LPAREN, ParserImplConstants.INCR, ParserImplConstants.DECR, ParserImplConstants.SEMICOLON, ParserImplConstants.IF, ParserImplConstants.SWITCH, ParserImplConstants.NATIVE, ParserImplConstants.STRICTFP, ParserImplConstants.AT, ParserImplConstants.PUBLIC, ParserImplConstants.PROTECTED, ParserImplConstants.PRIVATE, ParserImplConstants.ABSTRACT, ParserImplConstants.STATIC, ParserImplConstants.FINAL, ParserImplConstants.TRANSIENT, ParserImplConstants.VOLATILE, ParserImplConstants.INTERFACE, ParserImplConstants.CLASS);
			}
		}
		return ensureNotNull(ret);
	}

	/* ZeroOrOne(
			LookAhead(2)
			Choice(
				OneOrMore(
					NonTerminal(stmt, BlockStatement)
					Action({
						ret = append(ret, stmt);
					})
				)
				Sequence(
					LookAhead(					quotesMode
	)
					NonTerminal(ret, NodeListVar)
				)
			)
		) */
	private int matchStatements1(int lookahead) {
		if (match(0, ParserImplConstants.VOLATILE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.NEW) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.THROW) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.LT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.HOOK) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_LIST_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.GT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.INTEGER_LITERAL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.DO) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THROW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DO) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RETURN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BREAK) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRY) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IF) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SYNCHRONIZED) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSERT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FOR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CONTINUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.WHILE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SWITCH) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACE) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.CLASS) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.STATIC) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.INTERFACE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.SEMICOLON) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.BREAK) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.BYTE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.TRY) != -1) {
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACE) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.IF) != -1) {
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.SYNCHRONIZED) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.NULL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.CHARACTER_LITERAL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.LPAREN) != -1) {
			if (match(1, ParserImplConstants.VOLATILE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NATIVE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRICTFP) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants._DEFAULT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ABSTRACT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STATIC) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PROTECTED) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SYNCHRONIZED) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FINAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_LIST_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PRIVATE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PUBLIC) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRANSIENT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.FOR) != -1) {
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.TRUE) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.LONG_LITERAL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.FINAL) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.BOOLEAN) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.DECR) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.AT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.THIS) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.PUBLIC) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.SWITCH) != -1) {
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.VOID) != -1) {
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.TRANSIENT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.LBRACE) != -1) {
			if (match(1, ParserImplConstants.VOLATILE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THROW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DO) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STATIC) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BREAK) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRY) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IF) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SYNCHRONIZED) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FOR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FINAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RBRACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PUBLIC) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SWITCH) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRANSIENT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NATIVE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRICTFP) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ABSTRACT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RETURN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PROTECTED) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSERT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_LIST_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PRIVATE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CONTINUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.WHILE) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.NODE_VARIABLE) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.COLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ARROW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.FLOAT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.NATIVE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.CHAR) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.ABSTRACT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.STRICTFP) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.INT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.RETURN) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.DOUBLE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.IDENTIFIER) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.COLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ARROW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.PROTECTED) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.LONG) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.SUPER) != -1) {
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.DOUBLE_LITERAL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.ASSERT) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.NODE_LIST_VARIABLE) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.SHORT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.PRIVATE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.CONTINUE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.FLOAT_LITERAL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.INCR) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.WHILE) != -1) {
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.FALSE) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.STRING_LITERAL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		return -1;
	}

	public BUTree<SInitializerDecl> parseInitializerDecl(BUTree<SNodeList> modifiers) throws ParseException {
		BUTree<SBlockStmt> block;
		block = parseBlock();
		return dress(SInitializerDecl.make(modifiers, block));
	}

	public BUTree<? extends SType> parseType(BUTree<SNodeList> annotations) throws ParseException {
		BUTree<? extends SType> primitiveType = null;
		BUTree<? extends SReferenceType> type = null;
		BUTree<SNodeList> arrayDims;
		if (match(0, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN) != -1) {
			primitiveType = parsePrimitiveType(annotations);
			if (matchType1(0) != -1) {
				lateRun();
				arrayDims = parseArrayDimsMandatory();
				type = dress(SArrayType.make(primitiveType, arrayDims));
			}
		} else if (match(0, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER) != -1) {
			type = parseQualifiedType(annotations);
			if (matchType2(0) != -1) {
				lateRun();
				arrayDims = parseArrayDimsMandatory();
				type = dress(SArrayType.make(type, arrayDims));
			}
		} else {
			throw produceParseException(ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN);
		}
		return type == null ? primitiveType : type;
	}

	/* Sequence(
			NonTerminal(Annotations)
			Terminal("LBRACKET")
		) */
	private int matchType1(int lookahead) {
		lookahead = matchAnnotations(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.LBRACKET);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(Annotations)
			Terminal("LBRACKET")
		) */
	private int matchType2(int lookahead) {
		lookahead = matchAnnotations(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.LBRACKET);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	public BUTree<? extends SReferenceType> parseReferenceType(BUTree<SNodeList> annotations) throws ParseException {
		BUTree<? extends SType> primitiveType;
		BUTree<? extends SReferenceType> type;
		BUTree<SNodeList> arrayDims;
		if (match(0, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN) != -1) {
			primitiveType = parsePrimitiveType(annotations);
			lateRun();
			arrayDims = parseArrayDimsMandatory();
			type = dress(SArrayType.make(primitiveType, arrayDims));
		} else if (match(0, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER) != -1) {
			type = parseQualifiedType(annotations);
			if (matchReferenceType1(0) != -1) {
				lateRun();
				arrayDims = parseArrayDimsMandatory();
				type = dress(SArrayType.make(type, arrayDims));
			}
		} else {
			throw produceParseException(ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN);
		}
		return type;
	}

	/* Sequence(
			NonTerminal(Annotations)
			Terminal("LBRACKET")
		) */
	private int matchReferenceType1(int lookahead) {
		lookahead = matchAnnotations(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.LBRACKET);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	public BUTree<SQualifiedType> parseQualifiedType(BUTree<SNodeList> annotations) throws ParseException {
		BUTree<SNodeOption> scope = none();
		BUTree<SQualifiedType> ret;
		BUTree<SName> name;
		BUTree<SNodeList> typeArgs = null;
		if (annotations == null) {
			run();
			annotations = emptyList();
		}
		name = parseName();
		if (matchQualifiedType1(0) != -1) {
			typeArgs = parseTypeArgumentsOrDiamond();
		}
		ret = dress(SQualifiedType.make(annotations, scope, name, optionOf(typeArgs)));
		while (matchQualifiedType2(0) != -1) {
			lateRun();
			parse(ParserImplConstants.DOT);
			scope = optionOf(ret);
			annotations = parseAnnotations();
			name = parseName();
			if (matchQualifiedType3(0) != -1) {
				typeArgs = parseTypeArgumentsOrDiamond();
			}
			ret = dress(SQualifiedType.make(annotations, scope, name, optionOf(typeArgs)));
		}
		return ret;
	}

	/* ZeroOrOne(
			LookAhead(2)
			NonTerminal(typeArgs, TypeArgumentsOrDiamond)
		) */
	private int matchQualifiedType1(int lookahead) {
		if (match(0, ParserImplConstants.LT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.HOOK) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_LIST_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.GT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		return -1;
	}

	/* ZeroOrMore(
			LookAhead(2)
			Action({
				lateRun();
			})
			Terminal("DOT")
			Action({
				scope = optionOf(ret);
			})
			NonTerminal(annotations, Annotations)
			NonTerminal(name, Name)
			ZeroOrOne(
				LookAhead(2)
				NonTerminal(typeArgs, TypeArgumentsOrDiamond)
			)
			Action({
				ret = dress(SQualifiedType.make(annotations, scope, name, optionOf(typeArgs)));
			})
		) */
	private int matchQualifiedType2(int lookahead) {
		if (match(0, ParserImplConstants.DOT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		return -1;
	}

	/* ZeroOrOne(
			LookAhead(2)
			NonTerminal(typeArgs, TypeArgumentsOrDiamond)
		) */
	private int matchQualifiedType3(int lookahead) {
		if (match(0, ParserImplConstants.LT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.HOOK) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_LIST_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.GT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		return -1;
	}

	public BUTree<SNodeList> parseTypeArguments() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<? extends SType> type;
		parse(ParserImplConstants.LT);
		if (match(0, ParserImplConstants.AT, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.HOOK, ParserImplConstants.NODE_LIST_VARIABLE) != -1) {
			ret = parseTypeArgumentList();
		}
		parse(ParserImplConstants.GT);
		return ret;
	}

	public BUTree<SNodeList> parseTypeArgumentsOrDiamond() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<? extends SType> type;
		parse(ParserImplConstants.LT);
		if (match(0, ParserImplConstants.AT, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.HOOK, ParserImplConstants.NODE_LIST_VARIABLE) != -1) {
			ret = parseTypeArgumentList();
		}
		parse(ParserImplConstants.GT);
		return ret;
	}

	public BUTree<SNodeList> parseTypeArgumentList() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<? extends SType> type;
		if (match(0, ParserImplConstants.AT, ParserImplConstants.BOOLEAN, ParserImplConstants.CHAR, ParserImplConstants.BYTE, ParserImplConstants.SHORT, ParserImplConstants.INT, ParserImplConstants.LONG, ParserImplConstants.FLOAT, ParserImplConstants.DOUBLE, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.HOOK) != -1) {
			type = parseTypeArgument();
			ret = append(ret, type);
			while (match(0, ParserImplConstants.COMMA) != -1) {
				parse(ParserImplConstants.COMMA);
				type = parseTypeArgument();
				ret = append(ret, type);
			}
			return ret;
		} else if (quotesMode) {
			parse(ParserImplConstants.NODE_LIST_VARIABLE);
			return makeVar();
		} else {
			throw produceParseException(ParserImplConstants.NODE_LIST_VARIABLE, ParserImplConstants.AT, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.HOOK);
		}
	}

	public BUTree<? extends SType> parseTypeArgument() throws ParseException {
		BUTree<? extends SType> ret;
		BUTree<SNodeList> annotations = null;
		run();
		annotations = parseAnnotations();
		if (match(0, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER) != -1) {
			ret = parseReferenceType(annotations);
		} else if (match(0, ParserImplConstants.HOOK) != -1) {
			ret = parseWildcard(annotations);
		} else {
			throw produceParseException(ParserImplConstants.HOOK, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT);
		}
		return ret;
	}

	public BUTree<SWildcardType> parseWildcard(BUTree<SNodeList> annotations) throws ParseException {
		BUTree<? extends SReferenceType> ext = null;
		BUTree<? extends SReferenceType> sup = null;
		BUTree<SNodeList> boundAnnotations = null;
		if (annotations == null) {
			run();
			annotations = emptyList();
		}
		parse(ParserImplConstants.HOOK);
		if (match(0, ParserImplConstants.EXTENDS, ParserImplConstants.SUPER) != -1) {
			if (match(0, ParserImplConstants.EXTENDS) != -1) {
				parse(ParserImplConstants.EXTENDS);
				run();
				boundAnnotations = parseAnnotations();
				ext = parseReferenceType(boundAnnotations);
			} else if (match(0, ParserImplConstants.SUPER) != -1) {
				parse(ParserImplConstants.SUPER);
				run();
				boundAnnotations = parseAnnotations();
				sup = parseReferenceType(boundAnnotations);
			} else {
				throw produceParseException(ParserImplConstants.SUPER, ParserImplConstants.EXTENDS);
			}
		}
		return dress(SWildcardType.make(annotations, optionOf(ext), optionOf(sup)));
	}

	public BUTree<SPrimitiveType> parsePrimitiveType(BUTree<SNodeList> annotations) throws ParseException {
		Primitive primitive;
		if (annotations == null) {
			run();
			annotations = emptyList();
		}
		if (match(0, ParserImplConstants.BOOLEAN) != -1) {
			parse(ParserImplConstants.BOOLEAN);
			primitive = Primitive.Boolean;
		} else if (match(0, ParserImplConstants.CHAR) != -1) {
			parse(ParserImplConstants.CHAR);
			primitive = Primitive.Char;
		} else if (match(0, ParserImplConstants.BYTE) != -1) {
			parse(ParserImplConstants.BYTE);
			primitive = Primitive.Byte;
		} else if (match(0, ParserImplConstants.SHORT) != -1) {
			parse(ParserImplConstants.SHORT);
			primitive = Primitive.Short;
		} else if (match(0, ParserImplConstants.INT) != -1) {
			parse(ParserImplConstants.INT);
			primitive = Primitive.Int;
		} else if (match(0, ParserImplConstants.LONG) != -1) {
			parse(ParserImplConstants.LONG);
			primitive = Primitive.Long;
		} else if (match(0, ParserImplConstants.FLOAT) != -1) {
			parse(ParserImplConstants.FLOAT);
			primitive = Primitive.Float;
		} else if (match(0, ParserImplConstants.DOUBLE) != -1) {
			parse(ParserImplConstants.DOUBLE);
			primitive = Primitive.Double;
		} else {
			throw produceParseException(ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN);
		}
		return dress(SPrimitiveType.make(annotations, primitive));
	}

	public BUTree<? extends SType> parseResultType() throws ParseException {
		BUTree<? extends SType> ret;
		if (match(0, ParserImplConstants.VOID) != -1) {
			run();
			parse(ParserImplConstants.VOID);
			ret = dress(SVoidType.make());
		} else if (match(0, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER) != -1) {
			ret = parseType(null);
		} else {
			throw produceParseException(ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.CHAR, ParserImplConstants.BYTE, ParserImplConstants.BOOLEAN, ParserImplConstants.DOUBLE, ParserImplConstants.LONG, ParserImplConstants.FLOAT, ParserImplConstants.SHORT, ParserImplConstants.INT, ParserImplConstants.VOID);
		}
		return ret;
	}

	public BUTree<SQualifiedType> parseAnnotatedQualifiedType() throws ParseException {
		BUTree<SNodeList> annotations;
		BUTree<SQualifiedType> ret;
		run();
		annotations = parseAnnotations();
		ret = parseQualifiedType(annotations);
		return ret;
	}

	public BUTree<SQualifiedName> parseQualifiedName() throws ParseException {
		BUTree<SNodeOption> qualifier = none();
		BUTree<SQualifiedName> ret = null;
		BUTree<SName> name;
		run();
		name = parseName();
		ret = dress(SQualifiedName.make(qualifier, name));
		while (matchQualifiedName1(0) != -1) {
			lateRun();
			parse(ParserImplConstants.DOT);
			qualifier = optionOf(ret);
			name = parseName();
			ret = dress(SQualifiedName.make(qualifier, name));
		}
		return ret;
	}

	/* ZeroOrMore(
			LookAhead(2)
			Action({
				lateRun();
			})
			Terminal("DOT")
			Action({
				qualifier = optionOf(ret);
			})
			NonTerminal(name, Name)
			Action({
				ret = dress(SQualifiedName.make(qualifier, name));
			})
		) */
	private int matchQualifiedName1(int lookahead) {
		if (match(0, ParserImplConstants.DOT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		return -1;
	}

	public BUTree<SName> parseName() throws ParseException {
		Token id;
		BUTree<SName> name;
		if (match(0, ParserImplConstants.IDENTIFIER) != -1) {
			run();
			id = parse(ParserImplConstants.IDENTIFIER);
			name = dress(SName.make(id.image));
		} else if (quotesMode) {
			name = parseNodeVar();
		} else {
			throw produceParseException(ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER);
		}
		return name;
	}

	public BUTree<? extends SExpr> parseExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		AssignOp op;
		BUTree<? extends SExpr> value;
		BUTree<SNodeList> params;
		if (matchExpression1(0) != -1) {
			run();
			ret = parseName();
			parse(ParserImplConstants.ARROW);
			ret = parseLambdaBody(singletonList(makeFormalParameter((BUTree<SName>) ret)), false);
		} else if (matchExpression2(0) != -1) {
			run();
			parse(ParserImplConstants.LPAREN);
			parse(ParserImplConstants.RPAREN);
			parse(ParserImplConstants.ARROW);
			ret = parseLambdaBody(emptyList(), true);
		} else if (matchExpression3(0) != -1) {
			run();
			parse(ParserImplConstants.LPAREN);
			ret = parseName();
			parse(ParserImplConstants.RPAREN);
			parse(ParserImplConstants.ARROW);
			ret = parseLambdaBody(singletonList(makeFormalParameter((BUTree<SName>) ret)), true);
		} else if (matchExpression4(0) != -1) {
			run();
			parse(ParserImplConstants.LPAREN);
			params = parseInferredFormalParameterList();
			parse(ParserImplConstants.RPAREN);
			parse(ParserImplConstants.ARROW);
			ret = parseLambdaBody(params, true);
		} else if (match(0, ParserImplConstants.MINUS, ParserImplConstants.PLUS, ParserImplConstants.LPAREN, ParserImplConstants.THIS, ParserImplConstants.SUPER, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.FALSE, ParserImplConstants.TRUE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.NULL, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.VOID, ParserImplConstants.LT, ParserImplConstants.NEW, ParserImplConstants.TILDE, ParserImplConstants.BANG, ParserImplConstants.INCR, ParserImplConstants.DECR) != -1) {
			ret = parseConditionalExpression();
			if (matchExpression5(0) != -1) {
				lateRun();
				op = parseAssignmentOperator();
				value = parseExpression();
				ret = dress(SAssignExpr.make(ret, op, value));
			}
		} else {
			throw produceParseException(ParserImplConstants.TILDE, ParserImplConstants.BANG, ParserImplConstants.LONG_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.STRING_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.NULL, ParserImplConstants.FALSE, ParserImplConstants.TRUE, ParserImplConstants.LT, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.INT, ParserImplConstants.LONG, ParserImplConstants.FLOAT, ParserImplConstants.DOUBLE, ParserImplConstants.BOOLEAN, ParserImplConstants.CHAR, ParserImplConstants.BYTE, ParserImplConstants.SHORT, ParserImplConstants.VOID, ParserImplConstants.LPAREN, ParserImplConstants.SUPER, ParserImplConstants.THIS, ParserImplConstants.NEW, ParserImplConstants.MINUS, ParserImplConstants.PLUS, ParserImplConstants.DECR, ParserImplConstants.INCR);
		}
		return ret;
	}

	/* Sequence(
			NonTerminal(Name)
			Terminal("ARROW")
		) */
	private int matchExpression1(int lookahead) {
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.ARROW);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("LPAREN")
			Terminal("RPAREN")
			Terminal("ARROW")
		) */
	private int matchExpression2(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.ARROW);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("LPAREN")
			NonTerminal(Name)
			Terminal("RPAREN")
			Terminal("ARROW")
		) */
	private int matchExpression3(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.ARROW);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("LPAREN")
			NonTerminal(Name)
			Terminal("COMMA")
		) */
	private int matchExpression4(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.COMMA);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			LookAhead(2)
			Action({
				lateRun();
			})
			NonTerminal(op, AssignmentOperator)
			NonTerminal(value, Expression)
			Action({
				ret = dress(SAssignExpr.make(ret, op, value));
			})
		) */
	private int matchExpression5(int lookahead) {
		if (match(0, ParserImplConstants.ANDASSIGN) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.STARASSIGN) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.SLASHASSIGN) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.REMASSIGN) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.PLUSASSIGN) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.LSHIFTASSIGN) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.ASSIGN) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.ORASSIGN) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.MINUSASSIGN) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.XORASSIGN) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		return -1;
	}

	public BUTree<SLambdaExpr> parseLambdaBody(BUTree<SNodeList> parameters, boolean parenthesis) throws ParseException {
		BUTree<SBlockStmt> block;
		BUTree<? extends SExpr> expr;
		BUTree<SLambdaExpr> ret;
		if (match(0, ParserImplConstants.LPAREN, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.STRING_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.NULL, ParserImplConstants.TRUE, ParserImplConstants.FALSE, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.VOID, ParserImplConstants.NEW, ParserImplConstants.SUPER, ParserImplConstants.THIS, ParserImplConstants.BANG, ParserImplConstants.TILDE, ParserImplConstants.MINUS, ParserImplConstants.PLUS, ParserImplConstants.INCR, ParserImplConstants.DECR) != -1) {
			expr = parseExpression();
			ret = dress(SLambdaExpr.make(parameters, parenthesis, left(expr)));
		} else if (match(0, ParserImplConstants.LBRACE) != -1) {
			block = parseBlock();
			ret = dress(SLambdaExpr.make(parameters, parenthesis, right(block)));
		} else {
			throw produceParseException(ParserImplConstants.LBRACE, ParserImplConstants.LPAREN, ParserImplConstants.DECR, ParserImplConstants.INCR, ParserImplConstants.VOID, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.LT, ParserImplConstants.NEW, ParserImplConstants.THIS, ParserImplConstants.SUPER, ParserImplConstants.NULL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.TRUE, ParserImplConstants.FALSE, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.STRING_LITERAL, ParserImplConstants.BANG, ParserImplConstants.TILDE, ParserImplConstants.PLUS, ParserImplConstants.MINUS);
		}
		return ret;
	}

	public BUTree<SNodeList> parseInferredFormalParameterList() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<SFormalParameter> param;
		param = parseInferredFormalParameter();
		ret = append(ret, param);
		while (match(0, ParserImplConstants.COMMA) != -1) {
			parse(ParserImplConstants.COMMA);
			param = parseInferredFormalParameter();
			ret = append(ret, param);
		}
		return ret;
	}

	public BUTree<SFormalParameter> parseInferredFormalParameter() throws ParseException {
		BUTree<SName> name;
		name = parseName();
		return makeFormalParameter(name);
	}

	public AssignOp parseAssignmentOperator() throws ParseException {
		AssignOp ret;
		if (match(0, ParserImplConstants.ASSIGN) != -1) {
			parse(ParserImplConstants.ASSIGN);
			ret = AssignOp.Normal;
		} else if (match(0, ParserImplConstants.STARASSIGN) != -1) {
			parse(ParserImplConstants.STARASSIGN);
			ret = AssignOp.Times;
		} else if (match(0, ParserImplConstants.SLASHASSIGN) != -1) {
			parse(ParserImplConstants.SLASHASSIGN);
			ret = AssignOp.Divide;
		} else if (match(0, ParserImplConstants.REMASSIGN) != -1) {
			parse(ParserImplConstants.REMASSIGN);
			ret = AssignOp.Remainder;
		} else if (match(0, ParserImplConstants.PLUSASSIGN) != -1) {
			parse(ParserImplConstants.PLUSASSIGN);
			ret = AssignOp.Plus;
		} else if (match(0, ParserImplConstants.MINUSASSIGN) != -1) {
			parse(ParserImplConstants.MINUSASSIGN);
			ret = AssignOp.Minus;
		} else if (match(0, ParserImplConstants.LSHIFTASSIGN) != -1) {
			parse(ParserImplConstants.LSHIFTASSIGN);
			ret = AssignOp.LeftShift;
		} else if (match(0, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
			parse(ParserImplConstants.RSIGNEDSHIFTASSIGN);
			ret = AssignOp.RightSignedShift;
		} else if (match(0, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
			parse(ParserImplConstants.RUNSIGNEDSHIFTASSIGN);
			ret = AssignOp.RightUnsignedShift;
		} else if (match(0, ParserImplConstants.ANDASSIGN) != -1) {
			parse(ParserImplConstants.ANDASSIGN);
			ret = AssignOp.And;
		} else if (match(0, ParserImplConstants.XORASSIGN) != -1) {
			parse(ParserImplConstants.XORASSIGN);
			ret = AssignOp.XOr;
		} else if (match(0, ParserImplConstants.ORASSIGN) != -1) {
			parse(ParserImplConstants.ORASSIGN);
			ret = AssignOp.Or;
		} else {
			throw produceParseException(ParserImplConstants.ORASSIGN, ParserImplConstants.XORASSIGN, ParserImplConstants.ANDASSIGN, ParserImplConstants.RUNSIGNEDSHIFTASSIGN, ParserImplConstants.RSIGNEDSHIFTASSIGN, ParserImplConstants.LSHIFTASSIGN, ParserImplConstants.MINUSASSIGN, ParserImplConstants.PLUSASSIGN, ParserImplConstants.REMASSIGN, ParserImplConstants.SLASHASSIGN, ParserImplConstants.STARASSIGN, ParserImplConstants.ASSIGN);
		}
		return ret;
	}

	public BUTree<? extends SExpr> parseConditionalExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> left;
		BUTree<? extends SExpr> right;
		ret = parseConditionalOrExpression();
		if (matchConditionalExpression1(0) != -1) {
			lateRun();
			parse(ParserImplConstants.HOOK);
			left = parseExpression();
			parse(ParserImplConstants.COLON);
			right = parseConditionalExpression();
			ret = dress(SConditionalExpr.make(ret, left, right));
		}
		return ret;
	}

	/* ZeroOrOne(
			LookAhead(2)
			Action({
				lateRun();
			})
			Terminal("HOOK")
			NonTerminal(left, Expression)
			Terminal("COLON")
			NonTerminal(right, ConditionalExpression)
			Action({
				ret = dress(SConditionalExpr.make(ret, left, right));
			})
		) */
	private int matchConditionalExpression1(int lookahead) {
		if (match(0, ParserImplConstants.HOOK) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		return -1;
	}

	public BUTree<? extends SExpr> parseConditionalOrExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		ret = parseConditionalAndExpression();
		while (matchConditionalOrExpression1(0) != -1) {
			lateRun();
			parse(ParserImplConstants.SC_OR);
			right = parseConditionalAndExpression();
			ret = dress(SBinaryExpr.make(ret, BinaryOp.Or, right));
		}
		return ret;
	}

	/* ZeroOrMore(
			LookAhead(2)
			Action({
				lateRun();
			})
			Terminal("SC_OR")
			NonTerminal(right, ConditionalAndExpression)
			Action({
				ret = dress(SBinaryExpr.make(ret, BinaryOp.Or, right));
			})
		) */
	private int matchConditionalOrExpression1(int lookahead) {
		if (match(0, ParserImplConstants.SC_OR) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		return -1;
	}

	public BUTree<? extends SExpr> parseConditionalAndExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		ret = parseInclusiveOrExpression();
		while (matchConditionalAndExpression1(0) != -1) {
			lateRun();
			parse(ParserImplConstants.SC_AND);
			right = parseInclusiveOrExpression();
			ret = dress(SBinaryExpr.make(ret, BinaryOp.And, right));
		}
		return ret;
	}

	/* ZeroOrMore(
			LookAhead(2)
			Action({
				lateRun();
			})
			Terminal("SC_AND")
			NonTerminal(right, InclusiveOrExpression)
			Action({
				ret = dress(SBinaryExpr.make(ret, BinaryOp.And, right));
			})
		) */
	private int matchConditionalAndExpression1(int lookahead) {
		if (match(0, ParserImplConstants.SC_AND) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		return -1;
	}

	public BUTree<? extends SExpr> parseInclusiveOrExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		ret = parseExclusiveOrExpression();
		while (matchInclusiveOrExpression1(0) != -1) {
			lateRun();
			parse(ParserImplConstants.BIT_OR);
			right = parseExclusiveOrExpression();
			ret = dress(SBinaryExpr.make(ret, BinaryOp.BinOr, right));
		}
		return ret;
	}

	/* ZeroOrMore(
			LookAhead(2)
			Action({
				lateRun();
			})
			Terminal("BIT_OR")
			NonTerminal(right, ExclusiveOrExpression)
			Action({
				ret = dress(SBinaryExpr.make(ret, BinaryOp.BinOr, right));
			})
		) */
	private int matchInclusiveOrExpression1(int lookahead) {
		if (match(0, ParserImplConstants.BIT_OR) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		return -1;
	}

	public BUTree<? extends SExpr> parseExclusiveOrExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		ret = parseAndExpression();
		while (matchExclusiveOrExpression1(0) != -1) {
			lateRun();
			parse(ParserImplConstants.XOR);
			right = parseAndExpression();
			ret = dress(SBinaryExpr.make(ret, BinaryOp.XOr, right));
		}
		return ret;
	}

	/* ZeroOrMore(
			LookAhead(2)
			Action({
				lateRun();
			})
			Terminal("XOR")
			NonTerminal(right, AndExpression)
			Action({
				ret = dress(SBinaryExpr.make(ret, BinaryOp.XOr, right));
			})
		) */
	private int matchExclusiveOrExpression1(int lookahead) {
		if (match(0, ParserImplConstants.XOR) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		return -1;
	}

	public BUTree<? extends SExpr> parseAndExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		ret = parseEqualityExpression();
		while (matchAndExpression1(0) != -1) {
			lateRun();
			parse(ParserImplConstants.BIT_AND);
			right = parseEqualityExpression();
			ret = dress(SBinaryExpr.make(ret, BinaryOp.BinAnd, right));
		}
		return ret;
	}

	/* ZeroOrMore(
			LookAhead(2)
			Action({
				lateRun();
			})
			Terminal("BIT_AND")
			NonTerminal(right, EqualityExpression)
			Action({
				ret = dress(SBinaryExpr.make(ret, BinaryOp.BinAnd, right));
			})
		) */
	private int matchAndExpression1(int lookahead) {
		if (match(0, ParserImplConstants.BIT_AND) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		return -1;
	}

	public BUTree<? extends SExpr> parseEqualityExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		BinaryOp op;
		ret = parseInstanceOfExpression();
		while (matchEqualityExpression1(0) != -1) {
			lateRun();
			if (match(0, ParserImplConstants.EQ) != -1) {
				parse(ParserImplConstants.EQ);
				op = BinaryOp.Equal;
			} else if (match(0, ParserImplConstants.NE) != -1) {
				parse(ParserImplConstants.NE);
				op = BinaryOp.NotEqual;
			} else {
				throw produceParseException(ParserImplConstants.NE, ParserImplConstants.EQ);
			}
			right = parseInstanceOfExpression();
			ret = dress(SBinaryExpr.make(ret, op, right));
		}
		return ret;
	}

	/* ZeroOrMore(
			LookAhead(2)
			Action({
				lateRun();
			})
			Choice(
				Sequence(
					Terminal("EQ")
					Action({
						op = BinaryOp.Equal;
					})
				)
				Sequence(
					Terminal("NE")
					Action({
						op = BinaryOp.NotEqual;
					})
				)
			)
			NonTerminal(right, InstanceOfExpression)
			Action({
				ret = dress(SBinaryExpr.make(ret, op, right));
			})
		) */
	private int matchEqualityExpression1(int lookahead) {
		if (match(0, ParserImplConstants.NE) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.EQ) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		return -1;
	}

	public BUTree<? extends SExpr> parseInstanceOfExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<SNodeList> annotations;
		BUTree<? extends SType> type;
		ret = parseRelationalExpression();
		if (matchInstanceOfExpression1(0) != -1) {
			lateRun();
			parse(ParserImplConstants.INSTANCEOF);
			run();
			annotations = parseAnnotations();
			type = parseType(annotations);
			ret = dress(SInstanceOfExpr.make(ret, type));
		}
		return ret;
	}

	/* ZeroOrOne(
			LookAhead(2)
			Action({
				lateRun();
			})
			Terminal("INSTANCEOF")
			Action({
				run();
			})
			NonTerminal(annotations, Annotations)
			NonTerminal(type, Type)
			Action({
				ret = dress(SInstanceOfExpr.make(ret, type));
			})
		) */
	private int matchInstanceOfExpression1(int lookahead) {
		if (match(0, ParserImplConstants.INSTANCEOF) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
		}
		return -1;
	}

	public BUTree<? extends SExpr> parseRelationalExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		BinaryOp op;
		ret = parseShiftExpression();
		while (matchRelationalExpression1(0) != -1) {
			lateRun();
			if (match(0, ParserImplConstants.LT) != -1) {
				parse(ParserImplConstants.LT);
				op = BinaryOp.Less;
			} else if (match(0, ParserImplConstants.GT) != -1) {
				parse(ParserImplConstants.GT);
				op = BinaryOp.Greater;
			} else if (match(0, ParserImplConstants.LE) != -1) {
				parse(ParserImplConstants.LE);
				op = BinaryOp.LessOrEqual;
			} else if (match(0, ParserImplConstants.GE) != -1) {
				parse(ParserImplConstants.GE);
				op = BinaryOp.GreaterOrEqual;
			} else {
				throw produceParseException(ParserImplConstants.GE, ParserImplConstants.LE, ParserImplConstants.GT, ParserImplConstants.LT);
			}
			right = parseShiftExpression();
			ret = dress(SBinaryExpr.make(ret, op, right));
		}
		return ret;
	}

	/* ZeroOrMore(
			LookAhead(2)
			Action({
				lateRun();
			})
			Choice(
				Sequence(
					Terminal("LT")
					Action({
						op = BinaryOp.Less;
					})
				)
				Sequence(
					Terminal("GT")
					Action({
						op = BinaryOp.Greater;
					})
				)
				Sequence(
					Terminal("LE")
					Action({
						op = BinaryOp.LessOrEqual;
					})
				)
				Sequence(
					Terminal("GE")
					Action({
						op = BinaryOp.GreaterOrEqual;
					})
				)
			)
			NonTerminal(right, ShiftExpression)
			Action({
				ret = dress(SBinaryExpr.make(ret, op, right));
			})
		) */
	private int matchRelationalExpression1(int lookahead) {
		if (match(0, ParserImplConstants.LT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.LE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.GT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.GE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		return -1;
	}

	public BUTree<? extends SExpr> parseShiftExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		BinaryOp op;
		ret = parseAdditiveExpression();
		while (matchShiftExpression1(0) != -1) {
			lateRun();
			if (match(0, ParserImplConstants.LSHIFT) != -1) {
				parse(ParserImplConstants.LSHIFT);
				op = BinaryOp.LeftShift;
			} else if (matchShiftExpression2(0) != -1) {
				parseRUNSIGNEDSHIFT();
				op = BinaryOp.RightUnsignedShift;
			} else if (matchShiftExpression3(0) != -1) {
				parseRSIGNEDSHIFT();
				op = BinaryOp.RightSignedShift;
			} else {
				throw produceParseException(ParserImplConstants.GT, ParserImplConstants.LSHIFT);
			}
			right = parseAdditiveExpression();
			ret = dress(SBinaryExpr.make(ret, op, right));
		}
		return ret;
	}

	/* ZeroOrMore(
			LookAhead(2)
			Action({
				lateRun();
			})
			Choice(
				Sequence(
					Terminal("LSHIFT")
					Action({
						op = BinaryOp.LeftShift;
					})
				)
				Sequence(
					LookAhead(3)
					NonTerminal(RUNSIGNEDSHIFT)
					Action({
						op = BinaryOp.RightUnsignedShift;
					})
				)
				Sequence(
					LookAhead(2)
					NonTerminal(RSIGNEDSHIFT)
					Action({
						op = BinaryOp.RightSignedShift;
					})
				)
			)
			NonTerminal(right, AdditiveExpression)
			Action({
				ret = dress(SBinaryExpr.make(ret, op, right));
			})
		) */
	private int matchShiftExpression1(int lookahead) {
		if (match(0, ParserImplConstants.LSHIFT) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.GT) != -1) {
			if (match(1, ParserImplConstants.GT) != -1) {
				return lookahead;
			}
		}
		return -1;
	}

	/* Sequence(
			LookAhead(3)
			NonTerminal(RUNSIGNEDSHIFT)
			Action({
				op = BinaryOp.RightUnsignedShift;
			})
		) */
	private int matchShiftExpression2(int lookahead) {
		if (match(0, ParserImplConstants.GT) != -1) {
			if (match(1, ParserImplConstants.GT) != -1) {
				if (match(2, ParserImplConstants.GT) != -1) {
					return lookahead;
				}
			}
		}
		return -1;
	}

	/* Sequence(
			LookAhead(2)
			NonTerminal(RSIGNEDSHIFT)
			Action({
				op = BinaryOp.RightSignedShift;
			})
		) */
	private int matchShiftExpression3(int lookahead) {
		if (match(0, ParserImplConstants.GT) != -1) {
			if (match(1, ParserImplConstants.GT) != -1) {
				return lookahead;
			}
		}
		return -1;
	}

	public BUTree<? extends SExpr> parseAdditiveExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		BinaryOp op;
		ret = parseMultiplicativeExpression();
		while (matchAdditiveExpression1(0) != -1) {
			lateRun();
			if (match(0, ParserImplConstants.PLUS) != -1) {
				parse(ParserImplConstants.PLUS);
				op = BinaryOp.Plus;
			} else if (match(0, ParserImplConstants.MINUS) != -1) {
				parse(ParserImplConstants.MINUS);
				op = BinaryOp.Minus;
			} else {
				throw produceParseException(ParserImplConstants.MINUS, ParserImplConstants.PLUS);
			}
			right = parseMultiplicativeExpression();
			ret = dress(SBinaryExpr.make(ret, op, right));
		}
		return ret;
	}

	/* ZeroOrMore(
			LookAhead(2)
			Action({
				lateRun();
			})
			Choice(
				Sequence(
					Terminal("PLUS")
					Action({
						op = BinaryOp.Plus;
					})
				)
				Sequence(
					Terminal("MINUS")
					Action({
						op = BinaryOp.Minus;
					})
				)
			)
			NonTerminal(right, MultiplicativeExpression)
			Action({
				ret = dress(SBinaryExpr.make(ret, op, right));
			})
		) */
	private int matchAdditiveExpression1(int lookahead) {
		if (match(0, ParserImplConstants.PLUS) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.MINUS) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		return -1;
	}

	public BUTree<? extends SExpr> parseMultiplicativeExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		BinaryOp op;
		ret = parseUnaryExpression();
		while (matchMultiplicativeExpression1(0) != -1) {
			lateRun();
			if (match(0, ParserImplConstants.STAR) != -1) {
				parse(ParserImplConstants.STAR);
				op = BinaryOp.Times;
			} else if (match(0, ParserImplConstants.SLASH) != -1) {
				parse(ParserImplConstants.SLASH);
				op = BinaryOp.Divide;
			} else if (match(0, ParserImplConstants.REM) != -1) {
				parse(ParserImplConstants.REM);
				op = BinaryOp.Remainder;
			} else {
				throw produceParseException(ParserImplConstants.REM, ParserImplConstants.SLASH, ParserImplConstants.STAR);
			}
			right = parseUnaryExpression();
			ret = dress(SBinaryExpr.make(ret, op, right));
		}
		return ret;
	}

	/* ZeroOrMore(
			LookAhead(2)
			Action({
				lateRun();
			})
			Choice(
				Sequence(
					Terminal("STAR")
					Action({
						op = BinaryOp.Times;
					})
				)
				Sequence(
					Terminal("SLASH")
					Action({
						op = BinaryOp.Divide;
					})
				)
				Sequence(
					Terminal("REM")
					Action({
						op = BinaryOp.Remainder;
					})
				)
			)
			NonTerminal(right, UnaryExpression)
			Action({
				ret = dress(SBinaryExpr.make(ret, op, right));
			})
		) */
	private int matchMultiplicativeExpression1(int lookahead) {
		if (match(0, ParserImplConstants.STAR) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.SLASH) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.REM) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		return -1;
	}

	public BUTree<? extends SExpr> parseUnaryExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		UnaryOp op;
		if (match(0, ParserImplConstants.DECR, ParserImplConstants.INCR) != -1) {
			ret = parsePrefixExpression();
		} else if (match(0, ParserImplConstants.MINUS, ParserImplConstants.PLUS) != -1) {
			run();
			if (match(0, ParserImplConstants.PLUS) != -1) {
				parse(ParserImplConstants.PLUS);
				op = UnaryOp.Positive;
			} else if (match(0, ParserImplConstants.MINUS) != -1) {
				parse(ParserImplConstants.MINUS);
				op = UnaryOp.Negative;
			} else {
				throw produceParseException(ParserImplConstants.MINUS, ParserImplConstants.PLUS);
			}
			ret = parseUnaryExpression();
			ret = dress(SUnaryExpr.make(op, ret));
		} else if (match(0, ParserImplConstants.BANG, ParserImplConstants.TILDE, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.DOUBLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.VOID, ParserImplConstants.NEW, ParserImplConstants.LT, ParserImplConstants.NULL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.STRING_LITERAL, ParserImplConstants.TRUE, ParserImplConstants.FALSE, ParserImplConstants.SUPER, ParserImplConstants.THIS, ParserImplConstants.LPAREN) != -1) {
			ret = parseUnaryExpressionNotPlusMinus();
		} else {
			throw produceParseException(ParserImplConstants.SUPER, ParserImplConstants.THIS, ParserImplConstants.FALSE, ParserImplConstants.TRUE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.NULL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.LPAREN, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.LT, ParserImplConstants.VOID, ParserImplConstants.BYTE, ParserImplConstants.SHORT, ParserImplConstants.INT, ParserImplConstants.LONG, ParserImplConstants.FLOAT, ParserImplConstants.DOUBLE, ParserImplConstants.BOOLEAN, ParserImplConstants.CHAR, ParserImplConstants.NEW, ParserImplConstants.BANG, ParserImplConstants.TILDE, ParserImplConstants.MINUS, ParserImplConstants.PLUS, ParserImplConstants.DECR, ParserImplConstants.INCR);
		}
		return ret;
	}

	public BUTree<? extends SExpr> parsePrefixExpression() throws ParseException {
		UnaryOp op;
		BUTree<? extends SExpr> ret;
		run();
		if (match(0, ParserImplConstants.INCR) != -1) {
			parse(ParserImplConstants.INCR);
			op = UnaryOp.PreIncrement;
		} else if (match(0, ParserImplConstants.DECR) != -1) {
			parse(ParserImplConstants.DECR);
			op = UnaryOp.PreDecrement;
		} else {
			throw produceParseException(ParserImplConstants.DECR, ParserImplConstants.INCR);
		}
		ret = parseUnaryExpression();
		return dress(SUnaryExpr.make(op, ret));
	}

	public BUTree<? extends SExpr> parseUnaryExpressionNotPlusMinus() throws ParseException {
		BUTree<? extends SExpr> ret;
		UnaryOp op;
		if (match(0, ParserImplConstants.BANG, ParserImplConstants.TILDE) != -1) {
			run();
			if (match(0, ParserImplConstants.TILDE) != -1) {
				parse(ParserImplConstants.TILDE);
				op = UnaryOp.Inverse;
			} else if (match(0, ParserImplConstants.BANG) != -1) {
				parse(ParserImplConstants.BANG);
				op = UnaryOp.Not;
			} else {
				throw produceParseException(ParserImplConstants.BANG, ParserImplConstants.TILDE);
			}
			ret = parseUnaryExpression();
			ret = dress(SUnaryExpr.make(op, ret));
		} else if (matchUnaryExpressionNotPlusMinus1(0) != -1) {
			ret = parseCastExpression();
		} else if (match(0, ParserImplConstants.SUPER, ParserImplConstants.THIS, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.DOUBLE, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.BOOLEAN, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.VOID, ParserImplConstants.NEW, ParserImplConstants.LT, ParserImplConstants.LPAREN, ParserImplConstants.LONG_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.STRING_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.FALSE, ParserImplConstants.TRUE, ParserImplConstants.NULL) != -1) {
			ret = parsePostfixExpression();
		} else {
			throw produceParseException(ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.BOOLEAN, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.DOUBLE, ParserImplConstants.VOID, ParserImplConstants.NEW, ParserImplConstants.SUPER, ParserImplConstants.THIS, ParserImplConstants.NULL, ParserImplConstants.FALSE, ParserImplConstants.TRUE, ParserImplConstants.LONG_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.STRING_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.LPAREN, ParserImplConstants.LT, ParserImplConstants.BANG, ParserImplConstants.TILDE);
		}
		return ret;
	}

	/* Sequence(
			NonTerminal(CastExpression)
		) */
	private int matchUnaryExpressionNotPlusMinus1(int lookahead) {
		lookahead = matchCastExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	public BUTree<? extends SExpr> parsePostfixExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		UnaryOp op;
		ret = parsePrimaryExpression();
		if (matchPostfixExpression1(0) != -1) {
			lateRun();
			if (match(0, ParserImplConstants.INCR) != -1) {
				parse(ParserImplConstants.INCR);
				op = UnaryOp.PostIncrement;
			} else if (match(0, ParserImplConstants.DECR) != -1) {
				parse(ParserImplConstants.DECR);
				op = UnaryOp.PostDecrement;
			} else {
				throw produceParseException(ParserImplConstants.DECR, ParserImplConstants.INCR);
			}
			ret = dress(SUnaryExpr.make(op, ret));
		}
		return ret;
	}

	/* ZeroOrOne(
			LookAhead(2)
			Action({
				lateRun();
			})
			Choice(
				Sequence(
					Terminal("INCR")
					Action({
						op = UnaryOp.PostIncrement;
					})
				)
				Sequence(
					Terminal("DECR")
					Action({
						op = UnaryOp.PostDecrement;
					})
				)
			)
			Action({
				ret = dress(SUnaryExpr.make(op, ret));
			})
		) */
	private int matchPostfixExpression1(int lookahead) {
		if (match(0, ParserImplConstants.INCR) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.DECR) != -1) {
			return lookahead;
		}
		return -1;
	}

	public BUTree<? extends SExpr> parseCastExpression() throws ParseException {
		BUTree<SNodeList> annotations = null;
		BUTree<? extends SType> primitiveType;
		BUTree<? extends SType> type;
		BUTree<SNodeList> arrayDims;
		BUTree<? extends SExpr> ret;
		run();
		parse(ParserImplConstants.LPAREN);
		run();
		annotations = parseAnnotations();
		if (match(0, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN) != -1) {
			primitiveType = parsePrimitiveType(annotations);
			if (match(0, ParserImplConstants.RPAREN) != -1) {
				parse(ParserImplConstants.RPAREN);
				ret = parseUnaryExpression();
				ret = dress(SCastExpr.make(primitiveType, ret));
			} else if (match(0, ParserImplConstants.AT, ParserImplConstants.LBRACKET) != -1) {
				lateRun();
				arrayDims = parseArrayDimsMandatory();
				type = dress(SArrayType.make(primitiveType, arrayDims));
				type = parseReferenceCastTypeRest(type);
				parse(ParserImplConstants.RPAREN);
				ret = parseUnaryExpressionNotPlusMinus();
				ret = dress(SCastExpr.make(type, ret));
			} else {
				throw produceParseException(ParserImplConstants.AT, ParserImplConstants.LBRACKET, ParserImplConstants.RPAREN);
			}
		} else if (match(0, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER) != -1) {
			type = parseQualifiedType(annotations);
			if (matchCastExpression1(0) != -1) {
				lateRun();
				arrayDims = parseArrayDimsMandatory();
				type = dress(SArrayType.make(type, arrayDims));
			}
			type = parseReferenceCastTypeRest(type);
			parse(ParserImplConstants.RPAREN);
			ret = parseUnaryExpressionNotPlusMinus();
			ret = dress(SCastExpr.make(type, ret));
		} else {
			throw produceParseException(ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN);
		}
		return ret;
	}

	/* Sequence(
			NonTerminal(Annotations)
			Terminal("LBRACKET")
		) */
	private int matchCastExpression1(int lookahead) {
		lookahead = matchAnnotations(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.LBRACKET);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	public BUTree<? extends SType> parseReferenceCastTypeRest(BUTree<? extends SType> type) throws ParseException {
		BUTree<SNodeList> types = emptyList();
		BUTree<SNodeList> annotations = null;
		if (matchReferenceCastTypeRest1(0) != -1) {
			types = append(types, type);
			lateRun();
			do {
				parse(ParserImplConstants.BIT_AND);
				run();
				annotations = parseAnnotations();
				type = parseReferenceType(annotations);
				types = append(types, type);
			} while (match(0, ParserImplConstants.BIT_AND) != -1);
			type = dress(SIntersectionType.make(types));
		}
		return type;
	}

	/* Sequence(
			Terminal("BIT_AND")
		) */
	private int matchReferenceCastTypeRest1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.BIT_AND);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	public BUTree<? extends SExpr> parseLiteral() throws ParseException {
		Token literal;
		BUTree<? extends SExpr> ret;
		run();
		if (match(0, ParserImplConstants.INTEGER_LITERAL) != -1) {
			literal = parse(ParserImplConstants.INTEGER_LITERAL);
			ret = SLiteralExpr.make(Integer.class, literal.image);
		} else if (match(0, ParserImplConstants.LONG_LITERAL) != -1) {
			literal = parse(ParserImplConstants.LONG_LITERAL);
			ret = SLiteralExpr.make(Long.class, literal.image);
		} else if (match(0, ParserImplConstants.FLOAT_LITERAL) != -1) {
			literal = parse(ParserImplConstants.FLOAT_LITERAL);
			ret = SLiteralExpr.make(Float.class, literal.image);
		} else if (match(0, ParserImplConstants.DOUBLE_LITERAL) != -1) {
			literal = parse(ParserImplConstants.DOUBLE_LITERAL);
			ret = SLiteralExpr.make(Double.class, literal.image);
		} else if (match(0, ParserImplConstants.CHARACTER_LITERAL) != -1) {
			literal = parse(ParserImplConstants.CHARACTER_LITERAL);
			ret = SLiteralExpr.make(Character.class, literal.image);
		} else if (match(0, ParserImplConstants.STRING_LITERAL) != -1) {
			literal = parse(ParserImplConstants.STRING_LITERAL);
			ret = SLiteralExpr.make(String.class, literal.image);
		} else if (match(0, ParserImplConstants.TRUE) != -1) {
			literal = parse(ParserImplConstants.TRUE);
			ret = SLiteralExpr.make(Boolean.class, literal.image);
		} else if (match(0, ParserImplConstants.FALSE) != -1) {
			literal = parse(ParserImplConstants.FALSE);
			ret = SLiteralExpr.make(Boolean.class, literal.image);
		} else if (match(0, ParserImplConstants.NULL) != -1) {
			literal = parse(ParserImplConstants.NULL);
			ret = SLiteralExpr.make(Void.class, literal.image);
		} else {
			throw produceParseException(ParserImplConstants.NULL, ParserImplConstants.FALSE, ParserImplConstants.TRUE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.INTEGER_LITERAL);
		}
		return dress(ret);
	}

	public BUTree<? extends SExpr> parsePrimaryExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		ret = parsePrimaryPrefix();
		while (matchPrimaryExpression1(0) != -1) {
			lateRun();
			ret = parsePrimarySuffix(ret);
		}
		return ret;
	}

	/* ZeroOrMore(
			LookAhead(2)
			Action({
				lateRun();
			})
			NonTerminal(ret, PrimarySuffix)
		) */
	private int matchPrimaryExpression1(int lookahead) {
		if (match(0, ParserImplConstants.DOUBLECOLON) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.DOT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.LBRACKET) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		return -1;
	}

	public BUTree<? extends SExpr> parsePrimaryExpressionWithoutSuperSuffix() throws ParseException {
		BUTree<? extends SExpr> ret;
		ret = parsePrimaryPrefix();
		while (matchPrimaryExpressionWithoutSuperSuffix1(0) != -1) {
			lateRun();
			ret = parsePrimarySuffixWithoutSuper(ret);
		}
		return ret;
	}

	/* Sequence(
			NonTerminal(PrimarySuffixWithoutSuper)
		) */
	private int matchPrimaryExpressionWithoutSuperSuffix1(int lookahead) {
		lookahead = matchPrimarySuffixWithoutSuper(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	public BUTree<? extends SExpr> parsePrimaryPrefix() throws ParseException {
		BUTree<? extends SExpr> ret = null;
		BUTree<SNodeList> typeArgs = null;
		BUTree<SNodeList> params;
		BUTree<? extends SType> type;
		if (match(0, ParserImplConstants.LONG_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.NULL, ParserImplConstants.FALSE, ParserImplConstants.TRUE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.FLOAT_LITERAL) != -1) {
			ret = parseLiteral();
		} else if (match(0, ParserImplConstants.THIS) != -1) {
			run();
			parse(ParserImplConstants.THIS);
			ret = dress(SThisExpr.make(none()));
		} else if (match(0, ParserImplConstants.SUPER) != -1) {
			run();
			parse(ParserImplConstants.SUPER);
			ret = dress(SSuperExpr.make(none()));
			if (match(0, ParserImplConstants.DOT) != -1) {
				lateRun();
				parse(ParserImplConstants.DOT);
				if (matchPrimaryPrefix1(0) != -1) {
					ret = parseMethodInvocation(ret);
				} else if (match(0, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER) != -1) {
					ret = parseFieldAccess(ret);
				} else {
					throw produceParseException(ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT);
				}
			} else if (match(0, ParserImplConstants.DOUBLECOLON) != -1) {
				lateRun();
				ret = parseMethodReferenceSuffix(ret);
			} else {
				throw produceParseException(ParserImplConstants.DOUBLECOLON, ParserImplConstants.DOT);
			}
		} else if (match(0, ParserImplConstants.NEW) != -1) {
			ret = parseAllocationExpression(null);
		} else if (matchPrimaryPrefix4(0) != -1) {
			run();
			type = parseResultType();
			parse(ParserImplConstants.DOT);
			parse(ParserImplConstants.CLASS);
			ret = dress(SClassExpr.make(type));
		} else if (matchPrimaryPrefix5(0) != -1) {
			run();
			type = parseResultType();
			ret = STypeExpr.make(type);
			ret = parseMethodReferenceSuffix(ret);
		} else if (matchPrimaryPrefix6(0) != -1) {
			run();
			ret = parseMethodInvocation(null);
		} else if (match(0, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER) != -1) {
			ret = parseName();
			if (match(0, ParserImplConstants.ARROW) != -1) {
				lateRun();
				parse(ParserImplConstants.ARROW);
				ret = parseLambdaBody(singletonList(makeFormalParameter((BUTree<SName>) ret)), false);
			}
		} else if (match(0, ParserImplConstants.LPAREN) != -1) {
			run();
			parse(ParserImplConstants.LPAREN);
			if (match(0, ParserImplConstants.RPAREN) != -1) {
				parse(ParserImplConstants.RPAREN);
				parse(ParserImplConstants.ARROW);
				ret = parseLambdaBody(emptyList(), true);
			} else if (matchPrimaryPrefix9(0) != -1) {
				ret = parseName();
				parse(ParserImplConstants.RPAREN);
				parse(ParserImplConstants.ARROW);
				ret = parseLambdaBody(singletonList(makeFormalParameter((BUTree<SName>) ret)), true);
			} else if (matchPrimaryPrefix10(0) != -1) {
				params = parseInferredFormalParameterList();
				parse(ParserImplConstants.RPAREN);
				parse(ParserImplConstants.ARROW);
				ret = parseLambdaBody(params, true);
			} else if (isLambda(0)) {
				params = parseFormalParameterList();
				parse(ParserImplConstants.RPAREN);
				parse(ParserImplConstants.ARROW);
				ret = parseLambdaBody(params, true);
			} else if (match(0, ParserImplConstants.LPAREN, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.STRING_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.NULL, ParserImplConstants.TRUE, ParserImplConstants.FALSE, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.VOID, ParserImplConstants.NEW, ParserImplConstants.SUPER, ParserImplConstants.THIS, ParserImplConstants.BANG, ParserImplConstants.TILDE, ParserImplConstants.MINUS, ParserImplConstants.PLUS, ParserImplConstants.INCR, ParserImplConstants.DECR) != -1) {
				ret = parseExpression();
				parse(ParserImplConstants.RPAREN);
				ret = dress(SParenthesizedExpr.make(ret));
			} else {
				throw produceParseException(ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.SUPER, ParserImplConstants.THIS, ParserImplConstants.NULL, ParserImplConstants.TRUE, ParserImplConstants.FALSE, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.STRING_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.LPAREN, ParserImplConstants.LT, ParserImplConstants.VOID, ParserImplConstants.FLOAT, ParserImplConstants.DOUBLE, ParserImplConstants.BYTE, ParserImplConstants.SHORT, ParserImplConstants.INT, ParserImplConstants.LONG, ParserImplConstants.BOOLEAN, ParserImplConstants.CHAR, ParserImplConstants.NEW, ParserImplConstants.TILDE, ParserImplConstants.BANG, ParserImplConstants.PLUS, ParserImplConstants.MINUS, ParserImplConstants.DECR, ParserImplConstants.INCR, ParserImplConstants.NODE_LIST_VARIABLE, ParserImplConstants._DEFAULT, ParserImplConstants.ABSTRACT, ParserImplConstants.FINAL, ParserImplConstants.STATIC, ParserImplConstants.PUBLIC, ParserImplConstants.PRIVATE, ParserImplConstants.PROTECTED, ParserImplConstants.AT, ParserImplConstants.STRICTFP, ParserImplConstants.VOLATILE, ParserImplConstants.TRANSIENT, ParserImplConstants.NATIVE, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.RPAREN);
			}
		} else {
			throw produceParseException(ParserImplConstants.LPAREN, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.LT, ParserImplConstants.VOID, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.NEW, ParserImplConstants.SUPER, ParserImplConstants.THIS, ParserImplConstants.NULL, ParserImplConstants.FALSE, ParserImplConstants.TRUE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.INTEGER_LITERAL);
		}
		return ret;
	}

	/* Sequence(
			NonTerminal(TypeArguments)
		) */
	private int matchPrimaryPrefix1_1_1(int lookahead) {
		lookahead = matchTypeArguments(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			NonTerminal(TypeArguments)
		) */
	private int matchPrimaryPrefix1_1(int lookahead) {
		int newLookahead;
		newLookahead = matchPrimaryPrefix1_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			ZeroOrOne(
				NonTerminal(TypeArguments)
			)
			NonTerminal(Name)
			Terminal("LPAREN")
		) */
	private int matchPrimaryPrefix1(int lookahead) {
		lookahead = matchPrimaryPrefix1_1(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(ResultType)
			Terminal("DOT")
			Terminal("CLASS")
		) */
	private int matchPrimaryPrefix4(int lookahead) {
		lookahead = matchResultType(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.DOT);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.CLASS);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(ResultType)
			Terminal("DOUBLECOLON")
		) */
	private int matchPrimaryPrefix5(int lookahead) {
		lookahead = matchResultType(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.DOUBLECOLON);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(TypeArguments)
		) */
	private int matchPrimaryPrefix6_1_1(int lookahead) {
		lookahead = matchTypeArguments(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			NonTerminal(TypeArguments)
		) */
	private int matchPrimaryPrefix6_1(int lookahead) {
		int newLookahead;
		newLookahead = matchPrimaryPrefix6_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			ZeroOrOne(
				NonTerminal(TypeArguments)
			)
			NonTerminal(Name)
			Terminal("LPAREN")
		) */
	private int matchPrimaryPrefix6(int lookahead) {
		lookahead = matchPrimaryPrefix6_1(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(Name)
			Terminal("RPAREN")
			Terminal("ARROW")
		) */
	private int matchPrimaryPrefix9(int lookahead) {
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.ARROW);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(Name)
			Terminal("COMMA")
		) */
	private int matchPrimaryPrefix10(int lookahead) {
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.COMMA);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	public BUTree<? extends SExpr> parsePrimarySuffix(BUTree<? extends SExpr> scope) throws ParseException {
		BUTree<? extends SExpr> ret;
		if (matchPrimarySuffix1(0) != -1) {
			ret = parsePrimarySuffixWithoutSuper(scope);
		} else if (match(0, ParserImplConstants.DOT) != -1) {
			parse(ParserImplConstants.DOT);
			parse(ParserImplConstants.SUPER);
			ret = dress(SSuperExpr.make(optionOf(scope)));
		} else if (match(0, ParserImplConstants.DOUBLECOLON) != -1) {
			ret = parseMethodReferenceSuffix(scope);
		} else {
			throw produceParseException(ParserImplConstants.DOUBLECOLON, ParserImplConstants.DOT, ParserImplConstants.LBRACKET);
		}
		return ret;
	}

	/* Sequence(
			LookAhead(2)
			NonTerminal(ret, PrimarySuffixWithoutSuper)
		) */
	private int matchPrimarySuffix1(int lookahead) {
		if (match(0, ParserImplConstants.DOT) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.LBRACKET) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		return -1;
	}

	public BUTree<? extends SExpr> parsePrimarySuffixWithoutSuper(BUTree<? extends SExpr> scope) throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<SName> name;
		if (match(0, ParserImplConstants.DOT) != -1) {
			parse(ParserImplConstants.DOT);
			if (match(0, ParserImplConstants.THIS) != -1) {
				parse(ParserImplConstants.THIS);
				ret = dress(SThisExpr.make(optionOf(scope)));
			} else if (match(0, ParserImplConstants.NEW) != -1) {
				ret = parseAllocationExpression(scope);
			} else if (matchPrimarySuffixWithoutSuper1(0) != -1) {
				ret = parseMethodInvocation(scope);
			} else if (match(0, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER) != -1) {
				ret = parseFieldAccess(scope);
			} else {
				throw produceParseException(ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.LT, ParserImplConstants.NEW, ParserImplConstants.THIS);
			}
		} else if (match(0, ParserImplConstants.LBRACKET) != -1) {
			parse(ParserImplConstants.LBRACKET);
			ret = parseExpression();
			parse(ParserImplConstants.RBRACKET);
			ret = dress(SArrayAccessExpr.make(scope, ret));
		} else {
			throw produceParseException(ParserImplConstants.LBRACKET, ParserImplConstants.DOT);
		}
		return ret;
	}

	/* Sequence(
			NonTerminal(TypeArguments)
		) */
	private int matchPrimarySuffixWithoutSuper1_1_1(int lookahead) {
		lookahead = matchTypeArguments(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* ZeroOrOne(
			NonTerminal(TypeArguments)
		) */
	private int matchPrimarySuffixWithoutSuper1_1(int lookahead) {
		int newLookahead;
		newLookahead = matchPrimarySuffixWithoutSuper1_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* Sequence(
			ZeroOrOne(
				NonTerminal(TypeArguments)
			)
			NonTerminal(Name)
			Terminal("LPAREN")
		) */
	private int matchPrimarySuffixWithoutSuper1(int lookahead) {
		lookahead = matchPrimarySuffixWithoutSuper1_1(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	public BUTree<? extends SExpr> parseFieldAccess(BUTree<? extends SExpr> scope) throws ParseException {
		BUTree<SName> name;
		name = parseName();
		return dress(SFieldAccessExpr.make(optionOf(scope), name));
	}

	public BUTree<? extends SExpr> parseMethodInvocation(BUTree<? extends SExpr> scope) throws ParseException {
		BUTree<SNodeList> typeArgs = null;
		BUTree<SName> name;
		BUTree<SNodeList> args = null;
		BUTree<? extends SExpr> ret;
		if (match(0, ParserImplConstants.LT) != -1) {
			typeArgs = parseTypeArguments();
		}
		name = parseName();
		args = parseArguments();
		return dress(SMethodInvocationExpr.make(optionOf(scope), ensureNotNull(typeArgs), name, args));
	}

	public BUTree<SNodeList> parseArguments() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<? extends SExpr> expr;
		parse(ParserImplConstants.LPAREN);
		if (match(0, ParserImplConstants.LPAREN, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.DECR, ParserImplConstants.INCR, ParserImplConstants.MINUS, ParserImplConstants.PLUS, ParserImplConstants.SUPER, ParserImplConstants.NEW, ParserImplConstants.VOID, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.LT, ParserImplConstants.FALSE, ParserImplConstants.NULL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.STRING_LITERAL, ParserImplConstants.TRUE, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.THIS, ParserImplConstants.TILDE, ParserImplConstants.BANG, ParserImplConstants.NODE_LIST_VARIABLE) != -1) {
			if (matchArguments1(0) != -1) {
				expr = parseExpression();
				ret = append(ret, expr);
				while (match(0, ParserImplConstants.COMMA) != -1) {
					parse(ParserImplConstants.COMMA);
					expr = parseExpression();
					ret = append(ret, expr);
				}
			} else if (quotesMode) {
				ret = parseNodeListVar();
			} else {
				throw produceParseException(ParserImplConstants.NODE_LIST_VARIABLE, ParserImplConstants.LPAREN, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.LT, ParserImplConstants.VOID, ParserImplConstants.DOUBLE, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.BOOLEAN, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.NEW, ParserImplConstants.SUPER, ParserImplConstants.THIS, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.NULL, ParserImplConstants.FALSE, ParserImplConstants.TRUE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.BANG, ParserImplConstants.TILDE, ParserImplConstants.MINUS, ParserImplConstants.PLUS, ParserImplConstants.INCR, ParserImplConstants.DECR);
			}
		}
		parse(ParserImplConstants.RPAREN);
		return ret;
	}

	/* Sequence(
			LookAhead(1)
			NonTerminal(expr, Expression)
			Action({
				ret = append(ret, expr);
			})
			ZeroOrMore(
				Terminal("COMMA")
				NonTerminal(expr, Expression)
				Action({
					ret = append(ret, expr);
				})
			)
		) */
	private int matchArguments1(int lookahead) {
		if (match(0, ParserImplConstants.NODE_VARIABLE) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.NEW) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.FLOAT) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.LT) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.CHAR) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.INTEGER_LITERAL) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.INT) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.MINUS) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.BYTE) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.IDENTIFIER) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.DOUBLE) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.LONG) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.SUPER) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.NULL) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.DOUBLE_LITERAL) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.CHARACTER_LITERAL) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.BANG) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.LPAREN) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.TRUE) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.LONG_LITERAL) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.BOOLEAN) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.DECR) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.SHORT) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.FLOAT_LITERAL) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.INCR) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.THIS) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.FALSE) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.STRING_LITERAL) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.TILDE) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.VOID) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.PLUS) != -1) {
			return lookahead;
		}
		return -1;
	}

	public BUTree<? extends SExpr> parseMethodReferenceSuffix(BUTree<? extends SExpr> scope) throws ParseException {
		BUTree<SNodeList> typeArgs = null;
		BUTree<SName> name;
		BUTree<? extends SExpr> ret;
		parse(ParserImplConstants.DOUBLECOLON);
		if (match(0, ParserImplConstants.LT) != -1) {
			typeArgs = parseTypeArguments();
		}
		if (match(0, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE) != -1) {
			name = parseName();
		} else if (match(0, ParserImplConstants.NEW) != -1) {
			parse(ParserImplConstants.NEW);
			name = SName.make("new");
		} else {
			throw produceParseException(ParserImplConstants.NEW, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER);
		}
		ret = dress(SMethodReferenceExpr.make(scope, ensureNotNull(typeArgs), name));
		return ret;
	}

	public BUTree<? extends SExpr> parseAllocationExpression(BUTree<? extends SExpr> scope) throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SType> type;
		BUTree<SNodeList> typeArgs = null;
		BUTree<SNodeList> anonymousBody = null;
		BUTree<SNodeList> args;
		BUTree<SNodeList> annotations = null;
		if (scope == null) run();

		parse(ParserImplConstants.NEW);
		if (match(0, ParserImplConstants.LT) != -1) {
			typeArgs = parseTypeArguments();
		}
		run();
		annotations = parseAnnotations();
		if (match(0, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN) != -1) {
			type = parsePrimitiveType(annotations);
			ret = parseArrayCreationExpr(type);
		} else if (match(0, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER) != -1) {
			type = parseQualifiedType(annotations);
			if (match(0, ParserImplConstants.AT, ParserImplConstants.LBRACKET) != -1) {
				ret = parseArrayCreationExpr(type);
			} else if (match(0, ParserImplConstants.LPAREN) != -1) {
				args = parseArguments();
				if (matchAllocationExpression1(0) != -1) {
					anonymousBody = parseClassOrInterfaceBody(TypeKind.Class);
				}
				ret = dress(SObjectCreationExpr.make(optionOf(scope), ensureNotNull(typeArgs), (BUTree<SQualifiedType>) type, args, optionOf(anonymousBody)));
			} else {
				throw produceParseException(ParserImplConstants.LPAREN, ParserImplConstants.AT, ParserImplConstants.LBRACKET);
			}
		} else {
			throw produceParseException(ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN);
		}
		return ret;
	}

	/* Sequence(
			Terminal("LBRACE")
		) */
	private int matchAllocationExpression1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.LBRACE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	public BUTree<? extends SExpr> parseArrayCreationExpr(BUTree<? extends SType> componentType) throws ParseException {
		BUTree<? extends SExpr> expr;
		BUTree<SNodeList> arrayDimExprs = emptyList();
		BUTree<SNodeList> arrayDims = emptyList();
		BUTree<SNodeList> annotations = null;
		BUTree<SArrayInitializerExpr> initializer;
		if (matchArrayCreationExpr1(0) != -1) {
			arrayDimExprs = parseArrayDimExprsMandatory();
			arrayDims = parseArrayDims();
			return dress(SArrayCreationExpr.make(componentType, arrayDimExprs, arrayDims, none()));
		} else if (match(0, ParserImplConstants.AT, ParserImplConstants.LBRACKET) != -1) {
			arrayDims = parseArrayDimsMandatory();
			initializer = parseArrayInitializer();
			return dress(SArrayCreationExpr.make(componentType, arrayDimExprs, arrayDims, optionOf(initializer)));
		} else {
			throw produceParseException(ParserImplConstants.AT, ParserImplConstants.LBRACKET);
		}
	}

	/* Sequence(
			NonTerminal(Annotations)
			Terminal("LBRACKET")
			NonTerminal(Expression)
			Terminal("RBRACKET")
		) */
	private int matchArrayCreationExpr1(int lookahead) {
		lookahead = matchAnnotations(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.LBRACKET);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RBRACKET);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	public BUTree<SNodeList> parseArrayDimExprsMandatory() throws ParseException {
		BUTree<SNodeList> arrayDimExprs = emptyList();
		BUTree<SNodeList> annotations;
		BUTree<? extends SExpr> expr;
		do {
			run();
			annotations = parseAnnotations();
			parse(ParserImplConstants.LBRACKET);
			expr = parseExpression();
			parse(ParserImplConstants.RBRACKET);
			arrayDimExprs = append(arrayDimExprs, dress(SArrayDimExpr.make(annotations, expr)));
		} while (matchArrayDimExprsMandatory1(0) != -1);
		return arrayDimExprs;
	}

	/* Sequence(
			NonTerminal(Annotations)
			Terminal("LBRACKET")
			NonTerminal(Expression)
			Terminal("RBRACKET")
		) */
	private int matchArrayDimExprsMandatory1(int lookahead) {
		lookahead = matchAnnotations(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.LBRACKET);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RBRACKET);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	public BUTree<SNodeList> parseArrayDimsMandatory() throws ParseException {
		BUTree<SNodeList> arrayDims = emptyList();
		BUTree<SNodeList> annotations;
		do {
			run();
			annotations = parseAnnotations();
			parse(ParserImplConstants.LBRACKET);
			parse(ParserImplConstants.RBRACKET);
			arrayDims = append(arrayDims, dress(SArrayDim.make(annotations)));
		} while (matchArrayDimsMandatory1(0) != -1);
		return arrayDims;
	}

	/* Sequence(
			NonTerminal(Annotations)
			Terminal("LBRACKET")
			Terminal("RBRACKET")
		) */
	private int matchArrayDimsMandatory1(int lookahead) {
		lookahead = matchAnnotations(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.LBRACKET);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RBRACKET);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	public BUTree<? extends SStmt> parseStatement() throws ParseException {
		BUTree<? extends SStmt> ret;
		if (matchStatement1(0) != -1) {
			ret = parseLabeledStatement();
		} else if (match(0, ParserImplConstants.ASSERT) != -1) {
			ret = parseAssertStatement();
		} else if (match(0, ParserImplConstants.LBRACE) != -1) {
			ret = parseBlock();
		} else if (match(0, ParserImplConstants.SEMICOLON) != -1) {
			ret = parseEmptyStatement();
		} else if (match(0, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.STRING_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.NULL, ParserImplConstants.TRUE, ParserImplConstants.FALSE, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.VOID, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.BYTE, ParserImplConstants.SHORT, ParserImplConstants.BOOLEAN, ParserImplConstants.CHAR, ParserImplConstants.FLOAT, ParserImplConstants.DOUBLE, ParserImplConstants.INT, ParserImplConstants.LONG, ParserImplConstants.LT, ParserImplConstants.LPAREN, ParserImplConstants.THIS, ParserImplConstants.SUPER, ParserImplConstants.NEW, ParserImplConstants.DECR, ParserImplConstants.INCR) != -1) {
			ret = parseStatementExpression();
		} else if (match(0, ParserImplConstants.SWITCH) != -1) {
			ret = parseSwitchStatement();
		} else if (match(0, ParserImplConstants.IF) != -1) {
			ret = parseIfStatement();
		} else if (match(0, ParserImplConstants.WHILE) != -1) {
			ret = parseWhileStatement();
		} else if (match(0, ParserImplConstants.DO) != -1) {
			ret = parseDoStatement();
		} else if (match(0, ParserImplConstants.FOR) != -1) {
			ret = parseForStatement();
		} else if (match(0, ParserImplConstants.BREAK) != -1) {
			ret = parseBreakStatement();
		} else if (match(0, ParserImplConstants.CONTINUE) != -1) {
			ret = parseContinueStatement();
		} else if (match(0, ParserImplConstants.RETURN) != -1) {
			ret = parseReturnStatement();
		} else if (match(0, ParserImplConstants.THROW) != -1) {
			ret = parseThrowStatement();
		} else if (match(0, ParserImplConstants.SYNCHRONIZED) != -1) {
			ret = parseSynchronizedStatement();
		} else if (match(0, ParserImplConstants.TRY) != -1) {
			ret = parseTryStatement();
		} else {
			throw produceParseException(ParserImplConstants.TRY, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.THROW, ParserImplConstants.RETURN, ParserImplConstants.CONTINUE, ParserImplConstants.BREAK, ParserImplConstants.FOR, ParserImplConstants.DO, ParserImplConstants.WHILE, ParserImplConstants.IF, ParserImplConstants.SWITCH, ParserImplConstants.VOID, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.LT, ParserImplConstants.NEW, ParserImplConstants.LPAREN, ParserImplConstants.THIS, ParserImplConstants.SUPER, ParserImplConstants.NULL, ParserImplConstants.FALSE, ParserImplConstants.TRUE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.INCR, ParserImplConstants.DECR, ParserImplConstants.SEMICOLON, ParserImplConstants.LBRACE, ParserImplConstants.ASSERT);
		}
		return ret;
	}

	/* Sequence(
			LookAhead(2)
			NonTerminal(ret, LabeledStatement)
		) */
	private int matchStatement1(int lookahead) {
		if (match(0, ParserImplConstants.NODE_VARIABLE) != -1) {
			if (match(1, ParserImplConstants.COLON) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.IDENTIFIER) != -1) {
			if (match(1, ParserImplConstants.COLON) != -1) {
				return lookahead;
			}
		}
		return -1;
	}

	public BUTree<SAssertStmt> parseAssertStatement() throws ParseException {
		BUTree<? extends SExpr> check;
		BUTree<? extends SExpr> msg = null;
		run();
		parse(ParserImplConstants.ASSERT);
		check = parseExpression();
		if (match(0, ParserImplConstants.COLON) != -1) {
			parse(ParserImplConstants.COLON);
			msg = parseExpression();
		}
		parse(ParserImplConstants.SEMICOLON);
		return dress(SAssertStmt.make(check, optionOf(msg)));
	}

	public BUTree<SLabeledStmt> parseLabeledStatement() throws ParseException {
		BUTree<SName> label;
		BUTree<? extends SStmt> stmt;
		run();
		label = parseName();
		parse(ParserImplConstants.COLON);
		stmt = parseStatement();
		return dress(SLabeledStmt.make(label, stmt));
	}

	public BUTree<SBlockStmt> parseBlock() throws ParseException {
		BUTree<SNodeList> stmts;
		run();
		parse(ParserImplConstants.LBRACE);
		stmts = parseStatements();
		parse(ParserImplConstants.RBRACE);
		return dress(SBlockStmt.make(ensureNotNull(stmts)));
	}

	public BUTree<? extends SStmt> parseBlockStatement() throws ParseException {
		BUTree<? extends SStmt> ret;
		BUTree<? extends SExpr> expr;
		BUTree<? extends STypeDecl> typeDecl;
		BUTree<SNodeList> modifiers;
		if (matchBlockStatement1(0) != -1) {
			run();
			run();
			modifiers = parseModifiersNoDefault();
			typeDecl = parseClassOrInterfaceDecl(modifiers);
			ret = dress(STypeDeclarationStmt.make(typeDecl));
		} else if (matchBlockStatement3(0) != -1) {
			run();
			expr = parseVariableDeclExpression();
			parse(ParserImplConstants.SEMICOLON);
			ret = dress(SExpressionStmt.make(expr));
		} else if (match(0, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.TRY, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.THROW, ParserImplConstants.RETURN, ParserImplConstants.CONTINUE, ParserImplConstants.BREAK, ParserImplConstants.FOR, ParserImplConstants.DO, ParserImplConstants.WHILE, ParserImplConstants.IF, ParserImplConstants.SWITCH, ParserImplConstants.VOID, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.DOUBLE, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.BOOLEAN, ParserImplConstants.NEW, ParserImplConstants.SUPER, ParserImplConstants.THIS, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.STRING_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.TRUE, ParserImplConstants.FALSE, ParserImplConstants.NULL, ParserImplConstants.LPAREN, ParserImplConstants.LT, ParserImplConstants.DECR, ParserImplConstants.INCR, ParserImplConstants.SEMICOLON, ParserImplConstants.LBRACE, ParserImplConstants.ASSERT) != -1) {
			ret = parseStatement();
		} else {
			throw produceParseException(ParserImplConstants.TRY, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.THROW, ParserImplConstants.RETURN, ParserImplConstants.CONTINUE, ParserImplConstants.BREAK, ParserImplConstants.FOR, ParserImplConstants.DO, ParserImplConstants.WHILE, ParserImplConstants.IF, ParserImplConstants.SWITCH, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.DOUBLE, ParserImplConstants.BOOLEAN, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.VOID, ParserImplConstants.NEW, ParserImplConstants.SUPER, ParserImplConstants.THIS, ParserImplConstants.LPAREN, ParserImplConstants.LT, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.STRING_LITERAL, ParserImplConstants.TRUE, ParserImplConstants.FALSE, ParserImplConstants.NULL, ParserImplConstants.INCR, ParserImplConstants.DECR, ParserImplConstants.SEMICOLON, ParserImplConstants.LBRACE, ParserImplConstants.ASSERT, ParserImplConstants.AT, ParserImplConstants.PRIVATE, ParserImplConstants.PROTECTED, ParserImplConstants.PUBLIC, ParserImplConstants.TRANSIENT, ParserImplConstants.FINAL, ParserImplConstants.STATIC, ParserImplConstants.ABSTRACT, ParserImplConstants.STRICTFP, ParserImplConstants.NATIVE, ParserImplConstants.VOLATILE, ParserImplConstants.CLASS, ParserImplConstants.INTERFACE);
		}
		return ret;
	}

	/* Choice(
			Terminal("CLASS")
			Terminal("INTERFACE")
		) */
	private int matchBlockStatement1_2(int lookahead) {
		int newLookahead;
		newLookahead = match(lookahead, ParserImplConstants.CLASS);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = match(lookahead, ParserImplConstants.INTERFACE);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			NonTerminal(ModifiersNoDefault)
			Choice(
				Terminal("CLASS")
				Terminal("INTERFACE")
			)
		) */
	private int matchBlockStatement1(int lookahead) {
		lookahead = matchModifiersNoDefault(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchBlockStatement1_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			NonTerminal(VariableDeclExpression)
		) */
	private int matchBlockStatement3(int lookahead) {
		lookahead = matchVariableDeclExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	public BUTree<SVariableDeclarationExpr> parseVariableDeclExpression() throws ParseException {
		BUTree<SNodeList> modifiers;
		BUTree<SLocalVariableDecl> variableDecl;
		run();
		run();
		modifiers = parseModifiersNoDefault();
		variableDecl = parseVariableDecl(modifiers);
		return dress(SVariableDeclarationExpr.make(variableDecl));
	}

	public BUTree<SEmptyStmt> parseEmptyStatement() throws ParseException {
		run();
		parse(ParserImplConstants.SEMICOLON);
		return dress(SEmptyStmt.make());
	}

	public BUTree<SExpressionStmt> parseStatementExpression() throws ParseException {
		BUTree<? extends SExpr> expr;
		AssignOp op;
		BUTree<? extends SExpr> value;
		run();
		if (match(0, ParserImplConstants.DECR, ParserImplConstants.INCR) != -1) {
			expr = parsePrefixExpression();
		} else if (match(0, ParserImplConstants.SUPER, ParserImplConstants.THIS, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.DOUBLE, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.BOOLEAN, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.VOID, ParserImplConstants.NEW, ParserImplConstants.LT, ParserImplConstants.LPAREN, ParserImplConstants.LONG_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.STRING_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.FALSE, ParserImplConstants.TRUE, ParserImplConstants.NULL) != -1) {
			expr = parsePrimaryExpression();
			if (match(0, ParserImplConstants.INCR, ParserImplConstants.RUNSIGNEDSHIFTASSIGN, ParserImplConstants.ANDASSIGN, ParserImplConstants.XORASSIGN, ParserImplConstants.ORASSIGN, ParserImplConstants.PLUSASSIGN, ParserImplConstants.MINUSASSIGN, ParserImplConstants.LSHIFTASSIGN, ParserImplConstants.RSIGNEDSHIFTASSIGN, ParserImplConstants.ASSIGN, ParserImplConstants.STARASSIGN, ParserImplConstants.SLASHASSIGN, ParserImplConstants.REMASSIGN, ParserImplConstants.DECR) != -1) {
				if (match(0, ParserImplConstants.INCR) != -1) {
					lateRun();
					parse(ParserImplConstants.INCR);
					expr = dress(SUnaryExpr.make(UnaryOp.PostIncrement, expr));
				} else if (match(0, ParserImplConstants.DECR) != -1) {
					lateRun();
					parse(ParserImplConstants.DECR);
					expr = dress(SUnaryExpr.make(UnaryOp.PostDecrement, expr));
				} else if (match(0, ParserImplConstants.ORASSIGN, ParserImplConstants.XORASSIGN, ParserImplConstants.ANDASSIGN, ParserImplConstants.RUNSIGNEDSHIFTASSIGN, ParserImplConstants.RSIGNEDSHIFTASSIGN, ParserImplConstants.LSHIFTASSIGN, ParserImplConstants.MINUSASSIGN, ParserImplConstants.PLUSASSIGN, ParserImplConstants.REMASSIGN, ParserImplConstants.SLASHASSIGN, ParserImplConstants.STARASSIGN, ParserImplConstants.ASSIGN) != -1) {
					lateRun();
					op = parseAssignmentOperator();
					value = parseExpression();
					expr = dress(SAssignExpr.make(expr, op, value));
				} else {
					throw produceParseException(ParserImplConstants.PLUSASSIGN, ParserImplConstants.MINUSASSIGN, ParserImplConstants.SLASHASSIGN, ParserImplConstants.REMASSIGN, ParserImplConstants.RUNSIGNEDSHIFTASSIGN, ParserImplConstants.ANDASSIGN, ParserImplConstants.LSHIFTASSIGN, ParserImplConstants.RSIGNEDSHIFTASSIGN, ParserImplConstants.XORASSIGN, ParserImplConstants.ORASSIGN, ParserImplConstants.ASSIGN, ParserImplConstants.STARASSIGN, ParserImplConstants.DECR, ParserImplConstants.INCR);
				}
			}
		} else {
			throw produceParseException(ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.LT, ParserImplConstants.LPAREN, ParserImplConstants.THIS, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.TRUE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.NULL, ParserImplConstants.FALSE, ParserImplConstants.NEW, ParserImplConstants.SUPER, ParserImplConstants.BOOLEAN, ParserImplConstants.SHORT, ParserImplConstants.INT, ParserImplConstants.CHAR, ParserImplConstants.BYTE, ParserImplConstants.DOUBLE, ParserImplConstants.LONG, ParserImplConstants.FLOAT, ParserImplConstants.VOID, ParserImplConstants.DECR, ParserImplConstants.INCR);
		}
		parse(ParserImplConstants.SEMICOLON);
		return dress(SExpressionStmt.make(expr));
	}

	public BUTree<SSwitchStmt> parseSwitchStatement() throws ParseException {
		BUTree<? extends SExpr> selector;
		BUTree<SSwitchCase> entry;
		BUTree<SNodeList> entries = emptyList();
		run();
		parse(ParserImplConstants.SWITCH);
		parse(ParserImplConstants.LPAREN);
		selector = parseExpression();
		parse(ParserImplConstants.RPAREN);
		parse(ParserImplConstants.LBRACE);
		while (match(0, ParserImplConstants._DEFAULT, ParserImplConstants.CASE) != -1) {
			entry = parseSwitchEntry();
			entries = append(entries, entry);
		}
		parse(ParserImplConstants.RBRACE);
		return dress(SSwitchStmt.make(selector, entries));
	}

	public BUTree<SSwitchCase> parseSwitchEntry() throws ParseException {
		BUTree<? extends SExpr> label = null;
		BUTree<SNodeList> stmts;
		run();
		if (match(0, ParserImplConstants.CASE) != -1) {
			parse(ParserImplConstants.CASE);
			label = parseExpression();
		} else if (match(0, ParserImplConstants._DEFAULT) != -1) {
			parse(ParserImplConstants._DEFAULT);
		} else {
			throw produceParseException(ParserImplConstants._DEFAULT, ParserImplConstants.CASE);
		}
		parse(ParserImplConstants.COLON);
		stmts = parseStatements();
		return dress(SSwitchCase.make(optionOf(label), ensureNotNull(stmts)));
	}

	public BUTree<SIfStmt> parseIfStatement() throws ParseException {
		BUTree<? extends SExpr> condition;
		BUTree<? extends SStmt> thenStmt;
		BUTree<? extends SStmt> elseStmt = null;
		run();
		parse(ParserImplConstants.IF);
		parse(ParserImplConstants.LPAREN);
		condition = parseExpression();
		parse(ParserImplConstants.RPAREN);
		thenStmt = parseStatement();
		if (matchIfStatement1(0) != -1) {
			parse(ParserImplConstants.ELSE);
			elseStmt = parseStatement();
		}
		return dress(SIfStmt.make(condition, thenStmt, optionOf(elseStmt)));
	}

	/* ZeroOrOne(
			LookAhead(1)
			Terminal("ELSE")
			NonTerminal(elseStmt, Statement)
		) */
	private int matchIfStatement1(int lookahead) {
		if (match(0, ParserImplConstants.ELSE) != -1) {
			return lookahead;
		}
		return -1;
	}

	public BUTree<SWhileStmt> parseWhileStatement() throws ParseException {
		BUTree<? extends SExpr> condition;
		BUTree<? extends SStmt> body;
		run();
		parse(ParserImplConstants.WHILE);
		parse(ParserImplConstants.LPAREN);
		condition = parseExpression();
		parse(ParserImplConstants.RPAREN);
		body = parseStatement();
		return dress(SWhileStmt.make(condition, body));
	}

	public BUTree<SDoStmt> parseDoStatement() throws ParseException {
		BUTree<? extends SExpr> condition;
		BUTree<? extends SStmt> body;
		run();
		parse(ParserImplConstants.DO);
		body = parseStatement();
		parse(ParserImplConstants.WHILE);
		parse(ParserImplConstants.LPAREN);
		condition = parseExpression();
		parse(ParserImplConstants.RPAREN);
		parse(ParserImplConstants.SEMICOLON);
		return dress(SDoStmt.make(body, condition));
	}

	public BUTree<? extends SStmt> parseForStatement() throws ParseException {
		BUTree<SVariableDeclarationExpr> varExpr = null;
		BUTree<? extends SExpr> expr = null;
		BUTree<SNodeList> init = null;
		BUTree<SNodeList> update = null;
		BUTree<? extends SStmt> body;
		run();
		parse(ParserImplConstants.FOR);
		parse(ParserImplConstants.LPAREN);
		if (matchForStatement1(0) != -1) {
			varExpr = parseVariableDeclExpression();
			parse(ParserImplConstants.COLON);
			expr = parseExpression();
		} else if (match(0, ParserImplConstants.INCR, ParserImplConstants.DECR, ParserImplConstants.PLUS, ParserImplConstants.MINUS, ParserImplConstants.LPAREN, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.LT, ParserImplConstants.VOID, ParserImplConstants.DOUBLE, ParserImplConstants.SHORT, ParserImplConstants.INT, ParserImplConstants.LONG, ParserImplConstants.FLOAT, ParserImplConstants.BOOLEAN, ParserImplConstants.CHAR, ParserImplConstants.BYTE, ParserImplConstants.NEW, ParserImplConstants.SUPER, ParserImplConstants.THIS, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.NULL, ParserImplConstants.FALSE, ParserImplConstants.TRUE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.TILDE, ParserImplConstants.BANG, ParserImplConstants.TRANSIENT, ParserImplConstants.FINAL, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.VOLATILE, ParserImplConstants.PRIVATE, ParserImplConstants.PROTECTED, ParserImplConstants.STATIC, ParserImplConstants.ABSTRACT, ParserImplConstants.PUBLIC, ParserImplConstants.STRICTFP, ParserImplConstants.NATIVE, ParserImplConstants.AT, ParserImplConstants.SEMICOLON) != -1) {
			if (match(0, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LPAREN, ParserImplConstants.DECR, ParserImplConstants.INCR, ParserImplConstants.PLUS, ParserImplConstants.MINUS, ParserImplConstants.LT, ParserImplConstants.VOID, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.SUPER, ParserImplConstants.NEW, ParserImplConstants.NULL, ParserImplConstants.FALSE, ParserImplConstants.TRUE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.THIS, ParserImplConstants.TILDE, ParserImplConstants.BANG, ParserImplConstants.AT, ParserImplConstants.PROTECTED, ParserImplConstants.PRIVATE, ParserImplConstants.PUBLIC, ParserImplConstants.FINAL, ParserImplConstants.TRANSIENT, ParserImplConstants.ABSTRACT, ParserImplConstants.STATIC, ParserImplConstants.NATIVE, ParserImplConstants.STRICTFP, ParserImplConstants.VOLATILE, ParserImplConstants.SYNCHRONIZED) != -1) {
				init = parseForInit();
			}
			parse(ParserImplConstants.SEMICOLON);
			if (match(0, ParserImplConstants.LPAREN, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.STRING_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.NULL, ParserImplConstants.TRUE, ParserImplConstants.FALSE, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.VOID, ParserImplConstants.NEW, ParserImplConstants.SUPER, ParserImplConstants.THIS, ParserImplConstants.BANG, ParserImplConstants.TILDE, ParserImplConstants.MINUS, ParserImplConstants.PLUS, ParserImplConstants.INCR, ParserImplConstants.DECR) != -1) {
				expr = parseExpression();
			}
			parse(ParserImplConstants.SEMICOLON);
			if (match(0, ParserImplConstants.LPAREN, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.MINUS, ParserImplConstants.PLUS, ParserImplConstants.BANG, ParserImplConstants.TILDE, ParserImplConstants.LONG_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.STRING_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.NULL, ParserImplConstants.FALSE, ParserImplConstants.TRUE, ParserImplConstants.VOID, ParserImplConstants.INT, ParserImplConstants.LONG, ParserImplConstants.FLOAT, ParserImplConstants.DOUBLE, ParserImplConstants.BOOLEAN, ParserImplConstants.CHAR, ParserImplConstants.BYTE, ParserImplConstants.SHORT, ParserImplConstants.LT, ParserImplConstants.NEW, ParserImplConstants.THIS, ParserImplConstants.SUPER, ParserImplConstants.DECR, ParserImplConstants.INCR) != -1) {
				update = parseForUpdate();
			}
		} else {
			throw produceParseException(ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LPAREN, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.STRING_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.FALSE, ParserImplConstants.TRUE, ParserImplConstants.NULL, ParserImplConstants.VOID, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.BOOLEAN, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.DOUBLE, ParserImplConstants.NEW, ParserImplConstants.SUPER, ParserImplConstants.THIS, ParserImplConstants.LT, ParserImplConstants.BANG, ParserImplConstants.TILDE, ParserImplConstants.MINUS, ParserImplConstants.PLUS, ParserImplConstants.DECR, ParserImplConstants.INCR, ParserImplConstants.VOLATILE, ParserImplConstants.TRANSIENT, ParserImplConstants.FINAL, ParserImplConstants.STATIC, ParserImplConstants.AT, ParserImplConstants.STRICTFP, ParserImplConstants.NATIVE, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.ABSTRACT, ParserImplConstants.PRIVATE, ParserImplConstants.PROTECTED, ParserImplConstants.PUBLIC, ParserImplConstants.SEMICOLON);
		}
		parse(ParserImplConstants.RPAREN);
		body = parseStatement();
		if (varExpr != null)
			return dress(SForeachStmt.make(varExpr, expr, body));
		else
			return dress(SForStmt.make(init, expr, update, body));

	}

	/* Sequence(
			NonTerminal(VariableDeclExpression)
			Terminal("COLON")
		) */
	private int matchForStatement1(int lookahead) {
		lookahead = matchVariableDeclExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.COLON);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	public BUTree<SNodeList> parseForInit() throws ParseException {
		BUTree<SNodeList> ret;
		BUTree<? extends SExpr> expr;
		if (matchForInit1(0) != -1) {
			expr = parseVariableDeclExpression();
			ret = emptyList();
			ret = append(ret, expr);
		} else if (match(0, ParserImplConstants.LPAREN, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.STRING_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.NULL, ParserImplConstants.TRUE, ParserImplConstants.FALSE, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.VOID, ParserImplConstants.NEW, ParserImplConstants.SUPER, ParserImplConstants.THIS, ParserImplConstants.BANG, ParserImplConstants.TILDE, ParserImplConstants.MINUS, ParserImplConstants.PLUS, ParserImplConstants.INCR, ParserImplConstants.DECR) != -1) {
			ret = parseExpressionList();
		} else {
			throw produceParseException(ParserImplConstants.BOOLEAN, ParserImplConstants.CHAR, ParserImplConstants.BYTE, ParserImplConstants.SHORT, ParserImplConstants.INT, ParserImplConstants.LONG, ParserImplConstants.FLOAT, ParserImplConstants.DOUBLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.VOID, ParserImplConstants.SUPER, ParserImplConstants.NEW, ParserImplConstants.NULL, ParserImplConstants.FALSE, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.TRUE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.THIS, ParserImplConstants.LPAREN, ParserImplConstants.LT, ParserImplConstants.BANG, ParserImplConstants.TILDE, ParserImplConstants.DECR, ParserImplConstants.INCR, ParserImplConstants.PLUS, ParserImplConstants.MINUS, ParserImplConstants.AT, ParserImplConstants.PUBLIC, ParserImplConstants.PROTECTED, ParserImplConstants.PRIVATE, ParserImplConstants.ABSTRACT, ParserImplConstants.STATIC, ParserImplConstants.FINAL, ParserImplConstants.TRANSIENT, ParserImplConstants.VOLATILE, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.NATIVE, ParserImplConstants.STRICTFP);
		}
		return ret;
	}

	/* Sequence(
			NonTerminal(Modifiers)
			NonTerminal(Type)
			NonTerminal(Name)
		) */
	private int matchForInit1(int lookahead) {
		lookahead = matchModifiers(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchType(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	public BUTree<SNodeList> parseExpressionList() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<? extends SExpr> expr;
		expr = parseExpression();
		ret = append(ret, expr);
		while (match(0, ParserImplConstants.COMMA) != -1) {
			parse(ParserImplConstants.COMMA);
			expr = parseExpression();
			ret = append(ret, expr);
		}
		return ret;
	}

	public BUTree<SNodeList> parseForUpdate() throws ParseException {
		BUTree<SNodeList> ret;
		ret = parseExpressionList();
		return ret;
	}

	public BUTree<SBreakStmt> parseBreakStatement() throws ParseException {
		BUTree<SName> id = null;
		run();
		parse(ParserImplConstants.BREAK);
		if (match(0, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER) != -1) {
			id = parseName();
		}
		parse(ParserImplConstants.SEMICOLON);
		return dress(SBreakStmt.make(optionOf(id)));
	}

	public BUTree<SContinueStmt> parseContinueStatement() throws ParseException {
		BUTree<SName> id = null;
		run();
		parse(ParserImplConstants.CONTINUE);
		if (match(0, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER) != -1) {
			id = parseName();
		}
		parse(ParserImplConstants.SEMICOLON);
		return dress(SContinueStmt.make(optionOf(id)));
	}

	public BUTree<SReturnStmt> parseReturnStatement() throws ParseException {
		BUTree<? extends SExpr> expr = null;
		run();
		parse(ParserImplConstants.RETURN);
		if (match(0, ParserImplConstants.LPAREN, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.STRING_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.NULL, ParserImplConstants.TRUE, ParserImplConstants.FALSE, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.VOID, ParserImplConstants.NEW, ParserImplConstants.SUPER, ParserImplConstants.THIS, ParserImplConstants.BANG, ParserImplConstants.TILDE, ParserImplConstants.MINUS, ParserImplConstants.PLUS, ParserImplConstants.INCR, ParserImplConstants.DECR) != -1) {
			expr = parseExpression();
		}
		parse(ParserImplConstants.SEMICOLON);
		return dress(SReturnStmt.make(optionOf(expr)));
	}

	public BUTree<SThrowStmt> parseThrowStatement() throws ParseException {
		BUTree<? extends SExpr> expr;
		run();
		parse(ParserImplConstants.THROW);
		expr = parseExpression();
		parse(ParserImplConstants.SEMICOLON);
		return dress(SThrowStmt.make(expr));
	}

	public BUTree<SSynchronizedStmt> parseSynchronizedStatement() throws ParseException {
		BUTree<? extends SExpr> expr;
		BUTree<SBlockStmt> block;
		run();
		parse(ParserImplConstants.SYNCHRONIZED);
		parse(ParserImplConstants.LPAREN);
		expr = parseExpression();
		parse(ParserImplConstants.RPAREN);
		block = parseBlock();
		return dress(SSynchronizedStmt.make(expr, block));
	}

	public BUTree<STryStmt> parseTryStatement() throws ParseException {
		BUTree<SNodeList> resources = null;
		ByRef<Boolean> trailingSemiColon = new ByRef<Boolean>(false);
		BUTree<SBlockStmt> tryBlock;
		BUTree<SBlockStmt> finallyBlock = null;
		BUTree<SNodeList> catchClauses = null;
		run();
		parse(ParserImplConstants.TRY);
		if (match(0, ParserImplConstants.LPAREN) != -1) {
			resources = parseResourceSpecification(trailingSemiColon);
			tryBlock = parseBlock();
			if (match(0, ParserImplConstants.CATCH) != -1) {
				catchClauses = parseCatchClauses();
			}
			if (match(0, ParserImplConstants.FINALLY) != -1) {
				parse(ParserImplConstants.FINALLY);
				finallyBlock = parseBlock();
			}
		} else if (match(0, ParserImplConstants.LBRACE) != -1) {
			tryBlock = parseBlock();
			if (match(0, ParserImplConstants.CATCH) != -1) {
				catchClauses = parseCatchClauses();
				if (match(0, ParserImplConstants.FINALLY) != -1) {
					parse(ParserImplConstants.FINALLY);
					finallyBlock = parseBlock();
				}
			} else if (match(0, ParserImplConstants.FINALLY) != -1) {
				parse(ParserImplConstants.FINALLY);
				finallyBlock = parseBlock();
			} else {
				throw produceParseException(ParserImplConstants.FINALLY, ParserImplConstants.CATCH);
			}
		} else {
			throw produceParseException(ParserImplConstants.LBRACE, ParserImplConstants.LPAREN);
		}
		return dress(STryStmt.make(ensureNotNull(resources), trailingSemiColon.value, tryBlock, ensureNotNull(catchClauses), optionOf(finallyBlock)));
	}

	public BUTree<SNodeList> parseCatchClauses() throws ParseException {
		BUTree<SNodeList> catchClauses = emptyList();
		BUTree<SCatchClause> catchClause;
		do {
			catchClause = parseCatchClause();
			catchClauses = append(catchClauses, catchClause);
		} while (match(0, ParserImplConstants.CATCH) != -1);
		return catchClauses;
	}

	public BUTree<SCatchClause> parseCatchClause() throws ParseException {
		BUTree<SFormalParameter> param;
		BUTree<SBlockStmt> catchBlock;
		run();
		parse(ParserImplConstants.CATCH);
		parse(ParserImplConstants.LPAREN);
		param = parseCatchFormalParameter();
		parse(ParserImplConstants.RPAREN);
		catchBlock = parseBlock();
		return dress(SCatchClause.make(param, catchBlock));
	}

	public BUTree<SFormalParameter> parseCatchFormalParameter() throws ParseException {
		BUTree<SNodeList> modifiers;
		BUTree<? extends SType> exceptType;
		BUTree<SNodeList> exceptTypes = emptyList();
		BUTree<SVariableDeclaratorId> exceptId;
		run();
		modifiers = parseModifiers();
		exceptType = parseQualifiedType(null);
		exceptTypes = append(exceptTypes, exceptType);
		if (matchCatchFormalParameter1(0) != -1) {
			lateRun();
			do {
				parse(ParserImplConstants.BIT_OR);
				exceptType = parseAnnotatedQualifiedType();
				exceptTypes = append(exceptTypes, exceptType);
			} while (match(0, ParserImplConstants.BIT_OR) != -1);
			exceptType = dress(SUnionType.make(exceptTypes));
		}
		exceptId = parseVariableDeclaratorId();
		return dress(SFormalParameter.make(modifiers, exceptType, false, exceptId));
	}

	/* Sequence(
			Terminal("BIT_OR")
		) */
	private int matchCatchFormalParameter1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.BIT_OR);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	public BUTree<SNodeList> parseResourceSpecification(ByRef<Boolean> trailingSemiColon) throws ParseException {
		BUTree<SNodeList> vars = emptyList();
		BUTree<SVariableDeclarationExpr> var;
		parse(ParserImplConstants.LPAREN);
		var = parseVariableDeclExpression();
		vars = append(vars, var);
		while (matchResourceSpecification1(0) != -1) {
			parse(ParserImplConstants.SEMICOLON);
			var = parseVariableDeclExpression();
			vars = append(vars, var);
		}
		if (matchResourceSpecification2(0) != -1) {
			parse(ParserImplConstants.SEMICOLON);
			trailingSemiColon.value = true;
		}
		parse(ParserImplConstants.RPAREN);
		return vars;
	}

	/* ZeroOrMore(
			LookAhead(2)
			Terminal("SEMICOLON")
			NonTerminal(var, VariableDeclExpression)
			Action({
				vars = append(vars, var);
			})
		) */
	private int matchResourceSpecification1(int lookahead) {
		if (match(0, ParserImplConstants.SEMICOLON) != -1) {
			if (match(1, ParserImplConstants.VOLATILE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SYNCHRONIZED) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NATIVE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FINAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ABSTRACT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRICTFP) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STATIC) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PRIVATE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PUBLIC) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PROTECTED) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRANSIENT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		return -1;
	}

	/* ZeroOrOne(
			LookAhead(2)
			Terminal("SEMICOLON")
			Action({
				trailingSemiColon.value = true;
			})
		) */
	private int matchResourceSpecification2(int lookahead) {
		if (match(0, ParserImplConstants.SEMICOLON) != -1) {
			return lookahead;
		}
		return -1;
	}

	public void parseRUNSIGNEDSHIFT() throws ParseException {
		parse(ParserImplConstants.GT);
		parse(ParserImplConstants.GT);
		parse(ParserImplConstants.GT);
		popNewWhitespaces(2);
	}

	public void parseRSIGNEDSHIFT() throws ParseException {
		parse(ParserImplConstants.GT);
		parse(ParserImplConstants.GT);
		popNewWhitespaces(1);
	}

	public BUTree<SNodeList> parseAnnotations() throws ParseException {
		BUTree<SNodeList> annotations = emptyList();
		BUTree<? extends SAnnotationExpr> annotation;
		while (match(0, ParserImplConstants.AT) != -1) {
			annotation = parseAnnotation();
			annotations = append(annotations, annotation);
		}
		return annotations;
	}

	public BUTree<? extends SAnnotationExpr> parseAnnotation() throws ParseException {
		BUTree<? extends SAnnotationExpr> ret;
		if (matchAnnotation1(0) != -1) {
			ret = parseNormalAnnotation();
		} else if (matchAnnotation4(0) != -1) {
			ret = parseSingleMemberAnnotation();
		} else if (matchAnnotation5(0) != -1) {
			ret = parseMarkerAnnotation();
		} else {
			throw produceParseException(ParserImplConstants.AT);
		}
		return ret;
	}

	/* Sequence(
			NonTerminal(Name)
			Terminal("ASSIGN")
		) */
	private int matchAnnotation1_4_1(int lookahead) {
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.ASSIGN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Choice(
			Sequence(
				NonTerminal(Name)
				Terminal("ASSIGN")
			)
			Terminal("RPAREN")
		) */
	private int matchAnnotation1_4(int lookahead) {
		int newLookahead;
		newLookahead = matchAnnotation1_4_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = match(lookahead, ParserImplConstants.RPAREN);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* Sequence(
			Terminal("AT")
			NonTerminal(QualifiedName)
			Terminal("LPAREN")
			Choice(
				Sequence(
					NonTerminal(Name)
					Terminal("ASSIGN")
				)
				Terminal("RPAREN")
			)
		) */
	private int matchAnnotation1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.AT);
		if (lookahead == -1)
			return -1;
		lookahead = matchQualifiedName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchAnnotation1_4(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("AT")
			NonTerminal(QualifiedName)
			Terminal("LPAREN")
		) */
	private int matchAnnotation4(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.AT);
		if (lookahead == -1)
			return -1;
		lookahead = matchQualifiedName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* Sequence(
			Terminal("AT")
			NonTerminal(QualifiedName)
		) */
	private int matchAnnotation5(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.AT);
		if (lookahead == -1)
			return -1;
		lookahead = matchQualifiedName(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	public BUTree<SNormalAnnotationExpr> parseNormalAnnotation() throws ParseException {
		BUTree<SQualifiedName> name;
		BUTree<SNodeList> pairs = null;
		run();
		parse(ParserImplConstants.AT);
		name = parseQualifiedName();
		parse(ParserImplConstants.LPAREN);
		if (match(0, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER) != -1) {
			pairs = parseMemberValuePairs();
		}
		parse(ParserImplConstants.RPAREN);
		return dress(SNormalAnnotationExpr.make(name, ensureNotNull(pairs)));
	}

	public BUTree<SMarkerAnnotationExpr> parseMarkerAnnotation() throws ParseException {
		BUTree<SQualifiedName> name;
		run();
		parse(ParserImplConstants.AT);
		name = parseQualifiedName();
		return dress(SMarkerAnnotationExpr.make(name));
	}

	public BUTree<SSingleMemberAnnotationExpr> parseSingleMemberAnnotation() throws ParseException {
		BUTree<SQualifiedName> name;
		BUTree<? extends SExpr> memberVal;
		run();
		parse(ParserImplConstants.AT);
		name = parseQualifiedName();
		parse(ParserImplConstants.LPAREN);
		memberVal = parseMemberValue();
		parse(ParserImplConstants.RPAREN);
		return dress(SSingleMemberAnnotationExpr.make(name, memberVal));
	}

	public BUTree<SNodeList> parseMemberValuePairs() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<SMemberValuePair> pair;
		pair = parseMemberValuePair();
		ret = append(ret, pair);
		while (match(0, ParserImplConstants.COMMA) != -1) {
			parse(ParserImplConstants.COMMA);
			pair = parseMemberValuePair();
			ret = append(ret, pair);
		}
		return ret;
	}

	public BUTree<SMemberValuePair> parseMemberValuePair() throws ParseException {
		BUTree<SName> name;
		BUTree<? extends SExpr> value;
		run();
		name = parseName();
		parse(ParserImplConstants.ASSIGN);
		value = parseMemberValue();
		return dress(SMemberValuePair.make(name, value));
	}

	public BUTree<? extends SExpr> parseMemberValue() throws ParseException {
		BUTree<? extends SExpr> ret;
		if (match(0, ParserImplConstants.AT) != -1) {
			ret = parseAnnotation();
		} else if (match(0, ParserImplConstants.LBRACE) != -1) {
			ret = parseMemberValueArrayInitializer();
		} else if (match(0, ParserImplConstants.MINUS, ParserImplConstants.PLUS, ParserImplConstants.LPAREN, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.FLOAT, ParserImplConstants.DOUBLE, ParserImplConstants.INT, ParserImplConstants.LONG, ParserImplConstants.BYTE, ParserImplConstants.SHORT, ParserImplConstants.BOOLEAN, ParserImplConstants.CHAR, ParserImplConstants.VOID, ParserImplConstants.NEW, ParserImplConstants.SUPER, ParserImplConstants.THIS, ParserImplConstants.LONG_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.STRING_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.NULL, ParserImplConstants.FALSE, ParserImplConstants.TRUE, ParserImplConstants.BANG, ParserImplConstants.TILDE, ParserImplConstants.INCR, ParserImplConstants.DECR) != -1) {
			ret = parseConditionalExpression();
		} else {
			throw produceParseException(ParserImplConstants.PLUS, ParserImplConstants.MINUS, ParserImplConstants.BANG, ParserImplConstants.TILDE, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.NULL, ParserImplConstants.TRUE, ParserImplConstants.FALSE, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.STRING_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.VOID, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.LT, ParserImplConstants.LPAREN, ParserImplConstants.THIS, ParserImplConstants.SUPER, ParserImplConstants.NEW, ParserImplConstants.DECR, ParserImplConstants.INCR, ParserImplConstants.LBRACE, ParserImplConstants.AT);
		}
		return ret;
	}

	public BUTree<? extends SExpr> parseMemberValueArrayInitializer() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<? extends SExpr> member;
		boolean trailingComma = false;
		run();
		parse(ParserImplConstants.LBRACE);
		if (match(0, ParserImplConstants.PLUS, ParserImplConstants.MINUS, ParserImplConstants.BANG, ParserImplConstants.TILDE, ParserImplConstants.SUPER, ParserImplConstants.THIS, ParserImplConstants.BOOLEAN, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.DOUBLE, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.VOID, ParserImplConstants.NEW, ParserImplConstants.LT, ParserImplConstants.LPAREN, ParserImplConstants.NULL, ParserImplConstants.FALSE, ParserImplConstants.TRUE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.DECR, ParserImplConstants.INCR, ParserImplConstants.LBRACE, ParserImplConstants.AT) != -1) {
			member = parseMemberValue();
			ret = append(ret, member);
			while (matchMemberValueArrayInitializer1(0) != -1) {
				parse(ParserImplConstants.COMMA);
				member = parseMemberValue();
				ret = append(ret, member);
			}
		}
		if (match(0, ParserImplConstants.COMMA) != -1) {
			parse(ParserImplConstants.COMMA);
			trailingComma = true;
		}
		parse(ParserImplConstants.RBRACE);
		return dress(SArrayInitializerExpr.make(ret, trailingComma));
	}

	/* ZeroOrMore(
			LookAhead(2)
			Terminal("COMMA")
			NonTerminal(member, MemberValue)
			Action({
				ret = append(ret, member);
			})
		) */
	private int matchMemberValueArrayInitializer1(int lookahead) {
		if (match(0, ParserImplConstants.COMMA) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		return -1;
	}
}
