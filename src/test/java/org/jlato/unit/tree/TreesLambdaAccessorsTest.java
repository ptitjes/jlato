package org.jlato.unit.tree;

import org.jlato.tree.*;
import org.jlato.tree.decl.*;
import org.jlato.tree.expr.*;
import org.jlato.tree.name.*;
import org.jlato.tree.stmt.*;
import org.jlato.tree.type.*;
import org.jlato.unit.util.Arbitrary;
import org.jlato.util.Mutation;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TreesLambdaAccessorsTest {

	@Test
	public void testAnnotationDecl() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<ExtendedModifier> modifiers = arbitrary.arbitraryListExtendedModifier();
			Name name = arbitrary.arbitraryName();
			NodeList<MemberDecl> members = arbitrary.arbitraryListMemberDecl();
			AnnotationDecl t = Trees.annotationDecl().withModifiers(modifiers).withName(name).withMembers(members);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(members, t.members());
			NodeList<ExtendedModifier> modifiers2 = arbitrary.arbitraryListExtendedModifier();
			Name name2 = arbitrary.arbitraryName();
			NodeList<MemberDecl> members2 = arbitrary.arbitraryListMemberDecl();
			AnnotationDecl t2 = t.withModifiers(mutationBy(modifiers, modifiers2)).withName(mutationBy(name, name2)).withMembers(mutationBy(members, members2));
			Assert.assertEquals(modifiers2, t2.modifiers());
			Assert.assertEquals(name2, t2.name());
			Assert.assertEquals(members2, t2.members());
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
			AnnotationMemberDecl t = Trees.annotationMemberDecl().withModifiers(modifiers).withType(type).withName(name).withDims(dims).withDefaultValue(defaultValue);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(type, t.type());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(dims, t.dims());
			Assert.assertEquals(defaultValue, t.defaultValue());
			NodeList<ExtendedModifier> modifiers2 = arbitrary.arbitraryListExtendedModifier();
			Type type2 = arbitrary.arbitraryType();
			Name name2 = arbitrary.arbitraryName();
			NodeList<ArrayDim> dims2 = arbitrary.arbitraryListArrayDim();
			NodeOption<Expr> defaultValue2 = arbitrary.arbitraryOptionExpr();
			AnnotationMemberDecl t2 = t.withModifiers(mutationBy(modifiers, modifiers2)).withType(mutationBy(type, type2)).withName(mutationBy(name, name2)).withDims(mutationBy(dims, dims2)).withDefaultValue(mutationBy(defaultValue, defaultValue2));
			Assert.assertEquals(modifiers2, t2.modifiers());
			Assert.assertEquals(type2, t2.type());
			Assert.assertEquals(name2, t2.name());
			Assert.assertEquals(dims2, t2.dims());
			Assert.assertEquals(defaultValue2, t2.defaultValue());
		}
	}

	@Test
	public void testArrayDim() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<AnnotationExpr> annotations = arbitrary.arbitraryListAnnotationExpr();
			ArrayDim t = Trees.arrayDim().withAnnotations(annotations);
			Assert.assertEquals(annotations, t.annotations());
			NodeList<AnnotationExpr> annotations2 = arbitrary.arbitraryListAnnotationExpr();
			ArrayDim t2 = t.withAnnotations(mutationBy(annotations, annotations2));
			Assert.assertEquals(annotations2, t2.annotations());
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
			ClassDecl t = Trees.classDecl().withModifiers(modifiers).withName(name).withTypeParams(typeParams).withExtendsClause(extendsClause).withImplementsClause(implementsClause).withMembers(members);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(typeParams, t.typeParams());
			Assert.assertEquals(extendsClause, t.extendsClause());
			Assert.assertEquals(implementsClause, t.implementsClause());
			Assert.assertEquals(members, t.members());
			NodeList<ExtendedModifier> modifiers2 = arbitrary.arbitraryListExtendedModifier();
			Name name2 = arbitrary.arbitraryName();
			NodeList<TypeParameter> typeParams2 = arbitrary.arbitraryListTypeParameter();
			NodeOption<QualifiedType> extendsClause2 = arbitrary.arbitraryOptionQualifiedType();
			NodeList<QualifiedType> implementsClause2 = arbitrary.arbitraryListQualifiedType();
			NodeList<MemberDecl> members2 = arbitrary.arbitraryListMemberDecl();
			ClassDecl t2 = t.withModifiers(mutationBy(modifiers, modifiers2)).withName(mutationBy(name, name2)).withTypeParams(mutationBy(typeParams, typeParams2)).withExtendsClause(mutationBy(extendsClause, extendsClause2)).withImplementsClause(mutationBy(implementsClause, implementsClause2)).withMembers(mutationBy(members, members2));
			Assert.assertEquals(modifiers2, t2.modifiers());
			Assert.assertEquals(name2, t2.name());
			Assert.assertEquals(typeParams2, t2.typeParams());
			Assert.assertEquals(extendsClause2, t2.extendsClause());
			Assert.assertEquals(implementsClause2, t2.implementsClause());
			Assert.assertEquals(members2, t2.members());
		}
	}

	@Test
	public void testCompilationUnit() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			PackageDecl packageDecl = arbitrary.arbitraryPackageDecl();
			NodeList<ImportDecl> imports = arbitrary.arbitraryListImportDecl();
			NodeList<TypeDecl> types = arbitrary.arbitraryListTypeDecl();
			CompilationUnit t = Trees.compilationUnit().withPackageDecl(packageDecl).withImports(imports).withTypes(types);
			Assert.assertEquals(packageDecl, t.packageDecl());
			Assert.assertEquals(imports, t.imports());
			Assert.assertEquals(types, t.types());
			PackageDecl packageDecl2 = arbitrary.arbitraryPackageDecl();
			NodeList<ImportDecl> imports2 = arbitrary.arbitraryListImportDecl();
			NodeList<TypeDecl> types2 = arbitrary.arbitraryListTypeDecl();
			CompilationUnit t2 = t.withPackageDecl(mutationBy(packageDecl, packageDecl2)).withImports(mutationBy(imports, imports2)).withTypes(mutationBy(types, types2));
			Assert.assertEquals(packageDecl2, t2.packageDecl());
			Assert.assertEquals(imports2, t2.imports());
			Assert.assertEquals(types2, t2.types());
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
			ConstructorDecl t = Trees.constructorDecl().withModifiers(modifiers).withTypeParams(typeParams).withName(name).withParams(params).withThrowsClause(throwsClause).withBody(body);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(typeParams, t.typeParams());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(params, t.params());
			Assert.assertEquals(throwsClause, t.throwsClause());
			Assert.assertEquals(body, t.body());
			NodeList<ExtendedModifier> modifiers2 = arbitrary.arbitraryListExtendedModifier();
			NodeList<TypeParameter> typeParams2 = arbitrary.arbitraryListTypeParameter();
			Name name2 = arbitrary.arbitraryName();
			NodeList<FormalParameter> params2 = arbitrary.arbitraryListFormalParameter();
			NodeList<QualifiedType> throwsClause2 = arbitrary.arbitraryListQualifiedType();
			BlockStmt body2 = arbitrary.arbitraryBlockStmt();
			ConstructorDecl t2 = t.withModifiers(mutationBy(modifiers, modifiers2)).withTypeParams(mutationBy(typeParams, typeParams2)).withName(mutationBy(name, name2)).withParams(mutationBy(params, params2)).withThrowsClause(mutationBy(throwsClause, throwsClause2)).withBody(mutationBy(body, body2));
			Assert.assertEquals(modifiers2, t2.modifiers());
			Assert.assertEquals(typeParams2, t2.typeParams());
			Assert.assertEquals(name2, t2.name());
			Assert.assertEquals(params2, t2.params());
			Assert.assertEquals(throwsClause2, t2.throwsClause());
			Assert.assertEquals(body2, t2.body());
		}
	}

	@Test
	public void testEnumConstantDecl() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<ExtendedModifier> modifiers = arbitrary.arbitraryListExtendedModifier();
			Name name = arbitrary.arbitraryName();
			NodeOption<NodeList<Expr>> args = arbitrary.arbitraryOptionListExpr();
			NodeOption<NodeList<MemberDecl>> classBody = arbitrary.arbitraryOptionListMemberDecl();
			EnumConstantDecl t = Trees.enumConstantDecl().withModifiers(modifiers).withName(name).withArgs(args).withClassBody(classBody);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(args, t.args());
			Assert.assertEquals(classBody, t.classBody());
			NodeList<ExtendedModifier> modifiers2 = arbitrary.arbitraryListExtendedModifier();
			Name name2 = arbitrary.arbitraryName();
			NodeOption<NodeList<Expr>> args2 = arbitrary.arbitraryOptionListExpr();
			NodeOption<NodeList<MemberDecl>> classBody2 = arbitrary.arbitraryOptionListMemberDecl();
			EnumConstantDecl t2 = t.withModifiers(mutationBy(modifiers, modifiers2)).withName(mutationBy(name, name2)).withArgs(mutationBy(args, args2)).withClassBody(mutationBy(classBody, classBody2));
			Assert.assertEquals(modifiers2, t2.modifiers());
			Assert.assertEquals(name2, t2.name());
			Assert.assertEquals(args2, t2.args());
			Assert.assertEquals(classBody2, t2.classBody());
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
			EnumDecl t = Trees.enumDecl().withModifiers(modifiers).withName(name).withImplementsClause(implementsClause).withEnumConstants(enumConstants).withTrailingComma(trailingComma).withMembers(members);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(implementsClause, t.implementsClause());
			Assert.assertEquals(enumConstants, t.enumConstants());
			Assert.assertEquals(trailingComma, t.trailingComma());
			Assert.assertEquals(members, t.members());
			NodeList<ExtendedModifier> modifiers2 = arbitrary.arbitraryListExtendedModifier();
			Name name2 = arbitrary.arbitraryName();
			NodeList<QualifiedType> implementsClause2 = arbitrary.arbitraryListQualifiedType();
			NodeList<EnumConstantDecl> enumConstants2 = arbitrary.arbitraryListEnumConstantDecl();
			boolean trailingComma2 = arbitrary.arbitraryBoolean();
			NodeList<MemberDecl> members2 = arbitrary.arbitraryListMemberDecl();
			EnumDecl t2 = t.withModifiers(mutationBy(modifiers, modifiers2)).withName(mutationBy(name, name2)).withImplementsClause(mutationBy(implementsClause, implementsClause2)).withEnumConstants(mutationBy(enumConstants, enumConstants2)).withTrailingComma(mutationBy(trailingComma, trailingComma2)).withMembers(mutationBy(members, members2));
			Assert.assertEquals(modifiers2, t2.modifiers());
			Assert.assertEquals(name2, t2.name());
			Assert.assertEquals(implementsClause2, t2.implementsClause());
			Assert.assertEquals(enumConstants2, t2.enumConstants());
			Assert.assertEquals(trailingComma2, t2.trailingComma());
			Assert.assertEquals(members2, t2.members());
		}
	}

	@Test
	public void testFieldDecl() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<ExtendedModifier> modifiers = arbitrary.arbitraryListExtendedModifier();
			Type type = arbitrary.arbitraryType();
			NodeList<VariableDeclarator> variables = arbitrary.arbitraryListVariableDeclarator();
			FieldDecl t = Trees.fieldDecl().withModifiers(modifiers).withType(type).withVariables(variables);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(type, t.type());
			Assert.assertEquals(variables, t.variables());
			NodeList<ExtendedModifier> modifiers2 = arbitrary.arbitraryListExtendedModifier();
			Type type2 = arbitrary.arbitraryType();
			NodeList<VariableDeclarator> variables2 = arbitrary.arbitraryListVariableDeclarator();
			FieldDecl t2 = t.withModifiers(mutationBy(modifiers, modifiers2)).withType(mutationBy(type, type2)).withVariables(mutationBy(variables, variables2));
			Assert.assertEquals(modifiers2, t2.modifiers());
			Assert.assertEquals(type2, t2.type());
			Assert.assertEquals(variables2, t2.variables());
		}
	}

	@Test
	public void testFormalParameter() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<ExtendedModifier> modifiers = arbitrary.arbitraryListExtendedModifier();
			Type type = arbitrary.arbitraryType();
			boolean isVarArgs = arbitrary.arbitraryBoolean();
			VariableDeclaratorId id = arbitrary.arbitraryVariableDeclaratorId();
			FormalParameter t = Trees.formalParameter().withModifiers(modifiers).withType(type).setVarArgs(isVarArgs).withId(id);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(type, t.type());
			Assert.assertEquals(isVarArgs, t.isVarArgs());
			Assert.assertEquals(id, t.id());
			NodeList<ExtendedModifier> modifiers2 = arbitrary.arbitraryListExtendedModifier();
			Type type2 = arbitrary.arbitraryType();
			boolean isVarArgs2 = arbitrary.arbitraryBoolean();
			VariableDeclaratorId id2 = arbitrary.arbitraryVariableDeclaratorId();
			FormalParameter t2 = t.withModifiers(mutationBy(modifiers, modifiers2)).withType(mutationBy(type, type2)).setVarArgs(mutationBy(isVarArgs, isVarArgs2)).withId(mutationBy(id, id2));
			Assert.assertEquals(modifiers2, t2.modifiers());
			Assert.assertEquals(type2, t2.type());
			Assert.assertEquals(isVarArgs2, t2.isVarArgs());
			Assert.assertEquals(id2, t2.id());
		}
	}

	@Test
	public void testImportDecl() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			QualifiedName name = arbitrary.arbitraryQualifiedName();
			boolean isStatic = arbitrary.arbitraryBoolean();
			boolean isOnDemand = arbitrary.arbitraryBoolean();
			ImportDecl t = Trees.importDecl().withName(name).setStatic(isStatic).setOnDemand(isOnDemand);
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(isStatic, t.isStatic());
			Assert.assertEquals(isOnDemand, t.isOnDemand());
			QualifiedName name2 = arbitrary.arbitraryQualifiedName();
			boolean isStatic2 = arbitrary.arbitraryBoolean();
			boolean isOnDemand2 = arbitrary.arbitraryBoolean();
			ImportDecl t2 = t.withName(mutationBy(name, name2)).setStatic(mutationBy(isStatic, isStatic2)).setOnDemand(mutationBy(isOnDemand, isOnDemand2));
			Assert.assertEquals(name2, t2.name());
			Assert.assertEquals(isStatic2, t2.isStatic());
			Assert.assertEquals(isOnDemand2, t2.isOnDemand());
		}
	}

	@Test
	public void testInitializerDecl() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<ExtendedModifier> modifiers = arbitrary.arbitraryListExtendedModifier();
			BlockStmt body = arbitrary.arbitraryBlockStmt();
			InitializerDecl t = Trees.initializerDecl().withModifiers(modifiers).withBody(body);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(body, t.body());
			NodeList<ExtendedModifier> modifiers2 = arbitrary.arbitraryListExtendedModifier();
			BlockStmt body2 = arbitrary.arbitraryBlockStmt();
			InitializerDecl t2 = t.withModifiers(mutationBy(modifiers, modifiers2)).withBody(mutationBy(body, body2));
			Assert.assertEquals(modifiers2, t2.modifiers());
			Assert.assertEquals(body2, t2.body());
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
			InterfaceDecl t = Trees.interfaceDecl().withModifiers(modifiers).withName(name).withTypeParams(typeParams).withExtendsClause(extendsClause).withMembers(members);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(typeParams, t.typeParams());
			Assert.assertEquals(extendsClause, t.extendsClause());
			Assert.assertEquals(members, t.members());
			NodeList<ExtendedModifier> modifiers2 = arbitrary.arbitraryListExtendedModifier();
			Name name2 = arbitrary.arbitraryName();
			NodeList<TypeParameter> typeParams2 = arbitrary.arbitraryListTypeParameter();
			NodeList<QualifiedType> extendsClause2 = arbitrary.arbitraryListQualifiedType();
			NodeList<MemberDecl> members2 = arbitrary.arbitraryListMemberDecl();
			InterfaceDecl t2 = t.withModifiers(mutationBy(modifiers, modifiers2)).withName(mutationBy(name, name2)).withTypeParams(mutationBy(typeParams, typeParams2)).withExtendsClause(mutationBy(extendsClause, extendsClause2)).withMembers(mutationBy(members, members2));
			Assert.assertEquals(modifiers2, t2.modifiers());
			Assert.assertEquals(name2, t2.name());
			Assert.assertEquals(typeParams2, t2.typeParams());
			Assert.assertEquals(extendsClause2, t2.extendsClause());
			Assert.assertEquals(members2, t2.members());
		}
	}

	@Test
	public void testLocalVariableDecl() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<ExtendedModifier> modifiers = arbitrary.arbitraryListExtendedModifier();
			Type type = arbitrary.arbitraryType();
			NodeList<VariableDeclarator> variables = arbitrary.arbitraryListVariableDeclarator();
			LocalVariableDecl t = Trees.localVariableDecl().withModifiers(modifiers).withType(type).withVariables(variables);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(type, t.type());
			Assert.assertEquals(variables, t.variables());
			NodeList<ExtendedModifier> modifiers2 = arbitrary.arbitraryListExtendedModifier();
			Type type2 = arbitrary.arbitraryType();
			NodeList<VariableDeclarator> variables2 = arbitrary.arbitraryListVariableDeclarator();
			LocalVariableDecl t2 = t.withModifiers(mutationBy(modifiers, modifiers2)).withType(mutationBy(type, type2)).withVariables(mutationBy(variables, variables2));
			Assert.assertEquals(modifiers2, t2.modifiers());
			Assert.assertEquals(type2, t2.type());
			Assert.assertEquals(variables2, t2.variables());
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
			MethodDecl t = Trees.methodDecl().withModifiers(modifiers).withTypeParams(typeParams).withType(type).withName(name).withParams(params).withDims(dims).withThrowsClause(throwsClause).withBody(body);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(typeParams, t.typeParams());
			Assert.assertEquals(type, t.type());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(params, t.params());
			Assert.assertEquals(dims, t.dims());
			Assert.assertEquals(throwsClause, t.throwsClause());
			Assert.assertEquals(body, t.body());
			NodeList<ExtendedModifier> modifiers2 = arbitrary.arbitraryListExtendedModifier();
			NodeList<TypeParameter> typeParams2 = arbitrary.arbitraryListTypeParameter();
			Type type2 = arbitrary.arbitraryType();
			Name name2 = arbitrary.arbitraryName();
			NodeList<FormalParameter> params2 = arbitrary.arbitraryListFormalParameter();
			NodeList<ArrayDim> dims2 = arbitrary.arbitraryListArrayDim();
			NodeList<QualifiedType> throwsClause2 = arbitrary.arbitraryListQualifiedType();
			NodeOption<BlockStmt> body2 = arbitrary.arbitraryOptionBlockStmt();
			MethodDecl t2 = t.withModifiers(mutationBy(modifiers, modifiers2)).withTypeParams(mutationBy(typeParams, typeParams2)).withType(mutationBy(type, type2)).withName(mutationBy(name, name2)).withParams(mutationBy(params, params2)).withDims(mutationBy(dims, dims2)).withThrowsClause(mutationBy(throwsClause, throwsClause2)).withBody(mutationBy(body, body2));
			Assert.assertEquals(modifiers2, t2.modifiers());
			Assert.assertEquals(typeParams2, t2.typeParams());
			Assert.assertEquals(type2, t2.type());
			Assert.assertEquals(name2, t2.name());
			Assert.assertEquals(params2, t2.params());
			Assert.assertEquals(dims2, t2.dims());
			Assert.assertEquals(throwsClause2, t2.throwsClause());
			Assert.assertEquals(body2, t2.body());
		}
	}

	@Test
	public void testModifier() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			ModifierKeyword keyword = arbitrary.arbitraryModifierKeyword();
			Modifier t = Trees.modifier().withKeyword(keyword);
			Assert.assertEquals(keyword, t.keyword());
			ModifierKeyword keyword2 = arbitrary.arbitraryModifierKeyword();
			Modifier t2 = t.withKeyword(mutationBy(keyword, keyword2));
			Assert.assertEquals(keyword2, t2.keyword());
		}
	}

	@Test
	public void testPackageDecl() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<AnnotationExpr> annotations = arbitrary.arbitraryListAnnotationExpr();
			QualifiedName name = arbitrary.arbitraryQualifiedName();
			PackageDecl t = Trees.packageDecl().withAnnotations(annotations).withName(name);
			Assert.assertEquals(annotations, t.annotations());
			Assert.assertEquals(name, t.name());
			NodeList<AnnotationExpr> annotations2 = arbitrary.arbitraryListAnnotationExpr();
			QualifiedName name2 = arbitrary.arbitraryQualifiedName();
			PackageDecl t2 = t.withAnnotations(mutationBy(annotations, annotations2)).withName(mutationBy(name, name2));
			Assert.assertEquals(annotations2, t2.annotations());
			Assert.assertEquals(name2, t2.name());
		}
	}

	@Test
	public void testTypeParameter() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<AnnotationExpr> annotations = arbitrary.arbitraryListAnnotationExpr();
			Name name = arbitrary.arbitraryName();
			NodeList<Type> bounds = arbitrary.arbitraryListType();
			TypeParameter t = Trees.typeParameter().withAnnotations(annotations).withName(name).withBounds(bounds);
			Assert.assertEquals(annotations, t.annotations());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(bounds, t.bounds());
			NodeList<AnnotationExpr> annotations2 = arbitrary.arbitraryListAnnotationExpr();
			Name name2 = arbitrary.arbitraryName();
			NodeList<Type> bounds2 = arbitrary.arbitraryListType();
			TypeParameter t2 = t.withAnnotations(mutationBy(annotations, annotations2)).withName(mutationBy(name, name2)).withBounds(mutationBy(bounds, bounds2));
			Assert.assertEquals(annotations2, t2.annotations());
			Assert.assertEquals(name2, t2.name());
			Assert.assertEquals(bounds2, t2.bounds());
		}
	}

	@Test
	public void testVariableDeclarator() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			VariableDeclaratorId id = arbitrary.arbitraryVariableDeclaratorId();
			NodeOption<Expr> init = arbitrary.arbitraryOptionExpr();
			VariableDeclarator t = Trees.variableDeclarator().withId(id).withInit(init);
			Assert.assertEquals(id, t.id());
			Assert.assertEquals(init, t.init());
			VariableDeclaratorId id2 = arbitrary.arbitraryVariableDeclaratorId();
			NodeOption<Expr> init2 = arbitrary.arbitraryOptionExpr();
			VariableDeclarator t2 = t.withId(mutationBy(id, id2)).withInit(mutationBy(init, init2));
			Assert.assertEquals(id2, t2.id());
			Assert.assertEquals(init2, t2.init());
		}
	}

	@Test
	public void testVariableDeclaratorId() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Name name = arbitrary.arbitraryName();
			NodeList<ArrayDim> dims = arbitrary.arbitraryListArrayDim();
			VariableDeclaratorId t = Trees.variableDeclaratorId().withName(name).withDims(dims);
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(dims, t.dims());
			Name name2 = arbitrary.arbitraryName();
			NodeList<ArrayDim> dims2 = arbitrary.arbitraryListArrayDim();
			VariableDeclaratorId t2 = t.withName(mutationBy(name, name2)).withDims(mutationBy(dims, dims2));
			Assert.assertEquals(name2, t2.name());
			Assert.assertEquals(dims2, t2.dims());
		}
	}

	@Test
	public void testArrayAccessExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr name = arbitrary.arbitraryExpr();
			Expr index = arbitrary.arbitraryExpr();
			ArrayAccessExpr t = Trees.arrayAccessExpr().withName(name).withIndex(index);
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(index, t.index());
			Expr name2 = arbitrary.arbitraryExpr();
			Expr index2 = arbitrary.arbitraryExpr();
			ArrayAccessExpr t2 = t.withName(mutationBy(name, name2)).withIndex(mutationBy(index, index2));
			Assert.assertEquals(name2, t2.name());
			Assert.assertEquals(index2, t2.index());
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
			ArrayCreationExpr t = Trees.arrayCreationExpr().withType(type).withDimExprs(dimExprs).withDims(dims).withInit(init);
			Assert.assertEquals(type, t.type());
			Assert.assertEquals(dimExprs, t.dimExprs());
			Assert.assertEquals(dims, t.dims());
			Assert.assertEquals(init, t.init());
			Type type2 = arbitrary.arbitraryType();
			NodeList<ArrayDimExpr> dimExprs2 = arbitrary.arbitraryListArrayDimExpr();
			NodeList<ArrayDim> dims2 = arbitrary.arbitraryListArrayDim();
			NodeOption<ArrayInitializerExpr> init2 = arbitrary.arbitraryOptionArrayInitializerExpr();
			ArrayCreationExpr t2 = t.withType(mutationBy(type, type2)).withDimExprs(mutationBy(dimExprs, dimExprs2)).withDims(mutationBy(dims, dims2)).withInit(mutationBy(init, init2));
			Assert.assertEquals(type2, t2.type());
			Assert.assertEquals(dimExprs2, t2.dimExprs());
			Assert.assertEquals(dims2, t2.dims());
			Assert.assertEquals(init2, t2.init());
		}
	}

	@Test
	public void testArrayDimExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<AnnotationExpr> annotations = arbitrary.arbitraryListAnnotationExpr();
			Expr expr = arbitrary.arbitraryExpr();
			ArrayDimExpr t = Trees.arrayDimExpr().withAnnotations(annotations).withExpr(expr);
			Assert.assertEquals(annotations, t.annotations());
			Assert.assertEquals(expr, t.expr());
			NodeList<AnnotationExpr> annotations2 = arbitrary.arbitraryListAnnotationExpr();
			Expr expr2 = arbitrary.arbitraryExpr();
			ArrayDimExpr t2 = t.withAnnotations(mutationBy(annotations, annotations2)).withExpr(mutationBy(expr, expr2));
			Assert.assertEquals(annotations2, t2.annotations());
			Assert.assertEquals(expr2, t2.expr());
		}
	}

	@Test
	public void testArrayInitializerExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<Expr> values = arbitrary.arbitraryListExpr();
			boolean trailingComma = arbitrary.arbitraryBoolean();
			ArrayInitializerExpr t = Trees.arrayInitializerExpr().withValues(values).withTrailingComma(trailingComma);
			Assert.assertEquals(values, t.values());
			Assert.assertEquals(trailingComma, t.trailingComma());
			NodeList<Expr> values2 = arbitrary.arbitraryListExpr();
			boolean trailingComma2 = arbitrary.arbitraryBoolean();
			ArrayInitializerExpr t2 = t.withValues(mutationBy(values, values2)).withTrailingComma(mutationBy(trailingComma, trailingComma2));
			Assert.assertEquals(values2, t2.values());
			Assert.assertEquals(trailingComma2, t2.trailingComma());
		}
	}

	@Test
	public void testAssignExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr target = arbitrary.arbitraryExpr();
			AssignOp op = arbitrary.arbitraryAssignOp();
			Expr value = arbitrary.arbitraryExpr();
			AssignExpr t = Trees.assignExpr().withTarget(target).withOp(op).withValue(value);
			Assert.assertEquals(target, t.target());
			Assert.assertEquals(op, t.op());
			Assert.assertEquals(value, t.value());
			Expr target2 = arbitrary.arbitraryExpr();
			AssignOp op2 = arbitrary.arbitraryAssignOp();
			Expr value2 = arbitrary.arbitraryExpr();
			AssignExpr t2 = t.withTarget(mutationBy(target, target2)).withOp(mutationBy(op, op2)).withValue(mutationBy(value, value2));
			Assert.assertEquals(target2, t2.target());
			Assert.assertEquals(op2, t2.op());
			Assert.assertEquals(value2, t2.value());
		}
	}

	@Test
	public void testBinaryExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr left = arbitrary.arbitraryExpr();
			BinaryOp op = arbitrary.arbitraryBinaryOp();
			Expr right = arbitrary.arbitraryExpr();
			BinaryExpr t = Trees.binaryExpr().withLeft(left).withOp(op).withRight(right);
			Assert.assertEquals(left, t.left());
			Assert.assertEquals(op, t.op());
			Assert.assertEquals(right, t.right());
			Expr left2 = arbitrary.arbitraryExpr();
			BinaryOp op2 = arbitrary.arbitraryBinaryOp();
			Expr right2 = arbitrary.arbitraryExpr();
			BinaryExpr t2 = t.withLeft(mutationBy(left, left2)).withOp(mutationBy(op, op2)).withRight(mutationBy(right, right2));
			Assert.assertEquals(left2, t2.left());
			Assert.assertEquals(op2, t2.op());
			Assert.assertEquals(right2, t2.right());
		}
	}

	@Test
	public void testCastExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Type type = arbitrary.arbitraryType();
			Expr expr = arbitrary.arbitraryExpr();
			CastExpr t = Trees.castExpr().withType(type).withExpr(expr);
			Assert.assertEquals(type, t.type());
			Assert.assertEquals(expr, t.expr());
			Type type2 = arbitrary.arbitraryType();
			Expr expr2 = arbitrary.arbitraryExpr();
			CastExpr t2 = t.withType(mutationBy(type, type2)).withExpr(mutationBy(expr, expr2));
			Assert.assertEquals(type2, t2.type());
			Assert.assertEquals(expr2, t2.expr());
		}
	}

	@Test
	public void testClassExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Type type = arbitrary.arbitraryType();
			ClassExpr t = Trees.classExpr().withType(type);
			Assert.assertEquals(type, t.type());
			Type type2 = arbitrary.arbitraryType();
			ClassExpr t2 = t.withType(mutationBy(type, type2));
			Assert.assertEquals(type2, t2.type());
		}
	}

	@Test
	public void testConditionalExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr condition = arbitrary.arbitraryExpr();
			Expr thenExpr = arbitrary.arbitraryExpr();
			Expr elseExpr = arbitrary.arbitraryExpr();
			ConditionalExpr t = Trees.conditionalExpr().withCondition(condition).withThenExpr(thenExpr).withElseExpr(elseExpr);
			Assert.assertEquals(condition, t.condition());
			Assert.assertEquals(thenExpr, t.thenExpr());
			Assert.assertEquals(elseExpr, t.elseExpr());
			Expr condition2 = arbitrary.arbitraryExpr();
			Expr thenExpr2 = arbitrary.arbitraryExpr();
			Expr elseExpr2 = arbitrary.arbitraryExpr();
			ConditionalExpr t2 = t.withCondition(mutationBy(condition, condition2)).withThenExpr(mutationBy(thenExpr, thenExpr2)).withElseExpr(mutationBy(elseExpr, elseExpr2));
			Assert.assertEquals(condition2, t2.condition());
			Assert.assertEquals(thenExpr2, t2.thenExpr());
			Assert.assertEquals(elseExpr2, t2.elseExpr());
		}
	}

	@Test
	public void testFieldAccessExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeOption<Expr> scope = arbitrary.arbitraryOptionExpr();
			Name name = arbitrary.arbitraryName();
			FieldAccessExpr t = Trees.fieldAccessExpr().withScope(scope).withName(name);
			Assert.assertEquals(scope, t.scope());
			Assert.assertEquals(name, t.name());
			NodeOption<Expr> scope2 = arbitrary.arbitraryOptionExpr();
			Name name2 = arbitrary.arbitraryName();
			FieldAccessExpr t2 = t.withScope(mutationBy(scope, scope2)).withName(mutationBy(name, name2));
			Assert.assertEquals(scope2, t2.scope());
			Assert.assertEquals(name2, t2.name());
		}
	}

	@Test
	public void testInstanceOfExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr expr = arbitrary.arbitraryExpr();
			Type type = arbitrary.arbitraryType();
			InstanceOfExpr t = Trees.instanceOfExpr().withExpr(expr).withType(type);
			Assert.assertEquals(expr, t.expr());
			Assert.assertEquals(type, t.type());
			Expr expr2 = arbitrary.arbitraryExpr();
			Type type2 = arbitrary.arbitraryType();
			InstanceOfExpr t2 = t.withExpr(mutationBy(expr, expr2)).withType(mutationBy(type, type2));
			Assert.assertEquals(expr2, t2.expr());
			Assert.assertEquals(type2, t2.type());
		}
	}

	@Test
	public void testLambdaExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<FormalParameter> params = arbitrary.arbitraryListFormalParameter();
			boolean hasParens = arbitrary.arbitraryBoolean();
			NodeEither<Expr, BlockStmt> body = arbitrary.arbitraryEitherExprBlockStmt();
			LambdaExpr t = Trees.lambdaExpr().withParams(params).setParens(hasParens).withBody(body);
			Assert.assertEquals(params, t.params());
			Assert.assertEquals(hasParens, t.hasParens());
			Assert.assertEquals(body, t.body());
			NodeList<FormalParameter> params2 = arbitrary.arbitraryListFormalParameter();
			boolean hasParens2 = arbitrary.arbitraryBoolean();
			NodeEither<Expr, BlockStmt> body2 = arbitrary.arbitraryEitherExprBlockStmt();
			LambdaExpr t2 = t.withParams(mutationBy(params, params2)).setParens(mutationBy(hasParens, hasParens2)).withBody(mutationBy(body, body2));
			Assert.assertEquals(params2, t2.params());
			Assert.assertEquals(hasParens2, t2.hasParens());
			Assert.assertEquals(body2, t2.body());
		}
	}

	@Test
	public void testMarkerAnnotationExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			QualifiedName name = arbitrary.arbitraryQualifiedName();
			MarkerAnnotationExpr t = Trees.markerAnnotationExpr().withName(name);
			Assert.assertEquals(name, t.name());
			QualifiedName name2 = arbitrary.arbitraryQualifiedName();
			MarkerAnnotationExpr t2 = t.withName(mutationBy(name, name2));
			Assert.assertEquals(name2, t2.name());
		}
	}

	@Test
	public void testMemberValuePair() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Name name = arbitrary.arbitraryName();
			Expr value = arbitrary.arbitraryExpr();
			MemberValuePair t = Trees.memberValuePair().withName(name).withValue(value);
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(value, t.value());
			Name name2 = arbitrary.arbitraryName();
			Expr value2 = arbitrary.arbitraryExpr();
			MemberValuePair t2 = t.withName(mutationBy(name, name2)).withValue(mutationBy(value, value2));
			Assert.assertEquals(name2, t2.name());
			Assert.assertEquals(value2, t2.value());
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
			MethodInvocationExpr t = Trees.methodInvocationExpr().withScope(scope).withTypeArgs(typeArgs).withName(name).withArgs(args);
			Assert.assertEquals(scope, t.scope());
			Assert.assertEquals(typeArgs, t.typeArgs());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(args, t.args());
			NodeOption<Expr> scope2 = arbitrary.arbitraryOptionExpr();
			NodeList<Type> typeArgs2 = arbitrary.arbitraryListType();
			Name name2 = arbitrary.arbitraryName();
			NodeList<Expr> args2 = arbitrary.arbitraryListExpr();
			MethodInvocationExpr t2 = t.withScope(mutationBy(scope, scope2)).withTypeArgs(mutationBy(typeArgs, typeArgs2)).withName(mutationBy(name, name2)).withArgs(mutationBy(args, args2));
			Assert.assertEquals(scope2, t2.scope());
			Assert.assertEquals(typeArgs2, t2.typeArgs());
			Assert.assertEquals(name2, t2.name());
			Assert.assertEquals(args2, t2.args());
		}
	}

	@Test
	public void testMethodReferenceExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr scope = arbitrary.arbitraryExpr();
			NodeList<Type> typeArgs = arbitrary.arbitraryListType();
			Name name = arbitrary.arbitraryName();
			MethodReferenceExpr t = Trees.methodReferenceExpr().withScope(scope).withTypeArgs(typeArgs).withName(name);
			Assert.assertEquals(scope, t.scope());
			Assert.assertEquals(typeArgs, t.typeArgs());
			Assert.assertEquals(name, t.name());
			Expr scope2 = arbitrary.arbitraryExpr();
			NodeList<Type> typeArgs2 = arbitrary.arbitraryListType();
			Name name2 = arbitrary.arbitraryName();
			MethodReferenceExpr t2 = t.withScope(mutationBy(scope, scope2)).withTypeArgs(mutationBy(typeArgs, typeArgs2)).withName(mutationBy(name, name2));
			Assert.assertEquals(scope2, t2.scope());
			Assert.assertEquals(typeArgs2, t2.typeArgs());
			Assert.assertEquals(name2, t2.name());
		}
	}

	@Test
	public void testNormalAnnotationExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			QualifiedName name = arbitrary.arbitraryQualifiedName();
			NodeList<MemberValuePair> pairs = arbitrary.arbitraryListMemberValuePair();
			NormalAnnotationExpr t = Trees.normalAnnotationExpr().withName(name).withPairs(pairs);
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(pairs, t.pairs());
			QualifiedName name2 = arbitrary.arbitraryQualifiedName();
			NodeList<MemberValuePair> pairs2 = arbitrary.arbitraryListMemberValuePair();
			NormalAnnotationExpr t2 = t.withName(mutationBy(name, name2)).withPairs(mutationBy(pairs, pairs2));
			Assert.assertEquals(name2, t2.name());
			Assert.assertEquals(pairs2, t2.pairs());
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
			ObjectCreationExpr t = Trees.objectCreationExpr().withScope(scope).withTypeArgs(typeArgs).withType(type).withArgs(args).withBody(body);
			Assert.assertEquals(scope, t.scope());
			Assert.assertEquals(typeArgs, t.typeArgs());
			Assert.assertEquals(type, t.type());
			Assert.assertEquals(args, t.args());
			Assert.assertEquals(body, t.body());
			NodeOption<Expr> scope2 = arbitrary.arbitraryOptionExpr();
			NodeList<Type> typeArgs2 = arbitrary.arbitraryListType();
			QualifiedType type2 = arbitrary.arbitraryQualifiedType();
			NodeList<Expr> args2 = arbitrary.arbitraryListExpr();
			NodeOption<NodeList<MemberDecl>> body2 = arbitrary.arbitraryOptionListMemberDecl();
			ObjectCreationExpr t2 = t.withScope(mutationBy(scope, scope2)).withTypeArgs(mutationBy(typeArgs, typeArgs2)).withType(mutationBy(type, type2)).withArgs(mutationBy(args, args2)).withBody(mutationBy(body, body2));
			Assert.assertEquals(scope2, t2.scope());
			Assert.assertEquals(typeArgs2, t2.typeArgs());
			Assert.assertEquals(type2, t2.type());
			Assert.assertEquals(args2, t2.args());
			Assert.assertEquals(body2, t2.body());
		}
	}

	@Test
	public void testParenthesizedExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr inner = arbitrary.arbitraryExpr();
			ParenthesizedExpr t = Trees.parenthesizedExpr().withInner(inner);
			Assert.assertEquals(inner, t.inner());
			Expr inner2 = arbitrary.arbitraryExpr();
			ParenthesizedExpr t2 = t.withInner(mutationBy(inner, inner2));
			Assert.assertEquals(inner2, t2.inner());
		}
	}

	@Test
	public void testSingleMemberAnnotationExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			QualifiedName name = arbitrary.arbitraryQualifiedName();
			Expr memberValue = arbitrary.arbitraryExpr();
			SingleMemberAnnotationExpr t = Trees.singleMemberAnnotationExpr().withName(name).withMemberValue(memberValue);
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(memberValue, t.memberValue());
			QualifiedName name2 = arbitrary.arbitraryQualifiedName();
			Expr memberValue2 = arbitrary.arbitraryExpr();
			SingleMemberAnnotationExpr t2 = t.withName(mutationBy(name, name2)).withMemberValue(mutationBy(memberValue, memberValue2));
			Assert.assertEquals(name2, t2.name());
			Assert.assertEquals(memberValue2, t2.memberValue());
		}
	}

	@Test
	public void testSuperExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeOption<Expr> classExpr = arbitrary.arbitraryOptionExpr();
			SuperExpr t = Trees.superExpr().withClassExpr(classExpr);
			Assert.assertEquals(classExpr, t.classExpr());
			NodeOption<Expr> classExpr2 = arbitrary.arbitraryOptionExpr();
			SuperExpr t2 = t.withClassExpr(mutationBy(classExpr, classExpr2));
			Assert.assertEquals(classExpr2, t2.classExpr());
		}
	}

	@Test
	public void testThisExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeOption<Expr> classExpr = arbitrary.arbitraryOptionExpr();
			ThisExpr t = Trees.thisExpr().withClassExpr(classExpr);
			Assert.assertEquals(classExpr, t.classExpr());
			NodeOption<Expr> classExpr2 = arbitrary.arbitraryOptionExpr();
			ThisExpr t2 = t.withClassExpr(mutationBy(classExpr, classExpr2));
			Assert.assertEquals(classExpr2, t2.classExpr());
		}
	}

	@Test
	public void testTypeExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Type type = arbitrary.arbitraryType();
			TypeExpr t = Trees.typeExpr().withType(type);
			Assert.assertEquals(type, t.type());
			Type type2 = arbitrary.arbitraryType();
			TypeExpr t2 = t.withType(mutationBy(type, type2));
			Assert.assertEquals(type2, t2.type());
		}
	}

	@Test
	public void testUnaryExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			UnaryOp op = arbitrary.arbitraryUnaryOp();
			Expr expr = arbitrary.arbitraryExpr();
			UnaryExpr t = Trees.unaryExpr().withOp(op).withExpr(expr);
			Assert.assertEquals(op, t.op());
			Assert.assertEquals(expr, t.expr());
			UnaryOp op2 = arbitrary.arbitraryUnaryOp();
			Expr expr2 = arbitrary.arbitraryExpr();
			UnaryExpr t2 = t.withOp(mutationBy(op, op2)).withExpr(mutationBy(expr, expr2));
			Assert.assertEquals(op2, t2.op());
			Assert.assertEquals(expr2, t2.expr());
		}
	}

	@Test
	public void testVariableDeclarationExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			LocalVariableDecl declaration = arbitrary.arbitraryLocalVariableDecl();
			VariableDeclarationExpr t = Trees.variableDeclarationExpr().withDeclaration(declaration);
			Assert.assertEquals(declaration, t.declaration());
			LocalVariableDecl declaration2 = arbitrary.arbitraryLocalVariableDecl();
			VariableDeclarationExpr t2 = t.withDeclaration(mutationBy(declaration, declaration2));
			Assert.assertEquals(declaration2, t2.declaration());
		}
	}

	@Test
	public void testName() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			String id = arbitrary.arbitraryString();
			Name t = Trees.name().withId(id);
			Assert.assertEquals(id, t.id());
			String id2 = arbitrary.arbitraryString();
			Name t2 = t.withId(mutationBy(id, id2));
			Assert.assertEquals(id2, t2.id());
		}
	}

	@Test
	public void testQualifiedName() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeOption<QualifiedName> qualifier = arbitrary.arbitraryOptionQualifiedName();
			Name name = arbitrary.arbitraryName();
			QualifiedName t = Trees.qualifiedName().withQualifier(qualifier).withName(name);
			Assert.assertEquals(qualifier, t.qualifier());
			Assert.assertEquals(name, t.name());
			NodeOption<QualifiedName> qualifier2 = arbitrary.arbitraryOptionQualifiedName();
			Name name2 = arbitrary.arbitraryName();
			QualifiedName t2 = t.withQualifier(mutationBy(qualifier, qualifier2)).withName(mutationBy(name, name2));
			Assert.assertEquals(qualifier2, t2.qualifier());
			Assert.assertEquals(name2, t2.name());
		}
	}

	@Test
	public void testAssertStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr check = arbitrary.arbitraryExpr();
			NodeOption<Expr> msg = arbitrary.arbitraryOptionExpr();
			AssertStmt t = Trees.assertStmt().withCheck(check).withMsg(msg);
			Assert.assertEquals(check, t.check());
			Assert.assertEquals(msg, t.msg());
			Expr check2 = arbitrary.arbitraryExpr();
			NodeOption<Expr> msg2 = arbitrary.arbitraryOptionExpr();
			AssertStmt t2 = t.withCheck(mutationBy(check, check2)).withMsg(mutationBy(msg, msg2));
			Assert.assertEquals(check2, t2.check());
			Assert.assertEquals(msg2, t2.msg());
		}
	}

	@Test
	public void testBlockStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<Stmt> stmts = arbitrary.arbitraryListStmt();
			BlockStmt t = Trees.blockStmt().withStmts(stmts);
			Assert.assertEquals(stmts, t.stmts());
			NodeList<Stmt> stmts2 = arbitrary.arbitraryListStmt();
			BlockStmt t2 = t.withStmts(mutationBy(stmts, stmts2));
			Assert.assertEquals(stmts2, t2.stmts());
		}
	}

	@Test
	public void testBreakStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeOption<Name> id = arbitrary.arbitraryOptionName();
			BreakStmt t = Trees.breakStmt().withId(id);
			Assert.assertEquals(id, t.id());
			NodeOption<Name> id2 = arbitrary.arbitraryOptionName();
			BreakStmt t2 = t.withId(mutationBy(id, id2));
			Assert.assertEquals(id2, t2.id());
		}
	}

	@Test
	public void testCatchClause() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			FormalParameter param = arbitrary.arbitraryFormalParameter();
			BlockStmt catchBlock = arbitrary.arbitraryBlockStmt();
			CatchClause t = Trees.catchClause().withParam(param).withCatchBlock(catchBlock);
			Assert.assertEquals(param, t.param());
			Assert.assertEquals(catchBlock, t.catchBlock());
			FormalParameter param2 = arbitrary.arbitraryFormalParameter();
			BlockStmt catchBlock2 = arbitrary.arbitraryBlockStmt();
			CatchClause t2 = t.withParam(mutationBy(param, param2)).withCatchBlock(mutationBy(catchBlock, catchBlock2));
			Assert.assertEquals(param2, t2.param());
			Assert.assertEquals(catchBlock2, t2.catchBlock());
		}
	}

	@Test
	public void testContinueStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeOption<Name> id = arbitrary.arbitraryOptionName();
			ContinueStmt t = Trees.continueStmt().withId(id);
			Assert.assertEquals(id, t.id());
			NodeOption<Name> id2 = arbitrary.arbitraryOptionName();
			ContinueStmt t2 = t.withId(mutationBy(id, id2));
			Assert.assertEquals(id2, t2.id());
		}
	}

	@Test
	public void testDoStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Stmt body = arbitrary.arbitraryStmt();
			Expr condition = arbitrary.arbitraryExpr();
			DoStmt t = Trees.doStmt().withBody(body).withCondition(condition);
			Assert.assertEquals(body, t.body());
			Assert.assertEquals(condition, t.condition());
			Stmt body2 = arbitrary.arbitraryStmt();
			Expr condition2 = arbitrary.arbitraryExpr();
			DoStmt t2 = t.withBody(mutationBy(body, body2)).withCondition(mutationBy(condition, condition2));
			Assert.assertEquals(body2, t2.body());
			Assert.assertEquals(condition2, t2.condition());
		}
	}

	@Test
	public void testExplicitConstructorInvocationStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<Type> typeArgs = arbitrary.arbitraryListType();
			boolean isThis = arbitrary.arbitraryBoolean();
			NodeOption<Expr> expr = arbitrary.arbitraryOptionExpr();
			NodeList<Expr> args = arbitrary.arbitraryListExpr();
			ExplicitConstructorInvocationStmt t = Trees.explicitConstructorInvocationStmt().withTypeArgs(typeArgs).setThis(isThis).withExpr(expr).withArgs(args);
			Assert.assertEquals(typeArgs, t.typeArgs());
			Assert.assertEquals(isThis, t.isThis());
			Assert.assertEquals(expr, t.expr());
			Assert.assertEquals(args, t.args());
			NodeList<Type> typeArgs2 = arbitrary.arbitraryListType();
			boolean isThis2 = arbitrary.arbitraryBoolean();
			NodeOption<Expr> expr2 = arbitrary.arbitraryOptionExpr();
			NodeList<Expr> args2 = arbitrary.arbitraryListExpr();
			ExplicitConstructorInvocationStmt t2 = t.withTypeArgs(mutationBy(typeArgs, typeArgs2)).setThis(mutationBy(isThis, isThis2)).withExpr(mutationBy(expr, expr2)).withArgs(mutationBy(args, args2));
			Assert.assertEquals(typeArgs2, t2.typeArgs());
			Assert.assertEquals(isThis2, t2.isThis());
			Assert.assertEquals(expr2, t2.expr());
			Assert.assertEquals(args2, t2.args());
		}
	}

	@Test
	public void testExpressionStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr expr = arbitrary.arbitraryExpr();
			ExpressionStmt t = Trees.expressionStmt().withExpr(expr);
			Assert.assertEquals(expr, t.expr());
			Expr expr2 = arbitrary.arbitraryExpr();
			ExpressionStmt t2 = t.withExpr(mutationBy(expr, expr2));
			Assert.assertEquals(expr2, t2.expr());
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
			ForStmt t = Trees.forStmt().withInit(init).withCompare(compare).withUpdate(update).withBody(body);
			Assert.assertEquals(init, t.init());
			Assert.assertEquals(compare, t.compare());
			Assert.assertEquals(update, t.update());
			Assert.assertEquals(body, t.body());
			NodeList<Expr> init2 = arbitrary.arbitraryListExpr();
			Expr compare2 = arbitrary.arbitraryExpr();
			NodeList<Expr> update2 = arbitrary.arbitraryListExpr();
			Stmt body2 = arbitrary.arbitraryStmt();
			ForStmt t2 = t.withInit(mutationBy(init, init2)).withCompare(mutationBy(compare, compare2)).withUpdate(mutationBy(update, update2)).withBody(mutationBy(body, body2));
			Assert.assertEquals(init2, t2.init());
			Assert.assertEquals(compare2, t2.compare());
			Assert.assertEquals(update2, t2.update());
			Assert.assertEquals(body2, t2.body());
		}
	}

	@Test
	public void testForeachStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			VariableDeclarationExpr var = arbitrary.arbitraryVariableDeclarationExpr();
			Expr iterable = arbitrary.arbitraryExpr();
			Stmt body = arbitrary.arbitraryStmt();
			ForeachStmt t = Trees.foreachStmt().withVar(var).withIterable(iterable).withBody(body);
			Assert.assertEquals(var, t.var());
			Assert.assertEquals(iterable, t.iterable());
			Assert.assertEquals(body, t.body());
			VariableDeclarationExpr var2 = arbitrary.arbitraryVariableDeclarationExpr();
			Expr iterable2 = arbitrary.arbitraryExpr();
			Stmt body2 = arbitrary.arbitraryStmt();
			ForeachStmt t2 = t.withVar(mutationBy(var, var2)).withIterable(mutationBy(iterable, iterable2)).withBody(mutationBy(body, body2));
			Assert.assertEquals(var2, t2.var());
			Assert.assertEquals(iterable2, t2.iterable());
			Assert.assertEquals(body2, t2.body());
		}
	}

	@Test
	public void testIfStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr condition = arbitrary.arbitraryExpr();
			Stmt thenStmt = arbitrary.arbitraryStmt();
			NodeOption<Stmt> elseStmt = arbitrary.arbitraryOptionStmt();
			IfStmt t = Trees.ifStmt().withCondition(condition).withThenStmt(thenStmt).withElseStmt(elseStmt);
			Assert.assertEquals(condition, t.condition());
			Assert.assertEquals(thenStmt, t.thenStmt());
			Assert.assertEquals(elseStmt, t.elseStmt());
			Expr condition2 = arbitrary.arbitraryExpr();
			Stmt thenStmt2 = arbitrary.arbitraryStmt();
			NodeOption<Stmt> elseStmt2 = arbitrary.arbitraryOptionStmt();
			IfStmt t2 = t.withCondition(mutationBy(condition, condition2)).withThenStmt(mutationBy(thenStmt, thenStmt2)).withElseStmt(mutationBy(elseStmt, elseStmt2));
			Assert.assertEquals(condition2, t2.condition());
			Assert.assertEquals(thenStmt2, t2.thenStmt());
			Assert.assertEquals(elseStmt2, t2.elseStmt());
		}
	}

	@Test
	public void testLabeledStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Name label = arbitrary.arbitraryName();
			Stmt stmt = arbitrary.arbitraryStmt();
			LabeledStmt t = Trees.labeledStmt().withLabel(label).withStmt(stmt);
			Assert.assertEquals(label, t.label());
			Assert.assertEquals(stmt, t.stmt());
			Name label2 = arbitrary.arbitraryName();
			Stmt stmt2 = arbitrary.arbitraryStmt();
			LabeledStmt t2 = t.withLabel(mutationBy(label, label2)).withStmt(mutationBy(stmt, stmt2));
			Assert.assertEquals(label2, t2.label());
			Assert.assertEquals(stmt2, t2.stmt());
		}
	}

	@Test
	public void testReturnStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeOption<Expr> expr = arbitrary.arbitraryOptionExpr();
			ReturnStmt t = Trees.returnStmt().withExpr(expr);
			Assert.assertEquals(expr, t.expr());
			NodeOption<Expr> expr2 = arbitrary.arbitraryOptionExpr();
			ReturnStmt t2 = t.withExpr(mutationBy(expr, expr2));
			Assert.assertEquals(expr2, t2.expr());
		}
	}

	@Test
	public void testSwitchCase() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeOption<Expr> label = arbitrary.arbitraryOptionExpr();
			NodeList<Stmt> stmts = arbitrary.arbitraryListStmt();
			SwitchCase t = Trees.switchCase().withLabel(label).withStmts(stmts);
			Assert.assertEquals(label, t.label());
			Assert.assertEquals(stmts, t.stmts());
			NodeOption<Expr> label2 = arbitrary.arbitraryOptionExpr();
			NodeList<Stmt> stmts2 = arbitrary.arbitraryListStmt();
			SwitchCase t2 = t.withLabel(mutationBy(label, label2)).withStmts(mutationBy(stmts, stmts2));
			Assert.assertEquals(label2, t2.label());
			Assert.assertEquals(stmts2, t2.stmts());
		}
	}

	@Test
	public void testSwitchStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr selector = arbitrary.arbitraryExpr();
			NodeList<SwitchCase> cases = arbitrary.arbitraryListSwitchCase();
			SwitchStmt t = Trees.switchStmt().withSelector(selector).withCases(cases);
			Assert.assertEquals(selector, t.selector());
			Assert.assertEquals(cases, t.cases());
			Expr selector2 = arbitrary.arbitraryExpr();
			NodeList<SwitchCase> cases2 = arbitrary.arbitraryListSwitchCase();
			SwitchStmt t2 = t.withSelector(mutationBy(selector, selector2)).withCases(mutationBy(cases, cases2));
			Assert.assertEquals(selector2, t2.selector());
			Assert.assertEquals(cases2, t2.cases());
		}
	}

	@Test
	public void testSynchronizedStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr expr = arbitrary.arbitraryExpr();
			BlockStmt block = arbitrary.arbitraryBlockStmt();
			SynchronizedStmt t = Trees.synchronizedStmt().withExpr(expr).withBlock(block);
			Assert.assertEquals(expr, t.expr());
			Assert.assertEquals(block, t.block());
			Expr expr2 = arbitrary.arbitraryExpr();
			BlockStmt block2 = arbitrary.arbitraryBlockStmt();
			SynchronizedStmt t2 = t.withExpr(mutationBy(expr, expr2)).withBlock(mutationBy(block, block2));
			Assert.assertEquals(expr2, t2.expr());
			Assert.assertEquals(block2, t2.block());
		}
	}

	@Test
	public void testThrowStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr expr = arbitrary.arbitraryExpr();
			ThrowStmt t = Trees.throwStmt().withExpr(expr);
			Assert.assertEquals(expr, t.expr());
			Expr expr2 = arbitrary.arbitraryExpr();
			ThrowStmt t2 = t.withExpr(mutationBy(expr, expr2));
			Assert.assertEquals(expr2, t2.expr());
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
			TryStmt t = Trees.tryStmt().withResources(resources).withTrailingSemiColon(trailingSemiColon).withTryBlock(tryBlock).withCatchs(catchs).withFinallyBlock(finallyBlock);
			Assert.assertEquals(resources, t.resources());
			Assert.assertEquals(trailingSemiColon, t.trailingSemiColon());
			Assert.assertEquals(tryBlock, t.tryBlock());
			Assert.assertEquals(catchs, t.catchs());
			Assert.assertEquals(finallyBlock, t.finallyBlock());
			NodeList<VariableDeclarationExpr> resources2 = arbitrary.arbitraryListVariableDeclarationExpr();
			boolean trailingSemiColon2 = arbitrary.arbitraryBoolean();
			BlockStmt tryBlock2 = arbitrary.arbitraryBlockStmt();
			NodeList<CatchClause> catchs2 = arbitrary.arbitraryListCatchClause();
			NodeOption<BlockStmt> finallyBlock2 = arbitrary.arbitraryOptionBlockStmt();
			TryStmt t2 = t.withResources(mutationBy(resources, resources2)).withTrailingSemiColon(mutationBy(trailingSemiColon, trailingSemiColon2)).withTryBlock(mutationBy(tryBlock, tryBlock2)).withCatchs(mutationBy(catchs, catchs2)).withFinallyBlock(mutationBy(finallyBlock, finallyBlock2));
			Assert.assertEquals(resources2, t2.resources());
			Assert.assertEquals(trailingSemiColon2, t2.trailingSemiColon());
			Assert.assertEquals(tryBlock2, t2.tryBlock());
			Assert.assertEquals(catchs2, t2.catchs());
			Assert.assertEquals(finallyBlock2, t2.finallyBlock());
		}
	}

	@Test
	public void testTypeDeclarationStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			TypeDecl typeDecl = arbitrary.arbitraryTypeDecl();
			TypeDeclarationStmt t = Trees.typeDeclarationStmt().withTypeDecl(typeDecl);
			Assert.assertEquals(typeDecl, t.typeDecl());
			TypeDecl typeDecl2 = arbitrary.arbitraryTypeDecl();
			TypeDeclarationStmt t2 = t.withTypeDecl(mutationBy(typeDecl, typeDecl2));
			Assert.assertEquals(typeDecl2, t2.typeDecl());
		}
	}

	@Test
	public void testWhileStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr condition = arbitrary.arbitraryExpr();
			Stmt body = arbitrary.arbitraryStmt();
			WhileStmt t = Trees.whileStmt().withCondition(condition).withBody(body);
			Assert.assertEquals(condition, t.condition());
			Assert.assertEquals(body, t.body());
			Expr condition2 = arbitrary.arbitraryExpr();
			Stmt body2 = arbitrary.arbitraryStmt();
			WhileStmt t2 = t.withCondition(mutationBy(condition, condition2)).withBody(mutationBy(body, body2));
			Assert.assertEquals(condition2, t2.condition());
			Assert.assertEquals(body2, t2.body());
		}
	}

	@Test
	public void testArrayType() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Type componentType = arbitrary.arbitraryType();
			NodeList<ArrayDim> dims = arbitrary.arbitraryListArrayDim();
			ArrayType t = Trees.arrayType().withComponentType(componentType).withDims(dims);
			Assert.assertEquals(componentType, t.componentType());
			Assert.assertEquals(dims, t.dims());
			Type componentType2 = arbitrary.arbitraryType();
			NodeList<ArrayDim> dims2 = arbitrary.arbitraryListArrayDim();
			ArrayType t2 = t.withComponentType(mutationBy(componentType, componentType2)).withDims(mutationBy(dims, dims2));
			Assert.assertEquals(componentType2, t2.componentType());
			Assert.assertEquals(dims2, t2.dims());
		}
	}

	@Test
	public void testIntersectionType() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<Type> types = arbitrary.arbitraryListType();
			IntersectionType t = Trees.intersectionType().withTypes(types);
			Assert.assertEquals(types, t.types());
			NodeList<Type> types2 = arbitrary.arbitraryListType();
			IntersectionType t2 = t.withTypes(mutationBy(types, types2));
			Assert.assertEquals(types2, t2.types());
		}
	}

	@Test
	public void testPrimitiveType() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<AnnotationExpr> annotations = arbitrary.arbitraryListAnnotationExpr();
			Primitive primitive = arbitrary.arbitraryPrimitive();
			PrimitiveType t = Trees.primitiveType().withAnnotations(annotations).withPrimitive(primitive);
			Assert.assertEquals(annotations, t.annotations());
			Assert.assertEquals(primitive, t.primitive());
			NodeList<AnnotationExpr> annotations2 = arbitrary.arbitraryListAnnotationExpr();
			Primitive primitive2 = arbitrary.arbitraryPrimitive();
			PrimitiveType t2 = t.withAnnotations(mutationBy(annotations, annotations2)).withPrimitive(mutationBy(primitive, primitive2));
			Assert.assertEquals(annotations2, t2.annotations());
			Assert.assertEquals(primitive2, t2.primitive());
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
			QualifiedType t = Trees.qualifiedType().withAnnotations(annotations).withScope(scope).withName(name).withTypeArgs(typeArgs);
			Assert.assertEquals(annotations, t.annotations());
			Assert.assertEquals(scope, t.scope());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(typeArgs, t.typeArgs());
			NodeList<AnnotationExpr> annotations2 = arbitrary.arbitraryListAnnotationExpr();
			NodeOption<QualifiedType> scope2 = arbitrary.arbitraryOptionQualifiedType();
			Name name2 = arbitrary.arbitraryName();
			NodeOption<NodeList<Type>> typeArgs2 = arbitrary.arbitraryOptionListType();
			QualifiedType t2 = t.withAnnotations(mutationBy(annotations, annotations2)).withScope(mutationBy(scope, scope2)).withName(mutationBy(name, name2)).withTypeArgs(mutationBy(typeArgs, typeArgs2));
			Assert.assertEquals(annotations2, t2.annotations());
			Assert.assertEquals(scope2, t2.scope());
			Assert.assertEquals(name2, t2.name());
			Assert.assertEquals(typeArgs2, t2.typeArgs());
		}
	}

	@Test
	public void testUnionType() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<Type> types = arbitrary.arbitraryListType();
			UnionType t = Trees.unionType().withTypes(types);
			Assert.assertEquals(types, t.types());
			NodeList<Type> types2 = arbitrary.arbitraryListType();
			UnionType t2 = t.withTypes(mutationBy(types, types2));
			Assert.assertEquals(types2, t2.types());
		}
	}

	@Test
	public void testWildcardType() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<AnnotationExpr> annotations = arbitrary.arbitraryListAnnotationExpr();
			NodeOption<ReferenceType> ext = arbitrary.arbitraryOptionReferenceType();
			NodeOption<ReferenceType> sup = arbitrary.arbitraryOptionReferenceType();
			WildcardType t = Trees.wildcardType().withAnnotations(annotations).withExt(ext).withSup(sup);
			Assert.assertEquals(annotations, t.annotations());
			Assert.assertEquals(ext, t.ext());
			Assert.assertEquals(sup, t.sup());
			NodeList<AnnotationExpr> annotations2 = arbitrary.arbitraryListAnnotationExpr();
			NodeOption<ReferenceType> ext2 = arbitrary.arbitraryOptionReferenceType();
			NodeOption<ReferenceType> sup2 = arbitrary.arbitraryOptionReferenceType();
			WildcardType t2 = t.withAnnotations(mutationBy(annotations, annotations2)).withExt(mutationBy(ext, ext2)).withSup(mutationBy(sup, sup2));
			Assert.assertEquals(annotations2, t2.annotations());
			Assert.assertEquals(ext2, t2.ext());
			Assert.assertEquals(sup2, t2.sup());
		}
	}

	private <T> Mutation<T> mutationBy(final T before, final T after) {
		return new Mutation<T>() {

			public T mutate(final T t) {
				Assert.assertEquals(before, t);
				return after;
			}
		};
	}
}
