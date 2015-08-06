package org.jlato.tree;

import org.jlato.internal.bu.Literals;
import org.jlato.internal.td.coll.TDNodeEither;
import org.jlato.internal.td.coll.TDNodeList;
import org.jlato.internal.td.coll.TDNodeOption;
import org.jlato.internal.td.decl.*;
import org.jlato.internal.td.expr.*;
import org.jlato.internal.td.name.TDName;
import org.jlato.internal.td.name.TDQualifiedName;
import org.jlato.internal.td.stmt.*;
import org.jlato.internal.td.type.*;
import org.jlato.tree.decl.*;
import org.jlato.tree.expr.*;
import org.jlato.tree.name.*;
import org.jlato.tree.stmt.*;
import org.jlato.tree.type.*;

public abstract class Trees {

	public static <T extends Tree> NodeOption<T> none() {
		return TDNodeOption.none();
	}

	public static <T extends Tree> NodeOption<T> some(T t) {
		return TDNodeOption.some(t);
	}

	public static <T extends Tree> NodeOption<T> optionOf(T t) {
		return TDNodeOption.of(t);
	}

	public static <TL extends Tree, TR extends Tree> NodeEither<TL, TR> left(TL t) {
		return TDNodeEither.<TL, TR>left(t);
	}

	public static <TL extends Tree, TR extends Tree> NodeEither<TL, TR> right(TR t) {
		return TDNodeEither.<TL, TR>right(t);
	}

	public static <T extends Tree> NodeList<T> emptyList() {
		return TDNodeList.empty();
	}

	public static <T extends Tree> NodeList<T> listOf(Iterable<T> ts) {
		return TDNodeList.of(ts);
	}

	public static <T extends Tree> NodeList<T> listOf(T t1) {
		return TDNodeList.of(t1);
	}

	public static <T extends Tree> NodeList<T> listOf(T t1, T t2) {
		return TDNodeList.of(t1, t2);
	}

	public static <T extends Tree> NodeList<T> listOf(T t1, T t2, T t3) {
		return TDNodeList.of(t1, t2, t3);
	}

	public static <T extends Tree> NodeList<T> listOf(T t1, T t2, T t3, T t4) {
		return TDNodeList.of(t1, t2, t3, t4);
	}

	public static <T extends Tree> NodeList<T> listOf(T t1, T t2, T t3, T t4, T t5) {
		return TDNodeList.of(t1, t2, t3, t4, t5);
	}

	public static <T extends Tree> NodeList<T> listOf(T t1, T t2, T t3, T t4, T t5, T t6) {
		return TDNodeList.of(t1, t2, t3, t4, t5, t6);
	}

	public static <T extends Tree> NodeList<T> listOf(T t1, T t2, T t3, T t4, T t5, T t6, T t7) {
		return TDNodeList.of(t1, t2, t3, t4, t5, t6, t7);
	}

	public static <T extends Tree> NodeList<T> listOf(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8) {
		return TDNodeList.of(t1, t2, t3, t4, t5, t6, t7, t8);
	}

	public static <T extends Tree> NodeList<T> listOf(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9) {
		return TDNodeList.of(t1, t2, t3, t4, t5, t6, t7, t8, t9);
	}

