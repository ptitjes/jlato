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
	public static AnnotationDecl annotationDecl() {
		return new TDAnnotationDecl(NodeList.<ExtendedModifier>empty(), (Name) null, NodeList.<MemberDecl>empty());
	}

	public static AnnotationDecl annotationDecl(Name name) {
		return new TDAnnotationDecl(NodeList.<ExtendedModifier>empty(), name, NodeList.<MemberDecl>empty());
	}

	@Deprecated
	public static AnnotationMemberDecl annotationMemberDecl() {
		return new TDAnnotationMemberDecl(NodeList.<ExtendedModifier>empty(), (Type) null, (Name) null, NodeList.<ArrayDim>empty(), NodeOption.<Expr>none());
	}

	public static AnnotationMemberDecl annotationMemberDecl(Type type, Name name) {
		return new TDAnnotationMemberDecl(NodeList.<ExtendedModifier>empty(), type, name, NodeList.<ArrayDim>empty(), NodeOption.<Expr>none());
	}

	public static ArrayDim arrayDim() {
		return new TDArrayDim(NodeList.<AnnotationExpr>empty());
	}

	@Deprecated
	public static ClassDecl classDecl() {
		return new TDClassDecl(NodeList.<ExtendedModifier>empty(), (Name) null, NodeList.<TypeParameter>empty(), NodeOption.<QualifiedType>none(), NodeList.<QualifiedType>empty(), NodeList.<MemberDecl>empty());
	}

	public static ClassDecl classDecl(Name name) {
		return new TDClassDecl(NodeList.<ExtendedModifier>empty(), name, NodeList.<TypeParameter>empty(), NodeOption.<QualifiedType>none(), NodeList.<QualifiedType>empty(), NodeList.<MemberDecl>empty());
	}

	@Deprecated
	public static CompilationUnit compilationUnit() {
		return new TDCompilationUnit((PackageDecl) null, NodeList.<ImportDecl>empty(), NodeList.<TypeDecl>empty());
	}

	public static CompilationUnit compilationUnit(PackageDecl packageDecl) {
		return new TDCompilationUnit(packageDecl, NodeList.<ImportDecl>empty(), NodeList.<TypeDecl>empty());
	}

	@Deprecated
	public static ConstructorDecl constructorDecl() {
		return new TDConstructorDecl(NodeList.<ExtendedModifier>empty(), NodeList.<TypeParameter>empty(), (Name) null, NodeList.<FormalParameter>empty(), NodeList.<QualifiedType>empty(), (BlockStmt) null);
	}

	public static ConstructorDecl constructorDecl(Name name, BlockStmt body) {
		return new TDConstructorDecl(NodeList.<ExtendedModifier>empty(), NodeList.<TypeParameter>empty(), name, NodeList.<FormalParameter>empty(), NodeList.<QualifiedType>empty(), body);
	}

	public static EmptyMemberDecl emptyMemberDecl() {
		return new TDEmptyMemberDecl();
	}

	public static EmptyTypeDecl emptyTypeDecl() {
		return new TDEmptyTypeDecl();
	}

	@Deprecated
	public static EnumConstantDecl enumConstantDecl() {
		return new TDEnumConstantDecl(NodeList.<ExtendedModifier>empty(), (Name) null, NodeOption.<NodeList<Expr>>none(), NodeOption.<NodeList<MemberDecl>>none());
	}

	public static EnumConstantDecl enumConstantDecl(Name name) {
		return new TDEnumConstantDecl(NodeList.<ExtendedModifier>empty(), name, NodeOption.<NodeList<Expr>>none(), NodeOption.<NodeList<MemberDecl>>none());
	}

	@Deprecated
	public static EnumDecl enumDecl() {
		return new TDEnumDecl(NodeList.<ExtendedModifier>empty(), (Name) null, NodeList.<QualifiedType>empty(), NodeList.<EnumConstantDecl>empty(), false, NodeList.<MemberDecl>empty());
	}

	public static EnumDecl enumDecl(Name name) {
		return new TDEnumDecl(NodeList.<ExtendedModifier>empty(), name, NodeList.<QualifiedType>empty(), NodeList.<EnumConstantDecl>empty(), false, NodeList.<MemberDecl>empty());
	}

	@Deprecated
	public static FieldDecl fieldDecl() {
		return new TDFieldDecl(NodeList.<ExtendedModifier>empty(), (Type) null, NodeList.<VariableDeclarator>empty());
	}

	public static FieldDecl fieldDecl(Type type) {
		return new TDFieldDecl(NodeList.<ExtendedModifier>empty(), type, NodeList.<VariableDeclarator>empty());
	}

	@Deprecated
	public static FormalParameter formalParameter() {
		return new TDFormalParameter(NodeList.<ExtendedModifier>empty(), (Type) null, false, (VariableDeclaratorId) null);
	}

	public static FormalParameter formalParameter(Type type, VariableDeclaratorId id) {
		return new TDFormalParameter(NodeList.<ExtendedModifier>empty(), type, false, id);
	}

	@Deprecated
	public static ImportDecl importDecl() {
		return new TDImportDecl((QualifiedName) null, false, false);
	}

	public static ImportDecl importDecl(QualifiedName name) {
		return new TDImportDecl(name, false, false);
	}

	@Deprecated
	public static InitializerDecl initializerDecl() {
		return new TDInitializerDecl(NodeList.<ExtendedModifier>empty(), (BlockStmt) null);
	}

	public static InitializerDecl initializerDecl(BlockStmt body) {
		return new TDInitializerDecl(NodeList.<ExtendedModifier>empty(), body);
	}

	@Deprecated
	public static InterfaceDecl interfaceDecl() {
		return new TDInterfaceDecl(NodeList.<ExtendedModifier>empty(), (Name) null, NodeList.<TypeParameter>empty(), NodeList.<QualifiedType>empty(), NodeList.<MemberDecl>empty());
	}

	public static InterfaceDecl interfaceDecl(Name name) {
		return new TDInterfaceDecl(NodeList.<ExtendedModifier>empty(), name, NodeList.<TypeParameter>empty(), NodeList.<QualifiedType>empty(), NodeList.<MemberDecl>empty());
	}

	@Deprecated
	public static LocalVariableDecl localVariableDecl() {
		return new TDLocalVariableDecl(NodeList.<ExtendedModifier>empty(), (Type) null, NodeList.<VariableDeclarator>empty());
	}

	public static LocalVariableDecl localVariableDecl(Type type) {
		return new TDLocalVariableDecl(NodeList.<ExtendedModifier>empty(), type, NodeList.<VariableDeclarator>empty());
	}

	@Deprecated
	public static MethodDecl methodDecl() {
		return new TDMethodDecl(NodeList.<ExtendedModifier>empty(), NodeList.<TypeParameter>empty(), (Type) null, (Name) null, NodeList.<FormalParameter>empty(), NodeList.<ArrayDim>empty(), NodeList.<QualifiedType>empty(), NodeOption.<BlockStmt>none());
	}

	public static MethodDecl methodDecl(Type type, Name name) {
		return new TDMethodDecl(NodeList.<ExtendedModifier>empty(), NodeList.<TypeParameter>empty(), type, name, NodeList.<FormalParameter>empty(), NodeList.<ArrayDim>empty(), NodeList.<QualifiedType>empty(), NodeOption.<BlockStmt>none());
	}

	@Deprecated
	public static Modifier modifier() {
		return new TDModifier((ModifierKeyword) null);
	}

	public static Modifier modifier(ModifierKeyword keyword) {
		return new TDModifier(keyword);
	}

	@Deprecated
	public static PackageDecl packageDecl() {
		return new TDPackageDecl(NodeList.<AnnotationExpr>empty(), (QualifiedName) null);
	}

	public static PackageDecl packageDecl(QualifiedName name) {
		return new TDPackageDecl(NodeList.<AnnotationExpr>empty(), name);
	}

	@Deprecated
	public static TypeParameter typeParameter() {
		return new TDTypeParameter(NodeList.<AnnotationExpr>empty(), (Name) null, NodeList.<Type>empty());
	}

	public static TypeParameter typeParameter(Name name) {
		return new TDTypeParameter(NodeList.<AnnotationExpr>empty(), name, NodeList.<Type>empty());
	}

	@Deprecated
	public static VariableDeclarator variableDeclarator() {
		return new TDVariableDeclarator((VariableDeclaratorId) null, NodeOption.<Expr>none());
	}

	public static VariableDeclarator variableDeclarator(VariableDeclaratorId id) {
		return new TDVariableDeclarator(id, NodeOption.<Expr>none());
	}

	@Deprecated
	public static VariableDeclaratorId variableDeclaratorId() {
		return new TDVariableDeclaratorId((Name) null, NodeList.<ArrayDim>empty());
	}

	public static VariableDeclaratorId variableDeclaratorId(Name name) {
		return new TDVariableDeclaratorId(name, NodeList.<ArrayDim>empty());
	}

	@Deprecated
	public static ArrayAccessExpr arrayAccessExpr() {
		return new TDArrayAccessExpr((Expr) null, (Expr) null);
	}

	public static ArrayAccessExpr arrayAccessExpr(Expr name, Expr index) {
		return new TDArrayAccessExpr(name, index);
	}

	@Deprecated
	public static ArrayCreationExpr arrayCreationExpr() {
		return new TDArrayCreationExpr((Type) null, NodeList.<ArrayDimExpr>empty(), NodeList.<ArrayDim>empty(), NodeOption.<ArrayInitializerExpr>none());
	}

	public static ArrayCreationExpr arrayCreationExpr(Type type) {
		return new TDArrayCreationExpr(type, NodeList.<ArrayDimExpr>empty(), NodeList.<ArrayDim>empty(), NodeOption.<ArrayInitializerExpr>none());
	}

	@Deprecated
	public static ArrayDimExpr arrayDimExpr() {
		return new TDArrayDimExpr(NodeList.<AnnotationExpr>empty(), (Expr) null);
	}

	public static ArrayDimExpr arrayDimExpr(Expr expr) {
		return new TDArrayDimExpr(NodeList.<AnnotationExpr>empty(), expr);
	}

	public static ArrayInitializerExpr arrayInitializerExpr() {
		return new TDArrayInitializerExpr(NodeList.<Expr>empty(), false);
	}

	@Deprecated
	public static AssignExpr assignExpr() {
		return new TDAssignExpr((Expr) null, (AssignOp) null, (Expr) null);
	}

	public static AssignExpr assignExpr(Expr target, AssignOp op, Expr value) {
		return new TDAssignExpr(target, op, value);
	}

	@Deprecated
	public static BinaryExpr binaryExpr() {
		return new TDBinaryExpr((Expr) null, (BinaryOp) null, (Expr) null);
	}

	public static BinaryExpr binaryExpr(Expr left, BinaryOp op, Expr right) {
		return new TDBinaryExpr(left, op, right);
	}

	@Deprecated
	public static CastExpr castExpr() {
		return new TDCastExpr((Type) null, (Expr) null);
	}

	public static CastExpr castExpr(Type type, Expr expr) {
		return new TDCastExpr(type, expr);
	}

	@Deprecated
	public static ClassExpr classExpr() {
		return new TDClassExpr((Type) null);
	}

	public static ClassExpr classExpr(Type type) {
		return new TDClassExpr(type);
	}

	@Deprecated
	public static ConditionalExpr conditionalExpr() {
		return new TDConditionalExpr((Expr) null, (Expr) null, (Expr) null);
	}

	public static ConditionalExpr conditionalExpr(Expr condition, Expr thenExpr, Expr elseExpr) {
		return new TDConditionalExpr(condition, thenExpr, elseExpr);
	}

	@Deprecated
	public static FieldAccessExpr fieldAccessExpr() {
		return new TDFieldAccessExpr(NodeOption.<Expr>none(), (Name) null);
	}

	public static FieldAccessExpr fieldAccessExpr(Name name) {
		return new TDFieldAccessExpr(NodeOption.<Expr>none(), name);
	}

	@Deprecated
	public static InstanceOfExpr instanceOfExpr() {
		return new TDInstanceOfExpr((Expr) null, (Type) null);
	}

	public static InstanceOfExpr instanceOfExpr(Expr expr, Type type) {
		return new TDInstanceOfExpr(expr, type);
	}

	public static LambdaExpr lambdaExpr() {
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
	public static MarkerAnnotationExpr markerAnnotationExpr() {
		return new TDMarkerAnnotationExpr((QualifiedName) null);
	}

	public static MarkerAnnotationExpr markerAnnotationExpr(QualifiedName name) {
		return new TDMarkerAnnotationExpr(name);
	}

	@Deprecated
	public static MemberValuePair memberValuePair() {
		return new TDMemberValuePair((Name) null, (Expr) null);
	}

	public static MemberValuePair memberValuePair(Name name, Expr value) {
		return new TDMemberValuePair(name, value);
	}

	@Deprecated
	public static MethodInvocationExpr methodInvocationExpr() {
		return new TDMethodInvocationExpr(NodeOption.<Expr>none(), NodeList.<Type>empty(), (Name) null, NodeList.<Expr>empty());
	}

	public static MethodInvocationExpr methodInvocationExpr(Name name) {
		return new TDMethodInvocationExpr(NodeOption.<Expr>none(), NodeList.<Type>empty(), name, NodeList.<Expr>empty());
	}

	@Deprecated
	public static MethodReferenceExpr methodReferenceExpr() {
		return new TDMethodReferenceExpr((Expr) null, NodeList.<Type>empty(), (Name) null);
	}

	public static MethodReferenceExpr methodReferenceExpr(Expr scope, Name name) {
		return new TDMethodReferenceExpr(scope, NodeList.<Type>empty(), name);
	}

	@Deprecated
	public static NormalAnnotationExpr normalAnnotationExpr() {
		return new TDNormalAnnotationExpr((QualifiedName) null, NodeList.<MemberValuePair>empty());
	}

	public static NormalAnnotationExpr normalAnnotationExpr(QualifiedName name) {
		return new TDNormalAnnotationExpr(name, NodeList.<MemberValuePair>empty());
	}

	@Deprecated
	public static ObjectCreationExpr objectCreationExpr() {
		return new TDObjectCreationExpr(NodeOption.<Expr>none(), NodeList.<Type>empty(), (QualifiedType) null, NodeList.<Expr>empty(), NodeOption.<NodeList<MemberDecl>>none());
	}

	public static ObjectCreationExpr objectCreationExpr(QualifiedType type) {
		return new TDObjectCreationExpr(NodeOption.<Expr>none(), NodeList.<Type>empty(), type, NodeList.<Expr>empty(), NodeOption.<NodeList<MemberDecl>>none());
	}

	@Deprecated
	public static ParenthesizedExpr parenthesizedExpr() {
		return new TDParenthesizedExpr((Expr) null);
	}

	public static ParenthesizedExpr parenthesizedExpr(Expr inner) {
		return new TDParenthesizedExpr(inner);
	}

	@Deprecated
	public static SingleMemberAnnotationExpr singleMemberAnnotationExpr() {
		return new TDSingleMemberAnnotationExpr((QualifiedName) null, (Expr) null);
	}

	public static SingleMemberAnnotationExpr singleMemberAnnotationExpr(QualifiedName name, Expr memberValue) {
		return new TDSingleMemberAnnotationExpr(name, memberValue);
	}

	public static SuperExpr superExpr() {
		return new TDSuperExpr(NodeOption.<Expr>none());
	}

	public static ThisExpr thisExpr() {
		return new TDThisExpr(NodeOption.<Expr>none());
	}

	@Deprecated
	public static TypeExpr typeExpr() {
		return new TDTypeExpr((Type) null);
	}

	public static TypeExpr typeExpr(Type type) {
		return new TDTypeExpr(type);
	}

	@Deprecated
	public static UnaryExpr unaryExpr() {
		return new TDUnaryExpr((UnaryOp) null, (Expr) null);
	}

	public static UnaryExpr unaryExpr(UnaryOp op, Expr expr) {
		return new TDUnaryExpr(op, expr);
	}

	@Deprecated
	public static VariableDeclarationExpr variableDeclarationExpr() {
		return new TDVariableDeclarationExpr((LocalVariableDecl) null);
	}

	public static VariableDeclarationExpr variableDeclarationExpr(LocalVariableDecl declaration) {
		return new TDVariableDeclarationExpr(declaration);
	}

	@Deprecated
	public static Name name() {
		return new TDName((String) null);
	}

	public static Name name(String id) {
		return new TDName(id);
	}

	@Deprecated
	public static QualifiedName qualifiedName() {
		return new TDQualifiedName(NodeOption.<QualifiedName>none(), (Name) null);
	}

	public static QualifiedName qualifiedName(Name name) {
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
	public static AssertStmt assertStmt() {
		return new TDAssertStmt((Expr) null, NodeOption.<Expr>none());
	}

	public static AssertStmt assertStmt(Expr check) {
		return new TDAssertStmt(check, NodeOption.<Expr>none());
	}

	public static BlockStmt blockStmt() {
		return new TDBlockStmt(NodeList.<Stmt>empty());
	}

	public static BreakStmt breakStmt() {
		return new TDBreakStmt(NodeOption.<Name>none());
	}

	@Deprecated
	public static CatchClause catchClause() {
		return new TDCatchClause((FormalParameter) null, (BlockStmt) null);
	}

	public static CatchClause catchClause(FormalParameter except, BlockStmt catchBlock) {
		return new TDCatchClause(except, catchBlock);
	}

	public static ContinueStmt continueStmt() {
		return new TDContinueStmt(NodeOption.<Name>none());
	}

	@Deprecated
	public static DoStmt doStmt() {
		return new TDDoStmt((Stmt) null, (Expr) null);
	}

	public static DoStmt doStmt(Stmt body, Expr condition) {
		return new TDDoStmt(body, condition);
	}

	public static EmptyStmt emptyStmt() {
		return new TDEmptyStmt();
	}

	public static ExplicitConstructorInvocationStmt explicitConstructorInvocationStmt() {
		return new TDExplicitConstructorInvocationStmt(NodeList.<Type>empty(), false, NodeOption.<Expr>none(), NodeList.<Expr>empty());
	}

	@Deprecated
	public static ExpressionStmt expressionStmt() {
		return new TDExpressionStmt((Expr) null);
	}

	public static ExpressionStmt expressionStmt(Expr expr) {
		return new TDExpressionStmt(expr);
	}

	@Deprecated
	public static ForStmt forStmt() {
		return new TDForStmt(NodeList.<Expr>empty(), (Expr) null, NodeList.<Expr>empty(), (Stmt) null);
	}

	public static ForStmt forStmt(Expr compare, Stmt body) {
		return new TDForStmt(NodeList.<Expr>empty(), compare, NodeList.<Expr>empty(), body);
	}

	@Deprecated
	public static ForeachStmt foreachStmt() {
		return new TDForeachStmt((VariableDeclarationExpr) null, (Expr) null, (Stmt) null);
	}

	public static ForeachStmt foreachStmt(VariableDeclarationExpr var, Expr iterable, Stmt body) {
		return new TDForeachStmt(var, iterable, body);
	}

	@Deprecated
	public static IfStmt ifStmt() {
		return new TDIfStmt((Expr) null, (Stmt) null, NodeOption.<Stmt>none());
	}

	public static IfStmt ifStmt(Expr condition, Stmt thenStmt) {
		return new TDIfStmt(condition, thenStmt, NodeOption.<Stmt>none());
	}

	@Deprecated
	public static LabeledStmt labeledStmt() {
		return new TDLabeledStmt((Name) null, (Stmt) null);
	}

	public static LabeledStmt labeledStmt(Name label, Stmt stmt) {
		return new TDLabeledStmt(label, stmt);
	}

	public static ReturnStmt returnStmt() {
		return new TDReturnStmt(NodeOption.<Expr>none());
	}

	public static SwitchCase switchCase() {
		return new TDSwitchCase(NodeOption.<Expr>none(), NodeList.<Stmt>empty());
	}

	@Deprecated
	public static SwitchStmt switchStmt() {
		return new TDSwitchStmt((Expr) null, NodeList.<SwitchCase>empty());
	}

	public static SwitchStmt switchStmt(Expr selector) {
		return new TDSwitchStmt(selector, NodeList.<SwitchCase>empty());
	}

	@Deprecated
	public static SynchronizedStmt synchronizedStmt() {
		return new TDSynchronizedStmt((Expr) null, (BlockStmt) null);
	}

	public static SynchronizedStmt synchronizedStmt(Expr expr, BlockStmt block) {
		return new TDSynchronizedStmt(expr, block);
	}

	@Deprecated
	public static ThrowStmt throwStmt() {
		return new TDThrowStmt((Expr) null);
	}

	public static ThrowStmt throwStmt(Expr expr) {
		return new TDThrowStmt(expr);
	}

	@Deprecated
	public static TryStmt tryStmt() {
		return new TDTryStmt(NodeList.<VariableDeclarationExpr>empty(), false, (BlockStmt) null, NodeList.<CatchClause>empty(), NodeOption.<BlockStmt>none());
	}

	public static TryStmt tryStmt(BlockStmt tryBlock) {
		return new TDTryStmt(NodeList.<VariableDeclarationExpr>empty(), false, tryBlock, NodeList.<CatchClause>empty(), NodeOption.<BlockStmt>none());
	}

	@Deprecated
	public static TypeDeclarationStmt typeDeclarationStmt() {
		return new TDTypeDeclarationStmt((TypeDecl) null);
	}

	public static TypeDeclarationStmt typeDeclarationStmt(TypeDecl typeDecl) {
		return new TDTypeDeclarationStmt(typeDecl);
	}

	@Deprecated
	public static WhileStmt whileStmt() {
		return new TDWhileStmt((Expr) null, (Stmt) null);
	}

	public static WhileStmt whileStmt(Expr condition, Stmt body) {
		return new TDWhileStmt(condition, body);
	}

	@Deprecated
	public static ArrayType arrayType() {
		return new TDArrayType((Type) null, NodeList.<ArrayDim>empty());
	}

	public static ArrayType arrayType(Type componentType) {
		return new TDArrayType(componentType, NodeList.<ArrayDim>empty());
	}

	public static IntersectionType intersectionType() {
		return new TDIntersectionType(NodeList.<Type>empty());
	}

	@Deprecated
	public static PrimitiveType primitiveType() {
		return new TDPrimitiveType(NodeList.<AnnotationExpr>empty(), (Primitive) null);
	}

	public static PrimitiveType primitiveType(Primitive primitive) {
		return new TDPrimitiveType(NodeList.<AnnotationExpr>empty(), primitive);
	}

	@Deprecated
	public static QualifiedType qualifiedType() {
		return new TDQualifiedType(NodeList.<AnnotationExpr>empty(), NodeOption.<QualifiedType>none(), (Name) null, NodeOption.<NodeList<Type>>none());
	}

	public static QualifiedType qualifiedType(Name name) {
		return new TDQualifiedType(NodeList.<AnnotationExpr>empty(), NodeOption.<QualifiedType>none(), name, NodeOption.<NodeList<Type>>none());
	}

	public static UnionType unionType() {
		return new TDUnionType(NodeList.<Type>empty());
	}

	public static UnknownType unknownType() {
		return new TDUnknownType();
	}

	public static VoidType voidType() {
		return new TDVoidType();
	}

	public static WildcardType wildcardType() {
		return new TDWildcardType(NodeList.<AnnotationExpr>empty(), NodeOption.<ReferenceType>none(), NodeOption.<ReferenceType>none());
	}
}
