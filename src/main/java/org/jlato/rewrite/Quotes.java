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

package org.jlato.rewrite;

import org.jlato.internal.bu.STree;
import org.jlato.internal.patterns.TreePattern;
import org.jlato.parser.ParseContext;
import org.jlato.parser.ParseException;
import org.jlato.parser.QuotesParser;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.*;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.stmt.Stmt;
import org.jlato.tree.type.Type;

/**
 * @author Didier Villevalois
 */
public abstract class Quotes {

	public static Pattern<PackageDecl> packageDecl(String string) {
		return quote(ParseContext.PackageDecl, string);
	}

	public static Pattern<ImportDecl> importDecl(String string) {
		return quote(ParseContext.ImportDecl, string);
	}

	public static Pattern<TypeDecl> typeDecl(String string) {
		return quote(ParseContext.TypeDecl, string);
	}

	public static Pattern<MemberDecl> memberDecl(String string) {
		return quote(ParseContext.MemberDecl, string);
	}

	public static Pattern<FormalParameter> param(String string) {
		return quote(ParseContext.Parameter, string);
	}

	public static Pattern<Stmt> stmt(String string) {
		return quote(ParseContext.Statement, string);
	}

	public static Pattern<Expr> expr(String string) {
		return quote(ParseContext.Expression, string);
	}

	public static Pattern<Type> type(String string) {
		return quote(ParseContext.Type, string);
	}

	public static <T extends Tree> Pattern<T> quote(ParseContext<T> context, String string) {
		QuotesParser parser = new QuotesParser();
		try {
			STree<?> tree = tree = parser.parse(context, string);
			return new TreePattern<T>(tree);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Can't parse quote: " + string, e);
		}
	}
}
