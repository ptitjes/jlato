package org.jlato.internal.parser;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.coll.*;
import org.jlato.internal.bu.decl.*;
import org.jlato.internal.bu.expr.*;
import org.jlato.internal.bu.name.*;
import org.jlato.internal.bu.stmt.*;
import org.jlato.internal.bu.type.*;
import org.jlato.parser.ParseException;
import org.jlato.parser.ParserImplConstants;
import org.jlato.parser.Token;
import org.jlato.tree.Problem.Severity;
import org.jlato.tree.decl.ModifierKeyword;
import org.jlato.tree.expr.AssignOp;
import org.jlato.tree.expr.BinaryOp;
import org.jlato.tree.expr.UnaryOp;
import org.jlato.tree.type.Primitive;

public class ParserImplementation extends ParserNewBase {

	public BUTree<SNodeList> parseNodeListVar() throws ParseException {
		parse(ParserImplConstants.NODE_LIST_VARIABLE);
		return makeVar();
	}

	public BUTree<SName> parseNodeVar() throws ParseException {
		parse(ParserImplConstants.NODE_VARIABLE);
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
		if (backupLookahead(matchNext(ParserImplConstants.EOF))) {
			parse(ParserImplConstants.EOF);
		} else {
			parse(ParserImplConstants.EOF);
		}
	}

	public BUTree<SPackageDecl> parsePackageDecl() throws ParseException {
		BUTree<SNodeList> annotations = null;
		BUTree<SQualifiedName> name;
		run();
		annotations = parseAnnotations();
		parse(ParserImplConstants.PACKAGE);
		name = parseQualifiedName();
		parse(ParserImplConstants.SEMICOLON);
		return dress(SPackageDecl.make(annotations, name));
	}

