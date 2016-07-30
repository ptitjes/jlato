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

package org.jlato.parser;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.decl.*;
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.bu.name.SQualifiedName;
import org.jlato.internal.bu.stmt.SStmt;
import org.jlato.internal.bu.type.SType;

import java.io.InputStream;
import java.io.Reader;

/**
 * @author Didier Villevalois
 */
public abstract class ParserInterface {

	protected interface Factory {

		ParserInterface newInstance(InputStream in, String encoding, ParserConfiguration configuration, boolean quotesMode);

		ParserInterface newInstance(Reader in, ParserConfiguration configuration, boolean quotesMode);
	}

	protected abstract void reset(InputStream inputStream, String encoding);

	protected abstract void reset(Reader reader);

	protected abstract BUTree<SCompilationUnit> parseCompilationUnit() throws ParseException;

	protected abstract BUTree<SPackageDecl> parsePackageDecl() throws ParseException;

	protected abstract BUTree<SImportDecl> parseImportDecl() throws ParseException;

	protected abstract BUTree<? extends STypeDecl> parseTypeDecl() throws ParseException;

	protected abstract BUTree<? extends SMemberDecl> parseMemberDecl(TypeKind kind) throws ParseException;

	protected abstract BUTree<? extends SMemberDecl> parseAnnotationMemberDecl() throws ParseException;

	protected abstract BUTree<SNodeList> parseModifiers() throws ParseException;

	protected abstract BUTree<SNodeList> parseAnnotations() throws ParseException;

	protected abstract BUTree<SMethodDecl> parseMethodDecl() throws ParseException;

	protected abstract BUTree<SFieldDecl> parseFieldDecl() throws ParseException;

	protected abstract BUTree<SAnnotationMemberDecl> parseAnnotationElementDecl() throws ParseException;

	protected abstract BUTree<SEnumConstantDecl> parseEnumConstantDecl() throws ParseException;

	protected abstract BUTree<SFormalParameter> parseFormalParameter() throws ParseException;

	protected abstract BUTree<STypeParameter> parseTypeParameter() throws ParseException;

	protected abstract BUTree<SNodeList> parseStatements() throws ParseException;

	protected abstract BUTree<? extends SStmt> parseBlockStatement() throws ParseException;

	protected abstract BUTree<? extends SExpr> parseExpression() throws ParseException;

	protected abstract BUTree<? extends SType> parseType() throws ParseException;

	protected abstract BUTree<SQualifiedName> parseQualifiedName() throws ParseException;

	protected abstract BUTree<SName> parseName() throws ParseException;

	public enum TypeKind {
		Empty,
		Class,
		Interface,
		Enum,
		AnnotationType,
		// Keep last comma
	}
}
