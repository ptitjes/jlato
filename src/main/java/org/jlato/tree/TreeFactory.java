package org.jlato.tree;

import org.jlato.internal.bu.Literals;
import org.jlato.tree.decl.*;
import org.jlato.tree.expr.*;
import org.jlato.tree.name.*;
import org.jlato.tree.stmt.*;
import org.jlato.tree.type.*;

public abstract class TreeFactory {

	@Deprecated
	public static AnnotationDecl annotationDecl() {
		return new AnnotationDecl(NodeList.<ExtendedModifier>empty(), null, NodeList.<MemberDecl>empty());
	}

	public static AnnotationDecl annotationDecl(Name name) {
		return new AnnotationDecl(NodeList.<ExtendedModifier>empty(), name, NodeList.<MemberDecl>empty());
	}

	@Deprecated
	public static AnnotationMemberDecl annotationMemberDecl() {
		return new AnnotationMemberDecl(NodeList.<ExtendedModifier>empty(), null, null, NodeList.<ArrayDim>empty(), NodeOption.<Expr>none());
	}

	public static AnnotationMemberDecl annotationMemberDecl(Type type, Name name) {
		return new AnnotationMemberDecl(NodeList.<ExtendedModifier>empty(), type, name, NodeList.<ArrayDim>empty(), NodeOption.<Expr>none());
	}

	public static ArrayDim arrayDim() {
		return new ArrayDim(NodeList.<AnnotationExpr>empty());
	}

	@Deprecated
	public static ClassDecl classDecl() {
		return new ClassDecl(NodeList.<ExtendedModifier>empty(), null, NodeList.<TypeParameter>empty(), NodeOption.<QualifiedType>none(), NodeList.<QualifiedType>empty(), NodeList.<MemberDecl>empty());
	}

	public static ClassDecl classDecl(Name name) {
		return new ClassDecl(NodeList.<ExtendedModifier>empty(), name, NodeList.<TypeParameter>empty(), NodeOption.<QualifiedType>none(), NodeList.<QualifiedType>empty(), NodeList.<MemberDecl>empty());
	}

	@Deprecated
	public static CompilationUnit compilationUnit() {
		return new CompilationUnit(null, NodeList.<ImportDecl>empty(), NodeList.<TypeDecl>empty());
	}

	public static CompilationUnit compilationUnit(PackageDecl packageDecl) {
		return new CompilationUnit(packageDecl, NodeList.<ImportDecl>empty(), NodeList.<TypeDecl>empty());
	}

	@Deprecated
	public static ConstructorDecl constructorDecl() {
		return new ConstructorDecl(NodeList.<ExtendedModifier>empty(), NodeList.<TypeParameter>empty(), null, NodeList.<FormalParameter>empty(), NodeList.<QualifiedType>empty(), null);
	}

	public static ConstructorDecl constructorDecl(Name name, BlockStmt body) {
		return new ConstructorDecl(NodeList.<ExtendedModifier>empty(), NodeList.<TypeParameter>empty(), name, NodeList.<FormalParameter>empty(), NodeList.<QualifiedType>empty(), body);
	}

	public static EmptyMemberDecl emptyMemberDecl() {
		return new EmptyMemberDecl();
	}

	public static EmptyTypeDecl emptyTypeDecl() {
		return new EmptyTypeDecl();
	}

	@Deprecated
	public static EnumConstantDecl enumConstantDecl() {
		return new EnumConstantDecl(NodeList.<ExtendedModifier>empty(), null, NodeOption.<NodeList<Expr>>none(), NodeOption.<NodeList<MemberDecl>>none());
	}

	public static EnumConstantDecl enumConstantDecl(Name name) {
		return new EnumConstantDecl(NodeList.<ExtendedModifier>empty(), name, NodeOption.<NodeList<Expr>>none(), NodeOption.<NodeList<MemberDecl>>none());
	}

	@Deprecated
	public static EnumDecl enumDecl() {
		return new EnumDecl(NodeList.<ExtendedModifier>empty(), null, NodeList.<QualifiedType>empty(), NodeList.<EnumConstantDecl>empty(), false, NodeList.<MemberDecl>empty());
	}

