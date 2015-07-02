package org.jlato.tree.expr;

import org.jlato.internal.bu.SNode;
import org.jlato.tree.Expr;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.Modifiers;
import org.jlato.tree.decl.VariableDecl;
import org.jlato.tree.decl.VariableDeclarator;
import org.jlato.tree.type.Type;

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
		super(new SLocation(new SNode(kind, runOf(declaration))));
	}

	public VariableDecl declaration() {
		return location.nodeChild(DECLARATION);
	}

	public VariableDeclarationExpr withDeclaration(VariableDecl declaration) {
		return location.nodeWithChild(DECLARATION, declaration);
	}

	private static final int DECLARATION = 0;
}