	public static <T extends Tree> NodeList<T> listOf(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10) {
		return TDNodeList.of(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
	}

	public static <T extends Tree> NodeList<T> listOf(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10, T t11) {
		return TDNodeList.of(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11);
	}

	public static <T extends Tree> NodeList<T> listOf(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10, T t11, T t12) {
		return TDNodeList.of(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
	}

	public static <T extends Tree> NodeList<T> listOf(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10, T t11, T t12, T t13) {
		return TDNodeList.of(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13);
	}

	public static <T extends Tree> NodeList<T> listOf(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10, T t11, T t12, T t13, T t14) {
		return TDNodeList.of(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14);
	}

	public static <T extends Tree> NodeList<T> listOf(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10, T t11, T t12, T t13, T t14, T t15) {
		return TDNodeList.of(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
	}

	public static <T extends Tree> NodeList<T> listOf(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10, T t11, T t12, T t13, T t14, T t15, T t16) {
		return TDNodeList.of(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16);
	}

	public static <T extends Tree> NodeList<T> listOf(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10, T t11, T t12, T t13, T t14, T t15, T t16, T t17) {
		return TDNodeList.of(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17);
	}

	public static <T extends Tree> NodeList<T> listOf(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10, T t11, T t12, T t13, T t14, T t15, T t16, T t17, T t18) {
		return TDNodeList.of(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18);
	}

	public static <T extends Tree> NodeList<T> listOf(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10, T t11, T t12, T t13, T t14, T t15, T t16, T t17, T t18, T t19) {
		return TDNodeList.of(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19);
	}

	public static <T extends Tree> NodeList<T> listOf(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10, T t11, T t12, T t13, T t14, T t15, T t16, T t17, T t18, T t19, T t20) {
		return TDNodeList.of(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20);
	}

	public static <T extends Tree> NodeList<T> listOf(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10, T t11, T t12, T t13, T t14, T t15, T t16, T t17, T t18, T t19, T t20, T t21) {
		return TDNodeList.of(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21);
	}

	public static <T extends Tree> NodeList<T> listOf(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10, T t11, T t12, T t13, T t14, T t15, T t16, T t17, T t18, T t19, T t20, T t21, T t22) {
		return TDNodeList.of(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22);
	}

	public static <T extends Tree> NodeList<T> listOf(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10, T t11, T t12, T t13, T t14, T t15, T t16, T t17, T t18, T t19, T t20, T t21, T t22, T t23) {
		return TDNodeList.of(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22, t23);
	}

	public static AnnotationDecl annotationDecl() {
		return new TDAnnotationDecl(Trees.<ExtendedModifier>emptyList(), null, Trees.<MemberDecl>emptyList());
	}

	public static AnnotationDecl annotationDecl(Name name) {
		return new TDAnnotationDecl(Trees.<ExtendedModifier>emptyList(), name, Trees.<MemberDecl>emptyList());
	}

	public static AnnotationMemberDecl annotationMemberDecl() {
		return new TDAnnotationMemberDecl(Trees.<ExtendedModifier>emptyList(), null, null, Trees.<ArrayDim>emptyList(), Trees.<Expr>none());
	}

	public static AnnotationMemberDecl annotationMemberDecl(Type type, Name name) {
		return new TDAnnotationMemberDecl(Trees.<ExtendedModifier>emptyList(), type, name, Trees.<ArrayDim>emptyList(), Trees.<Expr>none());
	}

	public static ArrayDim arrayDim() {
		return new TDArrayDim(Trees.<AnnotationExpr>emptyList());
	}

	public static ClassDecl classDecl() {
		return new TDClassDecl(Trees.<ExtendedModifier>emptyList(), null, Trees.<TypeParameter>emptyList(), Trees.<QualifiedType>none(), Trees.<QualifiedType>emptyList(), Trees.<MemberDecl>emptyList());
	}

	public static ClassDecl classDecl(Name name) {
		return new TDClassDecl(Trees.<ExtendedModifier>emptyList(), name, Trees.<TypeParameter>emptyList(), Trees.<QualifiedType>none(), Trees.<QualifiedType>emptyList(), Trees.<MemberDecl>emptyList());
	}

	public static CompilationUnit compilationUnit() {
		return new TDCompilationUnit(null, Trees.<ImportDecl>emptyList(), Trees.<TypeDecl>emptyList());
	}

	public static CompilationUnit compilationUnit(PackageDecl packageDecl) {
		return new TDCompilationUnit(packageDecl, Trees.<ImportDecl>emptyList(), Trees.<TypeDecl>emptyList());
	}

	public static ConstructorDecl constructorDecl() {
		return new TDConstructorDecl(Trees.<ExtendedModifier>emptyList(), Trees.<TypeParameter>emptyList(), null, Trees.<FormalParameter>emptyList(), Trees.<QualifiedType>emptyList(), Trees.blockStmt());
	}

	public static ConstructorDecl constructorDecl(Name name) {
		return new TDConstructorDecl(Trees.<ExtendedModifier>emptyList(), Trees.<TypeParameter>emptyList(), name, Trees.<FormalParameter>emptyList(), Trees.<QualifiedType>emptyList(), Trees.blockStmt());
	}

	public static EmptyMemberDecl emptyMemberDecl() {
		return new TDEmptyMemberDecl();
	}

	public static EmptyTypeDecl emptyTypeDecl() {
		return new TDEmptyTypeDecl();
	}

	public static EnumConstantDecl enumConstantDecl() {
		return new TDEnumConstantDecl(Trees.<ExtendedModifier>emptyList(), null, Trees.<NodeList<Expr>>none(), Trees.<NodeList<MemberDecl>>none());
	}

	public static EnumConstantDecl enumConstantDecl(Name name) {
		return new TDEnumConstantDecl(Trees.<ExtendedModifier>emptyList(), name, Trees.<NodeList<Expr>>none(), Trees.<NodeList<MemberDecl>>none());
	}

	public static EnumDecl enumDecl() {
		return new TDEnumDecl(Trees.<ExtendedModifier>emptyList(), null, Trees.<QualifiedType>emptyList(), Trees.<EnumConstantDecl>emptyList(), false, Trees.<MemberDecl>emptyList());
	}

	public static EnumDecl enumDecl(Name name) {
		return new TDEnumDecl(Trees.<ExtendedModifier>emptyList(), name, Trees.<QualifiedType>emptyList(), Trees.<EnumConstantDecl>emptyList(), false, Trees.<MemberDecl>emptyList());
	}

	public static FieldDecl fieldDecl() {
		return new TDFieldDecl(Trees.<ExtendedModifier>emptyList(), null, Trees.<VariableDeclarator>emptyList());
	}

	public static FieldDecl fieldDecl(Type type) {
		return new TDFieldDecl(Trees.<ExtendedModifier>emptyList(), type, Trees.<VariableDeclarator>emptyList());
	}

	public static FormalParameter formalParameter() {
		return new TDFormalParameter(Trees.<ExtendedModifier>emptyList(), null, false, null);
	}

	public static FormalParameter formalParameter(Type type, VariableDeclaratorId id) {
		return new TDFormalParameter(Trees.<ExtendedModifier>emptyList(), type, false, id);
	}

	public static ImportDecl importDecl() {
		return new TDImportDecl(null, false, false);
	}

	public static ImportDecl importDecl(QualifiedName name) {
		return new TDImportDecl(name, false, false);
	}

	public static InitializerDecl initializerDecl() {
		return new TDInitializerDecl(Trees.<ExtendedModifier>emptyList(), null);
	}

	public static InitializerDecl initializerDecl(BlockStmt body) {
		return new TDInitializerDecl(Trees.<ExtendedModifier>emptyList(), body);
	}

	public static InterfaceDecl interfaceDecl() {
		return new TDInterfaceDecl(Trees.<ExtendedModifier>emptyList(), null, Trees.<TypeParameter>emptyList(), Trees.<QualifiedType>emptyList(), Trees.<MemberDecl>emptyList());
	}

	public static InterfaceDecl interfaceDecl(Name name) {
		return new TDInterfaceDecl(Trees.<ExtendedModifier>emptyList(), name, Trees.<TypeParameter>emptyList(), Trees.<QualifiedType>emptyList(), Trees.<MemberDecl>emptyList());
	}

	public static LocalVariableDecl localVariableDecl() {
		return new TDLocalVariableDecl(Trees.<ExtendedModifier>emptyList(), null, Trees.<VariableDeclarator>emptyList());
	}

	public static LocalVariableDecl localVariableDecl(Type type) {
		return new TDLocalVariableDecl(Trees.<ExtendedModifier>emptyList(), type, Trees.<VariableDeclarator>emptyList());
	}

	public static MethodDecl methodDecl() {
		return new TDMethodDecl(Trees.<ExtendedModifier>emptyList(), Trees.<TypeParameter>emptyList(), null, null, Trees.<FormalParameter>emptyList(), Trees.<ArrayDim>emptyList(), Trees.<QualifiedType>emptyList(), Trees.<BlockStmt>none());
	}

	public static MethodDecl methodDecl(Type type, Name name) {
		return new TDMethodDecl(Trees.<ExtendedModifier>emptyList(), Trees.<TypeParameter>emptyList(), type, name, Trees.<FormalParameter>emptyList(), Trees.<ArrayDim>emptyList(), Trees.<QualifiedType>emptyList(), Trees.<BlockStmt>none());
	}

	public static Modifier modifier() {
		return new TDModifier((ModifierKeyword) null);
	}

	public static Modifier modifier(ModifierKeyword keyword) {
		return new TDModifier(keyword);
	}

	public static PackageDecl packageDecl() {
		return new TDPackageDecl(Trees.<AnnotationExpr>emptyList(), null);
	}

	public static PackageDecl packageDecl(QualifiedName name) {
		return new TDPackageDecl(Trees.<AnnotationExpr>emptyList(), name);
	}

	public static TypeParameter typeParameter() {
		return new TDTypeParameter(Trees.<AnnotationExpr>emptyList(), null, Trees.<Type>emptyList());
	}

	public static TypeParameter typeParameter(Name name) {
		return new TDTypeParameter(Trees.<AnnotationExpr>emptyList(), name, Trees.<Type>emptyList());
	}

	public static VariableDeclarator variableDeclarator() {
		return new TDVariableDeclarator(null, Trees.<Expr>none());
	}

	public static VariableDeclarator variableDeclarator(VariableDeclaratorId id) {
		return new TDVariableDeclarator(id, Trees.<Expr>none());
	}

	public static VariableDeclaratorId variableDeclaratorId() {
		return new TDVariableDeclaratorId(null, Trees.<ArrayDim>emptyList());
	}

	public static VariableDeclaratorId variableDeclaratorId(Name name) {
		return new TDVariableDeclaratorId(name, Trees.<ArrayDim>emptyList());
	}

	public static ArrayAccessExpr arrayAccessExpr() {
		return new TDArrayAccessExpr(null, null);
	}

	public static ArrayAccessExpr arrayAccessExpr(Expr name, Expr index) {
		return new TDArrayAccessExpr(name, index);
	}

	public static ArrayCreationExpr arrayCreationExpr() {
		return new TDArrayCreationExpr(null, Trees.<ArrayDimExpr>emptyList(), Trees.<ArrayDim>emptyList(), Trees.<ArrayInitializerExpr>none());
	}

	public static ArrayCreationExpr arrayCreationExpr(Type type) {
		return new TDArrayCreationExpr(type, Trees.<ArrayDimExpr>emptyList(), Trees.<ArrayDim>emptyList(), Trees.<ArrayInitializerExpr>none());
	}

	public static ArrayDimExpr arrayDimExpr() {
		return new TDArrayDimExpr(Trees.<AnnotationExpr>emptyList(), null);
	}

	public static ArrayDimExpr arrayDimExpr(Expr expr) {
		return new TDArrayDimExpr(Trees.<AnnotationExpr>emptyList(), expr);
	}

	public static ArrayInitializerExpr arrayInitializerExpr() {
		return new TDArrayInitializerExpr(Trees.<Expr>emptyList(), false);
	}

	public static AssignExpr assignExpr() {
		return new TDAssignExpr(null, null, null);
	}

	public static AssignExpr assignExpr(Expr target, AssignOp op, Expr value) {
		return new TDAssignExpr(target, op, value);
	}

	public static BinaryExpr binaryExpr() {
		return new TDBinaryExpr(null, null, null);
	}

	public static BinaryExpr binaryExpr(Expr left, BinaryOp op, Expr right) {
		return new TDBinaryExpr(left, op, right);
	}

	public static CastExpr castExpr() {
		return new TDCastExpr(null, null);
	}

	public static CastExpr castExpr(Type type, Expr expr) {
		return new TDCastExpr(type, expr);
	}

	public static ClassExpr classExpr() {
		return new TDClassExpr((Type) null);
	}

	public static ClassExpr classExpr(Type type) {
		return new TDClassExpr(type);
	}

	public static ConditionalExpr conditionalExpr() {
		return new TDConditionalExpr(null, null, null);
	}

	public static ConditionalExpr conditionalExpr(Expr condition, Expr thenExpr, Expr elseExpr) {
		return new TDConditionalExpr(condition, thenExpr, elseExpr);
	}

	public static FieldAccessExpr fieldAccessExpr() {
		return new TDFieldAccessExpr(Trees.<Expr>none(), null);
	}

	public static FieldAccessExpr fieldAccessExpr(Name name) {
		return new TDFieldAccessExpr(Trees.<Expr>none(), name);
	}

	public static InstanceOfExpr instanceOfExpr() {
		return new TDInstanceOfExpr(null, null);
	}

	public static InstanceOfExpr instanceOfExpr(Expr expr, Type type) {
		return new TDInstanceOfExpr(expr, type);
	}

	public static LambdaExpr lambdaExpr() {
		return new TDLambdaExpr(Trees.<FormalParameter>emptyList(), true, Trees.<Expr, BlockStmt>right(blockStmt()));
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

	public static MarkerAnnotationExpr markerAnnotationExpr() {
		return new TDMarkerAnnotationExpr((QualifiedName) null);
	}

	public static MarkerAnnotationExpr markerAnnotationExpr(QualifiedName name) {
		return new TDMarkerAnnotationExpr(name);
	}

	public static MemberValuePair memberValuePair() {
		return new TDMemberValuePair(null, null);
	}

	public static MemberValuePair memberValuePair(Name name, Expr value) {
		return new TDMemberValuePair(name, value);
	}

	public static MethodInvocationExpr methodInvocationExpr() {
		return new TDMethodInvocationExpr(Trees.<Expr>none(), Trees.<Type>emptyList(), null, Trees.<Expr>emptyList());
	}

	public static MethodInvocationExpr methodInvocationExpr(Name name) {
		return new TDMethodInvocationExpr(Trees.<Expr>none(), Trees.<Type>emptyList(), name, Trees.<Expr>emptyList());
	}

	public static MethodReferenceExpr methodReferenceExpr() {
		return new TDMethodReferenceExpr(null, Trees.<Type>emptyList(), null);
	}

	public static MethodReferenceExpr methodReferenceExpr(Expr scope, Name name) {
		return new TDMethodReferenceExpr(scope, Trees.<Type>emptyList(), name);
	}

	public static NormalAnnotationExpr normalAnnotationExpr() {
		return new TDNormalAnnotationExpr(null, Trees.<MemberValuePair>emptyList());
	}

	public static NormalAnnotationExpr normalAnnotationExpr(QualifiedName name) {
		return new TDNormalAnnotationExpr(name, Trees.<MemberValuePair>emptyList());
	}

	public static ObjectCreationExpr objectCreationExpr() {
		return new TDObjectCreationExpr(Trees.<Expr>none(), Trees.<Type>emptyList(), null, Trees.<Expr>emptyList(), Trees.<NodeList<MemberDecl>>none());
	}

	public static ObjectCreationExpr objectCreationExpr(QualifiedType type) {
		return new TDObjectCreationExpr(Trees.<Expr>none(), Trees.<Type>emptyList(), type, Trees.<Expr>emptyList(), Trees.<NodeList<MemberDecl>>none());
	}

	public static ParenthesizedExpr parenthesizedExpr() {
		return new TDParenthesizedExpr((Expr) null);
	}

	public static ParenthesizedExpr parenthesizedExpr(Expr inner) {
		return new TDParenthesizedExpr(inner);
	}

	public static SingleMemberAnnotationExpr singleMemberAnnotationExpr() {
		return new TDSingleMemberAnnotationExpr(null, null);
	}

	public static SingleMemberAnnotationExpr singleMemberAnnotationExpr(QualifiedName name, Expr memberValue) {
		return new TDSingleMemberAnnotationExpr(name, memberValue);
	}

	public static SuperExpr superExpr() {
		return new TDSuperExpr(Trees.<Expr>none());
	}

	public static ThisExpr thisExpr() {
		return new TDThisExpr(Trees.<Expr>none());
	}

	public static TypeExpr typeExpr() {
		return new TDTypeExpr((Type) null);
	}

	public static TypeExpr typeExpr(Type type) {
		return new TDTypeExpr(type);
	}

	public static UnaryExpr unaryExpr() {
		return new TDUnaryExpr(null, null);
	}

	public static UnaryExpr unaryExpr(UnaryOp op, Expr expr) {
		return new TDUnaryExpr(op, expr);
	}

	public static VariableDeclarationExpr variableDeclarationExpr() {
		return new TDVariableDeclarationExpr((LocalVariableDecl) null);
	}

	public static VariableDeclarationExpr variableDeclarationExpr(LocalVariableDecl declaration) {
		return new TDVariableDeclarationExpr(declaration);
	}

	public static Name name() {
		return new TDName((String) null);
	}

	public static Name name(String id) {
		return new TDName(id);
	}

	public static QualifiedName qualifiedName() {
		return new TDQualifiedName(Trees.<QualifiedName>none(), null);
	}

	public static QualifiedName qualifiedName(Name name) {
		return new TDQualifiedName(Trees.<QualifiedName>none(), name);
	}

	public static QualifiedName qualifiedName(String nameString) {
		final String[] split = nameString.split("\\.");
		QualifiedName name = null;
		for (String part : split) {
			name = new TDQualifiedName(optionOf(name), new TDName(part));
		}
		return name;
	}

	public static AssertStmt assertStmt() {
		return new TDAssertStmt(null, Trees.<Expr>none());
	}

	public static AssertStmt assertStmt(Expr check) {
		return new TDAssertStmt(check, Trees.<Expr>none());
	}

	public static BlockStmt blockStmt() {
		return new TDBlockStmt(Trees.<Stmt>emptyList());
	}

	public static BreakStmt breakStmt() {
		return new TDBreakStmt(Trees.<Name>none());
	}

	public static CatchClause catchClause() {
		return new TDCatchClause(null, null);
	}

	public static CatchClause catchClause(FormalParameter param, BlockStmt catchBlock) {
		return new TDCatchClause(param, catchBlock);
	}

	public static ContinueStmt continueStmt() {
		return new TDContinueStmt(Trees.<Name>none());
	}

	public static DoStmt doStmt() {
		return new TDDoStmt(null, null);
	}

	public static DoStmt doStmt(Stmt body, Expr condition) {
		return new TDDoStmt(body, condition);
	}

	public static EmptyStmt emptyStmt() {
		return new TDEmptyStmt();
	}

	public static ExplicitConstructorInvocationStmt explicitConstructorInvocationStmt() {
		return new TDExplicitConstructorInvocationStmt(Trees.<Type>emptyList(), false, Trees.<Expr>none(), Trees.<Expr>emptyList());
	}

	public static ExpressionStmt expressionStmt() {
		return new TDExpressionStmt((Expr) null);
	}

	public static ExpressionStmt expressionStmt(Expr expr) {
		return new TDExpressionStmt(expr);
	}

	public static ForStmt forStmt() {
		return new TDForStmt(Trees.<Expr>emptyList(), null, Trees.<Expr>emptyList(), null);
	}

	public static ForStmt forStmt(Expr compare, Stmt body) {
		return new TDForStmt(Trees.<Expr>emptyList(), compare, Trees.<Expr>emptyList(), body);
	}

	public static ForeachStmt foreachStmt() {
		return new TDForeachStmt(null, null, null);
	}

	public static ForeachStmt foreachStmt(VariableDeclarationExpr var, Expr iterable, Stmt body) {
		return new TDForeachStmt(var, iterable, body);
	}

	public static IfStmt ifStmt() {
		return new TDIfStmt(null, null, Trees.<Stmt>none());
	}

	public static IfStmt ifStmt(Expr condition, Stmt thenStmt) {
		return new TDIfStmt(condition, thenStmt, Trees.<Stmt>none());
	}

	public static LabeledStmt labeledStmt() {
		return new TDLabeledStmt(null, null);
	}

	public static LabeledStmt labeledStmt(Name label, Stmt stmt) {
		return new TDLabeledStmt(label, stmt);
	}

	public static ReturnStmt returnStmt() {
		return new TDReturnStmt(Trees.<Expr>none());
	}

	public static SwitchCase switchCase() {
		return new TDSwitchCase(Trees.<Expr>none(), Trees.<Stmt>emptyList());
	}

	public static SwitchStmt switchStmt() {
		return new TDSwitchStmt(null, Trees.<SwitchCase>emptyList());
	}

	public static SwitchStmt switchStmt(Expr selector) {
		return new TDSwitchStmt(selector, Trees.<SwitchCase>emptyList());
	}

	public static SynchronizedStmt synchronizedStmt() {
		return new TDSynchronizedStmt(null, null);
	}

	public static SynchronizedStmt synchronizedStmt(Expr expr, BlockStmt block) {
		return new TDSynchronizedStmt(expr, block);
	}

	public static ThrowStmt throwStmt() {
		return new TDThrowStmt((Expr) null);
	}

	public static ThrowStmt throwStmt(Expr expr) {
		return new TDThrowStmt(expr);
	}

	public static TryStmt tryStmt() {
		return new TDTryStmt(Trees.<VariableDeclarationExpr>emptyList(), false, null, Trees.<CatchClause>emptyList(), Trees.<BlockStmt>none());
	}

	public static TryStmt tryStmt(BlockStmt tryBlock) {
		return new TDTryStmt(Trees.<VariableDeclarationExpr>emptyList(), false, tryBlock, Trees.<CatchClause>emptyList(), Trees.<BlockStmt>none());
	}

	public static TypeDeclarationStmt typeDeclarationStmt() {
		return new TDTypeDeclarationStmt((TypeDecl) null);
	}

	public static TypeDeclarationStmt typeDeclarationStmt(TypeDecl typeDecl) {
		return new TDTypeDeclarationStmt(typeDecl);
	}

	public static WhileStmt whileStmt() {
		return new TDWhileStmt(null, null);
	}

	public static WhileStmt whileStmt(Expr condition, Stmt body) {
		return new TDWhileStmt(condition, body);
	}

	public static ArrayType arrayType() {
		return new TDArrayType(null, Trees.<ArrayDim>emptyList());
	}

	public static ArrayType arrayType(Type componentType) {
		return new TDArrayType(componentType, Trees.<ArrayDim>emptyList());
	}

	public static IntersectionType intersectionType() {
		return new TDIntersectionType(Trees.<Type>emptyList());
	}

	public static PrimitiveType primitiveType() {
		return new TDPrimitiveType(Trees.<AnnotationExpr>emptyList(), null);
	}

	public static PrimitiveType primitiveType(Primitive primitive) {
		return new TDPrimitiveType(Trees.<AnnotationExpr>emptyList(), primitive);
	}

	public static QualifiedType qualifiedType() {
		return new TDQualifiedType(Trees.<AnnotationExpr>emptyList(), Trees.<QualifiedType>none(), null, Trees.<NodeList<Type>>none());
	}

	public static QualifiedType qualifiedType(Name name) {
		return new TDQualifiedType(Trees.<AnnotationExpr>emptyList(), Trees.<QualifiedType>none(), name, Trees.<NodeList<Type>>none());
	}

	public static UnionType unionType() {
		return new TDUnionType(Trees.<Type>emptyList());
	}

	public static UnknownType unknownType() {
		return new TDUnknownType();
	}

	public static VoidType voidType() {
		return new TDVoidType();
	}

	public static WildcardType wildcardType() {
		return new TDWildcardType(Trees.<AnnotationExpr>emptyList(), Trees.<ReferenceType>none(), Trees.<ReferenceType>none());
	}
}
