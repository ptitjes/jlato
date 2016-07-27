package org.jlato.internal.parser;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.coll.*;
import org.jlato.internal.bu.decl.*;
import org.jlato.internal.bu.expr.*;
import org.jlato.internal.bu.name.*;
import org.jlato.internal.bu.stmt.*;
import org.jlato.internal.bu.type.*;
import org.jlato.parser.ParseException;
import org.jlato.tree.Problem.Severity;
import org.jlato.tree.decl.ModifierKeyword;
import org.jlato.tree.expr.AssignOp;
import org.jlato.tree.expr.BinaryOp;
import org.jlato.tree.expr.UnaryOp;
import org.jlato.tree.type.Primitive;

public class ParserImplementation extends ParserBaseExtended {

	public BUTree<SNodeList> parseNodeListVar() throws ParseException {
		parse("<NODE_LIST_VARIABLE>");
		return makeVar();
	}

	public BUTree<SName> parseNodeVar() throws ParseException {
		parse("<NODE_VARIABLE>");
		return makeVar();
	}

	public BUTree<SCompilationUnit> parseCompilationUnit() throws ParseException {
		BUTree<SPackageDecl> packageDecl = null;
		BUTree<SNodeList> imports;
		BUTree<SNodeList> types;
		BUTree<SCompilationUnit> compilationUnit;
		run();
		if (backupLookahead(matchCompilationUnit1())) {
			packageDecl = parsePackageDecl();
		}
		imports = parseImportDecls();
		types = parseTypeDecls();
		compilationUnit = dress(SCompilationUnit.make(packageDecl, imports, types));
		parseEpilog();
		return dressWithPrologAndEpilog(compilationUnit);
	}

	private boolean matchCompilationUnit1() {
		if (!matchPackageDecl())
			return false;
		return true;
	}

	public void parseEpilog() throws ParseException {
		if (backupLookahead(matchNext("<EOF>"))) {
			parse("<EOF>");
		} else {
			parse("\\u001a");
		}
	}

	public BUTree<SPackageDecl> parsePackageDecl() throws ParseException {
		BUTree<SNodeList> annotations = null;
		BUTree<SQualifiedName> name;
		run();
		annotations = parseAnnotations();
		parse("package");
		name = parseQualifiedName();
		parse(";");
		return dress(SPackageDecl.make(annotations, name));
	}

	public BUTree<SNodeList> parseImportDecls() throws ParseException {
		BUTree<SNodeList> imports = emptyList();
		BUTree<SImportDecl> importDecl = null;
		while (backupLookahead(matchNext("import"))) {
			importDecl = parseImportDecl();
			imports = append(imports, importDecl);
		}
		return imports;
	}

	public BUTree<SImportDecl> parseImportDecl() throws ParseException {
		BUTree<SQualifiedName> name;
		boolean isStatic = false;
		boolean isAsterisk = false;
		run();
		parse("import");
		if (backupLookahead(matchNext("static"))) {
			parse("static");
			isStatic = true;
		}
		name = parseQualifiedName();
		if (backupLookahead(matchNext("."))) {
			parse(".");
			parse("*");
			isAsterisk = true;
		}
		parse(";");
		return dress(SImportDecl.make(name, isStatic, isAsterisk));
	}

	public BUTree<SNodeList> parseTypeDecls() throws ParseException {
		BUTree<SNodeList> types = emptyList();
		BUTree<? extends STypeDecl> typeDecl = null;
		while (backupLookahead(matchNext("abstract", "class", "strictfp", ";", "transient", "private", "public", "volatile", "native", "synchronized", "interface", "final", "default", "protected", "static", "enum", "@"))) {
			typeDecl = parseTypeDecl();
			types = append(types, typeDecl);
		}
		return types;
	}

