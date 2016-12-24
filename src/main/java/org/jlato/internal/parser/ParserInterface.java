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
public interface ParserInterface {

	interface Factory {

		ParserInterface newInstance(InputStream in, String encoding, ParserConfiguration configuration, boolean quotesMode);

		ParserInterface newInstance(Reader in, ParserConfiguration configuration, boolean quotesMode);
	}

	public static class DefaultParserFactory implements ParserInterface.Factory {
		@Override
		public ParserInterface newInstance(InputStream in, String encoding, ParserConfiguration configuration, boolean quotesMode) {
			ParserImplementation parser = new ParserImplementation();
			parser.configure(configuration, quotesMode);
			parser.reset(in, encoding);
			return parser;
		}

		@Override
		public ParserInterface newInstance(Reader in, ParserConfiguration configuration, boolean quotesMode) {
			ParserImplementation parser = new ParserImplementation();
			parser.configure(configuration, quotesMode);
			parser.reset(in);
			return parser;
		}
	}

	void reset(InputStream inputStream, String encoding);

	void reset(Reader reader);

	void clearStats();

	void printStats();

	BUTree<SCompilationUnit> parseCompilationUnitEntry() throws ParseException;

	BUTree<SPackageDecl> parsePackageDeclEntry() throws ParseException;

	BUTree<SImportDecl> parseImportDeclEntry() throws ParseException;

	BUTree<? extends STypeDecl> parseTypeDeclEntry() throws ParseException;

	BUTree<? extends SMemberDecl> parseMemberDeclEntry(TypeKind kind) throws ParseException;

	BUTree<? extends SMemberDecl> parseAnnotationMemberDeclEntry() throws ParseException;

	BUTree<SNodeList> parseModifiersEntry() throws ParseException;

	BUTree<SNodeList> parseAnnotationsEntry() throws ParseException;

	BUTree<SMethodDecl> parseMethodDeclEntry() throws ParseException;

	BUTree<SFieldDecl> parseFieldDeclEntry() throws ParseException;

	BUTree<SAnnotationMemberDecl> parseAnnotationElementDeclEntry() throws ParseException;

	BUTree<SEnumConstantDecl> parseEnumConstantDeclEntry() throws ParseException;

	BUTree<SFormalParameter> parseFormalParameterEntry() throws ParseException;

	BUTree<STypeParameter> parseTypeParameterEntry() throws ParseException;

	BUTree<SNodeList> parseStatementsEntry() throws ParseException;

	BUTree<? extends SStmt> parseBlockStatementEntry() throws ParseException;

	BUTree<? extends SExpr> parseExpressionEntry() throws ParseException;

	BUTree<? extends SType> parseTypeEntry() throws ParseException;

	BUTree<SQualifiedName> parseQualifiedNameEntry() throws ParseException;

	BUTree<SName> parseNameEntry() throws ParseException;
}
