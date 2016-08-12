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

package org.jlato.tree;

import org.jlato.internal.bu.Literals;
import org.jlato.internal.td.coll.*;
import org.jlato.internal.td.decl.*;
import org.jlato.internal.td.expr.*;
import org.jlato.internal.td.name.*;
import org.jlato.internal.td.stmt.*;
import org.jlato.internal.td.type.*;
import org.jlato.tree.decl.*;
import org.jlato.tree.expr.*;
import org.jlato.tree.name.Name;
import org.jlato.tree.name.QualifiedName;
import org.jlato.tree.stmt.*;
import org.jlato.tree.type.*;

/**
 * A factory for tree nodes.
 */
public final class Trees {

	private Trees() {
	}

	public static <T extends Tree> NodeOption<T> none() {
		return TDNodeOption.none();
	}

	public static <T extends Tree> NodeOption<T> some(T t) {
		return TDNodeOption.some(t);
	}

	public static <T extends Tree> NodeOption<T> optionOf(T t) {
		return TDNodeOption.of(t);
	}

	public static <TL extends Tree, TR extends Tree> NodeEither<TL, TR> left(TL t) {
		return TDNodeEither.<TL, TR>left(t);
	}

	public static <TL extends Tree, TR extends Tree> NodeEither<TL, TR> right(TR t) {
		return TDNodeEither.<TL, TR>right(t);
	}

	public static <T extends Tree> NodeList<T> emptyList() {
		return TDNodeList.empty();
	}

	public static <T extends Tree> NodeList<T> listOf(Iterable<T> ts) {
		return TDNodeList.of(ts);
	}

	public static <T extends Tree> NodeList<T> listOf(T t1) {
		return TDNodeList.of(t1);
	}

	public static <T extends Tree> NodeList<T> listOf(T t1, T t2) {
		return TDNodeList.of(t1, t2);
	}

	public static <T extends Tree> NodeList<T> listOf(T t1, T t2, T t3) {
		return TDNodeList.of(t1, t2, t3);
	}

	public static <T extends Tree> NodeList<T> listOf(T t1, T t2, T t3, T t4) {
		return TDNodeList.of(t1, t2, t3, t4);
	}

	public static <T extends Tree> NodeList<T> listOf(T t1, T t2, T t3, T t4, T t5) {
		return TDNodeList.of(t1, t2, t3, t4, t5);
	}

	public static <T extends Tree> NodeList<T> listOf(T t1, T t2, T t3, T t4, T t5, T t6) {
		return TDNodeList.of(t1, t2, t3, t4, t5, t6);
	}

	public static <T extends Tree> NodeList<T> listOf(T t1, T t2, T t3, T t4, T t5, T t6, T t7) {
		return TDNodeList.of(t1, t2, t3, t4, t5, t6, t7);
	}

	public static <T extends Tree> NodeList<T> listOf(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8) {
		return TDNodeList.of(t1, t2, t3, t4, t5, t6, t7, t8);
	}

	public static <T extends Tree> NodeList<T> listOf(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9) {
		return TDNodeList.of(t1, t2, t3, t4, t5, t6, t7, t8, t9);
	}

