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

/**
 * Internal implementation of the Java parser as a recursive descent parser using ALL(*) predictions.
 */
public class ParserImplementation2 extends ParserNewBase2 {

	protected Grammar initializeGrammar() {
		return new JavaGrammar();
	}

	static class JavaGrammar extends Grammar {

		public static final int ADDITIVE_EXPRESSION = 213;

		public static final int ADDITIVE_EXPRESSION_2 = 219;

		public static final int ADDITIVE_EXPRESSION_2_1 = 220;

		public static final int AND_EXPRESSION = 201;

		public static final int AND_EXPRESSION_2 = 204;

		public static final int ANNOTATED_QUALIFIED_TYPE = 66;

		public static final int ANNOTATION = 53;

		public static final int ANNOTATIONS = 17;

		public static final int ANNOTATIONS_1 = 324;

		public static final int ANNOTATIONS_ENTRY = 16;

		public static final int ANNOTATION_1 = 325;

		public static final int ANNOTATION_ELEMENT_DECL_ENTRY = 22;

		public static final int ANNOTATION_MEMBER_DECL_ENTRY = 12;

		public static final int ANNOTATION_TYPE_BODY = 87;

		public static final int ANNOTATION_TYPE_BODY_2 = 88;

		public static final int ANNOTATION_TYPE_BODY_2_1 = 89;

		public static final int ANNOTATION_TYPE_BODY_2_1_2 = 90;

		public static final int ANNOTATION_TYPE_BODY_DECL = 13;

		public static final int ANNOTATION_TYPE_BODY_DECL_1 = 91;

		public static final int ANNOTATION_TYPE_BODY_DECL_1_2_2 = 92;

		public static final int ANNOTATION_TYPE_DECL = 61;

		public static final int ANNOTATION_TYPE_MEMBER_DECL = 23;

		public static final int ANNOTATION_TYPE_MEMBER_DECL_6 = 94;

		public static final int ARGUMENTS = 85;

		public static final int ARGUMENTS_2 = 260;

		public static final int ARGUMENTS_2_1 = 261;

		public static final int ARGUMENTS_2_1_2_2 = 262;

		public static final int ARRAY_CREATION_EXPR = 243;

		public static final int ARRAY_CREATION_EXPR_2 = 267;

		public static final int ARRAY_CREATION_EXPR_4 = 268;

		public static final int ARRAY_CREATION_EXPR_REST = 269;

		public static final int ARRAY_DIMS = 93;

		public static final int ARRAY_DIMS_1 = 116;

		public static final int ARRAY_DIMS_MANDATORY = 153;

		public static final int ARRAY_DIMS_MANDATORY_1 = 272;

		public static final int ARRAY_DIM_EXPRS_MANDATORY = 270;

		public static final int ARRAY_DIM_EXPRS_MANDATORY_1 = 271;

		public static final int ARRAY_INITIALIZER = 118;

		public static final int ARRAY_INITIALIZER_2 = 119;

		public static final int ARRAY_INITIALIZER_2_2 = 120;

		public static final int ARRAY_INITIALIZER_3 = 121;

		public static final int ASSERT_STATEMENT = 276;

		public static final int ASSERT_STATEMENT_3 = 290;

		public static final int ASSIGNMENT_EXPRESSION = 177;

		public static final int ASSIGNMENT_EXPRESSION_2 = 180;

		public static final int ASSIGNMENT_OPERATOR = 181;

		public static final int ASSIGNMENT_OPERATOR_1 = 191;

		public static final int BLOCK = 128;

		public static final int BLOCK_STATEMENT = 33;

		public static final int BLOCK_STATEMENT_1 = 291;

		public static final int BLOCK_STATEMENT_ENTRY = 32;

		public static final int BREAK_STATEMENT = 284;

		public static final int BREAK_STATEMENT_2 = 307;

		public static final int CAST_EXPRESSION = 231;

		public static final int CAST_EXPRESSION_3 = 236;

		public static final int CATCH_CLAUSE = 318;

		public static final int CATCH_CLAUSES = 313;

		public static final int CATCH_CLAUSES_1 = 317;

		public static final int CATCH_FORMAL_PARAMETER = 319;

		public static final int CATCH_FORMAL_PARAMETER_3 = 320;

		public static final int CATCH_FORMAL_PARAMETER_3_1 = 321;

		public static final int CLASS_CREATION_EXPR = 255;

		public static final int CLASS_CREATION_EXPR_2 = 265;

		public static final int CLASS_CREATION_EXPR_6 = 266;

		public static final int CLASS_OR_INTERFACE_BODY = 72;

		public static final int CLASS_OR_INTERFACE_BODY_DECL = 11;

		public static final int CLASS_OR_INTERFACE_BODY_DECLS = 83;

		public static final int CLASS_OR_INTERFACE_BODY_DECLS_1 = 102;

		public static final int CLASS_OR_INTERFACE_BODY_DECLS_1_1 = 103;

		public static final int CLASS_OR_INTERFACE_BODY_DECLS_1_1_2 = 104;

		public static final int CLASS_OR_INTERFACE_BODY_DECL_1 = 105;

		public static final int CLASS_OR_INTERFACE_BODY_DECL_1_2_2 = 106;

		public static final int CLASS_OR_INTERFACE_DECL = 59;

		public static final int CLASS_OR_INTERFACE_DECL_1 = 62;

		public static final int CLASS_OR_INTERFACE_DECL_1_1_3 = 63;

		public static final int CLASS_OR_INTERFACE_DECL_1_1_4 = 65;

		public static final int CLASS_OR_INTERFACE_DECL_1_1_5 = 67;

		public static final int CLASS_OR_INTERFACE_DECL_1_2_3 = 69;

		public static final int CLASS_OR_INTERFACE_DECL_1_2_4 = 70;

		public static final int COMPILATION_UNIT = 2;

		public static final int COMPILATION_UNIT_1 = 44;

		public static final int COMPILATION_UNIT_ENTRY = 1;

		public static final int CONDITIONAL_AND_EXPRESSION = 195;

		public static final int CONDITIONAL_AND_EXPRESSION_2 = 198;

		public static final int CONDITIONAL_EXPRESSION = 179;

		public static final int CONDITIONAL_EXPRESSION_2 = 193;

		public static final int CONDITIONAL_EXPRESSION_2_4 = 194;

		public static final int CONDITIONAL_OR_EXPRESSION = 192;

		public static final int CONDITIONAL_OR_EXPRESSION_2 = 196;

		public static final int CONSTRUCTOR_DECL = 108;

		public static final int CONSTRUCTOR_DECL_1 = 137;

		public static final int CONSTRUCTOR_DECL_4 = 138;

		public static final int CONTINUE_STATEMENT = 285;

		public static final int CONTINUE_STATEMENT_2 = 308;

		public static final int DO_STATEMENT = 282;

		public static final int ELEMENT_VALUE = 95;

		public static final int ELEMENT_VALUE_1 = 333;

		public static final int ELEMENT_VALUE_ARRAY_INITIALIZER = 334;

		public static final int ELEMENT_VALUE_ARRAY_INITIALIZER_2 = 335;

		public static final int ELEMENT_VALUE_ARRAY_INITIALIZER_3 = 337;

		public static final int ELEMENT_VALUE_LIST = 336;

		public static final int ELEMENT_VALUE_LIST_2 = 338;

		public static final int ELEMENT_VALUE_PAIR = 331;

		public static final int ELEMENT_VALUE_PAIR_LIST = 330;

		public static final int ELEMENT_VALUE_PAIR_LIST_2 = 332;

		public static final int EMPTY_STATEMENT = 277;

		public static final int ENUM_CONSTANT_DECL = 25;

		public static final int ENUM_CONSTANT_DECL_3 = 84;

		public static final int ENUM_CONSTANT_DECL_4 = 86;

		public static final int ENUM_CONSTANT_DECL_ENTRY = 24;

		public static final int ENUM_DECL = 60;

		public static final int ENUM_DECL_3 = 77;

		public static final int ENUM_DECL_5 = 78;

		public static final int ENUM_DECL_5_1 = 79;

		public static final int ENUM_DECL_5_1_2_2 = 80;

		public static final int ENUM_DECL_6 = 81;

		public static final int ENUM_DECL_7 = 82;

		public static final int EPILOG = 5;

		public static final int EQUALITY_EXPRESSION = 203;

		public static final int EQUALITY_EXPRESSION_2 = 206;

		public static final int EQUALITY_EXPRESSION_2_1 = 207;

		public static final int EXCLUSIVE_OR_EXPRESSION = 199;

		public static final int EXCLUSIVE_OR_EXPRESSION_2 = 202;

		public static final int EXPLICIT_CONSTRUCTOR_INVOCATION = 139;

		public static final int EXPLICIT_CONSTRUCTOR_INVOCATION_1 = 140;

		public static final int EXPLICIT_CONSTRUCTOR_INVOCATION_1_1_1 = 141;

		public static final int EXPLICIT_CONSTRUCTOR_INVOCATION_1_2_1 = 143;

		public static final int EXPLICIT_CONSTRUCTOR_INVOCATION_1_2_2 = 145;

		public static final int EXPRESSION = 35;

		public static final int EXPRESSION_1 = 176;

		public static final int EXPRESSION_ENTRY = 34;

		public static final int EXPRESSION_STATEMENT = 278;

		public static final int EXTENDS_LIST = 71;

		public static final int EXTENDS_LIST_2 = 73;

		public static final int EXTENDS_LIST_2_2_2 = 74;

		public static final int FIELD_ACCESS = 253;

		public static final int FIELD_DECL = 21;

		public static final int FIELD_DECL_ENTRY = 20;

		public static final int FORMAL_PARAMETER = 27;

		public static final int FORMAL_PARAMETERS = 124;

		public static final int FORMAL_PARAMETERS_2 = 129;

		public static final int FORMAL_PARAMETER_3 = 133;

		public static final int FORMAL_PARAMETER_4 = 134;

		public static final int FORMAL_PARAMETER_4_1_1 = 135;

		public static final int FORMAL_PARAMETER_ENTRY = 26;

		public static final int FORMAL_PARAMETER_LIST = 130;

		public static final int FORMAL_PARAMETER_LIST_1 = 131;

		public static final int FORMAL_PARAMETER_LIST_1_2_2 = 132;

		public static final int FOR_INIT = 300;

		public static final int FOR_INIT_1 = 304;

		public static final int FOR_STATEMENT = 283;

		public static final int FOR_STATEMENT_3 = 298;

		public static final int FOR_STATEMENT_3_2_1 = 299;

		public static final int FOR_STATEMENT_3_2_3 = 301;

		public static final int FOR_STATEMENT_3_2_5 = 302;

		public static final int FOR_UPDATE = 303;

		public static final int IF_STATEMENT = 280;

		public static final int IF_STATEMENT_6 = 297;

		public static final int IMPLEMENTS_LIST = 68;

		public static final int IMPLEMENTS_LIST_2 = 75;

		public static final int IMPLEMENTS_LIST_2_2_2 = 76;

		public static final int IMPORT_DECL = 7;

		public static final int IMPORT_DECLS = 45;

		public static final int IMPORT_DECLS_1 = 47;

		public static final int IMPORT_DECL_2 = 48;

		public static final int IMPORT_DECL_4 = 49;

		public static final int IMPORT_DECL_ENTRY = 6;

		public static final int INCLUSIVE_OR_EXPRESSION = 197;

		public static final int INCLUSIVE_OR_EXPRESSION_2 = 200;

		public static final int INFERRED_FORMAL_PARAMETER = 189;

		public static final int INFERRED_FORMAL_PARAMETER_LIST = 187;

		public static final int INFERRED_FORMAL_PARAMETER_LIST_2 = 190;

		public static final int INITIALIZER_DECL = 107;

		public static final int INSTANCE_OF_EXPRESSION = 205;

		public static final int INSTANCE_OF_EXPRESSION_2 = 209;

		public static final int LABELED_STATEMENT = 275;

		public static final int LAMBDA_BODY = 186;

		public static final int LAMBDA_BODY_1 = 188;

		public static final int LAMBDA_EXPRESSION = 178;

		public static final int LAMBDA_EXPRESSION_1 = 182;

		public static final int LAMBDA_EXPRESSION_WITHOUT_CAST = 184;

		public static final int LAMBDA_EXPRESSION_WITHOUT_CAST_1 = 185;

		public static final int LITERAL = 239;

		public static final int LITERAL_1 = 240;

		public static final int MARKER_ANNOTATION = 327;

		public static final int MEMBER_DECL_ENTRY = 10;

		public static final int METHOD_DECL = 19;

		public static final int METHOD_DECL_1 = 122;

		public static final int METHOD_DECL_6 = 125;

		public static final int METHOD_DECL_7 = 127;

		public static final int METHOD_DECL_ENTRY = 18;

		public static final int METHOD_INVOCATION = 252;

		public static final int METHOD_INVOCATION_1 = 259;

		public static final int METHOD_REFERENCE_SUFFIX = 254;

		public static final int METHOD_REFERENCE_SUFFIX_2 = 263;

		public static final int METHOD_REFERENCE_SUFFIX_3 = 264;

		public static final int MODIFIERS = 15;

		public static final int MODIFIERS_1 = 51;

		public static final int MODIFIERS_1_1 = 52;

		public static final int MODIFIERS_ENTRY = 14;

		public static final int MODIFIERS_NO_DEFAULT = 54;

		public static final int MODIFIERS_NO_DEFAULT_1 = 55;

		public static final int MODIFIERS_NO_DEFAULT_1_1 = 56;

		public static final int MULTIPLICATIVE_EXPRESSION = 218;

		public static final int MULTIPLICATIVE_EXPRESSION_2 = 222;

		public static final int MULTIPLICATIVE_EXPRESSION_2_1 = 223;

		public static final int NAME = 41;

		public static final int NAME_1 = 175;

		public static final int NAME_ENTRY = 40;

		public static final int NODE_LIST_VAR = 42;

		public static final int NODE_VAR = 43;

		public static final int NORMAL_ANNOTATION = 326;

		public static final int NORMAL_ANNOTATION_4 = 329;

		public static final int PACKAGE_DECL = 4;

		public static final int PACKAGE_DECL_ENTRY = 3;

		public static final int POSTFIX_EXPRESSION = 232;

		public static final int POSTFIX_EXPRESSION_2 = 234;

		public static final int POSTFIX_EXPRESSION_2_1 = 235;

		public static final int PREFIX_EXPRESSION = 225;

		public static final int PREFIX_EXPRESSION_1 = 228;

		public static final int PRIMARY_EXPRESSION = 233;

		public static final int PRIMARY_EXPRESSION_1 = 241;

		public static final int PRIMARY_EXPRESSION_WITHOUT_SUPER_SUFFIX = 144;

		public static final int PRIMARY_EXPRESSION_WITHOUT_SUPER_SUFFIX_2 = 247;

		public static final int PRIMARY_NO_NEW_ARRAY = 242;

		public static final int PRIMARY_NO_NEW_ARRAY_2 = 245;

		public static final int PRIMARY_PREFIX = 244;

		public static final int PRIMARY_PREFIX_1 = 249;

		public static final int PRIMARY_PREFIX_1_3_2 = 250;

		public static final int PRIMARY_PREFIX_1_3_2_1_2 = 251;

		public static final int PRIMARY_SUFFIX = 246;

		public static final int PRIMARY_SUFFIX_1 = 256;

		public static final int PRIMARY_SUFFIX_WITHOUT_SUPER = 248;

		public static final int PRIMARY_SUFFIX_WITHOUT_SUPER_1 = 257;

		public static final int PRIMARY_SUFFIX_WITHOUT_SUPER_1_1_2 = 258;

		public static final int PRIMITIVE_TYPE = 151;

		public static final int PRIMITIVE_TYPE_1 = 172;

		public static final int QUALIFIED_NAME = 39;

		public static final int QUALIFIED_NAME_2 = 174;

		public static final int QUALIFIED_NAME_ENTRY = 38;

		public static final int QUALIFIED_TYPE = 154;

		public static final int QUALIFIED_TYPE_2 = 159;

		public static final int QUALIFIED_TYPE_3 = 161;

		public static final int QUALIFIED_TYPE_3_4 = 162;

		public static final int REFERENCE_CAST_TYPE_REST = 183;

		public static final int REFERENCE_CAST_TYPE_REST_1 = 237;

		public static final int REFERENCE_CAST_TYPE_REST_1_1 = 238;

		public static final int REFERENCE_TYPE = 156;

		public static final int REFERENCE_TYPE_1 = 157;

		public static final int REFERENCE_TYPE_1_2_2 = 158;

		public static final int RELATIONAL_EXPRESSION = 208;

		public static final int RELATIONAL_EXPRESSION_2 = 211;

		public static final int RELATIONAL_EXPRESSION_2_1 = 212;

		public static final int RESOURCE_SPECIFICATION = 311;

		public static final int RESOURCE_SPECIFICATION_3 = 322;

		public static final int RESOURCE_SPECIFICATION_4 = 323;

		public static final int RESULT_TYPE = 123;

		public static final int RESULT_TYPE_1 = 173;

		public static final int RETURN_STATEMENT = 286;

		public static final int RETURN_STATEMENT_2 = 309;

		public static final int R_S_I_G_N_E_D_S_H_I_F_T = 217;

		public static final int R_U_N_S_I_G_N_E_D_S_H_I_F_T = 216;

		public static final int SHIFT_EXPRESSION = 210;

		public static final int SHIFT_EXPRESSION_2 = 214;

		public static final int SHIFT_EXPRESSION_2_1 = 215;

		public static final int SINGLE_ELEMENT_ANNOTATION = 328;

		public static final int STATEMENT = 273;

		public static final int STATEMENTS = 31;

		public static final int STATEMENTS_1 = 146;

		public static final int STATEMENTS_1_1 = 147;

		public static final int STATEMENTS_1_1_2_1 = 148;

		public static final int STATEMENTS_1_1_2_2 = 149;

		public static final int STATEMENTS_ENTRY = 30;

		public static final int STATEMENT_1 = 274;

		public static final int STATEMENT_EXPRESSION = 293;

		public static final int STATEMENT_EXPRESSION_LIST = 305;

		public static final int STATEMENT_EXPRESSION_LIST_2 = 306;

		public static final int SWITCH_ENTRY = 295;

		public static final int SWITCH_ENTRY_1 = 296;

		public static final int SWITCH_STATEMENT = 279;

		public static final int SWITCH_STATEMENT_6 = 294;

		public static final int SYNCHRONIZED_STATEMENT = 288;

		public static final int THROWS_CLAUSE = 126;

		public static final int THROWS_CLAUSE_3 = 136;

		public static final int THROW_STATEMENT = 287;

		public static final int TRY_STATEMENT = 289;

		public static final int TRY_STATEMENT_2 = 310;

		public static final int TRY_STATEMENT_2_1_3 = 312;

		public static final int TRY_STATEMENT_2_1_4 = 314;

		public static final int TRY_STATEMENT_2_2_2 = 315;

		public static final int TRY_STATEMENT_2_2_2_1_2 = 316;

		public static final int TYPE = 37;

		public static final int TYPE_1 = 150;

		public static final int TYPE_1_1_2 = 152;

		public static final int TYPE_1_2_2 = 155;

		public static final int TYPE_ARGUMENT = 166;

		public static final int TYPE_ARGUMENTS = 142;

		public static final int TYPE_ARGUMENTS_2 = 163;

		public static final int TYPE_ARGUMENTS_OR_DIAMOND = 160;

		public static final int TYPE_ARGUMENTS_OR_DIAMOND_2 = 165;

		public static final int TYPE_ARGUMENT_2 = 168;

		public static final int TYPE_ARGUMENT_LIST = 164;

		public static final int TYPE_ARGUMENT_LIST_2_2 = 167;

		public static final int TYPE_BOUNDS = 99;

		public static final int TYPE_BOUNDS_2 = 100;

		public static final int TYPE_BOUNDS_2_2_2 = 101;

		public static final int TYPE_DECL = 9;

		public static final int TYPE_DECLS = 46;

		public static final int TYPE_DECLS_1 = 50;

		public static final int TYPE_DECL_1 = 57;

		public static final int TYPE_DECL_1_2_2 = 58;

		public static final int TYPE_DECL_ENTRY = 8;

		public static final int TYPE_ENTRY = 36;

		public static final int TYPE_PARAMETER = 29;

		public static final int TYPE_PARAMETERS = 64;

		public static final int TYPE_PARAMETERS_2 = 96;

		public static final int TYPE_PARAMETERS_2_2_2 = 97;

		public static final int TYPE_PARAMETER_3 = 98;

		public static final int TYPE_PARAMETER_ENTRY = 28;

		public static final int UNARY_EXPRESSION = 221;

		public static final int UNARY_EXPRESSION_1 = 224;

		public static final int UNARY_EXPRESSION_1_2_1 = 226;

		public static final int UNARY_EXPRESSION_NOT_PLUS_MINUS = 227;

		public static final int UNARY_EXPRESSION_NOT_PLUS_MINUS_1 = 229;

		public static final int UNARY_EXPRESSION_NOT_PLUS_MINUS_1_1_1 = 230;

		public static final int VARIABLE_DECL = 110;

		public static final int VARIABLE_DECLARATOR = 111;

		public static final int VARIABLE_DECLARATORS = 109;

		public static final int VARIABLE_DECLARATORS_2 = 112;

		public static final int VARIABLE_DECLARATOR_2 = 114;

		public static final int VARIABLE_DECLARATOR_ID = 113;

		public static final int VARIABLE_DECL_EXPRESSION = 292;

		public static final int VARIABLE_INITIALIZER = 115;

		public static final int VARIABLE_INITIALIZER_1 = 117;

		public static final int WHILE_STATEMENT = 281;

		public static final int WILDCARD = 169;

		public static final int WILDCARD_2 = 170;

		public static final int WILDCARD_2_1 = 171;

		static final NonTerminal CompilationUnitEntry_1 = nonTerminal("CompilationUnitEntry_1", COMPILATION_UNIT);

		static final Sequence CompilationUnitEntry = sequence("CompilationUnitEntry", CompilationUnitEntry_1);

		static final NonTerminal PackageDeclEntry_1 = nonTerminal("PackageDeclEntry_1", PACKAGE_DECL);

		static final NonTerminal PackageDeclEntry_2 = nonTerminal("PackageDeclEntry_2", EPILOG);

		static final Sequence PackageDeclEntry = sequence("PackageDeclEntry", PackageDeclEntry_1, PackageDeclEntry_2);

		static final NonTerminal ImportDeclEntry_1 = nonTerminal("ImportDeclEntry_1", IMPORT_DECL);

		static final NonTerminal ImportDeclEntry_2 = nonTerminal("ImportDeclEntry_2", EPILOG);

		static final Sequence ImportDeclEntry = sequence("ImportDeclEntry", ImportDeclEntry_1, ImportDeclEntry_2);

		static final NonTerminal TypeDeclEntry_1 = nonTerminal("TypeDeclEntry_1", TYPE_DECL);

		static final NonTerminal TypeDeclEntry_2 = nonTerminal("TypeDeclEntry_2", EPILOG);

		static final Sequence TypeDeclEntry = sequence("TypeDeclEntry", TypeDeclEntry_1, TypeDeclEntry_2);

		static final NonTerminal MemberDeclEntry_1 = nonTerminal("MemberDeclEntry_1", CLASS_OR_INTERFACE_BODY_DECL);

		static final NonTerminal MemberDeclEntry_2 = nonTerminal("MemberDeclEntry_2", EPILOG);

		static final Sequence MemberDeclEntry = sequence("MemberDeclEntry", MemberDeclEntry_1, MemberDeclEntry_2);

		static final NonTerminal AnnotationMemberDeclEntry_1 = nonTerminal("AnnotationMemberDeclEntry_1", ANNOTATION_TYPE_BODY_DECL);

		static final NonTerminal AnnotationMemberDeclEntry_2 = nonTerminal("AnnotationMemberDeclEntry_2", EPILOG);

		static final Sequence AnnotationMemberDeclEntry = sequence("AnnotationMemberDeclEntry", AnnotationMemberDeclEntry_1, AnnotationMemberDeclEntry_2);

		static final NonTerminal ModifiersEntry_1 = nonTerminal("ModifiersEntry_1", MODIFIERS);

		static final NonTerminal ModifiersEntry_2 = nonTerminal("ModifiersEntry_2", EPILOG);

		static final Sequence ModifiersEntry = sequence("ModifiersEntry", ModifiersEntry_1, ModifiersEntry_2);

		static final NonTerminal AnnotationsEntry_1 = nonTerminal("AnnotationsEntry_1", ANNOTATIONS);

		static final NonTerminal AnnotationsEntry_2 = nonTerminal("AnnotationsEntry_2", EPILOG);

		static final Sequence AnnotationsEntry = sequence("AnnotationsEntry", AnnotationsEntry_1, AnnotationsEntry_2);

		static final NonTerminal MethodDeclEntry_1 = nonTerminal("MethodDeclEntry_1", MODIFIERS);

		static final NonTerminal MethodDeclEntry_2 = nonTerminal("MethodDeclEntry_2", METHOD_DECL);

		static final NonTerminal MethodDeclEntry_3 = nonTerminal("MethodDeclEntry_3", EPILOG);

		static final Sequence MethodDeclEntry = sequence("MethodDeclEntry", MethodDeclEntry_1, MethodDeclEntry_2, MethodDeclEntry_3);

		static final NonTerminal FieldDeclEntry_1 = nonTerminal("FieldDeclEntry_1", MODIFIERS);

		static final NonTerminal FieldDeclEntry_2 = nonTerminal("FieldDeclEntry_2", FIELD_DECL);

		static final NonTerminal FieldDeclEntry_3 = nonTerminal("FieldDeclEntry_3", EPILOG);

		static final Sequence FieldDeclEntry = sequence("FieldDeclEntry", FieldDeclEntry_1, FieldDeclEntry_2, FieldDeclEntry_3);

		static final NonTerminal AnnotationElementDeclEntry_1 = nonTerminal("AnnotationElementDeclEntry_1", MODIFIERS);

		static final NonTerminal AnnotationElementDeclEntry_2 = nonTerminal("AnnotationElementDeclEntry_2", ANNOTATION_TYPE_MEMBER_DECL);

		static final NonTerminal AnnotationElementDeclEntry_3 = nonTerminal("AnnotationElementDeclEntry_3", EPILOG);

		static final Sequence AnnotationElementDeclEntry = sequence("AnnotationElementDeclEntry", AnnotationElementDeclEntry_1, AnnotationElementDeclEntry_2, AnnotationElementDeclEntry_3);

		static final NonTerminal EnumConstantDeclEntry_1 = nonTerminal("EnumConstantDeclEntry_1", ENUM_CONSTANT_DECL);

		static final NonTerminal EnumConstantDeclEntry_2 = nonTerminal("EnumConstantDeclEntry_2", EPILOG);

		static final Sequence EnumConstantDeclEntry = sequence("EnumConstantDeclEntry", EnumConstantDeclEntry_1, EnumConstantDeclEntry_2);

		static final NonTerminal FormalParameterEntry_1 = nonTerminal("FormalParameterEntry_1", FORMAL_PARAMETER);

		static final NonTerminal FormalParameterEntry_2 = nonTerminal("FormalParameterEntry_2", EPILOG);

		static final Sequence FormalParameterEntry = sequence("FormalParameterEntry", FormalParameterEntry_1, FormalParameterEntry_2);

		static final NonTerminal TypeParameterEntry_1 = nonTerminal("TypeParameterEntry_1", TYPE_PARAMETER);

		static final NonTerminal TypeParameterEntry_2 = nonTerminal("TypeParameterEntry_2", EPILOG);

		static final Sequence TypeParameterEntry = sequence("TypeParameterEntry", TypeParameterEntry_1, TypeParameterEntry_2);

		static final NonTerminal StatementsEntry_1 = nonTerminal("StatementsEntry_1", STATEMENTS);

		static final NonTerminal StatementsEntry_2 = nonTerminal("StatementsEntry_2", EPILOG);

		static final Sequence StatementsEntry = sequence("StatementsEntry", StatementsEntry_1, StatementsEntry_2);

		static final NonTerminal BlockStatementEntry_1 = nonTerminal("BlockStatementEntry_1", BLOCK_STATEMENT);

		static final NonTerminal BlockStatementEntry_2 = nonTerminal("BlockStatementEntry_2", EPILOG);

		static final Sequence BlockStatementEntry = sequence("BlockStatementEntry", BlockStatementEntry_1, BlockStatementEntry_2);

		static final NonTerminal ExpressionEntry_1 = nonTerminal("ExpressionEntry_1", EXPRESSION);

		static final NonTerminal ExpressionEntry_2 = nonTerminal("ExpressionEntry_2", EPILOG);

		static final Sequence ExpressionEntry = sequence("ExpressionEntry", ExpressionEntry_1, ExpressionEntry_2);

		static final NonTerminal TypeEntry_1 = nonTerminal("TypeEntry_1", ANNOTATIONS);

		static final NonTerminal TypeEntry_2 = nonTerminal("TypeEntry_2", TYPE);

		static final NonTerminal TypeEntry_3 = nonTerminal("TypeEntry_3", EPILOG);

		static final Sequence TypeEntry = sequence("TypeEntry", TypeEntry_1, TypeEntry_2, TypeEntry_3);

		static final NonTerminal QualifiedNameEntry_1 = nonTerminal("QualifiedNameEntry_1", QUALIFIED_NAME);

		static final NonTerminal QualifiedNameEntry_2 = nonTerminal("QualifiedNameEntry_2", EPILOG);

		static final Sequence QualifiedNameEntry = sequence("QualifiedNameEntry", QualifiedNameEntry_1, QualifiedNameEntry_2);

		static final NonTerminal NameEntry_1 = nonTerminal("NameEntry_1", NAME);

		static final NonTerminal NameEntry_2 = nonTerminal("NameEntry_2", EPILOG);

		static final Sequence NameEntry = sequence("NameEntry", NameEntry_1, NameEntry_2);

		static final Sequence Epilog = sequence("Epilog", terminal("Epilog_1", TokenType.EOF));

		static final Sequence NodeListVar = sequence("NodeListVar", terminal("NodeListVar_1", TokenType.NODE_LIST_VARIABLE));

		static final Sequence NodeVar = sequence("NodeVar", terminal("NodeVar_1", TokenType.NODE_VARIABLE));

		static final NonTerminal CompilationUnit_1_1 = nonTerminal("CompilationUnit_1_1", PACKAGE_DECL);

		static final ZeroOrOne CompilationUnit_1 = zeroOrOne("CompilationUnit_1", CompilationUnit_1_1);

		static final NonTerminal CompilationUnit_2 = nonTerminal("CompilationUnit_2", IMPORT_DECLS);

		static final NonTerminal CompilationUnit_3 = nonTerminal("CompilationUnit_3", TYPE_DECLS);

		static final NonTerminal CompilationUnit_4 = nonTerminal("CompilationUnit_4", EPILOG);

		static final Sequence CompilationUnit = sequence("CompilationUnit", CompilationUnit_1, CompilationUnit_2, CompilationUnit_3, CompilationUnit_4);

		static final NonTerminal PackageDecl_1 = nonTerminal("PackageDecl_1", ANNOTATIONS);

		static final NonTerminal PackageDecl_3 = nonTerminal("PackageDecl_3", QUALIFIED_NAME);

		static final Sequence PackageDecl = sequence("PackageDecl", PackageDecl_1, terminal("PackageDecl_2", TokenType.PACKAGE), PackageDecl_3, terminal("PackageDecl_4", TokenType.SEMICOLON));

		static final NonTerminal ImportDecls_1_1 = nonTerminal("ImportDecls_1_1", IMPORT_DECL);

		static final ZeroOrMore ImportDecls_1 = zeroOrMore("ImportDecls_1", ImportDecls_1_1);

		static final Sequence ImportDecls = sequence("ImportDecls", ImportDecls_1);

		static final ZeroOrOne ImportDecl_2 = zeroOrOne("ImportDecl_2", terminal("ImportDecl_2_1", TokenType.STATIC));

		static final NonTerminal ImportDecl_3 = nonTerminal("ImportDecl_3", QUALIFIED_NAME);

		static final ZeroOrOne ImportDecl_4 = zeroOrOne("ImportDecl_4", sequence("ImportDecl_4", terminal("ImportDecl_4_1", TokenType.DOT), terminal("ImportDecl_4_2", TokenType.STAR)));

		static final Sequence ImportDecl = sequence("ImportDecl", terminal("ImportDecl_1", TokenType.IMPORT), ImportDecl_2, ImportDecl_3, ImportDecl_4, terminal("ImportDecl_5", TokenType.SEMICOLON));

		static final NonTerminal TypeDecls_1_1 = nonTerminal("TypeDecls_1_1", TYPE_DECL);

		static final ZeroOrMore TypeDecls_1 = zeroOrMore("TypeDecls_1", TypeDecls_1_1);

		static final Sequence TypeDecls = sequence("TypeDecls", TypeDecls_1);

		static final NonTerminal Modifiers_1_1_13_1 = nonTerminal("Modifiers_1_1_13_1", ANNOTATION);

		static final Choice Modifiers_1_1 = choice("Modifiers_1_1", sequence("Modifiers_1_1_1", terminal("Modifiers_1_1_1_1", TokenType.PUBLIC)), sequence("Modifiers_1_1_2", terminal("Modifiers_1_1_2_1", TokenType.PROTECTED)), sequence("Modifiers_1_1_3", terminal("Modifiers_1_1_3_1", TokenType.PRIVATE)), sequence("Modifiers_1_1_4", terminal("Modifiers_1_1_4_1", TokenType.ABSTRACT)), sequence("Modifiers_1_1_5", terminal("Modifiers_1_1_5_1", TokenType.DEFAULT)), sequence("Modifiers_1_1_6", terminal("Modifiers_1_1_6_1", TokenType.STATIC)), sequence("Modifiers_1_1_7", terminal("Modifiers_1_1_7_1", TokenType.FINAL)), sequence("Modifiers_1_1_8", terminal("Modifiers_1_1_8_1", TokenType.TRANSIENT)), sequence("Modifiers_1_1_9", terminal("Modifiers_1_1_9_1", TokenType.VOLATILE)), sequence("Modifiers_1_1_10", terminal("Modifiers_1_1_10_1", TokenType.SYNCHRONIZED)), sequence("Modifiers_1_1_11", terminal("Modifiers_1_1_11_1", TokenType.NATIVE)), sequence("Modifiers_1_1_12", terminal("Modifiers_1_1_12_1", TokenType.STRICTFP)), sequence("Modifiers_1_1_13", Modifiers_1_1_13_1));

		static final ZeroOrMore Modifiers_1 = zeroOrMore("Modifiers_1", Modifiers_1_1);

		static final Sequence Modifiers = sequence("Modifiers", Modifiers_1);

		static final NonTerminal ModifiersNoDefault_1_1_12_1 = nonTerminal("ModifiersNoDefault_1_1_12_1", ANNOTATION);

		static final Choice ModifiersNoDefault_1_1 = choice("ModifiersNoDefault_1_1", sequence("ModifiersNoDefault_1_1_1", terminal("ModifiersNoDefault_1_1_1_1", TokenType.PUBLIC)), sequence("ModifiersNoDefault_1_1_2", terminal("ModifiersNoDefault_1_1_2_1", TokenType.PROTECTED)), sequence("ModifiersNoDefault_1_1_3", terminal("ModifiersNoDefault_1_1_3_1", TokenType.PRIVATE)), sequence("ModifiersNoDefault_1_1_4", terminal("ModifiersNoDefault_1_1_4_1", TokenType.ABSTRACT)), sequence("ModifiersNoDefault_1_1_5", terminal("ModifiersNoDefault_1_1_5_1", TokenType.STATIC)), sequence("ModifiersNoDefault_1_1_6", terminal("ModifiersNoDefault_1_1_6_1", TokenType.FINAL)), sequence("ModifiersNoDefault_1_1_7", terminal("ModifiersNoDefault_1_1_7_1", TokenType.TRANSIENT)), sequence("ModifiersNoDefault_1_1_8", terminal("ModifiersNoDefault_1_1_8_1", TokenType.VOLATILE)), sequence("ModifiersNoDefault_1_1_9", terminal("ModifiersNoDefault_1_1_9_1", TokenType.SYNCHRONIZED)), sequence("ModifiersNoDefault_1_1_10", terminal("ModifiersNoDefault_1_1_10_1", TokenType.NATIVE)), sequence("ModifiersNoDefault_1_1_11", terminal("ModifiersNoDefault_1_1_11_1", TokenType.STRICTFP)), sequence("ModifiersNoDefault_1_1_12", ModifiersNoDefault_1_1_12_1));

		static final ZeroOrMore ModifiersNoDefault_1 = zeroOrMore("ModifiersNoDefault_1", ModifiersNoDefault_1_1);

		static final Sequence ModifiersNoDefault = sequence("ModifiersNoDefault", ModifiersNoDefault_1);

		static final NonTerminal TypeDecl_1_2_1 = nonTerminal("TypeDecl_1_2_1", MODIFIERS);

		static final NonTerminal TypeDecl_1_2_2_1 = nonTerminal("TypeDecl_1_2_2_1", CLASS_OR_INTERFACE_DECL);

		static final NonTerminal TypeDecl_1_2_2_2 = nonTerminal("TypeDecl_1_2_2_2", ENUM_DECL);

		static final NonTerminal TypeDecl_1_2_2_3 = nonTerminal("TypeDecl_1_2_2_3", ANNOTATION_TYPE_DECL);

		static final Choice TypeDecl_1_2_2 = choice("TypeDecl_1_2_2", TypeDecl_1_2_2_1, TypeDecl_1_2_2_2, TypeDecl_1_2_2_3);

		static final Choice TypeDecl_1 = choice("TypeDecl_1", sequence("TypeDecl_1_1", terminal("TypeDecl_1_1_1", TokenType.SEMICOLON)), sequence("TypeDecl_1_2", TypeDecl_1_2_1, TypeDecl_1_2_2));

		static final Sequence TypeDecl = sequence("TypeDecl", TypeDecl_1);

		static final NonTerminal ClassOrInterfaceDecl_1_1_2 = nonTerminal("ClassOrInterfaceDecl_1_1_2", NAME);

		static final NonTerminal ClassOrInterfaceDecl_1_1_3_1 = nonTerminal("ClassOrInterfaceDecl_1_1_3_1", TYPE_PARAMETERS);

		static final ZeroOrOne ClassOrInterfaceDecl_1_1_3 = zeroOrOne("ClassOrInterfaceDecl_1_1_3", ClassOrInterfaceDecl_1_1_3_1);

		static final NonTerminal ClassOrInterfaceDecl_1_1_4_2 = nonTerminal("ClassOrInterfaceDecl_1_1_4_2", ANNOTATED_QUALIFIED_TYPE);

		static final ZeroOrOne ClassOrInterfaceDecl_1_1_4 = zeroOrOne("ClassOrInterfaceDecl_1_1_4", sequence("ClassOrInterfaceDecl_1_1_4", terminal("ClassOrInterfaceDecl_1_1_4_1", TokenType.EXTENDS), ClassOrInterfaceDecl_1_1_4_2));

		static final NonTerminal ClassOrInterfaceDecl_1_1_5_1 = nonTerminal("ClassOrInterfaceDecl_1_1_5_1", IMPLEMENTS_LIST);

		static final ZeroOrOne ClassOrInterfaceDecl_1_1_5 = zeroOrOne("ClassOrInterfaceDecl_1_1_5", ClassOrInterfaceDecl_1_1_5_1);

		static final NonTerminal ClassOrInterfaceDecl_1_2_2 = nonTerminal("ClassOrInterfaceDecl_1_2_2", NAME);

		static final NonTerminal ClassOrInterfaceDecl_1_2_3_1 = nonTerminal("ClassOrInterfaceDecl_1_2_3_1", TYPE_PARAMETERS);

		static final ZeroOrOne ClassOrInterfaceDecl_1_2_3 = zeroOrOne("ClassOrInterfaceDecl_1_2_3", ClassOrInterfaceDecl_1_2_3_1);

		static final NonTerminal ClassOrInterfaceDecl_1_2_4_1 = nonTerminal("ClassOrInterfaceDecl_1_2_4_1", EXTENDS_LIST);

		static final ZeroOrOne ClassOrInterfaceDecl_1_2_4 = zeroOrOne("ClassOrInterfaceDecl_1_2_4", ClassOrInterfaceDecl_1_2_4_1);

		static final Choice ClassOrInterfaceDecl_1 = choice("ClassOrInterfaceDecl_1", sequence("ClassOrInterfaceDecl_1_1", terminal("ClassOrInterfaceDecl_1_1_1", TokenType.CLASS), ClassOrInterfaceDecl_1_1_2, ClassOrInterfaceDecl_1_1_3, ClassOrInterfaceDecl_1_1_4, ClassOrInterfaceDecl_1_1_5), sequence("ClassOrInterfaceDecl_1_2", terminal("ClassOrInterfaceDecl_1_2_1", TokenType.INTERFACE), ClassOrInterfaceDecl_1_2_2, ClassOrInterfaceDecl_1_2_3, ClassOrInterfaceDecl_1_2_4));

		static final NonTerminal ClassOrInterfaceDecl_2 = nonTerminal("ClassOrInterfaceDecl_2", CLASS_OR_INTERFACE_BODY);

		static final Sequence ClassOrInterfaceDecl = sequence("ClassOrInterfaceDecl", ClassOrInterfaceDecl_1, ClassOrInterfaceDecl_2);

		static final NonTerminal ExtendsList_2_1_1 = nonTerminal("ExtendsList_2_1_1", NODE_LIST_VAR);

		static final NonTerminal ExtendsList_2_2_1 = nonTerminal("ExtendsList_2_2_1", ANNOTATED_QUALIFIED_TYPE);

		static final NonTerminal ExtendsList_2_2_2_2 = nonTerminal("ExtendsList_2_2_2_2", ANNOTATED_QUALIFIED_TYPE);

		static final ZeroOrMore ExtendsList_2_2_2 = zeroOrMore("ExtendsList_2_2_2", sequence("ExtendsList_2_2_2", terminal("ExtendsList_2_2_2_1", TokenType.COMMA), ExtendsList_2_2_2_2));

		static final Choice ExtendsList_2 = choice("ExtendsList_2", sequence("ExtendsList_2_1", ExtendsList_2_1_1), sequence("ExtendsList_2_2", ExtendsList_2_2_1, ExtendsList_2_2_2));

		static final Sequence ExtendsList = sequence("ExtendsList", terminal("ExtendsList_1", TokenType.EXTENDS), ExtendsList_2);

		static final NonTerminal ImplementsList_2_1_1 = nonTerminal("ImplementsList_2_1_1", NODE_LIST_VAR);

		static final NonTerminal ImplementsList_2_2_1 = nonTerminal("ImplementsList_2_2_1", ANNOTATED_QUALIFIED_TYPE);

		static final NonTerminal ImplementsList_2_2_2_2 = nonTerminal("ImplementsList_2_2_2_2", ANNOTATED_QUALIFIED_TYPE);

		static final ZeroOrMore ImplementsList_2_2_2 = zeroOrMore("ImplementsList_2_2_2", sequence("ImplementsList_2_2_2", terminal("ImplementsList_2_2_2_1", TokenType.COMMA), ImplementsList_2_2_2_2));

		static final Choice ImplementsList_2 = choice("ImplementsList_2", sequence("ImplementsList_2_1", ImplementsList_2_1_1), sequence("ImplementsList_2_2", ImplementsList_2_2_1, ImplementsList_2_2_2));

		static final Sequence ImplementsList = sequence("ImplementsList", terminal("ImplementsList_1", TokenType.IMPLEMENTS), ImplementsList_2);

		static final NonTerminal EnumDecl_2 = nonTerminal("EnumDecl_2", NAME);

		static final NonTerminal EnumDecl_3_1 = nonTerminal("EnumDecl_3_1", IMPLEMENTS_LIST);

		static final ZeroOrOne EnumDecl_3 = zeroOrOne("EnumDecl_3", EnumDecl_3_1);

		static final NonTerminal EnumDecl_5_1_1_1 = nonTerminal("EnumDecl_5_1_1_1", NODE_LIST_VAR);

		static final NonTerminal EnumDecl_5_1_2_1 = nonTerminal("EnumDecl_5_1_2_1", ENUM_CONSTANT_DECL);

		static final NonTerminal EnumDecl_5_1_2_2_2 = nonTerminal("EnumDecl_5_1_2_2_2", ENUM_CONSTANT_DECL);

		static final ZeroOrMore EnumDecl_5_1_2_2 = zeroOrMore("EnumDecl_5_1_2_2", sequence("EnumDecl_5_1_2_2", terminal("EnumDecl_5_1_2_2_1", TokenType.COMMA), EnumDecl_5_1_2_2_2));

		static final Choice EnumDecl_5_1 = choice("EnumDecl_5_1", sequence("EnumDecl_5_1_1", EnumDecl_5_1_1_1), sequence("EnumDecl_5_1_2", EnumDecl_5_1_2_1, EnumDecl_5_1_2_2));

		static final ZeroOrOne EnumDecl_5 = zeroOrOne("EnumDecl_5", EnumDecl_5_1);

		static final ZeroOrOne EnumDecl_6 = zeroOrOne("EnumDecl_6", terminal("EnumDecl_6_1", TokenType.COMMA));

		static final NonTerminal EnumDecl_7_2 = nonTerminal("EnumDecl_7_2", CLASS_OR_INTERFACE_BODY_DECLS);

		static final ZeroOrOne EnumDecl_7 = zeroOrOne("EnumDecl_7", sequence("EnumDecl_7", terminal("EnumDecl_7_1", TokenType.SEMICOLON), EnumDecl_7_2));

		static final Sequence EnumDecl = sequence("EnumDecl", terminal("EnumDecl_1", TokenType.ENUM), EnumDecl_2, EnumDecl_3, terminal("EnumDecl_4", TokenType.LBRACE), EnumDecl_5, EnumDecl_6, EnumDecl_7, terminal("EnumDecl_8", TokenType.RBRACE));

		static final NonTerminal EnumConstantDecl_1 = nonTerminal("EnumConstantDecl_1", MODIFIERS);

		static final NonTerminal EnumConstantDecl_2 = nonTerminal("EnumConstantDecl_2", NAME);

		static final NonTerminal EnumConstantDecl_3_1 = nonTerminal("EnumConstantDecl_3_1", ARGUMENTS);

		static final ZeroOrOne EnumConstantDecl_3 = zeroOrOne("EnumConstantDecl_3", EnumConstantDecl_3_1);

		static final NonTerminal EnumConstantDecl_4_1 = nonTerminal("EnumConstantDecl_4_1", CLASS_OR_INTERFACE_BODY);

		static final ZeroOrOne EnumConstantDecl_4 = zeroOrOne("EnumConstantDecl_4", EnumConstantDecl_4_1);

		static final Sequence EnumConstantDecl = sequence("EnumConstantDecl", EnumConstantDecl_1, EnumConstantDecl_2, EnumConstantDecl_3, EnumConstantDecl_4);

		static final NonTerminal AnnotationTypeDecl_3 = nonTerminal("AnnotationTypeDecl_3", NAME);

		static final NonTerminal AnnotationTypeDecl_4 = nonTerminal("AnnotationTypeDecl_4", ANNOTATION_TYPE_BODY);

		static final Sequence AnnotationTypeDecl = sequence("AnnotationTypeDecl", terminal("AnnotationTypeDecl_1", TokenType.AT), terminal("AnnotationTypeDecl_2", TokenType.INTERFACE), AnnotationTypeDecl_3, AnnotationTypeDecl_4);

		static final NonTerminal AnnotationTypeBody_2_1_1_1 = nonTerminal("AnnotationTypeBody_2_1_1_1", NODE_LIST_VAR);

		static final NonTerminal AnnotationTypeBody_2_1_2_1 = nonTerminal("AnnotationTypeBody_2_1_2_1", ANNOTATION_TYPE_BODY_DECL);

		static final OneOrMore AnnotationTypeBody_2_1_2 = oneOrMore("AnnotationTypeBody_2_1_2", AnnotationTypeBody_2_1_2_1);

		static final Choice AnnotationTypeBody_2_1 = choice("AnnotationTypeBody_2_1", sequence("AnnotationTypeBody_2_1_1", AnnotationTypeBody_2_1_1_1), AnnotationTypeBody_2_1_2);

		static final ZeroOrOne AnnotationTypeBody_2 = zeroOrOne("AnnotationTypeBody_2", AnnotationTypeBody_2_1);

		static final Sequence AnnotationTypeBody = sequence("AnnotationTypeBody", terminal("AnnotationTypeBody_1", TokenType.LBRACE), AnnotationTypeBody_2, terminal("AnnotationTypeBody_3", TokenType.RBRACE));

		static final NonTerminal AnnotationTypeBodyDecl_1_2_1 = nonTerminal("AnnotationTypeBodyDecl_1_2_1", MODIFIERS);

		static final NonTerminal AnnotationTypeBodyDecl_1_2_2_1 = nonTerminal("AnnotationTypeBodyDecl_1_2_2_1", ANNOTATION_TYPE_MEMBER_DECL);

		static final NonTerminal AnnotationTypeBodyDecl_1_2_2_2 = nonTerminal("AnnotationTypeBodyDecl_1_2_2_2", CLASS_OR_INTERFACE_DECL);

		static final NonTerminal AnnotationTypeBodyDecl_1_2_2_3 = nonTerminal("AnnotationTypeBodyDecl_1_2_2_3", ENUM_DECL);

		static final NonTerminal AnnotationTypeBodyDecl_1_2_2_4 = nonTerminal("AnnotationTypeBodyDecl_1_2_2_4", ANNOTATION_TYPE_DECL);

		static final NonTerminal AnnotationTypeBodyDecl_1_2_2_5 = nonTerminal("AnnotationTypeBodyDecl_1_2_2_5", FIELD_DECL);

		static final Choice AnnotationTypeBodyDecl_1_2_2 = choice("AnnotationTypeBodyDecl_1_2_2", AnnotationTypeBodyDecl_1_2_2_1, AnnotationTypeBodyDecl_1_2_2_2, AnnotationTypeBodyDecl_1_2_2_3, AnnotationTypeBodyDecl_1_2_2_4, AnnotationTypeBodyDecl_1_2_2_5);

		static final Choice AnnotationTypeBodyDecl_1 = choice("AnnotationTypeBodyDecl_1", sequence("AnnotationTypeBodyDecl_1_1", terminal("AnnotationTypeBodyDecl_1_1_1", TokenType.SEMICOLON)), sequence("AnnotationTypeBodyDecl_1_2", AnnotationTypeBodyDecl_1_2_1, AnnotationTypeBodyDecl_1_2_2));

		static final Sequence AnnotationTypeBodyDecl = sequence("AnnotationTypeBodyDecl", AnnotationTypeBodyDecl_1);

		static final NonTerminal AnnotationTypeMemberDecl_1 = nonTerminal("AnnotationTypeMemberDecl_1", TYPE);

		static final NonTerminal AnnotationTypeMemberDecl_2 = nonTerminal("AnnotationTypeMemberDecl_2", NAME);

		static final NonTerminal AnnotationTypeMemberDecl_5 = nonTerminal("AnnotationTypeMemberDecl_5", ARRAY_DIMS);

		static final NonTerminal AnnotationTypeMemberDecl_6_2 = nonTerminal("AnnotationTypeMemberDecl_6_2", ELEMENT_VALUE);

		static final ZeroOrOne AnnotationTypeMemberDecl_6 = zeroOrOne("AnnotationTypeMemberDecl_6", sequence("AnnotationTypeMemberDecl_6", terminal("AnnotationTypeMemberDecl_6_1", TokenType.DEFAULT), AnnotationTypeMemberDecl_6_2));

		static final Sequence AnnotationTypeMemberDecl = sequence("AnnotationTypeMemberDecl", AnnotationTypeMemberDecl_1, AnnotationTypeMemberDecl_2, terminal("AnnotationTypeMemberDecl_3", TokenType.LPAREN), terminal("AnnotationTypeMemberDecl_4", TokenType.RPAREN), AnnotationTypeMemberDecl_5, AnnotationTypeMemberDecl_6, terminal("AnnotationTypeMemberDecl_7", TokenType.SEMICOLON));

		static final NonTerminal TypeParameters_2_1_1 = nonTerminal("TypeParameters_2_1_1", NODE_LIST_VAR);

		static final NonTerminal TypeParameters_2_2_1 = nonTerminal("TypeParameters_2_2_1", TYPE_PARAMETER);

		static final NonTerminal TypeParameters_2_2_2_2 = nonTerminal("TypeParameters_2_2_2_2", TYPE_PARAMETER);

		static final ZeroOrMore TypeParameters_2_2_2 = zeroOrMore("TypeParameters_2_2_2", sequence("TypeParameters_2_2_2", terminal("TypeParameters_2_2_2_1", TokenType.COMMA), TypeParameters_2_2_2_2));

		static final Choice TypeParameters_2 = choice("TypeParameters_2", sequence("TypeParameters_2_1", TypeParameters_2_1_1), sequence("TypeParameters_2_2", TypeParameters_2_2_1, TypeParameters_2_2_2));

		static final Sequence TypeParameters = sequence("TypeParameters", terminal("TypeParameters_1", TokenType.LT), TypeParameters_2, terminal("TypeParameters_3", TokenType.GT));

		static final NonTerminal TypeParameter_1 = nonTerminal("TypeParameter_1", ANNOTATIONS);

		static final NonTerminal TypeParameter_2 = nonTerminal("TypeParameter_2", NAME);

		static final NonTerminal TypeParameter_3_1 = nonTerminal("TypeParameter_3_1", TYPE_BOUNDS);

		static final ZeroOrOne TypeParameter_3 = zeroOrOne("TypeParameter_3", TypeParameter_3_1);

		static final Sequence TypeParameter = sequence("TypeParameter", TypeParameter_1, TypeParameter_2, TypeParameter_3);

		static final NonTerminal TypeBounds_2_1_1 = nonTerminal("TypeBounds_2_1_1", NODE_LIST_VAR);

		static final NonTerminal TypeBounds_2_2_1 = nonTerminal("TypeBounds_2_2_1", ANNOTATED_QUALIFIED_TYPE);

		static final NonTerminal TypeBounds_2_2_2_2 = nonTerminal("TypeBounds_2_2_2_2", ANNOTATED_QUALIFIED_TYPE);

		static final ZeroOrMore TypeBounds_2_2_2 = zeroOrMore("TypeBounds_2_2_2", sequence("TypeBounds_2_2_2", terminal("TypeBounds_2_2_2_1", TokenType.BIT_AND), TypeBounds_2_2_2_2));

		static final Choice TypeBounds_2 = choice("TypeBounds_2", sequence("TypeBounds_2_1", TypeBounds_2_1_1), sequence("TypeBounds_2_2", TypeBounds_2_2_1, TypeBounds_2_2_2));

		static final Sequence TypeBounds = sequence("TypeBounds", terminal("TypeBounds_1", TokenType.EXTENDS), TypeBounds_2);

		static final NonTerminal ClassOrInterfaceBody_2 = nonTerminal("ClassOrInterfaceBody_2", CLASS_OR_INTERFACE_BODY_DECLS);

		static final Sequence ClassOrInterfaceBody = sequence("ClassOrInterfaceBody", terminal("ClassOrInterfaceBody_1", TokenType.LBRACE), ClassOrInterfaceBody_2, terminal("ClassOrInterfaceBody_3", TokenType.RBRACE));

		static final NonTerminal ClassOrInterfaceBodyDecls_1_1_1_1 = nonTerminal("ClassOrInterfaceBodyDecls_1_1_1_1", NODE_LIST_VAR);

		static final NonTerminal ClassOrInterfaceBodyDecls_1_1_2_1 = nonTerminal("ClassOrInterfaceBodyDecls_1_1_2_1", CLASS_OR_INTERFACE_BODY_DECL);

		static final OneOrMore ClassOrInterfaceBodyDecls_1_1_2 = oneOrMore("ClassOrInterfaceBodyDecls_1_1_2", ClassOrInterfaceBodyDecls_1_1_2_1);

		static final Choice ClassOrInterfaceBodyDecls_1_1 = choice("ClassOrInterfaceBodyDecls_1_1", sequence("ClassOrInterfaceBodyDecls_1_1_1", ClassOrInterfaceBodyDecls_1_1_1_1), ClassOrInterfaceBodyDecls_1_1_2);

		static final ZeroOrOne ClassOrInterfaceBodyDecls_1 = zeroOrOne("ClassOrInterfaceBodyDecls_1", ClassOrInterfaceBodyDecls_1_1);

		static final Sequence ClassOrInterfaceBodyDecls = sequence("ClassOrInterfaceBodyDecls", ClassOrInterfaceBodyDecls_1);

		static final NonTerminal ClassOrInterfaceBodyDecl_1_2_1 = nonTerminal("ClassOrInterfaceBodyDecl_1_2_1", MODIFIERS);

		static final NonTerminal ClassOrInterfaceBodyDecl_1_2_2_1_1 = nonTerminal("ClassOrInterfaceBodyDecl_1_2_2_1_1", INITIALIZER_DECL);

		static final NonTerminal ClassOrInterfaceBodyDecl_1_2_2_2 = nonTerminal("ClassOrInterfaceBodyDecl_1_2_2_2", CLASS_OR_INTERFACE_DECL);

		static final NonTerminal ClassOrInterfaceBodyDecl_1_2_2_3 = nonTerminal("ClassOrInterfaceBodyDecl_1_2_2_3", ENUM_DECL);

		static final NonTerminal ClassOrInterfaceBodyDecl_1_2_2_4 = nonTerminal("ClassOrInterfaceBodyDecl_1_2_2_4", ANNOTATION_TYPE_DECL);

		static final NonTerminal ClassOrInterfaceBodyDecl_1_2_2_5_1 = nonTerminal("ClassOrInterfaceBodyDecl_1_2_2_5_1", CONSTRUCTOR_DECL);

		static final NonTerminal ClassOrInterfaceBodyDecl_1_2_2_6_1 = nonTerminal("ClassOrInterfaceBodyDecl_1_2_2_6_1", FIELD_DECL);

		static final NonTerminal ClassOrInterfaceBodyDecl_1_2_2_7 = nonTerminal("ClassOrInterfaceBodyDecl_1_2_2_7", METHOD_DECL);

		static final Choice ClassOrInterfaceBodyDecl_1_2_2 = choice("ClassOrInterfaceBodyDecl_1_2_2", sequence("ClassOrInterfaceBodyDecl_1_2_2_1", ClassOrInterfaceBodyDecl_1_2_2_1_1), ClassOrInterfaceBodyDecl_1_2_2_2, ClassOrInterfaceBodyDecl_1_2_2_3, ClassOrInterfaceBodyDecl_1_2_2_4, sequence("ClassOrInterfaceBodyDecl_1_2_2_5", ClassOrInterfaceBodyDecl_1_2_2_5_1), sequence("ClassOrInterfaceBodyDecl_1_2_2_6", ClassOrInterfaceBodyDecl_1_2_2_6_1), ClassOrInterfaceBodyDecl_1_2_2_7);

		static final Choice ClassOrInterfaceBodyDecl_1 = choice("ClassOrInterfaceBodyDecl_1", sequence("ClassOrInterfaceBodyDecl_1_1", terminal("ClassOrInterfaceBodyDecl_1_1_1", TokenType.SEMICOLON)), sequence("ClassOrInterfaceBodyDecl_1_2", ClassOrInterfaceBodyDecl_1_2_1, ClassOrInterfaceBodyDecl_1_2_2));

		static final Sequence ClassOrInterfaceBodyDecl = sequence("ClassOrInterfaceBodyDecl", ClassOrInterfaceBodyDecl_1);

		static final NonTerminal FieldDecl_1 = nonTerminal("FieldDecl_1", TYPE);

		static final NonTerminal FieldDecl_2 = nonTerminal("FieldDecl_2", VARIABLE_DECLARATORS);

		static final Sequence FieldDecl = sequence("FieldDecl", FieldDecl_1, FieldDecl_2, terminal("FieldDecl_3", TokenType.SEMICOLON));

		static final NonTerminal VariableDecl_1 = nonTerminal("VariableDecl_1", TYPE);

		static final NonTerminal VariableDecl_2 = nonTerminal("VariableDecl_2", VARIABLE_DECLARATORS);

		static final Sequence VariableDecl = sequence("VariableDecl", VariableDecl_1, VariableDecl_2);

		static final NonTerminal VariableDeclarators_1 = nonTerminal("VariableDeclarators_1", VARIABLE_DECLARATOR);

		static final NonTerminal VariableDeclarators_2_2 = nonTerminal("VariableDeclarators_2_2", VARIABLE_DECLARATOR);

		static final ZeroOrMore VariableDeclarators_2 = zeroOrMore("VariableDeclarators_2", sequence("VariableDeclarators_2", terminal("VariableDeclarators_2_1", TokenType.COMMA), VariableDeclarators_2_2));

		static final Sequence VariableDeclarators = sequence("VariableDeclarators", VariableDeclarators_1, VariableDeclarators_2);

		static final NonTerminal VariableDeclarator_1 = nonTerminal("VariableDeclarator_1", VARIABLE_DECLARATOR_ID);

		static final NonTerminal VariableDeclarator_2_2 = nonTerminal("VariableDeclarator_2_2", VARIABLE_INITIALIZER);

		static final ZeroOrOne VariableDeclarator_2 = zeroOrOne("VariableDeclarator_2", sequence("VariableDeclarator_2", terminal("VariableDeclarator_2_1", TokenType.ASSIGN), VariableDeclarator_2_2));

		static final Sequence VariableDeclarator = sequence("VariableDeclarator", VariableDeclarator_1, VariableDeclarator_2);

		static final NonTerminal VariableDeclaratorId_1 = nonTerminal("VariableDeclaratorId_1", NAME);

		static final NonTerminal VariableDeclaratorId_2 = nonTerminal("VariableDeclaratorId_2", ARRAY_DIMS);

		static final Sequence VariableDeclaratorId = sequence("VariableDeclaratorId", VariableDeclaratorId_1, VariableDeclaratorId_2);

		static final NonTerminal ArrayDims_1_1 = nonTerminal("ArrayDims_1_1", ANNOTATIONS);

		static final ZeroOrMore ArrayDims_1 = zeroOrMore("ArrayDims_1", sequence("ArrayDims_1", ArrayDims_1_1, terminal("ArrayDims_1_2", TokenType.LBRACKET), terminal("ArrayDims_1_3", TokenType.RBRACKET)));

		static final Sequence ArrayDims = sequence("ArrayDims", ArrayDims_1);

		static final NonTerminal VariableInitializer_1_1 = nonTerminal("VariableInitializer_1_1", ARRAY_INITIALIZER);

		static final NonTerminal VariableInitializer_1_2 = nonTerminal("VariableInitializer_1_2", EXPRESSION);

		static final Choice VariableInitializer_1 = choice("VariableInitializer_1", VariableInitializer_1_1, VariableInitializer_1_2);

		static final Sequence VariableInitializer = sequence("VariableInitializer", VariableInitializer_1);

		static final NonTerminal ArrayInitializer_2_1 = nonTerminal("ArrayInitializer_2_1", VARIABLE_INITIALIZER);

		static final NonTerminal ArrayInitializer_2_2_2 = nonTerminal("ArrayInitializer_2_2_2", VARIABLE_INITIALIZER);

		static final ZeroOrMore ArrayInitializer_2_2 = zeroOrMore("ArrayInitializer_2_2", sequence("ArrayInitializer_2_2", terminal("ArrayInitializer_2_2_1", TokenType.COMMA), ArrayInitializer_2_2_2));

		static final ZeroOrOne ArrayInitializer_2 = zeroOrOne("ArrayInitializer_2", sequence("ArrayInitializer_2", ArrayInitializer_2_1, ArrayInitializer_2_2));

		static final ZeroOrOne ArrayInitializer_3 = zeroOrOne("ArrayInitializer_3", terminal("ArrayInitializer_3_1", TokenType.COMMA));

		static final Sequence ArrayInitializer = sequence("ArrayInitializer", terminal("ArrayInitializer_1", TokenType.LBRACE), ArrayInitializer_2, ArrayInitializer_3, terminal("ArrayInitializer_4", TokenType.RBRACE));

		static final NonTerminal MethodDecl_1_1 = nonTerminal("MethodDecl_1_1", TYPE_PARAMETERS);

		static final NonTerminal MethodDecl_1_2 = nonTerminal("MethodDecl_1_2", ANNOTATIONS);

		static final ZeroOrOne MethodDecl_1 = zeroOrOne("MethodDecl_1", sequence("MethodDecl_1", MethodDecl_1_1, MethodDecl_1_2));

		static final NonTerminal MethodDecl_2 = nonTerminal("MethodDecl_2", RESULT_TYPE);

		static final NonTerminal MethodDecl_3 = nonTerminal("MethodDecl_3", NAME);

		static final NonTerminal MethodDecl_4 = nonTerminal("MethodDecl_4", FORMAL_PARAMETERS);

		static final NonTerminal MethodDecl_5 = nonTerminal("MethodDecl_5", ARRAY_DIMS);

		static final NonTerminal MethodDecl_6_1 = nonTerminal("MethodDecl_6_1", THROWS_CLAUSE);

		static final ZeroOrOne MethodDecl_6 = zeroOrOne("MethodDecl_6", MethodDecl_6_1);

		static final NonTerminal MethodDecl_7_1 = nonTerminal("MethodDecl_7_1", BLOCK);

		static final Choice MethodDecl_7 = choice("MethodDecl_7", MethodDecl_7_1, sequence("MethodDecl_7_2", terminal("MethodDecl_7_2_1", TokenType.SEMICOLON)));

		static final Sequence MethodDecl = sequence("MethodDecl", MethodDecl_1, MethodDecl_2, MethodDecl_3, MethodDecl_4, MethodDecl_5, MethodDecl_6, MethodDecl_7);

		static final NonTerminal FormalParameters_2_1 = nonTerminal("FormalParameters_2_1", FORMAL_PARAMETER_LIST);

		static final ZeroOrOne FormalParameters_2 = zeroOrOne("FormalParameters_2", FormalParameters_2_1);

		static final Sequence FormalParameters = sequence("FormalParameters", terminal("FormalParameters_1", TokenType.LPAREN), FormalParameters_2, terminal("FormalParameters_3", TokenType.RPAREN));

		static final NonTerminal FormalParameterList_1_1_1 = nonTerminal("FormalParameterList_1_1_1", NODE_LIST_VAR);

		static final NonTerminal FormalParameterList_1_2_1 = nonTerminal("FormalParameterList_1_2_1", FORMAL_PARAMETER);

		static final NonTerminal FormalParameterList_1_2_2_2 = nonTerminal("FormalParameterList_1_2_2_2", FORMAL_PARAMETER);

		static final ZeroOrMore FormalParameterList_1_2_2 = zeroOrMore("FormalParameterList_1_2_2", sequence("FormalParameterList_1_2_2", terminal("FormalParameterList_1_2_2_1", TokenType.COMMA), FormalParameterList_1_2_2_2));

		static final Choice FormalParameterList_1 = choice("FormalParameterList_1", sequence("FormalParameterList_1_1", FormalParameterList_1_1_1), sequence("FormalParameterList_1_2", FormalParameterList_1_2_1, FormalParameterList_1_2_2));

		static final Sequence FormalParameterList = sequence("FormalParameterList", FormalParameterList_1);

		static final NonTerminal FormalParameter_1 = nonTerminal("FormalParameter_1", MODIFIERS);

		static final NonTerminal FormalParameter_2 = nonTerminal("FormalParameter_2", TYPE);

		static final NonTerminal FormalParameter_3_1 = nonTerminal("FormalParameter_3_1", ANNOTATIONS);

		static final ZeroOrOne FormalParameter_3 = zeroOrOne("FormalParameter_3", sequence("FormalParameter_3", FormalParameter_3_1, terminal("FormalParameter_3_2", TokenType.ELLIPSIS)));

		static final NonTerminal FormalParameter_4_1_1_1 = nonTerminal("FormalParameter_4_1_1_1", NAME);

		static final ZeroOrOne FormalParameter_4_1_1 = zeroOrOne("FormalParameter_4_1_1", sequence("FormalParameter_4_1_1", FormalParameter_4_1_1_1, terminal("FormalParameter_4_1_1_2", TokenType.DOT)));

		static final NonTerminal FormalParameter_4_2 = nonTerminal("FormalParameter_4_2", VARIABLE_DECLARATOR_ID);

		static final Choice FormalParameter_4 = choice("FormalParameter_4", sequence("FormalParameter_4_1", FormalParameter_4_1_1, terminal("FormalParameter_4_1_2", TokenType.THIS)), FormalParameter_4_2);

		static final Sequence FormalParameter = sequence("FormalParameter", FormalParameter_1, FormalParameter_2, FormalParameter_3, FormalParameter_4);

		static final NonTerminal ThrowsClause_2 = nonTerminal("ThrowsClause_2", ANNOTATED_QUALIFIED_TYPE);

		static final NonTerminal ThrowsClause_3_2 = nonTerminal("ThrowsClause_3_2", ANNOTATED_QUALIFIED_TYPE);

		static final ZeroOrMore ThrowsClause_3 = zeroOrMore("ThrowsClause_3", sequence("ThrowsClause_3", terminal("ThrowsClause_3_1", TokenType.COMMA), ThrowsClause_3_2));

		static final Sequence ThrowsClause = sequence("ThrowsClause", terminal("ThrowsClause_1", TokenType.THROWS), ThrowsClause_2, ThrowsClause_3);

		static final NonTerminal ConstructorDecl_1_1 = nonTerminal("ConstructorDecl_1_1", TYPE_PARAMETERS);

		static final ZeroOrOne ConstructorDecl_1 = zeroOrOne("ConstructorDecl_1", ConstructorDecl_1_1);

		static final NonTerminal ConstructorDecl_2 = nonTerminal("ConstructorDecl_2", NAME);

		static final NonTerminal ConstructorDecl_3 = nonTerminal("ConstructorDecl_3", FORMAL_PARAMETERS);

		static final NonTerminal ConstructorDecl_4_1 = nonTerminal("ConstructorDecl_4_1", THROWS_CLAUSE);

		static final ZeroOrOne ConstructorDecl_4 = zeroOrOne("ConstructorDecl_4", ConstructorDecl_4_1);

		static final NonTerminal ConstructorDecl_6 = nonTerminal("ConstructorDecl_6", STATEMENTS);

		static final Sequence ConstructorDecl = sequence("ConstructorDecl", ConstructorDecl_1, ConstructorDecl_2, ConstructorDecl_3, ConstructorDecl_4, terminal("ConstructorDecl_5", TokenType.LBRACE), ConstructorDecl_6, terminal("ConstructorDecl_7", TokenType.RBRACE));

		static final NonTerminal ExplicitConstructorInvocation_1_1_1_1 = nonTerminal("ExplicitConstructorInvocation_1_1_1_1", TYPE_ARGUMENTS);

		static final ZeroOrOne ExplicitConstructorInvocation_1_1_1 = zeroOrOne("ExplicitConstructorInvocation_1_1_1", ExplicitConstructorInvocation_1_1_1_1);

		static final NonTerminal ExplicitConstructorInvocation_1_1_3 = nonTerminal("ExplicitConstructorInvocation_1_1_3", ARGUMENTS);

		static final NonTerminal ExplicitConstructorInvocation_1_2_1_1 = nonTerminal("ExplicitConstructorInvocation_1_2_1_1", PRIMARY_EXPRESSION_WITHOUT_SUPER_SUFFIX);

		static final ZeroOrOne ExplicitConstructorInvocation_1_2_1 = zeroOrOne("ExplicitConstructorInvocation_1_2_1", sequence("ExplicitConstructorInvocation_1_2_1", ExplicitConstructorInvocation_1_2_1_1, terminal("ExplicitConstructorInvocation_1_2_1_2", TokenType.DOT)));

		static final NonTerminal ExplicitConstructorInvocation_1_2_2_1 = nonTerminal("ExplicitConstructorInvocation_1_2_2_1", TYPE_ARGUMENTS);

		static final ZeroOrOne ExplicitConstructorInvocation_1_2_2 = zeroOrOne("ExplicitConstructorInvocation_1_2_2", ExplicitConstructorInvocation_1_2_2_1);

		static final NonTerminal ExplicitConstructorInvocation_1_2_4 = nonTerminal("ExplicitConstructorInvocation_1_2_4", ARGUMENTS);

		static final Choice ExplicitConstructorInvocation_1 = choice("ExplicitConstructorInvocation_1", sequence("ExplicitConstructorInvocation_1_1", ExplicitConstructorInvocation_1_1_1, terminal("ExplicitConstructorInvocation_1_1_2", TokenType.THIS), ExplicitConstructorInvocation_1_1_3, terminal("ExplicitConstructorInvocation_1_1_4", TokenType.SEMICOLON)), sequence("ExplicitConstructorInvocation_1_2", ExplicitConstructorInvocation_1_2_1, ExplicitConstructorInvocation_1_2_2, terminal("ExplicitConstructorInvocation_1_2_3", TokenType.SUPER), ExplicitConstructorInvocation_1_2_4, terminal("ExplicitConstructorInvocation_1_2_5", TokenType.SEMICOLON)));

		static final Sequence ExplicitConstructorInvocation = sequence("ExplicitConstructorInvocation", ExplicitConstructorInvocation_1);

		static final NonTerminal Statements_1_1_1_1 = nonTerminal("Statements_1_1_1_1", NODE_LIST_VAR);

		static final NonTerminal Statements_1_1_2_1_1 = nonTerminal("Statements_1_1_2_1_1", EXPLICIT_CONSTRUCTOR_INVOCATION);

		static final ZeroOrOne Statements_1_1_2_1 = zeroOrOne("Statements_1_1_2_1", Statements_1_1_2_1_1);

		static final NonTerminal Statements_1_1_2_2_1 = nonTerminal("Statements_1_1_2_2_1", BLOCK_STATEMENT);

		static final ZeroOrMore Statements_1_1_2_2 = zeroOrMore("Statements_1_1_2_2", Statements_1_1_2_2_1);

		static final Choice Statements_1_1 = choice("Statements_1_1", sequence("Statements_1_1_1", Statements_1_1_1_1), sequence("Statements_1_1_2", Statements_1_1_2_1, Statements_1_1_2_2));

		static final ZeroOrOne Statements_1 = zeroOrOne("Statements_1", Statements_1_1);

		static final Sequence Statements = sequence("Statements", Statements_1);

		static final NonTerminal InitializerDecl_1 = nonTerminal("InitializerDecl_1", BLOCK);

		static final Sequence InitializerDecl = sequence("InitializerDecl", InitializerDecl_1);

		static final NonTerminal Type_1_1_1 = nonTerminal("Type_1_1_1", PRIMITIVE_TYPE);

		static final NonTerminal Type_1_1_2_1 = nonTerminal("Type_1_1_2_1", ARRAY_DIMS_MANDATORY);

		static final ZeroOrOne Type_1_1_2 = zeroOrOne("Type_1_1_2", Type_1_1_2_1);

		static final NonTerminal Type_1_2_1 = nonTerminal("Type_1_2_1", QUALIFIED_TYPE);

		static final NonTerminal Type_1_2_2_1 = nonTerminal("Type_1_2_2_1", ARRAY_DIMS_MANDATORY);

		static final ZeroOrOne Type_1_2_2 = zeroOrOne("Type_1_2_2", Type_1_2_2_1);

		static final Choice Type_1 = choice("Type_1", sequence("Type_1_1", Type_1_1_1, Type_1_1_2), sequence("Type_1_2", Type_1_2_1, Type_1_2_2));

		static final Sequence Type = sequence("Type", Type_1);

		static final NonTerminal ReferenceType_1_1_1 = nonTerminal("ReferenceType_1_1_1", PRIMITIVE_TYPE);

		static final NonTerminal ReferenceType_1_1_2 = nonTerminal("ReferenceType_1_1_2", ARRAY_DIMS_MANDATORY);

		static final NonTerminal ReferenceType_1_2_1 = nonTerminal("ReferenceType_1_2_1", QUALIFIED_TYPE);

		static final NonTerminal ReferenceType_1_2_2_1 = nonTerminal("ReferenceType_1_2_2_1", ARRAY_DIMS_MANDATORY);

		static final ZeroOrOne ReferenceType_1_2_2 = zeroOrOne("ReferenceType_1_2_2", ReferenceType_1_2_2_1);

		static final Choice ReferenceType_1 = choice("ReferenceType_1", sequence("ReferenceType_1_1", ReferenceType_1_1_1, ReferenceType_1_1_2), sequence("ReferenceType_1_2", ReferenceType_1_2_1, ReferenceType_1_2_2));

		static final Sequence ReferenceType = sequence("ReferenceType", ReferenceType_1);

		static final NonTerminal QualifiedType_1 = nonTerminal("QualifiedType_1", NAME);

		static final NonTerminal QualifiedType_2_1 = nonTerminal("QualifiedType_2_1", TYPE_ARGUMENTS_OR_DIAMOND);

		static final ZeroOrOne QualifiedType_2 = zeroOrOne("QualifiedType_2", QualifiedType_2_1);

		static final NonTerminal QualifiedType_3_2 = nonTerminal("QualifiedType_3_2", ANNOTATIONS);

		static final NonTerminal QualifiedType_3_3 = nonTerminal("QualifiedType_3_3", NAME);

		static final NonTerminal QualifiedType_3_4_1 = nonTerminal("QualifiedType_3_4_1", TYPE_ARGUMENTS_OR_DIAMOND);

		static final ZeroOrOne QualifiedType_3_4 = zeroOrOne("QualifiedType_3_4", QualifiedType_3_4_1);

		static final ZeroOrMore QualifiedType_3 = zeroOrMore("QualifiedType_3", sequence("QualifiedType_3", terminal("QualifiedType_3_1", TokenType.DOT), QualifiedType_3_2, QualifiedType_3_3, QualifiedType_3_4));

		static final Sequence QualifiedType = sequence("QualifiedType", QualifiedType_1, QualifiedType_2, QualifiedType_3);

		static final NonTerminal TypeArguments_2_1 = nonTerminal("TypeArguments_2_1", TYPE_ARGUMENT_LIST);

		static final ZeroOrOne TypeArguments_2 = zeroOrOne("TypeArguments_2", TypeArguments_2_1);

		static final Sequence TypeArguments = sequence("TypeArguments", terminal("TypeArguments_1", TokenType.LT), TypeArguments_2, terminal("TypeArguments_3", TokenType.GT));

		static final NonTerminal TypeArgumentsOrDiamond_2_1 = nonTerminal("TypeArgumentsOrDiamond_2_1", TYPE_ARGUMENT_LIST);

		static final ZeroOrOne TypeArgumentsOrDiamond_2 = zeroOrOne("TypeArgumentsOrDiamond_2", TypeArgumentsOrDiamond_2_1);

		static final Sequence TypeArgumentsOrDiamond = sequence("TypeArgumentsOrDiamond", terminal("TypeArgumentsOrDiamond_1", TokenType.LT), TypeArgumentsOrDiamond_2, terminal("TypeArgumentsOrDiamond_3", TokenType.GT));

		static final NonTerminal TypeArgumentList_1_1 = nonTerminal("TypeArgumentList_1_1", NODE_LIST_VAR);

		static final NonTerminal TypeArgumentList_2_1 = nonTerminal("TypeArgumentList_2_1", TYPE_ARGUMENT);

		static final NonTerminal TypeArgumentList_2_2_2 = nonTerminal("TypeArgumentList_2_2_2", TYPE_ARGUMENT);

		static final ZeroOrMore TypeArgumentList_2_2 = zeroOrMore("TypeArgumentList_2_2", sequence("TypeArgumentList_2_2", terminal("TypeArgumentList_2_2_1", TokenType.COMMA), TypeArgumentList_2_2_2));

		static final Choice TypeArgumentList = choice("TypeArgumentList", sequence("TypeArgumentList_1", TypeArgumentList_1_1), sequence("TypeArgumentList_2", TypeArgumentList_2_1, TypeArgumentList_2_2));

		static final NonTerminal TypeArgument_1 = nonTerminal("TypeArgument_1", ANNOTATIONS);

		static final NonTerminal TypeArgument_2_1 = nonTerminal("TypeArgument_2_1", REFERENCE_TYPE);

		static final NonTerminal TypeArgument_2_2 = nonTerminal("TypeArgument_2_2", WILDCARD);

		static final Choice TypeArgument_2 = choice("TypeArgument_2", TypeArgument_2_1, TypeArgument_2_2);

		static final Sequence TypeArgument = sequence("TypeArgument", TypeArgument_1, TypeArgument_2);

		static final NonTerminal Wildcard_2_1_1_2 = nonTerminal("Wildcard_2_1_1_2", ANNOTATIONS);

		static final NonTerminal Wildcard_2_1_1_3 = nonTerminal("Wildcard_2_1_1_3", REFERENCE_TYPE);

		static final NonTerminal Wildcard_2_1_2_2 = nonTerminal("Wildcard_2_1_2_2", ANNOTATIONS);

		static final NonTerminal Wildcard_2_1_2_3 = nonTerminal("Wildcard_2_1_2_3", REFERENCE_TYPE);

		static final Choice Wildcard_2_1 = choice("Wildcard_2_1", sequence("Wildcard_2_1_1", terminal("Wildcard_2_1_1_1", TokenType.EXTENDS), Wildcard_2_1_1_2, Wildcard_2_1_1_3), sequence("Wildcard_2_1_2", terminal("Wildcard_2_1_2_1", TokenType.SUPER), Wildcard_2_1_2_2, Wildcard_2_1_2_3));

		static final ZeroOrOne Wildcard_2 = zeroOrOne("Wildcard_2", Wildcard_2_1);

		static final Sequence Wildcard = sequence("Wildcard", terminal("Wildcard_1", TokenType.HOOK), Wildcard_2);

		static final Choice PrimitiveType_1 = choice("PrimitiveType_1", sequence("PrimitiveType_1_1", terminal("PrimitiveType_1_1_1", TokenType.BOOLEAN)), sequence("PrimitiveType_1_2", terminal("PrimitiveType_1_2_1", TokenType.CHAR)), sequence("PrimitiveType_1_3", terminal("PrimitiveType_1_3_1", TokenType.BYTE)), sequence("PrimitiveType_1_4", terminal("PrimitiveType_1_4_1", TokenType.SHORT)), sequence("PrimitiveType_1_5", terminal("PrimitiveType_1_5_1", TokenType.INT)), sequence("PrimitiveType_1_6", terminal("PrimitiveType_1_6_1", TokenType.LONG)), sequence("PrimitiveType_1_7", terminal("PrimitiveType_1_7_1", TokenType.FLOAT)), sequence("PrimitiveType_1_8", terminal("PrimitiveType_1_8_1", TokenType.DOUBLE)));

		static final Sequence PrimitiveType = sequence("PrimitiveType", PrimitiveType_1);

		static final NonTerminal ResultType_1_2 = nonTerminal("ResultType_1_2", TYPE);

		static final Choice ResultType_1 = choice("ResultType_1", sequence("ResultType_1_1", terminal("ResultType_1_1_1", TokenType.VOID)), ResultType_1_2);

		static final Sequence ResultType = sequence("ResultType", ResultType_1);

		static final NonTerminal AnnotatedQualifiedType_1 = nonTerminal("AnnotatedQualifiedType_1", ANNOTATIONS);

		static final NonTerminal AnnotatedQualifiedType_2 = nonTerminal("AnnotatedQualifiedType_2", QUALIFIED_TYPE);

		static final Sequence AnnotatedQualifiedType = sequence("AnnotatedQualifiedType", AnnotatedQualifiedType_1, AnnotatedQualifiedType_2);

		static final NonTerminal QualifiedName_1 = nonTerminal("QualifiedName_1", NAME);

		static final NonTerminal QualifiedName_2_2 = nonTerminal("QualifiedName_2_2", NAME);

		static final ZeroOrMore QualifiedName_2 = zeroOrMore("QualifiedName_2", sequence("QualifiedName_2", terminal("QualifiedName_2_1", TokenType.DOT), QualifiedName_2_2));

		static final Sequence QualifiedName = sequence("QualifiedName", QualifiedName_1, QualifiedName_2);

		static final NonTerminal Name_1_1_1 = nonTerminal("Name_1_1_1", NODE_VAR);

		static final Choice Name_1 = choice("Name_1", sequence("Name_1_1", Name_1_1_1), sequence("Name_1_2", terminal("Name_1_2_1", TokenType.IDENTIFIER)));

		static final Sequence Name = sequence("Name", Name_1);

		static final NonTerminal Expression_1_1 = nonTerminal("Expression_1_1", ASSIGNMENT_EXPRESSION);

		static final NonTerminal Expression_1_2 = nonTerminal("Expression_1_2", LAMBDA_EXPRESSION);

		static final Choice Expression_1 = choice("Expression_1", Expression_1_1, Expression_1_2);

		static final Sequence Expression = sequence("Expression", Expression_1);

		static final NonTerminal AssignmentExpression_1 = nonTerminal("AssignmentExpression_1", CONDITIONAL_EXPRESSION);

		static final NonTerminal AssignmentExpression_2_1 = nonTerminal("AssignmentExpression_2_1", ASSIGNMENT_OPERATOR);

		static final NonTerminal AssignmentExpression_2_2 = nonTerminal("AssignmentExpression_2_2", EXPRESSION);

		static final ZeroOrOne AssignmentExpression_2 = zeroOrOne("AssignmentExpression_2", sequence("AssignmentExpression_2", AssignmentExpression_2_1, AssignmentExpression_2_2));

		static final Sequence AssignmentExpression = sequence("AssignmentExpression", AssignmentExpression_1, AssignmentExpression_2);

		static final NonTerminal LambdaExpression_1_1_2 = nonTerminal("LambdaExpression_1_1_2", ANNOTATIONS);

		static final NonTerminal LambdaExpression_1_1_3 = nonTerminal("LambdaExpression_1_1_3", REFERENCE_TYPE);

		static final NonTerminal LambdaExpression_1_1_4 = nonTerminal("LambdaExpression_1_1_4", REFERENCE_CAST_TYPE_REST);

		static final NonTerminal LambdaExpression_1_1_6 = nonTerminal("LambdaExpression_1_1_6", LAMBDA_EXPRESSION);

		static final NonTerminal LambdaExpression_1_2 = nonTerminal("LambdaExpression_1_2", LAMBDA_EXPRESSION_WITHOUT_CAST);

		static final Choice LambdaExpression_1 = choice("LambdaExpression_1", sequence("LambdaExpression_1_1", terminal("LambdaExpression_1_1_1", TokenType.LPAREN), LambdaExpression_1_1_2, LambdaExpression_1_1_3, LambdaExpression_1_1_4, terminal("LambdaExpression_1_1_5", TokenType.RPAREN), LambdaExpression_1_1_6), LambdaExpression_1_2);

		static final Sequence LambdaExpression = sequence("LambdaExpression", LambdaExpression_1);

		static final NonTerminal LambdaExpressionWithoutCast_1_1_1 = nonTerminal("LambdaExpressionWithoutCast_1_1_1", NAME);

		static final NonTerminal LambdaExpressionWithoutCast_1_1_3 = nonTerminal("LambdaExpressionWithoutCast_1_1_3", LAMBDA_BODY);

		static final NonTerminal LambdaExpressionWithoutCast_1_2_4 = nonTerminal("LambdaExpressionWithoutCast_1_2_4", LAMBDA_BODY);

		static final NonTerminal LambdaExpressionWithoutCast_1_3_2 = nonTerminal("LambdaExpressionWithoutCast_1_3_2", INFERRED_FORMAL_PARAMETER_LIST);

		static final NonTerminal LambdaExpressionWithoutCast_1_3_5 = nonTerminal("LambdaExpressionWithoutCast_1_3_5", LAMBDA_BODY);

		static final NonTerminal LambdaExpressionWithoutCast_1_4_2 = nonTerminal("LambdaExpressionWithoutCast_1_4_2", FORMAL_PARAMETER_LIST);

		static final NonTerminal LambdaExpressionWithoutCast_1_4_5 = nonTerminal("LambdaExpressionWithoutCast_1_4_5", LAMBDA_BODY);

		static final Choice LambdaExpressionWithoutCast_1 = choice("LambdaExpressionWithoutCast_1", sequence("LambdaExpressionWithoutCast_1_1", LambdaExpressionWithoutCast_1_1_1, terminal("LambdaExpressionWithoutCast_1_1_2", TokenType.ARROW), LambdaExpressionWithoutCast_1_1_3), sequence("LambdaExpressionWithoutCast_1_2", terminal("LambdaExpressionWithoutCast_1_2_1", TokenType.LPAREN), terminal("LambdaExpressionWithoutCast_1_2_2", TokenType.RPAREN), terminal("LambdaExpressionWithoutCast_1_2_3", TokenType.ARROW), LambdaExpressionWithoutCast_1_2_4), sequence("LambdaExpressionWithoutCast_1_3", terminal("LambdaExpressionWithoutCast_1_3_1", TokenType.LPAREN), LambdaExpressionWithoutCast_1_3_2, terminal("LambdaExpressionWithoutCast_1_3_3", TokenType.RPAREN), terminal("LambdaExpressionWithoutCast_1_3_4", TokenType.ARROW), LambdaExpressionWithoutCast_1_3_5), sequence("LambdaExpressionWithoutCast_1_4", terminal("LambdaExpressionWithoutCast_1_4_1", TokenType.LPAREN), LambdaExpressionWithoutCast_1_4_2, terminal("LambdaExpressionWithoutCast_1_4_3", TokenType.RPAREN), terminal("LambdaExpressionWithoutCast_1_4_4", TokenType.ARROW), LambdaExpressionWithoutCast_1_4_5));

		static final Sequence LambdaExpressionWithoutCast = sequence("LambdaExpressionWithoutCast", LambdaExpressionWithoutCast_1);

		static final NonTerminal LambdaBody_1_1_1 = nonTerminal("LambdaBody_1_1_1", EXPRESSION);

		static final NonTerminal LambdaBody_1_2_1 = nonTerminal("LambdaBody_1_2_1", BLOCK);

		static final Choice LambdaBody_1 = choice("LambdaBody_1", sequence("LambdaBody_1_1", LambdaBody_1_1_1), sequence("LambdaBody_1_2", LambdaBody_1_2_1));

		static final Sequence LambdaBody = sequence("LambdaBody", LambdaBody_1);

		static final NonTerminal InferredFormalParameterList_1 = nonTerminal("InferredFormalParameterList_1", INFERRED_FORMAL_PARAMETER);

		static final NonTerminal InferredFormalParameterList_2_2 = nonTerminal("InferredFormalParameterList_2_2", INFERRED_FORMAL_PARAMETER);

		static final ZeroOrMore InferredFormalParameterList_2 = zeroOrMore("InferredFormalParameterList_2", sequence("InferredFormalParameterList_2", terminal("InferredFormalParameterList_2_1", TokenType.COMMA), InferredFormalParameterList_2_2));

		static final Sequence InferredFormalParameterList = sequence("InferredFormalParameterList", InferredFormalParameterList_1, InferredFormalParameterList_2);

		static final NonTerminal InferredFormalParameter_1 = nonTerminal("InferredFormalParameter_1", NAME);

		static final Sequence InferredFormalParameter = sequence("InferredFormalParameter", InferredFormalParameter_1);

		static final Choice AssignmentOperator_1 = choice("AssignmentOperator_1", sequence("AssignmentOperator_1_1", terminal("AssignmentOperator_1_1_1", TokenType.ASSIGN)), sequence("AssignmentOperator_1_2", terminal("AssignmentOperator_1_2_1", TokenType.STARASSIGN)), sequence("AssignmentOperator_1_3", terminal("AssignmentOperator_1_3_1", TokenType.SLASHASSIGN)), sequence("AssignmentOperator_1_4", terminal("AssignmentOperator_1_4_1", TokenType.REMASSIGN)), sequence("AssignmentOperator_1_5", terminal("AssignmentOperator_1_5_1", TokenType.PLUSASSIGN)), sequence("AssignmentOperator_1_6", terminal("AssignmentOperator_1_6_1", TokenType.MINUSASSIGN)), sequence("AssignmentOperator_1_7", terminal("AssignmentOperator_1_7_1", TokenType.LSHIFTASSIGN)), sequence("AssignmentOperator_1_8", terminal("AssignmentOperator_1_8_1", TokenType.RSIGNEDSHIFTASSIGN)), sequence("AssignmentOperator_1_9", terminal("AssignmentOperator_1_9_1", TokenType.RUNSIGNEDSHIFTASSIGN)), sequence("AssignmentOperator_1_10", terminal("AssignmentOperator_1_10_1", TokenType.ANDASSIGN)), sequence("AssignmentOperator_1_11", terminal("AssignmentOperator_1_11_1", TokenType.XORASSIGN)), sequence("AssignmentOperator_1_12", terminal("AssignmentOperator_1_12_1", TokenType.ORASSIGN)));

		static final Sequence AssignmentOperator = sequence("AssignmentOperator", AssignmentOperator_1);

		static final NonTerminal ConditionalExpression_1 = nonTerminal("ConditionalExpression_1", CONDITIONAL_OR_EXPRESSION);

		static final NonTerminal ConditionalExpression_2_2 = nonTerminal("ConditionalExpression_2_2", EXPRESSION);

		static final NonTerminal ConditionalExpression_2_4_1 = nonTerminal("ConditionalExpression_2_4_1", CONDITIONAL_EXPRESSION);

		static final NonTerminal ConditionalExpression_2_4_2 = nonTerminal("ConditionalExpression_2_4_2", LAMBDA_EXPRESSION);

		static final Choice ConditionalExpression_2_4 = choice("ConditionalExpression_2_4", ConditionalExpression_2_4_1, ConditionalExpression_2_4_2);

		static final ZeroOrOne ConditionalExpression_2 = zeroOrOne("ConditionalExpression_2", sequence("ConditionalExpression_2", terminal("ConditionalExpression_2_1", TokenType.HOOK), ConditionalExpression_2_2, terminal("ConditionalExpression_2_3", TokenType.COLON), ConditionalExpression_2_4));

		static final Sequence ConditionalExpression = sequence("ConditionalExpression", ConditionalExpression_1, ConditionalExpression_2);

		static final NonTerminal ConditionalOrExpression_1 = nonTerminal("ConditionalOrExpression_1", CONDITIONAL_AND_EXPRESSION);

		static final NonTerminal ConditionalOrExpression_2_2 = nonTerminal("ConditionalOrExpression_2_2", CONDITIONAL_AND_EXPRESSION);

		static final ZeroOrMore ConditionalOrExpression_2 = zeroOrMore("ConditionalOrExpression_2", sequence("ConditionalOrExpression_2", terminal("ConditionalOrExpression_2_1", TokenType.SC_OR), ConditionalOrExpression_2_2));

		static final Sequence ConditionalOrExpression = sequence("ConditionalOrExpression", ConditionalOrExpression_1, ConditionalOrExpression_2);

		static final NonTerminal ConditionalAndExpression_1 = nonTerminal("ConditionalAndExpression_1", INCLUSIVE_OR_EXPRESSION);

		static final NonTerminal ConditionalAndExpression_2_2 = nonTerminal("ConditionalAndExpression_2_2", INCLUSIVE_OR_EXPRESSION);

		static final ZeroOrMore ConditionalAndExpression_2 = zeroOrMore("ConditionalAndExpression_2", sequence("ConditionalAndExpression_2", terminal("ConditionalAndExpression_2_1", TokenType.SC_AND), ConditionalAndExpression_2_2));

		static final Sequence ConditionalAndExpression = sequence("ConditionalAndExpression", ConditionalAndExpression_1, ConditionalAndExpression_2);

		static final NonTerminal InclusiveOrExpression_1 = nonTerminal("InclusiveOrExpression_1", EXCLUSIVE_OR_EXPRESSION);

		static final NonTerminal InclusiveOrExpression_2_2 = nonTerminal("InclusiveOrExpression_2_2", EXCLUSIVE_OR_EXPRESSION);

		static final ZeroOrMore InclusiveOrExpression_2 = zeroOrMore("InclusiveOrExpression_2", sequence("InclusiveOrExpression_2", terminal("InclusiveOrExpression_2_1", TokenType.BIT_OR), InclusiveOrExpression_2_2));

		static final Sequence InclusiveOrExpression = sequence("InclusiveOrExpression", InclusiveOrExpression_1, InclusiveOrExpression_2);

		static final NonTerminal ExclusiveOrExpression_1 = nonTerminal("ExclusiveOrExpression_1", AND_EXPRESSION);

		static final NonTerminal ExclusiveOrExpression_2_2 = nonTerminal("ExclusiveOrExpression_2_2", AND_EXPRESSION);

		static final ZeroOrMore ExclusiveOrExpression_2 = zeroOrMore("ExclusiveOrExpression_2", sequence("ExclusiveOrExpression_2", terminal("ExclusiveOrExpression_2_1", TokenType.XOR), ExclusiveOrExpression_2_2));

		static final Sequence ExclusiveOrExpression = sequence("ExclusiveOrExpression", ExclusiveOrExpression_1, ExclusiveOrExpression_2);

		static final NonTerminal AndExpression_1 = nonTerminal("AndExpression_1", EQUALITY_EXPRESSION);

		static final NonTerminal AndExpression_2_2 = nonTerminal("AndExpression_2_2", EQUALITY_EXPRESSION);

		static final ZeroOrMore AndExpression_2 = zeroOrMore("AndExpression_2", sequence("AndExpression_2", terminal("AndExpression_2_1", TokenType.BIT_AND), AndExpression_2_2));

		static final Sequence AndExpression = sequence("AndExpression", AndExpression_1, AndExpression_2);

		static final NonTerminal EqualityExpression_1 = nonTerminal("EqualityExpression_1", INSTANCE_OF_EXPRESSION);

		static final Choice EqualityExpression_2_1 = choice("EqualityExpression_2_1", sequence("EqualityExpression_2_1_1", terminal("EqualityExpression_2_1_1_1", TokenType.EQ)), sequence("EqualityExpression_2_1_2", terminal("EqualityExpression_2_1_2_1", TokenType.NE)));

		static final NonTerminal EqualityExpression_2_2 = nonTerminal("EqualityExpression_2_2", INSTANCE_OF_EXPRESSION);

		static final ZeroOrMore EqualityExpression_2 = zeroOrMore("EqualityExpression_2", sequence("EqualityExpression_2", EqualityExpression_2_1, EqualityExpression_2_2));

		static final Sequence EqualityExpression = sequence("EqualityExpression", EqualityExpression_1, EqualityExpression_2);

		static final NonTerminal InstanceOfExpression_1 = nonTerminal("InstanceOfExpression_1", RELATIONAL_EXPRESSION);

		static final NonTerminal InstanceOfExpression_2_2 = nonTerminal("InstanceOfExpression_2_2", ANNOTATIONS);

		static final NonTerminal InstanceOfExpression_2_3 = nonTerminal("InstanceOfExpression_2_3", TYPE);

		static final ZeroOrOne InstanceOfExpression_2 = zeroOrOne("InstanceOfExpression_2", sequence("InstanceOfExpression_2", terminal("InstanceOfExpression_2_1", TokenType.INSTANCEOF), InstanceOfExpression_2_2, InstanceOfExpression_2_3));

		static final Sequence InstanceOfExpression = sequence("InstanceOfExpression", InstanceOfExpression_1, InstanceOfExpression_2);

		static final NonTerminal RelationalExpression_1 = nonTerminal("RelationalExpression_1", SHIFT_EXPRESSION);

		static final Choice RelationalExpression_2_1 = choice("RelationalExpression_2_1", sequence("RelationalExpression_2_1_1", terminal("RelationalExpression_2_1_1_1", TokenType.LT)), sequence("RelationalExpression_2_1_2", terminal("RelationalExpression_2_1_2_1", TokenType.GT)), sequence("RelationalExpression_2_1_3", terminal("RelationalExpression_2_1_3_1", TokenType.LE)), sequence("RelationalExpression_2_1_4", terminal("RelationalExpression_2_1_4_1", TokenType.GE)));

		static final NonTerminal RelationalExpression_2_2 = nonTerminal("RelationalExpression_2_2", SHIFT_EXPRESSION);

		static final ZeroOrMore RelationalExpression_2 = zeroOrMore("RelationalExpression_2", sequence("RelationalExpression_2", RelationalExpression_2_1, RelationalExpression_2_2));

		static final Sequence RelationalExpression = sequence("RelationalExpression", RelationalExpression_1, RelationalExpression_2);

		static final NonTerminal ShiftExpression_1 = nonTerminal("ShiftExpression_1", ADDITIVE_EXPRESSION);

		static final NonTerminal ShiftExpression_2_1_2_1 = nonTerminal("ShiftExpression_2_1_2_1", R_U_N_S_I_G_N_E_D_S_H_I_F_T);

		static final NonTerminal ShiftExpression_2_1_3_1 = nonTerminal("ShiftExpression_2_1_3_1", R_S_I_G_N_E_D_S_H_I_F_T);

		static final Choice ShiftExpression_2_1 = choice("ShiftExpression_2_1", sequence("ShiftExpression_2_1_1", terminal("ShiftExpression_2_1_1_1", TokenType.LSHIFT)), sequence("ShiftExpression_2_1_2", ShiftExpression_2_1_2_1), sequence("ShiftExpression_2_1_3", ShiftExpression_2_1_3_1));

		static final NonTerminal ShiftExpression_2_2 = nonTerminal("ShiftExpression_2_2", ADDITIVE_EXPRESSION);

		static final ZeroOrMore ShiftExpression_2 = zeroOrMore("ShiftExpression_2", sequence("ShiftExpression_2", ShiftExpression_2_1, ShiftExpression_2_2));

		static final Sequence ShiftExpression = sequence("ShiftExpression", ShiftExpression_1, ShiftExpression_2);

		static final NonTerminal AdditiveExpression_1 = nonTerminal("AdditiveExpression_1", MULTIPLICATIVE_EXPRESSION);

		static final Choice AdditiveExpression_2_1 = choice("AdditiveExpression_2_1", sequence("AdditiveExpression_2_1_1", terminal("AdditiveExpression_2_1_1_1", TokenType.PLUS)), sequence("AdditiveExpression_2_1_2", terminal("AdditiveExpression_2_1_2_1", TokenType.MINUS)));

		static final NonTerminal AdditiveExpression_2_2 = nonTerminal("AdditiveExpression_2_2", MULTIPLICATIVE_EXPRESSION);

		static final ZeroOrMore AdditiveExpression_2 = zeroOrMore("AdditiveExpression_2", sequence("AdditiveExpression_2", AdditiveExpression_2_1, AdditiveExpression_2_2));

		static final Sequence AdditiveExpression = sequence("AdditiveExpression", AdditiveExpression_1, AdditiveExpression_2);

		static final NonTerminal MultiplicativeExpression_1 = nonTerminal("MultiplicativeExpression_1", UNARY_EXPRESSION);

		static final Choice MultiplicativeExpression_2_1 = choice("MultiplicativeExpression_2_1", sequence("MultiplicativeExpression_2_1_1", terminal("MultiplicativeExpression_2_1_1_1", TokenType.STAR)), sequence("MultiplicativeExpression_2_1_2", terminal("MultiplicativeExpression_2_1_2_1", TokenType.SLASH)), sequence("MultiplicativeExpression_2_1_3", terminal("MultiplicativeExpression_2_1_3_1", TokenType.REM)));

		static final NonTerminal MultiplicativeExpression_2_2 = nonTerminal("MultiplicativeExpression_2_2", UNARY_EXPRESSION);

		static final ZeroOrMore MultiplicativeExpression_2 = zeroOrMore("MultiplicativeExpression_2", sequence("MultiplicativeExpression_2", MultiplicativeExpression_2_1, MultiplicativeExpression_2_2));

		static final Sequence MultiplicativeExpression = sequence("MultiplicativeExpression", MultiplicativeExpression_1, MultiplicativeExpression_2);

		static final NonTerminal UnaryExpression_1_1 = nonTerminal("UnaryExpression_1_1", PREFIX_EXPRESSION);

		static final Choice UnaryExpression_1_2_1 = choice("UnaryExpression_1_2_1", sequence("UnaryExpression_1_2_1_1", terminal("UnaryExpression_1_2_1_1_1", TokenType.PLUS)), sequence("UnaryExpression_1_2_1_2", terminal("UnaryExpression_1_2_1_2_1", TokenType.MINUS)));

		static final NonTerminal UnaryExpression_1_2_2 = nonTerminal("UnaryExpression_1_2_2", UNARY_EXPRESSION);

		static final NonTerminal UnaryExpression_1_3 = nonTerminal("UnaryExpression_1_3", UNARY_EXPRESSION_NOT_PLUS_MINUS);

		static final Choice UnaryExpression_1 = choice("UnaryExpression_1", UnaryExpression_1_1, sequence("UnaryExpression_1_2", UnaryExpression_1_2_1, UnaryExpression_1_2_2), UnaryExpression_1_3);

		static final Sequence UnaryExpression = sequence("UnaryExpression", UnaryExpression_1);

		static final Choice PrefixExpression_1 = choice("PrefixExpression_1", sequence("PrefixExpression_1_1", terminal("PrefixExpression_1_1_1", TokenType.INCR)), sequence("PrefixExpression_1_2", terminal("PrefixExpression_1_2_1", TokenType.DECR)));

		static final NonTerminal PrefixExpression_2 = nonTerminal("PrefixExpression_2", UNARY_EXPRESSION);

		static final Sequence PrefixExpression = sequence("PrefixExpression", PrefixExpression_1, PrefixExpression_2);

		static final Choice UnaryExpressionNotPlusMinus_1_1_1 = choice("UnaryExpressionNotPlusMinus_1_1_1", sequence("UnaryExpressionNotPlusMinus_1_1_1_1", terminal("UnaryExpressionNotPlusMinus_1_1_1_1_1", TokenType.TILDE)), sequence("UnaryExpressionNotPlusMinus_1_1_1_2", terminal("UnaryExpressionNotPlusMinus_1_1_1_2_1", TokenType.BANG)));

		static final NonTerminal UnaryExpressionNotPlusMinus_1_1_2 = nonTerminal("UnaryExpressionNotPlusMinus_1_1_2", UNARY_EXPRESSION);

		static final NonTerminal UnaryExpressionNotPlusMinus_1_2 = nonTerminal("UnaryExpressionNotPlusMinus_1_2", CAST_EXPRESSION);

		static final NonTerminal UnaryExpressionNotPlusMinus_1_3 = nonTerminal("UnaryExpressionNotPlusMinus_1_3", POSTFIX_EXPRESSION);

		static final Choice UnaryExpressionNotPlusMinus_1 = choice("UnaryExpressionNotPlusMinus_1", sequence("UnaryExpressionNotPlusMinus_1_1", UnaryExpressionNotPlusMinus_1_1_1, UnaryExpressionNotPlusMinus_1_1_2), UnaryExpressionNotPlusMinus_1_2, UnaryExpressionNotPlusMinus_1_3);

		static final Sequence UnaryExpressionNotPlusMinus = sequence("UnaryExpressionNotPlusMinus", UnaryExpressionNotPlusMinus_1);

		static final NonTerminal PostfixExpression_1 = nonTerminal("PostfixExpression_1", PRIMARY_EXPRESSION);

		static final Choice PostfixExpression_2_1 = choice("PostfixExpression_2_1", sequence("PostfixExpression_2_1_1", terminal("PostfixExpression_2_1_1_1", TokenType.INCR)), sequence("PostfixExpression_2_1_2", terminal("PostfixExpression_2_1_2_1", TokenType.DECR)));

		static final ZeroOrOne PostfixExpression_2 = zeroOrOne("PostfixExpression_2", PostfixExpression_2_1);

		static final Sequence PostfixExpression = sequence("PostfixExpression", PostfixExpression_1, PostfixExpression_2);

		static final NonTerminal CastExpression_2 = nonTerminal("CastExpression_2", ANNOTATIONS);

		static final NonTerminal CastExpression_3_1_1 = nonTerminal("CastExpression_3_1_1", PRIMITIVE_TYPE);

		static final NonTerminal CastExpression_3_1_3 = nonTerminal("CastExpression_3_1_3", UNARY_EXPRESSION);

		static final NonTerminal CastExpression_3_2_1 = nonTerminal("CastExpression_3_2_1", REFERENCE_TYPE);

		static final NonTerminal CastExpression_3_2_2 = nonTerminal("CastExpression_3_2_2", REFERENCE_CAST_TYPE_REST);

		static final NonTerminal CastExpression_3_2_4 = nonTerminal("CastExpression_3_2_4", UNARY_EXPRESSION_NOT_PLUS_MINUS);

		static final Choice CastExpression_3 = choice("CastExpression_3", sequence("CastExpression_3_1", CastExpression_3_1_1, terminal("CastExpression_3_1_2", TokenType.RPAREN), CastExpression_3_1_3), sequence("CastExpression_3_2", CastExpression_3_2_1, CastExpression_3_2_2, terminal("CastExpression_3_2_3", TokenType.RPAREN), CastExpression_3_2_4));

		static final Sequence CastExpression = sequence("CastExpression", terminal("CastExpression_1", TokenType.LPAREN), CastExpression_2, CastExpression_3);

		static final NonTerminal ReferenceCastTypeRest_1_1_2 = nonTerminal("ReferenceCastTypeRest_1_1_2", ANNOTATIONS);

		static final NonTerminal ReferenceCastTypeRest_1_1_3 = nonTerminal("ReferenceCastTypeRest_1_1_3", REFERENCE_TYPE);

		static final OneOrMore ReferenceCastTypeRest_1_1 = oneOrMore("ReferenceCastTypeRest_1_1", sequence("ReferenceCastTypeRest_1_1", terminal("ReferenceCastTypeRest_1_1_1", TokenType.BIT_AND), ReferenceCastTypeRest_1_1_2, ReferenceCastTypeRest_1_1_3));

		static final ZeroOrOne ReferenceCastTypeRest_1 = zeroOrOne("ReferenceCastTypeRest_1", ReferenceCastTypeRest_1_1);

		static final Sequence ReferenceCastTypeRest = sequence("ReferenceCastTypeRest", ReferenceCastTypeRest_1);

		static final Choice Literal_1 = choice("Literal_1", sequence("Literal_1_1", terminal("Literal_1_1_1", TokenType.INTEGER_LITERAL)), sequence("Literal_1_2", terminal("Literal_1_2_1", TokenType.LONG_LITERAL)), sequence("Literal_1_3", terminal("Literal_1_3_1", TokenType.FLOAT_LITERAL)), sequence("Literal_1_4", terminal("Literal_1_4_1", TokenType.DOUBLE_LITERAL)), sequence("Literal_1_5", terminal("Literal_1_5_1", TokenType.CHARACTER_LITERAL)), sequence("Literal_1_6", terminal("Literal_1_6_1", TokenType.STRING_LITERAL)), sequence("Literal_1_7", terminal("Literal_1_7_1", TokenType.TRUE)), sequence("Literal_1_8", terminal("Literal_1_8_1", TokenType.FALSE)), sequence("Literal_1_9", terminal("Literal_1_9_1", TokenType.NULL)));

		static final Sequence Literal = sequence("Literal", Literal_1);

		static final NonTerminal PrimaryExpression_1_1 = nonTerminal("PrimaryExpression_1_1", PRIMARY_NO_NEW_ARRAY);

		static final NonTerminal PrimaryExpression_1_2 = nonTerminal("PrimaryExpression_1_2", ARRAY_CREATION_EXPR);

		static final Choice PrimaryExpression_1 = choice("PrimaryExpression_1", PrimaryExpression_1_1, PrimaryExpression_1_2);

		static final Sequence PrimaryExpression = sequence("PrimaryExpression", PrimaryExpression_1);

		static final NonTerminal PrimaryNoNewArray_1 = nonTerminal("PrimaryNoNewArray_1", PRIMARY_PREFIX);

		static final NonTerminal PrimaryNoNewArray_2_1 = nonTerminal("PrimaryNoNewArray_2_1", PRIMARY_SUFFIX);

		static final ZeroOrMore PrimaryNoNewArray_2 = zeroOrMore("PrimaryNoNewArray_2", PrimaryNoNewArray_2_1);

		static final Sequence PrimaryNoNewArray = sequence("PrimaryNoNewArray", PrimaryNoNewArray_1, PrimaryNoNewArray_2);

		static final NonTerminal PrimaryExpressionWithoutSuperSuffix_1 = nonTerminal("PrimaryExpressionWithoutSuperSuffix_1", PRIMARY_PREFIX);

		static final NonTerminal PrimaryExpressionWithoutSuperSuffix_2_1 = nonTerminal("PrimaryExpressionWithoutSuperSuffix_2_1", PRIMARY_SUFFIX_WITHOUT_SUPER);

		static final ZeroOrMore PrimaryExpressionWithoutSuperSuffix_2 = zeroOrMore("PrimaryExpressionWithoutSuperSuffix_2", PrimaryExpressionWithoutSuperSuffix_2_1);

		static final Sequence PrimaryExpressionWithoutSuperSuffix = sequence("PrimaryExpressionWithoutSuperSuffix", PrimaryExpressionWithoutSuperSuffix_1, PrimaryExpressionWithoutSuperSuffix_2);

		static final NonTerminal PrimaryPrefix_1_1 = nonTerminal("PrimaryPrefix_1_1", LITERAL);

		static final NonTerminal PrimaryPrefix_1_3_2_1_2_1 = nonTerminal("PrimaryPrefix_1_3_2_1_2_1", METHOD_INVOCATION);

		static final NonTerminal PrimaryPrefix_1_3_2_1_2_2 = nonTerminal("PrimaryPrefix_1_3_2_1_2_2", FIELD_ACCESS);

		static final Choice PrimaryPrefix_1_3_2_1_2 = choice("PrimaryPrefix_1_3_2_1_2", PrimaryPrefix_1_3_2_1_2_1, PrimaryPrefix_1_3_2_1_2_2);

		static final NonTerminal PrimaryPrefix_1_3_2_2_1 = nonTerminal("PrimaryPrefix_1_3_2_2_1", METHOD_REFERENCE_SUFFIX);

		static final Choice PrimaryPrefix_1_3_2 = choice("PrimaryPrefix_1_3_2", sequence("PrimaryPrefix_1_3_2_1", terminal("PrimaryPrefix_1_3_2_1_1", TokenType.DOT), PrimaryPrefix_1_3_2_1_2), sequence("PrimaryPrefix_1_3_2_2", PrimaryPrefix_1_3_2_2_1));

		static final NonTerminal PrimaryPrefix_1_4 = nonTerminal("PrimaryPrefix_1_4", CLASS_CREATION_EXPR);

		static final NonTerminal PrimaryPrefix_1_5_1 = nonTerminal("PrimaryPrefix_1_5_1", RESULT_TYPE);

		static final NonTerminal PrimaryPrefix_1_6_1 = nonTerminal("PrimaryPrefix_1_6_1", RESULT_TYPE);

		static final NonTerminal PrimaryPrefix_1_6_2 = nonTerminal("PrimaryPrefix_1_6_2", METHOD_REFERENCE_SUFFIX);

		static final NonTerminal PrimaryPrefix_1_7_1 = nonTerminal("PrimaryPrefix_1_7_1", METHOD_INVOCATION);

		static final NonTerminal PrimaryPrefix_1_8_1 = nonTerminal("PrimaryPrefix_1_8_1", NAME);

		static final NonTerminal PrimaryPrefix_1_9_2 = nonTerminal("PrimaryPrefix_1_9_2", EXPRESSION);

		static final Choice PrimaryPrefix_1 = choice("PrimaryPrefix_1", PrimaryPrefix_1_1, sequence("PrimaryPrefix_1_2", terminal("PrimaryPrefix_1_2_1", TokenType.THIS)), sequence("PrimaryPrefix_1_3", terminal("PrimaryPrefix_1_3_1", TokenType.SUPER), PrimaryPrefix_1_3_2), PrimaryPrefix_1_4, sequence("PrimaryPrefix_1_5", PrimaryPrefix_1_5_1, terminal("PrimaryPrefix_1_5_2", TokenType.DOT), terminal("PrimaryPrefix_1_5_3", TokenType.CLASS)), sequence("PrimaryPrefix_1_6", PrimaryPrefix_1_6_1, PrimaryPrefix_1_6_2), sequence("PrimaryPrefix_1_7", PrimaryPrefix_1_7_1), sequence("PrimaryPrefix_1_8", PrimaryPrefix_1_8_1), sequence("PrimaryPrefix_1_9", terminal("PrimaryPrefix_1_9_1", TokenType.LPAREN), PrimaryPrefix_1_9_2, terminal("PrimaryPrefix_1_9_3", TokenType.RPAREN)));

		static final Sequence PrimaryPrefix = sequence("PrimaryPrefix", PrimaryPrefix_1);

		static final NonTerminal PrimarySuffix_1_1_1 = nonTerminal("PrimarySuffix_1_1_1", PRIMARY_SUFFIX_WITHOUT_SUPER);

		static final NonTerminal PrimarySuffix_1_3 = nonTerminal("PrimarySuffix_1_3", METHOD_REFERENCE_SUFFIX);

		static final Choice PrimarySuffix_1 = choice("PrimarySuffix_1", sequence("PrimarySuffix_1_1", PrimarySuffix_1_1_1), sequence("PrimarySuffix_1_2", terminal("PrimarySuffix_1_2_1", TokenType.DOT), terminal("PrimarySuffix_1_2_2", TokenType.SUPER)), PrimarySuffix_1_3);

		static final Sequence PrimarySuffix = sequence("PrimarySuffix", PrimarySuffix_1);

		static final NonTerminal PrimarySuffixWithoutSuper_1_1_2_2 = nonTerminal("PrimarySuffixWithoutSuper_1_1_2_2", CLASS_CREATION_EXPR);

		static final NonTerminal PrimarySuffixWithoutSuper_1_1_2_3 = nonTerminal("PrimarySuffixWithoutSuper_1_1_2_3", METHOD_INVOCATION);

		static final NonTerminal PrimarySuffixWithoutSuper_1_1_2_4 = nonTerminal("PrimarySuffixWithoutSuper_1_1_2_4", FIELD_ACCESS);

		static final Choice PrimarySuffixWithoutSuper_1_1_2 = choice("PrimarySuffixWithoutSuper_1_1_2", sequence("PrimarySuffixWithoutSuper_1_1_2_1", terminal("PrimarySuffixWithoutSuper_1_1_2_1_1", TokenType.THIS)), PrimarySuffixWithoutSuper_1_1_2_2, PrimarySuffixWithoutSuper_1_1_2_3, PrimarySuffixWithoutSuper_1_1_2_4);

		static final NonTerminal PrimarySuffixWithoutSuper_1_2_2 = nonTerminal("PrimarySuffixWithoutSuper_1_2_2", EXPRESSION);

		static final Choice PrimarySuffixWithoutSuper_1 = choice("PrimarySuffixWithoutSuper_1", sequence("PrimarySuffixWithoutSuper_1_1", terminal("PrimarySuffixWithoutSuper_1_1_1", TokenType.DOT), PrimarySuffixWithoutSuper_1_1_2), sequence("PrimarySuffixWithoutSuper_1_2", terminal("PrimarySuffixWithoutSuper_1_2_1", TokenType.LBRACKET), PrimarySuffixWithoutSuper_1_2_2, terminal("PrimarySuffixWithoutSuper_1_2_3", TokenType.RBRACKET)));

		static final Sequence PrimarySuffixWithoutSuper = sequence("PrimarySuffixWithoutSuper", PrimarySuffixWithoutSuper_1);

		static final NonTerminal FieldAccess_1 = nonTerminal("FieldAccess_1", NAME);

		static final Sequence FieldAccess = sequence("FieldAccess", FieldAccess_1);

		static final NonTerminal MethodInvocation_1_1 = nonTerminal("MethodInvocation_1_1", TYPE_ARGUMENTS);

		static final ZeroOrOne MethodInvocation_1 = zeroOrOne("MethodInvocation_1", MethodInvocation_1_1);

		static final NonTerminal MethodInvocation_2 = nonTerminal("MethodInvocation_2", NAME);

		static final NonTerminal MethodInvocation_3 = nonTerminal("MethodInvocation_3", ARGUMENTS);

		static final Sequence MethodInvocation = sequence("MethodInvocation", MethodInvocation_1, MethodInvocation_2, MethodInvocation_3);

		static final NonTerminal Arguments_2_1_1_1 = nonTerminal("Arguments_2_1_1_1", NODE_LIST_VAR);

		static final NonTerminal Arguments_2_1_2_1 = nonTerminal("Arguments_2_1_2_1", EXPRESSION);

		static final NonTerminal Arguments_2_1_2_2_2 = nonTerminal("Arguments_2_1_2_2_2", EXPRESSION);

		static final ZeroOrMore Arguments_2_1_2_2 = zeroOrMore("Arguments_2_1_2_2", sequence("Arguments_2_1_2_2", terminal("Arguments_2_1_2_2_1", TokenType.COMMA), Arguments_2_1_2_2_2));

		static final Choice Arguments_2_1 = choice("Arguments_2_1", sequence("Arguments_2_1_1", Arguments_2_1_1_1), sequence("Arguments_2_1_2", Arguments_2_1_2_1, Arguments_2_1_2_2));

		static final ZeroOrOne Arguments_2 = zeroOrOne("Arguments_2", Arguments_2_1);

		static final Sequence Arguments = sequence("Arguments", terminal("Arguments_1", TokenType.LPAREN), Arguments_2, terminal("Arguments_3", TokenType.RPAREN));

		static final NonTerminal MethodReferenceSuffix_2_1 = nonTerminal("MethodReferenceSuffix_2_1", TYPE_ARGUMENTS);

		static final ZeroOrOne MethodReferenceSuffix_2 = zeroOrOne("MethodReferenceSuffix_2", MethodReferenceSuffix_2_1);

		static final NonTerminal MethodReferenceSuffix_3_1 = nonTerminal("MethodReferenceSuffix_3_1", NAME);

		static final Choice MethodReferenceSuffix_3 = choice("MethodReferenceSuffix_3", MethodReferenceSuffix_3_1, sequence("MethodReferenceSuffix_3_2", terminal("MethodReferenceSuffix_3_2_1", TokenType.NEW)));

		static final Sequence MethodReferenceSuffix = sequence("MethodReferenceSuffix", terminal("MethodReferenceSuffix_1", TokenType.DOUBLECOLON), MethodReferenceSuffix_2, MethodReferenceSuffix_3);

		static final NonTerminal ClassCreationExpr_2_1 = nonTerminal("ClassCreationExpr_2_1", TYPE_ARGUMENTS);

		static final ZeroOrOne ClassCreationExpr_2 = zeroOrOne("ClassCreationExpr_2", ClassCreationExpr_2_1);

		static final NonTerminal ClassCreationExpr_3 = nonTerminal("ClassCreationExpr_3", ANNOTATIONS);

		static final NonTerminal ClassCreationExpr_4 = nonTerminal("ClassCreationExpr_4", QUALIFIED_TYPE);

		static final NonTerminal ClassCreationExpr_5 = nonTerminal("ClassCreationExpr_5", ARGUMENTS);

		static final NonTerminal ClassCreationExpr_6_1 = nonTerminal("ClassCreationExpr_6_1", CLASS_OR_INTERFACE_BODY);

		static final ZeroOrOne ClassCreationExpr_6 = zeroOrOne("ClassCreationExpr_6", ClassCreationExpr_6_1);

		static final Sequence ClassCreationExpr = sequence("ClassCreationExpr", terminal("ClassCreationExpr_1", TokenType.NEW), ClassCreationExpr_2, ClassCreationExpr_3, ClassCreationExpr_4, ClassCreationExpr_5, ClassCreationExpr_6);

		static final NonTerminal ArrayCreationExpr_2_1 = nonTerminal("ArrayCreationExpr_2_1", TYPE_ARGUMENTS);

		static final ZeroOrOne ArrayCreationExpr_2 = zeroOrOne("ArrayCreationExpr_2", ArrayCreationExpr_2_1);

		static final NonTerminal ArrayCreationExpr_3 = nonTerminal("ArrayCreationExpr_3", ANNOTATIONS);

		static final NonTerminal ArrayCreationExpr_4_1 = nonTerminal("ArrayCreationExpr_4_1", PRIMITIVE_TYPE);

		static final NonTerminal ArrayCreationExpr_4_2 = nonTerminal("ArrayCreationExpr_4_2", QUALIFIED_TYPE);

		static final Choice ArrayCreationExpr_4 = choice("ArrayCreationExpr_4", ArrayCreationExpr_4_1, ArrayCreationExpr_4_2);

		static final NonTerminal ArrayCreationExpr_5 = nonTerminal("ArrayCreationExpr_5", ARRAY_CREATION_EXPR_REST);

		static final Sequence ArrayCreationExpr = sequence("ArrayCreationExpr", terminal("ArrayCreationExpr_1", TokenType.NEW), ArrayCreationExpr_2, ArrayCreationExpr_3, ArrayCreationExpr_4, ArrayCreationExpr_5);

		static final NonTerminal ArrayCreationExprRest_1_1 = nonTerminal("ArrayCreationExprRest_1_1", ARRAY_DIM_EXPRS_MANDATORY);

		static final NonTerminal ArrayCreationExprRest_1_2 = nonTerminal("ArrayCreationExprRest_1_2", ARRAY_DIMS);

		static final NonTerminal ArrayCreationExprRest_2_1 = nonTerminal("ArrayCreationExprRest_2_1", ARRAY_DIMS_MANDATORY);

		static final NonTerminal ArrayCreationExprRest_2_2 = nonTerminal("ArrayCreationExprRest_2_2", ARRAY_INITIALIZER);

		static final Choice ArrayCreationExprRest = choice("ArrayCreationExprRest", sequence("ArrayCreationExprRest_1", ArrayCreationExprRest_1_1, ArrayCreationExprRest_1_2), sequence("ArrayCreationExprRest_2", ArrayCreationExprRest_2_1, ArrayCreationExprRest_2_2));

		static final NonTerminal ArrayDimExprsMandatory_1_1 = nonTerminal("ArrayDimExprsMandatory_1_1", ANNOTATIONS);

		static final NonTerminal ArrayDimExprsMandatory_1_3 = nonTerminal("ArrayDimExprsMandatory_1_3", EXPRESSION);

		static final OneOrMore ArrayDimExprsMandatory_1 = oneOrMore("ArrayDimExprsMandatory_1", sequence("ArrayDimExprsMandatory_1", ArrayDimExprsMandatory_1_1, terminal("ArrayDimExprsMandatory_1_2", TokenType.LBRACKET), ArrayDimExprsMandatory_1_3, terminal("ArrayDimExprsMandatory_1_4", TokenType.RBRACKET)));

		static final Sequence ArrayDimExprsMandatory = sequence("ArrayDimExprsMandatory", ArrayDimExprsMandatory_1);

		static final NonTerminal ArrayDimsMandatory_1_1 = nonTerminal("ArrayDimsMandatory_1_1", ANNOTATIONS);

		static final OneOrMore ArrayDimsMandatory_1 = oneOrMore("ArrayDimsMandatory_1", sequence("ArrayDimsMandatory_1", ArrayDimsMandatory_1_1, terminal("ArrayDimsMandatory_1_2", TokenType.LBRACKET), terminal("ArrayDimsMandatory_1_3", TokenType.RBRACKET)));

		static final Sequence ArrayDimsMandatory = sequence("ArrayDimsMandatory", ArrayDimsMandatory_1);

		static final NonTerminal Statement_1_1 = nonTerminal("Statement_1_1", LABELED_STATEMENT);

		static final NonTerminal Statement_1_2 = nonTerminal("Statement_1_2", ASSERT_STATEMENT);

		static final NonTerminal Statement_1_3 = nonTerminal("Statement_1_3", BLOCK);

		static final NonTerminal Statement_1_4 = nonTerminal("Statement_1_4", EMPTY_STATEMENT);

		static final NonTerminal Statement_1_5 = nonTerminal("Statement_1_5", EXPRESSION_STATEMENT);

		static final NonTerminal Statement_1_6 = nonTerminal("Statement_1_6", SWITCH_STATEMENT);

		static final NonTerminal Statement_1_7 = nonTerminal("Statement_1_7", IF_STATEMENT);

		static final NonTerminal Statement_1_8 = nonTerminal("Statement_1_8", WHILE_STATEMENT);

		static final NonTerminal Statement_1_9 = nonTerminal("Statement_1_9", DO_STATEMENT);

		static final NonTerminal Statement_1_10 = nonTerminal("Statement_1_10", FOR_STATEMENT);

		static final NonTerminal Statement_1_11 = nonTerminal("Statement_1_11", BREAK_STATEMENT);

		static final NonTerminal Statement_1_12 = nonTerminal("Statement_1_12", CONTINUE_STATEMENT);

		static final NonTerminal Statement_1_13 = nonTerminal("Statement_1_13", RETURN_STATEMENT);

		static final NonTerminal Statement_1_14 = nonTerminal("Statement_1_14", THROW_STATEMENT);

		static final NonTerminal Statement_1_15 = nonTerminal("Statement_1_15", SYNCHRONIZED_STATEMENT);

		static final NonTerminal Statement_1_16 = nonTerminal("Statement_1_16", TRY_STATEMENT);

		static final Choice Statement_1 = choice("Statement_1", Statement_1_1, Statement_1_2, Statement_1_3, Statement_1_4, Statement_1_5, Statement_1_6, Statement_1_7, Statement_1_8, Statement_1_9, Statement_1_10, Statement_1_11, Statement_1_12, Statement_1_13, Statement_1_14, Statement_1_15, Statement_1_16);

		static final Sequence Statement = sequence("Statement", Statement_1);

		static final NonTerminal AssertStatement_2 = nonTerminal("AssertStatement_2", EXPRESSION);

		static final NonTerminal AssertStatement_3_2 = nonTerminal("AssertStatement_3_2", EXPRESSION);

		static final ZeroOrOne AssertStatement_3 = zeroOrOne("AssertStatement_3", sequence("AssertStatement_3", terminal("AssertStatement_3_1", TokenType.COLON), AssertStatement_3_2));

		static final Sequence AssertStatement = sequence("AssertStatement", terminal("AssertStatement_1", TokenType.ASSERT), AssertStatement_2, AssertStatement_3, terminal("AssertStatement_4", TokenType.SEMICOLON));

		static final NonTerminal LabeledStatement_1 = nonTerminal("LabeledStatement_1", NAME);

		static final NonTerminal LabeledStatement_3 = nonTerminal("LabeledStatement_3", STATEMENT);

		static final Sequence LabeledStatement = sequence("LabeledStatement", LabeledStatement_1, terminal("LabeledStatement_2", TokenType.COLON), LabeledStatement_3);

		static final NonTerminal Block_2 = nonTerminal("Block_2", STATEMENTS);

		static final Sequence Block = sequence("Block", terminal("Block_1", TokenType.LBRACE), Block_2, terminal("Block_3", TokenType.RBRACE));

		static final NonTerminal BlockStatement_1_1_1 = nonTerminal("BlockStatement_1_1_1", MODIFIERS_NO_DEFAULT);

		static final NonTerminal BlockStatement_1_1_2 = nonTerminal("BlockStatement_1_1_2", CLASS_OR_INTERFACE_DECL);

		static final NonTerminal BlockStatement_1_2_1 = nonTerminal("BlockStatement_1_2_1", VARIABLE_DECL_EXPRESSION);

		static final NonTerminal BlockStatement_1_3 = nonTerminal("BlockStatement_1_3", STATEMENT);

		static final Choice BlockStatement_1 = choice("BlockStatement_1", sequence("BlockStatement_1_1", BlockStatement_1_1_1, BlockStatement_1_1_2), sequence("BlockStatement_1_2", BlockStatement_1_2_1, terminal("BlockStatement_1_2_2", TokenType.SEMICOLON)), BlockStatement_1_3);

		static final Sequence BlockStatement = sequence("BlockStatement", BlockStatement_1);

		static final NonTerminal VariableDeclExpression_1 = nonTerminal("VariableDeclExpression_1", MODIFIERS_NO_DEFAULT);

		static final NonTerminal VariableDeclExpression_2 = nonTerminal("VariableDeclExpression_2", VARIABLE_DECL);

		static final Sequence VariableDeclExpression = sequence("VariableDeclExpression", VariableDeclExpression_1, VariableDeclExpression_2);

		static final Sequence EmptyStatement = sequence("EmptyStatement", terminal("EmptyStatement_1", TokenType.SEMICOLON));

		static final NonTerminal ExpressionStatement_1 = nonTerminal("ExpressionStatement_1", STATEMENT_EXPRESSION);

		static final Sequence ExpressionStatement = sequence("ExpressionStatement", ExpressionStatement_1, terminal("ExpressionStatement_2", TokenType.SEMICOLON));

		static final NonTerminal StatementExpression_1 = nonTerminal("StatementExpression_1", EXPRESSION);

		static final Sequence StatementExpression = sequence("StatementExpression", StatementExpression_1);

		static final NonTerminal SwitchStatement_3 = nonTerminal("SwitchStatement_3", EXPRESSION);

		static final NonTerminal SwitchStatement_6_1 = nonTerminal("SwitchStatement_6_1", SWITCH_ENTRY);

		static final ZeroOrMore SwitchStatement_6 = zeroOrMore("SwitchStatement_6", SwitchStatement_6_1);

		static final Sequence SwitchStatement = sequence("SwitchStatement", terminal("SwitchStatement_1", TokenType.SWITCH), terminal("SwitchStatement_2", TokenType.LPAREN), SwitchStatement_3, terminal("SwitchStatement_4", TokenType.RPAREN), terminal("SwitchStatement_5", TokenType.LBRACE), SwitchStatement_6, terminal("SwitchStatement_7", TokenType.RBRACE));

		static final NonTerminal SwitchEntry_1_1_2 = nonTerminal("SwitchEntry_1_1_2", EXPRESSION);

		static final Choice SwitchEntry_1 = choice("SwitchEntry_1", sequence("SwitchEntry_1_1", terminal("SwitchEntry_1_1_1", TokenType.CASE), SwitchEntry_1_1_2), terminal("SwitchEntry_1_2", TokenType.DEFAULT));

		static final NonTerminal SwitchEntry_3 = nonTerminal("SwitchEntry_3", STATEMENTS);

		static final Sequence SwitchEntry = sequence("SwitchEntry", SwitchEntry_1, terminal("SwitchEntry_2", TokenType.COLON), SwitchEntry_3);

		static final NonTerminal IfStatement_3 = nonTerminal("IfStatement_3", EXPRESSION);

		static final NonTerminal IfStatement_5 = nonTerminal("IfStatement_5", STATEMENT);

		static final NonTerminal IfStatement_6_2 = nonTerminal("IfStatement_6_2", STATEMENT);

		static final ZeroOrOne IfStatement_6 = zeroOrOne("IfStatement_6", sequence("IfStatement_6", terminal("IfStatement_6_1", TokenType.ELSE), IfStatement_6_2));

		static final Sequence IfStatement = sequence("IfStatement", terminal("IfStatement_1", TokenType.IF), terminal("IfStatement_2", TokenType.LPAREN), IfStatement_3, terminal("IfStatement_4", TokenType.RPAREN), IfStatement_5, IfStatement_6);

		static final NonTerminal WhileStatement_3 = nonTerminal("WhileStatement_3", EXPRESSION);

		static final NonTerminal WhileStatement_5 = nonTerminal("WhileStatement_5", STATEMENT);

		static final Sequence WhileStatement = sequence("WhileStatement", terminal("WhileStatement_1", TokenType.WHILE), terminal("WhileStatement_2", TokenType.LPAREN), WhileStatement_3, terminal("WhileStatement_4", TokenType.RPAREN), WhileStatement_5);

		static final NonTerminal DoStatement_2 = nonTerminal("DoStatement_2", STATEMENT);

		static final NonTerminal DoStatement_5 = nonTerminal("DoStatement_5", EXPRESSION);

		static final Sequence DoStatement = sequence("DoStatement", terminal("DoStatement_1", TokenType.DO), DoStatement_2, terminal("DoStatement_3", TokenType.WHILE), terminal("DoStatement_4", TokenType.LPAREN), DoStatement_5, terminal("DoStatement_6", TokenType.RPAREN), terminal("DoStatement_7", TokenType.SEMICOLON));

		static final NonTerminal ForStatement_3_1_1 = nonTerminal("ForStatement_3_1_1", VARIABLE_DECL_EXPRESSION);

		static final NonTerminal ForStatement_3_1_3 = nonTerminal("ForStatement_3_1_3", EXPRESSION);

		static final NonTerminal ForStatement_3_2_1_1 = nonTerminal("ForStatement_3_2_1_1", FOR_INIT);

		static final ZeroOrOne ForStatement_3_2_1 = zeroOrOne("ForStatement_3_2_1", ForStatement_3_2_1_1);

		static final NonTerminal ForStatement_3_2_3_1 = nonTerminal("ForStatement_3_2_3_1", EXPRESSION);

		static final ZeroOrOne ForStatement_3_2_3 = zeroOrOne("ForStatement_3_2_3", ForStatement_3_2_3_1);

		static final NonTerminal ForStatement_3_2_5_1 = nonTerminal("ForStatement_3_2_5_1", FOR_UPDATE);

		static final ZeroOrOne ForStatement_3_2_5 = zeroOrOne("ForStatement_3_2_5", ForStatement_3_2_5_1);

		static final Choice ForStatement_3 = choice("ForStatement_3", sequence("ForStatement_3_1", ForStatement_3_1_1, terminal("ForStatement_3_1_2", TokenType.COLON), ForStatement_3_1_3), sequence("ForStatement_3_2", ForStatement_3_2_1, terminal("ForStatement_3_2_2", TokenType.SEMICOLON), ForStatement_3_2_3, terminal("ForStatement_3_2_4", TokenType.SEMICOLON), ForStatement_3_2_5));

		static final NonTerminal ForStatement_5 = nonTerminal("ForStatement_5", STATEMENT);

		static final Sequence ForStatement = sequence("ForStatement", terminal("ForStatement_1", TokenType.FOR), terminal("ForStatement_2", TokenType.LPAREN), ForStatement_3, terminal("ForStatement_4", TokenType.RPAREN), ForStatement_5);

		static final NonTerminal ForInit_1_1_1 = nonTerminal("ForInit_1_1_1", VARIABLE_DECL_EXPRESSION);

		static final NonTerminal ForInit_1_2 = nonTerminal("ForInit_1_2", STATEMENT_EXPRESSION_LIST);

		static final Choice ForInit_1 = choice("ForInit_1", sequence("ForInit_1_1", ForInit_1_1_1), ForInit_1_2);

		static final Sequence ForInit = sequence("ForInit", ForInit_1);

		static final NonTerminal StatementExpressionList_1 = nonTerminal("StatementExpressionList_1", STATEMENT_EXPRESSION);

		static final NonTerminal StatementExpressionList_2_2 = nonTerminal("StatementExpressionList_2_2", STATEMENT_EXPRESSION);

		static final ZeroOrMore StatementExpressionList_2 = zeroOrMore("StatementExpressionList_2", sequence("StatementExpressionList_2", terminal("StatementExpressionList_2_1", TokenType.COMMA), StatementExpressionList_2_2));

		static final Sequence StatementExpressionList = sequence("StatementExpressionList", StatementExpressionList_1, StatementExpressionList_2);

		static final NonTerminal ForUpdate_1 = nonTerminal("ForUpdate_1", STATEMENT_EXPRESSION_LIST);

		static final Sequence ForUpdate = sequence("ForUpdate", ForUpdate_1);

		static final NonTerminal BreakStatement_2_1 = nonTerminal("BreakStatement_2_1", NAME);

		static final ZeroOrOne BreakStatement_2 = zeroOrOne("BreakStatement_2", BreakStatement_2_1);

		static final Sequence BreakStatement = sequence("BreakStatement", terminal("BreakStatement_1", TokenType.BREAK), BreakStatement_2, terminal("BreakStatement_3", TokenType.SEMICOLON));

		static final NonTerminal ContinueStatement_2_1 = nonTerminal("ContinueStatement_2_1", NAME);

		static final ZeroOrOne ContinueStatement_2 = zeroOrOne("ContinueStatement_2", ContinueStatement_2_1);

		static final Sequence ContinueStatement = sequence("ContinueStatement", terminal("ContinueStatement_1", TokenType.CONTINUE), ContinueStatement_2, terminal("ContinueStatement_3", TokenType.SEMICOLON));

		static final NonTerminal ReturnStatement_2_1 = nonTerminal("ReturnStatement_2_1", EXPRESSION);

		static final ZeroOrOne ReturnStatement_2 = zeroOrOne("ReturnStatement_2", ReturnStatement_2_1);

		static final Sequence ReturnStatement = sequence("ReturnStatement", terminal("ReturnStatement_1", TokenType.RETURN), ReturnStatement_2, terminal("ReturnStatement_3", TokenType.SEMICOLON));

		static final NonTerminal ThrowStatement_2 = nonTerminal("ThrowStatement_2", EXPRESSION);

		static final Sequence ThrowStatement = sequence("ThrowStatement", terminal("ThrowStatement_1", TokenType.THROW), ThrowStatement_2, terminal("ThrowStatement_3", TokenType.SEMICOLON));

		static final NonTerminal SynchronizedStatement_3 = nonTerminal("SynchronizedStatement_3", EXPRESSION);

		static final NonTerminal SynchronizedStatement_5 = nonTerminal("SynchronizedStatement_5", BLOCK);

		static final Sequence SynchronizedStatement = sequence("SynchronizedStatement", terminal("SynchronizedStatement_1", TokenType.SYNCHRONIZED), terminal("SynchronizedStatement_2", TokenType.LPAREN), SynchronizedStatement_3, terminal("SynchronizedStatement_4", TokenType.RPAREN), SynchronizedStatement_5);

		static final NonTerminal TryStatement_2_1_1 = nonTerminal("TryStatement_2_1_1", RESOURCE_SPECIFICATION);

		static final NonTerminal TryStatement_2_1_2 = nonTerminal("TryStatement_2_1_2", BLOCK);

		static final NonTerminal TryStatement_2_1_3_1 = nonTerminal("TryStatement_2_1_3_1", CATCH_CLAUSES);

		static final ZeroOrOne TryStatement_2_1_3 = zeroOrOne("TryStatement_2_1_3", TryStatement_2_1_3_1);

		static final NonTerminal TryStatement_2_1_4_2 = nonTerminal("TryStatement_2_1_4_2", BLOCK);

		static final ZeroOrOne TryStatement_2_1_4 = zeroOrOne("TryStatement_2_1_4", sequence("TryStatement_2_1_4", terminal("TryStatement_2_1_4_1", TokenType.FINALLY), TryStatement_2_1_4_2));

		static final NonTerminal TryStatement_2_2_1 = nonTerminal("TryStatement_2_2_1", BLOCK);

		static final NonTerminal TryStatement_2_2_2_1_1 = nonTerminal("TryStatement_2_2_2_1_1", CATCH_CLAUSES);

		static final NonTerminal TryStatement_2_2_2_1_2_2 = nonTerminal("TryStatement_2_2_2_1_2_2", BLOCK);

		static final ZeroOrOne TryStatement_2_2_2_1_2 = zeroOrOne("TryStatement_2_2_2_1_2", sequence("TryStatement_2_2_2_1_2", terminal("TryStatement_2_2_2_1_2_1", TokenType.FINALLY), TryStatement_2_2_2_1_2_2));

		static final NonTerminal TryStatement_2_2_2_2_2 = nonTerminal("TryStatement_2_2_2_2_2", BLOCK);

		static final Choice TryStatement_2_2_2 = choice("TryStatement_2_2_2", sequence("TryStatement_2_2_2_1", TryStatement_2_2_2_1_1, TryStatement_2_2_2_1_2), sequence("TryStatement_2_2_2_2", terminal("TryStatement_2_2_2_2_1", TokenType.FINALLY), TryStatement_2_2_2_2_2));

		static final Choice TryStatement_2 = choice("TryStatement_2", sequence("TryStatement_2_1", TryStatement_2_1_1, TryStatement_2_1_2, TryStatement_2_1_3, TryStatement_2_1_4), sequence("TryStatement_2_2", TryStatement_2_2_1, TryStatement_2_2_2));

		static final Sequence TryStatement = sequence("TryStatement", terminal("TryStatement_1", TokenType.TRY), TryStatement_2);

		static final NonTerminal CatchClauses_1_1 = nonTerminal("CatchClauses_1_1", CATCH_CLAUSE);

		static final OneOrMore CatchClauses_1 = oneOrMore("CatchClauses_1", CatchClauses_1_1);

		static final Sequence CatchClauses = sequence("CatchClauses", CatchClauses_1);

		static final NonTerminal CatchClause_3 = nonTerminal("CatchClause_3", CATCH_FORMAL_PARAMETER);

		static final NonTerminal CatchClause_5 = nonTerminal("CatchClause_5", BLOCK);

		static final Sequence CatchClause = sequence("CatchClause", terminal("CatchClause_1", TokenType.CATCH), terminal("CatchClause_2", TokenType.LPAREN), CatchClause_3, terminal("CatchClause_4", TokenType.RPAREN), CatchClause_5);

		static final NonTerminal CatchFormalParameter_1 = nonTerminal("CatchFormalParameter_1", MODIFIERS);

		static final NonTerminal CatchFormalParameter_2 = nonTerminal("CatchFormalParameter_2", QUALIFIED_TYPE);

		static final NonTerminal CatchFormalParameter_3_1_2 = nonTerminal("CatchFormalParameter_3_1_2", ANNOTATED_QUALIFIED_TYPE);

		static final OneOrMore CatchFormalParameter_3_1 = oneOrMore("CatchFormalParameter_3_1", sequence("CatchFormalParameter_3_1", terminal("CatchFormalParameter_3_1_1", TokenType.BIT_OR), CatchFormalParameter_3_1_2));

		static final ZeroOrOne CatchFormalParameter_3 = zeroOrOne("CatchFormalParameter_3", CatchFormalParameter_3_1);

		static final NonTerminal CatchFormalParameter_4 = nonTerminal("CatchFormalParameter_4", VARIABLE_DECLARATOR_ID);

		static final Sequence CatchFormalParameter = sequence("CatchFormalParameter", CatchFormalParameter_1, CatchFormalParameter_2, CatchFormalParameter_3, CatchFormalParameter_4);

		static final NonTerminal ResourceSpecification_2 = nonTerminal("ResourceSpecification_2", VARIABLE_DECL_EXPRESSION);

		static final NonTerminal ResourceSpecification_3_2 = nonTerminal("ResourceSpecification_3_2", VARIABLE_DECL_EXPRESSION);

		static final ZeroOrMore ResourceSpecification_3 = zeroOrMore("ResourceSpecification_3", sequence("ResourceSpecification_3", terminal("ResourceSpecification_3_1", TokenType.SEMICOLON), ResourceSpecification_3_2));

		static final ZeroOrOne ResourceSpecification_4 = zeroOrOne("ResourceSpecification_4", terminal("ResourceSpecification_4_1", TokenType.SEMICOLON));

		static final Sequence ResourceSpecification = sequence("ResourceSpecification", terminal("ResourceSpecification_1", TokenType.LPAREN), ResourceSpecification_2, ResourceSpecification_3, ResourceSpecification_4, terminal("ResourceSpecification_5", TokenType.RPAREN));

		static final Sequence RUNSIGNEDSHIFT = sequence("RUNSIGNEDSHIFT", terminal("RUNSIGNEDSHIFT_1", TokenType.GT), terminal("RUNSIGNEDSHIFT_2", TokenType.GT), terminal("RUNSIGNEDSHIFT_3", TokenType.GT));

		static final Sequence RSIGNEDSHIFT = sequence("RSIGNEDSHIFT", terminal("RSIGNEDSHIFT_1", TokenType.GT), terminal("RSIGNEDSHIFT_2", TokenType.GT));

		static final NonTerminal Annotations_1_1 = nonTerminal("Annotations_1_1", ANNOTATION);

		static final ZeroOrMore Annotations_1 = zeroOrMore("Annotations_1", Annotations_1_1);

		static final Sequence Annotations = sequence("Annotations", Annotations_1);

		static final NonTerminal Annotation_1_1 = nonTerminal("Annotation_1_1", NORMAL_ANNOTATION);

		static final NonTerminal Annotation_1_2 = nonTerminal("Annotation_1_2", MARKER_ANNOTATION);

		static final NonTerminal Annotation_1_3 = nonTerminal("Annotation_1_3", SINGLE_ELEMENT_ANNOTATION);

		static final Choice Annotation_1 = choice("Annotation_1", Annotation_1_1, Annotation_1_2, Annotation_1_3);

		static final Sequence Annotation = sequence("Annotation", Annotation_1);

		static final NonTerminal NormalAnnotation_2 = nonTerminal("NormalAnnotation_2", QUALIFIED_NAME);

		static final NonTerminal NormalAnnotation_4_1 = nonTerminal("NormalAnnotation_4_1", ELEMENT_VALUE_PAIR_LIST);

		static final ZeroOrOne NormalAnnotation_4 = zeroOrOne("NormalAnnotation_4", NormalAnnotation_4_1);

		static final Sequence NormalAnnotation = sequence("NormalAnnotation", terminal("NormalAnnotation_1", TokenType.AT), NormalAnnotation_2, terminal("NormalAnnotation_3", TokenType.LPAREN), NormalAnnotation_4, terminal("NormalAnnotation_5", TokenType.RPAREN));

		static final NonTerminal MarkerAnnotation_2 = nonTerminal("MarkerAnnotation_2", QUALIFIED_NAME);

		static final Sequence MarkerAnnotation = sequence("MarkerAnnotation", terminal("MarkerAnnotation_1", TokenType.AT), MarkerAnnotation_2);

		static final NonTerminal SingleElementAnnotation_2 = nonTerminal("SingleElementAnnotation_2", QUALIFIED_NAME);

		static final NonTerminal SingleElementAnnotation_4 = nonTerminal("SingleElementAnnotation_4", ELEMENT_VALUE);

		static final Sequence SingleElementAnnotation = sequence("SingleElementAnnotation", terminal("SingleElementAnnotation_1", TokenType.AT), SingleElementAnnotation_2, terminal("SingleElementAnnotation_3", TokenType.LPAREN), SingleElementAnnotation_4, terminal("SingleElementAnnotation_5", TokenType.RPAREN));

		static final NonTerminal ElementValuePairList_1 = nonTerminal("ElementValuePairList_1", ELEMENT_VALUE_PAIR);

		static final NonTerminal ElementValuePairList_2_2 = nonTerminal("ElementValuePairList_2_2", ELEMENT_VALUE_PAIR);

		static final ZeroOrMore ElementValuePairList_2 = zeroOrMore("ElementValuePairList_2", sequence("ElementValuePairList_2", terminal("ElementValuePairList_2_1", TokenType.COMMA), ElementValuePairList_2_2));

		static final Sequence ElementValuePairList = sequence("ElementValuePairList", ElementValuePairList_1, ElementValuePairList_2);

		static final NonTerminal ElementValuePair_1 = nonTerminal("ElementValuePair_1", NAME);

		static final NonTerminal ElementValuePair_3 = nonTerminal("ElementValuePair_3", ELEMENT_VALUE);

		static final Sequence ElementValuePair = sequence("ElementValuePair", ElementValuePair_1, terminal("ElementValuePair_2", TokenType.ASSIGN), ElementValuePair_3);

		static final NonTerminal ElementValue_1_1 = nonTerminal("ElementValue_1_1", CONDITIONAL_EXPRESSION);

		static final NonTerminal ElementValue_1_2 = nonTerminal("ElementValue_1_2", ELEMENT_VALUE_ARRAY_INITIALIZER);

		static final NonTerminal ElementValue_1_3 = nonTerminal("ElementValue_1_3", ANNOTATION);

		static final Choice ElementValue_1 = choice("ElementValue_1", ElementValue_1_1, ElementValue_1_2, ElementValue_1_3);

		static final Sequence ElementValue = sequence("ElementValue", ElementValue_1);

		static final NonTerminal ElementValueArrayInitializer_2_1 = nonTerminal("ElementValueArrayInitializer_2_1", ELEMENT_VALUE_LIST);

		static final ZeroOrOne ElementValueArrayInitializer_2 = zeroOrOne("ElementValueArrayInitializer_2", ElementValueArrayInitializer_2_1);

		static final ZeroOrOne ElementValueArrayInitializer_3 = zeroOrOne("ElementValueArrayInitializer_3", terminal("ElementValueArrayInitializer_3_1", TokenType.COMMA));

		static final Sequence ElementValueArrayInitializer = sequence("ElementValueArrayInitializer", terminal("ElementValueArrayInitializer_1", TokenType.LBRACE), ElementValueArrayInitializer_2, ElementValueArrayInitializer_3, terminal("ElementValueArrayInitializer_4", TokenType.RBRACE));

		static final NonTerminal ElementValueList_1 = nonTerminal("ElementValueList_1", ELEMENT_VALUE);

		static final NonTerminal ElementValueList_2_2 = nonTerminal("ElementValueList_2_2", ELEMENT_VALUE);

		static final ZeroOrMore ElementValueList_2 = zeroOrMore("ElementValueList_2", sequence("ElementValueList_2", terminal("ElementValueList_2_1", TokenType.COMMA), ElementValueList_2_2));

		static final Sequence ElementValueList = sequence("ElementValueList", ElementValueList_1, ElementValueList_2);

		protected void initializeProductions() {
			addProduction(COMPILATION_UNIT_ENTRY, CompilationUnitEntry, true);
			addProduction(PACKAGE_DECL_ENTRY, PackageDeclEntry, true);
			addProduction(IMPORT_DECL_ENTRY, ImportDeclEntry, true);
			addProduction(TYPE_DECL_ENTRY, TypeDeclEntry, true);
			addProduction(MEMBER_DECL_ENTRY, MemberDeclEntry, true);
			addProduction(ANNOTATION_MEMBER_DECL_ENTRY, AnnotationMemberDeclEntry, true);
			addProduction(MODIFIERS_ENTRY, ModifiersEntry, true);
			addProduction(ANNOTATIONS_ENTRY, AnnotationsEntry, true);
			addProduction(METHOD_DECL_ENTRY, MethodDeclEntry, true);
			addProduction(FIELD_DECL_ENTRY, FieldDeclEntry, true);
			addProduction(ANNOTATION_ELEMENT_DECL_ENTRY, AnnotationElementDeclEntry, true);
			addProduction(ENUM_CONSTANT_DECL_ENTRY, EnumConstantDeclEntry, true);
			addProduction(FORMAL_PARAMETER_ENTRY, FormalParameterEntry, true);
			addProduction(TYPE_PARAMETER_ENTRY, TypeParameterEntry, true);
			addProduction(STATEMENTS_ENTRY, StatementsEntry, true);
			addProduction(BLOCK_STATEMENT_ENTRY, BlockStatementEntry, true);
			addProduction(EXPRESSION_ENTRY, ExpressionEntry, true);
			addProduction(TYPE_ENTRY, TypeEntry, true);
			addProduction(QUALIFIED_NAME_ENTRY, QualifiedNameEntry, true);
			addProduction(NAME_ENTRY, NameEntry, true);
			addProduction(EPILOG, Epilog, false);
			addProduction(NODE_LIST_VAR, NodeListVar, false);
			addProduction(NODE_VAR, NodeVar, false);
			addProduction(COMPILATION_UNIT, CompilationUnit, false);
			addChoicePoint(COMPILATION_UNIT_1, CompilationUnit_1);
			addProduction(PACKAGE_DECL, PackageDecl, false);
			addProduction(IMPORT_DECLS, ImportDecls, false);
			addChoicePoint(IMPORT_DECLS_1, ImportDecls_1);
			addProduction(IMPORT_DECL, ImportDecl, false);
			addChoicePoint(IMPORT_DECL_2, ImportDecl_2);
			addChoicePoint(IMPORT_DECL_4, ImportDecl_4);
			addProduction(TYPE_DECLS, TypeDecls, false);
			addChoicePoint(TYPE_DECLS_1, TypeDecls_1);
			addProduction(MODIFIERS, Modifiers, false);
			addChoicePoint(MODIFIERS_1, Modifiers_1);
			addChoicePoint(MODIFIERS_1_1, Modifiers_1_1);
			addProduction(MODIFIERS_NO_DEFAULT, ModifiersNoDefault, false);
			addChoicePoint(MODIFIERS_NO_DEFAULT_1, ModifiersNoDefault_1);
			addChoicePoint(MODIFIERS_NO_DEFAULT_1_1, ModifiersNoDefault_1_1);
			addProduction(TYPE_DECL, TypeDecl, false);
			addChoicePoint(TYPE_DECL_1, TypeDecl_1);
			addChoicePoint(TYPE_DECL_1_2_2, TypeDecl_1_2_2);
			addProduction(CLASS_OR_INTERFACE_DECL, ClassOrInterfaceDecl, false);
			addChoicePoint(CLASS_OR_INTERFACE_DECL_1, ClassOrInterfaceDecl_1);
			addChoicePoint(CLASS_OR_INTERFACE_DECL_1_1_3, ClassOrInterfaceDecl_1_1_3);
			addChoicePoint(CLASS_OR_INTERFACE_DECL_1_1_4, ClassOrInterfaceDecl_1_1_4);
			addChoicePoint(CLASS_OR_INTERFACE_DECL_1_1_5, ClassOrInterfaceDecl_1_1_5);
			addChoicePoint(CLASS_OR_INTERFACE_DECL_1_2_3, ClassOrInterfaceDecl_1_2_3);
			addChoicePoint(CLASS_OR_INTERFACE_DECL_1_2_4, ClassOrInterfaceDecl_1_2_4);
			addProduction(EXTENDS_LIST, ExtendsList, false);
			addChoicePoint(EXTENDS_LIST_2, ExtendsList_2);
			addChoicePoint(EXTENDS_LIST_2_2_2, ExtendsList_2_2_2);
			addProduction(IMPLEMENTS_LIST, ImplementsList, false);
			addChoicePoint(IMPLEMENTS_LIST_2, ImplementsList_2);
			addChoicePoint(IMPLEMENTS_LIST_2_2_2, ImplementsList_2_2_2);
			addProduction(ENUM_DECL, EnumDecl, false);
			addChoicePoint(ENUM_DECL_3, EnumDecl_3);
			addChoicePoint(ENUM_DECL_5, EnumDecl_5);
			addChoicePoint(ENUM_DECL_5_1, EnumDecl_5_1);
			addChoicePoint(ENUM_DECL_5_1_2_2, EnumDecl_5_1_2_2);
			addChoicePoint(ENUM_DECL_6, EnumDecl_6);
			addChoicePoint(ENUM_DECL_7, EnumDecl_7);
			addProduction(ENUM_CONSTANT_DECL, EnumConstantDecl, false);
			addChoicePoint(ENUM_CONSTANT_DECL_3, EnumConstantDecl_3);
			addChoicePoint(ENUM_CONSTANT_DECL_4, EnumConstantDecl_4);
			addProduction(ANNOTATION_TYPE_DECL, AnnotationTypeDecl, false);
			addProduction(ANNOTATION_TYPE_BODY, AnnotationTypeBody, false);
			addChoicePoint(ANNOTATION_TYPE_BODY_2, AnnotationTypeBody_2);
			addChoicePoint(ANNOTATION_TYPE_BODY_2_1, AnnotationTypeBody_2_1);
			addChoicePoint(ANNOTATION_TYPE_BODY_2_1_2, AnnotationTypeBody_2_1_2);
			addProduction(ANNOTATION_TYPE_BODY_DECL, AnnotationTypeBodyDecl, false);
			addChoicePoint(ANNOTATION_TYPE_BODY_DECL_1, AnnotationTypeBodyDecl_1);
			addChoicePoint(ANNOTATION_TYPE_BODY_DECL_1_2_2, AnnotationTypeBodyDecl_1_2_2);
			addProduction(ANNOTATION_TYPE_MEMBER_DECL, AnnotationTypeMemberDecl, false);
			addChoicePoint(ANNOTATION_TYPE_MEMBER_DECL_6, AnnotationTypeMemberDecl_6);
			addProduction(TYPE_PARAMETERS, TypeParameters, false);
			addChoicePoint(TYPE_PARAMETERS_2, TypeParameters_2);
			addChoicePoint(TYPE_PARAMETERS_2_2_2, TypeParameters_2_2_2);
			addProduction(TYPE_PARAMETER, TypeParameter, false);
			addChoicePoint(TYPE_PARAMETER_3, TypeParameter_3);
			addProduction(TYPE_BOUNDS, TypeBounds, false);
			addChoicePoint(TYPE_BOUNDS_2, TypeBounds_2);
			addChoicePoint(TYPE_BOUNDS_2_2_2, TypeBounds_2_2_2);
			addProduction(CLASS_OR_INTERFACE_BODY, ClassOrInterfaceBody, false);
			addProduction(CLASS_OR_INTERFACE_BODY_DECLS, ClassOrInterfaceBodyDecls, false);
			addChoicePoint(CLASS_OR_INTERFACE_BODY_DECLS_1, ClassOrInterfaceBodyDecls_1);
			addChoicePoint(CLASS_OR_INTERFACE_BODY_DECLS_1_1, ClassOrInterfaceBodyDecls_1_1);
			addChoicePoint(CLASS_OR_INTERFACE_BODY_DECLS_1_1_2, ClassOrInterfaceBodyDecls_1_1_2);
			addProduction(CLASS_OR_INTERFACE_BODY_DECL, ClassOrInterfaceBodyDecl, false);
			addChoicePoint(CLASS_OR_INTERFACE_BODY_DECL_1, ClassOrInterfaceBodyDecl_1);
			addChoicePoint(CLASS_OR_INTERFACE_BODY_DECL_1_2_2, ClassOrInterfaceBodyDecl_1_2_2);
			addProduction(FIELD_DECL, FieldDecl, false);
			addProduction(VARIABLE_DECL, VariableDecl, false);
			addProduction(VARIABLE_DECLARATORS, VariableDeclarators, false);
			addChoicePoint(VARIABLE_DECLARATORS_2, VariableDeclarators_2);
			addProduction(VARIABLE_DECLARATOR, VariableDeclarator, false);
			addChoicePoint(VARIABLE_DECLARATOR_2, VariableDeclarator_2);
			addProduction(VARIABLE_DECLARATOR_ID, VariableDeclaratorId, false);
			addProduction(ARRAY_DIMS, ArrayDims, false);
			addChoicePoint(ARRAY_DIMS_1, ArrayDims_1);
			addProduction(VARIABLE_INITIALIZER, VariableInitializer, false);
			addChoicePoint(VARIABLE_INITIALIZER_1, VariableInitializer_1);
			addProduction(ARRAY_INITIALIZER, ArrayInitializer, false);
			addChoicePoint(ARRAY_INITIALIZER_2, ArrayInitializer_2);
			addChoicePoint(ARRAY_INITIALIZER_2_2, ArrayInitializer_2_2);
			addChoicePoint(ARRAY_INITIALIZER_3, ArrayInitializer_3);
			addProduction(METHOD_DECL, MethodDecl, false);
			addChoicePoint(METHOD_DECL_1, MethodDecl_1);
			addChoicePoint(METHOD_DECL_6, MethodDecl_6);
			addChoicePoint(METHOD_DECL_7, MethodDecl_7);
			addProduction(FORMAL_PARAMETERS, FormalParameters, false);
			addChoicePoint(FORMAL_PARAMETERS_2, FormalParameters_2);
			addProduction(FORMAL_PARAMETER_LIST, FormalParameterList, false);
			addChoicePoint(FORMAL_PARAMETER_LIST_1, FormalParameterList_1);
			addChoicePoint(FORMAL_PARAMETER_LIST_1_2_2, FormalParameterList_1_2_2);
			addProduction(FORMAL_PARAMETER, FormalParameter, false);
			addChoicePoint(FORMAL_PARAMETER_3, FormalParameter_3);
			addChoicePoint(FORMAL_PARAMETER_4, FormalParameter_4);
			addChoicePoint(FORMAL_PARAMETER_4_1_1, FormalParameter_4_1_1);
			addProduction(THROWS_CLAUSE, ThrowsClause, false);
			addChoicePoint(THROWS_CLAUSE_3, ThrowsClause_3);
			addProduction(CONSTRUCTOR_DECL, ConstructorDecl, false);
			addChoicePoint(CONSTRUCTOR_DECL_1, ConstructorDecl_1);
			addChoicePoint(CONSTRUCTOR_DECL_4, ConstructorDecl_4);
			addProduction(EXPLICIT_CONSTRUCTOR_INVOCATION, ExplicitConstructorInvocation, false);
			addChoicePoint(EXPLICIT_CONSTRUCTOR_INVOCATION_1, ExplicitConstructorInvocation_1);
			addChoicePoint(EXPLICIT_CONSTRUCTOR_INVOCATION_1_1_1, ExplicitConstructorInvocation_1_1_1);
			addChoicePoint(EXPLICIT_CONSTRUCTOR_INVOCATION_1_2_1, ExplicitConstructorInvocation_1_2_1);
			addChoicePoint(EXPLICIT_CONSTRUCTOR_INVOCATION_1_2_2, ExplicitConstructorInvocation_1_2_2);
			addProduction(STATEMENTS, Statements, false);
			addChoicePoint(STATEMENTS_1, Statements_1);
			addChoicePoint(STATEMENTS_1_1, Statements_1_1);
			addChoicePoint(STATEMENTS_1_1_2_1, Statements_1_1_2_1);
			addChoicePoint(STATEMENTS_1_1_2_2, Statements_1_1_2_2);
			addProduction(INITIALIZER_DECL, InitializerDecl, false);
			addProduction(TYPE, Type, false);
			addChoicePoint(TYPE_1, Type_1);
			addChoicePoint(TYPE_1_1_2, Type_1_1_2);
			addChoicePoint(TYPE_1_2_2, Type_1_2_2);
			addProduction(REFERENCE_TYPE, ReferenceType, false);
			addChoicePoint(REFERENCE_TYPE_1, ReferenceType_1);
			addChoicePoint(REFERENCE_TYPE_1_2_2, ReferenceType_1_2_2);
			addProduction(QUALIFIED_TYPE, QualifiedType, false);
			addChoicePoint(QUALIFIED_TYPE_2, QualifiedType_2);
			addChoicePoint(QUALIFIED_TYPE_3, QualifiedType_3);
			addChoicePoint(QUALIFIED_TYPE_3_4, QualifiedType_3_4);
			addProduction(TYPE_ARGUMENTS, TypeArguments, false);
			addChoicePoint(TYPE_ARGUMENTS_2, TypeArguments_2);
			addProduction(TYPE_ARGUMENTS_OR_DIAMOND, TypeArgumentsOrDiamond, false);
			addChoicePoint(TYPE_ARGUMENTS_OR_DIAMOND_2, TypeArgumentsOrDiamond_2);
			addProduction(TYPE_ARGUMENT_LIST, TypeArgumentList, false);
			addChoicePoint(TYPE_ARGUMENT_LIST, TypeArgumentList);
			addChoicePoint(TYPE_ARGUMENT_LIST_2_2, TypeArgumentList_2_2);
			addProduction(TYPE_ARGUMENT, TypeArgument, false);
			addChoicePoint(TYPE_ARGUMENT_2, TypeArgument_2);
			addProduction(WILDCARD, Wildcard, false);
			addChoicePoint(WILDCARD_2, Wildcard_2);
			addChoicePoint(WILDCARD_2_1, Wildcard_2_1);
			addProduction(PRIMITIVE_TYPE, PrimitiveType, false);
			addChoicePoint(PRIMITIVE_TYPE_1, PrimitiveType_1);
			addProduction(RESULT_TYPE, ResultType, false);
			addChoicePoint(RESULT_TYPE_1, ResultType_1);
			addProduction(ANNOTATED_QUALIFIED_TYPE, AnnotatedQualifiedType, false);
			addProduction(QUALIFIED_NAME, QualifiedName, false);
			addChoicePoint(QUALIFIED_NAME_2, QualifiedName_2);
			addProduction(NAME, Name, false);
			addChoicePoint(NAME_1, Name_1);
			addProduction(EXPRESSION, Expression, false);
			addChoicePoint(EXPRESSION_1, Expression_1);
			addProduction(ASSIGNMENT_EXPRESSION, AssignmentExpression, false);
			addChoicePoint(ASSIGNMENT_EXPRESSION_2, AssignmentExpression_2);
			addProduction(LAMBDA_EXPRESSION, LambdaExpression, false);
			addChoicePoint(LAMBDA_EXPRESSION_1, LambdaExpression_1);
			addProduction(LAMBDA_EXPRESSION_WITHOUT_CAST, LambdaExpressionWithoutCast, false);
			addChoicePoint(LAMBDA_EXPRESSION_WITHOUT_CAST_1, LambdaExpressionWithoutCast_1);
			addProduction(LAMBDA_BODY, LambdaBody, false);
			addChoicePoint(LAMBDA_BODY_1, LambdaBody_1);
			addProduction(INFERRED_FORMAL_PARAMETER_LIST, InferredFormalParameterList, false);
			addChoicePoint(INFERRED_FORMAL_PARAMETER_LIST_2, InferredFormalParameterList_2);
			addProduction(INFERRED_FORMAL_PARAMETER, InferredFormalParameter, false);
			addProduction(ASSIGNMENT_OPERATOR, AssignmentOperator, false);
			addChoicePoint(ASSIGNMENT_OPERATOR_1, AssignmentOperator_1);
			addProduction(CONDITIONAL_EXPRESSION, ConditionalExpression, false);
			addChoicePoint(CONDITIONAL_EXPRESSION_2, ConditionalExpression_2);
			addChoicePoint(CONDITIONAL_EXPRESSION_2_4, ConditionalExpression_2_4);
			addProduction(CONDITIONAL_OR_EXPRESSION, ConditionalOrExpression, false);
			addChoicePoint(CONDITIONAL_OR_EXPRESSION_2, ConditionalOrExpression_2);
			addProduction(CONDITIONAL_AND_EXPRESSION, ConditionalAndExpression, false);
			addChoicePoint(CONDITIONAL_AND_EXPRESSION_2, ConditionalAndExpression_2);
			addProduction(INCLUSIVE_OR_EXPRESSION, InclusiveOrExpression, false);
			addChoicePoint(INCLUSIVE_OR_EXPRESSION_2, InclusiveOrExpression_2);
			addProduction(EXCLUSIVE_OR_EXPRESSION, ExclusiveOrExpression, false);
			addChoicePoint(EXCLUSIVE_OR_EXPRESSION_2, ExclusiveOrExpression_2);
			addProduction(AND_EXPRESSION, AndExpression, false);
			addChoicePoint(AND_EXPRESSION_2, AndExpression_2);
			addProduction(EQUALITY_EXPRESSION, EqualityExpression, false);
			addChoicePoint(EQUALITY_EXPRESSION_2, EqualityExpression_2);
			addChoicePoint(EQUALITY_EXPRESSION_2_1, EqualityExpression_2_1);
			addProduction(INSTANCE_OF_EXPRESSION, InstanceOfExpression, false);
			addChoicePoint(INSTANCE_OF_EXPRESSION_2, InstanceOfExpression_2);
			addProduction(RELATIONAL_EXPRESSION, RelationalExpression, false);
			addChoicePoint(RELATIONAL_EXPRESSION_2, RelationalExpression_2);
			addChoicePoint(RELATIONAL_EXPRESSION_2_1, RelationalExpression_2_1);
			addProduction(SHIFT_EXPRESSION, ShiftExpression, false);
			addChoicePoint(SHIFT_EXPRESSION_2, ShiftExpression_2);
			addChoicePoint(SHIFT_EXPRESSION_2_1, ShiftExpression_2_1);
			addProduction(ADDITIVE_EXPRESSION, AdditiveExpression, false);
			addChoicePoint(ADDITIVE_EXPRESSION_2, AdditiveExpression_2);
			addChoicePoint(ADDITIVE_EXPRESSION_2_1, AdditiveExpression_2_1);
			addProduction(MULTIPLICATIVE_EXPRESSION, MultiplicativeExpression, false);
			addChoicePoint(MULTIPLICATIVE_EXPRESSION_2, MultiplicativeExpression_2);
			addChoicePoint(MULTIPLICATIVE_EXPRESSION_2_1, MultiplicativeExpression_2_1);
			addProduction(UNARY_EXPRESSION, UnaryExpression, false);
			addChoicePoint(UNARY_EXPRESSION_1, UnaryExpression_1);
			addChoicePoint(UNARY_EXPRESSION_1_2_1, UnaryExpression_1_2_1);
			addProduction(PREFIX_EXPRESSION, PrefixExpression, false);
			addChoicePoint(PREFIX_EXPRESSION_1, PrefixExpression_1);
			addProduction(UNARY_EXPRESSION_NOT_PLUS_MINUS, UnaryExpressionNotPlusMinus, false);
			addChoicePoint(UNARY_EXPRESSION_NOT_PLUS_MINUS_1, UnaryExpressionNotPlusMinus_1);
			addChoicePoint(UNARY_EXPRESSION_NOT_PLUS_MINUS_1_1_1, UnaryExpressionNotPlusMinus_1_1_1);
			addProduction(POSTFIX_EXPRESSION, PostfixExpression, false);
			addChoicePoint(POSTFIX_EXPRESSION_2, PostfixExpression_2);
			addChoicePoint(POSTFIX_EXPRESSION_2_1, PostfixExpression_2_1);
			addProduction(CAST_EXPRESSION, CastExpression, false);
			addChoicePoint(CAST_EXPRESSION_3, CastExpression_3);
			addProduction(REFERENCE_CAST_TYPE_REST, ReferenceCastTypeRest, false);
			addChoicePoint(REFERENCE_CAST_TYPE_REST_1, ReferenceCastTypeRest_1);
			addChoicePoint(REFERENCE_CAST_TYPE_REST_1_1, ReferenceCastTypeRest_1_1);
			addProduction(LITERAL, Literal, false);
			addChoicePoint(LITERAL_1, Literal_1);
			addProduction(PRIMARY_EXPRESSION, PrimaryExpression, false);
			addChoicePoint(PRIMARY_EXPRESSION_1, PrimaryExpression_1);
			addProduction(PRIMARY_NO_NEW_ARRAY, PrimaryNoNewArray, false);
			addChoicePoint(PRIMARY_NO_NEW_ARRAY_2, PrimaryNoNewArray_2);
			addProduction(PRIMARY_EXPRESSION_WITHOUT_SUPER_SUFFIX, PrimaryExpressionWithoutSuperSuffix, false);
			addChoicePoint(PRIMARY_EXPRESSION_WITHOUT_SUPER_SUFFIX_2, PrimaryExpressionWithoutSuperSuffix_2);
			addProduction(PRIMARY_PREFIX, PrimaryPrefix, false);
			addChoicePoint(PRIMARY_PREFIX_1, PrimaryPrefix_1);
			addChoicePoint(PRIMARY_PREFIX_1_3_2, PrimaryPrefix_1_3_2);
			addChoicePoint(PRIMARY_PREFIX_1_3_2_1_2, PrimaryPrefix_1_3_2_1_2);
			addProduction(PRIMARY_SUFFIX, PrimarySuffix, false);
			addChoicePoint(PRIMARY_SUFFIX_1, PrimarySuffix_1);
			addProduction(PRIMARY_SUFFIX_WITHOUT_SUPER, PrimarySuffixWithoutSuper, false);
			addChoicePoint(PRIMARY_SUFFIX_WITHOUT_SUPER_1, PrimarySuffixWithoutSuper_1);
			addChoicePoint(PRIMARY_SUFFIX_WITHOUT_SUPER_1_1_2, PrimarySuffixWithoutSuper_1_1_2);
			addProduction(FIELD_ACCESS, FieldAccess, false);
			addProduction(METHOD_INVOCATION, MethodInvocation, false);
			addChoicePoint(METHOD_INVOCATION_1, MethodInvocation_1);
			addProduction(ARGUMENTS, Arguments, false);
			addChoicePoint(ARGUMENTS_2, Arguments_2);
			addChoicePoint(ARGUMENTS_2_1, Arguments_2_1);
			addChoicePoint(ARGUMENTS_2_1_2_2, Arguments_2_1_2_2);
			addProduction(METHOD_REFERENCE_SUFFIX, MethodReferenceSuffix, false);
			addChoicePoint(METHOD_REFERENCE_SUFFIX_2, MethodReferenceSuffix_2);
			addChoicePoint(METHOD_REFERENCE_SUFFIX_3, MethodReferenceSuffix_3);
			addProduction(CLASS_CREATION_EXPR, ClassCreationExpr, false);
			addChoicePoint(CLASS_CREATION_EXPR_2, ClassCreationExpr_2);
			addChoicePoint(CLASS_CREATION_EXPR_6, ClassCreationExpr_6);
			addProduction(ARRAY_CREATION_EXPR, ArrayCreationExpr, false);
			addChoicePoint(ARRAY_CREATION_EXPR_2, ArrayCreationExpr_2);
			addChoicePoint(ARRAY_CREATION_EXPR_4, ArrayCreationExpr_4);
			addProduction(ARRAY_CREATION_EXPR_REST, ArrayCreationExprRest, false);
			addChoicePoint(ARRAY_CREATION_EXPR_REST, ArrayCreationExprRest);
			addProduction(ARRAY_DIM_EXPRS_MANDATORY, ArrayDimExprsMandatory, false);
			addChoicePoint(ARRAY_DIM_EXPRS_MANDATORY_1, ArrayDimExprsMandatory_1);
			addProduction(ARRAY_DIMS_MANDATORY, ArrayDimsMandatory, false);
			addChoicePoint(ARRAY_DIMS_MANDATORY_1, ArrayDimsMandatory_1);
			addProduction(STATEMENT, Statement, false);
			addChoicePoint(STATEMENT_1, Statement_1);
			addProduction(ASSERT_STATEMENT, AssertStatement, false);
			addChoicePoint(ASSERT_STATEMENT_3, AssertStatement_3);
			addProduction(LABELED_STATEMENT, LabeledStatement, false);
			addProduction(BLOCK, Block, false);
			addProduction(BLOCK_STATEMENT, BlockStatement, false);
			addChoicePoint(BLOCK_STATEMENT_1, BlockStatement_1);
			addProduction(VARIABLE_DECL_EXPRESSION, VariableDeclExpression, false);
			addProduction(EMPTY_STATEMENT, EmptyStatement, false);
			addProduction(EXPRESSION_STATEMENT, ExpressionStatement, false);
			addProduction(STATEMENT_EXPRESSION, StatementExpression, false);
			addProduction(SWITCH_STATEMENT, SwitchStatement, false);
			addChoicePoint(SWITCH_STATEMENT_6, SwitchStatement_6);
			addProduction(SWITCH_ENTRY, SwitchEntry, false);
			addChoicePoint(SWITCH_ENTRY_1, SwitchEntry_1);
			addProduction(IF_STATEMENT, IfStatement, false);
			addChoicePoint(IF_STATEMENT_6, IfStatement_6);
			addProduction(WHILE_STATEMENT, WhileStatement, false);
			addProduction(DO_STATEMENT, DoStatement, false);
			addProduction(FOR_STATEMENT, ForStatement, false);
			addChoicePoint(FOR_STATEMENT_3, ForStatement_3);
			addChoicePoint(FOR_STATEMENT_3_2_1, ForStatement_3_2_1);
			addChoicePoint(FOR_STATEMENT_3_2_3, ForStatement_3_2_3);
			addChoicePoint(FOR_STATEMENT_3_2_5, ForStatement_3_2_5);
			addProduction(FOR_INIT, ForInit, false);
			addChoicePoint(FOR_INIT_1, ForInit_1);
			addProduction(STATEMENT_EXPRESSION_LIST, StatementExpressionList, false);
			addChoicePoint(STATEMENT_EXPRESSION_LIST_2, StatementExpressionList_2);
			addProduction(FOR_UPDATE, ForUpdate, false);
			addProduction(BREAK_STATEMENT, BreakStatement, false);
			addChoicePoint(BREAK_STATEMENT_2, BreakStatement_2);
			addProduction(CONTINUE_STATEMENT, ContinueStatement, false);
			addChoicePoint(CONTINUE_STATEMENT_2, ContinueStatement_2);
			addProduction(RETURN_STATEMENT, ReturnStatement, false);
			addChoicePoint(RETURN_STATEMENT_2, ReturnStatement_2);
			addProduction(THROW_STATEMENT, ThrowStatement, false);
			addProduction(SYNCHRONIZED_STATEMENT, SynchronizedStatement, false);
			addProduction(TRY_STATEMENT, TryStatement, false);
			addChoicePoint(TRY_STATEMENT_2, TryStatement_2);
			addChoicePoint(TRY_STATEMENT_2_1_3, TryStatement_2_1_3);
			addChoicePoint(TRY_STATEMENT_2_1_4, TryStatement_2_1_4);
			addChoicePoint(TRY_STATEMENT_2_2_2, TryStatement_2_2_2);
			addChoicePoint(TRY_STATEMENT_2_2_2_1_2, TryStatement_2_2_2_1_2);
			addProduction(CATCH_CLAUSES, CatchClauses, false);
			addChoicePoint(CATCH_CLAUSES_1, CatchClauses_1);
			addProduction(CATCH_CLAUSE, CatchClause, false);
			addProduction(CATCH_FORMAL_PARAMETER, CatchFormalParameter, false);
			addChoicePoint(CATCH_FORMAL_PARAMETER_3, CatchFormalParameter_3);
			addChoicePoint(CATCH_FORMAL_PARAMETER_3_1, CatchFormalParameter_3_1);
			addProduction(RESOURCE_SPECIFICATION, ResourceSpecification, false);
			addChoicePoint(RESOURCE_SPECIFICATION_3, ResourceSpecification_3);
			addChoicePoint(RESOURCE_SPECIFICATION_4, ResourceSpecification_4);
			addProduction(R_U_N_S_I_G_N_E_D_S_H_I_F_T, RUNSIGNEDSHIFT, false);
			addProduction(R_S_I_G_N_E_D_S_H_I_F_T, RSIGNEDSHIFT, false);
			addProduction(ANNOTATIONS, Annotations, false);
			addChoicePoint(ANNOTATIONS_1, Annotations_1);
			addProduction(ANNOTATION, Annotation, false);
			addChoicePoint(ANNOTATION_1, Annotation_1);
			addProduction(NORMAL_ANNOTATION, NormalAnnotation, false);
			addChoicePoint(NORMAL_ANNOTATION_4, NormalAnnotation_4);
			addProduction(MARKER_ANNOTATION, MarkerAnnotation, false);
			addProduction(SINGLE_ELEMENT_ANNOTATION, SingleElementAnnotation, false);
			addProduction(ELEMENT_VALUE_PAIR_LIST, ElementValuePairList, false);
			addChoicePoint(ELEMENT_VALUE_PAIR_LIST_2, ElementValuePairList_2);
			addProduction(ELEMENT_VALUE_PAIR, ElementValuePair, false);
			addProduction(ELEMENT_VALUE, ElementValue, false);
			addChoicePoint(ELEMENT_VALUE_1, ElementValue_1);
			addProduction(ELEMENT_VALUE_ARRAY_INITIALIZER, ElementValueArrayInitializer, false);
			addChoicePoint(ELEMENT_VALUE_ARRAY_INITIALIZER_2, ElementValueArrayInitializer_2);
			addChoicePoint(ELEMENT_VALUE_ARRAY_INITIALIZER_3, ElementValueArrayInitializer_3);
			addProduction(ELEMENT_VALUE_LIST, ElementValueList, false);
			addChoicePoint(ELEMENT_VALUE_LIST_2, ElementValueList_2);
		}
	}

	/* sequence(
		action({ entryPoint = JavaGrammar.COMPILATION_UNIT_ENTRY; })
		nonTerminal(ret, CompilationUnit)
		action({ return ret; })
	) */
	protected BUTree<SCompilationUnit> parseCompilationUnitEntry() throws ParseException {
		BUTree<SCompilationUnit> ret;
		entryPoint = JavaGrammar.COMPILATION_UNIT_ENTRY;
		pushCallStack(JavaGrammar.CompilationUnitEntry_1);
		ret = parseCompilationUnit();
		popCallStack();
		return ret;
	}

	/* sequence(
		action({ entryPoint = JavaGrammar.PACKAGE_DECL_ENTRY; })
		nonTerminal(ret, PackageDecl)
		nonTerminal(Epilog)
		action({ return dressWithPrologAndEpilog(ret); })
	) */
	protected BUTree<SPackageDecl> parsePackageDeclEntry() throws ParseException {
		BUTree<SPackageDecl> ret;
		entryPoint = JavaGrammar.PACKAGE_DECL_ENTRY;
		pushCallStack(JavaGrammar.PackageDeclEntry_1);
		ret = parsePackageDecl();
		popCallStack();
		pushCallStack(JavaGrammar.PackageDeclEntry_2);
		parseEpilog();
		popCallStack();
		return dressWithPrologAndEpilog(ret);
	}

	/* sequence(
		action({ entryPoint = JavaGrammar.IMPORT_DECL_ENTRY; })
		nonTerminal(ret, ImportDecl)
		nonTerminal(Epilog)
		action({ return dressWithPrologAndEpilog(ret); })
	) */
	protected BUTree<SImportDecl> parseImportDeclEntry() throws ParseException {
		BUTree<SImportDecl> ret;
		entryPoint = JavaGrammar.IMPORT_DECL_ENTRY;
		pushCallStack(JavaGrammar.ImportDeclEntry_1);
		ret = parseImportDecl();
		popCallStack();
		pushCallStack(JavaGrammar.ImportDeclEntry_2);
		parseEpilog();
		popCallStack();
		return dressWithPrologAndEpilog(ret);
	}

	/* sequence(
		action({ entryPoint = JavaGrammar.TYPE_DECL_ENTRY; })
		nonTerminal(ret, TypeDecl)
		nonTerminal(Epilog)
		action({ return dressWithPrologAndEpilog(ret); })
	) */
	protected BUTree<? extends STypeDecl> parseTypeDeclEntry() throws ParseException {
		BUTree<? extends STypeDecl> ret;
		entryPoint = JavaGrammar.TYPE_DECL_ENTRY;
		pushCallStack(JavaGrammar.TypeDeclEntry_1);
		ret = parseTypeDecl();
		popCallStack();
		pushCallStack(JavaGrammar.TypeDeclEntry_2);
		parseEpilog();
		popCallStack();
		return dressWithPrologAndEpilog(ret);
	}

	/* sequence(
		action({ entryPoint = JavaGrammar.MEMBER_DECL_ENTRY; })
		nonTerminal(ret, ClassOrInterfaceBodyDecl)
		nonTerminal(Epilog)
		action({ return dressWithPrologAndEpilog(ret); })
	) */
	protected BUTree<? extends SMemberDecl> parseMemberDeclEntry(TypeKind typeKind) throws ParseException {
		BUTree<? extends SMemberDecl> ret;
		entryPoint = JavaGrammar.MEMBER_DECL_ENTRY;
		pushCallStack(JavaGrammar.MemberDeclEntry_1);
		ret = parseClassOrInterfaceBodyDecl(typeKind);
		popCallStack();
		pushCallStack(JavaGrammar.MemberDeclEntry_2);
		parseEpilog();
		popCallStack();
		return dressWithPrologAndEpilog(ret);
	}

	/* sequence(
		action({ entryPoint = JavaGrammar.ANNOTATION_MEMBER_DECL_ENTRY; })
		nonTerminal(ret, AnnotationTypeBodyDecl)
		nonTerminal(Epilog)
		action({ return dressWithPrologAndEpilog(ret); })
	) */
	protected BUTree<? extends SMemberDecl> parseAnnotationMemberDeclEntry() throws ParseException {
		BUTree<? extends SMemberDecl> ret;
		entryPoint = JavaGrammar.ANNOTATION_MEMBER_DECL_ENTRY;
		pushCallStack(JavaGrammar.AnnotationMemberDeclEntry_1);
		ret = parseAnnotationTypeBodyDecl();
		popCallStack();
		pushCallStack(JavaGrammar.AnnotationMemberDeclEntry_2);
		parseEpilog();
		popCallStack();
		return dressWithPrologAndEpilog(ret);
	}

	/* sequence(
		action({ entryPoint = JavaGrammar.MODIFIERS_ENTRY; })
		nonTerminal(ret, Modifiers)
		nonTerminal(Epilog)
		action({ return ret; })
	) */
	protected BUTree<SNodeList> parseModifiersEntry() throws ParseException {
		BUTree<SNodeList> ret;
		entryPoint = JavaGrammar.MODIFIERS_ENTRY;
		pushCallStack(JavaGrammar.ModifiersEntry_1);
		ret = parseModifiers();
		popCallStack();
		pushCallStack(JavaGrammar.ModifiersEntry_2);
		parseEpilog();
		popCallStack();
		return ret;
	}

	/* sequence(
		action({ entryPoint = JavaGrammar.ANNOTATIONS_ENTRY; })
		nonTerminal(ret, Annotations)
		nonTerminal(Epilog)
		action({ return ret; })
	) */
	protected BUTree<SNodeList> parseAnnotationsEntry() throws ParseException {
		BUTree<SNodeList> ret;
		entryPoint = JavaGrammar.ANNOTATIONS_ENTRY;
		pushCallStack(JavaGrammar.AnnotationsEntry_1);
		ret = parseAnnotations();
		popCallStack();
		pushCallStack(JavaGrammar.AnnotationsEntry_2);
		parseEpilog();
		popCallStack();
		return ret;
	}

	/* sequence(
		action({ entryPoint = JavaGrammar.METHOD_DECL_ENTRY; })
		action({ run(); })
		nonTerminal(modifiers, Modifiers)
		nonTerminal(ret, MethodDecl)
		nonTerminal(Epilog)
		action({ return dressWithPrologAndEpilog(ret); })
	) */
	protected BUTree<SMethodDecl> parseMethodDeclEntry() throws ParseException {
		BUTree<SNodeList> modifiers;
		BUTree<SMethodDecl> ret;
		entryPoint = JavaGrammar.METHOD_DECL_ENTRY;
		run();
		pushCallStack(JavaGrammar.MethodDeclEntry_1);
		modifiers = parseModifiers();
		popCallStack();
		pushCallStack(JavaGrammar.MethodDeclEntry_2);
		ret = parseMethodDecl(modifiers);
		popCallStack();
		pushCallStack(JavaGrammar.MethodDeclEntry_3);
		parseEpilog();
		popCallStack();
		return dressWithPrologAndEpilog(ret);
	}

	/* sequence(
		action({ entryPoint = JavaGrammar.FIELD_DECL_ENTRY; })
		action({ run(); })
		nonTerminal(modifiers, Modifiers)
		nonTerminal(ret, FieldDecl)
		nonTerminal(Epilog)
		action({ return dressWithPrologAndEpilog(ret); })
	) */
	protected BUTree<SFieldDecl> parseFieldDeclEntry() throws ParseException {
		BUTree<SNodeList> modifiers;
		BUTree<SFieldDecl> ret;
		entryPoint = JavaGrammar.FIELD_DECL_ENTRY;
		run();
		pushCallStack(JavaGrammar.FieldDeclEntry_1);
		modifiers = parseModifiers();
		popCallStack();
		pushCallStack(JavaGrammar.FieldDeclEntry_2);
		ret = parseFieldDecl(modifiers);
		popCallStack();
		pushCallStack(JavaGrammar.FieldDeclEntry_3);
		parseEpilog();
		popCallStack();
		return dressWithPrologAndEpilog(ret);
	}

	/* sequence(
		action({ entryPoint = JavaGrammar.ANNOTATION_ELEMENT_DECL_ENTRY; })
		action({ run(); })
		nonTerminal(modifiers, Modifiers)
		nonTerminal(ret, AnnotationTypeMemberDecl)
		nonTerminal(Epilog)
		action({ return dressWithPrologAndEpilog(ret); })
	) */
	protected BUTree<SAnnotationMemberDecl> parseAnnotationElementDeclEntry() throws ParseException {
		BUTree<SNodeList> modifiers;
		BUTree<SAnnotationMemberDecl> ret;
		entryPoint = JavaGrammar.ANNOTATION_ELEMENT_DECL_ENTRY;
		run();
		pushCallStack(JavaGrammar.AnnotationElementDeclEntry_1);
		modifiers = parseModifiers();
		popCallStack();
		pushCallStack(JavaGrammar.AnnotationElementDeclEntry_2);
		ret = parseAnnotationTypeMemberDecl(modifiers);
		popCallStack();
		pushCallStack(JavaGrammar.AnnotationElementDeclEntry_3);
		parseEpilog();
		popCallStack();
		return dressWithPrologAndEpilog(ret);
	}

	/* sequence(
		action({ entryPoint = JavaGrammar.ENUM_CONSTANT_DECL_ENTRY; })
		nonTerminal(ret, EnumConstantDecl)
		nonTerminal(Epilog)
		action({ return dressWithPrologAndEpilog(ret); })
	) */
	protected BUTree<SEnumConstantDecl> parseEnumConstantDeclEntry() throws ParseException {
		BUTree<SEnumConstantDecl> ret;
		entryPoint = JavaGrammar.ENUM_CONSTANT_DECL_ENTRY;
		pushCallStack(JavaGrammar.EnumConstantDeclEntry_1);
		ret = parseEnumConstantDecl();
		popCallStack();
		pushCallStack(JavaGrammar.EnumConstantDeclEntry_2);
		parseEpilog();
		popCallStack();
		return dressWithPrologAndEpilog(ret);
	}

	/* sequence(
		action({ entryPoint = JavaGrammar.FORMAL_PARAMETER_ENTRY; })
		nonTerminal(ret, FormalParameter)
		nonTerminal(Epilog)
		action({ return dressWithPrologAndEpilog(ret); })
	) */
	protected BUTree<SFormalParameter> parseFormalParameterEntry() throws ParseException {
		BUTree<SFormalParameter> ret;
		entryPoint = JavaGrammar.FORMAL_PARAMETER_ENTRY;
		pushCallStack(JavaGrammar.FormalParameterEntry_1);
		ret = parseFormalParameter();
		popCallStack();
		pushCallStack(JavaGrammar.FormalParameterEntry_2);
		parseEpilog();
		popCallStack();
		return dressWithPrologAndEpilog(ret);
	}

	/* sequence(
		action({ entryPoint = JavaGrammar.TYPE_PARAMETER_ENTRY; })
		nonTerminal(ret, TypeParameter)
		nonTerminal(Epilog)
		action({ return dressWithPrologAndEpilog(ret); })
	) */
	protected BUTree<STypeParameter> parseTypeParameterEntry() throws ParseException {
		BUTree<STypeParameter> ret;
		entryPoint = JavaGrammar.TYPE_PARAMETER_ENTRY;
		pushCallStack(JavaGrammar.TypeParameterEntry_1);
		ret = parseTypeParameter();
		popCallStack();
		pushCallStack(JavaGrammar.TypeParameterEntry_2);
		parseEpilog();
		popCallStack();
		return dressWithPrologAndEpilog(ret);
	}

	/* sequence(
		action({ entryPoint = JavaGrammar.STATEMENTS_ENTRY; })
		nonTerminal(ret, Statements)
		nonTerminal(Epilog)
		action({ return ret; })
	) */
	protected BUTree<SNodeList> parseStatementsEntry() throws ParseException {
		BUTree<SNodeList> ret;
		entryPoint = JavaGrammar.STATEMENTS_ENTRY;
		pushCallStack(JavaGrammar.StatementsEntry_1);
		ret = parseStatements(false);
		popCallStack();
		pushCallStack(JavaGrammar.StatementsEntry_2);
		parseEpilog();
		popCallStack();
		return ret;
	}

	/* sequence(
		action({ entryPoint = JavaGrammar.BLOCK_STATEMENT_ENTRY; })
		nonTerminal(ret, BlockStatement)
		nonTerminal(Epilog)
		action({ return dressWithPrologAndEpilog(ret); })
	) */
	protected BUTree<? extends SStmt> parseBlockStatementEntry() throws ParseException {
		BUTree<? extends SStmt> ret;
		entryPoint = JavaGrammar.BLOCK_STATEMENT_ENTRY;
		pushCallStack(JavaGrammar.BlockStatementEntry_1);
		ret = parseBlockStatement();
		popCallStack();
		pushCallStack(JavaGrammar.BlockStatementEntry_2);
		parseEpilog();
		popCallStack();
		return dressWithPrologAndEpilog(ret);
	}

	/* sequence(
		action({ entryPoint = JavaGrammar.EXPRESSION_ENTRY; })
		nonTerminal(ret, Expression)
		nonTerminal(Epilog)
		action({ return dressWithPrologAndEpilog(ret); })
	) */
	protected BUTree<? extends SExpr> parseExpressionEntry() throws ParseException {
		BUTree<? extends SExpr> ret;
		entryPoint = JavaGrammar.EXPRESSION_ENTRY;
		pushCallStack(JavaGrammar.ExpressionEntry_1);
		ret = parseExpression();
		popCallStack();
		pushCallStack(JavaGrammar.ExpressionEntry_2);
		parseEpilog();
		popCallStack();
		return dressWithPrologAndEpilog(ret);
	}

	/* sequence(
		action({ entryPoint = JavaGrammar.TYPE_ENTRY; })
		action({ run(); })
		nonTerminal(annotations, Annotations)
		nonTerminal(ret, Type)
		nonTerminal(Epilog)
		action({ return dressWithPrologAndEpilog(ret); })
	) */
	protected BUTree<? extends SType> parseTypeEntry() throws ParseException {
		BUTree<SNodeList> annotations;
		BUTree<? extends SType> ret;
		entryPoint = JavaGrammar.TYPE_ENTRY;
		run();
		pushCallStack(JavaGrammar.TypeEntry_1);
		annotations = parseAnnotations();
		popCallStack();
		pushCallStack(JavaGrammar.TypeEntry_2);
		ret = parseType(annotations);
		popCallStack();
		pushCallStack(JavaGrammar.TypeEntry_3);
		parseEpilog();
		popCallStack();
		return dressWithPrologAndEpilog(ret);
	}

	/* sequence(
		action({ entryPoint = JavaGrammar.QUALIFIED_NAME_ENTRY; })
		nonTerminal(ret, QualifiedName)
		nonTerminal(Epilog)
		action({ return dressWithPrologAndEpilog(ret); })
	) */
	protected BUTree<SQualifiedName> parseQualifiedNameEntry() throws ParseException {
		BUTree<SQualifiedName> ret;
		entryPoint = JavaGrammar.QUALIFIED_NAME_ENTRY;
		pushCallStack(JavaGrammar.QualifiedNameEntry_1);
		ret = parseQualifiedName();
		popCallStack();
		pushCallStack(JavaGrammar.QualifiedNameEntry_2);
		parseEpilog();
		popCallStack();
		return dressWithPrologAndEpilog(ret);
	}

	/* sequence(
		action({ entryPoint = JavaGrammar.NAME_ENTRY; })
		nonTerminal(ret, Name)
		nonTerminal(Epilog)
		action({ return dressWithPrologAndEpilog(ret); })
	) */
	protected BUTree<SName> parseNameEntry() throws ParseException {
		BUTree<SName> ret;
		entryPoint = JavaGrammar.NAME_ENTRY;
		pushCallStack(JavaGrammar.NameEntry_1);
		ret = parseName();
		popCallStack();
		pushCallStack(JavaGrammar.NameEntry_2);
		parseEpilog();
		popCallStack();
		return dressWithPrologAndEpilog(ret);
	}

	/* sequence(
		terminal(EOF)
	) */
	protected void parseEpilog() throws ParseException {
		parse(TokenType.EOF);
	}

	/* sequence(
		terminal(id, NODE_LIST_VARIABLE)
		action({ return makeVar(id); })
	) */
	protected BUTree<SNodeList> parseNodeListVar() throws ParseException {
		Token id;
		id = parse(TokenType.NODE_LIST_VARIABLE);
		return makeVar(id);
	}

	/* sequence(
		terminal(id, NODE_VARIABLE)
		action({ return makeVar(id); })
	) */
	protected BUTree<SName> parseNodeVar() throws ParseException {
		Token id;
		id = parse(TokenType.NODE_VARIABLE);
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
		run();
		if (predict(JavaGrammar.COMPILATION_UNIT_1) == 1) {
			pushCallStack(JavaGrammar.CompilationUnit_1_1);
			packageDecl = parsePackageDecl();
			popCallStack();
		}
		pushCallStack(JavaGrammar.CompilationUnit_2);
		imports = parseImportDecls();
		popCallStack();
		pushCallStack(JavaGrammar.CompilationUnit_3);
		types = parseTypeDecls();
		popCallStack();
		compilationUnit = dress(SCompilationUnit.make(packageDecl, imports, types));
		pushCallStack(JavaGrammar.CompilationUnit_4);
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
		run();
		pushCallStack(JavaGrammar.PackageDecl_1);
		annotations = parseAnnotations();
		popCallStack();
		parse(TokenType.PACKAGE);
		pushCallStack(JavaGrammar.PackageDecl_3);
		name = parseQualifiedName();
		popCallStack();
		parse(TokenType.SEMICOLON);
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
		while (match(0, TokenType.IMPORT) != -1) {
			pushCallStack(JavaGrammar.ImportDecls_1_1);
			importDecl = parseImportDecl();
			popCallStack();
			imports = append(imports, importDecl);
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
			terminal(DOT)
			terminal(STAR)
			action({ isAsterisk = true; })
		)
		terminal(SEMICOLON)
		action({ return dress(SImportDecl.make(name, isStatic, isAsterisk)); })
	) */
	protected BUTree<SImportDecl> parseImportDecl() throws ParseException {
		BUTree<SQualifiedName> name;
		boolean isStatic = false;
		boolean isAsterisk = false;
		run();
		parse(TokenType.IMPORT);
		if (match(0, TokenType.STATIC) != -1) {
			parse(TokenType.STATIC);
			isStatic = true;
		}
		pushCallStack(JavaGrammar.ImportDecl_3);
		name = parseQualifiedName();
		popCallStack();
		if (match(0, TokenType.DOT) != -1) {
			parse(TokenType.DOT);
			parse(TokenType.STAR);
			isAsterisk = true;
		}
		parse(TokenType.SEMICOLON);
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
		while (match(0, TokenType.ABSTRACT, TokenType.AT, TokenType.CLASS, TokenType.DEFAULT, TokenType.ENUM, TokenType.FINAL, TokenType.INTERFACE, TokenType.NATIVE, TokenType.PRIVATE, TokenType.PROTECTED, TokenType.PUBLIC, TokenType.SEMICOLON, TokenType.STATIC, TokenType.STRICTFP, TokenType.SYNCHRONIZED, TokenType.TRANSIENT, TokenType.VOLATILE) != -1) {
			pushCallStack(JavaGrammar.TypeDecls_1_1);
			typeDecl = parseTypeDecl();
			popCallStack();
			types = append(types, typeDecl);
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
		while (predict(JavaGrammar.MODIFIERS_1) == 1) {
			switch (getToken(0).kind) {
				case TokenType.PUBLIC:
					parse(TokenType.PUBLIC);
					modifiers = append(modifiers, SModifier.make(ModifierKeyword.Public));
					break;
				case TokenType.PROTECTED:
					parse(TokenType.PROTECTED);
					modifiers = append(modifiers, SModifier.make(ModifierKeyword.Protected));
					break;
				case TokenType.PRIVATE:
					parse(TokenType.PRIVATE);
					modifiers = append(modifiers, SModifier.make(ModifierKeyword.Private));
					break;
				case TokenType.ABSTRACT:
					parse(TokenType.ABSTRACT);
					modifiers = append(modifiers, SModifier.make(ModifierKeyword.Abstract));
					break;
				case TokenType.DEFAULT:
					parse(TokenType.DEFAULT);
					modifiers = append(modifiers, SModifier.make(ModifierKeyword.Default));
					break;
				case TokenType.STATIC:
					parse(TokenType.STATIC);
					modifiers = append(modifiers, SModifier.make(ModifierKeyword.Static));
					break;
				case TokenType.FINAL:
					parse(TokenType.FINAL);
					modifiers = append(modifiers, SModifier.make(ModifierKeyword.Final));
					break;
				case TokenType.TRANSIENT:
					parse(TokenType.TRANSIENT);
					modifiers = append(modifiers, SModifier.make(ModifierKeyword.Transient));
					break;
				case TokenType.VOLATILE:
					parse(TokenType.VOLATILE);
					modifiers = append(modifiers, SModifier.make(ModifierKeyword.Volatile));
					break;
				case TokenType.SYNCHRONIZED:
					parse(TokenType.SYNCHRONIZED);
					modifiers = append(modifiers, SModifier.make(ModifierKeyword.Synchronized));
					break;
				case TokenType.NATIVE:
					parse(TokenType.NATIVE);
					modifiers = append(modifiers, SModifier.make(ModifierKeyword.Native));
					break;
				case TokenType.STRICTFP:
					parse(TokenType.STRICTFP);
					modifiers = append(modifiers, SModifier.make(ModifierKeyword.StrictFP));
					break;
				case TokenType.AT:
					pushCallStack(JavaGrammar.Modifiers_1_1_13_1);
					ann = parseAnnotation();
					popCallStack();
					modifiers = append(modifiers, ann);
					break;
				default:
					throw produceParseException(TokenType.ABSTRACT, TokenType.AT, TokenType.DEFAULT, TokenType.FINAL, TokenType.NATIVE, TokenType.PRIVATE, TokenType.PROTECTED, TokenType.PUBLIC, TokenType.STATIC, TokenType.STRICTFP, TokenType.SYNCHRONIZED, TokenType.TRANSIENT, TokenType.VOLATILE);
			}
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
		while (match(0, TokenType.ABSTRACT, TokenType.AT, TokenType.FINAL, TokenType.NATIVE, TokenType.PRIVATE, TokenType.PROTECTED, TokenType.PUBLIC, TokenType.STATIC, TokenType.STRICTFP, TokenType.SYNCHRONIZED, TokenType.TRANSIENT, TokenType.VOLATILE) != -1) {
			switch (getToken(0).kind) {
				case TokenType.PUBLIC:
					parse(TokenType.PUBLIC);
					modifiers = append(modifiers, SModifier.make(ModifierKeyword.Public));
					break;
				case TokenType.PROTECTED:
					parse(TokenType.PROTECTED);
					modifiers = append(modifiers, SModifier.make(ModifierKeyword.Protected));
					break;
				case TokenType.PRIVATE:
					parse(TokenType.PRIVATE);
					modifiers = append(modifiers, SModifier.make(ModifierKeyword.Private));
					break;
				case TokenType.ABSTRACT:
					parse(TokenType.ABSTRACT);
					modifiers = append(modifiers, SModifier.make(ModifierKeyword.Abstract));
					break;
				case TokenType.STATIC:
					parse(TokenType.STATIC);
					modifiers = append(modifiers, SModifier.make(ModifierKeyword.Static));
					break;
				case TokenType.FINAL:
					parse(TokenType.FINAL);
					modifiers = append(modifiers, SModifier.make(ModifierKeyword.Final));
					break;
				case TokenType.TRANSIENT:
					parse(TokenType.TRANSIENT);
					modifiers = append(modifiers, SModifier.make(ModifierKeyword.Transient));
					break;
				case TokenType.VOLATILE:
					parse(TokenType.VOLATILE);
					modifiers = append(modifiers, SModifier.make(ModifierKeyword.Volatile));
					break;
				case TokenType.SYNCHRONIZED:
					parse(TokenType.SYNCHRONIZED);
					modifiers = append(modifiers, SModifier.make(ModifierKeyword.Synchronized));
					break;
				case TokenType.NATIVE:
					parse(TokenType.NATIVE);
					modifiers = append(modifiers, SModifier.make(ModifierKeyword.Native));
					break;
				case TokenType.STRICTFP:
					parse(TokenType.STRICTFP);
					modifiers = append(modifiers, SModifier.make(ModifierKeyword.StrictFP));
					break;
				case TokenType.AT:
					pushCallStack(JavaGrammar.ModifiersNoDefault_1_1_12_1);
					ann = parseAnnotation();
					popCallStack();
					modifiers = append(modifiers, ann);
					break;
				default:
					throw produceParseException(TokenType.ABSTRACT, TokenType.AT, TokenType.FINAL, TokenType.NATIVE, TokenType.PRIVATE, TokenType.PROTECTED, TokenType.PUBLIC, TokenType.STATIC, TokenType.STRICTFP, TokenType.SYNCHRONIZED, TokenType.TRANSIENT, TokenType.VOLATILE);
			}
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
		run();
		switch (getToken(0).kind) {
			case TokenType.SEMICOLON:
				parse(TokenType.SEMICOLON);
				ret = dress(SEmptyTypeDecl.make());
				break;
			case TokenType.ABSTRACT:
			case TokenType.AT:
			case TokenType.CLASS:
			case TokenType.DEFAULT:
			case TokenType.ENUM:
			case TokenType.FINAL:
			case TokenType.INTERFACE:
			case TokenType.NATIVE:
			case TokenType.PRIVATE:
			case TokenType.PROTECTED:
			case TokenType.PUBLIC:
			case TokenType.STATIC:
			case TokenType.STRICTFP:
			case TokenType.SYNCHRONIZED:
			case TokenType.TRANSIENT:
			case TokenType.VOLATILE:
				pushCallStack(JavaGrammar.TypeDecl_1_2_1);
				modifiers = parseModifiers();
				popCallStack();
				switch (getToken(0).kind) {
					case TokenType.CLASS:
					case TokenType.INTERFACE:
						pushCallStack(JavaGrammar.TypeDecl_1_2_2_1);
						ret = parseClassOrInterfaceDecl(modifiers);
						popCallStack();
						break;
					case TokenType.ENUM:
						pushCallStack(JavaGrammar.TypeDecl_1_2_2_2);
						ret = parseEnumDecl(modifiers);
						popCallStack();
						break;
					case TokenType.AT:
						pushCallStack(JavaGrammar.TypeDecl_1_2_2_3);
						ret = parseAnnotationTypeDecl(modifiers);
						popCallStack();
						break;
					default:
						throw produceParseException(TokenType.AT, TokenType.CLASS, TokenType.ENUM, TokenType.INTERFACE);
				}
				break;
			default:
				throw produceParseException(TokenType.ABSTRACT, TokenType.AT, TokenType.CLASS, TokenType.DEFAULT, TokenType.ENUM, TokenType.FINAL, TokenType.INTERFACE, TokenType.NATIVE, TokenType.PRIVATE, TokenType.PROTECTED, TokenType.PUBLIC, TokenType.SEMICOLON, TokenType.STATIC, TokenType.STRICTFP, TokenType.SYNCHRONIZED, TokenType.TRANSIENT, TokenType.VOLATILE);
		}
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
					terminal(EXTENDS)
					nonTerminal(superClassType, AnnotatedQualifiedType)
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
		switch (getToken(0).kind) {
			case TokenType.CLASS:
				parse(TokenType.CLASS);
				typeKind = TypeKind.Class;
				pushCallStack(JavaGrammar.ClassOrInterfaceDecl_1_1_2);
				name = parseName();
				popCallStack();
				if (match(0, TokenType.LT) != -1) {
					pushCallStack(JavaGrammar.ClassOrInterfaceDecl_1_1_3_1);
					typeParams = parseTypeParameters();
					popCallStack();
				}
				if (match(0, TokenType.EXTENDS) != -1) {
					parse(TokenType.EXTENDS);
					pushCallStack(JavaGrammar.ClassOrInterfaceDecl_1_1_4_2);
					superClassType = parseAnnotatedQualifiedType();
					popCallStack();
				}
				if (predict(JavaGrammar.CLASS_OR_INTERFACE_DECL_1_1_5) == 1) {
					pushCallStack(JavaGrammar.ClassOrInterfaceDecl_1_1_5_1);
					implementsClause = parseImplementsList(typeKind, problem);
					popCallStack();
				}
				break;
			case TokenType.INTERFACE:
				parse(TokenType.INTERFACE);
				typeKind = TypeKind.Interface;
				pushCallStack(JavaGrammar.ClassOrInterfaceDecl_1_2_2);
				name = parseName();
				popCallStack();
				if (match(0, TokenType.LT) != -1) {
					pushCallStack(JavaGrammar.ClassOrInterfaceDecl_1_2_3_1);
					typeParams = parseTypeParameters();
					popCallStack();
				}
				if (predict(JavaGrammar.CLASS_OR_INTERFACE_DECL_1_2_4) == 1) {
					pushCallStack(JavaGrammar.ClassOrInterfaceDecl_1_2_4_1);
					extendsClause = parseExtendsList();
					popCallStack();
				}
				break;
			default:
				throw produceParseException(TokenType.CLASS, TokenType.INTERFACE);
		}
		pushCallStack(JavaGrammar.ClassOrInterfaceDecl_2);
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
			sequence(
				lookAhead({ quotesMode })
				nonTerminal(ret, NodeListVar)
			)
			sequence(
				nonTerminal(cit, AnnotatedQualifiedType)
				action({ ret = append(ret, cit); })
				zeroOrMore(
					terminal(COMMA)
					nonTerminal(cit, AnnotatedQualifiedType)
					action({ ret = append(ret, cit); })
				)
			)
		)
		action({ return ret; })
	) */
	protected BUTree<SNodeList> parseExtendsList() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<SQualifiedType> cit;
		BUTree<SNodeList> annotations = null;
		parse(TokenType.EXTENDS);
		switch (getToken(0).kind) {
			case TokenType.NODE_LIST_VARIABLE:
				pushCallStack(JavaGrammar.ExtendsList_2_1_1);
				ret = parseNodeListVar();
				popCallStack();
				break;
			case TokenType.AT:
			case TokenType.IDENTIFIER:
			case TokenType.NODE_VARIABLE:
				pushCallStack(JavaGrammar.ExtendsList_2_2_1);
				cit = parseAnnotatedQualifiedType();
				popCallStack();
				ret = append(ret, cit);
				while (predict(JavaGrammar.EXTENDS_LIST_2_2_2) == 1) {
					parse(TokenType.COMMA);
					pushCallStack(JavaGrammar.ExtendsList_2_2_2_2);
					cit = parseAnnotatedQualifiedType();
					popCallStack();
					ret = append(ret, cit);
				}
				break;
			default:
				throw produceParseException(TokenType.AT, TokenType.IDENTIFIER, TokenType.NODE_LIST_VARIABLE, TokenType.NODE_VARIABLE);
		}
		return ret;
	}

	/* sequence(
		terminal(IMPLEMENTS)
		choice(
			sequence(
				lookAhead({ quotesMode })
				nonTerminal(ret, NodeListVar)
			)
			sequence(
				nonTerminal(cit, AnnotatedQualifiedType)
				action({ ret = append(ret, cit); })
				zeroOrMore(
					terminal(COMMA)
					nonTerminal(cit, AnnotatedQualifiedType)
					action({ ret = append(ret, cit); })
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
		parse(TokenType.IMPLEMENTS);
		switch (getToken(0).kind) {
			case TokenType.NODE_LIST_VARIABLE:
				pushCallStack(JavaGrammar.ImplementsList_2_1_1);
				ret = parseNodeListVar();
				popCallStack();
				break;
			case TokenType.AT:
			case TokenType.IDENTIFIER:
			case TokenType.NODE_VARIABLE:
				pushCallStack(JavaGrammar.ImplementsList_2_2_1);
				cit = parseAnnotatedQualifiedType();
				popCallStack();
				ret = append(ret, cit);
				while (match(0, TokenType.COMMA) != -1) {
					parse(TokenType.COMMA);
					pushCallStack(JavaGrammar.ImplementsList_2_2_2_2);
					cit = parseAnnotatedQualifiedType();
					popCallStack();
					ret = append(ret, cit);
				}
				if (typeKind == TypeKind.Interface) problem.value = new BUProblem(Severity.ERROR, "An interface cannot implement other interfaces");

				break;
			default:
				throw produceParseException(TokenType.AT, TokenType.IDENTIFIER, TokenType.NODE_LIST_VARIABLE, TokenType.NODE_VARIABLE);
		}
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
				sequence(
					lookAhead({ quotesMode })
					nonTerminal(constants, NodeListVar)
				)
				sequence(
					nonTerminal(entry, EnumConstantDecl)
					action({ constants = append(constants, entry); })
					zeroOrMore(
						terminal(COMMA)
						nonTerminal(entry, EnumConstantDecl)
						action({ constants = append(constants, entry); })
					)
				)
			)
		)
		zeroOrOne(
			terminal(COMMA)
			action({ trailingComma = true; })
		)
		zeroOrOne(
			terminal(SEMICOLON)
			nonTerminal(members, ClassOrInterfaceBodyDecls)
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
		parse(TokenType.ENUM);
		pushCallStack(JavaGrammar.EnumDecl_2);
		name = parseName();
		popCallStack();
		if (match(0, TokenType.IMPLEMENTS) != -1) {
			pushCallStack(JavaGrammar.EnumDecl_3_1);
			implementsClause = parseImplementsList(TypeKind.Enum, problem);
			popCallStack();
		}
		parse(TokenType.LBRACE);
		switch (getToken(0).kind) {
			case TokenType.NODE_LIST_VARIABLE:
				pushCallStack(JavaGrammar.EnumDecl_5_1_1_1);
				constants = parseNodeListVar();
				popCallStack();
				break;
			case TokenType.ABSTRACT:
			case TokenType.AT:
			case TokenType.DEFAULT:
			case TokenType.FINAL:
			case TokenType.IDENTIFIER:
			case TokenType.NATIVE:
			case TokenType.NODE_VARIABLE:
			case TokenType.PRIVATE:
			case TokenType.PROTECTED:
			case TokenType.PUBLIC:
			case TokenType.STATIC:
			case TokenType.STRICTFP:
			case TokenType.SYNCHRONIZED:
			case TokenType.TRANSIENT:
			case TokenType.VOLATILE:
				pushCallStack(JavaGrammar.EnumDecl_5_1_2_1);
				entry = parseEnumConstantDecl();
				popCallStack();
				constants = append(constants, entry);
				while (predict(JavaGrammar.ENUM_DECL_5_1_2_2) == 1) {
					parse(TokenType.COMMA);
					pushCallStack(JavaGrammar.EnumDecl_5_1_2_2_2);
					entry = parseEnumConstantDecl();
					popCallStack();
					constants = append(constants, entry);
				}
				break;
		}
		if (match(0, TokenType.COMMA) != -1) {
			parse(TokenType.COMMA);
			trailingComma = true;
		}
		if (match(0, TokenType.SEMICOLON) != -1) {
			parse(TokenType.SEMICOLON);
			pushCallStack(JavaGrammar.EnumDecl_7_2);
			members = parseClassOrInterfaceBodyDecls(TypeKind.Enum);
			popCallStack();
		}
		parse(TokenType.RBRACE);
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
		run();
		pushCallStack(JavaGrammar.EnumConstantDecl_1);
		modifiers = parseModifiers();
		popCallStack();
		pushCallStack(JavaGrammar.EnumConstantDecl_2);
		name = parseName();
		popCallStack();
		if (match(0, TokenType.LPAREN) != -1) {
			pushCallStack(JavaGrammar.EnumConstantDecl_3_1);
			args = parseArguments();
			popCallStack();
		}
		if (match(0, TokenType.LBRACE) != -1) {
			pushCallStack(JavaGrammar.EnumConstantDecl_4_1);
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
		parse(TokenType.AT);
		parse(TokenType.INTERFACE);
		pushCallStack(JavaGrammar.AnnotationTypeDecl_3);
		name = parseName();
		popCallStack();
		pushCallStack(JavaGrammar.AnnotationTypeDecl_4);
		members = parseAnnotationTypeBody();
		popCallStack();
		return dress(SAnnotationDecl.make(modifiers, name, members));
	}

	/* sequence(
		terminal(LBRACE)
		zeroOrOne(
			choice(
				sequence(
					lookAhead({ quotesMode })
					nonTerminal(ret, NodeListVar)
				)
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
		parse(TokenType.LBRACE);
		switch (getToken(0).kind) {
			case TokenType.NODE_LIST_VARIABLE:
				pushCallStack(JavaGrammar.AnnotationTypeBody_2_1_1_1);
				ret = parseNodeListVar();
				popCallStack();
				break;
			case TokenType.ABSTRACT:
			case TokenType.AT:
			case TokenType.BOOLEAN:
			case TokenType.BYTE:
			case TokenType.CHAR:
			case TokenType.CLASS:
			case TokenType.DEFAULT:
			case TokenType.DOUBLE:
			case TokenType.ENUM:
			case TokenType.FINAL:
			case TokenType.FLOAT:
			case TokenType.IDENTIFIER:
			case TokenType.INT:
			case TokenType.INTERFACE:
			case TokenType.LONG:
			case TokenType.NATIVE:
			case TokenType.NODE_VARIABLE:
			case TokenType.PRIVATE:
			case TokenType.PROTECTED:
			case TokenType.PUBLIC:
			case TokenType.SEMICOLON:
			case TokenType.SHORT:
			case TokenType.STATIC:
			case TokenType.STRICTFP:
			case TokenType.SYNCHRONIZED:
			case TokenType.TRANSIENT:
			case TokenType.VOLATILE:
				do {
					pushCallStack(JavaGrammar.AnnotationTypeBody_2_1_2_1);
					member = parseAnnotationTypeBodyDecl();
					popCallStack();
					ret = append(ret, member);
				} while (predict(JavaGrammar.ANNOTATION_TYPE_BODY_2_1_2) == 1);
				break;
		}
		parse(TokenType.RBRACE);
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
		run();
		switch (getToken(0).kind) {
			case TokenType.SEMICOLON:
				parse(TokenType.SEMICOLON);
				ret = dress(SEmptyTypeDecl.make());
				break;
			case TokenType.ABSTRACT:
			case TokenType.AT:
			case TokenType.BOOLEAN:
			case TokenType.BYTE:
			case TokenType.CHAR:
			case TokenType.CLASS:
			case TokenType.DEFAULT:
			case TokenType.DOUBLE:
			case TokenType.ENUM:
			case TokenType.FINAL:
			case TokenType.FLOAT:
			case TokenType.IDENTIFIER:
			case TokenType.INT:
			case TokenType.INTERFACE:
			case TokenType.LONG:
			case TokenType.NATIVE:
			case TokenType.NODE_VARIABLE:
			case TokenType.PRIVATE:
			case TokenType.PROTECTED:
			case TokenType.PUBLIC:
			case TokenType.SHORT:
			case TokenType.STATIC:
			case TokenType.STRICTFP:
			case TokenType.SYNCHRONIZED:
			case TokenType.TRANSIENT:
			case TokenType.VOLATILE:
				pushCallStack(JavaGrammar.AnnotationTypeBodyDecl_1_2_1);
				modifiers = parseModifiers();
				popCallStack();
				switch (predict(JavaGrammar.ANNOTATION_TYPE_BODY_DECL_1_2_2)) {
					case 1:
						pushCallStack(JavaGrammar.AnnotationTypeBodyDecl_1_2_2_1);
						ret = parseAnnotationTypeMemberDecl(modifiers);
						popCallStack();
						break;
					case 2:
						pushCallStack(JavaGrammar.AnnotationTypeBodyDecl_1_2_2_2);
						ret = parseClassOrInterfaceDecl(modifiers);
						popCallStack();
						break;
					case 3:
						pushCallStack(JavaGrammar.AnnotationTypeBodyDecl_1_2_2_3);
						ret = parseEnumDecl(modifiers);
						popCallStack();
						break;
					case 4:
						pushCallStack(JavaGrammar.AnnotationTypeBodyDecl_1_2_2_4);
						ret = parseAnnotationTypeDecl(modifiers);
						popCallStack();
						break;
					case 5:
						pushCallStack(JavaGrammar.AnnotationTypeBodyDecl_1_2_2_5);
						ret = parseFieldDecl(modifiers);
						popCallStack();
						break;
					default:
						throw produceParseException(TokenType.AT, TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.CLASS, TokenType.DOUBLE, TokenType.ENUM, TokenType.FLOAT, TokenType.IDENTIFIER, TokenType.INT, TokenType.INTERFACE, TokenType.LONG, TokenType.NODE_VARIABLE, TokenType.SHORT);
				}
				break;
			default:
				throw produceParseException(TokenType.ABSTRACT, TokenType.AT, TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.CLASS, TokenType.DEFAULT, TokenType.DOUBLE, TokenType.ENUM, TokenType.FINAL, TokenType.FLOAT, TokenType.IDENTIFIER, TokenType.INT, TokenType.INTERFACE, TokenType.LONG, TokenType.NATIVE, TokenType.NODE_VARIABLE, TokenType.PRIVATE, TokenType.PROTECTED, TokenType.PUBLIC, TokenType.SEMICOLON, TokenType.SHORT, TokenType.STATIC, TokenType.STRICTFP, TokenType.SYNCHRONIZED, TokenType.TRANSIENT, TokenType.VOLATILE);
		}
		return ret;
	}

	/* sequence(
		nonTerminal(type, Type)
		nonTerminal(name, Name)
		terminal(LPAREN)
		terminal(RPAREN)
		nonTerminal(dims, ArrayDims)
		zeroOrOne(
			terminal(DEFAULT)
			nonTerminal(value, ElementValue)
			action({ defaultValue = optionOf(value); })
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
		pushCallStack(JavaGrammar.AnnotationTypeMemberDecl_1);
		type = parseType(null);
		popCallStack();
		pushCallStack(JavaGrammar.AnnotationTypeMemberDecl_2);
		name = parseName();
		popCallStack();
		parse(TokenType.LPAREN);
		parse(TokenType.RPAREN);
		pushCallStack(JavaGrammar.AnnotationTypeMemberDecl_5);
		dims = parseArrayDims();
		popCallStack();
		if (match(0, TokenType.DEFAULT) != -1) {
			parse(TokenType.DEFAULT);
			pushCallStack(JavaGrammar.AnnotationTypeMemberDecl_6_2);
			value = parseElementValue();
			popCallStack();
			defaultValue = optionOf(value);
		}
		parse(TokenType.SEMICOLON);
		return dress(SAnnotationMemberDecl.make(modifiers, type, name, dims, defaultValue));
	}

	/* sequence(
		terminal(LT)
		choice(
			sequence(
				lookAhead({ quotesMode })
				nonTerminal(ret, NodeListVar)
			)
			sequence(
				nonTerminal(tp, TypeParameter)
				action({ ret = append(ret, tp); })
				zeroOrMore(
					terminal(COMMA)
					nonTerminal(tp, TypeParameter)
					action({ ret = append(ret, tp); })
				)
			)
		)
		terminal(GT)
		action({ return ret; })
	) */
	protected BUTree<SNodeList> parseTypeParameters() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<STypeParameter> tp;
		parse(TokenType.LT);
		switch (getToken(0).kind) {
			case TokenType.NODE_LIST_VARIABLE:
				pushCallStack(JavaGrammar.TypeParameters_2_1_1);
				ret = parseNodeListVar();
				popCallStack();
				break;
			case TokenType.AT:
			case TokenType.IDENTIFIER:
			case TokenType.NODE_VARIABLE:
				pushCallStack(JavaGrammar.TypeParameters_2_2_1);
				tp = parseTypeParameter();
				popCallStack();
				ret = append(ret, tp);
				while (predict(JavaGrammar.TYPE_PARAMETERS_2_2_2) == 1) {
					parse(TokenType.COMMA);
					pushCallStack(JavaGrammar.TypeParameters_2_2_2_2);
					tp = parseTypeParameter();
					popCallStack();
					ret = append(ret, tp);
				}
				break;
			default:
				throw produceParseException(TokenType.AT, TokenType.IDENTIFIER, TokenType.NODE_LIST_VARIABLE, TokenType.NODE_VARIABLE);
		}
		parse(TokenType.GT);
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
		run();
		pushCallStack(JavaGrammar.TypeParameter_1);
		annotations = parseAnnotations();
		popCallStack();
		pushCallStack(JavaGrammar.TypeParameter_2);
		name = parseName();
		popCallStack();
		if (predict(JavaGrammar.TYPE_PARAMETER_3) == 1) {
			pushCallStack(JavaGrammar.TypeParameter_3_1);
			typeBounds = parseTypeBounds();
			popCallStack();
		}
		return dress(STypeParameter.make(annotations, name, ensureNotNull(typeBounds)));
	}

	/* sequence(
		terminal(EXTENDS)
		choice(
			sequence(
				lookAhead({ quotesMode })
				nonTerminal(ret, NodeListVar)
			)
			sequence(
				nonTerminal(cit, AnnotatedQualifiedType)
				action({ ret = append(ret, cit); })
				zeroOrMore(
					terminal(BIT_AND)
					nonTerminal(cit, AnnotatedQualifiedType)
					action({ ret = append(ret, cit); })
				)
			)
		)
		action({ return ret; })
	) */
	protected BUTree<SNodeList> parseTypeBounds() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<SQualifiedType> cit;
		BUTree<SNodeList> annotations = null;
		parse(TokenType.EXTENDS);
		switch (getToken(0).kind) {
			case TokenType.NODE_LIST_VARIABLE:
				pushCallStack(JavaGrammar.TypeBounds_2_1_1);
				ret = parseNodeListVar();
				popCallStack();
				break;
			case TokenType.AT:
			case TokenType.IDENTIFIER:
			case TokenType.NODE_VARIABLE:
				pushCallStack(JavaGrammar.TypeBounds_2_2_1);
				cit = parseAnnotatedQualifiedType();
				popCallStack();
				ret = append(ret, cit);
				while (predict(JavaGrammar.TYPE_BOUNDS_2_2_2) == 1) {
					parse(TokenType.BIT_AND);
					pushCallStack(JavaGrammar.TypeBounds_2_2_2_2);
					cit = parseAnnotatedQualifiedType();
					popCallStack();
					ret = append(ret, cit);
				}
				break;
			default:
				throw produceParseException(TokenType.AT, TokenType.IDENTIFIER, TokenType.NODE_LIST_VARIABLE, TokenType.NODE_VARIABLE);
		}
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
		parse(TokenType.LBRACE);
		pushCallStack(JavaGrammar.ClassOrInterfaceBody_2);
		ret = parseClassOrInterfaceBodyDecls(typeKind);
		popCallStack();
		parse(TokenType.RBRACE);
		return ret;
	}

	/* sequence(
		zeroOrOne(
			choice(
				sequence(
					lookAhead({ quotesMode })
					nonTerminal(ret, NodeListVar)
				)
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
		switch (getToken(0).kind) {
			case TokenType.NODE_LIST_VARIABLE:
				pushCallStack(JavaGrammar.ClassOrInterfaceBodyDecls_1_1_1_1);
				ret = parseNodeListVar();
				popCallStack();
				break;
			case TokenType.ABSTRACT:
			case TokenType.AT:
			case TokenType.BOOLEAN:
			case TokenType.BYTE:
			case TokenType.CHAR:
			case TokenType.CLASS:
			case TokenType.DEFAULT:
			case TokenType.DOUBLE:
			case TokenType.ENUM:
			case TokenType.FINAL:
			case TokenType.FLOAT:
			case TokenType.IDENTIFIER:
			case TokenType.INT:
			case TokenType.INTERFACE:
			case TokenType.LBRACE:
			case TokenType.LONG:
			case TokenType.LT:
			case TokenType.NATIVE:
			case TokenType.NODE_VARIABLE:
			case TokenType.PRIVATE:
			case TokenType.PROTECTED:
			case TokenType.PUBLIC:
			case TokenType.SEMICOLON:
			case TokenType.SHORT:
			case TokenType.STATIC:
			case TokenType.STRICTFP:
			case TokenType.SYNCHRONIZED:
			case TokenType.TRANSIENT:
			case TokenType.VOID:
			case TokenType.VOLATILE:
				do {
					pushCallStack(JavaGrammar.ClassOrInterfaceBodyDecls_1_1_2_1);
					member = parseClassOrInterfaceBodyDecl(typeKind);
					popCallStack();
					ret = append(ret, member);
				} while (predict(JavaGrammar.CLASS_OR_INTERFACE_BODY_DECLS_1_1_2) == 1);
				break;
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
		run();
		switch (getToken(0).kind) {
			case TokenType.SEMICOLON:
				parse(TokenType.SEMICOLON);
				ret = dress(SEmptyMemberDecl.make());
				break;
			case TokenType.ABSTRACT:
			case TokenType.AT:
			case TokenType.BOOLEAN:
			case TokenType.BYTE:
			case TokenType.CHAR:
			case TokenType.CLASS:
			case TokenType.DEFAULT:
			case TokenType.DOUBLE:
			case TokenType.ENUM:
			case TokenType.FINAL:
			case TokenType.FLOAT:
			case TokenType.IDENTIFIER:
			case TokenType.INT:
			case TokenType.INTERFACE:
			case TokenType.LBRACE:
			case TokenType.LONG:
			case TokenType.LT:
			case TokenType.NATIVE:
			case TokenType.NODE_VARIABLE:
			case TokenType.PRIVATE:
			case TokenType.PROTECTED:
			case TokenType.PUBLIC:
			case TokenType.SHORT:
			case TokenType.STATIC:
			case TokenType.STRICTFP:
			case TokenType.SYNCHRONIZED:
			case TokenType.TRANSIENT:
			case TokenType.VOID:
			case TokenType.VOLATILE:
				pushCallStack(JavaGrammar.ClassOrInterfaceBodyDecl_1_2_1);
				modifiers = parseModifiers();
				popCallStack();
				if (modifiers != null && contains(modifiers, SModifier.make(ModifierKeyword.Default)) && typeKind != TypeKind.Interface) problem = new BUProblem(Severity.ERROR, "Only interfaces can have default members");

				switch (predict(JavaGrammar.CLASS_OR_INTERFACE_BODY_DECL_1_2_2)) {
					case 1:
						pushCallStack(JavaGrammar.ClassOrInterfaceBodyDecl_1_2_2_1_1);
						ret = parseInitializerDecl(modifiers);
						popCallStack();
						if (typeKind == TypeKind.Interface) ret = ret.withProblem(new BUProblem(Severity.ERROR, "An interface cannot have initializers"));

						break;
					case 2:
						pushCallStack(JavaGrammar.ClassOrInterfaceBodyDecl_1_2_2_2);
						ret = parseClassOrInterfaceDecl(modifiers);
						popCallStack();
						break;
					case 3:
						pushCallStack(JavaGrammar.ClassOrInterfaceBodyDecl_1_2_2_3);
						ret = parseEnumDecl(modifiers);
						popCallStack();
						break;
					case 4:
						pushCallStack(JavaGrammar.ClassOrInterfaceBodyDecl_1_2_2_4);
						ret = parseAnnotationTypeDecl(modifiers);
						popCallStack();
						break;
					case 5:
						pushCallStack(JavaGrammar.ClassOrInterfaceBodyDecl_1_2_2_5_1);
						ret = parseConstructorDecl(modifiers);
						popCallStack();
						if (typeKind == TypeKind.Interface) ret = ret.withProblem(new BUProblem(Severity.ERROR, "An interface cannot have constructors"));

						break;
					case 6:
						pushCallStack(JavaGrammar.ClassOrInterfaceBodyDecl_1_2_2_6_1);
						ret = parseFieldDecl(modifiers);
						popCallStack();
						break;
					case 7:
						pushCallStack(JavaGrammar.ClassOrInterfaceBodyDecl_1_2_2_7);
						ret = parseMethodDecl(modifiers);
						popCallStack();
						break;
					default:
						throw produceParseException(TokenType.AT, TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.CLASS, TokenType.DOUBLE, TokenType.ENUM, TokenType.FLOAT, TokenType.IDENTIFIER, TokenType.INT, TokenType.INTERFACE, TokenType.LBRACE, TokenType.LONG, TokenType.LT, TokenType.NODE_VARIABLE, TokenType.SHORT, TokenType.VOID);
				}
				break;
			default:
				throw produceParseException(TokenType.ABSTRACT, TokenType.AT, TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.CLASS, TokenType.DEFAULT, TokenType.DOUBLE, TokenType.ENUM, TokenType.FINAL, TokenType.FLOAT, TokenType.IDENTIFIER, TokenType.INT, TokenType.INTERFACE, TokenType.LBRACE, TokenType.LONG, TokenType.LT, TokenType.NATIVE, TokenType.NODE_VARIABLE, TokenType.PRIVATE, TokenType.PROTECTED, TokenType.PUBLIC, TokenType.SEMICOLON, TokenType.SHORT, TokenType.STATIC, TokenType.STRICTFP, TokenType.SYNCHRONIZED, TokenType.TRANSIENT, TokenType.VOID, TokenType.VOLATILE);
		}
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
		pushCallStack(JavaGrammar.FieldDecl_1);
		type = parseType(null);
		popCallStack();
		pushCallStack(JavaGrammar.FieldDecl_2);
		variables = parseVariableDeclarators();
		popCallStack();
		parse(TokenType.SEMICOLON);
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
		pushCallStack(JavaGrammar.VariableDecl_1);
		type = parseType(null);
		popCallStack();
		pushCallStack(JavaGrammar.VariableDecl_2);
		variables = parseVariableDeclarators();
		popCallStack();
		return dress(SLocalVariableDecl.make(modifiers, type, variables));
	}

	/* sequence(
		nonTerminal(val, VariableDeclarator)
		action({ variables = append(variables, val); })
		zeroOrMore(
			terminal(COMMA)
			nonTerminal(val, VariableDeclarator)
			action({ variables = append(variables, val); })
		)
		action({ return variables; })
	) */
	protected BUTree<SNodeList> parseVariableDeclarators() throws ParseException {
		BUTree<SNodeList> variables = emptyList();
		BUTree<SVariableDeclarator> val;
		pushCallStack(JavaGrammar.VariableDeclarators_1);
		val = parseVariableDeclarator();
		popCallStack();
		variables = append(variables, val);
		while (match(0, TokenType.COMMA) != -1) {
			parse(TokenType.COMMA);
			pushCallStack(JavaGrammar.VariableDeclarators_2_2);
			val = parseVariableDeclarator();
			popCallStack();
			variables = append(variables, val);
		}
		return variables;
	}

	/* sequence(
		action({ run(); })
		nonTerminal(id, VariableDeclaratorId)
		zeroOrOne(
			terminal(ASSIGN)
			nonTerminal(initExpr, VariableInitializer)
			action({ init = optionOf(initExpr); })
		)
		action({ return dress(SVariableDeclarator.make(id, init)); })
	) */
	protected BUTree<SVariableDeclarator> parseVariableDeclarator() throws ParseException {
		BUTree<SVariableDeclaratorId> id;
		BUTree<SNodeOption> init = none();
		BUTree<? extends SExpr> initExpr = null;
		run();
		pushCallStack(JavaGrammar.VariableDeclarator_1);
		id = parseVariableDeclaratorId();
		popCallStack();
		if (match(0, TokenType.ASSIGN) != -1) {
			parse(TokenType.ASSIGN);
			pushCallStack(JavaGrammar.VariableDeclarator_2_2);
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
		run();
		pushCallStack(JavaGrammar.VariableDeclaratorId_1);
		name = parseName();
		popCallStack();
		pushCallStack(JavaGrammar.VariableDeclaratorId_2);
		arrayDims = parseArrayDims();
		popCallStack();
		return dress(SVariableDeclaratorId.make(name, arrayDims));
	}

	/* sequence(
		zeroOrMore(
			action({ run(); })
			nonTerminal(annotations, Annotations)
			terminal(LBRACKET)
			terminal(RBRACKET)
			action({ arrayDims = append(arrayDims, dress(SArrayDim.make(annotations))); })
		)
		action({ return arrayDims; })
	) */
	protected BUTree<SNodeList> parseArrayDims() throws ParseException {
		BUTree<SNodeList> arrayDims = emptyList();
		BUTree<SNodeList> annotations;
		while (predict(JavaGrammar.ARRAY_DIMS_1) == 1) {
			run();
			pushCallStack(JavaGrammar.ArrayDims_1_1);
			annotations = parseAnnotations();
			popCallStack();
			parse(TokenType.LBRACKET);
			parse(TokenType.RBRACKET);
			arrayDims = append(arrayDims, dress(SArrayDim.make(annotations)));
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
		switch (getToken(0).kind) {
			case TokenType.LBRACE:
				pushCallStack(JavaGrammar.VariableInitializer_1_1);
				ret = parseArrayInitializer();
				popCallStack();
				break;
			case TokenType.BANG:
			case TokenType.BOOLEAN:
			case TokenType.BYTE:
			case TokenType.CHAR:
			case TokenType.CHARACTER_LITERAL:
			case TokenType.DECR:
			case TokenType.DOUBLE:
			case TokenType.DOUBLE_LITERAL:
			case TokenType.FALSE:
			case TokenType.FLOAT:
			case TokenType.FLOAT_LITERAL:
			case TokenType.IDENTIFIER:
			case TokenType.INCR:
			case TokenType.INT:
			case TokenType.INTEGER_LITERAL:
			case TokenType.LONG:
			case TokenType.LONG_LITERAL:
			case TokenType.LPAREN:
			case TokenType.LT:
			case TokenType.MINUS:
			case TokenType.NEW:
			case TokenType.NODE_VARIABLE:
			case TokenType.NULL:
			case TokenType.PLUS:
			case TokenType.SHORT:
			case TokenType.STRING_LITERAL:
			case TokenType.SUPER:
			case TokenType.THIS:
			case TokenType.TILDE:
			case TokenType.TRUE:
			case TokenType.VOID:
				pushCallStack(JavaGrammar.VariableInitializer_1_2);
				ret = parseExpression();
				popCallStack();
				break;
			default:
				throw produceParseException(TokenType.BANG, TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.CHARACTER_LITERAL, TokenType.DECR, TokenType.DOUBLE, TokenType.DOUBLE_LITERAL, TokenType.FALSE, TokenType.FLOAT, TokenType.FLOAT_LITERAL, TokenType.IDENTIFIER, TokenType.INCR, TokenType.INT, TokenType.INTEGER_LITERAL, TokenType.LBRACE, TokenType.LONG, TokenType.LONG_LITERAL, TokenType.LPAREN, TokenType.LT, TokenType.MINUS, TokenType.NEW, TokenType.NODE_VARIABLE, TokenType.NULL, TokenType.PLUS, TokenType.SHORT, TokenType.STRING_LITERAL, TokenType.SUPER, TokenType.THIS, TokenType.TILDE, TokenType.TRUE, TokenType.VOID);
		}
		return ret;
	}

	/* sequence(
		action({ run(); })
		terminal(LBRACE)
		zeroOrOne(
			nonTerminal(val, VariableInitializer)
			action({ values = append(values, val); })
			zeroOrMore(
				terminal(COMMA)
				nonTerminal(val, VariableInitializer)
				action({ values = append(values, val); })
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
		run();
		parse(TokenType.LBRACE);
		if (match(0, TokenType.BANG, TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.CHARACTER_LITERAL, TokenType.DECR, TokenType.DOUBLE, TokenType.DOUBLE_LITERAL, TokenType.FALSE, TokenType.FLOAT, TokenType.FLOAT_LITERAL, TokenType.IDENTIFIER, TokenType.INCR, TokenType.INT, TokenType.INTEGER_LITERAL, TokenType.LBRACE, TokenType.LONG, TokenType.LONG_LITERAL, TokenType.LPAREN, TokenType.LT, TokenType.MINUS, TokenType.NEW, TokenType.NODE_VARIABLE, TokenType.NULL, TokenType.PLUS, TokenType.SHORT, TokenType.STRING_LITERAL, TokenType.SUPER, TokenType.THIS, TokenType.TILDE, TokenType.TRUE, TokenType.VOID) != -1) {
			pushCallStack(JavaGrammar.ArrayInitializer_2_1);
			val = parseVariableInitializer();
			popCallStack();
			values = append(values, val);
			while (predict(JavaGrammar.ARRAY_INITIALIZER_2_2) == 1) {
				parse(TokenType.COMMA);
				pushCallStack(JavaGrammar.ArrayInitializer_2_2_2);
				val = parseVariableInitializer();
				popCallStack();
				values = append(values, val);
			}
		}
		if (match(0, TokenType.COMMA) != -1) {
			parse(TokenType.COMMA);
			trailingComma = true;
		}
		parse(TokenType.RBRACE);
		return dress(SArrayInitializerExpr.make(values, trailingComma));
	}

	/* sequence(
		zeroOrOne(
			nonTerminal(typeParameters, TypeParameters)
			nonTerminal(additionalAnnotations, Annotations)
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
		if (match(0, TokenType.LT) != -1) {
			pushCallStack(JavaGrammar.MethodDecl_1_1);
			typeParameters = parseTypeParameters();
			popCallStack();
			pushCallStack(JavaGrammar.MethodDecl_1_2);
			additionalAnnotations = parseAnnotations();
			popCallStack();
		}
		pushCallStack(JavaGrammar.MethodDecl_2);
		type = parseResultType();
		popCallStack();
		pushCallStack(JavaGrammar.MethodDecl_3);
		name = parseName();
		popCallStack();
		pushCallStack(JavaGrammar.MethodDecl_4);
		parameters = parseFormalParameters();
		popCallStack();
		pushCallStack(JavaGrammar.MethodDecl_5);
		arrayDims = parseArrayDims();
		popCallStack();
		if (match(0, TokenType.THROWS) != -1) {
			pushCallStack(JavaGrammar.MethodDecl_6_1);
			throwsClause = parseThrowsClause();
			popCallStack();
		}
		switch (getToken(0).kind) {
			case TokenType.LBRACE:
				pushCallStack(JavaGrammar.MethodDecl_7_1);
				block = parseBlock();
				popCallStack();
				break;
			case TokenType.SEMICOLON:
				parse(TokenType.SEMICOLON);
				if (modifiers != null && contains(modifiers, SModifier.make(ModifierKeyword.Default))) problem = new BUProblem(Severity.ERROR, "Default methods must have a body");

				break;
			default:
				throw produceParseException(TokenType.LBRACE, TokenType.SEMICOLON);
		}
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
		parse(TokenType.LPAREN);
		if (match(0, TokenType.ABSTRACT, TokenType.AT, TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.DEFAULT, TokenType.DOUBLE, TokenType.FINAL, TokenType.FLOAT, TokenType.IDENTIFIER, TokenType.INT, TokenType.LONG, TokenType.NATIVE, TokenType.NODE_LIST_VARIABLE, TokenType.NODE_VARIABLE, TokenType.PRIVATE, TokenType.PROTECTED, TokenType.PUBLIC, TokenType.SHORT, TokenType.STATIC, TokenType.STRICTFP, TokenType.SYNCHRONIZED, TokenType.TRANSIENT, TokenType.VOLATILE) != -1) {
			pushCallStack(JavaGrammar.FormalParameters_2_1);
			ret = parseFormalParameterList();
			popCallStack();
		}
		parse(TokenType.RPAREN);
		return ensureNotNull(ret);
	}

	/* sequence(
		choice(
			sequence(
				lookAhead({ quotesMode })
				nonTerminal(ret, NodeListVar)
			)
			sequence(
				nonTerminal(par, FormalParameter)
				action({ ret = append(ret, par); })
				zeroOrMore(
					terminal(COMMA)
					nonTerminal(par, FormalParameter)
					action({ ret = append(ret, par); })
				)
			)
		)
		action({ return ret; })
	) */
	protected BUTree<SNodeList> parseFormalParameterList() throws ParseException {
		BUTree<SNodeList> ret = null;
		BUTree<SFormalParameter> par;
		switch (getToken(0).kind) {
			case TokenType.NODE_LIST_VARIABLE:
				pushCallStack(JavaGrammar.FormalParameterList_1_1_1);
				ret = parseNodeListVar();
				popCallStack();
				break;
			case TokenType.ABSTRACT:
			case TokenType.AT:
			case TokenType.BOOLEAN:
			case TokenType.BYTE:
			case TokenType.CHAR:
			case TokenType.DEFAULT:
			case TokenType.DOUBLE:
			case TokenType.FINAL:
			case TokenType.FLOAT:
			case TokenType.IDENTIFIER:
			case TokenType.INT:
			case TokenType.LONG:
			case TokenType.NATIVE:
			case TokenType.NODE_VARIABLE:
			case TokenType.PRIVATE:
			case TokenType.PROTECTED:
			case TokenType.PUBLIC:
			case TokenType.SHORT:
			case TokenType.STATIC:
			case TokenType.STRICTFP:
			case TokenType.SYNCHRONIZED:
			case TokenType.TRANSIENT:
			case TokenType.VOLATILE:
				pushCallStack(JavaGrammar.FormalParameterList_1_2_1);
				par = parseFormalParameter();
				popCallStack();
				ret = append(ret, par);
				while (predict(JavaGrammar.FORMAL_PARAMETER_LIST_1_2_2) == 1) {
					parse(TokenType.COMMA);
					pushCallStack(JavaGrammar.FormalParameterList_1_2_2_2);
					par = parseFormalParameter();
					popCallStack();
					ret = append(ret, par);
				}
				break;
			default:
				throw produceParseException(TokenType.ABSTRACT, TokenType.AT, TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.DEFAULT, TokenType.DOUBLE, TokenType.FINAL, TokenType.FLOAT, TokenType.IDENTIFIER, TokenType.INT, TokenType.LONG, TokenType.NATIVE, TokenType.NODE_LIST_VARIABLE, TokenType.NODE_VARIABLE, TokenType.PRIVATE, TokenType.PROTECTED, TokenType.PUBLIC, TokenType.SHORT, TokenType.STATIC, TokenType.STRICTFP, TokenType.SYNCHRONIZED, TokenType.TRANSIENT, TokenType.VOLATILE);
		}
		return ret;
	}

	/* sequence(
		action({ run(); })
		nonTerminal(modifiers, Modifiers)
		nonTerminal(type, Type)
		zeroOrOne(
			nonTerminal(ellipsisAnnotations, Annotations)
			terminal(ELLIPSIS)
			action({ isVarArg = true; })
		)
		choice(
			sequence(
				zeroOrOne(
					nonTerminal(receiverTypeName, Name)
					terminal(DOT)
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
		run();
		pushCallStack(JavaGrammar.FormalParameter_1);
		modifiers = parseModifiers();
		popCallStack();
		pushCallStack(JavaGrammar.FormalParameter_2);
		type = parseType(null);
		popCallStack();
		if (match(0, TokenType.AT, TokenType.ELLIPSIS) != -1) {
			pushCallStack(JavaGrammar.FormalParameter_3_1);
			ellipsisAnnotations = parseAnnotations();
			popCallStack();
			parse(TokenType.ELLIPSIS);
			isVarArg = true;
		}
		switch (predict(JavaGrammar.FORMAL_PARAMETER_4)) {
			case 1:
				if (match(0, TokenType.IDENTIFIER, TokenType.NODE_VARIABLE) != -1) {
					pushCallStack(JavaGrammar.FormalParameter_4_1_1_1);
					receiverTypeName = parseName();
					popCallStack();
					parse(TokenType.DOT);
				}
				parse(TokenType.THIS);
				isReceiver = true;
				break;
			case 2:
				pushCallStack(JavaGrammar.FormalParameter_4_2);
				id = parseVariableDeclaratorId();
				popCallStack();
				break;
			default:
				throw produceParseException(TokenType.IDENTIFIER, TokenType.NODE_VARIABLE, TokenType.THIS);
		}
		return dress(SFormalParameter.make(modifiers, type, isVarArg, ensureNotNull(ellipsisAnnotations), optionOf(id), isReceiver, optionOf(receiverTypeName)));
	}

	/* sequence(
		terminal(THROWS)
		nonTerminal(cit, AnnotatedQualifiedType)
		action({ ret = append(ret, cit); })
		zeroOrMore(
			terminal(COMMA)
			nonTerminal(cit, AnnotatedQualifiedType)
			action({ ret = append(ret, cit); })
		)
		action({ return ret; })
	) */
	protected BUTree<SNodeList> parseThrowsClause() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<SQualifiedType> cit;
		parse(TokenType.THROWS);
		pushCallStack(JavaGrammar.ThrowsClause_2);
		cit = parseAnnotatedQualifiedType();
		popCallStack();
		ret = append(ret, cit);
		while (match(0, TokenType.COMMA) != -1) {
			parse(TokenType.COMMA);
			pushCallStack(JavaGrammar.ThrowsClause_3_2);
			cit = parseAnnotatedQualifiedType();
			popCallStack();
			ret = append(ret, cit);
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
		if (match(0, TokenType.LT) != -1) {
			pushCallStack(JavaGrammar.ConstructorDecl_1_1);
			typeParameters = parseTypeParameters();
			popCallStack();
		}
		pushCallStack(JavaGrammar.ConstructorDecl_2);
		name = parseName();
		popCallStack();
		pushCallStack(JavaGrammar.ConstructorDecl_3);
		parameters = parseFormalParameters();
		popCallStack();
		if (match(0, TokenType.THROWS) != -1) {
			pushCallStack(JavaGrammar.ConstructorDecl_4_1);
			throwsClause = parseThrowsClause();
			popCallStack();
		}
		run();
		parse(TokenType.LBRACE);
		pushCallStack(JavaGrammar.ConstructorDecl_6);
		stmts = parseStatements(true);
		popCallStack();
		parse(TokenType.RBRACE);
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
					nonTerminal(expr, PrimaryExpressionWithoutSuperSuffix)
					terminal(DOT)
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
		run();
		switch (predict(JavaGrammar.EXPLICIT_CONSTRUCTOR_INVOCATION_1)) {
			case 1:
				if (match(0, TokenType.LT) != -1) {
					pushCallStack(JavaGrammar.ExplicitConstructorInvocation_1_1_1_1);
					typeArgs = parseTypeArguments();
					popCallStack();
				}
				parse(TokenType.THIS);
				isThis = true;
				pushCallStack(JavaGrammar.ExplicitConstructorInvocation_1_1_3);
				args = parseArguments();
				popCallStack();
				parse(TokenType.SEMICOLON);
				break;
			case 2:
				if (predict(JavaGrammar.EXPLICIT_CONSTRUCTOR_INVOCATION_1_2_1) == 1) {
					pushCallStack(JavaGrammar.ExplicitConstructorInvocation_1_2_1_1);
					expr = parsePrimaryExpressionWithoutSuperSuffix();
					popCallStack();
					parse(TokenType.DOT);
				}
				if (match(0, TokenType.LT) != -1) {
					pushCallStack(JavaGrammar.ExplicitConstructorInvocation_1_2_2_1);
					typeArgs = parseTypeArguments();
					popCallStack();
				}
				parse(TokenType.SUPER);
				pushCallStack(JavaGrammar.ExplicitConstructorInvocation_1_2_4);
				args = parseArguments();
				popCallStack();
				parse(TokenType.SEMICOLON);
				break;
			default:
				throw produceParseException(TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.CHARACTER_LITERAL, TokenType.DOUBLE, TokenType.DOUBLE_LITERAL, TokenType.FALSE, TokenType.FLOAT, TokenType.FLOAT_LITERAL, TokenType.IDENTIFIER, TokenType.INT, TokenType.INTEGER_LITERAL, TokenType.LONG, TokenType.LONG_LITERAL, TokenType.LPAREN, TokenType.LT, TokenType.NEW, TokenType.NODE_VARIABLE, TokenType.NULL, TokenType.SHORT, TokenType.STRING_LITERAL, TokenType.SUPER, TokenType.THIS, TokenType.TRUE, TokenType.VOID);
		}
		return dress(SExplicitConstructorInvocationStmt.make(ensureNotNull(typeArgs), isThis, optionOf(expr), args));
	}

	/* sequence(
		zeroOrOne(
			choice(
				sequence(
					nonTerminal(ret, NodeListVar)
				)
				sequence(
					zeroOrOne(
						lookAhead({ inConstructor })
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
	protected BUTree<SNodeList> parseStatements(boolean inConstructor) throws ParseException {
		BUTree<SNodeList> ret = null;
		BUTree<? extends SStmt> stmt;
		switch (getToken(0).kind) {
			case TokenType.NODE_LIST_VARIABLE:
				pushCallStack(JavaGrammar.Statements_1_1_1_1);
				ret = parseNodeListVar();
				popCallStack();
				break;
			case TokenType.ABSTRACT:
			case TokenType.ASSERT:
			case TokenType.AT:
			case TokenType.BANG:
			case TokenType.BOOLEAN:
			case TokenType.BREAK:
			case TokenType.BYTE:
			case TokenType.CHAR:
			case TokenType.CHARACTER_LITERAL:
			case TokenType.CLASS:
			case TokenType.CONTINUE:
			case TokenType.DECR:
			case TokenType.DO:
			case TokenType.DOUBLE:
			case TokenType.DOUBLE_LITERAL:
			case TokenType.EOF:
			case TokenType.FALSE:
			case TokenType.FINAL:
			case TokenType.FLOAT:
			case TokenType.FLOAT_LITERAL:
			case TokenType.FOR:
			case TokenType.IDENTIFIER:
			case TokenType.IF:
			case TokenType.INCR:
			case TokenType.INT:
			case TokenType.INTEGER_LITERAL:
			case TokenType.INTERFACE:
			case TokenType.LBRACE:
			case TokenType.LONG:
			case TokenType.LONG_LITERAL:
			case TokenType.LPAREN:
			case TokenType.LT:
			case TokenType.MINUS:
			case TokenType.NATIVE:
			case TokenType.NEW:
			case TokenType.NODE_VARIABLE:
			case TokenType.NULL:
			case TokenType.PLUS:
			case TokenType.PRIVATE:
			case TokenType.PROTECTED:
			case TokenType.PUBLIC:
			case TokenType.RBRACE:
			case TokenType.RETURN:
			case TokenType.SEMICOLON:
			case TokenType.SHORT:
			case TokenType.STATIC:
			case TokenType.STRICTFP:
			case TokenType.STRING_LITERAL:
			case TokenType.SUPER:
			case TokenType.SWITCH:
			case TokenType.SYNCHRONIZED:
			case TokenType.THIS:
			case TokenType.THROW:
			case TokenType.TILDE:
			case TokenType.TRANSIENT:
			case TokenType.TRUE:
			case TokenType.TRY:
			case TokenType.VOID:
			case TokenType.VOLATILE:
			case TokenType.WHILE:
				if (predict(JavaGrammar.STATEMENTS_1_1_2_1) == 1) {
					pushCallStack(JavaGrammar.Statements_1_1_2_1_1);
					stmt = parseExplicitConstructorInvocation();
					popCallStack();
					ret = append(ret, stmt);
				}
				while (predict(JavaGrammar.STATEMENTS_1_1_2_2) == 1) {
					pushCallStack(JavaGrammar.Statements_1_1_2_2_1);
					stmt = parseBlockStatement();
					popCallStack();
					ret = append(ret, stmt);
				}
				break;
		}
		return ensureNotNull(ret);
	}

	/* sequence(
		nonTerminal(block, Block)
		action({ return dress(SInitializerDecl.make(modifiers, block)); })
	) */
	protected BUTree<SInitializerDecl> parseInitializerDecl(BUTree<SNodeList> modifiers) throws ParseException {
		BUTree<SBlockStmt> block;
		pushCallStack(JavaGrammar.InitializerDecl_1);
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
		switch (getToken(0).kind) {
			case TokenType.BOOLEAN:
			case TokenType.BYTE:
			case TokenType.CHAR:
			case TokenType.DOUBLE:
			case TokenType.FLOAT:
			case TokenType.INT:
			case TokenType.LONG:
			case TokenType.SHORT:
				pushCallStack(JavaGrammar.Type_1_1_1);
				primitiveType = parsePrimitiveType(annotations);
				popCallStack();
				if (predict(JavaGrammar.TYPE_1_1_2) == 1) {
					lateRun();
					pushCallStack(JavaGrammar.Type_1_1_2_1);
					arrayDims = parseArrayDimsMandatory();
					popCallStack();
					type = dress(SArrayType.make(primitiveType, arrayDims));
				}
				break;
			case TokenType.IDENTIFIER:
			case TokenType.NODE_VARIABLE:
				pushCallStack(JavaGrammar.Type_1_2_1);
				type = parseQualifiedType(annotations);
				popCallStack();
				if (predict(JavaGrammar.TYPE_1_2_2) == 1) {
					lateRun();
					pushCallStack(JavaGrammar.Type_1_2_2_1);
					arrayDims = parseArrayDimsMandatory();
					popCallStack();
					type = dress(SArrayType.make(type, arrayDims));
				}
				break;
			default:
				throw produceParseException(TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.DOUBLE, TokenType.FLOAT, TokenType.IDENTIFIER, TokenType.INT, TokenType.LONG, TokenType.NODE_VARIABLE, TokenType.SHORT);
		}
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
		switch (getToken(0).kind) {
			case TokenType.BOOLEAN:
			case TokenType.BYTE:
			case TokenType.CHAR:
			case TokenType.DOUBLE:
			case TokenType.FLOAT:
			case TokenType.INT:
			case TokenType.LONG:
			case TokenType.SHORT:
				pushCallStack(JavaGrammar.ReferenceType_1_1_1);
				primitiveType = parsePrimitiveType(annotations);
				popCallStack();
				lateRun();
				pushCallStack(JavaGrammar.ReferenceType_1_1_2);
				arrayDims = parseArrayDimsMandatory();
				popCallStack();
				type = dress(SArrayType.make(primitiveType, arrayDims));
				break;
			case TokenType.IDENTIFIER:
			case TokenType.NODE_VARIABLE:
				pushCallStack(JavaGrammar.ReferenceType_1_2_1);
				type = parseQualifiedType(annotations);
				popCallStack();
				if (predict(JavaGrammar.REFERENCE_TYPE_1_2_2) == 1) {
					lateRun();
					pushCallStack(JavaGrammar.ReferenceType_1_2_2_1);
					arrayDims = parseArrayDimsMandatory();
					popCallStack();
					type = dress(SArrayType.make(type, arrayDims));
				}
				break;
			default:
				throw produceParseException(TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.DOUBLE, TokenType.FLOAT, TokenType.IDENTIFIER, TokenType.INT, TokenType.LONG, TokenType.NODE_VARIABLE, TokenType.SHORT);
		}
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
		action({ return ret; })
	) */
	protected BUTree<SQualifiedType> parseQualifiedType(BUTree<SNodeList> annotations) throws ParseException {
		BUTree<SNodeOption> scope = none();
		BUTree<SQualifiedType> ret;
		BUTree<SName> name;
		BUTree<SNodeList> typeArgs = null;
		if (annotations == null) {
			run();
			annotations = emptyList();
		}
		pushCallStack(JavaGrammar.QualifiedType_1);
		name = parseName();
		popCallStack();
		if (predict(JavaGrammar.QUALIFIED_TYPE_2) == 1) {
			pushCallStack(JavaGrammar.QualifiedType_2_1);
			typeArgs = parseTypeArgumentsOrDiamond();
			popCallStack();
		}
		ret = dress(SQualifiedType.make(annotations, scope, name, optionOf(typeArgs)));
		while (predict(JavaGrammar.QUALIFIED_TYPE_3) == 1) {
			lateRun();
			parse(TokenType.DOT);
			scope = optionOf(ret);
			pushCallStack(JavaGrammar.QualifiedType_3_2);
			annotations = parseAnnotations();
			popCallStack();
			pushCallStack(JavaGrammar.QualifiedType_3_3);
			name = parseName();
			popCallStack();
			if (predict(JavaGrammar.QUALIFIED_TYPE_3_4) == 1) {
				pushCallStack(JavaGrammar.QualifiedType_3_4_1);
				typeArgs = parseTypeArgumentsOrDiamond();
				popCallStack();
			}
			ret = dress(SQualifiedType.make(annotations, scope, name, optionOf(typeArgs)));
		}
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
	protected BUTree<SNodeList> parseTypeArguments() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<? extends SType> type;
		parse(TokenType.LT);
		if (match(0, TokenType.AT, TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.DOUBLE, TokenType.FLOAT, TokenType.HOOK, TokenType.IDENTIFIER, TokenType.INT, TokenType.LONG, TokenType.NODE_LIST_VARIABLE, TokenType.NODE_VARIABLE, TokenType.SHORT) != -1) {
			pushCallStack(JavaGrammar.TypeArguments_2_1);
			ret = parseTypeArgumentList();
			popCallStack();
		}
		parse(TokenType.GT);
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
		parse(TokenType.LT);
		if (match(0, TokenType.AT, TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.DOUBLE, TokenType.FLOAT, TokenType.HOOK, TokenType.IDENTIFIER, TokenType.INT, TokenType.LONG, TokenType.NODE_LIST_VARIABLE, TokenType.NODE_VARIABLE, TokenType.SHORT) != -1) {
			pushCallStack(JavaGrammar.TypeArgumentsOrDiamond_2_1);
			ret = parseTypeArgumentList();
			popCallStack();
		}
		parse(TokenType.GT);
		return ret;
	}

	/* choice(
		sequence(
			lookAhead({ quotesMode })
			nonTerminal(ret, NodeListVar)
			action({ return ret; })
		)
		sequence(
			nonTerminal(type, TypeArgument)
			action({ ret = append(ret, type); })
			zeroOrMore(
				terminal(COMMA)
				nonTerminal(type, TypeArgument)
				action({ ret = append(ret, type); })
			)
			action({ return ret; })
		)
	) */
	protected BUTree<SNodeList> parseTypeArgumentList() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<? extends SType> type;
		switch (getToken(0).kind) {
			case TokenType.NODE_LIST_VARIABLE:
				pushCallStack(JavaGrammar.TypeArgumentList_1_1);
				ret = parseNodeListVar();
				popCallStack();
				return ret;
			case TokenType.AT:
			case TokenType.BOOLEAN:
			case TokenType.BYTE:
			case TokenType.CHAR:
			case TokenType.DOUBLE:
			case TokenType.FLOAT:
			case TokenType.HOOK:
			case TokenType.IDENTIFIER:
			case TokenType.INT:
			case TokenType.LONG:
			case TokenType.NODE_VARIABLE:
			case TokenType.SHORT:
				pushCallStack(JavaGrammar.TypeArgumentList_2_1);
				type = parseTypeArgument();
				popCallStack();
				ret = append(ret, type);
				while (match(0, TokenType.COMMA) != -1) {
					parse(TokenType.COMMA);
					pushCallStack(JavaGrammar.TypeArgumentList_2_2_2);
					type = parseTypeArgument();
					popCallStack();
					ret = append(ret, type);
				}
				return ret;
			default:
				throw produceParseException(TokenType.AT, TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.DOUBLE, TokenType.FLOAT, TokenType.HOOK, TokenType.IDENTIFIER, TokenType.INT, TokenType.LONG, TokenType.NODE_LIST_VARIABLE, TokenType.NODE_VARIABLE, TokenType.SHORT);
		}
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
		run();
		pushCallStack(JavaGrammar.TypeArgument_1);
		annotations = parseAnnotations();
		popCallStack();
		switch (getToken(0).kind) {
			case TokenType.BOOLEAN:
			case TokenType.BYTE:
			case TokenType.CHAR:
			case TokenType.DOUBLE:
			case TokenType.FLOAT:
			case TokenType.IDENTIFIER:
			case TokenType.INT:
			case TokenType.LONG:
			case TokenType.NODE_VARIABLE:
			case TokenType.SHORT:
				pushCallStack(JavaGrammar.TypeArgument_2_1);
				ret = parseReferenceType(annotations);
				popCallStack();
				break;
			case TokenType.HOOK:
				pushCallStack(JavaGrammar.TypeArgument_2_2);
				ret = parseWildcard(annotations);
				popCallStack();
				break;
			default:
				throw produceParseException(TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.DOUBLE, TokenType.FLOAT, TokenType.HOOK, TokenType.IDENTIFIER, TokenType.INT, TokenType.LONG, TokenType.NODE_VARIABLE, TokenType.SHORT);
		}
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
		if (annotations == null) {
			run();
			annotations = emptyList();
		}
		parse(TokenType.HOOK);
		switch (getToken(0).kind) {
			case TokenType.EXTENDS:
				parse(TokenType.EXTENDS);
				run();
				pushCallStack(JavaGrammar.Wildcard_2_1_1_2);
				boundAnnotations = parseAnnotations();
				popCallStack();
				pushCallStack(JavaGrammar.Wildcard_2_1_1_3);
				ext = parseReferenceType(boundAnnotations);
				popCallStack();
				break;
			case TokenType.SUPER:
				parse(TokenType.SUPER);
				run();
				pushCallStack(JavaGrammar.Wildcard_2_1_2_2);
				boundAnnotations = parseAnnotations();
				popCallStack();
				pushCallStack(JavaGrammar.Wildcard_2_1_2_3);
				sup = parseReferenceType(boundAnnotations);
				popCallStack();
				break;
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
		if (annotations == null) {
			run();
			annotations = emptyList();
		}
		switch (getToken(0).kind) {
			case TokenType.BOOLEAN:
				parse(TokenType.BOOLEAN);
				primitive = Primitive.Boolean;
				break;
			case TokenType.CHAR:
				parse(TokenType.CHAR);
				primitive = Primitive.Char;
				break;
			case TokenType.BYTE:
				parse(TokenType.BYTE);
				primitive = Primitive.Byte;
				break;
			case TokenType.SHORT:
				parse(TokenType.SHORT);
				primitive = Primitive.Short;
				break;
			case TokenType.INT:
				parse(TokenType.INT);
				primitive = Primitive.Int;
				break;
			case TokenType.LONG:
				parse(TokenType.LONG);
				primitive = Primitive.Long;
				break;
			case TokenType.FLOAT:
				parse(TokenType.FLOAT);
				primitive = Primitive.Float;
				break;
			case TokenType.DOUBLE:
				parse(TokenType.DOUBLE);
				primitive = Primitive.Double;
				break;
			default:
				throw produceParseException(TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.DOUBLE, TokenType.FLOAT, TokenType.INT, TokenType.LONG, TokenType.SHORT);
		}
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
		switch (getToken(0).kind) {
			case TokenType.VOID:
				run();
				parse(TokenType.VOID);
				ret = dress(SVoidType.make());
				break;
			case TokenType.BOOLEAN:
			case TokenType.BYTE:
			case TokenType.CHAR:
			case TokenType.DOUBLE:
			case TokenType.FLOAT:
			case TokenType.IDENTIFIER:
			case TokenType.INT:
			case TokenType.LONG:
			case TokenType.NODE_VARIABLE:
			case TokenType.SHORT:
				pushCallStack(JavaGrammar.ResultType_1_2);
				ret = parseType(null);
				popCallStack();
				break;
			default:
				throw produceParseException(TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.DOUBLE, TokenType.FLOAT, TokenType.IDENTIFIER, TokenType.INT, TokenType.LONG, TokenType.NODE_VARIABLE, TokenType.SHORT, TokenType.VOID);
		}
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
		run();
		pushCallStack(JavaGrammar.AnnotatedQualifiedType_1);
		annotations = parseAnnotations();
		popCallStack();
		pushCallStack(JavaGrammar.AnnotatedQualifiedType_2);
		ret = parseQualifiedType(annotations);
		popCallStack();
		return ret;
	}

	/* sequence(
		action({ run(); })
		nonTerminal(name, Name)
		action({ ret = dress(SQualifiedName.make(qualifier, name)); })
		zeroOrMore(
			action({ lateRun(); })
			terminal(DOT)
			action({ qualifier = optionOf(ret); })
			nonTerminal(name, Name)
			action({ ret = dress(SQualifiedName.make(qualifier, name)); })
		)
		action({ return ret; })
	) */
	protected BUTree<SQualifiedName> parseQualifiedName() throws ParseException {
		BUTree<SNodeOption> qualifier = none();
		BUTree<SQualifiedName> ret = null;
		BUTree<SName> name;
		run();
		pushCallStack(JavaGrammar.QualifiedName_1);
		name = parseName();
		popCallStack();
		ret = dress(SQualifiedName.make(qualifier, name));
		while (predict(JavaGrammar.QUALIFIED_NAME_2) == 1) {
			lateRun();
			parse(TokenType.DOT);
			qualifier = optionOf(ret);
			pushCallStack(JavaGrammar.QualifiedName_2_2);
			name = parseName();
			popCallStack();
			ret = dress(SQualifiedName.make(qualifier, name));
		}
		return ret;
	}

	/* sequence(
		choice(
			sequence(
				lookAhead({ quotesMode })
				nonTerminal(name, NodeVar)
			)
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
		switch (getToken(0).kind) {
			case TokenType.NODE_VARIABLE:
				pushCallStack(JavaGrammar.Name_1_1_1);
				name = parseNodeVar();
				popCallStack();
				break;
			case TokenType.IDENTIFIER:
				run();
				id = parse(TokenType.IDENTIFIER);
				name = dress(SName.make(id.image));
				break;
			default:
				throw produceParseException(TokenType.IDENTIFIER, TokenType.NODE_VARIABLE);
		}
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
		switch (predict(JavaGrammar.EXPRESSION_1)) {
			case 1:
				pushCallStack(JavaGrammar.Expression_1_1);
				ret = parseAssignmentExpression();
				popCallStack();
				break;
			case 2:
				pushCallStack(JavaGrammar.Expression_1_2);
				ret = parseLambdaExpression();
				popCallStack();
				break;
			default:
				throw produceParseException(TokenType.BANG, TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.CHARACTER_LITERAL, TokenType.DECR, TokenType.DOUBLE, TokenType.DOUBLE_LITERAL, TokenType.FALSE, TokenType.FLOAT, TokenType.FLOAT_LITERAL, TokenType.IDENTIFIER, TokenType.INCR, TokenType.INT, TokenType.INTEGER_LITERAL, TokenType.LONG, TokenType.LONG_LITERAL, TokenType.LPAREN, TokenType.LT, TokenType.MINUS, TokenType.NEW, TokenType.NODE_VARIABLE, TokenType.NULL, TokenType.PLUS, TokenType.SHORT, TokenType.STRING_LITERAL, TokenType.SUPER, TokenType.THIS, TokenType.TILDE, TokenType.TRUE, TokenType.VOID);
		}
		return ret;
	}

	/* sequence(
		nonTerminal(ret, ConditionalExpression)
		zeroOrOne(
			action({ lateRun(); })
			nonTerminal(op, AssignmentOperator)
			nonTerminal(expr, Expression)
			action({ ret = dress(SAssignExpr.make(ret, op, expr)); })
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parseAssignmentExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		AssignOp op;
		BUTree<? extends SExpr> expr;
		pushCallStack(JavaGrammar.AssignmentExpression_1);
		ret = parseConditionalExpression();
		popCallStack();
		if (match(0, TokenType.ANDASSIGN, TokenType.ASSIGN, TokenType.LSHIFTASSIGN, TokenType.MINUSASSIGN, TokenType.ORASSIGN, TokenType.PLUSASSIGN, TokenType.REMASSIGN, TokenType.RSIGNEDSHIFTASSIGN, TokenType.RUNSIGNEDSHIFTASSIGN, TokenType.SLASHASSIGN, TokenType.STARASSIGN, TokenType.XORASSIGN) != -1) {
			lateRun();
			pushCallStack(JavaGrammar.AssignmentExpression_2_1);
			op = parseAssignmentOperator();
			popCallStack();
			pushCallStack(JavaGrammar.AssignmentExpression_2_2);
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
		switch (predict(JavaGrammar.LAMBDA_EXPRESSION_1)) {
			case 1:
				run();
				parse(TokenType.LPAREN);
				run();
				pushCallStack(JavaGrammar.LambdaExpression_1_1_2);
				annotations = parseAnnotations();
				popCallStack();
				pushCallStack(JavaGrammar.LambdaExpression_1_1_3);
				type = parseReferenceType(annotations);
				popCallStack();
				pushCallStack(JavaGrammar.LambdaExpression_1_1_4);
				type = parseReferenceCastTypeRest(type);
				popCallStack();
				parse(TokenType.RPAREN);
				pushCallStack(JavaGrammar.LambdaExpression_1_1_6);
				ret = parseLambdaExpression();
				popCallStack();
				ret = dress(SCastExpr.make(type, ret));
				break;
			case 2:
				pushCallStack(JavaGrammar.LambdaExpression_1_2);
				ret = parseLambdaExpressionWithoutCast();
				popCallStack();
				break;
			default:
				throw produceParseException(TokenType.IDENTIFIER, TokenType.LPAREN, TokenType.NODE_VARIABLE);
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
		run();
		switch (predict(JavaGrammar.LAMBDA_EXPRESSION_WITHOUT_CAST_1)) {
			case 1:
				pushCallStack(JavaGrammar.LambdaExpressionWithoutCast_1_1_1);
				name = parseName();
				popCallStack();
				parse(TokenType.ARROW);
				pushCallStack(JavaGrammar.LambdaExpressionWithoutCast_1_1_3);
				ret = parseLambdaBody(singletonList(makeFormalParameter(name)), false);
				popCallStack();
				break;
			case 2:
				parse(TokenType.LPAREN);
				parse(TokenType.RPAREN);
				parse(TokenType.ARROW);
				pushCallStack(JavaGrammar.LambdaExpressionWithoutCast_1_2_4);
				ret = parseLambdaBody(emptyList(), true);
				popCallStack();
				break;
			case 3:
				parse(TokenType.LPAREN);
				pushCallStack(JavaGrammar.LambdaExpressionWithoutCast_1_3_2);
				params = parseInferredFormalParameterList();
				popCallStack();
				parse(TokenType.RPAREN);
				parse(TokenType.ARROW);
				pushCallStack(JavaGrammar.LambdaExpressionWithoutCast_1_3_5);
				ret = parseLambdaBody(params, true);
				popCallStack();
				break;
			case 4:
				parse(TokenType.LPAREN);
				pushCallStack(JavaGrammar.LambdaExpressionWithoutCast_1_4_2);
				params = parseFormalParameterList();
				popCallStack();
				parse(TokenType.RPAREN);
				parse(TokenType.ARROW);
				pushCallStack(JavaGrammar.LambdaExpressionWithoutCast_1_4_5);
				ret = parseLambdaBody(params, true);
				popCallStack();
				break;
			default:
				throw produceParseException(TokenType.IDENTIFIER, TokenType.LPAREN, TokenType.NODE_VARIABLE);
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
		switch (getToken(0).kind) {
			case TokenType.BANG:
			case TokenType.BOOLEAN:
			case TokenType.BYTE:
			case TokenType.CHAR:
			case TokenType.CHARACTER_LITERAL:
			case TokenType.DECR:
			case TokenType.DOUBLE:
			case TokenType.DOUBLE_LITERAL:
			case TokenType.FALSE:
			case TokenType.FLOAT:
			case TokenType.FLOAT_LITERAL:
			case TokenType.IDENTIFIER:
			case TokenType.INCR:
			case TokenType.INT:
			case TokenType.INTEGER_LITERAL:
			case TokenType.LONG:
			case TokenType.LONG_LITERAL:
			case TokenType.LPAREN:
			case TokenType.LT:
			case TokenType.MINUS:
			case TokenType.NEW:
			case TokenType.NODE_VARIABLE:
			case TokenType.NULL:
			case TokenType.PLUS:
			case TokenType.SHORT:
			case TokenType.STRING_LITERAL:
			case TokenType.SUPER:
			case TokenType.THIS:
			case TokenType.TILDE:
			case TokenType.TRUE:
			case TokenType.VOID:
				pushCallStack(JavaGrammar.LambdaBody_1_1_1);
				expr = parseExpression();
				popCallStack();
				ret = dress(SLambdaExpr.make(parameters, parenthesis, left(expr)));
				break;
			case TokenType.LBRACE:
				pushCallStack(JavaGrammar.LambdaBody_1_2_1);
				block = parseBlock();
				popCallStack();
				ret = dress(SLambdaExpr.make(parameters, parenthesis, right(block)));
				break;
			default:
				throw produceParseException(TokenType.BANG, TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.CHARACTER_LITERAL, TokenType.DECR, TokenType.DOUBLE, TokenType.DOUBLE_LITERAL, TokenType.FALSE, TokenType.FLOAT, TokenType.FLOAT_LITERAL, TokenType.IDENTIFIER, TokenType.INCR, TokenType.INT, TokenType.INTEGER_LITERAL, TokenType.LBRACE, TokenType.LONG, TokenType.LONG_LITERAL, TokenType.LPAREN, TokenType.LT, TokenType.MINUS, TokenType.NEW, TokenType.NODE_VARIABLE, TokenType.NULL, TokenType.PLUS, TokenType.SHORT, TokenType.STRING_LITERAL, TokenType.SUPER, TokenType.THIS, TokenType.TILDE, TokenType.TRUE, TokenType.VOID);
		}
		return ret;
	}

	/* sequence(
		nonTerminal(param, InferredFormalParameter)
		action({ ret = append(ret, param); })
		zeroOrMore(
			terminal(COMMA)
			nonTerminal(param, InferredFormalParameter)
			action({ ret = append(ret, param); })
		)
		action({ return ret; })
	) */
	protected BUTree<SNodeList> parseInferredFormalParameterList() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<SFormalParameter> param;
		pushCallStack(JavaGrammar.InferredFormalParameterList_1);
		param = parseInferredFormalParameter();
		popCallStack();
		ret = append(ret, param);
		while (match(0, TokenType.COMMA) != -1) {
			parse(TokenType.COMMA);
			pushCallStack(JavaGrammar.InferredFormalParameterList_2_2);
			param = parseInferredFormalParameter();
			popCallStack();
			ret = append(ret, param);
		}
		return ret;
	}

	/* sequence(
		nonTerminal(name, Name)
		action({ return makeFormalParameter(name); })
	) */
	protected BUTree<SFormalParameter> parseInferredFormalParameter() throws ParseException {
		BUTree<SName> name;
		pushCallStack(JavaGrammar.InferredFormalParameter_1);
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
		switch (getToken(0).kind) {
			case TokenType.ASSIGN:
				parse(TokenType.ASSIGN);
				ret = AssignOp.Normal;
				break;
			case TokenType.STARASSIGN:
				parse(TokenType.STARASSIGN);
				ret = AssignOp.Times;
				break;
			case TokenType.SLASHASSIGN:
				parse(TokenType.SLASHASSIGN);
				ret = AssignOp.Divide;
				break;
			case TokenType.REMASSIGN:
				parse(TokenType.REMASSIGN);
				ret = AssignOp.Remainder;
				break;
			case TokenType.PLUSASSIGN:
				parse(TokenType.PLUSASSIGN);
				ret = AssignOp.Plus;
				break;
			case TokenType.MINUSASSIGN:
				parse(TokenType.MINUSASSIGN);
				ret = AssignOp.Minus;
				break;
			case TokenType.LSHIFTASSIGN:
				parse(TokenType.LSHIFTASSIGN);
				ret = AssignOp.LeftShift;
				break;
			case TokenType.RSIGNEDSHIFTASSIGN:
				parse(TokenType.RSIGNEDSHIFTASSIGN);
				ret = AssignOp.RightSignedShift;
				break;
			case TokenType.RUNSIGNEDSHIFTASSIGN:
				parse(TokenType.RUNSIGNEDSHIFTASSIGN);
				ret = AssignOp.RightUnsignedShift;
				break;
			case TokenType.ANDASSIGN:
				parse(TokenType.ANDASSIGN);
				ret = AssignOp.And;
				break;
			case TokenType.XORASSIGN:
				parse(TokenType.XORASSIGN);
				ret = AssignOp.XOr;
				break;
			case TokenType.ORASSIGN:
				parse(TokenType.ORASSIGN);
				ret = AssignOp.Or;
				break;
			default:
				throw produceParseException(TokenType.ANDASSIGN, TokenType.ASSIGN, TokenType.LSHIFTASSIGN, TokenType.MINUSASSIGN, TokenType.ORASSIGN, TokenType.PLUSASSIGN, TokenType.REMASSIGN, TokenType.RSIGNEDSHIFTASSIGN, TokenType.RUNSIGNEDSHIFTASSIGN, TokenType.SLASHASSIGN, TokenType.STARASSIGN, TokenType.XORASSIGN);
		}
		return ret;
	}

	/* sequence(
		nonTerminal(ret, ConditionalOrExpression)
		zeroOrOne(
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
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parseConditionalExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> left;
		BUTree<? extends SExpr> right;
		pushCallStack(JavaGrammar.ConditionalExpression_1);
		ret = parseConditionalOrExpression();
		popCallStack();
		if (match(0, TokenType.HOOK) != -1) {
			lateRun();
			parse(TokenType.HOOK);
			pushCallStack(JavaGrammar.ConditionalExpression_2_2);
			left = parseExpression();
			popCallStack();
			parse(TokenType.COLON);
			switch (predict(JavaGrammar.CONDITIONAL_EXPRESSION_2_4)) {
				case 1:
					pushCallStack(JavaGrammar.ConditionalExpression_2_4_1);
					right = parseConditionalExpression();
					popCallStack();
					break;
				case 2:
					pushCallStack(JavaGrammar.ConditionalExpression_2_4_2);
					right = parseLambdaExpression();
					popCallStack();
					break;
				default:
					throw produceParseException(TokenType.BANG, TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.CHARACTER_LITERAL, TokenType.DECR, TokenType.DOUBLE, TokenType.DOUBLE_LITERAL, TokenType.FALSE, TokenType.FLOAT, TokenType.FLOAT_LITERAL, TokenType.IDENTIFIER, TokenType.INCR, TokenType.INT, TokenType.INTEGER_LITERAL, TokenType.LONG, TokenType.LONG_LITERAL, TokenType.LPAREN, TokenType.LT, TokenType.MINUS, TokenType.NEW, TokenType.NODE_VARIABLE, TokenType.NULL, TokenType.PLUS, TokenType.SHORT, TokenType.STRING_LITERAL, TokenType.SUPER, TokenType.THIS, TokenType.TILDE, TokenType.TRUE, TokenType.VOID);
			}
			ret = dress(SConditionalExpr.make(ret, left, right));
		}
		return ret;
	}

	/* sequence(
		nonTerminal(ret, ConditionalAndExpression)
		zeroOrMore(
			action({ lateRun(); })
			terminal(SC_OR)
			nonTerminal(right, ConditionalAndExpression)
			action({ ret = dress(SBinaryExpr.make(ret, BinaryOp.Or, right)); })
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parseConditionalOrExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		pushCallStack(JavaGrammar.ConditionalOrExpression_1);
		ret = parseConditionalAndExpression();
		popCallStack();
		while (match(0, TokenType.SC_OR) != -1) {
			lateRun();
			parse(TokenType.SC_OR);
			pushCallStack(JavaGrammar.ConditionalOrExpression_2_2);
			right = parseConditionalAndExpression();
			popCallStack();
			ret = dress(SBinaryExpr.make(ret, BinaryOp.Or, right));
		}
		return ret;
	}

	/* sequence(
		nonTerminal(ret, InclusiveOrExpression)
		zeroOrMore(
			action({ lateRun(); })
			terminal(SC_AND)
			nonTerminal(right, InclusiveOrExpression)
			action({ ret = dress(SBinaryExpr.make(ret, BinaryOp.And, right)); })
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parseConditionalAndExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		pushCallStack(JavaGrammar.ConditionalAndExpression_1);
		ret = parseInclusiveOrExpression();
		popCallStack();
		while (match(0, TokenType.SC_AND) != -1) {
			lateRun();
			parse(TokenType.SC_AND);
			pushCallStack(JavaGrammar.ConditionalAndExpression_2_2);
			right = parseInclusiveOrExpression();
			popCallStack();
			ret = dress(SBinaryExpr.make(ret, BinaryOp.And, right));
		}
		return ret;
	}

	/* sequence(
		nonTerminal(ret, ExclusiveOrExpression)
		zeroOrMore(
			action({ lateRun(); })
			terminal(BIT_OR)
			nonTerminal(right, ExclusiveOrExpression)
			action({ ret = dress(SBinaryExpr.make(ret, BinaryOp.BinOr, right)); })
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parseInclusiveOrExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		pushCallStack(JavaGrammar.InclusiveOrExpression_1);
		ret = parseExclusiveOrExpression();
		popCallStack();
		while (match(0, TokenType.BIT_OR) != -1) {
			lateRun();
			parse(TokenType.BIT_OR);
			pushCallStack(JavaGrammar.InclusiveOrExpression_2_2);
			right = parseExclusiveOrExpression();
			popCallStack();
			ret = dress(SBinaryExpr.make(ret, BinaryOp.BinOr, right));
		}
		return ret;
	}

	/* sequence(
		nonTerminal(ret, AndExpression)
		zeroOrMore(
			action({ lateRun(); })
			terminal(XOR)
			nonTerminal(right, AndExpression)
			action({ ret = dress(SBinaryExpr.make(ret, BinaryOp.XOr, right)); })
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parseExclusiveOrExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		pushCallStack(JavaGrammar.ExclusiveOrExpression_1);
		ret = parseAndExpression();
		popCallStack();
		while (match(0, TokenType.XOR) != -1) {
			lateRun();
			parse(TokenType.XOR);
			pushCallStack(JavaGrammar.ExclusiveOrExpression_2_2);
			right = parseAndExpression();
			popCallStack();
			ret = dress(SBinaryExpr.make(ret, BinaryOp.XOr, right));
		}
		return ret;
	}

	/* sequence(
		nonTerminal(ret, EqualityExpression)
		zeroOrMore(
			action({ lateRun(); })
			terminal(BIT_AND)
			nonTerminal(right, EqualityExpression)
			action({ ret = dress(SBinaryExpr.make(ret, BinaryOp.BinAnd, right)); })
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parseAndExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		pushCallStack(JavaGrammar.AndExpression_1);
		ret = parseEqualityExpression();
		popCallStack();
		while (match(0, TokenType.BIT_AND) != -1) {
			lateRun();
			parse(TokenType.BIT_AND);
			pushCallStack(JavaGrammar.AndExpression_2_2);
			right = parseEqualityExpression();
			popCallStack();
			ret = dress(SBinaryExpr.make(ret, BinaryOp.BinAnd, right));
		}
		return ret;
	}

	/* sequence(
		nonTerminal(ret, InstanceOfExpression)
		zeroOrMore(
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
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parseEqualityExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		BinaryOp op;
		pushCallStack(JavaGrammar.EqualityExpression_1);
		ret = parseInstanceOfExpression();
		popCallStack();
		while (match(0, TokenType.EQ, TokenType.NE) != -1) {
			lateRun();
			switch (getToken(0).kind) {
				case TokenType.EQ:
					parse(TokenType.EQ);
					op = BinaryOp.Equal;
					break;
				case TokenType.NE:
					parse(TokenType.NE);
					op = BinaryOp.NotEqual;
					break;
				default:
					throw produceParseException(TokenType.EQ, TokenType.NE);
			}
			pushCallStack(JavaGrammar.EqualityExpression_2_2);
			right = parseInstanceOfExpression();
			popCallStack();
			ret = dress(SBinaryExpr.make(ret, op, right));
		}
		return ret;
	}

	/* sequence(
		nonTerminal(ret, RelationalExpression)
		zeroOrOne(
			action({ lateRun(); })
			terminal(INSTANCEOF)
			action({ run(); })
			nonTerminal(annotations, Annotations)
			nonTerminal(type, Type)
			action({ ret = dress(SInstanceOfExpr.make(ret, type)); })
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parseInstanceOfExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<SNodeList> annotations;
		BUTree<? extends SType> type;
		pushCallStack(JavaGrammar.InstanceOfExpression_1);
		ret = parseRelationalExpression();
		popCallStack();
		if (match(0, TokenType.INSTANCEOF) != -1) {
			lateRun();
			parse(TokenType.INSTANCEOF);
			run();
			pushCallStack(JavaGrammar.InstanceOfExpression_2_2);
			annotations = parseAnnotations();
			popCallStack();
			pushCallStack(JavaGrammar.InstanceOfExpression_2_3);
			type = parseType(annotations);
			popCallStack();
			ret = dress(SInstanceOfExpr.make(ret, type));
		}
		return ret;
	}

	/* sequence(
		nonTerminal(ret, ShiftExpression)
		zeroOrMore(
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
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parseRelationalExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		BinaryOp op;
		pushCallStack(JavaGrammar.RelationalExpression_1);
		ret = parseShiftExpression();
		popCallStack();
		while (match(0, TokenType.GE, TokenType.GT, TokenType.LE, TokenType.LT) != -1) {
			lateRun();
			switch (getToken(0).kind) {
				case TokenType.LT:
					parse(TokenType.LT);
					op = BinaryOp.Less;
					break;
				case TokenType.GT:
					parse(TokenType.GT);
					op = BinaryOp.Greater;
					break;
				case TokenType.LE:
					parse(TokenType.LE);
					op = BinaryOp.LessOrEqual;
					break;
				case TokenType.GE:
					parse(TokenType.GE);
					op = BinaryOp.GreaterOrEqual;
					break;
				default:
					throw produceParseException(TokenType.GE, TokenType.GT, TokenType.LE, TokenType.LT);
			}
			pushCallStack(JavaGrammar.RelationalExpression_2_2);
			right = parseShiftExpression();
			popCallStack();
			ret = dress(SBinaryExpr.make(ret, op, right));
		}
		return ret;
	}

	/* sequence(
		nonTerminal(ret, AdditiveExpression)
		zeroOrMore(
			action({ lateRun(); })
			choice(
				sequence(
					terminal(LSHIFT)
					action({ op = BinaryOp.LeftShift; })
				)
				sequence(
					lookAhead(3)
					nonTerminal(RUNSIGNEDSHIFT)
					action({ op = BinaryOp.RightUnsignedShift; })
				)
				sequence(
					nonTerminal(RSIGNEDSHIFT)
					action({ op = BinaryOp.RightSignedShift; })
				)
			)
			nonTerminal(right, AdditiveExpression)
			action({ ret = dress(SBinaryExpr.make(ret, op, right)); })
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parseShiftExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		BinaryOp op;
		pushCallStack(JavaGrammar.ShiftExpression_1);
		ret = parseAdditiveExpression();
		popCallStack();
		while (predict(JavaGrammar.SHIFT_EXPRESSION_2) == 1) {
			lateRun();
			switch (predict(JavaGrammar.SHIFT_EXPRESSION_2_1)) {
				case 1:
					parse(TokenType.LSHIFT);
					op = BinaryOp.LeftShift;
					break;
				case 2:
					pushCallStack(JavaGrammar.ShiftExpression_2_1_2_1);
					parseRUNSIGNEDSHIFT();
					popCallStack();
					op = BinaryOp.RightUnsignedShift;
					break;
				case 3:
					pushCallStack(JavaGrammar.ShiftExpression_2_1_3_1);
					parseRSIGNEDSHIFT();
					popCallStack();
					op = BinaryOp.RightSignedShift;
					break;
				default:
					throw produceParseException(TokenType.GT, TokenType.LSHIFT);
			}
			pushCallStack(JavaGrammar.ShiftExpression_2_2);
			right = parseAdditiveExpression();
			popCallStack();
			ret = dress(SBinaryExpr.make(ret, op, right));
		}
		return ret;
	}

	/* sequence(
		nonTerminal(ret, MultiplicativeExpression)
		zeroOrMore(
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
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parseAdditiveExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		BinaryOp op;
		pushCallStack(JavaGrammar.AdditiveExpression_1);
		ret = parseMultiplicativeExpression();
		popCallStack();
		while (match(0, TokenType.MINUS, TokenType.PLUS) != -1) {
			lateRun();
			switch (getToken(0).kind) {
				case TokenType.PLUS:
					parse(TokenType.PLUS);
					op = BinaryOp.Plus;
					break;
				case TokenType.MINUS:
					parse(TokenType.MINUS);
					op = BinaryOp.Minus;
					break;
				default:
					throw produceParseException(TokenType.MINUS, TokenType.PLUS);
			}
			pushCallStack(JavaGrammar.AdditiveExpression_2_2);
			right = parseMultiplicativeExpression();
			popCallStack();
			ret = dress(SBinaryExpr.make(ret, op, right));
		}
		return ret;
	}

	/* sequence(
		nonTerminal(ret, UnaryExpression)
		zeroOrMore(
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
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parseMultiplicativeExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		BinaryOp op;
		pushCallStack(JavaGrammar.MultiplicativeExpression_1);
		ret = parseUnaryExpression();
		popCallStack();
		while (match(0, TokenType.REM, TokenType.SLASH, TokenType.STAR) != -1) {
			lateRun();
			switch (getToken(0).kind) {
				case TokenType.STAR:
					parse(TokenType.STAR);
					op = BinaryOp.Times;
					break;
				case TokenType.SLASH:
					parse(TokenType.SLASH);
					op = BinaryOp.Divide;
					break;
				case TokenType.REM:
					parse(TokenType.REM);
					op = BinaryOp.Remainder;
					break;
				default:
					throw produceParseException(TokenType.REM, TokenType.SLASH, TokenType.STAR);
			}
			pushCallStack(JavaGrammar.MultiplicativeExpression_2_2);
			right = parseUnaryExpression();
			popCallStack();
			ret = dress(SBinaryExpr.make(ret, op, right));
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
		switch (getToken(0).kind) {
			case TokenType.DECR:
			case TokenType.INCR:
				pushCallStack(JavaGrammar.UnaryExpression_1_1);
				ret = parsePrefixExpression();
				popCallStack();
				break;
			case TokenType.MINUS:
			case TokenType.PLUS:
				run();
				switch (getToken(0).kind) {
					case TokenType.PLUS:
						parse(TokenType.PLUS);
						op = UnaryOp.Positive;
						break;
					case TokenType.MINUS:
						parse(TokenType.MINUS);
						op = UnaryOp.Negative;
						break;
					default:
						throw produceParseException(TokenType.MINUS, TokenType.PLUS);
				}
				pushCallStack(JavaGrammar.UnaryExpression_1_2_2);
				ret = parseUnaryExpression();
				popCallStack();
				ret = dress(SUnaryExpr.make(op, ret));
				break;
			case TokenType.BANG:
			case TokenType.BOOLEAN:
			case TokenType.BYTE:
			case TokenType.CHAR:
			case TokenType.CHARACTER_LITERAL:
			case TokenType.DOUBLE:
			case TokenType.DOUBLE_LITERAL:
			case TokenType.FALSE:
			case TokenType.FLOAT:
			case TokenType.FLOAT_LITERAL:
			case TokenType.IDENTIFIER:
			case TokenType.INT:
			case TokenType.INTEGER_LITERAL:
			case TokenType.LONG:
			case TokenType.LONG_LITERAL:
			case TokenType.LPAREN:
			case TokenType.LT:
			case TokenType.NEW:
			case TokenType.NODE_VARIABLE:
			case TokenType.NULL:
			case TokenType.SHORT:
			case TokenType.STRING_LITERAL:
			case TokenType.SUPER:
			case TokenType.THIS:
			case TokenType.TILDE:
			case TokenType.TRUE:
			case TokenType.VOID:
				pushCallStack(JavaGrammar.UnaryExpression_1_3);
				ret = parseUnaryExpressionNotPlusMinus();
				popCallStack();
				break;
			default:
				throw produceParseException(TokenType.BANG, TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.CHARACTER_LITERAL, TokenType.DECR, TokenType.DOUBLE, TokenType.DOUBLE_LITERAL, TokenType.FALSE, TokenType.FLOAT, TokenType.FLOAT_LITERAL, TokenType.IDENTIFIER, TokenType.INCR, TokenType.INT, TokenType.INTEGER_LITERAL, TokenType.LONG, TokenType.LONG_LITERAL, TokenType.LPAREN, TokenType.LT, TokenType.MINUS, TokenType.NEW, TokenType.NODE_VARIABLE, TokenType.NULL, TokenType.PLUS, TokenType.SHORT, TokenType.STRING_LITERAL, TokenType.SUPER, TokenType.THIS, TokenType.TILDE, TokenType.TRUE, TokenType.VOID);
		}
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
		run();
		switch (getToken(0).kind) {
			case TokenType.INCR:
				parse(TokenType.INCR);
				op = UnaryOp.PreIncrement;
				break;
			case TokenType.DECR:
				parse(TokenType.DECR);
				op = UnaryOp.PreDecrement;
				break;
			default:
				throw produceParseException(TokenType.DECR, TokenType.INCR);
		}
		pushCallStack(JavaGrammar.PrefixExpression_2);
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
		switch (predict(JavaGrammar.UNARY_EXPRESSION_NOT_PLUS_MINUS_1)) {
			case 1:
				run();
				switch (getToken(0).kind) {
					case TokenType.TILDE:
						parse(TokenType.TILDE);
						op = UnaryOp.Inverse;
						break;
					case TokenType.BANG:
						parse(TokenType.BANG);
						op = UnaryOp.Not;
						break;
					default:
						throw produceParseException(TokenType.BANG, TokenType.TILDE);
				}
				pushCallStack(JavaGrammar.UnaryExpressionNotPlusMinus_1_1_2);
				ret = parseUnaryExpression();
				popCallStack();
				ret = dress(SUnaryExpr.make(op, ret));
				break;
			case 2:
				pushCallStack(JavaGrammar.UnaryExpressionNotPlusMinus_1_2);
				ret = parseCastExpression();
				popCallStack();
				break;
			case 3:
				pushCallStack(JavaGrammar.UnaryExpressionNotPlusMinus_1_3);
				ret = parsePostfixExpression();
				popCallStack();
				break;
			default:
				throw produceParseException(TokenType.BANG, TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.CHARACTER_LITERAL, TokenType.DOUBLE, TokenType.DOUBLE_LITERAL, TokenType.FALSE, TokenType.FLOAT, TokenType.FLOAT_LITERAL, TokenType.IDENTIFIER, TokenType.INT, TokenType.INTEGER_LITERAL, TokenType.LONG, TokenType.LONG_LITERAL, TokenType.LPAREN, TokenType.LT, TokenType.NEW, TokenType.NODE_VARIABLE, TokenType.NULL, TokenType.SHORT, TokenType.STRING_LITERAL, TokenType.SUPER, TokenType.THIS, TokenType.TILDE, TokenType.TRUE, TokenType.VOID);
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
		pushCallStack(JavaGrammar.PostfixExpression_1);
		ret = parsePrimaryExpression();
		popCallStack();
		if (match(0, TokenType.DECR, TokenType.INCR) != -1) {
			lateRun();
			switch (getToken(0).kind) {
				case TokenType.INCR:
					parse(TokenType.INCR);
					op = UnaryOp.PostIncrement;
					break;
				case TokenType.DECR:
					parse(TokenType.DECR);
					op = UnaryOp.PostDecrement;
					break;
				default:
					throw produceParseException(TokenType.DECR, TokenType.INCR);
			}
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
		run();
		parse(TokenType.LPAREN);
		run();
		pushCallStack(JavaGrammar.CastExpression_2);
		annotations = parseAnnotations();
		popCallStack();
		switch (predict(JavaGrammar.CAST_EXPRESSION_3)) {
			case 1:
				pushCallStack(JavaGrammar.CastExpression_3_1_1);
				primitiveType = parsePrimitiveType(annotations);
				popCallStack();
				parse(TokenType.RPAREN);
				pushCallStack(JavaGrammar.CastExpression_3_1_3);
				ret = parseUnaryExpression();
				popCallStack();
				ret = dress(SCastExpr.make(primitiveType, ret));
				break;
			case 2:
				pushCallStack(JavaGrammar.CastExpression_3_2_1);
				type = parseReferenceType(annotations);
				popCallStack();
				pushCallStack(JavaGrammar.CastExpression_3_2_2);
				type = parseReferenceCastTypeRest(type);
				popCallStack();
				parse(TokenType.RPAREN);
				pushCallStack(JavaGrammar.CastExpression_3_2_4);
				ret = parseUnaryExpressionNotPlusMinus();
				popCallStack();
				ret = dress(SCastExpr.make(type, ret));
				break;
			default:
				throw produceParseException(TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.DOUBLE, TokenType.FLOAT, TokenType.IDENTIFIER, TokenType.INT, TokenType.LONG, TokenType.NODE_VARIABLE, TokenType.SHORT);
		}
		return ret;
	}

	/* sequence(
		zeroOrOne(
			action({ types = append(types, type); })
			action({ lateRun(); })
			oneOrMore(
				terminal(BIT_AND)
				action({ run(); })
				nonTerminal(annotations, Annotations)
				nonTerminal(type, ReferenceType)
				action({ types = append(types, type); })
			)
			action({ type = dress(SIntersectionType.make(types)); })
		)
		action({ return type; })
	) */
	protected BUTree<? extends SType> parseReferenceCastTypeRest(BUTree<? extends SType> type) throws ParseException {
		BUTree<SNodeList> types = emptyList();
		BUTree<SNodeList> annotations = null;
		if (match(0, TokenType.BIT_AND) != -1) {
			types = append(types, type);
			lateRun();
			do {
				parse(TokenType.BIT_AND);
				run();
				pushCallStack(JavaGrammar.ReferenceCastTypeRest_1_1_2);
				annotations = parseAnnotations();
				popCallStack();
				pushCallStack(JavaGrammar.ReferenceCastTypeRest_1_1_3);
				type = parseReferenceType(annotations);
				popCallStack();
				types = append(types, type);
			} while (match(0, TokenType.BIT_AND) != -1);
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
		run();
		switch (getToken(0).kind) {
			case TokenType.INTEGER_LITERAL:
				literal = parse(TokenType.INTEGER_LITERAL);
				ret = SLiteralExpr.make(Integer.class, literal.image);
				break;
			case TokenType.LONG_LITERAL:
				literal = parse(TokenType.LONG_LITERAL);
				ret = SLiteralExpr.make(Long.class, literal.image);
				break;
			case TokenType.FLOAT_LITERAL:
				literal = parse(TokenType.FLOAT_LITERAL);
				ret = SLiteralExpr.make(Float.class, literal.image);
				break;
			case TokenType.DOUBLE_LITERAL:
				literal = parse(TokenType.DOUBLE_LITERAL);
				ret = SLiteralExpr.make(Double.class, literal.image);
				break;
			case TokenType.CHARACTER_LITERAL:
				literal = parse(TokenType.CHARACTER_LITERAL);
				ret = SLiteralExpr.make(Character.class, literal.image);
				break;
			case TokenType.STRING_LITERAL:
				literal = parse(TokenType.STRING_LITERAL);
				ret = SLiteralExpr.make(String.class, literal.image);
				break;
			case TokenType.TRUE:
				literal = parse(TokenType.TRUE);
				ret = SLiteralExpr.make(Boolean.class, literal.image);
				break;
			case TokenType.FALSE:
				literal = parse(TokenType.FALSE);
				ret = SLiteralExpr.make(Boolean.class, literal.image);
				break;
			case TokenType.NULL:
				literal = parse(TokenType.NULL);
				ret = SLiteralExpr.make(Void.class, literal.image);
				break;
			default:
				throw produceParseException(TokenType.CHARACTER_LITERAL, TokenType.DOUBLE_LITERAL, TokenType.FALSE, TokenType.FLOAT_LITERAL, TokenType.INTEGER_LITERAL, TokenType.LONG_LITERAL, TokenType.NULL, TokenType.STRING_LITERAL, TokenType.TRUE);
		}
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
		switch (predict(JavaGrammar.PRIMARY_EXPRESSION_1)) {
			case 1:
				pushCallStack(JavaGrammar.PrimaryExpression_1_1);
				ret = parsePrimaryNoNewArray();
				popCallStack();
				break;
			case 2:
				pushCallStack(JavaGrammar.PrimaryExpression_1_2);
				ret = parseArrayCreationExpr(null);
				popCallStack();
				break;
			default:
				throw produceParseException(TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.CHARACTER_LITERAL, TokenType.DOUBLE, TokenType.DOUBLE_LITERAL, TokenType.FALSE, TokenType.FLOAT, TokenType.FLOAT_LITERAL, TokenType.IDENTIFIER, TokenType.INT, TokenType.INTEGER_LITERAL, TokenType.LONG, TokenType.LONG_LITERAL, TokenType.LPAREN, TokenType.LT, TokenType.NEW, TokenType.NODE_VARIABLE, TokenType.NULL, TokenType.SHORT, TokenType.STRING_LITERAL, TokenType.SUPER, TokenType.THIS, TokenType.TRUE, TokenType.VOID);
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
		pushCallStack(JavaGrammar.PrimaryNoNewArray_1);
		ret = parsePrimaryPrefix();
		popCallStack();
		while (match(0, TokenType.DOT, TokenType.DOUBLECOLON, TokenType.LBRACKET) != -1) {
			lateRun();
			pushCallStack(JavaGrammar.PrimaryNoNewArray_2_1);
			ret = parsePrimarySuffix(ret);
			popCallStack();
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
		pushCallStack(JavaGrammar.PrimaryExpressionWithoutSuperSuffix_1);
		ret = parsePrimaryPrefix();
		popCallStack();
		while (predict(JavaGrammar.PRIMARY_EXPRESSION_WITHOUT_SUPER_SUFFIX_2) == 1) {
			lateRun();
			pushCallStack(JavaGrammar.PrimaryExpressionWithoutSuperSuffix_2_1);
			ret = parsePrimarySuffixWithoutSuper(ret);
			popCallStack();
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
			sequence(
				nonTerminal(ret, Name)
			)
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
		switch (predict(JavaGrammar.PRIMARY_PREFIX_1)) {
			case 1:
				pushCallStack(JavaGrammar.PrimaryPrefix_1_1);
				ret = parseLiteral();
				popCallStack();
				break;
			case 2:
				run();
				parse(TokenType.THIS);
				ret = dress(SThisExpr.make(none()));
				break;
			case 3:
				run();
				parse(TokenType.SUPER);
				ret = dress(SSuperExpr.make(none()));
				switch (getToken(0).kind) {
					case TokenType.DOT:
						lateRun();
						parse(TokenType.DOT);
						switch (predict(JavaGrammar.PRIMARY_PREFIX_1_3_2_1_2)) {
							case 1:
								pushCallStack(JavaGrammar.PrimaryPrefix_1_3_2_1_2_1);
								ret = parseMethodInvocation(ret);
								popCallStack();
								break;
							case 2:
								pushCallStack(JavaGrammar.PrimaryPrefix_1_3_2_1_2_2);
								ret = parseFieldAccess(ret);
								popCallStack();
								break;
							default:
								throw produceParseException(TokenType.IDENTIFIER, TokenType.LT, TokenType.NODE_VARIABLE);
						}
						break;
					case TokenType.DOUBLECOLON:
						lateRun();
						pushCallStack(JavaGrammar.PrimaryPrefix_1_3_2_2_1);
						ret = parseMethodReferenceSuffix(ret);
						popCallStack();
						break;
					default:
						throw produceParseException(TokenType.DOT, TokenType.DOUBLECOLON);
				}
				break;
			case 4:
				pushCallStack(JavaGrammar.PrimaryPrefix_1_4);
				ret = parseClassCreationExpr(null);
				popCallStack();
				break;
			case 5:
				run();
				pushCallStack(JavaGrammar.PrimaryPrefix_1_5_1);
				type = parseResultType();
				popCallStack();
				parse(TokenType.DOT);
				parse(TokenType.CLASS);
				ret = dress(SClassExpr.make(type));
				break;
			case 6:
				run();
				pushCallStack(JavaGrammar.PrimaryPrefix_1_6_1);
				type = parseResultType();
				popCallStack();
				ret = STypeExpr.make(type);
				pushCallStack(JavaGrammar.PrimaryPrefix_1_6_2);
				ret = parseMethodReferenceSuffix(ret);
				popCallStack();
				break;
			case 7:
				run();
				pushCallStack(JavaGrammar.PrimaryPrefix_1_7_1);
				ret = parseMethodInvocation(null);
				popCallStack();
				break;
			case 8:
				pushCallStack(JavaGrammar.PrimaryPrefix_1_8_1);
				ret = parseName();
				popCallStack();
				break;
			case 9:
				run();
				parse(TokenType.LPAREN);
				pushCallStack(JavaGrammar.PrimaryPrefix_1_9_2);
				ret = parseExpression();
				popCallStack();
				parse(TokenType.RPAREN);
				ret = dress(SParenthesizedExpr.make(ret));
				break;
			default:
				throw produceParseException(TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.CHARACTER_LITERAL, TokenType.DOUBLE, TokenType.DOUBLE_LITERAL, TokenType.FALSE, TokenType.FLOAT, TokenType.FLOAT_LITERAL, TokenType.IDENTIFIER, TokenType.INT, TokenType.INTEGER_LITERAL, TokenType.LONG, TokenType.LONG_LITERAL, TokenType.LPAREN, TokenType.LT, TokenType.NEW, TokenType.NODE_VARIABLE, TokenType.NULL, TokenType.SHORT, TokenType.STRING_LITERAL, TokenType.SUPER, TokenType.THIS, TokenType.TRUE, TokenType.VOID);
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
		switch (predict(JavaGrammar.PRIMARY_SUFFIX_1)) {
			case 1:
				pushCallStack(JavaGrammar.PrimarySuffix_1_1_1);
				ret = parsePrimarySuffixWithoutSuper(scope);
				popCallStack();
				break;
			case 2:
				parse(TokenType.DOT);
				parse(TokenType.SUPER);
				ret = dress(SSuperExpr.make(optionOf(scope)));
				break;
			case 3:
				pushCallStack(JavaGrammar.PrimarySuffix_1_3);
				ret = parseMethodReferenceSuffix(scope);
				popCallStack();
				break;
			default:
				throw produceParseException(TokenType.DOT, TokenType.DOUBLECOLON, TokenType.LBRACKET);
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
		switch (getToken(0).kind) {
			case TokenType.DOT:
				parse(TokenType.DOT);
				switch (predict(JavaGrammar.PRIMARY_SUFFIX_WITHOUT_SUPER_1_1_2)) {
					case 1:
						parse(TokenType.THIS);
						ret = dress(SThisExpr.make(optionOf(scope)));
						break;
					case 2:
						pushCallStack(JavaGrammar.PrimarySuffixWithoutSuper_1_1_2_2);
						ret = parseClassCreationExpr(scope);
						popCallStack();
						break;
					case 3:
						pushCallStack(JavaGrammar.PrimarySuffixWithoutSuper_1_1_2_3);
						ret = parseMethodInvocation(scope);
						popCallStack();
						break;
					case 4:
						pushCallStack(JavaGrammar.PrimarySuffixWithoutSuper_1_1_2_4);
						ret = parseFieldAccess(scope);
						popCallStack();
						break;
					default:
						throw produceParseException(TokenType.IDENTIFIER, TokenType.LT, TokenType.NEW, TokenType.NODE_VARIABLE, TokenType.THIS);
				}
				break;
			case TokenType.LBRACKET:
				parse(TokenType.LBRACKET);
				pushCallStack(JavaGrammar.PrimarySuffixWithoutSuper_1_2_2);
				ret = parseExpression();
				popCallStack();
				parse(TokenType.RBRACKET);
				ret = dress(SArrayAccessExpr.make(scope, ret));
				break;
			default:
				throw produceParseException(TokenType.DOT, TokenType.LBRACKET);
		}
		return ret;
	}

	/* sequence(
		nonTerminal(name, Name)
		action({ return dress(SFieldAccessExpr.make(optionOf(scope), name)); })
	) */
	protected BUTree<? extends SExpr> parseFieldAccess(BUTree<? extends SExpr> scope) throws ParseException {
		BUTree<SName> name;
		pushCallStack(JavaGrammar.FieldAccess_1);
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
		if (match(0, TokenType.LT) != -1) {
			pushCallStack(JavaGrammar.MethodInvocation_1_1);
			typeArgs = parseTypeArguments();
			popCallStack();
		}
		pushCallStack(JavaGrammar.MethodInvocation_2);
		name = parseName();
		popCallStack();
		pushCallStack(JavaGrammar.MethodInvocation_3);
		args = parseArguments();
		popCallStack();
		return dress(SMethodInvocationExpr.make(optionOf(scope), ensureNotNull(typeArgs), name, args));
	}

	/* sequence(
		terminal(LPAREN)
		zeroOrOne(
			choice(
				sequence(
					lookAhead({ quotesMode })
					nonTerminal(ret, NodeListVar)
				)
				sequence(
					nonTerminal(expr, Expression)
					action({ ret = append(ret, expr); })
					zeroOrMore(
						terminal(COMMA)
						nonTerminal(expr, Expression)
						action({ ret = append(ret, expr); })
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
		parse(TokenType.LPAREN);
		switch (getToken(0).kind) {
			case TokenType.NODE_LIST_VARIABLE:
				pushCallStack(JavaGrammar.Arguments_2_1_1_1);
				ret = parseNodeListVar();
				popCallStack();
				break;
			case TokenType.BANG:
			case TokenType.BOOLEAN:
			case TokenType.BYTE:
			case TokenType.CHAR:
			case TokenType.CHARACTER_LITERAL:
			case TokenType.DECR:
			case TokenType.DOUBLE:
			case TokenType.DOUBLE_LITERAL:
			case TokenType.FALSE:
			case TokenType.FLOAT:
			case TokenType.FLOAT_LITERAL:
			case TokenType.IDENTIFIER:
			case TokenType.INCR:
			case TokenType.INT:
			case TokenType.INTEGER_LITERAL:
			case TokenType.LONG:
			case TokenType.LONG_LITERAL:
			case TokenType.LPAREN:
			case TokenType.LT:
			case TokenType.MINUS:
			case TokenType.NEW:
			case TokenType.NODE_VARIABLE:
			case TokenType.NULL:
			case TokenType.PLUS:
			case TokenType.SHORT:
			case TokenType.STRING_LITERAL:
			case TokenType.SUPER:
			case TokenType.THIS:
			case TokenType.TILDE:
			case TokenType.TRUE:
			case TokenType.VOID:
				pushCallStack(JavaGrammar.Arguments_2_1_2_1);
				expr = parseExpression();
				popCallStack();
				ret = append(ret, expr);
				while (predict(JavaGrammar.ARGUMENTS_2_1_2_2) == 1) {
					parse(TokenType.COMMA);
					pushCallStack(JavaGrammar.Arguments_2_1_2_2_2);
					expr = parseExpression();
					popCallStack();
					ret = append(ret, expr);
				}
				break;
		}
		parse(TokenType.RPAREN);
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
		parse(TokenType.DOUBLECOLON);
		if (match(0, TokenType.LT) != -1) {
			pushCallStack(JavaGrammar.MethodReferenceSuffix_2_1);
			typeArgs = parseTypeArguments();
			popCallStack();
		}
		switch (getToken(0).kind) {
			case TokenType.IDENTIFIER:
			case TokenType.NODE_VARIABLE:
				pushCallStack(JavaGrammar.MethodReferenceSuffix_3_1);
				name = parseName();
				popCallStack();
				break;
			case TokenType.NEW:
				parse(TokenType.NEW);
				name = SName.make("new");
				break;
			default:
				throw produceParseException(TokenType.IDENTIFIER, TokenType.NEW, TokenType.NODE_VARIABLE);
		}
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
		if (scope == null) run();

		parse(TokenType.NEW);
		if (match(0, TokenType.LT) != -1) {
			pushCallStack(JavaGrammar.ClassCreationExpr_2_1);
			typeArgs = parseTypeArguments();
			popCallStack();
		}
		run();
		pushCallStack(JavaGrammar.ClassCreationExpr_3);
		annotations = parseAnnotations();
		popCallStack();
		pushCallStack(JavaGrammar.ClassCreationExpr_4);
		type = parseQualifiedType(annotations);
		popCallStack();
		pushCallStack(JavaGrammar.ClassCreationExpr_5);
		args = parseArguments();
		popCallStack();
		if (match(0, TokenType.LBRACE) != -1) {
			pushCallStack(JavaGrammar.ClassCreationExpr_6_1);
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
		if (scope == null) run();

		parse(TokenType.NEW);
		if (match(0, TokenType.LT) != -1) {
			pushCallStack(JavaGrammar.ArrayCreationExpr_2_1);
			typeArgs = parseTypeArguments();
			popCallStack();
		}
		run();
		pushCallStack(JavaGrammar.ArrayCreationExpr_3);
		annotations = parseAnnotations();
		popCallStack();
		switch (getToken(0).kind) {
			case TokenType.BOOLEAN:
			case TokenType.BYTE:
			case TokenType.CHAR:
			case TokenType.DOUBLE:
			case TokenType.FLOAT:
			case TokenType.INT:
			case TokenType.LONG:
			case TokenType.SHORT:
				pushCallStack(JavaGrammar.ArrayCreationExpr_4_1);
				type = parsePrimitiveType(annotations);
				popCallStack();
				break;
			case TokenType.IDENTIFIER:
			case TokenType.NODE_VARIABLE:
				pushCallStack(JavaGrammar.ArrayCreationExpr_4_2);
				type = parseQualifiedType(annotations);
				popCallStack();
				break;
			default:
				throw produceParseException(TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.DOUBLE, TokenType.FLOAT, TokenType.IDENTIFIER, TokenType.INT, TokenType.LONG, TokenType.NODE_VARIABLE, TokenType.SHORT);
		}
		pushCallStack(JavaGrammar.ArrayCreationExpr_5);
		ret = parseArrayCreationExprRest(type);
		popCallStack();
		return ret;
	}

	/* choice(
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
	) */
	protected BUTree<? extends SExpr> parseArrayCreationExprRest(BUTree<? extends SType> componentType) throws ParseException {
		BUTree<? extends SExpr> expr;
		BUTree<SNodeList> arrayDimExprs = emptyList();
		BUTree<SNodeList> arrayDims = emptyList();
		BUTree<SNodeList> annotations = null;
		BUTree<SArrayInitializerExpr> initializer;
		switch (predict(JavaGrammar.ARRAY_CREATION_EXPR_REST)) {
			case 1:
				pushCallStack(JavaGrammar.ArrayCreationExprRest_1_1);
				arrayDimExprs = parseArrayDimExprsMandatory();
				popCallStack();
				pushCallStack(JavaGrammar.ArrayCreationExprRest_1_2);
				arrayDims = parseArrayDims();
				popCallStack();
				return dress(SArrayCreationExpr.make(componentType, arrayDimExprs, arrayDims, none()));
			case 2:
				pushCallStack(JavaGrammar.ArrayCreationExprRest_2_1);
				arrayDims = parseArrayDimsMandatory();
				popCallStack();
				pushCallStack(JavaGrammar.ArrayCreationExprRest_2_2);
				initializer = parseArrayInitializer();
				popCallStack();
				return dress(SArrayCreationExpr.make(componentType, arrayDimExprs, arrayDims, optionOf(initializer)));
			default:
				throw produceParseException(TokenType.AT, TokenType.LBRACKET);
		}
	}

	/* sequence(
		oneOrMore(
			action({ run(); })
			nonTerminal(annotations, Annotations)
			terminal(LBRACKET)
			nonTerminal(expr, Expression)
			terminal(RBRACKET)
			action({ arrayDimExprs = append(arrayDimExprs, dress(SArrayDimExpr.make(annotations, expr))); })
		)
		action({ return arrayDimExprs; })
	) */
	protected BUTree<SNodeList> parseArrayDimExprsMandatory() throws ParseException {
		BUTree<SNodeList> arrayDimExprs = emptyList();
		BUTree<SNodeList> annotations;
		BUTree<? extends SExpr> expr;
		do {
			run();
			pushCallStack(JavaGrammar.ArrayDimExprsMandatory_1_1);
			annotations = parseAnnotations();
			popCallStack();
			parse(TokenType.LBRACKET);
			pushCallStack(JavaGrammar.ArrayDimExprsMandatory_1_3);
			expr = parseExpression();
			popCallStack();
			parse(TokenType.RBRACKET);
			arrayDimExprs = append(arrayDimExprs, dress(SArrayDimExpr.make(annotations, expr)));
		} while (predict(JavaGrammar.ARRAY_DIM_EXPRS_MANDATORY_1) == 1);
		return arrayDimExprs;
	}

	/* sequence(
		oneOrMore(
			action({ run(); })
			nonTerminal(annotations, Annotations)
			terminal(LBRACKET)
			terminal(RBRACKET)
			action({ arrayDims = append(arrayDims, dress(SArrayDim.make(annotations))); })
		)
		action({ return arrayDims; })
	) */
	protected BUTree<SNodeList> parseArrayDimsMandatory() throws ParseException {
		BUTree<SNodeList> arrayDims = emptyList();
		BUTree<SNodeList> annotations;
		do {
			run();
			pushCallStack(JavaGrammar.ArrayDimsMandatory_1_1);
			annotations = parseAnnotations();
			popCallStack();
			parse(TokenType.LBRACKET);
			parse(TokenType.RBRACKET);
			arrayDims = append(arrayDims, dress(SArrayDim.make(annotations)));
		} while (predict(JavaGrammar.ARRAY_DIMS_MANDATORY_1) == 1);
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
		switch (predict(JavaGrammar.STATEMENT_1)) {
			case 1:
				pushCallStack(JavaGrammar.Statement_1_1);
				ret = parseLabeledStatement();
				popCallStack();
				break;
			case 2:
				pushCallStack(JavaGrammar.Statement_1_2);
				ret = parseAssertStatement();
				popCallStack();
				break;
			case 3:
				pushCallStack(JavaGrammar.Statement_1_3);
				ret = parseBlock();
				popCallStack();
				break;
			case 4:
				pushCallStack(JavaGrammar.Statement_1_4);
				ret = parseEmptyStatement();
				popCallStack();
				break;
			case 5:
				pushCallStack(JavaGrammar.Statement_1_5);
				ret = parseExpressionStatement();
				popCallStack();
				break;
			case 6:
				pushCallStack(JavaGrammar.Statement_1_6);
				ret = parseSwitchStatement();
				popCallStack();
				break;
			case 7:
				pushCallStack(JavaGrammar.Statement_1_7);
				ret = parseIfStatement();
				popCallStack();
				break;
			case 8:
				pushCallStack(JavaGrammar.Statement_1_8);
				ret = parseWhileStatement();
				popCallStack();
				break;
			case 9:
				pushCallStack(JavaGrammar.Statement_1_9);
				ret = parseDoStatement();
				popCallStack();
				break;
			case 10:
				pushCallStack(JavaGrammar.Statement_1_10);
				ret = parseForStatement();
				popCallStack();
				break;
			case 11:
				pushCallStack(JavaGrammar.Statement_1_11);
				ret = parseBreakStatement();
				popCallStack();
				break;
			case 12:
				pushCallStack(JavaGrammar.Statement_1_12);
				ret = parseContinueStatement();
				popCallStack();
				break;
			case 13:
				pushCallStack(JavaGrammar.Statement_1_13);
				ret = parseReturnStatement();
				popCallStack();
				break;
			case 14:
				pushCallStack(JavaGrammar.Statement_1_14);
				ret = parseThrowStatement();
				popCallStack();
				break;
			case 15:
				pushCallStack(JavaGrammar.Statement_1_15);
				ret = parseSynchronizedStatement();
				popCallStack();
				break;
			case 16:
				pushCallStack(JavaGrammar.Statement_1_16);
				ret = parseTryStatement();
				popCallStack();
				break;
			default:
				throw produceParseException(TokenType.ASSERT, TokenType.BANG, TokenType.BOOLEAN, TokenType.BREAK, TokenType.BYTE, TokenType.CHAR, TokenType.CHARACTER_LITERAL, TokenType.CONTINUE, TokenType.DECR, TokenType.DO, TokenType.DOUBLE, TokenType.DOUBLE_LITERAL, TokenType.FALSE, TokenType.FLOAT, TokenType.FLOAT_LITERAL, TokenType.FOR, TokenType.IDENTIFIER, TokenType.IF, TokenType.INCR, TokenType.INT, TokenType.INTEGER_LITERAL, TokenType.LBRACE, TokenType.LONG, TokenType.LONG_LITERAL, TokenType.LPAREN, TokenType.LT, TokenType.MINUS, TokenType.NEW, TokenType.NODE_VARIABLE, TokenType.NULL, TokenType.PLUS, TokenType.RETURN, TokenType.SEMICOLON, TokenType.SHORT, TokenType.STRING_LITERAL, TokenType.SUPER, TokenType.SWITCH, TokenType.SYNCHRONIZED, TokenType.THIS, TokenType.THROW, TokenType.TILDE, TokenType.TRUE, TokenType.TRY, TokenType.VOID, TokenType.WHILE);
		}
		return ret;
	}

	/* sequence(
		action({ run(); })
		terminal(ASSERT)
		nonTerminal(check, Expression)
		zeroOrOne(
			terminal(COLON)
			nonTerminal(msg, Expression)
		)
		terminal(SEMICOLON)
		action({ return dress(SAssertStmt.make(check, optionOf(msg))); })
	) */
	protected BUTree<SAssertStmt> parseAssertStatement() throws ParseException {
		BUTree<? extends SExpr> check;
		BUTree<? extends SExpr> msg = null;
		run();
		parse(TokenType.ASSERT);
		pushCallStack(JavaGrammar.AssertStatement_2);
		check = parseExpression();
		popCallStack();
		if (match(0, TokenType.COLON) != -1) {
			parse(TokenType.COLON);
			pushCallStack(JavaGrammar.AssertStatement_3_2);
			msg = parseExpression();
			popCallStack();
		}
		parse(TokenType.SEMICOLON);
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
		run();
		pushCallStack(JavaGrammar.LabeledStatement_1);
		label = parseName();
		popCallStack();
		parse(TokenType.COLON);
		pushCallStack(JavaGrammar.LabeledStatement_3);
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
		run();
		parse(TokenType.LBRACE);
		pushCallStack(JavaGrammar.Block_2);
		stmts = parseStatements(false);
		popCallStack();
		parse(TokenType.RBRACE);
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
		switch (predict(JavaGrammar.BLOCK_STATEMENT_1)) {
			case 1:
				run();
				run();
				pushCallStack(JavaGrammar.BlockStatement_1_1_1);
				modifiers = parseModifiersNoDefault();
				popCallStack();
				pushCallStack(JavaGrammar.BlockStatement_1_1_2);
				typeDecl = parseClassOrInterfaceDecl(modifiers);
				popCallStack();
				ret = dress(STypeDeclarationStmt.make(typeDecl));
				break;
			case 2:
				run();
				pushCallStack(JavaGrammar.BlockStatement_1_2_1);
				expr = parseVariableDeclExpression();
				popCallStack();
				parse(TokenType.SEMICOLON);
				ret = dress(SExpressionStmt.make(expr));
				break;
			case 3:
				pushCallStack(JavaGrammar.BlockStatement_1_3);
				ret = parseStatement();
				popCallStack();
				break;
			default:
				throw produceParseException(TokenType.ABSTRACT, TokenType.ASSERT, TokenType.AT, TokenType.BANG, TokenType.BOOLEAN, TokenType.BREAK, TokenType.BYTE, TokenType.CHAR, TokenType.CHARACTER_LITERAL, TokenType.CLASS, TokenType.CONTINUE, TokenType.DECR, TokenType.DO, TokenType.DOUBLE, TokenType.DOUBLE_LITERAL, TokenType.FALSE, TokenType.FINAL, TokenType.FLOAT, TokenType.FLOAT_LITERAL, TokenType.FOR, TokenType.IDENTIFIER, TokenType.IF, TokenType.INCR, TokenType.INT, TokenType.INTEGER_LITERAL, TokenType.INTERFACE, TokenType.LBRACE, TokenType.LONG, TokenType.LONG_LITERAL, TokenType.LPAREN, TokenType.LT, TokenType.MINUS, TokenType.NATIVE, TokenType.NEW, TokenType.NODE_VARIABLE, TokenType.NULL, TokenType.PLUS, TokenType.PRIVATE, TokenType.PROTECTED, TokenType.PUBLIC, TokenType.RETURN, TokenType.SEMICOLON, TokenType.SHORT, TokenType.STATIC, TokenType.STRICTFP, TokenType.STRING_LITERAL, TokenType.SUPER, TokenType.SWITCH, TokenType.SYNCHRONIZED, TokenType.THIS, TokenType.THROW, TokenType.TILDE, TokenType.TRANSIENT, TokenType.TRUE, TokenType.TRY, TokenType.VOID, TokenType.VOLATILE, TokenType.WHILE);
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
		run();
		run();
		pushCallStack(JavaGrammar.VariableDeclExpression_1);
		modifiers = parseModifiersNoDefault();
		popCallStack();
		pushCallStack(JavaGrammar.VariableDeclExpression_2);
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
		run();
		parse(TokenType.SEMICOLON);
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
		run();
		pushCallStack(JavaGrammar.ExpressionStatement_1);
		expr = parseStatementExpression();
		popCallStack();
		parse(TokenType.SEMICOLON);
		return dress(SExpressionStmt.make(expr));
	}

	/* sequence(
		nonTerminal(ret, Expression)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parseStatementExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		pushCallStack(JavaGrammar.StatementExpression_1);
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
		run();
		parse(TokenType.SWITCH);
		parse(TokenType.LPAREN);
		pushCallStack(JavaGrammar.SwitchStatement_3);
		selector = parseExpression();
		popCallStack();
		parse(TokenType.RPAREN);
		parse(TokenType.LBRACE);
		while (match(0, TokenType.CASE, TokenType.DEFAULT) != -1) {
			pushCallStack(JavaGrammar.SwitchStatement_6_1);
			entry = parseSwitchEntry();
			popCallStack();
			entries = append(entries, entry);
		}
		parse(TokenType.RBRACE);
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
	protected BUTree<SSwitchCase> parseSwitchEntry() throws ParseException {
		BUTree<? extends SExpr> label = null;
		BUTree<SNodeList> stmts;
		run();
		switch (getToken(0).kind) {
			case TokenType.CASE:
				parse(TokenType.CASE);
				pushCallStack(JavaGrammar.SwitchEntry_1_1_2);
				label = parseExpression();
				popCallStack();
				break;
			case TokenType.DEFAULT:
				parse(TokenType.DEFAULT);
				break;
			default:
				throw produceParseException(TokenType.CASE, TokenType.DEFAULT);
		}
		parse(TokenType.COLON);
		pushCallStack(JavaGrammar.SwitchEntry_3);
		stmts = parseStatements(false);
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
			terminal(ELSE)
			nonTerminal(elseStmt, Statement)
		)
		action({ return dress(SIfStmt.make(condition, thenStmt, optionOf(elseStmt))); })
	) */
	protected BUTree<SIfStmt> parseIfStatement() throws ParseException {
		BUTree<? extends SExpr> condition;
		BUTree<? extends SStmt> thenStmt;
		BUTree<? extends SStmt> elseStmt = null;
		run();
		parse(TokenType.IF);
		parse(TokenType.LPAREN);
		pushCallStack(JavaGrammar.IfStatement_3);
		condition = parseExpression();
		popCallStack();
		parse(TokenType.RPAREN);
		pushCallStack(JavaGrammar.IfStatement_5);
		thenStmt = parseStatement();
		popCallStack();
		if (match(0, TokenType.ELSE) != -1) {
			parse(TokenType.ELSE);
			pushCallStack(JavaGrammar.IfStatement_6_2);
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
		run();
		parse(TokenType.WHILE);
		parse(TokenType.LPAREN);
		pushCallStack(JavaGrammar.WhileStatement_3);
		condition = parseExpression();
		popCallStack();
		parse(TokenType.RPAREN);
		pushCallStack(JavaGrammar.WhileStatement_5);
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
		run();
		parse(TokenType.DO);
		pushCallStack(JavaGrammar.DoStatement_2);
		body = parseStatement();
		popCallStack();
		parse(TokenType.WHILE);
		parse(TokenType.LPAREN);
		pushCallStack(JavaGrammar.DoStatement_5);
		condition = parseExpression();
		popCallStack();
		parse(TokenType.RPAREN);
		parse(TokenType.SEMICOLON);
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
		run();
		parse(TokenType.FOR);
		parse(TokenType.LPAREN);
		switch (predict(JavaGrammar.FOR_STATEMENT_3)) {
			case 1:
				pushCallStack(JavaGrammar.ForStatement_3_1_1);
				varExpr = parseVariableDeclExpression();
				popCallStack();
				parse(TokenType.COLON);
				pushCallStack(JavaGrammar.ForStatement_3_1_3);
				expr = parseExpression();
				popCallStack();
				break;
			case 2:
				if (match(0, TokenType.ABSTRACT, TokenType.AT, TokenType.BANG, TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.CHARACTER_LITERAL, TokenType.DECR, TokenType.DOUBLE, TokenType.DOUBLE_LITERAL, TokenType.FALSE, TokenType.FINAL, TokenType.FLOAT, TokenType.FLOAT_LITERAL, TokenType.IDENTIFIER, TokenType.INCR, TokenType.INT, TokenType.INTEGER_LITERAL, TokenType.LONG, TokenType.LONG_LITERAL, TokenType.LPAREN, TokenType.LT, TokenType.MINUS, TokenType.NATIVE, TokenType.NEW, TokenType.NODE_VARIABLE, TokenType.NULL, TokenType.PLUS, TokenType.PRIVATE, TokenType.PROTECTED, TokenType.PUBLIC, TokenType.SHORT, TokenType.STATIC, TokenType.STRICTFP, TokenType.STRING_LITERAL, TokenType.SUPER, TokenType.SYNCHRONIZED, TokenType.THIS, TokenType.TILDE, TokenType.TRANSIENT, TokenType.TRUE, TokenType.VOID, TokenType.VOLATILE) != -1) {
					pushCallStack(JavaGrammar.ForStatement_3_2_1_1);
					init = parseForInit();
					popCallStack();
				}
				parse(TokenType.SEMICOLON);
				if (match(0, TokenType.BANG, TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.CHARACTER_LITERAL, TokenType.DECR, TokenType.DOUBLE, TokenType.DOUBLE_LITERAL, TokenType.FALSE, TokenType.FLOAT, TokenType.FLOAT_LITERAL, TokenType.IDENTIFIER, TokenType.INCR, TokenType.INT, TokenType.INTEGER_LITERAL, TokenType.LONG, TokenType.LONG_LITERAL, TokenType.LPAREN, TokenType.LT, TokenType.MINUS, TokenType.NEW, TokenType.NODE_VARIABLE, TokenType.NULL, TokenType.PLUS, TokenType.SHORT, TokenType.STRING_LITERAL, TokenType.SUPER, TokenType.THIS, TokenType.TILDE, TokenType.TRUE, TokenType.VOID) != -1) {
					pushCallStack(JavaGrammar.ForStatement_3_2_3_1);
					expr = parseExpression();
					popCallStack();
				}
				parse(TokenType.SEMICOLON);
				if (predict(JavaGrammar.FOR_STATEMENT_3_2_5) == 1) {
					pushCallStack(JavaGrammar.ForStatement_3_2_5_1);
					update = parseForUpdate();
					popCallStack();
				}
				break;
			default:
				throw produceParseException(TokenType.ABSTRACT, TokenType.AT, TokenType.BANG, TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.CHARACTER_LITERAL, TokenType.DECR, TokenType.DOUBLE, TokenType.DOUBLE_LITERAL, TokenType.FALSE, TokenType.FINAL, TokenType.FLOAT, TokenType.FLOAT_LITERAL, TokenType.IDENTIFIER, TokenType.INCR, TokenType.INT, TokenType.INTEGER_LITERAL, TokenType.LONG, TokenType.LONG_LITERAL, TokenType.LPAREN, TokenType.LT, TokenType.MINUS, TokenType.NATIVE, TokenType.NEW, TokenType.NODE_VARIABLE, TokenType.NULL, TokenType.PLUS, TokenType.PRIVATE, TokenType.PROTECTED, TokenType.PUBLIC, TokenType.SEMICOLON, TokenType.SHORT, TokenType.STATIC, TokenType.STRICTFP, TokenType.STRING_LITERAL, TokenType.SUPER, TokenType.SYNCHRONIZED, TokenType.THIS, TokenType.TILDE, TokenType.TRANSIENT, TokenType.TRUE, TokenType.VOID, TokenType.VOLATILE);
		}
		parse(TokenType.RPAREN);
		pushCallStack(JavaGrammar.ForStatement_5);
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
				action({
					ret = emptyList();
					ret = append(ret, expr);
				})
			)
			nonTerminal(ret, StatementExpressionList)
		)
		action({ return ret; })
	) */
	protected BUTree<SNodeList> parseForInit() throws ParseException {
		BUTree<SNodeList> ret;
		BUTree<? extends SExpr> expr;
		switch (predict(JavaGrammar.FOR_INIT_1)) {
			case 1:
				pushCallStack(JavaGrammar.ForInit_1_1_1);
				expr = parseVariableDeclExpression();
				popCallStack();
				ret = emptyList();
				ret = append(ret, expr);
				break;
			case 2:
				pushCallStack(JavaGrammar.ForInit_1_2);
				ret = parseStatementExpressionList();
				popCallStack();
				break;
			default:
				throw produceParseException(TokenType.ABSTRACT, TokenType.AT, TokenType.BANG, TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.CHARACTER_LITERAL, TokenType.DECR, TokenType.DOUBLE, TokenType.DOUBLE_LITERAL, TokenType.FALSE, TokenType.FINAL, TokenType.FLOAT, TokenType.FLOAT_LITERAL, TokenType.IDENTIFIER, TokenType.INCR, TokenType.INT, TokenType.INTEGER_LITERAL, TokenType.LONG, TokenType.LONG_LITERAL, TokenType.LPAREN, TokenType.LT, TokenType.MINUS, TokenType.NATIVE, TokenType.NEW, TokenType.NODE_VARIABLE, TokenType.NULL, TokenType.PLUS, TokenType.PRIVATE, TokenType.PROTECTED, TokenType.PUBLIC, TokenType.SHORT, TokenType.STATIC, TokenType.STRICTFP, TokenType.STRING_LITERAL, TokenType.SUPER, TokenType.SYNCHRONIZED, TokenType.THIS, TokenType.TILDE, TokenType.TRANSIENT, TokenType.TRUE, TokenType.VOID, TokenType.VOLATILE);
		}
		return ret;
	}

	/* sequence(
		nonTerminal(expr, StatementExpression)
		action({ ret = append(ret, expr); })
		zeroOrMore(
			terminal(COMMA)
			nonTerminal(expr, StatementExpression)
			action({ ret = append(ret, expr); })
		)
		action({ return ret; })
	) */
	protected BUTree<SNodeList> parseStatementExpressionList() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<? extends SExpr> expr;
		pushCallStack(JavaGrammar.StatementExpressionList_1);
		expr = parseStatementExpression();
		popCallStack();
		ret = append(ret, expr);
		while (match(0, TokenType.COMMA) != -1) {
			parse(TokenType.COMMA);
			pushCallStack(JavaGrammar.StatementExpressionList_2_2);
			expr = parseStatementExpression();
			popCallStack();
			ret = append(ret, expr);
		}
		return ret;
	}

	/* sequence(
		nonTerminal(ret, StatementExpressionList)
		action({ return ret; })
	) */
	protected BUTree<SNodeList> parseForUpdate() throws ParseException {
		BUTree<SNodeList> ret;
		pushCallStack(JavaGrammar.ForUpdate_1);
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
		run();
		parse(TokenType.BREAK);
		if (match(0, TokenType.IDENTIFIER, TokenType.NODE_VARIABLE) != -1) {
			pushCallStack(JavaGrammar.BreakStatement_2_1);
			id = parseName();
			popCallStack();
		}
		parse(TokenType.SEMICOLON);
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
		run();
		parse(TokenType.CONTINUE);
		if (match(0, TokenType.IDENTIFIER, TokenType.NODE_VARIABLE) != -1) {
			pushCallStack(JavaGrammar.ContinueStatement_2_1);
			id = parseName();
			popCallStack();
		}
		parse(TokenType.SEMICOLON);
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
		run();
		parse(TokenType.RETURN);
		if (match(0, TokenType.BANG, TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.CHARACTER_LITERAL, TokenType.DECR, TokenType.DOUBLE, TokenType.DOUBLE_LITERAL, TokenType.FALSE, TokenType.FLOAT, TokenType.FLOAT_LITERAL, TokenType.IDENTIFIER, TokenType.INCR, TokenType.INT, TokenType.INTEGER_LITERAL, TokenType.LONG, TokenType.LONG_LITERAL, TokenType.LPAREN, TokenType.LT, TokenType.MINUS, TokenType.NEW, TokenType.NODE_VARIABLE, TokenType.NULL, TokenType.PLUS, TokenType.SHORT, TokenType.STRING_LITERAL, TokenType.SUPER, TokenType.THIS, TokenType.TILDE, TokenType.TRUE, TokenType.VOID) != -1) {
			pushCallStack(JavaGrammar.ReturnStatement_2_1);
			expr = parseExpression();
			popCallStack();
		}
		parse(TokenType.SEMICOLON);
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
		run();
		parse(TokenType.THROW);
		pushCallStack(JavaGrammar.ThrowStatement_2);
		expr = parseExpression();
		popCallStack();
		parse(TokenType.SEMICOLON);
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
		run();
		parse(TokenType.SYNCHRONIZED);
		parse(TokenType.LPAREN);
		pushCallStack(JavaGrammar.SynchronizedStatement_3);
		expr = parseExpression();
		popCallStack();
		parse(TokenType.RPAREN);
		pushCallStack(JavaGrammar.SynchronizedStatement_5);
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
					terminal(FINALLY)
					nonTerminal(finallyBlock, Block)
				)
			)
			sequence(
				nonTerminal(tryBlock, Block)
				choice(
					sequence(
						nonTerminal(catchClauses, CatchClauses)
						zeroOrOne(
							terminal(FINALLY)
							nonTerminal(finallyBlock, Block)
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
		run();
		parse(TokenType.TRY);
		switch (getToken(0).kind) {
			case TokenType.LPAREN:
				pushCallStack(JavaGrammar.TryStatement_2_1_1);
				resources = parseResourceSpecification(trailingSemiColon);
				popCallStack();
				pushCallStack(JavaGrammar.TryStatement_2_1_2);
				tryBlock = parseBlock();
				popCallStack();
				if (match(0, TokenType.CATCH) != -1) {
					pushCallStack(JavaGrammar.TryStatement_2_1_3_1);
					catchClauses = parseCatchClauses();
					popCallStack();
				}
				if (predict(JavaGrammar.TRY_STATEMENT_2_1_4) == 1) {
					parse(TokenType.FINALLY);
					pushCallStack(JavaGrammar.TryStatement_2_1_4_2);
					finallyBlock = parseBlock();
					popCallStack();
				}
				break;
			case TokenType.LBRACE:
				pushCallStack(JavaGrammar.TryStatement_2_2_1);
				tryBlock = parseBlock();
				popCallStack();
				switch (getToken(0).kind) {
					case TokenType.CATCH:
						pushCallStack(JavaGrammar.TryStatement_2_2_2_1_1);
						catchClauses = parseCatchClauses();
						popCallStack();
						if (predict(JavaGrammar.TRY_STATEMENT_2_2_2_1_2) == 1) {
							parse(TokenType.FINALLY);
							pushCallStack(JavaGrammar.TryStatement_2_2_2_1_2_2);
							finallyBlock = parseBlock();
							popCallStack();
						}
						break;
					case TokenType.FINALLY:
						parse(TokenType.FINALLY);
						pushCallStack(JavaGrammar.TryStatement_2_2_2_2_2);
						finallyBlock = parseBlock();
						popCallStack();
						break;
					default:
						throw produceParseException(TokenType.CATCH, TokenType.FINALLY);
				}
				break;
			default:
				throw produceParseException(TokenType.LBRACE, TokenType.LPAREN);
		}
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
		do {
			pushCallStack(JavaGrammar.CatchClauses_1_1);
			catchClause = parseCatchClause();
			popCallStack();
			catchClauses = append(catchClauses, catchClause);
		} while (match(0, TokenType.CATCH) != -1);
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
		run();
		parse(TokenType.CATCH);
		parse(TokenType.LPAREN);
		pushCallStack(JavaGrammar.CatchClause_3);
		param = parseCatchFormalParameter();
		popCallStack();
		parse(TokenType.RPAREN);
		pushCallStack(JavaGrammar.CatchClause_5);
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
				terminal(BIT_OR)
				nonTerminal(exceptType, AnnotatedQualifiedType)
				action({ exceptTypes = append(exceptTypes, exceptType); })
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
		run();
		pushCallStack(JavaGrammar.CatchFormalParameter_1);
		modifiers = parseModifiers();
		popCallStack();
		pushCallStack(JavaGrammar.CatchFormalParameter_2);
		exceptType = parseQualifiedType(null);
		popCallStack();
		exceptTypes = append(exceptTypes, exceptType);
		if (match(0, TokenType.BIT_OR) != -1) {
			lateRun();
			do {
				parse(TokenType.BIT_OR);
				pushCallStack(JavaGrammar.CatchFormalParameter_3_1_2);
				exceptType = parseAnnotatedQualifiedType();
				popCallStack();
				exceptTypes = append(exceptTypes, exceptType);
			} while (match(0, TokenType.BIT_OR) != -1);
			exceptType = dress(SUnionType.make(exceptTypes));
		}
		pushCallStack(JavaGrammar.CatchFormalParameter_4);
		exceptId = parseVariableDeclaratorId();
		popCallStack();
		return dress(SFormalParameter.make(modifiers, exceptType, false, emptyList(), optionOf(exceptId), false, none()));
	}

	/* sequence(
		terminal(LPAREN)
		nonTerminal(var, VariableDeclExpression)
		action({ vars = append(vars, var); })
		zeroOrMore(
			terminal(SEMICOLON)
			nonTerminal(var, VariableDeclExpression)
			action({ vars = append(vars, var); })
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
		parse(TokenType.LPAREN);
		pushCallStack(JavaGrammar.ResourceSpecification_2);
		var = parseVariableDeclExpression();
		popCallStack();
		vars = append(vars, var);
		while (predict(JavaGrammar.RESOURCE_SPECIFICATION_3) == 1) {
			parse(TokenType.SEMICOLON);
			pushCallStack(JavaGrammar.ResourceSpecification_3_2);
			var = parseVariableDeclExpression();
			popCallStack();
			vars = append(vars, var);
		}
		if (match(0, TokenType.SEMICOLON) != -1) {
			parse(TokenType.SEMICOLON);
			trailingSemiColon.value = true;
		}
		parse(TokenType.RPAREN);
		return vars;
	}

	/* sequence(
		terminal(GT)
		terminal(GT)
		terminal(GT)
		action({ popNewWhitespaces(2); })
	) */
	protected void parseRUNSIGNEDSHIFT() throws ParseException {
		parse(TokenType.GT);
		parse(TokenType.GT);
		parse(TokenType.GT);
		popNewWhitespaces(2);
	}

	/* sequence(
		terminal(GT)
		terminal(GT)
		action({ popNewWhitespaces(1); })
	) */
	protected void parseRSIGNEDSHIFT() throws ParseException {
		parse(TokenType.GT);
		parse(TokenType.GT);
		popNewWhitespaces(1);
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
		while (match(0, TokenType.AT) != -1) {
			pushCallStack(JavaGrammar.Annotations_1_1);
			annotation = parseAnnotation();
			popCallStack();
			annotations = append(annotations, annotation);
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
		switch (predict(JavaGrammar.ANNOTATION_1)) {
			case 1:
				pushCallStack(JavaGrammar.Annotation_1_1);
				ret = parseNormalAnnotation();
				popCallStack();
				break;
			case 2:
				pushCallStack(JavaGrammar.Annotation_1_2);
				ret = parseMarkerAnnotation();
				popCallStack();
				break;
			case 3:
				pushCallStack(JavaGrammar.Annotation_1_3);
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
		run();
		parse(TokenType.AT);
		pushCallStack(JavaGrammar.NormalAnnotation_2);
		name = parseQualifiedName();
		popCallStack();
		parse(TokenType.LPAREN);
		if (match(0, TokenType.IDENTIFIER, TokenType.NODE_VARIABLE) != -1) {
			pushCallStack(JavaGrammar.NormalAnnotation_4_1);
			pairs = parseElementValuePairList();
			popCallStack();
		}
		parse(TokenType.RPAREN);
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
		run();
		parse(TokenType.AT);
		pushCallStack(JavaGrammar.MarkerAnnotation_2);
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
		run();
		parse(TokenType.AT);
		pushCallStack(JavaGrammar.SingleElementAnnotation_2);
		name = parseQualifiedName();
		popCallStack();
		parse(TokenType.LPAREN);
		pushCallStack(JavaGrammar.SingleElementAnnotation_4);
		value = parseElementValue();
		popCallStack();
		parse(TokenType.RPAREN);
		return dress(SSingleMemberAnnotationExpr.make(name, value));
	}

	/* sequence(
		nonTerminal(pair, ElementValuePair)
		action({ ret = append(ret, pair); })
		zeroOrMore(
			terminal(COMMA)
			nonTerminal(pair, ElementValuePair)
			action({ ret = append(ret, pair); })
		)
		action({ return ret; })
	) */
	protected BUTree<SNodeList> parseElementValuePairList() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<SMemberValuePair> pair;
		pushCallStack(JavaGrammar.ElementValuePairList_1);
		pair = parseElementValuePair();
		popCallStack();
		ret = append(ret, pair);
		while (match(0, TokenType.COMMA) != -1) {
			parse(TokenType.COMMA);
			pushCallStack(JavaGrammar.ElementValuePairList_2_2);
			pair = parseElementValuePair();
			popCallStack();
			ret = append(ret, pair);
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
		run();
		pushCallStack(JavaGrammar.ElementValuePair_1);
		name = parseName();
		popCallStack();
		parse(TokenType.ASSIGN);
		pushCallStack(JavaGrammar.ElementValuePair_3);
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
		switch (getToken(0).kind) {
			case TokenType.BANG:
			case TokenType.BOOLEAN:
			case TokenType.BYTE:
			case TokenType.CHAR:
			case TokenType.CHARACTER_LITERAL:
			case TokenType.DECR:
			case TokenType.DOUBLE:
			case TokenType.DOUBLE_LITERAL:
			case TokenType.FALSE:
			case TokenType.FLOAT:
			case TokenType.FLOAT_LITERAL:
			case TokenType.IDENTIFIER:
			case TokenType.INCR:
			case TokenType.INT:
			case TokenType.INTEGER_LITERAL:
			case TokenType.LONG:
			case TokenType.LONG_LITERAL:
			case TokenType.LPAREN:
			case TokenType.LT:
			case TokenType.MINUS:
			case TokenType.NEW:
			case TokenType.NODE_VARIABLE:
			case TokenType.NULL:
			case TokenType.PLUS:
			case TokenType.SHORT:
			case TokenType.STRING_LITERAL:
			case TokenType.SUPER:
			case TokenType.THIS:
			case TokenType.TILDE:
			case TokenType.TRUE:
			case TokenType.VOID:
				pushCallStack(JavaGrammar.ElementValue_1_1);
				ret = parseConditionalExpression();
				popCallStack();
				break;
			case TokenType.LBRACE:
				pushCallStack(JavaGrammar.ElementValue_1_2);
				ret = parseElementValueArrayInitializer();
				popCallStack();
				break;
			case TokenType.AT:
				pushCallStack(JavaGrammar.ElementValue_1_3);
				ret = parseAnnotation();
				popCallStack();
				break;
			default:
				throw produceParseException(TokenType.AT, TokenType.BANG, TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.CHARACTER_LITERAL, TokenType.DECR, TokenType.DOUBLE, TokenType.DOUBLE_LITERAL, TokenType.FALSE, TokenType.FLOAT, TokenType.FLOAT_LITERAL, TokenType.IDENTIFIER, TokenType.INCR, TokenType.INT, TokenType.INTEGER_LITERAL, TokenType.LBRACE, TokenType.LONG, TokenType.LONG_LITERAL, TokenType.LPAREN, TokenType.LT, TokenType.MINUS, TokenType.NEW, TokenType.NODE_VARIABLE, TokenType.NULL, TokenType.PLUS, TokenType.SHORT, TokenType.STRING_LITERAL, TokenType.SUPER, TokenType.THIS, TokenType.TILDE, TokenType.TRUE, TokenType.VOID);
		}
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
		run();
		parse(TokenType.LBRACE);
		if (match(0, TokenType.AT, TokenType.BANG, TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.CHARACTER_LITERAL, TokenType.DECR, TokenType.DOUBLE, TokenType.DOUBLE_LITERAL, TokenType.FALSE, TokenType.FLOAT, TokenType.FLOAT_LITERAL, TokenType.IDENTIFIER, TokenType.INCR, TokenType.INT, TokenType.INTEGER_LITERAL, TokenType.LBRACE, TokenType.LONG, TokenType.LONG_LITERAL, TokenType.LPAREN, TokenType.LT, TokenType.MINUS, TokenType.NEW, TokenType.NODE_VARIABLE, TokenType.NULL, TokenType.PLUS, TokenType.SHORT, TokenType.STRING_LITERAL, TokenType.SUPER, TokenType.THIS, TokenType.TILDE, TokenType.TRUE, TokenType.VOID) != -1) {
			pushCallStack(JavaGrammar.ElementValueArrayInitializer_2_1);
			values = parseElementValueList();
			popCallStack();
		}
		if (match(0, TokenType.COMMA) != -1) {
			parse(TokenType.COMMA);
			trailingComma = true;
		}
		parse(TokenType.RBRACE);
		return dress(SArrayInitializerExpr.make(ensureNotNull(values), trailingComma));
	}

	/* sequence(
		nonTerminal(value, ElementValue)
		action({ ret = append(ret, value); })
		zeroOrMore(
			terminal(COMMA)
			nonTerminal(value, ElementValue)
			action({ ret = append(ret, value); })
		)
		action({ return ret; })
	) */
	protected BUTree<SNodeList> parseElementValueList() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<? extends SExpr> value;
		pushCallStack(JavaGrammar.ElementValueList_1);
		value = parseElementValue();
		popCallStack();
		ret = append(ret, value);
		while (predict(JavaGrammar.ELEMENT_VALUE_LIST_2) == 1) {
			parse(TokenType.COMMA);
			pushCallStack(JavaGrammar.ElementValueList_2_2);
			value = parseElementValue();
			popCallStack();
			ret = append(ret, value);
		}
		return ret;
	}
}
