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
		protected CompilationUnit callProduction(ASTParser parser) throws ParseException {
			return parser.CompilationUnit();
		}
	};

	public final static ParseContext<TypeDecl> TypeDecl = new ParseContext<TypeDecl>() {
		@Override
		protected TypeDecl callProduction(ASTParser parser) throws ParseException {
			return parser.TypeDecl();
		}
	};

	public final static ParseContext<Decl> ClassBodyDecl = new ParseContext<Decl>() {
		@Override
		protected Decl callProduction(ASTParser parser) throws ParseException {
			return parser.ClassOrInterfaceBodyDecl(TypeKind.Class);
		}
	};

	public final static ParseContext<Stmt> Statement = new ParseContext<Stmt>() {
		@Override
		protected Stmt callProduction(ASTParser parser) throws ParseException {
			return parser.Statement();
		}
	};

	public final static ParseContext<Expr> Expression = new ParseContext<Expr>() {
		@Override
		protected Expr callProduction(ASTParser parser) throws ParseException {
			return parser.Expression();
		}
	};

	public final static ParseContext<Type> Type = new ParseContext<Type>() {
		@Override
		protected Type callProduction(ASTParser parser) throws ParseException {
			final NodeList<AnnotationExpr> annotations = parser.Annotations();
			return parser.Type(annotations);
		}
	};

	protected abstract T callProduction(ASTParser parser) throws ParseException;
}
