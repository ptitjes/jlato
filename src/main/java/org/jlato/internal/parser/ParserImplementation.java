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
import org.jlato.parser.ParserInterface.TypeKind;
import org.jlato.tree.Problem.Severity;
import org.jlato.tree.decl.ModifierKeyword;
import org.jlato.tree.expr.AssignOp;
import org.jlato.tree.expr.BinaryOp;
import org.jlato.tree.expr.UnaryOp;
import org.jlato.tree.type.Primitive;

import static org.jlato.parser.ParserImplConstants.GT;
import static org.jlato.parser.ParserImplConstants.RSIGNEDSHIFT;
import static org.jlato.parser.ParserImplConstants.RUNSIGNEDSHIFT;

/**
 * Internal implementation of the Java parser as a recursive descent parser.
 */
public class ParserImplementation extends ParserNewBase {

	/* sequence(
		terminal(NODE_LIST_VARIABLE)
		action({ return makeVar(); })
	) */
	public BUTree<SNodeList> parseNodeListVar() throws ParseException {
		parse(ParserImplConstants.NODE_LIST_VARIABLE);
		return makeVar();
	}

	/* sequence(
		terminal(NODE_LIST_VARIABLE)
	) */
	private int matchNodeListVar(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.NODE_LIST_VARIABLE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(NODE_VARIABLE)
		action({ return makeVar(); })
	) */
	public BUTree<SName> parseNodeVar() throws ParseException {
		parse(ParserImplConstants.NODE_VARIABLE);
		return makeVar();
	}

