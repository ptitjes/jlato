package org.jlato.tree;

import org.jlato.internal.bu.Literals;
import org.jlato.internal.td.decl.*;
import org.jlato.internal.td.expr.*;
import org.jlato.internal.td.name.*;
import org.jlato.internal.td.stmt.*;
import org.jlato.internal.td.type.*;
import org.jlato.tree.decl.*;
import org.jlato.tree.expr.*;
import org.jlato.tree.name.*;
import org.jlato.tree.stmt.*;
import org.jlato.tree.type.*;

public abstract class TreeFactory {

	@Deprecated
	public static TDAnnotationDecl annotationDecl() {
		return new TDAnnotationDecl(NodeList.<ExtendedModifier>empty(), (Name) null, NodeList.<MemberDecl>empty());
	}

	public static TDAnnotationDecl annotationDecl(Name name) {
		return new TDAnnotationDecl(NodeList.<ExtendedModifier>empty(), name, NodeList.<MemberDecl>empty());
	}

	@Deprecated
	public static TDAnnotationMemberDecl annotationMemberDecl() {
		return new TDAnnotationMemberDecl(NodeList.<ExtendedModifier>empty(), (Type) null, (Name) null, NodeList.<ArrayDim>empty(), NodeOption.<Expr>none());
	}

	public static TDAnnotationMemberDecl annotationMemberDecl(Type type, Name name) {
		return new TDAnnotationMemberDecl(NodeList.<ExtendedModifier>empty(), type, name, NodeList.<ArrayDim>empty(), NodeOption.<Expr>none());
	}

	public static TDArrayDim arrayDim() {
		return new TDArrayDim(NodeList.<AnnotationExpr>empty());
	}

	@Deprecated
	public static TDClassDecl classDecl() {
		return new TDClassDecl(NodeList.<ExtendedModifier>empty(), (Name) null, NodeList.<TypeParameter>empty(), NodeOption.<QualifiedType>none(), NodeList.<QualifiedType>empty(), NodeList.<MemberDecl>empty());
	}

	public static TDClassDecl classDecl(Name name) {
		return new TDClassDecl(NodeList.<ExtendedModifier>empty(), name, NodeList.<TypeParameter>empty(), NodeOption.<QualifiedType>none(), NodeList.<QualifiedType>empty(), NodeList.<MemberDecl>empty());
	}

	@Deprecated
	public static TDCompilationUnit compilationUnit() {
		return new TDCompilationUnit((PackageDecl) null, NodeList.<ImportDecl>empty(), NodeList.<TypeDecl>empty());
	}

	public static TDCompilationUnit compilationUnit(PackageDecl packageDecl) {
		return new TDCompilationUnit(packageDecl, NodeList.<ImportDecl>empty(), NodeList.<TypeDecl>empty());
	}

	@Deprecated
	public static TDConstructorDecl constructorDecl() {
		return new TDConstructorDecl(NodeList.<ExtendedModifier>empty(), NodeList.<TypeParameter>empty(), (Name) null, NodeList.<FormalParameter>empty(), NodeList.<QualifiedType>empty(), (BlockStmt) null);
	}

	public static TDConstructorDecl constructorDecl(Name name, BlockStmt body) {
		return new TDConstructorDecl(NodeList.<ExtendedModifier>empty(), NodeList.<TypeParameter>empty(), name, NodeList.<FormalParameter>empty(), NodeList.<QualifiedType>empty(), body);
	}

	public static TDEmptyMemberDecl emptyMemberDecl() {
		return new TDEmptyMemberDecl();
	}

	public static TDEmptyTypeDecl emptyTypeDecl() {
		return new TDEmptyTypeDecl();
	}

	@Deprecated
	public static TDEnumConstantDecl enumConstantDecl() {
		return new TDEnumConstantDecl(NodeList.<ExtendedModifier>empty(), (Name) null, NodeOption.<NodeList<Expr>>none(), NodeOption.<NodeList<MemberDecl>>none());
	}

	public static TDEnumConstantDecl enumConstantDecl(Name name) {
		return new TDEnumConstantDecl(NodeList.<ExtendedModifier>empty(), name, NodeOption.<NodeList<Expr>>none(), NodeOption.<NodeList<MemberDecl>>none());
	}

