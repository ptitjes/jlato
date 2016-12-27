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

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.coll.*;
import org.jlato.internal.bu.decl.*;
import org.jlato.internal.bu.expr.*;
import org.jlato.internal.bu.name.*;
import org.jlato.internal.bu.stmt.*;
import org.jlato.internal.bu.type.*;
import org.jlato.internal.parser.all.Grammar;
import org.jlato.parser.ParseException;
import org.jlato.tree.Problem.Severity;
import org.jlato.tree.decl.ModifierKeyword;
import org.jlato.tree.expr.AssignOp;
import org.jlato.tree.expr.BinaryOp;
import org.jlato.tree.expr.UnaryOp;
import org.jlato.tree.type.Primitive;

import java.io.IOException;

/**
 * Internal implementation of the Java parser as a recursive descent parser using ALL(*) predictions.
 */
class ParserImplementation extends ParserBaseALL {

	@Override
	protected Grammar initializeGrammar() {
		try {
			return Grammar.decode(serializedGrammar);
		} catch (IOException e) {
			throw new RuntimeException("Can't initialize grammar", e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Can't initialize grammar", e);
		}
	}

	/* Identifiers for non-terminal start states */
	static final int COMPILATION_UNIT_ENTRY = 0;

	static final int PACKAGE_DECL_ENTRY = 1;

	static final int IMPORT_DECL_ENTRY = 2;

	static final int TYPE_DECL_ENTRY = 3;

	static final int MEMBER_DECL_ENTRY = 4;

	static final int ANNOTATION_MEMBER_DECL_ENTRY = 5;

	static final int MODIFIERS_ENTRY = 6;

	static final int ANNOTATIONS_ENTRY = 7;

	static final int METHOD_DECL_ENTRY = 8;

	static final int FIELD_DECL_ENTRY = 9;

	static final int ANNOTATION_ELEMENT_DECL_ENTRY = 10;

	static final int ENUM_CONSTANT_DECL_ENTRY = 11;

	static final int FORMAL_PARAMETER_ENTRY = 12;

	static final int TYPE_PARAMETER_ENTRY = 13;

	static final int STATEMENTS_ENTRY = 14;

	static final int BLOCK_STATEMENT_ENTRY = 15;

	static final int EXPRESSION_ENTRY = 16;

	static final int TYPE_ENTRY = 17;

	static final int QUALIFIED_NAME_ENTRY = 18;

	static final int NAME_ENTRY = 19;

	static final int EPILOG = 20;

	static final int NODE_LIST_VAR = 21;

	static final int NODE_VAR = 22;

	static final int COMPILATION_UNIT = 23;

	static final int PACKAGE_DECL = 24;

	static final int IMPORT_DECLS = 25;

	static final int IMPORT_DECL = 26;

	static final int TYPE_DECLS = 27;

	static final int MODIFIERS = 28;

	static final int MODIFIERS_NO_DEFAULT = 29;

	static final int TYPE_DECL = 30;

	static final int CLASS_OR_INTERFACE_DECL = 31;

	static final int EXTENDS_LIST = 32;

	static final int IMPLEMENTS_LIST = 33;

	static final int ENUM_DECL = 34;

	static final int ENUM_CONSTANT_DECL = 35;

	static final int ANNOTATION_TYPE_DECL = 36;

	static final int ANNOTATION_TYPE_BODY = 37;

	static final int ANNOTATION_TYPE_BODY_DECL = 38;

	static final int ANNOTATION_TYPE_MEMBER_DECL = 39;

	static final int TYPE_PARAMETERS = 40;

	static final int TYPE_PARAMETER = 41;

	static final int TYPE_BOUNDS = 42;

	static final int CLASS_OR_INTERFACE_BODY = 43;

	static final int CLASS_OR_INTERFACE_BODY_DECLS = 44;

	static final int CLASS_OR_INTERFACE_BODY_DECL = 45;

	static final int FIELD_DECL = 46;

	static final int VARIABLE_DECL = 47;

	static final int VARIABLE_DECLARATORS = 48;

	static final int VARIABLE_DECLARATOR = 49;

	static final int VARIABLE_DECLARATOR_ID = 50;

	static final int ARRAY_DIMS = 51;

	static final int VARIABLE_INITIALIZER = 52;

	static final int ARRAY_INITIALIZER = 53;

	static final int METHOD_DECL = 54;

	static final int FORMAL_PARAMETERS = 55;

	static final int FORMAL_PARAMETER_LIST = 56;

	static final int FORMAL_PARAMETER = 57;

	static final int THROWS_CLAUSE = 58;

	static final int CONSTRUCTOR_DECL = 59;

	static final int EXPLICIT_CONSTRUCTOR_INVOCATION = 60;

	static final int STATEMENTS = 61;

	static final int INITIALIZER_DECL = 62;

	static final int TYPE = 63;

	static final int REFERENCE_TYPE = 64;

	static final int QUALIFIED_TYPE = 65;

	static final int TYPE_ARGUMENTS = 66;

	static final int TYPE_ARGUMENTS_OR_DIAMOND = 67;

	static final int TYPE_ARGUMENT_LIST = 68;

	static final int TYPE_ARGUMENT = 69;

	static final int WILDCARD = 70;

	static final int PRIMITIVE_TYPE = 71;

	static final int RESULT_TYPE = 72;

	static final int ANNOTATED_QUALIFIED_TYPE = 73;

	static final int QUALIFIED_NAME = 74;

	static final int NAME = 75;

	static final int EXPRESSION = 76;

	static final int ASSIGNMENT_EXPRESSION = 77;

	static final int LAMBDA_EXPRESSION = 78;

	static final int LAMBDA_EXPRESSION_WITHOUT_CAST = 79;

	static final int LAMBDA_BODY = 80;

	static final int INFERRED_FORMAL_PARAMETER_LIST = 81;

	static final int INFERRED_FORMAL_PARAMETER = 82;

	static final int ASSIGNMENT_OPERATOR = 83;

	static final int CONDITIONAL_EXPRESSION = 84;

	static final int CONDITIONAL_OR_EXPRESSION = 85;

	static final int CONDITIONAL_AND_EXPRESSION = 86;

	static final int INCLUSIVE_OR_EXPRESSION = 87;

	static final int EXCLUSIVE_OR_EXPRESSION = 88;

	static final int AND_EXPRESSION = 89;

	static final int EQUALITY_EXPRESSION = 90;

	static final int INSTANCE_OF_EXPRESSION = 91;

	static final int RELATIONAL_EXPRESSION = 92;

	static final int SHIFT_EXPRESSION = 93;

	static final int ADDITIVE_EXPRESSION = 94;

	static final int MULTIPLICATIVE_EXPRESSION = 95;

	static final int UNARY_EXPRESSION = 96;

	static final int PREFIX_EXPRESSION = 97;

	static final int UNARY_EXPRESSION_NOT_PLUS_MINUS = 98;

	static final int POSTFIX_EXPRESSION = 99;

	static final int CAST_EXPRESSION = 100;

	static final int REFERENCE_CAST_TYPE_REST = 101;

	static final int LITERAL = 102;

	static final int PRIMARY_EXPRESSION = 103;

	static final int PRIMARY_NO_NEW_ARRAY = 104;

	static final int PRIMARY_EXPRESSION_WITHOUT_SUPER_SUFFIX = 105;

	static final int PRIMARY_PREFIX = 106;

	static final int PRIMARY_SUFFIX = 107;

	static final int PRIMARY_SUFFIX_WITHOUT_SUPER = 108;

	static final int FIELD_ACCESS = 109;

	static final int METHOD_INVOCATION = 110;

	static final int ARGUMENTS = 111;

	static final int METHOD_REFERENCE_SUFFIX = 112;

	static final int CLASS_CREATION_EXPR = 113;

	static final int ARRAY_CREATION_EXPR = 114;

	static final int ARRAY_CREATION_EXPR_REST = 115;

	static final int ARRAY_DIM_EXPRS_MANDATORY = 116;

	static final int ARRAY_DIMS_MANDATORY = 117;

	static final int STATEMENT = 118;

	static final int ASSERT_STATEMENT = 119;

	static final int LABELED_STATEMENT = 120;

	static final int BLOCK = 121;

	static final int BLOCK_STATEMENT = 122;

	static final int VARIABLE_DECL_EXPRESSION = 123;

	static final int EMPTY_STATEMENT = 124;

	static final int EXPRESSION_STATEMENT = 125;

	static final int STATEMENT_EXPRESSION = 126;

	static final int SWITCH_STATEMENT = 127;

	static final int SWITCH_ENTRY = 128;

	static final int IF_STATEMENT = 129;

	static final int WHILE_STATEMENT = 130;

	static final int DO_STATEMENT = 131;

	static final int FOR_STATEMENT = 132;

	static final int FOR_INIT = 133;

	static final int STATEMENT_EXPRESSION_LIST = 134;

	static final int FOR_UPDATE = 135;

	static final int BREAK_STATEMENT = 136;

	static final int CONTINUE_STATEMENT = 137;

	static final int RETURN_STATEMENT = 138;

	static final int THROW_STATEMENT = 139;

	static final int SYNCHRONIZED_STATEMENT = 140;

	static final int TRY_STATEMENT = 141;

	static final int CATCH_CLAUSES = 142;

	static final int CATCH_CLAUSE = 143;

	static final int CATCH_FORMAL_PARAMETER = 144;

	static final int RESOURCE_SPECIFICATION = 145;

	static final int ANNOTATIONS = 146;

	static final int ANNOTATION = 147;

	static final int NORMAL_ANNOTATION = 148;

	static final int MARKER_ANNOTATION = 149;

	static final int SINGLE_ELEMENT_ANNOTATION = 150;

	static final int ELEMENT_VALUE_PAIR_LIST = 151;

	static final int ELEMENT_VALUE_PAIR = 152;

	static final int ELEMENT_VALUE = 153;

	static final int ELEMENT_VALUE_ARRAY_INITIALIZER = 154;

	static final int ELEMENT_VALUE_LIST = 155;

	/* Identifiers for (non-ll1) choice-point states */
	static final int COMPILATION_UNIT_1 = 0;

	static final int MODIFIERS_1 = 1;

	static final int ENUM_DECL_5_1_2_2 = 2;

	static final int ANNOTATION_TYPE_BODY_DECL_1_2_2 = 3;

	static final int TYPE_PARAMETER_3 = 4;

	static final int CLASS_OR_INTERFACE_BODY_DECL_1_2_2 = 5;

	static final int ARRAY_DIMS_1 = 6;

	static final int ARRAY_INITIALIZER_2_1_2 = 7;

	static final int FORMAL_PARAMETER_4 = 8;

	static final int EXPLICIT_CONSTRUCTOR_INVOCATION_1 = 9;

	static final int EXPLICIT_CONSTRUCTOR_INVOCATION_1_2_1 = 10;

	static final int STATEMENTS_1 = 11;

	static final int STATEMENTS_1_1_2_1 = 12;

	static final int TYPE_1_2_2 = 13;

	static final int QUALIFIED_TYPE_2 = 14;

	static final int QUALIFIED_TYPE_3 = 15;

	static final int QUALIFIED_TYPE_3_1_4 = 16;

	static final int QUALIFIED_NAME_2 = 17;

	static final int EXPRESSION_1 = 18;

	static final int LAMBDA_EXPRESSION_1 = 19;

	static final int LAMBDA_EXPRESSION_WITHOUT_CAST_1 = 20;

	static final int CONDITIONAL_EXPRESSION_2_1_4 = 21;

	static final int SHIFT_EXPRESSION_2 = 22;

	static final int SHIFT_EXPRESSION_2_1_1 = 23;

	static final int UNARY_EXPRESSION_NOT_PLUS_MINUS_1 = 24;

	static final int CAST_EXPRESSION_3 = 25;

	static final int PRIMARY_EXPRESSION_1 = 26;

	static final int PRIMARY_EXPRESSION_WITHOUT_SUPER_SUFFIX_2 = 27;

	static final int PRIMARY_PREFIX_1 = 28;

	static final int PRIMARY_PREFIX_1_3_2_1_2 = 29;

	static final int PRIMARY_SUFFIX_1 = 30;

	static final int PRIMARY_SUFFIX_WITHOUT_SUPER_1_1_2 = 31;

	static final int ARRAY_CREATION_EXPR_REST_1 = 32;

	static final int ARRAY_DIM_EXPRS_MANDATORY_1 = 33;

	static final int ARRAY_DIMS_MANDATORY_1 = 34;

	static final int STATEMENT_1 = 35;

	static final int BLOCK_STATEMENT_1 = 36;

	static final int FOR_STATEMENT_3 = 37;

	static final int FOR_INIT_1 = 38;

	static final int TRY_STATEMENT_2_2_2_1_2 = 39;

	static final int RESOURCE_SPECIFICATION_3 = 40;

	static final int ANNOTATION_1 = 41;

	static final int ELEMENT_VALUE_LIST_2 = 42;

	/* Identifiers for non-terminal return states */
	static final int COMPILATION_UNIT_ENTRY_1 = 1;

	static final int PACKAGE_DECL_ENTRY_1 = 4;

	static final int PACKAGE_DECL_ENTRY_2 = 3;

	static final int IMPORT_DECL_ENTRY_1 = 7;

	static final int IMPORT_DECL_ENTRY_2 = 6;

	static final int TYPE_DECL_ENTRY_1 = 10;

	static final int TYPE_DECL_ENTRY_2 = 9;

	static final int MEMBER_DECL_ENTRY_1 = 13;

	static final int MEMBER_DECL_ENTRY_2 = 12;

	static final int ANNOTATION_MEMBER_DECL_ENTRY_1 = 16;

	static final int ANNOTATION_MEMBER_DECL_ENTRY_2 = 15;

	static final int MODIFIERS_ENTRY_1 = 19;

	static final int MODIFIERS_ENTRY_2 = 18;

	static final int ANNOTATIONS_ENTRY_1 = 22;

	static final int ANNOTATIONS_ENTRY_2 = 21;

	static final int METHOD_DECL_ENTRY_1 = 25;

	static final int METHOD_DECL_ENTRY_2 = 26;

	static final int METHOD_DECL_ENTRY_3 = 24;

	static final int FIELD_DECL_ENTRY_1 = 29;

	static final int FIELD_DECL_ENTRY_2 = 30;

	static final int FIELD_DECL_ENTRY_3 = 28;

	static final int ANNOTATION_ELEMENT_DECL_ENTRY_1 = 33;

	static final int ANNOTATION_ELEMENT_DECL_ENTRY_2 = 34;

	static final int ANNOTATION_ELEMENT_DECL_ENTRY_3 = 32;

	static final int ENUM_CONSTANT_DECL_ENTRY_1 = 37;

	static final int ENUM_CONSTANT_DECL_ENTRY_2 = 36;

	static final int FORMAL_PARAMETER_ENTRY_1 = 40;

	static final int FORMAL_PARAMETER_ENTRY_2 = 39;

	static final int TYPE_PARAMETER_ENTRY_1 = 43;

	static final int TYPE_PARAMETER_ENTRY_2 = 42;

	static final int STATEMENTS_ENTRY_1 = 46;

	static final int STATEMENTS_ENTRY_2 = 45;

	static final int BLOCK_STATEMENT_ENTRY_1 = 49;

	static final int BLOCK_STATEMENT_ENTRY_2 = 48;

	static final int EXPRESSION_ENTRY_1 = 52;

	static final int EXPRESSION_ENTRY_2 = 51;

	static final int TYPE_ENTRY_1 = 55;

	static final int TYPE_ENTRY_2 = 56;

	static final int TYPE_ENTRY_3 = 54;

	static final int QUALIFIED_NAME_ENTRY_1 = 59;

	static final int QUALIFIED_NAME_ENTRY_2 = 58;

	static final int NAME_ENTRY_1 = 62;

	static final int NAME_ENTRY_2 = 61;

	static final int COMPILATION_UNIT_1_1 = 71;

	static final int COMPILATION_UNIT_2 = 73;

	static final int COMPILATION_UNIT_3 = 74;

	static final int COMPILATION_UNIT_4 = 70;

	static final int PACKAGE_DECL_1 = 77;

	static final int PACKAGE_DECL_3 = 79;

	static final int IMPORT_DECLS_1_1 = 80;

	static final int IMPORT_DECL_3 = 88;

	static final int TYPE_DECLS_1_1 = 92;

	static final int MODIFIERS_1_1_13_1 = 95;

	static final int MODIFIERS_NO_DEFAULT_1_1_12_1 = 111;

	static final int TYPE_DECL_1_2_1 = 130;

	static final int TYPE_DECL_1_2_2_1 = 127;

	static final int TYPE_DECL_1_2_2_2 = 127;

	static final int TYPE_DECL_1_2_2_3 = 127;

	static final int CLASS_OR_INTERFACE_DECL_1_1_2 = 139;

	static final int CLASS_OR_INTERFACE_DECL_1_1_3_1 = 140;

	static final int CLASS_OR_INTERFACE_DECL_1_1_4_1_2 = 142;

	static final int CLASS_OR_INTERFACE_DECL_1_1_5_1 = 136;

	static final int CLASS_OR_INTERFACE_DECL_1_2_2 = 148;

	static final int CLASS_OR_INTERFACE_DECL_1_2_3_1 = 149;

	static final int CLASS_OR_INTERFACE_DECL_1_2_4_1 = 136;

	static final int CLASS_OR_INTERFACE_DECL_2 = 135;

	static final int EXTENDS_LIST_2_1 = 153;

	static final int EXTENDS_LIST_2_2_1 = 157;

	static final int EXTENDS_LIST_2_2_2_1_2 = 157;

	static final int IMPLEMENTS_LIST_2_1 = 161;

	static final int IMPLEMENTS_LIST_2_2_1 = 165;

	static final int IMPLEMENTS_LIST_2_2_2_1_2 = 165;

	static final int ENUM_DECL_2 = 171;

	static final int ENUM_DECL_3_1 = 172;

	static final int ENUM_DECL_5_1_1 = 175;

	static final int ENUM_DECL_5_1_2_1 = 179;

	static final int ENUM_DECL_5_1_2_2_1_2 = 179;

	static final int ENUM_DECL_7_1_2 = 184;

	static final int ENUM_CONSTANT_DECL_1 = 189;

	static final int ENUM_CONSTANT_DECL_2 = 190;

	static final int ENUM_CONSTANT_DECL_3_1 = 191;

	static final int ENUM_CONSTANT_DECL_4_1 = 188;

	static final int ANNOTATION_TYPE_DECL_3 = 198;

	static final int ANNOTATION_TYPE_DECL_4 = 195;

	static final int ANNOTATION_TYPE_BODY_2_1_1 = 202;

	static final int ANNOTATION_TYPE_BODY_2_1_2_1 = 206;

	static final int ANNOTATION_TYPE_BODY_DECL_1_2_1 = 211;

	static final int ANNOTATION_TYPE_BODY_DECL_1_2_2_1 = 208;

	static final int ANNOTATION_TYPE_BODY_DECL_1_2_2_2 = 208;

	static final int ANNOTATION_TYPE_BODY_DECL_1_2_2_3 = 208;

	static final int ANNOTATION_TYPE_BODY_DECL_1_2_2_4 = 208;

	static final int ANNOTATION_TYPE_BODY_DECL_1_2_2_5 = 208;

	static final int ANNOTATION_TYPE_MEMBER_DECL_1 = 219;

	static final int ANNOTATION_TYPE_MEMBER_DECL_2 = 220;

	static final int ANNOTATION_TYPE_MEMBER_DECL_5 = 223;

	static final int ANNOTATION_TYPE_MEMBER_DECL_6_1_2 = 224;

	static final int TYPE_PARAMETERS_2_1 = 230;

	static final int TYPE_PARAMETERS_2_2_1 = 233;

	static final int TYPE_PARAMETERS_2_2_2_1_2 = 233;

	static final int TYPE_PARAMETER_1 = 238;

	static final int TYPE_PARAMETER_2 = 239;

	static final int TYPE_PARAMETER_3_1 = 237;

	static final int TYPE_BOUNDS_2_1 = 242;

	static final int TYPE_BOUNDS_2_2_1 = 246;

	static final int TYPE_BOUNDS_2_2_2_1_2 = 246;

	static final int CLASS_OR_INTERFACE_BODY_2 = 252;

	static final int CLASS_OR_INTERFACE_BODY_DECLS_1_1_1 = 254;

	static final int CLASS_OR_INTERFACE_BODY_DECLS_1_1_2_1 = 258;

	static final int CLASS_OR_INTERFACE_BODY_DECL_1_2_1 = 263;

	static final int CLASS_OR_INTERFACE_BODY_DECL_1_2_2_1_1 = 260;

	static final int CLASS_OR_INTERFACE_BODY_DECL_1_2_2_2 = 260;

	static final int CLASS_OR_INTERFACE_BODY_DECL_1_2_2_3 = 260;

	static final int CLASS_OR_INTERFACE_BODY_DECL_1_2_2_4 = 260;

	static final int CLASS_OR_INTERFACE_BODY_DECL_1_2_2_5_1 = 260;

	static final int CLASS_OR_INTERFACE_BODY_DECL_1_2_2_6_1 = 260;

	static final int CLASS_OR_INTERFACE_BODY_DECL_1_2_2_7 = 260;

	static final int FIELD_DECL_1 = 273;

	static final int FIELD_DECL_2 = 274;

	static final int VARIABLE_DECL_1 = 277;

	static final int VARIABLE_DECL_2 = 276;

	static final int VARIABLE_DECLARATORS_1 = 280;

	static final int VARIABLE_DECLARATORS_2_1_2 = 280;

	static final int VARIABLE_DECLARATOR_1 = 285;

	static final int VARIABLE_DECLARATOR_2_1_2 = 284;

	static final int VARIABLE_DECLARATOR_ID_1 = 290;

	static final int VARIABLE_DECLARATOR_ID_2 = 289;

	static final int ARRAY_DIMS_1_1_1 = 294;

	static final int VARIABLE_INITIALIZER_1_1 = 297;

	static final int VARIABLE_INITIALIZER_1_2 = 297;

	static final int ARRAY_INITIALIZER_2_1_1 = 305;

	static final int ARRAY_INITIALIZER_2_1_2_1_2 = 305;

	static final int METHOD_DECL_1_1_1 = 314;

	static final int METHOD_DECL_1_1_2 = 312;

	static final int METHOD_DECL_2 = 315;

	static final int METHOD_DECL_3 = 316;

	static final int METHOD_DECL_4 = 317;

	static final int METHOD_DECL_5 = 318;

	static final int METHOD_DECL_6_1 = 319;

	static final int METHOD_DECL_7_1 = 311;

	static final int FORMAL_PARAMETERS_2_1 = 326;

	static final int FORMAL_PARAMETER_LIST_1_1 = 329;

	static final int FORMAL_PARAMETER_LIST_1_2_1 = 332;

	static final int FORMAL_PARAMETER_LIST_1_2_2_1_2 = 332;

	static final int FORMAL_PARAMETER_1 = 337;

	static final int FORMAL_PARAMETER_2 = 338;

	static final int FORMAL_PARAMETER_3_1_1 = 341;

	static final int FORMAL_PARAMETER_4_1_1_1_1 = 345;

	static final int FORMAL_PARAMETER_4_2 = 336;

	static final int THROWS_CLAUSE_2 = 350;

	static final int THROWS_CLAUSE_3_1_2 = 350;

	static final int CONSTRUCTOR_DECL_1_1 = 355;

	static final int CONSTRUCTOR_DECL_2 = 357;

	static final int CONSTRUCTOR_DECL_3 = 358;

	static final int CONSTRUCTOR_DECL_4_1 = 359;

	static final int CONSTRUCTOR_DECL_6 = 362;

	static final int EXPLICIT_CONSTRUCTOR_INVOCATION_1_1_1_1 = 366;

	static final int EXPLICIT_CONSTRUCTOR_INVOCATION_1_1_3 = 369;

	static final int EXPLICIT_CONSTRUCTOR_INVOCATION_1_2_1_1_1 = 373;

	static final int EXPLICIT_CONSTRUCTOR_INVOCATION_1_2_2_1 = 374;

	static final int EXPLICIT_CONSTRUCTOR_INVOCATION_1_2_4 = 377;

	static final int STATEMENTS_1_1_1 = 379;

	static final int STATEMENTS_1_1_2_1_1 = 383;

	static final int STATEMENTS_1_1_2_2_1 = 383;

	static final int INITIALIZER_DECL_1 = 387;

	static final int TYPE_1_1_1 = 391;

	static final int TYPE_1_1_2_1 = 389;

	static final int TYPE_1_2_1 = 394;

	static final int TYPE_1_2_2_1 = 389;

	static final int REFERENCE_TYPE_1_1_1 = 399;

	static final int REFERENCE_TYPE_1_1_2 = 397;

	static final int REFERENCE_TYPE_1_2_1 = 401;

	static final int REFERENCE_TYPE_1_2_2_1 = 397;

	static final int QUALIFIED_TYPE_1 = 405;

	static final int QUALIFIED_TYPE_2_1 = 406;

	static final int QUALIFIED_TYPE_3_1_2 = 410;

	static final int QUALIFIED_TYPE_3_1_3 = 411;

	static final int QUALIFIED_TYPE_3_1_4_1 = 406;

	static final int TYPE_ARGUMENTS_2 = 416;

	static final int TYPE_ARGUMENTS_OR_DIAMOND_2_1 = 420;

	static final int TYPE_ARGUMENT_LIST_1_1 = 423;

	static final int TYPE_ARGUMENT_LIST_2_1 = 426;

	static final int TYPE_ARGUMENT_LIST_2_2_1_2 = 426;

	static final int TYPE_ARGUMENT_1 = 431;

	static final int TYPE_ARGUMENT_2_1 = 430;

	static final int TYPE_ARGUMENT_2_2 = 430;

	static final int WILDCARD_2_1_1_2 = 440;

	static final int WILDCARD_2_1_1_3 = 435;

	static final int WILDCARD_2_1_2_2 = 443;

	static final int WILDCARD_2_1_2_3 = 435;

	static final int RESULT_TYPE_1_2 = 455;

	static final int ANNOTATED_QUALIFIED_TYPE_1 = 460;

	static final int ANNOTATED_QUALIFIED_TYPE_2 = 459;

	static final int QUALIFIED_NAME_1 = 463;

	static final int QUALIFIED_NAME_2_1_2 = 463;

	static final int NAME_1_1 = 467;

	static final int EXPRESSION_1_1 = 471;

	static final int EXPRESSION_1_2 = 471;

	static final int ASSIGNMENT_EXPRESSION_1 = 476;

	static final int ASSIGNMENT_EXPRESSION_2_1_1 = 478;

	static final int ASSIGNMENT_EXPRESSION_2_1_2 = 475;

	static final int LAMBDA_EXPRESSION_1_1_2 = 483;

	static final int LAMBDA_EXPRESSION_1_1_3 = 484;

	static final int LAMBDA_EXPRESSION_1_1_4 = 485;

	static final int LAMBDA_EXPRESSION_1_1_6 = 480;

	static final int LAMBDA_EXPRESSION_1_2 = 480;

	static final int LAMBDA_EXPRESSION_WITHOUT_CAST_1_1_1 = 491;

	static final int LAMBDA_EXPRESSION_WITHOUT_CAST_1_1_3 = 489;

	static final int LAMBDA_EXPRESSION_WITHOUT_CAST_1_2_4 = 489;

	static final int LAMBDA_EXPRESSION_WITHOUT_CAST_1_3_2 = 499;

	static final int LAMBDA_EXPRESSION_WITHOUT_CAST_1_3_5 = 489;

	static final int LAMBDA_EXPRESSION_WITHOUT_CAST_1_4_2 = 504;

	static final int LAMBDA_EXPRESSION_WITHOUT_CAST_1_4_5 = 489;

	static final int LAMBDA_BODY_1_1_1 = 508;

	static final int LAMBDA_BODY_1_2_1 = 508;

	static final int INFERRED_FORMAL_PARAMETER_LIST_1 = 513;

	static final int INFERRED_FORMAL_PARAMETER_LIST_2_1_2 = 513;

	static final int INFERRED_FORMAL_PARAMETER_1 = 517;

	static final int CONDITIONAL_EXPRESSION_1 = 534;

	static final int CONDITIONAL_EXPRESSION_2_1_2 = 537;

	static final int CONDITIONAL_EXPRESSION_2_1_4_1 = 533;

	static final int CONDITIONAL_EXPRESSION_2_1_4_2 = 533;

	static final int CONDITIONAL_OR_EXPRESSION_1 = 543;

	static final int CONDITIONAL_OR_EXPRESSION_2_1_2 = 543;

	static final int CONDITIONAL_AND_EXPRESSION_1 = 548;

	static final int CONDITIONAL_AND_EXPRESSION_2_1_2 = 548;

	static final int INCLUSIVE_OR_EXPRESSION_1 = 553;

	static final int INCLUSIVE_OR_EXPRESSION_2_1_2 = 553;

	static final int EXCLUSIVE_OR_EXPRESSION_1 = 558;

	static final int EXCLUSIVE_OR_EXPRESSION_2_1_2 = 558;

	static final int AND_EXPRESSION_1 = 563;

	static final int AND_EXPRESSION_2_1_2 = 563;

	static final int EQUALITY_EXPRESSION_1 = 568;

	static final int EQUALITY_EXPRESSION_2_1_2 = 568;

	static final int INSTANCE_OF_EXPRESSION_1 = 575;

	static final int INSTANCE_OF_EXPRESSION_2_1_2 = 578;

	static final int INSTANCE_OF_EXPRESSION_2_1_3 = 574;

	static final int RELATIONAL_EXPRESSION_1 = 581;

	static final int RELATIONAL_EXPRESSION_2_1_2 = 581;

	static final int SHIFT_EXPRESSION_1 = 590;

	static final int SHIFT_EXPRESSION_2_1_2 = 590;

	static final int ADDITIVE_EXPRESSION_1 = 601;

	static final int ADDITIVE_EXPRESSION_2_1_2 = 601;

	static final int MULTIPLICATIVE_EXPRESSION_1 = 608;

	static final int MULTIPLICATIVE_EXPRESSION_2_1_2 = 608;

	static final int UNARY_EXPRESSION_1_1 = 615;

	static final int UNARY_EXPRESSION_1_2_2 = 615;

	static final int UNARY_EXPRESSION_1_3 = 615;

	static final int PREFIX_EXPRESSION_2 = 623;

	static final int UNARY_EXPRESSION_NOT_PLUS_MINUS_1_1_2 = 628;

	static final int UNARY_EXPRESSION_NOT_PLUS_MINUS_1_2 = 628;

	static final int UNARY_EXPRESSION_NOT_PLUS_MINUS_1_3 = 628;

	static final int POSTFIX_EXPRESSION_1 = 637;

	static final int CAST_EXPRESSION_2 = 644;

	static final int CAST_EXPRESSION_3_1_1 = 646;

	static final int CAST_EXPRESSION_3_1_3 = 642;

	static final int CAST_EXPRESSION_3_2_1 = 649;

	static final int CAST_EXPRESSION_3_2_2 = 650;

	static final int CAST_EXPRESSION_3_2_4 = 642;

	static final int REFERENCE_CAST_TYPE_REST_1_1_1_2 = 657;

	static final int REFERENCE_CAST_TYPE_REST_1_1_1_3 = 655;

	static final int PRIMARY_EXPRESSION_1_1 = 670;

	static final int PRIMARY_EXPRESSION_1_2 = 670;

	static final int PRIMARY_NO_NEW_ARRAY_1 = 675;

	static final int PRIMARY_NO_NEW_ARRAY_2_1 = 675;

	static final int PRIMARY_EXPRESSION_WITHOUT_SUPER_SUFFIX_1 = 679;

	static final int PRIMARY_EXPRESSION_WITHOUT_SUPER_SUFFIX_2_1 = 679;

	static final int PRIMARY_PREFIX_1_1 = 682;

	static final int PRIMARY_PREFIX_1_3_2_1_2_1 = 682;

	static final int PRIMARY_PREFIX_1_3_2_1_2_2 = 682;

	static final int PRIMARY_PREFIX_1_3_2_2_1 = 682;

	static final int PRIMARY_PREFIX_1_4 = 682;

	static final int PRIMARY_PREFIX_1_5_1 = 694;

	static final int PRIMARY_PREFIX_1_6_1 = 697;

	static final int PRIMARY_PREFIX_1_6_2 = 682;

	static final int PRIMARY_PREFIX_1_7_1 = 682;

	static final int PRIMARY_PREFIX_1_8 = 682;

	static final int PRIMARY_PREFIX_1_9_2 = 702;

	static final int PRIMARY_SUFFIX_1_1_1 = 704;

	static final int PRIMARY_SUFFIX_1_3 = 704;

	static final int PRIMARY_SUFFIX_WITHOUT_SUPER_1_1_2_2 = 710;

	static final int PRIMARY_SUFFIX_WITHOUT_SUPER_1_1_2_3 = 710;

	static final int PRIMARY_SUFFIX_WITHOUT_SUPER_1_1_2_4 = 710;

	static final int PRIMARY_SUFFIX_WITHOUT_SUPER_1_2_2 = 719;

	static final int FIELD_ACCESS_1 = 721;

	static final int METHOD_INVOCATION_1_1 = 724;

	static final int METHOD_INVOCATION_2 = 726;

	static final int METHOD_INVOCATION_3 = 723;

	static final int ARGUMENTS_2_1_1 = 730;

	static final int ARGUMENTS_2_1_2_1 = 734;

	static final int ARGUMENTS_2_1_2_2_1_2 = 734;

	static final int METHOD_REFERENCE_SUFFIX_2_1 = 740;

	static final int METHOD_REFERENCE_SUFFIX_3_1 = 738;

	static final int CLASS_CREATION_EXPR_2_1 = 747;

	static final int CLASS_CREATION_EXPR_3 = 749;

	static final int CLASS_CREATION_EXPR_4 = 750;

	static final int CLASS_CREATION_EXPR_5 = 751;

	static final int CLASS_CREATION_EXPR_6_1 = 745;

	static final int ARRAY_CREATION_EXPR_2_1 = 756;

	static final int ARRAY_CREATION_EXPR_3 = 758;

	static final int ARRAY_CREATION_EXPR_4_1 = 759;

	static final int ARRAY_CREATION_EXPR_4_2 = 759;

	static final int ARRAY_CREATION_EXPR_5 = 754;

	static final int ARRAY_CREATION_EXPR_REST_1_1_1 = 765;

	static final int ARRAY_CREATION_EXPR_REST_1_1_2 = 763;

	static final int ARRAY_CREATION_EXPR_REST_1_2_1 = 767;

	static final int ARRAY_CREATION_EXPR_REST_1_2_2 = 763;

	static final int ARRAY_DIM_EXPRS_MANDATORY_1_1_1 = 771;

	static final int ARRAY_DIM_EXPRS_MANDATORY_1_1_3 = 773;

	static final int ARRAY_DIMS_MANDATORY_1_1_1 = 777;

	static final int STATEMENT_1_1 = 780;

	static final int STATEMENT_1_2 = 780;

	static final int STATEMENT_1_3 = 780;

	static final int STATEMENT_1_4 = 780;

	static final int STATEMENT_1_5 = 780;

	static final int STATEMENT_1_6 = 780;

	static final int STATEMENT_1_7 = 780;

	static final int STATEMENT_1_8 = 780;

	static final int STATEMENT_1_9 = 780;

	static final int STATEMENT_1_10 = 780;

	static final int STATEMENT_1_11 = 780;

	static final int STATEMENT_1_12 = 780;

	static final int STATEMENT_1_13 = 780;

	static final int STATEMENT_1_14 = 780;

	static final int STATEMENT_1_15 = 780;

	static final int STATEMENT_1_16 = 780;

	static final int ASSERT_STATEMENT_2 = 800;

	static final int ASSERT_STATEMENT_3_1_2 = 801;

	static final int LABELED_STATEMENT_1 = 806;

	static final int LABELED_STATEMENT_3 = 805;

	static final int BLOCK_2 = 811;

	static final int BLOCK_STATEMENT_1_1_1 = 815;

	static final int BLOCK_STATEMENT_1_1_2 = 813;

	static final int BLOCK_STATEMENT_1_2_1 = 817;

	static final int BLOCK_STATEMENT_1_3 = 813;

	static final int VARIABLE_DECL_EXPRESSION_1 = 821;

	static final int VARIABLE_DECL_EXPRESSION_2 = 820;

	static final int EXPRESSION_STATEMENT_1 = 826;

	static final int STATEMENT_EXPRESSION_1 = 828;

	static final int SWITCH_STATEMENT_3 = 833;

	static final int SWITCH_STATEMENT_6_1 = 835;

	static final int SWITCH_ENTRY_1_1_2 = 840;

	static final int SWITCH_ENTRY_3 = 839;

	static final int IF_STATEMENT_3 = 849;

	static final int IF_STATEMENT_5 = 851;

	static final int IF_STATEMENT_6_1_2 = 846;

	static final int WHILE_STATEMENT_3 = 858;

	static final int WHILE_STATEMENT_5 = 855;

	static final int DO_STATEMENT_2 = 863;

	static final int DO_STATEMENT_5 = 866;

	static final int FOR_STATEMENT_3_1_1 = 874;

	static final int FOR_STATEMENT_3_1_3 = 872;

	static final int FOR_STATEMENT_3_2_1_1 = 877;

	static final int FOR_STATEMENT_3_2_3_1 = 880;

	static final int FOR_STATEMENT_3_2_5_1 = 872;

	static final int FOR_STATEMENT_5 = 869;

	static final int FOR_INIT_1_1_1 = 886;

	static final int FOR_INIT_1_2 = 886;

	static final int STATEMENT_EXPRESSION_LIST_1 = 891;

	static final int STATEMENT_EXPRESSION_LIST_2_1_2 = 891;

	static final int FOR_UPDATE_1 = 895;

	static final int BREAK_STATEMENT_2_1 = 899;

	static final int CONTINUE_STATEMENT_2_1 = 904;

	static final int RETURN_STATEMENT_2_1 = 909;

	static final int THROW_STATEMENT_2 = 914;

	static final int SYNCHRONIZED_STATEMENT_3 = 919;

	static final int SYNCHRONIZED_STATEMENT_5 = 916;

	static final int TRY_STATEMENT_2_1_1 = 925;

	static final int TRY_STATEMENT_2_1_2 = 926;

	static final int TRY_STATEMENT_2_1_3_1 = 927;

	static final int TRY_STATEMENT_2_1_4_1_2 = 922;

	static final int TRY_STATEMENT_2_2_1 = 932;

	static final int TRY_STATEMENT_2_2_2_1_1 = 934;

	static final int TRY_STATEMENT_2_2_2_1_2_1_2 = 922;

	static final int TRY_STATEMENT_2_2_2_2_2 = 922;

	static final int CATCH_CLAUSES_1_1 = 941;

	static final int CATCH_CLAUSE_3 = 946;

	static final int CATCH_CLAUSE_5 = 943;

	static final int CATCH_FORMAL_PARAMETER_1 = 950;

	static final int CATCH_FORMAL_PARAMETER_2 = 951;

	static final int CATCH_FORMAL_PARAMETER_3_1_1_2 = 954;

	static final int CATCH_FORMAL_PARAMETER_4 = 949;

	static final int RESOURCE_SPECIFICATION_2 = 959;

	static final int RESOURCE_SPECIFICATION_3_1_2 = 959;

	static final int ANNOTATIONS_1_1 = 965;

	static final int ANNOTATION_1_1 = 969;

	static final int ANNOTATION_1_2 = 969;

	static final int ANNOTATION_1_3 = 969;

	static final int NORMAL_ANNOTATION_2 = 976;

	static final int NORMAL_ANNOTATION_4_1 = 978;

	static final int MARKER_ANNOTATION_2 = 981;

	static final int SINGLE_ELEMENT_ANNOTATION_2 = 986;

	static final int SINGLE_ELEMENT_ANNOTATION_4 = 988;

	static final int ELEMENT_VALUE_PAIR_LIST_1 = 991;

	static final int ELEMENT_VALUE_PAIR_LIST_2_1_2 = 991;

	static final int ELEMENT_VALUE_PAIR_1 = 996;

	static final int ELEMENT_VALUE_PAIR_3 = 995;

	static final int ELEMENT_VALUE_1_1 = 999;

	static final int ELEMENT_VALUE_1_2 = 999;

	static final int ELEMENT_VALUE_1_3 = 999;

	static final int ELEMENT_VALUE_ARRAY_INITIALIZER_2_1 = 1006;

	static final int ELEMENT_VALUE_LIST_1 = 1012;

	static final int ELEMENT_VALUE_LIST_2_1_2 = 1012;

	/* sequence(
		action({ entryPoint = COMPILATION_UNIT_ENTRY; })
		nonTerminal(ret, CompilationUnit)
		action({ return ret; })
	) */
	public BUTree<SCompilationUnit> parseCompilationUnitEntry() throws ParseException {
		BUTree<SCompilationUnit> ret;
		int __token;
		entryPoint = COMPILATION_UNIT_ENTRY;
		pushCallStack(COMPILATION_UNIT_ENTRY_1);
		ret = parseCompilationUnit();
		popCallStack();
		return ret;
	}

	/* sequence(
		action({ entryPoint = PACKAGE_DECL_ENTRY; })
		nonTerminal(ret, PackageDecl)
		nonTerminal(Epilog)
		action({ return dressWithPrologAndEpilog(ret); })
	) */
	public BUTree<SPackageDecl> parsePackageDeclEntry() throws ParseException {
		BUTree<SPackageDecl> ret;
		int __token;
		entryPoint = PACKAGE_DECL_ENTRY;
		pushCallStack(PACKAGE_DECL_ENTRY_1);
		ret = parsePackageDecl();
		popCallStack();
		pushCallStack(PACKAGE_DECL_ENTRY_2);
		parseEpilog();
		popCallStack();
		return dressWithPrologAndEpilog(ret);
	}

	/* sequence(
		action({ entryPoint = IMPORT_DECL_ENTRY; })
		nonTerminal(ret, ImportDecl)
		nonTerminal(Epilog)
		action({ return dressWithPrologAndEpilog(ret); })
	) */
	public BUTree<SImportDecl> parseImportDeclEntry() throws ParseException {
		BUTree<SImportDecl> ret;
		int __token;
		entryPoint = IMPORT_DECL_ENTRY;
		pushCallStack(IMPORT_DECL_ENTRY_1);
		ret = parseImportDecl();
		popCallStack();
		pushCallStack(IMPORT_DECL_ENTRY_2);
		parseEpilog();
		popCallStack();
		return dressWithPrologAndEpilog(ret);
	}

	/* sequence(
		action({ entryPoint = TYPE_DECL_ENTRY; })
		nonTerminal(ret, TypeDecl)
		nonTerminal(Epilog)
		action({ return dressWithPrologAndEpilog(ret); })
	) */
	public BUTree<? extends STypeDecl> parseTypeDeclEntry() throws ParseException {
		BUTree<? extends STypeDecl> ret;
		int __token;
		entryPoint = TYPE_DECL_ENTRY;
		pushCallStack(TYPE_DECL_ENTRY_1);
		ret = parseTypeDecl();
		popCallStack();
		pushCallStack(TYPE_DECL_ENTRY_2);
		parseEpilog();
		popCallStack();
		return dressWithPrologAndEpilog(ret);
	}

	/* sequence(
		action({ entryPoint = MEMBER_DECL_ENTRY; })
		nonTerminal(ret, ClassOrInterfaceBodyDecl)
		nonTerminal(Epilog)
		action({ return dressWithPrologAndEpilog(ret); })
	) */
	public BUTree<? extends SMemberDecl> parseMemberDeclEntry(TypeKind typeKind) throws ParseException {
		BUTree<? extends SMemberDecl> ret;
		int __token;
		entryPoint = MEMBER_DECL_ENTRY;
		pushCallStack(MEMBER_DECL_ENTRY_1);
		ret = parseClassOrInterfaceBodyDecl(typeKind);
		popCallStack();
		pushCallStack(MEMBER_DECL_ENTRY_2);
		parseEpilog();
		popCallStack();
		return dressWithPrologAndEpilog(ret);
	}

	/* sequence(
		action({ entryPoint = ANNOTATION_MEMBER_DECL_ENTRY; })
		nonTerminal(ret, AnnotationTypeBodyDecl)
		nonTerminal(Epilog)
		action({ return dressWithPrologAndEpilog(ret); })
	) */
	public BUTree<? extends SMemberDecl> parseAnnotationMemberDeclEntry() throws ParseException {
		BUTree<? extends SMemberDecl> ret;
		int __token;
		entryPoint = ANNOTATION_MEMBER_DECL_ENTRY;
		pushCallStack(ANNOTATION_MEMBER_DECL_ENTRY_1);
		ret = parseAnnotationTypeBodyDecl();
		popCallStack();
		pushCallStack(ANNOTATION_MEMBER_DECL_ENTRY_2);
		parseEpilog();
		popCallStack();
		return dressWithPrologAndEpilog(ret);
	}

	/* sequence(
		action({ entryPoint = MODIFIERS_ENTRY; })
		nonTerminal(ret, Modifiers)
		nonTerminal(Epilog)
		action({ return ret; })
	) */
	public BUTree<SNodeList> parseModifiersEntry() throws ParseException {
		BUTree<SNodeList> ret;
		int __token;
		entryPoint = MODIFIERS_ENTRY;
		pushCallStack(MODIFIERS_ENTRY_1);
		ret = parseModifiers();
		popCallStack();
		pushCallStack(MODIFIERS_ENTRY_2);
		parseEpilog();
		popCallStack();
		return ret;
	}

	/* sequence(
		action({ entryPoint = ANNOTATIONS_ENTRY; })
		nonTerminal(ret, Annotations)
		nonTerminal(Epilog)
		action({ return ret; })
	) */
	public BUTree<SNodeList> parseAnnotationsEntry() throws ParseException {
		BUTree<SNodeList> ret;
		int __token;
		entryPoint = ANNOTATIONS_ENTRY;
		pushCallStack(ANNOTATIONS_ENTRY_1);
		ret = parseAnnotations();
		popCallStack();
		pushCallStack(ANNOTATIONS_ENTRY_2);
		parseEpilog();
		popCallStack();
		return ret;
	}

	/* sequence(
		action({ entryPoint = METHOD_DECL_ENTRY; })
		action({ run(); })
		nonTerminal(modifiers, Modifiers)
		nonTerminal(ret, MethodDecl)
		nonTerminal(Epilog)
		action({ return dressWithPrologAndEpilog(ret); })
	) */
	public BUTree<SMethodDecl> parseMethodDeclEntry() throws ParseException {
		BUTree<SNodeList> modifiers;
		BUTree<SMethodDecl> ret;
		int __token;
		entryPoint = METHOD_DECL_ENTRY;
		run();
		pushCallStack(METHOD_DECL_ENTRY_1);
		modifiers = parseModifiers();
		popCallStack();
		pushCallStack(METHOD_DECL_ENTRY_2);
		ret = parseMethodDecl(modifiers);
		popCallStack();
		pushCallStack(METHOD_DECL_ENTRY_3);
		parseEpilog();
		popCallStack();
		return dressWithPrologAndEpilog(ret);
	}

	/* sequence(
		action({ entryPoint = FIELD_DECL_ENTRY; })
		action({ run(); })
		nonTerminal(modifiers, Modifiers)
		nonTerminal(ret, FieldDecl)
		nonTerminal(Epilog)
		action({ return dressWithPrologAndEpilog(ret); })
	) */
	public BUTree<SFieldDecl> parseFieldDeclEntry() throws ParseException {
		BUTree<SNodeList> modifiers;
		BUTree<SFieldDecl> ret;
		int __token;
		entryPoint = FIELD_DECL_ENTRY;
		run();
		pushCallStack(FIELD_DECL_ENTRY_1);
		modifiers = parseModifiers();
		popCallStack();
		pushCallStack(FIELD_DECL_ENTRY_2);
		ret = parseFieldDecl(modifiers);
		popCallStack();
		pushCallStack(FIELD_DECL_ENTRY_3);
		parseEpilog();
		popCallStack();
		return dressWithPrologAndEpilog(ret);
	}

	/* sequence(
		action({ entryPoint = ANNOTATION_ELEMENT_DECL_ENTRY; })
		action({ run(); })
		nonTerminal(modifiers, Modifiers)
		nonTerminal(ret, AnnotationTypeMemberDecl)
		nonTerminal(Epilog)
		action({ return dressWithPrologAndEpilog(ret); })
	) */
	public BUTree<SAnnotationMemberDecl> parseAnnotationElementDeclEntry() throws ParseException {
		BUTree<SNodeList> modifiers;
		BUTree<SAnnotationMemberDecl> ret;
		int __token;
		entryPoint = ANNOTATION_ELEMENT_DECL_ENTRY;
		run();
		pushCallStack(ANNOTATION_ELEMENT_DECL_ENTRY_1);
		modifiers = parseModifiers();
		popCallStack();
		pushCallStack(ANNOTATION_ELEMENT_DECL_ENTRY_2);
		ret = parseAnnotationTypeMemberDecl(modifiers);
		popCallStack();
		pushCallStack(ANNOTATION_ELEMENT_DECL_ENTRY_3);
		parseEpilog();
		popCallStack();
		return dressWithPrologAndEpilog(ret);
	}

	/* sequence(
		action({ entryPoint = ENUM_CONSTANT_DECL_ENTRY; })
		nonTerminal(ret, EnumConstantDecl)
		nonTerminal(Epilog)
		action({ return dressWithPrologAndEpilog(ret); })
	) */
	public BUTree<SEnumConstantDecl> parseEnumConstantDeclEntry() throws ParseException {
		BUTree<SEnumConstantDecl> ret;
		int __token;
		entryPoint = ENUM_CONSTANT_DECL_ENTRY;
		pushCallStack(ENUM_CONSTANT_DECL_ENTRY_1);
		ret = parseEnumConstantDecl();
		popCallStack();
		pushCallStack(ENUM_CONSTANT_DECL_ENTRY_2);
		parseEpilog();
		popCallStack();
		return dressWithPrologAndEpilog(ret);
	}

	/* sequence(
		action({ entryPoint = FORMAL_PARAMETER_ENTRY; })
		nonTerminal(ret, FormalParameter)
		nonTerminal(Epilog)
		action({ return dressWithPrologAndEpilog(ret); })
	) */
	public BUTree<SFormalParameter> parseFormalParameterEntry() throws ParseException {
		BUTree<SFormalParameter> ret;
		int __token;
		entryPoint = FORMAL_PARAMETER_ENTRY;
		pushCallStack(FORMAL_PARAMETER_ENTRY_1);
		ret = parseFormalParameter();
		popCallStack();
		pushCallStack(FORMAL_PARAMETER_ENTRY_2);
		parseEpilog();
		popCallStack();
		return dressWithPrologAndEpilog(ret);
	}

	/* sequence(
		action({ entryPoint = TYPE_PARAMETER_ENTRY; })
		nonTerminal(ret, TypeParameter)
		nonTerminal(Epilog)
		action({ return dressWithPrologAndEpilog(ret); })
	) */
	public BUTree<STypeParameter> parseTypeParameterEntry() throws ParseException {
		BUTree<STypeParameter> ret;
		int __token;
		entryPoint = TYPE_PARAMETER_ENTRY;
		pushCallStack(TYPE_PARAMETER_ENTRY_1);
		ret = parseTypeParameter();
		popCallStack();
		pushCallStack(TYPE_PARAMETER_ENTRY_2);
		parseEpilog();
		popCallStack();
		return dressWithPrologAndEpilog(ret);
	}

	/* sequence(
		action({ entryPoint = STATEMENTS_ENTRY; })
		nonTerminal(ret, Statements)
		nonTerminal(Epilog)
		action({ return ret; })
	) */
	public BUTree<SNodeList> parseStatementsEntry() throws ParseException {
		BUTree<SNodeList> ret;
		int __token;
		entryPoint = STATEMENTS_ENTRY;
		pushCallStack(STATEMENTS_ENTRY_1);
		ret = parseStatements();
		popCallStack();
		pushCallStack(STATEMENTS_ENTRY_2);
		parseEpilog();
		popCallStack();
		return ret;
	}

	/* sequence(
		action({ entryPoint = BLOCK_STATEMENT_ENTRY; })
		nonTerminal(ret, BlockStatement)
		nonTerminal(Epilog)
		action({ return dressWithPrologAndEpilog(ret); })
	) */
	public BUTree<? extends SStmt> parseBlockStatementEntry() throws ParseException {
		BUTree<? extends SStmt> ret;
		int __token;
		entryPoint = BLOCK_STATEMENT_ENTRY;
		pushCallStack(BLOCK_STATEMENT_ENTRY_1);
		ret = parseBlockStatement();
		popCallStack();
		pushCallStack(BLOCK_STATEMENT_ENTRY_2);
		parseEpilog();
		popCallStack();
		return dressWithPrologAndEpilog(ret);
	}

	/* sequence(
		action({ entryPoint = EXPRESSION_ENTRY; })
		nonTerminal(ret, Expression)
		nonTerminal(Epilog)
		action({ return dressWithPrologAndEpilog(ret); })
	) */
	public BUTree<? extends SExpr> parseExpressionEntry() throws ParseException {
		BUTree<? extends SExpr> ret;
		int __token;
		entryPoint = EXPRESSION_ENTRY;
		pushCallStack(EXPRESSION_ENTRY_1);
		ret = parseExpression();
		popCallStack();
		pushCallStack(EXPRESSION_ENTRY_2);
		parseEpilog();
		popCallStack();
		return dressWithPrologAndEpilog(ret);
	}

	/* sequence(
		action({ entryPoint = TYPE_ENTRY; })
		action({ run(); })
		nonTerminal(annotations, Annotations)
		nonTerminal(ret, Type)
		nonTerminal(Epilog)
		action({ return dressWithPrologAndEpilog(ret); })
	) */
	public BUTree<? extends SType> parseTypeEntry() throws ParseException {
		BUTree<SNodeList> annotations;
		BUTree<? extends SType> ret;
		int __token;
		entryPoint = TYPE_ENTRY;
		run();
		pushCallStack(TYPE_ENTRY_1);
		annotations = parseAnnotations();
		popCallStack();
		pushCallStack(TYPE_ENTRY_2);
		ret = parseType(annotations);
		popCallStack();
		pushCallStack(TYPE_ENTRY_3);
		parseEpilog();
		popCallStack();
		return dressWithPrologAndEpilog(ret);
	}

	/* sequence(
		action({ entryPoint = QUALIFIED_NAME_ENTRY; })
		nonTerminal(ret, QualifiedName)
		nonTerminal(Epilog)
		action({ return dressWithPrologAndEpilog(ret); })
	) */
	public BUTree<SQualifiedName> parseQualifiedNameEntry() throws ParseException {
		BUTree<SQualifiedName> ret;
		int __token;
		entryPoint = QUALIFIED_NAME_ENTRY;
		pushCallStack(QUALIFIED_NAME_ENTRY_1);
		ret = parseQualifiedName();
		popCallStack();
		pushCallStack(QUALIFIED_NAME_ENTRY_2);
		parseEpilog();
		popCallStack();
		return dressWithPrologAndEpilog(ret);
	}

	/* sequence(
		action({ entryPoint = NAME_ENTRY; })
		nonTerminal(ret, Name)
		nonTerminal(Epilog)
		action({ return dressWithPrologAndEpilog(ret); })
	) */
	public BUTree<SName> parseNameEntry() throws ParseException {
		BUTree<SName> ret;
		int __token;
		entryPoint = NAME_ENTRY;
		pushCallStack(NAME_ENTRY_1);
		ret = parseName();
		popCallStack();
		pushCallStack(NAME_ENTRY_2);
		parseEpilog();
		popCallStack();
		return dressWithPrologAndEpilog(ret);
	}

	/* sequence(
		terminal(EOF)
	) */
	protected void parseEpilog() throws ParseException {
		int __token;
		consume(TokenType.EOF);
	}

	/* sequence(
		terminal(id, NODE_LIST_VARIABLE)
		action({ return makeVar(id); })
	) */
	protected BUTree<SNodeList> parseNodeListVar() throws ParseException {
		Token id;
		int __token;
		id = consume(TokenType.NODE_LIST_VARIABLE);
		return makeVar(id);
	}

	/* sequence(
		terminal(id, NODE_VARIABLE)
		action({ return makeVar(id); })
	) */
	protected BUTree<SName> parseNodeVar() throws ParseException {
		Token id;
		int __token;
		id = consume(TokenType.NODE_VARIABLE);
		return makeVar(id);
	}

	/* sequence(
		action({ run(); })
		zeroOrOne(
			nonTerminal(packageDecl, PackageDecl)
		)
		nonTerminal(imports, ImportDecls)
		nonTerminal(types, TypeDecls)
		action({ compilationUnit = dress(SCompilationUnit.make(packageDecl, imports, types)); })
		nonTerminal(Epilog)
		action({ return dressWithPrologAndEpilog(compilationUnit); })
	) */
	protected BUTree<SCompilationUnit> parseCompilationUnit() throws ParseException {
		BUTree<SPackageDecl> packageDecl = null;
		BUTree<SNodeList> imports;
		BUTree<SNodeList> types;
		BUTree<SCompilationUnit> compilationUnit;
		int __token;
		run();
		__token = getToken(0).kind;
		if (predict(COMPILATION_UNIT_1) == 1) {
			pushCallStack(COMPILATION_UNIT_1_1);
			packageDecl = parsePackageDecl();
			popCallStack();
		}
		pushCallStack(COMPILATION_UNIT_2);
		imports = parseImportDecls();
		popCallStack();
		pushCallStack(COMPILATION_UNIT_3);
		types = parseTypeDecls();
		popCallStack();
		compilationUnit = dress(SCompilationUnit.make(packageDecl, imports, types));
		pushCallStack(COMPILATION_UNIT_4);
		parseEpilog();
		popCallStack();
		return dressWithPrologAndEpilog(compilationUnit);
	}

	/* sequence(
		action({ run(); })
		nonTerminal(annotations, Annotations)
		terminal(PACKAGE)
		nonTerminal(name, QualifiedName)
		terminal(SEMICOLON)
		action({ return dress(SPackageDecl.make(annotations, name)); })
	) */
	protected BUTree<SPackageDecl> parsePackageDecl() throws ParseException {
		BUTree<SNodeList> annotations = null;
		BUTree<SQualifiedName> name;
		int __token;
		run();
		pushCallStack(PACKAGE_DECL_1);
		annotations = parseAnnotations();
		popCallStack();
		consume(TokenType.PACKAGE);
		pushCallStack(PACKAGE_DECL_3);
		name = parseQualifiedName();
		popCallStack();
		consume(TokenType.SEMICOLON);
		return dress(SPackageDecl.make(annotations, name));
	}

	/* sequence(
		zeroOrMore(
			nonTerminal(importDecl, ImportDecl)
			action({ imports = append(imports, importDecl); })
		)
		action({ return imports; })
	) */
	protected BUTree<SNodeList> parseImportDecls() throws ParseException {
		BUTree<SNodeList> imports = emptyList();
		BUTree<SImportDecl> importDecl = null;
		int __token;
		__token = getToken(0).kind;
		while (__token == TokenType.IMPORT) {
			pushCallStack(IMPORT_DECLS_1_1);
			importDecl = parseImportDecl();
			popCallStack();
			imports = append(imports, importDecl);
			__token = getToken(0).kind;
		}
		return imports;
	}

	/* sequence(
		action({ run(); })
		terminal(IMPORT)
		zeroOrOne(
			terminal(STATIC)
			action({ isStatic = true; })
		)
		nonTerminal(name, QualifiedName)
		zeroOrOne(
			sequence(
				terminal(DOT)
				terminal(STAR)
				action({ isAsterisk = true; })
			)
		)
		terminal(SEMICOLON)
		action({ return dress(SImportDecl.make(name, isStatic, isAsterisk)); })
	) */
	protected BUTree<SImportDecl> parseImportDecl() throws ParseException {
		BUTree<SQualifiedName> name;
		boolean isStatic = false;
		boolean isAsterisk = false;
		int __token;
		run();
		consume(TokenType.IMPORT);
		__token = getToken(0).kind;
		if (__token == TokenType.STATIC) {
			consume(TokenType.STATIC);
			isStatic = true;
		}
		pushCallStack(IMPORT_DECL_3);
		name = parseQualifiedName();
		popCallStack();
		__token = getToken(0).kind;
		if (__token == TokenType.DOT) {
			consume(TokenType.DOT);
			consume(TokenType.STAR);
			isAsterisk = true;
		}
		consume(TokenType.SEMICOLON);
		return dress(SImportDecl.make(name, isStatic, isAsterisk));
	}

	/* sequence(
		zeroOrMore(
			nonTerminal(typeDecl, TypeDecl)
			action({ types = append(types, typeDecl); })
		)
		action({ return types; })
	) */
	protected BUTree<SNodeList> parseTypeDecls() throws ParseException {
		BUTree<SNodeList> types = emptyList();
		BUTree<? extends STypeDecl> typeDecl = null;
		int __token;
		__token = getToken(0).kind;
		while (((__token - 9 & ~63) == 0 && (1L << __token - 9 & (1L << TokenType.ABSTRACT - 9 | 1L << TokenType.CLASS - 9 | 1L << TokenType.DEFAULT - 9 | 1L << TokenType.ENUM - 9 | 1L << TokenType.FINAL - 9 | 1L << TokenType.INTERFACE - 9 | 1L << TokenType.NATIVE - 9 | 1L << TokenType.PRIVATE - 9 | 1L << TokenType.PROTECTED - 9 | 1L << TokenType.PUBLIC - 9 | 1L << TokenType.STATIC - 9 | 1L << TokenType.STRICTFP - 9 | 1L << TokenType.SYNCHRONIZED - 9 | 1L << TokenType.TRANSIENT - 9 | 1L << TokenType.VOLATILE - 9)) != 0) || ((__token - 86 & ~63) == 0 && (1L << __token - 86 & (1L << TokenType.SEMICOLON - 86 | 1L << TokenType.AT - 86)) != 0)) {
			pushCallStack(TYPE_DECLS_1_1);
			typeDecl = parseTypeDecl();
			popCallStack();
			types = append(types, typeDecl);
			__token = getToken(0).kind;
		}
		return types;
	}

	/* sequence(
		zeroOrMore(
			choice(
				sequence(
					terminal(PUBLIC)
					action({ modifiers = append(modifiers, SModifier.make(ModifierKeyword.Public)); })
				)
				sequence(
					terminal(PROTECTED)
					action({ modifiers = append(modifiers, SModifier.make(ModifierKeyword.Protected)); })
				)
				sequence(
					terminal(PRIVATE)
					action({ modifiers = append(modifiers, SModifier.make(ModifierKeyword.Private)); })
				)
				sequence(
					terminal(ABSTRACT)
					action({ modifiers = append(modifiers, SModifier.make(ModifierKeyword.Abstract)); })
				)
				sequence(
					terminal(DEFAULT)
					action({ modifiers = append(modifiers, SModifier.make(ModifierKeyword.Default)); })
				)
				sequence(
					terminal(STATIC)
					action({ modifiers = append(modifiers, SModifier.make(ModifierKeyword.Static)); })
				)
				sequence(
					terminal(FINAL)
					action({ modifiers = append(modifiers, SModifier.make(ModifierKeyword.Final)); })
				)
				sequence(
					terminal(TRANSIENT)
					action({ modifiers = append(modifiers, SModifier.make(ModifierKeyword.Transient)); })
				)
				sequence(
					terminal(VOLATILE)
					action({ modifiers = append(modifiers, SModifier.make(ModifierKeyword.Volatile)); })
				)
				sequence(
					terminal(SYNCHRONIZED)
					action({ modifiers = append(modifiers, SModifier.make(ModifierKeyword.Synchronized)); })
				)
				sequence(
					terminal(NATIVE)
					action({ modifiers = append(modifiers, SModifier.make(ModifierKeyword.Native)); })
				)
				sequence(
					terminal(STRICTFP)
					action({ modifiers = append(modifiers, SModifier.make(ModifierKeyword.StrictFP)); })
				)
				sequence(
					nonTerminal(ann, Annotation)
					action({ modifiers = append(modifiers, ann); })
				)
			)
		)
		action({ return modifiers; })
	) */
	protected BUTree<SNodeList> parseModifiers() throws ParseException {
		BUTree<SNodeList> modifiers = emptyList();
		BUTree<? extends SAnnotationExpr> ann;
		int __token;
		__token = getToken(0).kind;
		while (predict(MODIFIERS_1) == 1) {
			__token = getToken(0).kind;
			if (__token == TokenType.PUBLIC) {
				consume(TokenType.PUBLIC);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Public));
			} else if (__token == TokenType.PROTECTED) {
				consume(TokenType.PROTECTED);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Protected));
			} else if (__token == TokenType.PRIVATE) {
				consume(TokenType.PRIVATE);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Private));
			} else if (__token == TokenType.ABSTRACT) {
				consume(TokenType.ABSTRACT);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Abstract));
			} else if (__token == TokenType.DEFAULT) {
				consume(TokenType.DEFAULT);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Default));
			} else if (__token == TokenType.STATIC) {
				consume(TokenType.STATIC);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Static));
			} else if (__token == TokenType.FINAL) {
				consume(TokenType.FINAL);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Final));
			} else if (__token == TokenType.TRANSIENT) {
				consume(TokenType.TRANSIENT);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Transient));
			} else if (__token == TokenType.VOLATILE) {
				consume(TokenType.VOLATILE);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Volatile));
			} else if (__token == TokenType.SYNCHRONIZED) {
				consume(TokenType.SYNCHRONIZED);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Synchronized));
			} else if (__token == TokenType.NATIVE) {
				consume(TokenType.NATIVE);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Native));
			} else if (__token == TokenType.STRICTFP) {
				consume(TokenType.STRICTFP);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.StrictFP));
			} else if (__token == TokenType.AT) {
				pushCallStack(MODIFIERS_1_1_13_1);
				ann = parseAnnotation();
				popCallStack();
				modifiers = append(modifiers, ann);
			} else
				throw produceParseException(TokenType.ABSTRACT, TokenType.DEFAULT, TokenType.FINAL, TokenType.NATIVE, TokenType.PRIVATE, TokenType.PROTECTED, TokenType.PUBLIC, TokenType.STATIC, TokenType.STRICTFP, TokenType.SYNCHRONIZED, TokenType.TRANSIENT, TokenType.VOLATILE, TokenType.AT);
			__token = getToken(0).kind;
		}
		return modifiers;
	}

	/* sequence(
		zeroOrMore(
			choice(
				sequence(
					terminal(PUBLIC)
					action({ modifiers = append(modifiers, SModifier.make(ModifierKeyword.Public)); })
				)
				sequence(
					terminal(PROTECTED)
					action({ modifiers = append(modifiers, SModifier.make(ModifierKeyword.Protected)); })
				)
				sequence(
					terminal(PRIVATE)
					action({ modifiers = append(modifiers, SModifier.make(ModifierKeyword.Private)); })
				)
				sequence(
					terminal(ABSTRACT)
					action({ modifiers = append(modifiers, SModifier.make(ModifierKeyword.Abstract)); })
				)
				sequence(
					terminal(STATIC)
					action({ modifiers = append(modifiers, SModifier.make(ModifierKeyword.Static)); })
				)
				sequence(
					terminal(FINAL)
					action({ modifiers = append(modifiers, SModifier.make(ModifierKeyword.Final)); })
				)
				sequence(
					terminal(TRANSIENT)
					action({ modifiers = append(modifiers, SModifier.make(ModifierKeyword.Transient)); })
				)
				sequence(
					terminal(VOLATILE)
					action({ modifiers = append(modifiers, SModifier.make(ModifierKeyword.Volatile)); })
				)
				sequence(
					terminal(SYNCHRONIZED)
					action({ modifiers = append(modifiers, SModifier.make(ModifierKeyword.Synchronized)); })
				)
				sequence(
					terminal(NATIVE)
					action({ modifiers = append(modifiers, SModifier.make(ModifierKeyword.Native)); })
				)
				sequence(
					terminal(STRICTFP)
					action({ modifiers = append(modifiers, SModifier.make(ModifierKeyword.StrictFP)); })
				)
				sequence(
					nonTerminal(ann, Annotation)
					action({ modifiers = append(modifiers, ann); })
				)
			)
		)
		action({ return modifiers; })
	) */
	protected BUTree<SNodeList> parseModifiersNoDefault() throws ParseException {
		BUTree<SNodeList> modifiers = emptyList();
		BUTree<? extends SAnnotationExpr> ann;
		int __token;
		__token = getToken(0).kind;
		while (((__token - 9 & ~63) == 0 && (1L << __token - 9 & (1L << TokenType.ABSTRACT - 9 | 1L << TokenType.FINAL - 9 | 1L << TokenType.NATIVE - 9 | 1L << TokenType.PRIVATE - 9 | 1L << TokenType.PROTECTED - 9 | 1L << TokenType.PUBLIC - 9 | 1L << TokenType.STATIC - 9 | 1L << TokenType.STRICTFP - 9 | 1L << TokenType.SYNCHRONIZED - 9 | 1L << TokenType.TRANSIENT - 9 | 1L << TokenType.VOLATILE - 9)) != 0) || ((__token - 89 & ~63) == 0 && (1L << __token - 89 & (1L << TokenType.AT - 89)) != 0)) {
			__token = getToken(0).kind;
			if (__token == TokenType.PUBLIC) {
				consume(TokenType.PUBLIC);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Public));
			} else if (__token == TokenType.PROTECTED) {
				consume(TokenType.PROTECTED);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Protected));
			} else if (__token == TokenType.PRIVATE) {
				consume(TokenType.PRIVATE);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Private));
			} else if (__token == TokenType.ABSTRACT) {
				consume(TokenType.ABSTRACT);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Abstract));
			} else if (__token == TokenType.STATIC) {
				consume(TokenType.STATIC);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Static));
			} else if (__token == TokenType.FINAL) {
				consume(TokenType.FINAL);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Final));
			} else if (__token == TokenType.TRANSIENT) {
				consume(TokenType.TRANSIENT);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Transient));
			} else if (__token == TokenType.VOLATILE) {
				consume(TokenType.VOLATILE);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Volatile));
			} else if (__token == TokenType.SYNCHRONIZED) {
				consume(TokenType.SYNCHRONIZED);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Synchronized));
			} else if (__token == TokenType.NATIVE) {
				consume(TokenType.NATIVE);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Native));
			} else if (__token == TokenType.STRICTFP) {
				consume(TokenType.STRICTFP);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.StrictFP));
			} else if (__token == TokenType.AT) {
				pushCallStack(MODIFIERS_NO_DEFAULT_1_1_12_1);
				ann = parseAnnotation();
				popCallStack();
				modifiers = append(modifiers, ann);
			} else
				throw produceParseException(TokenType.ABSTRACT, TokenType.FINAL, TokenType.NATIVE, TokenType.PRIVATE, TokenType.PROTECTED, TokenType.PUBLIC, TokenType.STATIC, TokenType.STRICTFP, TokenType.SYNCHRONIZED, TokenType.TRANSIENT, TokenType.VOLATILE, TokenType.AT);
			__token = getToken(0).kind;
		}
		return modifiers;
	}

	/* sequence(
		action({ run(); })
		choice(
			sequence(
				terminal(SEMICOLON)
				action({ ret = dress(SEmptyTypeDecl.make()); })
			)
			sequence(
				nonTerminal(modifiers, Modifiers)
				choice(
					nonTerminal(ret, ClassOrInterfaceDecl)
					nonTerminal(ret, EnumDecl)
					nonTerminal(ret, AnnotationTypeDecl)
				)
			)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends STypeDecl> parseTypeDecl() throws ParseException {
		BUTree<SNodeList> modifiers;
		BUTree<? extends STypeDecl> ret;
		int __token;
		run();
		__token = getToken(0).kind;
		if (__token == TokenType.SEMICOLON) {
			consume(TokenType.SEMICOLON);
			ret = dress(SEmptyTypeDecl.make());
		} else if (((__token - 9 & ~63) == 0 && (1L << __token - 9 & (1L << TokenType.ABSTRACT - 9 | 1L << TokenType.CLASS - 9 | 1L << TokenType.DEFAULT - 9 | 1L << TokenType.ENUM - 9 | 1L << TokenType.FINAL - 9 | 1L << TokenType.INTERFACE - 9 | 1L << TokenType.NATIVE - 9 | 1L << TokenType.PRIVATE - 9 | 1L << TokenType.PROTECTED - 9 | 1L << TokenType.PUBLIC - 9 | 1L << TokenType.STATIC - 9 | 1L << TokenType.STRICTFP - 9 | 1L << TokenType.SYNCHRONIZED - 9 | 1L << TokenType.TRANSIENT - 9 | 1L << TokenType.VOLATILE - 9)) != 0) || ((__token - 89 & ~63) == 0 && (1L << __token - 89 & (1L << TokenType.AT - 89)) != 0)) {
			pushCallStack(TYPE_DECL_1_2_1);
			modifiers = parseModifiers();
			popCallStack();
			__token = getToken(0).kind;
			if (__token == TokenType.CLASS || __token == TokenType.INTERFACE) {
				pushCallStack(TYPE_DECL_1_2_2_1);
				ret = parseClassOrInterfaceDecl(modifiers);
				popCallStack();
			} else if (__token == TokenType.ENUM) {
				pushCallStack(TYPE_DECL_1_2_2_2);
				ret = parseEnumDecl(modifiers);
				popCallStack();
			} else if (__token == TokenType.AT) {
				pushCallStack(TYPE_DECL_1_2_2_3);
				ret = parseAnnotationTypeDecl(modifiers);
				popCallStack();
			} else
				throw produceParseException(TokenType.CLASS, TokenType.ENUM, TokenType.INTERFACE, TokenType.AT);
		} else
			throw produceParseException(TokenType.ABSTRACT, TokenType.CLASS, TokenType.DEFAULT, TokenType.ENUM, TokenType.FINAL, TokenType.INTERFACE, TokenType.NATIVE, TokenType.PRIVATE, TokenType.PROTECTED, TokenType.PUBLIC, TokenType.STATIC, TokenType.STRICTFP, TokenType.SYNCHRONIZED, TokenType.TRANSIENT, TokenType.VOLATILE, TokenType.SEMICOLON, TokenType.AT);
		return ret;
	}

	/* sequence(
		choice(
			sequence(
				terminal(CLASS)
				action({ typeKind = TypeKind.Class; })
				nonTerminal(name, Name)
				zeroOrOne(
					nonTerminal(typeParams, TypeParameters)
				)
				zeroOrOne(
					sequence(
						terminal(EXTENDS)
						nonTerminal(superClassType, AnnotatedQualifiedType)
					)
				)
				zeroOrOne(
					nonTerminal(implementsClause, ImplementsList)
				)
			)
			sequence(
				terminal(INTERFACE)
				action({ typeKind = TypeKind.Interface; })
				nonTerminal(name, Name)
				zeroOrOne(
					nonTerminal(typeParams, TypeParameters)
				)
				zeroOrOne(
					nonTerminal(extendsClause, ExtendsList)
				)
			)
		)
		nonTerminal(members, ClassOrInterfaceBody)
		action({
			if (typeKind == TypeKind.Interface)
				return dress(SInterfaceDecl.make(modifiers, name, ensureNotNull(typeParams), ensureNotNull(extendsClause), members)).withProblem(problem.value);
			else {
				return dress(SClassDecl.make(modifiers, name, ensureNotNull(typeParams), optionOf(superClassType), ensureNotNull(implementsClause), members));
			}
		})
	) */
	protected BUTree<? extends STypeDecl> parseClassOrInterfaceDecl(BUTree<SNodeList> modifiers) throws ParseException {
		TypeKind typeKind;
		BUTree<SName> name;
		BUTree<SNodeList> typeParams = null;
		BUTree<SQualifiedType> superClassType = null;
		BUTree<SNodeList> extendsClause = null;
		BUTree<SNodeList> implementsClause = null;
		BUTree<SNodeList> members;
		ByRef<BUProblem> problem = new ByRef<BUProblem>(null);
		int __token;
		__token = getToken(0).kind;
		if (__token == TokenType.CLASS) {
			consume(TokenType.CLASS);
			typeKind = TypeKind.Class;
			pushCallStack(CLASS_OR_INTERFACE_DECL_1_1_2);
			name = parseName();
			popCallStack();
			__token = getToken(0).kind;
			if (__token == TokenType.LT) {
				pushCallStack(CLASS_OR_INTERFACE_DECL_1_1_3_1);
				typeParams = parseTypeParameters();
				popCallStack();
			}
			__token = getToken(0).kind;
			if (__token == TokenType.EXTENDS) {
				consume(TokenType.EXTENDS);
				pushCallStack(CLASS_OR_INTERFACE_DECL_1_1_4_1_2);
				superClassType = parseAnnotatedQualifiedType();
				popCallStack();
			}
			__token = getToken(0).kind;
			if (__token == TokenType.IMPLEMENTS) {
				pushCallStack(CLASS_OR_INTERFACE_DECL_1_1_5_1);
				implementsClause = parseImplementsList(typeKind, problem);
				popCallStack();
			}
		} else if (__token == TokenType.INTERFACE) {
			consume(TokenType.INTERFACE);
			typeKind = TypeKind.Interface;
			pushCallStack(CLASS_OR_INTERFACE_DECL_1_2_2);
			name = parseName();
			popCallStack();
			__token = getToken(0).kind;
			if (__token == TokenType.LT) {
				pushCallStack(CLASS_OR_INTERFACE_DECL_1_2_3_1);
				typeParams = parseTypeParameters();
				popCallStack();
			}
			__token = getToken(0).kind;
			if (__token == TokenType.EXTENDS) {
				pushCallStack(CLASS_OR_INTERFACE_DECL_1_2_4_1);
				extendsClause = parseExtendsList();
				popCallStack();
			}
		} else
			throw produceParseException(TokenType.CLASS, TokenType.INTERFACE);
		pushCallStack(CLASS_OR_INTERFACE_DECL_2);
		members = parseClassOrInterfaceBody(typeKind);
		popCallStack();
		if (typeKind == TypeKind.Interface)
			return dress(SInterfaceDecl.make(modifiers, name, ensureNotNull(typeParams), ensureNotNull(extendsClause), members)).withProblem(problem.value);
		else {
			return dress(SClassDecl.make(modifiers, name, ensureNotNull(typeParams), optionOf(superClassType), ensureNotNull(implementsClause), members));
		}
	}

	/* sequence(
		terminal(EXTENDS)
		choice(
			nonTerminal(ret, NodeListVar)
			sequence(
				nonTerminal(cit, AnnotatedQualifiedType)
				action({ ret = append(ret, cit); })
				zeroOrMore(
					sequence(
						terminal(COMMA)
						nonTerminal(cit, AnnotatedQualifiedType)
						action({ ret = append(ret, cit); })
					)
				)
			)
		)
		action({ return ret; })
	) */
	protected BUTree<SNodeList> parseExtendsList() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<SQualifiedType> cit;
		BUTree<SNodeList> annotations = null;
		int __token;
		consume(TokenType.EXTENDS);
		__token = getToken(0).kind;
		if (__token == TokenType.NODE_LIST_VARIABLE) {
			pushCallStack(EXTENDS_LIST_2_1);
			ret = parseNodeListVar();
			popCallStack();
		} else if ((__token - 89 & ~63) == 0 && (1L << __token - 89 & (1L << TokenType.AT - 89 | 1L << TokenType.NODE_VARIABLE - 89 | 1L << TokenType.IDENTIFIER - 89)) != 0) {
			pushCallStack(EXTENDS_LIST_2_2_1);
			cit = parseAnnotatedQualifiedType();
			popCallStack();
			ret = append(ret, cit);
			__token = getToken(0).kind;
			while (__token == TokenType.COMMA) {
				consume(TokenType.COMMA);
				pushCallStack(EXTENDS_LIST_2_2_2_1_2);
				cit = parseAnnotatedQualifiedType();
				popCallStack();
				ret = append(ret, cit);
				__token = getToken(0).kind;
			}
		} else
			throw produceParseException(TokenType.AT, TokenType.NODE_VARIABLE, TokenType.NODE_LIST_VARIABLE, TokenType.IDENTIFIER);
		return ret;
	}

	/* sequence(
		terminal(IMPLEMENTS)
		choice(
			nonTerminal(ret, NodeListVar)
			sequence(
				nonTerminal(cit, AnnotatedQualifiedType)
				action({ ret = append(ret, cit); })
				zeroOrMore(
					sequence(
						terminal(COMMA)
						nonTerminal(cit, AnnotatedQualifiedType)
						action({ ret = append(ret, cit); })
					)
				)
				action({
					if (typeKind == TypeKind.Interface) problem.value = new BUProblem(Severity.ERROR, "An interface cannot implement other interfaces");
				})
			)
		)
		action({ return ret; })
	) */
	protected BUTree<SNodeList> parseImplementsList(TypeKind typeKind, ByRef<BUProblem> problem) throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<SQualifiedType> cit;
		BUTree<SNodeList> annotations = null;
		int __token;
		consume(TokenType.IMPLEMENTS);
		__token = getToken(0).kind;
		if (__token == TokenType.NODE_LIST_VARIABLE) {
			pushCallStack(IMPLEMENTS_LIST_2_1);
			ret = parseNodeListVar();
			popCallStack();
		} else if ((__token - 89 & ~63) == 0 && (1L << __token - 89 & (1L << TokenType.AT - 89 | 1L << TokenType.NODE_VARIABLE - 89 | 1L << TokenType.IDENTIFIER - 89)) != 0) {
			pushCallStack(IMPLEMENTS_LIST_2_2_1);
			cit = parseAnnotatedQualifiedType();
			popCallStack();
			ret = append(ret, cit);
			__token = getToken(0).kind;
			while (__token == TokenType.COMMA) {
				consume(TokenType.COMMA);
				pushCallStack(IMPLEMENTS_LIST_2_2_2_1_2);
				cit = parseAnnotatedQualifiedType();
				popCallStack();
				ret = append(ret, cit);
				__token = getToken(0).kind;
			}
			if (typeKind == TypeKind.Interface) problem.value = new BUProblem(Severity.ERROR, "An interface cannot implement other interfaces");
		} else
			throw produceParseException(TokenType.AT, TokenType.NODE_VARIABLE, TokenType.NODE_LIST_VARIABLE, TokenType.IDENTIFIER);
		return ret;
	}

	/* sequence(
		terminal(ENUM)
		nonTerminal(name, Name)
		zeroOrOne(
			nonTerminal(implementsClause, ImplementsList)
		)
		terminal(LBRACE)
		zeroOrOne(
			choice(
				nonTerminal(constants, NodeListVar)
				sequence(
					nonTerminal(entry, EnumConstantDecl)
					action({ constants = append(constants, entry); })
					zeroOrMore(
						sequence(
							terminal(COMMA)
							nonTerminal(entry, EnumConstantDecl)
							action({ constants = append(constants, entry); })
						)
					)
				)
			)
		)
		zeroOrOne(
			terminal(COMMA)
			action({ trailingComma = true; })
		)
		zeroOrOne(
			sequence(
				terminal(SEMICOLON)
				nonTerminal(members, ClassOrInterfaceBodyDecls)
			)
		)
		terminal(RBRACE)
		action({ return dress(SEnumDecl.make(modifiers, name, implementsClause, constants, trailingComma, ensureNotNull(members))).withProblem(problem.value); })
	) */
	protected BUTree<? extends STypeDecl> parseEnumDecl(BUTree<SNodeList> modifiers) throws ParseException {
		BUTree<SName> name;
		BUTree<SNodeList> implementsClause = emptyList();
		BUTree<SEnumConstantDecl> entry;
		BUTree<SNodeList> constants = emptyList();
		boolean trailingComma = false;
		BUTree<SNodeList> members = null;
		ByRef<BUProblem> problem = new ByRef<BUProblem>(null);
		int __token;
		consume(TokenType.ENUM);
		pushCallStack(ENUM_DECL_2);
		name = parseName();
		popCallStack();
		__token = getToken(0).kind;
		if (__token == TokenType.IMPLEMENTS) {
			pushCallStack(ENUM_DECL_3_1);
			implementsClause = parseImplementsList(TypeKind.Enum, problem);
			popCallStack();
		}
		consume(TokenType.LBRACE);
		__token = getToken(0).kind;
		if (__token == TokenType.NODE_LIST_VARIABLE) {
			pushCallStack(ENUM_DECL_5_1_1);
			constants = parseNodeListVar();
			popCallStack();
		} else if (((__token - 9 & ~63) == 0 && (1L << __token - 9 & (1L << TokenType.ABSTRACT - 9 | 1L << TokenType.DEFAULT - 9 | 1L << TokenType.FINAL - 9 | 1L << TokenType.NATIVE - 9 | 1L << TokenType.PRIVATE - 9 | 1L << TokenType.PROTECTED - 9 | 1L << TokenType.PUBLIC - 9 | 1L << TokenType.STATIC - 9 | 1L << TokenType.STRICTFP - 9 | 1L << TokenType.SYNCHRONIZED - 9 | 1L << TokenType.TRANSIENT - 9 | 1L << TokenType.VOLATILE - 9)) != 0) || ((__token - 89 & ~63) == 0 && (1L << __token - 89 & (1L << TokenType.AT - 89 | 1L << TokenType.NODE_VARIABLE - 89 | 1L << TokenType.IDENTIFIER - 89)) != 0)) {
			pushCallStack(ENUM_DECL_5_1_2_1);
			entry = parseEnumConstantDecl();
			popCallStack();
			constants = append(constants, entry);
			__token = getToken(0).kind;
			while (predict(ENUM_DECL_5_1_2_2) == 1) {
				consume(TokenType.COMMA);
				pushCallStack(ENUM_DECL_5_1_2_2_1_2);
				entry = parseEnumConstantDecl();
				popCallStack();
				constants = append(constants, entry);
				__token = getToken(0).kind;
			}
		}
		__token = getToken(0).kind;
		if (__token == TokenType.COMMA) {
			consume(TokenType.COMMA);
			trailingComma = true;
		}
		__token = getToken(0).kind;
		if (__token == TokenType.SEMICOLON) {
			consume(TokenType.SEMICOLON);
			pushCallStack(ENUM_DECL_7_1_2);
			members = parseClassOrInterfaceBodyDecls(TypeKind.Enum);
			popCallStack();
		}
		consume(TokenType.RBRACE);
		return dress(SEnumDecl.make(modifiers, name, implementsClause, constants, trailingComma, ensureNotNull(members))).withProblem(problem.value);
	}

	/* sequence(
		action({ run(); })
		nonTerminal(modifiers, Modifiers)
		nonTerminal(name, Name)
		zeroOrOne(
			nonTerminal(args, Arguments)
		)
		zeroOrOne(
			nonTerminal(classBody, ClassOrInterfaceBody)
		)
		action({ return dress(SEnumConstantDecl.make(modifiers, name, optionOf(args), optionOf(classBody))); })
	) */
	protected BUTree<SEnumConstantDecl> parseEnumConstantDecl() throws ParseException {
		BUTree<SNodeList> modifiers = null;
		BUTree<SName> name;
		BUTree<SNodeList> args = null;
		BUTree<SNodeList> classBody = null;
		int __token;
		run();
		pushCallStack(ENUM_CONSTANT_DECL_1);
		modifiers = parseModifiers();
		popCallStack();
		pushCallStack(ENUM_CONSTANT_DECL_2);
		name = parseName();
		popCallStack();
		__token = getToken(0).kind;
		if (__token == TokenType.LPAREN) {
			pushCallStack(ENUM_CONSTANT_DECL_3_1);
			args = parseArguments();
			popCallStack();
		}
		__token = getToken(0).kind;
		if (__token == TokenType.LBRACE) {
			pushCallStack(ENUM_CONSTANT_DECL_4_1);
			classBody = parseClassOrInterfaceBody(TypeKind.Class);
			popCallStack();
		}
		return dress(SEnumConstantDecl.make(modifiers, name, optionOf(args), optionOf(classBody)));
	}

	/* sequence(
		terminal(AT)
		terminal(INTERFACE)
		nonTerminal(name, Name)
		nonTerminal(members, AnnotationTypeBody)
		action({ return dress(SAnnotationDecl.make(modifiers, name, members)); })
	) */
	protected BUTree<SAnnotationDecl> parseAnnotationTypeDecl(BUTree<SNodeList> modifiers) throws ParseException {
		BUTree<SName> name;
		BUTree<SNodeList> members;
		int __token;
		consume(TokenType.AT);
		consume(TokenType.INTERFACE);
		pushCallStack(ANNOTATION_TYPE_DECL_3);
		name = parseName();
		popCallStack();
		pushCallStack(ANNOTATION_TYPE_DECL_4);
		members = parseAnnotationTypeBody();
		popCallStack();
		return dress(SAnnotationDecl.make(modifiers, name, members));
	}

	/* sequence(
		terminal(LBRACE)
		zeroOrOne(
			choice(
				nonTerminal(ret, NodeListVar)
				oneOrMore(
					nonTerminal(member, AnnotationTypeBodyDecl)
					action({ ret = append(ret, member); })
				)
			)
		)
		terminal(RBRACE)
		action({ return ret; })
	) */
	protected BUTree<SNodeList> parseAnnotationTypeBody() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<? extends SMemberDecl> member;
		int __token;
		consume(TokenType.LBRACE);
		__token = getToken(0).kind;
		if (__token == TokenType.NODE_LIST_VARIABLE) {
			pushCallStack(ANNOTATION_TYPE_BODY_2_1_1);
			ret = parseNodeListVar();
			popCallStack();
		} else if (((__token - 9 & ~63) == 0 && (1L << __token - 9 & (1L << TokenType.ABSTRACT - 9 | 1L << TokenType.BOOLEAN - 9 | 1L << TokenType.BYTE - 9 | 1L << TokenType.CHAR - 9 | 1L << TokenType.CLASS - 9 | 1L << TokenType.DEFAULT - 9 | 1L << TokenType.DOUBLE - 9 | 1L << TokenType.ENUM - 9 | 1L << TokenType.FINAL - 9 | 1L << TokenType.FLOAT - 9 | 1L << TokenType.INT - 9 | 1L << TokenType.INTERFACE - 9 | 1L << TokenType.LONG - 9 | 1L << TokenType.NATIVE - 9 | 1L << TokenType.PRIVATE - 9 | 1L << TokenType.PROTECTED - 9 | 1L << TokenType.PUBLIC - 9 | 1L << TokenType.SHORT - 9 | 1L << TokenType.STATIC - 9 | 1L << TokenType.STRICTFP - 9 | 1L << TokenType.SYNCHRONIZED - 9 | 1L << TokenType.TRANSIENT - 9 | 1L << TokenType.VOLATILE - 9)) != 0) || ((__token - 86 & ~63) == 0 && (1L << __token - 86 & (1L << TokenType.SEMICOLON - 86 | 1L << TokenType.AT - 86 | 1L << TokenType.NODE_VARIABLE - 86 | 1L << TokenType.IDENTIFIER - 86)) != 0)) {
			do {
				pushCallStack(ANNOTATION_TYPE_BODY_2_1_2_1);
				member = parseAnnotationTypeBodyDecl();
				popCallStack();
				ret = append(ret, member);
				__token = getToken(0).kind;
			} while (((__token - 9 & ~63) == 0 && (1L << __token - 9 & (1L << TokenType.ABSTRACT - 9 | 1L << TokenType.BOOLEAN - 9 | 1L << TokenType.BYTE - 9 | 1L << TokenType.CHAR - 9 | 1L << TokenType.CLASS - 9 | 1L << TokenType.DEFAULT - 9 | 1L << TokenType.DOUBLE - 9 | 1L << TokenType.ENUM - 9 | 1L << TokenType.FINAL - 9 | 1L << TokenType.FLOAT - 9 | 1L << TokenType.INT - 9 | 1L << TokenType.INTERFACE - 9 | 1L << TokenType.LONG - 9 | 1L << TokenType.NATIVE - 9 | 1L << TokenType.PRIVATE - 9 | 1L << TokenType.PROTECTED - 9 | 1L << TokenType.PUBLIC - 9 | 1L << TokenType.SHORT - 9 | 1L << TokenType.STATIC - 9 | 1L << TokenType.STRICTFP - 9 | 1L << TokenType.SYNCHRONIZED - 9 | 1L << TokenType.TRANSIENT - 9 | 1L << TokenType.VOLATILE - 9)) != 0) || ((__token - 86 & ~63) == 0 && (1L << __token - 86 & (1L << TokenType.SEMICOLON - 86 | 1L << TokenType.AT - 86 | 1L << TokenType.NODE_VARIABLE - 86 | 1L << TokenType.IDENTIFIER - 86)) != 0));
		}
		consume(TokenType.RBRACE);
		return ret;
	}

	/* sequence(
		action({ run(); })
		choice(
			sequence(
				terminal(SEMICOLON)
				action({ ret = dress(SEmptyTypeDecl.make()); })
			)
			sequence(
				nonTerminal(modifiers, Modifiers)
				choice(
					nonTerminal(ret, AnnotationTypeMemberDecl)
					nonTerminal(ret, ClassOrInterfaceDecl)
					nonTerminal(ret, EnumDecl)
					nonTerminal(ret, AnnotationTypeDecl)
					nonTerminal(ret, FieldDecl)
				)
			)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SMemberDecl> parseAnnotationTypeBodyDecl() throws ParseException {
		BUTree<SNodeList> modifiers;
		BUTree<? extends SMemberDecl> ret;
		int __token;
		run();
		__token = getToken(0).kind;
		if (__token == TokenType.SEMICOLON) {
			consume(TokenType.SEMICOLON);
			ret = dress(SEmptyTypeDecl.make());
		} else if (((__token - 9 & ~63) == 0 && (1L << __token - 9 & (1L << TokenType.ABSTRACT - 9 | 1L << TokenType.BOOLEAN - 9 | 1L << TokenType.BYTE - 9 | 1L << TokenType.CHAR - 9 | 1L << TokenType.CLASS - 9 | 1L << TokenType.DEFAULT - 9 | 1L << TokenType.DOUBLE - 9 | 1L << TokenType.ENUM - 9 | 1L << TokenType.FINAL - 9 | 1L << TokenType.FLOAT - 9 | 1L << TokenType.INT - 9 | 1L << TokenType.INTERFACE - 9 | 1L << TokenType.LONG - 9 | 1L << TokenType.NATIVE - 9 | 1L << TokenType.PRIVATE - 9 | 1L << TokenType.PROTECTED - 9 | 1L << TokenType.PUBLIC - 9 | 1L << TokenType.SHORT - 9 | 1L << TokenType.STATIC - 9 | 1L << TokenType.STRICTFP - 9 | 1L << TokenType.SYNCHRONIZED - 9 | 1L << TokenType.TRANSIENT - 9 | 1L << TokenType.VOLATILE - 9)) != 0) || ((__token - 89 & ~63) == 0 && (1L << __token - 89 & (1L << TokenType.AT - 89 | 1L << TokenType.NODE_VARIABLE - 89 | 1L << TokenType.IDENTIFIER - 89)) != 0)) {
			pushCallStack(ANNOTATION_TYPE_BODY_DECL_1_2_1);
			modifiers = parseModifiers();
			popCallStack();
			switch (predict(ANNOTATION_TYPE_BODY_DECL_1_2_2)) {
				case 1:
					pushCallStack(ANNOTATION_TYPE_BODY_DECL_1_2_2_1);
					ret = parseAnnotationTypeMemberDecl(modifiers);
					popCallStack();
					break;
				case 2:
					pushCallStack(ANNOTATION_TYPE_BODY_DECL_1_2_2_2);
					ret = parseClassOrInterfaceDecl(modifiers);
					popCallStack();
					break;
				case 3:
					pushCallStack(ANNOTATION_TYPE_BODY_DECL_1_2_2_3);
					ret = parseEnumDecl(modifiers);
					popCallStack();
					break;
				case 4:
					pushCallStack(ANNOTATION_TYPE_BODY_DECL_1_2_2_4);
					ret = parseAnnotationTypeDecl(modifiers);
					popCallStack();
					break;
				case 5:
					pushCallStack(ANNOTATION_TYPE_BODY_DECL_1_2_2_5);
					ret = parseFieldDecl(modifiers);
					popCallStack();
					break;
				default:
					throw produceParseException(TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.CLASS, TokenType.DOUBLE, TokenType.ENUM, TokenType.FLOAT, TokenType.INT, TokenType.INTERFACE, TokenType.LONG, TokenType.SHORT, TokenType.AT, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER);
			}
		} else
			throw produceParseException(TokenType.ABSTRACT, TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.CLASS, TokenType.DEFAULT, TokenType.DOUBLE, TokenType.ENUM, TokenType.FINAL, TokenType.FLOAT, TokenType.INT, TokenType.INTERFACE, TokenType.LONG, TokenType.NATIVE, TokenType.PRIVATE, TokenType.PROTECTED, TokenType.PUBLIC, TokenType.SHORT, TokenType.STATIC, TokenType.STRICTFP, TokenType.SYNCHRONIZED, TokenType.TRANSIENT, TokenType.VOLATILE, TokenType.SEMICOLON, TokenType.AT, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER);
		return ret;
	}

	/* sequence(
		nonTerminal(type, Type)
		nonTerminal(name, Name)
		terminal(LPAREN)
		terminal(RPAREN)
		nonTerminal(dims, ArrayDims)
		zeroOrOne(
			sequence(
				terminal(DEFAULT)
				nonTerminal(value, ElementValue)
				action({ defaultValue = optionOf(value); })
			)
		)
		terminal(SEMICOLON)
		action({ return dress(SAnnotationMemberDecl.make(modifiers, type, name, dims, defaultValue)); })
	) */
	protected BUTree<SAnnotationMemberDecl> parseAnnotationTypeMemberDecl(BUTree<SNodeList> modifiers) throws ParseException {
		BUTree<? extends SType> type;
		BUTree<SName> name;
		BUTree<SNodeList> dims;
		BUTree<SNodeOption> defaultValue = none();
		BUTree<? extends SExpr> value = null;
		int __token;
		pushCallStack(ANNOTATION_TYPE_MEMBER_DECL_1);
		type = parseType(null);
		popCallStack();
		pushCallStack(ANNOTATION_TYPE_MEMBER_DECL_2);
		name = parseName();
		popCallStack();
		consume(TokenType.LPAREN);
		consume(TokenType.RPAREN);
		pushCallStack(ANNOTATION_TYPE_MEMBER_DECL_5);
		dims = parseArrayDims();
		popCallStack();
		__token = getToken(0).kind;
		if (__token == TokenType.DEFAULT) {
			consume(TokenType.DEFAULT);
			pushCallStack(ANNOTATION_TYPE_MEMBER_DECL_6_1_2);
			value = parseElementValue();
			popCallStack();
			defaultValue = optionOf(value);
		}
		consume(TokenType.SEMICOLON);
		return dress(SAnnotationMemberDecl.make(modifiers, type, name, dims, defaultValue));
	}

	/* sequence(
		terminal(LT)
		choice(
			nonTerminal(ret, NodeListVar)
			sequence(
				nonTerminal(tp, TypeParameter)
				action({ ret = append(ret, tp); })
				zeroOrMore(
					sequence(
						terminal(COMMA)
						nonTerminal(tp, TypeParameter)
						action({ ret = append(ret, tp); })
					)
				)
			)
		)
		terminal(GT)
		action({ return ret; })
	) */
	protected BUTree<SNodeList> parseTypeParameters() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<STypeParameter> tp;
		int __token;
		consume(TokenType.LT);
		__token = getToken(0).kind;
		if (__token == TokenType.NODE_LIST_VARIABLE) {
			pushCallStack(TYPE_PARAMETERS_2_1);
			ret = parseNodeListVar();
			popCallStack();
		} else if ((__token - 89 & ~63) == 0 && (1L << __token - 89 & (1L << TokenType.AT - 89 | 1L << TokenType.NODE_VARIABLE - 89 | 1L << TokenType.IDENTIFIER - 89)) != 0) {
			pushCallStack(TYPE_PARAMETERS_2_2_1);
			tp = parseTypeParameter();
			popCallStack();
			ret = append(ret, tp);
			__token = getToken(0).kind;
			while (__token == TokenType.COMMA) {
				consume(TokenType.COMMA);
				pushCallStack(TYPE_PARAMETERS_2_2_2_1_2);
				tp = parseTypeParameter();
				popCallStack();
				ret = append(ret, tp);
				__token = getToken(0).kind;
			}
		} else
			throw produceParseException(TokenType.AT, TokenType.NODE_VARIABLE, TokenType.NODE_LIST_VARIABLE, TokenType.IDENTIFIER);
		consume(TokenType.GT);
		return ret;
	}

	/* sequence(
		action({ run(); })
		nonTerminal(annotations, Annotations)
		nonTerminal(name, Name)
		zeroOrOne(
			nonTerminal(typeBounds, TypeBounds)
		)
		action({ return dress(STypeParameter.make(annotations, name, ensureNotNull(typeBounds))); })
	) */
	protected BUTree<STypeParameter> parseTypeParameter() throws ParseException {
		BUTree<SNodeList> annotations = null;
		BUTree<SName> name;
		BUTree<SNodeList> typeBounds = null;
		int __token;
		run();
		pushCallStack(TYPE_PARAMETER_1);
		annotations = parseAnnotations();
		popCallStack();
		pushCallStack(TYPE_PARAMETER_2);
		name = parseName();
		popCallStack();
		__token = getToken(0).kind;
		if (predict(TYPE_PARAMETER_3) == 1) {
			pushCallStack(TYPE_PARAMETER_3_1);
			typeBounds = parseTypeBounds();
			popCallStack();
		}
		return dress(STypeParameter.make(annotations, name, ensureNotNull(typeBounds)));
	}

	/* sequence(
		terminal(EXTENDS)
		choice(
			nonTerminal(ret, NodeListVar)
			sequence(
				nonTerminal(cit, AnnotatedQualifiedType)
				action({ ret = append(ret, cit); })
				zeroOrMore(
					sequence(
						terminal(BIT_AND)
						nonTerminal(cit, AnnotatedQualifiedType)
						action({ ret = append(ret, cit); })
					)
				)
			)
		)
		action({ return ret; })
	) */
	protected BUTree<SNodeList> parseTypeBounds() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<SQualifiedType> cit;
		BUTree<SNodeList> annotations = null;
		int __token;
		consume(TokenType.EXTENDS);
		__token = getToken(0).kind;
		if (__token == TokenType.NODE_LIST_VARIABLE) {
			pushCallStack(TYPE_BOUNDS_2_1);
			ret = parseNodeListVar();
			popCallStack();
		} else if ((__token - 89 & ~63) == 0 && (1L << __token - 89 & (1L << TokenType.AT - 89 | 1L << TokenType.NODE_VARIABLE - 89 | 1L << TokenType.IDENTIFIER - 89)) != 0) {
			pushCallStack(TYPE_BOUNDS_2_2_1);
			cit = parseAnnotatedQualifiedType();
			popCallStack();
			ret = append(ret, cit);
			__token = getToken(0).kind;
			while (__token == TokenType.BIT_AND) {
				consume(TokenType.BIT_AND);
				pushCallStack(TYPE_BOUNDS_2_2_2_1_2);
				cit = parseAnnotatedQualifiedType();
				popCallStack();
				ret = append(ret, cit);
				__token = getToken(0).kind;
			}
		} else
			throw produceParseException(TokenType.AT, TokenType.NODE_VARIABLE, TokenType.NODE_LIST_VARIABLE, TokenType.IDENTIFIER);
		return ret;
	}

	/* sequence(
		terminal(LBRACE)
		nonTerminal(ret, ClassOrInterfaceBodyDecls)
		terminal(RBRACE)
		action({ return ret; })
	) */
	protected BUTree<SNodeList> parseClassOrInterfaceBody(TypeKind typeKind) throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<? extends SMemberDecl> member;
		int __token;
		consume(TokenType.LBRACE);
		pushCallStack(CLASS_OR_INTERFACE_BODY_2);
		ret = parseClassOrInterfaceBodyDecls(typeKind);
		popCallStack();
		consume(TokenType.RBRACE);
		return ret;
	}

	/* sequence(
		zeroOrOne(
			choice(
				nonTerminal(ret, NodeListVar)
				oneOrMore(
					nonTerminal(member, ClassOrInterfaceBodyDecl)
					action({ ret = append(ret, member); })
				)
			)
		)
		action({ return ret; })
	) */
	protected BUTree<SNodeList> parseClassOrInterfaceBodyDecls(TypeKind typeKind) throws ParseException {
		BUTree<? extends SMemberDecl> member;
		BUTree<SNodeList> ret = emptyList();
		int __token;
		__token = getToken(0).kind;
		if (__token == TokenType.NODE_LIST_VARIABLE) {
			pushCallStack(CLASS_OR_INTERFACE_BODY_DECLS_1_1_1);
			ret = parseNodeListVar();
			popCallStack();
		} else if (((__token - 9 & ~63) == 0 && (1L << __token - 9 & (1L << TokenType.ABSTRACT - 9 | 1L << TokenType.BOOLEAN - 9 | 1L << TokenType.BYTE - 9 | 1L << TokenType.CHAR - 9 | 1L << TokenType.CLASS - 9 | 1L << TokenType.DEFAULT - 9 | 1L << TokenType.DOUBLE - 9 | 1L << TokenType.ENUM - 9 | 1L << TokenType.FINAL - 9 | 1L << TokenType.FLOAT - 9 | 1L << TokenType.INT - 9 | 1L << TokenType.INTERFACE - 9 | 1L << TokenType.LONG - 9 | 1L << TokenType.NATIVE - 9 | 1L << TokenType.PRIVATE - 9 | 1L << TokenType.PROTECTED - 9 | 1L << TokenType.PUBLIC - 9 | 1L << TokenType.SHORT - 9 | 1L << TokenType.STATIC - 9 | 1L << TokenType.STRICTFP - 9 | 1L << TokenType.SYNCHRONIZED - 9 | 1L << TokenType.TRANSIENT - 9 | 1L << TokenType.VOID - 9 | 1L << TokenType.VOLATILE - 9)) != 0) || ((__token - 82 & ~63) == 0 && (1L << __token - 82 & (1L << TokenType.LBRACE - 82 | 1L << TokenType.SEMICOLON - 82 | 1L << TokenType.AT - 82 | 1L << TokenType.LT - 82 | 1L << TokenType.NODE_VARIABLE - 82 | 1L << TokenType.IDENTIFIER - 82)) != 0)) {
			do {
				pushCallStack(CLASS_OR_INTERFACE_BODY_DECLS_1_1_2_1);
				member = parseClassOrInterfaceBodyDecl(typeKind);
				popCallStack();
				ret = append(ret, member);
				__token = getToken(0).kind;
			} while (((__token - 9 & ~63) == 0 && (1L << __token - 9 & (1L << TokenType.ABSTRACT - 9 | 1L << TokenType.BOOLEAN - 9 | 1L << TokenType.BYTE - 9 | 1L << TokenType.CHAR - 9 | 1L << TokenType.CLASS - 9 | 1L << TokenType.DEFAULT - 9 | 1L << TokenType.DOUBLE - 9 | 1L << TokenType.ENUM - 9 | 1L << TokenType.FINAL - 9 | 1L << TokenType.FLOAT - 9 | 1L << TokenType.INT - 9 | 1L << TokenType.INTERFACE - 9 | 1L << TokenType.LONG - 9 | 1L << TokenType.NATIVE - 9 | 1L << TokenType.PRIVATE - 9 | 1L << TokenType.PROTECTED - 9 | 1L << TokenType.PUBLIC - 9 | 1L << TokenType.SHORT - 9 | 1L << TokenType.STATIC - 9 | 1L << TokenType.STRICTFP - 9 | 1L << TokenType.SYNCHRONIZED - 9 | 1L << TokenType.TRANSIENT - 9 | 1L << TokenType.VOID - 9 | 1L << TokenType.VOLATILE - 9)) != 0) || ((__token - 82 & ~63) == 0 && (1L << __token - 82 & (1L << TokenType.LBRACE - 82 | 1L << TokenType.SEMICOLON - 82 | 1L << TokenType.AT - 82 | 1L << TokenType.LT - 82 | 1L << TokenType.NODE_VARIABLE - 82 | 1L << TokenType.IDENTIFIER - 82)) != 0));
		}
		return ret;
	}

	/* sequence(
		action({ run(); })
		choice(
			sequence(
				terminal(SEMICOLON)
				action({ ret = dress(SEmptyMemberDecl.make()); })
			)
			sequence(
				nonTerminal(modifiers, Modifiers)
				action({
					if (modifiers != null && contains(modifiers, SModifier.make(ModifierKeyword.Default)) && typeKind != TypeKind.Interface) problem = new BUProblem(Severity.ERROR, "Only interfaces can have default members");
				})
				choice(
					sequence(
						nonTerminal(ret, InitializerDecl)
						action({
							if (typeKind == TypeKind.Interface) ret = ret.withProblem(new BUProblem(Severity.ERROR, "An interface cannot have initializers"));
						})
					)
					nonTerminal(ret, ClassOrInterfaceDecl)
					nonTerminal(ret, EnumDecl)
					nonTerminal(ret, AnnotationTypeDecl)
					sequence(
						nonTerminal(ret, ConstructorDecl)
						action({
							if (typeKind == TypeKind.Interface) ret = ret.withProblem(new BUProblem(Severity.ERROR, "An interface cannot have constructors"));
						})
					)
					sequence(
						nonTerminal(ret, FieldDecl)
					)
					nonTerminal(ret, MethodDecl)
				)
			)
		)
		action({ return ret.withProblem(problem); })
	) */
	protected BUTree<? extends SMemberDecl> parseClassOrInterfaceBodyDecl(TypeKind typeKind) throws ParseException {
		BUTree<SNodeList> modifiers;
		BUTree<? extends SMemberDecl> ret;
		BUProblem problem = null;
		int __token;
		run();
		__token = getToken(0).kind;
		if (__token == TokenType.SEMICOLON) {
			consume(TokenType.SEMICOLON);
			ret = dress(SEmptyMemberDecl.make());
		} else if (((__token - 9 & ~63) == 0 && (1L << __token - 9 & (1L << TokenType.ABSTRACT - 9 | 1L << TokenType.BOOLEAN - 9 | 1L << TokenType.BYTE - 9 | 1L << TokenType.CHAR - 9 | 1L << TokenType.CLASS - 9 | 1L << TokenType.DEFAULT - 9 | 1L << TokenType.DOUBLE - 9 | 1L << TokenType.ENUM - 9 | 1L << TokenType.FINAL - 9 | 1L << TokenType.FLOAT - 9 | 1L << TokenType.INT - 9 | 1L << TokenType.INTERFACE - 9 | 1L << TokenType.LONG - 9 | 1L << TokenType.NATIVE - 9 | 1L << TokenType.PRIVATE - 9 | 1L << TokenType.PROTECTED - 9 | 1L << TokenType.PUBLIC - 9 | 1L << TokenType.SHORT - 9 | 1L << TokenType.STATIC - 9 | 1L << TokenType.STRICTFP - 9 | 1L << TokenType.SYNCHRONIZED - 9 | 1L << TokenType.TRANSIENT - 9 | 1L << TokenType.VOID - 9 | 1L << TokenType.VOLATILE - 9)) != 0) || ((__token - 82 & ~63) == 0 && (1L << __token - 82 & (1L << TokenType.LBRACE - 82 | 1L << TokenType.AT - 82 | 1L << TokenType.LT - 82 | 1L << TokenType.NODE_VARIABLE - 82 | 1L << TokenType.IDENTIFIER - 82)) != 0)) {
			pushCallStack(CLASS_OR_INTERFACE_BODY_DECL_1_2_1);
			modifiers = parseModifiers();
			popCallStack();
			if (modifiers != null && contains(modifiers, SModifier.make(ModifierKeyword.Default)) && typeKind != TypeKind.Interface) problem = new BUProblem(Severity.ERROR, "Only interfaces can have default members");
			switch (predict(CLASS_OR_INTERFACE_BODY_DECL_1_2_2)) {
				case 1:
					pushCallStack(CLASS_OR_INTERFACE_BODY_DECL_1_2_2_1_1);
					ret = parseInitializerDecl(modifiers);
					popCallStack();
					if (typeKind == TypeKind.Interface) ret = ret.withProblem(new BUProblem(Severity.ERROR, "An interface cannot have initializers"));
					break;
				case 2:
					pushCallStack(CLASS_OR_INTERFACE_BODY_DECL_1_2_2_2);
					ret = parseClassOrInterfaceDecl(modifiers);
					popCallStack();
					break;
				case 3:
					pushCallStack(CLASS_OR_INTERFACE_BODY_DECL_1_2_2_3);
					ret = parseEnumDecl(modifiers);
					popCallStack();
					break;
				case 4:
					pushCallStack(CLASS_OR_INTERFACE_BODY_DECL_1_2_2_4);
					ret = parseAnnotationTypeDecl(modifiers);
					popCallStack();
					break;
				case 5:
					pushCallStack(CLASS_OR_INTERFACE_BODY_DECL_1_2_2_5_1);
					ret = parseConstructorDecl(modifiers);
					popCallStack();
					if (typeKind == TypeKind.Interface) ret = ret.withProblem(new BUProblem(Severity.ERROR, "An interface cannot have constructors"));
					break;
				case 6:
					pushCallStack(CLASS_OR_INTERFACE_BODY_DECL_1_2_2_6_1);
					ret = parseFieldDecl(modifiers);
					popCallStack();
					break;
				case 7:
					pushCallStack(CLASS_OR_INTERFACE_BODY_DECL_1_2_2_7);
					ret = parseMethodDecl(modifiers);
					popCallStack();
					break;
				default:
					throw produceParseException(TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.CLASS, TokenType.DOUBLE, TokenType.ENUM, TokenType.FLOAT, TokenType.INT, TokenType.INTERFACE, TokenType.LONG, TokenType.SHORT, TokenType.VOID, TokenType.LBRACE, TokenType.AT, TokenType.LT, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER);
			}
		} else
			throw produceParseException(TokenType.ABSTRACT, TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.CLASS, TokenType.DEFAULT, TokenType.DOUBLE, TokenType.ENUM, TokenType.FINAL, TokenType.FLOAT, TokenType.INT, TokenType.INTERFACE, TokenType.LONG, TokenType.NATIVE, TokenType.PRIVATE, TokenType.PROTECTED, TokenType.PUBLIC, TokenType.SHORT, TokenType.STATIC, TokenType.STRICTFP, TokenType.SYNCHRONIZED, TokenType.TRANSIENT, TokenType.VOID, TokenType.VOLATILE, TokenType.LBRACE, TokenType.SEMICOLON, TokenType.AT, TokenType.LT, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER);
		return ret.withProblem(problem);
	}

	/* sequence(
		nonTerminal(type, Type)
		nonTerminal(variables, VariableDeclarators)
		terminal(SEMICOLON)
		action({ return dress(SFieldDecl.make(modifiers, type, variables)); })
	) */
	protected BUTree<SFieldDecl> parseFieldDecl(BUTree<SNodeList> modifiers) throws ParseException {
		BUTree<? extends SType> type;
		BUTree<SNodeList> variables = emptyList();
		BUTree<SVariableDeclarator> val;
		int __token;
		pushCallStack(FIELD_DECL_1);
		type = parseType(null);
		popCallStack();
		pushCallStack(FIELD_DECL_2);
		variables = parseVariableDeclarators();
		popCallStack();
		consume(TokenType.SEMICOLON);
		return dress(SFieldDecl.make(modifiers, type, variables));
	}

	/* sequence(
		nonTerminal(type, Type)
		nonTerminal(variables, VariableDeclarators)
		action({ return dress(SLocalVariableDecl.make(modifiers, type, variables)); })
	) */
	protected BUTree<SLocalVariableDecl> parseVariableDecl(BUTree<SNodeList> modifiers) throws ParseException {
		BUTree<? extends SType> type;
		BUTree<SNodeList> variables = emptyList();
		int __token;
		pushCallStack(VARIABLE_DECL_1);
		type = parseType(null);
		popCallStack();
		pushCallStack(VARIABLE_DECL_2);
		variables = parseVariableDeclarators();
		popCallStack();
		return dress(SLocalVariableDecl.make(modifiers, type, variables));
	}

	/* sequence(
		nonTerminal(val, VariableDeclarator)
		action({ variables = append(variables, val); })
		zeroOrMore(
			sequence(
				terminal(COMMA)
				nonTerminal(val, VariableDeclarator)
				action({ variables = append(variables, val); })
			)
		)
		action({ return variables; })
	) */
	protected BUTree<SNodeList> parseVariableDeclarators() throws ParseException {
		BUTree<SNodeList> variables = emptyList();
		BUTree<SVariableDeclarator> val;
		int __token;
		pushCallStack(VARIABLE_DECLARATORS_1);
		val = parseVariableDeclarator();
		popCallStack();
		variables = append(variables, val);
		__token = getToken(0).kind;
		while (__token == TokenType.COMMA) {
			consume(TokenType.COMMA);
			pushCallStack(VARIABLE_DECLARATORS_2_1_2);
			val = parseVariableDeclarator();
			popCallStack();
			variables = append(variables, val);
			__token = getToken(0).kind;
		}
		return variables;
	}

	/* sequence(
		action({ run(); })
		nonTerminal(id, VariableDeclaratorId)
		zeroOrOne(
			sequence(
				terminal(ASSIGN)
				nonTerminal(initExpr, VariableInitializer)
				action({ init = optionOf(initExpr); })
			)
		)
		action({ return dress(SVariableDeclarator.make(id, init)); })
	) */
	protected BUTree<SVariableDeclarator> parseVariableDeclarator() throws ParseException {
		BUTree<SVariableDeclaratorId> id;
		BUTree<SNodeOption> init = none();
		BUTree<? extends SExpr> initExpr = null;
		int __token;
		run();
		pushCallStack(VARIABLE_DECLARATOR_1);
		id = parseVariableDeclaratorId();
		popCallStack();
		__token = getToken(0).kind;
		if (__token == TokenType.ASSIGN) {
			consume(TokenType.ASSIGN);
			pushCallStack(VARIABLE_DECLARATOR_2_1_2);
			initExpr = parseVariableInitializer();
			popCallStack();
			init = optionOf(initExpr);
		}
		return dress(SVariableDeclarator.make(id, init));
	}

	/* sequence(
		action({ run(); })
		nonTerminal(name, Name)
		nonTerminal(arrayDims, ArrayDims)
		action({ return dress(SVariableDeclaratorId.make(name, arrayDims)); })
	) */
	protected BUTree<SVariableDeclaratorId> parseVariableDeclaratorId() throws ParseException {
		BUTree<SName> name;
		BUTree<SNodeList> arrayDims;
		int __token;
		run();
		pushCallStack(VARIABLE_DECLARATOR_ID_1);
		name = parseName();
		popCallStack();
		pushCallStack(VARIABLE_DECLARATOR_ID_2);
		arrayDims = parseArrayDims();
		popCallStack();
		return dress(SVariableDeclaratorId.make(name, arrayDims));
	}

	/* sequence(
		zeroOrMore(
			sequence(
				action({ run(); })
				nonTerminal(annotations, Annotations)
				terminal(LBRACKET)
				terminal(RBRACKET)
				action({ arrayDims = append(arrayDims, dress(SArrayDim.make(annotations))); })
			)
		)
		action({ return arrayDims; })
	) */
	protected BUTree<SNodeList> parseArrayDims() throws ParseException {
		BUTree<SNodeList> arrayDims = emptyList();
		BUTree<SNodeList> annotations;
		int __token;
		__token = getToken(0).kind;
		while (predict(ARRAY_DIMS_1) == 1) {
			run();
			pushCallStack(ARRAY_DIMS_1_1_1);
			annotations = parseAnnotations();
			popCallStack();
			consume(TokenType.LBRACKET);
			consume(TokenType.RBRACKET);
			arrayDims = append(arrayDims, dress(SArrayDim.make(annotations)));
			__token = getToken(0).kind;
		}
		return arrayDims;
	}

	/* sequence(
		choice(
			nonTerminal(ret, ArrayInitializer)
			nonTerminal(ret, Expression)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parseVariableInitializer() throws ParseException {
		BUTree<? extends SExpr> ret;
		int __token;
		__token = getToken(0).kind;
		if (__token == TokenType.LBRACE) {
			pushCallStack(VARIABLE_INITIALIZER_1_1);
			ret = parseArrayInitializer();
			popCallStack();
		} else if (((__token - 11 & ~63) == 0 && (1L << __token - 11 & (1L << TokenType.BOOLEAN - 11 | 1L << TokenType.BYTE - 11 | 1L << TokenType.CHAR - 11 | 1L << TokenType.DOUBLE - 11 | 1L << TokenType.FALSE - 11 | 1L << TokenType.FLOAT - 11 | 1L << TokenType.INT - 11 | 1L << TokenType.LONG - 11 | 1L << TokenType.NEW - 11 | 1L << TokenType.NULL - 11 | 1L << TokenType.SHORT - 11 | 1L << TokenType.SUPER - 11 | 1L << TokenType.THIS - 11 | 1L << TokenType.TRUE - 11 | 1L << TokenType.VOID - 11 | 1L << TokenType.LONG_LITERAL - 11 | 1L << TokenType.INTEGER_LITERAL - 11 | 1L << TokenType.FLOAT_LITERAL - 11 | 1L << TokenType.DOUBLE_LITERAL - 11)) != 0) || ((__token - 78 & ~63) == 0 && (1L << __token - 78 & (1L << TokenType.CHARACTER_LITERAL - 78 | 1L << TokenType.STRING_LITERAL - 78 | 1L << TokenType.LPAREN - 78 | 1L << TokenType.LT - 78 | 1L << TokenType.BANG - 78 | 1L << TokenType.TILDE - 78 | 1L << TokenType.INCR - 78 | 1L << TokenType.DECR - 78 | 1L << TokenType.PLUS - 78 | 1L << TokenType.MINUS - 78 | 1L << TokenType.NODE_VARIABLE - 78 | 1L << TokenType.IDENTIFIER - 78)) != 0)) {
			pushCallStack(VARIABLE_INITIALIZER_1_2);
			ret = parseExpression();
			popCallStack();
		} else
			throw produceParseException(TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.DOUBLE, TokenType.FALSE, TokenType.FLOAT, TokenType.INT, TokenType.LONG, TokenType.NEW, TokenType.NULL, TokenType.SHORT, TokenType.SUPER, TokenType.THIS, TokenType.TRUE, TokenType.VOID, TokenType.LONG_LITERAL, TokenType.INTEGER_LITERAL, TokenType.FLOAT_LITERAL, TokenType.DOUBLE_LITERAL, TokenType.CHARACTER_LITERAL, TokenType.STRING_LITERAL, TokenType.LPAREN, TokenType.LBRACE, TokenType.LT, TokenType.BANG, TokenType.TILDE, TokenType.INCR, TokenType.DECR, TokenType.PLUS, TokenType.MINUS, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER);
		return ret;
	}

	/* sequence(
		action({ run(); })
		terminal(LBRACE)
		zeroOrOne(
			sequence(
				nonTerminal(val, VariableInitializer)
				action({ values = append(values, val); })
				zeroOrMore(
					sequence(
						terminal(COMMA)
						nonTerminal(val, VariableInitializer)
						action({ values = append(values, val); })
					)
				)
			)
		)
		zeroOrOne(
			terminal(COMMA)
			action({ trailingComma = true; })
		)
		terminal(RBRACE)
		action({ return dress(SArrayInitializerExpr.make(values, trailingComma)); })
	) */
	protected BUTree<SArrayInitializerExpr> parseArrayInitializer() throws ParseException {
		BUTree<SNodeList> values = emptyList();
		BUTree<? extends SExpr> val;
		boolean trailingComma = false;
		int __token;
		run();
		consume(TokenType.LBRACE);
		__token = getToken(0).kind;
		if (((__token - 11 & ~63) == 0 && (1L << __token - 11 & (1L << TokenType.BOOLEAN - 11 | 1L << TokenType.BYTE - 11 | 1L << TokenType.CHAR - 11 | 1L << TokenType.DOUBLE - 11 | 1L << TokenType.FALSE - 11 | 1L << TokenType.FLOAT - 11 | 1L << TokenType.INT - 11 | 1L << TokenType.LONG - 11 | 1L << TokenType.NEW - 11 | 1L << TokenType.NULL - 11 | 1L << TokenType.SHORT - 11 | 1L << TokenType.SUPER - 11 | 1L << TokenType.THIS - 11 | 1L << TokenType.TRUE - 11 | 1L << TokenType.VOID - 11 | 1L << TokenType.LONG_LITERAL - 11 | 1L << TokenType.INTEGER_LITERAL - 11 | 1L << TokenType.FLOAT_LITERAL - 11 | 1L << TokenType.DOUBLE_LITERAL - 11)) != 0) || ((__token - 78 & ~63) == 0 && (1L << __token - 78 & (1L << TokenType.CHARACTER_LITERAL - 78 | 1L << TokenType.STRING_LITERAL - 78 | 1L << TokenType.LPAREN - 78 | 1L << TokenType.LBRACE - 78 | 1L << TokenType.LT - 78 | 1L << TokenType.BANG - 78 | 1L << TokenType.TILDE - 78 | 1L << TokenType.INCR - 78 | 1L << TokenType.DECR - 78 | 1L << TokenType.PLUS - 78 | 1L << TokenType.MINUS - 78 | 1L << TokenType.NODE_VARIABLE - 78 | 1L << TokenType.IDENTIFIER - 78)) != 0)) {
			pushCallStack(ARRAY_INITIALIZER_2_1_1);
			val = parseVariableInitializer();
			popCallStack();
			values = append(values, val);
			__token = getToken(0).kind;
			while (predict(ARRAY_INITIALIZER_2_1_2) == 1) {
				consume(TokenType.COMMA);
				pushCallStack(ARRAY_INITIALIZER_2_1_2_1_2);
				val = parseVariableInitializer();
				popCallStack();
				values = append(values, val);
				__token = getToken(0).kind;
			}
		}
		__token = getToken(0).kind;
		if (__token == TokenType.COMMA) {
			consume(TokenType.COMMA);
			trailingComma = true;
		}
		consume(TokenType.RBRACE);
		return dress(SArrayInitializerExpr.make(values, trailingComma));
	}

	/* sequence(
		zeroOrOne(
			sequence(
				nonTerminal(typeParameters, TypeParameters)
				nonTerminal(additionalAnnotations, Annotations)
			)
		)
		nonTerminal(type, ResultType)
		nonTerminal(name, Name)
		nonTerminal(parameters, FormalParameters)
		nonTerminal(arrayDims, ArrayDims)
		zeroOrOne(
			nonTerminal(throwsClause, ThrowsClause)
		)
		choice(
			nonTerminal(block, Block)
			sequence(
				terminal(SEMICOLON)
				action({
					if (modifiers != null && contains(modifiers, SModifier.make(ModifierKeyword.Default))) problem = new BUProblem(Severity.ERROR, "Default methods must have a body");
				})
			)
		)
		action({ return dress(SMethodDecl.make(modifiers, ensureNotNull(typeParameters), ensureNotNull(additionalAnnotations), type, name, parameters, arrayDims, ensureNotNull(throwsClause), optionOf(block))).withProblem(problem); })
	) */
	protected BUTree<SMethodDecl> parseMethodDecl(BUTree<SNodeList> modifiers) throws ParseException {
		BUTree<SNodeList> typeParameters = null;
		BUTree<SNodeList> additionalAnnotations = null;
		BUTree<? extends SType> type;
		BUTree<SName> name;
		BUTree<SNodeList> parameters;
		BUTree<SNodeList> arrayDims;
		BUTree<SNodeList> throwsClause = null;
		BUTree<SBlockStmt> block = null;
		BUProblem problem = null;
		int __token;
		__token = getToken(0).kind;
		if (__token == TokenType.LT) {
			pushCallStack(METHOD_DECL_1_1_1);
			typeParameters = parseTypeParameters();
			popCallStack();
			pushCallStack(METHOD_DECL_1_1_2);
			additionalAnnotations = parseAnnotations();
			popCallStack();
		}
		pushCallStack(METHOD_DECL_2);
		type = parseResultType();
		popCallStack();
		pushCallStack(METHOD_DECL_3);
		name = parseName();
		popCallStack();
		pushCallStack(METHOD_DECL_4);
		parameters = parseFormalParameters();
		popCallStack();
		pushCallStack(METHOD_DECL_5);
		arrayDims = parseArrayDims();
		popCallStack();
		__token = getToken(0).kind;
		if (__token == TokenType.THROWS) {
			pushCallStack(METHOD_DECL_6_1);
			throwsClause = parseThrowsClause();
			popCallStack();
		}
		__token = getToken(0).kind;
		if (__token == TokenType.LBRACE) {
			pushCallStack(METHOD_DECL_7_1);
			block = parseBlock();
			popCallStack();
		} else if (__token == TokenType.SEMICOLON) {
			consume(TokenType.SEMICOLON);
			if (modifiers != null && contains(modifiers, SModifier.make(ModifierKeyword.Default))) problem = new BUProblem(Severity.ERROR, "Default methods must have a body");
		} else
			throw produceParseException(TokenType.LBRACE, TokenType.SEMICOLON);
		return dress(SMethodDecl.make(modifiers, ensureNotNull(typeParameters), ensureNotNull(additionalAnnotations), type, name, parameters, arrayDims, ensureNotNull(throwsClause), optionOf(block))).withProblem(problem);
	}

	/* sequence(
		terminal(LPAREN)
		zeroOrOne(
			nonTerminal(ret, FormalParameterList)
		)
		terminal(RPAREN)
		action({ return ensureNotNull(ret); })
	) */
	protected BUTree<SNodeList> parseFormalParameters() throws ParseException {
		BUTree<SNodeList> ret = null;
		BUTree<SFormalParameter> par;
		int __token;
		consume(TokenType.LPAREN);
		__token = getToken(0).kind;
		if (((__token - 9 & ~63) == 0 && (1L << __token - 9 & (1L << TokenType.ABSTRACT - 9 | 1L << TokenType.BOOLEAN - 9 | 1L << TokenType.BYTE - 9 | 1L << TokenType.CHAR - 9 | 1L << TokenType.DEFAULT - 9 | 1L << TokenType.DOUBLE - 9 | 1L << TokenType.FINAL - 9 | 1L << TokenType.FLOAT - 9 | 1L << TokenType.INT - 9 | 1L << TokenType.LONG - 9 | 1L << TokenType.NATIVE - 9 | 1L << TokenType.PRIVATE - 9 | 1L << TokenType.PROTECTED - 9 | 1L << TokenType.PUBLIC - 9 | 1L << TokenType.SHORT - 9 | 1L << TokenType.STATIC - 9 | 1L << TokenType.STRICTFP - 9 | 1L << TokenType.SYNCHRONIZED - 9 | 1L << TokenType.TRANSIENT - 9 | 1L << TokenType.VOLATILE - 9)) != 0) || ((__token - 89 & ~63) == 0 && (1L << __token - 89 & (1L << TokenType.AT - 89 | 1L << TokenType.NODE_VARIABLE - 89 | 1L << TokenType.NODE_LIST_VARIABLE - 89 | 1L << TokenType.IDENTIFIER - 89)) != 0)) {
			pushCallStack(FORMAL_PARAMETERS_2_1);
			ret = parseFormalParameterList();
			popCallStack();
		}
		consume(TokenType.RPAREN);
		return ensureNotNull(ret);
	}

	/* sequence(
		choice(
			nonTerminal(ret, NodeListVar)
			sequence(
				nonTerminal(par, FormalParameter)
				action({ ret = append(ret, par); })
				zeroOrMore(
					sequence(
						terminal(COMMA)
						nonTerminal(par, FormalParameter)
						action({ ret = append(ret, par); })
					)
				)
			)
		)
		action({ return ret; })
	) */
	protected BUTree<SNodeList> parseFormalParameterList() throws ParseException {
		BUTree<SNodeList> ret = null;
		BUTree<SFormalParameter> par;
		int __token;
		__token = getToken(0).kind;
		if (__token == TokenType.NODE_LIST_VARIABLE) {
			pushCallStack(FORMAL_PARAMETER_LIST_1_1);
			ret = parseNodeListVar();
			popCallStack();
		} else if (((__token - 9 & ~63) == 0 && (1L << __token - 9 & (1L << TokenType.ABSTRACT - 9 | 1L << TokenType.BOOLEAN - 9 | 1L << TokenType.BYTE - 9 | 1L << TokenType.CHAR - 9 | 1L << TokenType.DEFAULT - 9 | 1L << TokenType.DOUBLE - 9 | 1L << TokenType.FINAL - 9 | 1L << TokenType.FLOAT - 9 | 1L << TokenType.INT - 9 | 1L << TokenType.LONG - 9 | 1L << TokenType.NATIVE - 9 | 1L << TokenType.PRIVATE - 9 | 1L << TokenType.PROTECTED - 9 | 1L << TokenType.PUBLIC - 9 | 1L << TokenType.SHORT - 9 | 1L << TokenType.STATIC - 9 | 1L << TokenType.STRICTFP - 9 | 1L << TokenType.SYNCHRONIZED - 9 | 1L << TokenType.TRANSIENT - 9 | 1L << TokenType.VOLATILE - 9)) != 0) || ((__token - 89 & ~63) == 0 && (1L << __token - 89 & (1L << TokenType.AT - 89 | 1L << TokenType.NODE_VARIABLE - 89 | 1L << TokenType.IDENTIFIER - 89)) != 0)) {
			pushCallStack(FORMAL_PARAMETER_LIST_1_2_1);
			par = parseFormalParameter();
			popCallStack();
			ret = append(ret, par);
			__token = getToken(0).kind;
			while (__token == TokenType.COMMA) {
				consume(TokenType.COMMA);
				pushCallStack(FORMAL_PARAMETER_LIST_1_2_2_1_2);
				par = parseFormalParameter();
				popCallStack();
				ret = append(ret, par);
				__token = getToken(0).kind;
			}
		} else
			throw produceParseException(TokenType.ABSTRACT, TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.DEFAULT, TokenType.DOUBLE, TokenType.FINAL, TokenType.FLOAT, TokenType.INT, TokenType.LONG, TokenType.NATIVE, TokenType.PRIVATE, TokenType.PROTECTED, TokenType.PUBLIC, TokenType.SHORT, TokenType.STATIC, TokenType.STRICTFP, TokenType.SYNCHRONIZED, TokenType.TRANSIENT, TokenType.VOLATILE, TokenType.AT, TokenType.NODE_VARIABLE, TokenType.NODE_LIST_VARIABLE, TokenType.IDENTIFIER);
		return ret;
	}

	/* sequence(
		action({ run(); })
		nonTerminal(modifiers, Modifiers)
		nonTerminal(type, Type)
		zeroOrOne(
			sequence(
				nonTerminal(ellipsisAnnotations, Annotations)
				terminal(ELLIPSIS)
				action({ isVarArg = true; })
			)
		)
		choice(
			sequence(
				zeroOrOne(
					sequence(
						nonTerminal(receiverTypeName, Name)
						terminal(DOT)
					)
				)
				terminal(THIS)
				action({ isReceiver = true; })
			)
			nonTerminal(id, VariableDeclaratorId)
		)
		action({ return dress(SFormalParameter.make(modifiers, type, isVarArg, ensureNotNull(ellipsisAnnotations), optionOf(id), isReceiver, optionOf(receiverTypeName))); })
	) */
	protected BUTree<SFormalParameter> parseFormalParameter() throws ParseException {
		BUTree<SNodeList> modifiers;
		BUTree<? extends SType> type;
		BUTree<SNodeList> ellipsisAnnotations = null;
		boolean isVarArg = false;
		BUTree<SVariableDeclaratorId> id = null;
		boolean isReceiver = false;
		BUTree<SName> receiverTypeName = null;
		int __token;
		run();
		pushCallStack(FORMAL_PARAMETER_1);
		modifiers = parseModifiers();
		popCallStack();
		pushCallStack(FORMAL_PARAMETER_2);
		type = parseType(null);
		popCallStack();
		__token = getToken(0).kind;
		if (__token == TokenType.AT || __token == TokenType.ELLIPSIS) {
			pushCallStack(FORMAL_PARAMETER_3_1_1);
			ellipsisAnnotations = parseAnnotations();
			popCallStack();
			consume(TokenType.ELLIPSIS);
			isVarArg = true;
		}
		switch (predict(FORMAL_PARAMETER_4)) {
			case 1:
				__token = getToken(0).kind;
				if (__token == TokenType.NODE_VARIABLE || __token == TokenType.IDENTIFIER) {
					pushCallStack(FORMAL_PARAMETER_4_1_1_1_1);
					receiverTypeName = parseName();
					popCallStack();
					consume(TokenType.DOT);
				}
				consume(TokenType.THIS);
				isReceiver = true;
				break;
			case 2:
				pushCallStack(FORMAL_PARAMETER_4_2);
				id = parseVariableDeclaratorId();
				popCallStack();
				break;
			default:
				throw produceParseException(TokenType.THIS, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER);
		}
		return dress(SFormalParameter.make(modifiers, type, isVarArg, ensureNotNull(ellipsisAnnotations), optionOf(id), isReceiver, optionOf(receiverTypeName)));
	}

	/* sequence(
		terminal(THROWS)
		nonTerminal(cit, AnnotatedQualifiedType)
		action({ ret = append(ret, cit); })
		zeroOrMore(
			sequence(
				terminal(COMMA)
				nonTerminal(cit, AnnotatedQualifiedType)
				action({ ret = append(ret, cit); })
			)
		)
		action({ return ret; })
	) */
	protected BUTree<SNodeList> parseThrowsClause() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<SQualifiedType> cit;
		int __token;
		consume(TokenType.THROWS);
		pushCallStack(THROWS_CLAUSE_2);
		cit = parseAnnotatedQualifiedType();
		popCallStack();
		ret = append(ret, cit);
		__token = getToken(0).kind;
		while (__token == TokenType.COMMA) {
			consume(TokenType.COMMA);
			pushCallStack(THROWS_CLAUSE_3_1_2);
			cit = parseAnnotatedQualifiedType();
			popCallStack();
			ret = append(ret, cit);
			__token = getToken(0).kind;
		}
		return ret;
	}

	/* sequence(
		zeroOrOne(
			nonTerminal(typeParameters, TypeParameters)
		)
		nonTerminal(name, Name)
		nonTerminal(parameters, FormalParameters)
		zeroOrOne(
			nonTerminal(throwsClause, ThrowsClause)
		)
		action({ run(); })
		terminal(LBRACE)
		nonTerminal(stmts, Statements)
		terminal(RBRACE)
		action({ block = dress(SBlockStmt.make(stmts)); })
		action({ return dress(SConstructorDecl.make(modifiers, ensureNotNull(typeParameters), name, parameters, ensureNotNull(throwsClause), block)); })
	) */
	protected BUTree<SConstructorDecl> parseConstructorDecl(BUTree<SNodeList> modifiers) throws ParseException {
		BUTree<SNodeList> typeParameters = null;
		BUTree<SName> name;
		BUTree<SNodeList> parameters;
		BUTree<SNodeList> throwsClause = null;
		BUTree<SExplicitConstructorInvocationStmt> exConsInv = null;
		BUTree<SBlockStmt> block;
		BUTree<SNodeList> stmts = emptyList();
		BUTree<? extends SStmt> stmt;
		int __token;
		__token = getToken(0).kind;
		if (__token == TokenType.LT) {
			pushCallStack(CONSTRUCTOR_DECL_1_1);
			typeParameters = parseTypeParameters();
			popCallStack();
		}
		pushCallStack(CONSTRUCTOR_DECL_2);
		name = parseName();
		popCallStack();
		pushCallStack(CONSTRUCTOR_DECL_3);
		parameters = parseFormalParameters();
		popCallStack();
		__token = getToken(0).kind;
		if (__token == TokenType.THROWS) {
			pushCallStack(CONSTRUCTOR_DECL_4_1);
			throwsClause = parseThrowsClause();
			popCallStack();
		}
		run();
		consume(TokenType.LBRACE);
		pushCallStack(CONSTRUCTOR_DECL_6);
		stmts = parseStatements();
		popCallStack();
		consume(TokenType.RBRACE);
		block = dress(SBlockStmt.make(stmts));
		return dress(SConstructorDecl.make(modifiers, ensureNotNull(typeParameters), name, parameters, ensureNotNull(throwsClause), block));
	}

	/* sequence(
		action({ run(); })
		choice(
			sequence(
				zeroOrOne(
					nonTerminal(typeArgs, TypeArguments)
				)
				terminal(THIS)
				action({ isThis = true; })
				nonTerminal(args, Arguments)
				terminal(SEMICOLON)
			)
			sequence(
				zeroOrOne(
					sequence(
						nonTerminal(expr, PrimaryExpressionWithoutSuperSuffix)
						terminal(DOT)
					)
				)
				zeroOrOne(
					nonTerminal(typeArgs, TypeArguments)
				)
				terminal(SUPER)
				nonTerminal(args, Arguments)
				terminal(SEMICOLON)
			)
		)
		action({ return dress(SExplicitConstructorInvocationStmt.make(ensureNotNull(typeArgs), isThis, optionOf(expr), args)); })
	) */
	protected BUTree<SExplicitConstructorInvocationStmt> parseExplicitConstructorInvocation() throws ParseException {
		boolean isThis = false;
		BUTree<SNodeList> args;
		BUTree<? extends SExpr> expr = null;
		BUTree<SNodeList> typeArgs = null;
		int __token;
		run();
		switch (predict(EXPLICIT_CONSTRUCTOR_INVOCATION_1)) {
			case 1:
				__token = getToken(0).kind;
				if (__token == TokenType.LT) {
					pushCallStack(EXPLICIT_CONSTRUCTOR_INVOCATION_1_1_1_1);
					typeArgs = parseTypeArguments();
					popCallStack();
				}
				consume(TokenType.THIS);
				isThis = true;
				pushCallStack(EXPLICIT_CONSTRUCTOR_INVOCATION_1_1_3);
				args = parseArguments();
				popCallStack();
				consume(TokenType.SEMICOLON);
				break;
			case 2:
				__token = getToken(0).kind;
				if (predict(EXPLICIT_CONSTRUCTOR_INVOCATION_1_2_1) == 1) {
					pushCallStack(EXPLICIT_CONSTRUCTOR_INVOCATION_1_2_1_1_1);
					expr = parsePrimaryExpressionWithoutSuperSuffix();
					popCallStack();
					consume(TokenType.DOT);
				}
				__token = getToken(0).kind;
				if (__token == TokenType.LT) {
					pushCallStack(EXPLICIT_CONSTRUCTOR_INVOCATION_1_2_2_1);
					typeArgs = parseTypeArguments();
					popCallStack();
				}
				consume(TokenType.SUPER);
				pushCallStack(EXPLICIT_CONSTRUCTOR_INVOCATION_1_2_4);
				args = parseArguments();
				popCallStack();
				consume(TokenType.SEMICOLON);
				break;
			default:
				throw produceParseException(TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.DOUBLE, TokenType.FALSE, TokenType.FLOAT, TokenType.INT, TokenType.LONG, TokenType.NEW, TokenType.NULL, TokenType.SHORT, TokenType.SUPER, TokenType.THIS, TokenType.TRUE, TokenType.VOID, TokenType.LONG_LITERAL, TokenType.INTEGER_LITERAL, TokenType.FLOAT_LITERAL, TokenType.DOUBLE_LITERAL, TokenType.CHARACTER_LITERAL, TokenType.STRING_LITERAL, TokenType.LPAREN, TokenType.LT, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER);
		}
		return dress(SExplicitConstructorInvocationStmt.make(ensureNotNull(typeArgs), isThis, optionOf(expr), args));
	}

	/* sequence(
		zeroOrOne(
			choice(
				nonTerminal(ret, NodeListVar)
				sequence(
					zeroOrOne(
						nonTerminal(stmt, ExplicitConstructorInvocation)
						action({ ret = append(ret, stmt); })
					)
					zeroOrMore(
						nonTerminal(stmt, BlockStatement)
						action({ ret = append(ret, stmt); })
					)
				)
			)
		)
		action({ return ensureNotNull(ret); })
	) */
	protected BUTree<SNodeList> parseStatements() throws ParseException {
		BUTree<SNodeList> ret = null;
		BUTree<? extends SStmt> stmt;
		int __token;
		__token = getToken(0).kind;
		if (__token == TokenType.NODE_LIST_VARIABLE) {
			pushCallStack(STATEMENTS_1_1_1);
			ret = parseNodeListVar();
			popCallStack();
		} else if (((__token - 0 & ~63) == 0 && (1L << __token - 0 & (1L << TokenType.EOF - 0 | 1L << TokenType.ABSTRACT - 0 | 1L << TokenType.ASSERT - 0 | 1L << TokenType.BOOLEAN - 0 | 1L << TokenType.BREAK - 0 | 1L << TokenType.BYTE - 0 | 1L << TokenType.CHAR - 0 | 1L << TokenType.CLASS - 0 | 1L << TokenType.CONTINUE - 0 | 1L << TokenType.DO - 0 | 1L << TokenType.DOUBLE - 0 | 1L << TokenType.FALSE - 0 | 1L << TokenType.FINAL - 0 | 1L << TokenType.FLOAT - 0 | 1L << TokenType.FOR - 0 | 1L << TokenType.IF - 0 | 1L << TokenType.INT - 0 | 1L << TokenType.INTERFACE - 0 | 1L << TokenType.LONG - 0 | 1L << TokenType.NATIVE - 0 | 1L << TokenType.NEW - 0 | 1L << TokenType.NULL - 0 | 1L << TokenType.PRIVATE - 0 | 1L << TokenType.PROTECTED - 0 | 1L << TokenType.PUBLIC - 0 | 1L << TokenType.RETURN - 0 | 1L << TokenType.SHORT - 0 | 1L << TokenType.STATIC - 0 | 1L << TokenType.STRICTFP - 0 | 1L << TokenType.SUPER - 0 | 1L << TokenType.SWITCH - 0 | 1L << TokenType.SYNCHRONIZED - 0 | 1L << TokenType.THIS - 0 | 1L << TokenType.THROW - 0 | 1L << TokenType.TRANSIENT - 0 | 1L << TokenType.TRUE - 0 | 1L << TokenType.TRY - 0 | 1L << TokenType.VOID - 0 | 1L << TokenType.VOLATILE - 0 | 1L << TokenType.WHILE - 0 | 1L << TokenType.LONG_LITERAL - 0 | 1L << TokenType.INTEGER_LITERAL - 0)) != 0 || (__token - 68 & ~63) == 0 && (1L << __token - 68 & (1L << TokenType.FLOAT_LITERAL - 68 | 1L << TokenType.DOUBLE_LITERAL - 68 | 1L << TokenType.CHARACTER_LITERAL - 68 | 1L << TokenType.STRING_LITERAL - 68 | 1L << TokenType.LPAREN - 68 | 1L << TokenType.LBRACE - 68 | 1L << TokenType.RBRACE - 68 | 1L << TokenType.SEMICOLON - 68 | 1L << TokenType.AT - 68 | 1L << TokenType.LT - 68 | 1L << TokenType.BANG - 68 | 1L << TokenType.TILDE - 68 | 1L << TokenType.INCR - 68 | 1L << TokenType.DECR - 68 | 1L << TokenType.PLUS - 68 | 1L << TokenType.MINUS - 68 | 1L << TokenType.NODE_VARIABLE - 68)) != 0) || ((__token - 132 & ~63) == 0 && (1L << __token - 132 & (1L << TokenType.IDENTIFIER - 132)) != 0)) {
			__token = getToken(0).kind;
			if (predict(STATEMENTS_1_1_2_1) == 1) {
				pushCallStack(STATEMENTS_1_1_2_1_1);
				stmt = parseExplicitConstructorInvocation();
				popCallStack();
				ret = append(ret, stmt);
			}
			__token = getToken(0).kind;
			while (((__token - 9 & ~63) == 0 && (1L << __token - 9 & (1L << TokenType.ABSTRACT - 9 | 1L << TokenType.ASSERT - 9 | 1L << TokenType.BOOLEAN - 9 | 1L << TokenType.BREAK - 9 | 1L << TokenType.BYTE - 9 | 1L << TokenType.CHAR - 9 | 1L << TokenType.CLASS - 9 | 1L << TokenType.CONTINUE - 9 | 1L << TokenType.DO - 9 | 1L << TokenType.DOUBLE - 9 | 1L << TokenType.FALSE - 9 | 1L << TokenType.FINAL - 9 | 1L << TokenType.FLOAT - 9 | 1L << TokenType.FOR - 9 | 1L << TokenType.IF - 9 | 1L << TokenType.INT - 9 | 1L << TokenType.INTERFACE - 9 | 1L << TokenType.LONG - 9 | 1L << TokenType.NATIVE - 9 | 1L << TokenType.NEW - 9 | 1L << TokenType.NULL - 9 | 1L << TokenType.PRIVATE - 9 | 1L << TokenType.PROTECTED - 9 | 1L << TokenType.PUBLIC - 9 | 1L << TokenType.RETURN - 9 | 1L << TokenType.SHORT - 9 | 1L << TokenType.STATIC - 9 | 1L << TokenType.STRICTFP - 9 | 1L << TokenType.SUPER - 9 | 1L << TokenType.SWITCH - 9 | 1L << TokenType.SYNCHRONIZED - 9 | 1L << TokenType.THIS - 9 | 1L << TokenType.THROW - 9 | 1L << TokenType.TRANSIENT - 9 | 1L << TokenType.TRUE - 9 | 1L << TokenType.TRY - 9 | 1L << TokenType.VOID - 9 | 1L << TokenType.VOLATILE - 9 | 1L << TokenType.WHILE - 9 | 1L << TokenType.LONG_LITERAL - 9 | 1L << TokenType.INTEGER_LITERAL - 9 | 1L << TokenType.FLOAT_LITERAL - 9 | 1L << TokenType.DOUBLE_LITERAL - 9)) != 0) || ((__token - 78 & ~63) == 0 && (1L << __token - 78 & (1L << TokenType.CHARACTER_LITERAL - 78 | 1L << TokenType.STRING_LITERAL - 78 | 1L << TokenType.LPAREN - 78 | 1L << TokenType.LBRACE - 78 | 1L << TokenType.SEMICOLON - 78 | 1L << TokenType.AT - 78 | 1L << TokenType.LT - 78 | 1L << TokenType.BANG - 78 | 1L << TokenType.TILDE - 78 | 1L << TokenType.INCR - 78 | 1L << TokenType.DECR - 78 | 1L << TokenType.PLUS - 78 | 1L << TokenType.MINUS - 78 | 1L << TokenType.NODE_VARIABLE - 78 | 1L << TokenType.IDENTIFIER - 78)) != 0)) {
				pushCallStack(STATEMENTS_1_1_2_2_1);
				stmt = parseBlockStatement();
				popCallStack();
				ret = append(ret, stmt);
				__token = getToken(0).kind;
			}
		}
		return ensureNotNull(ret);
	}

	/* sequence(
		nonTerminal(block, Block)
		action({ return dress(SInitializerDecl.make(modifiers, block)); })
	) */
	protected BUTree<SInitializerDecl> parseInitializerDecl(BUTree<SNodeList> modifiers) throws ParseException {
		BUTree<SBlockStmt> block;
		int __token;
		pushCallStack(INITIALIZER_DECL_1);
		block = parseBlock();
		popCallStack();
		return dress(SInitializerDecl.make(modifiers, block));
	}

	/* sequence(
		choice(
			sequence(
				nonTerminal(primitiveType, PrimitiveType)
				zeroOrOne(
					action({ lateRun(); })
					nonTerminal(arrayDims, ArrayDimsMandatory)
					action({ type = dress(SArrayType.make(primitiveType, arrayDims)); })
				)
			)
			sequence(
				nonTerminal(type, QualifiedType)
				zeroOrOne(
					action({ lateRun(); })
					nonTerminal(arrayDims, ArrayDimsMandatory)
					action({ type = dress(SArrayType.make(type, arrayDims)); })
				)
			)
		)
		action({ return type == null ? primitiveType : type; })
	) */
	protected BUTree<? extends SType> parseType(BUTree<SNodeList> annotations) throws ParseException {
		BUTree<? extends SType> primitiveType = null;
		BUTree<? extends SReferenceType> type = null;
		BUTree<SNodeList> arrayDims;
		int __token;
		__token = getToken(0).kind;
		if ((__token - 11 & ~63) == 0 && (1L << __token - 11 & (1L << TokenType.BOOLEAN - 11 | 1L << TokenType.BYTE - 11 | 1L << TokenType.CHAR - 11 | 1L << TokenType.DOUBLE - 11 | 1L << TokenType.FLOAT - 11 | 1L << TokenType.INT - 11 | 1L << TokenType.LONG - 11 | 1L << TokenType.SHORT - 11)) != 0) {
			pushCallStack(TYPE_1_1_1);
			primitiveType = parsePrimitiveType(annotations);
			popCallStack();
			__token = getToken(0).kind;
			if (__token == TokenType.LBRACKET || __token == TokenType.AT) {
				lateRun();
				pushCallStack(TYPE_1_1_2_1);
				arrayDims = parseArrayDimsMandatory();
				popCallStack();
				type = dress(SArrayType.make(primitiveType, arrayDims));
			}
		} else if (__token == TokenType.NODE_VARIABLE || __token == TokenType.IDENTIFIER) {
			pushCallStack(TYPE_1_2_1);
			type = parseQualifiedType(annotations);
			popCallStack();
			__token = getToken(0).kind;
			if (predict(TYPE_1_2_2) == 1) {
				lateRun();
				pushCallStack(TYPE_1_2_2_1);
				arrayDims = parseArrayDimsMandatory();
				popCallStack();
				type = dress(SArrayType.make(type, arrayDims));
			}
		} else
			throw produceParseException(TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.DOUBLE, TokenType.FLOAT, TokenType.INT, TokenType.LONG, TokenType.SHORT, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER);
		return type == null ? primitiveType : type;
	}

	/* sequence(
		choice(
			sequence(
				nonTerminal(primitiveType, PrimitiveType)
				action({ lateRun(); })
				nonTerminal(arrayDims, ArrayDimsMandatory)
				action({ type = dress(SArrayType.make(primitiveType, arrayDims)); })
			)
			sequence(
				nonTerminal(type, QualifiedType)
				zeroOrOne(
					action({ lateRun(); })
					nonTerminal(arrayDims, ArrayDimsMandatory)
					action({ type = dress(SArrayType.make(type, arrayDims)); })
				)
			)
		)
		action({ return type; })
	) */
	protected BUTree<? extends SReferenceType> parseReferenceType(BUTree<SNodeList> annotations) throws ParseException {
		BUTree<? extends SType> primitiveType;
		BUTree<? extends SReferenceType> type;
		BUTree<SNodeList> arrayDims;
		int __token;
		__token = getToken(0).kind;
		if ((__token - 11 & ~63) == 0 && (1L << __token - 11 & (1L << TokenType.BOOLEAN - 11 | 1L << TokenType.BYTE - 11 | 1L << TokenType.CHAR - 11 | 1L << TokenType.DOUBLE - 11 | 1L << TokenType.FLOAT - 11 | 1L << TokenType.INT - 11 | 1L << TokenType.LONG - 11 | 1L << TokenType.SHORT - 11)) != 0) {
			pushCallStack(REFERENCE_TYPE_1_1_1);
			primitiveType = parsePrimitiveType(annotations);
			popCallStack();
			lateRun();
			pushCallStack(REFERENCE_TYPE_1_1_2);
			arrayDims = parseArrayDimsMandatory();
			popCallStack();
			type = dress(SArrayType.make(primitiveType, arrayDims));
		} else if (__token == TokenType.NODE_VARIABLE || __token == TokenType.IDENTIFIER) {
			pushCallStack(REFERENCE_TYPE_1_2_1);
			type = parseQualifiedType(annotations);
			popCallStack();
			__token = getToken(0).kind;
			if (__token == TokenType.LBRACKET || __token == TokenType.AT) {
				lateRun();
				pushCallStack(REFERENCE_TYPE_1_2_2_1);
				arrayDims = parseArrayDimsMandatory();
				popCallStack();
				type = dress(SArrayType.make(type, arrayDims));
			}
		} else
			throw produceParseException(TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.DOUBLE, TokenType.FLOAT, TokenType.INT, TokenType.LONG, TokenType.SHORT, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER);
		return type;
	}

	/* sequence(
		action({
			if (annotations == null) {
				run();
				annotations = emptyList();
			}
		})
		nonTerminal(name, Name)
		zeroOrOne(
			nonTerminal(typeArgs, TypeArgumentsOrDiamond)
		)
		action({ ret = dress(SQualifiedType.make(annotations, scope, name, optionOf(typeArgs))); })
		zeroOrMore(
			sequence(
				action({ lateRun(); })
				terminal(DOT)
				action({ scope = optionOf(ret); })
				nonTerminal(annotations, Annotations)
				nonTerminal(name, Name)
				zeroOrOne(
					nonTerminal(typeArgs, TypeArgumentsOrDiamond)
				)
				action({ ret = dress(SQualifiedType.make(annotations, scope, name, optionOf(typeArgs))); })
			)
		)
		action({ return ret; })
	) */
	protected BUTree<SQualifiedType> parseQualifiedType(BUTree<SNodeList> annotations) throws ParseException {
		BUTree<SNodeOption> scope = none();
		BUTree<SQualifiedType> ret;
		BUTree<SName> name;
		BUTree<SNodeList> typeArgs = null;
		int __token;
		if (annotations == null) {
			run();
			annotations = emptyList();
		}
		pushCallStack(QUALIFIED_TYPE_1);
		name = parseName();
		popCallStack();
		__token = getToken(0).kind;
		if (predict(QUALIFIED_TYPE_2) == 1) {
			pushCallStack(QUALIFIED_TYPE_2_1);
			typeArgs = parseTypeArgumentsOrDiamond();
			popCallStack();
		}
		ret = dress(SQualifiedType.make(annotations, scope, name, optionOf(typeArgs)));
		__token = getToken(0).kind;
		while (predict(QUALIFIED_TYPE_3) == 1) {
			lateRun();
			consume(TokenType.DOT);
			scope = optionOf(ret);
			pushCallStack(QUALIFIED_TYPE_3_1_2);
			annotations = parseAnnotations();
			popCallStack();
			pushCallStack(QUALIFIED_TYPE_3_1_3);
			name = parseName();
			popCallStack();
			__token = getToken(0).kind;
			if (predict(QUALIFIED_TYPE_3_1_4) == 1) {
				pushCallStack(QUALIFIED_TYPE_3_1_4_1);
				typeArgs = parseTypeArgumentsOrDiamond();
				popCallStack();
			}
			ret = dress(SQualifiedType.make(annotations, scope, name, optionOf(typeArgs)));
			__token = getToken(0).kind;
		}
		return ret;
	}

	/* sequence(
		terminal(LT)
		nonTerminal(ret, TypeArgumentList)
		terminal(GT)
		action({ return ret; })
	) */
	protected BUTree<SNodeList> parseTypeArguments() throws ParseException {
		BUTree<SNodeList> ret = null;
		BUTree<? extends SType> type;
		int __token;
		consume(TokenType.LT);
		pushCallStack(TYPE_ARGUMENTS_2);
		ret = parseTypeArgumentList();
		popCallStack();
		consume(TokenType.GT);
		return ret;
	}

	/* sequence(
		terminal(LT)
		zeroOrOne(
			nonTerminal(ret, TypeArgumentList)
		)
		terminal(GT)
		action({ return ret; })
	) */
	protected BUTree<SNodeList> parseTypeArgumentsOrDiamond() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<? extends SType> type;
		int __token;
		consume(TokenType.LT);
		__token = getToken(0).kind;
		if (((__token - 11 & ~63) == 0 && (1L << __token - 11 & (1L << TokenType.BOOLEAN - 11 | 1L << TokenType.BYTE - 11 | 1L << TokenType.CHAR - 11 | 1L << TokenType.DOUBLE - 11 | 1L << TokenType.FLOAT - 11 | 1L << TokenType.INT - 11 | 1L << TokenType.LONG - 11 | 1L << TokenType.SHORT - 11)) != 0) || ((__token - 89 & ~63) == 0 && (1L << __token - 89 & (1L << TokenType.AT - 89 | 1L << TokenType.HOOK - 89 | 1L << TokenType.NODE_VARIABLE - 89 | 1L << TokenType.NODE_LIST_VARIABLE - 89 | 1L << TokenType.IDENTIFIER - 89)) != 0)) {
			pushCallStack(TYPE_ARGUMENTS_OR_DIAMOND_2_1);
			ret = parseTypeArgumentList();
			popCallStack();
		}
		consume(TokenType.GT);
		return ret;
	}

	/* choice(
		sequence(
			nonTerminal(ret, NodeListVar)
			action({ return ret; })
		)
		sequence(
			nonTerminal(type, TypeArgument)
			action({ ret = append(ret, type); })
			zeroOrMore(
				sequence(
					terminal(COMMA)
					nonTerminal(type, TypeArgument)
					action({ ret = append(ret, type); })
				)
			)
			action({ return ret; })
		)
	) */
	protected BUTree<SNodeList> parseTypeArgumentList() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<? extends SType> type;
		int __token;
		__token = getToken(0).kind;
		if (__token == TokenType.NODE_LIST_VARIABLE) {
			pushCallStack(TYPE_ARGUMENT_LIST_1_1);
			ret = parseNodeListVar();
			popCallStack();
			return ret;
		} else if (((__token - 11 & ~63) == 0 && (1L << __token - 11 & (1L << TokenType.BOOLEAN - 11 | 1L << TokenType.BYTE - 11 | 1L << TokenType.CHAR - 11 | 1L << TokenType.DOUBLE - 11 | 1L << TokenType.FLOAT - 11 | 1L << TokenType.INT - 11 | 1L << TokenType.LONG - 11 | 1L << TokenType.SHORT - 11)) != 0) || ((__token - 89 & ~63) == 0 && (1L << __token - 89 & (1L << TokenType.AT - 89 | 1L << TokenType.HOOK - 89 | 1L << TokenType.NODE_VARIABLE - 89 | 1L << TokenType.IDENTIFIER - 89)) != 0)) {
			pushCallStack(TYPE_ARGUMENT_LIST_2_1);
			type = parseTypeArgument();
			popCallStack();
			ret = append(ret, type);
			__token = getToken(0).kind;
			while (__token == TokenType.COMMA) {
				consume(TokenType.COMMA);
				pushCallStack(TYPE_ARGUMENT_LIST_2_2_1_2);
				type = parseTypeArgument();
				popCallStack();
				ret = append(ret, type);
				__token = getToken(0).kind;
			}
			return ret;
		} else
			throw produceParseException(TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.DOUBLE, TokenType.FLOAT, TokenType.INT, TokenType.LONG, TokenType.SHORT, TokenType.AT, TokenType.HOOK, TokenType.NODE_VARIABLE, TokenType.NODE_LIST_VARIABLE, TokenType.IDENTIFIER);
	}

	/* sequence(
		action({ run(); })
		nonTerminal(annotations, Annotations)
		choice(
			nonTerminal(ret, ReferenceType)
			nonTerminal(ret, Wildcard)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SType> parseTypeArgument() throws ParseException {
		BUTree<? extends SType> ret;
		BUTree<SNodeList> annotations = null;
		int __token;
		run();
		pushCallStack(TYPE_ARGUMENT_1);
		annotations = parseAnnotations();
		popCallStack();
		__token = getToken(0).kind;
		if (((__token - 11 & ~63) == 0 && (1L << __token - 11 & (1L << TokenType.BOOLEAN - 11 | 1L << TokenType.BYTE - 11 | 1L << TokenType.CHAR - 11 | 1L << TokenType.DOUBLE - 11 | 1L << TokenType.FLOAT - 11 | 1L << TokenType.INT - 11 | 1L << TokenType.LONG - 11 | 1L << TokenType.SHORT - 11)) != 0) || ((__token - 130 & ~63) == 0 && (1L << __token - 130 & (1L << TokenType.NODE_VARIABLE - 130 | 1L << TokenType.IDENTIFIER - 130)) != 0)) {
			pushCallStack(TYPE_ARGUMENT_2_1);
			ret = parseReferenceType(annotations);
			popCallStack();
		} else if (__token == TokenType.HOOK) {
			pushCallStack(TYPE_ARGUMENT_2_2);
			ret = parseWildcard(annotations);
			popCallStack();
		} else
			throw produceParseException(TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.DOUBLE, TokenType.FLOAT, TokenType.INT, TokenType.LONG, TokenType.SHORT, TokenType.HOOK, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER);
		return ret;
	}

	/* sequence(
		action({
			if (annotations == null) {
				run();
				annotations = emptyList();
			}
		})
		terminal(HOOK)
		zeroOrOne(
			choice(
				sequence(
					terminal(EXTENDS)
					action({ run(); })
					nonTerminal(boundAnnotations, Annotations)
					nonTerminal(ext, ReferenceType)
				)
				sequence(
					terminal(SUPER)
					action({ run(); })
					nonTerminal(boundAnnotations, Annotations)
					nonTerminal(sup, ReferenceType)
				)
			)
		)
		action({ return dress(SWildcardType.make(annotations, optionOf(ext), optionOf(sup))); })
	) */
	protected BUTree<SWildcardType> parseWildcard(BUTree<SNodeList> annotations) throws ParseException {
		BUTree<? extends SReferenceType> ext = null;
		BUTree<? extends SReferenceType> sup = null;
		BUTree<SNodeList> boundAnnotations = null;
		int __token;
		if (annotations == null) {
			run();
			annotations = emptyList();
		}
		consume(TokenType.HOOK);
		__token = getToken(0).kind;
		if (__token == TokenType.EXTENDS) {
			consume(TokenType.EXTENDS);
			run();
			pushCallStack(WILDCARD_2_1_1_2);
			boundAnnotations = parseAnnotations();
			popCallStack();
			pushCallStack(WILDCARD_2_1_1_3);
			ext = parseReferenceType(boundAnnotations);
			popCallStack();
		} else if (__token == TokenType.SUPER) {
			consume(TokenType.SUPER);
			run();
			pushCallStack(WILDCARD_2_1_2_2);
			boundAnnotations = parseAnnotations();
			popCallStack();
			pushCallStack(WILDCARD_2_1_2_3);
			sup = parseReferenceType(boundAnnotations);
			popCallStack();
		}
		return dress(SWildcardType.make(annotations, optionOf(ext), optionOf(sup)));
	}

	/* sequence(
		action({
			if (annotations == null) {
				run();
				annotations = emptyList();
			}
		})
		choice(
			sequence(
				terminal(BOOLEAN)
				action({ primitive = Primitive.Boolean; })
			)
			sequence(
				terminal(CHAR)
				action({ primitive = Primitive.Char; })
			)
			sequence(
				terminal(BYTE)
				action({ primitive = Primitive.Byte; })
			)
			sequence(
				terminal(SHORT)
				action({ primitive = Primitive.Short; })
			)
			sequence(
				terminal(INT)
				action({ primitive = Primitive.Int; })
			)
			sequence(
				terminal(LONG)
				action({ primitive = Primitive.Long; })
			)
			sequence(
				terminal(FLOAT)
				action({ primitive = Primitive.Float; })
			)
			sequence(
				terminal(DOUBLE)
				action({ primitive = Primitive.Double; })
			)
		)
		action({ return dress(SPrimitiveType.make(annotations, primitive)); })
	) */
	protected BUTree<SPrimitiveType> parsePrimitiveType(BUTree<SNodeList> annotations) throws ParseException {
		Primitive primitive;
		int __token;
		if (annotations == null) {
			run();
			annotations = emptyList();
		}
		__token = getToken(0).kind;
		if (__token == TokenType.BOOLEAN) {
			consume(TokenType.BOOLEAN);
			primitive = Primitive.Boolean;
		} else if (__token == TokenType.CHAR) {
			consume(TokenType.CHAR);
			primitive = Primitive.Char;
		} else if (__token == TokenType.BYTE) {
			consume(TokenType.BYTE);
			primitive = Primitive.Byte;
		} else if (__token == TokenType.SHORT) {
			consume(TokenType.SHORT);
			primitive = Primitive.Short;
		} else if (__token == TokenType.INT) {
			consume(TokenType.INT);
			primitive = Primitive.Int;
		} else if (__token == TokenType.LONG) {
			consume(TokenType.LONG);
			primitive = Primitive.Long;
		} else if (__token == TokenType.FLOAT) {
			consume(TokenType.FLOAT);
			primitive = Primitive.Float;
		} else if (__token == TokenType.DOUBLE) {
			consume(TokenType.DOUBLE);
			primitive = Primitive.Double;
		} else
			throw produceParseException(TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.DOUBLE, TokenType.FLOAT, TokenType.INT, TokenType.LONG, TokenType.SHORT);
		return dress(SPrimitiveType.make(annotations, primitive));
	}

	/* sequence(
		choice(
			sequence(
				action({ run(); })
				terminal(VOID)
				action({ ret = dress(SVoidType.make()); })
			)
			nonTerminal(ret, Type)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SType> parseResultType() throws ParseException {
		BUTree<? extends SType> ret;
		int __token;
		__token = getToken(0).kind;
		if (__token == TokenType.VOID) {
			run();
			consume(TokenType.VOID);
			ret = dress(SVoidType.make());
		} else if (((__token - 11 & ~63) == 0 && (1L << __token - 11 & (1L << TokenType.BOOLEAN - 11 | 1L << TokenType.BYTE - 11 | 1L << TokenType.CHAR - 11 | 1L << TokenType.DOUBLE - 11 | 1L << TokenType.FLOAT - 11 | 1L << TokenType.INT - 11 | 1L << TokenType.LONG - 11 | 1L << TokenType.SHORT - 11)) != 0) || ((__token - 130 & ~63) == 0 && (1L << __token - 130 & (1L << TokenType.NODE_VARIABLE - 130 | 1L << TokenType.IDENTIFIER - 130)) != 0)) {
			pushCallStack(RESULT_TYPE_1_2);
			ret = parseType(null);
			popCallStack();
		} else
			throw produceParseException(TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.DOUBLE, TokenType.FLOAT, TokenType.INT, TokenType.LONG, TokenType.SHORT, TokenType.VOID, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER);
		return ret;
	}

	/* sequence(
		action({ run(); })
		nonTerminal(annotations, Annotations)
		nonTerminal(ret, QualifiedType)
		action({ return ret; })
	) */
	protected BUTree<SQualifiedType> parseAnnotatedQualifiedType() throws ParseException {
		BUTree<SNodeList> annotations;
		BUTree<SQualifiedType> ret;
		int __token;
		run();
		pushCallStack(ANNOTATED_QUALIFIED_TYPE_1);
		annotations = parseAnnotations();
		popCallStack();
		pushCallStack(ANNOTATED_QUALIFIED_TYPE_2);
		ret = parseQualifiedType(annotations);
		popCallStack();
		return ret;
	}

	/* sequence(
		action({ run(); })
		nonTerminal(name, Name)
		action({ ret = dress(SQualifiedName.make(qualifier, name)); })
		zeroOrMore(
			sequence(
				action({ lateRun(); })
				terminal(DOT)
				action({ qualifier = optionOf(ret); })
				nonTerminal(name, Name)
				action({ ret = dress(SQualifiedName.make(qualifier, name)); })
			)
		)
		action({ return ret; })
	) */
	protected BUTree<SQualifiedName> parseQualifiedName() throws ParseException {
		BUTree<SNodeOption> qualifier = none();
		BUTree<SQualifiedName> ret = null;
		BUTree<SName> name;
		int __token;
		run();
		pushCallStack(QUALIFIED_NAME_1);
		name = parseName();
		popCallStack();
		ret = dress(SQualifiedName.make(qualifier, name));
		__token = getToken(0).kind;
		while (predict(QUALIFIED_NAME_2) == 1) {
			lateRun();
			consume(TokenType.DOT);
			qualifier = optionOf(ret);
			pushCallStack(QUALIFIED_NAME_2_1_2);
			name = parseName();
			popCallStack();
			ret = dress(SQualifiedName.make(qualifier, name));
			__token = getToken(0).kind;
		}
		return ret;
	}

	/* sequence(
		choice(
			nonTerminal(name, NodeVar)
			sequence(
				action({ run(); })
				terminal(id, IDENTIFIER)
				action({ name = dress(SName.make(id.image)); })
			)
		)
		action({ return name; })
	) */
	protected BUTree<SName> parseName() throws ParseException {
		Token id;
		BUTree<SName> name;
		int __token;
		__token = getToken(0).kind;
		if (__token == TokenType.NODE_VARIABLE) {
			pushCallStack(NAME_1_1);
			name = parseNodeVar();
			popCallStack();
		} else if (__token == TokenType.IDENTIFIER) {
			run();
			id = consume(TokenType.IDENTIFIER);
			name = dress(SName.make(id.image));
		} else
			throw produceParseException(TokenType.NODE_VARIABLE, TokenType.IDENTIFIER);
		return name;
	}

	/* sequence(
		choice(
			nonTerminal(ret, AssignmentExpression)
			nonTerminal(ret, LambdaExpression)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parseExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		int __token;
		switch (predict(EXPRESSION_1)) {
			case 1:
				pushCallStack(EXPRESSION_1_1);
				ret = parseAssignmentExpression();
				popCallStack();
				break;
			case 2:
				pushCallStack(EXPRESSION_1_2);
				ret = parseLambdaExpression();
				popCallStack();
				break;
			default:
				throw produceParseException(TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.DOUBLE, TokenType.FALSE, TokenType.FLOAT, TokenType.INT, TokenType.LONG, TokenType.NEW, TokenType.NULL, TokenType.SHORT, TokenType.SUPER, TokenType.THIS, TokenType.TRUE, TokenType.VOID, TokenType.LONG_LITERAL, TokenType.INTEGER_LITERAL, TokenType.FLOAT_LITERAL, TokenType.DOUBLE_LITERAL, TokenType.CHARACTER_LITERAL, TokenType.STRING_LITERAL, TokenType.LPAREN, TokenType.LT, TokenType.BANG, TokenType.TILDE, TokenType.INCR, TokenType.DECR, TokenType.PLUS, TokenType.MINUS, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER);
		}
		return ret;
	}

	/* sequence(
		nonTerminal(ret, ConditionalExpression)
		zeroOrOne(
			sequence(
				action({ lateRun(); })
				nonTerminal(op, AssignmentOperator)
				nonTerminal(expr, Expression)
				action({ ret = dress(SAssignExpr.make(ret, op, expr)); })
			)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parseAssignmentExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		AssignOp op;
		BUTree<? extends SExpr> expr;
		int __token;
		pushCallStack(ASSIGNMENT_EXPRESSION_1);
		ret = parseConditionalExpression();
		popCallStack();
		__token = getToken(0).kind;
		if ((__token - 90 & ~63) == 0 && (1L << __token - 90 & (1L << TokenType.ASSIGN - 90 | 1L << TokenType.PLUSASSIGN - 90 | 1L << TokenType.MINUSASSIGN - 90 | 1L << TokenType.STARASSIGN - 90 | 1L << TokenType.SLASHASSIGN - 90 | 1L << TokenType.ANDASSIGN - 90 | 1L << TokenType.ORASSIGN - 90 | 1L << TokenType.XORASSIGN - 90 | 1L << TokenType.REMASSIGN - 90 | 1L << TokenType.LSHIFTASSIGN - 90 | 1L << TokenType.RSIGNEDSHIFTASSIGN - 90 | 1L << TokenType.RUNSIGNEDSHIFTASSIGN - 90)) != 0) {
			lateRun();
			pushCallStack(ASSIGNMENT_EXPRESSION_2_1_1);
			op = parseAssignmentOperator();
			popCallStack();
			pushCallStack(ASSIGNMENT_EXPRESSION_2_1_2);
			expr = parseExpression();
			popCallStack();
			ret = dress(SAssignExpr.make(ret, op, expr));
		}
		return ret;
	}

	/* sequence(
		choice(
			sequence(
				action({ run(); })
				terminal(LPAREN)
				action({ run(); })
				nonTerminal(annotations, Annotations)
				nonTerminal(type, ReferenceType)
				nonTerminal(type, ReferenceCastTypeRest)
				terminal(RPAREN)
				nonTerminal(ret, LambdaExpression)
				action({ ret = dress(SCastExpr.make(type, ret)); })
			)
			nonTerminal(ret, LambdaExpressionWithoutCast)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parseLambdaExpression() throws ParseException {
		BUTree<SNodeList> annotations;
		BUTree<? extends SType> type;
		BUTree<? extends SExpr> ret;
		int __token;
		switch (predict(LAMBDA_EXPRESSION_1)) {
			case 1:
				run();
				consume(TokenType.LPAREN);
				run();
				pushCallStack(LAMBDA_EXPRESSION_1_1_2);
				annotations = parseAnnotations();
				popCallStack();
				pushCallStack(LAMBDA_EXPRESSION_1_1_3);
				type = parseReferenceType(annotations);
				popCallStack();
				pushCallStack(LAMBDA_EXPRESSION_1_1_4);
				type = parseReferenceCastTypeRest(type);
				popCallStack();
				consume(TokenType.RPAREN);
				pushCallStack(LAMBDA_EXPRESSION_1_1_6);
				ret = parseLambdaExpression();
				popCallStack();
				ret = dress(SCastExpr.make(type, ret));
				break;
			case 2:
				pushCallStack(LAMBDA_EXPRESSION_1_2);
				ret = parseLambdaExpressionWithoutCast();
				popCallStack();
				break;
			default:
				throw produceParseException(TokenType.LPAREN, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER);
		}
		return ret;
	}

	/* sequence(
		action({ run(); })
		choice(
			sequence(
				nonTerminal(name, Name)
				terminal(ARROW)
				nonTerminal(ret, LambdaBody)
			)
			sequence(
				terminal(LPAREN)
				terminal(RPAREN)
				terminal(ARROW)
				nonTerminal(ret, LambdaBody)
			)
			sequence(
				terminal(LPAREN)
				nonTerminal(params, InferredFormalParameterList)
				terminal(RPAREN)
				terminal(ARROW)
				nonTerminal(ret, LambdaBody)
			)
			sequence(
				terminal(LPAREN)
				nonTerminal(params, FormalParameterList)
				terminal(RPAREN)
				terminal(ARROW)
				nonTerminal(ret, LambdaBody)
			)
		)
		action({ return ret; })
	) */
	protected BUTree<SLambdaExpr> parseLambdaExpressionWithoutCast() throws ParseException {
		BUTree<SLambdaExpr> ret;
		BUTree<SName> name;
		BUTree<SNodeList> params;
		int __token;
		run();
		switch (predict(LAMBDA_EXPRESSION_WITHOUT_CAST_1)) {
			case 1:
				pushCallStack(LAMBDA_EXPRESSION_WITHOUT_CAST_1_1_1);
				name = parseName();
				popCallStack();
				consume(TokenType.ARROW);
				pushCallStack(LAMBDA_EXPRESSION_WITHOUT_CAST_1_1_3);
				ret = parseLambdaBody(singletonList(makeFormalParameter(name)), false);
				popCallStack();
				break;
			case 2:
				consume(TokenType.LPAREN);
				consume(TokenType.RPAREN);
				consume(TokenType.ARROW);
				pushCallStack(LAMBDA_EXPRESSION_WITHOUT_CAST_1_2_4);
				ret = parseLambdaBody(emptyList(), true);
				popCallStack();
				break;
			case 3:
				consume(TokenType.LPAREN);
				pushCallStack(LAMBDA_EXPRESSION_WITHOUT_CAST_1_3_2);
				params = parseInferredFormalParameterList();
				popCallStack();
				consume(TokenType.RPAREN);
				consume(TokenType.ARROW);
				pushCallStack(LAMBDA_EXPRESSION_WITHOUT_CAST_1_3_5);
				ret = parseLambdaBody(params, true);
				popCallStack();
				break;
			case 4:
				consume(TokenType.LPAREN);
				pushCallStack(LAMBDA_EXPRESSION_WITHOUT_CAST_1_4_2);
				params = parseFormalParameterList();
				popCallStack();
				consume(TokenType.RPAREN);
				consume(TokenType.ARROW);
				pushCallStack(LAMBDA_EXPRESSION_WITHOUT_CAST_1_4_5);
				ret = parseLambdaBody(params, true);
				popCallStack();
				break;
			default:
				throw produceParseException(TokenType.LPAREN, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER);
		}
		return ret;
	}

	/* sequence(
		choice(
			sequence(
				nonTerminal(expr, Expression)
				action({ ret = dress(SLambdaExpr.make(parameters, parenthesis, left(expr))); })
			)
			sequence(
				nonTerminal(block, Block)
				action({ ret = dress(SLambdaExpr.make(parameters, parenthesis, right(block))); })
			)
		)
		action({ return ret; })
	) */
	protected BUTree<SLambdaExpr> parseLambdaBody(BUTree<SNodeList> parameters, boolean parenthesis) throws ParseException {
		BUTree<SBlockStmt> block;
		BUTree<? extends SExpr> expr;
		BUTree<SLambdaExpr> ret;
		int __token;
		__token = getToken(0).kind;
		if (((__token - 11 & ~63) == 0 && (1L << __token - 11 & (1L << TokenType.BOOLEAN - 11 | 1L << TokenType.BYTE - 11 | 1L << TokenType.CHAR - 11 | 1L << TokenType.DOUBLE - 11 | 1L << TokenType.FALSE - 11 | 1L << TokenType.FLOAT - 11 | 1L << TokenType.INT - 11 | 1L << TokenType.LONG - 11 | 1L << TokenType.NEW - 11 | 1L << TokenType.NULL - 11 | 1L << TokenType.SHORT - 11 | 1L << TokenType.SUPER - 11 | 1L << TokenType.THIS - 11 | 1L << TokenType.TRUE - 11 | 1L << TokenType.VOID - 11 | 1L << TokenType.LONG_LITERAL - 11 | 1L << TokenType.INTEGER_LITERAL - 11 | 1L << TokenType.FLOAT_LITERAL - 11 | 1L << TokenType.DOUBLE_LITERAL - 11)) != 0) || ((__token - 78 & ~63) == 0 && (1L << __token - 78 & (1L << TokenType.CHARACTER_LITERAL - 78 | 1L << TokenType.STRING_LITERAL - 78 | 1L << TokenType.LPAREN - 78 | 1L << TokenType.LT - 78 | 1L << TokenType.BANG - 78 | 1L << TokenType.TILDE - 78 | 1L << TokenType.INCR - 78 | 1L << TokenType.DECR - 78 | 1L << TokenType.PLUS - 78 | 1L << TokenType.MINUS - 78 | 1L << TokenType.NODE_VARIABLE - 78 | 1L << TokenType.IDENTIFIER - 78)) != 0)) {
			pushCallStack(LAMBDA_BODY_1_1_1);
			expr = parseExpression();
			popCallStack();
			ret = dress(SLambdaExpr.make(parameters, parenthesis, left(expr)));
		} else if (__token == TokenType.LBRACE) {
			pushCallStack(LAMBDA_BODY_1_2_1);
			block = parseBlock();
			popCallStack();
			ret = dress(SLambdaExpr.make(parameters, parenthesis, right(block)));
		} else
			throw produceParseException(TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.DOUBLE, TokenType.FALSE, TokenType.FLOAT, TokenType.INT, TokenType.LONG, TokenType.NEW, TokenType.NULL, TokenType.SHORT, TokenType.SUPER, TokenType.THIS, TokenType.TRUE, TokenType.VOID, TokenType.LONG_LITERAL, TokenType.INTEGER_LITERAL, TokenType.FLOAT_LITERAL, TokenType.DOUBLE_LITERAL, TokenType.CHARACTER_LITERAL, TokenType.STRING_LITERAL, TokenType.LPAREN, TokenType.LBRACE, TokenType.LT, TokenType.BANG, TokenType.TILDE, TokenType.INCR, TokenType.DECR, TokenType.PLUS, TokenType.MINUS, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER);
		return ret;
	}

	/* sequence(
		nonTerminal(param, InferredFormalParameter)
		action({ ret = append(ret, param); })
		zeroOrMore(
			sequence(
				terminal(COMMA)
				nonTerminal(param, InferredFormalParameter)
				action({ ret = append(ret, param); })
			)
		)
		action({ return ret; })
	) */
	protected BUTree<SNodeList> parseInferredFormalParameterList() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<SFormalParameter> param;
		int __token;
		pushCallStack(INFERRED_FORMAL_PARAMETER_LIST_1);
		param = parseInferredFormalParameter();
		popCallStack();
		ret = append(ret, param);
		__token = getToken(0).kind;
		while (__token == TokenType.COMMA) {
			consume(TokenType.COMMA);
			pushCallStack(INFERRED_FORMAL_PARAMETER_LIST_2_1_2);
			param = parseInferredFormalParameter();
			popCallStack();
			ret = append(ret, param);
			__token = getToken(0).kind;
		}
		return ret;
	}

	/* sequence(
		nonTerminal(name, Name)
		action({ return makeFormalParameter(name); })
	) */
	protected BUTree<SFormalParameter> parseInferredFormalParameter() throws ParseException {
		BUTree<SName> name;
		int __token;
		pushCallStack(INFERRED_FORMAL_PARAMETER_1);
		name = parseName();
		popCallStack();
		return makeFormalParameter(name);
	}

	/* sequence(
		choice(
			sequence(
				terminal(ASSIGN)
				action({ ret = AssignOp.Normal; })
			)
			sequence(
				terminal(STARASSIGN)
				action({ ret = AssignOp.Times; })
			)
			sequence(
				terminal(SLASHASSIGN)
				action({ ret = AssignOp.Divide; })
			)
			sequence(
				terminal(REMASSIGN)
				action({ ret = AssignOp.Remainder; })
			)
			sequence(
				terminal(PLUSASSIGN)
				action({ ret = AssignOp.Plus; })
			)
			sequence(
				terminal(MINUSASSIGN)
				action({ ret = AssignOp.Minus; })
			)
			sequence(
				terminal(LSHIFTASSIGN)
				action({ ret = AssignOp.LeftShift; })
			)
			sequence(
				terminal(RSIGNEDSHIFTASSIGN)
				action({ ret = AssignOp.RightSignedShift; })
			)
			sequence(
				terminal(RUNSIGNEDSHIFTASSIGN)
				action({ ret = AssignOp.RightUnsignedShift; })
			)
			sequence(
				terminal(ANDASSIGN)
				action({ ret = AssignOp.And; })
			)
			sequence(
				terminal(XORASSIGN)
				action({ ret = AssignOp.XOr; })
			)
			sequence(
				terminal(ORASSIGN)
				action({ ret = AssignOp.Or; })
			)
		)
		action({ return ret; })
	) */
	protected AssignOp parseAssignmentOperator() throws ParseException {
		AssignOp ret;
		int __token;
		__token = getToken(0).kind;
		if (__token == TokenType.ASSIGN) {
			consume(TokenType.ASSIGN);
			ret = AssignOp.Normal;
		} else if (__token == TokenType.STARASSIGN) {
			consume(TokenType.STARASSIGN);
			ret = AssignOp.Times;
		} else if (__token == TokenType.SLASHASSIGN) {
			consume(TokenType.SLASHASSIGN);
			ret = AssignOp.Divide;
		} else if (__token == TokenType.REMASSIGN) {
			consume(TokenType.REMASSIGN);
			ret = AssignOp.Remainder;
		} else if (__token == TokenType.PLUSASSIGN) {
			consume(TokenType.PLUSASSIGN);
			ret = AssignOp.Plus;
		} else if (__token == TokenType.MINUSASSIGN) {
			consume(TokenType.MINUSASSIGN);
			ret = AssignOp.Minus;
		} else if (__token == TokenType.LSHIFTASSIGN) {
			consume(TokenType.LSHIFTASSIGN);
			ret = AssignOp.LeftShift;
		} else if (__token == TokenType.RSIGNEDSHIFTASSIGN) {
			consume(TokenType.RSIGNEDSHIFTASSIGN);
			ret = AssignOp.RightSignedShift;
		} else if (__token == TokenType.RUNSIGNEDSHIFTASSIGN) {
			consume(TokenType.RUNSIGNEDSHIFTASSIGN);
			ret = AssignOp.RightUnsignedShift;
		} else if (__token == TokenType.ANDASSIGN) {
			consume(TokenType.ANDASSIGN);
			ret = AssignOp.And;
		} else if (__token == TokenType.XORASSIGN) {
			consume(TokenType.XORASSIGN);
			ret = AssignOp.XOr;
		} else if (__token == TokenType.ORASSIGN) {
			consume(TokenType.ORASSIGN);
			ret = AssignOp.Or;
		} else
			throw produceParseException(TokenType.ASSIGN, TokenType.PLUSASSIGN, TokenType.MINUSASSIGN, TokenType.STARASSIGN, TokenType.SLASHASSIGN, TokenType.ANDASSIGN, TokenType.ORASSIGN, TokenType.XORASSIGN, TokenType.REMASSIGN, TokenType.LSHIFTASSIGN, TokenType.RSIGNEDSHIFTASSIGN, TokenType.RUNSIGNEDSHIFTASSIGN);
		return ret;
	}

	/* sequence(
		nonTerminal(ret, ConditionalOrExpression)
		zeroOrOne(
			sequence(
				action({ lateRun(); })
				terminal(HOOK)
				nonTerminal(left, Expression)
				terminal(COLON)
				choice(
					nonTerminal(right, ConditionalExpression)
					nonTerminal(right, LambdaExpression)
				)
				action({ ret = dress(SConditionalExpr.make(ret, left, right)); })
			)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parseConditionalExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> left;
		BUTree<? extends SExpr> right;
		int __token;
		pushCallStack(CONDITIONAL_EXPRESSION_1);
		ret = parseConditionalOrExpression();
		popCallStack();
		__token = getToken(0).kind;
		if (__token == TokenType.HOOK) {
			lateRun();
			consume(TokenType.HOOK);
			pushCallStack(CONDITIONAL_EXPRESSION_2_1_2);
			left = parseExpression();
			popCallStack();
			consume(TokenType.COLON);
			switch (predict(CONDITIONAL_EXPRESSION_2_1_4)) {
				case 1:
					pushCallStack(CONDITIONAL_EXPRESSION_2_1_4_1);
					right = parseConditionalExpression();
					popCallStack();
					break;
				case 2:
					pushCallStack(CONDITIONAL_EXPRESSION_2_1_4_2);
					right = parseLambdaExpression();
					popCallStack();
					break;
				default:
					throw produceParseException(TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.DOUBLE, TokenType.FALSE, TokenType.FLOAT, TokenType.INT, TokenType.LONG, TokenType.NEW, TokenType.NULL, TokenType.SHORT, TokenType.SUPER, TokenType.THIS, TokenType.TRUE, TokenType.VOID, TokenType.LONG_LITERAL, TokenType.INTEGER_LITERAL, TokenType.FLOAT_LITERAL, TokenType.DOUBLE_LITERAL, TokenType.CHARACTER_LITERAL, TokenType.STRING_LITERAL, TokenType.LPAREN, TokenType.LT, TokenType.BANG, TokenType.TILDE, TokenType.INCR, TokenType.DECR, TokenType.PLUS, TokenType.MINUS, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER);
			}
			ret = dress(SConditionalExpr.make(ret, left, right));
		}
		return ret;
	}

	/* sequence(
		nonTerminal(ret, ConditionalAndExpression)
		zeroOrMore(
			sequence(
				action({ lateRun(); })
				terminal(SC_OR)
				nonTerminal(right, ConditionalAndExpression)
				action({ ret = dress(SBinaryExpr.make(ret, BinaryOp.Or, right)); })
			)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parseConditionalOrExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		int __token;
		pushCallStack(CONDITIONAL_OR_EXPRESSION_1);
		ret = parseConditionalAndExpression();
		popCallStack();
		__token = getToken(0).kind;
		while (__token == TokenType.SC_OR) {
			lateRun();
			consume(TokenType.SC_OR);
			pushCallStack(CONDITIONAL_OR_EXPRESSION_2_1_2);
			right = parseConditionalAndExpression();
			popCallStack();
			ret = dress(SBinaryExpr.make(ret, BinaryOp.Or, right));
			__token = getToken(0).kind;
		}
		return ret;
	}

	/* sequence(
		nonTerminal(ret, InclusiveOrExpression)
		zeroOrMore(
			sequence(
				action({ lateRun(); })
				terminal(SC_AND)
				nonTerminal(right, InclusiveOrExpression)
				action({ ret = dress(SBinaryExpr.make(ret, BinaryOp.And, right)); })
			)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parseConditionalAndExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		int __token;
		pushCallStack(CONDITIONAL_AND_EXPRESSION_1);
		ret = parseInclusiveOrExpression();
		popCallStack();
		__token = getToken(0).kind;
		while (__token == TokenType.SC_AND) {
			lateRun();
			consume(TokenType.SC_AND);
			pushCallStack(CONDITIONAL_AND_EXPRESSION_2_1_2);
			right = parseInclusiveOrExpression();
			popCallStack();
			ret = dress(SBinaryExpr.make(ret, BinaryOp.And, right));
			__token = getToken(0).kind;
		}
		return ret;
	}

	/* sequence(
		nonTerminal(ret, ExclusiveOrExpression)
		zeroOrMore(
			sequence(
				action({ lateRun(); })
				terminal(BIT_OR)
				nonTerminal(right, ExclusiveOrExpression)
				action({ ret = dress(SBinaryExpr.make(ret, BinaryOp.BinOr, right)); })
			)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parseInclusiveOrExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		int __token;
		pushCallStack(INCLUSIVE_OR_EXPRESSION_1);
		ret = parseExclusiveOrExpression();
		popCallStack();
		__token = getToken(0).kind;
		while (__token == TokenType.BIT_OR) {
			lateRun();
			consume(TokenType.BIT_OR);
			pushCallStack(INCLUSIVE_OR_EXPRESSION_2_1_2);
			right = parseExclusiveOrExpression();
			popCallStack();
			ret = dress(SBinaryExpr.make(ret, BinaryOp.BinOr, right));
			__token = getToken(0).kind;
		}
		return ret;
	}

	/* sequence(
		nonTerminal(ret, AndExpression)
		zeroOrMore(
			sequence(
				action({ lateRun(); })
				terminal(XOR)
				nonTerminal(right, AndExpression)
				action({ ret = dress(SBinaryExpr.make(ret, BinaryOp.XOr, right)); })
			)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parseExclusiveOrExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		int __token;
		pushCallStack(EXCLUSIVE_OR_EXPRESSION_1);
		ret = parseAndExpression();
		popCallStack();
		__token = getToken(0).kind;
		while (__token == TokenType.XOR) {
			lateRun();
			consume(TokenType.XOR);
			pushCallStack(EXCLUSIVE_OR_EXPRESSION_2_1_2);
			right = parseAndExpression();
			popCallStack();
			ret = dress(SBinaryExpr.make(ret, BinaryOp.XOr, right));
			__token = getToken(0).kind;
		}
		return ret;
	}

	/* sequence(
		nonTerminal(ret, EqualityExpression)
		zeroOrMore(
			sequence(
				action({ lateRun(); })
				terminal(BIT_AND)
				nonTerminal(right, EqualityExpression)
				action({ ret = dress(SBinaryExpr.make(ret, BinaryOp.BinAnd, right)); })
			)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parseAndExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		int __token;
		pushCallStack(AND_EXPRESSION_1);
		ret = parseEqualityExpression();
		popCallStack();
		__token = getToken(0).kind;
		while (__token == TokenType.BIT_AND) {
			lateRun();
			consume(TokenType.BIT_AND);
			pushCallStack(AND_EXPRESSION_2_1_2);
			right = parseEqualityExpression();
			popCallStack();
			ret = dress(SBinaryExpr.make(ret, BinaryOp.BinAnd, right));
			__token = getToken(0).kind;
		}
		return ret;
	}

	/* sequence(
		nonTerminal(ret, InstanceOfExpression)
		zeroOrMore(
			sequence(
				action({ lateRun(); })
				choice(
					sequence(
						terminal(EQ)
						action({ op = BinaryOp.Equal; })
					)
					sequence(
						terminal(NE)
						action({ op = BinaryOp.NotEqual; })
					)
				)
				nonTerminal(right, InstanceOfExpression)
				action({ ret = dress(SBinaryExpr.make(ret, op, right)); })
			)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parseEqualityExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		BinaryOp op;
		int __token;
		pushCallStack(EQUALITY_EXPRESSION_1);
		ret = parseInstanceOfExpression();
		popCallStack();
		__token = getToken(0).kind;
		while (__token == TokenType.EQ || __token == TokenType.NE) {
			lateRun();
			__token = getToken(0).kind;
			if (__token == TokenType.EQ) {
				consume(TokenType.EQ);
				op = BinaryOp.Equal;
			} else if (__token == TokenType.NE) {
				consume(TokenType.NE);
				op = BinaryOp.NotEqual;
			} else
				throw produceParseException(TokenType.EQ, TokenType.NE);
			pushCallStack(EQUALITY_EXPRESSION_2_1_2);
			right = parseInstanceOfExpression();
			popCallStack();
			ret = dress(SBinaryExpr.make(ret, op, right));
			__token = getToken(0).kind;
		}
		return ret;
	}

	/* sequence(
		nonTerminal(ret, RelationalExpression)
		zeroOrOne(
			sequence(
				action({ lateRun(); })
				terminal(INSTANCEOF)
				action({ run(); })
				nonTerminal(annotations, Annotations)
				nonTerminal(type, Type)
				action({ ret = dress(SInstanceOfExpr.make(ret, type)); })
			)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parseInstanceOfExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<SNodeList> annotations;
		BUTree<? extends SType> type;
		int __token;
		pushCallStack(INSTANCE_OF_EXPRESSION_1);
		ret = parseRelationalExpression();
		popCallStack();
		__token = getToken(0).kind;
		if (__token == TokenType.INSTANCEOF) {
			lateRun();
			consume(TokenType.INSTANCEOF);
			run();
			pushCallStack(INSTANCE_OF_EXPRESSION_2_1_2);
			annotations = parseAnnotations();
			popCallStack();
			pushCallStack(INSTANCE_OF_EXPRESSION_2_1_3);
			type = parseType(annotations);
			popCallStack();
			ret = dress(SInstanceOfExpr.make(ret, type));
		}
		return ret;
	}

	/* sequence(
		nonTerminal(ret, ShiftExpression)
		zeroOrMore(
			sequence(
				action({ lateRun(); })
				choice(
					sequence(
						terminal(LT)
						action({ op = BinaryOp.Less; })
					)
					sequence(
						terminal(GT)
						action({ op = BinaryOp.Greater; })
					)
					sequence(
						terminal(LE)
						action({ op = BinaryOp.LessOrEqual; })
					)
					sequence(
						terminal(GE)
						action({ op = BinaryOp.GreaterOrEqual; })
					)
				)
				nonTerminal(right, ShiftExpression)
				action({ ret = dress(SBinaryExpr.make(ret, op, right)); })
			)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parseRelationalExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		BinaryOp op;
		int __token;
		pushCallStack(RELATIONAL_EXPRESSION_1);
		ret = parseShiftExpression();
		popCallStack();
		__token = getToken(0).kind;
		while ((__token - 91 & ~63) == 0 && (1L << __token - 91 & (1L << TokenType.LT - 91 | 1L << TokenType.LE - 91 | 1L << TokenType.GE - 91 | 1L << TokenType.GT - 91)) != 0) {
			lateRun();
			__token = getToken(0).kind;
			if (__token == TokenType.LT) {
				consume(TokenType.LT);
				op = BinaryOp.Less;
			} else if (__token == TokenType.GT) {
				consume(TokenType.GT);
				op = BinaryOp.Greater;
			} else if (__token == TokenType.LE) {
				consume(TokenType.LE);
				op = BinaryOp.LessOrEqual;
			} else if (__token == TokenType.GE) {
				consume(TokenType.GE);
				op = BinaryOp.GreaterOrEqual;
			} else
				throw produceParseException(TokenType.LT, TokenType.LE, TokenType.GE, TokenType.GT);
			pushCallStack(RELATIONAL_EXPRESSION_2_1_2);
			right = parseShiftExpression();
			popCallStack();
			ret = dress(SBinaryExpr.make(ret, op, right));
			__token = getToken(0).kind;
		}
		return ret;
	}

	/* sequence(
		nonTerminal(ret, AdditiveExpression)
		zeroOrMore(
			sequence(
				action({ lateRun(); })
				choice(
					sequence(
						terminal(LSHIFT)
						action({ op = BinaryOp.LeftShift; })
					)
					sequence(
						terminal(GT)
						terminal(GT)
						terminal(GT)
						action({ popNewWhitespaces(2); })
						action({ op = BinaryOp.RightUnsignedShift; })
					)
					sequence(
						terminal(GT)
						terminal(GT)
						action({ popNewWhitespaces(1); })
						action({ op = BinaryOp.RightSignedShift; })
					)
				)
				nonTerminal(right, AdditiveExpression)
				action({ ret = dress(SBinaryExpr.make(ret, op, right)); })
			)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parseShiftExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		BinaryOp op;
		int __token;
		pushCallStack(SHIFT_EXPRESSION_1);
		ret = parseAdditiveExpression();
		popCallStack();
		__token = getToken(0).kind;
		while (predict(SHIFT_EXPRESSION_2) == 1) {
			lateRun();
			switch (predict(SHIFT_EXPRESSION_2_1_1)) {
				case 1:
					consume(TokenType.LSHIFT);
					op = BinaryOp.LeftShift;
					break;
				case 2:
					consume(TokenType.GT);
					consume(TokenType.GT);
					consume(TokenType.GT);
					popNewWhitespaces(2);
					op = BinaryOp.RightUnsignedShift;
					break;
				case 3:
					consume(TokenType.GT);
					consume(TokenType.GT);
					popNewWhitespaces(1);
					op = BinaryOp.RightSignedShift;
					break;
				default:
					throw produceParseException(TokenType.LSHIFT, TokenType.GT);
			}
			pushCallStack(SHIFT_EXPRESSION_2_1_2);
			right = parseAdditiveExpression();
			popCallStack();
			ret = dress(SBinaryExpr.make(ret, op, right));
			__token = getToken(0).kind;
		}
		return ret;
	}

	/* sequence(
		nonTerminal(ret, MultiplicativeExpression)
		zeroOrMore(
			sequence(
				action({ lateRun(); })
				choice(
					sequence(
						terminal(PLUS)
						action({ op = BinaryOp.Plus; })
					)
					sequence(
						terminal(MINUS)
						action({ op = BinaryOp.Minus; })
					)
				)
				nonTerminal(right, MultiplicativeExpression)
				action({ ret = dress(SBinaryExpr.make(ret, op, right)); })
			)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parseAdditiveExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		BinaryOp op;
		int __token;
		pushCallStack(ADDITIVE_EXPRESSION_1);
		ret = parseMultiplicativeExpression();
		popCallStack();
		__token = getToken(0).kind;
		while (__token == TokenType.PLUS || __token == TokenType.MINUS) {
			lateRun();
			__token = getToken(0).kind;
			if (__token == TokenType.PLUS) {
				consume(TokenType.PLUS);
				op = BinaryOp.Plus;
			} else if (__token == TokenType.MINUS) {
				consume(TokenType.MINUS);
				op = BinaryOp.Minus;
			} else
				throw produceParseException(TokenType.PLUS, TokenType.MINUS);
			pushCallStack(ADDITIVE_EXPRESSION_2_1_2);
			right = parseMultiplicativeExpression();
			popCallStack();
			ret = dress(SBinaryExpr.make(ret, op, right));
			__token = getToken(0).kind;
		}
		return ret;
	}

	/* sequence(
		nonTerminal(ret, UnaryExpression)
		zeroOrMore(
			sequence(
				action({ lateRun(); })
				choice(
					sequence(
						terminal(STAR)
						action({ op = BinaryOp.Times; })
					)
					sequence(
						terminal(SLASH)
						action({ op = BinaryOp.Divide; })
					)
					sequence(
						terminal(REM)
						action({ op = BinaryOp.Remainder; })
					)
				)
				nonTerminal(right, UnaryExpression)
				action({ ret = dress(SBinaryExpr.make(ret, op, right)); })
			)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parseMultiplicativeExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		BinaryOp op;
		int __token;
		pushCallStack(MULTIPLICATIVE_EXPRESSION_1);
		ret = parseUnaryExpression();
		popCallStack();
		__token = getToken(0).kind;
		while ((__token - 106 & ~63) == 0 && (1L << __token - 106 & (1L << TokenType.STAR - 106 | 1L << TokenType.SLASH - 106 | 1L << TokenType.REM - 106)) != 0) {
			lateRun();
			__token = getToken(0).kind;
			if (__token == TokenType.STAR) {
				consume(TokenType.STAR);
				op = BinaryOp.Times;
			} else if (__token == TokenType.SLASH) {
				consume(TokenType.SLASH);
				op = BinaryOp.Divide;
			} else if (__token == TokenType.REM) {
				consume(TokenType.REM);
				op = BinaryOp.Remainder;
			} else
				throw produceParseException(TokenType.STAR, TokenType.SLASH, TokenType.REM);
			pushCallStack(MULTIPLICATIVE_EXPRESSION_2_1_2);
			right = parseUnaryExpression();
			popCallStack();
			ret = dress(SBinaryExpr.make(ret, op, right));
			__token = getToken(0).kind;
		}
		return ret;
	}

	/* sequence(
		choice(
			nonTerminal(ret, PrefixExpression)
			sequence(
				action({ run(); })
				choice(
					sequence(
						terminal(PLUS)
						action({ op = UnaryOp.Positive; })
					)
					sequence(
						terminal(MINUS)
						action({ op = UnaryOp.Negative; })
					)
				)
				nonTerminal(ret, UnaryExpression)
				action({ ret = dress(SUnaryExpr.make(op, ret)); })
			)
			nonTerminal(ret, UnaryExpressionNotPlusMinus)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parseUnaryExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		UnaryOp op;
		int __token;
		__token = getToken(0).kind;
		if (__token == TokenType.INCR || __token == TokenType.DECR) {
			pushCallStack(UNARY_EXPRESSION_1_1);
			ret = parsePrefixExpression();
			popCallStack();
		} else if (__token == TokenType.PLUS || __token == TokenType.MINUS) {
			run();
			__token = getToken(0).kind;
			if (__token == TokenType.PLUS) {
				consume(TokenType.PLUS);
				op = UnaryOp.Positive;
			} else if (__token == TokenType.MINUS) {
				consume(TokenType.MINUS);
				op = UnaryOp.Negative;
			} else
				throw produceParseException(TokenType.PLUS, TokenType.MINUS);
			pushCallStack(UNARY_EXPRESSION_1_2_2);
			ret = parseUnaryExpression();
			popCallStack();
			ret = dress(SUnaryExpr.make(op, ret));
		} else if (((__token - 11 & ~63) == 0 && (1L << __token - 11 & (1L << TokenType.BOOLEAN - 11 | 1L << TokenType.BYTE - 11 | 1L << TokenType.CHAR - 11 | 1L << TokenType.DOUBLE - 11 | 1L << TokenType.FALSE - 11 | 1L << TokenType.FLOAT - 11 | 1L << TokenType.INT - 11 | 1L << TokenType.LONG - 11 | 1L << TokenType.NEW - 11 | 1L << TokenType.NULL - 11 | 1L << TokenType.SHORT - 11 | 1L << TokenType.SUPER - 11 | 1L << TokenType.THIS - 11 | 1L << TokenType.TRUE - 11 | 1L << TokenType.VOID - 11 | 1L << TokenType.LONG_LITERAL - 11 | 1L << TokenType.INTEGER_LITERAL - 11 | 1L << TokenType.FLOAT_LITERAL - 11 | 1L << TokenType.DOUBLE_LITERAL - 11)) != 0) || ((__token - 78 & ~63) == 0 && (1L << __token - 78 & (1L << TokenType.CHARACTER_LITERAL - 78 | 1L << TokenType.STRING_LITERAL - 78 | 1L << TokenType.LPAREN - 78 | 1L << TokenType.LT - 78 | 1L << TokenType.BANG - 78 | 1L << TokenType.TILDE - 78 | 1L << TokenType.NODE_VARIABLE - 78 | 1L << TokenType.IDENTIFIER - 78)) != 0)) {
			pushCallStack(UNARY_EXPRESSION_1_3);
			ret = parseUnaryExpressionNotPlusMinus();
			popCallStack();
		} else
			throw produceParseException(TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.DOUBLE, TokenType.FALSE, TokenType.FLOAT, TokenType.INT, TokenType.LONG, TokenType.NEW, TokenType.NULL, TokenType.SHORT, TokenType.SUPER, TokenType.THIS, TokenType.TRUE, TokenType.VOID, TokenType.LONG_LITERAL, TokenType.INTEGER_LITERAL, TokenType.FLOAT_LITERAL, TokenType.DOUBLE_LITERAL, TokenType.CHARACTER_LITERAL, TokenType.STRING_LITERAL, TokenType.LPAREN, TokenType.LT, TokenType.BANG, TokenType.TILDE, TokenType.INCR, TokenType.DECR, TokenType.PLUS, TokenType.MINUS, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER);
		return ret;
	}

	/* sequence(
		action({ run(); })
		choice(
			sequence(
				terminal(INCR)
				action({ op = UnaryOp.PreIncrement; })
			)
			sequence(
				terminal(DECR)
				action({ op = UnaryOp.PreDecrement; })
			)
		)
		nonTerminal(ret, UnaryExpression)
		action({ return dress(SUnaryExpr.make(op, ret)); })
	) */
	protected BUTree<? extends SExpr> parsePrefixExpression() throws ParseException {
		UnaryOp op;
		BUTree<? extends SExpr> ret;
		int __token;
		run();
		__token = getToken(0).kind;
		if (__token == TokenType.INCR) {
			consume(TokenType.INCR);
			op = UnaryOp.PreIncrement;
		} else if (__token == TokenType.DECR) {
			consume(TokenType.DECR);
			op = UnaryOp.PreDecrement;
		} else
			throw produceParseException(TokenType.INCR, TokenType.DECR);
		pushCallStack(PREFIX_EXPRESSION_2);
		ret = parseUnaryExpression();
		popCallStack();
		return dress(SUnaryExpr.make(op, ret));
	}

	/* sequence(
		choice(
			sequence(
				action({ run(); })
				choice(
					sequence(
						terminal(TILDE)
						action({ op = UnaryOp.Inverse; })
					)
					sequence(
						terminal(BANG)
						action({ op = UnaryOp.Not; })
					)
				)
				nonTerminal(ret, UnaryExpression)
				action({ ret = dress(SUnaryExpr.make(op, ret)); })
			)
			nonTerminal(ret, CastExpression)
			nonTerminal(ret, PostfixExpression)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parseUnaryExpressionNotPlusMinus() throws ParseException {
		BUTree<? extends SExpr> ret;
		UnaryOp op;
		int __token;
		switch (predict(UNARY_EXPRESSION_NOT_PLUS_MINUS_1)) {
			case 1:
				run();
				__token = getToken(0).kind;
				if (__token == TokenType.TILDE) {
					consume(TokenType.TILDE);
					op = UnaryOp.Inverse;
				} else if (__token == TokenType.BANG) {
					consume(TokenType.BANG);
					op = UnaryOp.Not;
				} else
					throw produceParseException(TokenType.BANG, TokenType.TILDE);
				pushCallStack(UNARY_EXPRESSION_NOT_PLUS_MINUS_1_1_2);
				ret = parseUnaryExpression();
				popCallStack();
				ret = dress(SUnaryExpr.make(op, ret));
				break;
			case 2:
				pushCallStack(UNARY_EXPRESSION_NOT_PLUS_MINUS_1_2);
				ret = parseCastExpression();
				popCallStack();
				break;
			case 3:
				pushCallStack(UNARY_EXPRESSION_NOT_PLUS_MINUS_1_3);
				ret = parsePostfixExpression();
				popCallStack();
				break;
			default:
				throw produceParseException(TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.DOUBLE, TokenType.FALSE, TokenType.FLOAT, TokenType.INT, TokenType.LONG, TokenType.NEW, TokenType.NULL, TokenType.SHORT, TokenType.SUPER, TokenType.THIS, TokenType.TRUE, TokenType.VOID, TokenType.LONG_LITERAL, TokenType.INTEGER_LITERAL, TokenType.FLOAT_LITERAL, TokenType.DOUBLE_LITERAL, TokenType.CHARACTER_LITERAL, TokenType.STRING_LITERAL, TokenType.LPAREN, TokenType.LT, TokenType.BANG, TokenType.TILDE, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER);
		}
		return ret;
	}

	/* sequence(
		nonTerminal(ret, PrimaryExpression)
		zeroOrOne(
			action({ lateRun(); })
			choice(
				sequence(
					terminal(INCR)
					action({ op = UnaryOp.PostIncrement; })
				)
				sequence(
					terminal(DECR)
					action({ op = UnaryOp.PostDecrement; })
				)
			)
			action({ ret = dress(SUnaryExpr.make(op, ret)); })
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parsePostfixExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		UnaryOp op;
		int __token;
		pushCallStack(POSTFIX_EXPRESSION_1);
		ret = parsePrimaryExpression();
		popCallStack();
		__token = getToken(0).kind;
		if (__token == TokenType.INCR || __token == TokenType.DECR) {
			lateRun();
			__token = getToken(0).kind;
			if (__token == TokenType.INCR) {
				consume(TokenType.INCR);
				op = UnaryOp.PostIncrement;
			} else if (__token == TokenType.DECR) {
				consume(TokenType.DECR);
				op = UnaryOp.PostDecrement;
			} else
				throw produceParseException(TokenType.INCR, TokenType.DECR);
			ret = dress(SUnaryExpr.make(op, ret));
		}
		return ret;
	}

	/* sequence(
		action({ run(); })
		terminal(LPAREN)
		action({ run(); })
		nonTerminal(annotations, Annotations)
		choice(
			sequence(
				nonTerminal(primitiveType, PrimitiveType)
				terminal(RPAREN)
				nonTerminal(ret, UnaryExpression)
				action({ ret = dress(SCastExpr.make(primitiveType, ret)); })
			)
			sequence(
				nonTerminal(type, ReferenceType)
				nonTerminal(type, ReferenceCastTypeRest)
				terminal(RPAREN)
				nonTerminal(ret, UnaryExpressionNotPlusMinus)
				action({ ret = dress(SCastExpr.make(type, ret)); })
			)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parseCastExpression() throws ParseException {
		BUTree<SNodeList> annotations = null;
		BUTree<? extends SType> primitiveType;
		BUTree<? extends SType> type;
		BUTree<SNodeList> arrayDims;
		BUTree<? extends SExpr> ret;
		int __token;
		run();
		consume(TokenType.LPAREN);
		run();
		pushCallStack(CAST_EXPRESSION_2);
		annotations = parseAnnotations();
		popCallStack();
		switch (predict(CAST_EXPRESSION_3)) {
			case 1:
				pushCallStack(CAST_EXPRESSION_3_1_1);
				primitiveType = parsePrimitiveType(annotations);
				popCallStack();
				consume(TokenType.RPAREN);
				pushCallStack(CAST_EXPRESSION_3_1_3);
				ret = parseUnaryExpression();
				popCallStack();
				ret = dress(SCastExpr.make(primitiveType, ret));
				break;
			case 2:
				pushCallStack(CAST_EXPRESSION_3_2_1);
				type = parseReferenceType(annotations);
				popCallStack();
				pushCallStack(CAST_EXPRESSION_3_2_2);
				type = parseReferenceCastTypeRest(type);
				popCallStack();
				consume(TokenType.RPAREN);
				pushCallStack(CAST_EXPRESSION_3_2_4);
				ret = parseUnaryExpressionNotPlusMinus();
				popCallStack();
				ret = dress(SCastExpr.make(type, ret));
				break;
			default:
				throw produceParseException(TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.DOUBLE, TokenType.FLOAT, TokenType.INT, TokenType.LONG, TokenType.SHORT, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER);
		}
		return ret;
	}

	/* sequence(
		zeroOrOne(
			action({ types = append(types, type); })
			action({ lateRun(); })
			oneOrMore(
				sequence(
					terminal(BIT_AND)
					action({ run(); })
					nonTerminal(annotations, Annotations)
					nonTerminal(type, ReferenceType)
					action({ types = append(types, type); })
				)
			)
			action({ type = dress(SIntersectionType.make(types)); })
		)
		action({ return type; })
	) */
	protected BUTree<? extends SType> parseReferenceCastTypeRest(BUTree<? extends SType> type) throws ParseException {
		BUTree<SNodeList> types = emptyList();
		BUTree<SNodeList> annotations = null;
		int __token;
		__token = getToken(0).kind;
		if (__token == TokenType.BIT_AND) {
			types = append(types, type);
			lateRun();
			do {
				consume(TokenType.BIT_AND);
				run();
				pushCallStack(REFERENCE_CAST_TYPE_REST_1_1_1_2);
				annotations = parseAnnotations();
				popCallStack();
				pushCallStack(REFERENCE_CAST_TYPE_REST_1_1_1_3);
				type = parseReferenceType(annotations);
				popCallStack();
				types = append(types, type);
				__token = getToken(0).kind;
			} while (__token == TokenType.BIT_AND);
			type = dress(SIntersectionType.make(types));
		}
		return type;
	}

	/* sequence(
		action({ run(); })
		choice(
			sequence(
				terminal(literal, INTEGER_LITERAL)
				action({ ret = SLiteralExpr.make(Integer.class, literal.image); })
			)
			sequence(
				terminal(literal, LONG_LITERAL)
				action({ ret = SLiteralExpr.make(Long.class, literal.image); })
			)
			sequence(
				terminal(literal, FLOAT_LITERAL)
				action({ ret = SLiteralExpr.make(Float.class, literal.image); })
			)
			sequence(
				terminal(literal, DOUBLE_LITERAL)
				action({ ret = SLiteralExpr.make(Double.class, literal.image); })
			)
			sequence(
				terminal(literal, CHARACTER_LITERAL)
				action({ ret = SLiteralExpr.make(Character.class, literal.image); })
			)
			sequence(
				terminal(literal, STRING_LITERAL)
				action({ ret = SLiteralExpr.make(String.class, literal.image); })
			)
			sequence(
				terminal(literal, TRUE)
				action({ ret = SLiteralExpr.make(Boolean.class, literal.image); })
			)
			sequence(
				terminal(literal, FALSE)
				action({ ret = SLiteralExpr.make(Boolean.class, literal.image); })
			)
			sequence(
				terminal(literal, NULL)
				action({ ret = SLiteralExpr.make(Void.class, literal.image); })
			)
		)
		action({ return dress(ret); })
	) */
	protected BUTree<? extends SExpr> parseLiteral() throws ParseException {
		Token literal;
		BUTree<? extends SExpr> ret;
		int __token;
		run();
		__token = getToken(0).kind;
		if (__token == TokenType.INTEGER_LITERAL) {
			literal = consume(TokenType.INTEGER_LITERAL);
			ret = SLiteralExpr.make(Integer.class, literal.image);
		} else if (__token == TokenType.LONG_LITERAL) {
			literal = consume(TokenType.LONG_LITERAL);
			ret = SLiteralExpr.make(Long.class, literal.image);
		} else if (__token == TokenType.FLOAT_LITERAL) {
			literal = consume(TokenType.FLOAT_LITERAL);
			ret = SLiteralExpr.make(Float.class, literal.image);
		} else if (__token == TokenType.DOUBLE_LITERAL) {
			literal = consume(TokenType.DOUBLE_LITERAL);
			ret = SLiteralExpr.make(Double.class, literal.image);
		} else if (__token == TokenType.CHARACTER_LITERAL) {
			literal = consume(TokenType.CHARACTER_LITERAL);
			ret = SLiteralExpr.make(Character.class, literal.image);
		} else if (__token == TokenType.STRING_LITERAL) {
			literal = consume(TokenType.STRING_LITERAL);
			ret = SLiteralExpr.make(String.class, literal.image);
		} else if (__token == TokenType.TRUE) {
			literal = consume(TokenType.TRUE);
			ret = SLiteralExpr.make(Boolean.class, literal.image);
		} else if (__token == TokenType.FALSE) {
			literal = consume(TokenType.FALSE);
			ret = SLiteralExpr.make(Boolean.class, literal.image);
		} else if (__token == TokenType.NULL) {
			literal = consume(TokenType.NULL);
			ret = SLiteralExpr.make(Void.class, literal.image);
		} else
			throw produceParseException(TokenType.FALSE, TokenType.NULL, TokenType.TRUE, TokenType.LONG_LITERAL, TokenType.INTEGER_LITERAL, TokenType.FLOAT_LITERAL, TokenType.DOUBLE_LITERAL, TokenType.CHARACTER_LITERAL, TokenType.STRING_LITERAL);
		return dress(ret);
	}

	/* sequence(
		choice(
			nonTerminal(ret, PrimaryNoNewArray)
			nonTerminal(ret, ArrayCreationExpr)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parsePrimaryExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		int __token;
		switch (predict(PRIMARY_EXPRESSION_1)) {
			case 1:
				pushCallStack(PRIMARY_EXPRESSION_1_1);
				ret = parsePrimaryNoNewArray();
				popCallStack();
				break;
			case 2:
				pushCallStack(PRIMARY_EXPRESSION_1_2);
				ret = parseArrayCreationExpr(null);
				popCallStack();
				break;
			default:
				throw produceParseException(TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.DOUBLE, TokenType.FALSE, TokenType.FLOAT, TokenType.INT, TokenType.LONG, TokenType.NEW, TokenType.NULL, TokenType.SHORT, TokenType.SUPER, TokenType.THIS, TokenType.TRUE, TokenType.VOID, TokenType.LONG_LITERAL, TokenType.INTEGER_LITERAL, TokenType.FLOAT_LITERAL, TokenType.DOUBLE_LITERAL, TokenType.CHARACTER_LITERAL, TokenType.STRING_LITERAL, TokenType.LPAREN, TokenType.LT, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER);
		}
		return ret;
	}

	/* sequence(
		nonTerminal(ret, PrimaryPrefix)
		zeroOrMore(
			action({ lateRun(); })
			nonTerminal(ret, PrimarySuffix)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parsePrimaryNoNewArray() throws ParseException {
		BUTree<? extends SExpr> ret;
		int __token;
		pushCallStack(PRIMARY_NO_NEW_ARRAY_1);
		ret = parsePrimaryPrefix();
		popCallStack();
		__token = getToken(0).kind;
		while ((__token - 84 & ~63) == 0 && (1L << __token - 84 & (1L << TokenType.LBRACKET - 84 | 1L << TokenType.DOT - 84 | 1L << TokenType.DOUBLECOLON - 84)) != 0) {
			lateRun();
			pushCallStack(PRIMARY_NO_NEW_ARRAY_2_1);
			ret = parsePrimarySuffix(ret);
			popCallStack();
			__token = getToken(0).kind;
		}
		return ret;
	}

	/* sequence(
		nonTerminal(ret, PrimaryPrefix)
		zeroOrMore(
			action({ lateRun(); })
			nonTerminal(ret, PrimarySuffixWithoutSuper)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parsePrimaryExpressionWithoutSuperSuffix() throws ParseException {
		BUTree<? extends SExpr> ret;
		int __token;
		pushCallStack(PRIMARY_EXPRESSION_WITHOUT_SUPER_SUFFIX_1);
		ret = parsePrimaryPrefix();
		popCallStack();
		__token = getToken(0).kind;
		while (predict(PRIMARY_EXPRESSION_WITHOUT_SUPER_SUFFIX_2) == 1) {
			lateRun();
			pushCallStack(PRIMARY_EXPRESSION_WITHOUT_SUPER_SUFFIX_2_1);
			ret = parsePrimarySuffixWithoutSuper(ret);
			popCallStack();
			__token = getToken(0).kind;
		}
		return ret;
	}

	/* sequence(
		choice(
			nonTerminal(ret, Literal)
			sequence(
				action({ run(); })
				terminal(THIS)
				action({ ret = dress(SThisExpr.make(none())); })
			)
			sequence(
				action({ run(); })
				terminal(SUPER)
				action({ ret = dress(SSuperExpr.make(none())); })
				choice(
					sequence(
						action({ lateRun(); })
						terminal(DOT)
						choice(
							nonTerminal(ret, MethodInvocation)
							nonTerminal(ret, FieldAccess)
						)
					)
					sequence(
						action({ lateRun(); })
						nonTerminal(ret, MethodReferenceSuffix)
					)
				)
			)
			nonTerminal(ret, ClassCreationExpr)
			sequence(
				action({ run(); })
				nonTerminal(type, ResultType)
				terminal(DOT)
				terminal(CLASS)
				action({ ret = dress(SClassExpr.make(type)); })
			)
			sequence(
				action({ run(); })
				nonTerminal(type, ResultType)
				action({ ret = STypeExpr.make(type); })
				nonTerminal(ret, MethodReferenceSuffix)
			)
			sequence(
				action({ run(); })
				nonTerminal(ret, MethodInvocation)
			)
			nonTerminal(ret, Name)
			sequence(
				action({ run(); })
				terminal(LPAREN)
				nonTerminal(ret, Expression)
				terminal(RPAREN)
				action({ ret = dress(SParenthesizedExpr.make(ret)); })
			)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parsePrimaryPrefix() throws ParseException {
		BUTree<? extends SExpr> ret = null;
		BUTree<SNodeList> typeArgs = null;
		BUTree<SNodeList> params;
		BUTree<? extends SType> type;
		int __token;
		switch (predict(PRIMARY_PREFIX_1)) {
			case 1:
				pushCallStack(PRIMARY_PREFIX_1_1);
				ret = parseLiteral();
				popCallStack();
				break;
			case 2:
				run();
				consume(TokenType.THIS);
				ret = dress(SThisExpr.make(none()));
				break;
			case 3:
				run();
				consume(TokenType.SUPER);
				ret = dress(SSuperExpr.make(none()));
				__token = getToken(0).kind;
				if (__token == TokenType.DOT) {
					lateRun();
					consume(TokenType.DOT);
					switch (predict(PRIMARY_PREFIX_1_3_2_1_2)) {
						case 1:
							pushCallStack(PRIMARY_PREFIX_1_3_2_1_2_1);
							ret = parseMethodInvocation(ret);
							popCallStack();
							break;
						case 2:
							pushCallStack(PRIMARY_PREFIX_1_3_2_1_2_2);
							ret = parseFieldAccess(ret);
							popCallStack();
							break;
						default:
							throw produceParseException(TokenType.LT, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER);
					}
				} else if (__token == TokenType.DOUBLECOLON) {
					lateRun();
					pushCallStack(PRIMARY_PREFIX_1_3_2_2_1);
					ret = parseMethodReferenceSuffix(ret);
					popCallStack();
				} else
					throw produceParseException(TokenType.DOT, TokenType.DOUBLECOLON);
				break;
			case 4:
				pushCallStack(PRIMARY_PREFIX_1_4);
				ret = parseClassCreationExpr(null);
				popCallStack();
				break;
			case 5:
				run();
				pushCallStack(PRIMARY_PREFIX_1_5_1);
				type = parseResultType();
				popCallStack();
				consume(TokenType.DOT);
				consume(TokenType.CLASS);
				ret = dress(SClassExpr.make(type));
				break;
			case 6:
				run();
				pushCallStack(PRIMARY_PREFIX_1_6_1);
				type = parseResultType();
				popCallStack();
				ret = STypeExpr.make(type);
				pushCallStack(PRIMARY_PREFIX_1_6_2);
				ret = parseMethodReferenceSuffix(ret);
				popCallStack();
				break;
			case 7:
				run();
				pushCallStack(PRIMARY_PREFIX_1_7_1);
				ret = parseMethodInvocation(null);
				popCallStack();
				break;
			case 8:
				pushCallStack(PRIMARY_PREFIX_1_8);
				ret = parseName();
				popCallStack();
				break;
			case 9:
				run();
				consume(TokenType.LPAREN);
				pushCallStack(PRIMARY_PREFIX_1_9_2);
				ret = parseExpression();
				popCallStack();
				consume(TokenType.RPAREN);
				ret = dress(SParenthesizedExpr.make(ret));
				break;
			default:
				throw produceParseException(TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.DOUBLE, TokenType.FALSE, TokenType.FLOAT, TokenType.INT, TokenType.LONG, TokenType.NEW, TokenType.NULL, TokenType.SHORT, TokenType.SUPER, TokenType.THIS, TokenType.TRUE, TokenType.VOID, TokenType.LONG_LITERAL, TokenType.INTEGER_LITERAL, TokenType.FLOAT_LITERAL, TokenType.DOUBLE_LITERAL, TokenType.CHARACTER_LITERAL, TokenType.STRING_LITERAL, TokenType.LPAREN, TokenType.LT, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER);
		}
		return ret;
	}

	/* sequence(
		choice(
			sequence(
				nonTerminal(ret, PrimarySuffixWithoutSuper)
			)
			sequence(
				terminal(DOT)
				terminal(SUPER)
				action({ ret = dress(SSuperExpr.make(optionOf(scope))); })
			)
			nonTerminal(ret, MethodReferenceSuffix)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parsePrimarySuffix(BUTree<? extends SExpr> scope) throws ParseException {
		BUTree<? extends SExpr> ret;
		int __token;
		switch (predict(PRIMARY_SUFFIX_1)) {
			case 1:
				pushCallStack(PRIMARY_SUFFIX_1_1_1);
				ret = parsePrimarySuffixWithoutSuper(scope);
				popCallStack();
				break;
			case 2:
				consume(TokenType.DOT);
				consume(TokenType.SUPER);
				ret = dress(SSuperExpr.make(optionOf(scope)));
				break;
			case 3:
				pushCallStack(PRIMARY_SUFFIX_1_3);
				ret = parseMethodReferenceSuffix(scope);
				popCallStack();
				break;
			default:
				throw produceParseException(TokenType.LBRACKET, TokenType.DOT, TokenType.DOUBLECOLON);
		}
		return ret;
	}

	/* sequence(
		choice(
			sequence(
				terminal(DOT)
				choice(
					sequence(
						terminal(THIS)
						action({ ret = dress(SThisExpr.make(optionOf(scope))); })
					)
					nonTerminal(ret, ClassCreationExpr)
					nonTerminal(ret, MethodInvocation)
					nonTerminal(ret, FieldAccess)
				)
			)
			sequence(
				terminal(LBRACKET)
				nonTerminal(ret, Expression)
				terminal(RBRACKET)
				action({ ret = dress(SArrayAccessExpr.make(scope, ret)); })
			)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parsePrimarySuffixWithoutSuper(BUTree<? extends SExpr> scope) throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<SName> name;
		int __token;
		__token = getToken(0).kind;
		if (__token == TokenType.DOT) {
			consume(TokenType.DOT);
			switch (predict(PRIMARY_SUFFIX_WITHOUT_SUPER_1_1_2)) {
				case 1:
					consume(TokenType.THIS);
					ret = dress(SThisExpr.make(optionOf(scope)));
					break;
				case 2:
					pushCallStack(PRIMARY_SUFFIX_WITHOUT_SUPER_1_1_2_2);
					ret = parseClassCreationExpr(scope);
					popCallStack();
					break;
				case 3:
					pushCallStack(PRIMARY_SUFFIX_WITHOUT_SUPER_1_1_2_3);
					ret = parseMethodInvocation(scope);
					popCallStack();
					break;
				case 4:
					pushCallStack(PRIMARY_SUFFIX_WITHOUT_SUPER_1_1_2_4);
					ret = parseFieldAccess(scope);
					popCallStack();
					break;
				default:
					throw produceParseException(TokenType.NEW, TokenType.THIS, TokenType.LT, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER);
			}
		} else if (__token == TokenType.LBRACKET) {
			consume(TokenType.LBRACKET);
			pushCallStack(PRIMARY_SUFFIX_WITHOUT_SUPER_1_2_2);
			ret = parseExpression();
			popCallStack();
			consume(TokenType.RBRACKET);
			ret = dress(SArrayAccessExpr.make(scope, ret));
		} else
			throw produceParseException(TokenType.LBRACKET, TokenType.DOT);
		return ret;
	}

	/* sequence(
		nonTerminal(name, Name)
		action({ return dress(SFieldAccessExpr.make(optionOf(scope), name)); })
	) */
	protected BUTree<? extends SExpr> parseFieldAccess(BUTree<? extends SExpr> scope) throws ParseException {
		BUTree<SName> name;
		int __token;
		pushCallStack(FIELD_ACCESS_1);
		name = parseName();
		popCallStack();
		return dress(SFieldAccessExpr.make(optionOf(scope), name));
	}

	/* sequence(
		zeroOrOne(
			nonTerminal(typeArgs, TypeArguments)
		)
		nonTerminal(name, Name)
		nonTerminal(args, Arguments)
		action({ return dress(SMethodInvocationExpr.make(optionOf(scope), ensureNotNull(typeArgs), name, args)); })
	) */
	protected BUTree<? extends SExpr> parseMethodInvocation(BUTree<? extends SExpr> scope) throws ParseException {
		BUTree<SNodeList> typeArgs = null;
		BUTree<SName> name;
		BUTree<SNodeList> args = null;
		BUTree<? extends SExpr> ret;
		int __token;
		__token = getToken(0).kind;
		if (__token == TokenType.LT) {
			pushCallStack(METHOD_INVOCATION_1_1);
			typeArgs = parseTypeArguments();
			popCallStack();
		}
		pushCallStack(METHOD_INVOCATION_2);
		name = parseName();
		popCallStack();
		pushCallStack(METHOD_INVOCATION_3);
		args = parseArguments();
		popCallStack();
		return dress(SMethodInvocationExpr.make(optionOf(scope), ensureNotNull(typeArgs), name, args));
	}

	/* sequence(
		terminal(LPAREN)
		zeroOrOne(
			choice(
				nonTerminal(ret, NodeListVar)
				sequence(
					nonTerminal(expr, Expression)
					action({ ret = append(ret, expr); })
					zeroOrMore(
						sequence(
							terminal(COMMA)
							nonTerminal(expr, Expression)
							action({ ret = append(ret, expr); })
						)
					)
				)
			)
		)
		terminal(RPAREN)
		action({ return ret; })
	) */
	protected BUTree<SNodeList> parseArguments() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<? extends SExpr> expr;
		int __token;
		consume(TokenType.LPAREN);
		__token = getToken(0).kind;
		if (__token == TokenType.NODE_LIST_VARIABLE) {
			pushCallStack(ARGUMENTS_2_1_1);
			ret = parseNodeListVar();
			popCallStack();
		} else if (((__token - 11 & ~63) == 0 && (1L << __token - 11 & (1L << TokenType.BOOLEAN - 11 | 1L << TokenType.BYTE - 11 | 1L << TokenType.CHAR - 11 | 1L << TokenType.DOUBLE - 11 | 1L << TokenType.FALSE - 11 | 1L << TokenType.FLOAT - 11 | 1L << TokenType.INT - 11 | 1L << TokenType.LONG - 11 | 1L << TokenType.NEW - 11 | 1L << TokenType.NULL - 11 | 1L << TokenType.SHORT - 11 | 1L << TokenType.SUPER - 11 | 1L << TokenType.THIS - 11 | 1L << TokenType.TRUE - 11 | 1L << TokenType.VOID - 11 | 1L << TokenType.LONG_LITERAL - 11 | 1L << TokenType.INTEGER_LITERAL - 11 | 1L << TokenType.FLOAT_LITERAL - 11 | 1L << TokenType.DOUBLE_LITERAL - 11)) != 0) || ((__token - 78 & ~63) == 0 && (1L << __token - 78 & (1L << TokenType.CHARACTER_LITERAL - 78 | 1L << TokenType.STRING_LITERAL - 78 | 1L << TokenType.LPAREN - 78 | 1L << TokenType.LT - 78 | 1L << TokenType.BANG - 78 | 1L << TokenType.TILDE - 78 | 1L << TokenType.INCR - 78 | 1L << TokenType.DECR - 78 | 1L << TokenType.PLUS - 78 | 1L << TokenType.MINUS - 78 | 1L << TokenType.NODE_VARIABLE - 78 | 1L << TokenType.IDENTIFIER - 78)) != 0)) {
			pushCallStack(ARGUMENTS_2_1_2_1);
			expr = parseExpression();
			popCallStack();
			ret = append(ret, expr);
			__token = getToken(0).kind;
			while (__token == TokenType.COMMA) {
				consume(TokenType.COMMA);
				pushCallStack(ARGUMENTS_2_1_2_2_1_2);
				expr = parseExpression();
				popCallStack();
				ret = append(ret, expr);
				__token = getToken(0).kind;
			}
		}
		consume(TokenType.RPAREN);
		return ret;
	}

	/* sequence(
		terminal(DOUBLECOLON)
		zeroOrOne(
			nonTerminal(typeArgs, TypeArguments)
		)
		choice(
			nonTerminal(name, Name)
			sequence(
				terminal(NEW)
				action({ name = SName.make("new"); })
			)
		)
		action({ ret = dress(SMethodReferenceExpr.make(scope, ensureNotNull(typeArgs), name)); })
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parseMethodReferenceSuffix(BUTree<? extends SExpr> scope) throws ParseException {
		BUTree<SNodeList> typeArgs = null;
		BUTree<SName> name;
		BUTree<? extends SExpr> ret;
		int __token;
		consume(TokenType.DOUBLECOLON);
		__token = getToken(0).kind;
		if (__token == TokenType.LT) {
			pushCallStack(METHOD_REFERENCE_SUFFIX_2_1);
			typeArgs = parseTypeArguments();
			popCallStack();
		}
		__token = getToken(0).kind;
		if (__token == TokenType.NODE_VARIABLE || __token == TokenType.IDENTIFIER) {
			pushCallStack(METHOD_REFERENCE_SUFFIX_3_1);
			name = parseName();
			popCallStack();
		} else if (__token == TokenType.NEW) {
			consume(TokenType.NEW);
			name = SName.make("new");
		} else
			throw produceParseException(TokenType.NEW, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER);
		ret = dress(SMethodReferenceExpr.make(scope, ensureNotNull(typeArgs), name));
		return ret;
	}

	/* sequence(
		action({
			if (scope == null) run();
		})
		terminal(NEW)
		zeroOrOne(
			nonTerminal(typeArgs, TypeArguments)
		)
		action({ run(); })
		nonTerminal(annotations, Annotations)
		nonTerminal(type, QualifiedType)
		nonTerminal(args, Arguments)
		zeroOrOne(
			nonTerminal(anonymousBody, ClassOrInterfaceBody)
		)
		action({ return dress(SObjectCreationExpr.make(optionOf(scope), ensureNotNull(typeArgs), (BUTree<SQualifiedType>) type, args, optionOf(anonymousBody))); })
	) */
	protected BUTree<? extends SExpr> parseClassCreationExpr(BUTree<? extends SExpr> scope) throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SType> type;
		BUTree<SNodeList> typeArgs = null;
		BUTree<SNodeList> anonymousBody = null;
		BUTree<SNodeList> args;
		BUTree<SNodeList> annotations = null;
		int __token;
		if (scope == null) run();
		consume(TokenType.NEW);
		__token = getToken(0).kind;
		if (__token == TokenType.LT) {
			pushCallStack(CLASS_CREATION_EXPR_2_1);
			typeArgs = parseTypeArguments();
			popCallStack();
		}
		run();
		pushCallStack(CLASS_CREATION_EXPR_3);
		annotations = parseAnnotations();
		popCallStack();
		pushCallStack(CLASS_CREATION_EXPR_4);
		type = parseQualifiedType(annotations);
		popCallStack();
		pushCallStack(CLASS_CREATION_EXPR_5);
		args = parseArguments();
		popCallStack();
		__token = getToken(0).kind;
		if (__token == TokenType.LBRACE) {
			pushCallStack(CLASS_CREATION_EXPR_6_1);
			anonymousBody = parseClassOrInterfaceBody(TypeKind.Class);
			popCallStack();
		}
		return dress(SObjectCreationExpr.make(optionOf(scope), ensureNotNull(typeArgs), (BUTree<SQualifiedType>) type, args, optionOf(anonymousBody)));
	}

	/* sequence(
		action({
			if (scope == null) run();
		})
		terminal(NEW)
		zeroOrOne(
			nonTerminal(typeArgs, TypeArguments)
		)
		action({ run(); })
		nonTerminal(annotations, Annotations)
		choice(
			nonTerminal(type, PrimitiveType)
			nonTerminal(type, QualifiedType)
		)
		nonTerminal(ret, ArrayCreationExprRest)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parseArrayCreationExpr(BUTree<? extends SExpr> scope) throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SType> type;
		BUTree<SNodeList> typeArgs = null;
		BUTree<SNodeList> anonymousBody = null;
		BUTree<SNodeList> args;
		BUTree<SNodeList> annotations = null;
		int __token;
		if (scope == null) run();
		consume(TokenType.NEW);
		__token = getToken(0).kind;
		if (__token == TokenType.LT) {
			pushCallStack(ARRAY_CREATION_EXPR_2_1);
			typeArgs = parseTypeArguments();
			popCallStack();
		}
		run();
		pushCallStack(ARRAY_CREATION_EXPR_3);
		annotations = parseAnnotations();
		popCallStack();
		__token = getToken(0).kind;
		if ((__token - 11 & ~63) == 0 && (1L << __token - 11 & (1L << TokenType.BOOLEAN - 11 | 1L << TokenType.BYTE - 11 | 1L << TokenType.CHAR - 11 | 1L << TokenType.DOUBLE - 11 | 1L << TokenType.FLOAT - 11 | 1L << TokenType.INT - 11 | 1L << TokenType.LONG - 11 | 1L << TokenType.SHORT - 11)) != 0) {
			pushCallStack(ARRAY_CREATION_EXPR_4_1);
			type = parsePrimitiveType(annotations);
			popCallStack();
		} else if (__token == TokenType.NODE_VARIABLE || __token == TokenType.IDENTIFIER) {
			pushCallStack(ARRAY_CREATION_EXPR_4_2);
			type = parseQualifiedType(annotations);
			popCallStack();
		} else
			throw produceParseException(TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.DOUBLE, TokenType.FLOAT, TokenType.INT, TokenType.LONG, TokenType.SHORT, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER);
		pushCallStack(ARRAY_CREATION_EXPR_5);
		ret = parseArrayCreationExprRest(type);
		popCallStack();
		return ret;
	}

	/* sequence(
		choice(
			sequence(
				nonTerminal(arrayDimExprs, ArrayDimExprsMandatory)
				nonTerminal(arrayDims, ArrayDims)
				action({ return dress(SArrayCreationExpr.make(componentType, arrayDimExprs, arrayDims, none())); })
			)
			sequence(
				nonTerminal(arrayDims, ArrayDimsMandatory)
				nonTerminal(initializer, ArrayInitializer)
				action({ return dress(SArrayCreationExpr.make(componentType, arrayDimExprs, arrayDims, optionOf(initializer))); })
			)
		)
	) */
	protected BUTree<? extends SExpr> parseArrayCreationExprRest(BUTree<? extends SType> componentType) throws ParseException {
		BUTree<? extends SExpr> expr;
		BUTree<SNodeList> arrayDimExprs = emptyList();
		BUTree<SNodeList> arrayDims = emptyList();
		BUTree<SNodeList> annotations = null;
		BUTree<SArrayInitializerExpr> initializer;
		int __token;
		switch (predict(ARRAY_CREATION_EXPR_REST_1)) {
			case 1:
				pushCallStack(ARRAY_CREATION_EXPR_REST_1_1_1);
				arrayDimExprs = parseArrayDimExprsMandatory();
				popCallStack();
				pushCallStack(ARRAY_CREATION_EXPR_REST_1_1_2);
				arrayDims = parseArrayDims();
				popCallStack();
				return dress(SArrayCreationExpr.make(componentType, arrayDimExprs, arrayDims, none()));
			case 2:
				pushCallStack(ARRAY_CREATION_EXPR_REST_1_2_1);
				arrayDims = parseArrayDimsMandatory();
				popCallStack();
				pushCallStack(ARRAY_CREATION_EXPR_REST_1_2_2);
				initializer = parseArrayInitializer();
				popCallStack();
				return dress(SArrayCreationExpr.make(componentType, arrayDimExprs, arrayDims, optionOf(initializer)));
			default:
				throw produceParseException(TokenType.LBRACKET, TokenType.AT);
		}
	}

	/* sequence(
		oneOrMore(
			sequence(
				action({ run(); })
				nonTerminal(annotations, Annotations)
				terminal(LBRACKET)
				nonTerminal(expr, Expression)
				terminal(RBRACKET)
				action({ arrayDimExprs = append(arrayDimExprs, dress(SArrayDimExpr.make(annotations, expr))); })
			)
		)
		action({ return arrayDimExprs; })
	) */
	protected BUTree<SNodeList> parseArrayDimExprsMandatory() throws ParseException {
		BUTree<SNodeList> arrayDimExprs = emptyList();
		BUTree<SNodeList> annotations;
		BUTree<? extends SExpr> expr;
		int __token;
		do {
			run();
			pushCallStack(ARRAY_DIM_EXPRS_MANDATORY_1_1_1);
			annotations = parseAnnotations();
			popCallStack();
			consume(TokenType.LBRACKET);
			pushCallStack(ARRAY_DIM_EXPRS_MANDATORY_1_1_3);
			expr = parseExpression();
			popCallStack();
			consume(TokenType.RBRACKET);
			arrayDimExprs = append(arrayDimExprs, dress(SArrayDimExpr.make(annotations, expr)));
			__token = getToken(0).kind;
		} while (predict(ARRAY_DIM_EXPRS_MANDATORY_1) == 1);
		return arrayDimExprs;
	}

	/* sequence(
		oneOrMore(
			sequence(
				action({ run(); })
				nonTerminal(annotations, Annotations)
				terminal(LBRACKET)
				terminal(RBRACKET)
				action({ arrayDims = append(arrayDims, dress(SArrayDim.make(annotations))); })
			)
		)
		action({ return arrayDims; })
	) */
	protected BUTree<SNodeList> parseArrayDimsMandatory() throws ParseException {
		BUTree<SNodeList> arrayDims = emptyList();
		BUTree<SNodeList> annotations;
		int __token;
		do {
			run();
			pushCallStack(ARRAY_DIMS_MANDATORY_1_1_1);
			annotations = parseAnnotations();
			popCallStack();
			consume(TokenType.LBRACKET);
			consume(TokenType.RBRACKET);
			arrayDims = append(arrayDims, dress(SArrayDim.make(annotations)));
			__token = getToken(0).kind;
		} while (predict(ARRAY_DIMS_MANDATORY_1) == 1);
		return arrayDims;
	}

	/* sequence(
		choice(
			nonTerminal(ret, LabeledStatement)
			nonTerminal(ret, AssertStatement)
			nonTerminal(ret, Block)
			nonTerminal(ret, EmptyStatement)
			nonTerminal(ret, ExpressionStatement)
			nonTerminal(ret, SwitchStatement)
			nonTerminal(ret, IfStatement)
			nonTerminal(ret, WhileStatement)
			nonTerminal(ret, DoStatement)
			nonTerminal(ret, ForStatement)
			nonTerminal(ret, BreakStatement)
			nonTerminal(ret, ContinueStatement)
			nonTerminal(ret, ReturnStatement)
			nonTerminal(ret, ThrowStatement)
			nonTerminal(ret, SynchronizedStatement)
			nonTerminal(ret, TryStatement)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SStmt> parseStatement() throws ParseException {
		BUTree<? extends SStmt> ret;
		int __token;
		switch (predict(STATEMENT_1)) {
			case 1:
				pushCallStack(STATEMENT_1_1);
				ret = parseLabeledStatement();
				popCallStack();
				break;
			case 2:
				pushCallStack(STATEMENT_1_2);
				ret = parseAssertStatement();
				popCallStack();
				break;
			case 3:
				pushCallStack(STATEMENT_1_3);
				ret = parseBlock();
				popCallStack();
				break;
			case 4:
				pushCallStack(STATEMENT_1_4);
				ret = parseEmptyStatement();
				popCallStack();
				break;
			case 5:
				pushCallStack(STATEMENT_1_5);
				ret = parseExpressionStatement();
				popCallStack();
				break;
			case 6:
				pushCallStack(STATEMENT_1_6);
				ret = parseSwitchStatement();
				popCallStack();
				break;
			case 7:
				pushCallStack(STATEMENT_1_7);
				ret = parseIfStatement();
				popCallStack();
				break;
			case 8:
				pushCallStack(STATEMENT_1_8);
				ret = parseWhileStatement();
				popCallStack();
				break;
			case 9:
				pushCallStack(STATEMENT_1_9);
				ret = parseDoStatement();
				popCallStack();
				break;
			case 10:
				pushCallStack(STATEMENT_1_10);
				ret = parseForStatement();
				popCallStack();
				break;
			case 11:
				pushCallStack(STATEMENT_1_11);
				ret = parseBreakStatement();
				popCallStack();
				break;
			case 12:
				pushCallStack(STATEMENT_1_12);
				ret = parseContinueStatement();
				popCallStack();
				break;
			case 13:
				pushCallStack(STATEMENT_1_13);
				ret = parseReturnStatement();
				popCallStack();
				break;
			case 14:
				pushCallStack(STATEMENT_1_14);
				ret = parseThrowStatement();
				popCallStack();
				break;
			case 15:
				pushCallStack(STATEMENT_1_15);
				ret = parseSynchronizedStatement();
				popCallStack();
				break;
			case 16:
				pushCallStack(STATEMENT_1_16);
				ret = parseTryStatement();
				popCallStack();
				break;
			default:
				throw produceParseException(TokenType.ASSERT, TokenType.BOOLEAN, TokenType.BREAK, TokenType.BYTE, TokenType.CHAR, TokenType.CONTINUE, TokenType.DO, TokenType.DOUBLE, TokenType.FALSE, TokenType.FLOAT, TokenType.FOR, TokenType.IF, TokenType.INT, TokenType.LONG, TokenType.NEW, TokenType.NULL, TokenType.RETURN, TokenType.SHORT, TokenType.SUPER, TokenType.SWITCH, TokenType.SYNCHRONIZED, TokenType.THIS, TokenType.THROW, TokenType.TRUE, TokenType.TRY, TokenType.VOID, TokenType.WHILE, TokenType.LONG_LITERAL, TokenType.INTEGER_LITERAL, TokenType.FLOAT_LITERAL, TokenType.DOUBLE_LITERAL, TokenType.CHARACTER_LITERAL, TokenType.STRING_LITERAL, TokenType.LPAREN, TokenType.LBRACE, TokenType.SEMICOLON, TokenType.LT, TokenType.BANG, TokenType.TILDE, TokenType.INCR, TokenType.DECR, TokenType.PLUS, TokenType.MINUS, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER);
		}
		return ret;
	}

	/* sequence(
		action({ run(); })
		terminal(ASSERT)
		nonTerminal(check, Expression)
		zeroOrOne(
			sequence(
				terminal(COLON)
				nonTerminal(msg, Expression)
			)
		)
		terminal(SEMICOLON)
		action({ return dress(SAssertStmt.make(check, optionOf(msg))); })
	) */
	protected BUTree<SAssertStmt> parseAssertStatement() throws ParseException {
		BUTree<? extends SExpr> check;
		BUTree<? extends SExpr> msg = null;
		int __token;
		run();
		consume(TokenType.ASSERT);
		pushCallStack(ASSERT_STATEMENT_2);
		check = parseExpression();
		popCallStack();
		__token = getToken(0).kind;
		if (__token == TokenType.COLON) {
			consume(TokenType.COLON);
			pushCallStack(ASSERT_STATEMENT_3_1_2);
			msg = parseExpression();
			popCallStack();
		}
		consume(TokenType.SEMICOLON);
		return dress(SAssertStmt.make(check, optionOf(msg)));
	}

	/* sequence(
		action({ run(); })
		nonTerminal(label, Name)
		terminal(COLON)
		nonTerminal(stmt, Statement)
		action({ return dress(SLabeledStmt.make(label, stmt)); })
	) */
	protected BUTree<SLabeledStmt> parseLabeledStatement() throws ParseException {
		BUTree<SName> label;
		BUTree<? extends SStmt> stmt;
		int __token;
		run();
		pushCallStack(LABELED_STATEMENT_1);
		label = parseName();
		popCallStack();
		consume(TokenType.COLON);
		pushCallStack(LABELED_STATEMENT_3);
		stmt = parseStatement();
		popCallStack();
		return dress(SLabeledStmt.make(label, stmt));
	}

	/* sequence(
		action({ run(); })
		terminal(LBRACE)
		nonTerminal(stmts, Statements)
		terminal(RBRACE)
		action({ return dress(SBlockStmt.make(ensureNotNull(stmts))); })
	) */
	protected BUTree<SBlockStmt> parseBlock() throws ParseException {
		BUTree<SNodeList> stmts;
		int __token;
		run();
		consume(TokenType.LBRACE);
		pushCallStack(BLOCK_2);
		stmts = parseStatements();
		popCallStack();
		consume(TokenType.RBRACE);
		return dress(SBlockStmt.make(ensureNotNull(stmts)));
	}

	/* sequence(
		choice(
			sequence(
				action({ run(); })
				action({ run(); })
				nonTerminal(modifiers, ModifiersNoDefault)
				nonTerminal(typeDecl, ClassOrInterfaceDecl)
				action({ ret = dress(STypeDeclarationStmt.make(typeDecl)); })
			)
			sequence(
				action({ run(); })
				nonTerminal(expr, VariableDeclExpression)
				terminal(SEMICOLON)
				action({ ret = dress(SExpressionStmt.make(expr)); })
			)
			nonTerminal(ret, Statement)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SStmt> parseBlockStatement() throws ParseException {
		BUTree<? extends SStmt> ret;
		BUTree<? extends SExpr> expr;
		BUTree<? extends STypeDecl> typeDecl;
		BUTree<SNodeList> modifiers;
		int __token;
		switch (predict(BLOCK_STATEMENT_1)) {
			case 1:
				run();
				run();
				pushCallStack(BLOCK_STATEMENT_1_1_1);
				modifiers = parseModifiersNoDefault();
				popCallStack();
				pushCallStack(BLOCK_STATEMENT_1_1_2);
				typeDecl = parseClassOrInterfaceDecl(modifiers);
				popCallStack();
				ret = dress(STypeDeclarationStmt.make(typeDecl));
				break;
			case 2:
				run();
				pushCallStack(BLOCK_STATEMENT_1_2_1);
				expr = parseVariableDeclExpression();
				popCallStack();
				consume(TokenType.SEMICOLON);
				ret = dress(SExpressionStmt.make(expr));
				break;
			case 3:
				pushCallStack(BLOCK_STATEMENT_1_3);
				ret = parseStatement();
				popCallStack();
				break;
			default:
				throw produceParseException(TokenType.ABSTRACT, TokenType.ASSERT, TokenType.BOOLEAN, TokenType.BREAK, TokenType.BYTE, TokenType.CHAR, TokenType.CLASS, TokenType.CONTINUE, TokenType.DO, TokenType.DOUBLE, TokenType.FALSE, TokenType.FINAL, TokenType.FLOAT, TokenType.FOR, TokenType.IF, TokenType.INT, TokenType.INTERFACE, TokenType.LONG, TokenType.NATIVE, TokenType.NEW, TokenType.NULL, TokenType.PRIVATE, TokenType.PROTECTED, TokenType.PUBLIC, TokenType.RETURN, TokenType.SHORT, TokenType.STATIC, TokenType.STRICTFP, TokenType.SUPER, TokenType.SWITCH, TokenType.SYNCHRONIZED, TokenType.THIS, TokenType.THROW, TokenType.TRANSIENT, TokenType.TRUE, TokenType.TRY, TokenType.VOID, TokenType.VOLATILE, TokenType.WHILE, TokenType.LONG_LITERAL, TokenType.INTEGER_LITERAL, TokenType.FLOAT_LITERAL, TokenType.DOUBLE_LITERAL, TokenType.CHARACTER_LITERAL, TokenType.STRING_LITERAL, TokenType.LPAREN, TokenType.LBRACE, TokenType.SEMICOLON, TokenType.AT, TokenType.LT, TokenType.BANG, TokenType.TILDE, TokenType.INCR, TokenType.DECR, TokenType.PLUS, TokenType.MINUS, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER);
		}
		return ret;
	}

	/* sequence(
		action({ run(); })
		action({ run(); })
		nonTerminal(modifiers, ModifiersNoDefault)
		nonTerminal(variableDecl, VariableDecl)
		action({ return dress(SVariableDeclarationExpr.make(variableDecl)); })
	) */
	protected BUTree<SVariableDeclarationExpr> parseVariableDeclExpression() throws ParseException {
		BUTree<SNodeList> modifiers;
		BUTree<SLocalVariableDecl> variableDecl;
		int __token;
		run();
		run();
		pushCallStack(VARIABLE_DECL_EXPRESSION_1);
		modifiers = parseModifiersNoDefault();
		popCallStack();
		pushCallStack(VARIABLE_DECL_EXPRESSION_2);
		variableDecl = parseVariableDecl(modifiers);
		popCallStack();
		return dress(SVariableDeclarationExpr.make(variableDecl));
	}

	/* sequence(
		action({ run(); })
		terminal(SEMICOLON)
		action({ return dress(SEmptyStmt.make()); })
	) */
	protected BUTree<SEmptyStmt> parseEmptyStatement() throws ParseException {
		int __token;
		run();
		consume(TokenType.SEMICOLON);
		return dress(SEmptyStmt.make());
	}

	/* sequence(
		action({ run(); })
		nonTerminal(expr, StatementExpression)
		terminal(SEMICOLON)
		action({ return dress(SExpressionStmt.make(expr)); })
	) */
	protected BUTree<SExpressionStmt> parseExpressionStatement() throws ParseException {
		BUTree<? extends SExpr> expr;
		AssignOp op;
		BUTree<? extends SExpr> value;
		int __token;
		run();
		pushCallStack(EXPRESSION_STATEMENT_1);
		expr = parseStatementExpression();
		popCallStack();
		consume(TokenType.SEMICOLON);
		return dress(SExpressionStmt.make(expr));
	}

	/* sequence(
		nonTerminal(ret, Expression)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parseStatementExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		int __token;
		pushCallStack(STATEMENT_EXPRESSION_1);
		ret = parseExpression();
		popCallStack();
		return ret;
	}

	/* sequence(
		action({ run(); })
		terminal(SWITCH)
		terminal(LPAREN)
		nonTerminal(selector, Expression)
		terminal(RPAREN)
		terminal(LBRACE)
		zeroOrMore(
			nonTerminal(entry, SwitchEntry)
			action({ entries = append(entries, entry); })
		)
		terminal(RBRACE)
		action({ return dress(SSwitchStmt.make(selector, entries)); })
	) */
	protected BUTree<SSwitchStmt> parseSwitchStatement() throws ParseException {
		BUTree<? extends SExpr> selector;
		BUTree<SSwitchCase> entry;
		BUTree<SNodeList> entries = emptyList();
		int __token;
		run();
		consume(TokenType.SWITCH);
		consume(TokenType.LPAREN);
		pushCallStack(SWITCH_STATEMENT_3);
		selector = parseExpression();
		popCallStack();
		consume(TokenType.RPAREN);
		consume(TokenType.LBRACE);
		__token = getToken(0).kind;
		while (__token == TokenType.CASE || __token == TokenType.DEFAULT) {
			pushCallStack(SWITCH_STATEMENT_6_1);
			entry = parseSwitchEntry();
			popCallStack();
			entries = append(entries, entry);
			__token = getToken(0).kind;
		}
		consume(TokenType.RBRACE);
		return dress(SSwitchStmt.make(selector, entries));
	}

	/* sequence(
		action({ run(); })
		choice(
			sequence(
				terminal(CASE)
				nonTerminal(label, Expression)
			)
			terminal(DEFAULT)
		)
		terminal(COLON)
		nonTerminal(stmts, Statements)
		action({ return dress(SSwitchCase.make(optionOf(label), ensureNotNull(stmts))); })
	) */
	public BUTree<SSwitchCase> parseSwitchEntry() throws ParseException {
		BUTree<? extends SExpr> label = null;
		BUTree<SNodeList> stmts;
		int __token;
		run();
		__token = getToken(0).kind;
		if (__token == TokenType.CASE) {
			consume(TokenType.CASE);
			pushCallStack(SWITCH_ENTRY_1_1_2);
			label = parseExpression();
			popCallStack();
		} else if (__token == TokenType.DEFAULT) {
			consume(TokenType.DEFAULT);
		} else
			throw produceParseException(TokenType.CASE, TokenType.DEFAULT);
		consume(TokenType.COLON);
		pushCallStack(SWITCH_ENTRY_3);
		stmts = parseStatements();
		popCallStack();
		return dress(SSwitchCase.make(optionOf(label), ensureNotNull(stmts)));
	}

	/* sequence(
		action({ run(); })
		terminal(IF)
		terminal(LPAREN)
		nonTerminal(condition, Expression)
		terminal(RPAREN)
		nonTerminal(thenStmt, Statement)
		zeroOrOne(
			sequence(
				terminal(ELSE)
				nonTerminal(elseStmt, Statement)
			)
		)
		action({ return dress(SIfStmt.make(condition, thenStmt, optionOf(elseStmt))); })
	) */
	protected BUTree<SIfStmt> parseIfStatement() throws ParseException {
		BUTree<? extends SExpr> condition;
		BUTree<? extends SStmt> thenStmt;
		BUTree<? extends SStmt> elseStmt = null;
		int __token;
		run();
		consume(TokenType.IF);
		consume(TokenType.LPAREN);
		pushCallStack(IF_STATEMENT_3);
		condition = parseExpression();
		popCallStack();
		consume(TokenType.RPAREN);
		pushCallStack(IF_STATEMENT_5);
		thenStmt = parseStatement();
		popCallStack();
		__token = getToken(0).kind;
		if (__token == TokenType.ELSE) {
			consume(TokenType.ELSE);
			pushCallStack(IF_STATEMENT_6_1_2);
			elseStmt = parseStatement();
			popCallStack();
		}
		return dress(SIfStmt.make(condition, thenStmt, optionOf(elseStmt)));
	}

	/* sequence(
		action({ run(); })
		terminal(WHILE)
		terminal(LPAREN)
		nonTerminal(condition, Expression)
		terminal(RPAREN)
		nonTerminal(body, Statement)
		action({ return dress(SWhileStmt.make(condition, body)); })
	) */
	protected BUTree<SWhileStmt> parseWhileStatement() throws ParseException {
		BUTree<? extends SExpr> condition;
		BUTree<? extends SStmt> body;
		int __token;
		run();
		consume(TokenType.WHILE);
		consume(TokenType.LPAREN);
		pushCallStack(WHILE_STATEMENT_3);
		condition = parseExpression();
		popCallStack();
		consume(TokenType.RPAREN);
		pushCallStack(WHILE_STATEMENT_5);
		body = parseStatement();
		popCallStack();
		return dress(SWhileStmt.make(condition, body));
	}

	/* sequence(
		action({ run(); })
		terminal(DO)
		nonTerminal(body, Statement)
		terminal(WHILE)
		terminal(LPAREN)
		nonTerminal(condition, Expression)
		terminal(RPAREN)
		terminal(SEMICOLON)
		action({ return dress(SDoStmt.make(body, condition)); })
	) */
	protected BUTree<SDoStmt> parseDoStatement() throws ParseException {
		BUTree<? extends SExpr> condition;
		BUTree<? extends SStmt> body;
		int __token;
		run();
		consume(TokenType.DO);
		pushCallStack(DO_STATEMENT_2);
		body = parseStatement();
		popCallStack();
		consume(TokenType.WHILE);
		consume(TokenType.LPAREN);
		pushCallStack(DO_STATEMENT_5);
		condition = parseExpression();
		popCallStack();
		consume(TokenType.RPAREN);
		consume(TokenType.SEMICOLON);
		return dress(SDoStmt.make(body, condition));
	}

	/* sequence(
		action({ run(); })
		terminal(FOR)
		terminal(LPAREN)
		choice(
			sequence(
				nonTerminal(varExpr, VariableDeclExpression)
				terminal(COLON)
				nonTerminal(expr, Expression)
			)
			sequence(
				zeroOrOne(
					nonTerminal(init, ForInit)
				)
				terminal(SEMICOLON)
				zeroOrOne(
					nonTerminal(expr, Expression)
				)
				terminal(SEMICOLON)
				zeroOrOne(
					nonTerminal(update, ForUpdate)
				)
			)
		)
		terminal(RPAREN)
		nonTerminal(body, Statement)
		action({
			if (varExpr != null)
				return dress(SForeachStmt.make(varExpr, expr, body));
			else
				return dress(SForStmt.make(init, expr, update, body));
		})
	) */
	protected BUTree<? extends SStmt> parseForStatement() throws ParseException {
		BUTree<SVariableDeclarationExpr> varExpr = null;
		BUTree<? extends SExpr> expr = null;
		BUTree<SNodeList> init = null;
		BUTree<SNodeList> update = null;
		BUTree<? extends SStmt> body;
		int __token;
		run();
		consume(TokenType.FOR);
		consume(TokenType.LPAREN);
		switch (predict(FOR_STATEMENT_3)) {
			case 1:
				pushCallStack(FOR_STATEMENT_3_1_1);
				varExpr = parseVariableDeclExpression();
				popCallStack();
				consume(TokenType.COLON);
				pushCallStack(FOR_STATEMENT_3_1_3);
				expr = parseExpression();
				popCallStack();
				break;
			case 2:
				__token = getToken(0).kind;
				if (((__token - 9 & ~63) == 0 && (1L << __token - 9 & (1L << TokenType.ABSTRACT - 9 | 1L << TokenType.BOOLEAN - 9 | 1L << TokenType.BYTE - 9 | 1L << TokenType.CHAR - 9 | 1L << TokenType.DOUBLE - 9 | 1L << TokenType.FALSE - 9 | 1L << TokenType.FINAL - 9 | 1L << TokenType.FLOAT - 9 | 1L << TokenType.INT - 9 | 1L << TokenType.LONG - 9 | 1L << TokenType.NATIVE - 9 | 1L << TokenType.NEW - 9 | 1L << TokenType.NULL - 9 | 1L << TokenType.PRIVATE - 9 | 1L << TokenType.PROTECTED - 9 | 1L << TokenType.PUBLIC - 9 | 1L << TokenType.SHORT - 9 | 1L << TokenType.STATIC - 9 | 1L << TokenType.STRICTFP - 9 | 1L << TokenType.SUPER - 9 | 1L << TokenType.SYNCHRONIZED - 9 | 1L << TokenType.THIS - 9 | 1L << TokenType.TRANSIENT - 9 | 1L << TokenType.TRUE - 9 | 1L << TokenType.VOID - 9 | 1L << TokenType.VOLATILE - 9 | 1L << TokenType.LONG_LITERAL - 9 | 1L << TokenType.INTEGER_LITERAL - 9 | 1L << TokenType.FLOAT_LITERAL - 9 | 1L << TokenType.DOUBLE_LITERAL - 9)) != 0) || ((__token - 78 & ~63) == 0 && (1L << __token - 78 & (1L << TokenType.CHARACTER_LITERAL - 78 | 1L << TokenType.STRING_LITERAL - 78 | 1L << TokenType.LPAREN - 78 | 1L << TokenType.AT - 78 | 1L << TokenType.LT - 78 | 1L << TokenType.BANG - 78 | 1L << TokenType.TILDE - 78 | 1L << TokenType.INCR - 78 | 1L << TokenType.DECR - 78 | 1L << TokenType.PLUS - 78 | 1L << TokenType.MINUS - 78 | 1L << TokenType.NODE_VARIABLE - 78 | 1L << TokenType.IDENTIFIER - 78)) != 0)) {
					pushCallStack(FOR_STATEMENT_3_2_1_1);
					init = parseForInit();
					popCallStack();
				}
				consume(TokenType.SEMICOLON);
				__token = getToken(0).kind;
				if (((__token - 11 & ~63) == 0 && (1L << __token - 11 & (1L << TokenType.BOOLEAN - 11 | 1L << TokenType.BYTE - 11 | 1L << TokenType.CHAR - 11 | 1L << TokenType.DOUBLE - 11 | 1L << TokenType.FALSE - 11 | 1L << TokenType.FLOAT - 11 | 1L << TokenType.INT - 11 | 1L << TokenType.LONG - 11 | 1L << TokenType.NEW - 11 | 1L << TokenType.NULL - 11 | 1L << TokenType.SHORT - 11 | 1L << TokenType.SUPER - 11 | 1L << TokenType.THIS - 11 | 1L << TokenType.TRUE - 11 | 1L << TokenType.VOID - 11 | 1L << TokenType.LONG_LITERAL - 11 | 1L << TokenType.INTEGER_LITERAL - 11 | 1L << TokenType.FLOAT_LITERAL - 11 | 1L << TokenType.DOUBLE_LITERAL - 11)) != 0) || ((__token - 78 & ~63) == 0 && (1L << __token - 78 & (1L << TokenType.CHARACTER_LITERAL - 78 | 1L << TokenType.STRING_LITERAL - 78 | 1L << TokenType.LPAREN - 78 | 1L << TokenType.LT - 78 | 1L << TokenType.BANG - 78 | 1L << TokenType.TILDE - 78 | 1L << TokenType.INCR - 78 | 1L << TokenType.DECR - 78 | 1L << TokenType.PLUS - 78 | 1L << TokenType.MINUS - 78 | 1L << TokenType.NODE_VARIABLE - 78 | 1L << TokenType.IDENTIFIER - 78)) != 0)) {
					pushCallStack(FOR_STATEMENT_3_2_3_1);
					expr = parseExpression();
					popCallStack();
				}
				consume(TokenType.SEMICOLON);
				__token = getToken(0).kind;
				if (((__token - 11 & ~63) == 0 && (1L << __token - 11 & (1L << TokenType.BOOLEAN - 11 | 1L << TokenType.BYTE - 11 | 1L << TokenType.CHAR - 11 | 1L << TokenType.DOUBLE - 11 | 1L << TokenType.FALSE - 11 | 1L << TokenType.FLOAT - 11 | 1L << TokenType.INT - 11 | 1L << TokenType.LONG - 11 | 1L << TokenType.NEW - 11 | 1L << TokenType.NULL - 11 | 1L << TokenType.SHORT - 11 | 1L << TokenType.SUPER - 11 | 1L << TokenType.THIS - 11 | 1L << TokenType.TRUE - 11 | 1L << TokenType.VOID - 11 | 1L << TokenType.LONG_LITERAL - 11 | 1L << TokenType.INTEGER_LITERAL - 11 | 1L << TokenType.FLOAT_LITERAL - 11 | 1L << TokenType.DOUBLE_LITERAL - 11)) != 0) || ((__token - 78 & ~63) == 0 && (1L << __token - 78 & (1L << TokenType.CHARACTER_LITERAL - 78 | 1L << TokenType.STRING_LITERAL - 78 | 1L << TokenType.LPAREN - 78 | 1L << TokenType.LT - 78 | 1L << TokenType.BANG - 78 | 1L << TokenType.TILDE - 78 | 1L << TokenType.INCR - 78 | 1L << TokenType.DECR - 78 | 1L << TokenType.PLUS - 78 | 1L << TokenType.MINUS - 78 | 1L << TokenType.NODE_VARIABLE - 78 | 1L << TokenType.IDENTIFIER - 78)) != 0)) {
					pushCallStack(FOR_STATEMENT_3_2_5_1);
					update = parseForUpdate();
					popCallStack();
				}
				break;
			default:
				throw produceParseException(TokenType.ABSTRACT, TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.DOUBLE, TokenType.FALSE, TokenType.FINAL, TokenType.FLOAT, TokenType.INT, TokenType.LONG, TokenType.NATIVE, TokenType.NEW, TokenType.NULL, TokenType.PRIVATE, TokenType.PROTECTED, TokenType.PUBLIC, TokenType.SHORT, TokenType.STATIC, TokenType.STRICTFP, TokenType.SUPER, TokenType.SYNCHRONIZED, TokenType.THIS, TokenType.TRANSIENT, TokenType.TRUE, TokenType.VOID, TokenType.VOLATILE, TokenType.LONG_LITERAL, TokenType.INTEGER_LITERAL, TokenType.FLOAT_LITERAL, TokenType.DOUBLE_LITERAL, TokenType.CHARACTER_LITERAL, TokenType.STRING_LITERAL, TokenType.LPAREN, TokenType.SEMICOLON, TokenType.AT, TokenType.LT, TokenType.BANG, TokenType.TILDE, TokenType.INCR, TokenType.DECR, TokenType.PLUS, TokenType.MINUS, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER);
		}
		consume(TokenType.RPAREN);
		pushCallStack(FOR_STATEMENT_5);
		body = parseStatement();
		popCallStack();
		if (varExpr != null)
			return dress(SForeachStmt.make(varExpr, expr, body));
		else
			return dress(SForStmt.make(init, expr, update, body));
	}

	/* sequence(
		choice(
			sequence(
				nonTerminal(expr, VariableDeclExpression)
				action({ ret = append(emptyList(), expr); })
			)
			nonTerminal(ret, StatementExpressionList)
		)
		action({ return ret; })
	) */
	protected BUTree<SNodeList> parseForInit() throws ParseException {
		BUTree<SNodeList> ret;
		BUTree<? extends SExpr> expr;
		int __token;
		switch (predict(FOR_INIT_1)) {
			case 1:
				pushCallStack(FOR_INIT_1_1_1);
				expr = parseVariableDeclExpression();
				popCallStack();
				ret = append(emptyList(), expr);
				break;
			case 2:
				pushCallStack(FOR_INIT_1_2);
				ret = parseStatementExpressionList();
				popCallStack();
				break;
			default:
				throw produceParseException(TokenType.ABSTRACT, TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.DOUBLE, TokenType.FALSE, TokenType.FINAL, TokenType.FLOAT, TokenType.INT, TokenType.LONG, TokenType.NATIVE, TokenType.NEW, TokenType.NULL, TokenType.PRIVATE, TokenType.PROTECTED, TokenType.PUBLIC, TokenType.SHORT, TokenType.STATIC, TokenType.STRICTFP, TokenType.SUPER, TokenType.SYNCHRONIZED, TokenType.THIS, TokenType.TRANSIENT, TokenType.TRUE, TokenType.VOID, TokenType.VOLATILE, TokenType.LONG_LITERAL, TokenType.INTEGER_LITERAL, TokenType.FLOAT_LITERAL, TokenType.DOUBLE_LITERAL, TokenType.CHARACTER_LITERAL, TokenType.STRING_LITERAL, TokenType.LPAREN, TokenType.AT, TokenType.LT, TokenType.BANG, TokenType.TILDE, TokenType.INCR, TokenType.DECR, TokenType.PLUS, TokenType.MINUS, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER);
		}
		return ret;
	}

	/* sequence(
		nonTerminal(expr, StatementExpression)
		action({ ret = append(ret, expr); })
		zeroOrMore(
			sequence(
				terminal(COMMA)
				nonTerminal(expr, StatementExpression)
				action({ ret = append(ret, expr); })
			)
		)
		action({ return ret; })
	) */
	protected BUTree<SNodeList> parseStatementExpressionList() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<? extends SExpr> expr;
		int __token;
		pushCallStack(STATEMENT_EXPRESSION_LIST_1);
		expr = parseStatementExpression();
		popCallStack();
		ret = append(ret, expr);
		__token = getToken(0).kind;
		while (__token == TokenType.COMMA) {
			consume(TokenType.COMMA);
			pushCallStack(STATEMENT_EXPRESSION_LIST_2_1_2);
			expr = parseStatementExpression();
			popCallStack();
			ret = append(ret, expr);
			__token = getToken(0).kind;
		}
		return ret;
	}

	/* sequence(
		nonTerminal(ret, StatementExpressionList)
		action({ return ret; })
	) */
	protected BUTree<SNodeList> parseForUpdate() throws ParseException {
		BUTree<SNodeList> ret;
		int __token;
		pushCallStack(FOR_UPDATE_1);
		ret = parseStatementExpressionList();
		popCallStack();
		return ret;
	}

	/* sequence(
		action({ run(); })
		terminal(BREAK)
		zeroOrOne(
			nonTerminal(id, Name)
		)
		terminal(SEMICOLON)
		action({ return dress(SBreakStmt.make(optionOf(id))); })
	) */
	protected BUTree<SBreakStmt> parseBreakStatement() throws ParseException {
		BUTree<SName> id = null;
		int __token;
		run();
		consume(TokenType.BREAK);
		__token = getToken(0).kind;
		if (__token == TokenType.NODE_VARIABLE || __token == TokenType.IDENTIFIER) {
			pushCallStack(BREAK_STATEMENT_2_1);
			id = parseName();
			popCallStack();
		}
		consume(TokenType.SEMICOLON);
		return dress(SBreakStmt.make(optionOf(id)));
	}

	/* sequence(
		action({ run(); })
		terminal(CONTINUE)
		zeroOrOne(
			nonTerminal(id, Name)
		)
		terminal(SEMICOLON)
		action({ return dress(SContinueStmt.make(optionOf(id))); })
	) */
	protected BUTree<SContinueStmt> parseContinueStatement() throws ParseException {
		BUTree<SName> id = null;
		int __token;
		run();
		consume(TokenType.CONTINUE);
		__token = getToken(0).kind;
		if (__token == TokenType.NODE_VARIABLE || __token == TokenType.IDENTIFIER) {
			pushCallStack(CONTINUE_STATEMENT_2_1);
			id = parseName();
			popCallStack();
		}
		consume(TokenType.SEMICOLON);
		return dress(SContinueStmt.make(optionOf(id)));
	}

	/* sequence(
		action({ run(); })
		terminal(RETURN)
		zeroOrOne(
			nonTerminal(expr, Expression)
		)
		terminal(SEMICOLON)
		action({ return dress(SReturnStmt.make(optionOf(expr))); })
	) */
	protected BUTree<SReturnStmt> parseReturnStatement() throws ParseException {
		BUTree<? extends SExpr> expr = null;
		int __token;
		run();
		consume(TokenType.RETURN);
		__token = getToken(0).kind;
		if (((__token - 11 & ~63) == 0 && (1L << __token - 11 & (1L << TokenType.BOOLEAN - 11 | 1L << TokenType.BYTE - 11 | 1L << TokenType.CHAR - 11 | 1L << TokenType.DOUBLE - 11 | 1L << TokenType.FALSE - 11 | 1L << TokenType.FLOAT - 11 | 1L << TokenType.INT - 11 | 1L << TokenType.LONG - 11 | 1L << TokenType.NEW - 11 | 1L << TokenType.NULL - 11 | 1L << TokenType.SHORT - 11 | 1L << TokenType.SUPER - 11 | 1L << TokenType.THIS - 11 | 1L << TokenType.TRUE - 11 | 1L << TokenType.VOID - 11 | 1L << TokenType.LONG_LITERAL - 11 | 1L << TokenType.INTEGER_LITERAL - 11 | 1L << TokenType.FLOAT_LITERAL - 11 | 1L << TokenType.DOUBLE_LITERAL - 11)) != 0) || ((__token - 78 & ~63) == 0 && (1L << __token - 78 & (1L << TokenType.CHARACTER_LITERAL - 78 | 1L << TokenType.STRING_LITERAL - 78 | 1L << TokenType.LPAREN - 78 | 1L << TokenType.LT - 78 | 1L << TokenType.BANG - 78 | 1L << TokenType.TILDE - 78 | 1L << TokenType.INCR - 78 | 1L << TokenType.DECR - 78 | 1L << TokenType.PLUS - 78 | 1L << TokenType.MINUS - 78 | 1L << TokenType.NODE_VARIABLE - 78 | 1L << TokenType.IDENTIFIER - 78)) != 0)) {
			pushCallStack(RETURN_STATEMENT_2_1);
			expr = parseExpression();
			popCallStack();
		}
		consume(TokenType.SEMICOLON);
		return dress(SReturnStmt.make(optionOf(expr)));
	}

	/* sequence(
		action({ run(); })
		terminal(THROW)
		nonTerminal(expr, Expression)
		terminal(SEMICOLON)
		action({ return dress(SThrowStmt.make(expr)); })
	) */
	protected BUTree<SThrowStmt> parseThrowStatement() throws ParseException {
		BUTree<? extends SExpr> expr;
		int __token;
		run();
		consume(TokenType.THROW);
		pushCallStack(THROW_STATEMENT_2);
		expr = parseExpression();
		popCallStack();
		consume(TokenType.SEMICOLON);
		return dress(SThrowStmt.make(expr));
	}

	/* sequence(
		action({ run(); })
		terminal(SYNCHRONIZED)
		terminal(LPAREN)
		nonTerminal(expr, Expression)
		terminal(RPAREN)
		nonTerminal(block, Block)
		action({ return dress(SSynchronizedStmt.make(expr, block)); })
	) */
	protected BUTree<SSynchronizedStmt> parseSynchronizedStatement() throws ParseException {
		BUTree<? extends SExpr> expr;
		BUTree<SBlockStmt> block;
		int __token;
		run();
		consume(TokenType.SYNCHRONIZED);
		consume(TokenType.LPAREN);
		pushCallStack(SYNCHRONIZED_STATEMENT_3);
		expr = parseExpression();
		popCallStack();
		consume(TokenType.RPAREN);
		pushCallStack(SYNCHRONIZED_STATEMENT_5);
		block = parseBlock();
		popCallStack();
		return dress(SSynchronizedStmt.make(expr, block));
	}

	/* sequence(
		action({ run(); })
		terminal(TRY)
		choice(
			sequence(
				nonTerminal(resources, ResourceSpecification)
				nonTerminal(tryBlock, Block)
				zeroOrOne(
					nonTerminal(catchClauses, CatchClauses)
				)
				zeroOrOne(
					sequence(
						terminal(FINALLY)
						nonTerminal(finallyBlock, Block)
					)
				)
			)
			sequence(
				nonTerminal(tryBlock, Block)
				choice(
					sequence(
						nonTerminal(catchClauses, CatchClauses)
						zeroOrOne(
							sequence(
								terminal(FINALLY)
								nonTerminal(finallyBlock, Block)
							)
						)
					)
					sequence(
						terminal(FINALLY)
						nonTerminal(finallyBlock, Block)
					)
				)
			)
		)
		action({ return dress(STryStmt.make(ensureNotNull(resources), trailingSemiColon.value, tryBlock, ensureNotNull(catchClauses), optionOf(finallyBlock))); })
	) */
	protected BUTree<STryStmt> parseTryStatement() throws ParseException {
		BUTree<SNodeList> resources = null;
		ByRef<Boolean> trailingSemiColon = new ByRef<Boolean>(false);
		BUTree<SBlockStmt> tryBlock;
		BUTree<SBlockStmt> finallyBlock = null;
		BUTree<SNodeList> catchClauses = null;
		int __token;
		run();
		consume(TokenType.TRY);
		__token = getToken(0).kind;
		if (__token == TokenType.LPAREN) {
			pushCallStack(TRY_STATEMENT_2_1_1);
			resources = parseResourceSpecification(trailingSemiColon);
			popCallStack();
			pushCallStack(TRY_STATEMENT_2_1_2);
			tryBlock = parseBlock();
			popCallStack();
			__token = getToken(0).kind;
			if (__token == TokenType.CATCH) {
				pushCallStack(TRY_STATEMENT_2_1_3_1);
				catchClauses = parseCatchClauses();
				popCallStack();
			}
			__token = getToken(0).kind;
			if (__token == TokenType.FINALLY) {
				consume(TokenType.FINALLY);
				pushCallStack(TRY_STATEMENT_2_1_4_1_2);
				finallyBlock = parseBlock();
				popCallStack();
			}
		} else if (__token == TokenType.LBRACE) {
			pushCallStack(TRY_STATEMENT_2_2_1);
			tryBlock = parseBlock();
			popCallStack();
			__token = getToken(0).kind;
			if (__token == TokenType.CATCH) {
				pushCallStack(TRY_STATEMENT_2_2_2_1_1);
				catchClauses = parseCatchClauses();
				popCallStack();
				__token = getToken(0).kind;
				if (predict(TRY_STATEMENT_2_2_2_1_2) == 1) {
					consume(TokenType.FINALLY);
					pushCallStack(TRY_STATEMENT_2_2_2_1_2_1_2);
					finallyBlock = parseBlock();
					popCallStack();
				}
			} else if (__token == TokenType.FINALLY) {
				consume(TokenType.FINALLY);
				pushCallStack(TRY_STATEMENT_2_2_2_2_2);
				finallyBlock = parseBlock();
				popCallStack();
			} else
				throw produceParseException(TokenType.CATCH, TokenType.FINALLY);
		} else
			throw produceParseException(TokenType.LPAREN, TokenType.LBRACE);
		return dress(STryStmt.make(ensureNotNull(resources), trailingSemiColon.value, tryBlock, ensureNotNull(catchClauses), optionOf(finallyBlock)));
	}

	/* sequence(
		oneOrMore(
			nonTerminal(catchClause, CatchClause)
			action({ catchClauses = append(catchClauses, catchClause); })
		)
		action({ return catchClauses; })
	) */
	protected BUTree<SNodeList> parseCatchClauses() throws ParseException {
		BUTree<SNodeList> catchClauses = emptyList();
		BUTree<SCatchClause> catchClause;
		int __token;
		do {
			pushCallStack(CATCH_CLAUSES_1_1);
			catchClause = parseCatchClause();
			popCallStack();
			catchClauses = append(catchClauses, catchClause);
			__token = getToken(0).kind;
		} while (__token == TokenType.CATCH);
		return catchClauses;
	}

	/* sequence(
		action({ run(); })
		terminal(CATCH)
		terminal(LPAREN)
		nonTerminal(param, CatchFormalParameter)
		terminal(RPAREN)
		nonTerminal(catchBlock, Block)
		action({ return dress(SCatchClause.make(param, catchBlock)); })
	) */
	protected BUTree<SCatchClause> parseCatchClause() throws ParseException {
		BUTree<SFormalParameter> param;
		BUTree<SBlockStmt> catchBlock;
		int __token;
		run();
		consume(TokenType.CATCH);
		consume(TokenType.LPAREN);
		pushCallStack(CATCH_CLAUSE_3);
		param = parseCatchFormalParameter();
		popCallStack();
		consume(TokenType.RPAREN);
		pushCallStack(CATCH_CLAUSE_5);
		catchBlock = parseBlock();
		popCallStack();
		return dress(SCatchClause.make(param, catchBlock));
	}

	/* sequence(
		action({ run(); })
		nonTerminal(modifiers, Modifiers)
		nonTerminal(exceptType, QualifiedType)
		action({ exceptTypes = append(exceptTypes, exceptType); })
		zeroOrOne(
			action({ lateRun(); })
			oneOrMore(
				sequence(
					terminal(BIT_OR)
					nonTerminal(exceptType, AnnotatedQualifiedType)
					action({ exceptTypes = append(exceptTypes, exceptType); })
				)
			)
			action({ exceptType = dress(SUnionType.make(exceptTypes)); })
		)
		nonTerminal(exceptId, VariableDeclaratorId)
		action({ return dress(SFormalParameter.make(modifiers, exceptType, false, emptyList(), optionOf(exceptId), false, none())); })
	) */
	protected BUTree<SFormalParameter> parseCatchFormalParameter() throws ParseException {
		BUTree<SNodeList> modifiers;
		BUTree<? extends SType> exceptType;
		BUTree<SNodeList> exceptTypes = emptyList();
		BUTree<SVariableDeclaratorId> exceptId;
		int __token;
		run();
		pushCallStack(CATCH_FORMAL_PARAMETER_1);
		modifiers = parseModifiers();
		popCallStack();
		pushCallStack(CATCH_FORMAL_PARAMETER_2);
		exceptType = parseQualifiedType(null);
		popCallStack();
		exceptTypes = append(exceptTypes, exceptType);
		__token = getToken(0).kind;
		if (__token == TokenType.BIT_OR) {
			lateRun();
			do {
				consume(TokenType.BIT_OR);
				pushCallStack(CATCH_FORMAL_PARAMETER_3_1_1_2);
				exceptType = parseAnnotatedQualifiedType();
				popCallStack();
				exceptTypes = append(exceptTypes, exceptType);
				__token = getToken(0).kind;
			} while (__token == TokenType.BIT_OR);
			exceptType = dress(SUnionType.make(exceptTypes));
		}
		pushCallStack(CATCH_FORMAL_PARAMETER_4);
		exceptId = parseVariableDeclaratorId();
		popCallStack();
		return dress(SFormalParameter.make(modifiers, exceptType, false, emptyList(), optionOf(exceptId), false, none()));
	}

	/* sequence(
		terminal(LPAREN)
		nonTerminal(var, VariableDeclExpression)
		action({ vars = append(vars, var); })
		zeroOrMore(
			sequence(
				terminal(SEMICOLON)
				nonTerminal(var, VariableDeclExpression)
				action({ vars = append(vars, var); })
			)
		)
		zeroOrOne(
			terminal(SEMICOLON)
			action({ trailingSemiColon.value = true; })
		)
		terminal(RPAREN)
		action({ return vars; })
	) */
	protected BUTree<SNodeList> parseResourceSpecification(ByRef<Boolean> trailingSemiColon) throws ParseException {
		BUTree<SNodeList> vars = emptyList();
		BUTree<SVariableDeclarationExpr> var;
		int __token;
		consume(TokenType.LPAREN);
		pushCallStack(RESOURCE_SPECIFICATION_2);
		var = parseVariableDeclExpression();
		popCallStack();
		vars = append(vars, var);
		__token = getToken(0).kind;
		while (predict(RESOURCE_SPECIFICATION_3) == 1) {
			consume(TokenType.SEMICOLON);
			pushCallStack(RESOURCE_SPECIFICATION_3_1_2);
			var = parseVariableDeclExpression();
			popCallStack();
			vars = append(vars, var);
			__token = getToken(0).kind;
		}
		__token = getToken(0).kind;
		if (__token == TokenType.SEMICOLON) {
			consume(TokenType.SEMICOLON);
			trailingSemiColon.value = true;
		}
		consume(TokenType.RPAREN);
		return vars;
	}

	/* sequence(
		zeroOrMore(
			nonTerminal(annotation, Annotation)
			action({ annotations = append(annotations, annotation); })
		)
		action({ return annotations; })
	) */
	protected BUTree<SNodeList> parseAnnotations() throws ParseException {
		BUTree<SNodeList> annotations = emptyList();
		BUTree<? extends SAnnotationExpr> annotation;
		int __token;
		__token = getToken(0).kind;
		while (__token == TokenType.AT) {
			pushCallStack(ANNOTATIONS_1_1);
			annotation = parseAnnotation();
			popCallStack();
			annotations = append(annotations, annotation);
			__token = getToken(0).kind;
		}
		return annotations;
	}

	/* sequence(
		choice(
			nonTerminal(ret, NormalAnnotation)
			nonTerminal(ret, MarkerAnnotation)
			nonTerminal(ret, SingleElementAnnotation)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SAnnotationExpr> parseAnnotation() throws ParseException {
		BUTree<? extends SAnnotationExpr> ret;
		int __token;
		switch (predict(ANNOTATION_1)) {
			case 1:
				pushCallStack(ANNOTATION_1_1);
				ret = parseNormalAnnotation();
				popCallStack();
				break;
			case 2:
				pushCallStack(ANNOTATION_1_2);
				ret = parseMarkerAnnotation();
				popCallStack();
				break;
			case 3:
				pushCallStack(ANNOTATION_1_3);
				ret = parseSingleElementAnnotation();
				popCallStack();
				break;
			default:
				throw produceParseException(TokenType.AT);
		}
		return ret;
	}

	/* sequence(
		action({ run(); })
		terminal(AT)
		nonTerminal(name, QualifiedName)
		terminal(LPAREN)
		zeroOrOne(
			nonTerminal(pairs, ElementValuePairList)
		)
		terminal(RPAREN)
		action({ return dress(SNormalAnnotationExpr.make(name, ensureNotNull(pairs))); })
	) */
	protected BUTree<SNormalAnnotationExpr> parseNormalAnnotation() throws ParseException {
		BUTree<SQualifiedName> name;
		BUTree<SNodeList> pairs = null;
		int __token;
		run();
		consume(TokenType.AT);
		pushCallStack(NORMAL_ANNOTATION_2);
		name = parseQualifiedName();
		popCallStack();
		consume(TokenType.LPAREN);
		__token = getToken(0).kind;
		if (__token == TokenType.NODE_VARIABLE || __token == TokenType.IDENTIFIER) {
			pushCallStack(NORMAL_ANNOTATION_4_1);
			pairs = parseElementValuePairList();
			popCallStack();
		}
		consume(TokenType.RPAREN);
		return dress(SNormalAnnotationExpr.make(name, ensureNotNull(pairs)));
	}

	/* sequence(
		action({ run(); })
		terminal(AT)
		nonTerminal(name, QualifiedName)
		action({ return dress(SMarkerAnnotationExpr.make(name)); })
	) */
	protected BUTree<SMarkerAnnotationExpr> parseMarkerAnnotation() throws ParseException {
		BUTree<SQualifiedName> name;
		int __token;
		run();
		consume(TokenType.AT);
		pushCallStack(MARKER_ANNOTATION_2);
		name = parseQualifiedName();
		popCallStack();
		return dress(SMarkerAnnotationExpr.make(name));
	}

	/* sequence(
		action({ run(); })
		terminal(AT)
		nonTerminal(name, QualifiedName)
		terminal(LPAREN)
		nonTerminal(value, ElementValue)
		terminal(RPAREN)
		action({ return dress(SSingleMemberAnnotationExpr.make(name, value)); })
	) */
	protected BUTree<SSingleMemberAnnotationExpr> parseSingleElementAnnotation() throws ParseException {
		BUTree<SQualifiedName> name;
		BUTree<? extends SExpr> value;
		int __token;
		run();
		consume(TokenType.AT);
		pushCallStack(SINGLE_ELEMENT_ANNOTATION_2);
		name = parseQualifiedName();
		popCallStack();
		consume(TokenType.LPAREN);
		pushCallStack(SINGLE_ELEMENT_ANNOTATION_4);
		value = parseElementValue();
		popCallStack();
		consume(TokenType.RPAREN);
		return dress(SSingleMemberAnnotationExpr.make(name, value));
	}

	/* sequence(
		nonTerminal(pair, ElementValuePair)
		action({ ret = append(ret, pair); })
		zeroOrMore(
			sequence(
				terminal(COMMA)
				nonTerminal(pair, ElementValuePair)
				action({ ret = append(ret, pair); })
			)
		)
		action({ return ret; })
	) */
	protected BUTree<SNodeList> parseElementValuePairList() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<SMemberValuePair> pair;
		int __token;
		pushCallStack(ELEMENT_VALUE_PAIR_LIST_1);
		pair = parseElementValuePair();
		popCallStack();
		ret = append(ret, pair);
		__token = getToken(0).kind;
		while (__token == TokenType.COMMA) {
			consume(TokenType.COMMA);
			pushCallStack(ELEMENT_VALUE_PAIR_LIST_2_1_2);
			pair = parseElementValuePair();
			popCallStack();
			ret = append(ret, pair);
			__token = getToken(0).kind;
		}
		return ret;
	}

	/* sequence(
		action({ run(); })
		nonTerminal(name, Name)
		terminal(ASSIGN)
		nonTerminal(value, ElementValue)
		action({ return dress(SMemberValuePair.make(name, value)); })
	) */
	protected BUTree<SMemberValuePair> parseElementValuePair() throws ParseException {
		BUTree<SName> name;
		BUTree<? extends SExpr> value;
		int __token;
		run();
		pushCallStack(ELEMENT_VALUE_PAIR_1);
		name = parseName();
		popCallStack();
		consume(TokenType.ASSIGN);
		pushCallStack(ELEMENT_VALUE_PAIR_3);
		value = parseElementValue();
		popCallStack();
		return dress(SMemberValuePair.make(name, value));
	}

	/* sequence(
		choice(
			nonTerminal(ret, ConditionalExpression)
			nonTerminal(ret, ElementValueArrayInitializer)
			nonTerminal(ret, Annotation)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parseElementValue() throws ParseException {
		BUTree<? extends SExpr> ret;
		int __token;
		__token = getToken(0).kind;
		if (((__token - 11 & ~63) == 0 && (1L << __token - 11 & (1L << TokenType.BOOLEAN - 11 | 1L << TokenType.BYTE - 11 | 1L << TokenType.CHAR - 11 | 1L << TokenType.DOUBLE - 11 | 1L << TokenType.FALSE - 11 | 1L << TokenType.FLOAT - 11 | 1L << TokenType.INT - 11 | 1L << TokenType.LONG - 11 | 1L << TokenType.NEW - 11 | 1L << TokenType.NULL - 11 | 1L << TokenType.SHORT - 11 | 1L << TokenType.SUPER - 11 | 1L << TokenType.THIS - 11 | 1L << TokenType.TRUE - 11 | 1L << TokenType.VOID - 11 | 1L << TokenType.LONG_LITERAL - 11 | 1L << TokenType.INTEGER_LITERAL - 11 | 1L << TokenType.FLOAT_LITERAL - 11 | 1L << TokenType.DOUBLE_LITERAL - 11)) != 0) || ((__token - 78 & ~63) == 0 && (1L << __token - 78 & (1L << TokenType.CHARACTER_LITERAL - 78 | 1L << TokenType.STRING_LITERAL - 78 | 1L << TokenType.LPAREN - 78 | 1L << TokenType.LT - 78 | 1L << TokenType.BANG - 78 | 1L << TokenType.TILDE - 78 | 1L << TokenType.INCR - 78 | 1L << TokenType.DECR - 78 | 1L << TokenType.PLUS - 78 | 1L << TokenType.MINUS - 78 | 1L << TokenType.NODE_VARIABLE - 78 | 1L << TokenType.IDENTIFIER - 78)) != 0)) {
			pushCallStack(ELEMENT_VALUE_1_1);
			ret = parseConditionalExpression();
			popCallStack();
		} else if (__token == TokenType.LBRACE) {
			pushCallStack(ELEMENT_VALUE_1_2);
			ret = parseElementValueArrayInitializer();
			popCallStack();
		} else if (__token == TokenType.AT) {
			pushCallStack(ELEMENT_VALUE_1_3);
			ret = parseAnnotation();
			popCallStack();
		} else
			throw produceParseException(TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.DOUBLE, TokenType.FALSE, TokenType.FLOAT, TokenType.INT, TokenType.LONG, TokenType.NEW, TokenType.NULL, TokenType.SHORT, TokenType.SUPER, TokenType.THIS, TokenType.TRUE, TokenType.VOID, TokenType.LONG_LITERAL, TokenType.INTEGER_LITERAL, TokenType.FLOAT_LITERAL, TokenType.DOUBLE_LITERAL, TokenType.CHARACTER_LITERAL, TokenType.STRING_LITERAL, TokenType.LPAREN, TokenType.LBRACE, TokenType.AT, TokenType.LT, TokenType.BANG, TokenType.TILDE, TokenType.INCR, TokenType.DECR, TokenType.PLUS, TokenType.MINUS, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER);
		return ret;
	}

	/* sequence(
		action({ run(); })
		terminal(LBRACE)
		zeroOrOne(
			nonTerminal(values, ElementValueList)
		)
		zeroOrOne(
			terminal(COMMA)
			action({ trailingComma = true; })
		)
		terminal(RBRACE)
		action({ return dress(SArrayInitializerExpr.make(ensureNotNull(values), trailingComma)); })
	) */
	protected BUTree<? extends SExpr> parseElementValueArrayInitializer() throws ParseException {
		BUTree<SNodeList> values = null;
		boolean trailingComma = false;
		int __token;
		run();
		consume(TokenType.LBRACE);
		__token = getToken(0).kind;
		if (((__token - 11 & ~63) == 0 && (1L << __token - 11 & (1L << TokenType.BOOLEAN - 11 | 1L << TokenType.BYTE - 11 | 1L << TokenType.CHAR - 11 | 1L << TokenType.DOUBLE - 11 | 1L << TokenType.FALSE - 11 | 1L << TokenType.FLOAT - 11 | 1L << TokenType.INT - 11 | 1L << TokenType.LONG - 11 | 1L << TokenType.NEW - 11 | 1L << TokenType.NULL - 11 | 1L << TokenType.SHORT - 11 | 1L << TokenType.SUPER - 11 | 1L << TokenType.THIS - 11 | 1L << TokenType.TRUE - 11 | 1L << TokenType.VOID - 11 | 1L << TokenType.LONG_LITERAL - 11 | 1L << TokenType.INTEGER_LITERAL - 11 | 1L << TokenType.FLOAT_LITERAL - 11 | 1L << TokenType.DOUBLE_LITERAL - 11)) != 0) || ((__token - 78 & ~63) == 0 && (1L << __token - 78 & (1L << TokenType.CHARACTER_LITERAL - 78 | 1L << TokenType.STRING_LITERAL - 78 | 1L << TokenType.LPAREN - 78 | 1L << TokenType.LBRACE - 78 | 1L << TokenType.AT - 78 | 1L << TokenType.LT - 78 | 1L << TokenType.BANG - 78 | 1L << TokenType.TILDE - 78 | 1L << TokenType.INCR - 78 | 1L << TokenType.DECR - 78 | 1L << TokenType.PLUS - 78 | 1L << TokenType.MINUS - 78 | 1L << TokenType.NODE_VARIABLE - 78 | 1L << TokenType.IDENTIFIER - 78)) != 0)) {
			pushCallStack(ELEMENT_VALUE_ARRAY_INITIALIZER_2_1);
			values = parseElementValueList();
			popCallStack();
		}
		__token = getToken(0).kind;
		if (__token == TokenType.COMMA) {
			consume(TokenType.COMMA);
			trailingComma = true;
		}
		consume(TokenType.RBRACE);
		return dress(SArrayInitializerExpr.make(ensureNotNull(values), trailingComma));
	}

	/* sequence(
		nonTerminal(value, ElementValue)
		action({ ret = append(ret, value); })
		zeroOrMore(
			sequence(
				terminal(COMMA)
				nonTerminal(value, ElementValue)
				action({ ret = append(ret, value); })
			)
		)
		action({ return ret; })
	) */
	protected BUTree<SNodeList> parseElementValueList() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<? extends SExpr> value;
		int __token;
		pushCallStack(ELEMENT_VALUE_LIST_1);
		value = parseElementValue();
		popCallStack();
		ret = append(ret, value);
		__token = getToken(0).kind;
		while (predict(ELEMENT_VALUE_LIST_2) == 1) {
			consume(TokenType.COMMA);
			pushCallStack(ELEMENT_VALUE_LIST_2_1_2);
			value = parseElementValue();
			popCallStack();
			ret = append(ret, value);
			__token = getToken(0).kind;
		}
		return ret;
	}

	private static final String serializedGrammar = "" + 
	"\uACEF\7\u7374\47\u6F74\u6730\u6A6E\u6176\u6F30\u6970\u7467\u7270\u616E\u2E72\u6174\u7367\u7230" + 
	"\u616E\u6C30\u4774\u616F\u6D63\u722D\u04FD\u8CAD\u82F5\u2C0E\2\u7872\u7704\u03F9\u7374\54\u6F74" + 
	"\u6730\u6A6E\u6176\u6F30\u6970\u7467\u7270\u616E\u2E72\u6174\u7367\u7230\u616E\u6C30\u4774\u616F" + 
	"\u6D63\u7255\u7463\u7467\uD348\uAFCA\uB5F1\u0627\u0C02\172\u7079\u0E02\u0101\uFF02\2\u1702\u0201" + 
	"\1\uFF7A\u7373\200\4\u7710\3\2\2\1\1\1\1\u7875\u7102\u7E02\u0279\u0E02\u0301\uFF02\2\u1802\u0501" + 
	"\1\uFF7A\u7373\200\4\u7710\5\3\2\1\1\1\1\u7875\u7102\u7E02\u0279\u0E02\u0501\uFF02\2\u1402\u0401" + 
	"\1\uFF7A\u7373\200\4\u7710\7\1\2\34\11\1\1\u7875\u7102\u7E02\u0279\u0E02\u0602\u0202\u0101\1\1\1" + 
	"\uFF7A\u7373\200\4\u7710\11\1\2\26\10\1\1\u7875\u7102\u7E02\u0279\u0E02\u0901\uFF02\2\u1E02\u0B01" + 
	"\1\uFF7A\u7373\200\4\u7710\13\5\2\1\1\1\1\u7875\u7102\u7E02\u0279\u0E02\u0B01\uFF02\2\u1402\u0A01" + 
	"\1\uFF7A\u7373\200\4\u7710\15\1\2\57\17\1\1\u7875\u7102\u7E02\u0279\u0E02\u0C02\u0402\u0101\1\1" + 
	"\1\uFF7A\u7373\200\4\u7710\17\1\2\26\16\1\1\u7875\u7102\u7E02\u0279\u0E02\u0F01\uFF02\2\u2602\u1101" + 
	"\1\uFF7A\u7373\200\4\u7710\21\7\2\1\1\1\1\u7875\u7102\u7E02\u0279\u0E02\u1101\uFF02\2\u1402\u1001" + 
	"\1\uFF7A\u7373\200\4\u7710\23\1\2\36\25\1\1\u7875\u7102\u7E02\u0279\u0E02\u1202\u0602\u0101\1\1" + 
	"\1\uFF7A\u7373\200\4\u7710\25\1\2\26\24\1\1\u7875\u7102\u7E02\u0279\u0E02\u1501\uFF02\2\u9202\u1701" + 
	"\1\uFF7A\u7373\200\4\u7710\27\11\2\1\1\1\1\u7875\u7102\u7E02\u0279\u0E02\u1701\uFF02\2\u1402\u1601" + 
	"\1\uFF7A\u7373\200\4\u7710\31\1\2\36\33\1\1\u7875\u7102\u7E02\u0279\u0E02\u1802\u0802\u0101\1\1" + 
	"\1\uFF7A\u7373\200\4\u7710\33\1\2\70\34\1\1\u7875\u7102\u7E02\u0279\u0E02\u1B01\uFF02\2\u1402\u1901" + 
	"\1\uFF7A\u7373\200\4\u7710\35\1\2\36\37\1\1\u7875\u7102\u7E02\u0279\u0E02\u1C02\u0902\u0101\1\1" + 
	"\1\uFF7A\u7373\200\4\u7710\37\1\2\60\40\1\1\u7875\u7102\u7E02\u0279\u0E02\u1F01\uFF02\2\u1402\u1D01" + 
	"\1\uFF7A\u7373\200\4\u7710\41\1\2\36\43\1\1\u7875\u7102\u7E02\u0279\u0E02\u2002\u0A02\u0101\1\1" + 
	"\1\uFF7A\u7373\200\4\u7710\43\1\2\51\44\1\1\u7875\u7102\u7E02\u0279\u0E02\u2301\uFF02\2\u1402\u2101" + 
	"\1\uFF7A\u7373\200\4\u7710\45\1\2\45\47\1\1\u7875\u7102\u7E02\u0279\u0E02\u2402\u0B02\u0101\1\1" + 
	"\1\uFF7A\u7373\200\4\u7710\47\1\2\26\46\1\1\u7875\u7102\u7E02\u0279\u0E02\u2701\uFF02\2\u3902\u2901" + 
	"\1\uFF7A\u7373\200\4\u7710\51\16\2\1\1\1\1\u7875\u7102\u7E02\u0279\u0E02\u2901\uFF02\2\u1402\u2801" + 
	"\1\uFF7A\u7373\200\4\u7710\53\1\2\53\55\1\1\u7875\u7102\u7E02\u0279\u0E02\u2A02\u0D02\u0101\1\1" + 
	"\1\uFF7A\u7373\200\4\u7710\55\1\2\26\54\1\1\u7875\u7102\u7E02\u0279\u0E02\u2D01\uFF02\2\u3D02\u2F01" + 
	"\1\uFF7A\u7373\200\4\u7710\57\20\2\1\1\1\1\u7875\u7102\u7E02\u0279\u0E02\u2F01\uFF02\2\u1402\u2E01" + 
	"\1\uFF7A\u7373\200\4\u7710\61\1\2\174\63\1\1\u7875\u7102\u7E02\u0279\u0E02\u3002\u0F02\u0101\1\1" + 
	"\1\uFF7A\u7373\200\4\u7710\63\1\2\26\62\1\1\u7875\u7102\u7E02\u0279\u0E02\u3301\uFF02\2\u4C02\u3501" + 
	"\1\uFF7A\u7373\200\4\u7710\65\22\2\1\1\1\1\u7875\u7102\u7E02\u0279\u0E02\u3501\uFF02\2\u1402\u3401" + 
	"\1\uFF7A\u7373\200\4\u7710\67\1\2\224\71\1\1\u7875\u7102\u7E02\u0279\u0E02\u3602\u1102\u0101\1\1" + 
	"\1\uFF7A\u7373\200\4\u7710\71\1\2\101\72\1\1\u7875\u7102\u7E02\u0279\u0E02\u3901\uFF02\2\u1402\u3701" + 
	"\1\uFF7A\u7373\200\4\u7710\73\1\2\114\75\1\1\u7875\u7102\u7E02\u0279\u0E02\u3A02\u1202\u0101\1\1" + 
	"\1\uFF7A\u7373\200\4\u7710\75\1\2\26\74\1\1\u7875\u7102\u7E02\u0279\u0E02\u3D01\uFF02\2\u4B02\u3F01" + 
	"\1\uFF7A\u7373\200\4\u7710\77\25\2\1\1\1\1\u7875\u7102\u7E02\u0279\u0E02\u3F01\uFF02\2\u1402\u3E01" + 
	"\1\uFF7A\u7373\200\4\u7710\101\1\2\1\1\2\102\u7875\u7102\u7E02\u0279\u0E02\u4002\u1402\u0101\1\1" + 
	"\1\uFF7A\u7373\200\4\u7710\103\1\2\1\1\205\104\u7875\u7102\u7E02\u0279\u0E02\u4202\u1502\u0101\1" + 
	"\1\1\uFF7A\u7373\200\4\u7710\105\1\2\1\1\204\106\u7875\u7102\u7E02\u0279\u0E02\u4402\u1602\u0101" + 
	"\1\1\1\uFF7A\u7373\200\4\u7714\107\1\4\111\112\1\1\1\1\u7875\u7102\u7E02\u0279\u0E02\u4602\u1702" + 
	"\u0101\1\1\1\uFF7A\u7373\200\4\u7710\111\1\2\33\113\1\1\u7875\u7102\u7E02\u0279\u0E02\u4901\uFF02" + 
	"\2\u1802\u4801\1\uFF7A\u7373\200\4\u7710\113\1\2\35\114\1\1\u7875\u7102\u7E02\u0279\u0E02\u4B01" + 
	"\uFF02\2\u1402\u4701\1\uFF7A\u7373\200\4\u7710\115\1\2\224\117\1\1\u7875\u7102\u7E02\u0279\u0E02" + 
	"\u4C02\u1802\u0101\1\1\1\uFF7A\u7373\200\4\u7710\117\1\2\1\1\54\120\u7875\u7102\u7E02\u0279\u0E02" + 
	"\u4F01\uFF02\2\u4A02\u5001\1\uFF7A\u7373\200\4\u7710\121\1\2\1\1\130\116\u7875\u7102\u7E02\u0279" + 
	"\u1202\u5101\uFF02\u0202\u5102\u5301\1\1\1\uFF7A\u7373\200\4\u7710\123\33\2\1\1\1\1\u7875\u7102" + 
	"\u7E02\u0279\u0E02\u5301\uFF02\2\u1A02\u5101\1\uFF7A\u7373\200\4\u7710\125\1\2\1\1\44\127\u7875" + 
	"\u7102\u7E02\u0279\u0E02\u5402\u1A02\u0101\1\1\1\uFF7A\u7373\200\4\u7714\127\1\4\130\131\1\1\1\1" + 
	"\u7875\u7102\u7E02\u0279\u0E02\u5701\uFF02\2\u4A02\u5901\1\uFF7A\u7373\200\4\u7710\131\1\2\1\1\62" + 
	"\130\u7875\u7102\u7E02\u0279\u1202\u5901\uFF02\u0202\u5902\u5B01\1\1\1\uFF7A\u7373\200\4\u7710\133" + 
	"\1\2\1\1\130\126\u7875\u7102\u7E02\u0279\u0E02\u5B01\uFF02\u0101\1\uFF02\u5802\u5B7A\u7373\200\4" + 
	"\u7710\135\1\2\1\1\154\133\u7875\u7102\u7E02\u0279\u1202\u5D01\uFF02\u0202\u5D02\u5F01\1\1\1\uFF7A" + 
	"\u7373\200\4\u7710\137\35\2\1\1\1\1\u7875\u7102\u7E02\u0279\u0E02\u5F01\uFF02\2\u1E02\u5D01\1\uFF7A" + 
	"\u7373\200\4\u7714\141\1\4\142\143\1\1\1\1\u7875\u7102\u7E02\u0279\u0E02\u6002\u1C02\u0101\1\1\1" + 
	"\uFF7A\u7373\200\4\u772C\143\1\20\1\144\145\146\147\150\151\152\153\154\155\156\157\160\1\1\1\1" + 
	"\u7875\u7102\u7E02\u0279\u0E02\u6301\uFF02\u0101\1\uFF02\u2D02\u5F7A\u7373\200\4\u7710\145\1\2\1" + 
	"\1\56\141\u7875\u7102\u7E02\u0279\u0E02\u6501\uFF02\u0101\1\uFF02\u2B02\u5F7A\u7373\200\4\u7710" + 
	"\147\1\2\1\1\13\141\u7875\u7102\u7E02\u0279\u0E02\u6701\uFF02\u0101\1\uFF02\u1402\u5F7A\u7373\200" + 
	"\4\u7710\151\1\2\1\1\62\141\u7875\u7102\u7E02\u0279\u0E02\u6901\uFF02\u0101\1\uFF02\u1B02\u5F7A" + 
	"\u7373\200\4\u7710\153\1\2\1\1\72\141\u7875\u7102\u7E02\u0279\u0E02\u6B01\uFF02\u0101\1\uFF02\u3C02" + 
	"\u5F7A\u7373\200\4\u7710\155\1\2\1\1\66\141\u7875\u7102\u7E02\u0279\u0E02\u6D01\uFF02\u0101\1\uFF02" + 
	"\u2702\u5F7A\u7373\200\4\u7710\157\1\2\1\1\63\141\u7875\u7102\u7E02\u0279\u0E02\u6F01\uFF02\2\u9302" + 
	"\u6001\1\uFF7A\u7373\200\4\u7714\161\1\4\162\163\1\1\1\1\u7875\u7102\u7E02\u0279\u0E02\u7002\u1D02" + 
	"\u0101\1\1\1\uFF7A\u7373\200\4\u772A\163\1\17\1\164\165\166\167\170\171\172\173\174\175\176\177" + 
	"\1\1\1\1\u7875\u7102\u7E02\u0279\u0E02\u7301\uFF02\u0101\1\uFF02\u2D02\u6F7A\u7373\200\4\u7710\165" + 
	"\1\2\1\1\56\161\u7875\u7102\u7E02\u0279\u0E02\u7501\uFF02\u0101\1\uFF02\u2B02\u6F7A\u7373\200\4" + 
	"\u7710\167\1\2\1\1\13\161\u7875\u7102\u7E02\u0279\u0E02\u7701\uFF02\u0101\1\uFF02\u3002\u6F7A\u7373" + 
	"\200\4\u7710\171\1\2\1\1\35\161\u7875\u7102\u7E02\u0279\u0E02\u7901\uFF02\u0101\1\uFF02\u3802\u6F7A" + 
	"\u7373\200\4\u7710\173\1\2\1\1\76\161\u7875\u7102\u7E02\u0279\u0E02\u7B01\uFF02\u0101\1\uFF02\u3402" + 
	"\u6F7A\u7373\200\4\u7710\175\1\2\1\1\51\161\u7875\u7102\u7E02\u0279\u0E02\u7D01\uFF02\u0101\1\uFF02" + 
	"\u3102\u6F7A\u7373\200\4\u7710\177\1\2\225\161\1\1\u7875\u7102\u7E02\u0279\u1402\u7F01\uFF02\u0401" + 
	"\uFF02\u8002\u8201\1\1\1\uFF7A\u7373\200\4\u7710\201\40\2\1\1\1\1\u7875\u7102\u7E02\u0279\u0E02" + 
	"\u8101\uFF02\u0101\1\uFF02\u5602\u7F7A\u7373\200\4\u7710\203\1\2\36\204\1\1\u7875\u7102\u7E02\u0279" + 
	"\u1602\u8301\uFF02\u0501\uFF02\u8302\u8402\u8601\1\1\1\uFF7A\u7373\200\4\u7710\205\1\2\41\201\1" + 
	"\1\u7875\u7102\u7E02\u0279\u0E02\u8501\uFF02\2\u2202\u8001\1\uFF7A\u7373\200\4\u7710\207\1\2\46" + 
	"\201\1\1\u7875\u7102\u7E02\u0279\u1402\u8701\uFF02\u0401\uFF02\u8902\u9301\1\1\1\uFF7A\u7373\200" + 
	"\4\u7710\211\41\2\1\1\1\1\u7875\u7102\u7E02\u0279\u0E02\u8901\uFF02\2\u2B02\u8801\1\uFF7A\u7373" + 
	"\200\4\u7710\213\1\2\1\1\23\214\u7875\u7102\u7E02\u0279\u0E02\u8B01\uFF02\2\u4B02\u8C01\1\uFF7A" + 
	"\u7373\200\4\u7714\215\1\4\216\217\1\1\1\1\u7875\u7102\u7E02\u0279\u1202\u8D01\uFF02\u0202\u8E02" + 
	"\u9001\1\1\1\uFF7A\u7373\200\4\u7710\217\1\2\52\216\1\1\u7875\u7102\u7E02\u0279\u1202\u8F01\uFF02" + 
	"\u0202\u8802\u9201\1\1\1\uFF7A\u7373\200\4\u7710\221\1\2\1\1\33\222\u7875\u7102\u7E02\u0279\u0E02" + 
	"\u9101\uFF02\2\u4902\u8F01\1\uFF7A\u7373\200\4\u7710\223\1\2\43\212\1\1\u7875\u7102\u7E02\u0279" + 
	"\u0E02\u9301\uFF02\u0101\1\uFF02\u2502\u937A\u7373\200\4\u7710\225\1\2\115\226\1\1\u7875\u7102\u7E02" + 
	"\u0279\u1202\u9501\uFF02\u0202\u9502\u9701\1\1\1\uFF7A\u7373\200\4\u7714\227\1\4\212\231\1\1\1\1" + 
	"\u7875\u7102\u7E02\u0279\u0E02\u9701\uFF02\2\u2802\u9601\1\uFF7A\u7373\200\4\u7710\231\1\2\42\212" + 
	"\1\1\u7875\u7102\u7E02\u0279\u0E02\u9901\uFF02\u0101\1\uFF02\u1902\u9A7A\u7373\200\4\u7710\233\42" + 
	"\2\1\1\1\1\u7875\u7102\u7E02\u0279\u1402\u9B01\uFF02\u0401\uFF02\u9B02\u9D01\1\1\1\uFF7A\u7373\200" + 
	"\4\u7710\235\1\2\27\233\1\1\u7875\u7102\u7E02\u0279\u0E02\u9D01\uFF02\2\u4902\u9E01\1\uFF7A\u7373" + 
	"\200\4\u7714\237\1\4\233\240\1\1\1\1\u7875\u7102\u7E02\u0279\u0E02\u9F01\uFF02\u0101\1\uFF02\u5702" + 
	"\u9F7A\u7373\200\4\u7710\241\1\2\113\237\1\1\u7875\u7102\u7E02\u0279\u0E02\uA101\uFF02\u0101\1\uFF02" + 
	"\u2102\uA27A\u7373\200\4\u7710\243\43\2\1\1\1\1\u7875\u7102\u7E02\u0279\u1402\uA301\uFF02\u0401" + 
	"\uFF02\uA302\uA501\1\1\1\uFF7A\u7373\200\4\u7710\245\1\2\27\243\1\1\u7875\u7102\u7E02\u0279\u0E02" + 
	"\uA501\uFF02\2\u4902\uA601\1\uFF7A\u7373\200\4\u7714\247\1\4\243\250\1\1\1\1\u7875\u7102\u7E02\u0279" + 
	"\u0E02\uA701\uFF02\u0101\1\uFF02\u5702\uA77A\u7373\200\4\u7710\251\1\2\113\247\1\1\u7875\u7102\u7E02" + 
	"\u0279\u0E02\uA901\uFF02\u0101\1\uFF02\u1802\uAA7A\u7373\200\4\u7710\253\44\2\1\1\1\1\u7875\u7102" + 
	"\u7E02\u0279\u0E02\uAB01\uFF02\2\u4B02\uAC01\1\uFF7A\u7373\200\4\u7714\255\1\4\256\257\1\1\1\1\u7875" + 
	"\u7102\u7E02\u0279\u0E02\uAD01\uFF02\u0101\1\uFF02\u5202\uAE7A\u7373\200\4\u7710\257\1\2\43\256" + 
	"\1\1\u7875\u7102\u7E02\u0279\u1202\uAF01\uFF02\u0202\uAF02\uB101\1\1\1\uFF7A\u7373\200\4\u7714\261" + 
	"\1\4\270\271\1\1\1\1\u7875\u7102\u7E02\u0279\u1402\uB101\uFF02\u0401\uFF02\uB102\uB301\1\1\1\uFF7A" + 
	"\u7373\200\4\u7710\263\1\2\27\261\1\1\u7875\u7102\u7E02\u0279\u0E02\uB301\uFF02\2\u2302\uB401\1" + 
	"\uFF7A\u7373\200\4\u7714\265\1\4\261\266\1\1\1\1\u7875\u7102\u7E02\u0279\u0E02\uB501\uFF02\u0101" + 
	"\1\uFF02\u5702\uB57A\u7373\200\4\u7710\267\1\2\45\265\1\1\u7875\u7102\u7E02\u0279\u1202\uB701\uFF02" + 
	"\u0202\uB802\uBA01\1\1\1\uFF7A\u7373\200\4\u7710\271\1\2\1\1\131\270\u7875\u7102\u7E02\u0279\u0E02" + 
	"\uB901\uFF02\u0101\1\uFF02\u5302\uA97A\u7373\200\4\u7710\273\1\2\1\1\130\274\u7875\u7102\u7E02\u0279" + 
	"\u0E02\uBB01\uFF02\2\u2C02\uB901\1\uFF7A\u7373\200\4\u7710\275\1\2\36\277\1\1\u7875\u7102\u7E02" + 
	"\u0279\u0E02\uBC02\u2302\u0101\1\1\1\uFF7A\u7373\200\4\u7710\277\1\2\115\300\1\1\u7875\u7102\u7E02" + 
	"\u0279\u1202\uBF01\uFF02\u0202\uBF02\uC101\1\1\1\uFF7A\u7373\200\4\u7714\301\1\4\276\303\1\1\1\1" + 
	"\u7875\u7102\u7E02\u0279\u0E02\uC101\uFF02\2\u6F02\uC001\1\uFF7A\u7373\200\4\u7710\303\1\2\55\276" + 
	"\1\1\u7875\u7102\u7E02\u0279\u0E02\uC301\uFF02\u0101\1\uFF02\u5902\uC47A\u7373\200\4\u7710\305\46" + 
	"\2\1\1\1\1\u7875\u7102\u7E02\u0279\u0E02\uC501\uFF02\u0101\1\uFF02\u2502\uC57A\u7373\200\4\u7710" + 
	"\307\1\2\115\310\1\1\u7875\u7102\u7E02\u0279\u0E02\uC701\uFF02\2\u2502\uC401\1\uFF7A\u7373\200\4" + 
	"\u7710\311\1\2\1\1\124\313\u7875\u7102\u7E02\u0279\u0E02\uC802\u2502\u0101\1\1\1\uFF7A\u7373\200" + 
	"\4\u7714\313\1\4\314\315\1\1\1\1\u7875\u7102\u7E02\u0279\u0E02\uCB01\uFF02\u0101\1\uFF02\u5302\uC87A" + 
	"\u7373\200\4\u7716\315\1\5\1\316\317\1\1\1\1\u7875\u7102\u7E02\u0279\u0E02\uCD01\uFF02\2\u1502\uCB01" + 
	"\1\uFF7A\u7373\200\4\u7710\317\1\2\50\320\1\1\u7875\u7102\u7E02\u0279\u1202\uCF01\uFF02\u0202\uCA02" + 
	"\uCE01\1\1\1\uFF7A\u7373\200\4\u7716\321\1\5\1\323\324\1\1\1\1\u7875\u7102\u7E02\u0279\u0E02\uD002" + 
	"\u2602\u0101\1\1\1\uFF7A\u7373\200\4\u7710\323\1\2\1\1\130\322\u7875\u7102\u7E02\u0279\u0E02\uD301" + 
	"\uFF02\2\u1C02\uD401\1\uFF7A\u7373\200\4\u771C\325\1\10\1\326\327\330\331\332\1\1\1\1\u7875\u7102" + 
	"\u7E02\u0279\u0E02\uD501\uFF02\2\u2702\uD101\1\uFF7A\u7373\200\4\u7710\327\1\2\41\322\1\1\u7875" + 
	"\u7102\u7E02\u0279\u0E02\uD701\uFF02\2\u2202\uD101\1\uFF7A\u7373\200\4\u7710\331\1\2\46\322\1\1" + 
	"\u7875\u7102\u7E02\u0279\u0E02\uD901\uFF02\2\u2E02\uD101\1\uFF7A\u7373\200\4\u7710\333\1\2\101\335" + 
	"\1\1\u7875\u7102\u7E02\u0279\u0E02\uDA02\u2702\u0101\1\1\1\uFF7A\u7373\200\4\u7710\335\1\2\115\336" + 
	"\1\1\u7875\u7102\u7E02\u0279\u0E02\uDD01\uFF02\u0101\1\uFF02\u5002\uDD7A\u7373\200\4\u7710\337\1" + 
	"\2\1\1\123\340\u7875\u7102\u7E02\u0279\u0E02\uDF01\uFF02\2\u3302\uE001\1\uFF7A\u7373\200\4\u7714" + 
	"\341\1\4\342\343\1\1\1\1\u7875\u7102\u7E02\u0279\u0E02\uE101\uFF02\u0101\1\uFF02\u5602\uDA7A\u7373" + 
	"\200\4\u7710\343\1\2\1\1\26\344\u7875\u7102\u7E02\u0279\u0E02\uE301\uFF02\2\u9902\uE101\1\uFF7A" + 
	"\u7373\200\4\u7710\345\1\2\1\1\135\347\u7875\u7102\u7E02\u0279\u0E02\uE402\u2802\u0101\1\1\1\uFF7A" + 
	"\u7373\200\4\u7716\347\1\5\1\351\352\1\1\1\1\u7875\u7102\u7E02\u0279\u0E02\uE701\uFF02\u0101\1\uFF02" + 
	"\u8102\uE47A\u7373\200\4\u7710\351\1\2\27\350\1\1\u7875\u7102\u7E02\u0279\u0E02\uE901\uFF02\2\u2902" + 
	"\uEA01\1\uFF7A\u7373\200\4\u7714\353\1\4\350\354\1\1\1\1\u7875\u7102\u7E02\u0279\u0E02\uEB01\uFF02" + 
	"\u0101\1\uFF02\u5702\uEB7A\u7373\200\4\u7710\355\1\2\53\353\1\1\u7875\u7102\u7E02\u0279\u0E02\uED01" + 
	"\uFF02\2\u9202\uEF01\1\uFF7A\u7373\200\4\u7710\357\53\2\1\1\1\1\u7875\u7102\u7E02\u0279\u0E02\uEF01" + 
	"\uFF02\2\u4B02\uF001\1\uFF7A\u7373\200\4\u7714\361\1\4\357\362\1\1\1\1\u7875\u7102\u7E02\u0279\u0E02" + 
	"\uF101\uFF02\2\u2A02\uEE01\1\uFF7A\u7373\200\4\u7710\363\1\2\1\1\33\365\u7875\u7102\u7E02\u0279" + 
	"\u0E02\uF202\u2A02\u0101\1\1\1\uFF7A\u7373\200\4\u7716\365\1\5\1\366\367\1\1\1\1\u7875\u7102\u7E02" + 
	"\u0279\u0E02\uF501\uFF02\2\u1502\uF301\1\uFF7A\u7373\200\4\u7710\367\1\2\113\370\1\1\u7875\u7102" + 
	"\u7E02\u0279\u1202\uF701\uFF02\u0202\uF202\uF801\1\1\1\uFF7A\u7373\200\4\u7710\371\1\2\1\1\156\372" + 
	"\u7875\u7102\u7E02\u0279\u0E02\uF901\uFF02\2\u4902\uF701\1\uFF7A\u7373\200\4\u7710\373\1\2\1\1\124" + 
	"\375\u7875\u7102\u7E02\u0279\u0E02\uFA02\u2B02\u0101\1\1\1\uFF7A\u7373\200\4\u7710\375\1\2\56\376" + 
	"\1\1\u7875\u7102\u7E02\u0279\u0E02\uFD01\uFF02\u0101\1\uFF02\u5302\uFA7A\u7373\200\4\u7714\377\1" + 
	"\4\u0100\u0101\1\1\1\1\u7875\u7102\u7E02\u0279\u0E02\uFE02\u2C02\u0101\1\1\1\uFF7A\u7373\200\4\u7716" + 
	"\u0101\1\5\1\u0102\u0103\1\1\1\1\u7875\u7102\u7E02\u0279\u0E03\u0101\uFF02\2\u1502\uFF01\1\uFF7A" + 
	"\u7373\200\4\u7710\u0103\1\2\57\u0104\1\1\u7875\u7102\u7E02\u0279\u1203\u0301\uFF02\u0202\uFE03" + 
	"\u0201\1\1\1\uFF7A\u7373\200\4\u7716\u0105\1\5\1\u0107\u0108\1\1\1\1\u7875\u7102\u7E02\u0279\u0E03" + 
	"\u0402\u2D02\u0101\1\1\1\uFF7A\u7373\200\4\u7710\u0107\1\2\1\1\130\u0106\u7875\u7102\u7E02\u0279" + 
	"\u0E03\u0701\uFF02\2\u1C03\u0801\1\uFF7A\u7373\200\4\u7720\u0109\1\12\1\u010A\u010B\u010C\u010D" + 
	"\u010E\u010F\u0110\1\1\1\1\u7875\u7102\u7E02\u0279\u0E03\u0901\uFF02\2\u3E03\u0501\1\uFF7A\u7373" + 
	"\200\4\u7710\u010B\1\2\41\u0106\1\1\u7875\u7102\u7E02\u0279\u0E03\u0B01\uFF02\2\u2203\u0501\1\uFF7A" + 
	"\u7373\200\4\u7710\u010D\1\2\46\u0106\1\1\u7875\u7102\u7E02\u0279\u0E03\u0D01\uFF02\2\u3B03\u0501" + 
	"\1\uFF7A\u7373\200\4\u7710\u010F\1\2\60\u0106\1\1\u7875\u7102\u7E02\u0279\u0E03\u0F01\uFF02\2\u3603" + 
	"\u0501\1\uFF7A\u7373\200\4\u7710\u0111\1\2\101\u0113\1\1\u7875\u7102\u7E02\u0279\u0E03\u1002\u2E02" + 
	"\u0101\1\1\1\uFF7A\u7373\200\4\u7710\u0113\1\2\62\u0114\1\1\u7875\u7102\u7E02\u0279\u0E03\u1301" + 
	"\uFF02\u0101\1\uFF02\u5603\u107A\u7373\200\4\u7710\u0115\1\2\101\u0117\1\1\u7875\u7102\u7E02\u0279" + 
	"\u0E03\u1402\u2F02\u0101\1\1\1\uFF7A\u7373\200\4\u7710\u0117\1\2\62\u0116\1\1\u7875\u7102\u7E02" + 
	"\u0279\u0E03\u1701\uFF02\2\u3103\u1901\1\uFF7A\u7373\200\4\u7710\u0119\62\2\1\1\1\1\u7875\u7102" + 
	"\u7E02\u0279\u1203\u1901\uFF02\u0203\u1703\u1A01\1\1\1\uFF7A\u7373\200\4\u7710\u011B\1\2\1\1\131" + 
	"\u011C\u7875\u7102\u7E02\u0279\u0E03\u1B01\uFF02\2\u3103\u1901\1\uFF7A\u7373\200\4\u7710\u011D\1" + 
	"\2\64\u011F\1\1\u7875\u7102\u7E02\u0279\u0E03\u1C02\u3102\u0101\1\1\1\uFF7A\u7373\200\4\u7714\u011F" + 
	"\1\4\u011E\u0120\1\1\1\1\u7875\u7102\u7E02\u0279\u0E03\u1F01\uFF02\u0101\1\uFF02\u5A03\u1F7A\u7373" + 
	"\200\4\u7710\u0121\1\2\66\u011E\1\1\u7875\u7102\u7E02\u0279\u0E03\u2101\uFF02\2\u4B03\u2301\1\uFF7A" + 
	"\u7373\200\4\u7710\u0123\64\2\1\1\1\1\u7875\u7102\u7E02\u0279\u0E03\u2301\uFF02\2\u3303\u2201\1" + 
	"\uFF7A\u7373\200\4\u7714\u0125\1\4\u0126\u0127\1\1\1\1\u7875\u7102\u7E02\u0279\u0E03\u2402\u3302" + 
	"\u0101\1\1\1\uFF7A\u7373\200\4\u7710\u0127\1\2\224\u0128\1\1\u7875\u7102\u7E02\u0279\u0E03\u2701" + 
	"\uFF02\u0101\1\uFF02\u5403\u277A\u7373\200\4\u7710\u0129\1\2\1\1\127\u0125\u7875\u7102\u7E02\u0279" + 
	"\u1403\u2901\uFF02\u0401\uFF03\u2A03\u2C01\1\1\1\uFF7A\u7373\200\4\u7710\u012B\66\2\1\1\1\1\u7875" + 
	"\u7102\u7E02\u0279\u0E03\u2B01\uFF02\2\u3503\u2A01\1\uFF7A\u7373\200\4\u7710\u012D\1\2\116\u012B" + 
	"\1\1\u7875\u7102\u7E02\u0279\u0E03\u2D01\uFF02\u0101\1\uFF02\u5203\u2E7A\u7373\200\4\u7710\u012F" + 
	"\67\2\1\1\1\1\u7875\u7102\u7E02\u0279\u1203\u2F01\uFF02\u0203\u2F03\u3101\1\1\1\uFF7A\u7373\200" + 
	"\4\u7714\u0131\1\4\u0136\u0137\1\1\1\1\u7875\u7102\u7E02\u0279\u0E03\u3101\uFF02\2\u3403\u3201\1" + 
	"\uFF7A\u7373\200\4\u7714\u0133\1\4\u0131\u0134\1\1\1\1\u7875\u7102\u7E02\u0279\u0E03\u3301\uFF02" + 
	"\u0101\1\uFF02\u5703\u337A\u7373\200\4\u7710\u0135\1\2\66\u0133\1\1\u7875\u7102\u7E02\u0279\u0E03" + 
	"\u3501\uFF02\u0101\1\uFF02\u5303\u2D7A\u7373\200\4\u7710\u0137\1\2\1\1\131\u0136\u7875\u7102\u7E02" + 
	"\u0279\u1203\u3701\uFF02\u0203\u3803\u3A01\1\1\1\uFF7A\u7373\200\4\u7710\u0139\70\2\1\1\1\1\u7875" + 
	"\u7102\u7E02\u0279\u0E03\u3901\uFF02\2\u4803\u3C01\1\uFF7A\u7373\200\4\u7710\u013B\1\2\52\u013C" + 
	"\1\1\u7875\u7102\u7E02\u0279\u0E03\u3B01\uFF02\2\u9203\u3901\1\uFF7A\u7373\200\4\u7710\u013D\1\2" + 
	"\115\u013E\1\1\u7875\u7102\u7E02\u0279\u0E03\u3D01\uFF02\2\u3703\u3E01\1\uFF7A\u7373\200\4\u7710" + 
	"\u013F\1\2\65\u0140\1\1\u7875\u7102\u7E02\u0279\u1203\u3F01\uFF02\u0203\u3F03\u4101\1\1\1\uFF7A" + 
	"\u7373\200\4\u7716\u0141\1\5\1\u0143\u0144\1\1\1\1\u7875\u7102\u7E02\u0279\u0E03\u4101\uFF02\2\u3A03" + 
	"\u4001\1\uFF7A\u7373\200\4\u7710\u0143\1\2\173\u0139\1\1\u7875\u7102\u7E02\u0279\u0E03\u4301\uFF02" + 
	"\u0101\1\uFF02\u5603\u377A\u7373\200\4\u7710\u0145\1\2\1\1\122\u0147\u7875\u7102\u7E02\u0279\u0E03" + 
	"\u4402\u3702\u0101\1\1\1\uFF7A\u7373\200\4\u7714\u0147\1\4\u0148\u0149\1\1\1\1\u7875\u7102\u7E02" + 
	"\u0279\u0E03\u4701\uFF02\u0101\1\uFF02\u5103\u447A\u7373\200\4\u7710\u0149\1\2\72\u0148\1\1\u7875" + 
	"\u7102\u7E02\u0279\u1403\u4901\uFF02\u0401\uFF03\u4A03\u4C01\1\1\1\uFF7A\u7373\200\4\u7710\u014B" + 
	"\72\2\1\1\1\1\u7875\u7102\u7E02\u0279\u0E03\u4B01\uFF02\2\u1503\u4A01\1\uFF7A\u7373\200\4\u7710" + 
	"\u014D\1\2\73\u014E\1\1\u7875\u7102\u7E02\u0279\u1203\u4D01\uFF02\u0203\u4903\u4E01\1\1\1\uFF7A" + 
	"\u7373\200\4\u7710\u014F\1\2\1\1\131\u0150\u7875\u7102\u7E02\u0279\u0E03\u4F01\uFF02\2\u3903\u4D01" + 
	"\1\uFF7A\u7373\200\4\u7710\u0151\1\2\36\u0153\1\1\u7875\u7102\u7E02\u0279\u0E03\u5002\u3902\u0101" + 
	"\1\1\1\uFF7A\u7373\200\4\u7710\u0153\1\2\101\u0154\1\1\u7875\u7102\u7E02\u0279\u1203\u5301\uFF02" + 
	"\u0203\u5303\u5501\1\1\1\uFF7A\u7373\200\4\u7716\u0155\1\5\1\u0158\u015C\1\1\1\1\u7875\u7102\u7E02" + 
	"\u0279\u0E03\u5501\uFF02\2\u9203\u5601\1\uFF7A\u7373\200\4\u7710\u0157\1\2\1\1\176\u0155\u7875\u7102" + 
	"\u7E02\u0279\u1203\u5701\uFF02\u0203\u5703\u5901\1\1\1\uFF7A\u7373\200\4\u7710\u0159\1\2\1\1\67" + 
	"\u0152\u7875\u7102\u7E02\u0279\u0E03\u5901\uFF02\2\u4B03\u5A01\1\uFF7A\u7373\200\4\u7710\u015B\1" + 
	"\2\1\1\132\u0159\u7875\u7102\u7E02\u0279\u0E03\u5B01\uFF02\2\u3203\u5101\1\uFF7A\u7373\200\4\u7710" + 
	"\u015D\1\2\1\1\71\u015F\u7875\u7102\u7E02\u0279\u0E03\u5C02\u3A02\u0101\1\1\1\uFF7A\u7373\200\4" + 
	"\u7710\u015F\1\2\113\u0160\1\1\u7875\u7102\u7E02\u0279\u1203\u5F01\uFF02\u0203\u5C03\u6001\1\1\1" + 
	"\uFF7A\u7373\200\4\u7710\u0161\1\2\1\1\131\u0162\u7875\u7102\u7E02\u0279\u0E03\u6101\uFF02\2\u4903" + 
	"\u5F01\1\uFF7A\u7373\200\4\u7714\u0163\1\4\u0165\u0166\1\1\1\1\u7875\u7102\u7E02\u0279\u0E03\u6202" + 
	"\u3B02\u0101\1\1\1\uFF7A\u7373\200\4\u7710\u0165\1\2\115\u0167\1\1\u7875\u7102\u7E02\u0279\u0E03" + 
	"\u6501\uFF02\2\u2803\u6401\1\uFF7A\u7373\200\4\u7710\u0167\1\2\71\u0168\1\1\u7875\u7102\u7E02\u0279" + 
	"\u1203\u6701\uFF02\u0203\u6703\u6901\1\1\1\uFF7A\u7373\200\4\u7710\u0169\1\2\1\1\124\u016B\u7875" + 
	"\u7102\u7E02\u0279\u0E03\u6901\uFF02\2\u3A03\u6801\1\uFF7A\u7373\200\4\u7710\u016B\1\2\77\u016C" + 
	"\1\1\u7875\u7102\u7E02\u0279\u0E03\u6B01\uFF02\u0101\1\uFF02\u5303\u627A\u7373\200\4\u7716\u016D" + 
	"\1\5\1\u016F\u0174\1\1\1\1\u7875\u7102\u7E02\u0279\u0E03\u6C02\u3C02\u0101\1\1\1\uFF7A\u7373\200" + 
	"\4\u7714\u016F\1\4\u0170\u0171\1\1\1\1\u7875\u7102\u7E02\u0279\u0E03\u6F01\uFF02\u0101\1\uFF02\u3503" + 
	"\u707A\u7373\200\4\u7710\u0171\1\2\104\u0170\1\1\u7875\u7102\u7E02\u0279\u0E03\u7101\uFF02\2\u6F03" + 
	"\u7201\1\uFF7A\u7373\200\4\u7710\u0173\1\2\1\1\130\u016E\u7875\u7102\u7E02\u0279\u1203\u7301\uFF02" + 
	"\u0203\u7303\u7501\1\1\1\uFF7A\u7373\200\4\u7714\u0175\1\4\u0178\u0179\1\1\1\1\u7875\u7102\u7E02" + 
	"\u0279\u0E03\u7501\uFF02\2\u6903\u7601\1\uFF7A\u7373\200\4\u7710\u0177\1\2\1\1\132\u0175\u7875\u7102" + 
	"\u7E02\u0279\u0E03\u7701\uFF02\u0101\1\uFF02\u3203\u787A\u7373\200\4\u7710\u0179\1\2\104\u0178\1" + 
	"\1\u7875\u7102\u7E02\u0279\u0E03\u7901\uFF02\2\u6F03\u7A01\1\uFF7A\u7373\200\4\u7710\u017B\1\2\1" + 
	"\1\130\u016E\u7875\u7102\u7E02\u0279\u1203\u7B01\uFF02\u0203\u7B03\u7D01\1\1\1\uFF7A\u7373\200\4" + 
	"\u7710\u017D\77\2\1\1\1\1\u7875\u7102\u7E02\u0279\u1403\u7D01\uFF02\u0401\uFF03\u7D03\u7F01\1\1" + 
	"\1\uFF7A\u7373\200\4\u7710\u017F\1\2\27\u017D\1\1\u7875\u7102\u7E02\u0279\u1203\u7F01\uFF02\u0203" + 
	"\u7F03\u8101\1\1\1\uFF7A\u7373\200\4\u7714\u0181\1\4\u017D\u0183\1\1\1\1\u7875\u7102\u7E02\u0279" + 
	"\u0E03\u8101\uFF02\2\u3C03\u8001\1\uFF7A\u7373\200\4\u7710\u0183\1\2\174\u0181\1\1\u7875\u7102\u7E02" + 
	"\u0279\u0E03\u8301\uFF02\2\u7903\u8401\1\uFF7A\u7373\200\4\u7710\u0185\100\2\1\1\1\1\u7875\u7102" + 
	"\u7E02\u0279\u1403\u8501\uFF02\u0401\uFF03\u8603\u8A01\1\1\1\uFF7A\u7373\200\4\u7710\u0187\101\2" + 
	"\1\1\1\1\u7875\u7102\u7E02\u0279\u0E03\u8701\uFF02\2\u4703\u8801\1\uFF7A\u7373\200\4\u7714\u0189" + 
	"\1\4\u0187\u018A\1\1\1\1\u7875\u7102\u7E02\u0279\u0E03\u8901\uFF02\2\u7503\u8601\1\uFF7A\u7373\200" + 
	"\4\u7710\u018B\1\2\103\u018C\1\1\u7875\u7102\u7E02\u0279\u1203\u8B01\uFF02\u0203\u8503\u8C01\1\1" + 
	"\1\uFF7A\u7373\200\4\u7710\u018D\1\2\167\u0187\1\1\u7875\u7102\u7E02\u0279\u1403\u8D01\uFF02\u0401" + 
	"\uFF03\u8E03\u9101\1\1\1\uFF7A\u7373\200\4\u7710\u018F\102\2\1\1\1\1\u7875\u7102\u7E02\u0279\u0E03" + 
	"\u8F01\uFF02\2\u4703\u9001\1\uFF7A\u7373\200\4\u7710\u0191\1\2\167\u018F\1\1\u7875\u7102\u7E02\u0279" + 
	"\u0E03\u9101\uFF02\2\u4103\u9201\1\uFF7A\u7373\200\4\u7714\u0193\1\4\u018F\u0194\1\1\1\1\u7875\u7102" + 
	"\u7E02\u0279\u0E03\u9301\uFF02\2\u7503\u8E01\1\uFF7A\u7373\200\4\u7710\u0195\1\2\115\u0197\1\1\u7875" + 
	"\u7102\u7E02\u0279\u0E03\u9402\u4102\u0101\1\1\1\uFF7A\u7373\200\4\u7714\u0197\1\4\u0198\u0199\1" + 
	"\1\1\1\u7875\u7102\u7E02\u0279\u1203\u9701\uFF02\u0203\u9403\u9901\1\1\1\uFF7A\u7373\200\4\u7710" + 
	"\u0199\1\2\105\u0198\1\1\u7875\u7102\u7E02\u0279\u0E03\u9901\uFF02\u0101\1\uFF02\u5803\u997A\u7373" + 
	"\200\4\u7710\u019B\1\2\224\u019C\1\1\u7875\u7102\u7E02\u0279\u0E03\u9B01\uFF02\2\u4B03\u9C01\1\uFF7A" + 
	"\u7373\200\4\u7714\u019D\1\4\u0198\u019E\1\1\1\1\u7875\u7102\u7E02\u0279\u0E03\u9D01\uFF02\2\u4303" + 
	"\u9701\1\uFF7A\u7373\200\4\u7710\u019F\1\2\1\1\135\u01A1\u7875\u7102\u7E02\u0279\u0E03\u9E02\u4202" + 
	"\u0101\1\1\1\uFF7A\u7373\200\4\u7710\u01A1\1\2\106\u01A2\1\1\u7875\u7102\u7E02\u0279\u0E03\uA101" + 
	"\uFF02\u0101\1\uFF02\u8103\u9E7A\u7373\200\4\u7710\u01A3\1\2\1\1\135\u01A5\u7875\u7102\u7E02\u0279" + 
	"\u0E03\uA202\u4302\u0101\1\1\1\uFF7A\u7373\200\4\u7714\u01A5\1\4\u01A6\u01A7\1\1\1\1\u7875\u7102" + 
	"\u7E02\u0279\u0E03\uA501\uFF02\u0101\1\uFF02\u8103\uA27A\u7373\200\4\u7710\u01A7\1\2\106\u01A6\1" + 
	"\1\u7875\u7102\u7E02\u0279\u1403\uA701\uFF02\u0401\uFF03\uA803\uAA01\1\1\1\uFF7A\u7373\200\4\u7710" + 
	"\u01A9\106\2\1\1\1\1\u7875\u7102\u7E02\u0279\u0E03\uA901\uFF02\2\u1503\uA801\1\uFF7A\u7373\200\4" + 
	"\u7710\u01AB\1\2\107\u01AC\1\1\u7875\u7102\u7E02\u0279\u1203\uAB01\uFF02\u0203\uA703\uAC01\1\1\1" + 
	"\uFF7A\u7373\200\4\u7710\u01AD\1\2\1\1\131\u01AE\u7875\u7102\u7E02\u0279\u0E03\uAD01\uFF02\2\u4503" + 
	"\uAB01\1\uFF7A\u7373\200\4\u7710\u01AF\1\2\224\u01B1\1\1\u7875\u7102\u7E02\u0279\u0E03\uAE02\u4502" + 
	"\u0101\1\1\1\uFF7A\u7373\200\4\u7716\u01B1\1\5\1\u01B2\u01B3\1\1\1\1\u7875\u7102\u7E02\u0279\u0E03" + 
	"\uB101\uFF02\2\u4003\uAF01\1\uFF7A\u7373\200\4\u7710\u01B3\1\2\110\u01B0\1\1\u7875\u7102\u7E02\u0279" + 
	"\u0E03\uB301\uFF02\u0101\1\uFF02\u5E03\uB47A\u7373\200\4\u7710\u01B5\110\2\1\1\1\1\u7875\u7102\u7E02" + 
	"\u0279\u1203\uB501\uFF02\u0203\uB303\uB601\1\1\1\uFF7A\u7373\200\4\u7716\u01B7\1\5\1\u01B8\u01BB" + 
	"\1\1\1\1\u7875\u7102\u7E02\u0279\u0E03\uB701\uFF02\u0101\1\uFF02\u1903\uB77A\u7373\200\4\u7710\u01B9" + 
	"\1\2\224\u01BA\1\1\u7875\u7102\u7E02\u0279\u0E03\uB901\uFF02\2\u4003\uB401\1\uFF7A\u7373\200\4\u7710" + 
	"\u01BB\1\2\1\1\64\u01BC\u7875\u7102\u7E02\u0279\u0E03\uBB01\uFF02\2\u9203\uBC01\1\uFF7A\u7373\200" + 
	"\4\u7710\u01BD\1\2\102\u01B5\1\1\u7875\u7102\u7E02\u0279\u2003\uBD01\uFF02\u0A01\uFF03\uBE03\uBF03" + 
	"\uC003\uC103\uC203\uC303\uC403\uC601\1\1\1\uFF7A\u7373\200\4\u7710\u01BF\111\2\1\1\1\1\u7875\u7102" + 
	"\u7E02\u0279\u0E03\uBF01\uFF02\u0101\1\uFF02\u0B03\uBD7A\u7373\200\4\u7710\u01C1\1\2\1\1\22\u01BF" + 
	"\u7875\u7102\u7E02\u0279\u0E03\uC101\uFF02\u0101\1\uFF02\u0D03\uBD7A\u7373\200\4\u7710\u01C3\1\2" + 
	"\1\1\61\u01BF\u7875\u7102\u7E02\u0279\u0E03\uC301\uFF02\u0101\1\uFF02\u2403\uBD7A\u7373\200\4\u7710" + 
	"\u01C5\1\2\1\1\50\u01BF\u7875\u7102\u7E02\u0279\u0E03\uC501\uFF02\u0101\1\uFF02\u1D03\uBD7A\u7373" + 
	"\200\4\u7710\u01C7\1\2\1\1\30\u01BF\u7875\u7102\u7E02\u0279\u1403\uC701\uFF02\u0401\uFF03\uC803" + 
	"\uCA01\1\1\1\uFF7A\u7373\200\4\u7710\u01C9\112\2\1\1\1\1\u7875\u7102\u7E02\u0279\u0E03\uC901\uFF02" + 
	"\u0101\1\uFF02\u3B03\uC77A\u7373\200\4\u7710\u01CB\1\2\101\u01C9\1\1\u7875\u7102\u7E02\u0279\u0E03" + 
	"\uCB01\uFF02\2\u9203\uCD01\1\uFF7A\u7373\200\4\u7710\u01CD\113\2\1\1\1\1\u7875\u7102\u7E02\u0279" + 
	"\u0E03\uCD01\uFF02\2\u4103\uCC01\1\uFF7A\u7373\200\4\u7710\u01CF\1\2\115\u01D1\1\1\u7875\u7102\u7E02" + 
	"\u0279\u0E03\uCE02\u4A02\u0101\1\1\1\uFF7A\u7373\200\4\u7714\u01D1\1\4\u01D0\u01D2\1\1\1\1\u7875" + 
	"\u7102\u7E02\u0279\u0E03\uD101\uFF02\u0101\1\uFF02\u5803\uD17A\u7373\200\4\u7710\u01D3\1\2\115\u01D1" + 
	"\1\1\u7875\u7102\u7E02\u0279\u1403\uD301\uFF02\u0401\uFF03\uD403\uD601\1\1\1\uFF7A\u7373\200\4\u7710" + 
	"\u01D5\115\2\1\1\1\1\u7875\u7102\u7E02\u0279\u0E03\uD501\uFF02\2\u1603\uD401\1\uFF7A\u7373\200\4" + 
	"\u7710\u01D7\1\2\1\1\206\u01D5\u7875\u7102\u7E02\u0279\u1403\uD701\uFF02\u0401\uFF03\uD803\uDA01" + 
	"\1\1\1\uFF7A\u7373\200\4\u7710\u01D9\116\2\1\1\1\1\u7875\u7102\u7E02\u0279\u0E03\uD901\uFF02\2\u4D03" + 
	"\uD801\1\uFF7A\u7373\200\4\u7710\u01DB\1\2\120\u01D9\1\1\u7875\u7102\u7E02\u0279\u0E03\uDB01\uFF02" + 
	"\2\u5403\uDD01\1\uFF7A\u7373\200\4\u7710\u01DD\117\2\1\1\1\1\u7875\u7102\u7E02\u0279\u1203\uDD01" + 
	"\uFF02\u0203\uDB03\uDE01\1\1\1\uFF7A\u7373\200\4\u7710\u01DF\1\2\125\u01E0\1\1\u7875\u7102\u7E02" + 
	"\u0279\u0E03\uDF01\uFF02\2\u4C03\uDC01\1\uFF7A\u7373\200\4\u7716\u01E1\1\5\1\u01E3\u01E9\1\1\1\1" + 
	"\u7875\u7102\u7E02\u0279\u0E03\uE002\u4E02\u0101\1\1\1\uFF7A\u7373\200\4\u7710\u01E3\1\2\1\1\122" + 
	"\u01E4\u7875\u7102\u7E02\u0279\u0E03\uE301\uFF02\2\u9203\uE401\1\uFF7A\u7373\200\4\u7710\u01E5\1" + 
	"\2\102\u01E6\1\1\u7875\u7102\u7E02\u0279\u0E03\uE501\uFF02\2\u6503\uE601\1\uFF7A\u7373\200\4\u7710" + 
	"\u01E7\1\2\1\1\123\u01E8\u7875\u7102\u7E02\u0279\u0E03\uE701\uFF02\2\u4E03\uE101\1\uFF7A\u7373\200" + 
	"\4\u7710\u01E9\1\2\121\u01E2\1\1\u7875\u7102\u7E02\u0279\u1803\uE901\uFF02\u0601\uFF03\uEA03\uED03" + 
	"\uF103\uF701\1\1\1\uFF7A\u7373\200\4\u7710\u01EB\121\2\1\1\1\1\u7875\u7102\u7E02\u0279\u0E03\uEB01" + 
	"\uFF02\2\u4B03\uEC01\1\uFF7A\u7373\200\4\u7710\u01ED\1\2\1\1\177\u01EE\u7875\u7102\u7E02\u0279\u0E03" + 
	"\uED01\uFF02\2\u5003\uEA01\1\uFF7A\u7373\200\4\u7710\u01EF\1\2\1\1\122\u01F0\u7875\u7102\u7E02\u0279" + 
	"\u0E03\uEF01\uFF02\u0101\1\uFF02\u5103\uEF7A\u7373\200\4\u7710\u01F1\1\2\1\1\177\u01F2\u7875\u7102" + 
	"\u7E02\u0279\u0E03\uF101\uFF02\2\u5003\uEA01\1\uFF7A\u7373\200\4\u7710\u01F3\1\2\1\1\122\u01F4\u7875" + 
	"\u7102\u7E02\u0279\u0E03\uF301\uFF02\2\u5103\uF401\1\uFF7A\u7373\200\4\u7710\u01F5\1\2\1\1\123\u01F6" + 
	"\u7875\u7102\u7E02\u0279\u0E03\uF501\uFF02\u0101\1\uFF02\u7D03\uF57A\u7373\200\4\u7710\u01F7\1\2" + 
	"\122\u01EB\1\1\u7875\u7102\u7E02\u0279\u0E03\uF701\uFF02\u0101\1\uFF02\u5003\uF77A\u7373\200\4\u7710" + 
	"\u01F9\1\2\72\u01FA\1\1\u7875\u7102\u7E02\u0279\u0E03\uF901\uFF02\u0101\1\uFF02\u5103\uF97A\u7373" + 
	"\200\4\u7710\u01FB\1\2\1\1\177\u01FC\u7875\u7102\u7E02\u0279\u0E03\uFB01\uFF02\2\u5003\uEA01\1\uFF7A" + 
	"\u7373\200\4\u7716\u01FD\1\5\1\u01FF\u0200\1\1\1\1\u7875\u7102\u7E02\u0279\u0E03\uFC02\u5002\u0101" + 
	"\1\1\1\uFF7A\u7373\200\4\u7710\u01FF\1\2\116\u01FE\1\1\u7875\u7102\u7E02\u0279\u0E03\uFF01\uFF02" + 
	"\2\u7903\uFD01\1\uFF7A\u7373\200\4\u7710\u0201\1\2\124\u0203\1\1\u7875\u7102\u7E02\u0279\u0E04\2" + 
	"\u5102\u0101\1\1\1\uFF7A\u7373\200\4\u7714\u0203\1\4\u0202\u0204\1\1\1\1\u7875\u7102\u7E02\u0279" + 
	"\u0E04\u0301\uFF02\u0101\1\uFF02\u5704\u037A\u7373\200\4\u7710\u0205\1\2\124\u0203\1\1\u7875\u7102" + 
	"\u7E02\u0279\u0E04\u0501\uFF02\2\u4B04\u0601\1\uFF7A\u7373\200\4\u7710\u0207\124\2\1\1\1\1\u7875" + 
	"\u7102\u7E02\u0279\u2804\u0701\uFF02\u0E01\uFF04\u0804\u0904\u0A04\u0B04\u0C04\u0D04\u0E04\u0F04" + 
	"\u1004\u1104\u1204\u1401\1\1\1\uFF7A\u7373\200\4\u7710\u0209\125\2\1\1\1\1\u7875\u7102\u7E02\u0279" + 
	"\u0E04\u0901\uFF02\u0101\1\uFF02\u5A04\u077A\u7373\200\4\u7710\u020B\1\2\1\1\165\u0209\u7875\u7102" + 
	"\u7E02\u0279\u0E04\u0B01\uFF02\u0101\1\uFF02\u7404\u077A\u7373\200\4\u7710\u020D\1\2\1\1\172\u0209" + 
	"\u7875\u7102\u7E02\u0279\u0E04\u0D01\uFF02\u0101\1\uFF02\u7104\u077A\u7373\200\4\u7710\u020F\1\2" + 
	"\1\1\164\u0209\u7875\u7102\u7E02\u0279\u0E04\u0F01\uFF02\u0101\1\uFF02\u7904\u077A\u7373\200\4\u7710" + 
	"\u0211\1\2\1\1\174\u0209\u7875\u7102\u7E02\u0279\u0E04\u1101\uFF02\u0101\1\uFF02\u7B04\u077A\u7373" + 
	"\200\4\u7710\u0213\1\2\1\1\167\u0209\u7875\u7102\u7E02\u0279\u0E04\u1301\uFF02\u0101\1\uFF02\u7704" + 
	"\u077A\u7373\200\4\u7710\u0215\1\2\1\1\170\u0209\u7875\u7102\u7E02\u0279\u0E04\u1501\uFF02\2\u5504" + 
	"\u1701\1\uFF7A\u7373\200\4\u7710\u0217\126\2\1\1\1\1\u7875\u7102\u7E02\u0279\u1204\u1701\uFF02\u0204" + 
	"\u1504\u1801\1\1\1\uFF7A\u7373\200\4\u7710\u0219\1\2\1\1\140\u021A\u7875\u7102\u7E02\u0279\u0E04" + 
	"\u1901\uFF02\2\u4C04\u1A01\1\uFF7A\u7373\200\4\u7710\u021B\1\2\1\1\141\u021C\u7875\u7102\u7E02\u0279" + 
	"\u1404\u1B01\uFF02\u0401\uFF04\u1B04\u1D01\1\1\1\uFF7A\u7373\200\4\u7710\u021D\1\2\126\u0217\1\1" + 
	"\u7875\u7102\u7E02\u0279\u0E04\u1D01\uFF02\2\u4E04\u1601\1\uFF7A\u7373\200\4\u7710\u021F\1\2\130" + 
	"\u0221\1\1\u7875\u7102\u7E02\u0279\u0E04\u1E02\u5502\u0101\1\1\1\uFF7A\u7373\200\4\u7714\u0221\1" + 
	"\4\u0220\u0222\1\1\1\1\u7875\u7102\u7E02\u0279\u0E04\u2101\uFF02\u0101\1\uFF02\u6404\u217A\u7373" + 
	"\200\4\u7710\u0223\1\2\130\u0221\1\1\u7875\u7102\u7E02\u0279\u0E04\u2301\uFF02\2\u5704\u2501\1\uFF7A" + 
	"\u7373\200\4\u7710\u0225\130\2\1\1\1\1\u7875\u7102\u7E02\u0279\u1204\u2501\uFF02\u0204\u2304\u2601" + 
	"\1\1\1\uFF7A\u7373\200\4\u7710\u0227\1\2\1\1\147\u0228\u7875\u7102\u7E02\u0279\u0E04\u2701\uFF02" + 
	"\2\u5704\u2501\1\uFF7A\u7373\200\4\u7710\u0229\1\2\132\u022B\1\1\u7875\u7102\u7E02\u0279\u0E04\u2802" + 
	"\u5702\u0101\1\1\1\uFF7A\u7373\200\4\u7714\u022B\1\4\u022A\u022C\1\1\1\1\u7875\u7102\u7E02\u0279" + 
	"\u0E04\u2B01\uFF02\u0101\1\uFF02\u6D04\u2B7A\u7373\200\4\u7710\u022D\1\2\132\u022B\1\1\u7875\u7102" + 
	"\u7E02\u0279\u0E04\u2D01\uFF02\2\u5904\u2F01\1\uFF7A\u7373\200\4\u7710\u022F\132\2\1\1\1\1\u7875" + 
	"\u7102\u7E02\u0279\u1204\u2F01\uFF02\u0204\u2D04\u3001\1\1\1\uFF7A\u7373\200\4\u7710\u0231\1\2\1" + 
	"\1\160\u0232\u7875\u7102\u7E02\u0279\u0E04\u3101\uFF02\2\u5904\u2F01\1\uFF7A\u7373\200\4\u7710\u0233" + 
	"\1\2\134\u0235\1\1\u7875\u7102\u7E02\u0279\u0E04\u3202\u5902\u0101\1\1\1\uFF7A\u7373\200\4\u7714" + 
	"\u0235\1\4\u0234\u0236\1\1\1\1\u7875\u7102\u7E02\u0279\u0E04\u3501\uFF02\u0101\1\uFF02\u6C04\u357A" + 
	"\u7373\200\4\u7710\u0237\1\2\134\u0235\1\1\u7875\u7102\u7E02\u0279\u0E04\u3701\uFF02\2\u5B04\u3901" + 
	"\1\uFF7A\u7373\200\4\u7710\u0239\134\2\1\1\1\1\u7875\u7102\u7E02\u0279\u1204\u3901\uFF02\u0204\u3704" + 
	"\u3A01\1\1\1\uFF7A\u7373\200\4\u7716\u023B\1\5\1\u023D\u023E\1\1\1\1\u7875\u7102\u7E02\u0279\u0E04" + 
	"\u3B01\uFF02\2\u5B04\u3901\1\uFF7A\u7373\200\4\u7710\u023D\1\2\1\1\142\u023C\u7875\u7102\u7E02\u0279" + 
	"\u0E04\u3D01\uFF02\u0101\1\uFF02\u6304\u3A7A\u7373\200\4\u7710\u023F\1\2\136\u0241\1\1\u7875\u7102" + 
	"\u7E02\u0279\u0E04\u3E02\u5B02\u0101\1\1\1\uFF7A\u7373\200\4\u7714\u0241\1\4\u0240\u0242\1\1\1\1" + 
	"\u7875\u7102\u7E02\u0279\u0E04\u4101\uFF02\u0101\1\uFF02\u2304\u417A\u7373\200\4\u7710\u0243\1\2" + 
	"\224\u0244\1\1\u7875\u7102\u7E02\u0279\u0E04\u4301\uFF02\2\u3F04\u3F01\1\uFF7A\u7373\200\4\u7710" + 
	"\u0245\1\2\137\u0247\1\1\u7875\u7102\u7E02\u0279\u0E04\u4402\u5C02\u0101\1\1\1\uFF7A\u7373\200\4" + 
	"\u7714\u0247\1\4\u0246\u0248\1\1\1\1\u7875\u7102\u7E02\u0279\u1804\u4701\uFF02\u0601\uFF04\u4804" + 
	"\u4904\u4A04\u4C01\1\1\1\uFF7A\u7373\200\4\u7710\u0249\1\2\137\u0247\1\1\u7875\u7102\u7E02\u0279" + 
	"\u0E04\u4901\uFF02\u0101\1\uFF02\u5B04\u477A\u7373\200\4\u7710\u024B\1\2\1\1\203\u0249\u7875\u7102" + 
	"\u7E02\u0279\u0E04\u4B01\uFF02\u0101\1\uFF02\u6104\u477A\u7373\200\4\u7710\u024D\1\2\1\1\144\u0249" + 
	"\u7875\u7102\u7E02\u0279\u0E04\u4D01\uFF02\2\u5E04\u4F01\1\uFF7A\u7373\200\4\u7710\u024F\137\2\1" + 
	"\1\1\1\u7875\u7102\u7E02\u0279\u1204\u4F01\uFF02\u0204\u4D04\u5001\1\1\1\uFF7A\u7373\200\4\u7718" + 
	"\u0251\1\6\1\u0253\u0254\u0257\1\1\1\1\u7875\u7102\u7E02\u0279\u0E04\u5101\uFF02\2\u5E04\u4F01\1" + 
	"\uFF7A\u7373\200\4\u7710\u0253\1\2\1\1\162\u0252\u7875\u7102\u7E02\u0279\u0E04\u5301\uFF02\u0101" + 
	"\1\uFF02\u8104\u537A\u7373\200\4\u7710\u0255\1\2\1\1\203\u0256\u7875\u7102\u7E02\u0279\u0E04\u5501" + 
	"\uFF02\u0101\1\uFF02\u8104\u507A\u7373\200\4\u7710\u0257\1\2\1\1\203\u0258\u7875\u7102\u7E02\u0279" + 
	"\u0E04\u5701\uFF02\u0101\1\uFF02\u8104\u507A\u7373\200\4\u7710\u0259\1\2\141\u025B\1\1\u7875\u7102" + 
	"\u7E02\u0279\u0E04\u5802\u5E02\u0101\1\1\1\uFF7A\u7373\200\4\u7714\u025B\1\4\u025A\u025C\1\1\1\1" + 
	"\u7875\u7102\u7E02\u0279\u1404\u5B01\uFF02\u0401\uFF04\u5C04\u5E01\1\1\1\uFF7A\u7373\200\4\u7710" + 
	"\u025D\1\2\141\u025B\1\1\u7875\u7102\u7E02\u0279\u0E04\u5D01\uFF02\u0101\1\uFF02\u6804\u5B7A\u7373" + 
	"\200\4\u7710\u025F\1\2\1\1\153\u025D\u7875\u7102\u7E02\u0279\u0E04\u5F01\uFF02\2\u6004\u6101\1\uFF7A" + 
	"\u7373\200\4\u7710\u0261\141\2\1\1\1\1\u7875\u7102\u7E02\u0279\u1204\u6101\uFF02\u0204\u5F04\u6201" + 
	"\1\1\1\uFF7A\u7373\200\4\u7718\u0263\1\6\1\u0265\u0266\u0267\1\1\1\1\u7875\u7102\u7E02\u0279\u0E04" + 
	"\u6301\uFF02\2\u6004\u6101\1\uFF7A\u7373\200\4\u7710\u0265\1\2\1\1\154\u0264\u7875\u7102\u7E02\u0279" + 
	"\u0E04\u6501\uFF02\u0101\1\uFF02\u6B04\u627A\u7373\200\4\u7710\u0267\1\2\1\1\161\u0264\u7875\u7102" + 
	"\u7E02\u0279\u1604\u6701\uFF02\u0501\uFF04\u6804\u6904\u6E01\1\1\1\uFF7A\u7373\200\4\u7710\u0269" + 
	"\142\2\1\1\1\1\u7875\u7102\u7E02\u0279\u0E04\u6901\uFF02\2\u6104\u6801\1\uFF7A\u7373\200\4\u7716" + 
	"\u026B\1\5\1\u026D\u026E\1\1\1\1\u7875\u7102\u7E02\u0279\u0E04\u6B01\uFF02\2\u6004\u6801\1\uFF7A" + 
	"\u7373\200\4\u7710\u026D\1\2\1\1\152\u026C\u7875\u7102\u7E02\u0279\u0E04\u6D01\uFF02\u0101\1\uFF02" + 
	"\u6904\u6A7A\u7373\200\4\u7710\u026F\1\2\144\u0269\1\1\u7875\u7102\u7E02\u0279\u1404\u6F01\uFF02" + 
	"\u0401\uFF04\u7104\u7301\1\1\1\uFF7A\u7373\200\4\u7710\u0271\143\2\1\1\1\1\u7875\u7102\u7E02\u0279" + 
	"\u0E04\u7101\uFF02\2\u6004\u7001\1\uFF7A\u7373\200\4\u7710\u0273\1\2\1\1\150\u0272\u7875\u7102\u7E02" + 
	"\u0279\u0E04\u7301\uFF02\u0101\1\uFF02\u6704\u707A\u7373\200\4\u7718\u0275\1\6\1\u0277\u027B\u027C" + 
	"\1\1\1\1\u7875\u7102\u7E02\u0279\u0E04\u7402\u6202\u0101\1\1\1\uFF7A\u7373\200\4\u7716\u0277\1\5" + 
	"\1\u0279\u027A\1\1\1\1\u7875\u7102\u7E02\u0279\u0E04\u7701\uFF02\2\u6004\u7501\1\uFF7A\u7373\200" + 
	"\4\u7710\u0279\1\2\1\1\137\u0278\u7875\u7102\u7E02\u0279\u0E04\u7901\uFF02\u0101\1\uFF02\u5C04\u767A" + 
	"\u7373\200\4\u7710\u027B\1\2\146\u0276\1\1\u7875\u7102\u7E02\u0279\u0E04\u7B01\uFF02\2\u6304\u7501" + 
	"\1\uFF7A\u7373\200\4\u7710\u027D\1\2\151\u027F\1\1\u7875\u7102\u7E02\u0279\u0E04\u7C02\u6302\u0101" + 
	"\1\1\1\uFF7A\u7373\200\4\u7714\u027F\1\4\u027E\u0280\1\1\1\1\u7875\u7102\u7E02\u0279\u1404\u7F01" + 
	"\uFF02\u0401\uFF04\u7F04\u8101\1\1\1\uFF7A\u7373\200\4\u7710\u0281\1\2\1\1\150\u027E\u7875\u7102" + 
	"\u7E02\u0279\u0E04\u8101\uFF02\u0101\1\uFF02\u6704\u7C7A\u7373\200\4\u7710\u0283\1\2\1\1\122\u0285" + 
	"\u7875\u7102\u7E02\u0279\u0E04\u8202\u6402\u0101\1\1\1\uFF7A\u7373\200\4\u7710\u0285\1\2\224\u0286" + 
	"\1\1\u7875\u7102\u7E02\u0279\u1404\u8501\uFF02\u0401\uFF04\u8504\u8901\1\1\1\uFF7A\u7373\200\4\u7710" + 
	"\u0287\1\2\111\u0288\1\1\u7875\u7102\u7E02\u0279\u0E04\u8701\uFF02\u0101\1\uFF02\u5104\u877A\u7373" + 
	"\200\4\u7710\u0289\1\2\142\u0284\1\1\u7875\u7102\u7E02\u0279\u0E04\u8901\uFF02\2\u4004\u8A01\1\uFF7A" + 
	"\u7373\200\4\u7710\u028B\1\2\147\u028C\1\1\u7875\u7102\u7E02\u0279\u0E04\u8B01\uFF02\u0101\1\uFF02" + 
	"\u5104\u8B7A\u7373\200\4\u7710\u028D\1\2\144\u0284\1\1\u7875\u7102\u7E02\u0279\u1204\u8D01\uFF02" + 
	"\u0204\u8D04\u8F01\1\1\1\uFF7A\u7373\200\4\u7710\u028F\147\2\1\1\1\1\u7875\u7102\u7E02\u0279\u0E04" + 
	"\u8F01\uFF02\u0101\1\uFF02\u6C04\u907A\u7373\200\4\u7714\u0291\1\4\u028F\u0290\1\1\1\1\u7875\u7102" + 
	"\u7E02\u0279\u0E04\u9101\uFF02\2\u9204\u9201\1\uFF7A\u7373\200\4\u7710\u0293\1\2\102\u0291\1\1\u7875" + 
	"\u7102\u7E02\u0279\u2204\u9301\uFF02\u0B01\uFF04\u9404\u9504\u9604\u9704\u9804\u9904\u9A04\u9B04" + 
	"\u9D01\1\1\1\uFF7A\u7373\200\4\u7710\u0295\150\2\1\1\1\1\u7875\u7102\u7E02\u0279\u0E04\u9501\uFF02" + 
	"\u0101\1\uFF02\u3F04\u937A\u7373\200\4\u7710\u0297\1\2\1\1\100\u0295\u7875\u7102\u7E02\u0279\u0E04" + 
	"\u9701\uFF02\u0101\1\uFF02\u4404\u937A\u7373\200\4\u7710\u0299\1\2\1\1\107\u0295\u7875\u7102\u7E02" + 
	"\u0279\u0E04\u9901\uFF02\u0101\1\uFF02\u4E04\u937A\u7373\200\4\u7710\u029B\1\2\1\1\121\u0295\u7875" + 
	"\u7102\u7E02\u0279\u0E04\u9B01\uFF02\u0101\1\uFF02\u3904\u937A\u7373\200\4\u7710\u029D\1\2\1\1\34" + 
	"\u0295\u7875\u7102\u7E02\u0279\u0E04\u9D01\uFF02\u0101\1\uFF02\u2904\u937A\u7373\200\4\u7716\u029F" + 
	"\1\5\1\u02A1\u02A2\1\1\1\1\u7875\u7102\u7E02\u0279\u0E04\u9E02\u6702\u0101\1\1\1\uFF7A\u7373\200" + 
	"\4\u7710\u02A1\1\2\152\u02A0\1\1\u7875\u7102\u7E02\u0279\u0E04\uA101\uFF02\2\u7204\u9F01\1\uFF7A" + 
	"\u7373\200\4\u7710\u02A3\1\2\154\u02A5\1\1\u7875\u7102\u7E02\u0279\u0E04\uA202\u6802\u0101\1\1\1" + 
	"\uFF7A\u7373\200\4\u7714\u02A5\1\4\u02A4\u02A6\1\1\1\1\u7875\u7102\u7E02\u0279\u0E04\uA501\uFF02" + 
	"\2\u6B04\uA401\1\uFF7A\u7373\200\4\u7710\u02A7\1\2\154\u02A9\1\1\u7875\u7102\u7E02\u0279\u0E04\uA602" + 
	"\u6902\u0101\1\1\1\uFF7A\u7373\200\4\u7714\u02A9\1\4\u02A8\u02AA\1\1\1\1\u7875\u7102\u7E02\u0279" + 
	"\u0E04\uA901\uFF02\2\u6C04\uA801\1\uFF7A\u7373\200\4\u7724\u02AB\1\14\1\u02AD\u02AE\u02AF\u02B6" + 
	"\u02B7\u02BA\u02BC\u02BD\u02BE\1\1\1\1\u7875\u7102\u7E02\u0279\u0E04\uAA02\u6A02\u0101\1\1\1\uFF7A" + 
	"\u7373\200\4\u7710\u02AD\1\2\150\u02AC\1\1\u7875\u7102\u7E02\u0279\u0E04\uAD01\uFF02\u0101\1\uFF02" + 
	"\u3504\uAA7A\u7373\200\4\u7710\u02AF\1\2\1\1\64\u02B0\u7875\u7102\u7E02\u0279\u1404\uAF01\uFF02" + 
	"\u0401\uFF04\uAF04\uB401\1\1\1\uFF7A\u7373\200\4\u7710\u02B1\1\2\1\1\132\u02B2\u7875\u7102\u7E02" + 
	"\u0279\u1404\uB101\uFF02\u0401\uFF04\uB104\uB301\1\1\1\uFF7A\u7373\200\4\u7710\u02B3\1\2\160\u02AC" + 
	"\1\1\u7875\u7102\u7E02\u0279\u0E04\uB301\uFF02\2\u6D04\uAB01\1\uFF7A\u7373\200\4\u7710\u02B5\1\2" + 
	"\162\u02AC\1\1\u7875\u7102\u7E02\u0279\u0E04\uB501\uFF02\2\u7104\uAB01\1\uFF7A\u7373\200\4\u7710" + 
	"\u02B7\1\2\112\u02B8\1\1\u7875\u7102\u7E02\u0279\u0E04\uB701\uFF02\u0101\1\uFF02\u5804\uB77A\u7373" + 
	"\200\4\u7710\u02B9\1\2\1\1\23\u02AC\u7875\u7102\u7E02\u0279\u0E04\uB901\uFF02\2\u4804\uBA01\1\uFF7A" + 
	"\u7373\200\4\u7710\u02BB\1\2\162\u02AC\1\1\u7875\u7102\u7E02\u0279\u0E04\uBB01\uFF02\2\u6E04\uAB01" + 
	"\1\uFF7A\u7373\200\4\u7710\u02BD\1\2\115\u02AC\1\1\u7875\u7102\u7E02\u0279\u0E04\uBD01\uFF02\u0101" + 
	"\1\uFF02\u5004\uBD7A\u7373\200\4\u7710\u02BF\1\2\116\u02C0\1\1\u7875\u7102\u7E02\u0279\u0E04\uBF01" + 
	"\uFF02\u0101\1\uFF02\u5104\uAA7A\u7373\200\4\u7718\u02C1\1\6\1\u02C3\u02C4\u02C6\1\1\1\1\u7875\u7102" + 
	"\u7E02\u0279\u0E04\uC002\u6B02\u0101\1\1\1\uFF7A\u7373\200\4\u7710\u02C3\1\2\156\u02C2\1\1\u7875" + 
	"\u7102\u7E02\u0279\u0E04\uC301\uFF02\u0101\1\uFF02\u5804\uC37A\u7373\200\4\u7710\u02C5\1\2\1\1\64" + 
	"\u02C2\u7875\u7102\u7E02\u0279\u0E04\uC501\uFF02\2\u7004\uC101\1\uFF7A\u7373\200\4\u7716\u02C7\1" + 
	"\5\1\u02C9\u02CF\1\1\1\1\u7875\u7102\u7E02\u0279\u0E04\uC602\u6C02\u0101\1\1\1\uFF7A\u7373\200\4" + 
	"\u7710\u02C9\1\2\1\1\132\u02CA\u7875\u7102\u7E02\u0279\u1804\uC901\uFF02\u0601\uFF04\uC904\uCA04" + 
	"\uCB04\uCD01\1\1\1\uFF7A\u7373\200\4\u7710\u02CB\1\2\1\1\67\u02C8\u7875\u7102\u7E02\u0279\u0E04" + 
	"\uCB01\uFF02\2\u7104\uC701\1\uFF7A\u7373\200\4\u7710\u02CD\1\2\160\u02C8\1\1\u7875\u7102\u7E02\u0279" + 
	"\u0E04\uCD01\uFF02\2\u6D04\uC701\1\uFF7A\u7373\200\4\u7710\u02CF\1\2\1\1\126\u02D0\u7875\u7102\u7E02" + 
	"\u0279\u0E04\uCF01\uFF02\2\u4C04\uD001\1\uFF7A\u7373\200\4\u7710\u02D1\1\2\1\1\127\u02C8\u7875\u7102" + 
	"\u7E02\u0279\u0E04\uD101\uFF02\2\u4B04\uD201\1\uFF7A\u7373\200\4\u7710\u02D3\157\2\1\1\1\1\u7875" + 
	"\u7102\u7E02\u0279\u1204\uD301\uFF02\u0204\uD404\uD601\1\1\1\uFF7A\u7373\200\4\u7710\u02D5\160\2" + 
	"\1\1\1\1\u7875\u7102\u7E02\u0279\u0E04\uD501\uFF02\2\u4B04\uD701\1\uFF7A\u7373\200\4\u7710\u02D7" + 
	"\1\2\104\u02D6\1\1\u7875\u7102\u7E02\u0279\u0E04\uD701\uFF02\2\u6F04\uD401\1\uFF7A\u7373\200\4\u7710" + 
	"\u02D9\1\2\1\1\122\u02DB\u7875\u7102\u7E02\u0279\u0E04\uD802\u6F02\u0101\1\1\1\uFF7A\u7373\200\4" + 
	"\u7714\u02DB\1\4\u02DC\u02DD\1\1\1\1\u7875\u7102\u7E02\u0279\u0E04\uDB01\uFF02\u0101\1\uFF02\u5104" + 
	"\uD87A\u7373\200\4\u7716\u02DD\1\5\1\u02DE\u02DF\1\1\1\1\u7875\u7102\u7E02\u0279\u0E04\uDD01\uFF02" + 
	"\2\u1504\uDB01\1\uFF7A\u7373\200\4\u7710\u02DF\1\2\116\u02E0\1\1\u7875\u7102\u7E02\u0279\u1204\uDF01" + 
	"\uFF02\u0204\uDA04\uE001\1\1\1\uFF7A\u7373\200\4\u7710\u02E1\1\2\1\1\131\u02E2\u7875\u7102\u7E02" + 
	"\u0279\u0E04\uE101\uFF02\2\u4C04\uDF01\1\uFF7A\u7373\200\4\u7710\u02E3\1\2\1\1\200\u02E5\u7875\u7102" + 
	"\u7E02\u0279\u0E04\uE202\u7002\u0101\1\1\1\uFF7A\u7373\200\4\u7714\u02E5\1\4\u02E6\u02E7\1\1\1\1" + 
	"\u7875\u7102\u7E02\u0279\u1404\uE501\uFF02\u0401\uFF04\uE604\uE801\1\1\1\uFF7A\u7373\200\4\u7710" + 
	"\u02E7\1\2\104\u02E6\1\1\u7875\u7102\u7E02\u0279\u0E04\uE701\uFF02\2\u4B04\uE301\1\uFF7A\u7373\200" + 
	"\4\u7710\u02E9\1\2\1\1\52\u02E4\u7875\u7102\u7E02\u0279\u0E04\uE901\uFF02\u0101\1\uFF02\u2804\uEA7A" + 
	"\u7373\200\4\u7710\u02EB\163\2\1\1\1\1\u7875\u7102\u7E02\u0279\u1204\uEB01\uFF02\u0204\uEB04\uED01" + 
	"\1\1\1\uFF7A\u7373\200\4\u7710\u02ED\1\2\224\u02EF\1\1\u7875\u7102\u7E02\u0279\u0E04\uED01\uFF02" + 
	"\2\u4204\uEC01\1\uFF7A\u7373\200\4\u7710\u02EF\1\2\103\u02F0\1\1\u7875\u7102\u7E02\u0279\u0E04\uEF01" + 
	"\uFF02\2\u6F04\uF001\1\uFF7A\u7373\200\4\u7714\u02F1\1\4\u02EB\u02F2\1\1\1\1\u7875\u7102\u7E02\u0279" + 
	"\u0E04\uF101\uFF02\2\u2B04\uEA01\1\uFF7A\u7373\200\4\u7710\u02F3\1\2\1\1\52\u02F5\u7875\u7102\u7E02" + 
	"\u0279\u0E04\uF202\u7202\u0101\1\1\1\uFF7A\u7373\200\4\u7714\u02F5\1\4\u02F6\u02F7\1\1\1\1\u7875" + 
	"\u7102\u7E02\u0279\u0E04\uF501\uFF02\2\u9204\uF701\1\uFF7A\u7373\200\4\u7710\u02F7\1\2\104\u02F6" + 
	"\1\1\u7875\u7102\u7E02\u0279\u1404\uF701\uFF02\u0401\uFF04\uF804\uFA01\1\1\1\uFF7A\u7373\200\4\u7710" + 
	"\u02F9\1\2\165\u02F4\1\1\u7875\u7102\u7E02\u0279\u0E04\uF901\uFF02\2\u4704\uF801\1\uFF7A\u7373\200" + 
	"\4\u7710\u02FB\1\2\103\u02F9\1\1\u7875\u7102\u7E02\u0279\u1404\uFB01\uFF02\u0401\uFF04\uFC04\uFF01" + 
	"\1\1\1\uFF7A\u7373\200\4\u7710\u02FD\165\2\1\1\1\1\u7875\u7102\u7E02\u0279\u0E04\uFD01\uFF02\2\u7404" + 
	"\uFE01\1\uFF7A\u7373\200\4\u7710\u02FF\1\2\65\u02FD\1\1\u7875\u7102\u7E02\u0279\u0E04\uFF01\uFF02" + 
	"\2\u7504\1\1\uFF7A\u7373\200\4\u7710\u0301\1\2\67\u02FD\1\1\u7875\u7102\u7E02\u0279\u0E05\u0101" + 
	"\uFF02\2\u9205\u0401\1\uFF7A\u7373\200\4\u7710\u0303\166\2\1\1\1\1\u7875\u7102\u7E02\u0279\u1205" + 
	"\u0301\uFF02\u0205\u0105\u0101\1\1\1\uFF7A\u7373\200\4\u7710\u0305\1\2\1\1\126\u0306\u7875\u7102" + 
	"\u7E02\u0279\u0E05\u0501\uFF02\2\u4C05\u0601\1\uFF7A\u7373\200\4\u7710\u0307\1\2\1\1\127\u0304\u7875" + 
	"\u7102\u7E02\u0279\u0E05\u0701\uFF02\2\u9205\u0A01\1\uFF7A\u7373\200\4\u7710\u0309\167\2\1\1\1\1" + 
	"\u7875\u7102\u7E02\u0279\u1205\u0901\uFF02\u0205\u0705\u0701\1\1\1\uFF7A\u7373\200\4\u7710\u030B" + 
	"\1\2\1\1\126\u030C\u7875\u7102\u7E02\u0279\u0E05\u0B01\uFF02\u0101\1\uFF02\u5505\u087A\u7373\200" + 
	"\4\u7732\u030D\1\23\1\u030F\u0310\u0311\u0312\u0313\u0314\u0315\u0316\u0317\u0318\u0319\u031A\u031B" + 
	"\u031C\u031D\u031E\1\1\1\1\u7875\u7102\u7E02\u0279\u0E05\u0C02\u7602\u0101\1\1\1\uFF7A\u7373\200" + 
	"\4\u7710\u030F\1\2\172\u030E\1\1\u7875\u7102\u7E02\u0279\u0E05\u0F01\uFF02\2\u7705\u0D01\1\uFF7A" + 
	"\u7373\200\4\u7710\u0311\1\2\173\u030E\1\1\u7875\u7102\u7E02\u0279\u0E05\u1101\uFF02\2\u7C05\u0D01" + 
	"\1\uFF7A\u7373\200\4\u7710\u0313\1\2\177\u030E\1\1\u7875\u7102\u7E02\u0279\u0E05\u1301\uFF02\2\u7F05" + 
	"\u0D01\1\uFF7A\u7373\200\4\u7710\u0315\1\2\203\u030E\1\1\u7875\u7102\u7E02\u0279\u0E05\u1501\uFF02" + 
	"\2\u8205\u0D01\1\uFF7A\u7373\200\4\u7710\u0317\1\2\205\u030E\1\1\u7875\u7102\u7E02\u0279\u0E05\u1701" + 
	"\uFF02\2\u8405\u0D01\1\uFF7A\u7373\200\4\u7710\u0319\1\2\212\u030E\1\1\u7875\u7102\u7E02\u0279\u0E05" + 
	"\u1901\uFF02\2\u8905\u0D01\1\uFF7A\u7373\200\4\u7710\u031B\1\2\214\u030E\1\1\u7875\u7102\u7E02\u0279" + 
	"\u0E05\u1B01\uFF02\2\u8B05\u0D01\1\uFF7A\u7373\200\4\u7710\u031D\1\2\216\u030E\1\1\u7875\u7102\u7E02" + 
	"\u0279\u0E05\u1D01\uFF02\2\u8D05\u0D01\1\uFF7A\u7373\200\4\u7710\u031F\1\2\1\1\14\u0321\u7875\u7102" + 
	"\u7E02\u0279\u0E05\u1E02\u7702\u0101\1\1\1\uFF7A\u7373\200\4\u7710\u0321\1\2\116\u0322\1\1\u7875" + 
	"\u7102\u7E02\u0279\u1205\u2101\uFF02\u0205\u2105\u2301\1\1\1\uFF7A\u7373\200\4\u7710\u0323\1\2\1" + 
	"\1\130\u0320\u7875\u7102\u7E02\u0279\u0E05\u2301\uFF02\u0101\1\uFF02\u5F05\u237A\u7373\200\4\u7710" + 
	"\u0325\1\2\116\u0323\1\1\u7875\u7102\u7E02\u0279\u0E05\u2501\uFF02\2\u4B05\u2701\1\uFF7A\u7373\200" + 
	"\4\u7710\u0327\172\2\1\1\1\1\u7875\u7102\u7E02\u0279\u0E05\u2701\uFF02\u0101\1\uFF02\u5F05\u277A" + 
	"\u7373\200\4\u7710\u0329\1\2\170\u0327\1\1\u7875\u7102\u7E02\u0279\u0E05\u2901\uFF02\u0101\1\uFF02" + 
	"\u5205\u2A7A\u7373\200\4\u7710\u032B\173\2\1\1\1\1\u7875\u7102\u7E02\u0279\u0E05\u2B01\uFF02\2\u3D05" + 
	"\u2C01\1\uFF7A\u7373\200\4\u7710\u032D\1\2\1\1\125\u032B\u7875\u7102\u7E02\u0279\u1605\u2D01\uFF02" + 
	"\u0501\uFF05\u2E05\u3005\u3301\1\1\1\uFF7A\u7373\200\4\u7710\u032F\174\2\1\1\1\1\u7875\u7102\u7E02" + 
	"\u0279\u0E05\u2F01\uFF02\2\u1D05\u3001\1\uFF7A\u7373\200\4\u7710\u0331\1\2\41\u032F\1\1\u7875\u7102" + 
	"\u7E02\u0279\u0E05\u3101\uFF02\2\u7B05\u3201\1\uFF7A\u7373\200\4\u7710\u0333\1\2\1\1\130\u032F\u7875" + 
	"\u7102\u7E02\u0279\u0E05\u3301\uFF02\2\u7605\u2E01\1\uFF7A\u7373\200\4\u7710\u0335\1\2\37\u0337" + 
	"\1\1\u7875\u7102\u7E02\u0279\u0E05\u3402\u7B02\u0101\1\1\1\uFF7A\u7373\200\4\u7710\u0337\1\2\61" + 
	"\u0336\1\1\u7875\u7102\u7E02\u0279\u0E05\u3701\uFF02\u0101\1\uFF02\u5605\u377A\u7373\200\4\u7710" + 
	"\u0339\176\2\1\1\1\1\u7875\u7102\u7E02\u0279\u0E05\u3901\uFF02\2\u7E05\u3B01\1\uFF7A\u7373\200\4" + 
	"\u7710\u033B\177\2\1\1\1\1\u7875\u7102\u7E02\u0279\u0E05\u3B01\uFF02\u0101\1\uFF02\u5605\u397A\u7373" + 
	"\200\4\u7710\u033D\1\2\116\u033E\1\1\u7875\u7102\u7E02\u0279\u0E05\u3C02\u7E02\u0101\1\1\1\uFF7A" + 
	"\u7373\200\4\u7710\u033F\1\2\1\1\65\u0341\u7875\u7102\u7E02\u0279\u0E05\u3E02\u7F02\u0101\1\1\1" + 
	"\uFF7A\u7373\200\4\u7710\u0341\1\2\1\1\122\u0342\u7875\u7102\u7E02\u0279\u0E05\u4101\uFF02\2\u4C05" + 
	"\u4201\1\uFF7A\u7373\200\4\u7710\u0343\1\2\1\1\123\u0344\u7875\u7102\u7E02\u0279\u0E05\u4301\uFF02" + 
	"\u0101\1\uFF02\u5205\u437A\u7373\200\4\u7714\u0345\1\4\u0346\u0347\1\1\1\1\u7875\u7102\u7E02\u0279" + 
	"\u0E05\u4501\uFF02\u0101\1\uFF02\u5305\u3E7A\u7373\200\4\u7710\u0347\1\2\202\u0345\1\1\u7875\u7102" + 
	"\u7E02\u0279\u1405\u4701\uFF02\u0401\uFF05\u4905\u4C01\1\1\1\uFF7A\u7373\200\4\u7710\u0349\202\2" + 
	"\1\1\1\1\u7875\u7102\u7E02\u0279\u0E05\u4901\uFF02\u0101\1\uFF02\u5F05\u4C7A\u7373\200\4\u7710\u034B" + 
	"\1\2\1\1\20\u034C\u7875\u7102\u7E02\u0279\u0E05\u4B01\uFF02\2\u4C05\u4901\1\uFF7A\u7373\200\4\u7710" + 
	"\u034D\1\2\1\1\26\u034A\u7875\u7102\u7E02\u0279\u0E05\u4D01\uFF02\2\u3D05\u4801\1\uFF7A\u7373\200" + 
	"\4\u7710\u034F\1\2\1\1\42\u0351\u7875\u7102\u7E02\u0279\u0E05\u4E02\u8102\u0101\1\1\1\uFF7A\u7373" + 
	"\200\4\u7710\u0351\1\2\1\1\122\u0352\u7875\u7102\u7E02\u0279\u0E05\u5101\uFF02\2\u4C05\u5201\1\uFF7A" + 
	"\u7373\200\4\u7710\u0353\1\2\1\1\123\u0354\u7875\u7102\u7E02\u0279\u0E05\u5301\uFF02\2\u7605\u5401" + 
	"\1\uFF7A\u7373\200\4\u7714\u0355\1\4\u0350\u0356\1\1\1\1\u7875\u7102\u7E02\u0279\u0E05\u5501\uFF02" + 
	"\u0101\1\uFF02\u1705\u557A\u7373\200\4\u7710\u0357\1\2\170\u0350\1\1\u7875\u7102\u7E02\u0279\u0E05" + 
	"\u5701\uFF02\u0101\1\uFF02\u3D05\u587A\u7373\200\4\u7710\u0359\204\2\1\1\1\1\u7875\u7102\u7E02\u0279" + 
	"\u0E05\u5901\uFF02\u0101\1\uFF02\u5005\u597A\u7373\200\4\u7710\u035B\1\2\116\u035C\1\1\u7875\u7102" + 
	"\u7E02\u0279\u0E05\u5B01\uFF02\u0101\1\uFF02\u5105\u5B7A\u7373\200\4\u7710\u035D\1\2\170\u0359\1" + 
	"\1\u7875\u7102\u7E02\u0279\u0E05\u5D01\uFF02\u0101\1\uFF02\u1505\u5E7A\u7373\200\4\u7710\u035F\205" + 
	"\2\1\1\1\1\u7875\u7102\u7E02\u0279\u0E05\u5F01\uFF02\2\u7605\u6001\1\uFF7A\u7373\200\4\u7710\u0361" + 
	"\1\2\1\1\77\u0362\u7875\u7102\u7E02\u0279\u0E05\u6101\uFF02\u0101\1\uFF02\u5005\u617A\u7373\200" + 
	"\4\u7710\u0363\1\2\116\u0364\1\1\u7875\u7102\u7E02\u0279\u0E05\u6301\uFF02\u0101\1\uFF02\u5105\u637A" + 
	"\u7373\200\4\u7710\u0365\1\2\1\1\130\u035F\u7875\u7102\u7E02\u0279\u0E05\u6501\uFF02\u0101\1\uFF02" + 
	"\u1E05\u667A\u7373\200\4\u7710\u0367\206\2\1\1\1\1\u7875\u7102\u7E02\u0279\u0E05\u6701\uFF02\u0101" + 
	"\1\uFF02\u5005\u677A\u7373\200\4\u7716\u0369\1\5\1\u036B\u036E\1\1\1\1\u7875\u7102\u7E02\u0279\u0E05" + 
	"\u6901\uFF02\u0101\1\uFF02\u5105\u747A\u7373\200\4\u7710\u036B\1\2\175\u036C\1\1\u7875\u7102\u7E02" + 
	"\u0279\u0E05\u6B01\uFF02\u0101\1\uFF02\u5F05\u6B7A\u7373\200\4\u7710\u036D\1\2\116\u036A\1\1\u7875" + 
	"\u7102\u7E02\u0279\u1205\u6D01\uFF02\u0205\u6D05\u6F01\1\1\1\uFF7A\u7373\200\4\u7710\u036F\1\2\1" + 
	"\1\130\u0371\u7875\u7102\u7E02\u0279\u0E05\u6F01\uFF02\2\u8505\u6E01\1\uFF7A\u7373\200\4\u7714\u0371" + 
	"\1\4\u0372\u0373\1\1\1\1\u7875\u7102\u7E02\u0279\u0E05\u7101\uFF02\u0101\1\uFF02\u5605\u727A\u7373" + 
	"\200\4\u7710\u0373\1\2\116\u0372\1\1\u7875\u7102\u7E02\u0279\u1205\u7301\uFF02\u0205\u6805\u7401" + 
	"\1\1\1\uFF7A\u7373\200\4\u7710\u0375\1\2\211\u036A\1\1\u7875\u7102\u7E02\u0279\u0E05\u7501\uFF02" + 
	"\2\u7605\u6601\1\uFF7A\u7373\200\4\u7716\u0377\1\5\1\u0379\u037A\1\1\1\1\u7875\u7102\u7E02\u0279" + 
	"\u0E05\u7602\u8502\u0101\1\1\1\uFF7A\u7373\200\4\u7710\u0379\1\2\175\u0378\1\1\u7875\u7102\u7E02" + 
	"\u0279\u0E05\u7901\uFF02\2\u8605\u7701\1\uFF7A\u7373\200\4\u7710\u037B\1\2\200\u037D\1\1\u7875\u7102" + 
	"\u7E02\u0279\u0E05\u7A02\u8602\u0101\1\1\1\uFF7A\u7373\200\4\u7714\u037D\1\4\u037C\u037E\1\1\1\1" + 
	"\u7875\u7102\u7E02\u0279\u0E05\u7D01\uFF02\u0101\1\uFF02\u5705\u7D7A\u7373\200\4\u7710\u037F\1\2" + 
	"\200\u037D\1\1\u7875\u7102\u7E02\u0279\u0E05\u7F01\uFF02\2\u8605\u8001\1\uFF7A\u7373\200\4\u7710" + 
	"\u0381\211\2\1\1\1\1\u7875\u7102\u7E02\u0279\u0E05\u8101\uFF02\u0101\1\uFF02\u0C05\u827A\u7373\200" + 
	"\4\u7710\u0383\212\2\1\1\1\1\u7875\u7102\u7E02\u0279\u1205\u8301\uFF02\u0205\u8305\u8501\1\1\1\uFF7A" + 
	"\u7373\200\4\u7710\u0385\1\2\1\1\130\u0383\u7875\u7102\u7E02\u0279\u0E05\u8501\uFF02\2\u4B05\u8401" + 
	"\1\uFF7A\u7373\200\4\u7710\u0387\1\2\1\1\25\u0389\u7875\u7102\u7E02\u0279\u0E05\u8602\u8902\u0101" + 
	"\1\1\1\uFF7A\u7373\200\4\u7714\u0389\1\4\u038A\u038B\1\1\1\1\u7875\u7102\u7E02\u0279\u0E05\u8901" + 
	"\uFF02\u0101\1\uFF02\u5605\u867A\u7373\200\4\u7710\u038B\1\2\115\u038A\1\1\u7875\u7102\u7E02\u0279" + 
	"\u0E05\u8B01\uFF02\u0101\1\uFF02\u2E05\u8C7A\u7373\200\4\u7710\u038D\214\2\1\1\1\1\u7875\u7102\u7E02" + 
	"\u0279\u1205\u8D01\uFF02\u0205\u8D05\u8F01\1\1\1\uFF7A\u7373\200\4\u7710\u038F\1\2\1\1\130\u038D" + 
	"\u7875\u7102\u7E02\u0279\u0E05\u8F01\uFF02\2\u4C05\u8E01\1\uFF7A\u7373\200\4\u7710\u0391\1\2\1\1" + 
	"\70\u0393\u7875\u7102\u7E02\u0279\u0E05\u9002\u8B02\u0101\1\1\1\uFF7A\u7373\200\4\u7710\u0393\1" + 
	"\2\116\u0394\1\1\u7875\u7102\u7E02\u0279\u0E05\u9301\uFF02\u0101\1\uFF02\u5605\u907A\u7373\200\4" + 
	"\u7710\u0395\1\2\1\1\66\u0397\u7875\u7102\u7E02\u0279\u0E05\u9402\u8C02\u0101\1\1\1\uFF7A\u7373" + 
	"\200\4\u7710\u0397\1\2\1\1\122\u0398\u7875\u7102\u7E02\u0279\u0E05\u9701\uFF02\2\u4C05\u9801\1\uFF7A" + 
	"\u7373\200\4\u7710\u0399\1\2\1\1\123\u039A\u7875\u7102\u7E02\u0279\u0E05\u9901\uFF02\2\u7905\u9501" + 
	"\1\uFF7A\u7373\200\4\u7710\u039B\1\2\1\1\74\u039D\u7875\u7102\u7E02\u0279\u0E05\u9A02\u8D02\u0101" + 
	"\1\1\1\uFF7A\u7373\200\4\u7716\u039D\1\5\1\u039E\u03A5\1\1\1\1\u7875\u7102\u7E02\u0279\u0E05\u9D01" + 
	"\uFF02\2\u9105\u9E01\1\uFF7A\u7373\200\4\u7710\u039F\1\2\173\u03A0\1\1\u7875\u7102\u7E02\u0279\u1205" + 
	"\u9F01\uFF02\u0205\u9F05\uA101\1\1\1\uFF7A\u7373\200\4\u7714\u03A1\1\4\u039C\u03A3\1\1\1\1\u7875" + 
	"\u7102\u7E02\u0279\u0E05\uA101\uFF02\2\u8E05\uA001\1\uFF7A\u7373\200\4\u7710\u03A3\1\2\1\1\36\u03A4" + 
	"\u7875\u7102\u7E02\u0279\u0E05\uA301\uFF02\2\u7905\u9B01\1\uFF7A\u7373\200\4\u7710\u03A5\1\2\173" + 
	"\u03A6\1\1\u7875\u7102\u7E02\u0279\u1405\uA501\uFF02\u0401\uFF05\uA505\uAA01\1\1\1\uFF7A\u7373\200" + 
	"\4\u7710\u03A7\1\2\220\u03A8\1\1\u7875\u7102\u7E02\u0279\u1205\uA701\uFF02\u0205\u9A05\uA801\1\1" + 
	"\1\uFF7A\u7373\200\4\u7710\u03A9\1\2\1\1\36\u03AA\u7875\u7102\u7E02\u0279\u0E05\uA901\uFF02\2\u7905" + 
	"\u9B01\1\uFF7A\u7373\200\4\u7710\u03AB\1\2\1\1\36\u03AC\u7875\u7102\u7E02\u0279\u0E05\uAB01\uFF02" + 
	"\2\u7905\u9B01\1\uFF7A\u7373\200\4\u7710\u03AD\1\2\221\u03AF\1\1\u7875\u7102\u7E02\u0279\u0E05\uAC02" + 
	"\u8E02\u0101\1\1\1\uFF7A\u7373\200\4\u7714\u03AF\1\4\u03AE\u03AD\1\1\1\1\u7875\u7102\u7E02\u0279" + 
	"\u0E05\uAF01\uFF02\u0101\1\uFF02\u0F05\uB07A\u7373\200\4\u7710\u03B1\221\2\1\1\1\1\u7875\u7102\u7E02" + 
	"\u0279\u0E05\uB101\uFF02\u0101\1\uFF02\u5005\uB17A\u7373\200\4\u7710\u03B3\1\2\222\u03B4\1\1\u7875" + 
	"\u7102\u7E02\u0279\u0E05\uB301\uFF02\u0101\1\uFF02\u5105\uB37A\u7373\200\4\u7710\u03B5\1\2\173\u03B1" + 
	"\1\1\u7875\u7102\u7E02\u0279\u0E05\uB501\uFF02\2\u1C05\uB701\1\uFF7A\u7373\200\4\u7710\u03B7\222" + 
	"\2\1\1\1\1\u7875\u7102\u7E02\u0279\u0E05\uB701\uFF02\2\u4105\uB801\1\uFF7A\u7373\200\4\u7714\u03B9" + 
	"\1\4\u03BA\u03BB\1\1\1\1\u7875\u7102\u7E02\u0279\u0E05\uB901\uFF02\2\u3205\uB601\1\uFF7A\u7373\200" + 
	"\4\u7710\u03BB\1\2\1\1\157\u03BD\u7875\u7102\u7E02\u0279\u1205\uBB01\uFF02\u0205\uB805\uBA01\1\1" + 
	"\1\uFF7A\u7373\200\4\u7710\u03BD\1\2\113\u03BC\1\1\u7875\u7102\u7E02\u0279\u0E05\uBD01\uFF02\u0101" + 
	"\1\uFF02\u5005\uBE7A\u7373\200\4\u7710\u03BF\223\2\1\1\1\1\u7875\u7102\u7E02\u0279\u0E05\uBF01\uFF02" + 
	"\2\u7B05\uC001\1\uFF7A\u7373\200\4\u7714\u03C1\1\4\u03C2\u03C3\1\1\1\1\u7875\u7102\u7E02\u0279\u1205" + 
	"\uC101\uFF02\u0205\uC305\uC501\1\1\1\uFF7A\u7373\200\4\u7710\u03C3\1\2\1\1\130\u03C4\u7875\u7102" + 
	"\u7E02\u0279\u0E05\uC301\uFF02\2\u7B05\uC001\1\uFF7A\u7373\200\4\u7710\u03C5\1\2\1\1\123\u03BF\u7875" + 
	"\u7102\u7E02\u0279\u0E05\uC501\uFF02\u0101\1\uFF02\u5605\uC37A\u7373\200\4\u7714\u03C7\1\4\u03C8" + 
	"\u03C9\1\1\1\1\u7875\u7102\u7E02\u0279\u0E05\uC602\u9202\u0101\1\1\1\uFF7A\u7373\200\4\u7710\u03C9" + 
	"\1\2\225\u03C7\1\1\u7875\u7102\u7E02\u0279\u1605\uC901\uFF02\u0501\uFF05\uCA05\uCB05\uCD01\1\1\1" + 
	"\uFF7A\u7373\200\4\u7710\u03CB\225\2\1\1\1\1\u7875\u7102\u7E02\u0279\u0E05\uCB01\uFF02\2\u9405\uCA01" + 
	"\1\uFF7A\u7373\200\4\u7710\u03CD\1\2\227\u03CB\1\1\u7875\u7102\u7E02\u0279\u0E05\uCD01\uFF02\2\u9605" + 
	"\uCA01\1\uFF7A\u7373\200\4\u7710\u03CF\1\2\1\1\133\u03D1\u7875\u7102\u7E02\u0279\u0E05\uCE02\u9402" + 
	"\u0101\1\1\1\uFF7A\u7373\200\4\u7710\u03D1\1\2\114\u03D2\1\1\u7875\u7102\u7E02\u0279\u0E05\uD101" + 
	"\uFF02\u0101\1\uFF02\u5005\uD17A\u7373\200\4\u7714\u03D3\1\4\u03D4\u03D5\1\1\1\1\u7875\u7102\u7E02" + 
	"\u0279\u0E05\uD301\uFF02\u0101\1\uFF02\u5105\uCE7A\u7373\200\4\u7710\u03D5\1\2\231\u03D4\1\1\u7875" + 
	"\u7102\u7E02\u0279\u0E05\uD501\uFF02\u0101\1\uFF02\u5905\uD67A\u7373\200\4\u7710\u03D7\227\2\1\1" + 
	"\1\1\u7875\u7102\u7E02\u0279\u0E05\uD701\uFF02\2\u4A05\uD601\1\uFF7A\u7373\200\4\u7710\u03D9\1\2" + 
	"\1\1\133\u03DB\u7875\u7102\u7E02\u0279\u0E05\uD802\u9602\u0101\1\1\1\uFF7A\u7373\200\4\u7710\u03DB" + 
	"\1\2\114\u03DC\1\1\u7875\u7102\u7E02\u0279\u0E05\uDB01\uFF02\u0101\1\uFF02\u5005\uDB7A\u7373\200" + 
	"\4\u7710\u03DD\1\2\233\u03DE\1\1\u7875\u7102\u7E02\u0279\u0E05\uDD01\uFF02\u0101\1\uFF02\u5105\uD87A" + 
	"\u7373\200\4\u7710\u03DF\1\2\232\u03E1\1\1\u7875\u7102\u7E02\u0279\u0E05\uDE02\u9702\u0101\1\1\1" + 
	"\uFF7A\u7373\200\4\u7714\u03E1\1\4\u03E0\u03E2\1\1\1\1\u7875\u7102\u7E02\u0279\u0E05\uE101\uFF02" + 
	"\u0101\1\uFF02\u5705\uE17A\u7373\200\4\u7710\u03E3\1\2\232\u03E1\1\1\u7875\u7102\u7E02\u0279\u0E05" + 
	"\uE301\uFF02\2\u4B05\uE501\1\uFF7A\u7373\200\4\u7710\u03E5\232\2\1\1\1\1\u7875\u7102\u7E02\u0279" + 
	"\u0E05\uE501\uFF02\u0101\1\uFF02\u5A05\uE57A\u7373\200\4\u7710\u03E7\1\2\233\u03E5\1\1\u7875\u7102" + 
	"\u7E02\u0279\u1605\uE701\uFF02\u0501\uFF05\uE805\uE905\uEB01\1\1\1\uFF7A\u7373\200\4\u7710\u03E9" + 
	"\233\2\1\1\1\1\u7875\u7102\u7E02\u0279\u0E05\uE901\uFF02\2\u5405\uE801\1\uFF7A\u7373\200\4\u7710" + 
	"\u03EB\1\2\234\u03E9\1\1\u7875\u7102\u7E02\u0279\u0E05\uEB01\uFF02\2\u9305\uE801\1\uFF7A\u7373\200" + 
	"\4\u7710\u03ED\1\2\1\1\124\u03EF\u7875\u7102\u7E02\u0279\u0E05\uEC02\u9A02\u0101\1\1\1\uFF7A\u7373" + 
	"\200\4\u7714\u03EF\1\4\u03F0\u03F1\1\1\1\1\u7875\u7102\u7E02\u0279\u1205\uEF01\uFF02\u0205\uF005" + 
	"\uF201\1\1\1\uFF7A\u7373\200\4\u7710\u03F1\1\2\235\u03F0\1\1\u7875\u7102\u7E02\u0279\u0E05\uF101" + 
	"\uFF02\u0101\1\uFF02\u5305\uEC7A\u7373\200\4\u7710\u03F3\1\2\1\1\131\u03F2\u7875\u7102\u7E02\u0279" + 
	"\u0E05\uF301\uFF02\2\u9905\uF501\1\uFF7A\u7373\200\4\u7710\u03F5\235\2\1\1\1\1\u7875\u7102\u7E02" + 
	"\u0279\u1205\uF501\uFF02\u0205\uF305\uF601\1\1\1\uFF7A\u7373\200\4\u7710\u03F7\1\2\1\1\131\u03F8" + 
	"\u7875\u7102\u7E02\u0279\u0E05\uF701\uFF02\2\u9905\uF501\1\uFF7A\u7A02\6\2\u9C02\2\u0202\u0502\u0802" + 
	"\u0B02\u0E02\u1102\u1402\u1702\u1B02\u1F02\u2302\u2602\u2902\u2C02\u2F02\u3202\u3502\u3902\u3C02" + 
	"\u3F02\u4102\u4302\u4502\u4B02\u5002\u5302\u5C02\u5F02\u6F02\u7E02\u8602\u9802\uA002\uA802\uBB02" + 
	"\uC202\uC702\uCF02\uD902\uE302\uEC02\uF102\uF902\uFD03\u0303\u0F03\u1303\u1603\u1B03\u2003\u2303" + 
	"\u2803\u2C03\u3603\u4303\u4803\u4F03\u5B03\u6103\u6B03\u7A03\u8203\u8403\u8C03\u9303\u9D03\uA103" + 
	"\uA603\uAD03\uB203\uBC03\uC603\uCA03\uCD03\uD203\uD603\uDA03\uDF03\uE803\uFB03\uFF04\u0404\u0604" + 
	"\u1404\u1D04\u2204\u2704\u2C04\u3104\u3604\u3D04\u4304\u4C04\u5704\u5E04\u6604\u6E04\u7304\u7B04" + 
	"\u8104\u8C04\u9204\u9D04\uA104\uA504\uA904\uBF04\uC504\uD004\uD204\uD704\uE104\uE804\uF104\uFA05" + 
	"\5\u0605\u0B05\u1D05\u2405\u2805\u2C05\u3305\u3605\u3805\u3B05\u3D05\u4605\u4D05\u5605\u5C05\u6405" + 
	"\u7505\u7905\u7E05\u8005\u8505\u8A05\u8F05\u9305\u9905\uAB05\uAE05\uB405\uBC05\uC505\uC805\uCD05" + 
	"\uD405\uD705\uDD05\uE205\uE605\uEB05\uF202\u2B02\u4502\u5F02\uB302\uD302\uEF03\u0703\u2303\u3103" + 
	"\u5303\u6B03\u7203\u7A03\u7E03\u8A03\u9503\u9603\u9B03\uCF03\uD603\uDF03\uE804\u1A04\u4E04\u4F04" + 
	"\u7304\u8404\u9D04\uA704\uA904\uB004\uBF04\uC804\uFA05\u0205\u0805\u0B05\u2C05\u6705\u7505\uA605" + 
	"\uBF05\uC805\uF402\2\2\2\2\2\2\2\2\2\2\2\2\2\2\2\2\2\2\2\2\u0102\u4602\u0B03\u7B04\uDA03\u4902\uCA02" + 
	"\u9902\uFE02\uA102\uAF02\uE602\uF203\uA702\u0103\uD302\2\u0102\u4702\u0102\u4902\u0102\u5002\u0102" + 
	"\u4A02\u0602\uD303\u0702\uBD05\uB602\u8203\u5102\u0205\u3505\u2F02\u0102\u5C02\u0405\u2D03\u0402" + 
	"\u7F02\uD002\u0102\u8802\u0202\uAC02\u8802\u0303\u0402\u7F02\uD002\u0102\uB302\u0303\u0402\u7F02" + 
	"\uD002\u0102\uC302\u0102\uCE02\u0102\uD002\u0403\u3A03\u6302\u8C02\u9502\u0102\uE902\u0102\uED02" + 
	"\u0302\uBC04\uE902\u8702\u0202\uB802\uFC02\u0103\u0202\u0203\u0402\uD002\u0105\u3402\u0203\u1403" + 
	"\u1202\u0103\u1802\u0305\uB503\u1D03\u5002\u0402\uDF04\uFB03\u3E03\u2102\u0203\u1C03\u3102\u0204" + 
	"\uFB03\u2902\u0103\u0402\u0203\u6603\u3D02\u0203\u4603\uF802\u0103\u4C02\u0203\u3F03\u6702\u0103" + 
	"\u0402\u0103\u7F02\u0203\u6A05\u2B02\u0103\u0402\u0603\u5204\u3E03\u1103\uC703\u1502\uDB02\u0504" + 
	"\u8F03\uE404\u8903\uAE03\uB302\u0603\u9103\u8A05\uB703\uCB04\uEE04\uF702\u0603\u6E03\u7604\uF404" + 
	"\uE404\uD404\uEB02\u0103\u9602\u0203\uA003\uA402\u0103\uAA02\u0103\uAE02\u0403\u8703\u8F04\u8604" + 
	"\uF702\u0304\uB903\u3B04\uB602\u0602\u9D02\u8E02\uF603\u5E02\uA505\uBA02\u0505\uD505\uD002\u5802" + 
	"\u4F05\uDA02\u1802\uEF03\u3C05\u8305\u2603\u9502\u8B03\u2204\u0505\u8802\uBE04\uAA02\u9403\u6503" + 
	"\u5904\uD102\uDC03\uEB05\uE404\uE202\uC603\u9B03\uCF04\uD602\uAB02\u1405\u3C04\u1905\u2005\u7005" + 
	"\u9203\u2905\u5A05\u5105\u4103\uDB05\u9705\u8D04\uBE05\u6805\u6204\uCF05\u2103\uFC04\uDE05\u0502" + 
	"\u0103\uD702\u0303\uD703\uE004\u1502\u0103\uE002\u0103\uE902\u0103\uF302\u0104\u0102\u0103\uDE02" + 
	"\u0305\uE704\u1503\uDC02\u0104\u1602\u0104\u1F02\u0104\u2402\u0104\u2902\u0104\u2E02\u0104\u3302" + 
	"\u0104\u3802\u0104\u3F02\u0104\u4502\u0104\u4E02\u0104\u5902\u0504\u6004\u8204\u6F04\u6704\u7402" + 
	"\u0104\u6702\u0204\u8204\u6702\u0104\u7402\u0104\u7402\u0203\uE57C\2\u01A0\u028C\3\u02AC\3\u027F" + 
	"\3\u02A0\3\u0177\4\u02A9\u02A5\3\u02A5\4\u02A9\u02C2\4\u02AC\u02C8\4\u02AC\u02C8\7\u0173\u017B\301" + 
	"\u02F1\u02D5\4\u02AC\u02C2\4\u02AC\u02C8\3\u02A0\3\u02F4\3\u02FF\5\u018F\u0301\u0187\11\u032F\u0355" + 
	"\u0367\u0359\u0350\u0361\u0327\3\u030E\3\u030E\13\u030E\u0185\u03A6\u03B1\u039C\u03A0\u0396\u0139" + 
	"\u01FE\3\u0181\6\u0333\u0378\u03C1\u036C\3\u030E\3\u030E\4\u033C\u037D\3\u030E\3\u0345\3\u030E\3" + 
	"\u030E\3\u030E\3\u030E\3\u036F\4\u0378\u0381\3\u036A\3\u030E\3\u030E\3\u030E\3\u030E\3\u030E\3\u030E" + 
	"\4\u03A8\u03A1\3\u03AF\3\u03B4\3\u039F\24\u0286\u0305\117\u02EF\u030B\u01B1\u01BD\u01BA\u01CE\u01E5" + 
	"\u0157\360\u0128\u0244\u013A\u019C\u0293\u02F8\6\141\u03E9\161\u03C7\3\u03CB\3\u03CB\3\u03CB\3\u03D4" + 
	"\3\u03E1\6\u03DE\u03E5\342\u03F6\3\u03E9\3\u03F0\27\31\3\32\6\34\11\40\14\57\17\50\22\36\25\224" + 
	"\30\70\34\60\40\51\44\45\47\73\52\53\55\77\60\174\63\116\66\101\72\114\75\115\100\77\u0349\u7802";
}
