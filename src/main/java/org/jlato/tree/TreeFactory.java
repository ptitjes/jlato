package org.jlato.tree;

import org.jlato.tree.decl.*;
import org.jlato.tree.expr.*;
import org.jlato.tree.name.Name;
import org.jlato.tree.name.QualifiedName;
import org.jlato.tree.stmt.*;
import org.jlato.tree.type.*;

public abstract class TreeFactory {

	public static AnnotationDecl annotationDecl() {
		return new AnnotationDecl(NodeList.<ExtendedModifier>empty(), null, NodeList.<MemberDecl>empty());
	}

	public static AnnotationMemberDecl annotationMemberDecl() {
		return new AnnotationMemberDecl(NodeList.<ExtendedModifier>empty(), null, null, NodeList.<ArrayDim>empty(), NodeOption.<Expr>none());
	}

	public static ArrayDim arrayDim() {
		return new ArrayDim(NodeList.<AnnotationExpr>empty());
	}

	public static ClassDecl classDecl() {
		return new ClassDecl(NodeList.<ExtendedModifier>empty(), null, NodeList.<TypeParameter>empty(), NodeOption.<QualifiedType>none(), NodeList.<QualifiedType>empty(), NodeList.<MemberDecl>empty());
	}

	public static CompilationUnit compilationUnit() {
		return new CompilationUnit(null, NodeList.<ImportDecl>empty(), NodeList.<TypeDecl>empty());
	}

	public static ConstructorDecl constructorDecl() {
		return new ConstructorDecl(NodeList.<ExtendedModifier>empty(), NodeList.<TypeParameter>empty(), null, NodeList.<FormalParameter>empty(), NodeList.<QualifiedType>empty(), null);
	}

	public static EmptyMemberDecl emptyMemberDecl() {
		return new EmptyMemberDecl();
	}

	public static EmptyTypeDecl emptyTypeDecl() {
		return new EmptyTypeDecl();
	}

	public static EnumConstantDecl enumConstantDecl() {
		return new EnumConstantDecl(NodeList.<ExtendedModifier>empty(), null, NodeOption.<NodeList<Expr>>none(), NodeOption.<NodeList<MemberDecl>>none());
	}

	public static EnumDecl enumDecl() {
		return new EnumDecl(NodeList.<ExtendedModifier>empty(), null, NodeList.<QualifiedType>empty(), NodeList.<EnumConstantDecl>empty(), false, NodeList.<MemberDecl>empty());
	}

	public static FieldDecl fieldDecl() {
		return new FieldDecl(NodeList.<ExtendedModifier>empty(), null, NodeList.<VariableDeclarator>empty());
	}

	public static FormalParameter formalParameter() {
		return new FormalParameter(NodeList.<ExtendedModifier>empty(), null, false, null);
	}

	public static ImportDecl importDecl() {
		return new ImportDecl(null, false, false);
	}

	public static InitializerDecl initializerDecl() {
		return new InitializerDecl(NodeList.<ExtendedModifier>empty(), null);
	}

	public static InterfaceDecl interfaceDecl() {
		return new InterfaceDecl(NodeList.<ExtendedModifier>empty(), null, NodeList.<TypeParameter>empty(), NodeList.<QualifiedType>empty(), NodeList.<MemberDecl>empty());
	}

	public static LocalVariableDecl localVariableDecl() {
		return new LocalVariableDecl(NodeList.<ExtendedModifier>empty(), null, NodeList.<VariableDeclarator>empty());
	}

	public static MethodDecl methodDecl() {
		return new MethodDecl(NodeList.<ExtendedModifier>empty(), NodeList.<TypeParameter>empty(), null, null, NodeList.<FormalParameter>empty(), NodeList.<ArrayDim>empty(), NodeList.<QualifiedType>empty(), NodeOption.<BlockStmt>none());
	}

	public static PackageDecl packageDecl() {
		return new PackageDecl(NodeList.<AnnotationExpr>empty(), null);
	}

	public static TypeParameter typeParameter() {
		return new TypeParameter(NodeList.<AnnotationExpr>empty(), null, NodeList.<Type>empty());
	}

	public static VariableDeclarator variableDeclarator() {
		return new VariableDeclarator(null, NodeOption.<Expr>none());
	}

	public static VariableDeclaratorId variableDeclaratorId() {
		return new VariableDeclaratorId(null, NodeList.<ArrayDim>empty());
	}

	public static ArrayAccessExpr arrayAccessExpr() {
		return new ArrayAccessExpr(null, null);
	}

	public static ArrayCreationExpr arrayCreationExpr() {
		return new ArrayCreationExpr(null, NodeList.<ArrayDimExpr>empty(), NodeList.<ArrayDim>empty(), NodeOption.<ArrayInitializerExpr>none());
	}

