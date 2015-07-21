package org.jlato.unit;

import org.jlato.tree.Kind;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.jlato.tree.TreeFactory.*;

@RunWith(JUnit4.class)
public class TreesKindTest {

	@Test
	public void testAnnotationDecl() {
		Assert.assertEquals(Kind.AnnotationDecl, annotationDecl().kind());
	}

	@Test
	public void testAnnotationMemberDecl() {
		Assert.assertEquals(Kind.AnnotationMemberDecl, annotationMemberDecl().kind());
	}

	@Test
	public void testArrayDim() {
		Assert.assertEquals(Kind.ArrayDim, arrayDim().kind());
	}

	@Test
	public void testClassDecl() {
		Assert.assertEquals(Kind.ClassDecl, classDecl().kind());
	}

	@Test
	public void testCompilationUnit() {
		Assert.assertEquals(Kind.CompilationUnit, compilationUnit().kind());
	}

	@Test
	public void testConstructorDecl() {
		Assert.assertEquals(Kind.ConstructorDecl, constructorDecl().kind());
	}

	@Test
	public void testEmptyMemberDecl() {
		Assert.assertEquals(Kind.EmptyMemberDecl, emptyMemberDecl().kind());
	}

	@Test
	public void testEmptyTypeDecl() {
		Assert.assertEquals(Kind.EmptyTypeDecl, emptyTypeDecl().kind());
	}

	@Test
	public void testEnumConstantDecl() {
		Assert.assertEquals(Kind.EnumConstantDecl, enumConstantDecl().kind());
	}

	@Test
	public void testEnumDecl() {
		Assert.assertEquals(Kind.EnumDecl, enumDecl().kind());
	}

	@Test
	public void testFieldDecl() {
		Assert.assertEquals(Kind.FieldDecl, fieldDecl().kind());
	}

	@Test
	public void testFormalParameter() {
		Assert.assertEquals(Kind.FormalParameter, formalParameter().kind());
	}

	@Test
	public void testImportDecl() {
		Assert.assertEquals(Kind.ImportDecl, importDecl().kind());
	}

	@Test
	public void testInitializerDecl() {
		Assert.assertEquals(Kind.InitializerDecl, initializerDecl().kind());
	}

	@Test
	public void testInterfaceDecl() {
		Assert.assertEquals(Kind.InterfaceDecl, interfaceDecl().kind());
	}

	@Test
	public void testLocalVariableDecl() {
		Assert.assertEquals(Kind.LocalVariableDecl, localVariableDecl().kind());
	}

	@Test
	public void testMethodDecl() {
		Assert.assertEquals(Kind.MethodDecl, methodDecl().kind());
	}

	@Test
	public void testPackageDecl() {
		Assert.assertEquals(Kind.PackageDecl, packageDecl().kind());
	}

	@Test
	public void testTypeParameter() {
		Assert.assertEquals(Kind.TypeParameter, typeParameter().kind());
	}

	@Test
	public void testVariableDeclarator() {
		Assert.assertEquals(Kind.VariableDeclarator, variableDeclarator().kind());
	}

	@Test
	public void testVariableDeclaratorId() {
		Assert.assertEquals(Kind.VariableDeclaratorId, variableDeclaratorId().kind());
	}

	@Test
	public void testArrayAccessExpr() {
		Assert.assertEquals(Kind.ArrayAccessExpr, arrayAccessExpr().kind());
	}

	@Test
	public void testArrayCreationExpr() {
		Assert.assertEquals(Kind.ArrayCreationExpr, arrayCreationExpr().kind());
	}

	@Test
	public void testArrayDimExpr() {
		Assert.assertEquals(Kind.ArrayDimExpr, arrayDimExpr().kind());
	}

	@Test
	public void testArrayInitializerExpr() {
		Assert.assertEquals(Kind.ArrayInitializerExpr, arrayInitializerExpr().kind());
	}

	@Test
	public void testAssignExpr() {
		Assert.assertEquals(Kind.AssignExpr, assignExpr().kind());
	}

	@Test
	public void testBinaryExpr() {
		Assert.assertEquals(Kind.BinaryExpr, binaryExpr().kind());
	}

	@Test
	public void testCastExpr() {
		Assert.assertEquals(Kind.CastExpr, castExpr().kind());
	}

	@Test
	public void testClassExpr() {
		Assert.assertEquals(Kind.ClassExpr, classExpr().kind());
	}

	@Test
	public void testConditionalExpr() {
		Assert.assertEquals(Kind.ConditionalExpr, conditionalExpr().kind());
	}

	@Test
	public void testFieldAccessExpr() {
		Assert.assertEquals(Kind.FieldAccessExpr, fieldAccessExpr().kind());
	}

	@Test
	public void testInstanceOfExpr() {
		Assert.assertEquals(Kind.InstanceOfExpr, instanceOfExpr().kind());
	}

	@Test
	public void testLambdaExpr() {
		Assert.assertEquals(Kind.LambdaExpr, lambdaExpr().kind());
	}

	@Test
	public void testMarkerAnnotationExpr() {
		Assert.assertEquals(Kind.MarkerAnnotationExpr, markerAnnotationExpr().kind());
	}

	@Test
	public void testMemberValuePair() {
		Assert.assertEquals(Kind.MemberValuePair, memberValuePair().kind());
	}

	@Test
	public void testMethodInvocationExpr() {
		Assert.assertEquals(Kind.MethodInvocationExpr, methodInvocationExpr().kind());
	}

	@Test
	public void testMethodReferenceExpr() {
		Assert.assertEquals(Kind.MethodReferenceExpr, methodReferenceExpr().kind());
	}

