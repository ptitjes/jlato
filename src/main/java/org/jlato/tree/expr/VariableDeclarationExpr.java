package org.jlato.tree.expr;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.Expr;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.VariableDecl;

public class VariableDeclarationExpr extends Expr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public VariableDeclarationExpr instantiate(SLocation location) {
			return new VariableDeclarationExpr(location);
		}

		public LexicalShape shape() {
			return null;
		}
	};

	private VariableDeclarationExpr(SLocation location) {
		super(location);
	}

	public VariableDeclarationExpr(VariableDecl declaration) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(declaration)))));
	}

	public VariableDecl declaration() {
		return location.nodeChild(DECLARATION);
	}

	public VariableDeclarationExpr withDeclaration(VariableDecl declaration) {
		return location.nodeWithChild(DECLARATION, declaration);
	}

	private static final int DECLARATION = 0;
}
