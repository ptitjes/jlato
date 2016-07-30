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
import org.jlato.parser.ParserConfiguration;
import org.jlato.parser.ParserInterface;

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

	private final ParserImplementation implementation = new ParserImplementation();

	public ParserNew(ParserConfiguration configuration, boolean quotesMode) {
		implementation.configure(configuration, quotesMode);
	}

	// Parser interface

	@Override
	protected void reset(InputStream inputStream, String encoding) {
		implementation.reset(inputStream, encoding);
	}

	@Override
	protected void reset(Reader reader) {
		implementation.reset(reader);
	}

	@Override
	protected BUTree<SCompilationUnit> parseCompilationUnit() throws ParseException {
		return implementation.parseCompilationUnit();
	}

	@Override
	protected BUTree<SPackageDecl> parsePackageDecl() throws ParseException {
		return implementation.wrapWithPrologAndEpilog(implementation.parsePackageDecl());
	}

	@Override
	protected BUTree<SImportDecl> parseImportDecl() throws ParseException {
		return implementation.wrapWithPrologAndEpilog(implementation.parseImportDecl());
	}

	@Override
	protected BUTree<? extends STypeDecl> parseTypeDecl() throws ParseException {
		return implementation.wrapWithPrologAndEpilog(implementation.parseTypeDecl());
	}

	@Override
	protected BUTree<? extends SMemberDecl> parseMemberDecl(ParserInterface.TypeKind kind) throws ParseException {
		return implementation.wrapWithPrologAndEpilog(implementation.parseClassOrInterfaceBodyDecl(kind));
	}

	@Override
	protected BUTree<? extends SMemberDecl> parseAnnotationMemberDecl() throws ParseException {
		return implementation.wrapWithPrologAndEpilog(implementation.parseAnnotationTypeBodyDecl());
	}

	@Override
	protected BUTree<SNodeList> parseModifiers() throws ParseException {
		return implementation.parseModifiers();
	}

	@Override
	protected BUTree<SNodeList> parseAnnotations() throws ParseException {
		return implementation.parseAnnotations();
	}

	@Override
	protected BUTree<SMethodDecl> parseMethodDecl() throws ParseException {
		implementation.run();
		BUTree<SNodeList> modifiers = implementation.parseModifiers();
		return implementation.wrapWithPrologAndEpilog(implementation.parseMethodDecl(modifiers));
	}

	@Override
	protected BUTree<SFieldDecl> parseFieldDecl() throws ParseException {
		implementation.run();
		BUTree<SNodeList> modifiers = implementation.parseModifiers();
		return implementation.wrapWithPrologAndEpilog(implementation.parseFieldDecl(modifiers));
	}

	@Override
	protected BUTree<SAnnotationMemberDecl> parseAnnotationElementDecl() throws ParseException {
		implementation.run();
		BUTree<SNodeList> modifiers = implementation.parseModifiers();
		return implementation.wrapWithPrologAndEpilog(implementation.parseAnnotationTypeMemberDecl(modifiers));
	}

	@Override
	protected BUTree<SEnumConstantDecl> parseEnumConstantDecl() throws ParseException {
		return implementation.wrapWithPrologAndEpilog(implementation.parseEnumConstantDecl());
	}

	@Override
	protected BUTree<SFormalParameter> parseFormalParameter() throws ParseException {
		return implementation.wrapWithPrologAndEpilog(implementation.parseFormalParameter());
	}

	@Override
	protected BUTree<STypeParameter> parseTypeParameter() throws ParseException {
		return implementation.wrapWithPrologAndEpilog(implementation.parseTypeParameter());
	}

	@Override
	protected BUTree<SNodeList> parseStatements() throws ParseException {
		return implementation.parseStatements();
	}

	@Override
	protected BUTree<? extends SStmt> parseBlockStatement() throws ParseException {
		return implementation.wrapWithPrologAndEpilog(implementation.parseBlockStatement());
	}

	@Override
	protected BUTree<? extends SExpr> parseExpression() throws ParseException {
		return implementation.wrapWithPrologAndEpilog(implementation.parseExpression());
	}

	@Override
	protected BUTree<? extends SType> parseType() throws ParseException {
		implementation.run();
		final BUTree<SNodeList> annotations = implementation.parseAnnotations();
		return implementation.wrapWithPrologAndEpilog(implementation.parseType(annotations));
	}

	@Override
	protected BUTree<SQualifiedName> parseQualifiedName() throws ParseException {
		return implementation.wrapWithPrologAndEpilog(implementation.parseQualifiedName());
	}

	@Override
	protected BUTree<SName> parseName() throws ParseException {
		return implementation.wrapWithPrologAndEpilog(implementation.parseName());
	}
}
