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
import org.jlato.internal.bu.STree;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.*;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.name.Name;
import org.jlato.tree.name.QualifiedName;
import org.jlato.tree.stmt.Stmt;
import org.jlato.tree.type.Type;

import static org.jlato.parser.ParserBase.TypeKind;

/**
 * @author Didier Villevalois
 */
public abstract class ParseContext<T extends Tree> {

	public final static ParseContext<CompilationUnit> CompilationUnit = new ParseContext<CompilationUnit>() {
		@Override
		protected BUTree<?> callProduction(ParserInterface parser) throws ParseException {
			return parser.parseCompilationUnit();
		}
	};

	public final static ParseContext<PackageDecl> PackageDecl = new ParseContext<PackageDecl>() {
		@Override
		protected BUTree<?> callProduction(ParserInterface parser) throws ParseException {
			return parser.parsePackageDecl();
		}
	};

	public final static ParseContext<ImportDecl> ImportDecl = new ParseContext<ImportDecl>() {
		@Override
		protected BUTree<?> callProduction(ParserInterface parser) throws ParseException {
			return parser.parseImportDecl();
		}
	};

	public final static ParseContext<TypeDecl> TypeDecl = new ParseContext<TypeDecl>() {
		@Override
		protected BUTree<?> callProduction(ParserInterface parser) throws ParseException {
			return parser.parseTypeDecl();
		}
	};

	public final static ParseContext<NodeList<Modifier>> Modifiers = new ParseContext<NodeList<Modifier>>() {
		@Override
		protected BUTree<?> callProduction(ParserInterface parser) throws ParseException {
			return parser.parseModifiers();
		}
	};

	public final static ParseContext<NodeList<AnnotationExpr>> Annotations = new ParseContext<NodeList<AnnotationExpr>>() {
		@Override
		protected BUTree<?> callProduction(ParserInterface parser) throws ParseException {
			return parser.parseAnnotations();
		}
	};

	public final static ParseContext<MemberDecl> MemberDecl(final TypeKind kind) {
		return new ParseContext<MemberDecl>() {
			@Override
			protected BUTree<?> callProduction(ParserInterface parser) throws ParseException {
				return parser.parseMemberDecl(kind);
			}
		};
	}

	public final static ParseContext<MemberDecl> MemberDecl = MemberDecl(TypeKind.Empty);

	public final static ParseContext<MemberDecl> Class_MemberDecl = MemberDecl(TypeKind.Class);

	public final static ParseContext<MemberDecl> Interface_MemberDecl = MemberDecl(TypeKind.Interface);

	public final static ParseContext<MemberDecl> Enum_MemberDecl = MemberDecl(TypeKind.Enum);

	public final static ParseContext<MemberDecl> Annotation_MemberDecl = new ParseContext<MemberDecl>() {
		@Override
		protected BUTree<?> callProduction(ParserInterface parser) throws ParseException {
			return parser.parseAnnotationMemberDecl();
		}
	};

	public final static ParseContext<MethodDecl> MethodDecl = new ParseContext<org.jlato.tree.decl.MethodDecl>() {
		@Override
		protected BUTree<?> callProduction(ParserInterface parser) throws ParseException {
			return parser.parseMethodDecl();
		}
	};

	public final static ParseContext<FieldDecl> FieldDecl = new ParseContext<org.jlato.tree.decl.FieldDecl>() {
		@Override
		protected BUTree<?> callProduction(ParserInterface parser) throws ParseException {
			return parser.parseFieldDecl();
		}
	};

	public final static ParseContext<AnnotationMemberDecl> AnnotationElementDecl = new ParseContext<org.jlato.tree.decl.AnnotationMemberDecl>() {
		@Override
		protected BUTree<?> callProduction(ParserInterface parser) throws ParseException {
			return parser.parseAnnotationElementDecl();
		}
	};

	public final static ParseContext<EnumConstantDecl> EnumConstantDecl = new ParseContext<org.jlato.tree.decl.EnumConstantDecl>() {
		@Override
		protected BUTree<?> callProduction(ParserInterface parser) throws ParseException {
			return parser.parseEnumConstantDecl();
		}
	};

	public final static ParseContext<FormalParameter> FormalParameter = new ParseContext<FormalParameter>() {
		@Override
		protected BUTree<?> callProduction(ParserInterface parser) throws ParseException {
			return parser.parseFormalParameter();
		}
	};

	public final static ParseContext<TypeParameter> TypeParameter = new ParseContext<org.jlato.tree.decl.TypeParameter>() {
		@Override
		protected BUTree<?> callProduction(ParserInterface parser) throws ParseException {
			return parser.parseTypeParameter();
		}
	};

	public final static ParseContext<NodeList<Stmt>> Statements = new ParseContext<NodeList<Stmt>>() {
		@Override
		protected BUTree<?> callProduction(ParserInterface parser) throws ParseException {
			return parser.parseStatements();
		}
	};

	public final static ParseContext<Stmt> Statement = new ParseContext<Stmt>() {
		@Override
		protected BUTree<?> callProduction(ParserInterface parser) throws ParseException {
			return parser.parseBlockStatement();
		}
	};

	public final static ParseContext<Expr> Expression = new ParseContext<Expr>() {
		@Override
		protected BUTree<?> callProduction(ParserInterface parser) throws ParseException {
			return parser.parseExpression();
		}
	};

	public final static ParseContext<Type> Type = new ParseContext<Type>() {
		@Override
		protected BUTree<?> callProduction(ParserInterface parser) throws ParseException {
			return parser.parseType();
		}
	};

	public final static ParseContext<QualifiedName> QualifiedName = new ParseContext<QualifiedName>() {
		@Override
		protected BUTree<?> callProduction(ParserInterface parser) throws ParseException {
			return parser.parseQualifiedName();
		}
	};

	public final static ParseContext<Name> Name = new ParseContext<Name>() {
		@Override
		protected BUTree<?> callProduction(ParserInterface parser) throws ParseException {
			return parser.parseName();
		}
	};

	protected abstract BUTree<?> callProduction(ParserInterface parser) throws ParseException;
}