	@Deprecated
	public static TDEnumDecl enumDecl() {
		return new TDEnumDecl(NodeList.<ExtendedModifier>empty(), (Name) null, NodeList.<QualifiedType>empty(), NodeList.<EnumConstantDecl>empty(), false, NodeList.<MemberDecl>empty());
	}

	public static TDEnumDecl enumDecl(Name name) {
		return new TDEnumDecl(NodeList.<ExtendedModifier>empty(), name, NodeList.<QualifiedType>empty(), NodeList.<EnumConstantDecl>empty(), false, NodeList.<MemberDecl>empty());
	}

	@Deprecated
	public static TDFieldDecl fieldDecl() {
		return new TDFieldDecl(NodeList.<ExtendedModifier>empty(), (Type) null, NodeList.<VariableDeclarator>empty());
	}

	public static TDFieldDecl fieldDecl(Type type) {
		return new TDFieldDecl(NodeList.<ExtendedModifier>empty(), type, NodeList.<VariableDeclarator>empty());
	}

	@Deprecated
	public static TDFormalParameter formalParameter() {
		return new TDFormalParameter(NodeList.<ExtendedModifier>empty(), (Type) null, false, (VariableDeclaratorId) null);
	}

	public static TDFormalParameter formalParameter(Type type, VariableDeclaratorId id) {
		return new TDFormalParameter(NodeList.<ExtendedModifier>empty(), type, false, id);
	}

	@Deprecated
	public static TDImportDecl importDecl() {
		return new TDImportDecl((QualifiedName) null, false, false);
	}

	public static TDImportDecl importDecl(QualifiedName name) {
		return new TDImportDecl(name, false, false);
	}

	@Deprecated
	public static TDInitializerDecl initializerDecl() {
		return new TDInitializerDecl(NodeList.<ExtendedModifier>empty(), (BlockStmt) null);
	}

	public static TDInitializerDecl initializerDecl(BlockStmt body) {
		return new TDInitializerDecl(NodeList.<ExtendedModifier>empty(), body);
	}

	@Deprecated
	public static TDInterfaceDecl interfaceDecl() {
		return new TDInterfaceDecl(NodeList.<ExtendedModifier>empty(), (Name) null, NodeList.<TypeParameter>empty(), NodeList.<QualifiedType>empty(), NodeList.<MemberDecl>empty());
	}

	public static TDInterfaceDecl interfaceDecl(Name name) {
		return new TDInterfaceDecl(NodeList.<ExtendedModifier>empty(), name, NodeList.<TypeParameter>empty(), NodeList.<QualifiedType>empty(), NodeList.<MemberDecl>empty());
	}

	@Deprecated
	public static TDLocalVariableDecl localVariableDecl() {
		return new TDLocalVariableDecl(NodeList.<ExtendedModifier>empty(), (Type) null, NodeList.<VariableDeclarator>empty());
	}

	public static TDLocalVariableDecl localVariableDecl(Type type) {
		return new TDLocalVariableDecl(NodeList.<ExtendedModifier>empty(), type, NodeList.<VariableDeclarator>empty());
	}

	@Deprecated
	public static TDMethodDecl methodDecl() {
		return new TDMethodDecl(NodeList.<ExtendedModifier>empty(), NodeList.<TypeParameter>empty(), (Type) null, (Name) null, NodeList.<FormalParameter>empty(), NodeList.<ArrayDim>empty(), NodeList.<QualifiedType>empty(), NodeOption.<BlockStmt>none());
	}

	public static TDMethodDecl methodDecl(Type type, Name name) {
		return new TDMethodDecl(NodeList.<ExtendedModifier>empty(), NodeList.<TypeParameter>empty(), type, name, NodeList.<FormalParameter>empty(), NodeList.<ArrayDim>empty(), NodeList.<QualifiedType>empty(), NodeOption.<BlockStmt>none());
	}

	@Deprecated
	public static TDModifier modifier() {
		return new TDModifier((ModifierKeyword) null);
	}

	public static TDModifier modifier(ModifierKeyword keyword) {
		return new TDModifier(keyword);
	}

	@Deprecated
	public static TDPackageDecl packageDecl() {
		return new TDPackageDecl(NodeList.<AnnotationExpr>empty(), (QualifiedName) null);
	}

	public static TDPackageDecl packageDecl(QualifiedName name) {
		return new TDPackageDecl(NodeList.<AnnotationExpr>empty(), name);
	}

