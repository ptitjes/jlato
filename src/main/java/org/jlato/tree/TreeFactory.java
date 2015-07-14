package org.jlato.tree;

import org.jlato.tree.decl.*;
import org.jlato.tree.expr.*;
import org.jlato.tree.name.*;
import org.jlato.tree.stmt.*;
import org.jlato.tree.type.*;

public abstract class TreeFactory {

	public static <EM extends Tree & ExtendedModifier> AnnotationDecl annotationDecl() {
		return new <EM>AnnotationDecl(NodeList.<EM>empty(), null, NodeList.<MemberDecl>empty());
	}

	public static <EM extends Tree & ExtendedModifier> AnnotationMemberDecl annotationMemberDecl() {
		return new <EM>AnnotationMemberDecl(NodeList.<EM>empty(), null, null, NodeList.<ArrayDim>empty(), null);
	}

	public static ArrayDim arrayDim() {
		return new ArrayDim(NodeList.<AnnotationExpr>empty());
	}

	public static <EM extends Tree & ExtendedModifier> ClassDecl classDecl() {
		return new <EM>ClassDecl(NodeList.<EM>empty(), null, NodeList.<TypeParameter>empty(), null, NodeList.<QualifiedType>empty(), NodeList.<MemberDecl>empty());
	}

	public static CompilationUnit compilationUnit() {
		return new CompilationUnit(null, null, NodeList.<ImportDecl>empty(), NodeList.<TypeDecl>empty());
	}

	public static <EM extends Tree & ExtendedModifier> ConstructorDecl constructorDecl() {
		return new <EM>ConstructorDecl(NodeList.<EM>empty(), NodeList.<TypeParameter>empty(), null, NodeList.<FormalParameter>empty(), NodeList.<QualifiedType>empty(), null);
	}

	public static EmptyMemberDecl emptyMemberDecl() {
		return new EmptyMemberDecl();
	}

	public static EmptyTypeDecl emptyTypeDecl() {
		return new EmptyTypeDecl();
	}

	public static <EM extends Tree & ExtendedModifier> EnumConstantDecl enumConstantDecl() {
		return new <EM>EnumConstantDecl(NodeList.<EM>empty(), null, NodeOption.<NodeList<Expr>>none(), NodeOption.<NodeList<MemberDecl>>none());
	}

	public static <EM extends Tree & ExtendedModifier> EnumDecl enumDecl() {
		return new <EM>EnumDecl(NodeList.<EM>empty(), null, NodeList.<QualifiedType>empty(), NodeList.<EnumConstantDecl>empty(), false, NodeList.<MemberDecl>empty());
	}

	public static <EM extends Tree & ExtendedModifier> FieldDecl fieldDecl() {
		return new <EM>FieldDecl(NodeList.<EM>empty(), null, NodeList.<VariableDeclarator>empty());
	}

	public static <EM extends Tree & ExtendedModifier> FormalParameter formalParameter() {
		return new <EM>FormalParameter(NodeList.<EM>empty(), null, false, null);
	}

	public static ImportDecl importDecl() {
		return new ImportDecl(null, false, false);
	}

	public static <EM extends Tree & ExtendedModifier> InitializerDecl initializerDecl() {
		return new <EM>InitializerDecl(NodeList.<EM>empty(), null);
	}

	public static <EM extends Tree & ExtendedModifier> InterfaceDecl interfaceDecl() {
		return new <EM>InterfaceDecl(NodeList.<EM>empty(), null, NodeList.<TypeParameter>empty(), NodeList.<QualifiedType>empty(), NodeList.<MemberDecl>empty());
	}

	public static <EM extends Tree & ExtendedModifier> LocalVariableDecl localVariableDecl() {
		return new <EM>LocalVariableDecl(NodeList.<EM>empty(), null, NodeList.<VariableDeclarator>empty());
	}

	public static <EM extends Tree & ExtendedModifier> MethodDecl methodDecl() {
		return new <EM>MethodDecl(NodeList.<EM>empty(), NodeList.<TypeParameter>empty(), null, null, NodeList.<FormalParameter>empty(), NodeList.<ArrayDim>empty(), NodeList.<QualifiedType>empty(), null);
	}

	public static PackageDecl packageDecl() {
		return new PackageDecl(NodeList.<AnnotationExpr>empty(), null);
	}

	public static TypeParameter typeParameter() {
		return new TypeParameter(NodeList.<AnnotationExpr>empty(), null, NodeList.<Type>empty());
	}

	public static VariableDeclarator variableDeclarator() {
		return new VariableDeclarator(null, null);
	}

	public static VariableDeclaratorId variableDeclaratorId() {
		return new VariableDeclaratorId(null, NodeList.<ArrayDim>empty());
	}

	public static ArrayAccessExpr arrayAccessExpr() {
		return new ArrayAccessExpr(null, null);
	}

	public static ArrayCreationExpr arrayCreationExpr() {
		return new ArrayCreationExpr(null, NodeList.<ArrayDimExpr>empty(), NodeList.<ArrayDim>empty(), null);
	}

	public static ArrayDimExpr arrayDimExpr() {
		return new ArrayDimExpr(NodeList.<AnnotationExpr>empty(), null);
	}

	public static ArrayInitializerExpr arrayInitializerExpr() {
		return new ArrayInitializerExpr(NodeList.<Expr>empty());
	}

	public static AssignExpr assignExpr() {
		return new AssignExpr(null, null, null);
	}

	public static BinaryExpr binaryExpr() {
		return new BinaryExpr(null, null, null);
	}

