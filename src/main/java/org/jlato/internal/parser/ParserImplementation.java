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
import org.jlato.internal.parser.all.GrammarSerialization;
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
			return GrammarSerialization.VERSION_1.decode(serializedGrammar);
		} catch (IOException e) {
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
	"\uACEF\7\u7A02\6\5\uF702\u0101\uFF02\2\u1702\u0201\1\uFF02\u0102\2\u0101\1\1\1\uFF02\u0301\uFF02" + 
	"\2\u1802\u0501\1\uFF02\u0302\u0102\u0101\1\1\1\uFF02\u0501\uFF02\2\u1402\u0401\1\uFF02\u0601\uFF02" + 
	"\2\u1A02\u0801\1\uFF02\u0602\u0202\u0101\1\1\1\uFF02\u0801\uFF02\2\u1402\u0701\1\uFF02\u0901\uFF02" + 
	"\2\u1E02\u0B01\1\uFF02\u0902\u0302\u0101\1\1\1\uFF02\u0B01\uFF02\2\u1402\u0A01\1\uFF02\u0C01\uFF02" + 
	"\2\u2D02\u0E01\1\uFF02\u0C02\u0402\u0101\1\1\1\uFF02\u0E01\uFF02\2\u1402\u0D01\1\uFF02\u0F01\uFF02" + 
	"\2\u2602\u1101\1\uFF02\u0F02\u0502\u0101\1\1\1\uFF02\u1101\uFF02\2\u1402\u1001\1\uFF02\u1201\uFF02" + 
	"\2\u1C02\u1401\1\uFF02\u1202\u0602\u0101\1\1\1\uFF02\u1401\uFF02\2\u1402\u1301\1\uFF02\u1501\uFF02" + 
	"\2\u9202\u1701\1\uFF02\u1502\u0702\u0101\1\1\1\uFF02\u1701\uFF02\2\u1402\u1601\1\uFF02\u1801\uFF02" + 
	"\2\u1C02\u1A01\1\uFF02\u1802\u0802\u0101\1\1\1\uFF02\u1A01\uFF02\2\u3602\u1B01\1\uFF02\u1B01\uFF02" + 
	"\2\u1402\u1901\1\uFF02\u1C01\uFF02\2\u1C02\u1E01\1\uFF02\u1C02\u0902\u0101\1\1\1\uFF02\u1E01\uFF02" + 
	"\2\u2E02\u1F01\1\uFF02\u1F01\uFF02\2\u1402\u1D01\1\uFF02\u2001\uFF02\2\u1C02\u2201\1\uFF02\u2002" + 
	"\u0A02\u0101\1\1\1\uFF02\u2201\uFF02\2\u2702\u2301\1\uFF02\u2301\uFF02\2\u1402\u2101\1\uFF02\u2401" + 
	"\uFF02\2\u2302\u2601\1\uFF02\u2402\u0B02\u0101\1\1\1\uFF02\u2601\uFF02\2\u1402\u2501\1\uFF02\u2701" + 
	"\uFF02\2\u3902\u2901\1\uFF02\u2702\u0C02\u0101\1\1\1\uFF02\u2901\uFF02\2\u1402\u2801\1\uFF02\u2A01" + 
	"\uFF02\2\u2902\u2C01\1\uFF02\u2A02\u0D02\u0101\1\1\1\uFF02\u2C01\uFF02\2\u1402\u2B01\1\uFF02\u2D01" + 
	"\uFF02\2\u3D02\u2F01\1\uFF02\u2D02\u0E02\u0101\1\1\1\uFF02\u2F01\uFF02\2\u1402\u2E01\1\uFF02\u3001" + 
	"\uFF02\2\u7A02\u3201\1\uFF02\u3002\u0F02\u0101\1\1\1\uFF02\u3201\uFF02\2\u1402\u3101\1\uFF02\u3301" + 
	"\uFF02\2\u4C02\u3501\1\uFF02\u3302\u1002\u0101\1\1\1\uFF02\u3501\uFF02\2\u1402\u3401\1\uFF02\u3601" + 
	"\uFF02\2\u9202\u3801\1\uFF02\u3602\u1102\u0101\1\1\1\uFF02\u3801\uFF02\2\u3F02\u3901\1\uFF02\u3901" + 
	"\uFF02\2\u1402\u3701\1\uFF02\u3A01\uFF02\2\u4A02\u3C01\1\uFF02\u3A02\u1202\u0101\1\1\1\uFF02\u3C01" + 
	"\uFF02\2\u1402\u3B01\1\uFF02\u3D01\uFF02\2\u4B02\u3F01\1\uFF02\u3D02\u1302\u0101\1\1\1\uFF02\u3F01" + 
	"\uFF02\2\u1402\u3E01\1\uFF02\u4001\uFF02\u0101\1\uFF02\2\u4002\u4002\u1402\u0101\1\1\1\uFF02\u4201" + 
	"\uFF02\u0101\1\uFF02\u8302\u4202\u4202\u1502\u0101\1\1\1\uFF02\u4401\uFF02\u0101\1\uFF02\u8202\u4402" + 
	"\u4402\u1602\u0101\1\1\1\uFF02\u4601\uFF02\u0202\u4702\u4901\1\1\1\uFF02\u4602\u1702\u0101\1\1\1" + 
	"\uFF02\u4801\uFF02\2\u1902\u4A01\1\uFF02\u4901\uFF02\2\u1802\u477C\2\u0402\1\1\113\1\2\35\114\1" + 
	"\1\114\1\2\26\110\1\1\115\1\2\224\117\1\1\116\32\2\1\1\1\1\117\1\2\1\1\54\120\120\1\2\114\121\1" + 
	"\1\121\1\2\1\1\130\116\122\1\4\123\124\1\1\1\1\123\33\2\1\1\1\1\124\1\2\34\122\1\1\125\1\2\1\1\44" + 
	"\127\126\34\2\1\1\1\1\127\1\4\130\131\1\1\1\1\130\1\2\114\132\1\1\131\1\2\1\1\62\130\132\1\4\133" + 
	"\134\1\1\1\1\133\1\2\1\1\130\126\134\1\2\1\1\132\135\135\1\2\1\1\154\133\136\1\4\137\140\1\1\1\1" + 
	"\137\35\2\1\1\1\1\140\1\2\40\136\1\1\141\1\4\142\143\1\1\1\1\142\36\2\1\1\1\1\143\1\20\1\144\145" + 
	"\146\147\150\151\152\153\154\155\156\157\160\1\1\1\1\144\1\2\1\1\57\141\145\1\2\1\1\56\141\146\1" + 
	"\2\1\1\55\141\147\1\2\1\1\13\141\150\1\2\1\1\26\141\151\1\2\1\1\62\141\152\1\2\1\1\35\141\153\1" + 
	"\2\1\1\72\141\154\1\2\1\1\76\141\155\1\2\1\1\66\141\156\1\2\1\1\51\141\157\1\2\1\1\63\141\160\1" + 
	"\2\225\141\1\1\161\1\4\162\163\1\1\1\1\162\37\2\1\1\1\1\163\1\17\1\164\165\166\167\170\171\172\173" + 
	"\174\175\176\177\1\1\1\1\164\1\2\1\1\57\161\165\1\2\1\1\56\161\166\1\2\1\1\55\161\167\1\2\1\1\13" + 
	"\161\170\1\2\1\1\62\161\171\1\2\1\1\35\161\172\1\2\1\1\72\161\173\1\2\1\1\76\161\174\1\2\1\1\66" + 
	"\161\175\1\2\1\1\51\161\176\1\2\1\1\63\161\177\1\2\225\161\1\1\200\1\5\1\202\203\1\1\1\1\201\40" + 
	"\2\1\1\1\1\202\1\2\1\1\130\201\203\1\2\36\204\1\1\204\1\6\1\205\206\207\1\1\1\1\205\1\2\41\201\1" + 
	"\1\206\1\2\44\201\1\1\207\1\2\46\201\1\1\210\1\5\1\213\224\1\1\1\1\211\41\2\1\1\1\1\212\1\2\55\211" + 
	"\1\1\213\1\2\1\1\23\214\214\1\2\115\215\1\u7A02\6\u0101\uFF02\u8C01\uFF02\u0202\u8C02\u8E01\1\1" + 
	"\1\uFF02\u8D01\uFF02\u0202\u8E02\u9001\1\1\1\uFF02\u8E01\uFF02\2\u2802\u8D01\1\uFF02\u8F01\uFF02" + 
	"\u0202\u8802\u9201\1\1\1\uFF02\u9001\uFF02\u0101\1\uFF02\u1902\u9002\u9101\uFF02\2\u4902\u8F01\1" + 
	"\uFF02\u9201\uFF02\2\u2102\u8901\1\uFF02\u9301\uFF02\u0101\1\uFF02\u2502\u9302\u9401\uFF02\2\u4B02" + 
	"\u9501\1\uFF02\u9501\uFF02\u0202\u9502\u9701\1\1\1\uFF02\u9601\uFF02\u0202\u8802\u9801\1\1\1\uFF02" + 
	"\u9701\uFF02\2\u2802\u9601\1\uFF02\u9801\uFF02\2\u2002\u8901\1\uFF02\u9901\uFF02\u0101\1\uFF02\u1902" + 
	"\u9A02\u9902\u2002\u0101\1\1\1\uFF02\u9B01\uFF02\u0401\uFF02\u9B02\u9D01\1\1\1\uFF02\u9C01\uFF02" + 
	"\2\u1502\u9A01\1\uFF02\u9D01\uFF02\2\u4902\u9E01\1\uFF02\u9E01\uFF02\u0202\u9902\u9F01\1\1\1\uFF02" + 
	"\u9F01\uFF02\u0101\1\uFF02\u5702\u9F02\uA001\uFF02\2\u4902\u9E01\1\uFF02\uA101\uFF02\u0101\1\uFF02" + 
	"\u2102\uA202\uA102\u2102\u0101\1\1\1\uFF02\uA301\uFF02\u0401\uFF02\uA302\uA501\1\1\1\uFF02\uA401" + 
	"\uFF02\2\u1502\uA201\1\uFF02\uA501\uFF02\2\u4902\uA601\1\uFF02\uA601\uFF02\u0202\uA102\uA701\1\1" + 
	"\1\uFF02\uA701\uFF02\u0101\1\uFF02\u5702\uA702\uA801\uFF02\2\u4902\uA601\1\uFF02\uA901\uFF02\u0101" + 
	"\1\uFF02\u1802\uAA02\uA902\u2202\u0101\1\1\1\uFF02\uAB01\uFF02\2\u4B02\uAC01\1\uFF02\uAC01\uFF02" + 
	"\u0202\uAC02\uAE01\1\1\1\uFF02\uAD01\uFF02\u0101\1\uFF02\u5202\uAE02\uAE01\uFF02\2\u2102\uAD01\1" + 
	"\uFF02\uAF01\uFF02\u0202\uAF02\uB101\1\1\1\uFF02\uB001\uFF02\u0202\uB602\uB801\1\1\1\uFF02\uB101" + 
	"\uFF02\u0401\uFF02\uB102\uB301\1\1\1\uFF02\uB201\uFF02\2\u1502\uB001\1\uFF02\uB301\uFF02\2\u2302" + 
	"\uB401\1\uFF02\uB401\uFF02\u0202\uAF02\uB501\1\1\1\uFF02\uB501\uFF02\u0101\1\uFF02\u5702\uB502\uB601" + 
	"\uFF02\2\u2302\uB401\1\uFF02\uB701\uFF02\u0202\uB802\uBA01\1\1\1\uFF02\uB801\uFF02\u0101\1\uFF02" + 
	"\u5702\uB602\uB901\uFF02\u0101\1\uFF02\u5302\uA902\uBA01\uFF02\u0101\1\uFF02\u5602\uBA02\uBB01\uFF02" + 
	"\2\u2C02\uB901\1\uFF02\uBC01\uFF02\2\u1C02\uBE01\1\uFF02\uBC02\u2302\u0101\1\1\1\uFF02\uBE01\uFF02" + 
	"\2\u4B02\uBF01\1\uFF02\uBF01\uFF02\u0202\uBF02\uC101\1\1\1\uFF02\uC001\uFF02\u0202\uBC02\uC201\1" + 
	"\1\1\uFF02\uC101\uFF02\2\u6F02\uC001\1\uFF02\uC201\uFF02\2\u2B02\uBD01\1\uFF02\uC301\uFF02\u0101" + 
	"\1\uFF02\u5902\uC402\uC302\u2402\u0101\1\1\1\uFF02\uC501\uFF02\u0101\1\uFF02\u2502\uC502\uC601\uFF02" + 
	"\2\u4B02\uC701\1\uFF02\uC701\uFF02\2\u2502\uC401\1\uFF02\uC801\uFF02\u0101\1\uFF02\u5202\uC902\uC802" + 
	"\u2502\u0101\1\1\1\uFF02\uCA01\uFF02\u0202\uCA02\uCC01\1\1\1\uFF02\uCB01\uFF02\u0101\1\uFF02\u5302" + 
	"\uC802\uCC01\uFF02\u0401\uFF02\uCC02\uCE01\1\1\1\uFF02\uCD01\uFF02\2\u1502\uCB01\1\uFF02\uCE01\uFF02" + 
	"\2\u2602\uCF01\1\uFF7C\2\u0402\320\1\4\314\317\1\1\1\1\321\1\5\1\323\324\1\1\1\1\322\50\2\1\1\1" + 
	"\1\323\1\2\1\1\130\322\324\1\2\36\325\1\1\325\1\10\1\326\327\330\331\332\1\1\1\1\326\1\2\51\322" + 
	"\1\1\327\1\2\41\322\1\1\330\1\2\44\322\1\1\331\1\2\46\322\1\1\332\1\2\60\322\1\1\333\1\2\101\335" + 
	"\1\1\334\51\2\1\1\1\1\335\1\2\115\336\1\1\336\1\2\1\1\122\337\337\1\2\1\1\123\340\340\1\2\65\341" + 
	"\1\1\341\1\4\342\343\1\1\1\1\342\1\2\1\1\130\334\343\1\2\1\1\26\344\344\1\2\233\342\1\1\345\1\2" + 
	"\1\1\135\347\346\52\2\1\1\1\1\347\1\5\1\351\352\1\1\1\1\350\1\2\1\1\203\346\351\1\2\27\350\1\1\352" + 
	"\1\2\53\353\1\1\353\1\4\350\354\1\1\1\1\354\1\2\1\1\131\355\355\1\2\53\353\1\1\356\1\2\224\360\1" + 
	"\1\357\53\2\1\1\1\1\360\1\2\115\361\1\1\361\1\4\357\362\1\1\1\1\362\1\2\54\357\1\1\363\1\2\1\1\33" + 
	"\365\364\54\2\1\1\1\1\365\1\5\1\366\367\1\1\1\1\366\1\2\27\364\1\1\367\1\2\113\370\1\1\370\1\4\364" + 
	"\371\1\1\1\1\371\1\2\1\1\156\372\372\1\2\113\370\1\1\373\1\2\1\1\124\375\374\55\2\1\1\1\1\375\1" + 
	"\2\56\376\1\1\376\1\2\1\1\125\374\377\1\4\u0100\u0101\1\1\1\1\u0100\56\2\1\1\1\1\u0101\1\5\1\u0102" + 
	"\u0103\1\1\1\1\u0102\1\2\27\u0100\1\1\u0103\1\2\57\u0104\1\1\u0104\1\4\u0100\u0103\1\1\1\1\u0105" + 
	"\1\5\1\u0107\u0108\1\1\1\1\u0106\57\2\1\1\1\1\u0107\1\2\1\1\130\u0106\u0108\1\2\36\u0109\1\1\u0109" + 
	"\1\12\1\u010A\u010B\u010C\u010D\u010E\u010F\u0110\1\1\1\1\u010A\1\2\100\u0106\1\1\u010B\1\2\41\u0106" + 
	"\1\1\u010C\1\2\44\u0106\1\1\u010D\1\2\46\u0106\1\1\u010E\1\2\75\u0106\1\1\u010F\1\2\60\u0106\1\1" + 
	"\u0110\1\2\70\u0106\1\1\u0111\1\2\101\u0113\1\1\u0112\60\2\1\1\1\1\u7A02\6\3\u1201\uFF02\2\u3003" + 
	"\u1301\1\uFF03\u1301\uFF02\u0101\1\uFF02\u5603\u1003\u1401\uFF02\2\u3F03\u1601\1\uFF03\u1402\u2F02" + 
	"\u0101\1\1\1\uFF03\u1601\uFF02\2\u3003\u1501\1\uFF03\u1701\uFF02\2\u3103\u1901\1\uFF03\u1702\u3002" + 
	"\u0101\1\1\1\uFF03\u1901\uFF02\u0203\u1703\u1A01\1\1\1\uFF03\u1A01\uFF02\u0101\1\uFF02\u5703\u1A03" + 
	"\u1B01\uFF02\2\u3103\u1901\1\uFF03\u1C01\uFF02\2\u3203\u1E01\1\uFF03\u1C02\u3102\u0101\1\1\1\uFF03" + 
	"\u1E01\uFF02\u0203\u1C03\u1F01\1\1\1\uFF03\u1F01\uFF02\u0101\1\uFF02\u5A03\u1F03\u2001\uFF02\2\u3403" + 
	"\u1D01\1\uFF03\u2101\uFF02\2\u4B03\u2301\1\uFF03\u2102\u3202\u0101\1\1\1\uFF03\u2301\uFF02\2\u3303" + 
	"\u2201\1\uFF03\u2401\uFF02\u0203\u2403\u2601\1\1\1\uFF03\u2402\u3302\u0101\1\1\1\uFF03\u2601\uFF02" + 
	"\2\u9203\u2701\1\uFF03\u2701\uFF02\u0101\1\uFF02\u5403\u2703\u2801\uFF02\u0101\1\uFF02\u5503\u2303" + 
	"\u2901\uFF02\u0401\uFF03\u2A03\u2C01\1\1\1\uFF03\u2902\u3402\u0101\1\1\1\uFF03\u2B01\uFF02\2\u3503" + 
	"\u2A01\1\uFF03\u2C01\uFF02\2\u4C03\u2A01\1\uFF03\u2D01\uFF02\u0101\1\uFF02\u5203\u2E03\u2D02\u3502" + 
	"\u0101\1\1\1\uFF03\u2F01\uFF02\u0203\u2F03\u3101\1\1\1\uFF03\u3001\uFF02\u0203\u3403\u3601\1\1\1" + 
	"\uFF03\u3101\uFF02\2\u3403\u3201\1\uFF03\u3201\uFF02\u0203\u2F03\u3301\1\1\1\uFF03\u3301\uFF02\u0101" + 
	"\1\uFF02\u5703\u3303\u3401\uFF02\2\u3403\u3201\1\uFF03\u3501\uFF02\u0101\1\uFF02\u5303\u2D03\u3601" + 
	"\uFF02\u0101\1\uFF02\u5703\u3403\u3701\uFF02\u0203\u3803\u3A01\1\1\1\uFF03\u3702\u3602\u0101\1\1" + 
	"\1\uFF03\u3901\uFF02\2\u4803\u3C01\1\uFF03\u3A01\uFF02\2\u2803\u3B01\1\uFF03\u3B01\uFF02\2\u9203" + 
	"\u3901\1\uFF03\u3C01\uFF02\2\u4B03\u3D01\1\uFF03\u3D01\uFF02\2\u3703\u3E01\1\uFF03\u3E01\uFF02\2" + 
	"\u3303\u3F01\1\uFF03\u3F01\uFF02\u0203\u3F03\u4101\1\1\1\uFF03\u4001\uFF02\u0401\uFF03\u4103\u4301" + 
	"\1\1\1\uFF03\u4101\uFF02\2\u3A03\u4001\1\uFF03\u4201\uFF02\2\u7903\u3801\1\uFF03\u4301\uFF02\u0101" + 
	"\1\uFF02\u5603\u3703\u4401\uFF02\u0101\1\uFF02\u5003\u4503\u4402\u3702\u0101\1\1\1\uFF03\u4601\uFF02" + 
	"\u0203\u4603\u4801\1\1\1\uFF03\u4701\uFF02\u0101\1\uFF02\u5103\u4403\u4801\uFF02\2\u3803\u4701\1" + 
	"\uFF03\u4901\uFF02\u0401\uFF03\u4A03\u4C01\1\1\1\uFF03\u4902\u3802\u0101\1\1\1\uFF03\u4B01\uFF02" + 
	"\2\u1503\u4A01\1\uFF03\u4C01\uFF02\2\u3903\u4D01\1\uFF03\u4D01\uFF02\u0203\u4903\u4E01\1\1\1\uFF03" + 
	"\u4E01\uFF02\u0101\1\uFF02\u5703\u4E03\u4F01\uFF02\2\u3903\u4D01\1\uFF03\u5001\uFF02\2\u1C03\u5201" + 
	"\1\uFF03\u5002\u3902\u0101\1\1\1\uFF03\u5201\uFF02\2\u3F03\u5301\1\uFF03\u5301\uFF02\u0203\u5303" + 
	"\u5501\1\1\1\uFF03\u5401\uFF02\u0401\uFF03\u5603\u5B01\1\1\1\uFF03\u5501\uFF02\2\u9203\u5601\1\uFF03" + 
	"\u5601\uFF7C\2\u0402\2\1\1\176\u0155\u0158\1\4\u0159\u015A\1\1\1\1\u0159\1\2\1\1\67\u0152\u015A" + 
	"\1\2\115\u015B\1\1\u015B\1\2\1\1\132\u0159\u015C\1\2\64\u0152\1\1\u015D\1\2\1\1\71\u015F\u015E\74" + 
	"\2\1\1\1\1\u015F\1\2\113\u0160\1\1\u0160\1\4\u015E\u0161\1\1\1\1\u0161\1\2\1\1\131\u0162\u0162\1" + 
	"\2\113\u0160\1\1\u0163\1\4\u0165\u0166\1\1\1\1\u0164\75\2\1\1\1\1\u0165\1\2\115\u0167\1\1\u0166" + 
	"\1\2\52\u0165\1\1\u0167\1\2\71\u0168\1\1\u0168\1\4\u0169\u016A\1\1\1\1\u0169\1\2\1\1\124\u016B\u016A" + 
	"\1\2\74\u0169\1\1\u016B\1\2\77\u016C\1\1\u016C\1\2\1\1\125\u0164\u016D\1\5\1\u016F\u0174\1\1\1\1" + 
	"\u016E\76\2\1\1\1\1\u016F\1\4\u0170\u0171\1\1\1\1\u0170\1\2\1\1\67\u0172\u0171\1\2\104\u0170\1\1" + 
	"\u0172\1\2\161\u0173\1\1\u0173\1\2\1\1\130\u016E\u0174\1\4\u0175\u0176\1\1\1\1\u0175\1\4\u0178\u0179" + 
	"\1\1\1\1\u0176\1\2\153\u0177\1\1\u0177\1\2\1\1\132\u0175\u0178\1\2\1\1\64\u017A\u0179\1\2\104\u0178" + 
	"\1\1\u017A\1\2\161\u017B\1\1\u017B\1\2\1\1\130\u016E\u017C\1\4\u017D\u017E\1\1\1\1\u017D\77\2\1" + 
	"\1\1\1\u017E\1\5\1\u017F\u0180\1\1\1\1\u017F\1\2\27\u017D\1\1\u0180\1\4\u0181\u0182\1\1\1\1\u0181" + 
	"\1\4\u017D\u0183\1\1\1\1\u0182\1\2\76\u0181\1\1\u0183\1\2\174\u0181\1\1\u0184\1\2\173\u0185\1\1" + 
	"\u0185\100\2\1\1\1\1\u0186\1\5\1\u0188\u018B\1\1\1\1\u0187\101\2\1\1\1\1\u0188\1\2\111\u0189\1\1" + 
	"\u0189\1\4\u0187\u018A\1\1\1\1\u018A\1\2\167\u0187\1\1\u018B\1\2\103\u018C\1\1\u018C\1\4\u0187\u018D" + 
	"\1\1\1\1\u018D\1\2\167\u0187\1\1\u018E\1\5\1\u0190\u0192\1\1\1\1\u018F\102\2\1\1\1\1\u0190\1\2\111" + 
	"\u0191\1\1\u0191\1\2\167\u018F\1\1\u0192\1\2\103\u0193\1\1\u0193\1\4\u018F\u0194\1\1\1\1\u0194\1" + 
	"\2\167\u018F\1\1\u0195\1\2\115\u0197\1\1\u0196\103\2\1\1\1\1\u0197\1\4\u0198\u0199\1\1\1\1\u0198" + 
	"\1\4\u0196\u019A\1\1\1\1\u0199\1\2\105\u0198\1\1\u019A\1\2\u7A02\6\u0101\1\uFF02\u5803\u9903\u9A01" + 
	"\uFF02\2\u9203\u9B01\1\uFF03\u9B01\uFF02\2\u4B03\u9C01\1\uFF03\u9C01\uFF02\u0203\u9603\u9D01\1\1" + 
	"\1\uFF03\u9D01\uFF02\2\u4303\u9701\1\uFF03\u9E01\uFF02\u0101\1\uFF02\u5B03\u9F03\u9E02\u4202\u0101" + 
	"\1\1\1\uFF03\uA001\uFF02\2\u4403\uA101\1\uFF03\uA101\uFF02\u0101\1\uFF02\u8103\u9E03\uA201\uFF02" + 
	"\u0101\1\uFF02\u5B03\uA303\uA202\u4302\u0101\1\1\1\uFF03\uA401\uFF02\u0203\uA403\uA601\1\1\1\uFF03" + 
	"\uA501\uFF02\u0101\1\uFF02\u8103\uA203\uA601\uFF02\2\u4403\uA501\1\uFF03\uA701\uFF02\u0401\uFF03" + 
	"\uA803\uAA01\1\1\1\uFF03\uA702\u4402\u0101\1\1\1\uFF03\uA901\uFF02\2\u1503\uA801\1\uFF03\uAA01\uFF02" + 
	"\2\u4503\uAB01\1\uFF03\uAB01\uFF02\u0203\uA703\uAC01\1\1\1\uFF03\uAC01\uFF02\u0101\1\uFF02\u5703" + 
	"\uAC03\uAD01\uFF02\2\u4503\uAB01\1\uFF03\uAE01\uFF02\2\u9203\uB001\1\uFF03\uAE02\u4502\u0101\1\1" + 
	"\1\uFF03\uB001\uFF02\u0401\uFF03\uB003\uB201\1\1\1\uFF03\uB101\uFF02\2\u4003\uAF01\1\uFF03\uB201" + 
	"\uFF02\2\u4603\uAF01\1\uFF03\uB301\uFF02\u0101\1\uFF02\u5E03\uB403\uB302\u4602\u0101\1\1\1\uFF03" + 
	"\uB501\uFF02\u0203\uB303\uB601\1\1\1\uFF03\uB601\uFF02\u0401\uFF03\uB603\uBA01\1\1\1\uFF03\uB701" + 
	"\uFF02\u0101\1\uFF02\u1903\uB703\uB801\uFF02\2\u9203\uB901\1\uFF03\uB901\uFF02\2\u4003\uB401\1\uFF03" + 
	"\uBA01\uFF02\u0101\1\uFF02\u3203\uBA03\uBB01\uFF02\2\u9203\uBC01\1\uFF03\uBC01\uFF02\2\u4003\uB401" + 
	"\1\uFF03\uBD01\uFF02\u0A01\uFF03\uBE03\uBF03\uC003\uC103\uC203\uC303\uC403\uC601\1\1\1\uFF03\uBD02" + 
	"\u4702\u0101\1\1\1\uFF03\uBF01\uFF02\u0101\1\uFF02\u0B03\uBD03\uC001\uFF02\u0101\1\uFF02\u1003\uBD03" + 
	"\uC101\uFF02\u0101\1\uFF02\u0D03\uBD03\uC201\uFF02\u0101\1\uFF02\u2F03\uBD03\uC301\uFF02\u0101\1" + 
	"\uFF02\u2403\uBD03\uC401\uFF02\u0101\1\uFF02\u2603\uBD03\uC501\uFF02\u0101\1\uFF02\u1D03\uBD03\uC601" + 
	"\uFF02\u0101\1\uFF02\u1603\uBD03\uC701\uFF02\u0401\uFF03\uC803\uCA01\1\1\1\uFF03\uC702\u4802\u0101" + 
	"\1\1\1\uFF03\uC901\uFF02\u0101\1\uFF02\u3B03\uC703\uCA01\uFF02\2\u3F03\uC801\1\uFF03\uCB01\uFF02" + 
	"\2\u9203\uCD01\1\uFF03\uCB02\u4902\u0101\1\1\1\uFF03\uCD01\uFF02\2\u4103\uCC01\1\uFF03\uCE01\uFF02" + 
	"\2\u4B03\uD001\1\uFF03\uCE02\u4A02\u0101\1\1\1\uFF03\uD001\uFF02\u0203\uCE03\uD101\1\1\1\uFF03\uD101" + 
	"\uFF02\u0101\1\uFF02\u5803\uD103\uD201\uFF02\2\u4B03\uD001\1\uFF03\uD301\uFF02\u0401\uFF03\uD403" + 
	"\uD601\1\1\1\uFF03\uD302\u4B02\u0101\1\1\1\uFF03\uD501\uFF02\2\u1603\uD401\1\uFF03\uD601\uFF02\u0101" + 
	"\1\uFF02\u8403\uD303\uD701\uFF02\u0401\uFF03\uD803\uDA01\1\1\1\uFF03\uD702\u4C02\u0101\1\1\1\uFF03" + 
	"\uD901\uFF02\2\u4D03\uD801\1\uFF03\uDA01\uFF02\2\u4E03\uD801\1\uFF03\uDB01\uFF02\2\u5403\uDD01\1" + 
	"\uFF03\uDB02\u4D02\u0101\1\1\1\uFF03\uDD01\uFF7C\2\u0402\4\u01DD\u01DF\1\1\1\1\u01DF\1\2\125\u01E0" + 
	"\1\1\u01E0\1\2\116\u01DD\1\1\u01E1\1\5\1\u01E3\u01E9\1\1\1\1\u01E2\120\2\1\1\1\1\u01E3\1\2\1\1\122" + 
	"\u01E4\u01E4\1\2\224\u01E5\1\1\u01E5\1\2\102\u01E6\1\1\u01E6\1\2\147\u01E7\1\1\u01E7\1\2\1\1\123" + 
	"\u01E8\u01E8\1\2\120\u01E2\1\1\u01E9\1\2\121\u01E2\1\1\u01EA\1\7\1\u01EC\u01EF\u01F3\u01F8\1\1\1" + 
	"\1\u01EB\121\2\1\1\1\1\u01EC\1\2\115\u01ED\1\1\u01ED\1\2\1\1\177\u01EE\u01EE\1\2\122\u01EB\1\1\u01EF" + 
	"\1\2\1\1\122\u01F0\u01F0\1\2\1\1\123\u01F1\u01F1\1\2\1\1\177\u01F2\u01F2\1\2\122\u01EB\1\1\u01F3" + 
	"\1\2\1\1\122\u01F4\u01F4\1\2\123\u01F5\1\1\u01F5\1\2\1\1\123\u01F6\u01F6\1\2\1\1\177\u01F7\u01F7" + 
	"\1\2\122\u01EB\1\1\u01F8\1\2\1\1\122\u01F9\u01F9\1\2\72\u01FA\1\1\u01FA\1\2\1\1\123\u01FB\u01FB" + 
	"\1\2\1\1\177\u01FC\u01FC\1\2\122\u01EB\1\1\u01FD\1\5\1\u01FF\u0200\1\1\1\1\u01FE\122\2\1\1\1\1\u01FF" + 
	"\1\2\116\u01FE\1\1\u0200\1\2\173\u01FE\1\1\u0201\1\2\124\u0203\1\1\u0202\123\2\1\1\1\1\u0203\1\4" + 
	"\u0202\u0204\1\1\1\1\u0204\1\2\1\1\131\u0205\u0205\1\2\124\u0203\1\1\u0206\1\2\115\u0207\1\1\u0207" + 
	"\124\2\1\1\1\1\u0208\1\17\1\u020A\u020B\u020C\u020D\u020E\u020F\u0210\u0211\u0212\u0213\u0214\u0215" + 
	"\1\1\1\1\u0209\125\2\1\1\1\1\u020A\1\2\1\1\134\u0209\u020B\1\2\1\1\165\u0209\u020C\1\2\1\1\166\u0209" + 
	"\u020D\1\2\1\1\172\u0209\u020E\1\2\1\1\163\u0209\u020F\1\2\1\1\164\u0209\u0210\1\2\1\1\173\u0209" + 
	"\u0211\1\2\1\1\174\u0209\u0212\1\2\1\1\175\u0209\u0213\1\2\1\1\167\u0209\u0214\1\2\1\1\171\u0209" + 
	"\u0215\1\2\1\1\170\u0209\u0216\1\2\127\u0218\1\1\u0217\126\2\1\1\1\1\u0218\1\4\u0217\u0219\1\1\1" + 
	"\1\u0219\1\2\1\1\140\u021A\u021A\1\2\116\u021B\1\1\u021B\1\2\1\1\141\u021C\u021C\1\5\1\u021D\u021E" + 
	"\1\1\1\1\u021D\1\2\126\u0217\1\1\u021E\1\2\120\u0217\1\1\u021F\1\2\130\u0221\1\1\u0220\127\2\1\1" + 
	"\1\1\u0221\1\4\u0220\u0222\1\1\1\1\u0222\1\2\u7A02\6\u0101\1\uFF02\u6404\u2104\u2201\uFF02\2\u5604" + 
	"\u2001\1\uFF04\u2301\uFF02\2\u5704\u2501\1\uFF04\u2302\u5602\u0101\1\1\1\uFF04\u2501\uFF02\u0204" + 
	"\u2304\u2601\1\1\1\uFF04\u2601\uFF02\u0101\1\uFF02\u6504\u2604\u2701\uFF02\2\u5704\u2501\1\uFF04" + 
	"\u2801\uFF02\2\u5804\u2A01\1\uFF04\u2802\u5702\u0101\1\1\1\uFF04\u2A01\uFF02\u0204\u2804\u2B01\1" + 
	"\1\1\uFF04\u2B01\uFF02\u0101\1\uFF02\u6D04\u2B04\u2C01\uFF02\2\u5804\u2A01\1\uFF04\u2D01\uFF02\2" + 
	"\u5904\u2F01\1\uFF04\u2D02\u5802\u0101\1\1\1\uFF04\u2F01\uFF02\u0204\u2D04\u3001\1\1\1\uFF04\u3001" + 
	"\uFF02\u0101\1\uFF02\u6E04\u3004\u3101\uFF02\2\u5904\u2F01\1\uFF04\u3201\uFF02\2\u5A04\u3401\1\uFF04" + 
	"\u3202\u5902\u0101\1\1\1\uFF04\u3401\uFF02\u0204\u3204\u3501\1\1\1\uFF04\u3501\uFF02\u0101\1\uFF02" + 
	"\u6C04\u3504\u3601\uFF02\2\u5A04\u3401\1\uFF04\u3701\uFF02\2\u5B04\u3901\1\uFF04\u3702\u5A02\u0101" + 
	"\1\1\1\uFF04\u3901\uFF02\u0204\u3704\u3A01\1\1\1\uFF04\u3A01\uFF02\u0401\uFF04\u3B04\u3D01\1\1\1" + 
	"\uFF04\u3B01\uFF02\2\u5B04\u3901\1\uFF04\u3C01\uFF02\u0101\1\uFF02\u6004\u3A04\u3D01\uFF02\u0101" + 
	"\1\uFF02\u6304\u3A04\u3E01\uFF02\2\u5C04\u4001\1\uFF04\u3E02\u5B02\u0101\1\1\1\uFF04\u4001\uFF02" + 
	"\u0204\u3E04\u4101\1\1\1\uFF04\u4101\uFF02\u0101\1\uFF02\u2304\u4104\u4201\uFF02\2\u9204\u4301\1" + 
	"\uFF04\u4301\uFF02\2\u3F04\u3F01\1\uFF04\u4401\uFF02\2\u5D04\u4601\1\uFF04\u4402\u5C02\u0101\1\1" + 
	"\1\uFF04\u4601\uFF02\u0204\u4404\u4701\1\1\1\uFF04\u4701\uFF02\u0601\uFF04\u4804\u4904\u4A04\u4C01" + 
	"\1\1\1\uFF04\u4801\uFF02\2\u5D04\u4601\1\uFF04\u4901\uFF02\u0101\1\uFF02\u5B04\u4704\u4A01\uFF02" + 
	"\u0101\1\uFF02\u8104\u4704\u4B01\uFF02\u0101\1\uFF02\u6104\u4704\u4C01\uFF02\u0101\1\uFF02\u6204" + 
	"\u4704\u4D01\uFF02\2\u5E04\u4F01\1\uFF04\u4D02\u5D02\u0101\1\1\1\uFF04\u4F01\uFF02\u0204\u4D04\u5001" + 
	"\1\1\1\uFF04\u5001\uFF02\u0501\uFF04\u5104\u5204\u5601\1\1\1\uFF04\u5101\uFF02\2\u5E04\u4F01\1\uFF04" + 
	"\u5201\uFF02\u0101\1\uFF02\u7004\u5004\u5301\uFF02\u0101\1\uFF02\u8104\u5304\u5401\uFF02\u0101\1" + 
	"\uFF02\u8104\u5404\u5501\uFF02\u0101\1\uFF02\u8104\u5004\u5601\uFF02\u0101\1\uFF02\u8104\u5604\u5701" + 
	"\uFF02\u0101\1\uFF02\u8104\u5004\u5801\uFF02\2\u5F04\u5A01\1\uFF04\u5802\u5E02\u0101\1\1\1\uFF04" + 
	"\u5A01\uFF02\u0204\u5804\u5B01\1\1\1\uFF04\u5B01\uFF02\u0401\uFF04\u5C04\u5E01\1\1\1\uFF04\u5C01" + 
	"\uFF02\2\u5F04\u5A01\1\uFF04\u5D01\uFF02\u0101\1\uFF02\u6804\u5B04\u5E01\uFF02\u0101\1\uFF02\u6904" + 
	"\u5B04\u5F01\uFF02\2\u6004\u6101\1\uFF04\u5F02\u5F02\u0101\1\1\1\uFF04\u6101\uFF02\u0204\u5F04\u6201" + 
	"\1\1\1\uFF04\u6201\uFF02\u0501\uFF04\u6304\u6404\u6601\1\1\1\uFF04\u6301\uFF02\2\u6004\u6101\1\uFF04" + 
	"\u6401\uFF02\u0101\1\uFF02\u6A04\u627C\2\u0402\u0266\1\2\1\1\155\u0264\u0267\1\2\1\1\161\u0264\u0268" + 
	"\1\6\1\u026A\u026B\u026F\1\1\1\1\u0269\142\2\1\1\1\1\u026A\1\2\143\u0269\1\1\u026B\1\5\1\u026D\u026E" + 
	"\1\1\1\1\u026C\1\2\142\u0269\1\1\u026D\1\2\1\1\152\u026C\u026E\1\2\1\1\153\u026C\u026F\1\2\144\u0269" + 
	"\1\1\u0270\1\5\1\u0273\u0274\1\1\1\1\u0271\143\2\1\1\1\1\u0272\1\2\142\u0271\1\1\u0273\1\2\1\1\150" + 
	"\u0272\u0274\1\2\1\1\151\u0272\u0275\1\6\1\u0277\u027B\u027C\1\1\1\1\u0276\144\2\1\1\1\1\u0277\1" + 
	"\5\1\u0279\u027A\1\1\1\1\u0278\1\2\142\u0276\1\1\u0279\1\2\1\1\137\u0278\u027A\1\2\1\1\136\u0278" + 
	"\u027B\1\2\146\u0276\1\1\u027C\1\2\145\u0276\1\1\u027D\1\2\151\u027F\1\1\u027E\145\2\1\1\1\1\u027F" + 
	"\1\4\u027E\u0280\1\1\1\1\u0280\1\5\1\u0281\u0282\1\1\1\1\u0281\1\2\1\1\150\u027E\u0282\1\2\1\1\151" + 
	"\u027E\u0283\1\2\1\1\122\u0285\u0284\146\2\1\1\1\1\u0285\1\2\224\u0286\1\1\u0286\1\5\1\u0287\u028A" + 
	"\1\1\1\1\u0287\1\2\111\u0288\1\1\u0288\1\2\1\1\123\u0289\u0289\1\2\142\u0284\1\1\u028A\1\2\102\u028B" + 
	"\1\1\u028B\1\2\147\u028C\1\1\u028C\1\2\1\1\123\u028D\u028D\1\2\144\u0284\1\1\u028E\1\4\u028F\u0290" + 
	"\1\1\1\1\u028F\147\2\1\1\1\1\u0290\1\2\1\1\156\u0292\u0291\1\4\u028F\u0290\1\1\1\1\u0292\1\2\224" + 
	"\u0293\1\1\u0293\1\2\102\u0291\1\1\u0294\1\14\1\u0296\u0297\u0298\u0299\u029A\u029B\u029C\u029D" + 
	"\u029E\1\1\1\1\u0295\150\2\1\1\1\1\u0296\1\2\1\1\101\u0295\u0297\1\2\1\1\100\u0295\u0298\1\2\1\1" + 
	"\106\u0295\u0299\1\2\1\1\107\u0295\u029A\1\2\1\1\120\u0295\u029B\1\2\1\1\121\u0295\u029C\1\2\1\1" + 
	"\73\u0295\u029D\1\2\1\1\34\u0295\u029E\1\2\1\1\53\u0295\u029F\1\5\1\u02A1\u02A2\1\1\1\1\u02A0\151" + 
	"\2\1\1\1\1\u02A1\1\2\152\u02A0\1\1\u02A2\1\2\164\u02A0\1\1\u02A3\1\2\154\u02A5\1\1\u02A4\152\2\1" + 
	"\1\1\1\u02A5\1\4\u02A4\u02A6\1\1\1\1\u02A6\1\2\155\u02A5\1\1\u02A7\1\2\154\u02A9\1\1\u02A8\153\2" + 
	"\1\1\1\u7A02\6\u0101\uFF04\uA801\uFF02\u0204\uA604\uA901\1\1\1\uFF04\uA901\uFF02\2\u6C04\uA801\1" + 
	"\uFF04\uAA01\uFF02\u0B01\uFF04\uAB04\uAC04\uAD04\uB404\uB504\uB804\uBA04\uBB04\uBD01\1\1\1\uFF04" + 
	"\uAA02\u6A02\u0101\1\1\1\uFF04\uAC01\uFF02\2\u6604\uAB01\1\uFF04\uAD01\uFF02\u0101\1\uFF02\u3504" + 
	"\uAA04\uAE01\uFF02\u0101\1\uFF02\u3204\uAE04\uAF01\uFF02\u0401\uFF04\uAF04\uB401\1\1\1\uFF04\uB001" + 
	"\uFF02\u0101\1\uFF02\u5804\uB004\uB101\uFF02\u0401\uFF04\uB104\uB301\1\1\1\uFF04\uB201\uFF02\2\u6E04" + 
	"\uAB01\1\uFF04\uB301\uFF02\2\u6D04\uAB01\1\uFF04\uB401\uFF02\2\u7004\uAB01\1\uFF04\uB501\uFF02\2" + 
	"\u7104\uAB01\1\uFF04\uB601\uFF02\2\u4804\uB701\1\uFF04\uB701\uFF02\u0101\1\uFF02\u5804\uB704\uB801" + 
	"\uFF02\u0101\1\uFF02\u1104\uAA04\uB901\uFF02\2\u4804\uBA01\1\uFF04\uBA01\uFF02\2\u7004\uAB01\1\uFF04" + 
	"\uBB01\uFF02\2\u6E04\uAB01\1\uFF04\uBC01\uFF02\2\u4B04\uAB01\1\uFF04\uBD01\uFF02\u0101\1\uFF02\u5004" + 
	"\uBD04\uBE01\uFF02\2\u4C04\uBF01\1\uFF04\uBF01\uFF02\u0101\1\uFF02\u5104\uAA04\uC001\uFF02\u0501" + 
	"\uFF04\uC104\uC204\uC501\1\1\1\uFF04\uC002\u6B02\u0101\1\1\1\uFF04\uC201\uFF02\2\u6C04\uC101\1\uFF04" + 
	"\uC301\uFF02\u0101\1\uFF02\u5804\uC304\uC401\uFF02\u0101\1\uFF02\u3204\uC004\uC501\uFF02\2\u7004" + 
	"\uC101\1\uFF04\uC601\uFF02\u0401\uFF04\uC704\uCE01\1\1\1\uFF04\uC602\u6C02\u0101\1\1\1\uFF04\uC801" + 
	"\uFF02\u0101\1\uFF02\u5804\uC804\uC901\uFF02\u0601\uFF04\uC904\uCA04\uCB04\uCD01\1\1\1\uFF04\uCA01" + 
	"\uFF02\u0101\1\uFF02\u3504\uC604\uCB01\uFF02\2\u7104\uC701\1\uFF04\uCC01\uFF02\2\u6E04\uC701\1\uFF04" + 
	"\uCD01\uFF02\2\u6D04\uC701\1\uFF04\uCE01\uFF02\u0101\1\uFF02\u5404\uCE04\uCF01\uFF02\2\u4C04\uD001" + 
	"\1\uFF04\uD001\uFF02\u0101\1\uFF02\u5504\uC604\uD101\uFF02\2\u4B04\uD201\1\uFF04\uD102\u6D02\u0101" + 
	"\1\1\1\uFF04\uD301\uFF02\u0204\uD404\uD601\1\1\1\uFF04\uD302\u6E02\u0101\1\1\1\uFF04\uD501\uFF02" + 
	"\2\u4B04\uD701\1\uFF04\uD601\uFF02\2\u4204\uD501\1\uFF04\uD701\uFF02\2\u6F04\uD401\1\uFF04\uD801" + 
	"\uFF02\u0101\1\uFF02\u5004\uD904\uD802\u6F02\u0101\1\1\1\uFF04\uDA01\uFF02\u0204\uDA04\uDC01\1\1" + 
	"\1\uFF04\uDB01\uFF02\u0101\1\uFF02\u5104\uD804\uDC01\uFF02\u0401\uFF04\uDC04\uDE01\1\1\1\uFF04\uDD01" + 
	"\uFF02\2\u1504\uDB01\1\uFF04\uDE01\uFF02\2\u4C04\uDF01\1\uFF04\uDF01\uFF02\u0204\uDA04\uE001\1\1" + 
	"\1\uFF04\uE001\uFF02\u0101\1\uFF02\u5704\uE004\uE101\uFF02\2\u4C04\uDF01\1\uFF04\uE201\uFF02\u0101" + 
	"\1\uFF02\u7E04\uE304\uE202\u7002\u0101\1\1\1\uFF04\uE401\uFF02\u0204\uE404\uE601\1\1\1\uFF04\uE501" + 
	"\uFF02\u0401\uFF04\uE604\uE801\1\1\1\uFF04\uE601\uFF02\2\u4204\uE501\1\uFF04\uE701\uFF02\2\u4B04" + 
	"\uE301\1\uFF04\uE801\uFF02\u0101\1\uFF02\u2804\uE204\uE901\uFF02\u0101\1\uFF02\u2804\uEA04\uE902" + 
	"\u7102\u0101\1\uFF7C\2\u0402\1\1\u02EC\1\4\u02ED\u02EE\1\1\1\1\u02ED\1\2\224\u02EF\1\1\u02EE\1\2" + 
	"\104\u02ED\1\1\u02EF\1\2\103\u02F0\1\1\u02F0\1\2\161\u02F1\1\1\u02F1\1\4\u02EB\u02F2\1\1\1\1\u02F2" + 
	"\1\2\55\u02EB\1\1\u02F3\1\2\1\1\52\u02F5\u02F4\164\2\1\1\1\1\u02F5\1\4\u02F6\u02F7\1\1\1\1\u02F6" + 
	"\1\2\224\u02F8\1\1\u02F7\1\2\104\u02F6\1\1\u02F8\1\5\1\u02FA\u02FB\1\1\1\1\u02F9\1\2\165\u02F4\1" + 
	"\1\u02FA\1\2\111\u02F9\1\1\u02FB\1\2\103\u02F9\1\1\u02FC\1\5\1\u02FE\u0300\1\1\1\1\u02FD\165\2\1" + 
	"\1\1\1\u02FE\1\2\166\u02FF\1\1\u02FF\1\2\65\u02FD\1\1\u0300\1\2\167\u0301\1\1\u0301\1\2\67\u02FD" + 
	"\1\1\u0302\1\2\224\u0305\1\1\u0303\166\2\1\1\1\1\u0304\1\4\u0303\u0302\1\1\1\1\u0305\1\2\1\1\126" + 
	"\u0306\u0306\1\2\116\u0307\1\1\u0307\1\2\1\1\127\u0304\u0308\1\2\224\u030B\1\1\u0309\167\2\1\1\1" + 
	"\1\u030A\1\4\u0309\u0308\1\1\1\1\u030B\1\2\1\1\126\u030C\u030C\1\2\1\1\127\u030A\u030D\1\23\1\u030F" + 
	"\u0310\u0311\u0312\u0313\u0314\u0315\u0316\u0317\u0318\u0319\u031A\u031B\u031C\u031D\u031E\1\1\1" + 
	"\1\u030E\170\2\1\1\1\1\u030F\1\2\172\u030E\1\1\u0310\1\2\171\u030E\1\1\u0311\1\2\173\u030E\1\1\u0312" + 
	"\1\2\176\u030E\1\1\u0313\1\2\177\u030E\1\1\u0314\1\2\201\u030E\1\1\u0315\1\2\203\u030E\1\1\u0316" + 
	"\1\2\204\u030E\1\1\u0317\1\2\205\u030E\1\1\u0318\1\2\206\u030E\1\1\u0319\1\2\212\u030E\1\1\u031A" + 
	"\1\2\213\u030E\1\1\u031B\1\2\214\u030E\1\1\u031C\1\2\215\u030E\1\1\u031D\1\2\216\u030E\1\1\u031E" + 
	"\1\2\217\u030E\1\1\u031F\1\2\1\1\14\u0321\u0320\171\2\1\1\1\1\u0321\1\2\116\u0322\1\1\u0322\1\4" + 
	"\u0323\u0324\1\1\1\1\u0323\1\2\1\1\130\u0320\u0324\1\2\1\1\141\u0325\u0325\1\2\116\u0323\1\1\u0326" + 
	"\1\2\115\u0328\1\1\u0327\172\2\1\1\1\1\u0328\1\2\1\1\141\u0329\u0329\1\2\170\u0327\1\1\u032A\1\2" + 
	"\1\1\124\u032C\u032B\173\2\1\1\1\1\u032C\1\2\77\u032D\1\1\u032D\1\2\1\1\125\u032B\u032E\1\6\1\u0330" + 
	"\u0332\u0334\1\1\1\1\u032F\174\u7A02\6\2\u0101\1\1\1\uFF05\u2F01\uFF02\2\u1D05\u3001\1\uFF05\u3001" + 
	"\uFF02\2\u1F05\u2E01\1\uFF05\u3101\uFF02\2\u7B05\u3201\1\uFF05\u3201\uFF02\u0101\1\uFF02\u5605\u2D05" + 
	"\u3301\uFF02\2\u7605\u2E01\1\uFF05\u3401\uFF02\2\u1D05\u3601\1\uFF05\u3402\u7B02\u0101\1\1\1\uFF05" + 
	"\u3601\uFF02\2\u2F05\u3501\1\uFF05\u3701\uFF02\u0101\1\uFF02\u5605\u3705\u3702\u7C02\u0101\1\1\1" + 
	"\uFF05\u3901\uFF02\2\u7E05\u3B01\1\uFF05\u3902\u7D02\u0101\1\1\1\uFF05\u3B01\uFF02\u0101\1\uFF02" + 
	"\u5605\u3905\u3C01\uFF02\2\u4C05\u3D01\1\uFF05\u3C02\u7E02\u0101\1\1\1\uFF05\u3E01\uFF02\u0101\1" + 
	"\uFF02\u3305\u3F05\u3E02\u7F02\u0101\1\1\1\uFF05\u4001\uFF02\u0101\1\uFF02\u5005\u4005\u4101\uFF02" + 
	"\2\u4C05\u4201\1\uFF05\u4201\uFF02\u0101\1\uFF02\u5105\u4205\u4301\uFF02\u0101\1\uFF02\u5205\u4305" + 
	"\u4401\uFF02\u0205\u4405\u4601\1\1\1\uFF05\u4501\uFF02\u0101\1\uFF02\u5305\u3E05\u4601\uFF02\2\u8005" + 
	"\u4401\1\uFF05\u4701\uFF02\u0401\uFF05\u4905\u4C01\1\1\1\uFF05\u4702\u8002\u0101\1\1\1\uFF05\u4901" + 
	"\uFF02\u0101\1\uFF02\u5F05\u4C05\u4A01\uFF02\u0101\1\uFF02\u0E05\u4A05\u4B01\uFF02\2\u4C05\u4901" + 
	"\1\uFF05\u4C01\uFF02\u0101\1\uFF02\u1405\u4805\u4D01\uFF02\2\u3D05\u4801\1\uFF05\u4E01\uFF02\u0101" + 
	"\1\uFF02\u2005\u4F05\u4E02\u8102\u0101\1\1\1\uFF05\u5001\uFF02\u0101\1\uFF02\u5005\u5005\u5101\uFF02" + 
	"\2\u4C05\u5201\1\uFF05\u5201\uFF02\u0101\1\uFF02\u5105\u5205\u5301\uFF02\2\u7605\u5401\1\uFF05\u5401" + 
	"\uFF02\u0205\u4E05\u5501\1\1\1\uFF05\u5501\uFF02\u0101\1\uFF02\u1705\u5505\u5601\uFF02\2\u7605\u4F01" + 
	"\1\uFF05\u5701\uFF02\u0101\1\uFF02\u3D05\u5805\u5702\u8202\u0101\1\1\1\uFF05\u5901\uFF02\u0101\1" + 
	"\uFF02\u5005\u5905\u5A01\uFF02\2\u4C05\u5B01\1\uFF05\u5B01\uFF02\u0101\1\uFF02\u5105\u5B05\u5C01" + 
	"\uFF02\2\u7605\u5801\1\uFF05\u5D01\uFF02\u0101\1\uFF02\u1505\u5E05\u5D02\u8302\u0101\1\1\1\uFF05" + 
	"\u5F01\uFF02\2\u7605\u6001\1\uFF05\u6001\uFF02\u0101\1\uFF02\u3D05\u6005\u6101\uFF02\u0101\1\uFF02" + 
	"\u5005\u6105\u6201\uFF02\2\u4C05\u6301\1\uFF05\u6301\uFF02\u0101\1\uFF02\u5105\u6305\u6401\uFF02" + 
	"\u0101\1\uFF02\u5605\u5D05\u6501\uFF02\u0101\1\uFF02\u1E05\u6605\u6502\u8402\u0101\1\1\1\uFF05\u6701" + 
	"\uFF02\u0101\1\uFF02\u5005\u6705\u6801\uFF02\u0401\uFF05\u6905\u6D01\1\1\1\uFF05\u6901\uFF02\u0101" + 
	"\1\uFF02\u5105\u7405\u6A01\uFF02\2\u7B05\u6B01\1\uFF05\u6B01\uFF02\u0101\1\uFF02\u5F05\u6B05\u6C01" + 
	"\uFF02\2\u4C05\u6901\1\uFF05\u6D01\uFF02\u0205\u6D05\u6F01\1\1\1\uFF05\u6E01\uFF02\u0101\1\uFF02" + 
	"\u5605\u6F05\u6F01\uFF02\2\u8505\u6E01\1\uFF05\u7001\uFF02\u0205\u7005\u7201\1\1\1\uFF05\u7101\uFF02" + 
	"\u0101\1\uFF02\u5605\u7205\u7201\uFF02\2\u4C05\u7101\1\uFF05\u7301\uFF02\u0205\u6805\u7401\1\1\1" + 
	"\uFF05\u7401\uFF02\2\u8705\u6901\1\uFF05\u747C\2\u0402\1\2\170\u0367\1\1\u0377\1\5\1\u0379\u037A" + 
	"\1\1\1\1\u0378\207\2\1\1\1\1\u0379\1\2\175\u0378\1\1\u037A\1\2\210\u0378\1\1\u037B\1\2\200\u037D" + 
	"\1\1\u037C\210\2\1\1\1\1\u037D\1\4\u037C\u037E\1\1\1\1\u037E\1\2\1\1\131\u037F\u037F\1\2\200\u037D" + 
	"\1\1\u0380\1\2\210\u0381\1\1\u0381\211\2\1\1\1\1\u0382\1\2\1\1\16\u0384\u0383\212\2\1\1\1\1\u0384" + 
	"\1\4\u0385\u0386\1\1\1\1\u0385\1\2\1\1\130\u0383\u0386\1\2\115\u0385\1\1\u0387\1\2\1\1\25\u0389" + 
	"\u0388\213\2\1\1\1\1\u0389\1\4\u038A\u038B\1\1\1\1\u038A\1\2\1\1\130\u0388\u038B\1\2\115\u038A\1" + 
	"\1\u038C\1\2\1\1\60\u038E\u038D\214\2\1\1\1\1\u038E\1\4\u038F\u0390\1\1\1\1\u038F\1\2\1\1\130\u038D" + 
	"\u0390\1\2\116\u038F\1\1\u0391\1\2\1\1\70\u0393\u0392\215\2\1\1\1\1\u0393\1\2\116\u0394\1\1\u0394" + 
	"\1\2\1\1\130\u0392\u0395\1\2\1\1\66\u0397\u0396\216\2\1\1\1\1\u0397\1\2\1\1\122\u0398\u0398\1\2" + 
	"\116\u0399\1\1\u0399\1\2\1\1\123\u039A\u039A\1\2\173\u0396\1\1\u039B\1\2\1\1\74\u039D\u039C\217" + 
	"\2\1\1\1\1\u039D\1\5\1\u039E\u03A5\1\1\1\1\u039E\1\2\223\u039F\1\1\u039F\1\2\173\u03A0\1\1\u03A0" + 
	"\1\4\u03A1\u03A2\1\1\1\1\u03A1\1\4\u039C\u03A3\1\1\1\1\u03A2\1\2\220\u03A1\1\1\u03A3\1\2\1\1\36" + 
	"\u03A4\u03A4\1\2\173\u039C\1\1\u03A5\1\2\173\u03A6\1\1\u03A6\1\5\1\u03A7\u03AB\1\1\1\1\u03A7\1\2" + 
	"\220\u03A8\1\1\u03A8\1\4\u039C\u03A9\1\1\1\1\u03A9\1\2\1\1\36\u03AA\u03AA\1\2\173\u039C\1\1\u03AB" + 
	"\1\2\1\1\36\u03AC\u03AC\1\2\173\u039C\1\1\u03AD\1\2\221\u03AF\1\1\u03AE\220\2\1\1\1\1\u03AF\1\4" + 
	"\u03AE\u03AD\1\1\1\1\u03B0\1\2\1\1\21\u03B2\u03B1\221\2\1\1\1\1\u03B2\1\2\1\1\122\u03B3\u03B3\1" + 
	"\2\222\u03B4\1\1\u03B4\1\2\1\1\123\u03B5\u03B5\1\2\173\u03B1\1\1\u03B6\1\2\36\u03B8\1\1\u03B7\222" + 
	"\2\1\1\1\1\u03B8\1\2\103\u03B9\1\1\u03B9\1\4\u03BA\u03BB\1\1\1\1\u03BA\1\2\64\u03B7\1\1\u03BB\1" + 
	"\2\u7A02\6\u0101\1\uFF02\u6D05\uBB05\uBB01\uFF02\u0205\uB805\uBA01\1\1\1\uFF05\uBC01\uFF02\2\u4905" + 
	"\uBB01\1\uFF05\uBD01\uFF02\u0101\1\uFF02\u5005\uBE05\uBD02\u9102\u0101\1\1\1\uFF05\uBF01\uFF02\2" + 
	"\u7B05\uC001\1\uFF05\uC001\uFF02\u0205\uC005\uC201\1\1\1\uFF05\uC101\uFF02\u0205\uC305\uC501\1\1" + 
	"\1\uFF05\uC201\uFF02\u0101\1\uFF02\u5605\uC205\uC301\uFF02\2\u7B05\uC001\1\uFF05\uC401\uFF02\u0101" + 
	"\1\uFF02\u5105\uBD05\uC501\uFF02\u0101\1\uFF02\u5605\uC305\uC601\uFF02\u0205\uC605\uC801\1\1\1\uFF05" + 
	"\uC602\u9202\u0101\1\1\1\uFF05\uC801\uFF02\2\u9305\uC601\1\uFF05\uC901\uFF02\u0501\uFF05\uCA05\uCB05" + 
	"\uCD01\1\1\1\uFF05\uC902\u9302\u0101\1\1\1\uFF05\uCB01\uFF02\2\u9405\uCA01\1\uFF05\uCC01\uFF02\2" + 
	"\u9505\uCA01\1\uFF05\uCD01\uFF02\2\u9605\uCA01\1\uFF05\uCE01\uFF02\u0101\1\uFF02\u5905\uCF05\uCE02" + 
	"\u9402\u0101\1\1\1\uFF05\uD001\uFF02\2\u4A05\uD101\1\uFF05\uD101\uFF02\u0101\1\uFF02\u5005\uD105" + 
	"\uD201\uFF02\u0205\uD205\uD401\1\1\1\uFF05\uD301\uFF02\u0101\1\uFF02\u5105\uCE05\uD401\uFF02\2\u9705" + 
	"\uD301\1\uFF05\uD501\uFF02\u0101\1\uFF02\u5905\uD605\uD502\u9502\u0101\1\1\1\uFF05\uD701\uFF02\2" + 
	"\u4A05\uD601\1\uFF05\uD801\uFF02\u0101\1\uFF02\u5905\uD905\uD802\u9602\u0101\1\1\1\uFF05\uDA01\uFF02" + 
	"\2\u4A05\uDB01\1\uFF05\uDB01\uFF02\u0101\1\uFF02\u5005\uDB05\uDC01\uFF02\2\u9905\uDD01\1\uFF05\uDD01" + 
	"\uFF02\u0101\1\uFF02\u5105\uD805\uDE01\uFF02\2\u9805\uE001\1\uFF05\uDE02\u9702\u0101\1\1\1\uFF05" + 
	"\uE001\uFF02\u0205\uDE05\uE101\1\1\1\uFF05\uE101\uFF02\u0101\1\uFF02\u5705\uE105\uE201\uFF02\2\u9805" + 
	"\uE001\1\uFF05\uE301\uFF02\2\u4B05\uE501\1\uFF05\uE302\u9802\u0101\1\1\1\uFF05\uE501\uFF02\u0101" + 
	"\1\uFF02\u5A05\uE505\uE601\uFF02\2\u9905\uE401\1\uFF05\uE701\uFF02\u0501\uFF05\uE805\uE905\uEB01" + 
	"\1\1\1\uFF05\uE702\u9902\u0101\1\1\1\uFF05\uE901\uFF02\2\u5405\uE801\1\uFF05\uEA01\uFF02\2\u9A05" + 
	"\uE801\1\uFF05\uEB01\uFF02\2\u9305\uE801\1\uFF05\uEC01\uFF02\u0101\1\uFF02\u5205\uED05\uEC02\u9A02" + 
	"\u0101\1\1\1\uFF05\uEE01\uFF02\u0205\uEE05\uF001\1\1\1\uFF05\uEF01\uFF02\u0205\uF005\uF201\1\1\1" + 
	"\uFF05\uF001\uFF02\2\u9B05\uEF01\1\uFF05\uF101\uFF02\u0101\1\uFF02\u5305\uEC05\uF201\uFF02\u0101" + 
	"\1\uFF02\u5705\uF005\uF301\uFF02\2\u9905\uF501\1\uFF05\uF302\u9B02\u0101\1\1\1\uFF05\uF501\uFF02" + 
	"\u0205\uF305\uF601\1\1\1\uFF05\uF601\uFF02\u0101\1\uFF02\u5705\uF605\uF701\uFF02\2\u9905\uF501\1" + 
	"\uFF02\u9C02\2\u0202\u0502\u0802\u0B02\u0E02\u1102\u1402\u1702\u1B02\u1F02\u2302\u2602\u2902\u2C02" + 
	"\u2F02\u3202\u3502\u3902\u3C02\u3F02\u4102\u4302\u4502\u4B02\u5002\u5302\u5C02\u5F02\u6F02\u7E02" + 
	"\u8602\u9802\uA002\uA802\uBB02\uC202\uC702\uCF02\uD902\uE302\uEC02\uF102\uF902\uFD03\u0303\u0F03" + 
	"\u1303\u1603\u1B03\u2003\u2303\u2803\u2C7C\2\u0402\u0138\u0145\u014A\u0151\u015D\u0163\u016D\u017C" + 
	"\u0184\u0186\u018E\u0195\u019F\u01A3\u01A8\u01AF\u01B4\u01BE\u01C8\u01CC\u01CF\u01D4\u01D8\u01DC" + 
	"\u01E1\u01EA\u01FD\u0201\u0206\u0208\u0216\u021F\u0224\u0229\u022E\u0233\u0238\u023F\u0245\u024E" + 
	"\u0259\u0260\u0268\u0270\u0275\u027D\u0283\u028E\u0294\u029F\u02A3\u02A7\u02AB\u02C1\u02C7\u02D2" + 
	"\u02D4\u02D9\u02E3\u02EA\u02F3\u02FC\u0302\u0308\u030D\u031F\u0326\u032A\u032E\u0335\u0338\u033A" + 
	"\u033D\u033F\u0348\u034F\u0358\u035E\u0366\u0377\u037B\u0380\u0382\u0387\u038C\u0391\u0395\u039B" + 
	"\u03AD\u03B0\u03B6\u03BE\u03C7\u03CA\u03CF\u03D6\u03D9\u03DF\u03E4\u03E8\u03ED\u03F4\55\107\141" + 
	"\265\325\361\u0109\u0125\u0133\u0155\u016D\u0174\u017C\u0180\u018C\u0197\u0198\u019D\u01D1\u01D8" + 
	"\u01E1\u01EA\u021C\u0250\u0251\u0275\u0286\u029F\u02A9\u02AB\u02B2\u02C1\u02CA\u02FC\u0304\u030A" + 
	"\u030D\u032E\u0369\u0377\u03A8\u03C1\u03CA\u03F6\2\2\2\2\2\2\2\2\2\2\2\2\2\2\2\2\2\2\2\2\3\110\15" + 
	"\u017D\364\u02DC\u0100\314\261\u014B\243\350\u01A9\233\3\u01D5\2\3\111\3\113\3\122\3\114\10\u03B8" + 
	"\277\u0109\325\u0153\204\4\u0337\u0331\3\136\6\201\322\u032F\u0106\3\212\4\256\212\5\201\322\u0106" + 
	"\3\265\5\201\322\u0106\3\305\3\320\3\322\6\u0165\216\227\u013C\3\353\3\357\5\211\276\u02EB\4\376" + 
	"\272\3\u0104\4\322\u0106\3\u0336\4\u0114\u0116\3\u011A\5\u03B7\u011F\u0152\6\341\u0123\u0140\u02FD" + 
	"\4\u011E\u0133\4\u012B\u02FD\3\u0106\4\u013F\u0168\4\u01FA\u0148\3\u014E\4\u0141\u0169\3\u0106\3" + 
	"\u0181\4\u016C\u032D\3\u0106\10\u0117\u0154\335\u0113\u0240\u01C9\7\u028B\u0291\u01B5\u01E6\u01B0" + 
	"\10\u03B9\u02F0\u02F9\u0193\u01CD\u018C\10\u0178\u02ED\u0170\u02F6\u02E6\u02D6\3\u0198\4\u01A6\u01A2" + 
	"\3\u01AC\3\u01B0\6\u0189\u0191\u0288\u02F9\5\u02B8\u02BB\u013D\10\u03BC\u0160\370\237\220\247\7" + 
	"\u03DC\u03D2\121\132\u03D7\32\u03E6\u0328\u0197\u01D1\226\u02E4\u01ED\u0207\u019D\u015B\u02AC\310" + 
	"\u0385\u02D8\215\u0167\255\u02D3\u038A\300\361\u0124\336\u013E\26\u0343\u02D1\u0322\u033E\u036A" + 
	"\u0399\u012B\u02C0\u035C\u0394\u01DD\u0307\u0353\u038F\u01FE\u0372\u0364\u021B\u02E0\u0323\3\u01D9" + 
	"\5\u0217\u01E2\u01D9\3\u01E2\3\u01EB\3\u01F5\3\u0203\3\u01E0\5\u0217\u03E9\u01DE\3\u0218\3\u0221" + 
	"\3\u0226\3\u022B\3\u0230\3\u0235\3\u023A\3\u0241\3\u0247\3\u0250\3\u025B\7\u0269\u0262\u0271\u0276" + 
	"\u0284\3\u0269\4\u0269\u0284\3\u0276\3\u0276\4\u028C\u01E7\3\u02AC\3\u027F\3\u02A0\3\u0177\4\u02A5" + 
	"\u02A9\3\u02A5\4\u02C2\u02A9\4\u02AC\u02C8\4\u02AC\u02C8\7\u017B\u0173\u02F1\u02D5\301\4\u02C2\u02AC" + 
	"\4\u02AC\u02C8\3\u02A0\3\u02F4\3\u02FF\5\u0187\u018F\u0301\11\u0350\u0355\u0327\u0361\u032F\u0367" + 
	"\u0359\3\u030E\u7A02\3\u3002\u0105\u0C02\u0903\u8303\u3705\uAF05\uA403\uFC05\u0C05\u9E05\u9A05\u9402" + 
	"\u0103\u7F02\u0405\uBF05\u7605\u3105\u6A02\u0105\u0C02\u0105\u0C02\u0205\u3A05\u7B02\u0105\u0C02" + 
	"\u0105\u4302\u0105\u0C02\u0105\u0C02\u0105\u0C02\u0105\u0C02\u0105\u6D02\u0205\u7605\u7F02\u0105" + 
	"\u6802\u0105\u0C02\u0105\u0C02\u0105\u0C02\u0105\u0C02\u0105\u0C02\u0105\u0C02\u0205\u9F05\uA602" + 
	"\u0105\uAD02\u0105\uB202\u0105\u9D02\u1203\u9A04\u4203\uE305\u0302\u4D03\uBB05\u0903\uB804\u8403" + 
	"\uAF03\uCC02\uEE03\u5503\u3803\u2604\u9104\uF604\uED02\u0405\uE705\uC502\u5F02\u6F02\u0105\uC902" + 
	"\u0105\uC902\u0105\uC902\u0105\uD202\u0105\uDF02\u0405\uDC05\uF402\uE005\uE302\u0105\uE702\u0105" + 
	"\uEE02\u1502\u1702\u0102\u1802\u0402\u1A02\u0702\u1E02\u0A02\u2D02\u0D02\u2602\u1002\u1C02\u1302" + 
	"\u9202\u1602\u3602\u1A02\u2E02\u1E02\u2702\u2202\u2302\u2502\u3902\u2802\u2902\u2B02\u3D02\u2E02" + 
	"\u7A02\u3102\u4C02\u3402\u3F02\u3802\u4A02\u3B02\u4B02\u3E02\u3D05\u4702";
}