	@Deprecated
	public static TDTypeParameter typeParameter() {
		return new TDTypeParameter(NodeList.<AnnotationExpr>empty(), (Name) null, NodeList.<Type>empty());
	}

	public static TDTypeParameter typeParameter(Name name) {
		return new TDTypeParameter(NodeList.<AnnotationExpr>empty(), name, NodeList.<Type>empty());
	}

	@Deprecated
	public static TDVariableDeclarator variableDeclarator() {
		return new TDVariableDeclarator((VariableDeclaratorId) null, NodeOption.<Expr>none());
	}

	public static TDVariableDeclarator variableDeclarator(VariableDeclaratorId id) {
		return new TDVariableDeclarator(id, NodeOption.<Expr>none());
	}

	@Deprecated
	public static TDVariableDeclaratorId variableDeclaratorId() {
		return new TDVariableDeclaratorId((Name) null, NodeList.<ArrayDim>empty());
	}

	public static TDVariableDeclaratorId variableDeclaratorId(Name name) {
		return new TDVariableDeclaratorId(name, NodeList.<ArrayDim>empty());
	}

	@Deprecated
	public static TDArrayAccessExpr arrayAccessExpr() {
		return new TDArrayAccessExpr((Expr) null, (Expr) null);
	}

	public static TDArrayAccessExpr arrayAccessExpr(Expr name, Expr index) {
		return new TDArrayAccessExpr(name, index);
	}

	@Deprecated
	public static TDArrayCreationExpr arrayCreationExpr() {
		return new TDArrayCreationExpr((Type) null, NodeList.<ArrayDimExpr>empty(), NodeList.<ArrayDim>empty(), NodeOption.<ArrayInitializerExpr>none());
	}

	public static TDArrayCreationExpr arrayCreationExpr(Type type) {
		return new TDArrayCreationExpr(type, NodeList.<ArrayDimExpr>empty(), NodeList.<ArrayDim>empty(), NodeOption.<ArrayInitializerExpr>none());
	}

	@Deprecated
	public static TDArrayDimExpr arrayDimExpr() {
		return new TDArrayDimExpr(NodeList.<AnnotationExpr>empty(), (Expr) null);
	}

	public static TDArrayDimExpr arrayDimExpr(Expr expr) {
		return new TDArrayDimExpr(NodeList.<AnnotationExpr>empty(), expr);
	}

	public static TDArrayInitializerExpr arrayInitializerExpr() {
		return new TDArrayInitializerExpr(NodeList.<Expr>empty(), false);
	}

	@Deprecated
	public static TDAssignExpr assignExpr() {
		return new TDAssignExpr((Expr) null, (AssignOp) null, (Expr) null);
	}

	public static TDAssignExpr assignExpr(Expr target, AssignOp op, Expr value) {
		return new TDAssignExpr(target, op, value);
	}

	@Deprecated
	public static TDBinaryExpr binaryExpr() {
		return new TDBinaryExpr((Expr) null, (BinaryOp) null, (Expr) null);
	}

	public static TDBinaryExpr binaryExpr(Expr left, BinaryOp op, Expr right) {
		return new TDBinaryExpr(left, op, right);
	}

	@Deprecated
	public static TDCastExpr castExpr() {
		return new TDCastExpr((Type) null, (Expr) null);
	}

	public static TDCastExpr castExpr(Type type, Expr expr) {
		return new TDCastExpr(type, expr);
	}

	@Deprecated
	public static TDClassExpr classExpr() {
		return new TDClassExpr((Type) null);
	}

	public static TDClassExpr classExpr(Type type) {
		return new TDClassExpr(type);
	}

	@Deprecated
	public static TDConditionalExpr conditionalExpr() {
		return new TDConditionalExpr((Expr) null, (Expr) null, (Expr) null);
	}

	public static TDConditionalExpr conditionalExpr(Expr condition, Expr thenExpr, Expr elseExpr) {
		return new TDConditionalExpr(condition, thenExpr, elseExpr);
	}

	@Deprecated
	public static TDFieldAccessExpr fieldAccessExpr() {
		return new TDFieldAccessExpr(NodeOption.<Expr>none(), (Name) null);
	}

