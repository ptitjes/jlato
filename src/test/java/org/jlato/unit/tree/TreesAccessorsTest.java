package org.jlato.unit.tree;

import org.jlato.tree.NodeEither;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Trees;
import org.jlato.tree.decl.AnnotationDecl;
import org.jlato.tree.decl.AnnotationMemberDecl;
import org.jlato.tree.decl.ArrayDim;
import org.jlato.tree.decl.ClassDecl;
import org.jlato.tree.decl.CompilationUnit;
import org.jlato.tree.decl.ConstructorDecl;
import org.jlato.tree.decl.EnumConstantDecl;
import org.jlato.tree.decl.EnumDecl;
import org.jlato.tree.decl.ExtendedModifier;
import org.jlato.tree.decl.FieldDecl;
import org.jlato.tree.decl.FormalParameter;
import org.jlato.tree.decl.ImportDecl;
import org.jlato.tree.decl.InitializerDecl;
import org.jlato.tree.decl.InterfaceDecl;
import org.jlato.tree.decl.LocalVariableDecl;
import org.jlato.tree.decl.MemberDecl;
import org.jlato.tree.decl.MethodDecl;
import org.jlato.tree.decl.Modifier;
import org.jlato.tree.decl.ModifierKeyword;
import org.jlato.tree.decl.PackageDecl;
import org.jlato.tree.decl.TypeDecl;
import org.jlato.tree.decl.TypeParameter;
import org.jlato.tree.decl.VariableDeclarator;
import org.jlato.tree.decl.VariableDeclaratorId;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.expr.ArrayAccessExpr;
import org.jlato.tree.expr.ArrayCreationExpr;
import org.jlato.tree.expr.ArrayDimExpr;
import org.jlato.tree.expr.ArrayInitializerExpr;
import org.jlato.tree.expr.AssignExpr;
import org.jlato.tree.expr.AssignOp;
import org.jlato.tree.expr.BinaryExpr;
import org.jlato.tree.expr.BinaryOp;
import org.jlato.tree.expr.CastExpr;
import org.jlato.tree.expr.ClassExpr;
import org.jlato.tree.expr.ConditionalExpr;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.expr.FieldAccessExpr;
import org.jlato.tree.expr.InstanceOfExpr;
import org.jlato.tree.expr.LambdaExpr;
import org.jlato.tree.expr.MarkerAnnotationExpr;
import org.jlato.tree.expr.MemberValuePair;
import org.jlato.tree.expr.MethodInvocationExpr;
import org.jlato.tree.expr.MethodReferenceExpr;
import org.jlato.tree.expr.NormalAnnotationExpr;
import org.jlato.tree.expr.ObjectCreationExpr;
import org.jlato.tree.expr.ParenthesizedExpr;
import org.jlato.tree.expr.SingleMemberAnnotationExpr;
import org.jlato.tree.expr.SuperExpr;
import org.jlato.tree.expr.ThisExpr;
import org.jlato.tree.expr.TypeExpr;
import org.jlato.tree.expr.UnaryExpr;
import org.jlato.tree.expr.UnaryOp;
import org.jlato.tree.expr.VariableDeclarationExpr;
import org.jlato.tree.name.Name;
import org.jlato.tree.name.QualifiedName;
import org.jlato.tree.stmt.AssertStmt;
import org.jlato.tree.stmt.BlockStmt;
import org.jlato.tree.stmt.BreakStmt;
import org.jlato.tree.stmt.CatchClause;
import org.jlato.tree.stmt.ContinueStmt;
import org.jlato.tree.stmt.DoStmt;
import org.jlato.tree.stmt.ExplicitConstructorInvocationStmt;
import org.jlato.tree.stmt.ExpressionStmt;
import org.jlato.tree.stmt.ForStmt;
import org.jlato.tree.stmt.ForeachStmt;
import org.jlato.tree.stmt.IfStmt;
import org.jlato.tree.stmt.LabeledStmt;
import org.jlato.tree.stmt.ReturnStmt;
import org.jlato.tree.stmt.Stmt;
import org.jlato.tree.stmt.SwitchCase;
import org.jlato.tree.stmt.SwitchStmt;
import org.jlato.tree.stmt.SynchronizedStmt;
import org.jlato.tree.stmt.ThrowStmt;
import org.jlato.tree.stmt.TryStmt;
import org.jlato.tree.stmt.TypeDeclarationStmt;
import org.jlato.tree.stmt.WhileStmt;
import org.jlato.tree.type.ArrayType;
import org.jlato.tree.type.IntersectionType;
import org.jlato.tree.type.Primitive;
import org.jlato.tree.type.PrimitiveType;
import org.jlato.tree.type.QualifiedType;
import org.jlato.tree.type.ReferenceType;
import org.jlato.tree.type.Type;
import org.jlato.tree.type.UnionType;
import org.jlato.tree.type.WildcardType;
import org.jlato.unit.util.Arbitrary;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TreesAccessorsTest {

	@Test
	public void testAnnotationDecl() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			NodeList<ExtendedModifier> modifiers = arbitrary.arbitraryListExtendedModifier();
			Name name = arbitrary.arbitraryName();
			NodeList<MemberDecl> members = arbitrary.arbitraryListMemberDecl();

			AnnotationDecl t = Trees.annotationDecl().withModifiers(modifiers).withName(name).withMembers(members);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(members, t.members());

			t = Trees.annotationDecl(name).withModifiers(modifiers).withMembers(members);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(members, t.members());

			t = t.withName(name.id());
			Assert.assertEquals(name, t.name());
		}
	}

	@Test
	public void testAnnotationMemberDecl() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			NodeList<ExtendedModifier> modifiers = arbitrary.arbitraryListExtendedModifier();
			Type type = arbitrary.arbitraryType();
			Name name = arbitrary.arbitraryName();
			NodeList<ArrayDim> dims = arbitrary.arbitraryListArrayDim();
			NodeOption<Expr> defaultValue = arbitrary.arbitraryOptionExpr();

			AnnotationMemberDecl t = Trees.annotationMemberDecl().withModifiers(modifiers).withType(type).withName(name).withDims(dims).withDefaultValue(defaultValue);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(type, t.type());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(dims, t.dims());
			Assert.assertEquals(defaultValue, t.defaultValue());

			t = Trees.annotationMemberDecl(type, name).withModifiers(modifiers).withDims(dims).withDefaultValue(defaultValue);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(type, t.type());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(dims, t.dims());
			Assert.assertEquals(defaultValue, t.defaultValue());

			t = t.withName(name.id());
			Assert.assertEquals(name, t.name());

			t = t.withDefaultValue(defaultValue.get());
			Assert.assertEquals(defaultValue, t.defaultValue());
			t = t.withNoDefaultValue();
			Assert.assertEquals(Trees.<Expr>none(), t.defaultValue());
		}
	}

	@Test
	public void testArrayDim() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			NodeList<AnnotationExpr> annotations = arbitrary.arbitraryListAnnotationExpr();

			ArrayDim t = Trees.arrayDim().withAnnotations(annotations);
			Assert.assertEquals(annotations, t.annotations());
		}
	}

	@Test
	public void testClassDecl() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			NodeList<ExtendedModifier> modifiers = arbitrary.arbitraryListExtendedModifier();
			Name name = arbitrary.arbitraryName();
			NodeList<TypeParameter> typeParams = arbitrary.arbitraryListTypeParameter();
			NodeOption<QualifiedType> extendsClause = arbitrary.arbitraryOptionQualifiedType();
			NodeList<QualifiedType> implementsClause = arbitrary.arbitraryListQualifiedType();
			NodeList<MemberDecl> members = arbitrary.arbitraryListMemberDecl();

			ClassDecl t = Trees.classDecl().withModifiers(modifiers).withName(name).withTypeParams(typeParams).withExtendsClause(extendsClause).withImplementsClause(implementsClause).withMembers(members);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(typeParams, t.typeParams());
			Assert.assertEquals(extendsClause, t.extendsClause());
			Assert.assertEquals(implementsClause, t.implementsClause());
			Assert.assertEquals(members, t.members());

			t = Trees.classDecl(name).withModifiers(modifiers).withTypeParams(typeParams).withExtendsClause(extendsClause).withImplementsClause(implementsClause).withMembers(members);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(typeParams, t.typeParams());
			Assert.assertEquals(extendsClause, t.extendsClause());
			Assert.assertEquals(implementsClause, t.implementsClause());
			Assert.assertEquals(members, t.members());

			t = t.withName(name.id());
			Assert.assertEquals(name, t.name());

			t = t.withExtendsClause(extendsClause.get());
			Assert.assertEquals(extendsClause, t.extendsClause());
			t = t.withNoExtendsClause();
			Assert.assertEquals(Trees.<QualifiedType>none(), t.extendsClause());
		}
	}

	@Test
	public void testCompilationUnit() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			PackageDecl packageDecl = arbitrary.arbitraryPackageDecl();
			NodeList<ImportDecl> imports = arbitrary.arbitraryListImportDecl();
			NodeList<TypeDecl> types = arbitrary.arbitraryListTypeDecl();

			CompilationUnit t = Trees.compilationUnit().withPackageDecl(packageDecl).withImports(imports).withTypes(types);
			Assert.assertEquals(packageDecl, t.packageDecl());
			Assert.assertEquals(imports, t.imports());
			Assert.assertEquals(types, t.types());

			t = Trees.compilationUnit(packageDecl).withImports(imports).withTypes(types);
			Assert.assertEquals(packageDecl, t.packageDecl());
			Assert.assertEquals(imports, t.imports());
			Assert.assertEquals(types, t.types());
		}
	}

	@Test
	public void testConstructorDecl() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			NodeList<ExtendedModifier> modifiers = arbitrary.arbitraryListExtendedModifier();
			NodeList<TypeParameter> typeParams = arbitrary.arbitraryListTypeParameter();
			Name name = arbitrary.arbitraryName();
			NodeList<FormalParameter> params = arbitrary.arbitraryListFormalParameter();
			NodeList<QualifiedType> throwsClause = arbitrary.arbitraryListQualifiedType();
			BlockStmt body = arbitrary.arbitraryBlockStmt();

			ConstructorDecl t = Trees.constructorDecl().withModifiers(modifiers).withTypeParams(typeParams).withName(name).withParams(params).withThrowsClause(throwsClause).withBody(body);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(typeParams, t.typeParams());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(params, t.params());
			Assert.assertEquals(throwsClause, t.throwsClause());
			Assert.assertEquals(body, t.body());

			t = Trees.constructorDecl(name).withModifiers(modifiers).withTypeParams(typeParams).withParams(params).withThrowsClause(throwsClause).withBody(body);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(typeParams, t.typeParams());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(params, t.params());
			Assert.assertEquals(throwsClause, t.throwsClause());
			Assert.assertEquals(body, t.body());

			t = t.withName(name.id());
			Assert.assertEquals(name, t.name());
		}
	}

	@Test
	public void testEnumConstantDecl() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			NodeList<ExtendedModifier> modifiers = arbitrary.arbitraryListExtendedModifier();
			Name name = arbitrary.arbitraryName();
			NodeOption<NodeList<Expr>> args = arbitrary.arbitraryOptionListExpr();
			NodeOption<NodeList<MemberDecl>> classBody = arbitrary.arbitraryOptionListMemberDecl();

			EnumConstantDecl t = Trees.enumConstantDecl().withModifiers(modifiers).withName(name).withArgs(args).withClassBody(classBody);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(args, t.args());
			Assert.assertEquals(classBody, t.classBody());

			t = Trees.enumConstantDecl(name).withModifiers(modifiers).withArgs(args).withClassBody(classBody);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(args, t.args());
			Assert.assertEquals(classBody, t.classBody());

			t = t.withName(name.id());
			Assert.assertEquals(name, t.name());

			t = t.withArgs(args.get()).withClassBody(classBody.get());
			Assert.assertEquals(args, t.args());
			Assert.assertEquals(classBody, t.classBody());
			t = t.withNoArgs().withNoClassBody();
			Assert.assertEquals(Trees.<NodeList<Expr>>none(), t.args());
			Assert.assertEquals(Trees.<NodeList<MemberDecl>>none(), t.classBody());
		}
	}

	@Test
	public void testEnumDecl() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			NodeList<ExtendedModifier> modifiers = arbitrary.arbitraryListExtendedModifier();
			Name name = arbitrary.arbitraryName();
			NodeList<QualifiedType> implementsClause = arbitrary.arbitraryListQualifiedType();
			NodeList<EnumConstantDecl> enumConstants = arbitrary.arbitraryListEnumConstantDecl();
			boolean trailingComma = arbitrary.arbitraryBoolean();
			NodeList<MemberDecl> members = arbitrary.arbitraryListMemberDecl();

			EnumDecl t = Trees.enumDecl().withModifiers(modifiers).withName(name).withImplementsClause(implementsClause).withEnumConstants(enumConstants).withTrailingComma(trailingComma).withMembers(members);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(implementsClause, t.implementsClause());
			Assert.assertEquals(enumConstants, t.enumConstants());
			Assert.assertEquals(trailingComma, t.trailingComma());
			Assert.assertEquals(members, t.members());

			t = Trees.enumDecl(name).withModifiers(modifiers).withImplementsClause(implementsClause).withEnumConstants(enumConstants).withTrailingComma(trailingComma).withMembers(members);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(implementsClause, t.implementsClause());
			Assert.assertEquals(enumConstants, t.enumConstants());
			Assert.assertEquals(trailingComma, t.trailingComma());
			Assert.assertEquals(members, t.members());

			t = t.withName(name.id());
			Assert.assertEquals(name, t.name());
		}
	}

	@Test
	public void testFieldDecl() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			NodeList<ExtendedModifier> modifiers = arbitrary.arbitraryListExtendedModifier();
			Type type = arbitrary.arbitraryType();
			NodeList<VariableDeclarator> variables = arbitrary.arbitraryListVariableDeclarator();

			FieldDecl t = Trees.fieldDecl().withModifiers(modifiers).withType(type).withVariables(variables);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(type, t.type());
			Assert.assertEquals(variables, t.variables());

			t = Trees.fieldDecl(type).withModifiers(modifiers).withVariables(variables);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(type, t.type());
			Assert.assertEquals(variables, t.variables());
		}
	}

	@Test
	public void testFormalParameter() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			NodeList<ExtendedModifier> modifiers = arbitrary.arbitraryListExtendedModifier();
			Type type = arbitrary.arbitraryType();
			boolean isVarArgs = arbitrary.arbitraryBoolean();
			VariableDeclaratorId id = arbitrary.arbitraryVariableDeclaratorId();

			FormalParameter t = Trees.formalParameter().withModifiers(modifiers).withType(type).setVarArgs(isVarArgs).withId(id);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(type, t.type());
			Assert.assertEquals(isVarArgs, t.isVarArgs());
			Assert.assertEquals(id, t.id());

			t = Trees.formalParameter(type, id).withModifiers(modifiers).setVarArgs(isVarArgs);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(type, t.type());
			Assert.assertEquals(isVarArgs, t.isVarArgs());
			Assert.assertEquals(id, t.id());
		}
	}

	@Test
	public void testImportDecl() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			QualifiedName name = arbitrary.arbitraryQualifiedName();
			boolean isStatic = arbitrary.arbitraryBoolean();
			boolean isOnDemand = arbitrary.arbitraryBoolean();

			ImportDecl t = Trees.importDecl().withName(name).setStatic(isStatic).setOnDemand(isOnDemand);
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(isStatic, t.isStatic());
			Assert.assertEquals(isOnDemand, t.isOnDemand());

			t = Trees.importDecl(name).setStatic(isStatic).setOnDemand(isOnDemand);
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(isStatic, t.isStatic());
			Assert.assertEquals(isOnDemand, t.isOnDemand());
		}
	}

	@Test
	public void testInitializerDecl() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			NodeList<ExtendedModifier> modifiers = arbitrary.arbitraryListExtendedModifier();
			BlockStmt body = arbitrary.arbitraryBlockStmt();

			InitializerDecl t = Trees.initializerDecl().withModifiers(modifiers).withBody(body);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(body, t.body());

			t = Trees.initializerDecl(body).withModifiers(modifiers);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(body, t.body());
		}
	}

	@Test
	public void testInterfaceDecl() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			NodeList<ExtendedModifier> modifiers = arbitrary.arbitraryListExtendedModifier();
			Name name = arbitrary.arbitraryName();
			NodeList<TypeParameter> typeParams = arbitrary.arbitraryListTypeParameter();
			NodeList<QualifiedType> extendsClause = arbitrary.arbitraryListQualifiedType();
			NodeList<MemberDecl> members = arbitrary.arbitraryListMemberDecl();

			InterfaceDecl t = Trees.interfaceDecl().withModifiers(modifiers).withName(name).withTypeParams(typeParams).withExtendsClause(extendsClause).withMembers(members);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(typeParams, t.typeParams());
			Assert.assertEquals(extendsClause, t.extendsClause());
			Assert.assertEquals(members, t.members());

			t = Trees.interfaceDecl(name).withModifiers(modifiers).withTypeParams(typeParams).withExtendsClause(extendsClause).withMembers(members);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(typeParams, t.typeParams());
			Assert.assertEquals(extendsClause, t.extendsClause());
			Assert.assertEquals(members, t.members());

			t = t.withName(name.id());
			Assert.assertEquals(name, t.name());
		}
	}

	@Test
	public void testLocalVariableDecl() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			NodeList<ExtendedModifier> modifiers = arbitrary.arbitraryListExtendedModifier();
			Type type = arbitrary.arbitraryType();
			NodeList<VariableDeclarator> variables = arbitrary.arbitraryListVariableDeclarator();

			LocalVariableDecl t = Trees.localVariableDecl().withModifiers(modifiers).withType(type).withVariables(variables);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(type, t.type());
			Assert.assertEquals(variables, t.variables());

			t = Trees.localVariableDecl(type).withModifiers(modifiers).withVariables(variables);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(type, t.type());
			Assert.assertEquals(variables, t.variables());
		}
	}

	@Test
	public void testMethodDecl() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			NodeList<ExtendedModifier> modifiers = arbitrary.arbitraryListExtendedModifier();
			NodeList<TypeParameter> typeParams = arbitrary.arbitraryListTypeParameter();
			Type type = arbitrary.arbitraryType();
			Name name = arbitrary.arbitraryName();
			NodeList<FormalParameter> params = arbitrary.arbitraryListFormalParameter();
			NodeList<ArrayDim> dims = arbitrary.arbitraryListArrayDim();
			NodeList<QualifiedType> throwsClause = arbitrary.arbitraryListQualifiedType();
			NodeOption<BlockStmt> body = arbitrary.arbitraryOptionBlockStmt();

			MethodDecl t = Trees.methodDecl().withModifiers(modifiers).withTypeParams(typeParams).withType(type).withName(name).withParams(params).withDims(dims).withThrowsClause(throwsClause).withBody(body);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(typeParams, t.typeParams());
			Assert.assertEquals(type, t.type());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(params, t.params());
			Assert.assertEquals(dims, t.dims());
			Assert.assertEquals(throwsClause, t.throwsClause());
			Assert.assertEquals(body, t.body());

			t = Trees.methodDecl(type, name).withModifiers(modifiers).withTypeParams(typeParams).withParams(params).withDims(dims).withThrowsClause(throwsClause).withBody(body);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(typeParams, t.typeParams());
			Assert.assertEquals(type, t.type());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(params, t.params());
			Assert.assertEquals(dims, t.dims());
			Assert.assertEquals(throwsClause, t.throwsClause());
			Assert.assertEquals(body, t.body());

			t = t.withName(name.id());
			Assert.assertEquals(name, t.name());

			t = t.withBody(body.get());
			Assert.assertEquals(body, t.body());
			t = t.withNoBody();
			Assert.assertEquals(Trees.<BlockStmt>none(), t.body());
		}
	}

	@Test
	public void testModifier() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			ModifierKeyword keyword = arbitrary.arbitraryModifierKeyword();

			Modifier t = Trees.modifier().withKeyword(keyword);
			Assert.assertEquals(keyword, t.keyword());

			t = Trees.modifier(keyword);
			Assert.assertEquals(keyword, t.keyword());
		}
	}

	@Test
	public void testPackageDecl() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			NodeList<AnnotationExpr> annotations = arbitrary.arbitraryListAnnotationExpr();
			QualifiedName name = arbitrary.arbitraryQualifiedName();

			PackageDecl t = Trees.packageDecl().withAnnotations(annotations).withName(name);
			Assert.assertEquals(annotations, t.annotations());
			Assert.assertEquals(name, t.name());

			t = Trees.packageDecl(name).withAnnotations(annotations);
			Assert.assertEquals(annotations, t.annotations());
			Assert.assertEquals(name, t.name());
		}
	}

	@Test
	public void testTypeParameter() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			NodeList<AnnotationExpr> annotations = arbitrary.arbitraryListAnnotationExpr();
			Name name = arbitrary.arbitraryName();
			NodeList<Type> bounds = arbitrary.arbitraryListType();

			TypeParameter t = Trees.typeParameter().withAnnotations(annotations).withName(name).withBounds(bounds);
			Assert.assertEquals(annotations, t.annotations());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(bounds, t.bounds());

			t = Trees.typeParameter(name).withAnnotations(annotations).withBounds(bounds);
			Assert.assertEquals(annotations, t.annotations());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(bounds, t.bounds());

			t = t.withName(name.id());
			Assert.assertEquals(name, t.name());
		}
	}

	@Test
	public void testVariableDeclarator() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			VariableDeclaratorId id = arbitrary.arbitraryVariableDeclaratorId();
			NodeOption<Expr> init = arbitrary.arbitraryOptionExpr();

			VariableDeclarator t = Trees.variableDeclarator().withId(id).withInit(init);
			Assert.assertEquals(id, t.id());
			Assert.assertEquals(init, t.init());

			t = Trees.variableDeclarator(id).withInit(init);
			Assert.assertEquals(id, t.id());
			Assert.assertEquals(init, t.init());

			t = t.withInit(init.get());
			Assert.assertEquals(init, t.init());
			t = t.withNoInit();
			Assert.assertEquals(Trees.<Expr>none(), t.init());
		}
	}

	@Test
	public void testVariableDeclaratorId() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			Name name = arbitrary.arbitraryName();
			NodeList<ArrayDim> dims = arbitrary.arbitraryListArrayDim();

			VariableDeclaratorId t = Trees.variableDeclaratorId().withName(name).withDims(dims);
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(dims, t.dims());

			t = Trees.variableDeclaratorId(name).withDims(dims);
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(dims, t.dims());

			t = t.withName(name.id());
			Assert.assertEquals(name, t.name());
		}
	}

	@Test
	public void testArrayAccessExpr() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			Expr name = arbitrary.arbitraryExpr();
			Expr index = arbitrary.arbitraryExpr();

			ArrayAccessExpr t = Trees.arrayAccessExpr().withName(name).withIndex(index);
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(index, t.index());

			t = Trees.arrayAccessExpr(name, index);
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(index, t.index());
		}
	}

	@Test
	public void testArrayCreationExpr() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			Type type = arbitrary.arbitraryType();
			NodeList<ArrayDimExpr> dimExprs = arbitrary.arbitraryListArrayDimExpr();
			NodeList<ArrayDim> dims = arbitrary.arbitraryListArrayDim();
			NodeOption<ArrayInitializerExpr> init = arbitrary.arbitraryOptionArrayInitializerExpr();

			ArrayCreationExpr t = Trees.arrayCreationExpr().withType(type).withDimExprs(dimExprs).withDims(dims).withInit(init);
			Assert.assertEquals(type, t.type());
			Assert.assertEquals(dimExprs, t.dimExprs());
			Assert.assertEquals(dims, t.dims());
			Assert.assertEquals(init, t.init());

			t = Trees.arrayCreationExpr(type).withDimExprs(dimExprs).withDims(dims).withInit(init);
			Assert.assertEquals(type, t.type());
			Assert.assertEquals(dimExprs, t.dimExprs());
			Assert.assertEquals(dims, t.dims());
			Assert.assertEquals(init, t.init());

			t = t.withInit(init.get());
			Assert.assertEquals(init, t.init());
			t = t.withNoInit();
			Assert.assertEquals(Trees.<ArrayInitializerExpr>none(), t.init());
		}
	}

	@Test
	public void testArrayDimExpr() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			NodeList<AnnotationExpr> annotations = arbitrary.arbitraryListAnnotationExpr();
			Expr expr = arbitrary.arbitraryExpr();

			ArrayDimExpr t = Trees.arrayDimExpr().withAnnotations(annotations).withExpr(expr);
			Assert.assertEquals(annotations, t.annotations());
			Assert.assertEquals(expr, t.expr());

			t = Trees.arrayDimExpr(expr).withAnnotations(annotations);
			Assert.assertEquals(annotations, t.annotations());
			Assert.assertEquals(expr, t.expr());
		}
	}

	@Test
	public void testArrayInitializerExpr() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			NodeList<Expr> values = arbitrary.arbitraryListExpr();
			boolean trailingComma = arbitrary.arbitraryBoolean();

			ArrayInitializerExpr t = Trees.arrayInitializerExpr().withValues(values).withTrailingComma(trailingComma);
			Assert.assertEquals(values, t.values());
			Assert.assertEquals(trailingComma, t.trailingComma());
		}
	}

	@Test
	public void testAssignExpr() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			Expr target = arbitrary.arbitraryExpr();
			AssignOp op = arbitrary.arbitraryAssignOp();
			Expr value = arbitrary.arbitraryExpr();

			AssignExpr t = Trees.assignExpr().withTarget(target).withOp(op).withValue(value);
			Assert.assertEquals(target, t.target());
			Assert.assertEquals(op, t.op());
			Assert.assertEquals(value, t.value());

			t = Trees.assignExpr(target, op, value);
			Assert.assertEquals(target, t.target());
			Assert.assertEquals(op, t.op());
			Assert.assertEquals(value, t.value());
		}
	}

	@Test
	public void testBinaryExpr() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			Expr left = arbitrary.arbitraryExpr();
			BinaryOp op = arbitrary.arbitraryBinaryOp();
			Expr right = arbitrary.arbitraryExpr();

			BinaryExpr t = Trees.binaryExpr().withLeft(left).withOp(op).withRight(right);
			Assert.assertEquals(left, t.left());
			Assert.assertEquals(op, t.op());
			Assert.assertEquals(right, t.right());

			t = Trees.binaryExpr(left, op, right);
			Assert.assertEquals(left, t.left());
			Assert.assertEquals(op, t.op());
			Assert.assertEquals(right, t.right());
		}
	}

	@Test
	public void testCastExpr() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			Type type = arbitrary.arbitraryType();
			Expr expr = arbitrary.arbitraryExpr();

			CastExpr t = Trees.castExpr().withType(type).withExpr(expr);
			Assert.assertEquals(type, t.type());
			Assert.assertEquals(expr, t.expr());

			t = Trees.castExpr(type, expr);
			Assert.assertEquals(type, t.type());
			Assert.assertEquals(expr, t.expr());
		}
	}

	@Test
	public void testClassExpr() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			Type type = arbitrary.arbitraryType();

			ClassExpr t = Trees.classExpr().withType(type);
			Assert.assertEquals(type, t.type());

			t = Trees.classExpr(type);
			Assert.assertEquals(type, t.type());
		}
	}

	@Test
	public void testConditionalExpr() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			Expr condition = arbitrary.arbitraryExpr();
			Expr thenExpr = arbitrary.arbitraryExpr();
			Expr elseExpr = arbitrary.arbitraryExpr();

			ConditionalExpr t = Trees.conditionalExpr().withCondition(condition).withThenExpr(thenExpr).withElseExpr(elseExpr);
			Assert.assertEquals(condition, t.condition());
			Assert.assertEquals(thenExpr, t.thenExpr());
			Assert.assertEquals(elseExpr, t.elseExpr());

			t = Trees.conditionalExpr(condition, thenExpr, elseExpr);
			Assert.assertEquals(condition, t.condition());
			Assert.assertEquals(thenExpr, t.thenExpr());
			Assert.assertEquals(elseExpr, t.elseExpr());
		}
	}

	@Test
	public void testFieldAccessExpr() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			NodeOption<Expr> scope = arbitrary.arbitraryOptionExpr();
			Name name = arbitrary.arbitraryName();

			FieldAccessExpr t = Trees.fieldAccessExpr().withScope(scope).withName(name);
			Assert.assertEquals(scope, t.scope());
			Assert.assertEquals(name, t.name());

			t = Trees.fieldAccessExpr(name).withScope(scope);
			Assert.assertEquals(scope, t.scope());
			Assert.assertEquals(name, t.name());

			t = t.withName(name.id());
			Assert.assertEquals(name, t.name());

			t = t.withScope(scope.get());
			Assert.assertEquals(scope, t.scope());
			t = t.withNoScope();
			Assert.assertEquals(Trees.<Expr>none(), t.scope());
		}
	}

	@Test
	public void testInstanceOfExpr() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			Expr expr = arbitrary.arbitraryExpr();
			Type type = arbitrary.arbitraryType();

			InstanceOfExpr t = Trees.instanceOfExpr().withExpr(expr).withType(type);
			Assert.assertEquals(expr, t.expr());
			Assert.assertEquals(type, t.type());

			t = Trees.instanceOfExpr(expr, type);
			Assert.assertEquals(expr, t.expr());
			Assert.assertEquals(type, t.type());
		}
	}

	@Test
	public void testLambdaExpr() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			NodeList<FormalParameter> params = arbitrary.arbitraryListFormalParameter();
			boolean hasParens = arbitrary.arbitraryBoolean();
			NodeEither<Expr, BlockStmt> body = arbitrary.arbitraryEitherExprBlockStmt();

			LambdaExpr t = Trees.lambdaExpr().withParams(params).setParens(hasParens).withBody(body);
			Assert.assertEquals(params, t.params());
			Assert.assertEquals(hasParens, t.hasParens());
			Assert.assertEquals(body, t.body());
			if (body.isLeft()) t = t.withBody(body.left());
			else t = t.withBody(body.right());
			Assert.assertEquals(body, t.body());
		}
	}

	@Test
	public void testMarkerAnnotationExpr() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			QualifiedName name = arbitrary.arbitraryQualifiedName();

			MarkerAnnotationExpr t = Trees.markerAnnotationExpr().withName(name);
			Assert.assertEquals(name, t.name());

			t = Trees.markerAnnotationExpr(name);
			Assert.assertEquals(name, t.name());
		}
	}

	@Test
	public void testMemberValuePair() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			Name name = arbitrary.arbitraryName();
			Expr value = arbitrary.arbitraryExpr();

			MemberValuePair t = Trees.memberValuePair().withName(name).withValue(value);
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(value, t.value());

			t = Trees.memberValuePair(name, value);
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(value, t.value());

			t = t.withName(name.id());
			Assert.assertEquals(name, t.name());
		}
	}

	@Test
	public void testMethodInvocationExpr() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			NodeOption<Expr> scope = arbitrary.arbitraryOptionExpr();
			NodeList<Type> typeArgs = arbitrary.arbitraryListType();
			Name name = arbitrary.arbitraryName();
			NodeList<Expr> args = arbitrary.arbitraryListExpr();

			MethodInvocationExpr t = Trees.methodInvocationExpr().withScope(scope).withTypeArgs(typeArgs).withName(name).withArgs(args);
			Assert.assertEquals(scope, t.scope());
			Assert.assertEquals(typeArgs, t.typeArgs());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(args, t.args());

			t = Trees.methodInvocationExpr(name).withScope(scope).withTypeArgs(typeArgs).withArgs(args);
			Assert.assertEquals(scope, t.scope());
			Assert.assertEquals(typeArgs, t.typeArgs());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(args, t.args());

			t = t.withName(name.id());
			Assert.assertEquals(name, t.name());

			t = t.withScope(scope.get());
			Assert.assertEquals(scope, t.scope());
			t = t.withNoScope();
			Assert.assertEquals(Trees.<Expr>none(), t.scope());
		}
	}

	@Test
	public void testMethodReferenceExpr() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			Expr scope = arbitrary.arbitraryExpr();
			NodeList<Type> typeArgs = arbitrary.arbitraryListType();
			Name name = arbitrary.arbitraryName();

			MethodReferenceExpr t = Trees.methodReferenceExpr().withScope(scope).withTypeArgs(typeArgs).withName(name);
			Assert.assertEquals(scope, t.scope());
			Assert.assertEquals(typeArgs, t.typeArgs());
			Assert.assertEquals(name, t.name());

			t = Trees.methodReferenceExpr(scope, name).withTypeArgs(typeArgs);
			Assert.assertEquals(scope, t.scope());
			Assert.assertEquals(typeArgs, t.typeArgs());
			Assert.assertEquals(name, t.name());

			t = t.withName(name.id());
			Assert.assertEquals(name, t.name());
		}
	}

	@Test
	public void testNormalAnnotationExpr() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			QualifiedName name = arbitrary.arbitraryQualifiedName();
			NodeList<MemberValuePair> pairs = arbitrary.arbitraryListMemberValuePair();

			NormalAnnotationExpr t = Trees.normalAnnotationExpr().withName(name).withPairs(pairs);
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(pairs, t.pairs());

			t = Trees.normalAnnotationExpr(name).withPairs(pairs);
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(pairs, t.pairs());
		}
	}

	@Test
	public void testObjectCreationExpr() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			NodeOption<Expr> scope = arbitrary.arbitraryOptionExpr();
			NodeList<Type> typeArgs = arbitrary.arbitraryListType();
			QualifiedType type = arbitrary.arbitraryQualifiedType();
			NodeList<Expr> args = arbitrary.arbitraryListExpr();
			NodeOption<NodeList<MemberDecl>> body = arbitrary.arbitraryOptionListMemberDecl();

			ObjectCreationExpr t = Trees.objectCreationExpr().withScope(scope).withTypeArgs(typeArgs).withType(type).withArgs(args).withBody(body);
			Assert.assertEquals(scope, t.scope());
			Assert.assertEquals(typeArgs, t.typeArgs());
			Assert.assertEquals(type, t.type());
			Assert.assertEquals(args, t.args());
			Assert.assertEquals(body, t.body());

			t = Trees.objectCreationExpr(type).withScope(scope).withTypeArgs(typeArgs).withArgs(args).withBody(body);
			Assert.assertEquals(scope, t.scope());
			Assert.assertEquals(typeArgs, t.typeArgs());
			Assert.assertEquals(type, t.type());
			Assert.assertEquals(args, t.args());
			Assert.assertEquals(body, t.body());

			t = t.withScope(scope.get()).withBody(body.get());
			Assert.assertEquals(scope, t.scope());
			Assert.assertEquals(body, t.body());
			t = t.withNoScope().withNoBody();
			Assert.assertEquals(Trees.<Expr>none(), t.scope());
			Assert.assertEquals(Trees.<NodeList<MemberDecl>>none(), t.body());
		}
	}

	@Test
	public void testParenthesizedExpr() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			Expr inner = arbitrary.arbitraryExpr();

			ParenthesizedExpr t = Trees.parenthesizedExpr().withInner(inner);
			Assert.assertEquals(inner, t.inner());

			t = Trees.parenthesizedExpr(inner);
			Assert.assertEquals(inner, t.inner());
		}
	}

	@Test
	public void testSingleMemberAnnotationExpr() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			QualifiedName name = arbitrary.arbitraryQualifiedName();
			Expr memberValue = arbitrary.arbitraryExpr();

			SingleMemberAnnotationExpr t = Trees.singleMemberAnnotationExpr().withName(name).withMemberValue(memberValue);
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(memberValue, t.memberValue());

			t = Trees.singleMemberAnnotationExpr(name, memberValue);
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(memberValue, t.memberValue());
		}
	}

	@Test
	public void testSuperExpr() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			NodeOption<Expr> classExpr = arbitrary.arbitraryOptionExpr();

			SuperExpr t = Trees.superExpr().withClassExpr(classExpr);
			Assert.assertEquals(classExpr, t.classExpr());

			t = t.withClassExpr(classExpr.get());
			Assert.assertEquals(classExpr, t.classExpr());
			t = t.withNoClassExpr();
			Assert.assertEquals(Trees.<Expr>none(), t.classExpr());
		}
	}

	@Test
	public void testThisExpr() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			NodeOption<Expr> classExpr = arbitrary.arbitraryOptionExpr();

			ThisExpr t = Trees.thisExpr().withClassExpr(classExpr);
			Assert.assertEquals(classExpr, t.classExpr());

			t = t.withClassExpr(classExpr.get());
			Assert.assertEquals(classExpr, t.classExpr());
			t = t.withNoClassExpr();
			Assert.assertEquals(Trees.<Expr>none(), t.classExpr());
		}
	}

	@Test
	public void testTypeExpr() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			Type type = arbitrary.arbitraryType();

			TypeExpr t = Trees.typeExpr().withType(type);
			Assert.assertEquals(type, t.type());

			t = Trees.typeExpr(type);
			Assert.assertEquals(type, t.type());
		}
	}

	@Test
	public void testUnaryExpr() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			UnaryOp op = arbitrary.arbitraryUnaryOp();
			Expr expr = arbitrary.arbitraryExpr();

			UnaryExpr t = Trees.unaryExpr().withOp(op).withExpr(expr);
			Assert.assertEquals(op, t.op());
			Assert.assertEquals(expr, t.expr());

			t = Trees.unaryExpr(op, expr);
			Assert.assertEquals(op, t.op());
			Assert.assertEquals(expr, t.expr());
		}
	}

	@Test
	public void testVariableDeclarationExpr() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			LocalVariableDecl declaration = arbitrary.arbitraryLocalVariableDecl();

			VariableDeclarationExpr t = Trees.variableDeclarationExpr().withDeclaration(declaration);
			Assert.assertEquals(declaration, t.declaration());

			t = Trees.variableDeclarationExpr(declaration);
			Assert.assertEquals(declaration, t.declaration());
		}
	}

	@Test
	public void testName() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			String id = arbitrary.arbitraryString();

			Name t = Trees.name().withId(id);
			Assert.assertEquals(id, t.id());

			t = Trees.name(id);
			Assert.assertEquals(id, t.id());
		}
	}

	@Test
	public void testQualifiedName() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			NodeOption<QualifiedName> qualifier = arbitrary.arbitraryOptionQualifiedName();
			Name name = arbitrary.arbitraryName();

			QualifiedName t = Trees.qualifiedName().withQualifier(qualifier).withName(name);
			Assert.assertEquals(qualifier, t.qualifier());
			Assert.assertEquals(name, t.name());

			t = Trees.qualifiedName(name).withQualifier(qualifier);
			Assert.assertEquals(qualifier, t.qualifier());
			Assert.assertEquals(name, t.name());

			t = t.withName(name.id());
			Assert.assertEquals(name, t.name());

			t = t.withQualifier(qualifier.get());
			Assert.assertEquals(qualifier, t.qualifier());
			t = t.withNoQualifier();
			Assert.assertEquals(Trees.<QualifiedName>none(), t.qualifier());
		}
	}

	@Test
	public void testAssertStmt() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			Expr check = arbitrary.arbitraryExpr();
			NodeOption<Expr> msg = arbitrary.arbitraryOptionExpr();

			AssertStmt t = Trees.assertStmt().withCheck(check).withMsg(msg);
			Assert.assertEquals(check, t.check());
			Assert.assertEquals(msg, t.msg());

			t = Trees.assertStmt(check).withMsg(msg);
			Assert.assertEquals(check, t.check());
			Assert.assertEquals(msg, t.msg());

			t = t.withMsg(msg.get());
			Assert.assertEquals(msg, t.msg());
			t = t.withNoMsg();
			Assert.assertEquals(Trees.<Expr>none(), t.msg());
		}
	}

	@Test
	public void testBlockStmt() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			NodeList<Stmt> stmts = arbitrary.arbitraryListStmt();

			BlockStmt t = Trees.blockStmt().withStmts(stmts);
			Assert.assertEquals(stmts, t.stmts());
		}
	}

	@Test
	public void testBreakStmt() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			NodeOption<Name> id = arbitrary.arbitraryOptionName();

			BreakStmt t = Trees.breakStmt().withId(id);
			Assert.assertEquals(id, t.id());

			t = t.withId(id.get());
			Assert.assertEquals(id, t.id());
			t = t.withNoId();
			Assert.assertEquals(Trees.<Name>none(), t.id());
		}
	}

	@Test
	public void testCatchClause() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			FormalParameter param = arbitrary.arbitraryFormalParameter();
			BlockStmt catchBlock = arbitrary.arbitraryBlockStmt();

			CatchClause t = Trees.catchClause().withParam(param).withCatchBlock(catchBlock);
			Assert.assertEquals(param, t.param());
			Assert.assertEquals(catchBlock, t.catchBlock());

			t = Trees.catchClause(param, catchBlock);
			Assert.assertEquals(param, t.param());
			Assert.assertEquals(catchBlock, t.catchBlock());
		}
	}

	@Test
	public void testContinueStmt() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			NodeOption<Name> id = arbitrary.arbitraryOptionName();

			ContinueStmt t = Trees.continueStmt().withId(id);
			Assert.assertEquals(id, t.id());

			t = t.withId(id.get());
			Assert.assertEquals(id, t.id());
			t = t.withNoId();
			Assert.assertEquals(Trees.<Name>none(), t.id());
		}
	}

	@Test
	public void testDoStmt() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			Stmt body = arbitrary.arbitraryStmt();
			Expr condition = arbitrary.arbitraryExpr();

			DoStmt t = Trees.doStmt().withBody(body).withCondition(condition);
			Assert.assertEquals(body, t.body());
			Assert.assertEquals(condition, t.condition());

			t = Trees.doStmt(body, condition);
			Assert.assertEquals(body, t.body());
			Assert.assertEquals(condition, t.condition());
		}
	}

	@Test
	public void testExplicitConstructorInvocationStmt() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			NodeList<Type> typeArgs = arbitrary.arbitraryListType();
			boolean isThis = arbitrary.arbitraryBoolean();
			NodeOption<Expr> expr = arbitrary.arbitraryOptionExpr();
			NodeList<Expr> args = arbitrary.arbitraryListExpr();

			ExplicitConstructorInvocationStmt t = Trees.explicitConstructorInvocationStmt().withTypeArgs(typeArgs).setThis(isThis).withExpr(expr).withArgs(args);
			Assert.assertEquals(typeArgs, t.typeArgs());
			Assert.assertEquals(isThis, t.isThis());
			Assert.assertEquals(expr, t.expr());
			Assert.assertEquals(args, t.args());

			t = t.withExpr(expr.get());
			Assert.assertEquals(expr, t.expr());
			t = t.withNoExpr();
			Assert.assertEquals(Trees.<Expr>none(), t.expr());
		}
	}

	@Test
	public void testExpressionStmt() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			Expr expr = arbitrary.arbitraryExpr();

			ExpressionStmt t = Trees.expressionStmt().withExpr(expr);
			Assert.assertEquals(expr, t.expr());

			t = Trees.expressionStmt(expr);
			Assert.assertEquals(expr, t.expr());
		}
	}

	@Test
	public void testForStmt() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			NodeList<Expr> init = arbitrary.arbitraryListExpr();
			Expr compare = arbitrary.arbitraryExpr();
			NodeList<Expr> update = arbitrary.arbitraryListExpr();
			Stmt body = arbitrary.arbitraryStmt();

			ForStmt t = Trees.forStmt().withInit(init).withCompare(compare).withUpdate(update).withBody(body);
			Assert.assertEquals(init, t.init());
			Assert.assertEquals(compare, t.compare());
			Assert.assertEquals(update, t.update());
			Assert.assertEquals(body, t.body());

			t = Trees.forStmt(compare, body).withInit(init).withUpdate(update);
			Assert.assertEquals(init, t.init());
			Assert.assertEquals(compare, t.compare());
			Assert.assertEquals(update, t.update());
			Assert.assertEquals(body, t.body());
		}
	}

	@Test
	public void testForeachStmt() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			VariableDeclarationExpr var = arbitrary.arbitraryVariableDeclarationExpr();
			Expr iterable = arbitrary.arbitraryExpr();
			Stmt body = arbitrary.arbitraryStmt();

			ForeachStmt t = Trees.foreachStmt().withVar(var).withIterable(iterable).withBody(body);
			Assert.assertEquals(var, t.var());
			Assert.assertEquals(iterable, t.iterable());
			Assert.assertEquals(body, t.body());

			t = Trees.foreachStmt(var, iterable, body);
			Assert.assertEquals(var, t.var());
			Assert.assertEquals(iterable, t.iterable());
			Assert.assertEquals(body, t.body());
		}
	}

	@Test
	public void testIfStmt() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			Expr condition = arbitrary.arbitraryExpr();
			Stmt thenStmt = arbitrary.arbitraryStmt();
			NodeOption<Stmt> elseStmt = arbitrary.arbitraryOptionStmt();

			IfStmt t = Trees.ifStmt().withCondition(condition).withThenStmt(thenStmt).withElseStmt(elseStmt);
			Assert.assertEquals(condition, t.condition());
			Assert.assertEquals(thenStmt, t.thenStmt());
			Assert.assertEquals(elseStmt, t.elseStmt());

			t = Trees.ifStmt(condition, thenStmt).withElseStmt(elseStmt);
			Assert.assertEquals(condition, t.condition());
			Assert.assertEquals(thenStmt, t.thenStmt());
			Assert.assertEquals(elseStmt, t.elseStmt());

			t = t.withElseStmt(elseStmt.get());
			Assert.assertEquals(elseStmt, t.elseStmt());
			t = t.withNoElseStmt();
			Assert.assertEquals(Trees.<Stmt>none(), t.elseStmt());
		}
	}

	@Test
	public void testLabeledStmt() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			Name label = arbitrary.arbitraryName();
			Stmt stmt = arbitrary.arbitraryStmt();

			LabeledStmt t = Trees.labeledStmt().withLabel(label).withStmt(stmt);
			Assert.assertEquals(label, t.label());
			Assert.assertEquals(stmt, t.stmt());

			t = Trees.labeledStmt(label, stmt);
			Assert.assertEquals(label, t.label());
			Assert.assertEquals(stmt, t.stmt());

			t = t.withLabel(label.id());
			Assert.assertEquals(label, t.label());
		}
	}

	@Test
	public void testReturnStmt() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			NodeOption<Expr> expr = arbitrary.arbitraryOptionExpr();

			ReturnStmt t = Trees.returnStmt().withExpr(expr);
			Assert.assertEquals(expr, t.expr());

			t = t.withExpr(expr.get());
			Assert.assertEquals(expr, t.expr());
			t = t.withNoExpr();
			Assert.assertEquals(Trees.<Expr>none(), t.expr());
		}
	}

	@Test
	public void testSwitchCase() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			NodeOption<Expr> label = arbitrary.arbitraryOptionExpr();
			NodeList<Stmt> stmts = arbitrary.arbitraryListStmt();

			SwitchCase t = Trees.switchCase().withLabel(label).withStmts(stmts);
			Assert.assertEquals(label, t.label());
			Assert.assertEquals(stmts, t.stmts());

			t = t.withLabel(label.get());
			Assert.assertEquals(label, t.label());
			t = t.withNoLabel();
			Assert.assertEquals(Trees.<Expr>none(), t.label());
		}
	}

	@Test
	public void testSwitchStmt() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			Expr selector = arbitrary.arbitraryExpr();
			NodeList<SwitchCase> cases = arbitrary.arbitraryListSwitchCase();

			SwitchStmt t = Trees.switchStmt().withSelector(selector).withCases(cases);
			Assert.assertEquals(selector, t.selector());
			Assert.assertEquals(cases, t.cases());

			t = Trees.switchStmt(selector).withCases(cases);
			Assert.assertEquals(selector, t.selector());
			Assert.assertEquals(cases, t.cases());
		}
	}

	@Test
	public void testSynchronizedStmt() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			Expr expr = arbitrary.arbitraryExpr();
			BlockStmt block = arbitrary.arbitraryBlockStmt();

			SynchronizedStmt t = Trees.synchronizedStmt().withExpr(expr).withBlock(block);
			Assert.assertEquals(expr, t.expr());
			Assert.assertEquals(block, t.block());

			t = Trees.synchronizedStmt(expr, block);
			Assert.assertEquals(expr, t.expr());
			Assert.assertEquals(block, t.block());
		}
	}

	@Test
	public void testThrowStmt() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			Expr expr = arbitrary.arbitraryExpr();

			ThrowStmt t = Trees.throwStmt().withExpr(expr);
			Assert.assertEquals(expr, t.expr());

			t = Trees.throwStmt(expr);
			Assert.assertEquals(expr, t.expr());
		}
	}

	@Test
	public void testTryStmt() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			NodeList<VariableDeclarationExpr> resources = arbitrary.arbitraryListVariableDeclarationExpr();
			boolean trailingSemiColon = arbitrary.arbitraryBoolean();
			BlockStmt tryBlock = arbitrary.arbitraryBlockStmt();
			NodeList<CatchClause> catchs = arbitrary.arbitraryListCatchClause();
			NodeOption<BlockStmt> finallyBlock = arbitrary.arbitraryOptionBlockStmt();

			TryStmt t = Trees.tryStmt().withResources(resources).withTrailingSemiColon(trailingSemiColon).withTryBlock(tryBlock).withCatchs(catchs).withFinallyBlock(finallyBlock);
			Assert.assertEquals(resources, t.resources());
			Assert.assertEquals(trailingSemiColon, t.trailingSemiColon());
			Assert.assertEquals(tryBlock, t.tryBlock());
			Assert.assertEquals(catchs, t.catchs());
			Assert.assertEquals(finallyBlock, t.finallyBlock());

			t = Trees.tryStmt(tryBlock).withResources(resources).withTrailingSemiColon(trailingSemiColon).withCatchs(catchs).withFinallyBlock(finallyBlock);
			Assert.assertEquals(resources, t.resources());
			Assert.assertEquals(trailingSemiColon, t.trailingSemiColon());
			Assert.assertEquals(tryBlock, t.tryBlock());
			Assert.assertEquals(catchs, t.catchs());
			Assert.assertEquals(finallyBlock, t.finallyBlock());

			t = t.withFinallyBlock(finallyBlock.get());
			Assert.assertEquals(finallyBlock, t.finallyBlock());
			t = t.withNoFinallyBlock();
			Assert.assertEquals(Trees.<BlockStmt>none(), t.finallyBlock());
		}
	}

	@Test
	public void testTypeDeclarationStmt() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			TypeDecl typeDecl = arbitrary.arbitraryTypeDecl();

			TypeDeclarationStmt t = Trees.typeDeclarationStmt().withTypeDecl(typeDecl);
			Assert.assertEquals(typeDecl, t.typeDecl());

			t = Trees.typeDeclarationStmt(typeDecl);
			Assert.assertEquals(typeDecl, t.typeDecl());
		}
	}

	@Test
	public void testWhileStmt() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			Expr condition = arbitrary.arbitraryExpr();
			Stmt body = arbitrary.arbitraryStmt();

			WhileStmt t = Trees.whileStmt().withCondition(condition).withBody(body);
			Assert.assertEquals(condition, t.condition());
			Assert.assertEquals(body, t.body());

			t = Trees.whileStmt(condition, body);
			Assert.assertEquals(condition, t.condition());
			Assert.assertEquals(body, t.body());
		}
	}

	@Test
	public void testArrayType() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			Type componentType = arbitrary.arbitraryType();
			NodeList<ArrayDim> dims = arbitrary.arbitraryListArrayDim();

			ArrayType t = Trees.arrayType().withComponentType(componentType).withDims(dims);
			Assert.assertEquals(componentType, t.componentType());
			Assert.assertEquals(dims, t.dims());

			t = Trees.arrayType(componentType).withDims(dims);
			Assert.assertEquals(componentType, t.componentType());
			Assert.assertEquals(dims, t.dims());
		}
	}

	@Test
	public void testIntersectionType() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			NodeList<Type> types = arbitrary.arbitraryListType();

			IntersectionType t = Trees.intersectionType().withTypes(types);
			Assert.assertEquals(types, t.types());
		}
	}

	@Test
	public void testPrimitiveType() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			NodeList<AnnotationExpr> annotations = arbitrary.arbitraryListAnnotationExpr();
			Primitive primitive = arbitrary.arbitraryPrimitive();

			PrimitiveType t = Trees.primitiveType().withAnnotations(annotations).withPrimitive(primitive);
			Assert.assertEquals(annotations, t.annotations());
			Assert.assertEquals(primitive, t.primitive());

			t = Trees.primitiveType(primitive).withAnnotations(annotations);
			Assert.assertEquals(annotations, t.annotations());
			Assert.assertEquals(primitive, t.primitive());
		}
	}

	@Test
	public void testQualifiedType() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			NodeList<AnnotationExpr> annotations = arbitrary.arbitraryListAnnotationExpr();
			NodeOption<QualifiedType> scope = arbitrary.arbitraryOptionQualifiedType();
			Name name = arbitrary.arbitraryName();
			NodeOption<NodeList<Type>> typeArgs = arbitrary.arbitraryOptionListType();

			QualifiedType t = Trees.qualifiedType().withAnnotations(annotations).withScope(scope).withName(name).withTypeArgs(typeArgs);
			Assert.assertEquals(annotations, t.annotations());
			Assert.assertEquals(scope, t.scope());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(typeArgs, t.typeArgs());

			t = Trees.qualifiedType(name).withAnnotations(annotations).withScope(scope).withTypeArgs(typeArgs);
			Assert.assertEquals(annotations, t.annotations());
			Assert.assertEquals(scope, t.scope());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(typeArgs, t.typeArgs());

			t = t.withName(name.id());
			Assert.assertEquals(name, t.name());

			t = t.withScope(scope.get()).withTypeArgs(typeArgs.get());
			Assert.assertEquals(scope, t.scope());
			Assert.assertEquals(typeArgs, t.typeArgs());
			t = t.withNoScope().withNoTypeArgs();
			Assert.assertEquals(Trees.<QualifiedType>none(), t.scope());
			Assert.assertEquals(Trees.<NodeList<Type>>none(), t.typeArgs());
		}
	}

	@Test
	public void testUnionType() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			NodeList<Type> types = arbitrary.arbitraryListType();

			UnionType t = Trees.unionType().withTypes(types);
			Assert.assertEquals(types, t.types());
		}
	}

	@Test
	public void testWildcardType() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			NodeList<AnnotationExpr> annotations = arbitrary.arbitraryListAnnotationExpr();
			NodeOption<ReferenceType> ext = arbitrary.arbitraryOptionReferenceType();
			NodeOption<ReferenceType> sup = arbitrary.arbitraryOptionReferenceType();

			WildcardType t = Trees.wildcardType().withAnnotations(annotations).withExt(ext).withSup(sup);
			Assert.assertEquals(annotations, t.annotations());
			Assert.assertEquals(ext, t.ext());
			Assert.assertEquals(sup, t.sup());

			t = t.withExt(ext.get()).withSup(sup.get());
			Assert.assertEquals(ext, t.ext());
			Assert.assertEquals(sup, t.sup());
			t = t.withNoExt().withNoSup();
			Assert.assertEquals(Trees.<ReferenceType>none(), t.ext());
			Assert.assertEquals(Trees.<ReferenceType>none(), t.sup());
		}
	}
}