	/* sequence(
		terminal(NODE_VARIABLE)
	) */
	private int matchNodeVar(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.NODE_VARIABLE);
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
	public BUTree<SCompilationUnit> parseCompilationUnit() throws ParseException {
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
	public void parseEpilog() throws ParseException {
		if (match(0, ParserImplConstants.EOF) != -1) {
			parse(ParserImplConstants.EOF);
		} else if (match(0, ParserImplConstants.EOF) != -1) {
			parse(ParserImplConstants.EOF);
		} else {
			throw produceParseException(ParserImplConstants.EOF);
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
		lookahead = match(lookahead, ParserImplConstants.PACKAGE);
		if (lookahead == -1)
			return -1;
		lookahead = matchQualifiedName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.SEMICOLON);
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
	public BUTree<SNodeList> parseImportDecls() throws ParseException {
		BUTree<SNodeList> imports = emptyList();
		BUTree<SImportDecl> importDecl = null;
		while (match(0, ParserImplConstants.IMPORT) != -1) {
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
	public BUTree<SImportDecl> parseImportDecl() throws ParseException {
		BUTree<SQualifiedName> name;
		boolean isStatic = false;
		boolean isAsterisk = false;
		run();
		parse(ParserImplConstants.IMPORT);
		if (match(0, ParserImplConstants.STATIC) != -1) {
			parse(ParserImplConstants.STATIC);
			isStatic = true;
		}
		name = parseQualifiedName();
		if (match(0, ParserImplConstants.DOT) != -1) {
			parse(ParserImplConstants.DOT);
			parse(ParserImplConstants.STAR);
			isAsterisk = true;
		}
		parse(ParserImplConstants.SEMICOLON);
		return dress(SImportDecl.make(name, isStatic, isAsterisk));
	}

	/* sequence(
		zeroOrMore(
			nonTerminal(typeDecl, TypeDecl)
			action({ types = append(types, typeDecl); })
		)
		action({ return types; })
	) */
	public BUTree<SNodeList> parseTypeDecls() throws ParseException {
		BUTree<SNodeList> types = emptyList();
		BUTree<? extends STypeDecl> typeDecl = null;
		while (match(0, ParserImplConstants.PROTECTED, ParserImplConstants.PUBLIC, ParserImplConstants.ABSTRACT, ParserImplConstants.PRIVATE, ParserImplConstants.STATIC, ParserImplConstants._DEFAULT, ParserImplConstants.TRANSIENT, ParserImplConstants.FINAL, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.VOLATILE, ParserImplConstants.STRICTFP, ParserImplConstants.NATIVE, ParserImplConstants.AT, ParserImplConstants.ENUM, ParserImplConstants.CLASS, ParserImplConstants.INTERFACE, ParserImplConstants.SEMICOLON) != -1) {
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
					terminal(_DEFAULT)
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
	public BUTree<SNodeList> parseModifiers() throws ParseException {
		BUTree<SNodeList> modifiers = emptyList();
		BUTree<? extends SAnnotationExpr> ann;
		while (matchModifiers_lookahead1(0) != -1) {
			if (match(0, ParserImplConstants.PUBLIC) != -1) {
				parse(ParserImplConstants.PUBLIC);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Public));
			} else if (match(0, ParserImplConstants.PROTECTED) != -1) {
				parse(ParserImplConstants.PROTECTED);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Protected));
			} else if (match(0, ParserImplConstants.PRIVATE) != -1) {
				parse(ParserImplConstants.PRIVATE);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Private));
			} else if (match(0, ParserImplConstants.ABSTRACT) != -1) {
				parse(ParserImplConstants.ABSTRACT);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Abstract));
			} else if (match(0, ParserImplConstants._DEFAULT) != -1) {
				parse(ParserImplConstants._DEFAULT);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Default));
			} else if (match(0, ParserImplConstants.STATIC) != -1) {
				parse(ParserImplConstants.STATIC);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Static));
			} else if (match(0, ParserImplConstants.FINAL) != -1) {
				parse(ParserImplConstants.FINAL);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Final));
			} else if (match(0, ParserImplConstants.TRANSIENT) != -1) {
				parse(ParserImplConstants.TRANSIENT);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Transient));
			} else if (match(0, ParserImplConstants.VOLATILE) != -1) {
				parse(ParserImplConstants.VOLATILE);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Volatile));
			} else if (match(0, ParserImplConstants.SYNCHRONIZED) != -1) {
				parse(ParserImplConstants.SYNCHRONIZED);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Synchronized));
			} else if (match(0, ParserImplConstants.NATIVE) != -1) {
				parse(ParserImplConstants.NATIVE);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Native));
			} else if (match(0, ParserImplConstants.STRICTFP) != -1) {
				parse(ParserImplConstants.STRICTFP);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.StrictFP));
			} else if (match(0, ParserImplConstants.AT) != -1) {
				ann = parseAnnotation();
				modifiers = append(modifiers, ann);
			} else {
				throw produceParseException(ParserImplConstants.AT, ParserImplConstants.STRICTFP, ParserImplConstants.NATIVE, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.VOLATILE, ParserImplConstants.TRANSIENT, ParserImplConstants.FINAL, ParserImplConstants.STATIC, ParserImplConstants._DEFAULT, ParserImplConstants.ABSTRACT, ParserImplConstants.PRIVATE, ParserImplConstants.PROTECTED, ParserImplConstants.PUBLIC);
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
					terminal(_DEFAULT)
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
				terminal(_DEFAULT)
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
				terminal(_DEFAULT)
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
			terminal(_DEFAULT)
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
		lookahead = match(lookahead, ParserImplConstants.PUBLIC);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(SYNCHRONIZED)
	) */
	private int matchModifiers_1_1_2_10(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.SYNCHRONIZED);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(NATIVE)
	) */
	private int matchModifiers_1_1_2_11(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.NATIVE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(STRICTFP)
	) */
	private int matchModifiers_1_1_2_12(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.STRICTFP);
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
		lookahead = match(lookahead, ParserImplConstants.PROTECTED);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(PRIVATE)
	) */
	private int matchModifiers_1_1_2_3(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.PRIVATE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(ABSTRACT)
	) */
	private int matchModifiers_1_1_2_4(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.ABSTRACT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(_DEFAULT)
	) */
	private int matchModifiers_1_1_2_5(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants._DEFAULT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(STATIC)
	) */
	private int matchModifiers_1_1_2_6(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.STATIC);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(FINAL)
	) */
	private int matchModifiers_1_1_2_7(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.FINAL);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(TRANSIENT)
	) */
	private int matchModifiers_1_1_2_8(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.TRANSIENT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(VOLATILE)
	) */
	private int matchModifiers_1_1_2_9(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.VOLATILE);
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
				terminal(_DEFAULT)
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
		if (match(0, ParserImplConstants.VOLATILE) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.SYNCHRONIZED) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.NATIVE) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.AT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.PUBLIC) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.FINAL) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.PROTECTED) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.ABSTRACT) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants._DEFAULT) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.TRANSIENT) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.STRICTFP) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.STATIC) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.PRIVATE) != -1) {
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
	public BUTree<SNodeList> parseModifiersNoDefault() throws ParseException {
		BUTree<SNodeList> modifiers = emptyList();
		BUTree<? extends SAnnotationExpr> ann;
		while (matchModifiersNoDefault_lookahead1(0) != -1) {
			if (match(0, ParserImplConstants.PUBLIC) != -1) {
				parse(ParserImplConstants.PUBLIC);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Public));
			} else if (match(0, ParserImplConstants.PROTECTED) != -1) {
				parse(ParserImplConstants.PROTECTED);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Protected));
			} else if (match(0, ParserImplConstants.PRIVATE) != -1) {
				parse(ParserImplConstants.PRIVATE);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Private));
			} else if (match(0, ParserImplConstants.ABSTRACT) != -1) {
				parse(ParserImplConstants.ABSTRACT);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Abstract));
			} else if (match(0, ParserImplConstants.STATIC) != -1) {
				parse(ParserImplConstants.STATIC);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Static));
			} else if (match(0, ParserImplConstants.FINAL) != -1) {
				parse(ParserImplConstants.FINAL);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Final));
			} else if (match(0, ParserImplConstants.TRANSIENT) != -1) {
				parse(ParserImplConstants.TRANSIENT);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Transient));
			} else if (match(0, ParserImplConstants.VOLATILE) != -1) {
				parse(ParserImplConstants.VOLATILE);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Volatile));
			} else if (match(0, ParserImplConstants.SYNCHRONIZED) != -1) {
				parse(ParserImplConstants.SYNCHRONIZED);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Synchronized));
			} else if (match(0, ParserImplConstants.NATIVE) != -1) {
				parse(ParserImplConstants.NATIVE);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.Native));
			} else if (match(0, ParserImplConstants.STRICTFP) != -1) {
				parse(ParserImplConstants.STRICTFP);
				modifiers = append(modifiers, SModifier.make(ModifierKeyword.StrictFP));
			} else if (match(0, ParserImplConstants.AT) != -1) {
				ann = parseAnnotation();
				modifiers = append(modifiers, ann);
			} else {
				throw produceParseException(ParserImplConstants.AT, ParserImplConstants.STRICTFP, ParserImplConstants.NATIVE, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.VOLATILE, ParserImplConstants.TRANSIENT, ParserImplConstants.FINAL, ParserImplConstants.STATIC, ParserImplConstants.ABSTRACT, ParserImplConstants.PRIVATE, ParserImplConstants.PROTECTED, ParserImplConstants.PUBLIC);
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
		lookahead = match(lookahead, ParserImplConstants.PUBLIC);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(NATIVE)
	) */
	private int matchModifiersNoDefault_1_1_2_10(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.NATIVE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(STRICTFP)
	) */
	private int matchModifiersNoDefault_1_1_2_11(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.STRICTFP);
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
		lookahead = match(lookahead, ParserImplConstants.PROTECTED);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(PRIVATE)
	) */
	private int matchModifiersNoDefault_1_1_2_3(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.PRIVATE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(ABSTRACT)
	) */
	private int matchModifiersNoDefault_1_1_2_4(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.ABSTRACT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(STATIC)
	) */
	private int matchModifiersNoDefault_1_1_2_5(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.STATIC);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(FINAL)
	) */
	private int matchModifiersNoDefault_1_1_2_6(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.FINAL);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(TRANSIENT)
	) */
	private int matchModifiersNoDefault_1_1_2_7(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.TRANSIENT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(VOLATILE)
	) */
	private int matchModifiersNoDefault_1_1_2_8(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.VOLATILE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(SYNCHRONIZED)
	) */
	private int matchModifiersNoDefault_1_1_2_9(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.SYNCHRONIZED);
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
		if (match(0, ParserImplConstants.VOLATILE) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.SYNCHRONIZED) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.NATIVE) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.AT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.PUBLIC) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.FINAL) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.PROTECTED) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.ABSTRACT) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.TRANSIENT) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.STRICTFP) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.STATIC) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.PRIVATE) != -1) {
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
	public BUTree<? extends STypeDecl> parseTypeDecl() throws ParseException {
		BUTree<SNodeList> modifiers;
		BUTree<? extends STypeDecl> ret;
		run();
		if (match(0, ParserImplConstants.SEMICOLON) != -1) {
			parse(ParserImplConstants.SEMICOLON);
			ret = dress(SEmptyTypeDecl.make());
		} else if (match(0, ParserImplConstants.PROTECTED, ParserImplConstants.PUBLIC, ParserImplConstants.ABSTRACT, ParserImplConstants.PRIVATE, ParserImplConstants.STATIC, ParserImplConstants._DEFAULT, ParserImplConstants.TRANSIENT, ParserImplConstants.FINAL, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.VOLATILE, ParserImplConstants.STRICTFP, ParserImplConstants.NATIVE, ParserImplConstants.AT, ParserImplConstants.ENUM, ParserImplConstants.CLASS, ParserImplConstants.INTERFACE) != -1) {
			modifiers = parseModifiers();
			if (match(0, ParserImplConstants.CLASS, ParserImplConstants.INTERFACE) != -1) {
				ret = parseClassOrInterfaceDecl(modifiers);
			} else if (match(0, ParserImplConstants.ENUM) != -1) {
				ret = parseEnumDecl(modifiers);
			} else if (match(0, ParserImplConstants.AT) != -1) {
				ret = parseAnnotationTypeDecl(modifiers);
			} else {
				throw produceParseException(ParserImplConstants.AT, ParserImplConstants.ENUM, ParserImplConstants.INTERFACE, ParserImplConstants.CLASS);
			}
		} else {
			throw produceParseException(ParserImplConstants.VOLATILE, ParserImplConstants.TRANSIENT, ParserImplConstants.NATIVE, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.AT, ParserImplConstants.STRICTFP, ParserImplConstants.PUBLIC, ParserImplConstants.PRIVATE, ParserImplConstants.PROTECTED, ParserImplConstants._DEFAULT, ParserImplConstants.ABSTRACT, ParserImplConstants.FINAL, ParserImplConstants.STATIC, ParserImplConstants.ENUM, ParserImplConstants.CLASS, ParserImplConstants.INTERFACE, ParserImplConstants.SEMICOLON);
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
	public BUTree<? extends STypeDecl> parseClassOrInterfaceDecl(BUTree<SNodeList> modifiers) throws ParseException {
		TypeKind typeKind;
		BUTree<SName> name;
		BUTree<SNodeList> typeParams = null;
		BUTree<SQualifiedType> superClassType = null;
		BUTree<SNodeList> extendsClause = null;
		BUTree<SNodeList> implementsClause = null;
		BUTree<SNodeList> members;
		ByRef<BUProblem> problem = new ByRef<BUProblem>(null);
		if (match(0, ParserImplConstants.CLASS) != -1) {
			parse(ParserImplConstants.CLASS);
			typeKind = TypeKind.Class;
			name = parseName();
			if (match(0, ParserImplConstants.LT) != -1) {
				typeParams = parseTypeParameters();
			}
			if (match(0, ParserImplConstants.EXTENDS) != -1) {
				parse(ParserImplConstants.EXTENDS);
				superClassType = parseAnnotatedQualifiedType();
			}
			if (match(0, ParserImplConstants.IMPLEMENTS) != -1) {
				implementsClause = parseImplementsList(typeKind, problem);
			}
		} else if (match(0, ParserImplConstants.INTERFACE) != -1) {
			parse(ParserImplConstants.INTERFACE);
			typeKind = TypeKind.Interface;
			name = parseName();
			if (match(0, ParserImplConstants.LT) != -1) {
				typeParams = parseTypeParameters();
			}
			if (match(0, ParserImplConstants.EXTENDS) != -1) {
				extendsClause = parseExtendsList();
			}
		} else {
			throw produceParseException(ParserImplConstants.INTERFACE, ParserImplConstants.CLASS);
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
		lookahead = match(lookahead, ParserImplConstants.CLASS);
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
		lookahead = match(lookahead, ParserImplConstants.EXTENDS);
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
		lookahead = match(lookahead, ParserImplConstants.INTERFACE);
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
	public BUTree<SNodeList> parseExtendsList() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<SQualifiedType> cit;
		BUTree<SNodeList> annotations = null;
		parse(ParserImplConstants.EXTENDS);
		if (match(0, ParserImplConstants.AT, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE) != -1) {
			cit = parseAnnotatedQualifiedType();
			ret = append(ret, cit);
			while (match(0, ParserImplConstants.COMMA) != -1) {
				parse(ParserImplConstants.COMMA);
				cit = parseAnnotatedQualifiedType();
				ret = append(ret, cit);
			}
		} else if (quotesMode) {
			ret = parseNodeListVar();
		} else {
			throw produceParseException(ParserImplConstants.NODE_LIST_VARIABLE, ParserImplConstants.AT, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE);
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
		lookahead = match(lookahead, ParserImplConstants.EXTENDS);
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
		lookahead = match(lookahead, ParserImplConstants.COMMA);
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
	public BUTree<SNodeList> parseImplementsList(TypeKind typeKind, ByRef<BUProblem> problem) throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<SQualifiedType> cit;
		BUTree<SNodeList> annotations = null;
		parse(ParserImplConstants.IMPLEMENTS);
		if (match(0, ParserImplConstants.AT, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE) != -1) {
			cit = parseAnnotatedQualifiedType();
			ret = append(ret, cit);
			while (match(0, ParserImplConstants.COMMA) != -1) {
				parse(ParserImplConstants.COMMA);
				cit = parseAnnotatedQualifiedType();
				ret = append(ret, cit);
			}
			if (typeKind == TypeKind.Interface) problem.value = new BUProblem(Severity.ERROR, "An interface cannot implement other interfaces");

		} else if (quotesMode) {
			ret = parseNodeListVar();
		} else {
			throw produceParseException(ParserImplConstants.NODE_LIST_VARIABLE, ParserImplConstants.AT, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE);
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
		lookahead = match(lookahead, ParserImplConstants.IMPLEMENTS);
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
		lookahead = match(lookahead, ParserImplConstants.COMMA);
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
						lookAhead(2)
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
		if (match(0, ParserImplConstants.IMPLEMENTS) != -1) {
			implementsClause = parseImplementsList(TypeKind.Enum, problem);
		}
		parse(ParserImplConstants.LBRACE);
		if (match(0, ParserImplConstants.PUBLIC, ParserImplConstants.AT, ParserImplConstants.STRICTFP, ParserImplConstants.NATIVE, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.VOLATILE, ParserImplConstants.TRANSIENT, ParserImplConstants.FINAL, ParserImplConstants.STATIC, ParserImplConstants._DEFAULT, ParserImplConstants.ABSTRACT, ParserImplConstants.PRIVATE, ParserImplConstants.PROTECTED, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.NODE_LIST_VARIABLE) != -1) {
			if (match(0, ParserImplConstants.AT, ParserImplConstants.STRICTFP, ParserImplConstants.NATIVE, ParserImplConstants.SYNCHRONIZED, ParserImplConstants._DEFAULT, ParserImplConstants.ABSTRACT, ParserImplConstants.PRIVATE, ParserImplConstants.PROTECTED, ParserImplConstants.VOLATILE, ParserImplConstants.TRANSIENT, ParserImplConstants.FINAL, ParserImplConstants.STATIC, ParserImplConstants.PUBLIC, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE) != -1) {
				entry = parseEnumConstantDecl();
				constants = append(constants, entry);
				while (matchEnumDecl_lookahead1(0) != -1) {
					parse(ParserImplConstants.COMMA);
					entry = parseEnumConstantDecl();
					constants = append(constants, entry);
				}
			} else if (quotesMode) {
				constants = parseNodeListVar();
			} else {
				throw produceParseException(ParserImplConstants.NODE_LIST_VARIABLE, ParserImplConstants.PROTECTED, ParserImplConstants.PRIVATE, ParserImplConstants.PUBLIC, ParserImplConstants.STRICTFP, ParserImplConstants.AT, ParserImplConstants.STATIC, ParserImplConstants.FINAL, ParserImplConstants.ABSTRACT, ParserImplConstants._DEFAULT, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.NATIVE, ParserImplConstants.TRANSIENT, ParserImplConstants.VOLATILE, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE);
			}
		}
		if (match(0, ParserImplConstants.COMMA) != -1) {
			parse(ParserImplConstants.COMMA);
			trailingComma = true;
		}
		if (match(0, ParserImplConstants.SEMICOLON) != -1) {
			parse(ParserImplConstants.SEMICOLON);
			members = parseClassOrInterfaceBodyDecls(TypeKind.Enum);
		}
		parse(ParserImplConstants.RBRACE);
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
						lookAhead(2)
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
		lookahead = match(lookahead, ParserImplConstants.ENUM);
		if (lookahead == -1)
			return -1;
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchEnumDecl_3(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.LBRACE);
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
		lookahead = match(lookahead, ParserImplConstants.RBRACE);
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
					lookAhead(2)
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
					lookAhead(2)
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
				lookAhead(2)
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
			lookAhead(2)
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
		lookAhead(2)
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
		lookAhead(2)
		terminal(COMMA)
		nonTerminal(entry, EnumConstantDecl)
	) */
	private int matchEnumDecl_5_1_1_1_3_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.COMMA);
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
		lookahead = match(lookahead, ParserImplConstants.COMMA);
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
		lookahead = match(lookahead, ParserImplConstants.SEMICOLON);
		if (lookahead == -1)
			return -1;
		lookahead = matchClassOrInterfaceBodyDecls(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		lookAhead(2)
		terminal(COMMA)
		nonTerminal(entry, EnumConstantDecl)
	) */
	private int matchEnumDecl_lookahead1(int lookahead) {
		if (match(0, ParserImplConstants.COMMA) != -1) {
			if (match(1, ParserImplConstants.VOLATILE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SYNCHRONIZED) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NATIVE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FINAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRICTFP) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ABSTRACT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants._DEFAULT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STATIC) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PRIVATE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PUBLIC) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PROTECTED) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRANSIENT) != -1) {
				return lookahead;
			}
		}
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
	public BUTree<SEnumConstantDecl> parseEnumConstantDecl() throws ParseException {
		BUTree<SNodeList> modifiers = null;
		BUTree<SName> name;
		BUTree<SNodeList> args = null;
		BUTree<SNodeList> classBody = null;
		run();
		modifiers = parseModifiers();
		name = parseName();
		if (match(0, ParserImplConstants.LPAREN) != -1) {
			args = parseArguments();
		}
		if (match(0, ParserImplConstants.LBRACE) != -1) {
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
	public BUTree<SAnnotationDecl> parseAnnotationTypeDecl(BUTree<SNodeList> modifiers) throws ParseException {
		BUTree<SName> name;
		BUTree<SNodeList> members;
		parse(ParserImplConstants.AT);
		parse(ParserImplConstants.INTERFACE);
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
		lookahead = match(lookahead, ParserImplConstants.AT);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.INTERFACE);
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
	public BUTree<SNodeList> parseAnnotationTypeBody() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<? extends SMemberDecl> member;
		parse(ParserImplConstants.LBRACE);
		if (match(0, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.VOLATILE, ParserImplConstants.STRICTFP, ParserImplConstants.NATIVE, ParserImplConstants.AT, ParserImplConstants.PROTECTED, ParserImplConstants.PUBLIC, ParserImplConstants.ABSTRACT, ParserImplConstants.PRIVATE, ParserImplConstants.STATIC, ParserImplConstants._DEFAULT, ParserImplConstants.TRANSIENT, ParserImplConstants.FINAL, ParserImplConstants.DOUBLE, ParserImplConstants.LONG, ParserImplConstants.FLOAT, ParserImplConstants.BOOLEAN, ParserImplConstants.SHORT, ParserImplConstants.INT, ParserImplConstants.CHAR, ParserImplConstants.BYTE, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.ENUM, ParserImplConstants.CLASS, ParserImplConstants.INTERFACE, ParserImplConstants.SEMICOLON, ParserImplConstants.NODE_LIST_VARIABLE) != -1) {
			if (match(0, ParserImplConstants.PROTECTED, ParserImplConstants.PUBLIC, ParserImplConstants.ABSTRACT, ParserImplConstants.PRIVATE, ParserImplConstants.STATIC, ParserImplConstants._DEFAULT, ParserImplConstants.TRANSIENT, ParserImplConstants.FINAL, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.VOLATILE, ParserImplConstants.STRICTFP, ParserImplConstants.NATIVE, ParserImplConstants.AT, ParserImplConstants.ENUM, ParserImplConstants.INTERFACE, ParserImplConstants.CLASS, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.SEMICOLON) != -1) {
				do {
					member = parseAnnotationTypeBodyDecl();
					ret = append(ret, member);
				} while (match(0, ParserImplConstants.PROTECTED, ParserImplConstants.PUBLIC, ParserImplConstants.ABSTRACT, ParserImplConstants.PRIVATE, ParserImplConstants.STATIC, ParserImplConstants._DEFAULT, ParserImplConstants.TRANSIENT, ParserImplConstants.FINAL, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.VOLATILE, ParserImplConstants.STRICTFP, ParserImplConstants.NATIVE, ParserImplConstants.AT, ParserImplConstants.ENUM, ParserImplConstants.INTERFACE, ParserImplConstants.CLASS, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.SEMICOLON) != -1);
			} else if (quotesMode) {
				ret = parseNodeListVar();
			} else {
				throw produceParseException(ParserImplConstants.NODE_LIST_VARIABLE, ParserImplConstants.PUBLIC, ParserImplConstants.PROTECTED, ParserImplConstants.AT, ParserImplConstants.NATIVE, ParserImplConstants.STRICTFP, ParserImplConstants.VOLATILE, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.FINAL, ParserImplConstants.TRANSIENT, ParserImplConstants._DEFAULT, ParserImplConstants.STATIC, ParserImplConstants.PRIVATE, ParserImplConstants.ABSTRACT, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.CLASS, ParserImplConstants.INTERFACE, ParserImplConstants.ENUM, ParserImplConstants.SEMICOLON);
			}
		}
		parse(ParserImplConstants.RBRACE);
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
		lookahead = match(lookahead, ParserImplConstants.LBRACE);
		if (lookahead == -1)
			return -1;
		lookahead = matchAnnotationTypeBody_2(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RBRACE);
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
	public BUTree<? extends SMemberDecl> parseAnnotationTypeBodyDecl() throws ParseException {
		BUTree<SNodeList> modifiers;
		BUTree<? extends SMemberDecl> ret;
		run();
		if (match(0, ParserImplConstants.SEMICOLON) != -1) {
			parse(ParserImplConstants.SEMICOLON);
			ret = dress(SEmptyTypeDecl.make());
		} else if (match(0, ParserImplConstants.PROTECTED, ParserImplConstants.PUBLIC, ParserImplConstants.ABSTRACT, ParserImplConstants.PRIVATE, ParserImplConstants.STATIC, ParserImplConstants._DEFAULT, ParserImplConstants.TRANSIENT, ParserImplConstants.FINAL, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.VOLATILE, ParserImplConstants.STRICTFP, ParserImplConstants.NATIVE, ParserImplConstants.AT, ParserImplConstants.INTERFACE, ParserImplConstants.CLASS, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.ENUM) != -1) {
			modifiers = parseModifiers();
			if (matchAnnotationTypeBodyDecl_lookahead1(0) != -1) {
				ret = parseAnnotationTypeMemberDecl(modifiers);
			} else if (match(0, ParserImplConstants.CLASS, ParserImplConstants.INTERFACE) != -1) {
				ret = parseClassOrInterfaceDecl(modifiers);
			} else if (match(0, ParserImplConstants.ENUM) != -1) {
				ret = parseEnumDecl(modifiers);
			} else if (match(0, ParserImplConstants.AT) != -1) {
				ret = parseAnnotationTypeDecl(modifiers);
			} else if (match(0, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT) != -1) {
				ret = parseFieldDecl(modifiers);
			} else {
				throw produceParseException(ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.AT, ParserImplConstants.ENUM, ParserImplConstants.INTERFACE, ParserImplConstants.CLASS);
			}
		} else {
			throw produceParseException(ParserImplConstants.VOLATILE, ParserImplConstants.TRANSIENT, ParserImplConstants.NATIVE, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.AT, ParserImplConstants.STRICTFP, ParserImplConstants.PUBLIC, ParserImplConstants.PRIVATE, ParserImplConstants.PROTECTED, ParserImplConstants._DEFAULT, ParserImplConstants.ABSTRACT, ParserImplConstants.FINAL, ParserImplConstants.STATIC, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.ENUM, ParserImplConstants.INTERFACE, ParserImplConstants.CLASS, ParserImplConstants.SEMICOLON);
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
		lookahead = match(lookahead, ParserImplConstants.SEMICOLON);
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
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
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
			terminal(_DEFAULT)
			nonTerminal(val, MemberValue)
			action({ defaultVal = optionOf(val); })
		)
		terminal(SEMICOLON)
		action({ return dress(SAnnotationMemberDecl.make(modifiers, type, name, dims, defaultVal)); })
	) */
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
		if (match(0, ParserImplConstants._DEFAULT) != -1) {
			parse(ParserImplConstants._DEFAULT);
			val = parseMemberValue();
			defaultVal = optionOf(val);
		}
		parse(ParserImplConstants.SEMICOLON);
		return dress(SAnnotationMemberDecl.make(modifiers, type, name, dims, defaultVal));
	}

	/* sequence(
		nonTerminal(type, Type)
		nonTerminal(name, Name)
		terminal(LPAREN)
		terminal(RPAREN)
		nonTerminal(dims, ArrayDims)
		zeroOrOne(
			terminal(_DEFAULT)
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
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchArrayDims(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchAnnotationTypeMemberDecl_6(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.SEMICOLON);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		terminal(_DEFAULT)
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
		terminal(_DEFAULT)
		nonTerminal(val, MemberValue)
	) */
	private int matchAnnotationTypeMemberDecl_6_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants._DEFAULT);
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
	public BUTree<SNodeList> parseTypeParameters() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<STypeParameter> tp;
		parse(ParserImplConstants.LT);
		if (match(0, ParserImplConstants.AT, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE) != -1) {
			tp = parseTypeParameter();
			ret = append(ret, tp);
			while (match(0, ParserImplConstants.COMMA) != -1) {
				parse(ParserImplConstants.COMMA);
				tp = parseTypeParameter();
				ret = append(ret, tp);
			}
		} else if (quotesMode) {
			ret = parseNodeListVar();
		} else {
			throw produceParseException(ParserImplConstants.NODE_LIST_VARIABLE, ParserImplConstants.AT, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE);
		}
		parse(ParserImplConstants.GT);
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
		lookahead = match(lookahead, ParserImplConstants.LT);
		if (lookahead == -1)
			return -1;
		lookahead = matchTypeParameters_2(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.GT);
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
		lookahead = match(lookahead, ParserImplConstants.COMMA);
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
	public BUTree<STypeParameter> parseTypeParameter() throws ParseException {
		BUTree<SNodeList> annotations = null;
		BUTree<SName> name;
		BUTree<SNodeList> typeBounds = null;
		run();
		annotations = parseAnnotations();
		name = parseName();
		if (match(0, ParserImplConstants.EXTENDS) != -1) {
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
	public BUTree<SNodeList> parseTypeBounds() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<SQualifiedType> cit;
		BUTree<SNodeList> annotations = null;
		parse(ParserImplConstants.EXTENDS);
		if (match(0, ParserImplConstants.AT, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE) != -1) {
			cit = parseAnnotatedQualifiedType();
			ret = append(ret, cit);
			while (match(0, ParserImplConstants.BIT_AND) != -1) {
				parse(ParserImplConstants.BIT_AND);
				cit = parseAnnotatedQualifiedType();
				ret = append(ret, cit);
			}
		} else if (quotesMode) {
			ret = parseNodeListVar();
		} else {
			throw produceParseException(ParserImplConstants.NODE_LIST_VARIABLE, ParserImplConstants.AT, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE);
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
		lookahead = match(lookahead, ParserImplConstants.EXTENDS);
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
		lookahead = match(lookahead, ParserImplConstants.BIT_AND);
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
	public BUTree<SNodeList> parseClassOrInterfaceBody(TypeKind typeKind) throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<? extends SMemberDecl> member;
		parse(ParserImplConstants.LBRACE);
		ret = parseClassOrInterfaceBodyDecls(typeKind);
		parse(ParserImplConstants.RBRACE);
		return ret;
	}

	/* sequence(
		terminal(LBRACE)
		nonTerminal(ret, ClassOrInterfaceBodyDecls)
		terminal(RBRACE)
	) */
	private int matchClassOrInterfaceBody(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.LBRACE);
		if (lookahead == -1)
			return -1;
		lookahead = matchClassOrInterfaceBodyDecls(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RBRACE);
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
	public BUTree<SNodeList> parseClassOrInterfaceBodyDecls(TypeKind typeKind) throws ParseException {
		BUTree<? extends SMemberDecl> member;
		BUTree<SNodeList> ret = emptyList();
		if (match(0, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.VOLATILE, ParserImplConstants.STRICTFP, ParserImplConstants.NATIVE, ParserImplConstants.AT, ParserImplConstants.PROTECTED, ParserImplConstants.PUBLIC, ParserImplConstants.ABSTRACT, ParserImplConstants.PRIVATE, ParserImplConstants.STATIC, ParserImplConstants._DEFAULT, ParserImplConstants.TRANSIENT, ParserImplConstants.FINAL, ParserImplConstants.LBRACE, ParserImplConstants.LT, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.VOID, ParserImplConstants.ENUM, ParserImplConstants.CLASS, ParserImplConstants.INTERFACE, ParserImplConstants.SEMICOLON, ParserImplConstants.NODE_LIST_VARIABLE) != -1) {
			if (match(0, ParserImplConstants.PROTECTED, ParserImplConstants.PUBLIC, ParserImplConstants.ABSTRACT, ParserImplConstants.PRIVATE, ParserImplConstants.STATIC, ParserImplConstants._DEFAULT, ParserImplConstants.TRANSIENT, ParserImplConstants.FINAL, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.VOLATILE, ParserImplConstants.STRICTFP, ParserImplConstants.NATIVE, ParserImplConstants.AT, ParserImplConstants.LBRACE, ParserImplConstants.LT, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.ENUM, ParserImplConstants.CLASS, ParserImplConstants.INTERFACE, ParserImplConstants.FLOAT, ParserImplConstants.DOUBLE, ParserImplConstants.BYTE, ParserImplConstants.SHORT, ParserImplConstants.INT, ParserImplConstants.LONG, ParserImplConstants.BOOLEAN, ParserImplConstants.CHAR, ParserImplConstants.VOID, ParserImplConstants.SEMICOLON) != -1) {
				do {
					member = parseClassOrInterfaceBodyDecl(typeKind);
					ret = append(ret, member);
				} while (match(0, ParserImplConstants.PROTECTED, ParserImplConstants.PUBLIC, ParserImplConstants.ABSTRACT, ParserImplConstants.PRIVATE, ParserImplConstants.STATIC, ParserImplConstants._DEFAULT, ParserImplConstants.TRANSIENT, ParserImplConstants.FINAL, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.VOLATILE, ParserImplConstants.STRICTFP, ParserImplConstants.NATIVE, ParserImplConstants.AT, ParserImplConstants.LBRACE, ParserImplConstants.LT, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.ENUM, ParserImplConstants.CLASS, ParserImplConstants.INTERFACE, ParserImplConstants.FLOAT, ParserImplConstants.DOUBLE, ParserImplConstants.BYTE, ParserImplConstants.SHORT, ParserImplConstants.INT, ParserImplConstants.LONG, ParserImplConstants.BOOLEAN, ParserImplConstants.CHAR, ParserImplConstants.VOID, ParserImplConstants.SEMICOLON) != -1);
			} else if (quotesMode) {
				ret = parseNodeListVar();
			} else {
				throw produceParseException(ParserImplConstants.NODE_LIST_VARIABLE, ParserImplConstants.PUBLIC, ParserImplConstants.PROTECTED, ParserImplConstants.AT, ParserImplConstants.NATIVE, ParserImplConstants.STRICTFP, ParserImplConstants.VOLATILE, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.FINAL, ParserImplConstants.TRANSIENT, ParserImplConstants._DEFAULT, ParserImplConstants.STATIC, ParserImplConstants.PRIVATE, ParserImplConstants.ABSTRACT, ParserImplConstants.INTERFACE, ParserImplConstants.CLASS, ParserImplConstants.ENUM, ParserImplConstants.LBRACE, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.LT, ParserImplConstants.VOID, ParserImplConstants.SEMICOLON);
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
	public BUTree<? extends SMemberDecl> parseClassOrInterfaceBodyDecl(TypeKind typeKind) throws ParseException {
		BUTree<SNodeList> modifiers;
		BUTree<? extends SMemberDecl> ret;
		BUProblem problem = null;
		run();
		if (match(0, ParserImplConstants.SEMICOLON) != -1) {
			parse(ParserImplConstants.SEMICOLON);
			ret = dress(SEmptyMemberDecl.make());
		} else if (match(0, ParserImplConstants.PROTECTED, ParserImplConstants.PUBLIC, ParserImplConstants.ABSTRACT, ParserImplConstants.PRIVATE, ParserImplConstants.STATIC, ParserImplConstants._DEFAULT, ParserImplConstants.TRANSIENT, ParserImplConstants.FINAL, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.VOLATILE, ParserImplConstants.STRICTFP, ParserImplConstants.NATIVE, ParserImplConstants.AT, ParserImplConstants.ENUM, ParserImplConstants.CLASS, ParserImplConstants.INTERFACE, ParserImplConstants.LBRACE, ParserImplConstants.LT, ParserImplConstants.VOID, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.FLOAT, ParserImplConstants.DOUBLE, ParserImplConstants.INT, ParserImplConstants.LONG, ParserImplConstants.BYTE, ParserImplConstants.SHORT, ParserImplConstants.BOOLEAN, ParserImplConstants.CHAR) != -1) {
			modifiers = parseModifiers();
			if (modifiers != null && contains(modifiers, SModifier.make(ModifierKeyword.Default)) && typeKind != TypeKind.Interface) problem = new BUProblem(Severity.ERROR, "Only interfaces can have default members");

			if (match(0, ParserImplConstants.LBRACE) != -1) {
				ret = parseInitializerDecl(modifiers);
				if (typeKind == TypeKind.Interface) ret = ret.withProblem(new BUProblem(Severity.ERROR, "An interface cannot have initializers"));

			} else if (match(0, ParserImplConstants.CLASS, ParserImplConstants.INTERFACE) != -1) {
				ret = parseClassOrInterfaceDecl(modifiers);
			} else if (match(0, ParserImplConstants.ENUM) != -1) {
				ret = parseEnumDecl(modifiers);
			} else if (match(0, ParserImplConstants.AT) != -1) {
				ret = parseAnnotationTypeDecl(modifiers);
			} else if (matchClassOrInterfaceBodyDecl_lookahead1(0) != -1) {
				ret = parseConstructorDecl(modifiers);
				if (typeKind == TypeKind.Interface) ret = ret.withProblem(new BUProblem(Severity.ERROR, "An interface cannot have constructors"));

			} else if (matchClassOrInterfaceBodyDecl_lookahead2(0) != -1) {
				ret = parseFieldDecl(modifiers);
			} else if (match(0, ParserImplConstants.LT, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.VOID) != -1) {
				ret = parseMethodDecl(modifiers);
			} else {
				throw produceParseException(ParserImplConstants.LT, ParserImplConstants.VOID, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.AT, ParserImplConstants.ENUM, ParserImplConstants.INTERFACE, ParserImplConstants.CLASS, ParserImplConstants.LBRACE);
			}
		} else {
			throw produceParseException(ParserImplConstants.VOLATILE, ParserImplConstants.TRANSIENT, ParserImplConstants.NATIVE, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.AT, ParserImplConstants.STRICTFP, ParserImplConstants.PUBLIC, ParserImplConstants.PRIVATE, ParserImplConstants.PROTECTED, ParserImplConstants._DEFAULT, ParserImplConstants.ABSTRACT, ParserImplConstants.FINAL, ParserImplConstants.STATIC, ParserImplConstants.LT, ParserImplConstants.SHORT, ParserImplConstants.INT, ParserImplConstants.CHAR, ParserImplConstants.BYTE, ParserImplConstants.BOOLEAN, ParserImplConstants.DOUBLE, ParserImplConstants.LONG, ParserImplConstants.FLOAT, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.VOID, ParserImplConstants.ENUM, ParserImplConstants.INTERFACE, ParserImplConstants.CLASS, ParserImplConstants.LBRACE, ParserImplConstants.SEMICOLON);
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
		lookahead = match(lookahead, ParserImplConstants.SEMICOLON);
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
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
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
		lookahead = match(lookahead, ParserImplConstants.LBRACKET);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RBRACKET);
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
		newLookahead = match(lookahead, ParserImplConstants.COMMA);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = match(lookahead, ParserImplConstants.ASSIGN);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = match(lookahead, ParserImplConstants.SEMICOLON);
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
	public BUTree<SFieldDecl> parseFieldDecl(BUTree<SNodeList> modifiers) throws ParseException {
		BUTree<? extends SType> type;
		BUTree<SNodeList> variables = emptyList();
		BUTree<SVariableDeclarator> val;
		type = parseType(null);
		variables = parseVariableDeclarators();
		parse(ParserImplConstants.SEMICOLON);
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
		lookahead = match(lookahead, ParserImplConstants.SEMICOLON);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(type, Type)
		nonTerminal(variables, VariableDeclarators)
		action({ return dress(SLocalVariableDecl.make(modifiers, type, variables)); })
	) */
	public BUTree<SLocalVariableDecl> parseVariableDecl(BUTree<SNodeList> modifiers) throws ParseException {
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
	public BUTree<SNodeList> parseVariableDeclarators() throws ParseException {
		BUTree<SNodeList> variables = emptyList();
		BUTree<SVariableDeclarator> val;
		val = parseVariableDeclarator();
		variables = append(variables, val);
		while (match(0, ParserImplConstants.COMMA) != -1) {
			parse(ParserImplConstants.COMMA);
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
		lookahead = match(lookahead, ParserImplConstants.COMMA);
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
	public BUTree<SVariableDeclarator> parseVariableDeclarator() throws ParseException {
		BUTree<SVariableDeclaratorId> id;
		BUTree<SNodeOption> init = none();
		BUTree<? extends SExpr> initExpr = null;
		run();
		id = parseVariableDeclaratorId();
		if (match(0, ParserImplConstants.ASSIGN) != -1) {
			parse(ParserImplConstants.ASSIGN);
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
		lookahead = match(lookahead, ParserImplConstants.ASSIGN);
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
	public BUTree<SVariableDeclaratorId> parseVariableDeclaratorId() throws ParseException {
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
	public BUTree<SNodeList> parseArrayDims() throws ParseException {
		BUTree<SNodeList> arrayDims = emptyList();
		BUTree<SNodeList> annotations;
		while (matchArrayDims_lookahead1(0) != -1) {
			run();
			annotations = parseAnnotations();
			parse(ParserImplConstants.LBRACKET);
			parse(ParserImplConstants.RBRACKET);
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
		lookahead = match(lookahead, ParserImplConstants.LBRACKET);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RBRACKET);
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
		lookahead = match(lookahead, ParserImplConstants.LBRACKET);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RBRACKET);
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
	public BUTree<? extends SExpr> parseVariableInitializer() throws ParseException {
		BUTree<? extends SExpr> ret;
		if (match(0, ParserImplConstants.LBRACE) != -1) {
			ret = parseArrayInitializer();
		} else if (match(0, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.DECR, ParserImplConstants.INCR, ParserImplConstants.LPAREN, ParserImplConstants.FLOAT, ParserImplConstants.DOUBLE, ParserImplConstants.BYTE, ParserImplConstants.SHORT, ParserImplConstants.INT, ParserImplConstants.LONG, ParserImplConstants.BOOLEAN, ParserImplConstants.CHAR, ParserImplConstants.VOID, ParserImplConstants.LT, ParserImplConstants.NEW, ParserImplConstants.THIS, ParserImplConstants.SUPER, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.TRUE, ParserImplConstants.FALSE, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.STRING_LITERAL, ParserImplConstants.NULL, ParserImplConstants.BANG, ParserImplConstants.TILDE, ParserImplConstants.PLUS, ParserImplConstants.MINUS) != -1) {
			ret = parseExpression();
		} else {
			throw produceParseException(ParserImplConstants.BANG, ParserImplConstants.TILDE, ParserImplConstants.LPAREN, ParserImplConstants.SUPER, ParserImplConstants.NEW, ParserImplConstants.VOID, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.LT, ParserImplConstants.TRUE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.NULL, ParserImplConstants.FALSE, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.THIS, ParserImplConstants.INCR, ParserImplConstants.DECR, ParserImplConstants.PLUS, ParserImplConstants.MINUS, ParserImplConstants.LBRACE);
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
				lookAhead(2)
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
	public BUTree<SArrayInitializerExpr> parseArrayInitializer() throws ParseException {
		BUTree<SNodeList> values = emptyList();
		BUTree<? extends SExpr> val;
		boolean trailingComma = false;
		run();
		parse(ParserImplConstants.LBRACE);
		if (match(0, ParserImplConstants.LPAREN, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.DECR, ParserImplConstants.INCR, ParserImplConstants.MINUS, ParserImplConstants.PLUS, ParserImplConstants.SUPER, ParserImplConstants.NEW, ParserImplConstants.VOID, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.LT, ParserImplConstants.FALSE, ParserImplConstants.NULL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.STRING_LITERAL, ParserImplConstants.TRUE, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.THIS, ParserImplConstants.TILDE, ParserImplConstants.BANG, ParserImplConstants.LBRACE) != -1) {
			val = parseVariableInitializer();
			values = append(values, val);
			while (matchArrayInitializer_lookahead1(0) != -1) {
				parse(ParserImplConstants.COMMA);
				val = parseVariableInitializer();
				values = append(values, val);
			}
		}
		if (match(0, ParserImplConstants.COMMA) != -1) {
			parse(ParserImplConstants.COMMA);
			trailingComma = true;
		}
		parse(ParserImplConstants.RBRACE);
		return dress(SArrayInitializerExpr.make(values, trailingComma));
	}

	/* sequence(
		terminal(LBRACE)
		zeroOrOne(
			nonTerminal(val, VariableInitializer)
			zeroOrMore(
				lookAhead(2)
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
		lookahead = match(lookahead, ParserImplConstants.LBRACE);
		if (lookahead == -1)
			return -1;
		lookahead = matchArrayInitializer_3(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchArrayInitializer_4(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RBRACE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		nonTerminal(val, VariableInitializer)
		zeroOrMore(
			lookAhead(2)
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
			lookAhead(2)
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
		lookAhead(2)
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
		lookAhead(2)
		terminal(COMMA)
		nonTerminal(val, VariableInitializer)
	) */
	private int matchArrayInitializer_3_1_3_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.COMMA);
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
		lookahead = match(lookahead, ParserImplConstants.COMMA);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		lookAhead(2)
		terminal(COMMA)
		nonTerminal(val, VariableInitializer)
	) */
	private int matchArrayInitializer_lookahead1(int lookahead) {
		if (match(0, ParserImplConstants.COMMA) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		return -1;
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
	public BUTree<SMethodDecl> parseMethodDecl(BUTree<SNodeList> modifiers) throws ParseException {
		BUTree<SNodeList> typeParameters = null;
		BUTree<? extends SType> type;
		BUTree<SName> name;
		BUTree<SNodeList> parameters;
		BUTree<SNodeList> arrayDims;
		BUTree<SNodeList> throwsClause = null;
		BUTree<SBlockStmt> block = null;
		BUProblem problem = null;
		if (match(0, ParserImplConstants.LT) != -1) {
			typeParameters = parseTypeParameters();
		}
		type = parseResultType();
		name = parseName();
		parameters = parseFormalParameters();
		arrayDims = parseArrayDims();
		if (match(0, ParserImplConstants.THROWS) != -1) {
			throwsClause = parseThrowsClause();
		}
		if (match(0, ParserImplConstants.LBRACE) != -1) {
			block = parseBlock();
		} else if (match(0, ParserImplConstants.SEMICOLON) != -1) {
			parse(ParserImplConstants.SEMICOLON);
			if (modifiers != null && contains(modifiers, SModifier.make(ModifierKeyword.Default))) problem = new BUProblem(Severity.ERROR, "Default methods must have a body");

		} else {
			throw produceParseException(ParserImplConstants.SEMICOLON, ParserImplConstants.LBRACE);
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
		lookahead = match(lookahead, ParserImplConstants.SEMICOLON);
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
	public BUTree<SNodeList> parseFormalParameters() throws ParseException {
		BUTree<SNodeList> ret = null;
		BUTree<SFormalParameter> par;
		parse(ParserImplConstants.LPAREN);
		if (match(0, ParserImplConstants.NODE_LIST_VARIABLE, ParserImplConstants.PUBLIC, ParserImplConstants.PROTECTED, ParserImplConstants.PRIVATE, ParserImplConstants.STRICTFP, ParserImplConstants.AT, ParserImplConstants.TRANSIENT, ParserImplConstants.VOLATILE, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.NATIVE, ParserImplConstants.ABSTRACT, ParserImplConstants._DEFAULT, ParserImplConstants.STATIC, ParserImplConstants.FINAL, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.INT, ParserImplConstants.LONG, ParserImplConstants.FLOAT, ParserImplConstants.DOUBLE, ParserImplConstants.BOOLEAN, ParserImplConstants.CHAR, ParserImplConstants.BYTE, ParserImplConstants.SHORT) != -1) {
			ret = parseFormalParameterList();
		}
		parse(ParserImplConstants.RPAREN);
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
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchFormalParameters_2(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
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
	public BUTree<SNodeList> parseFormalParameterList() throws ParseException {
		BUTree<SNodeList> ret = null;
		BUTree<SFormalParameter> par;
		if (match(0, ParserImplConstants.AT, ParserImplConstants.STRICTFP, ParserImplConstants.NATIVE, ParserImplConstants.SYNCHRONIZED, ParserImplConstants._DEFAULT, ParserImplConstants.ABSTRACT, ParserImplConstants.PRIVATE, ParserImplConstants.PROTECTED, ParserImplConstants.VOLATILE, ParserImplConstants.TRANSIENT, ParserImplConstants.FINAL, ParserImplConstants.STATIC, ParserImplConstants.PUBLIC, ParserImplConstants.BOOLEAN, ParserImplConstants.CHAR, ParserImplConstants.BYTE, ParserImplConstants.SHORT, ParserImplConstants.INT, ParserImplConstants.LONG, ParserImplConstants.FLOAT, ParserImplConstants.DOUBLE, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER) != -1) {
			par = parseFormalParameter();
			ret = append(ret, par);
			while (match(0, ParserImplConstants.COMMA) != -1) {
				parse(ParserImplConstants.COMMA);
				par = parseFormalParameter();
				ret = append(ret, par);
			}
		} else if (quotesMode) {
			ret = parseNodeListVar();
		} else {
			throw produceParseException(ParserImplConstants.NODE_LIST_VARIABLE, ParserImplConstants.PROTECTED, ParserImplConstants.PRIVATE, ParserImplConstants.PUBLIC, ParserImplConstants.STRICTFP, ParserImplConstants.AT, ParserImplConstants.STATIC, ParserImplConstants.FINAL, ParserImplConstants.ABSTRACT, ParserImplConstants._DEFAULT, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.NATIVE, ParserImplConstants.TRANSIENT, ParserImplConstants.VOLATILE, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE);
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
		lookahead = match(lookahead, ParserImplConstants.COMMA);
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
		nonTerminal(id, VariableDeclaratorId)
		action({ return dress(SFormalParameter.make(modifiers, type, isVarArg, id)); })
	) */
	public BUTree<SFormalParameter> parseFormalParameter() throws ParseException {
		BUTree<SNodeList> modifiers;
		BUTree<? extends SType> type;
		boolean isVarArg = false;
		BUTree<SVariableDeclaratorId> id;
		run();
		modifiers = parseModifiers();
		type = parseType(null);
		if (match(0, ParserImplConstants.ELLIPSIS) != -1) {
			parse(ParserImplConstants.ELLIPSIS);
			isVarArg = true;
		}
		id = parseVariableDeclaratorId();
		return dress(SFormalParameter.make(modifiers, type, isVarArg, id));
	}

	/* sequence(
		nonTerminal(modifiers, Modifiers)
		nonTerminal(type, Type)
		zeroOrOne(
			terminal(ELLIPSIS)
		)
		nonTerminal(id, VariableDeclaratorId)
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
		lookahead = matchVariableDeclaratorId(lookahead);
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
		lookahead = match(lookahead, ParserImplConstants.ELLIPSIS);
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
	public BUTree<SNodeList> parseThrowsClause() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<SQualifiedType> cit;
		parse(ParserImplConstants.THROWS);
		cit = parseAnnotatedQualifiedType();
		ret = append(ret, cit);
		while (match(0, ParserImplConstants.COMMA) != -1) {
			parse(ParserImplConstants.COMMA);
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
		lookahead = match(lookahead, ParserImplConstants.THROWS);
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
		lookahead = match(lookahead, ParserImplConstants.COMMA);
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
		zeroOrOne(
			choice(
				sequence(
					lookAhead(2)
					choice(
						sequence(
							lookAhead(
								nonTerminal(ExplicitConstructorInvocation)
							)
							nonTerminal(stmt, ExplicitConstructorInvocation)
							action({ stmts = append(stmts, stmt); })
						)
						sequence(
							lookAhead(2)
							nonTerminal(stmt, BlockStatement)
							action({ stmts = append(stmts, stmt); })
						)
					)
					zeroOrMore(
						lookAhead(2)
						nonTerminal(stmt, BlockStatement)
						action({ stmts = append(stmts, stmt); })
					)
				)
				sequence(
					lookAhead({ quotesMode })
					nonTerminal(stmts, NodeListVar)
				)
			)
		)
		terminal(RBRACE)
		action({ block = dress(SBlockStmt.make(stmts)); })
		action({ return dress(SConstructorDecl.make(modifiers, ensureNotNull(typeParameters), name, parameters, ensureNotNull(throwsClause), block)); })
	) */
	public BUTree<SConstructorDecl> parseConstructorDecl(BUTree<SNodeList> modifiers) throws ParseException {
		BUTree<SNodeList> typeParameters = null;
		BUTree<SName> name;
		BUTree<SNodeList> parameters;
		BUTree<SNodeList> throwsClause = null;
		BUTree<SExplicitConstructorInvocationStmt> exConsInv = null;
		BUTree<SBlockStmt> block;
		BUTree<SNodeList> stmts = emptyList();
		BUTree<? extends SStmt> stmt;
		if (match(0, ParserImplConstants.LT) != -1) {
			typeParameters = parseTypeParameters();
		}
		name = parseName();
		parameters = parseFormalParameters();
		if (match(0, ParserImplConstants.THROWS) != -1) {
			throwsClause = parseThrowsClause();
		}
		run();
		parse(ParserImplConstants.LBRACE);
		if (match(0, ParserImplConstants.TRY, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.THROW, ParserImplConstants.RETURN, ParserImplConstants.CONTINUE, ParserImplConstants.BREAK, ParserImplConstants.FOR, ParserImplConstants.DO, ParserImplConstants.WHILE, ParserImplConstants.IF, ParserImplConstants.SWITCH, ParserImplConstants.INCR, ParserImplConstants.DECR, ParserImplConstants.FLOAT, ParserImplConstants.DOUBLE, ParserImplConstants.INT, ParserImplConstants.LONG, ParserImplConstants.BYTE, ParserImplConstants.SHORT, ParserImplConstants.BOOLEAN, ParserImplConstants.CHAR, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.VOID, ParserImplConstants.LT, ParserImplConstants.STRING_LITERAL, ParserImplConstants.TRUE, ParserImplConstants.FALSE, ParserImplConstants.NULL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.THIS, ParserImplConstants.SUPER, ParserImplConstants.NEW, ParserImplConstants.LPAREN, ParserImplConstants.SEMICOLON, ParserImplConstants.LBRACE, ParserImplConstants.ASSERT, ParserImplConstants.PROTECTED, ParserImplConstants.PUBLIC, ParserImplConstants.ABSTRACT, ParserImplConstants.PRIVATE, ParserImplConstants.NATIVE, ParserImplConstants.AT, ParserImplConstants.STRICTFP, ParserImplConstants.FINAL, ParserImplConstants.STATIC, ParserImplConstants.VOLATILE, ParserImplConstants.TRANSIENT, ParserImplConstants.INTERFACE, ParserImplConstants.CLASS, ParserImplConstants.NODE_LIST_VARIABLE) != -1) {
			if (matchConstructorDecl_lookahead1(0) != -1) {
				if (matchConstructorDecl_lookahead2(0) != -1) {
					stmt = parseExplicitConstructorInvocation();
					stmts = append(stmts, stmt);
				} else if (matchConstructorDecl_lookahead3(0) != -1) {
					stmt = parseBlockStatement();
					stmts = append(stmts, stmt);
				} else {
					throw produceParseException(ParserImplConstants.PUBLIC, ParserImplConstants.AT, ParserImplConstants.NATIVE, ParserImplConstants.STRICTFP, ParserImplConstants.VOLATILE, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.FINAL, ParserImplConstants.TRANSIENT, ParserImplConstants.ABSTRACT, ParserImplConstants.STATIC, ParserImplConstants.PROTECTED, ParserImplConstants.PRIVATE, ParserImplConstants.INT, ParserImplConstants.LONG, ParserImplConstants.FLOAT, ParserImplConstants.DOUBLE, ParserImplConstants.BOOLEAN, ParserImplConstants.CHAR, ParserImplConstants.BYTE, ParserImplConstants.SHORT, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.TRY, ParserImplConstants.CONTINUE, ParserImplConstants.RETURN, ParserImplConstants.THROW, ParserImplConstants.WHILE, ParserImplConstants.DO, ParserImplConstants.FOR, ParserImplConstants.BREAK, ParserImplConstants.SEMICOLON, ParserImplConstants.NEW, ParserImplConstants.VOID, ParserImplConstants.LT, ParserImplConstants.LPAREN, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.NULL, ParserImplConstants.FALSE, ParserImplConstants.TRUE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.THIS, ParserImplConstants.SUPER, ParserImplConstants.DECR, ParserImplConstants.INCR, ParserImplConstants.SWITCH, ParserImplConstants.IF, ParserImplConstants.ASSERT, ParserImplConstants.LBRACE, ParserImplConstants.CLASS, ParserImplConstants.INTERFACE);
				}
				while (matchConstructorDecl_lookahead4(0) != -1) {
					stmt = parseBlockStatement();
					stmts = append(stmts, stmt);
				}
			} else if (quotesMode) {
				stmts = parseNodeListVar();
			} else {
				throw produceParseException(ParserImplConstants.NODE_LIST_VARIABLE, ParserImplConstants.PUBLIC, ParserImplConstants.PROTECTED, ParserImplConstants.PRIVATE, ParserImplConstants.ABSTRACT, ParserImplConstants.STATIC, ParserImplConstants.FINAL, ParserImplConstants.TRANSIENT, ParserImplConstants.VOLATILE, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.NATIVE, ParserImplConstants.STRICTFP, ParserImplConstants.AT, ParserImplConstants.CLASS, ParserImplConstants.INTERFACE, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.DOUBLE, ParserImplConstants.LBRACE, ParserImplConstants.SEMICOLON, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.FALSE, ParserImplConstants.NULL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.STRING_LITERAL, ParserImplConstants.TRUE, ParserImplConstants.THIS, ParserImplConstants.VOID, ParserImplConstants.SUPER, ParserImplConstants.NEW, ParserImplConstants.LPAREN, ParserImplConstants.LT, ParserImplConstants.INCR, ParserImplConstants.DECR, ParserImplConstants.SWITCH, ParserImplConstants.ASSERT, ParserImplConstants.BREAK, ParserImplConstants.CONTINUE, ParserImplConstants.RETURN, ParserImplConstants.THROW, ParserImplConstants.IF, ParserImplConstants.WHILE, ParserImplConstants.DO, ParserImplConstants.FOR, ParserImplConstants.TRY);
			}
		}
		parse(ParserImplConstants.RBRACE);
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
		zeroOrOne(
			choice(
				sequence(
					lookAhead(2)
					choice(
						sequence(
							lookAhead(
								nonTerminal(ExplicitConstructorInvocation)
							)
							nonTerminal(stmt, ExplicitConstructorInvocation)
						)
						sequence(
							lookAhead(2)
							nonTerminal(stmt, BlockStatement)
						)
					)
					zeroOrMore(
						lookAhead(2)
						nonTerminal(stmt, BlockStatement)
					)
				)
				sequence(
					lookAhead({ quotesMode })
					nonTerminal(stmts, NodeListVar)
				)
			)
		)
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
		lookahead = match(lookahead, ParserImplConstants.LBRACE);
		if (lookahead == -1)
			return -1;
		lookahead = matchConstructorDecl_7(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RBRACE);
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

	/* zeroOrOne(
		choice(
			sequence(
				lookAhead(2)
				choice(
					sequence(
						lookAhead(
							nonTerminal(ExplicitConstructorInvocation)
						)
						nonTerminal(stmt, ExplicitConstructorInvocation)
					)
					sequence(
						lookAhead(2)
						nonTerminal(stmt, BlockStatement)
					)
				)
				zeroOrMore(
					lookAhead(2)
					nonTerminal(stmt, BlockStatement)
				)
			)
			sequence(
				lookAhead({ quotesMode })
				nonTerminal(stmts, NodeListVar)
			)
		)
	) */
	private int matchConstructorDecl_7(int lookahead) {
		int newLookahead;
		newLookahead = matchConstructorDecl_7_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		choice(
			sequence(
				lookAhead(2)
				choice(
					sequence(
						lookAhead(
							nonTerminal(ExplicitConstructorInvocation)
						)
						nonTerminal(stmt, ExplicitConstructorInvocation)
					)
					sequence(
						lookAhead(2)
						nonTerminal(stmt, BlockStatement)
					)
				)
				zeroOrMore(
					lookAhead(2)
					nonTerminal(stmt, BlockStatement)
				)
			)
			sequence(
				lookAhead({ quotesMode })
				nonTerminal(stmts, NodeListVar)
			)
		)
	) */
	private int matchConstructorDecl_7_1(int lookahead) {
		lookahead = matchConstructorDecl_7_1_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			lookAhead(2)
			choice(
				sequence(
					lookAhead(
						nonTerminal(ExplicitConstructorInvocation)
					)
					nonTerminal(stmt, ExplicitConstructorInvocation)
				)
				sequence(
					lookAhead(2)
					nonTerminal(stmt, BlockStatement)
				)
			)
			zeroOrMore(
				lookAhead(2)
				nonTerminal(stmt, BlockStatement)
			)
		)
		sequence(
			lookAhead({ quotesMode })
			nonTerminal(stmts, NodeListVar)
		)
	) */
	private int matchConstructorDecl_7_1_1(int lookahead) {
		int newLookahead;
		newLookahead = matchConstructorDecl_7_1_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchConstructorDecl_7_1_1_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		lookAhead(2)
		choice(
			sequence(
				lookAhead(
					nonTerminal(ExplicitConstructorInvocation)
				)
				nonTerminal(stmt, ExplicitConstructorInvocation)
			)
			sequence(
				lookAhead(2)
				nonTerminal(stmt, BlockStatement)
			)
		)
		zeroOrMore(
			lookAhead(2)
			nonTerminal(stmt, BlockStatement)
		)
	) */
	private int matchConstructorDecl_7_1_1_1(int lookahead) {
		lookahead = matchConstructorDecl_7_1_1_1_2(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchConstructorDecl_7_1_1_1_3(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			lookAhead(
				nonTerminal(ExplicitConstructorInvocation)
			)
			nonTerminal(stmt, ExplicitConstructorInvocation)
		)
		sequence(
			lookAhead(2)
			nonTerminal(stmt, BlockStatement)
		)
	) */
	private int matchConstructorDecl_7_1_1_1_2(int lookahead) {
		int newLookahead;
		newLookahead = matchConstructorDecl_7_1_1_1_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchConstructorDecl_7_1_1_1_2_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		lookAhead(
			nonTerminal(ExplicitConstructorInvocation)
		)
		nonTerminal(stmt, ExplicitConstructorInvocation)
	) */
	private int matchConstructorDecl_7_1_1_1_2_1(int lookahead) {
		lookahead = matchExplicitConstructorInvocation(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead(2)
		nonTerminal(stmt, BlockStatement)
	) */
	private int matchConstructorDecl_7_1_1_1_2_2(int lookahead) {
		lookahead = matchBlockStatement(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		lookAhead(2)
		nonTerminal(stmt, BlockStatement)
	) */
	private int matchConstructorDecl_7_1_1_1_3(int lookahead) {
		int newLookahead;
		newLookahead = matchConstructorDecl_7_1_1_1_3_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchConstructorDecl_7_1_1_1_3_1(lookahead);
		}
		return lookahead;
	}

	/* sequence(
		lookAhead(2)
		nonTerminal(stmt, BlockStatement)
	) */
	private int matchConstructorDecl_7_1_1_1_3_1(int lookahead) {
		lookahead = matchBlockStatement(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead({ quotesMode })
		nonTerminal(stmts, NodeListVar)
	) */
	private int matchConstructorDecl_7_1_1_2(int lookahead) {
		lookahead = quotesMode ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = matchNodeListVar(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead(2)
		choice(
			sequence(
				lookAhead(
					nonTerminal(ExplicitConstructorInvocation)
				)
				nonTerminal(stmt, ExplicitConstructorInvocation)
			)
			sequence(
				lookAhead(2)
				nonTerminal(stmt, BlockStatement)
			)
		)
		zeroOrMore(
			lookAhead(2)
			nonTerminal(stmt, BlockStatement)
		)
	) */
	private int matchConstructorDecl_lookahead1(int lookahead) {
		if (match(0, ParserImplConstants.VOLATILE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.NEW) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.THROW) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.LT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.HOOK) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_LIST_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.GT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.INTEGER_LITERAL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.DO) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THROW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DO) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RETURN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BREAK) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRY) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IF) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SYNCHRONIZED) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSERT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FOR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CONTINUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SWITCH) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.WHILE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACE) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.CLASS) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.STATIC) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.INTERFACE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.SEMICOLON) != -1) {
			if (match(1, ParserImplConstants.VOLATILE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THROW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DO) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STATIC) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BREAK) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRY) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IF) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SYNCHRONIZED) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FOR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FINAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PUBLIC) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SWITCH) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRANSIENT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NATIVE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ABSTRACT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRICTFP) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RETURN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PROTECTED) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSERT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PRIVATE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CONTINUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.WHILE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.BREAK) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.BYTE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.TRY) != -1) {
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACE) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.IF) != -1) {
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.SYNCHRONIZED) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.NULL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.CHARACTER_LITERAL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.FOR) != -1) {
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.LPAREN) != -1) {
			if (match(1, ParserImplConstants.VOLATILE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NATIVE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ABSTRACT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants._DEFAULT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRICTFP) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STATIC) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PROTECTED) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SYNCHRONIZED) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FINAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_LIST_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PRIVATE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PUBLIC) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRANSIENT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.TRUE) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.LONG_LITERAL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.FINAL) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.BOOLEAN) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.DECR) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.AT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.THIS) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.PUBLIC) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.SWITCH) != -1) {
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.VOID) != -1) {
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.TRANSIENT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.LBRACE) != -1) {
			if (match(1, ParserImplConstants.VOLATILE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THROW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DO) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STATIC) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BREAK) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRY) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IF) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SYNCHRONIZED) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FOR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FINAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RBRACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PUBLIC) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SWITCH) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRANSIENT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NATIVE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ABSTRACT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRICTFP) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RETURN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PROTECTED) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSERT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_LIST_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PRIVATE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CONTINUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.WHILE) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.NODE_VARIABLE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ARROW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.COLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.FLOAT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.NATIVE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.CHAR) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.STRICTFP) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.ABSTRACT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.INT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.RETURN) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.IDENTIFIER) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ARROW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.COLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.DOUBLE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.PROTECTED) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.LONG) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.SUPER) != -1) {
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.DOUBLE_LITERAL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.ASSERT) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.SHORT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.PRIVATE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.CONTINUE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.FLOAT_LITERAL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.INCR) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.WHILE) != -1) {
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.FALSE) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.STRING_LITERAL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		return -1;
	}

	/* sequence(
		nonTerminal(ExplicitConstructorInvocation)
	) */
	private int matchConstructorDecl_lookahead2(int lookahead) {
		lookahead = matchExplicitConstructorInvocation(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead(2)
		nonTerminal(stmt, BlockStatement)
	) */
	private int matchConstructorDecl_lookahead3(int lookahead) {
		if (match(0, ParserImplConstants.VOLATILE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.NEW) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.THROW) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.LT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.HOOK) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_LIST_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.GT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.INTEGER_LITERAL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.CLASS) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.DO) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THROW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DO) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RETURN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BREAK) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRY) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IF) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SYNCHRONIZED) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSERT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FOR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CONTINUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.WHILE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SWITCH) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACE) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.STATIC) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.INTERFACE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.SEMICOLON) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.BREAK) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.BYTE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.TRY) != -1) {
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACE) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.IF) != -1) {
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.SYNCHRONIZED) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.NULL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.CHARACTER_LITERAL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.FOR) != -1) {
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.LPAREN) != -1) {
			if (match(1, ParserImplConstants.VOLATILE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NATIVE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRICTFP) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants._DEFAULT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ABSTRACT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STATIC) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PROTECTED) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SYNCHRONIZED) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FINAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_LIST_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PRIVATE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PUBLIC) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRANSIENT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.TRUE) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.FINAL) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.LONG_LITERAL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.BOOLEAN) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.DECR) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.AT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.PUBLIC) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.THIS) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.SWITCH) != -1) {
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.VOID) != -1) {
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.TRANSIENT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.LBRACE) != -1) {
			if (match(1, ParserImplConstants.VOLATILE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THROW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DO) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STATIC) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BREAK) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRY) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IF) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SYNCHRONIZED) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FOR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FINAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RBRACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PUBLIC) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SWITCH) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRANSIENT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NATIVE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRICTFP) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ABSTRACT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RETURN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PROTECTED) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSERT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_LIST_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PRIVATE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CONTINUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.WHILE) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.NODE_VARIABLE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.COLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ARROW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.NATIVE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.FLOAT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.CHAR) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.STRICTFP) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.ABSTRACT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.INT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.RETURN) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.DOUBLE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.IDENTIFIER) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.COLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ARROW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.PROTECTED) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.LONG) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.SUPER) != -1) {
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.DOUBLE_LITERAL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.ASSERT) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.PRIVATE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.SHORT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.CONTINUE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.FLOAT_LITERAL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.INCR) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.WHILE) != -1) {
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.STRING_LITERAL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.FALSE) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		return -1;
	}

	/* zeroOrMore(
		lookAhead(2)
		nonTerminal(stmt, BlockStatement)
	) */
	private int matchConstructorDecl_lookahead4(int lookahead) {
		if (match(0, ParserImplConstants.VOLATILE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.NEW) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.THROW) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.LT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.HOOK) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_LIST_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.GT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.INTEGER_LITERAL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.CLASS) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.DO) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THROW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DO) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RETURN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BREAK) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRY) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IF) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SYNCHRONIZED) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSERT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FOR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CONTINUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.WHILE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SWITCH) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACE) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.STATIC) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.INTERFACE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.SEMICOLON) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.BREAK) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.BYTE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.TRY) != -1) {
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACE) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.IF) != -1) {
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.SYNCHRONIZED) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.NULL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.CHARACTER_LITERAL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.FOR) != -1) {
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.LPAREN) != -1) {
			if (match(1, ParserImplConstants.VOLATILE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NATIVE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRICTFP) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants._DEFAULT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ABSTRACT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STATIC) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PROTECTED) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SYNCHRONIZED) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FINAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_LIST_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PRIVATE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PUBLIC) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRANSIENT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.TRUE) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.FINAL) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.LONG_LITERAL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.BOOLEAN) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.DECR) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.AT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.PUBLIC) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.THIS) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.SWITCH) != -1) {
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.VOID) != -1) {
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.TRANSIENT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.LBRACE) != -1) {
			if (match(1, ParserImplConstants.VOLATILE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THROW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DO) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STATIC) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BREAK) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRY) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IF) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SYNCHRONIZED) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FOR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FINAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RBRACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PUBLIC) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SWITCH) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRANSIENT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NATIVE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRICTFP) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ABSTRACT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RETURN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PROTECTED) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSERT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_LIST_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PRIVATE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CONTINUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.WHILE) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.NODE_VARIABLE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.COLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ARROW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.NATIVE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.FLOAT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.CHAR) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.STRICTFP) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.ABSTRACT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.INT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.RETURN) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.DOUBLE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.IDENTIFIER) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.COLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ARROW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.PROTECTED) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.LONG) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.SUPER) != -1) {
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.DOUBLE_LITERAL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.ASSERT) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.PRIVATE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.SHORT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.CONTINUE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.FLOAT_LITERAL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.INCR) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.WHILE) != -1) {
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.STRING_LITERAL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.FALSE) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		return -1;
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
	public BUTree<SExplicitConstructorInvocationStmt> parseExplicitConstructorInvocation() throws ParseException {
		boolean isThis = false;
		BUTree<SNodeList> args;
		BUTree<? extends SExpr> expr = null;
		BUTree<SNodeList> typeArgs = null;
		run();
		if (matchExplicitConstructorInvocation_lookahead1(0) != -1) {
			if (match(0, ParserImplConstants.LT) != -1) {
				typeArgs = parseTypeArguments();
			}
			parse(ParserImplConstants.THIS);
			isThis = true;
			args = parseArguments();
			parse(ParserImplConstants.SEMICOLON);
		} else if (match(0, ParserImplConstants.LPAREN, ParserImplConstants.NEW, ParserImplConstants.SUPER, ParserImplConstants.THIS, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.NULL, ParserImplConstants.FALSE, ParserImplConstants.TRUE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.VOID, ParserImplConstants.BOOLEAN, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.DOUBLE) != -1) {
			if (matchExplicitConstructorInvocation_lookahead2(0) != -1) {
				expr = parsePrimaryExpressionWithoutSuperSuffix();
				parse(ParserImplConstants.DOT);
			}
			if (match(0, ParserImplConstants.LT) != -1) {
				typeArgs = parseTypeArguments();
			}
			parse(ParserImplConstants.SUPER);
			args = parseArguments();
			parse(ParserImplConstants.SEMICOLON);
		} else {
			throw produceParseException(ParserImplConstants.SUPER, ParserImplConstants.THIS, ParserImplConstants.STRING_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.FALSE, ParserImplConstants.TRUE, ParserImplConstants.NULL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.LPAREN, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.VOID, ParserImplConstants.NEW);
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
		lookahead = match(lookahead, ParserImplConstants.THIS);
		if (lookahead == -1)
			return -1;
		lookahead = matchArguments(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.SEMICOLON);
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
		lookahead = match(lookahead, ParserImplConstants.SUPER);
		if (lookahead == -1)
			return -1;
		lookahead = matchArguments(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.SEMICOLON);
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
		lookahead = match(lookahead, ParserImplConstants.DOT);
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
		lookahead = match(lookahead, ParserImplConstants.THIS);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
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
		lookahead = match(lookahead, ParserImplConstants.DOT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		zeroOrOne(
			lookAhead(2)
			choice(
				oneOrMore(
					nonTerminal(stmt, BlockStatement)
					action({ ret = append(ret, stmt); })
				)
				sequence(
					lookAhead({ quotesMode })
					nonTerminal(ret, NodeListVar)
				)
			)
		)
		action({ return ensureNotNull(ret); })
	) */
	public BUTree<SNodeList> parseStatements() throws ParseException {
		BUTree<SNodeList> ret = null;
		BUTree<? extends SStmt> stmt;
		if (matchStatements_lookahead1(0) != -1) {
			if (match(0, ParserImplConstants.DO, ParserImplConstants.WHILE, ParserImplConstants.IF, ParserImplConstants.SWITCH, ParserImplConstants.RETURN, ParserImplConstants.CONTINUE, ParserImplConstants.BREAK, ParserImplConstants.FOR, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.TRUE, ParserImplConstants.FALSE, ParserImplConstants.NULL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.STRING_LITERAL, ParserImplConstants.VOID, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.DOUBLE, ParserImplConstants.BOOLEAN, ParserImplConstants.NEW, ParserImplConstants.SUPER, ParserImplConstants.THIS, ParserImplConstants.LPAREN, ParserImplConstants.LT, ParserImplConstants.DECR, ParserImplConstants.INCR, ParserImplConstants.SEMICOLON, ParserImplConstants.LBRACE, ParserImplConstants.ASSERT, ParserImplConstants.TRY, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.THROW, ParserImplConstants.PUBLIC, ParserImplConstants.PRIVATE, ParserImplConstants.PROTECTED, ParserImplConstants.AT, ParserImplConstants.VOLATILE, ParserImplConstants.STRICTFP, ParserImplConstants.NATIVE, ParserImplConstants.STATIC, ParserImplConstants.ABSTRACT, ParserImplConstants.TRANSIENT, ParserImplConstants.FINAL, ParserImplConstants.CLASS, ParserImplConstants.INTERFACE) != -1) {
				do {
					stmt = parseBlockStatement();
					ret = append(ret, stmt);
				} while (match(0, ParserImplConstants.DO, ParserImplConstants.WHILE, ParserImplConstants.IF, ParserImplConstants.SWITCH, ParserImplConstants.RETURN, ParserImplConstants.CONTINUE, ParserImplConstants.BREAK, ParserImplConstants.FOR, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.TRUE, ParserImplConstants.FALSE, ParserImplConstants.NULL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.STRING_LITERAL, ParserImplConstants.VOID, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.DOUBLE, ParserImplConstants.BOOLEAN, ParserImplConstants.NEW, ParserImplConstants.SUPER, ParserImplConstants.THIS, ParserImplConstants.LPAREN, ParserImplConstants.LT, ParserImplConstants.DECR, ParserImplConstants.INCR, ParserImplConstants.SEMICOLON, ParserImplConstants.LBRACE, ParserImplConstants.ASSERT, ParserImplConstants.TRY, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.THROW, ParserImplConstants.PUBLIC, ParserImplConstants.PRIVATE, ParserImplConstants.PROTECTED, ParserImplConstants.AT, ParserImplConstants.VOLATILE, ParserImplConstants.STRICTFP, ParserImplConstants.NATIVE, ParserImplConstants.STATIC, ParserImplConstants.ABSTRACT, ParserImplConstants.TRANSIENT, ParserImplConstants.FINAL, ParserImplConstants.CLASS, ParserImplConstants.INTERFACE) != -1);
			} else if (quotesMode) {
				ret = parseNodeListVar();
			} else {
				throw produceParseException(ParserImplConstants.NODE_LIST_VARIABLE, ParserImplConstants.TRY, ParserImplConstants.DO, ParserImplConstants.WHILE, ParserImplConstants.BREAK, ParserImplConstants.FOR, ParserImplConstants.RETURN, ParserImplConstants.CONTINUE, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.THROW, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LBRACE, ParserImplConstants.ASSERT, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.NULL, ParserImplConstants.FALSE, ParserImplConstants.TRUE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.SUPER, ParserImplConstants.THIS, ParserImplConstants.CHAR, ParserImplConstants.BYTE, ParserImplConstants.BOOLEAN, ParserImplConstants.LONG, ParserImplConstants.FLOAT, ParserImplConstants.SHORT, ParserImplConstants.INT, ParserImplConstants.DOUBLE, ParserImplConstants.VOID, ParserImplConstants.NEW, ParserImplConstants.LT, ParserImplConstants.LPAREN, ParserImplConstants.INCR, ParserImplConstants.DECR, ParserImplConstants.SEMICOLON, ParserImplConstants.IF, ParserImplConstants.SWITCH, ParserImplConstants.NATIVE, ParserImplConstants.STRICTFP, ParserImplConstants.AT, ParserImplConstants.PUBLIC, ParserImplConstants.PROTECTED, ParserImplConstants.PRIVATE, ParserImplConstants.ABSTRACT, ParserImplConstants.STATIC, ParserImplConstants.FINAL, ParserImplConstants.TRANSIENT, ParserImplConstants.VOLATILE, ParserImplConstants.INTERFACE, ParserImplConstants.CLASS);
			}
		}
		return ensureNotNull(ret);
	}

	/* sequence(
		zeroOrOne(
			lookAhead(2)
			choice(
				oneOrMore(
					nonTerminal(stmt, BlockStatement)
				)
				sequence(
					lookAhead({ quotesMode })
					nonTerminal(ret, NodeListVar)
				)
			)
		)
	) */
	private int matchStatements(int lookahead) {
		lookahead = matchStatements_1(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		lookAhead(2)
		choice(
			oneOrMore(
				nonTerminal(stmt, BlockStatement)
			)
			sequence(
				lookAhead({ quotesMode })
				nonTerminal(ret, NodeListVar)
			)
		)
	) */
	private int matchStatements_1(int lookahead) {
		int newLookahead;
		newLookahead = matchStatements_1_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		lookAhead(2)
		choice(
			oneOrMore(
				nonTerminal(stmt, BlockStatement)
			)
			sequence(
				lookAhead({ quotesMode })
				nonTerminal(ret, NodeListVar)
			)
		)
	) */
	private int matchStatements_1_1(int lookahead) {
		lookahead = matchStatements_1_1_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		oneOrMore(
			nonTerminal(stmt, BlockStatement)
		)
		sequence(
			lookAhead({ quotesMode })
			nonTerminal(ret, NodeListVar)
		)
	) */
	private int matchStatements_1_1_2(int lookahead) {
		int newLookahead;
		newLookahead = matchStatements_1_1_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchStatements_1_1_2_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* oneOrMore(
		nonTerminal(stmt, BlockStatement)
	) */
	private int matchStatements_1_1_2_1(int lookahead) {
		int newLookahead;
		newLookahead = matchStatements_1_1_2_1_1(lookahead);
		if (newLookahead == -1)
			return -1;
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchStatements_1_1_2_1_1(lookahead);
		}
		return lookahead;
	}

	/* sequence(
		nonTerminal(stmt, BlockStatement)
	) */
	private int matchStatements_1_1_2_1_1(int lookahead) {
		lookahead = matchBlockStatement(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead({ quotesMode })
		nonTerminal(ret, NodeListVar)
	) */
	private int matchStatements_1_1_2_2(int lookahead) {
		lookahead = quotesMode ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = matchNodeListVar(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		lookAhead(2)
		choice(
			oneOrMore(
				nonTerminal(stmt, BlockStatement)
			)
			sequence(
				lookAhead({ quotesMode })
				nonTerminal(ret, NodeListVar)
			)
		)
	) */
	private int matchStatements_lookahead1(int lookahead) {
		if (match(0, ParserImplConstants.VOLATILE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.NEW) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.THROW) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.LT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.HOOK) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_LIST_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.GT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.INTEGER_LITERAL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.DO) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THROW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DO) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RETURN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BREAK) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRY) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IF) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SYNCHRONIZED) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSERT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FOR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CONTINUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.WHILE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SWITCH) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACE) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.CLASS) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.STATIC) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.INTERFACE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.SEMICOLON) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.BREAK) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.BYTE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.TRY) != -1) {
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACE) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.IF) != -1) {
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.SYNCHRONIZED) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.NULL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.CHARACTER_LITERAL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.LPAREN) != -1) {
			if (match(1, ParserImplConstants.VOLATILE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NATIVE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRICTFP) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants._DEFAULT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ABSTRACT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STATIC) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PROTECTED) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SYNCHRONIZED) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FINAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_LIST_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PRIVATE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PUBLIC) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRANSIENT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.FOR) != -1) {
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.TRUE) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.LONG_LITERAL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.FINAL) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.BOOLEAN) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.DECR) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.AT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.THIS) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.PUBLIC) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.SWITCH) != -1) {
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.VOID) != -1) {
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.TRANSIENT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.LBRACE) != -1) {
			if (match(1, ParserImplConstants.VOLATILE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THROW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DO) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STATIC) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BREAK) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRY) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IF) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SYNCHRONIZED) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FOR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FINAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RBRACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PUBLIC) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SWITCH) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRANSIENT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NATIVE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRICTFP) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ABSTRACT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RETURN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PROTECTED) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSERT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_LIST_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PRIVATE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CONTINUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.WHILE) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.NODE_VARIABLE) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.COLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ARROW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.FLOAT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.NATIVE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.CHAR) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.ABSTRACT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.STRICTFP) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.INT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.RETURN) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.DOUBLE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.IDENTIFIER) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.COLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ARROW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.PROTECTED) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.LONG) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.SUPER) != -1) {
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.DOUBLE_LITERAL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.ASSERT) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.NODE_LIST_VARIABLE) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.SHORT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.PRIVATE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTERFACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CLASS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.CONTINUE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.FLOAT_LITERAL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.INCR) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.WHILE) != -1) {
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.FALSE) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.STRING_LITERAL) != -1) {
			if (match(1, ParserImplConstants.ANDASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACKET) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LSHIFTASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.XORASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STARASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLECOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SLASHASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SEMICOLON) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.REMASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUSASSIGN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUSASSIGN) != -1) {
				return lookahead;
			}
		}
		return -1;
	}

	/* sequence(
		nonTerminal(block, Block)
		action({ return dress(SInitializerDecl.make(modifiers, block)); })
	) */
	public BUTree<SInitializerDecl> parseInitializerDecl(BUTree<SNodeList> modifiers) throws ParseException {
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
	public BUTree<? extends SType> parseType(BUTree<SNodeList> annotations) throws ParseException {
		BUTree<? extends SType> primitiveType = null;
		BUTree<? extends SReferenceType> type = null;
		BUTree<SNodeList> arrayDims;
		if (match(0, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN) != -1) {
			primitiveType = parsePrimitiveType(annotations);
			if (matchType_lookahead1(0) != -1) {
				lateRun();
				arrayDims = parseArrayDimsMandatory();
				type = dress(SArrayType.make(primitiveType, arrayDims));
			}
		} else if (match(0, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER) != -1) {
			type = parseQualifiedType(annotations);
			if (matchType_lookahead2(0) != -1) {
				lateRun();
				arrayDims = parseArrayDimsMandatory();
				type = dress(SArrayType.make(type, arrayDims));
			}
		} else {
			throw produceParseException(ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN);
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
		lookahead = match(lookahead, ParserImplConstants.LBRACKET);
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
		lookahead = match(lookahead, ParserImplConstants.LBRACKET);
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
	public BUTree<? extends SReferenceType> parseReferenceType(BUTree<SNodeList> annotations) throws ParseException {
		BUTree<? extends SType> primitiveType;
		BUTree<? extends SReferenceType> type;
		BUTree<SNodeList> arrayDims;
		if (match(0, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN) != -1) {
			primitiveType = parsePrimitiveType(annotations);
			lateRun();
			arrayDims = parseArrayDimsMandatory();
			type = dress(SArrayType.make(primitiveType, arrayDims));
		} else if (match(0, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER) != -1) {
			type = parseQualifiedType(annotations);
			if (matchReferenceType_lookahead1(0) != -1) {
				lateRun();
				arrayDims = parseArrayDimsMandatory();
				type = dress(SArrayType.make(type, arrayDims));
			}
		} else {
			throw produceParseException(ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN);
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
		lookahead = match(lookahead, ParserImplConstants.LBRACKET);
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
		if (matchQualifiedType_lookahead1(0) != -1) {
			typeArgs = parseTypeArgumentsOrDiamond();
		}
		ret = dress(SQualifiedType.make(annotations, scope, name, optionOf(typeArgs)));
		while (matchQualifiedType_lookahead2(0) != -1) {
			lateRun();
			parse(ParserImplConstants.DOT);
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
		lookahead = match(lookahead, ParserImplConstants.DOT);
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
		if (match(0, ParserImplConstants.LT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.HOOK) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_LIST_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.GT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
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
		if (match(0, ParserImplConstants.DOT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
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
		if (match(0, ParserImplConstants.LT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.HOOK) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_LIST_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.GT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
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
	public BUTree<SNodeList> parseTypeArguments() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<? extends SType> type;
		parse(ParserImplConstants.LT);
		if (match(0, ParserImplConstants.AT, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.HOOK, ParserImplConstants.NODE_LIST_VARIABLE) != -1) {
			ret = parseTypeArgumentList();
		}
		parse(ParserImplConstants.GT);
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
		lookahead = match(lookahead, ParserImplConstants.LT);
		if (lookahead == -1)
			return -1;
		lookahead = matchTypeArguments_2(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.GT);
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
	public BUTree<SNodeList> parseTypeArgumentsOrDiamond() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<? extends SType> type;
		parse(ParserImplConstants.LT);
		if (match(0, ParserImplConstants.AT, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.HOOK, ParserImplConstants.NODE_LIST_VARIABLE) != -1) {
			ret = parseTypeArgumentList();
		}
		parse(ParserImplConstants.GT);
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
		lookahead = match(lookahead, ParserImplConstants.LT);
		if (lookahead == -1)
			return -1;
		lookahead = matchTypeArgumentsOrDiamond_2(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.GT);
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
			terminal(NODE_LIST_VARIABLE)
			action({ return makeVar(); })
		)
	) */
	public BUTree<SNodeList> parseTypeArgumentList() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<? extends SType> type;
		if (match(0, ParserImplConstants.AT, ParserImplConstants.BOOLEAN, ParserImplConstants.CHAR, ParserImplConstants.BYTE, ParserImplConstants.SHORT, ParserImplConstants.INT, ParserImplConstants.LONG, ParserImplConstants.FLOAT, ParserImplConstants.DOUBLE, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.HOOK) != -1) {
			type = parseTypeArgument();
			ret = append(ret, type);
			while (match(0, ParserImplConstants.COMMA) != -1) {
				parse(ParserImplConstants.COMMA);
				type = parseTypeArgument();
				ret = append(ret, type);
			}
			return ret;
		} else if (quotesMode) {
			parse(ParserImplConstants.NODE_LIST_VARIABLE);
			return makeVar();
		} else {
			throw produceParseException(ParserImplConstants.NODE_LIST_VARIABLE, ParserImplConstants.AT, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.HOOK);
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
			terminal(NODE_LIST_VARIABLE)
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
		lookahead = match(lookahead, ParserImplConstants.COMMA);
		if (lookahead == -1)
			return -1;
		lookahead = matchTypeArgument(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead({ quotesMode })
		terminal(NODE_LIST_VARIABLE)
	) */
	private int matchTypeArgumentList_2(int lookahead) {
		lookahead = quotesMode ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.NODE_LIST_VARIABLE);
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
	public BUTree<? extends SType> parseTypeArgument() throws ParseException {
		BUTree<? extends SType> ret;
		BUTree<SNodeList> annotations = null;
		run();
		annotations = parseAnnotations();
		if (match(0, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER) != -1) {
			ret = parseReferenceType(annotations);
		} else if (match(0, ParserImplConstants.HOOK) != -1) {
			ret = parseWildcard(annotations);
		} else {
			throw produceParseException(ParserImplConstants.HOOK, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT);
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
	public BUTree<SWildcardType> parseWildcard(BUTree<SNodeList> annotations) throws ParseException {
		BUTree<? extends SReferenceType> ext = null;
		BUTree<? extends SReferenceType> sup = null;
		BUTree<SNodeList> boundAnnotations = null;
		if (annotations == null) {
			run();
			annotations = emptyList();
		}
		parse(ParserImplConstants.HOOK);
		if (match(0, ParserImplConstants.EXTENDS, ParserImplConstants.SUPER) != -1) {
			if (match(0, ParserImplConstants.EXTENDS) != -1) {
				parse(ParserImplConstants.EXTENDS);
				run();
				boundAnnotations = parseAnnotations();
				ext = parseReferenceType(boundAnnotations);
			} else if (match(0, ParserImplConstants.SUPER) != -1) {
				parse(ParserImplConstants.SUPER);
				run();
				boundAnnotations = parseAnnotations();
				sup = parseReferenceType(boundAnnotations);
			} else {
				throw produceParseException(ParserImplConstants.SUPER, ParserImplConstants.EXTENDS);
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
		lookahead = match(lookahead, ParserImplConstants.HOOK);
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
		lookahead = match(lookahead, ParserImplConstants.EXTENDS);
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
		lookahead = match(lookahead, ParserImplConstants.SUPER);
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
	public BUTree<SPrimitiveType> parsePrimitiveType(BUTree<SNodeList> annotations) throws ParseException {
		Primitive primitive;
		if (annotations == null) {
			run();
			annotations = emptyList();
		}
		if (match(0, ParserImplConstants.BOOLEAN) != -1) {
			parse(ParserImplConstants.BOOLEAN);
			primitive = Primitive.Boolean;
		} else if (match(0, ParserImplConstants.CHAR) != -1) {
			parse(ParserImplConstants.CHAR);
			primitive = Primitive.Char;
		} else if (match(0, ParserImplConstants.BYTE) != -1) {
			parse(ParserImplConstants.BYTE);
			primitive = Primitive.Byte;
		} else if (match(0, ParserImplConstants.SHORT) != -1) {
			parse(ParserImplConstants.SHORT);
			primitive = Primitive.Short;
		} else if (match(0, ParserImplConstants.INT) != -1) {
			parse(ParserImplConstants.INT);
			primitive = Primitive.Int;
		} else if (match(0, ParserImplConstants.LONG) != -1) {
			parse(ParserImplConstants.LONG);
			primitive = Primitive.Long;
		} else if (match(0, ParserImplConstants.FLOAT) != -1) {
			parse(ParserImplConstants.FLOAT);
			primitive = Primitive.Float;
		} else if (match(0, ParserImplConstants.DOUBLE) != -1) {
			parse(ParserImplConstants.DOUBLE);
			primitive = Primitive.Double;
		} else {
			throw produceParseException(ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN);
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
		lookahead = match(lookahead, ParserImplConstants.BOOLEAN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(CHAR)
	) */
	private int matchPrimitiveType_2_2(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.CHAR);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(BYTE)
	) */
	private int matchPrimitiveType_2_3(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.BYTE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(SHORT)
	) */
	private int matchPrimitiveType_2_4(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.SHORT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(INT)
	) */
	private int matchPrimitiveType_2_5(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.INT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(LONG)
	) */
	private int matchPrimitiveType_2_6(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.LONG);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(FLOAT)
	) */
	private int matchPrimitiveType_2_7(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.FLOAT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(DOUBLE)
	) */
	private int matchPrimitiveType_2_8(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.DOUBLE);
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
	public BUTree<? extends SType> parseResultType() throws ParseException {
		BUTree<? extends SType> ret;
		if (match(0, ParserImplConstants.VOID) != -1) {
			run();
			parse(ParserImplConstants.VOID);
			ret = dress(SVoidType.make());
		} else if (match(0, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER) != -1) {
			ret = parseType(null);
		} else {
			throw produceParseException(ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.CHAR, ParserImplConstants.BYTE, ParserImplConstants.BOOLEAN, ParserImplConstants.DOUBLE, ParserImplConstants.LONG, ParserImplConstants.FLOAT, ParserImplConstants.SHORT, ParserImplConstants.INT, ParserImplConstants.VOID);
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
		lookahead = match(lookahead, ParserImplConstants.VOID);
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
	public BUTree<SQualifiedType> parseAnnotatedQualifiedType() throws ParseException {
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
	public BUTree<SQualifiedName> parseQualifiedName() throws ParseException {
		BUTree<SNodeOption> qualifier = none();
		BUTree<SQualifiedName> ret = null;
		BUTree<SName> name;
		run();
		name = parseName();
		ret = dress(SQualifiedName.make(qualifier, name));
		while (matchQualifiedName_lookahead1(0) != -1) {
			lateRun();
			parse(ParserImplConstants.DOT);
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
		lookahead = match(lookahead, ParserImplConstants.DOT);
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
		if (match(0, ParserImplConstants.DOT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
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
	public BUTree<SName> parseName() throws ParseException {
		Token id;
		BUTree<SName> name;
		if (match(0, ParserImplConstants.IDENTIFIER) != -1) {
			run();
			id = parse(ParserImplConstants.IDENTIFIER);
			name = dress(SName.make(id.image));
		} else if (quotesMode) {
			name = parseNodeVar();
		} else {
			throw produceParseException(ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER);
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
		lookahead = match(lookahead, ParserImplConstants.IDENTIFIER);
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
					lookAhead(2)
					action({ lateRun(); })
					nonTerminal(op, AssignmentOperator)
					nonTerminal(value, Expression)
					action({ ret = dress(SAssignExpr.make(ret, op, value)); })
				)
			)
		)
		action({ return ret; })
	) */
	public BUTree<? extends SExpr> parseExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		AssignOp op;
		BUTree<? extends SExpr> value;
		BUTree<SNodeList> params;
		if (matchExpression_lookahead1(0) != -1) {
			run();
			ret = parseName();
			parse(ParserImplConstants.ARROW);
			ret = parseLambdaBody(singletonList(makeFormalParameter((BUTree<SName>) ret)), false);
		} else if (matchExpression_lookahead2(0) != -1) {
			run();
			parse(ParserImplConstants.LPAREN);
			parse(ParserImplConstants.RPAREN);
			parse(ParserImplConstants.ARROW);
			ret = parseLambdaBody(emptyList(), true);
		} else if (matchExpression_lookahead3(0) != -1) {
			run();
			parse(ParserImplConstants.LPAREN);
			ret = parseName();
			parse(ParserImplConstants.RPAREN);
			parse(ParserImplConstants.ARROW);
			ret = parseLambdaBody(singletonList(makeFormalParameter((BUTree<SName>) ret)), true);
		} else if (matchExpression_lookahead4(0) != -1) {
			run();
			parse(ParserImplConstants.LPAREN);
			params = parseInferredFormalParameterList();
			parse(ParserImplConstants.RPAREN);
			parse(ParserImplConstants.ARROW);
			ret = parseLambdaBody(params, true);
		} else if (match(0, ParserImplConstants.MINUS, ParserImplConstants.PLUS, ParserImplConstants.LPAREN, ParserImplConstants.THIS, ParserImplConstants.SUPER, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.FALSE, ParserImplConstants.TRUE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.NULL, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.VOID, ParserImplConstants.LT, ParserImplConstants.NEW, ParserImplConstants.TILDE, ParserImplConstants.BANG, ParserImplConstants.INCR, ParserImplConstants.DECR) != -1) {
			ret = parseConditionalExpression();
			if (matchExpression_lookahead5(0) != -1) {
				lateRun();
				op = parseAssignmentOperator();
				value = parseExpression();
				ret = dress(SAssignExpr.make(ret, op, value));
			}
		} else {
			throw produceParseException(ParserImplConstants.TILDE, ParserImplConstants.BANG, ParserImplConstants.LONG_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.STRING_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.NULL, ParserImplConstants.FALSE, ParserImplConstants.TRUE, ParserImplConstants.LT, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.INT, ParserImplConstants.LONG, ParserImplConstants.FLOAT, ParserImplConstants.DOUBLE, ParserImplConstants.BOOLEAN, ParserImplConstants.CHAR, ParserImplConstants.BYTE, ParserImplConstants.SHORT, ParserImplConstants.VOID, ParserImplConstants.LPAREN, ParserImplConstants.SUPER, ParserImplConstants.THIS, ParserImplConstants.NEW, ParserImplConstants.MINUS, ParserImplConstants.PLUS, ParserImplConstants.DECR, ParserImplConstants.INCR);
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
					lookAhead(2)
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
				lookAhead(2)
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
		lookahead = match(lookahead, ParserImplConstants.ARROW);
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
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.ARROW);
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
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.ARROW);
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
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchInferredFormalParameterList(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.ARROW);
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
			lookAhead(2)
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
		lookAhead(2)
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
		lookAhead(2)
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
		lookahead = match(lookahead, ParserImplConstants.ARROW);
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
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.ARROW);
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
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.ARROW);
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
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.COMMA);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		lookAhead(2)
		nonTerminal(op, AssignmentOperator)
		nonTerminal(value, Expression)
	) */
	private int matchExpression_lookahead5(int lookahead) {
		if (match(0, ParserImplConstants.ANDASSIGN) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.STARASSIGN) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.SLASHASSIGN) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.REMASSIGN) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.PLUSASSIGN) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.LSHIFTASSIGN) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.ASSIGN) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.ORASSIGN) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.MINUSASSIGN) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.XORASSIGN) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		return -1;
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
	public BUTree<SLambdaExpr> parseLambdaBody(BUTree<SNodeList> parameters, boolean parenthesis) throws ParseException {
		BUTree<SBlockStmt> block;
		BUTree<? extends SExpr> expr;
		BUTree<SLambdaExpr> ret;
		if (match(0, ParserImplConstants.LPAREN, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.STRING_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.NULL, ParserImplConstants.TRUE, ParserImplConstants.FALSE, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.VOID, ParserImplConstants.NEW, ParserImplConstants.SUPER, ParserImplConstants.THIS, ParserImplConstants.BANG, ParserImplConstants.TILDE, ParserImplConstants.MINUS, ParserImplConstants.PLUS, ParserImplConstants.INCR, ParserImplConstants.DECR) != -1) {
			expr = parseExpression();
			ret = dress(SLambdaExpr.make(parameters, parenthesis, left(expr)));
		} else if (match(0, ParserImplConstants.LBRACE) != -1) {
			block = parseBlock();
			ret = dress(SLambdaExpr.make(parameters, parenthesis, right(block)));
		} else {
			throw produceParseException(ParserImplConstants.LBRACE, ParserImplConstants.LPAREN, ParserImplConstants.DECR, ParserImplConstants.INCR, ParserImplConstants.VOID, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.LT, ParserImplConstants.NEW, ParserImplConstants.THIS, ParserImplConstants.SUPER, ParserImplConstants.NULL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.TRUE, ParserImplConstants.FALSE, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.STRING_LITERAL, ParserImplConstants.BANG, ParserImplConstants.TILDE, ParserImplConstants.PLUS, ParserImplConstants.MINUS);
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
	public BUTree<SNodeList> parseInferredFormalParameterList() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<SFormalParameter> param;
		param = parseInferredFormalParameter();
		ret = append(ret, param);
		while (match(0, ParserImplConstants.COMMA) != -1) {
			parse(ParserImplConstants.COMMA);
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
		lookahead = match(lookahead, ParserImplConstants.COMMA);
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
	public BUTree<SFormalParameter> parseInferredFormalParameter() throws ParseException {
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
	public AssignOp parseAssignmentOperator() throws ParseException {
		AssignOp ret;
		if (match(0, ParserImplConstants.ASSIGN) != -1) {
			parse(ParserImplConstants.ASSIGN);
			ret = AssignOp.Normal;
		} else if (match(0, ParserImplConstants.STARASSIGN) != -1) {
			parse(ParserImplConstants.STARASSIGN);
			ret = AssignOp.Times;
		} else if (match(0, ParserImplConstants.SLASHASSIGN) != -1) {
			parse(ParserImplConstants.SLASHASSIGN);
			ret = AssignOp.Divide;
		} else if (match(0, ParserImplConstants.REMASSIGN) != -1) {
			parse(ParserImplConstants.REMASSIGN);
			ret = AssignOp.Remainder;
		} else if (match(0, ParserImplConstants.PLUSASSIGN) != -1) {
			parse(ParserImplConstants.PLUSASSIGN);
			ret = AssignOp.Plus;
		} else if (match(0, ParserImplConstants.MINUSASSIGN) != -1) {
			parse(ParserImplConstants.MINUSASSIGN);
			ret = AssignOp.Minus;
		} else if (match(0, ParserImplConstants.LSHIFTASSIGN) != -1) {
			parse(ParserImplConstants.LSHIFTASSIGN);
			ret = AssignOp.LeftShift;
		} else if (match(0, ParserImplConstants.RSIGNEDSHIFTASSIGN) != -1) {
			parse(ParserImplConstants.RSIGNEDSHIFTASSIGN);
			ret = AssignOp.RightSignedShift;
		} else if (match(0, ParserImplConstants.RUNSIGNEDSHIFTASSIGN) != -1) {
			parse(ParserImplConstants.RUNSIGNEDSHIFTASSIGN);
			ret = AssignOp.RightUnsignedShift;
		} else if (match(0, ParserImplConstants.ANDASSIGN) != -1) {
			parse(ParserImplConstants.ANDASSIGN);
			ret = AssignOp.And;
		} else if (match(0, ParserImplConstants.XORASSIGN) != -1) {
			parse(ParserImplConstants.XORASSIGN);
			ret = AssignOp.XOr;
		} else if (match(0, ParserImplConstants.ORASSIGN) != -1) {
			parse(ParserImplConstants.ORASSIGN);
			ret = AssignOp.Or;
		} else {
			throw produceParseException(ParserImplConstants.ORASSIGN, ParserImplConstants.XORASSIGN, ParserImplConstants.ANDASSIGN, ParserImplConstants.RUNSIGNEDSHIFTASSIGN, ParserImplConstants.RSIGNEDSHIFTASSIGN, ParserImplConstants.LSHIFTASSIGN, ParserImplConstants.MINUSASSIGN, ParserImplConstants.PLUSASSIGN, ParserImplConstants.REMASSIGN, ParserImplConstants.SLASHASSIGN, ParserImplConstants.STARASSIGN, ParserImplConstants.ASSIGN);
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
		lookahead = match(lookahead, ParserImplConstants.ASSIGN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(ANDASSIGN)
	) */
	private int matchAssignmentOperator_1_10(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.ANDASSIGN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(XORASSIGN)
	) */
	private int matchAssignmentOperator_1_11(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.XORASSIGN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(ORASSIGN)
	) */
	private int matchAssignmentOperator_1_12(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.ORASSIGN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(STARASSIGN)
	) */
	private int matchAssignmentOperator_1_2(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.STARASSIGN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(SLASHASSIGN)
	) */
	private int matchAssignmentOperator_1_3(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.SLASHASSIGN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(REMASSIGN)
	) */
	private int matchAssignmentOperator_1_4(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.REMASSIGN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(PLUSASSIGN)
	) */
	private int matchAssignmentOperator_1_5(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.PLUSASSIGN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(MINUSASSIGN)
	) */
	private int matchAssignmentOperator_1_6(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.MINUSASSIGN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(LSHIFTASSIGN)
	) */
	private int matchAssignmentOperator_1_7(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.LSHIFTASSIGN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(RSIGNEDSHIFTASSIGN)
	) */
	private int matchAssignmentOperator_1_8(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.RSIGNEDSHIFTASSIGN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(RUNSIGNEDSHIFTASSIGN)
	) */
	private int matchAssignmentOperator_1_9(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.RUNSIGNEDSHIFTASSIGN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(ret, ConditionalOrExpression)
		zeroOrOne(
			lookAhead(2)
			action({ lateRun(); })
			terminal(HOOK)
			nonTerminal(left, Expression)
			terminal(COLON)
			nonTerminal(right, ConditionalExpression)
			action({ ret = dress(SConditionalExpr.make(ret, left, right)); })
		)
		action({ return ret; })
	) */
	public BUTree<? extends SExpr> parseConditionalExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> left;
		BUTree<? extends SExpr> right;
		ret = parseConditionalOrExpression();
		if (matchConditionalExpression_lookahead1(0) != -1) {
			lateRun();
			parse(ParserImplConstants.HOOK);
			left = parseExpression();
			parse(ParserImplConstants.COLON);
			right = parseConditionalExpression();
			ret = dress(SConditionalExpr.make(ret, left, right));
		}
		return ret;
	}

	/* sequence(
		nonTerminal(ret, ConditionalOrExpression)
		zeroOrOne(
			lookAhead(2)
			terminal(HOOK)
			nonTerminal(left, Expression)
			terminal(COLON)
			nonTerminal(right, ConditionalExpression)
		)
	) */
	private int matchConditionalExpression(int lookahead) {
		lookahead = matchConditionalOrExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchConditionalExpression_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		lookAhead(2)
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
		lookAhead(2)
		terminal(HOOK)
		nonTerminal(left, Expression)
		terminal(COLON)
		nonTerminal(right, ConditionalExpression)
	) */
	private int matchConditionalExpression_2_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.HOOK);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.COLON);
		if (lookahead == -1)
			return -1;
		lookahead = matchConditionalExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		lookAhead(2)
		terminal(HOOK)
		nonTerminal(left, Expression)
		terminal(COLON)
		nonTerminal(right, ConditionalExpression)
	) */
	private int matchConditionalExpression_lookahead1(int lookahead) {
		if (match(0, ParserImplConstants.HOOK) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		return -1;
	}

	/* sequence(
		nonTerminal(ret, ConditionalAndExpression)
		zeroOrMore(
			lookAhead(2)
			action({ lateRun(); })
			terminal(SC_OR)
			nonTerminal(right, ConditionalAndExpression)
			action({ ret = dress(SBinaryExpr.make(ret, BinaryOp.Or, right)); })
		)
		action({ return ret; })
	) */
	public BUTree<? extends SExpr> parseConditionalOrExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		ret = parseConditionalAndExpression();
		while (matchConditionalOrExpression_lookahead1(0) != -1) {
			lateRun();
			parse(ParserImplConstants.SC_OR);
			right = parseConditionalAndExpression();
			ret = dress(SBinaryExpr.make(ret, BinaryOp.Or, right));
		}
		return ret;
	}

	/* sequence(
		nonTerminal(ret, ConditionalAndExpression)
		zeroOrMore(
			lookAhead(2)
			terminal(SC_OR)
			nonTerminal(right, ConditionalAndExpression)
		)
	) */
	private int matchConditionalOrExpression(int lookahead) {
		lookahead = matchConditionalAndExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchConditionalOrExpression_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		lookAhead(2)
		terminal(SC_OR)
		nonTerminal(right, ConditionalAndExpression)
	) */
	private int matchConditionalOrExpression_2(int lookahead) {
		int newLookahead;
		newLookahead = matchConditionalOrExpression_2_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchConditionalOrExpression_2_1(lookahead);
		}
		return lookahead;
	}

	/* sequence(
		lookAhead(2)
		terminal(SC_OR)
		nonTerminal(right, ConditionalAndExpression)
	) */
	private int matchConditionalOrExpression_2_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.SC_OR);
		if (lookahead == -1)
			return -1;
		lookahead = matchConditionalAndExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		lookAhead(2)
		terminal(SC_OR)
		nonTerminal(right, ConditionalAndExpression)
	) */
	private int matchConditionalOrExpression_lookahead1(int lookahead) {
		if (match(0, ParserImplConstants.SC_OR) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		return -1;
	}

	/* sequence(
		nonTerminal(ret, InclusiveOrExpression)
		zeroOrMore(
			lookAhead(2)
			action({ lateRun(); })
			terminal(SC_AND)
			nonTerminal(right, InclusiveOrExpression)
			action({ ret = dress(SBinaryExpr.make(ret, BinaryOp.And, right)); })
		)
		action({ return ret; })
	) */
	public BUTree<? extends SExpr> parseConditionalAndExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		ret = parseInclusiveOrExpression();
		while (matchConditionalAndExpression_lookahead1(0) != -1) {
			lateRun();
			parse(ParserImplConstants.SC_AND);
			right = parseInclusiveOrExpression();
			ret = dress(SBinaryExpr.make(ret, BinaryOp.And, right));
		}
		return ret;
	}

	/* sequence(
		nonTerminal(ret, InclusiveOrExpression)
		zeroOrMore(
			lookAhead(2)
			terminal(SC_AND)
			nonTerminal(right, InclusiveOrExpression)
		)
	) */
	private int matchConditionalAndExpression(int lookahead) {
		lookahead = matchInclusiveOrExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchConditionalAndExpression_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		lookAhead(2)
		terminal(SC_AND)
		nonTerminal(right, InclusiveOrExpression)
	) */
	private int matchConditionalAndExpression_2(int lookahead) {
		int newLookahead;
		newLookahead = matchConditionalAndExpression_2_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchConditionalAndExpression_2_1(lookahead);
		}
		return lookahead;
	}

	/* sequence(
		lookAhead(2)
		terminal(SC_AND)
		nonTerminal(right, InclusiveOrExpression)
	) */
	private int matchConditionalAndExpression_2_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.SC_AND);
		if (lookahead == -1)
			return -1;
		lookahead = matchInclusiveOrExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		lookAhead(2)
		terminal(SC_AND)
		nonTerminal(right, InclusiveOrExpression)
	) */
	private int matchConditionalAndExpression_lookahead1(int lookahead) {
		if (match(0, ParserImplConstants.SC_AND) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		return -1;
	}

	/* sequence(
		nonTerminal(ret, ExclusiveOrExpression)
		zeroOrMore(
			lookAhead(2)
			action({ lateRun(); })
			terminal(BIT_OR)
			nonTerminal(right, ExclusiveOrExpression)
			action({ ret = dress(SBinaryExpr.make(ret, BinaryOp.BinOr, right)); })
		)
		action({ return ret; })
	) */
	public BUTree<? extends SExpr> parseInclusiveOrExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		ret = parseExclusiveOrExpression();
		while (matchInclusiveOrExpression_lookahead1(0) != -1) {
			lateRun();
			parse(ParserImplConstants.BIT_OR);
			right = parseExclusiveOrExpression();
			ret = dress(SBinaryExpr.make(ret, BinaryOp.BinOr, right));
		}
		return ret;
	}

	/* sequence(
		nonTerminal(ret, ExclusiveOrExpression)
		zeroOrMore(
			lookAhead(2)
			terminal(BIT_OR)
			nonTerminal(right, ExclusiveOrExpression)
		)
	) */
	private int matchInclusiveOrExpression(int lookahead) {
		lookahead = matchExclusiveOrExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchInclusiveOrExpression_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		lookAhead(2)
		terminal(BIT_OR)
		nonTerminal(right, ExclusiveOrExpression)
	) */
	private int matchInclusiveOrExpression_2(int lookahead) {
		int newLookahead;
		newLookahead = matchInclusiveOrExpression_2_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchInclusiveOrExpression_2_1(lookahead);
		}
		return lookahead;
	}

	/* sequence(
		lookAhead(2)
		terminal(BIT_OR)
		nonTerminal(right, ExclusiveOrExpression)
	) */
	private int matchInclusiveOrExpression_2_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.BIT_OR);
		if (lookahead == -1)
			return -1;
		lookahead = matchExclusiveOrExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		lookAhead(2)
		terminal(BIT_OR)
		nonTerminal(right, ExclusiveOrExpression)
	) */
	private int matchInclusiveOrExpression_lookahead1(int lookahead) {
		if (match(0, ParserImplConstants.BIT_OR) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		return -1;
	}

	/* sequence(
		nonTerminal(ret, AndExpression)
		zeroOrMore(
			lookAhead(2)
			action({ lateRun(); })
			terminal(XOR)
			nonTerminal(right, AndExpression)
			action({ ret = dress(SBinaryExpr.make(ret, BinaryOp.XOr, right)); })
		)
		action({ return ret; })
	) */
	public BUTree<? extends SExpr> parseExclusiveOrExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		ret = parseAndExpression();
		while (matchExclusiveOrExpression_lookahead1(0) != -1) {
			lateRun();
			parse(ParserImplConstants.XOR);
			right = parseAndExpression();
			ret = dress(SBinaryExpr.make(ret, BinaryOp.XOr, right));
		}
		return ret;
	}

	/* sequence(
		nonTerminal(ret, AndExpression)
		zeroOrMore(
			lookAhead(2)
			terminal(XOR)
			nonTerminal(right, AndExpression)
		)
	) */
	private int matchExclusiveOrExpression(int lookahead) {
		lookahead = matchAndExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchExclusiveOrExpression_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		lookAhead(2)
		terminal(XOR)
		nonTerminal(right, AndExpression)
	) */
	private int matchExclusiveOrExpression_2(int lookahead) {
		int newLookahead;
		newLookahead = matchExclusiveOrExpression_2_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchExclusiveOrExpression_2_1(lookahead);
		}
		return lookahead;
	}

	/* sequence(
		lookAhead(2)
		terminal(XOR)
		nonTerminal(right, AndExpression)
	) */
	private int matchExclusiveOrExpression_2_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.XOR);
		if (lookahead == -1)
			return -1;
		lookahead = matchAndExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		lookAhead(2)
		terminal(XOR)
		nonTerminal(right, AndExpression)
	) */
	private int matchExclusiveOrExpression_lookahead1(int lookahead) {
		if (match(0, ParserImplConstants.XOR) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		return -1;
	}

	/* sequence(
		nonTerminal(ret, EqualityExpression)
		zeroOrMore(
			lookAhead(2)
			action({ lateRun(); })
			terminal(BIT_AND)
			nonTerminal(right, EqualityExpression)
			action({ ret = dress(SBinaryExpr.make(ret, BinaryOp.BinAnd, right)); })
		)
		action({ return ret; })
	) */
	public BUTree<? extends SExpr> parseAndExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		ret = parseEqualityExpression();
		while (matchAndExpression_lookahead1(0) != -1) {
			lateRun();
			parse(ParserImplConstants.BIT_AND);
			right = parseEqualityExpression();
			ret = dress(SBinaryExpr.make(ret, BinaryOp.BinAnd, right));
		}
		return ret;
	}

	/* sequence(
		nonTerminal(ret, EqualityExpression)
		zeroOrMore(
			lookAhead(2)
			terminal(BIT_AND)
			nonTerminal(right, EqualityExpression)
		)
	) */
	private int matchAndExpression(int lookahead) {
		lookahead = matchEqualityExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchAndExpression_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		lookAhead(2)
		terminal(BIT_AND)
		nonTerminal(right, EqualityExpression)
	) */
	private int matchAndExpression_2(int lookahead) {
		int newLookahead;
		newLookahead = matchAndExpression_2_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchAndExpression_2_1(lookahead);
		}
		return lookahead;
	}

	/* sequence(
		lookAhead(2)
		terminal(BIT_AND)
		nonTerminal(right, EqualityExpression)
	) */
	private int matchAndExpression_2_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.BIT_AND);
		if (lookahead == -1)
			return -1;
		lookahead = matchEqualityExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		lookAhead(2)
		terminal(BIT_AND)
		nonTerminal(right, EqualityExpression)
	) */
	private int matchAndExpression_lookahead1(int lookahead) {
		if (match(0, ParserImplConstants.BIT_AND) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		return -1;
	}

	/* sequence(
		nonTerminal(ret, InstanceOfExpression)
		zeroOrMore(
			lookAhead(2)
			action({ lateRun(); })
			choice(
				sequence(
					terminal(EQ)
					action({ op = BinaryOp.Equal; })
				)
				sequence(
					terminal(NE)
					action({ op = BinaryOp.NotEqual; })
				)
			)
			nonTerminal(right, InstanceOfExpression)
			action({ ret = dress(SBinaryExpr.make(ret, op, right)); })
		)
		action({ return ret; })
	) */
	public BUTree<? extends SExpr> parseEqualityExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		BinaryOp op;
		ret = parseInstanceOfExpression();
		while (matchEqualityExpression_lookahead1(0) != -1) {
			lateRun();
			if (match(0, ParserImplConstants.EQ) != -1) {
				parse(ParserImplConstants.EQ);
				op = BinaryOp.Equal;
			} else if (match(0, ParserImplConstants.NE) != -1) {
				parse(ParserImplConstants.NE);
				op = BinaryOp.NotEqual;
			} else {
				throw produceParseException(ParserImplConstants.NE, ParserImplConstants.EQ);
			}
			right = parseInstanceOfExpression();
			ret = dress(SBinaryExpr.make(ret, op, right));
		}
		return ret;
	}

	/* sequence(
		nonTerminal(ret, InstanceOfExpression)
		zeroOrMore(
			lookAhead(2)
			choice(
				sequence(
					terminal(EQ)
				)
				sequence(
					terminal(NE)
				)
			)
			nonTerminal(right, InstanceOfExpression)
		)
	) */
	private int matchEqualityExpression(int lookahead) {
		lookahead = matchInstanceOfExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchEqualityExpression_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		lookAhead(2)
		choice(
			sequence(
				terminal(EQ)
			)
			sequence(
				terminal(NE)
			)
		)
		nonTerminal(right, InstanceOfExpression)
	) */
	private int matchEqualityExpression_2(int lookahead) {
		int newLookahead;
		newLookahead = matchEqualityExpression_2_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchEqualityExpression_2_1(lookahead);
		}
		return lookahead;
	}

	/* sequence(
		lookAhead(2)
		choice(
			sequence(
				terminal(EQ)
			)
			sequence(
				terminal(NE)
			)
		)
		nonTerminal(right, InstanceOfExpression)
	) */
	private int matchEqualityExpression_2_1(int lookahead) {
		lookahead = matchEqualityExpression_2_1_3(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchInstanceOfExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			terminal(EQ)
		)
		sequence(
			terminal(NE)
		)
	) */
	private int matchEqualityExpression_2_1_3(int lookahead) {
		int newLookahead;
		newLookahead = matchEqualityExpression_2_1_3_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchEqualityExpression_2_1_3_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		terminal(EQ)
	) */
	private int matchEqualityExpression_2_1_3_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.EQ);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(NE)
	) */
	private int matchEqualityExpression_2_1_3_2(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.NE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		lookAhead(2)
		choice(
			sequence(
				terminal(EQ)
			)
			sequence(
				terminal(NE)
			)
		)
		nonTerminal(right, InstanceOfExpression)
	) */
	private int matchEqualityExpression_lookahead1(int lookahead) {
		if (match(0, ParserImplConstants.NE) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.EQ) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		return -1;
	}

	/* sequence(
		nonTerminal(ret, RelationalExpression)
		zeroOrOne(
			lookAhead(2)
			action({ lateRun(); })
			terminal(INSTANCEOF)
			action({ run(); })
			nonTerminal(annotations, Annotations)
			nonTerminal(type, Type)
			action({ ret = dress(SInstanceOfExpr.make(ret, type)); })
		)
		action({ return ret; })
	) */
	public BUTree<? extends SExpr> parseInstanceOfExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<SNodeList> annotations;
		BUTree<? extends SType> type;
		ret = parseRelationalExpression();
		if (matchInstanceOfExpression_lookahead1(0) != -1) {
			lateRun();
			parse(ParserImplConstants.INSTANCEOF);
			run();
			annotations = parseAnnotations();
			type = parseType(annotations);
			ret = dress(SInstanceOfExpr.make(ret, type));
		}
		return ret;
	}

	/* sequence(
		nonTerminal(ret, RelationalExpression)
		zeroOrOne(
			lookAhead(2)
			terminal(INSTANCEOF)
			nonTerminal(annotations, Annotations)
			nonTerminal(type, Type)
		)
	) */
	private int matchInstanceOfExpression(int lookahead) {
		lookahead = matchRelationalExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchInstanceOfExpression_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		lookAhead(2)
		terminal(INSTANCEOF)
		nonTerminal(annotations, Annotations)
		nonTerminal(type, Type)
	) */
	private int matchInstanceOfExpression_2(int lookahead) {
		int newLookahead;
		newLookahead = matchInstanceOfExpression_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return lookahead;
	}

	/* sequence(
		lookAhead(2)
		terminal(INSTANCEOF)
		nonTerminal(annotations, Annotations)
		nonTerminal(type, Type)
	) */
	private int matchInstanceOfExpression_2_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.INSTANCEOF);
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

	/* zeroOrOne(
		lookAhead(2)
		terminal(INSTANCEOF)
		nonTerminal(annotations, Annotations)
		nonTerminal(type, Type)
	) */
	private int matchInstanceOfExpression_lookahead1(int lookahead) {
		if (match(0, ParserImplConstants.INSTANCEOF) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
		}
		return -1;
	}

	/* sequence(
		nonTerminal(ret, ShiftExpression)
		zeroOrMore(
			lookAhead(2)
			action({ lateRun(); })
			choice(
				sequence(
					terminal(LT)
					action({ op = BinaryOp.Less; })
				)
				sequence(
					terminal(GT)
					action({ op = BinaryOp.Greater; })
				)
				sequence(
					terminal(LE)
					action({ op = BinaryOp.LessOrEqual; })
				)
				sequence(
					terminal(GE)
					action({ op = BinaryOp.GreaterOrEqual; })
				)
			)
			nonTerminal(right, ShiftExpression)
			action({ ret = dress(SBinaryExpr.make(ret, op, right)); })
		)
		action({ return ret; })
	) */
	public BUTree<? extends SExpr> parseRelationalExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		BinaryOp op;
		ret = parseShiftExpression();
		while (matchRelationalExpression_lookahead1(0) != -1) {
			lateRun();
			if (match(0, ParserImplConstants.LT) != -1) {
				parse(ParserImplConstants.LT);
				op = BinaryOp.Less;
			} else if (match(0, ParserImplConstants.GT) != -1) {
				parse(ParserImplConstants.GT);
				op = BinaryOp.Greater;
			} else if (match(0, ParserImplConstants.LE) != -1) {
				parse(ParserImplConstants.LE);
				op = BinaryOp.LessOrEqual;
			} else if (match(0, ParserImplConstants.GE) != -1) {
				parse(ParserImplConstants.GE);
				op = BinaryOp.GreaterOrEqual;
			} else {
				throw produceParseException(ParserImplConstants.GE, ParserImplConstants.LE, ParserImplConstants.GT, ParserImplConstants.LT);
			}
			right = parseShiftExpression();
			ret = dress(SBinaryExpr.make(ret, op, right));
		}
		return ret;
	}

	/* sequence(
		nonTerminal(ret, ShiftExpression)
		zeroOrMore(
			lookAhead(2)
			choice(
				sequence(
					terminal(LT)
				)
				sequence(
					terminal(GT)
				)
				sequence(
					terminal(LE)
				)
				sequence(
					terminal(GE)
				)
			)
			nonTerminal(right, ShiftExpression)
		)
	) */
	private int matchRelationalExpression(int lookahead) {
		lookahead = matchShiftExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchRelationalExpression_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		lookAhead(2)
		choice(
			sequence(
				terminal(LT)
			)
			sequence(
				terminal(GT)
			)
			sequence(
				terminal(LE)
			)
			sequence(
				terminal(GE)
			)
		)
		nonTerminal(right, ShiftExpression)
	) */
	private int matchRelationalExpression_2(int lookahead) {
		int newLookahead;
		newLookahead = matchRelationalExpression_2_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchRelationalExpression_2_1(lookahead);
		}
		return lookahead;
	}

	/* sequence(
		lookAhead(2)
		choice(
			sequence(
				terminal(LT)
			)
			sequence(
				terminal(GT)
			)
			sequence(
				terminal(LE)
			)
			sequence(
				terminal(GE)
			)
		)
		nonTerminal(right, ShiftExpression)
	) */
	private int matchRelationalExpression_2_1(int lookahead) {
		lookahead = matchRelationalExpression_2_1_3(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchShiftExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			terminal(LT)
		)
		sequence(
			terminal(GT)
		)
		sequence(
			terminal(LE)
		)
		sequence(
			terminal(GE)
		)
	) */
	private int matchRelationalExpression_2_1_3(int lookahead) {
		int newLookahead;
		newLookahead = matchRelationalExpression_2_1_3_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchRelationalExpression_2_1_3_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchRelationalExpression_2_1_3_3(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchRelationalExpression_2_1_3_4(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		terminal(LT)
	) */
	private int matchRelationalExpression_2_1_3_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.LT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(GT)
	) */
	private int matchRelationalExpression_2_1_3_2(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.GT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(LE)
	) */
	private int matchRelationalExpression_2_1_3_3(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.LE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(GE)
	) */
	private int matchRelationalExpression_2_1_3_4(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.GE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		lookAhead(2)
		choice(
			sequence(
				terminal(LT)
			)
			sequence(
				terminal(GT)
			)
			sequence(
				terminal(LE)
			)
			sequence(
				terminal(GE)
			)
		)
		nonTerminal(right, ShiftExpression)
	) */
	private int matchRelationalExpression_lookahead1(int lookahead) {
		if (match(0, ParserImplConstants.LT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.LE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.GT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.GE) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		return -1;
	}

	/* sequence(
		nonTerminal(ret, AdditiveExpression)
		zeroOrMore(
			lookAhead(2)
			action({ lateRun(); })
			choice(
				sequence(
					terminal(LSHIFT)
					action({ op = BinaryOp.LeftShift; })
				)
				sequence(
					lookAhead(3)
					nonTerminal(RUNSIGNEDSHIFT)
					action({ op = BinaryOp.RightUnsignedShift; })
				)
				sequence(
					lookAhead(2)
					nonTerminal(RSIGNEDSHIFT)
					action({ op = BinaryOp.RightSignedShift; })
				)
			)
			nonTerminal(right, AdditiveExpression)
			action({ ret = dress(SBinaryExpr.make(ret, op, right)); })
		)
		action({ return ret; })
	) */
	public BUTree<? extends SExpr> parseShiftExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		BinaryOp op;
		ret = parseAdditiveExpression();
		while (matchShiftExpression_lookahead1(0) != -1) {
			lateRun();
			if (match(0, ParserImplConstants.LSHIFT) != -1) {
				parse(ParserImplConstants.LSHIFT);
				op = BinaryOp.LeftShift;
			} else if (matchShiftExpression_lookahead2(0) != -1) {
				parseRUNSIGNEDSHIFT();
				op = BinaryOp.RightUnsignedShift;
			} else if (matchShiftExpression_lookahead3(0) != -1) {
				parseRSIGNEDSHIFT();
				op = BinaryOp.RightSignedShift;
			} else {
				throw produceParseException(ParserImplConstants.GT, ParserImplConstants.LSHIFT);
			}
			right = parseAdditiveExpression();
			ret = dress(SBinaryExpr.make(ret, op, right));
		}
		return ret;
	}

	/* sequence(
		nonTerminal(ret, AdditiveExpression)
		zeroOrMore(
			lookAhead(2)
			choice(
				sequence(
					terminal(LSHIFT)
				)
				sequence(
					lookAhead(3)
					nonTerminal(RUNSIGNEDSHIFT)
				)
				sequence(
					lookAhead(2)
					nonTerminal(RSIGNEDSHIFT)
				)
			)
			nonTerminal(right, AdditiveExpression)
		)
	) */
	private int matchShiftExpression(int lookahead) {
		lookahead = matchAdditiveExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchShiftExpression_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		lookAhead(2)
		choice(
			sequence(
				terminal(LSHIFT)
			)
			sequence(
				lookAhead(3)
				nonTerminal(RUNSIGNEDSHIFT)
			)
			sequence(
				lookAhead(2)
				nonTerminal(RSIGNEDSHIFT)
			)
		)
		nonTerminal(right, AdditiveExpression)
	) */
	private int matchShiftExpression_2(int lookahead) {
		int newLookahead;
		newLookahead = matchShiftExpression_2_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchShiftExpression_2_1(lookahead);
		}
		return lookahead;
	}

	/* sequence(
		lookAhead(2)
		choice(
			sequence(
				terminal(LSHIFT)
			)
			sequence(
				lookAhead(3)
				nonTerminal(RUNSIGNEDSHIFT)
			)
			sequence(
				lookAhead(2)
				nonTerminal(RSIGNEDSHIFT)
			)
		)
		nonTerminal(right, AdditiveExpression)
	) */
	private int matchShiftExpression_2_1(int lookahead) {
		lookahead = matchShiftExpression_2_1_3(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchAdditiveExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			terminal(LSHIFT)
		)
		sequence(
			lookAhead(3)
			nonTerminal(RUNSIGNEDSHIFT)
		)
		sequence(
			lookAhead(2)
			nonTerminal(RSIGNEDSHIFT)
		)
	) */
	private int matchShiftExpression_2_1_3(int lookahead) {
		int newLookahead;
		newLookahead = matchShiftExpression_2_1_3_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchShiftExpression_2_1_3_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchShiftExpression_2_1_3_3(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		terminal(LSHIFT)
	) */
	private int matchShiftExpression_2_1_3_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.LSHIFT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead(3)
		nonTerminal(RUNSIGNEDSHIFT)
	) */
	private int matchShiftExpression_2_1_3_2(int lookahead) {
		lookahead = matchRUNSIGNEDSHIFT(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead(2)
		nonTerminal(RSIGNEDSHIFT)
	) */
	private int matchShiftExpression_2_1_3_3(int lookahead) {
		lookahead = matchRSIGNEDSHIFT(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		lookAhead(2)
		choice(
			sequence(
				terminal(LSHIFT)
			)
			sequence(
				lookAhead(3)
				nonTerminal(RUNSIGNEDSHIFT)
			)
			sequence(
				lookAhead(2)
				nonTerminal(RSIGNEDSHIFT)
			)
		)
		nonTerminal(right, AdditiveExpression)
	) */
	private int matchShiftExpression_lookahead1(int lookahead) {
		if (match(0, ParserImplConstants.LSHIFT) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.GT) != -1) {
			if (match(1, ParserImplConstants.GT) != -1) {
				return lookahead;
			}
		}
		return -1;
	}

	/* sequence(
		lookAhead(3)
		nonTerminal(RUNSIGNEDSHIFT)
	) */
	private int matchShiftExpression_lookahead2(int lookahead) {
		if (match(0, ParserImplConstants.GT) != -1) {
			if (match(1, ParserImplConstants.GT) != -1) {
				if (match(2, ParserImplConstants.GT) != -1) {
					return lookahead;
				}
			}
		}
		return -1;
	}

	/* sequence(
		lookAhead(2)
		nonTerminal(RSIGNEDSHIFT)
	) */
	private int matchShiftExpression_lookahead3(int lookahead) {
		if (match(0, ParserImplConstants.GT) != -1) {
			if (match(1, ParserImplConstants.GT) != -1) {
				return lookahead;
			}
		}
		return -1;
	}

	/* sequence(
		nonTerminal(ret, MultiplicativeExpression)
		zeroOrMore(
			lookAhead(2)
			action({ lateRun(); })
			choice(
				sequence(
					terminal(PLUS)
					action({ op = BinaryOp.Plus; })
				)
				sequence(
					terminal(MINUS)
					action({ op = BinaryOp.Minus; })
				)
			)
			nonTerminal(right, MultiplicativeExpression)
			action({ ret = dress(SBinaryExpr.make(ret, op, right)); })
		)
		action({ return ret; })
	) */
	public BUTree<? extends SExpr> parseAdditiveExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		BinaryOp op;
		ret = parseMultiplicativeExpression();
		while (matchAdditiveExpression_lookahead1(0) != -1) {
			lateRun();
			if (match(0, ParserImplConstants.PLUS) != -1) {
				parse(ParserImplConstants.PLUS);
				op = BinaryOp.Plus;
			} else if (match(0, ParserImplConstants.MINUS) != -1) {
				parse(ParserImplConstants.MINUS);
				op = BinaryOp.Minus;
			} else {
				throw produceParseException(ParserImplConstants.MINUS, ParserImplConstants.PLUS);
			}
			right = parseMultiplicativeExpression();
			ret = dress(SBinaryExpr.make(ret, op, right));
		}
		return ret;
	}

	/* sequence(
		nonTerminal(ret, MultiplicativeExpression)
		zeroOrMore(
			lookAhead(2)
			choice(
				sequence(
					terminal(PLUS)
				)
				sequence(
					terminal(MINUS)
				)
			)
			nonTerminal(right, MultiplicativeExpression)
		)
	) */
	private int matchAdditiveExpression(int lookahead) {
		lookahead = matchMultiplicativeExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchAdditiveExpression_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		lookAhead(2)
		choice(
			sequence(
				terminal(PLUS)
			)
			sequence(
				terminal(MINUS)
			)
		)
		nonTerminal(right, MultiplicativeExpression)
	) */
	private int matchAdditiveExpression_2(int lookahead) {
		int newLookahead;
		newLookahead = matchAdditiveExpression_2_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchAdditiveExpression_2_1(lookahead);
		}
		return lookahead;
	}

	/* sequence(
		lookAhead(2)
		choice(
			sequence(
				terminal(PLUS)
			)
			sequence(
				terminal(MINUS)
			)
		)
		nonTerminal(right, MultiplicativeExpression)
	) */
	private int matchAdditiveExpression_2_1(int lookahead) {
		lookahead = matchAdditiveExpression_2_1_3(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchMultiplicativeExpression(lookahead);
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
	private int matchAdditiveExpression_2_1_3(int lookahead) {
		int newLookahead;
		newLookahead = matchAdditiveExpression_2_1_3_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchAdditiveExpression_2_1_3_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		terminal(PLUS)
	) */
	private int matchAdditiveExpression_2_1_3_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.PLUS);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(MINUS)
	) */
	private int matchAdditiveExpression_2_1_3_2(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.MINUS);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		lookAhead(2)
		choice(
			sequence(
				terminal(PLUS)
			)
			sequence(
				terminal(MINUS)
			)
		)
		nonTerminal(right, MultiplicativeExpression)
	) */
	private int matchAdditiveExpression_lookahead1(int lookahead) {
		if (match(0, ParserImplConstants.PLUS) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.MINUS) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		return -1;
	}

	/* sequence(
		nonTerminal(ret, UnaryExpression)
		zeroOrMore(
			lookAhead(2)
			action({ lateRun(); })
			choice(
				sequence(
					terminal(STAR)
					action({ op = BinaryOp.Times; })
				)
				sequence(
					terminal(SLASH)
					action({ op = BinaryOp.Divide; })
				)
				sequence(
					terminal(REM)
					action({ op = BinaryOp.Remainder; })
				)
			)
			nonTerminal(right, UnaryExpression)
			action({ ret = dress(SBinaryExpr.make(ret, op, right)); })
		)
		action({ return ret; })
	) */
	public BUTree<? extends SExpr> parseMultiplicativeExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SExpr> right;
		BinaryOp op;
		ret = parseUnaryExpression();
		while (matchMultiplicativeExpression_lookahead1(0) != -1) {
			lateRun();
			if (match(0, ParserImplConstants.STAR) != -1) {
				parse(ParserImplConstants.STAR);
				op = BinaryOp.Times;
			} else if (match(0, ParserImplConstants.SLASH) != -1) {
				parse(ParserImplConstants.SLASH);
				op = BinaryOp.Divide;
			} else if (match(0, ParserImplConstants.REM) != -1) {
				parse(ParserImplConstants.REM);
				op = BinaryOp.Remainder;
			} else {
				throw produceParseException(ParserImplConstants.REM, ParserImplConstants.SLASH, ParserImplConstants.STAR);
			}
			right = parseUnaryExpression();
			ret = dress(SBinaryExpr.make(ret, op, right));
		}
		return ret;
	}

	/* sequence(
		nonTerminal(ret, UnaryExpression)
		zeroOrMore(
			lookAhead(2)
			choice(
				sequence(
					terminal(STAR)
				)
				sequence(
					terminal(SLASH)
				)
				sequence(
					terminal(REM)
				)
			)
			nonTerminal(right, UnaryExpression)
		)
	) */
	private int matchMultiplicativeExpression(int lookahead) {
		lookahead = matchUnaryExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchMultiplicativeExpression_2(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		lookAhead(2)
		choice(
			sequence(
				terminal(STAR)
			)
			sequence(
				terminal(SLASH)
			)
			sequence(
				terminal(REM)
			)
		)
		nonTerminal(right, UnaryExpression)
	) */
	private int matchMultiplicativeExpression_2(int lookahead) {
		int newLookahead;
		newLookahead = matchMultiplicativeExpression_2_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchMultiplicativeExpression_2_1(lookahead);
		}
		return lookahead;
	}

	/* sequence(
		lookAhead(2)
		choice(
			sequence(
				terminal(STAR)
			)
			sequence(
				terminal(SLASH)
			)
			sequence(
				terminal(REM)
			)
		)
		nonTerminal(right, UnaryExpression)
	) */
	private int matchMultiplicativeExpression_2_1(int lookahead) {
		lookahead = matchMultiplicativeExpression_2_1_3(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchUnaryExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			terminal(STAR)
		)
		sequence(
			terminal(SLASH)
		)
		sequence(
			terminal(REM)
		)
	) */
	private int matchMultiplicativeExpression_2_1_3(int lookahead) {
		int newLookahead;
		newLookahead = matchMultiplicativeExpression_2_1_3_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchMultiplicativeExpression_2_1_3_2(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = matchMultiplicativeExpression_2_1_3_3(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		terminal(STAR)
	) */
	private int matchMultiplicativeExpression_2_1_3_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.STAR);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(SLASH)
	) */
	private int matchMultiplicativeExpression_2_1_3_2(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.SLASH);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(REM)
	) */
	private int matchMultiplicativeExpression_2_1_3_3(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.REM);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		lookAhead(2)
		choice(
			sequence(
				terminal(STAR)
			)
			sequence(
				terminal(SLASH)
			)
			sequence(
				terminal(REM)
			)
		)
		nonTerminal(right, UnaryExpression)
	) */
	private int matchMultiplicativeExpression_lookahead1(int lookahead) {
		if (match(0, ParserImplConstants.STAR) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.SLASH) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.REM) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		return -1;
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
	public BUTree<? extends SExpr> parseUnaryExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		UnaryOp op;
		if (match(0, ParserImplConstants.DECR, ParserImplConstants.INCR) != -1) {
			ret = parsePrefixExpression();
		} else if (match(0, ParserImplConstants.MINUS, ParserImplConstants.PLUS) != -1) {
			run();
			if (match(0, ParserImplConstants.PLUS) != -1) {
				parse(ParserImplConstants.PLUS);
				op = UnaryOp.Positive;
			} else if (match(0, ParserImplConstants.MINUS) != -1) {
				parse(ParserImplConstants.MINUS);
				op = UnaryOp.Negative;
			} else {
				throw produceParseException(ParserImplConstants.MINUS, ParserImplConstants.PLUS);
			}
			ret = parseUnaryExpression();
			ret = dress(SUnaryExpr.make(op, ret));
		} else if (match(0, ParserImplConstants.BANG, ParserImplConstants.TILDE, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.DOUBLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.VOID, ParserImplConstants.NEW, ParserImplConstants.LT, ParserImplConstants.NULL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.STRING_LITERAL, ParserImplConstants.TRUE, ParserImplConstants.FALSE, ParserImplConstants.SUPER, ParserImplConstants.THIS, ParserImplConstants.LPAREN) != -1) {
			ret = parseUnaryExpressionNotPlusMinus();
		} else {
			throw produceParseException(ParserImplConstants.SUPER, ParserImplConstants.THIS, ParserImplConstants.FALSE, ParserImplConstants.TRUE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.NULL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.LPAREN, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.LT, ParserImplConstants.VOID, ParserImplConstants.BYTE, ParserImplConstants.SHORT, ParserImplConstants.INT, ParserImplConstants.LONG, ParserImplConstants.FLOAT, ParserImplConstants.DOUBLE, ParserImplConstants.BOOLEAN, ParserImplConstants.CHAR, ParserImplConstants.NEW, ParserImplConstants.BANG, ParserImplConstants.TILDE, ParserImplConstants.MINUS, ParserImplConstants.PLUS, ParserImplConstants.DECR, ParserImplConstants.INCR);
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
		lookahead = match(lookahead, ParserImplConstants.PLUS);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(MINUS)
	) */
	private int matchUnaryExpression_1_2_2_2(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.MINUS);
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
	public BUTree<? extends SExpr> parsePrefixExpression() throws ParseException {
		UnaryOp op;
		BUTree<? extends SExpr> ret;
		run();
		if (match(0, ParserImplConstants.INCR) != -1) {
			parse(ParserImplConstants.INCR);
			op = UnaryOp.PreIncrement;
		} else if (match(0, ParserImplConstants.DECR) != -1) {
			parse(ParserImplConstants.DECR);
			op = UnaryOp.PreDecrement;
		} else {
			throw produceParseException(ParserImplConstants.DECR, ParserImplConstants.INCR);
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
		lookahead = match(lookahead, ParserImplConstants.INCR);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(DECR)
	) */
	private int matchPrefixExpression_2_2(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.DECR);
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
				lookAhead(
					nonTerminal(CastExpression)
				)
				nonTerminal(ret, CastExpression)
			)
			nonTerminal(ret, PostfixExpression)
		)
		action({ return ret; })
	) */
	public BUTree<? extends SExpr> parseUnaryExpressionNotPlusMinus() throws ParseException {
		BUTree<? extends SExpr> ret;
		UnaryOp op;
		if (match(0, ParserImplConstants.BANG, ParserImplConstants.TILDE) != -1) {
			run();
			if (match(0, ParserImplConstants.TILDE) != -1) {
				parse(ParserImplConstants.TILDE);
				op = UnaryOp.Inverse;
			} else if (match(0, ParserImplConstants.BANG) != -1) {
				parse(ParserImplConstants.BANG);
				op = UnaryOp.Not;
			} else {
				throw produceParseException(ParserImplConstants.BANG, ParserImplConstants.TILDE);
			}
			ret = parseUnaryExpression();
			ret = dress(SUnaryExpr.make(op, ret));
		} else if (matchUnaryExpressionNotPlusMinus_lookahead1(0) != -1) {
			ret = parseCastExpression();
		} else if (match(0, ParserImplConstants.SUPER, ParserImplConstants.THIS, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.DOUBLE, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.BOOLEAN, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.VOID, ParserImplConstants.NEW, ParserImplConstants.LT, ParserImplConstants.LPAREN, ParserImplConstants.LONG_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.STRING_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.FALSE, ParserImplConstants.TRUE, ParserImplConstants.NULL) != -1) {
			ret = parsePostfixExpression();
		} else {
			throw produceParseException(ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.BOOLEAN, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.DOUBLE, ParserImplConstants.VOID, ParserImplConstants.NEW, ParserImplConstants.SUPER, ParserImplConstants.THIS, ParserImplConstants.NULL, ParserImplConstants.FALSE, ParserImplConstants.TRUE, ParserImplConstants.LONG_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.STRING_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.LPAREN, ParserImplConstants.LT, ParserImplConstants.BANG, ParserImplConstants.TILDE);
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
				lookAhead(
					nonTerminal(CastExpression)
				)
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
			lookAhead(
				nonTerminal(CastExpression)
			)
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
		lookahead = match(lookahead, ParserImplConstants.TILDE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(BANG)
	) */
	private int matchUnaryExpressionNotPlusMinus_1_1_2_2(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.BANG);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead(
			nonTerminal(CastExpression)
		)
		nonTerminal(ret, CastExpression)
	) */
	private int matchUnaryExpressionNotPlusMinus_1_2(int lookahead) {
		lookahead = matchCastExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(CastExpression)
	) */
	private int matchUnaryExpressionNotPlusMinus_lookahead1(int lookahead) {
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
	public BUTree<? extends SExpr> parsePostfixExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		UnaryOp op;
		ret = parsePrimaryExpression();
		if (matchPostfixExpression_lookahead1(0) != -1) {
			lateRun();
			if (match(0, ParserImplConstants.INCR) != -1) {
				parse(ParserImplConstants.INCR);
				op = UnaryOp.PostIncrement;
			} else if (match(0, ParserImplConstants.DECR) != -1) {
				parse(ParserImplConstants.DECR);
				op = UnaryOp.PostDecrement;
			} else {
				throw produceParseException(ParserImplConstants.DECR, ParserImplConstants.INCR);
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
		lookahead = match(lookahead, ParserImplConstants.INCR);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(DECR)
	) */
	private int matchPostfixExpression_2_1_3_2(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.DECR);
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
		if (match(0, ParserImplConstants.INCR) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.DECR) != -1) {
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
		if (match(0, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN) != -1) {
			primitiveType = parsePrimitiveType(annotations);
			if (match(0, ParserImplConstants.RPAREN) != -1) {
				parse(ParserImplConstants.RPAREN);
				ret = parseUnaryExpression();
				ret = dress(SCastExpr.make(primitiveType, ret));
			} else if (match(0, ParserImplConstants.AT, ParserImplConstants.LBRACKET) != -1) {
				lateRun();
				arrayDims = parseArrayDimsMandatory();
				type = dress(SArrayType.make(primitiveType, arrayDims));
				type = parseReferenceCastTypeRest(type);
				parse(ParserImplConstants.RPAREN);
				ret = parseUnaryExpressionNotPlusMinus();
				ret = dress(SCastExpr.make(type, ret));
			} else {
				throw produceParseException(ParserImplConstants.AT, ParserImplConstants.LBRACKET, ParserImplConstants.RPAREN);
			}
		} else if (match(0, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER) != -1) {
			type = parseQualifiedType(annotations);
			if (matchCastExpression_lookahead1(0) != -1) {
				lateRun();
				arrayDims = parseArrayDimsMandatory();
				type = dress(SArrayType.make(type, arrayDims));
			}
			type = parseReferenceCastTypeRest(type);
			parse(ParserImplConstants.RPAREN);
			ret = parseUnaryExpressionNotPlusMinus();
			ret = dress(SCastExpr.make(type, ret));
		} else {
			throw produceParseException(ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN);
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
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
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
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
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
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
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
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
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
		lookahead = match(lookahead, ParserImplConstants.LBRACKET);
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
	public BUTree<? extends SType> parseReferenceCastTypeRest(BUTree<? extends SType> type) throws ParseException {
		BUTree<SNodeList> types = emptyList();
		BUTree<SNodeList> annotations = null;
		if (matchReferenceCastTypeRest_lookahead1(0) != -1) {
			types = append(types, type);
			lateRun();
			do {
				parse(ParserImplConstants.BIT_AND);
				run();
				annotations = parseAnnotations();
				type = parseReferenceType(annotations);
				types = append(types, type);
			} while (match(0, ParserImplConstants.BIT_AND) != -1);
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
		lookahead = match(lookahead, ParserImplConstants.BIT_AND);
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
		lookahead = match(lookahead, ParserImplConstants.BIT_AND);
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
	public BUTree<? extends SExpr> parseLiteral() throws ParseException {
		Token literal;
		BUTree<? extends SExpr> ret;
		run();
		if (match(0, ParserImplConstants.INTEGER_LITERAL) != -1) {
			literal = parse(ParserImplConstants.INTEGER_LITERAL);
			ret = SLiteralExpr.make(Integer.class, literal.image);
		} else if (match(0, ParserImplConstants.LONG_LITERAL) != -1) {
			literal = parse(ParserImplConstants.LONG_LITERAL);
			ret = SLiteralExpr.make(Long.class, literal.image);
		} else if (match(0, ParserImplConstants.FLOAT_LITERAL) != -1) {
			literal = parse(ParserImplConstants.FLOAT_LITERAL);
			ret = SLiteralExpr.make(Float.class, literal.image);
		} else if (match(0, ParserImplConstants.DOUBLE_LITERAL) != -1) {
			literal = parse(ParserImplConstants.DOUBLE_LITERAL);
			ret = SLiteralExpr.make(Double.class, literal.image);
		} else if (match(0, ParserImplConstants.CHARACTER_LITERAL) != -1) {
			literal = parse(ParserImplConstants.CHARACTER_LITERAL);
			ret = SLiteralExpr.make(Character.class, literal.image);
		} else if (match(0, ParserImplConstants.STRING_LITERAL) != -1) {
			literal = parse(ParserImplConstants.STRING_LITERAL);
			ret = SLiteralExpr.make(String.class, literal.image);
		} else if (match(0, ParserImplConstants.TRUE) != -1) {
			literal = parse(ParserImplConstants.TRUE);
			ret = SLiteralExpr.make(Boolean.class, literal.image);
		} else if (match(0, ParserImplConstants.FALSE) != -1) {
			literal = parse(ParserImplConstants.FALSE);
			ret = SLiteralExpr.make(Boolean.class, literal.image);
		} else if (match(0, ParserImplConstants.NULL) != -1) {
			literal = parse(ParserImplConstants.NULL);
			ret = SLiteralExpr.make(Void.class, literal.image);
		} else {
			throw produceParseException(ParserImplConstants.NULL, ParserImplConstants.FALSE, ParserImplConstants.TRUE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.INTEGER_LITERAL);
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
		lookahead = match(lookahead, ParserImplConstants.INTEGER_LITERAL);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(literal, LONG_LITERAL)
	) */
	private int matchLiteral_2_2(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.LONG_LITERAL);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(literal, FLOAT_LITERAL)
	) */
	private int matchLiteral_2_3(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.FLOAT_LITERAL);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(literal, DOUBLE_LITERAL)
	) */
	private int matchLiteral_2_4(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.DOUBLE_LITERAL);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(literal, CHARACTER_LITERAL)
	) */
	private int matchLiteral_2_5(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.CHARACTER_LITERAL);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(literal, STRING_LITERAL)
	) */
	private int matchLiteral_2_6(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.STRING_LITERAL);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(literal, TRUE)
	) */
	private int matchLiteral_2_7(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.TRUE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(literal, FALSE)
	) */
	private int matchLiteral_2_8(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.FALSE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(literal, NULL)
	) */
	private int matchLiteral_2_9(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.NULL);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		nonTerminal(ret, PrimaryPrefix)
		zeroOrMore(
			lookAhead(2)
			action({ lateRun(); })
			nonTerminal(ret, PrimarySuffix)
		)
		action({ return ret; })
	) */
	public BUTree<? extends SExpr> parsePrimaryExpression() throws ParseException {
		BUTree<? extends SExpr> ret;
		ret = parsePrimaryPrefix();
		while (matchPrimaryExpression_lookahead1(0) != -1) {
			lateRun();
			ret = parsePrimarySuffix(ret);
		}
		return ret;
	}

	/* sequence(
		nonTerminal(ret, PrimaryPrefix)
		zeroOrMore(
			lookAhead(2)
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
		lookAhead(2)
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
		lookAhead(2)
		nonTerminal(ret, PrimarySuffix)
	) */
	private int matchPrimaryExpression_2_1(int lookahead) {
		lookahead = matchPrimarySuffix(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		lookAhead(2)
		nonTerminal(ret, PrimarySuffix)
	) */
	private int matchPrimaryExpression_lookahead1(int lookahead) {
		if (match(0, ParserImplConstants.DOUBLECOLON) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.DOT) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.LBRACKET) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		return -1;
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
	public BUTree<? extends SExpr> parsePrimaryExpressionWithoutSuperSuffix() throws ParseException {
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
	public BUTree<? extends SExpr> parsePrimaryPrefix() throws ParseException {
		BUTree<? extends SExpr> ret = null;
		BUTree<SNodeList> typeArgs = null;
		BUTree<SNodeList> params;
		BUTree<? extends SType> type;
		if (match(0, ParserImplConstants.LONG_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.NULL, ParserImplConstants.FALSE, ParserImplConstants.TRUE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.FLOAT_LITERAL) != -1) {
			ret = parseLiteral();
		} else if (match(0, ParserImplConstants.THIS) != -1) {
			run();
			parse(ParserImplConstants.THIS);
			ret = dress(SThisExpr.make(none()));
		} else if (match(0, ParserImplConstants.SUPER) != -1) {
			run();
			parse(ParserImplConstants.SUPER);
			ret = dress(SSuperExpr.make(none()));
			if (match(0, ParserImplConstants.DOT) != -1) {
				lateRun();
				parse(ParserImplConstants.DOT);
				if (matchPrimaryPrefix_lookahead1(0) != -1) {
					ret = parseMethodInvocation(ret);
				} else if (match(0, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER) != -1) {
					ret = parseFieldAccess(ret);
				} else {
					throw produceParseException(ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT);
				}
			} else if (match(0, ParserImplConstants.DOUBLECOLON) != -1) {
				lateRun();
				ret = parseMethodReferenceSuffix(ret);
			} else {
				throw produceParseException(ParserImplConstants.DOUBLECOLON, ParserImplConstants.DOT);
			}
		} else if (match(0, ParserImplConstants.NEW) != -1) {
			ret = parseAllocationExpression(null);
		} else if (matchPrimaryPrefix_lookahead2(0) != -1) {
			run();
			type = parseResultType();
			parse(ParserImplConstants.DOT);
			parse(ParserImplConstants.CLASS);
			ret = dress(SClassExpr.make(type));
		} else if (matchPrimaryPrefix_lookahead3(0) != -1) {
			run();
			type = parseResultType();
			ret = STypeExpr.make(type);
			ret = parseMethodReferenceSuffix(ret);
		} else if (matchPrimaryPrefix_lookahead4(0) != -1) {
			run();
			ret = parseMethodInvocation(null);
		} else if (match(0, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER) != -1) {
			ret = parseName();
			if (match(0, ParserImplConstants.ARROW) != -1) {
				lateRun();
				parse(ParserImplConstants.ARROW);
				ret = parseLambdaBody(singletonList(makeFormalParameter((BUTree<SName>) ret)), false);
			}
		} else if (match(0, ParserImplConstants.LPAREN) != -1) {
			run();
			parse(ParserImplConstants.LPAREN);
			if (match(0, ParserImplConstants.RPAREN) != -1) {
				parse(ParserImplConstants.RPAREN);
				parse(ParserImplConstants.ARROW);
				ret = parseLambdaBody(emptyList(), true);
			} else if (matchPrimaryPrefix_lookahead5(0) != -1) {
				ret = parseName();
				parse(ParserImplConstants.RPAREN);
				parse(ParserImplConstants.ARROW);
				ret = parseLambdaBody(singletonList(makeFormalParameter((BUTree<SName>) ret)), true);
			} else if (matchPrimaryPrefix_lookahead6(0) != -1) {
				params = parseInferredFormalParameterList();
				parse(ParserImplConstants.RPAREN);
				parse(ParserImplConstants.ARROW);
				ret = parseLambdaBody(params, true);
			} else if (isLambda(0)) {
				params = parseFormalParameterList();
				parse(ParserImplConstants.RPAREN);
				parse(ParserImplConstants.ARROW);
				ret = parseLambdaBody(params, true);
			} else if (match(0, ParserImplConstants.LPAREN, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.STRING_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.NULL, ParserImplConstants.TRUE, ParserImplConstants.FALSE, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.VOID, ParserImplConstants.NEW, ParserImplConstants.SUPER, ParserImplConstants.THIS, ParserImplConstants.BANG, ParserImplConstants.TILDE, ParserImplConstants.MINUS, ParserImplConstants.PLUS, ParserImplConstants.INCR, ParserImplConstants.DECR) != -1) {
				ret = parseExpression();
				parse(ParserImplConstants.RPAREN);
				ret = dress(SParenthesizedExpr.make(ret));
			} else {
				throw produceParseException(ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.SUPER, ParserImplConstants.THIS, ParserImplConstants.NULL, ParserImplConstants.TRUE, ParserImplConstants.FALSE, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.STRING_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.LPAREN, ParserImplConstants.LT, ParserImplConstants.VOID, ParserImplConstants.FLOAT, ParserImplConstants.DOUBLE, ParserImplConstants.BYTE, ParserImplConstants.SHORT, ParserImplConstants.INT, ParserImplConstants.LONG, ParserImplConstants.BOOLEAN, ParserImplConstants.CHAR, ParserImplConstants.NEW, ParserImplConstants.TILDE, ParserImplConstants.BANG, ParserImplConstants.PLUS, ParserImplConstants.MINUS, ParserImplConstants.DECR, ParserImplConstants.INCR, ParserImplConstants.NODE_LIST_VARIABLE, ParserImplConstants._DEFAULT, ParserImplConstants.ABSTRACT, ParserImplConstants.FINAL, ParserImplConstants.STATIC, ParserImplConstants.PUBLIC, ParserImplConstants.PRIVATE, ParserImplConstants.PROTECTED, ParserImplConstants.AT, ParserImplConstants.STRICTFP, ParserImplConstants.VOLATILE, ParserImplConstants.TRANSIENT, ParserImplConstants.NATIVE, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.RPAREN);
			}
		} else {
			throw produceParseException(ParserImplConstants.LPAREN, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.LT, ParserImplConstants.VOID, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.NEW, ParserImplConstants.SUPER, ParserImplConstants.THIS, ParserImplConstants.NULL, ParserImplConstants.FALSE, ParserImplConstants.TRUE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.INTEGER_LITERAL);
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
		lookahead = match(lookahead, ParserImplConstants.THIS);
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
		lookahead = match(lookahead, ParserImplConstants.SUPER);
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
		lookahead = match(lookahead, ParserImplConstants.DOT);
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
		lookahead = match(lookahead, ParserImplConstants.DOT);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.CLASS);
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
		lookahead = match(lookahead, ParserImplConstants.ARROW);
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
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
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
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.ARROW);
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
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.ARROW);
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
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.ARROW);
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
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.ARROW);
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
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
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
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
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
		lookahead = match(lookahead, ParserImplConstants.DOT);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.CLASS);
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
		lookahead = match(lookahead, ParserImplConstants.DOUBLECOLON);
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
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
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
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.ARROW);
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
		lookahead = match(lookahead, ParserImplConstants.COMMA);
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
	public BUTree<? extends SExpr> parsePrimarySuffix(BUTree<? extends SExpr> scope) throws ParseException {
		BUTree<? extends SExpr> ret;
		if (matchPrimarySuffix_lookahead1(0) != -1) {
			ret = parsePrimarySuffixWithoutSuper(scope);
		} else if (match(0, ParserImplConstants.DOT) != -1) {
			parse(ParserImplConstants.DOT);
			parse(ParserImplConstants.SUPER);
			ret = dress(SSuperExpr.make(optionOf(scope)));
		} else if (match(0, ParserImplConstants.DOUBLECOLON) != -1) {
			ret = parseMethodReferenceSuffix(scope);
		} else {
			throw produceParseException(ParserImplConstants.DOUBLECOLON, ParserImplConstants.DOT, ParserImplConstants.LBRACKET);
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
		lookahead = match(lookahead, ParserImplConstants.DOT);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.SUPER);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead(2)
		nonTerminal(ret, PrimarySuffixWithoutSuper)
	) */
	private int matchPrimarySuffix_lookahead1(int lookahead) {
		if (match(0, ParserImplConstants.DOT) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.LBRACKET) != -1) {
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
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
	public BUTree<? extends SExpr> parsePrimarySuffixWithoutSuper(BUTree<? extends SExpr> scope) throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<SName> name;
		if (match(0, ParserImplConstants.DOT) != -1) {
			parse(ParserImplConstants.DOT);
			if (match(0, ParserImplConstants.THIS) != -1) {
				parse(ParserImplConstants.THIS);
				ret = dress(SThisExpr.make(optionOf(scope)));
			} else if (match(0, ParserImplConstants.NEW) != -1) {
				ret = parseAllocationExpression(scope);
			} else if (matchPrimarySuffixWithoutSuper_lookahead1(0) != -1) {
				ret = parseMethodInvocation(scope);
			} else if (match(0, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER) != -1) {
				ret = parseFieldAccess(scope);
			} else {
				throw produceParseException(ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.LT, ParserImplConstants.NEW, ParserImplConstants.THIS);
			}
		} else if (match(0, ParserImplConstants.LBRACKET) != -1) {
			parse(ParserImplConstants.LBRACKET);
			ret = parseExpression();
			parse(ParserImplConstants.RBRACKET);
			ret = dress(SArrayAccessExpr.make(scope, ret));
		} else {
			throw produceParseException(ParserImplConstants.LBRACKET, ParserImplConstants.DOT);
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
		lookahead = match(lookahead, ParserImplConstants.DOT);
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
		lookahead = match(lookahead, ParserImplConstants.THIS);
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
		lookahead = match(lookahead, ParserImplConstants.LBRACKET);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RBRACKET);
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
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
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
	public BUTree<? extends SExpr> parseFieldAccess(BUTree<? extends SExpr> scope) throws ParseException {
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
	public BUTree<? extends SExpr> parseMethodInvocation(BUTree<? extends SExpr> scope) throws ParseException {
		BUTree<SNodeList> typeArgs = null;
		BUTree<SName> name;
		BUTree<SNodeList> args = null;
		BUTree<? extends SExpr> ret;
		if (match(0, ParserImplConstants.LT) != -1) {
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
					lookAhead(1)
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
	public BUTree<SNodeList> parseArguments() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<? extends SExpr> expr;
		parse(ParserImplConstants.LPAREN);
		if (match(0, ParserImplConstants.LPAREN, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.DECR, ParserImplConstants.INCR, ParserImplConstants.MINUS, ParserImplConstants.PLUS, ParserImplConstants.SUPER, ParserImplConstants.NEW, ParserImplConstants.VOID, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.LT, ParserImplConstants.FALSE, ParserImplConstants.NULL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.STRING_LITERAL, ParserImplConstants.TRUE, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.THIS, ParserImplConstants.TILDE, ParserImplConstants.BANG, ParserImplConstants.NODE_LIST_VARIABLE) != -1) {
			if (matchArguments_lookahead1(0) != -1) {
				expr = parseExpression();
				ret = append(ret, expr);
				while (match(0, ParserImplConstants.COMMA) != -1) {
					parse(ParserImplConstants.COMMA);
					expr = parseExpression();
					ret = append(ret, expr);
				}
			} else if (quotesMode) {
				ret = parseNodeListVar();
			} else {
				throw produceParseException(ParserImplConstants.NODE_LIST_VARIABLE, ParserImplConstants.LPAREN, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.LT, ParserImplConstants.VOID, ParserImplConstants.DOUBLE, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.BOOLEAN, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.NEW, ParserImplConstants.SUPER, ParserImplConstants.THIS, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.NULL, ParserImplConstants.FALSE, ParserImplConstants.TRUE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.BANG, ParserImplConstants.TILDE, ParserImplConstants.MINUS, ParserImplConstants.PLUS, ParserImplConstants.INCR, ParserImplConstants.DECR);
			}
		}
		parse(ParserImplConstants.RPAREN);
		return ret;
	}

	/* sequence(
		terminal(LPAREN)
		zeroOrOne(
			choice(
				sequence(
					lookAhead(1)
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
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchArguments_2(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		choice(
			sequence(
				lookAhead(1)
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
				lookAhead(1)
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
			lookAhead(1)
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
		lookAhead(1)
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
		lookahead = matchArguments_2_1_1_1_4(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		terminal(COMMA)
		nonTerminal(expr, Expression)
	) */
	private int matchArguments_2_1_1_1_4(int lookahead) {
		int newLookahead;
		newLookahead = matchArguments_2_1_1_1_4_1(lookahead);
		while (newLookahead != -1) {
			lookahead = newLookahead;
			newLookahead = matchArguments_2_1_1_1_4_1(lookahead);
		}
		return lookahead;
	}

	/* sequence(
		terminal(COMMA)
		nonTerminal(expr, Expression)
	) */
	private int matchArguments_2_1_1_1_4_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.COMMA);
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
		lookAhead(1)
		nonTerminal(expr, Expression)
		zeroOrMore(
			terminal(COMMA)
			nonTerminal(expr, Expression)
		)
	) */
	private int matchArguments_lookahead1(int lookahead) {
		if (match(0, ParserImplConstants.NODE_VARIABLE) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.NEW) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.FLOAT) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.LT) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.CHAR) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.INTEGER_LITERAL) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.INT) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.MINUS) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.BYTE) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.IDENTIFIER) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.DOUBLE) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.LONG) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.SUPER) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.NULL) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.DOUBLE_LITERAL) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.CHARACTER_LITERAL) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.BANG) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.LPAREN) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.TRUE) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.LONG_LITERAL) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.BOOLEAN) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.DECR) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.SHORT) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.FLOAT_LITERAL) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.INCR) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.THIS) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.FALSE) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.STRING_LITERAL) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.TILDE) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.VOID) != -1) {
			return lookahead;
		}
		if (match(0, ParserImplConstants.PLUS) != -1) {
			return lookahead;
		}
		return -1;
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
	public BUTree<? extends SExpr> parseMethodReferenceSuffix(BUTree<? extends SExpr> scope) throws ParseException {
		BUTree<SNodeList> typeArgs = null;
		BUTree<SName> name;
		BUTree<? extends SExpr> ret;
		parse(ParserImplConstants.DOUBLECOLON);
		if (match(0, ParserImplConstants.LT) != -1) {
			typeArgs = parseTypeArguments();
		}
		if (match(0, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE) != -1) {
			name = parseName();
		} else if (match(0, ParserImplConstants.NEW) != -1) {
			parse(ParserImplConstants.NEW);
			name = SName.make("new");
		} else {
			throw produceParseException(ParserImplConstants.NEW, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER);
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
		lookahead = match(lookahead, ParserImplConstants.DOUBLECOLON);
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
		lookahead = match(lookahead, ParserImplConstants.NEW);
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
	public BUTree<? extends SExpr> parseAllocationExpression(BUTree<? extends SExpr> scope) throws ParseException {
		BUTree<? extends SExpr> ret;
		BUTree<? extends SType> type;
		BUTree<SNodeList> typeArgs = null;
		BUTree<SNodeList> anonymousBody = null;
		BUTree<SNodeList> args;
		BUTree<SNodeList> annotations = null;
		if (scope == null) run();

		parse(ParserImplConstants.NEW);
		if (match(0, ParserImplConstants.LT) != -1) {
			typeArgs = parseTypeArguments();
		}
		run();
		annotations = parseAnnotations();
		if (match(0, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN) != -1) {
			type = parsePrimitiveType(annotations);
			ret = parseArrayCreationExpr(type);
		} else if (match(0, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER) != -1) {
			type = parseQualifiedType(annotations);
			if (match(0, ParserImplConstants.AT, ParserImplConstants.LBRACKET) != -1) {
				ret = parseArrayCreationExpr(type);
			} else if (match(0, ParserImplConstants.LPAREN) != -1) {
				args = parseArguments();
				if (matchAllocationExpression_lookahead1(0) != -1) {
					anonymousBody = parseClassOrInterfaceBody(TypeKind.Class);
				}
				ret = dress(SObjectCreationExpr.make(optionOf(scope), ensureNotNull(typeArgs), (BUTree<SQualifiedType>) type, args, optionOf(anonymousBody)));
			} else {
				throw produceParseException(ParserImplConstants.LPAREN, ParserImplConstants.AT, ParserImplConstants.LBRACKET);
			}
		} else {
			throw produceParseException(ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN);
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
		lookahead = match(lookahead, ParserImplConstants.NEW);
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
		lookahead = match(lookahead, ParserImplConstants.LBRACE);
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
	public BUTree<? extends SExpr> parseArrayCreationExpr(BUTree<? extends SType> componentType) throws ParseException {
		BUTree<? extends SExpr> expr;
		BUTree<SNodeList> arrayDimExprs = emptyList();
		BUTree<SNodeList> arrayDims = emptyList();
		BUTree<SNodeList> annotations = null;
		BUTree<SArrayInitializerExpr> initializer;
		if (matchArrayCreationExpr_lookahead1(0) != -1) {
			arrayDimExprs = parseArrayDimExprsMandatory();
			arrayDims = parseArrayDims();
			return dress(SArrayCreationExpr.make(componentType, arrayDimExprs, arrayDims, none()));
		} else if (match(0, ParserImplConstants.AT, ParserImplConstants.LBRACKET) != -1) {
			arrayDims = parseArrayDimsMandatory();
			initializer = parseArrayInitializer();
			return dress(SArrayCreationExpr.make(componentType, arrayDimExprs, arrayDims, optionOf(initializer)));
		} else {
			throw produceParseException(ParserImplConstants.AT, ParserImplConstants.LBRACKET);
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
		lookahead = match(lookahead, ParserImplConstants.LBRACKET);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RBRACKET);
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
		lookahead = match(lookahead, ParserImplConstants.LBRACKET);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RBRACKET);
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
		lookahead = match(lookahead, ParserImplConstants.LBRACKET);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RBRACKET);
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
	public BUTree<SNodeList> parseArrayDimsMandatory() throws ParseException {
		BUTree<SNodeList> arrayDims = emptyList();
		BUTree<SNodeList> annotations;
		do {
			run();
			annotations = parseAnnotations();
			parse(ParserImplConstants.LBRACKET);
			parse(ParserImplConstants.RBRACKET);
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
		lookahead = match(lookahead, ParserImplConstants.LBRACKET);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RBRACKET);
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
		lookahead = match(lookahead, ParserImplConstants.LBRACKET);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RBRACKET);
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
	public BUTree<? extends SStmt> parseStatement() throws ParseException {
		BUTree<? extends SStmt> ret;
		if (matchStatement_lookahead1(0) != -1) {
			ret = parseLabeledStatement();
		} else if (match(0, ParserImplConstants.ASSERT) != -1) {
			ret = parseAssertStatement();
		} else if (match(0, ParserImplConstants.LBRACE) != -1) {
			ret = parseBlock();
		} else if (match(0, ParserImplConstants.SEMICOLON) != -1) {
			ret = parseEmptyStatement();
		} else if (match(0, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.STRING_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.NULL, ParserImplConstants.TRUE, ParserImplConstants.FALSE, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.VOID, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.BYTE, ParserImplConstants.SHORT, ParserImplConstants.BOOLEAN, ParserImplConstants.CHAR, ParserImplConstants.FLOAT, ParserImplConstants.DOUBLE, ParserImplConstants.INT, ParserImplConstants.LONG, ParserImplConstants.LT, ParserImplConstants.LPAREN, ParserImplConstants.THIS, ParserImplConstants.SUPER, ParserImplConstants.NEW, ParserImplConstants.DECR, ParserImplConstants.INCR) != -1) {
			ret = parseStatementExpression();
		} else if (match(0, ParserImplConstants.SWITCH) != -1) {
			ret = parseSwitchStatement();
		} else if (match(0, ParserImplConstants.IF) != -1) {
			ret = parseIfStatement();
		} else if (match(0, ParserImplConstants.WHILE) != -1) {
			ret = parseWhileStatement();
		} else if (match(0, ParserImplConstants.DO) != -1) {
			ret = parseDoStatement();
		} else if (match(0, ParserImplConstants.FOR) != -1) {
			ret = parseForStatement();
		} else if (match(0, ParserImplConstants.BREAK) != -1) {
			ret = parseBreakStatement();
		} else if (match(0, ParserImplConstants.CONTINUE) != -1) {
			ret = parseContinueStatement();
		} else if (match(0, ParserImplConstants.RETURN) != -1) {
			ret = parseReturnStatement();
		} else if (match(0, ParserImplConstants.THROW) != -1) {
			ret = parseThrowStatement();
		} else if (match(0, ParserImplConstants.SYNCHRONIZED) != -1) {
			ret = parseSynchronizedStatement();
		} else if (match(0, ParserImplConstants.TRY) != -1) {
			ret = parseTryStatement();
		} else {
			throw produceParseException(ParserImplConstants.TRY, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.THROW, ParserImplConstants.RETURN, ParserImplConstants.CONTINUE, ParserImplConstants.BREAK, ParserImplConstants.FOR, ParserImplConstants.DO, ParserImplConstants.WHILE, ParserImplConstants.IF, ParserImplConstants.SWITCH, ParserImplConstants.VOID, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.LT, ParserImplConstants.NEW, ParserImplConstants.LPAREN, ParserImplConstants.THIS, ParserImplConstants.SUPER, ParserImplConstants.NULL, ParserImplConstants.FALSE, ParserImplConstants.TRUE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.INCR, ParserImplConstants.DECR, ParserImplConstants.SEMICOLON, ParserImplConstants.LBRACE, ParserImplConstants.ASSERT);
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
		if (match(0, ParserImplConstants.NODE_VARIABLE) != -1) {
			if (match(1, ParserImplConstants.COLON) != -1) {
				return lookahead;
			}
		}
		if (match(0, ParserImplConstants.IDENTIFIER) != -1) {
			if (match(1, ParserImplConstants.COLON) != -1) {
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
	public BUTree<SAssertStmt> parseAssertStatement() throws ParseException {
		BUTree<? extends SExpr> check;
		BUTree<? extends SExpr> msg = null;
		run();
		parse(ParserImplConstants.ASSERT);
		check = parseExpression();
		if (match(0, ParserImplConstants.COLON) != -1) {
			parse(ParserImplConstants.COLON);
			msg = parseExpression();
		}
		parse(ParserImplConstants.SEMICOLON);
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
		lookahead = match(lookahead, ParserImplConstants.ASSERT);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchAssertStatement_4(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.SEMICOLON);
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
		lookahead = match(lookahead, ParserImplConstants.COLON);
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
	public BUTree<SLabeledStmt> parseLabeledStatement() throws ParseException {
		BUTree<SName> label;
		BUTree<? extends SStmt> stmt;
		run();
		label = parseName();
		parse(ParserImplConstants.COLON);
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
		lookahead = match(lookahead, ParserImplConstants.COLON);
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
	public BUTree<SBlockStmt> parseBlock() throws ParseException {
		BUTree<SNodeList> stmts;
		run();
		parse(ParserImplConstants.LBRACE);
		stmts = parseStatements();
		parse(ParserImplConstants.RBRACE);
		return dress(SBlockStmt.make(ensureNotNull(stmts)));
	}

	/* sequence(
		terminal(LBRACE)
		nonTerminal(stmts, Statements)
		terminal(RBRACE)
	) */
	private int matchBlock(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.LBRACE);
		if (lookahead == -1)
			return -1;
		lookahead = matchStatements(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RBRACE);
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
					nonTerminal(VariableDeclExpression)
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
	public BUTree<? extends SStmt> parseBlockStatement() throws ParseException {
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
			parse(ParserImplConstants.SEMICOLON);
			ret = dress(SExpressionStmt.make(expr));
		} else if (match(0, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.TRY, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.THROW, ParserImplConstants.RETURN, ParserImplConstants.CONTINUE, ParserImplConstants.BREAK, ParserImplConstants.FOR, ParserImplConstants.DO, ParserImplConstants.WHILE, ParserImplConstants.IF, ParserImplConstants.SWITCH, ParserImplConstants.VOID, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.DOUBLE, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.BOOLEAN, ParserImplConstants.NEW, ParserImplConstants.SUPER, ParserImplConstants.THIS, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.STRING_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.TRUE, ParserImplConstants.FALSE, ParserImplConstants.NULL, ParserImplConstants.LPAREN, ParserImplConstants.LT, ParserImplConstants.DECR, ParserImplConstants.INCR, ParserImplConstants.SEMICOLON, ParserImplConstants.LBRACE, ParserImplConstants.ASSERT) != -1) {
			ret = parseStatement();
		} else {
			throw produceParseException(ParserImplConstants.TRY, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.THROW, ParserImplConstants.RETURN, ParserImplConstants.CONTINUE, ParserImplConstants.BREAK, ParserImplConstants.FOR, ParserImplConstants.DO, ParserImplConstants.WHILE, ParserImplConstants.IF, ParserImplConstants.SWITCH, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.DOUBLE, ParserImplConstants.BOOLEAN, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.VOID, ParserImplConstants.NEW, ParserImplConstants.SUPER, ParserImplConstants.THIS, ParserImplConstants.LPAREN, ParserImplConstants.LT, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.STRING_LITERAL, ParserImplConstants.TRUE, ParserImplConstants.FALSE, ParserImplConstants.NULL, ParserImplConstants.INCR, ParserImplConstants.DECR, ParserImplConstants.SEMICOLON, ParserImplConstants.LBRACE, ParserImplConstants.ASSERT, ParserImplConstants.AT, ParserImplConstants.PRIVATE, ParserImplConstants.PROTECTED, ParserImplConstants.PUBLIC, ParserImplConstants.TRANSIENT, ParserImplConstants.FINAL, ParserImplConstants.STATIC, ParserImplConstants.ABSTRACT, ParserImplConstants.STRICTFP, ParserImplConstants.NATIVE, ParserImplConstants.VOLATILE, ParserImplConstants.CLASS, ParserImplConstants.INTERFACE);
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
					nonTerminal(VariableDeclExpression)
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
				nonTerminal(VariableDeclExpression)
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
			nonTerminal(VariableDeclExpression)
		)
		nonTerminal(expr, VariableDeclExpression)
		terminal(SEMICOLON)
	) */
	private int matchBlockStatement_1_2(int lookahead) {
		lookahead = matchVariableDeclExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.SEMICOLON);
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
		newLookahead = match(lookahead, ParserImplConstants.CLASS);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = match(lookahead, ParserImplConstants.INTERFACE);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		nonTerminal(VariableDeclExpression)
	) */
	private int matchBlockStatement_lookahead2(int lookahead) {
		lookahead = matchVariableDeclExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		action({ run(); })
		action({ run(); })
		nonTerminal(modifiers, ModifiersNoDefault)
		nonTerminal(variableDecl, VariableDecl)
		action({ return dress(SVariableDeclarationExpr.make(variableDecl)); })
	) */
	public BUTree<SVariableDeclarationExpr> parseVariableDeclExpression() throws ParseException {
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
	public BUTree<SEmptyStmt> parseEmptyStatement() throws ParseException {
		run();
		parse(ParserImplConstants.SEMICOLON);
		return dress(SEmptyStmt.make());
	}

	/* sequence(
		terminal(SEMICOLON)
	) */
	private int matchEmptyStatement(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.SEMICOLON);
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
	public BUTree<SExpressionStmt> parseStatementExpression() throws ParseException {
		BUTree<? extends SExpr> expr;
		AssignOp op;
		BUTree<? extends SExpr> value;
		run();
		if (match(0, ParserImplConstants.DECR, ParserImplConstants.INCR) != -1) {
			expr = parsePrefixExpression();
		} else if (match(0, ParserImplConstants.SUPER, ParserImplConstants.THIS, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.DOUBLE, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.BOOLEAN, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.VOID, ParserImplConstants.NEW, ParserImplConstants.LT, ParserImplConstants.LPAREN, ParserImplConstants.LONG_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.STRING_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.FALSE, ParserImplConstants.TRUE, ParserImplConstants.NULL) != -1) {
			expr = parsePrimaryExpression();
			if (match(0, ParserImplConstants.INCR, ParserImplConstants.RUNSIGNEDSHIFTASSIGN, ParserImplConstants.ANDASSIGN, ParserImplConstants.XORASSIGN, ParserImplConstants.ORASSIGN, ParserImplConstants.PLUSASSIGN, ParserImplConstants.MINUSASSIGN, ParserImplConstants.LSHIFTASSIGN, ParserImplConstants.RSIGNEDSHIFTASSIGN, ParserImplConstants.ASSIGN, ParserImplConstants.STARASSIGN, ParserImplConstants.SLASHASSIGN, ParserImplConstants.REMASSIGN, ParserImplConstants.DECR) != -1) {
				if (match(0, ParserImplConstants.INCR) != -1) {
					lateRun();
					parse(ParserImplConstants.INCR);
					expr = dress(SUnaryExpr.make(UnaryOp.PostIncrement, expr));
				} else if (match(0, ParserImplConstants.DECR) != -1) {
					lateRun();
					parse(ParserImplConstants.DECR);
					expr = dress(SUnaryExpr.make(UnaryOp.PostDecrement, expr));
				} else if (match(0, ParserImplConstants.ORASSIGN, ParserImplConstants.XORASSIGN, ParserImplConstants.ANDASSIGN, ParserImplConstants.RUNSIGNEDSHIFTASSIGN, ParserImplConstants.RSIGNEDSHIFTASSIGN, ParserImplConstants.LSHIFTASSIGN, ParserImplConstants.MINUSASSIGN, ParserImplConstants.PLUSASSIGN, ParserImplConstants.REMASSIGN, ParserImplConstants.SLASHASSIGN, ParserImplConstants.STARASSIGN, ParserImplConstants.ASSIGN) != -1) {
					lateRun();
					op = parseAssignmentOperator();
					value = parseExpression();
					expr = dress(SAssignExpr.make(expr, op, value));
				} else {
					throw produceParseException(ParserImplConstants.PLUSASSIGN, ParserImplConstants.MINUSASSIGN, ParserImplConstants.SLASHASSIGN, ParserImplConstants.REMASSIGN, ParserImplConstants.RUNSIGNEDSHIFTASSIGN, ParserImplConstants.ANDASSIGN, ParserImplConstants.LSHIFTASSIGN, ParserImplConstants.RSIGNEDSHIFTASSIGN, ParserImplConstants.XORASSIGN, ParserImplConstants.ORASSIGN, ParserImplConstants.ASSIGN, ParserImplConstants.STARASSIGN, ParserImplConstants.DECR, ParserImplConstants.INCR);
				}
			}
		} else {
			throw produceParseException(ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.LT, ParserImplConstants.LPAREN, ParserImplConstants.THIS, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.TRUE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.NULL, ParserImplConstants.FALSE, ParserImplConstants.NEW, ParserImplConstants.SUPER, ParserImplConstants.BOOLEAN, ParserImplConstants.SHORT, ParserImplConstants.INT, ParserImplConstants.CHAR, ParserImplConstants.BYTE, ParserImplConstants.DOUBLE, ParserImplConstants.LONG, ParserImplConstants.FLOAT, ParserImplConstants.VOID, ParserImplConstants.DECR, ParserImplConstants.INCR);
		}
		parse(ParserImplConstants.SEMICOLON);
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
		lookahead = match(lookahead, ParserImplConstants.SEMICOLON);
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
		lookahead = match(lookahead, ParserImplConstants.INCR);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(DECR)
	) */
	private int matchStatementExpression_2_2_2_1_1_2(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.DECR);
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
		while (match(0, ParserImplConstants._DEFAULT, ParserImplConstants.CASE) != -1) {
			entry = parseSwitchEntry();
			entries = append(entries, entry);
		}
		parse(ParserImplConstants.RBRACE);
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
		lookahead = match(lookahead, ParserImplConstants.SWITCH);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.LBRACE);
		if (lookahead == -1)
			return -1;
		lookahead = matchSwitchStatement_7(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RBRACE);
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
			terminal(_DEFAULT)
		)
		terminal(COLON)
		nonTerminal(stmts, Statements)
		action({ return dress(SSwitchCase.make(optionOf(label), ensureNotNull(stmts))); })
	) */
	public BUTree<SSwitchCase> parseSwitchEntry() throws ParseException {
		BUTree<? extends SExpr> label = null;
		BUTree<SNodeList> stmts;
		run();
		if (match(0, ParserImplConstants.CASE) != -1) {
			parse(ParserImplConstants.CASE);
			label = parseExpression();
		} else if (match(0, ParserImplConstants._DEFAULT) != -1) {
			parse(ParserImplConstants._DEFAULT);
		} else {
			throw produceParseException(ParserImplConstants._DEFAULT, ParserImplConstants.CASE);
		}
		parse(ParserImplConstants.COLON);
		stmts = parseStatements();
		return dress(SSwitchCase.make(optionOf(label), ensureNotNull(stmts)));
	}

	/* sequence(
		choice(
			sequence(
				terminal(CASE)
				nonTerminal(label, Expression)
			)
			terminal(_DEFAULT)
		)
		terminal(COLON)
		nonTerminal(stmts, Statements)
	) */
	private int matchSwitchEntry(int lookahead) {
		lookahead = matchSwitchEntry_2(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.COLON);
		if (lookahead == -1)
			return -1;
		lookahead = matchStatements(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* choice(
		sequence(
			terminal(CASE)
			nonTerminal(label, Expression)
		)
		terminal(_DEFAULT)
	) */
	private int matchSwitchEntry_2(int lookahead) {
		int newLookahead;
		newLookahead = matchSwitchEntry_2_1(lookahead);
		if (newLookahead != -1)
			return newLookahead;
		newLookahead = match(lookahead, ParserImplConstants._DEFAULT);
		if (newLookahead != -1)
			return newLookahead;
		return -1;
	}

	/* sequence(
		terminal(CASE)
		nonTerminal(label, Expression)
	) */
	private int matchSwitchEntry_2_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.CASE);
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
		if (matchIfStatement_lookahead1(0) != -1) {
			parse(ParserImplConstants.ELSE);
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
		lookahead = match(lookahead, ParserImplConstants.IF);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
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
		lookahead = match(lookahead, ParserImplConstants.ELSE);
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
		if (match(0, ParserImplConstants.ELSE) != -1) {
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

	/* sequence(
		terminal(WHILE)
		terminal(LPAREN)
		nonTerminal(condition, Expression)
		terminal(RPAREN)
		nonTerminal(body, Statement)
	) */
	private int matchWhileStatement(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.WHILE);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
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
		lookahead = match(lookahead, ParserImplConstants.DO);
		if (lookahead == -1)
			return -1;
		lookahead = matchStatement(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.WHILE);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.SEMICOLON);
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
					nonTerminal(VariableDeclExpression)
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
	public BUTree<? extends SStmt> parseForStatement() throws ParseException {
		BUTree<SVariableDeclarationExpr> varExpr = null;
		BUTree<? extends SExpr> expr = null;
		BUTree<SNodeList> init = null;
		BUTree<SNodeList> update = null;
		BUTree<? extends SStmt> body;
		run();
		parse(ParserImplConstants.FOR);
		parse(ParserImplConstants.LPAREN);
		if (matchForStatement_lookahead1(0) != -1) {
			varExpr = parseVariableDeclExpression();
			parse(ParserImplConstants.COLON);
			expr = parseExpression();
		} else if (match(0, ParserImplConstants.INCR, ParserImplConstants.DECR, ParserImplConstants.PLUS, ParserImplConstants.MINUS, ParserImplConstants.LPAREN, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.LT, ParserImplConstants.VOID, ParserImplConstants.DOUBLE, ParserImplConstants.SHORT, ParserImplConstants.INT, ParserImplConstants.LONG, ParserImplConstants.FLOAT, ParserImplConstants.BOOLEAN, ParserImplConstants.CHAR, ParserImplConstants.BYTE, ParserImplConstants.NEW, ParserImplConstants.SUPER, ParserImplConstants.THIS, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.NULL, ParserImplConstants.FALSE, ParserImplConstants.TRUE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.TILDE, ParserImplConstants.BANG, ParserImplConstants.TRANSIENT, ParserImplConstants.FINAL, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.VOLATILE, ParserImplConstants.PRIVATE, ParserImplConstants.PROTECTED, ParserImplConstants.STATIC, ParserImplConstants.ABSTRACT, ParserImplConstants.PUBLIC, ParserImplConstants.STRICTFP, ParserImplConstants.NATIVE, ParserImplConstants.AT, ParserImplConstants.SEMICOLON) != -1) {
			if (match(0, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LPAREN, ParserImplConstants.DECR, ParserImplConstants.INCR, ParserImplConstants.PLUS, ParserImplConstants.MINUS, ParserImplConstants.LT, ParserImplConstants.VOID, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.SUPER, ParserImplConstants.NEW, ParserImplConstants.NULL, ParserImplConstants.FALSE, ParserImplConstants.TRUE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.THIS, ParserImplConstants.TILDE, ParserImplConstants.BANG, ParserImplConstants.AT, ParserImplConstants.PROTECTED, ParserImplConstants.PRIVATE, ParserImplConstants.PUBLIC, ParserImplConstants.FINAL, ParserImplConstants.TRANSIENT, ParserImplConstants.ABSTRACT, ParserImplConstants.STATIC, ParserImplConstants.NATIVE, ParserImplConstants.STRICTFP, ParserImplConstants.VOLATILE, ParserImplConstants.SYNCHRONIZED) != -1) {
				init = parseForInit();
			}
			parse(ParserImplConstants.SEMICOLON);
			if (match(0, ParserImplConstants.LPAREN, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.STRING_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.NULL, ParserImplConstants.TRUE, ParserImplConstants.FALSE, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.VOID, ParserImplConstants.NEW, ParserImplConstants.SUPER, ParserImplConstants.THIS, ParserImplConstants.BANG, ParserImplConstants.TILDE, ParserImplConstants.MINUS, ParserImplConstants.PLUS, ParserImplConstants.INCR, ParserImplConstants.DECR) != -1) {
				expr = parseExpression();
			}
			parse(ParserImplConstants.SEMICOLON);
			if (match(0, ParserImplConstants.LPAREN, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.MINUS, ParserImplConstants.PLUS, ParserImplConstants.BANG, ParserImplConstants.TILDE, ParserImplConstants.LONG_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.STRING_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.NULL, ParserImplConstants.FALSE, ParserImplConstants.TRUE, ParserImplConstants.VOID, ParserImplConstants.INT, ParserImplConstants.LONG, ParserImplConstants.FLOAT, ParserImplConstants.DOUBLE, ParserImplConstants.BOOLEAN, ParserImplConstants.CHAR, ParserImplConstants.BYTE, ParserImplConstants.SHORT, ParserImplConstants.LT, ParserImplConstants.NEW, ParserImplConstants.THIS, ParserImplConstants.SUPER, ParserImplConstants.DECR, ParserImplConstants.INCR) != -1) {
				update = parseForUpdate();
			}
		} else {
			throw produceParseException(ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LPAREN, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.STRING_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.FALSE, ParserImplConstants.TRUE, ParserImplConstants.NULL, ParserImplConstants.VOID, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.BOOLEAN, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.DOUBLE, ParserImplConstants.NEW, ParserImplConstants.SUPER, ParserImplConstants.THIS, ParserImplConstants.LT, ParserImplConstants.BANG, ParserImplConstants.TILDE, ParserImplConstants.MINUS, ParserImplConstants.PLUS, ParserImplConstants.DECR, ParserImplConstants.INCR, ParserImplConstants.VOLATILE, ParserImplConstants.TRANSIENT, ParserImplConstants.FINAL, ParserImplConstants.STATIC, ParserImplConstants.AT, ParserImplConstants.STRICTFP, ParserImplConstants.NATIVE, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.ABSTRACT, ParserImplConstants.PRIVATE, ParserImplConstants.PROTECTED, ParserImplConstants.PUBLIC, ParserImplConstants.SEMICOLON);
		}
		parse(ParserImplConstants.RPAREN);
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
					nonTerminal(VariableDeclExpression)
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
		lookahead = match(lookahead, ParserImplConstants.FOR);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchForStatement_4(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
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
				nonTerminal(VariableDeclExpression)
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
			nonTerminal(VariableDeclExpression)
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
		lookahead = match(lookahead, ParserImplConstants.COLON);
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
		lookahead = match(lookahead, ParserImplConstants.SEMICOLON);
		if (lookahead == -1)
			return -1;
		lookahead = matchForStatement_4_2_3(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.SEMICOLON);
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
		nonTerminal(VariableDeclExpression)
		terminal(COLON)
	) */
	private int matchForStatement_lookahead1(int lookahead) {
		lookahead = matchVariableDeclExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.COLON);
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
	public BUTree<SNodeList> parseForInit() throws ParseException {
		BUTree<SNodeList> ret;
		BUTree<? extends SExpr> expr;
		if (matchForInit_lookahead1(0) != -1) {
			expr = parseVariableDeclExpression();
			ret = emptyList();
			ret = append(ret, expr);
		} else if (match(0, ParserImplConstants.LPAREN, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.STRING_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.NULL, ParserImplConstants.TRUE, ParserImplConstants.FALSE, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.VOID, ParserImplConstants.NEW, ParserImplConstants.SUPER, ParserImplConstants.THIS, ParserImplConstants.BANG, ParserImplConstants.TILDE, ParserImplConstants.MINUS, ParserImplConstants.PLUS, ParserImplConstants.INCR, ParserImplConstants.DECR) != -1) {
			ret = parseExpressionList();
		} else {
			throw produceParseException(ParserImplConstants.BOOLEAN, ParserImplConstants.CHAR, ParserImplConstants.BYTE, ParserImplConstants.SHORT, ParserImplConstants.INT, ParserImplConstants.LONG, ParserImplConstants.FLOAT, ParserImplConstants.DOUBLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.VOID, ParserImplConstants.SUPER, ParserImplConstants.NEW, ParserImplConstants.NULL, ParserImplConstants.FALSE, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.TRUE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.THIS, ParserImplConstants.LPAREN, ParserImplConstants.LT, ParserImplConstants.BANG, ParserImplConstants.TILDE, ParserImplConstants.DECR, ParserImplConstants.INCR, ParserImplConstants.PLUS, ParserImplConstants.MINUS, ParserImplConstants.AT, ParserImplConstants.PUBLIC, ParserImplConstants.PROTECTED, ParserImplConstants.PRIVATE, ParserImplConstants.ABSTRACT, ParserImplConstants.STATIC, ParserImplConstants.FINAL, ParserImplConstants.TRANSIENT, ParserImplConstants.VOLATILE, ParserImplConstants.SYNCHRONIZED, ParserImplConstants.NATIVE, ParserImplConstants.STRICTFP);
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
	public BUTree<SNodeList> parseExpressionList() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<? extends SExpr> expr;
		expr = parseExpression();
		ret = append(ret, expr);
		while (match(0, ParserImplConstants.COMMA) != -1) {
			parse(ParserImplConstants.COMMA);
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
		lookahead = match(lookahead, ParserImplConstants.COMMA);
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
	public BUTree<SNodeList> parseForUpdate() throws ParseException {
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
	public BUTree<SBreakStmt> parseBreakStatement() throws ParseException {
		BUTree<SName> id = null;
		run();
		parse(ParserImplConstants.BREAK);
		if (match(0, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER) != -1) {
			id = parseName();
		}
		parse(ParserImplConstants.SEMICOLON);
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
		lookahead = match(lookahead, ParserImplConstants.BREAK);
		if (lookahead == -1)
			return -1;
		lookahead = matchBreakStatement_3(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.SEMICOLON);
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
	public BUTree<SContinueStmt> parseContinueStatement() throws ParseException {
		BUTree<SName> id = null;
		run();
		parse(ParserImplConstants.CONTINUE);
		if (match(0, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER) != -1) {
			id = parseName();
		}
		parse(ParserImplConstants.SEMICOLON);
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
		lookahead = match(lookahead, ParserImplConstants.CONTINUE);
		if (lookahead == -1)
			return -1;
		lookahead = matchContinueStatement_3(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.SEMICOLON);
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
	public BUTree<SReturnStmt> parseReturnStatement() throws ParseException {
		BUTree<? extends SExpr> expr = null;
		run();
		parse(ParserImplConstants.RETURN);
		if (match(0, ParserImplConstants.LPAREN, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.STRING_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.NULL, ParserImplConstants.TRUE, ParserImplConstants.FALSE, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.VOID, ParserImplConstants.NEW, ParserImplConstants.SUPER, ParserImplConstants.THIS, ParserImplConstants.BANG, ParserImplConstants.TILDE, ParserImplConstants.MINUS, ParserImplConstants.PLUS, ParserImplConstants.INCR, ParserImplConstants.DECR) != -1) {
			expr = parseExpression();
		}
		parse(ParserImplConstants.SEMICOLON);
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
		lookahead = match(lookahead, ParserImplConstants.RETURN);
		if (lookahead == -1)
			return -1;
		lookahead = matchReturnStatement_3(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.SEMICOLON);
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
	public BUTree<SThrowStmt> parseThrowStatement() throws ParseException {
		BUTree<? extends SExpr> expr;
		run();
		parse(ParserImplConstants.THROW);
		expr = parseExpression();
		parse(ParserImplConstants.SEMICOLON);
		return dress(SThrowStmt.make(expr));
	}

	/* sequence(
		terminal(THROW)
		nonTerminal(expr, Expression)
		terminal(SEMICOLON)
	) */
	private int matchThrowStatement(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.THROW);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.SEMICOLON);
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

	/* sequence(
		terminal(SYNCHRONIZED)
		terminal(LPAREN)
		nonTerminal(expr, Expression)
		terminal(RPAREN)
		nonTerminal(block, Block)
	) */
	private int matchSynchronizedStatement(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.SYNCHRONIZED);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchExpression(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
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
	public BUTree<STryStmt> parseTryStatement() throws ParseException {
		BUTree<SNodeList> resources = null;
		ByRef<Boolean> trailingSemiColon = new ByRef<Boolean>(false);
		BUTree<SBlockStmt> tryBlock;
		BUTree<SBlockStmt> finallyBlock = null;
		BUTree<SNodeList> catchClauses = null;
		run();
		parse(ParserImplConstants.TRY);
		if (match(0, ParserImplConstants.LPAREN) != -1) {
			resources = parseResourceSpecification(trailingSemiColon);
			tryBlock = parseBlock();
			if (match(0, ParserImplConstants.CATCH) != -1) {
				catchClauses = parseCatchClauses();
			}
			if (match(0, ParserImplConstants.FINALLY) != -1) {
				parse(ParserImplConstants.FINALLY);
				finallyBlock = parseBlock();
			}
		} else if (match(0, ParserImplConstants.LBRACE) != -1) {
			tryBlock = parseBlock();
			if (match(0, ParserImplConstants.CATCH) != -1) {
				catchClauses = parseCatchClauses();
				if (match(0, ParserImplConstants.FINALLY) != -1) {
					parse(ParserImplConstants.FINALLY);
					finallyBlock = parseBlock();
				}
			} else if (match(0, ParserImplConstants.FINALLY) != -1) {
				parse(ParserImplConstants.FINALLY);
				finallyBlock = parseBlock();
			} else {
				throw produceParseException(ParserImplConstants.FINALLY, ParserImplConstants.CATCH);
			}
		} else {
			throw produceParseException(ParserImplConstants.LBRACE, ParserImplConstants.LPAREN);
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
		lookahead = match(lookahead, ParserImplConstants.TRY);
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
		lookahead = match(lookahead, ParserImplConstants.FINALLY);
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
		lookahead = match(lookahead, ParserImplConstants.FINALLY);
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
		lookahead = match(lookahead, ParserImplConstants.FINALLY);
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
	public BUTree<SNodeList> parseCatchClauses() throws ParseException {
		BUTree<SNodeList> catchClauses = emptyList();
		BUTree<SCatchClause> catchClause;
		do {
			catchClause = parseCatchClause();
			catchClauses = append(catchClauses, catchClause);
		} while (match(0, ParserImplConstants.CATCH) != -1);
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

	/* sequence(
		terminal(CATCH)
		terminal(LPAREN)
		nonTerminal(param, CatchFormalParameter)
		terminal(RPAREN)
		nonTerminal(catchBlock, Block)
	) */
	private int matchCatchClause(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.CATCH);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchCatchFormalParameter(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
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
		action({ return dress(SFormalParameter.make(modifiers, exceptType, false, exceptId)); })
	) */
	public BUTree<SFormalParameter> parseCatchFormalParameter() throws ParseException {
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
				parse(ParserImplConstants.BIT_OR);
				exceptType = parseAnnotatedQualifiedType();
				exceptTypes = append(exceptTypes, exceptType);
			} while (match(0, ParserImplConstants.BIT_OR) != -1);
			exceptType = dress(SUnionType.make(exceptTypes));
		}
		exceptId = parseVariableDeclaratorId();
		return dress(SFormalParameter.make(modifiers, exceptType, false, exceptId));
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
		lookahead = match(lookahead, ParserImplConstants.BIT_OR);
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
		lookahead = match(lookahead, ParserImplConstants.BIT_OR);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(LPAREN)
		nonTerminal(var, VariableDeclExpression)
		action({ vars = append(vars, var); })
		zeroOrMore(
			lookAhead(2)
			terminal(SEMICOLON)
			nonTerminal(var, VariableDeclExpression)
			action({ vars = append(vars, var); })
		)
		zeroOrOne(
			lookAhead(2)
			terminal(SEMICOLON)
			action({ trailingSemiColon.value = true; })
		)
		terminal(RPAREN)
		action({ return vars; })
	) */
	public BUTree<SNodeList> parseResourceSpecification(ByRef<Boolean> trailingSemiColon) throws ParseException {
		BUTree<SNodeList> vars = emptyList();
		BUTree<SVariableDeclarationExpr> var;
		parse(ParserImplConstants.LPAREN);
		var = parseVariableDeclExpression();
		vars = append(vars, var);
		while (matchResourceSpecification_lookahead1(0) != -1) {
			parse(ParserImplConstants.SEMICOLON);
			var = parseVariableDeclExpression();
			vars = append(vars, var);
		}
		if (matchResourceSpecification_lookahead2(0) != -1) {
			parse(ParserImplConstants.SEMICOLON);
			trailingSemiColon.value = true;
		}
		parse(ParserImplConstants.RPAREN);
		return vars;
	}

	/* sequence(
		terminal(LPAREN)
		nonTerminal(var, VariableDeclExpression)
		zeroOrMore(
			lookAhead(2)
			terminal(SEMICOLON)
			nonTerminal(var, VariableDeclExpression)
		)
		zeroOrOne(
			lookAhead(2)
			terminal(SEMICOLON)
		)
		terminal(RPAREN)
	) */
	private int matchResourceSpecification(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
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
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
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
		lookAhead(2)
		terminal(SEMICOLON)
		nonTerminal(var, VariableDeclExpression)
	) */
	private int matchResourceSpecification_4_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.SEMICOLON);
		if (lookahead == -1)
			return -1;
		lookahead = matchVariableDeclExpression(lookahead);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		lookAhead(2)
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
		lookAhead(2)
		terminal(SEMICOLON)
	) */
	private int matchResourceSpecification_5_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.SEMICOLON);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		lookAhead(2)
		terminal(SEMICOLON)
		nonTerminal(var, VariableDeclExpression)
	) */
	private int matchResourceSpecification_lookahead1(int lookahead) {
		if (match(0, ParserImplConstants.SEMICOLON) != -1) {
			if (match(1, ParserImplConstants.VOLATILE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SYNCHRONIZED) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NATIVE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FINAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.ABSTRACT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRICTFP) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STATIC) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PRIVATE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PUBLIC) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PROTECTED) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRANSIENT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
		}
		return -1;
	}

	/* zeroOrOne(
		lookAhead(2)
		terminal(SEMICOLON)
	) */
	private int matchResourceSpecification_lookahead2(int lookahead) {
		if (match(0, ParserImplConstants.SEMICOLON) != -1) {
			return lookahead;
		}
		return -1;
	}

	/* sequence(
		lookAhead({ getToken(1).kind == GT && getToken(1).realKind == RUNSIGNEDSHIFT })
		terminal(GT)
		terminal(GT)
		terminal(GT)
		action({ popNewWhitespaces(2); })
	) */
	public void parseRUNSIGNEDSHIFT() throws ParseException {
		parse(ParserImplConstants.GT);
		parse(ParserImplConstants.GT);
		parse(ParserImplConstants.GT);
		popNewWhitespaces(2);
	}

	/* sequence(
		lookAhead({ getToken(1).kind == GT && getToken(1).realKind == RUNSIGNEDSHIFT })
		terminal(GT)
		terminal(GT)
		terminal(GT)
	) */
	private int matchRUNSIGNEDSHIFT(int lookahead) {
		lookahead = getToken(1).kind == GT && getToken(1).realKind == RUNSIGNEDSHIFT ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.GT);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.GT);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.GT);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		lookAhead({ getToken(1).kind == GT && getToken(1).realKind == RSIGNEDSHIFT })
		terminal(GT)
		terminal(GT)
		action({ popNewWhitespaces(1); })
	) */
	public void parseRSIGNEDSHIFT() throws ParseException {
		parse(ParserImplConstants.GT);
		parse(ParserImplConstants.GT);
		popNewWhitespaces(1);
	}

	/* sequence(
		lookAhead({ getToken(1).kind == GT && getToken(1).realKind == RSIGNEDSHIFT })
		terminal(GT)
		terminal(GT)
	) */
	private int matchRSIGNEDSHIFT(int lookahead) {
		lookahead = getToken(1).kind == GT && getToken(1).realKind == RSIGNEDSHIFT ? lookahead : -1;
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.GT);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.GT);
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
	public BUTree<SNodeList> parseAnnotations() throws ParseException {
		BUTree<SNodeList> annotations = emptyList();
		BUTree<? extends SAnnotationExpr> annotation;
		while (match(0, ParserImplConstants.AT) != -1) {
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
	public BUTree<? extends SAnnotationExpr> parseAnnotation() throws ParseException {
		BUTree<? extends SAnnotationExpr> ret;
		if (matchAnnotation_lookahead1(0) != -1) {
			ret = parseNormalAnnotation();
		} else if (matchAnnotation_lookahead2(0) != -1) {
			ret = parseSingleMemberAnnotation();
		} else if (matchAnnotation_lookahead3(0) != -1) {
			ret = parseMarkerAnnotation();
		} else {
			throw produceParseException(ParserImplConstants.AT);
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
		lookahead = match(lookahead, ParserImplConstants.AT);
		if (lookahead == -1)
			return -1;
		lookahead = matchQualifiedName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
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
		newLookahead = match(lookahead, ParserImplConstants.RPAREN);
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
		lookahead = match(lookahead, ParserImplConstants.ASSIGN);
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
		lookahead = match(lookahead, ParserImplConstants.AT);
		if (lookahead == -1)
			return -1;
		lookahead = matchQualifiedName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* sequence(
		terminal(AT)
		nonTerminal(QualifiedName)
	) */
	private int matchAnnotation_lookahead3(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.AT);
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
	public BUTree<SNormalAnnotationExpr> parseNormalAnnotation() throws ParseException {
		BUTree<SQualifiedName> name;
		BUTree<SNodeList> pairs = null;
		run();
		parse(ParserImplConstants.AT);
		name = parseQualifiedName();
		parse(ParserImplConstants.LPAREN);
		if (match(0, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER) != -1) {
			pairs = parseMemberValuePairs();
		}
		parse(ParserImplConstants.RPAREN);
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
		lookahead = match(lookahead, ParserImplConstants.AT);
		if (lookahead == -1)
			return -1;
		lookahead = matchQualifiedName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchNormalAnnotation_5(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
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
	public BUTree<SMarkerAnnotationExpr> parseMarkerAnnotation() throws ParseException {
		BUTree<SQualifiedName> name;
		run();
		parse(ParserImplConstants.AT);
		name = parseQualifiedName();
		return dress(SMarkerAnnotationExpr.make(name));
	}

	/* sequence(
		terminal(AT)
		nonTerminal(name, QualifiedName)
	) */
	private int matchMarkerAnnotation(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.AT);
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

	/* sequence(
		terminal(AT)
		nonTerminal(name, QualifiedName)
		terminal(LPAREN)
		nonTerminal(memberVal, MemberValue)
		terminal(RPAREN)
	) */
	private int matchSingleMemberAnnotation(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.AT);
		if (lookahead == -1)
			return -1;
		lookahead = matchQualifiedName(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.LPAREN);
		if (lookahead == -1)
			return -1;
		lookahead = matchMemberValue(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RPAREN);
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
	public BUTree<SNodeList> parseMemberValuePairs() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<SMemberValuePair> pair;
		pair = parseMemberValuePair();
		ret = append(ret, pair);
		while (match(0, ParserImplConstants.COMMA) != -1) {
			parse(ParserImplConstants.COMMA);
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
		lookahead = match(lookahead, ParserImplConstants.COMMA);
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
	public BUTree<SMemberValuePair> parseMemberValuePair() throws ParseException {
		BUTree<SName> name;
		BUTree<? extends SExpr> value;
		run();
		name = parseName();
		parse(ParserImplConstants.ASSIGN);
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
		lookahead = match(lookahead, ParserImplConstants.ASSIGN);
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
	public BUTree<? extends SExpr> parseMemberValue() throws ParseException {
		BUTree<? extends SExpr> ret;
		if (match(0, ParserImplConstants.AT) != -1) {
			ret = parseAnnotation();
		} else if (match(0, ParserImplConstants.LBRACE) != -1) {
			ret = parseMemberValueArrayInitializer();
		} else if (match(0, ParserImplConstants.MINUS, ParserImplConstants.PLUS, ParserImplConstants.LPAREN, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LT, ParserImplConstants.FLOAT, ParserImplConstants.DOUBLE, ParserImplConstants.INT, ParserImplConstants.LONG, ParserImplConstants.BYTE, ParserImplConstants.SHORT, ParserImplConstants.BOOLEAN, ParserImplConstants.CHAR, ParserImplConstants.VOID, ParserImplConstants.NEW, ParserImplConstants.SUPER, ParserImplConstants.THIS, ParserImplConstants.LONG_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.STRING_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.NULL, ParserImplConstants.FALSE, ParserImplConstants.TRUE, ParserImplConstants.BANG, ParserImplConstants.TILDE, ParserImplConstants.INCR, ParserImplConstants.DECR) != -1) {
			ret = parseConditionalExpression();
		} else {
			throw produceParseException(ParserImplConstants.PLUS, ParserImplConstants.MINUS, ParserImplConstants.BANG, ParserImplConstants.TILDE, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.NULL, ParserImplConstants.TRUE, ParserImplConstants.FALSE, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.STRING_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.VOID, ParserImplConstants.IDENTIFIER, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.LONG, ParserImplConstants.INT, ParserImplConstants.DOUBLE, ParserImplConstants.FLOAT, ParserImplConstants.CHAR, ParserImplConstants.BOOLEAN, ParserImplConstants.SHORT, ParserImplConstants.BYTE, ParserImplConstants.LT, ParserImplConstants.LPAREN, ParserImplConstants.THIS, ParserImplConstants.SUPER, ParserImplConstants.NEW, ParserImplConstants.DECR, ParserImplConstants.INCR, ParserImplConstants.LBRACE, ParserImplConstants.AT);
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
				lookAhead(2)
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
	public BUTree<? extends SExpr> parseMemberValueArrayInitializer() throws ParseException {
		BUTree<SNodeList> ret = emptyList();
		BUTree<? extends SExpr> member;
		boolean trailingComma = false;
		run();
		parse(ParserImplConstants.LBRACE);
		if (match(0, ParserImplConstants.PLUS, ParserImplConstants.MINUS, ParserImplConstants.BANG, ParserImplConstants.TILDE, ParserImplConstants.SUPER, ParserImplConstants.THIS, ParserImplConstants.BOOLEAN, ParserImplConstants.FLOAT, ParserImplConstants.LONG, ParserImplConstants.DOUBLE, ParserImplConstants.BYTE, ParserImplConstants.CHAR, ParserImplConstants.INT, ParserImplConstants.SHORT, ParserImplConstants.NODE_VARIABLE, ParserImplConstants.IDENTIFIER, ParserImplConstants.VOID, ParserImplConstants.NEW, ParserImplConstants.LT, ParserImplConstants.LPAREN, ParserImplConstants.NULL, ParserImplConstants.FALSE, ParserImplConstants.TRUE, ParserImplConstants.STRING_LITERAL, ParserImplConstants.CHARACTER_LITERAL, ParserImplConstants.DOUBLE_LITERAL, ParserImplConstants.FLOAT_LITERAL, ParserImplConstants.LONG_LITERAL, ParserImplConstants.INTEGER_LITERAL, ParserImplConstants.DECR, ParserImplConstants.INCR, ParserImplConstants.LBRACE, ParserImplConstants.AT) != -1) {
			member = parseMemberValue();
			ret = append(ret, member);
			while (matchMemberValueArrayInitializer_lookahead1(0) != -1) {
				parse(ParserImplConstants.COMMA);
				member = parseMemberValue();
				ret = append(ret, member);
			}
		}
		if (match(0, ParserImplConstants.COMMA) != -1) {
			parse(ParserImplConstants.COMMA);
			trailingComma = true;
		}
		parse(ParserImplConstants.RBRACE);
		return dress(SArrayInitializerExpr.make(ret, trailingComma));
	}

	/* sequence(
		terminal(LBRACE)
		zeroOrOne(
			nonTerminal(member, MemberValue)
			zeroOrMore(
				lookAhead(2)
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
		lookahead = match(lookahead, ParserImplConstants.LBRACE);
		if (lookahead == -1)
			return -1;
		lookahead = matchMemberValueArrayInitializer_3(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = matchMemberValueArrayInitializer_4(lookahead);
		if (lookahead == -1)
			return -1;
		lookahead = match(lookahead, ParserImplConstants.RBRACE);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrOne(
		nonTerminal(member, MemberValue)
		zeroOrMore(
			lookAhead(2)
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
			lookAhead(2)
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
		lookAhead(2)
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
		lookAhead(2)
		terminal(COMMA)
		nonTerminal(member, MemberValue)
	) */
	private int matchMemberValueArrayInitializer_3_1_3_1(int lookahead) {
		lookahead = match(lookahead, ParserImplConstants.COMMA);
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
		lookahead = match(lookahead, ParserImplConstants.COMMA);
		if (lookahead == -1)
			return -1;
		return lookahead;
	}

	/* zeroOrMore(
		lookAhead(2)
		terminal(COMMA)
		nonTerminal(member, MemberValue)
	) */
	private int matchMemberValueArrayInitializer_lookahead1(int lookahead) {
		if (match(0, ParserImplConstants.COMMA) != -1) {
			if (match(1, ParserImplConstants.NODE_VARIABLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NEW) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHAR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INTEGER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.MINUS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BYTE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.IDENTIFIER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SUPER) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.NULL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DOUBLE_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.CHARACTER_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BANG) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LPAREN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TRUE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LONG_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.BOOLEAN) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.SHORT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.DECR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.AT) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FLOAT_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.INCR) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.THIS) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.STRING_LITERAL) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.FALSE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.TILDE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.VOID) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.LBRACE) != -1) {
				return lookahead;
			}
			if (match(1, ParserImplConstants.PLUS) != -1) {
				return lookahead;
			}
		}
		return -1;
	}
}
