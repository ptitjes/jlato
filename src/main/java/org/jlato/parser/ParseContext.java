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

import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.*;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.stmt.Stmt;
import org.jlato.tree.type.Type;

import static org.jlato.tree.decl.TypeDecl.TypeKind;

/**
 * @author Didier Villevalois
 */
public abstract class ParseContext<T extends Tree> {

	public final static ParseContext<CompilationUnit> CompilationUnit = new ParseContext<CompilationUnit>() {
		@Override
		protected CompilationUnit callProduction(ParserBase parser) throws ParseException {
			return parser.CompilationUnit();
		}
	};

	public final static ParseContext<PackageDecl> PackageDecl = new ParseContext<PackageDecl>() {
		@Override
		protected PackageDecl callProduction(ParserBase parser) throws ParseException {
			return parser.PackageDecl();
		}
	};

	public final static ParseContext<ImportDecl> ImportDecl = new ParseContext<ImportDecl>() {
		@Override
		protected ImportDecl callProduction(ParserBase parser) throws ParseException {
			return parser.ImportDecl();
		}
	};

	public final static ParseContext<TypeDecl> TypeDecl = new ParseContext<TypeDecl>() {
		@Override
		protected TypeDecl callProduction(ParserBase parser) throws ParseException {
			return parser.TypeDecl();
		}
	};

	public final static ParseContext<MemberDecl> MemberDecl = new ParseContext<MemberDecl>() {
		@Override
		protected MemberDecl callProduction(ParserBase parser) throws ParseException {
			return parser.ClassOrInterfaceBodyDecl(TypeKind.Class);
		}
	};

	public final static ParseContext<MemberDecl> Class_MemberDecl = MemberDecl;

	public final static ParseContext<MemberDecl> Interface_MemberDecl = MemberDecl;

	public final static ParseContext<MemberDecl> Enum_MemberDecl = MemberDecl;

	public final static ParseContext<MemberDecl> Annotation_MemberDecl = new ParseContext<MemberDecl>() {
		@Override
		protected MemberDecl callProduction(ParserBase parser) throws ParseException {
			return parser.AnnotationTypeBodyDecl();
		}
	};

	public final static ParseContext<MethodDecl> MethodDecl = new ParseContext<org.jlato.tree.decl.MethodDecl>() {
		@Override
		protected MethodDecl callProduction(ParserBase parser) throws ParseException {
			NodeList modifiers = parser.Modifiers();
			return parser.MethodDecl(modifiers);
		}
	};

	public final static ParseContext<FieldDecl> FieldDecl = new ParseContext<org.jlato.tree.decl.FieldDecl>() {
		@Override
		protected FieldDecl callProduction(ParserBase parser) throws ParseException {
			NodeList modifiers = parser.Modifiers();
			return parser.FieldDecl(modifiers);
		}
	};

	public final static ParseContext<AnnotationMemberDecl> AnnotationMemberDecl = new ParseContext<org.jlato.tree.decl.AnnotationMemberDecl>() {
		@Override
		protected AnnotationMemberDecl callProduction(ParserBase parser) throws ParseException {
			NodeList modifiers = parser.Modifiers();
			return parser.AnnotationTypeMemberDecl(modifiers);
		}
	};

	public final static ParseContext<EnumConstantDecl> EnumConstantDecl = new ParseContext<org.jlato.tree.decl.EnumConstantDecl>() {
		@Override
		protected EnumConstantDecl callProduction(ParserBase parser) throws ParseException {
			return parser.EnumConstantDecl();
		}
	};

	public final static ParseContext<FormalParameter> Parameter = new ParseContext<FormalParameter>() {
		@Override
		protected FormalParameter callProduction(ParserBase parser) throws ParseException {
			return parser.FormalParameter();
		}
	};

	public final static ParseContext<TypeParameter> TypeParameter = new ParseContext<org.jlato.tree.decl.TypeParameter>() {
		@Override
		protected TypeParameter callProduction(ParserBase parser) throws ParseException {
			return parser.TypeParameter();
		}
	};

	public final static ParseContext<Stmt> Statement = new ParseContext<Stmt>() {
		@Override
		protected Stmt callProduction(ParserBase parser) throws ParseException {
			return parser.Statement();
		}
	};

	public final static ParseContext<Expr> Expression = new ParseContext<Expr>() {
		@Override
		protected Expr callProduction(ParserBase parser) throws ParseException {
			return parser.Expression();
		}
	};

	public final static ParseContext<Type> Type = new ParseContext<Type>() {
		@Override
		protected Type callProduction(ParserBase parser) throws ParseException {
			final NodeList<AnnotationExpr> annotations = parser.Annotations();
			return parser.Type(annotations);
		}
	};

	protected abstract T callProduction(ParserBase parser) throws ParseException;
}
