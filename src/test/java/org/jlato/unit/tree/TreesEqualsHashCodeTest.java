package org.jlato.unit.tree;

import org.jlato.tree.NodeEither;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.decl.*;
import org.jlato.tree.expr.*;
import org.jlato.tree.name.*;
import org.jlato.tree.stmt.*;
import org.jlato.tree.type.*;
import org.jlato.unit.util.Arbitrary;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.jlato.tree.TreeFactory.*;

@RunWith(JUnit4.class)
public class TreesEqualsHashCodeTest {

	@Test
	public void testAnnotationDecl() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<ExtendedModifier> modifiers = arbitrary.arbitraryListExtendedModifier();
			Name name = arbitrary.arbitraryName();
			NodeList<MemberDecl> members = arbitrary.arbitraryListMemberDecl();
			AnnotationDecl expected = annotationDecl().withModifiers(modifiers).withName(name).withMembers(members);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			AnnotationDecl actual = annotationDecl();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withModifiers(modifiers);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withName(name);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withMembers(members);
			Assert.assertTrue(expected.equals(actual));
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
			AnnotationMemberDecl expected = annotationMemberDecl().withModifiers(modifiers).withType(type).withName(name).withDims(dims).withDefaultValue(defaultValue);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			AnnotationMemberDecl actual = annotationMemberDecl();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withModifiers(modifiers);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withType(type);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withName(name);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withDims(dims);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withDefaultValue(defaultValue);
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testArrayDim() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<AnnotationExpr> annotations = arbitrary.arbitraryListAnnotationExpr();
			ArrayDim expected = arrayDim().withAnnotations(annotations);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			ArrayDim actual = arrayDim();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withAnnotations(annotations);
			Assert.assertTrue(expected.equals(actual));
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
			ClassDecl expected = classDecl().withModifiers(modifiers).withName(name).withTypeParams(typeParams).withExtendsClause(extendsClause).withImplementsClause(implementsClause).withMembers(members);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			ClassDecl actual = classDecl();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withModifiers(modifiers);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withName(name);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withTypeParams(typeParams);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withExtendsClause(extendsClause);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withImplementsClause(implementsClause);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withMembers(members);
			Assert.assertTrue(expected.equals(actual));
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
			CompilationUnit expected = compilationUnit().withPackageDecl(packageDecl).withImports(imports).withTypes(types);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			CompilationUnit actual = compilationUnit();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withPackageDecl(packageDecl);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withImports(imports);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withTypes(types);
			Assert.assertTrue(expected.equals(actual));
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
			ConstructorDecl expected = constructorDecl().withModifiers(modifiers).withTypeParams(typeParams).withName(name).withParams(params).withThrowsClause(throwsClause).withBody(body);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			ConstructorDecl actual = constructorDecl();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withModifiers(modifiers);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withTypeParams(typeParams);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withName(name);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withParams(params);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withThrowsClause(throwsClause);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withBody(body);
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testEmptyMemberDecl() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			EmptyMemberDecl expected = emptyMemberDecl();
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			EmptyMemberDecl actual = emptyMemberDecl();
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testEmptyTypeDecl() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			EmptyTypeDecl expected = emptyTypeDecl();
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			EmptyTypeDecl actual = emptyTypeDecl();
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
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
			EnumConstantDecl expected = enumConstantDecl().withModifiers(modifiers).withName(name).withArgs(args).withClassBody(classBody);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			EnumConstantDecl actual = enumConstantDecl();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withModifiers(modifiers);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withName(name);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withArgs(args);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withClassBody(classBody);
			Assert.assertTrue(expected.equals(actual));
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
			EnumDecl expected = enumDecl().withModifiers(modifiers).withName(name).withImplementsClause(implementsClause).withEnumConstants(enumConstants).withTrailingComma(trailingComma).withMembers(members);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			EnumDecl actual = enumDecl();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withModifiers(modifiers);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withName(name);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withImplementsClause(implementsClause);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withEnumConstants(enumConstants);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withTrailingComma(trailingComma);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withMembers(members);
			Assert.assertTrue(expected.equals(actual));
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
			FieldDecl expected = fieldDecl().withModifiers(modifiers).withType(type).withVariables(variables);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			FieldDecl actual = fieldDecl();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withModifiers(modifiers);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withType(type);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withVariables(variables);
			Assert.assertTrue(expected.equals(actual));
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
			VariableDeclaratorId id = arbitrary.arbitraryVariableDeclaratorId();
			FormalParameter expected = formalParameter().withModifiers(modifiers).withType(type).setVarArgs(isVarArgs).withId(id);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			FormalParameter actual = formalParameter();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withModifiers(modifiers);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withType(type);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.setVarArgs(isVarArgs);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withId(id);
			Assert.assertTrue(expected.equals(actual));
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
			ImportDecl expected = importDecl().withName(name).setStatic(isStatic).setOnDemand(isOnDemand);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			ImportDecl actual = importDecl();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withName(name);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.setStatic(isStatic);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.setOnDemand(isOnDemand);
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testInitializerDecl() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<ExtendedModifier> modifiers = arbitrary.arbitraryListExtendedModifier();
			BlockStmt body = arbitrary.arbitraryBlockStmt();
			InitializerDecl expected = initializerDecl().withModifiers(modifiers).withBody(body);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			InitializerDecl actual = initializerDecl();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withModifiers(modifiers);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withBody(body);
			Assert.assertTrue(expected.equals(actual));
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
			InterfaceDecl expected = interfaceDecl().withModifiers(modifiers).withName(name).withTypeParams(typeParams).withExtendsClause(extendsClause).withMembers(members);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			InterfaceDecl actual = interfaceDecl();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withModifiers(modifiers);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withName(name);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withTypeParams(typeParams);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withExtendsClause(extendsClause);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withMembers(members);
			Assert.assertTrue(expected.equals(actual));
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
			LocalVariableDecl expected = localVariableDecl().withModifiers(modifiers).withType(type).withVariables(variables);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			LocalVariableDecl actual = localVariableDecl();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withModifiers(modifiers);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withType(type);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withVariables(variables);
			Assert.assertTrue(expected.equals(actual));
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
			MethodDecl expected = methodDecl().withModifiers(modifiers).withTypeParams(typeParams).withType(type).withName(name).withParams(params).withDims(dims).withThrowsClause(throwsClause).withBody(body);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			MethodDecl actual = methodDecl();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withModifiers(modifiers);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withTypeParams(typeParams);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withType(type);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withName(name);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withParams(params);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withDims(dims);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withThrowsClause(throwsClause);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withBody(body);
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testPackageDecl() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<AnnotationExpr> annotations = arbitrary.arbitraryListAnnotationExpr();
			QualifiedName name = arbitrary.arbitraryQualifiedName();
			PackageDecl expected = packageDecl().withAnnotations(annotations).withName(name);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			PackageDecl actual = packageDecl();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withAnnotations(annotations);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withName(name);
			Assert.assertTrue(expected.equals(actual));
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
			TypeParameter expected = typeParameter().withAnnotations(annotations).withName(name).withBounds(bounds);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			TypeParameter actual = typeParameter();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withAnnotations(annotations);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withName(name);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withBounds(bounds);
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testVariableDeclarator() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			VariableDeclaratorId id = arbitrary.arbitraryVariableDeclaratorId();
			NodeOption<Expr> init = arbitrary.arbitraryOptionExpr();
			VariableDeclarator expected = variableDeclarator().withId(id).withInit(init);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			VariableDeclarator actual = variableDeclarator();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withId(id);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withInit(init);
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testVariableDeclaratorId() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Name name = arbitrary.arbitraryName();
			NodeList<ArrayDim> dims = arbitrary.arbitraryListArrayDim();
			VariableDeclaratorId expected = variableDeclaratorId().withName(name).withDims(dims);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			VariableDeclaratorId actual = variableDeclaratorId();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withName(name);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withDims(dims);
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testArrayAccessExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr name = arbitrary.arbitraryExpr();
			Expr index = arbitrary.arbitraryExpr();
			ArrayAccessExpr expected = arrayAccessExpr().withName(name).withIndex(index);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			ArrayAccessExpr actual = arrayAccessExpr();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withName(name);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withIndex(index);
			Assert.assertTrue(expected.equals(actual));
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
			ArrayCreationExpr expected = arrayCreationExpr().withType(type).withDimExprs(dimExprs).withDims(dims).withInit(init);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			ArrayCreationExpr actual = arrayCreationExpr();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withType(type);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withDimExprs(dimExprs);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withDims(dims);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withInit(init);
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testArrayDimExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<AnnotationExpr> annotations = arbitrary.arbitraryListAnnotationExpr();
			Expr expr = arbitrary.arbitraryExpr();
			ArrayDimExpr expected = arrayDimExpr().withAnnotations(annotations).withExpr(expr);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			ArrayDimExpr actual = arrayDimExpr();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withAnnotations(annotations);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withExpr(expr);
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testArrayInitializerExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<Expr> values = arbitrary.arbitraryListExpr();
			boolean trailingComma = arbitrary.arbitraryBoolean();
			ArrayInitializerExpr expected = arrayInitializerExpr().withValues(values).withTrailingComma(trailingComma);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			ArrayInitializerExpr actual = arrayInitializerExpr();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withValues(values);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withTrailingComma(trailingComma);
			Assert.assertTrue(expected.equals(actual));
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
			AssignExpr expected = assignExpr().withTarget(target).withOp(op).withValue(value);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			AssignExpr actual = assignExpr();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withTarget(target);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withOp(op);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withValue(value);
			Assert.assertTrue(expected.equals(actual));
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
			BinaryExpr expected = binaryExpr().withLeft(left).withOp(op).withRight(right);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			BinaryExpr actual = binaryExpr();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withLeft(left);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withOp(op);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withRight(right);
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testCastExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Type type = arbitrary.arbitraryType();
			Expr expr = arbitrary.arbitraryExpr();
			CastExpr expected = castExpr().withType(type).withExpr(expr);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			CastExpr actual = castExpr();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withType(type);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withExpr(expr);
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testClassExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Type type = arbitrary.arbitraryType();
			ClassExpr expected = classExpr().withType(type);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			ClassExpr actual = classExpr();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withType(type);
			Assert.assertTrue(expected.equals(actual));
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
			ConditionalExpr expected = conditionalExpr().withCondition(condition).withThenExpr(thenExpr).withElseExpr(elseExpr);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			ConditionalExpr actual = conditionalExpr();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withCondition(condition);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withThenExpr(thenExpr);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withElseExpr(elseExpr);
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testFieldAccessExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeOption<Expr> scope = arbitrary.arbitraryOptionExpr();
			Name name = arbitrary.arbitraryName();
			FieldAccessExpr expected = fieldAccessExpr().withScope(scope).withName(name);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			FieldAccessExpr actual = fieldAccessExpr();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withScope(scope);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withName(name);
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testInstanceOfExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr expr = arbitrary.arbitraryExpr();
			Type type = arbitrary.arbitraryType();
			InstanceOfExpr expected = instanceOfExpr().withExpr(expr).withType(type);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			InstanceOfExpr actual = instanceOfExpr();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withExpr(expr);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withType(type);
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testLambdaExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<FormalParameter> params = arbitrary.arbitraryListFormalParameter();
			boolean hasParens = arbitrary.arbitraryBoolean();
			NodeEither<Expr, BlockStmt> body = arbitrary.arbitraryEitherExprBlockStmt();
			LambdaExpr expected = lambdaExpr().withParams(params).setParens(hasParens).withBody(body);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			LambdaExpr actual = lambdaExpr();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withParams(params);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.setParens(hasParens);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withBody(body);
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testMarkerAnnotationExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			QualifiedName name = arbitrary.arbitraryQualifiedName();
			MarkerAnnotationExpr expected = markerAnnotationExpr().withName(name);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			MarkerAnnotationExpr actual = markerAnnotationExpr();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withName(name);
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testMemberValuePair() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Name name = arbitrary.arbitraryName();
			Expr value = arbitrary.arbitraryExpr();
			MemberValuePair expected = memberValuePair().withName(name).withValue(value);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			MemberValuePair actual = memberValuePair();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withName(name);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withValue(value);
			Assert.assertTrue(expected.equals(actual));
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
			MethodInvocationExpr expected = methodInvocationExpr().withScope(scope).withTypeArgs(typeArgs).withName(name).withArgs(args);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			MethodInvocationExpr actual = methodInvocationExpr();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withScope(scope);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withTypeArgs(typeArgs);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withName(name);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withArgs(args);
			Assert.assertTrue(expected.equals(actual));
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
			MethodReferenceExpr expected = methodReferenceExpr().withScope(scope).withTypeArgs(typeArgs).withName(name);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			MethodReferenceExpr actual = methodReferenceExpr();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withScope(scope);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withTypeArgs(typeArgs);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withName(name);
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testNormalAnnotationExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			QualifiedName name = arbitrary.arbitraryQualifiedName();
			NodeList<MemberValuePair> pairs = arbitrary.arbitraryListMemberValuePair();
			NormalAnnotationExpr expected = normalAnnotationExpr().withName(name).withPairs(pairs);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			NormalAnnotationExpr actual = normalAnnotationExpr();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withName(name);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withPairs(pairs);
			Assert.assertTrue(expected.equals(actual));
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
			ObjectCreationExpr expected = objectCreationExpr().withScope(scope).withTypeArgs(typeArgs).withType(type).withArgs(args).withBody(body);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			ObjectCreationExpr actual = objectCreationExpr();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withScope(scope);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withTypeArgs(typeArgs);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withType(type);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withArgs(args);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withBody(body);
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testParenthesizedExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr inner = arbitrary.arbitraryExpr();
			ParenthesizedExpr expected = parenthesizedExpr().withInner(inner);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			ParenthesizedExpr actual = parenthesizedExpr();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withInner(inner);
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testSingleMemberAnnotationExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			QualifiedName name = arbitrary.arbitraryQualifiedName();
			Expr memberValue = arbitrary.arbitraryExpr();
			SingleMemberAnnotationExpr expected = singleMemberAnnotationExpr().withName(name).withMemberValue(memberValue);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			SingleMemberAnnotationExpr actual = singleMemberAnnotationExpr();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withName(name);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withMemberValue(memberValue);
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testSuperExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeOption<Expr> classExpr = arbitrary.arbitraryOptionExpr();
			SuperExpr expected = superExpr().withClassExpr(classExpr);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			SuperExpr actual = superExpr();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withClassExpr(classExpr);
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testThisExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeOption<Expr> classExpr = arbitrary.arbitraryOptionExpr();
			ThisExpr expected = thisExpr().withClassExpr(classExpr);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			ThisExpr actual = thisExpr();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withClassExpr(classExpr);
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testTypeExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Type type = arbitrary.arbitraryType();
			TypeExpr expected = typeExpr().withType(type);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			TypeExpr actual = typeExpr();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withType(type);
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testUnaryExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			UnaryOp op = arbitrary.arbitraryUnaryOp();
			Expr expr = arbitrary.arbitraryExpr();
			UnaryExpr expected = unaryExpr().withOp(op).withExpr(expr);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			UnaryExpr actual = unaryExpr();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withOp(op);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withExpr(expr);
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testVariableDeclarationExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			LocalVariableDecl declaration = arbitrary.arbitraryLocalVariableDecl();
			VariableDeclarationExpr expected = variableDeclarationExpr().withDeclaration(declaration);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			VariableDeclarationExpr actual = variableDeclarationExpr();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withDeclaration(declaration);
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testName() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			String id = arbitrary.arbitraryString();
			Name expected = name().withId(id);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			Name actual = name();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withId(id);
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testQualifiedName() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeOption<QualifiedName> qualifier = arbitrary.arbitraryOptionQualifiedName();
			Name name = arbitrary.arbitraryName();
			QualifiedName expected = qualifiedName().withQualifier(qualifier).withName(name);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			QualifiedName actual = qualifiedName();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withQualifier(qualifier);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withName(name);
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testAssertStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr check = arbitrary.arbitraryExpr();
			NodeOption<Expr> msg = arbitrary.arbitraryOptionExpr();
			AssertStmt expected = assertStmt().withCheck(check).withMsg(msg);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			AssertStmt actual = assertStmt();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withCheck(check);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withMsg(msg);
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testBlockStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<Stmt> stmts = arbitrary.arbitraryListStmt();
			BlockStmt expected = blockStmt().withStmts(stmts);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			BlockStmt actual = blockStmt();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withStmts(stmts);
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testBreakStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeOption<Name> id = arbitrary.arbitraryOptionName();
			BreakStmt expected = breakStmt().withId(id);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			BreakStmt actual = breakStmt();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withId(id);
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testCatchClause() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			FormalParameter except = arbitrary.arbitraryFormalParameter();
			BlockStmt catchBlock = arbitrary.arbitraryBlockStmt();
			CatchClause expected = catchClause().withExcept(except).withCatchBlock(catchBlock);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			CatchClause actual = catchClause();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withExcept(except);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withCatchBlock(catchBlock);
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testContinueStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeOption<Name> id = arbitrary.arbitraryOptionName();
			ContinueStmt expected = continueStmt().withId(id);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			ContinueStmt actual = continueStmt();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withId(id);
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testDoStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Stmt body = arbitrary.arbitraryStmt();
			Expr condition = arbitrary.arbitraryExpr();
			DoStmt expected = doStmt().withBody(body).withCondition(condition);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			DoStmt actual = doStmt();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withBody(body);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withCondition(condition);
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testEmptyStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			EmptyStmt expected = emptyStmt();
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			EmptyStmt actual = emptyStmt();
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
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
			ExplicitConstructorInvocationStmt expected = explicitConstructorInvocationStmt().withTypeArgs(typeArgs).setThis(isThis).withExpr(expr).withArgs(args);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			ExplicitConstructorInvocationStmt actual = explicitConstructorInvocationStmt();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withTypeArgs(typeArgs);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.setThis(isThis);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withExpr(expr);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withArgs(args);
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testExpressionStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr expr = arbitrary.arbitraryExpr();
			ExpressionStmt expected = expressionStmt().withExpr(expr);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			ExpressionStmt actual = expressionStmt();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withExpr(expr);
			Assert.assertTrue(expected.equals(actual));
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
			ForStmt expected = forStmt().withInit(init).withCompare(compare).withUpdate(update).withBody(body);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			ForStmt actual = forStmt();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withInit(init);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withCompare(compare);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withUpdate(update);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withBody(body);
			Assert.assertTrue(expected.equals(actual));
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
			ForeachStmt expected = foreachStmt().withVar(var).withIterable(iterable).withBody(body);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			ForeachStmt actual = foreachStmt();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withVar(var);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withIterable(iterable);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withBody(body);
			Assert.assertTrue(expected.equals(actual));
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
			IfStmt expected = ifStmt().withCondition(condition).withThenStmt(thenStmt).withElseStmt(elseStmt);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			IfStmt actual = ifStmt();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withCondition(condition);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withThenStmt(thenStmt);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withElseStmt(elseStmt);
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testLabeledStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Name label = arbitrary.arbitraryName();
			Stmt stmt = arbitrary.arbitraryStmt();
			LabeledStmt expected = labeledStmt().withLabel(label).withStmt(stmt);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			LabeledStmt actual = labeledStmt();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withLabel(label);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withStmt(stmt);
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testReturnStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeOption<Expr> expr = arbitrary.arbitraryOptionExpr();
			ReturnStmt expected = returnStmt().withExpr(expr);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			ReturnStmt actual = returnStmt();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withExpr(expr);
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testSwitchCase() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeOption<Expr> label = arbitrary.arbitraryOptionExpr();
			NodeList<Stmt> stmts = arbitrary.arbitraryListStmt();
			SwitchCase expected = switchCase().withLabel(label).withStmts(stmts);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			SwitchCase actual = switchCase();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withLabel(label);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withStmts(stmts);
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testSwitchStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr selector = arbitrary.arbitraryExpr();
			NodeList<SwitchCase> cases = arbitrary.arbitraryListSwitchCase();
			SwitchStmt expected = switchStmt().withSelector(selector).withCases(cases);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			SwitchStmt actual = switchStmt();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withSelector(selector);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withCases(cases);
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testSynchronizedStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr expr = arbitrary.arbitraryExpr();
			BlockStmt block = arbitrary.arbitraryBlockStmt();
			SynchronizedStmt expected = synchronizedStmt().withExpr(expr).withBlock(block);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			SynchronizedStmt actual = synchronizedStmt();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withExpr(expr);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withBlock(block);
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testThrowStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr expr = arbitrary.arbitraryExpr();
			ThrowStmt expected = throwStmt().withExpr(expr);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			ThrowStmt actual = throwStmt();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withExpr(expr);
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testTryStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<VariableDeclarationExpr> resources = arbitrary.arbitraryListVariableDeclarationExpr();
			BlockStmt tryBlock = arbitrary.arbitraryBlockStmt();
			NodeList<CatchClause> catchs = arbitrary.arbitraryListCatchClause();
			NodeOption<BlockStmt> finallyBlock = arbitrary.arbitraryOptionBlockStmt();
			TryStmt expected = tryStmt().withResources(resources).withTryBlock(tryBlock).withCatchs(catchs).withFinallyBlock(finallyBlock);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			TryStmt actual = tryStmt();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withResources(resources);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withTryBlock(tryBlock);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withCatchs(catchs);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withFinallyBlock(finallyBlock);
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testTypeDeclarationStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			TypeDecl typeDecl = arbitrary.arbitraryTypeDecl();
			TypeDeclarationStmt expected = typeDeclarationStmt().withTypeDecl(typeDecl);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			TypeDeclarationStmt actual = typeDeclarationStmt();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withTypeDecl(typeDecl);
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testWhileStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr condition = arbitrary.arbitraryExpr();
			Stmt body = arbitrary.arbitraryStmt();
			WhileStmt expected = whileStmt().withCondition(condition).withBody(body);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			WhileStmt actual = whileStmt();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withCondition(condition);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withBody(body);
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testArrayType() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Type componentType = arbitrary.arbitraryType();
			NodeList<ArrayDim> dims = arbitrary.arbitraryListArrayDim();
			ArrayType expected = arrayType().withComponentType(componentType).withDims(dims);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			ArrayType actual = arrayType();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withComponentType(componentType);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withDims(dims);
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testIntersectionType() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<Type> types = arbitrary.arbitraryListType();
			IntersectionType expected = intersectionType().withTypes(types);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			IntersectionType actual = intersectionType();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withTypes(types);
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testPrimitiveType() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<AnnotationExpr> annotations = arbitrary.arbitraryListAnnotationExpr();
			Primitive primitive = arbitrary.arbitraryPrimitive();
			PrimitiveType expected = primitiveType().withAnnotations(annotations).withPrimitive(primitive);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			PrimitiveType actual = primitiveType();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withAnnotations(annotations);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withPrimitive(primitive);
			Assert.assertTrue(expected.equals(actual));
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
			QualifiedType expected = qualifiedType().withAnnotations(annotations).withScope(scope).withName(name).withTypeArgs(typeArgs);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			QualifiedType actual = qualifiedType();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withAnnotations(annotations);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withScope(scope);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withName(name);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withTypeArgs(typeArgs);
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testUnionType() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<Type> types = arbitrary.arbitraryListType();
			UnionType expected = unionType().withTypes(types);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			UnionType actual = unionType();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withTypes(types);
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testUnknownType() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			UnknownType expected = unknownType();
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			UnknownType actual = unknownType();
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testVoidType() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			VoidType expected = voidType();
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			VoidType actual = voidType();
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	@Test
	public void testWildcardType() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<AnnotationExpr> annotations = arbitrary.arbitraryListAnnotationExpr();
			NodeOption<ReferenceType> ext = arbitrary.arbitraryOptionReferenceType();
			NodeOption<ReferenceType> sup = arbitrary.arbitraryOptionReferenceType();
			WildcardType expected = wildcardType().withAnnotations(annotations).withExt(ext).withSup(sup);
			Assert.assertTrue(expected.equals(expected));
			Assert.assertFalse(expected.equals(null));
			WildcardType actual = wildcardType();
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withAnnotations(annotations);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withExt(ext);
			Assert.assertFalse(expected.equals(actual));
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
			actual = actual.withSup(sup);
			Assert.assertTrue(expected.equals(actual));
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}
}