	public static EnumDecl enumDecl(Name name) {
		return new EnumDecl(NodeList.<ExtendedModifier>empty(), name, NodeList.<QualifiedType>empty(), NodeList.<EnumConstantDecl>empty(), false, NodeList.<MemberDecl>empty());
	}

	@Deprecated
	public static FieldDecl fieldDecl() {
		return new FieldDecl(NodeList.<ExtendedModifier>empty(), null, NodeList.<VariableDeclarator>empty());
	}

	public static FieldDecl fieldDecl(Type type) {
		return new FieldDecl(NodeList.<ExtendedModifier>empty(), type, NodeList.<VariableDeclarator>empty());
	}

	@Deprecated
	public static FormalParameter formalParameter() {
		return new FormalParameter(NodeList.<ExtendedModifier>empty(), null, false, null);
	}

	public static FormalParameter formalParameter(Type type, VariableDeclaratorId id) {
		return new FormalParameter(NodeList.<ExtendedModifier>empty(), type, false, id);
	}

	@Deprecated
	public static ImportDecl importDecl() {
		return new ImportDecl(null, false, false);
	}

	public static ImportDecl importDecl(QualifiedName name) {
		return new ImportDecl(name, false, false);
	}

	@Deprecated
	public static InitializerDecl initializerDecl() {
		return new InitializerDecl(NodeList.<ExtendedModifier>empty(), null);
	}

	public static InitializerDecl initializerDecl(BlockStmt body) {
		return new InitializerDecl(NodeList.<ExtendedModifier>empty(), body);
	}

	@Deprecated
	public static InterfaceDecl interfaceDecl() {
		return new InterfaceDecl(NodeList.<ExtendedModifier>empty(), null, NodeList.<TypeParameter>empty(), NodeList.<QualifiedType>empty(), NodeList.<MemberDecl>empty());
	}

	public static InterfaceDecl interfaceDecl(Name name) {
		return new InterfaceDecl(NodeList.<ExtendedModifier>empty(), name, NodeList.<TypeParameter>empty(), NodeList.<QualifiedType>empty(), NodeList.<MemberDecl>empty());
	}

	@Deprecated
	public static LocalVariableDecl localVariableDecl() {
		return new LocalVariableDecl(NodeList.<ExtendedModifier>empty(), null, NodeList.<VariableDeclarator>empty());
	}

	public static LocalVariableDecl localVariableDecl(Type type) {
		return new LocalVariableDecl(NodeList.<ExtendedModifier>empty(), type, NodeList.<VariableDeclarator>empty());
	}

	@Deprecated
	public static MethodDecl methodDecl() {
		return new MethodDecl(NodeList.<ExtendedModifier>empty(), NodeList.<TypeParameter>empty(), null, null, NodeList.<FormalParameter>empty(), NodeList.<ArrayDim>empty(), NodeList.<QualifiedType>empty(), NodeOption.<BlockStmt>none());
	}

	public static MethodDecl methodDecl(Type type, Name name) {
		return new MethodDecl(NodeList.<ExtendedModifier>empty(), NodeList.<TypeParameter>empty(), type, name, NodeList.<FormalParameter>empty(), NodeList.<ArrayDim>empty(), NodeList.<QualifiedType>empty(), NodeOption.<BlockStmt>none());
	}

	@Deprecated
	public static Modifier modifier() {
		return new Modifier(null);
	}

	public static Modifier modifier(ModifierKeyword keyword) {
		return new Modifier(keyword);
	}

	@Deprecated
	public static PackageDecl packageDecl() {
		return new PackageDecl(NodeList.<AnnotationExpr>empty(), null);
	}

	public static PackageDecl packageDecl(QualifiedName name) {
		return new PackageDecl(NodeList.<AnnotationExpr>empty(), name);
	}

	@Deprecated
	public static TypeParameter typeParameter() {
		return new TypeParameter(NodeList.<AnnotationExpr>empty(), null, NodeList.<Type>empty());
	}

	public static TypeParameter typeParameter(Name name) {
		return new TypeParameter(NodeList.<AnnotationExpr>empty(), name, NodeList.<Type>empty());
	}

