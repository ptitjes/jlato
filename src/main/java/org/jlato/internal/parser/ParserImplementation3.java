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

/**
 * Internal implementation of the Java parser as a recursive descent parser.
 */
public class ParserImplementation3 extends ParserNewBase {

	/* sequence(
		terminal(id, NODE_LIST_VARIABLE)
		action({ return makeVar(id); })
	) */
	protected BUTree<SNodeList> parseNodeListVar() throws ParseException {
		Token id;
		id = parse(TokenType.NODE_LIST_VARIABLE);
		return makeVar(id);
	}

	/* sequence(
		terminal(id, NODE_LIST_VARIABLE)
	) */
	private int matchNodeListVar(int lookahead) {
		lookahead = match(lookahead, TokenType.NODE_LIST_VARIABLE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(id, NODE_VARIABLE)
		action({ return makeVar(id); })
	) */
	protected BUTree<SName> parseNodeVar() throws ParseException {
		Token id;
		id = parse(TokenType.NODE_VARIABLE);
		return makeVar(id);
	}

	/* sequence(
		terminal(id, NODE_VARIABLE)
	) */
	private int matchNodeVar(int lookahead) {
		lookahead = match(lookahead, TokenType.NODE_VARIABLE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		action({ run(); })
		zeroOrOne(
			lookAhead(
				nonTerminal(PackageDecl)
			)
			nonTerminal(packageDecl, PackageDecl)
		)
		nonTerminal(imports, ImportDecls)
		nonTerminal(types, TypeDecls)
		action({ compilationUnit = dress(SCompilationUnit.make(packageDecl, imports, types)); })
		nonTerminal(Epilog)
		action({ return dressWithPrologAndEpilog(compilationUnit); })
	) */
	protected BUTree<SCompilationUnit> parseCompilationUnit() throws ParseException {
		BUTree<SPackageDecl> packageDecl = null;
		BUTree<SNodeList> imports;
		BUTree<SNodeList> types;
		BUTree<SCompilationUnit> compilationUnit;
		run();
		if (matchCompilationUnit_lookahead1(0) != -1) {
			packageDecl = parsePackageDecl();
		}
		imports = parseImportDecls();
		types = parseTypeDecls();
		compilationUnit = dress(SCompilationUnit.make(packageDecl, imports, types));
		parseEpilog();
		return dressWithPrologAndEpilog(compilationUnit);
	}

	/* sequence(
		nonTerminal(PackageDecl)
	) */
	private int matchCompilationUnit_lookahead1(int lookahead) {
		lookahead = matchPackageDecl(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		terminal(EOF)
		terminal(EOF)
	) */
	protected void parseEpilog() throws ParseException {
		if (match(0, TokenType.EOF) != -1) {
			parse(TokenType.EOF);
		} else if (match(0, TokenType.EOF) != -1) {
			parse(TokenType.EOF);
		} else {
			throw produceParseException(TokenType.EOF);
		}
	}

	/* sequence(
		action({ run(); })
		nonTerminal(annotations, Annotations)
		terminal(PACKAGE)
		nonTerminal(name, QualifiedName)
		terminal(SEMICOLON)
		action({ return dress(SPackageDecl.make(annotations, name)); })
	) */
	protected BUTree<SPackageDecl> parsePackageDecl() throws ParseException {
		BUTree<SNodeList> annotations = null;
		BUTree<SQualifiedName> name;
		run();
		annotations = parseAnnotations();
		parse(TokenType.PACKAGE);
		name = parseQualifiedName();
		parse(TokenType.SEMICOLON);
		return dress(SPackageDecl.make(annotations, name));
	}

	/* sequence(
		nonTerminal(annotations, Annotations)
		terminal(PACKAGE)
		nonTerminal(name, QualifiedName)
		terminal(SEMICOLON)
	) */
	private int matchPackageDecl(int lookahead) {
		lookahead = matchAnnotations(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.PACKAGE);
		if (lookahead == -1)
			return -1;
		lookahead = matchQualifiedName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.SEMICOLON);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		zeroOrMore(
			nonTerminal(importDecl, ImportDecl)
			action({ imports = append(imports, importDecl); })
		)
		action({ return imports; })
	) */
	protected BUTree<SNodeList> parseImportDecls() throws ParseException {
		BUTree<SNodeList> imports = emptyList();
		BUTree<SImportDecl> importDecl = null;
		while (match(0, TokenType.IMPORT) != -1) {
			importDecl = parseImportDecl();
			imports = append(imports, importDecl);
		}
		return imports;
	}

	/* sequence(
		action({ run(); })
		terminal(IMPORT)
		zeroOrOne(
			terminal(STATIC)
			action({ isStatic = true; })
		)
		nonTerminal(name, QualifiedName)
		zeroOrOne(
			terminal(DOT)
			terminal(STAR)
			action({ isAsterisk = true; })
		)
		terminal(SEMICOLON)
		action({ return dress(SImportDecl.make(name, isStatic, isAsterisk)); })
	) */
	protected BUTree<SImportDecl> parseImportDecl() throws ParseException {
		BUTree<SQualifiedName> name;
		boolean isStatic = false;
		boolean isAsterisk = false;
		run();
		parse(TokenType.IMPORT);
		if (match(0, TokenType.STATIC) != -1) {
			parse(TokenType.STATIC);
			isStatic = true;
		}
		name = parseQualifiedName();
		if (match(0, TokenType.DOT) != -1) {
			parse(TokenType.DOT);
			parse(TokenType.STAR);
			isAsterisk = true;
		}
		parse(TokenType.SEMICOLON);
		return dress(SImportDecl.make(name, isStatic, isAsterisk));
	}

	/* sequence(
		zeroOrMore(
			nonTerminal(typeDecl, TypeDecl)
			action({ types = append(types, typeDecl); })
		)
		action({ return types; })
	) */
	protected BUTree<SNodeList> parseTypeDecls() throws ParseException {
		BUTree<SNodeList> types = emptyList();
		BUTree<? extends STypeDecl> typeDecl = null;
		while (match(0, TokenType.PROTECTED, TokenType.PUBLIC, TokenType.ABSTRACT, TokenType.PRIVATE, TokenType.STATIC, TokenType.DEFAULT, TokenType.TRANSIENT, TokenType.FINAL, TokenType.SYNCHRONIZED, TokenType.VOLATILE, TokenType.STRICTFP, TokenType.NATIVE, TokenType.AT, TokenType.ENUM, TokenType.CLASS, TokenType.INTERFACE, TokenType.SEMICOLON) != -1) {
			typeDecl = parseTypeDecl();
			types = append(types, typeDecl);
		}
		return types;
	}

	/* sequence(
		zeroOrMore(
			lookAhead(2)
			choice(
				sequence(
					terminal(PUBLIC)
					action({ modifiers = append(modifiers, SModifier.make(ModifierKeyword.Public)); })
				)
				sequence(
					terminal(PROTECTED)
					action({ modifiers = append(modifiers, SModifier.make(ModifierKeyword.Protected)); })
				)
				sequence(
					terminal(PRIVATE)
					action({ modifiers = append(modifiers, SModifier.make(ModifierKeyword.Private)); })
				)
				sequence(
					terminal(ABSTRACT)
					action({ modifiers = append(modifiers, SModifier.make(ModifierKeyword.Abstract)); })
				)
				sequence(
					terminal(DEFAULT)
					action({ modifiers = append(modifiers, SModifier.make(ModifierKeyword.Default)); })
				)
				sequence(
					terminal(STATIC)
					action({ modifiers = append(modifiers, SModifier.make(ModifierKeyword.Static)); })
				)
				sequence(
					terminal(FINAL)
					action({ modifiers = append(modifiers, SModifier.make(ModifierKeyword.Final)); })
				)
				sequence(
					terminal(TRANSIENT)
					action({ modifiers = append(modifiers, SModifier.make(ModifierKeyword.Transient)); })
				)
				sequence(
					terminal(VOLATILE)
					action({ modifiers = append(modifiers, SModifier.make(ModifierKeyword.Volatile)); })
				)
				sequence(
					terminal(SYNCHRONIZED)
					action({ modifiers = append(modifiers, SModifier.make(ModifierKeyword.Synchronized)); })
				)
				sequence(
					terminal(NATIVE)
					action({ modifiers = append(modifiers, SModifier.make(ModifierKeyword.Native)); })
				)
				sequence(
					terminal(STRICTFP)
					action({ modifiers = append(modifiers, SModifier.make(ModifierKeyword.StrictFP)); })
				)
				sequence(
					nonTerminal(ann, Annotation)
					action({ modifiers = append(modifiers, ann); })
				)
			)
		)
		action({ return modifiers; })
	) */
	protected BUTree<SNodeList> parseModifiers() throws ParseException {
		BUTree<SNodeList> modifiers = emptyList();
		BUTree<? extends SAnnotationExpr> ann;
		while (matchModifiers_lookahead1(0) != -1) {
			if (match(0, TokenType.PUBLIC) != -1) {
				parse(TokenType.PUBLIC);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Public));
			} else if (match(0, TokenType.PROTECTED) != -1) {
				parse(TokenType.PROTECTED);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Protected));
			} else if (match(0, TokenType.PRIVATE) != -1) {
				parse(TokenType.PRIVATE);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Private));
			} else if (match(0, TokenType.ABSTRACT) != -1) {
				parse(TokenType.ABSTRACT);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Abstract));
			} else if (match(0, TokenType.DEFAULT) != -1) {
				parse(TokenType.DEFAULT);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Default));
			} else if (match(0, TokenType.STATIC) != -1) {
				parse(TokenType.STATIC);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Static));
			} else if (match(0, TokenType.FINAL) != -1) {
				parse(TokenType.FINAL);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Final));
			} else if (match(0, TokenType.TRANSIENT) != -1) {
				parse(TokenType.TRANSIENT);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Transient));
			} else if (match(0, TokenType.VOLATILE) != -1) {
				parse(TokenType.VOLATILE);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Volatile));
			} else if (match(0, TokenType.SYNCHRONIZED) != -1) {
				parse(TokenType.SYNCHRONIZED);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Synchronized));
			} else if (match(0, TokenType.NATIVE) != -1) {
				parse(TokenType.NATIVE);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Native));
			} else if (match(0, TokenType.STRICTFP) != -1) {
				parse(TokenType.STRICTFP);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.StrictFP));
			} else if (match(0, TokenType.AT) != -1) {
				ann = parseAnnotation();
				modifiers = append(modifiers, ann);
			} else {
				throw produceParseException(TokenType.AT, TokenType.STRICTFP, TokenType.NATIVE, TokenType.SYNCHRONIZED, TokenType.VOLATILE, TokenType.TRANSIENT, TokenType.FINAL, TokenType.STATIC, TokenType.DEFAULT, TokenType.ABSTRACT, TokenType.PRIVATE, TokenType.PROTECTED, TokenType.PUBLIC);
			}
		}
		return modifiers;
	}

	/* sequence(
		zeroOrMore(
			lookAhead(2)
			choice(
				sequence(
					terminal(PUBLIC)
				)
				sequence(
					terminal(PROTECTED)
				)
				sequence(
					terminal(PRIVATE)
				)
				sequence(
					terminal(ABSTRACT)
				)
				sequence(
					terminal(DEFAULT)
				)
				sequence(
					terminal(STATIC)
				)
				sequence(
					terminal(FINAL)
				)
				sequence(
					terminal(TRANSIENT)
				)
				sequence(
					terminal(VOLATILE)
				)
				sequence(
					terminal(SYNCHRONIZED)
				)
				sequence(
					terminal(NATIVE)
				)
				sequence(
					terminal(STRICTFP)
				)
				sequence(
					nonTerminal(ann, Annotation)
				)
			)
		)
	) */
	private int matchModifiers(int lookahead) {
		lookahead = matchModifiers_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		lookAhead(2)
		choice(
			sequence(
				terminal(PUBLIC)
			)
			sequence(
				terminal(PROTECTED)
			)
			sequence(
				terminal(PRIVATE)
			)
			sequence(
				terminal(ABSTRACT)
			)
			sequence(
				terminal(DEFAULT)
			)
			sequence(
				terminal(STATIC)
			)
			sequence(
				terminal(FINAL)
			)
			sequence(
				terminal(TRANSIENT)
			)
			sequence(
				terminal(VOLATILE)
			)
			sequence(
				terminal(SYNCHRONIZED)
			)
			sequence(
				terminal(NATIVE)
			)
			sequence(
				terminal(STRICTFP)
			)
			sequence(
				nonTerminal(ann, Annotation)
			)
		)
	) */
	private int matchModifiers_1(int lookahead) {
		int newLookahead;
		newLookahead = matchModifiers_1_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchModifiers_1_1(lookahead);
		}
		return lookahead;
	}

	/* sequence(
		lookAhead(2)
		choice(
			sequence(
				terminal(PUBLIC)
			)
			sequence(
				terminal(PROTECTED)
			)
			sequence(
				terminal(PRIVATE)
			)
			sequence(
				terminal(ABSTRACT)
			)
			sequence(
				terminal(DEFAULT)
			)
			sequence(
				terminal(STATIC)
			)
			sequence(
				terminal(FINAL)
			)
			sequence(
				terminal(TRANSIENT)
			)
			sequence(
				terminal(VOLATILE)
			)
			sequence(
				terminal(SYNCHRONIZED)
			)
			sequence(
				terminal(NATIVE)
			)
			sequence(
				terminal(STRICTFP)
			)
			sequence(
				nonTerminal(ann, Annotation)
			)
		)
	) */
	private int matchModifiers_1_1(int lookahead) {
		lookahead = matchModifiers_1_1_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			terminal(PUBLIC)
		)
		sequence(
			terminal(PROTECTED)
		)
		sequence(
			terminal(PRIVATE)
		)
		sequence(
			terminal(ABSTRACT)
		)
		sequence(
			terminal(DEFAULT)
		)
		sequence(
			terminal(STATIC)
		)
		sequence(
			terminal(FINAL)
		)
		sequence(
			terminal(TRANSIENT)
		)
		sequence(
			terminal(VOLATILE)
		)
		sequence(
			terminal(SYNCHRONIZED)
		)
		sequence(
			terminal(NATIVE)
		)
		sequence(
			terminal(STRICTFP)
		)
		sequence(
			nonTerminal(ann, Annotation)
		)
	) */
	private int matchModifiers_1_1_2(int lookahead) {
		int newLookahead;
		newLookahead = matchModifiers_1_1_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchModifiers_1_1_2_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchModifiers_1_1_2_3(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchModifiers_1_1_2_4(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchModifiers_1_1_2_5(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchModifiers_1_1_2_6(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchModifiers_1_1_2_7(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchModifiers_1_1_2_8(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchModifiers_1_1_2_9(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchModifiers_1_1_2_10(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchModifiers_1_1_2_11(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchModifiers_1_1_2_12(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchModifiers_1_1_2_13(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		terminal(PUBLIC)
	) */
	private int matchModifiers_1_1_2_1(int lookahead) {
		lookahead = match(lookahead, TokenType.PUBLIC);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(SYNCHRONIZED)
	) */
	private int matchModifiers_1_1_2_10(int lookahead) {
		lookahead = match(lookahead, TokenType.SYNCHRONIZED);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(NATIVE)
	) */
	private int matchModifiers_1_1_2_11(int lookahead) {
		lookahead = match(lookahead, TokenType.NATIVE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(STRICTFP)
	) */
	private int matchModifiers_1_1_2_12(int lookahead) {
		lookahead = match(lookahead, TokenType.STRICTFP);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(ann, Annotation)
	) */
	private int matchModifiers_1_1_2_13(int lookahead) {
		lookahead = matchAnnotation(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(PROTECTED)
	) */
	private int matchModifiers_1_1_2_2(int lookahead) {
		lookahead = match(lookahead, TokenType.PROTECTED);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(PRIVATE)
	) */
	private int matchModifiers_1_1_2_3(int lookahead) {
		lookahead = match(lookahead, TokenType.PRIVATE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(ABSTRACT)
	) */
	private int matchModifiers_1_1_2_4(int lookahead) {
		lookahead = match(lookahead, TokenType.ABSTRACT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(DEFAULT)
	) */
	private int matchModifiers_1_1_2_5(int lookahead) {
		lookahead = match(lookahead, TokenType.DEFAULT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(STATIC)
	) */
	private int matchModifiers_1_1_2_6(int lookahead) {
		lookahead = match(lookahead, TokenType.STATIC);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(FINAL)
	) */
	private int matchModifiers_1_1_2_7(int lookahead) {
		lookahead = match(lookahead, TokenType.FINAL);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(TRANSIENT)
	) */
	private int matchModifiers_1_1_2_8(int lookahead) {
		lookahead = match(lookahead, TokenType.TRANSIENT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(VOLATILE)
	) */
	private int matchModifiers_1_1_2_9(int lookahead) {
		lookahead = match(lookahead, TokenType.VOLATILE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		lookAhead(2)
		choice(
			sequence(
				terminal(PUBLIC)
			)
			sequence(
				terminal(PROTECTED)
			)
			sequence(
				terminal(PRIVATE)
			)
			sequence(
				terminal(ABSTRACT)
			)
			sequence(
				terminal(DEFAULT)
			)
			sequence(
				terminal(STATIC)
			)
			sequence(
				terminal(FINAL)
			)
			sequence(
				terminal(TRANSIENT)
			)
			sequence(
				terminal(VOLATILE)
			)
			sequence(
				terminal(SYNCHRONIZED)
			)
			sequence(
				terminal(NATIVE)
			)
			sequence(
				terminal(STRICTFP)
			)
			sequence(
				nonTerminal(ann, Annotation)
			)
		)
	) */
	private int matchModifiers_lookahead1(int lookahead) {
		if (match(0, TokenType.VOLATILE) != -1) {
			return lookahead;
		}
		if (match(0, TokenType.SYNCHRONIZED) != -1) {
			return lookahead;
		}
		if (match(0, TokenType.NATIVE) != -1) {
			return lookahead;
		}
		if (match(0, TokenType.AT) != -1) {
			if (match(1, TokenType.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, TokenType.PUBLIC) != -1) {
			return lookahead;
		}
		if (match(0, TokenType.FINAL) != -1) {
			return lookahead;
		}
		if (match(0, TokenType.PROTECTED) != -1) {
			return lookahead;
		}
		if (match(0, TokenType.ABSTRACT) != -1) {
			return lookahead;
		}
		if (match(0, TokenType.TRANSIENT) != -1) {
			return lookahead;
		}
		if (match(0, TokenType.STRICTFP) != -1) {
			return lookahead;
		}
		if (match(0, TokenType.DEFAULT) != -1) {
			return lookahead;
		}
		if (match(0, TokenType.STATIC) != -1) {
			return lookahead;
		}
		if (match(0, TokenType.PRIVATE) != -1) {
			return lookahead;
		}
		return -1;
	}

	/* sequence(
		zeroOrMore(
			lookAhead(2)
			choice(
				sequence(
					terminal(PUBLIC)
					action({ modifiers = append(modifiers, SModifier.make(ModifierKeyword.Public)); })
				)
				sequence(
					terminal(PROTECTED)
					action({ modifiers = append(modifiers, SModifier.make(ModifierKeyword.Protected)); })
				)
				sequence(
					terminal(PRIVATE)
					action({ modifiers = append(modifiers, SModifier.make(ModifierKeyword.Private)); })
				)
				sequence(
					terminal(ABSTRACT)
					action({ modifiers = append(modifiers, SModifier.make(ModifierKeyword.Abstract)); })
				)
				sequence(
					terminal(STATIC)
					action({ modifiers = append(modifiers, SModifier.make(ModifierKeyword.Static)); })
				)
				sequence(
					terminal(FINAL)
					action({ modifiers = append(modifiers, SModifier.make(ModifierKeyword.Final)); })
				)
				sequence(
					terminal(TRANSIENT)
					action({ modifiers = append(modifiers, SModifier.make(ModifierKeyword.Transient)); })
				)
				sequence(
					terminal(VOLATILE)
					action({ modifiers = append(modifiers, SModifier.make(ModifierKeyword.Volatile)); })
				)
				sequence(
					terminal(SYNCHRONIZED)
					action({ modifiers = append(modifiers, SModifier.make(ModifierKeyword.Synchronized)); })
				)
				sequence(
					terminal(NATIVE)
					action({ modifiers = append(modifiers, SModifier.make(ModifierKeyword.Native)); })
				)
				sequence(
					terminal(STRICTFP)
					action({ modifiers = append(modifiers, SModifier.make(ModifierKeyword.StrictFP)); })
				)
				sequence(
					nonTerminal(ann, Annotation)
					action({ modifiers = append(modifiers, ann); })
				)
			)
		)
		action({ return modifiers; })
	) */
	protected BUTree<SNodeList> parseModifiersNoDefault() throws ParseException {
		BUTree<SNodeList> modifiers = emptyList();
		BUTree<? extends SAnnotationExpr> ann;
		while (matchModifiersNoDefault_lookahead1(0) != -1) {
			if (match(0, TokenType.PUBLIC) != -1) {
				parse(TokenType.PUBLIC);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Public));
			} else if (match(0, TokenType.PROTECTED) != -1) {
				parse(TokenType.PROTECTED);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Protected));
			} else if (match(0, TokenType.PRIVATE) != -1) {
				parse(TokenType.PRIVATE);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Private));
			} else if (match(0, TokenType.ABSTRACT) != -1) {
				parse(TokenType.ABSTRACT);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Abstract));
			} else if (match(0, TokenType.STATIC) != -1) {
				parse(TokenType.STATIC);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Static));
			} else if (match(0, TokenType.FINAL) != -1) {
				parse(TokenType.FINAL);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Final));
			} else if (match(0, TokenType.TRANSIENT) != -1) {
				parse(TokenType.TRANSIENT);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Transient));
			} else if (match(0, TokenType.VOLATILE) != -1) {
				parse(TokenType.VOLATILE);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Volatile));
			} else if (match(0, TokenType.SYNCHRONIZED) != -1) {
				parse(TokenType.SYNCHRONIZED);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Synchronized));
			} else if (match(0, TokenType.NATIVE) != -1) {
				parse(TokenType.NATIVE);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Native));
			} else if (match(0, TokenType.STRICTFP) != -1) {
				parse(TokenType.STRICTFP);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.StrictFP));
			} else if (match(0, TokenType.AT) != -1) {
				ann = parseAnnotation();
				modifiers = append(modifiers, ann);
			} else {
				throw produceParseException(TokenType.AT, TokenType.STRICTFP, TokenType.NATIVE, TokenType.SYNCHRONIZED, TokenType.VOLATILE, TokenType.TRANSIENT, TokenType.FINAL, TokenType.STATIC, TokenType.ABSTRACT, TokenType.PRIVATE, TokenType.PROTECTED, TokenType.PUBLIC);
			}
		}
		return modifiers;
	}

	/* sequence(
		zeroOrMore(
			lookAhead(2)
			choice(
				sequence(
					terminal(PUBLIC)
				)
				sequence(
					terminal(PROTECTED)
				)
				sequence(
					terminal(PRIVATE)
				)
				sequence(
					terminal(ABSTRACT)
				)
				sequence(
					terminal(STATIC)
				)
				sequence(
					terminal(FINAL)
				)
				sequence(
					terminal(TRANSIENT)
				)
				sequence(
					terminal(VOLATILE)
				)
				sequence(
					terminal(SYNCHRONIZED)
				)
				sequence(
					terminal(NATIVE)
				)
				sequence(
					terminal(STRICTFP)
				)
				sequence(
					nonTerminal(ann, Annotation)
				)
			)
		)
	) */
	private int matchModifiersNoDefault(int lookahead) {
		lookahead = matchModifiersNoDefault_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		lookAhead(2)
		choice(
			sequence(
				terminal(PUBLIC)
			)
			sequence(
				terminal(PROTECTED)
			)
			sequence(
				terminal(PRIVATE)
			)
			sequence(
				terminal(ABSTRACT)
			)
			sequence(
				terminal(STATIC)
			)
			sequence(
				terminal(FINAL)
			)
			sequence(
				terminal(TRANSIENT)
			)
			sequence(
				terminal(VOLATILE)
			)
			sequence(
				terminal(SYNCHRONIZED)
			)
			sequence(
				terminal(NATIVE)
			)
			sequence(
				terminal(STRICTFP)
			)
			sequence(
				nonTerminal(ann, Annotation)
			)
		)
	) */
	private int matchModifiersNoDefault_1(int lookahead) {
		int newLookahead;
		newLookahead = matchModifiersNoDefault_1_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchModifiersNoDefault_1_1(lookahead);
		}
		return lookahead;
	}

	/* sequence(
		lookAhead(2)
		choice(
			sequence(
				terminal(PUBLIC)
			)
			sequence(
				terminal(PROTECTED)
			)
			sequence(
				terminal(PRIVATE)
			)
			sequence(
				terminal(ABSTRACT)
			)
			sequence(
				terminal(STATIC)
			)
			sequence(
				terminal(FINAL)
			)
			sequence(
				terminal(TRANSIENT)
			)
			sequence(
				terminal(VOLATILE)
			)
			sequence(
				terminal(SYNCHRONIZED)
			)
			sequence(
				terminal(NATIVE)
			)
			sequence(
				terminal(STRICTFP)
			)
			sequence(
				nonTerminal(ann, Annotation)
			)
		)
	) */
	private int matchModifiersNoDefault_1_1(int lookahead) {
		lookahead = matchModifiersNoDefault_1_1_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			terminal(PUBLIC)
		)
		sequence(
			terminal(PROTECTED)
		)
		sequence(
			terminal(PRIVATE)
		)
		sequence(
			terminal(ABSTRACT)
		)
		sequence(
			terminal(STATIC)
		)
		sequence(
			terminal(FINAL)
		)
		sequence(
			terminal(TRANSIENT)
		)
		sequence(
			terminal(VOLATILE)
		)
		sequence(
			terminal(SYNCHRONIZED)
		)
		sequence(
			terminal(NATIVE)
		)
		sequence(
			terminal(STRICTFP)
		)
		sequence(
			nonTerminal(ann, Annotation)
		)
	) */
	private int matchModifiersNoDefault_1_1_2(int lookahead) {
		int newLookahead;
		newLookahead = matchModifiersNoDefault_1_1_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchModifiersNoDefault_1_1_2_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchModifiersNoDefault_1_1_2_3(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchModifiersNoDefault_1_1_2_4(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchModifiersNoDefault_1_1_2_5(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchModifiersNoDefault_1_1_2_6(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchModifiersNoDefault_1_1_2_7(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchModifiersNoDefault_1_1_2_8(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchModifiersNoDefault_1_1_2_9(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchModifiersNoDefault_1_1_2_10(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchModifiersNoDefault_1_1_2_11(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchModifiersNoDefault_1_1_2_12(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		terminal(PUBLIC)
	) */
	private int matchModifiersNoDefault_1_1_2_1(int lookahead) {
		lookahead = match(lookahead, TokenType.PUBLIC);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(NATIVE)
	) */
	private int matchModifiersNoDefault_1_1_2_10(int lookahead) {
		lookahead = match(lookahead, TokenType.NATIVE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(STRICTFP)
	) */
	private int matchModifiersNoDefault_1_1_2_11(int lookahead) {
		lookahead = match(lookahead, TokenType.STRICTFP);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(ann, Annotation)
	) */
	private int matchModifiersNoDefault_1_1_2_12(int lookahead) {
		lookahead = matchAnnotation(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(PROTECTED)
	) */
	private int matchModifiersNoDefault_1_1_2_2(int lookahead) {
		lookahead = match(lookahead, TokenType.PROTECTED);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(PRIVATE)
	) */
	private int matchModifiersNoDefault_1_1_2_3(int lookahead) {
		lookahead = match(lookahead, TokenType.PRIVATE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(ABSTRACT)
	) */
	private int matchModifiersNoDefault_1_1_2_4(int lookahead) {
		lookahead = match(lookahead, TokenType.ABSTRACT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(STATIC)
	) */
	private int matchModifiersNoDefault_1_1_2_5(int lookahead) {
		lookahead = match(lookahead, TokenType.STATIC);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(FINAL)
	) */
	private int matchModifiersNoDefault_1_1_2_6(int lookahead) {
		lookahead = match(lookahead, TokenType.FINAL);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(TRANSIENT)
	) */
	private int matchModifiersNoDefault_1_1_2_7(int lookahead) {
		lookahead = match(lookahead, TokenType.TRANSIENT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(VOLATILE)
	) */
	private int matchModifiersNoDefault_1_1_2_8(int lookahead) {
		lookahead = match(lookahead, TokenType.VOLATILE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(SYNCHRONIZED)
	) */
	private int matchModifiersNoDefault_1_1_2_9(int lookahead) {
		lookahead = match(lookahead, TokenType.SYNCHRONIZED);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		lookAhead(2)
		choice(
			sequence(
				terminal(PUBLIC)
			)
			sequence(
				terminal(PROTECTED)
			)
			sequence(
				terminal(PRIVATE)
			)
			sequence(
				terminal(ABSTRACT)
			)
			sequence(
				terminal(STATIC)
			)
			sequence(
				terminal(FINAL)
			)
			sequence(
				terminal(TRANSIENT)
			)
			sequence(
				terminal(VOLATILE)
			)
			sequence(
				terminal(SYNCHRONIZED)
			)
			sequence(
				terminal(NATIVE)
			)
			sequence(
				terminal(STRICTFP)
			)
			sequence(
				nonTerminal(ann, Annotation)
			)
		)
	) */
	private int matchModifiersNoDefault_lookahead1(int lookahead) {
		if (match(0, TokenType.VOLATILE) != -1) {
			return lookahead;
		}
		if (match(0, TokenType.SYNCHRONIZED) != -1) {
			return lookahead;
		}
		if (match(0, TokenType.NATIVE) != -1) {
			return lookahead;
		}
		if (match(0, TokenType.AT) != -1) {
			if (match(1, TokenType.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, TokenType.PUBLIC) != -1) {
			return lookahead;
		}
		if (match(0, TokenType.FINAL) != -1) {
			return lookahead;
		}
		if (match(0, TokenType.PROTECTED) != -1) {
			return lookahead;
		}
		if (match(0, TokenType.ABSTRACT) != -1) {
			return lookahead;
		}
		if (match(0, TokenType.TRANSIENT) != -1) {
			return lookahead;
		}
		if (match(0, TokenType.STRICTFP) != -1) {
			return lookahead;
		}
		if (match(0, TokenType.STATIC) != -1) {
			return lookahead;
		}
		if (match(0, TokenType.PRIVATE) != -1) {
			return lookahead;
		}
		return -1;
	}

	/* sequence(
		action({ run(); })
		choice(
			sequence(
				terminal(SEMICOLON)
				action({ ret = dress(SEmptyTypeDecl.make()); })
			)
			sequence(
				nonTerminal(modifiers, Modifiers)
				choice(
					nonTerminal(ret, ClassOrInterfaceDecl)
					nonTerminal(ret, EnumDecl)
					nonTerminal(ret, AnnotationTypeDecl)
				)
			)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends STypeDecl> parseTypeDecl() throws ParseException {
		BUTree<SNodeList> modifiers;
		BUTree<? extends STypeDecl> ret;
		run();
		if (match(0, TokenType.SEMICOLON) != -1) {
			parse(TokenType.SEMICOLON);
			ret = dress(SEmptyTypeDecl.make());
		} else if (match(0, TokenType.PROTECTED, TokenType.PUBLIC, TokenType.ABSTRACT, TokenType.PRIVATE, TokenType.STATIC, TokenType.DEFAULT, TokenType.TRANSIENT, TokenType.FINAL, TokenType.SYNCHRONIZED, TokenType.VOLATILE, TokenType.STRICTFP, TokenType.NATIVE, TokenType.AT, TokenType.ENUM, TokenType.CLASS, TokenType.INTERFACE) != -1) {
			modifiers = parseModifiers();
			if (match(0, TokenType.CLASS, TokenType.INTERFACE) != -1) {
				ret = parseClassOrInterfaceDecl(modifiers);
			} else if (match(0, TokenType.ENUM) != -1) {
				ret = parseEnumDecl(modifiers);
			} else if (match(0, TokenType.AT) != -1) {
				ret = parseAnnotationTypeDecl(modifiers);
			} else {
				throw produceParseException(TokenType.AT, TokenType.ENUM, TokenType.INTERFACE, TokenType.CLASS);
			}
		} else {
			throw produceParseException(TokenType.VOLATILE, TokenType.TRANSIENT, TokenType.NATIVE, TokenType.SYNCHRONIZED, TokenType.AT, TokenType.STRICTFP, TokenType.PUBLIC, TokenType.PRIVATE, TokenType.PROTECTED, TokenType.DEFAULT, TokenType.ABSTRACT, TokenType.FINAL, TokenType.STATIC, TokenType.ENUM, TokenType.CLASS, TokenType.INTERFACE, TokenType.SEMICOLON);
		}
		return ret;
	}

	/* sequence(
		choice(
			sequence(
				terminal(CLASS)
				action({ typeKind = TypeKind.Class; })
				nonTerminal(name, Name)
				zeroOrOne(
					nonTerminal(typeParams, TypeParameters)
				)
				zeroOrOne(
					terminal(EXTENDS)
					nonTerminal(superClassType, AnnotatedQualifiedType)
				)
				zeroOrOne(
					nonTerminal(implementsClause, ImplementsList)
				)
			)
			sequence(
				terminal(INTERFACE)
				action({ typeKind = TypeKind.Interface; })
				nonTerminal(name, Name)
				zeroOrOne(
					nonTerminal(typeParams, TypeParameters)
				)
				zeroOrOne(
					nonTerminal(extendsClause, ExtendsList)
				)
			)
		)
		nonTerminal(members, ClassOrInterfaceBody)
		action({
			if (typeKind == TypeKind.Interface)
				return dress(SInterfaceDecl.make(modifiers, name, ensureNotNull(typeParams), ensureNotNull(extendsClause), members)).withProblem(problem.value);
			else {
				return dress(SClassDecl.make(modifiers, name, ensureNotNull(typeParams), optionOf(superClassType), ensureNotNull(implementsClause), members));
			}
		})
	) */
	protected BUTree<? extends STypeDecl> parseClassOrInterfaceDecl(BUTree<SNodeList> modifiers) throws ParseException {
		TypeKind typeKind;
		BUTree<SName> name;
		BUTree<SNodeList> typeParams = null;
		BUTree<SQualifiedType> superClassType = null;
		BUTree<SNodeList> extendsClause = null;
		BUTree<SNodeList> implementsClause = null;
		BUTree<SNodeList> members;
		ByRef<BUProblem> problem = new ByRef<BUProblem>(null);
		if (match(0, TokenType.CLASS) != -1) {
			parse(TokenType.CLASS);
			typeKind = TypeKind.Class;
			name = parseName();
			if (match(0, TokenType.LT) != -1) {
				typeParams = parseTypeParameters();
			}
			if (match(0, TokenType.EXTENDS) != -1) {
				parse(TokenType.EXTENDS);
				superClassType = parseAnnotatedQualifiedType();
			}
			if (match(0, TokenType.IMPLEMENTS) != -1) {
				implementsClause = parseImplementsList(typeKind, problem);
			}
		} else if (match(0, TokenType.INTERFACE) != -1) {
			parse(TokenType.INTERFACE);
			typeKind = TypeKind.Interface;
			name = parseName();
			if (match(0, TokenType.LT) != -1) {
				typeParams = parseTypeParameters();
			}
			if (match(0, TokenType.EXTENDS) != -1) {
				extendsClause = parseExtendsList();
			}
		} else {
			throw produceParseException(TokenType.INTERFACE, TokenType.CLASS);
		}
		members = parseClassOrInterfaceBody(typeKind);
		if (typeKind == TypeKind.Interface)
			return dress(SInterfaceDecl.make(modifiers, name, ensureNotNull(typeParams), ensureNotNull(extendsClause), members)).withProblem(problem.value);
		else {
			return dress(SClassDecl.make(modifiers, name, ensureNotNull(typeParams), optionOf(superClassType), ensureNotNull(implementsClause), members));
		}
	}

	/* sequence(
		choice(
			sequence(
				terminal(CLASS)
				nonTerminal(name, Name)
				zeroOrOne(
					nonTerminal(typeParams, TypeParameters)
				)
				zeroOrOne(
					terminal(EXTENDS)
					nonTerminal(superClassType, AnnotatedQualifiedType)
				)
				zeroOrOne(
					nonTerminal(implementsClause, ImplementsList)
				)
			)
			sequence(
				terminal(INTERFACE)
				nonTerminal(name, Name)
				zeroOrOne(
					nonTerminal(typeParams, TypeParameters)
				)
				zeroOrOne(
					nonTerminal(extendsClause, ExtendsList)
				)
			)
		)
		nonTerminal(members, ClassOrInterfaceBody)
	) */
	private int matchClassOrInterfaceDecl(int lookahead) {
		lookahead = matchClassOrInterfaceDecl_1(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchClassOrInterfaceBody(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			terminal(CLASS)
			nonTerminal(name, Name)
			zeroOrOne(
				nonTerminal(typeParams, TypeParameters)
			)
			zeroOrOne(
				terminal(EXTENDS)
				nonTerminal(superClassType, AnnotatedQualifiedType)
			)
			zeroOrOne(
				nonTerminal(implementsClause, ImplementsList)
			)
		)
		sequence(
			terminal(INTERFACE)
			nonTerminal(name, Name)
			zeroOrOne(
				nonTerminal(typeParams, TypeParameters)
			)
			zeroOrOne(
				nonTerminal(extendsClause, ExtendsList)
			)
		)
	) */
	private int matchClassOrInterfaceDecl_1(int lookahead) {
		int newLookahead;
		newLookahead = matchClassOrInterfaceDecl_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchClassOrInterfaceDecl_1_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		terminal(CLASS)
		nonTerminal(name, Name)
		zeroOrOne(
			nonTerminal(typeParams, TypeParameters)
		)
		zeroOrOne(
			terminal(EXTENDS)
			nonTerminal(superClassType, AnnotatedQualifiedType)
		)
		zeroOrOne(
			nonTerminal(implementsClause, ImplementsList)
		)
	) */
	private int matchClassOrInterfaceDecl_1_1(int lookahead) {
		lookahead = match(lookahead, TokenType.CLASS);
		if (lookahead == -1)
			return -1;
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchClassOrInterfaceDecl_1_1_4(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchClassOrInterfaceDecl_1_1_5(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchClassOrInterfaceDecl_1_1_6(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		nonTerminal(typeParams, TypeParameters)
	) */
	private int matchClassOrInterfaceDecl_1_1_4(int lookahead) {
		int newLookahead;
		newLookahead = matchClassOrInterfaceDecl_1_1_4_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		nonTerminal(typeParams, TypeParameters)
	) */
	private int matchClassOrInterfaceDecl_1_1_4_1(int lookahead) {
		lookahead = matchTypeParameters(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		terminal(EXTENDS)
		nonTerminal(superClassType, AnnotatedQualifiedType)
	) */
	private int matchClassOrInterfaceDecl_1_1_5(int lookahead) {
		int newLookahead;
		newLookahead = matchClassOrInterfaceDecl_1_1_5_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		terminal(EXTENDS)
		nonTerminal(superClassType, AnnotatedQualifiedType)
	) */
	private int matchClassOrInterfaceDecl_1_1_5_1(int lookahead) {
		lookahead = match(lookahead, TokenType.EXTENDS);
		if (lookahead == -1)
			return -1;
		lookahead = matchAnnotatedQualifiedType(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		nonTerminal(implementsClause, ImplementsList)
	) */
	private int matchClassOrInterfaceDecl_1_1_6(int lookahead) {
		int newLookahead;
		newLookahead = matchClassOrInterfaceDecl_1_1_6_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		nonTerminal(implementsClause, ImplementsList)
	) */
	private int matchClassOrInterfaceDecl_1_1_6_1(int lookahead) {
		lookahead = matchImplementsList(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(INTERFACE)
		nonTerminal(name, Name)
		zeroOrOne(
			nonTerminal(typeParams, TypeParameters)
		)
		zeroOrOne(
			nonTerminal(extendsClause, ExtendsList)
		)
	) */
	private int matchClassOrInterfaceDecl_1_2(int lookahead) {
		lookahead = match(lookahead, TokenType.INTERFACE);
		if (lookahead == -1)
			return -1;
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchClassOrInterfaceDecl_1_2_4(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchClassOrInterfaceDecl_1_2_5(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		nonTerminal(typeParams, TypeParameters)
	) */
	private int matchClassOrInterfaceDecl_1_2_4(int lookahead) {
		int newLookahead;
		newLookahead = matchClassOrInterfaceDecl_1_2_4_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		nonTerminal(typeParams, TypeParameters)
	) */
	private int matchClassOrInterfaceDecl_1_2_4_1(int lookahead) {
		lookahead = matchTypeParameters(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		nonTerminal(extendsClause, ExtendsList)
	) */
	private int matchClassOrInterfaceDecl_1_2_5(int lookahead) {
		int newLookahead;
		newLookahead = matchClassOrInterfaceDecl_1_2_5_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		nonTerminal(extendsClause, ExtendsList)
	) */
	private int matchClassOrInterfaceDecl_1_2_5_1(int lookahead) {
		lookahead = matchExtendsList(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(EXTENDS)
		choice(
			sequence(
				nonTerminal(cit, AnnotatedQualifiedType)
				action({ ret = append(ret, cit); })
				zeroOrMore(
					terminal(COMMA)
					nonTerminal(cit, AnnotatedQualifiedType)
					action({ ret = append(ret, cit); })
				)
			)
			sequence(
				lookAhead({ quotesMode })
				nonTerminal(ret, NodeListVar)
			)
		)
		action({ return ret; })
	) */
	protected BUTree<SNodeList> parseExtendsList() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<SQualifiedType> cit;
		BUTree<SNodeList> annotations = null;
		parse(TokenType.EXTENDS);
		if (match(0, TokenType.AT, TokenType.IDENTIFIER, TokenType.NODE_VARIABLE) != -1) {
			cit = parseAnnotatedQualifiedType();
			ret = append(ret, cit);
			while (match(0, TokenType.COMMA) != -1) {
				parse(TokenType.COMMA);
				cit = parseAnnotatedQualifiedType();
				ret = append(ret, cit);
			}
		} else if (quotesMode) {
			ret = parseNodeListVar();
		} else {
			throw produceParseException(TokenType.NODE_LIST_VARIABLE, TokenType.AT, TokenType.IDENTIFIER, TokenType.NODE_VARIABLE);
		}
		return ret;
	}

	/* sequence(
		terminal(EXTENDS)
		choice(
			sequence(
				nonTerminal(cit, AnnotatedQualifiedType)
				zeroOrMore(
					terminal(COMMA)
					nonTerminal(cit, AnnotatedQualifiedType)
				)
			)
			sequence(
				lookAhead({ quotesMode })
				nonTerminal(ret, NodeListVar)
			)
		)
	) */
	private int matchExtendsList(int lookahead) {
		lookahead = match(lookahead, TokenType.EXTENDS);
		if (lookahead == -1)
			return -1;
		lookahead = matchExtendsList_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			nonTerminal(cit, AnnotatedQualifiedType)
			zeroOrMore(
				terminal(COMMA)
				nonTerminal(cit, AnnotatedQualifiedType)
			)
		)
		sequence(
			lookAhead({ quotesMode })
			nonTerminal(ret, NodeListVar)
		)
	) */
	private int matchExtendsList_2(int lookahead) {
		int newLookahead;
		newLookahead = matchExtendsList_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchExtendsList_2_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		nonTerminal(cit, AnnotatedQualifiedType)
		zeroOrMore(
			terminal(COMMA)
			nonTerminal(cit, AnnotatedQualifiedType)
		)
	) */
	private int matchExtendsList_2_1(int lookahead) {
		lookahead = matchAnnotatedQualifiedType(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchExtendsList_2_1_3(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		terminal(COMMA)
		nonTerminal(cit, AnnotatedQualifiedType)
	) */
	private int matchExtendsList_2_1_3(int lookahead) {
		int newLookahead;
		newLookahead = matchExtendsList_2_1_3_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchExtendsList_2_1_3_1(lookahead);
		}
		return lookahead;
	}

	/* sequence(
		terminal(COMMA)
		nonTerminal(cit, AnnotatedQualifiedType)
	) */
	private int matchExtendsList_2_1_3_1(int lookahead) {
		lookahead = match(lookahead, TokenType.COMMA);
		if (lookahead == -1)
			return -1;
		lookahead = matchAnnotatedQualifiedType(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead({ quotesMode })
		nonTerminal(ret, NodeListVar)
	) */
	private int matchExtendsList_2_2(int lookahead) {
		lookahead = quotesMode ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = matchNodeListVar(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(IMPLEMENTS)
		choice(
			sequence(
				nonTerminal(cit, AnnotatedQualifiedType)
				action({ ret = append(ret, cit); })
				zeroOrMore(
					terminal(COMMA)
					nonTerminal(cit, AnnotatedQualifiedType)
					action({ ret = append(ret, cit); })
				)
				action({
					if (typeKind == TypeKind.Interface) problem.value = new BUProblem(Severity.ERROR, "An interface cannot implement other interfaces");
				})
			)
			sequence(
				lookAhead({ quotesMode })
				nonTerminal(ret, NodeListVar)
			)
		)
		action({ return ret; })
	) */
	protected BUTree<SNodeList> parseImplementsList(TypeKind typeKind, ByRef<BUProblem> problem) throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<SQualifiedType> cit;
		BUTree<SNodeList> annotations = null;
		parse(TokenType.IMPLEMENTS);
		if (match(0, TokenType.AT, TokenType.IDENTIFIER, TokenType.NODE_VARIABLE) != -1) {
			cit = parseAnnotatedQualifiedType();
			ret = append(ret, cit);
			while (match(0, TokenType.COMMA) != -1) {
				parse(TokenType.COMMA);
				cit = parseAnnotatedQualifiedType();
				ret = append(ret, cit);
			}
			if (typeKind == TypeKind.Interface) problem.value = new BUProblem(Severity.ERROR, "An interface cannot implement other interfaces");

		} else if (quotesMode) {
			ret = parseNodeListVar();
		} else {
			throw produceParseException(TokenType.NODE_LIST_VARIABLE, TokenType.AT, TokenType.IDENTIFIER, TokenType.NODE_VARIABLE);
		}
		return ret;
	}

	/* sequence(
		terminal(IMPLEMENTS)
		choice(
			sequence(
				nonTerminal(cit, AnnotatedQualifiedType)
				zeroOrMore(
					terminal(COMMA)
					nonTerminal(cit, AnnotatedQualifiedType)
				)
			)
			sequence(
				lookAhead({ quotesMode })
				nonTerminal(ret, NodeListVar)
			)
		)
	) */
	private int matchImplementsList(int lookahead) {
		lookahead = match(lookahead, TokenType.IMPLEMENTS);
		if (lookahead == -1)
			return -1;
		lookahead = matchImplementsList_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			nonTerminal(cit, AnnotatedQualifiedType)
			zeroOrMore(
				terminal(COMMA)
				nonTerminal(cit, AnnotatedQualifiedType)
			)
		)
		sequence(
			lookAhead({ quotesMode })
			nonTerminal(ret, NodeListVar)
		)
	) */
	private int matchImplementsList_2(int lookahead) {
		int newLookahead;
		newLookahead = matchImplementsList_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchImplementsList_2_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		nonTerminal(cit, AnnotatedQualifiedType)
		zeroOrMore(
			terminal(COMMA)
			nonTerminal(cit, AnnotatedQualifiedType)
		)
	) */
	private int matchImplementsList_2_1(int lookahead) {
		lookahead = matchAnnotatedQualifiedType(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchImplementsList_2_1_3(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		terminal(COMMA)
		nonTerminal(cit, AnnotatedQualifiedType)
	) */
	private int matchImplementsList_2_1_3(int lookahead) {
		int newLookahead;
		newLookahead = matchImplementsList_2_1_3_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchImplementsList_2_1_3_1(lookahead);
		}
		return lookahead;
	}

	/* sequence(
		terminal(COMMA)
		nonTerminal(cit, AnnotatedQualifiedType)
	) */
	private int matchImplementsList_2_1_3_1(int lookahead) {
		lookahead = match(lookahead, TokenType.COMMA);
		if (lookahead == -1)
			return -1;
		lookahead = matchAnnotatedQualifiedType(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead({ quotesMode })
		nonTerminal(ret, NodeListVar)
	) */
	private int matchImplementsList_2_2(int lookahead) {
		lookahead = quotesMode ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = matchNodeListVar(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(ENUM)
		nonTerminal(name, Name)
		zeroOrOne(
			nonTerminal(implementsClause, ImplementsList)
		)
		terminal(LBRACE)
		zeroOrOne(
			choice(
				sequence(
					nonTerminal(entry, EnumConstantDecl)
					action({ constants = append(constants, entry); })
					zeroOrMore(
						negativeLookAhead(
							zeroOrOne(
								terminal(COMMA)
							)
							choice(
								terminal(SEMICOLON)
								terminal(RBRACE)
							)
						)
						terminal(COMMA)
						nonTerminal(entry, EnumConstantDecl)
						action({ constants = append(constants, entry); })
					)
				)
				sequence(
					lookAhead({ quotesMode })
					nonTerminal(constants, NodeListVar)
				)
			)
		)
		zeroOrOne(
			terminal(COMMA)
			action({ trailingComma = true; })
		)
		zeroOrOne(
			terminal(SEMICOLON)
			nonTerminal(members, ClassOrInterfaceBodyDecls)
		)
		terminal(RBRACE)
		action({ return dress(SEnumDecl.make(modifiers, name, implementsClause, constants, trailingComma, ensureNotNull(members))).withProblem(problem.value); })
	) */
	protected BUTree<? extends STypeDecl> parseEnumDecl(BUTree<SNodeList> modifiers) throws ParseException {
		BUTree<SName> name;
		BUTree<SNodeList> implementsClause = emptyList();
		BUTree<SEnumConstantDecl> entry;
		BUTree<SNodeList> constants = emptyList();
		boolean trailingComma = false;
		BUTree<SNodeList> members = null;
		ByRef<BUProblem> problem = new ByRef<BUProblem>(null);
		parse(TokenType.ENUM);
		name = parseName();
		if (match(0, TokenType.IMPLEMENTS) != -1) {
			implementsClause = parseImplementsList(TypeKind.Enum, problem);
		}
		parse(TokenType.LBRACE);
		if (match(0, TokenType.PUBLIC, TokenType.AT, TokenType.STRICTFP, TokenType.NATIVE, TokenType.SYNCHRONIZED, TokenType.VOLATILE, TokenType.TRANSIENT, TokenType.FINAL, TokenType.STATIC, TokenType.DEFAULT, TokenType.ABSTRACT, TokenType.PRIVATE, TokenType.PROTECTED, TokenType.IDENTIFIER, TokenType.NODE_VARIABLE, TokenType.NODE_LIST_VARIABLE) != -1) {
			if (match(0, TokenType.AT, TokenType.STRICTFP, TokenType.NATIVE, TokenType.SYNCHRONIZED, TokenType.DEFAULT, TokenType.ABSTRACT, TokenType.PRIVATE, TokenType.PROTECTED, TokenType.VOLATILE, TokenType.TRANSIENT, TokenType.FINAL, TokenType.STATIC, TokenType.PUBLIC, TokenType.IDENTIFIER, TokenType.NODE_VARIABLE) != -1) {
				entry = parseEnumConstantDecl();
				constants = append(constants, entry);
				while (matchEnumDecl_lookahead1(0) == -1) {
					parse(TokenType.COMMA);
					entry = parseEnumConstantDecl();
					constants = append(constants, entry);
				}
			} else if (quotesMode) {
				constants = parseNodeListVar();
			} else {
				throw produceParseException(TokenType.NODE_LIST_VARIABLE, TokenType.PROTECTED, TokenType.PRIVATE, TokenType.PUBLIC, TokenType.STRICTFP, TokenType.AT, TokenType.STATIC, TokenType.FINAL, TokenType.ABSTRACT, TokenType.DEFAULT, TokenType.SYNCHRONIZED, TokenType.NATIVE, TokenType.TRANSIENT, TokenType.VOLATILE, TokenType.IDENTIFIER, TokenType.NODE_VARIABLE);
			}
		}
		if (match(0, TokenType.COMMA) != -1) {
			parse(TokenType.COMMA);
			trailingComma = true;
		}
		if (match(0, TokenType.SEMICOLON) != -1) {
			parse(TokenType.SEMICOLON);
			members = parseClassOrInterfaceBodyDecls(TypeKind.Enum);
		}
		parse(TokenType.RBRACE);
		return dress(SEnumDecl.make(modifiers, name, implementsClause, constants, trailingComma, ensureNotNull(members))).withProblem(problem.value);
	}

	/* sequence(
		terminal(ENUM)
		nonTerminal(name, Name)
		zeroOrOne(
			nonTerminal(implementsClause, ImplementsList)
		)
		terminal(LBRACE)
		zeroOrOne(
			choice(
				sequence(
					nonTerminal(entry, EnumConstantDecl)
					zeroOrMore(
						negativeLookAhead(
							zeroOrOne(
								terminal(COMMA)
							)
							choice(
								terminal(SEMICOLON)
								terminal(RBRACE)
							)
						)
						terminal(COMMA)
						nonTerminal(entry, EnumConstantDecl)
					)
				)
				sequence(
					lookAhead({ quotesMode })
					nonTerminal(constants, NodeListVar)
				)
			)
		)
		zeroOrOne(
			terminal(COMMA)
		)
		zeroOrOne(
			terminal(SEMICOLON)
			nonTerminal(members, ClassOrInterfaceBodyDecls)
		)
		terminal(RBRACE)
	) */
	private int matchEnumDecl(int lookahead) {
		lookahead = match(lookahead, TokenType.ENUM);
		if (lookahead == -1)
			return -1;
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchEnumDecl_3(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.LBRACE);
		if (lookahead == -1)
			return -1;
		lookahead = matchEnumDecl_5(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchEnumDecl_6(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchEnumDecl_7(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.RBRACE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		nonTerminal(implementsClause, ImplementsList)
	) */
	private int matchEnumDecl_3(int lookahead) {
		int newLookahead;
		newLookahead = matchEnumDecl_3_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		nonTerminal(implementsClause, ImplementsList)
	) */
	private int matchEnumDecl_3_1(int lookahead) {
		lookahead = matchImplementsList(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		choice(
			sequence(
				nonTerminal(entry, EnumConstantDecl)
				zeroOrMore(
					negativeLookAhead(
						zeroOrOne(
							terminal(COMMA)
						)
						choice(
							terminal(SEMICOLON)
							terminal(RBRACE)
						)
					)
					terminal(COMMA)
					nonTerminal(entry, EnumConstantDecl)
				)
			)
			sequence(
				lookAhead({ quotesMode })
				nonTerminal(constants, NodeListVar)
			)
		)
	) */
	private int matchEnumDecl_5(int lookahead) {
		int newLookahead;
		newLookahead = matchEnumDecl_5_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		choice(
			sequence(
				nonTerminal(entry, EnumConstantDecl)
				zeroOrMore(
					negativeLookAhead(
						zeroOrOne(
							terminal(COMMA)
						)
						choice(
							terminal(SEMICOLON)
							terminal(RBRACE)
						)
					)
					terminal(COMMA)
					nonTerminal(entry, EnumConstantDecl)
				)
			)
			sequence(
				lookAhead({ quotesMode })
				nonTerminal(constants, NodeListVar)
			)
		)
	) */
	private int matchEnumDecl_5_1(int lookahead) {
		lookahead = matchEnumDecl_5_1_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			nonTerminal(entry, EnumConstantDecl)
			zeroOrMore(
				negativeLookAhead(
					zeroOrOne(
						terminal(COMMA)
					)
					choice(
						terminal(SEMICOLON)
						terminal(RBRACE)
					)
				)
				terminal(COMMA)
				nonTerminal(entry, EnumConstantDecl)
			)
		)
		sequence(
			lookAhead({ quotesMode })
			nonTerminal(constants, NodeListVar)
		)
	) */
	private int matchEnumDecl_5_1_1(int lookahead) {
		int newLookahead;
		newLookahead = matchEnumDecl_5_1_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchEnumDecl_5_1_1_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		nonTerminal(entry, EnumConstantDecl)
		zeroOrMore(
			negativeLookAhead(
				zeroOrOne(
					terminal(COMMA)
				)
				choice(
					terminal(SEMICOLON)
					terminal(RBRACE)
				)
			)
			terminal(COMMA)
			nonTerminal(entry, EnumConstantDecl)
		)
	) */
	private int matchEnumDecl_5_1_1_1(int lookahead) {
		lookahead = matchEnumConstantDecl(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchEnumDecl_5_1_1_1_3(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		negativeLookAhead(
			zeroOrOne(
				terminal(COMMA)
			)
			choice(
				terminal(SEMICOLON)
				terminal(RBRACE)
			)
		)
		terminal(COMMA)
		nonTerminal(entry, EnumConstantDecl)
	) */
	private int matchEnumDecl_5_1_1_1_3(int lookahead) {
		int newLookahead;
		newLookahead = matchEnumDecl_5_1_1_1_3_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchEnumDecl_5_1_1_1_3_1(lookahead);
		}
		return lookahead;
	}

	/* sequence(
		negativeLookAhead(
			zeroOrOne(
				terminal(COMMA)
			)
			choice(
				terminal(SEMICOLON)
				terminal(RBRACE)
			)
		)
		terminal(COMMA)
		nonTerminal(entry, EnumConstantDecl)
	) */
	private int matchEnumDecl_5_1_1_1_3_1(int lookahead) {
		lookahead = match(lookahead, TokenType.COMMA);
		if (lookahead == -1)
			return -1;
		lookahead = matchEnumConstantDecl(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead({ quotesMode })
		nonTerminal(constants, NodeListVar)
	) */
	private int matchEnumDecl_5_1_1_2(int lookahead) {
		lookahead = quotesMode ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = matchNodeListVar(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		terminal(COMMA)
	) */
	private int matchEnumDecl_6(int lookahead) {
		int newLookahead;
		newLookahead = matchEnumDecl_6_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		terminal(COMMA)
	) */
	private int matchEnumDecl_6_1(int lookahead) {
		lookahead = match(lookahead, TokenType.COMMA);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		terminal(SEMICOLON)
		nonTerminal(members, ClassOrInterfaceBodyDecls)
	) */
	private int matchEnumDecl_7(int lookahead) {
		int newLookahead;
		newLookahead = matchEnumDecl_7_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		terminal(SEMICOLON)
		nonTerminal(members, ClassOrInterfaceBodyDecls)
	) */
	private int matchEnumDecl_7_1(int lookahead) {
		lookahead = match(lookahead, TokenType.SEMICOLON);
		if (lookahead == -1)
			return -1;
		lookahead = matchClassOrInterfaceBodyDecls(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		zeroOrOne(
			terminal(COMMA)
		)
		choice(
			terminal(SEMICOLON)
			terminal(RBRACE)
		)
	) */
	private int matchEnumDecl_lookahead1(int lookahead) {
		lookahead = matchEnumDecl_lookahead1_1(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchEnumDecl_lookahead1_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		terminal(COMMA)
	) */
	private int matchEnumDecl_lookahead1_1(int lookahead) {
		int newLookahead;
		newLookahead = matchEnumDecl_lookahead1_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		terminal(COMMA)
	) */
	private int matchEnumDecl_lookahead1_1_1(int lookahead) {
		lookahead = match(lookahead, TokenType.COMMA);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		terminal(SEMICOLON)
		terminal(RBRACE)
	) */
	private int matchEnumDecl_lookahead1_2(int lookahead) {
		int newLookahead;
		newLookahead = match(lookahead, TokenType.SEMICOLON);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = match(lookahead, TokenType.RBRACE);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		action({ run(); })
		nonTerminal(modifiers, Modifiers)
		nonTerminal(name, Name)
		zeroOrOne(
			nonTerminal(args, Arguments)
		)
		zeroOrOne(
			nonTerminal(classBody, ClassOrInterfaceBody)
		)
		action({ return dress(SEnumConstantDecl.make(modifiers, name, optionOf(args), optionOf(classBody))); })
	) */
	protected BUTree<SEnumConstantDecl> parseEnumConstantDecl() throws ParseException {
		BUTree<SNodeList> modifiers = null;
		BUTree<SName> name;
		BUTree<SNodeList> args = null;
		BUTree<SNodeList> classBody = null;
		run();
		modifiers = parseModifiers();
		name = parseName();
		if (match(0, TokenType.LPAREN) != -1) {
			args = parseArguments();
		}
		if (match(0, TokenType.LBRACE) != -1) {
			classBody = parseClassOrInterfaceBody(TypeKind.Class);
		}
		return dress(SEnumConstantDecl.make(modifiers, name, optionOf(args), optionOf(classBody)));
	}

	/* sequence(
		nonTerminal(modifiers, Modifiers)
		nonTerminal(name, Name)
		zeroOrOne(
			nonTerminal(args, Arguments)
		)
		zeroOrOne(
			nonTerminal(classBody, ClassOrInterfaceBody)
		)
	) */
	private int matchEnumConstantDecl(int lookahead) {
		lookahead = matchModifiers(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchEnumConstantDecl_4(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchEnumConstantDecl_5(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		nonTerminal(args, Arguments)
	) */
	private int matchEnumConstantDecl_4(int lookahead) {
		int newLookahead;
		newLookahead = matchEnumConstantDecl_4_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		nonTerminal(args, Arguments)
	) */
	private int matchEnumConstantDecl_4_1(int lookahead) {
		lookahead = matchArguments(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		nonTerminal(classBody, ClassOrInterfaceBody)
	) */
	private int matchEnumConstantDecl_5(int lookahead) {
		int newLookahead;
		newLookahead = matchEnumConstantDecl_5_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		nonTerminal(classBody, ClassOrInterfaceBody)
	) */
	private int matchEnumConstantDecl_5_1(int lookahead) {
		lookahead = matchClassOrInterfaceBody(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(AT)
		terminal(INTERFACE)
		nonTerminal(name, Name)
		nonTerminal(members, AnnotationTypeBody)
		action({ return dress(SAnnotationDecl.make(modifiers, name, members)); })
	) */
	protected BUTree<SAnnotationDecl> parseAnnotationTypeDecl(BUTree<SNodeList> modifiers) throws ParseException {
		BUTree<SName> name;
		BUTree<SNodeList> members;
		parse(TokenType.AT);
		parse(TokenType.INTERFACE);
		name = parseName();
		members = parseAnnotationTypeBody();
		return dress(SAnnotationDecl.make(modifiers, name, members));
	}

	/* sequence(
		terminal(AT)
		terminal(INTERFACE)
		nonTerminal(name, Name)
		nonTerminal(members, AnnotationTypeBody)
	) */
	private int matchAnnotationTypeDecl(int lookahead) {
		lookahead = match(lookahead, TokenType.AT);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.INTERFACE);
		if (lookahead == -1)
			return -1;
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchAnnotationTypeBody(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(LBRACE)
		zeroOrOne(
			choice(
				oneOrMore(
					nonTerminal(member, AnnotationTypeBodyDecl)
					action({ ret = append(ret, member); })
				)
				sequence(
					lookAhead({ quotesMode })
					nonTerminal(ret, NodeListVar)
				)
			)
		)
		terminal(RBRACE)
		action({ return ret; })
	) */
	protected BUTree<SNodeList> parseAnnotationTypeBody() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<? extends SMemberDecl> member;
		parse(TokenType.LBRACE);
		if (match(0, TokenType.SYNCHRONIZED, TokenType.VOLATILE, TokenType.STRICTFP, TokenType.NATIVE, TokenType.AT, TokenType.PROTECTED, TokenType.PUBLIC, TokenType.ABSTRACT, TokenType.PRIVATE, TokenType.STATIC, TokenType.DEFAULT, TokenType.TRANSIENT, TokenType.FINAL, TokenType.DOUBLE, TokenType.LONG, TokenType.FLOAT, TokenType.BOOLEAN, TokenType.SHORT, TokenType.INT, TokenType.CHAR, TokenType.BYTE, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER, TokenType.ENUM, TokenType.CLASS, TokenType.INTERFACE, TokenType.SEMICOLON, TokenType.NODE_LIST_VARIABLE) != -1) {
			if (match(0, TokenType.PROTECTED, TokenType.PUBLIC, TokenType.ABSTRACT, TokenType.PRIVATE, TokenType.STATIC, TokenType.DEFAULT, TokenType.TRANSIENT, TokenType.FINAL, TokenType.SYNCHRONIZED, TokenType.VOLATILE, TokenType.STRICTFP, TokenType.NATIVE, TokenType.AT, TokenType.ENUM, TokenType.INTERFACE, TokenType.CLASS, TokenType.IDENTIFIER, TokenType.NODE_VARIABLE, TokenType.LONG, TokenType.INT, TokenType.DOUBLE, TokenType.FLOAT, TokenType.CHAR, TokenType.BOOLEAN, TokenType.SHORT, TokenType.BYTE, TokenType.SEMICOLON) != -1) {
				do {
					member = parseAnnotationTypeBodyDecl();
					ret = append(ret, member);
				} while (match(0, TokenType.PROTECTED, TokenType.PUBLIC, TokenType.ABSTRACT, TokenType.PRIVATE, TokenType.STATIC, TokenType.DEFAULT, TokenType.TRANSIENT, TokenType.FINAL, TokenType.SYNCHRONIZED, TokenType.VOLATILE, TokenType.STRICTFP, TokenType.NATIVE, TokenType.AT, TokenType.ENUM, TokenType.INTERFACE, TokenType.CLASS, TokenType.IDENTIFIER, TokenType.NODE_VARIABLE, TokenType.LONG, TokenType.INT, TokenType.DOUBLE, TokenType.FLOAT, TokenType.CHAR, TokenType.BOOLEAN, TokenType.SHORT, TokenType.BYTE, TokenType.SEMICOLON) != -1);
			} else if (quotesMode) {
				ret = parseNodeListVar();
			} else {
				throw produceParseException(TokenType.NODE_LIST_VARIABLE, TokenType.PUBLIC, TokenType.PROTECTED, TokenType.AT, TokenType.NATIVE, TokenType.STRICTFP, TokenType.VOLATILE, TokenType.SYNCHRONIZED, TokenType.FINAL, TokenType.TRANSIENT, TokenType.DEFAULT, TokenType.STATIC, TokenType.PRIVATE, TokenType.ABSTRACT, TokenType.DOUBLE, TokenType.FLOAT, TokenType.CHAR, TokenType.BOOLEAN, TokenType.LONG, TokenType.INT, TokenType.SHORT, TokenType.BYTE, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER, TokenType.CLASS, TokenType.INTERFACE, TokenType.ENUM, TokenType.SEMICOLON);
			}
		}
		parse(TokenType.RBRACE);
		return ret;
	}

	/* sequence(
		terminal(LBRACE)
		zeroOrOne(
			choice(
				oneOrMore(
					nonTerminal(member, AnnotationTypeBodyDecl)
				)
				sequence(
					lookAhead({ quotesMode })
					nonTerminal(ret, NodeListVar)
				)
			)
		)
		terminal(RBRACE)
	) */
	private int matchAnnotationTypeBody(int lookahead) {
		lookahead = match(lookahead, TokenType.LBRACE);
		if (lookahead == -1)
			return -1;
		lookahead = matchAnnotationTypeBody_2(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.RBRACE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		choice(
			oneOrMore(
				nonTerminal(member, AnnotationTypeBodyDecl)
			)
			sequence(
				lookAhead({ quotesMode })
				nonTerminal(ret, NodeListVar)
			)
		)
	) */
	private int matchAnnotationTypeBody_2(int lookahead) {
		int newLookahead;
		newLookahead = matchAnnotationTypeBody_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		choice(
			oneOrMore(
				nonTerminal(member, AnnotationTypeBodyDecl)
			)
			sequence(
				lookAhead({ quotesMode })
				nonTerminal(ret, NodeListVar)
			)
		)
	) */
	private int matchAnnotationTypeBody_2_1(int lookahead) {
		lookahead = matchAnnotationTypeBody_2_1_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		oneOrMore(
			nonTerminal(member, AnnotationTypeBodyDecl)
		)
		sequence(
			lookAhead({ quotesMode })
			nonTerminal(ret, NodeListVar)
		)
	) */
	private int matchAnnotationTypeBody_2_1_1(int lookahead) {
		int newLookahead;
		newLookahead = matchAnnotationTypeBody_2_1_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchAnnotationTypeBody_2_1_1_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* oneOrMore(
		nonTerminal(member, AnnotationTypeBodyDecl)
	) */
	private int matchAnnotationTypeBody_2_1_1_1(int lookahead) {
		int newLookahead;
		newLookahead = matchAnnotationTypeBody_2_1_1_1_1(lookahead);
		if (newLookahead == -1)
			return -1;
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchAnnotationTypeBody_2_1_1_1_1(lookahead);
		}
		return lookahead;
	}

	/* sequence(
		nonTerminal(member, AnnotationTypeBodyDecl)
	) */
	private int matchAnnotationTypeBody_2_1_1_1_1(int lookahead) {
		lookahead = matchAnnotationTypeBodyDecl(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead({ quotesMode })
		nonTerminal(ret, NodeListVar)
	) */
	private int matchAnnotationTypeBody_2_1_1_2(int lookahead) {
		lookahead = quotesMode ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = matchNodeListVar(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		action({ run(); })
		choice(
			sequence(
				terminal(SEMICOLON)
				action({ ret = dress(SEmptyTypeDecl.make()); })
			)
			sequence(
				nonTerminal(modifiers, Modifiers)
				choice(
					sequence(
						lookAhead(
							nonTerminal(Type)
							nonTerminal(Name)
							terminal(LPAREN)
						)
						nonTerminal(ret, AnnotationTypeMemberDecl)
					)
					nonTerminal(ret, ClassOrInterfaceDecl)
					nonTerminal(ret, EnumDecl)
					nonTerminal(ret, AnnotationTypeDecl)
					nonTerminal(ret, FieldDecl)
				)
			)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SMemberDecl> parseAnnotationTypeBodyDecl() throws ParseException {
		BUTree<SNodeList> modifiers;
		BUTree<? extends SMemberDecl> ret;
		run();
		if (match(0, TokenType.SEMICOLON) != -1) {
			parse(TokenType.SEMICOLON);
			ret = dress(SEmptyTypeDecl.make());
		} else if (match(0, TokenType.PROTECTED, TokenType.PUBLIC, TokenType.ABSTRACT, TokenType.PRIVATE, TokenType.STATIC, TokenType.DEFAULT, TokenType.TRANSIENT, TokenType.FINAL, TokenType.SYNCHRONIZED, TokenType.VOLATILE, TokenType.STRICTFP, TokenType.NATIVE, TokenType.AT, TokenType.INTERFACE, TokenType.CLASS, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER, TokenType.CHAR, TokenType.BOOLEAN, TokenType.SHORT, TokenType.BYTE, TokenType.LONG, TokenType.INT, TokenType.DOUBLE, TokenType.FLOAT, TokenType.ENUM) != -1) {
			modifiers = parseModifiers();
			if (matchAnnotationTypeBodyDecl_lookahead1(0) != -1) {
				ret = parseAnnotationTypeMemberDecl(modifiers);
			} else if (match(0, TokenType.CLASS, TokenType.INTERFACE) != -1) {
				ret = parseClassOrInterfaceDecl(modifiers);
			} else if (match(0, TokenType.ENUM) != -1) {
				ret = parseEnumDecl(modifiers);
			} else if (match(0, TokenType.AT) != -1) {
				ret = parseAnnotationTypeDecl(modifiers);
			} else if (match(0, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER, TokenType.CHAR, TokenType.BOOLEAN, TokenType.SHORT, TokenType.BYTE, TokenType.LONG, TokenType.INT, TokenType.DOUBLE, TokenType.FLOAT) != -1) {
				ret = parseFieldDecl(modifiers);
			} else {
				throw produceParseException(TokenType.SHORT, TokenType.BYTE, TokenType.CHAR, TokenType.BOOLEAN, TokenType.DOUBLE, TokenType.FLOAT, TokenType.LONG, TokenType.INT, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER, TokenType.AT, TokenType.ENUM, TokenType.INTERFACE, TokenType.CLASS);
			}
		} else {
			throw produceParseException(TokenType.VOLATILE, TokenType.TRANSIENT, TokenType.NATIVE, TokenType.SYNCHRONIZED, TokenType.AT, TokenType.STRICTFP, TokenType.PUBLIC, TokenType.PRIVATE, TokenType.PROTECTED, TokenType.DEFAULT, TokenType.ABSTRACT, TokenType.FINAL, TokenType.STATIC, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER, TokenType.CHAR, TokenType.BOOLEAN, TokenType.SHORT, TokenType.BYTE, TokenType.LONG, TokenType.INT, TokenType.DOUBLE, TokenType.FLOAT, TokenType.ENUM, TokenType.INTERFACE, TokenType.CLASS, TokenType.SEMICOLON);
		}
		return ret;
	}

	/* sequence(
		choice(
			sequence(
				terminal(SEMICOLON)
			)
			sequence(
				nonTerminal(modifiers, Modifiers)
				choice(
					sequence(
						lookAhead(
							nonTerminal(Type)
							nonTerminal(Name)
							terminal(LPAREN)
						)
						nonTerminal(ret, AnnotationTypeMemberDecl)
					)
					nonTerminal(ret, ClassOrInterfaceDecl)
					nonTerminal(ret, EnumDecl)
					nonTerminal(ret, AnnotationTypeDecl)
					nonTerminal(ret, FieldDecl)
				)
			)
		)
	) */
	private int matchAnnotationTypeBodyDecl(int lookahead) {
		lookahead = matchAnnotationTypeBodyDecl_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			terminal(SEMICOLON)
		)
		sequence(
			nonTerminal(modifiers, Modifiers)
			choice(
				sequence(
					lookAhead(
						nonTerminal(Type)
						nonTerminal(Name)
						terminal(LPAREN)
					)
					nonTerminal(ret, AnnotationTypeMemberDecl)
				)
				nonTerminal(ret, ClassOrInterfaceDecl)
				nonTerminal(ret, EnumDecl)
				nonTerminal(ret, AnnotationTypeDecl)
				nonTerminal(ret, FieldDecl)
			)
		)
	) */
	private int matchAnnotationTypeBodyDecl_2(int lookahead) {
		int newLookahead;
		newLookahead = matchAnnotationTypeBodyDecl_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchAnnotationTypeBodyDecl_2_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		terminal(SEMICOLON)
	) */
	private int matchAnnotationTypeBodyDecl_2_1(int lookahead) {
		lookahead = match(lookahead, TokenType.SEMICOLON);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(modifiers, Modifiers)
		choice(
			sequence(
				lookAhead(
					nonTerminal(Type)
					nonTerminal(Name)
					terminal(LPAREN)
				)
				nonTerminal(ret, AnnotationTypeMemberDecl)
			)
			nonTerminal(ret, ClassOrInterfaceDecl)
			nonTerminal(ret, EnumDecl)
			nonTerminal(ret, AnnotationTypeDecl)
			nonTerminal(ret, FieldDecl)
		)
	) */
	private int matchAnnotationTypeBodyDecl_2_2(int lookahead) {
		lookahead = matchModifiers(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchAnnotationTypeBodyDecl_2_2_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			lookAhead(
				nonTerminal(Type)
				nonTerminal(Name)
				terminal(LPAREN)
			)
			nonTerminal(ret, AnnotationTypeMemberDecl)
		)
		nonTerminal(ret, ClassOrInterfaceDecl)
		nonTerminal(ret, EnumDecl)
		nonTerminal(ret, AnnotationTypeDecl)
		nonTerminal(ret, FieldDecl)
	) */
	private int matchAnnotationTypeBodyDecl_2_2_2(int lookahead) {
		int newLookahead;
		newLookahead = matchAnnotationTypeBodyDecl_2_2_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchClassOrInterfaceDecl(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchEnumDecl(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchAnnotationTypeDecl(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchFieldDecl(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		lookAhead(
			nonTerminal(Type)
			nonTerminal(Name)
			terminal(LPAREN)
		)
		nonTerminal(ret, AnnotationTypeMemberDecl)
	) */
	private int matchAnnotationTypeBodyDecl_2_2_2_1(int lookahead) {
		lookahead = matchAnnotationTypeMemberDecl(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(Type)
		nonTerminal(Name)
		terminal(LPAREN)
	) */
	private int matchAnnotationTypeBodyDecl_lookahead1(int lookahead) {
		lookahead = matchType(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.LPAREN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(type, Type)
		nonTerminal(name, Name)
		terminal(LPAREN)
		terminal(RPAREN)
		nonTerminal(dims, ArrayDims)
		zeroOrOne(
			terminal(DEFAULT)
			nonTerminal(val, MemberValue)
			action({ defaultVal = optionOf(val); })
		)
		terminal(SEMICOLON)
		action({ return dress(SAnnotationMemberDecl.make(modifiers, type, name, dims, defaultVal)); })
	) */
	protected BUTree<SAnnotationMemberDecl> parseAnnotationTypeMemberDecl(BUTree<SNodeList> modifiers) throws ParseException {
		BUTree<? extends SType> type;
		BUTree<SName> name;
		BUTree<SNodeList> dims;
		BUTree<SNodeOption> defaultVal = none();
		BUTree<? extends SExpr> val = null;
		type = parseType(null);
		name = parseName();
		parse(TokenType.LPAREN);
		parse(TokenType.RPAREN);
		dims = parseArrayDims();
		if (match(0, TokenType.DEFAULT) != -1) {
			parse(TokenType.DEFAULT);
			val = parseMemberValue();
			defaultVal = optionOf(val);
		}
		parse(TokenType.SEMICOLON);
		return dress(SAnnotationMemberDecl.make(modifiers, type, name, dims, defaultVal));
	}

	/* sequence(
		nonTerminal(type, Type)
		nonTerminal(name, Name)
		terminal(LPAREN)
		terminal(RPAREN)
		nonTerminal(dims, ArrayDims)
		zeroOrOne(
			terminal(DEFAULT)
			nonTerminal(val, MemberValue)
		)
		terminal(SEMICOLON)
	) */
	private int matchAnnotationTypeMemberDecl(int lookahead) {
		lookahead = matchType(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchArrayDims(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchAnnotationTypeMemberDecl_6(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.SEMICOLON);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		terminal(DEFAULT)
		nonTerminal(val, MemberValue)
	) */
	private int matchAnnotationTypeMemberDecl_6(int lookahead) {
		int newLookahead;
		newLookahead = matchAnnotationTypeMemberDecl_6_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		terminal(DEFAULT)
		nonTerminal(val, MemberValue)
	) */
	private int matchAnnotationTypeMemberDecl_6_1(int lookahead) {
		lookahead = match(lookahead, TokenType.DEFAULT);
		if (lookahead == -1)
			return -1;
		lookahead = matchMemberValue(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(LT)
		choice(
			sequence(
				nonTerminal(tp, TypeParameter)
				action({ ret = append(ret, tp); })
				zeroOrMore(
					terminal(COMMA)
					nonTerminal(tp, TypeParameter)
					action({ ret = append(ret, tp); })
				)
			)
			sequence(
				lookAhead({ quotesMode })
				nonTerminal(ret, NodeListVar)
			)
		)
		terminal(GT)
		action({ return ret; })
	) */
	protected BUTree<SNodeList> parseTypeParameters() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<STypeParameter> tp;
		parse(TokenType.LT);
		if (match(0, TokenType.AT, TokenType.IDENTIFIER, TokenType.NODE_VARIABLE) != -1) {
			tp = parseTypeParameter();
			ret = append(ret, tp);
			while (match(0, TokenType.COMMA) != -1) {
				parse(TokenType.COMMA);
				tp = parseTypeParameter();
				ret = append(ret, tp);
			}
		} else if (quotesMode) {
			ret = parseNodeListVar();
		} else {
			throw produceParseException(TokenType.NODE_LIST_VARIABLE, TokenType.AT, TokenType.IDENTIFIER, TokenType.NODE_VARIABLE);
		}
		parse(TokenType.GT);
		return ret;
	}

	/* sequence(
		terminal(LT)
		choice(
			sequence(
				nonTerminal(tp, TypeParameter)
				zeroOrMore(
					terminal(COMMA)
					nonTerminal(tp, TypeParameter)
				)
			)
			sequence(
				lookAhead({ quotesMode })
				nonTerminal(ret, NodeListVar)
			)
		)
		terminal(GT)
	) */
	private int matchTypeParameters(int lookahead) {
		lookahead = match(lookahead, TokenType.LT);
		if (lookahead == -1)
			return -1;
		lookahead = matchTypeParameters_2(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.GT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			nonTerminal(tp, TypeParameter)
			zeroOrMore(
				terminal(COMMA)
				nonTerminal(tp, TypeParameter)
			)
		)
		sequence(
			lookAhead({ quotesMode })
			nonTerminal(ret, NodeListVar)
		)
	) */
	private int matchTypeParameters_2(int lookahead) {
		int newLookahead;
		newLookahead = matchTypeParameters_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchTypeParameters_2_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		nonTerminal(tp, TypeParameter)
		zeroOrMore(
			terminal(COMMA)
			nonTerminal(tp, TypeParameter)
		)
	) */
	private int matchTypeParameters_2_1(int lookahead) {
		lookahead = matchTypeParameter(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchTypeParameters_2_1_3(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		terminal(COMMA)
		nonTerminal(tp, TypeParameter)
	) */
	private int matchTypeParameters_2_1_3(int lookahead) {
		int newLookahead;
		newLookahead = matchTypeParameters_2_1_3_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchTypeParameters_2_1_3_1(lookahead);
		}
		return lookahead;
	}

	/* sequence(
		terminal(COMMA)
		nonTerminal(tp, TypeParameter)
	) */
	private int matchTypeParameters_2_1_3_1(int lookahead) {
		lookahead = match(lookahead, TokenType.COMMA);
		if (lookahead == -1)
			return -1;
		lookahead = matchTypeParameter(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead({ quotesMode })
		nonTerminal(ret, NodeListVar)
	) */
	private int matchTypeParameters_2_2(int lookahead) {
		lookahead = quotesMode ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = matchNodeListVar(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		action({ run(); })
		nonTerminal(annotations, Annotations)
		nonTerminal(name, Name)
		zeroOrOne(
			nonTerminal(typeBounds, TypeBounds)
		)
		action({ return dress(STypeParameter.make(annotations, name, ensureNotNull(typeBounds))); })
	) */
	protected BUTree<STypeParameter> parseTypeParameter() throws ParseException {
		BUTree<SNodeList> annotations = null;
		BUTree<SName> name;
		BUTree<SNodeList> typeBounds = null;
		run();
		annotations = parseAnnotations();
		name = parseName();
		if (match(0, TokenType.EXTENDS) != -1) {
			typeBounds = parseTypeBounds();
		}
		return dress(STypeParameter.make(annotations, name, ensureNotNull(typeBounds)));
	}

	/* sequence(
		nonTerminal(annotations, Annotations)
		nonTerminal(name, Name)
		zeroOrOne(
			nonTerminal(typeBounds, TypeBounds)
		)
	) */
	private int matchTypeParameter(int lookahead) {
		lookahead = matchAnnotations(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchTypeParameter_4(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		nonTerminal(typeBounds, TypeBounds)
	) */
	private int matchTypeParameter_4(int lookahead) {
		int newLookahead;
		newLookahead = matchTypeParameter_4_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		nonTerminal(typeBounds, TypeBounds)
	) */
	private int matchTypeParameter_4_1(int lookahead) {
		lookahead = matchTypeBounds(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(EXTENDS)
		choice(
			sequence(
				nonTerminal(cit, AnnotatedQualifiedType)
				action({ ret = append(ret, cit); })
				zeroOrMore(
					terminal(BIT_AND)
					nonTerminal(cit, AnnotatedQualifiedType)
					action({ ret = append(ret, cit); })
				)
			)
			sequence(
				lookAhead({ quotesMode })
				nonTerminal(ret, NodeListVar)
			)
		)
		action({ return ret; })
	) */
	protected BUTree<SNodeList> parseTypeBounds() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<SQualifiedType> cit;
		BUTree<SNodeList> annotations = null;
		parse(TokenType.EXTENDS);
		if (match(0, TokenType.AT, TokenType.IDENTIFIER, TokenType.NODE_VARIABLE) != -1) {
			cit = parseAnnotatedQualifiedType();
			ret = append(ret, cit);
			while (match(0, TokenType.BIT_AND) != -1) {
				parse(TokenType.BIT_AND);
				cit = parseAnnotatedQualifiedType();
				ret = append(ret, cit);
			}
		} else if (quotesMode) {
			ret = parseNodeListVar();
		} else {
			throw produceParseException(TokenType.NODE_LIST_VARIABLE, TokenType.AT, TokenType.IDENTIFIER, TokenType.NODE_VARIABLE);
		}
		return ret;
	}

	/* sequence(
		terminal(EXTENDS)
		choice(
			sequence(
				nonTerminal(cit, AnnotatedQualifiedType)
				zeroOrMore(
					terminal(BIT_AND)
					nonTerminal(cit, AnnotatedQualifiedType)
				)
			)
			sequence(
				lookAhead({ quotesMode })
				nonTerminal(ret, NodeListVar)
			)
		)
	) */
	private int matchTypeBounds(int lookahead) {
		lookahead = match(lookahead, TokenType.EXTENDS);
		if (lookahead == -1)
			return -1;
		lookahead = matchTypeBounds_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			nonTerminal(cit, AnnotatedQualifiedType)
			zeroOrMore(
				terminal(BIT_AND)
				nonTerminal(cit, AnnotatedQualifiedType)
			)
		)
		sequence(
			lookAhead({ quotesMode })
			nonTerminal(ret, NodeListVar)
		)
	) */
	private int matchTypeBounds_2(int lookahead) {
		int newLookahead;
		newLookahead = matchTypeBounds_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchTypeBounds_2_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		nonTerminal(cit, AnnotatedQualifiedType)
		zeroOrMore(
			terminal(BIT_AND)
			nonTerminal(cit, AnnotatedQualifiedType)
		)
	) */
	private int matchTypeBounds_2_1(int lookahead) {
		lookahead = matchAnnotatedQualifiedType(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchTypeBounds_2_1_3(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		terminal(BIT_AND)
		nonTerminal(cit, AnnotatedQualifiedType)
	) */
	private int matchTypeBounds_2_1_3(int lookahead) {
		int newLookahead;
		newLookahead = matchTypeBounds_2_1_3_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchTypeBounds_2_1_3_1(lookahead);
		}
		return lookahead;
	}

	/* sequence(
		terminal(BIT_AND)
		nonTerminal(cit, AnnotatedQualifiedType)
	) */
	private int matchTypeBounds_2_1_3_1(int lookahead) {
		lookahead = match(lookahead, TokenType.BIT_AND);
		if (lookahead == -1)
			return -1;
		lookahead = matchAnnotatedQualifiedType(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead({ quotesMode })
		nonTerminal(ret, NodeListVar)
	) */
	private int matchTypeBounds_2_2(int lookahead) {
		lookahead = quotesMode ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = matchNodeListVar(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(LBRACE)
		nonTerminal(ret, ClassOrInterfaceBodyDecls)
		terminal(RBRACE)
		action({ return ret; })
	) */
	protected BUTree<SNodeList> parseClassOrInterfaceBody(TypeKind typeKind) throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<? extends SMemberDecl> member;
		parse(TokenType.LBRACE);
		ret = parseClassOrInterfaceBodyDecls(typeKind);
		parse(TokenType.RBRACE);
		return ret;
	}

	/* sequence(
		terminal(LBRACE)
		nonTerminal(ret, ClassOrInterfaceBodyDecls)
		terminal(RBRACE)
	) */
	private int matchClassOrInterfaceBody(int lookahead) {
		lookahead = match(lookahead, TokenType.LBRACE);
		if (lookahead == -1)
			return -1;
		lookahead = matchClassOrInterfaceBodyDecls(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.RBRACE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		zeroOrOne(
			choice(
				oneOrMore(
					nonTerminal(member, ClassOrInterfaceBodyDecl)
					action({ ret = append(ret, member); })
				)
				sequence(
					lookAhead({ quotesMode })
					nonTerminal(ret, NodeListVar)
				)
			)
		)
		action({ return ret; })
	) */
	protected BUTree<SNodeList> parseClassOrInterfaceBodyDecls(TypeKind typeKind) throws ParseException {
		BUTree<? extends SMemberDecl> member;
		BUTree<SNodeList> ret = emptyList();
		if (match(0, TokenType.SYNCHRONIZED, TokenType.VOLATILE, TokenType.STRICTFP, TokenType.NATIVE, TokenType.AT, TokenType.PROTECTED, TokenType.PUBLIC, TokenType.ABSTRACT, TokenType.PRIVATE, TokenType.STATIC, TokenType.DEFAULT, TokenType.TRANSIENT, TokenType.FINAL, TokenType.LBRACE, TokenType.LT, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER, TokenType.LONG, TokenType.INT, TokenType.SHORT, TokenType.BYTE, TokenType.CHAR, TokenType.BOOLEAN, TokenType.DOUBLE, TokenType.FLOAT, TokenType.VOID, TokenType.ENUM, TokenType.CLASS, TokenType.INTERFACE, TokenType.SEMICOLON, TokenType.NODE_LIST_VARIABLE) != -1) {
			if (match(0, TokenType.PROTECTED, TokenType.PUBLIC, TokenType.ABSTRACT, TokenType.PRIVATE, TokenType.STATIC, TokenType.DEFAULT, TokenType.TRANSIENT, TokenType.FINAL, TokenType.SYNCHRONIZED, TokenType.VOLATILE, TokenType.STRICTFP, TokenType.NATIVE, TokenType.AT, TokenType.LBRACE, TokenType.LT, TokenType.IDENTIFIER, TokenType.NODE_VARIABLE, TokenType.ENUM, TokenType.CLASS, TokenType.INTERFACE, TokenType.FLOAT, TokenType.DOUBLE, TokenType.BYTE, TokenType.SHORT, TokenType.INT, TokenType.LONG, TokenType.BOOLEAN, TokenType.CHAR, TokenType.VOID, TokenType.SEMICOLON) != -1) {
				do {
					member = parseClassOrInterfaceBodyDecl(typeKind);
					ret = append(ret, member);
				} while (match(0, TokenType.PROTECTED, TokenType.PUBLIC, TokenType.ABSTRACT, TokenType.PRIVATE, TokenType.STATIC, TokenType.DEFAULT, TokenType.TRANSIENT, TokenType.FINAL, TokenType.SYNCHRONIZED, TokenType.VOLATILE, TokenType.STRICTFP, TokenType.NATIVE, TokenType.AT, TokenType.LBRACE, TokenType.LT, TokenType.IDENTIFIER, TokenType.NODE_VARIABLE, TokenType.ENUM, TokenType.CLASS, TokenType.INTERFACE, TokenType.FLOAT, TokenType.DOUBLE, TokenType.BYTE, TokenType.SHORT, TokenType.INT, TokenType.LONG, TokenType.BOOLEAN, TokenType.CHAR, TokenType.VOID, TokenType.SEMICOLON) != -1);
			} else if (quotesMode) {
				ret = parseNodeListVar();
			} else {
				throw produceParseException(TokenType.NODE_LIST_VARIABLE, TokenType.PUBLIC, TokenType.PROTECTED, TokenType.AT, TokenType.NATIVE, TokenType.STRICTFP, TokenType.VOLATILE, TokenType.SYNCHRONIZED, TokenType.FINAL, TokenType.TRANSIENT, TokenType.DEFAULT, TokenType.STATIC, TokenType.PRIVATE, TokenType.ABSTRACT, TokenType.INTERFACE, TokenType.CLASS, TokenType.ENUM, TokenType.LBRACE, TokenType.IDENTIFIER, TokenType.NODE_VARIABLE, TokenType.DOUBLE, TokenType.FLOAT, TokenType.SHORT, TokenType.BYTE, TokenType.LONG, TokenType.INT, TokenType.CHAR, TokenType.BOOLEAN, TokenType.LT, TokenType.VOID, TokenType.SEMICOLON);
			}
		}
		return ret;
	}

	/* sequence(
		zeroOrOne(
			choice(
				oneOrMore(
					nonTerminal(member, ClassOrInterfaceBodyDecl)
				)
				sequence(
					lookAhead({ quotesMode })
					nonTerminal(ret, NodeListVar)
				)
			)
		)
	) */
	private int matchClassOrInterfaceBodyDecls(int lookahead) {
		lookahead = matchClassOrInterfaceBodyDecls_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		choice(
			oneOrMore(
				nonTerminal(member, ClassOrInterfaceBodyDecl)
			)
			sequence(
				lookAhead({ quotesMode })
				nonTerminal(ret, NodeListVar)
			)
		)
	) */
	private int matchClassOrInterfaceBodyDecls_1(int lookahead) {
		int newLookahead;
		newLookahead = matchClassOrInterfaceBodyDecls_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		choice(
			oneOrMore(
				nonTerminal(member, ClassOrInterfaceBodyDecl)
			)
			sequence(
				lookAhead({ quotesMode })
				nonTerminal(ret, NodeListVar)
			)
		)
	) */
	private int matchClassOrInterfaceBodyDecls_1_1(int lookahead) {
		lookahead = matchClassOrInterfaceBodyDecls_1_1_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		oneOrMore(
			nonTerminal(member, ClassOrInterfaceBodyDecl)
		)
		sequence(
			lookAhead({ quotesMode })
			nonTerminal(ret, NodeListVar)
		)
	) */
	private int matchClassOrInterfaceBodyDecls_1_1_1(int lookahead) {
		int newLookahead;
		newLookahead = matchClassOrInterfaceBodyDecls_1_1_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchClassOrInterfaceBodyDecls_1_1_1_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* oneOrMore(
		nonTerminal(member, ClassOrInterfaceBodyDecl)
	) */
	private int matchClassOrInterfaceBodyDecls_1_1_1_1(int lookahead) {
		int newLookahead;
		newLookahead = matchClassOrInterfaceBodyDecls_1_1_1_1_1(lookahead);
		if (newLookahead == -1)
			return -1;
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchClassOrInterfaceBodyDecls_1_1_1_1_1(lookahead);
		}
		return lookahead;
	}

	/* sequence(
		nonTerminal(member, ClassOrInterfaceBodyDecl)
	) */
	private int matchClassOrInterfaceBodyDecls_1_1_1_1_1(int lookahead) {
		lookahead = matchClassOrInterfaceBodyDecl(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead({ quotesMode })
		nonTerminal(ret, NodeListVar)
	) */
	private int matchClassOrInterfaceBodyDecls_1_1_1_2(int lookahead) {
		lookahead = quotesMode ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = matchNodeListVar(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		action({ run(); })
		choice(
			sequence(
				terminal(SEMICOLON)
				action({ ret = dress(SEmptyMemberDecl.make()); })
			)
			sequence(
				nonTerminal(modifiers, Modifiers)
				action({
					if (modifiers != null && contains(modifiers, SModifier.make(ModifierKeyword.Default)) && typeKind != TypeKind.Interface) problem = new BUProblem(Severity.ERROR, "Only interfaces can have default members");
				})
				choice(
					sequence(
						nonTerminal(ret, InitializerDecl)
						action({
							if (typeKind == TypeKind.Interface) ret = ret.withProblem(new BUProblem(Severity.ERROR, "An interface cannot have initializers"));
						})
					)
					nonTerminal(ret, ClassOrInterfaceDecl)
					nonTerminal(ret, EnumDecl)
					nonTerminal(ret, AnnotationTypeDecl)
					sequence(
						lookAhead(
							zeroOrOne(
								nonTerminal(TypeParameters)
							)
							nonTerminal(Name)
							terminal(LPAREN)
						)
						nonTerminal(ret, ConstructorDecl)
						action({
							if (typeKind == TypeKind.Interface) ret = ret.withProblem(new BUProblem(Severity.ERROR, "An interface cannot have constructors"));
						})
					)
					sequence(
						lookAhead(
							nonTerminal(Type)
							nonTerminal(Name)
							zeroOrMore(
								terminal(LBRACKET)
								terminal(RBRACKET)
							)
							choice(
								terminal(COMMA)
								terminal(ASSIGN)
								terminal(SEMICOLON)
							)
						)
						nonTerminal(ret, FieldDecl)
					)
					nonTerminal(ret, MethodDecl)
				)
			)
		)
		action({ return ret.withProblem(problem); })
	) */
	protected BUTree<? extends SMemberDecl> parseClassOrInterfaceBodyDecl(TypeKind typeKind) throws ParseException {
		BUTree<SNodeList> modifiers;
		BUTree<? extends SMemberDecl> ret;
		BUProblem problem = null;
		run();
		if (match(0, TokenType.SEMICOLON) != -1) {
			parse(TokenType.SEMICOLON);
			ret = dress(SEmptyMemberDecl.make());
		} else if (match(0, TokenType.PROTECTED, TokenType.PUBLIC, TokenType.ABSTRACT, TokenType.PRIVATE, TokenType.STATIC, TokenType.DEFAULT, TokenType.TRANSIENT, TokenType.FINAL, TokenType.SYNCHRONIZED, TokenType.VOLATILE, TokenType.STRICTFP, TokenType.NATIVE, TokenType.AT, TokenType.ENUM, TokenType.CLASS, TokenType.INTERFACE, TokenType.LBRACE, TokenType.LT, TokenType.VOID, TokenType.IDENTIFIER, TokenType.NODE_VARIABLE, TokenType.FLOAT, TokenType.DOUBLE, TokenType.INT, TokenType.LONG, TokenType.BYTE, TokenType.SHORT, TokenType.BOOLEAN, TokenType.CHAR) != -1) {
			modifiers = parseModifiers();
			if (modifiers != null && contains(modifiers, SModifier.make(ModifierKeyword.Default)) && typeKind != TypeKind.Interface) problem = new BUProblem(Severity.ERROR, "Only interfaces can have default members");

			if (match(0, TokenType.LBRACE) != -1) {
				ret = parseInitializerDecl(modifiers);
				if (typeKind == TypeKind.Interface) ret = ret.withProblem(new BUProblem(Severity.ERROR, "An interface cannot have initializers"));

			} else if (match(0, TokenType.CLASS, TokenType.INTERFACE) != -1) {
				ret = parseClassOrInterfaceDecl(modifiers);
			} else if (match(0, TokenType.ENUM) != -1) {
				ret = parseEnumDecl(modifiers);
			} else if (match(0, TokenType.AT) != -1) {
				ret = parseAnnotationTypeDecl(modifiers);
			} else if (matchClassOrInterfaceBodyDecl_lookahead1(0) != -1) {
				ret = parseConstructorDecl(modifiers);
				if (typeKind == TypeKind.Interface) ret = ret.withProblem(new BUProblem(Severity.ERROR, "An interface cannot have constructors"));

			} else if (matchClassOrInterfaceBodyDecl_lookahead2(0) != -1) {
				ret = parseFieldDecl(modifiers);
			} else if (match(0, TokenType.LT, TokenType.LONG, TokenType.INT, TokenType.DOUBLE, TokenType.FLOAT, TokenType.CHAR, TokenType.BOOLEAN, TokenType.SHORT, TokenType.BYTE, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER, TokenType.VOID) != -1) {
				ret = parseMethodDecl(modifiers);
			} else {
				throw produceParseException(TokenType.LT, TokenType.VOID, TokenType.IDENTIFIER, TokenType.NODE_VARIABLE, TokenType.SHORT, TokenType.BYTE, TokenType.CHAR, TokenType.BOOLEAN, TokenType.DOUBLE, TokenType.FLOAT, TokenType.LONG, TokenType.INT, TokenType.AT, TokenType.ENUM, TokenType.INTERFACE, TokenType.CLASS, TokenType.LBRACE);
			}
		} else {
			throw produceParseException(TokenType.VOLATILE, TokenType.TRANSIENT, TokenType.NATIVE, TokenType.SYNCHRONIZED, TokenType.AT, TokenType.STRICTFP, TokenType.PUBLIC, TokenType.PRIVATE, TokenType.PROTECTED, TokenType.DEFAULT, TokenType.ABSTRACT, TokenType.FINAL, TokenType.STATIC, TokenType.LT, TokenType.SHORT, TokenType.INT, TokenType.CHAR, TokenType.BYTE, TokenType.BOOLEAN, TokenType.DOUBLE, TokenType.LONG, TokenType.FLOAT, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER, TokenType.VOID, TokenType.ENUM, TokenType.INTERFACE, TokenType.CLASS, TokenType.LBRACE, TokenType.SEMICOLON);
		}
		return ret.withProblem(problem);
	}

	/* sequence(
		choice(
			sequence(
				terminal(SEMICOLON)
			)
			sequence(
				nonTerminal(modifiers, Modifiers)
				choice(
					sequence(
						nonTerminal(ret, InitializerDecl)
					)
					nonTerminal(ret, ClassOrInterfaceDecl)
					nonTerminal(ret, EnumDecl)
					nonTerminal(ret, AnnotationTypeDecl)
					sequence(
						lookAhead(
							zeroOrOne(
								nonTerminal(TypeParameters)
							)
							nonTerminal(Name)
							terminal(LPAREN)
						)
						nonTerminal(ret, ConstructorDecl)
					)
					sequence(
						lookAhead(
							nonTerminal(Type)
							nonTerminal(Name)
							zeroOrMore(
								terminal(LBRACKET)
								terminal(RBRACKET)
							)
							choice(
								terminal(COMMA)
								terminal(ASSIGN)
								terminal(SEMICOLON)
							)
						)
						nonTerminal(ret, FieldDecl)
					)
					nonTerminal(ret, MethodDecl)
				)
			)
		)
	) */
	private int matchClassOrInterfaceBodyDecl(int lookahead) {
		lookahead = matchClassOrInterfaceBodyDecl_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			terminal(SEMICOLON)
		)
		sequence(
			nonTerminal(modifiers, Modifiers)
			choice(
				sequence(
					nonTerminal(ret, InitializerDecl)
				)
				nonTerminal(ret, ClassOrInterfaceDecl)
				nonTerminal(ret, EnumDecl)
				nonTerminal(ret, AnnotationTypeDecl)
				sequence(
					lookAhead(
						zeroOrOne(
							nonTerminal(TypeParameters)
						)
						nonTerminal(Name)
						terminal(LPAREN)
					)
					nonTerminal(ret, ConstructorDecl)
				)
				sequence(
					lookAhead(
						nonTerminal(Type)
						nonTerminal(Name)
						zeroOrMore(
							terminal(LBRACKET)
							terminal(RBRACKET)
						)
						choice(
							terminal(COMMA)
							terminal(ASSIGN)
							terminal(SEMICOLON)
						)
					)
					nonTerminal(ret, FieldDecl)
				)
				nonTerminal(ret, MethodDecl)
			)
		)
	) */
	private int matchClassOrInterfaceBodyDecl_2(int lookahead) {
		int newLookahead;
		newLookahead = matchClassOrInterfaceBodyDecl_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchClassOrInterfaceBodyDecl_2_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		terminal(SEMICOLON)
	) */
	private int matchClassOrInterfaceBodyDecl_2_1(int lookahead) {
		lookahead = match(lookahead, TokenType.SEMICOLON);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(modifiers, Modifiers)
		choice(
			sequence(
				nonTerminal(ret, InitializerDecl)
			)
			nonTerminal(ret, ClassOrInterfaceDecl)
			nonTerminal(ret, EnumDecl)
			nonTerminal(ret, AnnotationTypeDecl)
			sequence(
				lookAhead(
					zeroOrOne(
						nonTerminal(TypeParameters)
					)
					nonTerminal(Name)
					terminal(LPAREN)
				)
				nonTerminal(ret, ConstructorDecl)
			)
			sequence(
				lookAhead(
					nonTerminal(Type)
					nonTerminal(Name)
					zeroOrMore(
						terminal(LBRACKET)
						terminal(RBRACKET)
					)
					choice(
						terminal(COMMA)
						terminal(ASSIGN)
						terminal(SEMICOLON)
					)
				)
				nonTerminal(ret, FieldDecl)
			)
			nonTerminal(ret, MethodDecl)
		)
	) */
	private int matchClassOrInterfaceBodyDecl_2_2(int lookahead) {
		lookahead = matchModifiers(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchClassOrInterfaceBodyDecl_2_2_3(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			nonTerminal(ret, InitializerDecl)
		)
		nonTerminal(ret, ClassOrInterfaceDecl)
		nonTerminal(ret, EnumDecl)
		nonTerminal(ret, AnnotationTypeDecl)
		sequence(
			lookAhead(
				zeroOrOne(
					nonTerminal(TypeParameters)
				)
				nonTerminal(Name)
				terminal(LPAREN)
			)
			nonTerminal(ret, ConstructorDecl)
		)
		sequence(
			lookAhead(
				nonTerminal(Type)
				nonTerminal(Name)
				zeroOrMore(
					terminal(LBRACKET)
					terminal(RBRACKET)
				)
				choice(
					terminal(COMMA)
					terminal(ASSIGN)
					terminal(SEMICOLON)
				)
			)
			nonTerminal(ret, FieldDecl)
		)
		nonTerminal(ret, MethodDecl)
	) */
	private int matchClassOrInterfaceBodyDecl_2_2_3(int lookahead) {
		int newLookahead;
		newLookahead = matchClassOrInterfaceBodyDecl_2_2_3_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchClassOrInterfaceDecl(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchEnumDecl(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchAnnotationTypeDecl(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchClassOrInterfaceBodyDecl_2_2_3_5(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchClassOrInterfaceBodyDecl_2_2_3_6(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchMethodDecl(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		nonTerminal(ret, InitializerDecl)
	) */
	private int matchClassOrInterfaceBodyDecl_2_2_3_1(int lookahead) {
		lookahead = matchInitializerDecl(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead(
			zeroOrOne(
				nonTerminal(TypeParameters)
			)
			nonTerminal(Name)
			terminal(LPAREN)
		)
		nonTerminal(ret, ConstructorDecl)
	) */
	private int matchClassOrInterfaceBodyDecl_2_2_3_5(int lookahead) {
		lookahead = matchConstructorDecl(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead(
			nonTerminal(Type)
			nonTerminal(Name)
			zeroOrMore(
				terminal(LBRACKET)
				terminal(RBRACKET)
			)
			choice(
				terminal(COMMA)
				terminal(ASSIGN)
				terminal(SEMICOLON)
			)
		)
		nonTerminal(ret, FieldDecl)
	) */
	private int matchClassOrInterfaceBodyDecl_2_2_3_6(int lookahead) {
		lookahead = matchFieldDecl(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		zeroOrOne(
			nonTerminal(TypeParameters)
		)
		nonTerminal(Name)
		terminal(LPAREN)
	) */
	private int matchClassOrInterfaceBodyDecl_lookahead1(int lookahead) {
		lookahead = matchClassOrInterfaceBodyDecl_lookahead1_1(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.LPAREN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		nonTerminal(TypeParameters)
	) */
	private int matchClassOrInterfaceBodyDecl_lookahead1_1(int lookahead) {
		int newLookahead;
		newLookahead = matchClassOrInterfaceBodyDecl_lookahead1_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		nonTerminal(TypeParameters)
	) */
	private int matchClassOrInterfaceBodyDecl_lookahead1_1_1(int lookahead) {
		lookahead = matchTypeParameters(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(Type)
		nonTerminal(Name)
		zeroOrMore(
			terminal(LBRACKET)
			terminal(RBRACKET)
		)
		choice(
			terminal(COMMA)
			terminal(ASSIGN)
			terminal(SEMICOLON)
		)
	) */
	private int matchClassOrInterfaceBodyDecl_lookahead2(int lookahead) {
		lookahead = matchType(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchClassOrInterfaceBodyDecl_lookahead2_3(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchClassOrInterfaceBodyDecl_lookahead2_4(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		terminal(LBRACKET)
		terminal(RBRACKET)
	) */
	private int matchClassOrInterfaceBodyDecl_lookahead2_3(int lookahead) {
		int newLookahead;
		newLookahead = matchClassOrInterfaceBodyDecl_lookahead2_3_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchClassOrInterfaceBodyDecl_lookahead2_3_1(lookahead);
		}
		return lookahead;
	}

	/* sequence(
		terminal(LBRACKET)
		terminal(RBRACKET)
	) */
	private int matchClassOrInterfaceBodyDecl_lookahead2_3_1(int lookahead) {
		lookahead = match(lookahead, TokenType.LBRACKET);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.RBRACKET);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		terminal(COMMA)
		terminal(ASSIGN)
		terminal(SEMICOLON)
	) */
	private int matchClassOrInterfaceBodyDecl_lookahead2_4(int lookahead) {
		int newLookahead;
		newLookahead = match(lookahead, TokenType.COMMA);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = match(lookahead, TokenType.ASSIGN);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = match(lookahead, TokenType.SEMICOLON);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		nonTerminal(type, Type)
		nonTerminal(variables, VariableDeclarators)
		terminal(SEMICOLON)
		action({ return dress(SFieldDecl.make(modifiers, type, variables)); })
	) */
	protected BUTree<SFieldDecl> parseFieldDecl(BUTree<SNodeList> modifiers) throws ParseException {
		BUTree<? extends SType> type;
		BUTree<SNodeList> variables = emptyList();
		BUTree<SVariableDeclarator> val;
		type = parseType(null);
		variables = parseVariableDeclarators();
		parse(TokenType.SEMICOLON);
		return dress(SFieldDecl.make(modifiers, type, variables));
	}

	/* sequence(
		nonTerminal(type, Type)
		nonTerminal(variables, VariableDeclarators)
		terminal(SEMICOLON)
	) */
	private int matchFieldDecl(int lookahead) {
		lookahead = matchType(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchVariableDeclarators(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.SEMICOLON);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(type, Type)
		nonTerminal(variables, VariableDeclarators)
		action({ return dress(SLocalVariableDecl.make(modifiers, type, variables)); })
	) */
	protected BUTree<SLocalVariableDecl> parseVariableDecl(BUTree<SNodeList> modifiers) throws ParseException {
		BUTree<? extends SType> type;
		BUTree<SNodeList> variables = emptyList();
		type = parseType(null);
		variables = parseVariableDeclarators();
		return dress(SLocalVariableDecl.make(modifiers, type, variables));
	}

	/* sequence(
		nonTerminal(type, Type)
		nonTerminal(variables, VariableDeclarators)
	) */
	private int matchVariableDecl(int lookahead) {
		lookahead = matchType(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchVariableDeclarators(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(val, VariableDeclarator)
		action({ variables = append(variables, val); })
		zeroOrMore(
			terminal(COMMA)
			nonTerminal(val, VariableDeclarator)
			action({ variables = append(variables, val); })
		)
		action({ return variables; })
	) */
	protected BUTree<SNodeList> parseVariableDeclarators() throws ParseException {
		BUTree<SNodeList> variables = emptyList();
		BUTree<SVariableDeclarator> val;
		val = parseVariableDeclarator();
		variables = append(variables, val);
		while (match(0, TokenType.COMMA) != -1) {
			parse(TokenType.COMMA);
			val = parseVariableDeclarator();
			variables = append(variables, val);
		}
		return variables;
	}

	/* sequence(
		nonTerminal(val, VariableDeclarator)
		zeroOrMore(
			terminal(COMMA)
			nonTerminal(val, VariableDeclarator)
		)
	) */
	private int matchVariableDeclarators(int lookahead) {
		lookahead = matchVariableDeclarator(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchVariableDeclarators_3(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		terminal(COMMA)
		nonTerminal(val, VariableDeclarator)
	) */
	private int matchVariableDeclarators_3(int lookahead) {
		int newLookahead;
		newLookahead = matchVariableDeclarators_3_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchVariableDeclarators_3_1(lookahead);
		}
		return lookahead;
	}

	/* sequence(
		terminal(COMMA)
		nonTerminal(val, VariableDeclarator)
	) */
	private int matchVariableDeclarators_3_1(int lookahead) {
		lookahead = match(lookahead, TokenType.COMMA);
		if (lookahead == -1)
			return -1;
		lookahead = matchVariableDeclarator(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		action({ run(); })
		nonTerminal(id, VariableDeclaratorId)
		zeroOrOne(
			terminal(ASSIGN)
			nonTerminal(initExpr, VariableInitializer)
			action({ init = optionOf(initExpr); })
		)
		action({ return dress(SVariableDeclarator.make(id, init)); })
	) */
	protected BUTree<SVariableDeclarator> parseVariableDeclarator() throws ParseException {
		BUTree<SVariableDeclaratorId> id;
		BUTree<SNodeOption> init = none();
		BUTree<? extends SExpr> initExpr = null;
		run();
		id = parseVariableDeclaratorId();
		if (match(0, TokenType.ASSIGN) != -1) {
			parse(TokenType.ASSIGN);
			initExpr = parseVariableInitializer();
			init = optionOf(initExpr);
		}
		return dress(SVariableDeclarator.make(id, init));
	}

	/* sequence(
		nonTerminal(id, VariableDeclaratorId)
		zeroOrOne(
			terminal(ASSIGN)
			nonTerminal(initExpr, VariableInitializer)
		)
	) */
	private int matchVariableDeclarator(int lookahead) {
		lookahead = matchVariableDeclaratorId(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchVariableDeclarator_3(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		terminal(ASSIGN)
		nonTerminal(initExpr, VariableInitializer)
	) */
	private int matchVariableDeclarator_3(int lookahead) {
		int newLookahead;
		newLookahead = matchVariableDeclarator_3_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		terminal(ASSIGN)
		nonTerminal(initExpr, VariableInitializer)
	) */
	private int matchVariableDeclarator_3_1(int lookahead) {
		lookahead = match(lookahead, TokenType.ASSIGN);
		if (lookahead == -1)
			return -1;
		lookahead = matchVariableInitializer(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		action({ run(); })
		nonTerminal(name, Name)
		nonTerminal(arrayDims, ArrayDims)
		action({ return dress(SVariableDeclaratorId.make(name, arrayDims)); })
	) */
	protected BUTree<SVariableDeclaratorId> parseVariableDeclaratorId() throws ParseException {
		BUTree<SName> name;
		BUTree<SNodeList> arrayDims;
		run();
		name = parseName();
		arrayDims = parseArrayDims();
		return dress(SVariableDeclaratorId.make(name, arrayDims));
	}

	/* sequence(
		nonTerminal(name, Name)
		nonTerminal(arrayDims, ArrayDims)
	) */
	private int matchVariableDeclaratorId(int lookahead) {
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchArrayDims(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		zeroOrMore(
			lookAhead(
				nonTerminal(Annotations)
				terminal(LBRACKET)
				terminal(RBRACKET)
			)
			action({ run(); })
			nonTerminal(annotations, Annotations)
			terminal(LBRACKET)
			terminal(RBRACKET)
			action({ arrayDims = append(arrayDims, dress(SArrayDim.make(annotations))); })
		)
		action({ return arrayDims; })
	) */
	protected BUTree<SNodeList> parseArrayDims() throws ParseException {
		BUTree<SNodeList> arrayDims = emptyList();
		BUTree<SNodeList> annotations;
		while (matchArrayDims_lookahead1(0) != -1) {
			run();
			annotations = parseAnnotations();
			parse(TokenType.LBRACKET);
			parse(TokenType.RBRACKET);
			arrayDims = append(arrayDims, dress(SArrayDim.make(annotations)));
		}
		return arrayDims;
	}

	/* sequence(
		zeroOrMore(
			lookAhead(
				nonTerminal(Annotations)
				terminal(LBRACKET)
				terminal(RBRACKET)
			)
			nonTerminal(annotations, Annotations)
			terminal(LBRACKET)
			terminal(RBRACKET)
		)
	) */
	private int matchArrayDims(int lookahead) {
		lookahead = matchArrayDims_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		lookAhead(
			nonTerminal(Annotations)
			terminal(LBRACKET)
			terminal(RBRACKET)
		)
		nonTerminal(annotations, Annotations)
		terminal(LBRACKET)
		terminal(RBRACKET)
	) */
	private int matchArrayDims_1(int lookahead) {
		int newLookahead;
		newLookahead = matchArrayDims_1_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchArrayDims_1_1(lookahead);
		}
		return lookahead;
	}

	/* sequence(
		lookAhead(
			nonTerminal(Annotations)
			terminal(LBRACKET)
			terminal(RBRACKET)
		)
		nonTerminal(annotations, Annotations)
		terminal(LBRACKET)
		terminal(RBRACKET)
	) */
	private int matchArrayDims_1_1(int lookahead) {
		lookahead = matchAnnotations(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.LBRACKET);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.RBRACKET);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(Annotations)
		terminal(LBRACKET)
		terminal(RBRACKET)
	) */
	private int matchArrayDims_lookahead1(int lookahead) {
		lookahead = matchAnnotations(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.LBRACKET);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.RBRACKET);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		choice(
			nonTerminal(ret, ArrayInitializer)
			nonTerminal(ret, Expression)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parseVariableInitializer() throws ParseException {
		BUTree<? extends SExpr> ret;
		if (match(0, TokenType.LBRACE) != -1) {
			ret = parseArrayInitializer();
		} else if (match(0, TokenType.IDENTIFIER, TokenType.NODE_VARIABLE, TokenType.MINUS, TokenType.PLUS, TokenType.TILDE, TokenType.BANG, TokenType.NULL, TokenType.FLOAT_LITERAL, TokenType.DOUBLE_LITERAL, TokenType.INTEGER_LITERAL, TokenType.LONG_LITERAL, TokenType.TRUE, TokenType.FALSE, TokenType.CHARACTER_LITERAL, TokenType.STRING_LITERAL, TokenType.SUPER, TokenType.THIS, TokenType.FLOAT, TokenType.LONG, TokenType.INT, TokenType.SHORT, TokenType.BYTE, TokenType.CHAR, TokenType.BOOLEAN, TokenType.DOUBLE, TokenType.VOID, TokenType.NEW, TokenType.LT, TokenType.LPAREN, TokenType.DECR, TokenType.INCR) != -1) {
			ret = parseExpression();
		} else {
			throw produceParseException(TokenType.INCR, TokenType.DECR, TokenType.MINUS, TokenType.PLUS, TokenType.LPAREN, TokenType.TILDE, TokenType.BANG, TokenType.NEW, TokenType.SUPER, TokenType.VOID, TokenType.FLOAT, TokenType.LONG, TokenType.DOUBLE, TokenType.BYTE, TokenType.CHAR, TokenType.INT, TokenType.SHORT, TokenType.BOOLEAN, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER, TokenType.LT, TokenType.THIS, TokenType.FALSE, TokenType.NULL, TokenType.DOUBLE_LITERAL, TokenType.CHARACTER_LITERAL, TokenType.STRING_LITERAL, TokenType.TRUE, TokenType.INTEGER_LITERAL, TokenType.LONG_LITERAL, TokenType.FLOAT_LITERAL, TokenType.LBRACE);
		}
		return ret;
	}

	/* sequence(
		choice(
			nonTerminal(ret, ArrayInitializer)
			nonTerminal(ret, Expression)
		)
	) */
	private int matchVariableInitializer(int lookahead) {
		lookahead = matchVariableInitializer_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		nonTerminal(ret, ArrayInitializer)
		nonTerminal(ret, Expression)
	) */
	private int matchVariableInitializer_1(int lookahead) {
		int newLookahead;
		newLookahead = matchArrayInitializer(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchExpression(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		action({ run(); })
		terminal(LBRACE)
		zeroOrOne(
			nonTerminal(val, VariableInitializer)
			action({ values = append(values, val); })
			zeroOrMore(
				negativeLookAhead(
					zeroOrOne(
						terminal(COMMA)
					)
					terminal(RBRACE)
				)
				terminal(COMMA)
				nonTerminal(val, VariableInitializer)
				action({ values = append(values, val); })
			)
		)
		zeroOrOne(
			terminal(COMMA)
			action({ trailingComma = true; })
		)
		terminal(RBRACE)
		action({ return dress(SArrayInitializerExpr.make(values, trailingComma)); })
	) */
	protected BUTree<SArrayInitializerExpr> parseArrayInitializer() throws ParseException {
		BUTree<SNodeList> values = emptyList();
		BUTree<? extends SExpr> val;
		boolean trailingComma = false;
		run();
		parse(TokenType.LBRACE);
		if (match(0, TokenType.LPAREN, TokenType.IDENTIFIER, TokenType.NODE_VARIABLE, TokenType.VOID, TokenType.DOUBLE, TokenType.FLOAT, TokenType.LONG, TokenType.INT, TokenType.SHORT, TokenType.BYTE, TokenType.CHAR, TokenType.BOOLEAN, TokenType.NEW, TokenType.SUPER, TokenType.LT, TokenType.THIS, TokenType.TRUE, TokenType.STRING_LITERAL, TokenType.CHARACTER_LITERAL, TokenType.DOUBLE_LITERAL, TokenType.NULL, TokenType.FALSE, TokenType.FLOAT_LITERAL, TokenType.LONG_LITERAL, TokenType.INTEGER_LITERAL, TokenType.TILDE, TokenType.BANG, TokenType.MINUS, TokenType.PLUS, TokenType.DECR, TokenType.INCR, TokenType.LBRACE) != -1) {
			val = parseVariableInitializer();
			values = append(values, val);
			while (matchArrayInitializer_lookahead1(0) == -1) {
				parse(TokenType.COMMA);
				val = parseVariableInitializer();
				values = append(values, val);
			}
		}
		if (match(0, TokenType.COMMA) != -1) {
			parse(TokenType.COMMA);
			trailingComma = true;
		}
		parse(TokenType.RBRACE);
		return dress(SArrayInitializerExpr.make(values, trailingComma));
	}

	/* sequence(
		terminal(LBRACE)
		zeroOrOne(
			nonTerminal(val, VariableInitializer)
			zeroOrMore(
				negativeLookAhead(
					zeroOrOne(
						terminal(COMMA)
					)
					terminal(RBRACE)
				)
				terminal(COMMA)
				nonTerminal(val, VariableInitializer)
			)
		)
		zeroOrOne(
			terminal(COMMA)
		)
		terminal(RBRACE)
	) */
	private int matchArrayInitializer(int lookahead) {
		lookahead = match(lookahead, TokenType.LBRACE);
		if (lookahead == -1)
			return -1;
		lookahead = matchArrayInitializer_3(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchArrayInitializer_4(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.RBRACE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		nonTerminal(val, VariableInitializer)
		zeroOrMore(
			negativeLookAhead(
				zeroOrOne(
					terminal(COMMA)
				)
				terminal(RBRACE)
			)
			terminal(COMMA)
			nonTerminal(val, VariableInitializer)
		)
	) */
	private int matchArrayInitializer_3(int lookahead) {
		int newLookahead;
		newLookahead = matchArrayInitializer_3_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		nonTerminal(val, VariableInitializer)
		zeroOrMore(
			negativeLookAhead(
				zeroOrOne(
					terminal(COMMA)
				)
				terminal(RBRACE)
			)
			terminal(COMMA)
			nonTerminal(val, VariableInitializer)
		)
	) */
	private int matchArrayInitializer_3_1(int lookahead) {
		lookahead = matchVariableInitializer(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchArrayInitializer_3_1_3(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		negativeLookAhead(
			zeroOrOne(
				terminal(COMMA)
			)
			terminal(RBRACE)
		)
		terminal(COMMA)
		nonTerminal(val, VariableInitializer)
	) */
	private int matchArrayInitializer_3_1_3(int lookahead) {
		int newLookahead;
		newLookahead = matchArrayInitializer_3_1_3_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchArrayInitializer_3_1_3_1(lookahead);
		}
		return lookahead;
	}

	/* sequence(
		negativeLookAhead(
			zeroOrOne(
				terminal(COMMA)
			)
			terminal(RBRACE)
		)
		terminal(COMMA)
		nonTerminal(val, VariableInitializer)
	) */
	private int matchArrayInitializer_3_1_3_1(int lookahead) {
		lookahead = match(lookahead, TokenType.COMMA);
		if (lookahead == -1)
			return -1;
		lookahead = matchVariableInitializer(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		terminal(COMMA)
	) */
	private int matchArrayInitializer_4(int lookahead) {
		int newLookahead;
		newLookahead = matchArrayInitializer_4_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		terminal(COMMA)
	) */
	private int matchArrayInitializer_4_1(int lookahead) {
		lookahead = match(lookahead, TokenType.COMMA);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		zeroOrOne(
			terminal(COMMA)
		)
		terminal(RBRACE)
	) */
	private int matchArrayInitializer_lookahead1(int lookahead) {
		lookahead = matchArrayInitializer_lookahead1_1(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.RBRACE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		terminal(COMMA)
	) */
	private int matchArrayInitializer_lookahead1_1(int lookahead) {
		int newLookahead;
		newLookahead = matchArrayInitializer_lookahead1_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		terminal(COMMA)
	) */
	private int matchArrayInitializer_lookahead1_1_1(int lookahead) {
		lookahead = match(lookahead, TokenType.COMMA);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		zeroOrOne(
			nonTerminal(typeParameters, TypeParameters)
		)
		nonTerminal(type, ResultType)
		nonTerminal(name, Name)
		nonTerminal(parameters, FormalParameters)
		nonTerminal(arrayDims, ArrayDims)
		zeroOrOne(
			nonTerminal(throwsClause, ThrowsClause)
		)
		choice(
			nonTerminal(block, Block)
			sequence(
				terminal(SEMICOLON)
				action({
					if (modifiers != null && contains(modifiers, SModifier.make(ModifierKeyword.Default))) problem = new BUProblem(Severity.ERROR, "Default methods must have a body");
				})
			)
		)
		action({ return dress(SMethodDecl.make(modifiers, ensureNotNull(typeParameters), type, name, parameters, arrayDims, ensureNotNull(throwsClause), optionOf(block))).withProblem(problem); })
	) */
	protected BUTree<SMethodDecl> parseMethodDecl(BUTree<SNodeList> modifiers) throws ParseException {
		BUTree<SNodeList> typeParameters = null;
		BUTree<? extends SType> type;
		BUTree<SName> name;
		BUTree<SNodeList> parameters;
		BUTree<SNodeList> arrayDims;
		BUTree<SNodeList> throwsClause = null;
		BUTree<SBlockStmt> block = null;
		BUProblem problem = null;
		if (match(0, TokenType.LT) != -1) {
			typeParameters = parseTypeParameters();
		}
		type = parseResultType();
		name = parseName();
		parameters = parseFormalParameters();
		arrayDims = parseArrayDims();
		if (match(0, TokenType.THROWS) != -1) {
			throwsClause = parseThrowsClause();
		}
		if (match(0, TokenType.LBRACE) != -1) {
			block = parseBlock();
		} else if (match(0, TokenType.SEMICOLON) != -1) {
			parse(TokenType.SEMICOLON);
			if (modifiers != null && contains(modifiers, SModifier.make(ModifierKeyword.Default))) problem = new BUProblem(Severity.ERROR, "Default methods must have a body");

		} else {
			throw produceParseException(TokenType.SEMICOLON, TokenType.LBRACE);
		}
		return dress(SMethodDecl.make(modifiers, ensureNotNull(typeParameters), type, name, parameters, arrayDims, ensureNotNull(throwsClause), optionOf(block))).withProblem(problem);
	}

	/* sequence(
		zeroOrOne(
			nonTerminal(typeParameters, TypeParameters)
		)
		nonTerminal(type, ResultType)
		nonTerminal(name, Name)
		nonTerminal(parameters, FormalParameters)
		nonTerminal(arrayDims, ArrayDims)
		zeroOrOne(
			nonTerminal(throwsClause, ThrowsClause)
		)
		choice(
			nonTerminal(block, Block)
			sequence(
				terminal(SEMICOLON)
			)
		)
	) */
	private int matchMethodDecl(int lookahead) {
		lookahead = matchMethodDecl_1(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchResultType(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchFormalParameters(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchArrayDims(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchMethodDecl_6(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchMethodDecl_7(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		nonTerminal(typeParameters, TypeParameters)
	) */
	private int matchMethodDecl_1(int lookahead) {
		int newLookahead;
		newLookahead = matchMethodDecl_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		nonTerminal(typeParameters, TypeParameters)
	) */
	private int matchMethodDecl_1_1(int lookahead) {
		lookahead = matchTypeParameters(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		nonTerminal(throwsClause, ThrowsClause)
	) */
	private int matchMethodDecl_6(int lookahead) {
		int newLookahead;
		newLookahead = matchMethodDecl_6_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		nonTerminal(throwsClause, ThrowsClause)
	) */
	private int matchMethodDecl_6_1(int lookahead) {
		lookahead = matchThrowsClause(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		nonTerminal(block, Block)
		sequence(
			terminal(SEMICOLON)
		)
	) */
	private int matchMethodDecl_7(int lookahead) {
		int newLookahead;
		newLookahead = matchBlock(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchMethodDecl_7_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		terminal(SEMICOLON)
	) */
	private int matchMethodDecl_7_2(int lookahead) {
		lookahead = match(lookahead, TokenType.SEMICOLON);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(LPAREN)
		zeroOrOne(
			nonTerminal(ret, FormalParameterList)
		)
		terminal(RPAREN)
		action({ return ensureNotNull(ret); })
	) */
	protected BUTree<SNodeList> parseFormalParameters() throws ParseException {
		BUTree<SNodeList> ret = null;
		BUTree<SFormalParameter> par;
		parse(TokenType.LPAREN);
		if (match(0, TokenType.NODE_LIST_VARIABLE, TokenType.PUBLIC, TokenType.PROTECTED, TokenType.PRIVATE, TokenType.STRICTFP, TokenType.AT, TokenType.TRANSIENT, TokenType.VOLATILE, TokenType.SYNCHRONIZED, TokenType.NATIVE, TokenType.ABSTRACT, TokenType.DEFAULT, TokenType.STATIC, TokenType.FINAL, TokenType.IDENTIFIER, TokenType.NODE_VARIABLE, TokenType.INT, TokenType.LONG, TokenType.FLOAT, TokenType.DOUBLE, TokenType.BOOLEAN, TokenType.CHAR, TokenType.BYTE, TokenType.SHORT) != -1) {
			ret = parseFormalParameterList();
		}
		parse(TokenType.RPAREN);
		return ensureNotNull(ret);
	}

	/* sequence(
		terminal(LPAREN)
		zeroOrOne(
			nonTerminal(ret, FormalParameterList)
		)
		terminal(RPAREN)
	) */
	private int matchFormalParameters(int lookahead) {
		lookahead = match(lookahead, TokenType.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchFormalParameters_2(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.RPAREN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		nonTerminal(ret, FormalParameterList)
	) */
	private int matchFormalParameters_2(int lookahead) {
		int newLookahead;
		newLookahead = matchFormalParameters_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		nonTerminal(ret, FormalParameterList)
	) */
	private int matchFormalParameters_2_1(int lookahead) {
		lookahead = matchFormalParameterList(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		choice(
			sequence(
				nonTerminal(par, FormalParameter)
				action({ ret = append(ret, par); })
				zeroOrMore(
					terminal(COMMA)
					nonTerminal(par, FormalParameter)
					action({ ret = append(ret, par); })
				)
			)
			sequence(
				lookAhead({ quotesMode })
				nonTerminal(ret, NodeListVar)
			)
		)
		action({ return ret; })
	) */
	protected BUTree<SNodeList> parseFormalParameterList() throws ParseException {
		BUTree<SNodeList> ret = null;
		BUTree<SFormalParameter> par;
		if (match(0, TokenType.AT, TokenType.STRICTFP, TokenType.NATIVE, TokenType.SYNCHRONIZED, TokenType.DEFAULT, TokenType.ABSTRACT, TokenType.PRIVATE, TokenType.PROTECTED, TokenType.VOLATILE, TokenType.TRANSIENT, TokenType.FINAL, TokenType.STATIC, TokenType.PUBLIC, TokenType.BOOLEAN, TokenType.CHAR, TokenType.BYTE, TokenType.SHORT, TokenType.INT, TokenType.LONG, TokenType.FLOAT, TokenType.DOUBLE, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER) != -1) {
			par = parseFormalParameter();
			ret = append(ret, par);
			while (match(0, TokenType.COMMA) != -1) {
				parse(TokenType.COMMA);
				par = parseFormalParameter();
				ret = append(ret, par);
			}
		} else if (quotesMode) {
			ret = parseNodeListVar();
		} else {
			throw produceParseException(TokenType.NODE_LIST_VARIABLE, TokenType.PROTECTED, TokenType.PRIVATE, TokenType.PUBLIC, TokenType.STRICTFP, TokenType.AT, TokenType.STATIC, TokenType.FINAL, TokenType.ABSTRACT, TokenType.DEFAULT, TokenType.SYNCHRONIZED, TokenType.NATIVE, TokenType.TRANSIENT, TokenType.VOLATILE, TokenType.LONG, TokenType.INT, TokenType.DOUBLE, TokenType.FLOAT, TokenType.CHAR, TokenType.BOOLEAN, TokenType.SHORT, TokenType.BYTE, TokenType.IDENTIFIER, TokenType.NODE_VARIABLE);
		}
		return ret;
	}

	/* sequence(
		choice(
			sequence(
				nonTerminal(par, FormalParameter)
				zeroOrMore(
					terminal(COMMA)
					nonTerminal(par, FormalParameter)
				)
			)
			sequence(
				lookAhead({ quotesMode })
				nonTerminal(ret, NodeListVar)
			)
		)
	) */
	private int matchFormalParameterList(int lookahead) {
		lookahead = matchFormalParameterList_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			nonTerminal(par, FormalParameter)
			zeroOrMore(
				terminal(COMMA)
				nonTerminal(par, FormalParameter)
			)
		)
		sequence(
			lookAhead({ quotesMode })
			nonTerminal(ret, NodeListVar)
		)
	) */
	private int matchFormalParameterList_1(int lookahead) {
		int newLookahead;
		newLookahead = matchFormalParameterList_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchFormalParameterList_1_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		nonTerminal(par, FormalParameter)
		zeroOrMore(
			terminal(COMMA)
			nonTerminal(par, FormalParameter)
		)
	) */
	private int matchFormalParameterList_1_1(int lookahead) {
		lookahead = matchFormalParameter(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchFormalParameterList_1_1_3(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		terminal(COMMA)
		nonTerminal(par, FormalParameter)
	) */
	private int matchFormalParameterList_1_1_3(int lookahead) {
		int newLookahead;
		newLookahead = matchFormalParameterList_1_1_3_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchFormalParameterList_1_1_3_1(lookahead);
		}
		return lookahead;
	}

	/* sequence(
		terminal(COMMA)
		nonTerminal(par, FormalParameter)
	) */
	private int matchFormalParameterList_1_1_3_1(int lookahead) {
		lookahead = match(lookahead, TokenType.COMMA);
		if (lookahead == -1)
			return -1;
		lookahead = matchFormalParameter(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead({ quotesMode })
		nonTerminal(ret, NodeListVar)
	) */
	private int matchFormalParameterList_1_2(int lookahead) {
		lookahead = quotesMode ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = matchNodeListVar(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		action({ run(); })
		nonTerminal(modifiers, Modifiers)
		nonTerminal(type, Type)
		zeroOrOne(
			terminal(ELLIPSIS)
			action({ isVarArg = true; })
		)
		choice(
			sequence(
				lookAhead(
					zeroOrOne(
						nonTerminal(Name)
						terminal(DOT)
					)
					terminal(THIS)
				)
				zeroOrOne(
					nonTerminal(receiverTypeName, Name)
					terminal(DOT)
				)
				terminal(THIS)
				action({ isReceiver = true; })
			)
			nonTerminal(id, VariableDeclaratorId)
		)
		action({ return dress(SFormalParameter.make(modifiers, type, isVarArg, optionOf(id), isReceiver, optionOf(receiverTypeName))); })
	) */
	protected BUTree<SFormalParameter> parseFormalParameter() throws ParseException {
		BUTree<SNodeList> modifiers;
		BUTree<? extends SType> type;
		boolean isVarArg = false;
		BUTree<SVariableDeclaratorId> id = null;
		boolean isReceiver = false;
		BUTree<SName> receiverTypeName = null;
		run();
		modifiers = parseModifiers();
		type = parseType(null);
		if (match(0, TokenType.ELLIPSIS) != -1) {
			parse(TokenType.ELLIPSIS);
			isVarArg = true;
		}
		if (matchFormalParameter_lookahead1(0) != -1) {
			if (match(0, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER) != -1) {
				receiverTypeName = parseName();
				parse(TokenType.DOT);
			}
			parse(TokenType.THIS);
			isReceiver = true;
		} else if (match(0, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER) != -1) {
			id = parseVariableDeclaratorId();
		} else {
			throw produceParseException(TokenType.NODE_VARIABLE, TokenType.IDENTIFIER, TokenType.THIS);
		}
		return dress(SFormalParameter.make(modifiers, type, isVarArg, optionOf(id), isReceiver, optionOf(receiverTypeName)));
	}

	/* sequence(
		nonTerminal(modifiers, Modifiers)
		nonTerminal(type, Type)
		zeroOrOne(
			terminal(ELLIPSIS)
		)
		choice(
			sequence(
				lookAhead(
					zeroOrOne(
						nonTerminal(Name)
						terminal(DOT)
					)
					terminal(THIS)
				)
				zeroOrOne(
					nonTerminal(receiverTypeName, Name)
					terminal(DOT)
				)
				terminal(THIS)
			)
			nonTerminal(id, VariableDeclaratorId)
		)
	) */
	private int matchFormalParameter(int lookahead) {
		lookahead = matchModifiers(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchType(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchFormalParameter_4(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchFormalParameter_5(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		terminal(ELLIPSIS)
	) */
	private int matchFormalParameter_4(int lookahead) {
		int newLookahead;
		newLookahead = matchFormalParameter_4_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		terminal(ELLIPSIS)
	) */
	private int matchFormalParameter_4_1(int lookahead) {
		lookahead = match(lookahead, TokenType.ELLIPSIS);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			lookAhead(
				zeroOrOne(
					nonTerminal(Name)
					terminal(DOT)
				)
				terminal(THIS)
			)
			zeroOrOne(
				nonTerminal(receiverTypeName, Name)
				terminal(DOT)
			)
			terminal(THIS)
		)
		nonTerminal(id, VariableDeclaratorId)
	) */
	private int matchFormalParameter_5(int lookahead) {
		int newLookahead;
		newLookahead = matchFormalParameter_5_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchVariableDeclaratorId(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		lookAhead(
			zeroOrOne(
				nonTerminal(Name)
				terminal(DOT)
			)
			terminal(THIS)
		)
		zeroOrOne(
			nonTerminal(receiverTypeName, Name)
			terminal(DOT)
		)
		terminal(THIS)
	) */
	private int matchFormalParameter_5_1(int lookahead) {
		lookahead = matchFormalParameter_5_1_2(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.THIS);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		nonTerminal(receiverTypeName, Name)
		terminal(DOT)
	) */
	private int matchFormalParameter_5_1_2(int lookahead) {
		int newLookahead;
		newLookahead = matchFormalParameter_5_1_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		nonTerminal(receiverTypeName, Name)
		terminal(DOT)
	) */
	private int matchFormalParameter_5_1_2_1(int lookahead) {
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.DOT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		zeroOrOne(
			nonTerminal(Name)
			terminal(DOT)
		)
		terminal(THIS)
	) */
	private int matchFormalParameter_lookahead1(int lookahead) {
		lookahead = matchFormalParameter_lookahead1_1(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.THIS);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		nonTerminal(Name)
		terminal(DOT)
	) */
	private int matchFormalParameter_lookahead1_1(int lookahead) {
		int newLookahead;
		newLookahead = matchFormalParameter_lookahead1_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		nonTerminal(Name)
		terminal(DOT)
	) */
	private int matchFormalParameter_lookahead1_1_1(int lookahead) {
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.DOT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(THROWS)
		nonTerminal(cit, AnnotatedQualifiedType)
		action({ ret = append(ret, cit); })
		zeroOrMore(
			terminal(COMMA)
			nonTerminal(cit, AnnotatedQualifiedType)
			action({ ret = append(ret, cit); })
		)
		action({ return ret; })
	) */
	protected BUTree<SNodeList> parseThrowsClause() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<SQualifiedType> cit;
		parse(TokenType.THROWS);
		cit = parseAnnotatedQualifiedType();
		ret = append(ret, cit);
		while (match(0, TokenType.COMMA) != -1) {
			parse(TokenType.COMMA);
			cit = parseAnnotatedQualifiedType();
			ret = append(ret, cit);
		}
		return ret;
	}

	/* sequence(
		terminal(THROWS)
		nonTerminal(cit, AnnotatedQualifiedType)
		zeroOrMore(
			terminal(COMMA)
			nonTerminal(cit, AnnotatedQualifiedType)
		)
	) */
	private int matchThrowsClause(int lookahead) {
		lookahead = match(lookahead, TokenType.THROWS);
		if (lookahead == -1)
			return -1;
		lookahead = matchAnnotatedQualifiedType(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchThrowsClause_4(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		terminal(COMMA)
		nonTerminal(cit, AnnotatedQualifiedType)
	) */
	private int matchThrowsClause_4(int lookahead) {
		int newLookahead;
		newLookahead = matchThrowsClause_4_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchThrowsClause_4_1(lookahead);
		}
		return lookahead;
	}

	/* sequence(
		terminal(COMMA)
		nonTerminal(cit, AnnotatedQualifiedType)
	) */
	private int matchThrowsClause_4_1(int lookahead) {
		lookahead = match(lookahead, TokenType.COMMA);
		if (lookahead == -1)
			return -1;
		lookahead = matchAnnotatedQualifiedType(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		zeroOrOne(
			nonTerminal(typeParameters, TypeParameters)
		)
		nonTerminal(name, Name)
		nonTerminal(parameters, FormalParameters)
		zeroOrOne(
			nonTerminal(throwsClause, ThrowsClause)
		)
		action({ run(); })
		terminal(LBRACE)
		nonTerminal(stmts, Statements)
		terminal(RBRACE)
		action({ block = dress(SBlockStmt.make(stmts)); })
		action({ return dress(SConstructorDecl.make(modifiers, ensureNotNull(typeParameters), name, parameters, ensureNotNull(throwsClause), block)); })
	) */
	protected BUTree<SConstructorDecl> parseConstructorDecl(BUTree<SNodeList> modifiers) throws ParseException {
		BUTree<SNodeList> typeParameters = null;
		BUTree<SName> name;
		BUTree<SNodeList> parameters;
		BUTree<SNodeList> throwsClause = null;
		BUTree<SExplicitConstructorInvocationStmt> exConsInv = null;
		BUTree<SBlockStmt> block;
		BUTree<SNodeList> stmts = emptyList();
		BUTree<? extends SStmt> stmt;
		if (match(0, TokenType.LT) != -1) {
			typeParameters = parseTypeParameters();
		}
		name = parseName();
		parameters = parseFormalParameters();
		if (match(0, TokenType.THROWS) != -1) {
			throwsClause = parseThrowsClause();
		}
		run();
		parse(TokenType.LBRACE);
		stmts = parseStatements(true);
		parse(TokenType.RBRACE);
		block = dress(SBlockStmt.make(stmts));
		return dress(SConstructorDecl.make(modifiers, ensureNotNull(typeParameters), name, parameters, ensureNotNull(throwsClause), block));
	}

	/* sequence(
		zeroOrOne(
			nonTerminal(typeParameters, TypeParameters)
		)
		nonTerminal(name, Name)
		nonTerminal(parameters, FormalParameters)
		zeroOrOne(
			nonTerminal(throwsClause, ThrowsClause)
		)
		terminal(LBRACE)
		nonTerminal(stmts, Statements)
		terminal(RBRACE)
	) */
	private int matchConstructorDecl(int lookahead) {
		lookahead = matchConstructorDecl_1(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchFormalParameters(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchConstructorDecl_4(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.LBRACE);
		if (lookahead == -1)
			return -1;
		lookahead = matchStatements(lookahead, true);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.RBRACE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		nonTerminal(typeParameters, TypeParameters)
	) */
	private int matchConstructorDecl_1(int lookahead) {
		int newLookahead;
		newLookahead = matchConstructorDecl_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		nonTerminal(typeParameters, TypeParameters)
	) */
	private int matchConstructorDecl_1_1(int lookahead) {
		lookahead = matchTypeParameters(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		nonTerminal(throwsClause, ThrowsClause)
	) */
	private int matchConstructorDecl_4(int lookahead) {
		int newLookahead;
		newLookahead = matchConstructorDecl_4_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		nonTerminal(throwsClause, ThrowsClause)
	) */
	private int matchConstructorDecl_4_1(int lookahead) {
		lookahead = matchThrowsClause(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		action({ run(); })
		choice(
			sequence(
				lookAhead(
					zeroOrOne(
						nonTerminal(TypeArguments)
					)
					terminal(THIS)
					terminal(LPAREN)
				)
				zeroOrOne(
					nonTerminal(typeArgs, TypeArguments)
				)
				terminal(THIS)
				action({ isThis = true; })
				nonTerminal(args, Arguments)
				terminal(SEMICOLON)
			)
			sequence(
				zeroOrOne(
					lookAhead(
						nonTerminal(PrimaryExpressionWithoutSuperSuffix)
						terminal(DOT)
					)
					nonTerminal(expr, PrimaryExpressionWithoutSuperSuffix)
					terminal(DOT)
				)
				zeroOrOne(
					nonTerminal(typeArgs, TypeArguments)
				)
				terminal(SUPER)
				nonTerminal(args, Arguments)
				terminal(SEMICOLON)
			)
		)
		action({ return dress(SExplicitConstructorInvocationStmt.make(ensureNotNull(typeArgs), isThis, optionOf(expr), args)); })
	) */
	protected BUTree<SExplicitConstructorInvocationStmt> parseExplicitConstructorInvocation() throws ParseException {
		boolean isThis = false;
		BUTree<SNodeList> args;
		BUTree<? extends SExpr> expr = null;
		BUTree<SNodeList> typeArgs = null;
		run();
		if (matchExplicitConstructorInvocation_lookahead1(0) != -1) {
			if (match(0, TokenType.LT) != -1) {
				typeArgs = parseTypeArguments();
			}
			parse(TokenType.THIS);
			isThis = true;
			args = parseArguments();
			parse(TokenType.SEMICOLON);
		} else if (match(0, TokenType.LPAREN, TokenType.NEW, TokenType.SUPER, TokenType.THIS, TokenType.CHARACTER_LITERAL, TokenType.DOUBLE_LITERAL, TokenType.FLOAT_LITERAL, TokenType.LONG_LITERAL, TokenType.NULL, TokenType.FALSE, TokenType.TRUE, TokenType.STRING_LITERAL, TokenType.INTEGER_LITERAL, TokenType.IDENTIFIER, TokenType.NODE_VARIABLE, TokenType.LT, TokenType.VOID, TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.INT, TokenType.SHORT, TokenType.FLOAT, TokenType.LONG, TokenType.DOUBLE) != -1) {
			if (matchExplicitConstructorInvocation_lookahead2(0) != -1) {
				expr = parsePrimaryExpressionWithoutSuperSuffix();
				parse(TokenType.DOT);
			}
			if (match(0, TokenType.LT) != -1) {
				typeArgs = parseTypeArguments();
			}
			parse(TokenType.SUPER);
			args = parseArguments();
			parse(TokenType.SEMICOLON);
		} else {
			throw produceParseException(TokenType.SUPER, TokenType.THIS, TokenType.STRING_LITERAL, TokenType.CHARACTER_LITERAL, TokenType.FALSE, TokenType.TRUE, TokenType.NULL, TokenType.LONG_LITERAL, TokenType.INTEGER_LITERAL, TokenType.DOUBLE_LITERAL, TokenType.FLOAT_LITERAL, TokenType.LPAREN, TokenType.IDENTIFIER, TokenType.NODE_VARIABLE, TokenType.LT, TokenType.SHORT, TokenType.BYTE, TokenType.LONG, TokenType.INT, TokenType.DOUBLE, TokenType.FLOAT, TokenType.CHAR, TokenType.BOOLEAN, TokenType.VOID, TokenType.NEW);
		}
		return dress(SExplicitConstructorInvocationStmt.make(ensureNotNull(typeArgs), isThis, optionOf(expr), args));
	}

	/* sequence(
		choice(
			sequence(
				lookAhead(
					zeroOrOne(
						nonTerminal(TypeArguments)
					)
					terminal(THIS)
					terminal(LPAREN)
				)
				zeroOrOne(
					nonTerminal(typeArgs, TypeArguments)
				)
				terminal(THIS)
				nonTerminal(args, Arguments)
				terminal(SEMICOLON)
			)
			sequence(
				zeroOrOne(
					lookAhead(
						nonTerminal(PrimaryExpressionWithoutSuperSuffix)
						terminal(DOT)
					)
					nonTerminal(expr, PrimaryExpressionWithoutSuperSuffix)
					terminal(DOT)
				)
				zeroOrOne(
					nonTerminal(typeArgs, TypeArguments)
				)
				terminal(SUPER)
				nonTerminal(args, Arguments)
				terminal(SEMICOLON)
			)
		)
	) */
	private int matchExplicitConstructorInvocation(int lookahead) {
		lookahead = matchExplicitConstructorInvocation_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			lookAhead(
				zeroOrOne(
					nonTerminal(TypeArguments)
				)
				terminal(THIS)
				terminal(LPAREN)
			)
			zeroOrOne(
				nonTerminal(typeArgs, TypeArguments)
			)
			terminal(THIS)
			nonTerminal(args, Arguments)
			terminal(SEMICOLON)
		)
		sequence(
			zeroOrOne(
				lookAhead(
					nonTerminal(PrimaryExpressionWithoutSuperSuffix)
					terminal(DOT)
				)
				nonTerminal(expr, PrimaryExpressionWithoutSuperSuffix)
				terminal(DOT)
			)
			zeroOrOne(
				nonTerminal(typeArgs, TypeArguments)
			)
			terminal(SUPER)
			nonTerminal(args, Arguments)
			terminal(SEMICOLON)
		)
	) */
	private int matchExplicitConstructorInvocation_2(int lookahead) {
		int newLookahead;
		newLookahead = matchExplicitConstructorInvocation_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchExplicitConstructorInvocation_2_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		lookAhead(
			zeroOrOne(
				nonTerminal(TypeArguments)
			)
			terminal(THIS)
			terminal(LPAREN)
		)
		zeroOrOne(
			nonTerminal(typeArgs, TypeArguments)
		)
		terminal(THIS)
		nonTerminal(args, Arguments)
		terminal(SEMICOLON)
	) */
	private int matchExplicitConstructorInvocation_2_1(int lookahead) {
		lookahead = matchExplicitConstructorInvocation_2_1_2(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.THIS);
		if (lookahead == -1)
			return -1;
		lookahead = matchArguments(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.SEMICOLON);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		nonTerminal(typeArgs, TypeArguments)
	) */
	private int matchExplicitConstructorInvocation_2_1_2(int lookahead) {
		int newLookahead;
		newLookahead = matchExplicitConstructorInvocation_2_1_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		nonTerminal(typeArgs, TypeArguments)
	) */
	private int matchExplicitConstructorInvocation_2_1_2_1(int lookahead) {
		lookahead = matchTypeArguments(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		zeroOrOne(
			lookAhead(
				nonTerminal(PrimaryExpressionWithoutSuperSuffix)
				terminal(DOT)
			)
			nonTerminal(expr, PrimaryExpressionWithoutSuperSuffix)
			terminal(DOT)
		)
		zeroOrOne(
			nonTerminal(typeArgs, TypeArguments)
		)
		terminal(SUPER)
		nonTerminal(args, Arguments)
		terminal(SEMICOLON)
	) */
	private int matchExplicitConstructorInvocation_2_2(int lookahead) {
		lookahead = matchExplicitConstructorInvocation_2_2_1(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchExplicitConstructorInvocation_2_2_2(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.SUPER);
		if (lookahead == -1)
			return -1;
		lookahead = matchArguments(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.SEMICOLON);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		lookAhead(
			nonTerminal(PrimaryExpressionWithoutSuperSuffix)
			terminal(DOT)
		)
		nonTerminal(expr, PrimaryExpressionWithoutSuperSuffix)
		terminal(DOT)
	) */
	private int matchExplicitConstructorInvocation_2_2_1(int lookahead) {
		int newLookahead;
		newLookahead = matchExplicitConstructorInvocation_2_2_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		lookAhead(
			nonTerminal(PrimaryExpressionWithoutSuperSuffix)
			terminal(DOT)
		)
		nonTerminal(expr, PrimaryExpressionWithoutSuperSuffix)
		terminal(DOT)
	) */
	private int matchExplicitConstructorInvocation_2_2_1_1(int lookahead) {
		lookahead = matchPrimaryExpressionWithoutSuperSuffix(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.DOT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		nonTerminal(typeArgs, TypeArguments)
	) */
	private int matchExplicitConstructorInvocation_2_2_2(int lookahead) {
		int newLookahead;
		newLookahead = matchExplicitConstructorInvocation_2_2_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		nonTerminal(typeArgs, TypeArguments)
	) */
	private int matchExplicitConstructorInvocation_2_2_2_1(int lookahead) {
		lookahead = matchTypeArguments(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		zeroOrOne(
			nonTerminal(TypeArguments)
		)
		terminal(THIS)
		terminal(LPAREN)
	) */
	private int matchExplicitConstructorInvocation_lookahead1(int lookahead) {
		lookahead = matchExplicitConstructorInvocation_lookahead1_1(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.THIS);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.LPAREN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		nonTerminal(TypeArguments)
	) */
	private int matchExplicitConstructorInvocation_lookahead1_1(int lookahead) {
		int newLookahead;
		newLookahead = matchExplicitConstructorInvocation_lookahead1_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		nonTerminal(TypeArguments)
	) */
	private int matchExplicitConstructorInvocation_lookahead1_1_1(int lookahead) {
		lookahead = matchTypeArguments(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(PrimaryExpressionWithoutSuperSuffix)
		terminal(DOT)
	) */
	private int matchExplicitConstructorInvocation_lookahead2(int lookahead) {
		lookahead = matchPrimaryExpressionWithoutSuperSuffix(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.DOT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		zeroOrOne(
			choice(
				sequence(
					zeroOrOne(
						lookAhead({ inConstructor })
						nonTerminal(stmt, ExplicitConstructorInvocation)
						action({ ret = append(ret, stmt); })
					)
					zeroOrMore(
						nonTerminal(stmt, BlockStatement)
						action({ ret = append(ret, stmt); })
					)
				)
				sequence(
					lookAhead({ quotesMode })
					nonTerminal(ret, NodeListVar)
				)
			)
		)
		action({ return ensureNotNull(ret); })
	) */
	protected BUTree<SNodeList> parseStatements(boolean inConstructor) throws ParseException {
		BUTree<SNodeList> ret = null;
		BUTree<? extends SStmt> stmt;
		if (match(0, TokenType.LT, TokenType.THIS, TokenType.NULL, TokenType.TRUE, TokenType.FALSE, TokenType.CHARACTER_LITERAL, TokenType.STRING_LITERAL, TokenType.FLOAT_LITERAL, TokenType.DOUBLE_LITERAL, TokenType.INTEGER_LITERAL, TokenType.LONG_LITERAL, TokenType.VOID, TokenType.IDENTIFIER, TokenType.NODE_VARIABLE, TokenType.INT, TokenType.SHORT, TokenType.BYTE, TokenType.CHAR, TokenType.DOUBLE, TokenType.FLOAT, TokenType.LONG, TokenType.BOOLEAN, TokenType.NEW, TokenType.SUPER, TokenType.LPAREN, TokenType.ABSTRACT, TokenType.PRIVATE, TokenType.FINAL, TokenType.STATIC, TokenType.PROTECTED, TokenType.PUBLIC, TokenType.AT, TokenType.STRICTFP, TokenType.VOLATILE, TokenType.TRANSIENT, TokenType.NATIVE, TokenType.SYNCHRONIZED, TokenType.CLASS, TokenType.INTERFACE, TokenType.ASSERT, TokenType.LBRACE, TokenType.SEMICOLON, TokenType.DECR, TokenType.INCR, TokenType.SWITCH, TokenType.IF, TokenType.WHILE, TokenType.DO, TokenType.FOR, TokenType.BREAK, TokenType.CONTINUE, TokenType.RETURN, TokenType.THROW, TokenType.TRY, TokenType.NODE_LIST_VARIABLE) != -1) {
			if (match(0, TokenType.IDENTIFIER, TokenType.NODE_VARIABLE, TokenType.INT, TokenType.SHORT, TokenType.BYTE, TokenType.CHAR, TokenType.DOUBLE, TokenType.FLOAT, TokenType.LONG, TokenType.BOOLEAN, TokenType.VOID, TokenType.NEW, TokenType.SUPER, TokenType.THIS, TokenType.LPAREN, TokenType.LT, TokenType.CHARACTER_LITERAL, TokenType.STRING_LITERAL, TokenType.FLOAT_LITERAL, TokenType.DOUBLE_LITERAL, TokenType.NULL, TokenType.TRUE, TokenType.FALSE, TokenType.INTEGER_LITERAL, TokenType.LONG_LITERAL, TokenType.SYNCHRONIZED, TokenType.TRY, TokenType.RETURN, TokenType.THROW, TokenType.BREAK, TokenType.CONTINUE, TokenType.DO, TokenType.FOR, TokenType.IF, TokenType.WHILE, TokenType.INCR, TokenType.DECR, TokenType.SWITCH, TokenType.LBRACE, TokenType.SEMICOLON, TokenType.ASSERT, TokenType.STATIC, TokenType.FINAL, TokenType.PRIVATE, TokenType.ABSTRACT, TokenType.PUBLIC, TokenType.PROTECTED, TokenType.STRICTFP, TokenType.AT, TokenType.NATIVE, TokenType.TRANSIENT, TokenType.VOLATILE, TokenType.INTERFACE, TokenType.CLASS) != -1) {
				if (inConstructor && matchStatements_lookahead1(0, inConstructor) != -1) {
					stmt = parseExplicitConstructorInvocation();
					ret = append(ret, stmt);
				}
				while (match(0, TokenType.DO, TokenType.WHILE, TokenType.IF, TokenType.SWITCH, TokenType.RETURN, TokenType.CONTINUE, TokenType.BREAK, TokenType.FOR, TokenType.IDENTIFIER, TokenType.NODE_VARIABLE, TokenType.INTEGER_LITERAL, TokenType.LONG_LITERAL, TokenType.TRUE, TokenType.FALSE, TokenType.NULL, TokenType.FLOAT_LITERAL, TokenType.DOUBLE_LITERAL, TokenType.CHARACTER_LITERAL, TokenType.STRING_LITERAL, TokenType.VOID, TokenType.BYTE, TokenType.CHAR, TokenType.INT, TokenType.SHORT, TokenType.FLOAT, TokenType.LONG, TokenType.DOUBLE, TokenType.BOOLEAN, TokenType.NEW, TokenType.SUPER, TokenType.THIS, TokenType.LPAREN, TokenType.LT, TokenType.DECR, TokenType.INCR, TokenType.SEMICOLON, TokenType.LBRACE, TokenType.ASSERT, TokenType.TRY, TokenType.SYNCHRONIZED, TokenType.THROW, TokenType.PUBLIC, TokenType.PRIVATE, TokenType.PROTECTED, TokenType.AT, TokenType.VOLATILE, TokenType.STRICTFP, TokenType.NATIVE, TokenType.STATIC, TokenType.ABSTRACT, TokenType.TRANSIENT, TokenType.FINAL, TokenType.CLASS, TokenType.INTERFACE) != -1) {
					stmt = parseBlockStatement();
					ret = append(ret, stmt);
				}
			} else if (quotesMode) {
				ret = parseNodeListVar();
			} else {
				throw produceParseException(TokenType.NODE_LIST_VARIABLE, TokenType.CHARACTER_LITERAL, TokenType.STRING_LITERAL, TokenType.TRUE, TokenType.FALSE, TokenType.NULL, TokenType.INTEGER_LITERAL, TokenType.LONG_LITERAL, TokenType.FLOAT_LITERAL, TokenType.DOUBLE_LITERAL, TokenType.THIS, TokenType.SUPER, TokenType.NEW, TokenType.VOID, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER, TokenType.BYTE, TokenType.CHAR, TokenType.BOOLEAN, TokenType.FLOAT, TokenType.LONG, TokenType.INT, TokenType.SHORT, TokenType.DOUBLE, TokenType.LT, TokenType.LPAREN, TokenType.TRY, TokenType.SYNCHRONIZED, TokenType.ASSERT, TokenType.SWITCH, TokenType.DECR, TokenType.INCR, TokenType.SEMICOLON, TokenType.LBRACE, TokenType.FOR, TokenType.DO, TokenType.WHILE, TokenType.IF, TokenType.THROW, TokenType.RETURN, TokenType.CONTINUE, TokenType.BREAK, TokenType.ABSTRACT, TokenType.PRIVATE, TokenType.FINAL, TokenType.STATIC, TokenType.VOLATILE, TokenType.TRANSIENT, TokenType.NATIVE, TokenType.AT, TokenType.STRICTFP, TokenType.PROTECTED, TokenType.PUBLIC, TokenType.INTERFACE, TokenType.CLASS);
			}
		}
		return ensureNotNull(ret);
	}

	/* sequence(
		zeroOrOne(
			choice(
				sequence(
					zeroOrOne(
						lookAhead({ inConstructor })
						nonTerminal(stmt, ExplicitConstructorInvocation)
					)
					zeroOrMore(
						nonTerminal(stmt, BlockStatement)
					)
				)
				sequence(
					lookAhead({ quotesMode })
					nonTerminal(ret, NodeListVar)
				)
			)
		)
	) */
	private int matchStatements(int lookahead, boolean inConstructor) {
		lookahead = matchStatements_1(lookahead, inConstructor);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		choice(
			sequence(
				zeroOrOne(
					lookAhead({ inConstructor })
					nonTerminal(stmt, ExplicitConstructorInvocation)
				)
				zeroOrMore(
					nonTerminal(stmt, BlockStatement)
				)
			)
			sequence(
				lookAhead({ quotesMode })
				nonTerminal(ret, NodeListVar)
			)
		)
	) */
	private int matchStatements_1(int lookahead, boolean inConstructor) {
		int newLookahead;
		newLookahead = matchStatements_1_1(lookahead, inConstructor);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		choice(
			sequence(
				zeroOrOne(
					lookAhead({ inConstructor })
					nonTerminal(stmt, ExplicitConstructorInvocation)
				)
				zeroOrMore(
					nonTerminal(stmt, BlockStatement)
				)
			)
			sequence(
				lookAhead({ quotesMode })
				nonTerminal(ret, NodeListVar)
			)
		)
	) */
	private int matchStatements_1_1(int lookahead, boolean inConstructor) {
		lookahead = matchStatements_1_1_1(lookahead, inConstructor);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			zeroOrOne(
				lookAhead({ inConstructor })
				nonTerminal(stmt, ExplicitConstructorInvocation)
			)
			zeroOrMore(
				nonTerminal(stmt, BlockStatement)
			)
		)
		sequence(
			lookAhead({ quotesMode })
			nonTerminal(ret, NodeListVar)
		)
	) */
	private int matchStatements_1_1_1(int lookahead, boolean inConstructor) {
		int newLookahead;
		newLookahead = matchStatements_1_1_1_1(lookahead, inConstructor);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchStatements_1_1_1_2(lookahead, inConstructor);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		zeroOrOne(
			lookAhead({ inConstructor })
			nonTerminal(stmt, ExplicitConstructorInvocation)
		)
		zeroOrMore(
			nonTerminal(stmt, BlockStatement)
		)
	) */
	private int matchStatements_1_1_1_1(int lookahead, boolean inConstructor) {
		lookahead = matchStatements_1_1_1_1_1(lookahead, inConstructor);
		if (lookahead == -1)
			return -1;
		lookahead = matchStatements_1_1_1_1_2(lookahead, inConstructor);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		lookAhead({ inConstructor })
		nonTerminal(stmt, ExplicitConstructorInvocation)
	) */
	private int matchStatements_1_1_1_1_1(int lookahead, boolean inConstructor) {
		int newLookahead;
		newLookahead = matchStatements_1_1_1_1_1_1(lookahead, inConstructor);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		lookAhead({ inConstructor })
		nonTerminal(stmt, ExplicitConstructorInvocation)
	) */
	private int matchStatements_1_1_1_1_1_1(int lookahead, boolean inConstructor) {
		lookahead = inConstructor ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = matchExplicitConstructorInvocation(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		nonTerminal(stmt, BlockStatement)
	) */
	private int matchStatements_1_1_1_1_2(int lookahead, boolean inConstructor) {
		int newLookahead;
		newLookahead = matchStatements_1_1_1_1_2_1(lookahead, inConstructor);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchStatements_1_1_1_1_2_1(lookahead, inConstructor);
		}
		return lookahead;
	}

	/* sequence(
		nonTerminal(stmt, BlockStatement)
	) */
	private int matchStatements_1_1_1_1_2_1(int lookahead, boolean inConstructor) {
		lookahead = matchBlockStatement(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead({ quotesMode })
		nonTerminal(ret, NodeListVar)
	) */
	private int matchStatements_1_1_1_2(int lookahead, boolean inConstructor) {
		lookahead = quotesMode ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = matchNodeListVar(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(ExplicitConstructorInvocation)
	) */
	private int matchStatements_lookahead1(int lookahead, boolean inConstructor) {
		lookahead = matchExplicitConstructorInvocation(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(block, Block)
		action({ return dress(SInitializerDecl.make(modifiers, block)); })
	) */
	protected BUTree<SInitializerDecl> parseInitializerDecl(BUTree<SNodeList> modifiers) throws ParseException {
		BUTree<SBlockStmt> block;
		block = parseBlock();
		return dress(SInitializerDecl.make(modifiers, block));
	}

	/* sequence(
		nonTerminal(block, Block)
	) */
	private int matchInitializerDecl(int lookahead) {
		lookahead = matchBlock(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		choice(
			sequence(
				nonTerminal(primitiveType, PrimitiveType)
				zeroOrOne(
					lookAhead(
						nonTerminal(Annotations)
						terminal(LBRACKET)
					)
					action({ lateRun(); })
					nonTerminal(arrayDims, ArrayDimsMandatory)
					action({ type = dress(SArrayType.make(primitiveType, arrayDims)); })
				)
			)
			sequence(
				nonTerminal(type, QualifiedType)
				zeroOrOne(
					lookAhead(
						nonTerminal(Annotations)
						terminal(LBRACKET)
					)
					action({ lateRun(); })
					nonTerminal(arrayDims, ArrayDimsMandatory)
					action({ type = dress(SArrayType.make(type, arrayDims)); })
				)
			)
		)
		action({ return type == null ? primitiveType : type; })
	) */
	protected BUTree<? extends SType> parseType(BUTree<SNodeList> annotations) throws ParseException {
		BUTree<? extends SType> primitiveType = null;
		BUTree<? extends SReferenceType> type = null;
		BUTree<SNodeList> arrayDims;
		if (match(0, TokenType.DOUBLE, TokenType.FLOAT, TokenType.LONG, TokenType.INT, TokenType.SHORT, TokenType.BYTE, TokenType.CHAR, TokenType.BOOLEAN) != -1) {
			primitiveType = parsePrimitiveType(annotations);
			if (matchType_lookahead1(0) != -1) {
				lateRun();
				arrayDims = parseArrayDimsMandatory();
				type = dress(SArrayType.make(primitiveType, arrayDims));
			}
		} else if (match(0, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER) != -1) {
			type = parseQualifiedType(annotations);
			if (matchType_lookahead2(0) != -1) {
				lateRun();
				arrayDims = parseArrayDimsMandatory();
				type = dress(SArrayType.make(type, arrayDims));
			}
		} else {
			throw produceParseException(TokenType.IDENTIFIER, TokenType.NODE_VARIABLE, TokenType.SHORT, TokenType.BYTE, TokenType.LONG, TokenType.INT, TokenType.DOUBLE, TokenType.FLOAT, TokenType.CHAR, TokenType.BOOLEAN);
		}
		return type == null ? primitiveType : type;
	}

	/* sequence(
		choice(
			sequence(
				nonTerminal(primitiveType, PrimitiveType)
				zeroOrOne(
					lookAhead(
						nonTerminal(Annotations)
						terminal(LBRACKET)
					)
					nonTerminal(arrayDims, ArrayDimsMandatory)
				)
			)
			sequence(
				nonTerminal(type, QualifiedType)
				zeroOrOne(
					lookAhead(
						nonTerminal(Annotations)
						terminal(LBRACKET)
					)
					nonTerminal(arrayDims, ArrayDimsMandatory)
				)
			)
		)
	) */
	private int matchType(int lookahead) {
		lookahead = matchType_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			nonTerminal(primitiveType, PrimitiveType)
			zeroOrOne(
				lookAhead(
					nonTerminal(Annotations)
					terminal(LBRACKET)
				)
				nonTerminal(arrayDims, ArrayDimsMandatory)
			)
		)
		sequence(
			nonTerminal(type, QualifiedType)
			zeroOrOne(
				lookAhead(
					nonTerminal(Annotations)
					terminal(LBRACKET)
				)
				nonTerminal(arrayDims, ArrayDimsMandatory)
			)
		)
	) */
	private int matchType_1(int lookahead) {
		int newLookahead;
		newLookahead = matchType_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchType_1_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		nonTerminal(primitiveType, PrimitiveType)
		zeroOrOne(
			lookAhead(
				nonTerminal(Annotations)
				terminal(LBRACKET)
			)
			nonTerminal(arrayDims, ArrayDimsMandatory)
		)
	) */
	private int matchType_1_1(int lookahead) {
		lookahead = matchPrimitiveType(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchType_1_1_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		lookAhead(
			nonTerminal(Annotations)
			terminal(LBRACKET)
		)
		nonTerminal(arrayDims, ArrayDimsMandatory)
	) */
	private int matchType_1_1_2(int lookahead) {
		int newLookahead;
		newLookahead = matchType_1_1_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		lookAhead(
			nonTerminal(Annotations)
			terminal(LBRACKET)
		)
		nonTerminal(arrayDims, ArrayDimsMandatory)
	) */
	private int matchType_1_1_2_1(int lookahead) {
		lookahead = matchArrayDimsMandatory(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(type, QualifiedType)
		zeroOrOne(
			lookAhead(
				nonTerminal(Annotations)
				terminal(LBRACKET)
			)
			nonTerminal(arrayDims, ArrayDimsMandatory)
		)
	) */
	private int matchType_1_2(int lookahead) {
		lookahead = matchQualifiedType(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchType_1_2_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		lookAhead(
			nonTerminal(Annotations)
			terminal(LBRACKET)
		)
		nonTerminal(arrayDims, ArrayDimsMandatory)
	) */
	private int matchType_1_2_2(int lookahead) {
		int newLookahead;
		newLookahead = matchType_1_2_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		lookAhead(
			nonTerminal(Annotations)
			terminal(LBRACKET)
		)
		nonTerminal(arrayDims, ArrayDimsMandatory)
	) */
	private int matchType_1_2_2_1(int lookahead) {
		lookahead = matchArrayDimsMandatory(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(Annotations)
		terminal(LBRACKET)
	) */
	private int matchType_lookahead1(int lookahead) {
		lookahead = matchAnnotations(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.LBRACKET);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(Annotations)
		terminal(LBRACKET)
	) */
	private int matchType_lookahead2(int lookahead) {
		lookahead = matchAnnotations(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.LBRACKET);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		choice(
			sequence(
				nonTerminal(primitiveType, PrimitiveType)
				action({ lateRun(); })
				nonTerminal(arrayDims, ArrayDimsMandatory)
				action({ type = dress(SArrayType.make(primitiveType, arrayDims)); })
			)
			sequence(
				nonTerminal(type, QualifiedType)
				zeroOrOne(
					lookAhead(
						nonTerminal(Annotations)
						terminal(LBRACKET)
					)
					action({ lateRun(); })
					nonTerminal(arrayDims, ArrayDimsMandatory)
					action({ type = dress(SArrayType.make(type, arrayDims)); })
				)
			)
		)
		action({ return type; })
	) */
	protected BUTree<? extends SReferenceType> parseReferenceType(BUTree<SNodeList> annotations) throws ParseException {
		BUTree<? extends SType> primitiveType;
		BUTree<? extends SReferenceType> type;
		BUTree<SNodeList> arrayDims;
		if (match(0, TokenType.DOUBLE, TokenType.FLOAT, TokenType.LONG, TokenType.INT, TokenType.SHORT, TokenType.BYTE, TokenType.CHAR, TokenType.BOOLEAN) != -1) {
			primitiveType = parsePrimitiveType(annotations);
			lateRun();
			arrayDims = parseArrayDimsMandatory();
			type = dress(SArrayType.make(primitiveType, arrayDims));
		} else if (match(0, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER) != -1) {
			type = parseQualifiedType(annotations);
			if (matchReferenceType_lookahead1(0) != -1) {
				lateRun();
				arrayDims = parseArrayDimsMandatory();
				type = dress(SArrayType.make(type, arrayDims));
			}
		} else {
			throw produceParseException(TokenType.IDENTIFIER, TokenType.NODE_VARIABLE, TokenType.SHORT, TokenType.BYTE, TokenType.LONG, TokenType.INT, TokenType.DOUBLE, TokenType.FLOAT, TokenType.CHAR, TokenType.BOOLEAN);
		}
		return type;
	}

	/* sequence(
		choice(
			sequence(
				nonTerminal(primitiveType, PrimitiveType)
				nonTerminal(arrayDims, ArrayDimsMandatory)
			)
			sequence(
				nonTerminal(type, QualifiedType)
				zeroOrOne(
					lookAhead(
						nonTerminal(Annotations)
						terminal(LBRACKET)
					)
					nonTerminal(arrayDims, ArrayDimsMandatory)
				)
			)
		)
	) */
	private int matchReferenceType(int lookahead) {
		lookahead = matchReferenceType_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			nonTerminal(primitiveType, PrimitiveType)
			nonTerminal(arrayDims, ArrayDimsMandatory)
		)
		sequence(
			nonTerminal(type, QualifiedType)
			zeroOrOne(
				lookAhead(
					nonTerminal(Annotations)
					terminal(LBRACKET)
				)
				nonTerminal(arrayDims, ArrayDimsMandatory)
			)
		)
	) */
	private int matchReferenceType_1(int lookahead) {
		int newLookahead;
		newLookahead = matchReferenceType_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchReferenceType_1_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		nonTerminal(primitiveType, PrimitiveType)
		nonTerminal(arrayDims, ArrayDimsMandatory)
	) */
	private int matchReferenceType_1_1(int lookahead) {
		lookahead = matchPrimitiveType(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchArrayDimsMandatory(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(type, QualifiedType)
		zeroOrOne(
			lookAhead(
				nonTerminal(Annotations)
				terminal(LBRACKET)
			)
			nonTerminal(arrayDims, ArrayDimsMandatory)
		)
	) */
	private int matchReferenceType_1_2(int lookahead) {
		lookahead = matchQualifiedType(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchReferenceType_1_2_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		lookAhead(
			nonTerminal(Annotations)
			terminal(LBRACKET)
		)
		nonTerminal(arrayDims, ArrayDimsMandatory)
	) */
	private int matchReferenceType_1_2_2(int lookahead) {
		int newLookahead;
		newLookahead = matchReferenceType_1_2_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		lookAhead(
			nonTerminal(Annotations)
			terminal(LBRACKET)
		)
		nonTerminal(arrayDims, ArrayDimsMandatory)
	) */
	private int matchReferenceType_1_2_2_1(int lookahead) {
		lookahead = matchArrayDimsMandatory(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(Annotations)
		terminal(LBRACKET)
	) */
	private int matchReferenceType_lookahead1(int lookahead) {
		lookahead = matchAnnotations(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.LBRACKET);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		action({
			if (annotations == null) {
				run();
				annotations = emptyList();
			}
		})
		nonTerminal(name, Name)
		zeroOrOne(
			lookAhead(2)
			nonTerminal(typeArgs, TypeArgumentsOrDiamond)
		)
		action({ ret = dress(SQualifiedType.make(annotations, scope, name, optionOf(typeArgs))); })
		zeroOrMore(
			lookAhead(2)
			action({ lateRun(); })
			terminal(DOT)
			action({ scope = optionOf(ret); })
			nonTerminal(annotations, Annotations)
			nonTerminal(name, Name)
			zeroOrOne(
				lookAhead(2)
				nonTerminal(typeArgs, TypeArgumentsOrDiamond)
			)
			action({ ret = dress(SQualifiedType.make(annotations, scope, name, optionOf(typeArgs))); })
		)
		action({ return ret; })
	) */
	protected BUTree<SQualifiedType> parseQualifiedType(BUTree<SNodeList> annotations) throws ParseException {
		BUTree<SNodeOption> scope = none();
		BUTree<SQualifiedType> ret;
		BUTree<SName> name;
		BUTree<SNodeList> typeArgs = null;
		if (annotations == null) {
			run();
			annotations = emptyList();
		}
		name = parseName();
		if (matchQualifiedType_lookahead1(0) != -1) {
			typeArgs = parseTypeArgumentsOrDiamond();
		}
		ret = dress(SQualifiedType.make(annotations, scope, name, optionOf(typeArgs)));
		while (matchQualifiedType_lookahead2(0) != -1) {
			lateRun();
			parse(TokenType.DOT);
			scope = optionOf(ret);
			annotations = parseAnnotations();
			name = parseName();
			if (matchQualifiedType_lookahead3(0) != -1) {
				typeArgs = parseTypeArgumentsOrDiamond();
			}
			ret = dress(SQualifiedType.make(annotations, scope, name, optionOf(typeArgs)));
		}
		return ret;
	}

	/* sequence(
		nonTerminal(name, Name)
		zeroOrOne(
			lookAhead(2)
			nonTerminal(typeArgs, TypeArgumentsOrDiamond)
		)
		zeroOrMore(
			lookAhead(2)
			terminal(DOT)
			nonTerminal(annotations, Annotations)
			nonTerminal(name, Name)
			zeroOrOne(
				lookAhead(2)
				nonTerminal(typeArgs, TypeArgumentsOrDiamond)
			)
		)
	) */
	private int matchQualifiedType(int lookahead) {
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchQualifiedType_3(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchQualifiedType_5(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		lookAhead(2)
		nonTerminal(typeArgs, TypeArgumentsOrDiamond)
	) */
	private int matchQualifiedType_3(int lookahead) {
		int newLookahead;
		newLookahead = matchQualifiedType_3_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		lookAhead(2)
		nonTerminal(typeArgs, TypeArgumentsOrDiamond)
	) */
	private int matchQualifiedType_3_1(int lookahead) {
		lookahead = matchTypeArgumentsOrDiamond(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		lookAhead(2)
		terminal(DOT)
		nonTerminal(annotations, Annotations)
		nonTerminal(name, Name)
		zeroOrOne(
			lookAhead(2)
			nonTerminal(typeArgs, TypeArgumentsOrDiamond)
		)
	) */
	private int matchQualifiedType_5(int lookahead) {
		int newLookahead;
		newLookahead = matchQualifiedType_5_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchQualifiedType_5_1(lookahead);
		}
		return lookahead;
	}

	/* sequence(
		lookAhead(2)
		terminal(DOT)
		nonTerminal(annotations, Annotations)
		nonTerminal(name, Name)
		zeroOrOne(
			lookAhead(2)
			nonTerminal(typeArgs, TypeArgumentsOrDiamond)
		)
	) */
	private int matchQualifiedType_5_1(int lookahead) {
		lookahead = match(lookahead, TokenType.DOT);
		if (lookahead == -1)
			return -1;
		lookahead = matchAnnotations(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchQualifiedType_5_1_7(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		lookAhead(2)
		nonTerminal(typeArgs, TypeArgumentsOrDiamond)
	) */
	private int matchQualifiedType_5_1_7(int lookahead) {
		int newLookahead;
		newLookahead = matchQualifiedType_5_1_7_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		lookAhead(2)
		nonTerminal(typeArgs, TypeArgumentsOrDiamond)
	) */
	private int matchQualifiedType_5_1_7_1(int lookahead) {
		lookahead = matchTypeArgumentsOrDiamond(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		lookAhead(2)
		nonTerminal(typeArgs, TypeArgumentsOrDiamond)
	) */
	private int matchQualifiedType_lookahead1(int lookahead) {
		if (match(0, TokenType.LT) != -1) {
			if (match(1, TokenType.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.HOOK) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.NODE_LIST_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.GT) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.INT) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.AT) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.LONG) != -1) {
				return lookahead;
			}
		}
		return -1;
	}

	/* zeroOrMore(
		lookAhead(2)
		terminal(DOT)
		nonTerminal(annotations, Annotations)
		nonTerminal(name, Name)
		zeroOrOne(
			lookAhead(2)
			nonTerminal(typeArgs, TypeArgumentsOrDiamond)
		)
	) */
	private int matchQualifiedType_lookahead2(int lookahead) {
		if (match(0, TokenType.DOT) != -1) {
			if (match(1, TokenType.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.AT) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		return -1;
	}

	/* zeroOrOne(
		lookAhead(2)
		nonTerminal(typeArgs, TypeArgumentsOrDiamond)
	) */
	private int matchQualifiedType_lookahead3(int lookahead) {
		if (match(0, TokenType.LT) != -1) {
			if (match(1, TokenType.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.HOOK) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.NODE_LIST_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.GT) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.INT) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.AT) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.LONG) != -1) {
				return lookahead;
			}
		}
		return -1;
	}

	/* sequence(
		terminal(LT)
		zeroOrOne(
			nonTerminal(ret, TypeArgumentList)
		)
		terminal(GT)
		action({ return ret; })
	) */
	protected BUTree<SNodeList> parseTypeArguments() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<? extends SType> type;
		parse(TokenType.LT);
		if (match(0, TokenType.AT, TokenType.DOUBLE, TokenType.FLOAT, TokenType.LONG, TokenType.INT, TokenType.SHORT, TokenType.BYTE, TokenType.CHAR, TokenType.BOOLEAN, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER, TokenType.HOOK, TokenType.NODE_LIST_VARIABLE) != -1) {
			ret = parseTypeArgumentList();
		}
		parse(TokenType.GT);
		return ret;
	}

	/* sequence(
		terminal(LT)
		zeroOrOne(
			nonTerminal(ret, TypeArgumentList)
		)
		terminal(GT)
	) */
	private int matchTypeArguments(int lookahead) {
		lookahead = match(lookahead, TokenType.LT);
		if (lookahead == -1)
			return -1;
		lookahead = matchTypeArguments_2(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.GT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		nonTerminal(ret, TypeArgumentList)
	) */
	private int matchTypeArguments_2(int lookahead) {
		int newLookahead;
		newLookahead = matchTypeArguments_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		nonTerminal(ret, TypeArgumentList)
	) */
	private int matchTypeArguments_2_1(int lookahead) {
		lookahead = matchTypeArgumentList(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(LT)
		zeroOrOne(
			nonTerminal(ret, TypeArgumentList)
		)
		terminal(GT)
		action({ return ret; })
	) */
	protected BUTree<SNodeList> parseTypeArgumentsOrDiamond() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<? extends SType> type;
		parse(TokenType.LT);
		if (match(0, TokenType.AT, TokenType.DOUBLE, TokenType.FLOAT, TokenType.LONG, TokenType.INT, TokenType.SHORT, TokenType.BYTE, TokenType.CHAR, TokenType.BOOLEAN, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER, TokenType.HOOK, TokenType.NODE_LIST_VARIABLE) != -1) {
			ret = parseTypeArgumentList();
		}
		parse(TokenType.GT);
		return ret;
	}

	/* sequence(
		terminal(LT)
		zeroOrOne(
			nonTerminal(ret, TypeArgumentList)
		)
		terminal(GT)
	) */
	private int matchTypeArgumentsOrDiamond(int lookahead) {
		lookahead = match(lookahead, TokenType.LT);
		if (lookahead == -1)
			return -1;
		lookahead = matchTypeArgumentsOrDiamond_2(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.GT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		nonTerminal(ret, TypeArgumentList)
	) */
	private int matchTypeArgumentsOrDiamond_2(int lookahead) {
		int newLookahead;
		newLookahead = matchTypeArgumentsOrDiamond_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		nonTerminal(ret, TypeArgumentList)
	) */
	private int matchTypeArgumentsOrDiamond_2_1(int lookahead) {
		lookahead = matchTypeArgumentList(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			nonTerminal(type, TypeArgument)
			action({ ret = append(ret, type); })
			zeroOrMore(
				terminal(COMMA)
				nonTerminal(type, TypeArgument)
				action({ ret = append(ret, type); })
			)
			action({ return ret; })
		)
		sequence(
			lookAhead({ quotesMode })
			terminal(id, NODE_LIST_VARIABLE)
			action({ return makeVar(id); })
		)
	) */
	protected BUTree<SNodeList> parseTypeArgumentList() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<? extends SType> type;
		Token id;
		if (match(0, TokenType.AT, TokenType.BOOLEAN, TokenType.CHAR, TokenType.BYTE, TokenType.SHORT, TokenType.INT, TokenType.LONG, TokenType.FLOAT, TokenType.DOUBLE, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER, TokenType.HOOK) != -1) {
			type = parseTypeArgument();
			ret = append(ret, type);
			while (match(0, TokenType.COMMA) != -1) {
				parse(TokenType.COMMA);
				type = parseTypeArgument();
				ret = append(ret, type);
			}
			return ret;
		} else if (quotesMode) {
			id = parse(TokenType.NODE_LIST_VARIABLE);
			return makeVar(id);
		} else {
			throw produceParseException(TokenType.NODE_LIST_VARIABLE, TokenType.AT, TokenType.IDENTIFIER, TokenType.NODE_VARIABLE, TokenType.CHAR, TokenType.BOOLEAN, TokenType.LONG, TokenType.INT, TokenType.SHORT, TokenType.BYTE, TokenType.DOUBLE, TokenType.FLOAT, TokenType.HOOK);
		}
	}

	/* choice(
		sequence(
			nonTerminal(type, TypeArgument)
			zeroOrMore(
				terminal(COMMA)
				nonTerminal(type, TypeArgument)
			)
		)
		sequence(
			lookAhead({ quotesMode })
			terminal(id, NODE_LIST_VARIABLE)
		)
	) */
	private int matchTypeArgumentList(int lookahead) {
		int newLookahead;
		newLookahead = matchTypeArgumentList_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchTypeArgumentList_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		nonTerminal(type, TypeArgument)
		zeroOrMore(
			terminal(COMMA)
			nonTerminal(type, TypeArgument)
		)
	) */
	private int matchTypeArgumentList_1(int lookahead) {
		lookahead = matchTypeArgument(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchTypeArgumentList_1_3(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		terminal(COMMA)
		nonTerminal(type, TypeArgument)
	) */
	private int matchTypeArgumentList_1_3(int lookahead) {
		int newLookahead;
		newLookahead = matchTypeArgumentList_1_3_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchTypeArgumentList_1_3_1(lookahead);
		}
		return lookahead;
	}

	/* sequence(
		terminal(COMMA)
		nonTerminal(type, TypeArgument)
	) */
	private int matchTypeArgumentList_1_3_1(int lookahead) {
		lookahead = match(lookahead, TokenType.COMMA);
		if (lookahead == -1)
			return -1;
		lookahead = matchTypeArgument(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead({ quotesMode })
		terminal(id, NODE_LIST_VARIABLE)
	) */
	private int matchTypeArgumentList_2(int lookahead) {
		lookahead = quotesMode ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.NODE_LIST_VARIABLE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		action({ run(); })
		nonTerminal(annotations, Annotations)
		choice(
			nonTerminal(ret, ReferenceType)
			nonTerminal(ret, Wildcard)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SType> parseTypeArgument() throws ParseException {
		BUTree<? extends SType> ret;
		BUTree<SNodeList> annotations = null;
		run();
		annotations = parseAnnotations();
		if (match(0, TokenType.SHORT, TokenType.BYTE, TokenType.CHAR, TokenType.BOOLEAN, TokenType.DOUBLE, TokenType.FLOAT, TokenType.LONG, TokenType.INT, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER) != -1) {
			ret = parseReferenceType(annotations);
		} else if (match(0, TokenType.HOOK) != -1) {
			ret = parseWildcard(annotations);
		} else {
			throw produceParseException(TokenType.HOOK, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER, TokenType.CHAR, TokenType.BOOLEAN, TokenType.SHORT, TokenType.BYTE, TokenType.LONG, TokenType.INT, TokenType.DOUBLE, TokenType.FLOAT);
		}
		return ret;
	}

	/* sequence(
		nonTerminal(annotations, Annotations)
		choice(
			nonTerminal(ret, ReferenceType)
			nonTerminal(ret, Wildcard)
		)
	) */
	private int matchTypeArgument(int lookahead) {
		lookahead = matchAnnotations(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchTypeArgument_3(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		nonTerminal(ret, ReferenceType)
		nonTerminal(ret, Wildcard)
	) */
	private int matchTypeArgument_3(int lookahead) {
		int newLookahead;
		newLookahead = matchReferenceType(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchWildcard(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		action({
			if (annotations == null) {
				run();
				annotations = emptyList();
			}
		})
		terminal(HOOK)
		zeroOrOne(
			choice(
				sequence(
					terminal(EXTENDS)
					action({ run(); })
					nonTerminal(boundAnnotations, Annotations)
					nonTerminal(ext, ReferenceType)
				)
				sequence(
					terminal(SUPER)
					action({ run(); })
					nonTerminal(boundAnnotations, Annotations)
					nonTerminal(sup, ReferenceType)
				)
			)
		)
		action({ return dress(SWildcardType.make(annotations, optionOf(ext), optionOf(sup))); })
	) */
	protected BUTree<SWildcardType> parseWildcard(BUTree<SNodeList> annotations) throws ParseException {
		BUTree<? extends SReferenceType> ext = null;
		BUTree<? extends SReferenceType> sup = null;
		BUTree<SNodeList> boundAnnotations = null;
		if (annotations == null) {
			run();
			annotations = emptyList();
		}
		parse(TokenType.HOOK);
		if (match(0, TokenType.EXTENDS, TokenType.SUPER) != -1) {
			if (match(0, TokenType.EXTENDS) != -1) {
				parse(TokenType.EXTENDS);
				run();
				boundAnnotations = parseAnnotations();
				ext = parseReferenceType(boundAnnotations);
			} else if (match(0, TokenType.SUPER) != -1) {
				parse(TokenType.SUPER);
				run();
				boundAnnotations = parseAnnotations();
				sup = parseReferenceType(boundAnnotations);
			} else {
				throw produceParseException(TokenType.SUPER, TokenType.EXTENDS);
			}
		}
		return dress(SWildcardType.make(annotations, optionOf(ext), optionOf(sup)));
	}

	/* sequence(
		terminal(HOOK)
		zeroOrOne(
			choice(
				sequence(
					terminal(EXTENDS)
					nonTerminal(boundAnnotations, Annotations)
					nonTerminal(ext, ReferenceType)
				)
				sequence(
					terminal(SUPER)
					nonTerminal(boundAnnotations, Annotations)
					nonTerminal(sup, ReferenceType)
				)
			)
		)
	) */
	private int matchWildcard(int lookahead) {
		lookahead = match(lookahead, TokenType.HOOK);
		if (lookahead == -1)
			return -1;
		lookahead = matchWildcard_3(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		choice(
			sequence(
				terminal(EXTENDS)
				nonTerminal(boundAnnotations, Annotations)
				nonTerminal(ext, ReferenceType)
			)
			sequence(
				terminal(SUPER)
				nonTerminal(boundAnnotations, Annotations)
				nonTerminal(sup, ReferenceType)
			)
		)
	) */
	private int matchWildcard_3(int lookahead) {
		int newLookahead;
		newLookahead = matchWildcard_3_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		choice(
			sequence(
				terminal(EXTENDS)
				nonTerminal(boundAnnotations, Annotations)
				nonTerminal(ext, ReferenceType)
			)
			sequence(
				terminal(SUPER)
				nonTerminal(boundAnnotations, Annotations)
				nonTerminal(sup, ReferenceType)
			)
		)
	) */
	private int matchWildcard_3_1(int lookahead) {
		lookahead = matchWildcard_3_1_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			terminal(EXTENDS)
			nonTerminal(boundAnnotations, Annotations)
			nonTerminal(ext, ReferenceType)
		)
		sequence(
			terminal(SUPER)
			nonTerminal(boundAnnotations, Annotations)
			nonTerminal(sup, ReferenceType)
		)
	) */
	private int matchWildcard_3_1_1(int lookahead) {
		int newLookahead;
		newLookahead = matchWildcard_3_1_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchWildcard_3_1_1_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		terminal(EXTENDS)
		nonTerminal(boundAnnotations, Annotations)
		nonTerminal(ext, ReferenceType)
	) */
	private int matchWildcard_3_1_1_1(int lookahead) {
		lookahead = match(lookahead, TokenType.EXTENDS);
		if (lookahead == -1)
			return -1;
		lookahead = matchAnnotations(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchReferenceType(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(SUPER)
		nonTerminal(boundAnnotations, Annotations)
		nonTerminal(sup, ReferenceType)
	) */
	private int matchWildcard_3_1_1_2(int lookahead) {
		lookahead = match(lookahead, TokenType.SUPER);
		if (lookahead == -1)
			return -1;
		lookahead = matchAnnotations(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchReferenceType(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		action({
			if (annotations == null) {
				run();
				annotations = emptyList();
			}
		})
		choice(
			sequence(
				terminal(BOOLEAN)
				action({ primitive = Primitive.Boolean; })
			)
			sequence(
				terminal(CHAR)
				action({ primitive = Primitive.Char; })
			)
			sequence(
				terminal(BYTE)
				action({ primitive = Primitive.Byte; })
			)
			sequence(
				terminal(SHORT)
				action({ primitive = Primitive.Short; })
			)
			sequence(
				terminal(INT)
				action({ primitive = Primitive.Int; })
			)
			sequence(
				terminal(LONG)
				action({ primitive = Primitive.Long; })
			)
			sequence(
				terminal(FLOAT)
				action({ primitive = Primitive.Float; })
			)
			sequence(
				terminal(DOUBLE)
				action({ primitive = Primitive.Double; })
			)
		)
		action({ return dress(SPrimitiveType.make(annotations, primitive)); })
	) */
	protected BUTree<SPrimitiveType> parsePrimitiveType(BUTree<SNodeList> annotations) throws ParseException {
		Primitive primitive;
		if (annotations == null) {
			run();
			annotations = emptyList();
		}
		if (match(0, TokenType.BOOLEAN) != -1) {
			parse(TokenType.BOOLEAN);
			primitive = Primitive.Boolean;
		} else if (match(0, TokenType.CHAR) != -1) {
			parse(TokenType.CHAR);
			primitive = Primitive.Char;
		} else if (match(0, TokenType.BYTE) != -1) {
			parse(TokenType.BYTE);
			primitive = Primitive.Byte;
		} else if (match(0, TokenType.SHORT) != -1) {
			parse(TokenType.SHORT);
			primitive = Primitive.Short;
		} else if (match(0, TokenType.INT) != -1) {
			parse(TokenType.INT);
			primitive = Primitive.Int;
		} else if (match(0, TokenType.LONG) != -1) {
			parse(TokenType.LONG);
			primitive = Primitive.Long;
		} else if (match(0, TokenType.FLOAT) != -1) {
			parse(TokenType.FLOAT);
			primitive = Primitive.Float;
		} else if (match(0, TokenType.DOUBLE) != -1) {
			parse(TokenType.DOUBLE);
			primitive = Primitive.Double;
		} else {
			throw produceParseException(TokenType.DOUBLE, TokenType.FLOAT, TokenType.LONG, TokenType.INT, TokenType.SHORT, TokenType.BYTE, TokenType.CHAR, TokenType.BOOLEAN);
		}
		return dress(SPrimitiveType.make(annotations, primitive));
	}

	/* sequence(
		choice(
			sequence(
				terminal(BOOLEAN)
			)
			sequence(
				terminal(CHAR)
			)
			sequence(
				terminal(BYTE)
			)
			sequence(
				terminal(SHORT)
			)
			sequence(
				terminal(INT)
			)
			sequence(
				terminal(LONG)
			)
			sequence(
				terminal(FLOAT)
			)
			sequence(
				terminal(DOUBLE)
			)
		)
	) */
	private int matchPrimitiveType(int lookahead) {
		lookahead = matchPrimitiveType_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			terminal(BOOLEAN)
		)
		sequence(
			terminal(CHAR)
		)
		sequence(
			terminal(BYTE)
		)
		sequence(
			terminal(SHORT)
		)
		sequence(
			terminal(INT)
		)
		sequence(
			terminal(LONG)
		)
		sequence(
			terminal(FLOAT)
		)
		sequence(
			terminal(DOUBLE)
		)
	) */
	private int matchPrimitiveType_2(int lookahead) {
		int newLookahead;
		newLookahead = matchPrimitiveType_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchPrimitiveType_2_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchPrimitiveType_2_3(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchPrimitiveType_2_4(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchPrimitiveType_2_5(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchPrimitiveType_2_6(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchPrimitiveType_2_7(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchPrimitiveType_2_8(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		terminal(BOOLEAN)
	) */
	private int matchPrimitiveType_2_1(int lookahead) {
		lookahead = match(lookahead, TokenType.BOOLEAN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(CHAR)
	) */
	private int matchPrimitiveType_2_2(int lookahead) {
		lookahead = match(lookahead, TokenType.CHAR);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(BYTE)
	) */
	private int matchPrimitiveType_2_3(int lookahead) {
		lookahead = match(lookahead, TokenType.BYTE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(SHORT)
	) */
	private int matchPrimitiveType_2_4(int lookahead) {
		lookahead = match(lookahead, TokenType.SHORT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(INT)
	) */
	private int matchPrimitiveType_2_5(int lookahead) {
		lookahead = match(lookahead, TokenType.INT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(LONG)
	) */
	private int matchPrimitiveType_2_6(int lookahead) {
		lookahead = match(lookahead, TokenType.LONG);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(FLOAT)
	) */
	private int matchPrimitiveType_2_7(int lookahead) {
		lookahead = match(lookahead, TokenType.FLOAT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(DOUBLE)
	) */
	private int matchPrimitiveType_2_8(int lookahead) {
		lookahead = match(lookahead, TokenType.DOUBLE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		choice(
			sequence(
				action({ run(); })
				terminal(VOID)
				action({ ret = dress(SVoidType.make()); })
			)
			nonTerminal(ret, Type)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SType> parseResultType() throws ParseException {
		BUTree<? extends SType> ret;
		if (match(0, TokenType.VOID) != -1) {
			run();
			parse(TokenType.VOID);
			ret = dress(SVoidType.make());
		} else if (match(0, TokenType.SHORT, TokenType.BYTE, TokenType.CHAR, TokenType.BOOLEAN, TokenType.DOUBLE, TokenType.FLOAT, TokenType.LONG, TokenType.INT, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER) != -1) {
			ret = parseType(null);
		} else {
			throw produceParseException(TokenType.NODE_VARIABLE, TokenType.IDENTIFIER, TokenType.CHAR, TokenType.BYTE, TokenType.BOOLEAN, TokenType.DOUBLE, TokenType.LONG, TokenType.FLOAT, TokenType.SHORT, TokenType.INT, TokenType.VOID);
		}
		return ret;
	}

	/* sequence(
		choice(
			sequence(
				terminal(VOID)
			)
			nonTerminal(ret, Type)
		)
	) */
	private int matchResultType(int lookahead) {
		lookahead = matchResultType_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			terminal(VOID)
		)
		nonTerminal(ret, Type)
	) */
	private int matchResultType_1(int lookahead) {
		int newLookahead;
		newLookahead = matchResultType_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchType(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		terminal(VOID)
	) */
	private int matchResultType_1_1(int lookahead) {
		lookahead = match(lookahead, TokenType.VOID);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		action({ run(); })
		nonTerminal(annotations, Annotations)
		nonTerminal(ret, QualifiedType)
		action({ return ret; })
	) */
	protected BUTree<SQualifiedType> parseAnnotatedQualifiedType() throws ParseException {
		BUTree<SNodeList> annotations;
		BUTree<SQualifiedType> ret;
		run();
		annotations = parseAnnotations();
		ret = parseQualifiedType(annotations);
		return ret;
	}

	/* sequence(
		nonTerminal(annotations, Annotations)
		nonTerminal(ret, QualifiedType)
	) */
	private int matchAnnotatedQualifiedType(int lookahead) {
		lookahead = matchAnnotations(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchQualifiedType(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		action({ run(); })
		nonTerminal(name, Name)
		action({ ret = dress(SQualifiedName.make(qualifier, name)); })
		zeroOrMore(
			lookAhead(2)
			action({ lateRun(); })
			terminal(DOT)
			action({ qualifier = optionOf(ret); })
			nonTerminal(name, Name)
			action({ ret = dress(SQualifiedName.make(qualifier, name)); })
		)
		action({ return ret; })
	) */
	protected BUTree<SQualifiedName> parseQualifiedName() throws ParseException {
		BUTree<SNodeOption> qualifier = none();
		BUTree<SQualifiedName> ret = null;
		BUTree<SName> name;
		run();
		name = parseName();
		ret = dress(SQualifiedName.make(qualifier, name));
		while (matchQualifiedName_lookahead1(0) != -1) {
			lateRun();
			parse(TokenType.DOT);
			qualifier = optionOf(ret);
			name = parseName();
			ret = dress(SQualifiedName.make(qualifier, name));
		}
		return ret;
	}

	/* sequence(
		nonTerminal(name, Name)
		zeroOrMore(
			lookAhead(2)
			terminal(DOT)
			nonTerminal(name, Name)
		)
	) */
	private int matchQualifiedName(int lookahead) {
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchQualifiedName_4(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		lookAhead(2)
		terminal(DOT)
		nonTerminal(name, Name)
	) */
	private int matchQualifiedName_4(int lookahead) {
		int newLookahead;
		newLookahead = matchQualifiedName_4_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchQualifiedName_4_1(lookahead);
		}
		return lookahead;
	}

	/* sequence(
		lookAhead(2)
		terminal(DOT)
		nonTerminal(name, Name)
	) */
	private int matchQualifiedName_4_1(int lookahead) {
		lookahead = match(lookahead, TokenType.DOT);
		if (lookahead == -1)
			return -1;
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		lookAhead(2)
		terminal(DOT)
		nonTerminal(name, Name)
	) */
	private int matchQualifiedName_lookahead1(int lookahead) {
		if (match(0, TokenType.DOT) != -1) {
			if (match(1, TokenType.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		return -1;
	}

	/* sequence(
		choice(
			sequence(
				action({ run(); })
				terminal(id, IDENTIFIER)
				action({ name = dress(SName.make(id.image)); })
			)
			sequence(
				lookAhead({ quotesMode })
				nonTerminal(name, NodeVar)
			)
		)
		action({ return name; })
	) */
	protected BUTree<SName> parseName() throws ParseException {
		Token id;
		BUTree<SName> name;
		if (match(0, TokenType.IDENTIFIER) != -1) {
			run();
			id = parse(TokenType.IDENTIFIER);
			name = dress(SName.make(id.image));
		} else if (quotesMode) {
			name = parseNodeVar();
		} else {
			throw produceParseException(TokenType.NODE_VARIABLE, TokenType.IDENTIFIER);
		}
		return name;
	}

	/* sequence(
		choice(
			sequence(
				terminal(id, IDENTIFIER)
			)
			sequence(
				lookAhead({ quotesMode })
				nonTerminal(name, NodeVar)
			)
		)
	) */
	private int matchName(int lookahead) {
		lookahead = matchName_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			terminal(id, IDENTIFIER)
		)
		sequence(
			lookAhead({ quotesMode })
			nonTerminal(name, NodeVar)
		)
	) */
	private int matchName_1(int lookahead) {
		int newLookahead;
		newLookahead = matchName_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchName_1_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		terminal(id, IDENTIFIER)
	) */
	private int matchName_1_1(int lookahead) {
		lookahead = match(lookahead, TokenType.IDENTIFIER);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead({ quotesMode })
		nonTerminal(name, NodeVar)
	) */
	private int matchName_1_2(int lookahead) {
		lookahead = quotesMode ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = matchNodeVar(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		choice(
			sequence(
				lookAhead(
					nonTerminal(Name)
					terminal(ARROW)
				)
				action({ run(); })
				nonTerminal(ret, Name)
				terminal(ARROW)
				nonTerminal(ret, LambdaBody)
			)
			sequence(
				lookAhead(
					terminal(LPAREN)
					terminal(RPAREN)
					terminal(ARROW)
				)
				action({ run(); })
				terminal(LPAREN)
				terminal(RPAREN)
				terminal(ARROW)
				nonTerminal(ret, LambdaBody)
			)
			sequence(
				lookAhead(
					terminal(LPAREN)
					nonTerminal(Name)
					terminal(RPAREN)
					terminal(ARROW)
				)
				action({ run(); })
				terminal(LPAREN)
				nonTerminal(ret, Name)
				terminal(RPAREN)
				terminal(ARROW)
				nonTerminal(ret, LambdaBody)
			)
			sequence(
				lookAhead(
					terminal(LPAREN)
					nonTerminal(Name)
					terminal(COMMA)
				)
				action({ run(); })
				terminal(LPAREN)
				nonTerminal(params, InferredFormalParameterList)
				terminal(RPAREN)
				terminal(ARROW)
				nonTerminal(ret, LambdaBody)
			)
			sequence(
				nonTerminal(ret, ConditionalExpression)
				zeroOrOne(
					action({ lateRun(); })
					nonTerminal(op, AssignmentOperator)
					nonTerminal(value, Expression)
					action({ ret = dress(SAssignExpr.make(ret, op, value)); })
				)
			)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parseExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		AssignOp op;
		BUTree<? extends SExpr> value;
		BUTree<SNodeList> params;
		if (matchExpression_lookahead1(0) != -1) {
			run();
			ret = parseName();
			parse(TokenType.ARROW);
			ret = parseLambdaBody(singletonList(makeFormalParameter((BUTree<SName>) ret)), false);
		} else if (matchExpression_lookahead2(0) != -1) {
			run();
			parse(TokenType.LPAREN);
			parse(TokenType.RPAREN);
			parse(TokenType.ARROW);
			ret = parseLambdaBody(emptyList(), true);
		} else if (matchExpression_lookahead3(0) != -1) {
			run();
			parse(TokenType.LPAREN);
			ret = parseName();
			parse(TokenType.RPAREN);
			parse(TokenType.ARROW);
			ret = parseLambdaBody(singletonList(makeFormalParameter((BUTree<SName>) ret)), true);
		} else if (matchExpression_lookahead4(0) != -1) {
			run();
			parse(TokenType.LPAREN);
			params = parseInferredFormalParameterList();
			parse(TokenType.RPAREN);
			parse(TokenType.ARROW);
			ret = parseLambdaBody(params, true);
		} else if (match(0, TokenType.LPAREN, TokenType.SUPER, TokenType.THIS, TokenType.DOUBLE_LITERAL, TokenType.FLOAT_LITERAL, TokenType.LONG_LITERAL, TokenType.INTEGER_LITERAL, TokenType.NULL, TokenType.FALSE, TokenType.TRUE, TokenType.STRING_LITERAL, TokenType.CHARACTER_LITERAL, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER, TokenType.LT, TokenType.VOID, TokenType.BYTE, TokenType.SHORT, TokenType.INT, TokenType.LONG, TokenType.FLOAT, TokenType.DOUBLE, TokenType.BOOLEAN, TokenType.CHAR, TokenType.NEW, TokenType.TILDE, TokenType.BANG, TokenType.MINUS, TokenType.PLUS, TokenType.INCR, TokenType.DECR) != -1) {
			ret = parseConditionalExpression();
			if (match(0, TokenType.ORASSIGN, TokenType.XORASSIGN, TokenType.ANDASSIGN, TokenType.RUNSIGNEDSHIFTASSIGN, TokenType.RSIGNEDSHIFTASSIGN, TokenType.LSHIFTASSIGN, TokenType.MINUSASSIGN, TokenType.PLUSASSIGN, TokenType.REMASSIGN, TokenType.SLASHASSIGN, TokenType.STARASSIGN, TokenType.ASSIGN) != -1) {
				lateRun();
				op = parseAssignmentOperator();
				value = parseExpression();
				ret = dress(SAssignExpr.make(ret, op, value));
			}
		} else {
			throw produceParseException(TokenType.BANG, TokenType.TILDE, TokenType.LPAREN, TokenType.DOUBLE_LITERAL, TokenType.FLOAT_LITERAL, TokenType.STRING_LITERAL, TokenType.CHARACTER_LITERAL, TokenType.FALSE, TokenType.TRUE, TokenType.NULL, TokenType.LONG_LITERAL, TokenType.INTEGER_LITERAL, TokenType.IDENTIFIER, TokenType.NODE_VARIABLE, TokenType.BOOLEAN, TokenType.CHAR, TokenType.BYTE, TokenType.SHORT, TokenType.INT, TokenType.LONG, TokenType.FLOAT, TokenType.DOUBLE, TokenType.VOID, TokenType.LT, TokenType.THIS, TokenType.SUPER, TokenType.NEW, TokenType.PLUS, TokenType.MINUS, TokenType.INCR, TokenType.DECR);
		}
		return ret;
	}

	/* sequence(
		choice(
			sequence(
				lookAhead(
					nonTerminal(Name)
					terminal(ARROW)
				)
				nonTerminal(ret, Name)
				terminal(ARROW)
				nonTerminal(ret, LambdaBody)
			)
			sequence(
				lookAhead(
					terminal(LPAREN)
					terminal(RPAREN)
					terminal(ARROW)
				)
				terminal(LPAREN)
				terminal(RPAREN)
				terminal(ARROW)
				nonTerminal(ret, LambdaBody)
			)
			sequence(
				lookAhead(
					terminal(LPAREN)
					nonTerminal(Name)
					terminal(RPAREN)
					terminal(ARROW)
				)
				terminal(LPAREN)
				nonTerminal(ret, Name)
				terminal(RPAREN)
				terminal(ARROW)
				nonTerminal(ret, LambdaBody)
			)
			sequence(
				lookAhead(
					terminal(LPAREN)
					nonTerminal(Name)
					terminal(COMMA)
				)
				terminal(LPAREN)
				nonTerminal(params, InferredFormalParameterList)
				terminal(RPAREN)
				terminal(ARROW)
				nonTerminal(ret, LambdaBody)
			)
			sequence(
				nonTerminal(ret, ConditionalExpression)
				zeroOrOne(
					nonTerminal(op, AssignmentOperator)
					nonTerminal(value, Expression)
				)
			)
		)
	) */
	private int matchExpression(int lookahead) {
		lookahead = matchExpression_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			lookAhead(
				nonTerminal(Name)
				terminal(ARROW)
			)
			nonTerminal(ret, Name)
			terminal(ARROW)
			nonTerminal(ret, LambdaBody)
		)
		sequence(
			lookAhead(
				terminal(LPAREN)
				terminal(RPAREN)
				terminal(ARROW)
			)
			terminal(LPAREN)
			terminal(RPAREN)
			terminal(ARROW)
			nonTerminal(ret, LambdaBody)
		)
		sequence(
			lookAhead(
				terminal(LPAREN)
				nonTerminal(Name)
				terminal(RPAREN)
				terminal(ARROW)
			)
			terminal(LPAREN)
			nonTerminal(ret, Name)
			terminal(RPAREN)
			terminal(ARROW)
			nonTerminal(ret, LambdaBody)
		)
		sequence(
			lookAhead(
				terminal(LPAREN)
				nonTerminal(Name)
				terminal(COMMA)
			)
			terminal(LPAREN)
			nonTerminal(params, InferredFormalParameterList)
			terminal(RPAREN)
			terminal(ARROW)
			nonTerminal(ret, LambdaBody)
		)
		sequence(
			nonTerminal(ret, ConditionalExpression)
			zeroOrOne(
				nonTerminal(op, AssignmentOperator)
				nonTerminal(value, Expression)
			)
		)
	) */
	private int matchExpression_1(int lookahead) {
		int newLookahead;
		newLookahead = matchExpression_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchExpression_1_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchExpression_1_3(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchExpression_1_4(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchExpression_1_5(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		lookAhead(
			nonTerminal(Name)
			terminal(ARROW)
		)
		nonTerminal(ret, Name)
		terminal(ARROW)
		nonTerminal(ret, LambdaBody)
	) */
	private int matchExpression_1_1(int lookahead) {
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.ARROW);
		if (lookahead == -1)
			return -1;
		lookahead = matchLambdaBody(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead(
			terminal(LPAREN)
			terminal(RPAREN)
			terminal(ARROW)
		)
		terminal(LPAREN)
		terminal(RPAREN)
		terminal(ARROW)
		nonTerminal(ret, LambdaBody)
	) */
	private int matchExpression_1_2(int lookahead) {
		lookahead = match(lookahead, TokenType.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.ARROW);
		if (lookahead == -1)
			return -1;
		lookahead = matchLambdaBody(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead(
			terminal(LPAREN)
			nonTerminal(Name)
			terminal(RPAREN)
			terminal(ARROW)
		)
		terminal(LPAREN)
		nonTerminal(ret, Name)
		terminal(RPAREN)
		terminal(ARROW)
		nonTerminal(ret, LambdaBody)
	) */
	private int matchExpression_1_3(int lookahead) {
		lookahead = match(lookahead, TokenType.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.ARROW);
		if (lookahead == -1)
			return -1;
		lookahead = matchLambdaBody(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead(
			terminal(LPAREN)
			nonTerminal(Name)
			terminal(COMMA)
		)
		terminal(LPAREN)
		nonTerminal(params, InferredFormalParameterList)
		terminal(RPAREN)
		terminal(ARROW)
		nonTerminal(ret, LambdaBody)
	) */
	private int matchExpression_1_4(int lookahead) {
		lookahead = match(lookahead, TokenType.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchInferredFormalParameterList(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.ARROW);
		if (lookahead == -1)
			return -1;
		lookahead = matchLambdaBody(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(ret, ConditionalExpression)
		zeroOrOne(
			nonTerminal(op, AssignmentOperator)
			nonTerminal(value, Expression)
		)
	) */
	private int matchExpression_1_5(int lookahead) {
		lookahead = matchConditionalExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpression_1_5_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		nonTerminal(op, AssignmentOperator)
		nonTerminal(value, Expression)
	) */
	private int matchExpression_1_5_2(int lookahead) {
		int newLookahead;
		newLookahead = matchExpression_1_5_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		nonTerminal(op, AssignmentOperator)
		nonTerminal(value, Expression)
	) */
	private int matchExpression_1_5_2_1(int lookahead) {
		lookahead = matchAssignmentOperator(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(Name)
		terminal(ARROW)
	) */
	private int matchExpression_lookahead1(int lookahead) {
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.ARROW);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(LPAREN)
		terminal(RPAREN)
		terminal(ARROW)
	) */
	private int matchExpression_lookahead2(int lookahead) {
		lookahead = match(lookahead, TokenType.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.ARROW);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(LPAREN)
		nonTerminal(Name)
		terminal(RPAREN)
		terminal(ARROW)
	) */
	private int matchExpression_lookahead3(int lookahead) {
		lookahead = match(lookahead, TokenType.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.ARROW);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(LPAREN)
		nonTerminal(Name)
		terminal(COMMA)
	) */
	private int matchExpression_lookahead4(int lookahead) {
		lookahead = match(lookahead, TokenType.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.COMMA);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		choice(
			sequence(
				nonTerminal(expr, Expression)
				action({ ret = dress(SLambdaExpr.make(parameters, parenthesis, left(expr))); })
			)
			sequence(
				nonTerminal(block, Block)
				action({ ret = dress(SLambdaExpr.make(parameters, parenthesis, right(block))); })
			)
		)
		action({ return ret; })
	) */
	protected BUTree<SLambdaExpr> parseLambdaBody(BUTree<SNodeList> parameters, boolean parenthesis) throws ParseException {
		BUTree<SBlockStmt> block;
		BUTree<? extends SExpr> expr;
		BUTree<SLambdaExpr> ret;
		if (match(0, TokenType.PLUS, TokenType.MINUS, TokenType.TILDE, TokenType.BANG, TokenType.LPAREN, TokenType.IDENTIFIER, TokenType.NODE_VARIABLE, TokenType.BOOLEAN, TokenType.INT, TokenType.SHORT, TokenType.BYTE, TokenType.CHAR, TokenType.DOUBLE, TokenType.FLOAT, TokenType.LONG, TokenType.VOID, TokenType.NEW, TokenType.SUPER, TokenType.THIS, TokenType.LT, TokenType.CHARACTER_LITERAL, TokenType.STRING_LITERAL, TokenType.FLOAT_LITERAL, TokenType.DOUBLE_LITERAL, TokenType.NULL, TokenType.TRUE, TokenType.FALSE, TokenType.INTEGER_LITERAL, TokenType.LONG_LITERAL, TokenType.INCR, TokenType.DECR) != -1) {
			expr = parseExpression();
			ret = dress(SLambdaExpr.make(parameters, parenthesis, left(expr)));
		} else if (match(0, TokenType.LBRACE) != -1) {
			block = parseBlock();
			ret = dress(SLambdaExpr.make(parameters, parenthesis, right(block)));
		} else {
			throw produceParseException(TokenType.LBRACE, TokenType.LPAREN, TokenType.DECR, TokenType.INCR, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER, TokenType.DOUBLE, TokenType.BYTE, TokenType.CHAR, TokenType.BOOLEAN, TokenType.FLOAT, TokenType.LONG, TokenType.INT, TokenType.SHORT, TokenType.VOID, TokenType.NEW, TokenType.LT, TokenType.NULL, TokenType.FLOAT_LITERAL, TokenType.DOUBLE_LITERAL, TokenType.INTEGER_LITERAL, TokenType.LONG_LITERAL, TokenType.TRUE, TokenType.FALSE, TokenType.CHARACTER_LITERAL, TokenType.STRING_LITERAL, TokenType.SUPER, TokenType.THIS, TokenType.TILDE, TokenType.BANG, TokenType.MINUS, TokenType.PLUS);
		}
		return ret;
	}

	/* sequence(
		choice(
			sequence(
				nonTerminal(expr, Expression)
			)
			sequence(
				nonTerminal(block, Block)
			)
		)
	) */
	private int matchLambdaBody(int lookahead) {
		lookahead = matchLambdaBody_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			nonTerminal(expr, Expression)
		)
		sequence(
			nonTerminal(block, Block)
		)
	) */
	private int matchLambdaBody_1(int lookahead) {
		int newLookahead;
		newLookahead = matchLambdaBody_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchLambdaBody_1_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		nonTerminal(expr, Expression)
	) */
	private int matchLambdaBody_1_1(int lookahead) {
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(block, Block)
	) */
	private int matchLambdaBody_1_2(int lookahead) {
		lookahead = matchBlock(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(param, InferredFormalParameter)
		action({ ret = append(ret, param); })
		zeroOrMore(
			terminal(COMMA)
			nonTerminal(param, InferredFormalParameter)
			action({ ret = append(ret, param); })
		)
		action({ return ret; })
	) */
	protected BUTree<SNodeList> parseInferredFormalParameterList() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<SFormalParameter> param;
		param = parseInferredFormalParameter();
		ret = append(ret, param);
		while (match(0, TokenType.COMMA) != -1) {
			parse(TokenType.COMMA);
			param = parseInferredFormalParameter();
			ret = append(ret, param);
		}
		return ret;
	}

	/* sequence(
		nonTerminal(param, InferredFormalParameter)
		zeroOrMore(
			terminal(COMMA)
			nonTerminal(param, InferredFormalParameter)
		)
	) */
	private int matchInferredFormalParameterList(int lookahead) {
		lookahead = matchInferredFormalParameter(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchInferredFormalParameterList_3(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		terminal(COMMA)
		nonTerminal(param, InferredFormalParameter)
	) */
	private int matchInferredFormalParameterList_3(int lookahead) {
		int newLookahead;
		newLookahead = matchInferredFormalParameterList_3_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchInferredFormalParameterList_3_1(lookahead);
		}
		return lookahead;
	}

	/* sequence(
		terminal(COMMA)
		nonTerminal(param, InferredFormalParameter)
	) */
	private int matchInferredFormalParameterList_3_1(int lookahead) {
		lookahead = match(lookahead, TokenType.COMMA);
		if (lookahead == -1)
			return -1;
		lookahead = matchInferredFormalParameter(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(name, Name)
		action({ return makeFormalParameter(name); })
	) */
	protected BUTree<SFormalParameter> parseInferredFormalParameter() throws ParseException {
		BUTree<SName> name;
		name = parseName();
		return makeFormalParameter(name);
	}

	/* sequence(
		nonTerminal(name, Name)
	) */
	private int matchInferredFormalParameter(int lookahead) {
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		choice(
			sequence(
				terminal(ASSIGN)
				action({ ret = AssignOp.Normal; })
			)
			sequence(
				terminal(STARASSIGN)
				action({ ret = AssignOp.Times; })
			)
			sequence(
				terminal(SLASHASSIGN)
				action({ ret = AssignOp.Divide; })
			)
			sequence(
				terminal(REMASSIGN)
				action({ ret = AssignOp.Remainder; })
			)
			sequence(
				terminal(PLUSASSIGN)
				action({ ret = AssignOp.Plus; })
			)
			sequence(
				terminal(MINUSASSIGN)
				action({ ret = AssignOp.Minus; })
			)
			sequence(
				terminal(LSHIFTASSIGN)
				action({ ret = AssignOp.LeftShift; })
			)
			sequence(
				terminal(RSIGNEDSHIFTASSIGN)
				action({ ret = AssignOp.RightSignedShift; })
			)
			sequence(
				terminal(RUNSIGNEDSHIFTASSIGN)
				action({ ret = AssignOp.RightUnsignedShift; })
			)
			sequence(
				terminal(ANDASSIGN)
				action({ ret = AssignOp.And; })
			)
			sequence(
				terminal(XORASSIGN)
				action({ ret = AssignOp.XOr; })
			)
			sequence(
				terminal(ORASSIGN)
				action({ ret = AssignOp.Or; })
			)
		)
		action({ return ret; })
	) */
	protected AssignOp parseAssignmentOperator() throws ParseException {
		AssignOp ret;
		if (match(0, TokenType.ASSIGN) != -1) {
			parse(TokenType.ASSIGN);
			ret = AssignOp.Normal;
		} else if (match(0, TokenType.STARASSIGN) != -1) {
			parse(TokenType.STARASSIGN);
			ret = AssignOp.Times;
		} else if (match(0, TokenType.SLASHASSIGN) != -1) {
			parse(TokenType.SLASHASSIGN);
			ret = AssignOp.Divide;
		} else if (match(0, TokenType.REMASSIGN) != -1) {
			parse(TokenType.REMASSIGN);
			ret = AssignOp.Remainder;
		} else if (match(0, TokenType.PLUSASSIGN) != -1) {
			parse(TokenType.PLUSASSIGN);
			ret = AssignOp.Plus;
		} else if (match(0, TokenType.MINUSASSIGN) != -1) {
			parse(TokenType.MINUSASSIGN);
			ret = AssignOp.Minus;
		} else if (match(0, TokenType.LSHIFTASSIGN) != -1) {
			parse(TokenType.LSHIFTASSIGN);
			ret = AssignOp.LeftShift;
		} else if (match(0, TokenType.RSIGNEDSHIFTASSIGN) != -1) {
			parse(TokenType.RSIGNEDSHIFTASSIGN);
			ret = AssignOp.RightSignedShift;
		} else if (match(0, TokenType.RUNSIGNEDSHIFTASSIGN) != -1) {
			parse(TokenType.RUNSIGNEDSHIFTASSIGN);
			ret = AssignOp.RightUnsignedShift;
		} else if (match(0, TokenType.ANDASSIGN) != -1) {
			parse(TokenType.ANDASSIGN);
			ret = AssignOp.And;
		} else if (match(0, TokenType.XORASSIGN) != -1) {
			parse(TokenType.XORASSIGN);
			ret = AssignOp.XOr;
		} else if (match(0, TokenType.ORASSIGN) != -1) {
			parse(TokenType.ORASSIGN);
			ret = AssignOp.Or;
		} else {
			throw produceParseException(TokenType.ORASSIGN, TokenType.XORASSIGN, TokenType.ANDASSIGN, TokenType.RUNSIGNEDSHIFTASSIGN, TokenType.RSIGNEDSHIFTASSIGN, TokenType.LSHIFTASSIGN, TokenType.MINUSASSIGN, TokenType.PLUSASSIGN, TokenType.REMASSIGN, TokenType.SLASHASSIGN, TokenType.STARASSIGN, TokenType.ASSIGN);
		}
		return ret;
	}

	/* sequence(
		choice(
			sequence(
				terminal(ASSIGN)
			)
			sequence(
				terminal(STARASSIGN)
			)
			sequence(
				terminal(SLASHASSIGN)
			)
			sequence(
				terminal(REMASSIGN)
			)
			sequence(
				terminal(PLUSASSIGN)
			)
			sequence(
				terminal(MINUSASSIGN)
			)
			sequence(
				terminal(LSHIFTASSIGN)
			)
			sequence(
				terminal(RSIGNEDSHIFTASSIGN)
			)
			sequence(
				terminal(RUNSIGNEDSHIFTASSIGN)
			)
			sequence(
				terminal(ANDASSIGN)
			)
			sequence(
				terminal(XORASSIGN)
			)
			sequence(
				terminal(ORASSIGN)
			)
		)
	) */
	private int matchAssignmentOperator(int lookahead) {
		lookahead = matchAssignmentOperator_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			terminal(ASSIGN)
		)
		sequence(
			terminal(STARASSIGN)
		)
		sequence(
			terminal(SLASHASSIGN)
		)
		sequence(
			terminal(REMASSIGN)
		)
		sequence(
			terminal(PLUSASSIGN)
		)
		sequence(
			terminal(MINUSASSIGN)
		)
		sequence(
			terminal(LSHIFTASSIGN)
		)
		sequence(
			terminal(RSIGNEDSHIFTASSIGN)
		)
		sequence(
			terminal(RUNSIGNEDSHIFTASSIGN)
		)
		sequence(
			terminal(ANDASSIGN)
		)
		sequence(
			terminal(XORASSIGN)
		)
		sequence(
			terminal(ORASSIGN)
		)
	) */
	private int matchAssignmentOperator_1(int lookahead) {
		int newLookahead;
		newLookahead = matchAssignmentOperator_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchAssignmentOperator_1_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchAssignmentOperator_1_3(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchAssignmentOperator_1_4(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchAssignmentOperator_1_5(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchAssignmentOperator_1_6(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchAssignmentOperator_1_7(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchAssignmentOperator_1_8(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchAssignmentOperator_1_9(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchAssignmentOperator_1_10(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchAssignmentOperator_1_11(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchAssignmentOperator_1_12(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		terminal(ASSIGN)
	) */
	private int matchAssignmentOperator_1_1(int lookahead) {
		lookahead = match(lookahead, TokenType.ASSIGN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(ANDASSIGN)
	) */
	private int matchAssignmentOperator_1_10(int lookahead) {
		lookahead = match(lookahead, TokenType.ANDASSIGN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(XORASSIGN)
	) */
	private int matchAssignmentOperator_1_11(int lookahead) {
		lookahead = match(lookahead, TokenType.XORASSIGN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(ORASSIGN)
	) */
	private int matchAssignmentOperator_1_12(int lookahead) {
		lookahead = match(lookahead, TokenType.ORASSIGN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(STARASSIGN)
	) */
	private int matchAssignmentOperator_1_2(int lookahead) {
		lookahead = match(lookahead, TokenType.STARASSIGN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(SLASHASSIGN)
	) */
	private int matchAssignmentOperator_1_3(int lookahead) {
		lookahead = match(lookahead, TokenType.SLASHASSIGN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(REMASSIGN)
	) */
	private int matchAssignmentOperator_1_4(int lookahead) {
		lookahead = match(lookahead, TokenType.REMASSIGN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(PLUSASSIGN)
	) */
	private int matchAssignmentOperator_1_5(int lookahead) {
		lookahead = match(lookahead, TokenType.PLUSASSIGN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(MINUSASSIGN)
	) */
	private int matchAssignmentOperator_1_6(int lookahead) {
		lookahead = match(lookahead, TokenType.MINUSASSIGN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(LSHIFTASSIGN)
	) */
	private int matchAssignmentOperator_1_7(int lookahead) {
		lookahead = match(lookahead, TokenType.LSHIFTASSIGN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(RSIGNEDSHIFTASSIGN)
	) */
	private int matchAssignmentOperator_1_8(int lookahead) {
		lookahead = match(lookahead, TokenType.RSIGNEDSHIFTASSIGN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(RUNSIGNEDSHIFTASSIGN)
	) */
	private int matchAssignmentOperator_1_9(int lookahead) {
		lookahead = match(lookahead, TokenType.RUNSIGNEDSHIFTASSIGN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(ret, BinaryExpression)
		zeroOrOne(
			action({ lateRun(); })
			terminal(HOOK)
			nonTerminal(left, Expression)
			terminal(COLON)
			nonTerminal(right, ConditionalExpression)
			action({ ret = dress(SConditionalExpr.make(ret, left, right)); })
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parseConditionalExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> left;
		BUTree<? extends SExpr> right;
		ret = parseBinaryExpression(1);
		if (match(0, TokenType.HOOK) != -1) {
			lateRun();
			parse(TokenType.HOOK);
			left = parseExpression();
			parse(TokenType.COLON);
			right = parseConditionalExpression();
			ret = dress(SConditionalExpr.make(ret, left, right));
		}
		return ret;
	}

	/* sequence(
		nonTerminal(ret, BinaryExpression)
		zeroOrOne(
			terminal(HOOK)
			nonTerminal(left, Expression)
			terminal(COLON)
			nonTerminal(right, ConditionalExpression)
		)
	) */
	private int matchConditionalExpression(int lookahead) {
		lookahead = matchBinaryExpression(lookahead, 1);
		if (lookahead == -1)
			return -1;
		lookahead = matchConditionalExpression_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		terminal(HOOK)
		nonTerminal(left, Expression)
		terminal(COLON)
		nonTerminal(right, ConditionalExpression)
	) */
	private int matchConditionalExpression_2(int lookahead) {
		int newLookahead;
		newLookahead = matchConditionalExpression_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		terminal(HOOK)
		nonTerminal(left, Expression)
		terminal(COLON)
		nonTerminal(right, ConditionalExpression)
	) */
	private int matchConditionalExpression_2_1(int lookahead) {
		lookahead = match(lookahead, TokenType.HOOK);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.COLON);
		if (lookahead == -1)
			return -1;
		lookahead = matchConditionalExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(ret, UnaryExpression)
		zeroOrMore(
			lookAhead(
				choice(
					sequence(
						lookAhead({ precedenceForInstanceOf() >= minPrecedence })
						terminal(INSTANCEOF)
					)
					nonTerminal(op, BinaryOperator)
				)
			)
			action({ lateRun(); })
			choice(
				sequence(
					lookAhead({ precedenceForInstanceOf() >= minPrecedence })
					terminal(INSTANCEOF)
					action({ run(); })
					nonTerminal(annotations, Annotations)
					nonTerminal(type, Type)
					action({ ret = dress(SInstanceOfExpr.make(ret, type)); })
				)
				sequence(
					lookAhead(
						nonTerminal(op, BinaryOperator)
					)
					nonTerminal(op, BinaryOperator)
					action({
						int oldPrecedence = minPrecedence;
						minPrecedence = precedenceFor(op) + (leftAssociative(op) ? 1 : 0);
					})
					nonTerminal(rhs, BinaryExpression)
					action({ minPrecedence = oldPrecedence; })
					action({ ret = dress(SBinaryExpr.make(ret, op, rhs)); })
				)
			)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parseBinaryExpression(int minPrecedence) throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> rhs;
		BinaryOp op;
		BUTree<SNodeList> annotations;
		BUTree<? extends SType> type;
		ret = parseUnaryExpression();
		while (matchBinaryExpression_lookahead1(0, minPrecedence) != -1) {
			lateRun();
			if (precedenceForInstanceOf() >= minPrecedence && matchBinaryExpression_lookahead2(0, minPrecedence) != -1) {
				parse(TokenType.INSTANCEOF);
				run();
				annotations = parseAnnotations();
				type = parseType(annotations);
				ret = dress(SInstanceOfExpr.make(ret, type));
			} else if (matchBinaryExpression_lookahead3(0, minPrecedence) != -1) {
				op = parseBinaryOperator(minPrecedence);
				int oldPrecedence = minPrecedence;
				minPrecedence = precedenceFor(op) + (leftAssociative(op) ? 1 : 0);
				rhs = parseBinaryExpression(minPrecedence);
				minPrecedence = oldPrecedence;
				ret = dress(SBinaryExpr.make(ret, op, rhs));
			} else {
				throw produceParseException(TokenType.SLASH, TokenType.REM, TokenType.SC_OR, TokenType.BIT_AND, TokenType.XOR, TokenType.SC_AND, TokenType.BIT_OR, TokenType.LT, TokenType.GT, TokenType.EQ, TokenType.NE, TokenType.LE, TokenType.GE, TokenType.MINUS, TokenType.STAR, TokenType.LSHIFT, TokenType.PLUS, TokenType.INSTANCEOF);
			}
		}
		return ret;
	}

	/* sequence(
		nonTerminal(ret, UnaryExpression)
		zeroOrMore(
			lookAhead(
				choice(
					sequence(
						lookAhead({ precedenceForInstanceOf() >= minPrecedence })
						terminal(INSTANCEOF)
					)
					nonTerminal(op, BinaryOperator)
				)
			)
			choice(
				sequence(
					lookAhead({ precedenceForInstanceOf() >= minPrecedence })
					terminal(INSTANCEOF)
					nonTerminal(annotations, Annotations)
					nonTerminal(type, Type)
				)
				sequence(
					lookAhead(
						nonTerminal(op, BinaryOperator)
					)
					nonTerminal(op, BinaryOperator)
					nonTerminal(rhs, BinaryExpression)
				)
			)
		)
	) */
	private int matchBinaryExpression(int lookahead, int minPrecedence) {
		lookahead = matchUnaryExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchBinaryExpression_2(lookahead, minPrecedence);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		lookAhead(
			choice(
				sequence(
					lookAhead({ precedenceForInstanceOf() >= minPrecedence })
					terminal(INSTANCEOF)
				)
				nonTerminal(op, BinaryOperator)
			)
		)
		choice(
			sequence(
				lookAhead({ precedenceForInstanceOf() >= minPrecedence })
				terminal(INSTANCEOF)
				nonTerminal(annotations, Annotations)
				nonTerminal(type, Type)
			)
			sequence(
				lookAhead(
					nonTerminal(op, BinaryOperator)
				)
				nonTerminal(op, BinaryOperator)
				nonTerminal(rhs, BinaryExpression)
			)
		)
	) */
	private int matchBinaryExpression_2(int lookahead, int minPrecedence) {
		int newLookahead;
		newLookahead = matchBinaryExpression_2_1(lookahead, minPrecedence);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchBinaryExpression_2_1(lookahead, minPrecedence);
		}
		return lookahead;
	}

	/* sequence(
		lookAhead(
			choice(
				sequence(
					lookAhead({ precedenceForInstanceOf() >= minPrecedence })
					terminal(INSTANCEOF)
				)
				nonTerminal(op, BinaryOperator)
			)
		)
		choice(
			sequence(
				lookAhead({ precedenceForInstanceOf() >= minPrecedence })
				terminal(INSTANCEOF)
				nonTerminal(annotations, Annotations)
				nonTerminal(type, Type)
			)
			sequence(
				lookAhead(
					nonTerminal(op, BinaryOperator)
				)
				nonTerminal(op, BinaryOperator)
				nonTerminal(rhs, BinaryExpression)
			)
		)
	) */
	private int matchBinaryExpression_2_1(int lookahead, int minPrecedence) {
		lookahead = matchBinaryExpression_2_1_3(lookahead, minPrecedence);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			lookAhead({ precedenceForInstanceOf() >= minPrecedence })
			terminal(INSTANCEOF)
			nonTerminal(annotations, Annotations)
			nonTerminal(type, Type)
		)
		sequence(
			lookAhead(
				nonTerminal(op, BinaryOperator)
			)
			nonTerminal(op, BinaryOperator)
			nonTerminal(rhs, BinaryExpression)
		)
	) */
	private int matchBinaryExpression_2_1_3(int lookahead, int minPrecedence) {
		int newLookahead;
		newLookahead = matchBinaryExpression_2_1_3_1(lookahead, minPrecedence);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchBinaryExpression_2_1_3_2(lookahead, minPrecedence);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		lookAhead({ precedenceForInstanceOf() >= minPrecedence })
		terminal(INSTANCEOF)
		nonTerminal(annotations, Annotations)
		nonTerminal(type, Type)
	) */
	private int matchBinaryExpression_2_1_3_1(int lookahead, int minPrecedence) {
		lookahead = precedenceForInstanceOf() >= minPrecedence ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.INSTANCEOF);
		if (lookahead == -1)
			return -1;
		lookahead = matchAnnotations(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchType(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead(
			nonTerminal(op, BinaryOperator)
		)
		nonTerminal(op, BinaryOperator)
		nonTerminal(rhs, BinaryExpression)
	) */
	private int matchBinaryExpression_2_1_3_2(int lookahead, int minPrecedence) {
		lookahead = matchBinaryOperator(lookahead, minPrecedence);
		if (lookahead == -1)
			return -1;
		lookahead = matchBinaryExpression(lookahead, minPrecedence);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		choice(
			sequence(
				lookAhead({ precedenceForInstanceOf() >= minPrecedence })
				terminal(INSTANCEOF)
			)
			nonTerminal(op, BinaryOperator)
		)
	) */
	private int matchBinaryExpression_lookahead1(int lookahead, int minPrecedence) {
		lookahead = matchBinaryExpression_lookahead1_1(lookahead, minPrecedence);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			lookAhead({ precedenceForInstanceOf() >= minPrecedence })
			terminal(INSTANCEOF)
		)
		nonTerminal(op, BinaryOperator)
	) */
	private int matchBinaryExpression_lookahead1_1(int lookahead, int minPrecedence) {
		int newLookahead;
		newLookahead = matchBinaryExpression_lookahead1_1_1(lookahead, minPrecedence);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchBinaryOperator(lookahead, minPrecedence);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		lookAhead({ precedenceForInstanceOf() >= minPrecedence })
		terminal(INSTANCEOF)
	) */
	private int matchBinaryExpression_lookahead1_1_1(int lookahead, int minPrecedence) {
		lookahead = precedenceForInstanceOf() >= minPrecedence ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.INSTANCEOF);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(INSTANCEOF)
	) */
	private int matchBinaryExpression_lookahead2(int lookahead, int minPrecedence) {
		lookahead = match(lookahead, TokenType.INSTANCEOF);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(op, BinaryOperator)
	) */
	private int matchBinaryExpression_lookahead3(int lookahead, int minPrecedence) {
		lookahead = matchBinaryOperator(lookahead, minPrecedence);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		choice(
			sequence(
				lookAhead({ precedenceFor(BinaryOp.Or) >= minPrecedence })
				terminal(SC_OR)
				action({ ret = BinaryOp.Or; })
			)
			sequence(
				lookAhead({ precedenceFor(BinaryOp.Plus) >= minPrecedence })
				terminal(SC_AND)
				action({ ret = BinaryOp.And; })
			)
			sequence(
				lookAhead({ precedenceFor(BinaryOp.Plus) >= minPrecedence })
				terminal(BIT_OR)
				action({ ret = BinaryOp.BinOr; })
			)
			sequence(
				lookAhead({ precedenceFor(BinaryOp.BinAnd) >= minPrecedence })
				terminal(BIT_AND)
				action({ ret = BinaryOp.BinAnd; })
			)
			sequence(
				lookAhead({ precedenceFor(BinaryOp.XOr) >= minPrecedence })
				terminal(XOR)
				action({ ret = BinaryOp.XOr; })
			)
			sequence(
				lookAhead({ precedenceFor(BinaryOp.Equal) >= minPrecedence })
				terminal(EQ)
				action({ ret = BinaryOp.Equal; })
			)
			sequence(
				lookAhead({ precedenceFor(BinaryOp.NotEqual) >= minPrecedence })
				terminal(NE)
				action({ ret = BinaryOp.NotEqual; })
			)
			sequence(
				lookAhead({ precedenceFor(BinaryOp.Less) >= minPrecedence })
				terminal(LT)
				action({ ret = BinaryOp.Less; })
			)
			sequence(
				lookAhead({ precedenceFor(BinaryOp.RightUnsignedShift) >= minPrecedence })
				terminal(GT)
				terminal(GT)
				terminal(GT)
				action({
					popNewWhitespaces(2);
					ret = BinaryOp.RightUnsignedShift;
				})
			)
			sequence(
				lookAhead({ precedenceFor(BinaryOp.RightSignedShift) >= minPrecedence })
				terminal(GT)
				terminal(GT)
				action({
					popNewWhitespaces(1);
					ret = BinaryOp.RightSignedShift;
				})
			)
			sequence(
				lookAhead({ precedenceFor(BinaryOp.Greater) >= minPrecedence })
				terminal(GT)
				action({ ret = BinaryOp.Greater; })
			)
			sequence(
				lookAhead({ precedenceFor(BinaryOp.LessOrEqual) >= minPrecedence })
				terminal(LE)
				action({ ret = BinaryOp.LessOrEqual; })
			)
			sequence(
				lookAhead({ precedenceFor(BinaryOp.GreaterOrEqual) >= minPrecedence })
				terminal(GE)
				action({ ret = BinaryOp.GreaterOrEqual; })
			)
			sequence(
				lookAhead({ precedenceFor(BinaryOp.LeftShift) >= minPrecedence })
				terminal(LSHIFT)
				action({ ret = BinaryOp.LeftShift; })
			)
			sequence(
				lookAhead({ precedenceFor(BinaryOp.Plus) >= minPrecedence })
				terminal(PLUS)
				action({ ret = BinaryOp.Plus; })
			)
			sequence(
				lookAhead({ precedenceFor(BinaryOp.Minus) >= minPrecedence })
				terminal(MINUS)
				action({ ret = BinaryOp.Minus; })
			)
			sequence(
				lookAhead({ precedenceFor(BinaryOp.Times) >= minPrecedence })
				terminal(STAR)
				action({ ret = BinaryOp.Times; })
			)
			sequence(
				lookAhead({ precedenceFor(BinaryOp.Divide) >= minPrecedence })
				terminal(SLASH)
				action({ ret = BinaryOp.Divide; })
			)
			sequence(
				lookAhead({ precedenceFor(BinaryOp.Remainder) >= minPrecedence })
				terminal(REM)
				action({ ret = BinaryOp.Remainder; })
			)
		)
		action({ return ret; })
	) */
	protected BinaryOp parseBinaryOperator(int minPrecedence) throws ParseException {
		BinaryOp ret;
		if (precedenceFor(BinaryOp.Or) >= minPrecedence && matchBinaryOperator_lookahead1(0, minPrecedence) != -1) {
			parse(TokenType.SC_OR);
			ret = BinaryOp.Or;
		} else if (precedenceFor(BinaryOp.Plus) >= minPrecedence && matchBinaryOperator_lookahead2(0, minPrecedence) != -1) {
			parse(TokenType.SC_AND);
			ret = BinaryOp.And;
		} else if (precedenceFor(BinaryOp.Plus) >= minPrecedence && matchBinaryOperator_lookahead3(0, minPrecedence) != -1) {
			parse(TokenType.BIT_OR);
			ret = BinaryOp.BinOr;
		} else if (precedenceFor(BinaryOp.BinAnd) >= minPrecedence && matchBinaryOperator_lookahead4(0, minPrecedence) != -1) {
			parse(TokenType.BIT_AND);
			ret = BinaryOp.BinAnd;
		} else if (precedenceFor(BinaryOp.XOr) >= minPrecedence && matchBinaryOperator_lookahead5(0, minPrecedence) != -1) {
			parse(TokenType.XOR);
			ret = BinaryOp.XOr;
		} else if (precedenceFor(BinaryOp.Equal) >= minPrecedence && matchBinaryOperator_lookahead6(0, minPrecedence) != -1) {
			parse(TokenType.EQ);
			ret = BinaryOp.Equal;
		} else if (precedenceFor(BinaryOp.NotEqual) >= minPrecedence && matchBinaryOperator_lookahead7(0, minPrecedence) != -1) {
			parse(TokenType.NE);
			ret = BinaryOp.NotEqual;
		} else if (precedenceFor(BinaryOp.Less) >= minPrecedence && matchBinaryOperator_lookahead8(0, minPrecedence) != -1) {
			parse(TokenType.LT);
			ret = BinaryOp.Less;
		} else if (precedenceFor(BinaryOp.RightUnsignedShift) >= minPrecedence && matchBinaryOperator_lookahead9(0, minPrecedence) != -1) {
			parse(TokenType.GT);
			parse(TokenType.GT);
			parse(TokenType.GT);
			popNewWhitespaces(2);
			ret = BinaryOp.RightUnsignedShift;
		} else if (precedenceFor(BinaryOp.RightSignedShift) >= minPrecedence && matchBinaryOperator_lookahead10(0, minPrecedence) != -1) {
			parse(TokenType.GT);
			parse(TokenType.GT);
			popNewWhitespaces(1);
			ret = BinaryOp.RightSignedShift;
		} else if (precedenceFor(BinaryOp.Greater) >= minPrecedence && matchBinaryOperator_lookahead11(0, minPrecedence) != -1) {
			parse(TokenType.GT);
			ret = BinaryOp.Greater;
		} else if (precedenceFor(BinaryOp.LessOrEqual) >= minPrecedence && matchBinaryOperator_lookahead12(0, minPrecedence) != -1) {
			parse(TokenType.LE);
			ret = BinaryOp.LessOrEqual;
		} else if (precedenceFor(BinaryOp.GreaterOrEqual) >= minPrecedence && matchBinaryOperator_lookahead13(0, minPrecedence) != -1) {
			parse(TokenType.GE);
			ret = BinaryOp.GreaterOrEqual;
		} else if (precedenceFor(BinaryOp.LeftShift) >= minPrecedence && matchBinaryOperator_lookahead14(0, minPrecedence) != -1) {
			parse(TokenType.LSHIFT);
			ret = BinaryOp.LeftShift;
		} else if (precedenceFor(BinaryOp.Plus) >= minPrecedence && matchBinaryOperator_lookahead15(0, minPrecedence) != -1) {
			parse(TokenType.PLUS);
			ret = BinaryOp.Plus;
		} else if (precedenceFor(BinaryOp.Minus) >= minPrecedence && matchBinaryOperator_lookahead16(0, minPrecedence) != -1) {
			parse(TokenType.MINUS);
			ret = BinaryOp.Minus;
		} else if (precedenceFor(BinaryOp.Times) >= minPrecedence && matchBinaryOperator_lookahead17(0, minPrecedence) != -1) {
			parse(TokenType.STAR);
			ret = BinaryOp.Times;
		} else if (precedenceFor(BinaryOp.Divide) >= minPrecedence && matchBinaryOperator_lookahead18(0, minPrecedence) != -1) {
			parse(TokenType.SLASH);
			ret = BinaryOp.Divide;
		} else if (precedenceFor(BinaryOp.Remainder) >= minPrecedence && matchBinaryOperator_lookahead19(0, minPrecedence) != -1) {
			parse(TokenType.REM);
			ret = BinaryOp.Remainder;
		} else {
			throw produceParseException(TokenType.REM, TokenType.SLASH, TokenType.STAR, TokenType.MINUS, TokenType.PLUS, TokenType.LSHIFT, TokenType.GE, TokenType.LE, TokenType.GT, TokenType.LT, TokenType.NE, TokenType.EQ, TokenType.XOR, TokenType.BIT_AND, TokenType.BIT_OR, TokenType.SC_AND, TokenType.SC_OR);
		}
		return ret;
	}

	/* sequence(
		choice(
			sequence(
				lookAhead({ precedenceFor(BinaryOp.Or) >= minPrecedence })
				terminal(SC_OR)
			)
			sequence(
				lookAhead({ precedenceFor(BinaryOp.Plus) >= minPrecedence })
				terminal(SC_AND)
			)
			sequence(
				lookAhead({ precedenceFor(BinaryOp.Plus) >= minPrecedence })
				terminal(BIT_OR)
			)
			sequence(
				lookAhead({ precedenceFor(BinaryOp.BinAnd) >= minPrecedence })
				terminal(BIT_AND)
			)
			sequence(
				lookAhead({ precedenceFor(BinaryOp.XOr) >= minPrecedence })
				terminal(XOR)
			)
			sequence(
				lookAhead({ precedenceFor(BinaryOp.Equal) >= minPrecedence })
				terminal(EQ)
			)
			sequence(
				lookAhead({ precedenceFor(BinaryOp.NotEqual) >= minPrecedence })
				terminal(NE)
			)
			sequence(
				lookAhead({ precedenceFor(BinaryOp.Less) >= minPrecedence })
				terminal(LT)
			)
			sequence(
				lookAhead({ precedenceFor(BinaryOp.RightUnsignedShift) >= minPrecedence })
				terminal(GT)
				terminal(GT)
				terminal(GT)
			)
			sequence(
				lookAhead({ precedenceFor(BinaryOp.RightSignedShift) >= minPrecedence })
				terminal(GT)
				terminal(GT)
			)
			sequence(
				lookAhead({ precedenceFor(BinaryOp.Greater) >= minPrecedence })
				terminal(GT)
			)
			sequence(
				lookAhead({ precedenceFor(BinaryOp.LessOrEqual) >= minPrecedence })
				terminal(LE)
			)
			sequence(
				lookAhead({ precedenceFor(BinaryOp.GreaterOrEqual) >= minPrecedence })
				terminal(GE)
			)
			sequence(
				lookAhead({ precedenceFor(BinaryOp.LeftShift) >= minPrecedence })
				terminal(LSHIFT)
			)
			sequence(
				lookAhead({ precedenceFor(BinaryOp.Plus) >= minPrecedence })
				terminal(PLUS)
			)
			sequence(
				lookAhead({ precedenceFor(BinaryOp.Minus) >= minPrecedence })
				terminal(MINUS)
			)
			sequence(
				lookAhead({ precedenceFor(BinaryOp.Times) >= minPrecedence })
				terminal(STAR)
			)
			sequence(
				lookAhead({ precedenceFor(BinaryOp.Divide) >= minPrecedence })
				terminal(SLASH)
			)
			sequence(
				lookAhead({ precedenceFor(BinaryOp.Remainder) >= minPrecedence })
				terminal(REM)
			)
		)
	) */
	private int matchBinaryOperator(int lookahead, int minPrecedence) {
		lookahead = matchBinaryOperator_1(lookahead, minPrecedence);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			lookAhead({ precedenceFor(BinaryOp.Or) >= minPrecedence })
			terminal(SC_OR)
		)
		sequence(
			lookAhead({ precedenceFor(BinaryOp.Plus) >= minPrecedence })
			terminal(SC_AND)
		)
		sequence(
			lookAhead({ precedenceFor(BinaryOp.Plus) >= minPrecedence })
			terminal(BIT_OR)
		)
		sequence(
			lookAhead({ precedenceFor(BinaryOp.BinAnd) >= minPrecedence })
			terminal(BIT_AND)
		)
		sequence(
			lookAhead({ precedenceFor(BinaryOp.XOr) >= minPrecedence })
			terminal(XOR)
		)
		sequence(
			lookAhead({ precedenceFor(BinaryOp.Equal) >= minPrecedence })
			terminal(EQ)
		)
		sequence(
			lookAhead({ precedenceFor(BinaryOp.NotEqual) >= minPrecedence })
			terminal(NE)
		)
		sequence(
			lookAhead({ precedenceFor(BinaryOp.Less) >= minPrecedence })
			terminal(LT)
		)
		sequence(
			lookAhead({ precedenceFor(BinaryOp.RightUnsignedShift) >= minPrecedence })
			terminal(GT)
			terminal(GT)
			terminal(GT)
		)
		sequence(
			lookAhead({ precedenceFor(BinaryOp.RightSignedShift) >= minPrecedence })
			terminal(GT)
			terminal(GT)
		)
		sequence(
			lookAhead({ precedenceFor(BinaryOp.Greater) >= minPrecedence })
			terminal(GT)
		)
		sequence(
			lookAhead({ precedenceFor(BinaryOp.LessOrEqual) >= minPrecedence })
			terminal(LE)
		)
		sequence(
			lookAhead({ precedenceFor(BinaryOp.GreaterOrEqual) >= minPrecedence })
			terminal(GE)
		)
		sequence(
			lookAhead({ precedenceFor(BinaryOp.LeftShift) >= minPrecedence })
			terminal(LSHIFT)
		)
		sequence(
			lookAhead({ precedenceFor(BinaryOp.Plus) >= minPrecedence })
			terminal(PLUS)
		)
		sequence(
			lookAhead({ precedenceFor(BinaryOp.Minus) >= minPrecedence })
			terminal(MINUS)
		)
		sequence(
			lookAhead({ precedenceFor(BinaryOp.Times) >= minPrecedence })
			terminal(STAR)
		)
		sequence(
			lookAhead({ precedenceFor(BinaryOp.Divide) >= minPrecedence })
			terminal(SLASH)
		)
		sequence(
			lookAhead({ precedenceFor(BinaryOp.Remainder) >= minPrecedence })
			terminal(REM)
		)
	) */
	private int matchBinaryOperator_1(int lookahead, int minPrecedence) {
		int newLookahead;
		newLookahead = matchBinaryOperator_1_1(lookahead, minPrecedence);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchBinaryOperator_1_2(lookahead, minPrecedence);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchBinaryOperator_1_3(lookahead, minPrecedence);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchBinaryOperator_1_4(lookahead, minPrecedence);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchBinaryOperator_1_5(lookahead, minPrecedence);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchBinaryOperator_1_6(lookahead, minPrecedence);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchBinaryOperator_1_7(lookahead, minPrecedence);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchBinaryOperator_1_8(lookahead, minPrecedence);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchBinaryOperator_1_9(lookahead, minPrecedence);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchBinaryOperator_1_10(lookahead, minPrecedence);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchBinaryOperator_1_11(lookahead, minPrecedence);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchBinaryOperator_1_12(lookahead, minPrecedence);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchBinaryOperator_1_13(lookahead, minPrecedence);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchBinaryOperator_1_14(lookahead, minPrecedence);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchBinaryOperator_1_15(lookahead, minPrecedence);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchBinaryOperator_1_16(lookahead, minPrecedence);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchBinaryOperator_1_17(lookahead, minPrecedence);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchBinaryOperator_1_18(lookahead, minPrecedence);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchBinaryOperator_1_19(lookahead, minPrecedence);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		lookAhead({ precedenceFor(BinaryOp.Or) >= minPrecedence })
		terminal(SC_OR)
	) */
	private int matchBinaryOperator_1_1(int lookahead, int minPrecedence) {
		lookahead = precedenceFor(BinaryOp.Or) >= minPrecedence ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.SC_OR);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead({ precedenceFor(BinaryOp.RightSignedShift) >= minPrecedence })
		terminal(GT)
		terminal(GT)
	) */
	private int matchBinaryOperator_1_10(int lookahead, int minPrecedence) {
		lookahead = precedenceFor(BinaryOp.RightSignedShift) >= minPrecedence ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.GT);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.GT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead({ precedenceFor(BinaryOp.Greater) >= minPrecedence })
		terminal(GT)
	) */
	private int matchBinaryOperator_1_11(int lookahead, int minPrecedence) {
		lookahead = precedenceFor(BinaryOp.Greater) >= minPrecedence ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.GT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead({ precedenceFor(BinaryOp.LessOrEqual) >= minPrecedence })
		terminal(LE)
	) */
	private int matchBinaryOperator_1_12(int lookahead, int minPrecedence) {
		lookahead = precedenceFor(BinaryOp.LessOrEqual) >= minPrecedence ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.LE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead({ precedenceFor(BinaryOp.GreaterOrEqual) >= minPrecedence })
		terminal(GE)
	) */
	private int matchBinaryOperator_1_13(int lookahead, int minPrecedence) {
		lookahead = precedenceFor(BinaryOp.GreaterOrEqual) >= minPrecedence ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.GE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead({ precedenceFor(BinaryOp.LeftShift) >= minPrecedence })
		terminal(LSHIFT)
	) */
	private int matchBinaryOperator_1_14(int lookahead, int minPrecedence) {
		lookahead = precedenceFor(BinaryOp.LeftShift) >= minPrecedence ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.LSHIFT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead({ precedenceFor(BinaryOp.Plus) >= minPrecedence })
		terminal(PLUS)
	) */
	private int matchBinaryOperator_1_15(int lookahead, int minPrecedence) {
		lookahead = precedenceFor(BinaryOp.Plus) >= minPrecedence ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.PLUS);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead({ precedenceFor(BinaryOp.Minus) >= minPrecedence })
		terminal(MINUS)
	) */
	private int matchBinaryOperator_1_16(int lookahead, int minPrecedence) {
		lookahead = precedenceFor(BinaryOp.Minus) >= minPrecedence ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.MINUS);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead({ precedenceFor(BinaryOp.Times) >= minPrecedence })
		terminal(STAR)
	) */
	private int matchBinaryOperator_1_17(int lookahead, int minPrecedence) {
		lookahead = precedenceFor(BinaryOp.Times) >= minPrecedence ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.STAR);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead({ precedenceFor(BinaryOp.Divide) >= minPrecedence })
		terminal(SLASH)
	) */
	private int matchBinaryOperator_1_18(int lookahead, int minPrecedence) {
		lookahead = precedenceFor(BinaryOp.Divide) >= minPrecedence ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.SLASH);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead({ precedenceFor(BinaryOp.Remainder) >= minPrecedence })
		terminal(REM)
	) */
	private int matchBinaryOperator_1_19(int lookahead, int minPrecedence) {
		lookahead = precedenceFor(BinaryOp.Remainder) >= minPrecedence ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.REM);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead({ precedenceFor(BinaryOp.Plus) >= minPrecedence })
		terminal(SC_AND)
	) */
	private int matchBinaryOperator_1_2(int lookahead, int minPrecedence) {
		lookahead = precedenceFor(BinaryOp.Plus) >= minPrecedence ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.SC_AND);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead({ precedenceFor(BinaryOp.Plus) >= minPrecedence })
		terminal(BIT_OR)
	) */
	private int matchBinaryOperator_1_3(int lookahead, int minPrecedence) {
		lookahead = precedenceFor(BinaryOp.Plus) >= minPrecedence ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.BIT_OR);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead({ precedenceFor(BinaryOp.BinAnd) >= minPrecedence })
		terminal(BIT_AND)
	) */
	private int matchBinaryOperator_1_4(int lookahead, int minPrecedence) {
		lookahead = precedenceFor(BinaryOp.BinAnd) >= minPrecedence ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.BIT_AND);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead({ precedenceFor(BinaryOp.XOr) >= minPrecedence })
		terminal(XOR)
	) */
	private int matchBinaryOperator_1_5(int lookahead, int minPrecedence) {
		lookahead = precedenceFor(BinaryOp.XOr) >= minPrecedence ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.XOR);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead({ precedenceFor(BinaryOp.Equal) >= minPrecedence })
		terminal(EQ)
	) */
	private int matchBinaryOperator_1_6(int lookahead, int minPrecedence) {
		lookahead = precedenceFor(BinaryOp.Equal) >= minPrecedence ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.EQ);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead({ precedenceFor(BinaryOp.NotEqual) >= minPrecedence })
		terminal(NE)
	) */
	private int matchBinaryOperator_1_7(int lookahead, int minPrecedence) {
		lookahead = precedenceFor(BinaryOp.NotEqual) >= minPrecedence ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.NE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead({ precedenceFor(BinaryOp.Less) >= minPrecedence })
		terminal(LT)
	) */
	private int matchBinaryOperator_1_8(int lookahead, int minPrecedence) {
		lookahead = precedenceFor(BinaryOp.Less) >= minPrecedence ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.LT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead({ precedenceFor(BinaryOp.RightUnsignedShift) >= minPrecedence })
		terminal(GT)
		terminal(GT)
		terminal(GT)
	) */
	private int matchBinaryOperator_1_9(int lookahead, int minPrecedence) {
		lookahead = precedenceFor(BinaryOp.RightUnsignedShift) >= minPrecedence ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.GT);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.GT);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.GT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(SC_OR)
	) */
	private int matchBinaryOperator_lookahead1(int lookahead, int minPrecedence) {
		lookahead = match(lookahead, TokenType.SC_OR);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(GT)
		terminal(GT)
	) */
	private int matchBinaryOperator_lookahead10(int lookahead, int minPrecedence) {
		lookahead = match(lookahead, TokenType.GT);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.GT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(GT)
	) */
	private int matchBinaryOperator_lookahead11(int lookahead, int minPrecedence) {
		lookahead = match(lookahead, TokenType.GT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(LE)
	) */
	private int matchBinaryOperator_lookahead12(int lookahead, int minPrecedence) {
		lookahead = match(lookahead, TokenType.LE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(GE)
	) */
	private int matchBinaryOperator_lookahead13(int lookahead, int minPrecedence) {
		lookahead = match(lookahead, TokenType.GE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(LSHIFT)
	) */
	private int matchBinaryOperator_lookahead14(int lookahead, int minPrecedence) {
		lookahead = match(lookahead, TokenType.LSHIFT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(PLUS)
	) */
	private int matchBinaryOperator_lookahead15(int lookahead, int minPrecedence) {
		lookahead = match(lookahead, TokenType.PLUS);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(MINUS)
	) */
	private int matchBinaryOperator_lookahead16(int lookahead, int minPrecedence) {
		lookahead = match(lookahead, TokenType.MINUS);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(STAR)
	) */
	private int matchBinaryOperator_lookahead17(int lookahead, int minPrecedence) {
		lookahead = match(lookahead, TokenType.STAR);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(SLASH)
	) */
	private int matchBinaryOperator_lookahead18(int lookahead, int minPrecedence) {
		lookahead = match(lookahead, TokenType.SLASH);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(REM)
	) */
	private int matchBinaryOperator_lookahead19(int lookahead, int minPrecedence) {
		lookahead = match(lookahead, TokenType.REM);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(SC_AND)
	) */
	private int matchBinaryOperator_lookahead2(int lookahead, int minPrecedence) {
		lookahead = match(lookahead, TokenType.SC_AND);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(BIT_OR)
	) */
	private int matchBinaryOperator_lookahead3(int lookahead, int minPrecedence) {
		lookahead = match(lookahead, TokenType.BIT_OR);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(BIT_AND)
	) */
	private int matchBinaryOperator_lookahead4(int lookahead, int minPrecedence) {
		lookahead = match(lookahead, TokenType.BIT_AND);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(XOR)
	) */
	private int matchBinaryOperator_lookahead5(int lookahead, int minPrecedence) {
		lookahead = match(lookahead, TokenType.XOR);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(EQ)
	) */
	private int matchBinaryOperator_lookahead6(int lookahead, int minPrecedence) {
		lookahead = match(lookahead, TokenType.EQ);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(NE)
	) */
	private int matchBinaryOperator_lookahead7(int lookahead, int minPrecedence) {
		lookahead = match(lookahead, TokenType.NE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(LT)
	) */
	private int matchBinaryOperator_lookahead8(int lookahead, int minPrecedence) {
		lookahead = match(lookahead, TokenType.LT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(GT)
		terminal(GT)
		terminal(GT)
	) */
	private int matchBinaryOperator_lookahead9(int lookahead, int minPrecedence) {
		lookahead = match(lookahead, TokenType.GT);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.GT);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.GT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		choice(
			nonTerminal(ret, PrefixExpression)
			sequence(
				action({ run(); })
				choice(
					sequence(
						terminal(PLUS)
						action({ op = UnaryOp.Positive; })
					)
					sequence(
						terminal(MINUS)
						action({ op = UnaryOp.Negative; })
					)
				)
				nonTerminal(ret, UnaryExpression)
				action({ ret = dress(SUnaryExpr.make(op, ret)); })
			)
			nonTerminal(ret, UnaryExpressionNotPlusMinus)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parseUnaryExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		UnaryOp op;
		if (match(0, TokenType.DECR, TokenType.INCR) != -1) {
			ret = parsePrefixExpression();
		} else if (match(0, TokenType.MINUS, TokenType.PLUS) != -1) {
			run();
			if (match(0, TokenType.PLUS) != -1) {
				parse(TokenType.PLUS);
				op = UnaryOp.Positive;
			} else if (match(0, TokenType.MINUS) != -1) {
				parse(TokenType.MINUS);
				op = UnaryOp.Negative;
			} else {
				throw produceParseException(TokenType.MINUS, TokenType.PLUS);
			}
			ret = parseUnaryExpression();
			ret = dress(SUnaryExpr.make(op, ret));
		} else if (match(0, TokenType.BANG, TokenType.TILDE, TokenType.BYTE, TokenType.CHAR, TokenType.BOOLEAN, TokenType.FLOAT, TokenType.LONG, TokenType.INT, TokenType.SHORT, TokenType.DOUBLE, TokenType.IDENTIFIER, TokenType.NODE_VARIABLE, TokenType.VOID, TokenType.NEW, TokenType.LT, TokenType.NULL, TokenType.INTEGER_LITERAL, TokenType.LONG_LITERAL, TokenType.FLOAT_LITERAL, TokenType.DOUBLE_LITERAL, TokenType.CHARACTER_LITERAL, TokenType.STRING_LITERAL, TokenType.TRUE, TokenType.FALSE, TokenType.SUPER, TokenType.THIS, TokenType.LPAREN) != -1) {
			ret = parseUnaryExpressionNotPlusMinus();
		} else {
			throw produceParseException(TokenType.SUPER, TokenType.THIS, TokenType.FALSE, TokenType.TRUE, TokenType.STRING_LITERAL, TokenType.CHARACTER_LITERAL, TokenType.NULL, TokenType.DOUBLE_LITERAL, TokenType.FLOAT_LITERAL, TokenType.LONG_LITERAL, TokenType.INTEGER_LITERAL, TokenType.LPAREN, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER, TokenType.LT, TokenType.VOID, TokenType.BYTE, TokenType.SHORT, TokenType.INT, TokenType.LONG, TokenType.FLOAT, TokenType.DOUBLE, TokenType.BOOLEAN, TokenType.CHAR, TokenType.NEW, TokenType.BANG, TokenType.TILDE, TokenType.MINUS, TokenType.PLUS, TokenType.DECR, TokenType.INCR);
		}
		return ret;
	}

	/* sequence(
		choice(
			nonTerminal(ret, PrefixExpression)
			sequence(
				choice(
					sequence(
						terminal(PLUS)
					)
					sequence(
						terminal(MINUS)
					)
				)
				nonTerminal(ret, UnaryExpression)
			)
			nonTerminal(ret, UnaryExpressionNotPlusMinus)
		)
	) */
	private int matchUnaryExpression(int lookahead) {
		lookahead = matchUnaryExpression_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		nonTerminal(ret, PrefixExpression)
		sequence(
			choice(
				sequence(
					terminal(PLUS)
				)
				sequence(
					terminal(MINUS)
				)
			)
			nonTerminal(ret, UnaryExpression)
		)
		nonTerminal(ret, UnaryExpressionNotPlusMinus)
	) */
	private int matchUnaryExpression_1(int lookahead) {
		int newLookahead;
		newLookahead = matchPrefixExpression(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchUnaryExpression_1_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchUnaryExpressionNotPlusMinus(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		choice(
			sequence(
				terminal(PLUS)
			)
			sequence(
				terminal(MINUS)
			)
		)
		nonTerminal(ret, UnaryExpression)
	) */
	private int matchUnaryExpression_1_2(int lookahead) {
		lookahead = matchUnaryExpression_1_2_2(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchUnaryExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			terminal(PLUS)
		)
		sequence(
			terminal(MINUS)
		)
	) */
	private int matchUnaryExpression_1_2_2(int lookahead) {
		int newLookahead;
		newLookahead = matchUnaryExpression_1_2_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchUnaryExpression_1_2_2_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		terminal(PLUS)
	) */
	private int matchUnaryExpression_1_2_2_1(int lookahead) {
		lookahead = match(lookahead, TokenType.PLUS);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(MINUS)
	) */
	private int matchUnaryExpression_1_2_2_2(int lookahead) {
		lookahead = match(lookahead, TokenType.MINUS);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		action({ run(); })
		choice(
			sequence(
				terminal(INCR)
				action({ op = UnaryOp.PreIncrement; })
			)
			sequence(
				terminal(DECR)
				action({ op = UnaryOp.PreDecrement; })
			)
		)
		nonTerminal(ret, UnaryExpression)
		action({ return dress(SUnaryExpr.make(op, ret)); })
	) */
	protected BUTree<? extends SExpr> parsePrefixExpression() throws ParseException {
		UnaryOp op;
		BUTree<? extends SExpr> ret;
		run();
		if (match(0, TokenType.INCR) != -1) {
			parse(TokenType.INCR);
			op = UnaryOp.PreIncrement;
		} else if (match(0, TokenType.DECR) != -1) {
			parse(TokenType.DECR);
			op = UnaryOp.PreDecrement;
		} else {
			throw produceParseException(TokenType.DECR, TokenType.INCR);
		}
		ret = parseUnaryExpression();
		return dress(SUnaryExpr.make(op, ret));
	}

	/* sequence(
		choice(
			sequence(
				terminal(INCR)
			)
			sequence(
				terminal(DECR)
			)
		)
		nonTerminal(ret, UnaryExpression)
	) */
	private int matchPrefixExpression(int lookahead) {
		lookahead = matchPrefixExpression_2(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchUnaryExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			terminal(INCR)
		)
		sequence(
			terminal(DECR)
		)
	) */
	private int matchPrefixExpression_2(int lookahead) {
		int newLookahead;
		newLookahead = matchPrefixExpression_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchPrefixExpression_2_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		terminal(INCR)
	) */
	private int matchPrefixExpression_2_1(int lookahead) {
		lookahead = match(lookahead, TokenType.INCR);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(DECR)
	) */
	private int matchPrefixExpression_2_2(int lookahead) {
		lookahead = match(lookahead, TokenType.DECR);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		choice(
			sequence(
				action({ run(); })
				choice(
					sequence(
						terminal(TILDE)
						action({ op = UnaryOp.Inverse; })
					)
					sequence(
						terminal(BANG)
						action({ op = UnaryOp.Not; })
					)
				)
				nonTerminal(ret, UnaryExpression)
				action({ ret = dress(SUnaryExpr.make(op, ret)); })
			)
			sequence(
				lookAhead({ isCast() })
				nonTerminal(ret, CastExpression)
			)
			nonTerminal(ret, PostfixExpression)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parseUnaryExpressionNotPlusMinus() throws ParseException {
		BUTree<? extends SExpr> ret;
		UnaryOp op;
		if (match(0, TokenType.BANG, TokenType.TILDE) != -1) {
			run();
			if (match(0, TokenType.TILDE) != -1) {
				parse(TokenType.TILDE);
				op = UnaryOp.Inverse;
			} else if (match(0, TokenType.BANG) != -1) {
				parse(TokenType.BANG);
				op = UnaryOp.Not;
			} else {
				throw produceParseException(TokenType.BANG, TokenType.TILDE);
			}
			ret = parseUnaryExpression();
			ret = dress(SUnaryExpr.make(op, ret));
		} else if (isCast(0)) {
			ret = parseCastExpression();
		} else if (match(0, TokenType.SUPER, TokenType.THIS, TokenType.FLOAT, TokenType.LONG, TokenType.DOUBLE, TokenType.BYTE, TokenType.CHAR, TokenType.INT, TokenType.SHORT, TokenType.BOOLEAN, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER, TokenType.VOID, TokenType.NEW, TokenType.LT, TokenType.LPAREN, TokenType.LONG_LITERAL, TokenType.INTEGER_LITERAL, TokenType.DOUBLE_LITERAL, TokenType.FLOAT_LITERAL, TokenType.STRING_LITERAL, TokenType.CHARACTER_LITERAL, TokenType.FALSE, TokenType.TRUE, TokenType.NULL) != -1) {
			ret = parsePostfixExpression();
		} else {
			throw produceParseException(TokenType.NODE_VARIABLE, TokenType.IDENTIFIER, TokenType.BOOLEAN, TokenType.BYTE, TokenType.CHAR, TokenType.INT, TokenType.SHORT, TokenType.FLOAT, TokenType.LONG, TokenType.DOUBLE, TokenType.VOID, TokenType.NEW, TokenType.SUPER, TokenType.THIS, TokenType.NULL, TokenType.FALSE, TokenType.TRUE, TokenType.LONG_LITERAL, TokenType.INTEGER_LITERAL, TokenType.STRING_LITERAL, TokenType.CHARACTER_LITERAL, TokenType.DOUBLE_LITERAL, TokenType.FLOAT_LITERAL, TokenType.LPAREN, TokenType.LT, TokenType.BANG, TokenType.TILDE);
		}
		return ret;
	}

	/* sequence(
		choice(
			sequence(
				choice(
					sequence(
						terminal(TILDE)
					)
					sequence(
						terminal(BANG)
					)
				)
				nonTerminal(ret, UnaryExpression)
			)
			sequence(
				lookAhead({ isCast() })
				nonTerminal(ret, CastExpression)
			)
			nonTerminal(ret, PostfixExpression)
		)
	) */
	private int matchUnaryExpressionNotPlusMinus(int lookahead) {
		lookahead = matchUnaryExpressionNotPlusMinus_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			choice(
				sequence(
					terminal(TILDE)
				)
				sequence(
					terminal(BANG)
				)
			)
			nonTerminal(ret, UnaryExpression)
		)
		sequence(
			lookAhead({ isCast() })
			nonTerminal(ret, CastExpression)
		)
		nonTerminal(ret, PostfixExpression)
	) */
	private int matchUnaryExpressionNotPlusMinus_1(int lookahead) {
		int newLookahead;
		newLookahead = matchUnaryExpressionNotPlusMinus_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchUnaryExpressionNotPlusMinus_1_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchPostfixExpression(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		choice(
			sequence(
				terminal(TILDE)
			)
			sequence(
				terminal(BANG)
			)
		)
		nonTerminal(ret, UnaryExpression)
	) */
	private int matchUnaryExpressionNotPlusMinus_1_1(int lookahead) {
		lookahead = matchUnaryExpressionNotPlusMinus_1_1_2(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchUnaryExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			terminal(TILDE)
		)
		sequence(
			terminal(BANG)
		)
	) */
	private int matchUnaryExpressionNotPlusMinus_1_1_2(int lookahead) {
		int newLookahead;
		newLookahead = matchUnaryExpressionNotPlusMinus_1_1_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchUnaryExpressionNotPlusMinus_1_1_2_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		terminal(TILDE)
	) */
	private int matchUnaryExpressionNotPlusMinus_1_1_2_1(int lookahead) {
		lookahead = match(lookahead, TokenType.TILDE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(BANG)
	) */
	private int matchUnaryExpressionNotPlusMinus_1_1_2_2(int lookahead) {
		lookahead = match(lookahead, TokenType.BANG);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead({ isCast() })
		nonTerminal(ret, CastExpression)
	) */
	private int matchUnaryExpressionNotPlusMinus_1_2(int lookahead) {
		lookahead = isCast(lookahead) ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = matchCastExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(ret, PrimaryExpression)
		zeroOrOne(
			lookAhead(2)
			action({ lateRun(); })
			choice(
				sequence(
					terminal(INCR)
					action({ op = UnaryOp.PostIncrement; })
				)
				sequence(
					terminal(DECR)
					action({ op = UnaryOp.PostDecrement; })
				)
			)
			action({ ret = dress(SUnaryExpr.make(op, ret)); })
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parsePostfixExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		UnaryOp op;
		ret = parsePrimaryExpression();
		if (matchPostfixExpression_lookahead1(0) != -1) {
			lateRun();
			if (match(0, TokenType.INCR) != -1) {
				parse(TokenType.INCR);
				op = UnaryOp.PostIncrement;
			} else if (match(0, TokenType.DECR) != -1) {
				parse(TokenType.DECR);
				op = UnaryOp.PostDecrement;
			} else {
				throw produceParseException(TokenType.DECR, TokenType.INCR);
			}
			ret = dress(SUnaryExpr.make(op, ret));
		}
		return ret;
	}

	/* sequence(
		nonTerminal(ret, PrimaryExpression)
		zeroOrOne(
			lookAhead(2)
			choice(
				sequence(
					terminal(INCR)
				)
				sequence(
					terminal(DECR)
				)
			)
		)
	) */
	private int matchPostfixExpression(int lookahead) {
		lookahead = matchPrimaryExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchPostfixExpression_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		lookAhead(2)
		choice(
			sequence(
				terminal(INCR)
			)
			sequence(
				terminal(DECR)
			)
		)
	) */
	private int matchPostfixExpression_2(int lookahead) {
		int newLookahead;
		newLookahead = matchPostfixExpression_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		lookAhead(2)
		choice(
			sequence(
				terminal(INCR)
			)
			sequence(
				terminal(DECR)
			)
		)
	) */
	private int matchPostfixExpression_2_1(int lookahead) {
		lookahead = matchPostfixExpression_2_1_3(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			terminal(INCR)
		)
		sequence(
			terminal(DECR)
		)
	) */
	private int matchPostfixExpression_2_1_3(int lookahead) {
		int newLookahead;
		newLookahead = matchPostfixExpression_2_1_3_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchPostfixExpression_2_1_3_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		terminal(INCR)
	) */
	private int matchPostfixExpression_2_1_3_1(int lookahead) {
		lookahead = match(lookahead, TokenType.INCR);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(DECR)
	) */
	private int matchPostfixExpression_2_1_3_2(int lookahead) {
		lookahead = match(lookahead, TokenType.DECR);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		lookAhead(2)
		choice(
			sequence(
				terminal(INCR)
			)
			sequence(
				terminal(DECR)
			)
		)
	) */
	private int matchPostfixExpression_lookahead1(int lookahead) {
		if (match(0, TokenType.INCR) != -1) {
			return lookahead;
		}
		if (match(0, TokenType.DECR) != -1) {
			return lookahead;
		}
		return -1;
	}

	/* sequence(
		action({ run(); })
		terminal(LPAREN)
		action({ run(); })
		nonTerminal(annotations, Annotations)
		choice(
			sequence(
				nonTerminal(primitiveType, PrimitiveType)
				choice(
					sequence(
						terminal(RPAREN)
						nonTerminal(ret, UnaryExpression)
						action({ ret = dress(SCastExpr.make(primitiveType, ret)); })
					)
					sequence(
						action({ lateRun(); })
						nonTerminal(arrayDims, ArrayDimsMandatory)
						action({ type = dress(SArrayType.make(primitiveType, arrayDims)); })
						nonTerminal(type, ReferenceCastTypeRest)
						terminal(RPAREN)
						nonTerminal(ret, UnaryExpressionNotPlusMinus)
						action({ ret = dress(SCastExpr.make(type, ret)); })
					)
				)
			)
			sequence(
				nonTerminal(type, QualifiedType)
				zeroOrOne(
					lookAhead(
						nonTerminal(Annotations)
						terminal(LBRACKET)
					)
					action({ lateRun(); })
					nonTerminal(arrayDims, ArrayDimsMandatory)
					action({ type = dress(SArrayType.make(type, arrayDims)); })
				)
				nonTerminal(type, ReferenceCastTypeRest)
				terminal(RPAREN)
				nonTerminal(ret, UnaryExpressionNotPlusMinus)
				action({ ret = dress(SCastExpr.make(type, ret)); })
			)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parseCastExpression() throws ParseException {
		BUTree<SNodeList> annotations = null;
		BUTree<? extends SType> primitiveType;
		BUTree<? extends SType> type;
		BUTree<SNodeList> arrayDims;
		BUTree<? extends SExpr> ret;
		run();
		parse(TokenType.LPAREN);
		run();
		annotations = parseAnnotations();
		if (match(0, TokenType.DOUBLE, TokenType.FLOAT, TokenType.LONG, TokenType.INT, TokenType.SHORT, TokenType.BYTE, TokenType.CHAR, TokenType.BOOLEAN) != -1) {
			primitiveType = parsePrimitiveType(annotations);
			if (match(0, TokenType.RPAREN) != -1) {
				parse(TokenType.RPAREN);
				ret = parseUnaryExpression();
				ret = dress(SCastExpr.make(primitiveType, ret));
			} else if (match(0, TokenType.AT, TokenType.LBRACKET) != -1) {
				lateRun();
				arrayDims = parseArrayDimsMandatory();
				type = dress(SArrayType.make(primitiveType, arrayDims));
				type = parseReferenceCastTypeRest(type);
				parse(TokenType.RPAREN);
				ret = parseUnaryExpressionNotPlusMinus();
				ret = dress(SCastExpr.make(type, ret));
			} else {
				throw produceParseException(TokenType.AT, TokenType.LBRACKET, TokenType.RPAREN);
			}
		} else if (match(0, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER) != -1) {
			type = parseQualifiedType(annotations);
			if (matchCastExpression_lookahead1(0) != -1) {
				lateRun();
				arrayDims = parseArrayDimsMandatory();
				type = dress(SArrayType.make(type, arrayDims));
			}
			type = parseReferenceCastTypeRest(type);
			parse(TokenType.RPAREN);
			ret = parseUnaryExpressionNotPlusMinus();
			ret = dress(SCastExpr.make(type, ret));
		} else {
			throw produceParseException(TokenType.IDENTIFIER, TokenType.NODE_VARIABLE, TokenType.SHORT, TokenType.BYTE, TokenType.LONG, TokenType.INT, TokenType.DOUBLE, TokenType.FLOAT, TokenType.CHAR, TokenType.BOOLEAN);
		}
		return ret;
	}

	/* sequence(
		terminal(LPAREN)
		nonTerminal(annotations, Annotations)
		choice(
			sequence(
				nonTerminal(primitiveType, PrimitiveType)
				choice(
					sequence(
						terminal(RPAREN)
						nonTerminal(ret, UnaryExpression)
					)
					sequence(
						nonTerminal(arrayDims, ArrayDimsMandatory)
						nonTerminal(type, ReferenceCastTypeRest)
						terminal(RPAREN)
						nonTerminal(ret, UnaryExpressionNotPlusMinus)
					)
				)
			)
			sequence(
				nonTerminal(type, QualifiedType)
				zeroOrOne(
					lookAhead(
						nonTerminal(Annotations)
						terminal(LBRACKET)
					)
					nonTerminal(arrayDims, ArrayDimsMandatory)
				)
				nonTerminal(type, ReferenceCastTypeRest)
				terminal(RPAREN)
				nonTerminal(ret, UnaryExpressionNotPlusMinus)
			)
		)
	) */
	private int matchCastExpression(int lookahead) {
		lookahead = match(lookahead, TokenType.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchAnnotations(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchCastExpression_5(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			nonTerminal(primitiveType, PrimitiveType)
			choice(
				sequence(
					terminal(RPAREN)
					nonTerminal(ret, UnaryExpression)
				)
				sequence(
					nonTerminal(arrayDims, ArrayDimsMandatory)
					nonTerminal(type, ReferenceCastTypeRest)
					terminal(RPAREN)
					nonTerminal(ret, UnaryExpressionNotPlusMinus)
				)
			)
		)
		sequence(
			nonTerminal(type, QualifiedType)
			zeroOrOne(
				lookAhead(
					nonTerminal(Annotations)
					terminal(LBRACKET)
				)
				nonTerminal(arrayDims, ArrayDimsMandatory)
			)
			nonTerminal(type, ReferenceCastTypeRest)
			terminal(RPAREN)
			nonTerminal(ret, UnaryExpressionNotPlusMinus)
		)
	) */
	private int matchCastExpression_5(int lookahead) {
		int newLookahead;
		newLookahead = matchCastExpression_5_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchCastExpression_5_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		nonTerminal(primitiveType, PrimitiveType)
		choice(
			sequence(
				terminal(RPAREN)
				nonTerminal(ret, UnaryExpression)
			)
			sequence(
				nonTerminal(arrayDims, ArrayDimsMandatory)
				nonTerminal(type, ReferenceCastTypeRest)
				terminal(RPAREN)
				nonTerminal(ret, UnaryExpressionNotPlusMinus)
			)
		)
	) */
	private int matchCastExpression_5_1(int lookahead) {
		lookahead = matchPrimitiveType(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchCastExpression_5_1_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			terminal(RPAREN)
			nonTerminal(ret, UnaryExpression)
		)
		sequence(
			nonTerminal(arrayDims, ArrayDimsMandatory)
			nonTerminal(type, ReferenceCastTypeRest)
			terminal(RPAREN)
			nonTerminal(ret, UnaryExpressionNotPlusMinus)
		)
	) */
	private int matchCastExpression_5_1_2(int lookahead) {
		int newLookahead;
		newLookahead = matchCastExpression_5_1_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchCastExpression_5_1_2_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		terminal(RPAREN)
		nonTerminal(ret, UnaryExpression)
	) */
	private int matchCastExpression_5_1_2_1(int lookahead) {
		lookahead = match(lookahead, TokenType.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchUnaryExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(arrayDims, ArrayDimsMandatory)
		nonTerminal(type, ReferenceCastTypeRest)
		terminal(RPAREN)
		nonTerminal(ret, UnaryExpressionNotPlusMinus)
	) */
	private int matchCastExpression_5_1_2_2(int lookahead) {
		lookahead = matchArrayDimsMandatory(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchReferenceCastTypeRest(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchUnaryExpressionNotPlusMinus(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(type, QualifiedType)
		zeroOrOne(
			lookAhead(
				nonTerminal(Annotations)
				terminal(LBRACKET)
			)
			nonTerminal(arrayDims, ArrayDimsMandatory)
		)
		nonTerminal(type, ReferenceCastTypeRest)
		terminal(RPAREN)
		nonTerminal(ret, UnaryExpressionNotPlusMinus)
	) */
	private int matchCastExpression_5_2(int lookahead) {
		lookahead = matchQualifiedType(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchCastExpression_5_2_2(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchReferenceCastTypeRest(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchUnaryExpressionNotPlusMinus(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		lookAhead(
			nonTerminal(Annotations)
			terminal(LBRACKET)
		)
		nonTerminal(arrayDims, ArrayDimsMandatory)
	) */
	private int matchCastExpression_5_2_2(int lookahead) {
		int newLookahead;
		newLookahead = matchCastExpression_5_2_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		lookAhead(
			nonTerminal(Annotations)
			terminal(LBRACKET)
		)
		nonTerminal(arrayDims, ArrayDimsMandatory)
	) */
	private int matchCastExpression_5_2_2_1(int lookahead) {
		lookahead = matchArrayDimsMandatory(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(Annotations)
		terminal(LBRACKET)
	) */
	private int matchCastExpression_lookahead1(int lookahead) {
		lookahead = matchAnnotations(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.LBRACKET);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		zeroOrOne(
			lookAhead(
				terminal(BIT_AND)
			)
			action({ types = append(types, type); })
			action({ lateRun(); })
			oneOrMore(
				terminal(BIT_AND)
				action({ run(); })
				nonTerminal(annotations, Annotations)
				nonTerminal(type, ReferenceType)
				action({ types = append(types, type); })
			)
			action({ type = dress(SIntersectionType.make(types)); })
		)
		action({ return type; })
	) */
	protected BUTree<? extends SType> parseReferenceCastTypeRest(BUTree<? extends SType> type) throws ParseException {
		BUTree<SNodeList> types = emptyList();
		BUTree<SNodeList> annotations = null;
		if (matchReferenceCastTypeRest_lookahead1(0) != -1) {
			types = append(types, type);
			lateRun();
			do {
				parse(TokenType.BIT_AND);
				run();
				annotations = parseAnnotations();
				type = parseReferenceType(annotations);
				types = append(types, type);
			} while (match(0, TokenType.BIT_AND) != -1);
			type = dress(SIntersectionType.make(types));
		}
		return type;
	}

	/* sequence(
		zeroOrOne(
			lookAhead(
				terminal(BIT_AND)
			)
			oneOrMore(
				terminal(BIT_AND)
				nonTerminal(annotations, Annotations)
				nonTerminal(type, ReferenceType)
			)
		)
	) */
	private int matchReferenceCastTypeRest(int lookahead) {
		lookahead = matchReferenceCastTypeRest_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		lookAhead(
			terminal(BIT_AND)
		)
		oneOrMore(
			terminal(BIT_AND)
			nonTerminal(annotations, Annotations)
			nonTerminal(type, ReferenceType)
		)
	) */
	private int matchReferenceCastTypeRest_1(int lookahead) {
		int newLookahead;
		newLookahead = matchReferenceCastTypeRest_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		lookAhead(
			terminal(BIT_AND)
		)
		oneOrMore(
			terminal(BIT_AND)
			nonTerminal(annotations, Annotations)
			nonTerminal(type, ReferenceType)
		)
	) */
	private int matchReferenceCastTypeRest_1_1(int lookahead) {
		lookahead = matchReferenceCastTypeRest_1_1_4(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* oneOrMore(
		terminal(BIT_AND)
		nonTerminal(annotations, Annotations)
		nonTerminal(type, ReferenceType)
	) */
	private int matchReferenceCastTypeRest_1_1_4(int lookahead) {
		int newLookahead;
		newLookahead = matchReferenceCastTypeRest_1_1_4_1(lookahead);
		if (newLookahead == -1)
			return -1;
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchReferenceCastTypeRest_1_1_4_1(lookahead);
		}
		return lookahead;
	}

	/* sequence(
		terminal(BIT_AND)
		nonTerminal(annotations, Annotations)
		nonTerminal(type, ReferenceType)
	) */
	private int matchReferenceCastTypeRest_1_1_4_1(int lookahead) {
		lookahead = match(lookahead, TokenType.BIT_AND);
		if (lookahead == -1)
			return -1;
		lookahead = matchAnnotations(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchReferenceType(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(BIT_AND)
	) */
	private int matchReferenceCastTypeRest_lookahead1(int lookahead) {
		lookahead = match(lookahead, TokenType.BIT_AND);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		action({ run(); })
		choice(
			sequence(
				terminal(literal, INTEGER_LITERAL)
				action({ ret = SLiteralExpr.make(Integer.class, literal.image); })
			)
			sequence(
				terminal(literal, LONG_LITERAL)
				action({ ret = SLiteralExpr.make(Long.class, literal.image); })
			)
			sequence(
				terminal(literal, FLOAT_LITERAL)
				action({ ret = SLiteralExpr.make(Float.class, literal.image); })
			)
			sequence(
				terminal(literal, DOUBLE_LITERAL)
				action({ ret = SLiteralExpr.make(Double.class, literal.image); })
			)
			sequence(
				terminal(literal, CHARACTER_LITERAL)
				action({ ret = SLiteralExpr.make(Character.class, literal.image); })
			)
			sequence(
				terminal(literal, STRING_LITERAL)
				action({ ret = SLiteralExpr.make(String.class, literal.image); })
			)
			sequence(
				terminal(literal, TRUE)
				action({ ret = SLiteralExpr.make(Boolean.class, literal.image); })
			)
			sequence(
				terminal(literal, FALSE)
				action({ ret = SLiteralExpr.make(Boolean.class, literal.image); })
			)
			sequence(
				terminal(literal, NULL)
				action({ ret = SLiteralExpr.make(Void.class, literal.image); })
			)
		)
		action({ return dress(ret); })
	) */
	protected BUTree<? extends SExpr> parseLiteral() throws ParseException {
		Token literal;
		BUTree<? extends SExpr> ret;
		run();
		if (match(0, TokenType.INTEGER_LITERAL) != -1) {
			literal = parse(TokenType.INTEGER_LITERAL);
			ret = SLiteralExpr.make(Integer.class, literal.image);
		} else if (match(0, TokenType.LONG_LITERAL) != -1) {
			literal = parse(TokenType.LONG_LITERAL);
			ret = SLiteralExpr.make(Long.class, literal.image);
		} else if (match(0, TokenType.FLOAT_LITERAL) != -1) {
			literal = parse(TokenType.FLOAT_LITERAL);
			ret = SLiteralExpr.make(Float.class, literal.image);
		} else if (match(0, TokenType.DOUBLE_LITERAL) != -1) {
			literal = parse(TokenType.DOUBLE_LITERAL);
			ret = SLiteralExpr.make(Double.class, literal.image);
		} else if (match(0, TokenType.CHARACTER_LITERAL) != -1) {
			literal = parse(TokenType.CHARACTER_LITERAL);
			ret = SLiteralExpr.make(Character.class, literal.image);
		} else if (match(0, TokenType.STRING_LITERAL) != -1) {
			literal = parse(TokenType.STRING_LITERAL);
			ret = SLiteralExpr.make(String.class, literal.image);
		} else if (match(0, TokenType.TRUE) != -1) {
			literal = parse(TokenType.TRUE);
			ret = SLiteralExpr.make(Boolean.class, literal.image);
		} else if (match(0, TokenType.FALSE) != -1) {
			literal = parse(TokenType.FALSE);
			ret = SLiteralExpr.make(Boolean.class, literal.image);
		} else if (match(0, TokenType.NULL) != -1) {
			literal = parse(TokenType.NULL);
			ret = SLiteralExpr.make(Void.class, literal.image);
		} else {
			throw produceParseException(TokenType.NULL, TokenType.FALSE, TokenType.TRUE, TokenType.STRING_LITERAL, TokenType.CHARACTER_LITERAL, TokenType.DOUBLE_LITERAL, TokenType.FLOAT_LITERAL, TokenType.LONG_LITERAL, TokenType.INTEGER_LITERAL);
		}
		return dress(ret);
	}

	/* sequence(
		choice(
			sequence(
				terminal(literal, INTEGER_LITERAL)
			)
			sequence(
				terminal(literal, LONG_LITERAL)
			)
			sequence(
				terminal(literal, FLOAT_LITERAL)
			)
			sequence(
				terminal(literal, DOUBLE_LITERAL)
			)
			sequence(
				terminal(literal, CHARACTER_LITERAL)
			)
			sequence(
				terminal(literal, STRING_LITERAL)
			)
			sequence(
				terminal(literal, TRUE)
			)
			sequence(
				terminal(literal, FALSE)
			)
			sequence(
				terminal(literal, NULL)
			)
		)
	) */
	private int matchLiteral(int lookahead) {
		lookahead = matchLiteral_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			terminal(literal, INTEGER_LITERAL)
		)
		sequence(
			terminal(literal, LONG_LITERAL)
		)
		sequence(
			terminal(literal, FLOAT_LITERAL)
		)
		sequence(
			terminal(literal, DOUBLE_LITERAL)
		)
		sequence(
			terminal(literal, CHARACTER_LITERAL)
		)
		sequence(
			terminal(literal, STRING_LITERAL)
		)
		sequence(
			terminal(literal, TRUE)
		)
		sequence(
			terminal(literal, FALSE)
		)
		sequence(
			terminal(literal, NULL)
		)
	) */
	private int matchLiteral_2(int lookahead) {
		int newLookahead;
		newLookahead = matchLiteral_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchLiteral_2_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchLiteral_2_3(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchLiteral_2_4(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchLiteral_2_5(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchLiteral_2_6(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchLiteral_2_7(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchLiteral_2_8(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchLiteral_2_9(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		terminal(literal, INTEGER_LITERAL)
	) */
	private int matchLiteral_2_1(int lookahead) {
		lookahead = match(lookahead, TokenType.INTEGER_LITERAL);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(literal, LONG_LITERAL)
	) */
	private int matchLiteral_2_2(int lookahead) {
		lookahead = match(lookahead, TokenType.LONG_LITERAL);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(literal, FLOAT_LITERAL)
	) */
	private int matchLiteral_2_3(int lookahead) {
		lookahead = match(lookahead, TokenType.FLOAT_LITERAL);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(literal, DOUBLE_LITERAL)
	) */
	private int matchLiteral_2_4(int lookahead) {
		lookahead = match(lookahead, TokenType.DOUBLE_LITERAL);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(literal, CHARACTER_LITERAL)
	) */
	private int matchLiteral_2_5(int lookahead) {
		lookahead = match(lookahead, TokenType.CHARACTER_LITERAL);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(literal, STRING_LITERAL)
	) */
	private int matchLiteral_2_6(int lookahead) {
		lookahead = match(lookahead, TokenType.STRING_LITERAL);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(literal, TRUE)
	) */
	private int matchLiteral_2_7(int lookahead) {
		lookahead = match(lookahead, TokenType.TRUE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(literal, FALSE)
	) */
	private int matchLiteral_2_8(int lookahead) {
		lookahead = match(lookahead, TokenType.FALSE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(literal, NULL)
	) */
	private int matchLiteral_2_9(int lookahead) {
		lookahead = match(lookahead, TokenType.NULL);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(ret, PrimaryPrefix)
		zeroOrMore(
			action({ lateRun(); })
			nonTerminal(ret, PrimarySuffix)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parsePrimaryExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		ret = parsePrimaryPrefix();
		while (match(0, TokenType.DOUBLECOLON, TokenType.DOT, TokenType.LBRACKET) != -1) {
			lateRun();
			ret = parsePrimarySuffix(ret);
		}
		return ret;
	}

	/* sequence(
		nonTerminal(ret, PrimaryPrefix)
		zeroOrMore(
			nonTerminal(ret, PrimarySuffix)
		)
	) */
	private int matchPrimaryExpression(int lookahead) {
		lookahead = matchPrimaryPrefix(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchPrimaryExpression_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		nonTerminal(ret, PrimarySuffix)
	) */
	private int matchPrimaryExpression_2(int lookahead) {
		int newLookahead;
		newLookahead = matchPrimaryExpression_2_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchPrimaryExpression_2_1(lookahead);
		}
		return lookahead;
	}

	/* sequence(
		nonTerminal(ret, PrimarySuffix)
	) */
	private int matchPrimaryExpression_2_1(int lookahead) {
		lookahead = matchPrimarySuffix(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(ret, PrimaryPrefix)
		zeroOrMore(
			lookAhead(
				nonTerminal(PrimarySuffixWithoutSuper)
			)
			action({ lateRun(); })
			nonTerminal(ret, PrimarySuffixWithoutSuper)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parsePrimaryExpressionWithoutSuperSuffix() throws ParseException {
		BUTree<? extends SExpr> ret;
		ret = parsePrimaryPrefix();
		while (matchPrimaryExpressionWithoutSuperSuffix_lookahead1(0) != -1) {
			lateRun();
			ret = parsePrimarySuffixWithoutSuper(ret);
		}
		return ret;
	}

	/* sequence(
		nonTerminal(ret, PrimaryPrefix)
		zeroOrMore(
			lookAhead(
				nonTerminal(PrimarySuffixWithoutSuper)
			)
			nonTerminal(ret, PrimarySuffixWithoutSuper)
		)
	) */
	private int matchPrimaryExpressionWithoutSuperSuffix(int lookahead) {
		lookahead = matchPrimaryPrefix(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchPrimaryExpressionWithoutSuperSuffix_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		lookAhead(
			nonTerminal(PrimarySuffixWithoutSuper)
		)
		nonTerminal(ret, PrimarySuffixWithoutSuper)
	) */
	private int matchPrimaryExpressionWithoutSuperSuffix_2(int lookahead) {
		int newLookahead;
		newLookahead = matchPrimaryExpressionWithoutSuperSuffix_2_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchPrimaryExpressionWithoutSuperSuffix_2_1(lookahead);
		}
		return lookahead;
	}

	/* sequence(
		lookAhead(
			nonTerminal(PrimarySuffixWithoutSuper)
		)
		nonTerminal(ret, PrimarySuffixWithoutSuper)
	) */
	private int matchPrimaryExpressionWithoutSuperSuffix_2_1(int lookahead) {
		lookahead = matchPrimarySuffixWithoutSuper(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(PrimarySuffixWithoutSuper)
	) */
	private int matchPrimaryExpressionWithoutSuperSuffix_lookahead1(int lookahead) {
		lookahead = matchPrimarySuffixWithoutSuper(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		choice(
			nonTerminal(ret, Literal)
			sequence(
				action({ run(); })
				terminal(THIS)
				action({ ret = dress(SThisExpr.make(none())); })
			)
			sequence(
				action({ run(); })
				terminal(SUPER)
				action({ ret = dress(SSuperExpr.make(none())); })
				choice(
					sequence(
						action({ lateRun(); })
						terminal(DOT)
						choice(
							sequence(
								lookAhead(
									zeroOrOne(
										nonTerminal(TypeArguments)
									)
									nonTerminal(Name)
									terminal(LPAREN)
								)
								nonTerminal(ret, MethodInvocation)
							)
							nonTerminal(ret, FieldAccess)
						)
					)
					sequence(
						action({ lateRun(); })
						nonTerminal(ret, MethodReferenceSuffix)
					)
				)
			)
			nonTerminal(ret, AllocationExpression)
			sequence(
				lookAhead(
					nonTerminal(ResultType)
					terminal(DOT)
					terminal(CLASS)
				)
				action({ run(); })
				nonTerminal(type, ResultType)
				terminal(DOT)
				terminal(CLASS)
				action({ ret = dress(SClassExpr.make(type)); })
			)
			sequence(
				lookAhead(
					nonTerminal(ResultType)
					terminal(DOUBLECOLON)
				)
				action({ run(); })
				nonTerminal(type, ResultType)
				action({ ret = STypeExpr.make(type); })
				nonTerminal(ret, MethodReferenceSuffix)
			)
			sequence(
				lookAhead(
					zeroOrOne(
						nonTerminal(TypeArguments)
					)
					nonTerminal(Name)
					terminal(LPAREN)
				)
				action({ run(); })
				nonTerminal(ret, MethodInvocation)
			)
			sequence(
				nonTerminal(ret, Name)
				zeroOrOne(
					action({ lateRun(); })
					terminal(ARROW)
					nonTerminal(ret, LambdaBody)
				)
			)
			sequence(
				action({ run(); })
				terminal(LPAREN)
				choice(
					sequence(
						terminal(RPAREN)
						terminal(ARROW)
						nonTerminal(ret, LambdaBody)
					)
					sequence(
						lookAhead(
							nonTerminal(Name)
							terminal(RPAREN)
							terminal(ARROW)
						)
						nonTerminal(ret, Name)
						terminal(RPAREN)
						terminal(ARROW)
						nonTerminal(ret, LambdaBody)
					)
					sequence(
						lookAhead(
							nonTerminal(Name)
							terminal(COMMA)
						)
						nonTerminal(params, InferredFormalParameterList)
						terminal(RPAREN)
						terminal(ARROW)
						nonTerminal(ret, LambdaBody)
					)
					sequence(
						lookAhead({ isLambda() })
						nonTerminal(params, FormalParameterList)
						terminal(RPAREN)
						terminal(ARROW)
						nonTerminal(ret, LambdaBody)
					)
					sequence(
						nonTerminal(ret, Expression)
						terminal(RPAREN)
						action({ ret = dress(SParenthesizedExpr.make(ret)); })
					)
				)
			)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parsePrimaryPrefix() throws ParseException {
		BUTree<? extends SExpr> ret = null;
		BUTree<SNodeList> typeArgs = null;
		BUTree<SNodeList> params;
		BUTree<? extends SType> type;
		if (match(0, TokenType.LONG_LITERAL, TokenType.INTEGER_LITERAL, TokenType.NULL, TokenType.FALSE, TokenType.TRUE, TokenType.STRING_LITERAL, TokenType.CHARACTER_LITERAL, TokenType.DOUBLE_LITERAL, TokenType.FLOAT_LITERAL) != -1) {
			ret = parseLiteral();
		} else if (match(0, TokenType.THIS) != -1) {
			run();
			parse(TokenType.THIS);
			ret = dress(SThisExpr.make(none()));
		} else if (match(0, TokenType.SUPER) != -1) {
			run();
			parse(TokenType.SUPER);
			ret = dress(SSuperExpr.make(none()));
			if (match(0, TokenType.DOT) != -1) {
				lateRun();
				parse(TokenType.DOT);
				if (matchPrimaryPrefix_lookahead1(0) != -1) {
					ret = parseMethodInvocation(ret);
				} else if (match(0, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER) != -1) {
					ret = parseFieldAccess(ret);
				} else {
					throw produceParseException(TokenType.IDENTIFIER, TokenType.NODE_VARIABLE, TokenType.LT);
				}
			} else if (match(0, TokenType.DOUBLECOLON) != -1) {
				lateRun();
				ret = parseMethodReferenceSuffix(ret);
			} else {
				throw produceParseException(TokenType.DOUBLECOLON, TokenType.DOT);
			}
		} else if (match(0, TokenType.NEW) != -1) {
			ret = parseAllocationExpression(null);
		} else if (matchPrimaryPrefix_lookahead2(0) != -1) {
			run();
			type = parseResultType();
			parse(TokenType.DOT);
			parse(TokenType.CLASS);
			ret = dress(SClassExpr.make(type));
		} else if (matchPrimaryPrefix_lookahead3(0) != -1) {
			run();
			type = parseResultType();
			ret = STypeExpr.make(type);
			ret = parseMethodReferenceSuffix(ret);
		} else if (matchPrimaryPrefix_lookahead4(0) != -1) {
			run();
			ret = parseMethodInvocation(null);
		} else if (match(0, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER) != -1) {
			ret = parseName();
			if (match(0, TokenType.ARROW) != -1) {
				lateRun();
				parse(TokenType.ARROW);
				ret = parseLambdaBody(singletonList(makeFormalParameter((BUTree<SName>) ret)), false);
			}
		} else if (match(0, TokenType.LPAREN) != -1) {
			run();
			parse(TokenType.LPAREN);
			if (match(0, TokenType.RPAREN) != -1) {
				parse(TokenType.RPAREN);
				parse(TokenType.ARROW);
				ret = parseLambdaBody(emptyList(), true);
			} else if (matchPrimaryPrefix_lookahead5(0) != -1) {
				ret = parseName();
				parse(TokenType.RPAREN);
				parse(TokenType.ARROW);
				ret = parseLambdaBody(singletonList(makeFormalParameter((BUTree<SName>) ret)), true);
			} else if (matchPrimaryPrefix_lookahead6(0) != -1) {
				params = parseInferredFormalParameterList();
				parse(TokenType.RPAREN);
				parse(TokenType.ARROW);
				ret = parseLambdaBody(params, true);
			} else if (isLambda(0)) {
				params = parseFormalParameterList();
				parse(TokenType.RPAREN);
				parse(TokenType.ARROW);
				ret = parseLambdaBody(params, true);
			} else if (match(0, TokenType.PLUS, TokenType.MINUS, TokenType.TILDE, TokenType.BANG, TokenType.LPAREN, TokenType.IDENTIFIER, TokenType.NODE_VARIABLE, TokenType.BOOLEAN, TokenType.INT, TokenType.SHORT, TokenType.BYTE, TokenType.CHAR, TokenType.DOUBLE, TokenType.FLOAT, TokenType.LONG, TokenType.VOID, TokenType.NEW, TokenType.SUPER, TokenType.THIS, TokenType.LT, TokenType.CHARACTER_LITERAL, TokenType.STRING_LITERAL, TokenType.FLOAT_LITERAL, TokenType.DOUBLE_LITERAL, TokenType.NULL, TokenType.TRUE, TokenType.FALSE, TokenType.INTEGER_LITERAL, TokenType.LONG_LITERAL, TokenType.INCR, TokenType.DECR) != -1) {
				ret = parseExpression();
				parse(TokenType.RPAREN);
				ret = dress(SParenthesizedExpr.make(ret));
			} else {
				throw produceParseException(TokenType.IDENTIFIER, TokenType.NODE_VARIABLE, TokenType.LT, TokenType.VOID, TokenType.BOOLEAN, TokenType.CHAR, TokenType.FLOAT, TokenType.DOUBLE, TokenType.INT, TokenType.LONG, TokenType.BYTE, TokenType.SHORT, TokenType.NEW, TokenType.LPAREN, TokenType.SUPER, TokenType.THIS, TokenType.FLOAT_LITERAL, TokenType.DOUBLE_LITERAL, TokenType.INTEGER_LITERAL, TokenType.LONG_LITERAL, TokenType.TRUE, TokenType.FALSE, TokenType.CHARACTER_LITERAL, TokenType.STRING_LITERAL, TokenType.NULL, TokenType.TILDE, TokenType.BANG, TokenType.PLUS, TokenType.MINUS, TokenType.DECR, TokenType.INCR, TokenType.NODE_LIST_VARIABLE, TokenType.DEFAULT, TokenType.ABSTRACT, TokenType.FINAL, TokenType.STATIC, TokenType.PUBLIC, TokenType.PRIVATE, TokenType.PROTECTED, TokenType.AT, TokenType.STRICTFP, TokenType.VOLATILE, TokenType.TRANSIENT, TokenType.NATIVE, TokenType.SYNCHRONIZED, TokenType.RPAREN);
			}
		} else {
			throw produceParseException(TokenType.LPAREN, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER, TokenType.LT, TokenType.VOID, TokenType.CHAR, TokenType.BOOLEAN, TokenType.LONG, TokenType.INT, TokenType.SHORT, TokenType.BYTE, TokenType.DOUBLE, TokenType.FLOAT, TokenType.NEW, TokenType.SUPER, TokenType.THIS, TokenType.NULL, TokenType.FALSE, TokenType.TRUE, TokenType.STRING_LITERAL, TokenType.CHARACTER_LITERAL, TokenType.DOUBLE_LITERAL, TokenType.FLOAT_LITERAL, TokenType.LONG_LITERAL, TokenType.INTEGER_LITERAL);
		}
		return ret;
	}

	/* sequence(
		choice(
			nonTerminal(ret, Literal)
			sequence(
				terminal(THIS)
			)
			sequence(
				terminal(SUPER)
				choice(
					sequence(
						terminal(DOT)
						choice(
							sequence(
								lookAhead(
									zeroOrOne(
										nonTerminal(TypeArguments)
									)
									nonTerminal(Name)
									terminal(LPAREN)
								)
								nonTerminal(ret, MethodInvocation)
							)
							nonTerminal(ret, FieldAccess)
						)
					)
					sequence(
						nonTerminal(ret, MethodReferenceSuffix)
					)
				)
			)
			nonTerminal(ret, AllocationExpression)
			sequence(
				lookAhead(
					nonTerminal(ResultType)
					terminal(DOT)
					terminal(CLASS)
				)
				nonTerminal(type, ResultType)
				terminal(DOT)
				terminal(CLASS)
			)
			sequence(
				lookAhead(
					nonTerminal(ResultType)
					terminal(DOUBLECOLON)
				)
				nonTerminal(type, ResultType)
				nonTerminal(ret, MethodReferenceSuffix)
			)
			sequence(
				lookAhead(
					zeroOrOne(
						nonTerminal(TypeArguments)
					)
					nonTerminal(Name)
					terminal(LPAREN)
				)
				nonTerminal(ret, MethodInvocation)
			)
			sequence(
				nonTerminal(ret, Name)
				zeroOrOne(
					terminal(ARROW)
					nonTerminal(ret, LambdaBody)
				)
			)
			sequence(
				terminal(LPAREN)
				choice(
					sequence(
						terminal(RPAREN)
						terminal(ARROW)
						nonTerminal(ret, LambdaBody)
					)
					sequence(
						lookAhead(
							nonTerminal(Name)
							terminal(RPAREN)
							terminal(ARROW)
						)
						nonTerminal(ret, Name)
						terminal(RPAREN)
						terminal(ARROW)
						nonTerminal(ret, LambdaBody)
					)
					sequence(
						lookAhead(
							nonTerminal(Name)
							terminal(COMMA)
						)
						nonTerminal(params, InferredFormalParameterList)
						terminal(RPAREN)
						terminal(ARROW)
						nonTerminal(ret, LambdaBody)
					)
					sequence(
						lookAhead({ isLambda() })
						nonTerminal(params, FormalParameterList)
						terminal(RPAREN)
						terminal(ARROW)
						nonTerminal(ret, LambdaBody)
					)
					sequence(
						nonTerminal(ret, Expression)
						terminal(RPAREN)
					)
				)
			)
		)
	) */
	private int matchPrimaryPrefix(int lookahead) {
		lookahead = matchPrimaryPrefix_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		nonTerminal(ret, Literal)
		sequence(
			terminal(THIS)
		)
		sequence(
			terminal(SUPER)
			choice(
				sequence(
					terminal(DOT)
					choice(
						sequence(
							lookAhead(
								zeroOrOne(
									nonTerminal(TypeArguments)
								)
								nonTerminal(Name)
								terminal(LPAREN)
							)
							nonTerminal(ret, MethodInvocation)
						)
						nonTerminal(ret, FieldAccess)
					)
				)
				sequence(
					nonTerminal(ret, MethodReferenceSuffix)
				)
			)
		)
		nonTerminal(ret, AllocationExpression)
		sequence(
			lookAhead(
				nonTerminal(ResultType)
				terminal(DOT)
				terminal(CLASS)
			)
			nonTerminal(type, ResultType)
			terminal(DOT)
			terminal(CLASS)
		)
		sequence(
			lookAhead(
				nonTerminal(ResultType)
				terminal(DOUBLECOLON)
			)
			nonTerminal(type, ResultType)
			nonTerminal(ret, MethodReferenceSuffix)
		)
		sequence(
			lookAhead(
				zeroOrOne(
					nonTerminal(TypeArguments)
				)
				nonTerminal(Name)
				terminal(LPAREN)
			)
			nonTerminal(ret, MethodInvocation)
		)
		sequence(
			nonTerminal(ret, Name)
			zeroOrOne(
				terminal(ARROW)
				nonTerminal(ret, LambdaBody)
			)
		)
		sequence(
			terminal(LPAREN)
			choice(
				sequence(
					terminal(RPAREN)
					terminal(ARROW)
					nonTerminal(ret, LambdaBody)
				)
				sequence(
					lookAhead(
						nonTerminal(Name)
						terminal(RPAREN)
						terminal(ARROW)
					)
					nonTerminal(ret, Name)
					terminal(RPAREN)
					terminal(ARROW)
					nonTerminal(ret, LambdaBody)
				)
				sequence(
					lookAhead(
						nonTerminal(Name)
						terminal(COMMA)
					)
					nonTerminal(params, InferredFormalParameterList)
					terminal(RPAREN)
					terminal(ARROW)
					nonTerminal(ret, LambdaBody)
				)
				sequence(
					lookAhead({ isLambda() })
					nonTerminal(params, FormalParameterList)
					terminal(RPAREN)
					terminal(ARROW)
					nonTerminal(ret, LambdaBody)
				)
				sequence(
					nonTerminal(ret, Expression)
					terminal(RPAREN)
				)
			)
		)
	) */
	private int matchPrimaryPrefix_1(int lookahead) {
		int newLookahead;
		newLookahead = matchLiteral(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchPrimaryPrefix_1_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchPrimaryPrefix_1_3(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchAllocationExpression(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchPrimaryPrefix_1_5(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchPrimaryPrefix_1_6(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchPrimaryPrefix_1_7(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchPrimaryPrefix_1_8(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchPrimaryPrefix_1_9(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		terminal(THIS)
	) */
	private int matchPrimaryPrefix_1_2(int lookahead) {
		lookahead = match(lookahead, TokenType.THIS);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(SUPER)
		choice(
			sequence(
				terminal(DOT)
				choice(
					sequence(
						lookAhead(
							zeroOrOne(
								nonTerminal(TypeArguments)
							)
							nonTerminal(Name)
							terminal(LPAREN)
						)
						nonTerminal(ret, MethodInvocation)
					)
					nonTerminal(ret, FieldAccess)
				)
			)
			sequence(
				nonTerminal(ret, MethodReferenceSuffix)
			)
		)
	) */
	private int matchPrimaryPrefix_1_3(int lookahead) {
		lookahead = match(lookahead, TokenType.SUPER);
		if (lookahead == -1)
			return -1;
		lookahead = matchPrimaryPrefix_1_3_4(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			terminal(DOT)
			choice(
				sequence(
					lookAhead(
						zeroOrOne(
							nonTerminal(TypeArguments)
						)
						nonTerminal(Name)
						terminal(LPAREN)
					)
					nonTerminal(ret, MethodInvocation)
				)
				nonTerminal(ret, FieldAccess)
			)
		)
		sequence(
			nonTerminal(ret, MethodReferenceSuffix)
		)
	) */
	private int matchPrimaryPrefix_1_3_4(int lookahead) {
		int newLookahead;
		newLookahead = matchPrimaryPrefix_1_3_4_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchPrimaryPrefix_1_3_4_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		terminal(DOT)
		choice(
			sequence(
				lookAhead(
					zeroOrOne(
						nonTerminal(TypeArguments)
					)
					nonTerminal(Name)
					terminal(LPAREN)
				)
				nonTerminal(ret, MethodInvocation)
			)
			nonTerminal(ret, FieldAccess)
		)
	) */
	private int matchPrimaryPrefix_1_3_4_1(int lookahead) {
		lookahead = match(lookahead, TokenType.DOT);
		if (lookahead == -1)
			return -1;
		lookahead = matchPrimaryPrefix_1_3_4_1_3(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			lookAhead(
				zeroOrOne(
					nonTerminal(TypeArguments)
				)
				nonTerminal(Name)
				terminal(LPAREN)
			)
			nonTerminal(ret, MethodInvocation)
		)
		nonTerminal(ret, FieldAccess)
	) */
	private int matchPrimaryPrefix_1_3_4_1_3(int lookahead) {
		int newLookahead;
		newLookahead = matchPrimaryPrefix_1_3_4_1_3_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchFieldAccess(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		lookAhead(
			zeroOrOne(
				nonTerminal(TypeArguments)
			)
			nonTerminal(Name)
			terminal(LPAREN)
		)
		nonTerminal(ret, MethodInvocation)
	) */
	private int matchPrimaryPrefix_1_3_4_1_3_1(int lookahead) {
		lookahead = matchMethodInvocation(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(ret, MethodReferenceSuffix)
	) */
	private int matchPrimaryPrefix_1_3_4_2(int lookahead) {
		lookahead = matchMethodReferenceSuffix(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead(
			nonTerminal(ResultType)
			terminal(DOT)
			terminal(CLASS)
		)
		nonTerminal(type, ResultType)
		terminal(DOT)
		terminal(CLASS)
	) */
	private int matchPrimaryPrefix_1_5(int lookahead) {
		lookahead = matchResultType(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.DOT);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.CLASS);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead(
			nonTerminal(ResultType)
			terminal(DOUBLECOLON)
		)
		nonTerminal(type, ResultType)
		nonTerminal(ret, MethodReferenceSuffix)
	) */
	private int matchPrimaryPrefix_1_6(int lookahead) {
		lookahead = matchResultType(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchMethodReferenceSuffix(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead(
			zeroOrOne(
				nonTerminal(TypeArguments)
			)
			nonTerminal(Name)
			terminal(LPAREN)
		)
		nonTerminal(ret, MethodInvocation)
	) */
	private int matchPrimaryPrefix_1_7(int lookahead) {
		lookahead = matchMethodInvocation(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(ret, Name)
		zeroOrOne(
			terminal(ARROW)
			nonTerminal(ret, LambdaBody)
		)
	) */
	private int matchPrimaryPrefix_1_8(int lookahead) {
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchPrimaryPrefix_1_8_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		terminal(ARROW)
		nonTerminal(ret, LambdaBody)
	) */
	private int matchPrimaryPrefix_1_8_2(int lookahead) {
		int newLookahead;
		newLookahead = matchPrimaryPrefix_1_8_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		terminal(ARROW)
		nonTerminal(ret, LambdaBody)
	) */
	private int matchPrimaryPrefix_1_8_2_1(int lookahead) {
		lookahead = match(lookahead, TokenType.ARROW);
		if (lookahead == -1)
			return -1;
		lookahead = matchLambdaBody(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(LPAREN)
		choice(
			sequence(
				terminal(RPAREN)
				terminal(ARROW)
				nonTerminal(ret, LambdaBody)
			)
			sequence(
				lookAhead(
					nonTerminal(Name)
					terminal(RPAREN)
					terminal(ARROW)
				)
				nonTerminal(ret, Name)
				terminal(RPAREN)
				terminal(ARROW)
				nonTerminal(ret, LambdaBody)
			)
			sequence(
				lookAhead(
					nonTerminal(Name)
					terminal(COMMA)
				)
				nonTerminal(params, InferredFormalParameterList)
				terminal(RPAREN)
				terminal(ARROW)
				nonTerminal(ret, LambdaBody)
			)
			sequence(
				lookAhead({ isLambda() })
				nonTerminal(params, FormalParameterList)
				terminal(RPAREN)
				terminal(ARROW)
				nonTerminal(ret, LambdaBody)
			)
			sequence(
				nonTerminal(ret, Expression)
				terminal(RPAREN)
			)
		)
	) */
	private int matchPrimaryPrefix_1_9(int lookahead) {
		lookahead = match(lookahead, TokenType.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchPrimaryPrefix_1_9_3(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			terminal(RPAREN)
			terminal(ARROW)
			nonTerminal(ret, LambdaBody)
		)
		sequence(
			lookAhead(
				nonTerminal(Name)
				terminal(RPAREN)
				terminal(ARROW)
			)
			nonTerminal(ret, Name)
			terminal(RPAREN)
			terminal(ARROW)
			nonTerminal(ret, LambdaBody)
		)
		sequence(
			lookAhead(
				nonTerminal(Name)
				terminal(COMMA)
			)
			nonTerminal(params, InferredFormalParameterList)
			terminal(RPAREN)
			terminal(ARROW)
			nonTerminal(ret, LambdaBody)
		)
		sequence(
			lookAhead({ isLambda() })
			nonTerminal(params, FormalParameterList)
			terminal(RPAREN)
			terminal(ARROW)
			nonTerminal(ret, LambdaBody)
		)
		sequence(
			nonTerminal(ret, Expression)
			terminal(RPAREN)
		)
	) */
	private int matchPrimaryPrefix_1_9_3(int lookahead) {
		int newLookahead;
		newLookahead = matchPrimaryPrefix_1_9_3_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchPrimaryPrefix_1_9_3_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchPrimaryPrefix_1_9_3_3(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchPrimaryPrefix_1_9_3_4(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchPrimaryPrefix_1_9_3_5(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		terminal(RPAREN)
		terminal(ARROW)
		nonTerminal(ret, LambdaBody)
	) */
	private int matchPrimaryPrefix_1_9_3_1(int lookahead) {
		lookahead = match(lookahead, TokenType.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.ARROW);
		if (lookahead == -1)
			return -1;
		lookahead = matchLambdaBody(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead(
			nonTerminal(Name)
			terminal(RPAREN)
			terminal(ARROW)
		)
		nonTerminal(ret, Name)
		terminal(RPAREN)
		terminal(ARROW)
		nonTerminal(ret, LambdaBody)
	) */
	private int matchPrimaryPrefix_1_9_3_2(int lookahead) {
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.ARROW);
		if (lookahead == -1)
			return -1;
		lookahead = matchLambdaBody(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead(
			nonTerminal(Name)
			terminal(COMMA)
		)
		nonTerminal(params, InferredFormalParameterList)
		terminal(RPAREN)
		terminal(ARROW)
		nonTerminal(ret, LambdaBody)
	) */
	private int matchPrimaryPrefix_1_9_3_3(int lookahead) {
		lookahead = matchInferredFormalParameterList(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.ARROW);
		if (lookahead == -1)
			return -1;
		lookahead = matchLambdaBody(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead({ isLambda() })
		nonTerminal(params, FormalParameterList)
		terminal(RPAREN)
		terminal(ARROW)
		nonTerminal(ret, LambdaBody)
	) */
	private int matchPrimaryPrefix_1_9_3_4(int lookahead) {
		lookahead = isLambda(lookahead) ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = matchFormalParameterList(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.ARROW);
		if (lookahead == -1)
			return -1;
		lookahead = matchLambdaBody(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(ret, Expression)
		terminal(RPAREN)
	) */
	private int matchPrimaryPrefix_1_9_3_5(int lookahead) {
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.RPAREN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		zeroOrOne(
			nonTerminal(TypeArguments)
		)
		nonTerminal(Name)
		terminal(LPAREN)
	) */
	private int matchPrimaryPrefix_lookahead1(int lookahead) {
		lookahead = matchPrimaryPrefix_lookahead1_1(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.LPAREN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		nonTerminal(TypeArguments)
	) */
	private int matchPrimaryPrefix_lookahead1_1(int lookahead) {
		int newLookahead;
		newLookahead = matchPrimaryPrefix_lookahead1_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		nonTerminal(TypeArguments)
	) */
	private int matchPrimaryPrefix_lookahead1_1_1(int lookahead) {
		lookahead = matchTypeArguments(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(ResultType)
		terminal(DOT)
		terminal(CLASS)
	) */
	private int matchPrimaryPrefix_lookahead2(int lookahead) {
		lookahead = matchResultType(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.DOT);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.CLASS);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(ResultType)
		terminal(DOUBLECOLON)
	) */
	private int matchPrimaryPrefix_lookahead3(int lookahead) {
		lookahead = matchResultType(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.DOUBLECOLON);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		zeroOrOne(
			nonTerminal(TypeArguments)
		)
		nonTerminal(Name)
		terminal(LPAREN)
	) */
	private int matchPrimaryPrefix_lookahead4(int lookahead) {
		lookahead = matchPrimaryPrefix_lookahead4_1(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.LPAREN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		nonTerminal(TypeArguments)
	) */
	private int matchPrimaryPrefix_lookahead4_1(int lookahead) {
		int newLookahead;
		newLookahead = matchPrimaryPrefix_lookahead4_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		nonTerminal(TypeArguments)
	) */
	private int matchPrimaryPrefix_lookahead4_1_1(int lookahead) {
		lookahead = matchTypeArguments(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(Name)
		terminal(RPAREN)
		terminal(ARROW)
	) */
	private int matchPrimaryPrefix_lookahead5(int lookahead) {
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.ARROW);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(Name)
		terminal(COMMA)
	) */
	private int matchPrimaryPrefix_lookahead6(int lookahead) {
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.COMMA);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		choice(
			sequence(
				lookAhead(2)
				nonTerminal(ret, PrimarySuffixWithoutSuper)
			)
			sequence(
				terminal(DOT)
				terminal(SUPER)
				action({ ret = dress(SSuperExpr.make(optionOf(scope))); })
			)
			nonTerminal(ret, MethodReferenceSuffix)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parsePrimarySuffix(BUTree<? extends SExpr> scope) throws ParseException {
		BUTree<? extends SExpr> ret;
		if (matchPrimarySuffix_lookahead1(0) != -1) {
			ret = parsePrimarySuffixWithoutSuper(scope);
		} else if (match(0, TokenType.DOT) != -1) {
			parse(TokenType.DOT);
			parse(TokenType.SUPER);
			ret = dress(SSuperExpr.make(optionOf(scope)));
		} else if (match(0, TokenType.DOUBLECOLON) != -1) {
			ret = parseMethodReferenceSuffix(scope);
		} else {
			throw produceParseException(TokenType.DOUBLECOLON, TokenType.DOT, TokenType.LBRACKET);
		}
		return ret;
	}

	/* sequence(
		choice(
			sequence(
				lookAhead(2)
				nonTerminal(ret, PrimarySuffixWithoutSuper)
			)
			sequence(
				terminal(DOT)
				terminal(SUPER)
			)
			nonTerminal(ret, MethodReferenceSuffix)
		)
	) */
	private int matchPrimarySuffix(int lookahead) {
		lookahead = matchPrimarySuffix_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			lookAhead(2)
			nonTerminal(ret, PrimarySuffixWithoutSuper)
		)
		sequence(
			terminal(DOT)
			terminal(SUPER)
		)
		nonTerminal(ret, MethodReferenceSuffix)
	) */
	private int matchPrimarySuffix_1(int lookahead) {
		int newLookahead;
		newLookahead = matchPrimarySuffix_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchPrimarySuffix_1_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchMethodReferenceSuffix(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		lookAhead(2)
		nonTerminal(ret, PrimarySuffixWithoutSuper)
	) */
	private int matchPrimarySuffix_1_1(int lookahead) {
		lookahead = matchPrimarySuffixWithoutSuper(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(DOT)
		terminal(SUPER)
	) */
	private int matchPrimarySuffix_1_2(int lookahead) {
		lookahead = match(lookahead, TokenType.DOT);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.SUPER);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead(2)
		nonTerminal(ret, PrimarySuffixWithoutSuper)
	) */
	private int matchPrimarySuffix_lookahead1(int lookahead) {
		if (match(0, TokenType.DOT) != -1) {
			if (match(1, TokenType.NEW) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.LT) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.THIS) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, TokenType.LBRACKET) != -1) {
			if (match(1, TokenType.NEW) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.LT) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.INT) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.LONG) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.NULL) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.BANG) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.DECR) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.INCR) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.THIS) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.VOID) != -1) {
				return lookahead;
			}
			if (match(1, TokenType.PLUS) != -1) {
				return lookahead;
			}
		}
		return -1;
	}

	/* sequence(
		choice(
			sequence(
				terminal(DOT)
				choice(
					sequence(
						terminal(THIS)
						action({ ret = dress(SThisExpr.make(optionOf(scope))); })
					)
					nonTerminal(ret, AllocationExpression)
					sequence(
						lookAhead(
							zeroOrOne(
								nonTerminal(TypeArguments)
							)
							nonTerminal(Name)
							terminal(LPAREN)
						)
						nonTerminal(ret, MethodInvocation)
					)
					nonTerminal(ret, FieldAccess)
				)
			)
			sequence(
				terminal(LBRACKET)
				nonTerminal(ret, Expression)
				terminal(RBRACKET)
				action({ ret = dress(SArrayAccessExpr.make(scope, ret)); })
			)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parsePrimarySuffixWithoutSuper(BUTree<? extends SExpr> scope) throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<SName> name;
		if (match(0, TokenType.DOT) != -1) {
			parse(TokenType.DOT);
			if (match(0, TokenType.THIS) != -1) {
				parse(TokenType.THIS);
				ret = dress(SThisExpr.make(optionOf(scope)));
			} else if (match(0, TokenType.NEW) != -1) {
				ret = parseAllocationExpression(scope);
			} else if (matchPrimarySuffixWithoutSuper_lookahead1(0) != -1) {
				ret = parseMethodInvocation(scope);
			} else if (match(0, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER) != -1) {
				ret = parseFieldAccess(scope);
			} else {
				throw produceParseException(TokenType.NODE_VARIABLE, TokenType.IDENTIFIER, TokenType.LT, TokenType.NEW, TokenType.THIS);
			}
		} else if (match(0, TokenType.LBRACKET) != -1) {
			parse(TokenType.LBRACKET);
			ret = parseExpression();
			parse(TokenType.RBRACKET);
			ret = dress(SArrayAccessExpr.make(scope, ret));
		} else {
			throw produceParseException(TokenType.LBRACKET, TokenType.DOT);
		}
		return ret;
	}

	/* sequence(
		choice(
			sequence(
				terminal(DOT)
				choice(
					sequence(
						terminal(THIS)
					)
					nonTerminal(ret, AllocationExpression)
					sequence(
						lookAhead(
							zeroOrOne(
								nonTerminal(TypeArguments)
							)
							nonTerminal(Name)
							terminal(LPAREN)
						)
						nonTerminal(ret, MethodInvocation)
					)
					nonTerminal(ret, FieldAccess)
				)
			)
			sequence(
				terminal(LBRACKET)
				nonTerminal(ret, Expression)
				terminal(RBRACKET)
			)
		)
	) */
	private int matchPrimarySuffixWithoutSuper(int lookahead) {
		lookahead = matchPrimarySuffixWithoutSuper_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			terminal(DOT)
			choice(
				sequence(
					terminal(THIS)
				)
				nonTerminal(ret, AllocationExpression)
				sequence(
					lookAhead(
						zeroOrOne(
							nonTerminal(TypeArguments)
						)
						nonTerminal(Name)
						terminal(LPAREN)
					)
					nonTerminal(ret, MethodInvocation)
				)
				nonTerminal(ret, FieldAccess)
			)
		)
		sequence(
			terminal(LBRACKET)
			nonTerminal(ret, Expression)
			terminal(RBRACKET)
		)
	) */
	private int matchPrimarySuffixWithoutSuper_1(int lookahead) {
		int newLookahead;
		newLookahead = matchPrimarySuffixWithoutSuper_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchPrimarySuffixWithoutSuper_1_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		terminal(DOT)
		choice(
			sequence(
				terminal(THIS)
			)
			nonTerminal(ret, AllocationExpression)
			sequence(
				lookAhead(
					zeroOrOne(
						nonTerminal(TypeArguments)
					)
					nonTerminal(Name)
					terminal(LPAREN)
				)
				nonTerminal(ret, MethodInvocation)
			)
			nonTerminal(ret, FieldAccess)
		)
	) */
	private int matchPrimarySuffixWithoutSuper_1_1(int lookahead) {
		lookahead = match(lookahead, TokenType.DOT);
		if (lookahead == -1)
			return -1;
		lookahead = matchPrimarySuffixWithoutSuper_1_1_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			terminal(THIS)
		)
		nonTerminal(ret, AllocationExpression)
		sequence(
			lookAhead(
				zeroOrOne(
					nonTerminal(TypeArguments)
				)
				nonTerminal(Name)
				terminal(LPAREN)
			)
			nonTerminal(ret, MethodInvocation)
		)
		nonTerminal(ret, FieldAccess)
	) */
	private int matchPrimarySuffixWithoutSuper_1_1_2(int lookahead) {
		int newLookahead;
		newLookahead = matchPrimarySuffixWithoutSuper_1_1_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchAllocationExpression(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchPrimarySuffixWithoutSuper_1_1_2_3(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchFieldAccess(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		terminal(THIS)
	) */
	private int matchPrimarySuffixWithoutSuper_1_1_2_1(int lookahead) {
		lookahead = match(lookahead, TokenType.THIS);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead(
			zeroOrOne(
				nonTerminal(TypeArguments)
			)
			nonTerminal(Name)
			terminal(LPAREN)
		)
		nonTerminal(ret, MethodInvocation)
	) */
	private int matchPrimarySuffixWithoutSuper_1_1_2_3(int lookahead) {
		lookahead = matchMethodInvocation(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(LBRACKET)
		nonTerminal(ret, Expression)
		terminal(RBRACKET)
	) */
	private int matchPrimarySuffixWithoutSuper_1_2(int lookahead) {
		lookahead = match(lookahead, TokenType.LBRACKET);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.RBRACKET);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		zeroOrOne(
			nonTerminal(TypeArguments)
		)
		nonTerminal(Name)
		terminal(LPAREN)
	) */
	private int matchPrimarySuffixWithoutSuper_lookahead1(int lookahead) {
		lookahead = matchPrimarySuffixWithoutSuper_lookahead1_1(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.LPAREN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		nonTerminal(TypeArguments)
	) */
	private int matchPrimarySuffixWithoutSuper_lookahead1_1(int lookahead) {
		int newLookahead;
		newLookahead = matchPrimarySuffixWithoutSuper_lookahead1_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		nonTerminal(TypeArguments)
	) */
	private int matchPrimarySuffixWithoutSuper_lookahead1_1_1(int lookahead) {
		lookahead = matchTypeArguments(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(name, Name)
		action({ return dress(SFieldAccessExpr.make(optionOf(scope), name)); })
	) */
	protected BUTree<? extends SExpr> parseFieldAccess(BUTree<? extends SExpr> scope) throws ParseException {
		BUTree<SName> name;
		name = parseName();
		return dress(SFieldAccessExpr.make(optionOf(scope), name));
	}

	/* sequence(
		nonTerminal(name, Name)
	) */
	private int matchFieldAccess(int lookahead) {
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		zeroOrOne(
			nonTerminal(typeArgs, TypeArguments)
		)
		nonTerminal(name, Name)
		nonTerminal(args, Arguments)
		action({ return dress(SMethodInvocationExpr.make(optionOf(scope), ensureNotNull(typeArgs), name, args)); })
	) */
	protected BUTree<? extends SExpr> parseMethodInvocation(BUTree<? extends SExpr> scope) throws ParseException {
		BUTree<SNodeList> typeArgs = null;
		BUTree<SName> name;
		BUTree<SNodeList> args = null;
		BUTree<? extends SExpr> ret;
		if (match(0, TokenType.LT) != -1) {
			typeArgs = parseTypeArguments();
		}
		name = parseName();
		args = parseArguments();
		return dress(SMethodInvocationExpr.make(optionOf(scope), ensureNotNull(typeArgs), name, args));
	}

	/* sequence(
		zeroOrOne(
			nonTerminal(typeArgs, TypeArguments)
		)
		nonTerminal(name, Name)
		nonTerminal(args, Arguments)
	) */
	private int matchMethodInvocation(int lookahead) {
		lookahead = matchMethodInvocation_1(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchArguments(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		nonTerminal(typeArgs, TypeArguments)
	) */
	private int matchMethodInvocation_1(int lookahead) {
		int newLookahead;
		newLookahead = matchMethodInvocation_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		nonTerminal(typeArgs, TypeArguments)
	) */
	private int matchMethodInvocation_1_1(int lookahead) {
		lookahead = matchTypeArguments(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(LPAREN)
		zeroOrOne(
			choice(
				sequence(
					nonTerminal(expr, Expression)
					action({ ret = append(ret, expr); })
					zeroOrMore(
						terminal(COMMA)
						nonTerminal(expr, Expression)
						action({ ret = append(ret, expr); })
					)
				)
				sequence(
					lookAhead({ quotesMode })
					nonTerminal(ret, NodeListVar)
				)
			)
		)
		terminal(RPAREN)
		action({ return ret; })
	) */
	protected BUTree<SNodeList> parseArguments() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<? extends SExpr> expr;
		parse(TokenType.LPAREN);
		if (match(0, TokenType.LPAREN, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER, TokenType.PLUS, TokenType.MINUS, TokenType.TILDE, TokenType.BANG, TokenType.LT, TokenType.CHAR, TokenType.BOOLEAN, TokenType.SHORT, TokenType.BYTE, TokenType.LONG, TokenType.INT, TokenType.DOUBLE, TokenType.FLOAT, TokenType.VOID, TokenType.SUPER, TokenType.THIS, TokenType.NEW, TokenType.CHARACTER_LITERAL, TokenType.STRING_LITERAL, TokenType.FLOAT_LITERAL, TokenType.DOUBLE_LITERAL, TokenType.NULL, TokenType.TRUE, TokenType.FALSE, TokenType.INTEGER_LITERAL, TokenType.LONG_LITERAL, TokenType.DECR, TokenType.INCR, TokenType.NODE_LIST_VARIABLE) != -1) {
			if (match(0, TokenType.PLUS, TokenType.MINUS, TokenType.TILDE, TokenType.BANG, TokenType.LPAREN, TokenType.IDENTIFIER, TokenType.NODE_VARIABLE, TokenType.BOOLEAN, TokenType.INT, TokenType.SHORT, TokenType.BYTE, TokenType.CHAR, TokenType.DOUBLE, TokenType.FLOAT, TokenType.LONG, TokenType.VOID, TokenType.NEW, TokenType.SUPER, TokenType.THIS, TokenType.LT, TokenType.CHARACTER_LITERAL, TokenType.STRING_LITERAL, TokenType.FLOAT_LITERAL, TokenType.DOUBLE_LITERAL, TokenType.NULL, TokenType.TRUE, TokenType.FALSE, TokenType.INTEGER_LITERAL, TokenType.LONG_LITERAL, TokenType.INCR, TokenType.DECR) != -1) {
				expr = parseExpression();
				ret = append(ret, expr);
				while (match(0, TokenType.COMMA) != -1) {
					parse(TokenType.COMMA);
					expr = parseExpression();
					ret = append(ret, expr);
				}
			} else if (quotesMode) {
				ret = parseNodeListVar();
			} else {
				throw produceParseException(TokenType.NODE_LIST_VARIABLE, TokenType.LPAREN, TokenType.DECR, TokenType.INCR, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER, TokenType.DOUBLE, TokenType.BYTE, TokenType.CHAR, TokenType.BOOLEAN, TokenType.FLOAT, TokenType.LONG, TokenType.INT, TokenType.SHORT, TokenType.VOID, TokenType.NEW, TokenType.LT, TokenType.NULL, TokenType.FLOAT_LITERAL, TokenType.DOUBLE_LITERAL, TokenType.INTEGER_LITERAL, TokenType.LONG_LITERAL, TokenType.TRUE, TokenType.FALSE, TokenType.CHARACTER_LITERAL, TokenType.STRING_LITERAL, TokenType.SUPER, TokenType.THIS, TokenType.TILDE, TokenType.BANG, TokenType.MINUS, TokenType.PLUS);
			}
		}
		parse(TokenType.RPAREN);
		return ret;
	}

	/* sequence(
		terminal(LPAREN)
		zeroOrOne(
			choice(
				sequence(
					nonTerminal(expr, Expression)
					zeroOrMore(
						terminal(COMMA)
						nonTerminal(expr, Expression)
					)
				)
				sequence(
					lookAhead({ quotesMode })
					nonTerminal(ret, NodeListVar)
				)
			)
		)
		terminal(RPAREN)
	) */
	private int matchArguments(int lookahead) {
		lookahead = match(lookahead, TokenType.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchArguments_2(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.RPAREN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		choice(
			sequence(
				nonTerminal(expr, Expression)
				zeroOrMore(
					terminal(COMMA)
					nonTerminal(expr, Expression)
				)
			)
			sequence(
				lookAhead({ quotesMode })
				nonTerminal(ret, NodeListVar)
			)
		)
	) */
	private int matchArguments_2(int lookahead) {
		int newLookahead;
		newLookahead = matchArguments_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		choice(
			sequence(
				nonTerminal(expr, Expression)
				zeroOrMore(
					terminal(COMMA)
					nonTerminal(expr, Expression)
				)
			)
			sequence(
				lookAhead({ quotesMode })
				nonTerminal(ret, NodeListVar)
			)
		)
	) */
	private int matchArguments_2_1(int lookahead) {
		lookahead = matchArguments_2_1_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			nonTerminal(expr, Expression)
			zeroOrMore(
				terminal(COMMA)
				nonTerminal(expr, Expression)
			)
		)
		sequence(
			lookAhead({ quotesMode })
			nonTerminal(ret, NodeListVar)
		)
	) */
	private int matchArguments_2_1_1(int lookahead) {
		int newLookahead;
		newLookahead = matchArguments_2_1_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchArguments_2_1_1_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		nonTerminal(expr, Expression)
		zeroOrMore(
			terminal(COMMA)
			nonTerminal(expr, Expression)
		)
	) */
	private int matchArguments_2_1_1_1(int lookahead) {
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchArguments_2_1_1_1_3(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		terminal(COMMA)
		nonTerminal(expr, Expression)
	) */
	private int matchArguments_2_1_1_1_3(int lookahead) {
		int newLookahead;
		newLookahead = matchArguments_2_1_1_1_3_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchArguments_2_1_1_1_3_1(lookahead);
		}
		return lookahead;
	}

	/* sequence(
		terminal(COMMA)
		nonTerminal(expr, Expression)
	) */
	private int matchArguments_2_1_1_1_3_1(int lookahead) {
		lookahead = match(lookahead, TokenType.COMMA);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead({ quotesMode })
		nonTerminal(ret, NodeListVar)
	) */
	private int matchArguments_2_1_1_2(int lookahead) {
		lookahead = quotesMode ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = matchNodeListVar(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(DOUBLECOLON)
		zeroOrOne(
			nonTerminal(typeArgs, TypeArguments)
		)
		choice(
			nonTerminal(name, Name)
			sequence(
				terminal(NEW)
				action({ name = SName.make("new"); })
			)
		)
		action({ ret = dress(SMethodReferenceExpr.make(scope, ensureNotNull(typeArgs), name)); })
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parseMethodReferenceSuffix(BUTree<? extends SExpr> scope) throws ParseException {
		BUTree<SNodeList> typeArgs = null;
		BUTree<SName> name;
		BUTree<? extends SExpr> ret;
		parse(TokenType.DOUBLECOLON);
		if (match(0, TokenType.LT) != -1) {
			typeArgs = parseTypeArguments();
		}
		if (match(0, TokenType.IDENTIFIER, TokenType.NODE_VARIABLE) != -1) {
			name = parseName();
		} else if (match(0, TokenType.NEW) != -1) {
			parse(TokenType.NEW);
			name = SName.make("new");
		} else {
			throw produceParseException(TokenType.NEW, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER);
		}
		ret = dress(SMethodReferenceExpr.make(scope, ensureNotNull(typeArgs), name));
		return ret;
	}

	/* sequence(
		terminal(DOUBLECOLON)
		zeroOrOne(
			nonTerminal(typeArgs, TypeArguments)
		)
		choice(
			nonTerminal(name, Name)
			sequence(
				terminal(NEW)
			)
		)
	) */
	private int matchMethodReferenceSuffix(int lookahead) {
		lookahead = match(lookahead, TokenType.DOUBLECOLON);
		if (lookahead == -1)
			return -1;
		lookahead = matchMethodReferenceSuffix_2(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchMethodReferenceSuffix_3(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		nonTerminal(typeArgs, TypeArguments)
	) */
	private int matchMethodReferenceSuffix_2(int lookahead) {
		int newLookahead;
		newLookahead = matchMethodReferenceSuffix_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		nonTerminal(typeArgs, TypeArguments)
	) */
	private int matchMethodReferenceSuffix_2_1(int lookahead) {
		lookahead = matchTypeArguments(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		nonTerminal(name, Name)
		sequence(
			terminal(NEW)
		)
	) */
	private int matchMethodReferenceSuffix_3(int lookahead) {
		int newLookahead;
		newLookahead = matchName(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchMethodReferenceSuffix_3_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		terminal(NEW)
	) */
	private int matchMethodReferenceSuffix_3_2(int lookahead) {
		lookahead = match(lookahead, TokenType.NEW);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		action({
			if (scope == null) run();
		})
		terminal(NEW)
		zeroOrOne(
			nonTerminal(typeArgs, TypeArguments)
		)
		action({ run(); })
		nonTerminal(annotations, Annotations)
		choice(
			sequence(
				nonTerminal(type, PrimitiveType)
				nonTerminal(ret, ArrayCreationExpr)
			)
			sequence(
				nonTerminal(type, QualifiedType)
				choice(
					nonTerminal(ret, ArrayCreationExpr)
					sequence(
						nonTerminal(args, Arguments)
						zeroOrOne(
							lookAhead(
								terminal(LBRACE)
							)
							nonTerminal(anonymousBody, ClassOrInterfaceBody)
						)
						action({ ret = dress(SObjectCreationExpr.make(optionOf(scope), ensureNotNull(typeArgs), (BUTree<SQualifiedType>) type, args, optionOf(anonymousBody))); })
					)
				)
			)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parseAllocationExpression(BUTree<? extends SExpr> scope) throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SType> type;
		BUTree<SNodeList> typeArgs = null;
		BUTree<SNodeList> anonymousBody = null;
		BUTree<SNodeList> args;
		BUTree<SNodeList> annotations = null;
		if (scope == null) run();

		parse(TokenType.NEW);
		if (match(0, TokenType.LT) != -1) {
			typeArgs = parseTypeArguments();
		}
		run();
		annotations = parseAnnotations();
		if (match(0, TokenType.DOUBLE, TokenType.FLOAT, TokenType.LONG, TokenType.INT, TokenType.SHORT, TokenType.BYTE, TokenType.CHAR, TokenType.BOOLEAN) != -1) {
			type = parsePrimitiveType(annotations);
			ret = parseArrayCreationExpr(type);
		} else if (match(0, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER) != -1) {
			type = parseQualifiedType(annotations);
			if (match(0, TokenType.AT, TokenType.LBRACKET) != -1) {
				ret = parseArrayCreationExpr(type);
			} else if (match(0, TokenType.LPAREN) != -1) {
				args = parseArguments();
				if (matchAllocationExpression_lookahead1(0) != -1) {
					anonymousBody = parseClassOrInterfaceBody(TypeKind.Class);
				}
				ret = dress(SObjectCreationExpr.make(optionOf(scope), ensureNotNull(typeArgs), (BUTree<SQualifiedType>) type, args, optionOf(anonymousBody)));
			} else {
				throw produceParseException(TokenType.LPAREN, TokenType.AT, TokenType.LBRACKET);
			}
		} else {
			throw produceParseException(TokenType.IDENTIFIER, TokenType.NODE_VARIABLE, TokenType.SHORT, TokenType.BYTE, TokenType.LONG, TokenType.INT, TokenType.DOUBLE, TokenType.FLOAT, TokenType.CHAR, TokenType.BOOLEAN);
		}
		return ret;
	}

	/* sequence(
		terminal(NEW)
		zeroOrOne(
			nonTerminal(typeArgs, TypeArguments)
		)
		nonTerminal(annotations, Annotations)
		choice(
			sequence(
				nonTerminal(type, PrimitiveType)
				nonTerminal(ret, ArrayCreationExpr)
			)
			sequence(
				nonTerminal(type, QualifiedType)
				choice(
					nonTerminal(ret, ArrayCreationExpr)
					sequence(
						nonTerminal(args, Arguments)
						zeroOrOne(
							lookAhead(
								terminal(LBRACE)
							)
							nonTerminal(anonymousBody, ClassOrInterfaceBody)
						)
					)
				)
			)
		)
	) */
	private int matchAllocationExpression(int lookahead) {
		lookahead = match(lookahead, TokenType.NEW);
		if (lookahead == -1)
			return -1;
		lookahead = matchAllocationExpression_3(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchAnnotations(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchAllocationExpression_6(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		nonTerminal(typeArgs, TypeArguments)
	) */
	private int matchAllocationExpression_3(int lookahead) {
		int newLookahead;
		newLookahead = matchAllocationExpression_3_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		nonTerminal(typeArgs, TypeArguments)
	) */
	private int matchAllocationExpression_3_1(int lookahead) {
		lookahead = matchTypeArguments(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			nonTerminal(type, PrimitiveType)
			nonTerminal(ret, ArrayCreationExpr)
		)
		sequence(
			nonTerminal(type, QualifiedType)
			choice(
				nonTerminal(ret, ArrayCreationExpr)
				sequence(
					nonTerminal(args, Arguments)
					zeroOrOne(
						lookAhead(
							terminal(LBRACE)
						)
						nonTerminal(anonymousBody, ClassOrInterfaceBody)
					)
				)
			)
		)
	) */
	private int matchAllocationExpression_6(int lookahead) {
		int newLookahead;
		newLookahead = matchAllocationExpression_6_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchAllocationExpression_6_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		nonTerminal(type, PrimitiveType)
		nonTerminal(ret, ArrayCreationExpr)
	) */
	private int matchAllocationExpression_6_1(int lookahead) {
		lookahead = matchPrimitiveType(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchArrayCreationExpr(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(type, QualifiedType)
		choice(
			nonTerminal(ret, ArrayCreationExpr)
			sequence(
				nonTerminal(args, Arguments)
				zeroOrOne(
					lookAhead(
						terminal(LBRACE)
					)
					nonTerminal(anonymousBody, ClassOrInterfaceBody)
				)
			)
		)
	) */
	private int matchAllocationExpression_6_2(int lookahead) {
		lookahead = matchQualifiedType(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchAllocationExpression_6_2_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		nonTerminal(ret, ArrayCreationExpr)
		sequence(
			nonTerminal(args, Arguments)
			zeroOrOne(
				lookAhead(
					terminal(LBRACE)
				)
				nonTerminal(anonymousBody, ClassOrInterfaceBody)
			)
		)
	) */
	private int matchAllocationExpression_6_2_2(int lookahead) {
		int newLookahead;
		newLookahead = matchArrayCreationExpr(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchAllocationExpression_6_2_2_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		nonTerminal(args, Arguments)
		zeroOrOne(
			lookAhead(
				terminal(LBRACE)
			)
			nonTerminal(anonymousBody, ClassOrInterfaceBody)
		)
	) */
	private int matchAllocationExpression_6_2_2_2(int lookahead) {
		lookahead = matchArguments(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchAllocationExpression_6_2_2_2_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		lookAhead(
			terminal(LBRACE)
		)
		nonTerminal(anonymousBody, ClassOrInterfaceBody)
	) */
	private int matchAllocationExpression_6_2_2_2_2(int lookahead) {
		int newLookahead;
		newLookahead = matchAllocationExpression_6_2_2_2_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		lookAhead(
			terminal(LBRACE)
		)
		nonTerminal(anonymousBody, ClassOrInterfaceBody)
	) */
	private int matchAllocationExpression_6_2_2_2_2_1(int lookahead) {
		lookahead = matchClassOrInterfaceBody(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(LBRACE)
	) */
	private int matchAllocationExpression_lookahead1(int lookahead) {
		lookahead = match(lookahead, TokenType.LBRACE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			lookAhead(
				nonTerminal(Annotations)
				terminal(LBRACKET)
				nonTerminal(Expression)
				terminal(RBRACKET)
			)
			nonTerminal(arrayDimExprs, ArrayDimExprsMandatory)
			nonTerminal(arrayDims, ArrayDims)
			action({ return dress(SArrayCreationExpr.make(componentType, arrayDimExprs, arrayDims, none())); })
		)
		sequence(
			nonTerminal(arrayDims, ArrayDimsMandatory)
			nonTerminal(initializer, ArrayInitializer)
			action({ return dress(SArrayCreationExpr.make(componentType, arrayDimExprs, arrayDims, optionOf(initializer))); })
		)
	) */
	protected BUTree<? extends SExpr> parseArrayCreationExpr(BUTree<? extends SType> componentType) throws ParseException {
		BUTree<? extends SExpr> expr;
		BUTree<SNodeList> arrayDimExprs = emptyList();
		BUTree<SNodeList> arrayDims = emptyList();
		BUTree<SNodeList> annotations = null;
		BUTree<SArrayInitializerExpr> initializer;
		if (matchArrayCreationExpr_lookahead1(0) != -1) {
			arrayDimExprs = parseArrayDimExprsMandatory();
			arrayDims = parseArrayDims();
			return dress(SArrayCreationExpr.make(componentType, arrayDimExprs, arrayDims, none()));
		} else if (match(0, TokenType.AT, TokenType.LBRACKET) != -1) {
			arrayDims = parseArrayDimsMandatory();
			initializer = parseArrayInitializer();
			return dress(SArrayCreationExpr.make(componentType, arrayDimExprs, arrayDims, optionOf(initializer)));
		} else {
			throw produceParseException(TokenType.AT, TokenType.LBRACKET);
		}
	}

	/* choice(
		sequence(
			lookAhead(
				nonTerminal(Annotations)
				terminal(LBRACKET)
				nonTerminal(Expression)
				terminal(RBRACKET)
			)
			nonTerminal(arrayDimExprs, ArrayDimExprsMandatory)
			nonTerminal(arrayDims, ArrayDims)
		)
		sequence(
			nonTerminal(arrayDims, ArrayDimsMandatory)
			nonTerminal(initializer, ArrayInitializer)
		)
	) */
	private int matchArrayCreationExpr(int lookahead) {
		int newLookahead;
		newLookahead = matchArrayCreationExpr_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchArrayCreationExpr_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		lookAhead(
			nonTerminal(Annotations)
			terminal(LBRACKET)
			nonTerminal(Expression)
			terminal(RBRACKET)
		)
		nonTerminal(arrayDimExprs, ArrayDimExprsMandatory)
		nonTerminal(arrayDims, ArrayDims)
	) */
	private int matchArrayCreationExpr_1(int lookahead) {
		lookahead = matchArrayDimExprsMandatory(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchArrayDims(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(arrayDims, ArrayDimsMandatory)
		nonTerminal(initializer, ArrayInitializer)
	) */
	private int matchArrayCreationExpr_2(int lookahead) {
		lookahead = matchArrayDimsMandatory(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchArrayInitializer(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(Annotations)
		terminal(LBRACKET)
		nonTerminal(Expression)
		terminal(RBRACKET)
	) */
	private int matchArrayCreationExpr_lookahead1(int lookahead) {
		lookahead = matchAnnotations(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.LBRACKET);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.RBRACKET);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		oneOrMore(
			lookAhead(
				nonTerminal(Annotations)
				terminal(LBRACKET)
				nonTerminal(Expression)
				terminal(RBRACKET)
			)
			action({ run(); })
			nonTerminal(annotations, Annotations)
			terminal(LBRACKET)
			nonTerminal(expr, Expression)
			terminal(RBRACKET)
			action({ arrayDimExprs = append(arrayDimExprs, dress(SArrayDimExpr.make(annotations, expr))); })
		)
		action({ return arrayDimExprs; })
	) */
	protected BUTree<SNodeList> parseArrayDimExprsMandatory() throws ParseException {
		BUTree<SNodeList> arrayDimExprs = emptyList();
		BUTree<SNodeList> annotations;
		BUTree<? extends SExpr> expr;
		do {
			run();
			annotations = parseAnnotations();
			parse(TokenType.LBRACKET);
			expr = parseExpression();
			parse(TokenType.RBRACKET);
			arrayDimExprs = append(arrayDimExprs, dress(SArrayDimExpr.make(annotations, expr)));
		} while (matchArrayDimExprsMandatory_lookahead1(0) != -1);
		return arrayDimExprs;
	}

	/* sequence(
		oneOrMore(
			lookAhead(
				nonTerminal(Annotations)
				terminal(LBRACKET)
				nonTerminal(Expression)
				terminal(RBRACKET)
			)
			nonTerminal(annotations, Annotations)
			terminal(LBRACKET)
			nonTerminal(expr, Expression)
			terminal(RBRACKET)
		)
	) */
	private int matchArrayDimExprsMandatory(int lookahead) {
		lookahead = matchArrayDimExprsMandatory_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* oneOrMore(
		lookAhead(
			nonTerminal(Annotations)
			terminal(LBRACKET)
			nonTerminal(Expression)
			terminal(RBRACKET)
		)
		nonTerminal(annotations, Annotations)
		terminal(LBRACKET)
		nonTerminal(expr, Expression)
		terminal(RBRACKET)
	) */
	private int matchArrayDimExprsMandatory_1(int lookahead) {
		int newLookahead;
		newLookahead = matchArrayDimExprsMandatory_1_1(lookahead);
		if (newLookahead == -1)
			return -1;
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchArrayDimExprsMandatory_1_1(lookahead);
		}
		return lookahead;
	}

	/* sequence(
		lookAhead(
			nonTerminal(Annotations)
			terminal(LBRACKET)
			nonTerminal(Expression)
			terminal(RBRACKET)
		)
		nonTerminal(annotations, Annotations)
		terminal(LBRACKET)
		nonTerminal(expr, Expression)
		terminal(RBRACKET)
	) */
	private int matchArrayDimExprsMandatory_1_1(int lookahead) {
		lookahead = matchAnnotations(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.LBRACKET);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.RBRACKET);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(Annotations)
		terminal(LBRACKET)
		nonTerminal(Expression)
		terminal(RBRACKET)
	) */
	private int matchArrayDimExprsMandatory_lookahead1(int lookahead) {
		lookahead = matchAnnotations(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.LBRACKET);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.RBRACKET);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		oneOrMore(
			lookAhead(
				nonTerminal(Annotations)
				terminal(LBRACKET)
				terminal(RBRACKET)
			)
			action({ run(); })
			nonTerminal(annotations, Annotations)
			terminal(LBRACKET)
			terminal(RBRACKET)
			action({ arrayDims = append(arrayDims, dress(SArrayDim.make(annotations))); })
		)
		action({ return arrayDims; })
	) */
	protected BUTree<SNodeList> parseArrayDimsMandatory() throws ParseException {
		BUTree<SNodeList> arrayDims = emptyList();
		BUTree<SNodeList> annotations;
		do {
			run();
			annotations = parseAnnotations();
			parse(TokenType.LBRACKET);
			parse(TokenType.RBRACKET);
			arrayDims = append(arrayDims, dress(SArrayDim.make(annotations)));
		} while (matchArrayDimsMandatory_lookahead1(0) != -1);
		return arrayDims;
	}

	/* sequence(
		oneOrMore(
			lookAhead(
				nonTerminal(Annotations)
				terminal(LBRACKET)
				terminal(RBRACKET)
			)
			nonTerminal(annotations, Annotations)
			terminal(LBRACKET)
			terminal(RBRACKET)
		)
	) */
	private int matchArrayDimsMandatory(int lookahead) {
		lookahead = matchArrayDimsMandatory_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* oneOrMore(
		lookAhead(
			nonTerminal(Annotations)
			terminal(LBRACKET)
			terminal(RBRACKET)
		)
		nonTerminal(annotations, Annotations)
		terminal(LBRACKET)
		terminal(RBRACKET)
	) */
	private int matchArrayDimsMandatory_1(int lookahead) {
		int newLookahead;
		newLookahead = matchArrayDimsMandatory_1_1(lookahead);
		if (newLookahead == -1)
			return -1;
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchArrayDimsMandatory_1_1(lookahead);
		}
		return lookahead;
	}

	/* sequence(
		lookAhead(
			nonTerminal(Annotations)
			terminal(LBRACKET)
			terminal(RBRACKET)
		)
		nonTerminal(annotations, Annotations)
		terminal(LBRACKET)
		terminal(RBRACKET)
	) */
	private int matchArrayDimsMandatory_1_1(int lookahead) {
		lookahead = matchAnnotations(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.LBRACKET);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.RBRACKET);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(Annotations)
		terminal(LBRACKET)
		terminal(RBRACKET)
	) */
	private int matchArrayDimsMandatory_lookahead1(int lookahead) {
		lookahead = matchAnnotations(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.LBRACKET);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.RBRACKET);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		choice(
			sequence(
				lookAhead(2)
				nonTerminal(ret, LabeledStatement)
			)
			nonTerminal(ret, AssertStatement)
			nonTerminal(ret, Block)
			nonTerminal(ret, EmptyStatement)
			nonTerminal(ret, StatementExpression)
			nonTerminal(ret, SwitchStatement)
			nonTerminal(ret, IfStatement)
			nonTerminal(ret, WhileStatement)
			nonTerminal(ret, DoStatement)
			nonTerminal(ret, ForStatement)
			nonTerminal(ret, BreakStatement)
			nonTerminal(ret, ContinueStatement)
			nonTerminal(ret, ReturnStatement)
			nonTerminal(ret, ThrowStatement)
			nonTerminal(ret, SynchronizedStatement)
			nonTerminal(ret, TryStatement)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SStmt> parseStatement() throws ParseException {
		BUTree<? extends SStmt> ret;
		if (matchStatement_lookahead1(0) != -1) {
			ret = parseLabeledStatement();
		} else if (match(0, TokenType.ASSERT) != -1) {
			ret = parseAssertStatement();
		} else if (match(0, TokenType.LBRACE) != -1) {
			ret = parseBlock();
		} else if (match(0, TokenType.SEMICOLON) != -1) {
			ret = parseEmptyStatement();
		} else if (match(0, TokenType.CHARACTER_LITERAL, TokenType.STRING_LITERAL, TokenType.FLOAT_LITERAL, TokenType.DOUBLE_LITERAL, TokenType.NULL, TokenType.TRUE, TokenType.FALSE, TokenType.INTEGER_LITERAL, TokenType.LONG_LITERAL, TokenType.VOID, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER, TokenType.BYTE, TokenType.SHORT, TokenType.BOOLEAN, TokenType.CHAR, TokenType.FLOAT, TokenType.DOUBLE, TokenType.INT, TokenType.LONG, TokenType.LT, TokenType.LPAREN, TokenType.THIS, TokenType.SUPER, TokenType.NEW, TokenType.DECR, TokenType.INCR) != -1) {
			ret = parseStatementExpression();
		} else if (match(0, TokenType.SWITCH) != -1) {
			ret = parseSwitchStatement();
		} else if (match(0, TokenType.IF) != -1) {
			ret = parseIfStatement();
		} else if (match(0, TokenType.WHILE) != -1) {
			ret = parseWhileStatement();
		} else if (match(0, TokenType.DO) != -1) {
			ret = parseDoStatement();
		} else if (match(0, TokenType.FOR) != -1) {
			ret = parseForStatement();
		} else if (match(0, TokenType.BREAK) != -1) {
			ret = parseBreakStatement();
		} else if (match(0, TokenType.CONTINUE) != -1) {
			ret = parseContinueStatement();
		} else if (match(0, TokenType.RETURN) != -1) {
			ret = parseReturnStatement();
		} else if (match(0, TokenType.THROW) != -1) {
			ret = parseThrowStatement();
		} else if (match(0, TokenType.SYNCHRONIZED) != -1) {
			ret = parseSynchronizedStatement();
		} else if (match(0, TokenType.TRY) != -1) {
			ret = parseTryStatement();
		} else {
			throw produceParseException(TokenType.TRY, TokenType.SYNCHRONIZED, TokenType.THROW, TokenType.RETURN, TokenType.CONTINUE, TokenType.BREAK, TokenType.FOR, TokenType.DO, TokenType.WHILE, TokenType.IF, TokenType.SWITCH, TokenType.VOID, TokenType.IDENTIFIER, TokenType.NODE_VARIABLE, TokenType.SHORT, TokenType.BYTE, TokenType.LONG, TokenType.INT, TokenType.DOUBLE, TokenType.FLOAT, TokenType.CHAR, TokenType.BOOLEAN, TokenType.LT, TokenType.NEW, TokenType.LPAREN, TokenType.THIS, TokenType.SUPER, TokenType.NULL, TokenType.FALSE, TokenType.TRUE, TokenType.STRING_LITERAL, TokenType.CHARACTER_LITERAL, TokenType.DOUBLE_LITERAL, TokenType.FLOAT_LITERAL, TokenType.LONG_LITERAL, TokenType.INTEGER_LITERAL, TokenType.INCR, TokenType.DECR, TokenType.SEMICOLON, TokenType.LBRACE, TokenType.ASSERT);
		}
		return ret;
	}

	/* sequence(
		choice(
			sequence(
				lookAhead(2)
				nonTerminal(ret, LabeledStatement)
			)
			nonTerminal(ret, AssertStatement)
			nonTerminal(ret, Block)
			nonTerminal(ret, EmptyStatement)
			nonTerminal(ret, StatementExpression)
			nonTerminal(ret, SwitchStatement)
			nonTerminal(ret, IfStatement)
			nonTerminal(ret, WhileStatement)
			nonTerminal(ret, DoStatement)
			nonTerminal(ret, ForStatement)
			nonTerminal(ret, BreakStatement)
			nonTerminal(ret, ContinueStatement)
			nonTerminal(ret, ReturnStatement)
			nonTerminal(ret, ThrowStatement)
			nonTerminal(ret, SynchronizedStatement)
			nonTerminal(ret, TryStatement)
		)
	) */
	private int matchStatement(int lookahead) {
		lookahead = matchStatement_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			lookAhead(2)
			nonTerminal(ret, LabeledStatement)
		)
		nonTerminal(ret, AssertStatement)
		nonTerminal(ret, Block)
		nonTerminal(ret, EmptyStatement)
		nonTerminal(ret, StatementExpression)
		nonTerminal(ret, SwitchStatement)
		nonTerminal(ret, IfStatement)
		nonTerminal(ret, WhileStatement)
		nonTerminal(ret, DoStatement)
		nonTerminal(ret, ForStatement)
		nonTerminal(ret, BreakStatement)
		nonTerminal(ret, ContinueStatement)
		nonTerminal(ret, ReturnStatement)
		nonTerminal(ret, ThrowStatement)
		nonTerminal(ret, SynchronizedStatement)
		nonTerminal(ret, TryStatement)
	) */
	private int matchStatement_1(int lookahead) {
		int newLookahead;
		newLookahead = matchStatement_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchAssertStatement(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchBlock(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchEmptyStatement(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchStatementExpression(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchSwitchStatement(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchIfStatement(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchWhileStatement(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchDoStatement(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchForStatement(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchBreakStatement(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchContinueStatement(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchReturnStatement(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchThrowStatement(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchSynchronizedStatement(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchTryStatement(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		lookAhead(2)
		nonTerminal(ret, LabeledStatement)
	) */
	private int matchStatement_1_1(int lookahead) {
		lookahead = matchLabeledStatement(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead(2)
		nonTerminal(ret, LabeledStatement)
	) */
	private int matchStatement_lookahead1(int lookahead) {
		if (match(0, TokenType.NODE_VARIABLE) != -1) {
			if (match(1, TokenType.COLON) != -1) {
				return lookahead;
			}
		}
		if (match(0, TokenType.IDENTIFIER) != -1) {
			if (match(1, TokenType.COLON) != -1) {
				return lookahead;
			}
		}
		return -1;
	}

	/* sequence(
		action({ run(); })
		terminal(ASSERT)
		nonTerminal(check, Expression)
		zeroOrOne(
			terminal(COLON)
			nonTerminal(msg, Expression)
		)
		terminal(SEMICOLON)
		action({ return dress(SAssertStmt.make(check, optionOf(msg))); })
	) */
	protected BUTree<SAssertStmt> parseAssertStatement() throws ParseException {
		BUTree<? extends SExpr> check;
		BUTree<? extends SExpr> msg = null;
		run();
		parse(TokenType.ASSERT);
		check = parseExpression();
		if (match(0, TokenType.COLON) != -1) {
			parse(TokenType.COLON);
			msg = parseExpression();
		}
		parse(TokenType.SEMICOLON);
		return dress(SAssertStmt.make(check, optionOf(msg)));
	}

	/* sequence(
		terminal(ASSERT)
		nonTerminal(check, Expression)
		zeroOrOne(
			terminal(COLON)
			nonTerminal(msg, Expression)
		)
		terminal(SEMICOLON)
	) */
	private int matchAssertStatement(int lookahead) {
		lookahead = match(lookahead, TokenType.ASSERT);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchAssertStatement_4(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.SEMICOLON);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		terminal(COLON)
		nonTerminal(msg, Expression)
	) */
	private int matchAssertStatement_4(int lookahead) {
		int newLookahead;
		newLookahead = matchAssertStatement_4_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		terminal(COLON)
		nonTerminal(msg, Expression)
	) */
	private int matchAssertStatement_4_1(int lookahead) {
		lookahead = match(lookahead, TokenType.COLON);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		action({ run(); })
		nonTerminal(label, Name)
		terminal(COLON)
		nonTerminal(stmt, Statement)
		action({ return dress(SLabeledStmt.make(label, stmt)); })
	) */
	protected BUTree<SLabeledStmt> parseLabeledStatement() throws ParseException {
		BUTree<SName> label;
		BUTree<? extends SStmt> stmt;
		run();
		label = parseName();
		parse(TokenType.COLON);
		stmt = parseStatement();
		return dress(SLabeledStmt.make(label, stmt));
	}

	/* sequence(
		nonTerminal(label, Name)
		terminal(COLON)
		nonTerminal(stmt, Statement)
	) */
	private int matchLabeledStatement(int lookahead) {
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.COLON);
		if (lookahead == -1)
			return -1;
		lookahead = matchStatement(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		action({ run(); })
		terminal(LBRACE)
		nonTerminal(stmts, Statements)
		terminal(RBRACE)
		action({ return dress(SBlockStmt.make(ensureNotNull(stmts))); })
	) */
	protected BUTree<SBlockStmt> parseBlock() throws ParseException {
		BUTree<SNodeList> stmts;
		run();
		parse(TokenType.LBRACE);
		stmts = parseStatements(false);
		parse(TokenType.RBRACE);
		return dress(SBlockStmt.make(ensureNotNull(stmts)));
	}

	/* sequence(
		terminal(LBRACE)
		nonTerminal(stmts, Statements)
		terminal(RBRACE)
	) */
	private int matchBlock(int lookahead) {
		lookahead = match(lookahead, TokenType.LBRACE);
		if (lookahead == -1)
			return -1;
		lookahead = matchStatements(lookahead, false);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.RBRACE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		choice(
			sequence(
				lookAhead(
					nonTerminal(ModifiersNoDefault)
					choice(
						terminal(CLASS)
						terminal(INTERFACE)
					)
				)
				action({ run(); })
				action({ run(); })
				nonTerminal(modifiers, ModifiersNoDefault)
				nonTerminal(typeDecl, ClassOrInterfaceDecl)
				action({ ret = dress(STypeDeclarationStmt.make(typeDecl)); })
			)
			sequence(
				lookAhead(
					nonTerminal(ModifiersNoDefault)
					nonTerminal(Type)
					nonTerminal(VariableDeclaratorId)
					choice(
						terminal(ASSIGN)
						terminal(COMMA)
						terminal(SEMICOLON)
					)
				)
				action({ run(); })
				nonTerminal(expr, VariableDeclExpression)
				terminal(SEMICOLON)
				action({ ret = dress(SExpressionStmt.make(expr)); })
			)
			nonTerminal(ret, Statement)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SStmt> parseBlockStatement() throws ParseException {
		BUTree<? extends SStmt> ret;
		BUTree<? extends SExpr> expr;
		BUTree<? extends STypeDecl> typeDecl;
		BUTree<SNodeList> modifiers;
		if (matchBlockStatement_lookahead1(0) != -1) {
			run();
			run();
			modifiers = parseModifiersNoDefault();
			typeDecl = parseClassOrInterfaceDecl(modifiers);
			ret = dress(STypeDeclarationStmt.make(typeDecl));
		} else if (matchBlockStatement_lookahead2(0) != -1) {
			run();
			expr = parseVariableDeclExpression();
			parse(TokenType.SEMICOLON);
			ret = dress(SExpressionStmt.make(expr));
		} else if (match(0, TokenType.IDENTIFIER, TokenType.NODE_VARIABLE, TokenType.TRY, TokenType.SYNCHRONIZED, TokenType.THROW, TokenType.RETURN, TokenType.CONTINUE, TokenType.BREAK, TokenType.FOR, TokenType.DO, TokenType.WHILE, TokenType.IF, TokenType.SWITCH, TokenType.VOID, TokenType.FLOAT, TokenType.LONG, TokenType.DOUBLE, TokenType.BYTE, TokenType.CHAR, TokenType.INT, TokenType.SHORT, TokenType.BOOLEAN, TokenType.NEW, TokenType.SUPER, TokenType.THIS, TokenType.FLOAT_LITERAL, TokenType.DOUBLE_LITERAL, TokenType.CHARACTER_LITERAL, TokenType.STRING_LITERAL, TokenType.INTEGER_LITERAL, TokenType.LONG_LITERAL, TokenType.TRUE, TokenType.FALSE, TokenType.NULL, TokenType.LPAREN, TokenType.LT, TokenType.DECR, TokenType.INCR, TokenType.SEMICOLON, TokenType.LBRACE, TokenType.ASSERT) != -1) {
			ret = parseStatement();
		} else {
			throw produceParseException(TokenType.TRY, TokenType.SYNCHRONIZED, TokenType.THROW, TokenType.RETURN, TokenType.CONTINUE, TokenType.BREAK, TokenType.FOR, TokenType.DO, TokenType.WHILE, TokenType.IF, TokenType.SWITCH, TokenType.BYTE, TokenType.CHAR, TokenType.INT, TokenType.SHORT, TokenType.FLOAT, TokenType.LONG, TokenType.DOUBLE, TokenType.BOOLEAN, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER, TokenType.VOID, TokenType.NEW, TokenType.SUPER, TokenType.THIS, TokenType.LPAREN, TokenType.LT, TokenType.INTEGER_LITERAL, TokenType.LONG_LITERAL, TokenType.FLOAT_LITERAL, TokenType.DOUBLE_LITERAL, TokenType.CHARACTER_LITERAL, TokenType.STRING_LITERAL, TokenType.TRUE, TokenType.FALSE, TokenType.NULL, TokenType.INCR, TokenType.DECR, TokenType.SEMICOLON, TokenType.LBRACE, TokenType.ASSERT, TokenType.AT, TokenType.PRIVATE, TokenType.PROTECTED, TokenType.PUBLIC, TokenType.TRANSIENT, TokenType.FINAL, TokenType.STATIC, TokenType.ABSTRACT, TokenType.STRICTFP, TokenType.NATIVE, TokenType.VOLATILE, TokenType.CLASS, TokenType.INTERFACE);
		}
		return ret;
	}

	/* sequence(
		choice(
			sequence(
				lookAhead(
					nonTerminal(ModifiersNoDefault)
					choice(
						terminal(CLASS)
						terminal(INTERFACE)
					)
				)
				nonTerminal(modifiers, ModifiersNoDefault)
				nonTerminal(typeDecl, ClassOrInterfaceDecl)
			)
			sequence(
				lookAhead(
					nonTerminal(ModifiersNoDefault)
					nonTerminal(Type)
					nonTerminal(VariableDeclaratorId)
					choice(
						terminal(ASSIGN)
						terminal(COMMA)
						terminal(SEMICOLON)
					)
				)
				nonTerminal(expr, VariableDeclExpression)
				terminal(SEMICOLON)
			)
			nonTerminal(ret, Statement)
		)
	) */
	private int matchBlockStatement(int lookahead) {
		lookahead = matchBlockStatement_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			lookAhead(
				nonTerminal(ModifiersNoDefault)
				choice(
					terminal(CLASS)
					terminal(INTERFACE)
				)
			)
			nonTerminal(modifiers, ModifiersNoDefault)
			nonTerminal(typeDecl, ClassOrInterfaceDecl)
		)
		sequence(
			lookAhead(
				nonTerminal(ModifiersNoDefault)
				nonTerminal(Type)
				nonTerminal(VariableDeclaratorId)
				choice(
					terminal(ASSIGN)
					terminal(COMMA)
					terminal(SEMICOLON)
				)
			)
			nonTerminal(expr, VariableDeclExpression)
			terminal(SEMICOLON)
		)
		nonTerminal(ret, Statement)
	) */
	private int matchBlockStatement_1(int lookahead) {
		int newLookahead;
		newLookahead = matchBlockStatement_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchBlockStatement_1_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchStatement(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		lookAhead(
			nonTerminal(ModifiersNoDefault)
			choice(
				terminal(CLASS)
				terminal(INTERFACE)
			)
		)
		nonTerminal(modifiers, ModifiersNoDefault)
		nonTerminal(typeDecl, ClassOrInterfaceDecl)
	) */
	private int matchBlockStatement_1_1(int lookahead) {
		lookahead = matchModifiersNoDefault(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchClassOrInterfaceDecl(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead(
			nonTerminal(ModifiersNoDefault)
			nonTerminal(Type)
			nonTerminal(VariableDeclaratorId)
			choice(
				terminal(ASSIGN)
				terminal(COMMA)
				terminal(SEMICOLON)
			)
		)
		nonTerminal(expr, VariableDeclExpression)
		terminal(SEMICOLON)
	) */
	private int matchBlockStatement_1_2(int lookahead) {
		lookahead = matchVariableDeclExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.SEMICOLON);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(ModifiersNoDefault)
		choice(
			terminal(CLASS)
			terminal(INTERFACE)
		)
	) */
	private int matchBlockStatement_lookahead1(int lookahead) {
		lookahead = matchModifiersNoDefault(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchBlockStatement_lookahead1_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		terminal(CLASS)
		terminal(INTERFACE)
	) */
	private int matchBlockStatement_lookahead1_2(int lookahead) {
		int newLookahead;
		newLookahead = match(lookahead, TokenType.CLASS);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = match(lookahead, TokenType.INTERFACE);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		nonTerminal(ModifiersNoDefault)
		nonTerminal(Type)
		nonTerminal(VariableDeclaratorId)
		choice(
			terminal(ASSIGN)
			terminal(COMMA)
			terminal(SEMICOLON)
		)
	) */
	private int matchBlockStatement_lookahead2(int lookahead) {
		lookahead = matchModifiersNoDefault(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchType(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchVariableDeclaratorId(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchBlockStatement_lookahead2_4(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		terminal(ASSIGN)
		terminal(COMMA)
		terminal(SEMICOLON)
	) */
	private int matchBlockStatement_lookahead2_4(int lookahead) {
		int newLookahead;
		newLookahead = match(lookahead, TokenType.ASSIGN);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = match(lookahead, TokenType.COMMA);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = match(lookahead, TokenType.SEMICOLON);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		action({ run(); })
		action({ run(); })
		nonTerminal(modifiers, ModifiersNoDefault)
		nonTerminal(variableDecl, VariableDecl)
		action({ return dress(SVariableDeclarationExpr.make(variableDecl)); })
	) */
	protected BUTree<SVariableDeclarationExpr> parseVariableDeclExpression() throws ParseException {
		BUTree<SNodeList> modifiers;
		BUTree<SLocalVariableDecl> variableDecl;
		run();
		run();
		modifiers = parseModifiersNoDefault();
		variableDecl = parseVariableDecl(modifiers);
		return dress(SVariableDeclarationExpr.make(variableDecl));
	}

	/* sequence(
		nonTerminal(modifiers, ModifiersNoDefault)
		nonTerminal(variableDecl, VariableDecl)
	) */
	private int matchVariableDeclExpression(int lookahead) {
		lookahead = matchModifiersNoDefault(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchVariableDecl(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		action({ run(); })
		terminal(SEMICOLON)
		action({ return dress(SEmptyStmt.make()); })
	) */
	protected BUTree<SEmptyStmt> parseEmptyStatement() throws ParseException {
		run();
		parse(TokenType.SEMICOLON);
		return dress(SEmptyStmt.make());
	}

	/* sequence(
		terminal(SEMICOLON)
	) */
	private int matchEmptyStatement(int lookahead) {
		lookahead = match(lookahead, TokenType.SEMICOLON);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		action({ run(); })
		choice(
			nonTerminal(expr, PrefixExpression)
			sequence(
				nonTerminal(expr, PrimaryExpression)
				zeroOrOne(
					choice(
						sequence(
							action({ lateRun(); })
							terminal(INCR)
							action({ expr = dress(SUnaryExpr.make(UnaryOp.PostIncrement, expr)); })
						)
						sequence(
							action({ lateRun(); })
							terminal(DECR)
							action({ expr = dress(SUnaryExpr.make(UnaryOp.PostDecrement, expr)); })
						)
						sequence(
							action({ lateRun(); })
							nonTerminal(op, AssignmentOperator)
							nonTerminal(value, Expression)
							action({ expr = dress(SAssignExpr.make(expr, op, value)); })
						)
					)
				)
			)
		)
		terminal(SEMICOLON)
		action({ return dress(SExpressionStmt.make(expr)); })
	) */
	protected BUTree<SExpressionStmt> parseStatementExpression() throws ParseException {
		BUTree<? extends SExpr> expr;
		AssignOp op;
		BUTree<? extends SExpr> value;
		run();
		if (match(0, TokenType.DECR, TokenType.INCR) != -1) {
			expr = parsePrefixExpression();
		} else if (match(0, TokenType.SUPER, TokenType.THIS, TokenType.FLOAT, TokenType.LONG, TokenType.DOUBLE, TokenType.BYTE, TokenType.CHAR, TokenType.INT, TokenType.SHORT, TokenType.BOOLEAN, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER, TokenType.VOID, TokenType.NEW, TokenType.LT, TokenType.LPAREN, TokenType.LONG_LITERAL, TokenType.INTEGER_LITERAL, TokenType.DOUBLE_LITERAL, TokenType.FLOAT_LITERAL, TokenType.STRING_LITERAL, TokenType.CHARACTER_LITERAL, TokenType.FALSE, TokenType.TRUE, TokenType.NULL) != -1) {
			expr = parsePrimaryExpression();
			if (match(0, TokenType.INCR, TokenType.RUNSIGNEDSHIFTASSIGN, TokenType.ANDASSIGN, TokenType.XORASSIGN, TokenType.ORASSIGN, TokenType.PLUSASSIGN, TokenType.MINUSASSIGN, TokenType.LSHIFTASSIGN, TokenType.RSIGNEDSHIFTASSIGN, TokenType.ASSIGN, TokenType.STARASSIGN, TokenType.SLASHASSIGN, TokenType.REMASSIGN, TokenType.DECR) != -1) {
				if (match(0, TokenType.INCR) != -1) {
					lateRun();
					parse(TokenType.INCR);
					expr = dress(SUnaryExpr.make(UnaryOp.PostIncrement, expr));
				} else if (match(0, TokenType.DECR) != -1) {
					lateRun();
					parse(TokenType.DECR);
					expr = dress(SUnaryExpr.make(UnaryOp.PostDecrement, expr));
				} else if (match(0, TokenType.ORASSIGN, TokenType.XORASSIGN, TokenType.ANDASSIGN, TokenType.RUNSIGNEDSHIFTASSIGN, TokenType.RSIGNEDSHIFTASSIGN, TokenType.LSHIFTASSIGN, TokenType.MINUSASSIGN, TokenType.PLUSASSIGN, TokenType.REMASSIGN, TokenType.SLASHASSIGN, TokenType.STARASSIGN, TokenType.ASSIGN) != -1) {
					lateRun();
					op = parseAssignmentOperator();
					value = parseExpression();
					expr = dress(SAssignExpr.make(expr, op, value));
				} else {
					throw produceParseException(TokenType.PLUSASSIGN, TokenType.MINUSASSIGN, TokenType.SLASHASSIGN, TokenType.REMASSIGN, TokenType.RUNSIGNEDSHIFTASSIGN, TokenType.ANDASSIGN, TokenType.LSHIFTASSIGN, TokenType.RSIGNEDSHIFTASSIGN, TokenType.XORASSIGN, TokenType.ORASSIGN, TokenType.ASSIGN, TokenType.STARASSIGN, TokenType.DECR, TokenType.INCR);
				}
			}
		} else {
			throw produceParseException(TokenType.NODE_VARIABLE, TokenType.IDENTIFIER, TokenType.LT, TokenType.LPAREN, TokenType.THIS, TokenType.CHARACTER_LITERAL, TokenType.DOUBLE_LITERAL, TokenType.TRUE, TokenType.STRING_LITERAL, TokenType.INTEGER_LITERAL, TokenType.FLOAT_LITERAL, TokenType.LONG_LITERAL, TokenType.NULL, TokenType.FALSE, TokenType.NEW, TokenType.SUPER, TokenType.BOOLEAN, TokenType.SHORT, TokenType.INT, TokenType.CHAR, TokenType.BYTE, TokenType.DOUBLE, TokenType.LONG, TokenType.FLOAT, TokenType.VOID, TokenType.DECR, TokenType.INCR);
		}
		parse(TokenType.SEMICOLON);
		return dress(SExpressionStmt.make(expr));
	}

	/* sequence(
		choice(
			nonTerminal(expr, PrefixExpression)
			sequence(
				nonTerminal(expr, PrimaryExpression)
				zeroOrOne(
					choice(
						sequence(
							terminal(INCR)
						)
						sequence(
							terminal(DECR)
						)
						sequence(
							nonTerminal(op, AssignmentOperator)
							nonTerminal(value, Expression)
						)
					)
				)
			)
		)
		terminal(SEMICOLON)
	) */
	private int matchStatementExpression(int lookahead) {
		lookahead = matchStatementExpression_2(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.SEMICOLON);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		nonTerminal(expr, PrefixExpression)
		sequence(
			nonTerminal(expr, PrimaryExpression)
			zeroOrOne(
				choice(
					sequence(
						terminal(INCR)
					)
					sequence(
						terminal(DECR)
					)
					sequence(
						nonTerminal(op, AssignmentOperator)
						nonTerminal(value, Expression)
					)
				)
			)
		)
	) */
	private int matchStatementExpression_2(int lookahead) {
		int newLookahead;
		newLookahead = matchPrefixExpression(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchStatementExpression_2_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		nonTerminal(expr, PrimaryExpression)
		zeroOrOne(
			choice(
				sequence(
					terminal(INCR)
				)
				sequence(
					terminal(DECR)
				)
				sequence(
					nonTerminal(op, AssignmentOperator)
					nonTerminal(value, Expression)
				)
			)
		)
	) */
	private int matchStatementExpression_2_2(int lookahead) {
		lookahead = matchPrimaryExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchStatementExpression_2_2_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		choice(
			sequence(
				terminal(INCR)
			)
			sequence(
				terminal(DECR)
			)
			sequence(
				nonTerminal(op, AssignmentOperator)
				nonTerminal(value, Expression)
			)
		)
	) */
	private int matchStatementExpression_2_2_2(int lookahead) {
		int newLookahead;
		newLookahead = matchStatementExpression_2_2_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		choice(
			sequence(
				terminal(INCR)
			)
			sequence(
				terminal(DECR)
			)
			sequence(
				nonTerminal(op, AssignmentOperator)
				nonTerminal(value, Expression)
			)
		)
	) */
	private int matchStatementExpression_2_2_2_1(int lookahead) {
		lookahead = matchStatementExpression_2_2_2_1_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			terminal(INCR)
		)
		sequence(
			terminal(DECR)
		)
		sequence(
			nonTerminal(op, AssignmentOperator)
			nonTerminal(value, Expression)
		)
	) */
	private int matchStatementExpression_2_2_2_1_1(int lookahead) {
		int newLookahead;
		newLookahead = matchStatementExpression_2_2_2_1_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchStatementExpression_2_2_2_1_1_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchStatementExpression_2_2_2_1_1_3(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		terminal(INCR)
	) */
	private int matchStatementExpression_2_2_2_1_1_1(int lookahead) {
		lookahead = match(lookahead, TokenType.INCR);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(DECR)
	) */
	private int matchStatementExpression_2_2_2_1_1_2(int lookahead) {
		lookahead = match(lookahead, TokenType.DECR);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(op, AssignmentOperator)
		nonTerminal(value, Expression)
	) */
	private int matchStatementExpression_2_2_2_1_1_3(int lookahead) {
		lookahead = matchAssignmentOperator(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		action({ run(); })
		terminal(SWITCH)
		terminal(LPAREN)
		nonTerminal(selector, Expression)
		terminal(RPAREN)
		terminal(LBRACE)
		zeroOrMore(
			nonTerminal(entry, SwitchEntry)
			action({ entries = append(entries, entry); })
		)
		terminal(RBRACE)
		action({ return dress(SSwitchStmt.make(selector, entries)); })
	) */
	protected BUTree<SSwitchStmt> parseSwitchStatement() throws ParseException {
		BUTree<? extends SExpr> selector;
		BUTree<SSwitchCase> entry;
		BUTree<SNodeList> entries = emptyList();
		run();
		parse(TokenType.SWITCH);
		parse(TokenType.LPAREN);
		selector = parseExpression();
		parse(TokenType.RPAREN);
		parse(TokenType.LBRACE);
		while (match(0, TokenType.DEFAULT, TokenType.CASE) != -1) {
			entry = parseSwitchEntry();
			entries = append(entries, entry);
		}
		parse(TokenType.RBRACE);
		return dress(SSwitchStmt.make(selector, entries));
	}

	/* sequence(
		terminal(SWITCH)
		terminal(LPAREN)
		nonTerminal(selector, Expression)
		terminal(RPAREN)
		terminal(LBRACE)
		zeroOrMore(
			nonTerminal(entry, SwitchEntry)
		)
		terminal(RBRACE)
	) */
	private int matchSwitchStatement(int lookahead) {
		lookahead = match(lookahead, TokenType.SWITCH);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.LBRACE);
		if (lookahead == -1)
			return -1;
		lookahead = matchSwitchStatement_7(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.RBRACE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		nonTerminal(entry, SwitchEntry)
	) */
	private int matchSwitchStatement_7(int lookahead) {
		int newLookahead;
		newLookahead = matchSwitchStatement_7_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchSwitchStatement_7_1(lookahead);
		}
		return lookahead;
	}

	/* sequence(
		nonTerminal(entry, SwitchEntry)
	) */
	private int matchSwitchStatement_7_1(int lookahead) {
		lookahead = matchSwitchEntry(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		action({ run(); })
		choice(
			sequence(
				terminal(CASE)
				nonTerminal(label, Expression)
			)
			terminal(DEFAULT)
		)
		terminal(COLON)
		nonTerminal(stmts, Statements)
		action({ return dress(SSwitchCase.make(optionOf(label), ensureNotNull(stmts))); })
	) */
	protected BUTree<SSwitchCase> parseSwitchEntry() throws ParseException {
		BUTree<? extends SExpr> label = null;
		BUTree<SNodeList> stmts;
		run();
		if (match(0, TokenType.CASE) != -1) {
			parse(TokenType.CASE);
			label = parseExpression();
		} else if (match(0, TokenType.DEFAULT) != -1) {
			parse(TokenType.DEFAULT);
		} else {
			throw produceParseException(TokenType.DEFAULT, TokenType.CASE);
		}
		parse(TokenType.COLON);
		stmts = parseStatements(false);
		return dress(SSwitchCase.make(optionOf(label), ensureNotNull(stmts)));
	}

	/* sequence(
		choice(
			sequence(
				terminal(CASE)
				nonTerminal(label, Expression)
			)
			terminal(DEFAULT)
		)
		terminal(COLON)
		nonTerminal(stmts, Statements)
	) */
	private int matchSwitchEntry(int lookahead) {
		lookahead = matchSwitchEntry_2(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.COLON);
		if (lookahead == -1)
			return -1;
		lookahead = matchStatements(lookahead, false);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			terminal(CASE)
			nonTerminal(label, Expression)
		)
		terminal(DEFAULT)
	) */
	private int matchSwitchEntry_2(int lookahead) {
		int newLookahead;
		newLookahead = matchSwitchEntry_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = match(lookahead, TokenType.DEFAULT);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		terminal(CASE)
		nonTerminal(label, Expression)
	) */
	private int matchSwitchEntry_2_1(int lookahead) {
		lookahead = match(lookahead, TokenType.CASE);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		action({ run(); })
		terminal(IF)
		terminal(LPAREN)
		nonTerminal(condition, Expression)
		terminal(RPAREN)
		nonTerminal(thenStmt, Statement)
		zeroOrOne(
			lookAhead(1)
			terminal(ELSE)
			nonTerminal(elseStmt, Statement)
		)
		action({ return dress(SIfStmt.make(condition, thenStmt, optionOf(elseStmt))); })
	) */
	protected BUTree<SIfStmt> parseIfStatement() throws ParseException {
		BUTree<? extends SExpr> condition;
		BUTree<? extends SStmt> thenStmt;
		BUTree<? extends SStmt> elseStmt = null;
		run();
		parse(TokenType.IF);
		parse(TokenType.LPAREN);
		condition = parseExpression();
		parse(TokenType.RPAREN);
		thenStmt = parseStatement();
		if (matchIfStatement_lookahead1(0) != -1) {
			parse(TokenType.ELSE);
			elseStmt = parseStatement();
		}
		return dress(SIfStmt.make(condition, thenStmt, optionOf(elseStmt)));
	}

	/* sequence(
		terminal(IF)
		terminal(LPAREN)
		nonTerminal(condition, Expression)
		terminal(RPAREN)
		nonTerminal(thenStmt, Statement)
		zeroOrOne(
			lookAhead(1)
			terminal(ELSE)
			nonTerminal(elseStmt, Statement)
		)
	) */
	private int matchIfStatement(int lookahead) {
		lookahead = match(lookahead, TokenType.IF);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchStatement(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchIfStatement_7(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		lookAhead(1)
		terminal(ELSE)
		nonTerminal(elseStmt, Statement)
	) */
	private int matchIfStatement_7(int lookahead) {
		int newLookahead;
		newLookahead = matchIfStatement_7_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		lookAhead(1)
		terminal(ELSE)
		nonTerminal(elseStmt, Statement)
	) */
	private int matchIfStatement_7_1(int lookahead) {
		lookahead = match(lookahead, TokenType.ELSE);
		if (lookahead == -1)
			return -1;
		lookahead = matchStatement(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		lookAhead(1)
		terminal(ELSE)
		nonTerminal(elseStmt, Statement)
	) */
	private int matchIfStatement_lookahead1(int lookahead) {
		if (match(0, TokenType.ELSE) != -1) {
			return lookahead;
		}
		return -1;
	}

	/* sequence(
		action({ run(); })
		terminal(WHILE)
		terminal(LPAREN)
		nonTerminal(condition, Expression)
		terminal(RPAREN)
		nonTerminal(body, Statement)
		action({ return dress(SWhileStmt.make(condition, body)); })
	) */
	protected BUTree<SWhileStmt> parseWhileStatement() throws ParseException {
		BUTree<? extends SExpr> condition;
		BUTree<? extends SStmt> body;
		run();
		parse(TokenType.WHILE);
		parse(TokenType.LPAREN);
		condition = parseExpression();
		parse(TokenType.RPAREN);
		body = parseStatement();
		return dress(SWhileStmt.make(condition, body));
	}

	/* sequence(
		terminal(WHILE)
		terminal(LPAREN)
		nonTerminal(condition, Expression)
		terminal(RPAREN)
		nonTerminal(body, Statement)
	) */
	private int matchWhileStatement(int lookahead) {
		lookahead = match(lookahead, TokenType.WHILE);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchStatement(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		action({ run(); })
		terminal(DO)
		nonTerminal(body, Statement)
		terminal(WHILE)
		terminal(LPAREN)
		nonTerminal(condition, Expression)
		terminal(RPAREN)
		terminal(SEMICOLON)
		action({ return dress(SDoStmt.make(body, condition)); })
	) */
	protected BUTree<SDoStmt> parseDoStatement() throws ParseException {
		BUTree<? extends SExpr> condition;
		BUTree<? extends SStmt> body;
		run();
		parse(TokenType.DO);
		body = parseStatement();
		parse(TokenType.WHILE);
		parse(TokenType.LPAREN);
		condition = parseExpression();
		parse(TokenType.RPAREN);
		parse(TokenType.SEMICOLON);
		return dress(SDoStmt.make(body, condition));
	}

	/* sequence(
		terminal(DO)
		nonTerminal(body, Statement)
		terminal(WHILE)
		terminal(LPAREN)
		nonTerminal(condition, Expression)
		terminal(RPAREN)
		terminal(SEMICOLON)
	) */
	private int matchDoStatement(int lookahead) {
		lookahead = match(lookahead, TokenType.DO);
		if (lookahead == -1)
			return -1;
		lookahead = matchStatement(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.WHILE);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.SEMICOLON);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		action({ run(); })
		terminal(FOR)
		terminal(LPAREN)
		choice(
			sequence(
				lookAhead(
					nonTerminal(Modifiers)
					nonTerminal(Type)
					nonTerminal(VariableDeclaratorId)
					terminal(COLON)
				)
				nonTerminal(varExpr, VariableDeclExpression)
				terminal(COLON)
				nonTerminal(expr, Expression)
			)
			sequence(
				zeroOrOne(
					nonTerminal(init, ForInit)
				)
				terminal(SEMICOLON)
				zeroOrOne(
					nonTerminal(expr, Expression)
				)
				terminal(SEMICOLON)
				zeroOrOne(
					nonTerminal(update, ForUpdate)
				)
			)
		)
		terminal(RPAREN)
		nonTerminal(body, Statement)
		action({
			if (varExpr != null)
				return dress(SForeachStmt.make(varExpr, expr, body));
			else
				return dress(SForStmt.make(init, expr, update, body));
		})
	) */
	protected BUTree<? extends SStmt> parseForStatement() throws ParseException {
		BUTree<SVariableDeclarationExpr> varExpr = null;
		BUTree<? extends SExpr> expr = null;
		BUTree<SNodeList> init = null;
		BUTree<SNodeList> update = null;
		BUTree<? extends SStmt> body;
		run();
		parse(TokenType.FOR);
		parse(TokenType.LPAREN);
		if (matchForStatement_lookahead1(0) != -1) {
			varExpr = parseVariableDeclExpression();
			parse(TokenType.COLON);
			expr = parseExpression();
		} else if (match(0, TokenType.DECR, TokenType.INCR, TokenType.MINUS, TokenType.PLUS, TokenType.BANG, TokenType.TILDE, TokenType.LPAREN, TokenType.INTEGER_LITERAL, TokenType.TRUE, TokenType.STRING_LITERAL, TokenType.NULL, TokenType.FALSE, TokenType.FLOAT_LITERAL, TokenType.LONG_LITERAL, TokenType.CHARACTER_LITERAL, TokenType.DOUBLE_LITERAL, TokenType.THIS, TokenType.SUPER, TokenType.NEW, TokenType.VOID, TokenType.LONG, TokenType.INT, TokenType.DOUBLE, TokenType.FLOAT, TokenType.CHAR, TokenType.BOOLEAN, TokenType.SHORT, TokenType.BYTE, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER, TokenType.LT, TokenType.TRANSIENT, TokenType.FINAL, TokenType.SYNCHRONIZED, TokenType.VOLATILE, TokenType.PRIVATE, TokenType.PROTECTED, TokenType.STATIC, TokenType.ABSTRACT, TokenType.PUBLIC, TokenType.STRICTFP, TokenType.NATIVE, TokenType.AT, TokenType.SEMICOLON) != -1) {
			if (match(0, TokenType.IDENTIFIER, TokenType.NODE_VARIABLE, TokenType.LPAREN, TokenType.LT, TokenType.DOUBLE_LITERAL, TokenType.CHARACTER_LITERAL, TokenType.STRING_LITERAL, TokenType.TRUE, TokenType.INTEGER_LITERAL, TokenType.LONG_LITERAL, TokenType.FLOAT_LITERAL, TokenType.FALSE, TokenType.NULL, TokenType.THIS, TokenType.SUPER, TokenType.NEW, TokenType.SHORT, TokenType.BYTE, TokenType.LONG, TokenType.INT, TokenType.CHAR, TokenType.BOOLEAN, TokenType.DOUBLE, TokenType.FLOAT, TokenType.VOID, TokenType.BANG, TokenType.TILDE, TokenType.DECR, TokenType.INCR, TokenType.MINUS, TokenType.PLUS, TokenType.AT, TokenType.PROTECTED, TokenType.PRIVATE, TokenType.PUBLIC, TokenType.FINAL, TokenType.TRANSIENT, TokenType.ABSTRACT, TokenType.STATIC, TokenType.NATIVE, TokenType.STRICTFP, TokenType.VOLATILE, TokenType.SYNCHRONIZED) != -1) {
				init = parseForInit();
			}
			parse(TokenType.SEMICOLON);
			if (match(0, TokenType.PLUS, TokenType.MINUS, TokenType.TILDE, TokenType.BANG, TokenType.LPAREN, TokenType.IDENTIFIER, TokenType.NODE_VARIABLE, TokenType.BOOLEAN, TokenType.INT, TokenType.SHORT, TokenType.BYTE, TokenType.CHAR, TokenType.DOUBLE, TokenType.FLOAT, TokenType.LONG, TokenType.VOID, TokenType.NEW, TokenType.SUPER, TokenType.THIS, TokenType.LT, TokenType.CHARACTER_LITERAL, TokenType.STRING_LITERAL, TokenType.FLOAT_LITERAL, TokenType.DOUBLE_LITERAL, TokenType.NULL, TokenType.TRUE, TokenType.FALSE, TokenType.INTEGER_LITERAL, TokenType.LONG_LITERAL, TokenType.INCR, TokenType.DECR) != -1) {
				expr = parseExpression();
			}
			parse(TokenType.SEMICOLON);
			if (match(0, TokenType.LPAREN, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER, TokenType.PLUS, TokenType.MINUS, TokenType.TILDE, TokenType.BANG, TokenType.LT, TokenType.CHAR, TokenType.BOOLEAN, TokenType.SHORT, TokenType.BYTE, TokenType.LONG, TokenType.INT, TokenType.DOUBLE, TokenType.FLOAT, TokenType.VOID, TokenType.SUPER, TokenType.THIS, TokenType.NEW, TokenType.CHARACTER_LITERAL, TokenType.STRING_LITERAL, TokenType.FLOAT_LITERAL, TokenType.DOUBLE_LITERAL, TokenType.NULL, TokenType.TRUE, TokenType.FALSE, TokenType.INTEGER_LITERAL, TokenType.LONG_LITERAL, TokenType.DECR, TokenType.INCR) != -1) {
				update = parseForUpdate();
			}
		} else {
			throw produceParseException(TokenType.IDENTIFIER, TokenType.NODE_VARIABLE, TokenType.LPAREN, TokenType.INCR, TokenType.DECR, TokenType.FALSE, TokenType.TRUE, TokenType.NULL, TokenType.LONG_LITERAL, TokenType.INTEGER_LITERAL, TokenType.DOUBLE_LITERAL, TokenType.FLOAT_LITERAL, TokenType.STRING_LITERAL, TokenType.CHARACTER_LITERAL, TokenType.VOID, TokenType.DOUBLE, TokenType.LONG, TokenType.FLOAT, TokenType.SHORT, TokenType.INT, TokenType.CHAR, TokenType.BYTE, TokenType.BOOLEAN, TokenType.NEW, TokenType.SUPER, TokenType.THIS, TokenType.LT, TokenType.TILDE, TokenType.BANG, TokenType.MINUS, TokenType.PLUS, TokenType.VOLATILE, TokenType.TRANSIENT, TokenType.FINAL, TokenType.STATIC, TokenType.AT, TokenType.STRICTFP, TokenType.NATIVE, TokenType.SYNCHRONIZED, TokenType.ABSTRACT, TokenType.PRIVATE, TokenType.PROTECTED, TokenType.PUBLIC, TokenType.SEMICOLON);
		}
		parse(TokenType.RPAREN);
		body = parseStatement();
		if (varExpr != null)
			return dress(SForeachStmt.make(varExpr, expr, body));
		else
			return dress(SForStmt.make(init, expr, update, body));

	}

	/* sequence(
		terminal(FOR)
		terminal(LPAREN)
		choice(
			sequence(
				lookAhead(
					nonTerminal(Modifiers)
					nonTerminal(Type)
					nonTerminal(VariableDeclaratorId)
					terminal(COLON)
				)
				nonTerminal(varExpr, VariableDeclExpression)
				terminal(COLON)
				nonTerminal(expr, Expression)
			)
			sequence(
				zeroOrOne(
					nonTerminal(init, ForInit)
				)
				terminal(SEMICOLON)
				zeroOrOne(
					nonTerminal(expr, Expression)
				)
				terminal(SEMICOLON)
				zeroOrOne(
					nonTerminal(update, ForUpdate)
				)
			)
		)
		terminal(RPAREN)
		nonTerminal(body, Statement)
	) */
	private int matchForStatement(int lookahead) {
		lookahead = match(lookahead, TokenType.FOR);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchForStatement_4(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchStatement(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			lookAhead(
				nonTerminal(Modifiers)
				nonTerminal(Type)
				nonTerminal(VariableDeclaratorId)
				terminal(COLON)
			)
			nonTerminal(varExpr, VariableDeclExpression)
			terminal(COLON)
			nonTerminal(expr, Expression)
		)
		sequence(
			zeroOrOne(
				nonTerminal(init, ForInit)
			)
			terminal(SEMICOLON)
			zeroOrOne(
				nonTerminal(expr, Expression)
			)
			terminal(SEMICOLON)
			zeroOrOne(
				nonTerminal(update, ForUpdate)
			)
		)
	) */
	private int matchForStatement_4(int lookahead) {
		int newLookahead;
		newLookahead = matchForStatement_4_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchForStatement_4_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		lookAhead(
			nonTerminal(Modifiers)
			nonTerminal(Type)
			nonTerminal(VariableDeclaratorId)
			terminal(COLON)
		)
		nonTerminal(varExpr, VariableDeclExpression)
		terminal(COLON)
		nonTerminal(expr, Expression)
	) */
	private int matchForStatement_4_1(int lookahead) {
		lookahead = matchVariableDeclExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.COLON);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		zeroOrOne(
			nonTerminal(init, ForInit)
		)
		terminal(SEMICOLON)
		zeroOrOne(
			nonTerminal(expr, Expression)
		)
		terminal(SEMICOLON)
		zeroOrOne(
			nonTerminal(update, ForUpdate)
		)
	) */
	private int matchForStatement_4_2(int lookahead) {
		lookahead = matchForStatement_4_2_1(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.SEMICOLON);
		if (lookahead == -1)
			return -1;
		lookahead = matchForStatement_4_2_3(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.SEMICOLON);
		if (lookahead == -1)
			return -1;
		lookahead = matchForStatement_4_2_5(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		nonTerminal(init, ForInit)
	) */
	private int matchForStatement_4_2_1(int lookahead) {
		int newLookahead;
		newLookahead = matchForStatement_4_2_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		nonTerminal(init, ForInit)
	) */
	private int matchForStatement_4_2_1_1(int lookahead) {
		lookahead = matchForInit(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		nonTerminal(expr, Expression)
	) */
	private int matchForStatement_4_2_3(int lookahead) {
		int newLookahead;
		newLookahead = matchForStatement_4_2_3_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		nonTerminal(expr, Expression)
	) */
	private int matchForStatement_4_2_3_1(int lookahead) {
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		nonTerminal(update, ForUpdate)
	) */
	private int matchForStatement_4_2_5(int lookahead) {
		int newLookahead;
		newLookahead = matchForStatement_4_2_5_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		nonTerminal(update, ForUpdate)
	) */
	private int matchForStatement_4_2_5_1(int lookahead) {
		lookahead = matchForUpdate(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(Modifiers)
		nonTerminal(Type)
		nonTerminal(VariableDeclaratorId)
		terminal(COLON)
	) */
	private int matchForStatement_lookahead1(int lookahead) {
		lookahead = matchModifiers(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchType(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchVariableDeclaratorId(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.COLON);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		choice(
			sequence(
				lookAhead(
					nonTerminal(Modifiers)
					nonTerminal(Type)
					nonTerminal(Name)
				)
				nonTerminal(expr, VariableDeclExpression)
				action({
					ret = emptyList();
					ret = append(ret, expr);
				})
			)
			nonTerminal(ret, ExpressionList)
		)
		action({ return ret; })
	) */
	protected BUTree<SNodeList> parseForInit() throws ParseException {
		BUTree<SNodeList> ret;
		BUTree<? extends SExpr> expr;
		if (matchForInit_lookahead1(0) != -1) {
			expr = parseVariableDeclExpression();
			ret = emptyList();
			ret = append(ret, expr);
		} else if (match(0, TokenType.PLUS, TokenType.MINUS, TokenType.TILDE, TokenType.BANG, TokenType.LPAREN, TokenType.IDENTIFIER, TokenType.NODE_VARIABLE, TokenType.BOOLEAN, TokenType.INT, TokenType.SHORT, TokenType.BYTE, TokenType.CHAR, TokenType.DOUBLE, TokenType.FLOAT, TokenType.LONG, TokenType.VOID, TokenType.NEW, TokenType.SUPER, TokenType.THIS, TokenType.LT, TokenType.CHARACTER_LITERAL, TokenType.STRING_LITERAL, TokenType.FLOAT_LITERAL, TokenType.DOUBLE_LITERAL, TokenType.NULL, TokenType.TRUE, TokenType.FALSE, TokenType.INTEGER_LITERAL, TokenType.LONG_LITERAL, TokenType.INCR, TokenType.DECR) != -1) {
			ret = parseExpressionList();
		} else {
			throw produceParseException(TokenType.DECR, TokenType.INCR, TokenType.MINUS, TokenType.PLUS, TokenType.LT, TokenType.IDENTIFIER, TokenType.NODE_VARIABLE, TokenType.LPAREN, TokenType.DOUBLE_LITERAL, TokenType.CHARACTER_LITERAL, TokenType.STRING_LITERAL, TokenType.TRUE, TokenType.INTEGER_LITERAL, TokenType.LONG_LITERAL, TokenType.FLOAT_LITERAL, TokenType.FALSE, TokenType.NULL, TokenType.THIS, TokenType.SUPER, TokenType.NEW, TokenType.VOID, TokenType.DOUBLE, TokenType.FLOAT, TokenType.SHORT, TokenType.BYTE, TokenType.LONG, TokenType.INT, TokenType.CHAR, TokenType.BOOLEAN, TokenType.BANG, TokenType.TILDE, TokenType.AT, TokenType.PUBLIC, TokenType.PROTECTED, TokenType.PRIVATE, TokenType.ABSTRACT, TokenType.STATIC, TokenType.FINAL, TokenType.TRANSIENT, TokenType.VOLATILE, TokenType.SYNCHRONIZED, TokenType.NATIVE, TokenType.STRICTFP);
		}
		return ret;
	}

	/* sequence(
		choice(
			sequence(
				lookAhead(
					nonTerminal(Modifiers)
					nonTerminal(Type)
					nonTerminal(Name)
				)
				nonTerminal(expr, VariableDeclExpression)
			)
			nonTerminal(ret, ExpressionList)
		)
	) */
	private int matchForInit(int lookahead) {
		lookahead = matchForInit_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			lookAhead(
				nonTerminal(Modifiers)
				nonTerminal(Type)
				nonTerminal(Name)
			)
			nonTerminal(expr, VariableDeclExpression)
		)
		nonTerminal(ret, ExpressionList)
	) */
	private int matchForInit_1(int lookahead) {
		int newLookahead;
		newLookahead = matchForInit_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchExpressionList(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		lookAhead(
			nonTerminal(Modifiers)
			nonTerminal(Type)
			nonTerminal(Name)
		)
		nonTerminal(expr, VariableDeclExpression)
	) */
	private int matchForInit_1_1(int lookahead) {
		lookahead = matchVariableDeclExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(Modifiers)
		nonTerminal(Type)
		nonTerminal(Name)
	) */
	private int matchForInit_lookahead1(int lookahead) {
		lookahead = matchModifiers(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchType(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(expr, Expression)
		action({ ret = append(ret, expr); })
		zeroOrMore(
			terminal(COMMA)
			nonTerminal(expr, Expression)
			action({ ret = append(ret, expr); })
		)
		action({ return ret; })
	) */
	protected BUTree<SNodeList> parseExpressionList() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<? extends SExpr> expr;
		expr = parseExpression();
		ret = append(ret, expr);
		while (match(0, TokenType.COMMA) != -1) {
			parse(TokenType.COMMA);
			expr = parseExpression();
			ret = append(ret, expr);
		}
		return ret;
	}

	/* sequence(
		nonTerminal(expr, Expression)
		zeroOrMore(
			terminal(COMMA)
			nonTerminal(expr, Expression)
		)
	) */
	private int matchExpressionList(int lookahead) {
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpressionList_3(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		terminal(COMMA)
		nonTerminal(expr, Expression)
	) */
	private int matchExpressionList_3(int lookahead) {
		int newLookahead;
		newLookahead = matchExpressionList_3_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchExpressionList_3_1(lookahead);
		}
		return lookahead;
	}

	/* sequence(
		terminal(COMMA)
		nonTerminal(expr, Expression)
	) */
	private int matchExpressionList_3_1(int lookahead) {
		lookahead = match(lookahead, TokenType.COMMA);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(ret, ExpressionList)
		action({ return ret; })
	) */
	protected BUTree<SNodeList> parseForUpdate() throws ParseException {
		BUTree<SNodeList> ret;
		ret = parseExpressionList();
		return ret;
	}

	/* sequence(
		nonTerminal(ret, ExpressionList)
	) */
	private int matchForUpdate(int lookahead) {
		lookahead = matchExpressionList(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		action({ run(); })
		terminal(BREAK)
		zeroOrOne(
			nonTerminal(id, Name)
		)
		terminal(SEMICOLON)
		action({ return dress(SBreakStmt.make(optionOf(id))); })
	) */
	protected BUTree<SBreakStmt> parseBreakStatement() throws ParseException {
		BUTree<SName> id = null;
		run();
		parse(TokenType.BREAK);
		if (match(0, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER) != -1) {
			id = parseName();
		}
		parse(TokenType.SEMICOLON);
		return dress(SBreakStmt.make(optionOf(id)));
	}

	/* sequence(
		terminal(BREAK)
		zeroOrOne(
			nonTerminal(id, Name)
		)
		terminal(SEMICOLON)
	) */
	private int matchBreakStatement(int lookahead) {
		lookahead = match(lookahead, TokenType.BREAK);
		if (lookahead == -1)
			return -1;
		lookahead = matchBreakStatement_3(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.SEMICOLON);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		nonTerminal(id, Name)
	) */
	private int matchBreakStatement_3(int lookahead) {
		int newLookahead;
		newLookahead = matchBreakStatement_3_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		nonTerminal(id, Name)
	) */
	private int matchBreakStatement_3_1(int lookahead) {
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		action({ run(); })
		terminal(CONTINUE)
		zeroOrOne(
			nonTerminal(id, Name)
		)
		terminal(SEMICOLON)
		action({ return dress(SContinueStmt.make(optionOf(id))); })
	) */
	protected BUTree<SContinueStmt> parseContinueStatement() throws ParseException {
		BUTree<SName> id = null;
		run();
		parse(TokenType.CONTINUE);
		if (match(0, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER) != -1) {
			id = parseName();
		}
		parse(TokenType.SEMICOLON);
		return dress(SContinueStmt.make(optionOf(id)));
	}

	/* sequence(
		terminal(CONTINUE)
		zeroOrOne(
			nonTerminal(id, Name)
		)
		terminal(SEMICOLON)
	) */
	private int matchContinueStatement(int lookahead) {
		lookahead = match(lookahead, TokenType.CONTINUE);
		if (lookahead == -1)
			return -1;
		lookahead = matchContinueStatement_3(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.SEMICOLON);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		nonTerminal(id, Name)
	) */
	private int matchContinueStatement_3(int lookahead) {
		int newLookahead;
		newLookahead = matchContinueStatement_3_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		nonTerminal(id, Name)
	) */
	private int matchContinueStatement_3_1(int lookahead) {
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		action({ run(); })
		terminal(RETURN)
		zeroOrOne(
			nonTerminal(expr, Expression)
		)
		terminal(SEMICOLON)
		action({ return dress(SReturnStmt.make(optionOf(expr))); })
	) */
	protected BUTree<SReturnStmt> parseReturnStatement() throws ParseException {
		BUTree<? extends SExpr> expr = null;
		run();
		parse(TokenType.RETURN);
		if (match(0, TokenType.PLUS, TokenType.MINUS, TokenType.TILDE, TokenType.BANG, TokenType.LPAREN, TokenType.IDENTIFIER, TokenType.NODE_VARIABLE, TokenType.BOOLEAN, TokenType.INT, TokenType.SHORT, TokenType.BYTE, TokenType.CHAR, TokenType.DOUBLE, TokenType.FLOAT, TokenType.LONG, TokenType.VOID, TokenType.NEW, TokenType.SUPER, TokenType.THIS, TokenType.LT, TokenType.CHARACTER_LITERAL, TokenType.STRING_LITERAL, TokenType.FLOAT_LITERAL, TokenType.DOUBLE_LITERAL, TokenType.NULL, TokenType.TRUE, TokenType.FALSE, TokenType.INTEGER_LITERAL, TokenType.LONG_LITERAL, TokenType.INCR, TokenType.DECR) != -1) {
			expr = parseExpression();
		}
		parse(TokenType.SEMICOLON);
		return dress(SReturnStmt.make(optionOf(expr)));
	}

	/* sequence(
		terminal(RETURN)
		zeroOrOne(
			nonTerminal(expr, Expression)
		)
		terminal(SEMICOLON)
	) */
	private int matchReturnStatement(int lookahead) {
		lookahead = match(lookahead, TokenType.RETURN);
		if (lookahead == -1)
			return -1;
		lookahead = matchReturnStatement_3(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.SEMICOLON);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		nonTerminal(expr, Expression)
	) */
	private int matchReturnStatement_3(int lookahead) {
		int newLookahead;
		newLookahead = matchReturnStatement_3_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		nonTerminal(expr, Expression)
	) */
	private int matchReturnStatement_3_1(int lookahead) {
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		action({ run(); })
		terminal(THROW)
		nonTerminal(expr, Expression)
		terminal(SEMICOLON)
		action({ return dress(SThrowStmt.make(expr)); })
	) */
	protected BUTree<SThrowStmt> parseThrowStatement() throws ParseException {
		BUTree<? extends SExpr> expr;
		run();
		parse(TokenType.THROW);
		expr = parseExpression();
		parse(TokenType.SEMICOLON);
		return dress(SThrowStmt.make(expr));
	}

	/* sequence(
		terminal(THROW)
		nonTerminal(expr, Expression)
		terminal(SEMICOLON)
	) */
	private int matchThrowStatement(int lookahead) {
		lookahead = match(lookahead, TokenType.THROW);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.SEMICOLON);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		action({ run(); })
		terminal(SYNCHRONIZED)
		terminal(LPAREN)
		nonTerminal(expr, Expression)
		terminal(RPAREN)
		nonTerminal(block, Block)
		action({ return dress(SSynchronizedStmt.make(expr, block)); })
	) */
	protected BUTree<SSynchronizedStmt> parseSynchronizedStatement() throws ParseException {
		BUTree<? extends SExpr> expr;
		BUTree<SBlockStmt> block;
		run();
		parse(TokenType.SYNCHRONIZED);
		parse(TokenType.LPAREN);
		expr = parseExpression();
		parse(TokenType.RPAREN);
		block = parseBlock();
		return dress(SSynchronizedStmt.make(expr, block));
	}

	/* sequence(
		terminal(SYNCHRONIZED)
		terminal(LPAREN)
		nonTerminal(expr, Expression)
		terminal(RPAREN)
		nonTerminal(block, Block)
	) */
	private int matchSynchronizedStatement(int lookahead) {
		lookahead = match(lookahead, TokenType.SYNCHRONIZED);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchBlock(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		action({ run(); })
		terminal(TRY)
		choice(
			sequence(
				nonTerminal(resources, ResourceSpecification)
				nonTerminal(tryBlock, Block)
				zeroOrOne(
					nonTerminal(catchClauses, CatchClauses)
				)
				zeroOrOne(
					terminal(FINALLY)
					nonTerminal(finallyBlock, Block)
				)
			)
			sequence(
				nonTerminal(tryBlock, Block)
				choice(
					sequence(
						nonTerminal(catchClauses, CatchClauses)
						zeroOrOne(
							terminal(FINALLY)
							nonTerminal(finallyBlock, Block)
						)
					)
					sequence(
						terminal(FINALLY)
						nonTerminal(finallyBlock, Block)
					)
				)
			)
		)
		action({ return dress(STryStmt.make(ensureNotNull(resources), trailingSemiColon.value, tryBlock, ensureNotNull(catchClauses), optionOf(finallyBlock))); })
	) */
	protected BUTree<STryStmt> parseTryStatement() throws ParseException {
		BUTree<SNodeList> resources = null;
		ByRef<Boolean> trailingSemiColon = new ByRef<Boolean>(false);
		BUTree<SBlockStmt> tryBlock;
		BUTree<SBlockStmt> finallyBlock = null;
		BUTree<SNodeList> catchClauses = null;
		run();
		parse(TokenType.TRY);
		if (match(0, TokenType.LPAREN) != -1) {
			resources = parseResourceSpecification(trailingSemiColon);
			tryBlock = parseBlock();
			if (match(0, TokenType.CATCH) != -1) {
				catchClauses = parseCatchClauses();
			}
			if (match(0, TokenType.FINALLY) != -1) {
				parse(TokenType.FINALLY);
				finallyBlock = parseBlock();
			}
		} else if (match(0, TokenType.LBRACE) != -1) {
			tryBlock = parseBlock();
			if (match(0, TokenType.CATCH) != -1) {
				catchClauses = parseCatchClauses();
				if (match(0, TokenType.FINALLY) != -1) {
					parse(TokenType.FINALLY);
					finallyBlock = parseBlock();
				}
			} else if (match(0, TokenType.FINALLY) != -1) {
				parse(TokenType.FINALLY);
				finallyBlock = parseBlock();
			} else {
				throw produceParseException(TokenType.FINALLY, TokenType.CATCH);
			}
		} else {
			throw produceParseException(TokenType.LBRACE, TokenType.LPAREN);
		}
		return dress(STryStmt.make(ensureNotNull(resources), trailingSemiColon.value, tryBlock, ensureNotNull(catchClauses), optionOf(finallyBlock)));
	}

	/* sequence(
		terminal(TRY)
		choice(
			sequence(
				nonTerminal(resources, ResourceSpecification)
				nonTerminal(tryBlock, Block)
				zeroOrOne(
					nonTerminal(catchClauses, CatchClauses)
				)
				zeroOrOne(
					terminal(FINALLY)
					nonTerminal(finallyBlock, Block)
				)
			)
			sequence(
				nonTerminal(tryBlock, Block)
				choice(
					sequence(
						nonTerminal(catchClauses, CatchClauses)
						zeroOrOne(
							terminal(FINALLY)
							nonTerminal(finallyBlock, Block)
						)
					)
					sequence(
						terminal(FINALLY)
						nonTerminal(finallyBlock, Block)
					)
				)
			)
		)
	) */
	private int matchTryStatement(int lookahead) {
		lookahead = match(lookahead, TokenType.TRY);
		if (lookahead == -1)
			return -1;
		lookahead = matchTryStatement_3(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			nonTerminal(resources, ResourceSpecification)
			nonTerminal(tryBlock, Block)
			zeroOrOne(
				nonTerminal(catchClauses, CatchClauses)
			)
			zeroOrOne(
				terminal(FINALLY)
				nonTerminal(finallyBlock, Block)
			)
		)
		sequence(
			nonTerminal(tryBlock, Block)
			choice(
				sequence(
					nonTerminal(catchClauses, CatchClauses)
					zeroOrOne(
						terminal(FINALLY)
						nonTerminal(finallyBlock, Block)
					)
				)
				sequence(
					terminal(FINALLY)
					nonTerminal(finallyBlock, Block)
				)
			)
		)
	) */
	private int matchTryStatement_3(int lookahead) {
		int newLookahead;
		newLookahead = matchTryStatement_3_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchTryStatement_3_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		nonTerminal(resources, ResourceSpecification)
		nonTerminal(tryBlock, Block)
		zeroOrOne(
			nonTerminal(catchClauses, CatchClauses)
		)
		zeroOrOne(
			terminal(FINALLY)
			nonTerminal(finallyBlock, Block)
		)
	) */
	private int matchTryStatement_3_1(int lookahead) {
		lookahead = matchResourceSpecification(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchBlock(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchTryStatement_3_1_3(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchTryStatement_3_1_4(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		nonTerminal(catchClauses, CatchClauses)
	) */
	private int matchTryStatement_3_1_3(int lookahead) {
		int newLookahead;
		newLookahead = matchTryStatement_3_1_3_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		nonTerminal(catchClauses, CatchClauses)
	) */
	private int matchTryStatement_3_1_3_1(int lookahead) {
		lookahead = matchCatchClauses(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		terminal(FINALLY)
		nonTerminal(finallyBlock, Block)
	) */
	private int matchTryStatement_3_1_4(int lookahead) {
		int newLookahead;
		newLookahead = matchTryStatement_3_1_4_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		terminal(FINALLY)
		nonTerminal(finallyBlock, Block)
	) */
	private int matchTryStatement_3_1_4_1(int lookahead) {
		lookahead = match(lookahead, TokenType.FINALLY);
		if (lookahead == -1)
			return -1;
		lookahead = matchBlock(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(tryBlock, Block)
		choice(
			sequence(
				nonTerminal(catchClauses, CatchClauses)
				zeroOrOne(
					terminal(FINALLY)
					nonTerminal(finallyBlock, Block)
				)
			)
			sequence(
				terminal(FINALLY)
				nonTerminal(finallyBlock, Block)
			)
		)
	) */
	private int matchTryStatement_3_2(int lookahead) {
		lookahead = matchBlock(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchTryStatement_3_2_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			nonTerminal(catchClauses, CatchClauses)
			zeroOrOne(
				terminal(FINALLY)
				nonTerminal(finallyBlock, Block)
			)
		)
		sequence(
			terminal(FINALLY)
			nonTerminal(finallyBlock, Block)
		)
	) */
	private int matchTryStatement_3_2_2(int lookahead) {
		int newLookahead;
		newLookahead = matchTryStatement_3_2_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchTryStatement_3_2_2_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		nonTerminal(catchClauses, CatchClauses)
		zeroOrOne(
			terminal(FINALLY)
			nonTerminal(finallyBlock, Block)
		)
	) */
	private int matchTryStatement_3_2_2_1(int lookahead) {
		lookahead = matchCatchClauses(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchTryStatement_3_2_2_1_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		terminal(FINALLY)
		nonTerminal(finallyBlock, Block)
	) */
	private int matchTryStatement_3_2_2_1_2(int lookahead) {
		int newLookahead;
		newLookahead = matchTryStatement_3_2_2_1_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		terminal(FINALLY)
		nonTerminal(finallyBlock, Block)
	) */
	private int matchTryStatement_3_2_2_1_2_1(int lookahead) {
		lookahead = match(lookahead, TokenType.FINALLY);
		if (lookahead == -1)
			return -1;
		lookahead = matchBlock(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(FINALLY)
		nonTerminal(finallyBlock, Block)
	) */
	private int matchTryStatement_3_2_2_2(int lookahead) {
		lookahead = match(lookahead, TokenType.FINALLY);
		if (lookahead == -1)
			return -1;
		lookahead = matchBlock(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		oneOrMore(
			nonTerminal(catchClause, CatchClause)
			action({ catchClauses = append(catchClauses, catchClause); })
		)
		action({ return catchClauses; })
	) */
	protected BUTree<SNodeList> parseCatchClauses() throws ParseException {
		BUTree<SNodeList> catchClauses = emptyList();
		BUTree<SCatchClause> catchClause;
		do {
			catchClause = parseCatchClause();
			catchClauses = append(catchClauses, catchClause);
		} while (match(0, TokenType.CATCH) != -1);
		return catchClauses;
	}

	/* sequence(
		oneOrMore(
			nonTerminal(catchClause, CatchClause)
		)
	) */
	private int matchCatchClauses(int lookahead) {
		lookahead = matchCatchClauses_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* oneOrMore(
		nonTerminal(catchClause, CatchClause)
	) */
	private int matchCatchClauses_1(int lookahead) {
		int newLookahead;
		newLookahead = matchCatchClauses_1_1(lookahead);
		if (newLookahead == -1)
			return -1;
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchCatchClauses_1_1(lookahead);
		}
		return lookahead;
	}

	/* sequence(
		nonTerminal(catchClause, CatchClause)
	) */
	private int matchCatchClauses_1_1(int lookahead) {
		lookahead = matchCatchClause(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		action({ run(); })
		terminal(CATCH)
		terminal(LPAREN)
		nonTerminal(param, CatchFormalParameter)
		terminal(RPAREN)
		nonTerminal(catchBlock, Block)
		action({ return dress(SCatchClause.make(param, catchBlock)); })
	) */
	protected BUTree<SCatchClause> parseCatchClause() throws ParseException {
		BUTree<SFormalParameter> param;
		BUTree<SBlockStmt> catchBlock;
		run();
		parse(TokenType.CATCH);
		parse(TokenType.LPAREN);
		param = parseCatchFormalParameter();
		parse(TokenType.RPAREN);
		catchBlock = parseBlock();
		return dress(SCatchClause.make(param, catchBlock));
	}

	/* sequence(
		terminal(CATCH)
		terminal(LPAREN)
		nonTerminal(param, CatchFormalParameter)
		terminal(RPAREN)
		nonTerminal(catchBlock, Block)
	) */
	private int matchCatchClause(int lookahead) {
		lookahead = match(lookahead, TokenType.CATCH);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchCatchFormalParameter(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchBlock(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		action({ run(); })
		nonTerminal(modifiers, Modifiers)
		nonTerminal(exceptType, QualifiedType)
		action({ exceptTypes = append(exceptTypes, exceptType); })
		zeroOrOne(
			lookAhead(
				terminal(BIT_OR)
			)
			action({ lateRun(); })
			oneOrMore(
				terminal(BIT_OR)
				nonTerminal(exceptType, AnnotatedQualifiedType)
				action({ exceptTypes = append(exceptTypes, exceptType); })
			)
			action({ exceptType = dress(SUnionType.make(exceptTypes)); })
		)
		nonTerminal(exceptId, VariableDeclaratorId)
		action({ return dress(SFormalParameter.make(modifiers, exceptType, false, optionOf(exceptId), false, none())); })
	) */
	protected BUTree<SFormalParameter> parseCatchFormalParameter() throws ParseException {
		BUTree<SNodeList> modifiers;
		BUTree<? extends SType> exceptType;
		BUTree<SNodeList> exceptTypes = emptyList();
		BUTree<SVariableDeclaratorId> exceptId;
		run();
		modifiers = parseModifiers();
		exceptType = parseQualifiedType(null);
		exceptTypes = append(exceptTypes, exceptType);
		if (matchCatchFormalParameter_lookahead1(0) != -1) {
			lateRun();
			do {
				parse(TokenType.BIT_OR);
				exceptType = parseAnnotatedQualifiedType();
				exceptTypes = append(exceptTypes, exceptType);
			} while (match(0, TokenType.BIT_OR) != -1);
			exceptType = dress(SUnionType.make(exceptTypes));
		}
		exceptId = parseVariableDeclaratorId();
		return dress(SFormalParameter.make(modifiers, exceptType, false, optionOf(exceptId), false, none()));
	}

	/* sequence(
		nonTerminal(modifiers, Modifiers)
		nonTerminal(exceptType, QualifiedType)
		zeroOrOne(
			lookAhead(
				terminal(BIT_OR)
			)
			oneOrMore(
				terminal(BIT_OR)
				nonTerminal(exceptType, AnnotatedQualifiedType)
			)
		)
		nonTerminal(exceptId, VariableDeclaratorId)
	) */
	private int matchCatchFormalParameter(int lookahead) {
		lookahead = matchModifiers(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchQualifiedType(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchCatchFormalParameter_5(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchVariableDeclaratorId(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		lookAhead(
			terminal(BIT_OR)
		)
		oneOrMore(
			terminal(BIT_OR)
			nonTerminal(exceptType, AnnotatedQualifiedType)
		)
	) */
	private int matchCatchFormalParameter_5(int lookahead) {
		int newLookahead;
		newLookahead = matchCatchFormalParameter_5_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		lookAhead(
			terminal(BIT_OR)
		)
		oneOrMore(
			terminal(BIT_OR)
			nonTerminal(exceptType, AnnotatedQualifiedType)
		)
	) */
	private int matchCatchFormalParameter_5_1(int lookahead) {
		lookahead = matchCatchFormalParameter_5_1_3(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* oneOrMore(
		terminal(BIT_OR)
		nonTerminal(exceptType, AnnotatedQualifiedType)
	) */
	private int matchCatchFormalParameter_5_1_3(int lookahead) {
		int newLookahead;
		newLookahead = matchCatchFormalParameter_5_1_3_1(lookahead);
		if (newLookahead == -1)
			return -1;
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchCatchFormalParameter_5_1_3_1(lookahead);
		}
		return lookahead;
	}

	/* sequence(
		terminal(BIT_OR)
		nonTerminal(exceptType, AnnotatedQualifiedType)
	) */
	private int matchCatchFormalParameter_5_1_3_1(int lookahead) {
		lookahead = match(lookahead, TokenType.BIT_OR);
		if (lookahead == -1)
			return -1;
		lookahead = matchAnnotatedQualifiedType(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(BIT_OR)
	) */
	private int matchCatchFormalParameter_lookahead1(int lookahead) {
		lookahead = match(lookahead, TokenType.BIT_OR);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(LPAREN)
		nonTerminal(var, VariableDeclExpression)
		action({ vars = append(vars, var); })
		zeroOrMore(
			negativeLookAhead(
				zeroOrOne(
					terminal(SEMICOLON)
				)
				terminal(RPAREN)
			)
			lookAhead(2)
			terminal(SEMICOLON)
			nonTerminal(var, VariableDeclExpression)
			action({ vars = append(vars, var); })
		)
		zeroOrOne(
			terminal(SEMICOLON)
			action({ trailingSemiColon.value = true; })
		)
		terminal(RPAREN)
		action({ return vars; })
	) */
	protected BUTree<SNodeList> parseResourceSpecification(ByRef<Boolean> trailingSemiColon) throws ParseException {
		BUTree<SNodeList> vars = emptyList();
		BUTree<SVariableDeclarationExpr> var;
		parse(TokenType.LPAREN);
		var = parseVariableDeclExpression();
		vars = append(vars, var);
		while (matchResourceSpecification_lookahead1(0) == -1) {
			parse(TokenType.SEMICOLON);
			var = parseVariableDeclExpression();
			vars = append(vars, var);
		}
		if (match(0, TokenType.SEMICOLON) != -1) {
			parse(TokenType.SEMICOLON);
			trailingSemiColon.value = true;
		}
		parse(TokenType.RPAREN);
		return vars;
	}

	/* sequence(
		terminal(LPAREN)
		nonTerminal(var, VariableDeclExpression)
		zeroOrMore(
			negativeLookAhead(
				zeroOrOne(
					terminal(SEMICOLON)
				)
				terminal(RPAREN)
			)
			lookAhead(2)
			terminal(SEMICOLON)
			nonTerminal(var, VariableDeclExpression)
		)
		zeroOrOne(
			terminal(SEMICOLON)
		)
		terminal(RPAREN)
	) */
	private int matchResourceSpecification(int lookahead) {
		lookahead = match(lookahead, TokenType.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchVariableDeclExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchResourceSpecification_4(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchResourceSpecification_5(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.RPAREN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		negativeLookAhead(
			zeroOrOne(
				terminal(SEMICOLON)
			)
			terminal(RPAREN)
		)
		lookAhead(2)
		terminal(SEMICOLON)
		nonTerminal(var, VariableDeclExpression)
	) */
	private int matchResourceSpecification_4(int lookahead) {
		int newLookahead;
		newLookahead = matchResourceSpecification_4_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchResourceSpecification_4_1(lookahead);
		}
		return lookahead;
	}

	/* sequence(
		negativeLookAhead(
			zeroOrOne(
				terminal(SEMICOLON)
			)
			terminal(RPAREN)
		)
		lookAhead(2)
		terminal(SEMICOLON)
		nonTerminal(var, VariableDeclExpression)
	) */
	private int matchResourceSpecification_4_1(int lookahead) {
		lookahead = match(lookahead, TokenType.SEMICOLON);
		if (lookahead == -1)
			return -1;
		lookahead = matchVariableDeclExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		terminal(SEMICOLON)
	) */
	private int matchResourceSpecification_5(int lookahead) {
		int newLookahead;
		newLookahead = matchResourceSpecification_5_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		terminal(SEMICOLON)
	) */
	private int matchResourceSpecification_5_1(int lookahead) {
		lookahead = match(lookahead, TokenType.SEMICOLON);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		zeroOrOne(
			terminal(SEMICOLON)
		)
		terminal(RPAREN)
	) */
	private int matchResourceSpecification_lookahead1(int lookahead) {
		lookahead = matchResourceSpecification_lookahead1_1(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.RPAREN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		terminal(SEMICOLON)
	) */
	private int matchResourceSpecification_lookahead1_1(int lookahead) {
		int newLookahead;
		newLookahead = matchResourceSpecification_lookahead1_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		terminal(SEMICOLON)
	) */
	private int matchResourceSpecification_lookahead1_1_1(int lookahead) {
		lookahead = match(lookahead, TokenType.SEMICOLON);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		zeroOrMore(
			nonTerminal(annotation, Annotation)
			action({ annotations = append(annotations, annotation); })
		)
		action({ return annotations; })
	) */
	protected BUTree<SNodeList> parseAnnotations() throws ParseException {
		BUTree<SNodeList> annotations = emptyList();
		BUTree<? extends SAnnotationExpr> annotation;
		while (match(0, TokenType.AT) != -1) {
			annotation = parseAnnotation();
			annotations = append(annotations, annotation);
		}
		return annotations;
	}

	/* sequence(
		zeroOrMore(
			nonTerminal(annotation, Annotation)
		)
	) */
	private int matchAnnotations(int lookahead) {
		lookahead = matchAnnotations_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		nonTerminal(annotation, Annotation)
	) */
	private int matchAnnotations_1(int lookahead) {
		int newLookahead;
		newLookahead = matchAnnotations_1_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchAnnotations_1_1(lookahead);
		}
		return lookahead;
	}

	/* sequence(
		nonTerminal(annotation, Annotation)
	) */
	private int matchAnnotations_1_1(int lookahead) {
		lookahead = matchAnnotation(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		choice(
			sequence(
				lookAhead(
					terminal(AT)
					nonTerminal(QualifiedName)
					terminal(LPAREN)
					choice(
						sequence(
							nonTerminal(Name)
							terminal(ASSIGN)
						)
						terminal(RPAREN)
					)
				)
				nonTerminal(ret, NormalAnnotation)
			)
			sequence(
				lookAhead(
					terminal(AT)
					nonTerminal(QualifiedName)
					terminal(LPAREN)
				)
				nonTerminal(ret, SingleMemberAnnotation)
			)
			sequence(
				lookAhead(
					terminal(AT)
					nonTerminal(QualifiedName)
				)
				nonTerminal(ret, MarkerAnnotation)
			)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SAnnotationExpr> parseAnnotation() throws ParseException {
		BUTree<? extends SAnnotationExpr> ret;
		if (matchAnnotation_lookahead1(0) != -1) {
			ret = parseNormalAnnotation();
		} else if (matchAnnotation_lookahead2(0) != -1) {
			ret = parseSingleMemberAnnotation();
		} else if (matchAnnotation_lookahead3(0) != -1) {
			ret = parseMarkerAnnotation();
		} else {
			throw produceParseException(TokenType.AT);
		}
		return ret;
	}

	/* sequence(
		choice(
			sequence(
				lookAhead(
					terminal(AT)
					nonTerminal(QualifiedName)
					terminal(LPAREN)
					choice(
						sequence(
							nonTerminal(Name)
							terminal(ASSIGN)
						)
						terminal(RPAREN)
					)
				)
				nonTerminal(ret, NormalAnnotation)
			)
			sequence(
				lookAhead(
					terminal(AT)
					nonTerminal(QualifiedName)
					terminal(LPAREN)
				)
				nonTerminal(ret, SingleMemberAnnotation)
			)
			sequence(
				lookAhead(
					terminal(AT)
					nonTerminal(QualifiedName)
				)
				nonTerminal(ret, MarkerAnnotation)
			)
		)
	) */
	private int matchAnnotation(int lookahead) {
		lookahead = matchAnnotation_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			lookAhead(
				terminal(AT)
				nonTerminal(QualifiedName)
				terminal(LPAREN)
				choice(
					sequence(
						nonTerminal(Name)
						terminal(ASSIGN)
					)
					terminal(RPAREN)
				)
			)
			nonTerminal(ret, NormalAnnotation)
		)
		sequence(
			lookAhead(
				terminal(AT)
				nonTerminal(QualifiedName)
				terminal(LPAREN)
			)
			nonTerminal(ret, SingleMemberAnnotation)
		)
		sequence(
			lookAhead(
				terminal(AT)
				nonTerminal(QualifiedName)
			)
			nonTerminal(ret, MarkerAnnotation)
		)
	) */
	private int matchAnnotation_1(int lookahead) {
		int newLookahead;
		newLookahead = matchAnnotation_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchAnnotation_1_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchAnnotation_1_3(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		lookAhead(
			terminal(AT)
			nonTerminal(QualifiedName)
			terminal(LPAREN)
			choice(
				sequence(
					nonTerminal(Name)
					terminal(ASSIGN)
				)
				terminal(RPAREN)
			)
		)
		nonTerminal(ret, NormalAnnotation)
	) */
	private int matchAnnotation_1_1(int lookahead) {
		lookahead = matchNormalAnnotation(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead(
			terminal(AT)
			nonTerminal(QualifiedName)
			terminal(LPAREN)
		)
		nonTerminal(ret, SingleMemberAnnotation)
	) */
	private int matchAnnotation_1_2(int lookahead) {
		lookahead = matchSingleMemberAnnotation(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead(
			terminal(AT)
			nonTerminal(QualifiedName)
		)
		nonTerminal(ret, MarkerAnnotation)
	) */
	private int matchAnnotation_1_3(int lookahead) {
		lookahead = matchMarkerAnnotation(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(AT)
		nonTerminal(QualifiedName)
		terminal(LPAREN)
		choice(
			sequence(
				nonTerminal(Name)
				terminal(ASSIGN)
			)
			terminal(RPAREN)
		)
	) */
	private int matchAnnotation_lookahead1(int lookahead) {
		lookahead = match(lookahead, TokenType.AT);
		if (lookahead == -1)
			return -1;
		lookahead = matchQualifiedName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchAnnotation_lookahead1_4(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			nonTerminal(Name)
			terminal(ASSIGN)
		)
		terminal(RPAREN)
	) */
	private int matchAnnotation_lookahead1_4(int lookahead) {
		int newLookahead;
		newLookahead = matchAnnotation_lookahead1_4_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = match(lookahead, TokenType.RPAREN);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		nonTerminal(Name)
		terminal(ASSIGN)
	) */
	private int matchAnnotation_lookahead1_4_1(int lookahead) {
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.ASSIGN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(AT)
		nonTerminal(QualifiedName)
		terminal(LPAREN)
	) */
	private int matchAnnotation_lookahead2(int lookahead) {
		lookahead = match(lookahead, TokenType.AT);
		if (lookahead == -1)
			return -1;
		lookahead = matchQualifiedName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.LPAREN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(AT)
		nonTerminal(QualifiedName)
	) */
	private int matchAnnotation_lookahead3(int lookahead) {
		lookahead = match(lookahead, TokenType.AT);
		if (lookahead == -1)
			return -1;
		lookahead = matchQualifiedName(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		action({ run(); })
		terminal(AT)
		nonTerminal(name, QualifiedName)
		terminal(LPAREN)
		zeroOrOne(
			nonTerminal(pairs, MemberValuePairs)
		)
		terminal(RPAREN)
		action({ return dress(SNormalAnnotationExpr.make(name, ensureNotNull(pairs))); })
	) */
	protected BUTree<SNormalAnnotationExpr> parseNormalAnnotation() throws ParseException {
		BUTree<SQualifiedName> name;
		BUTree<SNodeList> pairs = null;
		run();
		parse(TokenType.AT);
		name = parseQualifiedName();
		parse(TokenType.LPAREN);
		if (match(0, TokenType.NODE_VARIABLE, TokenType.IDENTIFIER) != -1) {
			pairs = parseMemberValuePairs();
		}
		parse(TokenType.RPAREN);
		return dress(SNormalAnnotationExpr.make(name, ensureNotNull(pairs)));
	}

	/* sequence(
		terminal(AT)
		nonTerminal(name, QualifiedName)
		terminal(LPAREN)
		zeroOrOne(
			nonTerminal(pairs, MemberValuePairs)
		)
		terminal(RPAREN)
	) */
	private int matchNormalAnnotation(int lookahead) {
		lookahead = match(lookahead, TokenType.AT);
		if (lookahead == -1)
			return -1;
		lookahead = matchQualifiedName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchNormalAnnotation_5(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.RPAREN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		nonTerminal(pairs, MemberValuePairs)
	) */
	private int matchNormalAnnotation_5(int lookahead) {
		int newLookahead;
		newLookahead = matchNormalAnnotation_5_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		nonTerminal(pairs, MemberValuePairs)
	) */
	private int matchNormalAnnotation_5_1(int lookahead) {
		lookahead = matchMemberValuePairs(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		action({ run(); })
		terminal(AT)
		nonTerminal(name, QualifiedName)
		action({ return dress(SMarkerAnnotationExpr.make(name)); })
	) */
	protected BUTree<SMarkerAnnotationExpr> parseMarkerAnnotation() throws ParseException {
		BUTree<SQualifiedName> name;
		run();
		parse(TokenType.AT);
		name = parseQualifiedName();
		return dress(SMarkerAnnotationExpr.make(name));
	}

	/* sequence(
		terminal(AT)
		nonTerminal(name, QualifiedName)
	) */
	private int matchMarkerAnnotation(int lookahead) {
		lookahead = match(lookahead, TokenType.AT);
		if (lookahead == -1)
			return -1;
		lookahead = matchQualifiedName(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		action({ run(); })
		terminal(AT)
		nonTerminal(name, QualifiedName)
		terminal(LPAREN)
		nonTerminal(memberVal, MemberValue)
		terminal(RPAREN)
		action({ return dress(SSingleMemberAnnotationExpr.make(name, memberVal)); })
	) */
	protected BUTree<SSingleMemberAnnotationExpr> parseSingleMemberAnnotation() throws ParseException {
		BUTree<SQualifiedName> name;
		BUTree<? extends SExpr> memberVal;
		run();
		parse(TokenType.AT);
		name = parseQualifiedName();
		parse(TokenType.LPAREN);
		memberVal = parseMemberValue();
		parse(TokenType.RPAREN);
		return dress(SSingleMemberAnnotationExpr.make(name, memberVal));
	}

	/* sequence(
		terminal(AT)
		nonTerminal(name, QualifiedName)
		terminal(LPAREN)
		nonTerminal(memberVal, MemberValue)
		terminal(RPAREN)
	) */
	private int matchSingleMemberAnnotation(int lookahead) {
		lookahead = match(lookahead, TokenType.AT);
		if (lookahead == -1)
			return -1;
		lookahead = matchQualifiedName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchMemberValue(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.RPAREN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(pair, MemberValuePair)
		action({ ret = append(ret, pair); })
		zeroOrMore(
			terminal(COMMA)
			nonTerminal(pair, MemberValuePair)
			action({ ret = append(ret, pair); })
		)
		action({ return ret; })
	) */
	protected BUTree<SNodeList> parseMemberValuePairs() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<SMemberValuePair> pair;
		pair = parseMemberValuePair();
		ret = append(ret, pair);
		while (match(0, TokenType.COMMA) != -1) {
			parse(TokenType.COMMA);
			pair = parseMemberValuePair();
			ret = append(ret, pair);
		}
		return ret;
	}

	/* sequence(
		nonTerminal(pair, MemberValuePair)
		zeroOrMore(
			terminal(COMMA)
			nonTerminal(pair, MemberValuePair)
		)
	) */
	private int matchMemberValuePairs(int lookahead) {
		lookahead = matchMemberValuePair(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchMemberValuePairs_3(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		terminal(COMMA)
		nonTerminal(pair, MemberValuePair)
	) */
	private int matchMemberValuePairs_3(int lookahead) {
		int newLookahead;
		newLookahead = matchMemberValuePairs_3_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchMemberValuePairs_3_1(lookahead);
		}
		return lookahead;
	}

	/* sequence(
		terminal(COMMA)
		nonTerminal(pair, MemberValuePair)
	) */
	private int matchMemberValuePairs_3_1(int lookahead) {
		lookahead = match(lookahead, TokenType.COMMA);
		if (lookahead == -1)
			return -1;
		lookahead = matchMemberValuePair(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		action({ run(); })
		nonTerminal(name, Name)
		terminal(ASSIGN)
		nonTerminal(value, MemberValue)
		action({ return dress(SMemberValuePair.make(name, value)); })
	) */
	protected BUTree<SMemberValuePair> parseMemberValuePair() throws ParseException {
		BUTree<SName> name;
		BUTree<? extends SExpr> value;
		run();
		name = parseName();
		parse(TokenType.ASSIGN);
		value = parseMemberValue();
		return dress(SMemberValuePair.make(name, value));
	}

	/* sequence(
		nonTerminal(name, Name)
		terminal(ASSIGN)
		nonTerminal(value, MemberValue)
	) */
	private int matchMemberValuePair(int lookahead) {
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.ASSIGN);
		if (lookahead == -1)
			return -1;
		lookahead = matchMemberValue(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		choice(
			nonTerminal(ret, Annotation)
			nonTerminal(ret, MemberValueArrayInitializer)
			nonTerminal(ret, ConditionalExpression)
		)
		action({ return ret; })
	) */
	protected BUTree<? extends SExpr> parseMemberValue() throws ParseException {
		BUTree<? extends SExpr> ret;
		if (match(0, TokenType.AT) != -1) {
			ret = parseAnnotation();
		} else if (match(0, TokenType.LBRACE) != -1) {
			ret = parseMemberValueArrayInitializer();
		} else if (match(0, TokenType.BANG, TokenType.TILDE, TokenType.INTEGER_LITERAL, TokenType.LONG_LITERAL, TokenType.CHARACTER_LITERAL, TokenType.STRING_LITERAL, TokenType.FLOAT_LITERAL, TokenType.DOUBLE_LITERAL, TokenType.NULL, TokenType.TRUE, TokenType.FALSE, TokenType.VOID, TokenType.IDENTIFIER, TokenType.NODE_VARIABLE, TokenType.DOUBLE, TokenType.FLOAT, TokenType.LONG, TokenType.BOOLEAN, TokenType.INT, TokenType.SHORT, TokenType.BYTE, TokenType.CHAR, TokenType.NEW, TokenType.SUPER, TokenType.THIS, TokenType.LPAREN, TokenType.LT, TokenType.PLUS, TokenType.MINUS, TokenType.DECR, TokenType.INCR) != -1) {
			ret = parseConditionalExpression();
		} else {
			throw produceParseException(TokenType.FALSE, TokenType.TRUE, TokenType.NULL, TokenType.DOUBLE_LITERAL, TokenType.FLOAT_LITERAL, TokenType.STRING_LITERAL, TokenType.CHARACTER_LITERAL, TokenType.LONG_LITERAL, TokenType.INTEGER_LITERAL, TokenType.THIS, TokenType.SUPER, TokenType.NEW, TokenType.VOID, TokenType.IDENTIFIER, TokenType.NODE_VARIABLE, TokenType.CHAR, TokenType.BYTE, TokenType.SHORT, TokenType.INT, TokenType.LONG, TokenType.FLOAT, TokenType.DOUBLE, TokenType.BOOLEAN, TokenType.LT, TokenType.LPAREN, TokenType.BANG, TokenType.TILDE, TokenType.PLUS, TokenType.MINUS, TokenType.DECR, TokenType.INCR, TokenType.LBRACE, TokenType.AT);
		}
		return ret;
	}

	/* sequence(
		choice(
			nonTerminal(ret, Annotation)
			nonTerminal(ret, MemberValueArrayInitializer)
			nonTerminal(ret, ConditionalExpression)
		)
	) */
	private int matchMemberValue(int lookahead) {
		lookahead = matchMemberValue_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		nonTerminal(ret, Annotation)
		nonTerminal(ret, MemberValueArrayInitializer)
		nonTerminal(ret, ConditionalExpression)
	) */
	private int matchMemberValue_1(int lookahead) {
		int newLookahead;
		newLookahead = matchAnnotation(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchMemberValueArrayInitializer(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchConditionalExpression(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		action({ run(); })
		terminal(LBRACE)
		zeroOrOne(
			nonTerminal(member, MemberValue)
			action({ ret = append(ret, member); })
			zeroOrMore(
				negativeLookAhead(
					zeroOrOne(
						terminal(COMMA)
					)
					terminal(RBRACE)
				)
				terminal(COMMA)
				nonTerminal(member, MemberValue)
				action({ ret = append(ret, member); })
			)
		)
		zeroOrOne(
			terminal(COMMA)
			action({ trailingComma = true; })
		)
		terminal(RBRACE)
		action({ return dress(SArrayInitializerExpr.make(ret, trailingComma)); })
	) */
	protected BUTree<? extends SExpr> parseMemberValueArrayInitializer() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<? extends SExpr> member;
		boolean trailingComma = false;
		run();
		parse(TokenType.LBRACE);
		if (match(0, TokenType.DECR, TokenType.INCR, TokenType.DOUBLE_LITERAL, TokenType.FLOAT_LITERAL, TokenType.STRING_LITERAL, TokenType.CHARACTER_LITERAL, TokenType.FALSE, TokenType.TRUE, TokenType.NULL, TokenType.LONG_LITERAL, TokenType.INTEGER_LITERAL, TokenType.CHAR, TokenType.BOOLEAN, TokenType.SHORT, TokenType.BYTE, TokenType.LONG, TokenType.INT, TokenType.DOUBLE, TokenType.FLOAT, TokenType.IDENTIFIER, TokenType.NODE_VARIABLE, TokenType.VOID, TokenType.LT, TokenType.LPAREN, TokenType.THIS, TokenType.SUPER, TokenType.NEW, TokenType.BANG, TokenType.TILDE, TokenType.PLUS, TokenType.MINUS, TokenType.LBRACE, TokenType.AT) != -1) {
			member = parseMemberValue();
			ret = append(ret, member);
			while (matchMemberValueArrayInitializer_lookahead1(0) == -1) {
				parse(TokenType.COMMA);
				member = parseMemberValue();
				ret = append(ret, member);
			}
		}
		if (match(0, TokenType.COMMA) != -1) {
			parse(TokenType.COMMA);
			trailingComma = true;
		}
		parse(TokenType.RBRACE);
		return dress(SArrayInitializerExpr.make(ret, trailingComma));
	}

	/* sequence(
		terminal(LBRACE)
		zeroOrOne(
			nonTerminal(member, MemberValue)
			zeroOrMore(
				negativeLookAhead(
					zeroOrOne(
						terminal(COMMA)
					)
					terminal(RBRACE)
				)
				terminal(COMMA)
				nonTerminal(member, MemberValue)
			)
		)
		zeroOrOne(
			terminal(COMMA)
		)
		terminal(RBRACE)
	) */
	private int matchMemberValueArrayInitializer(int lookahead) {
		lookahead = match(lookahead, TokenType.LBRACE);
		if (lookahead == -1)
			return -1;
		lookahead = matchMemberValueArrayInitializer_3(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchMemberValueArrayInitializer_4(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.RBRACE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		nonTerminal(member, MemberValue)
		zeroOrMore(
			negativeLookAhead(
				zeroOrOne(
					terminal(COMMA)
				)
				terminal(RBRACE)
			)
			terminal(COMMA)
			nonTerminal(member, MemberValue)
		)
	) */
	private int matchMemberValueArrayInitializer_3(int lookahead) {
		int newLookahead;
		newLookahead = matchMemberValueArrayInitializer_3_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		nonTerminal(member, MemberValue)
		zeroOrMore(
			negativeLookAhead(
				zeroOrOne(
					terminal(COMMA)
				)
				terminal(RBRACE)
			)
			terminal(COMMA)
			nonTerminal(member, MemberValue)
		)
	) */
	private int matchMemberValueArrayInitializer_3_1(int lookahead) {
		lookahead = matchMemberValue(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchMemberValueArrayInitializer_3_1_3(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		negativeLookAhead(
			zeroOrOne(
				terminal(COMMA)
			)
			terminal(RBRACE)
		)
		terminal(COMMA)
		nonTerminal(member, MemberValue)
	) */
	private int matchMemberValueArrayInitializer_3_1_3(int lookahead) {
		int newLookahead;
		newLookahead = matchMemberValueArrayInitializer_3_1_3_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchMemberValueArrayInitializer_3_1_3_1(lookahead);
		}
		return lookahead;
	}

	/* sequence(
		negativeLookAhead(
			zeroOrOne(
				terminal(COMMA)
			)
			terminal(RBRACE)
		)
		terminal(COMMA)
		nonTerminal(member, MemberValue)
	) */
	private int matchMemberValueArrayInitializer_3_1_3_1(int lookahead) {
		lookahead = match(lookahead, TokenType.COMMA);
		if (lookahead == -1)
			return -1;
		lookahead = matchMemberValue(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		terminal(COMMA)
	) */
	private int matchMemberValueArrayInitializer_4(int lookahead) {
		int newLookahead;
		newLookahead = matchMemberValueArrayInitializer_4_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		terminal(COMMA)
	) */
	private int matchMemberValueArrayInitializer_4_1(int lookahead) {
		lookahead = match(lookahead, TokenType.COMMA);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		zeroOrOne(
			terminal(COMMA)
		)
		terminal(RBRACE)
	) */
	private int matchMemberValueArrayInitializer_lookahead1(int lookahead) {
		lookahead = matchMemberValueArrayInitializer_lookahead1_1(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, TokenType.RBRACE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		terminal(COMMA)
	) */
	private int matchMemberValueArrayInitializer_lookahead1_1(int lookahead) {
		int newLookahead;
		newLookahead = matchMemberValueArrayInitializer_lookahead1_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		terminal(COMMA)
	) */
	private int matchMemberValueArrayInitializer_lookahead1_1_1(int lookahead) {
		lookahead = match(lookahead, TokenType.COMMA);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}
}