	public static <T extends Tree> NodeList<T> listOf(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10) {
		return TDNodeList.of(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
	}

	public static <T extends Tree> NodeList<T> listOf(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10, T t11) {
		return TDNodeList.of(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11);
	}

	public static <T extends Tree> NodeList<T> listOf(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10, T t11, T t12) {
		return TDNodeList.of(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
	}

	public static <T extends Tree> NodeList<T> listOf(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10, T t11, T t12, T t13) {
		return TDNodeList.of(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13);
	}

	public static <T extends Tree> NodeList<T> listOf(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10, T t11, T t12, T t13, T t14) {
		return TDNodeList.of(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14);
	}

	public static <T extends Tree> NodeList<T> listOf(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10, T t11, T t12, T t13, T t14, T t15) {
		return TDNodeList.of(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
	}

	public static <T extends Tree> NodeList<T> listOf(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10, T t11, T t12, T t13, T t14, T t15, T t16) {
		return TDNodeList.of(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16);
	}

	public static <T extends Tree> NodeList<T> listOf(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10, T t11, T t12, T t13, T t14, T t15, T t16, T t17) {
		return TDNodeList.of(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17);
	}

	public static <T extends Tree> NodeList<T> listOf(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10, T t11, T t12, T t13, T t14, T t15, T t16, T t17, T t18) {
		return TDNodeList.of(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18);
	}

	public static <T extends Tree> NodeList<T> listOf(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10, T t11, T t12, T t13, T t14, T t15, T t16, T t17, T t18, T t19) {
		return TDNodeList.of(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19);
	}

	public static <T extends Tree> NodeList<T> listOf(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10, T t11, T t12, T t13, T t14, T t15, T t16, T t17, T t18, T t19, T t20) {
		return TDNodeList.of(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20);
	}

	public static <T extends Tree> NodeList<T> listOf(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10, T t11, T t12, T t13, T t14, T t15, T t16, T t17, T t18, T t19, T t20, T t21) {
		return TDNodeList.of(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21);
	}

	public static <T extends Tree> NodeList<T> listOf(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10, T t11, T t12, T t13, T t14, T t15, T t16, T t17, T t18, T t19, T t20, T t21, T t22) {
		return TDNodeList.of(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22);
	}

	public static <T extends Tree> NodeList<T> listOf(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10, T t11, T t12, T t13, T t14, T t15, T t16, T t17, T t18, T t19, T t20, T t21, T t22, T t23) {
		return TDNodeList.of(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22, t23);
	}

	public static <T extends Tree> NodeMap<T> emptyMap() {
		return TDNodeMap.empty();
	}

	/**
	 * Creates an annotation type declaration.
	 *
	 * @return the new annotation type declaration instance.
	 */
	public static AnnotationDecl annotationDecl() {
		return new TDAnnotationDecl(Trees.<ExtendedModifier>emptyList(), null, Trees.<MemberDecl>emptyList());
	}

	/**
	 * Creates an annotation type declaration.
	 *
	 * @param name the name child tree.
	 * @return the new annotation type declaration instance.
	 */
	public static AnnotationDecl annotationDecl(Name name) {
		return new TDAnnotationDecl(Trees.<ExtendedModifier>emptyList(), name, Trees.<MemberDecl>emptyList());
	}

	/**
	 * Creates an annotation type member declaration.
	 *
	 * @return the new annotation type member declaration instance.
	 */
	public static AnnotationMemberDecl annotationMemberDecl() {
		return new TDAnnotationMemberDecl(Trees.<ExtendedModifier>emptyList(), null, null, Trees.<ArrayDim>emptyList(), Trees.<Expr>none());
	}

	/**
	 * Creates an annotation type member declaration.
	 *
	 * @param type the type child tree.
	 * @param name the name child tree.
	 * @return the new annotation type member declaration instance.
	 */
	public static AnnotationMemberDecl annotationMemberDecl(Type type, Name name) {
		return new TDAnnotationMemberDecl(Trees.<ExtendedModifier>emptyList(), type, name, Trees.<ArrayDim>emptyList(), Trees.<Expr>none());
	}

	/**
	 * Creates an array dimension.
	 *
	 * @return the new array dimension instance.
	 */
	public static ArrayDim arrayDim() {
		return new TDArrayDim(Trees.<AnnotationExpr>emptyList());
	}

	/**
	 * Creates a class declaration.
	 *
	 * @return the new class declaration instance.
	 */
	public static ClassDecl classDecl() {
		return new TDClassDecl(Trees.<ExtendedModifier>emptyList(), null, Trees.<TypeParameter>emptyList(), Trees.<QualifiedType>none(), Trees.<QualifiedType>emptyList(), Trees.<MemberDecl>emptyList());
	}

	/**
	 * Creates a class declaration.
	 *
	 * @param name the name child tree.
	 * @return the new class declaration instance.
	 */
	public static ClassDecl classDecl(Name name) {
		return new TDClassDecl(Trees.<ExtendedModifier>emptyList(), name, Trees.<TypeParameter>emptyList(), Trees.<QualifiedType>none(), Trees.<QualifiedType>emptyList(), Trees.<MemberDecl>emptyList());
	}

	/**
	 * Creates a compilation unit.
	 *
	 * @return the new compilation unit instance.
	 */
	public static CompilationUnit compilationUnit() {
		return new TDCompilationUnit(null, Trees.<ImportDecl>emptyList(), Trees.<TypeDecl>emptyList());
	}

	/**
	 * Creates a compilation unit.
	 *
	 * @param packageDecl the package declaration child tree.
	 * @return the new compilation unit instance.
	 */
	public static CompilationUnit compilationUnit(PackageDecl packageDecl) {
		return new TDCompilationUnit(packageDecl, Trees.<ImportDecl>emptyList(), Trees.<TypeDecl>emptyList());
	}

	/**
	 * Creates a constructor declaration.
	 *
	 * @return the new constructor declaration instance.
	 */
	public static ConstructorDecl constructorDecl() {
		return new TDConstructorDecl(Trees.<ExtendedModifier>emptyList(), Trees.<TypeParameter>emptyList(), null, Trees.<FormalParameter>emptyList(), Trees.<QualifiedType>emptyList(), Trees.blockStmt());
	}

	/**
	 * Creates a constructor declaration.
	 *
	 * @param name the name child tree.
	 * @return the new constructor declaration instance.
	 */
	public static ConstructorDecl constructorDecl(Name name) {
		return new TDConstructorDecl(Trees.<ExtendedModifier>emptyList(), Trees.<TypeParameter>emptyList(), name, Trees.<FormalParameter>emptyList(), Trees.<QualifiedType>emptyList(), Trees.blockStmt());
	}

	/**
	 * Creates an empty member declaration.
	 *
	 * @return the new empty member declaration instance.
	 */
	public static EmptyMemberDecl emptyMemberDecl() {
		return new TDEmptyMemberDecl();
	}

	/**
	 * Creates an empty type declaration.
	 *
	 * @return the new empty type declaration instance.
	 */
	public static EmptyTypeDecl emptyTypeDecl() {
		return new TDEmptyTypeDecl();
	}

	/**
	 * Creates an enum constant declaration.
	 *
	 * @return the new enum constant declaration instance.
	 */
	public static EnumConstantDecl enumConstantDecl() {
		return new TDEnumConstantDecl(Trees.<ExtendedModifier>emptyList(), null, Trees.<NodeList<Expr>>none(), Trees.<NodeList<MemberDecl>>none());
	}

	/**
	 * Creates an enum constant declaration.
	 *
	 * @param name the name child tree.
	 * @return the new enum constant declaration instance.
	 */
	public static EnumConstantDecl enumConstantDecl(Name name) {
		return new TDEnumConstantDecl(Trees.<ExtendedModifier>emptyList(), name, Trees.<NodeList<Expr>>none(), Trees.<NodeList<MemberDecl>>none());
	}

	/**
	 * Creates an enum declaration.
	 *
	 * @return the new enum declaration instance.
	 */
	public static EnumDecl enumDecl() {
		return new TDEnumDecl(Trees.<ExtendedModifier>emptyList(), null, Trees.<QualifiedType>emptyList(), Trees.<EnumConstantDecl>emptyList(), false, Trees.<MemberDecl>emptyList());
	}

	/**
	 * Creates an enum declaration.
	 *
	 * @param name the name child tree.
	 * @return the new enum declaration instance.
	 */
	public static EnumDecl enumDecl(Name name) {
		return new TDEnumDecl(Trees.<ExtendedModifier>emptyList(), name, Trees.<QualifiedType>emptyList(), Trees.<EnumConstantDecl>emptyList(), false, Trees.<MemberDecl>emptyList());
	}

	/**
	 * Creates a field declaration.
	 *
	 * @return the new field declaration instance.
	 */
	public static FieldDecl fieldDecl() {
		return new TDFieldDecl(Trees.<ExtendedModifier>emptyList(), null, Trees.<VariableDeclarator>emptyList());
	}

	/**
	 * Creates a field declaration.
	 *
	 * @param type the type child tree.
	 * @return the new field declaration instance.
	 */
	public static FieldDecl fieldDecl(Type type) {
		return new TDFieldDecl(Trees.<ExtendedModifier>emptyList(), type, Trees.<VariableDeclarator>emptyList());
	}

	/**
	 * Creates a formal parameter.
	 *
	 * @return the new formal parameter instance.
	 */
	public static FormalParameter formalParameter() {
		return new TDFormalParameter(Trees.<ExtendedModifier>emptyList(), null, false, Trees.<AnnotationExpr>emptyList(), Trees.<VariableDeclaratorId>none(), false, Trees.<Name>none());
	}

	/**
	 * Creates a formal parameter.
	 *
	 * @param type the type child tree.
	 * @return the new formal parameter instance.
	 */
	public static FormalParameter formalParameter(Type type) {
		return new TDFormalParameter(Trees.<ExtendedModifier>emptyList(), type, false, Trees.<AnnotationExpr>emptyList(), Trees.<VariableDeclaratorId>none(), false, Trees.<Name>none());
	}

	/**
	 * Creates an import declaration.
	 *
	 * @return the new import declaration instance.
	 */
	public static ImportDecl importDecl() {
		return new TDImportDecl(null, false, false);
	}

	/**
	 * Creates an import declaration.
	 *
	 * @param name the name child tree.
	 * @return the new import declaration instance.
	 */
	public static ImportDecl importDecl(QualifiedName name) {
		return new TDImportDecl(name, false, false);
	}

	/**
	 * Creates an initializer declaration.
	 *
	 * @return the new initializer declaration instance.
	 */
	public static InitializerDecl initializerDecl() {
		return new TDInitializerDecl(Trees.<ExtendedModifier>emptyList(), null);
	}

	/**
	 * Creates an initializer declaration.
	 *
	 * @param body the body child tree.
	 * @return the new initializer declaration instance.
	 */
	public static InitializerDecl initializerDecl(BlockStmt body) {
		return new TDInitializerDecl(Trees.<ExtendedModifier>emptyList(), body);
	}

	/**
	 * Creates an interface declaration.
	 *
	 * @return the new interface declaration instance.
	 */
	public static InterfaceDecl interfaceDecl() {
		return new TDInterfaceDecl(Trees.<ExtendedModifier>emptyList(), null, Trees.<TypeParameter>emptyList(), Trees.<QualifiedType>emptyList(), Trees.<MemberDecl>emptyList());
	}

	/**
	 * Creates an interface declaration.
	 *
	 * @param name the name child tree.
	 * @return the new interface declaration instance.
	 */
	public static InterfaceDecl interfaceDecl(Name name) {
		return new TDInterfaceDecl(Trees.<ExtendedModifier>emptyList(), name, Trees.<TypeParameter>emptyList(), Trees.<QualifiedType>emptyList(), Trees.<MemberDecl>emptyList());
	}

	/**
	 * Creates a local variable declaration.
	 *
	 * @return the new local variable declaration instance.
	 */
	public static LocalVariableDecl localVariableDecl() {
		return new TDLocalVariableDecl(Trees.<ExtendedModifier>emptyList(), null, Trees.<VariableDeclarator>emptyList());
	}

	/**
	 * Creates a local variable declaration.
	 *
	 * @param type the type child tree.
	 * @return the new local variable declaration instance.
	 */
	public static LocalVariableDecl localVariableDecl(Type type) {
		return new TDLocalVariableDecl(Trees.<ExtendedModifier>emptyList(), type, Trees.<VariableDeclarator>emptyList());
	}

	/**
	 * Creates a method declaration.
	 *
	 * @return the new method declaration instance.
	 */
	public static MethodDecl methodDecl() {
		return new TDMethodDecl(Trees.<ExtendedModifier>emptyList(), Trees.<TypeParameter>emptyList(), Trees.<AnnotationExpr>emptyList(), null, null, Trees.<FormalParameter>emptyList(), Trees.<ArrayDim>emptyList(), Trees.<QualifiedType>emptyList(), Trees.<BlockStmt>none());
	}

	/**
	 * Creates a method declaration.
	 *
	 * @param type the type child tree.
	 * @param name the name child tree.
	 * @return the new method declaration instance.
	 */
	public static MethodDecl methodDecl(Type type, Name name) {
		return new TDMethodDecl(Trees.<ExtendedModifier>emptyList(), Trees.<TypeParameter>emptyList(), Trees.<AnnotationExpr>emptyList(), type, name, Trees.<FormalParameter>emptyList(), Trees.<ArrayDim>emptyList(), Trees.<QualifiedType>emptyList(), Trees.<BlockStmt>none());
	}

	/**
	 * Creates a modifier.
	 *
	 * @return the new modifier instance.
	 */
	public static Modifier modifier() {
		return new TDModifier((ModifierKeyword) null);
	}

	/**
	 * Creates a modifier.
	 *
	 * @param keyword the keyword child tree.
	 * @return the new modifier instance.
	 */
	public static Modifier modifier(ModifierKeyword keyword) {
		return new TDModifier(keyword);
	}

	/**
	 * Creates a package declaration.
	 *
	 * @return the new package declaration instance.
	 */
	public static PackageDecl packageDecl() {
		return new TDPackageDecl(Trees.<AnnotationExpr>emptyList(), null);
	}

	/**
	 * Creates a package declaration.
	 *
	 * @param name the name child tree.
	 * @return the new package declaration instance.
	 */
	public static PackageDecl packageDecl(QualifiedName name) {
		return new TDPackageDecl(Trees.<AnnotationExpr>emptyList(), name);
	}

	/**
	 * Creates a type parameter.
	 *
	 * @return the new type parameter instance.
	 */
	public static TypeParameter typeParameter() {
		return new TDTypeParameter(Trees.<AnnotationExpr>emptyList(), null, Trees.<Type>emptyList());
	}

	/**
	 * Creates a type parameter.
	 *
	 * @param name the name child tree.
	 * @return the new type parameter instance.
	 */
	public static TypeParameter typeParameter(Name name) {
		return new TDTypeParameter(Trees.<AnnotationExpr>emptyList(), name, Trees.<Type>emptyList());
	}

	/**
	 * Creates a variable declarator.
	 *
	 * @return the new variable declarator instance.
	 */
	public static VariableDeclarator variableDeclarator() {
		return new TDVariableDeclarator(null, Trees.<Expr>none());
	}

	/**
	 * Creates a variable declarator.
	 *
	 * @param id the identifier child tree.
	 * @return the new variable declarator instance.
	 */
	public static VariableDeclarator variableDeclarator(VariableDeclaratorId id) {
		return new TDVariableDeclarator(id, Trees.<Expr>none());
	}

	/**
	 * Creates a variable declarator identifier.
	 *
	 * @return the new variable declarator identifier instance.
	 */
	public static VariableDeclaratorId variableDeclaratorId() {
		return new TDVariableDeclaratorId(null, Trees.<ArrayDim>emptyList());
	}

	/**
	 * Creates a variable declarator identifier.
	 *
	 * @param name the name child tree.
	 * @return the new variable declarator identifier instance.
	 */
	public static VariableDeclaratorId variableDeclaratorId(Name name) {
		return new TDVariableDeclaratorId(name, Trees.<ArrayDim>emptyList());
	}

	/**
	 * Creates an array access expression.
	 *
	 * @return the new array access expression instance.
	 */
	public static ArrayAccessExpr arrayAccessExpr() {
		return new TDArrayAccessExpr(null, null);
	}

	/**
	 * Creates an array access expression.
	 *
	 * @param name  the name child tree.
	 * @param index the index child tree.
	 * @return the new array access expression instance.
	 */
	public static ArrayAccessExpr arrayAccessExpr(Expr name, Expr index) {
		return new TDArrayAccessExpr(name, index);
	}

	/**
	 * Creates an array creation expression.
	 *
	 * @return the new array creation expression instance.
	 */
	public static ArrayCreationExpr arrayCreationExpr() {
		return new TDArrayCreationExpr(null, Trees.<ArrayDimExpr>emptyList(), Trees.<ArrayDim>emptyList(), Trees.<ArrayInitializerExpr>none());
	}

	/**
	 * Creates an array creation expression.
	 *
	 * @param type the type child tree.
	 * @return the new array creation expression instance.
	 */
	public static ArrayCreationExpr arrayCreationExpr(Type type) {
		return new TDArrayCreationExpr(type, Trees.<ArrayDimExpr>emptyList(), Trees.<ArrayDim>emptyList(), Trees.<ArrayInitializerExpr>none());
	}

	/**
	 * Creates an array dimension expression.
	 *
	 * @return the new array dimension expression instance.
	 */
	public static ArrayDimExpr arrayDimExpr() {
		return new TDArrayDimExpr(Trees.<AnnotationExpr>emptyList(), null);
	}

	/**
	 * Creates an array dimension expression.
	 *
	 * @param expr the expression child tree.
	 * @return the new array dimension expression instance.
	 */
	public static ArrayDimExpr arrayDimExpr(Expr expr) {
		return new TDArrayDimExpr(Trees.<AnnotationExpr>emptyList(), expr);
	}

	/**
	 * Creates an array initializer expression.
	 *
	 * @return the new array initializer expression instance.
	 */
	public static ArrayInitializerExpr arrayInitializerExpr() {
		return new TDArrayInitializerExpr(Trees.<Expr>emptyList(), false);
	}

	/**
	 * Creates an assignment expression.
	 *
	 * @return the new assignment expression instance.
	 */
	public static AssignExpr assignExpr() {
		return new TDAssignExpr(null, null, null);
	}

	/**
	 * Creates an assignment expression.
	 *
	 * @param target the target child tree.
	 * @param op     the op child tree.
	 * @param value  the value child tree.
	 * @return the new assignment expression instance.
	 */
	public static AssignExpr assignExpr(Expr target, AssignOp op, Expr value) {
		return new TDAssignExpr(target, op, value);
	}

	/**
	 * Creates a binary expression.
	 *
	 * @return the new binary expression instance.
	 */
	public static BinaryExpr binaryExpr() {
		return new TDBinaryExpr(null, null, null);
	}

	/**
	 * Creates a binary expression.
	 *
	 * @param left  the left child tree.
	 * @param op    the op child tree.
	 * @param right the right child tree.
	 * @return the new binary expression instance.
	 */
	public static BinaryExpr binaryExpr(Expr left, BinaryOp op, Expr right) {
		return new TDBinaryExpr(left, op, right);
	}

	/**
	 * Creates a cast expression.
	 *
	 * @return the new cast expression instance.
	 */
	public static CastExpr castExpr() {
		return new TDCastExpr(null, null);
	}

	/**
	 * Creates a cast expression.
	 *
	 * @param type the type child tree.
	 * @param expr the expression child tree.
	 * @return the new cast expression instance.
	 */
	public static CastExpr castExpr(Type type, Expr expr) {
		return new TDCastExpr(type, expr);
	}

	/**
	 * Creates a 'class' expression.
	 *
	 * @return the new 'class' expression instance.
	 */
	public static ClassExpr classExpr() {
		return new TDClassExpr((Type) null);
	}

	/**
	 * Creates a 'class' expression.
	 *
	 * @param type the type child tree.
	 * @return the new 'class' expression instance.
	 */
	public static ClassExpr classExpr(Type type) {
		return new TDClassExpr(type);
	}

	/**
	 * Creates a conditional expression.
	 *
	 * @return the new conditional expression instance.
	 */
	public static ConditionalExpr conditionalExpr() {
		return new TDConditionalExpr(null, null, null);
	}

	/**
	 * Creates a conditional expression.
	 *
	 * @param condition the condition child tree.
	 * @param thenExpr  the then expression child tree.
	 * @param elseExpr  the else expression child tree.
	 * @return the new conditional expression instance.
	 */
	public static ConditionalExpr conditionalExpr(Expr condition, Expr thenExpr, Expr elseExpr) {
		return new TDConditionalExpr(condition, thenExpr, elseExpr);
	}

	/**
	 * Creates a field access expression.
	 *
	 * @return the new field access expression instance.
	 */
	public static FieldAccessExpr fieldAccessExpr() {
		return new TDFieldAccessExpr(Trees.<Expr>none(), null);
	}

	/**
	 * Creates a field access expression.
	 *
	 * @param name the name child tree.
	 * @return the new field access expression instance.
	 */
	public static FieldAccessExpr fieldAccessExpr(Name name) {
		return new TDFieldAccessExpr(Trees.<Expr>none(), name);
	}

	/**
	 * Creates an 'instanceof' expression.
	 *
	 * @return the new 'instanceof' expression instance.
	 */
	public static InstanceOfExpr instanceOfExpr() {
		return new TDInstanceOfExpr(null, null);
	}

	/**
	 * Creates an 'instanceof' expression.
	 *
	 * @param expr the expression child tree.
	 * @param type the type child tree.
	 * @return the new 'instanceof' expression instance.
	 */
	public static InstanceOfExpr instanceOfExpr(Expr expr, Type type) {
		return new TDInstanceOfExpr(expr, type);
	}

	/**
	 * Creates a lambda expression.
	 *
	 * @return the new lambda expression instance.
	 */
	public static LambdaExpr lambdaExpr() {
		return new TDLambdaExpr(Trees.<FormalParameter>emptyList(), true, Trees.<Expr, BlockStmt>right(blockStmt()));
	}

	public static LiteralExpr<Void> nullLiteralExpr() {
		return new TDLiteralExpr<Void>(Void.class, Literals.from(Void.class, null));
	}

	public static LiteralExpr<Boolean> literalExpr(boolean value) {
		return new TDLiteralExpr<Boolean>(Boolean.class, Literals.from(Boolean.class, value));
	}

	public static LiteralExpr<Integer> literalExpr(int value) {
		return new TDLiteralExpr<Integer>(Integer.class, Literals.from(Integer.class, value));
	}

	public static LiteralExpr<Long> literalExpr(long value) {
		return new TDLiteralExpr<Long>(Long.class, Literals.from(Long.class, value));
	}

	public static LiteralExpr<Float> literalExpr(float value) {
		return new TDLiteralExpr<Float>(Float.class, Literals.from(Float.class, value));
	}

	public static LiteralExpr<Double> literalExpr(double value) {
		return new TDLiteralExpr<Double>(Double.class, Literals.from(Double.class, value));
	}

	public static LiteralExpr<Character> literalExpr(char value) {
		return new TDLiteralExpr<Character>(Character.class, Literals.from(Character.class, value));
	}

	public static LiteralExpr<String> literalExpr(String value) {
		return new TDLiteralExpr<String>(String.class, Literals.from(String.class, value));
	}

	/**
	 * Creates a marker annotation expression.
	 *
	 * @return the new marker annotation expression instance.
	 */
	public static MarkerAnnotationExpr markerAnnotationExpr() {
		return new TDMarkerAnnotationExpr((QualifiedName) null);
	}

	/**
	 * Creates a marker annotation expression.
	 *
	 * @param name the name child tree.
	 * @return the new marker annotation expression instance.
	 */
	public static MarkerAnnotationExpr markerAnnotationExpr(QualifiedName name) {
		return new TDMarkerAnnotationExpr(name);
	}

	/**
	 * Creates an annotation member value pair.
	 *
	 * @return the new annotation member value pair instance.
	 */
	public static MemberValuePair memberValuePair() {
		return new TDMemberValuePair(null, null);
	}

	/**
	 * Creates an annotation member value pair.
	 *
	 * @param name  the name child tree.
	 * @param value the value child tree.
	 * @return the new annotation member value pair instance.
	 */
	public static MemberValuePair memberValuePair(Name name, Expr value) {
		return new TDMemberValuePair(name, value);
	}

	/**
	 * Creates a method invocation expression.
	 *
	 * @return the new method invocation expression instance.
	 */
	public static MethodInvocationExpr methodInvocationExpr() {
		return new TDMethodInvocationExpr(Trees.<Expr>none(), Trees.<Type>emptyList(), null, Trees.<Expr>emptyList());
	}

	/**
	 * Creates a method invocation expression.
	 *
	 * @param name the name child tree.
	 * @return the new method invocation expression instance.
	 */
	public static MethodInvocationExpr methodInvocationExpr(Name name) {
		return new TDMethodInvocationExpr(Trees.<Expr>none(), Trees.<Type>emptyList(), name, Trees.<Expr>emptyList());
	}

	/**
	 * Creates a method reference expression.
	 *
	 * @return the new method reference expression instance.
	 */
	public static MethodReferenceExpr methodReferenceExpr() {
		return new TDMethodReferenceExpr(null, Trees.<Type>emptyList(), null);
	}

	/**
	 * Creates a method reference expression.
	 *
	 * @param scope the scope child tree.
	 * @param name  the name child tree.
	 * @return the new method reference expression instance.
	 */
	public static MethodReferenceExpr methodReferenceExpr(Expr scope, Name name) {
		return new TDMethodReferenceExpr(scope, Trees.<Type>emptyList(), name);
	}

	/**
	 * Creates a normal annotation expression.
	 *
	 * @return the new normal annotation expression instance.
	 */
	public static NormalAnnotationExpr normalAnnotationExpr() {
		return new TDNormalAnnotationExpr(null, Trees.<MemberValuePair>emptyList());
	}

	/**
	 * Creates a normal annotation expression.
	 *
	 * @param name the name child tree.
	 * @return the new normal annotation expression instance.
	 */
	public static NormalAnnotationExpr normalAnnotationExpr(QualifiedName name) {
		return new TDNormalAnnotationExpr(name, Trees.<MemberValuePair>emptyList());
	}

	/**
	 * Creates an object creation expression.
	 *
	 * @return the new object creation expression instance.
	 */
	public static ObjectCreationExpr objectCreationExpr() {
		return new TDObjectCreationExpr(Trees.<Expr>none(), Trees.<Type>emptyList(), null, Trees.<Expr>emptyList(), Trees.<NodeList<MemberDecl>>none());
	}

	/**
	 * Creates an object creation expression.
	 *
	 * @param type the type child tree.
	 * @return the new object creation expression instance.
	 */
	public static ObjectCreationExpr objectCreationExpr(QualifiedType type) {
		return new TDObjectCreationExpr(Trees.<Expr>none(), Trees.<Type>emptyList(), type, Trees.<Expr>emptyList(), Trees.<NodeList<MemberDecl>>none());
	}

	/**
	 * Creates a parenthesized expression.
	 *
	 * @return the new parenthesized expression instance.
	 */
	public static ParenthesizedExpr parenthesizedExpr() {
		return new TDParenthesizedExpr((Expr) null);
	}

	/**
	 * Creates a parenthesized expression.
	 *
	 * @param inner the inner child tree.
	 * @return the new parenthesized expression instance.
	 */
	public static ParenthesizedExpr parenthesizedExpr(Expr inner) {
		return new TDParenthesizedExpr(inner);
	}

	/**
	 * Creates a single member annotation expression.
	 *
	 * @return the new single member annotation expression instance.
	 */
	public static SingleMemberAnnotationExpr singleMemberAnnotationExpr() {
		return new TDSingleMemberAnnotationExpr(null, null);
	}

	/**
	 * Creates a single member annotation expression.
	 *
	 * @param name        the name child tree.
	 * @param memberValue the member value child tree.
	 * @return the new single member annotation expression instance.
	 */
	public static SingleMemberAnnotationExpr singleMemberAnnotationExpr(QualifiedName name, Expr memberValue) {
		return new TDSingleMemberAnnotationExpr(name, memberValue);
	}

	/**
	 * Creates a 'super' expression.
	 *
	 * @return the new 'super' expression instance.
	 */
	public static SuperExpr superExpr() {
		return new TDSuperExpr(Trees.<Expr>none());
	}

	/**
	 * Creates a 'this' expression.
	 *
	 * @return the new 'this' expression instance.
	 */
	public static ThisExpr thisExpr() {
		return new TDThisExpr(Trees.<Expr>none());
	}

	/**
	 * Creates a type expression.
	 *
	 * @return the new type expression instance.
	 */
	public static TypeExpr typeExpr() {
		return new TDTypeExpr((Type) null);
	}

	/**
	 * Creates a type expression.
	 *
	 * @param type the type child tree.
	 * @return the new type expression instance.
	 */
	public static TypeExpr typeExpr(Type type) {
		return new TDTypeExpr(type);
	}

	/**
	 * Creates an unary expression.
	 *
	 * @return the new unary expression instance.
	 */
	public static UnaryExpr unaryExpr() {
		return new TDUnaryExpr(null, null);
	}

	/**
	 * Creates an unary expression.
	 *
	 * @param op   the op child tree.
	 * @param expr the expression child tree.
	 * @return the new unary expression instance.
	 */
	public static UnaryExpr unaryExpr(UnaryOp op, Expr expr) {
		return new TDUnaryExpr(op, expr);
	}

	/**
	 * Creates a variable declaration expression.
	 *
	 * @return the new variable declaration expression instance.
	 */
	public static VariableDeclarationExpr variableDeclarationExpr() {
		return new TDVariableDeclarationExpr((LocalVariableDecl) null);
	}

	/**
	 * Creates a variable declaration expression.
	 *
	 * @param declaration the declaration child tree.
	 * @return the new variable declaration expression instance.
	 */
	public static VariableDeclarationExpr variableDeclarationExpr(LocalVariableDecl declaration) {
		return new TDVariableDeclarationExpr(declaration);
	}

	/**
	 * Creates a name.
	 *
	 * @return the new name instance.
	 */
	public static Name name() {
		return new TDName((String) null);
	}

	/**
	 * Creates a name.
	 *
	 * @param id the identifier child tree.
	 * @return the new name instance.
	 */
	public static Name name(String id) {
		return new TDName(id);
	}

	/**
	 * Creates a qualified name.
	 *
	 * @return the new qualified name instance.
	 */
	public static QualifiedName qualifiedName() {
		return new TDQualifiedName(Trees.<QualifiedName>none(), null);
	}

	/**
	 * Creates a qualified name.
	 *
	 * @param name the name child tree.
	 * @return the new qualified name instance.
	 */
	public static QualifiedName qualifiedName(Name name) {
		return new TDQualifiedName(Trees.<QualifiedName>none(), name);
	}

	public static QualifiedName qualifiedName(String nameString) {
		final String[] split = nameString.split("\\.");
		QualifiedName name = null;
		for (String part : split) {
			name = new TDQualifiedName(optionOf(name), new TDName(part));
		}
		return name;
	}

	/**
	 * Creates an 'assert' statement.
	 *
	 * @return the new 'assert' statement instance.
	 */
	public static AssertStmt assertStmt() {
		return new TDAssertStmt(null, Trees.<Expr>none());
	}

	/**
	 * Creates an 'assert' statement.
	 *
	 * @param check the check child tree.
	 * @return the new 'assert' statement instance.
	 */
	public static AssertStmt assertStmt(Expr check) {
		return new TDAssertStmt(check, Trees.<Expr>none());
	}

	/**
	 * Creates a block statement.
	 *
	 * @return the new block statement instance.
	 */
	public static BlockStmt blockStmt() {
		return new TDBlockStmt(Trees.<Stmt>emptyList());
	}

	/**
	 * Creates a 'break' statement.
	 *
	 * @return the new 'break' statement instance.
	 */
	public static BreakStmt breakStmt() {
		return new TDBreakStmt(Trees.<Name>none());
	}

	/**
	 * Creates a 'catch' clause.
	 *
	 * @return the new 'catch' clause instance.
	 */
	public static CatchClause catchClause() {
		return new TDCatchClause(null, null);
	}

	/**
	 * Creates a 'catch' clause.
	 *
	 * @param param      the parameter child tree.
	 * @param catchBlock the 'catch' block child tree.
	 * @return the new 'catch' clause instance.
	 */
	public static CatchClause catchClause(FormalParameter param, BlockStmt catchBlock) {
		return new TDCatchClause(param, catchBlock);
	}

	/**
	 * Creates a 'continue' statement.
	 *
	 * @return the new 'continue' statement instance.
	 */
	public static ContinueStmt continueStmt() {
		return new TDContinueStmt(Trees.<Name>none());
	}

	/**
	 * Creates a 'do-while' statement.
	 *
	 * @return the new 'do-while' statement instance.
	 */
	public static DoStmt doStmt() {
		return new TDDoStmt(null, null);
	}

	/**
	 * Creates a 'do-while' statement.
	 *
	 * @param body      the body child tree.
	 * @param condition the condition child tree.
	 * @return the new 'do-while' statement instance.
	 */
	public static DoStmt doStmt(Stmt body, Expr condition) {
		return new TDDoStmt(body, condition);
	}

	/**
	 * Creates an empty statement.
	 *
	 * @return the new empty statement instance.
	 */
	public static EmptyStmt emptyStmt() {
		return new TDEmptyStmt();
	}

	/**
	 * Creates an explicit constructor invocation statement.
	 *
	 * @return the new explicit constructor invocation statement instance.
	 */
	public static ExplicitConstructorInvocationStmt explicitConstructorInvocationStmt() {
		return new TDExplicitConstructorInvocationStmt(Trees.<Type>emptyList(), false, Trees.<Expr>none(), Trees.<Expr>emptyList());
	}

	/**
	 * Creates an expression statement.
	 *
	 * @return the new expression statement instance.
	 */
	public static ExpressionStmt expressionStmt() {
		return new TDExpressionStmt((Expr) null);
	}

	/**
	 * Creates an expression statement.
	 *
	 * @param expr the expression child tree.
	 * @return the new expression statement instance.
	 */
	public static ExpressionStmt expressionStmt(Expr expr) {
		return new TDExpressionStmt(expr);
	}

	/**
	 * Creates a 'for' statement.
	 *
	 * @return the new 'for' statement instance.
	 */
	public static ForStmt forStmt() {
		return new TDForStmt(Trees.<Expr>emptyList(), null, Trees.<Expr>emptyList(), null);
	}

	/**
	 * Creates a 'for' statement.
	 *
	 * @param compare the compare child tree.
	 * @param body    the body child tree.
	 * @return the new 'for' statement instance.
	 */
	public static ForStmt forStmt(Expr compare, Stmt body) {
		return new TDForStmt(Trees.<Expr>emptyList(), compare, Trees.<Expr>emptyList(), body);
	}

	/**
	 * Creates a "enhanced" 'for' statement.
	 *
	 * @return the new "enhanced" 'for' statement instance.
	 */
	public static ForeachStmt foreachStmt() {
		return new TDForeachStmt(null, null, null);
	}

	/**
	 * Creates a "enhanced" 'for' statement.
	 *
	 * @param var      the var child tree.
	 * @param iterable the iterable child tree.
	 * @param body     the body child tree.
	 * @return the new "enhanced" 'for' statement instance.
	 */
	public static ForeachStmt foreachStmt(VariableDeclarationExpr var, Expr iterable, Stmt body) {
		return new TDForeachStmt(var, iterable, body);
	}

	/**
	 * Creates an 'if' statement.
	 *
	 * @return the new 'if' statement instance.
	 */
	public static IfStmt ifStmt() {
		return new TDIfStmt(null, null, Trees.<Stmt>none());
	}

	/**
	 * Creates an 'if' statement.
	 *
	 * @param condition the condition child tree.
	 * @param thenStmt  the then statement child tree.
	 * @return the new 'if' statement instance.
	 */
	public static IfStmt ifStmt(Expr condition, Stmt thenStmt) {
		return new TDIfStmt(condition, thenStmt, Trees.<Stmt>none());
	}

	/**
	 * Creates a labeled statement.
	 *
	 * @return the new labeled statement instance.
	 */
	public static LabeledStmt labeledStmt() {
		return new TDLabeledStmt(null, null);
	}

	/**
	 * Creates a labeled statement.
	 *
	 * @param label the label child tree.
	 * @param stmt  the statement child tree.
	 * @return the new labeled statement instance.
	 */
	public static LabeledStmt labeledStmt(Name label, Stmt stmt) {
		return new TDLabeledStmt(label, stmt);
	}

	/**
	 * Creates a 'return' statement.
	 *
	 * @return the new 'return' statement instance.
	 */
	public static ReturnStmt returnStmt() {
		return new TDReturnStmt(Trees.<Expr>none());
	}

	/**
	 * Creates a 'switch' case.
	 *
	 * @return the new 'switch' case instance.
	 */
	public static SwitchCase switchCase() {
		return new TDSwitchCase(Trees.<Expr>none(), Trees.<Stmt>emptyList());
	}

	/**
	 * Creates a 'switch' statement.
	 *
	 * @return the new 'switch' statement instance.
	 */
	public static SwitchStmt switchStmt() {
		return new TDSwitchStmt(null, Trees.<SwitchCase>emptyList());
	}

	/**
	 * Creates a 'switch' statement.
	 *
	 * @param selector the selector child tree.
	 * @return the new 'switch' statement instance.
	 */
	public static SwitchStmt switchStmt(Expr selector) {
		return new TDSwitchStmt(selector, Trees.<SwitchCase>emptyList());
	}

	/**
	 * Creates a 'synchronized' statement.
	 *
	 * @return the new 'synchronized' statement instance.
	 */
	public static SynchronizedStmt synchronizedStmt() {
		return new TDSynchronizedStmt(null, null);
	}

	/**
	 * Creates a 'synchronized' statement.
	 *
	 * @param expr  the expression child tree.
	 * @param block the block child tree.
	 * @return the new 'synchronized' statement instance.
	 */
	public static SynchronizedStmt synchronizedStmt(Expr expr, BlockStmt block) {
		return new TDSynchronizedStmt(expr, block);
	}

	/**
	 * Creates a 'throw' statement.
	 *
	 * @return the new 'throw' statement instance.
	 */
	public static ThrowStmt throwStmt() {
		return new TDThrowStmt((Expr) null);
	}

	/**
	 * Creates a 'throw' statement.
	 *
	 * @param expr the expression child tree.
	 * @return the new 'throw' statement instance.
	 */
	public static ThrowStmt throwStmt(Expr expr) {
		return new TDThrowStmt(expr);
	}

	/**
	 * Creates a 'try' statement.
	 *
	 * @return the new 'try' statement instance.
	 */
	public static TryStmt tryStmt() {
		return new TDTryStmt(Trees.<VariableDeclarationExpr>emptyList(), false, null, Trees.<CatchClause>emptyList(), Trees.<BlockStmt>none());
	}

	/**
	 * Creates a 'try' statement.
	 *
	 * @param tryBlock the 'try' block child tree.
	 * @return the new 'try' statement instance.
	 */
	public static TryStmt tryStmt(BlockStmt tryBlock) {
		return new TDTryStmt(Trees.<VariableDeclarationExpr>emptyList(), false, tryBlock, Trees.<CatchClause>emptyList(), Trees.<BlockStmt>none());
	}

	/**
	 * Creates a type declaration statement.
	 *
	 * @return the new type declaration statement instance.
	 */
	public static TypeDeclarationStmt typeDeclarationStmt() {
		return new TDTypeDeclarationStmt((TypeDecl) null);
	}

	/**
	 * Creates a type declaration statement.
	 *
	 * @param typeDecl the type declaration child tree.
	 * @return the new type declaration statement instance.
	 */
	public static TypeDeclarationStmt typeDeclarationStmt(TypeDecl typeDecl) {
		return new TDTypeDeclarationStmt(typeDecl);
	}

	/**
	 * Creates a 'while' statement.
	 *
	 * @return the new 'while' statement instance.
	 */
	public static WhileStmt whileStmt() {
		return new TDWhileStmt(null, null);
	}

	/**
	 * Creates a 'while' statement.
	 *
	 * @param condition the condition child tree.
	 * @param body      the body child tree.
	 * @return the new 'while' statement instance.
	 */
	public static WhileStmt whileStmt(Expr condition, Stmt body) {
		return new TDWhileStmt(condition, body);
	}

	/**
	 * Creates an array type.
	 *
	 * @return the new array type instance.
	 */
	public static ArrayType arrayType() {
		return new TDArrayType(null, Trees.<ArrayDim>emptyList());
	}

	/**
	 * Creates an array type.
	 *
	 * @param componentType the component type child tree.
	 * @return the new array type instance.
	 */
	public static ArrayType arrayType(Type componentType) {
		return new TDArrayType(componentType, Trees.<ArrayDim>emptyList());
	}

	/**
	 * Creates an intersection type.
	 *
	 * @return the new intersection type instance.
	 */
	public static IntersectionType intersectionType() {
		return new TDIntersectionType(Trees.<Type>emptyList());
	}

	/**
	 * Creates a primitive type.
	 *
	 * @return the new primitive type instance.
	 */
	public static PrimitiveType primitiveType() {
		return new TDPrimitiveType(Trees.<AnnotationExpr>emptyList(), null);
	}

	/**
	 * Creates a primitive type.
	 *
	 * @param primitive the primitive child tree.
	 * @return the new primitive type instance.
	 */
	public static PrimitiveType primitiveType(Primitive primitive) {
		return new TDPrimitiveType(Trees.<AnnotationExpr>emptyList(), primitive);
	}

	/**
	 * Creates a qualified type.
	 *
	 * @return the new qualified type instance.
	 */
	public static QualifiedType qualifiedType() {
		return new TDQualifiedType(Trees.<AnnotationExpr>emptyList(), Trees.<QualifiedType>none(), null, Trees.<NodeList<Type>>none());
	}

	/**
	 * Creates a qualified type.
	 *
	 * @param name the name child tree.
	 * @return the new qualified type instance.
	 */
	public static QualifiedType qualifiedType(Name name) {
		return new TDQualifiedType(Trees.<AnnotationExpr>emptyList(), Trees.<QualifiedType>none(), name, Trees.<NodeList<Type>>none());
	}

	/**
	 * Creates an union type.
	 *
	 * @return the new union type instance.
	 */
	public static UnionType unionType() {
		return new TDUnionType(Trees.<Type>emptyList());
	}

	/**
	 * Creates an unknown type.
	 *
	 * @return the new unknown type instance.
	 */
	public static UnknownType unknownType() {
		return new TDUnknownType();
	}

	/**
	 * Creates a void type.
	 *
	 * @return the new void type instance.
	 */
	public static VoidType voidType() {
		return new TDVoidType();
	}

	/**
	 * Creates a wildcard type.
	 *
	 * @return the new wildcard type instance.
	 */
	public static WildcardType wildcardType() {
		return new TDWildcardType(Trees.<AnnotationExpr>emptyList(), Trees.<ReferenceType>none(), Trees.<ReferenceType>none());
	}
}