	@Deprecated
	public static VariableDeclarator variableDeclarator() {
		return new VariableDeclarator(null, NodeOption.<Expr>none());
	}

	public static VariableDeclarator variableDeclarator(VariableDeclaratorId id) {
		return new VariableDeclarator(id, NodeOption.<Expr>none());
	}

	@Deprecated
	public static VariableDeclaratorId variableDeclaratorId() {
		return new VariableDeclaratorId(null, NodeList.<ArrayDim>empty());
	}

	public static VariableDeclaratorId variableDeclaratorId(Name name) {
		return new VariableDeclaratorId(name, NodeList.<ArrayDim>empty());
	}

	@Deprecated
	public static ArrayAccessExpr arrayAccessExpr() {
		return new ArrayAccessExpr(null, null);
	}

	public static ArrayAccessExpr arrayAccessExpr(Expr name, Expr index) {
		return new ArrayAccessExpr(name, index);
	}

	@Deprecated
	public static ArrayCreationExpr arrayCreationExpr() {
		return new ArrayCreationExpr(null, NodeList.<ArrayDimExpr>empty(), NodeList.<ArrayDim>empty(), NodeOption.<ArrayInitializerExpr>none());
	}

	public static ArrayCreationExpr arrayCreationExpr(Type type) {
		return new ArrayCreationExpr(type, NodeList.<ArrayDimExpr>empty(), NodeList.<ArrayDim>empty(), NodeOption.<ArrayInitializerExpr>none());
	}

	@Deprecated
	public static ArrayDimExpr arrayDimExpr() {
		return new ArrayDimExpr(NodeList.<AnnotationExpr>empty(), null);
	}

	public static ArrayDimExpr arrayDimExpr(Expr expr) {
		return new ArrayDimExpr(NodeList.<AnnotationExpr>empty(), expr);
	}

	public static ArrayInitializerExpr arrayInitializerExpr() {
		return new ArrayInitializerExpr(NodeList.<Expr>empty(), false);
	}

	@Deprecated
	public static AssignExpr assignExpr() {
		return new AssignExpr(null, null, null);
	}

	public static AssignExpr assignExpr(Expr target, AssignOp op, Expr value) {
		return new AssignExpr(target, op, value);
	}

	@Deprecated
	public static BinaryExpr binaryExpr() {
		return new BinaryExpr(null, null, null);
	}

	public static BinaryExpr binaryExpr(Expr left, BinaryOp op, Expr right) {
		return new BinaryExpr(left, op, right);
	}

	@Deprecated
	public static CastExpr castExpr() {
		return new CastExpr(null, null);
	}

	public static CastExpr castExpr(Type type, Expr expr) {
		return new CastExpr(type, expr);
	}

	@Deprecated
	public static ClassExpr classExpr() {
		return new ClassExpr(null);
	}

	public static ClassExpr classExpr(Type type) {
		return new ClassExpr(type);
	}

	@Deprecated
	public static ConditionalExpr conditionalExpr() {
		return new ConditionalExpr(null, null, null);
	}

	public static ConditionalExpr conditionalExpr(Expr condition, Expr thenExpr, Expr elseExpr) {
		return new ConditionalExpr(condition, thenExpr, elseExpr);
	}

	@Deprecated
	public static FieldAccessExpr fieldAccessExpr() {
		return new FieldAccessExpr(NodeOption.<Expr>none(), null);
	}

	public static FieldAccessExpr fieldAccessExpr(Name name) {
		return new FieldAccessExpr(NodeOption.<Expr>none(), name);
	}

	@Deprecated
	public static InstanceOfExpr instanceOfExpr() {
		return new InstanceOfExpr(null, null);
	}

	public static InstanceOfExpr instanceOfExpr(Expr expr, Type type) {
		return new InstanceOfExpr(expr, type);
	}

	public static LambdaExpr lambdaExpr() {
		return new LambdaExpr(NodeList.<FormalParameter>empty(), true, NodeEither.<Expr, BlockStmt>right(blockStmt()));
	}