	public static TDFieldAccessExpr fieldAccessExpr(Name name) {
		return new TDFieldAccessExpr(NodeOption.<Expr>none(), name);
	}

	@Deprecated
	public static TDInstanceOfExpr instanceOfExpr() {
		return new TDInstanceOfExpr((Expr) null, (Type) null);
	}

	public static TDInstanceOfExpr instanceOfExpr(Expr expr, Type type) {
		return new TDInstanceOfExpr(expr, type);
	}

	public static TDLambdaExpr lambdaExpr() {
		return new TDLambdaExpr(NodeList.<FormalParameter>empty(), true, NodeEither.<Expr, BlockStmt>right(blockStmt()));
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

	@Deprecated
	public static TDMarkerAnnotationExpr markerAnnotationExpr() {
		return new TDMarkerAnnotationExpr((QualifiedName) null);
	}

	public static TDMarkerAnnotationExpr markerAnnotationExpr(QualifiedName name) {
		return new TDMarkerAnnotationExpr(name);
	}

	@Deprecated
	public static TDMemberValuePair memberValuePair() {
		return new TDMemberValuePair((Name) null, (Expr) null);
	}

	public static TDMemberValuePair memberValuePair(Name name, Expr value) {
		return new TDMemberValuePair(name, value);
	}

	@Deprecated
	public static TDMethodInvocationExpr methodInvocationExpr() {
		return new TDMethodInvocationExpr(NodeOption.<Expr>none(), NodeList.<Type>empty(), (Name) null, NodeList.<Expr>empty());
	}

	public static TDMethodInvocationExpr methodInvocationExpr(Name name) {
		return new TDMethodInvocationExpr(NodeOption.<Expr>none(), NodeList.<Type>empty(), name, NodeList.<Expr>empty());
	}

	@Deprecated
	public static TDMethodReferenceExpr methodReferenceExpr() {
		return new TDMethodReferenceExpr((Expr) null, NodeList.<Type>empty(), (Name) null);
	}

	public static TDMethodReferenceExpr methodReferenceExpr(Expr scope, Name name) {
		return new TDMethodReferenceExpr(scope, NodeList.<Type>empty(), name);
	}

	@Deprecated
	public static TDNormalAnnotationExpr normalAnnotationExpr() {
		return new TDNormalAnnotationExpr((QualifiedName) null, NodeList.<MemberValuePair>empty());
	}

	public static TDNormalAnnotationExpr normalAnnotationExpr(QualifiedName name) {
		return new TDNormalAnnotationExpr(name, NodeList.<MemberValuePair>empty());
	}

	@Deprecated
	public static TDObjectCreationExpr objectCreationExpr() {
		return new TDObjectCreationExpr(NodeOption.<Expr>none(), NodeList.<Type>empty(), (QualifiedType) null, NodeList.<Expr>empty(), NodeOption.<NodeList<MemberDecl>>none());
	}

	public static TDObjectCreationExpr objectCreationExpr(QualifiedType type) {
		return new TDObjectCreationExpr(NodeOption.<Expr>none(), NodeList.<Type>empty(), type, NodeList.<Expr>empty(), NodeOption.<NodeList<MemberDecl>>none());
	}

	@Deprecated
	public static TDParenthesizedExpr parenthesizedExpr() {
		return new TDParenthesizedExpr((Expr) null);
	}

	public static TDParenthesizedExpr parenthesizedExpr(Expr inner) {
		return new TDParenthesizedExpr(inner);
	}

	@Deprecated
	public static TDSingleMemberAnnotationExpr singleMemberAnnotationExpr() {
		return new TDSingleMemberAnnotationExpr((QualifiedName) null, (Expr) null);
	}

	public static TDSingleMemberAnnotationExpr singleMemberAnnotationExpr(QualifiedName name, Expr memberValue) {
		return new TDSingleMemberAnnotationExpr(name, memberValue);
	}

	public static TDSuperExpr superExpr() {
		return new TDSuperExpr(NodeOption.<Expr>none());
	}

	public static TDThisExpr thisExpr() {
		return new TDThisExpr(NodeOption.<Expr>none());
	}

	@Deprecated
	public static TDTypeExpr typeExpr() {
		return new TDTypeExpr((Type) null);
	}

	public static TDTypeExpr typeExpr(Type type) {
		return new TDTypeExpr(type);
	}

	@Deprecated
	public static TDUnaryExpr unaryExpr() {
		return new TDUnaryExpr((UnaryOp) null, (Expr) null);
	}

	public static TDUnaryExpr unaryExpr(UnaryOp op, Expr expr) {
		return new TDUnaryExpr(op, expr);
	}

	@Deprecated
	public static TDVariableDeclarationExpr variableDeclarationExpr() {
		return new TDVariableDeclarationExpr((LocalVariableDecl) null);
	}

	public static TDVariableDeclarationExpr variableDeclarationExpr(LocalVariableDecl declaration) {
		return new TDVariableDeclarationExpr(declaration);
	}

	@Deprecated
	public static TDName name() {
		return new TDName((String) null);
	}

	public static TDName name(String id) {
		return new TDName(id);
	}

	@Deprecated
	public static TDQualifiedName qualifiedName() {
		return new TDQualifiedName(NodeOption.<QualifiedName>none(), (Name) null);
	}

	public static TDQualifiedName qualifiedName(Name name) {
		return new TDQualifiedName(NodeOption.<QualifiedName>none(), name);
	}

	public static QualifiedName qualifiedName(String nameString) {
		final String[] split = nameString.split("\\.");
		QualifiedName name = null;
		for (String part : split) {
			name = new TDQualifiedName(NodeOption.of(name), new TDName(part));
		}
		return name;
	}

	@Deprecated
	public static TDAssertStmt assertStmt() {
		return new TDAssertStmt((Expr) null, NodeOption.<Expr>none());
	}

	public static TDAssertStmt assertStmt(Expr check) {
		return new TDAssertStmt(check, NodeOption.<Expr>none());
	}

	public static TDBlockStmt blockStmt() {
		return new TDBlockStmt(NodeList.<Stmt>empty());
	}

	public static TDBreakStmt breakStmt() {
		return new TDBreakStmt(NodeOption.<Name>none());
	}

	@Deprecated
	public static TDCatchClause catchClause() {
		return new TDCatchClause((FormalParameter) null, (BlockStmt) null);
	}

	public static TDCatchClause catchClause(FormalParameter except, BlockStmt catchBlock) {
		return new TDCatchClause(except, catchBlock);
	}

	public static TDContinueStmt continueStmt() {
		return new TDContinueStmt(NodeOption.<Name>none());
	}

	@Deprecated
	public static TDDoStmt doStmt() {
		return new TDDoStmt((Stmt) null, (Expr) null);
	}

	public static TDDoStmt doStmt(Stmt body, Expr condition) {
		return new TDDoStmt(body, condition);
	}

	public static TDEmptyStmt emptyStmt() {
		return new TDEmptyStmt();
	}

	public static TDExplicitConstructorInvocationStmt explicitConstructorInvocationStmt() {
		return new TDExplicitConstructorInvocationStmt(NodeList.<Type>empty(), false, NodeOption.<Expr>none(), NodeList.<Expr>empty());
	}

	@Deprecated
	public static TDExpressionStmt expressionStmt() {
		return new TDExpressionStmt((Expr) null);
	}

	public static TDExpressionStmt expressionStmt(Expr expr) {
		return new TDExpressionStmt(expr);
	}

	@Deprecated
	public static TDForStmt forStmt() {
		return new TDForStmt(NodeList.<Expr>empty(), (Expr) null, NodeList.<Expr>empty(), (Stmt) null);
	}

	public static TDForStmt forStmt(Expr compare, Stmt body) {
		return new TDForStmt(NodeList.<Expr>empty(), compare, NodeList.<Expr>empty(), body);
	}

	@Deprecated
	public static TDForeachStmt foreachStmt() {
		return new TDForeachStmt((VariableDeclarationExpr) null, (Expr) null, (Stmt) null);
	}

	public static TDForeachStmt foreachStmt(VariableDeclarationExpr var, Expr iterable, Stmt body) {
		return new TDForeachStmt(var, iterable, body);
	}

	@Deprecated
	public static TDIfStmt ifStmt() {
		return new TDIfStmt((Expr) null, (Stmt) null, NodeOption.<Stmt>none());
	}

	public static TDIfStmt ifStmt(Expr condition, Stmt thenStmt) {
		return new TDIfStmt(condition, thenStmt, NodeOption.<Stmt>none());
	}

	@Deprecated
	public static TDLabeledStmt labeledStmt() {
		return new TDLabeledStmt((Name) null, (Stmt) null);
	}

	public static TDLabeledStmt labeledStmt(Name label, Stmt stmt) {
		return new TDLabeledStmt(label, stmt);
	}

	public static TDReturnStmt returnStmt() {
		return new TDReturnStmt(NodeOption.<Expr>none());
	}

	public static TDSwitchCase switchCase() {
		return new TDSwitchCase(NodeOption.<Expr>none(), NodeList.<Stmt>empty());
	}

	@Deprecated
	public static TDSwitchStmt switchStmt() {
		return new TDSwitchStmt((Expr) null, NodeList.<SwitchCase>empty());
	}

	public static TDSwitchStmt switchStmt(Expr selector) {
		return new TDSwitchStmt(selector, NodeList.<SwitchCase>empty());
	}

	@Deprecated
	public static TDSynchronizedStmt synchronizedStmt() {
		return new TDSynchronizedStmt((Expr) null, (BlockStmt) null);
	}

	public static TDSynchronizedStmt synchronizedStmt(Expr expr, BlockStmt block) {
		return new TDSynchronizedStmt(expr, block);
	}

	@Deprecated
	public static TDThrowStmt throwStmt() {
		return new TDThrowStmt((Expr) null);
	}

	public static TDThrowStmt throwStmt(Expr expr) {
		return new TDThrowStmt(expr);
	}

	@Deprecated
	public static TDTryStmt tryStmt() {
		return new TDTryStmt(NodeList.<VariableDeclarationExpr>empty(), false, (BlockStmt) null, NodeList.<CatchClause>empty(), NodeOption.<BlockStmt>none());
	}

	public static TDTryStmt tryStmt(BlockStmt tryBlock) {
		return new TDTryStmt(NodeList.<VariableDeclarationExpr>empty(), false, tryBlock, NodeList.<CatchClause>empty(), NodeOption.<BlockStmt>none());
	}

	@Deprecated
	public static TDTypeDeclarationStmt typeDeclarationStmt() {
		return new TDTypeDeclarationStmt((TypeDecl) null);
	}

	public static TDTypeDeclarationStmt typeDeclarationStmt(TypeDecl typeDecl) {
		return new TDTypeDeclarationStmt(typeDecl);
	}

	@Deprecated
	public static TDWhileStmt whileStmt() {
		return new TDWhileStmt((Expr) null, (Stmt) null);
	}

	public static TDWhileStmt whileStmt(Expr condition, Stmt body) {
		return new TDWhileStmt(condition, body);
	}

	@Deprecated
	public static TDArrayType arrayType() {
		return new TDArrayType((Type) null, NodeList.<ArrayDim>empty());
	}

	public static TDArrayType arrayType(Type componentType) {
		return new TDArrayType(componentType, NodeList.<ArrayDim>empty());
	}

	public static TDIntersectionType intersectionType() {
		return new TDIntersectionType(NodeList.<Type>empty());
	}

	@Deprecated
	public static TDPrimitiveType primitiveType() {
		return new TDPrimitiveType(NodeList.<AnnotationExpr>empty(), (Primitive) null);
	}

	public static TDPrimitiveType primitiveType(Primitive primitive) {
		return new TDPrimitiveType(NodeList.<AnnotationExpr>empty(), primitive);
	}

	@Deprecated
	public static TDQualifiedType qualifiedType() {
		return new TDQualifiedType(NodeList.<AnnotationExpr>empty(), NodeOption.<QualifiedType>none(), (Name) null, NodeOption.<NodeList<Type>>none());
	}

	public static TDQualifiedType qualifiedType(Name name) {
		return new TDQualifiedType(NodeList.<AnnotationExpr>empty(), NodeOption.<QualifiedType>none(), name, NodeOption.<NodeList<Type>>none());
	}

	public static TDUnionType unionType() {
		return new TDUnionType(NodeList.<Type>empty());
	}

	public static TDUnknownType unknownType() {
		return new TDUnknownType();
	}

	public static TDVoidType voidType() {
		return new TDVoidType();
	}

	public static TDWildcardType wildcardType() {
		return new TDWildcardType(NodeList.<AnnotationExpr>empty(), NodeOption.<ReferenceType>none(), NodeOption.<ReferenceType>none());
	}
}
