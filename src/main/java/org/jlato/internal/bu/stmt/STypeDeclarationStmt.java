package org.jlato.internal.bu.stmt;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.decl.STypeDecl;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.stmt.TDTypeDeclarationStmt;
import org.jlato.tree.*;
import org.jlato.tree.decl.*;

import static org.jlato.internal.shapes.LexicalShape.child;
import static org.jlato.internal.shapes.LexicalShape.composite;

public class STypeDeclarationStmt extends SNode<STypeDeclarationStmt> implements SStmt {

	public static BUTree<STypeDeclarationStmt> make(BUTree<? extends STypeDecl> typeDecl) {
		return new BUTree<STypeDeclarationStmt>(new STypeDeclarationStmt(typeDecl));
	}

	public final BUTree<? extends STypeDecl> typeDecl;

	public STypeDeclarationStmt(BUTree<? extends STypeDecl> typeDecl) {
		this.typeDecl = typeDecl;
	}

	@Override
	public Kind kind() {
		return Kind.TypeDeclarationStmt;
	}

	public STypeDeclarationStmt withTypeDecl(BUTree<? extends STypeDecl> typeDecl) {
		return new STypeDeclarationStmt(typeDecl);
	}

	@Override
	protected Tree doInstantiate(TDLocation<STypeDeclarationStmt> location) {
		return new TDTypeDeclarationStmt(location);
	}

	@Override
	public LexicalShape shape() {
		return shape;
	}

	@Override
	public STraversal firstChild() {
		return TYPE_DECL;
	}

	@Override
	public STraversal lastChild() {
		return TYPE_DECL;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		STypeDeclarationStmt state = (STypeDeclarationStmt) o;
		if (typeDecl == null ? state.typeDecl != null : !typeDecl.equals(state.typeDecl))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		if (typeDecl != null) result = 37 * result + typeDecl.hashCode();
		return result;
	}

	public static STypeSafeTraversal<STypeDeclarationStmt, STypeDecl, TypeDecl> TYPE_DECL = new STypeSafeTraversal<STypeDeclarationStmt, STypeDecl, TypeDecl>() {

		@Override
		public BUTree<?> doTraverse(STypeDeclarationStmt state) {
			return state.typeDecl;
		}

		@Override
		public STypeDeclarationStmt doRebuildParentState(STypeDeclarationStmt state, BUTree<STypeDecl> child) {
			return state.withTypeDecl(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(TYPE_DECL)
	);
}