	public static ArrayDimExpr arrayDimExpr() {
		return new ArrayDimExpr(NodeList.<AnnotationExpr>empty(), null);
	}

	public static ArrayInitializerExpr arrayInitializerExpr() {
		return new ArrayInitializerExpr(NodeList.<Expr>empty(), false);
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
		return new FieldAccessExpr(NodeOption.<Expr>none(), null);
	}

	public static InstanceOfExpr instanceOfExpr() {
		return new InstanceOfExpr(null, null);
	}

	public static LambdaExpr lambdaExpr() {
		return new LambdaExpr(NodeList.<FormalParameter>empty(), true, NodeEither.<Expr, BlockStmt>right(blockStmt()));
	}

	public static MarkerAnnotationExpr markerAnnotationExpr() {
		return new MarkerAnnotationExpr(null);
	}

	public static MemberValuePair memberValuePair() {
		return new MemberValuePair(null, null);
	}

	public static MethodInvocationExpr methodInvocationExpr() {
		return new MethodInvocationExpr(NodeOption.<Expr>none(), NodeList.<Type>empty(), null, NodeList.<Expr>empty());
	}

	public static MethodReferenceExpr methodReferenceExpr() {
		return new MethodReferenceExpr(null, NodeList.<Type>empty(), null);
	}

	public static NormalAnnotationExpr normalAnnotationExpr() {
		return new NormalAnnotationExpr(null, NodeList.<MemberValuePair>empty());
	}

	public static ObjectCreationExpr objectCreationExpr() {
		return new ObjectCreationExpr(NodeOption.<Expr>none(), NodeList.<Type>empty(), null, NodeList.<Expr>empty(), NodeOption.<NodeList<MemberDecl>>none());
	}

	public static ParenthesizedExpr parenthesizedExpr() {
		return new ParenthesizedExpr(null);
	}

	public static SingleMemberAnnotationExpr singleMemberAnnotationExpr() {
		return new SingleMemberAnnotationExpr(null, null);
	}

	public static SuperExpr superExpr() {
		return new SuperExpr(NodeOption.<Expr>none());
	}

	public static ThisExpr thisExpr() {
		return new ThisExpr(NodeOption.<Expr>none());
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

	public static Name name(String string) {
		return new Name(string);
	}

	public static QualifiedName qualifiedName() {
		return new QualifiedName(NodeOption.<QualifiedName>none(), null);
	}

	public static QualifiedName qualifiedName(String nameString) {
		final String[] split = nameString.split("\\.");
		QualifiedName name = null;
		for (String part : split) {
			name = new QualifiedName(NodeOption.of(name), new Name(part));
		}
		return name;
	}

	public static AssertStmt assertStmt() {
		return new AssertStmt(null, NodeOption.<Expr>none());
	}

	public static BlockStmt blockStmt() {
		return new BlockStmt(NodeList.<Stmt>empty());
	}

	public static BreakStmt breakStmt() {
		return new BreakStmt(NodeOption.<Name>none());
	}

	public static CatchClause catchClause() {
		return new CatchClause(null, null);
	}

	public static ContinueStmt continueStmt() {
		return new ContinueStmt(NodeOption.<Name>none());
	}

	public static DoStmt doStmt() {
		return new DoStmt(null, null);
	}

	public static EmptyStmt emptyStmt() {
		return new EmptyStmt();
	}

	public static ExplicitConstructorInvocationStmt explicitConstructorInvocationStmt() {
		return new ExplicitConstructorInvocationStmt(NodeList.<Type>empty(), false, NodeOption.<Expr>none(), NodeList.<Expr>empty());
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
		return new IfStmt(null, null, NodeOption.<Stmt>none());
	}

	public static LabeledStmt labeledStmt() {
		return new LabeledStmt(null, null);
	}

	public static ReturnStmt returnStmt() {
		return new ReturnStmt(NodeOption.<Expr>none());
	}

	public static SwitchCase switchCase() {
		return new SwitchCase(NodeOption.<Expr>none(), NodeList.<Stmt>empty());
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
		return new TryStmt(NodeList.<VariableDeclarationExpr>empty(), false, null, NodeList.<CatchClause>empty(), NodeOption.<BlockStmt>none());
	}

	public static TypeDeclarationStmt typeDeclarationStmt() {
		return new TypeDeclarationStmt(null);
	}

	public static WhileStmt whileStmt() {
		return new WhileStmt(null, null);
	}

	public static ArrayType arrayType() {
		return new ArrayType(null, NodeList.<ArrayDim>empty());
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
		return new WildcardType(NodeList.<AnnotationExpr>empty(), NodeOption.<ReferenceType>none(), NodeOption.<ReferenceType>none());
	}
}
