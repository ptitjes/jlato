package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.decl.SLocalVariableDecl;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDVariableDeclarationExpr;
import org.jlato.tree.*;
import org.jlato.tree.decl.*;

import static org.jlato.internal.shapes.LexicalShape.child;

public class SVariableDeclarationExpr extends SNodeState<SVariableDeclarationExpr> implements SExpr {

	public static BUTree<SVariableDeclarationExpr> make(BUTree<SLocalVariableDecl> declaration) {
		return new BUTree<SVariableDeclarationExpr>(new SVariableDeclarationExpr(declaration));
	}

	public final BUTree<SLocalVariableDecl> declaration;

	public SVariableDeclarationExpr(BUTree<SLocalVariableDecl> declaration) {
		this.declaration = declaration;
	}

	@Override
	public Kind kind() {
		return Kind.VariableDeclarationExpr;
	}

	public BUTree<SLocalVariableDecl> declaration() {
		return declaration;
	}

	public SVariableDeclarationExpr withDeclaration(BUTree<SLocalVariableDecl> declaration) {
		return new SVariableDeclarationExpr(declaration);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SVariableDeclarationExpr> location) {
		return new TDVariableDeclarationExpr(location);
	}

	@Override
	public LexicalShape shape() {
		return shape;
	}

	@Override
	public STraversal firstChild() {
		return DECLARATION;
	}

	@Override
	public STraversal lastChild() {
		return DECLARATION;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SVariableDeclarationExpr state = (SVariableDeclarationExpr) o;
		if (declaration == null ? state.declaration != null : !declaration.equals(state.declaration))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		if (declaration != null) result = 37 * result + declaration.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SVariableDeclarationExpr, SLocalVariableDecl, LocalVariableDecl> DECLARATION = new STypeSafeTraversal<SVariableDeclarationExpr, SLocalVariableDecl, LocalVariableDecl>() {

		@Override
		public BUTree<?> doTraverse(SVariableDeclarationExpr state) {
			return state.declaration;
		}

		@Override
		public SVariableDeclarationExpr doRebuildParentState(SVariableDeclarationExpr state, BUTree<SLocalVariableDecl> child) {
			return state.withDeclaration(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public static final LexicalShape shape = child(DECLARATION);
}
