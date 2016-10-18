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
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.decl.*;
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.bu.name.SQualifiedName;
import org.jlato.internal.bu.stmt.SStmt;
import org.jlato.internal.bu.type.SType;
import org.jlato.parser.ParseException;
import org.jlato.parser.ParserConfiguration;

import java.io.InputStream;
import java.io.Reader;

/**
 * @author Didier Villevalois
 */
public class ParserNew extends ParserInterface {

	public static class ParserNewFactory implements Factory {
		@Override
		public ParserInterface newInstance(InputStream in, String encoding, ParserConfiguration configuration, boolean quotesMode) {
			ParserNew parser = new ParserNew(configuration, quotesMode);
			parser.reset(in, encoding);
			return parser;
		}

		@Override
		public ParserInterface newInstance(Reader in, ParserConfiguration configuration, boolean quotesMode) {
			ParserNew parser = new ParserNew(configuration, quotesMode);
			parser.reset(in);
			return parser;
		}
	}

	public void clearStats() {
		implementation.clearStats();
	}

	public void printStats() {
		implementation.printStats();
	}

	private ParserBase implementation;

	public ParserNew(ParserConfiguration configuration, boolean quotesMode) {
		if (configuration.parser.equals("2")) {
			implementation = new ParserImplementation2();
		} else {
			implementation = new ParserImplementation();
		}

		implementation.configure(configuration, quotesMode);
	}

	// Parser interface

	@Override
	public void reset(InputStream inputStream, String encoding) {
		implementation.reset(inputStream, encoding);
	}

	@Override
	public void reset(Reader reader) {
		implementation.reset(reader);
	}

	@Override
	public BUTree<SCompilationUnit> parseCompilationUnit() throws ParseException {
		return implementation.parseCompilationUnitEntry();
	}

	@Override
	public BUTree<SPackageDecl> parsePackageDecl() throws ParseException {
		return implementation.parsePackageDeclEntry();
	}

	@Override
	public BUTree<SImportDecl> parseImportDecl() throws ParseException {
		return implementation.parseImportDeclEntry();
	}

	@Override
	public BUTree<? extends STypeDecl> parseTypeDecl() throws ParseException {
		return implementation.parseTypeDeclEntry();
	}

	@Override
	public BUTree<? extends SMemberDecl> parseMemberDecl(TypeKind kind) throws ParseException {
		return implementation.parseMemberDeclEntry(kind);
	}

	@Override
	public BUTree<? extends SMemberDecl> parseAnnotationMemberDecl() throws ParseException {
		return implementation.parseAnnotationMemberDeclEntry();
	}

	@Override
	public BUTree<SNodeList> parseModifiers() throws ParseException {
		return implementation.parseModifiersEntry();
	}

	@Override
	public BUTree<SNodeList> parseAnnotations() throws ParseException {
		return implementation.parseAnnotationsEntry();
	}

	@Override
	public BUTree<SMethodDecl> parseMethodDecl() throws ParseException {
		return implementation.parseMethodDeclEntry();
	}

	@Override
	public BUTree<SFieldDecl> parseFieldDecl() throws ParseException {
		return implementation.parseFieldDeclEntry();
	}

	@Override
	public BUTree<SAnnotationMemberDecl> parseAnnotationElementDecl() throws ParseException {
		return implementation.parseAnnotationElementDeclEntry();
	}

	@Override
	public BUTree<SEnumConstantDecl> parseEnumConstantDecl() throws ParseException {
		return implementation.parseEnumConstantDeclEntry();
	}

	@Override
	public BUTree<SFormalParameter> parseFormalParameter() throws ParseException {
		return implementation.parseFormalParameterEntry();
	}

	@Override
	public BUTree<STypeParameter> parseTypeParameter() throws ParseException {
		return implementation.parseTypeParameterEntry();
	}

	@Override
	public BUTree<SNodeList> parseStatements() throws ParseException {
		return implementation.parseStatementsEntry();
	}

	@Override
	public BUTree<? extends SStmt> parseBlockStatement() throws ParseException {
		return implementation.parseBlockStatementEntry();
	}

	@Override
	public BUTree<? extends SExpr> parseExpression() throws ParseException {
		return implementation.parseExpressionEntry();
	}

	@Override
	public BUTree<? extends SType> parseType() throws ParseException {
		return implementation.parseTypeEntry();
	}

	@Override
	public BUTree<SQualifiedName> parseQualifiedName() throws ParseException {
		return implementation.parseQualifiedNameEntry();
	}

	@Override
	public BUTree<SName> parseName() throws ParseException {
		return implementation.parseNameEntry();
	}
}
