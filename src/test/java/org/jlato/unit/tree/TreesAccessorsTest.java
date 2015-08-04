package org.jlato.unit.tree;

import org.jlato.tree.*;
import org.jlato.tree.decl.*;
import org.jlato.tree.expr.*;
import org.jlato.tree.name.*;
import org.jlato.tree.stmt.*;
import org.jlato.tree.type.*;
import static org.jlato.tree.Trees.*;
import org.jlato.unit.util.Arbitrary;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TreesAccessorsTest {

	@Test
	public void testAnnotationDecl() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<ExtendedModifier> modifiers = arbitrary.arbitraryListExtendedModifier();
			Name name = arbitrary.arbitraryName();
			NodeList<MemberDecl> members = arbitrary.arbitraryListMemberDecl();
			AnnotationDecl t = annotationDecl().withModifiers(modifiers).withName(name).withMembers(members);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(members, t.members());
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
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(type, t.type());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(dims, t.dims());
			Assert.assertEquals(defaultValue, t.defaultValue());
		}
	}

	@Test
	public void testArrayDim() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<AnnotationExpr> annotations = arbitrary.arbitraryListAnnotationExpr();
			ArrayDim t = arrayDim().withAnnotations(annotations);
			Assert.assertEquals(annotations, t.annotations());
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
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(typeParams, t.typeParams());
			Assert.assertEquals(extendsClause, t.extendsClause());
			Assert.assertEquals(implementsClause, t.implementsClause());
			Assert.assertEquals(members, t.members());
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
			Assert.assertEquals(packageDecl, t.packageDecl());
			Assert.assertEquals(imports, t.imports());
			Assert.assertEquals(types, t.types());
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
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(typeParams, t.typeParams());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(params, t.params());
			Assert.assertEquals(throwsClause, t.throwsClause());
			Assert.assertEquals(body, t.body());
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
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(args, t.args());
			Assert.assertEquals(classBody, t.classBody());
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
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(implementsClause, t.implementsClause());
			Assert.assertEquals(enumConstants, t.enumConstants());
			Assert.assertEquals(trailingComma, t.trailingComma());
			Assert.assertEquals(members, t.members());
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
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(type, t.type());
			Assert.assertEquals(variables, t.variables());
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
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(type, t.type());
			Assert.assertEquals(isVarArgs, t.isVarArgs());
			Assert.assertEquals(id, t.id());
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
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(isStatic, t.isStatic());
			Assert.assertEquals(isOnDemand, t.isOnDemand());
		}
	}

	@Test
	public void testInitializerDecl() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<ExtendedModifier> modifiers = arbitrary.arbitraryListExtendedModifier();
			BlockStmt body = arbitrary.arbitraryBlockStmt();
			InitializerDecl t = initializerDecl().withModifiers(modifiers).withBody(body);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(body, t.body());
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
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(typeParams, t.typeParams());
			Assert.assertEquals(extendsClause, t.extendsClause());
			Assert.assertEquals(members, t.members());
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
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(type, t.type());
			Assert.assertEquals(variables, t.variables());
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
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(typeParams, t.typeParams());
			Assert.assertEquals(type, t.type());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(params, t.params());
			Assert.assertEquals(dims, t.dims());
			Assert.assertEquals(throwsClause, t.throwsClause());
			Assert.assertEquals(body, t.body());
		}
	}

	@Test
	public void testPackageDecl() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<AnnotationExpr> annotations = arbitrary.arbitraryListAnnotationExpr();
			QualifiedName name = arbitrary.arbitraryQualifiedName();
			PackageDecl t = packageDecl().withAnnotations(annotations).withName(name);
			Assert.assertEquals(annotations, t.annotations());
			Assert.assertEquals(name, t.name());
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
			Assert.assertEquals(annotations, t.annotations());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(bounds, t.bounds());
		}
	}

	@Test
	public void testVariableDeclarator() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			VariableDeclaratorId id = arbitrary.arbitraryVariableDeclaratorId();
			NodeOption<Expr> init = arbitrary.arbitraryOptionExpr();
			VariableDeclarator t = variableDeclarator().withId(id).withInit(init);
			Assert.assertEquals(id, t.id());
			Assert.assertEquals(init, t.init());
		}
	}

	@Test
	public void testVariableDeclaratorId() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Name name = arbitrary.arbitraryName();
			NodeList<ArrayDim> dims = arbitrary.arbitraryListArrayDim();
			VariableDeclaratorId t = variableDeclaratorId().withName(name).withDims(dims);
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(dims, t.dims());
		}
	}

	@Test
	public void testArrayAccessExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr name = arbitrary.arbitraryExpr();
			Expr index = arbitrary.arbitraryExpr();
			ArrayAccessExpr t = arrayAccessExpr().withName(name).withIndex(index);
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(index, t.index());
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
			Assert.assertEquals(type, t.type());
			Assert.assertEquals(dimExprs, t.dimExprs());
			Assert.assertEquals(dims, t.dims());
			Assert.assertEquals(init, t.init());
		}
	}

	@Test
	public void testArrayDimExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<AnnotationExpr> annotations = arbitrary.arbitraryListAnnotationExpr();
			Expr expr = arbitrary.arbitraryExpr();
			ArrayDimExpr t = arrayDimExpr().withAnnotations(annotations).withExpr(expr);
			Assert.assertEquals(annotations, t.annotations());
			Assert.assertEquals(expr, t.expr());
		}
	}

	@Test
	public void testArrayInitializerExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<Expr> values = arbitrary.arbitraryListExpr();
			boolean trailingComma = arbitrary.arbitraryBoolean();
			ArrayInitializerExpr t = arrayInitializerExpr().withValues(values).withTrailingComma(trailingComma);
			Assert.assertEquals(values, t.values());
			Assert.assertEquals(trailingComma, t.trailingComma());
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
			Assert.assertEquals(target, t.target());
			Assert.assertEquals(op, t.op());
			Assert.assertEquals(value, t.value());
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
			Assert.assertEquals(left, t.left());
			Assert.assertEquals(op, t.op());
			Assert.assertEquals(right, t.right());
		}
	}

	@Test
	public void testCastExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Type type = arbitrary.arbitraryType();
			Expr expr = arbitrary.arbitraryExpr();
			CastExpr t = castExpr().withType(type).withExpr(expr);
			Assert.assertEquals(type, t.type());
			Assert.assertEquals(expr, t.expr());
		}
	}

	@Test
	public void testClassExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Type type = arbitrary.arbitraryType();
			ClassExpr t = classExpr().withType(type);
			Assert.assertEquals(type, t.type());
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
			Assert.assertEquals(condition, t.condition());
			Assert.assertEquals(thenExpr, t.thenExpr());
			Assert.assertEquals(elseExpr, t.elseExpr());
		}
	}

	@Test
	public void testFieldAccessExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeOption<Expr> scope = arbitrary.arbitraryOptionExpr();
			Name name = arbitrary.arbitraryName();
			FieldAccessExpr t = fieldAccessExpr().withScope(scope).withName(name);
			Assert.assertEquals(scope, t.scope());
			Assert.assertEquals(name, t.name());
		}
	}

	@Test
	public void testInstanceOfExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr expr = arbitrary.arbitraryExpr();
			Type type = arbitrary.arbitraryType();
			InstanceOfExpr t = instanceOfExpr().withExpr(expr).withType(type);
			Assert.assertEquals(expr, t.expr());
			Assert.assertEquals(type, t.type());
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
			Assert.assertEquals(params, t.params());
			Assert.assertEquals(hasParens, t.hasParens());
			Assert.assertEquals(body, t.body());
		}
	}

	@Test
	public void testMarkerAnnotationExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			QualifiedName name = arbitrary.arbitraryQualifiedName();
			MarkerAnnotationExpr t = markerAnnotationExpr().withName(name);
			Assert.assertEquals(name, t.name());
		}
	}

	@Test
	public void testMemberValuePair() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Name name = arbitrary.arbitraryName();
			Expr value = arbitrary.arbitraryExpr();
			MemberValuePair t = memberValuePair().withName(name).withValue(value);
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(value, t.value());
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
			Assert.assertEquals(scope, t.scope());
			Assert.assertEquals(typeArgs, t.typeArgs());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(args, t.args());
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
			Assert.assertEquals(scope, t.scope());
			Assert.assertEquals(typeArgs, t.typeArgs());
			Assert.assertEquals(name, t.name());
		}
	}

	@Test
	public void testNormalAnnotationExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			QualifiedName name = arbitrary.arbitraryQualifiedName();
			NodeList<MemberValuePair> pairs = arbitrary.arbitraryListMemberValuePair();
			NormalAnnotationExpr t = normalAnnotationExpr().withName(name).withPairs(pairs);
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(pairs, t.pairs());
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
			Assert.assertEquals(scope, t.scope());
			Assert.assertEquals(typeArgs, t.typeArgs());
			Assert.assertEquals(type, t.type());
			Assert.assertEquals(args, t.args());
			Assert.assertEquals(body, t.body());
		}
	}

	@Test
	public void testParenthesizedExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr inner = arbitrary.arbitraryExpr();
			ParenthesizedExpr t = parenthesizedExpr().withInner(inner);
			Assert.assertEquals(inner, t.inner());
		}
	}

	@Test
	public void testSingleMemberAnnotationExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			QualifiedName name = arbitrary.arbitraryQualifiedName();
			Expr memberValue = arbitrary.arbitraryExpr();
			SingleMemberAnnotationExpr t = singleMemberAnnotationExpr().withName(name).withMemberValue(memberValue);
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(memberValue, t.memberValue());
		}
	}

	@Test
	public void testSuperExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeOption<Expr> classExpr = arbitrary.arbitraryOptionExpr();
			SuperExpr t = superExpr().withClassExpr(classExpr);
			Assert.assertEquals(classExpr, t.classExpr());
		}
	}

	@Test
	public void testThisExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeOption<Expr> classExpr = arbitrary.arbitraryOptionExpr();
			ThisExpr t = thisExpr().withClassExpr(classExpr);
			Assert.assertEquals(classExpr, t.classExpr());
		}
	}

	@Test
	public void testTypeExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Type type = arbitrary.arbitraryType();
			TypeExpr t = typeExpr().withType(type);
			Assert.assertEquals(type, t.type());
		}
	}

	@Test
	public void testUnaryExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			UnaryOp op = arbitrary.arbitraryUnaryOp();
			Expr expr = arbitrary.arbitraryExpr();
			UnaryExpr t = unaryExpr().withOp(op).withExpr(expr);
			Assert.assertEquals(op, t.op());
			Assert.assertEquals(expr, t.expr());
		}
	}

	@Test
	public void testVariableDeclarationExpr() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			LocalVariableDecl declaration = arbitrary.arbitraryLocalVariableDecl();
			VariableDeclarationExpr t = variableDeclarationExpr().withDeclaration(declaration);
			Assert.assertEquals(declaration, t.declaration());
		}
	}

	@Test
	public void testName() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			String id = arbitrary.arbitraryString();
			Name t = name().withId(id);
			Assert.assertEquals(id, t.id());
		}
	}

	@Test
	public void testQualifiedName() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeOption<QualifiedName> qualifier = arbitrary.arbitraryOptionQualifiedName();
			Name name = arbitrary.arbitraryName();
			QualifiedName t = qualifiedName().withQualifier(qualifier).withName(name);
			Assert.assertEquals(qualifier, t.qualifier());
			Assert.assertEquals(name, t.name());
		}
	}

	@Test
	public void testAssertStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr check = arbitrary.arbitraryExpr();
			NodeOption<Expr> msg = arbitrary.arbitraryOptionExpr();
			AssertStmt t = assertStmt().withCheck(check).withMsg(msg);
			Assert.assertEquals(check, t.check());
			Assert.assertEquals(msg, t.msg());
		}
	}

	@Test
	public void testBlockStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<Stmt> stmts = arbitrary.arbitraryListStmt();
			BlockStmt t = blockStmt().withStmts(stmts);
			Assert.assertEquals(stmts, t.stmts());
		}
	}

	@Test
	public void testBreakStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeOption<Name> id = arbitrary.arbitraryOptionName();
			BreakStmt t = breakStmt().withId(id);
			Assert.assertEquals(id, t.id());
		}
	}

	@Test
	public void testCatchClause() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			FormalParameter except = arbitrary.arbitraryFormalParameter();
			BlockStmt catchBlock = arbitrary.arbitraryBlockStmt();
			CatchClause t = catchClause().withExcept(except).withCatchBlock(catchBlock);
			Assert.assertEquals(except, t.except());
			Assert.assertEquals(catchBlock, t.catchBlock());
		}
	}

	@Test
	public void testContinueStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeOption<Name> id = arbitrary.arbitraryOptionName();
			ContinueStmt t = continueStmt().withId(id);
			Assert.assertEquals(id, t.id());
		}
	}

	@Test
	public void testDoStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Stmt body = arbitrary.arbitraryStmt();
			Expr condition = arbitrary.arbitraryExpr();
			DoStmt t = doStmt().withBody(body).withCondition(condition);
			Assert.assertEquals(body, t.body());
			Assert.assertEquals(condition, t.condition());
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
			Assert.assertEquals(typeArgs, t.typeArgs());
			Assert.assertEquals(isThis, t.isThis());
			Assert.assertEquals(expr, t.expr());
			Assert.assertEquals(args, t.args());
		}
	}

	@Test
	public void testExpressionStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr expr = arbitrary.arbitraryExpr();
			ExpressionStmt t = expressionStmt().withExpr(expr);
			Assert.assertEquals(expr, t.expr());
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
			Assert.assertEquals(init, t.init());
			Assert.assertEquals(compare, t.compare());
			Assert.assertEquals(update, t.update());
			Assert.assertEquals(body, t.body());
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
			Assert.assertEquals(var, t.var());
			Assert.assertEquals(iterable, t.iterable());
			Assert.assertEquals(body, t.body());
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
			Assert.assertEquals(condition, t.condition());
			Assert.assertEquals(thenStmt, t.thenStmt());
			Assert.assertEquals(elseStmt, t.elseStmt());
		}
	}

	@Test
	public void testLabeledStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Name label = arbitrary.arbitraryName();
			Stmt stmt = arbitrary.arbitraryStmt();
			LabeledStmt t = labeledStmt().withLabel(label).withStmt(stmt);
			Assert.assertEquals(label, t.label());
			Assert.assertEquals(stmt, t.stmt());
		}
	}

	@Test
	public void testReturnStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeOption<Expr> expr = arbitrary.arbitraryOptionExpr();
			ReturnStmt t = returnStmt().withExpr(expr);
			Assert.assertEquals(expr, t.expr());
		}
	}

	@Test
	public void testSwitchCase() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeOption<Expr> label = arbitrary.arbitraryOptionExpr();
			NodeList<Stmt> stmts = arbitrary.arbitraryListStmt();
			SwitchCase t = switchCase().withLabel(label).withStmts(stmts);
			Assert.assertEquals(label, t.label());
			Assert.assertEquals(stmts, t.stmts());
		}
	}

	@Test
	public void testSwitchStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr selector = arbitrary.arbitraryExpr();
			NodeList<SwitchCase> cases = arbitrary.arbitraryListSwitchCase();
			SwitchStmt t = switchStmt().withSelector(selector).withCases(cases);
			Assert.assertEquals(selector, t.selector());
			Assert.assertEquals(cases, t.cases());
		}
	}

	@Test
	public void testSynchronizedStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr expr = arbitrary.arbitraryExpr();
			BlockStmt block = arbitrary.arbitraryBlockStmt();
			SynchronizedStmt t = synchronizedStmt().withExpr(expr).withBlock(block);
			Assert.assertEquals(expr, t.expr());
			Assert.assertEquals(block, t.block());
		}
	}

	@Test
	public void testThrowStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr expr = arbitrary.arbitraryExpr();
			ThrowStmt t = throwStmt().withExpr(expr);
			Assert.assertEquals(expr, t.expr());
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
			Assert.assertEquals(resources, t.resources());
			Assert.assertEquals(tryBlock, t.tryBlock());
			Assert.assertEquals(catchs, t.catchs());
			Assert.assertEquals(finallyBlock, t.finallyBlock());
		}
	}

	@Test
	public void testTypeDeclarationStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			TypeDecl typeDecl = arbitrary.arbitraryTypeDecl();
			TypeDeclarationStmt t = typeDeclarationStmt().withTypeDecl(typeDecl);
			Assert.assertEquals(typeDecl, t.typeDecl());
		}
	}

	@Test
	public void testWhileStmt() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Expr condition = arbitrary.arbitraryExpr();
			Stmt body = arbitrary.arbitraryStmt();
			WhileStmt t = whileStmt().withCondition(condition).withBody(body);
			Assert.assertEquals(condition, t.condition());
			Assert.assertEquals(body, t.body());
		}
	}

	@Test
	public void testArrayType() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			Type componentType = arbitrary.arbitraryType();
			NodeList<ArrayDim> dims = arbitrary.arbitraryListArrayDim();
			ArrayType t = arrayType().withComponentType(componentType).withDims(dims);
			Assert.assertEquals(componentType, t.componentType());
			Assert.assertEquals(dims, t.dims());
		}
	}

	@Test
	public void testIntersectionType() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<Type> types = arbitrary.arbitraryListType();
			IntersectionType t = intersectionType().withTypes(types);
			Assert.assertEquals(types, t.types());
		}
	}

	@Test
	public void testPrimitiveType() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<AnnotationExpr> annotations = arbitrary.arbitraryListAnnotationExpr();
			Primitive primitive = arbitrary.arbitraryPrimitive();
			PrimitiveType t = primitiveType().withAnnotations(annotations).withPrimitive(primitive);
			Assert.assertEquals(annotations, t.annotations());
			Assert.assertEquals(primitive, t.primitive());
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
			Assert.assertEquals(annotations, t.annotations());
			Assert.assertEquals(scope, t.scope());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(typeArgs, t.typeArgs());
		}
	}

	@Test
	public void testUnionType() {
		Arbitrary arbitrary = new Arbitrary();
		for (int i = 0; i < 10; i++) {
			NodeList<Type> types = arbitrary.arbitraryListType();
			UnionType t = unionType().withTypes(types);
			Assert.assertEquals(types, t.types());
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
			Assert.assertEquals(annotations, t.annotations());
			Assert.assertEquals(ext, t.ext());
			Assert.assertEquals(sup, t.sup());
		}
	}
}
