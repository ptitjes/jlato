/*
 * Copyright (C) 2015 Didier Villevalois.
 *
 * This file is part of JLaTo.
 *
 * JLaTo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JLaTo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
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
import org.jlato.tree.NodeList;
import org.jlato.tree.decl.*;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.name.Name;
import org.jlato.tree.name.QualifiedName;
import org.jlato.tree.stmt.Stmt;
import org.jlato.tree.type.Type;

import java.io.InputStream;
import java.io.Reader;

/**
 * @author Didier Villevalois
 */
abstract class ParserInterface {

	interface Factory {

		ParserInterface newInstance(InputStream in, String encoding);

		ParserInterface newInstance(Reader in);
	}

	protected ParserConfiguration configuration;
	protected boolean quotesMode = false;

	final void configure(ParserConfiguration configuration) {
		this.configuration = configuration;
	}

	abstract void reset(InputStream inputStream, String encoding);

	abstract void reset(Reader reader);

	abstract BUTree<SCompilationUnit> parseCompilationUnit() throws ParseException;

	abstract BUTree<SPackageDecl> parsePackageDecl() throws ParseException;

	abstract BUTree<SImportDecl> parseImportDecl() throws ParseException;

	abstract BUTree<? extends STypeDecl> parseTypeDecl() throws ParseException;

	abstract BUTree<? extends SMemberDecl> parseMemberDecl(TypeKind kind) throws ParseException;

	abstract BUTree<? extends SMemberDecl> parseAnnotationMemberDecl() throws ParseException;

	abstract BUTree<SNodeList> parseModifiers() throws ParseException;

	abstract BUTree<SNodeList> parseAnnotations() throws ParseException;

	abstract BUTree<SMethodDecl> parseMethodDecl() throws ParseException;

	abstract BUTree<SFieldDecl> parseFieldDecl() throws ParseException;

	abstract BUTree<SAnnotationMemberDecl> parseAnnotationElementDecl() throws ParseException;

	abstract BUTree<SEnumConstantDecl> parseEnumConstantDecl() throws ParseException;

	abstract BUTree<SFormalParameter> parseFormalParameter() throws ParseException;

	abstract BUTree<STypeParameter> parseTypeParameter() throws ParseException;

	abstract BUTree<SNodeList> parseStatements() throws ParseException;

	abstract BUTree<? extends SStmt> parseStatement() throws ParseException;

	abstract BUTree<? extends SExpr> parseExpression() throws ParseException;

	abstract BUTree<? extends SType> parseType() throws ParseException;

	abstract BUTree<SQualifiedName> parseQualifiedName() throws ParseException;

	abstract BUTree<SName> parseName() throws ParseException;

	public enum TypeKind {
		Empty,
		Class,
		Interface,
		Enum,
		AnnotationType,
		// Keep last comma
	}
}
