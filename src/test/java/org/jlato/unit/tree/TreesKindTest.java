package org.jlato.unit.tree;

import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.Trees;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TreesKindTest {

	@Test
	public void testAnnotationDecl() {
		Assert.assertEquals(Kind.AnnotationDecl, Trees.annotationDecl().kind());
		Assert.assertEquals(Kind.AnnotationDecl, TDTree.stateOf(Trees.annotationDecl()).kind());
	}

	@Test
	public void testAnnotationMemberDecl() {
		Assert.assertEquals(Kind.AnnotationMemberDecl, Trees.annotationMemberDecl().kind());
		Assert.assertEquals(Kind.AnnotationMemberDecl, TDTree.stateOf(Trees.annotationMemberDecl()).kind());
	}

	@Test
	public void testArrayDim() {
		Assert.assertEquals(Kind.ArrayDim, Trees.arrayDim().kind());
		Assert.assertEquals(Kind.ArrayDim, TDTree.stateOf(Trees.arrayDim()).kind());
	}

	@Test
	public void testClassDecl() {
		Assert.assertEquals(Kind.ClassDecl, Trees.classDecl().kind());
		Assert.assertEquals(Kind.ClassDecl, TDTree.stateOf(Trees.classDecl()).kind());
	}

	@Test
	public void testCompilationUnit() {
		Assert.assertEquals(Kind.CompilationUnit, Trees.compilationUnit().kind());
		Assert.assertEquals(Kind.CompilationUnit, TDTree.stateOf(Trees.compilationUnit()).kind());
	}

	@Test
	public void testConstructorDecl() {
		Assert.assertEquals(Kind.ConstructorDecl, Trees.constructorDecl().kind());
		Assert.assertEquals(Kind.ConstructorDecl, TDTree.stateOf(Trees.constructorDecl()).kind());
	}

	@Test
	public void testEmptyMemberDecl() {
		Assert.assertEquals(Kind.EmptyMemberDecl, Trees.emptyMemberDecl().kind());
		Assert.assertEquals(Kind.EmptyMemberDecl, TDTree.stateOf(Trees.emptyMemberDecl()).kind());
	}

	@Test
	public void testEmptyTypeDecl() {
		Assert.assertEquals(Kind.EmptyTypeDecl, Trees.emptyTypeDecl().kind());
		Assert.assertEquals(Kind.EmptyTypeDecl, TDTree.stateOf(Trees.emptyTypeDecl()).kind());
	}

	@Test
	public void testEnumConstantDecl() {
		Assert.assertEquals(Kind.EnumConstantDecl, Trees.enumConstantDecl().kind());
		Assert.assertEquals(Kind.EnumConstantDecl, TDTree.stateOf(Trees.enumConstantDecl()).kind());
	}

	@Test
	public void testEnumDecl() {
		Assert.assertEquals(Kind.EnumDecl, Trees.enumDecl().kind());
		Assert.assertEquals(Kind.EnumDecl, TDTree.stateOf(Trees.enumDecl()).kind());
	}

	@Test
	public void testFieldDecl() {
		Assert.assertEquals(Kind.FieldDecl, Trees.fieldDecl().kind());
		Assert.assertEquals(Kind.FieldDecl, TDTree.stateOf(Trees.fieldDecl()).kind());
	}

	@Test
	public void testFormalParameter() {
		Assert.assertEquals(Kind.FormalParameter, Trees.formalParameter().kind());
		Assert.assertEquals(Kind.FormalParameter, TDTree.stateOf(Trees.formalParameter()).kind());
	}

	@Test
	public void testImportDecl() {
		Assert.assertEquals(Kind.ImportDecl, Trees.importDecl().kind());
		Assert.assertEquals(Kind.ImportDecl, TDTree.stateOf(Trees.importDecl()).kind());
	}

	@Test
	public void testInitializerDecl() {
		Assert.assertEquals(Kind.InitializerDecl, Trees.initializerDecl().kind());
		Assert.assertEquals(Kind.InitializerDecl, TDTree.stateOf(Trees.initializerDecl()).kind());
	}

	@Test
	public void testInterfaceDecl() {
		Assert.assertEquals(Kind.InterfaceDecl, Trees.interfaceDecl().kind());
		Assert.assertEquals(Kind.InterfaceDecl, TDTree.stateOf(Trees.interfaceDecl()).kind());
	}

	@Test
	public void testLocalVariableDecl() {
		Assert.assertEquals(Kind.LocalVariableDecl, Trees.localVariableDecl().kind());
		Assert.assertEquals(Kind.LocalVariableDecl, TDTree.stateOf(Trees.localVariableDecl()).kind());
	}

	@Test
	public void testMethodDecl() {
		Assert.assertEquals(Kind.MethodDecl, Trees.methodDecl().kind());
		Assert.assertEquals(Kind.MethodDecl, TDTree.stateOf(Trees.methodDecl()).kind());
	}

	@Test
	public void testModifier() {
		Assert.assertEquals(Kind.Modifier, Trees.modifier().kind());
		Assert.assertEquals(Kind.Modifier, TDTree.stateOf(Trees.modifier()).kind());
	}

	@Test
	public void testPackageDecl() {
		Assert.assertEquals(Kind.PackageDecl, Trees.packageDecl().kind());
		Assert.assertEquals(Kind.PackageDecl, TDTree.stateOf(Trees.packageDecl()).kind());
	}

	@Test
	public void testTypeParameter() {
		Assert.assertEquals(Kind.TypeParameter, Trees.typeParameter().kind());
		Assert.assertEquals(Kind.TypeParameter, TDTree.stateOf(Trees.typeParameter()).kind());
	}

	@Test
	public void testVariableDeclarator() {
		Assert.assertEquals(Kind.VariableDeclarator, Trees.variableDeclarator().kind());
		Assert.assertEquals(Kind.VariableDeclarator, TDTree.stateOf(Trees.variableDeclarator()).kind());
	}

	@Test
	public void testVariableDeclaratorId() {
		Assert.assertEquals(Kind.VariableDeclaratorId, Trees.variableDeclaratorId().kind());
		Assert.assertEquals(Kind.VariableDeclaratorId, TDTree.stateOf(Trees.variableDeclaratorId()).kind());
	}

	@Test
	public void testArrayAccessExpr() {
		Assert.assertEquals(Kind.ArrayAccessExpr, Trees.arrayAccessExpr().kind());
		Assert.assertEquals(Kind.ArrayAccessExpr, TDTree.stateOf(Trees.arrayAccessExpr()).kind());
	}

	@Test
	public void testArrayCreationExpr() {
		Assert.assertEquals(Kind.ArrayCreationExpr, Trees.arrayCreationExpr().kind());
		Assert.assertEquals(Kind.ArrayCreationExpr, TDTree.stateOf(Trees.arrayCreationExpr()).kind());
	}

	@Test
	public void testArrayDimExpr() {
		Assert.assertEquals(Kind.ArrayDimExpr, Trees.arrayDimExpr().kind());
		Assert.assertEquals(Kind.ArrayDimExpr, TDTree.stateOf(Trees.arrayDimExpr()).kind());
	}

	@Test
	public void testArrayInitializerExpr() {
		Assert.assertEquals(Kind.ArrayInitializerExpr, Trees.arrayInitializerExpr().kind());
		Assert.assertEquals(Kind.ArrayInitializerExpr, TDTree.stateOf(Trees.arrayInitializerExpr()).kind());
	}

	@Test
	public void testAssignExpr() {
		Assert.assertEquals(Kind.AssignExpr, Trees.assignExpr().kind());
		Assert.assertEquals(Kind.AssignExpr, TDTree.stateOf(Trees.assignExpr()).kind());
	}

	@Test
	public void testBinaryExpr() {
		Assert.assertEquals(Kind.BinaryExpr, Trees.binaryExpr().kind());
		Assert.assertEquals(Kind.BinaryExpr, TDTree.stateOf(Trees.binaryExpr()).kind());
	}

	@Test
	public void testCastExpr() {
		Assert.assertEquals(Kind.CastExpr, Trees.castExpr().kind());
		Assert.assertEquals(Kind.CastExpr, TDTree.stateOf(Trees.castExpr()).kind());
	}

	@Test
	public void testClassExpr() {
		Assert.assertEquals(Kind.ClassExpr, Trees.classExpr().kind());
		Assert.assertEquals(Kind.ClassExpr, TDTree.stateOf(Trees.classExpr()).kind());
	}

	@Test
	public void testConditionalExpr() {
		Assert.assertEquals(Kind.ConditionalExpr, Trees.conditionalExpr().kind());
		Assert.assertEquals(Kind.ConditionalExpr, TDTree.stateOf(Trees.conditionalExpr()).kind());
	}

	@Test
	public void testFieldAccessExpr() {
		Assert.assertEquals(Kind.FieldAccessExpr, Trees.fieldAccessExpr().kind());
		Assert.assertEquals(Kind.FieldAccessExpr, TDTree.stateOf(Trees.fieldAccessExpr()).kind());
	}

	@Test
	public void testInstanceOfExpr() {
		Assert.assertEquals(Kind.InstanceOfExpr, Trees.instanceOfExpr().kind());
		Assert.assertEquals(Kind.InstanceOfExpr, TDTree.stateOf(Trees.instanceOfExpr()).kind());
	}

	@Test
	public void testLambdaExpr() {
		Assert.assertEquals(Kind.LambdaExpr, Trees.lambdaExpr().kind());
		Assert.assertEquals(Kind.LambdaExpr, TDTree.stateOf(Trees.lambdaExpr()).kind());
	}

	@Test
	public void testMarkerAnnotationExpr() {
		Assert.assertEquals(Kind.MarkerAnnotationExpr, Trees.markerAnnotationExpr().kind());
		Assert.assertEquals(Kind.MarkerAnnotationExpr, TDTree.stateOf(Trees.markerAnnotationExpr()).kind());
	}

	@Test
	public void testMemberValuePair() {
		Assert.assertEquals(Kind.MemberValuePair, Trees.memberValuePair().kind());
		Assert.assertEquals(Kind.MemberValuePair, TDTree.stateOf(Trees.memberValuePair()).kind());
	}

	@Test
	public void testMethodInvocationExpr() {
		Assert.assertEquals(Kind.MethodInvocationExpr, Trees.methodInvocationExpr().kind());
		Assert.assertEquals(Kind.MethodInvocationExpr, TDTree.stateOf(Trees.methodInvocationExpr()).kind());
	}

	@Test
	public void testMethodReferenceExpr() {
		Assert.assertEquals(Kind.MethodReferenceExpr, Trees.methodReferenceExpr().kind());
		Assert.assertEquals(Kind.MethodReferenceExpr, TDTree.stateOf(Trees.methodReferenceExpr()).kind());
	}

	@Test
	public void testNormalAnnotationExpr() {
		Assert.assertEquals(Kind.NormalAnnotationExpr, Trees.normalAnnotationExpr().kind());
		Assert.assertEquals(Kind.NormalAnnotationExpr, TDTree.stateOf(Trees.normalAnnotationExpr()).kind());
	}

	@Test
	public void testObjectCreationExpr() {
		Assert.assertEquals(Kind.ObjectCreationExpr, Trees.objectCreationExpr().kind());
		Assert.assertEquals(Kind.ObjectCreationExpr, TDTree.stateOf(Trees.objectCreationExpr()).kind());
	}

	@Test
	public void testParenthesizedExpr() {
		Assert.assertEquals(Kind.ParenthesizedExpr, Trees.parenthesizedExpr().kind());
		Assert.assertEquals(Kind.ParenthesizedExpr, TDTree.stateOf(Trees.parenthesizedExpr()).kind());
	}

	@Test
	public void testSingleMemberAnnotationExpr() {
		Assert.assertEquals(Kind.SingleMemberAnnotationExpr, Trees.singleMemberAnnotationExpr().kind());
		Assert.assertEquals(Kind.SingleMemberAnnotationExpr, TDTree.stateOf(Trees.singleMemberAnnotationExpr()).kind());
	}

	@Test
	public void testSuperExpr() {
		Assert.assertEquals(Kind.SuperExpr, Trees.superExpr().kind());
		Assert.assertEquals(Kind.SuperExpr, TDTree.stateOf(Trees.superExpr()).kind());
	}

	@Test
	public void testThisExpr() {
		Assert.assertEquals(Kind.ThisExpr, Trees.thisExpr().kind());
		Assert.assertEquals(Kind.ThisExpr, TDTree.stateOf(Trees.thisExpr()).kind());
	}

	@Test
	public void testTypeExpr() {
		Assert.assertEquals(Kind.TypeExpr, Trees.typeExpr().kind());
		Assert.assertEquals(Kind.TypeExpr, TDTree.stateOf(Trees.typeExpr()).kind());
	}

	@Test
	public void testUnaryExpr() {
		Assert.assertEquals(Kind.UnaryExpr, Trees.unaryExpr().kind());
		Assert.assertEquals(Kind.UnaryExpr, TDTree.stateOf(Trees.unaryExpr()).kind());
	}

	@Test
	public void testVariableDeclarationExpr() {
		Assert.assertEquals(Kind.VariableDeclarationExpr, Trees.variableDeclarationExpr().kind());
		Assert.assertEquals(Kind.VariableDeclarationExpr, TDTree.stateOf(Trees.variableDeclarationExpr()).kind());
	}

	@Test
	public void testName() {
		Assert.assertEquals(Kind.Name, Trees.name().kind());
		Assert.assertEquals(Kind.Name, TDTree.stateOf(Trees.name()).kind());
	}

	@Test
	public void testQualifiedName() {
		Assert.assertEquals(Kind.QualifiedName, Trees.qualifiedName().kind());
		Assert.assertEquals(Kind.QualifiedName, TDTree.stateOf(Trees.qualifiedName()).kind());
	}

	@Test
	public void testAssertStmt() {
		Assert.assertEquals(Kind.AssertStmt, Trees.assertStmt().kind());
		Assert.assertEquals(Kind.AssertStmt, TDTree.stateOf(Trees.assertStmt()).kind());
	}

	@Test
	public void testBlockStmt() {
		Assert.assertEquals(Kind.BlockStmt, Trees.blockStmt().kind());
		Assert.assertEquals(Kind.BlockStmt, TDTree.stateOf(Trees.blockStmt()).kind());
	}

	@Test
	public void testBreakStmt() {
		Assert.assertEquals(Kind.BreakStmt, Trees.breakStmt().kind());
		Assert.assertEquals(Kind.BreakStmt, TDTree.stateOf(Trees.breakStmt()).kind());
	}

	@Test
	public void testCatchClause() {
		Assert.assertEquals(Kind.CatchClause, Trees.catchClause().kind());
		Assert.assertEquals(Kind.CatchClause, TDTree.stateOf(Trees.catchClause()).kind());
	}

	@Test
	public void testContinueStmt() {
		Assert.assertEquals(Kind.ContinueStmt, Trees.continueStmt().kind());
		Assert.assertEquals(Kind.ContinueStmt, TDTree.stateOf(Trees.continueStmt()).kind());
	}

	@Test
	public void testDoStmt() {
		Assert.assertEquals(Kind.DoStmt, Trees.doStmt().kind());
		Assert.assertEquals(Kind.DoStmt, TDTree.stateOf(Trees.doStmt()).kind());
	}

	@Test
	public void testEmptyStmt() {
		Assert.assertEquals(Kind.EmptyStmt, Trees.emptyStmt().kind());
		Assert.assertEquals(Kind.EmptyStmt, TDTree.stateOf(Trees.emptyStmt()).kind());
	}

	@Test
	public void testExplicitConstructorInvocationStmt() {
		Assert.assertEquals(Kind.ExplicitConstructorInvocationStmt, Trees.explicitConstructorInvocationStmt().kind());
		Assert.assertEquals(Kind.ExplicitConstructorInvocationStmt, TDTree.stateOf(Trees.explicitConstructorInvocationStmt()).kind());
	}

	@Test
	public void testExpressionStmt() {
		Assert.assertEquals(Kind.ExpressionStmt, Trees.expressionStmt().kind());
		Assert.assertEquals(Kind.ExpressionStmt, TDTree.stateOf(Trees.expressionStmt()).kind());
	}

	@Test
	public void testForStmt() {
		Assert.assertEquals(Kind.ForStmt, Trees.forStmt().kind());
		Assert.assertEquals(Kind.ForStmt, TDTree.stateOf(Trees.forStmt()).kind());
	}

	@Test
	public void testForeachStmt() {
		Assert.assertEquals(Kind.ForeachStmt, Trees.foreachStmt().kind());
		Assert.assertEquals(Kind.ForeachStmt, TDTree.stateOf(Trees.foreachStmt()).kind());
	}

	@Test
	public void testIfStmt() {
		Assert.assertEquals(Kind.IfStmt, Trees.ifStmt().kind());
		Assert.assertEquals(Kind.IfStmt, TDTree.stateOf(Trees.ifStmt()).kind());
	}

	@Test
	public void testLabeledStmt() {
		Assert.assertEquals(Kind.LabeledStmt, Trees.labeledStmt().kind());
		Assert.assertEquals(Kind.LabeledStmt, TDTree.stateOf(Trees.labeledStmt()).kind());
	}

	@Test
	public void testReturnStmt() {
		Assert.assertEquals(Kind.ReturnStmt, Trees.returnStmt().kind());
		Assert.assertEquals(Kind.ReturnStmt, TDTree.stateOf(Trees.returnStmt()).kind());
	}

	@Test
	public void testSwitchCase() {
		Assert.assertEquals(Kind.SwitchCase, Trees.switchCase().kind());
		Assert.assertEquals(Kind.SwitchCase, TDTree.stateOf(Trees.switchCase()).kind());
	}

	@Test
	public void testSwitchStmt() {
		Assert.assertEquals(Kind.SwitchStmt, Trees.switchStmt().kind());
		Assert.assertEquals(Kind.SwitchStmt, TDTree.stateOf(Trees.switchStmt()).kind());
	}

	@Test
	public void testSynchronizedStmt() {
		Assert.assertEquals(Kind.SynchronizedStmt, Trees.synchronizedStmt().kind());
		Assert.assertEquals(Kind.SynchronizedStmt, TDTree.stateOf(Trees.synchronizedStmt()).kind());
	}

	@Test
	public void testThrowStmt() {
		Assert.assertEquals(Kind.ThrowStmt, Trees.throwStmt().kind());
		Assert.assertEquals(Kind.ThrowStmt, TDTree.stateOf(Trees.throwStmt()).kind());
	}

	@Test
	public void testTryStmt() {
		Assert.assertEquals(Kind.TryStmt, Trees.tryStmt().kind());
		Assert.assertEquals(Kind.TryStmt, TDTree.stateOf(Trees.tryStmt()).kind());
	}

	@Test
	public void testTypeDeclarationStmt() {
		Assert.assertEquals(Kind.TypeDeclarationStmt, Trees.typeDeclarationStmt().kind());
		Assert.assertEquals(Kind.TypeDeclarationStmt, TDTree.stateOf(Trees.typeDeclarationStmt()).kind());
	}

	@Test
	public void testWhileStmt() {
		Assert.assertEquals(Kind.WhileStmt, Trees.whileStmt().kind());
		Assert.assertEquals(Kind.WhileStmt, TDTree.stateOf(Trees.whileStmt()).kind());
	}

	@Test
	public void testArrayType() {
		Assert.assertEquals(Kind.ArrayType, Trees.arrayType().kind());
		Assert.assertEquals(Kind.ArrayType, TDTree.stateOf(Trees.arrayType()).kind());
	}

	@Test
	public void testIntersectionType() {
		Assert.assertEquals(Kind.IntersectionType, Trees.intersectionType().kind());
		Assert.assertEquals(Kind.IntersectionType, TDTree.stateOf(Trees.intersectionType()).kind());
	}

	@Test
	public void testPrimitiveType() {
		Assert.assertEquals(Kind.PrimitiveType, Trees.primitiveType().kind());
		Assert.assertEquals(Kind.PrimitiveType, TDTree.stateOf(Trees.primitiveType()).kind());
	}

	@Test
	public void testQualifiedType() {
		Assert.assertEquals(Kind.QualifiedType, Trees.qualifiedType().kind());
		Assert.assertEquals(Kind.QualifiedType, TDTree.stateOf(Trees.qualifiedType()).kind());
	}

	@Test
	public void testUnionType() {
		Assert.assertEquals(Kind.UnionType, Trees.unionType().kind());
		Assert.assertEquals(Kind.UnionType, TDTree.stateOf(Trees.unionType()).kind());
	}

	@Test
	public void testUnknownType() {
		Assert.assertEquals(Kind.UnknownType, Trees.unknownType().kind());
		Assert.assertEquals(Kind.UnknownType, TDTree.stateOf(Trees.unknownType()).kind());
	}

	@Test
	public void testVoidType() {
		Assert.assertEquals(Kind.VoidType, Trees.voidType().kind());
		Assert.assertEquals(Kind.VoidType, TDTree.stateOf(Trees.voidType()).kind());
	}

	@Test
	public void testWildcardType() {
		Assert.assertEquals(Kind.WildcardType, Trees.wildcardType().kind());
		Assert.assertEquals(Kind.WildcardType, TDTree.stateOf(Trees.wildcardType()).kind());
	}
}
