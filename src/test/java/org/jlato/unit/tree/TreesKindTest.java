package org.jlato.unit.tree;

import org.jlato.tree.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TreesKindTest {

	@Test
	public void testAnnotationDecl() {
		Assert.assertEquals(Kind.AnnotationDecl, Trees.annotationDecl().kind());
	}

	@Test
	public void testAnnotationMemberDecl() {
		Assert.assertEquals(Kind.AnnotationMemberDecl, Trees.annotationMemberDecl().kind());
	}

	@Test
	public void testArrayDim() {
		Assert.assertEquals(Kind.ArrayDim, Trees.arrayDim().kind());
	}

	@Test
	public void testClassDecl() {
		Assert.assertEquals(Kind.ClassDecl, Trees.classDecl().kind());
	}

	@Test
	public void testCompilationUnit() {
		Assert.assertEquals(Kind.CompilationUnit, Trees.compilationUnit().kind());
	}

	@Test
	public void testConstructorDecl() {
		Assert.assertEquals(Kind.ConstructorDecl, Trees.constructorDecl().kind());
	}

	@Test
	public void testEmptyMemberDecl() {
		Assert.assertEquals(Kind.EmptyMemberDecl, Trees.emptyMemberDecl().kind());
	}

	@Test
	public void testEmptyTypeDecl() {
		Assert.assertEquals(Kind.EmptyTypeDecl, Trees.emptyTypeDecl().kind());
	}

	@Test
	public void testEnumConstantDecl() {
		Assert.assertEquals(Kind.EnumConstantDecl, Trees.enumConstantDecl().kind());
	}

	@Test
	public void testEnumDecl() {
		Assert.assertEquals(Kind.EnumDecl, Trees.enumDecl().kind());
	}

	@Test
	public void testFieldDecl() {
		Assert.assertEquals(Kind.FieldDecl, Trees.fieldDecl().kind());
	}

	@Test
	public void testFormalParameter() {
		Assert.assertEquals(Kind.FormalParameter, Trees.formalParameter().kind());
	}

	@Test
	public void testImportDecl() {
		Assert.assertEquals(Kind.ImportDecl, Trees.importDecl().kind());
	}

	@Test
	public void testInitializerDecl() {
		Assert.assertEquals(Kind.InitializerDecl, Trees.initializerDecl().kind());
	}

	@Test
	public void testInterfaceDecl() {
		Assert.assertEquals(Kind.InterfaceDecl, Trees.interfaceDecl().kind());
	}

	@Test
	public void testLocalVariableDecl() {
		Assert.assertEquals(Kind.LocalVariableDecl, Trees.localVariableDecl().kind());
	}

	@Test
	public void testMethodDecl() {
		Assert.assertEquals(Kind.MethodDecl, Trees.methodDecl().kind());
	}

	@Test
	public void testModifier() {
		Assert.assertEquals(Kind.Modifier, Trees.modifier().kind());
	}

	@Test
	public void testPackageDecl() {
		Assert.assertEquals(Kind.PackageDecl, Trees.packageDecl().kind());
	}

	@Test
	public void testTypeParameter() {
		Assert.assertEquals(Kind.TypeParameter, Trees.typeParameter().kind());
	}

	@Test
	public void testVariableDeclarator() {
		Assert.assertEquals(Kind.VariableDeclarator, Trees.variableDeclarator().kind());
	}

	@Test
	public void testVariableDeclaratorId() {
		Assert.assertEquals(Kind.VariableDeclaratorId, Trees.variableDeclaratorId().kind());
	}

	@Test
	public void testArrayAccessExpr() {
		Assert.assertEquals(Kind.ArrayAccessExpr, Trees.arrayAccessExpr().kind());
	}

	@Test
	public void testArrayCreationExpr() {
		Assert.assertEquals(Kind.ArrayCreationExpr, Trees.arrayCreationExpr().kind());
	}

	@Test
	public void testArrayDimExpr() {
		Assert.assertEquals(Kind.ArrayDimExpr, Trees.arrayDimExpr().kind());
	}

	@Test
	public void testArrayInitializerExpr() {
		Assert.assertEquals(Kind.ArrayInitializerExpr, Trees.arrayInitializerExpr().kind());
	}

	@Test
	public void testAssignExpr() {
		Assert.assertEquals(Kind.AssignExpr, Trees.assignExpr().kind());
	}

	@Test
	public void testBinaryExpr() {
		Assert.assertEquals(Kind.BinaryExpr, Trees.binaryExpr().kind());
	}

	@Test
	public void testCastExpr() {
		Assert.assertEquals(Kind.CastExpr, Trees.castExpr().kind());
	}

	@Test
	public void testClassExpr() {
		Assert.assertEquals(Kind.ClassExpr, Trees.classExpr().kind());
	}

	@Test
	public void testConditionalExpr() {
		Assert.assertEquals(Kind.ConditionalExpr, Trees.conditionalExpr().kind());
	}

	@Test
	public void testFieldAccessExpr() {
		Assert.assertEquals(Kind.FieldAccessExpr, Trees.fieldAccessExpr().kind());
	}

	@Test
	public void testInstanceOfExpr() {
		Assert.assertEquals(Kind.InstanceOfExpr, Trees.instanceOfExpr().kind());
	}

	@Test
	public void testLambdaExpr() {
		Assert.assertEquals(Kind.LambdaExpr, Trees.lambdaExpr().kind());
	}

	@Test
	public void testMarkerAnnotationExpr() {
		Assert.assertEquals(Kind.MarkerAnnotationExpr, Trees.markerAnnotationExpr().kind());
	}

	@Test
	public void testMemberValuePair() {
		Assert.assertEquals(Kind.MemberValuePair, Trees.memberValuePair().kind());
	}

	@Test
	public void testMethodInvocationExpr() {
		Assert.assertEquals(Kind.MethodInvocationExpr, Trees.methodInvocationExpr().kind());
	}

	@Test
	public void testMethodReferenceExpr() {
		Assert.assertEquals(Kind.MethodReferenceExpr, Trees.methodReferenceExpr().kind());
	}

	@Test
	public void testNormalAnnotationExpr() {
		Assert.assertEquals(Kind.NormalAnnotationExpr, Trees.normalAnnotationExpr().kind());
	}

	@Test
	public void testObjectCreationExpr() {
		Assert.assertEquals(Kind.ObjectCreationExpr, Trees.objectCreationExpr().kind());
	}

	@Test
	public void testParenthesizedExpr() {
		Assert.assertEquals(Kind.ParenthesizedExpr, Trees.parenthesizedExpr().kind());
	}

	@Test
	public void testSingleMemberAnnotationExpr() {
		Assert.assertEquals(Kind.SingleMemberAnnotationExpr, Trees.singleMemberAnnotationExpr().kind());
	}

	@Test
	public void testSuperExpr() {
		Assert.assertEquals(Kind.SuperExpr, Trees.superExpr().kind());
	}

	@Test
	public void testThisExpr() {
		Assert.assertEquals(Kind.ThisExpr, Trees.thisExpr().kind());
	}

	@Test
	public void testTypeExpr() {
		Assert.assertEquals(Kind.TypeExpr, Trees.typeExpr().kind());
	}

	@Test
	public void testUnaryExpr() {
		Assert.assertEquals(Kind.UnaryExpr, Trees.unaryExpr().kind());
	}

	@Test
	public void testVariableDeclarationExpr() {
		Assert.assertEquals(Kind.VariableDeclarationExpr, Trees.variableDeclarationExpr().kind());
	}

	@Test
	public void testName() {
		Assert.assertEquals(Kind.Name, Trees.name().kind());
	}

	@Test
	public void testQualifiedName() {
		Assert.assertEquals(Kind.QualifiedName, Trees.qualifiedName().kind());
	}

	@Test
	public void testAssertStmt() {
		Assert.assertEquals(Kind.AssertStmt, Trees.assertStmt().kind());
	}

	@Test
	public void testBlockStmt() {
		Assert.assertEquals(Kind.BlockStmt, Trees.blockStmt().kind());
	}

	@Test
	public void testBreakStmt() {
		Assert.assertEquals(Kind.BreakStmt, Trees.breakStmt().kind());
	}

	@Test
	public void testCatchClause() {
		Assert.assertEquals(Kind.CatchClause, Trees.catchClause().kind());
	}

	@Test
	public void testContinueStmt() {
		Assert.assertEquals(Kind.ContinueStmt, Trees.continueStmt().kind());
	}

	@Test
	public void testDoStmt() {
		Assert.assertEquals(Kind.DoStmt, Trees.doStmt().kind());
	}

	@Test
	public void testEmptyStmt() {
		Assert.assertEquals(Kind.EmptyStmt, Trees.emptyStmt().kind());
	}

	@Test
	public void testExplicitConstructorInvocationStmt() {
		Assert.assertEquals(Kind.ExplicitConstructorInvocationStmt, Trees.explicitConstructorInvocationStmt().kind());
	}

	@Test
	public void testExpressionStmt() {
		Assert.assertEquals(Kind.ExpressionStmt, Trees.expressionStmt().kind());
	}

	@Test
	public void testForStmt() {
		Assert.assertEquals(Kind.ForStmt, Trees.forStmt().kind());
	}

	@Test
	public void testForeachStmt() {
		Assert.assertEquals(Kind.ForeachStmt, Trees.foreachStmt().kind());
	}

	@Test
	public void testIfStmt() {
		Assert.assertEquals(Kind.IfStmt, Trees.ifStmt().kind());
	}

	@Test
	public void testLabeledStmt() {
		Assert.assertEquals(Kind.LabeledStmt, Trees.labeledStmt().kind());
	}

	@Test
	public void testReturnStmt() {
		Assert.assertEquals(Kind.ReturnStmt, Trees.returnStmt().kind());
	}

	@Test
	public void testSwitchCase() {
		Assert.assertEquals(Kind.SwitchCase, Trees.switchCase().kind());
	}

	@Test
	public void testSwitchStmt() {
		Assert.assertEquals(Kind.SwitchStmt, Trees.switchStmt().kind());
	}

	@Test
	public void testSynchronizedStmt() {
		Assert.assertEquals(Kind.SynchronizedStmt, Trees.synchronizedStmt().kind());
	}

	@Test
	public void testThrowStmt() {
		Assert.assertEquals(Kind.ThrowStmt, Trees.throwStmt().kind());
	}

	@Test
	public void testTryStmt() {
		Assert.assertEquals(Kind.TryStmt, Trees.tryStmt().kind());
	}

	@Test
	public void testTypeDeclarationStmt() {
		Assert.assertEquals(Kind.TypeDeclarationStmt, Trees.typeDeclarationStmt().kind());
	}

	@Test
	public void testWhileStmt() {
		Assert.assertEquals(Kind.WhileStmt, Trees.whileStmt().kind());
	}

	@Test
	public void testArrayType() {
		Assert.assertEquals(Kind.ArrayType, Trees.arrayType().kind());
	}

	@Test
	public void testIntersectionType() {
		Assert.assertEquals(Kind.IntersectionType, Trees.intersectionType().kind());
	}

	@Test
	public void testPrimitiveType() {
		Assert.assertEquals(Kind.PrimitiveType, Trees.primitiveType().kind());
	}

	@Test
	public void testQualifiedType() {
		Assert.assertEquals(Kind.QualifiedType, Trees.qualifiedType().kind());
	}

	@Test
	public void testUnionType() {
		Assert.assertEquals(Kind.UnionType, Trees.unionType().kind());
	}

	@Test
	public void testUnknownType() {
		Assert.assertEquals(Kind.UnknownType, Trees.unknownType().kind());
	}

	@Test
	public void testVoidType() {
		Assert.assertEquals(Kind.VoidType, Trees.voidType().kind());
	}

	@Test
	public void testWildcardType() {
		Assert.assertEquals(Kind.WildcardType, Trees.wildcardType().kind());
	}
}