	public BUTree<SNodeList> parseImportDecls() throws ParseException {
		BUTree<SNodeList> imports = emptyList();
		BUTree<SImportDecl> importDecl = null;
		while (backupLookahead(matchNext(ParserImplConstants.IMPORT))) {
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
		parse(ParserImplConstants.IMPORT);
		if (backupLookahead(matchNext(ParserImplConstants.STATIC))) {
			parse(ParserImplConstants.STATIC);
			isStatic = true;
		}
		name = parseQualifiedName();
		if (backupLookahead(matchNext(ParserImplConstants.DOT))) {
			parse(ParserImplConstants.DOT);
			parse(ParserImplConstants.STAR);
			isAsterisk = true;
		}
		parse(ParserImplConstants.SEMICOLON);
		return dress(SImportDecl.make(name, isStatic, isAsterisk));
	}

	public BUTree<SNodeList> parseTypeDecls() throws ParseException {
		BUTree<SNodeList> types = emptyList();
		BUTree<? extends STypeDecl> typeDecl = null;
		while (backupLookahead(matchNext(ParserImplConstants.CLASS, ParserImplConstants.ENUM, ParserImplConstants.VOLATILE, ParserImplConstants.SEMICOLON, ParserImplConstants.PROTECTED, ParserImplConstants.PUBLIC, ParserImplConstants.STRICTFP, ParserImplConstants.AT, ParserImplConstants.ABSTRACT, ParserImplConstants.PRIVATE, ParserImplConstants.TRANSIENT, ParserImplConstants._DEFAULT, ParserImplConstants.FINAL, ParserImplConstants.STATIC, ParserImplConstants.INTERFACE, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.NATIVE))) {
			typeDecl = parseTypeDecl();
			types = append(types, typeDecl);
		}
		return types;
	}

	public BUTree<SNodeList> parseModifiers() throws ParseException {
		BUTree<SNodeList> modifiers = emptyList();
		BUTree<? extends SAnnotationExpr> ann;
		while (backupLookahead(matchNext(ParserImplConstants.VOLATILE, ParserImplConstants.PROTECTED, ParserImplConstants.PUBLIC, ParserImplConstants.STRICTFP, ParserImplConstants.AT, ParserImplConstants.ABSTRACT, ParserImplConstants.PRIVATE, ParserImplConstants.TRANSIENT, ParserImplConstants._DEFAULT, ParserImplConstants.FINAL, ParserImplConstants.STATIC, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.NATIVE))) {
			if (backupLookahead(matchNext(ParserImplConstants.PUBLIC))) {
				parse(ParserImplConstants.PUBLIC);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Public));
			} else if (backupLookahead(matchNext(ParserImplConstants.PROTECTED))) {
				parse(ParserImplConstants.PROTECTED);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Protected));
			} else if (backupLookahead(matchNext(ParserImplConstants.PRIVATE))) {
				parse(ParserImplConstants.PRIVATE);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Private));
			} else if (backupLookahead(matchNext(ParserImplConstants.ABSTRACT))) {
				parse(ParserImplConstants.ABSTRACT);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Abstract));
			} else if (backupLookahead(matchNext(ParserImplConstants._DEFAULT))) {
				parse(ParserImplConstants._DEFAULT);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Default));
			} else if (backupLookahead(matchNext(ParserImplConstants.STATIC))) {
				parse(ParserImplConstants.STATIC);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Static));
			} else if (backupLookahead(matchNext(ParserImplConstants.FINAL))) {
				parse(ParserImplConstants.FINAL);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Final));
			} else if (backupLookahead(matchNext(ParserImplConstants.TRANSIENT))) {
				parse(ParserImplConstants.TRANSIENT);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Transient));
			} else if (backupLookahead(matchNext(ParserImplConstants.VOLATILE))) {
				parse(ParserImplConstants.VOLATILE);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Volatile));
			} else if (backupLookahead(matchNext(ParserImplConstants.SYNCHRONIZED))) {
				parse(ParserImplConstants.SYNCHRONIZED);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Synchronized));
			} else if (backupLookahead(matchNext(ParserImplConstants.NATIVE))) {
				parse(ParserImplConstants.NATIVE);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Native));
			} else if (backupLookahead(matchNext(ParserImplConstants.STRICTFP))) {
				parse(ParserImplConstants.STRICTFP);
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
		while (backupLookahead(matchNext(ParserImplConstants.STRICTFP, ParserImplConstants.AT, ParserImplConstants.ABSTRACT, ParserImplConstants.TRANSIENT, ParserImplConstants.PRIVATE, ParserImplConstants.FINAL, ParserImplConstants.STATIC, ParserImplConstants.VOLATILE, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.PROTECTED, ParserImplConstants.PUBLIC, ParserImplConstants.NATIVE))) {
			if (backupLookahead(matchNext(ParserImplConstants.PUBLIC))) {
				parse(ParserImplConstants.PUBLIC);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Public));
			} else if (backupLookahead(matchNext(ParserImplConstants.PROTECTED))) {
				parse(ParserImplConstants.PROTECTED);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Protected));
			} else if (backupLookahead(matchNext(ParserImplConstants.PRIVATE))) {
				parse(ParserImplConstants.PRIVATE);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Private));
			} else if (backupLookahead(matchNext(ParserImplConstants.ABSTRACT))) {
				parse(ParserImplConstants.ABSTRACT);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Abstract));
			} else if (backupLookahead(matchNext(ParserImplConstants.STATIC))) {
				parse(ParserImplConstants.STATIC);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Static));
			} else if (backupLookahead(matchNext(ParserImplConstants.FINAL))) {
				parse(ParserImplConstants.FINAL);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Final));
			} else if (backupLookahead(matchNext(ParserImplConstants.TRANSIENT))) {
				parse(ParserImplConstants.TRANSIENT);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Transient));
			} else if (backupLookahead(matchNext(ParserImplConstants.VOLATILE))) {
				parse(ParserImplConstants.VOLATILE);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Volatile));
			} else if (backupLookahead(matchNext(ParserImplConstants.SYNCHRONIZED))) {
				parse(ParserImplConstants.SYNCHRONIZED);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Synchronized));
			} else if (backupLookahead(matchNext(ParserImplConstants.NATIVE))) {
				parse(ParserImplConstants.NATIVE);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Native));
			} else if (backupLookahead(matchNext(ParserImplConstants.STRICTFP))) {
				parse(ParserImplConstants.STRICTFP);
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
		if (backupLookahead(matchNext(ParserImplConstants.SEMICOLON))) {
			parse(ParserImplConstants.SEMICOLON);
			ret = dress(SEmptyTypeDecl.make());
		} else {
			modifiers = parseModifiers();
			if (backupLookahead(matchNext(ParserImplConstants.CLASS, ParserImplConstants.INTERFACE))) {
				ret = parseClassOrInterfaceDecl(modifiers);
			} else if (backupLookahead(matchNext(ParserImplConstants.ENUM))) {
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
		if (backupLookahead(matchNext(ParserImplConstants.CLASS))) {
			parse(ParserImplConstants.CLASS);
			typeKind = TypeKind.Class;
			name = parseName();
			if (backupLookahead(matchNext(ParserImplConstants.LT))) {
				typeParams = parseTypeParameters();
			}
			if (backupLookahead(matchNext(ParserImplConstants.EXTENDS))) {
				parse(ParserImplConstants.EXTENDS);
				superClassType = parseAnnotatedQualifiedType();
			}
			if (backupLookahead(matchNext(ParserImplConstants.IMPLEMENTS))) {
				implementsClause = parseImplementsList(typeKind, problem);
			}
		} else {
			parse(ParserImplConstants.INTERFACE);
			typeKind = TypeKind.Interface;
			name = parseName();
			if (backupLookahead(matchNext(ParserImplConstants.LT))) {
				typeParams = parseTypeParameters();
			}
			if (backupLookahead(matchNext(ParserImplConstants.EXTENDS))) {
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
		parse(ParserImplConstants.EXTENDS);
		if (backupLookahead(matchNext(ParserImplConstants.AT, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER))) {
			cit = parseAnnotatedQualifiedType();
			ret = append(ret, cit);
			while (backupLookahead(matchNext(ParserImplConstants.COMMA))) {
				parse(ParserImplConstants.COMMA);
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
		parse(ParserImplConstants.IMPLEMENTS);
		if (backupLookahead(matchNext(ParserImplConstants.AT, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER))) {
			cit = parseAnnotatedQualifiedType();
			ret = append(ret, cit);
			while (backupLookahead(matchNext(ParserImplConstants.COMMA))) {
				parse(ParserImplConstants.COMMA);
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
		parse(ParserImplConstants.ENUM);
		name = parseName();
		if (backupLookahead(matchNext(ParserImplConstants.IMPLEMENTS))) {
			implementsClause = parseImplementsList(TypeKind.Enum, problem);
		}
		parse(ParserImplConstants.LBRACE);
		if (backupLookahead(matchNext(ParserImplConstants.NODE_VARIABLE, ParserImplConstants.VOLATILE, ParserImplConstants.PROTECTED, ParserImplConstants.PUBLIC, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_LIST_VARIABLE, ParserImplConstants.STRICTFP, ParserImplConstants.AT, ParserImplConstants.ABSTRACT, ParserImplConstants.PRIVATE, ParserImplConstants.TRANSIENT, ParserImplConstants._DEFAULT, ParserImplConstants.FINAL, ParserImplConstants.STATIC, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.NATIVE))) {
			if (backupLookahead(matchNext(ParserImplConstants.NODE_VARIABLE, ParserImplConstants.VOLATILE, ParserImplConstants.PROTECTED, ParserImplConstants.PUBLIC, ParserImplConstants.IDENTIFIER, ParserImplConstants.STRICTFP, ParserImplConstants.AT, ParserImplConstants.ABSTRACT, ParserImplConstants.PRIVATE, ParserImplConstants.TRANSIENT, ParserImplConstants._DEFAULT, ParserImplConstants.FINAL, ParserImplConstants.STATIC, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.NATIVE))) {
				entry = parseEnumConstantDecl();
				constants = append(constants, entry);
				while (backupLookahead(matchNext(ParserImplConstants.COMMA))) {
					parse(ParserImplConstants.COMMA);
					entry = parseEnumConstantDecl();
					constants = append(constants, entry);
				}
			} else {
				constants = parseNodeListVar();
			}
		}
		if (backupLookahead(matchNext(ParserImplConstants.COMMA))) {
			parse(ParserImplConstants.COMMA);
			trailingComma = true;
		}
		if (backupLookahead(matchNext(ParserImplConstants.SEMICOLON))) {
			parse(ParserImplConstants.SEMICOLON);
			members = parseClassOrInterfaceBodyDecls(TypeKind.Enum);
		}
		parse(ParserImplConstants.RBRACE);
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
		if (backupLookahead(matchNext(ParserImplConstants.LPAREN))) {
			args = parseArguments();
		}
		if (backupLookahead(matchNext(ParserImplConstants.LBRACE))) {
			classBody = parseClassOrInterfaceBody(TypeKind.Class);
		}
		return dress(SEnumConstantDecl.make(modifiers, name, optionOf(args), optionOf(classBody)));
	}

	public BUTree<SAnnotationDecl> parseAnnotationTypeDecl(BUTree<SNodeList> modifiers) throws ParseException {
		BUTree<SName> name;
		BUTree<SNodeList> members;
		parse(ParserImplConstants.AT);
		parse(ParserImplConstants.INTERFACE);
		name = parseName();
		members = parseAnnotationTypeBody();
		return dress(SAnnotationDecl.make(modifiers, name, members));
	}

	public BUTree<SNodeList> parseAnnotationTypeBody() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<? extends SMemberDecl> member;
		parse(ParserImplConstants.LBRACE);
		if (backupLookahead(matchNext(ParserImplConstants.ENUM, ParserImplConstants.BYTE, ParserImplConstants.LONG, ParserImplConstants.PUBLIC, ParserImplConstants.ABSTRACT, ParserImplConstants.TRANSIENT, ParserImplConstants._DEFAULT, ParserImplConstants.FINAL, ParserImplConstants.STATIC, ParserImplConstants.INT, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.SHORT, ParserImplConstants.CLASS, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.FLOAT, ParserImplConstants.VOLATILE, ParserImplConstants.SEMICOLON, ParserImplConstants.PROTECTED, ParserImplConstants.IDENTIFIER, ParserImplConstants.DOUBLE, ParserImplConstants.STRICTFP, ParserImplConstants.NODE_LIST_VARIABLE, ParserImplConstants.AT, ParserImplConstants.PRIVATE, ParserImplConstants.CHAR, ParserImplConstants.INTERFACE, ParserImplConstants.BOOLEAN, ParserImplConstants.NATIVE))) {
			if (backupLookahead(matchNext(ParserImplConstants.ENUM, ParserImplConstants.BYTE, ParserImplConstants.LONG, ParserImplConstants.PUBLIC, ParserImplConstants.ABSTRACT, ParserImplConstants.TRANSIENT, ParserImplConstants._DEFAULT, ParserImplConstants.FINAL, ParserImplConstants.STATIC, ParserImplConstants.INT, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.SHORT, ParserImplConstants.CLASS, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.FLOAT, ParserImplConstants.VOLATILE, ParserImplConstants.SEMICOLON, ParserImplConstants.PROTECTED, ParserImplConstants.DOUBLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.STRICTFP, ParserImplConstants.AT, ParserImplConstants.PRIVATE, ParserImplConstants.CHAR, ParserImplConstants.INTERFACE, ParserImplConstants.BOOLEAN, ParserImplConstants.NATIVE))) {
				do {
					member = parseAnnotationTypeBodyDecl();
					ret = append(ret, member);
				} while (backupLookahead(matchNext(ParserImplConstants.ENUM, ParserImplConstants.BYTE, ParserImplConstants.LONG, ParserImplConstants.PUBLIC, ParserImplConstants.ABSTRACT, ParserImplConstants.TRANSIENT, ParserImplConstants._DEFAULT, ParserImplConstants.FINAL, ParserImplConstants.STATIC, ParserImplConstants.INT, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.SHORT, ParserImplConstants.CLASS, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.FLOAT, ParserImplConstants.VOLATILE, ParserImplConstants.SEMICOLON, ParserImplConstants.PROTECTED, ParserImplConstants.DOUBLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.STRICTFP, ParserImplConstants.AT, ParserImplConstants.PRIVATE, ParserImplConstants.CHAR, ParserImplConstants.INTERFACE, ParserImplConstants.BOOLEAN, ParserImplConstants.NATIVE)));
			} else {
				ret = parseNodeListVar();
			}
		}
		parse(ParserImplConstants.RBRACE);
		return ret;
	}

	public BUTree<? extends SMemberDecl> parseAnnotationTypeBodyDecl() throws ParseException {
		BUTree<SNodeList> modifiers;
		BUTree<? extends SMemberDecl> ret;
		run();
		if (backupLookahead(matchNext(ParserImplConstants.SEMICOLON))) {
			parse(ParserImplConstants.SEMICOLON);
			ret = dress(SEmptyTypeDecl.make());
		} else {
			modifiers = parseModifiers();
			if (backupLookahead(matchAnnotationTypeBodyDecl1())) {
				ret = parseAnnotationTypeMemberDecl(modifiers);
			} else if (backupLookahead(matchNext(ParserImplConstants.CLASS, ParserImplConstants.INTERFACE))) {
				ret = parseClassOrInterfaceDecl(modifiers);
			} else if (backupLookahead(matchNext(ParserImplConstants.ENUM))) {
				ret = parseEnumDecl(modifiers);
			} else if (backupLookahead(matchNext(ParserImplConstants.AT))) {
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
		if (!match(ParserImplConstants.LPAREN))
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
		parse(ParserImplConstants.LPAREN);
		parse(ParserImplConstants.RPAREN);
		dims = parseArrayDims();
		if (backupLookahead(matchNext(ParserImplConstants._DEFAULT))) {
			parse(ParserImplConstants._DEFAULT);
			val = parseMemberValue();
			defaultVal = optionOf(val);
		}
		parse(ParserImplConstants.SEMICOLON);
		return dress(SAnnotationMemberDecl.make(modifiers, type, name, dims, defaultVal));
	}

	public BUTree<SNodeList> parseTypeParameters() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<STypeParameter> tp;
		parse(ParserImplConstants.LT);
		if (backupLookahead(matchNext(ParserImplConstants.AT, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER))) {
			tp = parseTypeParameter();
			ret = append(ret, tp);
			while (backupLookahead(matchNext(ParserImplConstants.COMMA))) {
				parse(ParserImplConstants.COMMA);
				tp = parseTypeParameter();
				ret = append(ret, tp);
			}
		} else {
			ret = parseNodeListVar();
		}
		parse(ParserImplConstants.GT);
		return ret;
	}

	public BUTree<STypeParameter> parseTypeParameter() throws ParseException {
		BUTree<SNodeList> annotations = null;
		BUTree<SName> name;
		BUTree<SNodeList> typeBounds = null;
		run();
		annotations = parseAnnotations();
		name = parseName();
		if (backupLookahead(matchNext(ParserImplConstants.EXTENDS))) {
			typeBounds = parseTypeBounds();
		}
		return dress(STypeParameter.make(annotations, name, ensureNotNull(typeBounds)));
	}

	public BUTree<SNodeList> parseTypeBounds() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<SQualifiedType> cit;
		BUTree<SNodeList> annotations = null;
		parse(ParserImplConstants.EXTENDS);
		if (backupLookahead(matchNext(ParserImplConstants.AT, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER))) {
			cit = parseAnnotatedQualifiedType();
			ret = append(ret, cit);
			while (backupLookahead(matchNext(ParserImplConstants.BIT_AND))) {
				parse(ParserImplConstants.BIT_AND);
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
		parse(ParserImplConstants.LBRACE);
		ret = parseClassOrInterfaceBodyDecls(typeKind);
		parse(ParserImplConstants.RBRACE);
		return ret;
	}

	public BUTree<SNodeList> parseClassOrInterfaceBodyDecls(TypeKind typeKind) throws ParseException {
		BUTree<? extends SMemberDecl> member;
		BUTree<SNodeList> ret = emptyList();
		if (backupLookahead(matchNext(ParserImplConstants.ENUM, ParserImplConstants.BYTE, ParserImplConstants.VOID, ParserImplConstants.LONG, ParserImplConstants.PUBLIC, ParserImplConstants.ABSTRACT, ParserImplConstants.TRANSIENT, ParserImplConstants._DEFAULT, ParserImplConstants.FINAL, ParserImplConstants.STATIC, ParserImplConstants.INT, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.SHORT, ParserImplConstants.CLASS, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.FLOAT, ParserImplConstants.LBRACE, ParserImplConstants.VOLATILE, ParserImplConstants.SEMICOLON, ParserImplConstants.PROTECTED, ParserImplConstants.DOUBLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.STRICTFP, ParserImplConstants.NODE_LIST_VARIABLE, ParserImplConstants.AT, ParserImplConstants.PRIVATE, ParserImplConstants.CHAR, ParserImplConstants.INTERFACE, ParserImplConstants.BOOLEAN, ParserImplConstants.NATIVE))) {
			if (backupLookahead(matchNext(ParserImplConstants.ENUM, ParserImplConstants.BYTE, ParserImplConstants.VOID, ParserImplConstants.LONG, ParserImplConstants.PUBLIC, ParserImplConstants.ABSTRACT, ParserImplConstants.TRANSIENT, ParserImplConstants._DEFAULT, ParserImplConstants.FINAL, ParserImplConstants.STATIC, ParserImplConstants.INT, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.SHORT, ParserImplConstants.CLASS, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.FLOAT, ParserImplConstants.LBRACE, ParserImplConstants.VOLATILE, ParserImplConstants.SEMICOLON, ParserImplConstants.PROTECTED, ParserImplConstants.IDENTIFIER, ParserImplConstants.DOUBLE, ParserImplConstants.STRICTFP, ParserImplConstants.AT, ParserImplConstants.PRIVATE, ParserImplConstants.CHAR, ParserImplConstants.INTERFACE, ParserImplConstants.BOOLEAN, ParserImplConstants.NATIVE))) {
				do {
					member = parseClassOrInterfaceBodyDecl(typeKind);
					ret = append(ret, member);
				} while (backupLookahead(matchNext(ParserImplConstants.ENUM, ParserImplConstants.BYTE, ParserImplConstants.VOID, ParserImplConstants.LONG, ParserImplConstants.PUBLIC, ParserImplConstants.ABSTRACT, ParserImplConstants.TRANSIENT, ParserImplConstants._DEFAULT, ParserImplConstants.FINAL, ParserImplConstants.STATIC, ParserImplConstants.INT, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.SHORT, ParserImplConstants.CLASS, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.FLOAT, ParserImplConstants.LBRACE, ParserImplConstants.VOLATILE, ParserImplConstants.SEMICOLON, ParserImplConstants.PROTECTED, ParserImplConstants.IDENTIFIER, ParserImplConstants.DOUBLE, ParserImplConstants.STRICTFP, ParserImplConstants.AT, ParserImplConstants.PRIVATE, ParserImplConstants.CHAR, ParserImplConstants.INTERFACE, ParserImplConstants.BOOLEAN, ParserImplConstants.NATIVE)));
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
		if (backupLookahead(matchNext(ParserImplConstants.SEMICOLON))) {
			parse(ParserImplConstants.SEMICOLON);
			ret = dress(SEmptyMemberDecl.make());
		} else {
			modifiers = parseModifiers();
			if (modifiers != null && contains(modifiers, SModifier.make(ModifierKeyword.Default)) && typeKind != TypeKind.Interface) problem = new BUProblem(Severity.ERROR, "Only interfaces can have default members");

			if (backupLookahead(matchNext(ParserImplConstants.LBRACE))) {
				ret = parseInitializerDecl(modifiers);
				if (typeKind == TypeKind.Interface) ret = ret.withProblem(new BUProblem(Severity.ERROR, "An interface cannot have initializers"));

			} else if (backupLookahead(matchNext(ParserImplConstants.CLASS, ParserImplConstants.INTERFACE))) {
				ret = parseClassOrInterfaceDecl(modifiers);
			} else if (backupLookahead(matchNext(ParserImplConstants.ENUM))) {
				ret = parseEnumDecl(modifiers);
			} else if (backupLookahead(matchNext(ParserImplConstants.AT))) {
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
		if (matchNext(ParserImplConstants.LT)) {
			if (!matchTypeParameters())
				return false;
		}
		if (!matchName())
			return false;
		if (!match(ParserImplConstants.LPAREN))
			return false;
		return true;
	}

	private boolean matchClassOrInterfaceBodyDecl2() {
		if (!matchType())
			return false;
		if (!matchName())
			return false;
		while (matchNext(ParserImplConstants.LBRACKET)) {
			if (!match(ParserImplConstants.LBRACKET))
				return false;
			if (!match(ParserImplConstants.RBRACKET))
				return false;
		}
		if (matchNext(ParserImplConstants.COMMA)) {
			if (!match(ParserImplConstants.COMMA))
				return false;
		} else if (matchNext(ParserImplConstants.ASSIGN)) {
			if (!match(ParserImplConstants.ASSIGN))
				return false;
		} else if (matchNext(ParserImplConstants.SEMICOLON)) {
			if (!match(ParserImplConstants.SEMICOLON))
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
		parse(ParserImplConstants.SEMICOLON);
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
		while (backupLookahead(matchNext(ParserImplConstants.COMMA))) {
			parse(ParserImplConstants.COMMA);
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
		if (backupLookahead(matchNext(ParserImplConstants.ASSIGN))) {
			parse(ParserImplConstants.ASSIGN);
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
			parse(ParserImplConstants.LBRACKET);
			parse(ParserImplConstants.RBRACKET);
			arrayDims = append(arrayDims, dress(SArrayDim.make(annotations)));
		}
		return arrayDims;
	}

	private boolean matchArrayDims1() {
		if (!matchAnnotations())
			return false;
		if (!match(ParserImplConstants.LBRACKET))
			return false;
		if (!match(ParserImplConstants.RBRACKET))
			return false;
		return true;
	}

	public BUTree<? extends SExpr> parseVariableInitializer() throws ParseException {
		BUTree<? extends SExpr> ret;
		if (backupLookahead(matchNext(ParserImplConstants.LBRACE))) {
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
		parse(ParserImplConstants.LBRACE);
		if (backupLookahead(matchNext(ParserImplConstants.LONG_LITERAL, ParserImplConstants.BYTE, ParserImplConstants.TRUE, ParserImplConstants.SUPER, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.LPAREN, ParserImplConstants.VOID, ParserImplConstants.LONG, ParserImplConstants.PLUS, ParserImplConstants.BANG, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.THIS, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.INT, ParserImplConstants.DECR, ParserImplConstants.SHORT, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.FLOAT, ParserImplConstants.LBRACE, ParserImplConstants.NEW, ParserImplConstants.IDENTIFIER, ParserImplConstants.DOUBLE, ParserImplConstants.FALSE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.TILDE, ParserImplConstants.CHAR, ParserImplConstants.INCR, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.MINUS, ParserImplConstants.BOOLEAN, ParserImplConstants.NULL))) {
			val = parseVariableInitializer();
			values = append(values, val);
			while (backupLookahead(matchNext(ParserImplConstants.COMMA))) {
				parse(ParserImplConstants.COMMA);
				val = parseVariableInitializer();
				values = append(values, val);
			}
		}
		if (backupLookahead(matchNext(ParserImplConstants.COMMA))) {
			parse(ParserImplConstants.COMMA);
			trailingComma = true;
		}
		parse(ParserImplConstants.RBRACE);
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
		if (backupLookahead(matchNext(ParserImplConstants.LT))) {
			typeParameters = parseTypeParameters();
		}
		type = parseResultType();
		name = parseName();
		parameters = parseFormalParameters();
		arrayDims = parseArrayDims();
		if (backupLookahead(matchNext(ParserImplConstants.THROWS))) {
			throwsClause = parseThrowsClause();
		}
		if (backupLookahead(matchNext(ParserImplConstants.LBRACE))) {
			block = parseBlock();
		} else {
			parse(ParserImplConstants.SEMICOLON);
			if (modifiers != null && contains(modifiers, SModifier.make(ModifierKeyword.Default))) problem = new BUProblem(Severity.ERROR, "Default methods must have a body");

		}
		return dress(SMethodDecl.make(modifiers, ensureNotNull(typeParameters), type, name, parameters, arrayDims, ensureNotNull(throwsClause), optionOf(block))).withProblem(problem);
	}

	public BUTree<SNodeList> parseFormalParameters() throws ParseException {
		BUTree<SNodeList> ret = null;
		BUTree<SFormalParameter> par;
		parse(ParserImplConstants.LPAREN);
		if (backupLookahead(matchNext(ParserImplConstants.NODE_VARIABLE, ParserImplConstants.BYTE, ParserImplConstants.FLOAT, ParserImplConstants.VOLATILE, ParserImplConstants.PROTECTED, ParserImplConstants.PUBLIC, ParserImplConstants.LONG, ParserImplConstants.IDENTIFIER, ParserImplConstants.DOUBLE, ParserImplConstants.NODE_LIST_VARIABLE, ParserImplConstants.STRICTFP, ParserImplConstants.AT, ParserImplConstants.ABSTRACT, ParserImplConstants.PRIVATE, ParserImplConstants.TRANSIENT, ParserImplConstants._DEFAULT, ParserImplConstants.FINAL, ParserImplConstants.CHAR, ParserImplConstants.STATIC, ParserImplConstants.INT, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.SHORT, ParserImplConstants.BOOLEAN, ParserImplConstants.NATIVE))) {
			ret = parseFormalParameterList();
		}
		parse(ParserImplConstants.RPAREN);
		return ensureNotNull(ret);
	}

	public BUTree<SNodeList> parseFormalParameterList() throws ParseException {
		BUTree<SNodeList> ret = null;
		BUTree<SFormalParameter> par;
		if (backupLookahead(matchNext(ParserImplConstants.NODE_VARIABLE, ParserImplConstants.BYTE, ParserImplConstants.FLOAT, ParserImplConstants.VOLATILE, ParserImplConstants.PROTECTED, ParserImplConstants.PUBLIC, ParserImplConstants.LONG, ParserImplConstants.DOUBLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.STRICTFP, ParserImplConstants.AT, ParserImplConstants.ABSTRACT, ParserImplConstants.PRIVATE, ParserImplConstants.TRANSIENT, ParserImplConstants._DEFAULT, ParserImplConstants.FINAL, ParserImplConstants.CHAR, ParserImplConstants.STATIC, ParserImplConstants.INT, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.SHORT, ParserImplConstants.BOOLEAN, ParserImplConstants.NATIVE))) {
			par = parseFormalParameter();
			ret = append(ret, par);
			while (backupLookahead(matchNext(ParserImplConstants.COMMA))) {
				parse(ParserImplConstants.COMMA);
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
		if (backupLookahead(matchNext(ParserImplConstants.ELLIPSIS))) {
			parse(ParserImplConstants.ELLIPSIS);
			isVarArg = true;
		}
		id = parseVariableDeclaratorId();
		return dress(SFormalParameter.make(modifiers, type, isVarArg, id));
	}

	public BUTree<SNodeList> parseThrowsClause() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<SQualifiedType> cit;
		parse(ParserImplConstants.THROWS);
		cit = parseAnnotatedQualifiedType();
		ret = append(ret, cit);
		while (backupLookahead(matchNext(ParserImplConstants.COMMA))) {
			parse(ParserImplConstants.COMMA);
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
		if (backupLookahead(matchNext(ParserImplConstants.LT))) {
			typeParameters = parseTypeParameters();
		}
		name = parseName();
		parameters = parseFormalParameters();
		if (backupLookahead(matchNext(ParserImplConstants.THROWS))) {
			throwsClause = parseThrowsClause();
		}
		run();
		parse(ParserImplConstants.LBRACE);
		if (backupLookahead(matchNext(ParserImplConstants.LONG_LITERAL, ParserImplConstants.BYTE, ParserImplConstants.TRUE, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.LPAREN, ParserImplConstants.VOID, ParserImplConstants.LONG, ParserImplConstants.FINAL, ParserImplConstants.THIS, ParserImplConstants.INT, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.RETURN, ParserImplConstants.CLASS, ParserImplConstants.LBRACE, ParserImplConstants.FLOAT, ParserImplConstants.VOLATILE, ParserImplConstants.SEMICOLON, ParserImplConstants.IDENTIFIER, ParserImplConstants.AT, ParserImplConstants.BREAK, ParserImplConstants.INTERFACE, ParserImplConstants.FOR, ParserImplConstants.BOOLEAN, ParserImplConstants.NULL, ParserImplConstants.SWITCH, ParserImplConstants.ASSERT, ParserImplConstants.SUPER, ParserImplConstants.PUBLIC, ParserImplConstants.TRY, ParserImplConstants.IF, ParserImplConstants.WHILE, ParserImplConstants.TRANSIENT, ParserImplConstants.ABSTRACT, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.DO, ParserImplConstants.STATIC, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.DECR, ParserImplConstants.SHORT, ParserImplConstants.THROW, ParserImplConstants.LT, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.NEW, ParserImplConstants.PROTECTED, ParserImplConstants.CONTINUE, ParserImplConstants.DOUBLE, ParserImplConstants.FALSE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.STRICTFP, ParserImplConstants.NODE_LIST_VARIABLE, ParserImplConstants.PRIVATE, ParserImplConstants.CHAR, ParserImplConstants.INCR, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.NATIVE))) {
			if (backupLookahead(matchNext(ParserImplConstants.LONG_LITERAL, ParserImplConstants.BYTE, ParserImplConstants.TRUE, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.LPAREN, ParserImplConstants.VOID, ParserImplConstants.LONG, ParserImplConstants.FINAL, ParserImplConstants.THIS, ParserImplConstants.INT, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.RETURN, ParserImplConstants.CLASS, ParserImplConstants.LBRACE, ParserImplConstants.FLOAT, ParserImplConstants.VOLATILE, ParserImplConstants.SEMICOLON, ParserImplConstants.IDENTIFIER, ParserImplConstants.AT, ParserImplConstants.BREAK, ParserImplConstants.INTERFACE, ParserImplConstants.FOR, ParserImplConstants.BOOLEAN, ParserImplConstants.NULL, ParserImplConstants.SWITCH, ParserImplConstants.ASSERT, ParserImplConstants.SUPER, ParserImplConstants.PUBLIC, ParserImplConstants.TRY, ParserImplConstants.IF, ParserImplConstants.WHILE, ParserImplConstants.TRANSIENT, ParserImplConstants.ABSTRACT, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.DO, ParserImplConstants.STATIC, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.DECR, ParserImplConstants.SHORT, ParserImplConstants.THROW, ParserImplConstants.LT, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.NEW, ParserImplConstants.PROTECTED, ParserImplConstants.CONTINUE, ParserImplConstants.DOUBLE, ParserImplConstants.FALSE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.STRICTFP, ParserImplConstants.PRIVATE, ParserImplConstants.CHAR, ParserImplConstants.INCR, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.NATIVE))) {
				if (backupLookahead(matchConstructorDecl1())) {
					stmt = parseExplicitConstructorInvocation();
					stmts = append(stmts, stmt);
				} else {
					stmt = parseBlockStatement();
					stmts = append(stmts, stmt);
				}
				while (backupLookahead(matchNext(ParserImplConstants.LONG_LITERAL, ParserImplConstants.BYTE, ParserImplConstants.TRUE, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.LPAREN, ParserImplConstants.VOID, ParserImplConstants.LONG, ParserImplConstants.FINAL, ParserImplConstants.THIS, ParserImplConstants.INT, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.RETURN, ParserImplConstants.CLASS, ParserImplConstants.LBRACE, ParserImplConstants.FLOAT, ParserImplConstants.VOLATILE, ParserImplConstants.SEMICOLON, ParserImplConstants.IDENTIFIER, ParserImplConstants.AT, ParserImplConstants.BREAK, ParserImplConstants.INTERFACE, ParserImplConstants.FOR, ParserImplConstants.BOOLEAN, ParserImplConstants.NULL, ParserImplConstants.SWITCH, ParserImplConstants.ASSERT, ParserImplConstants.SUPER, ParserImplConstants.PUBLIC, ParserImplConstants.TRY, ParserImplConstants.IF, ParserImplConstants.WHILE, ParserImplConstants.TRANSIENT, ParserImplConstants.ABSTRACT, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.DO, ParserImplConstants.STATIC, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.DECR, ParserImplConstants.SHORT, ParserImplConstants.THROW, ParserImplConstants.LT, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.NEW, ParserImplConstants.PROTECTED, ParserImplConstants.CONTINUE, ParserImplConstants.DOUBLE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.FALSE, ParserImplConstants.STRICTFP, ParserImplConstants.PRIVATE, ParserImplConstants.CHAR, ParserImplConstants.INCR, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.NATIVE))) {
					stmt = parseBlockStatement();
					stmts = append(stmts, stmt);
				}
			} else {
				stmts = parseNodeListVar();
			}
		}
		parse(ParserImplConstants.RBRACE);
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
			if (backupLookahead(matchNext(ParserImplConstants.LT))) {
				typeArgs = parseTypeArguments();
			}
			parse(ParserImplConstants.THIS);
			isThis = true;
			args = parseArguments();
			parse(ParserImplConstants.SEMICOLON);
		} else {
			if (backupLookahead(matchExplicitConstructorInvocation2())) {
				expr = parsePrimaryExpressionWithoutSuperSuffix();
				parse(ParserImplConstants.DOT);
			}
			if (backupLookahead(matchNext(ParserImplConstants.LT))) {
				typeArgs = parseTypeArguments();
			}
			parse(ParserImplConstants.SUPER);
			args = parseArguments();
			parse(ParserImplConstants.SEMICOLON);
		}
		return dress(SExplicitConstructorInvocationStmt.make(ensureNotNull(typeArgs), isThis, optionOf(expr), args));
	}

	private boolean matchExplicitConstructorInvocation1() {
		if (matchNext(ParserImplConstants.LT)) {
			if (!matchTypeArguments())
				return false;
		}
		if (!match(ParserImplConstants.THIS))
			return false;
		if (!match(ParserImplConstants.LPAREN))
			return false;
		return true;
	}

	private boolean matchExplicitConstructorInvocation2() {
		if (!matchPrimaryExpressionWithoutSuperSuffix())
			return false;
		if (!match(ParserImplConstants.DOT))
			return false;
		return true;
	}

	public BUTree<SNodeList> parseStatements() throws ParseException {
		BUTree<SNodeList> ret = null;
		BUTree<? extends SStmt> stmt;
		if (backupLookahead(matchNext(ParserImplConstants.LONG_LITERAL, ParserImplConstants.BYTE, ParserImplConstants.TRUE, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.LPAREN, ParserImplConstants.VOID, ParserImplConstants.LONG, ParserImplConstants.FINAL, ParserImplConstants.THIS, ParserImplConstants.INT, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.RETURN, ParserImplConstants.CLASS, ParserImplConstants.LBRACE, ParserImplConstants.FLOAT, ParserImplConstants.VOLATILE, ParserImplConstants.SEMICOLON, ParserImplConstants.IDENTIFIER, ParserImplConstants.AT, ParserImplConstants.BREAK, ParserImplConstants.INTERFACE, ParserImplConstants.FOR, ParserImplConstants.BOOLEAN, ParserImplConstants.NULL, ParserImplConstants.SWITCH, ParserImplConstants.ASSERT, ParserImplConstants.SUPER, ParserImplConstants.PUBLIC, ParserImplConstants.TRY, ParserImplConstants.IF, ParserImplConstants.WHILE, ParserImplConstants.TRANSIENT, ParserImplConstants.ABSTRACT, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.DO, ParserImplConstants.STATIC, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.DECR, ParserImplConstants.SHORT, ParserImplConstants.THROW, ParserImplConstants.LT, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.NEW, ParserImplConstants.PROTECTED, ParserImplConstants.CONTINUE, ParserImplConstants.DOUBLE, ParserImplConstants.FALSE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.STRICTFP, ParserImplConstants.NODE_LIST_VARIABLE, ParserImplConstants.PRIVATE, ParserImplConstants.CHAR, ParserImplConstants.INCR, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.NATIVE))) {
			if (backupLookahead(matchNext(ParserImplConstants.LONG_LITERAL, ParserImplConstants.BYTE, ParserImplConstants.TRUE, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.LPAREN, ParserImplConstants.VOID, ParserImplConstants.LONG, ParserImplConstants.FINAL, ParserImplConstants.THIS, ParserImplConstants.INT, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.RETURN, ParserImplConstants.CLASS, ParserImplConstants.LBRACE, ParserImplConstants.FLOAT, ParserImplConstants.VOLATILE, ParserImplConstants.SEMICOLON, ParserImplConstants.IDENTIFIER, ParserImplConstants.AT, ParserImplConstants.BREAK, ParserImplConstants.INTERFACE, ParserImplConstants.FOR, ParserImplConstants.BOOLEAN, ParserImplConstants.NULL, ParserImplConstants.SWITCH, ParserImplConstants.ASSERT, ParserImplConstants.SUPER, ParserImplConstants.PUBLIC, ParserImplConstants.TRY, ParserImplConstants.IF, ParserImplConstants.WHILE, ParserImplConstants.TRANSIENT, ParserImplConstants.ABSTRACT, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.DO, ParserImplConstants.STATIC, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.DECR, ParserImplConstants.SHORT, ParserImplConstants.THROW, ParserImplConstants.LT, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.NEW, ParserImplConstants.PROTECTED, ParserImplConstants.CONTINUE, ParserImplConstants.DOUBLE, ParserImplConstants.FALSE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.STRICTFP, ParserImplConstants.PRIVATE, ParserImplConstants.CHAR, ParserImplConstants.INCR, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.NATIVE))) {
				do {
					stmt = parseBlockStatement();
					ret = append(ret, stmt);
				} while (backupLookahead(matchNext(ParserImplConstants.LONG_LITERAL, ParserImplConstants.BYTE, ParserImplConstants.TRUE, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.LPAREN, ParserImplConstants.VOID, ParserImplConstants.LONG, ParserImplConstants.FINAL, ParserImplConstants.THIS, ParserImplConstants.INT, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.RETURN, ParserImplConstants.CLASS, ParserImplConstants.LBRACE, ParserImplConstants.FLOAT, ParserImplConstants.VOLATILE, ParserImplConstants.SEMICOLON, ParserImplConstants.IDENTIFIER, ParserImplConstants.AT, ParserImplConstants.BREAK, ParserImplConstants.INTERFACE, ParserImplConstants.FOR, ParserImplConstants.BOOLEAN, ParserImplConstants.NULL, ParserImplConstants.SWITCH, ParserImplConstants.ASSERT, ParserImplConstants.SUPER, ParserImplConstants.PUBLIC, ParserImplConstants.TRY, ParserImplConstants.IF, ParserImplConstants.WHILE, ParserImplConstants.TRANSIENT, ParserImplConstants.ABSTRACT, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.DO, ParserImplConstants.STATIC, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.DECR, ParserImplConstants.SHORT, ParserImplConstants.THROW, ParserImplConstants.LT, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.NEW, ParserImplConstants.PROTECTED, ParserImplConstants.CONTINUE, ParserImplConstants.DOUBLE, ParserImplConstants.FALSE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.STRICTFP, ParserImplConstants.PRIVATE, ParserImplConstants.CHAR, ParserImplConstants.INCR, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.NATIVE)));
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
		if (backupLookahead(matchNext(ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.FLOAT, ParserImplConstants.INT, ParserImplConstants.LONG, ParserImplConstants.SHORT, ParserImplConstants.BOOLEAN, ParserImplConstants.DOUBLE))) {
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
		if (!match(ParserImplConstants.LBRACKET))
			return false;
		return true;
	}

	private boolean matchType2() {
		if (!matchAnnotations())
			return false;
		if (!match(ParserImplConstants.LBRACKET))
			return false;
		return true;
	}

	public BUTree<? extends SReferenceType> parseReferenceType(BUTree<SNodeList> annotations) throws ParseException {
		BUTree<? extends SType> primitiveType;
		BUTree<? extends SReferenceType> type;
		BUTree<SNodeList> arrayDims;
		if (backupLookahead(matchNext(ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.FLOAT, ParserImplConstants.INT, ParserImplConstants.LONG, ParserImplConstants.SHORT, ParserImplConstants.BOOLEAN, ParserImplConstants.DOUBLE))) {
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
		if (!match(ParserImplConstants.LBRACKET))
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
		if (backupLookahead(matchNext(ParserImplConstants.LT))) {
			typeArgs = parseTypeArgumentsOrDiamond();
		}
		ret = dress(SQualifiedType.make(annotations, scope, name, optionOf(typeArgs)));
		while (backupLookahead(matchNext(ParserImplConstants.DOT))) {
			parse(ParserImplConstants.DOT);
			scope = optionOf(ret);
			lateRun();
			annotations = parseAnnotations();
			name = parseName();
			if (backupLookahead(matchNext(ParserImplConstants.LT))) {
				typeArgs = parseTypeArgumentsOrDiamond();
			}
			ret = dress(SQualifiedType.make(annotations, scope, name, optionOf(typeArgs)));
		}
		return ret;
	}

	public BUTree<SNodeList> parseTypeArguments() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<? extends SType> type;
		parse(ParserImplConstants.LT);
		if (backupLookahead(matchNext(ParserImplConstants.NODE_VARIABLE, ParserImplConstants.BYTE, ParserImplConstants.HOOK, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.DOUBLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_LIST_VARIABLE, ParserImplConstants.AT, ParserImplConstants.CHAR, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.BOOLEAN))) {
			ret = parseTypeArgumentList();
		}
		parse(ParserImplConstants.GT);
		return ret;
	}

	public BUTree<SNodeList> parseTypeArgumentsOrDiamond() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<? extends SType> type;
		parse(ParserImplConstants.LT);
		if (backupLookahead(matchNext(ParserImplConstants.NODE_VARIABLE, ParserImplConstants.BYTE, ParserImplConstants.HOOK, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.DOUBLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_LIST_VARIABLE, ParserImplConstants.AT, ParserImplConstants.CHAR, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.BOOLEAN))) {
			ret = parseTypeArgumentList();
		}
		parse(ParserImplConstants.GT);
		return ret;
	}

	public BUTree<SNodeList> parseTypeArgumentList() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<? extends SType> type;
		if (backupLookahead(matchNext(ParserImplConstants.AT, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.HOOK, ParserImplConstants.FLOAT, ParserImplConstants.INT, ParserImplConstants.LONG, ParserImplConstants.SHORT, ParserImplConstants.BOOLEAN, ParserImplConstants.DOUBLE, ParserImplConstants.IDENTIFIER))) {
			type = parseTypeArgument();
			ret = append(ret, type);
			while (backupLookahead(matchNext(ParserImplConstants.COMMA))) {
				parse(ParserImplConstants.COMMA);
				type = parseTypeArgument();
				ret = append(ret, type);
			}
			return ret;
		} else {
			parse(ParserImplConstants.NODE_LIST_VARIABLE);
			return makeVar();
		}
	}

	public BUTree<? extends SType> parseTypeArgument() throws ParseException {
		BUTree<? extends SType> ret;
		BUTree<SNodeList> annotations = null;
		run();
		annotations = parseAnnotations();
		if (backupLookahead(matchNext(ParserImplConstants.NODE_VARIABLE, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.FLOAT, ParserImplConstants.INT, ParserImplConstants.LONG, ParserImplConstants.SHORT, ParserImplConstants.BOOLEAN, ParserImplConstants.DOUBLE, ParserImplConstants.IDENTIFIER))) {
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
		parse(ParserImplConstants.HOOK);
		if (backupLookahead(matchNext(ParserImplConstants.EXTENDS, ParserImplConstants.SUPER))) {
			if (backupLookahead(matchNext(ParserImplConstants.EXTENDS))) {
				parse(ParserImplConstants.EXTENDS);
				run();
				boundAnnotations = parseAnnotations();
				ext = parseReferenceType(boundAnnotations);
			} else {
				parse(ParserImplConstants.SUPER);
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
		if (backupLookahead(matchNext(ParserImplConstants.BOOLEAN))) {
			parse(ParserImplConstants.BOOLEAN);
			primitive = Primitive.Boolean;
		} else if (backupLookahead(matchNext(ParserImplConstants.CHAR))) {
			parse(ParserImplConstants.CHAR);
			primitive = Primitive.Char;
		} else if (backupLookahead(matchNext(ParserImplConstants.BYTE))) {
			parse(ParserImplConstants.BYTE);
			primitive = Primitive.Byte;
		} else if (backupLookahead(matchNext(ParserImplConstants.SHORT))) {
			parse(ParserImplConstants.SHORT);
			primitive = Primitive.Short;
		} else if (backupLookahead(matchNext(ParserImplConstants.INT))) {
			parse(ParserImplConstants.INT);
			primitive = Primitive.Int;
		} else if (backupLookahead(matchNext(ParserImplConstants.LONG))) {
			parse(ParserImplConstants.LONG);
			primitive = Primitive.Long;
		} else if (backupLookahead(matchNext(ParserImplConstants.FLOAT))) {
			parse(ParserImplConstants.FLOAT);
			primitive = Primitive.Float;
		} else {
			parse(ParserImplConstants.DOUBLE);
			primitive = Primitive.Double;
		}
		return dress(SPrimitiveType.make(annotations, primitive));
	}

	public BUTree<? extends SType> parseResultType() throws ParseException {
		BUTree<? extends SType> ret;
		if (backupLookahead(matchNext(ParserImplConstants.VOID))) {
			run();
			parse(ParserImplConstants.VOID);
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
		while (backupLookahead(matchNext(ParserImplConstants.DOT))) {
			lateRun();
			parse(ParserImplConstants.DOT);
			qualifier = optionOf(ret);
			name = parseName();
			ret = dress(SQualifiedName.make(qualifier, name));
		}
		return ret;
	}

	public BUTree<SName> parseName() throws ParseException {
		BUTree<SName> name;
		if (backupLookahead(matchNext(ParserImplConstants.IDENTIFIER))) {
			run();
			name = dress(SName.make(parse(ParserImplConstants.IDENTIFIER).image));
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
			parse(ParserImplConstants.ARROW);
			ret = parseLambdaBody(singletonList(makeFormalParameter((BUTree<SName>) ret)), false);
		} else if (backupLookahead(matchExpression2())) {
			run();
			parse(ParserImplConstants.LPAREN);
			parse(ParserImplConstants.RPAREN);
			parse(ParserImplConstants.ARROW);
			ret = parseLambdaBody(emptyList(), true);
		} else if (backupLookahead(matchExpression3())) {
			run();
			parse(ParserImplConstants.LPAREN);
			ret = parseName();
			parse(ParserImplConstants.RPAREN);
			parse(ParserImplConstants.ARROW);
			ret = parseLambdaBody(singletonList(makeFormalParameter((BUTree<SName>) ret)), true);
		} else if (backupLookahead(matchExpression4())) {
			run();
			parse(ParserImplConstants.LPAREN);
			params = parseInferredFormalParameterList();
			parse(ParserImplConstants.RPAREN);
			parse(ParserImplConstants.ARROW);
			ret = parseLambdaBody(params, true);
		} else {
			ret = parseConditionalExpression();
			if (backupLookahead(matchNext(ParserImplConstants.XORASSIGN, ParserImplConstants.ORASSIGN, ParserImplConstants.RUNSIGNEDSHIFTASSIGN, ParserImplConstants.STARASSIGN, ParserImplConstants.SLASHASSIGN, ParserImplConstants.LSHIFTASSIGN, ParserImplConstants.RSIGNEDSHIFTASSIGN, ParserImplConstants.ASSIGN, ParserImplConstants.REMASSIGN, ParserImplConstants.MINUSASSIGN, ParserImplConstants.PLUSASSIGN, ParserImplConstants.ANDASSIGN))) {
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
		if (!match(ParserImplConstants.ARROW))
			return false;
		return true;
	}

	private boolean matchExpression2() {
		if (!match(ParserImplConstants.LPAREN))
			return false;
		if (!match(ParserImplConstants.RPAREN))
			return false;
		if (!match(ParserImplConstants.ARROW))
			return false;
		return true;
	}

	private boolean matchExpression3() {
		if (!match(ParserImplConstants.LPAREN))
			return false;
		if (!matchName())
			return false;
		if (!match(ParserImplConstants.RPAREN))
			return false;
		if (!match(ParserImplConstants.ARROW))
			return false;
		return true;
	}

	private boolean matchExpression4() {
		if (!match(ParserImplConstants.LPAREN))
			return false;
		if (!matchName())
			return false;
		if (!match(ParserImplConstants.COMMA))
			return false;
		return true;
	}

	public BUTree<SLambdaExpr> parseLambdaBody(BUTree<SNodeList> parameters, boolean parenthesis) throws ParseException {
		BUTree<SBlockStmt> block;
		BUTree<? extends SExpr> expr;
		BUTree<SLambdaExpr> ret;
		if (backupLookahead(matchNext(ParserImplConstants.LONG_LITERAL, ParserImplConstants.BYTE, ParserImplConstants.TRUE, ParserImplConstants.SUPER, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.LPAREN, ParserImplConstants.VOID, ParserImplConstants.LONG, ParserImplConstants.PLUS, ParserImplConstants.BANG, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.THIS, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.DECR, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.FLOAT, ParserImplConstants.NEW, ParserImplConstants.DOUBLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.STRING_LITERAL, ParserImplConstants.FALSE, ParserImplConstants.TILDE, ParserImplConstants.CHAR, ParserImplConstants.INCR, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.MINUS, ParserImplConstants.BOOLEAN, ParserImplConstants.NULL))) {
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
		while (backupLookahead(matchNext(ParserImplConstants.COMMA))) {
			parse(ParserImplConstants.COMMA);
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
		if (backupLookahead(matchNext(ParserImplConstants.ASSIGN))) {
			parse(ParserImplConstants.ASSIGN);
			ret = AssignOp.Normal;
		} else if (backupLookahead(matchNext(ParserImplConstants.STARASSIGN))) {
			parse(ParserImplConstants.STARASSIGN);
			ret = AssignOp.Times;
		} else if (backupLookahead(matchNext(ParserImplConstants.SLASHASSIGN))) {
			parse(ParserImplConstants.SLASHASSIGN);
			ret = AssignOp.Divide;
		} else if (backupLookahead(matchNext(ParserImplConstants.REMASSIGN))) {
			parse(ParserImplConstants.REMASSIGN);
			ret = AssignOp.Remainder;
		} else if (backupLookahead(matchNext(ParserImplConstants.PLUSASSIGN))) {
			parse(ParserImplConstants.PLUSASSIGN);
			ret = AssignOp.Plus;
		} else if (backupLookahead(matchNext(ParserImplConstants.MINUSASSIGN))) {
			parse(ParserImplConstants.MINUSASSIGN);
			ret = AssignOp.Minus;
		} else if (backupLookahead(matchNext(ParserImplConstants.LSHIFTASSIGN))) {
			parse(ParserImplConstants.LSHIFTASSIGN);
			ret = AssignOp.LeftShift;
		} else if (backupLookahead(matchNext(ParserImplConstants.RSIGNEDSHIFTASSIGN))) {
			parse(ParserImplConstants.RSIGNEDSHIFTASSIGN);
			ret = AssignOp.RightSignedShift;
		} else if (backupLookahead(matchNext(ParserImplConstants.RUNSIGNEDSHIFTASSIGN))) {
			parse(ParserImplConstants.RUNSIGNEDSHIFTASSIGN);
			ret = AssignOp.RightUnsignedShift;
		} else if (backupLookahead(matchNext(ParserImplConstants.ANDASSIGN))) {
			parse(ParserImplConstants.ANDASSIGN);
			ret = AssignOp.And;
		} else if (backupLookahead(matchNext(ParserImplConstants.XORASSIGN))) {
			parse(ParserImplConstants.XORASSIGN);
			ret = AssignOp.XOr;
		} else {
			parse(ParserImplConstants.ORASSIGN);
			ret = AssignOp.Or;
		}
		return ret;
	}

	public BUTree<? extends SExpr> parseConditionalExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> left;
		BUTree<? extends SExpr> right;
		ret = parseConditionalOrExpression();
		if (backupLookahead(matchNext(ParserImplConstants.HOOK))) {
			lateRun();
			parse(ParserImplConstants.HOOK);
			left = parseExpression();
			parse(ParserImplConstants.COLON);
			right = parseConditionalExpression();
			ret = dress(SConditionalExpr.make(ret, left, right));
		}
		return ret;
	}

	public BUTree<? extends SExpr> parseConditionalOrExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		ret = parseConditionalAndExpression();
		while (backupLookahead(matchNext(ParserImplConstants.SC_OR))) {
			lateRun();
			parse(ParserImplConstants.SC_OR);
			right = parseConditionalAndExpression();
			ret = dress(SBinaryExpr.make(ret, BinaryOp.Or, right));
		}
		return ret;
	}

	public BUTree<? extends SExpr> parseConditionalAndExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		ret = parseInclusiveOrExpression();
		while (backupLookahead(matchNext(ParserImplConstants.SC_AND))) {
			lateRun();
			parse(ParserImplConstants.SC_AND);
			right = parseInclusiveOrExpression();
			ret = dress(SBinaryExpr.make(ret, BinaryOp.And, right));
		}
		return ret;
	}

	public BUTree<? extends SExpr> parseInclusiveOrExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		ret = parseExclusiveOrExpression();
		while (backupLookahead(matchNext(ParserImplConstants.BIT_OR))) {
			lateRun();
			parse(ParserImplConstants.BIT_OR);
			right = parseExclusiveOrExpression();
			ret = dress(SBinaryExpr.make(ret, BinaryOp.BinOr, right));
		}
		return ret;
	}

	public BUTree<? extends SExpr> parseExclusiveOrExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		ret = parseAndExpression();
		while (backupLookahead(matchNext(ParserImplConstants.XOR))) {
			lateRun();
			parse(ParserImplConstants.XOR);
			right = parseAndExpression();
			ret = dress(SBinaryExpr.make(ret, BinaryOp.XOr, right));
		}
		return ret;
	}

	public BUTree<? extends SExpr> parseAndExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		ret = parseEqualityExpression();
		while (backupLookahead(matchNext(ParserImplConstants.BIT_AND))) {
			lateRun();
			parse(ParserImplConstants.BIT_AND);
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
		while (backupLookahead(matchNext(ParserImplConstants.NE, ParserImplConstants.EQ))) {
			lateRun();
			if (backupLookahead(matchNext(ParserImplConstants.EQ))) {
				parse(ParserImplConstants.EQ);
				op = BinaryOp.Equal;
			} else {
				parse(ParserImplConstants.NE);
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
		if (backupLookahead(matchNext(ParserImplConstants.INSTANCEOF))) {
			lateRun();
			parse(ParserImplConstants.INSTANCEOF);
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
		while (backupLookahead(matchNext(ParserImplConstants.LT, ParserImplConstants.LE, ParserImplConstants.GE, ParserImplConstants.GT))) {
			lateRun();
			if (backupLookahead(matchNext(ParserImplConstants.LT))) {
				parse(ParserImplConstants.LT);
				op = BinaryOp.Less;
			} else if (backupLookahead(matchNext(ParserImplConstants.GT))) {
				parse(ParserImplConstants.GT);
				op = BinaryOp.Greater;
			} else if (backupLookahead(matchNext(ParserImplConstants.LE))) {
				parse(ParserImplConstants.LE);
				op = BinaryOp.LessOrEqual;
			} else {
				parse(ParserImplConstants.GE);
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
		while (backupLookahead(matchNext(ParserImplConstants.LSHIFT, ParserImplConstants.GT))) {
			lateRun();
			if (backupLookahead(matchNext(ParserImplConstants.LSHIFT))) {
				parse(ParserImplConstants.LSHIFT);
				op = BinaryOp.LeftShift;
			} else if (backupLookahead(matchNext(ParserImplConstants.GT))) {
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
		while (backupLookahead(matchNext(ParserImplConstants.PLUS, ParserImplConstants.MINUS))) {
			lateRun();
			if (backupLookahead(matchNext(ParserImplConstants.PLUS))) {
				parse(ParserImplConstants.PLUS);
				op = BinaryOp.Plus;
			} else {
				parse(ParserImplConstants.MINUS);
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
		while (backupLookahead(matchNext(ParserImplConstants.SLASH, ParserImplConstants.STAR, ParserImplConstants.REM))) {
			lateRun();
			if (backupLookahead(matchNext(ParserImplConstants.STAR))) {
				parse(ParserImplConstants.STAR);
				op = BinaryOp.Times;
			} else if (backupLookahead(matchNext(ParserImplConstants.SLASH))) {
				parse(ParserImplConstants.SLASH);
				op = BinaryOp.Divide;
			} else {
				parse(ParserImplConstants.REM);
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
		if (backupLookahead(matchNext(ParserImplConstants.INCR, ParserImplConstants.DECR))) {
			ret = parsePrefixExpression();
		} else if (backupLookahead(matchNext(ParserImplConstants.PLUS, ParserImplConstants.MINUS))) {
			run();
			if (backupLookahead(matchNext(ParserImplConstants.PLUS))) {
				parse(ParserImplConstants.PLUS);
				op = UnaryOp.Positive;
			} else {
				parse(ParserImplConstants.MINUS);
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
		if (backupLookahead(matchNext(ParserImplConstants.INCR))) {
			parse(ParserImplConstants.INCR);
			op = UnaryOp.PreIncrement;
		} else {
			parse(ParserImplConstants.DECR);
			op = UnaryOp.PreDecrement;
		}
		ret = parseUnaryExpression();
		return dress(SUnaryExpr.make(op, ret));
	}

	public BUTree<? extends SExpr> parseUnaryExpressionNotPlusMinus() throws ParseException {
		BUTree<? extends SExpr> ret;
		UnaryOp op;
		if (backupLookahead(matchNext(ParserImplConstants.TILDE, ParserImplConstants.BANG))) {
			run();
			if (backupLookahead(matchNext(ParserImplConstants.TILDE))) {
				parse(ParserImplConstants.TILDE);
				op = UnaryOp.Inverse;
			} else {
				parse(ParserImplConstants.BANG);
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
		if (backupLookahead(matchNext(ParserImplConstants.INCR, ParserImplConstants.DECR))) {
			lateRun();
			if (backupLookahead(matchNext(ParserImplConstants.INCR))) {
				parse(ParserImplConstants.INCR);
				op = UnaryOp.PostIncrement;
			} else {
				parse(ParserImplConstants.DECR);
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
		parse(ParserImplConstants.LPAREN);
		run();
		annotations = parseAnnotations();
		if (backupLookahead(matchNext(ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.FLOAT, ParserImplConstants.INT, ParserImplConstants.LONG, ParserImplConstants.SHORT, ParserImplConstants.BOOLEAN, ParserImplConstants.DOUBLE))) {
			primitiveType = parsePrimitiveType(annotations);
			if (backupLookahead(matchNext(ParserImplConstants.RPAREN))) {
				parse(ParserImplConstants.RPAREN);
				ret = parseUnaryExpression();
				ret = dress(SCastExpr.make(primitiveType, ret));
			} else {
				lateRun();
				arrayDims = parseArrayDimsMandatory();
				type = dress(SArrayType.make(primitiveType, arrayDims));
				type = parseReferenceCastTypeRest(type);
				parse(ParserImplConstants.RPAREN);
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
			parse(ParserImplConstants.RPAREN);
			ret = parseUnaryExpressionNotPlusMinus();
			ret = dress(SCastExpr.make(type, ret));
		}
		return ret;
	}

	private boolean matchCastExpression1() {
		if (!matchAnnotations())
			return false;
		if (!match(ParserImplConstants.LBRACKET))
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
				parse(ParserImplConstants.BIT_AND);
				run();
				annotations = parseAnnotations();
				type = parseReferenceType(annotations);
				types = append(types, type);
			} while (backupLookahead(matchNext(ParserImplConstants.BIT_AND)));
			type = dress(SIntersectionType.make(types));
		}
		return type;
	}

	private boolean matchReferenceCastTypeRest1() {
		if (!match(ParserImplConstants.BIT_AND))
			return false;
		return true;
	}

	public BUTree<? extends SExpr> parseLiteral() throws ParseException {
		BUTree<? extends SExpr> ret;
		Token token;
		run();
		if (backupLookahead(matchNext(ParserImplConstants.INTEGER_LITERAL))) {
			token = parse(ParserImplConstants.INTEGER_LITERAL);
			ret = SLiteralExpr.make(Integer.class, token.image);
		} else if (backupLookahead(matchNext(ParserImplConstants.LONG_LITERAL))) {
			token = parse(ParserImplConstants.LONG_LITERAL);
			ret = SLiteralExpr.make(Long.class, token.image);
		} else if (backupLookahead(matchNext(ParserImplConstants.FLOAT_LITERAL))) {
			token = parse(ParserImplConstants.FLOAT_LITERAL);
			ret = SLiteralExpr.make(Float.class, token.image);
		} else if (backupLookahead(matchNext(ParserImplConstants.DOUBLE_LITERAL))) {
			token = parse(ParserImplConstants.DOUBLE_LITERAL);
			ret = SLiteralExpr.make(Double.class, token.image);
		} else if (backupLookahead(matchNext(ParserImplConstants.CHARACTER_LITERAL))) {
			token = parse(ParserImplConstants.CHARACTER_LITERAL);
			ret = SLiteralExpr.make(Character.class, token.image);
		} else if (backupLookahead(matchNext(ParserImplConstants.STRING_LITERAL))) {
			token = parse(ParserImplConstants.STRING_LITERAL);
			ret = SLiteralExpr.make(String.class, token.image);
		} else if (backupLookahead(matchNext(ParserImplConstants.TRUE))) {
			token = parse(ParserImplConstants.TRUE);
			ret = SLiteralExpr.make(Boolean.class, token.image);
		} else if (backupLookahead(matchNext(ParserImplConstants.FALSE))) {
			token = parse(ParserImplConstants.FALSE);
			ret = SLiteralExpr.make(Boolean.class, token.image);
		} else {
			token = parse(ParserImplConstants.NULL);
			ret = SLiteralExpr.make(Void.class, token.image);
		}
		return dress(ret);
	}

	public BUTree<? extends SExpr> parsePrimaryExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		ret = parsePrimaryPrefix();
		while (backupLookahead(matchNext(ParserImplConstants.LBRACKET, ParserImplConstants.DOT, ParserImplConstants.DOUBLECOLON))) {
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
		if (backupLookahead(matchNext(ParserImplConstants.LONG_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.TRUE, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.NULL, ParserImplConstants.STRING_LITERAL, ParserImplConstants.FALSE))) {
			ret = parseLiteral();
		} else if (backupLookahead(matchNext(ParserImplConstants.THIS))) {
			run();
			parse(ParserImplConstants.THIS);
			ret = dress(SThisExpr.make(none()));
		} else if (backupLookahead(matchNext(ParserImplConstants.SUPER))) {
			run();
			parse(ParserImplConstants.SUPER);
			ret = dress(SSuperExpr.make(none()));
			if (backupLookahead(matchNext(ParserImplConstants.DOT))) {
				lateRun();
				parse(ParserImplConstants.DOT);
				if (backupLookahead(matchPrimaryPrefix1())) {
					ret = parseMethodInvocation(ret);
				} else {
					ret = parseFieldAccess(ret);
				}
			} else {
				lateRun();
				ret = parseMethodReferenceSuffix(ret);
			}
		} else if (backupLookahead(matchNext(ParserImplConstants.NEW))) {
			ret = parseAllocationExpression(null);
		} else if (backupLookahead(matchPrimaryPrefix2())) {
			run();
			type = parseResultType();
			parse(ParserImplConstants.DOT);
			parse(ParserImplConstants.CLASS);
			ret = dress(SClassExpr.make(type));
		} else if (backupLookahead(matchPrimaryPrefix3())) {
			run();
			type = parseResultType();
			ret = STypeExpr.make(type);
			ret = parseMethodReferenceSuffix(ret);
		} else if (backupLookahead(matchPrimaryPrefix4())) {
			run();
			ret = parseMethodInvocation(null);
		} else if (backupLookahead(matchNext(ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER))) {
			ret = parseName();
			if (backupLookahead(matchNext(ParserImplConstants.ARROW))) {
				lateRun();
				parse(ParserImplConstants.ARROW);
				ret = parseLambdaBody(singletonList(makeFormalParameter((BUTree<SName>) ret)), false);
			}
		} else {
			run();
			parse(ParserImplConstants.LPAREN);
			if (backupLookahead(matchNext(ParserImplConstants.RPAREN))) {
				parse(ParserImplConstants.RPAREN);
				parse(ParserImplConstants.ARROW);
				ret = parseLambdaBody(emptyList(), true);
			} else if (backupLookahead(matchPrimaryPrefix5())) {
				ret = parseName();
				parse(ParserImplConstants.RPAREN);
				parse(ParserImplConstants.ARROW);
				ret = parseLambdaBody(singletonList(makeFormalParameter((BUTree<SName>) ret)), true);
			} else if (backupLookahead(matchPrimaryPrefix6())) {
				params = parseInferredFormalParameterList();
				parse(ParserImplConstants.RPAREN);
				parse(ParserImplConstants.ARROW);
				ret = parseLambdaBody(params, true);
			} else if (isLambda() && backupLookahead(matchNext(ParserImplConstants.NODE_VARIABLE, ParserImplConstants.BYTE, ParserImplConstants.FLOAT, ParserImplConstants.VOLATILE, ParserImplConstants.PROTECTED, ParserImplConstants.PUBLIC, ParserImplConstants.LONG, ParserImplConstants.DOUBLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_LIST_VARIABLE, ParserImplConstants.STRICTFP, ParserImplConstants.AT, ParserImplConstants.ABSTRACT, ParserImplConstants.PRIVATE, ParserImplConstants.TRANSIENT, ParserImplConstants._DEFAULT, ParserImplConstants.FINAL, ParserImplConstants.CHAR, ParserImplConstants.STATIC, ParserImplConstants.INT, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.SHORT, ParserImplConstants.BOOLEAN, ParserImplConstants.NATIVE))) {
				params = parseFormalParameterList();
				parse(ParserImplConstants.RPAREN);
				parse(ParserImplConstants.ARROW);
				ret = parseLambdaBody(params, true);
			} else {
				ret = parseExpression();
				parse(ParserImplConstants.RPAREN);
				ret = dress(SParenthesizedExpr.make(ret));
			}
		}
		return ret;
	}

	private boolean matchPrimaryPrefix1() {
		if (matchNext(ParserImplConstants.LT)) {
			if (!matchTypeArguments())
				return false;
		}
		if (!matchName())
			return false;
		if (!match(ParserImplConstants.LPAREN))
			return false;
		return true;
	}

	private boolean matchPrimaryPrefix2() {
		if (!matchResultType())
			return false;
		if (!match(ParserImplConstants.DOT))
			return false;
		if (!match(ParserImplConstants.CLASS))
			return false;
		return true;
	}

	private boolean matchPrimaryPrefix3() {
		if (!matchResultType())
			return false;
		if (!match(ParserImplConstants.DOUBLECOLON))
			return false;
		return true;
	}

	private boolean matchPrimaryPrefix4() {
		if (matchNext(ParserImplConstants.LT)) {
			if (!matchTypeArguments())
				return false;
		}
		if (!matchName())
			return false;
		if (!match(ParserImplConstants.LPAREN))
			return false;
		return true;
	}

	private boolean matchPrimaryPrefix5() {
		if (!matchName())
			return false;
		if (!match(ParserImplConstants.RPAREN))
			return false;
		if (!match(ParserImplConstants.ARROW))
			return false;
		return true;
	}

	private boolean matchPrimaryPrefix6() {
		if (!matchName())
			return false;
		if (!match(ParserImplConstants.COMMA))
			return false;
		return true;
	}

	public BUTree<? extends SExpr> parsePrimarySuffix(BUTree<? extends SExpr> scope) throws ParseException {
		BUTree<? extends SExpr> ret;
		if (backupLookahead(matchNext(ParserImplConstants.LBRACKET, ParserImplConstants.DOT))) {
			ret = parsePrimarySuffixWithoutSuper(scope);
		} else if (backupLookahead(matchNext(ParserImplConstants.DOT))) {
			parse(ParserImplConstants.DOT);
			parse(ParserImplConstants.SUPER);
			ret = dress(SSuperExpr.make(optionOf(scope)));
		} else {
			ret = parseMethodReferenceSuffix(scope);
		}
		return ret;
	}

	public BUTree<? extends SExpr> parsePrimarySuffixWithoutSuper(BUTree<? extends SExpr> scope) throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<SName> name;
		if (backupLookahead(matchNext(ParserImplConstants.DOT))) {
			parse(ParserImplConstants.DOT);
			if (backupLookahead(matchNext(ParserImplConstants.THIS))) {
				parse(ParserImplConstants.THIS);
				ret = dress(SThisExpr.make(optionOf(scope)));
			} else if (backupLookahead(matchNext(ParserImplConstants.NEW))) {
				ret = parseAllocationExpression(scope);
			} else if (backupLookahead(matchPrimarySuffixWithoutSuper1())) {
				ret = parseMethodInvocation(scope);
			} else {
				ret = parseFieldAccess(scope);
			}
		} else {
			parse(ParserImplConstants.LBRACKET);
			ret = parseExpression();
			parse(ParserImplConstants.RBRACKET);
			ret = dress(SArrayAccessExpr.make(scope, ret));
		}
		return ret;
	}

	private boolean matchPrimarySuffixWithoutSuper1() {
		if (matchNext(ParserImplConstants.LT)) {
			if (!matchTypeArguments())
				return false;
		}
		if (!matchName())
			return false;
		if (!match(ParserImplConstants.LPAREN))
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
		if (backupLookahead(matchNext(ParserImplConstants.LT))) {
			typeArgs = parseTypeArguments();
		}
		name = parseName();
		args = parseArguments();
		return dress(SMethodInvocationExpr.make(optionOf(scope), ensureNotNull(typeArgs), name, args));
	}

	public BUTree<SNodeList> parseArguments() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<? extends SExpr> expr;
		parse(ParserImplConstants.LPAREN);
		if (backupLookahead(matchNext(ParserImplConstants.LONG_LITERAL, ParserImplConstants.BYTE, ParserImplConstants.TRUE, ParserImplConstants.SUPER, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.LPAREN, ParserImplConstants.VOID, ParserImplConstants.LONG, ParserImplConstants.PLUS, ParserImplConstants.BANG, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.THIS, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.INT, ParserImplConstants.DECR, ParserImplConstants.SHORT, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.FLOAT, ParserImplConstants.NEW, ParserImplConstants.IDENTIFIER, ParserImplConstants.DOUBLE, ParserImplConstants.FALSE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.NODE_LIST_VARIABLE, ParserImplConstants.TILDE, ParserImplConstants.CHAR, ParserImplConstants.INCR, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.MINUS, ParserImplConstants.BOOLEAN, ParserImplConstants.NULL))) {
			if (backupLookahead(matchNext(ParserImplConstants.LONG_LITERAL, ParserImplConstants.BYTE, ParserImplConstants.TRUE, ParserImplConstants.SUPER, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.LPAREN, ParserImplConstants.VOID, ParserImplConstants.LONG, ParserImplConstants.PLUS, ParserImplConstants.BANG, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.THIS, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.DECR, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.FLOAT, ParserImplConstants.NEW, ParserImplConstants.IDENTIFIER, ParserImplConstants.DOUBLE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.FALSE, ParserImplConstants.TILDE, ParserImplConstants.CHAR, ParserImplConstants.INCR, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.MINUS, ParserImplConstants.BOOLEAN, ParserImplConstants.NULL))) {
				expr = parseExpression();
				ret = append(ret, expr);
				while (backupLookahead(matchNext(ParserImplConstants.COMMA))) {
					parse(ParserImplConstants.COMMA);
					expr = parseExpression();
					ret = append(ret, expr);
				}
			} else {
				ret = parseNodeListVar();
			}
		}
		parse(ParserImplConstants.RPAREN);
		return ret;
	}

	public BUTree<? extends SExpr> parseMethodReferenceSuffix(BUTree<? extends SExpr> scope) throws ParseException {
		BUTree<SNodeList> typeArgs = null;
		BUTree<SName> name;
		BUTree<? extends SExpr> ret;
		parse(ParserImplConstants.DOUBLECOLON);
		if (backupLookahead(matchNext(ParserImplConstants.LT))) {
			typeArgs = parseTypeArguments();
		}
		if (backupLookahead(matchNext(ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER))) {
			name = parseName();
		} else {
			parse(ParserImplConstants.NEW);
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

		parse(ParserImplConstants.NEW);
		if (backupLookahead(matchNext(ParserImplConstants.LT))) {
			typeArgs = parseTypeArguments();
		}
		run();
		annotations = parseAnnotations();
		if (backupLookahead(matchNext(ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.FLOAT, ParserImplConstants.INT, ParserImplConstants.LONG, ParserImplConstants.SHORT, ParserImplConstants.BOOLEAN, ParserImplConstants.DOUBLE))) {
			type = parsePrimitiveType(annotations);
			ret = parseArrayCreationExpr(type);
		} else {
			type = parseQualifiedType(annotations);
			if (backupLookahead(matchNext(ParserImplConstants.AT, ParserImplConstants.LBRACKET))) {
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
		if (!match(ParserImplConstants.LBRACE))
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
		if (!match(ParserImplConstants.LBRACKET))
			return false;
		if (!matchExpression())
			return false;
		if (!match(ParserImplConstants.RBRACKET))
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
			parse(ParserImplConstants.LBRACKET);
			expr = parseExpression();
			parse(ParserImplConstants.RBRACKET);
			arrayDimExprs = append(arrayDimExprs, dress(SArrayDimExpr.make(annotations, expr)));
		} while (backupLookahead(matchArrayDimExprsMandatory1()));
		return arrayDimExprs;
	}

	private boolean matchArrayDimExprsMandatory1() {
		if (!matchAnnotations())
			return false;
		if (!match(ParserImplConstants.LBRACKET))
			return false;
		if (!matchExpression())
			return false;
		if (!match(ParserImplConstants.RBRACKET))
			return false;
		return true;
	}

	public BUTree<SNodeList> parseArrayDimsMandatory() throws ParseException {
		BUTree<SNodeList> arrayDims = emptyList();
		BUTree<SNodeList> annotations;
		do {
			run();
			annotations = parseAnnotations();
			parse(ParserImplConstants.LBRACKET);
			parse(ParserImplConstants.RBRACKET);
			arrayDims = append(arrayDims, dress(SArrayDim.make(annotations)));
		} while (backupLookahead(matchArrayDimsMandatory1()));
		return arrayDims;
	}

	private boolean matchArrayDimsMandatory1() {
		if (!matchAnnotations())
			return false;
		if (!match(ParserImplConstants.LBRACKET))
			return false;
		if (!match(ParserImplConstants.RBRACKET))
			return false;
		return true;
	}

	public BUTree<? extends SStmt> parseStatement() throws ParseException {
		BUTree<? extends SStmt> ret;
		if (backupLookahead(matchNext(ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER))) {
			ret = parseLabeledStatement();
		} else if (backupLookahead(matchNext(ParserImplConstants.ASSERT))) {
			ret = parseAssertStatement();
		} else if (backupLookahead(matchNext(ParserImplConstants.LBRACE))) {
			ret = parseBlock();
		} else if (backupLookahead(matchNext(ParserImplConstants.SEMICOLON))) {
			ret = parseEmptyStatement();
		} else if (backupLookahead(matchNext(ParserImplConstants.LONG_LITERAL, ParserImplConstants.BYTE, ParserImplConstants.TRUE, ParserImplConstants.SUPER, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.LPAREN, ParserImplConstants.VOID, ParserImplConstants.LONG, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.THIS, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.INT, ParserImplConstants.DECR, ParserImplConstants.SHORT, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.FLOAT, ParserImplConstants.NEW, ParserImplConstants.IDENTIFIER, ParserImplConstants.DOUBLE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.FALSE, ParserImplConstants.CHAR, ParserImplConstants.INCR, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.BOOLEAN, ParserImplConstants.NULL))) {
			ret = parseStatementExpression();
		} else if (backupLookahead(matchNext(ParserImplConstants.SWITCH))) {
			ret = parseSwitchStatement();
		} else if (backupLookahead(matchNext(ParserImplConstants.IF))) {
			ret = parseIfStatement();
		} else if (backupLookahead(matchNext(ParserImplConstants.WHILE))) {
			ret = parseWhileStatement();
		} else if (backupLookahead(matchNext(ParserImplConstants.DO))) {
			ret = parseDoStatement();
		} else if (backupLookahead(matchNext(ParserImplConstants.FOR))) {
			ret = parseForStatement();
		} else if (backupLookahead(matchNext(ParserImplConstants.BREAK))) {
			ret = parseBreakStatement();
		} else if (backupLookahead(matchNext(ParserImplConstants.CONTINUE))) {
			ret = parseContinueStatement();
		} else if (backupLookahead(matchNext(ParserImplConstants.RETURN))) {
			ret = parseReturnStatement();
		} else if (backupLookahead(matchNext(ParserImplConstants.THROW))) {
			ret = parseThrowStatement();
		} else if (backupLookahead(matchNext(ParserImplConstants.SYNCHRONIZED))) {
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
		parse(ParserImplConstants.ASSERT);
		check = parseExpression();
		if (backupLookahead(matchNext(ParserImplConstants.COLON))) {
			parse(ParserImplConstants.COLON);
			msg = parseExpression();
		}
		parse(ParserImplConstants.SEMICOLON);
		return dress(SAssertStmt.make(check, optionOf(msg)));
	}

	public BUTree<SLabeledStmt> parseLabeledStatement() throws ParseException {
		BUTree<SName> label;
		BUTree<? extends SStmt> stmt;
		run();
		label = parseName();
		parse(ParserImplConstants.COLON);
		stmt = parseStatement();
		return dress(SLabeledStmt.make(label, stmt));
	}

	public BUTree<SBlockStmt> parseBlock() throws ParseException {
		BUTree<SNodeList> stmts;
		run();
		parse(ParserImplConstants.LBRACE);
		stmts = parseStatements();
		parse(ParserImplConstants.RBRACE);
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
			parse(ParserImplConstants.SEMICOLON);
			ret = dress(SExpressionStmt.make(expr));
		} else {
			ret = parseStatement();
		}
		return ret;
	}

	private boolean matchBlockStatement1() {
		if (!matchModifiersNoDefault())
			return false;
		if (matchNext(ParserImplConstants.CLASS)) {
			if (!match(ParserImplConstants.CLASS))
				return false;
		} else if (matchNext(ParserImplConstants.INTERFACE)) {
			if (!match(ParserImplConstants.INTERFACE))
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
		parse(ParserImplConstants.SEMICOLON);
		return dress(SEmptyStmt.make());
	}

	public BUTree<SExpressionStmt> parseStatementExpression() throws ParseException {
		BUTree<? extends SExpr> expr;
		AssignOp op;
		BUTree<? extends SExpr> value;
		run();
		if (backupLookahead(matchNext(ParserImplConstants.INCR, ParserImplConstants.DECR))) {
			expr = parsePrefixExpression();
		} else {
			expr = parsePrimaryExpression();
			if (backupLookahead(matchNext(ParserImplConstants.XORASSIGN, ParserImplConstants.RUNSIGNEDSHIFTASSIGN, ParserImplConstants.MINUSASSIGN, ParserImplConstants.PLUSASSIGN, ParserImplConstants.ORASSIGN, ParserImplConstants.STARASSIGN, ParserImplConstants.INCR, ParserImplConstants.LSHIFTASSIGN, ParserImplConstants.SLASHASSIGN, ParserImplConstants.RSIGNEDSHIFTASSIGN, ParserImplConstants.ASSIGN, ParserImplConstants.REMASSIGN, ParserImplConstants.DECR, ParserImplConstants.ANDASSIGN))) {
				if (backupLookahead(matchNext(ParserImplConstants.INCR))) {
					lateRun();
					parse(ParserImplConstants.INCR);
					expr = dress(SUnaryExpr.make(UnaryOp.PostIncrement, expr));
				} else if (backupLookahead(matchNext(ParserImplConstants.DECR))) {
					lateRun();
					parse(ParserImplConstants.DECR);
					expr = dress(SUnaryExpr.make(UnaryOp.PostDecrement, expr));
				} else {
					lateRun();
					op = parseAssignmentOperator();
					value = parseExpression();
					expr = dress(SAssignExpr.make(expr, op, value));
				}
			}
		}
		parse(ParserImplConstants.SEMICOLON);
		return dress(SExpressionStmt.make(expr));
	}

	public BUTree<SSwitchStmt> parseSwitchStatement() throws ParseException {
		BUTree<? extends SExpr> selector;
		BUTree<SSwitchCase> entry;
		BUTree<SNodeList> entries = emptyList();
		run();
		parse(ParserImplConstants.SWITCH);
		parse(ParserImplConstants.LPAREN);
		selector = parseExpression();
		parse(ParserImplConstants.RPAREN);
		parse(ParserImplConstants.LBRACE);
		while (backupLookahead(matchNext(ParserImplConstants._DEFAULT, ParserImplConstants.CASE))) {
			entry = parseSwitchEntry();
			entries = append(entries, entry);
		}
		parse(ParserImplConstants.RBRACE);
		return dress(SSwitchStmt.make(selector, entries));
	}

	public BUTree<SSwitchCase> parseSwitchEntry() throws ParseException {
		BUTree<? extends SExpr> label = null;
		BUTree<SNodeList> stmts;
		run();
		if (backupLookahead(matchNext(ParserImplConstants.CASE))) {
			parse(ParserImplConstants.CASE);
			label = parseExpression();
		} else {
			parse(ParserImplConstants._DEFAULT);
		}
		parse(ParserImplConstants.COLON);
		stmts = parseStatements();
		return dress(SSwitchCase.make(optionOf(label), ensureNotNull(stmts)));
	}

	public BUTree<SIfStmt> parseIfStatement() throws ParseException {
		BUTree<? extends SExpr> condition;
		BUTree<? extends SStmt> thenStmt;
		BUTree<? extends SStmt> elseStmt = null;
		run();
		parse(ParserImplConstants.IF);
		parse(ParserImplConstants.LPAREN);
		condition = parseExpression();
		parse(ParserImplConstants.RPAREN);
		thenStmt = parseStatement();
		if (backupLookahead(matchNext(ParserImplConstants.ELSE))) {
			parse(ParserImplConstants.ELSE);
			elseStmt = parseStatement();
		}
		return dress(SIfStmt.make(condition, thenStmt, optionOf(elseStmt)));
	}

	public BUTree<SWhileStmt> parseWhileStatement() throws ParseException {
		BUTree<? extends SExpr> condition;
		BUTree<? extends SStmt> body;
		run();
		parse(ParserImplConstants.WHILE);
		parse(ParserImplConstants.LPAREN);
		condition = parseExpression();
		parse(ParserImplConstants.RPAREN);
		body = parseStatement();
		return dress(SWhileStmt.make(condition, body));
	}

	public BUTree<SDoStmt> parseDoStatement() throws ParseException {
		BUTree<? extends SExpr> condition;
		BUTree<? extends SStmt> body;
		run();
		parse(ParserImplConstants.DO);
		body = parseStatement();
		parse(ParserImplConstants.WHILE);
		parse(ParserImplConstants.LPAREN);
		condition = parseExpression();
		parse(ParserImplConstants.RPAREN);
		parse(ParserImplConstants.SEMICOLON);
		return dress(SDoStmt.make(body, condition));
	}

	public BUTree<? extends SStmt> parseForStatement() throws ParseException {
		BUTree<SVariableDeclarationExpr> varExpr = null;
		BUTree<? extends SExpr> expr = null;
		BUTree<SNodeList> init = null;
		BUTree<SNodeList> update = null;
		BUTree<? extends SStmt> body;
		run();
		parse(ParserImplConstants.FOR);
		parse(ParserImplConstants.LPAREN);
		if (backupLookahead(matchForStatement1())) {
			varExpr = parseVariableDeclExpression();
			parse(ParserImplConstants.COLON);
			expr = parseExpression();
		} else {
			if (backupLookahead(matchNext(ParserImplConstants.LONG_LITERAL, ParserImplConstants.BYTE, ParserImplConstants.TRUE, ParserImplConstants.SUPER, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.LPAREN, ParserImplConstants.VOID, ParserImplConstants.LONG, ParserImplConstants.PUBLIC, ParserImplConstants.PLUS, ParserImplConstants.ABSTRACT, ParserImplConstants.TRANSIENT, ParserImplConstants.BANG, ParserImplConstants.FINAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.THIS, ParserImplConstants.STATIC, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.INT, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.SHORT, ParserImplConstants.DECR, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.FLOAT, ParserImplConstants.VOLATILE, ParserImplConstants.NEW, ParserImplConstants.PROTECTED, ParserImplConstants.IDENTIFIER, ParserImplConstants.DOUBLE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.FALSE, ParserImplConstants.STRICTFP, ParserImplConstants.AT, ParserImplConstants.PRIVATE, ParserImplConstants.TILDE, ParserImplConstants.CHAR, ParserImplConstants.INCR, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.MINUS, ParserImplConstants.BOOLEAN, ParserImplConstants.NATIVE, ParserImplConstants.NULL))) {
				init = parseForInit();
			}
			parse(ParserImplConstants.SEMICOLON);
			if (backupLookahead(matchNext(ParserImplConstants.LONG_LITERAL, ParserImplConstants.BYTE, ParserImplConstants.TRUE, ParserImplConstants.SUPER, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.LPAREN, ParserImplConstants.VOID, ParserImplConstants.LONG, ParserImplConstants.PLUS, ParserImplConstants.BANG, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.THIS, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.DECR, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.FLOAT, ParserImplConstants.NEW, ParserImplConstants.DOUBLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.STRING_LITERAL, ParserImplConstants.FALSE, ParserImplConstants.TILDE, ParserImplConstants.CHAR, ParserImplConstants.INCR, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.MINUS, ParserImplConstants.BOOLEAN, ParserImplConstants.NULL))) {
				expr = parseExpression();
			}
			parse(ParserImplConstants.SEMICOLON);
			if (backupLookahead(matchNext(ParserImplConstants.LONG_LITERAL, ParserImplConstants.BYTE, ParserImplConstants.TRUE, ParserImplConstants.SUPER, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.LPAREN, ParserImplConstants.VOID, ParserImplConstants.LONG, ParserImplConstants.PLUS, ParserImplConstants.BANG, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.THIS, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.INT, ParserImplConstants.DECR, ParserImplConstants.SHORT, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.FLOAT, ParserImplConstants.NEW, ParserImplConstants.IDENTIFIER, ParserImplConstants.DOUBLE, ParserImplConstants.FALSE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.TILDE, ParserImplConstants.CHAR, ParserImplConstants.INCR, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.MINUS, ParserImplConstants.BOOLEAN, ParserImplConstants.NULL))) {
				update = parseForUpdate();
			}
		}
		parse(ParserImplConstants.RPAREN);
		body = parseStatement();
		if (varExpr != null)
	return dress(SForeachStmt.make(varExpr, expr, body));
else
	return dress(SForStmt.make(init, expr, update, body));

	}

	private boolean matchForStatement1() {
		if (!matchVariableDeclExpression())
			return false;
		if (!match(ParserImplConstants.COLON))
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
		while (backupLookahead(matchNext(ParserImplConstants.COMMA))) {
			parse(ParserImplConstants.COMMA);
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
		parse(ParserImplConstants.BREAK);
		if (backupLookahead(matchNext(ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER))) {
			id = parseName();
		}
		parse(ParserImplConstants.SEMICOLON);
		return dress(SBreakStmt.make(optionOf(id)));
	}

	public BUTree<SContinueStmt> parseContinueStatement() throws ParseException {
		BUTree<SName> id = null;
		run();
		parse(ParserImplConstants.CONTINUE);
		if (backupLookahead(matchNext(ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER))) {
			id = parseName();
		}
		parse(ParserImplConstants.SEMICOLON);
		return dress(SContinueStmt.make(optionOf(id)));
	}

	public BUTree<SReturnStmt> parseReturnStatement() throws ParseException {
		BUTree<? extends SExpr> expr = null;
		run();
		parse(ParserImplConstants.RETURN);
		if (backupLookahead(matchNext(ParserImplConstants.LONG_LITERAL, ParserImplConstants.BYTE, ParserImplConstants.TRUE, ParserImplConstants.SUPER, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.LPAREN, ParserImplConstants.VOID, ParserImplConstants.LONG, ParserImplConstants.PLUS, ParserImplConstants.BANG, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.THIS, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.DECR, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.FLOAT, ParserImplConstants.NEW, ParserImplConstants.DOUBLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.STRING_LITERAL, ParserImplConstants.FALSE, ParserImplConstants.TILDE, ParserImplConstants.CHAR, ParserImplConstants.INCR, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.MINUS, ParserImplConstants.BOOLEAN, ParserImplConstants.NULL))) {
			expr = parseExpression();
		}
		parse(ParserImplConstants.SEMICOLON);
		return dress(SReturnStmt.make(optionOf(expr)));
	}

	public BUTree<SThrowStmt> parseThrowStatement() throws ParseException {
		BUTree<? extends SExpr> expr;
		run();
		parse(ParserImplConstants.THROW);
		expr = parseExpression();
		parse(ParserImplConstants.SEMICOLON);
		return dress(SThrowStmt.make(expr));
	}

	public BUTree<SSynchronizedStmt> parseSynchronizedStatement() throws ParseException {
		BUTree<? extends SExpr> expr;
		BUTree<SBlockStmt> block;
		run();
		parse(ParserImplConstants.SYNCHRONIZED);
		parse(ParserImplConstants.LPAREN);
		expr = parseExpression();
		parse(ParserImplConstants.RPAREN);
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
		parse(ParserImplConstants.TRY);
		if (backupLookahead(matchNext(ParserImplConstants.LPAREN))) {
			resources = parseResourceSpecification(trailingSemiColon);
			tryBlock = parseBlock();
			if (backupLookahead(matchNext(ParserImplConstants.CATCH))) {
				catchClauses = parseCatchClauses();
			}
			if (backupLookahead(matchNext(ParserImplConstants.FINALLY))) {
				parse(ParserImplConstants.FINALLY);
				finallyBlock = parseBlock();
			}
		} else {
			tryBlock = parseBlock();
			if (backupLookahead(matchNext(ParserImplConstants.CATCH))) {
				catchClauses = parseCatchClauses();
				if (backupLookahead(matchNext(ParserImplConstants.FINALLY))) {
					parse(ParserImplConstants.FINALLY);
					finallyBlock = parseBlock();
				}
			} else {
				parse(ParserImplConstants.FINALLY);
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
		} while (backupLookahead(matchNext(ParserImplConstants.CATCH)));
		return catchClauses;
	}

	public BUTree<SCatchClause> parseCatchClause() throws ParseException {
		BUTree<SFormalParameter> param;
		BUTree<SBlockStmt> catchBlock;
		run();
		parse(ParserImplConstants.CATCH);
		parse(ParserImplConstants.LPAREN);
		param = parseCatchFormalParameter();
		parse(ParserImplConstants.RPAREN);
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
				parse(ParserImplConstants.BIT_OR);
				exceptType = parseAnnotatedQualifiedType();
				exceptTypes = append(exceptTypes, exceptType);
			} while (backupLookahead(matchNext(ParserImplConstants.BIT_OR)));
			exceptType = dress(SUnionType.make(exceptTypes));
		}
		exceptId = parseVariableDeclaratorId();
		return dress(SFormalParameter.make(modifiers, exceptType, false, exceptId));
	}

	private boolean matchCatchFormalParameter1() {
		if (!match(ParserImplConstants.BIT_OR))
			return false;
		return true;
	}

	public BUTree<SNodeList> parseResourceSpecification(ByRef<Boolean> trailingSemiColon) throws ParseException {
		BUTree<SNodeList> vars = emptyList();
		BUTree<SVariableDeclarationExpr> var;
		parse(ParserImplConstants.LPAREN);
		var = parseVariableDeclExpression();
		vars = append(vars, var);
		while (backupLookahead(matchNext(ParserImplConstants.SEMICOLON))) {
			parse(ParserImplConstants.SEMICOLON);
			var = parseVariableDeclExpression();
			vars = append(vars, var);
		}
		if (backupLookahead(matchNext(ParserImplConstants.SEMICOLON))) {
			parse(ParserImplConstants.SEMICOLON);
			trailingSemiColon.value = true;
		}
		parse(ParserImplConstants.RPAREN);
		return vars;
	}

	public void parseRUNSIGNEDSHIFT() throws ParseException {
		parse(ParserImplConstants.GT);
		parse(ParserImplConstants.GT);
		parse(ParserImplConstants.GT);
		popNewWhitespaces();
	}

	public void parseRSIGNEDSHIFT() throws ParseException {
		parse(ParserImplConstants.GT);
		parse(ParserImplConstants.GT);
		popNewWhitespaces();
	}

	public BUTree<SNodeList> parseAnnotations() throws ParseException {
		BUTree<SNodeList> annotations = emptyList();
		BUTree<? extends SAnnotationExpr> annotation;
		while (backupLookahead(matchNext(ParserImplConstants.AT))) {
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
		if (!match(ParserImplConstants.AT))
			return false;
		if (!matchQualifiedName())
			return false;
		if (!match(ParserImplConstants.LPAREN))
			return false;
		if (matchNext(ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER)) {
			if (!matchName())
				return false;
			if (!match(ParserImplConstants.ASSIGN))
				return false;
		} else if (matchNext(ParserImplConstants.RPAREN)) {
			if (!match(ParserImplConstants.RPAREN))
				return false;
		}
		return true;
	}

	private boolean matchAnnotation2() {
		if (!match(ParserImplConstants.AT))
			return false;
		if (!matchQualifiedName())
			return false;
		if (!match(ParserImplConstants.LPAREN))
			return false;
		return true;
	}

	private boolean matchAnnotation3() {
		if (!match(ParserImplConstants.AT))
			return false;
		if (!matchQualifiedName())
			return false;
		return true;
	}

	public BUTree<SNormalAnnotationExpr> parseNormalAnnotation() throws ParseException {
		BUTree<SQualifiedName> name;
		BUTree<SNodeList> pairs = null;
		run();
		parse(ParserImplConstants.AT);
		name = parseQualifiedName();
		parse(ParserImplConstants.LPAREN);
		if (backupLookahead(matchNext(ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER))) {
			pairs = parseMemberValuePairs();
		}
		parse(ParserImplConstants.RPAREN);
		return dress(SNormalAnnotationExpr.make(name, ensureNotNull(pairs)));
	}

	public BUTree<SMarkerAnnotationExpr> parseMarkerAnnotation() throws ParseException {
		BUTree<SQualifiedName> name;
		run();
		parse(ParserImplConstants.AT);
		name = parseQualifiedName();
		return dress(SMarkerAnnotationExpr.make(name));
	}

	public BUTree<SSingleMemberAnnotationExpr> parseSingleMemberAnnotation() throws ParseException {
		BUTree<SQualifiedName> name;
		BUTree<? extends SExpr> memberVal;
		run();
		parse(ParserImplConstants.AT);
		name = parseQualifiedName();
		parse(ParserImplConstants.LPAREN);
		memberVal = parseMemberValue();
		parse(ParserImplConstants.RPAREN);
		return dress(SSingleMemberAnnotationExpr.make(name, memberVal));
	}

	public BUTree<SNodeList> parseMemberValuePairs() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<SMemberValuePair> pair;
		pair = parseMemberValuePair();
		ret = append(ret, pair);
		while (backupLookahead(matchNext(ParserImplConstants.COMMA))) {
			parse(ParserImplConstants.COMMA);
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
		parse(ParserImplConstants.ASSIGN);
		value = parseMemberValue();
		return dress(SMemberValuePair.make(name, value));
	}

	public BUTree<? extends SExpr> parseMemberValue() throws ParseException {
		BUTree<? extends SExpr> ret;
		if (backupLookahead(matchNext(ParserImplConstants.AT))) {
			ret = parseAnnotation();
		} else if (backupLookahead(matchNext(ParserImplConstants.LBRACE))) {
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
		parse(ParserImplConstants.LBRACE);
		if (backupLookahead(matchNext(ParserImplConstants.LONG_LITERAL, ParserImplConstants.BYTE, ParserImplConstants.TRUE, ParserImplConstants.SUPER, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.LPAREN, ParserImplConstants.VOID, ParserImplConstants.LONG, ParserImplConstants.PLUS, ParserImplConstants.BANG, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.THIS, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.DECR, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.FLOAT, ParserImplConstants.LBRACE, ParserImplConstants.NEW, ParserImplConstants.DOUBLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.FALSE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.AT, ParserImplConstants.TILDE, ParserImplConstants.CHAR, ParserImplConstants.INCR, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.MINUS, ParserImplConstants.BOOLEAN, ParserImplConstants.NULL))) {
			member = parseMemberValue();
			ret = append(ret, member);
			while (backupLookahead(matchNext(ParserImplConstants.COMMA))) {
				parse(ParserImplConstants.COMMA);
				member = parseMemberValue();
				ret = append(ret, member);
			}
		}
		if (backupLookahead(matchNext(ParserImplConstants.COMMA))) {
			parse(ParserImplConstants.COMMA);
			trailingComma = true;
		}
		parse(ParserImplConstants.RBRACE);
		return dress(SArrayInitializerExpr.make(ret, trailingComma));
	}

	private boolean matchTypeArguments() {
		if (!match(ParserImplConstants.LT))
			return false;
		if (matchNext(ParserImplConstants.NODE_VARIABLE, ParserImplConstants.BYTE, ParserImplConstants.HOOK, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.DOUBLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_LIST_VARIABLE, ParserImplConstants.AT, ParserImplConstants.CHAR, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.BOOLEAN)) {
			if (!matchTypeArgumentList())
				return false;
		}
		if (!match(ParserImplConstants.GT))
			return false;
		return true;
	}

	private boolean matchQualifiedName() {
		if (!matchName())
			return false;
		while (matchNext(ParserImplConstants.DOT)) {
			if (!match(ParserImplConstants.DOT))
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
		if (matchNext(ParserImplConstants.VOID)) {
			if (!match(ParserImplConstants.VOID))
				return false;
		} else if (matchNext(ParserImplConstants.NODE_VARIABLE, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.FLOAT, ParserImplConstants.INT, ParserImplConstants.LONG, ParserImplConstants.SHORT, ParserImplConstants.BOOLEAN, ParserImplConstants.DOUBLE, ParserImplConstants.IDENTIFIER)) {
			if (!matchType())
				return false;
		}
		return true;
	}

	private boolean matchName() {
		if (matchNext(ParserImplConstants.IDENTIFIER)) {
			if (!match(ParserImplConstants.IDENTIFIER))
				return false;
		} else if (matchNext(ParserImplConstants.NODE_VARIABLE)) {
			if (!matchNodeVar())
				return false;
		}
		return true;
	}

	private boolean matchType() {
		if (matchNext(ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.FLOAT, ParserImplConstants.INT, ParserImplConstants.LONG, ParserImplConstants.SHORT, ParserImplConstants.BOOLEAN, ParserImplConstants.DOUBLE)) {
			if (!matchPrimitiveType())
				return false;
			if (matchType1()) {
				if (!matchArrayDimsMandatory())
					return false;
			}
		} else if (matchNext(ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER)) {
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
		if (matchNext(ParserImplConstants.DOT)) {
			if (!match(ParserImplConstants.DOT))
				return false;
			if (matchNext(ParserImplConstants.THIS)) {
				if (!match(ParserImplConstants.THIS))
					return false;
			} else if (matchNext(ParserImplConstants.NEW)) {
				if (!matchAllocationExpression())
					return false;
			} else if (matchPrimarySuffixWithoutSuper1()) {
				if (!matchMethodInvocation())
					return false;
			} else if (matchNext(ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER)) {
				if (!matchFieldAccess())
					return false;
			}
		} else if (matchNext(ParserImplConstants.LBRACKET)) {
			if (!match(ParserImplConstants.LBRACKET))
				return false;
			if (!matchExpression())
				return false;
			if (!match(ParserImplConstants.RBRACKET))
				return false;
		}
		return true;
	}

	private boolean matchAnnotations() {
		boolean matched = false;
		while (matchNext(ParserImplConstants.AT)) {
			if (matchAnnotation()) matched = true;
		}
		return matched;
	}

	private boolean matchTypeParameters() {
		if (!match(ParserImplConstants.LT))
			return false;
		if (matchNext(ParserImplConstants.AT, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER)) {
			if (!matchTypeParameter())
				return false;
			while (matchNext(ParserImplConstants.COMMA)) {
				if (!match(ParserImplConstants.COMMA))
					return false;
				if (!matchTypeParameter())
					return false;
			}
		} else if (matchNext(ParserImplConstants.NODE_LIST_VARIABLE)) {
			if (!matchNodeListVar())
				return false;
		}
		if (!match(ParserImplConstants.GT))
			return false;
		return true;
	}

	private boolean matchExplicitConstructorInvocation() {
		if (matchExplicitConstructorInvocation1()) {
			if (matchNext(ParserImplConstants.LT)) {
				if (!matchTypeArguments())
					return false;
			}
			if (!match(ParserImplConstants.THIS))
				return false;
			if (!matchArguments())
				return false;
			if (!match(ParserImplConstants.SEMICOLON))
				return false;
		} else if (matchNext(ParserImplConstants.LONG_LITERAL, ParserImplConstants.BYTE, ParserImplConstants.TRUE, ParserImplConstants.SUPER, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.LPAREN, ParserImplConstants.VOID, ParserImplConstants.LONG, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.THIS, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.FLOAT, ParserImplConstants.NEW, ParserImplConstants.IDENTIFIER, ParserImplConstants.DOUBLE, ParserImplConstants.FALSE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.CHAR, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.BOOLEAN, ParserImplConstants.NULL)) {
			if (matchExplicitConstructorInvocation2()) {
				if (!matchPrimaryExpressionWithoutSuperSuffix())
					return false;
				if (!match(ParserImplConstants.DOT))
					return false;
			}
			if (matchNext(ParserImplConstants.LT)) {
				if (!matchTypeArguments())
					return false;
			}
			if (!match(ParserImplConstants.SUPER))
				return false;
			if (!matchArguments())
				return false;
			if (!match(ParserImplConstants.SEMICOLON))
				return false;
		}
		return true;
	}

	private boolean matchExpression() {
		if (matchExpression1()) {
			if (!matchName())
				return false;
			if (!match(ParserImplConstants.ARROW))
				return false;
			if (!matchLambdaBody())
				return false;
		} else if (matchExpression2()) {
			if (!match(ParserImplConstants.LPAREN))
				return false;
			if (!match(ParserImplConstants.RPAREN))
				return false;
			if (!match(ParserImplConstants.ARROW))
				return false;
			if (!matchLambdaBody())
				return false;
		} else if (matchExpression3()) {
			if (!match(ParserImplConstants.LPAREN))
				return false;
			if (!matchName())
				return false;
			if (!match(ParserImplConstants.RPAREN))
				return false;
			if (!match(ParserImplConstants.ARROW))
				return false;
			if (!matchLambdaBody())
				return false;
		} else if (matchExpression4()) {
			if (!match(ParserImplConstants.LPAREN))
				return false;
			if (!matchInferredFormalParameterList())
				return false;
			if (!match(ParserImplConstants.RPAREN))
				return false;
			if (!match(ParserImplConstants.ARROW))
				return false;
			if (!matchLambdaBody())
				return false;
		} else if (matchNext(ParserImplConstants.LONG_LITERAL, ParserImplConstants.BYTE, ParserImplConstants.TRUE, ParserImplConstants.SUPER, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.LPAREN, ParserImplConstants.VOID, ParserImplConstants.LONG, ParserImplConstants.PLUS, ParserImplConstants.BANG, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.THIS, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.INT, ParserImplConstants.DECR, ParserImplConstants.SHORT, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.FLOAT, ParserImplConstants.NEW, ParserImplConstants.IDENTIFIER, ParserImplConstants.DOUBLE, ParserImplConstants.FALSE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.TILDE, ParserImplConstants.CHAR, ParserImplConstants.INCR, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.MINUS, ParserImplConstants.BOOLEAN, ParserImplConstants.NULL)) {
			if (!matchConditionalExpression())
				return false;
			if (matchNext(ParserImplConstants.XORASSIGN, ParserImplConstants.ORASSIGN, ParserImplConstants.RUNSIGNEDSHIFTASSIGN, ParserImplConstants.STARASSIGN, ParserImplConstants.SLASHASSIGN, ParserImplConstants.LSHIFTASSIGN, ParserImplConstants.RSIGNEDSHIFTASSIGN, ParserImplConstants.ASSIGN, ParserImplConstants.REMASSIGN, ParserImplConstants.MINUSASSIGN, ParserImplConstants.PLUSASSIGN, ParserImplConstants.ANDASSIGN)) {
				if (!matchAssignmentOperator())
					return false;
				if (!matchExpression())
					return false;
			}
		}
		return true;
	}

	private boolean matchModifiersNoDefault() {
		while (matchNext(ParserImplConstants.STRICTFP, ParserImplConstants.AT, ParserImplConstants.ABSTRACT, ParserImplConstants.TRANSIENT, ParserImplConstants.PRIVATE, ParserImplConstants.FINAL, ParserImplConstants.STATIC, ParserImplConstants.VOLATILE, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.PROTECTED, ParserImplConstants.PUBLIC, ParserImplConstants.NATIVE)) {
			if (matchNext(ParserImplConstants.PUBLIC)) {
				if (!match(ParserImplConstants.PUBLIC))
					return false;
			} else if (matchNext(ParserImplConstants.PROTECTED)) {
				if (!match(ParserImplConstants.PROTECTED))
					return false;
			} else if (matchNext(ParserImplConstants.PRIVATE)) {
				if (!match(ParserImplConstants.PRIVATE))
					return false;
			} else if (matchNext(ParserImplConstants.ABSTRACT)) {
				if (!match(ParserImplConstants.ABSTRACT))
					return false;
			} else if (matchNext(ParserImplConstants.STATIC)) {
				if (!match(ParserImplConstants.STATIC))
					return false;
			} else if (matchNext(ParserImplConstants.FINAL)) {
				if (!match(ParserImplConstants.FINAL))
					return false;
			} else if (matchNext(ParserImplConstants.TRANSIENT)) {
				if (!match(ParserImplConstants.TRANSIENT))
					return false;
			} else if (matchNext(ParserImplConstants.VOLATILE)) {
				if (!match(ParserImplConstants.VOLATILE))
					return false;
			} else if (matchNext(ParserImplConstants.SYNCHRONIZED)) {
				if (!match(ParserImplConstants.SYNCHRONIZED))
					return false;
			} else if (matchNext(ParserImplConstants.NATIVE)) {
				if (!match(ParserImplConstants.NATIVE))
					return false;
			} else if (matchNext(ParserImplConstants.STRICTFP)) {
				if (!match(ParserImplConstants.STRICTFP))
					return false;
			} else if (matchNext(ParserImplConstants.AT)) {
				if (!matchAnnotation())
					return false;
			}
		}
		return true;
	}

	private boolean matchCastExpression() {
		if (!match(ParserImplConstants.LPAREN))
			return false;
		if (!matchAnnotations())
			return false;
		if (matchNext(ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.FLOAT, ParserImplConstants.INT, ParserImplConstants.LONG, ParserImplConstants.SHORT, ParserImplConstants.BOOLEAN, ParserImplConstants.DOUBLE)) {
			if (!matchPrimitiveType())
				return false;
			if (matchNext(ParserImplConstants.RPAREN)) {
				if (!match(ParserImplConstants.RPAREN))
					return false;
				if (!matchUnaryExpression())
					return false;
			} else if (matchNext(ParserImplConstants.AT, ParserImplConstants.LBRACKET)) {
				if (!matchArrayDimsMandatory())
					return false;
				if (!matchReferenceCastTypeRest())
					return false;
				if (!match(ParserImplConstants.RPAREN))
					return false;
				if (!matchUnaryExpressionNotPlusMinus())
					return false;
			}
		} else if (matchNext(ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER)) {
			if (!matchQualifiedType())
				return false;
			if (matchCastExpression1()) {
				if (!matchArrayDimsMandatory())
					return false;
			}
			if (!matchReferenceCastTypeRest())
				return false;
			if (!match(ParserImplConstants.RPAREN))
				return false;
			if (!matchUnaryExpressionNotPlusMinus())
				return false;
		}
		return true;
	}

	private boolean matchModifiers() {
		while (matchNext(ParserImplConstants.VOLATILE, ParserImplConstants.PROTECTED, ParserImplConstants.PUBLIC, ParserImplConstants.STRICTFP, ParserImplConstants.AT, ParserImplConstants.ABSTRACT, ParserImplConstants.PRIVATE, ParserImplConstants.TRANSIENT, ParserImplConstants._DEFAULT, ParserImplConstants.FINAL, ParserImplConstants.STATIC, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.NATIVE)) {
			if (matchNext(ParserImplConstants.PUBLIC)) {
				if (!match(ParserImplConstants.PUBLIC))
					return false;
			} else if (matchNext(ParserImplConstants.PROTECTED)) {
				if (!match(ParserImplConstants.PROTECTED))
					return false;
			} else if (matchNext(ParserImplConstants.PRIVATE)) {
				if (!match(ParserImplConstants.PRIVATE))
					return false;
			} else if (matchNext(ParserImplConstants.ABSTRACT)) {
				if (!match(ParserImplConstants.ABSTRACT))
					return false;
			} else if (matchNext(ParserImplConstants._DEFAULT)) {
				if (!match(ParserImplConstants._DEFAULT))
					return false;
			} else if (matchNext(ParserImplConstants.STATIC)) {
				if (!match(ParserImplConstants.STATIC))
					return false;
			} else if (matchNext(ParserImplConstants.FINAL)) {
				if (!match(ParserImplConstants.FINAL))
					return false;
			} else if (matchNext(ParserImplConstants.TRANSIENT)) {
				if (!match(ParserImplConstants.TRANSIENT))
					return false;
			} else if (matchNext(ParserImplConstants.VOLATILE)) {
				if (!match(ParserImplConstants.VOLATILE))
					return false;
			} else if (matchNext(ParserImplConstants.SYNCHRONIZED)) {
				if (!match(ParserImplConstants.SYNCHRONIZED))
					return false;
			} else if (matchNext(ParserImplConstants.NATIVE)) {
				if (!match(ParserImplConstants.NATIVE))
					return false;
			} else if (matchNext(ParserImplConstants.STRICTFP)) {
				if (!match(ParserImplConstants.STRICTFP))
					return false;
			} else if (matchNext(ParserImplConstants.AT)) {
				if (!matchAnnotation())
					return false;
			}
		}
		return true;
	}

	private boolean matchPackageDecl() {
		if (!matchAnnotations())
			return false;
		if (!match(ParserImplConstants.PACKAGE))
			return false;
		if (!matchQualifiedName())
			return false;
		if (!match(ParserImplConstants.SEMICOLON))
			return false;
		return true;
	}

	private boolean matchTypeParameter() {
		if (!matchAnnotations())
			return false;
		if (!matchName())
			return false;
		if (matchNext(ParserImplConstants.EXTENDS)) {
			if (!matchTypeBounds())
				return false;
		}
		return true;
	}

	private boolean matchNodeVar() {
		if (!match(ParserImplConstants.NODE_VARIABLE))
			return false;
		return true;
	}

	private boolean matchConditionalExpression() {
		if (!matchConditionalOrExpression())
			return false;
		if (matchNext(ParserImplConstants.HOOK)) {
			if (!match(ParserImplConstants.HOOK))
				return false;
			if (!matchExpression())
				return false;
			if (!match(ParserImplConstants.COLON))
				return false;
			if (!matchConditionalExpression())
				return false;
		}
		return true;
	}

	private boolean matchArguments() {
		if (!match(ParserImplConstants.LPAREN))
			return false;
		if (matchNext(ParserImplConstants.LONG_LITERAL, ParserImplConstants.BYTE, ParserImplConstants.TRUE, ParserImplConstants.SUPER, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.LPAREN, ParserImplConstants.VOID, ParserImplConstants.LONG, ParserImplConstants.PLUS, ParserImplConstants.BANG, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.THIS, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.INT, ParserImplConstants.DECR, ParserImplConstants.SHORT, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.FLOAT, ParserImplConstants.NEW, ParserImplConstants.IDENTIFIER, ParserImplConstants.DOUBLE, ParserImplConstants.FALSE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.NODE_LIST_VARIABLE, ParserImplConstants.TILDE, ParserImplConstants.CHAR, ParserImplConstants.INCR, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.MINUS, ParserImplConstants.BOOLEAN, ParserImplConstants.NULL)) {
			if (matchNext(ParserImplConstants.LONG_LITERAL, ParserImplConstants.BYTE, ParserImplConstants.TRUE, ParserImplConstants.SUPER, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.LPAREN, ParserImplConstants.VOID, ParserImplConstants.LONG, ParserImplConstants.PLUS, ParserImplConstants.BANG, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.THIS, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.DECR, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.FLOAT, ParserImplConstants.NEW, ParserImplConstants.IDENTIFIER, ParserImplConstants.DOUBLE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.FALSE, ParserImplConstants.TILDE, ParserImplConstants.CHAR, ParserImplConstants.INCR, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.MINUS, ParserImplConstants.BOOLEAN, ParserImplConstants.NULL)) {
				if (!matchExpression())
					return false;
				while (matchNext(ParserImplConstants.COMMA)) {
					if (!match(ParserImplConstants.COMMA))
						return false;
					if (!matchExpression())
						return false;
				}
			} else if (matchNext(ParserImplConstants.NODE_LIST_VARIABLE)) {
				if (!matchNodeListVar())
					return false;
			}
		}
		if (!match(ParserImplConstants.RPAREN))
			return false;
		return true;
	}

	private boolean matchMethodInvocation() {
		if (matchNext(ParserImplConstants.LT)) {
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
		if (matchNext(ParserImplConstants.INCR, ParserImplConstants.DECR)) {
			if (!matchPrefixExpression())
				return false;
		} else if (matchNext(ParserImplConstants.PLUS, ParserImplConstants.MINUS)) {
			if (matchNext(ParserImplConstants.PLUS)) {
				if (!match(ParserImplConstants.PLUS))
					return false;
			} else if (matchNext(ParserImplConstants.MINUS)) {
				if (!match(ParserImplConstants.MINUS))
					return false;
			}
			if (!matchUnaryExpression())
				return false;
		} else if (matchNext(ParserImplConstants.LONG_LITERAL, ParserImplConstants.BYTE, ParserImplConstants.TRUE, ParserImplConstants.SUPER, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.LPAREN, ParserImplConstants.VOID, ParserImplConstants.LONG, ParserImplConstants.BANG, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.THIS, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.FLOAT, ParserImplConstants.NEW, ParserImplConstants.IDENTIFIER, ParserImplConstants.DOUBLE, ParserImplConstants.FALSE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.TILDE, ParserImplConstants.CHAR, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.BOOLEAN, ParserImplConstants.NULL)) {
			if (!matchUnaryExpressionNotPlusMinus())
				return false;
		}
		return true;
	}

	private boolean matchPrimaryPrefix() {
		if (matchNext(ParserImplConstants.LONG_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.TRUE, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.NULL, ParserImplConstants.STRING_LITERAL, ParserImplConstants.FALSE)) {
			if (!matchLiteral())
				return false;
		} else if (matchNext(ParserImplConstants.THIS)) {
			if (!match(ParserImplConstants.THIS))
				return false;
		} else if (matchNext(ParserImplConstants.SUPER)) {
			if (!match(ParserImplConstants.SUPER))
				return false;
			if (matchNext(ParserImplConstants.DOT)) {
				if (!match(ParserImplConstants.DOT))
					return false;
				if (matchPrimaryPrefix1()) {
					if (!matchMethodInvocation())
						return false;
				} else if (matchNext(ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER)) {
					if (!matchFieldAccess())
						return false;
				}
			} else if (matchNext(ParserImplConstants.DOUBLECOLON)) {
				if (!matchMethodReferenceSuffix())
					return false;
			}
		} else if (matchNext(ParserImplConstants.NEW)) {
			if (!matchAllocationExpression())
				return false;
		} else if (matchPrimaryPrefix2()) {
			if (!matchResultType())
				return false;
			if (!match(ParserImplConstants.DOT))
				return false;
			if (!match(ParserImplConstants.CLASS))
				return false;
		} else if (matchPrimaryPrefix3()) {
			if (!matchResultType())
				return false;
			if (!matchMethodReferenceSuffix())
				return false;
		} else if (matchPrimaryPrefix4()) {
			if (!matchMethodInvocation())
				return false;
		} else if (matchNext(ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER)) {
			if (!matchName())
				return false;
			if (matchNext(ParserImplConstants.ARROW)) {
				if (!match(ParserImplConstants.ARROW))
					return false;
				if (!matchLambdaBody())
					return false;
			}
		} else if (matchNext(ParserImplConstants.LPAREN)) {
			if (!match(ParserImplConstants.LPAREN))
				return false;
			if (matchNext(ParserImplConstants.RPAREN)) {
				if (!match(ParserImplConstants.RPAREN))
					return false;
				if (!match(ParserImplConstants.ARROW))
					return false;
				if (!matchLambdaBody())
					return false;
			} else if (matchPrimaryPrefix5()) {
				if (!matchName())
					return false;
				if (!match(ParserImplConstants.RPAREN))
					return false;
				if (!match(ParserImplConstants.ARROW))
					return false;
				if (!matchLambdaBody())
					return false;
			} else if (matchPrimaryPrefix6()) {
				if (!matchInferredFormalParameterList())
					return false;
				if (!match(ParserImplConstants.RPAREN))
					return false;
				if (!match(ParserImplConstants.ARROW))
					return false;
				if (!matchLambdaBody())
					return false;
			} else if (matchNext(ParserImplConstants.NODE_VARIABLE, ParserImplConstants.BYTE, ParserImplConstants.FLOAT, ParserImplConstants.VOLATILE, ParserImplConstants.PROTECTED, ParserImplConstants.PUBLIC, ParserImplConstants.LONG, ParserImplConstants.DOUBLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_LIST_VARIABLE, ParserImplConstants.STRICTFP, ParserImplConstants.AT, ParserImplConstants.ABSTRACT, ParserImplConstants.PRIVATE, ParserImplConstants.TRANSIENT, ParserImplConstants._DEFAULT, ParserImplConstants.FINAL, ParserImplConstants.CHAR, ParserImplConstants.STATIC, ParserImplConstants.INT, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.SHORT, ParserImplConstants.BOOLEAN, ParserImplConstants.NATIVE)) {
				if (!matchFormalParameterList())
					return false;
				if (!match(ParserImplConstants.RPAREN))
					return false;
				if (!match(ParserImplConstants.ARROW))
					return false;
				if (!matchLambdaBody())
					return false;
			} else if (matchNext(ParserImplConstants.LONG_LITERAL, ParserImplConstants.BYTE, ParserImplConstants.TRUE, ParserImplConstants.SUPER, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.LPAREN, ParserImplConstants.VOID, ParserImplConstants.LONG, ParserImplConstants.PLUS, ParserImplConstants.BANG, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.THIS, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.DECR, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.FLOAT, ParserImplConstants.NEW, ParserImplConstants.DOUBLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.STRING_LITERAL, ParserImplConstants.FALSE, ParserImplConstants.TILDE, ParserImplConstants.CHAR, ParserImplConstants.INCR, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.MINUS, ParserImplConstants.BOOLEAN, ParserImplConstants.NULL)) {
				if (!matchExpression())
					return false;
				if (!match(ParserImplConstants.RPAREN))
					return false;
			}
		}
		return true;
	}

	private boolean matchAssignmentOperator() {
		if (matchNext(ParserImplConstants.ASSIGN)) {
			if (!match(ParserImplConstants.ASSIGN))
				return false;
		} else if (matchNext(ParserImplConstants.STARASSIGN)) {
			if (!match(ParserImplConstants.STARASSIGN))
				return false;
		} else if (matchNext(ParserImplConstants.SLASHASSIGN)) {
			if (!match(ParserImplConstants.SLASHASSIGN))
				return false;
		} else if (matchNext(ParserImplConstants.REMASSIGN)) {
			if (!match(ParserImplConstants.REMASSIGN))
				return false;
		} else if (matchNext(ParserImplConstants.PLUSASSIGN)) {
			if (!match(ParserImplConstants.PLUSASSIGN))
				return false;
		} else if (matchNext(ParserImplConstants.MINUSASSIGN)) {
			if (!match(ParserImplConstants.MINUSASSIGN))
				return false;
		} else if (matchNext(ParserImplConstants.LSHIFTASSIGN)) {
			if (!match(ParserImplConstants.LSHIFTASSIGN))
				return false;
		} else if (matchNext(ParserImplConstants.RSIGNEDSHIFTASSIGN)) {
			if (!match(ParserImplConstants.RSIGNEDSHIFTASSIGN))
				return false;
		} else if (matchNext(ParserImplConstants.RUNSIGNEDSHIFTASSIGN)) {
			if (!match(ParserImplConstants.RUNSIGNEDSHIFTASSIGN))
				return false;
		} else if (matchNext(ParserImplConstants.ANDASSIGN)) {
			if (!match(ParserImplConstants.ANDASSIGN))
				return false;
		} else if (matchNext(ParserImplConstants.XORASSIGN)) {
			if (!match(ParserImplConstants.XORASSIGN))
				return false;
		} else if (matchNext(ParserImplConstants.ORASSIGN)) {
			if (!match(ParserImplConstants.ORASSIGN))
				return false;
		}
		return true;
	}

	private boolean matchInferredFormalParameterList() {
		if (!matchInferredFormalParameter())
			return false;
		while (matchNext(ParserImplConstants.COMMA)) {
			if (!match(ParserImplConstants.COMMA))
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
		if (!match(ParserImplConstants.NODE_LIST_VARIABLE))
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
		if (matchNext(ParserImplConstants.LONG_LITERAL, ParserImplConstants.BYTE, ParserImplConstants.TRUE, ParserImplConstants.SUPER, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.LPAREN, ParserImplConstants.VOID, ParserImplConstants.LONG, ParserImplConstants.PLUS, ParserImplConstants.BANG, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.THIS, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.DECR, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.FLOAT, ParserImplConstants.NEW, ParserImplConstants.DOUBLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.STRING_LITERAL, ParserImplConstants.FALSE, ParserImplConstants.TILDE, ParserImplConstants.CHAR, ParserImplConstants.INCR, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.MINUS, ParserImplConstants.BOOLEAN, ParserImplConstants.NULL)) {
			if (!matchExpression())
				return false;
		} else if (matchNext(ParserImplConstants.LBRACE)) {
			if (!matchBlock())
				return false;
		}
		return true;
	}

	private boolean matchReferenceCastTypeRest() {
		if (matchReferenceCastTypeRest1()) {
			do {
				if (!match(ParserImplConstants.BIT_AND))
					return false;
				if (!matchAnnotations())
					return false;
				if (!matchReferenceType())
					return false;
			} while (matchNext(ParserImplConstants.BIT_AND));
		}
		return true;
	}

	private boolean matchUnaryExpressionNotPlusMinus() {
		if (matchNext(ParserImplConstants.TILDE, ParserImplConstants.BANG)) {
			if (matchNext(ParserImplConstants.TILDE)) {
				if (!match(ParserImplConstants.TILDE))
					return false;
			} else if (matchNext(ParserImplConstants.BANG)) {
				if (!match(ParserImplConstants.BANG))
					return false;
			}
			if (!matchUnaryExpression())
				return false;
		} else if (matchUnaryExpressionNotPlusMinus1()) {
			if (!matchCastExpression())
				return false;
		} else if (matchNext(ParserImplConstants.LONG_LITERAL, ParserImplConstants.BYTE, ParserImplConstants.TRUE, ParserImplConstants.SUPER, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.LPAREN, ParserImplConstants.VOID, ParserImplConstants.LONG, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.THIS, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.FLOAT, ParserImplConstants.NEW, ParserImplConstants.DOUBLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.STRING_LITERAL, ParserImplConstants.FALSE, ParserImplConstants.CHAR, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.BOOLEAN, ParserImplConstants.NULL)) {
			if (!matchPostfixExpression())
				return false;
		}
		return true;
	}

	private boolean matchQualifiedType() {
		if (!matchName())
			return false;
		if (matchNext(ParserImplConstants.LT)) {
			if (!matchTypeArgumentsOrDiamond())
				return false;
		}
		while (matchNext(ParserImplConstants.DOT)) {
			if (!match(ParserImplConstants.DOT))
				return false;
			if (!matchAnnotations())
				return false;
			if (!matchName())
				return false;
			if (matchNext(ParserImplConstants.LT)) {
				if (!matchTypeArgumentsOrDiamond())
					return false;
			}
		}
		return true;
	}

	private boolean matchAllocationExpression() {
		if (!match(ParserImplConstants.NEW))
			return false;
		if (matchNext(ParserImplConstants.LT)) {
			if (!matchTypeArguments())
				return false;
		}
		if (!matchAnnotations())
			return false;
		if (matchNext(ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.FLOAT, ParserImplConstants.INT, ParserImplConstants.LONG, ParserImplConstants.SHORT, ParserImplConstants.BOOLEAN, ParserImplConstants.DOUBLE)) {
			if (!matchPrimitiveType())
				return false;
			if (!matchArrayCreationExpr())
				return false;
		} else if (matchNext(ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER)) {
			if (!matchQualifiedType())
				return false;
			if (matchNext(ParserImplConstants.AT, ParserImplConstants.LBRACKET)) {
				if (!matchArrayCreationExpr())
					return false;
			} else if (matchNext(ParserImplConstants.LPAREN)) {
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
		if (matchNext(ParserImplConstants.AT, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.HOOK, ParserImplConstants.FLOAT, ParserImplConstants.INT, ParserImplConstants.LONG, ParserImplConstants.SHORT, ParserImplConstants.BOOLEAN, ParserImplConstants.DOUBLE, ParserImplConstants.IDENTIFIER)) {
			if (!matchTypeArgument())
				return false;
			while (matchNext(ParserImplConstants.COMMA)) {
				if (!match(ParserImplConstants.COMMA))
					return false;
				if (!matchTypeArgument())
					return false;
			}
		} else if (matchNext(ParserImplConstants.NODE_LIST_VARIABLE)) {
			if (!match(ParserImplConstants.NODE_LIST_VARIABLE))
				return false;
		}
		return true;
	}

	private boolean matchPrimitiveType() {
		if (matchNext(ParserImplConstants.BOOLEAN)) {
			if (!match(ParserImplConstants.BOOLEAN))
				return false;
		} else if (matchNext(ParserImplConstants.CHAR)) {
			if (!match(ParserImplConstants.CHAR))
				return false;
		} else if (matchNext(ParserImplConstants.BYTE)) {
			if (!match(ParserImplConstants.BYTE))
				return false;
		} else if (matchNext(ParserImplConstants.SHORT)) {
			if (!match(ParserImplConstants.SHORT))
				return false;
		} else if (matchNext(ParserImplConstants.INT)) {
			if (!match(ParserImplConstants.INT))
				return false;
		} else if (matchNext(ParserImplConstants.LONG)) {
			if (!match(ParserImplConstants.LONG))
				return false;
		} else if (matchNext(ParserImplConstants.FLOAT)) {
			if (!match(ParserImplConstants.FLOAT))
				return false;
		} else if (matchNext(ParserImplConstants.DOUBLE)) {
			if (!match(ParserImplConstants.DOUBLE))
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
			if (!match(ParserImplConstants.LBRACKET))
				return false;
			if (!match(ParserImplConstants.RBRACKET))
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
		while (matchNext(ParserImplConstants.COMMA)) {
			if (!match(ParserImplConstants.COMMA))
				return false;
			if (!matchVariableDeclarator())
				return false;
		}
		return true;
	}

	private boolean matchTypeArgumentsOrDiamond() {
		if (!match(ParserImplConstants.LT))
			return false;
		if (matchNext(ParserImplConstants.NODE_VARIABLE, ParserImplConstants.BYTE, ParserImplConstants.HOOK, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.DOUBLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_LIST_VARIABLE, ParserImplConstants.AT, ParserImplConstants.CHAR, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.BOOLEAN)) {
			if (!matchTypeArgumentList())
				return false;
		}
		if (!match(ParserImplConstants.GT))
			return false;
		return true;
	}

	private boolean matchSingleMemberAnnotation() {
		if (!match(ParserImplConstants.AT))
			return false;
		if (!matchQualifiedName())
			return false;
		if (!match(ParserImplConstants.LPAREN))
			return false;
		if (!matchMemberValue())
			return false;
		if (!match(ParserImplConstants.RPAREN))
			return false;
		return true;
	}

	private boolean matchTypeArgument() {
		if (!matchAnnotations())
			return false;
		if (matchNext(ParserImplConstants.NODE_VARIABLE, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.FLOAT, ParserImplConstants.INT, ParserImplConstants.LONG, ParserImplConstants.SHORT, ParserImplConstants.BOOLEAN, ParserImplConstants.DOUBLE, ParserImplConstants.IDENTIFIER)) {
			if (!matchReferenceType())
				return false;
		} else if (matchNext(ParserImplConstants.HOOK)) {
			if (!matchWildcard())
				return false;
		}
		return true;
	}

	private boolean matchClassOrInterfaceBody() {
		if (!match(ParserImplConstants.LBRACE))
			return false;
		if (!matchClassOrInterfaceBodyDecls())
			return false;
		if (!match(ParserImplConstants.RBRACE))
			return false;
		return true;
	}

	private boolean matchReferenceType() {
		if (matchNext(ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.FLOAT, ParserImplConstants.INT, ParserImplConstants.LONG, ParserImplConstants.SHORT, ParserImplConstants.BOOLEAN, ParserImplConstants.DOUBLE)) {
			if (!matchPrimitiveType())
				return false;
			if (!matchArrayDimsMandatory())
				return false;
		} else if (matchNext(ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER)) {
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
		if (!match(ParserImplConstants.EXTENDS))
			return false;
		if (matchNext(ParserImplConstants.AT, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER)) {
			if (!matchAnnotatedQualifiedType())
				return false;
			while (matchNext(ParserImplConstants.BIT_AND)) {
				if (!match(ParserImplConstants.BIT_AND))
					return false;
				if (!matchAnnotatedQualifiedType())
					return false;
			}
		} else if (matchNext(ParserImplConstants.NODE_LIST_VARIABLE)) {
			if (!matchNodeListVar())
				return false;
		}
		return true;
	}

	private boolean matchFormalParameterList() {
		if (matchNext(ParserImplConstants.NODE_VARIABLE, ParserImplConstants.BYTE, ParserImplConstants.FLOAT, ParserImplConstants.VOLATILE, ParserImplConstants.PROTECTED, ParserImplConstants.PUBLIC, ParserImplConstants.LONG, ParserImplConstants.DOUBLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.STRICTFP, ParserImplConstants.AT, ParserImplConstants.ABSTRACT, ParserImplConstants.PRIVATE, ParserImplConstants.TRANSIENT, ParserImplConstants._DEFAULT, ParserImplConstants.FINAL, ParserImplConstants.CHAR, ParserImplConstants.STATIC, ParserImplConstants.INT, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.SHORT, ParserImplConstants.BOOLEAN, ParserImplConstants.NATIVE)) {
			if (!matchFormalParameter())
				return false;
			while (matchNext(ParserImplConstants.COMMA)) {
				if (!match(ParserImplConstants.COMMA))
					return false;
				if (!matchFormalParameter())
					return false;
			}
		} else if (matchNext(ParserImplConstants.NODE_LIST_VARIABLE)) {
			if (!matchNodeListVar())
				return false;
		}
		return true;
	}

	private boolean matchMarkerAnnotation() {
		if (!match(ParserImplConstants.AT))
			return false;
		if (!matchQualifiedName())
			return false;
		return true;
	}

	private boolean matchMethodReferenceSuffix() {
		if (!match(ParserImplConstants.DOUBLECOLON))
			return false;
		if (matchNext(ParserImplConstants.LT)) {
			if (!matchTypeArguments())
				return false;
		}
		if (matchNext(ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER)) {
			if (!matchName())
				return false;
		} else if (matchNext(ParserImplConstants.NEW)) {
			if (!match(ParserImplConstants.NEW))
				return false;
		}
		return true;
	}

	private boolean matchLiteral() {
		if (matchNext(ParserImplConstants.INTEGER_LITERAL)) {
			if (!match(ParserImplConstants.INTEGER_LITERAL))
				return false;
		} else if (matchNext(ParserImplConstants.LONG_LITERAL)) {
			if (!match(ParserImplConstants.LONG_LITERAL))
				return false;
		} else if (matchNext(ParserImplConstants.FLOAT_LITERAL)) {
			if (!match(ParserImplConstants.FLOAT_LITERAL))
				return false;
		} else if (matchNext(ParserImplConstants.DOUBLE_LITERAL)) {
			if (!match(ParserImplConstants.DOUBLE_LITERAL))
				return false;
		} else if (matchNext(ParserImplConstants.CHARACTER_LITERAL)) {
			if (!match(ParserImplConstants.CHARACTER_LITERAL))
				return false;
		} else if (matchNext(ParserImplConstants.STRING_LITERAL)) {
			if (!match(ParserImplConstants.STRING_LITERAL))
				return false;
		} else if (matchNext(ParserImplConstants.TRUE)) {
			if (!match(ParserImplConstants.TRUE))
				return false;
		} else if (matchNext(ParserImplConstants.FALSE)) {
			if (!match(ParserImplConstants.FALSE))
				return false;
		} else if (matchNext(ParserImplConstants.NULL)) {
			if (!match(ParserImplConstants.NULL))
				return false;
		}
		return true;
	}

	private boolean matchPrefixExpression() {
		if (matchNext(ParserImplConstants.INCR)) {
			if (!match(ParserImplConstants.INCR))
				return false;
		} else if (matchNext(ParserImplConstants.DECR)) {
			if (!match(ParserImplConstants.DECR))
				return false;
		}
		if (!matchUnaryExpression())
			return false;
		return true;
	}

	private boolean matchNormalAnnotation() {
		if (!match(ParserImplConstants.AT))
			return false;
		if (!matchQualifiedName())
			return false;
		if (!match(ParserImplConstants.LPAREN))
			return false;
		if (matchNext(ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER)) {
			if (!matchMemberValuePairs())
				return false;
		}
		if (!match(ParserImplConstants.RPAREN))
			return false;
		return true;
	}

	private boolean matchBlock() {
		if (!match(ParserImplConstants.LBRACE))
			return false;
		if (!matchStatements())
			return false;
		if (!match(ParserImplConstants.RBRACE))
			return false;
		return true;
	}

	private boolean matchPostfixExpression() {
		if (!matchPrimaryExpression())
			return false;
		if (matchNext(ParserImplConstants.INCR, ParserImplConstants.DECR)) {
			if (matchNext(ParserImplConstants.INCR)) {
				if (!match(ParserImplConstants.INCR))
					return false;
			} else if (matchNext(ParserImplConstants.DECR)) {
				if (!match(ParserImplConstants.DECR))
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
		} else if (matchNext(ParserImplConstants.AT, ParserImplConstants.LBRACKET)) {
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
		while (matchNext(ParserImplConstants.SC_OR)) {
			if (!match(ParserImplConstants.SC_OR))
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
		if (matchNext(ParserImplConstants.ELLIPSIS)) {
			if (!match(ParserImplConstants.ELLIPSIS))
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
		while (matchNext(ParserImplConstants.LBRACKET, ParserImplConstants.DOT, ParserImplConstants.DOUBLECOLON)) {
			if (!matchPrimarySuffix())
				return false;
		}
		return true;
	}

	private boolean matchArrayDimExprsMandatory() {
		do {
			if (!matchAnnotations())
				return false;
			if (!match(ParserImplConstants.LBRACKET))
				return false;
			if (!matchExpression())
				return false;
			if (!match(ParserImplConstants.RBRACKET))
				return false;
		} while (matchArrayDimExprsMandatory1());
		return true;
	}

	private boolean matchClassOrInterfaceBodyDecls() {
		if (matchNext(ParserImplConstants.ENUM, ParserImplConstants.BYTE, ParserImplConstants.VOID, ParserImplConstants.LONG, ParserImplConstants.PUBLIC, ParserImplConstants.ABSTRACT, ParserImplConstants.TRANSIENT, ParserImplConstants._DEFAULT, ParserImplConstants.FINAL, ParserImplConstants.STATIC, ParserImplConstants.INT, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.SHORT, ParserImplConstants.CLASS, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.FLOAT, ParserImplConstants.LBRACE, ParserImplConstants.VOLATILE, ParserImplConstants.SEMICOLON, ParserImplConstants.PROTECTED, ParserImplConstants.DOUBLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.STRICTFP, ParserImplConstants.NODE_LIST_VARIABLE, ParserImplConstants.AT, ParserImplConstants.PRIVATE, ParserImplConstants.CHAR, ParserImplConstants.INTERFACE, ParserImplConstants.BOOLEAN, ParserImplConstants.NATIVE)) {
			if (matchNext(ParserImplConstants.ENUM, ParserImplConstants.BYTE, ParserImplConstants.VOID, ParserImplConstants.LONG, ParserImplConstants.PUBLIC, ParserImplConstants.ABSTRACT, ParserImplConstants.TRANSIENT, ParserImplConstants._DEFAULT, ParserImplConstants.FINAL, ParserImplConstants.STATIC, ParserImplConstants.INT, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.SHORT, ParserImplConstants.CLASS, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.FLOAT, ParserImplConstants.LBRACE, ParserImplConstants.VOLATILE, ParserImplConstants.SEMICOLON, ParserImplConstants.PROTECTED, ParserImplConstants.IDENTIFIER, ParserImplConstants.DOUBLE, ParserImplConstants.STRICTFP, ParserImplConstants.AT, ParserImplConstants.PRIVATE, ParserImplConstants.CHAR, ParserImplConstants.INTERFACE, ParserImplConstants.BOOLEAN, ParserImplConstants.NATIVE)) {
				do {
					if (!matchClassOrInterfaceBodyDecl())
						return false;
				} while (matchNext(ParserImplConstants.ENUM, ParserImplConstants.BYTE, ParserImplConstants.VOID, ParserImplConstants.LONG, ParserImplConstants.PUBLIC, ParserImplConstants.ABSTRACT, ParserImplConstants.TRANSIENT, ParserImplConstants._DEFAULT, ParserImplConstants.FINAL, ParserImplConstants.STATIC, ParserImplConstants.INT, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.SHORT, ParserImplConstants.CLASS, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.FLOAT, ParserImplConstants.LBRACE, ParserImplConstants.VOLATILE, ParserImplConstants.SEMICOLON, ParserImplConstants.PROTECTED, ParserImplConstants.IDENTIFIER, ParserImplConstants.DOUBLE, ParserImplConstants.STRICTFP, ParserImplConstants.AT, ParserImplConstants.PRIVATE, ParserImplConstants.CHAR, ParserImplConstants.INTERFACE, ParserImplConstants.BOOLEAN, ParserImplConstants.NATIVE));
			} else if (matchNext(ParserImplConstants.NODE_LIST_VARIABLE)) {
				if (!matchNodeListVar())
					return false;
			}
		}
		return true;
	}

	private boolean matchVariableDeclarator() {
		if (!matchVariableDeclaratorId())
			return false;
		if (matchNext(ParserImplConstants.ASSIGN)) {
			if (!match(ParserImplConstants.ASSIGN))
				return false;
			if (!matchVariableInitializer())
				return false;
		}
		return true;
	}

	private boolean matchMemberValuePairs() {
		if (!matchMemberValuePair())
			return false;
		while (matchNext(ParserImplConstants.COMMA)) {
			if (!match(ParserImplConstants.COMMA))
				return false;
			if (!matchMemberValuePair())
				return false;
		}
		return true;
	}

	private boolean matchWildcard() {
		if (!match(ParserImplConstants.HOOK))
			return false;
		if (matchNext(ParserImplConstants.EXTENDS, ParserImplConstants.SUPER)) {
			if (matchNext(ParserImplConstants.EXTENDS)) {
				if (!match(ParserImplConstants.EXTENDS))
					return false;
				if (!matchAnnotations())
					return false;
				if (!matchReferenceType())
					return false;
			} else if (matchNext(ParserImplConstants.SUPER)) {
				if (!match(ParserImplConstants.SUPER))
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
		if (matchNext(ParserImplConstants.AT)) {
			if (!matchAnnotation())
				return false;
		} else if (matchNext(ParserImplConstants.LBRACE)) {
			if (!matchMemberValueArrayInitializer())
				return false;
		} else if (matchNext(ParserImplConstants.LONG_LITERAL, ParserImplConstants.BYTE, ParserImplConstants.TRUE, ParserImplConstants.SUPER, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.LPAREN, ParserImplConstants.VOID, ParserImplConstants.LONG, ParserImplConstants.PLUS, ParserImplConstants.BANG, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.THIS, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.DECR, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.FLOAT, ParserImplConstants.NEW, ParserImplConstants.DOUBLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.FALSE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.TILDE, ParserImplConstants.CHAR, ParserImplConstants.INCR, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.MINUS, ParserImplConstants.BOOLEAN, ParserImplConstants.NULL)) {
			if (!matchConditionalExpression())
				return false;
		}
		return true;
	}

	private boolean matchArrayDims() {
		while (matchArrayDims1()) {
			if (!matchAnnotations())
				return false;
			if (!match(ParserImplConstants.LBRACKET))
				return false;
			if (!match(ParserImplConstants.RBRACKET))
				return false;
		}
		return true;
	}

	private boolean matchConditionalAndExpression() {
		if (!matchInclusiveOrExpression())
			return false;
		while (matchNext(ParserImplConstants.SC_AND)) {
			if (!match(ParserImplConstants.SC_AND))
				return false;
			if (!matchInclusiveOrExpression())
				return false;
		}
		return true;
	}

	private boolean matchStatements() {
		if (matchNext(ParserImplConstants.LONG_LITERAL, ParserImplConstants.BYTE, ParserImplConstants.TRUE, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.LPAREN, ParserImplConstants.VOID, ParserImplConstants.LONG, ParserImplConstants.FINAL, ParserImplConstants.THIS, ParserImplConstants.INT, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.RETURN, ParserImplConstants.CLASS, ParserImplConstants.LBRACE, ParserImplConstants.FLOAT, ParserImplConstants.VOLATILE, ParserImplConstants.SEMICOLON, ParserImplConstants.IDENTIFIER, ParserImplConstants.AT, ParserImplConstants.BREAK, ParserImplConstants.INTERFACE, ParserImplConstants.FOR, ParserImplConstants.BOOLEAN, ParserImplConstants.NULL, ParserImplConstants.SWITCH, ParserImplConstants.ASSERT, ParserImplConstants.SUPER, ParserImplConstants.PUBLIC, ParserImplConstants.TRY, ParserImplConstants.IF, ParserImplConstants.WHILE, ParserImplConstants.TRANSIENT, ParserImplConstants.ABSTRACT, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.DO, ParserImplConstants.STATIC, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.DECR, ParserImplConstants.SHORT, ParserImplConstants.THROW, ParserImplConstants.LT, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.NEW, ParserImplConstants.PROTECTED, ParserImplConstants.CONTINUE, ParserImplConstants.DOUBLE, ParserImplConstants.FALSE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.STRICTFP, ParserImplConstants.NODE_LIST_VARIABLE, ParserImplConstants.PRIVATE, ParserImplConstants.CHAR, ParserImplConstants.INCR, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.NATIVE)) {
			if (matchNext(ParserImplConstants.LONG_LITERAL, ParserImplConstants.BYTE, ParserImplConstants.TRUE, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.LPAREN, ParserImplConstants.VOID, ParserImplConstants.LONG, ParserImplConstants.FINAL, ParserImplConstants.THIS, ParserImplConstants.INT, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.RETURN, ParserImplConstants.CLASS, ParserImplConstants.LBRACE, ParserImplConstants.FLOAT, ParserImplConstants.VOLATILE, ParserImplConstants.SEMICOLON, ParserImplConstants.IDENTIFIER, ParserImplConstants.AT, ParserImplConstants.BREAK, ParserImplConstants.INTERFACE, ParserImplConstants.FOR, ParserImplConstants.BOOLEAN, ParserImplConstants.NULL, ParserImplConstants.SWITCH, ParserImplConstants.ASSERT, ParserImplConstants.SUPER, ParserImplConstants.PUBLIC, ParserImplConstants.TRY, ParserImplConstants.IF, ParserImplConstants.WHILE, ParserImplConstants.TRANSIENT, ParserImplConstants.ABSTRACT, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.DO, ParserImplConstants.STATIC, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.DECR, ParserImplConstants.SHORT, ParserImplConstants.THROW, ParserImplConstants.LT, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.NEW, ParserImplConstants.PROTECTED, ParserImplConstants.CONTINUE, ParserImplConstants.DOUBLE, ParserImplConstants.FALSE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.STRICTFP, ParserImplConstants.PRIVATE, ParserImplConstants.CHAR, ParserImplConstants.INCR, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.NATIVE)) {
				do {
					if (!matchBlockStatement())
						return false;
				} while (matchNext(ParserImplConstants.LONG_LITERAL, ParserImplConstants.BYTE, ParserImplConstants.TRUE, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.LPAREN, ParserImplConstants.VOID, ParserImplConstants.LONG, ParserImplConstants.FINAL, ParserImplConstants.THIS, ParserImplConstants.INT, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.RETURN, ParserImplConstants.CLASS, ParserImplConstants.LBRACE, ParserImplConstants.FLOAT, ParserImplConstants.VOLATILE, ParserImplConstants.SEMICOLON, ParserImplConstants.IDENTIFIER, ParserImplConstants.AT, ParserImplConstants.BREAK, ParserImplConstants.INTERFACE, ParserImplConstants.FOR, ParserImplConstants.BOOLEAN, ParserImplConstants.NULL, ParserImplConstants.SWITCH, ParserImplConstants.ASSERT, ParserImplConstants.SUPER, ParserImplConstants.PUBLIC, ParserImplConstants.TRY, ParserImplConstants.IF, ParserImplConstants.WHILE, ParserImplConstants.TRANSIENT, ParserImplConstants.ABSTRACT, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.DO, ParserImplConstants.STATIC, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.DECR, ParserImplConstants.SHORT, ParserImplConstants.THROW, ParserImplConstants.LT, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.NEW, ParserImplConstants.PROTECTED, ParserImplConstants.CONTINUE, ParserImplConstants.DOUBLE, ParserImplConstants.FALSE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.STRICTFP, ParserImplConstants.PRIVATE, ParserImplConstants.CHAR, ParserImplConstants.INCR, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.NATIVE));
			} else if (matchNext(ParserImplConstants.NODE_LIST_VARIABLE)) {
				if (!matchNodeListVar())
					return false;
			}
		}
		return true;
	}

	private boolean matchArrayInitializer() {
		if (!match(ParserImplConstants.LBRACE))
			return false;
		if (matchNext(ParserImplConstants.LONG_LITERAL, ParserImplConstants.BYTE, ParserImplConstants.TRUE, ParserImplConstants.SUPER, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.LPAREN, ParserImplConstants.VOID, ParserImplConstants.LONG, ParserImplConstants.PLUS, ParserImplConstants.BANG, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.THIS, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.INT, ParserImplConstants.DECR, ParserImplConstants.SHORT, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.FLOAT, ParserImplConstants.LBRACE, ParserImplConstants.NEW, ParserImplConstants.IDENTIFIER, ParserImplConstants.DOUBLE, ParserImplConstants.FALSE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.TILDE, ParserImplConstants.CHAR, ParserImplConstants.INCR, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.MINUS, ParserImplConstants.BOOLEAN, ParserImplConstants.NULL)) {
			if (!matchVariableInitializer())
				return false;
			while (matchNext(ParserImplConstants.COMMA)) {
				if (!match(ParserImplConstants.COMMA))
					return false;
				if (!matchVariableInitializer())
					return false;
			}
		}
		if (matchNext(ParserImplConstants.COMMA)) {
			if (!match(ParserImplConstants.COMMA))
				return false;
		}
		if (!match(ParserImplConstants.RBRACE))
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
		if (matchNext(ParserImplConstants.SEMICOLON)) {
			if (!match(ParserImplConstants.SEMICOLON))
				return false;
		} else if (matchNext(ParserImplConstants.ENUM, ParserImplConstants.BYTE, ParserImplConstants.VOID, ParserImplConstants.LONG, ParserImplConstants.PUBLIC, ParserImplConstants.ABSTRACT, ParserImplConstants.TRANSIENT, ParserImplConstants._DEFAULT, ParserImplConstants.FINAL, ParserImplConstants.STATIC, ParserImplConstants.INT, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.SHORT, ParserImplConstants.CLASS, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.FLOAT, ParserImplConstants.LBRACE, ParserImplConstants.VOLATILE, ParserImplConstants.PROTECTED, ParserImplConstants.DOUBLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.STRICTFP, ParserImplConstants.AT, ParserImplConstants.PRIVATE, ParserImplConstants.CHAR, ParserImplConstants.INTERFACE, ParserImplConstants.BOOLEAN, ParserImplConstants.NATIVE)) {
			if (!matchModifiers())
				return false;
			if (matchNext(ParserImplConstants.LBRACE)) {
				if (!matchInitializerDecl())
					return false;
			} else if (matchNext(ParserImplConstants.CLASS, ParserImplConstants.INTERFACE)) {
				if (!matchClassOrInterfaceDecl())
					return false;
			} else if (matchNext(ParserImplConstants.ENUM)) {
				if (!matchEnumDecl())
					return false;
			} else if (matchNext(ParserImplConstants.AT)) {
				if (!matchAnnotationTypeDecl())
					return false;
			} else if (matchClassOrInterfaceBodyDecl1()) {
				if (!matchConstructorDecl())
					return false;
			} else if (matchClassOrInterfaceBodyDecl2()) {
				if (!matchFieldDecl())
					return false;
			} else if (matchNext(ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.VOID, ParserImplConstants.FLOAT, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.LONG, ParserImplConstants.BOOLEAN, ParserImplConstants.IDENTIFIER, ParserImplConstants.DOUBLE)) {
				if (!matchMethodDecl())
					return false;
			}
		}
		return true;
	}

	private boolean matchMemberValuePair() {
		if (!matchName())
			return false;
		if (!match(ParserImplConstants.ASSIGN))
			return false;
		if (!matchMemberValue())
			return false;
		return true;
	}

	private boolean matchMemberValueArrayInitializer() {
		if (!match(ParserImplConstants.LBRACE))
			return false;
		if (matchNext(ParserImplConstants.LONG_LITERAL, ParserImplConstants.BYTE, ParserImplConstants.TRUE, ParserImplConstants.SUPER, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.LPAREN, ParserImplConstants.VOID, ParserImplConstants.LONG, ParserImplConstants.PLUS, ParserImplConstants.BANG, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.THIS, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.DECR, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.FLOAT, ParserImplConstants.LBRACE, ParserImplConstants.NEW, ParserImplConstants.DOUBLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.FALSE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.AT, ParserImplConstants.TILDE, ParserImplConstants.CHAR, ParserImplConstants.INCR, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.MINUS, ParserImplConstants.BOOLEAN, ParserImplConstants.NULL)) {
			if (!matchMemberValue())
				return false;
			while (matchNext(ParserImplConstants.COMMA)) {
				if (!match(ParserImplConstants.COMMA))
					return false;
				if (!matchMemberValue())
					return false;
			}
		}
		if (matchNext(ParserImplConstants.COMMA)) {
			if (!match(ParserImplConstants.COMMA))
				return false;
		}
		if (!match(ParserImplConstants.RBRACE))
			return false;
		return true;
	}

	private boolean matchInclusiveOrExpression() {
		if (!matchExclusiveOrExpression())
			return false;
		while (matchNext(ParserImplConstants.BIT_OR)) {
			if (!match(ParserImplConstants.BIT_OR))
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
			if (!match(ParserImplConstants.SEMICOLON))
				return false;
		} else if (matchNext(ParserImplConstants.LONG_LITERAL, ParserImplConstants.SWITCH, ParserImplConstants.ASSERT, ParserImplConstants.BYTE, ParserImplConstants.TRUE, ParserImplConstants.SUPER, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.LPAREN, ParserImplConstants.VOID, ParserImplConstants.LONG, ParserImplConstants.TRY, ParserImplConstants.IF, ParserImplConstants.WHILE, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.THIS, ParserImplConstants.DO, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.INT, ParserImplConstants.RETURN, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.SHORT, ParserImplConstants.DECR, ParserImplConstants.THROW, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.FLOAT, ParserImplConstants.LBRACE, ParserImplConstants.NEW, ParserImplConstants.SEMICOLON, ParserImplConstants.CONTINUE, ParserImplConstants.DOUBLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.FALSE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.BREAK, ParserImplConstants.CHAR, ParserImplConstants.INCR, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.FOR, ParserImplConstants.BOOLEAN, ParserImplConstants.NULL)) {
			if (!matchStatement())
				return false;
		}
		return true;
	}

	private boolean matchPrimarySuffix() {
		if (matchNext(ParserImplConstants.LBRACKET, ParserImplConstants.DOT)) {
			if (!matchPrimarySuffixWithoutSuper())
				return false;
		} else if (matchNext(ParserImplConstants.DOT)) {
			if (!match(ParserImplConstants.DOT))
				return false;
			if (!match(ParserImplConstants.SUPER))
				return false;
		} else if (matchNext(ParserImplConstants.DOUBLECOLON)) {
			if (!matchMethodReferenceSuffix())
				return false;
		}
		return true;
	}

	private boolean matchVariableInitializer() {
		if (matchNext(ParserImplConstants.LBRACE)) {
			if (!matchArrayInitializer())
				return false;
		} else if (matchNext(ParserImplConstants.LONG_LITERAL, ParserImplConstants.BYTE, ParserImplConstants.TRUE, ParserImplConstants.SUPER, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.LPAREN, ParserImplConstants.VOID, ParserImplConstants.LONG, ParserImplConstants.PLUS, ParserImplConstants.BANG, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.THIS, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.DECR, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.FLOAT, ParserImplConstants.NEW, ParserImplConstants.IDENTIFIER, ParserImplConstants.DOUBLE, ParserImplConstants.FALSE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.TILDE, ParserImplConstants.CHAR, ParserImplConstants.INCR, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.MINUS, ParserImplConstants.BOOLEAN, ParserImplConstants.NULL)) {
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
		if (!match(ParserImplConstants.SEMICOLON))
			return false;
		return true;
	}

	private boolean matchConstructorDecl() {
		if (matchNext(ParserImplConstants.LT)) {
			if (!matchTypeParameters())
				return false;
		}
		if (!matchName())
			return false;
		if (!matchFormalParameters())
			return false;
		if (matchNext(ParserImplConstants.THROWS)) {
			if (!matchThrowsClause())
				return false;
		}
		if (!match(ParserImplConstants.LBRACE))
			return false;
		if (matchNext(ParserImplConstants.LONG_LITERAL, ParserImplConstants.BYTE, ParserImplConstants.TRUE, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.LPAREN, ParserImplConstants.VOID, ParserImplConstants.LONG, ParserImplConstants.FINAL, ParserImplConstants.THIS, ParserImplConstants.INT, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.RETURN, ParserImplConstants.CLASS, ParserImplConstants.LBRACE, ParserImplConstants.FLOAT, ParserImplConstants.VOLATILE, ParserImplConstants.SEMICOLON, ParserImplConstants.IDENTIFIER, ParserImplConstants.AT, ParserImplConstants.BREAK, ParserImplConstants.INTERFACE, ParserImplConstants.FOR, ParserImplConstants.BOOLEAN, ParserImplConstants.NULL, ParserImplConstants.SWITCH, ParserImplConstants.ASSERT, ParserImplConstants.SUPER, ParserImplConstants.PUBLIC, ParserImplConstants.TRY, ParserImplConstants.IF, ParserImplConstants.WHILE, ParserImplConstants.TRANSIENT, ParserImplConstants.ABSTRACT, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.DO, ParserImplConstants.STATIC, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.DECR, ParserImplConstants.SHORT, ParserImplConstants.THROW, ParserImplConstants.LT, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.NEW, ParserImplConstants.PROTECTED, ParserImplConstants.CONTINUE, ParserImplConstants.DOUBLE, ParserImplConstants.FALSE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.STRICTFP, ParserImplConstants.NODE_LIST_VARIABLE, ParserImplConstants.PRIVATE, ParserImplConstants.CHAR, ParserImplConstants.INCR, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.NATIVE)) {
			if (matchNext(ParserImplConstants.LONG_LITERAL, ParserImplConstants.BYTE, ParserImplConstants.TRUE, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.LPAREN, ParserImplConstants.VOID, ParserImplConstants.LONG, ParserImplConstants.FINAL, ParserImplConstants.THIS, ParserImplConstants.INT, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.RETURN, ParserImplConstants.CLASS, ParserImplConstants.LBRACE, ParserImplConstants.FLOAT, ParserImplConstants.VOLATILE, ParserImplConstants.SEMICOLON, ParserImplConstants.IDENTIFIER, ParserImplConstants.AT, ParserImplConstants.BREAK, ParserImplConstants.INTERFACE, ParserImplConstants.FOR, ParserImplConstants.BOOLEAN, ParserImplConstants.NULL, ParserImplConstants.SWITCH, ParserImplConstants.ASSERT, ParserImplConstants.SUPER, ParserImplConstants.PUBLIC, ParserImplConstants.TRY, ParserImplConstants.IF, ParserImplConstants.WHILE, ParserImplConstants.TRANSIENT, ParserImplConstants.ABSTRACT, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.DO, ParserImplConstants.STATIC, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.DECR, ParserImplConstants.SHORT, ParserImplConstants.THROW, ParserImplConstants.LT, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.NEW, ParserImplConstants.PROTECTED, ParserImplConstants.CONTINUE, ParserImplConstants.DOUBLE, ParserImplConstants.FALSE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.STRICTFP, ParserImplConstants.PRIVATE, ParserImplConstants.CHAR, ParserImplConstants.INCR, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.NATIVE)) {
				if (matchConstructorDecl1()) {
					if (!matchExplicitConstructorInvocation())
						return false;
				} else if (matchNext(ParserImplConstants.LONG_LITERAL, ParserImplConstants.BYTE, ParserImplConstants.TRUE, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.LPAREN, ParserImplConstants.VOID, ParserImplConstants.LONG, ParserImplConstants.FINAL, ParserImplConstants.THIS, ParserImplConstants.INT, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.RETURN, ParserImplConstants.CLASS, ParserImplConstants.LBRACE, ParserImplConstants.FLOAT, ParserImplConstants.VOLATILE, ParserImplConstants.SEMICOLON, ParserImplConstants.IDENTIFIER, ParserImplConstants.AT, ParserImplConstants.BREAK, ParserImplConstants.INTERFACE, ParserImplConstants.FOR, ParserImplConstants.BOOLEAN, ParserImplConstants.NULL, ParserImplConstants.SWITCH, ParserImplConstants.ASSERT, ParserImplConstants.SUPER, ParserImplConstants.PUBLIC, ParserImplConstants.TRY, ParserImplConstants.IF, ParserImplConstants.WHILE, ParserImplConstants.TRANSIENT, ParserImplConstants.ABSTRACT, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.DO, ParserImplConstants.STATIC, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.DECR, ParserImplConstants.SHORT, ParserImplConstants.THROW, ParserImplConstants.LT, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.NEW, ParserImplConstants.PROTECTED, ParserImplConstants.CONTINUE, ParserImplConstants.DOUBLE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.FALSE, ParserImplConstants.STRICTFP, ParserImplConstants.PRIVATE, ParserImplConstants.CHAR, ParserImplConstants.INCR, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.NATIVE)) {
					if (!matchBlockStatement())
						return false;
				}
				while (matchNext(ParserImplConstants.LONG_LITERAL, ParserImplConstants.BYTE, ParserImplConstants.TRUE, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.LPAREN, ParserImplConstants.VOID, ParserImplConstants.LONG, ParserImplConstants.FINAL, ParserImplConstants.THIS, ParserImplConstants.INT, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.RETURN, ParserImplConstants.CLASS, ParserImplConstants.LBRACE, ParserImplConstants.FLOAT, ParserImplConstants.VOLATILE, ParserImplConstants.SEMICOLON, ParserImplConstants.IDENTIFIER, ParserImplConstants.AT, ParserImplConstants.BREAK, ParserImplConstants.INTERFACE, ParserImplConstants.FOR, ParserImplConstants.BOOLEAN, ParserImplConstants.NULL, ParserImplConstants.SWITCH, ParserImplConstants.ASSERT, ParserImplConstants.SUPER, ParserImplConstants.PUBLIC, ParserImplConstants.TRY, ParserImplConstants.IF, ParserImplConstants.WHILE, ParserImplConstants.TRANSIENT, ParserImplConstants.ABSTRACT, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.DO, ParserImplConstants.STATIC, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.DECR, ParserImplConstants.SHORT, ParserImplConstants.THROW, ParserImplConstants.LT, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.NEW, ParserImplConstants.PROTECTED, ParserImplConstants.CONTINUE, ParserImplConstants.DOUBLE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.FALSE, ParserImplConstants.STRICTFP, ParserImplConstants.PRIVATE, ParserImplConstants.CHAR, ParserImplConstants.INCR, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.NATIVE)) {
					if (!matchBlockStatement())
						return false;
				}
			} else if (matchNext(ParserImplConstants.NODE_LIST_VARIABLE)) {
				if (!matchNodeListVar())
					return false;
			}
		}
		if (!match(ParserImplConstants.RBRACE))
			return false;
		return true;
	}

	private boolean matchInitializerDecl() {
		if (!matchBlock())
			return false;
		return true;
	}

	private boolean matchClassOrInterfaceDecl() {
		if (matchNext(ParserImplConstants.CLASS)) {
			if (!match(ParserImplConstants.CLASS))
				return false;
			if (!matchName())
				return false;
			if (matchNext(ParserImplConstants.LT)) {
				if (!matchTypeParameters())
					return false;
			}
			if (matchNext(ParserImplConstants.EXTENDS)) {
				if (!match(ParserImplConstants.EXTENDS))
					return false;
				if (!matchAnnotatedQualifiedType())
					return false;
			}
			if (matchNext(ParserImplConstants.IMPLEMENTS)) {
				if (!matchImplementsList())
					return false;
			}
		} else if (matchNext(ParserImplConstants.INTERFACE)) {
			if (!match(ParserImplConstants.INTERFACE))
				return false;
			if (!matchName())
				return false;
			if (matchNext(ParserImplConstants.LT)) {
				if (!matchTypeParameters())
					return false;
			}
			if (matchNext(ParserImplConstants.EXTENDS)) {
				if (!matchExtendsList())
					return false;
			}
		}
		if (!matchClassOrInterfaceBody())
			return false;
		return true;
	}

	private boolean matchStatement() {
		if (matchNext(ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER)) {
			if (!matchLabeledStatement())
				return false;
		} else if (matchNext(ParserImplConstants.ASSERT)) {
			if (!matchAssertStatement())
				return false;
		} else if (matchNext(ParserImplConstants.LBRACE)) {
			if (!matchBlock())
				return false;
		} else if (matchNext(ParserImplConstants.SEMICOLON)) {
			if (!matchEmptyStatement())
				return false;
		} else if (matchNext(ParserImplConstants.LONG_LITERAL, ParserImplConstants.BYTE, ParserImplConstants.TRUE, ParserImplConstants.SUPER, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.LPAREN, ParserImplConstants.VOID, ParserImplConstants.LONG, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.THIS, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.INT, ParserImplConstants.DECR, ParserImplConstants.SHORT, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.FLOAT, ParserImplConstants.NEW, ParserImplConstants.IDENTIFIER, ParserImplConstants.DOUBLE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.FALSE, ParserImplConstants.CHAR, ParserImplConstants.INCR, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.BOOLEAN, ParserImplConstants.NULL)) {
			if (!matchStatementExpression())
				return false;
		} else if (matchNext(ParserImplConstants.SWITCH)) {
			if (!matchSwitchStatement())
				return false;
		} else if (matchNext(ParserImplConstants.IF)) {
			if (!matchIfStatement())
				return false;
		} else if (matchNext(ParserImplConstants.WHILE)) {
			if (!matchWhileStatement())
				return false;
		} else if (matchNext(ParserImplConstants.DO)) {
			if (!matchDoStatement())
				return false;
		} else if (matchNext(ParserImplConstants.FOR)) {
			if (!matchForStatement())
				return false;
		} else if (matchNext(ParserImplConstants.BREAK)) {
			if (!matchBreakStatement())
				return false;
		} else if (matchNext(ParserImplConstants.CONTINUE)) {
			if (!matchContinueStatement())
				return false;
		} else if (matchNext(ParserImplConstants.RETURN)) {
			if (!matchReturnStatement())
				return false;
		} else if (matchNext(ParserImplConstants.THROW)) {
			if (!matchThrowStatement())
				return false;
		} else if (matchNext(ParserImplConstants.SYNCHRONIZED)) {
			if (!matchSynchronizedStatement())
				return false;
		} else if (matchNext(ParserImplConstants.TRY)) {
			if (!matchTryStatement())
				return false;
		}
		return true;
	}

	private boolean matchExclusiveOrExpression() {
		if (!matchAndExpression())
			return false;
		while (matchNext(ParserImplConstants.XOR)) {
			if (!match(ParserImplConstants.XOR))
				return false;
			if (!matchAndExpression())
				return false;
		}
		return true;
	}

	private boolean matchMethodDecl() {
		if (matchNext(ParserImplConstants.LT)) {
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
		if (matchNext(ParserImplConstants.THROWS)) {
			if (!matchThrowsClause())
				return false;
		}
		if (matchNext(ParserImplConstants.LBRACE)) {
			if (!matchBlock())
				return false;
		} else if (matchNext(ParserImplConstants.SEMICOLON)) {
			if (!match(ParserImplConstants.SEMICOLON))
				return false;
		}
		return true;
	}

	private boolean matchEnumDecl() {
		if (!match(ParserImplConstants.ENUM))
			return false;
		if (!matchName())
			return false;
		if (matchNext(ParserImplConstants.IMPLEMENTS)) {
			if (!matchImplementsList())
				return false;
		}
		if (!match(ParserImplConstants.LBRACE))
			return false;
		if (matchNext(ParserImplConstants.NODE_VARIABLE, ParserImplConstants.VOLATILE, ParserImplConstants.PROTECTED, ParserImplConstants.PUBLIC, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_LIST_VARIABLE, ParserImplConstants.STRICTFP, ParserImplConstants.AT, ParserImplConstants.ABSTRACT, ParserImplConstants.PRIVATE, ParserImplConstants.TRANSIENT, ParserImplConstants._DEFAULT, ParserImplConstants.FINAL, ParserImplConstants.STATIC, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.NATIVE)) {
			if (matchNext(ParserImplConstants.NODE_VARIABLE, ParserImplConstants.VOLATILE, ParserImplConstants.PROTECTED, ParserImplConstants.PUBLIC, ParserImplConstants.IDENTIFIER, ParserImplConstants.STRICTFP, ParserImplConstants.AT, ParserImplConstants.ABSTRACT, ParserImplConstants.PRIVATE, ParserImplConstants.TRANSIENT, ParserImplConstants._DEFAULT, ParserImplConstants.FINAL, ParserImplConstants.STATIC, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.NATIVE)) {
				if (!matchEnumConstantDecl())
					return false;
				while (matchNext(ParserImplConstants.COMMA)) {
					if (!match(ParserImplConstants.COMMA))
						return false;
					if (!matchEnumConstantDecl())
						return false;
				}
			} else if (matchNext(ParserImplConstants.NODE_LIST_VARIABLE)) {
				if (!matchNodeListVar())
					return false;
			}
		}
		if (matchNext(ParserImplConstants.COMMA)) {
			if (!match(ParserImplConstants.COMMA))
				return false;
		}
		if (matchNext(ParserImplConstants.SEMICOLON)) {
			if (!match(ParserImplConstants.SEMICOLON))
				return false;
			if (!matchClassOrInterfaceBodyDecls())
				return false;
		}
		if (!match(ParserImplConstants.RBRACE))
			return false;
		return true;
	}

	private boolean matchAnnotationTypeDecl() {
		if (!match(ParserImplConstants.AT))
			return false;
		if (!match(ParserImplConstants.INTERFACE))
			return false;
		if (!matchName())
			return false;
		if (!matchAnnotationTypeBody())
			return false;
		return true;
	}

	private boolean matchAnnotationTypeBody() {
		if (!match(ParserImplConstants.LBRACE))
			return false;
		if (matchNext(ParserImplConstants.ENUM, ParserImplConstants.BYTE, ParserImplConstants.LONG, ParserImplConstants.PUBLIC, ParserImplConstants.ABSTRACT, ParserImplConstants.TRANSIENT, ParserImplConstants._DEFAULT, ParserImplConstants.FINAL, ParserImplConstants.STATIC, ParserImplConstants.INT, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.SHORT, ParserImplConstants.CLASS, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.FLOAT, ParserImplConstants.VOLATILE, ParserImplConstants.SEMICOLON, ParserImplConstants.PROTECTED, ParserImplConstants.IDENTIFIER, ParserImplConstants.DOUBLE, ParserImplConstants.STRICTFP, ParserImplConstants.NODE_LIST_VARIABLE, ParserImplConstants.AT, ParserImplConstants.PRIVATE, ParserImplConstants.CHAR, ParserImplConstants.INTERFACE, ParserImplConstants.BOOLEAN, ParserImplConstants.NATIVE)) {
			if (matchNext(ParserImplConstants.ENUM, ParserImplConstants.BYTE, ParserImplConstants.LONG, ParserImplConstants.PUBLIC, ParserImplConstants.ABSTRACT, ParserImplConstants.TRANSIENT, ParserImplConstants._DEFAULT, ParserImplConstants.FINAL, ParserImplConstants.STATIC, ParserImplConstants.INT, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.SHORT, ParserImplConstants.CLASS, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.FLOAT, ParserImplConstants.VOLATILE, ParserImplConstants.SEMICOLON, ParserImplConstants.PROTECTED, ParserImplConstants.DOUBLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.STRICTFP, ParserImplConstants.AT, ParserImplConstants.PRIVATE, ParserImplConstants.CHAR, ParserImplConstants.INTERFACE, ParserImplConstants.BOOLEAN, ParserImplConstants.NATIVE)) {
				do {
					if (!matchAnnotationTypeBodyDecl())
						return false;
				} while (matchNext(ParserImplConstants.ENUM, ParserImplConstants.BYTE, ParserImplConstants.LONG, ParserImplConstants.PUBLIC, ParserImplConstants.ABSTRACT, ParserImplConstants.TRANSIENT, ParserImplConstants._DEFAULT, ParserImplConstants.FINAL, ParserImplConstants.STATIC, ParserImplConstants.INT, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.SHORT, ParserImplConstants.CLASS, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.FLOAT, ParserImplConstants.VOLATILE, ParserImplConstants.SEMICOLON, ParserImplConstants.PROTECTED, ParserImplConstants.DOUBLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.STRICTFP, ParserImplConstants.AT, ParserImplConstants.PRIVATE, ParserImplConstants.CHAR, ParserImplConstants.INTERFACE, ParserImplConstants.BOOLEAN, ParserImplConstants.NATIVE));
			} else if (matchNext(ParserImplConstants.NODE_LIST_VARIABLE)) {
				if (!matchNodeListVar())
					return false;
			}
		}
		if (!match(ParserImplConstants.RBRACE))
			return false;
		return true;
	}

	private boolean matchExtendsList() {
		if (!match(ParserImplConstants.EXTENDS))
			return false;
		if (matchNext(ParserImplConstants.AT, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER)) {
			if (!matchAnnotatedQualifiedType())
				return false;
			while (matchNext(ParserImplConstants.COMMA)) {
				if (!match(ParserImplConstants.COMMA))
					return false;
				if (!matchAnnotatedQualifiedType())
					return false;
			}
		} else if (matchNext(ParserImplConstants.NODE_LIST_VARIABLE)) {
			if (!matchNodeListVar())
				return false;
		}
		return true;
	}

	private boolean matchDoStatement() {
		if (!match(ParserImplConstants.DO))
			return false;
		if (!matchStatement())
			return false;
		if (!match(ParserImplConstants.WHILE))
			return false;
		if (!match(ParserImplConstants.LPAREN))
			return false;
		if (!matchExpression())
			return false;
		if (!match(ParserImplConstants.RPAREN))
			return false;
		if (!match(ParserImplConstants.SEMICOLON))
			return false;
		return true;
	}

	private boolean matchStatementExpression() {
		if (matchNext(ParserImplConstants.INCR, ParserImplConstants.DECR)) {
			if (!matchPrefixExpression())
				return false;
		} else if (matchNext(ParserImplConstants.LONG_LITERAL, ParserImplConstants.BYTE, ParserImplConstants.TRUE, ParserImplConstants.SUPER, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.LPAREN, ParserImplConstants.VOID, ParserImplConstants.LONG, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.THIS, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.FLOAT, ParserImplConstants.NEW, ParserImplConstants.DOUBLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.STRING_LITERAL, ParserImplConstants.FALSE, ParserImplConstants.CHAR, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.BOOLEAN, ParserImplConstants.NULL)) {
			if (!matchPrimaryExpression())
				return false;
			if (matchNext(ParserImplConstants.XORASSIGN, ParserImplConstants.RUNSIGNEDSHIFTASSIGN, ParserImplConstants.MINUSASSIGN, ParserImplConstants.PLUSASSIGN, ParserImplConstants.ORASSIGN, ParserImplConstants.STARASSIGN, ParserImplConstants.INCR, ParserImplConstants.LSHIFTASSIGN, ParserImplConstants.SLASHASSIGN, ParserImplConstants.RSIGNEDSHIFTASSIGN, ParserImplConstants.ASSIGN, ParserImplConstants.REMASSIGN, ParserImplConstants.DECR, ParserImplConstants.ANDASSIGN)) {
				if (matchNext(ParserImplConstants.INCR)) {
					if (!match(ParserImplConstants.INCR))
						return false;
				} else if (matchNext(ParserImplConstants.DECR)) {
					if (!match(ParserImplConstants.DECR))
						return false;
				} else if (matchNext(ParserImplConstants.XORASSIGN, ParserImplConstants.ORASSIGN, ParserImplConstants.RUNSIGNEDSHIFTASSIGN, ParserImplConstants.STARASSIGN, ParserImplConstants.SLASHASSIGN, ParserImplConstants.LSHIFTASSIGN, ParserImplConstants.RSIGNEDSHIFTASSIGN, ParserImplConstants.ASSIGN, ParserImplConstants.REMASSIGN, ParserImplConstants.MINUSASSIGN, ParserImplConstants.PLUSASSIGN, ParserImplConstants.ANDASSIGN)) {
					if (!matchAssignmentOperator())
						return false;
					if (!matchExpression())
						return false;
				}
			}
		}
		if (!match(ParserImplConstants.SEMICOLON))
			return false;
		return true;
	}

	private boolean matchEmptyStatement() {
		if (!match(ParserImplConstants.SEMICOLON))
			return false;
		return true;
	}

	private boolean matchImplementsList() {
		if (!match(ParserImplConstants.IMPLEMENTS))
			return false;
		if (matchNext(ParserImplConstants.AT, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER)) {
			if (!matchAnnotatedQualifiedType())
				return false;
			while (matchNext(ParserImplConstants.COMMA)) {
				if (!match(ParserImplConstants.COMMA))
					return false;
				if (!matchAnnotatedQualifiedType())
					return false;
			}
		} else if (matchNext(ParserImplConstants.NODE_LIST_VARIABLE)) {
			if (!matchNodeListVar())
				return false;
		}
		return true;
	}

	private boolean matchWhileStatement() {
		if (!match(ParserImplConstants.WHILE))
			return false;
		if (!match(ParserImplConstants.LPAREN))
			return false;
		if (!matchExpression())
			return false;
		if (!match(ParserImplConstants.RPAREN))
			return false;
		if (!matchStatement())
			return false;
		return true;
	}

	private boolean matchSynchronizedStatement() {
		if (!match(ParserImplConstants.SYNCHRONIZED))
			return false;
		if (!match(ParserImplConstants.LPAREN))
			return false;
		if (!matchExpression())
			return false;
		if (!match(ParserImplConstants.RPAREN))
			return false;
		if (!matchBlock())
			return false;
		return true;
	}

	private boolean matchBreakStatement() {
		if (!match(ParserImplConstants.BREAK))
			return false;
		if (matchNext(ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER)) {
			if (!matchName())
				return false;
		}
		if (!match(ParserImplConstants.SEMICOLON))
			return false;
		return true;
	}

	private boolean matchIfStatement() {
		if (!match(ParserImplConstants.IF))
			return false;
		if (!match(ParserImplConstants.LPAREN))
			return false;
		if (!matchExpression())
			return false;
		if (!match(ParserImplConstants.RPAREN))
			return false;
		if (!matchStatement())
			return false;
		if (matchNext(ParserImplConstants.ELSE)) {
			if (!match(ParserImplConstants.ELSE))
				return false;
			if (!matchStatement())
				return false;
		}
		return true;
	}

	private boolean matchSwitchStatement() {
		if (!match(ParserImplConstants.SWITCH))
			return false;
		if (!match(ParserImplConstants.LPAREN))
			return false;
		if (!matchExpression())
			return false;
		if (!match(ParserImplConstants.RPAREN))
			return false;
		if (!match(ParserImplConstants.LBRACE))
			return false;
		while (matchNext(ParserImplConstants._DEFAULT, ParserImplConstants.CASE)) {
			if (!matchSwitchEntry())
				return false;
		}
		if (!match(ParserImplConstants.RBRACE))
			return false;
		return true;
	}

	private boolean matchAndExpression() {
		if (!matchEqualityExpression())
			return false;
		while (matchNext(ParserImplConstants.BIT_AND)) {
			if (!match(ParserImplConstants.BIT_AND))
				return false;
			if (!matchEqualityExpression())
				return false;
		}
		return true;
	}

	private boolean matchLabeledStatement() {
		if (!matchName())
			return false;
		if (!match(ParserImplConstants.COLON))
			return false;
		if (!matchStatement())
			return false;
		return true;
	}

	private boolean matchTryStatement() {
		if (!match(ParserImplConstants.TRY))
			return false;
		if (matchNext(ParserImplConstants.LPAREN)) {
			if (!matchResourceSpecification())
				return false;
			if (!matchBlock())
				return false;
			if (matchNext(ParserImplConstants.CATCH)) {
				if (!matchCatchClauses())
					return false;
			}
			if (matchNext(ParserImplConstants.FINALLY)) {
				if (!match(ParserImplConstants.FINALLY))
					return false;
				if (!matchBlock())
					return false;
			}
		} else if (matchNext(ParserImplConstants.LBRACE)) {
			if (!matchBlock())
				return false;
			if (matchNext(ParserImplConstants.CATCH)) {
				if (!matchCatchClauses())
					return false;
				if (matchNext(ParserImplConstants.FINALLY)) {
					if (!match(ParserImplConstants.FINALLY))
						return false;
					if (!matchBlock())
						return false;
				}
			} else if (matchNext(ParserImplConstants.FINALLY)) {
				if (!match(ParserImplConstants.FINALLY))
					return false;
				if (!matchBlock())
					return false;
			}
		}
		return true;
	}

	private boolean matchReturnStatement() {
		if (!match(ParserImplConstants.RETURN))
			return false;
		if (matchNext(ParserImplConstants.LONG_LITERAL, ParserImplConstants.BYTE, ParserImplConstants.TRUE, ParserImplConstants.SUPER, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.LPAREN, ParserImplConstants.VOID, ParserImplConstants.LONG, ParserImplConstants.PLUS, ParserImplConstants.BANG, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.THIS, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.DECR, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.FLOAT, ParserImplConstants.NEW, ParserImplConstants.DOUBLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.STRING_LITERAL, ParserImplConstants.FALSE, ParserImplConstants.TILDE, ParserImplConstants.CHAR, ParserImplConstants.INCR, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.MINUS, ParserImplConstants.BOOLEAN, ParserImplConstants.NULL)) {
			if (!matchExpression())
				return false;
		}
		if (!match(ParserImplConstants.SEMICOLON))
			return false;
		return true;
	}

	private boolean matchAssertStatement() {
		if (!match(ParserImplConstants.ASSERT))
			return false;
		if (!matchExpression())
			return false;
		if (matchNext(ParserImplConstants.COLON)) {
			if (!match(ParserImplConstants.COLON))
				return false;
			if (!matchExpression())
				return false;
		}
		if (!match(ParserImplConstants.SEMICOLON))
			return false;
		return true;
	}

	private boolean matchThrowStatement() {
		if (!match(ParserImplConstants.THROW))
			return false;
		if (!matchExpression())
			return false;
		if (!match(ParserImplConstants.SEMICOLON))
			return false;
		return true;
	}

	private boolean matchThrowsClause() {
		if (!match(ParserImplConstants.THROWS))
			return false;
		if (!matchAnnotatedQualifiedType())
			return false;
		while (matchNext(ParserImplConstants.COMMA)) {
			if (!match(ParserImplConstants.COMMA))
				return false;
			if (!matchAnnotatedQualifiedType())
				return false;
		}
		return true;
	}

	private boolean matchForStatement() {
		if (!match(ParserImplConstants.FOR))
			return false;
		if (!match(ParserImplConstants.LPAREN))
			return false;
		if (matchForStatement1()) {
			if (!matchVariableDeclExpression())
				return false;
			if (!match(ParserImplConstants.COLON))
				return false;
			if (!matchExpression())
				return false;
		} else if (matchNext(ParserImplConstants.LONG_LITERAL, ParserImplConstants.BYTE, ParserImplConstants.TRUE, ParserImplConstants.SUPER, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.LPAREN, ParserImplConstants.VOID, ParserImplConstants.LONG, ParserImplConstants.PUBLIC, ParserImplConstants.PLUS, ParserImplConstants.ABSTRACT, ParserImplConstants.TRANSIENT, ParserImplConstants.BANG, ParserImplConstants.FINAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.THIS, ParserImplConstants.STATIC, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.INT, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.SHORT, ParserImplConstants.DECR, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.FLOAT, ParserImplConstants.VOLATILE, ParserImplConstants.NEW, ParserImplConstants.SEMICOLON, ParserImplConstants.PROTECTED, ParserImplConstants.IDENTIFIER, ParserImplConstants.DOUBLE, ParserImplConstants.FALSE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.STRICTFP, ParserImplConstants.AT, ParserImplConstants.PRIVATE, ParserImplConstants.TILDE, ParserImplConstants.CHAR, ParserImplConstants.INCR, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.MINUS, ParserImplConstants.BOOLEAN, ParserImplConstants.NATIVE, ParserImplConstants.NULL)) {
			if (matchNext(ParserImplConstants.LONG_LITERAL, ParserImplConstants.BYTE, ParserImplConstants.TRUE, ParserImplConstants.SUPER, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.LPAREN, ParserImplConstants.VOID, ParserImplConstants.LONG, ParserImplConstants.PUBLIC, ParserImplConstants.PLUS, ParserImplConstants.ABSTRACT, ParserImplConstants.TRANSIENT, ParserImplConstants.BANG, ParserImplConstants.FINAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.THIS, ParserImplConstants.STATIC, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.INT, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.SHORT, ParserImplConstants.DECR, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.FLOAT, ParserImplConstants.VOLATILE, ParserImplConstants.NEW, ParserImplConstants.PROTECTED, ParserImplConstants.IDENTIFIER, ParserImplConstants.DOUBLE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.FALSE, ParserImplConstants.STRICTFP, ParserImplConstants.AT, ParserImplConstants.PRIVATE, ParserImplConstants.TILDE, ParserImplConstants.CHAR, ParserImplConstants.INCR, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.MINUS, ParserImplConstants.BOOLEAN, ParserImplConstants.NATIVE, ParserImplConstants.NULL)) {
				if (!matchForInit())
					return false;
			}
			if (!match(ParserImplConstants.SEMICOLON))
				return false;
			if (matchNext(ParserImplConstants.LONG_LITERAL, ParserImplConstants.BYTE, ParserImplConstants.TRUE, ParserImplConstants.SUPER, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.LPAREN, ParserImplConstants.VOID, ParserImplConstants.LONG, ParserImplConstants.PLUS, ParserImplConstants.BANG, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.THIS, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.DECR, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.FLOAT, ParserImplConstants.NEW, ParserImplConstants.DOUBLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.STRING_LITERAL, ParserImplConstants.FALSE, ParserImplConstants.TILDE, ParserImplConstants.CHAR, ParserImplConstants.INCR, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.MINUS, ParserImplConstants.BOOLEAN, ParserImplConstants.NULL)) {
				if (!matchExpression())
					return false;
			}
			if (!match(ParserImplConstants.SEMICOLON))
				return false;
			if (matchNext(ParserImplConstants.LONG_LITERAL, ParserImplConstants.BYTE, ParserImplConstants.TRUE, ParserImplConstants.SUPER, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.LPAREN, ParserImplConstants.VOID, ParserImplConstants.LONG, ParserImplConstants.PLUS, ParserImplConstants.BANG, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.THIS, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.INT, ParserImplConstants.DECR, ParserImplConstants.SHORT, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.FLOAT, ParserImplConstants.NEW, ParserImplConstants.IDENTIFIER, ParserImplConstants.DOUBLE, ParserImplConstants.FALSE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.TILDE, ParserImplConstants.CHAR, ParserImplConstants.INCR, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.MINUS, ParserImplConstants.BOOLEAN, ParserImplConstants.NULL)) {
				if (!matchForUpdate())
					return false;
			}
		}
		if (!match(ParserImplConstants.RPAREN))
			return false;
		if (!matchStatement())
			return false;
		return true;
	}

	private boolean matchFormalParameters() {
		if (!match(ParserImplConstants.LPAREN))
			return false;
		if (matchNext(ParserImplConstants.NODE_VARIABLE, ParserImplConstants.BYTE, ParserImplConstants.FLOAT, ParserImplConstants.VOLATILE, ParserImplConstants.PROTECTED, ParserImplConstants.PUBLIC, ParserImplConstants.LONG, ParserImplConstants.IDENTIFIER, ParserImplConstants.DOUBLE, ParserImplConstants.NODE_LIST_VARIABLE, ParserImplConstants.STRICTFP, ParserImplConstants.AT, ParserImplConstants.ABSTRACT, ParserImplConstants.PRIVATE, ParserImplConstants.TRANSIENT, ParserImplConstants._DEFAULT, ParserImplConstants.FINAL, ParserImplConstants.CHAR, ParserImplConstants.STATIC, ParserImplConstants.INT, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.SHORT, ParserImplConstants.BOOLEAN, ParserImplConstants.NATIVE)) {
			if (!matchFormalParameterList())
				return false;
		}
		if (!match(ParserImplConstants.RPAREN))
			return false;
		return true;
	}

	private boolean matchContinueStatement() {
		if (!match(ParserImplConstants.CONTINUE))
			return false;
		if (matchNext(ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER)) {
			if (!matchName())
				return false;
		}
		if (!match(ParserImplConstants.SEMICOLON))
			return false;
		return true;
	}

	private boolean matchEnumConstantDecl() {
		if (!matchModifiers())
			return false;
		if (!matchName())
			return false;
		if (matchNext(ParserImplConstants.LPAREN)) {
			if (!matchArguments())
				return false;
		}
		if (matchNext(ParserImplConstants.LBRACE)) {
			if (!matchClassOrInterfaceBody())
				return false;
		}
		return true;
	}

	private boolean matchCatchClauses() {
		do {
			if (!matchCatchClause())
				return false;
		} while (matchNext(ParserImplConstants.CATCH));
		return true;
	}

	private boolean matchAnnotationTypeBodyDecl() {
		if (matchNext(ParserImplConstants.SEMICOLON)) {
			if (!match(ParserImplConstants.SEMICOLON))
				return false;
		} else if (matchNext(ParserImplConstants.ENUM, ParserImplConstants.BYTE, ParserImplConstants.LONG, ParserImplConstants.PUBLIC, ParserImplConstants.ABSTRACT, ParserImplConstants.TRANSIENT, ParserImplConstants._DEFAULT, ParserImplConstants.FINAL, ParserImplConstants.STATIC, ParserImplConstants.INT, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.SHORT, ParserImplConstants.CLASS, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.FLOAT, ParserImplConstants.VOLATILE, ParserImplConstants.PROTECTED, ParserImplConstants.DOUBLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.STRICTFP, ParserImplConstants.AT, ParserImplConstants.PRIVATE, ParserImplConstants.CHAR, ParserImplConstants.INTERFACE, ParserImplConstants.BOOLEAN, ParserImplConstants.NATIVE)) {
			if (!matchModifiers())
				return false;
			if (matchAnnotationTypeBodyDecl1()) {
				if (!matchAnnotationTypeMemberDecl())
					return false;
			} else if (matchNext(ParserImplConstants.CLASS, ParserImplConstants.INTERFACE)) {
				if (!matchClassOrInterfaceDecl())
					return false;
			} else if (matchNext(ParserImplConstants.ENUM)) {
				if (!matchEnumDecl())
					return false;
			} else if (matchNext(ParserImplConstants.AT)) {
				if (!matchAnnotationTypeDecl())
					return false;
			} else if (matchNext(ParserImplConstants.NODE_VARIABLE, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.FLOAT, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.LONG, ParserImplConstants.BOOLEAN, ParserImplConstants.DOUBLE, ParserImplConstants.IDENTIFIER)) {
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
		if (matchNext(ParserImplConstants.CASE)) {
			if (!match(ParserImplConstants.CASE))
				return false;
			if (!matchExpression())
				return false;
		} else if (matchNext(ParserImplConstants._DEFAULT)) {
			if (!match(ParserImplConstants._DEFAULT))
				return false;
		}
		if (!match(ParserImplConstants.COLON))
			return false;
		if (!matchStatements())
			return false;
		return true;
	}

	private boolean matchResourceSpecification() {
		if (!match(ParserImplConstants.LPAREN))
			return false;
		if (!matchVariableDeclExpression())
			return false;
		while (matchNext(ParserImplConstants.SEMICOLON)) {
			if (!match(ParserImplConstants.SEMICOLON))
				return false;
			if (!matchVariableDeclExpression())
				return false;
		}
		if (matchNext(ParserImplConstants.SEMICOLON)) {
			if (!match(ParserImplConstants.SEMICOLON))
				return false;
		}
		if (!match(ParserImplConstants.RPAREN))
			return false;
		return true;
	}

	private boolean matchEqualityExpression() {
		if (!matchInstanceOfExpression())
			return false;
		while (matchNext(ParserImplConstants.NE, ParserImplConstants.EQ)) {
			if (matchNext(ParserImplConstants.EQ)) {
				if (!match(ParserImplConstants.EQ))
					return false;
			} else if (matchNext(ParserImplConstants.NE)) {
				if (!match(ParserImplConstants.NE))
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
		} else if (matchNext(ParserImplConstants.LONG_LITERAL, ParserImplConstants.BYTE, ParserImplConstants.TRUE, ParserImplConstants.SUPER, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.LPAREN, ParserImplConstants.VOID, ParserImplConstants.LONG, ParserImplConstants.PLUS, ParserImplConstants.BANG, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.THIS, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.DECR, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.FLOAT, ParserImplConstants.NEW, ParserImplConstants.DOUBLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.STRING_LITERAL, ParserImplConstants.FALSE, ParserImplConstants.TILDE, ParserImplConstants.CHAR, ParserImplConstants.INCR, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.MINUS, ParserImplConstants.BOOLEAN, ParserImplConstants.NULL)) {
			if (!matchExpressionList())
				return false;
		}
		return true;
	}

	private boolean matchCatchClause() {
		if (!match(ParserImplConstants.CATCH))
			return false;
		if (!match(ParserImplConstants.LPAREN))
			return false;
		if (!matchCatchFormalParameter())
			return false;
		if (!match(ParserImplConstants.RPAREN))
			return false;
		if (!matchBlock())
			return false;
		return true;
	}

	private boolean matchInstanceOfExpression() {
		if (!matchRelationalExpression())
			return false;
		if (matchNext(ParserImplConstants.INSTANCEOF)) {
			if (!match(ParserImplConstants.INSTANCEOF))
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
		if (!match(ParserImplConstants.LPAREN))
			return false;
		if (!match(ParserImplConstants.RPAREN))
			return false;
		if (!matchArrayDims())
			return false;
		if (matchNext(ParserImplConstants._DEFAULT)) {
			if (!match(ParserImplConstants._DEFAULT))
				return false;
			if (!matchMemberValue())
				return false;
		}
		if (!match(ParserImplConstants.SEMICOLON))
			return false;
		return true;
	}

	private boolean matchExpressionList() {
		if (!matchExpression())
			return false;
		while (matchNext(ParserImplConstants.COMMA)) {
			if (!match(ParserImplConstants.COMMA))
				return false;
			if (!matchExpression())
				return false;
		}
		return true;
	}

	private boolean matchRelationalExpression() {
		if (!matchShiftExpression())
			return false;
		while (matchNext(ParserImplConstants.LT, ParserImplConstants.LE, ParserImplConstants.GE, ParserImplConstants.GT)) {
			if (matchNext(ParserImplConstants.LT)) {
				if (!match(ParserImplConstants.LT))
					return false;
			} else if (matchNext(ParserImplConstants.GT)) {
				if (!match(ParserImplConstants.GT))
					return false;
			} else if (matchNext(ParserImplConstants.LE)) {
				if (!match(ParserImplConstants.LE))
					return false;
			} else if (matchNext(ParserImplConstants.GE)) {
				if (!match(ParserImplConstants.GE))
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
				if (!match(ParserImplConstants.BIT_OR))
					return false;
				if (!matchAnnotatedQualifiedType())
					return false;
			} while (matchNext(ParserImplConstants.BIT_OR));
		}
		if (!matchVariableDeclaratorId())
			return false;
		return true;
	}

	private boolean matchShiftExpression() {
		if (!matchAdditiveExpression())
			return false;
		while (matchNext(ParserImplConstants.LSHIFT, ParserImplConstants.GT)) {
			if (matchNext(ParserImplConstants.LSHIFT)) {
				if (!match(ParserImplConstants.LSHIFT))
					return false;
			} else if (matchNext(ParserImplConstants.GT)) {
				if (!matchRSIGNEDSHIFT())
					return false;
			} else if (matchNext(ParserImplConstants.GT)) {
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
		while (matchNext(ParserImplConstants.PLUS, ParserImplConstants.MINUS)) {
			if (matchNext(ParserImplConstants.PLUS)) {
				if (!match(ParserImplConstants.PLUS))
					return false;
			} else if (matchNext(ParserImplConstants.MINUS)) {
				if (!match(ParserImplConstants.MINUS))
					return false;
			}
			if (!matchMultiplicativeExpression())
				return false;
		}
		return true;
	}

	private boolean matchRSIGNEDSHIFT() {
		if (!match(ParserImplConstants.GT))
			return false;
		if (!match(ParserImplConstants.GT))
			return false;
		return true;
	}

	private boolean matchRUNSIGNEDSHIFT() {
		if (!match(ParserImplConstants.GT))
			return false;
		if (!match(ParserImplConstants.GT))
			return false;
		if (!match(ParserImplConstants.GT))
			return false;
		return true;
	}

	private boolean matchMultiplicativeExpression() {
		if (!matchUnaryExpression())
			return false;
		while (matchNext(ParserImplConstants.SLASH, ParserImplConstants.STAR, ParserImplConstants.REM)) {
			if (matchNext(ParserImplConstants.STAR)) {
				if (!match(ParserImplConstants.STAR))
					return false;
			} else if (matchNext(ParserImplConstants.SLASH)) {
				if (!match(ParserImplConstants.SLASH))
					return false;
			} else if (matchNext(ParserImplConstants.REM)) {
				if (!match(ParserImplConstants.REM))
					return false;
			}
			if (!matchUnaryExpression())
				return false;
		}
		return true;
	}
}