	public static CastExpr castExpr() {
		return new CastExpr(null, null);
	}

	public static ClassExpr classExpr() {
		return new ClassExpr(null);
	}

	public static ConditionalExpr conditionalExpr() {
		return new ConditionalExpr(null, null, null);
	}

	public static FieldAccessExpr fieldAccessExpr() {
		return new FieldAccessExpr(null, NodeList.<Type>empty(), null);
	}

	public static InstanceOfExpr instanceOfExpr() {
		return new InstanceOfExpr(null, null);
	}

	public static LambdaExpr lambdaExpr() {
		return new LambdaExpr(NodeList.<FormalParameter>empty(), null, null);
	}

	public static MarkerAnnotationExpr markerAnnotationExpr() {
		return new MarkerAnnotationExpr(null);
	}

	public static MemberValuePair memberValuePair() {
		return new MemberValuePair(null, null);
	}

	public static MethodInvocationExpr methodInvocationExpr() {
		return new MethodInvocationExpr(null, NodeList.<Type>empty(), null, NodeList.<Expr>empty());
	}

	public static MethodReferenceExpr methodReferenceExpr() {
		return new MethodReferenceExpr(null, NodeList.<Type>empty(), null);
	}

	public static NormalAnnotationExpr normalAnnotationExpr() {
		return new NormalAnnotationExpr(null, NodeList.<MemberValuePair>empty());
	}

	public static ObjectCreationExpr objectCreationExpr() {
		return new ObjectCreationExpr(null, NodeList.<Type>empty(), null, NodeList.<Expr>empty(), NodeList.<MemberDecl>empty());
	}

	public static ParenthesizedExpr parenthesizedExpr() {
		return new ParenthesizedExpr(null);
	}

	public static SingleMemberAnnotationExpr singleMemberAnnotationExpr() {
		return new SingleMemberAnnotationExpr(null, null);
	}

	public static SuperExpr superExpr() {
		return new SuperExpr(null);
	}

	public static ThisExpr thisExpr() {
		return new ThisExpr(null);
	}

	public static TypeExpr typeExpr() {
		return new TypeExpr(null);
	}

	public static UnaryExpr unaryExpr() {
		return new UnaryExpr(null, null);
	}

	public static VariableDeclarationExpr variableDeclarationExpr() {
		return new VariableDeclarationExpr(null);
	}

	public static Name name() {
		return new Name(null);
	}

	public static QualifiedName qualifiedName() {
		return new QualifiedName(NodeOption.<QualifiedName>none(), null);
	}

	public static AssertStmt assertStmt() {
		return new AssertStmt(null, null);
	}

	public static BlockStmt blockStmt() {
		return new BlockStmt(NodeList.<Stmt>empty());
	}

	public static BreakStmt breakStmt() {
		return new BreakStmt(null);
	}

	public static CatchClause catchClause() {
		return new CatchClause(null, null);
	}

	public static ContinueStmt continueStmt() {
		return new ContinueStmt(null);
	}

	public static DoStmt doStmt() {
		return new DoStmt(null, null);
	}

	public static EmptyStmt emptyStmt() {
		return new EmptyStmt();
	}

	public static ExplicitConstructorInvocationStmt explicitConstructorInvocationStmt() {
		return new ExplicitConstructorInvocationStmt(NodeList.<Type>empty(), false, null, NodeList.<Expr>empty());
	}

	public static ExpressionStmt expressionStmt() {
		return new ExpressionStmt(null);
	}

	public static ForStmt forStmt() {
		return new ForStmt(NodeList.<Expr>empty(), null, NodeList.<Expr>empty(), null);
	}

	public static ForeachStmt foreachStmt() {
		return new ForeachStmt(null, null, null);
	}

	public static IfStmt ifStmt() {
		return new IfStmt(null, null, null);
	}

	public static LabeledStmt labeledStmt() {
		return new LabeledStmt(null, null);
	}

	public static ReturnStmt returnStmt() {
		return new ReturnStmt(null);
	}

	public static SwitchCase switchCase() {
		return new SwitchCase(null, NodeList.<Stmt>empty());
	}

	public static SwitchStmt switchStmt() {
		return new SwitchStmt(null, NodeList.<SwitchCase>empty());
	}

	public static SynchronizedStmt synchronizedStmt() {
		return new SynchronizedStmt(null, null);
	}

	public static ThrowStmt throwStmt() {
		return new ThrowStmt(null);
	}

	public static TryStmt tryStmt() {
		return new TryStmt(NodeList.<VariableDeclarationExpr>empty(), null, NodeList.<CatchClause>empty(), null);
	}

	public static TypeDeclarationStmt typeDeclarationStmt() {
		return new TypeDeclarationStmt(null);
	}

	public static WhileStmt whileStmt() {
		return new WhileStmt(null, null);
	}

	public static ArrayType arrayType() {
		return new ArrayType(NodeList.<AnnotationExpr>empty(), null, NodeList.<ArrayDim>empty());
	}

	public static IntersectionType intersectionType() {
		return new IntersectionType(NodeList.<Type>empty());
	}

	public static PrimitiveType primitiveType() {
		return new PrimitiveType(NodeList.<AnnotationExpr>empty(), null);
	}

	public static QualifiedType qualifiedType() {
		return new QualifiedType(NodeList.<AnnotationExpr>empty(), NodeOption.<QualifiedType>none(), null, NodeOption.<NodeList<Type>>none());
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
		return new WildcardType(NodeList.<AnnotationExpr>empty(), null, null);
	}
}