	public BUTree<SNodeList> parseModifiers() throws ParseException {
		BUTree<SNodeList> modifiers = emptyList();
		BUTree<? extends SAnnotationExpr> ann;
		while (backupLookahead(matchNext("abstract", "strictfp", "transient", "private", "public", "volatile", "native", "synchronized", "final", "default", "protected", "static", "@"))) {
			if (backupLookahead(matchNext("public"))) {
				parse("public");
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Public));
			} else if (backupLookahead(matchNext("protected"))) {
				parse("protected");
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Protected));
			} else if (backupLookahead(matchNext("private"))) {
				parse("private");
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Private));
			} else if (backupLookahead(matchNext("abstract"))) {
				parse("abstract");
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Abstract));
			} else if (backupLookahead(matchNext("default"))) {
				parse("default");
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Default));
			} else if (backupLookahead(matchNext("static"))) {
				parse("static");
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Static));
			} else if (backupLookahead(matchNext("final"))) {
				parse("final");
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Final));
			} else if (backupLookahead(matchNext("transient"))) {
				parse("transient");
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Transient));
			} else if (backupLookahead(matchNext("volatile"))) {
				parse("volatile");
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Volatile));
			} else if (backupLookahead(matchNext("synchronized"))) {
				parse("synchronized");
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Synchronized));
			} else if (backupLookahead(matchNext("native"))) {
				parse("native");
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Native));
			} else if (backupLookahead(matchNext("strictfp"))) {
				parse("strictfp");
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.StrictFP));
			} else {
				ann = parseAnnotation();
				modifiers = append(modifiers, ann);
			}
		}
		return modifiers;
	}

	public BUTree<SNodeList> parseModifiersNoDefault() throws ParseException {
		BUTree<SNodeList> modifiers = emptyList();
		BUTree<? extends SAnnotationExpr> ann;
		while (backupLookahead(matchNext("volatile", "abstract", "native", "strictfp", "transient", "private", "synchronized", "final", "protected", "static", "public", "@"))) {
			if (backupLookahead(matchNext("public"))) {
				parse("public");
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Public));
			} else if (backupLookahead(matchNext("protected"))) {
				parse("protected");
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Protected));
			} else if (backupLookahead(matchNext("private"))) {
				parse("private");
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Private));
			} else if (backupLookahead(matchNext("abstract"))) {
				parse("abstract");
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Abstract));
			} else if (backupLookahead(matchNext("static"))) {
				parse("static");
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Static));
			} else if (backupLookahead(matchNext("final"))) {
				parse("final");
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Final));
			} else if (backupLookahead(matchNext("transient"))) {
				parse("transient");
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Transient));
			} else if (backupLookahead(matchNext("volatile"))) {
				parse("volatile");
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Volatile));
			} else if (backupLookahead(matchNext("synchronized"))) {
				parse("synchronized");
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Synchronized));
			} else if (backupLookahead(matchNext("native"))) {
				parse("native");
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Native));
			} else if (backupLookahead(matchNext("strictfp"))) {
				parse("strictfp");
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.StrictFP));
			} else {
				ann = parseAnnotation();
				modifiers = append(modifiers, ann);
			}
		}
		return modifiers;
	}

	public BUTree<? extends STypeDecl> parseTypeDecl() throws ParseException {
		BUTree<SNodeList> modifiers;
		BUTree<? extends STypeDecl> ret;
		run();
		if (backupLookahead(matchNext(";"))) {
			parse(";");
			ret = dress(SEmptyTypeDecl.make());
		} else {
			modifiers = parseModifiers();
			if (backupLookahead(matchNext("class", "interface"))) {
				ret = parseClassOrInterfaceDecl(modifiers);
			} else if (backupLookahead(matchNext("enum"))) {
				ret = parseEnumDecl(modifiers);
			} else {
				ret = parseAnnotationTypeDecl(modifiers);
			}
		}
		return ret;
	}

	public BUTree<? extends STypeDecl> parseClassOrInterfaceDecl(BUTree<SNodeList> modifiers) throws ParseException {
		TypeKind typeKind;
		BUTree<SName> name;
		BUTree<SNodeList> typeParams = null;
		BUTree<SQualifiedType> superClassType = null;
		BUTree<SNodeList> extendsClause = null;
		BUTree<SNodeList> implementsClause = null;
		BUTree<SNodeList> members;
		ByRef<BUProblem> problem = new ByRef<BUProblem>(null);
		if (backupLookahead(matchNext("class"))) {
			parse("class");
			typeKind = TypeKind.Class;
			name = parseName();
			if (backupLookahead(matchNext("<"))) {
				typeParams = parseTypeParameters();
			}
			if (backupLookahead(matchNext("extends"))) {
				parse("extends");
				superClassType = parseAnnotatedQualifiedType();
			}
			if (backupLookahead(matchNext("implements"))) {
				implementsClause = parseImplementsList(typeKind, problem);
			}
		} else {
			parse("interface");
			typeKind = TypeKind.Interface;
			name = parseName();
			if (backupLookahead(matchNext("<"))) {
				typeParams = parseTypeParameters();
			}
			if (backupLookahead(matchNext("extends"))) {
				extendsClause = parseExtendsList();
			}
		}
		members = parseClassOrInterfaceBody(typeKind);
		if (typeKind == TypeKind.Interface)
	return dress(SInterfaceDecl.make(modifiers, name, ensureNotNull(typeParams), ensureNotNull(extendsClause), members)).withProblem(problem.value);
else {
	return dress(SClassDecl.make(modifiers, name, ensureNotNull(typeParams), optionOf(superClassType), ensureNotNull(implementsClause), members));
}
	}

	public BUTree<SNodeList> parseExtendsList() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<SQualifiedType> cit;
		BUTree<SNodeList> annotations = null;
		parse("extends");
		if (backupLookahead(matchNext("<NODE_VARIABLE>", "<IDENTIFIER>", "@"))) {
			cit = parseAnnotatedQualifiedType();
			ret = append(ret, cit);
			while (backupLookahead(matchNext(","))) {
				parse(",");
				cit = parseAnnotatedQualifiedType();
				ret = append(ret, cit);
			}
		} else {
			ret = parseNodeListVar();
		}
		return ret;
	}

	public BUTree<SNodeList> parseImplementsList(TypeKind typeKind, ByRef<BUProblem> problem) throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<SQualifiedType> cit;
		BUTree<SNodeList> annotations = null;
		parse("implements");
		if (backupLookahead(matchNext("<NODE_VARIABLE>", "<IDENTIFIER>", "@"))) {
			cit = parseAnnotatedQualifiedType();
			ret = append(ret, cit);
			while (backupLookahead(matchNext(","))) {
				parse(",");
				cit = parseAnnotatedQualifiedType();
				ret = append(ret, cit);
			}
			if (typeKind == TypeKind.Interface) problem.value = new BUProblem(Severity.ERROR, "An interface cannot implement other interfaces");

		} else {
			ret = parseNodeListVar();
		}
		return ret;
	}

	public BUTree<? extends STypeDecl> parseEnumDecl(BUTree<SNodeList> modifiers) throws ParseException {
		BUTree<SName> name;
		BUTree<SNodeList> implementsClause = emptyList();
		BUTree<SEnumConstantDecl> entry;
		BUTree<SNodeList> constants = emptyList();
		boolean trailingComma = false;
		BUTree<SNodeList> members = null;
		ByRef<BUProblem> problem = new ByRef<BUProblem>(null);
		parse("enum");
		name = parseName();
		if (backupLookahead(matchNext("implements"))) {
			implementsClause = parseImplementsList(TypeKind.Enum, problem);
		}
		parse("{");
		if (backupLookahead(matchNext("<NODE_LIST_VARIABLE>", "abstract", "strictfp", "<NODE_VARIABLE>", "transient", "<IDENTIFIER>", "private", "public", "volatile", "native", "synchronized", "final", "default", "protected", "static", "@"))) {
			if (backupLookahead(matchNext("abstract", "strictfp", "<NODE_VARIABLE>", "transient", "<IDENTIFIER>", "private", "public", "volatile", "native", "synchronized", "final", "default", "protected", "static", "@"))) {
				entry = parseEnumConstantDecl();
				constants = append(constants, entry);
				while (backupLookahead(matchNext(","))) {
					parse(",");
					entry = parseEnumConstantDecl();
					constants = append(constants, entry);
				}
			} else {
				constants = parseNodeListVar();
			}
		}
		if (backupLookahead(matchNext(","))) {
			parse(",");
			trailingComma = true;
		}
		if (backupLookahead(matchNext(";"))) {
			parse(";");
			members = parseClassOrInterfaceBodyDecls(TypeKind.Enum);
		}
		parse("}");
		return dress(SEnumDecl.make(modifiers, name, implementsClause, constants, trailingComma, ensureNotNull(members))).withProblem(problem.value);
	}

	public BUTree<SEnumConstantDecl> parseEnumConstantDecl() throws ParseException {
		BUTree<SNodeList> modifiers = null;
		BUTree<SName> name;
		BUTree<SNodeList> args = null;
		BUTree<SNodeList> classBody = null;
		run();
		modifiers = parseModifiers();
		name = parseName();
		if (backupLookahead(matchNext("("))) {
			args = parseArguments();
		}
		if (backupLookahead(matchNext("{"))) {
			classBody = parseClassOrInterfaceBody(TypeKind.Class);
		}
		return dress(SEnumConstantDecl.make(modifiers, name, optionOf(args), optionOf(classBody)));
	}

	public BUTree<SAnnotationDecl> parseAnnotationTypeDecl(BUTree<SNodeList> modifiers) throws ParseException {
		BUTree<SName> name;
		BUTree<SNodeList> members;
		parse("@");
		parse("interface");
		name = parseName();
		members = parseAnnotationTypeBody();
		return dress(SAnnotationDecl.make(modifiers, name, members));
	}

	public BUTree<SNodeList> parseAnnotationTypeBody() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<? extends SMemberDecl> member;
		parse("{");
		if (backupLookahead(matchNext("class", "abstract", "strictfp", ";", "transient", "private", "double", "float", "long", "byte", "final", "default", "static", "enum", "<NODE_LIST_VARIABLE>", "short", "<NODE_VARIABLE>", "<IDENTIFIER>", "char", "public", "volatile", "native", "synchronized", "interface", "protected", "@", "int", "boolean"))) {
			if (backupLookahead(matchNext("class", "abstract", "strictfp", ";", "transient", "private", "double", "float", "long", "byte", "final", "default", "static", "enum", "short", "<NODE_VARIABLE>", "<IDENTIFIER>", "char", "public", "volatile", "native", "synchronized", "interface", "protected", "@", "int", "boolean"))) {
				do {
					member = parseAnnotationTypeBodyDecl();
					ret = append(ret, member);
				} while (backupLookahead(matchNext("class", "abstract", "strictfp", ";", "transient", "private", "double", "float", "long", "byte", "final", "default", "static", "enum", "short", "<NODE_VARIABLE>", "<IDENTIFIER>", "char", "public", "volatile", "native", "synchronized", "interface", "protected", "@", "int", "boolean")));
			} else {
				ret = parseNodeListVar();
			}
		}
		parse("}");
		return ret;
	}

	public BUTree<? extends SMemberDecl> parseAnnotationTypeBodyDecl() throws ParseException {
		BUTree<SNodeList> modifiers;
		BUTree<? extends SMemberDecl> ret;
		run();
		if (backupLookahead(matchNext(";"))) {
			parse(";");
			ret = dress(SEmptyTypeDecl.make());
		} else {
			modifiers = parseModifiers();
			if (backupLookahead(matchAnnotationTypeBodyDecl1())) {
				ret = parseAnnotationTypeMemberDecl(modifiers);
			} else if (backupLookahead(matchNext("class", "interface"))) {
				ret = parseClassOrInterfaceDecl(modifiers);
			} else if (backupLookahead(matchNext("enum"))) {
				ret = parseEnumDecl(modifiers);
			} else if (backupLookahead(matchNext("@"))) {
				ret = parseAnnotationTypeDecl(modifiers);
			} else {
				ret = parseFieldDecl(modifiers);
			}
		}
		return ret;
	}

	private boolean matchAnnotationTypeBodyDecl1() {
		if (!matchType())
			return false;
		if (!matchName())
			return false;
		if (!match("("))
			return false;
		return true;
	}

	public BUTree<SAnnotationMemberDecl> parseAnnotationTypeMemberDecl(BUTree<SNodeList> modifiers) throws ParseException {
		BUTree<? extends SType> type;
		BUTree<SName> name;
		BUTree<SNodeList> dims;
		BUTree<SNodeOption> defaultVal = none();
		BUTree<? extends SExpr> val = null;
		type = parseType(null);
		name = parseName();
		parse("(");
		parse(")");
		dims = parseArrayDims();
		if (backupLookahead(matchNext("default"))) {
			parse("default");
			val = parseMemberValue();
			defaultVal = optionOf(val);
		}
		parse(";");
		return dress(SAnnotationMemberDecl.make(modifiers, type, name, dims, defaultVal));
	}

	public BUTree<SNodeList> parseTypeParameters() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<STypeParameter> tp;
		parse("<");
		if (backupLookahead(matchNext("<NODE_VARIABLE>", "<IDENTIFIER>", "@"))) {
			tp = parseTypeParameter();
			ret = append(ret, tp);
			while (backupLookahead(matchNext(","))) {
				parse(",");
				tp = parseTypeParameter();
				ret = append(ret, tp);
			}
		} else {
			ret = parseNodeListVar();
		}
		parse(">");
		return ret;
	}

	public BUTree<STypeParameter> parseTypeParameter() throws ParseException {
		BUTree<SNodeList> annotations = null;
		BUTree<SName> name;
		BUTree<SNodeList> typeBounds = null;
		run();
		annotations = parseAnnotations();
		name = parseName();
		if (backupLookahead(matchNext("extends"))) {
			typeBounds = parseTypeBounds();
		}
		return dress(STypeParameter.make(annotations, name, ensureNotNull(typeBounds)));
	}

	public BUTree<SNodeList> parseTypeBounds() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<SQualifiedType> cit;
		BUTree<SNodeList> annotations = null;
		parse("extends");
		if (backupLookahead(matchNext("<NODE_VARIABLE>", "<IDENTIFIER>", "@"))) {
			cit = parseAnnotatedQualifiedType();
			ret = append(ret, cit);
			while (backupLookahead(matchNext("&"))) {
				parse("&");
				cit = parseAnnotatedQualifiedType();
				ret = append(ret, cit);
			}
		} else {
			ret = parseNodeListVar();
		}
		return ret;
	}

	public BUTree<SNodeList> parseClassOrInterfaceBody(TypeKind typeKind) throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<? extends SMemberDecl> member;
		parse("{");
		ret = parseClassOrInterfaceBodyDecls(typeKind);
		parse("}");
		return ret;
	}

	public BUTree<SNodeList> parseClassOrInterfaceBodyDecls(TypeKind typeKind) throws ParseException {
		BUTree<? extends SMemberDecl> member;
		BUTree<SNodeList> ret = emptyList();
		if (backupLookahead(matchNext("class", "abstract", "{", "strictfp", ";", "transient", "private", "double", "float", "long", "byte", "final", "default", "static", "enum", "void", "<NODE_LIST_VARIABLE>", "short", "<", "<NODE_VARIABLE>", "<IDENTIFIER>", "char", "public", "volatile", "native", "synchronized", "interface", "protected", "@", "int", "boolean"))) {
			if (backupLookahead(matchNext("class", "abstract", "{", "strictfp", ";", "transient", "private", "double", "float", "long", "byte", "final", "default", "static", "enum", "void", "short", "<", "<NODE_VARIABLE>", "<IDENTIFIER>", "char", "public", "volatile", "native", "synchronized", "interface", "protected", "@", "int", "boolean"))) {
				do {
					member = parseClassOrInterfaceBodyDecl(typeKind);
					ret = append(ret, member);
				} while (backupLookahead(matchNext("class", "abstract", "{", "strictfp", ";", "transient", "private", "double", "float", "long", "byte", "final", "default", "static", "enum", "void", "short", "<", "<NODE_VARIABLE>", "<IDENTIFIER>", "char", "public", "volatile", "native", "synchronized", "interface", "protected", "@", "int", "boolean")));
			} else {
				ret = parseNodeListVar();
			}
		}
		return ret;
	}

	public BUTree<? extends SMemberDecl> parseClassOrInterfaceBodyDecl(TypeKind typeKind) throws ParseException {
		BUTree<SNodeList> modifiers;
		BUTree<? extends SMemberDecl> ret;
		BUProblem problem = null;
		run();
		if (backupLookahead(matchNext(";"))) {
			parse(";");
			ret = dress(SEmptyMemberDecl.make());
		} else {
			modifiers = parseModifiers();
			if (modifiers != null && contains(modifiers, SModifier.make(ModifierKeyword.Default)) && typeKind != TypeKind.Interface) problem = new BUProblem(Severity.ERROR, "Only interfaces can have default members");

			if (backupLookahead(matchNext("{"))) {
				ret = parseInitializerDecl(modifiers);
				if (typeKind == TypeKind.Interface) ret = ret.withProblem(new BUProblem(Severity.ERROR, "An interface cannot have initializers"));

			} else if (backupLookahead(matchNext("class", "interface"))) {
				ret = parseClassOrInterfaceDecl(modifiers);
			} else if (backupLookahead(matchNext("enum"))) {
				ret = parseEnumDecl(modifiers);
			} else if (backupLookahead(matchNext("@"))) {
				ret = parseAnnotationTypeDecl(modifiers);
			} else if (backupLookahead(matchClassOrInterfaceBodyDecl1())) {
				ret = parseConstructorDecl(modifiers);
				if (typeKind == TypeKind.Interface) ret = ret.withProblem(new BUProblem(Severity.ERROR, "An interface cannot have constructors"));

			} else if (backupLookahead(matchClassOrInterfaceBodyDecl2())) {
				ret = parseFieldDecl(modifiers);
			} else {
				ret = parseMethodDecl(modifiers);
			}
		}
		return ret.withProblem(problem);
	}

	private boolean matchClassOrInterfaceBodyDecl1() {
		if (matchNext("<")) {
			if (!matchTypeParameters())
				return false;
		}
		if (!matchName())
			return false;
		if (!match("("))
			return false;
		return true;
	}

	private boolean matchClassOrInterfaceBodyDecl2() {
		if (!matchType())
			return false;
		if (!matchName())
			return false;
		while (matchNext("[")) {
			if (!match("["))
				return false;
			if (!match("]"))
				return false;
		}
		if (matchNext(",")) {
			if (!match(","))
				return false;
		} else if (matchNext("=")) {
			if (!match("="))
				return false;
		} else if (matchNext(";")) {
			if (!match(";"))
				return false;
		}
		return true;
	}

	public BUTree<SFieldDecl> parseFieldDecl(BUTree<SNodeList> modifiers) throws ParseException {
		BUTree<? extends SType> type;
		BUTree<SNodeList> variables = emptyList();
		BUTree<SVariableDeclarator> val;
		type = parseType(null);
		variables = parseVariableDeclarators();
		parse(";");
		return dress(SFieldDecl.make(modifiers, type, variables));
	}

	public BUTree<SLocalVariableDecl> parseVariableDecl(BUTree<SNodeList> modifiers) throws ParseException {
		BUTree<? extends SType> type;
		BUTree<SNodeList> variables = emptyList();
		type = parseType(null);
		variables = parseVariableDeclarators();
		return dress(SLocalVariableDecl.make(modifiers, type, variables));
	}

	public BUTree<SNodeList> parseVariableDeclarators() throws ParseException {
		BUTree<SNodeList> variables = emptyList();
		BUTree<SVariableDeclarator> val;
		val = parseVariableDeclarator();
		variables = append(variables, val);
		while (backupLookahead(matchNext(","))) {
			parse(",");
			val = parseVariableDeclarator();
			variables = append(variables, val);
		}
		return variables;
	}

	public BUTree<SVariableDeclarator> parseVariableDeclarator() throws ParseException {
		BUTree<SVariableDeclaratorId> id;
		BUTree<SNodeOption> init = none();
		BUTree<? extends SExpr> initExpr = null;
		run();
		id = parseVariableDeclaratorId();
		if (backupLookahead(matchNext("="))) {
			parse("=");
			initExpr = parseVariableInitializer();
			init = optionOf(initExpr);
		}
		return dress(SVariableDeclarator.make(id, init));
	}

	public BUTree<SVariableDeclaratorId> parseVariableDeclaratorId() throws ParseException {
		BUTree<SName> name;
		BUTree<SNodeList> arrayDims;
		run();
		name = parseName();
		arrayDims = parseArrayDims();
		return dress(SVariableDeclaratorId.make(name, arrayDims));
	}

	public BUTree<SNodeList> parseArrayDims() throws ParseException {
		BUTree<SNodeList> arrayDims = emptyList();
		BUTree<SNodeList> annotations;
		while (backupLookahead(matchArrayDims1())) {
			run();
			annotations = parseAnnotations();
			parse("[");
			parse("]");
			arrayDims = append(arrayDims, dress(SArrayDim.make(annotations)));
		}
		return arrayDims;
	}

	private boolean matchArrayDims1() {
		if (!matchAnnotations())
			return false;
		if (!match("["))
			return false;
		if (!match("]"))
			return false;
		return true;
	}

	public BUTree<? extends SExpr> parseVariableInitializer() throws ParseException {
		BUTree<? extends SExpr> ret;
		if (backupLookahead(matchNext("{"))) {
			ret = parseArrayInitializer();
		} else {
			ret = parseExpression();
		}
		return ret;
	}

	public BUTree<SArrayInitializerExpr> parseArrayInitializer() throws ParseException {
		BUTree<SNodeList> values = emptyList();
		BUTree<? extends SExpr> val;
		boolean trailingComma = false;
		run();
		parse("{");
		if (backupLookahead(matchNext("{", "double", "float", "-", "<FLOAT_LITERAL>", "long", "byte", "<LONG_LITERAL>", "(", "false", "super", "<INTEGER_LITERAL>", "null", "void", "this", "short", "<", "<NODE_VARIABLE>", "<IDENTIFIER>", "new", "char", "++", "--", "<CHARACTER_LITERAL>", "<DOUBLE_LITERAL>", "<STRING_LITERAL>", "+", "true", "!", "~", "int", "boolean"))) {
			val = parseVariableInitializer();
			values = append(values, val);
			while (backupLookahead(matchNext(","))) {
				parse(",");
				val = parseVariableInitializer();
				values = append(values, val);
			}
		}
		if (backupLookahead(matchNext(","))) {
			parse(",");
			trailingComma = true;
		}
		parse("}");
		return dress(SArrayInitializerExpr.make(values, trailingComma));
	}

	public BUTree<SMethodDecl> parseMethodDecl(BUTree<SNodeList> modifiers) throws ParseException {
		BUTree<SNodeList> typeParameters = null;
		BUTree<? extends SType> type;
		BUTree<SName> name;
		BUTree<SNodeList> parameters;
		BUTree<SNodeList> arrayDims;
		BUTree<SNodeList> throwsClause = null;
		BUTree<SBlockStmt> block = null;
		BUProblem problem = null;
		if (backupLookahead(matchNext("<"))) {
			typeParameters = parseTypeParameters();
		}
		type = parseResultType();
		name = parseName();
		parameters = parseFormalParameters();
		arrayDims = parseArrayDims();
		if (backupLookahead(matchNext("throws"))) {
			throwsClause = parseThrowsClause();
		}
		if (backupLookahead(matchNext("{"))) {
			block = parseBlock();
		} else {
			parse(";");
			if (modifiers != null && contains(modifiers, SModifier.make(ModifierKeyword.Default))) problem = new BUProblem(Severity.ERROR, "Default methods must have a body");

		}
		return dress(SMethodDecl.make(modifiers, ensureNotNull(typeParameters), type, name, parameters, arrayDims, ensureNotNull(throwsClause), optionOf(block))).withProblem(problem);
	}

	public BUTree<SNodeList> parseFormalParameters() throws ParseException {
		BUTree<SNodeList> ret = null;
		BUTree<SFormalParameter> par;
		parse("(");
		if (backupLookahead(matchNext("<NODE_LIST_VARIABLE>", "abstract", "short", "strictfp", "<NODE_VARIABLE>", "transient", "<IDENTIFIER>", "private", "double", "char", "float", "public", "volatile", "long", "native", "byte", "synchronized", "final", "default", "protected", "static", "@", "int", "boolean"))) {
			ret = parseFormalParameterList();
		}
		parse(")");
		return ensureNotNull(ret);
	}

	public BUTree<SNodeList> parseFormalParameterList() throws ParseException {
		BUTree<SNodeList> ret = null;
		BUTree<SFormalParameter> par;
		if (backupLookahead(matchNext("abstract", "short", "strictfp", "<NODE_VARIABLE>", "transient", "<IDENTIFIER>", "private", "double", "char", "float", "public", "volatile", "long", "native", "byte", "synchronized", "final", "default", "protected", "static", "@", "int", "boolean"))) {
			par = parseFormalParameter();
			ret = append(ret, par);
			while (backupLookahead(matchNext(","))) {
				parse(",");
				par = parseFormalParameter();
				ret = append(ret, par);
			}
		} else {
			ret = parseNodeListVar();
		}
		return ret;
	}

	public BUTree<SFormalParameter> parseFormalParameter() throws ParseException {
		BUTree<SNodeList> modifiers;
		BUTree<? extends SType> type;
		boolean isVarArg = false;
		BUTree<SVariableDeclaratorId> id;
		run();
		modifiers = parseModifiers();
		type = parseType(null);
		if (backupLookahead(matchNext("..."))) {
			parse("...");
			isVarArg = true;
		}
		id = parseVariableDeclaratorId();
		return dress(SFormalParameter.make(modifiers, type, isVarArg, id));
	}

	public BUTree<SNodeList> parseThrowsClause() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<SQualifiedType> cit;
		parse("throws");
		cit = parseAnnotatedQualifiedType();
		ret = append(ret, cit);
		while (backupLookahead(matchNext(","))) {
			parse(",");
			cit = parseAnnotatedQualifiedType();
			ret = append(ret, cit);
		}
		return ret;
	}

	public BUTree<SConstructorDecl> parseConstructorDecl(BUTree<SNodeList> modifiers) throws ParseException {
		BUTree<SNodeList> typeParameters = null;
		BUTree<SName> name;
		BUTree<SNodeList> parameters;
		BUTree<SNodeList> throwsClause = null;
		BUTree<SExplicitConstructorInvocationStmt> exConsInv = null;
		BUTree<SBlockStmt> block;
		BUTree<SNodeList> stmts = emptyList();
		BUTree<? extends SStmt> stmt;
		if (backupLookahead(matchNext("<"))) {
			typeParameters = parseTypeParameters();
		}
		name = parseName();
		parameters = parseFormalParameters();
		if (backupLookahead(matchNext("throws"))) {
			throwsClause = parseThrowsClause();
		}
		run();
		parse("{");
		if (backupLookahead(matchNext("strictfp", ";", "transient", "<LONG_LITERAL>", "false", "final", "<INTEGER_LITERAL>", "assert", "break", "null", "do", "void", "this", "short", "<", "<NODE_VARIABLE>", "<CHARACTER_LITERAL>", "++", "<DOUBLE_LITERAL>", "native", "true", "try", "@", "switch", "class", "abstract", "throw", "{", "double", "private", "if", "float", "return", "<FLOAT_LITERAL>", "long", "byte", "(", "super", "static", "<NODE_LIST_VARIABLE>", "for", "<IDENTIFIER>", "continue", "new", "char", "--", "public", "volatile", "<STRING_LITERAL>", "synchronized", "interface", "while", "protected", "int", "boolean"))) {
			if (backupLookahead(matchNext("strictfp", ";", "transient", "<LONG_LITERAL>", "false", "final", "<INTEGER_LITERAL>", "assert", "break", "null", "do", "void", "this", "short", "<", "<NODE_VARIABLE>", "<CHARACTER_LITERAL>", "++", "<DOUBLE_LITERAL>", "native", "true", "try", "@", "switch", "class", "abstract", "throw", "{", "double", "private", "if", "float", "return", "<FLOAT_LITERAL>", "long", "byte", "(", "super", "static", "for", "<IDENTIFIER>", "continue", "new", "char", "--", "public", "volatile", "<STRING_LITERAL>", "synchronized", "interface", "while", "protected", "int", "boolean"))) {
				if (backupLookahead(matchConstructorDecl1())) {
					stmt = parseExplicitConstructorInvocation();
					stmts = append(stmts, stmt);
				} else {
					stmt = parseBlockStatement();
					stmts = append(stmts, stmt);
				}
				while (backupLookahead(matchNext("strictfp", ";", "transient", "<LONG_LITERAL>", "false", "final", "<INTEGER_LITERAL>", "assert", "break", "null", "do", "void", "this", "short", "<", "<NODE_VARIABLE>", "<CHARACTER_LITERAL>", "++", "<DOUBLE_LITERAL>", "native", "true", "try", "@", "switch", "class", "abstract", "throw", "{", "double", "private", "if", "float", "return", "<FLOAT_LITERAL>", "long", "byte", "(", "super", "static", "for", "<IDENTIFIER>", "continue", "new", "char", "--", "public", "volatile", "<STRING_LITERAL>", "synchronized", "interface", "while", "protected", "int", "boolean"))) {
					stmt = parseBlockStatement();
					stmts = append(stmts, stmt);
				}
			} else {
				stmts = parseNodeListVar();
			}
		}
		parse("}");
		block = dress(SBlockStmt.make(stmts));
		return dress(SConstructorDecl.make(modifiers, ensureNotNull(typeParameters), name, parameters, ensureNotNull(throwsClause), block));
	}

	private boolean matchConstructorDecl1() {
		if (!matchExplicitConstructorInvocation())
			return false;
		return true;
	}

	public BUTree<SExplicitConstructorInvocationStmt> parseExplicitConstructorInvocation() throws ParseException {
		boolean isThis = false;
		BUTree<SNodeList> args;
		BUTree<? extends SExpr> expr = null;
		BUTree<SNodeList> typeArgs = null;
		run();
		if (backupLookahead(matchExplicitConstructorInvocation1())) {
			if (backupLookahead(matchNext("<"))) {
				typeArgs = parseTypeArguments();
			}
			parse("this");
			isThis = true;
			args = parseArguments();
			parse(";");
		} else {
			if (backupLookahead(matchExplicitConstructorInvocation2())) {
				expr = parsePrimaryExpressionWithoutSuperSuffix();
				parse(".");
			}
			if (backupLookahead(matchNext("<"))) {
				typeArgs = parseTypeArguments();
			}
			parse("super");
			args = parseArguments();
			parse(";");
		}
		return dress(SExplicitConstructorInvocationStmt.make(ensureNotNull(typeArgs), isThis, optionOf(expr), args));
	}

	private boolean matchExplicitConstructorInvocation1() {
		if (matchNext("<")) {
			if (!matchTypeArguments())
				return false;
		}
		if (!match("this"))
			return false;
		if (!match("("))
			return false;
		return true;
	}

	private boolean matchExplicitConstructorInvocation2() {
		if (!matchPrimaryExpressionWithoutSuperSuffix())
			return false;
		if (!match("."))
			return false;
		return true;
	}

	public BUTree<SNodeList> parseStatements() throws ParseException {
		BUTree<SNodeList> ret = null;
		BUTree<? extends SStmt> stmt;
		if (backupLookahead(matchNext("strictfp", ";", "transient", "<LONG_LITERAL>", "false", "final", "<INTEGER_LITERAL>", "assert", "break", "null", "do", "void", "this", "short", "<", "<NODE_VARIABLE>", "<CHARACTER_LITERAL>", "++", "<DOUBLE_LITERAL>", "native", "true", "try", "@", "switch", "class", "abstract", "throw", "{", "double", "private", "if", "float", "return", "<FLOAT_LITERAL>", "long", "byte", "(", "super", "static", "<NODE_LIST_VARIABLE>", "for", "<IDENTIFIER>", "continue", "new", "char", "--", "public", "volatile", "<STRING_LITERAL>", "synchronized", "interface", "while", "protected", "int", "boolean"))) {
			if (backupLookahead(matchNext("strictfp", ";", "transient", "<LONG_LITERAL>", "false", "final", "<INTEGER_LITERAL>", "assert", "break", "null", "do", "void", "this", "short", "<", "<NODE_VARIABLE>", "<CHARACTER_LITERAL>", "++", "<DOUBLE_LITERAL>", "native", "true", "try", "@", "switch", "class", "abstract", "throw", "{", "double", "private", "if", "float", "return", "<FLOAT_LITERAL>", "long", "byte", "(", "super", "static", "for", "<IDENTIFIER>", "continue", "new", "char", "--", "public", "volatile", "<STRING_LITERAL>", "synchronized", "interface", "while", "protected", "int", "boolean"))) {
				do {
					stmt = parseBlockStatement();
					ret = append(ret, stmt);
				} while (backupLookahead(matchNext("strictfp", ";", "transient", "<LONG_LITERAL>", "false", "final", "<INTEGER_LITERAL>", "assert", "break", "null", "do", "void", "this", "short", "<", "<NODE_VARIABLE>", "<CHARACTER_LITERAL>", "++", "<DOUBLE_LITERAL>", "native", "true", "try", "@", "switch", "class", "abstract", "throw", "{", "double", "private", "if", "float", "return", "<FLOAT_LITERAL>", "long", "byte", "(", "super", "static", "for", "<IDENTIFIER>", "continue", "new", "char", "--", "public", "volatile", "<STRING_LITERAL>", "synchronized", "interface", "while", "protected", "int", "boolean")));
			} else {
				ret = parseNodeListVar();
			}
		}
		return ensureNotNull(ret);
	}

	public BUTree<SInitializerDecl> parseInitializerDecl(BUTree<SNodeList> modifiers) throws ParseException {
		BUTree<SBlockStmt> block;
		block = parseBlock();
		return dress(SInitializerDecl.make(modifiers, block));
	}

	public BUTree<? extends SType> parseType(BUTree<SNodeList> annotations) throws ParseException {
		BUTree<? extends SType> primitiveType = null;
		BUTree<? extends SReferenceType> type = null;
		BUTree<SNodeList> arrayDims;
		if (backupLookahead(matchNext("short", "long", "byte", "double", "char", "float", "boolean", "int"))) {
			primitiveType = parsePrimitiveType(annotations);
			if (backupLookahead(matchType1())) {
				lateRun();
				arrayDims = parseArrayDimsMandatory();
				type = dress(SArrayType.make(primitiveType, arrayDims));
			}
		} else {
			type = parseQualifiedType(annotations);
			if (backupLookahead(matchType2())) {
				lateRun();
				arrayDims = parseArrayDimsMandatory();
				type = dress(SArrayType.make(type, arrayDims));
			}
		}
		return type == null ? primitiveType : type;
	}

	private boolean matchType1() {
		if (!matchAnnotations())
			return false;
		if (!match("["))
			return false;
		return true;
	}

	private boolean matchType2() {
		if (!matchAnnotations())
			return false;
		if (!match("["))
			return false;
		return true;
	}

	public BUTree<? extends SReferenceType> parseReferenceType(BUTree<SNodeList> annotations) throws ParseException {
		BUTree<? extends SType> primitiveType;
		BUTree<? extends SReferenceType> type;
		BUTree<SNodeList> arrayDims;
		if (backupLookahead(matchNext("short", "long", "byte", "double", "char", "float", "boolean", "int"))) {
			primitiveType = parsePrimitiveType(annotations);
			lateRun();
			arrayDims = parseArrayDimsMandatory();
			type = dress(SArrayType.make(primitiveType, arrayDims));
		} else {
			type = parseQualifiedType(annotations);
			if (backupLookahead(matchReferenceType1())) {
				lateRun();
				arrayDims = parseArrayDimsMandatory();
				type = dress(SArrayType.make(type, arrayDims));
			}
		}
		return type;
	}

	private boolean matchReferenceType1() {
		if (!matchAnnotations())
			return false;
		if (!match("["))
			return false;
		return true;
	}

	public BUTree<SQualifiedType> parseQualifiedType(BUTree<SNodeList> annotations) throws ParseException {
		BUTree<SNodeOption> scope = none();
		BUTree<SQualifiedType> ret;
		BUTree<SName> name;
		BUTree<SNodeList> typeArgs = null;
		if (annotations == null) {
	run();
	annotations = emptyList();
}
		name = parseName();
		if (backupLookahead(matchNext("<"))) {
			typeArgs = parseTypeArgumentsOrDiamond();
		}
		ret = dress(SQualifiedType.make(annotations, scope, name, optionOf(typeArgs)));
		while (backupLookahead(matchNext("."))) {
			parse(".");
			scope = optionOf(ret);
			lateRun();
			annotations = parseAnnotations();
			name = parseName();
			if (backupLookahead(matchNext("<"))) {
				typeArgs = parseTypeArgumentsOrDiamond();
			}
			ret = dress(SQualifiedType.make(annotations, scope, name, optionOf(typeArgs)));
		}
		return ret;
	}

	public BUTree<SNodeList> parseTypeArguments() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<? extends SType> type;
		parse("<");
		if (backupLookahead(matchNext("<NODE_LIST_VARIABLE>", "short", "<NODE_VARIABLE>", "<IDENTIFIER>", "double", "char", "float", "long", "byte", "@", "int", "boolean", "?"))) {
			ret = parseTypeArgumentList();
		}
		parse(">");
		return ret;
	}

	public BUTree<SNodeList> parseTypeArgumentsOrDiamond() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<? extends SType> type;
		parse("<");
		if (backupLookahead(matchNext("<NODE_LIST_VARIABLE>", "short", "<NODE_VARIABLE>", "<IDENTIFIER>", "double", "char", "float", "long", "byte", "@", "int", "boolean", "?"))) {
			ret = parseTypeArgumentList();
		}
		parse(">");
		return ret;
	}

	public BUTree<SNodeList> parseTypeArgumentList() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<? extends SType> type;
		if (backupLookahead(matchNext("short", "long", "<NODE_VARIABLE>", "byte", "<IDENTIFIER>", "double", "char", "float", "@", "boolean", "int", "?"))) {
			type = parseTypeArgument();
			ret = append(ret, type);
			while (backupLookahead(matchNext(","))) {
				parse(",");
				type = parseTypeArgument();
				ret = append(ret, type);
			}
			return ret;
		} else {
			parse("<NODE_LIST_VARIABLE>");
			return makeVar();
		}
	}

	public BUTree<? extends SType> parseTypeArgument() throws ParseException {
		BUTree<? extends SType> ret;
		BUTree<SNodeList> annotations = null;
		run();
		annotations = parseAnnotations();
		if (backupLookahead(matchNext("short", "long", "<NODE_VARIABLE>", "byte", "<IDENTIFIER>", "double", "char", "float", "boolean", "int"))) {
			ret = parseReferenceType(annotations);
		} else {
			ret = parseWildcard(annotations);
		}
		return ret;
	}

	public BUTree<SWildcardType> parseWildcard(BUTree<SNodeList> annotations) throws ParseException {
		BUTree<? extends SReferenceType> ext = null;
		BUTree<? extends SReferenceType> sup = null;
		BUTree<SNodeList> boundAnnotations = null;
		if (annotations == null) {
	run();
	annotations = emptyList();
}
		parse("?");
		if (backupLookahead(matchNext("extends", "super"))) {
			if (backupLookahead(matchNext("extends"))) {
				parse("extends");
				run();
				boundAnnotations = parseAnnotations();
				ext = parseReferenceType(boundAnnotations);
			} else {
				parse("super");
				run();
				boundAnnotations = parseAnnotations();
				sup = parseReferenceType(boundAnnotations);
			}
		}
		return dress(SWildcardType.make(annotations, optionOf(ext), optionOf(sup)));
	}

	public BUTree<SPrimitiveType> parsePrimitiveType(BUTree<SNodeList> annotations) throws ParseException {
		Primitive primitive;
		if (annotations == null) {
	run();
	annotations = emptyList();
}
		if (backupLookahead(matchNext("boolean"))) {
			parse("boolean");
			primitive = Primitive.Boolean;
		} else if (backupLookahead(matchNext("char"))) {
			parse("char");
			primitive = Primitive.Char;
		} else if (backupLookahead(matchNext("byte"))) {
			parse("byte");
			primitive = Primitive.Byte;
		} else if (backupLookahead(matchNext("short"))) {
			parse("short");
			primitive = Primitive.Short;
		} else if (backupLookahead(matchNext("int"))) {
			parse("int");
			primitive = Primitive.Int;
		} else if (backupLookahead(matchNext("long"))) {
			parse("long");
			primitive = Primitive.Long;
		} else if (backupLookahead(matchNext("float"))) {
			parse("float");
			primitive = Primitive.Float;
		} else {
			parse("double");
			primitive = Primitive.Double;
		}
		return dress(SPrimitiveType.make(annotations, primitive));
	}

	public BUTree<? extends SType> parseResultType() throws ParseException {
		BUTree<? extends SType> ret;
		if (backupLookahead(matchNext("void"))) {
			run();
			parse("void");
			ret = dress(SVoidType.make());
		} else {
			ret = parseType(null);
		}
		return ret;
	}

	public BUTree<SQualifiedType> parseAnnotatedQualifiedType() throws ParseException {
		BUTree<SNodeList> annotations;
		BUTree<SQualifiedType> ret;
		run();
		annotations = parseAnnotations();
		ret = parseQualifiedType(annotations);
		return ret;
	}

	public BUTree<SQualifiedName> parseQualifiedName() throws ParseException {
		BUTree<SNodeOption> qualifier = none();
		BUTree<SQualifiedName> ret = null;
		BUTree<SName> name;
		run();
		name = parseName();
		ret = dress(SQualifiedName.make(qualifier, name));
		while (backupLookahead(matchNext("."))) {
			lateRun();
			parse(".");
			qualifier = optionOf(ret);
			name = parseName();
			ret = dress(SQualifiedName.make(qualifier, name));
		}
		return ret;
	}

	public BUTree<SName> parseName() throws ParseException {
		BUTree<SName> name;
		if (backupLookahead(matchNext("<IDENTIFIER>"))) {
			run();
			parse("<IDENTIFIER>");
			name = dress(SName.make(getToken(0).image));
		} else {
			name = parseNodeVar();
		}
		return name;
	}

	public BUTree<? extends SExpr> parseExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		AssignOp op;
		BUTree<? extends SExpr> value;
		BUTree<SNodeList> params;
		if (backupLookahead(matchExpression1())) {
			run();
			ret = parseName();
			parse("->");
			ret = parseLambdaBody(singletonList(makeFormalParameter((BUTree<SName>) ret)), false);
		} else if (backupLookahead(matchExpression2())) {
			run();
			parse("(");
			parse(")");
			parse("->");
			ret = parseLambdaBody(emptyList(), true);
		} else if (backupLookahead(matchExpression3())) {
			run();
			parse("(");
			ret = parseName();
			parse(")");
			parse("->");
			ret = parseLambdaBody(singletonList(makeFormalParameter((BUTree<SName>) ret)), true);
		} else if (backupLookahead(matchExpression4())) {
			run();
			parse("(");
			params = parseInferredFormalParameterList();
			parse(")");
			parse("->");
			ret = parseLambdaBody(params, true);
		} else {
			ret = parseConditionalExpression();
			if (backupLookahead(matchNext(">>=", "%=", "<<=", "&=", "|=", "+=", ">>>=", "*=", "/=", "-=", "^=", "="))) {
				lateRun();
				op = parseAssignmentOperator();
				value = parseExpression();
				ret = dress(SAssignExpr.make(ret, op, value));
			}
		}
		return ret;
	}

	private boolean matchExpression1() {
		if (!matchName())
			return false;
		if (!match("->"))
			return false;
		return true;
	}

	private boolean matchExpression2() {
		if (!match("("))
			return false;
		if (!match(")"))
			return false;
		if (!match("->"))
			return false;
		return true;
	}

	private boolean matchExpression3() {
		if (!match("("))
			return false;
		if (!matchName())
			return false;
		if (!match(")"))
			return false;
		if (!match("->"))
			return false;
		return true;
	}

	private boolean matchExpression4() {
		if (!match("("))
			return false;
		if (!matchName())
			return false;
		if (!match(","))
			return false;
		return true;
	}

	public BUTree<SLambdaExpr> parseLambdaBody(BUTree<SNodeList> parameters, boolean parenthesis) throws ParseException {
		BUTree<SBlockStmt> block;
		BUTree<? extends SExpr> expr;
		BUTree<SLambdaExpr> ret;
		if (backupLookahead(matchNext("double", "float", "-", "<FLOAT_LITERAL>", "long", "byte", "<LONG_LITERAL>", "(", "false", "super", "<INTEGER_LITERAL>", "null", "void", "this", "short", "<", "<NODE_VARIABLE>", "<IDENTIFIER>", "new", "char", "++", "--", "<CHARACTER_LITERAL>", "<DOUBLE_LITERAL>", "<STRING_LITERAL>", "+", "true", "!", "~", "int", "boolean"))) {
			expr = parseExpression();
			ret = dress(SLambdaExpr.make(parameters, parenthesis, left(expr)));
		} else {
			block = parseBlock();
			ret = dress(SLambdaExpr.make(parameters, parenthesis, right(block)));
		}
		return ret;
	}

	public BUTree<SNodeList> parseInferredFormalParameterList() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<SFormalParameter> param;
		param = parseInferredFormalParameter();
		ret = append(ret, param);
		while (backupLookahead(matchNext(","))) {
			parse(",");
			param = parseInferredFormalParameter();
			ret = append(ret, param);
		}
		return ret;
	}

	public BUTree<SFormalParameter> parseInferredFormalParameter() throws ParseException {
		BUTree<SName> name;
		name = parseName();
		return makeFormalParameter(name);
	}

	public AssignOp parseAssignmentOperator() throws ParseException {
		AssignOp ret;
		if (backupLookahead(matchNext("="))) {
			parse("=");
			ret = AssignOp.Normal;
		} else if (backupLookahead(matchNext("*="))) {
			parse("*=");
			ret = AssignOp.Times;
		} else if (backupLookahead(matchNext("/="))) {
			parse("/=");
			ret = AssignOp.Divide;
		} else if (backupLookahead(matchNext("%="))) {
			parse("%=");
			ret = AssignOp.Remainder;
		} else if (backupLookahead(matchNext("+="))) {
			parse("+=");
			ret = AssignOp.Plus;
		} else if (backupLookahead(matchNext("-="))) {
			parse("-=");
			ret = AssignOp.Minus;
		} else if (backupLookahead(matchNext("<<="))) {
			parse("<<=");
			ret = AssignOp.LeftShift;
		} else if (backupLookahead(matchNext(">>="))) {
			parse(">>=");
			ret = AssignOp.RightSignedShift;
		} else if (backupLookahead(matchNext(">>>="))) {
			parse(">>>=");
			ret = AssignOp.RightUnsignedShift;
		} else if (backupLookahead(matchNext("&="))) {
			parse("&=");
			ret = AssignOp.And;
		} else if (backupLookahead(matchNext("^="))) {
			parse("^=");
			ret = AssignOp.XOr;
		} else {
			parse("|=");
			ret = AssignOp.Or;
		}
		return ret;
	}

	public BUTree<? extends SExpr> parseConditionalExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> left;
		BUTree<? extends SExpr> right;
		ret = parseConditionalOrExpression();
		if (backupLookahead(matchNext("?"))) {
			lateRun();
			parse("?");
			left = parseExpression();
			parse(":");
			right = parseConditionalExpression();
			ret = dress(SConditionalExpr.make(ret, left, right));
		}
		return ret;
	}

	public BUTree<? extends SExpr> parseConditionalOrExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		ret = parseConditionalAndExpression();
		while (backupLookahead(matchNext("||"))) {
			lateRun();
			parse("||");
			right = parseConditionalAndExpression();
			ret = dress(SBinaryExpr.make(ret, BinaryOp.Or, right));
		}
		return ret;
	}

	public BUTree<? extends SExpr> parseConditionalAndExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		ret = parseInclusiveOrExpression();
		while (backupLookahead(matchNext("&&"))) {
			lateRun();
			parse("&&");
			right = parseInclusiveOrExpression();
			ret = dress(SBinaryExpr.make(ret, BinaryOp.And, right));
		}
		return ret;
	}

	public BUTree<? extends SExpr> parseInclusiveOrExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		ret = parseExclusiveOrExpression();
		while (backupLookahead(matchNext("|"))) {
			lateRun();
			parse("|");
			right = parseExclusiveOrExpression();
			ret = dress(SBinaryExpr.make(ret, BinaryOp.BinOr, right));
		}
		return ret;
	}

	public BUTree<? extends SExpr> parseExclusiveOrExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		ret = parseAndExpression();
		while (backupLookahead(matchNext("^"))) {
			lateRun();
			parse("^");
			right = parseAndExpression();
			ret = dress(SBinaryExpr.make(ret, BinaryOp.XOr, right));
		}
		return ret;
	}

	public BUTree<? extends SExpr> parseAndExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		ret = parseEqualityExpression();
		while (backupLookahead(matchNext("&"))) {
			lateRun();
			parse("&");
			right = parseEqualityExpression();
			ret = dress(SBinaryExpr.make(ret, BinaryOp.BinAnd, right));
		}
		return ret;
	}

	public BUTree<? extends SExpr> parseEqualityExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		BinaryOp op;
		ret = parseInstanceOfExpression();
		while (backupLookahead(matchNext("==", "!="))) {
			lateRun();
			if (backupLookahead(matchNext("=="))) {
				parse("==");
				op = BinaryOp.Equal;
			} else {
				parse("!=");
				op = BinaryOp.NotEqual;
			}
			right = parseInstanceOfExpression();
			ret = dress(SBinaryExpr.make(ret, op, right));
		}
		return ret;
	}

	public BUTree<? extends SExpr> parseInstanceOfExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<SNodeList> annotations;
		BUTree<? extends SType> type;
		ret = parseRelationalExpression();
		if (backupLookahead(matchNext("instanceof"))) {
			lateRun();
			parse("instanceof");
			run();
			annotations = parseAnnotations();
			type = parseType(annotations);
			ret = dress(SInstanceOfExpr.make(ret, type));
		}
		return ret;
	}

	public BUTree<? extends SExpr> parseRelationalExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		BinaryOp op;
		ret = parseShiftExpression();
		while (backupLookahead(matchNext("<", "<=", ">=", ">"))) {
			lateRun();
			if (backupLookahead(matchNext("<"))) {
				parse("<");
				op = BinaryOp.Less;
			} else if (backupLookahead(matchNext(">"))) {
				parse(">");
				op = BinaryOp.Greater;
			} else if (backupLookahead(matchNext("<="))) {
				parse("<=");
				op = BinaryOp.LessOrEqual;
			} else {
				parse(">=");
				op = BinaryOp.GreaterOrEqual;
			}
			right = parseShiftExpression();
			ret = dress(SBinaryExpr.make(ret, op, right));
		}
		return ret;
	}

	public BUTree<? extends SExpr> parseShiftExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		BinaryOp op;
		ret = parseAdditiveExpression();
		while (backupLookahead(matchNext("<<", ">"))) {
			lateRun();
			if (backupLookahead(matchNext("<<"))) {
				parse("<<");
				op = BinaryOp.LeftShift;
			} else if (backupLookahead(matchNext(">"))) {
				parseRSIGNEDSHIFT();
				op = BinaryOp.RightSignedShift;
			} else {
				parseRUNSIGNEDSHIFT();
				op = BinaryOp.RightUnsignedShift;
			}
			right = parseAdditiveExpression();
			ret = dress(SBinaryExpr.make(ret, op, right));
		}
		return ret;
	}

	public BUTree<? extends SExpr> parseAdditiveExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		BinaryOp op;
		ret = parseMultiplicativeExpression();
		while (backupLookahead(matchNext("+", "-"))) {
			lateRun();
			if (backupLookahead(matchNext("+"))) {
				parse("+");
				op = BinaryOp.Plus;
			} else {
				parse("-");
				op = BinaryOp.Minus;
			}
			right = parseMultiplicativeExpression();
			ret = dress(SBinaryExpr.make(ret, op, right));
		}
		return ret;
	}

	public BUTree<? extends SExpr> parseMultiplicativeExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		BinaryOp op;
		ret = parseUnaryExpression();
		while (backupLookahead(matchNext("*", "%", "/"))) {
			lateRun();
			if (backupLookahead(matchNext("*"))) {
				parse("*");
				op = BinaryOp.Times;
			} else if (backupLookahead(matchNext("/"))) {
				parse("/");
				op = BinaryOp.Divide;
			} else {
				parse("%");
				op = BinaryOp.Remainder;
			}
			right = parseUnaryExpression();
			ret = dress(SBinaryExpr.make(ret, op, right));
		}
		return ret;
	}

	public BUTree<? extends SExpr> parseUnaryExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		UnaryOp op;
		if (backupLookahead(matchNext("++", "--"))) {
			ret = parsePrefixExpression();
		} else if (backupLookahead(matchNext("+", "-"))) {
			run();
			if (backupLookahead(matchNext("+"))) {
				parse("+");
				op = UnaryOp.Positive;
			} else {
				parse("-");
				op = UnaryOp.Negative;
			}
			ret = parseUnaryExpression();
			ret = dress(SUnaryExpr.make(op, ret));
		} else {
			ret = parseUnaryExpressionNotPlusMinus();
		}
		return ret;
	}

	public BUTree<? extends SExpr> parsePrefixExpression() throws ParseException {
		UnaryOp op;
		BUTree<? extends SExpr> ret;
		run();
		if (backupLookahead(matchNext("++"))) {
			parse("++");
			op = UnaryOp.PreIncrement;
		} else {
			parse("--");
			op = UnaryOp.PreDecrement;
		}
		ret = parseUnaryExpression();
		return dress(SUnaryExpr.make(op, ret));
	}

	public BUTree<? extends SExpr> parseUnaryExpressionNotPlusMinus() throws ParseException {
		BUTree<? extends SExpr> ret;
		UnaryOp op;
		if (backupLookahead(matchNext("!", "~"))) {
			run();
			if (backupLookahead(matchNext("~"))) {
				parse("~");
				op = UnaryOp.Inverse;
			} else {
				parse("!");
				op = UnaryOp.Not;
			}
			ret = parseUnaryExpression();
			ret = dress(SUnaryExpr.make(op, ret));
		} else if (backupLookahead(matchUnaryExpressionNotPlusMinus1())) {
			ret = parseCastExpression();
		} else {
			ret = parsePostfixExpression();
		}
		return ret;
	}

	private boolean matchUnaryExpressionNotPlusMinus1() {
		if (!matchCastExpression())
			return false;
		return true;
	}

	public BUTree<? extends SExpr> parsePostfixExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		UnaryOp op;
		ret = parsePrimaryExpression();
		if (backupLookahead(matchNext("++", "--"))) {
			lateRun();
			if (backupLookahead(matchNext("++"))) {
				parse("++");
				op = UnaryOp.PostIncrement;
			} else {
				parse("--");
				op = UnaryOp.PostDecrement;
			}
			ret = dress(SUnaryExpr.make(op, ret));
		}
		return ret;
	}

	public BUTree<? extends SExpr> parseCastExpression() throws ParseException {
		BUTree<SNodeList> annotations = null;
		BUTree<? extends SType> primitiveType;
		BUTree<? extends SType> type;
		BUTree<SNodeList> arrayDims;
		BUTree<? extends SExpr> ret;
		run();
		parse("(");
		run();
		annotations = parseAnnotations();
		if (backupLookahead(matchNext("short", "long", "byte", "double", "char", "float", "boolean", "int"))) {
			primitiveType = parsePrimitiveType(annotations);
			if (backupLookahead(matchNext(")"))) {
				parse(")");
				ret = parseUnaryExpression();
				ret = dress(SCastExpr.make(primitiveType, ret));
			} else {
				lateRun();
				arrayDims = parseArrayDimsMandatory();
				type = dress(SArrayType.make(primitiveType, arrayDims));
				type = parseReferenceCastTypeRest(type);
				parse(")");
				ret = parseUnaryExpressionNotPlusMinus();
				ret = dress(SCastExpr.make(type, ret));
			}
		} else {
			type = parseQualifiedType(annotations);
			if (backupLookahead(matchCastExpression1())) {
				lateRun();
				arrayDims = parseArrayDimsMandatory();
				type = dress(SArrayType.make(type, arrayDims));
			}
			type = parseReferenceCastTypeRest(type);
			parse(")");
			ret = parseUnaryExpressionNotPlusMinus();
			ret = dress(SCastExpr.make(type, ret));
		}
		return ret;
	}

	private boolean matchCastExpression1() {
		if (!matchAnnotations())
			return false;
		if (!match("["))
			return false;
		return true;
	}

	public BUTree<? extends SType> parseReferenceCastTypeRest(BUTree<? extends SType> type) throws ParseException {
		BUTree<SNodeList> types = emptyList();
		BUTree<SNodeList> annotations = null;
		if (backupLookahead(matchReferenceCastTypeRest1())) {
			types = append(types, type);
			lateRun();
			do {
				parse("&");
				run();
				annotations = parseAnnotations();
				type = parseReferenceType(annotations);
				types = append(types, type);
			} while (backupLookahead(matchNext("&")));
			type = dress(SIntersectionType.make(types));
		}
		return type;
	}

	private boolean matchReferenceCastTypeRest1() {
		if (!match("&"))
			return false;
		return true;
	}

	public BUTree<? extends SExpr> parseLiteral() throws ParseException {
		BUTree<? extends SExpr> ret;
		run();
		if (backupLookahead(matchNext("<INTEGER_LITERAL>"))) {
			parse("<INTEGER_LITERAL>");
			ret = SLiteralExpr.make(Integer.class, getToken(0).image);
		} else if (backupLookahead(matchNext("<LONG_LITERAL>"))) {
			parse("<LONG_LITERAL>");
			ret = SLiteralExpr.make(Long.class, getToken(0).image);
		} else if (backupLookahead(matchNext("<FLOAT_LITERAL>"))) {
			parse("<FLOAT_LITERAL>");
			ret = SLiteralExpr.make(Float.class, getToken(0).image);
		} else if (backupLookahead(matchNext("<DOUBLE_LITERAL>"))) {
			parse("<DOUBLE_LITERAL>");
			ret = SLiteralExpr.make(Double.class, getToken(0).image);
		} else if (backupLookahead(matchNext("<CHARACTER_LITERAL>"))) {
			parse("<CHARACTER_LITERAL>");
			ret = SLiteralExpr.make(Character.class, getToken(0).image);
		} else if (backupLookahead(matchNext("<STRING_LITERAL>"))) {
			parse("<STRING_LITERAL>");
			ret = SLiteralExpr.make(String.class, getToken(0).image);
		} else if (backupLookahead(matchNext("true"))) {
			parse("true");
			ret = SLiteralExpr.make(Boolean.class, getToken(0).image);
		} else if (backupLookahead(matchNext("false"))) {
			parse("false");
			ret = SLiteralExpr.make(Boolean.class, getToken(0).image);
		} else {
			parse("null");
			ret = SLiteralExpr.make(Void.class, getToken(0).image);
		}
		return dress(ret);
	}

	public BUTree<? extends SExpr> parsePrimaryExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		ret = parsePrimaryPrefix();
		while (backupLookahead(matchNext("[", "::", "."))) {
			lateRun();
			ret = parsePrimarySuffix(ret);
		}
		return ret;
	}

	public BUTree<? extends SExpr> parsePrimaryExpressionWithoutSuperSuffix() throws ParseException {
		BUTree<? extends SExpr> ret;
		ret = parsePrimaryPrefix();
		while (backupLookahead(matchPrimaryExpressionWithoutSuperSuffix1())) {
			lateRun();
			ret = parsePrimarySuffixWithoutSuper(ret);
		}
		return ret;
	}

	private boolean matchPrimaryExpressionWithoutSuperSuffix1() {
		if (!matchPrimarySuffixWithoutSuper())
			return false;
		return true;
	}

	public BUTree<? extends SExpr> parsePrimaryPrefix() throws ParseException {
		BUTree<? extends SExpr> ret = null;
		BUTree<SNodeList> typeArgs = null;
		BUTree<SNodeList> params;
		BUTree<? extends SType> type;
		if (backupLookahead(matchNext("<DOUBLE_LITERAL>", "<STRING_LITERAL>", "true", "<LONG_LITERAL>", "false", "<INTEGER_LITERAL>", "<CHARACTER_LITERAL>", "null", "<FLOAT_LITERAL>"))) {
			ret = parseLiteral();
		} else if (backupLookahead(matchNext("this"))) {
			run();
			parse("this");
			ret = dress(SThisExpr.make(none()));
		} else if (backupLookahead(matchNext("super"))) {
			run();
			parse("super");
			ret = dress(SSuperExpr.make(none()));
			if (backupLookahead(matchNext("."))) {
				lateRun();
				parse(".");
				if (backupLookahead(matchPrimaryPrefix1())) {
					ret = parseMethodInvocation(ret);
				} else {
					ret = parseFieldAccess(ret);
				}
			} else {
				lateRun();
				ret = parseMethodReferenceSuffix(ret);
			}
		} else if (backupLookahead(matchNext("new"))) {
			ret = parseAllocationExpression(null);
		} else if (backupLookahead(matchPrimaryPrefix2())) {
			run();
			type = parseResultType();
			parse(".");
			parse("class");
			ret = dress(SClassExpr.make(type));
		} else if (backupLookahead(matchPrimaryPrefix3())) {
			run();
			type = parseResultType();
			ret = STypeExpr.make(type);
			ret = parseMethodReferenceSuffix(ret);
		} else if (backupLookahead(matchPrimaryPrefix4())) {
			run();
			ret = parseMethodInvocation(null);
		} else if (backupLookahead(matchNext("<NODE_VARIABLE>", "<IDENTIFIER>"))) {
			ret = parseName();
			if (backupLookahead(matchNext("->"))) {
				lateRun();
				parse("->");
				ret = parseLambdaBody(singletonList(makeFormalParameter((BUTree<SName>) ret)), false);
			}
		} else {
			run();
			parse("(");
			if (backupLookahead(matchNext(")"))) {
				parse(")");
				parse("->");
				ret = parseLambdaBody(emptyList(), true);
			} else if (backupLookahead(matchPrimaryPrefix5())) {
				ret = parseName();
				parse(")");
				parse("->");
				ret = parseLambdaBody(singletonList(makeFormalParameter((BUTree<SName>) ret)), true);
			} else if (backupLookahead(matchPrimaryPrefix6())) {
				params = parseInferredFormalParameterList();
				parse(")");
				parse("->");
				ret = parseLambdaBody(params, true);
			} else if (backupLookahead(matchNext("<NODE_LIST_VARIABLE>", "abstract", "short", "strictfp", "<NODE_VARIABLE>", "transient", "<IDENTIFIER>", "private", "double", "char", "float", "public", "volatile", "long", "native", "byte", "synchronized", "final", "default", "protected", "static", "@", "int", "boolean"))) {
				params = parseFormalParameterList();
				parse(")");
				parse("->");
				ret = parseLambdaBody(params, true);
			} else {
				ret = parseExpression();
				parse(")");
				ret = dress(SParenthesizedExpr.make(ret));
			}
		}
		return ret;
	}

	private boolean matchPrimaryPrefix1() {
		if (matchNext("<")) {
			if (!matchTypeArguments())
				return false;
		}
		if (!matchName())
			return false;
		if (!match("("))
			return false;
		return true;
	}

	private boolean matchPrimaryPrefix2() {
		if (!matchResultType())
			return false;
		if (!match("."))
			return false;
		if (!match("class"))
			return false;
		return true;
	}

	private boolean matchPrimaryPrefix3() {
		if (!matchResultType())
			return false;
		if (!match("::"))
			return false;
		return true;
	}

	private boolean matchPrimaryPrefix4() {
		if (matchNext("<")) {
			if (!matchTypeArguments())
				return false;
		}
		if (!matchName())
			return false;
		if (!match("("))
			return false;
		return true;
	}

	private boolean matchPrimaryPrefix5() {
		if (!matchName())
			return false;
		if (!match(")"))
			return false;
		if (!match("->"))
			return false;
		return true;
	}

	private boolean matchPrimaryPrefix6() {
		if (!matchName())
			return false;
		if (!match(","))
			return false;
		return true;
	}

	public BUTree<? extends SExpr> parsePrimarySuffix(BUTree<? extends SExpr> scope) throws ParseException {
		BUTree<? extends SExpr> ret;
		if (backupLookahead(matchNext("[", "."))) {
			ret = parsePrimarySuffixWithoutSuper(scope);
		} else if (backupLookahead(matchNext("."))) {
			parse(".");
			parse("super");
			ret = dress(SSuperExpr.make(optionOf(scope)));
		} else {
			ret = parseMethodReferenceSuffix(scope);
		}
		return ret;
	}

	public BUTree<? extends SExpr> parsePrimarySuffixWithoutSuper(BUTree<? extends SExpr> scope) throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<SName> name;
		if (backupLookahead(matchNext("."))) {
			parse(".");
			if (backupLookahead(matchNext("this"))) {
				parse("this");
				ret = dress(SThisExpr.make(optionOf(scope)));
			} else if (backupLookahead(matchNext("new"))) {
				ret = parseAllocationExpression(scope);
			} else if (backupLookahead(matchPrimarySuffixWithoutSuper1())) {
				ret = parseMethodInvocation(scope);
			} else {
				ret = parseFieldAccess(scope);
			}
		} else {
			parse("[");
			ret = parseExpression();
			parse("]");
			ret = dress(SArrayAccessExpr.make(scope, ret));
		}
		return ret;
	}

	private boolean matchPrimarySuffixWithoutSuper1() {
		if (matchNext("<")) {
			if (!matchTypeArguments())
				return false;
		}
		if (!matchName())
			return false;
		if (!match("("))
			return false;
		return true;
	}

	public BUTree<? extends SExpr> parseFieldAccess(BUTree<? extends SExpr> scope) throws ParseException {
		BUTree<SName> name;
		name = parseName();
		return dress(SFieldAccessExpr.make(optionOf(scope), name));
	}

	public BUTree<? extends SExpr> parseMethodInvocation(BUTree<? extends SExpr> scope) throws ParseException {
		BUTree<SNodeList> typeArgs = null;
		BUTree<SName> name;
		BUTree<SNodeList> args = null;
		BUTree<? extends SExpr> ret;
		if (backupLookahead(matchNext("<"))) {
			typeArgs = parseTypeArguments();
		}
		name = parseName();
		args = parseArguments();
		return dress(SMethodInvocationExpr.make(optionOf(scope), ensureNotNull(typeArgs), name, args));
	}

	public BUTree<SNodeList> parseArguments() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<? extends SExpr> expr;
		parse("(");
		if (backupLookahead(matchNext("double", "float", "-", "<FLOAT_LITERAL>", "long", "byte", "<LONG_LITERAL>", "(", "false", "super", "<INTEGER_LITERAL>", "null", "void", "this", "<NODE_LIST_VARIABLE>", "short", "<", "<NODE_VARIABLE>", "<IDENTIFIER>", "new", "char", "++", "--", "<CHARACTER_LITERAL>", "<DOUBLE_LITERAL>", "<STRING_LITERAL>", "+", "true", "!", "~", "int", "boolean"))) {
			if (backupLookahead(matchNext("double", "float", "-", "<FLOAT_LITERAL>", "long", "byte", "<LONG_LITERAL>", "(", "false", "super", "<INTEGER_LITERAL>", "null", "void", "this", "short", "<", "<NODE_VARIABLE>", "<IDENTIFIER>", "new", "char", "++", "--", "<CHARACTER_LITERAL>", "<DOUBLE_LITERAL>", "<STRING_LITERAL>", "+", "true", "!", "~", "int", "boolean"))) {
				expr = parseExpression();
				ret = append(ret, expr);
				while (backupLookahead(matchNext(","))) {
					parse(",");
					expr = parseExpression();
					ret = append(ret, expr);
				}
			} else {
				ret = parseNodeListVar();
			}
		}
		parse(")");
		return ret;
	}

	public BUTree<? extends SExpr> parseMethodReferenceSuffix(BUTree<? extends SExpr> scope) throws ParseException {
		BUTree<SNodeList> typeArgs = null;
		BUTree<SName> name;
		BUTree<? extends SExpr> ret;
		parse("::");
		if (backupLookahead(matchNext("<"))) {
			typeArgs = parseTypeArguments();
		}
		if (backupLookahead(matchNext("<NODE_VARIABLE>", "<IDENTIFIER>"))) {
			name = parseName();
		} else {
			parse("new");
			name = SName.make("new");
		}
		ret = dress(SMethodReferenceExpr.make(scope, ensureNotNull(typeArgs), name));
		return ret;
	}

	public BUTree<? extends SExpr> parseAllocationExpression(BUTree<? extends SExpr> scope) throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SType> type;
		BUTree<SNodeList> typeArgs = null;
		BUTree<SNodeList> anonymousBody = null;
		BUTree<SNodeList> args;
		BUTree<SNodeList> annotations = null;
		if (scope == null) run();

		parse("new");
		if (backupLookahead(matchNext("<"))) {
			typeArgs = parseTypeArguments();
		}
		run();
		annotations = parseAnnotations();
		if (backupLookahead(matchNext("short", "long", "byte", "double", "char", "float", "boolean", "int"))) {
			type = parsePrimitiveType(annotations);
			ret = parseArrayCreationExpr(type);
		} else {
			type = parseQualifiedType(annotations);
			if (backupLookahead(matchNext("[", "@"))) {
				ret = parseArrayCreationExpr(type);
			} else {
				args = parseArguments();
				if (backupLookahead(matchAllocationExpression1())) {
					anonymousBody = parseClassOrInterfaceBody(TypeKind.Class);
				}
				ret = dress(SObjectCreationExpr.make(optionOf(scope), ensureNotNull(typeArgs), (BUTree<SQualifiedType>) type, args, optionOf(anonymousBody)));
			}
		}
		return ret;
	}

	private boolean matchAllocationExpression1() {
		if (!match("{"))
			return false;
		return true;
	}

	public BUTree<? extends SExpr> parseArrayCreationExpr(BUTree<? extends SType> componentType) throws ParseException {
		BUTree<? extends SExpr> expr;
		BUTree<SNodeList> arrayDimExprs = emptyList();
		BUTree<SNodeList> arrayDims = emptyList();
		BUTree<SNodeList> annotations = null;
		BUTree<SArrayInitializerExpr> initializer;
		if (backupLookahead(matchArrayCreationExpr1())) {
			arrayDimExprs = parseArrayDimExprsMandatory();
			arrayDims = parseArrayDims();
			return dress(SArrayCreationExpr.make(componentType, arrayDimExprs, arrayDims, none()));
		} else {
			arrayDims = parseArrayDimsMandatory();
			initializer = parseArrayInitializer();
			return dress(SArrayCreationExpr.make(componentType, arrayDimExprs, arrayDims, optionOf(initializer)));
		}
	}

	private boolean matchArrayCreationExpr1() {
		if (!matchAnnotations())
			return false;
		if (!match("["))
			return false;
		if (!matchExpression())
			return false;
		if (!match("]"))
			return false;
		return true;
	}

	public BUTree<SNodeList> parseArrayDimExprsMandatory() throws ParseException {
		BUTree<SNodeList> arrayDimExprs = emptyList();
		BUTree<SNodeList> annotations;
		BUTree<? extends SExpr> expr;
		do {
			run();
			annotations = parseAnnotations();
			parse("[");
			expr = parseExpression();
			parse("]");
			arrayDimExprs = append(arrayDimExprs, dress(SArrayDimExpr.make(annotations, expr)));
		} while (backupLookahead(matchArrayDimExprsMandatory1()));
		return arrayDimExprs;
	}

	private boolean matchArrayDimExprsMandatory1() {
		if (!matchAnnotations())
			return false;
		if (!match("["))
			return false;
		if (!matchExpression())
			return false;
		if (!match("]"))
			return false;
		return true;
	}

	public BUTree<SNodeList> parseArrayDimsMandatory() throws ParseException {
		BUTree<SNodeList> arrayDims = emptyList();
		BUTree<SNodeList> annotations;
		do {
			run();
			annotations = parseAnnotations();
			parse("[");
			parse("]");
			arrayDims = append(arrayDims, dress(SArrayDim.make(annotations)));
		} while (backupLookahead(matchArrayDimsMandatory1()));
		return arrayDims;
	}

	private boolean matchArrayDimsMandatory1() {
		if (!matchAnnotations())
			return false;
		if (!match("["))
			return false;
		if (!match("]"))
			return false;
		return true;
	}

	public BUTree<? extends SStmt> parseStatement() throws ParseException {
		BUTree<? extends SStmt> ret;
		if (backupLookahead(matchNext("<NODE_VARIABLE>", "<IDENTIFIER>"))) {
			ret = parseLabeledStatement();
		} else if (backupLookahead(matchNext("assert"))) {
			ret = parseAssertStatement();
		} else if (backupLookahead(matchNext("{"))) {
			ret = parseBlock();
		} else if (backupLookahead(matchNext(";"))) {
			ret = parseEmptyStatement();
		} else if (backupLookahead(matchNext("double", "float", "<FLOAT_LITERAL>", "long", "byte", "<LONG_LITERAL>", "(", "false", "super", "<INTEGER_LITERAL>", "null", "void", "this", "short", "<", "<NODE_VARIABLE>", "<IDENTIFIER>", "new", "char", "--", "++", "<CHARACTER_LITERAL>", "<DOUBLE_LITERAL>", "<STRING_LITERAL>", "true", "int", "boolean"))) {
			ret = parseStatementExpression();
		} else if (backupLookahead(matchNext("switch"))) {
			ret = parseSwitchStatement();
		} else if (backupLookahead(matchNext("if"))) {
			ret = parseIfStatement();
		} else if (backupLookahead(matchNext("while"))) {
			ret = parseWhileStatement();
		} else if (backupLookahead(matchNext("do"))) {
			ret = parseDoStatement();
		} else if (backupLookahead(matchNext("for"))) {
			ret = parseForStatement();
		} else if (backupLookahead(matchNext("break"))) {
			ret = parseBreakStatement();
		} else if (backupLookahead(matchNext("continue"))) {
			ret = parseContinueStatement();
		} else if (backupLookahead(matchNext("return"))) {
			ret = parseReturnStatement();
		} else if (backupLookahead(matchNext("throw"))) {
			ret = parseThrowStatement();
		} else if (backupLookahead(matchNext("synchronized"))) {
			ret = parseSynchronizedStatement();
		} else {
			ret = parseTryStatement();
		}
		return ret;
	}

	public BUTree<SAssertStmt> parseAssertStatement() throws ParseException {
		BUTree<? extends SExpr> check;
		BUTree<? extends SExpr> msg = null;
		run();
		parse("assert");
		check = parseExpression();
		if (backupLookahead(matchNext(":"))) {
			parse(":");
			msg = parseExpression();
		}
		parse(";");
		return dress(SAssertStmt.make(check, optionOf(msg)));
	}

	public BUTree<SLabeledStmt> parseLabeledStatement() throws ParseException {
		BUTree<SName> label;
		BUTree<? extends SStmt> stmt;
		run();
		label = parseName();
		parse(":");
		stmt = parseStatement();
		return dress(SLabeledStmt.make(label, stmt));
	}

	public BUTree<SBlockStmt> parseBlock() throws ParseException {
		BUTree<SNodeList> stmts;
		run();
		parse("{");
		stmts = parseStatements();
		parse("}");
		return dress(SBlockStmt.make(ensureNotNull(stmts)));
	}

	public BUTree<? extends SStmt> parseBlockStatement() throws ParseException {
		BUTree<? extends SStmt> ret;
		BUTree<? extends SExpr> expr;
		BUTree<? extends STypeDecl> typeDecl;
		BUTree<SNodeList> modifiers;
		if (backupLookahead(matchBlockStatement1())) {
			run();
			run();
			modifiers = parseModifiersNoDefault();
			typeDecl = parseClassOrInterfaceDecl(modifiers);
			ret = dress(STypeDeclarationStmt.make(typeDecl));
		} else if (backupLookahead(matchBlockStatement2())) {
			run();
			expr = parseVariableDeclExpression();
			parse(";");
			ret = dress(SExpressionStmt.make(expr));
		} else {
			ret = parseStatement();
		}
		return ret;
	}

	private boolean matchBlockStatement1() {
		if (!matchModifiersNoDefault())
			return false;
		if (matchNext("class")) {
			if (!match("class"))
				return false;
		} else if (matchNext("interface")) {
			if (!match("interface"))
				return false;
		}
		return true;
	}

	private boolean matchBlockStatement2() {
		if (!matchVariableDeclExpression())
			return false;
		return true;
	}

	public BUTree<SVariableDeclarationExpr> parseVariableDeclExpression() throws ParseException {
		BUTree<SNodeList> modifiers;
		BUTree<SLocalVariableDecl> variableDecl;
		run();
		run();
		modifiers = parseModifiersNoDefault();
		variableDecl = parseVariableDecl(modifiers);
		return dress(SVariableDeclarationExpr.make(variableDecl));
	}

	public BUTree<SEmptyStmt> parseEmptyStatement() throws ParseException {
		run();
		parse(";");
		return dress(SEmptyStmt.make());
	}

	public BUTree<SExpressionStmt> parseStatementExpression() throws ParseException {
		BUTree<? extends SExpr> expr;
		AssignOp op;
		BUTree<? extends SExpr> value;
		run();
		if (backupLookahead(matchNext("++", "--"))) {
			expr = parsePrefixExpression();
		} else {
			expr = parsePrimaryExpression();
			if (backupLookahead(matchNext(">>=", "<<=", "|=", "++", "--", "^=", "%=", "&=", "+=", ">>>=", "*=", "/=", "-=", "="))) {
				if (backupLookahead(matchNext("++"))) {
					lateRun();
					parse("++");
					expr = dress(SUnaryExpr.make(UnaryOp.PostIncrement, expr));
				} else if (backupLookahead(matchNext("--"))) {
					lateRun();
					parse("--");
					expr = dress(SUnaryExpr.make(UnaryOp.PostDecrement, expr));
				} else {
					lateRun();
					op = parseAssignmentOperator();
					value = parseExpression();
					expr = dress(SAssignExpr.make(expr, op, value));
				}
			}
		}
		parse(";");
		return dress(SExpressionStmt.make(expr));
	}

	public BUTree<SSwitchStmt> parseSwitchStatement() throws ParseException {
		BUTree<? extends SExpr> selector;
		BUTree<SSwitchCase> entry;
		BUTree<SNodeList> entries = emptyList();
		run();
		parse("switch");
		parse("(");
		selector = parseExpression();
		parse(")");
		parse("{");
		while (backupLookahead(matchNext("case", "default"))) {
			entry = parseSwitchEntry();
			entries = append(entries, entry);
		}
		parse("}");
		return dress(SSwitchStmt.make(selector, entries));
	}

	public BUTree<SSwitchCase> parseSwitchEntry() throws ParseException {
		BUTree<? extends SExpr> label = null;
		BUTree<SNodeList> stmts;
		run();
		if (backupLookahead(matchNext("case"))) {
			parse("case");
			label = parseExpression();
		} else {
			parse("default");
		}
		parse(":");
		stmts = parseStatements();
		return dress(SSwitchCase.make(optionOf(label), ensureNotNull(stmts)));
	}

	public BUTree<SIfStmt> parseIfStatement() throws ParseException {
		BUTree<? extends SExpr> condition;
		BUTree<? extends SStmt> thenStmt;
		BUTree<? extends SStmt> elseStmt = null;
		run();
		parse("if");
		parse("(");
		condition = parseExpression();
		parse(")");
		thenStmt = parseStatement();
		if (backupLookahead(matchNext("else"))) {
			parse("else");
			elseStmt = parseStatement();
		}
		return dress(SIfStmt.make(condition, thenStmt, optionOf(elseStmt)));
	}

	public BUTree<SWhileStmt> parseWhileStatement() throws ParseException {
		BUTree<? extends SExpr> condition;
		BUTree<? extends SStmt> body;
		run();
		parse("while");
		parse("(");
		condition = parseExpression();
		parse(")");
		body = parseStatement();
		return dress(SWhileStmt.make(condition, body));
	}

	public BUTree<SDoStmt> parseDoStatement() throws ParseException {
		BUTree<? extends SExpr> condition;
		BUTree<? extends SStmt> body;
		run();
		parse("do");
		body = parseStatement();
		parse("while");
		parse("(");
		condition = parseExpression();
		parse(")");
		parse(";");
		return dress(SDoStmt.make(body, condition));
	}

	public BUTree<? extends SStmt> parseForStatement() throws ParseException {
		BUTree<SVariableDeclarationExpr> varExpr = null;
		BUTree<? extends SExpr> expr = null;
		BUTree<SNodeList> init = null;
		BUTree<SNodeList> update = null;
		BUTree<? extends SStmt> body;
		run();
		parse("for");
		parse("(");
		if (backupLookahead(matchForStatement1())) {
			varExpr = parseVariableDeclExpression();
			parse(":");
			expr = parseExpression();
		} else {
			if (backupLookahead(matchNext("abstract", "strictfp", "transient", "private", "double", "float", "-", "<FLOAT_LITERAL>", "long", "byte", "<LONG_LITERAL>", "(", "false", "super", "final", "<INTEGER_LITERAL>", "static", "null", "void", "this", "short", "<", "<NODE_VARIABLE>", "<IDENTIFIER>", "new", "char", "++", "--", "<CHARACTER_LITERAL>", "public", "<DOUBLE_LITERAL>", "volatile", "<STRING_LITERAL>", "native", "+", "true", "synchronized", "!", "protected", "~", "@", "int", "boolean"))) {
				init = parseForInit();
			}
			parse(";");
			if (backupLookahead(matchNext("double", "float", "-", "<FLOAT_LITERAL>", "long", "byte", "<LONG_LITERAL>", "(", "false", "super", "<INTEGER_LITERAL>", "null", "void", "this", "short", "<", "<NODE_VARIABLE>", "<IDENTIFIER>", "new", "char", "++", "--", "<CHARACTER_LITERAL>", "<DOUBLE_LITERAL>", "<STRING_LITERAL>", "+", "true", "!", "~", "int", "boolean"))) {
				expr = parseExpression();
			}
			parse(";");
			if (backupLookahead(matchNext("double", "float", "-", "<FLOAT_LITERAL>", "long", "byte", "<LONG_LITERAL>", "(", "false", "super", "<INTEGER_LITERAL>", "null", "void", "this", "short", "<", "<NODE_VARIABLE>", "<IDENTIFIER>", "new", "char", "--", "++", "<CHARACTER_LITERAL>", "<DOUBLE_LITERAL>", "<STRING_LITERAL>", "+", "true", "!", "~", "int", "boolean"))) {
				update = parseForUpdate();
			}
		}
		parse(")");
		body = parseStatement();
		if (varExpr != null)
	return dress(SForeachStmt.make(varExpr, expr, body));
else
	return dress(SForStmt.make(init, expr, update, body));

	}

	private boolean matchForStatement1() {
		if (!matchVariableDeclExpression())
			return false;
		if (!match(":"))
			return false;
		return true;
	}

	public BUTree<SNodeList> parseForInit() throws ParseException {
		BUTree<SNodeList> ret;
		BUTree<? extends SExpr> expr;
		if (backupLookahead(matchForInit1())) {
			expr = parseVariableDeclExpression();
			ret = emptyList();
			ret = append(ret, expr);
		} else {
			ret = parseExpressionList();
		}
		return ret;
	}

	private boolean matchForInit1() {
		if (!matchModifiers())
			return false;
		if (!matchType())
			return false;
		if (!matchName())
			return false;
		return true;
	}

	public BUTree<SNodeList> parseExpressionList() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<? extends SExpr> expr;
		expr = parseExpression();
		ret = append(ret, expr);
		while (backupLookahead(matchNext(","))) {
			parse(",");
			expr = parseExpression();
			ret = append(ret, expr);
		}
		return ret;
	}

	public BUTree<SNodeList> parseForUpdate() throws ParseException {
		BUTree<SNodeList> ret;
		ret = parseExpressionList();
		return ret;
	}

	public BUTree<SBreakStmt> parseBreakStatement() throws ParseException {
		BUTree<SName> id = null;
		run();
		parse("break");
		if (backupLookahead(matchNext("<NODE_VARIABLE>", "<IDENTIFIER>"))) {
			id = parseName();
		}
		parse(";");
		return dress(SBreakStmt.make(optionOf(id)));
	}

	public BUTree<SContinueStmt> parseContinueStatement() throws ParseException {
		BUTree<SName> id = null;
		run();
		parse("continue");
		if (backupLookahead(matchNext("<NODE_VARIABLE>", "<IDENTIFIER>"))) {
			id = parseName();
		}
		parse(";");
		return dress(SContinueStmt.make(optionOf(id)));
	}

	public BUTree<SReturnStmt> parseReturnStatement() throws ParseException {
		BUTree<? extends SExpr> expr = null;
		run();
		parse("return");
		if (backupLookahead(matchNext("double", "float", "-", "<FLOAT_LITERAL>", "long", "byte", "<LONG_LITERAL>", "(", "false", "super", "<INTEGER_LITERAL>", "null", "void", "this", "short", "<", "<NODE_VARIABLE>", "<IDENTIFIER>", "new", "char", "++", "--", "<CHARACTER_LITERAL>", "<DOUBLE_LITERAL>", "<STRING_LITERAL>", "+", "true", "!", "~", "int", "boolean"))) {
			expr = parseExpression();
		}
		parse(";");
		return dress(SReturnStmt.make(optionOf(expr)));
	}

	public BUTree<SThrowStmt> parseThrowStatement() throws ParseException {
		BUTree<? extends SExpr> expr;
		run();
		parse("throw");
		expr = parseExpression();
		parse(";");
		return dress(SThrowStmt.make(expr));
	}

	public BUTree<SSynchronizedStmt> parseSynchronizedStatement() throws ParseException {
		BUTree<? extends SExpr> expr;
		BUTree<SBlockStmt> block;
		run();
		parse("synchronized");
		parse("(");
		expr = parseExpression();
		parse(")");
		block = parseBlock();
		return dress(SSynchronizedStmt.make(expr, block));
	}

	public BUTree<STryStmt> parseTryStatement() throws ParseException {
		BUTree<SNodeList> resources = null;
		ByRef<Boolean> trailingSemiColon = new ByRef<Boolean>(false);
		BUTree<SBlockStmt> tryBlock;
		BUTree<SBlockStmt> finallyBlock = null;
		BUTree<SNodeList> catchClauses = null;
		run();
		parse("try");
		if (backupLookahead(matchNext("("))) {
			resources = parseResourceSpecification(trailingSemiColon);
			tryBlock = parseBlock();
			if (backupLookahead(matchNext("catch"))) {
				catchClauses = parseCatchClauses();
			}
			if (backupLookahead(matchNext("finally"))) {
				parse("finally");
				finallyBlock = parseBlock();
			}
		} else {
			tryBlock = parseBlock();
			if (backupLookahead(matchNext("catch"))) {
				catchClauses = parseCatchClauses();
				if (backupLookahead(matchNext("finally"))) {
					parse("finally");
					finallyBlock = parseBlock();
				}
			} else {
				parse("finally");
				finallyBlock = parseBlock();
			}
		}
		return dress(STryStmt.make(ensureNotNull(resources), trailingSemiColon.value, tryBlock, ensureNotNull(catchClauses), optionOf(finallyBlock)));
	}

	public BUTree<SNodeList> parseCatchClauses() throws ParseException {
		BUTree<SNodeList> catchClauses = emptyList();
		BUTree<SCatchClause> catchClause;
		do {
			catchClause = parseCatchClause();
			catchClauses = append(catchClauses, catchClause);
		} while (backupLookahead(matchNext("catch")));
		return catchClauses;
	}

	public BUTree<SCatchClause> parseCatchClause() throws ParseException {
		BUTree<SFormalParameter> param;
		BUTree<SBlockStmt> catchBlock;
		run();
		parse("catch");
		parse("(");
		param = parseCatchFormalParameter();
		parse(")");
		catchBlock = parseBlock();
		return dress(SCatchClause.make(param, catchBlock));
	}

	public BUTree<SFormalParameter> parseCatchFormalParameter() throws ParseException {
		BUTree<SNodeList> modifiers;
		BUTree<? extends SType> exceptType;
		BUTree<SNodeList> exceptTypes = emptyList();
		BUTree<SVariableDeclaratorId> exceptId;
		run();
		modifiers = parseModifiers();
		exceptType = parseQualifiedType(null);
		exceptTypes = append(exceptTypes, exceptType);
		if (backupLookahead(matchCatchFormalParameter1())) {
			lateRun();
			do {
				parse("|");
				exceptType = parseAnnotatedQualifiedType();
				exceptTypes = append(exceptTypes, exceptType);
			} while (backupLookahead(matchNext("|")));
			exceptType = dress(SUnionType.make(exceptTypes));
		}
		exceptId = parseVariableDeclaratorId();
		return dress(SFormalParameter.make(modifiers, exceptType, false, exceptId));
	}

	private boolean matchCatchFormalParameter1() {
		if (!match("|"))
			return false;
		return true;
	}

	public BUTree<SNodeList> parseResourceSpecification(ByRef<Boolean> trailingSemiColon) throws ParseException {
		BUTree<SNodeList> vars = emptyList();
		BUTree<SVariableDeclarationExpr> var;
		parse("(");
		var = parseVariableDeclExpression();
		vars = append(vars, var);
		while (backupLookahead(matchNext(";"))) {
			parse(";");
			var = parseVariableDeclExpression();
			vars = append(vars, var);
		}
		if (backupLookahead(matchNext(";"))) {
			parse(";");
			trailingSemiColon.value = true;
		}
		parse(")");
		return vars;
	}

	public void parseRUNSIGNEDSHIFT() throws ParseException {
		parse(">");
		parse(">");
		parse(">");
		popNewWhitespaces();
	}

	public void parseRSIGNEDSHIFT() throws ParseException {
		parse(">");
		parse(">");
		popNewWhitespaces();
	}

	public BUTree<SNodeList> parseAnnotations() throws ParseException {
		BUTree<SNodeList> annotations = emptyList();
		BUTree<? extends SAnnotationExpr> annotation;
		while (backupLookahead(matchNext("@"))) {
			annotation = parseAnnotation();
			annotations = append(annotations, annotation);
		}
		return annotations;
	}

	public BUTree<? extends SAnnotationExpr> parseAnnotation() throws ParseException {
		BUTree<? extends SAnnotationExpr> ret;
		if (backupLookahead(matchAnnotation1())) {
			ret = parseNormalAnnotation();
		} else if (backupLookahead(matchAnnotation2())) {
			ret = parseSingleMemberAnnotation();
		} else {
			ret = parseMarkerAnnotation();
		}
		return ret;
	}

	private boolean matchAnnotation1() {
		if (!match("@"))
			return false;
		if (!matchQualifiedName())
			return false;
		if (!match("("))
			return false;
		if (matchNext("<NODE_VARIABLE>", "<IDENTIFIER>")) {
			if (!matchName())
				return false;
			if (!match("="))
				return false;
		} else if (matchNext(")")) {
			if (!match(")"))
				return false;
		}
		return true;
	}

	private boolean matchAnnotation2() {
		if (!match("@"))
			return false;
		if (!matchQualifiedName())
			return false;
		if (!match("("))
			return false;
		return true;
	}

	private boolean matchAnnotation3() {
		if (!match("@"))
			return false;
		if (!matchQualifiedName())
			return false;
		return true;
	}

	public BUTree<SNormalAnnotationExpr> parseNormalAnnotation() throws ParseException {
		BUTree<SQualifiedName> name;
		BUTree<SNodeList> pairs = null;
		run();
		parse("@");
		name = parseQualifiedName();
		parse("(");
		if (backupLookahead(matchNext("<NODE_VARIABLE>", "<IDENTIFIER>"))) {
			pairs = parseMemberValuePairs();
		}
		parse(")");
		return dress(SNormalAnnotationExpr.make(name, ensureNotNull(pairs)));
	}

	public BUTree<SMarkerAnnotationExpr> parseMarkerAnnotation() throws ParseException {
		BUTree<SQualifiedName> name;
		run();
		parse("@");
		name = parseQualifiedName();
		return dress(SMarkerAnnotationExpr.make(name));
	}

	public BUTree<SSingleMemberAnnotationExpr> parseSingleMemberAnnotation() throws ParseException {
		BUTree<SQualifiedName> name;
		BUTree<? extends SExpr> memberVal;
		run();
		parse("@");
		name = parseQualifiedName();
		parse("(");
		memberVal = parseMemberValue();
		parse(")");
		return dress(SSingleMemberAnnotationExpr.make(name, memberVal));
	}

	public BUTree<SNodeList> parseMemberValuePairs() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<SMemberValuePair> pair;
		pair = parseMemberValuePair();
		ret = append(ret, pair);
		while (backupLookahead(matchNext(","))) {
			parse(",");
			pair = parseMemberValuePair();
			ret = append(ret, pair);
		}
		return ret;
	}

	public BUTree<SMemberValuePair> parseMemberValuePair() throws ParseException {
		BUTree<SName> name;
		BUTree<? extends SExpr> value;
		run();
		name = parseName();
		parse("=");
		value = parseMemberValue();
		return dress(SMemberValuePair.make(name, value));
	}

	public BUTree<? extends SExpr> parseMemberValue() throws ParseException {
		BUTree<? extends SExpr> ret;
		if (backupLookahead(matchNext("@"))) {
			ret = parseAnnotation();
		} else if (backupLookahead(matchNext("{"))) {
			ret = parseMemberValueArrayInitializer();
		} else {
			ret = parseConditionalExpression();
		}
		return ret;
	}

	public BUTree<? extends SExpr> parseMemberValueArrayInitializer() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<? extends SExpr> member;
		boolean trailingComma = false;
		run();
		parse("{");
		if (backupLookahead(matchNext("{", "double", "float", "-", "<FLOAT_LITERAL>", "long", "byte", "<LONG_LITERAL>", "(", "false", "super", "<INTEGER_LITERAL>", "null", "void", "this", "short", "<", "<NODE_VARIABLE>", "<IDENTIFIER>", "new", "char", "--", "++", "<CHARACTER_LITERAL>", "<DOUBLE_LITERAL>", "<STRING_LITERAL>", "+", "true", "!", "~", "@", "int", "boolean"))) {
			member = parseMemberValue();
			ret = append(ret, member);
			while (backupLookahead(matchNext(","))) {
				parse(",");
				member = parseMemberValue();
				ret = append(ret, member);
			}
		}
		if (backupLookahead(matchNext(","))) {
			parse(",");
			trailingComma = true;
		}
		parse("}");
		return dress(SArrayInitializerExpr.make(ret, trailingComma));
	}

	private boolean matchTypeArguments() {
		if (!match("<"))
			return false;
		if (matchNext("<NODE_LIST_VARIABLE>", "short", "<NODE_VARIABLE>", "<IDENTIFIER>", "double", "char", "float", "long", "byte", "@", "int", "boolean", "?")) {
			if (!matchTypeArgumentList())
				return false;
		}
		if (!match(">"))
			return false;
		return true;
	}

	private boolean matchQualifiedName() {
		if (!matchName())
			return false;
		while (matchNext(".")) {
			if (!match("."))
				return false;
			if (!matchName())
				return false;
		}
		return true;
	}

	private boolean matchPrimaryExpressionWithoutSuperSuffix() {
		if (!matchPrimaryPrefix())
			return false;
		while (matchPrimaryExpressionWithoutSuperSuffix1()) {
			if (!matchPrimarySuffixWithoutSuper())
				return false;
		}
		return true;
	}

	private boolean matchVariableDeclExpression() {
		if (!matchModifiersNoDefault())
			return false;
		if (!matchVariableDecl())
			return false;
		return true;
	}

	private boolean matchResultType() {
		if (matchNext("void")) {
			if (!match("void"))
				return false;
		} else if (matchNext("short", "long", "<NODE_VARIABLE>", "byte", "<IDENTIFIER>", "double", "char", "float", "boolean", "int")) {
			if (!matchType())
				return false;
		}
		return true;
	}

	private boolean matchName() {
		if (matchNext("<IDENTIFIER>")) {
			if (!match("<IDENTIFIER>"))
				return false;
		} else if (matchNext("<NODE_VARIABLE>")) {
			if (!matchNodeVar())
				return false;
		}
		return true;
	}

	private boolean matchType() {
		if (matchNext("short", "long", "byte", "double", "char", "float", "boolean", "int")) {
			if (!matchPrimitiveType())
				return false;
			if (matchType1()) {
				if (!matchArrayDimsMandatory())
					return false;
			}
		} else if (matchNext("<NODE_VARIABLE>", "<IDENTIFIER>")) {
			if (!matchQualifiedType())
				return false;
			if (matchType2()) {
				if (!matchArrayDimsMandatory())
					return false;
			}
		}
		return true;
	}

	private boolean matchPrimarySuffixWithoutSuper() {
		if (matchNext(".")) {
			if (!match("."))
				return false;
			if (matchNext("this")) {
				if (!match("this"))
					return false;
			} else if (matchNext("new")) {
				if (!matchAllocationExpression())
					return false;
			} else if (matchPrimarySuffixWithoutSuper1()) {
				if (!matchMethodInvocation())
					return false;
			} else if (matchNext("<NODE_VARIABLE>", "<IDENTIFIER>")) {
				if (!matchFieldAccess())
					return false;
			}
		} else if (matchNext("[")) {
			if (!match("["))
				return false;
			if (!matchExpression())
				return false;
			if (!match("]"))
				return false;
		}
		return true;
	}

	private boolean matchAnnotations() {
		while (matchNext("@")) {
			if (!matchAnnotation())
				return false;
		}
		return true;
	}

	private boolean matchTypeParameters() {
		if (!match("<"))
			return false;
		if (matchNext("<NODE_VARIABLE>", "<IDENTIFIER>", "@")) {
			if (!matchTypeParameter())
				return false;
			while (matchNext(",")) {
				if (!match(","))
					return false;
				if (!matchTypeParameter())
					return false;
			}
		} else if (matchNext("<NODE_LIST_VARIABLE>")) {
			if (!matchNodeListVar())
				return false;
		}
		if (!match(">"))
			return false;
		return true;
	}

	private boolean matchExplicitConstructorInvocation() {
		if (matchExplicitConstructorInvocation1()) {
			if (matchNext("<")) {
				if (!matchTypeArguments())
					return false;
			}
			if (!match("this"))
				return false;
			if (!matchArguments())
				return false;
			if (!match(";"))
				return false;
		} else if (matchNext("double", "float", "<FLOAT_LITERAL>", "long", "byte", "<LONG_LITERAL>", "(", "false", "super", "<INTEGER_LITERAL>", "null", "void", "this", "short", "<", "<NODE_VARIABLE>", "<IDENTIFIER>", "new", "char", "<CHARACTER_LITERAL>", "<DOUBLE_LITERAL>", "<STRING_LITERAL>", "true", "int", "boolean")) {
			if (matchExplicitConstructorInvocation2()) {
				if (!matchPrimaryExpressionWithoutSuperSuffix())
					return false;
				if (!match("."))
					return false;
			}
			if (matchNext("<")) {
				if (!matchTypeArguments())
					return false;
			}
			if (!match("super"))
				return false;
			if (!matchArguments())
				return false;
			if (!match(";"))
				return false;
		}
		return true;
	}

	private boolean matchExpression() {
		if (matchExpression1()) {
			if (!matchName())
				return false;
			if (!match("->"))
				return false;
			if (!matchLambdaBody())
				return false;
		} else if (matchExpression2()) {
			if (!match("("))
				return false;
			if (!match(")"))
				return false;
			if (!match("->"))
				return false;
			if (!matchLambdaBody())
				return false;
		} else if (matchExpression3()) {
			if (!match("("))
				return false;
			if (!matchName())
				return false;
			if (!match(")"))
				return false;
			if (!match("->"))
				return false;
			if (!matchLambdaBody())
				return false;
		} else if (matchExpression4()) {
			if (!match("("))
				return false;
			if (!matchInferredFormalParameterList())
				return false;
			if (!match(")"))
				return false;
			if (!match("->"))
				return false;
			if (!matchLambdaBody())
				return false;
		} else if (matchNext("double", "float", "-", "<FLOAT_LITERAL>", "long", "byte", "<LONG_LITERAL>", "(", "false", "super", "<INTEGER_LITERAL>", "null", "void", "this", "short", "<", "<NODE_VARIABLE>", "<IDENTIFIER>", "new", "char", "--", "++", "<CHARACTER_LITERAL>", "<DOUBLE_LITERAL>", "<STRING_LITERAL>", "+", "true", "!", "~", "int", "boolean")) {
			if (!matchConditionalExpression())
				return false;
			if (matchNext(">>=", "%=", "<<=", "&=", "|=", "+=", ">>>=", "*=", "/=", "-=", "^=", "=")) {
				if (!matchAssignmentOperator())
					return false;
				if (!matchExpression())
					return false;
			}
		}
		return true;
	}

	private boolean matchModifiersNoDefault() {
		while (matchNext("volatile", "abstract", "native", "strictfp", "transient", "private", "synchronized", "final", "protected", "static", "public", "@")) {
			if (matchNext("public")) {
				if (!match("public"))
					return false;
			} else if (matchNext("protected")) {
				if (!match("protected"))
					return false;
			} else if (matchNext("private")) {
				if (!match("private"))
					return false;
			} else if (matchNext("abstract")) {
				if (!match("abstract"))
					return false;
			} else if (matchNext("static")) {
				if (!match("static"))
					return false;
			} else if (matchNext("final")) {
				if (!match("final"))
					return false;
			} else if (matchNext("transient")) {
				if (!match("transient"))
					return false;
			} else if (matchNext("volatile")) {
				if (!match("volatile"))
					return false;
			} else if (matchNext("synchronized")) {
				if (!match("synchronized"))
					return false;
			} else if (matchNext("native")) {
				if (!match("native"))
					return false;
			} else if (matchNext("strictfp")) {
				if (!match("strictfp"))
					return false;
			} else if (matchNext("@")) {
				if (!matchAnnotation())
					return false;
			}
		}
		return true;
	}

	private boolean matchCastExpression() {
		if (!match("("))
			return false;
		if (!matchAnnotations())
			return false;
		if (matchNext("short", "long", "byte", "double", "char", "float", "boolean", "int")) {
			if (!matchPrimitiveType())
				return false;
			if (matchNext(")")) {
				if (!match(")"))
					return false;
				if (!matchUnaryExpression())
					return false;
			} else if (matchNext("[", "@")) {
				if (!matchArrayDimsMandatory())
					return false;
				if (!matchReferenceCastTypeRest())
					return false;
				if (!match(")"))
					return false;
				if (!matchUnaryExpressionNotPlusMinus())
					return false;
			}
		} else if (matchNext("<NODE_VARIABLE>", "<IDENTIFIER>")) {
			if (!matchQualifiedType())
				return false;
			if (matchCastExpression1()) {
				if (!matchArrayDimsMandatory())
					return false;
			}
			if (!matchReferenceCastTypeRest())
				return false;
			if (!match(")"))
				return false;
			if (!matchUnaryExpressionNotPlusMinus())
				return false;
		}
		return true;
	}

	private boolean matchModifiers() {
		while (matchNext("abstract", "strictfp", "transient", "private", "public", "volatile", "native", "synchronized", "final", "default", "protected", "static", "@")) {
			if (matchNext("public")) {
				if (!match("public"))
					return false;
			} else if (matchNext("protected")) {
				if (!match("protected"))
					return false;
			} else if (matchNext("private")) {
				if (!match("private"))
					return false;
			} else if (matchNext("abstract")) {
				if (!match("abstract"))
					return false;
			} else if (matchNext("default")) {
				if (!match("default"))
					return false;
			} else if (matchNext("static")) {
				if (!match("static"))
					return false;
			} else if (matchNext("final")) {
				if (!match("final"))
					return false;
			} else if (matchNext("transient")) {
				if (!match("transient"))
					return false;
			} else if (matchNext("volatile")) {
				if (!match("volatile"))
					return false;
			} else if (matchNext("synchronized")) {
				if (!match("synchronized"))
					return false;
			} else if (matchNext("native")) {
				if (!match("native"))
					return false;
			} else if (matchNext("strictfp")) {
				if (!match("strictfp"))
					return false;
			} else if (matchNext("@")) {
				if (!matchAnnotation())
					return false;
			}
		}
		return true;
	}

	private boolean matchPackageDecl() {
		if (!matchAnnotations())
			return false;
		if (!match("package"))
			return false;
		if (!matchQualifiedName())
			return false;
		if (!match(";"))
			return false;
		return true;
	}

	private boolean matchTypeParameter() {
		if (!matchAnnotations())
			return false;
		if (!matchName())
			return false;
		if (matchNext("extends")) {
			if (!matchTypeBounds())
				return false;
		}
		return true;
	}

	private boolean matchNodeVar() {
		if (!match("<NODE_VARIABLE>"))
			return false;
		return true;
	}

	private boolean matchConditionalExpression() {
		if (!matchConditionalOrExpression())
			return false;
		if (matchNext("?")) {
			if (!match("?"))
				return false;
			if (!matchExpression())
				return false;
			if (!match(":"))
				return false;
			if (!matchConditionalExpression())
				return false;
		}
		return true;
	}

	private boolean matchArguments() {
		if (!match("("))
			return false;
		if (matchNext("double", "float", "-", "<FLOAT_LITERAL>", "long", "byte", "<LONG_LITERAL>", "(", "false", "super", "<INTEGER_LITERAL>", "null", "void", "this", "<NODE_LIST_VARIABLE>", "short", "<", "<NODE_VARIABLE>", "<IDENTIFIER>", "new", "char", "++", "--", "<CHARACTER_LITERAL>", "<DOUBLE_LITERAL>", "<STRING_LITERAL>", "+", "true", "!", "~", "int", "boolean")) {
			if (matchNext("double", "float", "-", "<FLOAT_LITERAL>", "long", "byte", "<LONG_LITERAL>", "(", "false", "super", "<INTEGER_LITERAL>", "null", "void", "this", "short", "<", "<NODE_VARIABLE>", "<IDENTIFIER>", "new", "char", "++", "--", "<CHARACTER_LITERAL>", "<DOUBLE_LITERAL>", "<STRING_LITERAL>", "+", "true", "!", "~", "int", "boolean")) {
				if (!matchExpression())
					return false;
				while (matchNext(",")) {
					if (!match(","))
						return false;
					if (!matchExpression())
						return false;
				}
			} else if (matchNext("<NODE_LIST_VARIABLE>")) {
				if (!matchNodeListVar())
					return false;
			}
		}
		if (!match(")"))
			return false;
		return true;
	}

	private boolean matchMethodInvocation() {
		if (matchNext("<")) {
			if (!matchTypeArguments())
				return false;
		}
		if (!matchName())
			return false;
		if (!matchArguments())
			return false;
		return true;
	}

	private boolean matchUnaryExpression() {
		if (matchNext("++", "--")) {
			if (!matchPrefixExpression())
				return false;
		} else if (matchNext("+", "-")) {
			if (matchNext("+")) {
				if (!match("+"))
					return false;
			} else if (matchNext("-")) {
				if (!match("-"))
					return false;
			}
			if (!matchUnaryExpression())
				return false;
		} else if (matchNext("double", "float", "<FLOAT_LITERAL>", "long", "byte", "<LONG_LITERAL>", "(", "false", "super", "<INTEGER_LITERAL>", "null", "void", "this", "short", "<", "<NODE_VARIABLE>", "<IDENTIFIER>", "new", "char", "<CHARACTER_LITERAL>", "<DOUBLE_LITERAL>", "<STRING_LITERAL>", "true", "!", "~", "int", "boolean")) {
			if (!matchUnaryExpressionNotPlusMinus())
				return false;
		}
		return true;
	}

	private boolean matchPrimaryPrefix() {
		if (matchNext("<DOUBLE_LITERAL>", "<STRING_LITERAL>", "true", "<LONG_LITERAL>", "false", "<INTEGER_LITERAL>", "<CHARACTER_LITERAL>", "null", "<FLOAT_LITERAL>")) {
			if (!matchLiteral())
				return false;
		} else if (matchNext("this")) {
			if (!match("this"))
				return false;
		} else if (matchNext("super")) {
			if (!match("super"))
				return false;
			if (matchNext(".")) {
				if (!match("."))
					return false;
				if (matchPrimaryPrefix1()) {
					if (!matchMethodInvocation())
						return false;
				} else if (matchNext("<NODE_VARIABLE>", "<IDENTIFIER>")) {
					if (!matchFieldAccess())
						return false;
				}
			} else if (matchNext("::")) {
				if (!matchMethodReferenceSuffix())
					return false;
			}
		} else if (matchNext("new")) {
			if (!matchAllocationExpression())
				return false;
		} else if (matchPrimaryPrefix2()) {
			if (!matchResultType())
				return false;
			if (!match("."))
				return false;
			if (!match("class"))
				return false;
		} else if (matchPrimaryPrefix3()) {
			if (!matchResultType())
				return false;
			if (!matchMethodReferenceSuffix())
				return false;
		} else if (matchPrimaryPrefix4()) {
			if (!matchMethodInvocation())
				return false;
		} else if (matchNext("<NODE_VARIABLE>", "<IDENTIFIER>")) {
			if (!matchName())
				return false;
			if (matchNext("->")) {
				if (!match("->"))
					return false;
				if (!matchLambdaBody())
					return false;
			}
		} else if (matchNext("(")) {
			if (!match("("))
				return false;
			if (matchNext(")")) {
				if (!match(")"))
					return false;
				if (!match("->"))
					return false;
				if (!matchLambdaBody())
					return false;
			} else if (matchPrimaryPrefix5()) {
				if (!matchName())
					return false;
				if (!match(")"))
					return false;
				if (!match("->"))
					return false;
				if (!matchLambdaBody())
					return false;
			} else if (matchPrimaryPrefix6()) {
				if (!matchInferredFormalParameterList())
					return false;
				if (!match(")"))
					return false;
				if (!match("->"))
					return false;
				if (!matchLambdaBody())
					return false;
			} else if (matchNext("<NODE_LIST_VARIABLE>", "abstract", "short", "strictfp", "<NODE_VARIABLE>", "transient", "<IDENTIFIER>", "private", "double", "char", "float", "public", "volatile", "long", "native", "byte", "synchronized", "final", "default", "protected", "static", "@", "int", "boolean")) {
				if (!matchFormalParameterList())
					return false;
				if (!match(")"))
					return false;
				if (!match("->"))
					return false;
				if (!matchLambdaBody())
					return false;
			} else if (matchNext("double", "float", "-", "<FLOAT_LITERAL>", "long", "byte", "<LONG_LITERAL>", "(", "false", "super", "<INTEGER_LITERAL>", "null", "void", "this", "short", "<", "<NODE_VARIABLE>", "<IDENTIFIER>", "new", "char", "++", "--", "<CHARACTER_LITERAL>", "<DOUBLE_LITERAL>", "<STRING_LITERAL>", "+", "true", "!", "~", "int", "boolean")) {
				if (!matchExpression())
					return false;
				if (!match(")"))
					return false;
			}
		}
		return true;
	}

	private boolean matchAssignmentOperator() {
		if (matchNext("=")) {
			if (!match("="))
				return false;
		} else if (matchNext("*=")) {
			if (!match("*="))
				return false;
		} else if (matchNext("/=")) {
			if (!match("/="))
				return false;
		} else if (matchNext("%=")) {
			if (!match("%="))
				return false;
		} else if (matchNext("+=")) {
			if (!match("+="))
				return false;
		} else if (matchNext("-=")) {
			if (!match("-="))
				return false;
		} else if (matchNext("<<=")) {
			if (!match("<<="))
				return false;
		} else if (matchNext(">>=")) {
			if (!match(">>="))
				return false;
		} else if (matchNext(">>>=")) {
			if (!match(">>>="))
				return false;
		} else if (matchNext("&=")) {
			if (!match("&="))
				return false;
		} else if (matchNext("^=")) {
			if (!match("^="))
				return false;
		} else if (matchNext("|=")) {
			if (!match("|="))
				return false;
		}
		return true;
	}

	private boolean matchInferredFormalParameterList() {
		if (!matchInferredFormalParameter())
			return false;
		while (matchNext(",")) {
			if (!match(","))
				return false;
			if (!matchInferredFormalParameter())
				return false;
		}
		return true;
	}

	private boolean matchVariableDecl() {
		if (!matchType())
			return false;
		if (!matchVariableDeclarators())
			return false;
		return true;
	}

	private boolean matchNodeListVar() {
		if (!match("<NODE_LIST_VARIABLE>"))
			return false;
		return true;
	}

	private boolean matchAnnotation() {
		if (matchAnnotation1()) {
			if (!matchNormalAnnotation())
				return false;
		} else if (matchAnnotation2()) {
			if (!matchSingleMemberAnnotation())
				return false;
		} else if (matchAnnotation3()) {
			if (!matchMarkerAnnotation())
				return false;
		}
		return true;
	}

	private boolean matchLambdaBody() {
		if (matchNext("double", "float", "-", "<FLOAT_LITERAL>", "long", "byte", "<LONG_LITERAL>", "(", "false", "super", "<INTEGER_LITERAL>", "null", "void", "this", "short", "<", "<NODE_VARIABLE>", "<IDENTIFIER>", "new", "char", "++", "--", "<CHARACTER_LITERAL>", "<DOUBLE_LITERAL>", "<STRING_LITERAL>", "+", "true", "!", "~", "int", "boolean")) {
			if (!matchExpression())
				return false;
		} else if (matchNext("{")) {
			if (!matchBlock())
				return false;
		}
		return true;
	}

	private boolean matchReferenceCastTypeRest() {
		if (matchReferenceCastTypeRest1()) {
			do {
				if (!match("&"))
					return false;
				if (!matchAnnotations())
					return false;
				if (!matchReferenceType())
					return false;
			} while (matchNext("&"));
		}
		return true;
	}

	private boolean matchUnaryExpressionNotPlusMinus() {
		if (matchNext("!", "~")) {
			if (matchNext("~")) {
				if (!match("~"))
					return false;
			} else if (matchNext("!")) {
				if (!match("!"))
					return false;
			}
			if (!matchUnaryExpression())
				return false;
		} else if (matchUnaryExpressionNotPlusMinus1()) {
			if (!matchCastExpression())
				return false;
		} else if (matchNext("double", "float", "<FLOAT_LITERAL>", "long", "byte", "<LONG_LITERAL>", "(", "false", "super", "<INTEGER_LITERAL>", "null", "void", "this", "short", "<", "<NODE_VARIABLE>", "<IDENTIFIER>", "new", "char", "<CHARACTER_LITERAL>", "<DOUBLE_LITERAL>", "<STRING_LITERAL>", "true", "int", "boolean")) {
			if (!matchPostfixExpression())
				return false;
		}
		return true;
	}

	private boolean matchQualifiedType() {
		if (!matchName())
			return false;
		if (matchNext("<")) {
			if (!matchTypeArgumentsOrDiamond())
				return false;
		}
		while (matchNext(".")) {
			if (!match("."))
				return false;
			if (!matchAnnotations())
				return false;
			if (!matchName())
				return false;
			if (matchNext("<")) {
				if (!matchTypeArgumentsOrDiamond())
					return false;
			}
		}
		return true;
	}

	private boolean matchAllocationExpression() {
		if (!match("new"))
			return false;
		if (matchNext("<")) {
			if (!matchTypeArguments())
				return false;
		}
		if (!matchAnnotations())
			return false;
		if (matchNext("short", "long", "byte", "double", "char", "float", "boolean", "int")) {
			if (!matchPrimitiveType())
				return false;
			if (!matchArrayCreationExpr())
				return false;
		} else if (matchNext("<NODE_VARIABLE>", "<IDENTIFIER>")) {
			if (!matchQualifiedType())
				return false;
			if (matchNext("[", "@")) {
				if (!matchArrayCreationExpr())
					return false;
			} else if (matchNext("(")) {
				if (!matchArguments())
					return false;
				if (matchAllocationExpression1()) {
					if (!matchClassOrInterfaceBody())
						return false;
				}
			}
		}
		return true;
	}

	private boolean matchTypeArgumentList() {
		if (matchNext("short", "long", "<NODE_VARIABLE>", "byte", "<IDENTIFIER>", "double", "char", "float", "@", "boolean", "int", "?")) {
			if (!matchTypeArgument())
				return false;
			while (matchNext(",")) {
				if (!match(","))
					return false;
				if (!matchTypeArgument())
					return false;
			}
		} else if (matchNext("<NODE_LIST_VARIABLE>")) {
			if (!match("<NODE_LIST_VARIABLE>"))
				return false;
		}
		return true;
	}

	private boolean matchPrimitiveType() {
		if (matchNext("boolean")) {
			if (!match("boolean"))
				return false;
		} else if (matchNext("char")) {
			if (!match("char"))
				return false;
		} else if (matchNext("byte")) {
			if (!match("byte"))
				return false;
		} else if (matchNext("short")) {
			if (!match("short"))
				return false;
		} else if (matchNext("int")) {
			if (!match("int"))
				return false;
		} else if (matchNext("long")) {
			if (!match("long"))
				return false;
		} else if (matchNext("float")) {
			if (!match("float"))
				return false;
		} else if (matchNext("double")) {
			if (!match("double"))
				return false;
		}
		return true;
	}

	private boolean matchFieldAccess() {
		if (!matchName())
			return false;
		return true;
	}

	private boolean matchArrayDimsMandatory() {
		do {
			if (!matchAnnotations())
				return false;
			if (!match("["))
				return false;
			if (!match("]"))
				return false;
		} while (matchArrayDimsMandatory1());
		return true;
	}

	private boolean matchInferredFormalParameter() {
		if (!matchName())
			return false;
		return true;
	}

	private boolean matchVariableDeclarators() {
		if (!matchVariableDeclarator())
			return false;
		while (matchNext(",")) {
			if (!match(","))
				return false;
			if (!matchVariableDeclarator())
				return false;
		}
		return true;
	}

	private boolean matchTypeArgumentsOrDiamond() {
		if (!match("<"))
			return false;
		if (matchNext("<NODE_LIST_VARIABLE>", "short", "<NODE_VARIABLE>", "<IDENTIFIER>", "double", "char", "float", "long", "byte", "@", "int", "boolean", "?")) {
			if (!matchTypeArgumentList())
				return false;
		}
		if (!match(">"))
			return false;
		return true;
	}

	private boolean matchSingleMemberAnnotation() {
		if (!match("@"))
			return false;
		if (!matchQualifiedName())
			return false;
		if (!match("("))
			return false;
		if (!matchMemberValue())
			return false;
		if (!match(")"))
			return false;
		return true;
	}

	private boolean matchTypeArgument() {
		if (!matchAnnotations())
			return false;
		if (matchNext("short", "long", "<NODE_VARIABLE>", "byte", "<IDENTIFIER>", "double", "char", "float", "boolean", "int")) {
			if (!matchReferenceType())
				return false;
		} else if (matchNext("?")) {
			if (!matchWildcard())
				return false;
		}
		return true;
	}

	private boolean matchClassOrInterfaceBody() {
		if (!match("{"))
			return false;
		if (!matchClassOrInterfaceBodyDecls())
			return false;
		if (!match("}"))
			return false;
		return true;
	}

	private boolean matchReferenceType() {
		if (matchNext("short", "long", "byte", "double", "char", "float", "boolean", "int")) {
			if (!matchPrimitiveType())
				return false;
			if (!matchArrayDimsMandatory())
				return false;
		} else if (matchNext("<NODE_VARIABLE>", "<IDENTIFIER>")) {
			if (!matchQualifiedType())
				return false;
			if (matchReferenceType1()) {
				if (!matchArrayDimsMandatory())
					return false;
			}
		}
		return true;
	}

	private boolean matchTypeBounds() {
		if (!match("extends"))
			return false;
		if (matchNext("<NODE_VARIABLE>", "<IDENTIFIER>", "@")) {
			if (!matchAnnotatedQualifiedType())
				return false;
			while (matchNext("&")) {
				if (!match("&"))
					return false;
				if (!matchAnnotatedQualifiedType())
					return false;
			}
		} else if (matchNext("<NODE_LIST_VARIABLE>")) {
			if (!matchNodeListVar())
				return false;
		}
		return true;
	}

	private boolean matchFormalParameterList() {
		if (matchNext("abstract", "short", "strictfp", "<NODE_VARIABLE>", "transient", "<IDENTIFIER>", "private", "double", "char", "float", "public", "volatile", "long", "native", "byte", "synchronized", "final", "default", "protected", "static", "@", "int", "boolean")) {
			if (!matchFormalParameter())
				return false;
			while (matchNext(",")) {
				if (!match(","))
					return false;
				if (!matchFormalParameter())
					return false;
			}
		} else if (matchNext("<NODE_LIST_VARIABLE>")) {
			if (!matchNodeListVar())
				return false;
		}
		return true;
	}

	private boolean matchMarkerAnnotation() {
		if (!match("@"))
			return false;
		if (!matchQualifiedName())
			return false;
		return true;
	}

	private boolean matchMethodReferenceSuffix() {
		if (!match("::"))
			return false;
		if (matchNext("<")) {
			if (!matchTypeArguments())
				return false;
		}
		if (matchNext("<NODE_VARIABLE>", "<IDENTIFIER>")) {
			if (!matchName())
				return false;
		} else if (matchNext("new")) {
			if (!match("new"))
				return false;
		}
		return true;
	}

	private boolean matchLiteral() {
		if (matchNext("<INTEGER_LITERAL>")) {
			if (!match("<INTEGER_LITERAL>"))
				return false;
		} else if (matchNext("<LONG_LITERAL>")) {
			if (!match("<LONG_LITERAL>"))
				return false;
		} else if (matchNext("<FLOAT_LITERAL>")) {
			if (!match("<FLOAT_LITERAL>"))
				return false;
		} else if (matchNext("<DOUBLE_LITERAL>")) {
			if (!match("<DOUBLE_LITERAL>"))
				return false;
		} else if (matchNext("<CHARACTER_LITERAL>")) {
			if (!match("<CHARACTER_LITERAL>"))
				return false;
		} else if (matchNext("<STRING_LITERAL>")) {
			if (!match("<STRING_LITERAL>"))
				return false;
		} else if (matchNext("true")) {
			if (!match("true"))
				return false;
		} else if (matchNext("false")) {
			if (!match("false"))
				return false;
		} else if (matchNext("null")) {
			if (!match("null"))
				return false;
		}
		return true;
	}

	private boolean matchPrefixExpression() {
		if (matchNext("++")) {
			if (!match("++"))
				return false;
		} else if (matchNext("--")) {
			if (!match("--"))
				return false;
		}
		if (!matchUnaryExpression())
			return false;
		return true;
	}

	private boolean matchNormalAnnotation() {
		if (!match("@"))
			return false;
		if (!matchQualifiedName())
			return false;
		if (!match("("))
			return false;
		if (matchNext("<NODE_VARIABLE>", "<IDENTIFIER>")) {
			if (!matchMemberValuePairs())
				return false;
		}
		if (!match(")"))
			return false;
		return true;
	}

	private boolean matchBlock() {
		if (!match("{"))
			return false;
		if (!matchStatements())
			return false;
		if (!match("}"))
			return false;
		return true;
	}

	private boolean matchPostfixExpression() {
		if (!matchPrimaryExpression())
			return false;
		if (matchNext("++", "--")) {
			if (matchNext("++")) {
				if (!match("++"))
					return false;
			} else if (matchNext("--")) {
				if (!match("--"))
					return false;
			}
		}
		return true;
	}

	private boolean matchArrayCreationExpr() {
		if (matchArrayCreationExpr1()) {
			if (!matchArrayDimExprsMandatory())
				return false;
			if (!matchArrayDims())
				return false;
		} else if (matchNext("[", "@")) {
			if (!matchArrayDimsMandatory())
				return false;
			if (!matchArrayInitializer())
				return false;
		}
		return true;
	}

	private boolean matchConditionalOrExpression() {
		if (!matchConditionalAndExpression())
			return false;
		while (matchNext("||")) {
			if (!match("||"))
				return false;
			if (!matchConditionalAndExpression())
				return false;
		}
		return true;
	}

	private boolean matchFormalParameter() {
		if (!matchModifiers())
			return false;
		if (!matchType())
			return false;
		if (matchNext("...")) {
			if (!match("..."))
				return false;
		}
		if (!matchVariableDeclaratorId())
			return false;
		return true;
	}

	private boolean matchAnnotatedQualifiedType() {
		if (!matchAnnotations())
			return false;
		if (!matchQualifiedType())
			return false;
		return true;
	}

	private boolean matchPrimaryExpression() {
		if (!matchPrimaryPrefix())
			return false;
		while (matchNext("[", "::", ".")) {
			if (!matchPrimarySuffix())
				return false;
		}
		return true;
	}

	private boolean matchArrayDimExprsMandatory() {
		do {
			if (!matchAnnotations())
				return false;
			if (!match("["))
				return false;
			if (!matchExpression())
				return false;
			if (!match("]"))
				return false;
		} while (matchArrayDimExprsMandatory1());
		return true;
	}

	private boolean matchClassOrInterfaceBodyDecls() {
		if (matchNext("class", "abstract", "{", "strictfp", ";", "transient", "private", "double", "float", "long", "byte", "final", "default", "static", "enum", "void", "<NODE_LIST_VARIABLE>", "short", "<", "<NODE_VARIABLE>", "<IDENTIFIER>", "char", "public", "volatile", "native", "synchronized", "interface", "protected", "@", "int", "boolean")) {
			if (matchNext("class", "abstract", "{", "strictfp", ";", "transient", "private", "double", "float", "long", "byte", "final", "default", "static", "enum", "void", "short", "<", "<NODE_VARIABLE>", "<IDENTIFIER>", "char", "public", "volatile", "native", "synchronized", "interface", "protected", "@", "int", "boolean")) {
				do {
					if (!matchClassOrInterfaceBodyDecl())
						return false;
				} while (matchNext("class", "abstract", "{", "strictfp", ";", "transient", "private", "double", "float", "long", "byte", "final", "default", "static", "enum", "void", "short", "<", "<NODE_VARIABLE>", "<IDENTIFIER>", "char", "public", "volatile", "native", "synchronized", "interface", "protected", "@", "int", "boolean"));
			} else if (matchNext("<NODE_LIST_VARIABLE>")) {
				if (!matchNodeListVar())
					return false;
			}
		}
		return true;
	}

	private boolean matchVariableDeclarator() {
		if (!matchVariableDeclaratorId())
			return false;
		if (matchNext("=")) {
			if (!match("="))
				return false;
			if (!matchVariableInitializer())
				return false;
		}
		return true;
	}

	private boolean matchMemberValuePairs() {
		if (!matchMemberValuePair())
			return false;
		while (matchNext(",")) {
			if (!match(","))
				return false;
			if (!matchMemberValuePair())
				return false;
		}
		return true;
	}

	private boolean matchWildcard() {
		if (!match("?"))
			return false;
		if (matchNext("extends", "super")) {
			if (matchNext("extends")) {
				if (!match("extends"))
					return false;
				if (!matchAnnotations())
					return false;
				if (!matchReferenceType())
					return false;
			} else if (matchNext("super")) {
				if (!match("super"))
					return false;
				if (!matchAnnotations())
					return false;
				if (!matchReferenceType())
					return false;
			}
		}
		return true;
	}

	private boolean matchMemberValue() {
		if (matchNext("@")) {
			if (!matchAnnotation())
				return false;
		} else if (matchNext("{")) {
			if (!matchMemberValueArrayInitializer())
				return false;
		} else if (matchNext("double", "float", "-", "<FLOAT_LITERAL>", "long", "byte", "<LONG_LITERAL>", "(", "false", "super", "<INTEGER_LITERAL>", "null", "void", "this", "short", "<", "<NODE_VARIABLE>", "<IDENTIFIER>", "new", "char", "++", "--", "<CHARACTER_LITERAL>", "<DOUBLE_LITERAL>", "<STRING_LITERAL>", "+", "true", "!", "~", "int", "boolean")) {
			if (!matchConditionalExpression())
				return false;
		}
		return true;
	}

	private boolean matchArrayDims() {
		while (matchArrayDims1()) {
			if (!matchAnnotations())
				return false;
			if (!match("["))
				return false;
			if (!match("]"))
				return false;
		}
		return true;
	}

	private boolean matchConditionalAndExpression() {
		if (!matchInclusiveOrExpression())
			return false;
		while (matchNext("&&")) {
			if (!match("&&"))
				return false;
			if (!matchInclusiveOrExpression())
				return false;
		}
		return true;
	}

	private boolean matchStatements() {
		if (matchNext("strictfp", ";", "transient", "<LONG_LITERAL>", "false", "final", "<INTEGER_LITERAL>", "assert", "break", "null", "do", "void", "this", "short", "<", "<NODE_VARIABLE>", "<CHARACTER_LITERAL>", "++", "<DOUBLE_LITERAL>", "native", "true", "try", "@", "switch", "class", "abstract", "throw", "{", "double", "private", "if", "float", "return", "<FLOAT_LITERAL>", "long", "byte", "(", "super", "static", "<NODE_LIST_VARIABLE>", "for", "<IDENTIFIER>", "continue", "new", "char", "--", "public", "volatile", "<STRING_LITERAL>", "synchronized", "interface", "while", "protected", "int", "boolean")) {
			if (matchNext("strictfp", ";", "transient", "<LONG_LITERAL>", "false", "final", "<INTEGER_LITERAL>", "assert", "break", "null", "do", "void", "this", "short", "<", "<NODE_VARIABLE>", "<CHARACTER_LITERAL>", "++", "<DOUBLE_LITERAL>", "native", "true", "try", "@", "switch", "class", "abstract", "throw", "{", "double", "private", "if", "float", "return", "<FLOAT_LITERAL>", "long", "byte", "(", "super", "static", "for", "<IDENTIFIER>", "continue", "new", "char", "--", "public", "volatile", "<STRING_LITERAL>", "synchronized", "interface", "while", "protected", "int", "boolean")) {
				do {
					if (!matchBlockStatement())
						return false;
				} while (matchNext("strictfp", ";", "transient", "<LONG_LITERAL>", "false", "final", "<INTEGER_LITERAL>", "assert", "break", "null", "do", "void", "this", "short", "<", "<NODE_VARIABLE>", "<CHARACTER_LITERAL>", "++", "<DOUBLE_LITERAL>", "native", "true", "try", "@", "switch", "class", "abstract", "throw", "{", "double", "private", "if", "float", "return", "<FLOAT_LITERAL>", "long", "byte", "(", "super", "static", "for", "<IDENTIFIER>", "continue", "new", "char", "--", "public", "volatile", "<STRING_LITERAL>", "synchronized", "interface", "while", "protected", "int", "boolean"));
			} else if (matchNext("<NODE_LIST_VARIABLE>")) {
				if (!matchNodeListVar())
					return false;
			}
		}
		return true;
	}

	private boolean matchArrayInitializer() {
		if (!match("{"))
			return false;
		if (matchNext("{", "double", "float", "-", "<FLOAT_LITERAL>", "long", "byte", "<LONG_LITERAL>", "(", "false", "super", "<INTEGER_LITERAL>", "null", "void", "this", "short", "<", "<NODE_VARIABLE>", "<IDENTIFIER>", "new", "char", "++", "--", "<CHARACTER_LITERAL>", "<DOUBLE_LITERAL>", "<STRING_LITERAL>", "+", "true", "!", "~", "int", "boolean")) {
			if (!matchVariableInitializer())
				return false;
			while (matchNext(",")) {
				if (!match(","))
					return false;
				if (!matchVariableInitializer())
					return false;
			}
		}
		if (matchNext(",")) {
			if (!match(","))
				return false;
		}
		if (!match("}"))
			return false;
		return true;
	}

	private boolean matchVariableDeclaratorId() {
		if (!matchName())
			return false;
		if (!matchArrayDims())
			return false;
		return true;
	}

	private boolean matchClassOrInterfaceBodyDecl() {
		if (matchNext(";")) {
			if (!match(";"))
				return false;
		} else if (matchNext("class", "abstract", "{", "strictfp", "transient", "private", "double", "float", "long", "byte", "final", "default", "static", "enum", "void", "short", "<", "<NODE_VARIABLE>", "<IDENTIFIER>", "char", "public", "volatile", "native", "synchronized", "interface", "protected", "@", "int", "boolean")) {
			if (!matchModifiers())
				return false;
			if (matchNext("{")) {
				if (!matchInitializerDecl())
					return false;
			} else if (matchNext("class", "interface")) {
				if (!matchClassOrInterfaceDecl())
					return false;
			} else if (matchNext("enum")) {
				if (!matchEnumDecl())
					return false;
			} else if (matchNext("@")) {
				if (!matchAnnotationTypeDecl())
					return false;
			} else if (matchClassOrInterfaceBodyDecl1()) {
				if (!matchConstructorDecl())
					return false;
			} else if (matchClassOrInterfaceBodyDecl2()) {
				if (!matchFieldDecl())
					return false;
			} else if (matchNext("short", "long", "<", "<NODE_VARIABLE>", "byte", "<IDENTIFIER>", "double", "char", "float", "void", "boolean", "int")) {
				if (!matchMethodDecl())
					return false;
			}
		}
		return true;
	}

	private boolean matchMemberValuePair() {
		if (!matchName())
			return false;
		if (!match("="))
			return false;
		if (!matchMemberValue())
			return false;
		return true;
	}

	private boolean matchMemberValueArrayInitializer() {
		if (!match("{"))
			return false;
		if (matchNext("{", "double", "float", "-", "<FLOAT_LITERAL>", "long", "byte", "<LONG_LITERAL>", "(", "false", "super", "<INTEGER_LITERAL>", "null", "void", "this", "short", "<", "<NODE_VARIABLE>", "<IDENTIFIER>", "new", "char", "--", "++", "<CHARACTER_LITERAL>", "<DOUBLE_LITERAL>", "<STRING_LITERAL>", "+", "true", "!", "~", "@", "int", "boolean")) {
			if (!matchMemberValue())
				return false;
			while (matchNext(",")) {
				if (!match(","))
					return false;
				if (!matchMemberValue())
					return false;
			}
		}
		if (matchNext(",")) {
			if (!match(","))
				return false;
		}
		if (!match("}"))
			return false;
		return true;
	}

	private boolean matchInclusiveOrExpression() {
		if (!matchExclusiveOrExpression())
			return false;
		while (matchNext("|")) {
			if (!match("|"))
				return false;
			if (!matchExclusiveOrExpression())
				return false;
		}
		return true;
	}

	private boolean matchBlockStatement() {
		if (matchBlockStatement1()) {
			if (!matchModifiersNoDefault())
				return false;
			if (!matchClassOrInterfaceDecl())
				return false;
		} else if (matchBlockStatement2()) {
			if (!matchVariableDeclExpression())
				return false;
			if (!match(";"))
				return false;
		} else if (matchNext("switch", "throw", "{", ";", "double", "if", "float", "return", "<FLOAT_LITERAL>", "long", "byte", "<LONG_LITERAL>", "(", "false", "super", "<INTEGER_LITERAL>", "assert", "break", "null", "do", "void", "this", "short", "for", "<", "<NODE_VARIABLE>", "<IDENTIFIER>", "continue", "new", "char", "++", "--", "<CHARACTER_LITERAL>", "<DOUBLE_LITERAL>", "<STRING_LITERAL>", "true", "synchronized", "try", "while", "int", "boolean")) {
			if (!matchStatement())
				return false;
		}
		return true;
	}

	private boolean matchPrimarySuffix() {
		if (matchNext("[", ".")) {
			if (!matchPrimarySuffixWithoutSuper())
				return false;
		} else if (matchNext(".")) {
			if (!match("."))
				return false;
			if (!match("super"))
				return false;
		} else if (matchNext("::")) {
			if (!matchMethodReferenceSuffix())
				return false;
		}
		return true;
	}

	private boolean matchVariableInitializer() {
		if (matchNext("{")) {
			if (!matchArrayInitializer())
				return false;
		} else if (matchNext("double", "float", "-", "<FLOAT_LITERAL>", "long", "byte", "<LONG_LITERAL>", "(", "false", "super", "<INTEGER_LITERAL>", "null", "void", "this", "short", "<", "<NODE_VARIABLE>", "<IDENTIFIER>", "new", "char", "++", "--", "<CHARACTER_LITERAL>", "<DOUBLE_LITERAL>", "<STRING_LITERAL>", "+", "true", "!", "~", "int", "boolean")) {
			if (!matchExpression())
				return false;
		}
		return true;
	}

	private boolean matchFieldDecl() {
		if (!matchType())
			return false;
		if (!matchVariableDeclarators())
			return false;
		if (!match(";"))
			return false;
		return true;
	}

	private boolean matchConstructorDecl() {
		if (matchNext("<")) {
			if (!matchTypeParameters())
				return false;
		}
		if (!matchName())
			return false;
		if (!matchFormalParameters())
			return false;
		if (matchNext("throws")) {
			if (!matchThrowsClause())
				return false;
		}
		if (!match("{"))
			return false;
		if (matchNext("strictfp", ";", "transient", "<LONG_LITERAL>", "false", "final", "<INTEGER_LITERAL>", "assert", "break", "null", "do", "void", "this", "short", "<", "<NODE_VARIABLE>", "<CHARACTER_LITERAL>", "++", "<DOUBLE_LITERAL>", "native", "true", "try", "@", "switch", "class", "abstract", "throw", "{", "double", "private", "if", "float", "return", "<FLOAT_LITERAL>", "long", "byte", "(", "super", "static", "<NODE_LIST_VARIABLE>", "for", "<IDENTIFIER>", "continue", "new", "char", "--", "public", "volatile", "<STRING_LITERAL>", "synchronized", "interface", "while", "protected", "int", "boolean")) {
			if (matchNext("strictfp", ";", "transient", "<LONG_LITERAL>", "false", "final", "<INTEGER_LITERAL>", "assert", "break", "null", "do", "void", "this", "short", "<", "<NODE_VARIABLE>", "<CHARACTER_LITERAL>", "++", "<DOUBLE_LITERAL>", "native", "true", "try", "@", "switch", "class", "abstract", "throw", "{", "double", "private", "if", "float", "return", "<FLOAT_LITERAL>", "long", "byte", "(", "super", "static", "for", "<IDENTIFIER>", "continue", "new", "char", "--", "public", "volatile", "<STRING_LITERAL>", "synchronized", "interface", "while", "protected", "int", "boolean")) {
				if (matchConstructorDecl1()) {
					if (!matchExplicitConstructorInvocation())
						return false;
				} else if (matchNext("strictfp", ";", "transient", "<LONG_LITERAL>", "false", "final", "<INTEGER_LITERAL>", "assert", "break", "null", "do", "void", "this", "short", "<", "<NODE_VARIABLE>", "<CHARACTER_LITERAL>", "++", "<DOUBLE_LITERAL>", "native", "true", "try", "@", "switch", "class", "abstract", "throw", "{", "double", "private", "if", "float", "return", "<FLOAT_LITERAL>", "long", "byte", "(", "super", "static", "for", "<IDENTIFIER>", "continue", "new", "char", "--", "public", "volatile", "<STRING_LITERAL>", "synchronized", "interface", "while", "protected", "int", "boolean")) {
					if (!matchBlockStatement())
						return false;
				}
				while (matchNext("strictfp", ";", "transient", "<LONG_LITERAL>", "false", "final", "<INTEGER_LITERAL>", "assert", "break", "null", "do", "void", "this", "short", "<", "<NODE_VARIABLE>", "<CHARACTER_LITERAL>", "++", "<DOUBLE_LITERAL>", "native", "true", "try", "@", "switch", "class", "abstract", "throw", "{", "double", "private", "if", "float", "return", "<FLOAT_LITERAL>", "long", "byte", "(", "super", "static", "for", "<IDENTIFIER>", "continue", "new", "char", "--", "public", "volatile", "<STRING_LITERAL>", "synchronized", "interface", "while", "protected", "int", "boolean")) {
					if (!matchBlockStatement())
						return false;
				}
			} else if (matchNext("<NODE_LIST_VARIABLE>")) {
				if (!matchNodeListVar())
					return false;
			}
		}
		if (!match("}"))
			return false;
		return true;
	}

	private boolean matchInitializerDecl() {
		if (!matchBlock())
			return false;
		return true;
	}

	private boolean matchClassOrInterfaceDecl() {
		if (matchNext("class")) {
			if (!match("class"))
				return false;
			if (!matchName())
				return false;
			if (matchNext("<")) {
				if (!matchTypeParameters())
					return false;
			}
			if (matchNext("extends")) {
				if (!match("extends"))
					return false;
				if (!matchAnnotatedQualifiedType())
					return false;
			}
			if (matchNext("implements")) {
				if (!matchImplementsList())
					return false;
			}
		} else if (matchNext("interface")) {
			if (!match("interface"))
				return false;
			if (!matchName())
				return false;
			if (matchNext("<")) {
				if (!matchTypeParameters())
					return false;
			}
			if (matchNext("extends")) {
				if (!matchExtendsList())
					return false;
			}
		}
		if (!matchClassOrInterfaceBody())
			return false;
		return true;
	}

	private boolean matchStatement() {
		if (matchNext("<NODE_VARIABLE>", "<IDENTIFIER>")) {
			if (!matchLabeledStatement())
				return false;
		} else if (matchNext("assert")) {
			if (!matchAssertStatement())
				return false;
		} else if (matchNext("{")) {
			if (!matchBlock())
				return false;
		} else if (matchNext(";")) {
			if (!matchEmptyStatement())
				return false;
		} else if (matchNext("double", "float", "<FLOAT_LITERAL>", "long", "byte", "<LONG_LITERAL>", "(", "false", "super", "<INTEGER_LITERAL>", "null", "void", "this", "short", "<", "<NODE_VARIABLE>", "<IDENTIFIER>", "new", "char", "--", "++", "<CHARACTER_LITERAL>", "<DOUBLE_LITERAL>", "<STRING_LITERAL>", "true", "int", "boolean")) {
			if (!matchStatementExpression())
				return false;
		} else if (matchNext("switch")) {
			if (!matchSwitchStatement())
				return false;
		} else if (matchNext("if")) {
			if (!matchIfStatement())
				return false;
		} else if (matchNext("while")) {
			if (!matchWhileStatement())
				return false;
		} else if (matchNext("do")) {
			if (!matchDoStatement())
				return false;
		} else if (matchNext("for")) {
			if (!matchForStatement())
				return false;
		} else if (matchNext("break")) {
			if (!matchBreakStatement())
				return false;
		} else if (matchNext("continue")) {
			if (!matchContinueStatement())
				return false;
		} else if (matchNext("return")) {
			if (!matchReturnStatement())
				return false;
		} else if (matchNext("throw")) {
			if (!matchThrowStatement())
				return false;
		} else if (matchNext("synchronized")) {
			if (!matchSynchronizedStatement())
				return false;
		} else if (matchNext("try")) {
			if (!matchTryStatement())
				return false;
		}
		return true;
	}

	private boolean matchExclusiveOrExpression() {
		if (!matchAndExpression())
			return false;
		while (matchNext("^")) {
			if (!match("^"))
				return false;
			if (!matchAndExpression())
				return false;
		}
		return true;
	}

	private boolean matchMethodDecl() {
		if (matchNext("<")) {
			if (!matchTypeParameters())
				return false;
		}
		if (!matchResultType())
			return false;
		if (!matchName())
			return false;
		if (!matchFormalParameters())
			return false;
		if (!matchArrayDims())
			return false;
		if (matchNext("throws")) {
			if (!matchThrowsClause())
				return false;
		}
		if (matchNext("{")) {
			if (!matchBlock())
				return false;
		} else if (matchNext(";")) {
			if (!match(";"))
				return false;
		}
		return true;
	}

	private boolean matchEnumDecl() {
		if (!match("enum"))
			return false;
		if (!matchName())
			return false;
		if (matchNext("implements")) {
			if (!matchImplementsList())
				return false;
		}
		if (!match("{"))
			return false;
		if (matchNext("<NODE_LIST_VARIABLE>", "abstract", "strictfp", "<NODE_VARIABLE>", "transient", "<IDENTIFIER>", "private", "public", "volatile", "native", "synchronized", "final", "default", "protected", "static", "@")) {
			if (matchNext("abstract", "strictfp", "<NODE_VARIABLE>", "transient", "<IDENTIFIER>", "private", "public", "volatile", "native", "synchronized", "final", "default", "protected", "static", "@")) {
				if (!matchEnumConstantDecl())
					return false;
				while (matchNext(",")) {
					if (!match(","))
						return false;
					if (!matchEnumConstantDecl())
						return false;
				}
			} else if (matchNext("<NODE_LIST_VARIABLE>")) {
				if (!matchNodeListVar())
					return false;
			}
		}
		if (matchNext(",")) {
			if (!match(","))
				return false;
		}
		if (matchNext(";")) {
			if (!match(";"))
				return false;
			if (!matchClassOrInterfaceBodyDecls())
				return false;
		}
		if (!match("}"))
			return false;
		return true;
	}

	private boolean matchAnnotationTypeDecl() {
		if (!match("@"))
			return false;
		if (!match("interface"))
			return false;
		if (!matchName())
			return false;
		if (!matchAnnotationTypeBody())
			return false;
		return true;
	}

	private boolean matchAnnotationTypeBody() {
		if (!match("{"))
			return false;
		if (matchNext("class", "abstract", "strictfp", ";", "transient", "private", "double", "float", "long", "byte", "final", "default", "static", "enum", "<NODE_LIST_VARIABLE>", "short", "<NODE_VARIABLE>", "<IDENTIFIER>", "char", "public", "volatile", "native", "synchronized", "interface", "protected", "@", "int", "boolean")) {
			if (matchNext("class", "abstract", "strictfp", ";", "transient", "private", "double", "float", "long", "byte", "final", "default", "static", "enum", "short", "<NODE_VARIABLE>", "<IDENTIFIER>", "char", "public", "volatile", "native", "synchronized", "interface", "protected", "@", "int", "boolean")) {
				do {
					if (!matchAnnotationTypeBodyDecl())
						return false;
				} while (matchNext("class", "abstract", "strictfp", ";", "transient", "private", "double", "float", "long", "byte", "final", "default", "static", "enum", "short", "<NODE_VARIABLE>", "<IDENTIFIER>", "char", "public", "volatile", "native", "synchronized", "interface", "protected", "@", "int", "boolean"));
			} else if (matchNext("<NODE_LIST_VARIABLE>")) {
				if (!matchNodeListVar())
					return false;
			}
		}
		if (!match("}"))
			return false;
		return true;
	}

	private boolean matchExtendsList() {
		if (!match("extends"))
			return false;
		if (matchNext("<NODE_VARIABLE>", "<IDENTIFIER>", "@")) {
			if (!matchAnnotatedQualifiedType())
				return false;
			while (matchNext(",")) {
				if (!match(","))
					return false;
				if (!matchAnnotatedQualifiedType())
					return false;
			}
		} else if (matchNext("<NODE_LIST_VARIABLE>")) {
			if (!matchNodeListVar())
				return false;
		}
		return true;
	}

	private boolean matchDoStatement() {
		if (!match("do"))
			return false;
		if (!matchStatement())
			return false;
		if (!match("while"))
			return false;
		if (!match("("))
			return false;
		if (!matchExpression())
			return false;
		if (!match(")"))
			return false;
		if (!match(";"))
			return false;
		return true;
	}

	private boolean matchStatementExpression() {
		if (matchNext("++", "--")) {
			if (!matchPrefixExpression())
				return false;
		} else if (matchNext("double", "float", "<FLOAT_LITERAL>", "long", "byte", "<LONG_LITERAL>", "(", "false", "super", "<INTEGER_LITERAL>", "null", "void", "this", "short", "<", "<NODE_VARIABLE>", "<IDENTIFIER>", "new", "char", "<CHARACTER_LITERAL>", "<DOUBLE_LITERAL>", "<STRING_LITERAL>", "true", "int", "boolean")) {
			if (!matchPrimaryExpression())
				return false;
			if (matchNext(">>=", "<<=", "|=", "++", "--", "^=", "%=", "&=", "+=", ">>>=", "*=", "/=", "-=", "=")) {
				if (matchNext("++")) {
					if (!match("++"))
						return false;
				} else if (matchNext("--")) {
					if (!match("--"))
						return false;
				} else if (matchNext(">>=", "%=", "<<=", "&=", "|=", "+=", ">>>=", "*=", "/=", "-=", "^=", "=")) {
					if (!matchAssignmentOperator())
						return false;
					if (!matchExpression())
						return false;
				}
			}
		}
		if (!match(";"))
			return false;
		return true;
	}

	private boolean matchEmptyStatement() {
		if (!match(";"))
			return false;
		return true;
	}

	private boolean matchImplementsList() {
		if (!match("implements"))
			return false;
		if (matchNext("<NODE_VARIABLE>", "<IDENTIFIER>", "@")) {
			if (!matchAnnotatedQualifiedType())
				return false;
			while (matchNext(",")) {
				if (!match(","))
					return false;
				if (!matchAnnotatedQualifiedType())
					return false;
			}
		} else if (matchNext("<NODE_LIST_VARIABLE>")) {
			if (!matchNodeListVar())
				return false;
		}
		return true;
	}

	private boolean matchWhileStatement() {
		if (!match("while"))
			return false;
		if (!match("("))
			return false;
		if (!matchExpression())
			return false;
		if (!match(")"))
			return false;
		if (!matchStatement())
			return false;
		return true;
	}

	private boolean matchSynchronizedStatement() {
		if (!match("synchronized"))
			return false;
		if (!match("("))
			return false;
		if (!matchExpression())
			return false;
		if (!match(")"))
			return false;
		if (!matchBlock())
			return false;
		return true;
	}

	private boolean matchBreakStatement() {
		if (!match("break"))
			return false;
		if (matchNext("<NODE_VARIABLE>", "<IDENTIFIER>")) {
			if (!matchName())
				return false;
		}
		if (!match(";"))
			return false;
		return true;
	}

	private boolean matchIfStatement() {
		if (!match("if"))
			return false;
		if (!match("("))
			return false;
		if (!matchExpression())
			return false;
		if (!match(")"))
			return false;
		if (!matchStatement())
			return false;
		if (matchNext("else")) {
			if (!match("else"))
				return false;
			if (!matchStatement())
				return false;
		}
		return true;
	}

	private boolean matchSwitchStatement() {
		if (!match("switch"))
			return false;
		if (!match("("))
			return false;
		if (!matchExpression())
			return false;
		if (!match(")"))
			return false;
		if (!match("{"))
			return false;
		while (matchNext("case", "default")) {
			if (!matchSwitchEntry())
				return false;
		}
		if (!match("}"))
			return false;
		return true;
	}

	private boolean matchAndExpression() {
		if (!matchEqualityExpression())
			return false;
		while (matchNext("&")) {
			if (!match("&"))
				return false;
			if (!matchEqualityExpression())
				return false;
		}
		return true;
	}

	private boolean matchLabeledStatement() {
		if (!matchName())
			return false;
		if (!match(":"))
			return false;
		if (!matchStatement())
			return false;
		return true;
	}

	private boolean matchTryStatement() {
		if (!match("try"))
			return false;
		if (matchNext("(")) {
			if (!matchResourceSpecification())
				return false;
			if (!matchBlock())
				return false;
			if (matchNext("catch")) {
				if (!matchCatchClauses())
					return false;
			}
			if (matchNext("finally")) {
				if (!match("finally"))
					return false;
				if (!matchBlock())
					return false;
			}
		} else if (matchNext("{")) {
			if (!matchBlock())
				return false;
			if (matchNext("catch")) {
				if (!matchCatchClauses())
					return false;
				if (matchNext("finally")) {
					if (!match("finally"))
						return false;
					if (!matchBlock())
						return false;
				}
			} else if (matchNext("finally")) {
				if (!match("finally"))
					return false;
				if (!matchBlock())
					return false;
			}
		}
		return true;
	}

	private boolean matchReturnStatement() {
		if (!match("return"))
			return false;
		if (matchNext("double", "float", "-", "<FLOAT_LITERAL>", "long", "byte", "<LONG_LITERAL>", "(", "false", "super", "<INTEGER_LITERAL>", "null", "void", "this", "short", "<", "<NODE_VARIABLE>", "<IDENTIFIER>", "new", "char", "++", "--", "<CHARACTER_LITERAL>", "<DOUBLE_LITERAL>", "<STRING_LITERAL>", "+", "true", "!", "~", "int", "boolean")) {
			if (!matchExpression())
				return false;
		}
		if (!match(";"))
			return false;
		return true;
	}

	private boolean matchAssertStatement() {
		if (!match("assert"))
			return false;
		if (!matchExpression())
			return false;
		if (matchNext(":")) {
			if (!match(":"))
				return false;
			if (!matchExpression())
				return false;
		}
		if (!match(";"))
			return false;
		return true;
	}

	private boolean matchThrowStatement() {
		if (!match("throw"))
			return false;
		if (!matchExpression())
			return false;
		if (!match(";"))
			return false;
		return true;
	}

	private boolean matchThrowsClause() {
		if (!match("throws"))
			return false;
		if (!matchAnnotatedQualifiedType())
			return false;
		while (matchNext(",")) {
			if (!match(","))
				return false;
			if (!matchAnnotatedQualifiedType())
				return false;
		}
		return true;
	}

	private boolean matchForStatement() {
		if (!match("for"))
			return false;
		if (!match("("))
			return false;
		if (matchForStatement1()) {
			if (!matchVariableDeclExpression())
				return false;
			if (!match(":"))
				return false;
			if (!matchExpression())
				return false;
		} else if (matchNext("abstract", "strictfp", ";", "transient", "private", "double", "float", "-", "<FLOAT_LITERAL>", "long", "byte", "<LONG_LITERAL>", "(", "false", "super", "final", "<INTEGER_LITERAL>", "static", "null", "void", "this", "short", "<", "<NODE_VARIABLE>", "<IDENTIFIER>", "new", "char", "++", "--", "<CHARACTER_LITERAL>", "public", "<DOUBLE_LITERAL>", "volatile", "<STRING_LITERAL>", "native", "+", "true", "synchronized", "!", "protected", "~", "@", "int", "boolean")) {
			if (matchNext("abstract", "strictfp", "transient", "private", "double", "float", "-", "<FLOAT_LITERAL>", "long", "byte", "<LONG_LITERAL>", "(", "false", "super", "final", "<INTEGER_LITERAL>", "static", "null", "void", "this", "short", "<", "<NODE_VARIABLE>", "<IDENTIFIER>", "new", "char", "++", "--", "<CHARACTER_LITERAL>", "public", "<DOUBLE_LITERAL>", "volatile", "<STRING_LITERAL>", "native", "+", "true", "synchronized", "!", "protected", "~", "@", "int", "boolean")) {
				if (!matchForInit())
					return false;
			}
			if (!match(";"))
				return false;
			if (matchNext("double", "float", "-", "<FLOAT_LITERAL>", "long", "byte", "<LONG_LITERAL>", "(", "false", "super", "<INTEGER_LITERAL>", "null", "void", "this", "short", "<", "<NODE_VARIABLE>", "<IDENTIFIER>", "new", "char", "++", "--", "<CHARACTER_LITERAL>", "<DOUBLE_LITERAL>", "<STRING_LITERAL>", "+", "true", "!", "~", "int", "boolean")) {
				if (!matchExpression())
					return false;
			}
			if (!match(";"))
				return false;
			if (matchNext("double", "float", "-", "<FLOAT_LITERAL>", "long", "byte", "<LONG_LITERAL>", "(", "false", "super", "<INTEGER_LITERAL>", "null", "void", "this", "short", "<", "<NODE_VARIABLE>", "<IDENTIFIER>", "new", "char", "--", "++", "<CHARACTER_LITERAL>", "<DOUBLE_LITERAL>", "<STRING_LITERAL>", "+", "true", "!", "~", "int", "boolean")) {
				if (!matchForUpdate())
					return false;
			}
		}
		if (!match(")"))
			return false;
		if (!matchStatement())
			return false;
		return true;
	}

	private boolean matchFormalParameters() {
		if (!match("("))
			return false;
		if (matchNext("<NODE_LIST_VARIABLE>", "abstract", "short", "strictfp", "<NODE_VARIABLE>", "transient", "<IDENTIFIER>", "private", "double", "char", "float", "public", "volatile", "long", "native", "byte", "synchronized", "final", "default", "protected", "static", "@", "int", "boolean")) {
			if (!matchFormalParameterList())
				return false;
		}
		if (!match(")"))
			return false;
		return true;
	}

	private boolean matchContinueStatement() {
		if (!match("continue"))
			return false;
		if (matchNext("<NODE_VARIABLE>", "<IDENTIFIER>")) {
			if (!matchName())
				return false;
		}
		if (!match(";"))
			return false;
		return true;
	}

	private boolean matchEnumConstantDecl() {
		if (!matchModifiers())
			return false;
		if (!matchName())
			return false;
		if (matchNext("(")) {
			if (!matchArguments())
				return false;
		}
		if (matchNext("{")) {
			if (!matchClassOrInterfaceBody())
				return false;
		}
		return true;
	}

	private boolean matchCatchClauses() {
		do {
			if (!matchCatchClause())
				return false;
		} while (matchNext("catch"));
		return true;
	}

	private boolean matchAnnotationTypeBodyDecl() {
		if (matchNext(";")) {
			if (!match(";"))
				return false;
		} else if (matchNext("class", "abstract", "strictfp", "transient", "private", "double", "float", "long", "byte", "final", "default", "static", "enum", "short", "<NODE_VARIABLE>", "<IDENTIFIER>", "char", "public", "volatile", "native", "synchronized", "interface", "protected", "@", "int", "boolean")) {
			if (!matchModifiers())
				return false;
			if (matchAnnotationTypeBodyDecl1()) {
				if (!matchAnnotationTypeMemberDecl())
					return false;
			} else if (matchNext("class", "interface")) {
				if (!matchClassOrInterfaceDecl())
					return false;
			} else if (matchNext("enum")) {
				if (!matchEnumDecl())
					return false;
			} else if (matchNext("@")) {
				if (!matchAnnotationTypeDecl())
					return false;
			} else if (matchNext("short", "long", "<NODE_VARIABLE>", "byte", "<IDENTIFIER>", "double", "char", "float", "boolean", "int")) {
				if (!matchFieldDecl())
					return false;
			}
		}
		return true;
	}

	private boolean matchForUpdate() {
		if (!matchExpressionList())
			return false;
		return true;
	}

	private boolean matchSwitchEntry() {
		if (matchNext("case")) {
			if (!match("case"))
				return false;
			if (!matchExpression())
				return false;
		} else if (matchNext("default")) {
			if (!match("default"))
				return false;
		}
		if (!match(":"))
			return false;
		if (!matchStatements())
			return false;
		return true;
	}

	private boolean matchResourceSpecification() {
		if (!match("("))
			return false;
		if (!matchVariableDeclExpression())
			return false;
		while (matchNext(";")) {
			if (!match(";"))
				return false;
			if (!matchVariableDeclExpression())
				return false;
		}
		if (matchNext(";")) {
			if (!match(";"))
				return false;
		}
		if (!match(")"))
			return false;
		return true;
	}

	private boolean matchEqualityExpression() {
		if (!matchInstanceOfExpression())
			return false;
		while (matchNext("==", "!=")) {
			if (matchNext("==")) {
				if (!match("=="))
					return false;
			} else if (matchNext("!=")) {
				if (!match("!="))
					return false;
			}
			if (!matchInstanceOfExpression())
				return false;
		}
		return true;
	}

	private boolean matchForInit() {
		if (matchForInit1()) {
			if (!matchVariableDeclExpression())
				return false;
		} else if (matchNext("double", "float", "-", "<FLOAT_LITERAL>", "long", "byte", "<LONG_LITERAL>", "(", "false", "super", "<INTEGER_LITERAL>", "null", "void", "this", "short", "<", "<NODE_VARIABLE>", "<IDENTIFIER>", "new", "char", "++", "--", "<CHARACTER_LITERAL>", "<DOUBLE_LITERAL>", "<STRING_LITERAL>", "+", "true", "!", "~", "int", "boolean")) {
			if (!matchExpressionList())
				return false;
		}
		return true;
	}

	private boolean matchCatchClause() {
		if (!match("catch"))
			return false;
		if (!match("("))
			return false;
		if (!matchCatchFormalParameter())
			return false;
		if (!match(")"))
			return false;
		if (!matchBlock())
			return false;
		return true;
	}

	private boolean matchInstanceOfExpression() {
		if (!matchRelationalExpression())
			return false;
		if (matchNext("instanceof")) {
			if (!match("instanceof"))
				return false;
			if (!matchAnnotations())
				return false;
			if (!matchType())
				return false;
		}
		return true;
	}

	private boolean matchAnnotationTypeMemberDecl() {
		if (!matchType())
			return false;
		if (!matchName())
			return false;
		if (!match("("))
			return false;
		if (!match(")"))
			return false;
		if (!matchArrayDims())
			return false;
		if (matchNext("default")) {
			if (!match("default"))
				return false;
			if (!matchMemberValue())
				return false;
		}
		if (!match(";"))
			return false;
		return true;
	}

	private boolean matchExpressionList() {
		if (!matchExpression())
			return false;
		while (matchNext(",")) {
			if (!match(","))
				return false;
			if (!matchExpression())
				return false;
		}
		return true;
	}

	private boolean matchRelationalExpression() {
		if (!matchShiftExpression())
			return false;
		while (matchNext("<", "<=", ">=", ">")) {
			if (matchNext("<")) {
				if (!match("<"))
					return false;
			} else if (matchNext(">")) {
				if (!match(">"))
					return false;
			} else if (matchNext("<=")) {
				if (!match("<="))
					return false;
			} else if (matchNext(">=")) {
				if (!match(">="))
					return false;
			}
			if (!matchShiftExpression())
				return false;
		}
		return true;
	}

	private boolean matchCatchFormalParameter() {
		if (!matchModifiers())
			return false;
		if (!matchQualifiedType())
			return false;
		if (matchCatchFormalParameter1()) {
			do {
				if (!match("|"))
					return false;
				if (!matchAnnotatedQualifiedType())
					return false;
			} while (matchNext("|"));
		}
		if (!matchVariableDeclaratorId())
			return false;
		return true;
	}

	private boolean matchShiftExpression() {
		if (!matchAdditiveExpression())
			return false;
		while (matchNext("<<", ">")) {
			if (matchNext("<<")) {
				if (!match("<<"))
					return false;
			} else if (matchNext(">")) {
				if (!matchRSIGNEDSHIFT())
					return false;
			} else if (matchNext(">")) {
				if (!matchRUNSIGNEDSHIFT())
					return false;
			}
			if (!matchAdditiveExpression())
				return false;
		}
		return true;
	}

	private boolean matchAdditiveExpression() {
		if (!matchMultiplicativeExpression())
			return false;
		while (matchNext("+", "-")) {
			if (matchNext("+")) {
				if (!match("+"))
					return false;
			} else if (matchNext("-")) {
				if (!match("-"))
					return false;
			}
			if (!matchMultiplicativeExpression())
				return false;
		}
		return true;
	}

	private boolean matchRSIGNEDSHIFT() {
		if (!match(">"))
			return false;
		if (!match(">"))
			return false;
		return true;
	}

	private boolean matchRUNSIGNEDSHIFT() {
		if (!match(">"))
			return false;
		if (!match(">"))
			return false;
		if (!match(">"))
			return false;
		return true;
	}

	private boolean matchMultiplicativeExpression() {
		if (!matchUnaryExpression())
			return false;
		while (matchNext("*", "%", "/")) {
			if (matchNext("*")) {
				if (!match("*"))
					return false;
			} else if (matchNext("/")) {
				if (!match("/"))
					return false;
			} else if (matchNext("%")) {
				if (!match("%"))
					return false;
			}
			if (!matchUnaryExpression())
				return false;
		}
		return true;
	}
}
