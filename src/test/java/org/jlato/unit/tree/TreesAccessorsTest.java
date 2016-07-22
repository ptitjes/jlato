package org.jlato.unit.tree;

import org.jlato.tree.NodeEither;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Trees;
import org.jlato.tree.decl.*;
import org.jlato.tree.expr.*;
import org.jlato.tree.name.Name;
import org.jlato.tree.name.QualifiedName;
import org.jlato.tree.stmt.*;
import org.jlato.tree.type.*;
import org.jlato.unit.util.Arbitrary;
import org.junit.Assert;
import org.junit.Test;
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

			// Use factory method without argument
			AnnotationDecl t = Trees.annotationDecl().withModifiers(modifiers).withName(name).withMembers(members);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(members, t.members());

			// Use factory method with arguments
			t = Trees.annotationDecl(name).withModifiers(modifiers).withMembers(members);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(members, t.members());

			// Use specialized name mutators
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

			// Use factory method without argument
			AnnotationMemberDecl t = Trees.annotationMemberDecl().withModifiers(modifiers).withType(type).withName(name).withDims(dims).withDefaultValue(defaultValue);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(type, t.type());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(dims, t.dims());
			Assert.assertEquals(defaultValue, t.defaultValue());

			// Use factory method with arguments
			t = Trees.annotationMemberDecl(type, name).withModifiers(modifiers).withDims(dims).withDefaultValue(defaultValue);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(type, t.type());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(dims, t.dims());
			Assert.assertEquals(defaultValue, t.defaultValue());

			// Use specialized name mutators
			t = t.withName(name.id());
			Assert.assertEquals(name, t.name());

			// Use specialized NodeOption.some() mutators
			t = t.withDefaultValue(defaultValue.get());
			Assert.assertEquals(defaultValue, t.defaultValue());

			// Use specialized NodeOption.none() mutators
			t = t.withNoDefaultValue();
			Assert.assertEquals(Trees.<Expr>none(), t.defaultValue());
		}
	}

	@Test
	public void testArrayDim() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			NodeList<AnnotationExpr> annotations = arbitrary.arbitraryListAnnotationExpr();

			// Use factory method without argument
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

			// Use factory method without argument
			ClassDecl t = Trees.classDecl().withModifiers(modifiers).withName(name).withTypeParams(typeParams).withExtendsClause(extendsClause).withImplementsClause(implementsClause).withMembers(members);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(typeParams, t.typeParams());
			Assert.assertEquals(extendsClause, t.extendsClause());
			Assert.assertEquals(implementsClause, t.implementsClause());
			Assert.assertEquals(members, t.members());

			// Use factory method with arguments
			t = Trees.classDecl(name).withModifiers(modifiers).withTypeParams(typeParams).withExtendsClause(extendsClause).withImplementsClause(implementsClause).withMembers(members);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(typeParams, t.typeParams());
			Assert.assertEquals(extendsClause, t.extendsClause());
			Assert.assertEquals(implementsClause, t.implementsClause());
			Assert.assertEquals(members, t.members());

			// Use specialized name mutators
			t = t.withName(name.id());
			Assert.assertEquals(name, t.name());

			// Use specialized NodeOption.some() mutators
			t = t.withExtendsClause(extendsClause.get());
			Assert.assertEquals(extendsClause, t.extendsClause());

			// Use specialized NodeOption.none() mutators
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

			// Use factory method without argument
			CompilationUnit t = Trees.compilationUnit().withPackageDecl(packageDecl).withImports(imports).withTypes(types);
			Assert.assertEquals(packageDecl, t.packageDecl());
			Assert.assertEquals(imports, t.imports());
			Assert.assertEquals(types, t.types());

			// Use factory method with arguments
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

			// Use factory method without argument
			ConstructorDecl t = Trees.constructorDecl().withModifiers(modifiers).withTypeParams(typeParams).withName(name).withParams(params).withThrowsClause(throwsClause).withBody(body);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(typeParams, t.typeParams());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(params, t.params());
			Assert.assertEquals(throwsClause, t.throwsClause());
			Assert.assertEquals(body, t.body());

			// Use factory method with arguments
			t = Trees.constructorDecl(name).withModifiers(modifiers).withTypeParams(typeParams).withParams(params).withThrowsClause(throwsClause).withBody(body);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(typeParams, t.typeParams());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(params, t.params());
			Assert.assertEquals(throwsClause, t.throwsClause());
			Assert.assertEquals(body, t.body());

			// Use specialized name mutators
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

			// Use factory method without argument
			EnumConstantDecl t = Trees.enumConstantDecl().withModifiers(modifiers).withName(name).withArgs(args).withClassBody(classBody);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(args, t.args());
			Assert.assertEquals(classBody, t.classBody());

			// Use factory method with arguments
			t = Trees.enumConstantDecl(name).withModifiers(modifiers).withArgs(args).withClassBody(classBody);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(args, t.args());
			Assert.assertEquals(classBody, t.classBody());

			// Use specialized name mutators
			t = t.withName(name.id());
			Assert.assertEquals(name, t.name());

			// Use specialized NodeOption.some() mutators
			t = t.withArgs(args.get()).withClassBody(classBody.get());
			Assert.assertEquals(args, t.args());
			Assert.assertEquals(classBody, t.classBody());

			// Use specialized NodeOption.none() mutators
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

			// Use factory method without argument
			EnumDecl t = Trees.enumDecl().withModifiers(modifiers).withName(name).withImplementsClause(implementsClause).withEnumConstants(enumConstants).withTrailingComma(trailingComma).withMembers(members);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(implementsClause, t.implementsClause());
			Assert.assertEquals(enumConstants, t.enumConstants());
			Assert.assertEquals(trailingComma, t.trailingComma());
			Assert.assertEquals(members, t.members());

			// Use factory method with arguments
			t = Trees.enumDecl(name).withModifiers(modifiers).withImplementsClause(implementsClause).withEnumConstants(enumConstants).withTrailingComma(trailingComma).withMembers(members);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(implementsClause, t.implementsClause());
			Assert.assertEquals(enumConstants, t.enumConstants());
			Assert.assertEquals(trailingComma, t.trailingComma());
			Assert.assertEquals(members, t.members());

			// Use specialized name mutators
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

			// Use factory method without argument
			FieldDecl t = Trees.fieldDecl().withModifiers(modifiers).withType(type).withVariables(variables);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(type, t.type());
			Assert.assertEquals(variables, t.variables());

			// Use factory method with arguments
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

			// Use factory method without argument
			FormalParameter t = Trees.formalParameter().withModifiers(modifiers).withType(type).setVarArgs(isVarArgs).withId(id);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(type, t.type());
			Assert.assertEquals(isVarArgs, t.isVarArgs());
			Assert.assertEquals(id, t.id());

			// Use factory method with arguments
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

			// Use factory method without argument
			ImportDecl t = Trees.importDecl().withName(name).setStatic(isStatic).setOnDemand(isOnDemand);
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(isStatic, t.isStatic());
			Assert.assertEquals(isOnDemand, t.isOnDemand());

			// Use factory method with arguments
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

			// Use factory method without argument
			InitializerDecl t = Trees.initializerDecl().withModifiers(modifiers).withBody(body);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(body, t.body());

			// Use factory method with arguments
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

			// Use factory method without argument
			InterfaceDecl t = Trees.interfaceDecl().withModifiers(modifiers).withName(name).withTypeParams(typeParams).withExtendsClause(extendsClause).withMembers(members);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(typeParams, t.typeParams());
			Assert.assertEquals(extendsClause, t.extendsClause());
			Assert.assertEquals(members, t.members());

			// Use factory method with arguments
			t = Trees.interfaceDecl(name).withModifiers(modifiers).withTypeParams(typeParams).withExtendsClause(extendsClause).withMembers(members);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(typeParams, t.typeParams());
			Assert.assertEquals(extendsClause, t.extendsClause());
			Assert.assertEquals(members, t.members());

			// Use specialized name mutators
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

			// Use factory method without argument
			LocalVariableDecl t = Trees.localVariableDecl().withModifiers(modifiers).withType(type).withVariables(variables);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(type, t.type());
			Assert.assertEquals(variables, t.variables());

			// Use factory method with arguments
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

			// Use factory method without argument
			MethodDecl t = Trees.methodDecl().withModifiers(modifiers).withTypeParams(typeParams).withType(type).withName(name).withParams(params).withDims(dims).withThrowsClause(throwsClause).withBody(body);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(typeParams, t.typeParams());
			Assert.assertEquals(type, t.type());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(params, t.params());
			Assert.assertEquals(dims, t.dims());
			Assert.assertEquals(throwsClause, t.throwsClause());
			Assert.assertEquals(body, t.body());

			// Use factory method with arguments
			t = Trees.methodDecl(type, name).withModifiers(modifiers).withTypeParams(typeParams).withParams(params).withDims(dims).withThrowsClause(throwsClause).withBody(body);
			Assert.assertEquals(modifiers, t.modifiers());
			Assert.assertEquals(typeParams, t.typeParams());
			Assert.assertEquals(type, t.type());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(params, t.params());
			Assert.assertEquals(dims, t.dims());
			Assert.assertEquals(throwsClause, t.throwsClause());
			Assert.assertEquals(body, t.body());

			// Use specialized name mutators
			t = t.withName(name.id());
			Assert.assertEquals(name, t.name());

			// Use specialized NodeOption.some() mutators
			t = t.withBody(body.get());
			Assert.assertEquals(body, t.body());

			// Use specialized NodeOption.none() mutators
			t = t.withNoBody();
			Assert.assertEquals(Trees.<BlockStmt>none(), t.body());
		}
	}

	@Test
	public void testModifier() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			ModifierKeyword keyword = arbitrary.arbitraryModifierKeyword();

			// Use factory method without argument
			Modifier t = Trees.modifier().withKeyword(keyword);
			Assert.assertEquals(keyword, t.keyword());

			// Use factory method with arguments
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

			// Use factory method without argument
			PackageDecl t = Trees.packageDecl().withAnnotations(annotations).withName(name);
			Assert.assertEquals(annotations, t.annotations());
			Assert.assertEquals(name, t.name());

			// Use factory method with arguments
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

			// Use factory method without argument
			TypeParameter t = Trees.typeParameter().withAnnotations(annotations).withName(name).withBounds(bounds);
			Assert.assertEquals(annotations, t.annotations());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(bounds, t.bounds());

			// Use factory method with arguments
			t = Trees.typeParameter(name).withAnnotations(annotations).withBounds(bounds);
			Assert.assertEquals(annotations, t.annotations());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(bounds, t.bounds());

			// Use specialized name mutators
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

			// Use factory method without argument
			VariableDeclarator t = Trees.variableDeclarator().withId(id).withInit(init);
			Assert.assertEquals(id, t.id());
			Assert.assertEquals(init, t.init());

			// Use factory method with arguments
			t = Trees.variableDeclarator(id).withInit(init);
			Assert.assertEquals(id, t.id());
			Assert.assertEquals(init, t.init());

			// Use specialized NodeOption.some() mutators
			t = t.withInit(init.get());
			Assert.assertEquals(init, t.init());

			// Use specialized NodeOption.none() mutators
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

			// Use factory method without argument
			VariableDeclaratorId t = Trees.variableDeclaratorId().withName(name).withDims(dims);
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(dims, t.dims());

			// Use factory method with arguments
			t = Trees.variableDeclaratorId(name).withDims(dims);
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(dims, t.dims());

			// Use specialized name mutators
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

			// Use factory method without argument
			ArrayAccessExpr t = Trees.arrayAccessExpr().withName(name).withIndex(index);
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(index, t.index());

			// Use factory method with arguments
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

			// Use factory method without argument
			ArrayCreationExpr t = Trees.arrayCreationExpr().withType(type).withDimExprs(dimExprs).withDims(dims).withInit(init);
			Assert.assertEquals(type, t.type());
			Assert.assertEquals(dimExprs, t.dimExprs());
			Assert.assertEquals(dims, t.dims());
			Assert.assertEquals(init, t.init());

			// Use factory method with arguments
			t = Trees.arrayCreationExpr(type).withDimExprs(dimExprs).withDims(dims).withInit(init);
			Assert.assertEquals(type, t.type());
			Assert.assertEquals(dimExprs, t.dimExprs());
			Assert.assertEquals(dims, t.dims());
			Assert.assertEquals(init, t.init());

			// Use specialized NodeOption.some() mutators
			t = t.withInit(init.get());
			Assert.assertEquals(init, t.init());

			// Use specialized NodeOption.none() mutators
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

			// Use factory method without argument
			ArrayDimExpr t = Trees.arrayDimExpr().withAnnotations(annotations).withExpr(expr);
			Assert.assertEquals(annotations, t.annotations());
			Assert.assertEquals(expr, t.expr());

			// Use factory method with arguments
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

			// Use factory method without argument
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

			// Use factory method without argument
			AssignExpr t = Trees.assignExpr().withTarget(target).withOp(op).withValue(value);
			Assert.assertEquals(target, t.target());
			Assert.assertEquals(op, t.op());
			Assert.assertEquals(value, t.value());

			// Use factory method with arguments
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

			// Use factory method without argument
			BinaryExpr t = Trees.binaryExpr().withLeft(left).withOp(op).withRight(right);
			Assert.assertEquals(left, t.left());
			Assert.assertEquals(op, t.op());
			Assert.assertEquals(right, t.right());

			// Use factory method with arguments
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

			// Use factory method without argument
			CastExpr t = Trees.castExpr().withType(type).withExpr(expr);
			Assert.assertEquals(type, t.type());
			Assert.assertEquals(expr, t.expr());

			// Use factory method with arguments
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

			// Use factory method without argument
			ClassExpr t = Trees.classExpr().withType(type);
			Assert.assertEquals(type, t.type());

			// Use factory method with arguments
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

			// Use factory method without argument
			ConditionalExpr t = Trees.conditionalExpr().withCondition(condition).withThenExpr(thenExpr).withElseExpr(elseExpr);
			Assert.assertEquals(condition, t.condition());
			Assert.assertEquals(thenExpr, t.thenExpr());
			Assert.assertEquals(elseExpr, t.elseExpr());

			// Use factory method with arguments
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

			// Use factory method without argument
			FieldAccessExpr t = Trees.fieldAccessExpr().withScope(scope).withName(name);
			Assert.assertEquals(scope, t.scope());
			Assert.assertEquals(name, t.name());

			// Use factory method with arguments
			t = Trees.fieldAccessExpr(name).withScope(scope);
			Assert.assertEquals(scope, t.scope());
			Assert.assertEquals(name, t.name());

			// Use specialized name mutators
			t = t.withName(name.id());
			Assert.assertEquals(name, t.name());

			// Use specialized NodeOption.some() mutators
			t = t.withScope(scope.get());
			Assert.assertEquals(scope, t.scope());

			// Use specialized NodeOption.none() mutators
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

			// Use factory method without argument
			InstanceOfExpr t = Trees.instanceOfExpr().withExpr(expr).withType(type);
			Assert.assertEquals(expr, t.expr());
			Assert.assertEquals(type, t.type());

			// Use factory method with arguments
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

			// Use factory method without argument
			LambdaExpr t = Trees.lambdaExpr().withParams(params).setParens(hasParens).withBody(body);
			Assert.assertEquals(params, t.params());
			Assert.assertEquals(hasParens, t.hasParens());
			Assert.assertEquals(body, t.body());

			// Use specialized NodeEither mutators
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

			// Use factory method without argument
			MarkerAnnotationExpr t = Trees.markerAnnotationExpr().withName(name);
			Assert.assertEquals(name, t.name());

			// Use factory method with arguments
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

			// Use factory method without argument
			MemberValuePair t = Trees.memberValuePair().withName(name).withValue(value);
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(value, t.value());

			// Use factory method with arguments
			t = Trees.memberValuePair(name, value);
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(value, t.value());

			// Use specialized name mutators
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

			// Use factory method without argument
			MethodInvocationExpr t = Trees.methodInvocationExpr().withScope(scope).withTypeArgs(typeArgs).withName(name).withArgs(args);
			Assert.assertEquals(scope, t.scope());
			Assert.assertEquals(typeArgs, t.typeArgs());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(args, t.args());

			// Use factory method with arguments
			t = Trees.methodInvocationExpr(name).withScope(scope).withTypeArgs(typeArgs).withArgs(args);
			Assert.assertEquals(scope, t.scope());
			Assert.assertEquals(typeArgs, t.typeArgs());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(args, t.args());

			// Use specialized name mutators
			t = t.withName(name.id());
			Assert.assertEquals(name, t.name());

			// Use specialized NodeOption.some() mutators
			t = t.withScope(scope.get());
			Assert.assertEquals(scope, t.scope());

			// Use specialized NodeOption.none() mutators
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

			// Use factory method without argument
			MethodReferenceExpr t = Trees.methodReferenceExpr().withScope(scope).withTypeArgs(typeArgs).withName(name);
			Assert.assertEquals(scope, t.scope());
			Assert.assertEquals(typeArgs, t.typeArgs());
			Assert.assertEquals(name, t.name());

			// Use factory method with arguments
			t = Trees.methodReferenceExpr(scope, name).withTypeArgs(typeArgs);
			Assert.assertEquals(scope, t.scope());
			Assert.assertEquals(typeArgs, t.typeArgs());
			Assert.assertEquals(name, t.name());

			// Use specialized name mutators
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

			// Use factory method without argument
			NormalAnnotationExpr t = Trees.normalAnnotationExpr().withName(name).withPairs(pairs);
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(pairs, t.pairs());

			// Use factory method with arguments
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

			// Use factory method without argument
			ObjectCreationExpr t = Trees.objectCreationExpr().withScope(scope).withTypeArgs(typeArgs).withType(type).withArgs(args).withBody(body);
			Assert.assertEquals(scope, t.scope());
			Assert.assertEquals(typeArgs, t.typeArgs());
			Assert.assertEquals(type, t.type());
			Assert.assertEquals(args, t.args());
			Assert.assertEquals(body, t.body());

			// Use factory method with arguments
			t = Trees.objectCreationExpr(type).withScope(scope).withTypeArgs(typeArgs).withArgs(args).withBody(body);
			Assert.assertEquals(scope, t.scope());
			Assert.assertEquals(typeArgs, t.typeArgs());
			Assert.assertEquals(type, t.type());
			Assert.assertEquals(args, t.args());
			Assert.assertEquals(body, t.body());

			// Use specialized NodeOption.some() mutators
			t = t.withScope(scope.get()).withBody(body.get());
			Assert.assertEquals(scope, t.scope());
			Assert.assertEquals(body, t.body());

			// Use specialized NodeOption.none() mutators
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

			// Use factory method without argument
			ParenthesizedExpr t = Trees.parenthesizedExpr().withInner(inner);
			Assert.assertEquals(inner, t.inner());

			// Use factory method with arguments
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

			// Use factory method without argument
			SingleMemberAnnotationExpr t = Trees.singleMemberAnnotationExpr().withName(name).withMemberValue(memberValue);
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(memberValue, t.memberValue());

			// Use factory method with arguments
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

			// Use factory method without argument
			SuperExpr t = Trees.superExpr().withClassExpr(classExpr);
			Assert.assertEquals(classExpr, t.classExpr());

			// Use specialized NodeOption.some() mutators
			t = t.withClassExpr(classExpr.get());
			Assert.assertEquals(classExpr, t.classExpr());

			// Use specialized NodeOption.none() mutators
			t = t.withNoClassExpr();
			Assert.assertEquals(Trees.<Expr>none(), t.classExpr());
		}
	}

	@Test
	public void testThisExpr() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			NodeOption<Expr> classExpr = arbitrary.arbitraryOptionExpr();

			// Use factory method without argument
			ThisExpr t = Trees.thisExpr().withClassExpr(classExpr);
			Assert.assertEquals(classExpr, t.classExpr());

			// Use specialized NodeOption.some() mutators
			t = t.withClassExpr(classExpr.get());
			Assert.assertEquals(classExpr, t.classExpr());

			// Use specialized NodeOption.none() mutators
			t = t.withNoClassExpr();
			Assert.assertEquals(Trees.<Expr>none(), t.classExpr());
		}
	}

	@Test
	public void testTypeExpr() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			Type type = arbitrary.arbitraryType();

			// Use factory method without argument
			TypeExpr t = Trees.typeExpr().withType(type);
			Assert.assertEquals(type, t.type());

			// Use factory method with arguments
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

			// Use factory method without argument
			UnaryExpr t = Trees.unaryExpr().withOp(op).withExpr(expr);
			Assert.assertEquals(op, t.op());
			Assert.assertEquals(expr, t.expr());

			// Use factory method with arguments
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

			// Use factory method without argument
			VariableDeclarationExpr t = Trees.variableDeclarationExpr().withDeclaration(declaration);
			Assert.assertEquals(declaration, t.declaration());

			// Use factory method with arguments
			t = Trees.variableDeclarationExpr(declaration);
			Assert.assertEquals(declaration, t.declaration());
		}
	}

	@Test
	public void testName() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			String id = arbitrary.arbitraryString();

			// Use factory method without argument
			Name t = Trees.name().withId(id);
			Assert.assertEquals(id, t.id());

			// Use factory method with arguments
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

			// Use factory method without argument
			QualifiedName t = Trees.qualifiedName().withQualifier(qualifier).withName(name);
			Assert.assertEquals(qualifier, t.qualifier());
			Assert.assertEquals(name, t.name());

			// Use factory method with arguments
			t = Trees.qualifiedName(name).withQualifier(qualifier);
			Assert.assertEquals(qualifier, t.qualifier());
			Assert.assertEquals(name, t.name());

			// Use specialized name mutators
			t = t.withName(name.id());
			Assert.assertEquals(name, t.name());

			// Use specialized NodeOption.some() mutators
			t = t.withQualifier(qualifier.get());
			Assert.assertEquals(qualifier, t.qualifier());

			// Use specialized NodeOption.none() mutators
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

			// Use factory method without argument
			AssertStmt t = Trees.assertStmt().withCheck(check).withMsg(msg);
			Assert.assertEquals(check, t.check());
			Assert.assertEquals(msg, t.msg());

			// Use factory method with arguments
			t = Trees.assertStmt(check).withMsg(msg);
			Assert.assertEquals(check, t.check());
			Assert.assertEquals(msg, t.msg());

			// Use specialized NodeOption.some() mutators
			t = t.withMsg(msg.get());
			Assert.assertEquals(msg, t.msg());

			// Use specialized NodeOption.none() mutators
			t = t.withNoMsg();
			Assert.assertEquals(Trees.<Expr>none(), t.msg());
		}
	}

	@Test
	public void testBlockStmt() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			NodeList<Stmt> stmts = arbitrary.arbitraryListStmt();

			// Use factory method without argument
			BlockStmt t = Trees.blockStmt().withStmts(stmts);
			Assert.assertEquals(stmts, t.stmts());
		}
	}

	@Test
	public void testBreakStmt() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			NodeOption<Name> id = arbitrary.arbitraryOptionName();

			// Use factory method without argument
			BreakStmt t = Trees.breakStmt().withId(id);
			Assert.assertEquals(id, t.id());

			// Use specialized NodeOption.some() mutators
			t = t.withId(id.get());
			Assert.assertEquals(id, t.id());

			// Use specialized NodeOption.none() mutators
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

			// Use factory method without argument
			CatchClause t = Trees.catchClause().withParam(param).withCatchBlock(catchBlock);
			Assert.assertEquals(param, t.param());
			Assert.assertEquals(catchBlock, t.catchBlock());

			// Use factory method with arguments
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

			// Use factory method without argument
			ContinueStmt t = Trees.continueStmt().withId(id);
			Assert.assertEquals(id, t.id());

			// Use specialized NodeOption.some() mutators
			t = t.withId(id.get());
			Assert.assertEquals(id, t.id());

			// Use specialized NodeOption.none() mutators
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

			// Use factory method without argument
			DoStmt t = Trees.doStmt().withBody(body).withCondition(condition);
			Assert.assertEquals(body, t.body());
			Assert.assertEquals(condition, t.condition());

			// Use factory method with arguments
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

			// Use factory method without argument
			ExplicitConstructorInvocationStmt t = Trees.explicitConstructorInvocationStmt().withTypeArgs(typeArgs).setThis(isThis).withExpr(expr).withArgs(args);
			Assert.assertEquals(typeArgs, t.typeArgs());
			Assert.assertEquals(isThis, t.isThis());
			Assert.assertEquals(expr, t.expr());
			Assert.assertEquals(args, t.args());

			// Use specialized NodeOption.some() mutators
			t = t.withExpr(expr.get());
			Assert.assertEquals(expr, t.expr());

			// Use specialized NodeOption.none() mutators
			t = t.withNoExpr();
			Assert.assertEquals(Trees.<Expr>none(), t.expr());
		}
	}

	@Test
	public void testExpressionStmt() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			Expr expr = arbitrary.arbitraryExpr();

			// Use factory method without argument
			ExpressionStmt t = Trees.expressionStmt().withExpr(expr);
			Assert.assertEquals(expr, t.expr());

			// Use factory method with arguments
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

			// Use factory method without argument
			ForStmt t = Trees.forStmt().withInit(init).withCompare(compare).withUpdate(update).withBody(body);
			Assert.assertEquals(init, t.init());
			Assert.assertEquals(compare, t.compare());
			Assert.assertEquals(update, t.update());
			Assert.assertEquals(body, t.body());

			// Use factory method with arguments
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

			// Use factory method without argument
			ForeachStmt t = Trees.foreachStmt().withVar(var).withIterable(iterable).withBody(body);
			Assert.assertEquals(var, t.var());
			Assert.assertEquals(iterable, t.iterable());
			Assert.assertEquals(body, t.body());

			// Use factory method with arguments
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

			// Use factory method without argument
			IfStmt t = Trees.ifStmt().withCondition(condition).withThenStmt(thenStmt).withElseStmt(elseStmt);
			Assert.assertEquals(condition, t.condition());
			Assert.assertEquals(thenStmt, t.thenStmt());
			Assert.assertEquals(elseStmt, t.elseStmt());

			// Use factory method with arguments
			t = Trees.ifStmt(condition, thenStmt).withElseStmt(elseStmt);
			Assert.assertEquals(condition, t.condition());
			Assert.assertEquals(thenStmt, t.thenStmt());
			Assert.assertEquals(elseStmt, t.elseStmt());

			// Use specialized NodeOption.some() mutators
			t = t.withElseStmt(elseStmt.get());
			Assert.assertEquals(elseStmt, t.elseStmt());

			// Use specialized NodeOption.none() mutators
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

			// Use factory method without argument
			LabeledStmt t = Trees.labeledStmt().withLabel(label).withStmt(stmt);
			Assert.assertEquals(label, t.label());
			Assert.assertEquals(stmt, t.stmt());

			// Use factory method with arguments
			t = Trees.labeledStmt(label, stmt);
			Assert.assertEquals(label, t.label());
			Assert.assertEquals(stmt, t.stmt());

			// Use specialized name mutators
			t = t.withLabel(label.id());
			Assert.assertEquals(label, t.label());
		}
	}

	@Test
	public void testReturnStmt() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			NodeOption<Expr> expr = arbitrary.arbitraryOptionExpr();

			// Use factory method without argument
			ReturnStmt t = Trees.returnStmt().withExpr(expr);
			Assert.assertEquals(expr, t.expr());

			// Use specialized NodeOption.some() mutators
			t = t.withExpr(expr.get());
			Assert.assertEquals(expr, t.expr());

			// Use specialized NodeOption.none() mutators
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

			// Use factory method without argument
			SwitchCase t = Trees.switchCase().withLabel(label).withStmts(stmts);
			Assert.assertEquals(label, t.label());
			Assert.assertEquals(stmts, t.stmts());

			// Use specialized NodeOption.some() mutators
			t = t.withLabel(label.get());
			Assert.assertEquals(label, t.label());

			// Use specialized NodeOption.none() mutators
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

			// Use factory method without argument
			SwitchStmt t = Trees.switchStmt().withSelector(selector).withCases(cases);
			Assert.assertEquals(selector, t.selector());
			Assert.assertEquals(cases, t.cases());

			// Use factory method with arguments
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

			// Use factory method without argument
			SynchronizedStmt t = Trees.synchronizedStmt().withExpr(expr).withBlock(block);
			Assert.assertEquals(expr, t.expr());
			Assert.assertEquals(block, t.block());

			// Use factory method with arguments
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

			// Use factory method without argument
			ThrowStmt t = Trees.throwStmt().withExpr(expr);
			Assert.assertEquals(expr, t.expr());

			// Use factory method with arguments
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

			// Use factory method without argument
			TryStmt t = Trees.tryStmt().withResources(resources).withTrailingSemiColon(trailingSemiColon).withTryBlock(tryBlock).withCatchs(catchs).withFinallyBlock(finallyBlock);
			Assert.assertEquals(resources, t.resources());
			Assert.assertEquals(trailingSemiColon, t.trailingSemiColon());
			Assert.assertEquals(tryBlock, t.tryBlock());
			Assert.assertEquals(catchs, t.catchs());
			Assert.assertEquals(finallyBlock, t.finallyBlock());

			// Use factory method with arguments
			t = Trees.tryStmt(tryBlock).withResources(resources).withTrailingSemiColon(trailingSemiColon).withCatchs(catchs).withFinallyBlock(finallyBlock);
			Assert.assertEquals(resources, t.resources());
			Assert.assertEquals(trailingSemiColon, t.trailingSemiColon());
			Assert.assertEquals(tryBlock, t.tryBlock());
			Assert.assertEquals(catchs, t.catchs());
			Assert.assertEquals(finallyBlock, t.finallyBlock());

			// Use specialized NodeOption.some() mutators
			t = t.withFinallyBlock(finallyBlock.get());
			Assert.assertEquals(finallyBlock, t.finallyBlock());

			// Use specialized NodeOption.none() mutators
			t = t.withNoFinallyBlock();
			Assert.assertEquals(Trees.<BlockStmt>none(), t.finallyBlock());
		}
	}

	@Test
	public void testTypeDeclarationStmt() {
		Arbitrary arbitrary = new Arbitrary(true);
		for (int i = 0; i < 10; i++) {
			TypeDecl typeDecl = arbitrary.arbitraryTypeDecl();

			// Use factory method without argument
			TypeDeclarationStmt t = Trees.typeDeclarationStmt().withTypeDecl(typeDecl);
			Assert.assertEquals(typeDecl, t.typeDecl());

			// Use factory method with arguments
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

			// Use factory method without argument
			WhileStmt t = Trees.whileStmt().withCondition(condition).withBody(body);
			Assert.assertEquals(condition, t.condition());
			Assert.assertEquals(body, t.body());

			// Use factory method with arguments
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

			// Use factory method without argument
			ArrayType t = Trees.arrayType().withComponentType(componentType).withDims(dims);
			Assert.assertEquals(componentType, t.componentType());
			Assert.assertEquals(dims, t.dims());

			// Use factory method with arguments
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

			// Use factory method without argument
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

			// Use factory method without argument
			PrimitiveType t = Trees.primitiveType().withAnnotations(annotations).withPrimitive(primitive);
			Assert.assertEquals(annotations, t.annotations());
			Assert.assertEquals(primitive, t.primitive());

			// Use factory method with arguments
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

			// Use factory method without argument
			QualifiedType t = Trees.qualifiedType().withAnnotations(annotations).withScope(scope).withName(name).withTypeArgs(typeArgs);
			Assert.assertEquals(annotations, t.annotations());
			Assert.assertEquals(scope, t.scope());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(typeArgs, t.typeArgs());

			// Use factory method with arguments
			t = Trees.qualifiedType(name).withAnnotations(annotations).withScope(scope).withTypeArgs(typeArgs);
			Assert.assertEquals(annotations, t.annotations());
			Assert.assertEquals(scope, t.scope());
			Assert.assertEquals(name, t.name());
			Assert.assertEquals(typeArgs, t.typeArgs());

			// Use specialized name mutators
			t = t.withName(name.id());
			Assert.assertEquals(name, t.name());

			// Use specialized NodeOption.some() mutators
			t = t.withScope(scope.get()).withTypeArgs(typeArgs.get());
			Assert.assertEquals(scope, t.scope());
			Assert.assertEquals(typeArgs, t.typeArgs());

			// Use specialized NodeOption.none() mutators
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

			// Use factory method without argument
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

			// Use factory method without argument
			WildcardType t = Trees.wildcardType().withAnnotations(annotations).withExt(ext).withSup(sup);
			Assert.assertEquals(annotations, t.annotations());
			Assert.assertEquals(ext, t.ext());
			Assert.assertEquals(sup, t.sup());

			// Use specialized NodeOption.some() mutators
			t = t.withExt(ext.get()).withSup(sup.get());
			Assert.assertEquals(ext, t.ext());
			Assert.assertEquals(sup, t.sup());

			// Use specialized NodeOption.none() mutators
			t = t.withNoExt().withNoSup();
			Assert.assertEquals(Trees.<ReferenceType>none(), t.ext());
			Assert.assertEquals(Trees.<ReferenceType>none(), t.sup());
		}
	}
}
