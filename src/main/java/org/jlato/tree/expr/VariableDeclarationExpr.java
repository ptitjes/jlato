package org.jlato.tree.expr;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.Expr;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.LocalVariableDecl;

import static org.jlato.internal.shapes.LexicalShape.Factory.child;

public class VariableDeclarationExpr extends Expr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public VariableDeclarationExpr instantiate(SLocation location) {
			return new VariableDeclarationExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private VariableDeclarationExpr(SLocation location) {
		super(location);
	}

	public VariableDeclarationExpr(LocalVariableDecl declaration) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(declaration)))));
	}

	public LocalVariableDecl declaration() {
		return location.nodeChild(DECLARATION);
	}

	public VariableDeclarationExpr withDeclaration(LocalVariableDecl declaration) {
		return location.nodeWithChild(DECLARATION, declaration);
	}

	private static final int DECLARATION = 0;

	public final static LexicalShape shape = child(DECLARATION);
}