	public static LiteralExpr<Void> nullLiteralExpr() {
	return new LiteralExpr<Void>(Void.class, Literals.from(Void.class, null));
	}

	public static LiteralExpr<Boolean> literalExpr(boolean value) {
	return new LiteralExpr<Boolean>(Boolean.class, Literals.from(Boolean.class, value));
	}

	public static LiteralExpr<Integer> literalExpr(int value) {
	return new LiteralExpr<Integer>(Integer.class, Literals.from(Integer.class, value));
	}

	public static LiteralExpr<Long> literalExpr(long value) {
	return new LiteralExpr<Long>(Long.class, Literals.from(Long.class, value));
	}

	public static LiteralExpr<Float> literalExpr(float value) {
	return new LiteralExpr<Float>(Float.class, Literals.from(Float.class, value));
	}

	public static LiteralExpr<Double> literalExpr(double value) {
	return new LiteralExpr<Double>(Double.class, Literals.from(Double.class, value));
	}

	public static LiteralExpr<Character> literalExpr(char value) {
	return new LiteralExpr<Character>(Character.class, Literals.from(Character.class, value));
	}

	public static LiteralExpr<String> literalExpr(String value) {
	return new LiteralExpr<String>(String.class, Literals.from(String.class, value));
	}

	@Deprecated
	public static MarkerAnnotationExpr markerAnnotationExpr() {
		return new MarkerAnnotationExpr(null);
	}

	public static MarkerAnnotationExpr markerAnnotationExpr(QualifiedName name) {
		return new MarkerAnnotationExpr(name);
	}

	@Deprecated
	public static MemberValuePair memberValuePair() {
		return new MemberValuePair(null, null);
	}

	public static MemberValuePair memberValuePair(Name name, Expr value) {
		return new MemberValuePair(name, value);
	}

	@Deprecated
	public static MethodInvocationExpr methodInvocationExpr() {
		return new MethodInvocationExpr(NodeOption.<Expr>none(), NodeList.<Type>empty(), null, NodeList.<Expr>empty());
	}

	public static MethodInvocationExpr methodInvocationExpr(Name name) {
		return new MethodInvocationExpr(NodeOption.<Expr>none(), NodeList.<Type>empty(), name, NodeList.<Expr>empty());
	}

	@Deprecated
	public static MethodReferenceExpr methodReferenceExpr() {
		return new MethodReferenceExpr(null, NodeList.<Type>empty(), null);
	}

	public static MethodReferenceExpr methodReferenceExpr(Expr scope, Name name) {
		return new MethodReferenceExpr(scope, NodeList.<Type>empty(), name);
	}

	@Deprecated
	public static NormalAnnotationExpr normalAnnotationExpr() {
		return new NormalAnnotationExpr(null, NodeList.<MemberValuePair>empty());
	}

	public static NormalAnnotationExpr normalAnnotationExpr(QualifiedName name) {
		return new NormalAnnotationExpr(name, NodeList.<MemberValuePair>empty());
	}

	@Deprecated
	public static ObjectCreationExpr objectCreationExpr() {
		return new ObjectCreationExpr(NodeOption.<Expr>none(), NodeList.<Type>empty(), null, NodeList.<Expr>empty(), NodeOption.<NodeList<MemberDecl>>none());
	}

	public static ObjectCreationExpr objectCreationExpr(QualifiedType type) {
		return new ObjectCreationExpr(NodeOption.<Expr>none(), NodeList.<Type>empty(), type, NodeList.<Expr>empty(), NodeOption.<NodeList<MemberDecl>>none());
	}

	@Deprecated
	public static ParenthesizedExpr parenthesizedExpr() {
		return new ParenthesizedExpr(null);
	}

	public static ParenthesizedExpr parenthesizedExpr(Expr inner) {
		return new ParenthesizedExpr(inner);
	}

	@Deprecated
	public static SingleMemberAnnotationExpr singleMemberAnnotationExpr() {
		return new SingleMemberAnnotationExpr(null, null);
	}

	public static SingleMemberAnnotationExpr singleMemberAnnotationExpr(QualifiedName name, Expr memberValue) {
		return new SingleMemberAnnotationExpr(name, memberValue);
	}

