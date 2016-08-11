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

package org.jlato.unit.tree;

import org.jlato.tree.NodeEither;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Trees;
import org.jlato.tree.decl.*;
import org.jlato.tree.expr.*;
import org.jlato.tree.name.Name;
import org.jlato.tree.name.QualifiedName;
import org.jlato.tree.stmt.*;
import org.jlato.tree.type.*;
import org.jlato.unit.util.Arbitrary;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TreesEqualsHashCodeTest {

	@Test
	public void testAnnotationDecl() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<ExtendedModifier> modifiers = arbitrary.arbitraryListExtendedModifier();
			Name name = arbitrary.arbitraryName();
			NodeList<MemberDecl> members = arbitrary.arbitraryListMemberDecl();
			AnnotationDecl expected = Trees.annotationDecl().withModifiers(modifiers).withName(name).withMembers(members);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			AnnotationDecl actual = Trees.annotationDecl();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withModifiers(modifiers);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withName(name);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withMembers(members);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testAnnotationMemberDecl() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<ExtendedModifier> modifiers = arbitrary.arbitraryListExtendedModifier();
			Type type = arbitrary.arbitraryType();
			Name name = arbitrary.arbitraryName();
			NodeList<ArrayDim> dims = arbitrary.arbitraryListArrayDim();
			NodeOption<Expr> defaultValue = arbitrary.arbitraryOptionExpr();
			AnnotationMemberDecl expected = Trees.annotationMemberDecl().withModifiers(modifiers).withType(type).withName(name).withDims(dims).withDefaultValue(defaultValue);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			AnnotationMemberDecl actual = Trees.annotationMemberDecl();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withModifiers(modifiers);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withType(type);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withName(name);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withDims(dims);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withDefaultValue(defaultValue);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testArrayDim() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<AnnotationExpr> annotations = arbitrary.arbitraryListAnnotationExpr();
			ArrayDim expected = Trees.arrayDim().withAnnotations(annotations);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			ArrayDim actual = Trees.arrayDim();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withAnnotations(annotations);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testClassDecl() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<ExtendedModifier> modifiers = arbitrary.arbitraryListExtendedModifier();
			Name name = arbitrary.arbitraryName();
			NodeList<TypeParameter> typeParams = arbitrary.arbitraryListTypeParameter();
			NodeOption<QualifiedType> extendsClause = arbitrary.arbitraryOptionQualifiedType();
			NodeList<QualifiedType> implementsClause = arbitrary.arbitraryListQualifiedType();
			NodeList<MemberDecl> members = arbitrary.arbitraryListMemberDecl();
			ClassDecl expected = Trees.classDecl().withModifiers(modifiers).withName(name).withTypeParams(typeParams).withExtendsClause(extendsClause).withImplementsClause(implementsClause).withMembers(members);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			ClassDecl actual = Trees.classDecl();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withModifiers(modifiers);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withName(name);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withTypeParams(typeParams);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withExtendsClause(extendsClause);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withImplementsClause(implementsClause);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withMembers(members);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testCompilationUnit() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			PackageDecl packageDecl = arbitrary.arbitraryPackageDecl();
			NodeList<ImportDecl> imports = arbitrary.arbitraryListImportDecl();
			NodeList<TypeDecl> types = arbitrary.arbitraryListTypeDecl();
			CompilationUnit expected = Trees.compilationUnit().withPackageDecl(packageDecl).withImports(imports).withTypes(types);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			CompilationUnit actual = Trees.compilationUnit();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withPackageDecl(packageDecl);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withImports(imports);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withTypes(types);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testConstructorDecl() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<ExtendedModifier> modifiers = arbitrary.arbitraryListExtendedModifier();
			NodeList<TypeParameter> typeParams = arbitrary.arbitraryListTypeParameter();
			Name name = arbitrary.arbitraryName();
			NodeList<FormalParameter> params = arbitrary.arbitraryListFormalParameter();
			NodeList<QualifiedType> throwsClause = arbitrary.arbitraryListQualifiedType();
			BlockStmt body = arbitrary.arbitraryBlockStmt();
			ConstructorDecl expected = Trees.constructorDecl().withModifiers(modifiers).withTypeParams(typeParams).withName(name).withParams(params).withThrowsClause(throwsClause).withBody(body);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			ConstructorDecl actual = Trees.constructorDecl();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withModifiers(modifiers);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withTypeParams(typeParams);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withName(name);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withParams(params);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withThrowsClause(throwsClause);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withBody(body);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testEmptyMemberDecl() {
		EmptyMemberDecl expected = Trees.emptyMemberDecl();
		Assert.assertEquals(expected, expected);
		Assert.assertNotEquals(expected, null);
		EmptyMemberDecl actual = Trees.emptyMemberDecl();
		Assert.assertEquals(expected, actual);
		Assert.assertEquals(expected.hashCode(), actual.hashCode());
	}

	@Test
	public void testEmptyTypeDecl() {
		EmptyTypeDecl expected = Trees.emptyTypeDecl();
		Assert.assertEquals(expected, expected);
		Assert.assertNotEquals(expected, null);
		EmptyTypeDecl actual = Trees.emptyTypeDecl();
		Assert.assertEquals(expected, actual);
		Assert.assertEquals(expected.hashCode(), actual.hashCode());
	}

	@Test
	public void testEnumConstantDecl() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<ExtendedModifier> modifiers = arbitrary.arbitraryListExtendedModifier();
			Name name = arbitrary.arbitraryName();
			NodeOption<NodeList<Expr>> args = arbitrary.arbitraryOptionListExpr();
			NodeOption<NodeList<MemberDecl>> classBody = arbitrary.arbitraryOptionListMemberDecl();
			EnumConstantDecl expected = Trees.enumConstantDecl().withModifiers(modifiers).withName(name).withArgs(args).withClassBody(classBody);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			EnumConstantDecl actual = Trees.enumConstantDecl();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withModifiers(modifiers);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withName(name);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withArgs(args);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withClassBody(classBody);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testEnumDecl() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<ExtendedModifier> modifiers = arbitrary.arbitraryListExtendedModifier();
			Name name = arbitrary.arbitraryName();
			NodeList<QualifiedType> implementsClause = arbitrary.arbitraryListQualifiedType();
			NodeList<EnumConstantDecl> enumConstants = arbitrary.arbitraryListEnumConstantDecl();
			boolean trailingComma = arbitrary.arbitraryBoolean();
			NodeList<MemberDecl> members = arbitrary.arbitraryListMemberDecl();
			EnumDecl expected = Trees.enumDecl().withModifiers(modifiers).withName(name).withImplementsClause(implementsClause).withEnumConstants(enumConstants).withTrailingComma(trailingComma).withMembers(members);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			EnumDecl actual = Trees.enumDecl();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withModifiers(modifiers);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withName(name);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withImplementsClause(implementsClause);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withEnumConstants(enumConstants);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withTrailingComma(trailingComma);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withMembers(members);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testFieldDecl() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<ExtendedModifier> modifiers = arbitrary.arbitraryListExtendedModifier();
			Type type = arbitrary.arbitraryType();
			NodeList<VariableDeclarator> variables = arbitrary.arbitraryListVariableDeclarator();
			FieldDecl expected = Trees.fieldDecl().withModifiers(modifiers).withType(type).withVariables(variables);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			FieldDecl actual = Trees.fieldDecl();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withModifiers(modifiers);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withType(type);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withVariables(variables);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testFormalParameter() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<ExtendedModifier> modifiers = arbitrary.arbitraryListExtendedModifier();
			Type type = arbitrary.arbitraryType();
			boolean isVarArgs = arbitrary.arbitraryBoolean();
			NodeList<AnnotationExpr> ellipsisAnnotations = arbitrary.arbitraryListAnnotationExpr();
			NodeOption<VariableDeclaratorId> id = arbitrary.arbitraryOptionVariableDeclaratorId();
			boolean isReceiver = arbitrary.arbitraryBoolean();
			NodeOption<Name> receiverTypeName = arbitrary.arbitraryOptionName();
			FormalParameter expected = Trees.formalParameter().withModifiers(modifiers).withType(type).setVarArgs(isVarArgs).withEllipsisAnnotations(ellipsisAnnotations).withId(id).setReceiver(isReceiver).withReceiverTypeName(receiverTypeName);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			FormalParameter actual = Trees.formalParameter();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withModifiers(modifiers);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withType(type);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.setVarArgs(isVarArgs);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withEllipsisAnnotations(ellipsisAnnotations);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withId(id);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.setReceiver(isReceiver);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withReceiverTypeName(receiverTypeName);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testImportDecl() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			QualifiedName name = arbitrary.arbitraryQualifiedName();
			boolean isStatic = arbitrary.arbitraryBoolean();
			boolean isOnDemand = arbitrary.arbitraryBoolean();
			ImportDecl expected = Trees.importDecl().withName(name).setStatic(isStatic).setOnDemand(isOnDemand);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			ImportDecl actual = Trees.importDecl();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withName(name);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.setStatic(isStatic);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.setOnDemand(isOnDemand);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testInitializerDecl() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<ExtendedModifier> modifiers = arbitrary.arbitraryListExtendedModifier();
			BlockStmt body = arbitrary.arbitraryBlockStmt();
			InitializerDecl expected = Trees.initializerDecl().withModifiers(modifiers).withBody(body);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			InitializerDecl actual = Trees.initializerDecl();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withModifiers(modifiers);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withBody(body);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testInterfaceDecl() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<ExtendedModifier> modifiers = arbitrary.arbitraryListExtendedModifier();
			Name name = arbitrary.arbitraryName();
			NodeList<TypeParameter> typeParams = arbitrary.arbitraryListTypeParameter();
			NodeList<QualifiedType> extendsClause = arbitrary.arbitraryListQualifiedType();
			NodeList<MemberDecl> members = arbitrary.arbitraryListMemberDecl();
			InterfaceDecl expected = Trees.interfaceDecl().withModifiers(modifiers).withName(name).withTypeParams(typeParams).withExtendsClause(extendsClause).withMembers(members);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			InterfaceDecl actual = Trees.interfaceDecl();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withModifiers(modifiers);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withName(name);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withTypeParams(typeParams);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withExtendsClause(extendsClause);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withMembers(members);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testLocalVariableDecl() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<ExtendedModifier> modifiers = arbitrary.arbitraryListExtendedModifier();
			Type type = arbitrary.arbitraryType();
			NodeList<VariableDeclarator> variables = arbitrary.arbitraryListVariableDeclarator();
			LocalVariableDecl expected = Trees.localVariableDecl().withModifiers(modifiers).withType(type).withVariables(variables);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			LocalVariableDecl actual = Trees.localVariableDecl();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withModifiers(modifiers);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withType(type);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withVariables(variables);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testMethodDecl() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<ExtendedModifier> modifiers = arbitrary.arbitraryListExtendedModifier();
			NodeList<TypeParameter> typeParams = arbitrary.arbitraryListTypeParameter();
			Type type = arbitrary.arbitraryType();
			Name name = arbitrary.arbitraryName();
			NodeList<FormalParameter> params = arbitrary.arbitraryListFormalParameter();
			NodeList<ArrayDim> dims = arbitrary.arbitraryListArrayDim();
			NodeList<QualifiedType> throwsClause = arbitrary.arbitraryListQualifiedType();
			NodeOption<BlockStmt> body = arbitrary.arbitraryOptionBlockStmt();
			MethodDecl expected = Trees.methodDecl().withModifiers(modifiers).withTypeParams(typeParams).withType(type).withName(name).withParams(params).withDims(dims).withThrowsClause(throwsClause).withBody(body);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			MethodDecl actual = Trees.methodDecl();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withModifiers(modifiers);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withTypeParams(typeParams);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withType(type);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withName(name);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withParams(params);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withDims(dims);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withThrowsClause(throwsClause);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withBody(body);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testModifier() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			ModifierKeyword keyword = arbitrary.arbitraryModifierKeyword();
			Modifier expected = Trees.modifier().withKeyword(keyword);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			Modifier actual = Trees.modifier();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withKeyword(keyword);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testPackageDecl() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<AnnotationExpr> annotations = arbitrary.arbitraryListAnnotationExpr();
			QualifiedName name = arbitrary.arbitraryQualifiedName();
			PackageDecl expected = Trees.packageDecl().withAnnotations(annotations).withName(name);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			PackageDecl actual = Trees.packageDecl();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withAnnotations(annotations);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withName(name);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testTypeParameter() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<AnnotationExpr> annotations = arbitrary.arbitraryListAnnotationExpr();
			Name name = arbitrary.arbitraryName();
			NodeList<Type> bounds = arbitrary.arbitraryListType();
			TypeParameter expected = Trees.typeParameter().withAnnotations(annotations).withName(name).withBounds(bounds);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			TypeParameter actual = Trees.typeParameter();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withAnnotations(annotations);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withName(name);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withBounds(bounds);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testVariableDeclarator() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			VariableDeclaratorId id = arbitrary.arbitraryVariableDeclaratorId();
			NodeOption<Expr> init = arbitrary.arbitraryOptionExpr();
			VariableDeclarator expected = Trees.variableDeclarator().withId(id).withInit(init);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			VariableDeclarator actual = Trees.variableDeclarator();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withId(id);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withInit(init);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testVariableDeclaratorId() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Name name = arbitrary.arbitraryName();
			NodeList<ArrayDim> dims = arbitrary.arbitraryListArrayDim();
			VariableDeclaratorId expected = Trees.variableDeclaratorId().withName(name).withDims(dims);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			VariableDeclaratorId actual = Trees.variableDeclaratorId();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withName(name);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withDims(dims);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testArrayAccessExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr name = arbitrary.arbitraryExpr();
			Expr index = arbitrary.arbitraryExpr();
			ArrayAccessExpr expected = Trees.arrayAccessExpr().withName(name).withIndex(index);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			ArrayAccessExpr actual = Trees.arrayAccessExpr();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withName(name);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withIndex(index);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testArrayCreationExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Type type = arbitrary.arbitraryType();
			NodeList<ArrayDimExpr> dimExprs = arbitrary.arbitraryListArrayDimExpr();
			NodeList<ArrayDim> dims = arbitrary.arbitraryListArrayDim();
			NodeOption<ArrayInitializerExpr> init = arbitrary.arbitraryOptionArrayInitializerExpr();
			ArrayCreationExpr expected = Trees.arrayCreationExpr().withType(type).withDimExprs(dimExprs).withDims(dims).withInit(init);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			ArrayCreationExpr actual = Trees.arrayCreationExpr();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withType(type);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withDimExprs(dimExprs);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withDims(dims);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withInit(init);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testArrayDimExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<AnnotationExpr> annotations = arbitrary.arbitraryListAnnotationExpr();
			Expr expr = arbitrary.arbitraryExpr();
			ArrayDimExpr expected = Trees.arrayDimExpr().withAnnotations(annotations).withExpr(expr);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			ArrayDimExpr actual = Trees.arrayDimExpr();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withAnnotations(annotations);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withExpr(expr);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testArrayInitializerExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<Expr> values = arbitrary.arbitraryListExpr();
			boolean trailingComma = arbitrary.arbitraryBoolean();
			ArrayInitializerExpr expected = Trees.arrayInitializerExpr().withValues(values).withTrailingComma(trailingComma);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			ArrayInitializerExpr actual = Trees.arrayInitializerExpr();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withValues(values);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withTrailingComma(trailingComma);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testAssignExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr target = arbitrary.arbitraryExpr();
			AssignOp op = arbitrary.arbitraryAssignOp();
			Expr value = arbitrary.arbitraryExpr();
			AssignExpr expected = Trees.assignExpr().withTarget(target).withOp(op).withValue(value);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			AssignExpr actual = Trees.assignExpr();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withTarget(target);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withOp(op);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withValue(value);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testBinaryExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr left = arbitrary.arbitraryExpr();
			BinaryOp op = arbitrary.arbitraryBinaryOp();
			Expr right = arbitrary.arbitraryExpr();
			BinaryExpr expected = Trees.binaryExpr().withLeft(left).withOp(op).withRight(right);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			BinaryExpr actual = Trees.binaryExpr();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withLeft(left);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withOp(op);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withRight(right);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testCastExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Type type = arbitrary.arbitraryType();
			Expr expr = arbitrary.arbitraryExpr();
			CastExpr expected = Trees.castExpr().withType(type).withExpr(expr);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			CastExpr actual = Trees.castExpr();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withType(type);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withExpr(expr);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testClassExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Type type = arbitrary.arbitraryType();
			ClassExpr expected = Trees.classExpr().withType(type);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			ClassExpr actual = Trees.classExpr();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withType(type);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testConditionalExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr condition = arbitrary.arbitraryExpr();
			Expr thenExpr = arbitrary.arbitraryExpr();
			Expr elseExpr = arbitrary.arbitraryExpr();
			ConditionalExpr expected = Trees.conditionalExpr().withCondition(condition).withThenExpr(thenExpr).withElseExpr(elseExpr);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			ConditionalExpr actual = Trees.conditionalExpr();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withCondition(condition);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withThenExpr(thenExpr);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withElseExpr(elseExpr);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testFieldAccessExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeOption<Expr> scope = arbitrary.arbitraryOptionExpr();
			Name name = arbitrary.arbitraryName();
			FieldAccessExpr expected = Trees.fieldAccessExpr().withScope(scope).withName(name);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			FieldAccessExpr actual = Trees.fieldAccessExpr();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withScope(scope);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withName(name);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testInstanceOfExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr expr = arbitrary.arbitraryExpr();
			Type type = arbitrary.arbitraryType();
			InstanceOfExpr expected = Trees.instanceOfExpr().withExpr(expr).withType(type);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			InstanceOfExpr actual = Trees.instanceOfExpr();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withExpr(expr);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withType(type);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testLambdaExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<FormalParameter> params = arbitrary.arbitraryListFormalParameter();
			boolean hasParens = !arbitrary.arbitraryBoolean();
			NodeEither<Expr, BlockStmt> body = arbitrary.arbitraryEitherExprBlockStmt();
			LambdaExpr expected = Trees.lambdaExpr().withParams(params).setParens(hasParens).withBody(body);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			LambdaExpr actual = Trees.lambdaExpr();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withParams(params);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.setParens(hasParens);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withBody(body);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testMarkerAnnotationExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			QualifiedName name = arbitrary.arbitraryQualifiedName();
			MarkerAnnotationExpr expected = Trees.markerAnnotationExpr().withName(name);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			MarkerAnnotationExpr actual = Trees.markerAnnotationExpr();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withName(name);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testMemberValuePair() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Name name = arbitrary.arbitraryName();
			Expr value = arbitrary.arbitraryExpr();
			MemberValuePair expected = Trees.memberValuePair().withName(name).withValue(value);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			MemberValuePair actual = Trees.memberValuePair();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withName(name);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withValue(value);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testMethodInvocationExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeOption<Expr> scope = arbitrary.arbitraryOptionExpr();
			NodeList<Type> typeArgs = arbitrary.arbitraryListType();
			Name name = arbitrary.arbitraryName();
			NodeList<Expr> args = arbitrary.arbitraryListExpr();
			MethodInvocationExpr expected = Trees.methodInvocationExpr().withScope(scope).withTypeArgs(typeArgs).withName(name).withArgs(args);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			MethodInvocationExpr actual = Trees.methodInvocationExpr();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withScope(scope);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withTypeArgs(typeArgs);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withName(name);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withArgs(args);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testMethodReferenceExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr scope = arbitrary.arbitraryExpr();
			NodeList<Type> typeArgs = arbitrary.arbitraryListType();
			Name name = arbitrary.arbitraryName();
			MethodReferenceExpr expected = Trees.methodReferenceExpr().withScope(scope).withTypeArgs(typeArgs).withName(name);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			MethodReferenceExpr actual = Trees.methodReferenceExpr();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withScope(scope);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withTypeArgs(typeArgs);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withName(name);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testNormalAnnotationExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			QualifiedName name = arbitrary.arbitraryQualifiedName();
			NodeList<MemberValuePair> pairs = arbitrary.arbitraryListMemberValuePair();
			NormalAnnotationExpr expected = Trees.normalAnnotationExpr().withName(name).withPairs(pairs);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			NormalAnnotationExpr actual = Trees.normalAnnotationExpr();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withName(name);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withPairs(pairs);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testObjectCreationExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeOption<Expr> scope = arbitrary.arbitraryOptionExpr();
			NodeList<Type> typeArgs = arbitrary.arbitraryListType();
			QualifiedType type = arbitrary.arbitraryQualifiedType();
			NodeList<Expr> args = arbitrary.arbitraryListExpr();
			NodeOption<NodeList<MemberDecl>> body = arbitrary.arbitraryOptionListMemberDecl();
			ObjectCreationExpr expected = Trees.objectCreationExpr().withScope(scope).withTypeArgs(typeArgs).withType(type).withArgs(args).withBody(body);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			ObjectCreationExpr actual = Trees.objectCreationExpr();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withScope(scope);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withTypeArgs(typeArgs);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withType(type);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withArgs(args);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withBody(body);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testParenthesizedExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr inner = arbitrary.arbitraryExpr();
			ParenthesizedExpr expected = Trees.parenthesizedExpr().withInner(inner);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			ParenthesizedExpr actual = Trees.parenthesizedExpr();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withInner(inner);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testSingleMemberAnnotationExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			QualifiedName name = arbitrary.arbitraryQualifiedName();
			Expr memberValue = arbitrary.arbitraryExpr();
			SingleMemberAnnotationExpr expected = Trees.singleMemberAnnotationExpr().withName(name).withMemberValue(memberValue);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			SingleMemberAnnotationExpr actual = Trees.singleMemberAnnotationExpr();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withName(name);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withMemberValue(memberValue);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testSuperExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeOption<Expr> classExpr = arbitrary.arbitraryOptionExpr();
			SuperExpr expected = Trees.superExpr().withClassExpr(classExpr);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			SuperExpr actual = Trees.superExpr();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withClassExpr(classExpr);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testThisExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeOption<Expr> classExpr = arbitrary.arbitraryOptionExpr();
			ThisExpr expected = Trees.thisExpr().withClassExpr(classExpr);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			ThisExpr actual = Trees.thisExpr();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withClassExpr(classExpr);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testTypeExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Type type = arbitrary.arbitraryType();
			TypeExpr expected = Trees.typeExpr().withType(type);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			TypeExpr actual = Trees.typeExpr();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withType(type);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testUnaryExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			UnaryOp op = arbitrary.arbitraryUnaryOp();
			Expr expr = arbitrary.arbitraryExpr();
			UnaryExpr expected = Trees.unaryExpr().withOp(op).withExpr(expr);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			UnaryExpr actual = Trees.unaryExpr();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withOp(op);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withExpr(expr);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testVariableDeclarationExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			LocalVariableDecl declaration = arbitrary.arbitraryLocalVariableDecl();
			VariableDeclarationExpr expected = Trees.variableDeclarationExpr().withDeclaration(declaration);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			VariableDeclarationExpr actual = Trees.variableDeclarationExpr();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withDeclaration(declaration);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testName() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			String id = arbitrary.arbitraryString();
			Name expected = Trees.name().withId(id);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			Name actual = Trees.name();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withId(id);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testQualifiedName() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeOption<QualifiedName> qualifier = arbitrary.arbitraryOptionQualifiedName();
			Name name = arbitrary.arbitraryName();
			QualifiedName expected = Trees.qualifiedName().withQualifier(qualifier).withName(name);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			QualifiedName actual = Trees.qualifiedName();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withQualifier(qualifier);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withName(name);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testAssertStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr check = arbitrary.arbitraryExpr();
			NodeOption<Expr> msg = arbitrary.arbitraryOptionExpr();
			AssertStmt expected = Trees.assertStmt().withCheck(check).withMsg(msg);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			AssertStmt actual = Trees.assertStmt();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withCheck(check);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withMsg(msg);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testBlockStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<Stmt> stmts = arbitrary.arbitraryListStmt();
			BlockStmt expected = Trees.blockStmt().withStmts(stmts);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			BlockStmt actual = Trees.blockStmt();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withStmts(stmts);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testBreakStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeOption<Name> id = arbitrary.arbitraryOptionName();
			BreakStmt expected = Trees.breakStmt().withId(id);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			BreakStmt actual = Trees.breakStmt();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withId(id);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testCatchClause() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			FormalParameter param = arbitrary.arbitraryFormalParameter();
			BlockStmt catchBlock = arbitrary.arbitraryBlockStmt();
			CatchClause expected = Trees.catchClause().withParam(param).withCatchBlock(catchBlock);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			CatchClause actual = Trees.catchClause();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withParam(param);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withCatchBlock(catchBlock);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testContinueStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeOption<Name> id = arbitrary.arbitraryOptionName();
			ContinueStmt expected = Trees.continueStmt().withId(id);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			ContinueStmt actual = Trees.continueStmt();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withId(id);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testDoStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Stmt body = arbitrary.arbitraryStmt();
			Expr condition = arbitrary.arbitraryExpr();
			DoStmt expected = Trees.doStmt().withBody(body).withCondition(condition);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			DoStmt actual = Trees.doStmt();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withBody(body);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withCondition(condition);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testEmptyStmt() {
		EmptyStmt expected = Trees.emptyStmt();
		Assert.assertEquals(expected, expected);
		Assert.assertNotEquals(expected, null);
		EmptyStmt actual = Trees.emptyStmt();
		Assert.assertEquals(expected, actual);
		Assert.assertEquals(expected.hashCode(), actual.hashCode());
	}

	@Test
	public void testExplicitConstructorInvocationStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<Type> typeArgs = arbitrary.arbitraryListType();
			boolean isThis = arbitrary.arbitraryBoolean();
			NodeOption<Expr> expr = arbitrary.arbitraryOptionExpr();
			NodeList<Expr> args = arbitrary.arbitraryListExpr();
			ExplicitConstructorInvocationStmt expected = Trees.explicitConstructorInvocationStmt().withTypeArgs(typeArgs).setThis(isThis).withExpr(expr).withArgs(args);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			ExplicitConstructorInvocationStmt actual = Trees.explicitConstructorInvocationStmt();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withTypeArgs(typeArgs);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.setThis(isThis);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withExpr(expr);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withArgs(args);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testExpressionStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr expr = arbitrary.arbitraryExpr();
			ExpressionStmt expected = Trees.expressionStmt().withExpr(expr);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			ExpressionStmt actual = Trees.expressionStmt();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withExpr(expr);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testForStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<Expr> init = arbitrary.arbitraryListExpr();
			Expr compare = arbitrary.arbitraryExpr();
			NodeList<Expr> update = arbitrary.arbitraryListExpr();
			Stmt body = arbitrary.arbitraryStmt();
			ForStmt expected = Trees.forStmt().withInit(init).withCompare(compare).withUpdate(update).withBody(body);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			ForStmt actual = Trees.forStmt();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withInit(init);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withCompare(compare);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withUpdate(update);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withBody(body);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testForeachStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			VariableDeclarationExpr var = arbitrary.arbitraryVariableDeclarationExpr();
			Expr iterable = arbitrary.arbitraryExpr();
			Stmt body = arbitrary.arbitraryStmt();
			ForeachStmt expected = Trees.foreachStmt().withVar(var).withIterable(iterable).withBody(body);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			ForeachStmt actual = Trees.foreachStmt();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withVar(var);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withIterable(iterable);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withBody(body);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testIfStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr condition = arbitrary.arbitraryExpr();
			Stmt thenStmt = arbitrary.arbitraryStmt();
			NodeOption<Stmt> elseStmt = arbitrary.arbitraryOptionStmt();
			IfStmt expected = Trees.ifStmt().withCondition(condition).withThenStmt(thenStmt).withElseStmt(elseStmt);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			IfStmt actual = Trees.ifStmt();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withCondition(condition);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withThenStmt(thenStmt);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withElseStmt(elseStmt);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testLabeledStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Name label = arbitrary.arbitraryName();
			Stmt stmt = arbitrary.arbitraryStmt();
			LabeledStmt expected = Trees.labeledStmt().withLabel(label).withStmt(stmt);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			LabeledStmt actual = Trees.labeledStmt();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withLabel(label);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withStmt(stmt);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testReturnStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeOption<Expr> expr = arbitrary.arbitraryOptionExpr();
			ReturnStmt expected = Trees.returnStmt().withExpr(expr);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			ReturnStmt actual = Trees.returnStmt();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withExpr(expr);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testSwitchCase() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeOption<Expr> label = arbitrary.arbitraryOptionExpr();
			NodeList<Stmt> stmts = arbitrary.arbitraryListStmt();
			SwitchCase expected = Trees.switchCase().withLabel(label).withStmts(stmts);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			SwitchCase actual = Trees.switchCase();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withLabel(label);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withStmts(stmts);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testSwitchStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr selector = arbitrary.arbitraryExpr();
			NodeList<SwitchCase> cases = arbitrary.arbitraryListSwitchCase();
			SwitchStmt expected = Trees.switchStmt().withSelector(selector).withCases(cases);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			SwitchStmt actual = Trees.switchStmt();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withSelector(selector);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withCases(cases);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testSynchronizedStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr expr = arbitrary.arbitraryExpr();
			BlockStmt block = arbitrary.arbitraryBlockStmt();
			SynchronizedStmt expected = Trees.synchronizedStmt().withExpr(expr).withBlock(block);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			SynchronizedStmt actual = Trees.synchronizedStmt();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withExpr(expr);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withBlock(block);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testThrowStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr expr = arbitrary.arbitraryExpr();
			ThrowStmt expected = Trees.throwStmt().withExpr(expr);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			ThrowStmt actual = Trees.throwStmt();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withExpr(expr);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testTryStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<VariableDeclarationExpr> resources = arbitrary.arbitraryListVariableDeclarationExpr();
			boolean trailingSemiColon = arbitrary.arbitraryBoolean();
			BlockStmt tryBlock = arbitrary.arbitraryBlockStmt();
			NodeList<CatchClause> catchs = arbitrary.arbitraryListCatchClause();
			NodeOption<BlockStmt> finallyBlock = arbitrary.arbitraryOptionBlockStmt();
			TryStmt expected = Trees.tryStmt().withResources(resources).withTrailingSemiColon(trailingSemiColon).withTryBlock(tryBlock).withCatchs(catchs).withFinallyBlock(finallyBlock);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			TryStmt actual = Trees.tryStmt();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withResources(resources);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withTrailingSemiColon(trailingSemiColon);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withTryBlock(tryBlock);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withCatchs(catchs);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withFinallyBlock(finallyBlock);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testTypeDeclarationStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			TypeDecl typeDecl = arbitrary.arbitraryTypeDecl();
			TypeDeclarationStmt expected = Trees.typeDeclarationStmt().withTypeDecl(typeDecl);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			TypeDeclarationStmt actual = Trees.typeDeclarationStmt();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withTypeDecl(typeDecl);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testWhileStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr condition = arbitrary.arbitraryExpr();
			Stmt body = arbitrary.arbitraryStmt();
			WhileStmt expected = Trees.whileStmt().withCondition(condition).withBody(body);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			WhileStmt actual = Trees.whileStmt();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withCondition(condition);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withBody(body);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testArrayType() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Type componentType = arbitrary.arbitraryType();
			NodeList<ArrayDim> dims = arbitrary.arbitraryListArrayDim();
			ArrayType expected = Trees.arrayType().withComponentType(componentType).withDims(dims);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			ArrayType actual = Trees.arrayType();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withComponentType(componentType);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withDims(dims);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testIntersectionType() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<Type> types = arbitrary.arbitraryListType();
			IntersectionType expected = Trees.intersectionType().withTypes(types);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			IntersectionType actual = Trees.intersectionType();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withTypes(types);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testPrimitiveType() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<AnnotationExpr> annotations = arbitrary.arbitraryListAnnotationExpr();
			Primitive primitive = arbitrary.arbitraryPrimitive();
			PrimitiveType expected = Trees.primitiveType().withAnnotations(annotations).withPrimitive(primitive);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			PrimitiveType actual = Trees.primitiveType();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withAnnotations(annotations);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withPrimitive(primitive);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testQualifiedType() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<AnnotationExpr> annotations = arbitrary.arbitraryListAnnotationExpr();
			NodeOption<QualifiedType> scope = arbitrary.arbitraryOptionQualifiedType();
			Name name = arbitrary.arbitraryName();
			NodeOption<NodeList<Type>> typeArgs = arbitrary.arbitraryOptionListType();
			QualifiedType expected = Trees.qualifiedType().withAnnotations(annotations).withScope(scope).withName(name).withTypeArgs(typeArgs);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			QualifiedType actual = Trees.qualifiedType();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withAnnotations(annotations);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withScope(scope);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withName(name);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withTypeArgs(typeArgs);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testUnionType() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<Type> types = arbitrary.arbitraryListType();
			UnionType expected = Trees.unionType().withTypes(types);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			UnionType actual = Trees.unionType();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withTypes(types);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testUnknownType() {
		UnknownType expected = Trees.unknownType();
		Assert.assertEquals(expected, expected);
		Assert.assertNotEquals(expected, null);
		UnknownType actual = Trees.unknownType();
		Assert.assertEquals(expected, actual);
		Assert.assertEquals(expected.hashCode(), actual.hashCode());
	}

	@Test
	public void testVoidType() {
		VoidType expected = Trees.voidType();
		Assert.assertEquals(expected, expected);
		Assert.assertNotEquals(expected, null);
		VoidType actual = Trees.voidType();
		Assert.assertEquals(expected, actual);
		Assert.assertEquals(expected.hashCode(), actual.hashCode());
	}

	@Test
	public void testWildcardType() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<AnnotationExpr> annotations = arbitrary.arbitraryListAnnotationExpr();
			NodeOption<ReferenceType> ext = arbitrary.arbitraryOptionReferenceType();
			NodeOption<ReferenceType> sup = arbitrary.arbitraryOptionReferenceType();
			WildcardType expected = Trees.wildcardType().withAnnotations(annotations).withExt(ext).withSup(sup);
			Assert.assertEquals(expected, expected);
			Assert.assertNotEquals(expected, null);
			WildcardType actual = Trees.wildcardType();
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withAnnotations(annotations);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withExt(ext);
			Assert.assertNotEquals(expected, actual);
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withSup(sup);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}
}
