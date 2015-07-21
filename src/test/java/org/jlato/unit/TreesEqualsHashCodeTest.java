package org.jlato.unit;

import org.jlato.tree.*;
import org.jlato.tree.decl.*;
import org.jlato.tree.expr.*;
import org.jlato.tree.expr.AssignExpr.AssignOp;
import org.jlato.tree.expr.BinaryExpr.BinaryOp;
import org.jlato.tree.expr.UnaryExpr.UnaryOp;
import org.jlato.tree.name.*;
import org.jlato.tree.stmt.*;
import org.jlato.tree.type.*;
import org.jlato.tree.type.PrimitiveType.Primitive;
import static org.jlato.tree.TreeFactory.*;
import org.jlato.unit.util.Arbitrary;
import org.junit.*;
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
			AnnotationDecl t = annotationDecl().withModifiers(modifiers).withName(name).withMembers(members);
			AnnotationDecl t2 = annotationDecl().withModifiers(modifiers).withName(name).withMembers(members);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
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
			AnnotationMemberDecl t = annotationMemberDecl().withModifiers(modifiers).withType(type).withName(name).withDims(dims).withDefaultValue(defaultValue);
			AnnotationMemberDecl t2 = annotationMemberDecl().withModifiers(modifiers).withType(type).withName(name).withDims(dims).withDefaultValue(defaultValue);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testArrayDim() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<AnnotationExpr> annotations = arbitrary.arbitraryListAnnotationExpr();
			ArrayDim t = arrayDim().withAnnotations(annotations);
			ArrayDim t2 = arrayDim().withAnnotations(annotations);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
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
			ClassDecl t = classDecl().withModifiers(modifiers).withName(name).withTypeParams(typeParams).withExtendsClause(extendsClause).withImplementsClause(implementsClause).withMembers(members);
			ClassDecl t2 = classDecl().withModifiers(modifiers).withName(name).withTypeParams(typeParams).withExtendsClause(extendsClause).withImplementsClause(implementsClause).withMembers(members);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testCompilationUnit() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			PackageDecl packageDecl = arbitrary.arbitraryPackageDecl();
			NodeList<ImportDecl> imports = arbitrary.arbitraryListImportDecl();
			NodeList<TypeDecl> types = arbitrary.arbitraryListTypeDecl();
			CompilationUnit t = compilationUnit().withPackageDecl(packageDecl).withImports(imports).withTypes(types);
			CompilationUnit t2 = compilationUnit().withPackageDecl(packageDecl).withImports(imports).withTypes(types);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
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
			ConstructorDecl t = constructorDecl().withModifiers(modifiers).withTypeParams(typeParams).withName(name).withParams(params).withThrowsClause(throwsClause).withBody(body);
			ConstructorDecl t2 = constructorDecl().withModifiers(modifiers).withTypeParams(typeParams).withName(name).withParams(params).withThrowsClause(throwsClause).withBody(body);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testEmptyMemberDecl() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			EmptyMemberDecl t = emptyMemberDecl();
			EmptyMemberDecl t2 = emptyMemberDecl();
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testEmptyTypeDecl() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			EmptyTypeDecl t = emptyTypeDecl();
			EmptyTypeDecl t2 = emptyTypeDecl();
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
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
			EnumConstantDecl t = enumConstantDecl().withModifiers(modifiers).withName(name).withArgs(args).withClassBody(classBody);
			EnumConstantDecl t2 = enumConstantDecl().withModifiers(modifiers).withName(name).withArgs(args).withClassBody(classBody);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
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
			EnumDecl t = enumDecl().withModifiers(modifiers).withName(name).withImplementsClause(implementsClause).withEnumConstants(enumConstants).withTrailingComma(trailingComma).withMembers(members);
			EnumDecl t2 = enumDecl().withModifiers(modifiers).withName(name).withImplementsClause(implementsClause).withEnumConstants(enumConstants).withTrailingComma(trailingComma).withMembers(members);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testFieldDecl() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<ExtendedModifier> modifiers = arbitrary.arbitraryListExtendedModifier();
			Type type = arbitrary.arbitraryType();
			NodeList<VariableDeclarator> variables = arbitrary.arbitraryListVariableDeclarator();
			FieldDecl t = fieldDecl().withModifiers(modifiers).withType(type).withVariables(variables);
			FieldDecl t2 = fieldDecl().withModifiers(modifiers).withType(type).withVariables(variables);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
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
			FormalParameter t = formalParameter().withModifiers(modifiers).withType(type).setVarArgs(isVarArgs).withId(id);
			FormalParameter t2 = formalParameter().withModifiers(modifiers).withType(type).setVarArgs(isVarArgs).withId(id);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testImportDecl() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			QualifiedName name = arbitrary.arbitraryQualifiedName();
			boolean isStatic = arbitrary.arbitraryBoolean();
			boolean isOnDemand = arbitrary.arbitraryBoolean();
			ImportDecl t = importDecl().withName(name).setStatic(isStatic).setOnDemand(isOnDemand);
			ImportDecl t2 = importDecl().withName(name).setStatic(isStatic).setOnDemand(isOnDemand);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testInitializerDecl() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<ExtendedModifier> modifiers = arbitrary.arbitraryListExtendedModifier();
			BlockStmt body = arbitrary.arbitraryBlockStmt();
			InitializerDecl t = initializerDecl().withModifiers(modifiers).withBody(body);
			InitializerDecl t2 = initializerDecl().withModifiers(modifiers).withBody(body);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
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
			InterfaceDecl t = interfaceDecl().withModifiers(modifiers).withName(name).withTypeParams(typeParams).withExtendsClause(extendsClause).withMembers(members);
			InterfaceDecl t2 = interfaceDecl().withModifiers(modifiers).withName(name).withTypeParams(typeParams).withExtendsClause(extendsClause).withMembers(members);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testLocalVariableDecl() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<ExtendedModifier> modifiers = arbitrary.arbitraryListExtendedModifier();
			Type type = arbitrary.arbitraryType();
			NodeList<VariableDeclarator> variables = arbitrary.arbitraryListVariableDeclarator();
			LocalVariableDecl t = localVariableDecl().withModifiers(modifiers).withType(type).withVariables(variables);
			LocalVariableDecl t2 = localVariableDecl().withModifiers(modifiers).withType(type).withVariables(variables);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
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
			MethodDecl t = methodDecl().withModifiers(modifiers).withTypeParams(typeParams).withType(type).withName(name).withParams(params).withDims(dims).withThrowsClause(throwsClause).withBody(body);
			MethodDecl t2 = methodDecl().withModifiers(modifiers).withTypeParams(typeParams).withType(type).withName(name).withParams(params).withDims(dims).withThrowsClause(throwsClause).withBody(body);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testPackageDecl() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<AnnotationExpr> annotations = arbitrary.arbitraryListAnnotationExpr();
			QualifiedName name = arbitrary.arbitraryQualifiedName();
			PackageDecl t = packageDecl().withAnnotations(annotations).withName(name);
			PackageDecl t2 = packageDecl().withAnnotations(annotations).withName(name);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testTypeParameter() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<AnnotationExpr> annotations = arbitrary.arbitraryListAnnotationExpr();
			Name name = arbitrary.arbitraryName();
			NodeList<Type> bounds = arbitrary.arbitraryListType();
			TypeParameter t = typeParameter().withAnnotations(annotations).withName(name).withBounds(bounds);
			TypeParameter t2 = typeParameter().withAnnotations(annotations).withName(name).withBounds(bounds);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testVariableDeclarator() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			VariableDeclaratorId id = arbitrary.arbitraryVariableDeclaratorId();
			NodeOption<Expr> init = arbitrary.arbitraryOptionExpr();
			VariableDeclarator t = variableDeclarator().withId(id).withInit(init);
			VariableDeclarator t2 = variableDeclarator().withId(id).withInit(init);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testVariableDeclaratorId() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Name name = arbitrary.arbitraryName();
			NodeList<ArrayDim> dims = arbitrary.arbitraryListArrayDim();
			VariableDeclaratorId t = variableDeclaratorId().withName(name).withDims(dims);
			VariableDeclaratorId t2 = variableDeclaratorId().withName(name).withDims(dims);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testArrayAccessExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr name = arbitrary.arbitraryExpr();
			Expr index = arbitrary.arbitraryExpr();
			ArrayAccessExpr t = arrayAccessExpr().withName(name).withIndex(index);
			ArrayAccessExpr t2 = arrayAccessExpr().withName(name).withIndex(index);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
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
			ArrayCreationExpr t = arrayCreationExpr().withType(type).withDimExprs(dimExprs).withDims(dims).withInit(init);
			ArrayCreationExpr t2 = arrayCreationExpr().withType(type).withDimExprs(dimExprs).withDims(dims).withInit(init);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testArrayDimExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<AnnotationExpr> annotations = arbitrary.arbitraryListAnnotationExpr();
			Expr expr = arbitrary.arbitraryExpr();
			ArrayDimExpr t = arrayDimExpr().withAnnotations(annotations).withExpr(expr);
			ArrayDimExpr t2 = arrayDimExpr().withAnnotations(annotations).withExpr(expr);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testArrayInitializerExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<Expr> values = arbitrary.arbitraryListExpr();
			ArrayInitializerExpr t = arrayInitializerExpr().withValues(values);
			ArrayInitializerExpr t2 = arrayInitializerExpr().withValues(values);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testAssignExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr target = arbitrary.arbitraryExpr();
			AssignOp op = arbitrary.arbitraryAssignOp();
			Expr value = arbitrary.arbitraryExpr();
			AssignExpr t = assignExpr().withTarget(target).withOp(op).withValue(value);
			AssignExpr t2 = assignExpr().withTarget(target).withOp(op).withValue(value);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testBinaryExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr left = arbitrary.arbitraryExpr();
			BinaryOp op = arbitrary.arbitraryBinaryOp();
			Expr right = arbitrary.arbitraryExpr();
			BinaryExpr t = binaryExpr().withLeft(left).withOp(op).withRight(right);
			BinaryExpr t2 = binaryExpr().withLeft(left).withOp(op).withRight(right);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testCastExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Type type = arbitrary.arbitraryType();
			Expr expr = arbitrary.arbitraryExpr();
			CastExpr t = castExpr().withType(type).withExpr(expr);
			CastExpr t2 = castExpr().withType(type).withExpr(expr);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testClassExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Type type = arbitrary.arbitraryType();
			ClassExpr t = classExpr().withType(type);
			ClassExpr t2 = classExpr().withType(type);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testConditionalExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr condition = arbitrary.arbitraryExpr();
			Expr thenExpr = arbitrary.arbitraryExpr();
			Expr elseExpr = arbitrary.arbitraryExpr();
			ConditionalExpr t = conditionalExpr().withCondition(condition).withThenExpr(thenExpr).withElseExpr(elseExpr);
			ConditionalExpr t2 = conditionalExpr().withCondition(condition).withThenExpr(thenExpr).withElseExpr(elseExpr);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testFieldAccessExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeOption<Expr> scope = arbitrary.arbitraryOptionExpr();
			Name name = arbitrary.arbitraryName();
			FieldAccessExpr t = fieldAccessExpr().withScope(scope).withName(name);
			FieldAccessExpr t2 = fieldAccessExpr().withScope(scope).withName(name);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testInstanceOfExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr expr = arbitrary.arbitraryExpr();
			Type type = arbitrary.arbitraryType();
			InstanceOfExpr t = instanceOfExpr().withExpr(expr).withType(type);
			InstanceOfExpr t2 = instanceOfExpr().withExpr(expr).withType(type);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testLambdaExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<FormalParameter> params = arbitrary.arbitraryListFormalParameter();
			boolean hasParens = arbitrary.arbitraryBoolean();
			NodeEither<Expr, BlockStmt> body = arbitrary.arbitraryEitherExprBlockStmt();
			LambdaExpr t = lambdaExpr().withParams(params).setParens(hasParens).withBody(body);
			LambdaExpr t2 = lambdaExpr().withParams(params).setParens(hasParens).withBody(body);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testMarkerAnnotationExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			QualifiedName name = arbitrary.arbitraryQualifiedName();
			MarkerAnnotationExpr t = markerAnnotationExpr().withName(name);
			MarkerAnnotationExpr t2 = markerAnnotationExpr().withName(name);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testMemberValuePair() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Name name = arbitrary.arbitraryName();
			Expr value = arbitrary.arbitraryExpr();
			MemberValuePair t = memberValuePair().withName(name).withValue(value);
			MemberValuePair t2 = memberValuePair().withName(name).withValue(value);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
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
			MethodInvocationExpr t = methodInvocationExpr().withScope(scope).withTypeArgs(typeArgs).withName(name).withArgs(args);
			MethodInvocationExpr t2 = methodInvocationExpr().withScope(scope).withTypeArgs(typeArgs).withName(name).withArgs(args);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testMethodReferenceExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr scope = arbitrary.arbitraryExpr();
			NodeList<Type> typeArgs = arbitrary.arbitraryListType();
			Name name = arbitrary.arbitraryName();
			MethodReferenceExpr t = methodReferenceExpr().withScope(scope).withTypeArgs(typeArgs).withName(name);
			MethodReferenceExpr t2 = methodReferenceExpr().withScope(scope).withTypeArgs(typeArgs).withName(name);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testNormalAnnotationExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			QualifiedName name = arbitrary.arbitraryQualifiedName();
			NodeList<MemberValuePair> pairs = arbitrary.arbitraryListMemberValuePair();
			NormalAnnotationExpr t = normalAnnotationExpr().withName(name).withPairs(pairs);
			NormalAnnotationExpr t2 = normalAnnotationExpr().withName(name).withPairs(pairs);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
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
			ObjectCreationExpr t = objectCreationExpr().withScope(scope).withTypeArgs(typeArgs).withType(type).withArgs(args).withBody(body);
			ObjectCreationExpr t2 = objectCreationExpr().withScope(scope).withTypeArgs(typeArgs).withType(type).withArgs(args).withBody(body);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testParenthesizedExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr inner = arbitrary.arbitraryExpr();
			ParenthesizedExpr t = parenthesizedExpr().withInner(inner);
			ParenthesizedExpr t2 = parenthesizedExpr().withInner(inner);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testSingleMemberAnnotationExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			QualifiedName name = arbitrary.arbitraryQualifiedName();
			Expr memberValue = arbitrary.arbitraryExpr();
			SingleMemberAnnotationExpr t = singleMemberAnnotationExpr().withName(name).withMemberValue(memberValue);
			SingleMemberAnnotationExpr t2 = singleMemberAnnotationExpr().withName(name).withMemberValue(memberValue);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testSuperExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeOption<Expr> classExpr = arbitrary.arbitraryOptionExpr();
			SuperExpr t = superExpr().withClassExpr(classExpr);
			SuperExpr t2 = superExpr().withClassExpr(classExpr);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testThisExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeOption<Expr> classExpr = arbitrary.arbitraryOptionExpr();
			ThisExpr t = thisExpr().withClassExpr(classExpr);
			ThisExpr t2 = thisExpr().withClassExpr(classExpr);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testTypeExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Type type = arbitrary.arbitraryType();
			TypeExpr t = typeExpr().withType(type);
			TypeExpr t2 = typeExpr().withType(type);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testUnaryExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			UnaryOp op = arbitrary.arbitraryUnaryOp();
			Expr expr = arbitrary.arbitraryExpr();
			UnaryExpr t = unaryExpr().withOp(op).withExpr(expr);
			UnaryExpr t2 = unaryExpr().withOp(op).withExpr(expr);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testVariableDeclarationExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			LocalVariableDecl declaration = arbitrary.arbitraryLocalVariableDecl();
			VariableDeclarationExpr t = variableDeclarationExpr().withDeclaration(declaration);
			VariableDeclarationExpr t2 = variableDeclarationExpr().withDeclaration(declaration);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testName() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			String id = arbitrary.arbitraryString();
			Name t = name().withId(id);
			Name t2 = name().withId(id);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testQualifiedName() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeOption<QualifiedName> qualifier = arbitrary.arbitraryOptionQualifiedName();
			Name name = arbitrary.arbitraryName();
			QualifiedName t = qualifiedName().withQualifier(qualifier).withName(name);
			QualifiedName t2 = qualifiedName().withQualifier(qualifier).withName(name);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testAssertStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr check = arbitrary.arbitraryExpr();
			NodeOption<Expr> msg = arbitrary.arbitraryOptionExpr();
			AssertStmt t = assertStmt().withCheck(check).withMsg(msg);
			AssertStmt t2 = assertStmt().withCheck(check).withMsg(msg);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testBlockStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<Stmt> stmts = arbitrary.arbitraryListStmt();
			BlockStmt t = blockStmt().withStmts(stmts);
			BlockStmt t2 = blockStmt().withStmts(stmts);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testBreakStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeOption<Name> id = arbitrary.arbitraryOptionName();
			BreakStmt t = breakStmt().withId(id);
			BreakStmt t2 = breakStmt().withId(id);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testCatchClause() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			FormalParameter except = arbitrary.arbitraryFormalParameter();
			BlockStmt catchBlock = arbitrary.arbitraryBlockStmt();
			CatchClause t = catchClause().withExcept(except).withCatchBlock(catchBlock);
			CatchClause t2 = catchClause().withExcept(except).withCatchBlock(catchBlock);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testContinueStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeOption<Name> id = arbitrary.arbitraryOptionName();
			ContinueStmt t = continueStmt().withId(id);
			ContinueStmt t2 = continueStmt().withId(id);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testDoStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Stmt body = arbitrary.arbitraryStmt();
			Expr condition = arbitrary.arbitraryExpr();
			DoStmt t = doStmt().withBody(body).withCondition(condition);
			DoStmt t2 = doStmt().withBody(body).withCondition(condition);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testEmptyStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			EmptyStmt t = emptyStmt();
			EmptyStmt t2 = emptyStmt();
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
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
			ExplicitConstructorInvocationStmt t = explicitConstructorInvocationStmt().withTypeArgs(typeArgs).setThis(isThis).withExpr(expr).withArgs(args);
			ExplicitConstructorInvocationStmt t2 = explicitConstructorInvocationStmt().withTypeArgs(typeArgs).setThis(isThis).withExpr(expr).withArgs(args);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testExpressionStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr expr = arbitrary.arbitraryExpr();
			ExpressionStmt t = expressionStmt().withExpr(expr);
			ExpressionStmt t2 = expressionStmt().withExpr(expr);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
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
			ForStmt t = forStmt().withInit(init).withCompare(compare).withUpdate(update).withBody(body);
			ForStmt t2 = forStmt().withInit(init).withCompare(compare).withUpdate(update).withBody(body);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testForeachStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			VariableDeclarationExpr var = arbitrary.arbitraryVariableDeclarationExpr();
			Expr iterable = arbitrary.arbitraryExpr();
			Stmt body = arbitrary.arbitraryStmt();
			ForeachStmt t = foreachStmt().withVar(var).withIterable(iterable).withBody(body);
			ForeachStmt t2 = foreachStmt().withVar(var).withIterable(iterable).withBody(body);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testIfStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr condition = arbitrary.arbitraryExpr();
			Stmt thenStmt = arbitrary.arbitraryStmt();
			NodeOption<Stmt> elseStmt = arbitrary.arbitraryOptionStmt();
			IfStmt t = ifStmt().withCondition(condition).withThenStmt(thenStmt).withElseStmt(elseStmt);
			IfStmt t2 = ifStmt().withCondition(condition).withThenStmt(thenStmt).withElseStmt(elseStmt);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testLabeledStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Name label = arbitrary.arbitraryName();
			Stmt stmt = arbitrary.arbitraryStmt();
			LabeledStmt t = labeledStmt().withLabel(label).withStmt(stmt);
			LabeledStmt t2 = labeledStmt().withLabel(label).withStmt(stmt);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testReturnStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeOption<Expr> expr = arbitrary.arbitraryOptionExpr();
			ReturnStmt t = returnStmt().withExpr(expr);
			ReturnStmt t2 = returnStmt().withExpr(expr);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testSwitchCase() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeOption<Expr> label = arbitrary.arbitraryOptionExpr();
			NodeList<Stmt> stmts = arbitrary.arbitraryListStmt();
			SwitchCase t = switchCase().withLabel(label).withStmts(stmts);
			SwitchCase t2 = switchCase().withLabel(label).withStmts(stmts);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testSwitchStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr selector = arbitrary.arbitraryExpr();
			NodeList<SwitchCase> cases = arbitrary.arbitraryListSwitchCase();
			SwitchStmt t = switchStmt().withSelector(selector).withCases(cases);
			SwitchStmt t2 = switchStmt().withSelector(selector).withCases(cases);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testSynchronizedStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr expr = arbitrary.arbitraryExpr();
			BlockStmt block = arbitrary.arbitraryBlockStmt();
			SynchronizedStmt t = synchronizedStmt().withExpr(expr).withBlock(block);
			SynchronizedStmt t2 = synchronizedStmt().withExpr(expr).withBlock(block);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testThrowStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr expr = arbitrary.arbitraryExpr();
			ThrowStmt t = throwStmt().withExpr(expr);
			ThrowStmt t2 = throwStmt().withExpr(expr);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
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
			TryStmt t = tryStmt().withResources(resources).withTryBlock(tryBlock).withCatchs(catchs).withFinallyBlock(finallyBlock);
			TryStmt t2 = tryStmt().withResources(resources).withTryBlock(tryBlock).withCatchs(catchs).withFinallyBlock(finallyBlock);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testTypeDeclarationStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			TypeDecl typeDecl = arbitrary.arbitraryTypeDecl();
			TypeDeclarationStmt t = typeDeclarationStmt().withTypeDecl(typeDecl);
			TypeDeclarationStmt t2 = typeDeclarationStmt().withTypeDecl(typeDecl);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testWhileStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr condition = arbitrary.arbitraryExpr();
			Stmt body = arbitrary.arbitraryStmt();
			WhileStmt t = whileStmt().withCondition(condition).withBody(body);
			WhileStmt t2 = whileStmt().withCondition(condition).withBody(body);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testArrayType() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Type componentType = arbitrary.arbitraryType();
			NodeList<ArrayDim> dims = arbitrary.arbitraryListArrayDim();
			ArrayType t = arrayType().withComponentType(componentType).withDims(dims);
			ArrayType t2 = arrayType().withComponentType(componentType).withDims(dims);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testIntersectionType() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<Type> types = arbitrary.arbitraryListType();
			IntersectionType t = intersectionType().withTypes(types);
			IntersectionType t2 = intersectionType().withTypes(types);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testPrimitiveType() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<AnnotationExpr> annotations = arbitrary.arbitraryListAnnotationExpr();
			Primitive primitive = arbitrary.arbitraryPrimitive();
			PrimitiveType t = primitiveType().withAnnotations(annotations).withPrimitive(primitive);
			PrimitiveType t2 = primitiveType().withAnnotations(annotations).withPrimitive(primitive);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
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
			QualifiedType t = qualifiedType().withAnnotations(annotations).withScope(scope).withName(name).withTypeArgs(typeArgs);
			QualifiedType t2 = qualifiedType().withAnnotations(annotations).withScope(scope).withName(name).withTypeArgs(typeArgs);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testUnionType() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<Type> types = arbitrary.arbitraryListType();
			UnionType t = unionType().withTypes(types);
			UnionType t2 = unionType().withTypes(types);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testUnknownType() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			UnknownType t = unknownType();
			UnknownType t2 = unknownType();
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testVoidType() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			VoidType t = voidType();
			VoidType t2 = voidType();
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}

	@Test
	public void testWildcardType() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<AnnotationExpr> annotations = arbitrary.arbitraryListAnnotationExpr();
			NodeOption<ReferenceType> ext = arbitrary.arbitraryOptionReferenceType();
			NodeOption<ReferenceType> sup = arbitrary.arbitraryOptionReferenceType();
			WildcardType t = wildcardType().withAnnotations(annotations).withExt(ext).withSup(sup);
			WildcardType t2 = wildcardType().withAnnotations(annotations).withExt(ext).withSup(sup);
			Assert.assertEquals(t, t2);
			Assert.assertEquals(t.hashCode(), t2.hashCode());
		}
	}
}