	@Test
	public void testNormalAnnotationExpr() {
		Assert.assertEquals(Kind.NormalAnnotationExpr, normalAnnotationExpr().kind());
	}

	@Test
	public void testObjectCreationExpr() {
		Assert.assertEquals(Kind.ObjectCreationExpr, objectCreationExpr().kind());
	}

	@Test
	public void testParenthesizedExpr() {
		Assert.assertEquals(Kind.ParenthesizedExpr, parenthesizedExpr().kind());
	}

	@Test
	public void testSingleMemberAnnotationExpr() {
		Assert.assertEquals(Kind.SingleMemberAnnotationExpr, singleMemberAnnotationExpr().kind());
	}

	@Test
	public void testSuperExpr() {
		Assert.assertEquals(Kind.SuperExpr, superExpr().kind());
	}

	@Test
	public void testThisExpr() {
		Assert.assertEquals(Kind.ThisExpr, thisExpr().kind());
	}

	@Test
	public void testTypeExpr() {
		Assert.assertEquals(Kind.TypeExpr, typeExpr().kind());
	}

	@Test
	public void testUnaryExpr() {
		Assert.assertEquals(Kind.UnaryExpr, unaryExpr().kind());
	}

	@Test
	public void testVariableDeclarationExpr() {
		Assert.assertEquals(Kind.VariableDeclarationExpr, variableDeclarationExpr().kind());
	}

	@Test
	public void testName() {
		Assert.assertEquals(Kind.Name, name().kind());
	}

	@Test
	public void testQualifiedName() {
		Assert.assertEquals(Kind.QualifiedName, qualifiedName().kind());
	}

	@Test
	public void testAssertStmt() {
		Assert.assertEquals(Kind.AssertStmt, assertStmt().kind());
	}

	@Test
	public void testBlockStmt() {
		Assert.assertEquals(Kind.BlockStmt, blockStmt().kind());
	}

	@Test
	public void testBreakStmt() {
		Assert.assertEquals(Kind.BreakStmt, breakStmt().kind());
	}

	@Test
	public void testCatchClause() {
		Assert.assertEquals(Kind.CatchClause, catchClause().kind());
	}

	@Test
	public void testContinueStmt() {
		Assert.assertEquals(Kind.ContinueStmt, continueStmt().kind());
	}

	@Test
	public void testDoStmt() {
		Assert.assertEquals(Kind.DoStmt, doStmt().kind());
	}

	@Test
	public void testEmptyStmt() {
		Assert.assertEquals(Kind.EmptyStmt, emptyStmt().kind());
	}

	@Test
	public void testExplicitConstructorInvocationStmt() {
		Assert.assertEquals(Kind.ExplicitConstructorInvocationStmt, explicitConstructorInvocationStmt().kind());
	}

	@Test
	public void testExpressionStmt() {
		Assert.assertEquals(Kind.ExpressionStmt, expressionStmt().kind());
	}

	@Test
	public void testForStmt() {
		Assert.assertEquals(Kind.ForStmt, forStmt().kind());
	}

	@Test
	public void testForeachStmt() {
		Assert.assertEquals(Kind.ForeachStmt, foreachStmt().kind());
	}

	@Test
	public void testIfStmt() {
		Assert.assertEquals(Kind.IfStmt, ifStmt().kind());
	}

	@Test
	public void testLabeledStmt() {
		Assert.assertEquals(Kind.LabeledStmt, labeledStmt().kind());
	}

	@Test
	public void testReturnStmt() {
		Assert.assertEquals(Kind.ReturnStmt, returnStmt().kind());
	}

	@Test
	public void testSwitchCase() {
		Assert.assertEquals(Kind.SwitchCase, switchCase().kind());
	}

	@Test
	public void testSwitchStmt() {
		Assert.assertEquals(Kind.SwitchStmt, switchStmt().kind());
	}

	@Test
	public void testSynchronizedStmt() {
		Assert.assertEquals(Kind.SynchronizedStmt, synchronizedStmt().kind());
	}

	@Test
	public void testThrowStmt() {
		Assert.assertEquals(Kind.ThrowStmt, throwStmt().kind());
	}

	@Test
	public void testTryStmt() {
		Assert.assertEquals(Kind.TryStmt, tryStmt().kind());
	}

	@Test
	public void testTypeDeclarationStmt() {
		Assert.assertEquals(Kind.TypeDeclarationStmt, typeDeclarationStmt().kind());
	}

	@Test
	public void testWhileStmt() {
		Assert.assertEquals(Kind.WhileStmt, whileStmt().kind());
	}

	@Test
	public void testArrayType() {
		Assert.assertEquals(Kind.ArrayType, arrayType().kind());
	}

	@Test
	public void testIntersectionType() {
		Assert.assertEquals(Kind.IntersectionType, intersectionType().kind());
	}

	@Test
	public void testPrimitiveType() {
		Assert.assertEquals(Kind.PrimitiveType, primitiveType().kind());
	}

	@Test
	public void testQualifiedType() {
		Assert.assertEquals(Kind.QualifiedType, qualifiedType().kind());
	}

	@Test
	public void testUnionType() {
		Assert.assertEquals(Kind.UnionType, unionType().kind());
	}

	@Test
	public void testUnknownType() {
		Assert.assertEquals(Kind.UnknownType, unknownType().kind());
	}

	@Test
	public void testVoidType() {
		Assert.assertEquals(Kind.VoidType, voidType().kind());
	}

	@Test
	public void testWildcardType() {
		Assert.assertEquals(Kind.WildcardType, wildcardType().kind());
	}
}
