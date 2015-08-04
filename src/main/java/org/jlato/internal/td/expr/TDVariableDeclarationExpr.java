package org.jlato.internal.td.expr;

import org.jlato.internal.bu.decl.SLocalVariableDecl;
import org.jlato.internal.bu.expr.SVariableDeclarationExpr;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.decl.LocalVariableDecl;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.expr.VariableDeclarationExpr;
import org.jlato.util.Mutation;

public class TDVariableDeclarationExpr extends TreeBase<SVariableDeclarationExpr, Expr, VariableDeclarationExpr> implements VariableDeclarationExpr {

	public Kind kind() {
		return Kind.VariableDeclarationExpr;
	}

	public TDVariableDeclarationExpr(SLocation<SVariableDeclarationExpr> location) {
		super(location);
	}

	public TDVariableDeclarationExpr(LocalVariableDecl declaration) {
		super(new SLocation<SVariableDeclarationExpr>(SVariableDeclarationExpr.make(TreeBase.<SLocalVariableDecl>treeOf(declaration))));
	}

	public LocalVariableDecl declaration() {
		return location.safeTraversal(SVariableDeclarationExpr.DECLARATION);
	}

	public VariableDeclarationExpr withDeclaration(LocalVariableDecl declaration) {
		return location.safeTraversalReplace(SVariableDeclarationExpr.DECLARATION, declaration);
	}

	public VariableDeclarationExpr withDeclaration(Mutation<LocalVariableDecl> mutation) {
		return location.safeTraversalMutate(SVariableDeclarationExpr.DECLARATION, mutation);
	}
}