	public static SuperExpr superExpr() {
		return new SuperExpr(NodeOption.<Expr>none());
	}

	public static ThisExpr thisExpr() {
		return new ThisExpr(NodeOption.<Expr>none());
	}

	@Deprecated
	public static TypeExpr typeExpr() {
		return new TypeExpr(null);
	}

	public static TypeExpr typeExpr(Type type) {
		return new TypeExpr(type);
	}

	@Deprecated
	public static UnaryExpr unaryExpr() {
		return new UnaryExpr(null, null);
	}

	public static UnaryExpr unaryExpr(UnaryOp op, Expr expr) {
		return new UnaryExpr(op, expr);
	}

	@Deprecated
	public static VariableDeclarationExpr variableDeclarationExpr() {
		return new VariableDeclarationExpr(null);
	}

	public static VariableDeclarationExpr variableDeclarationExpr(LocalVariableDecl declaration) {
		return new VariableDeclarationExpr(declaration);
	}

	@Deprecated
	public static Name name() {
		return new Name(null);
	}

	public static Name name(String id) {
		return new Name(id);
	}

	@Deprecated
	public static QualifiedName qualifiedName() {
		return new QualifiedName(NodeOption.<QualifiedName>none(), null);
	}

	public static QualifiedName qualifiedName(Name name) {
		return new QualifiedName(NodeOption.<QualifiedName>none(), name);
	}

	public static QualifiedName qualifiedName(String nameString) {
		final String[] split = nameString.split("\\.");
		QualifiedName name = null;
		for (String part : split) {
			name = new QualifiedName(NodeOption.of(name), new Name(part));
		}
		return name;
	}

	@Deprecated
	public static AssertStmt assertStmt() {
		return new AssertStmt(null, NodeOption.<Expr>none());
	}

	public static AssertStmt assertStmt(Expr check) {
		return new AssertStmt(check, NodeOption.<Expr>none());
	}

	public static BlockStmt blockStmt() {
		return new BlockStmt(NodeList.<Stmt>empty());
	}

	public static BreakStmt breakStmt() {
		return new BreakStmt(NodeOption.<Name>none());
	}

	@Deprecated
	public static CatchClause catchClause() {
		return new CatchClause(null, null);
	}

	public static CatchClause catchClause(FormalParameter except, BlockStmt catchBlock) {
		return new CatchClause(except, catchBlock);
	}

	public static ContinueStmt continueStmt() {
		return new ContinueStmt(NodeOption.<Name>none());
	}

	@Deprecated
	public static DoStmt doStmt() {
		return new DoStmt(null, null);
	}

	public static DoStmt doStmt(Stmt body, Expr condition) {
		return new DoStmt(body, condition);
	}

	public static EmptyStmt emptyStmt() {
		return new EmptyStmt();
	}

	public static ExplicitConstructorInvocationStmt explicitConstructorInvocationStmt() {
		return new ExplicitConstructorInvocationStmt(NodeList.<Type>empty(), false, NodeOption.<Expr>none(), NodeList.<Expr>empty());
	}

	@Deprecated
	public static ExpressionStmt expressionStmt() {
		return new ExpressionStmt(null);
	}

	public static ExpressionStmt expressionStmt(Expr expr) {
		return new ExpressionStmt(expr);
	}

	@Deprecated
	public static ForStmt forStmt() {
		return new ForStmt(NodeList.<Expr>empty(), null, NodeList.<Expr>empty(), null);
	}

	public static ForStmt forStmt(Expr compare, Stmt body) {
		return new ForStmt(NodeList.<Expr>empty(), compare, NodeList.<Expr>empty(), body);
	}

	@Deprecated
	public static ForeachStmt foreachStmt() {
		return new ForeachStmt(null, null, null);
	}

	public static ForeachStmt foreachStmt(VariableDeclarationExpr var, Expr iterable, Stmt body) {
		return new ForeachStmt(var, iterable, body);
	}

	@Deprecated
	public static IfStmt ifStmt() {
		return new IfStmt(null, null, NodeOption.<Stmt>none());
	}

