package org.jlato.internal.bu.stmt;

import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.decl.STypeDecl;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.stmt.TDTypeDeclarationStmt;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.TypeDecl;

import static org.jlato.internal.shapes.LexicalShape.*;

public class STypeDeclarationStmt extends SNodeState<STypeDeclarationStmt> implements SStmt {

	public static STree<STypeDeclarationStmt> make(STree<? extends STypeDecl> typeDecl) {
		return new STree<STypeDeclarationStmt>(new STypeDeclarationStmt(typeDecl));
	}

	public final STree<? extends STypeDecl> typeDecl;

	public STypeDeclarationStmt(STree<? extends STypeDecl> typeDecl) {
		this.typeDecl = typeDecl;
	}

	@Override
	public Kind kind() {
		return Kind.TypeDeclarationStmt;
	}

	public STree<? extends STypeDecl> typeDecl() {
		return typeDecl;
	}

	public STypeDeclarationStmt withTypeDecl(STree<? extends STypeDecl> typeDecl) {
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
		public STree<?> doTraverse(STypeDeclarationStmt state) {
			return state.typeDecl;
		}

		@Override
		public STypeDeclarationStmt doRebuildParentState(STypeDeclarationStmt state, STree<STypeDecl> child) {
			return state.withTypeDecl(child);
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

	public static final LexicalShape shape = composite(
			child(TYPE_DECL)
	);
}
