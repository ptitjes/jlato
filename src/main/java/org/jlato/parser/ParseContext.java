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

import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.STree;
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
		protected STree<?> callProduction(ParserBase parser) throws ParseException {
			return parser.CompilationUnit();
		}
	};

	public final static ParseContext<PackageDecl> PackageDecl = new ParseContext<PackageDecl>() {
		@Override
		protected STree<?> callProduction(ParserBase parser) throws ParseException {
			return parser.PackageDecl();
		}
	};

	public final static ParseContext<ImportDecl> ImportDecl = new ParseContext<ImportDecl>() {
		@Override
		protected STree<?> callProduction(ParserBase parser) throws ParseException {
			return parser.ImportDecl();
		}
	};

	public final static ParseContext<TypeDecl> TypeDecl = new ParseContext<TypeDecl>() {
		@Override
		protected STree<?> callProduction(ParserBase parser) throws ParseException {
			return parser.TypeDecl();
		}
	};

	public final static ParseContext<MemberDecl> MemberDecl = new ParseContext<MemberDecl>() {
		@Override
		protected STree<?> callProduction(ParserBase parser) throws ParseException {
			return parser.ClassOrInterfaceBodyDecl(TypeKind.Class);
		}
	};

	public final static ParseContext<MemberDecl> Class_MemberDecl = MemberDecl;

	public final static ParseContext<MemberDecl> Interface_MemberDecl = MemberDecl;

	public final static ParseContext<MemberDecl> Enum_MemberDecl = MemberDecl;

	public final static ParseContext<MemberDecl> Annotation_MemberDecl = new ParseContext<MemberDecl>() {
		@Override
		protected STree<?> callProduction(ParserBase parser) throws ParseException {
			return parser.AnnotationTypeBodyDecl();
		}
	};

	public final static ParseContext<MethodDecl> MethodDecl = new ParseContext<org.jlato.tree.decl.MethodDecl>() {
		@Override
		protected STree<?> callProduction(ParserBase parser) throws ParseException {
			STree<SNodeListState> modifiers = parser.Modifiers();
			return parser.MethodDecl(modifiers);
		}
	};

	public final static ParseContext<FieldDecl> FieldDecl = new ParseContext<org.jlato.tree.decl.FieldDecl>() {
		@Override
		protected STree<?> callProduction(ParserBase parser) throws ParseException {
			STree<SNodeListState> modifiers = parser.Modifiers();
			return parser.FieldDecl(modifiers);
		}
	};

	public final static ParseContext<AnnotationMemberDecl> AnnotationMemberDecl = new ParseContext<org.jlato.tree.decl.AnnotationMemberDecl>() {
		@Override
		protected STree<?> callProduction(ParserBase parser) throws ParseException {
			STree<SNodeListState> modifiers = parser.Modifiers();
			return parser.AnnotationTypeMemberDecl(modifiers);
		}
	};

	public final static ParseContext<EnumConstantDecl> EnumConstantDecl = new ParseContext<org.jlato.tree.decl.EnumConstantDecl>() {
		@Override
		protected STree<?> callProduction(ParserBase parser) throws ParseException {
			return parser.EnumConstantDecl();
		}
	};

	public final static ParseContext<FormalParameter> Parameter = new ParseContext<FormalParameter>() {
		@Override
		protected STree<?> callProduction(ParserBase parser) throws ParseException {
			return parser.FormalParameter();
		}
	};

	public final static ParseContext<TypeParameter> TypeParameter = new ParseContext<org.jlato.tree.decl.TypeParameter>() {
		@Override
		protected STree<?> callProduction(ParserBase parser) throws ParseException {
			return parser.TypeParameter();
		}
	};

	public final static ParseContext<Stmt> Statement = new ParseContext<Stmt>() {
		@Override
		protected STree<?> callProduction(ParserBase parser) throws ParseException {
			return parser.Statement();
		}
	};

	public final static ParseContext<Expr> Expression = new ParseContext<Expr>() {
		@Override
		protected STree<?> callProduction(ParserBase parser) throws ParseException {
			return parser.Expression();
		}
	};

	public final static ParseContext<Type> Type = new ParseContext<Type>() {
		@Override
		protected STree<?> callProduction(ParserBase parser) throws ParseException {
			final STree<SNodeListState> annotations = parser.Annotations();
			return parser.Type(annotations);
		}
	};

	protected abstract STree<?> callProduction(ParserBase parser) throws ParseException;
}