	public static IfStmt ifStmt(Expr condition, Stmt thenStmt) {
		return new IfStmt(condition, thenStmt, NodeOption.<Stmt>none());
	}

	@Deprecated
	public static LabeledStmt labeledStmt() {
		return new LabeledStmt(null, null);
	}

	public static LabeledStmt labeledStmt(Name label, Stmt stmt) {
		return new LabeledStmt(label, stmt);
	}

	public static ReturnStmt returnStmt() {
		return new ReturnStmt(NodeOption.<Expr>none());
	}

	public static SwitchCase switchCase() {
		return new SwitchCase(NodeOption.<Expr>none(), NodeList.<Stmt>empty());
	}

	@Deprecated
	public static SwitchStmt switchStmt() {
		return new SwitchStmt(null, NodeList.<SwitchCase>empty());
	}

	public static SwitchStmt switchStmt(Expr selector) {
		return new SwitchStmt(selector, NodeList.<SwitchCase>empty());
	}

	@Deprecated
	public static SynchronizedStmt synchronizedStmt() {
		return new SynchronizedStmt(null, null);
	}

	public static SynchronizedStmt synchronizedStmt(Expr expr, BlockStmt block) {
		return new SynchronizedStmt(expr, block);
	}

	@Deprecated
	public static ThrowStmt throwStmt() {
		return new ThrowStmt(null);
	}

	public static ThrowStmt throwStmt(Expr expr) {
		return new ThrowStmt(expr);
	}

	@Deprecated
	public static TryStmt tryStmt() {
		return new TryStmt(NodeList.<VariableDeclarationExpr>empty(), false, null, NodeList.<CatchClause>empty(), NodeOption.<BlockStmt>none());
	}

	public static TryStmt tryStmt(BlockStmt tryBlock) {
		return new TryStmt(NodeList.<VariableDeclarationExpr>empty(), false, tryBlock, NodeList.<CatchClause>empty(), NodeOption.<BlockStmt>none());
	}

	@Deprecated
	public static TypeDeclarationStmt typeDeclarationStmt() {
		return new TypeDeclarationStmt(null);
	}

	public static TypeDeclarationStmt typeDeclarationStmt(TypeDecl typeDecl) {
		return new TypeDeclarationStmt(typeDecl);
	}

	@Deprecated
	public static WhileStmt whileStmt() {
		return new WhileStmt(null, null);
	}

	public static WhileStmt whileStmt(Expr condition, Stmt body) {
		return new WhileStmt(condition, body);
	}

	@Deprecated
	public static ArrayType arrayType() {
		return new ArrayType(null, NodeList.<ArrayDim>empty());
	}

	public static ArrayType arrayType(Type componentType) {
		return new ArrayType(componentType, NodeList.<ArrayDim>empty());
	}

	public static IntersectionType intersectionType() {
		return new IntersectionType(NodeList.<Type>empty());
	}

	@Deprecated
	public static PrimitiveType primitiveType() {
		return new PrimitiveType(NodeList.<AnnotationExpr>empty(), null);
	}

	public static PrimitiveType primitiveType(Primitive primitive) {
		return new PrimitiveType(NodeList.<AnnotationExpr>empty(), primitive);
	}

	@Deprecated
	public static QualifiedType qualifiedType() {
		return new QualifiedType(NodeList.<AnnotationExpr>empty(), NodeOption.<QualifiedType>none(), null, NodeOption.<NodeList<Type>>none());
	}

	public static QualifiedType qualifiedType(Name name) {
		return new QualifiedType(NodeList.<AnnotationExpr>empty(), NodeOption.<QualifiedType>none(), name, NodeOption.<NodeList<Type>>none());
	}

	public static UnionType unionType() {
		return new UnionType(NodeList.<Type>empty());
	}

	public static UnknownType unknownType() {
		return new UnknownType();
	}

	public static VoidType voidType() {
		return new VoidType();
	}

	public static WildcardType wildcardType() {
		return new WildcardType(NodeList.<AnnotationExpr>empty(), NodeOption.<ReferenceType>none(), NodeOption.<ReferenceType>none());
	}
}
