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
import org.jlato.internal.parser.ParserInterface;
import org.jlato.internal.parser.TypeKind;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.*;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.name.Name;
import org.jlato.tree.name.QualifiedName;
import org.jlato.tree.stmt.Stmt;
import org.jlato.tree.type.Type;

/**
 * @author Didier Villevalois
 */
public abstract class ParseContext<T extends Tree> {

	public final static ParseContext<CompilationUnit> CompilationUnit = new ParseContext<CompilationUnit>() {
		@Override
		protected BUTree<?> callProduction(ParserInterface parser) throws ParseException {
			return parser.parseCompilationUnitEntry();
		}
	};

	public final static ParseContext<PackageDecl> PackageDecl = new ParseContext<PackageDecl>() {
		@Override
		protected BUTree<?> callProduction(ParserInterface parser) throws ParseException {
			return parser.parsePackageDeclEntry();
		}
	};

	public final static ParseContext<ImportDecl> ImportDecl = new ParseContext<ImportDecl>() {
		@Override
		protected BUTree<?> callProduction(ParserInterface parser) throws ParseException {
			return parser.parseImportDeclEntry();
		}
	};

	public final static ParseContext<TypeDecl> TypeDecl = new ParseContext<TypeDecl>() {
		@Override
		protected BUTree<?> callProduction(ParserInterface parser) throws ParseException {
			return parser.parseTypeDeclEntry();
		}
	};

	public final static ParseContext<NodeList<Modifier>> Modifiers = new ParseContext<NodeList<Modifier>>() {
		@Override
		protected BUTree<?> callProduction(ParserInterface parser) throws ParseException {
			return parser.parseModifiersEntry();
		}
	};

	public final static ParseContext<NodeList<AnnotationExpr>> Annotations = new ParseContext<NodeList<AnnotationExpr>>() {
		@Override
		protected BUTree<?> callProduction(ParserInterface parser) throws ParseException {
			return parser.parseAnnotationsEntry();
		}
	};

	private static ParseContext<MemberDecl> memberDecl(final TypeKind kind) {
		return new ParseContext<MemberDecl>() {
			@Override
			protected BUTree<?> callProduction(ParserInterface parser) throws ParseException {
				return parser.parseMemberDeclEntry(kind);
			}
		};
	}

	public final static ParseContext<MemberDecl> MemberDecl = memberDecl(TypeKind.Empty);

	public final static ParseContext<MemberDecl> MemberDeclWithinClass = memberDecl(TypeKind.Class);

	public final static ParseContext<MemberDecl> MemberDeclWithinInterface = memberDecl(TypeKind.Interface);

	public final static ParseContext<MemberDecl> MemberDeclWithinEnum = memberDecl(TypeKind.Enum);

	public final static ParseContext<MemberDecl> MemberDeclWithinAnnotation = new ParseContext<MemberDecl>() {
		@Override
		protected BUTree<?> callProduction(ParserInterface parser) throws ParseException {
			return parser.parseAnnotationMemberDeclEntry();
		}
	};

	public final static ParseContext<MethodDecl> MethodDecl = new ParseContext<org.jlato.tree.decl.MethodDecl>() {
		@Override
		protected BUTree<?> callProduction(ParserInterface parser) throws ParseException {
			return parser.parseMethodDeclEntry();
		}
	};

	public final static ParseContext<FieldDecl> FieldDecl = new ParseContext<org.jlato.tree.decl.FieldDecl>() {
		@Override
		protected BUTree<?> callProduction(ParserInterface parser) throws ParseException {
			return parser.parseFieldDeclEntry();
		}
	};

	public final static ParseContext<AnnotationMemberDecl> AnnotationElementDecl = new ParseContext<org.jlato.tree.decl.AnnotationMemberDecl>() {
		@Override
		protected BUTree<?> callProduction(ParserInterface parser) throws ParseException {
			return parser.parseAnnotationElementDeclEntry();
		}
	};

	public final static ParseContext<EnumConstantDecl> EnumConstantDecl = new ParseContext<org.jlato.tree.decl.EnumConstantDecl>() {
		@Override
		protected BUTree<?> callProduction(ParserInterface parser) throws ParseException {
			return parser.parseEnumConstantDeclEntry();
		}
	};

	public final static ParseContext<FormalParameter> FormalParameter = new ParseContext<FormalParameter>() {
		@Override
		protected BUTree<?> callProduction(ParserInterface parser) throws ParseException {
			return parser.parseFormalParameterEntry();
		}
	};

	public final static ParseContext<TypeParameter> TypeParameter = new ParseContext<org.jlato.tree.decl.TypeParameter>() {
		@Override
		protected BUTree<?> callProduction(ParserInterface parser) throws ParseException {
			return parser.parseTypeParameterEntry();
		}
	};

	public final static ParseContext<NodeList<Stmt>> Statements = new ParseContext<NodeList<Stmt>>() {
		@Override
		protected BUTree<?> callProduction(ParserInterface parser) throws ParseException {
			return parser.parseStatementsEntry();
		}
	};

	public final static ParseContext<Stmt> Statement = new ParseContext<Stmt>() {
		@Override
		protected BUTree<?> callProduction(ParserInterface parser) throws ParseException {
			return parser.parseBlockStatementEntry();
		}
	};

	public final static ParseContext<Expr> Expression = new ParseContext<Expr>() {
		@Override
		protected BUTree<?> callProduction(ParserInterface parser) throws ParseException {
			return parser.parseExpressionEntry();
		}
	};

	public final static ParseContext<Type> Type = new ParseContext<Type>() {
		@Override
		protected BUTree<?> callProduction(ParserInterface parser) throws ParseException {
			return parser.parseTypeEntry();
		}
	};

	public final static ParseContext<QualifiedName> QualifiedName = new ParseContext<QualifiedName>() {
		@Override
		protected BUTree<?> callProduction(ParserInterface parser) throws ParseException {
			return parser.parseQualifiedNameEntry();
		}
	};

	public final static ParseContext<Name> Name = new ParseContext<Name>() {
		@Override
		protected BUTree<?> callProduction(ParserInterface parser) throws ParseException {
			return parser.parseNameEntry();
		}
	};

	protected abstract BUTree<?> callProduction(ParserInterface parser) throws ParseException;
}
