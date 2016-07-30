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

public enum Kind {
	/**
	 * Kind for an annotation type declaration.
	 */
	AnnotationDecl,
	/**
	 * Kind for an annotation type member declaration.
	 */
	AnnotationMemberDecl,
	/**
	 * Kind for an array dimension.
	 */
	ArrayDim,
	/**
	 * Kind for a class declaration.
	 */
	ClassDecl,
	/**
	 * Kind for a compilation unit.
	 */
	CompilationUnit,
	/**
	 * Kind for a constructor declaration.
	 */
	ConstructorDecl,
	/**
	 * Kind for an empty member declaration.
	 */
	EmptyMemberDecl,
	/**
	 * Kind for an empty type declaration.
	 */
	EmptyTypeDecl,
	/**
	 * Kind for an enum constant declaration.
	 */
	EnumConstantDecl,
	/**
	 * Kind for an enum declaration.
	 */
	EnumDecl,
	/**
	 * Kind for a field declaration.
	 */
	FieldDecl,
	/**
	 * Kind for a formal parameter.
	 */
	FormalParameter,
	/**
	 * Kind for an import declaration.
	 */
	ImportDecl,
	/**
	 * Kind for an initializer declaration.
	 */
	InitializerDecl,
	/**
	 * Kind for an interface declaration.
	 */
	InterfaceDecl,
	/**
	 * Kind for a local variable declaration.
	 */
	LocalVariableDecl,
	/**
	 * Kind for a method declaration.
	 */
	MethodDecl,
	/**
	 * Kind for a modifier.
	 */
	Modifier,
	/**
	 * Kind for a package declaration.
	 */
	PackageDecl,
	/**
	 * Kind for a type parameter.
	 */
	TypeParameter,
	/**
	 * Kind for a variable declarator.
	 */
	VariableDeclarator,
	/**
	 * Kind for a variable declarator identifier.
	 */
	VariableDeclaratorId,
	/**
	 * Kind for an array access expression.
	 */
	ArrayAccessExpr,
	/**
	 * Kind for an array creation expression.
	 */
	ArrayCreationExpr,
	/**
	 * Kind for an array dimension expression.
	 */
	ArrayDimExpr,
	/**
	 * Kind for an array initializer expression.
	 */
	ArrayInitializerExpr,
	/**
	 * Kind for an assignment expression.
	 */
	AssignExpr,
	/**
	 * Kind for a binary expression.
	 */
	BinaryExpr,
	/**
	 * Kind for a cast expression.
	 */
	CastExpr,
	/**
	 * Kind for a 'class' expression.
	 */
	ClassExpr,
	/**
	 * Kind for a conditional expression.
	 */
	ConditionalExpr,
	/**
	 * Kind for a field access expression.
	 */
	FieldAccessExpr,
	/**
	 * Kind for an 'instanceof' expression.
	 */
	InstanceOfExpr,
	/**
	 * Kind for a lambda expression.
	 */
	LambdaExpr,
	/**
	 * Kind for a literal expression.
	 */
	LiteralExpr,
	/**
	 * Kind for a marker annotation expression.
	 */
	MarkerAnnotationExpr,
	/**
	 * Kind for an annotation member value pair.
	 */
	MemberValuePair,
	/**
	 * Kind for a method invocation expression.
	 */
	MethodInvocationExpr,
	/**
	 * Kind for a method reference expression.
	 */
	MethodReferenceExpr,
	/**
	 * Kind for a normal annotation expression.
	 */
	NormalAnnotationExpr,
	/**
	 * Kind for an object creation expression.
	 */
	ObjectCreationExpr,
	/**
	 * Kind for a parenthesized expression.
	 */
	ParenthesizedExpr,
	/**
	 * Kind for a single member annotation expression.
	 */
	SingleMemberAnnotationExpr,
	/**
	 * Kind for a 'super' expression.
	 */
	SuperExpr,
	/**
	 * Kind for a 'this' expression.
	 */
	ThisExpr,
	/**
	 * Kind for a type expression.
	 */
	TypeExpr,
	/**
	 * Kind for an unary expression.
	 */
	UnaryExpr,
	/**
	 * Kind for a variable declaration expression.
	 */
	VariableDeclarationExpr,
	/**
	 * Kind for a name.
	 */
	Name,
	/**
	 * Kind for a qualified name.
	 */
	QualifiedName,
	/**
	 * Kind for an 'assert' statement.
	 */
	AssertStmt,
	/**
	 * Kind for a block statement.
	 */
	BlockStmt,
	/**
	 * Kind for a 'break' statement.
	 */
	BreakStmt,
	/**
	 * Kind for a 'catch' clause.
	 */
	CatchClause,
	/**
	 * Kind for a 'continue' statement.
	 */
	ContinueStmt,
	/**
	 * Kind for a 'do-while' statement.
	 */
	DoStmt,
	/**
	 * Kind for an empty statement.
	 */
	EmptyStmt,
	/**
	 * Kind for an explicit constructor invocation statement.
	 */
	ExplicitConstructorInvocationStmt,
	/**
	 * Kind for an expression statement.
	 */
	ExpressionStmt,
	/**
	 * Kind for a 'for' statement.
	 */
	ForStmt,
	/**
	 * Kind for a "enhanced" 'for' statement.
	 */
	ForeachStmt,
	/**
	 * Kind for an 'if' statement.
	 */
	IfStmt,
	/**
	 * Kind for a labeled statement.
	 */
	LabeledStmt,
	/**
	 * Kind for a 'return' statement.
	 */
	ReturnStmt,
	/**
	 * Kind for a 'switch' case.
	 */
	SwitchCase,
	/**
	 * Kind for a 'switch' statement.
	 */
	SwitchStmt,
	/**
	 * Kind for a 'synchronized' statement.
	 */
	SynchronizedStmt,
	/**
	 * Kind for a 'throw' statement.
	 */
	ThrowStmt,
	/**
	 * Kind for a 'try' statement.
	 */
	TryStmt,
	/**
	 * Kind for a type declaration statement.
	 */
	TypeDeclarationStmt,
	/**
	 * Kind for a 'while' statement.
	 */
	WhileStmt,
	/**
	 * Kind for an array type.
	 */
	ArrayType,
	/**
	 * Kind for an intersection type.
	 */
	IntersectionType,
	/**
	 * Kind for a primitive type.
	 */
	PrimitiveType,
	/**
	 * Kind for a qualified type.
	 */
	QualifiedType,
	/**
	 * Kind for an union type.
	 */
	UnionType,
	/**
	 * Kind for an unknown type.
	 */
	UnknownType,
	/**
	 * Kind for a void type.
	 */
	VoidType,
	/**
	 * Kind for a wildcard type.
	 */
	WildcardType
}
