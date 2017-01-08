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
// Decision count: 180 (LL1: 142; ALL*: 38)
// State count: 1013 (Non-terminal end: 156; choices: 180; non-terminal: 401; terminal: 276)
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

	static final int CLASS_OR_INTERFACE_BODY_DECL_1_2_2 = 4;

	static final int ARRAY_INITIALIZER_2_1_2 = 5;

	static final int FORMAL_PARAMETER_4 = 6;

	static final int EXPLICIT_CONSTRUCTOR_INVOCATION_1 = 7;

	static final int EXPLICIT_CONSTRUCTOR_INVOCATION_1_2_1 = 8;

	static final int STATEMENTS_1 = 9;

	static final int STATEMENTS_1_1_2_1 = 10;

	static final int TYPE_2 = 11;

	static final int QUALIFIED_TYPE_3 = 12;

	static final int QUALIFIED_NAME_2 = 13;

	static final int EXPRESSION_1 = 14;

	static final int LAMBDA_EXPRESSION_1 = 15;

	static final int LAMBDA_EXPRESSION_WITHOUT_CAST_1 = 16;

	static final int CONDITIONAL_EXPRESSION_2_1_4 = 17;

	static final int SHIFT_EXPRESSION_2 = 18;

	static final int SHIFT_EXPRESSION_2_1_1 = 19;

	static final int UNARY_EXPRESSION_NOT_PLUS_MINUS_1 = 20;

	static final int CAST_EXPRESSION_3 = 21;

	static final int PRIMARY_EXPRESSION_1 = 22;

	static final int PRIMARY_EXPRESSION_WITHOUT_SUPER_SUFFIX_2 = 23;

	static final int PRIMARY_PREFIX_1 = 24;

	static final int PRIMARY_PREFIX_1_3_2_1_2 = 25;

	static final int PRIMARY_SUFFIX_1 = 26;

	static final int PRIMARY_SUFFIX_WITHOUT_SUPER_1_1_2 = 27;

	static final int ARRAY_CREATION_EXPR_REST_1 = 28;

	static final int ARRAY_DIM_EXPRS_MANDATORY_1 = 29;

	static final int ARRAY_DIMS_MANDATORY_1 = 30;

	static final int STATEMENT_1 = 31;

	static final int BLOCK_STATEMENT_1 = 32;

	static final int FOR_STATEMENT_3 = 33;

	static final int FOR_INIT_1 = 34;

	static final int RESOURCE_SPECIFICATION_3 = 35;

	static final int ANNOTATION_1 = 36;

	static final int ELEMENT_VALUE_LIST_2 = 37;

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

	static final int IMPORT_DECLS_1_1_1 = 80;

	static final int IMPORT_DECL_3 = 88;

	static final int TYPE_DECLS_1_1_1 = 92;

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

	static final int ANNOTATION_TYPE_BODY_2_1_2_1_1 = 206;

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

	static final int CLASS_OR_INTERFACE_BODY_DECLS_1_1_2_1_1 = 258;

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

	static final int STATEMENTS_1_1_2_1_1_1 = 383;

	static final int STATEMENTS_1_1_2_2_1_1 = 383;

	static final int INITIALIZER_DECL_1 = 387;

	static final int TYPE_1_1 = 390;

	static final int TYPE_1_2 = 390;

	static final int TYPE_2_1_1 = 389;

	static final int REFERENCE_TYPE_1_1_1 = 397;

	static final int REFERENCE_TYPE_1_1_2 = 395;

	static final int REFERENCE_TYPE_1_2_1 = 399;

	static final int REFERENCE_TYPE_1_2_2_1_1 = 395;

	static final int QUALIFIED_TYPE_1 = 403;

	static final int QUALIFIED_TYPE_2_1 = 404;

	static final int QUALIFIED_TYPE_3_1_2 = 408;

	static final int QUALIFIED_TYPE_3_1_3 = 409;

	static final int QUALIFIED_TYPE_3_1_4_1 = 404;

	static final int TYPE_ARGUMENTS_2 = 414;

	static final int TYPE_ARGUMENTS_OR_DIAMOND_2_1 = 418;

	static final int TYPE_ARGUMENT_LIST_1_1 = 421;

	static final int TYPE_ARGUMENT_LIST_2_1 = 424;

	static final int TYPE_ARGUMENT_LIST_2_2_1_2 = 424;

	static final int TYPE_ARGUMENT_1 = 429;

	static final int TYPE_ARGUMENT_2_1 = 428;

	static final int TYPE_ARGUMENT_2_2 = 428;

	static final int WILDCARD_2_1_1_2 = 438;

	static final int WILDCARD_2_1_1_3 = 433;

	static final int WILDCARD_2_1_2_2 = 441;

	static final int WILDCARD_2_1_2_3 = 433;

	static final int RESULT_TYPE_1_2 = 453;

	static final int ANNOTATED_QUALIFIED_TYPE_1 = 458;

	static final int ANNOTATED_QUALIFIED_TYPE_2 = 457;

	static final int QUALIFIED_NAME_1 = 461;

	static final int QUALIFIED_NAME_2_1_2 = 461;

	static final int NAME_1_1 = 465;

	static final int EXPRESSION_1_1 = 469;

	static final int EXPRESSION_1_2 = 469;

	static final int ASSIGNMENT_EXPRESSION_1 = 474;

	static final int ASSIGNMENT_EXPRESSION_2_1_1 = 476;

	static final int ASSIGNMENT_EXPRESSION_2_1_2 = 473;

	static final int LAMBDA_EXPRESSION_1_1_2 = 481;

	static final int LAMBDA_EXPRESSION_1_1_3 = 482;

	static final int LAMBDA_EXPRESSION_1_1_4 = 483;

	static final int LAMBDA_EXPRESSION_1_1_6 = 478;

	static final int LAMBDA_EXPRESSION_1_2 = 478;

	static final int LAMBDA_EXPRESSION_WITHOUT_CAST_1_1_1 = 489;

	static final int LAMBDA_EXPRESSION_WITHOUT_CAST_1_1_3 = 487;

	static final int LAMBDA_EXPRESSION_WITHOUT_CAST_1_2_4 = 487;

	static final int LAMBDA_EXPRESSION_WITHOUT_CAST_1_3_2 = 497;

	static final int LAMBDA_EXPRESSION_WITHOUT_CAST_1_3_5 = 487;

	static final int LAMBDA_EXPRESSION_WITHOUT_CAST_1_4_2 = 502;

	static final int LAMBDA_EXPRESSION_WITHOUT_CAST_1_4_5 = 487;

	static final int LAMBDA_BODY_1_1_1 = 506;

	static final int LAMBDA_BODY_1_2_1 = 506;

	static final int INFERRED_FORMAL_PARAMETER_LIST_1 = 511;

	static final int INFERRED_FORMAL_PARAMETER_LIST_2_1_2 = 511;

	static final int INFERRED_FORMAL_PARAMETER_1 = 515;

	static final int CONDITIONAL_EXPRESSION_1 = 532;

	static final int CONDITIONAL_EXPRESSION_2_1_2 = 535;

	static final int CONDITIONAL_EXPRESSION_2_1_4_1 = 531;

	static final int CONDITIONAL_EXPRESSION_2_1_4_2 = 531;

	static final int CONDITIONAL_OR_EXPRESSION_1 = 541;

	static final int CONDITIONAL_OR_EXPRESSION_2_1_2 = 541;

	static final int CONDITIONAL_AND_EXPRESSION_1 = 546;

	static final int CONDITIONAL_AND_EXPRESSION_2_1_2 = 546;

	static final int INCLUSIVE_OR_EXPRESSION_1 = 551;

	static final int INCLUSIVE_OR_EXPRESSION_2_1_2 = 551;

	static final int EXCLUSIVE_OR_EXPRESSION_1 = 556;

	static final int EXCLUSIVE_OR_EXPRESSION_2_1_2 = 556;

	static final int AND_EXPRESSION_1 = 561;

	static final int AND_EXPRESSION_2_1_2 = 561;

	static final int EQUALITY_EXPRESSION_1 = 566;

	static final int EQUALITY_EXPRESSION_2_1_2 = 566;

	static final int INSTANCE_OF_EXPRESSION_1 = 573;

	static final int INSTANCE_OF_EXPRESSION_2_1_2 = 576;

	static final int INSTANCE_OF_EXPRESSION_2_1_3 = 572;

	static final int RELATIONAL_EXPRESSION_1 = 579;

	static final int RELATIONAL_EXPRESSION_2_1_2 = 579;

	static final int SHIFT_EXPRESSION_1 = 588;

	static final int SHIFT_EXPRESSION_2_1_2 = 588;

	static final int ADDITIVE_EXPRESSION_1 = 599;

	static final int ADDITIVE_EXPRESSION_2_1_2 = 599;

	static final int MULTIPLICATIVE_EXPRESSION_1 = 606;

	static final int MULTIPLICATIVE_EXPRESSION_2_1_2 = 606;

	static final int UNARY_EXPRESSION_1_1 = 613;

	static final int UNARY_EXPRESSION_1_2_2 = 613;

	static final int UNARY_EXPRESSION_1_3 = 613;

	static final int PREFIX_EXPRESSION_2 = 621;

	static final int UNARY_EXPRESSION_NOT_PLUS_MINUS_1_1_2 = 626;

	static final int UNARY_EXPRESSION_NOT_PLUS_MINUS_1_2 = 626;

	static final int UNARY_EXPRESSION_NOT_PLUS_MINUS_1_3 = 626;

	static final int POSTFIX_EXPRESSION_1 = 635;

	static final int CAST_EXPRESSION_2 = 642;

	static final int CAST_EXPRESSION_3_1_1 = 644;

	static final int CAST_EXPRESSION_3_1_3 = 640;

	static final int CAST_EXPRESSION_3_2_1 = 647;

	static final int CAST_EXPRESSION_3_2_2 = 648;

	static final int CAST_EXPRESSION_3_2_4 = 640;

	static final int REFERENCE_CAST_TYPE_REST_1_1_1_1_2 = 655;

	static final int REFERENCE_CAST_TYPE_REST_1_1_1_1_3 = 653;

	static final int PRIMARY_EXPRESSION_1_1 = 668;

	static final int PRIMARY_EXPRESSION_1_2 = 668;

	static final int PRIMARY_NO_NEW_ARRAY_1 = 673;

	static final int PRIMARY_NO_NEW_ARRAY_2_1_1 = 673;

	static final int PRIMARY_EXPRESSION_WITHOUT_SUPER_SUFFIX_1 = 677;

	static final int PRIMARY_EXPRESSION_WITHOUT_SUPER_SUFFIX_2_1_1 = 677;

	static final int PRIMARY_PREFIX_1_1 = 680;

	static final int PRIMARY_PREFIX_1_3_2_1_2_1 = 680;

	static final int PRIMARY_PREFIX_1_3_2_1_2_2 = 680;

	static final int PRIMARY_PREFIX_1_3_2_2_1 = 680;

	static final int PRIMARY_PREFIX_1_4 = 680;

	static final int PRIMARY_PREFIX_1_5_1 = 692;

	static final int PRIMARY_PREFIX_1_6_1 = 695;

	static final int PRIMARY_PREFIX_1_6_2 = 680;

	static final int PRIMARY_PREFIX_1_7_1 = 680;

	static final int PRIMARY_PREFIX_1_8 = 680;

	static final int PRIMARY_PREFIX_1_9_2 = 700;

	static final int PRIMARY_SUFFIX_1_1_1 = 702;

	static final int PRIMARY_SUFFIX_1_3 = 702;

	static final int PRIMARY_SUFFIX_WITHOUT_SUPER_1_1_2_2 = 708;

	static final int PRIMARY_SUFFIX_WITHOUT_SUPER_1_1_2_3 = 708;

	static final int PRIMARY_SUFFIX_WITHOUT_SUPER_1_1_2_4 = 708;

	static final int PRIMARY_SUFFIX_WITHOUT_SUPER_1_2_2 = 717;

	static final int FIELD_ACCESS_1 = 719;

	static final int METHOD_INVOCATION_1_1 = 722;

	static final int METHOD_INVOCATION_2 = 724;

	static final int METHOD_INVOCATION_3 = 721;

	static final int ARGUMENTS_2_1_1 = 728;

	static final int ARGUMENTS_2_1_2_1 = 732;

	static final int ARGUMENTS_2_1_2_2_1_2 = 732;

	static final int METHOD_REFERENCE_SUFFIX_2_1 = 738;

	static final int METHOD_REFERENCE_SUFFIX_3_1 = 736;

	static final int CLASS_CREATION_EXPR_2_1 = 745;

	static final int CLASS_CREATION_EXPR_3 = 747;

	static final int CLASS_CREATION_EXPR_4 = 748;

	static final int CLASS_CREATION_EXPR_5 = 749;

	static final int CLASS_CREATION_EXPR_6_1 = 743;

	static final int ARRAY_CREATION_EXPR_2_1 = 754;

	static final int ARRAY_CREATION_EXPR_3 = 756;

	static final int ARRAY_CREATION_EXPR_4_1 = 757;

	static final int ARRAY_CREATION_EXPR_4_2 = 757;

	static final int ARRAY_CREATION_EXPR_5 = 752;

	static final int ARRAY_CREATION_EXPR_REST_1_1_1 = 763;

	static final int ARRAY_CREATION_EXPR_REST_1_1_2 = 761;

	static final int ARRAY_CREATION_EXPR_REST_1_2_1 = 765;

	static final int ARRAY_CREATION_EXPR_REST_1_2_2 = 761;

	static final int ARRAY_DIM_EXPRS_MANDATORY_1_1_1 = 769;

	static final int ARRAY_DIM_EXPRS_MANDATORY_1_1_3 = 771;

	static final int ARRAY_DIMS_MANDATORY_1_1_1 = 775;

	static final int STATEMENT_1_1 = 778;

	static final int STATEMENT_1_2 = 778;

	static final int STATEMENT_1_3 = 778;

	static final int STATEMENT_1_4 = 778;

	static final int STATEMENT_1_5 = 778;

	static final int STATEMENT_1_6 = 778;

	static final int STATEMENT_1_7 = 778;

	static final int STATEMENT_1_8 = 778;

	static final int STATEMENT_1_9 = 778;

	static final int STATEMENT_1_10 = 778;

	static final int STATEMENT_1_11 = 778;

	static final int STATEMENT_1_12 = 778;

	static final int STATEMENT_1_13 = 778;

	static final int STATEMENT_1_14 = 778;

	static final int STATEMENT_1_15 = 778;

	static final int STATEMENT_1_16 = 778;

	static final int ASSERT_STATEMENT_2 = 798;

	static final int ASSERT_STATEMENT_3_1_2 = 799;

	static final int LABELED_STATEMENT_1 = 804;

	static final int LABELED_STATEMENT_3 = 803;

	static final int BLOCK_2 = 809;

	static final int BLOCK_STATEMENT_1_1_1 = 813;

	static final int BLOCK_STATEMENT_1_1_2 = 811;

	static final int BLOCK_STATEMENT_1_2_1 = 815;

	static final int BLOCK_STATEMENT_1_3 = 811;

	static final int VARIABLE_DECL_EXPRESSION_1 = 819;

	static final int VARIABLE_DECL_EXPRESSION_2 = 818;

	static final int EXPRESSION_STATEMENT_1 = 824;

	static final int STATEMENT_EXPRESSION_1 = 826;

	static final int SWITCH_STATEMENT_3 = 831;

	static final int SWITCH_STATEMENT_6_1_1 = 833;

	static final int SWITCH_ENTRY_1_1_2 = 838;

	static final int SWITCH_ENTRY_3 = 837;

	static final int IF_STATEMENT_3 = 847;

	static final int IF_STATEMENT_5 = 849;

	static final int IF_STATEMENT_6_1_2 = 844;

	static final int WHILE_STATEMENT_3 = 856;

	static final int WHILE_STATEMENT_5 = 853;

	static final int DO_STATEMENT_2 = 861;

	static final int DO_STATEMENT_5 = 864;

	static final int FOR_STATEMENT_3_1_1 = 872;

	static final int FOR_STATEMENT_3_1_3 = 870;

	static final int FOR_STATEMENT_3_2_1_1 = 875;

	static final int FOR_STATEMENT_3_2_3_1 = 878;

	static final int FOR_STATEMENT_3_2_5_1 = 870;

	static final int FOR_STATEMENT_5 = 867;

	static final int FOR_INIT_1_1_1 = 884;

	static final int FOR_INIT_1_2 = 884;

	static final int STATEMENT_EXPRESSION_LIST_1 = 889;

	static final int STATEMENT_EXPRESSION_LIST_2_1_2 = 889;

	static final int FOR_UPDATE_1 = 893;

	static final int BREAK_STATEMENT_2_1 = 897;

	static final int CONTINUE_STATEMENT_2_1 = 902;

	static final int RETURN_STATEMENT_2_1 = 907;

	static final int THROW_STATEMENT_2 = 912;

	static final int SYNCHRONIZED_STATEMENT_3 = 917;

	static final int SYNCHRONIZED_STATEMENT_5 = 914;

	static final int TRY_STATEMENT_2_1_1 = 923;

	static final int TRY_STATEMENT_2_1_2 = 924;

	static final int TRY_STATEMENT_2_1_3_1 = 925;

	static final int TRY_STATEMENT_2_1_4_1_2 = 920;

	static final int TRY_STATEMENT_2_2_1 = 930;

	static final int TRY_STATEMENT_2_2_2_1_1 = 932;

	static final int TRY_STATEMENT_2_2_2_1_2_1_2 = 920;

	static final int TRY_STATEMENT_2_2_2_2_2 = 920;

	static final int CATCH_CLAUSES_1_1_1 = 939;

	static final int CATCH_CLAUSE_3 = 944;

	static final int CATCH_CLAUSE_5 = 941;

	static final int CATCH_FORMAL_PARAMETER_1 = 948;

	static final int CATCH_FORMAL_PARAMETER_2 = 949;

	static final int CATCH_FORMAL_PARAMETER_3_1_1_1_2 = 952;

	static final int CATCH_FORMAL_PARAMETER_4 = 947;

	static final int RESOURCE_SPECIFICATION_2 = 957;

	static final int RESOURCE_SPECIFICATION_3_1_2 = 957;

	static final int ANNOTATIONS_1_1_1 = 963;

	static final int ANNOTATION_1_1 = 967;

	static final int ANNOTATION_1_2 = 967;

	static final int ANNOTATION_1_3 = 967;

	static final int NORMAL_ANNOTATION_2 = 974;

	static final int NORMAL_ANNOTATION_4_1 = 976;

	static final int MARKER_ANNOTATION_2 = 979;

	static final int SINGLE_ELEMENT_ANNOTATION_2 = 984;

	static final int SINGLE_ELEMENT_ANNOTATION_4 = 986;

	static final int ELEMENT_VALUE_PAIR_LIST_1 = 989;

	static final int ELEMENT_VALUE_PAIR_LIST_2_1_2 = 989;

	static final int ELEMENT_VALUE_PAIR_1 = 994;

	static final int ELEMENT_VALUE_PAIR_3 = 993;

	static final int ELEMENT_VALUE_1_1 = 997;

	static final int ELEMENT_VALUE_1_2 = 997;

	static final int ELEMENT_VALUE_1_3 = 997;

	static final int ELEMENT_VALUE_ARRAY_INITIALIZER_2_1 = 1004;

	static final int ELEMENT_VALUE_LIST_1 = 1010;

	static final int ELEMENT_VALUE_LIST_2_1_2 = 1010;

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
		nonTerminal(ret, ClassOrInterfaceBodyDecl, { typeKind })
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
		action({
			entryPoint = METHOD_DECL_ENTRY;
			run();
		})
		nonTerminal(modifiers, Modifiers)
		nonTerminal(ret, MethodDecl, { modifiers })
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
		action({
			entryPoint = FIELD_DECL_ENTRY;
			run();
		})
		nonTerminal(modifiers, Modifiers)
		nonTerminal(ret, FieldDecl, { modifiers })
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
		action({
			entryPoint = ANNOTATION_ELEMENT_DECL_ENTRY;
			run();
		})
		nonTerminal(modifiers, Modifiers)
		nonTerminal(ret, AnnotationTypeMemberDecl, { modifiers })
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
		action({
			entryPoint = TYPE_ENTRY;
			run();
		})
		nonTerminal(annotations, Annotations)
		nonTerminal(ret, Type, { annotations })
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
		if (predict(COMPILATION_UNIT_1) == 0) {
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
			sequence(
				nonTerminal(importDecl, ImportDecl)
				action({ imports = append(imports, importDecl); })
			)
		)
		action({ return imports; })
	) */
	protected BUTree<SNodeList> parseImportDecls() throws ParseException {
		BUTree<SNodeList> imports = emptyList();
		BUTree<SImportDecl> importDecl = null;
		int __token;
		__token = getToken(0).kind;
		while (__token == TokenType.IMPORT) {
			pushCallStack(IMPORT_DECLS_1_1_1);
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
			sequence(
				terminal(STATIC)
				action({ isStatic = true; })
			)
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
			sequence(
				nonTerminal(typeDecl, TypeDecl)
				action({ types = append(types, typeDecl); })
			)
		)
		action({ return types; })
	) */
	protected BUTree<SNodeList> parseTypeDecls() throws ParseException {
		BUTree<SNodeList> types = emptyList();
		BUTree<? extends STypeDecl> typeDecl = null;
		int __token;
		__token = getToken(0).kind;
		while (__token != TokenType.EOF) {
			pushCallStack(TYPE_DECLS_1_1_1);
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
		while (predict(MODIFIERS_1) == 0) {
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
		while (((__token - 6 & ~63) == 0 && (1L << __token - 6 & (1L << TokenType.ABSTRACT - 6 | 1L << TokenType.FINAL - 6 | 1L << TokenType.NATIVE - 6 | 1L << TokenType.PRIVATE - 6 | 1L << TokenType.PROTECTED - 6 | 1L << TokenType.PUBLIC - 6 | 1L << TokenType.STATIC - 6 | 1L << TokenType.STRICTFP - 6 | 1L << TokenType.SYNCHRONIZED - 6 | 1L << TokenType.TRANSIENT - 6 | 1L << TokenType.VOLATILE - 6)) != 0) || ((__token - 74 & ~63) == 0 && (1L << __token - 74 & (1L << TokenType.AT - 74)) != 0)) {
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
					nonTerminal(ret, ClassOrInterfaceDecl, { }, { modifiers })
					nonTerminal(ret, EnumDecl, { }, { modifiers })
					nonTerminal(ret, AnnotationTypeDecl, { }, { modifiers })
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
		} else if (((__token - 6 & ~63) == 0 && (1L << __token - 6 & (1L << TokenType.ABSTRACT - 6 | 1L << TokenType.CLASS - 6 | 1L << TokenType.DEFAULT - 6 | 1L << TokenType.ENUM - 6 | 1L << TokenType.FINAL - 6 | 1L << TokenType.INTERFACE - 6 | 1L << TokenType.NATIVE - 6 | 1L << TokenType.PRIVATE - 6 | 1L << TokenType.PROTECTED - 6 | 1L << TokenType.PUBLIC - 6 | 1L << TokenType.STATIC - 6 | 1L << TokenType.STRICTFP - 6 | 1L << TokenType.SYNCHRONIZED - 6 | 1L << TokenType.TRANSIENT - 6 | 1L << TokenType.VOLATILE - 6)) != 0) || ((__token - 74 & ~63) == 0 && (1L << __token - 74 & (1L << TokenType.AT - 74)) != 0)) {
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
					nonTerminal(implementsClause, ImplementsList, { }, { typeKind, problem })
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
		nonTerminal(members, ClassOrInterfaceBody, { }, { typeKind })
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
		} else if ((__token - 74 & ~63) == 0 && (1L << __token - 74 & (1L << TokenType.AT - 74 | 1L << TokenType.NODE_VARIABLE - 74 | 1L << TokenType.IDENTIFIER - 74)) != 0) {
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
		} else if ((__token - 74 & ~63) == 0 && (1L << __token - 74 & (1L << TokenType.AT - 74 | 1L << TokenType.NODE_VARIABLE - 74 | 1L << TokenType.IDENTIFIER - 74)) != 0) {
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
			nonTerminal(implementsClause, ImplementsList, { }, { TypeKind.Enum, problem })
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
			sequence(
				terminal(COMMA)
				action({ trailingComma = true; })
			)
		)
		zeroOrOne(
			sequence(
				terminal(SEMICOLON)
				nonTerminal(members, ClassOrInterfaceBodyDecls, { }, { TypeKind.Enum })
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
		} else if (((__token - 6 & ~63) == 0 && (1L << __token - 6 & (1L << TokenType.ABSTRACT - 6 | 1L << TokenType.DEFAULT - 6 | 1L << TokenType.FINAL - 6 | 1L << TokenType.NATIVE - 6 | 1L << TokenType.PRIVATE - 6 | 1L << TokenType.PROTECTED - 6 | 1L << TokenType.PUBLIC - 6 | 1L << TokenType.STATIC - 6 | 1L << TokenType.STRICTFP - 6 | 1L << TokenType.SYNCHRONIZED - 6 | 1L << TokenType.TRANSIENT - 6 | 1L << TokenType.VOLATILE - 6)) != 0) || ((__token - 74 & ~63) == 0 && (1L << __token - 74 & (1L << TokenType.AT - 74 | 1L << TokenType.NODE_VARIABLE - 74 | 1L << TokenType.IDENTIFIER - 74)) != 0)) {
			pushCallStack(ENUM_DECL_5_1_2_1);
			entry = parseEnumConstantDecl();
			popCallStack();
			constants = append(constants, entry);
			__token = getToken(0).kind;
			while (predict(ENUM_DECL_5_1_2_2) == 0) {
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
			nonTerminal(classBody, ClassOrInterfaceBody, { }, { TypeKind.Class })
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
					sequence(
						nonTerminal(member, AnnotationTypeBodyDecl)
						action({ ret = append(ret, member); })
					)
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
		} else if (((__token - 6 & ~63) == 0 && (1L << __token - 6 & (1L << TokenType.ABSTRACT - 6 | 1L << TokenType.BOOLEAN - 6 | 1L << TokenType.BYTE - 6 | 1L << TokenType.CHAR - 6 | 1L << TokenType.CLASS - 6 | 1L << TokenType.DEFAULT - 6 | 1L << TokenType.DOUBLE - 6 | 1L << TokenType.ENUM - 6 | 1L << TokenType.FINAL - 6 | 1L << TokenType.FLOAT - 6 | 1L << TokenType.INT - 6 | 1L << TokenType.INTERFACE - 6 | 1L << TokenType.LONG - 6 | 1L << TokenType.NATIVE - 6 | 1L << TokenType.PRIVATE - 6 | 1L << TokenType.PROTECTED - 6 | 1L << TokenType.PUBLIC - 6 | 1L << TokenType.SHORT - 6 | 1L << TokenType.STATIC - 6 | 1L << TokenType.STRICTFP - 6 | 1L << TokenType.SYNCHRONIZED - 6 | 1L << TokenType.TRANSIENT - 6 | 1L << TokenType.VOLATILE - 6)) != 0) || ((__token - 71 & ~63) == 0 && (1L << __token - 71 & (1L << TokenType.SEMICOLON - 71 | 1L << TokenType.AT - 71 | 1L << TokenType.NODE_VARIABLE - 71 | 1L << TokenType.IDENTIFIER - 71)) != 0)) {
			do {
				pushCallStack(ANNOTATION_TYPE_BODY_2_1_2_1_1);
				member = parseAnnotationTypeBodyDecl();
				popCallStack();
				ret = append(ret, member);
				__token = getToken(0).kind;
			} while (__token != TokenType.RBRACE);
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
					nonTerminal(ret, AnnotationTypeMemberDecl, { }, { modifiers })
					nonTerminal(ret, ClassOrInterfaceDecl, { }, { modifiers })
					nonTerminal(ret, EnumDecl, { }, { modifiers })
					nonTerminal(ret, AnnotationTypeDecl, { }, { modifiers })
					nonTerminal(ret, FieldDecl, { }, { modifiers })
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
		} else if (((__token - 6 & ~63) == 0 && (1L << __token - 6 & (1L << TokenType.ABSTRACT - 6 | 1L << TokenType.BOOLEAN - 6 | 1L << TokenType.BYTE - 6 | 1L << TokenType.CHAR - 6 | 1L << TokenType.CLASS - 6 | 1L << TokenType.DEFAULT - 6 | 1L << TokenType.DOUBLE - 6 | 1L << TokenType.ENUM - 6 | 1L << TokenType.FINAL - 6 | 1L << TokenType.FLOAT - 6 | 1L << TokenType.INT - 6 | 1L << TokenType.INTERFACE - 6 | 1L << TokenType.LONG - 6 | 1L << TokenType.NATIVE - 6 | 1L << TokenType.PRIVATE - 6 | 1L << TokenType.PROTECTED - 6 | 1L << TokenType.PUBLIC - 6 | 1L << TokenType.SHORT - 6 | 1L << TokenType.STATIC - 6 | 1L << TokenType.STRICTFP - 6 | 1L << TokenType.SYNCHRONIZED - 6 | 1L << TokenType.TRANSIENT - 6 | 1L << TokenType.VOLATILE - 6)) != 0) || ((__token - 74 & ~63) == 0 && (1L << __token - 74 & (1L << TokenType.AT - 74 | 1L << TokenType.NODE_VARIABLE - 74 | 1L << TokenType.IDENTIFIER - 74)) != 0)) {
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
		nonTerminal(type, Type, { }, { null })
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
		} else if ((__token - 74 & ~63) == 0 && (1L << __token - 74 & (1L << TokenType.AT - 74 | 1L << TokenType.NODE_VARIABLE - 74 | 1L << TokenType.IDENTIFIER - 74)) != 0) {
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
		if (__token == TokenType.EXTENDS) {
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
		} else if ((__token - 74 & ~63) == 0 && (1L << __token - 74 & (1L << TokenType.AT - 74 | 1L << TokenType.NODE_VARIABLE - 74 | 1L << TokenType.IDENTIFIER - 74)) != 0) {
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
		nonTerminal(ret, ClassOrInterfaceBodyDecls, { }, { typeKind })
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
					sequence(
						nonTerminal(member, ClassOrInterfaceBodyDecl, { }, { typeKind })
						action({ ret = append(ret, member); })
					)
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
		} else if (((__token - 6 & ~63) == 0 && (1L << __token - 6 & (1L << TokenType.ABSTRACT - 6 | 1L << TokenType.BOOLEAN - 6 | 1L << TokenType.BYTE - 6 | 1L << TokenType.CHAR - 6 | 1L << TokenType.CLASS - 6 | 1L << TokenType.DEFAULT - 6 | 1L << TokenType.DOUBLE - 6 | 1L << TokenType.ENUM - 6 | 1L << TokenType.FINAL - 6 | 1L << TokenType.FLOAT - 6 | 1L << TokenType.INT - 6 | 1L << TokenType.INTERFACE - 6 | 1L << TokenType.LONG - 6 | 1L << TokenType.NATIVE - 6 | 1L << TokenType.PRIVATE - 6 | 1L << TokenType.PROTECTED - 6 | 1L << TokenType.PUBLIC - 6 | 1L << TokenType.SHORT - 6 | 1L << TokenType.STATIC - 6 | 1L << TokenType.STRICTFP - 6 | 1L << TokenType.SYNCHRONIZED - 6 | 1L << TokenType.TRANSIENT - 6 | 1L << TokenType.VOID - 6 | 1L << TokenType.VOLATILE - 6 | 1L << TokenType.LBRACE - 6)) != 0) || ((__token - 71 & ~63) == 0 && (1L << __token - 71 & (1L << TokenType.SEMICOLON - 71 | 1L << TokenType.AT - 71 | 1L << TokenType.LT - 71 | 1L << TokenType.NODE_VARIABLE - 71 | 1L << TokenType.IDENTIFIER - 71)) != 0)) {
			do {
				pushCallStack(CLASS_OR_INTERFACE_BODY_DECLS_1_1_2_1_1);
				member = parseClassOrInterfaceBodyDecl(typeKind);
				popCallStack();
				ret = append(ret, member);
				__token = getToken(0).kind;
			} while (__token != TokenType.RBRACE);
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
						nonTerminal(ret, InitializerDecl, { }, { modifiers })
						action({
							if (typeKind == TypeKind.Interface) ret = ret.withProblem(new BUProblem(Severity.ERROR, "An interface cannot have initializers"));
						})
					)
					nonTerminal(ret, ClassOrInterfaceDecl, { }, { modifiers })
					nonTerminal(ret, EnumDecl, { }, { modifiers })
					nonTerminal(ret, AnnotationTypeDecl, { }, { modifiers })
					sequence(
						nonTerminal(ret, ConstructorDecl, { }, { modifiers })
						action({
							if (typeKind == TypeKind.Interface) ret = ret.withProblem(new BUProblem(Severity.ERROR, "An interface cannot have constructors"));
						})
					)
					sequence(
						nonTerminal(ret, FieldDecl, { }, { modifiers })
					)
					nonTerminal(ret, MethodDecl, { }, { modifiers })
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
		} else if (((__token - 6 & ~63) == 0 && (1L << __token - 6 & (1L << TokenType.ABSTRACT - 6 | 1L << TokenType.BOOLEAN - 6 | 1L << TokenType.BYTE - 6 | 1L << TokenType.CHAR - 6 | 1L << TokenType.CLASS - 6 | 1L << TokenType.DEFAULT - 6 | 1L << TokenType.DOUBLE - 6 | 1L << TokenType.ENUM - 6 | 1L << TokenType.FINAL - 6 | 1L << TokenType.FLOAT - 6 | 1L << TokenType.INT - 6 | 1L << TokenType.INTERFACE - 6 | 1L << TokenType.LONG - 6 | 1L << TokenType.NATIVE - 6 | 1L << TokenType.PRIVATE - 6 | 1L << TokenType.PROTECTED - 6 | 1L << TokenType.PUBLIC - 6 | 1L << TokenType.SHORT - 6 | 1L << TokenType.STATIC - 6 | 1L << TokenType.STRICTFP - 6 | 1L << TokenType.SYNCHRONIZED - 6 | 1L << TokenType.TRANSIENT - 6 | 1L << TokenType.VOID - 6 | 1L << TokenType.VOLATILE - 6 | 1L << TokenType.LBRACE - 6)) != 0) || ((__token - 74 & ~63) == 0 && (1L << __token - 74 & (1L << TokenType.AT - 74 | 1L << TokenType.LT - 74 | 1L << TokenType.NODE_VARIABLE - 74 | 1L << TokenType.IDENTIFIER - 74)) != 0)) {
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
		nonTerminal(type, Type, { }, { null })
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
		nonTerminal(type, Type, { }, { null })
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
		while (__token == TokenType.LBRACKET || __token == TokenType.AT) {
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
		} else if (((__token - 8 & ~63) == 0 && (1L << __token - 8 & (1L << TokenType.BOOLEAN - 8 | 1L << TokenType.BYTE - 8 | 1L << TokenType.CHAR - 8 | 1L << TokenType.DOUBLE - 8 | 1L << TokenType.FALSE - 8 | 1L << TokenType.FLOAT - 8 | 1L << TokenType.INT - 8 | 1L << TokenType.LONG - 8 | 1L << TokenType.NEW - 8 | 1L << TokenType.NULL - 8 | 1L << TokenType.SHORT - 8 | 1L << TokenType.SUPER - 8 | 1L << TokenType.THIS - 8 | 1L << TokenType.TRUE - 8 | 1L << TokenType.VOID - 8 | 1L << TokenType.LONG_LITERAL - 8 | 1L << TokenType.INTEGER_LITERAL - 8 | 1L << TokenType.FLOAT_LITERAL - 8 | 1L << TokenType.DOUBLE_LITERAL - 8 | 1L << TokenType.CHARACTER_LITERAL - 8 | 1L << TokenType.STRING_LITERAL - 8 | 1L << TokenType.LPAREN - 8)) != 0) || ((__token - 76 & ~63) == 0 && (1L << __token - 76 & (1L << TokenType.LT - 76 | 1L << TokenType.BANG - 76 | 1L << TokenType.TILDE - 76 | 1L << TokenType.INCR - 76 | 1L << TokenType.DECR - 76 | 1L << TokenType.PLUS - 76 | 1L << TokenType.MINUS - 76 | 1L << TokenType.NODE_VARIABLE - 76 | 1L << TokenType.IDENTIFIER - 76)) != 0)) {
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
			sequence(
				terminal(COMMA)
				action({ trailingComma = true; })
			)
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
		if (__token != TokenType.RBRACE && __token != TokenType.COMMA) {
			pushCallStack(ARRAY_INITIALIZER_2_1_1);
			val = parseVariableInitializer();
			popCallStack();
			values = append(values, val);
			__token = getToken(0).kind;
			while (predict(ARRAY_INITIALIZER_2_1_2) == 0) {
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
		if (__token != TokenType.RPAREN) {
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
		} else if (((__token - 6 & ~63) == 0 && (1L << __token - 6 & (1L << TokenType.ABSTRACT - 6 | 1L << TokenType.BOOLEAN - 6 | 1L << TokenType.BYTE - 6 | 1L << TokenType.CHAR - 6 | 1L << TokenType.DEFAULT - 6 | 1L << TokenType.DOUBLE - 6 | 1L << TokenType.FINAL - 6 | 1L << TokenType.FLOAT - 6 | 1L << TokenType.INT - 6 | 1L << TokenType.LONG - 6 | 1L << TokenType.NATIVE - 6 | 1L << TokenType.PRIVATE - 6 | 1L << TokenType.PROTECTED - 6 | 1L << TokenType.PUBLIC - 6 | 1L << TokenType.SHORT - 6 | 1L << TokenType.STATIC - 6 | 1L << TokenType.STRICTFP - 6 | 1L << TokenType.SYNCHRONIZED - 6 | 1L << TokenType.TRANSIENT - 6 | 1L << TokenType.VOLATILE - 6)) != 0) || ((__token - 74 & ~63) == 0 && (1L << __token - 74 & (1L << TokenType.AT - 74 | 1L << TokenType.NODE_VARIABLE - 74 | 1L << TokenType.IDENTIFIER - 74)) != 0)) {
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
		nonTerminal(type, Type, { }, { null })
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
				if (__token != TokenType.THIS) {
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
		action({
			block = dress(SBlockStmt.make(stmts));
			return dress(SConstructorDecl.make(modifiers, ensureNotNull(typeParameters), name, parameters, ensureNotNull(throwsClause), block));
		})
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
				if (predict(EXPLICIT_CONSTRUCTOR_INVOCATION_1_2_1) == 0) {
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
						sequence(
							nonTerminal(stmt, ExplicitConstructorInvocation)
							action({ ret = append(ret, stmt); })
						)
					)
					zeroOrMore(
						sequence(
							nonTerminal(stmt, BlockStatement)
							action({ ret = append(ret, stmt); })
						)
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
		} else if (((__token - 0 & ~63) == 0 && (1L << __token - 0 & (1L << TokenType.EOF - 0 | 1L << TokenType.ABSTRACT - 0 | 1L << TokenType.ASSERT - 0 | 1L << TokenType.BOOLEAN - 0 | 1L << TokenType.BREAK - 0 | 1L << TokenType.BYTE - 0 | 1L << TokenType.CASE - 0 | 1L << TokenType.CHAR - 0 | 1L << TokenType.CLASS - 0 | 1L << TokenType.CONTINUE - 0 | 1L << TokenType.DEFAULT - 0 | 1L << TokenType.DO - 0 | 1L << TokenType.DOUBLE - 0 | 1L << TokenType.FALSE - 0 | 1L << TokenType.FINAL - 0 | 1L << TokenType.FLOAT - 0 | 1L << TokenType.FOR - 0 | 1L << TokenType.IF - 0 | 1L << TokenType.INT - 0 | 1L << TokenType.INTERFACE - 0 | 1L << TokenType.LONG - 0 | 1L << TokenType.NATIVE - 0 | 1L << TokenType.NEW - 0 | 1L << TokenType.NULL - 0 | 1L << TokenType.PRIVATE - 0 | 1L << TokenType.PROTECTED - 0 | 1L << TokenType.PUBLIC - 0 | 1L << TokenType.RETURN - 0 | 1L << TokenType.SHORT - 0 | 1L << TokenType.STATIC - 0 | 1L << TokenType.STRICTFP - 0 | 1L << TokenType.SUPER - 0 | 1L << TokenType.SWITCH - 0 | 1L << TokenType.SYNCHRONIZED - 0 | 1L << TokenType.THIS - 0 | 1L << TokenType.THROW - 0 | 1L << TokenType.TRANSIENT - 0 | 1L << TokenType.TRUE - 0 | 1L << TokenType.TRY - 0 | 1L << TokenType.VOID - 0 | 1L << TokenType.VOLATILE - 0 | 1L << TokenType.WHILE - 0 | 1L << TokenType.LONG_LITERAL - 0 | 1L << TokenType.INTEGER_LITERAL - 0 | 1L << TokenType.FLOAT_LITERAL - 0 | 1L << TokenType.DOUBLE_LITERAL - 0 | 1L << TokenType.CHARACTER_LITERAL - 0)) != 0) || ((__token - 64 & ~63) == 0 && (1L << __token - 64 & (1L << TokenType.STRING_LITERAL - 64 | 1L << TokenType.LPAREN - 64 | 1L << TokenType.LBRACE - 64 | 1L << TokenType.RBRACE - 64 | 1L << TokenType.SEMICOLON - 64 | 1L << TokenType.AT - 64 | 1L << TokenType.LT - 64 | 1L << TokenType.BANG - 64 | 1L << TokenType.TILDE - 64 | 1L << TokenType.INCR - 64 | 1L << TokenType.DECR - 64 | 1L << TokenType.PLUS - 64 | 1L << TokenType.MINUS - 64 | 1L << TokenType.NODE_VARIABLE - 64 | 1L << TokenType.IDENTIFIER - 64)) != 0)) {
			__token = getToken(0).kind;
			if (predict(STATEMENTS_1_1_2_1) == 0) {
				pushCallStack(STATEMENTS_1_1_2_1_1_1);
				stmt = parseExplicitConstructorInvocation();
				popCallStack();
				ret = append(ret, stmt);
			}
			__token = getToken(0).kind;
			while (((__token - 0 & ~63) != 0 || (1L << __token - 0 & (1L << TokenType.EOF - 0 | 1L << TokenType.CASE - 0 | 1L << TokenType.DEFAULT - 0)) == 0) && ((__token - 68 & ~63) != 0 || (1L << __token - 68 & (1L << TokenType.RBRACE - 68)) == 0)) {
				pushCallStack(STATEMENTS_1_1_2_2_1_1);
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
			nonTerminal(type, PrimitiveType, { }, { annotations })
			nonTerminal(type, QualifiedType, { }, { annotations })
		)
		zeroOrOne(
			sequence(
				action({ lateRun(); })
				nonTerminal(arrayDims, ArrayDimsMandatory)
				action({ type = dress(SArrayType.make(type, arrayDims)); })
			)
		)
		action({ return type; })
	) */
	protected BUTree<? extends SType> parseType(BUTree<SNodeList> annotations) throws ParseException {
		BUTree<? extends SType> type = null;
		BUTree<SNodeList> arrayDims;
		int __token;
		__token = getToken(0).kind;
		if ((__token - 8 & ~63) == 0 && (1L << __token - 8 & (1L << TokenType.BOOLEAN - 8 | 1L << TokenType.BYTE - 8 | 1L << TokenType.CHAR - 8 | 1L << TokenType.DOUBLE - 8 | 1L << TokenType.FLOAT - 8 | 1L << TokenType.INT - 8 | 1L << TokenType.LONG - 8 | 1L << TokenType.SHORT - 8)) != 0) {
			pushCallStack(TYPE_1_1);
			type = parsePrimitiveType(annotations);
			popCallStack();
		} else if (__token == TokenType.NODE_VARIABLE || __token == TokenType.IDENTIFIER) {
			pushCallStack(TYPE_1_2);
			type = parseQualifiedType(annotations);
			popCallStack();
		} else
			throw produceParseException(TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.DOUBLE, TokenType.FLOAT, TokenType.INT, TokenType.LONG, TokenType.SHORT, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER);
		__token = getToken(0).kind;
		if (predict(TYPE_2) == 0) {
			lateRun();
			pushCallStack(TYPE_2_1_1);
			arrayDims = parseArrayDimsMandatory();
			popCallStack();
			type = dress(SArrayType.make(type, arrayDims));
		}
		return type;
	}

	/* sequence(
		choice(
			sequence(
				nonTerminal(primitiveType, PrimitiveType, { }, { annotations })
				action({ lateRun(); })
				nonTerminal(arrayDims, ArrayDimsMandatory)
				action({ type = dress(SArrayType.make(primitiveType, arrayDims)); })
			)
			sequence(
				nonTerminal(type, QualifiedType, { }, { annotations })
				zeroOrOne(
					sequence(
						action({ lateRun(); })
						nonTerminal(arrayDims, ArrayDimsMandatory)
						action({ type = dress(SArrayType.make(type, arrayDims)); })
					)
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
		if ((__token - 8 & ~63) == 0 && (1L << __token - 8 & (1L << TokenType.BOOLEAN - 8 | 1L << TokenType.BYTE - 8 | 1L << TokenType.CHAR - 8 | 1L << TokenType.DOUBLE - 8 | 1L << TokenType.FLOAT - 8 | 1L << TokenType.INT - 8 | 1L << TokenType.LONG - 8 | 1L << TokenType.SHORT - 8)) != 0) {
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
				pushCallStack(REFERENCE_TYPE_1_2_2_1_1);
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
		if (__token == TokenType.LT) {
			pushCallStack(QUALIFIED_TYPE_2_1);
			typeArgs = parseTypeArgumentsOrDiamond();
			popCallStack();
		}
		ret = dress(SQualifiedType.make(annotations, scope, name, optionOf(typeArgs)));
		__token = getToken(0).kind;
		while (predict(QUALIFIED_TYPE_3) == 0) {
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
			if (__token == TokenType.LT) {
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
		if (__token != TokenType.GT) {
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
		} else if (((__token - 8 & ~63) == 0 && (1L << __token - 8 & (1L << TokenType.BOOLEAN - 8 | 1L << TokenType.BYTE - 8 | 1L << TokenType.CHAR - 8 | 1L << TokenType.DOUBLE - 8 | 1L << TokenType.FLOAT - 8 | 1L << TokenType.INT - 8 | 1L << TokenType.LONG - 8 | 1L << TokenType.SHORT - 8)) != 0) || ((__token - 74 & ~63) == 0 && (1L << __token - 74 & (1L << TokenType.AT - 74 | 1L << TokenType.HOOK - 74 | 1L << TokenType.NODE_VARIABLE - 74 | 1L << TokenType.IDENTIFIER - 74)) != 0)) {
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
			nonTerminal(ret, ReferenceType, { }, { annotations })
			nonTerminal(ret, Wildcard, { }, { annotations })
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
		if (((__token - 8 & ~63) == 0 && (1L << __token - 8 & (1L << TokenType.BOOLEAN - 8 | 1L << TokenType.BYTE - 8 | 1L << TokenType.CHAR - 8 | 1L << TokenType.DOUBLE - 8 | 1L << TokenType.FLOAT - 8 | 1L << TokenType.INT - 8 | 1L << TokenType.LONG - 8 | 1L << TokenType.SHORT - 8)) != 0) || ((__token - 115 & ~63) == 0 && (1L << __token - 115 & (1L << TokenType.NODE_VARIABLE - 115 | 1L << TokenType.IDENTIFIER - 115)) != 0)) {
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
					nonTerminal(ext, ReferenceType, { }, { boundAnnotations })
				)
				sequence(
					terminal(SUPER)
					action({ run(); })
					nonTerminal(boundAnnotations, Annotations)
					nonTerminal(sup, ReferenceType, { }, { boundAnnotations })
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
			nonTerminal(ret, Type, { }, { null })
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
		} else if (((__token - 8 & ~63) == 0 && (1L << __token - 8 & (1L << TokenType.BOOLEAN - 8 | 1L << TokenType.BYTE - 8 | 1L << TokenType.CHAR - 8 | 1L << TokenType.DOUBLE - 8 | 1L << TokenType.FLOAT - 8 | 1L << TokenType.INT - 8 | 1L << TokenType.LONG - 8 | 1L << TokenType.SHORT - 8)) != 0) || ((__token - 115 & ~63) == 0 && (1L << __token - 115 & (1L << TokenType.NODE_VARIABLE - 115 | 1L << TokenType.IDENTIFIER - 115)) != 0)) {
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
		nonTerminal(ret, QualifiedType, { }, { annotations })
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
		while (predict(QUALIFIED_NAME_2) == 0) {
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
		if (((__token - 0 & ~63) != 0 || (1L << __token - 0 & (1L << TokenType.EOF - 0)) == 0) && ((__token - 66 & ~63) != 0 || (1L << __token - 66 & (1L << TokenType.RPAREN - 66 | 1L << TokenType.RBRACE - 66 | 1L << TokenType.RBRACKET - 66 | 1L << TokenType.SEMICOLON - 66 | 1L << TokenType.COMMA - 66 | 1L << TokenType.COLON - 66)) == 0)) {
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
				nonTerminal(type, ReferenceType, { }, { annotations })
				nonTerminal(type, ReferenceCastTypeRest, { }, { type })
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
				nonTerminal(ret, LambdaBody, { }, { singletonList(makeFormalParameter(name)), false })
			)
			sequence(
				terminal(LPAREN)
				terminal(RPAREN)
				terminal(ARROW)
				nonTerminal(ret, LambdaBody, { }, { emptyList(), true })
			)
			sequence(
				terminal(LPAREN)
				nonTerminal(params, InferredFormalParameterList)
				terminal(RPAREN)
				terminal(ARROW)
				nonTerminal(ret, LambdaBody, { }, { params, true })
			)
			sequence(
				terminal(LPAREN)
				nonTerminal(params, FormalParameterList)
				terminal(RPAREN)
				terminal(ARROW)
				nonTerminal(ret, LambdaBody, { }, { params, true })
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
		if (((__token - 8 & ~63) == 0 && (1L << __token - 8 & (1L << TokenType.BOOLEAN - 8 | 1L << TokenType.BYTE - 8 | 1L << TokenType.CHAR - 8 | 1L << TokenType.DOUBLE - 8 | 1L << TokenType.FALSE - 8 | 1L << TokenType.FLOAT - 8 | 1L << TokenType.INT - 8 | 1L << TokenType.LONG - 8 | 1L << TokenType.NEW - 8 | 1L << TokenType.NULL - 8 | 1L << TokenType.SHORT - 8 | 1L << TokenType.SUPER - 8 | 1L << TokenType.THIS - 8 | 1L << TokenType.TRUE - 8 | 1L << TokenType.VOID - 8 | 1L << TokenType.LONG_LITERAL - 8 | 1L << TokenType.INTEGER_LITERAL - 8 | 1L << TokenType.FLOAT_LITERAL - 8 | 1L << TokenType.DOUBLE_LITERAL - 8 | 1L << TokenType.CHARACTER_LITERAL - 8 | 1L << TokenType.STRING_LITERAL - 8 | 1L << TokenType.LPAREN - 8)) != 0) || ((__token - 76 & ~63) == 0 && (1L << __token - 76 & (1L << TokenType.LT - 76 | 1L << TokenType.BANG - 76 | 1L << TokenType.TILDE - 76 | 1L << TokenType.INCR - 76 | 1L << TokenType.DECR - 76 | 1L << TokenType.PLUS - 76 | 1L << TokenType.MINUS - 76 | 1L << TokenType.NODE_VARIABLE - 76 | 1L << TokenType.IDENTIFIER - 76)) != 0)) {
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
				nonTerminal(type, Type, { }, { annotations })
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
		while ((__token - 76 & ~63) == 0 && (1L << __token - 76 & (1L << TokenType.LT - 76 | 1L << TokenType.LE - 76 | 1L << TokenType.GE - 76 | 1L << TokenType.GT - 76)) != 0) {
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
						action({
							popNewWhitespaces(2);
							op = BinaryOp.RightUnsignedShift;
						})
					)
					sequence(
						terminal(GT)
						terminal(GT)
						action({
							popNewWhitespaces(1);
							op = BinaryOp.RightSignedShift;
						})
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
		while (predict(SHIFT_EXPRESSION_2) == 0) {
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
		while ((__token - 91 & ~63) == 0 && (1L << __token - 91 & (1L << TokenType.STAR - 91 | 1L << TokenType.SLASH - 91 | 1L << TokenType.REM - 91)) != 0) {
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
		} else if (((__token - 8 & ~63) == 0 && (1L << __token - 8 & (1L << TokenType.BOOLEAN - 8 | 1L << TokenType.BYTE - 8 | 1L << TokenType.CHAR - 8 | 1L << TokenType.DOUBLE - 8 | 1L << TokenType.FALSE - 8 | 1L << TokenType.FLOAT - 8 | 1L << TokenType.INT - 8 | 1L << TokenType.LONG - 8 | 1L << TokenType.NEW - 8 | 1L << TokenType.NULL - 8 | 1L << TokenType.SHORT - 8 | 1L << TokenType.SUPER - 8 | 1L << TokenType.THIS - 8 | 1L << TokenType.TRUE - 8 | 1L << TokenType.VOID - 8 | 1L << TokenType.LONG_LITERAL - 8 | 1L << TokenType.INTEGER_LITERAL - 8 | 1L << TokenType.FLOAT_LITERAL - 8 | 1L << TokenType.DOUBLE_LITERAL - 8 | 1L << TokenType.CHARACTER_LITERAL - 8 | 1L << TokenType.STRING_LITERAL - 8 | 1L << TokenType.LPAREN - 8)) != 0) || ((__token - 76 & ~63) == 0 && (1L << __token - 76 & (1L << TokenType.LT - 76 | 1L << TokenType.BANG - 76 | 1L << TokenType.TILDE - 76 | 1L << TokenType.NODE_VARIABLE - 76 | 1L << TokenType.IDENTIFIER - 76)) != 0)) {
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
			sequence(
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
				nonTerminal(primitiveType, PrimitiveType, { }, { annotations })
				terminal(RPAREN)
				nonTerminal(ret, UnaryExpression)
				action({ ret = dress(SCastExpr.make(primitiveType, ret)); })
			)
			sequence(
				nonTerminal(type, ReferenceType, { }, { annotations })
				nonTerminal(type, ReferenceCastTypeRest, { }, { type })
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
			sequence(
				action({
					types = append(types, type);
					lateRun();
				})
				oneOrMore(
					sequence(
						terminal(BIT_AND)
						action({ run(); })
						nonTerminal(annotations, Annotations)
						nonTerminal(type, ReferenceType, { }, { annotations })
						action({ types = append(types, type); })
					)
				)
				action({ type = dress(SIntersectionType.make(types)); })
			)
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
				pushCallStack(REFERENCE_CAST_TYPE_REST_1_1_1_1_2);
				annotations = parseAnnotations();
				popCallStack();
				pushCallStack(REFERENCE_CAST_TYPE_REST_1_1_1_1_3);
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
			nonTerminal(ret, ArrayCreationExpr, { }, { null })
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
			sequence(
				action({ lateRun(); })
				nonTerminal(ret, PrimarySuffix, { }, { ret })
			)
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
		while ((__token - 69 & ~63) == 0 && (1L << __token - 69 & (1L << TokenType.LBRACKET - 69 | 1L << TokenType.DOT - 69 | 1L << TokenType.DOUBLECOLON - 69)) != 0) {
			lateRun();
			pushCallStack(PRIMARY_NO_NEW_ARRAY_2_1_1);
			ret = parsePrimarySuffix(ret);
			popCallStack();
			__token = getToken(0).kind;
		}
		return ret;
	}

	/* sequence(
		nonTerminal(ret, PrimaryPrefix)
		zeroOrMore(
			sequence(
				action({ lateRun(); })
				nonTerminal(ret, PrimarySuffixWithoutSuper, { }, { ret })
			)
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
		while (predict(PRIMARY_EXPRESSION_WITHOUT_SUPER_SUFFIX_2) == 0) {
			lateRun();
			pushCallStack(PRIMARY_EXPRESSION_WITHOUT_SUPER_SUFFIX_2_1_1);
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
							nonTerminal(ret, MethodInvocation, { }, { ret })
							nonTerminal(ret, FieldAccess, { }, { ret })
						)
					)
					sequence(
						action({ lateRun(); })
						nonTerminal(ret, MethodReferenceSuffix, { }, { ret })
					)
				)
			)
			nonTerminal(ret, ClassCreationExpr, { }, { null })
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
				nonTerminal(ret, MethodReferenceSuffix, { }, { ret })
			)
			sequence(
				action({ run(); })
				nonTerminal(ret, MethodInvocation, { }, { null })
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
				nonTerminal(ret, PrimarySuffixWithoutSuper, { }, { scope })
			)
			sequence(
				terminal(DOT)
				terminal(SUPER)
				action({ ret = dress(SSuperExpr.make(optionOf(scope))); })
			)
			nonTerminal(ret, MethodReferenceSuffix, { }, { scope })
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
					nonTerminal(ret, ClassCreationExpr, { }, { scope })
					nonTerminal(ret, MethodInvocation, { }, { scope })
					nonTerminal(ret, FieldAccess, { }, { scope })
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
		} else if (((__token - 8 & ~63) == 0 && (1L << __token - 8 & (1L << TokenType.BOOLEAN - 8 | 1L << TokenType.BYTE - 8 | 1L << TokenType.CHAR - 8 | 1L << TokenType.DOUBLE - 8 | 1L << TokenType.FALSE - 8 | 1L << TokenType.FLOAT - 8 | 1L << TokenType.INT - 8 | 1L << TokenType.LONG - 8 | 1L << TokenType.NEW - 8 | 1L << TokenType.NULL - 8 | 1L << TokenType.SHORT - 8 | 1L << TokenType.SUPER - 8 | 1L << TokenType.THIS - 8 | 1L << TokenType.TRUE - 8 | 1L << TokenType.VOID - 8 | 1L << TokenType.LONG_LITERAL - 8 | 1L << TokenType.INTEGER_LITERAL - 8 | 1L << TokenType.FLOAT_LITERAL - 8 | 1L << TokenType.DOUBLE_LITERAL - 8 | 1L << TokenType.CHARACTER_LITERAL - 8 | 1L << TokenType.STRING_LITERAL - 8 | 1L << TokenType.LPAREN - 8)) != 0) || ((__token - 76 & ~63) == 0 && (1L << __token - 76 & (1L << TokenType.LT - 76 | 1L << TokenType.BANG - 76 | 1L << TokenType.TILDE - 76 | 1L << TokenType.INCR - 76 | 1L << TokenType.DECR - 76 | 1L << TokenType.PLUS - 76 | 1L << TokenType.MINUS - 76 | 1L << TokenType.NODE_VARIABLE - 76 | 1L << TokenType.IDENTIFIER - 76)) != 0)) {
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
		action({
			ret = dress(SMethodReferenceExpr.make(scope, ensureNotNull(typeArgs), name));
			return ret;
		})
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
		nonTerminal(type, QualifiedType, { }, { annotations })
		nonTerminal(args, Arguments)
		zeroOrOne(
			nonTerminal(anonymousBody, ClassOrInterfaceBody, { }, { TypeKind.Class })
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
			nonTerminal(type, PrimitiveType, { }, { annotations })
			nonTerminal(type, QualifiedType, { }, { annotations })
		)
		nonTerminal(ret, ArrayCreationExprRest, { }, { type })
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
		if ((__token - 8 & ~63) == 0 && (1L << __token - 8 & (1L << TokenType.BOOLEAN - 8 | 1L << TokenType.BYTE - 8 | 1L << TokenType.CHAR - 8 | 1L << TokenType.DOUBLE - 8 | 1L << TokenType.FLOAT - 8 | 1L << TokenType.INT - 8 | 1L << TokenType.LONG - 8 | 1L << TokenType.SHORT - 8)) != 0) {
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
		} while (predict(ARRAY_DIM_EXPRS_MANDATORY_1) == 0);
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
		} while (predict(ARRAY_DIMS_MANDATORY_1) == 0);
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
				action({
					run();
					run();
				})
				nonTerminal(modifiers, ModifiersNoDefault)
				nonTerminal(typeDecl, ClassOrInterfaceDecl, { }, { modifiers })
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
		action({
			run();
			run();
		})
		nonTerminal(modifiers, ModifiersNoDefault)
		nonTerminal(variableDecl, VariableDecl, { }, { modifiers })
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
			sequence(
				nonTerminal(entry, SwitchEntry)
				action({ entries = append(entries, entry); })
			)
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
		while (__token != TokenType.RBRACE) {
			pushCallStack(SWITCH_STATEMENT_6_1_1);
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
				if (__token != TokenType.SEMICOLON) {
					pushCallStack(FOR_STATEMENT_3_2_1_1);
					init = parseForInit();
					popCallStack();
				}
				consume(TokenType.SEMICOLON);
				__token = getToken(0).kind;
				if (__token != TokenType.SEMICOLON) {
					pushCallStack(FOR_STATEMENT_3_2_3_1);
					expr = parseExpression();
					popCallStack();
				}
				consume(TokenType.SEMICOLON);
				__token = getToken(0).kind;
				if (__token != TokenType.RPAREN) {
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
		if (__token != TokenType.SEMICOLON) {
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
		if (__token != TokenType.SEMICOLON) {
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
		if (__token != TokenType.SEMICOLON) {
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
				nonTerminal(resources, ResourceSpecification, { }, { trailingSemiColon })
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
				if (__token == TokenType.FINALLY) {
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
			sequence(
				nonTerminal(catchClause, CatchClause)
				action({ catchClauses = append(catchClauses, catchClause); })
			)
		)
		action({ return catchClauses; })
	) */
	protected BUTree<SNodeList> parseCatchClauses() throws ParseException {
		BUTree<SNodeList> catchClauses = emptyList();
		BUTree<SCatchClause> catchClause;
		int __token;
		do {
			pushCallStack(CATCH_CLAUSES_1_1_1);
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
		nonTerminal(exceptType, QualifiedType, { }, { null })
		action({ exceptTypes = append(exceptTypes, exceptType); })
		zeroOrOne(
			sequence(
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
				pushCallStack(CATCH_FORMAL_PARAMETER_3_1_1_1_2);
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
			sequence(
				terminal(SEMICOLON)
				action({ trailingSemiColon.value = true; })
			)
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
		while (predict(RESOURCE_SPECIFICATION_3) == 0) {
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
			sequence(
				nonTerminal(annotation, Annotation)
				action({ annotations = append(annotations, annotation); })
			)
		)
		action({ return annotations; })
	) */
	protected BUTree<SNodeList> parseAnnotations() throws ParseException {
		BUTree<SNodeList> annotations = emptyList();
		BUTree<? extends SAnnotationExpr> annotation;
		int __token;
		__token = getToken(0).kind;
		while (__token == TokenType.AT) {
			pushCallStack(ANNOTATIONS_1_1_1);
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
		if (__token != TokenType.RPAREN) {
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
		if (((__token - 8 & ~63) == 0 && (1L << __token - 8 & (1L << TokenType.BOOLEAN - 8 | 1L << TokenType.BYTE - 8 | 1L << TokenType.CHAR - 8 | 1L << TokenType.DOUBLE - 8 | 1L << TokenType.FALSE - 8 | 1L << TokenType.FLOAT - 8 | 1L << TokenType.INT - 8 | 1L << TokenType.LONG - 8 | 1L << TokenType.NEW - 8 | 1L << TokenType.NULL - 8 | 1L << TokenType.SHORT - 8 | 1L << TokenType.SUPER - 8 | 1L << TokenType.THIS - 8 | 1L << TokenType.TRUE - 8 | 1L << TokenType.VOID - 8 | 1L << TokenType.LONG_LITERAL - 8 | 1L << TokenType.INTEGER_LITERAL - 8 | 1L << TokenType.FLOAT_LITERAL - 8 | 1L << TokenType.DOUBLE_LITERAL - 8 | 1L << TokenType.CHARACTER_LITERAL - 8 | 1L << TokenType.STRING_LITERAL - 8 | 1L << TokenType.LPAREN - 8)) != 0) || ((__token - 76 & ~63) == 0 && (1L << __token - 76 & (1L << TokenType.LT - 76 | 1L << TokenType.BANG - 76 | 1L << TokenType.TILDE - 76 | 1L << TokenType.INCR - 76 | 1L << TokenType.DECR - 76 | 1L << TokenType.PLUS - 76 | 1L << TokenType.MINUS - 76 | 1L << TokenType.NODE_VARIABLE - 76 | 1L << TokenType.IDENTIFIER - 76)) != 0)) {
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
			sequence(
				terminal(COMMA)
				action({ trailingComma = true; })
			)
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
		if (__token != TokenType.RBRACE && __token != TokenType.COMMA) {
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
		while (predict(ELEMENT_VALUE_LIST_2) == 0) {
			consume(TokenType.COMMA);
			pushCallStack(ELEMENT_VALUE_LIST_2_1_2);
			value = parseElementValue();
			popCallStack();
			ret = append(ret, value);
			__token = getToken(0).kind;
		}
		return ret;
	}

	static final String serializedGrammar = "" + 
	"\uA199\uCEB7\uF6DB\uD9AA\u03F5\234\46\25\0\uFFFF\0\27\1\uFFFF\uFFFF\1\0\0\uFFFF\uFFFF\uFFFF\uFFFF" + 
	"\2\uFFFF\0\30\4\uFFFF\uFFFF\3\1\0\uFFFF\uFFFF\uFFFF\uFFFF\4\uFFFF\0\24\3\uFFFF\uFFFF\5\uFFFF\0\32" + 
	"\7\uFFFF\uFFFF\6\2\0\uFFFF\uFFFF\uFFFF\uFFFF\7\uFFFF\0\24\6\uFFFF\uFFFF\10\uFFFF\0\36\12\uFFFF\uFFFF" + 
	"\11\3\0\uFFFF\uFFFF\uFFFF\uFFFF\12\uFFFF\0\24\11\uFFFF\uFFFF\13\uFFFF\0\55\15\uFFFF\uFFFF\14\4\0" + 
	"\uFFFF\uFFFF\uFFFF\uFFFF\15\uFFFF\0\24\14\uFFFF\uFFFF\16\uFFFF\0\46\20\uFFFF\uFFFF\17\5\0\uFFFF" + 
	"\uFFFF\uFFFF\uFFFF\20\uFFFF\0\24\17\uFFFF\uFFFF\21\uFFFF\0\34\23\uFFFF\uFFFF\22\6\0\uFFFF\uFFFF" + 
	"\uFFFF\uFFFF\23\uFFFF\0\24\22\uFFFF\uFFFF\24\uFFFF\0\222\26\uFFFF\uFFFF\25\7\0\uFFFF\uFFFF\uFFFF" + 
	"\uFFFF\26\uFFFF\0\24\25\uFFFF\uFFFF\27\uFFFF\0\34\31\uFFFF\uFFFF\30\10\0\uFFFF\uFFFF\uFFFF\uFFFF" + 
	"\31\uFFFF\0\66\32\uFFFF\uFFFF\32\uFFFF\0\24\30\uFFFF\uFFFF\33\uFFFF\0\34\35\uFFFF\uFFFF\34\11\0" + 
	"\uFFFF\uFFFF\uFFFF\uFFFF\35\uFFFF\0\56\36\uFFFF\uFFFF\36\uFFFF\0\24\34\uFFFF\uFFFF\37\uFFFF\0\34" + 
	"\41\uFFFF\uFFFF\40\12\0\uFFFF\uFFFF\uFFFF\uFFFF\41\uFFFF\0\47\42\uFFFF\uFFFF\42\uFFFF\0\24\40\uFFFF" + 
	"\uFFFF\43\uFFFF\0\43\45\uFFFF\uFFFF\44\13\0\uFFFF\uFFFF\uFFFF\uFFFF\45\uFFFF\0\24\44\uFFFF\uFFFF" + 
	"\46\uFFFF\0\71\50\uFFFF\uFFFF\47\14\0\uFFFF\uFFFF\uFFFF\uFFFF\50\uFFFF\0\24\47\uFFFF\uFFFF\51\uFFFF" + 
	"\0\51\53\uFFFF\uFFFF\52\15\0\uFFFF\uFFFF\uFFFF\uFFFF\53\uFFFF\0\24\52\uFFFF\uFFFF\54\uFFFF\0\75" + 
	"\56\uFFFF\uFFFF\55\16\0\uFFFF\uFFFF\uFFFF\uFFFF\56\uFFFF\0\24\55\uFFFF\uFFFF\57\uFFFF\0\172\61\uFFFF" + 
	"\uFFFF\60\17\0\uFFFF\uFFFF\uFFFF\uFFFF\61\uFFFF\0\24\60\uFFFF\uFFFF\62\uFFFF\0\114\64\uFFFF\uFFFF" + 
	"\63\20\0\uFFFF\uFFFF\uFFFF\uFFFF\64\uFFFF\0\24\63\uFFFF\uFFFF\65\uFFFF\0\222\67\uFFFF\uFFFF\66\21" + 
	"\0\uFFFF\uFFFF\uFFFF\uFFFF\67\uFFFF\0\77\70\uFFFF\uFFFF\70\uFFFF\0\24\66\uFFFF\uFFFF\71\uFFFF\0" + 
	"\112\73\uFFFF\uFFFF\72\22\0\uFFFF\uFFFF\uFFFF\uFFFF\73\uFFFF\0\24\72\uFFFF\uFFFF\74\uFFFF\0\113" + 
	"\76\uFFFF\uFFFF\75\23\0\uFFFF\uFFFF\uFFFF\uFFFF\76\uFFFF\0\24\75\uFFFF\uFFFF\77\uFFFF\0\uFFFF\uFFFF" + 
	"\0\100\100\24\0\uFFFF\uFFFF\uFFFF\uFFFF\101\uFFFF\0\uFFFF\uFFFF\164\102\102\25\0\uFFFF\uFFFF\uFFFF" + 
	"\uFFFF\103\uFFFF\0\uFFFF\uFFFF\163\104\104\26\0\uFFFF\uFFFF\uFFFF\uFFFF\105\uFFFF\2\110\107\uFFFF" + 
	"\uFFFF\uFFFF\uFFFF\106\27\0\uFFFF\uFFFF\uFFFF\uFFFF\107\uFFFF\0\31\111\uFFFF\uFFFF\110\uFFFF\0\30" + 
	"\107\uFFFF\uFFFF\111\uFFFF\0\33\112\uFFFF\uFFFF\112\uFFFF\0\24\106\uFFFF\uFFFF\113\uFFFF\0\222\115" + 
	"\uFFFF\uFFFF\114\30\0\uFFFF\uFFFF\uFFFF\uFFFF\115\uFFFF\0\uFFFF\uFFFF\47\116\116\uFFFF\0\112\117" + 
	"\uFFFF\uFFFF\117\uFFFF\0\uFFFF\uFFFF\107\114\120\uFFFF\2\122\121\uFFFF\uFFFF\uFFFF\uFFFF\121\31" + 
	"\0\uFFFF\uFFFF\uFFFF\uFFFF\122\uFFFF\0\32\120\uFFFF\uFFFF\123\uFFFF\0\uFFFF\uFFFF\37\125\124\32" + 
	"\0\uFFFF\uFFFF\uFFFF\uFFFF\125\uFFFF\2\127\126\uFFFF\uFFFF\uFFFF\uFFFF\126\uFFFF\0\112\130\uFFFF" + 
	"\uFFFF\127\uFFFF\0\uFFFF\uFFFF\55\126\130\uFFFF\2\132\131\uFFFF\uFFFF\uFFFF\uFFFF\131\uFFFF\0\uFFFF" + 
	"\uFFFF\107\124\132\uFFFF\0\uFFFF\uFFFF\111\133\133\uFFFF\0\uFFFF\uFFFF\133\131\134\uFFFF\2\136\135" + 
	"\uFFFF\uFFFF\uFFFF\uFFFF\135\33\0\uFFFF\uFFFF\uFFFF\uFFFF\136\uFFFF\0\36\134\uFFFF\uFFFF\137\uFFFF" + 
	"\2\141\140\uFFFF\uFFFF\uFFFF\uFFFF\140\34\0\uFFFF\uFFFF\uFFFF\uFFFF\141\uFFFF\16\uFFFF\142\143\144" + 
	"\145\146\147\150\151\152\153\154\155\156\uFFFF\uFFFF\uFFFF\uFFFF\142\uFFFF\0\uFFFF\uFFFF\52\137" + 
	"\143\uFFFF\0\uFFFF\uFFFF\51\137\144\uFFFF\0\uFFFF\uFFFF\50\137\145\uFFFF\0\uFFFF\uFFFF\6\137\146" + 
	"\uFFFF\0\uFFFF\uFFFF\21\137\147\uFFFF\0\uFFFF\uFFFF\55\137\150\uFFFF\0\uFFFF\uFFFF\30\137\151\uFFFF" + 
	"\0\uFFFF\uFFFF\65\137\152\uFFFF\0\uFFFF\uFFFF\71\137\153\uFFFF\0\uFFFF\uFFFF\61\137\154\uFFFF\0" + 
	"\uFFFF\uFFFF\44\137\155\uFFFF\0\uFFFF\uFFFF\56\137\156\uFFFF\0\223\137\uFFFF\uFFFF\157\uFFFF\2\161" + 
	"\160\uFFFF\uFFFF\uFFFF\uFFFF\160\35\0\uFFFF\uFFFF\uFFFF\uFFFF\161\uFFFF\15\uFFFF\162\163\164\165" + 
	"\166\167\170\171\172\173\174\175\uFFFF\uFFFF\uFFFF\uFFFF\162\uFFFF\0\uFFFF\uFFFF\52\157\163\uFFFF" + 
	"\0\uFFFF\uFFFF\51\157\164\uFFFF\0\uFFFF\uFFFF\50\157\165\uFFFF\0\uFFFF\uFFFF\6\157\166\uFFFF\0\uFFFF" + 
	"\uFFFF\55\157\167\uFFFF\0\uFFFF\uFFFF\30\157\170\uFFFF\0\uFFFF\uFFFF\65\157\171\uFFFF\0\uFFFF\uFFFF" + 
	"\71\157\172\uFFFF\0\uFFFF\uFFFF\61\157\173\uFFFF\0\uFFFF\uFFFF\44\157\174\uFFFF\0\uFFFF\uFFFF\56" + 
	"\157\175\uFFFF\0\223\157\uFFFF\uFFFF\176\uFFFF\3\uFFFF\200\201\uFFFF\uFFFF\uFFFF\uFFFF\177\36\0" + 
	"\uFFFF\uFFFF\uFFFF\uFFFF\200\uFFFF\0\uFFFF\uFFFF\107\177\201\uFFFF\0\34\202\uFFFF\uFFFF\202\uFFFF" + 
	"\4\uFFFF\203\204\205\uFFFF\uFFFF\uFFFF\uFFFF\203\uFFFF\0\37\177\uFFFF\uFFFF\204\uFFFF\0\42\177\uFFFF" + 
	"\uFFFF\205\uFFFF\0\44\177\uFFFF\uFFFF\206\uFFFF\3\uFFFF\211\222\uFFFF\uFFFF\uFFFF\uFFFF\207\37\0" + 
	"\uFFFF\uFFFF\uFFFF\uFFFF\210\uFFFF\0\53\207\uFFFF\uFFFF\211\uFFFF\0\uFFFF\uFFFF\16\212\212\uFFFF" + 
	"\0\113\213\uFFFF\uFFFF\213\uFFFF\2\215\214\uFFFF\uFFFF\uFFFF\uFFFF\214\uFFFF\2\217\216\uFFFF\uFFFF" + 
	"\uFFFF\uFFFF\215\uFFFF\0\50\214\uFFFF\uFFFF\216\uFFFF\2\221\210\uFFFF\uFFFF\uFFFF\uFFFF\217\uFFFF" + 
	"\0\uFFFF\uFFFF\26\220\220\uFFFF\0\111\216\uFFFF\uFFFF\221\uFFFF\0\41\210\uFFFF\uFFFF\222\uFFFF\0" + 
	"\uFFFF\uFFFF\42\223\223\uFFFF\0\113\224\uFFFF\uFFFF\224\uFFFF\2\226\225\uFFFF\uFFFF\uFFFF\uFFFF" + 
	"\225\uFFFF\2\227\210\uFFFF\uFFFF\uFFFF\uFFFF\226\uFFFF\0\50\225\uFFFF\uFFFF\227\uFFFF\0\40\210\uFFFF" + 
	"\uFFFF\230\uFFFF\0\uFFFF\uFFFF\26\232\231\40\0\uFFFF\uFFFF\uFFFF\uFFFF\232\uFFFF\3\uFFFF\233\234" + 
	"\uFFFF\uFFFF\uFFFF\uFFFF\233\uFFFF\0\25\231\uFFFF\uFFFF\234\uFFFF\0\111\235\uFFFF\uFFFF\235\uFFFF" + 
	"\2\236\231\uFFFF\uFFFF\uFFFF\uFFFF\236\uFFFF\0\uFFFF\uFFFF\110\237\237\uFFFF\0\111\235\uFFFF\uFFFF" + 
	"\240\uFFFF\0\uFFFF\uFFFF\36\242\241\41\0\uFFFF\uFFFF\uFFFF\uFFFF\242\uFFFF\3\uFFFF\243\244\uFFFF" + 
	"\uFFFF\uFFFF\uFFFF\243\uFFFF\0\25\241\uFFFF\uFFFF\244\uFFFF\0\111\245\uFFFF\uFFFF\245\uFFFF\2\246" + 
	"\241\uFFFF\uFFFF\uFFFF\uFFFF\246\uFFFF\0\uFFFF\uFFFF\110\247\247\uFFFF\0\111\245\uFFFF\uFFFF\250" + 
	"\uFFFF\0\uFFFF\uFFFF\25\252\251\42\0\uFFFF\uFFFF\uFFFF\uFFFF\252\uFFFF\0\113\253\uFFFF\uFFFF\253" + 
	"\uFFFF\2\255\254\uFFFF\uFFFF\uFFFF\uFFFF\254\uFFFF\0\uFFFF\uFFFF\103\256\255\uFFFF\0\41\254\uFFFF" + 
	"\uFFFF\256\uFFFF\2\260\257\uFFFF\uFFFF\uFFFF\uFFFF\257\uFFFF\2\267\266\uFFFF\uFFFF\uFFFF\uFFFF\260" + 
	"\uFFFF\3\uFFFF\261\262\uFFFF\uFFFF\uFFFF\uFFFF\261\uFFFF\0\25\257\uFFFF\uFFFF\262\uFFFF\0\43\263" + 
	"\uFFFF\uFFFF\263\uFFFF\2\264\257\uFFFF\uFFFF\uFFFF\uFFFF\264\uFFFF\0\uFFFF\uFFFF\110\265\265\uFFFF" + 
	"\0\43\263\uFFFF\uFFFF\266\uFFFF\2\271\270\uFFFF\uFFFF\uFFFF\uFFFF\267\uFFFF\0\uFFFF\uFFFF\110\266" + 
	"\270\uFFFF\0\uFFFF\uFFFF\104\251\271\uFFFF\0\uFFFF\uFFFF\107\272\272\uFFFF\0\54\270\uFFFF\uFFFF" + 
	"\273\uFFFF\0\34\275\uFFFF\uFFFF\274\43\0\uFFFF\uFFFF\uFFFF\uFFFF\275\uFFFF\0\113\276\uFFFF\uFFFF" + 
	"\276\uFFFF\2\300\277\uFFFF\uFFFF\uFFFF\uFFFF\277\uFFFF\2\301\274\uFFFF\uFFFF\uFFFF\uFFFF\300\uFFFF" + 
	"\0\157\277\uFFFF\uFFFF\301\uFFFF\0\53\274\uFFFF\uFFFF\302\uFFFF\0\uFFFF\uFFFF\112\304\303\44\0\uFFFF" + 
	"\uFFFF\uFFFF\uFFFF\304\uFFFF\0\uFFFF\uFFFF\42\305\305\uFFFF\0\113\306\uFFFF\uFFFF\306\uFFFF\0\45" + 
	"\303\uFFFF\uFFFF\307\uFFFF\0\uFFFF\uFFFF\103\311\310\45\0\uFFFF\uFFFF\uFFFF\uFFFF\311\uFFFF\2\313" + 
	"\312\uFFFF\uFFFF\uFFFF\uFFFF\312\uFFFF\0\uFFFF\uFFFF\104\310\313\uFFFF\3\uFFFF\314\315\uFFFF\uFFFF" + 
	"\uFFFF\uFFFF\314\uFFFF\0\25\312\uFFFF\uFFFF\315\uFFFF\0\46\316\uFFFF\uFFFF\316\uFFFF\2\315\312\uFFFF" + 
	"\uFFFF\uFFFF\uFFFF\317\uFFFF\3\uFFFF\321\322\uFFFF\uFFFF\uFFFF\uFFFF\320\46\0\uFFFF\uFFFF\uFFFF" + 
	"\uFFFF\321\uFFFF\0\uFFFF\uFFFF\107\320\322\uFFFF\0\34\323\uFFFF\uFFFF\323\uFFFF\6\uFFFF\324\325" + 
	"\326\327\330\uFFFF\uFFFF\uFFFF\uFFFF\324\uFFFF\0\47\320\uFFFF\uFFFF\325\uFFFF\0\37\320\uFFFF\uFFFF" + 
	"\326\uFFFF\0\42\320\uFFFF\uFFFF\327\uFFFF\0\44\320\uFFFF\uFFFF\330\uFFFF\0\56\320\uFFFF\uFFFF\331" + 
	"\uFFFF\0\77\333\uFFFF\uFFFF\332\47\0\uFFFF\uFFFF\uFFFF\uFFFF\333\uFFFF\0\113\334\uFFFF\uFFFF\334" + 
	"\uFFFF\0\uFFFF\uFFFF\101\335\335\uFFFF\0\uFFFF\uFFFF\102\336\336\uFFFF\0\63\337\uFFFF\uFFFF\337" + 
	"\uFFFF\2\341\340\uFFFF\uFFFF\uFFFF\uFFFF\340\uFFFF\0\uFFFF\uFFFF\107\332\341\uFFFF\0\uFFFF\uFFFF" + 
	"\21\342\342\uFFFF\0\231\340\uFFFF\uFFFF\343\uFFFF\0\uFFFF\uFFFF\114\345\344\50\0\uFFFF\uFFFF\uFFFF" + 
	"\uFFFF\345\uFFFF\3\uFFFF\347\350\uFFFF\uFFFF\uFFFF\uFFFF\346\uFFFF\0\uFFFF\uFFFF\162\344\347\uFFFF" + 
	"\0\25\346\uFFFF\uFFFF\350\uFFFF\0\51\351\uFFFF\uFFFF\351\uFFFF\2\352\346\uFFFF\uFFFF\uFFFF\uFFFF" + 
	"\352\uFFFF\0\uFFFF\uFFFF\110\353\353\uFFFF\0\51\351\uFFFF\uFFFF\354\uFFFF\0\222\356\uFFFF\uFFFF" + 
	"\355\51\0\uFFFF\uFFFF\uFFFF\uFFFF\356\uFFFF\0\113\357\uFFFF\uFFFF\357\uFFFF\2\360\355\uFFFF\uFFFF" + 
	"\uFFFF\uFFFF\360\uFFFF\0\52\355\uFFFF\uFFFF\361\uFFFF\0\uFFFF\uFFFF\26\363\362\52\0\uFFFF\uFFFF" + 
	"\uFFFF\uFFFF\363\uFFFF\3\uFFFF\364\365\uFFFF\uFFFF\uFFFF\uFFFF\364\uFFFF\0\25\362\uFFFF\uFFFF\365" + 
	"\uFFFF\0\111\366\uFFFF\uFFFF\366\uFFFF\2\367\362\uFFFF\uFFFF\uFFFF\uFFFF\367\uFFFF\0\uFFFF\uFFFF" + 
	"\135\370\370\uFFFF\0\111\366\uFFFF\uFFFF\371\uFFFF\0\uFFFF\uFFFF\103\373\372\53\0\uFFFF\uFFFF\uFFFF" + 
	"\uFFFF\373\uFFFF\0\54\374\uFFFF\uFFFF\374\uFFFF\0\uFFFF\uFFFF\104\372\375\uFFFF\2\377\376\uFFFF" + 
	"\uFFFF\uFFFF\uFFFF\376\54\0\uFFFF\uFFFF\uFFFF\uFFFF\377\uFFFF\3\uFFFF\u0100\u0101\uFFFF\uFFFF\uFFFF" + 
	"\uFFFF\u0100\uFFFF\0\25\376\uFFFF\uFFFF\u0101\uFFFF\0\55\u0102\uFFFF\uFFFF\u0102\uFFFF\2\u0101\376" + 
	"\uFFFF\uFFFF\uFFFF\uFFFF\u0103\uFFFF\3\uFFFF\u0105\u0106\uFFFF\uFFFF\uFFFF\uFFFF\u0104\55\0\uFFFF" + 
	"\uFFFF\uFFFF\uFFFF\u0105\uFFFF\0\uFFFF\uFFFF\107\u0104\u0106\uFFFF\0\34\u0107\uFFFF\uFFFF\u0107" + 
	"\uFFFF\10\uFFFF\u0108\u0109\u010A\u010B\u010C\u010D\u010E\uFFFF\uFFFF\uFFFF\uFFFF\u0108\uFFFF\0" + 
	"\76\u0104\uFFFF\uFFFF\u0109\uFFFF\0\37\u0104\uFFFF\uFFFF\u010A\uFFFF\0\42\u0104\uFFFF\uFFFF\u010B" + 
	"\uFFFF\0\44\u0104\uFFFF\uFFFF\u010C\uFFFF\0\73\u0104\uFFFF\uFFFF\u010D\uFFFF\0\56\u0104\uFFFF\uFFFF" + 
	"\u010E\uFFFF\0\66\u0104\uFFFF\uFFFF\u010F\uFFFF\0\77\u0111\uFFFF\uFFFF\u0110\56\0\uFFFF\uFFFF\uFFFF" + 
	"\uFFFF\u0111\uFFFF\0\60\u0112\uFFFF\uFFFF\u0112\uFFFF\0\uFFFF\uFFFF\107\u0110\u0113\uFFFF\0\77\u0115" + 
	"\uFFFF\uFFFF\u0114\57\0\uFFFF\uFFFF\uFFFF\uFFFF\u0115\uFFFF\0\60\u0114\uFFFF\uFFFF\u0116\uFFFF\0" + 
	"\61\u0118\uFFFF\uFFFF\u0117\60\0\uFFFF\uFFFF\uFFFF\uFFFF\u0118\uFFFF\2\u0119\u0117\uFFFF\uFFFF\uFFFF" + 
	"\uFFFF\u0119\uFFFF\0\uFFFF\uFFFF\110\u011A\u011A\uFFFF\0\61\u0118\uFFFF\uFFFF\u011B\uFFFF\0\62\u011D" + 
	"\uFFFF\uFFFF\u011C\61\0\uFFFF\uFFFF\uFFFF\uFFFF\u011D\uFFFF\2\u011E\u011C\uFFFF\uFFFF\uFFFF\uFFFF" + 
	"\u011E\uFFFF\0\uFFFF\uFFFF\113\u011F\u011F\uFFFF\0\64\u011C\uFFFF\uFFFF\u0120\uFFFF\0\113\u0122" + 
	"\uFFFF\uFFFF\u0121\62\0\uFFFF\uFFFF\uFFFF\uFFFF\u0122\uFFFF\0\63\u0121\uFFFF\uFFFF\u0123\uFFFF\2" + 
	"\u0125\u0124\uFFFF\uFFFF\uFFFF\uFFFF\u0124\63\0\uFFFF\uFFFF\uFFFF\uFFFF\u0125\uFFFF\0\222\u0126" + 
	"\uFFFF\uFFFF\u0126\uFFFF\0\uFFFF\uFFFF\105\u0127\u0127\uFFFF\0\uFFFF\uFFFF\106\u0123\u0128\uFFFF" + 
	"\3\uFFFF\u012A\u012B\uFFFF\uFFFF\uFFFF\uFFFF\u0129\64\0\uFFFF\uFFFF\uFFFF\uFFFF\u012A\uFFFF\0\65" + 
	"\u0129\uFFFF\uFFFF\u012B\uFFFF\0\114\u0129\uFFFF\uFFFF\u012C\uFFFF\0\uFFFF\uFFFF\103\u012E\u012D" + 
	"\65\0\uFFFF\uFFFF\uFFFF\uFFFF\u012E\uFFFF\2\u0130\u012F\uFFFF\uFFFF\uFFFF\uFFFF\u012F\uFFFF\2\u0135" + 
	"\u0134\uFFFF\uFFFF\uFFFF\uFFFF\u0130\uFFFF\0\64\u0131\uFFFF\uFFFF\u0131\uFFFF\2\u0132\u012F\uFFFF" + 
	"\uFFFF\uFFFF\uFFFF\u0132\uFFFF\0\uFFFF\uFFFF\110\u0133\u0133\uFFFF\0\64\u0131\uFFFF\uFFFF\u0134" + 
	"\uFFFF\0\uFFFF\uFFFF\104\u012D\u0135\uFFFF\0\uFFFF\uFFFF\110\u0134\u0136\uFFFF\2\u0139\u0138\uFFFF" + 
	"\uFFFF\uFFFF\uFFFF\u0137\66\0\uFFFF\uFFFF\uFFFF\uFFFF\u0138\uFFFF\0\110\u013B\uFFFF\uFFFF\u0139" + 
	"\uFFFF\0\50\u013A\uFFFF\uFFFF\u013A\uFFFF\0\222\u0138\uFFFF\uFFFF\u013B\uFFFF\0\113\u013C\uFFFF" + 
	"\uFFFF\u013C\uFFFF\0\67\u013D\uFFFF\uFFFF\u013D\uFFFF\0\63\u013E\uFFFF\uFFFF\u013E\uFFFF\2\u0140" + 
	"\u013F\uFFFF\uFFFF\uFFFF\uFFFF\u013F\uFFFF\3\uFFFF\u0141\u0142\uFFFF\uFFFF\uFFFF\uFFFF\u0140\uFFFF" + 
	"\0\72\u013F\uFFFF\uFFFF\u0141\uFFFF\0\171\u0137\uFFFF\uFFFF\u0142\uFFFF\0\uFFFF\uFFFF\107\u0137" + 
	"\u0143\uFFFF\0\uFFFF\uFFFF\101\u0145\u0144\67\0\uFFFF\uFFFF\uFFFF\uFFFF\u0145\uFFFF\2\u0147\u0146" + 
	"\uFFFF\uFFFF\uFFFF\uFFFF\u0146\uFFFF\0\uFFFF\uFFFF\102\u0144\u0147\uFFFF\0\70\u0146\uFFFF\uFFFF" + 
	"\u0148\uFFFF\3\uFFFF\u014A\u014B\uFFFF\uFFFF\uFFFF\uFFFF\u0149\70\0\uFFFF\uFFFF\uFFFF\uFFFF\u014A" + 
	"\uFFFF\0\25\u0149\uFFFF\uFFFF\u014B\uFFFF\0\71\u014C\uFFFF\uFFFF\u014C\uFFFF\2\u014D\u0149\uFFFF" + 
	"\uFFFF\uFFFF\uFFFF\u014D\uFFFF\0\uFFFF\uFFFF\110\u014E\u014E\uFFFF\0\71\u014C\uFFFF\uFFFF\u014F" + 
	"\uFFFF\0\34\u0151\uFFFF\uFFFF\u0150\71\0\uFFFF\uFFFF\uFFFF\uFFFF\u0151\uFFFF\0\77\u0152\uFFFF\uFFFF" + 
	"\u0152\uFFFF\2\u0154\u0153\uFFFF\uFFFF\uFFFF\uFFFF\u0153\uFFFF\3\uFFFF\u0156\u015A\uFFFF\uFFFF\uFFFF" + 
	"\uFFFF\u0154\uFFFF\0\222\u0155\uFFFF\uFFFF\u0155\uFFFF\0\uFFFF\uFFFF\155\u0153\u0156\uFFFF\2\u0158" + 
	"\u0157\uFFFF\uFFFF\uFFFF\uFFFF\u0157\uFFFF\0\uFFFF\uFFFF\62\u0150\u0158\uFFFF\0\113\u0159\uFFFF" + 
	"\uFFFF\u0159\uFFFF\0\uFFFF\uFFFF\111\u0157\u015A\uFFFF\0\62\u0150\uFFFF\uFFFF\u015B\uFFFF\0\uFFFF" + 
	"\uFFFF\64\u015D\u015C\72\0\uFFFF\uFFFF\uFFFF\uFFFF\u015D\uFFFF\0\111\u015E\uFFFF\uFFFF\u015E\uFFFF" + 
	"\2\u015F\u015C\uFFFF\uFFFF\uFFFF\uFFFF\u015F\uFFFF\0\uFFFF\uFFFF\110\u0160\u0160\uFFFF\0\111\u015E" + 
	"\uFFFF\uFFFF\u0161\uFFFF\2\u0164\u0163\uFFFF\uFFFF\uFFFF\uFFFF\u0162\73\0\uFFFF\uFFFF\uFFFF\uFFFF" + 
	"\u0163\uFFFF\0\113\u0165\uFFFF\uFFFF\u0164\uFFFF\0\50\u0163\uFFFF\uFFFF\u0165\uFFFF\0\67\u0166\uFFFF" + 
	"\uFFFF\u0166\uFFFF\2\u0168\u0167\uFFFF\uFFFF\uFFFF\uFFFF\u0167\uFFFF\0\uFFFF\uFFFF\103\u0169\u0168" + 
	"\uFFFF\0\72\u0167\uFFFF\uFFFF\u0169\uFFFF\0\75\u016A\uFFFF\uFFFF\u016A\uFFFF\0\uFFFF\uFFFF\104\u0162" + 
	"\u016B\uFFFF\3\uFFFF\u016D\u0172\uFFFF\uFFFF\uFFFF\uFFFF\u016C\74\0\uFFFF\uFFFF\uFFFF\uFFFF\u016D" + 
	"\uFFFF\2\u016F\u016E\uFFFF\uFFFF\uFFFF\uFFFF\u016E\uFFFF\0\uFFFF\uFFFF\62\u0170\u016F\uFFFF\0\102" + 
	"\u016E\uFFFF\uFFFF\u0170\uFFFF\0\157\u0171\uFFFF\uFFFF\u0171\uFFFF\0\uFFFF\uFFFF\107\u016C\u0172" + 
	"\uFFFF\2\u0174\u0173\uFFFF\uFFFF\uFFFF\uFFFF\u0173\uFFFF\2\u0177\u0176\uFFFF\uFFFF\uFFFF\uFFFF\u0174" + 
	"\uFFFF\0\151\u0175\uFFFF\uFFFF\u0175\uFFFF\0\uFFFF\uFFFF\111\u0173\u0176\uFFFF\0\uFFFF\uFFFF\57" + 
	"\u0178\u0177\uFFFF\0\102\u0176\uFFFF\uFFFF\u0178\uFFFF\0\157\u0179\uFFFF\uFFFF\u0179\uFFFF\0\uFFFF" + 
	"\uFFFF\107\u016C\u017A\uFFFF\2\u017C\u017B\uFFFF\uFFFF\uFFFF\uFFFF\u017B\75\0\uFFFF\uFFFF\uFFFF" + 
	"\uFFFF\u017C\uFFFF\3\uFFFF\u017D\u017E\uFFFF\uFFFF\uFFFF\uFFFF\u017D\uFFFF\0\25\u017B\uFFFF\uFFFF" + 
	"\u017E\uFFFF\2\u0180\u017F\uFFFF\uFFFF\uFFFF\uFFFF\u017F\uFFFF\2\u0181\u017B\uFFFF\uFFFF\uFFFF\uFFFF" + 
	"\u0180\uFFFF\0\74\u017F\uFFFF\uFFFF\u0181\uFFFF\0\172\u017F\uFFFF\uFFFF\u0182\uFFFF\0\171\u0183" + 
	"\uFFFF\uFFFF\u0183\76\0\uFFFF\uFFFF\uFFFF\uFFFF\u0184\uFFFF\3\uFFFF\u0187\u0188\uFFFF\uFFFF\uFFFF" + 
	"\uFFFF\u0185\77\0\uFFFF\uFFFF\uFFFF\uFFFF\u0186\uFFFF\2\u0189\u0185\uFFFF\uFFFF\uFFFF\uFFFF\u0187" + 
	"\uFFFF\0\107\u0186\uFFFF\uFFFF\u0188\uFFFF\0\101\u0186\uFFFF\uFFFF\u0189\uFFFF\0\165\u0185\uFFFF" + 
	"\uFFFF\u018A\uFFFF\3\uFFFF\u018C\u018E\uFFFF\uFFFF\uFFFF\uFFFF\u018B\100\0\uFFFF\uFFFF\uFFFF\uFFFF" + 
	"\u018C\uFFFF\0\107\u018D\uFFFF\uFFFF\u018D\uFFFF\0\165\u018B\uFFFF\uFFFF\u018E\uFFFF\0\101\u018F" + 
	"\uFFFF\uFFFF\u018F\uFFFF\2\u0190\u018B\uFFFF\uFFFF\uFFFF\uFFFF\u0190\uFFFF\0\165\u018B\uFFFF\uFFFF" + 
	"\u0191\uFFFF\0\113\u0193\uFFFF\uFFFF\u0192\101\0\uFFFF\uFFFF\uFFFF\uFFFF\u0193\uFFFF\2\u0195\u0194" + 
	"\uFFFF\uFFFF\uFFFF\uFFFF\u0194\uFFFF\2\u0196\u0192\uFFFF\uFFFF\uFFFF\uFFFF\u0195\uFFFF\0\103\u0194" + 
	"\uFFFF\uFFFF\u0196\uFFFF\0\uFFFF\uFFFF\111\u0197\u0197\uFFFF\0\222\u0198\uFFFF\uFFFF\u0198\uFFFF" + 
	"\0\113\u0199\uFFFF\uFFFF\u0199\uFFFF\2\u019A\u0194\uFFFF\uFFFF\uFFFF\uFFFF\u019A\uFFFF\0\103\u0194" + 
	"\uFFFF\uFFFF\u019B\uFFFF\0\uFFFF\uFFFF\114\u019D\u019C\102\0\uFFFF\uFFFF\uFFFF\uFFFF\u019D\uFFFF" + 
	"\0\104\u019E\uFFFF\uFFFF\u019E\uFFFF\0\uFFFF\uFFFF\162\u019C\u019F\uFFFF\0\uFFFF\uFFFF\114\u01A1" + 
	"\u01A0\103\0\uFFFF\uFFFF\uFFFF\uFFFF\u01A1\uFFFF\2\u01A3\u01A2\uFFFF\uFFFF\uFFFF\uFFFF\u01A2\uFFFF" + 
	"\0\uFFFF\uFFFF\162\u01A0\u01A3\uFFFF\0\104\u01A2\uFFFF\uFFFF\u01A4\uFFFF\3\uFFFF\u01A6\u01A7\uFFFF" + 
	"\uFFFF\uFFFF\uFFFF\u01A5\104\0\uFFFF\uFFFF\uFFFF\uFFFF\u01A6\uFFFF\0\25\u01A5\uFFFF\uFFFF\u01A7" + 
	"\uFFFF\0\105\u01A8\uFFFF\uFFFF\u01A8\uFFFF\2\u01A9\u01A5\uFFFF\uFFFF\uFFFF\uFFFF\u01A9\uFFFF\0\uFFFF" + 
	"\uFFFF\110\u01AA\u01AA\uFFFF\0\105\u01A8\uFFFF\uFFFF\u01AB\uFFFF\0\222\u01AD\uFFFF\uFFFF\u01AC\105" + 
	"\0\uFFFF\uFFFF\uFFFF\uFFFF\u01AD\uFFFF\3\uFFFF\u01AE\u01AF\uFFFF\uFFFF\uFFFF\uFFFF\u01AE\uFFFF\0" + 
	"\100\u01AC\uFFFF\uFFFF\u01AF\uFFFF\0\106\u01AC\uFFFF\uFFFF\u01B0\uFFFF\0\uFFFF\uFFFF\117\u01B2\u01B1" + 
	"\106\0\uFFFF\uFFFF\uFFFF\uFFFF\u01B2\uFFFF\2\u01B3\u01B1\uFFFF\uFFFF\uFFFF\uFFFF\u01B3\uFFFF\3\uFFFF" + 
	"\u01B4\u01B7\uFFFF\uFFFF\uFFFF\uFFFF\u01B4\uFFFF\0\uFFFF\uFFFF\26\u01B5\u01B5\uFFFF\0\222\u01B6" + 
	"\uFFFF\uFFFF\u01B6\uFFFF\0\100\u01B1\uFFFF\uFFFF\u01B7\uFFFF\0\uFFFF\uFFFF\57\u01B8\u01B8\uFFFF" + 
	"\0\222\u01B9\uFFFF\uFFFF\u01B9\uFFFF\0\100\u01B1\uFFFF\uFFFF\u01BA\uFFFF\11\uFFFF\u01BC\u01BD\u01BE" + 
	"\u01BF\u01C0\u01C1\u01C2\u01C3\uFFFF\uFFFF\uFFFF\uFFFF\u01BB\107\0\uFFFF\uFFFF\uFFFF\uFFFF\u01BC" + 
	"\uFFFF\0\uFFFF\uFFFF\10\u01BB\u01BD\uFFFF\0\uFFFF\uFFFF\15\u01BB\u01BE\uFFFF\0\uFFFF\uFFFF\12\u01BB" + 
	"\u01BF\uFFFF\0\uFFFF\uFFFF\54\u01BB\u01C0\uFFFF\0\uFFFF\uFFFF\41\u01BB\u01C1\uFFFF\0\uFFFF\uFFFF" + 
	"\43\u01BB\u01C2\uFFFF\0\uFFFF\uFFFF\32\u01BB\u01C3\uFFFF\0\uFFFF\uFFFF\23\u01BB\u01C4\uFFFF\3\uFFFF" + 
	"\u01C6\u01C7\uFFFF\uFFFF\uFFFF\uFFFF\u01C5\110\0\uFFFF\uFFFF\uFFFF\uFFFF\u01C6\uFFFF\0\uFFFF\uFFFF" + 
	"\70\u01C5\u01C7\uFFFF\0\77\u01C5\uFFFF\uFFFF\u01C8\uFFFF\0\222\u01CA\uFFFF\uFFFF\u01C9\111\0\uFFFF" + 
	"\uFFFF\uFFFF\uFFFF\u01CA\uFFFF\0\101\u01C9\uFFFF\uFFFF\u01CB\uFFFF\0\113\u01CD\uFFFF\uFFFF\u01CC" + 
	"\112\0\uFFFF\uFFFF\uFFFF\uFFFF\u01CD\uFFFF\2\u01CE\u01CC\uFFFF\uFFFF\uFFFF\uFFFF\u01CE\uFFFF\0\uFFFF" + 
	"\uFFFF\111\u01CF\u01CF\uFFFF\0\113\u01CD\uFFFF\uFFFF\u01D0\uFFFF\3\uFFFF\u01D2\u01D3\uFFFF\uFFFF" + 
	"\uFFFF\uFFFF\u01D1\113\0\uFFFF\uFFFF\uFFFF\uFFFF\u01D2\uFFFF\0\26\u01D1\uFFFF\uFFFF\u01D3\uFFFF" + 
	"\0\uFFFF\uFFFF\165\u01D1\u01D4\uFFFF\3\uFFFF\u01D6\u01D7\uFFFF\uFFFF\uFFFF\uFFFF\u01D5\114\0\uFFFF" + 
	"\uFFFF\uFFFF\uFFFF\u01D6\uFFFF\0\115\u01D5\uFFFF\uFFFF\u01D7\uFFFF\0\116\u01D5\uFFFF\uFFFF\u01D8" + 
	"\uFFFF\0\124\u01DA\uFFFF\uFFFF\u01D9\115\0\uFFFF\uFFFF\uFFFF\uFFFF\u01DA\uFFFF\2\u01DB\u01D9\uFFFF" + 
	"\uFFFF\uFFFF\uFFFF\u01DB\uFFFF\0\123\u01DC\uFFFF\uFFFF\u01DC\uFFFF\0\114\u01D9\uFFFF\uFFFF\u01DD" + 
	"\uFFFF\3\uFFFF\u01DF\u01E5\uFFFF\uFFFF\uFFFF\uFFFF\u01DE\116\0\uFFFF\uFFFF\uFFFF\uFFFF\u01DF\uFFFF" + 
	"\0\uFFFF\uFFFF\101\u01E0\u01E0\uFFFF\0\222\u01E1\uFFFF\uFFFF\u01E1\uFFFF\0\100\u01E2\uFFFF\uFFFF" + 
	"\u01E2\uFFFF\0\145\u01E3\uFFFF\uFFFF\u01E3\uFFFF\0\uFFFF\uFFFF\102\u01E4\u01E4\uFFFF\0\116\u01DE" + 
	"\uFFFF\uFFFF\u01E5\uFFFF\0\117\u01DE\uFFFF\uFFFF\u01E6\uFFFF\5\uFFFF\u01E8\u01EB\u01EF\u01F4\uFFFF" + 
	"\uFFFF\uFFFF\uFFFF\u01E7\117\0\uFFFF\uFFFF\uFFFF\uFFFF\u01E8\uFFFF\0\113\u01E9\uFFFF\uFFFF\u01E9" + 
	"\uFFFF\0\uFFFF\uFFFF\156\u01EA\u01EA\uFFFF\0\120\u01E7\uFFFF\uFFFF\u01EB\uFFFF\0\uFFFF\uFFFF\101" + 
	"\u01EC\u01EC\uFFFF\0\uFFFF\uFFFF\102\u01ED\u01ED\uFFFF\0\uFFFF\uFFFF\156\u01EE\u01EE\uFFFF\0\120" + 
	"\u01E7\uFFFF\uFFFF\u01EF\uFFFF\0\uFFFF\uFFFF\101\u01F0\u01F0\uFFFF\0\121\u01F1\uFFFF\uFFFF\u01F1" + 
	"\uFFFF\0\uFFFF\uFFFF\102\u01F2\u01F2\uFFFF\0\uFFFF\uFFFF\156\u01F3\u01F3\uFFFF\0\120\u01E7\uFFFF" + 
	"\uFFFF\u01F4\uFFFF\0\uFFFF\uFFFF\101\u01F5\u01F5\uFFFF\0\70\u01F6\uFFFF\uFFFF\u01F6\uFFFF\0\uFFFF" + 
	"\uFFFF\102\u01F7\u01F7\uFFFF\0\uFFFF\uFFFF\156\u01F8\u01F8\uFFFF\0\120\u01E7\uFFFF\uFFFF\u01F9\uFFFF" + 
	"\3\uFFFF\u01FB\u01FC\uFFFF\uFFFF\uFFFF\uFFFF\u01FA\120\0\uFFFF\uFFFF\uFFFF\uFFFF\u01FB\uFFFF\0\114" + 
	"\u01FA\uFFFF\uFFFF\u01FC\uFFFF\0\171\u01FA\uFFFF\uFFFF\u01FD\uFFFF\0\122\u01FF\uFFFF\uFFFF\u01FE" + 
	"\121\0\uFFFF\uFFFF\uFFFF\uFFFF\u01FF\uFFFF\2\u0200\u01FE\uFFFF\uFFFF\uFFFF\uFFFF\u0200\uFFFF\0\uFFFF" + 
	"\uFFFF\110\u0201\u0201\uFFFF\0\122\u01FF\uFFFF\uFFFF\u0202\uFFFF\0\113\u0203\uFFFF\uFFFF\u0203\122" + 
	"\0\uFFFF\uFFFF\uFFFF\uFFFF\u0204\uFFFF\15\uFFFF\u0206\u0207\u0208\u0209\u020A\u020B\u020C\u020D" + 
	"\u020E\u020F\u0210\u0211\uFFFF\uFFFF\uFFFF\uFFFF\u0205\123\0\uFFFF\uFFFF\uFFFF\uFFFF\u0206\uFFFF" + 
	"\0\uFFFF\uFFFF\113\u0205\u0207\uFFFF\0\uFFFF\uFFFF\144\u0205\u0208\uFFFF\0\uFFFF\uFFFF\145\u0205" + 
	"\u0209\uFFFF\0\uFFFF\uFFFF\151\u0205\u020A\uFFFF\0\uFFFF\uFFFF\142\u0205\u020B\uFFFF\0\uFFFF\uFFFF" + 
	"\143\u0205\u020C\uFFFF\0\uFFFF\uFFFF\152\u0205\u020D\uFFFF\0\uFFFF\uFFFF\153\u0205\u020E\uFFFF\0" + 
	"\uFFFF\uFFFF\154\u0205\u020F\uFFFF\0\uFFFF\uFFFF\146\u0205\u0210\uFFFF\0\uFFFF\uFFFF\150\u0205\u0211" + 
	"\uFFFF\0\uFFFF\uFFFF\147\u0205\u0212\uFFFF\0\125\u0214\uFFFF\uFFFF\u0213\124\0\uFFFF\uFFFF\uFFFF" + 
	"\uFFFF\u0214\uFFFF\2\u0215\u0213\uFFFF\uFFFF\uFFFF\uFFFF\u0215\uFFFF\0\uFFFF\uFFFF\117\u0216\u0216" + 
	"\uFFFF\0\114\u0217\uFFFF\uFFFF\u0217\uFFFF\0\uFFFF\uFFFF\120\u0218\u0218\uFFFF\3\uFFFF\u0219\u021A" + 
	"\uFFFF\uFFFF\uFFFF\uFFFF\u0219\uFFFF\0\124\u0213\uFFFF\uFFFF\u021A\uFFFF\0\116\u0213\uFFFF\uFFFF" + 
	"\u021B\uFFFF\0\126\u021D\uFFFF\uFFFF\u021C\125\0\uFFFF\uFFFF\uFFFF\uFFFF\u021D\uFFFF\2\u021E\u021C" + 
	"\uFFFF\uFFFF\uFFFF\uFFFF\u021E\uFFFF\0\uFFFF\uFFFF\125\u021F\u021F\uFFFF\0\126\u021D\uFFFF\uFFFF" + 
	"\u0220\uFFFF\0\127\u0222\uFFFF\uFFFF\u0221\126\0\uFFFF\uFFFF\uFFFF\uFFFF\u0222\uFFFF\2\u0223\u0221" + 
	"\uFFFF\uFFFF\uFFFF\uFFFF\u0223\uFFFF\0\uFFFF\uFFFF\126\u0224\u0224\uFFFF\0\127\u0222\uFFFF\uFFFF" + 
	"\u0225\uFFFF\0\130\u0227\uFFFF\uFFFF\u0226\127\0\uFFFF\uFFFF\uFFFF\uFFFF\u0227\uFFFF\2\u0228\u0226" + 
	"\uFFFF\uFFFF\uFFFF\uFFFF\u0228\uFFFF\0\uFFFF\uFFFF\136\u0229\u0229\uFFFF\0\130\u0227\uFFFF\uFFFF" + 
	"\u022A\uFFFF\0\131\u022C\uFFFF\uFFFF\u022B\130\0\uFFFF\uFFFF\uFFFF\uFFFF\u022C\uFFFF\2\u022D\u022B" + 
	"\uFFFF\uFFFF\uFFFF\uFFFF\u022D\uFFFF\0\uFFFF\uFFFF\137\u022E\u022E\uFFFF\0\131\u022C\uFFFF\uFFFF" + 
	"\u022F\uFFFF\0\132\u0231\uFFFF\uFFFF\u0230\131\0\uFFFF\uFFFF\uFFFF\uFFFF\u0231\uFFFF\2\u0232\u0230" + 
	"\uFFFF\uFFFF\uFFFF\uFFFF\u0232\uFFFF\0\uFFFF\uFFFF\135\u0233\u0233\uFFFF\0\132\u0231\uFFFF\uFFFF" + 
	"\u0234\uFFFF\0\133\u0236\uFFFF\uFFFF\u0235\132\0\uFFFF\uFFFF\uFFFF\uFFFF\u0236\uFFFF\2\u0237\u0235" + 
	"\uFFFF\uFFFF\uFFFF\uFFFF\u0237\uFFFF\3\uFFFF\u0239\u023A\uFFFF\uFFFF\uFFFF\uFFFF\u0238\uFFFF\0\133" + 
	"\u0236\uFFFF\uFFFF\u0239\uFFFF\0\uFFFF\uFFFF\121\u0238\u023A\uFFFF\0\uFFFF\uFFFF\124\u0238\u023B" + 
	"\uFFFF\0\134\u023D\uFFFF\uFFFF\u023C\133\0\uFFFF\uFFFF\uFFFF\uFFFF\u023D\uFFFF\2\u023E\u023C\uFFFF" + 
	"\uFFFF\uFFFF\uFFFF\u023E\uFFFF\0\uFFFF\uFFFF\40\u023F\u023F\uFFFF\0\222\u0240\uFFFF\uFFFF\u0240" + 
	"\uFFFF\0\77\u023C\uFFFF\uFFFF\u0241\uFFFF\0\135\u0243\uFFFF\uFFFF\u0242\134\0\uFFFF\uFFFF\uFFFF" + 
	"\uFFFF\u0243\uFFFF\2\u0244\u0242\uFFFF\uFFFF\uFFFF\uFFFF\u0244\uFFFF\5\uFFFF\u0246\u0247\u0248\u0249" + 
	"\uFFFF\uFFFF\uFFFF\uFFFF\u0245\uFFFF\0\135\u0243\uFFFF\uFFFF\u0246\uFFFF\0\uFFFF\uFFFF\114\u0245" + 
	"\u0247\uFFFF\0\uFFFF\uFFFF\162\u0245\u0248\uFFFF\0\uFFFF\uFFFF\122\u0245\u0249\uFFFF\0\uFFFF\uFFFF" + 
	"\123\u0245\u024A\uFFFF\0\136\u024C\uFFFF\uFFFF\u024B\135\0\uFFFF\uFFFF\uFFFF\uFFFF\u024C\uFFFF\2" + 
	"\u024D\u024B\uFFFF\uFFFF\uFFFF\uFFFF\u024D\uFFFF\4\uFFFF\u024F\u0250\u0253\uFFFF\uFFFF\uFFFF\uFFFF" + 
	"\u024E\uFFFF\0\136\u024C\uFFFF\uFFFF\u024F\uFFFF\0\uFFFF\uFFFF\141\u024E\u0250\uFFFF\0\uFFFF\uFFFF" + 
	"\162\u0251\u0251\uFFFF\0\uFFFF\uFFFF\162\u0252\u0252\uFFFF\0\uFFFF\uFFFF\162\u024E\u0253\uFFFF\0" + 
	"\uFFFF\uFFFF\162\u0254\u0254\uFFFF\0\uFFFF\uFFFF\162\u024E\u0255\uFFFF\0\137\u0257\uFFFF\uFFFF\u0256" + 
	"\136\0\uFFFF\uFFFF\uFFFF\uFFFF\u0257\uFFFF\2\u0258\u0256\uFFFF\uFFFF\uFFFF\uFFFF\u0258\uFFFF\3\uFFFF" + 
	"\u025A\u025B\uFFFF\uFFFF\uFFFF\uFFFF\u0259\uFFFF\0\137\u0257\uFFFF\uFFFF\u025A\uFFFF\0\uFFFF\uFFFF" + 
	"\131\u0259\u025B\uFFFF\0\uFFFF\uFFFF\132\u0259\u025C\uFFFF\0\140\u025E\uFFFF\uFFFF\u025D\137\0\uFFFF" + 
	"\uFFFF\uFFFF\uFFFF\u025E\uFFFF\2\u025F\u025D\uFFFF\uFFFF\uFFFF\uFFFF\u025F\uFFFF\4\uFFFF\u0261\u0262" + 
	"\u0263\uFFFF\uFFFF\uFFFF\uFFFF\u0260\uFFFF\0\140\u025E\uFFFF\uFFFF\u0261\uFFFF\0\uFFFF\uFFFF\133" + 
	"\u0260\u0262\uFFFF\0\uFFFF\uFFFF\134\u0260\u0263\uFFFF\0\uFFFF\uFFFF\140\u0260\u0264\uFFFF\4\uFFFF" + 
	"\u0266\u0267\u026B\uFFFF\uFFFF\uFFFF\uFFFF\u0265\140\0\uFFFF\uFFFF\uFFFF\uFFFF\u0266\uFFFF\0\141" + 
	"\u0265\uFFFF\uFFFF\u0267\uFFFF\3\uFFFF\u0269\u026A\uFFFF\uFFFF\uFFFF\uFFFF\u0268\uFFFF\0\140\u0265" + 
	"\uFFFF\uFFFF\u0269\uFFFF\0\uFFFF\uFFFF\131\u0268\u026A\uFFFF\0\uFFFF\uFFFF\132\u0268\u026B\uFFFF" + 
	"\0\142\u0265\uFFFF\uFFFF\u026C\uFFFF\3\uFFFF\u026F\u0270\uFFFF\uFFFF\uFFFF\uFFFF\u026D\141\0\uFFFF" + 
	"\uFFFF\uFFFF\uFFFF\u026E\uFFFF\0\140\u026D\uFFFF\uFFFF\u026F\uFFFF\0\uFFFF\uFFFF\127\u026E\u0270" + 
	"\uFFFF\0\uFFFF\uFFFF\130\u026E\u0271\uFFFF\4\uFFFF\u0273\u0277\u0278\uFFFF\uFFFF\uFFFF\uFFFF\u0272" + 
	"\142\0\uFFFF\uFFFF\uFFFF\uFFFF\u0273\uFFFF\3\uFFFF\u0275\u0276\uFFFF\uFFFF\uFFFF\uFFFF\u0274\uFFFF" + 
	"\0\140\u0272\uFFFF\uFFFF\u0275\uFFFF\0\uFFFF\uFFFF\116\u0274\u0276\uFFFF\0\uFFFF\uFFFF\115\u0274" + 
	"\u0277\uFFFF\0\144\u0272\uFFFF\uFFFF\u0278\uFFFF\0\143\u0272\uFFFF\uFFFF\u0279\uFFFF\0\147\u027B" + 
	"\uFFFF\uFFFF\u027A\143\0\uFFFF\uFFFF\uFFFF\uFFFF\u027B\uFFFF\2\u027C\u027A\uFFFF\uFFFF\uFFFF\uFFFF" + 
	"\u027C\uFFFF\3\uFFFF\u027D\u027E\uFFFF\uFFFF\uFFFF\uFFFF\u027D\uFFFF\0\uFFFF\uFFFF\127\u027A\u027E" + 
	"\uFFFF\0\uFFFF\uFFFF\130\u027A\u027F\uFFFF\0\uFFFF\uFFFF\101\u0281\u0280\144\0\uFFFF\uFFFF\uFFFF" + 
	"\uFFFF\u0281\uFFFF\0\222\u0282\uFFFF\uFFFF\u0282\uFFFF\3\uFFFF\u0283\u0286\uFFFF\uFFFF\uFFFF\uFFFF" + 
	"\u0283\uFFFF\0\107\u0284\uFFFF\uFFFF\u0284\uFFFF\0\uFFFF\uFFFF\102\u0285\u0285\uFFFF\0\140\u0280" + 
	"\uFFFF\uFFFF\u0286\uFFFF\0\100\u0287\uFFFF\uFFFF\u0287\uFFFF\0\145\u0288\uFFFF\uFFFF\u0288\uFFFF" + 
	"\0\uFFFF\uFFFF\102\u0289\u0289\uFFFF\0\142\u0280\uFFFF\uFFFF\u028A\uFFFF\2\u028C\u028B\uFFFF\uFFFF" + 
	"\uFFFF\uFFFF\u028B\145\0\uFFFF\uFFFF\uFFFF\uFFFF\u028C\uFFFF\0\uFFFF\uFFFF\135\u028E\u028D\uFFFF" + 
	"\2\u028C\u028B\uFFFF\uFFFF\uFFFF\uFFFF\u028E\uFFFF\0\222\u028F\uFFFF\uFFFF\u028F\uFFFF\0\100\u028D" + 
	"\uFFFF\uFFFF\u0290\uFFFF\12\uFFFF\u0292\u0293\u0294\u0295\u0296\u0297\u0298\u0299\u029A\uFFFF\uFFFF" + 
	"\uFFFF\uFFFF\u0291\146\0\uFFFF\uFFFF\uFFFF\uFFFF\u0292\uFFFF\0\uFFFF\uFFFF\74\u0291\u0293\uFFFF" + 
	"\0\uFFFF\uFFFF\73\u0291\u0294\uFFFF\0\uFFFF\uFFFF\75\u0291\u0295\uFFFF\0\uFFFF\uFFFF\76\u0291\u0296" + 
	"\uFFFF\0\uFFFF\uFFFF\77\u0291\u0297\uFFFF\0\uFFFF\uFFFF\100\u0291\u0298\uFFFF\0\uFFFF\uFFFF\66\u0291" + 
	"\u0299\uFFFF\0\uFFFF\uFFFF\27\u0291\u029A\uFFFF\0\uFFFF\uFFFF\46\u0291\u029B\uFFFF\3\uFFFF\u029D" + 
	"\u029E\uFFFF\uFFFF\uFFFF\uFFFF\u029C\147\0\uFFFF\uFFFF\uFFFF\uFFFF\u029D\uFFFF\0\150\u029C\uFFFF" + 
	"\uFFFF\u029E\uFFFF\0\162\u029C\uFFFF\uFFFF\u029F\uFFFF\0\152\u02A1\uFFFF\uFFFF\u02A0\150\0\uFFFF" + 
	"\uFFFF\uFFFF\uFFFF\u02A1\uFFFF\2\u02A2\u02A0\uFFFF\uFFFF\uFFFF\uFFFF\u02A2\uFFFF\0\153\u02A1\uFFFF" + 
	"\uFFFF\u02A3\uFFFF\0\152\u02A5\uFFFF\uFFFF\u02A4\151\0\uFFFF\uFFFF\uFFFF\uFFFF\u02A5\uFFFF\2\u02A6" + 
	"\u02A4\uFFFF\uFFFF\uFFFF\uFFFF\u02A6\uFFFF\0\154\u02A5\uFFFF\uFFFF\u02A7\uFFFF\12\uFFFF\u02A9\u02AA" + 
	"\u02AB\u02B2\u02B3\u02B6\u02B8\u02B9\u02BA\uFFFF\uFFFF\uFFFF\uFFFF\u02A8\152\0\uFFFF\uFFFF\uFFFF" + 
	"\uFFFF\u02A9\uFFFF\0\146\u02A8\uFFFF\uFFFF\u02AA\uFFFF\0\uFFFF\uFFFF\62\u02A8\u02AB\uFFFF\0\uFFFF" + 
	"\uFFFF\57\u02AC\u02AC\uFFFF\3\uFFFF\u02AD\u02B1\uFFFF\uFFFF\uFFFF\uFFFF\u02AD\uFFFF\0\uFFFF\uFFFF" + 
	"\111\u02AE\u02AE\uFFFF\3\uFFFF\u02AF\u02B0\uFFFF\uFFFF\uFFFF\uFFFF\u02AF\uFFFF\0\156\u02A8\uFFFF" + 
	"\uFFFF\u02B0\uFFFF\0\155\u02A8\uFFFF\uFFFF\u02B1\uFFFF\0\160\u02A8\uFFFF\uFFFF\u02B2\uFFFF\0\161" + 
	"\u02A8\uFFFF\uFFFF\u02B3\uFFFF\0\110\u02B4\uFFFF\uFFFF\u02B4\uFFFF\0\uFFFF\uFFFF\111\u02B5\u02B5" + 
	"\uFFFF\0\uFFFF\uFFFF\16\u02A8\u02B6\uFFFF\0\110\u02B7\uFFFF\uFFFF\u02B7\uFFFF\0\160\u02A8\uFFFF" + 
	"\uFFFF\u02B8\uFFFF\0\156\u02A8\uFFFF\uFFFF\u02B9\uFFFF\0\113\u02A8\uFFFF\uFFFF\u02BA\uFFFF\0\uFFFF" + 
	"\uFFFF\101\u02BB\u02BB\uFFFF\0\114\u02BC\uFFFF\uFFFF\u02BC\uFFFF\0\uFFFF\uFFFF\102\u02A8\u02BD\uFFFF" + 
	"\4\uFFFF\u02BF\u02C0\u02C2\uFFFF\uFFFF\uFFFF\uFFFF\u02BE\153\0\uFFFF\uFFFF\uFFFF\uFFFF\u02BF\uFFFF" + 
	"\0\154\u02BE\uFFFF\uFFFF\u02C0\uFFFF\0\uFFFF\uFFFF\111\u02C1\u02C1\uFFFF\0\uFFFF\uFFFF\57\u02BE" + 
	"\u02C2\uFFFF\0\160\u02BE\uFFFF\uFFFF\u02C3\uFFFF\3\uFFFF\u02C5\u02CB\uFFFF\uFFFF\uFFFF\uFFFF\u02C4" + 
	"\154\0\uFFFF\uFFFF\uFFFF\uFFFF\u02C5\uFFFF\0\uFFFF\uFFFF\111\u02C6\u02C6\uFFFF\5\uFFFF\u02C7\u02C8" + 
	"\u02C9\u02CA\uFFFF\uFFFF\uFFFF\uFFFF\u02C7\uFFFF\0\uFFFF\uFFFF\62\u02C4\u02C8\uFFFF\0\161\u02C4" + 
	"\uFFFF\uFFFF\u02C9\uFFFF\0\156\u02C4\uFFFF\uFFFF\u02CA\uFFFF\0\155\u02C4\uFFFF\uFFFF\u02CB\uFFFF" + 
	"\0\uFFFF\uFFFF\105\u02CC\u02CC\uFFFF\0\114\u02CD\uFFFF\uFFFF\u02CD\uFFFF\0\uFFFF\uFFFF\106\u02C4" + 
	"\u02CE\uFFFF\0\113\u02CF\uFFFF\uFFFF\u02CF\155\0\uFFFF\uFFFF\uFFFF\uFFFF\u02D0\uFFFF\2\u02D3\u02D2" + 
	"\uFFFF\uFFFF\uFFFF\uFFFF\u02D1\156\0\uFFFF\uFFFF\uFFFF\uFFFF\u02D2\uFFFF\0\113\u02D4\uFFFF\uFFFF" + 
	"\u02D3\uFFFF\0\102\u02D2\uFFFF\uFFFF\u02D4\uFFFF\0\157\u02D1\uFFFF\uFFFF\u02D5\uFFFF\0\uFFFF\uFFFF" + 
	"\101\u02D7\u02D6\157\0\uFFFF\uFFFF\uFFFF\uFFFF\u02D7\uFFFF\2\u02D9\u02D8\uFFFF\uFFFF\uFFFF\uFFFF" + 
	"\u02D8\uFFFF\0\uFFFF\uFFFF\102\u02D6\u02D9\uFFFF\3\uFFFF\u02DA\u02DB\uFFFF\uFFFF\uFFFF\uFFFF\u02DA" + 
	"\uFFFF\0\25\u02D8\uFFFF\uFFFF\u02DB\uFFFF\0\114\u02DC\uFFFF\uFFFF\u02DC\uFFFF\2\u02DD\u02D8\uFFFF" + 
	"\uFFFF\uFFFF\uFFFF\u02DD\uFFFF\0\uFFFF\uFFFF\110\u02DE\u02DE\uFFFF\0\114\u02DC\uFFFF\uFFFF\u02DF" + 
	"\uFFFF\0\uFFFF\uFFFF\157\u02E1\u02E0\160\0\uFFFF\uFFFF\uFFFF\uFFFF\u02E1\uFFFF\2\u02E3\u02E2\uFFFF" + 
	"\uFFFF\uFFFF\uFFFF\u02E2\uFFFF\3\uFFFF\u02E4\u02E5\uFFFF\uFFFF\uFFFF\uFFFF\u02E3\uFFFF\0\102\u02E2" + 
	"\uFFFF\uFFFF\u02E4\uFFFF\0\113\u02E0\uFFFF\uFFFF\u02E5\uFFFF\0\uFFFF\uFFFF\45\u02E0\u02E6\uFFFF" + 
	"\0\uFFFF\uFFFF\45\u02E8\u02E7\161\0\uFFFF\uFFFF\uFFFF\uFFFF\u02E8\uFFFF\2\u02EA\u02E9\uFFFF\uFFFF" + 
	"\uFFFF\uFFFF\u02E9\uFFFF\0\222\u02EB\uFFFF\uFFFF\u02EA\uFFFF\0\102\u02E9\uFFFF\uFFFF\u02EB\uFFFF" + 
	"\0\101\u02EC\uFFFF\uFFFF\u02EC\uFFFF\0\157\u02ED\uFFFF\uFFFF\u02ED\uFFFF\2\u02EE\u02E7\uFFFF\uFFFF" + 
	"\uFFFF\uFFFF\u02EE\uFFFF\0\53\u02E7\uFFFF\uFFFF\u02EF\uFFFF\0\uFFFF\uFFFF\45\u02F1\u02F0\162\0\uFFFF" + 
	"\uFFFF\uFFFF\uFFFF\u02F1\uFFFF\2\u02F3\u02F2\uFFFF\uFFFF\uFFFF\uFFFF\u02F2\uFFFF\0\222\u02F4\uFFFF" + 
	"\uFFFF\u02F3\uFFFF\0\102\u02F2\uFFFF\uFFFF\u02F4\uFFFF\3\uFFFF\u02F6\u02F7\uFFFF\uFFFF\uFFFF\uFFFF" + 
	"\u02F5\uFFFF\0\163\u02F0\uFFFF\uFFFF\u02F6\uFFFF\0\107\u02F5\uFFFF\uFFFF\u02F7\uFFFF\0\101\u02F5" + 
	"\uFFFF\uFFFF\u02F8\uFFFF\3\uFFFF\u02FA\u02FC\uFFFF\uFFFF\uFFFF\uFFFF\u02F9\163\0\uFFFF\uFFFF\uFFFF" + 
	"\uFFFF\u02FA\uFFFF\0\164\u02FB\uFFFF\uFFFF\u02FB\uFFFF\0\63\u02F9\uFFFF\uFFFF\u02FC\uFFFF\0\165" + 
	"\u02FD\uFFFF\uFFFF\u02FD\uFFFF\0\65\u02F9\uFFFF\uFFFF\u02FE\uFFFF\0\222\u0301\uFFFF\uFFFF\u02FF" + 
	"\164\0\uFFFF\uFFFF\uFFFF\uFFFF\u0300\uFFFF\2\u02FE\u02FF\uFFFF\uFFFF\uFFFF\uFFFF\u0301\uFFFF\0\uFFFF" + 
	"\uFFFF\105\u0302\u0302\uFFFF\0\114\u0303\uFFFF\uFFFF\u0303\uFFFF\0\uFFFF\uFFFF\106\u0300\u0304\uFFFF" + 
	"\0\222\u0307\uFFFF\uFFFF\u0305\165\0\uFFFF\uFFFF\uFFFF\uFFFF\u0306\uFFFF\2\u0304\u0305\uFFFF\uFFFF" + 
	"\uFFFF\uFFFF\u0307\uFFFF\0\uFFFF\uFFFF\105\u0308\u0308\uFFFF\0\uFFFF\uFFFF\106\u0306\u0309\uFFFF" + 
	"\21\uFFFF\u030B\u030C\u030D\u030E\u030F\u0310\u0311\u0312\u0313\u0314\u0315\u0316\u0317\u0318\u0319" + 
	"\u031A\uFFFF\uFFFF\uFFFF\uFFFF\u030A\166\0\uFFFF\uFFFF\uFFFF\uFFFF\u030B\uFFFF\0\170\u030A\uFFFF" + 
	"\uFFFF\u030C\uFFFF\0\167\u030A\uFFFF\uFFFF\u030D\uFFFF\0\171\u030A\uFFFF\uFFFF\u030E\uFFFF\0\174" + 
	"\u030A\uFFFF\uFFFF\u030F\uFFFF\0\175\u030A\uFFFF\uFFFF\u0310\uFFFF\0\177\u030A\uFFFF\uFFFF\u0311" + 
	"\uFFFF\0\201\u030A\uFFFF\uFFFF\u0312\uFFFF\0\202\u030A\uFFFF\uFFFF\u0313\uFFFF\0\203\u030A\uFFFF" + 
	"\uFFFF\u0314\uFFFF\0\204\u030A\uFFFF\uFFFF\u0315\uFFFF\0\210\u030A\uFFFF\uFFFF\u0316\uFFFF\0\211" + 
	"\u030A\uFFFF\uFFFF\u0317\uFFFF\0\212\u030A\uFFFF\uFFFF\u0318\uFFFF\0\213\u030A\uFFFF\uFFFF\u0319" + 
	"\uFFFF\0\214\u030A\uFFFF\uFFFF\u031A\uFFFF\0\215\u030A\uFFFF\uFFFF\u031B\uFFFF\0\uFFFF\uFFFF\7\u031D" + 
	"\u031C\167\0\uFFFF\uFFFF\uFFFF\uFFFF\u031D\uFFFF\0\114\u031E\uFFFF\uFFFF\u031E\uFFFF\2\u0320\u031F" + 
	"\uFFFF\uFFFF\uFFFF\uFFFF\u031F\uFFFF\0\uFFFF\uFFFF\107\u031C\u0320\uFFFF\0\uFFFF\uFFFF\120\u0321" + 
	"\u0321\uFFFF\0\114\u031F\uFFFF\uFFFF\u0322\uFFFF\0\113\u0324\uFFFF\uFFFF\u0323\170\0\uFFFF\uFFFF" + 
	"\uFFFF\uFFFF\u0324\uFFFF\0\uFFFF\uFFFF\120\u0325\u0325\uFFFF\0\166\u0323\uFFFF\uFFFF\u0326\uFFFF" + 
	"\0\uFFFF\uFFFF\103\u0328\u0327\171\0\uFFFF\uFFFF\uFFFF\uFFFF\u0328\uFFFF\0\75\u0329\uFFFF\uFFFF" + 
	"\u0329\uFFFF\0\uFFFF\uFFFF\104\u0327\u032A\uFFFF\4\uFFFF\u032C\u032E\u0330\uFFFF\uFFFF\uFFFF\uFFFF" + 
	"\u032B\172\0\uFFFF\uFFFF\uFFFF\uFFFF\u032C\uFFFF\0\35\u032D\uFFFF\uFFFF\u032D\uFFFF\0\37\u032B\uFFFF" + 
	"\uFFFF\u032E\uFFFF\0\173\u032F\uFFFF\uFFFF\u032F\uFFFF\0\uFFFF\uFFFF\107\u032B\u0330\uFFFF\0\166" + 
	"\u032B\uFFFF\uFFFF\u0331\uFFFF\0\35\u0333\uFFFF\uFFFF\u0332\173\0\uFFFF\uFFFF\uFFFF\uFFFF\u0333" + 
	"\uFFFF\0\57\u0332\uFFFF\uFFFF\u0334\uFFFF\0\uFFFF\uFFFF\107\u0335\u0335\174\0\uFFFF\uFFFF\uFFFF" + 
	"\uFFFF\u0336\uFFFF\0\176\u0338\uFFFF\uFFFF\u0337\175\0\uFFFF\uFFFF\uFFFF\uFFFF\u0338\uFFFF\0\uFFFF" + 
	"\uFFFF\107\u0337\u0339\uFFFF\0\114\u033A\uFFFF\uFFFF\u033A\176\0\uFFFF\uFFFF\uFFFF\uFFFF\u033B\uFFFF" + 
	"\0\uFFFF\uFFFF\60\u033D\u033C\177\0\uFFFF\uFFFF\uFFFF\uFFFF\u033D\uFFFF\0\uFFFF\uFFFF\101\u033E" + 
	"\u033E\uFFFF\0\114\u033F\uFFFF\uFFFF\u033F\uFFFF\0\uFFFF\uFFFF\102\u0340\u0340\uFFFF\0\uFFFF\uFFFF" + 
	"\103\u0341\u0341\uFFFF\2\u0343\u0342\uFFFF\uFFFF\uFFFF\uFFFF\u0342\uFFFF\0\uFFFF\uFFFF\104\u033C" + 
	"\u0343\uFFFF\0\200\u0341\uFFFF\uFFFF\u0344\uFFFF\3\uFFFF\u0347\u0349\uFFFF\uFFFF\uFFFF\uFFFF\u0345" + 
	"\200\0\uFFFF\uFFFF\uFFFF\uFFFF\u0346\uFFFF\0\uFFFF\uFFFF\120\u034A\u0347\uFFFF\0\uFFFF\uFFFF\13" + 
	"\u0348\u0348\uFFFF\0\114\u0346\uFFFF\uFFFF\u0349\uFFFF\0\uFFFF\uFFFF\21\u0346\u034A\uFFFF\0\75\u0345" + 
	"\uFFFF\uFFFF\u034B\uFFFF\0\uFFFF\uFFFF\35\u034D\u034C\201\0\uFFFF\uFFFF\uFFFF\uFFFF\u034D\uFFFF" + 
	"\0\uFFFF\uFFFF\101\u034E\u034E\uFFFF\0\114\u034F\uFFFF\uFFFF\u034F\uFFFF\0\uFFFF\uFFFF\102\u0350" + 
	"\u0350\uFFFF\0\166\u0351\uFFFF\uFFFF\u0351\uFFFF\2\u0352\u034C\uFFFF\uFFFF\uFFFF\uFFFF\u0352\uFFFF" + 
	"\0\uFFFF\uFFFF\24\u0353\u0353\uFFFF\0\166\u034C\uFFFF\uFFFF\u0354\uFFFF\0\uFFFF\uFFFF\72\u0356\u0355" + 
	"\202\0\uFFFF\uFFFF\uFFFF\uFFFF\u0356\uFFFF\0\uFFFF\uFFFF\101\u0357\u0357\uFFFF\0\114\u0358\uFFFF" + 
	"\uFFFF\u0358\uFFFF\0\uFFFF\uFFFF\102\u0359\u0359\uFFFF\0\166\u0355\uFFFF\uFFFF\u035A\uFFFF\0\uFFFF" + 
	"\uFFFF\22\u035C\u035B\203\0\uFFFF\uFFFF\uFFFF\uFFFF\u035C\uFFFF\0\166\u035D\uFFFF\uFFFF\u035D\uFFFF" + 
	"\0\uFFFF\uFFFF\72\u035E\u035E\uFFFF\0\uFFFF\uFFFF\101\u035F\u035F\uFFFF\0\114\u0360\uFFFF\uFFFF" + 
	"\u0360\uFFFF\0\uFFFF\uFFFF\102\u0361\u0361\uFFFF\0\uFFFF\uFFFF\107\u035B\u0362\uFFFF\0\uFFFF\uFFFF" + 
	"\33\u0364\u0363\204\0\uFFFF\uFFFF\uFFFF\uFFFF\u0364\uFFFF\0\uFFFF\uFFFF\101\u0365\u0365\uFFFF\3" + 
	"\uFFFF\u0367\u036A\uFFFF\uFFFF\uFFFF\uFFFF\u0366\uFFFF\0\uFFFF\uFFFF\102\u0372\u0367\uFFFF\0\173" + 
	"\u0368\uFFFF\uFFFF\u0368\uFFFF\0\uFFFF\uFFFF\120\u0369\u0369\uFFFF\0\114\u0366\uFFFF\uFFFF\u036A" + 
	"\uFFFF\2\u036C\u036B\uFFFF\uFFFF\uFFFF\uFFFF\u036B\uFFFF\0\uFFFF\uFFFF\107\u036D\u036C\uFFFF\0\205" + 
	"\u036B\uFFFF\uFFFF\u036D\uFFFF\2\u036F\u036E\uFFFF\uFFFF\uFFFF\uFFFF\u036E\uFFFF\0\uFFFF\uFFFF\107" + 
	"\u0370\u036F\uFFFF\0\114\u036E\uFFFF\uFFFF\u0370\uFFFF\2\u0371\u0366\uFFFF\uFFFF\uFFFF\uFFFF\u0371" + 
	"\uFFFF\0\207\u0366\uFFFF\uFFFF\u0372\uFFFF\0\166\u0363\uFFFF\uFFFF\u0373\uFFFF\3\uFFFF\u0375\u0376" + 
	"\uFFFF\uFFFF\uFFFF\uFFFF\u0374\205\0\uFFFF\uFFFF\uFFFF\uFFFF\u0375\uFFFF\0\173\u0374\uFFFF\uFFFF" + 
	"\u0376\uFFFF\0\206\u0374\uFFFF\uFFFF\u0377\uFFFF\0\176\u0379\uFFFF\uFFFF\u0378\206\0\uFFFF\uFFFF" + 
	"\uFFFF\uFFFF\u0379\uFFFF\2\u037A\u0378\uFFFF\uFFFF\uFFFF\uFFFF\u037A\uFFFF\0\uFFFF\uFFFF\110\u037B" + 
	"\u037B\uFFFF\0\176\u0379\uFFFF\uFFFF\u037C\uFFFF\0\206\u037D\uFFFF\uFFFF\u037D\207\0\uFFFF\uFFFF" + 
	"\uFFFF\uFFFF\u037E\uFFFF\0\uFFFF\uFFFF\11\u0380\u037F\210\0\uFFFF\uFFFF\uFFFF\uFFFF\u0380\uFFFF" + 
	"\2\u0382\u0381\uFFFF\uFFFF\uFFFF\uFFFF\u0381\uFFFF\0\uFFFF\uFFFF\107\u037F\u0382\uFFFF\0\113\u0381" + 
	"\uFFFF\uFFFF\u0383\uFFFF\0\uFFFF\uFFFF\20\u0385\u0384\211\0\uFFFF\uFFFF\uFFFF\uFFFF\u0385\uFFFF" + 
	"\2\u0387\u0386\uFFFF\uFFFF\uFFFF\uFFFF\u0386\uFFFF\0\uFFFF\uFFFF\107\u0384\u0387\uFFFF\0\113\u0386" + 
	"\uFFFF\uFFFF\u0388\uFFFF\0\uFFFF\uFFFF\53\u038A\u0389\212\0\uFFFF\uFFFF\uFFFF\uFFFF\u038A\uFFFF" + 
	"\2\u038C\u038B\uFFFF\uFFFF\uFFFF\uFFFF\u038B\uFFFF\0\uFFFF\uFFFF\107\u0389\u038C\uFFFF\0\114\u038B" + 
	"\uFFFF\uFFFF\u038D\uFFFF\0\uFFFF\uFFFF\63\u038F\u038E\213\0\uFFFF\uFFFF\uFFFF\uFFFF\u038F\uFFFF" + 
	"\0\114\u0390\uFFFF\uFFFF\u0390\uFFFF\0\uFFFF\uFFFF\107\u038E\u0391\uFFFF\0\uFFFF\uFFFF\61\u0393" + 
	"\u0392\214\0\uFFFF\uFFFF\uFFFF\uFFFF\u0393\uFFFF\0\uFFFF\uFFFF\101\u0394\u0394\uFFFF\0\114\u0395" + 
	"\uFFFF\uFFFF\u0395\uFFFF\0\uFFFF\uFFFF\102\u0396\u0396\uFFFF\0\171\u0392\uFFFF\uFFFF\u0397\uFFFF" + 
	"\0\uFFFF\uFFFF\67\u0399\u0398\215\0\uFFFF\uFFFF\uFFFF\uFFFF\u0399\uFFFF\3\uFFFF\u039A\u03A1\uFFFF" + 
	"\uFFFF\uFFFF\uFFFF\u039A\uFFFF\0\221\u039B\uFFFF\uFFFF\u039B\uFFFF\0\171\u039C\uFFFF\uFFFF\u039C" + 
	"\uFFFF\2\u039E\u039D\uFFFF\uFFFF\uFFFF\uFFFF\u039D\uFFFF\2\u039F\u0398\uFFFF\uFFFF\uFFFF\uFFFF\u039E" + 
	"\uFFFF\0\216\u039D\uFFFF\uFFFF\u039F\uFFFF\0\uFFFF\uFFFF\31\u03A0\u03A0\uFFFF\0\171\u0398\uFFFF" + 
	"\uFFFF\u03A1\uFFFF\0\171\u03A2\uFFFF\uFFFF\u03A2\uFFFF\3\uFFFF\u03A3\u03A7\uFFFF\uFFFF\uFFFF\uFFFF" + 
	"\u03A3\uFFFF\0\216\u03A4\uFFFF\uFFFF\u03A4\uFFFF\2\u03A5\u0398\uFFFF\uFFFF\uFFFF\uFFFF\u03A5\uFFFF" + 
	"\0\uFFFF\uFFFF\31\u03A6\u03A6\uFFFF\0\171\u0398\uFFFF\uFFFF\u03A7\uFFFF\0\uFFFF\uFFFF\31\u03A8\u03A8" + 
	"\uFFFF\0\171\u0398\uFFFF\uFFFF\u03A9\uFFFF\0\217\u03AB\uFFFF\uFFFF\u03AA\216\0\uFFFF\uFFFF\uFFFF" + 
	"\uFFFF\u03AB\uFFFF\2\u03A9\u03AA\uFFFF\uFFFF\uFFFF\uFFFF\u03AC\uFFFF\0\uFFFF\uFFFF\14\u03AE\u03AD" + 
	"\217\0\uFFFF\uFFFF\uFFFF\uFFFF\u03AE\uFFFF\0\uFFFF\uFFFF\101\u03AF\u03AF\uFFFF\0\220\u03B0\uFFFF" + 
	"\uFFFF\u03B0\uFFFF\0\uFFFF\uFFFF\102\u03B1\u03B1\uFFFF\0\171\u03AD\uFFFF\uFFFF\u03B2\uFFFF\0\34" + 
	"\u03B4\uFFFF\uFFFF\u03B3\220\0\uFFFF\uFFFF\uFFFF\uFFFF\u03B4\uFFFF\0\101\u03B5\uFFFF\uFFFF\u03B5" + 
	"\uFFFF\2\u03B7\u03B6\uFFFF\uFFFF\uFFFF\uFFFF\u03B6\uFFFF\0\62\u03B3\uFFFF\uFFFF\u03B7\uFFFF\0\uFFFF" + 
	"\uFFFF\136\u03B9\u03B8\uFFFF\2\u03B7\u03B6\uFFFF\uFFFF\uFFFF\uFFFF\u03B9\uFFFF\0\111\u03B8\uFFFF" + 
	"\uFFFF\u03BA\uFFFF\0\uFFFF\uFFFF\101\u03BC\u03BB\221\0\uFFFF\uFFFF\uFFFF\uFFFF\u03BC\uFFFF\0\173" + 
	"\u03BD\uFFFF\uFFFF\u03BD\uFFFF\2\u03BF\u03BE\uFFFF\uFFFF\uFFFF\uFFFF\u03BE\uFFFF\2\u03C2\u03C1\uFFFF" + 
	"\uFFFF\uFFFF\uFFFF\u03BF\uFFFF\0\uFFFF\uFFFF\107\u03C0\u03C0\uFFFF\0\173\u03BD\uFFFF\uFFFF\u03C1" + 
	"\uFFFF\0\uFFFF\uFFFF\102\u03BB\u03C2\uFFFF\0\uFFFF\uFFFF\107\u03C1\u03C3\uFFFF\2\u03C5\u03C4\uFFFF" + 
	"\uFFFF\uFFFF\uFFFF\u03C4\222\0\uFFFF\uFFFF\uFFFF\uFFFF\u03C5\uFFFF\0\223\u03C3\uFFFF\uFFFF\u03C6" + 
	"\uFFFF\4\uFFFF\u03C8\u03C9\u03CA\uFFFF\uFFFF\uFFFF\uFFFF\u03C7\223\0\uFFFF\uFFFF\uFFFF\uFFFF\u03C8" + 
	"\uFFFF\0\224\u03C7\uFFFF\uFFFF\u03C9\uFFFF\0\225\u03C7\uFFFF\uFFFF\u03CA\uFFFF\0\226\u03C7\uFFFF" + 
	"\uFFFF\u03CB\uFFFF\0\uFFFF\uFFFF\112\u03CD\u03CC\224\0\uFFFF\uFFFF\uFFFF\uFFFF\u03CD\uFFFF\0\112" + 
	"\u03CE\uFFFF\uFFFF\u03CE\uFFFF\0\uFFFF\uFFFF\101\u03CF\u03CF\uFFFF\2\u03D1\u03D0\uFFFF\uFFFF\uFFFF" + 
	"\uFFFF\u03D0\uFFFF\0\uFFFF\uFFFF\102\u03CC\u03D1\uFFFF\0\227\u03D0\uFFFF\uFFFF\u03D2\uFFFF\0\uFFFF" + 
	"\uFFFF\112\u03D4\u03D3\225\0\uFFFF\uFFFF\uFFFF\uFFFF\u03D4\uFFFF\0\112\u03D3\uFFFF\uFFFF\u03D5\uFFFF" + 
	"\0\uFFFF\uFFFF\112\u03D7\u03D6\226\0\uFFFF\uFFFF\uFFFF\uFFFF\u03D7\uFFFF\0\112\u03D8\uFFFF\uFFFF" + 
	"\u03D8\uFFFF\0\uFFFF\uFFFF\101\u03D9\u03D9\uFFFF\0\231\u03DA\uFFFF\uFFFF\u03DA\uFFFF\0\uFFFF\uFFFF" + 
	"\102\u03D6\u03DB\uFFFF\0\230\u03DD\uFFFF\uFFFF\u03DC\227\0\uFFFF\uFFFF\uFFFF\uFFFF\u03DD\uFFFF\2" + 
	"\u03DE\u03DC\uFFFF\uFFFF\uFFFF\uFFFF\u03DE\uFFFF\0\uFFFF\uFFFF\110\u03DF\u03DF\uFFFF\0\230\u03DD" + 
	"\uFFFF\uFFFF\u03E0\uFFFF\0\113\u03E2\uFFFF\uFFFF\u03E1\230\0\uFFFF\uFFFF\uFFFF\uFFFF\u03E2\uFFFF" + 
	"\0\uFFFF\uFFFF\113\u03E3\u03E3\uFFFF\0\231\u03E1\uFFFF\uFFFF\u03E4\uFFFF\4\uFFFF\u03E6\u03E7\u03E8" + 
	"\uFFFF\uFFFF\uFFFF\uFFFF\u03E5\231\0\uFFFF\uFFFF\uFFFF\uFFFF\u03E6\uFFFF\0\124\u03E5\uFFFF\uFFFF" + 
	"\u03E7\uFFFF\0\232\u03E5\uFFFF\uFFFF\u03E8\uFFFF\0\223\u03E5\uFFFF\uFFFF\u03E9\uFFFF\0\uFFFF\uFFFF" + 
	"\103\u03EB\u03EA\232\0\uFFFF\uFFFF\uFFFF\uFFFF\u03EB\uFFFF\2\u03ED\u03EC\uFFFF\uFFFF\uFFFF\uFFFF" + 
	"\u03EC\uFFFF\2\u03EF\u03EE\uFFFF\uFFFF\uFFFF\uFFFF\u03ED\uFFFF\0\233\u03EC\uFFFF\uFFFF\u03EE\uFFFF" + 
	"\0\uFFFF\uFFFF\104\u03EA\u03EF\uFFFF\0\uFFFF\uFFFF\110\u03EE\u03F0\uFFFF\0\231\u03F2\uFFFF\uFFFF" + 
	"\u03F1\233\0\uFFFF\uFFFF\uFFFF\uFFFF\u03F2\uFFFF\2\u03F3\u03F1\uFFFF\uFFFF\uFFFF\uFFFF\u03F3\uFFFF" + 
	"\0\uFFFF\uFFFF\110\u03F4\u03F4\uFFFF\0\231\u03F2\uFFFF\uFFFF\0\2\5\10\13\16\21\24\27\33\37\43\46" + 
	"\51\54\57\62\65\71\74\77\101\103\105\113\120\123\134\137\157\176\206\230\240\250\273\302\307\317" + 
	"\331\343\354\361\371\375\u0103\u010F\u0113\u0116\u011B\u0120\u0123\u0128\u012C\u0136\u0143\u0148" + 
	"\u014F\u015B\u0161\u016B\u017A\u0182\u0184\u018A\u0191\u019B\u019F\u01A4\u01AB\u01B0\u01BA\u01C4" + 
	"\u01C8\u01CB\u01D0\u01D4\u01D8\u01DD\u01E6\u01F9\u01FD\u0202\u0204\u0212\u021B\u0220\u0225\u022A" + 
	"\u022F\u0234\u023B\u0241\u024A\u0255\u025C\u0264\u026C\u0271\u0279\u027F\u028A\u0290\u029B\u029F" + 
	"\u02A3\u02A7\u02BD\u02C3\u02CE\u02D0\u02D5\u02DF\u02E6\u02EF\u02F8\u02FE\u0304\u0309\u031B\u0322" + 
	"\u0326\u032A\u0331\u0334\u0336\u0339\u033B\u0344\u034B\u0354\u035A\u0362\u0373\u0377\u037C\u037E" + 
	"\u0383\u0388\u038D\u0391\u0397\u03A9\u03AC\u03B2\u03BA\u03C3\u03C6\u03CB\u03D2\u03D5\u03DB\u03E0" + 
	"\u03E4\u03E9\u03F0\105\137\263\323\u0107\u0131\u0153\u016B\u0172\u017A\u017E\u0186\u0194\u01CD\u01D4" + 
	"\u01DD\u01E6\u0218\u024C\u024D\u0271\u0282\u029B\u02A5\u02A7\u02AE\u02BD\u02C6\u02F8\u0300\u0306" + 
	"\u0309\u032A\u0365\u0373\u03BD\u03C6\u03F2\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\1\106\13\231" + 
	"\241\257\312\346\362\376\u0149\u017B\u01A5\u02D8\1\u01D1\0\1\107\1\111\1\120\1\112\6\202\275\323" + 
	"\u0107\u0151\u03B4\2\u032D\u0333\1\134\4\177\320\u0104\u032B\1\210\2\210\254\3\177\320\u0104\1\263" + 
	"\3\177\320\u0104\1\303\1\316\1\320\4\214\225\u013A\u0163\1\351\1\355\3\207\274\u02E7\2\270\374\1" + 
	"\u0102\2\320\u0104\1\u0332\2\u0112\u0114\1\u0118\3\u011D\u0150\u03B3\4\337\u0121\u013E\u02F9\2\u011C" + 
	"\u0131\2\u0129\u02F9\1\u0104\2\u013D\u0166\2\u0146\u01F6\1\u014C\2\u013F\u0167\1\u0104\1\u017F\2" + 
	"\u016A\u0329\1\u0104\6\333\u0111\u0115\u0152\u01C5\u023C\5\u01AC\u01B1\u01E2\u0287\u028D\6\u0186" + 
	"\u018F\u01C9\u02EC\u02F5\u03B5\6\u016E\u0176\u02D2\u02E2\u02E9\u02F2\1\u0194\2\u019E\u01A2\1\u01A8" + 
	"\1\u01AC\4\u0186\u018D\u0284\u02F5\3\u013B\u02B4\u02B7\6\216\235\245\366\u015E\u03B8\5\117\130\u03CE" + 
	"\u03D3\u03D8\30\213\224\253\276\306\334\357\u0122\u013C\u0159\u0165\u0193\u0199\u01CD\u01E9\u0203" + 
	"\u02A8\u02CF\u02D4\u02E0\u0324\u0381\u0386\u03E2\24\u0129\u01D9\u01FA\u0217\u02BC\u02CD\u02DC\u0303" + 
	"\u031E\u031F\u033A\u033F\u034F\u0358\u0360\u0366\u036E\u038B\u0390\u0395\1\u01D5\3\u01D5\u01DE\u0213" + 
	"\1\u01DE\1\u01E7\1\u01F1\1\u01FF\1\u01DC\3\u01DA\u0213\u03E5\1\u0214\1\u021D\1\u0222\1\u0227\1\u022C" + 
	"\1\u0231\1\u0236\1\u023D\1\u0243\1\u024C\1\u0257\5\u025E\u0265\u026D\u0272\u0280\1\u0265\2\u0265" + 
	"\u0280\1\u0272\1\u0272\2\u01E3\u0288\1\u02A8\1\u027B\1\u029C\1\u0175\2\u02A1\u02A5\1\u02A1\2\u02A5" + 
	"\u02BE\2\u02A8\u02C4\2\u02A8\u02C4\5\277\u0171\u0179\u02D1\u02ED\2\u02A8\u02BE\2\u02A8\u02C4\1\u029C" + 
	"\1\u02F0\1\u02FB\3\u0185\u018B\u02FD\7\u0323\u032B\u034C\u0351\u0355\u035D\u0363\1\u030A\1\u030A" + 
	"\11\u0137\u0183\u01FA\u030A\u0392\u0398\u039C\u03A2\u03AD\1\u017F\4\u032F\u0368\u0374\u03BD\1\u030A" + 
	"\1\u030A\2\u0338\u0379\1\u030A\1\u0341\1\u030A\1\u030A\1\u030A\1\u030A\1\u036B\2\u0374\u037D\1\u0366" + 
	"\1\u030A\1\u030A\1\u030A\1\u030A\1\u030A\1\u030A\2\u039D\u03A4\1\u03AB\1\u03B0\1\u039B\22\115\356" + 
	"\u0126\u0138\u0155\u0198\u01AD\u01B6\u01B9\u01CA\u01E1\u0240\u0282\u028F\u02EB\u02F4\u0301\u0307" + 
	"\4\137\157\u03C3\u03E5\1\u03C7\1\u03C7\1\u03C7\1\u03D0\1\u03DD\4\340\u03DA\u03E1\u03F2\1\u03E5\1" + 
	"\u03EC\27\1\30\4\32\7\36\12\55\15\46\20\34\23\222\26\66\32\56\36\47\42\43\45\71\50\51\53\75\56\172" + 
	"\61\114\64\77\70\112\73\113\76\75\u0345";
}
