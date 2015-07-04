package org.jlato.tree.expr;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeData;
import org.jlato.tree.Expr;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.VariableDecl;

public class VariableDeclarationExpr extends Expr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public VariableDeclarationExpr instantiate(SLocation location) {
			return new VariableDeclarationExpr(location);
		}
	};

	private VariableDeclarationExpr(SLocation location) {
		super(location);
	}

	public VariableDeclarationExpr(VariableDecl declaration) {
		super(new SLocation(new SNode(kind, new SNodeData(treesOf(declaration)))));
	}

	public VariableDecl declaration() {
		return location.nodeChild(DECLARATION);
	}

	public VariableDeclarationExpr withDeclaration(VariableDecl declaration) {
		return location.nodeWithChild(DECLARATION, declaration);
	}

	private static final int DECLARATION = 0;
}
