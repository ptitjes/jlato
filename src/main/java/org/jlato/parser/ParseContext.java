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

import org.jlato.tree.*;
import org.jlato.tree.decl.CompilationUnit;
import org.jlato.tree.decl.TypeDecl;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.Type;

import static org.jlato.tree.decl.TypeDecl.*;

/**
 * @author Didier Villevalois
 */
public abstract class ParseContext<T extends Tree> {

	public final static ParseContext<CompilationUnit> CompilationUnit = new ParseContext<CompilationUnit>() {
		@Override
		protected CompilationUnit callProduction(ParserImpl parser) throws ParseException {
			return parser.CompilationUnit();
		}
	};

	public final static ParseContext<TypeDecl> TypeDecl = new ParseContext<TypeDecl>() {
		@Override
		protected TypeDecl callProduction(ParserImpl parser) throws ParseException {
			return parser.TypeDecl();
		}
	};

	public final static ParseContext<Decl> ClassBodyDecl = new ParseContext<Decl>() {
		@Override
		protected Decl callProduction(ParserImpl parser) throws ParseException {
			return parser.ClassOrInterfaceBodyDecl(TypeKind.Class);
		}
	};

	public final static ParseContext<Stmt> Statement = new ParseContext<Stmt>() {
		@Override
		protected Stmt callProduction(ParserImpl parser) throws ParseException {
			return parser.Statement();
		}
	};

	public final static ParseContext<Expr> Expression = new ParseContext<Expr>() {
		@Override
		protected Expr callProduction(ParserImpl parser) throws ParseException {
			return parser.Expression();
		}
	};

	public final static ParseContext<Type> Type = new ParseContext<Type>() {
		@Override
		protected Type callProduction(ParserImpl parser) throws ParseException {
			final NodeList<AnnotationExpr> annotations = parser.Annotations();
			return parser.Type(annotations);
		}
	};

	protected abstract T callProduction(ParserImpl parser) throws ParseException;
}
