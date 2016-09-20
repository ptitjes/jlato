/*
 * Copyright (C) 2015-2016 Didier Villevalois.
 *
 * This file is part of JLaTo.
 *
 * JLaTo is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * JLaTo is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JLaTo.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.jlato.internal.parser;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.decl.*;
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.bu.name.SQualifiedName;
import org.jlato.internal.bu.stmt.SStmt;
import org.jlato.internal.bu.type.SType;
import org.jlato.parser.ParseException;

/**
 * @author Didier Villevalois
 */
public abstract class ParserNewBase extends ParserBase {

	@Override
	protected BUTree<SCompilationUnit> parseCompilationUnitEntry() throws ParseException {
		return parseCompilationUnit();
	}

	@Override
	protected BUTree<SPackageDecl> parsePackageDeclEntry() throws ParseException {
		return wrapWithPrologAndEpilog(parsePackageDecl());
	}

	@Override
	protected BUTree<SImportDecl> parseImportDeclEntry() throws ParseException {
		return wrapWithPrologAndEpilog(parseImportDecl());
	}

	@Override
	protected BUTree<? extends STypeDecl> parseTypeDeclEntry() throws ParseException {
		return wrapWithPrologAndEpilog(parseTypeDecl());
	}

	@Override
	protected BUTree<? extends SMemberDecl> parseMemberDeclEntry(TypeKind kind) throws ParseException {
		return wrapWithPrologAndEpilog(parseClassOrInterfaceBodyDecl(kind));
	}

	@Override
	protected BUTree<? extends SMemberDecl> parseAnnotationMemberDeclEntry() throws ParseException {
		return wrapWithPrologAndEpilog(parseAnnotationTypeBodyDecl());
	}

	@Override
	protected BUTree<SNodeList> parseModifiersEntry() throws ParseException {
		return parseModifiers();
	}

	@Override
	protected BUTree<SNodeList> parseAnnotationsEntry() throws ParseException {
		return parseAnnotations();
	}

	@Override
	protected BUTree<SMethodDecl> parseMethodDeclEntry() throws ParseException {
		run();
		BUTree<SNodeList> modifiers = parseModifiers();
		return wrapWithPrologAndEpilog(parseMethodDecl(modifiers));
	}

	@Override
	protected BUTree<SFieldDecl> parseFieldDeclEntry() throws ParseException {
		run();
		BUTree<SNodeList> modifiers = parseModifiers();
		return wrapWithPrologAndEpilog(parseFieldDecl(modifiers));
	}

	@Override
	protected BUTree<SAnnotationMemberDecl> parseAnnotationElementDeclEntry() throws ParseException {
		run();
		BUTree<SNodeList> modifiers = parseModifiers();
		return wrapWithPrologAndEpilog(parseAnnotationTypeMemberDecl(modifiers));
	}

	@Override
	protected BUTree<SEnumConstantDecl> parseEnumConstantDeclEntry() throws ParseException {
		return wrapWithPrologAndEpilog(parseEnumConstantDecl());
	}

	@Override
	protected BUTree<SFormalParameter> parseFormalParameterEntry() throws ParseException {
		return wrapWithPrologAndEpilog(parseFormalParameter());
	}

	@Override
	protected BUTree<STypeParameter> parseTypeParameterEntry() throws ParseException {
		return wrapWithPrologAndEpilog(parseTypeParameter());
	}

	@Override
	protected BUTree<SNodeList> parseStatementsEntry() throws ParseException {
		return parseStatements(false);
	}

	@Override
	protected BUTree<? extends SStmt> parseBlockStatementEntry() throws ParseException {
		return wrapWithPrologAndEpilog(parseBlockStatement());
	}

	@Override
	protected BUTree<? extends SExpr> parseExpressionEntry() throws ParseException {
		return wrapWithPrologAndEpilog(parseExpression());
	}

	@Override
	protected BUTree<? extends SType> parseTypeEntry() throws ParseException {
		run();
		final BUTree<SNodeList> annotations = parseAnnotations();
		return wrapWithPrologAndEpilog(parseType(annotations));
	}

	@Override
	protected BUTree<SQualifiedName> parseQualifiedNameEntry() throws ParseException {
		return wrapWithPrologAndEpilog(parseQualifiedName());
	}

	@Override
	protected BUTree<SName> parseNameEntry() throws ParseException {
		return wrapWithPrologAndEpilog(parseName());
	}

	protected <S extends STree> BUTree<S> wrapWithPrologAndEpilog(BUTree<S> tree) throws ParseException {
		parseEpilog();
		return dressWithPrologAndEpilog(tree);
	}

	protected abstract BUTree<SCompilationUnit> parseCompilationUnit() throws ParseException;

	protected abstract BUTree<SPackageDecl> parsePackageDecl() throws ParseException;

	protected abstract BUTree<SImportDecl> parseImportDecl() throws ParseException;

	protected abstract BUTree<? extends STypeDecl> parseTypeDecl() throws ParseException;

	protected abstract BUTree<? extends SMemberDecl> parseClassOrInterfaceBodyDecl(TypeKind kind) throws ParseException;

	protected abstract BUTree<? extends SMemberDecl> parseAnnotationTypeBodyDecl() throws ParseException;

	protected abstract BUTree<SNodeList> parseModifiers() throws ParseException;

	protected abstract BUTree<SMethodDecl> parseMethodDecl(BUTree<SNodeList> modifiers) throws ParseException;

	protected abstract BUTree<SFieldDecl> parseFieldDecl(BUTree<SNodeList> modifiers) throws ParseException;

	protected abstract BUTree<SAnnotationMemberDecl> parseAnnotationTypeMemberDecl(BUTree<SNodeList> modifiers) throws ParseException;

	protected abstract BUTree<SEnumConstantDecl> parseEnumConstantDecl() throws ParseException;

	protected abstract BUTree<SFormalParameter> parseFormalParameter() throws ParseException;

	protected abstract BUTree<STypeParameter> parseTypeParameter() throws ParseException;

	protected abstract BUTree<SNodeList> parseStatements(boolean inConstructor) throws ParseException;

	protected abstract BUTree<? extends SStmt> parseBlockStatement() throws ParseException;

	protected abstract BUTree<? extends SExpr> parseExpression() throws ParseException;

	protected abstract BUTree<SNodeList> parseAnnotations() throws ParseException;

	protected abstract BUTree<? extends SType> parseType(BUTree<SNodeList> annotations) throws ParseException;

	protected abstract BUTree<SQualifiedName> parseQualifiedName() throws ParseException;

	protected abstract BUTree<SName> parseName() throws ParseException;

	protected abstract void parseEpilog() throws ParseException;
}
